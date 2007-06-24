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
package org.eclipse.php.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.tptp.platform.analysis.core.AnalysisConstants;

/**
 * Stub for Analysis launch configuration
 * @author Roy, 2007
 *
 */
public class LaunchConfigurationStub implements ILaunchConfiguration {
	
	private final String rootPath;
	private final List<String> projectNames;

	public LaunchConfigurationStub(String rootPath, List<String> projectNames) {
		super();
		this.rootPath = rootPath;
		this.projectNames = projectNames;
	}
	
	public boolean contentsEqual(ILaunchConfiguration configuration) {
		return false;
	}

	public ILaunchConfigurationWorkingCopy copy(String name) throws CoreException {
		return null;
	}

	public void delete() throws CoreException {

	}

	public boolean exists() {

		return false;
	}

	public boolean getAttribute(String attributeName, boolean defaultValue) throws CoreException {
		if (attributeName.startsWith("codereview.java")) {
			return true;
		}
		return false;
	}

	public int getAttribute(String attributeName, int defaultValue) throws CoreException {
		return AnalysisConstants.ANALYSIS_SCOPE_PROJECTS;
	}

	public List getAttribute(String attributeName, List defaultValue) throws CoreException {

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		if (projectNames == null || projectNames.size() == 0) {
			throw new IllegalArgumentException("Need to specify project names to load");
		}
		
		for (String name: projectNames) {
			final IProjectDescription loadProjectDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path(rootPath + "/" + name +  "/.project"));
			IProject project = root.getProject(name);
			project.create(loadProjectDescription, null);
			project.open(null);
		}
		
		List list = new ArrayList(10);

		IProject[] projectArray = root.getProjects();
		for (int iCtr = 0; iCtr < projectArray.length; iCtr++) {
			final String name = projectArray[iCtr].getName();
			if (projectArray[iCtr].isOpen()) {
				list.add(name);
			}
		}
		return list;
	}

	public Set getAttribute(String attributeName, Set defaultValue) throws CoreException {

		return null;
	}

	public Map getAttribute(String attributeName, Map defaultValue) throws CoreException {

		return null;
	}

	public String getAttribute(String attributeName, String defaultValue) throws CoreException {

		return null;
	}

	public Map getAttributes() throws CoreException {

		return null;
	}

	public String getCategory() throws CoreException {

		return null;
	}

	public IFile getFile() {

		return null;
	}

	public IPath getLocation() {

		return null;
	}

	public IResource[] getMappedResources() throws CoreException {

		return null;
	}

	public String getMemento() throws CoreException {

		return null;
	}

	public Set getModes() throws CoreException {

		return null;
	}

	public String getName() {
		return "PDT Analyzer";
	}

	public ILaunchDelegate getPreferredDelegate(Set modes) throws CoreException {

		return null;
	}

	public ILaunchConfigurationType getType() throws CoreException {

		return null;
	}

	public ILaunchConfigurationWorkingCopy getWorkingCopy() throws CoreException {

		return null;
	}

	public boolean isLocal() {

		return false;
	}

	public boolean isMigrationCandidate() throws CoreException {

		return false;
	}

	public boolean isReadOnly() {

		return false;
	}

	public boolean isWorkingCopy() {

		return false;
	}

	public ILaunch launch(String mode, IProgressMonitor monitor) throws CoreException {

		return null;
	}

	public ILaunch launch(String mode, IProgressMonitor monitor, boolean build) throws CoreException {

		return null;
	}

	public ILaunch launch(String mode, IProgressMonitor monitor, boolean build, boolean register) throws CoreException {

		return null;
	}

	public void migrate() throws CoreException {

	}

	public boolean supportsMode(String mode) throws CoreException {

		return false;
	}

	public Object getAdapter(Class adapter) {

		return null;
	}

}
