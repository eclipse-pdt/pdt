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
package org.eclipse.php.core.project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.wst.validation.internal.plugin.ValidationPlugin;

public class PHPNature implements IProjectNature {
	public static final String ID = PHPCorePlugin.ID + ".PHPNature";

	public static final String PROJECTTYPE_VALUE = "PHP"; //$NON-NLS-1$
	public static final String VALIDATION_BUILDER_ID = ValidationPlugin.VALIDATION_BUILDER_ID;

	private static int instanceCount = 0;
	public int instanceStamp;

	//protected WebSettings fWebSettings;

	protected IProject project;
	protected PHPProjectOptions options;

	//	private static final String LINKS_BUILDER_ID = "com.ibm.etools.webtools.additions.linksbuilder"; //$NON-NLS-1$

	public static int getInstanceCount() {
		return instanceCount;
	}

	/**
	 * WebNatureRuntime constructor comment.
	 */
	public PHPNature() {
		super();
		++instanceCount;
		instanceStamp = instanceCount;
	}

	/*
	 * Do nothing with a cvs ignore file for web projects, till a better solution is found from OTI
	 */

	public void addCVSIgnoreFile() {
		//Do nothing
	}

	/**
	 * Adds a builder to the build spec for the given project.
	 */
	protected ICommand addToFrontOfBuildSpec(String builderID) throws CoreException {
		ICommand command = null;
		IProjectDescription description = getProject().getDescription();
		ICommand[] commands = description.getBuildSpec();
		boolean found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderID)) {
				found = true;
				command = commands[i];
				break;
			}
		}
		if (!found) {
			command = description.newCommand();
			command.setBuilderName(builderID);
			ICommand[] newCommands = new ICommand[commands.length + 1];
			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			newCommands[0] = command;
			IProjectDescription desc = getProject().getDescription();
			desc.setBuildSpec(newCommands);
			getProject().setDescription(desc, null);
		}
		return command;
	}

	/**
	 * Create a default file for the user given the name (directory relative to the project) and the
	 * default contents for the file.
	 * 
	 * @param newFilePath -
	 *            IPath
	 * @param newFileContents -
	 *            String
	 */
	public void createFile(IPath newFilePath, String newFileContents) throws CoreException {

		IPath projectPath = project.getFullPath();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		createFolder(newFilePath.removeLastSegments(1).toString());

		IFile outputFile = workspace.getRoot().getFile(projectPath.append(newFilePath));
		outputFile.refreshLocal(IResource.DEPTH_INFINITE, null);

		InputStream inputStream = new ByteArrayInputStream(newFileContents.getBytes());
		if (!(outputFile.exists()))
			outputFile.create(inputStream, true, null);
	}

	/**
	 * Removes this nature from the project.
	 * 
	 * @see IProjectNature#deconfigure
	 */
	public void deconfigure() throws CoreException {
		//		super.deconfigure();
		//		removeFromBuildSpec(J2EEPlugin.LINKS_BUILDER_ID);
		clean();
	}

	private void clean() {
		options = null;
		project = null;
	}

	/**
	 * Insert the method's description here. Creation date: (11/1/2001 2:25:22 PM)
	 * 
	 * @param builderID
	 *            java.lang.String
	 * @exception org.eclipse.core.runtime.CoreException
	 *                The exception description.
	 */
	protected void removeFromBuildSpec(String builderID) throws org.eclipse.core.runtime.CoreException {
		IProjectDescription description = getProject().getDescription();
		ICommand[] commands = description.getBuildSpec();
		boolean found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderID)) {
				found = true;
				break;
			}
		}
		if (!found) {
			ICommand command = description.newCommand();
			command.setBuilderName(builderID);
			ICommand[] newCommands = new ICommand[commands.length + 1];
			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			newCommands[0] = command;
			IProjectDescription desc = getProject().getDescription();
			desc.setBuildSpec(newCommands);
			getProject().setDescription(desc, null);
		}

	}

	/**
	 * Configures the project with this nature.
	 * 
	 * @see IProjectNature#configure()
	 */
	//	public void primConfigure() throws CoreException {
	//		addToFrontOfBuildSpec(VALIDATION_BUILDER_ID);
	//		addToFrontOfBuildSpec(PHPProjectOptions.BUILDER_ID);
	//	}
	/**
	 * Configures the project with this nature. This is called by <code>IProject.addNature</code>
	 * and should not be called directly by clients. The nature extension id is added to the list of
	 * natures on the project by <code>IProject.addNature</code>, and need not be added here.
	 * 
	 * All subtypes must call super.
	 * 
	 * @exception CoreException
	 *                if this method fails.
	 */
	public void configure() throws org.eclipse.core.runtime.CoreException {
		// enable workspace validation for this nature
		addToFrontOfBuildSpec(VALIDATION_BUILDER_ID);
		addBuildersToProject();
	}

	/*
	 * Adds to the project the builders registered to the buildersInitializer extention point (in case they are not in the list yet).
	 * It is not enough to call this function in "configure" for the following reason:
	 * in case a project has been created BEFORE a certain builder existed, the new builder will not be added to the old project, since "configure"
	 * is called only upon creation. 
	 */
	public void addBuildersToProject() {
		//  load all registered extensions and add them to build spec
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.core.buildersInitializer"); //$NON-NLS-1$
		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			try {
				addToFrontOfBuildSpec(element.getAttribute("id"));
			} catch (InvalidRegistryObjectException e) {
				Logger.logException("Failed loading builder", e);
			} catch (CoreException e) {
				Logger.logException("Failed loading builder", e);
			} //$NON-NLS-1$		   
		}
	}

	/**
	 * Returns the project to which this project nature applies.
	 * 
	 * @return the project handle
	 */
	public org.eclipse.core.resources.IProject getProject() {
		return project;
	}

	/**
	 * Sets the project to which this nature applies. Used when instantiating this project nature
	 * runtime. This is called by <code>IProject.addNature</code> and should not be called
	 * directly by clients.
	 * 
	 * @param project
	 *            the project to which this nature applies
	 */
	public void setProject(org.eclipse.core.resources.IProject newProject) {
		clean();
		project = newProject;
		addBuildersToProject();
		//		
		//      configure should only be called when project is created, not here		
		//		//need to be called here since getNature and createNature will not call it
		//		try {
		//			configure();
		//		} catch (CoreException e) {
		//			//Ignore
		//		}
	}

	/**
	 * Create a folder relative to the project based on aProjectRelativePathString.
	 * 
	 * @exception com.ibm.itp.core.api.resources.CoreException
	 *                The exception description.
	 */
	public IFolder createFolder(String aProjectRelativePathString) throws CoreException {
		if (aProjectRelativePathString != null && aProjectRelativePathString.length() > 0)
			return createFolder(new Path(aProjectRelativePathString));
		return null;
	}

	/**
	 * Create a folder relative to the project based on aProjectRelativePathString.
	 * 
	 * @exception com.ibm.itp.core.api.resources.CoreException
	 *                The exception description.
	 */
	public IFolder createFolder(IPath aProjectRelativePath) throws CoreException {
		if (aProjectRelativePath != null && !aProjectRelativePath.isEmpty()) {
			IFolder folder = getWorkspace().getRoot().getFolder(getProjectPath().append(aProjectRelativePath));
			if (!folder.exists())
				folder.create(true, true, null);
			return folder;
		}
		return null;
	}

	/**
	 * Adds a nauture to a project
	 */
	//	protected static void addNatureToProject(IProject proj, String natureId) throws CoreException {
	//		ProjectUtilities.addNatureToProject(proj, natureId);
	//	}
	/**
	 * Return the full path of the project.
	 */
	protected IPath getProjectPath() {
		return getProject().getFullPath();
	}

	public IWorkspace getWorkspace() {
		return getProject().getWorkspace();
	}

	public PHPProjectOptions getOptions() {
		if (options == null) {
			options = new PHPProjectOptions(project);
			//			boolean useProjectSpecificSettings = new ProjectScope(project).getNode(PhpOptionsPreferenceKeys.PHP_OPTION_NODE).getBoolean(PhpOptionsPreferenceKeys.PHP_OPTIONS_PER_PROJECT, false);
			//			if (!useProjectSpecificSettings) {
			//				PhpVerionsProjectOptionAdapter.setVersion(options, CorePreferenceConstants.getPreferenceStore().getString(CorePreferenceConstants.Keys.PHP_VERSION));
			//			}
			//			IEclipsePreferences projectProperties = new ProjectScope(project).getNode(PhpOptionsPreferenceKeys.PHP_OPTION_NODE);
			//			projectProperties.put
			//			projectProperties
		}
		return options;
	}

}