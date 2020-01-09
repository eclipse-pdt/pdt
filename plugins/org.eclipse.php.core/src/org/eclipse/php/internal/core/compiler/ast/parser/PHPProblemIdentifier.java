/*******************************************************************************
 * Copyright (c) 2014, 2017, 2019 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifierExtension;
import org.eclipse.php.internal.core.PHPCorePlugin;

/**
 * Class provides "PHP Syntax Error" marker type for DLTK problem mechanism.
 * 
 * @author Michal Niewrzal
 */
public enum PHPProblemIdentifier implements IProblemIdentifier, IProblemIdentifierExtension {

	SYNTAX, USE_STATEMENTS /* deprecated */, AbstractMethodInAbstractClass, BodyForAbstractMethod, MethodRequiresBody, AbstractMethodsInConcreteClass, UndefinedType, ClassExtendFinalClass, CannotInstantiateType, ImportNotFound, DuplicateImport, UnusedImport, UnnecessaryImport, DuplicateDeclaration, DuplicateMethodDeclaration, DuplicateConstantDeclaration, DuplicateFieldDeclaration, AbstractMethodMustBeImplemented, SuperclassMustBeAClass, SuperInterfaceMustBeAnInterface, CannotUseTypeAsTrait, NestedNamespaceDeclarations, InvalidConstantExpression, ReassignAutoGlobalVariable, CannotUseReservedWord, UndefinedVariable, UnusedVariable

	;

	public static final String MARKER_TYPE_ID = "org.eclipse.php.core.phpproblemmarker"; //$NON-NLS-1$

	@Override
	public String contributor() {
		return PHPCorePlugin.ID;
	}

	@Override
	public String getMarkerType() {
		return MARKER_TYPE_ID;
	}

}
