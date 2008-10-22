/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import java.lang.reflect.Array;
import java.util.*;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.Model;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ProjectFragmentContainer;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.dltk.internal.ui.scriptview.LibraryContainer;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkingSet;

/**
 * Content provider for the PackageExplorer.
 * 
 * <p>
 * Since 2.1 this content provider can provide the children for flat or
 * hierarchical layout.
 * </p>
 * 
 * @see org.eclipse.jdt.ui.StandardJavaElementContentProvider
 */
public class ProjectOutlineContentProvider extends ScriptExplorerContentProvider implements ITreeContentProvider, IElementChangedListener, IPropertyChangeListener {

		private static final Image CLASSES_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_CLASSES_GROUP.createImage();
		private static final Image CONSTANTS_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage();
		private static final Image FUNCTIONS_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP.createImage();

		public enum ProjectOutlineGroups {
			
			GROUP_CLASSES(CLASSES_GROUP_IMAGE, "Classes" ), 
			GROUP_CONSTANTS(CONSTANTS_GROUP_IMAGE, "Constants"),
			GROUP_FUNCTIONS(FUNCTIONS_GROUP_IMAGE, "Functions");
			
			private Image image;
			private String text;

			ProjectOutlineGroups(Image image, String text){
				this.image = image;
				this.text = text;
			}
			
			public Image getImage() {
				return image;
			}

			public String getText() {
				return text;
			}
			
			protected Object[] getChildren() {
				TreeSet<IModelElement> childrenList = new TreeSet<IModelElement>(new Comparator<IModelElement>() {
					public int compare(IModelElement o1, IModelElement o2) {
						return (o1.getElementName().compareTo(o2.getElementName()));

					}
				});

				if (scripProject != null && scripProject instanceof IScriptProject) {
					IScriptProject scriptProject = (IScriptProject) scripProject;
					ArrayList<IProjectFragment> projectFragments = new ArrayList<IProjectFragment>();

					// getting project's fragments
					try {
						for (IProjectFragment projectFragment : (scriptProject.getProjectFragments())) {
							if (!projectFragment.isExternal()) { //adding only non-external resources
								projectFragments.add(projectFragment);
							}
						}
					} catch (ModelException e) {
						Logger.logException(e);
					}

					// foreach fragment getting its children, 
					// and then merging them into last fragments list
					for (IProjectFragment projectFragment : projectFragments) {
						IModelElement[] children = null;

						switch (this) {
							case GROUP_CLASSES:
								children = OutlineUtils.getGlobalClasses(projectFragment, "", false);
								break;

							case GROUP_FUNCTIONS:
								children = OutlineUtils.getGlobalFunctions(projectFragment, "", false);
								break;

							case GROUP_CONSTANTS:
								children = OutlineUtils.getGlobalConstants(projectFragment, "", false);
								break;
						}

						childrenList.addAll(Arrays.asList(children));
						if (null != children) {
							for (IModelElement child : children) {
								childrenList.add(child);
							}
						}
					}
				}

				if (null == childrenList || childrenList.isEmpty())
					return StandardModelElementContentProvider.NO_CHILDREN;
				return childrenList.toArray();
			}



		}


	protected static final int ORIGINAL = 0;
	protected static final int PARENT = 1 << 0;
	protected static final int GRANT_PARENT = 1 << 1;
	protected static final int PROJECT = 1 << 2;

	private TreeViewer fViewer;
	private Object fInput;
	private boolean fIsFlatLayout;
	private boolean fShowLibrariesNode;
	private boolean fFoldPackages;
	static IScriptProject scripProject = null;


	private Collection fPendingUpdates;
	private boolean showGroups;
	private ProjectOutlineGroups[] fProjectOutlineGroups;

	/**
	 * Creates a new content provider for Java elements.
	 * 
	 * @param provideMembers
	 *            if set, members of compilation units and class files are shown
	 */
	public ProjectOutlineContentProvider(final boolean provideMembers) {
		super(provideMembers);
		fShowLibrariesNode = false;
		fIsFlatLayout = false;
		fFoldPackages = arePackagesFoldedInHierarchicalLayout();
		fPendingUpdates = null;
		showGroups = true;//FIXME : PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(ShowGroupsAction.PREF_SHOW_GROUPS);

		DLTKUIPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	private boolean arePackagesFoldedInHierarchicalLayout() {
		return getPreferenceStore().getBoolean(PreferenceConstants.APPEARANCE_FOLD_PACKAGES_IN_PACKAGE_EXPLORER);
	}

	protected IPreferenceStore getPreferenceStore() {
		return DLTKUIPlugin.getDefault().getPreferenceStore();
	}

	protected Object getViewerInput() {
		return fInput;
	}

	/*
	 * (non-Javadoc) Method declared on IElementChangedListener.
	 */
	public void elementChanged(final ElementChangedEvent event) {
		final ArrayList runnables = new ArrayList();
		try {
			// 58952 delete project does not update Package Explorer [package
			// explorer]
			// if the input to the viewer is deleted then refresh to avoid the
			// display of stale elements
			if (inputDeleted(runnables)) {
				return;
			}

			processDelta(event.getDelta(), runnables);
		} catch (ModelException e) {
			DLTKUIPlugin.log(e);
		} finally {
			//FIXME : fviewer is somehow null (nirc) 
			executeRunnables(runnables);
		}
	}

	/*protected final void executeRunnables(final Collection runnables) {

		// now post all collected runnables
		Control ctrl = fViewer.getControl();
		if (ctrl != null && !ctrl.isDisposed()) {
			// Are we in the UIThread? If so spin it until we are done
			if (ctrl.getDisplay().getThread() == Thread.currentThread()) {
				runUpdates(runnables);
			} else {
				synchronized (this) {
					if (fPendingUpdates == null) {
						fPendingUpdates = runnables;
					} else {
						fPendingUpdates.addAll(runnables);
					}
				}
				ctrl.getDisplay().asyncExec(new Runnable() {
					public void run() {
						runPendingUpdates();
					}
				});
			}
		}
	}*/

	/**
	 * Run all of the runnables that are the widget updates. Must be called in
	 * the display thread.
	 */
	public void runPendingUpdates() {
		Collection pendingUpdates;
		synchronized (this) {
			pendingUpdates = fPendingUpdates;
			fPendingUpdates = null;
		}
		if (pendingUpdates != null && fViewer != null) {
			Control control = fViewer.getControl();
			if (control != null && !control.isDisposed()) {
				runUpdates(pendingUpdates);
			}
		}
	}

	private void runUpdates(final Collection runnables) {
		Iterator runnableIterator = runnables.iterator();
		while (runnableIterator.hasNext()) {
			((Runnable) runnableIterator.next()).run();
		}
	}

	private boolean inputDeleted(final Collection runnables) {
		if (fInput == null) {
			return false;
		}
		if (fInput instanceof IModelElement && ((IModelElement) fInput).exists()) {
			return false;
		}
		if (fInput instanceof IResource && ((IResource) fInput).exists()) {
			return false;
		}
		if (fInput instanceof WorkingSetModel) {
			return false;
		}
		if (fInput instanceof IWorkingSet) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=156239
			return false;
		}
		postRefresh(fInput, ProjectOutlineContentProvider.ORIGINAL, fInput, runnables);
		return true;
	}

	/*
	 * (non-Javadoc) Method declared on IContentProvider.
	 */
	public void dispose() {
		super.dispose();
		DLTKCore.removeElementChangedListener(this);
		DLTKUIPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.StandardJavaElementContentProvider#getPackageFragmentRootContent(org.eclipse.jdt.core.IPackageFragmentRoot)
	 */
	protected Object[] getProjectFragmentContent(final IProjectFragment root) throws ModelException {
		if (fIsFlatLayout) {
			return super.getProjectFragmentContent(root);
		}

		// hierarchical package mode
		ArrayList result = new ArrayList();
		getHierarchicalPackageChildren(root, null, result);
		if (!isProjectProjectFragment(root)) {
			Object[] nonJavaResources = root.getForeignResources();
			for (int i = 0; i < nonJavaResources.length; i++) {
				result.add(nonJavaResources[i]);
			}
		}
		return result.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.StandardJavaElementContentProvider#getPackageContent(org.eclipse.jdt.core.IPackageFragment)
	 */
	protected Object[] getScriptFolderContent(final IScriptFolder fragment) throws ModelException {
		if (fIsFlatLayout) {
			return super.getScriptFolderContent(fragment);
		}

		// hierarchical package mode
		ArrayList result = new ArrayList();

		getHierarchicalPackageChildren((IProjectFragment) fragment.getParent(), fragment, result);
		Object[] nonPackages = super.getScriptFolderContent(fragment);
		if (result.isEmpty()) {
			return nonPackages;
		}
		for (int i = 0; i < nonPackages.length; i++) {
			result.add(nonPackages[i]);
		}
		return result.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.StandardJavaElementContentProvider#getFolderContent(org.eclipse.core.resources.IFolder)
	 */
	protected Object[] getFolderContent(final IFolder folder) throws CoreException {
		if (fIsFlatLayout) {
			return super.getFolderContent(folder);
		}

		// hierarchical package mode
		ArrayList result = new ArrayList();

		getHierarchicalPackagesInFolder(folder, result);
		Object[] others = super.getFolderContent(folder);
		if (result.isEmpty()) {
			return others;
		}
		for (int i = 0; i < others.length; i++) {
			result.add(others[i]);
		}
		return result.toArray();
	}

	public Object[] getChildren(final Object element) {
	
		if (element instanceof IScriptProject){
			scripProject = (IScriptProject) element;
			return getGroupNodes();
		}
		if (element instanceof Model) {			
			return new Object[] {getViewerInput()} ; 
		}
		if (element instanceof ProjectOutlineGroups)
			return ((ProjectOutlineGroups) element).getChildren();

		return super.getChildren(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ProjectOutlineGroups)
			return true;

		return super.hasChildren(element);
	}


	ProjectOutlineGroups[] getGroupNodes() {

		if (showGroups ) {
			if (fProjectOutlineGroups == null)
				fProjectOutlineGroups = ProjectOutlineGroups.values();
		} else
			fProjectOutlineGroups = new ProjectOutlineGroups[0];
		return fProjectOutlineGroups;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.StandardJavaElementContentProvider#getPackageFragmentRoots(org.eclipse.jdt.core.IJavaProject)
	 */
	protected Object[] getProjectFragments(final IScriptProject project) throws ModelException {
		if (!project.getProject().isOpen()) {
			return StandardModelElementContentProvider.NO_CHILDREN;
		}

		List result = new ArrayList();

		boolean addZIPContainer = false;

		IProjectFragment[] roots = project.getProjectFragments();
		for (int i = 0; i < roots.length; i++) {
			IProjectFragment root = roots[i];
			IBuildpathEntry classpathEntry = root.getRawBuildpathEntry();
			if (classpathEntry == null) {
				continue;
			}
			int entryKind = classpathEntry.getEntryKind();
			if (entryKind == IBuildpathEntry.BPE_CONTAINER) {
				// all ClassPathContainers are added later
			} else if (fShowLibrariesNode && (entryKind == IBuildpathEntry.BPE_LIBRARY /*
																																									 * ||
																																									 * entryKind ==
																																									 * IBuildpathEntry.BPE_VARIABLE
																																									 */)) {
				addZIPContainer = true;
			} else {
				if (isProjectProjectFragment(root)) {
					// filter out package fragments that correspond to projects
					// and
					// replace them with the package fragments directly
					Object[] fragments = getProjectFragmentContent(root);
					for (int j = 0; j < fragments.length; j++) {
						result.add(fragments[j]);
					}
				} else {
					result.add(root);
				}
			}
		}

		if (addZIPContainer) {
			result.add(new LibraryContainer(project));
		}

		// separate loop to make sure all containers are on the classpath
		IBuildpathEntry[] rawBuidspath = project.getRawBuildpath();
		for (int i = 0; i < rawBuidspath.length; i++) {
			IBuildpathEntry classpathEntry = rawBuidspath[i];
			if (classpathEntry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
				result.add(new BuildPathContainer(project, classpathEntry));
			}
		}
		Object[] resources = project.getForeignResources();
		for (int i = 0; i < resources.length; i++) {
			result.add(resources[i]);
		}
		return result.toArray();
	}

	private Object[] getContainerProjectFragments(final ProjectFragmentContainer container) {
		return container.getChildren();
	}

	private Object[] getNonJavaProjects(final IScriptModel model) throws ModelException {
		return model.getForeignResources();
	}

	protected Object internalGetParent(final Object element) {
		if (!fIsFlatLayout && element instanceof IScriptFolder) {
			return getHierarchicalPackageParent((IScriptFolder) element);
		} else if (element instanceof IProjectFragment) {
			// since we insert logical package containers we have to fix
			// up the parent for package fragment roots so that they refer
			// to the container and containers refer to the project
			IProjectFragment root = (IProjectFragment) element;

			try {
				IBuildpathEntry entry = root.getRawBuildpathEntry();
				if (entry != null) {
					int entryKind = entry.getEntryKind();
					if (entryKind == IBuildpathEntry.BPE_CONTAINER) {
						return new BuildPathContainer(root.getScriptProject(), entry);
					} else if (fShowLibrariesNode && (entryKind == IBuildpathEntry.BPE_LIBRARY /*
																																																																			 * ||
																																																																			 * entryKind ==
																																																																			 * IBuildpathEntry.BPE_VARIABLE
																																																																			 */)) {
						return new LibraryContainer(root.getScriptProject());
					}
				}
			} catch (ModelException e) {
				// fall through
			}
		} else if (element instanceof ProjectFragmentContainer) {
			return ((ProjectFragmentContainer) element).getScriptProject();
		}
		return super.internalGetParent(element);
	}

	/*
	 * (non-Javadoc) Method declared on IContentProvider.
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		if (null != newInput && newInput instanceof Model){
			try {
				newInput = ((Model)newInput).getScriptProjects()[0];
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		fViewer = (TreeViewer) viewer;
		super.inputChanged(viewer, oldInput, newInput);
		if (oldInput == null && newInput != null ) {
			DLTKCore.addElementChangedListener(this);
		} else if (oldInput != null && newInput == null) {
			DLTKCore.removeElementChangedListener(this);
		}

		if (fInput == null || !fInput.equals(newInput) )
			fInput = newInput;
	}

	// hierarchical packages
	/**
	 * Returns the hierarchical packages inside a given fragment or root.
	 * 
	 * @param parent
	 *            The parent package fragment root
	 * @param fragment
	 *            The package to get the children for or 'null' to get the
	 *            children of the root.
	 * @param result
	 *            Collection where the resulting elements are added
	 * @throws JavaModelException
	 */
	private void getHierarchicalPackageChildren(final IProjectFragment parent, final IScriptFolder fragment, final Collection result) throws ModelException {
		IModelElement[] children = parent.getChildren();

		List newElements = new ArrayList();
		if (fragment == null || fragment.isRootFolder()) {
			for (int i = 0; i < children.length; ++i) {
				if (children[i] instanceof IScriptFolder) {
					IScriptFolder scriptFolder = ((IScriptFolder) children[i]);
					if (scriptFolder.isRootFolder()) {
						IModelElement[] members = scriptFolder.getChildren();
						for (int j = 0; j < members.length; ++j) {
							newElements.add(members[j]);
						}
						continue;
					}
				}
				newElements.add(children[i]);
			}
			children = (IModelElement[]) newElements.toArray(new IModelElement[newElements.size()]);
		}

		String prefix = fragment != null ? fragment.getElementName() + IScriptFolder.PACKAGE_DELIMETER_STR : ""; //$NON-NLS-1$
		int prefixLen = prefix.length();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof IScriptFolder) {
				IScriptFolder curr = (IScriptFolder) children[i];
				String name = curr.getElementName();
				if (name.startsWith(prefix) && name.length() > prefixLen && name.indexOf(IScriptFolder.PACKAGE_DELIMITER, prefixLen) == -1) {
					if (fFoldPackages) {
						curr = ProjectOutlineContentProvider.getFolded(children, curr);
					}
					result.add(curr);
				} /*
																																									 * else if (fragment == null && curr.isRootFolder()) {
																																									 * result.add(curr); }
																																									 */
			} else {
				result.add(children[i]);
			}
		}
	}

	/**
	 * Returns the hierarchical packages inside a given folder.
	 * 
	 * @param folder
	 *            The parent folder
	 * @param result
	 *            Collection where the resulting elements are added
	 * @throws CoreException
	 *             thrown when elements could not be accessed
	 */
	private void getHierarchicalPackagesInFolder(final IFolder folder, final Collection result) throws CoreException {
		IResource[] resources = folder.members();
		for (int i = 0; i < resources.length; i++) {
			IResource resource = resources[i];
			if (resource instanceof IFolder) {
				IFolder curr = (IFolder) resource;
				IModelElement element = DLTKCore.create(curr);
				if (element instanceof IScriptFolder) {
					if (fFoldPackages) {
						IScriptFolder fragment = (IScriptFolder) element;
						IProjectFragment root = (IProjectFragment) fragment.getParent();
						element = ProjectOutlineContentProvider.getFolded(root.getChildren(), fragment);
					}
					result.add(element);
				}
			}
		}
	}

	public Object getHierarchicalPackageParent(final IScriptFolder child) {
		String name = child.getElementName();
		IProjectFragment parent = (IProjectFragment) child.getParent();
		int index = name.lastIndexOf(IScriptFolder.PACKAGE_DELIMITER);
		if (index != -1) {
			String realParentName = name.substring(0, index);
			IScriptFolder element = parent.getScriptFolder(realParentName);
			if (element.exists()) {
				try {
					if (fFoldPackages && ProjectOutlineContentProvider.isEmpty(element) && ProjectOutlineContentProvider.findSinglePackageChild(element, parent.getChildren()) != null) {
						return getHierarchicalPackageParent(element);
					}
				} catch (ModelException e) {
					// ignore
				}
				return element;
			} else { // bug 65240
				IResource resource = element.getResource();
				if (resource != null) {
					return resource;
				}
			}
		}
		if (parent.getResource() instanceof IProject) {
			return parent.getScriptProject();
		}
		return parent;
	}

	private static IScriptFolder getFolded(final IModelElement[] children, IScriptFolder pack) throws ModelException {
		while (ProjectOutlineContentProvider.isEmpty(pack)) {
			IScriptFolder collapsed = ProjectOutlineContentProvider.findSinglePackageChild(pack, children);
			if (collapsed == null) {
				return pack;
			}
			pack = collapsed;
		}
		return pack;
	}

	private static boolean isEmpty(final IScriptFolder fragment) throws ModelException {
		return !fragment.containsScriptResources() && fragment.getForeignResources().length == 0;
	}

	private static IScriptFolder findSinglePackageChild(final IScriptFolder fragment, final IModelElement[] children) {
		String prefix = fragment.getElementName() + IScriptFolder.PACKAGE_DELIMITER;
		int prefixLen = prefix.length();
		IScriptFolder found = null;
		for (int i = 0; i < children.length; i++) {
			IModelElement element = children[i];
			String name = element.getElementName();
			if (name.startsWith(prefix) && name.length() > prefixLen && name.indexOf(IScriptFolder.PACKAGE_DELIMITER, prefixLen) == -1) {
				if (found == null) {
					found = (IScriptFolder) element;
				} else {
					return null;
				}
			}
		}
		return found;
	}

	// ------ delta processing ------

	/**
	 * Processes a delta recursively. When more than two children are affected
	 * the tree is fully refreshed starting at this node.
	 * 
	 * @param delta
	 *            the delta to process
	 * @param runnables
	 *            the resulting view changes as runnables (type {@link Runnable})
	 * @return true is returned if the conclusion is to refresh a parent of an
	 *         element. In that case no siblings need to be processed
	 * @throws JavaModelException
	 *             thrown when the access to an element failed
	 */
	private boolean processDelta(final IModelElementDelta delta, final Collection runnables) throws ModelException {

		int kind = delta.getKind();
		int flags = delta.getFlags();
		IModelElement element = delta.getElement();
		int elementType = element.getElementType();

		if (elementType != IModelElement.SCRIPT_MODEL && elementType != IModelElement.SCRIPT_PROJECT) {
			IScriptProject proj = element.getScriptProject();
			if (proj == null || !proj.getProject().isOpen()) {
				// needed if
				// parent
				// already did
				// the 'open'
				// check!
				return false;
			}
		}

		if (!fIsFlatLayout && elementType == IModelElement.SCRIPT_FOLDER) {
			if (kind == IModelElementDelta.REMOVED) {
				final Object parent = getHierarchicalPackageParent((IScriptFolder) element);
				if (parent instanceof IProjectFragment) {
					postRemove(element, runnables);
					return false;
				} else {
					postRefresh(internalGetParent(parent), ProjectOutlineContentProvider.GRANT_PARENT, element, runnables);
					return true;
				}
			} else if (kind == IModelElementDelta.ADDED) {
				final Object parent = getHierarchicalPackageParent((IScriptFolder) element);
				if (parent instanceof IProjectFragment) {
					postAdd(parent, element, runnables);
					return false;
				} else {
					postRefresh(internalGetParent(parent), ProjectOutlineContentProvider.GRANT_PARENT, element, runnables);
					return true;
				}
			}
			handleAffectedChildren(delta, element, runnables);
			return false;
		}

		if (elementType == IModelElement.SOURCE_MODULE) {
			ISourceModule cu = (ISourceModule) element;
			if (!ScriptModelUtil.isPrimary(cu)) {
				return false;
			}

			if (!getProvideMembers() && cu.isWorkingCopy() && kind == IModelElementDelta.CHANGED) {
				return false;
			}

			if (kind == IModelElementDelta.CHANGED && !ProjectOutlineContentProvider.isStructuralCUChange(flags)) {
				return false; // test moved ahead
			}

			if (!isOnClassPath(cu)) { // TODO: isOnClassPath expensive! Should
				// be put after all cheap tests
				return false;
			}

		}

		if (elementType == IModelElement.SCRIPT_PROJECT) {
			// handle open and closing of a project
			if ((flags & (IModelElementDelta.F_CLOSED | IModelElementDelta.F_OPENED)) != 0) {
				postRefresh(element, ProjectOutlineContentProvider.ORIGINAL, element, runnables);
				return false;
			}
			// if the class path has changed we refresh the entire project
			if ((flags & IModelElementDelta.F_BUILDPATH_CHANGED) != 0) {
				postRefresh(element, ProjectOutlineContentProvider.ORIGINAL, element, runnables);
				return false;
			}
			// if added it could be that the corresponding IProject is already
			// shown. Remove it first.
			// bug 184296
			//FIXME : if (kind == IModelElementDelta.ADDED ) {

			if (kind == IModelElementDelta.ADDED || kind == IModelElementDelta.CHANGED) {
				postRemove(element.getResource(), runnables);
				postAdd(element.getParent(), element, runnables);
				return false;
			}
		}

		if (kind == IModelElementDelta.REMOVED) {
			Object parent = internalGetParent(element);
			if (element instanceof IScriptFolder) {
				// refresh package fragment root to allow filtering empty
				// (parent) packages: bug 72923
				if (fViewer.testFindItem(parent) != null) {
					postRefresh(parent, ProjectOutlineContentProvider.PARENT, element, runnables);
				}
				return true;
			}

			postRemove(element, runnables);
			if (parent instanceof IScriptFolder) {
				postUpdateIcon((IScriptFolder) parent, runnables);
			}
			// we are filtering out empty subpackages, so we
			// a package becomes empty we remove it from the viewer.
			if (isScriptFolderEmpty(element.getParent())) {
				if (fViewer.testFindItem(parent) != null) {
					postRefresh(internalGetParent(parent), ProjectOutlineContentProvider.GRANT_PARENT, element, runnables);
				}
				return true;
			}
			return false;
		}

		if (kind == IModelElementDelta.ADDED) {
			Object parent = internalGetParent(element);
			// we are filtering out empty subpackages, so we
			// have to handle additions to them specially.
			if (parent instanceof IScriptFolder) {
				Object grandparent = internalGetParent(parent);
				// 1GE8SI6: ITPJUI:WIN98 - Rename is not shown in Packages View
				// avoid posting a refresh to an invisible parent
				if (parent.equals(fInput)) {
					postRefresh(parent, ProjectOutlineContentProvider.PARENT, element, runnables);
				} else {
					// refresh from grandparent if parent isn't visible yet
					if (fViewer.testFindItem(parent) == null) {
						postRefresh(grandparent, ProjectOutlineContentProvider.GRANT_PARENT, element, runnables);
					} else {
						postRefresh(parent, ProjectOutlineContentProvider.PARENT, element, runnables);
					}
				}
				return true;
			} else {
				postAdd(parent, element, runnables);
			}
		}

		if (elementType == IModelElement.SOURCE_MODULE) {
			if (kind == IModelElementDelta.CHANGED) {
				// isStructuralCUChange already performed above
				postRefresh(element, ProjectOutlineContentProvider.ORIGINAL, element, runnables);
				updateSelection(delta, runnables);
			}
			return false;
		}
		// no changes possible in class files
		// if (elementType == IModelElement.CLASS_FILE) {
		// return false;
		// }

		if (elementType == IModelElement.PROJECT_FRAGMENT) {
			// the contents of an external JAR has changed
			if ((flags & IModelElementDelta.F_ARCHIVE_CONTENT_CHANGED) != 0) {
				postRefresh(element, ProjectOutlineContentProvider.ORIGINAL, element, runnables);
				return false;
			}
			// the source attachment of a JAR has changed
			// if ((flags & ( | IModelElementDelta.F_SOURCEDETACHED)) != 0) {
			// postUpdateIcon(element, runnables);
			// }

			if (isBuildPathChange(delta)) {
				// throw the towel and do a full refresh of the affected java
				// project.
				postRefresh(element.getScriptProject(), ProjectOutlineContentProvider.PROJECT, element, runnables);
				return true;
			}
		}
		handleAffectedChildren(delta, element, runnables);
		return false;
	}

	private static boolean isStructuralCUChange(final int flags) {
		// No refresh on working copy creation (F_PRIMARY_WORKING_COPY)
		return (flags & IModelElementDelta.F_CHILDREN) != 0 || (flags & (IModelElementDelta.F_CONTENT | IModelElementDelta.F_FINE_GRAINED)) == IModelElementDelta.F_CONTENT;
	}

	/* package */void handleAffectedChildren(final IModelElementDelta delta, final IModelElement element, final Collection runnables) throws ModelException {
		int count = 0;

		IResourceDelta[] resourceDeltas = delta.getResourceDeltas();
		if (resourceDeltas != null) {
			for (int i = 0; i < resourceDeltas.length; i++) {
				int kind = resourceDeltas[i].getKind();
				if (kind == IResourceDelta.ADDED || kind == IResourceDelta.REMOVED) {
					count++;
				}
			}
		}
		IModelElementDelta[] affectedChildren = delta.getAffectedChildren();
		for (int i = 0; i < affectedChildren.length; i++) {
			int kind = affectedChildren[i].getKind();
			if (kind == IModelElementDelta.ADDED || kind == IModelElementDelta.REMOVED) {
				count++;
			}
		}

		if (count > 1) {
			// more than one child changed, refresh from here downwards
			if (element instanceof IScriptFolder) {
				// a package fragment might become non empty refresh from the
				// parent
				IModelElement parent = (IModelElement) internalGetParent(element);
				// 1GE8SI6: ITPJUI:WIN98 - Rename is not shown in Packages View
				// avoid posting a refresh to an invisible parent
				if (element.equals(fInput)) {
					postRefresh(element, ProjectOutlineContentProvider.ORIGINAL, element, runnables);
				} else {
					postRefresh(parent, ProjectOutlineContentProvider.PARENT, element, runnables);
				}
			} else if (element instanceof IProjectFragment) {
				Object toRefresh = internalGetParent(element);
				postRefresh(toRefresh, ProjectOutlineContentProvider.ORIGINAL, toRefresh, runnables);
			} else {
				postRefresh(element, ProjectOutlineContentProvider.ORIGINAL, element, runnables);
			}
			return;
		}
		if (resourceDeltas != null) {
			for (int i = 0; i < resourceDeltas.length; i++) {
				if (processResourceDelta(resourceDeltas[i], element, runnables)) {
					return; // early return, element got refreshed
				}
			}
		}
		for (int i = 0; i < affectedChildren.length; i++) {
			if (processDelta(affectedChildren[i], runnables)) {
				return; // early return, element got refreshed
			}
		}
	}

	protected void processAffectedChildren(final IModelElementDelta[] affectedChildren, final Collection runnables) throws ModelException {
		for (int i = 0; i < affectedChildren.length; i++) {
			processDelta(affectedChildren[i], runnables);
		}
	}

	private boolean isOnClassPath(final ISourceModule element) {
		IScriptProject project = element.getScriptProject();
		if (project == null || !project.exists()) {
			return false;
		}
		return project.isOnBuildpath(element);
	}

	/**
	 * Updates the selection. It finds newly added elements and selects them.
	 * 
	 * @param delta
	 *            the delta to process
	 * @param runnables
	 *            the resulting view changes as runnables (type {@link Runnable})
	 */
	private void updateSelection(final IModelElementDelta delta, final Collection runnables) {
		final IModelElement addedElement = findAddedElement(delta);
		if (addedElement != null) {
			final StructuredSelection selection = new StructuredSelection(addedElement);
			runnables.add(new Runnable() {
				public void run() {
					// 19431
					// if the item is already visible then select it
					if (fViewer.testFindItem(addedElement) != null) {
						fViewer.setSelection(selection);
					}
				}
			});
		}
	}

	private IModelElement findAddedElement(final IModelElementDelta delta) {
		if (delta.getKind() == IModelElementDelta.ADDED) {
			return delta.getElement();
		}

		IModelElementDelta[] affectedChildren = delta.getAffectedChildren();
		for (int i = 0; i < affectedChildren.length; i++) {
			return findAddedElement(affectedChildren[i]);
		}

		return null;
	}

	/**
	 * Updates the package icon
	 * 
	 * @param element
	 *            the element to update
	 * @param runnables
	 *            the resulting view changes as runnables (type {@link Runnable})
	 */
	private void postUpdateIcon(final IModelElement element, final Collection runnables) {
		runnables.add(new Runnable() {
			public void run() {
				// 1GF87WR: ITPUI:ALL - SWTEx + NPE closing a workbench window.
				fViewer.update(element, new String[] { IBasicPropertyConstants.P_IMAGE });
			}
		});
	}

	/**
	 * Process a resource delta.
	 * 
	 * @param delta
	 *            the delta to process
	 * @param parent
	 *            the parent
	 * @param runnables
	 *            the resulting view changes as runnables (type {@link Runnable})
	 * @return true if the parent got refreshed
	 */
	private boolean processResourceDelta(final IResourceDelta delta, final Object parent, final Collection runnables) {
		int status = delta.getKind();
		int flags = delta.getFlags();

		IResource resource = delta.getResource();
		// filter out changes affecting the output folder
		if (resource == null) {
			return false;
		}

		// this could be optimized by handling all the added children in the
		// parent
		if ((status & IResourceDelta.REMOVED) != 0) {
			if (parent instanceof IScriptFolder) {
				// refresh one level above to deal with empty package filtering
				// properly
				postRefresh(internalGetParent(parent), ProjectOutlineContentProvider.PARENT, parent, runnables);
				return true;
			} else {
				postRemove(resource, runnables);
			}
		}
		if ((status & IResourceDelta.ADDED) != 0) {
			if (parent instanceof IScriptFolder) {
				// refresh one level above to deal with empty package filtering
				// properly
				postRefresh(internalGetParent(parent), ProjectOutlineContentProvider.PARENT, parent, runnables);
				return true;
			} else {
				postAdd(parent, resource, runnables);
			}
		}
		if ((status & IResourceDelta.CHANGED) != 0) {
			if ((flags & IResourceDelta.TYPE) != 0) {
				postRefresh(parent, ProjectOutlineContentProvider.PARENT, resource, runnables);
				return true;
			}
		}
		// open/close state change of a project
		if ((flags & IResourceDelta.OPEN) != 0) {
			postProjectStateChanged(internalGetParent(parent), runnables);
			return true;
		}
		IResourceDelta[] resourceDeltas = delta.getAffectedChildren();
		for (int i = 0; i < resourceDeltas.length; i++) {
			if (processResourceDelta(resourceDeltas[i], resource, runnables)) {
				return false; // early return, element got refreshed
			}
		}
		return false;
	}

	public void setIsFlatLayout(final boolean state) {
		fIsFlatLayout = state;
	}

	public void setShowLibrariesNode(final boolean state) {
		fShowLibrariesNode = state;
	}

	protected void postRefresh(Object root, final int relation, final Object affectedElement, final Collection runnables) {
		// JFace doesn't refresh when object isn't part of the viewer
		// Therefore move the refresh start down to the viewer's input
		if (isParent(root, fInput)) {
			root = fInput;
		}
		List toRefresh = new ArrayList(1);
		toRefresh.add(root);
		augmentElementToRefresh(toRefresh, relation, affectedElement);
		postRefresh(toRefresh, true, runnables);
	}

	/**
	 * Can be implemented by subclasses to add additional elements to refresh
	 * 
	 * @param toRefresh
	 *            the elements to refresh
	 * @param relation
	 *            the relation to the affected element ({@link #GRANT_PARENT},
	 *            {@link #PARENT}, {@link #ORIGINAL}, {@link #PROJECT})
	 * @param affectedElement
	 *            the affected element
	 */
	protected void augmentElementToRefresh(final List toRefresh, final int relation, final Object affectedElement) {
	}

	private boolean isParent(final Object root, final Object child) {
		Object parent = getParent(child);
		if (parent == null) {
			return false;
		}
		if (parent.equals(root)) {
			return true;
		}
		return isParent(root, parent);
	}

	protected void postRefresh(final List toRefresh, final boolean updateLabels, final Collection runnables) {
		runnables.add(new Runnable() {
			public void run() {
				for (Iterator iter = toRefresh.iterator(); iter.hasNext();) {
					fViewer.refresh(iter.next(), updateLabels);
				}
			}
		});
	}

	protected void postAdd(final Object parent, final Object element, final Collection runnables) {
		runnables.add(new Runnable() {
			public void run() {
				Widget[] items = fViewer.testFindItems(element);
				for (int i = 0; i < items.length; i++) {
					Widget item = items[i];
					if (item instanceof TreeItem && !item.isDisposed()) {
						TreeItem parentItem = ((TreeItem) item).getParentItem();
						if (parentItem != null && !parentItem.isDisposed() && parent.equals(parentItem.getData())) {
							return; // no add, element already added (most
							// likely by a refresh)
						}
					}
				}
				fViewer.add(parent, element);
			}
		});
	}

	protected void postRemove(final Object element, final Collection runnables) {
		runnables.add(new Runnable() {
			public void run() {
				fViewer.remove(element);
			}
		});
	}

	protected void postProjectStateChanged(final Object root, final Collection runnables) {
		runnables.add(new Runnable() {
			public void run() {
				fViewer.refresh(root, true);
				// trigger a synthetic selection change so that action refresh
				// their
				// enable state.
				fViewer.setSelection(fViewer.getSelection());
			}
		});
	}

	/*
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(final PropertyChangeEvent event) {
		if (arePackagesFoldedInHierarchicalLayout() != fFoldPackages) {
			fFoldPackages = arePackagesFoldedInHierarchicalLayout();
			if (fViewer != null && !fViewer.getControl().isDisposed()) {
				fViewer.getControl().setRedraw(false);
				Object[] expandedObjects = fViewer.getExpandedElements();
				fViewer.refresh();
				fViewer.setExpandedElements(expandedObjects);
				fViewer.getControl().setRedraw(true);
			}
		}
	}
}
