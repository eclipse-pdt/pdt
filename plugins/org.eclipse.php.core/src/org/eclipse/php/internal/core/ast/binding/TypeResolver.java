/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.binding;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;

/**
 * THis visitor get an expression and resolve the expression type
 */
public class TypeResolver extends AbstractVisitor {

	/**
	 * Resolve an expression given a scope
	 * @param scope
	 * @param expression
	 * @return the resolved type 
	 */
	public static final Attribute resolve(ScopeBase scope, Expression expression) {
		final TypeResolver typeResolver = new TypeResolver(scope);
		expression.accept(typeResolver);
		return typeResolver.evaluatedAttribute;
	}

	// result attribute is calculated here 
	public Attribute evaluatedAttribute;

	// scope used during the type resolving
	private final ScopeBase scope;

	/**
	 * should not be constructed by users.
	 * use {@link #resolve(ScopeBase, Expression)} instead.
	 */
	protected TypeResolver(ScopeBase scope) {
		this.scope = scope;
	}

	/**
	 * in the case of prefix / postfix operation we have the same logic
	 */
	private void resolveFixOperations() {
		if (evaluatedAttribute == Attribute.NULL_ATTRIBUTE) {
			evaluatedAttribute = Attribute.INT_ATTRIBUTE;
		} else if (evaluatedAttribute == Attribute.STRING_ATTRIBUTE) {
			final CompositeAttribute compositeAttribute = new CompositeAttribute();
			compositeAttribute.addAttribute(Attribute.STRING_ATTRIBUTE);
			compositeAttribute.addAttribute(Attribute.INT_ATTRIBUTE);
			evaluatedAttribute = compositeAttribute;
		}
		// in the other case we hold the same attribute
	}

	private void throwException(ASTNode node) {
		throw new UnsupportedOperationException("cannot resolve: " + node.getStart() + ", type: " + node.getType()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public boolean visit(ArrayAccess indexedVariable) {
		scope.lookup(indexedVariable);
		return false;
	}

	/**
	 * Returns an array attribute
	 */
	public boolean visit(ArrayCreation arrayExpressionon) {
		final ArrayAttribute attribute = new ArrayAttribute();
		ArrayElement[] elements = arrayExpressionon.getElements();
		for (int i = 0; i < elements.length; i++) {
			ArrayElement element = elements[i];
			element.getValue().accept(this);
			attribute.addAttribute(evaluatedAttribute);
		}
		evaluatedAttribute = attribute;
		return false;
	}

	public boolean visit(ArrayElement arrayElement) {
		throwException(arrayElement);
		return false;
	}

	/**
	 * Gets the value attribute
	 */
	public boolean visit(Assignment assignment) {
		assignment.getValue().accept(this);
		return false;
	}

	public boolean visit(ASTError astError) {
		evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
		return false;
	}

	public boolean visit(BackTickExpression expression) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean visit(Block blockStatement) {
		throwException(blockStatement);
		return false;
	}

	public boolean visit(BreakStatement breakStatement) {
		throwException(breakStatement);
		return false;
	}

	public boolean visit(CastExpression castExpression) {
		castExpression.getExpr().accept(this);
		return false;
	}

	public boolean visit(CatchClause catchStatement) {
		throwException(catchStatement);
		return false;
	}

	public boolean visit(StaticConstantAccess classConstant) {
		evaluatedAttribute = scope.programScope.lookup(classConstant);
		return false;
	}

	public boolean visit(ClassConstantDeclaration classConstantDeclaratio) {
		throwException(classConstantDeclaratio);
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		throwException(classDeclaration);
		return false;
	}

	/**
	 * Returns the class name declaration
	 */
	public boolean visit(ClassInstanceCreation classInstanceCreation) {
		classInstanceCreation.getClassName().accept(this);
		return false;
	}

	public boolean visit(ClassName className) {
		if (className.getClassName().getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) className.getClassName();
			evaluatedAttribute = new ClassAttribute(id.getName());
		} else {
			evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
		}
		return false;
	}

	public boolean visit(CloneExpression cloneExpression) {
		cloneExpression.getExpr().accept(this);
		return false;
	}

	public boolean visit(Comment comment) {
		throwException(comment);
		return false;
	}

	public boolean visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.getIfFalse().accept(this);
		Attribute falseExpression = evaluatedAttribute;
		conditionalExpression.getIfTrue().accept(this);
		Attribute trueExpression = evaluatedAttribute;
		if (!falseExpression.equals(trueExpression)) {
			final CompositeAttribute compositeAttribute = new CompositeAttribute();
			compositeAttribute.addAttribute(falseExpression);
			compositeAttribute.addAttribute(trueExpression);
			evaluatedAttribute = compositeAttribute;
		} // else - it is the same attribute, so return the attribute itself
		return false;
	}

	public boolean visit(ContinueStatement continueStatement) {
		throwException(continueStatement);
		return false;
	}

	public boolean visit(DeclareStatement declareStatement) {
		throwException(declareStatement);
		return false;
	}

	public boolean visit(Dispatch dispatch) {
		evaluatedAttribute = scope.lookup(dispatch);
		return false;
	}

	public boolean visit(DoStatement doStatement) {
		throwException(doStatement);
		return false;
	}

	public boolean visit(EchoStatement echoStatement) {
		throwException(echoStatement);
		return false;
	}

	public boolean visit(EmptyStatement emptyStatement) {
		throwException(emptyStatement);
		return false;
	}

	public boolean visit(ExpressionStatement expressionStatement) {
		throwException(expressionStatement);
		return false;
	}

	public boolean visit(FieldsDeclaration classVariableDeclaratio) {
		throwException(classVariableDeclaratio);
		return false;
	}

	public boolean visit(ForEachStatement forEachStatement) {
		throwException(forEachStatement);
		return false;
	}

	public boolean visit(FormalParameter formalParameter) {
		throwException(formalParameter);
		return false;
	}

	public boolean visit(ForStatement forStatement) {
		throwException(forStatement);
		return false;
	}

	public boolean visit(FunctionDeclaration functionDeclaration) {
		throwException(functionDeclaration);
		return false;
	}

	public boolean visit(FunctionInvocation functionInvocation) {
		scope.lookup(functionInvocation);
		return false;
	}

	public boolean visit(FunctionName functionName) {
		throwException(functionName);
		return false;
	}

	public boolean visit(GlobalStatement globalStatement) {
		throwException(globalStatement);
		return false;
	}

	public boolean visit(Identifier identifier) {
		scope.lookup(identifier.getName());
		return false;
	}

	public boolean visit(IfStatement ifStatement) {
		throwException(ifStatement);
		return false;
	}

	public boolean visit(IgnoreError ignoreError) {
		// returns the expression attribute
		ignoreError.getExpr().accept(this);
		return false;
	}

	public boolean visit(Include include) {
		evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
		return false;
	}

	public boolean visit(InfixExpression infixExpression) {
		Attribute leftAttr = null;
		Attribute rightAttr = null;
		switch (infixExpression.getOperator()) {
			case InfixExpression.OP_IS_IDENTICAL: // "===";
			case InfixExpression.OP_IS_NOT_IDENTICAL: // "!==";
			case InfixExpression.OP_IS_EQUAL: // "==";
			case InfixExpression.OP_IS_NOT_EQUAL: // "!=";
			case InfixExpression.OP_RGREATER: // "<";
			case InfixExpression.OP_IS_SMALLER_OR_EQUAL: // "<=";
			case InfixExpression.OP_LGREATER: // ">";
			case InfixExpression.OP_IS_GREATER_OR_EQUAL: // ">=";
			case InfixExpression.OP_BOOL_OR: // "||";
			case InfixExpression.OP_BOOL_AND: // "&&";
				evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
				break;
			case InfixExpression.OP_STRING_OR: // "or";
			case InfixExpression.OP_STRING_AND: // "and";
			case InfixExpression.OP_STRING_XOR: // "xor"
				// the operation gets the left hand side attribute
				infixExpression.getLeft().accept(this);
				break;
			case InfixExpression.OP_OR: // "|"
			case InfixExpression.OP_AND: // "&";
			case InfixExpression.OP_XOR: // "^";
				// the operation gets int attribute, but if string OP string gets string
				infixExpression.getLeft().accept(this);
				leftAttr = evaluatedAttribute;
				if (leftAttr != Attribute.STRING_ATTRIBUTE) {
					evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				} else {
					infixExpression.getRight().accept(this);
					rightAttr = evaluatedAttribute;
					if (rightAttr == Attribute.STRING_ATTRIBUTE) {
						evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
					} else {
						evaluatedAttribute = Attribute.INT_ATTRIBUTE;
					}
				}
				break;
			case InfixExpression.OP_CONCAT: // ".";
				evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
				break;
			case InfixExpression.OP_PLUS: // "+";
			case InfixExpression.OP_MINUS: // "-";
				infixExpression.getLeft().accept(this);
				leftAttr = evaluatedAttribute;
				infixExpression.getRight().accept(this);
				rightAttr = evaluatedAttribute;
				if (leftAttr.getType() == AttributeType.STRING_ATTRIBUTE || rightAttr.getType() == AttributeType.STRING_ATTRIBUTE) {
					final CompositeAttribute attr = new CompositeAttribute();
					attr.addAttribute(Attribute.INT_ATTRIBUTE);
					attr.addAttribute(Attribute.REAL_ATTRIBUTE);
					evaluatedAttribute = attr;
				}
				if (leftAttr == Attribute.REAL_ATTRIBUTE || rightAttr == Attribute.REAL_ATTRIBUTE) {
					evaluatedAttribute = Attribute.REAL_ATTRIBUTE;
				} else {
					evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				}
				break;

			case InfixExpression.OP_MUL: // "*";
			case InfixExpression.OP_DIV: // "/";
				infixExpression.getLeft().accept(this);
				leftAttr = evaluatedAttribute;
				infixExpression.getRight().accept(this);
				rightAttr = evaluatedAttribute;
				if (leftAttr.getType() == AttributeType.ARRAY_ATTRIBUTE || leftAttr.getType() == AttributeType.CLASS_ATTRIBUTE || rightAttr.getType() == AttributeType.STRING_ATTRIBUTE || rightAttr.getType() == AttributeType.CLASS_ATTRIBUTE) {
					// warning here 
					evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
				}
				final CompositeAttribute attr = new CompositeAttribute();
				attr.addAttribute(Attribute.INT_ATTRIBUTE);
				attr.addAttribute(Attribute.REAL_ATTRIBUTE);
				evaluatedAttribute = attr;
				break;
			case InfixExpression.OP_MOD: // "%";
			case InfixExpression.OP_SL:
			case InfixExpression.OP_SR:
				evaluatedAttribute = Attribute.INT_ATTRIBUTE;
			default:
				throw new IllegalArgumentException();
		}
		return false;
	}

	public boolean visit(InLineHtml inLineHtml) {
		throwException(inLineHtml);
		return false;
	}

	public boolean visit(InstanceOfExpression instanceOfExpression) {
		evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
		return false;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		throwException(interfaceDeclaration);
		return false;
	}

	public boolean visit(ListVariable listVariable) {
		scope.lookup(listVariable);
		return false;
	}

	public boolean visit(MethodDeclaration classMethodDeclaration) {
		throwException(classMethodDeclaration);
		return false;
	}

	public boolean visit(PostfixExpression postfixExpressions) {
		postfixExpressions.getVariable().accept(this);
		resolveFixOperations();
		return false;
	}

	public boolean visit(PrefixExpression prefixExpression) {
		prefixExpression.getVariable().accept(this);
		resolveFixOperations();
		return false;
	}

	public boolean visit(Program program) {
		throwException(program);
		return false;
	}

	public boolean visit(Quote quote) {
		evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
		return false;
	}

	public boolean visit(Reference reference) {
		reference.getExpression().accept(this);
		return false;
	}

	public boolean visit(ReflectionVariable reflectionVariable) {
		scope.lookup(reflectionVariable);
		return false;
	}

	public boolean visit(ReturnStatement returnStatement) {
		throwException(returnStatement);
		return false;
	}

	public boolean visit(Scalar scalar) {
		switch (scalar.getScalarType()) {
			case Scalar.TYPE_INT:
				this.evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				break;
			case Scalar.TYPE_REAL:
				this.evaluatedAttribute = Attribute.REAL_ATTRIBUTE;
				break;
			case Scalar.TYPE_STRING:
				if ("true".equals(scalar.getStringValue()) || "false".equals(scalar.getStringValue())) { //$NON-NLS-1$ //$NON-NLS-2$
					this.evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
				} else {
					this.evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
				}
				break;
			case Scalar.TYPE_SYSTEM:
				if ("__LINE__".endsWith(scalar.getStringValue())) { //$NON-NLS-1$
					this.evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				} else {
					this.evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
				}
				break;
			default:
				assert false;
		}
		return false;
	}

	public boolean visit(StaticFieldAccess staticMember) {
		scope.lookup(staticMember);
		return false;
	}

	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean visit(StaticStatement staticStatement) {
		throwException(staticStatement);
		return false;
	}

	public boolean visit(SwitchCase caseStatement) {
		throwException(caseStatement);
		return false;
	}

	public boolean visit(SwitchStatement switchStatement) {
		throwException(switchStatement);
		return false;}

	public boolean visit(ThrowStatement throwStatement) {
		throwException(throwStatement);
		return false;	}

	public boolean visit(TryStatement tryStatement) {
		throwException(tryStatement);
		return false;}

	public boolean visit(UnaryOperation unaryOperation) {
		unaryOperation.getExpr().accept(this);
		switch (unaryOperation.getOperator()) {
			case UnaryOperation.OP_MINUS:
			case UnaryOperation.OP_PLUS:
				if (evaluatedAttribute != Attribute.REAL_ATTRIBUTE) {
					evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				} // if it is real it stays real, else do it int			
				break;
			case UnaryOperation.OP_NOT:
				evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
				break;
			case UnaryOperation.OP_TILDA:
				if (evaluatedAttribute != Attribute.REAL_ATTRIBUTE && evaluatedAttribute != Attribute.INT_ATTRIBUTE && evaluatedAttribute != Attribute.STRING_ATTRIBUTE) {
					// semantic error found here
					evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
				}
				break;
			default:
				throwException(unaryOperation);
		}
		return false;
	}

	public boolean visit(Variable variable) {
		scope.lookup(variable);
		return false;
	}

	public boolean visit(WhileStatement whileStatement) {
		throwException(whileStatement);
		return false;
	}
}
