/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.dltk.evaluation.types.SimpleType;
import org.eclipse.dltk.ti.goals.FixedAnswerEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;

public class ScalarEvaluator extends FixedAnswerEvaluator {

	public ScalarEvaluator(IGoal goal, Scalar scalar) {
		super(goal, evaluateScalar(scalar));
	}

	private static Object evaluateScalar(Scalar scalar) {
		int scalarType = scalar.getScalarType();
		
		int simpleType = SimpleType.TYPE_NONE;
		switch (scalarType) {
			case Scalar.TYPE_INT:
			case Scalar.TYPE_REAL:
				simpleType = SimpleType.TYPE_NUMBER;
				break;
			case Scalar.TYPE_STRING:
				if ("null".equalsIgnoreCase(scalar.getValue())) {
					simpleType = SimpleType.TYPE_NULL;
					break;
				}
			case Scalar.TYPE_SYSTEM:
				simpleType = SimpleType.TYPE_STRING;
				break;
		}
		return new SimpleType(simpleType);
	}
}
