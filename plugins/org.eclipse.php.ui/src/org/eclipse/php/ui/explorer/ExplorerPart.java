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
package org.eclipse.php.ui.explorer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.project.PHPNature;
import org.eclipse.php.internal.ui.editor.LinkingSelectionListener;
import org.eclipse.php.internal.ui.util.MultiElementSelection;
import org.eclipse.php.internal.ui.util.TreePath;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.dnd.DelegatingDropAdapter;
import org.eclipse.php.ui.dnd.PHPViewerDragAdapter;
import org.eclipse.php.ui.dnd.ResourceTransferDragAdapter;
import org.eclipse.php.ui.dnd.TransferDropTargetListener;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.php.ui.treecontent.TreeProvider;
import org.eclipse.php.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.ui.util.DecoratingPHPLabelProvider;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.php.ui.util.FilterUpdater;
import org.eclipse.php.ui.util.PHPElementComparer;
import org.eclipse.php.ui.util.PHPElementImageProvider;
import org.eclipse.php.ui.util.PHPElementLabels;
import org.eclipse.php.ui.util.PHPElementSorter;
import org.eclipse.php.ui.util.StatusBarUpdater;
import org.eclipse.php.ui.workingset.ConfigureWorkingSetAction;
import org.eclipse.php.ui.workingset.ExplorerViewActionGroup;
import org.eclipse.php.ui.workingset.WorkingSetModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

public class ExplorerPart extends ViewPart implements IMenuListener {

	private class ExplorerTreeViewer extends PHPTreeViewer {
		java.util.List fPendingGetChildren;

		public ExplorerTreeViewer(final Composite parent, final int style) {
			super(parent, style);
			fPendingGetChildren = Collections.synchronizedList(new ArrayList());
			setComparer(new PHPElementComparer());
		}

		public void add(final Object parentElement, final Object[] childElements) {
			if (fPendingGetChildren.contains(parentElement))
				return;
			super.add(parentElement, childElements);
		}

		private TreePath createTreePath(final TreeItem item) {
			final List result = new ArrayList();
			result.add(item.getData());
			TreeItem parent = item.getParentItem();
			while (parent != null) {
				result.add(parent.getData());
				parent = parent.getParentItem();
			}
			Collections.reverse(result);
			return new TreePath(result.toArray());
		}

		// Sends the object through the given filters
		private Object filter(final Object object, final Object parent, final ViewerFilter[] filters) {
			Object rv = null;
			for (int i = 0; i < filters.length; i++) {
				final ViewerFilter filter = filters[i];
				if (filter.select(fViewer, parent, object))
					rv = object;
			}
			return rv;
		}

		/*
		 * @see org.eclipse.jface.viewers.StructuredViewer#filter(java.lang.Object[])
		 * @since 3.0
		 */
		protected Object[] filter(final Object[] elements) {
			final ViewerFilter[] filters = getFilters();
			if (filters == null || filters.length == 0)
				return elements;

			final ArrayList filtered = new ArrayList(elements.length);
			final Object root = getRoot();
			for (int i = 0; i < elements.length; i++) {
				boolean add = true;
				if (!isEssential(elements[i]))
					for (int j = 0; j < filters.length; j++) {
						add = filters[j].select(this, root, elements[i]);
						if (!add)
							break;
					}
				if (add)
					filtered.add(elements[i]);
			}
			return filtered.toArray();
		}

		private Object getElement(final TreeItem item) {
			final Object result = item.getData();
			if (result == null)
				return null;
			return result;
		}

		protected Object[] getRawChildren(final Object parent) {
			try {
				fPendingGetChildren.add(parent);
				return super.getRawChildren(parent);
			} finally {
				fPendingGetChildren.remove(parent);
			}
		}

		public ISelection getSelection() {
			final IContentProvider cp = getContentProvider();
			if (!(cp instanceof IMultiElementTreeContentProvider))
				return super.getSelection();
			final Control control = getControl();
			if (control == null || control.isDisposed())
				return StructuredSelection.EMPTY;
			final Tree tree = getTree();
			final TreeItem[] selection = tree.getSelection();
			final List result = new ArrayList(selection.length);
			final List treePaths = new ArrayList();
			for (int i = 0; i < selection.length; i++) {
				final TreeItem item = selection[i];
				final Object element = getElement(item);
				if (element == null)
					continue;
				if (!result.contains(element))
					result.add(element);
				treePaths.add(createTreePath(item));
			}
			return new MultiElementSelection(this, result, (TreePath[]) treePaths.toArray(new TreePath[treePaths.size()]));
		}

		protected Object[] getSortedChildren(final Object parent) {
			final IParentAwareSorter sorter = getSorter() instanceof IParentAwareSorter ? (IParentAwareSorter) getSorter() : null;
			if (sorter != null)
				sorter.setParent(parent);
			try {
				return super.getSortedChildren(parent);
			} finally {
				if (sorter != null)
					sorter.setParent(null);
			}
		}

		protected void handleInvalidSelection(final ISelection invalidSelection, ISelection newSelection) {
			final IStructuredSelection is = (IStructuredSelection) invalidSelection;
			List ns = null;
			if (newSelection instanceof IStructuredSelection)
				ns = new ArrayList(((IStructuredSelection) newSelection).toList());
			else
				ns = new ArrayList();
			boolean changed = false;
			for (final Iterator iter = is.iterator(); iter.hasNext();) {
				final Object element = iter.next();
				if (element instanceof PHPProjectModel) {

					final IProject project = PHPWorkspaceModelManager.getInstance().getProjectForModel((PHPProjectModel) element);
					if (!project.isOpen()) {
						ns.add(project);
						changed = true;
					}
				} else if (element instanceof IProject) {
					final IProject project = (IProject) element;
					if (project.isOpen())
						changed = true;
				}
			}
			if (changed) {
				newSelection = new StructuredSelection(ns);
				setSelection(newSelection);
			}
			super.handleInvalidSelection(invalidSelection, newSelection);
		}

		/* Checks if a filtered object in essential (ie. is a parent that
		 * should not be removed).
		 */
		private boolean isEssential(final Object object) {
			if (object instanceof IContainer) {
				final IContainer folder = (IContainer) object;
				try {
					if (object instanceof IProject) {
						final IProject project = (IProject) object;
						if (project.isOpen() && ((IProject) object).hasNature(PHPNature.ID))
							return false;
					} else {
						final Object obj = filter(object, object, fViewer.getFilters());
						return obj != null && folder.members().length > 0;
					}
				} catch (final CoreException e) {
					return false;
				}
			}
			return false;
		}

		/*
		 * @see AbstractTreeViewer#isExpandable(java.lang.Object)
		 */
		public boolean isExpandable(final Object parent) {
			final ViewerFilter[] filters = fViewer.getFilters();
			final Object[] children = ((ITreeContentProvider) fViewer.getContentProvider()).getChildren(parent);
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
	}

	private ExplorerActionGroup fActionSet;
	protected ExplorerContentProvider fContentProvider;

	private Menu fContextMenu;
	private FilterUpdater fFilterUpdater;
	protected ExplorerLabelProvider fLabelProvider;
	private ISelection fLastOpenSelection;

	private boolean fLinkingEnabled;
	private final IPartListener fPartListener = new IPartListener() {
		public void partActivated(IWorkbenchPart part) {
			if (part instanceof IEditorPart)
				editorActivated((IEditorPart) part);
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

	private ISelectionChangedListener fPostSelectionListener;
	private int fRootMode = ExplorerViewActionGroup.SHOW_PROJECTS;
	private final LinkingSelectionListener fSelectionListener = new LinkingSelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			super.selectionChanged(part, selection);
		}

	};

	private PHPTreeViewer fViewer;

	private WorkingSetModel fWorkingSetModel;

	private String fWorkingSetName;

	public ExplorerPart() {
		fPostSelectionListener = new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handlePostSelectionChanged(event);
			}
		};
	}

	public void collapseAll() {
		try {
			fViewer.getControl().setRedraw(false);
			fViewer.collapseToLevel(getViewPartInput(), AbstractTreeViewer.ALL_LEVELS);
		} finally {
			fViewer.getControl().setRedraw(true);
		}
	}

	public ExplorerContentProvider createContentProvider() {
		final IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		final boolean showCUChildren = store.getBoolean(PreferenceConstants.SHOW_CU_CHILDREN);
		if (showProjects())
			return new ExplorerContentProvider(this, showCUChildren);
		return new WorkingSetAwareContentProvider(this, showCUChildren, fWorkingSetModel);
	}

	protected ExplorerLabelProvider createLabelProvider() {
		return new ExplorerLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | PHPElementLabels.P_COMPRESSED, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | PHPElementImageProvider.SMALL_ICONS | PHPElementImageProvider.OVERLAY_ICONS, fContentProvider);
	}

	public void createPartControl(final Composite parent) {
		fViewer = createViewer(parent);
		fSelectionListener.setViewer(getViewer());
		fViewer.setUseHashlookup(true);
		initDragAndDrop();

		setProviders();

		final MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		fContextMenu = menuMgr.createContextMenu(fViewer.getTree());
		fViewer.getTree().setMenu(fContextMenu);

		final IWorkbenchPartSite site = getSite();
		site.registerContextMenu(menuMgr, fViewer);
		site.setSelectionProvider(fViewer);
		site.getPage().addPartListener(fPartListener);

		initLinkingEnabled();
		fActionSet = new ExplorerActionGroup(this);
		if (fWorkingSetModel != null)
			fActionSet.getWorkingSetActionGroup().setWorkingSetModel(fWorkingSetModel);

		fViewer.setInput(findInputElement());

		initKeyListener();

		fViewer.addPostSelectionChangedListener(fPostSelectionListener);

		fViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(final DoubleClickEvent event) {
				fActionSet.handleDoubleClick(event);
			}
		});

		fViewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				fActionSet.handleOpen(event);
				fLastOpenSelection = event.getSelection();
			}
		});

		final IStatusLineManager slManager = getViewSite().getActionBars().getStatusLineManager();
		fViewer.addSelectionChangedListener(new StatusBarUpdater(slManager));

		fillActionBars();

		updateTitle();

		fFilterUpdater = new FilterUpdater(fViewer);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fFilterUpdater);

		// Syncing the package explorer has to be done here. It can't be done
		// when restoring the link state since the package explorers input isn't
		// set yet.
		if (isLinkingEnabled()) {
			final IEditorPart editor = getViewSite().getPage().getActiveEditor();
			if (editor != null)
				editorActivated(editor);
		}

	}

	private PHPTreeViewer createViewer(final Composite composite) {
		return new ExplorerTreeViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	private void createWorkingSetModel() {
		SafeRunner.run(new ISafeRunnable() {
			public void handleException(final Throwable exception) {
				fWorkingSetModel = new WorkingSetModel();
			}

			public void run() throws Exception {
				fWorkingSetModel = new WorkingSetModel();
			}
		});
	}

	public void dispose() {
		getSite().getPage().removePostSelectionListener(fSelectionListener);
		if (fContextMenu != null && !fContextMenu.isDisposed())
			fContextMenu.dispose();

		getSite().getPage().removePartListener(fPartListener);

		if (fActionSet != null)
			fActionSet.dispose();

		if (fFilterUpdater != null)
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fFilterUpdater);

		if (fWorkingSetModel != null)
			fWorkingSetModel.dispose();

		super.dispose();
	}

	void editorActivated(final IEditorPart editor) {
		if (!isLinkingEnabled())
			return;
		final Object input = getElementOfInput(editor.getEditorInput());
		if (input == null)
			return;
		if (!inputIsSelected(editor.getEditorInput()))
			showInput(input);
		else
			fViewer.getTree().showSelection();

	}

	private void fillActionBars() {
		final IActionBars actionBars = getViewSite().getActionBars();
		fActionSet.fillActionBars(actionBars);
	}

	private Object findInputElement() {
		if (showWorkingSets())
			return fWorkingSetModel;
		final Object input = getSite().getPage().getInput();
		if (input instanceof IWorkspace || input instanceof IWorkspaceRoot)
			return PHPWorkspaceModelManager.getInstance();
		else if (input instanceof IContainer)
			return input;
		return PHPWorkspaceModelManager.getInstance();
	}

	Object getElementOfInput(final IEditorInput input) {
		if (input instanceof IFileEditorInput)
			return ((IFileEditorInput) input).getFile();
		return null;
	}

	String getFrameName(final Object element) {
		if (element instanceof PHPCodeData)
			return ((PHPCodeData) element).getName();
		return fLabelProvider.getText(element);
	}

	public int getRootMode() {
		return fRootMode;
	}

	/**
	 * Returns the current selection.
	 */
	private ISelection getSelection() {
		return fViewer.getSelection();
	}

	String getToolTipText(final Object element) {
		String result;
		if (!(element instanceof IResource)) {
			if (element instanceof PHPWorkspaceModelManager)
				result = PHPUIMessages.PHPExplorerPart_workspace;
			else if (element instanceof PHPCodeData)
				result = PHPElementLabels.getTextLabel(element, AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS);
			else
				result = fLabelProvider.getText(element);
		} else {
			final IPath path = ((IResource) element).getFullPath();
			if (path.isRoot())
				result = PHPUIMessages.PHPExplorer_title;
			else
				result = path.makeRelative().toString();
		}

		if (fWorkingSetName == null)
			return result;

		final String wsstr = MessageFormat.format(PHPUIMessages.PHPExplorer_toolTip, new String[] { fWorkingSetName });
		if (result.length() == 0)
			return wsstr;
		return MessageFormat.format(PHPUIMessages.PHPExplorer_toolTip2, new String[] { result, fWorkingSetName });
	}

	public TreeViewer getViewer() {
		return fViewer;
	}

	public Object getViewPartInput() {
		if (fViewer != null)
			return fViewer.getInput();
		return null;
	}

	public WorkingSetModel getWorkingSetModel() {
		return fWorkingSetModel;
	}

	private void handlePostSelectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		// If the selection is the same as the one that triggered the last
		// open event then do nothing. The editor already got revealed.
		if (isLinkingEnabled() && !selection.equals(fLastOpenSelection))
			linkToEditor((IStructuredSelection) selection);
		fLastOpenSelection = null;
	}

	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (showWorkingSets())
			createWorkingSetModel();
	}

	private void initDrag() {
		final int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		final Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getInstance(), ResourceTransfer.getInstance(), FileTransfer.getInstance() };
		final TransferDragSourceListener[] dragListeners = new TransferDragSourceListener[] { new SelectionTransferDragAdapter(fViewer), new ResourceTransferDragAdapter(fViewer), new FileTransferDragAdapter(fViewer) };
		fViewer.addDragSupport(ops, transfers, new PHPViewerDragAdapter(fViewer, dragListeners));
	}

	private void initDragAndDrop() {
		initDrag();
		initDrop();
	}

	private void initDrop() {
		final int ops = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK | DND.DROP_DEFAULT;
		final Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getInstance(), FileTransfer.getInstance() };
		final TransferDropTargetListener[] dropListeners = new TransferDropTargetListener[] { new SelectionTransferDropAdapter(fViewer), new FileTransferDropAdapter(fViewer) };
		fViewer.addDropSupport(ops, transfers, new DelegatingDropAdapter(dropListeners));
	}

	private void initKeyListener() {
		fViewer.getControl().addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent event) {
				fActionSet.handleKeyEvent(event);
			}
		});
	}

	private void initLinkingEnabled() {
		setLinkingEnabled(PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.LINK_BROWSING_PROJECTS_TO_EDITOR));
	}

	private boolean inputIsSelected(final IEditorInput input) {
		final IStructuredSelection selection = (IStructuredSelection) fViewer.getSelection();
		if (selection.size() != 1)
			return false;
		IEditorInput selectionAsInput = null;
		selectionAsInput = EditorUtility.getEditorInput(selection.getFirstElement());
		return input.equals(selectionAsInput);
	}

	boolean isLinkingEnabled() {
		return fLinkingEnabled;
	}

	private void linkToEditor(final IStructuredSelection selection) {
		// ignore selection changes if the package explorer is not the active part.
		// In this case the selection change isn't triggered by a user.
		if (this != getSite().getPage().getActivePart())
			return;
		final Object obj = selection.getFirstElement();

		if (selection.size() == 1) {
			final IEditorPart part = EditorUtility.isOpenInEditor(obj);
			if (part != null) {
				final IWorkbenchPage page = getSite().getPage();
				page.bringToTop(part);
				if (obj instanceof PHPCodeData)
					EditorUtility.revealInEditor(part, (PHPCodeData) obj);
			}
		}
	}

	public void menuAboutToShow(final IMenuManager menu) {
		PHPUiPlugin.createStandardGroups(menu);

		fActionSet.setContext(new ActionContext(getSelection()));
		fActionSet.fillContextMenu(menu);
		fActionSet.setContext(null);
	}

	void projectStateChanged(final Object root) {
		final Control ctrl = fViewer.getControl();
		if (ctrl != null && !ctrl.isDisposed()) {
			fViewer.refresh(root, true);
			// trigger a syntetic selection change so that action refresh their
			// enable state.
			fViewer.setSelection(fViewer.getSelection());
		}
	}

	public void rootModeChanged(final int newMode) {
		fRootMode = newMode;
		if (showWorkingSets() && fWorkingSetModel == null) {
			createWorkingSetModel();
			if (fActionSet != null)
				fActionSet.getWorkingSetActionGroup().setWorkingSetModel(fWorkingSetModel);
		}
		final ISelection selection = fViewer.getSelection();
		final Object input = fViewer.getInput();
		final boolean isRootInputChange = PHPWorkspaceModelManager.getInstance().equals(input) || fWorkingSetModel != null && fWorkingSetModel.equals(input) || input instanceof IWorkingSet;
		try {
			fViewer.getControl().setRedraw(false);
			if (isRootInputChange)
				fViewer.setInput(null);
			setProviders();
			setSorter();
			fActionSet.getWorkingSetActionGroup().fillFilters(fViewer);
			if (isRootInputChange)
				fViewer.setInput(findInputElement());
			fViewer.setSelection(selection, true);
		} finally {
			fViewer.getControl().setRedraw(true);
		}
		if (isRootInputChange && fWorkingSetModel.needsConfiguration()) {
			final ConfigureWorkingSetAction action = new ConfigureWorkingSetAction(getSite());
			action.setWorkingSetModel(fWorkingSetModel);
			action.run();
			fWorkingSetModel.configured();
		}
	}

	public void setFocus() {
		fViewer.getTree().setFocus();

	}

	public void setLinkingEnabled(final boolean enabled) {
		fLinkingEnabled = enabled;
		PreferenceConstants.getPreferenceStore().setValue(PreferenceConstants.LINK_BROWSING_PROJECTS_TO_EDITOR, enabled);

		final IWorkbenchPartSite site = getSite();
		if (site == null)
			return;
		final IWorkbenchPage page = site.getPage();
		if (page == null)
			return;

		if (enabled) {
			page.addPostSelectionListener(fSelectionListener);
			final IEditorPart editor = page.getActiveEditor();
			if (editor != null)
				editorActivated(editor);
		} else
			page.removePostSelectionListener(fSelectionListener);
	}

	private void setProviders() {
		fContentProvider = createContentProvider();
		final IPHPTreeContentProvider[] treeProviders = TreeProvider.getTreeProviders(getViewSite().getId());
		fContentProvider.setTreeProviders(treeProviders);
		fViewer.setContentProvider(fContentProvider);

		fViewer.setSorter(new ExplorerSorter());

		fLabelProvider = createLabelProvider();
		fLabelProvider.setTreeProviders(treeProviders);
		fViewer.setLabelProvider(new DecoratingPHPLabelProvider(fLabelProvider, false));
	}

	private void setSorter() {
		if (showWorkingSets())
			fViewer.setSorter(new WorkingSetAwarePHPElementSorter());
		else
			fViewer.setSorter(new PHPElementSorter());
	}

	void setWorkingSetName(final String workingSetName) {
		fWorkingSetName = workingSetName;
	}

	boolean showInput(final Object element) {
		if (element != null) {
			final ISelection newSelection = new StructuredSelection(element);
			fViewer.setSelection(newSelection, true);
			//			if (fViewer.getSelection().equals(newSelection)) {
			//				fViewer.reveal(element);
			//			} else {
			//				try {
			//					fViewer.removePostSelectionChangedListener(fPostSelectionListener);
			//					fViewer.setSelection(newSelection, true);
			//
			//					while (element != null && fViewer.getSelection().isEmpty()) {
			//						// Try to select parent in case element is filtered
			//						element = getParent(element);
			//						if (element != null) {
			//							newSelection = new StructuredSelection(element);
			//							fViewer.setSelection(newSelection, true);
			//						}
			//					}
			//				} finally {
			//					fViewer.addPostSelectionChangedListener(fPostSelectionListener);
			//				}
			//			}
			return true;
		}
		return false;
	}

	/* package */boolean showProjects() {
		return fRootMode == ExplorerViewActionGroup.SHOW_PROJECTS;
	}

	/* package */boolean showWorkingSets() {
		return fRootMode == ExplorerViewActionGroup.SHOW_WORKING_SETS;
	}

	void updateTitle() {
		final Object input = fViewer.getInput();
		if (input == null || input instanceof PHPWorkspaceModelManager) {
			setContentDescription(""); //$NON-NLS-1$
			setTitleToolTip(""); //$NON-NLS-1$
		} else {
			final String inputText = PHPElementLabels.getTextLabel(input, AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS);
			setContentDescription(inputText);
			setTitleToolTip(getToolTipText(input));
		}
	}

}