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
package org.eclipse.php.internal.ui.explorer;

import java.util.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.IOpenable;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.*;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.GlobalNamespace;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.jsdt.core.*;
import org.eclipse.wst.jsdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.wst.jsdt.ui.ProjectLibraryRoot;
import org.eclipse.wst.jsdt.ui.project.JsNature;

/**
 * 
 * 
 * @author apeled, ncohen
 *
 */
public class PHPExplorerContentProvider extends ScriptExplorerContentProvider implements IIncludepathListener /*, IResourceChangeListener*/{

	public PHPExplorerContentProvider(boolean provideMembers) {
		super(provideMembers);
		IncludePathManager.getInstance().registerIncludepathListener(this);
		setIsFlatLayout(false);
	}

	public void setIsFlatLayout(final boolean state) {
		super.setIsFlatLayout(false);
	}

	@Override
	public void dispose() {
		super.dispose();
		IncludePathManager.getInstance().unregisterIncludepathListener(this);
	}

	private Object[] getNonPhpProjects(final IScriptModel model) throws ModelException {
		return model.getForeignResources();
	}

	public Object[] getChildren(Object parentElement) {

		// include path node
		if (parentElement instanceof IncludePath) {
			final Object entry = ((IncludePath) parentElement).getEntry();
			if (entry instanceof IBuildpathEntry) {
				return getBuildPathEntryChildren(parentElement, entry);
			}
		}

		// JavaScript nodes 
		if (parentElement instanceof ProjectLibraryRoot) {
			return ((ProjectLibraryRoot) parentElement).getChildren();
		}
		if (parentElement instanceof PackageFragmentRootContainer) {
			return getContainerPackageFragmentRoots((PackageFragmentRootContainer) parentElement, true);
		}

		try {
			// don't show local method variables:
			if (parentElement instanceof IMethod) {
				return NO_CHILDREN;
			}

			// aggregate php projects and non php projects (includes closed ones)
			if (parentElement instanceof IScriptModel) {
				return StandardModelElementContentProvider.concatenate(getScriptProjects((IScriptModel) parentElement), getNonPhpProjects((IScriptModel) parentElement));
			}

			// Handles SourceModule and downwards as well as ExternalProjectFragments (i.e language model)
			if (parentElement instanceof ISourceModule || !(parentElement instanceof IOpenable) || parentElement instanceof ExternalProjectFragment) {
				if (parentElement instanceof IFolder) {
					return ((IFolder) parentElement).members();
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

					ArrayList<Object> returnChlidren = new ArrayList<Object>();

					boolean groupByNamespace = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EXPLORER_GROUP_BY_NAMESPACES);
					if (groupByNamespace && parentElement instanceof IScriptProject && supportsNamespaces((IScriptProject) parentElement)) {
						returnChlidren.add(new GlobalNamespace((IScriptProject) parentElement));
						returnChlidren.addAll(Arrays.asList(getAllNamespaces((IScriptProject) parentElement)));
					} else {
						IResource[] resChildren = ((IContainer) resource).members();
						for (IResource resource2 : resChildren) {
							IModelElement modelElement = DLTKCore.create(resource2);
							if (modelElement != null) {
								returnChlidren.add(modelElement);
							} else {
								returnChlidren.add(resource2);
							}
						}
					}

					// Adding External libraries to the treeview :
					if (parentElement instanceof IScriptProject) {
						IScriptProject scriptProject = (IScriptProject) parentElement;
						IProject project = scriptProject.getProject();

						// Add include path node
						IncludePath[] includePaths = IncludePathManager.getInstance().getIncludePaths(project);
						IncludePathContainer incPathContainer = new IncludePathContainer(scriptProject, includePaths);
						returnChlidren.add(incPathContainer);

						// Add the language library
						Object[] projectChildren = getProjectFragments(scriptProject);
						for (Object modelElement : projectChildren) {
							if (modelElement instanceof BuildPathContainer && ((BuildPathContainer) modelElement).getBuildpathEntry().getPath().equals(LanguageModelInitializer.LANGUAGE_CONTAINER_PATH)) {
								returnChlidren.add(modelElement);
							}
						}

						boolean hasJsNature = JsNature.hasNature(project);
						if (hasJsNature) {
							ProjectLibraryRoot projectLibs = new ProjectLibraryRoot(JavaScriptCore.create(project));
							returnChlidren.add(projectLibs);
						}
					}
					return returnChlidren.toArray();
				}
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}

		return NO_CHILDREN;
	}

	private boolean supportsNamespaces(IScriptProject project) {
		PHPVersion version = ProjectOptions.getPhpVersion(project.getProject());
		return version.isGreaterThan(PHPVersion.PHP5);
	}

	protected Object[] getAllNamespaces(final IScriptProject project) throws ModelException {
		SearchEngine engine = new SearchEngine();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(project, IDLTKSearchScope.SOURCES);
		SearchParticipant[] participants = new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };
		SearchPattern pattern = SearchPattern.createPattern("*", IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
		final Map<String, List<IType>> processedNamespaces = new HashMap<String, List<IType>>();
		try {
			engine.search(pattern, participants, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {

					Object element = match.getElement();
					if (element instanceof IType) {
						IType type = (IType) element;
						String elementName = type.getElementName();

						if (PHPFlags.isNamespace(type.getFlags())) {
							List<IType> result;
							if (!processedNamespaces.containsKey(elementName)) {
								result = new LinkedList<IType>();
								processedNamespaces.put(elementName, result);
							} else {
								result = processedNamespaces.get(elementName);
							}
							result.add(type);
						}
					}
				}
			}, new NullProgressMonitor());
		} catch (CoreException e) {
			throw new ModelException(e);
		}

		List<IType> result = new LinkedList<IType>();
		for (String namespaceName : processedNamespaces.keySet()) {
			List<IType> list = processedNamespaces.get(namespaceName);
			result.add(new NamespaceNode(project, namespaceName, list.toArray(new IType[list.size()])));
		}
		return result.toArray();
	}

	private Object[] getContainerPackageFragmentRoots(PackageFragmentRootContainer container, boolean createFolder) {

		Object[] children = container.getChildren();
		if (children == null)
			return new Object[0];

		ArrayList<IJavaScriptElement> allChildren = new ArrayList<IJavaScriptElement>();
		ArrayList expanded = new ArrayList();
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
		for (IProjectFragment projectFragment : findProjectFragments) {
			//can be only one
			return getChildren(projectFragment);
		}
		return getChildren(((BuildpathEntry) entry).getPath());
	}

	protected class IncludePathContainer extends BuildPathContainer {
		private IncludePath[] fIncludePath;

		public IncludePathContainer(IScriptProject parent, IncludePath[] entries) {
			super(parent, DLTKCore.newContainerEntry(parent.getPath()));
			fIncludePath = entries;
		}

		public String getLabel() {
			return PHPUIMessages.getString("IncludePathExplorerNode_label");
		}

		public IAdaptable[] getChildren() {
			return fIncludePath;
		}
	}

	/**
	 * This method overrides the   
	 */
	public void refresh(IProject project) {
		Collection<Runnable> runnables = new ArrayList<Runnable>();
		final ArrayList<IScriptProject> resources = new ArrayList<IScriptProject>(1);
		resources.add(DLTKCore.create(project));

		postRefresh(resources, true, runnables);
		this.executeRunnables(runnables);
	}

	static class NamespaceNode extends SourceType {
		private IType[] namespaces;

		public NamespaceNode(IScriptProject project, String name, IType[] namespaces) {
			super((ModelElement) project, name);
			this.namespaces = namespaces;
		}

		public IModelElement[] getChildren(IProgressMonitor monitor) throws ModelException {
			List<IModelElement> children = new LinkedList<IModelElement>();
			for (IType namespace : namespaces) {
				children.addAll(Arrays.asList(namespace.getChildren()));
			}
			return children.toArray(new IModelElement[children.size()]);
		}

		public int getFlags() throws ModelException {
			return Modifiers.AccNameSpace;
		}

		public boolean exists() {
			return true;
		}
	}
}