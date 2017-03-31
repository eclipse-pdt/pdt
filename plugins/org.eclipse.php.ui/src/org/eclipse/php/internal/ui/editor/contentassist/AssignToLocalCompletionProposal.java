/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.HierarchicalVisitor;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewrite;
import org.eclipse.php.internal.ui.corext.fix.LinkedProposalModel;
import org.eclipse.php.internal.ui.text.correction.proposals.ASTRewriteCorrectionProposal;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.text.edits.TextEditGroup;

/**
 * Create local variable for function/method call
 */
public class AssignToLocalCompletionProposal extends ASTRewriteCorrectionProposal {
	public static final String COMMAND_ID = "org.eclipse.php.internal.ui.assignToLocal.assist"; //$NON-NLS-1$
	public static final String KEY_NAME = "name"; //$NON-NLS-1$
	public static final String DEFAULT_NAME = "localVar"; //$NON-NLS-1$

	protected ASTNode context;

	public AssignToLocalCompletionProposal(ISourceModule cu, ASTNode context) {
		super(Messages.AssignToLocalCompletionProposal_name, cu, null, 0,
				PHPPluginImages.DESC_FIELD_DEFAULT.createImage()); // $NON-NLS-N$
		this.context = context;
		setCommandId(COMMAND_ID);
	}

	public static ExpressionStatement getStatement(ASTNode node) {
		if (node == null) {
			return null;
		} else if (node instanceof ExpressionStatement) {
			return (ExpressionStatement) node;
		}

		return node.getParent() == null ? null : getStatement(node.getParent());
	}

	public static Expression getMainExpression(ASTNode node) {
		ExpressionStatement statement = getStatement(node);

		return statement == null || statement.getExpression() == null ? null : statement.getExpression();
	}

	public static boolean isEnd(ASTNode coveringNode) {
		return coveringNode instanceof ExpressionStatement;
	}

	public static boolean isFunctionCall(ASTNode coveringNode) {
		if (coveringNode instanceof FunctionInvocation) {
			return true;
		}

		return isEnd(coveringNode) || coveringNode.getParent() == null ? false
				: isFunctionCall(coveringNode.getParent());
	}

	public static boolean isAssigned(ASTNode coveringNode) {
		if (coveringNode instanceof Assignment) {
			return true;
		}

		return isEnd(coveringNode) || coveringNode.getParent() == null ? false : isAssigned(coveringNode.getParent());
	}

	@Override
	protected ASTRewrite getRewrite() throws CoreException {
		LinkedProposalModel linkedModel = getLinkedProposalModel();
		TextEditGroup editGroup = new TextEditGroup(COMMAND_ID);

		ExpressionStatement statement = getStatement(context);
		Expression expression = statement.getExpression();

		AST ast = statement.getAST();
		ASTRewrite astRewrite = ASTRewrite.create(ast);
		String[] names = possibleNames(expression);
		for (int i = 0; i < names.length; i++) {
			linkedModel.getPositionGroup(KEY_NAME, true).addProposal(names[0], null, 10);
		}
		Variable variable = ast.newVariable(names[0]);

		Assignment assign = ast.newAssignment(variable, Assignment.OP_EQUAL,
				(Expression) astRewrite.createCopyTarget(expression));
		astRewrite.replace(expression, assign, editGroup);

		linkedModel.getPositionGroup(KEY_NAME, true).addPosition(astRewrite.track(variable.getName()), true);
		linkedModel.setEndPosition(astRewrite.track(statement));

		return astRewrite;
	}

	protected String[] possibleNames(ASTNode node) {
		String basic = getBasicName(node);
		if (basic == null || basic.length() < 1) {
			basic = DEFAULT_NAME;
		}
		if (basic.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			basic = basic.substring(1);
		}
		basic = Character.toLowerCase(basic.charAt(0)) + basic.substring(1);

		ASTNode scope = searchScope(node);
		FindVariableVisitor find;
		int num = -1;
		do {
			num++;
			find = new FindVariableVisitor(num == 0 ? basic : basic + num);
			scope.childrenAccept(find);
		} while (find.found);

		return new String[] { find.search };
	}

	private ASTNode searchScope(ASTNode node) {
		if (node.getParent() == null) {
			return node.getRoot();
		}
		if (node.getParent() instanceof FunctionDeclaration) {
			return node.getParent();
		}

		return searchScope(node.getParent());
	}

	private String getBasicName(ASTNode node) {
		if (node != null) {
			switch (node.getType()) {
			case ASTNode.METHOD_INVOCATION:
				MethodInvocation inv = (MethodInvocation) node;
				return getBasicName(inv.getMethod());
			case ASTNode.FUNCTION_INVOCATION:
				FunctionInvocation func = (FunctionInvocation) node;
				if (func.getFunctionName() == null || func.getFunctionName().getName() == null) {
					return DEFAULT_NAME;
				}

				return getBasicName(func.getFunctionName().getName());
			case ASTNode.STATIC_METHOD_INVOCATION:
				StaticMethodInvocation st = (StaticMethodInvocation) node;
				return getBasicName(st.getMethod());
			case ASTNode.CLASS_INSTANCE_CREATION:
				ClassInstanceCreation ci = (ClassInstanceCreation) node;
				if (ci.getClassName() != null) {
					return getBasicName(ci.getClassName().getName());
				}
			case ASTNode.VARIABLE:
				return getBasicName(((Variable) node).getName());
			case ASTNode.ARRAY_ACCESS:
				return getBasicName(((ArrayAccess) node).getName());
			case ASTNode.IDENTIFIER:
			case ASTNode.NAMESPACE_NAME:
				return ((Identifier) node).getName();
			}
		}

		return DEFAULT_NAME;
	}

	private class FindVariableVisitor extends HierarchicalVisitor {
		public boolean found = false;
		public String search;

		public FindVariableVisitor(String search) {
			this.search = search;
		}

		@Override
		public boolean visit(ASTNode node) {
			return !found && super.visit(node);
		}

		@Override
		public boolean visit(FunctionDeclaration node) {
			return false;
		}

		@Override
		public boolean visit(MethodDeclaration method) {
			return false;
		}

		@Override
		public boolean visit(Variable var) {
			if ((var.isDollared() || org.eclipse.php.internal.core.corext.ASTNodes.isQuotedDollaredCurlied(var))
					&& var.getName() instanceof Identifier) {
				String name = ((Identifier) var.getName()).getName();
				if (name != null && name.equals(search)) {
					found = true;
					return false;
				}
			}

			return super.visit(var);
		}

	}
}
