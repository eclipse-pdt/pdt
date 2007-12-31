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
package org.eclipse.php.internal.core.ast.visitor;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * Abstract visitor to apply a single method over all AST nodes
 * In order to continue the traverse, one should call node.childrenAccept();
 */
public abstract class ApplyAll extends AbstractVisitor {

	/**
	 * Performs the apply method over each node
	 * @param node
	 */
	protected abstract boolean apply(ASTNode node);

	public boolean visit(ArrayElement arrayElement) {
		return apply(arrayElement);
	}

	public boolean visit(ArrayCreation arrayExpression) {
		return apply(arrayExpression);
	}

	public boolean visit(Assignment assignment) {
		return apply(assignment);
	}

	public boolean visit(ASTError astError) {
		return apply(astError);
	}

	public boolean visit(InfixExpression binaryOperation) {
		return apply(binaryOperation);
	}

	public boolean visit(Block blockStatement) {
		return apply(blockStatement);
	}

	public boolean visit(BreakStatement breakStatement) {
		return apply(breakStatement);
	}

	public boolean visit(SwitchCase caseStatement) {
		return apply(caseStatement);
	}

	public boolean visit(CastExpression castExpression) {
		return apply(castExpression);
	}

	public boolean visit(CatchClause catchStatement) {
		return apply(catchStatement);
	}

	public boolean visit(StaticConstantAccess classConstant) {
		return apply(classConstant);
	}

	public boolean visit(ClassConstantDeclaration classConstantDeclaratio) {
		return apply(classConstantDeclaratio);
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		return apply(classDeclaration);
	}

	public boolean visit(ClassInstanceCreation classInstanciation) {
		return apply(classInstanciation);
	}

	public boolean visit(MethodDeclaration classMethodDeclaration) {
		return apply(classMethodDeclaration);
	}

	public boolean visit(ClassName className) {
		return apply(className);
	}

	public boolean visit(FieldsDeclaration classVariableDeclaratio) {
		return apply(classVariableDeclaratio);
	}

	public boolean visit(CloneExpression cloneExpression) {
		return apply(cloneExpression);
	}

	public boolean visit(Comment comment) {
		return apply(comment);
	}

	public boolean visit(ConditionalExpression conditionalExpression) {
		return apply(conditionalExpression);
	}

	public boolean visit(ContinueStatement continueStatement) {
		return apply(continueStatement);
	}

	public boolean visit(DeclareStatement declareStatement) {
		return apply(declareStatement);
	}

	public boolean visit(Dispatch dispatch) {
		return apply(dispatch);
	}

	public boolean visit(DoStatement doStatement) {
		return apply(doStatement);
	}

	public boolean visit(EchoStatement echoStatement) {
		return apply(echoStatement);
	}

	public boolean visit(EmptyStatement evalStatement) {
		return apply(evalStatement);
	}

	public boolean visit(ForEachStatement forEachStatement) {
		return apply(forEachStatement);
	}

	public boolean visit(FormalParameter formalParameter) {
		return apply(formalParameter);
	}

	public boolean visit(ForStatement forStatement) {
		return apply(forStatement);
	}

	public boolean visit(FunctionDeclaration functionDeclaration) {
		return apply(functionDeclaration);
	}

	public boolean visit(FunctionInvocation functionInvocation) {
		return apply(functionInvocation);
	}

	public boolean visit(FunctionName functionName) {
		return apply(functionName);
	}

	public boolean visit(GlobalStatement globalStatement) {
		return apply(globalStatement);
	}

	public boolean visit(Identifier identifier) {
		return apply(identifier);
	}

	public boolean visit(IfStatement ifStatement) {
		return apply(ifStatement);
	}

	public boolean visit(IgnoreError ignoreError) {
		return apply(ignoreError);
	}

	public boolean visit(Include include) {
		return apply(include);
	}

	public boolean visit(ArrayAccess indexedVariable) {
		return apply(indexedVariable);
	}

	public boolean visit(InLineHtml inLineHtml) {
		return apply(inLineHtml);
	}

	public boolean visit(InstanceOfExpression instanceOfExpression) {
		return apply(instanceOfExpression);
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		return apply(interfaceDeclaration);
	}

	public boolean visit(ListVariable listVariable) {
		return apply(listVariable);
	}

	public boolean visit(PostfixExpression postfixExpressions) {
		return apply(postfixExpressions);
	}

	public boolean visit(PrefixExpression prefixExpression) {
		return apply(prefixExpression);
	}

	public boolean visit(Program program) {
		return apply(program);
	}

	public boolean visit(Quote quote) {
		return apply(quote);
	}

	public boolean visit(Reference reference) {
		return apply(reference);
	}

	public boolean visit(ReflectionVariable reflectionVariable) {
		return apply(reflectionVariable);
	}

	public boolean visit(ReturnStatement returnStatement) {
		return apply(returnStatement);
	}

	public boolean visit(Scalar scalar) {
		return apply(scalar);
	}

	public boolean visit(StaticFieldAccess staticMember) {
		return apply(staticMember);
	}

	public boolean visit(StaticStatement staticStatement) {
		return apply(staticStatement);
	}

	public boolean visit(SwitchStatement switchStatement) {
		return apply(switchStatement);
	}

	public boolean visit(ThrowStatement throwStatement) {
		return apply(throwStatement);
	}

	public boolean visit(TryStatement tryStatement) {
		return apply(tryStatement);
	}

	public boolean visit(UnaryOperation unaryOperation) {
		return apply(unaryOperation);
	}

	public boolean visit(Variable variable) {
		return apply(variable);
	}

	public boolean visit(WhileStatement whileStatement) {
		return apply(whileStatement);
	}
}
