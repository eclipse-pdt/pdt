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
package org.eclipse.php.phpunit.launch;

import static org.eclipse.php.phpunit.launch.PHPUnitLaunchAttributes.*;

import java.io.File;
import java.io.FileReader;
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
import org.eclipse.php.phpunit.PHPUnitPreferenceKeys;
import org.eclipse.ui.*;
import org.osgi.framework.Bundle;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class PHPUnitLaunchUtils {

	private static final String RESOURCES_DIRECTORY = "resources"; //$NON-NLS-1$
	private static final String COMPOSER_JSON_FILENAME = "composer.json"; //$NON-NLS-1$
	private static final String COMPOSER_JSON_CONFIG = "config"; //$NON-NLS-1$
	private static final String COMPOSER_JSON_CONFIG_VENDORDIR = "vendor-dir"; //$NON-NLS-1$
	private static final String COMPOSER_JSON_CONFIG_VENDORDIR_DIRSEP = "/"; //$NON-NLS-1$
	private static final String DEFAULT_COMPOSER_VENDORDIR = "vendor"; //$NON-NLS-1$
	private static final String START_SCRIPT_PATH = "phpunit/phpunit"; //$NON-NLS-1$
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

	private static @Nullable IPath getVendorPath(@Nullable IProject project) {
		if (project == null || project.getLocation() == null) {
			return null;
		}
		String vendorDir = DEFAULT_COMPOSER_VENDORDIR;
		IPath projectLocation = project.getLocation();
		File composerJsonFile = projectLocation.append(COMPOSER_JSON_FILENAME).toFile();
		if (composerJsonFile.exists() && composerJsonFile.isFile() && composerJsonFile.length() > 0) {
			try (FileReader reader = new FileReader(composerJsonFile)) {
				JsonParser parser = new JsonParser();
				JsonElement jComposer = parser.parse(reader);
				if (jComposer.isJsonObject() && ((JsonObject) jComposer).has(COMPOSER_JSON_CONFIG)) {
					JsonElement jConfig = ((JsonObject) jComposer).get(COMPOSER_JSON_CONFIG);
					if (jConfig.isJsonObject() && ((JsonObject) jConfig).has(COMPOSER_JSON_CONFIG_VENDORDIR)) {
						JsonElement jVendorDir = ((JsonObject) jConfig).get(COMPOSER_JSON_CONFIG_VENDORDIR);
						if (jVendorDir.isJsonPrimitive() && ((JsonPrimitive) jVendorDir).isString()) {
							vendorDir = ((JsonPrimitive) jVendorDir).getAsString();
							int vendorDirLength = vendorDir.length();
							if (vendorDirLength > 1 && vendorDir.endsWith(COMPOSER_JSON_CONFIG_VENDORDIR_DIRSEP)) {
								vendorDir = vendorDir.substring(0, vendorDirLength - 1);
							}
						}
					}
				}
			} catch (Exception e) {
				PHPUnitPlugin.log(e);
			}
		}
		return projectLocation.append(vendorDir);
	}

	public static @Nullable String findComposerExecutionFile(@Nullable IProject project) {
		IPath vendorPath = getVendorPath(project);
		if (vendorPath == null) {
			return null;
		}
		IPath startScriptDirectory = vendorPath.append(START_SCRIPT_PATH);
		for (String startScriptName : new String[] { START_SCRIPT_NAME, OLD_START_SCRIPT_NAME }) {
			IPath startScriptPath = startScriptDirectory.append(startScriptName);
			File startScriptFile = startScriptPath.toFile();
			if (startScriptFile.exists() && startScriptFile.isFile()) {
				return startScriptPath.toOSString();
			}
		}
		return null;
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

		if (context != null) {
			if (context instanceof IStructuredSelection) {
				sSelection = (IStructuredSelection) context;
			} else if (context instanceof IEditorPart) {
				editor = (IEditorPart) context;
			}
		}
		if (sSelection == null && editor == null) {
			final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null) {
				return null;
			}
			final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (page == null) {
				return null;
			}
			final ISelection selection = page.getSelection();
			if (selection instanceof IStructuredSelection) {
				sSelection = (IStructuredSelection) selection;
			}
			editor = page.getActiveEditor();
		}
		if (sSelection != null) {
			obj = calculateContext(sSelection);
		}
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
