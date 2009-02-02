/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.core.ExternalScriptFolder;
import org.eclipse.dltk.internal.ui.StandardModelElementContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.php.internal.core.includepath.IIncludepathListener;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;

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

	@Override
	public Object[] getChildren(Object parentElement) {

		if (parentElement instanceof IncludePath) {
			Object entry = ((IncludePath) parentElement).getEntry();
			if (entry instanceof IBuildpathEntry) {
				ArrayList<Object> res;
				IScriptProject scriptProject = DLTKCore.create(((IncludePath) parentElement).getProject());
				IProjectFragment[] findProjectFragments = scriptProject.findProjectFragments((IBuildpathEntry) entry);
				for (IProjectFragment projectFragment : findProjectFragments) {
					//can be only one
					return getChildren(projectFragment);
				}
				return getChildren(((BuildpathEntry) entry).getPath());
			}
		}
		try {

			// aggregate php projects and non php projects (includes closed ones)
			if (parentElement instanceof IScriptModel) {
				return StandardModelElementContentProvider.concatenate(
					getScriptProjects((IScriptModel) parentElement), 
					getNonPhpProjects((IScriptModel) parentElement));
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

					IResource[] resChildren = ((IContainer) resource).members();
					ArrayList<Object> returnChlidren = new ArrayList<Object>();

					for (IResource resource2 : resChildren) {
						IModelElement modelElement = DLTKCore.create(resource2);
						if (modelElement != null) {
							returnChlidren.add(modelElement);
						} else {
							returnChlidren.add(resource2);
						}
					}
					// Adding External libraries to the treeview :
					if (parentElement instanceof IScriptProject) {
						IScriptProject project = (IScriptProject) parentElement;
						// Add include path node
						IncludePath[] includePaths = IncludePathManager.getInstance().getIncludePaths(project.getProject());
						IncludePathContainer incPathContainer = new IncludePathContainer(project, includePaths);
						returnChlidren.add(incPathContainer);

						// Add the language library
						Object[] projectChildren = getProjectFragments(project);
						for (Object modelElement : projectChildren) {
							//adding only "external" fragments (so we won't double-add source-folders we get on the resource-wise visiting
							if (modelElement instanceof BuildPathContainer) {
								returnChlidren.add(modelElement);
							}
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
}