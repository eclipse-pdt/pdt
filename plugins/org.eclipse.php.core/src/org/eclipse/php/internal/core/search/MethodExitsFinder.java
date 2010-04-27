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
import org.eclipse.php.internal.core.corext.ASTNodes;

/**
 * Searches for exit execution path for a given method
 * 
 * @author Roy, 2008
 * 
 */
public class MethodExitsFinder extends AbstractOccurrencesFinder {

	private static final String EXIT_POINT_OF = CoreMessages
			.getString("MethodExitsFinder.0"); //$NON-NLS-1$
	public static final String ID = "MethodExitsFinder"; //$NON-NLS-1$
	private FunctionDeclaration fFunctionDeclaration;
	private ASTNode fExitPointNode;

	/**
	 * @param root
	 *            the AST root
	 * @param node
	 *            the selected node
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot = root;
		fExitPointNode = node;
		if (isExitExecutionPath(node)) {
			fFunctionDeclaration = (FunctionDeclaration) ASTNodes.getParent(
					node, ASTNode.FUNCTION_DECLARATION);
			if (fFunctionDeclaration == null)
				return "MethodExitsFinder_no_return_type_selected"; //$NON-NLS-1$
			return null;

		}
		fDescription = "MethodExitsFinder_occurrence_exit_description"; //$NON-NLS-1$
		return fDescription;
	}

	private final boolean isExitExecutionPath(ASTNode node) {
		return node != null
				&& (node.getType() == ASTNode.RETURN_STATEMENT || node
						.getType() == ASTNode.THROW_STATEMENT);
	}

	protected void findOccurrences() {
		fDescription = Messages.format(EXIT_POINT_OF, fFunctionDeclaration
				.getFunctionName().getName());
		fFunctionDeclaration.accept(this);
		// TODO : check execution path to determine if the last bracket
		// is also a possible exit path
		// Block block= fMethodDeclaration.getBody();
		// if (block != null) {
		// List statements= block.statements();
		// if (statements.size() > 0) {
		// Statement last= (Statement)statements.get(statements.size() - 1);
		// int maxVariableId= LocalVariableIndex.perform(fMethodDeclaration);
		// FlowContext flowContext= new FlowContext(0, maxVariableId + 1);
		// flowContext.setConsiderAccessMode(false);
		// flowContext.setComputeMode(FlowContext.ARGUMENTS);
		// InOutFlowAnalyzer flowAnalyzer= new InOutFlowAnalyzer(flowContext);
		// FlowInfo info= flowAnalyzer.perform(new ASTNode[] {last});
		// if (!info.isNoReturn() && !isVoid) {
		// if (!info.isPartialReturn())
		// return;
		// }
		// }
		int offset = fFunctionDeclaration.getEnd() - 1;
		fResult.add(new OccurrenceLocation(offset, 1, getOccurrenceType(null),
				fDescription));
		// }
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

	public boolean visit(ReturnStatement node) {
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(),
				getOccurrenceType(null), fDescription));
		return super.visit(node);
	}

	public boolean visit(ThrowStatement node) {
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(),
				getOccurrenceType(null), fDescription));
		return true;
	}

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
		return "MethodExitsFinder_job_label"; //$NON-NLS-1$
	}

	public int getSearchKind() {
		return IOccurrencesFinder.K_EXIT_POINT_OCCURRENCE;
	}

	public String getUnformattedPluralLabel() {
		return "MethodExitsFinder_label_plural"; //$NON-NLS-1$
	}

	public String getUnformattedSingularLabel() {
		return "MethodExitsFinder_label_singular"; //$NON-NLS-1$
	}
}
