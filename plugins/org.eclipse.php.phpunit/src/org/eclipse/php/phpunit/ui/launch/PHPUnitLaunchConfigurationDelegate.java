/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.launch;

import static org.eclipse.php.phpunit.launch.PHPUnitLaunchAttributes.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.debug.core.debugger.parameters.IDebugParametersKeys;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.launching.PHPExecutableLaunchDelegate;
import org.eclipse.php.internal.debug.core.launching.PHPLaunchUtilities;
import org.eclipse.php.internal.debug.core.preferences.PHPDebugCorePreferenceNames;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.xdebug.communication.XDebugCommunicationDaemon;
import org.eclipse.php.internal.debug.core.zend.communication.DebuggerCommunicationDaemon;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.PHPUnitPreferenceKeys;
import org.eclipse.php.phpunit.launch.PHPUnitLaunchAttributes;
import org.eclipse.php.phpunit.launch.PHPUnitLaunchException;
import org.eclipse.php.phpunit.launch.PHPUnitLaunchUtils;
import org.eclipse.php.phpunit.model.connection.PHPUnitConnectionListener;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;
import org.eclipse.swt.widgets.Display;

public class PHPUnitLaunchConfigurationDelegate extends PHPExecutableLaunchDelegate {

	public static final String PRINTER_NAME = "PHPUnitLogger"; //$NON-NLS-1$

	private static final String PRINTER_DIRECTORY = "printer";//$NON-NLS-1$
	private static final String TMP_PRINTER_DIRECTORY = "phpunit_printer";//$NON-NLS-1$
	private static final String ENV_PORT = "PHPUNIT_PORT"; //$NON-NLS-1$
	private static final String TIMESTAMP_DATA_FORMAT = "yyyyMMdd-HHmm"; //$NON-NLS-1$
	private static final String XML_FILE_FORMAT = "%s-%s.xml";//$NON-NLS-1$
	private static final String NAMESPACE_SEPARATOR = "\\"; //$NON-NLS-1$
	private static final String NAMESPACE_SEPARATOR_ESCAPED = "\\\\"; //$NON-NLS-1$

	@Override
	public boolean buildForLaunch(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor)
			throws CoreException {
		return false;
	}

	@Override
	public void launch(ILaunchConfiguration config, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		if (!PHPUnitLaunchUtils.launchIsPHPUnit(launch)) {
			displayErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationDelegate_Bad_Config);
		}

		if (config.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_CODE_COVERAGE, false)) {
			launch.setAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_COLLECT_CODE_COVERAGE, "1"); //$NON-NLS-1$
		}

		IPath elementToTest = findElementToTest(config);
		if (elementToTest == null || elementToTest.isEmpty()) {
			displayErrorMessage(PHPUnitMessages.PHPUnitLaunchShortcut_Unable_To_Generate);
			return;
		}

		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		final IResource resource = workspaceRoot.findMember(elementToTest);
		if (resource == null) {
			displayErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationTab_No_Container);
			return;
		}
		final IProject project = resource.getProject();

		String fileToExecute = findFileToExecute(config, project);
		if (fileToExecute == null) {
			return;
		}

		final ILaunchConfigurationWorkingCopy wconfig;
		if (config.isWorkingCopy()) {
			wconfig = (ILaunchConfigurationWorkingCopy) config;
		} else {
			wconfig = config.getWorkingCopy();
		}
		setAdditionalAttributes(wconfig, fileToExecute, project);
		setEnvironmentVariables(wconfig);

		PHPUnitOptionsList phpUnitOptionsList = createPHPUnitOptionsList(wconfig, project);
		if (!wconfig.hasAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_RERUN)) {
			config = wconfig.doSave();
		} else {
			config = wconfig;
		}

		if (!(resource instanceof IProject)) {
			phpUnitOptionsList.setElementToTest(resource.getLocation().toOSString());
		}

		File workingDirectory = resource.getLocation().toFile();
		if (workingDirectory.isFile()) {
			workingDirectory = workingDirectory.getParentFile();
		}

		if (PHPUnitLaunchUtils.isPHPUnitRunning()) {
			Display.getDefault()
					.syncExec(() -> ErrorDialog.openError(Display.getCurrent().getActiveShell(),
							PHPUnitMessages.PHPUnitConnection_Launching,
							PHPUnitMessages.PHPUnitConnection_Unable_to_run, new Status(IStatus.ERROR, PHPUnitPlugin.ID,
									0, PHPUnitMessages.PHPUnitConnection_Previous_session_exists, null)));
			return;
		}

		final int port = Integer.parseInt(envVariables.get(ENV_PORT));

		startListening(port, launch);

		PHPexeItem execItem = PHPLaunchUtilities.getPHPExe(config);
		if (execItem != null) {
			// Update launch configuration - in case PHP executable attributes
			// have changed
			ILaunchConfigurationWorkingCopy wc = config.getWorkingCopy();
			wc.setAttribute(IPHPDebugConstants.ATTR_EXECUTABLE_LOCATION, execItem.getExecutable().toString());
			wc.setAttribute(PHPDebugCorePreferenceNames.PHP_DEBUGGER_ID, execItem.getDebuggerID());
			IDebuggerConfiguration debuggerConfiguration = PHPDebuggersRegistry
					.getDebuggerConfiguration(execItem.getDebuggerID());
			wc.setAttribute(PHPDebugCorePreferenceNames.CONFIGURATION_DELEGATE_CLASS,
					debuggerConfiguration.getScriptLaunchDelegateClass());
			if ((mode.equals(ILaunchManager.DEBUG_MODE) || mode.equals(ILaunchManager.PROFILE_MODE))
					&& debuggerConfiguration.getDebuggerId().equals(PHPDebuggersRegistry.NONE_DEBUGGER_ID)) {
				wc.setAttribute(IDebugUIConstants.ATTR_PRIVATE, true);
			}
			if (execItem.getINILocation() != null) {
				wc.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, execItem.getINILocation().toString());
			} else {
				wc.setAttribute(IPHPDebugConstants.ATTR_INI_LOCATION, (String) null);
			}
			if (!wc.hasAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_RERUN)) {
				wc.doSave();
			}
		}

		if (execItem != null) {
			PHPUnitBasicLauncher launcher = new PHPUnitBasicLauncher(config, launch, phpUnitOptionsList);
			if (XDebugCommunicationDaemon.XDEBUG_DEBUGGER_ID.equals(execItem.getDebuggerID())) {
				launcher = new PHPUnitXDLauncher(config, launch, phpUnitOptionsList);
			} else if (DebuggerCommunicationDaemon.ZEND_DEBUGGER_ID.equals(execItem.getDebuggerID())) {
				launcher = new PHPUnitZDLauncher(config, launch, phpUnitOptionsList);
			}

			launch.setAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_PHPUNIT_LAUNCH, Boolean.TRUE.toString());
			try {
				launcher.launch(mode, project, workingDirectory, envVariables, monitor);
			} catch (PHPUnitLaunchException e) {
				displayErrorMessage(e.getMessage());
			}
		} else {
			displayErrorMessage(PHPDebugCoreMessages.PHPExecutableLaunchDelegate_4);
			return;
		}

	}

	private IPath findElementToTest(ILaunchConfiguration config) throws CoreException {
		String containerType = config.getAttribute(ATTRIBUTE_CONTAINER_TYPE, ""); //$NON-NLS-1$
		String container = config.getAttribute(ATTRIBUTE_CONTAINER, ""); //$NON-NLS-1$
		String file = config.getAttribute(ATTRIBUTE_FILE, ""); //$NON-NLS-1$
		String projectName = config.getAttribute(ATTRIBUTE_PROJECT, ""); //$NON-NLS-1$
		boolean runContainer = config.getAttribute(ATTRIBUTE_RUN_CONTAINER, true);

		IPath resourcePath = null;
		if (containerType.equals(PROJECT_CONTAINER)) {
			if (runContainer) {
				resourcePath = new Path(projectName);
			} else {
				resourcePath = new Path(projectName).append(file);
			}
		} else if (containerType.equals(FOLDER_CONTAINER)) {
			if (runContainer) {
				resourcePath = new Path(projectName).append(container);
			}
		} else if (containerType.equals(SOURCE_CONTAINER)) {
			if (runContainer) {
				resourcePath = new Path(projectName).append(container);
			} else {
				resourcePath = new Path(projectName).append(file);
			}
		}
		return resourcePath;
	}

	private String findFileToExecute(ILaunchConfiguration config, IProject project) throws CoreException {
		String runType = config.getAttribute(ATTRIBUTE_EXECUTION_TYPE, PHAR_EXECUTION_TYPE);
		String result = null;
		if (COMPOSER_EXECUTION_TYPE.equals(runType)) {
			result = PHPUnitLaunchUtils.findComposerExecutionFile(project);
			if (result == null) {
				displayErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationDelegate_no_composer_dependency);
			}
		} else if (PHAR_EXECUTION_TYPE.equals(runType)) {
			result = PHPUnitPreferenceKeys.getPHPUnitPharPath();
			if (result == null || !new File(result).exists()) {
				result = null;
				displayErrorMessage(PHPUnitMessages.PHPUnitLaunchConfigurationDelegate_no_phar);
			}
		}
		return result;
	}

	private PHPUnitOptionsList createPHPUnitOptionsList(ILaunchConfigurationWorkingCopy config, IProject project)
			throws CoreException {
		PHPUnitOptionsList optionsList = new PHPUnitOptionsList();
		optionsList.add(PHPUnitOption.INCLUDE_PATH, getPrinterDirectory());
		optionsList.add(PHPUnitOption.PRINTER, PRINTER_NAME);

		boolean logXml = config.getAttribute(ATTRIBUTE_LOG_XML, false);
		if (logXml) {
			String xmlLocation = PHPUnitPreferenceKeys.getReportPath();
			if (xmlLocation != null) {
				String timestamp = new SimpleDateFormat(TIMESTAMP_DATA_FORMAT).format(new Date());
				String fileName = String.format(XML_FILE_FORMAT, config.getName(), timestamp);
				optionsList.add(PHPUnitOption.LOG_JUNIT, new Path(xmlLocation).append(fileName).toOSString());

				config.setAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_LOG_XML_LOCATION,
						new Path(xmlLocation).append(fileName).toOSString());
			}
		}

		String configurationXml = config.getAttribute(ATTRIBUTE_PHPUNIT_CFG, (String) null);
		if (configurationXml != null && !configurationXml.isEmpty()) {
			IResource configurationXmlResource = project.findMember(configurationXml);
			if (configurationXmlResource != null && configurationXmlResource.exists()) {
				optionsList.add(PHPUnitOption.CONFIGURATION, configurationXmlResource.getLocation().toOSString());
			}
		}
		if (config.hasAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_FILTER)) {
			List<String> filters = config.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_FILTER,
					Collections.emptyList());
			if (!filters.isEmpty()) {
				int pos = 0;
				StringBuilder sb = new StringBuilder();
				for (String filter : filters) {
					if (pos++ > 0) {
						sb.append('|');
					}
					sb.append('(');
					sb.append(filter);
					sb.append(')');
				}
				sb.insert(0, "/^"); //$NON-NLS-1$
				sb.append("$/"); //$NON-NLS-1$
				optionsList.add(PHPUnitOption.FILTER,
						sb.toString().replace(NAMESPACE_SEPARATOR, NAMESPACE_SEPARATOR_ESCAPED));
			}
		}

		if (config.hasAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_TEST_SUITE)) {
			List<String> filters = config.getAttribute(PHPUnitLaunchAttributes.ATTRIBUTE_TEST_SUITE,
					Collections.emptyList());
			if (!filters.isEmpty()) {
				int pos = 0;
				StringBuilder sb = new StringBuilder();
				for (String filter : filters) {
					if (pos++ > 0) {
						sb.append(',');
					}
					sb.append(filter);
				}
				optionsList.add(PHPUnitOption.TEST_SUITE,
						sb.toString().replace(NAMESPACE_SEPARATOR, NAMESPACE_SEPARATOR_ESCAPED));
			}

		}
		return optionsList;
	}

	private String getPrinterDirectory() {
		IPath resourcePath = PHPUnitLaunchUtils.getResourcesPath().append(PRINTER_DIRECTORY);
		File printerFile = resourcePath.append(PRINTER_NAME + ".php") //$NON-NLS-1$
				.toFile();

		String tmpDirectoryPath = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
		File tmpDirectory = new File(tmpDirectoryPath);
		if (tmpDirectory.exists()) {
			File printerDirectory = new File(tmpDirectory, TMP_PRINTER_DIRECTORY);
			if (!printerDirectory.exists()) {
				printerDirectory.mkdirs();
			}
			File tmpPrinterFile = new File(printerDirectory, PRINTER_NAME + ".php"); //$NON-NLS-1$
			if (tmpPrinterFile.exists() && tmpPrinterFile.lastModified() >= printerFile.lastModified()) {
				return tmpPrinterFile.getParentFile().getAbsolutePath();
			}

			try {
				FileUtils.copyFile(printerFile, tmpPrinterFile);
				return tmpPrinterFile.getParentFile().getAbsolutePath();
			} catch (IOException e) {
				PHPUnitPlugin.log(e);
				return resourcePath.toOSString();
			}
		}
		return resourcePath.toOSString();
	}

	private void setAdditionalAttributes(ILaunchConfigurationWorkingCopy wconfig, String fileToRun, IProject project)
			throws CoreException {
		// don't ever stop on the first line
		wconfig.setAttribute(IDebugParametersKeys.FIRST_LINE_BREAKPOINT, false);
		wconfig.setAttribute(IPHPDebugConstants.ATTR_FILE, fileToRun);
		wconfig.setAttribute(IPHPDebugConstants.ATTR_FILE_FULL_PATH, fileToRun);
		wconfig.setAttribute(IPHPDebugConstants.PHP_Project, project.getName());
	}

	private void setEnvironmentVariables(final ILaunchConfigurationWorkingCopy wconfig) throws CoreException {
		// Try get any user variable at first.
		synchronized (wconfig) {
			envVariables = wconfig.getAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES,
					new HashMap<String, String>());
			envVariables = ObjectUtils.defaultIfNull(envVariables, new HashMap<>());
			envVariables.computeIfAbsent(ENV_PORT, key -> {
				final String port = String.valueOf(PHPUnitPreferenceKeys.getPort());
				envVariables.put(key, port);
				return port;
			});
			wconfig.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, envVariables);
		}

	}

	private void startListening(int port, ILaunch launch) {
		PHPUnitView.activateView(true);

		PHPUnitConnectionListener listener = new PHPUnitConnectionListener(port, launch);
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(new PHPUnitLaunchListener(launch));
		PHPUnitView.getDefault().startRunning(launch, listener);

		listener.start();
	}

	private void displayErrorMessage(final String message) {
		final Display display = Display.getDefault();
		display.asyncExec(() -> MessageDialog.openError(display.getActiveShell(),
				PHPUnitMessages.PHPUnitLaunchConfigurationDelegate_Launching, message));
	}

}
