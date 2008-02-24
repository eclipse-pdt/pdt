/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.ast.nodes.ThrowStatement;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.ui.corext.ASTNodes;
import org.eclipse.php.internal.ui.corext.dom.NodeFinder;

/**
 * Searches for exit execution path for a given method
 * @author Roy, 2008
 *
 */
public class MethodExitsFinder extends AbstractVisitor implements IOccurrencesFinder {

	public static final String ID= "MethodExitsFinder"; //$NON-NLS-1$
	
	private FunctionDeclaration fFunctionDeclaration;
	private List<OccurrenceLocation> fResult;
	private String fExitDescription;
	private Program fASTRoot;

	public String initialize(Program root, int offset, int length) {
		return initialize(root, NodeFinder.perform(root, offset, length));
	}
	
	/**
	 * @param root the AST root
	 * @param node the selected node
	 * @return returns a message if there is a problem
	 */
	public String initialize(Program root, ASTNode node) {
		fASTRoot= root;
		
		if (isExitExecutionPath(node)) {
			fFunctionDeclaration= (FunctionDeclaration)ASTNodes.getParent(node, ASTNode.FUNCTION_DECLARATION);
			if (fFunctionDeclaration == null)
				return "MethodExitsFinder_no_return_type_selected";
			return null;
			
		}
		
		fExitDescription= "MethodExitsFinder_occurrence_exit_description";
		return fExitDescription;
	}

	private final boolean isExitExecutionPath(ASTNode node) {
		return node != null && (node.getType() == ASTNode.RETURN_STATEMENT || node.getType() == ASTNode.THROW_STATEMENT) ; 
	}

	private void performSearch() {
		fResult= new ArrayList<OccurrenceLocation>();
		markReferences();
	}

	public OccurrenceLocation[] getOccurrences() {
		performSearch();
		if (fResult.isEmpty())
			return null;

		return fResult.toArray(new OccurrenceLocation[fResult.size()]);
	}	
	
	
	private void markReferences() {
		fExitDescription= Messages.format("Exit point of ''{0}()", fFunctionDeclaration.getFunctionName().getName());
		
		fFunctionDeclaration.accept(this);

//      TODO : check execution path to determine if the last bracket 
// 			   is also a possible exit path
//		Block block= fMethodDeclaration.getBody();
//		if (block != null) {
//			List statements= block.statements();
//			if (statements.size() > 0) {
//				Statement last= (Statement)statements.get(statements.size() - 1);
//				int maxVariableId= LocalVariableIndex.perform(fMethodDeclaration);
//				FlowContext flowContext= new FlowContext(0, maxVariableId + 1);
//				flowContext.setConsiderAccessMode(false);
//				flowContext.setComputeMode(FlowContext.ARGUMENTS);
//				InOutFlowAnalyzer flowAnalyzer= new InOutFlowAnalyzer(flowContext);
//				FlowInfo info= flowAnalyzer.perform(new ASTNode[] {last});
//				if (!info.isNoReturn() && !isVoid) {
//					if (!info.isPartialReturn())
//						return;
//				}
//			}
		int offset= fFunctionDeclaration.getStart() + fFunctionDeclaration.getLength() - 1; // closing bracket
		fResult.add(new OccurrenceLocation(offset, 1, K_EXIT_POINT_OCCURRENCE, fExitDescription));
//		}
	}

	public boolean visit(ReturnStatement node) {
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), K_EXIT_POINT_OCCURRENCE, fExitDescription));
		return super.visit(node);
	}
			
	public boolean visit(ThrowStatement node) {
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), K_EXIT_POINT_OCCURRENCE, fExitDescription));
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
		return "MethodExitsFinder_job_label";
	}

	public int getSearchKind() {
		return IOccurrencesFinder.K_BREAK_TARGET_OCCURRENCE;
	}

	public String getUnformattedPluralLabel() {
		return "MethodExitsFinder_label_plural";
	}

	public String getUnformattedSingularLabel() {
		return "MethodExitsFinder_label_singular";
	}

}
