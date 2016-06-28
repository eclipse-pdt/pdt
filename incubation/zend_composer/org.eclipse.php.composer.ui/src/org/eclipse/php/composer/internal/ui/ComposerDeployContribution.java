package org.eclipse.php.composer.internal.ui;
///*******************************************************************************
// * Copyright (c) 2014, 2016 Zend Technologies and others.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Contributors:
// *     Zend Technologies - initial API and implementation
// *******************************************************************************/
// package com.zend.php.composer.internal.ui;
//
// import org.eclipse.core.resources.*;
// import org.eclipse.core.runtime.*;
// import org.zend.php.zendserver.deployment.core.debugger.IDeploymentHelper;
// import
/// org.zend.php.zendserver.deployment.core.descriptor.DescriptorContainerManager;
// import org.zend.php.zendserver.deployment.debug.core.IDeploymentContribution;
//
// import com.zend.php.composer.core.ComposerService;
// import com.zend.php.composer.ui.console.ConsoleCommandExecutor;
// import com.zend.php.composer.ui.jobs.InstallDependenciesJob;
//
/// **
// * @author Wojciech Galanciak, 2014
// *
// */
// public class ComposerDeployContribution implements IDeploymentContribution {
//
// @Override
// public IStatus performAfter(IProgressMonitor monitor, IDeploymentHelper
/// helper) {
// return Status.OK_STATUS;
// }
//
// @Override
// public IStatus performBefore(IProgressMonitor monitor, IDeploymentHelper
/// helper) {
// String projectName = helper.getProjectName();
// if (projectName != null && !projectName.isEmpty()) {
// IProject project =
/// ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
// if (project != null && ComposerService.isComposerProject(project)
// && !ComposerService.isInstalled(project)) {
// ComposerService composer = new ComposerService(project, new
/// ConsoleCommandExecutor());
// WorkspaceJob job = new InstallDependenciesJob(composer);
// try {
// job.runInWorkspace(monitor);
// IFile descFile = project.getFile(new
/// Path(DescriptorContainerManager.DESCRIPTOR_PATH));
// if (descFile.exists()) {
// descFile.refreshLocal(IResource.DEPTH_ONE, monitor);
// }
// } catch (CoreException e) {
// ComposerUIPlugin.logError(e);
// }
// }
// }
// return Status.OK_STATUS;
// }
//
// }
