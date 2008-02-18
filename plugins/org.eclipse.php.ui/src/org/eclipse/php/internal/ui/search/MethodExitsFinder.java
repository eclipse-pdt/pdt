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

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Block;
import org.eclipse.php.internal.core.ast.nodes.CatchClause;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.internal.core.ast.nodes.FunctionName;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.ast.nodes.Statement;
import org.eclipse.php.internal.core.ast.nodes.ThrowStatement;
import org.eclipse.php.internal.core.ast.nodes.TryStatement;
import org.eclipse.php.internal.core.ast.nodes.TypeDeclaration;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.ui.corext.ASTNodes;
import org.eclipse.php.internal.ui.corext.dom.NodeFinder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MethodExitsFinder extends AbstractVisitor implements IOccurrencesFinder {

	public static final String ID= "MethodExitsFinder"; //$NON-NLS-1$
	
	private FunctionDeclaration fMethodDeclaration;
	private List fResult;
	private List fCatchedExceptions;
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
		
		if (node instanceof ReturnStatement) {
			fMethodDeclaration= (FunctionDeclaration)ASTNodes.getParent(node, ASTNode.FUNCTION_DECLARATION);
			if (fMethodDeclaration == null)
				return "MethodExitsFinder_no_return_type_selected";
			return null;
			
		}
		
		fExitDescription= "MethodExitsFinder_occurrence_exit_description";
		return fExitDescription;
	}

	private void performSearch() {
		fResult= new ArrayList();
		markReferences();
	}

	public OccurrenceLocation[] getOccurrences() {
		performSearch();
		if (fResult.isEmpty())
			return null;

		return (OccurrenceLocation[]) fResult.toArray(new OccurrenceLocation[fResult.size()]);
	}	
	
	
	private void markReferences() {
		fCatchedExceptions= new ArrayList();
		boolean isVoid= true;
		fMethodDeclaration.accept(this);
		Block block= fMethodDeclaration.getBody();
		if (block != null) {
			List statements= block.statements();
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
			int offset= fMethodDeclaration.getStart() + fMethodDeclaration.getLength() - 1; // closing bracket
			fResult.add(new OccurrenceLocation(offset, 1, 0, fExitDescription));
		}
	}

	public boolean visit(TypeDeclaration node) {
		// Don't dive into a local type.
		return false;
	}

	public boolean visit(ReturnStatement node) {
		fResult.add(new OccurrenceLocation(node.getStart(), node.getLength(), 0, fExitDescription));
		return super.visit(node);
	}
	
	public boolean visit(TryStatement node) {
		int currentSize= fCatchedExceptions.size();
		List catchClauses= node.catchClauses();
		for (Iterator iter= catchClauses.iterator(); iter.hasNext();) {
//			IVariableBinding variable= ((CatchClause)iter.next()).getException().resolveBinding();
//			if (variable != null && variable.getType() != null) {
//				fCatchedExceptions.add(variable.getType());
//			}
		}
		node.getBody().accept(this);
		int toRemove= fCatchedExceptions.size() - currentSize;
		for(int i= toRemove; i > 0; i--) {
			fCatchedExceptions.remove(currentSize);
		}
		
		// visit catch and finally
		for (Iterator iter= catchClauses.iterator(); iter.hasNext(); ) {
			((CatchClause)iter.next()).accept(this);
		}
			
		// return false. We have visited the body by ourselves.	
		return false;
	}
	
	public boolean visit(ThrowStatement node) {
		fResult.add(new OccurrenceLocation(node.getStart(), 5, 0, fExitDescription));
		return true;
	}
	
	public boolean visit(FunctionInvocation node) {
		FunctionName name = node.getFunctionName();
		fResult.add(new OccurrenceLocation(name.getStart(), name.getLength(), 0, fExitDescription));
		return true;
	}
	
	public Program getASTRoot() {
		return fASTRoot;
	}

	public String getElementName() {
		return fMethodDeclaration.getFunctionName().getName();
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
