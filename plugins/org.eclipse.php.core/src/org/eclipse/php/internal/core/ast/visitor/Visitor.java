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
package org.eclipse.php.internal.core.ast.visitor;

import org.eclipse.php.internal.core.ast.nodes.*;

/**
 * A visitor for abstract syntax trees.
 * <p>
 * For each different concrete AST node type there is a method that visits the
 * given node to perform some arbitrary operation.
 * <p>
 * Subclasses may implement this method as needed.
 * <p>
 * 
 * @author Moshe S., Roy G. ,2007
 */
public interface Visitor {

	/**
	 * Visits the given AST node prior to the type-specific visit. (before
	 * <code>visit</code>).
	 * <p>
	 * The default implementation does nothing. Subclasses may reimplement.
	 * </p>
	 * 
	 * @param node
	 *            the node to visit
	 */
	public void preVisit(ASTNode node);

	/**
	 * Visits the given AST node following the type-specific visit (after
	 * <code>endVisit</code>).
	 * <p>
	 * The default implementation does nothing. Subclasses may reimplement.
	 * </p>
	 * 
	 * @param node
	 *            the node to visit
	 */
	public void postVisit(ASTNode node);

	public boolean visit(ArrayAccess arrayAccess);

	public void endVisit(ArrayAccess arrayAccess);

	public boolean visit(ArrayCreation arrayCreation);

	public void endVisit(ArrayCreation arrayCreation);

	public boolean visit(ArrayElement arrayElement);

	public void endVisit(ArrayElement arrayElement);

	public boolean visit(Assignment assignment);

	public void endVisit(Assignment assignment);

	public boolean visit(ASTError astError);

	public void endVisit(ASTError astError);

	public boolean visit(BackTickExpression backTickExpression);

	public void endVisit(BackTickExpression backTickExpression);

	public boolean visit(Block block);

	public void endVisit(Block block);

	public boolean visit(BreakStatement breakStatement);

	public void endVisit(BreakStatement breakStatement);

	public boolean visit(CastExpression castExpression);

	public void endVisit(CastExpression castExpression);

	public boolean visit(CatchClause catchClause);

	public void endVisit(CatchClause catchClause);

	public boolean visit(ConstantDeclaration classConstantDeclaration);

	public void endVisit(ConstantDeclaration classConstantDeclaration);

	public boolean visit(ClassDeclaration classDeclaration);

	public void endVisit(ClassDeclaration classDeclaration);

	public boolean visit(ClassInstanceCreation classInstanceCreation);

	public void endVisit(ClassInstanceCreation classInstanceCreation);

	public boolean visit(ClassName className);

	public void endVisit(ClassName className);

	public boolean visit(CloneExpression cloneExpression);

	public void endVisit(CloneExpression cloneExpression);

	public boolean visit(Comment comment);

	public void endVisit(Comment comment);

	public boolean visit(ConditionalExpression conditionalExpression);

	public void endVisit(ConditionalExpression conditionalExpression);

	public boolean visit(ContinueStatement continueStatement);

	public void endVisit(ContinueStatement continueStatement);

	public boolean visit(DeclareStatement declareStatement);

	public void endVisit(DeclareStatement declareStatement);

	public boolean visit(DoStatement doStatement);

	public void endVisit(DoStatement doStatement);

	public boolean visit(EchoStatement echoStatement);

	public void endVisit(EchoStatement echoStatement);

	public boolean visit(EmptyStatement emptyStatement);

	public void endVisit(EmptyStatement emptyStatement);

	public boolean visit(ExpressionStatement expressionStatement);

	public void endVisit(ExpressionStatement expressionStatement);

	public boolean visit(FieldAccess fieldAccess);

	public void endVisit(FieldAccess fieldAccess);

	public boolean visit(FieldsDeclaration fieldsDeclaration);

	public void endVisit(FieldsDeclaration fieldsDeclaration);

	public boolean visit(ForEachStatement forEachStatement);

	public void endVisit(ForEachStatement forEachStatement);

	public boolean visit(FormalParameter formalParameter);

	public void endVisit(FormalParameter formalParameter);

	public boolean visit(ForStatement forStatement);

	public void endVisit(ForStatement forStatement);

	public boolean visit(FunctionDeclaration functionDeclaration);

	public void endVisit(FunctionDeclaration functionDeclaration);

	public boolean visit(FunctionInvocation functionInvocation);

	public void endVisit(FunctionInvocation functionInvocation);

	public boolean visit(FunctionName functionName);

	public void endVisit(FunctionName functionName);

	public boolean visit(GlobalStatement globalStatement);

	public void endVisit(GlobalStatement globalStatement);

	public boolean visit(GotoLabel gotoLabel);

	public void endVisit(GotoLabel gotoLabel);

	public boolean visit(GotoStatement gotoStatement);

	public void endVisit(GotoStatement gotoStatement);

	public boolean visit(Identifier identifier);

	public void endVisit(Identifier identifier);

	public boolean visit(IfStatement ifStatement);

	public void endVisit(IfStatement ifStatement);

	public boolean visit(IgnoreError ignoreError);

	public void endVisit(IgnoreError ignoreError);

	public boolean visit(Include include);

	public void endVisit(Include include);

	public boolean visit(InfixExpression infixExpression);

	public void endVisit(InfixExpression infixExpression);

	public boolean visit(InLineHtml inLineHtml);

	public void endVisit(InLineHtml inLineHtml);

	public boolean visit(InstanceOfExpression instanceOfExpression);

	public void endVisit(InstanceOfExpression instanceOfExpression);

	public boolean visit(InterfaceDeclaration interfaceDeclaration);

	public void endVisit(InterfaceDeclaration interfaceDeclaration);

	public boolean visit(LambdaFunctionDeclaration lambdaFunctionDeclaration);

	public void endVisit(LambdaFunctionDeclaration lambdaFunctionDeclaration);

	public boolean visit(ListVariable listVariable);

	public void endVisit(ListVariable listVariable);

	public boolean visit(MethodDeclaration methodDeclaration);

	public void endVisit(MethodDeclaration methodDeclaration);

	public boolean visit(MethodInvocation methodInvocation);

	public void endVisit(MethodInvocation methodInvocation);

	public boolean visit(NamespaceName namespaceName);

	public void endVisit(NamespaceName namespaceName);

	public boolean visit(NamespaceDeclaration namespaceDeclaration);

	public void endVisit(NamespaceDeclaration namespaceDeclaration);

	public boolean visit(ParenthesisExpression parenthesisExpression);

	public void endVisit(ParenthesisExpression parenthesisExpression);

	public boolean visit(PostfixExpression postfixExpression);

	public void endVisit(PostfixExpression postfixExpression);

	public boolean visit(PrefixExpression prefixExpression);

	public void endVisit(PrefixExpression prefixExpression);

	public boolean visit(Program program);

	public void endVisit(Program program);

	public boolean visit(Quote quote);

	public void endVisit(Quote quote);

	public boolean visit(Reference reference);

	public void endVisit(Reference reference);

	public boolean visit(ReflectionVariable reflectionVariable);

	public void endVisit(ReflectionVariable reflectionVariable);

	public boolean visit(ReturnStatement returnStatement);

	public void endVisit(ReturnStatement returnStatement);

	public boolean visit(Scalar scalar);

	public void endVisit(Scalar scalar);

	public boolean visit(SingleFieldDeclaration singleFieldDeclaration);

	public void endVisit(SingleFieldDeclaration singleFieldDeclaration);

	public boolean visit(StaticConstantAccess classConstantAccess);

	public void endVisit(StaticConstantAccess staticConstantAccess);

	public boolean visit(StaticFieldAccess staticFieldAccess);

	public void endVisit(StaticFieldAccess staticFieldAccess);

	public boolean visit(StaticMethodInvocation staticMethodInvocation);

	public void endVisit(StaticMethodInvocation staticMethodInvocation);

	public boolean visit(StaticStatement staticStatement);

	public void endVisit(StaticStatement staticStatement);

	public boolean visit(SwitchCase switchCase);

	public void endVisit(SwitchCase switchCase);

	public boolean visit(SwitchStatement switchStatement);

	public void endVisit(SwitchStatement switchStatement);

	public boolean visit(ThrowStatement throwStatement);

	public void endVisit(ThrowStatement throwStatement);

	public boolean visit(TryStatement tryStatement);

	public void endVisit(TryStatement tryStatement);

	public boolean visit(UnaryOperation unaryOperation);

	public void endVisit(UnaryOperation unaryOperation);

	public boolean visit(Variable variable);

	public void endVisit(Variable variable);

	public boolean visit(UseStatement useStatement);

	public void endVisit(UseStatement useStatement);

	public boolean visit(UseStatementPart useStatementPart);

	public void endVisit(UseStatementPart useStatementPart);

	public boolean visit(WhileStatement whileStatement);

	public void endVisit(WhileStatement whileStatement);

	public boolean visit(ASTNode node);

	public void endVisit(ASTNode node);

	// php5.4 starts

	public boolean visit(ChainingInstanceCall node);

	public void endVisit(ChainingInstanceCall node);

	public boolean visit(DereferenceNode node);

	public void endVisit(DereferenceNode node);

	public boolean visit(FullyQualifiedTraitMethodReference node);

	public void endVisit(FullyQualifiedTraitMethodReference node);

	public boolean visit(PHPArrayDereferenceList node);

	public void endVisit(PHPArrayDereferenceList node);

	public boolean visit(TraitAlias node);

	public void endVisit(TraitAlias node);

	public boolean visit(TraitAliasStatement node);

	public void endVisit(TraitAliasStatement node);

	public boolean visit(TraitDeclaration node);

	public void endVisit(TraitDeclaration node);

	public boolean visit(TraitPrecedence node);

	public void endVisit(TraitPrecedence node);

	public boolean visit(TraitPrecedenceStatement node);

	public void endVisit(TraitPrecedenceStatement node);

	public boolean visit(TraitUseStatement node);

	public void endVisit(TraitUseStatement node);
	// php5.4 ends

}
