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
import org.eclipse.php.internal.core.PHPCoreConstants;
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

		DefaultScope.INSTANCE.getNode(PHPCoreConstants.PLUGIN_ID).node(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID)
				.putBoolean(PHPCoreConstants.ENABLED, true);

		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.SYNTAX, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.MethodRequiresBody, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.BodyForAbstractMethod, ProblemSeverity.ERROR);

		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.USE_STATEMENTS, ProblemSeverity.WARNING);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.AbstractMethodInAbstractClass,
				ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.MethodRequiresBody, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.AbstractMethodsInConcreteClass,
				ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.UndefinedType, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.ClassExtendFinalClass, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.CannotInstantiateType, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.ImportNotFound, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.DuplicateImport, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.UnusedImport, ProblemSeverity.WARNING);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.UnnecessaryImport, ProblemSeverity.WARNING);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.DuplicateDeclaration, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.AbstractMethodMustBeImplemented,
				ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.SuperclassMustBeAClass, ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.SuperInterfaceMustBeAnInterface,
				ProblemSeverity.ERROR);
		problemPreferences.setDefaultSeverity(PHPProblemIdentifier.CannotUseTypeAsTrait, ProblemSeverity.ERROR);

	}

}