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
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.FixedAnswerEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;

public class ScalarEvaluator extends FixedAnswerEvaluator {

	public ScalarEvaluator(IGoal goal, Scalar scalar) {
		super(goal, evaluateScalar(goal, scalar));
	}

	private static Object evaluateScalar(IGoal goal, Scalar scalar) {
		int scalarType = scalar.getScalarType();

		IEvaluatedType simpleType = PHPSimpleTypes.VOID;
		switch (scalarType) {
		case Scalar.TYPE_INT:
		case Scalar.TYPE_REAL:
			simpleType = PHPSimpleTypes.NUMBER;
			break;
		case Scalar.TYPE_STRING:
			if ("null".equalsIgnoreCase(scalar.getValue())) { //$NON-NLS-1$
				simpleType = PHPSimpleTypes.NULL;
				break;
			}
			// checking specific case for "return $this;" statement
			if ("this".equalsIgnoreCase(scalar.getValue())) { //$NON-NLS-1$
				IContext context = goal.getContext();
				if (context instanceof MethodContext) {
					MethodDeclaration methodNode = ((MethodContext) context).getMethodNode();
					if (methodNode != null) {
						String declaringTypeName = methodNode.getDeclaringTypeName();
						if (declaringTypeName != null) {
							IEvaluatedType resolved = PHPSimpleTypes.fromString(declaringTypeName);
							if (resolved == null) {
								return new PHPClassType(declaringTypeName);
							}
						}
					}
				}
			}

		case Scalar.TYPE_SYSTEM:
			String value = scalar.getValue();
			if ("true".equalsIgnoreCase(value) //$NON-NLS-1$
					|| "false".equalsIgnoreCase(value)) { //$NON-NLS-1$
				simpleType = PHPSimpleTypes.BOOLEAN;
			} else {
				simpleType = PHPSimpleTypes.STRING;
			}
			break;
		}
		return simpleType;
	}
}
