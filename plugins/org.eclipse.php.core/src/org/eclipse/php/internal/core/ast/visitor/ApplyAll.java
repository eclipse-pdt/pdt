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
public class ApplyAll extends AbstractVisitor {

	/**
	 * Performs the apply method over each node
	 * @param node
	 */
	public void apply(ASTNode node) {
	};

	public void visit(ArrayElement arrayElement) {
		apply(arrayElement);
	}

	public void visit(ArrayCreation arrayExpression) {
		apply(arrayExpression);
	}

	public void visit(Assignment assignment) {
		apply(assignment);
	}

	public void visit(ASTError astError) {
		apply(astError);
	}

	public void visit(InfixExpression binaryOperation) {
		apply(binaryOperation);
	}

	public void visit(Block blockStatement) {
		apply(blockStatement);
	}

	public void visit(BreakStatement breakStatement) {
		apply(breakStatement);
	}

	public void visit(SwitchCase caseStatement) {
		apply(caseStatement);
	}

	public void visit(CastExpression castExpression) {
		apply(castExpression);
	}

	public void visit(CatchClause catchStatement) {
		apply(catchStatement);
	}

	public void visit(StaticConstantAccess classConstant) {
		apply(classConstant);
	}

	public void visit(ClassConstantDeclaration classConstantDeclaratio) {
		apply(classConstantDeclaratio);
	}

	public void visit(ClassDeclaration classDeclaration) {
		apply(classDeclaration);
	}

	public void visit(ClassInstanceCreation classInstanciation) {
		apply(classInstanciation);
	}

	public void visit(MethodDeclaration classMethodDeclaration) {
		apply(classMethodDeclaration);
	}

	public void visit(ClassName className) {
		apply(className);
	}

	public void visit(FieldsDeclaration classVariableDeclaratio) {
		apply(classVariableDeclaratio);
	}

	public void visit(CloneExpression cloneExpression) {
		apply(cloneExpression);
	}

	public void visit(Comment comment) {
		apply(comment);
	}

	public void visit(ConditionalExpression conditionalExpression) {
		apply(conditionalExpression);
	}

	public void visit(ContinueStatement continueStatement) {
		apply(continueStatement);
	}

	public void visit(DeclareStatement declareStatement) {
		apply(declareStatement);
	}

	public void visit(Dispatch dispatch) {
		apply(dispatch);
	}

	public void visit(DoStatement doStatement) {
		apply(doStatement);
	}

	public void visit(EchoStatement echoStatement) {
		apply(echoStatement);
	}

	public void visit(EmptyStatement evalStatement) {
		apply(evalStatement);
	}

	public void visit(ForEachStatement forEachStatement) {
		apply(forEachStatement);
	}

	public void visit(FormalParameter formalParameter) {
		apply(formalParameter);
	}

	public void visit(ForStatement forStatement) {
		apply(forStatement);
	}

	public void visit(FunctionDeclaration functionDeclaration) {
		apply(functionDeclaration);
	}

	public void visit(FunctionInvocation functionInvocation) {
		apply(functionInvocation);
	}

	public void visit(FunctionName functionName) {
		apply(functionName);
	}

	public void visit(GlobalStatement globalStatement) {
		apply(globalStatement);
	}

	public void visit(Identifier identifier) {
		apply(identifier);
	}

	public void visit(IfStatement ifStatement) {
		apply(ifStatement);
	}

	public void visit(IgnoreError ignoreError) {
		apply(ignoreError);
	}

	public void visit(Include include) {
		apply(include);
	}

	public void visit(ArrayAccess indexedVariable) {
		apply(indexedVariable);
	}

	public void visit(InLineHtml inLineHtml) {
		apply(inLineHtml);
	}

	public void visit(InstanceOfExpression instanceOfExpression) {
		apply(instanceOfExpression);
	}

	public void visit(InterfaceDeclaration interfaceDeclaration) {
		apply(interfaceDeclaration);
	}

	public void visit(ListVariable listVariable) {
		apply(listVariable);
	}

	public void visit(PostfixExpression postfixExpressions) {
		apply(postfixExpressions);
	}

	public void visit(PrefixExpression prefixExpression) {
		apply(prefixExpression);
	}

	public void visit(Program program) {
		apply(program);
	}

	public void visit(Quote quote) {
		apply(quote);
	}

	public void visit(Reference reference) {
		apply(reference);
	}

	public void visit(ReflectionVariable reflectionVariable) {
		apply(reflectionVariable);
	}

	public void visit(ReturnStatement returnStatement) {
		apply(returnStatement);
	}

	public void visit(Scalar scalar) {
		apply(scalar);
	}

	public void visit(StaticFieldAccess staticMember) {
		apply(staticMember);
	}

	public void visit(StaticStatement staticStatement) {
		apply(staticStatement);
	}

	public void visit(SwitchStatement switchStatement) {
		apply(switchStatement);
	}

	public void visit(ThrowStatement throwStatement) {
		apply(throwStatement);
	}

	public void visit(TryStatement tryStatement) {
		apply(tryStatement);
	}

	public void visit(UnaryOperation unaryOperation) {
		apply(unaryOperation);
	}

	public void visit(Variable variable) {
		apply(variable);
	}

	public void visit(WhileStatement whileStatement) {
		apply(whileStatement);
	}
}
