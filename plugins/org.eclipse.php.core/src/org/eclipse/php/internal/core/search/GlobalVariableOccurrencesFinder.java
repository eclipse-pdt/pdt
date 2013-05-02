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

import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * A global variable occurrences finder.
 * 
 * @author shalom
 */
public class GlobalVariableOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "GlobalVariableOccurrencesFinder"; //$NON-NLS-1$
	private String globalName;
	private boolean isGlobalScope;

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node (must be an {@link Identifier} or
	 *            {@link Scalar} instance)
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		isGlobalScope = true;
		if (node.getType() == ASTNode.SCALAR) {
			// We have a scalar inside a GLOBALS array.
			// For example: $GLOBALS['b'] = $GLOBALS['a'] + $GLOBALS['b'];
			globalName = ((Scalar) node).getStringValue();
			if (globalName.length() > 2 && isQuated(globalName)) {
				globalName = globalName.substring(1, globalName.length() - 1);
			}
			return null;
		}
		if (node.getType() == ASTNode.IDENTIFIER) {
			globalName = ((Identifier) node).getName();
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
		fDescription = Messages.format(BASE_DESCRIPTION, '$' + globalName);
		fASTRoot.accept(this);
	}

	/**
	 * class declaration
	 */
	public boolean visit(ClassDeclaration classDeclaration) {
		setGlobalScope(false);
		return true;
	}

	public void endVisit(ClassDeclaration classDeclaration) {
		setGlobalScope(true);
	}

	/**
	 * change the name of the function
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		setGlobalScope(false);
		return true;

	}

	public void endVisit(FunctionDeclaration functionDeclaration) {
		setGlobalScope(true);
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		setGlobalScope(false);
		return true;
	}

	public void endVisit(InterfaceDeclaration interfaceDeclaration) {
		setGlobalScope(true);
	}

	public boolean visit(FieldsDeclaration fieldDeclaration) {
		setGlobalScope(false);
		return true;
	}

	public void endVisit(FieldsDeclaration fieldsDeclaration) {
		setGlobalScope(true);
	}

	/**
	 * Visit $a on global references: $a = 5;
	 */
	public boolean visit(Variable variable) {
		if (!(variable.getParent() instanceof StaticDispatch)
				&& variable.isDollared()
				&& variable.getName().getType() == ASTNode.IDENTIFIER) {
			Identifier identifier = (Identifier) variable.getName();
			if (isGlobalScope && globalName.equals(identifier.getName())) {
				addOccurrence(variable);
			}
		}
		return true;
	}

	/**
	 * Make sure we mark the occurrences when selecting a scalar inside a
	 * GLOBALS array access.
	 */
	public boolean visit(Scalar scalar) {
		String stringValue = scalar.getStringValue();
		if (stringValue.length() > 2 && isQuated(stringValue)) {
			stringValue = stringValue.substring(1, stringValue.length() - 1);
		}
		if (globalName.equals(stringValue)) {
			ASTNode parent = scalar.getParent();
			if (parent.getType() == ASTNode.ARRAY_ACCESS) {
				Expression variableName = ((ArrayAccess) parent).getName();
				if (variableName.getType() == ASTNode.VARIABLE) {
					final Variable var = (Variable) variableName;
					Expression varName = var.getName();
					if (var.isDollared()
							&& varName.getType() == ASTNode.IDENTIFIER) {
						final String name = ((Identifier) varName).getName();
						if ("GLOBALS".equals(name) || "_GLOBALS".equals(name)) { //$NON-NLS-1$ //$NON-NLS-2$
							addOccurrence(scalar);
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Visit $a on global references (on function/methods) : ...global $a;$a =
	 * 5;...
	 */
	public boolean visit(GlobalStatement globalStatement) {
		final List<Variable> variables = globalStatement.variables();
		for (final Variable variable : variables) {
			if (variable.isDollared()) {
				Identifier identifier = (Identifier) variable.getName();
				if (globalName.equals(identifier.getName())) {
					addOccurrence(variable);
					setGlobalScope(true);
				}
			}
		}
		return true;
	}

	/**
	 * Visit $GLOBALS['variableName'] occurrences
	 */
	public boolean visit(ArrayAccess arrayAccess) {
		// check the case of $GLOBALS['var']
		final Expression variableName = arrayAccess.getName();
		if (variableName.getType() == ASTNode.VARIABLE) {
			final Variable var = (Variable) variableName;
			Expression varName = var.getName();
			if (var.isDollared() && varName.getType() == ASTNode.IDENTIFIER) {
				final Identifier id = (Identifier) varName;
				if (id.getName().equals("GLOBALS")) { //$NON-NLS-1$
					final Expression index = arrayAccess.getIndex();
					if (index != null && index.getType() == ASTNode.SCALAR) {
						Scalar scalar = (Scalar) index;
						final String stringValue = scalar.getStringValue();
						if (stringValue.length() > 2 && isQuated(stringValue)) {
							if (globalName.equals(stringValue.substring(1,
									stringValue.length() - 1))) {
								addOccurrence(scalar);
							}
						}
					}
				}
			}
		}
		return true;
	}

	private boolean isQuated(String stringValue) {
		char first = stringValue.charAt(0);
		char last = stringValue.charAt(stringValue.length() - 1);
		return (first == '\'' && last == '\'' || first == '\"' && last == '\"');
	}

	private void addOccurrence(ASTNode node) {
		int readWriteType = getOccurrenceType(node);
		String desc = fDescription;
		if (readWriteType == IOccurrencesFinder.F_WRITE_OCCURRENCE) {
			desc = Messages.format(BASE_WRITE_DESCRIPTION, '$' + globalName);
		}
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(),
				readWriteType, desc));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#
	 * getOccurrenceReadWriteType
	 * (org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		if (node.getType() == ASTNode.VARIABLE) {
			Variable variable = (Variable) node;
			ASTNode parent = variable.getParent();
			int parentType = parent.getType();
			if (parentType == ASTNode.ASSIGNMENT) {
				Assignment assignment = (Assignment) parent;
				if (assignment.getLeftHandSide() == node) {
					return IOccurrencesFinder.F_WRITE_OCCURRENCE;
				}
			} else if (parentType == ASTNode.POSTFIX_EXPRESSION
					|| parentType == ASTNode.PREFIX_EXPRESSION
					|| parentType == ASTNode.CATCH_CLAUSE) {
				return IOccurrencesFinder.F_WRITE_OCCURRENCE;
			} else if (parentType == ASTNode.FOR_EACH_STATEMENT) {
				if (variable.getLocationInParent() != ForEachStatement.EXPRESSION_PROPERTY) {
					return IOccurrencesFinder.F_WRITE_OCCURRENCE;
				}
			}
		}
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return globalName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param isGlobalScope
	 *            the isGlobalScope to set
	 */
	public void setGlobalScope(boolean isGlobalScope) {
		this.isGlobalScope = isGlobalScope;
	}
}
