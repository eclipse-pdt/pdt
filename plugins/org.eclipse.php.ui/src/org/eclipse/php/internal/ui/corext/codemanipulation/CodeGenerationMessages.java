/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.codemanipulation;

import org.eclipse.osgi.util.NLS;

public final class CodeGenerationMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.php.internal.ui.corext.codemanipulation.CodeGenerationMessages";//$NON-NLS-1$

	private CodeGenerationMessages() {
		// Do not instantiate
	}

	public static String AddGetterSetterOperation_description;
	public static String AddGetterSetterOperation_error_input_type_not_found;
	public static String AddImportsOperation_description;
	public static String AddImportsOperation_error_not_visible_class;
	public static String AddImportsOperation_error_notresolved_message;
	public static String AddImportsOperation_error_importclash;
	public static String AddImportsOperation_error_invalid_selection;
	public static String AddUnimplementedMethodsOperation_description;
	public static String AddCustomConstructorOperation_description;
	public static String OrganizeImportsOperation_description;
	public static String AddJavaDocStubOperation_description;
	public static String AddDelegateMethodsOperation_monitor_message;
	public static String GenerateHashCodeEqualsOperation_description;

	static {
		NLS.initializeMessages(BUNDLE_NAME, CodeGenerationMessages.class);
	}

	public static String GenerateHashCodeEqualsOperation_hash_code_comment;
	public static String GenerateHashCodeEqualsOperation_tag_param;
	public static String GenerateHashCodeEqualsOperation_hash_code_argument;
	public static String GenerateHashCodeEqualsOperation_tag_return;
	public static String GenerateHashCodeEqualsOperation_return_comment;
	public static String GenerateToStringOperation_customStringBuilder_style_name;
	public static String GenerateToStringOperation_objectClassGetNameVariableDescription;
	public static String GenerateToStringOperation_objectClassNameVariableDescription;
	public static String GenerateToStringOperation_objectHashCodeVariableDescription;
	public static String GenerateToStringOperation_objectIdentityHashCodeVariableDescription;
	public static String GenerateToStringOperation_objectSuperToStringVariableDescription;
	public static String GenerateToStringOperation_description;
	public static String GenerateToStringOperation_memberNameParenthesesVariableDescription;
	public static String GenerateToStringOperation_memberNameVariableDescription;
	public static String GenerateToStringOperation_memberValueVariableDescription;
	public static String GenerateToStringOperation_otherFieldsVariableDescription;
	public static String GenerateToStringOperation_string_format_style_name;
	public static String GenerateToStringOperation_StringBuilder_chained_style_name;
	public static String GenerateToStringOperation_stringBuilder_style_name;
	public static String GenerateToStringOperation_stringConcatenation_style_name;
	public static String GenerateToStringOperation_warning_no_arrays_collections_with_this_style;
}
