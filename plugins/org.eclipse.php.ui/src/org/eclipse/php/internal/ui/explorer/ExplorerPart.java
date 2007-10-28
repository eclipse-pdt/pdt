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
package org.eclipse.php.internal.ui.explorer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPInterfaceNameData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData.PHPSuperClassNameData;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.LinkingSelectionListener;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.treecontent.TreeProvider;
import org.eclipse.php.internal.ui.util.*;
import org.eclipse.php.internal.ui.util.TreePath;
import org.eclipse.php.internal.ui.workingset.ConfigureWorkingSetAction;
import org.eclipse.php.internal.ui.workingset.ExplorerViewActionGroup;
import org.eclipse.php.internal.ui.workingset.WorkingSetModel;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.*;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;
import org.eclipse.ui.views.navigator.NavigatorDragAdapter;
import org.eclipse.ui.views.navigator.NavigatorDropAdapter;

public class ExplorerPart extends ViewPart implements IMenuListener, FocusListener, IPartListener2 {

	private static String MEMENTO_ROOT_MODE = "ExplorerPart.rootMode"; //$NON-NLS-1$
	private static String MEMENTO_WORKING_SET = "ExplorerPart.workingSet"; //$NON-NLS-1$

	private PHPTreeViewer fViewer;
	protected ExplorerContentProvider fContentProvider;
	private IContextActivation contextActivation;

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.FocusListener#focusGained(org.eclipse.swt.events.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
		
		// Why do we need refresh here? 
		//fContentProvider.postRefresh(fViewer.getInput());
		
		// activate the org.eclipse.php.ui.contexts.window context 
		// only for this view the allow the F3 shortcut which conflict with JDT
		IContextService service = (IContextService) PHPUiPlugin.getDefault().getWorkbench().getService(IContextService.class);
		if (service != null) {
			contextActivation = service.activateContext("org.eclipse.php.ui.contexts.window"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see
	 org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
		// deactivate the org.eclipse.php.ui.contexts.window context
		// the context used only for this view menu
		IContextService service = (IContextService) PHPUiPlugin.getDefault().getWorkbench().getService(IContextService.class);
		if (service != null && contextActivation != null) {
			service.deactivateContext(contextActivation);
		}
	}
	
	public void partActivated(IWorkbenchPartReference partRef) {
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	public void partClosed(IWorkbenchPartReference partRef) {
	}

	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	public void partHidden(IWorkbenchPartReference partRef) {
	}

	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

	public void partOpened(IWorkbenchPartReference partRef) {
	}

	public void partVisible(IWorkbenchPartReference partRef) {
		// We have to refresh the view, since if the editor has been updated
		// while this view was hidden (another view was active in the same folder)
		// - the view is not synchronized with the editor contents.
		if(partRef.getId().equals("org.eclipse.php.ui.explorer")){ //$NON-NLS-1$
			fContentProvider.postRefresh(fViewer.getInput());
		}
	}


	protected ExplorerLabelProvider fLabelProvider;

	private ExplorerActionGroup fActionSet;
	private Menu fContextMenu;

	private boolean fLinkingEnabled;
	private String fWorkingSetName;

	private ISelection fLastOpenSelection;
	private ISelectionChangedListener fPostSelectionListener;

	private FilterUpdater fFilterUpdater;
	private int fRootMode = ExplorerViewActionGroup.SHOW_PROJECTS;
	private WorkingSetModel fWorkingSetModel;

	private IPartListener fPartListener = new IPartListener() {
		public void partActivated(IWorkbenchPart part) {
			if (part instanceof IEditorPart) {
				editorActivated((IEditorPart) part);
			}
		}

		public void partBroughtToTop(IWorkbenchPart part) {
		}

		public void partClosed(IWorkbenchPart part) {
		}

		public void partDeactivated(IWorkbenchPart part) {
		}

		public void partOpened(IWorkbenchPart part) {
		}
	};

	private class ExplorerTreeViewer extends PHPTreeViewer {
		java.util.List fPendingGetChildren;

		public ExplorerTreeViewer(Composite parent, int style) {
			super(parent, style);
			fPendingGetChildren = Collections.synchronizedList(new ArrayList());
			setComparer(new PHPOutlineElementComparer());
		}

		public void add(Object parentElement, Object[] childElements) {
			if (fPendingGetChildren.contains(parentElement))
				return;
			super.add(parentElement, childElements);
		}

		protected Object[] getRawChildren(Object parent) {
			try {
				fPendingGetChildren.add(parent);
				return super.getRawChildren(parent);
			} finally {
				fPendingGetChildren.remove(parent);
			}
		}

		private Object getElement(TreeItem item) {
			Object result = item.getData();
			if (result == null)
				return null;
			return result;
		}

		private TreePath createTreePath(TreeItem item) {
			List result = new ArrayList();
			result.add(item.getData());
			TreeItem parent = item.getParentItem();
			while (parent != null) {
				result.add(parent.getData());
				parent = parent.getParentItem();
			}
			Collections.reverse(result);
			return new TreePath(result.toArray());
		}

		public ISelection getSelection() {
			IContentProvider cp = getContentProvider();
			if (!(cp instanceof IMultiElementTreeContentProvider)) {
				return super.getSelection();
			}
			Control control = getControl();
			if (control == null || control.isDisposed() || !control.isVisible()) {
				return StructuredSelection.EMPTY;
			}
			Tree tree = getTree();
			TreeItem[] selection = tree.getSelection();
			List result = new ArrayList(selection.length);
			List treePaths = new ArrayList();
			for (int i = 0; i < selection.length; i++) {
				TreeItem item = selection[i];
				Object element = getElement(item);
				if (element == null)
					continue;
				if (!result.contains(element)) {
					result.add(element);
				}
				treePaths.add(createTreePath(item));
			}
			return new MultiElementSelection(this, result, (TreePath[]) treePaths.toArray(new TreePath[treePaths.size()]));
		}

		protected Object[] getSortedChildren(Object parent) {
			IParentAwareSorter sorter = getSorter() instanceof IParentAwareSorter ? (IParentAwareSorter) getSorter() : null;
			if (sorter != null)
				sorter.setParent(parent);
			try {
				return super.getSortedChildren(parent);
			} finally {
				if (sorter != null)
					sorter.setParent(null);
			}
		}

		/*
		 * @see AbstractTreeViewer#isExpandable(java.lang.Object)
		 */
		public boolean isExpandable(Object parent) {
			ViewerFilter[] filters = fViewer.getFilters();
			Object[] children = ((ITreeContentProvider) fViewer.getContentProvider()).getChildren(parent);
			for (int i = 0; i < children.length; i++) {
				Object object = children[i];

				if (isEssential(object))
					return true;

				object = filter(object, parent, filters);
				if (object != null)
					return true;
			}
			return false;
		}

		// Sends the object through the given filters
		private Object filter(Object object, Object parent, ViewerFilter[] filters) {
			Object rv = null;
			for (int i = 0; i < filters.length; i++) {
				ViewerFilter filter = filters[i];
				if (filter.select(fViewer, parent, object))
					rv = object;
			}
			return rv;
		}

		/*
		 * @see org.eclipse.jface.viewers.StructuredViewer#filter(java.lang.Object[])
		 * @since 3.0
		 */
		protected Object[] filter(Object[] elements) {
			ViewerFilter[] filters = getFilters();
			if (filters == null || filters.length == 0)
				return elements;

			ArrayList filtered = new ArrayList(elements.length);
			Object root = getRoot();
			for (int i = 0; i < elements.length; i++) {
				boolean add = true;
				if (!isEssential(elements[i])) {
					for (int j = 0; j < filters.length; j++) {
						add = filters[j].select(this, root, elements[i]);
						if (!add)
							break;
					}
				}
				if (add)
					filtered.add(elements[i]);
			}
			return filtered.toArray();
		}

		/* Checks if a filtered object in essential (ie. is a parent that
		 * should not be removed).
		 */
		private boolean isEssential(Object object) {
			if (object instanceof IContainer) {
				IContainer folder = (IContainer) object;
				try {
					if (object instanceof IProject) {
						IProject project = (IProject) object;
						if (project.isOpen() && ((IProject) object).hasNature(PHPNature.ID))
							return false;
					} else {
						Object obj = filter(object, object, fViewer.getFilters());
						return obj != null && folder.members().length > 0;
					}
				} catch (CoreException e) {
					return false;
				}
			}
			return false;
		}

		protected void handleInvalidSelection(ISelection invalidSelection, ISelection newSelection) {
			IStructuredSelection is = (IStructuredSelection) invalidSelection;
			List ns = null;
			if (newSelection instanceof IStructuredSelection) {
				ns = new ArrayList(((IStructuredSelection) newSelection).toList());
			} else {
				ns = new ArrayList();
			}
			boolean changed = false;
			for (Iterator iter = is.iterator(); iter.hasNext();) {
				Object element = iter.next();
				if (element instanceof PHPProjectModel) {

					IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) element);
					if (!project.isOpen()) {
						ns.add(project);
						changed = true;
					}
				} else if (element instanceof IProject) {
					IProject project = (IProject) element;
					if (project.isOpen()) {
						changed = true;
					}
				}
			}
			if (changed) {
				newSelection = new StructuredSelection(ns);
				setSelection(newSelection);
			}
			super.handleInvalidSelection(invalidSelection, newSelection);
		}

	}

	public ExplorerPart() {
		fPostSelectionListener = new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handlePostSelectionChanged(event);
			}
		};
	}

	public void createPartControl(Composite parent) {
		fViewer = createViewer(parent);
		fViewer.getControl().addFocusListener(this);

		fSelectionListener.setViewer(getViewer());
		fViewer.setUseHashlookup(true);
		initDragAndDrop();

		setProviders();

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		fContextMenu = menuMgr.createContextMenu(fViewer.getTree());
		fViewer.getTree().setMenu(fContextMenu);

		IWorkbenchPartSite site = getSite();
		site.registerContextMenu(menuMgr, fViewer);
		site.setSelectionProvider(fViewer);
		site.getPage().addPartListener(fPartListener);
		site.getPage().addPartListener(this);

		initLinkingEnabled();
		fActionSet = new ExplorerActionGroup(this);
		if (fWorkingSetModel != null)
			fActionSet.getWorkingSetActionGroup().setWorkingSetModel(fWorkingSetModel);

		if (memento != null) {
			IMemento wsMemento = memento.getChild(MEMENTO_WORKING_SET);
			if (wsMemento != null)
				fActionSet.restoreFilterAndSorterState(memento.getChild(MEMENTO_WORKING_SET));
		}

		fViewer.setInput(findInputElement());

		initKeyListener();

		fViewer.addPostSelectionChangedListener(fPostSelectionListener);

		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				fActionSet.handleDoubleClick(event);
			}
		});

		fViewer.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				fActionSet.handleOpen(event);
				fLastOpenSelection = event.getSelection();
			}
		});

		IStatusLineManager slManager = getViewSite().getActionBars().getStatusLineManager();
		fViewer.addSelectionChangedListener(new StatusBarUpdater(slManager));

		fillActionBars();

		updateTitle();

		fFilterUpdater = new FilterUpdater(fViewer);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fFilterUpdater);

		// Syncing the package explorer has to be done here. It can't be done
		// when restoring the link state since the package explorers input isn't
		// set yet.
		if (isLinkingEnabled()) {
			IEditorPart editor = getViewSite().getPage().getActiveEditor();
			if (editor != null) {
				editorActivated(editor);
			}
		}

	}

	private PHPTreeViewer createViewer(Composite composite) {
		return new ExplorerTreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	Object getElementOfInput(IEditorInput input) {
		if (input instanceof IFileEditorInput)
			return ((IFileEditorInput) input).getFile();
		return null;
	}

	public void setFocus() {
		fViewer.getTree().setFocus();

	}

	protected void initDragAndDrop() {
		int ops = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getInstance(), ResourceTransfer.getInstance(), FileTransfer.getInstance(), PluginTransfer.getInstance() };
		TreeViewer viewer = getViewer();
		viewer.addDragSupport(ops, transfers, new NavigatorDragAdapter(viewer));
		NavigatorDropAdapter adapter = new PHPNavigatorDropAdapter(viewer);
		adapter.setFeedbackEnabled(false);
		viewer.addDropSupport(ops | DND.DROP_DEFAULT, transfers, adapter);
		//        dragDetectListener = new Listener() {
		//            public void handleEvent(Event event) {
		//                dragDetected = true;
		//            }
		//        };
		//		viewer.getControl().addListener(SWT.DragDetect, dragDetectListener);
	}

	private void initKeyListener() {
		fViewer.getControl().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				fActionSet.handleKeyEvent(event);
			}
		});
	}

	private void handlePostSelectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		// If the selection is the same as the one that triggered the last
		// open event then do nothing. The editor already got revealed.
		if (isLinkingEnabled() && !selection.equals(fLastOpenSelection)) {
			linkToEditor((IStructuredSelection) selection);
		}
		fLastOpenSelection = null;
	}

	private void linkToEditor(IStructuredSelection selection) {
		// ignore selection changes if the package explorer is not the active part.
		// In this case the selection change isn't triggered by a user.
		if (this != getSite().getPage().getActivePart())
			return;
		Object obj = selection.getFirstElement();

		if (selection.size() == 1) {
			IEditorPart part = EditorUtility.isOpenInEditor(obj);
			if (part != null) {
				IWorkbenchPage page = getSite().getPage();
				page.bringToTop(part);
				if (obj instanceof PHPCodeData)
					EditorUtility.revealInEditor(part, (PHPCodeData) obj);
			}
		}
	}

	private void setProviders() {
		fContentProvider = createContentProvider();
		IPHPTreeContentProvider[] treeProviders = TreeProvider.getTreeProviders(getViewSite().getId());
		fContentProvider.setTreeProviders(treeProviders);
		fViewer.setContentProvider(fContentProvider);

		fViewer.setSorter(new ExplorerSorter());

		fLabelProvider = createLabelProvider();
		fLabelProvider.setTreeProviders(treeProviders);
		fViewer.setLabelProvider(new DecoratingLabelProvider(fLabelProvider, PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator()));
	}

	void projectStateChanged(Object root) {
		Control ctrl = fViewer.getControl();
		if (ctrl != null && !ctrl.isDisposed() && ctrl.isVisible()) {
			fViewer.refresh(root, true);
			// trigger a syntetic selection change so that action refresh their
			// enable state.
			fViewer.setSelection(fViewer.getSelection());
		}
	}

	public ExplorerContentProvider createContentProvider() {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		boolean showCUChildren = store.getBoolean(PreferenceConstants.SHOW_CU_CHILDREN);
		if (showProjects())
			return new ExplorerContentProvider(this, showCUChildren);
		return new WorkingSetAwareContentProvider(this, showCUChildren, fWorkingSetModel);
	}

	boolean showProjects() {
		return fRootMode == ExplorerViewActionGroup.SHOW_PROJECTS;
	}

	boolean showWorkingSets() {
		return fRootMode == ExplorerViewActionGroup.SHOW_WORKING_SETS;
	}

	protected ExplorerLabelProvider createLabelProvider() {
		return new ExplorerLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | PHPElementLabels.P_COMPRESSED, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | PHPElementImageProvider.SMALL_ICONS | PHPElementImageProvider.OVERLAY_ICONS, fContentProvider);
	}

	private Object findInputElement() {
		if (showWorkingSets()) {
			return fWorkingSetModel;
		}
		Object input = getSite().getPage().getInput();
		if (input instanceof IWorkspace || input instanceof IWorkspaceRoot) {
			return PHPWorkspaceModelManager.getInstance();
		} else if (input instanceof IContainer) {
			return input;
		}
		return PHPWorkspaceModelManager.getInstance();
	}

	public void dispose() {
		getSite().getPage().removePostSelectionListener(fSelectionListener);
		if (fContextMenu != null && !fContextMenu.isDisposed())
			fContextMenu.dispose();

		getSite().getPage().removePartListener(fPartListener);
		getSite().getPage().removePartListener(this);

		if (fActionSet != null)
			fActionSet.dispose();

		if (fFilterUpdater != null)
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fFilterUpdater);

		if (fWorkingSetModel != null)
			fWorkingSetModel.dispose();

		super.dispose();
	}

	public void menuAboutToShow(IMenuManager menu) {
		PHPUiPlugin.createStandardGroups(menu);

		fActionSet.setContext(new ActionContext(getSelection()));
		fActionSet.fillContextMenu(menu);
		fActionSet.setContext(null);
	}

	void editorActivated(IEditorPart editor) {
		if (!isLinkingEnabled())
			return;
		Object input = getElementOfInput(editor.getEditorInput());
		if (input == null)
			return;
		if (!inputIsSelected(editor.getEditorInput()))
			showInput(input);
		else
			this.fViewer.getTree().showSelection();

	}

	private boolean inputIsSelected(IEditorInput input) {
		IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
		if (selection.size() != 1)
			return false;
		IEditorInput selectionAsInput = null;
		selectionAsInput = EditorUtility.getEditorInput(selection.getFirstElement());
		return input.equals(selectionAsInput);
	}

	boolean showInput(Object element) {
		if (element != null) {
			ISelection newSelection = new StructuredSelection(element);
			if (fViewer.getSelection().equals(newSelection)) {
				fViewer.reveal(element);
			} else {
				try {
					fViewer.removePostSelectionChangedListener(fPostSelectionListener);
					fViewer.setSelection(newSelection, true);

					while (element != null && fViewer.getSelection().isEmpty()) {
						// Try to select parent in case element is filtered
						element = fContentProvider.getParent(element);
						if (element != null) {
							newSelection = new StructuredSelection(element);
							fViewer.setSelection(newSelection, true);
						}
					}
				} finally {
					fViewer.addPostSelectionChangedListener(fPostSelectionListener);
				}
			}
			return true;
		}
		return false;
	}

	public TreeViewer getViewer() {
		return fViewer;
	}

	private void fillActionBars() {
		IActionBars actionBars = getViewSite().getActionBars();
		fActionSet.fillActionBars(actionBars);
	}

	/**
	 * Returns the current selection.
	 */
	private ISelection getSelection() {
		return fViewer.getSelection();
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

	boolean isLinkingEnabled() {
		return fLinkingEnabled;
	}

	private void initLinkingEnabled() {
		setLinkingEnabled(PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.LINK_BROWSING_PROJECTS_TO_EDITOR));
	}

	public void setLinkingEnabled(boolean enabled) {
		fLinkingEnabled = enabled;
		PreferenceConstants.getPreferenceStore().setValue(PreferenceConstants.LINK_BROWSING_PROJECTS_TO_EDITOR, enabled);

		IWorkbenchPartSite site = getSite();
		if (site == null)
			return;
		IWorkbenchPage page = site.getPage();
		if (page == null)
			return;

		if (enabled) {
			page.addPostSelectionListener(fSelectionListener);
			IEditorPart editor = page.getActiveEditor();
			if (editor != null)
				editorActivated(editor);
		} else
			page.removePostSelectionListener(fSelectionListener);
	}

	String getFrameName(Object element) {
		if (element instanceof PHPCodeData) {
			return ((PHPCodeData) element).getName();
		}
		return fLabelProvider.getText(element);
	}

	String getToolTipText(Object element) {
		String result;
		if (!(element instanceof IResource)) {
			if (element instanceof PHPWorkspaceModelManager) {
				result = PHPUIMessages.getString("PHPExplorerPart_workspace");
			} else if (element instanceof PHPCodeData) {
				result = PHPElementLabels.getTextLabel(element, AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS);
			} else {
				result = fLabelProvider.getText(element);
			}
		} else {
			IPath path = ((IResource) element).getFullPath();
			if (path.isRoot()) {
				result = PHPUIMessages.getString("PHPExplorer_title");
			} else {
				result = path.makeRelative().toString();
			}
		}

		if (fWorkingSetName == null)
			return result;

		String wsstr = MessageFormat.format(PHPUIMessages.getString("PHPExplorer_toolTip"), new String[] { fWorkingSetName });
		if (result.length() == 0)
			return wsstr;
		return MessageFormat.format(PHPUIMessages.getString("PHPExplorer_toolTip2"), new String[] { result, fWorkingSetName });
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

	void setWorkingSetName(String workingSetName) {
		fWorkingSetName = workingSetName;
	}

	public void rootModeChanged(int newMode) {
		fRootMode = newMode;
		if (showWorkingSets() && fWorkingSetModel == null) {
			createWorkingSetModel();
			if (fActionSet != null) {
				fActionSet.getWorkingSetActionGroup().setWorkingSetModel(fWorkingSetModel);
			}
		}
		ISelection selection = fViewer.getSelection();
		Object input = fViewer.getInput();
		boolean isRootInputChange = PHPWorkspaceModelManager.getInstance().equals(input) || (fWorkingSetModel != null && fWorkingSetModel.equals(input)) || input instanceof IWorkingSet;
		try {
			fViewer.getControl().setRedraw(false);
			if (isRootInputChange) {
				fViewer.setInput(null);
			}
			setProviders();
			setSorter();
			fActionSet.getWorkingSetActionGroup().fillFilters(fViewer);
			if (isRootInputChange) {
				fViewer.setInput(findInputElement());
			}
			fViewer.setSelection(selection, true);
		} finally {
			fViewer.getControl().setRedraw(true);
		}
		if (isRootInputChange && fWorkingSetModel!= null && fWorkingSetModel.needsConfiguration()) {
			ConfigureWorkingSetAction action = new ConfigureWorkingSetAction(getSite());
			action.setWorkingSetModel(fWorkingSetModel);
			action.run();
			fWorkingSetModel.configured();
		}
	}

	private void setSorter() {
		if (showWorkingSets()) {
			fViewer.setSorter(new WorkingSetAwarePHPElementSorter());
		} else {
			fViewer.setSorter(new PHPElementSorter());
		}
	}

	private void createWorkingSetModel(final IMemento memento) {
		SafeRunner.run(new ISafeRunnable() {
			public void run() throws Exception {
				fWorkingSetModel = new WorkingSetModel(memento);
			}

			public void handleException(Throwable exception) {
				fWorkingSetModel = new WorkingSetModel(memento);
			}

		});
	}

	private void createWorkingSetModel() {
		createWorkingSetModel(null);
	}

	public WorkingSetModel getWorkingSetModel() {
		return fWorkingSetModel;
	}

	public int getRootMode() {
		return fRootMode;
	}

	IMemento memento;

	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			final Integer rootModeInteger = memento.getInteger(MEMENTO_ROOT_MODE);
			if (rootModeInteger != null) {
				fRootMode = rootModeInteger.intValue();
			}
		}
		if (showWorkingSets()) {
			createWorkingSetModel(memento.getChild(MEMENTO_WORKING_SET));
		}
		this.memento = memento;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
	 */
	public void saveState(IMemento memento) {
		if (memento != null) {
			memento.putInteger(MEMENTO_ROOT_MODE, fRootMode);
			IMemento wsMemento = memento.createChild(MEMENTO_WORKING_SET);
			if (fWorkingSetModel != null) {
				fWorkingSetModel.saveState(wsMemento);
			}
			fActionSet.saveFilterAndSorterState(wsMemento);
		}
		super.saveState(memento);
	}

	private LinkingSelectionListener fSelectionListener = new LinkingSelectionListener() {

		/* (non-Javadoc)
		 * @see org.eclipse.php.internal.ui.editor.LinkingSelectionListener#createSelection(java.lang.Object)
		 */
		protected ISelection createSelection(Object element) {
			if (element instanceof PHPCodeData) {
				if (element instanceof PHPSuperClassNameData) {
					PHPSuperClassNameData superClassNameData = (PHPSuperClassNameData) element;
					PHPClassData classData = (PHPClassData) superClassNameData.getContainer();
					if (classData != null) {
						PHPClassData superClassData = PHPModelUtil.discoverSuperClass(classData, superClassNameData.getName());
						if (superClassData != null) {
							element = superClassData;
						}
					}
				} else if (element instanceof PHPInterfaceNameData) {
					PHPInterfaceNameData interfaceNameData = (PHPInterfaceNameData) element;
					PHPClassData classData = (PHPClassData) interfaceNameData.getContainer();
					if (classData != null) {
						PHPClassData interfaceData = PHPModelUtil.discoverInterface(classData, interfaceNameData.getName());
						if (interfaceData != null) {
							element = interfaceData;
						}
					}
				}
				PHPFileData fileData = PHPModelUtil.getPHPFileContainer((PHPCodeData) element);
				IResource res = PHPModelUtil.getResource(fileData);
				if (res != null && res.getProject().isAccessible()) {
					return new StructuredSelection(res);
				}
				if (fileData != null) {
					return new StructuredSelection(fileData);
				}
			}
			return super.createSelection(element);
		}

	};

	public Object getAdapter(Class adapter) {
		if (adapter == IShowInSource.class) {
            return getShowInSource();
        }
        if (adapter == IShowInTarget.class) {
            return getShowInTarget();
        }
		return super.getAdapter(adapter);
	}
	
	/**
     * Returns the <code>IShowInSource</code> for this view.
     */
    protected IShowInSource getShowInSource() {
        return new IShowInSource() {
            public ShowInContext getShowInContext() {
                return new ShowInContext(getViewer().getInput(), getViewer()
                        .getSelection());
            }
        };
    }
   
    /**
     * Returns the <code>IShowInTarget</code> for this view.
     */
    protected IShowInTarget getShowInTarget() {
    	return new IShowInTarget() {
            public boolean show(ShowInContext context) {
                ArrayList toSelect = new ArrayList();
                ISelection sel = context.getSelection();
                if (sel instanceof IStructuredSelection) {
                    IStructuredSelection ssel = (IStructuredSelection) sel;
                    for (Iterator i = ssel.iterator(); i.hasNext();) {
                        Object o = i.next();
                        if (o instanceof IResource) {
                            toSelect.add(o);
                        } else if (o instanceof IMarker) {
                            IResource r = ((IMarker) o).getResource();
                            if (r.getType() != IResource.ROOT) {
                                toSelect.add(r);
                            }
                        } else if (o instanceof IAdaptable) {
                            IAdaptable adaptable = (IAdaptable) o;
                            o = adaptable.getAdapter(IResource.class);
                            if (o instanceof IResource) {
                                toSelect.add(o);
                            } else {
                                o = adaptable.getAdapter(IMarker.class);
                                if (o instanceof IMarker) {
                                    IResource r = ((IMarker) o).getResource();
                                    if (r.getType() != IResource.ROOT) {
                                        toSelect.add(r);
                                    }
                                }
                            }
                        }
                    }
                }
                if (toSelect.isEmpty()) {
                    Object input = context.getInput();
                    if (input instanceof IAdaptable) {
                        IAdaptable adaptable = (IAdaptable) input;
                        Object o = adaptable.getAdapter(IResource.class);
                        if (o instanceof IResource) {
                            toSelect.add(o);
                        }
                    }
                }
                if (!toSelect.isEmpty()) {
                    getViewer().setSelection(new StructuredSelection(toSelect), true);
                    return true;
                }
                return false;
            }
        };
    }
}