/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.extract;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;

/**
 * This visitor is activated in order to get a list of name suggestions for
 * extracting elements
 * 
 * @author Eden K., 2008
 * 
 */
public class NameSuggestVisitor extends AbstractVisitor {
	private LinkedList<String> suggestions = new LinkedList<String>();

	public List<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(LinkedList<String> suggestions) {
		this.suggestions = suggestions;
	}

	public boolean visit(Identifier identifier) {
		// add to the top of the list since we want the suggestions to be in
		// reversed order
		// and ignore duplicates
		if (!suggestions.contains(identifier.getName())) {
			suggestions.addFirst(identifier.getName());
		}
		return false;
	}

	public boolean visit(FunctionInvocation functionInvocation) {
		functionInvocation.getFunctionName().accept(this);
		// we want to skip the parameters
		return false;
	}

	public boolean visit(Scalar scalar) {
		String value = ""; //$NON-NLS-1$
		switch (scalar.getScalarType()) {
		case Scalar.TYPE_INT:
			value = "i"; //$NON-NLS-1$
			break;
		case Scalar.TYPE_REAL:
			value = "d"; //$NON-NLS-1$
			break;
		case Scalar.TYPE_STRING:
			// boolean strings will get "bool" as a name, other strings will get
			// "str"
			if (scalar.getStringValue().equalsIgnoreCase("true") || scalar.getStringValue().equalsIgnoreCase("false")) { //$NON-NLS-1$ //$NON-NLS-2$
				value = "bool"; //$NON-NLS-1$
				// } else if (scalar.getStringValue().equalsIgnoreCase("null")) { //$NON-NLS-1$
				// value = "unknown"; //$NON-NLS-1$
			} else {
				value = "str"; //$NON-NLS-1$
			}
			break;
		case Scalar.TYPE_SYSTEM:
			value = scalar.getStringValue().toLowerCase().replaceAll("_", ""); //$NON-NLS-1$ //$NON-NLS-2$
			break;
		default:
			break;
		}
		suggestions.add(value);
		return false;
	}

}
