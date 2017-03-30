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

/**
 * This class represents the template for creating a PHP interface code
 * 
 */
public class PHPTraitTemplate extends PHPElementTemplate {
	private String requiredPHPsBlock;

	public static final String EXTENDS_INTERFACES_STRUCT = "extends_interfaces"; //$NON-NLS-1$
	public static final String EXTENDS_INTERFACES_STRUCT_COMPILED = "extends_interfaces_compiled"; //$NON-NLS-1$

	@Override
	public String getTemplatePath() {
		return TypeWizardConstants.INTERFACE_TEMPLATE_LOCATION;
	}

	@Override
	public String processTemplate(NewPHPElementData data) {
		// handle class default PHPDOC
		set(DEFAULT_PHPDOC_VAR, ""); //$NON-NLS-1$
		if (data.isGeneratePHPDoc) {
			set(DEFAULT_PHPDOC_VAR, getDefaultPHPDoc());
		}

		// handle interfaces declaration
		extract(INPUT, INTERFACES_STRUCT, INTERFACES_STRUCT_COMPILED);
		set(INTERFACES_STRUCT, ""); //$NON-NLS-1$
		if (data.interfaces.length > 0) {
			for (int i = 0; i < data.interfaces.length; i++) {
				String interfaceName = data.interfaces[i].getElementName();
				if (!isImported(data, data.interfaces[i])) {
					interfaceName = "\\" + interfaceName; //$NON-NLS-1$
				}
				// if not the last interface name, add the ','
				if ((i + 1) < data.interfaces.length) {
					interfaceName += ", "; //$NON-NLS-1$
				}
				set(INTERFACE_NAME_VAR, interfaceName);
				compile(INTERFACES_STRUCT_COMPILED, INTERFACES_STRUCT, true);
			}
		}
		extract(INPUT, EXTENDS_INTERFACES_STRUCT, EXTENDS_INTERFACES_STRUCT_COMPILED);
		set(EXTENDS_INTERFACES_STRUCT, ""); //$NON-NLS-1$
		if (data.interfaces.length > 0) {
			compile(EXTENDS_INTERFACES_STRUCT_COMPILED, EXTENDS_INTERFACES_STRUCT, true);
		}

		// handle class
		extract(INPUT, CLASS_STRUCT, CLASS_STRUCT_COMPILED);
		set(CLASS_STRUCT, ""); //$NON-NLS-1$
		set(CLASS_NAME_VAR, data.className);
		set(ELEMENT_TYPE_VAR, TypeWizardConstants.TRAIT_TYPE);
		compile(CLASS_STRUCT_COMPILED, CLASS_STRUCT, false);

		// handle namespace
		extract(INPUT, NAMESPACE_STRUCT, NAMESPACE_STRUCT_COMPILED);
		set(NAMESPACE_STRUCT, ""); //$NON-NLS-1$

		if (data.namespace != null && !data.namespace.isEmpty()) {
			set(NAMESPACE_NAME, data.namespace);
			compile(NAMESPACE_STRUCT_COMPILED, NAMESPACE_STRUCT, true);
		}

		// handle requires list declaration
		handleRequires(data);

		// handle use
		extract(INPUT, USE_STRUCT, USE_STRUCT_COMPILED);
		set(USE_STRUCT, ""); //$NON-NLS-1$

		for (int i = 0; i < data.imports.length; i++) {
			set(USE, data.imports[i]);
			compile(USE_STRUCT_COMPILED, USE_STRUCT, true);
		}

		// handle namespace in existing file
		extract(CLASS_STRUCT, NAMESPACE_IN_FILE_STRUCT, NAMESPACE_IN_FILE_STRUCT_COMPILED);
		set(NAMESPACE_IN_FILE_STRUCT, ""); //$NON-NLS-1$

		// handle use in existing file
		extract(CLASS_STRUCT, USE_IN_FILE_STRUCT, USE_IN_STRUCT_COMPILED);
		set(USE_IN_FILE_STRUCT, ""); //$NON-NLS-1$

		if (data.isExistingFile && data.isInFirstBlock) {
			if (data.namespace != null && !data.namespace.isEmpty()) {
				set(NAMESPACE_NAME, data.namespace);
				compile(NAMESPACE_IN_FILE_STRUCT_COMPILED, NAMESPACE_IN_FILE_STRUCT, true);
			}
			for (int i = 0; i < data.imports.length; i++) {
				set(USE, data.imports[i]);
				compile(USE_IN_STRUCT_COMPILED, USE_IN_FILE_STRUCT, true);
			}
		}

		// handle php_content
		extract(INPUT, PHP_CONTENT_STRUCT, PHP_CONTENT_STRUCT_COMPILED);
		compile(PHP_CONTENT_STRUCT_COMPILED, PHP_CONTENT_STRUCT, false);

		// a new php file or a new block ==> include the PHP open/close tag in
		// string
		if (!data.isExistingFile || (data.isExistingFile && !data.isInFirstBlock)
				|| (data.isExistingFile && data.isInFirstBlock && !data.hasFirstBlock)) {
			compile(get(PHP_CONTENT_STRUCT));
			return compile(get(INPUT));
		}

		// an existing file, take only class code
		else {
			return compile(get(CLASS_STRUCT));
		}
	}

	@Override
	public String getRequiredPHPs() {
		return requiredPHPsBlock;
	}
}
