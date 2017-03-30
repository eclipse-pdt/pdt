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
import java.util.Arrays;
import java.util.List;

import org.eclipse.dltk.core.*;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.ui.INewWizard;

/**
 * This class represents the Wizard for creating a new PHP Class
 */
public class NewPHPClassWizard extends NewPHPTypeWizard implements INewWizard {

	private ArrayList<IMethod> funcsToAdd = new ArrayList<>();

	public NewPHPClassWizard() {
		super();
		setWindowTitle(Messages.NewPHPClassWizard_0);
		setDefaultPageImageDescriptor(PHPUiPlugin.getImageDescriptor(TypeWizardConstants.CLASS_ICON_WIZBAN));
		setNeedsProgressMonitor(true);
	}

	public NewPHPClassWizard(NewPHPTypePage page) {
		this();
		this.page = page;
		addPage(page);
	}

	@Override
	public void addPages() {
		if (page == null) {
			page = new NewPHPClassPage();
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
		final PostFinishValidator validator = new ClassPostFinishValidator();
		validator.packAndValidate();

		final String containerName = page.getSourceText();
		final String fileName = page.getNewFileName();

		// ///TEMPLATE///////////////

		PHPElementTemplate templateEngine = new PHPClassTemplate();
		try {
			templateEngine.resolveTemplate();
		} catch (IOException ioe) {
			Logger.logException(ioe);
		}

		// The data object that will be passed to the template engine
		NewPHPElementData data = populatePHPElementData();

		final String contents = templateEngine.processTemplate(data);
		compilationResult = contents;
		// Create the class in a new file :
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

	// populate data object that is passed to the template engine
	private NewPHPElementData populatePHPElementData() {
		PHPVersion phpVersion = page.getPhpVersion();
		NewPHPClassPage page = (NewPHPClassPage) this.page;
		NewPHPElementData data = new NewPHPElementData();
		data.phpVersion = phpVersion;
		data.isGeneratePHPDoc = page.isCheckboxCreationChecked(NewPHPClassPage.PHP_DOC_BLOCKS);
		data.isGenerateTODOs = page.isCheckboxCreationChecked(NewPHPClassPage.TODOS);
		data.superClass = page.getSuperClassData();
		List<IType> interfacesList = page.getInterfaces();
		IType[] interfaces = new IType[interfacesList.size()];
		interfacesList.toArray(interfaces);
		data.interfaces = interfaces;
		List<IType> traitsList = page.getTraits();
		IType[] traits = new IType[traitsList.size()];
		traitsList.toArray(traits);
		data.traits = traits;
		List<String> existingImports = getExistingImports();
		data.existingImports = existingImports.toArray(new String[0]);
		List<String> imports = new ArrayList<>();
		for (IType type : interfaces) {
			addImport(imports, type, existingImports);
		}
		if (data.superClass != null) {
			addImport(imports, data.superClass, existingImports);
		}
		for (IType type : traits) {
			addImport(imports, type, existingImports);
		}
		data.isGenerateConstructor = page.isCheckboxCreationChecked(NewPHPClassPage.CONSTRUCTOR);

		data.isGenerateDestructor = page.isCheckboxCreationChecked(NewPHPClassPage.DESTRUCTOR);
		data.isFinal = page.isCreateModifierChecked(1);
		data.isAbstract = page.isCreateModifierChecked(2);

		IMethod[] funcs = new IMethod[funcsToAdd.size()];
		funcsToAdd.toArray(funcs);
		for (IMethod method : funcs) {
			try {
				IParameter[] parameters = method.getParameters();
				for (IParameter parameter : parameters) {
					if (parameter.getType() != null) {
						IType[] parameterTypes = PHPModelUtils.getTypes(parameter.getType(), method.getSourceModule(),
								method.getSourceRange().getOffset(), null);
						for (IType parameterType : parameterTypes) {
							addImport(imports, parameterType, existingImports);
						}
					}
				}
			} catch (ModelException e) {
			}
		}
		data.funcsToAdd = funcs;
		data.className = page.getElementName();
		data.namespace = page.getNamespace();
		data.realNamespace = page.getRealNamespace();
		data.isExistingFile = page.isInExistingPHPFile();
		data.requiredToAdd = getRequires();
		if (data.isExistingFile) {
			data.isInFirstBlock = page.isInFirstPHPBlock();
			data.hasFirstBlock = true;
		}
		data.imports = imports.toArray(new String[0]);
		return data;
	}

	/**
	 * Processes this wizard's data after 'Finish' is clicked. This validator
	 * retrieves required information for generating code and validates it.
	 * 
	 * @author yaronm
	 */
	class ClassPostFinishValidator extends PostFinishValidator {

		@Override
		public void packAndValidate() {
			super.packAndValidate();
			// run over all requested interfaces and add their functions and
			// requires in order to be overriden
			handleInterfaces();

			// run over all super class hierarchy
			// add abstract method to be overriden
			IType superClassData = ((NewPHPClassPage) page).getSuperClassData();
			if (superClassData != null) {
				extractReqruiresInclude(superClassData);

				addRequiredFuns(superClassData);
			}

		}

		private void addRequiredFuns(IType superClassData) {
			if (((NewPHPClassPage) page).isCheckboxCreationChecked(NewPHPClassPage.INHERITED_ABSTRACT_METHODS)) {
				try {
					funcsToAdd.addAll(Arrays.asList(PHPModelUtils.getUnimplementedMethods(superClassData, null)));
				} catch (ModelException e) {
				}
			}
		}

		// adds all the methods to override from the hierarchy of Interfaces
		// recursively
		// plus adds all the required php files
		private void handleInterfaces() {
			for (IType currentInterface : page.getInterfaces()) {
				if (currentInterface != null) {
					extractReqruiresInclude(currentInterface);
					addRequiredFuns(currentInterface);
				}
			}
			for (IType currentInterface : page.getTraits()) {
				if (currentInterface != null) {
					extractReqruiresInclude(currentInterface);
				}
			}
		}
	}
}
