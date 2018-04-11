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

import static org.eclipse.php.phpunit.launch.PHPUnitLaunchAttributes.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.ui.launching.PHPExecutableLaunchTab;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.launch.PHPUnitLaunchUtils;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.IEditorPart;

public class PHPUnitLaunchShortcut implements ILaunchShortcut {

	private static final Set<String> CONFIG_FILES = new HashSet<>(Arrays.asList("phpunit.xml", "phpunit.xml.dist")); //$NON-NLS-1$ //$NON-NLS-2$

	boolean launchInBackground = true;

	PHPExecutableLaunchTab phpTab;

	PHPUnitLaunchConfigurationTab tab;

	public void setLaunchInBackground(boolean launchInBackground) {
		this.launchInBackground = launchInBackground;
	}

	@Override
	public void launch(final IEditorPart editor, final String mode) {
		IModelElement element = ((PHPStructuredEditor) editor).getModelElement();
		final ISourceReference highlighted = ((PHPStructuredEditor) editor).computeHighlightRangeSourceReference();
		if (highlighted instanceof IMethod) {
			final String elementName = ((IMethod) highlighted).getElementName();
			final TextViewer textViewer = ((PHPStructuredEditor) editor).getTextViewer();
			final StyledText textWidget = textViewer == null ? null : textViewer.getTextWidget();
			String selectionText = textWidget == null ? null : textWidget.getSelectionText();
			if (selectionText != null && selectionText.equals(elementName) && selectionText.startsWith("test")) { //$NON-NLS-1$
				element = ((IMethod) highlighted).getPrimaryElement();
			}
		}

		launch(element, mode);

	}

	@Override
	public void launch(final ISelection selection, final String mode) {
		Object element = null;
		if (selection instanceof IStructuredSelection) {
			element = ((IStructuredSelection) selection).getFirstElement();
		}
		launch(element, mode);
	}

	private void launch(Object selectedElement, final String mode) {
		try {
			String osString = null;
			String configName;
			if (selectedElement instanceof IModelElement) {
				IModelElement me = (IModelElement) selectedElement;
				osString = me.getPath().toOSString();
				configName = me.getElementName();
			} else if (selectedElement instanceof IResource) {
				final IResource resource = (IResource) selectedElement;
				osString = resource.getFullPath().toOSString();
				configName = resource.getName();
			} else {
				configName = PHPUnitMessages.PHPUnitLaunchShortcut_New_Configuration;
			}

			ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfiguration[] launchConfigurations = launchManager
					.getLaunchConfigurations(launchManager.getLaunchConfigurationType(PHPUnitPlugin.CONFIG_TYPE));
			ILaunchConfiguration config = null;
			for (ILaunchConfiguration launchConfig : launchConfigurations) {
				if (launchConfig.getAttribute(PHPUnitPlugin.ELEMENT_PATH_ATTR, "").equals(osString)) { //$NON-NLS-1$
					config = launchConfig;
					break;
				}
			}
			if (config == null) {
				final ILaunchConfigurationWorkingCopy wconfig = launchManager
						.getLaunchConfigurationType(PHPUnitPlugin.CONFIG_TYPE)
						.newInstance(null, launchManager.generateLaunchConfigurationName(configName));

				getPHPExeTab().setDefaults(wconfig);
				getTab().setDefaults(wconfig);
				wconfig.setAttribute(IDebugUIConstants.ATTR_LAUNCH_IN_BACKGROUND, launchInBackground);
				wconfig.setAttribute(PHPUnitPlugin.ELEMENT_PATH_ATTR, osString);

				generateConfigFromSelection(selectedElement, wconfig);
				wconfig.setAttribute(IPHPDebugConstants.RUN_WITH_DEBUG_INFO, false);
				config = wconfig.doSave();
			}
			DebugUITools.launch(config, mode);
		} catch (final CoreException e) {
			PHPUnitPlugin.log(e);
		}
	}

	private void generateConfigFromSelection(Object selectedElement, ILaunchConfigurationWorkingCopy config) {
		IProject project = null;
		// case of xml configuration file
		if (selectedElement instanceof IResource) {
			IResource resource = (IResource) selectedElement;
			config.setAttribute(ATTRIBUTE_PROJECT, resource.getProject().getName());
			config.setAttribute(ATTRIBUTE_FILE, resource.getFullPath().toString());

			if (resource.getType() == IResource.FILE) {
				final IFile file = (IFile) selectedElement;
				if (PHPToolkitUtil.hasPHPExtention(file)) {
					selectedElement = DLTKCore.create(file);
				} else if (CONFIG_FILES.contains(file.getName())) {
					config.setAttribute(ATTRIBUTE_PHPUNIT_CFG, file.getProjectRelativePath().toString());
					selectedElement = DLTKCore.create(file.getParent());
				}
			} else {
				config.setAttribute(ATTRIBUTE_CONTAINER, resource.getProjectRelativePath().toString());
				config.setAttribute(ATTRIBUTE_RUN_CONTAINER, true);
			}
			project = resource.getProject();
		}
		if (selectedElement instanceof IModelElement) {
			if (selectedElement instanceof IMethod) {
				IMethod method = (IMethod) selectedElement;

				config.setAttribute(ATTRIBUTE_METHOD_NAME, method.getElementName());

				selectedElement = method.getDeclaringType();
			}

			if (selectedElement instanceof IType) {
				IType type = (IType) selectedElement;

				config.setAttribute(ATTRIBUTE_RUN_CONTAINER, false);
				config.setAttribute(ATTRIBUTE_PROJECT, type.getScriptProject().getProject().getName());
				config.setAttribute(ATTRIBUTE_CLASS, type.getElementName());
				config.setAttribute(ATTRIBUTE_FILE,
						type.getSourceModule().getResource().getProjectRelativePath().toString());
				project = type.getScriptProject().getProject();
			} else {
				IModelElement fContainerElement = (IModelElement) selectedElement;
				project = fContainerElement.getScriptProject().getProject();

				config.setAttribute(ATTRIBUTE_RUN_CONTAINER, true);
				config.setAttribute(ATTRIBUTE_PROJECT, project.getName());
				config.setAttribute(ATTRIBUTE_CONTAINER, getProjectRelativePath(fContainerElement));
			}
		}
		String typeName = getContainerType(selectedElement);
		config.setAttribute(ATTRIBUTE_CONTAINER_TYPE, typeName);

		if (PHPUnitLaunchUtils.findComposerExecutionFile(project) != null) {
			config.setAttribute(ATTRIBUTE_EXECUTION_TYPE, COMPOSER_EXECUTION_TYPE);
		}
	}

	private String getContainerType(Object container) {
		if (container instanceof IScriptProject || container instanceof IProject) {
			return PROJECT_CONTAINER;
		}
		if (container instanceof IScriptFolder || container instanceof IProjectFragment
				|| container instanceof IFolder) {
			return FOLDER_CONTAINER;
		}
		if (container instanceof ISourceModule) {
			return SOURCE_CONTAINER;
		}

		return null;
	}

	private String getProjectRelativePath(final Object element) {
		if (element instanceof IScriptFolder) {
			return ((IScriptFolder) element).getResource().getProjectRelativePath().toString();
		}

		if (element instanceof IProjectFragment) {
			return ((IProjectFragment) element).getResource().getProjectRelativePath().toString();
		}

		if (element instanceof IScriptProject) {
			return ((IScriptProject) element).getPath().toString();
		}

		if (element instanceof ISourceModule) {
			return ((ISourceModule) element).getResource().getProjectRelativePath().toString();
		}

		return ""; //$NON-NLS-1$
	}

	public PHPUnitLaunchConfigurationTab getTab() {
		if (tab == null) {
			tab = new PHPUnitLaunchConfigurationTab();
		}
		return tab;
	}

	public PHPExecutableLaunchTab getPHPExeTab() {
		if (phpTab == null) {
			phpTab = new PHPExecutableLaunchTab();
		}
		return phpTab;
	}

}
