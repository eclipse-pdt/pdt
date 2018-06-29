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
package org.eclipse.php.phpunit.ui.wizards;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.ui.preferences.includepath.IncludePathUtils;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.wizards.templates.TestSuiteTemplate;

public class TestSuiteWizard extends PHPUnitWizard {

	public TestSuiteWizard() {
		super();
		setWindowTitle(PHPUnitMessages.TestSuiteWizard_0);
		setDefaultPageImageDescriptor(PHPUnitPlugin.getImageDescriptor("wizban/newsuite_wiz.png")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		page = new TestSuiteWizardPage();
		addPage(page);
		page.init(getSelection());
	}

	@Override
	String generateFile() {
		final TestSuiteTemplate template = new TestSuiteTemplate();
		try {
			template.resolveTemplate();
			template.setTestClassParams(page.getClassName(),
					page.getTestContainer().getFullPath().append(page.getFileName()).toOSString());
			template.setTestSuperClass(page.getSuperClass(), page.getSuperClassName(),
					page.getTestContainer().getProject());
			final IType[] tests = ((TestSuiteWizardPage) page).getElementsToTest();
			if (tests != null) {
				for (int i = 0; i < tests.length; ++i) {
					template.addTest(tests[i].getElementName());
					IPath relativeLocation = IncludePathUtils
							.getRelativeLocationFromIncludePath(tests[i].getScriptProject(), tests[i]);
					if (!relativeLocation.isEmpty()) {
						template.addRequire(relativeLocation.toOSString());
					}
				}
			}
			template.compileTests();
			return template.compileTemplate();
		} catch (final IOException e) {
			PHPUnitPlugin.log(e);
		}
		return ""; //$NON-NLS-1$
	}

}