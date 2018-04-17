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
package org.eclipse.php.internal.core.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.php.core.validation.IProblemPreferences;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;

public class PHPCorePreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 *      initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		CorePreferenceConstants.initializeDefaultValues();

		IProblemPreferences problemPreferences = PHPCorePlugin.getDefault().getProblemPreferences();

		problemPreferences.setSeverity(PHPProblemIdentifier.SYNTAX, ProblemSeverity.ERROR, DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.MethodRequiresBody, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.BodyForAbstractMethod, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);

		problemPreferences.setSeverity(PHPProblemIdentifier.USE_STATEMENTS, ProblemSeverity.WARNING,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.AbstractMethodInAbstractClass, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.MethodRequiresBody, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.AbstractMethodsInConcreteClass, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.UndefinedType, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.ClassExtendFinalClass, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.CannotInstantiateType, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.ImportNotFound, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.DuplicateImport, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.UnusedImport, ProblemSeverity.WARNING,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.UnnecessaryImport, ProblemSeverity.WARNING,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.DuplicateDeclaration, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.AbstractMethodMustBeImplemented, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.SuperclassMustBeAClass, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.SuperInterfaceMustBeAnInterface, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.CannotUseTypeAsTrait, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.ReassignAutoGlobalVariable, ProblemSeverity.ERROR,
				DefaultScope.INSTANCE);

		problemPreferences.setSeverity(PHPProblemIdentifier.UndefinedVariable, ProblemSeverity.WARNING,
				DefaultScope.INSTANCE);
		problemPreferences.setSeverity(PHPProblemIdentifier.UnusedVariable, ProblemSeverity.WARNING,
				DefaultScope.INSTANCE);

	}

}