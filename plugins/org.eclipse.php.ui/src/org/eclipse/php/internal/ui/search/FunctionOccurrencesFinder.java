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
package org.eclipse.php.internal.ui.search;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Functions occurrences finder.
 * 
 * @author shalom
 */
public class FunctionOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "FunctionOccurrencesFinder"; //$NON-NLS-1$
	private String functionName;

	/**
	 * @param root the AST root
	 * @param node the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		if (node.getType() == ASTNode.IDENTIFIER) {
			functionName = ((Identifier) node).getName();
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description"; //$NON-NLS-1$
		return fDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences()
	 */
	protected void findOccurrences() {
		fDescription = Messages.format(BASE_DESCRIPTION, functionName + BRACKETS);
		fASTRoot.accept(this);
	}

	/**
	 * Visit the function declaration
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		// Handle nesting functions that might be nested inside a method declaration
		ASTNode parent = functionDeclaration.getParent();
		while (parent.getType() == ASTNode.BLOCK || parent.getType() == ASTNode.FUNCTION_DECLARATION) {
			parent = parent.getParent();
		}
		if (parent.getType() != ASTNode.METHOD_DECLARATION) {
			// check the function name
			Identifier name = functionDeclaration.getFunctionName();
			if (functionName.equalsIgnoreCase(name.getName())) {
				fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), getOccurrenceType(name), fDescription));
			}
		}
		return true;
	}

	/**
	 * skip static call invocation, and add to changes list the global calls
	 */
	public boolean visit(FunctionInvocation functionInvocation) {
		final Expression functionName = functionInvocation.getFunctionName().getName();
		final int invocationParent = functionInvocation.getParent().getType();
		if (functionName.getType() == ASTNode.IDENTIFIER && invocationParent != ASTNode.METHOD_INVOCATION && invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
			final Identifier identifier = (Identifier) functionName;
			if (this.functionName.equalsIgnoreCase(identifier.getName())) {
				fResult.add(new OccurrenceLocation(functionName.getStart(), functionName.getLength(), getOccurrenceType(functionName), fDescription));
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return functionName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}