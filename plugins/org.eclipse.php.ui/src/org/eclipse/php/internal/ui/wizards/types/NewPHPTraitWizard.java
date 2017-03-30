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
package org.eclipse.php.internal.ui.wizards.types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.INewWizard;

/**
 * This class represents the Wizard of creating a new PHP Interface
 * 
 */
public class NewPHPTraitWizard extends NewPHPTypeWizard implements INewWizard {

	public NewPHPTraitWizard() {
		super();
		setWindowTitle(Messages.NewPHPTraitWizard_0);
		setDefaultPageImageDescriptor(PHPUiPlugin.getImageDescriptor(TypeWizardConstants.TRAIT_ICON_WIZBAN));
		setNeedsProgressMonitor(true);
	}

	public NewPHPTraitWizard(NewPHPTypePage page) {
		this();
		this.page = page;
		addPage(page);
	}

	@Override
	public void addPages() {
		if (page == null) {
			page = new NewPHPTraitPage();
			addPage(page);
			page.init(getSelection());
		}
	}

	@Override
	public boolean performFinish() {
		if (page.isInExistingPHPFile()) {
			// add the existing file's name to the already existings requires !
			existingPHPFile = DLTKCore.createSourceModuleFrom(page.getExisitngFile());
		}

		// populate data members from the UI's values using the Post Finish
		// validator
		final PostFinishValidator validator = new InterfacePostFinishValidator();
		validator.packAndValidate();

		final String containerName = page.getSourceText();
		final String fileName = page.getNewFileName();

		// ///TEMPLATE///////////////
		PHPElementTemplate templateEngine = new PHPTraitTemplate();
		try {
			templateEngine.resolveTemplate();
		} catch (IOException ioe) {
		}

		NewPHPElementData data = populatePHPElementData();

		final String contents = templateEngine.processTemplate(data);
		compilationResult = contents;

		// Create an interface in a new file
		if (!page.isInExistingPHPFile()) {
			// create a new file and inject the code
			createNewPhpFile(containerName, fileName, contents);
		} else { // an existing file
			injectCodeIntoExistingFile();
		}

		if (validator.hasWarnings()) {
			getShell().getDisplay().asyncExec(() -> showWarningsDialog(validator.getWarnings()));
		}
		page.saveGeneratedGroupValues();
		return true;
	}

	// populate the data object that is passed to the template engine
	private NewPHPElementData populatePHPElementData() {
		NewPHPTraitPage page = (NewPHPTraitPage) this.page;
		NewPHPElementData data = new NewPHPElementData();
		data.isGeneratePHPDoc = page.isCheckboxCreationChecked(NewPHPTypePage.PHP_DOC_BLOCKS);
		List<?> interfacesList = page.getInterfaces();
		IType[] interfaces = new IType[interfacesList.size()];
		interfacesList.toArray(interfaces);
		data.interfaces = interfaces;
		data.className = page.getElementName();
		data.namespace = page.getNamespace();
		List<String> existingImports = getExistingImports();
		data.existingImports = existingImports.toArray(new String[0]);
		List<String> imports = new ArrayList<>();
		for (IType type : interfaces) {
			addImport(imports, type, existingImports);
		}
		data.imports = imports.toArray(new String[0]);
		data.isExistingFile = page.isInExistingPHPFile();
		if (data.isExistingFile) {
			data.isInFirstBlock = page.isInFirstPHPBlock();
			data.hasFirstBlock = true;
		}
		data.isExistingFile = page.isInExistingPHPFile();
		data.requiredToAdd = getRequires();
		return data;
	}

	/**
	 * Processes this wizard's data after 'Finish' is clicked. This validator
	 * retrieves required information for generating code and validates it.
	 * 
	 * @author yaronm
	 */
	class InterfacePostFinishValidator extends PostFinishValidator {

		@Override
		public void packAndValidate() {
			super.packAndValidate();
			// run over all requested interfaces and add their functions and
			// requires in order to be overriden
			handleInterfaces(requiredNamesExcludeList);
		}

		// adds all the methods to override from the hierarchy of Interfaces
		// recursively
		// plus adds all the required php files
		private void handleInterfaces(List<?> requiredNamesExcludeList) {
			for (IType currentInterface : page.getInterfaces()) {
				if (currentInterface != null) {
					extractReqruiresInclude(currentInterface);
				}
			}
		}
	}
}
