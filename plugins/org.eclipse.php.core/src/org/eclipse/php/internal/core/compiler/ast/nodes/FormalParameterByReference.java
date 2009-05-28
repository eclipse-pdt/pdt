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
package org.eclipse.php.internal.core.compiler.ast.nodes;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;

public class FormalParameterByReference extends FormalParameter {

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName, Expression defaultValue) {
		super(start, end, type, parameterName, defaultValue);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName, boolean isMandatory) {
		super(start, end, type, parameterName, isMandatory);
	}

	public FormalParameterByReference(int start, int end, SimpleReference type, VariableReference parameterName) {
		super(start, end, type, parameterName);
	}

	public int getKind() {
		return ASTNodeKinds.FORMAL_PARAMETER_BYREF;
	}
}
