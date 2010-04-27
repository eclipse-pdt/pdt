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

import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.ast.nodes.*;

public class BreakContinueTargetFinder extends AbstractOccurrencesFinder {

	private static final String TARGET_OF = CoreMessages
			.getString("BreakContinueTargetFinder.0"); //$NON-NLS-1$
	public static final String ID = "BreakContinueTargetFinder"; //$NON-NLS-1$
	private static final int[] TARGETS = { ASTNode.FOR_STATEMENT,
			ASTNode.WHILE_STATEMENT, ASTNode.SWITCH_STATEMENT,
			ASTNode.FOR_EACH_STATEMENT, ASTNode.DO_STATEMENT };
	private static final int[] STOPPERS = { ASTNode.PROGRAM,
			ASTNode.FUNCTION_DECLARATION };

	private FunctionDeclaration fFunctionDeclaration;
	private Statement statement;
	private int nestingLevel;

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		nestingLevel = 0;
		if (node != null) {
			if (node.getType() == ASTNode.BREAK_STATEMENT) {
				BreakStatement bStatement = (BreakStatement) node;
				nestingLevel = getNestingLevel(bStatement.getExpression());
				statement = bStatement;
				return null;
			}
			if (node.getType() == ASTNode.CONTINUE_STATEMENT) {
				ContinueStatement cStatement = (ContinueStatement) node;
				nestingLevel = getNestingLevel(cStatement.getExpression());
				statement = cStatement;
				return null;
			}
		}
		fDescription = "BreakContinueTargetFinder_occurrence_description"; //$NON-NLS-1$
		return fDescription;
	}

	/*
	 * Returns the nesting level of the expression. Since the Break and the
	 * Continue can provide an optional nesting level to break or continue, the
	 * expression attached to the statements hold this information. In case the
	 * (optional) expression is null, this method returns a default value of 1.
	 * Also note that the break argument accepts any expression, including a
	 * function result. But since this result is dynamically determined at
	 * runtime, we return a nesting level of 0 in this case. Zero nesting level
	 * will not mark any occurrence for the break or the continue.
	 * 
	 * @param expression
	 * 
	 * @return The nesting level determined from the expression (>=0).
	 */
	protected final int getNestingLevel(Expression expression) {
		if (expression == null) {
			return 1;
		}
		if (expression.getType() == ASTNode.SCALAR) {
			Scalar scalar = (Scalar) expression;
			return Integer.parseInt(scalar.getStringValue());
		}
		return 0;
	}

	/*
	 * Returns true if the given node type is one of the TARGET nodes.
	 */
	private boolean isTarget(int nodeType) {
		for (int target : TARGETS) {
			if (target == nodeType) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Returns true if the given node type is one of the STOPPER nodes.
	 */
	private boolean isStopper(int nodeType) {
		return nodeType == STOPPERS[0] || nodeType == STOPPERS[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences
	 * ()
	 */
	protected void findOccurrences() {
		if (nestingLevel == 0) {
			// do nothing
			return;
		}
		String nestingStr = ""; //$NON-NLS-1$
		if (nestingLevel > 1) {
			nestingStr = ' ' + Integer.toString(nestingLevel);
		}
		fDescription = Messages
				.format(
						TARGET_OF,
						(statement.getType() == ASTNode.BREAK_STATEMENT) ? "break" + nestingStr : "continue" + nestingStr); //$NON-NLS-1$ //$NON-NLS-2$
		// No need for the visitor. Just traverse up the AST tree and locate the
		// target.
		addOccurrences();
	}

	/*
	 * Traverse up the AST tree and locate the node to add as an occurrence
	 * target.
	 */
	private void addOccurrences() {
		boolean targetFound = false;
		int nestingCount = 0;
		int blockEnd = -1;
		ASTNode parent = statement.getParent();
		while (!targetFound) {
			int type = parent.getType();
			if (isStopper(type)) {
				break;
			}
			if (isTarget(type)) {
				nestingCount++;
				if (nestingCount == nestingLevel) {
					// Found the target level
					fResult.add(new OccurrenceLocation(parent.getStart(),
							getLength(parent), getOccurrenceType(null),
							fDescription));
					// In cases where we have a block, mark the closing curly
					// bracket
					if (blockEnd > -1) {
						fResult.add(new OccurrenceLocation(blockEnd - 1, 1,
								getOccurrenceType(null), fDescription));
					}
					targetFound = true;
				} else {
					blockEnd = -1;
				}
			} else if (type == ASTNode.BLOCK) {
				blockEnd = parent.getEnd();
			} else {
				blockEnd = -1;
			}
			parent = parent.getParent();
		}
	}

	/*
	 * Returns the length of the target node start. For example: A
	 * WhileStatement will return a length of 5, which is the length of the
	 * keyword 'while'.
	 */
	private int getLength(ASTNode parent) {
		switch (parent.getType()) {
		case ASTNode.FOR_STATEMENT:
			return 3;
		case ASTNode.WHILE_STATEMENT:
			return 5;
		case ASTNode.SWITCH_STATEMENT:
			return 6;
		case ASTNode.FOR_EACH_STATEMENT:
			return 7;
		case ASTNode.DO_STATEMENT:
			return 2;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#
	 * getOccurrenceReadWriteType
	 * (org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceType(ASTNode node) {
		return IOccurrencesFinder.K_EXIT_POINT_OCCURRENCE;
	}

	// public boolean visit(ReturnStatement node) {
	// fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(),
	// getOccurrenceType(null), fDescription));
	// return super.visit(node);
	// }

	public Program getASTRoot() {
		return fASTRoot;
	}

	public String getElementName() {
		return fFunctionDeclaration.getFunctionName().getName();
	}

	public String getID() {
		return ID;
	}

	public String getJobLabel() {
		return "BreakContinueTargetFinder_job_label"; //$NON-NLS-1$
	}

	public int getSearchKind() {
		return IOccurrencesFinder.K_BREAK_TARGET_OCCURRENCE;
	}

	public String getUnformattedPluralLabel() {
		return "BreakContinueTargetFinder_label_plural"; //$NON-NLS-1$
	}

	public String getUnformattedSingularLabel() {
		return "BreakContinueTargetFinder_label_singular"; //$NON-NLS-1$
	}
}
