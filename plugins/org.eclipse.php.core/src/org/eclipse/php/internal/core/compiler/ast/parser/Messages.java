/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.core.compiler.ast.parser.messages"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	public static String AbstractASTParser_0;
	public static String AbstractASTParser_1;
	public static String AbstractASTParser_4;
	public static String AbstractASTParser_5;
	public static String AbstractASTParser_6;
	public static String AbstractASTParser_7;
	public static String AbstractASTParser_AbstractAsFinalError;
	public static String AbstractASTParser_AbstractPropertyError;
	public static String AbstractASTParser_FinalPropertyError;
	public static String AbstractASTParser_MultipleAbstractModifierError;
	public static String AbstractASTParser_MultipleAccessModifiersError;
	public static String AbstractASTParser_MultipleAccessSetModifiersError;
	public static String AbstractASTParser_MultipleFinalModifierError;
	public static String AbstractASTParser_MultipleStaticModifiersError;

	public static String AbstractMethodInAbstractClass;
	public static String BodyForAbstractMethod;
	public static String MethodRequiresBody;
	public static String AbstractMethodsInConcreteClass;
	public static String UndefinedType;
	public static String AbstractMethodMustBeImplemented;
	public static String ImportNotFound;
	public static String DuplicateImport;
	public static String DuplicateDeclaration;
	public static String DuplicateMethodDeclaration;
	public static String DuplicateFieldDeclaration;
	public static String DuplicateConstantDeclaration;
	public static String UnusedImport;
	public static String UnnecessaryImport;
	public static String ClassExtendFinalClass;
	public static String CannotInstantiateType;
	public static String SuperclassMustBeAClass;
	public static String SuperInterfaceMustBeAnInterface;
	public static String CannotUseTypeAsTrait;
	public static String NestedNamespaceDeclarations;
	public static String InvalidConstantExpression;
	public static String DynamicClassNotAllowed;
	public static String UsupportedNonConstantOperand;
	public static String OperatorAcceptOnlyPositiveNumbers;
	public static String ReassignAutoGlobalVariable;
	public static String HeredocMixedIndentation;
	public static String HeredocInvalidIndentation;
	public static String CannotUseReservedWord;
	public static String UnexpectedNamespaceDeclaration;
	public static String FirstTypeMustMatchFileName;
	public static String PropertyInEnum;
	public static String EnumCaseInClass;
	public static String EnumCaseWithType;
	public static String EnumCaseWithoutType;
	public static String IntefaceMethodAccessType;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return ""; //$NON-NLS-1$
		}
	}

	public static String getString(String key, String... args) {
		try {
			return MessageFormat.format(RESOURCE_BUNDLE.getString(key), (Object[]) args);
		} catch (MissingResourceException e) {
			return ""; //$NON-NLS-1$
		}
	}
}
