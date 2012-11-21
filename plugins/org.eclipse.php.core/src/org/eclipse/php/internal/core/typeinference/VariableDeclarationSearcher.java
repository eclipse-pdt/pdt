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
package org.eclipse.php.internal.core.typeinference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.typeinference.context.ContextFinder;

/**
 * This visitor builds local variable declarations tree for specified file. The
 * resulting tree contains variable declarations list (one per
 * {@link DeclarationScope}), where the list entries are possible variable
 * declarations. Examples:
 * <p>
 * 1. Here are two possible declarations for "$a":<br/>
 * 
 * <pre>
 * $a = &quot;some&quot;;
 * if (someCondition()) {
 * 	$a = &quot;other&quot;;
 * }
 * list($a, list($b)) = array(...)
 * </pre>
 * 
 * <br/>
 * </p>
 * 
 * @author michael
 */
public class VariableDeclarationSearcher extends ContextFinder {

	/**
	 * Scope variable declarations map
	 */
	private Map<IContext, DeclarationScope> scopes = new HashMap<IContext, DeclarationScope>();

	/**
	 * Stack of processed AST nodes
	 */
	protected Stack<ASTNode> nodesStack = new Stack<ASTNode>();

	public VariableDeclarationSearcher(ISourceModule sourceModule) {
		super(sourceModule);
	}

	/**
	 * Override to invoke additional processing on this kind of node
	 * 
	 * @param node
	 */
	protected void postProcess(ModuleDeclaration node) {
	}

	public final boolean visit(ModuleDeclaration node) throws Exception {
		if (!isInteresting(node)) {
			visitGeneral(node);
			return false;
		}

		postProcess(node);

		return super.visit(node);
	}

	public final boolean endvisit(ModuleDeclaration node) throws Exception {
		return super.endvisit(node);
	}

	/**
	 * Override to invoke additional processing on this kind of node
	 * 
	 * @param node
	 */
	protected void postProcess(TypeDeclaration node) {
	}

	public final boolean visit(TypeDeclaration node) throws Exception {
		if (!isInteresting(node)) {
			visitGeneral(node);
			return false;
		}

		postProcess(node);

		return super.visit(node);
	}

	public final boolean endvisit(TypeDeclaration node) throws Exception {
		return super.endvisit(node);
	}

	/**
	 * Override to invoke additional processing on this kind of node
	 * 
	 * @param node
	 */
	protected void postProcess(MethodDeclaration node) {
	}

	public final boolean visit(MethodDeclaration node) throws Exception {
		if (!isInteresting(node)) {
			visitGeneral(node);
			return false;
		}

		postProcess(node);

		return super.visit(node);
	}

	public final boolean endvisit(MethodDeclaration node) throws Exception {
		return super.endvisit(node);
	}

	/**
	 * Override to invoke additional processing on this kind of node
	 * 
	 * @param node
	 */
	protected void postProcess(Expression node) {
	}

	public final boolean visit(Expression node) throws Exception {
		if (!isInteresting(node)) {
			visitGeneral(node);
			return false;
		}
		ASTNode parent = nodesStack.peek();
		if (isConditional(parent)) {
			getScope().enterInnerBlock((Statement) parent);
		}

		if (node instanceof Assignment) {
			Expression variable = ((Assignment) node).getVariable();
			addDeclaredVariables(variable, node);
		}

		postProcess(node);

		return super.visit(node);
	}

	private void addDeclaredVariables(Expression variable, Expression node) {
		if (variable instanceof VariableReference) {
			VariableReference varReference = (VariableReference) variable;
			getScope().addDeclaration(varReference.getName(), node);
		} else if (variable instanceof ListVariable) {
			ListVariable varReference = (ListVariable) variable;
			for (Expression nestedVar : varReference.getVariables()) {
				addDeclaredVariables(nestedVar, node);
			}
		} else if (variable instanceof ReflectionArrayVariableReference) {
			Expression expression = ((ReflectionArrayVariableReference) variable)
					.getExpression();
			while (expression instanceof ReflectionArrayVariableReference) {
				expression = ((ReflectionArrayVariableReference) expression)
						.getExpression();
			}
			if (expression instanceof ArrayVariableReference) {
				ArrayVariableReference varRef = (ArrayVariableReference) expression;
				getScope().addDeclaration(varRef.getName(), node);
			}
		}
	}

	public boolean endvisit(Expression node) throws Exception {
		return super.endvisit(node);
	}

	/**
	 * Override to invoke additional processing on this kind of node
	 * 
	 * @param node
	 */
	protected void postProcess(Statement node) {
	}

	public final boolean visit(Statement node) throws Exception {
		if (!isInteresting(node)) {
			visitGeneral(node);
			return false;
		}

		ASTNode parent = nodesStack.peek();
		if (isConditional(parent)) {
			getScope().enterInnerBlock((Statement) parent);
		}

		if (node instanceof GlobalStatement) {
			GlobalStatement globalStatement = (GlobalStatement) node;
			for (Expression variable : globalStatement.getVariables()) {
				if (variable instanceof VariableReference) {
					VariableReference varReference = (VariableReference) variable;
					getScope().addDeclaration(varReference.getName(),
							globalStatement);
				}
			}
		} else if (node instanceof FormalParameter) {
			FormalParameter parameter = (FormalParameter) node;
			getScope().addDeclaration(parameter.getName(), parameter);
		} else if (node instanceof CatchClause) {
			CatchClause clause = (CatchClause) node;
			VariableReference varReference = clause.getVariable();
			getScope().addDeclaration(varReference.getName(), clause);
		} else if (node instanceof ForEachStatement) {
			ForEachStatement foreachStatement = (ForEachStatement) node;

			Expression value = foreachStatement.getValue();
			if (value instanceof ReferenceExpression) { // foreach ( $array as
				// &$value )
				value = ((ReferenceExpression) value).getVariable();
			}

			if (value instanceof SimpleReference) {
				String variableName = ((SimpleReference) value).getName();
				getScope().addDeclaration(variableName, foreachStatement);
			}

			final Expression key = foreachStatement.getKey();
			if (key instanceof SimpleReference) {
				String variableName = ((SimpleReference) key).getName();
				getScope().addDeclaration(variableName, foreachStatement);
			}
		} else if (node instanceof StaticStatement) {
			StaticStatement staticStatement = (StaticStatement) node;
			// Collection<? extends Expression> expressions =
			// staticStatement.getExpressions();
			for (Expression variable : staticStatement.getExpressions()) {
				if (variable instanceof VariableReference) {
					VariableReference varReference = (VariableReference) variable;
					getScope().addDeclaration(varReference.getName(),
							staticStatement);
				}
			}
			// VariableReference varReference =
			// staticStatement.getExpressions();
			// getScope().addDeclaration(varReference.getName(), clause);
		}

		postProcess(node);

		return super.visit(node);
	}

	public final boolean endvisit(Statement node) throws Exception {
		if (isConditional(node)) {
			getScope().exitInnerBlock();
		}
		return super.endvisit(node);
	}

	/**
	 * Override to invoke additional processing on this kind of node
	 * 
	 * @param node
	 */
	protected void postProcessGeneral(ASTNode node) {
	}

	public final boolean visitGeneral(ASTNode node) throws Exception {
		nodesStack.push(node);
		postProcessGeneral(node);
		return super.visitGeneral(node);
	}

	public final void endvisitGeneral(ASTNode node) throws Exception {
		nodesStack.pop();
		super.endvisitGeneral(node);
	}

	/**
	 * Returns whether the sub-tree of the given node should be processed. By
	 * default it well process all nodes.
	 * 
	 * @param node
	 * @return
	 */
	protected boolean isInteresting(ASTNode node) {
		return true;
	}

	/**
	 * Checks whether the given AST node makes possible conditional branch in
	 * variables declaration flow.
	 * 
	 * @param node
	 * @return
	 */
	protected boolean isConditional(ASTNode node) {
		if (node instanceof Statement) {
			int kind = ((Statement) node).getKind();
			return kind == ASTNodeKinds.CATCH_CLAUSE
					|| kind == ASTNodeKinds.IF_STATEMENT
					|| kind == ASTNodeKinds.FOR_STATEMENT
					|| kind == ASTNodeKinds.FOR_EACH_STATEMENT
					|| kind == ASTNodeKinds.SWITCH_CASE
					|| kind == ASTNodeKinds.WHILE_STATEMENT;
		}
		return false;
	}

	/**
	 * Returns declaration scope for current context
	 * 
	 * @return
	 */
	protected DeclarationScope getScope() {
		return getScope(contextStack.peek());
	}

	/**
	 * Returns declaration scope for given context
	 * 
	 * @param context
	 * @return
	 */
	protected DeclarationScope getScope(IContext context) {
		if (!scopes.containsKey(context)) {
			scopes.put(context, new DeclarationScope(context));
		}
		return scopes.get(context);
	}

	/**
	 * Returns all declaration scopes
	 * 
	 * @return
	 */
	public DeclarationScope[] getScopes() {
		Collection<DeclarationScope> values = scopes.values();
		return (DeclarationScope[]) values.toArray(new DeclarationScope[values
				.size()]);
	}

	/**
	 * Returns all declarations for the specified variable in the given context
	 */
	public Declaration[] getDeclarations(String varName, IContext context) {
		return getScope(context).getDeclarations(varName);
	}
}
