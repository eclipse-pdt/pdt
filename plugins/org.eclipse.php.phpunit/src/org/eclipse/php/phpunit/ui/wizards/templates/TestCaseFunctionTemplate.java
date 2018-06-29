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
package org.eclipse.php.phpunit.ui.wizards.templates;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.IModelElement;

public class TestCaseFunctionTemplate extends TestCaseTemplate {
	private static final String TEMPLATE_PATH = "resources/templates/ZendPHPUnitFunctionTest.tpl.php"; //$NON-NLS-1$

	@Override
	protected String getTemplatePath() {
		return TEMPLATE_PATH;
	}

	@Override
	public void setMasterElement(IModelElement masterElement, String masterElementName, IProject project) {
		setMasterFunctionName(masterElementName);
		super.setMasterElement(masterElement, masterElementName, project);
	}

	protected void setMasterFunctionName(String name) {
		set("MasterElementNameCamelized", name.substring(0, 1).toUpperCase() + name.substring(1)); //$NON-NLS-1$
	}

}
