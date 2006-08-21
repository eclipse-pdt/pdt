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
package org.eclipse.php.ui.projectOutline;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IContentProvider;
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
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.actions.OpenAction;
import org.eclipse.php.internal.ui.editor.LinkingSelectionListener;
import org.eclipse.php.internal.ui.util.MultiElementSelection;
import org.eclipse.php.internal.ui.util.TreePath;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.php.ui.explorer.IMultiElementTreeContentProvider;
import org.eclipse.php.ui.explorer.PHPTreeViewer;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.php.ui.treecontent.TreeProvider;
import org.eclipse.php.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.ui.util.DecoratingPHPLabelProvider;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.php.ui.util.PHPElementComparer;
import org.eclipse.php.ui.util.PHPElementImageProvider;
import org.eclipse.php.ui.util.PHPElementLabels;
import org.eclipse.php.ui.util.StatusBarUpdater;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

public class ProjectOutlinePart extends ViewPart implements IMenuListener {

	private class ProjectOutlineTreeViewer extends PHPTreeViewer {
		java.util.List fPendingGetChildren;

		public ProjectOutlineTreeViewer(final Composite parent, final int style) {
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
			for (int i = 0; i < filters.length; i++) {
				final ViewerFilter filter = filters[i];
				if (!filter.select(fViewer, parent, object))
					return null;
			}
			return object;
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

		/*
		 * @see org.eclipse.jface.viewers.StructuredViewer#filter(java.lang.Object)
		 */
		protected Object[] getFilteredChildren(final Object parent) {
			final List list = new ArrayList();
			final ViewerFilter[] filters = fViewer.getFilters();
			if (fViewer.getContentProvider() == null)
				return new Object[0];

			final Object[] children = ((ITreeContentProvider) fViewer.getContentProvider()).getChildren(parent);
			for (int i = 0; children != null && i < children.length; i++) {
				Object object = children[i];
				if (!isEssential(object)) {
					object = filter(object, parent, filters);
					if (object != null)
						list.add(object);
				} else
					list.add(object);
			}
			return list.toArray();
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
				setSelection(newSelection, true);
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
					return folder.members().length > 0;
				} catch (final CoreException e) {
					e.printStackTrace();
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
	class UpdateViewJob extends Job {//implements Runnable {

		public UpdateViewJob() {
			super("updateViewJob");
			setSystem(true);
		}

		protected IStatus run(final IProgressMonitor monitor) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					getViewer().setInput(currentProject);
				}
			});
			return Status.OK_STATUS;
		}

	}

	protected ProjectOutlineViewGroup actionGroup;

	protected IProject currentProject;
	protected ProjectOutlineContentProvider fContentProvider;
	private Menu fContextMenu;

	protected ProjectOutlineLabelProvider fLabelProvider;

	private ISelection fLastOpenSelection;
	private boolean fLinkingEnabled;
	private final IPartListener fPartListener = new IPartListener() {
		public void partActivated(IWorkbenchPart part) {
			if (getViewer().getTree().getVisible() && part instanceof PHPStructuredEditor)
				updateInputForCurrentEditor((IEditorPart) part);
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

	private final LinkingSelectionListener fSelectionListener = new LinkingSelectionListener() {

		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (structuredSelection.size() > 0) {
					Object firstElement = structuredSelection.getFirstElement();
					if (firstElement instanceof IProject) {
						setProject((IProject) structuredSelection.getFirstElement());
						return;
					}
				}
			}
			super.selectionChanged(part, selection);
		}
	};

	protected PHPTreeViewer fViewer;

	private String fWorkingSetName;

	OpenAction openEditorAction;

	private boolean showAll = false;

	private UpdateViewJob updateViewJob;

	public ProjectOutlinePart() {
		fPostSelectionListener = new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				handlePostSelectionChanged(event);
			}
		};
	}

	private void addMouseTrackListener() {
		final Tree tree = fViewer.getTree();
		tree.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseHover(final MouseEvent e) {
				final TreeItem item = tree.getItem(new Point(e.x, e.y));
				if (item != null) {
					final Object o = item.getData();
					if (o instanceof PHPCodeData)
						tree.setToolTipText(fLabelProvider.getTooltipText(o));
				}
			}

		});
	}

	public void collapseAll() {
		try {
			fViewer.getControl().setRedraw(false);
			fViewer.collapseToLevel(getViewPartInput(), AbstractTreeViewer.ALL_LEVELS);
		} finally {
			fViewer.getControl().setRedraw(true);
		}
	}

	protected ProjectOutlineViewGroup createActionGroup() {
		return new ProjectOutlineViewGroup(this);
	}

	public ProjectOutlineContentProvider createContentProvider() {
		final IPreferenceStore store = PreferenceConstants.getPreferenceStore();
		final boolean showCUChildren = store.getBoolean(PreferenceConstants.SHOW_CU_CHILDREN);
		return new ProjectOutlineContentProvider(this, showCUChildren);
	}

	protected ProjectOutlineLabelProvider createLabelProvider() {
		return new ProjectOutlineLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | PHPElementLabels.M_PARAMETER_NAMES, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS | PHPElementImageProvider.SMALL_ICONS | PHPElementImageProvider.OVERLAY_ICONS, fContentProvider);
	}

	public void createPartControl(final Composite parent) {
		fViewer = createViewer(parent);
		fSelectionListener.setViewer(getViewer());
		fSelectionListener.setResetEmptySelection(true);
		setProviders();
		fViewer.setUseHashlookup(true);

		setUpPopupMenu();
		initLinkingEnabled();
		actionGroup = createActionGroup();

		fViewer.addPostSelectionChangedListener(fPostSelectionListener);
		addMouseTrackListener();
		fViewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				fLastOpenSelection = event.getSelection();
				openEditorAction.run((IStructuredSelection) fLastOpenSelection);
			}
		});
		getSite().getPage().addPartListener(fPartListener);

		final IStatusLineManager slManager = getViewSite().getActionBars().getStatusLineManager();
		fViewer.addSelectionChangedListener(new StatusBarUpdater(slManager));
		updateTitle();

		final IEditorPart editorPart = getViewSite().getPage().getActiveEditor();
		updateInputForCurrentEditor(editorPart);

		openEditorAction = new OpenAction(getSite());
		fillActionBars();

		// refresh linking:
		setLinkingEnabled(isLinkingEnabled());

		fViewer.refresh();
		PHPWorkspaceModelManager.getInstance().addModelListener(fContentProvider);

	}

	private PHPTreeViewer createViewer(final Composite composite) {
		return new ProjectOutlineTreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	public void dispose() {
		if (fContextMenu != null && !fContextMenu.isDisposed())
			fContextMenu.dispose();
		getSite().getPage().removePartListener(fPartListener);
		getSite().getPage().removePostSelectionListener(fSelectionListener);
		PHPWorkspaceModelManager.getInstance().removeModelListener(fContentProvider);
		super.dispose();
	}

	void editorActivated(final IEditorPart editor) {
	}

	private void fillActionBars() {
		final IActionBars actionBars = getViewSite().getActionBars();
		actionGroup.fillActionBars(actionBars);
	}

	String getFrameName(final Object element) {
		if (element instanceof PHPCodeData)
			return ((PHPCodeData) element).getName();
		return fLabelProvider.getText(element);
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

	private void handlePostSelectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		// If the selection is the same as the one that triggered the last
		// open event then do nothing. The editor already got revealed.
		if (isLinkingEnabled() && !selection.equals(fLastOpenSelection))
			linkToEditor((IStructuredSelection) selection);
		fLastOpenSelection = selection;
	}

	public void handleUpdateInput(final IEditorPart editorPart) {
		IProject project = null;

		if (editorPart != null)
			if (editorPart instanceof PHPStructuredEditor) {
				final PHPStructuredEditor phpEditor = (PHPStructuredEditor) editorPart;
				final IFile file = phpEditor.getFile();
				project = file.getProject();
			} else {
				final IEditorInput editorInput = editorPart.getEditorInput();
				if (editorInput instanceof FileEditorInput) {
					final FileEditorInput fileEditorInput = (FileEditorInput) editorInput;
					project = fileEditorInput.getFile().getProject();
				}
			}
		setProject(project);
	}

	private void initLinkingEnabled() {
		setLinkingEnabled(PreferenceConstants.getPreferenceStore().getBoolean(PreferenceConstants.LINK_BROWSING_PROJECTS_TO_EDITOR));
	}

	public boolean isInCurrentProject(final Object element) {
		if (currentProject != null)
			return currentProject.equals(PHPModelUtil.getResource(element).getProject());
		return false;
	}

	boolean isLinkingEnabled() {
		return fLinkingEnabled;
	}

	public boolean isShowAll() {
		return showAll;
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
		actionGroup.setContext(new ActionContext(fViewer.getSelection()));
		actionGroup.fillContextMenu(menu);
		actionGroup.setContext(null);
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

	public void setProject(final IProject project) {
		if (project == currentProject)
			return;
		currentProject = project;
		if (updateViewJob == null)
			updateViewJob = new UpdateViewJob();
		updateViewJob.schedule();
		actionGroup.updateActions();

	}

	private void setProviders() {
		fContentProvider = createContentProvider();
		final IPHPTreeContentProvider[] treeProviders = TreeProvider.getTreeProviders(getViewSite().getId());
		fContentProvider.setTreeProviders(treeProviders);
		fViewer.setContentProvider(fContentProvider);

		fLabelProvider = createLabelProvider();
		fLabelProvider.setTreeProviders(treeProviders);
		fViewer.setLabelProvider(new DecoratingPHPLabelProvider(fLabelProvider, false));
	}

	public void setShowAll(final boolean showAll) {
		if (showAll != this.showAll) {
			this.showAll = showAll;
			if (updateViewJob == null)
				updateViewJob = new UpdateViewJob();
			updateViewJob.schedule();
		}
	}

	private void setUpPopupMenu() {

		final MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		fContextMenu = menuMgr.createContextMenu(fViewer.getTree());
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(final IMenuManager mgr) {
				final ISelection selection = fViewer.getSelection();
				if (!selection.isEmpty()) {
					final IStructuredSelection s = (IStructuredSelection) selection;
					if (s.getFirstElement() instanceof PHPFunctionData) {
						//						mgr.add(action);
					}
				}
			}
		});
		fViewer.getTree().setMenu(fContextMenu);
		final IWorkbenchPartSite site = getSite();
		site.registerContextMenu(menuMgr, fViewer);
		site.setSelectionProvider(fViewer);
	}

	private void updateInputForCurrentEditor(final IEditorPart editorPart) {
		handleUpdateInput(editorPart);
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
