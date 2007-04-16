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
		throw new UnsupportedOperationException("cannot resolve: " + node.getStart() + ", type: " + node.getType());
	}

	public void visit(ArrayAccess indexedVariable) {
		scope.lookup(indexedVariable);
	}

	/**
	 * Returns an array attribute
	 */
	public void visit(ArrayCreation arrayExpressionon) {
		final ArrayAttribute attribute = new ArrayAttribute();
		ArrayElement[] elements = arrayExpressionon.getElements();
		for (int i = 0; i < elements.length; i++) {
			ArrayElement element = elements[i];
			element.getValue().accept(this);
			attribute.addAttribute(evaluatedAttribute);
		}
		evaluatedAttribute = attribute;
	}

	public void visit(ArrayElement arrayElement) {
		throwException(arrayElement);
	}

	/**
	 * Gets the value attribute
	 */
	public void visit(Assignment assignment) {
		assignment.getValue().accept(this);
	}

	public void visit(ASTError astError) {
		evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
	}

	public void visit(BackTickExpression expression) {
		// TODO Auto-generated method stub

	}

	public void visit(Block blockStatement) {
		throwException(blockStatement);
	}

	public void visit(BreakStatement breakStatement) {
		throwException(breakStatement);
	}

	public void visit(CastExpression castExpression) {
		castExpression.getExpr().accept(this);
	}

	public void visit(CatchClause catchStatement) {
		throwException(catchStatement);
	}

	public void visit(StaticConstantAccess classConstant) {
		evaluatedAttribute = scope.programScope.lookup(classConstant);
	}

	public void visit(ClassConstantDeclaration classConstantDeclaratio) {
		throwException(classConstantDeclaratio);
	}

	public void visit(ClassDeclaration classDeclaration) {
		throwException(classDeclaration);
	}

	/**
	 * Returns the class name declaration
	 */
	public void visit(ClassInstanceCreation classInstanceCreation) {
		classInstanceCreation.getClassName().accept(this);
	}

	public void visit(ClassName className) {
		if (className.getClassName().getType() == ASTNode.IDENTIFIER) {
			Identifier id = (Identifier) className.getClassName();
			evaluatedAttribute = new ClassAttribute(id.getName());
		} else {
			evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
		}
	}

	public void visit(CloneExpression cloneExpression) {
		cloneExpression.getExpr().accept(this);
	}

	public void visit(Comment comment) {
		throwException(comment);
	}

	public void visit(ConditionalExpression conditionalExpression) {
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
	}

	public void visit(ContinueStatement continueStatement) {
		throwException(continueStatement);
	}

	public void visit(DeclareStatement declareStatement) {
		throwException(declareStatement);
	}

	public void visit(Dispatch dispatch) {
		evaluatedAttribute = scope.lookup(dispatch);
	}

	public void visit(DoStatement doStatement) {
		throwException(doStatement);
	}

	public void visit(EchoStatement echoStatement) {
		throwException(echoStatement);
	}

	public void visit(EmptyStatement emptyStatement) {
		throwException(emptyStatement);
	}

	public void visit(ExpressionStatement expressionStatement) {
		throwException(expressionStatement);
	}

	public void visit(FieldsDeclaration classVariableDeclaratio) {
		throwException(classVariableDeclaratio);
	}

	public void visit(ForEachStatement forEachStatement) {
		throwException(forEachStatement);
	}

	public void visit(FormalParameter formalParameter) {
		throwException(formalParameter);
	}

	public void visit(ForStatement forStatement) {
		throwException(forStatement);
	}

	public void visit(FunctionDeclaration functionDeclaration) {
		throwException(functionDeclaration);
	}

	public void visit(FunctionInvocation functionInvocation) {
		scope.lookup(functionInvocation);
	}

	public void visit(FunctionName functionName) {
		throwException(functionName);
	}

	public void visit(GlobalStatement globalStatement) {
		throwException(globalStatement);
	}

	public void visit(Identifier identifier) {
		scope.lookup(identifier.getName());
	}

	public void visit(IfStatement ifStatement) {
		throwException(ifStatement);
	}

	public void visit(IgnoreError ignoreError) {
		// returns the expression attribute
		super.visit(ignoreError);
	}

	public void visit(Include include) {
		evaluatedAttribute = Attribute.NULL_ATTRIBUTE;
	}

	public void visit(InfixExpression infixExpression) {
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
				if (leftAttr.getType() == AttributeType.ARRAY_ATTRIBUTE || leftAttr.getType() == AttributeType.CLASS_ATTRIBUTE || rightAttr.getType() == AttributeType.STRING_ATTRIBUTE || rightAttr.getType() == AttributeType.CLASS_ATTRIBUTE) {
					// semantic error is detected
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
					// semantic error is detected
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
	}

	public void visit(InLineHtml inLineHtml) {
		throwException(inLineHtml);
	}

	public void visit(InstanceOfExpression instanceOfExpression) {
		evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
	}

	public void visit(InterfaceDeclaration interfaceDeclaration) {
		throwException(interfaceDeclaration);
	}

	public void visit(ListVariable listVariable) {
		scope.lookup(listVariable);
	}

	public void visit(MethodDeclaration classMethodDeclaration) {
		throwException(classMethodDeclaration);
	}

	public void visit(PostfixExpression postfixExpressions) {
		super.visit(postfixExpressions);
		resolveFixOperations();
	}

	public void visit(PrefixExpression prefixExpression) {
		super.visit(prefixExpression);
		resolveFixOperations();
	}

	public void visit(Program program) {
		throwException(program);
	}

	public void visit(Quote quote) {
		evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
	}

	public void visit(Reference reference) {
		reference.getExpression().accept(this);
	}

	public void visit(ReflectionVariable reflectionVariable) {
		scope.lookup(reflectionVariable);
	}

	public void visit(ReturnStatement returnStatement) {
		throwException(returnStatement);
	}

	public void visit(Scalar scalar) {
		switch (scalar.getScalarType()) {
			case Scalar.TYPE_INT:
				this.evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				break;
			case Scalar.TYPE_REAL:
				this.evaluatedAttribute = Attribute.REAL_ATTRIBUTE;
				break;
			case Scalar.TYPE_STRING:
				if ("true".equals(scalar.getStringValue()) || "false".equals(scalar.getStringValue())) {
					this.evaluatedAttribute = Attribute.BOOL_ATTRIBUTE;
				} else {
					this.evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
				}
				break;
			case Scalar.TYPE_SYSTEM:
				if ("__LINE__".endsWith(scalar.getStringValue())) {
					this.evaluatedAttribute = Attribute.INT_ATTRIBUTE;
				} else {
					this.evaluatedAttribute = Attribute.STRING_ATTRIBUTE;
				}
				break;
			default:
				assert false;
		}
	}

	public void visit(StaticFieldAccess staticMember) {
		scope.lookup(staticMember);
	}

	public void visit(StaticMethodInvocation staticMethodInvocation) {
		// TODO Auto-generated method stub

	}

	public void visit(StaticStatement staticStatement) {
		throwException(staticStatement);
	}

	public void visit(SwitchCase caseStatement) {
		throwException(caseStatement);
	}

	public void visit(SwitchStatement switchStatement) {
		throwException(switchStatement);
	}

	public void visit(ThrowStatement throwStatement) {
		throwException(throwStatement);
	}

	public void visit(TryStatement tryStatement) {
		throwException(tryStatement);
	}

	public void visit(UnaryOperation unaryOperation) {
		super.visit(unaryOperation);
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
	}

	public void visit(Variable variable) {
		scope.lookup(variable);
	}

	public void visit(WhileStatement whileStatement) {
		throwException(whileStatement);
	}
}
