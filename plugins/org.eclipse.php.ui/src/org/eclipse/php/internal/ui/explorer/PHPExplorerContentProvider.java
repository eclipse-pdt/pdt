/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.IOpenable;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ISearchEngine.SearchFor;
import org.eclipse.dltk.core.index2.search.ISearchRequestor;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.*;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ProjectFragmentContainer;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.typeinference.GlobalNamespace;
import org.eclipse.php.internal.core.util.OutlineFilter;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.util.NamespaceNode;
import org.eclipse.wst.jsdt.core.*;
import org.eclipse.wst.jsdt.core.ElementChangedEvent;
import org.eclipse.wst.jsdt.core.IElementChangedListener;
import org.eclipse.wst.jsdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.wst.jsdt.ui.ProjectLibraryRoot;
import org.eclipse.wst.jsdt.ui.StandardJavaScriptElementContentProvider;
import org.eclipse.wst.jsdt.ui.project.JsNature;

/**
 * 
 * @author apeled, ncohen
 * 
 */
public class PHPExplorerContentProvider extends ScriptExplorerContentProvider
		implements IIncludepathListener /* , IResourceChangeListener */, IElementChangedListener {

	StandardJavaScriptElementContentProvider jsContentProvider;

	private static int[] SEARCH_IN = { IModelElement.TYPE, IModelElement.METHOD, IModelElement.FIELD };

	public PHPExplorerContentProvider(boolean provideMembers) {
		super(provideMembers);
		init();
	}

	protected void init() {
		IncludePathManager.getInstance().registerIncludepathListener(this);
		setIsFlatLayout(false);
		jsContentProvider = new StandardJavaScriptElementContentProvider(true);
	}

	@Override
	public void setIsFlatLayout(final boolean state) {
		super.setIsFlatLayout(false);
	}

	@Override
	public void dispose() {
		super.dispose();
		IncludePathManager.getInstance().unregisterIncludepathListener(this);
		JavaScriptCore.removeElementChangedListener(this);
	}

	private Object[] getNonPhpProjects(final IScriptModel model) throws ModelException {
		return model.getForeignResources();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IPath) {
			IPath path = (IPath) parentElement;
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IResource iesource = workspace.getRoot().findMember(path);
			if (iesource instanceof IProject) {
				IProject project = (IProject) iesource;
				IScriptProject sp = DLTKCore.create(project);
				if (sp instanceof ScriptProject) {
					ScriptProject scriptProject = (ScriptProject) sp;
					try {
						return scriptProject.getAllProjectFragments();
					} catch (ModelException e) {
						Logger.logException(e);
					}
				}
			}
		}
		// include path node
		if (parentElement instanceof IncludePath) {
			final Object entry = ((IncludePath) parentElement).getEntry();
			if (entry instanceof IBuildpathEntry) {
				int entryKind = ((IBuildpathEntry) entry).getEntryKind();
				if (entryKind == IBuildpathEntry.BPE_CONTAINER || entryKind == IBuildpathEntry.BPE_LIBRARY) {
					return getBuildPathEntryChildren(parentElement, entry);
				}
			}
		}

		// JavaScript nodes
		if (parentElement instanceof ProjectLibraryRoot) {
			return ((ProjectLibraryRoot) parentElement).getChildren();
		}
		if (parentElement instanceof PackageFragmentRootContainer) {
			return getContainerPackageFragmentRoots((PackageFragmentRootContainer) parentElement, true);
		}

		if (parentElement instanceof org.eclipse.wst.jsdt.core.IJavaScriptElement) {
			return jsContentProvider.getChildren(parentElement);
		}

		try {
			// don't show local method variables:
			if (parentElement instanceof IMethod) {
				IMethod method = (IMethod) parentElement;
				return OutlineFilter.filterChildrenForOutline(method, method.getChildren());
			}

			// aggregate php projects and non php projects (includes closed
			// ones)
			if (parentElement instanceof IScriptModel) {
				return StandardModelElementContentProvider.concatenate(getScriptProjects((IScriptModel) parentElement),
						getNonPhpProjects((IScriptModel) parentElement));
			}

			// Handles SourceModule and downwards as well as
			// ExternalProjectFragments (i.e language model)
			if (parentElement instanceof ISourceModule || !(parentElement instanceof IOpenable)
					|| parentElement instanceof ExternalProjectFragment) {
				if (parentElement instanceof IFolder) {
					IResource[] members = ((IFolder) parentElement).members();
					ArrayList<Object> returnChlidren = new ArrayList<Object>();
					for (IResource resource2 : members) {
						IModelElement modelElement = DLTKCore.create(resource2);
						if (modelElement != null && isSourceFolder(modelElement)) {
							returnChlidren.add(modelElement);
						} else {
							returnChlidren.add(resource2);
						}
					}
					return (Object[]) returnChlidren.toArray(new Object[returnChlidren.size()]);
				}

				for (ITreeContentProvider provider : TreeContentProviderRegistry.getInstance().getTreeProviders()) {

					Object[] providerChildren = provider.getChildren(parentElement);
					if (providerChildren != null) {
						return providerChildren;
					}
				}

				return super.getChildren(parentElement);
			}

			if (parentElement instanceof IOpenable) {
				if (parentElement instanceof ExternalScriptFolder) {
					return super.getChildren(parentElement);
				}

				IResource resource = ((IOpenable) parentElement).getResource();
				if (resource instanceof IContainer) {

					// contributed by Toshihiro Izumi
					if (!resource.isAccessible()) {
						return NO_CHILDREN;
					}

					ArrayList<Object> returnChildren = new ArrayList<Object>();

					boolean groupByNamespace = PHPUiPlugin.getDefault().getPreferenceStore()
							.getBoolean(PreferenceConstants.EXPLORER_GROUP_BY_NAMESPACES);
					if (groupByNamespace && parentElement instanceof IModelElement
							&& isSourceFolder(DLTKCore.create(resource))
							&& supportsNamespaces(((IOpenable) parentElement).getScriptProject())) {
						if (hasGlobals((IModelElement) parentElement)) {
							returnChildren.add(new GlobalNamespace((IModelElement) parentElement));
						}

						returnChildren.addAll(collectNamespaces(DLTKCore.create(resource)));

						IResource[] resChildren = ((IContainer) resource).members();
						for (IResource resource2 : resChildren) {
							IModelElement modelElement = DLTKCore.create(resource2);
							if (modelElement == null || !isInSourceFolder(modelElement)) {
								returnChildren.add(resource2);
							}
						}
					} else {
						IResource[] resChildren = ((IContainer) resource).members();
						for (IResource resource2 : resChildren) {
							IModelElement modelElement = DLTKCore.create(resource2);
							if (modelElement != null && isInSourceFolder(modelElement)) {
								returnChildren.add(modelElement);
							} else {
								returnChildren.add(resource2);
							}
						}
					}

					// Adding External libraries to the treeview :
					if (parentElement instanceof IScriptProject) {
						IScriptProject scriptProject = (IScriptProject) parentElement;
						IProject project = scriptProject.getProject();

						boolean hasJsNature = JsNature.hasNature(project);
						if (hasJsNature) {
							ProjectLibraryRoot projectLibs = new ProjectLibraryRoot(JavaScriptCore.create(project));
							returnChildren.add(projectLibs);
						}

						returnChildren.addAll(getScriptProjectContent(scriptProject));
					}
					return returnChildren.toArray();
				}
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}

		if (parentElement instanceof ArchiveProjectFragment || parentElement instanceof ArchiveFolder) {
			return super.getChildren(parentElement);
		}

		return NO_CHILDREN;
	}

	protected List<Object> getScriptProjectContent(IScriptProject scriptProject) {
		List<Object> returnChildren = new ArrayList<Object>();
		IProject project = scriptProject.getProject();

		// Add include path node
		IncludePath[] includePaths = IncludePathManager.getInstance().getIncludePaths(project);
		try {
			if (project.hasNature(PHPNature.ID)) {
				IncludePathContainer incPathContainer = new IncludePathContainer(scriptProject, includePaths);
				returnChildren.add(incPathContainer);
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}

		// Add the language library
		try {
			Object[] projectChildren = getProjectFragments(scriptProject);
			for (Object modelElement : projectChildren) {
				if (modelElement instanceof BuildPathContainer && ((BuildPathContainer) modelElement)
						.getBuildpathEntry().getPath().equals(LanguageModelInitializer.LANGUAGE_CONTAINER_PATH)) {
					returnChildren.add(modelElement);
				}
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}

		// let extensions contribute explorer root elements
		for (ITreeContentProvider provider : TreeContentProviderRegistry.getInstance().getTreeProviders()) {

			Object[] providerChildren = provider.getChildren(scriptProject);
			if (providerChildren != null) {
				returnChildren.addAll(new ArrayList<Object>(Arrays.asList(providerChildren)));
			}
		}

		return returnChildren;
	}

	private boolean hasGlobals(IModelElement parent) {
		final boolean[] result = new boolean[1];
		IDLTKSearchScope scope = SearchEngine.createSearchScope(parent, IDLTKSearchScope.SOURCES);
		ISearchEngine searchEngine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
		ISearchRequestor requestor = new ISearchRequestor() {

			@Override
			public void match(int elementType, int flags, int offset, int length, int nameOffset, int nameLength,
					String elementName, String metadata, String doc, String qualifier, String parent,
					ISourceModule sourceModule, boolean isReference) {
				result[0] = true;
			}
		};
		for (int type : SEARCH_IN) {
			searchEngine.search(type, PHPCoreConstants.GLOBAL_NAMESPACE, null, 0, 0, 1, SearchFor.DECLARATIONS,
					MatchRule.PREFIX, scope, requestor, null);
			if (result[0]) {
				return true;
			}
		}

		return result[0];
	}

	private boolean isSourceFolder(IModelElement modelElement) {
		ScriptProject project = (ScriptProject) modelElement.getScriptProject();
		IBuildpathEntry[] buildpath = null;
		try {
			buildpath = project.getResolvedBuildpath();
		} catch (ModelException e) {

		}
		if (buildpath == null) {
			return false;
		}
		for (int j = 0, buildpathLength = buildpath.length; j < buildpathLength; j++) {
			IBuildpathEntry entry = buildpath[j];
			// root path
			IPath path = entry.getPath();
			if (path != null && path.equals(modelElement.getResource().getFullPath())) {
				return true;
			}
		}

		return false;
	}

	private boolean isInSourceFolder(IModelElement modelElement) {
		return modelElement.getScriptProject().isOnBuildpath(modelElement);
	}

	protected boolean supportsNamespaces(IScriptProject project) {
		PHPVersion version = ProjectOptions.getPHPVersion(project.getProject());
		return version.isGreaterThan(PHPVersion.PHP5);
	}

	protected Object[] getAllNamespaces(final IScriptProject project) throws ModelException {
		return collectNamespaces(project).toArray();
	}

	protected List<IType> collectNamespaces(IModelElement create) throws ModelException {

		IDLTKSearchScope scope = SearchEngine.createSearchScope(create, IDLTKSearchScope.SOURCES);

		final Set<String> names = new HashSet<String>();
		ISearchEngine searchEngine = ModelAccess.getSearchEngine(PHPLanguageToolkit.getDefault());
		searchEngine.search(IModelElement.PACKAGE_DECLARATION, null, null, 0, 0, 0, SearchFor.DECLARATIONS,
				MatchRule.PREFIX, scope, new ISearchRequestor() {

					@Override
					public void match(int elementType, int flags, int offset, int length, int nameOffset,
							int nameLength, String elementName, String metadata, String doc, String qualifier,
							String parent, ISourceModule sourceModule, boolean isReference) {
						names.add(elementName);
					}
				}, null);

		List<IType> result = new LinkedList<IType>();
		for (String entry : names) {
			result.add(new NamespaceNode(create, entry));
		}

		return result;
	}

	private Object[] getContainerPackageFragmentRoots(PackageFragmentRootContainer container, boolean createFolder) {

		Object[] children = container.getChildren();
		if (children == null)
			return new Object[0];

		ArrayList<IJavaScriptElement> allChildren = new ArrayList<IJavaScriptElement>();
		ArrayList<Object> expanded = new ArrayList<Object>();
		expanded.addAll(Arrays.asList(children));

		if (expanded == null || expanded.size() < 1)
			return new Object[0];

		Object next = expanded.remove(0);

		while (next != null) {
			try {
				if (next instanceof IPackageFragment) {
					expanded.addAll(Arrays.asList(((IPackageFragment) next).getChildren()));
				} else if (next instanceof IPackageFragmentRoot) {
					expanded.addAll(Arrays.asList(((IPackageFragmentRoot) next).getChildren()));
				} else if (next instanceof IClassFile) {
					List<IJavaScriptElement> newChildren = Arrays.asList(((IClassFile) next).getChildren());
					allChildren.removeAll(newChildren);
					allChildren.addAll(newChildren);
				} else if (next instanceof IJavaScriptUnit) {
					List<IJavaScriptElement> newChildren = Arrays.asList(((IJavaScriptUnit) next).getChildren());
					allChildren.removeAll(newChildren);
					allChildren.addAll(newChildren);

				}
			} catch (JavaScriptModelException ex) {
				Logger.logException(ex);
			}

			if (expanded.size() > 0)
				next = expanded.remove(0);
			else
				next = null;
		}

		return allChildren.toArray();
	}

	/**
	 * @param parentElement
	 * @param entry
	 * @return
	 */
	private Object[] getBuildPathEntryChildren(Object parentElement, Object entry) {
		IScriptProject scriptProject = DLTKCore.create(((IncludePath) parentElement).getProject());
		IProjectFragment[] findProjectFragments = scriptProject.findProjectFragments((IBuildpathEntry) entry);
		List<Object> children = new LinkedList<Object>();
		for (IProjectFragment projectFragment : findProjectFragments) {
			Object[] fragmentChildren = getChildren(projectFragment);
			children.addAll(Arrays.asList(fragmentChildren));
		}
		if (!children.isEmpty()) {
			return children.toArray(new Object[children.size()]);
		}
		return getChildren(((BuildpathEntry) entry).getPath());
	}

	public class IncludePathContainer extends ProjectFragmentContainer {
		private IncludePath[] fIncludePath;

		public IncludePathContainer(IScriptProject parent, IncludePath[] entries) {
			super(parent);
			fIncludePath = entries;
		}

		@Override
		public String getLabel() {
			return PHPUIMessages.IncludePathExplorerNode_label;
		}

		@Override
		public IAdaptable[] getChildren() {
			return fIncludePath;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(fIncludePath);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			IncludePathContainer other = (IncludePathContainer) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (!Arrays.equals(fIncludePath, other.fIncludePath))
				return false;
			return true;
		}

		private PHPExplorerContentProvider getOuterType() {
			return PHPExplorerContentProvider.this;
		}

		@Override
		public IProjectFragment[] getProjectFragments() {
			return null;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return DLTKPluginImages.DESC_OBJS_LIBRARY;
		}
	}

	/**
	 * This method overrides the
	 */
	@Override
	public void refresh(IProject project) {
		Collection<Runnable> runnables = new ArrayList<Runnable>();
		final ArrayList<IScriptProject> resources = new ArrayList<IScriptProject>(1);
		resources.add(DLTKCore.create(project));

		postRefresh(resources, true, runnables);
		executeRunnables(runnables);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		if (oldInput == null && newInput != null) {
			JavaScriptCore.addElementChangedListener(this);
		} else if (oldInput != null && newInput == null) {
			JavaScriptCore.removeElementChangedListener(this);
		}
	}

	@Override
	public void elementChanged(ElementChangedEvent event) {
		IJavaScriptElementDelta[] affectedChildren = event.getDelta().getAffectedChildren();
		final ArrayList<Runnable> runnables = new ArrayList<Runnable>();
		for (int i = 0; i < affectedChildren.length; i++) {
			if (processDelta(affectedChildren[i], runnables)) {
				return; // early return, element got refreshed
			}
		}

	}

	private boolean processDelta(IJavaScriptElementDelta delta, ArrayList<Runnable> runnables) {
		int flags = delta.getFlags();
		IJavaScriptElement element = delta.getElement();
		int elementType = element.getElementType();

		if (elementType != IJavaScriptElement.JAVASCRIPT_MODEL
				&& elementType != IJavaScriptElement.JAVASCRIPT_PROJECT) {
			IJavaScriptProject proj = element.getJavaScriptProject();
			if (proj == null || !proj.getProject().isOpen())
				return false;
		}

		if (elementType == IJavaScriptElement.JAVASCRIPT_PROJECT) {
			// if the raw class path has changed we refresh the entire project
			if ((flags & IJavaScriptElementDelta.F_INCLUDEPATH_CHANGED) != 0) {

				final ArrayList<IScriptProject> resources = new ArrayList<IScriptProject>(1);
				IProject project = ((IJavaScriptProject) element).getProject();
				resources.add(DLTKCore.create(project));
				postRefresh(resources, true, runnables);
				try {
					this.executeRunnables(runnables);
				} catch (NullPointerException ex) {
					// workaround for bug 501274
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IProjectFragment) {
			IResource resource = ((IModelElement) element).getResource();
			if (resource instanceof IFolder && resource.getParent() != null) {
				IResource parentResource = resource.getParent();
				IModelElement modelElement = DLTKCore.create(parentResource);
				if (modelElement != null) {
					return modelElement;
				}
				return parentResource;
			}

		}
		return super.getParent(element);
	}
}