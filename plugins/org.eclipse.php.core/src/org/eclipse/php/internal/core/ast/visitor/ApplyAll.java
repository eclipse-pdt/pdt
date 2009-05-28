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
 * Abstract visitor to apply a single method over all AST nodes
 * In order to continue the traverse, one should call node.childrenAccept();
 */
public abstract class ApplyAll extends AbstractVisitor {

	/**
	 * Performs the apply method over each node
	 * @param node
	 */
	protected abstract boolean apply(ASTNode node);

	/**
	 * Performs the end visit method over each node
	 * @param node
	 */
	public void endVisitNode(ASTNode node) {
		return;
	}

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

	public boolean visit(BackTickExpression backTickExpression) {
		return apply(backTickExpression);
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

	public boolean visit(ConstantDeclaration classConstantDeclaratio) {
		return apply(classConstantDeclaratio);
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		return apply(classDeclaration);
	}

	public boolean visit(ClassInstanceCreation classInstanciation) {
		return apply(classInstanciation);
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
	
	public boolean visit(ExpressionStatement expressionStatement) {
		return apply(expressionStatement);
	}	

	public boolean visit(FieldAccess filedAccess) {
		return apply(filedAccess);
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

	public boolean visit(MethodDeclaration classMethodDeclaration) {
		return apply(classMethodDeclaration);
	}
	
	public boolean visit(MethodInvocation methodInvocation) {
		return apply(methodInvocation);
	}	
	
	public boolean visit(ParenthesisExpression parenthesisExpression) {
		return apply(parenthesisExpression);
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

	public boolean visit(SingleFieldDeclaration singleFieldDeclaration) {
		return apply(singleFieldDeclaration);
	}
	
	public boolean visit(StaticFieldAccess staticMember) {
		return apply(staticMember);
	}

	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		return apply(staticMethodInvocation);
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

	public boolean visit(NamespaceName name) {
		return apply(name);
	}

	public boolean visit(NamespaceDeclaration decl) {
		return apply(decl);
	}

	public boolean visit(UseStatementPart usePart) {
		return apply(usePart);
	}

	public boolean visit(UseStatement statement) {
		return apply(statement);
	}

	public boolean visit(LambdaFunctionDeclaration func) {
		return apply(func);
	}

	public boolean visit(GotoLabel gotoLabel) {
		return apply(gotoLabel);
	}

	public boolean visit(GotoStatement gotoStatement) {
		return apply(gotoStatement);
	}
	
	public void endVisit(ArrayAccess arrayAccess) {
		endVisitNode(arrayAccess);
	}

	public void endVisit(ArrayCreation arrayCreation) {
		endVisitNode(arrayCreation);
	}

	public void endVisit(ArrayElement arrayElement) {
		endVisitNode(arrayElement);
	}

	public void endVisit(ASTError astError) {
		endVisitNode(astError);
	}

	public void endVisit(BackTickExpression backTickExpression) {
		endVisitNode(backTickExpression);
	}

	public void endVisit(Block block) {
		endVisitNode(block);
	}

	public void endVisit(BreakStatement breakStatement) {
		endVisitNode(breakStatement);
	}

	public void endVisit(CastExpression castExpression) {
		endVisitNode(castExpression);
	}

	public void endVisit(CatchClause catchClause) {
		endVisitNode(catchClause);
	}

	public void endVisit(ConstantDeclaration classConstantDeclaration) {
		endVisitNode(classConstantDeclaration);
	}

	public void endVisit(ClassDeclaration classDeclaration) {
		endVisitNode(classDeclaration);
	}

	public void endVisit(ClassInstanceCreation classInstanceCreation) {
		endVisitNode(classInstanceCreation);
	}

	public void endVisit(ClassName className) {
		endVisitNode(className);
	}

	public void endVisit(CloneExpression cloneExpression) {
		endVisitNode(cloneExpression);
	}

	public void endVisit(Comment comment) {
		endVisitNode(comment);
	}

	public void endVisit(ConditionalExpression conditionalExpression) {
		endVisitNode(conditionalExpression);
	}

	public void endVisit(ContinueStatement continueStatement) {
		endVisitNode(continueStatement);
	}

	public void endVisit(DeclareStatement declareStatement) {
		endVisitNode(declareStatement);
	}

	public void endVisit(DoStatement doStatement) {
		endVisitNode(doStatement);
	}

	public void endVisit(EchoStatement echoStatement) {
		endVisitNode(echoStatement);		
	}

	public void endVisit(EmptyStatement emptyStatement) {
		endVisitNode(emptyStatement);
	}

	public void endVisit(ExpressionStatement expressionStatement) {
		endVisitNode(expressionStatement);
	}

	public void endVisit(FieldAccess fieldAccess) {
		endVisitNode(fieldAccess);
	}

	public void endVisit(FieldsDeclaration fieldsDeclaration) {
		endVisitNode(fieldsDeclaration);
	}

	public void endVisit(ForEachStatement forEachStatement) {
		endVisitNode(forEachStatement);
	}

	public void endVisit(FormalParameter formalParameter) {
		endVisitNode(formalParameter);
	}

	public void endVisit(ForStatement forStatement) {
		endVisitNode(forStatement);
	}

	public void endVisit(FunctionDeclaration functionDeclaration) {
		endVisitNode(functionDeclaration);
	}

	public void endVisit(FunctionInvocation functionInvocation) {
		endVisitNode(functionInvocation);
	}

	public void endVisit(FunctionName functionName) {
		endVisitNode(functionName);
	}

	public void endVisit(GlobalStatement globalStatement) {
		endVisitNode(globalStatement);
	}

	public void endVisit(Identifier identifier) {
		endVisitNode(identifier);
	}

	public void endVisit(IfStatement ifStatement) {
		endVisitNode(ifStatement);
	}

	public void endVisit(IgnoreError ignoreError) {
		endVisitNode(ignoreError);
	}

	public void endVisit(Include include) {
		endVisitNode(include);
	}

	public void endVisit(InfixExpression infixExpression) {
		endVisitNode(infixExpression);
	}

	public void endVisit(InLineHtml inLineHtml) {
		endVisitNode(inLineHtml);
	}

	public void endVisit(InstanceOfExpression instanceOfExpression) {
		endVisitNode(instanceOfExpression);
	}

	public void endVisit(InterfaceDeclaration interfaceDeclaration) {
		endVisitNode(interfaceDeclaration);
	}

	public void endVisit(ListVariable listVariable) {
		endVisitNode(listVariable);
	}

	public void endVisit(MethodDeclaration methodDeclaration) {
		endVisitNode(methodDeclaration);
	}

	public void endVisit(MethodInvocation methodInvocation) {
		endVisitNode(methodInvocation);
	}

	public void endVisit(ParenthesisExpression parenthesisExpression) {
		endVisitNode(parenthesisExpression);
	}

	public void endVisit(PostfixExpression postfixExpression) {
		endVisitNode(postfixExpression);
	}

	public void endVisit(PrefixExpression prefixExpression) {
		endVisitNode(prefixExpression);
	}

	public void endVisit(Program program) {
		endVisitNode(program);
	}

	public void endVisit(Quote quote) {
		endVisitNode(quote);
	}

	public void endVisit(Reference reference) {
		endVisitNode(reference);
	}

	public void endVisit(ReflectionVariable reflectionVariable) {
		endVisitNode(reflectionVariable);
	}

	public void endVisit(ReturnStatement returnStatement) {
		endVisitNode(returnStatement);
	}

	public void endVisit(Scalar scalar) {
		endVisitNode(scalar);
	}
	
	public void endVisit(SingleFieldDeclaration singleFieldDeclaration) {
		endVisitNode(singleFieldDeclaration);
	}	

	public void endVisit(StaticConstantAccess staticConstantAccess) {
		endVisitNode(staticConstantAccess);
	}

	public void endVisit(StaticFieldAccess staticFieldAccess) {
		endVisitNode(staticFieldAccess);
	}

	public void endVisit(StaticMethodInvocation staticMethodInvocation) {
		endVisitNode(staticMethodInvocation);
	}

	public void endVisit(StaticStatement staticStatement) {
		endVisitNode(staticStatement);
	}

	public void endVisit(SwitchCase switchCase) {
		endVisitNode(switchCase);
	}

	public void endVisit(SwitchStatement switchStatement) {
		endVisitNode(switchStatement);
	}

	public void endVisit(ThrowStatement throwStatement) {
		endVisitNode(throwStatement);
	}

	public void endVisit(TryStatement tryStatement) {
		endVisitNode(tryStatement);
	}

	public void endVisit(UnaryOperation unaryOperation) {
		endVisitNode(unaryOperation);
	}

	public void endVisit(Variable variable) {
		endVisitNode(variable);
	}

	public void endVisit(WhileStatement whileStatement) {
		endVisitNode(whileStatement);
	}

	public void endVisit(Assignment assignment) {
		endVisitNode(assignment);
	}

	public void endVisit(NamespaceName name) {
		endVisitNode(name);
	}

	public void endVisit(NamespaceDeclaration decl) {
		endVisitNode(decl);
	}

	public void endVisit(UseStatementPart part) {
		endVisitNode(part);
	}

	public void endVisit(UseStatement useStatement) {
		endVisitNode(useStatement);
	}

	public void endVisit(LambdaFunctionDeclaration func) {
		endVisitNode(func);
	}

	public void endVisit(GotoStatement gotoStatement) {
		endVisitNode(gotoStatement);
	}

	public void endVisit(GotoLabel gotoLabel) {
		endVisitNode(gotoLabel);
	}
}
