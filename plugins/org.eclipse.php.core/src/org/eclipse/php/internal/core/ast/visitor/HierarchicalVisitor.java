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
 * <p>This class provides a convenient behaviour-only 
 * extension mechanism for the ASTNode hierarchy.
 * If you feel like you would like to add a method to
 * the ASTNode hierarchy (or a subtree of the hierarchy),
 * and you want to have different implementations
 * of it at different points in the hierarchy,
 * simply create a HierarchicalASTVisitor representing
 * the new method and all its implementations,
 * locating each implementation within the right
 * visit(XX) method.  If you wanted to add a method implementation to abstract
 * class Foo, an ASTNode descendant, put your implementation in visit(Foo). 
 * This class will provide appropriate dispatch, just as if the method
 * implementations had been added to the ASTNode hierarchy.
 * </p>
 * 
 * <p><b>Details:<b></p>
 * 
 * <p>This class has a visit(XX node) method for every for every 
 * class (concrete or abstract) XX in the ASTNode hierarchy. In this class'
 * default implementations of these methods, the method corresponding to a given
 * ASTNode descendant class will call (and return the return value of) the
 * visit(YY) method for it's superclass YY, with the exception of the
 * visit(ASTNode) method which simply returns true, since ASTNode doesn't have a
 * superclass that is within the ASTNode hierarchy.
 * </p>
 * 
 * <p>Because of this organization, when visit(XX) methods  are overridden in a
 * subclass, and the visitor is applied to a node, only the most specialized
 * overridden method implementation for the node's type will be called, unless
 * this most specialized method calls other visit methods (this is discouraged)
 * or, (preferably) calls super.visit(XX node), (the reference type of the
 * parameter must be XX) which will invoke this class' implementation of the
 * method, which will, in turn, invoke the visit(YY) method corresponding to the
 * superclass, YY.
 * </p>
 * 
 * <p>Thus, the dispatching behaviour achieved when 
 * HierarchicalASTVisitors' visit(XX) methods, corresponding to a particular
 * concrete or abstract ASTNode descendant class, are overridden is exactly
 * analogous to the dispatching behaviour obtained when method implementations
 * are added to the same ASTNode descendant classes.
 * </p>
 */
public class HierarchicalVisitor extends AbstractVisitor {
	
	/**
	 * Abstract Nodes that we added to the abstract visitor 
	 */
	public boolean visit(ASTNode node) {
		return true;
	}

	public boolean visit(Statement statement) {
		return visit((ASTNode) statement);
	}
	
	public boolean visit(Expression expression) {
		return visit((ASTNode) expression);
	}

	public boolean visit(TypeDeclaration typeDeclaration) {
		return visit((Statement) typeDeclaration);
	}

	public boolean visit(VariableBase variableBase) {
		return visit((Expression) variableBase);
	}
	
	public boolean visit(Dispatch dispatch) {
		return visit((VariableBase) dispatch);
	}
	
	public boolean visit(StaticDispatch staticDispatch) {
		return visit((VariableBase) staticDispatch);
	}
	
	public boolean visit(BodyDeclaration bodyDeclaration) {
		return visit((Statement) bodyDeclaration);
	}
	
	/**
	 * Redirect to the hierarchical node
	 */
	
	public boolean visit(ArrayAccess arrayAccess) {
		return visit((Variable) arrayAccess);
	}

	public boolean visit(ArrayCreation arrayCreation) {
		return visit((Expression) arrayCreation);
	}

	public boolean visit(ArrayElement arrayElement) {
		return visit((ASTNode) arrayElement);
	}

	public boolean visit(Assignment assignment) {
		return visit((Expression) assignment);
	}

	public boolean visit(ASTError astError) {
		return visit((Statement) astError);
	}

	public boolean visit(BackTickExpression backTickExpression) {
		return visit((Expression) backTickExpression);
	}

	public boolean visit(Block block) {
		return visit((Statement) block);
	}

	public boolean visit(BreakStatement breakStatement) {
		return visit((Statement) breakStatement);
	}

	public boolean visit(CastExpression castExpression) {
		return visit((Expression) castExpression);
	}

	public boolean visit(CatchClause catchClause) {
		return visit((Statement) catchClause);
	}

	public boolean visit(ConstantDeclaration classConstantDeclaration) {
		return visit((Statement) classConstantDeclaration);
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		return visit((TypeDeclaration) classDeclaration);
	}

	public boolean visit(ClassInstanceCreation classInstanceCreation) {
		return visit((Expression) classInstanceCreation);
	}

	public boolean visit(ClassName className) {
		return visit((ASTNode) className);
	}

	public boolean visit(CloneExpression cloneExpression) {
		return visit((Expression) cloneExpression);
	}

	public boolean visit(Comment comment) {
		return visit((ASTNode) comment);
	}

	public boolean visit(ConditionalExpression conditionalExpression) {
		return visit((Expression) conditionalExpression);
	}

	public boolean visit(ContinueStatement continueStatement) {
		return visit((Statement) continueStatement);
	}

	public boolean visit(DeclareStatement declareStatement) {
		return visit((Statement) declareStatement);
	}

	public boolean visit(DoStatement doStatement) {
		return visit((Statement) doStatement);
	}

	public boolean visit(EchoStatement echoStatement) {
		return visit((Statement) echoStatement);
	}

	public boolean visit(EmptyStatement emptyStatement) {
		return visit((Statement) emptyStatement);
	}

	public boolean visit(ExpressionStatement expressionStatement) {
		return visit((Statement) expressionStatement);
	}

	public boolean visit(FieldAccess fieldAccess) {
		return visit((Dispatch) fieldAccess);
	}

	public boolean visit(FieldsDeclaration fieldsDeclaration) {
		return visit((BodyDeclaration) fieldsDeclaration);
	}

	public boolean visit(ForEachStatement forEachStatement) {
		return visit((Statement) forEachStatement);
	}

	public boolean visit(FormalParameter formalParameter) {
		return visit((ASTNode) formalParameter);
	}

	public boolean visit(ForStatement forStatement) {
		return visit((Statement) forStatement);
	}

	public boolean visit(FunctionDeclaration functionDeclaration) {
		return visit((Statement) functionDeclaration);
	}

	public boolean visit(FunctionInvocation functionInvocation) {
		return visit((VariableBase) functionInvocation);
	}

	public boolean visit(FunctionName functionName) {
		return visit((ASTNode) functionName);
	}

	public boolean visit(GlobalStatement globalStatement) {
		return visit((Statement) globalStatement);
	}

	public boolean visit(Identifier identifier) {
		return visit((Expression) identifier);
	}

	public boolean visit(IfStatement ifStatement) {
		return visit((Statement) ifStatement);
	}

	public boolean visit(IgnoreError ignoreError) {
		return visit((Expression) ignoreError);
	}

	public boolean visit(Include include) {
		return visit((Expression) include);
	}

	public boolean visit(InfixExpression infixExpression) {
		return visit((Expression) infixExpression);
	}

	public boolean visit(InLineHtml inLineHtml) {
		return visit((Statement) inLineHtml);
	}

	public boolean visit(InstanceOfExpression instanceOfExpression) {
		return visit((Expression) instanceOfExpression);
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		return visit((TypeDeclaration) interfaceDeclaration);
	}

	public boolean visit(ListVariable listVariable) {
		return visit((VariableBase) listVariable);
	}

	public boolean visit(MethodDeclaration methodDeclaration) {
		return visit((BodyDeclaration) methodDeclaration);
	}

	public boolean visit(MethodInvocation methodInvocation) {
		return visit((Dispatch) methodInvocation);
	}

	public boolean visit(ParenthesisExpression parenthesisExpression) {
		return visit((Expression) parenthesisExpression);

	}

	public boolean visit(PostfixExpression postfixExpression) {
		return visit((Expression) postfixExpression);
	}

	public boolean visit(PrefixExpression prefixExpression) {
		return visit((Expression) prefixExpression);
	}

	public boolean visit(Program program) {
		return visit((ASTNode) program);
	}

	public boolean visit(Quote quote) {
		return visit((Expression) quote);
	}

	public boolean visit(Reference reference) {
		return visit((Expression) reference);
	}

	public boolean visit(ReflectionVariable reflectionVariable) {
		return visit((Variable) reflectionVariable);
	}

	public boolean visit(ReturnStatement returnStatement) {
		return visit((Statement) returnStatement);
	}

	public boolean visit(Scalar scalar) {
		return visit((Expression) scalar);
	}
	
	public boolean visit(SingleFieldDeclaration singleFieldDeclaration) {
		return visit((ASTNode) singleFieldDeclaration);
	}	

	public boolean visit(StaticConstantAccess classConstantAccess) {
		return visit((StaticDispatch) classConstantAccess);
	}

	public boolean visit(StaticFieldAccess staticFieldAccess) {
		return visit((StaticDispatch) staticFieldAccess);
	}

	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		return visit((StaticDispatch) staticMethodInvocation);
	}

	public boolean visit(StaticStatement staticStatement) {
		return visit((Statement) staticStatement);
	}

	public boolean visit(SwitchCase switchCase) {
		return visit((Statement) switchCase);
	}

	public boolean visit(SwitchStatement switchStatement) {
		return visit((Statement) switchStatement);
	}

	public boolean visit(ThrowStatement throwStatement) {
		return visit((Statement) throwStatement);
	}

	public boolean visit(TryStatement tryStatement) {
		return visit((Statement) tryStatement);
	}

	public boolean visit(UnaryOperation unaryOperation) {
		return visit((Expression) unaryOperation);
	}

	public boolean visit(Variable variable) {
		return visit((VariableBase) variable);
	}

	public boolean visit(WhileStatement whileStatement) {
		return visit((Statement) whileStatement);
	}

}
