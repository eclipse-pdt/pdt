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
package org.eclipse.php.internal.core.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Functions occurrences finder.
 * 
 * @author shalom
 */
public class FunctionOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "FunctionOccurrencesFinder"; //$NON-NLS-1$
	private String functionName;
	private ASTNode erroneousNode;
	private Identifier nameNode;
	private Map<Identifier, String> nodeToFullName = new HashMap<Identifier, String>();

	// private String nameSpace;

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node (must be an {@link Identifier} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		fProblems = getProblems(root);

		if (node.getType() == ASTNode.IDENTIFIER) {
			nameNode = (Identifier) node;
			if (nameNode.getParent() instanceof NamespaceName) {
				nameNode = (NamespaceName) nameNode.getParent();
			}
			functionName = ((Identifier) nameNode).getName();
			// functionName = getFullName(root, identifier);
			if (hasProblems(node.getStart(), node.getEnd())) {
				erroneousNode = node;
			}
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description"; //$NON-NLS-1$
		return fDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences
	 * ()
	 */
	protected void findOccurrences() {
		fDescription = Messages.format(BASE_DESCRIPTION, functionName
				+ BRACKETS);
		if (erroneousNode != null) {
			// Add just this node in order to handle re-factoring properly
			fResult.add(new OccurrenceLocation(erroneousNode.getStart(),
					erroneousNode.getLength(),
					getOccurrenceType(erroneousNode), fDescription));
		} else {
			fASTRoot.accept(this);
			if (nodeToFullName.containsKey(nameNode)) {
				String fullName = nodeToFullName.get(nameNode);
				for (Iterator<Identifier> iterator = nodeToFullName.keySet()
						.iterator(); iterator.hasNext();) {
					Identifier nameNode = iterator.next();
					if (nodeToFullName.get(nameNode).equals(fullName)) {
						fResult.add(new OccurrenceLocation(nameNode.getStart(),
								nameNode.getLength(),
								getOccurrenceType(nameNode), fDescription));
					}
				}
			}
		}
	}

	/**
	 * Visit the function declaration
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		// Handle nesting functions that might be nested inside a method
		// declaration
		ASTNode parent = functionDeclaration.getParent();
		while (parent.getType() == ASTNode.BLOCK
				|| parent.getType() == ASTNode.FUNCTION_DECLARATION) {
			parent = parent.getParent();
		}
		if (parent.getType() != ASTNode.METHOD_DECLARATION) {
			// check the function name
			Identifier name = functionDeclaration.getFunctionName();
			String fullName = getFullName(name, fLastUseParts,
					fCurrentNamespace);
			nodeToFullName.put(name, fullName);
			// if (this.functionName.equalsIgnoreCase(fullName)) {
			// fResult.add(new OccurrenceLocation(name.getStart(), name
			// .getLength(), getOccurrenceType(name), fDescription));
			// }
		}
		return true;
	}

	/**
	 * skip static call invocation, and add to changes list the global calls
	 */
	public boolean visit(FunctionInvocation functionInvocation) {
		final Expression functionName = functionInvocation.getFunctionName()
				.getName();
		final int invocationParent = functionInvocation.getParent().getType();
		if ((functionName.getType() == ASTNode.IDENTIFIER || functionName
				.getType() == ASTNode.NAMESPACE_NAME)
				&& invocationParent != ASTNode.METHOD_INVOCATION
				&& invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
			final Identifier identifier = (Identifier) functionName;
			String fullName = getFullName(identifier, fLastUseParts,
					fCurrentNamespace);
			nodeToFullName.put(identifier, fullName);
			// if (this.functionName.equalsIgnoreCase(fullName)) {
			// fResult.add(new OccurrenceLocation(functionName.getStart(),
			// functionName.getLength(),
			// getOccurrenceType(functionName), fDescription));
			// }
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#
	 * getOccurrenceReadWriteType
	 * (org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return functionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}