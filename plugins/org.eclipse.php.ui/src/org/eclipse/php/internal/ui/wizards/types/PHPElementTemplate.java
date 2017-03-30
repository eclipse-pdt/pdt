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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;

/**
 * This class represents the template for creating a PHP element code This class
 * is intended to be extended.
 * 
 */
public abstract class PHPElementTemplate extends TextTemplate {
	public static final String INPUT = "input"; //$NON-NLS-1$
	public static final String PHP_CONTENT_STRUCT = "php_content"; //$NON-NLS-1$
	public static final String PHP_CONTENT_STRUCT_COMPILED = "php_content_compiled"; //$NON-NLS-1$
	public static final String CLASS_STRUCT = "class"; //$NON-NLS-1$
	public static final String CLASS_STRUCT_COMPILED = "class_compiled"; //$NON-NLS-1$
	public static final String CLASS_NAME_VAR = "class_name"; //$NON-NLS-1$
	public static final String ELEMENT_TYPE_VAR = "element_type"; //$NON-NLS-1$
	public static final String FUNC_PHPDOC_STRUCT = "func_phpdoc"; //$NON-NLS-1$
	public static final String FUNC_PHPDOC_STRUCT_COMPILED = "func_phpdoc_compiled"; //$NON-NLS-1$
	public static final String FUNC_PHPDOC_PARAMS_STRUCT = "func_phpdoc_params"; //$NON-NLS-1$
	public static final String FUNC_PHPDOC_PARAMS_STRUCT_COMPILED = "func_phpdoc_params_compiled"; //$NON-NLS-1$
	public static final String FUNC_PHPDOC_PARAM_VAR = "func_phpdoc_param"; //$NON-NLS-1$
	public static final String FUNC_PHPDOC_SEE_VAR = "func_phpdoc_see"; //$NON-NLS-1$
	public static final String FUNC_PARAMS_STRUCT = "func_params"; //$NON-NLS-1$
	public static final String FUNC_PARAMS_STRUCT_COMPILED = "func_params_compiled"; //$NON-NLS-1$
	public static final String FUNC_PARAM_NAME_VAR = "func_param_name"; //$NON-NLS-1$
	public static final String FUNCTIONS_STRUCT = "functions"; //$NON-NLS-1$
	public static final String FUNCTIONS_STRUCT_COMPILED = "functions_compiled"; //$NON-NLS-1$
	public static final String FUNCTION_NAME_VAR = "func_name"; //$NON-NLS-1$
	public static final String FUNCTION_MODIFIER_VAR = "func_modifier"; //$NON-NLS-1$
	public static final String FUNCTION_STATIC_VAR = "func_static_modifier"; //$NON-NLS-1$
	public static final String FUNCTION_PARENT_CALL = "func_parent_call"; //$NON-NLS-1$
	public static final String REQUIRES_STRUCT = "requires"; //$NON-NLS-1$
	public static final String REQUIRES_STRUCT_COMPILED = "requires_compiled"; //$NON-NLS-1$
	public static final String REQUIRES_IN_FILE_STRUCT = "requires_in_file"; //$NON-NLS-1$
	public static final String REQUIRES_IN_FILE_STRUCT_COMPILED = "requires_in_file_compiled"; //$NON-NLS-1$
	public static final String REQUIRES_NAME_VAR = "require_location"; //$NON-NLS-1$
	public static final String SUPERCLASS_STRUCT = "superclass"; //$NON-NLS-1$
	public static final String SUPERCLASS_STRUCT_COMPILED = "superclass_compiled"; //$NON-NLS-1$
	public static final String SUPERCLASS_NAME_VAR = "superclass_name"; //$NON-NLS-1$
	public static final String INTERFACES_STRUCT = "interfaces"; //$NON-NLS-1$
	public static final String INTERFACES_STRUCT_COMPILED = "interfaces_compiled"; //$NON-NLS-1$
	public static final String INTERFACE_NAME_VAR = "interface_name"; //$NON-NLS-1$
	public static final String IMPLEMENTS_STRUCT = "implements"; //$NON-NLS-1$
	public static final String IMPLEMENTS_STRUCT_COMPILED = "implements_compiled"; //$NON-NLS-1$
	public static final String TRAITS_STRUCT = "traits"; //$NON-NLS-1$
	public static final String TRAITS_STRUCT_COMPILED = "traits_compiled"; //$NON-NLS-1$
	public static final String TRAIT_NAME_VAR = "trait_name"; //$NON-NLS-1$
	public static final String USESTRAITS_STRUCT = "usetrait"; //$NON-NLS-1$
	public static final String USESTRAITS_STRUCT_COMPILED = "usetrait_compiled"; //$NON-NLS-1$
	public static final String TODO_VAR = "todo_text"; //$NON-NLS-1$
	public static final String TODO_TEXT = "//TODO - Insert your code here"; //$NON-NLS-1$
	public static final String DEFAULT_PHPDOC_VAR = "default_phpdoc"; //$NON-NLS-1$
	public static final String DEFAULT_PHPDOC_TEXT = "/**\n *\n */"; //$NON-NLS-1$
	public static final String ABSTRACT_VAR = "abstract_var"; //$NON-NLS-1$
	public static final String FINAL_VAR = "final_var"; //$NON-NLS-1$
	public static final String ABSTRACT_TEXT = "abstract"; //$NON-NLS-1$
	public static final String FINAL_TEXT = "final"; //$NON-NLS-1$
	public static final String NAMESPACE_STRUCT = "namespace"; //$NON-NLS-1$
	public static final String NAMESPACE_NAME = "namespace_name"; //$NON-NLS-1$
	public static final String NAMESPACE_STRUCT_COMPILED = "namespace_compiled"; //$NON-NLS-1$
	public static final String NAMESPACE_IN_FILE_STRUCT = "namespace_in_file"; //$NON-NLS-1$
	public static final String NAMESPACE_IN_FILE_STRUCT_COMPILED = "namespace_in_file_compiled"; //$NON-NLS-1$
	public static final String USE_STRUCT = "use"; //$NON-NLS-1$
	public static final String USE = "use_full_name"; //$NON-NLS-1$
	public static final String USE_STRUCT_COMPILED = "use_compiled"; //$NON-NLS-1$
	public static final String USE_IN_FILE_STRUCT = "use_in_file"; //$NON-NLS-1$
	public static final String USE_IN_STRUCT_COMPILED = "use_in_file_compiled"; //$NON-NLS-1$

	public static final String[] EXCLUDE_PARAM_TYPES_LIST = new String[] { "boolean", "bool", "integer", "int", "float", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			"double", "string", "mixed", "void", "unknown_type" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	public String resolveTemplate() throws IOException {
		return set(INPUT, readTemplate());
	}

	public String readTemplate() throws IOException {
		URL url = FileLocator.find(PHPUiPlugin.getDefault().getBundle(), new Path(getTemplatePath()), null);
		url = FileLocator.resolve(url);
		final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) url.getContent()));
		String line;
		final StringBuilder buffer = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.setLength(buffer.length() - 1); // removing the extra new line
		return buffer.toString();
	}

	public String getVisibilityModifier(int modifiers) {
		if (Flags.isProtected(modifiers)) {
			return TypeWizardConstants.PROTECTED_MODIFIER;
		}
		if (Flags.isPublic(modifiers)) {
			return TypeWizardConstants.PUBLIC_MODIFIER;
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isStaticFunction(int funcModifiers) {
		return Flags.isStatic(funcModifiers);
	}

	public abstract String processTemplate(NewPHPElementData data);

	public abstract String getRequiredPHPs();

	public abstract String getTemplatePath();

	protected boolean isImported(NewPHPElementData data, IType superClass) {
		String name = superClass.getElementName();
		try {
			IModelElement parent = superClass.getParent();
			if (parent instanceof IType && PHPFlags.isNamespace(((IType) parent).getFlags())) {
				name = parent.getElementName() + "\\" + name; //$NON-NLS-1$
			}
		} catch (ModelException e) {
			// do not log, just continue
		}
		if ((data.realNamespace + "\\" + superClass.getElementName()) //$NON-NLS-1$
				.equals(name)) {
			return true;
		}
		for (String importEntry : data.imports) {
			if (importEntry.equals(name)) {
				return true;
			}
		}
		for (String importEntry : data.existingImports) {
			if (importEntry.equals(name)) {
				return true;
			}
		}
		return false;
	}

	protected String getDefaultPHPDoc() {
		return "/** \n * @author " + System.getProperty("user.name") //$NON-NLS-1$ //$NON-NLS-2$
				+ "\n * \n */"; //$NON-NLS-1$
	}

	protected void handleRequires(NewPHPElementData data) {
		extract(INPUT, REQUIRES_STRUCT, REQUIRES_STRUCT_COMPILED);
		set(REQUIRES_STRUCT, ""); //$NON-NLS-1$
		extract(CLASS_STRUCT, REQUIRES_IN_FILE_STRUCT, REQUIRES_IN_FILE_STRUCT_COMPILED);
		set(REQUIRES_IN_FILE_STRUCT, ""); //$NON-NLS-1$
		if (data.isExistingFile && data.isInFirstBlock) {
			for (int i = 0; i < data.requiredToAdd.length; i++) {
				set(REQUIRES_NAME_VAR, data.requiredToAdd[i]);
				compile(REQUIRES_IN_FILE_STRUCT_COMPILED, REQUIRES_IN_FILE_STRUCT, true);
			}
		} else {
			for (int i = 0; i < data.requiredToAdd.length; i++) {
				set(REQUIRES_NAME_VAR, data.requiredToAdd[i]);
				compile(REQUIRES_STRUCT_COMPILED, REQUIRES_STRUCT, true);
			}
		}
	}

}
