/*******************************************************************************
 * Copyright (c) 2014 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public enum PhpProblemIdentifier implements IProblemIdentifier, IProblemIdentifierExtension {

	SYNTAX, USE_STATEMENTS, AbstractMethodInAbstractClass, BodyForAbstractMethod, MethodRequiresBody, AbstractMethodsInConcreteClass;

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
