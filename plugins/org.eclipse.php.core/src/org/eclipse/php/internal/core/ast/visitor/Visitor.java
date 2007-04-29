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
 * A visitor for abstract syntax trees. <p>
 * For each different concrete AST node type there is a method that visits
 * the given node to perform some arbitrary operation.<p> 
 * Subclasses may implement this method as needed.<p>
 * 
 * @author Moshe S., Roy G. ,2007
 */
public interface Visitor {

	public void visit(ArrayAccess arrayAccess);

	public void visit(ArrayCreation arrayCreation);

	public void visit(ArrayElement arrayElement);

	public void visit(Assignment assignment);

	public void visit(ASTError astError);

	public void visit(BackTickExpression backTickExpression);

	public void visit(Block block);

	public void visit(BreakStatement breakStatement);

	public void visit(CastExpression castExpression);

	public void visit(CatchClause catchClause);

	public void visit(ClassConstantDeclaration classConstantDeclaration);

	public void visit(ClassDeclaration classDeclaration);

	public void visit(ClassInstanceCreation classInstanceCreation);

	public void visit(ClassName className);

	public void visit(CloneExpression cloneExpression);

	public void visit(Comment comment);

	public void visit(ConditionalExpression conditionalExpression);

	public void visit(ContinueStatement continueStatement);

	public void visit(DeclareStatement declareStatement);

	public void visit(DoStatement doStatement);

	public void visit(EchoStatement echoStatement);

	public void visit(EmptyStatement emptyStatement);

	public void visit(ExpressionStatement expressionStatement);

	public void visit(FieldAccess fieldAccess);

	public void visit(FieldsDeclaration fieldsDeclaration);

	public void visit(ForEachStatement forEachStatement);

	public void visit(FormalParameter formalParameter);

	public void visit(ForStatement forStatement);

	public void visit(FunctionDeclaration functionDeclaration);

	public void visit(FunctionInvocation functionInvocation);

	public void visit(FunctionName functionName);

	public void visit(GlobalStatement globalStatement);

	public void visit(Identifier identifier);

	public void visit(IfStatement ifStatement);

	public void visit(IgnoreError ignoreError);

	public void visit(Include include);

	public void visit(InfixExpression infixExpression);

	public void visit(InLineHtml inLineHtml);

	public void visit(InstanceOfExpression instanceOfExpression);

	public void visit(InterfaceDeclaration interfaceDeclaration);

	public void visit(ListVariable listVariable);

	public void visit(MethodDeclaration methodDeclaration);

	public void visit(MethodInvocation methodInvocation);

	public void visit(ParenthesisExpression parenthesisExpression);

	public void visit(PostfixExpression postfixExpression);

	public void visit(PrefixExpression prefixExpression);

	public void visit(Program program);

	public void visit(Quote quote);

	public void visit(Reference reference);

	public void visit(ReflectionVariable reflectionVariable);

	public void visit(ReturnStatement returnStatement);

	public void visit(Scalar scalar);

	public void visit(StaticConstantAccess classConstantAccess);

	public void visit(StaticFieldAccess staticFieldAccess);

	public void visit(StaticMethodInvocation staticMethodInvocation);

	public void visit(StaticStatement staticStatement);

	public void visit(SwitchCase switchCase);

	public void visit(SwitchStatement switchStatement);

	public void visit(ThrowStatement throwStatement);

	public void visit(TryStatement tryStatement);

	public void visit(UnaryOperation unaryOperation);

	public void visit(Variable variable);

	public void visit(WhileStatement whileStatement);
}
