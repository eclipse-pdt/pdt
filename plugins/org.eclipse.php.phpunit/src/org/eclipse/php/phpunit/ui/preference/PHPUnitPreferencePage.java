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
package org.eclipse.php.phpunit.ui.preference;

import java.io.IOException;
import java.net.ServerSocket;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.preference.*;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.PHPUnitPreferenceKeys;
import org.eclipse.php.phpunit.launch.PHPUnitLaunchUtils;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PHPUnitPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String ID = PHPUnitPlugin.ID + ".preferences"; //$NON-NLS-1$
	private static final String PHAR_FILE = "phpunit.phar"; //$NON-NLS-1$

	private IntegerFieldEditor fPHPUnitPortField;
	private FileFieldEditor fPHPUnitPharField;

	public PHPUnitPreferencePage() {
		super(PHPUnitMessages.PHPUnitPreferencePage_Name, PHPUnitPlugin.getImageDescriptor(PHPUnitPlugin.ICON_MAIN),
				GRID);
		setPreferenceStore(PHPUnitPlugin.getDefault().getPreferenceStore());

	}

	@Override
	public void init(final IWorkbench workbench) {
		// do nothing!
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void createFieldEditors() {
		fPHPUnitPharField = new FileFieldEditor(PHPUnitPreferenceKeys.PHPUNIT_PHAR_PATH,
				PHPUnitMessages.PHPUnitPreferencePage_Phpunit_phar, getFieldEditorParent());
		fPHPUnitPharField.setFileExtensions(new String[] { "*.phar" }); //$NON-NLS-1$
		GridData gridData = (GridData) fPHPUnitPharField.getTextControl(getFieldEditorParent()).getLayoutData();
		gridData.widthHint = 120;
		addField(fPHPUnitPharField);

		fPHPUnitPortField = new IntegerFieldEditor(PHPUnitPreferenceKeys.PORT,
				PHPUnitMessages.PHPUnitPreferencePage_Port, getFieldEditorParent(), 5);
		addField(fPHPUnitPortField);

		addField(new BooleanFieldEditor(PHPUnitPreferenceKeys.CODE_COVERAGE,
				PHPUnitMessages.PHPUnitPreferencePage_Coverage, BooleanFieldEditor.SEPARATE_LABEL,
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(PHPUnitPreferenceKeys.REPORTING,
				PHPUnitMessages.PHPUnitPreferencePage_Generate_Report, BooleanFieldEditor.SEPARATE_LABEL,
				getFieldEditorParent()));

		DirectoryFieldEditor directoryFieldEditor = new DirectoryFieldEditor(PHPUnitPreferenceKeys.REPORT_PATH,
				PHPUnitMessages.PHPUnitPreferencePage_TransformedXMLOutput0, getFieldEditorParent());
		gridData = (GridData) directoryFieldEditor.getTextControl(getFieldEditorParent()).getLayoutData();
		gridData.widthHint = 120;
		addField(directoryFieldEditor);
	}

	@Override
	public boolean performOk() {
		if (portIsOccupied()) {
			setErrorMessage(PHPUnitMessages.PHPUnitPreferencePage_PortOccupied);
			return false;
		}
		return super.performOk();
	}

	@Override
	public void performDefaults() {
		IPath resourcesPath = PHPUnitLaunchUtils.getResourcesPath();
		if (resourcesPath != null) {
			IPath pharPath = resourcesPath.append(PHAR_FILE);
			if (pharPath.toFile().exists()) {
				getPreferenceStore().setDefault(PHPUnitPreferenceKeys.PHPUNIT_PHAR_PATH, pharPath.toOSString());

			}
		}
		super.performDefaults();
	}

	/**
	 * @return true if port can be opened
	 */
	private boolean portIsOccupied() {
		int portNumber = fPHPUnitPortField.getIntValue();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			return true;
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					return true;
				}
			}
		}
		return false;
	}

}
