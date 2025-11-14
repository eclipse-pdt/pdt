/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class FormalParameterByReference extends FormalParameter {

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName,
			Expression defaultValue, boolean isMandatory, boolean isVariadic, List<PropertyHook> hooks) {
		super(start, end, type, parameterName, defaultValue, isMandatory, isVariadic, hooks);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName,
			Expression defaultValue, boolean isMandatory, boolean isVariadic) {
		super(start, end, type, parameterName, defaultValue, isMandatory, isVariadic);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName,
			Expression defaultValue) {
		super(start, end, type, parameterName, defaultValue);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName,
			boolean isMandatory) {
		super(start, end, type, parameterName, isMandatory);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName) {
		super(start, end, type, parameterName);
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.FORMAL_PARAMETER_BYREF;
	}
}
