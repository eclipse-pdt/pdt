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

package org.eclipse.php.internal.debug.ui.launching;

import java.util.ArrayList;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.resources.ExternalFileDecorator;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.editor.UntitledPHPEditor;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class PHPExeLaunchShortcut implements ILaunchShortcut {

	//The following 4 lines were copied from org.eclipse.debug.core.model.LaunchConfigurationDelegate
	private static final String DEBUG_UI = "org.eclipse.debug.ui";
	private static final IStatus promptStatus = new Status(IStatus.INFO, DEBUG_UI, 200, "", null);
	private static final String DEBUG_CORE = "org.eclipse.debug.core";
	private static final IStatus saveScopedDirtyEditors = new Status(IStatus.INFO, DEBUG_CORE, 222, "", null);

	/**
	 * 
	 */
	public PHPExeLaunchShortcut() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers.ISelection, java.lang.String)
	 */
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			searchAndLaunch(((IStructuredSelection) selection).toArray(), mode, getPHPExeLaunchConfigType());
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart, java.lang.String)
	 */
	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		IFile file = (IFile) input.getAdapter(IFile.class);
		if (file == null) {
			IPath path = null;

			if (input instanceof IStorageEditorInput) {
				IStorageEditorInput editorInput = (IStorageEditorInput) input;
				try {
					LocalFileStorage fileStorage = (LocalFileStorage) editorInput.getStorage();
					path = fileStorage.getFullPath();
				} catch (CoreException e) {
					Logger.logException(e);
				}
			} else if (input instanceof IURIEditorInput) {
				path = URIUtil.toPath(((IURIEditorInput) input).getURI());
			} else if (input instanceof NonExistingPHPFileEditorInput) {
				IPath oldPath = ((NonExistingPHPFileEditorInput) input).getPath();//Untitled dummy path
				IStatusHandler prompter = DebugPlugin.getDefault().getStatusHandler(promptStatus);
				if (prompter != null) {
					try {
						int[] breakpointLines = getBreakpointLines(oldPath);

						// the following line will ask the user to save all unsaved documents
						// see org.eclipse.debug.core.model.LaunchConfigurationDelegate
						if (!(Boolean) prompter.handleStatus(saveScopedDirtyEditors, new Object[] {})) {
							return;//save canceled
						}
						//retrieve the new path after save and remove from map
						path = UntitledPHPEditor.latestSavedUntitled.get(oldPath);
						UntitledPHPEditor.latestSavedUntitled.remove(oldPath);
						if (path != null) {
							copyBreakPoints(path, breakpointLines);
						}
					} catch (Exception e) {
						Logger.logException(e);
						return;
					}
				}
			}

			if (path != null) {
				if (ExternalFilesRegistry.getInstance().isEntryExist(path.toString())) {
					file = ExternalFilesRegistry.getInstance().getFileEntry(path.toString());
				} else {
					file = ExternalFileDecorator.createFile(path.toString());
				}
			}
		}
		if (file != null) {
			searchAndLaunch(new Object[] { file }, mode, getPHPExeLaunchConfigType());
		}
	}

	//copy the given line breakpoints to the file of the given path
	private void copyBreakPoints(IPath newPath, int[] lineNumbers) throws CoreException {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().getFile(newPath);
		for (int i = 0; i < lineNumbers.length; i++) {
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(PHPDebugTarget.createBreakpoint(resource, lineNumbers[i]));
		}
	}

	//reteive all the line numbers of breakpoints that exist within the file in the given path 
	private int[] getBreakpointLines(IPath path) throws CoreException {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(IPHPConstants.ID_PHP_DEBUG_CORE);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < breakpoints.length; i++) {
			PHPConditionalBreakpoint breakPoint = (PHPConditionalBreakpoint) breakpoints[i];
			if (breakPoint.getRuntimeBreakpoint().getFileName().equals(path.toString())) {
				list.add(breakPoint.getLineNumber());

			}
		}
		int[] result = new int[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	protected ILaunchConfigurationType getPHPExeLaunchConfigType() {
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		return lm.getLaunchConfigurationType(IPHPConstants.PHPEXELaunchType);
	}

	public static void searchAndLaunch(Object[] search, String mode, ILaunchConfigurationType configType) {
		int entries = search == null ? 0 : search.length;
		for (int i = 0; i < entries; i++) {
			try {
				String phpPathString = null;
				String phpFileLocation = null;
				IProject project = null;
				Object obj = search[i];

				//TODO: if IProject, offer choices?
				if (obj instanceof PHPCodeData) {
					obj = PHPModelUtil.getResource(obj);
				}
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					project = file.getProject();
					IContentType contentType = Platform.getContentTypeManager().getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
					if (contentType.isAssociatedWith(file.getName())) {
						phpPathString = file.getFullPath().toString();
						phpFileLocation = file.getLocation().toString();
					}
				}

				if (phpPathString == null) {
					// Could not find target to launch
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, IStatus.OK, PHPDebugUIMessages.launch_failure_no_target, null));
				}

				String defaultPHPExe = getDefaultPHPExe(project);
				PHPexes exes = new PHPexes();
				exes.load(PHPProjectPreferences.getModelPreferences());
				PHPexeItem defaultEXE = exes.getItem(defaultPHPExe);
				if (defaultEXE == null) {
					defaultEXE = exes.getDefaultItem();
				}
				String phpExeName = (defaultEXE != null) ? defaultEXE.getPhpEXE().getAbsolutePath().toString() : null;

				if (phpExeName == null) {
					MessageDialog.openError(PHPDebugUIPlugin.getActiveWorkbenchShell(), PHPDebugUIMessages.launch_noexe_msg_title, PHPDebugUIMessages.launch_noexe_msg_text);
					PreferencesUtil.createPreferenceDialogOn(PHPDebugUIPlugin.getActiveWorkbenchShell(), "org.eclipse.php.debug.ui.preferencesphps.PHPsPreferencePage", null, null).open();
					return;
				}

				// Launch the app
				ILaunchConfiguration config = findLaunchConfiguration(project, phpPathString, phpFileLocation, phpExeName, mode, configType);
				if (config != null) {
					DebugUITools.launch(config, mode);
				} else {
					// Could not find launch configuration
					throw new CoreException(new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, IStatus.OK, PHPDebugUIMessages.launch_failure_no_config, null));
				}
			} catch (CoreException ce) {
				final IStatus stat = ce.getStatus();
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						ErrorDialog.openError(PHPDebugUIPlugin.getActiveWorkbenchShell(), PHPDebugUIMessages.launch_failure_msg_title, PHPDebugUIMessages.launch_failure_exec_msg_text, stat);
					}
				});
			}
		}
	}

	// Returns the default php executable name for the current project. 
	// In case the project does not have any special settings, return the workspace default.
	private static String getDefaultPHPExe(IProject project) {
		Preferences prefs = PHPProjectPreferences.getModelPreferences();
		String phpExe = prefs.getString(PHPDebugCorePreferenceNames.DEFAULT_PHP);
		if (project != null) {
			// In case that the project is not null, check that we have project-specific settings for it.
			// Otherwise, map it to the workspace default server.
			IScopeContext[] preferenceScopes = createPreferenceScopes(project);
			if (preferenceScopes[0] instanceof ProjectScope) {
				IEclipsePreferences node = preferenceScopes[0].getNode(PHPProjectPreferences.getPreferenceNodeQualifier());
				if (node != null) {
					phpExe = node.get(PHPDebugCorePreferenceNames.DEFAULT_PHP, phpExe);
				}
			}
		}
		return phpExe;
	}

	// Creates a preferences scope for the given project.
	// This scope will be used to search for preferences values.
	private static IScopeContext[] createPreferenceScopes(IProject project) {
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project), new InstanceScope(), new DefaultScope() };
		}
		return new IScopeContext[] { new InstanceScope(), new DefaultScope() };
	}

	/**
	 * Locate a configuration to relaunch for the given type.  If one cannot be found, create one.
	 * 
	 * @return a re-useable config or <code>null</code> if none
	 */
	protected static ILaunchConfiguration findLaunchConfiguration(IProject phpProject, String phpPathString, String phpFileFullLocation, String phpExeName, String mode, ILaunchConfigurationType configType) {
		ILaunchConfiguration config = null;

		try {
			ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configType);

			int numConfigs = configs == null ? 0 : configs.length;
			for (int i = 0; i < numConfigs; i++) {
				String fileName = configs[i].getAttribute(PHPCoreConstants.ATTR_FILE, (String) null);
				String exeName = configs[i].getAttribute(PHPCoreConstants.ATTR_LOCATION, (String) null);

				if (phpPathString.equals(fileName) && exeName.equals(phpExeName)) {
					config = configs[i].getWorkingCopy();
					break;
				}
			}

			if (config == null) {
				config = createConfiguration(phpProject, phpPathString, phpFileFullLocation, phpExeName, configType);
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return config;
	}

	/**
	 * Create & return a new configuration
	 */
	protected static ILaunchConfiguration createConfiguration(IProject phpProject, String phpPathString, String phpFileFullLocation, String phpExeName, ILaunchConfigurationType configType) throws CoreException {
		ILaunchConfiguration config = null;
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, getNewConfigurationName(phpPathString));

		wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, PHPExecutableLaunchDelegate.class.getName());
		wc.setAttribute(PHPCoreConstants.ATTR_FILE, phpPathString);
		wc.setAttribute(PHPCoreConstants.ATTR_FILE_FULL_PATH, phpFileFullLocation);
		wc.setAttribute(PHPCoreConstants.ATTR_LOCATION, phpExeName);
		wc.setAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, PHPDebugPlugin.getDebugInfoOption());
		wc.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, PHPProjectPreferences.getStopAtFirstLine(phpProject));

		config = wc.doSave();

		return config;
	}

	/**
	 * Returns a name for a newly created launch configuration according to the given file name.
	 * In case the name generation fails, return the "New_configuration" string.
	 * 
	 * @param fileName	The original file name that this shortcut shoul execute.
	 * @return The new configuration name, or "New_configuration" in case it fails for some reason.
	 */
	protected static String getNewConfigurationName(String fileName) {
		String configurationName = "New_configuration";
		try {
			IPath path = Path.fromOSString(fileName);
			String fileExtention = path.getFileExtension();
			String lastSegment = path.lastSegment();
			if (lastSegment != null) {
				if (fileExtention != null) {
					lastSegment = lastSegment.replaceFirst("." + fileExtention, "");
				}
				configurationName = lastSegment;
			}
		} catch (Exception e) {
			Logger.log(Logger.WARNING_DEBUG, "Could not generate configuration name for " + fileName + ".\nThe default name will be used.", e);
		}
		return DebugPlugin.getDefault().getLaunchManager().generateUniqueLaunchConfigurationNameFrom(configurationName);
	}
}
