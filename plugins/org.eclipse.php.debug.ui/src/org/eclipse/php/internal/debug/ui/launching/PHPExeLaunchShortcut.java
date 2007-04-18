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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.resources.ExternalFileDecorator;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPProjectPreferences;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;

public class PHPExeLaunchShortcut implements ILaunchShortcut {

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
		String device = null;
		if (file == null && input instanceof JavaFileEditorInput) {
			// It's probably a launch for a file that is not in the workspace.
			IPath path = ((JavaFileEditorInput) input).getPath();
			file = ((IWorkspaceRoot) ResourcesPlugin.getWorkspace().getRoot()).getFile(path);
			file = new ExternalFileDecorator(file, path.getDevice());
		}
		if (file != null) {
			searchAndLaunch(new Object[] { file }, mode, getPHPExeLaunchConfigType());
		}
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
				ILaunchConfiguration config = findLaunchConfiguration(project.getName(), phpPathString, phpExeName, mode, configType);
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
	protected static ILaunchConfiguration findLaunchConfiguration(String phpProject, String phpPathString, String phpExeName, String mode, ILaunchConfigurationType configType) {
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
				config = createConfiguration(phpProject, phpPathString, phpExeName, configType);
			}
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return config;
	}

	/**
	 * Create & return a new configuration
	 */
	protected static ILaunchConfiguration createConfiguration(String phpProject, String phpPathString, String phpExeName, ILaunchConfigurationType configType) throws CoreException {
		ILaunchConfiguration config = null;
		//		if (!FileUtils.fileExists(phpPathString)) {
		//			return null;
		//		}
		ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, DebugPlugin.getDefault().getLaunchManager().generateUniqueLaunchConfigurationNameFrom("New_configuration"));

		wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS, PHPExecutableLaunchDelegate.class.getName());
		wc.setAttribute(PHPCoreConstants.ATTR_FILE, phpPathString);
		wc.setAttribute(PHPCoreConstants.ATTR_FILE_FULL_PATH, phpPathString);
		wc.setAttribute(PHPCoreConstants.ATTR_LOCATION, phpExeName);
		wc.setAttribute(IPHPConstants.RUN_WITH_DEBUG_INFO, PHPDebugPlugin.getDebugInfoOption());

		config = wc.doSave();

		return config;
	}

}
