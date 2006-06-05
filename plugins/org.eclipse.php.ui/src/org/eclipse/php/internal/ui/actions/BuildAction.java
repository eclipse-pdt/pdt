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
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceAction;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.ide.actions.BuildUtilities;

public class BuildAction extends WorkspaceAction {

	/**
	 * The id of an incremental build action.
	 */
	public static final String ID_BUILD = PlatformUI.PLUGIN_ID + ".BuildAction";//$NON-NLS-1$

	/**
	 * The id of a rebuild all action.
	 */
	public static final String ID_REBUILD_ALL = PlatformUI.PLUGIN_ID + ".RebuildAllAction";//$NON-NLS-1$

	private int buildType;

	/**
	 * The list of IProjects to build (computed lazily).
	 */
	private List projectsToBuild = null;

	/**
	 * Creates a new action of the appropriate type. The action id is 
	 * <code>ID_BUILD</code> for incremental builds and <code>ID_REBUILD_ALL</code>
	 * for full builds.
	 *
	 * @param shell the shell for any dialogs
	 * @param type the type of build; one of
	 *  <code>IncrementalProjectBuilder.INCREMENTAL_BUILD</code> or 
	 *  <code>IncrementalProjectBuilder.FULL_BUILD</code>
	 */
	public BuildAction(Shell shell, int type) {
		super(shell, "");//$NON-NLS-1$

		if (type == IncrementalProjectBuilder.INCREMENTAL_BUILD) {
			setText(IDEWorkbenchMessages.BuildAction_text);
			setToolTipText(IDEWorkbenchMessages.BuildAction_toolTip);
			setId(ID_BUILD);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IIDEHelpContextIds.INCREMENTAL_BUILD_ACTION);
		} else {
			setText(IDEWorkbenchMessages.RebuildAction_text);
			setToolTipText(IDEWorkbenchMessages.RebuildAction_tooltip);
			setId(ID_REBUILD_ALL);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IIDEHelpContextIds.FULL_BUILD_ACTION);
		}

		this.buildType = type;
	}

	/**
	 * Adds the given project and all of its prerequisities, transitively,
	 * to the provided set.
	 */
	private void addAllProjects(IProject project, HashSet projects) {
		if (project == null || !project.isAccessible() || projects.contains(project))
			return;
		projects.add(project);
		try {
			IProject[] preReqs = project.getReferencedProjects();
			for (int i = 0; i < preReqs.length; i++)
				addAllProjects(preReqs[i], projects);
		} catch (CoreException e) {
			//ignore inaccessible projects
		}
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 */
	protected List getActionResources() {
		return getProjectsToBuild();
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 */
	protected String getOperationMessage() {
		return IDEWorkbenchMessages.BuildAction_operationMessage;
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 */
	protected String getProblemsMessage() {
		return IDEWorkbenchMessages.BuildAction_problemMessage;
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 */
	protected String getProblemsTitle() {
		return IDEWorkbenchMessages.BuildAction_problemTitle;
	}

	/**
	 * Returns the projects to build.
	 * This contains the set of projects which have builders, across all selected resources.
	 */
	List getProjectsToBuild() {
		if (projectsToBuild == null) {
			projectsToBuild = new ArrayList(3);
			for (Iterator i = getSelectedResources().iterator(); i.hasNext();) {
				IResource resource = (IResource) i.next();
				IProject project = resource.getProject();
				if (project != null) {
					if (!projectsToBuild.contains(project)) {
						if (hasBuilder(project)) {
							projectsToBuild.add(project);
						}
					}
				}
			}
		}
		return projectsToBuild;
	}

	/**
	 * Returns whether there are builders configured on the given project.
	 *
	 * @return <code>true</code> if it has builders,
	 *   <code>false</code> if not, or if this couldn't be determined
	 */
	boolean hasBuilder(IProject project) {
		try {
			ICommand[] commands = project.getDescription().getBuildSpec();
			if (commands.length > 0) {
				return true;
			}
		} catch (CoreException e) {
			// this method is called when selection changes, so
			// just fall through if it fails.
			// this shouldn't happen anyway, since the list of selected resources
			// has already been checked for accessibility before this is called
		}
		return false;
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 */
	protected void invokeOperation(IResource resource, IProgressMonitor monitor) throws CoreException {
		((IProject) resource).build(buildType, monitor);
	}

	/* (non-Javadoc)
	 * Method declared on Action
	 */
	public boolean isEnabled() {
		//update enablement based on active window and part
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			selectionChanged(new StructuredSelection(BuildUtilities.findSelectedProjects(window)));
		}
		return super.isEnabled();
	}

	/**
	 * Returns whether the user's preference is set to automatically save modified
	 * resources before a manual build is done.
	 *
	 * @return <code>true</code> if Save All Before Build is enabled
	 */
	public static boolean isSaveAllSet() {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		return store.getBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD);
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 *
	 * Change the order of the resources so that
	 * it matches the build order. Closed and
	 * non existant projects are eliminated. Also,
	 * any projects in cycles are eliminated.
	 */
	List pruneResources(List resourceCollection) {
		//recursively compute project prerequisites
		HashSet toBuild = new HashSet();
		for (Iterator it = resourceCollection.iterator(); it.hasNext();)
			addAllProjects((IProject) it.next(), toBuild);

		// Optimize...
		if (toBuild.size() < 2)
			return resourceCollection;

		// Try the workspace's description build order if specified
		String[] orderedNames = ResourcesPlugin.getWorkspace().getDescription().getBuildOrder();
		if (orderedNames != null) {
			List orderedProjects = new ArrayList(toBuild.size());
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			for (int i = 0; i < orderedNames.length; i++) {
				IProject handle = root.getProject(orderedNames[i]);
				if (toBuild.contains(handle)) {
					orderedProjects.add(handle);
					toBuild.remove(handle);
				}
			}
			//Add anything not specified before we return
			orderedProjects.addAll(toBuild);
			return orderedProjects;
		}

		// Try the project prerequisite order then
		IProject[] projects = new IProject[toBuild.size()];
		projects = (IProject[]) toBuild.toArray(projects);
		IWorkspace.ProjectOrder po = ResourcesPlugin.getWorkspace().computeProjectOrder(projects);
		ArrayList orderedProjects = new ArrayList();
		orderedProjects.addAll(Arrays.asList(po.projects));
		return orderedProjects;
	}

	/* (non-Javadoc)
	 * Method declared on IAction; overrides method on WorkspaceAction.
	 * This override allows the user to save the contents of selected
	 * open editors so that the updated contents will be used for building.
	 */
	public void run() {
		List projects = getProjectsToBuild();
		if (projects == null || projects.isEmpty())
			return;

		// Save all resources prior to doing build
		BuildUtilities.saveEditors(projects);
		runInBackground(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule(), ResourcesPlugin.FAMILY_MANUAL_BUILD);
	}

	/* (non-Javadoc)
	 * Method declared on WorkspaceAction.
	 */
	protected boolean shouldPerformResourcePruning() {
		return true;
	}

	/**
	 * The <code>BuildAction</code> implementation of this
	 * <code>SelectionListenerAction</code> method ensures that this action is
	 * enabled only if all of the selected resources have buildable projects.
	 */
	protected boolean updateSelection(IStructuredSelection s) {
		projectsToBuild = null;
		IProject[] projects = (IProject[]) getProjectsToBuild().toArray(new IProject[0]);
		return BuildUtilities.isEnabled(projects, IncrementalProjectBuilder.INCREMENTAL_BUILD);
	}

}
