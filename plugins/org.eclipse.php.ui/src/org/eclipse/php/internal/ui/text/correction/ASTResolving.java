/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction;

import java.util.Iterator;
import java.util.List;

import org.eclipse.php.core.ast.nodes.*;

public class ASTResolving {

	/**
	 * Finds the ancestor type of <code>node</code> (includes <code>node</code>
	 * in the search).
	 *
	 * @param node
	 *            the node to start the search from, can be <code>null</code>
	 * @param treatModifiersOutside
	 *            if set, modifiers are not part of their type, but of the
	 *            type's parent
	 * @return returns the ancestor type of <code>node</code>
	 *         (AbstractTypeDeclaration or AnonymousTypeDeclaration) if any
	 *         (including <code>node</code>), <code>null</code> otherwise
	 */
	public static ASTNode findParentType(ASTNode node) {
		while (node != null) {
			if (node instanceof TypeDeclaration) {
				return node;
			} else if (node instanceof AnonymousClassDeclaration) {
				return node;
			}
			node = node.getParent();
		}
		return null;
	}

	public static ITypeBinding guessBindingForReference(ASTNode node) {
		return getPossibleReferenceBinding(node);
	}

	private static ITypeBinding getPossibleReferenceBinding(ASTNode node) {
		ASTNode parent = node.getParent();
		switch (parent.getType()) {
		case ASTNode.ASSIGNMENT:
			Assignment assignment = (Assignment) parent;
			if (node.equals(assignment.getLeftHandSide())) {
				return assignment.getRightHandSide().resolveTypeBinding();
			}
			return assignment.getLeftHandSide().resolveTypeBinding();
		case ASTNode.INFIX_EXPRESSION:
			InfixExpression infix = (InfixExpression) parent;
			int op = infix.getOperator();
			if (op == InfixExpression.OP_AND || op == InfixExpression.OP_OR) {
				// boolean operation
				return infix.getAST().resolveWellKnownType("boolean"); //$NON-NLS-1$
			} else if (op == InfixExpression.OP_SL || op == InfixExpression.OP_SR) {
				// asymmetric operation
				return infix.getAST().resolveWellKnownType("int"); //$NON-NLS-1$
			}
			if (node.equals(infix.getLeft())) {
				// xx operation expression
				ITypeBinding rigthHandBinding = infix.getRight().resolveTypeBinding();
				if (rigthHandBinding != null) {
					return rigthHandBinding;
				}
			} else {
				// expression operation xx
				ITypeBinding leftHandBinding = infix.getLeft().resolveTypeBinding();
				if (leftHandBinding != null) {
					return leftHandBinding;
				}
			}
			if (op != InfixExpression.OP_IS_EQUAL && op != InfixExpression.OP_IS_NOT_EQUAL) {
				return infix.getAST().resolveWellKnownType("int"); //$NON-NLS-1$
			}
			break;
		case ASTNode.INSTANCE_OF_EXPRESSION:
			InstanceOfExpression instanceofExpression = (InstanceOfExpression) parent;
			return instanceofExpression.getExpression().resolveTypeBinding();
		case ASTNode.SINGLE_FIELD_DECLARATION:
			SingleFieldDeclaration frag = (SingleFieldDeclaration) parent;
			if (frag.getName().equals(node)) {
				return frag.getName().resolveTypeBinding();
			}
			break;
		case ASTNode.METHOD_INVOCATION:
			MethodInvocation methodInvocation = (MethodInvocation) parent;
			IMethodBinding methodBinding = methodInvocation.resolveMethodBinding();
			if (methodBinding != null) {
				return getParameterTypeBinding(node, methodInvocation.getMethod().parameters(), methodBinding);
			}
			break;
		case ASTNode.CLASS_INSTANCE_CREATION: {
			ClassInstanceCreation creation = (ClassInstanceCreation) parent;
			IMethodBinding creationBinding = creation.resolveConstructorBinding();
			if (creationBinding != null) {
				return getParameterTypeBinding(node, creation.ctorParams(), creationBinding);
			}
			break;
		}
		case ASTNode.PARENTHESIS_EXPRESSION:
			return guessBindingForReference(parent);
		case ASTNode.ARRAY_ACCESS:
			if (((ArrayAccess) parent).getIndex().equals(node)) {
				return parent.getAST().resolveWellKnownType("int"); //$NON-NLS-1$
			} else {
				ITypeBinding parentBinding = getPossibleReferenceBinding(parent);
				if (parentBinding == null) {
					parentBinding = parent.getAST().resolveWellKnownType("java.lang.Object"); //$NON-NLS-1$
				}
				return parentBinding.createArrayType(1);
			}
		case ASTNode.CONDITIONAL_EXPRESSION:
			ConditionalExpression expression = (ConditionalExpression) parent;
			if (node.equals(expression.getCondition())) {
				return parent.getAST().resolveWellKnownType("boolean"); //$NON-NLS-1$
			}
			if (node.equals(expression.getIfFalse())) {
				return expression.getIfTrue().resolveTypeBinding();
			}
			return expression.getIfFalse().resolveTypeBinding();
		case ASTNode.IF_STATEMENT:
		case ASTNode.WHILE_STATEMENT:
		case ASTNode.DO_STATEMENT:
			if (node instanceof Expression) {
				return parent.getAST().resolveWellKnownType("boolean"); //$NON-NLS-1$
			}
			break;
		case ASTNode.SWITCH_STATEMENT:
			if (((SwitchStatement) parent).getExpression().equals(node)) {
				return parent.getAST().resolveWellKnownType("int"); //$NON-NLS-1$
			}
			break;
		case ASTNode.CAST_EXPRESSION:
			return ((CastExpression) parent).resolveTypeBinding();
		case ASTNode.THROW_STATEMENT:
		case ASTNode.CATCH_CLAUSE:
			return parent.getAST().resolveWellKnownType("Exception"); //$NON-NLS-1$
		case ASTNode.FIELD_ACCESS:
			if (node.equals(((FieldAccess) parent).getField())) {
				return getPossibleReferenceBinding(parent);
			}
			break;
		case ASTNode.SWITCH_CASE:
			if (node.equals(((SwitchCase) parent).getValue()) && parent.getParent() instanceof SwitchStatement) {
				return ((SwitchStatement) parent.getParent()).getExpression().resolveTypeBinding();
			}
			break;
		default:
			// do nothing
		}

		return null;
	}

	private static ITypeBinding getParameterTypeBinding(ASTNode node, List<Expression> args, IMethodBinding binding) {
		ITypeBinding[] paramTypes = binding.getParameterTypes();
		int index = args.indexOf(node);
		if (binding.isVarargs() && index >= paramTypes.length - 1) {
			return paramTypes[paramTypes.length - 1].getComponentType();
		}
		if (index >= 0 && index < paramTypes.length) {
			return paramTypes[index];
		}
		return null;
	}

	public static ITypeBinding guessBindingForTypeReference(ASTNode node) {
		StructuralPropertyDescriptor locationInParent = node.getLocationInParent();
		if (locationInParent == Identifier.NAME_PROPERTY) {
			node = node.getParent();
		}
		return getPossibleTypeBinding(node);
	}

	private static ITypeBinding getPossibleTypeBinding(ASTNode node) {
		ASTNode parent = node.getParent();
		switch (parent.getType()) {
		case ASTNode.FIELD_DECLARATION:
			return guessVariableType(((FieldsDeclaration) parent).fields());
		case ASTNode.ARRAY_CREATION:
			ArrayCreation creation = (ArrayCreation) parent;
			return creation.resolveTypeBinding();
		case ASTNode.CLASS_INSTANCE_CREATION:
			return getPossibleReferenceBinding(parent);
		case ASTNode.CAST_EXPRESSION:
			return getPossibleReferenceBinding(parent);
		}
		return null;
	}

	private static ITypeBinding guessVariableType(List<SingleFieldDeclaration> fragments) {
		for (Iterator<SingleFieldDeclaration> iter = fragments.iterator(); iter.hasNext();) {
			SingleFieldDeclaration frag = (SingleFieldDeclaration) iter.next();
			if (frag.getName() != null) {
				return frag.getName().resolveTypeBinding();
			}
		}
		return null;
	}

	/**
	 * Returns the method binding of the node's parent method declaration or
	 * <code>null</code> if the node is not inside a method.
	 * 
	 * @param node
	 *            the ast node
	 * @return the method binding of the node's parent method declaration or
	 *         <code>null</code> if the node
	 */
	public static MethodDeclaration findParentMethodDeclaration(ASTNode node) {
		while (node != null) {
			if (node.getType() == ASTNode.METHOD_DECLARATION) {
				return (MethodDeclaration) node;
			}
			node = node.getParent();
		}
		return null;
	}

	public static int getPossibleTypeKinds(ASTNode node) {
		int kinds = internalGetPossibleTypeKinds(node);
		return kinds;
	}

	private static int internalGetPossibleTypeKinds(ASTNode node) {
		int kind = SimilarElementsRequestor.ALL_TYPES;

		ASTNode parent = node.getParent();

		switch (parent.getType()) {
		case ASTNode.CLASS_DECLARATION:
		case ASTNode.INTERFACE_DECLARATION:
			kind = SimilarElementsRequestor.CLASSES | SimilarElementsRequestor.INTERFACES;
			break;
		case ASTNode.METHOD_DECLARATION:
			kind = SimilarElementsRequestor.ALL_TYPES;
			break;
		case ASTNode.INSTANCE_OF_EXPRESSION:
			kind = SimilarElementsRequestor.REF_TYPES;
			break;
		case ASTNode.THROW_STATEMENT:
			kind = SimilarElementsRequestor.CLASSES;
			break;
		case ASTNode.CLASS_INSTANCE_CREATION:
			kind = SimilarElementsRequestor.CLASSES | SimilarElementsRequestor.INTERFACES;
			break;
		case ASTNode.SINGLE_FIELD_DECLARATION:
			int superParent = parent.getParent().getType();
			if (superParent == ASTNode.CATCH_CLAUSE) {
				kind = SimilarElementsRequestor.CLASSES;
			}
			break;
		default:
		}
		return kind;
	}
}
