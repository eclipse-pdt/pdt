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

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.wizards.templates.TestCaseClassTemplate;
import org.eclipse.php.phpunit.ui.wizards.templates.TestCaseFunctionTemplate;

public class TestCaseWizard extends PHPUnitWizard {

	public TestCaseWizard() {
		super();
		setWindowTitle(PHPUnitMessages.TestCaseWizard_0);
		setDefaultPageImageDescriptor(PHPUnitPlugin.getImageDescriptor("wizban/newtest_wiz.png")); //$NON-NLS-1$
	}

	@Override
	public void addPages() {
		page = new TestCaseWizardPage();
		addPage(page);
		page.init(getSelection());
	}

	String generateClassFile() {
		final TestCaseClassTemplate template = new TestCaseClassTemplate();
		try {
			final TestCaseWizardPage page = (TestCaseWizardPage) this.page;
			template.resolveTemplate();
			final IType masterClass = (IType) page.getElementToTest();
			final IProject project = page.getTestContainer().getProject();
			template.setMasterElement(masterClass, page.getElementToTestName(), project);
			template.setTestClassParams(page.getClassName(),
					page.getTestContainer().getLocation().append(page.getFileName()).toOSString());
			template.setTestSuperClass(page.getSuperClass(), page.getSuperClassName(), project);
			if (masterClass != null) {
				final IMethod[] functions = masterClass.getMethods();
				int modifiers;
				for (int i = 0; i < functions.length; ++i) {
					modifiers = functions[i].getFlags();

					if (!PHPFlags.isAbstract(modifiers) && !PHPFlags.isPrivate(modifiers)
							&& !PHPFlags.isProtected(modifiers)) {
						template.addMethod(functions[i].getElementName(), (modifiers & PHPFlags.AccStatic) > 0);
					}
				}
			}
			template.compileMethods();
			return template.compileTemplate();
		} catch (final IOException | ModelException e) {
			PHPUnitPlugin.log(e);
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	String generateFile() {
		if (((TestCaseWizardPage) page).getElementToTest() instanceof IMethod) {
			return generateFunctionFile();
		}
		return generateClassFile();
	}

	private String generateFunctionFile() {
		final TestCaseFunctionTemplate template = new TestCaseFunctionTemplate();
		try {
			final TestCaseWizardPage page = (TestCaseWizardPage) this.page;
			template.resolveTemplate();
			template.setTestClassParams(page.getClassName(),
					page.getTestContainer().getLocation().append(page.getFileName()).toOSString());
			IProject project = page.getTestContainer().getProject();
			template.setTestSuperClass(page.getSuperClass(), page.getSuperClassName(), project);
			template.setMasterElement(page.getElementToTest(), page.getElementToTestName(), project);
			return template.compileTemplate();
		} catch (final IOException e) {
			PHPUnitPlugin.log(e);
		}
		return ""; //$NON-NLS-1$
	}

}
