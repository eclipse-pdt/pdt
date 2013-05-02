/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.template.php;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to get NLSed messages.
 */
final class PhpTemplateMessages extends NLS {

	private static final String BUNDLE_NAME = PhpTemplateMessages.class
			.getName();

	private PhpTemplateMessages() {
		// Do not instantiate
	}

	public static String ContextType_error_multiple_cursor_variables;
	public static String CompilationUnitContextType_variable_description_file;
	public static String CompilationUnitContextType_variable_description_primary_type_name;
	public static String CompilationUnitContextType_variable_description_enclosing_method;
	public static String CompilationUnitContextType_variable_description_enclosing_type;
	public static String CompilationUnitContextType_variable_description_enclosing_package;
	public static String CompilationUnitContextType_variable_description_enclosing_project;
	public static String CompilationUnitContextType_variable_description_enclosing_method_arguments;
	public static String CompilationUnitContextType_variable_description_return_type;
	public static String CodeTemplateContextType_0;
	public static String CodeTemplateContextType_variable_description_todo;
	public static String CodeTemplateContextType_variable_description_packdeclaration;
	public static String CodeTemplateContextType_variable_description_typedeclaration;
	public static String CodeTemplateContextType_variable_description_getterfieldname;
	public static String CodeTemplateContextType_variable_description_getterfieldtype;
	public static String CodeTemplateContextType_variable_description_fieldname;
	public static String CodeTemplateContextType_variable_description_fieldtype;
	public static String CodeTemplateContextType_variable_description_barefieldname;
	public static String CodeTemplateContextType_variable_description_param;
	public static String CodeTemplateContextType_variable_description_typecomment;
	public static String CodeTemplateContextType_variable_description_exceptiontype;
	public static String CodeTemplateContextType_variable_description_exceptionvar;
	public static String CodeTemplateContextType_variable_description_enclosingtype;
	public static String CodeTemplateContextType_variable_description_typename;
	public static String CodeTemplateContextType_variable_description_enclosingmethod;
	public static String CodeTemplateContextType_variable_description_bodystatement;
	public static String CodeTemplateContextType_variable_description_returntype;
	public static String CodeTemplateContextType_variable_description_tags;
	public static String CodeTemplateContextType_variable_description_see_overridden_tag;
	public static String CodeTemplateContextType_variable_description_see_target_tag;
	public static String CodeTemplateContextType_variable_description_filename;
	public static String CodeTemplateContextType_variable_description_filecomment;
	public static String CodeTemplateContextType_variable_description_packagename;
	public static String CodeTemplateContextType_variable_description_projectname;
	public static String CodeTemplateContextType_validate_unknownvariable;
	public static String CodeTemplateContextType_validate_missingvariable;
	public static String CodeTemplateContextType_validate_invalidcomment;
	public static String TemplateSet_3;
	public static String TemplateSet_error_missing_attribute;
	public static String TemplateSet_error_read;
	public static String TemplateSet_error_write;
	public static String Context_error_cannot_evaluate;

	static {
		NLS.initializeMessages(BUNDLE_NAME, PhpTemplateMessages.class);
	}
}