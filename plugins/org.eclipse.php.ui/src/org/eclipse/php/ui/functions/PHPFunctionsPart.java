/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.functions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.Logger;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.util.MultiElementSelection;
import org.eclipse.php.internal.ui.util.TreePath;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.explorer.ExplorerMessages;
import org.eclipse.php.ui.explorer.IMultiElementTreeContentProvider;
import org.eclipse.php.ui.explorer.PHPTreeViewer;
import org.eclipse.php.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.ui.util.DecoratingPHPLabelProvider;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.php.ui.util.PHPElementImageProvider;
import org.eclipse.php.ui.util.PHPElementLabels;
import org.eclipse.php.ui.util.PHPManualFactory;
import org.eclipse.php.ui.util.StatusBarUpdater;
import org.eclipse.php.ui.workingset.FunctionsViewGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPFunctionsPart extends ViewPart implements IMenuListener, IPartListener {

	private PHPTreeViewer fViewer;
	private PHPFunctionsContentProvider fContentProvider;
	private PHPFunctionsLabelProvider fLabelProvider;

	private Menu fContextMenu;
	private String fWorkingSetName;

	private Action showFunctionHelpAction;
	private FunctionsViewGroup actionGroup;

	private boolean shouldRefresh = true;

	public PHPFunctionsPart() {
	}

	public void createPartControl(Composite parent) {
		fViewer = createViewer(parent);
		fViewer.setUseHashlookup(true);
		setProviders();

		setUpPopupMenu();
		actionGroup = new FunctionsViewGroup(this);

		addDoubleClickListener();
		addMouseTrackListener();
		getSite().getPage().addPartListener(this);

		IStatusLineManager slManager = getViewSite().getActionBars().getStatusLineManager();
		fViewer.addSelectionChangedListener(new StatusBarUpdater(slManager));

		fViewer.getTree().addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				Display.getCurrent().asyncExec(new Runnable() {

					public void run() {
						if (!fViewer.getTree().isDisposed() && (shouldRefresh || fViewer.getTree().getItems().length == 0)) {
							fViewer.refresh();
							shouldRefresh = false;
						}
					}

				});
			}

			public void focusLost(FocusEvent e) {
			}

		});
		updateTitle();
	}

	private void setUpPopupMenu() {
		showFunctionHelpAction = new ShowFunctionHelpAction();

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		fContextMenu = menuMgr.createContextMenu(fViewer.getTree());
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				ISelection selection = fViewer.getSelection();
				if (!selection.isEmpty()) {
					IStructuredSelection s = (IStructuredSelection) selection;
					if (s.getFirstElement() instanceof PHPFunctionData) {
						mgr.add(showFunctionHelpAction);
					}
				}
			}
		});
		fViewer.getTree().setMenu(fContextMenu);
		IWorkbenchPartSite site = getSite();
		site.registerContextMenu(menuMgr, fViewer);
		site.setSelectionProvider(fViewer);
	}

	private void updateInputForCurrentEditor(final IEditorPart editorPart) {
		actionGroup.handleUpdateInput(editorPart);
	}

	private PHPTreeViewer createViewer(Composite composite) {
		return new PHPTreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	private void addMouseTrackListener() {
		final Tree tree = fViewer.getTree();
		tree.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseHover(MouseEvent e) {
				TreeItem item = tree.getItem(new Point(e.x, e.y));
				if (item != null) {
					Object o = item.getData();
					if (o instanceof PHPCodeData) {
						tree.setToolTipText(fLabelProvider.getTooltipText(o));
					}
				}
			}

		});
	}

	private void addDoubleClickListener() {
		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IEditorPart editor = getViewSite().getPage().getActiveEditor();
				StructuredSelection selection = (StructuredSelection) fViewer.getSelection();
				if (editor != null && editor instanceof ITextEditor && selection != null && !selection.isEmpty()) {
					if (selection.getFirstElement() instanceof PHPCodeData) {
						PHPCodeData codeData = (PHPCodeData) selection.getFirstElement();
						ITextEditor textEditor = (ITextEditor) editor;
						int caretPosition = ((ITextSelection) textEditor.getSelectionProvider().getSelection()).getOffset();
						IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
						try {
							document.replace(caretPosition, 0, codeData.getName());
						} catch (BadLocationException e) {
							Logger.logException(e);
						}
						textEditor.setFocus();
						textEditor.getSelectionProvider().setSelection(new TextSelection(document, caretPosition + codeData.getName().length(), 0));
						// TODO: 
						// show codeData completion/description/param list
						//                        ContentAssistant ca = new ContentAssistant();
						//                        ca.setContentAssistProcessor(new PHPContentAssistProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
						//                        ca.install(((PHPStructuredEditor) editor).getTextViewer());
						//                        ca.setAutoActivationDelay(0);
						//                        ca.showPossibleCompletions();

					}
				}
			}
		});
	}

	public void setFocus() {
		fViewer.getTree().setFocus();
	}

	private void setProviders() {
		fContentProvider = createContentProvider();
		fViewer.setContentProvider(fContentProvider);

		fLabelProvider = createLabelProvider();
		fViewer.setLabelProvider(new DecoratingPHPLabelProvider(fLabelProvider, false));
	}

	void projectStateChanged(Object root) {
		Control ctrl = fViewer.getControl();
		if (ctrl != null && !ctrl.isDisposed()) {
			fViewer.refresh(root, true);
			// trigger a syntetic selection change so that action refresh their
			// enable state.
			fViewer.setSelection(fViewer.getSelection());
		}
	}

	public PHPFunctionsContentProvider createContentProvider() {
		//		IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		//		boolean showCUChildren = store.getBoolean(PreferenceConstants.SHOW_CU_CHILDREN);
		return new PHPFunctionsContentProvider();
	}

	private PHPFunctionsLabelProvider createLabelProvider() {
		return new PHPFunctionsLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | PHPElementLabels.M_PARAMETER_NAMES, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | PHPElementImageProvider.SMALL_ICONS | PHPElementImageProvider.OVERLAY_ICONS, fContentProvider);
	}

	public void dispose() {
		getSite().getPage().removePartListener(this);
		if (fContextMenu != null && !fContextMenu.isDisposed()) {
			fContextMenu.dispose();
		}
		super.dispose();
	}

	public void partActivated(IWorkbenchPart part) {
		if (part.equals(this)) {
			Display.getCurrent().asyncExec(new Runnable() {
				public void run() {
					setFocus();
				}

			});
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
		if (part.equals(this) && getViewer().getInput() == null) {
			updateInputForCurrentEditor(null);
			setFocus();
		}
		if (PHPFunctionsPart.this.getViewer().getTree().getVisible() && part instanceof PHPStructuredEditor) {
			updateInputForCurrentEditor((IEditorPart) part);
		}

	}

	public void menuAboutToShow(IMenuManager menu) {
		PHPUiPlugin.createStandardGroups(menu);
	}

	void editorActivated(IEditorPart editor) {
	}

	public TreeViewer getViewer() {
		return fViewer;
	}

	public void collapseAll() {
		try {
			fViewer.getControl().setRedraw(false);
			fViewer.collapseToLevel(getViewPartInput(), AbstractTreeViewer.ALL_LEVELS);
		} finally {
			fViewer.getControl().setRedraw(true);
		}
	}

	public Object getViewPartInput() {
		if (fViewer != null) {
			return fViewer.getInput();
		}
		return null;
	}

	String getFrameName(Object element) {
		if (element instanceof PHPCodeData) {
			return ((PHPCodeData) element).getName();
		} else {
			return fLabelProvider.getText(element);
		}
	}

	String getToolTipText(Object element) {
		String result;
		if (!(element instanceof IResource)) {
			if (element instanceof PHPWorkspaceModelManager) {
				result = ExplorerMessages.PHPExplorerPart_workspace;
			} else if (element instanceof PHPCodeData) {
				result = PHPElementLabels.getTextLabel(element, AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS);
			} else {
				result = fLabelProvider.getText(element);
			}
		} else {
			IPath path = ((IResource) element).getFullPath();
			if (path.isRoot()) {
				result = ExplorerMessages.PHPExplorer_title;
			} else {
				result = path.makeRelative().toString();
			}
		}

		if (fWorkingSetName == null)
			return result;

		String wsstr = MessageFormat.format(ExplorerMessages.PHPExplorer_toolTip, new String[] { fWorkingSetName });
		if (result.length() == 0)
			return wsstr;
		return MessageFormat.format(ExplorerMessages.PHPExplorer_toolTip2, new String[] { result, fWorkingSetName });
	}

	void updateTitle() {
		Object input = fViewer.getInput();
		if (input == null || (input instanceof PHPWorkspaceModelManager)) {
			setContentDescription(""); //$NON-NLS-1$
			setTitleToolTip(""); //$NON-NLS-1$
		} else {
			String inputText = PHPElementLabels.getTextLabel(input, AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS);
			setContentDescription(inputText);
			setTitleToolTip(getToolTipText(input));
		}
	}

	class ShowFunctionHelpAction extends Action {
		private final Pattern METHOD_PATTERN = Pattern.compile("([A-Z])");

		public ShowFunctionHelpAction() {
			super("Open Manual");
		}

		public void run() {
			ISelection selection = fViewer.getSelection();
			if (!selection.isEmpty()) {
				IStructuredSelection s = (IStructuredSelection) selection;
				if (s.getFirstElement() instanceof PHPFunctionData) {
					PHPFunctionData funcData = (PHPFunctionData) s.getFirstElement();
					PHPCodeData container = funcData.getContainer();
					String functionName = "";
					if (container instanceof PHPClassData) {
						Matcher m = METHOD_PATTERN.matcher(funcData.getName());
						functionName = container.getName() + "-" + m.replaceAll("-$1");
					} else {
						functionName = funcData.getName();
					}
					PHPManualFactory.getManual().showFunctionHelp(functionName);
				}
			}
		}
	}
}
