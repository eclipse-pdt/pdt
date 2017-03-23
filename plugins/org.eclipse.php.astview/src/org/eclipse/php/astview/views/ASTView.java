/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/

package org.eclipse.php.astview.views;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filebuffers.IFileBufferListener;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProblemRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.php.astview.ASTViewImages;
import org.eclipse.php.astview.ASTViewPlugin;
import org.eclipse.php.astview.EditorUtility;
import org.eclipse.php.astview.NodeFinder;
import org.eclipse.php.astview.TreeInfoCollector;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class ASTView extends ViewPart implements IShowInSource {

	private class ASTViewSelectionProvider implements ISelectionProvider {
		ListenerList fListeners = new ListenerList(ListenerList.IDENTITY);

		@Override
		public void addSelectionChangedListener(ISelectionChangedListener listener) {
			fListeners.add(listener);
		}

		@Override
		public ISelection getSelection() {
			IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
			ArrayList externalSelection = new ArrayList();
			for (Iterator iter = selection.iterator(); iter.hasNext();) {
				Object unwrapped = ASTView.unwrapAttribute(iter.next());
				if (unwrapped != null)
					externalSelection.add(unwrapped);
			}
			return new StructuredSelection(externalSelection);
		}

		@Override
		public void removeSelectionChangedListener(ISelectionChangedListener listener) {
			fListeners.remove(listener);
		}

		@Override
		public void setSelection(ISelection selection) {
			// not supported
		}
	}

	private class ASTInputKindAction extends Action {
		public static final int USE_PARSER = 1;
		public static final int USE_RECONCILE = 2;
		public static final int USE_CACHE = 3;
		public static final int USE_FOCAL = 4;

		private int fInputKind;

		public ASTInputKindAction(String label, int inputKind) {
			super(label, AS_RADIO_BUTTON);
			fInputKind = inputKind;
			if (inputKind == getCurrentInputKind()) {
				setChecked(true);
			}
		}

		public int getInputKind() {
			return fInputKind;
		}

		@Override
		public void run() {
			setASTInputType(fInputKind);
		}
	}

	private static class ListenerMix implements ISelectionListener, IFileBufferListener, IDocumentListener,
			ISelectionChangedListener, IDoubleClickListener, IPartListener2 {

		private boolean fASTViewVisible = true;
		private ASTView fView;

		public ListenerMix(ASTView view) {
			fView = view;
		}

		public void dispose() {
			fView = null;
		}

		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (fASTViewVisible) {
				fView.handleEditorPostSelectionChanged(part, selection);
			}
		}

		@Override
		public void bufferCreated(IFileBuffer buffer) {
			// not interesting
		}

		@Override
		public void bufferDisposed(IFileBuffer buffer) {
			if (buffer instanceof ITextFileBuffer) {
				fView.handleDocumentDisposed(((ITextFileBuffer) buffer).getDocument());
			}
		}

		@Override
		public void bufferContentAboutToBeReplaced(IFileBuffer buffer) {
			// not interesting
		}

		@Override
		public void bufferContentReplaced(IFileBuffer buffer) {
			// not interesting
		}

		@Override
		public void stateChanging(IFileBuffer buffer) {
			// not interesting
		}

		@Override
		public void dirtyStateChanged(IFileBuffer buffer, boolean isDirty) {
			// not interesting
		}

		@Override
		public void stateValidationChanged(IFileBuffer buffer, boolean isStateValidated) {
			// not interesting
		}

		@Override
		public void underlyingFileMoved(IFileBuffer buffer, IPath path) {
			// not interesting
		}

		@Override
		public void underlyingFileDeleted(IFileBuffer buffer) {
			// not interesting
		}

		@Override
		public void stateChangeFailed(IFileBuffer buffer) {
			// not interesting
		}

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			// not interesting
		}

		@Override
		public void documentChanged(DocumentEvent event) {
			fView.handleDocumentChanged(event.getDocument());
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			fView.handleSelectionChanged(event.getSelection());
		}

		@Override
		public void doubleClick(DoubleClickEvent event) {
			fView.handleDoubleClick(event);
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part == fView) {
				fASTViewVisible = false;
			}
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			IWorkbenchPart part = partRef.getPart(false);
			if (part == fView) {
				fASTViewVisible = true;
			}
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			// not interesting
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
			// not interesting
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			fView.notifyWorkbenchPartClosed(partRef);
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
			// not interesting
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			// not interesting
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
			// not interesting
		}
	}

	private final static String SETTINGS_LINK_WITH_EDITOR = "link_with_editor"; //$NON-NLS-1$
	private final static String SETTINGS_INPUT_KIND = "input_kind"; //$NON-NLS-1$
	private final static String SETTINGS_NO_BINDINGS = "create_bindings"; //$NON-NLS-1$
	private final static String SETTINGS_NO_STATEMENTS_RECOVERY = "no_statements_recovery"; //$NON-NLS-1$
	private final static String SETTINGS_NO_BINDINGS_RECOVERY = "no_bindings_recovery"; //$NON-NLS-1$
	private final static String SETTINGS_SHOW_NON_RELEVANT = "show_non_relevant";//$NON-NLS-1$

	public static final String LINK_WITH_EDITOR_COMMAND_ID = IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR; 

	private SashForm fSash;
	private TreeViewer fViewer;
	private ASTViewLabelProvider fASTLabelProvider;
	private TreeViewer fTray;

	private DrillDownAdapter fDrillDownAdapter;
	private Action fFocusAction;
	private Action fRefreshAction;
	private Action fCreateBindingsAction;
	private Action fStatementsRecoveryAction;
	private Action fBindingsRecoveryAction;
	private Action fFilterNonRelevantAction;
	private Action fFindDeclaringNodeAction;
	private Action fParseBindingFromKeyAction;
	private Action fParseBindingFromElementAction;
	private Action fCollapseAction;
	private Action fExpandAction;
	private Action fClearAction;
	private TreeCopyAction fCopyAction;
	private Action fDoubleClickAction;
	private Action fLinkWithEditor;
	private Action fDeleteAction;

	private PHPVersion fPHPVersion;

	private ASTInputKindAction[] fASTInputKindActions;
	private int fCurrentInputKind;

	private ITextEditor fEditor;
	private ISourceModule fSourceRoot;
	private Program fRoot;
	private IDocument fCurrentDocument;
	private ArrayList fTrayRoots;

	private boolean fDoLinkWithEditor;
	private boolean fCreateBindings;
	private NonRelevantFilter fNonRelevantFilter;
	private boolean fStatementsRecovery;
	private boolean fBindingsRecovery;

	private Object fPreviousDouble;

	private ListenerMix fSuperListener;
	private ISelectionChangedListener fTrayUpdater = null; // XXX: never set

	private IDialogSettings fDialogSettings;

	public ASTView() {
		fSuperListener = null;
		fDialogSettings = ASTViewPlugin.getDefault().getDialogSettings();
		fDoLinkWithEditor = fDialogSettings.getBoolean(SETTINGS_LINK_WITH_EDITOR);
		try {
			fCurrentInputKind = fDialogSettings.getInt(SETTINGS_INPUT_KIND);
		} catch (NumberFormatException e) {
			fCurrentInputKind = ASTInputKindAction.USE_PARSER;
		}
		fCreateBindings = !fDialogSettings.getBoolean(SETTINGS_NO_BINDINGS); // inverse
																				// so
																				// that
																				// default
																				// is
																				// to
																				// create
																				// bindings
		fStatementsRecovery = !fDialogSettings.getBoolean(SETTINGS_NO_STATEMENTS_RECOVERY); // inverse
																							// so
																							// that
																							// default
																							// is
																							// use
																							// recovery
		fBindingsRecovery = !fDialogSettings.getBoolean(SETTINGS_NO_BINDINGS_RECOVERY); // inverse
																						// so
																						// that
																						// default
																						// is
																						// use
																						// recovery
		fPHPVersion = PHPVersion.getLatestVersion();
		fNonRelevantFilter = new NonRelevantFilter();
		fNonRelevantFilter.setShowNonRelevant(fDialogSettings.getBoolean(SETTINGS_SHOW_NON_RELEVANT));
	}

	final void notifyWorkbenchPartClosed(IWorkbenchPartReference partRef) {
		if (fEditor != null && fEditor.equals(partRef.getPart(false))) {
			try {
				setInput(null);
			} catch (CoreException e) {
				// ignore
			}
		}
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.setSite(site);
		if (fSuperListener == null) {
			fSuperListener = new ListenerMix(this);

			ISelectionService service = site.getWorkbenchWindow().getSelectionService();
			service.addPostSelectionListener(fSuperListener);
			site.getPage().addPartListener(fSuperListener);
			FileBuffers.getTextFileBufferManager().addFileBufferListener(fSuperListener);
		}
	}

	public PHPVersion getCurrentPHPVersion() {
		return fPHPVersion;
	}

	public int getCurrentInputKind() {
		return fCurrentInputKind;
	}

	public void setInput(ITextEditor editor) throws CoreException {
		if (fEditor != null) {
			uninstallModificationListener();
		}

		fEditor = null;
		fRoot = null;

		if (editor != null) {
			ISourceModule typeRoot = EditorUtility.getPhpInput(editor);
			if (typeRoot == null) {
				throw new CoreException(getErrorStatus("Editor not showing a CU or class file", null)); //$NON-NLS-1$
			}
			fSourceRoot = typeRoot;
			PHPVersion astLevel = getInitialASTLevel(typeRoot);

			ISelection selection = editor.getSelectionProvider().getSelection();
			if (selection instanceof ITextSelection) {
				ITextSelection textSelection = (ITextSelection) selection;
				fRoot = internalSetInput(typeRoot, textSelection.getOffset(), textSelection.getLength(), astLevel);
				fEditor = editor;
				setASTLevel(astLevel, false);
			}
			installModificationListener();
		}

	}

	private PHPVersion getInitialASTLevel(ISourceModule typeRoot) {
		return ProjectOptions.getPHPVersion(typeRoot.getScriptProject().getProject());
	}

	private Program internalSetInput(ISourceModule input, int offset, int length, PHPVersion phpVersion)
			throws CoreException {
		if (input.getBuffer() == null) {
			throw new CoreException(getErrorStatus("Input has no buffer", null)); //$NON-NLS-1$
		}

		try {
			Program root = createAST(input, phpVersion, offset);
			resetView(root);
			if (root == null) {
				setContentDescription("AST could not be created."); //$NON-NLS-1$
				return null;
			}
			ASTNode node = NodeFinder.perform(root, offset, length);
			if (node != null) {
				fViewer.getTree().setRedraw(false);
				try {
					fASTLabelProvider.setSelectedRange(node.getStart(), node.getLength());
					fViewer.setSelection(new StructuredSelection(node), true);
				} finally {
					fViewer.getTree().setRedraw(true);
				}
			}
			return root;

		} catch (RuntimeException e) {
			throw new CoreException(getErrorStatus("Could not create AST:\n" + e.getMessage(), e)); //$NON-NLS-1$
		} catch (Exception e) {
			throw new CoreException(getErrorStatus("Could not create AST:\n" + e.getMessage(), e)); //$NON-NLS-1$
		}
	}

	private void clearView() {
		resetView(null);
		setContentDescription("Open a PHP editor and press the 'Show AST of active editor' toolbar button"); //$NON-NLS-1$
	}

	private void resetView(Program root) {
		fViewer.setInput(root);
		fViewer.getTree().setEnabled(root != null);
		fSash.setMaximizedControl(fViewer.getTree());
		fTrayRoots = new ArrayList();
		setASTUptoDate(root != null);
		fClearAction.setEnabled(root != null);
		fFindDeclaringNodeAction.setEnabled(root != null);
		fPreviousDouble = null; // avoid leaking AST
	}

	private Program createAST(ISourceModule input, PHPVersion phpVersion, int offset) throws Exception {
		long startTime;
		long endTime;
		Program root = null;

		if ((getCurrentInputKind() == ASTInputKindAction.USE_RECONCILE)) {
			final IProblemRequestor problemRequestor = new IProblemRequestor() { // strange:
																					// don't
																					// get
																					// bindings
																					// when
																					// supplying
																					// null
																					// as
																					// problemRequestor
				@Override
				public void acceptProblem(IProblem problem) {
					/* not interested */}

				@Override
				public void beginReporting() {
					/* not interested */}

				@Override
				public void endReporting() {
					/* not interested */}

				@Override
				public boolean isActive() {
					return true;
				}
			};
			WorkingCopyOwner workingCopyOwner = new WorkingCopyOwner() {
				public IProblemRequestor getProblemRequestor(ISourceModule workingCopy) {
					return problemRequestor;
				}
			};
			ISourceModule wc = input.getWorkingCopy(workingCopyOwner, null, new NullProgressMonitor());
			try {
				startTime = System.currentTimeMillis();
				wc.reconcile(true, wc.getOwner(), new NullProgressMonitor()); // getCurrentASTLevel(),
																				// null,
																				// null);

				root = SharedASTProvider.getAST(wc, SharedASTProvider.WAIT_YES, null);
				endTime = System.currentTimeMillis();

			} finally {
				wc.discardWorkingCopy();
			}

		} else if (input instanceof ISourceModule && (getCurrentInputKind() == ASTInputKindAction.USE_CACHE)) {
			ISourceModule cu = input;
			startTime = System.currentTimeMillis();
			root = SharedASTProvider.getAST(cu, SharedASTProvider.WAIT_YES, null);
			endTime = System.currentTimeMillis();

		} else {
			ISourceModule sm = input;
			StringReader st = new StringReader(sm.getBuffer().getContents());
			ASTParser parser = ASTParser.newParser(st, phpVersion, false, sm);
			startTime = System.currentTimeMillis();
			root = parser.createAST(null);
			endTime = System.currentTimeMillis();
		}
		if (root != null) {
			updateContentDescription(input, root, endTime - startTime);
		}
		return root;
	}

	protected void refreshASTSettingsActions() {
		boolean enabled;
		switch (getCurrentInputKind()) {
		case ASTInputKindAction.USE_RECONCILE:
		case ASTInputKindAction.USE_CACHE:
			enabled = false;
			break;
		default:
			enabled = true;
			break;
		}
		fBindingsRecoveryAction.setEnabled(enabled);
		fCreateBindingsAction.setEnabled(enabled);
		fStatementsRecoveryAction.setEnabled(enabled);
	}

	private void updateContentDescription(IModelElement element, Program root, long time) {
		String version = root.getAST().apiLevel().getAlias();
		if (getCurrentInputKind() == ASTInputKindAction.USE_RECONCILE) {
			version += ", from reconciler"; //$NON-NLS-1$
		} else if (getCurrentInputKind() == ASTInputKindAction.USE_CACHE) {
			version += ", from ASTProvider"; //$NON-NLS-1$
		} else if (getCurrentInputKind() == ASTInputKindAction.USE_FOCAL) {
			version += ", using focal position"; //$NON-NLS-1$
		}
		TreeInfoCollector collector = new TreeInfoCollector(root);

		String msg = "{0} ({1}).  Creation time: {2,number} ms.  Size: {3,number} nodes, {4,number} bytes (AST nodes only)."; //$NON-NLS-1$
		Object[] args = { element.getElementName(), version, time, collector.getNumberOfNodes(), collector.getSize() };
		setContentDescription(MessageFormat.format(msg, args));

	}

	@Override
	public void dispose() {
		if (fSuperListener != null) {
			if (fEditor != null) {
				uninstallModificationListener();
			}
			ISelectionService service = getSite().getWorkbenchWindow().getSelectionService();
			service.removePostSelectionListener(fSuperListener);
			getSite().getPage().removePartListener(fSuperListener);
			FileBuffers.getTextFileBufferManager().removeFileBufferListener(fSuperListener);
			fSuperListener.dispose(); // removes reference to view
			fSuperListener = null;
		}
		if (fTrayUpdater != null) {
			fViewer.removePostSelectionChangedListener(fTrayUpdater);
			fTray.removePostSelectionChangedListener(fTrayUpdater);
			fTrayUpdater = null;
		}
		super.dispose();
	}

	private IStatus getErrorStatus(String message, Throwable th) {
		return new Status(IStatus.ERROR, ASTViewPlugin.getPluginId(), IStatus.ERROR, message, th);
	}

	@Override
	public void createPartControl(Composite parent) {
		fSash = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);
		fViewer = new TreeViewer(fSash, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		fDrillDownAdapter = new DrillDownAdapter(fViewer);
		fViewer.setContentProvider(new ASTViewContentProvider());
		fASTLabelProvider = new ASTViewLabelProvider();
		fViewer.setLabelProvider(fASTLabelProvider);
		fViewer.addSelectionChangedListener(fSuperListener);
		fViewer.addDoubleClickListener(fSuperListener);
		fViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (!fCreateBindings && element instanceof Binding)
					return false;
				return true;
			}
		});
		fViewer.addFilter(fNonRelevantFilter);

		ViewForm trayForm = new ViewForm(fSash, SWT.NONE);
		Label label = new Label(trayForm, SWT.NONE);
		label.setText(" Comparison Tray (* = selection in the upper tree):"); //$NON-NLS-1$
		trayForm.setTopLeft(label);

		fTray = new TreeViewer(trayForm, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		trayForm.setContent(fTray.getTree());

		makeActions();
		hookContextMenu();
		hookTrayContextMenu();
		contributeToActionBars();
		getSite().setSelectionProvider(new ASTViewSelectionProvider());

		try {
			IEditorPart part = EditorUtility.getActiveEditor();
			if (part instanceof ITextEditor) {
				setInput((ITextEditor) part);
			}
		} catch (CoreException e) {
			// ignore
		}
		if (fSourceRoot == null) {
			clearView();
		} else {
			setASTUptoDate(fSourceRoot != null);
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				ASTView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(fViewer.getControl());
		fViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, fViewer);
	}

	private void hookTrayContextMenu() {
		MenuManager menuMgr = new MenuManager("#TrayPopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(fCopyAction);
				manager.add(fDeleteAction);
				manager.add(new Separator());
				manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		Menu menu = menuMgr.createContextMenu(fTray.getControl());
		fTray.getControl().setMenu(menu);
		getSite().registerContextMenu("#TrayPopupMenu", menuMgr, fTray); //$NON-NLS-1$
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
		bars.setGlobalActionHandler(ActionFactory.COPY.getId(), fCopyAction);
		bars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), fFocusAction);
		bars.setGlobalActionHandler(ActionFactory.DELETE.getId(), fDeleteAction);

		IHandlerService handlerService = (IHandlerService) getViewSite().getService(IHandlerService.class);
		handlerService.activateHandler(LINK_WITH_EDITOR_COMMAND_ID, new ActionHandler(fLinkWithEditor));
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(fCreateBindingsAction);
		manager.add(fStatementsRecoveryAction);
		manager.add(fBindingsRecoveryAction);
		manager.add(fFilterNonRelevantAction);
		manager.add(new Separator());
		for (ASTInputKindAction fASTInputKindAction : fASTInputKindActions) {
			manager.add(fASTInputKindAction);
		}
		manager.add(new Separator());
		manager.add(fFindDeclaringNodeAction);
		manager.add(fParseBindingFromKeyAction);
		manager.add(fParseBindingFromElementAction);
		manager.add(new Separator());
		manager.add(fLinkWithEditor);
	}

	protected void fillContextMenu(IMenuManager manager) {
		manager.add(fFocusAction);
		manager.add(fRefreshAction);
		manager.add(fClearAction);
		manager.add(fCollapseAction);
		manager.add(fExpandAction);
		manager.add(new Separator());
		manager.add(fCopyAction);
		manager.add(new Separator());

		fDrillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(fFocusAction);
		manager.add(fRefreshAction);
		manager.add(fClearAction);
		manager.add(new Separator());
		fDrillDownAdapter.addNavigationActions(manager);
		manager.add(new Separator());
		manager.add(fExpandAction);
		manager.add(fCollapseAction);
		manager.add(fLinkWithEditor);
	}

	private void setASTUptoDate(boolean isuptoDate) {
		fRefreshAction.setEnabled(!isuptoDate && fSourceRoot != null);
	}

	private void makeActions() {
		fRefreshAction = new Action() {
			@Override
			public void run() {
				performRefresh();
			}
		};
		fRefreshAction.setText("&Refresh AST"); //$NON-NLS-1$
		fRefreshAction.setToolTipText("Refresh AST"); //$NON-NLS-1$
		fRefreshAction.setEnabled(false);
		ASTViewImages.setImageDescriptors(fRefreshAction, ASTViewImages.REFRESH);

		fClearAction = new Action() {
			@Override
			public void run() {
				performClear();
			}
		};
		fClearAction.setText("&Clear AST"); //$NON-NLS-1$
		fClearAction.setToolTipText("Clear AST and release memory"); //$NON-NLS-1$
		fClearAction.setEnabled(false);
		ASTViewImages.setImageDescriptors(fClearAction, ASTViewImages.CLEAR);

		fASTInputKindActions = new ASTInputKindAction[] {
				new ASTInputKindAction("Use ASTParser.&createAST", ASTInputKindAction.USE_PARSER), //$NON-NLS-1$
				new ASTInputKindAction("Use ASTParser with &focal position", ASTInputKindAction.USE_FOCAL), //$NON-NLS-1$
				new ASTInputKindAction("Use IProgram.&reconcile", ASTInputKindAction.USE_RECONCILE), //$NON-NLS-1$
				new ASTInputKindAction("Use SharedASTProvider.&getAST", ASTInputKindAction.USE_CACHE) //$NON-NLS-1$
		};

		fCreateBindingsAction = new Action("&Create Bindings", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				performCreateBindings();
			}
		};
		fCreateBindingsAction.setChecked(fCreateBindings);
		fCreateBindingsAction.setToolTipText("Create Bindings"); //$NON-NLS-1$
		fCreateBindingsAction.setEnabled(true);

		fStatementsRecoveryAction = new Action("&Statements Recovery", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				performStatementsRecovery();
			}
		};
		fStatementsRecoveryAction.setChecked(fStatementsRecovery);
		fStatementsRecoveryAction.setEnabled(true);

		fBindingsRecoveryAction = new Action("&Bindings Recovery", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				performBindingsRecovery();
			}
		};
		fBindingsRecoveryAction.setChecked(fBindingsRecovery);
		fBindingsRecoveryAction.setEnabled(true);

		fFilterNonRelevantAction = new Action("&Hide Non-Relevant Attributes", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			@Override
			public void run() {
				performFilterNonRelevant();
			}
		};
		fFilterNonRelevantAction.setChecked(!fNonRelevantFilter.isShowNonRelevant());
		fFilterNonRelevantAction.setToolTipText("Hide non-relevant binding attributes"); //$NON-NLS-1$
		fFilterNonRelevantAction.setEnabled(true);

		fFindDeclaringNodeAction = new Action("Find &Declaring Node...", IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			@Override
			public void run() {
				performFindDeclaringNode();
			}
		};
		fFindDeclaringNodeAction.setToolTipText("Find Declaring Node..."); //$NON-NLS-1$
		fFindDeclaringNodeAction.setEnabled(false);

		fParseBindingFromElementAction = new Action("&Parse Binding from &Element Handle...", IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			@Override
			public void run() {
				performParseBindingFromElement();
			}
		};
		fParseBindingFromElementAction.setToolTipText("Parse Binding from Element Handle..."); //$NON-NLS-1$
		fParseBindingFromElementAction.setEnabled(true);

		fParseBindingFromKeyAction = new Action("Parse Binding from &Key...", IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			@Override
			public void run() {
				performParseBindingFromKey();
			}
		};
		fParseBindingFromKeyAction.setToolTipText("Parse Binding from Key..."); //$NON-NLS-1$
		fParseBindingFromKeyAction.setEnabled(true);

		fFocusAction = new Action() {
			@Override
			public void run() {
				performSetFocus();
			}
		};
		fFocusAction.setText("&Show AST of active editor"); //$NON-NLS-1$
		fFocusAction.setToolTipText("Show AST of active editor"); //$NON-NLS-1$
		fFocusAction.setActionDefinitionId("org.eclipse.ui.file.refresh"); //$NON-NLS-1$
		ASTViewImages.setImageDescriptors(fFocusAction, ASTViewImages.SETFOCUS);

		fCollapseAction = new Action() {
			@Override
			public void run() {
				performCollapse();
			}
		};
		fCollapseAction.setText("C&ollapse"); //$NON-NLS-1$
		fCollapseAction.setToolTipText("Collapse Selected Node"); //$NON-NLS-1$
		fCollapseAction.setEnabled(false);
		ASTViewImages.setImageDescriptors(fCollapseAction, ASTViewImages.COLLAPSE);

		fExpandAction = new Action() {
			@Override
			public void run() {
				performExpand();
			}
		};
		fExpandAction.setText("E&xpand"); //$NON-NLS-1$
		fExpandAction.setToolTipText("Expand Selected Node"); //$NON-NLS-1$
		fExpandAction.setEnabled(false);
		ASTViewImages.setImageDescriptors(fExpandAction, ASTViewImages.EXPAND);

		fCopyAction = new TreeCopyAction(new Tree[] { fViewer.getTree(), fTray.getTree() });

		fDoubleClickAction = new Action() {
			@Override
			public void run() {
				performDoubleClick();
			}
		};

		fLinkWithEditor = new Action() {
			@Override
			public void run() {
				performLinkWithEditor();
			}
		};
		fLinkWithEditor.setChecked(fDoLinkWithEditor);
		fLinkWithEditor.setText("&Link with Editor"); //$NON-NLS-1$
		fLinkWithEditor.setToolTipText("Link With Editor"); //$NON-NLS-1$
		fLinkWithEditor.setActionDefinitionId(LINK_WITH_EDITOR_COMMAND_ID);
		ASTViewImages.setImageDescriptors(fLinkWithEditor, ASTViewImages.LINK_WITH_EDITOR);

		fDeleteAction = new Action() {
			@Override
			public void run() {
				performDelete();
			}
		};
		fDeleteAction.setText("&Delete"); //$NON-NLS-1$
		fDeleteAction.setToolTipText("Delete Binding from Tray"); //$NON-NLS-1$
		fDeleteAction.setEnabled(false);
		fDeleteAction.setImageDescriptor(ASTViewPlugin.getDefault().getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		fDeleteAction.setId(ActionFactory.DELETE.getId());
		fDeleteAction.setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);

		refreshASTSettingsActions();
	}

	private void refreshAST() throws CoreException {
		ASTNode node = getASTNodeNearSelection((IStructuredSelection) fViewer.getSelection());
		int offset = 0;
		int length = 0;
		if (node != null) {
			offset = node.getStart();
			length = node.getLength();
		}

		internalSetInput(fSourceRoot, offset, length, getCurrentPHPVersion());
	}

	protected void setASTLevel(PHPVersion level, boolean doRefresh) {
		PHPVersion oldLevel = fPHPVersion;
		fPHPVersion = level;


		if (doRefresh && fSourceRoot != null && oldLevel != fPHPVersion) {
			try {
				refreshAST();
			} catch (CoreException e) {
				showAndLogError("Could not set AST to new level.", e); //$NON-NLS-1$
				// set back to old level
				fPHPVersion = oldLevel;
			}
		}
	}

	protected void setASTInputType(int inputKind) {
		if (inputKind != fCurrentInputKind) {
			fCurrentInputKind = inputKind;
			fDialogSettings.put(SETTINGS_INPUT_KIND, inputKind);
			for (ASTInputKindAction curr : fASTInputKindActions) {
				curr.setChecked(curr.getInputKind() == inputKind);
			}
			refreshASTSettingsActions();
			performRefresh();
		}
	}

	private ASTNode getASTNodeNearSelection(IStructuredSelection selection) {
		Object elem = selection.getFirstElement();
		if (elem instanceof ASTAttribute) {
			return ((ASTAttribute) elem).getParentASTNode();
		} else if (elem instanceof ASTNode) {
			return (ASTNode) elem;
		}
		return null;
	}

	private void installModificationListener() {
		fCurrentDocument = fEditor.getDocumentProvider().getDocument(fEditor.getEditorInput());
		fCurrentDocument.addDocumentListener(fSuperListener);
	}

	private void uninstallModificationListener() {
		if (fCurrentDocument != null) {
			fCurrentDocument.removeDocumentListener(fSuperListener);
			fCurrentDocument = null;
		}
	}

	protected void handleDocumentDisposed(IDocument document) {
		uninstallModificationListener();
	}

	protected void handleDocumentChanged(IDocument document) {
		setASTUptoDate(false);
	}

	protected void handleSelectionChanged(ISelection selection) {
		fExpandAction.setEnabled(!selection.isEmpty());
		fCollapseAction.setEnabled(!selection.isEmpty());
		fCopyAction.setEnabled(!selection.isEmpty());
	}

	protected void handleEditorPostSelectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof ITextSelection)) {
			return;
		}
		ITextSelection textSelection = (ITextSelection) selection;
		if (part == fEditor) {
			fViewer.getTree().setRedraw(false);
			try {
				fASTLabelProvider.setSelectedRange(textSelection.getOffset(), textSelection.getLength());
			} finally {
				fViewer.getTree().setRedraw(true);
			}
		}
		if (!fDoLinkWithEditor) {
			return;
		}
		if (fRoot == null || part != fEditor) {
			if (part instanceof ITextEditor && (EditorUtility.getPhpInput((ITextEditor) part) != null)) {
				try {
					setInput((ITextEditor) part);
				} catch (CoreException e) {
					setContentDescription(e.getStatus().getMessage());
				}
			}

		} else { // fRoot != null && part == fEditor
			doLinkWithEditor(selection);
		}
	}

	private void doLinkWithEditor(ISelection selection) {
		ITextSelection textSelection = (ITextSelection) selection;
		int offset = textSelection.getOffset();
		int length = textSelection.getLength();

		NodeFinder finder = new NodeFinder(offset, length);
		fRoot.accept(finder);
		ASTNode covering = finder.getCoveringNode();
		if (covering != null) {
			fViewer.reveal(covering);
			fViewer.setSelection(new StructuredSelection(covering));
		}
	}

	protected void handleDoubleClick(DoubleClickEvent event) {
		fDoubleClickAction.run();
	}

	protected void performLinkWithEditor() {
		fDoLinkWithEditor = fLinkWithEditor.isChecked();
		fDialogSettings.put(SETTINGS_LINK_WITH_EDITOR, fDoLinkWithEditor);

		if (fDoLinkWithEditor && fEditor != null) {
			ISelectionProvider selectionProvider = fEditor.getSelectionProvider();
			if (selectionProvider != null) { // can be null when editor is
												// closed
				doLinkWithEditor(selectionProvider.getSelection());
			}
		}
	}

	protected void performCollapse() {
		IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
		if (selection.isEmpty()) {
			fViewer.collapseAll();
		} else {
			Object[] selected = selection.toArray();
			fViewer.getTree().setRedraw(false);
			for (Object element : selected) {
				fViewer.collapseToLevel(element, AbstractTreeViewer.ALL_LEVELS);
			}
			fViewer.getTree().setRedraw(true);
		}
	}

	protected void performExpand() {
		IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
		if (selection.isEmpty()) {
			fViewer.expandToLevel(3);
		} else {
			Object[] selected = selection.toArray();
			fViewer.getTree().setRedraw(false);
			for (Object element : selected) {
				fViewer.expandToLevel(element, 3);
			}
			fViewer.getTree().setRedraw(true);
		}
	}

	protected void performSetFocus() {
		IEditorPart part = EditorUtility.getActiveEditor();
		if (part instanceof ITextEditor) {
			try {
				setInput((ITextEditor) part);
			} catch (CoreException e) {
				showAndLogError("Could not set AST view input ", e); //$NON-NLS-1$
			}
		}
	}

	protected void performRefresh() {
		if (fSourceRoot != null) {
			try {
				refreshAST();
			} catch (CoreException e) {
				showAndLogError("Could not set AST view input ", e); //$NON-NLS-1$
			}
		}
	}

	protected void performClear() {
		fSourceRoot = null;
		try {
			setInput(null);
		} catch (CoreException e) {
			showAndLogError("Could not reset AST view ", e); //$NON-NLS-1$
		}
		clearView();
	}

	private void showAndLogError(String message, CoreException e) {
		ASTViewPlugin.log(message, e);
		ErrorDialog.openError(getSite().getShell(), "AST View", message, e.getStatus()); //$NON-NLS-1$
	}

	private void showAndLogError(String message, Throwable e) {
		IStatus status = new Status(IStatus.ERROR, ASTViewPlugin.getPluginId(), 0, message, e);
		ASTViewPlugin.log(status);
		ErrorDialog.openError(getSite().getShell(), "AST View", null, status); //$NON-NLS-1$
	}

	protected void performCreateBindings() {
		fCreateBindings = fCreateBindingsAction.isChecked();
		fDialogSettings.put(SETTINGS_NO_BINDINGS, !fCreateBindings);
		performRefresh();
	}

	protected void performStatementsRecovery() {
		fStatementsRecovery = fStatementsRecoveryAction.isChecked();
		fDialogSettings.put(SETTINGS_NO_STATEMENTS_RECOVERY, !fStatementsRecovery);
		performRefresh();
	}

	protected void performBindingsRecovery() {
		fBindingsRecovery = fBindingsRecoveryAction.isChecked();
		fDialogSettings.put(SETTINGS_NO_BINDINGS_RECOVERY, !fBindingsRecovery);
		performRefresh();
	}

	protected void performFilterNonRelevant() {
		boolean showNonRelevant = !fFilterNonRelevantAction.isChecked();
		fNonRelevantFilter.setShowNonRelevant(showNonRelevant);
		fDialogSettings.put(SETTINGS_SHOW_NON_RELEVANT, showNonRelevant);
		fViewer.refresh();
	}

	protected void performFindDeclaringNode() {
		String msg = "Find Declaring Node from Key";
		String key = askForKey(msg);
		if (key == null)
			return;
		// TODO : should look for the declaring node
		ASTNode node = null; // fRoot. findDeclaringNode(key);
		if (node != null) {
			fViewer.setSelection(new StructuredSelection(node), true);
		} else {
			MessageDialog.openError(getSite().getShell(), "Find Declaring Node from Key",
					"The declaring node for key '" + key + "' could not be found");
		}
	}

	private String askForKey(String dialogTitle) {
		InputDialog dialog = new InputDialog(getSite().getShell(), dialogTitle,
				"Key: (optionally surrounded by <KEY: '> and <'>)", "", null);
		if (dialog.open() != Window.OK)
			return null;

		String key = dialog.getValue();
		if (key.startsWith("KEY: '") && key.endsWith("'"))
			key = key.substring(6, key.length() - 1);
		return key;
	}

	protected void performParseBindingFromKey() {
		// TODO work on this action is required
		// String msg = "Parse Binding from Key";
		// String key= askForKey(msg);
		// if (key == null)
		// return;
		// ASTParser parser= ASTParser.newParser(fCurrentASTLevel, fSourceRoot);
		// class MyASTRequestor extends ASTRequestor {
		// String fBindingKey;
		// IBinding fBinding;
		// public void acceptBinding(String bindingKey, IBinding binding) {
		// fBindingKey= bindingKey;
		// fBinding= binding;
		// }
		// }
		// MyASTRequestor requestor= new MyASTRequestor();
		// ASTAttribute item;
		// Object viewerInput= fViewer.getInput();
		// try {
		// parser.createASTs(new IProgram[0], new String[] { key }, requestor,
		// null);
		// if (requestor.fBindingKey != null) {
		// String name= requestor.fBindingKey + ": " +
		// Binding.getBindingLabel(requestor.fBinding);
		// item= new Binding(viewerInput, name, requestor.fBinding, true);
		// } else {
		// item= new Error(viewerInput, "Key not resolved: " + key, null);
		// }
		// } catch (RuntimeException e) {
		// item= new Error(viewerInput, "Error resolving key: " + key, e);
		// }
		// fViewer.add(viewerInput, item);
		// fViewer.setSelection(new StructuredSelection(item), true);
	}

	protected void performParseBindingFromElement() {
		InputDialog dialog = new InputDialog(getSite().getShell(), "Parse Binding from Java Element",
				"IJavaElement#getHandleIdentifier():", "", null);
		if (dialog.open() != Window.OK)
			return;

		// TODO : work on this action is required
		/*
		 * IJavaElement handle= JavaCore.create(handleIdentifier);
		 * 
		 * Object viewerInput= fViewer.getInput(); ASTAttribute item; if (handle == null) { item= new Error(viewerInput,
		 * "handleIdentifier not resolved: " + handleIdentifier, null); } else if (! handle.exists()) { item= new
		 * Error(viewerInput, "element does not exist: " + handleIdentifier, null); } else if (handle.getJavaProject()
		 * == null) { item= new Error(viewerInput, "getJavaProject() is null: " + handleIdentifier, null); } else {
		 * IJavaProject project= handle.getJavaProject(); ASTParser parser= ASTParser.newParser(fCurrentASTLevel);
		 * parser.setProject(project); IBinding[] bindings= parser.createBindings(new IJavaElement[] { handle }, null);
		 * String name= handleIdentifier + ": " + Binding.getBindingLabel(bindings[0]); item= new Binding(viewerInput,
		 * name, bindings[0], true); } fViewer.add(viewerInput, item); fViewer.setSelection(new
		 * StructuredSelection(item), true);
		 */ }

	protected void performDoubleClick() {
		if (fEditor == null) {
			return;
		}

		ISelection selection = fViewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();

		boolean isTripleClick = (obj == fPreviousDouble);
		fPreviousDouble = isTripleClick ? null : obj;

		if (obj instanceof ExceptionAttribute) {
			Throwable exception = ((ExceptionAttribute) obj).getException();
			if (exception != null) {
				String label = ((ExceptionAttribute) obj).getLabel();
				showAndLogError("An error occurred while calculating an AST View Label:\n" + label, exception); //$NON-NLS-1$
				return;
			}
		}

		ASTNode node = null;
		if (obj instanceof ASTNode) {
			node = (ASTNode) obj;

		} else if (obj instanceof NodeProperty) {
			Object val = ((NodeProperty) obj).getNode();
			if (val instanceof ASTNode) {
				node = (ASTNode) val;
			}

		} else if (obj instanceof Binding) {

			// TODO shou;d cpmplete the IBinding part

			// IBinding binding= ((Binding) obj).getBinding();
			// ASTNode declaring= fRoot.findDeclaringNode(binding);
			// if (declaring != null) {
			// fViewer.reveal(declaring);
			// fViewer.setSelection(new StructuredSelection(declaring));
			// } else {
			// fViewer.getTree().getDisplay().beep();
			// }
			// return;

		} else if (obj instanceof ProblemNode) {
			ProblemNode problemNode = (ProblemNode) obj;
			EditorUtility.selectInEditor(fEditor, problemNode.getOffset(), problemNode.getLength());
			return;

		} else if (obj instanceof PhpElement) {
			// TODO : complete this task for open in edtior
			/*
			 * ISourceModule phpElement= ((PhpElement) obj).getPhpElement(); if (phpElement instanceof IPackageFragment)
			 * { ShowInPackageViewAction showInPackageViewAction= new ShowInPackageViewAction(getViewSite());
			 * showInPackageViewAction.run(phpElement); } else { try { IEditorPart editorPart=
			 * JavaUI.openInEditor(phpElement); if (editorPart != null) JavaUI.revealInEditor(editorPart, phpElement); }
			 * catch (PartInitException e) { showAndLogError( "Could not open editor.", e); //$NON-NLS-1$ } catch
			 * (JavaModelException e) { showAndLogError("Could not open editor." , e); //$NON-NLS-1$ } }
			 */ return;
		}

		if (node != null) {
			int offset = isTripleClick ? fRoot.getExtendedStartPosition(node) : node.getStart();
			int length = isTripleClick ? fRoot.getExtendedLength(node) : node.getLength();

			EditorUtility.selectInEditor(fEditor, offset, length);
		}
	}

	protected void performTrayDoubleClick() {
		IStructuredSelection selection = (IStructuredSelection) fTray.getSelection();
		if (selection.size() != 1)
			return;
		Object obj = selection.getFirstElement();
		if (obj instanceof ExceptionAttribute) {
			Throwable exception = ((ExceptionAttribute) obj).getException();
			if (exception != null) {
				String label = ((ExceptionAttribute) obj).getLabel();
				showAndLogError("An error occurred while calculating an AST View Label:\n" + label, exception); //$NON-NLS-1$
				return;
			}
		}
		if (obj instanceof Binding) {
			Binding binding = (Binding) obj;
			fViewer.setSelection(new StructuredSelection(binding), true);
		}
	}

	protected void performDelete() {
		boolean removed = false;
		IStructuredSelection selection = (IStructuredSelection) fTray.getSelection();
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			if (obj instanceof DynamicAttributeProperty)
				obj = ((DynamicAttributeProperty) obj).getParent();
			if (obj instanceof DynamicBindingProperty)
				obj = ((DynamicBindingProperty) obj).getParent();

			removed |= fTrayRoots.remove(obj);
		}
		if (removed)
			fTray.setInput(fTrayRoots);
	}

	@Override
	public void setFocus() {
		fViewer.getControl().setFocus();
	}

	@Override
	public ShowInContext getShowInContext() {
		return new ShowInContext(null, getSite().getSelectionProvider().getSelection());
	}

	/**
	 * @param attribute
	 *            an attribute
	 * @return the object inside the attribute, or <code>null</code> iff none
	 */
	static Object unwrapAttribute(Object attribute) {
		if (attribute instanceof Binding) {
			return ((Binding) attribute).getBinding();
		} else if (attribute instanceof PhpElement) {
			return ((PhpElement) attribute).getPhpElement();
		} else if (attribute instanceof ASTNode) {
			return attribute;
		} else {
			return null;
		}
	}
}
