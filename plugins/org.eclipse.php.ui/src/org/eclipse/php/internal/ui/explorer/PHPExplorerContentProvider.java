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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ExternalProjectFragment;
import org.eclipse.dltk.internal.core.ExternalScriptFolder;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.Logger;

/**
 * 
 * 
 * @author apeled, ncohen
 *
 */
public class PHPExplorerContentProvider extends ScriptExplorerContentProvider /*implements IResourceChangeListener*/{
	IncludePathManager includePathManager;

	public PHPExplorerContentProvider(boolean provideMembers) {
		super(provideMembers);
		// get the include path manager
		includePathManager = IncludePathManager.getInstance();
		super.setIsFlatLayout(false);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		try {
			// Handles SourceModule and downwards as well as ExternalProjectFragments (i.e language model)
			if (parentElement instanceof IncludePath) {
				//TODO return ((IncludePathEntry)parentElement).getChildren();
			}

			if (parentElement instanceof ISourceModule || !(parentElement instanceof IOpenable) || parentElement instanceof ExternalProjectFragment) {
				if (parentElement instanceof IFolder) {
					return ((IFolder) parentElement).members();
				}

				
				return super.getChildren(parentElement);
			}

			if (parentElement instanceof IOpenable) {
				if (parentElement instanceof ExternalScriptFolder) {
					return ((ExternalScriptFolder) parentElement).getChildren();
				}
				
				IResource resource = ((IOpenable) parentElement).getResource();
				if (resource instanceof IContainer) {

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
						IncludePath[] includePath = includePathManager.getIncludePath(project.getProject());
						IncludePathContainer incPathContainer = new IncludePathContainer(project, includePath);
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

	/**
	 * 
	 * @author apeled, ncohen
	 *
	 */
	protected class IncludePathContainer extends BuildPathContainer {
		private IncludePath[] fIncludePath;

		public IncludePathContainer(IScriptProject parent, IncludePath[] entries) {
			super(parent, DLTKCore.newContainerEntry(parent.getPath()));
			fIncludePath = entries;
		}

		public String getLabel() {
			// FIXME externalize string
			return "PHP Include Path";
		}

		public IAdaptable[] getChildren() {
			IScriptProject scriptProject = getScriptProject();
			ArrayList<IAdaptable> res = new ArrayList<IAdaptable>();
			for (int i = 0; i < fIncludePath.length; i++) {
				Object entry = fIncludePath[i].getEntry();

				if (entry instanceof IBuildpathEntry) {

					IBuildpathEntry buildpathEntry = (IBuildpathEntry) entry;
					if (buildpathEntry.getEntryKind() != IBuildpathEntry.BPE_PROJECT) {
						// Add libraries
						IProjectFragment[] findProjectFragments = scriptProject.findProjectFragments(buildpathEntry);
						for (IProjectFragment projectFragment : findProjectFragments) {
							res.add(projectFragment);
						}
						continue;
					}
				}
				res.add(fIncludePath[i]);

			}

			return res.toArray(new IAdaptable[res.size()]);
		}

	}

}