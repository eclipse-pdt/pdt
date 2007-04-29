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
 * Trivial (empty) implementation of {@link Visitor}
 * This visitor traverses over the AST by default 
 * @see Visitor 
 */
public abstract class AbstractVisitor implements Visitor {

	public void visit(ArrayAccess arrayAccess) {
		arrayAccess.childrenAccept(this);
	}

	public void visit(ArrayCreation arrayCreation) {
		arrayCreation.childrenAccept(this);
	}

	public void visit(ArrayElement arrayElement) {
		arrayElement.childrenAccept(this);
	}

	public void visit(Assignment assignment) {
		assignment.childrenAccept(this);
	}

	public void visit(ASTError astError) {
		astError.childrenAccept(this);
	}

	public void visit(BackTickExpression backTickExpression) {
		backTickExpression.childrenAccept(this);
	}

	public void visit(Block block) {
		block.childrenAccept(this);
	}

	public void visit(BreakStatement breakStatement) {
		breakStatement.childrenAccept(this);
	}

	public void visit(CastExpression castExpression) {
		castExpression.childrenAccept(this);
	}

	public void visit(CatchClause catchClause) {
		catchClause.childrenAccept(this);
	}

	public void visit(ClassConstantDeclaration classConstantDeclaration) {
		classConstantDeclaration.childrenAccept(this);
	}

	public void visit(ClassDeclaration classDeclaration) {
		classDeclaration.childrenAccept(this);
	}

	public void visit(ClassInstanceCreation classInstanceCreation) {
		classInstanceCreation.childrenAccept(this);
	}

	public void visit(ClassName className) {
		className.childrenAccept(this);
	}

	public void visit(CloneExpression cloneExpression) {
		cloneExpression.childrenAccept(this);
	}

	public void visit(Comment comment) {
		comment.childrenAccept(this);
	}

	public void visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.childrenAccept(this);
	}

	public void visit(ContinueStatement continueStatement) {
		continueStatement.childrenAccept(this);
	}

	public void visit(DeclareStatement declareStatement) {
		declareStatement.childrenAccept(this);
	}

	public void visit(DoStatement doStatement) {
		doStatement.childrenAccept(this);
	}

	public void visit(EchoStatement echoStatement) {
		echoStatement.childrenAccept(this);
	}

	public void visit(EmptyStatement emptyStatement) {
		emptyStatement.childrenAccept(this);
	}

	public void visit(ExpressionStatement expressionStatement) {
		expressionStatement.childrenAccept(this);
	}

	public void visit(FieldAccess fieldAccess) {
		fieldAccess.childrenAccept(this);
	}

	public void visit(FieldsDeclaration fieldsDeclaration) {
		fieldsDeclaration.childrenAccept(this);
	}

	public void visit(ForEachStatement forEachStatement) {
		forEachStatement.childrenAccept(this);
	}

	public void visit(FormalParameter formalParameter) {
		formalParameter.childrenAccept(this);
	}

	public void visit(ForStatement forStatement) {
		forStatement.childrenAccept(this);
	}

	public void visit(FunctionDeclaration functionDeclaration) {
		functionDeclaration.childrenAccept(this);
	}

	public void visit(FunctionInvocation functionInvocation) {
		functionInvocation.childrenAccept(this);
	}

	public void visit(FunctionName functionName) {
		functionName.childrenAccept(this);
	}

	public void visit(GlobalStatement globalStatement) {
		globalStatement.childrenAccept(this);
	}

	public void visit(Identifier identifier) {
		identifier.childrenAccept(this);
	}

	public void visit(IfStatement ifStatement) {
		ifStatement.childrenAccept(this);
	}

	public void visit(IgnoreError ignoreError) {
		ignoreError.childrenAccept(this);
	}

	public void visit(Include include) {
		include.childrenAccept(this);
	}

	public void visit(InfixExpression infixExpression) {
		infixExpression.childrenAccept(this);
	}

	public void visit(InLineHtml inLineHtml) {
		inLineHtml.childrenAccept(this);
	}

	public void visit(InstanceOfExpression instanceOfExpression) {
		instanceOfExpression.childrenAccept(this);
	}

	public void visit(InterfaceDeclaration interfaceDeclaration) {
		interfaceDeclaration.childrenAccept(this);
	}

	public void visit(ListVariable listVariable) {
		listVariable.childrenAccept(this);
	}

	public void visit(MethodDeclaration methodDeclaration) {
		methodDeclaration.childrenAccept(this);
	}

	public void visit(MethodInvocation methodInvocation) {
		methodInvocation.childrenAccept(this);
	}

	public void visit(ParenthesisExpression parenthesisExpression) {
		parenthesisExpression.childrenAccept(this);		
	}

	public void visit(PostfixExpression postfixExpression) {
		postfixExpression.childrenAccept(this);
	}

	public void visit(PrefixExpression prefixExpression) {
		prefixExpression.childrenAccept(this);
	}

	public void visit(Program program) {
		program.childrenAccept(this);
	}

	public void visit(Quote quote) {
		quote.childrenAccept(this);
	}

	public void visit(Reference reference) {
		reference.childrenAccept(this);
	}

	public void visit(ReflectionVariable reflectionVariable) {
		reflectionVariable.childrenAccept(this);
	}

	public void visit(ReturnStatement returnStatement) {
		returnStatement.childrenAccept(this);
	}

	public void visit(Scalar scalar) {
		scalar.childrenAccept(this);
	}

	public void visit(StaticConstantAccess classConstantAccess) {
		classConstantAccess.childrenAccept(this);
	}

	public void visit(StaticFieldAccess staticFieldAccess) {
		staticFieldAccess.childrenAccept(this);
	}

	public void visit(StaticMethodInvocation staticMethodInvocation) {
		staticMethodInvocation.childrenAccept(this);
	}

	public void visit(StaticStatement staticStatement) {
		staticStatement.childrenAccept(this);
	}

	public void visit(SwitchCase switchCase) {
		switchCase.childrenAccept(this);
	}

	public void visit(SwitchStatement switchStatement) {
		switchStatement.childrenAccept(this);
	}

	public void visit(ThrowStatement throwStatement) {
		throwStatement.childrenAccept(this);
	}

	public void visit(TryStatement tryStatement) {
		tryStatement.childrenAccept(this);
	}

	public void visit(UnaryOperation unaryOperation) {
		unaryOperation.childrenAccept(this);
	}

	public void visit(Variable variable) {
		variable.childrenAccept(this);
	}

	public void visit(WhileStatement whileStatement) {
		whileStatement.childrenAccept(this);
	}
}
