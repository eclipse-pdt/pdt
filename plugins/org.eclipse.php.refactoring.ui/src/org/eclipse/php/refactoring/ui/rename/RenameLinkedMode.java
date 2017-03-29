/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ScriptModelUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.link.ILinkedModeListener;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.link.LinkedModeUI.ExitFlags;
import org.eclipse.jface.text.link.LinkedModeUI.IExitPolicy;
import org.eclipse.jface.text.link.LinkedPosition;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.core.search.IOccurrencesFinder.OccurrenceLocation;
import org.eclipse.php.internal.ui.editor.EditorHighlightingSynchronizer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.refactoring.core.LinkedNodeFinder;
import org.eclipse.php.refactoring.core.utils.ASTUtils;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.php.refactoring.ui.utils.PHPConventionsUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.texteditor.link.EditorLinkedModeUI;

public class RenameLinkedMode {

	private class FocusEditingSupport implements IEditingSupport {
		public boolean ownsFocusShell() {
			if (fInfoPopup == null)
				return false;
			if (fInfoPopup.ownsFocusShell()) {
				return true;
			}

			Shell editorShell = fEditor.getSite().getShell();
			Shell activeShell = editorShell.getDisplay().getActiveShell();
			if (editorShell == activeShell)
				return true;
			return false;
		}

		public boolean isOriginator(DocumentEvent event, IRegion subjectRegion) {
			return false; // leave on external modification outside positions
		}

	}

	private class EditorSynchronizer implements ILinkedModeListener {
		public void left(LinkedModeModel model, int flags) {
			linkedModeLeft();
			if ((flags & ILinkedModeListener.UPDATE_CARET) != 0) {
				doRename(fShowPreview);
			}
		}

		public void resume(LinkedModeModel model, int flags) {
		}

		public void suspend(LinkedModeModel model) {
		}
	}

	private class ExitPolicy implements IExitPolicy {
		private IDocument fDocument;

		public ExitPolicy(IDocument document) {
			fDocument = document;

		}

		public ExitFlags doExit(LinkedModeModel model, VerifyEvent event, int offset, int length) {
			fShowPreview = (event.stateMask & SWT.CTRL) != 0
					&& (event.character == SWT.CR || event.character == SWT.LF);

			if (length == 0 && (event.character == SWT.BS || event.character == SWT.DEL)) {
				LinkedPosition position = model
						.findPosition(new PHPElementLinkedPosition(fDocument, offset, 0, LinkedPositionGroup.NO_STOP));
				if (position != null) {
					if (event.character == SWT.BS) {
						if (offset - 1 < position.getOffset()) {
							// skip backspace at beginning of linked position
							event.doit = false;
						}
					} else /* event.character == SWT.DEL */ {
						if (offset + 1 > position.getOffset() + position.getLength()) {
							// skip delete at end of linked position
							event.doit = false;
						}
					}
				}
			}

			return null; // don't change behavior
		}
	}

	private static RenameLinkedMode fgActiveLinkedMode;

	private final PHPStructuredEditor fEditor;
	// private final IModelElement fJavaElement;

	private RenameInformationPopup fInfoPopup;

	private Point fOriginalSelection;
	private String fOriginalName;

	private PHPElementLinkedPosition fNamePosition;
	private LinkedModeModel fLinkedModeModel;
	private LinkedPositionGroup fLinkedPositionGroup;
	private final FocusEditingSupport fFocusEditingSupport;
	private boolean fShowPreview;

	// private Program root;

	private ASTNode selectedNode;

	public RenameLinkedMode(IModelElement element, PHPStructuredEditor editor) {
		Assert.isNotNull(editor);
		fEditor = editor;
		// fJavaElement = element;
		fFocusEditingSupport = new FocusEditingSupport();
	}

	public static RenameLinkedMode getActiveLinkedMode() {
		if (fgActiveLinkedMode != null) {
			ISourceViewer viewer = fgActiveLinkedMode.fEditor.getTextViewer();
			if (viewer != null) {
				StyledText textWidget = viewer.getTextWidget();
				if (textWidget != null && !textWidget.isDisposed()) {
					return fgActiveLinkedMode;
				}
			}
			// make sure we don't hold onto the active linked mode if anything
			// went wrong with canceling:
			fgActiveLinkedMode = null;
		}
		return null;
	}

	public void start() {
		if (getActiveLinkedMode() != null) {
			// for safety; should already be handled in RenameJavaElementAction
			fgActiveLinkedMode.startFullDialog();
			return;
		}

		TextViewer viewer = fEditor.getTextViewer();
		IDocument document = viewer.getDocument();
		fOriginalSelection = viewer.getSelectedRange();
		int offset = fOriginalSelection.x;

		IEditorInput input = fEditor.getEditorInput();

		// Not applicable to other editor input.
		// E.g. external file.
		if (!(input instanceof IFileEditorInput)) {
			if (input instanceof IURIEditorInput) {
				MessageDialog.openError(fEditor.getEditorSite().getShell(), Messages.RenameLinkedMode_1,
						Messages.RenameLinkedMode_3);
			} else {
				MessageDialog.openError(fEditor.getEditorSite().getShell(), Messages.RenameLinkedMode_1,
						Messages.RenameLinkedMode_4);
			}
			return;
		}
		IFile file = ((IFileEditorInput) input).getFile();

		ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);

		if (sourceModule == null) {
			MessageDialog.openError(fEditor.getEditorSite().getShell(), Messages.RenameLinkedMode_1,
					Messages.RenameLinkedMode_2);
			return;
		}

		try {
			Program root = ASTUtils.createProgramFromSource(sourceModule);

			fLinkedPositionGroup = new LinkedPositionGroup();
			selectedNode = NodeFinder.perform(root, fOriginalSelection.x, fOriginalSelection.y);
			if (selectedNode == null) {
				MessageDialog.openError(fEditor.getEditorSite().getShell(), Messages.RenameLinkedMode_1,
						Messages.RenameLinkedMode_4);
				return;
			}
			if (((ASTNode) selectedNode).getParent() instanceof NamespaceName) {
				NamespaceName namespaceName = (NamespaceName) ((ASTNode) selectedNode).getParent();
				if (namespaceName.segments() != null && namespaceName.segments().size() > 0) {

					if (!namespaceName.segments().get(namespaceName.segments().size() - 1).equals(selectedNode)) {
						MessageDialog.openError(fEditor.getEditorSite().getShell(), Messages.RenameLinkedMode_1,
								Messages.RenameLinkedMode_4);
						return;
					}
				}
			}

			fOriginalName = getCurrentElementName(selectedNode);

			final int pos = selectedNode.getStart();

			OccurrenceLocation[] sameNodes = LinkedNodeFinder.findByNode(root, selectedNode);

			// TODO: copied from LinkedNamesAssistProposal#apply(..):
			// sort for iteration order, starting with the node @ offset
			Arrays.sort(sameNodes, new Comparator<OccurrenceLocation>() {
				public int compare(OccurrenceLocation o1, OccurrenceLocation o2) {
					return rank(o1) - rank(o2);
				}

				/**
				 * Returns the absolute rank of an <code>ASTNode</code>. Nodes
				 * preceding <code>pos</code> are ranked last.
				 * 
				 * @param node
				 *            the node to compute the rank for
				 * @return the rank of the node with respect to the invocation
				 *         offset
				 */
				private int rank(OccurrenceLocation node) {
					int relativeRank = node.getOffset() + node.getLength() - pos;
					if (relativeRank < 0)
						return Integer.MAX_VALUE + relativeRank;
					else
						return relativeRank;
				}
			});

			String name = ""; //$NON-NLS-1$
			for (int i = 0; i < sameNodes.length; i++) {
				OccurrenceLocation elem = sameNodes[i];

				PHPElementLinkedPosition linkedPosition = null;
				String oriName = document.get(elem.getOffset(), elem.getLength());
				int index = oriName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
				if (index > 0) {
					linkedPosition = new PHPElementLinkedPosition(document, elem.getOffset() + (index + 1),
							elem.getLength() - (index + 1), i);
				} else if (index == 0) {
					linkedPosition = new PHPElementLinkedPosition(document, elem.getOffset() + (index + 1),
							elem.getLength() - (index + 1), i);
				} else {
					linkedPosition = new PHPElementLinkedPosition(document, elem.getOffset(), elem.getLength(), i);
				}

				if (i == 0) {
					fNamePosition = linkedPosition;
					name = getName(linkedPosition);
				}

				// make sure the name of the locations are the same.
				if (isSameString(linkedPosition, name)) {
					fLinkedPositionGroup.addPosition(linkedPosition);
				}
			}
			fLinkedModeModel = new LinkedModeModel();
			fLinkedModeModel.addGroup(fLinkedPositionGroup);
			fLinkedModeModel.forceInstall();
			fLinkedModeModel.addLinkingListener(new EditorHighlightingSynchronizer(fEditor));
			fLinkedModeModel.addLinkingListener(new EditorSynchronizer());

			EditorLinkedModeUI ui = new EditorLinkedModeUI(fLinkedModeModel, viewer);
			ui.setExitPosition(viewer, offset, 0, Integer.MAX_VALUE);
			ui.setExitPolicy(new ExitPolicy(document));
			((PHPStructuredTextViewer) viewer).setFireSelectionChanged(false);
			ui.enter();
			String selectedText = ""; //$NON-NLS-1$
			if (fOriginalSelection.y == 0) {
				// place the cursor under the name
				selectedText = name;
			} else {
				selectedText = viewer.getTextWidget().getText(fOriginalSelection.x,
						fOriginalSelection.x + fOriginalSelection.y - 1);
			}

			if (selectedText.startsWith("$")) { //$NON-NLS-1$
				viewer.setSelectedRange(fOriginalSelection.x + 1, fOriginalSelection.y - 1);
			} else {
				viewer.setSelectedRange(fOriginalSelection.x, fOriginalSelection.y); // by
																						// default,full
																						// word
																						// is
																						// selected;
																						// restore
																						// original
																						// selection
			}

			IEditingSupportRegistry registry = viewer;
			registry.register(fFocusEditingSupport);

			openSecondaryPopup();
			// startAnimation();
			fgActiveLinkedMode = this;

		} catch (Exception e) {
			MessageDialog.openError(fEditor.getEditorSite().getShell(), Messages.RenameLinkedMode_1,
					Messages.RenameLinkedMode_4);
		}
	}

	private boolean isSameString(PHPElementLinkedPosition linkedPosition, String name) throws BadLocationException {
		return name.equals(getName(linkedPosition));
	}

	private String getName(PHPElementLinkedPosition linkedPosition) throws BadLocationException {

		IDocument document = fEditor.getDocument();
		String name = document.get(linkedPosition.offset, linkedPosition.length);
		return name;
	}

	void doRename(boolean showPreview) {
		cancel();

		Image image = null;
		Label label = null;

		fShowPreview |= showPreview;
		try {
			SourceViewer sourceViewer = fEditor.getTextViewer();

			Control viewerControl = sourceViewer.getControl();
			if (viewerControl instanceof Composite) {
				Composite composite = (Composite) viewerControl;
				Display display = composite.getDisplay();

				// Flush pending redraw requests:
				while (!display.isDisposed() && display.readAndDispatch()) {
				}

				// Copy editor area:
				GC gc = new GC(composite);
				Point size;
				try {
					size = composite.getSize();
					image = new Image(gc.getDevice(), size.x, size.y);
					gc.copyArea(image, 0, 0);
				} finally {
					gc.dispose();
					gc = null;
				}

				// Persist editor area while executing refactoring:
				label = new Label(composite, SWT.NONE);
				label.setImage(image);
				label.setBounds(0, 0, size.x, size.y);
				label.moveAbove(null);
			}

			String newName = fNamePosition.getContent();
			if (fOriginalName.equals(newName))
				return;
			RenameSupport renameSupport = undoAndCreateRenameSupport(newName);

			if (renameSupport == null)
				return;

			Shell shell = fEditor.getSite().getShell();
			boolean executed;
			if (fShowPreview) { // could have been updated by
				// undoAndCreateRenameSupport(..)
				executed = renameSupport.openDialog(shell);
			} else {
				renameSupport.perform(shell, fEditor.getSite().getWorkbenchWindow());
				executed = true;
			}
			if (executed) {
				restoreFullSelection();
			}

			IFile file = ((IFileEditorInput) fEditor.getEditorInput()).getFile();

			ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);

			Program program = ASTUtils.createProgramFromSource(sourceModule);

			ScriptModelUtil.reconcile(sourceModule);
			fEditor.reconciled(program, true, new NullProgressMonitor());

		} catch (Exception e) {
			// TODO: handle the errors.

		} finally {
			if (label != null)
				label.dispose();
			if (image != null)
				image.dispose();
		}
	}

	public void cancel() {
		if (fLinkedModeModel != null) {
			fLinkedModeModel.exit(ILinkedModeListener.NONE);
		}
		linkedModeLeft();
	}

	private void restoreFullSelection() {
		if (fOriginalSelection.y != 0) {
			int originalOffset = fOriginalSelection.x;
			LinkedPosition[] positions = fLinkedPositionGroup.getPositions();
			for (int i = 0; i < positions.length; i++) {
				LinkedPosition position = positions[i];
				if (!position.isDeleted() && position.includes(originalOffset)) {
					fEditor.getTextViewer().setSelectedRange(position.offset, position.length);
					return;
				}
			}
		}
	}

	private RenameSupport undoAndCreateRenameSupport(String newName) throws CoreException {
		// Assumption: the linked mode model should be shut down by now.

		final ISourceViewer viewer = fEditor.getTextViewer();

		try {
			if (!fOriginalName.equals(newName)) {
				fEditor.getSite().getWorkbenchWindow().run(false, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						if (viewer instanceof ITextViewerExtension6) {
							IUndoManager undoManager = ((ITextViewerExtension6) viewer).getUndoManager();
							if (undoManager != null && undoManager.undoable()) {
								undoManager.undo();
							}
						}
					}
				});
			}
		} catch (InvocationTargetException e) {
			throw new CoreException(
					new Status(IStatus.ERROR, RefactoringUIPlugin.PLUGIN_ID, Messages.RenameLinkedMode_0, e));
		} catch (InterruptedException e) {
			// canceling is OK
			return null;
		} finally {
			IFile file = ((IFileEditorInput) fEditor.getEditorInput()).getFile();
			ISourceModule sourceModule = DLTKCore.createSourceModuleFrom(file);
			try {
				Program program = ASTUtils.createProgramFromSource(sourceModule);
				ScriptModelUtil.reconcile(sourceModule);
				fEditor.reconciled(program, true, new NullProgressMonitor());
			} catch (Exception e) {
			}

		}

		viewer.setSelectedRange(fOriginalSelection.x, fOriginalSelection.y);

		final int elementType = PhpElementConciliator.concile(selectedNode);
		RenameSupport renameSupport = RenameSupport.create(
				selectedNode.getProgramRoot().getSourceModule().getResource(), elementType, selectedNode, newName,
				RenameSupport.UPDATE_REFERENCES);
		return renameSupport;
	}

	public void startFullDialog() {
		cancel();

		try {
			String newName = fNamePosition.getContent();
			RenameSupport renameSupport = undoAndCreateRenameSupport(newName);
			if (renameSupport != null)
				renameSupport.openDialog(fEditor.getSite().getShell());
		} catch (CoreException e) {
		} catch (BadLocationException e) {
		}
	}

	private void linkedModeLeft() {
		fgActiveLinkedMode = null;
		if (fInfoPopup != null) {
			fInfoPopup.close();
		}

		TextViewer viewer = fEditor.getTextViewer();
		viewer.unregister(fFocusEditingSupport);

		((PHPStructuredTextViewer) viewer).setFireSelectionChanged(true);
	}

	private void openSecondaryPopup() {
		fInfoPopup = new RenameInformationPopup(fEditor, this);
		fInfoPopup.open();
	}

	public boolean isCaretInLinkedPosition() {
		return getCurrentLinkedPosition() != null;
	}

	public LinkedPosition getCurrentLinkedPosition() {
		Point selection = fEditor.getTextViewer().getSelectedRange();
		int start = selection.x;
		int end = start + selection.y;
		LinkedPosition[] positions = fLinkedPositionGroup.getPositions();
		for (int i = 0; i < positions.length; i++) {
			LinkedPosition position = positions[i];
			if (position.includes(start) && position.includes(end))
				return position;
		}
		return null;
	}

	public boolean isEnabled() {
		try {
			String newName = fNamePosition.getContent();
			if (fOriginalName.equals(newName))
				return false;
			return PHPConventionsUtil.validateIdentifier(newName);
		} catch (BadLocationException e) {
			return false;
		}

	}

	public boolean isOriginalName() {
		try {
			String newName = fNamePosition.getContent();
			return fOriginalName.equals(newName);
		} catch (BadLocationException e) {
			return false;
		}
	}

	public String getCurrentElementName(ASTNode identifier) {
		if (identifier instanceof Variable) {
			Identifier id = (Identifier) ((Variable) identifier).getName();
			return id.getName();
		}
		if (identifier instanceof Identifier) {
			return ((Identifier) identifier).getName();
		}

		if (identifier.getType() == ASTNode.SCALAR) {
			final String stringValue = ((Scalar) identifier).getStringValue();
			if (((Scalar) identifier).getStringValue().charAt(0) == '"') {
				return stringValue.substring(1, stringValue.length() - 1);
			} else {
				return stringValue;
			}

		}
		return identifier.toString();
	}

}
