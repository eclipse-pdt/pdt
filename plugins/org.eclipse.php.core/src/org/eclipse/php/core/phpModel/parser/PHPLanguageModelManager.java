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
package org.eclipse.php.core.phpModel.parser;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.phpModel.IPHPLanguageModel;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPKeywordData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.core.project.properties.handlers.PhpVersionChangedHandler;
import org.eclipse.php.core.project.properties.handlers.PhpVersionProjectPropertyHandler;

public class PHPLanguageModelManager extends PhpModelProxy implements IPHPLanguageModel {

	// to avoid casting we keep a second reference of the model with the right type
	private IPHPLanguageModel languageModel;
	private IProject project;
	private PhpVersionListener phpVersionListener;

	public void initialize(IProject project) {
		this.project = project;
		String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);

		setVersion(phpVersion);

		phpVersionListener = new PhpVersionListener();
		PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(phpVersionListener);
	}

	private void setVersion(String phpVersion) {
		languageModel = (IPHPLanguageModel) PHPLanguageManagerProvider.instance().getPHPLanguageManager(phpVersion).getModel();
		model = languageModel;
	}

	public String getPHPVersion() {
		return languageModel.getPHPVersion();
	}

	public PHPClassData getClass(String className) {
		return languageModel.getClass(className);
	}

	public PHPKeywordData[] getKeywordData() {
		return languageModel.getKeywordData();
	}

	public PHPVariableData[] getServerVariables() {
		return languageModel.getServerVariables();
	}

	public PHPVariableData[] getSessionVariables() {
		return languageModel.getSessionVariables();
	}

	public PHPVariableData[] getPHPVariables() {
		return languageModel.getPHPVariables();
	}

	public void dispose() {
		super.dispose();
		PhpVersionChangedHandler.getInstance().removePhpVersionChangedListener(phpVersionListener);
	}

	private class PhpVersionListener implements IPreferencesPropagatorListener {

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			String newVersion = (String) event.getNewValue();
			setVersion(newVersion);
		}

		public IProject getProject() {
			return PHPLanguageModelManager.this.project;
		}
	}

}
