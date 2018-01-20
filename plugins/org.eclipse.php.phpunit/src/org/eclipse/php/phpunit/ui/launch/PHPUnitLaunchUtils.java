/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import static org.eclipse.php.phpunit.ui.launch.PHPUnitLaunchAttributes.*;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.preference.PHPUnitPreferenceKeys;
import org.eclipse.ui.*;
import org.osgi.framework.Bundle;

public class PHPUnitLaunchUtils {

	private static final String RESOURCES_DIRECTORY = "resources"; //$NON-NLS-1$
	private static final String START_SCRIPT_PATH = "vendor/phpunit/phpunit"; //$NON-NLS-1$
	private static final String START_SCRIPT_NAME = "phpunit";//$NON-NLS-1$
	private static final String OLD_START_SCRIPT_NAME = START_SCRIPT_NAME + ".php";//$NON-NLS-1$

	public static void initializeDefaults(final ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(ATTRIBUTE_CODE_COVERAGE, PHPUnitPreferenceKeys.getCodeCoverage());
		config.setAttribute(ATTRIBUTE_LOG_XML, PHPUnitPreferenceKeys.getReporting());
		config.setAttribute(ATTRIBUTE_EXECUTION_TYPE, PHAR_EXECUTION_TYPE);
		config.setAttribute(ATTRIBUTE_PROJECT, ""); //$NON-NLS-1$
		config.setAttribute(ATTRIBUTE_CONTAINER, ""); //$NON-NLS-1$
		config.setAttribute(ATTRIBUTE_CONTAINER_TYPE, SOURCE_CONTAINER);

		config.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, false);
	}

	public static IPath getResourcesPath() {
		Bundle bundle = PHPUnitPlugin.getDefault().getBundle();
		try {
			String file = FileLocator.resolve(bundle.getEntry(RESOURCES_DIRECTORY)).getFile();
			return new Path(file);
		} catch (IOException e) {
			PHPUnitPlugin.log(e);
			return null;
		}
	}

	public static String findComposerExecutionFile(@Nullable IProject project) {
		if (project == null || project.getLocation() == null) {
			return null;
		}
		IPath startScriptDirectory = project.getLocation().append(START_SCRIPT_PATH);

		IPath startScriptPath = startScriptDirectory.append(START_SCRIPT_NAME);
		if (startScriptPath.toFile().exists() && startScriptPath.toFile().isFile()) {
			return startScriptPath.toOSString();
		} else {
			startScriptPath = startScriptDirectory.append(OLD_START_SCRIPT_NAME);
			if (startScriptPath.toFile().exists() && startScriptPath.toFile().isFile()) {
				return startScriptDirectory.append(OLD_START_SCRIPT_NAME).toOSString();
			} else {
				return null;
			}
		}
	}

	public static IModelElement calculateContext(final IEditorPart part) {
		if (part != null) {
			final IEditorInput input = part.getEditorInput();
			if (input instanceof IFileEditorInput) {
				return PHPToolkitUtil.getSourceModule(((IFileEditorInput) input).getFile());
			}
		}
		return null;
	}

	public static IModelElement calculateContext(final IStructuredSelection ss) {
		if (!ss.isEmpty()) {
			final Object obj = ss.getFirstElement();
			if (obj instanceof IModelElement) {
				IModelElement codeData = (IModelElement) obj;
				while (codeData != null && !(codeData instanceof IType) && !(codeData instanceof ISourceModule)
						&& !(codeData instanceof IScriptFolder) && !(codeData instanceof IScriptProject)) {
					codeData = codeData.getParent();
				}
				return codeData;
			}
			if (obj instanceof IResource) {
				return DLTKCore.create((IFile) obj);
			}
		}
		return null;
	}

	/**
	 * Returns the current element context from which to initialize default
	 * settings, or <code>null</code> if none.
	 *
	 * @return Java element context.
	 */
	protected static IModelElement calculateContext(final Object context) {
		IStructuredSelection sSelection = null;
		IEditorPart editor = null;
		IModelElement obj = null;

		if (context != null)
			if (context instanceof IStructuredSelection)
				sSelection = (IStructuredSelection) context;
			else if (context instanceof IEditorPart)
				editor = (IEditorPart) context;
		if (sSelection == null && editor == null) {
			final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null)
				return null;
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (page == null)
				return null;
			final ISelection selection = page.getSelection();
			if (selection instanceof IStructuredSelection)
				sSelection = (IStructuredSelection) selection;
			editor = page.getActiveEditor();
		}
		if (sSelection != null)
			obj = calculateContext(sSelection);
		if (obj == null && editor != null) {
			obj = calculateContext(editor);
		}

		return obj;
	}

	public static boolean launchIsPHPUnit(final ILaunch launch) {
		try {
			if (launch.getLaunchConfiguration().getType().getIdentifier().startsWith(PHPUnitPlugin.ID + ".")) { //$NON-NLS-1$
				return true;
			}
		} catch (CoreException e) {
			PHPUnitPlugin.log(e);
		}
		return false;
	}

	public static boolean isPHPUnitRunning() {
		ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
		for (ILaunch launch : launches) {
			if (launch.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_PHPUNIT_LAUNCH) != null) {
				return true;
			}
		}
		return false;
	}

}
