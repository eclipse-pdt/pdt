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
package org.eclipse.php.internal.core.compiler.ast.visitor;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

public abstract class PHPASTVisitor extends ASTVisitor {

	public boolean endvisit(ArrayCreation s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ArrayElement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ArrayVariableReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(Assignment s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ASTError s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(BackTickExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(BreakStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(CastExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(CatchClause s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ConstantDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ClassDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ClassInstanceCreation s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(CloneExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(Comment s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ConditionalExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ContinueStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ConstantReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(DeclareStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(Dispatch s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(DoStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(EchoStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(EmptyStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ExpressionStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(FieldAccess s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ForEachStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(FormalParameter s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(FormalParameterByReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ForStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(GlobalStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(IfStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(IgnoreError s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(Include s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(InfixExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(InstanceOfExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(InterfaceDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ListVariable s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PHPCallArgumentsList s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PHPCallExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PHPDocBlock s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PHPDocTag s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PHPFieldDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PHPMethodDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PostfixExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(PrefixExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(Quote s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ReferenceExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ReflectionArrayVariableReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ReflectionCallExpression s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ReflectionStaticMethodInvocation s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ReflectionVariableReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ReturnStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(Scalar s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(SimpleReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(StaticConstantAccess s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(StaticDispatch s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(StaticFieldAccess s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(StaticMethodInvocation s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(StaticStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(SwitchCase s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(SwitchStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(ThrowStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(TryStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(TypeReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(UnaryOperation s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(VariableReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(WhileStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(NamespaceDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(UseStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(UsePart s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(NamespaceReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(FullyQualifiedReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(GotoLabel s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean endvisit(GotoStatement s) throws Exception {
		endvisitGeneral(s);
		return false;
	}
	
	public boolean endvisit(LambdaFunctionDeclaration s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean visit(ArrayCreation s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ArrayElement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ArrayVariableReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(Assignment s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ASTError s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(BackTickExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(BreakStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(CastExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(CatchClause s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ConstantDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ClassDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ClassInstanceCreation s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(CloneExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(Comment s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ConditionalExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ConstantReference s) throws Exception {
		endvisitGeneral(s);
		return false;
	}

	public boolean visit(ContinueStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(DeclareStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(Dispatch s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(DoStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(EchoStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(EmptyStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ExpressionStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(FieldAccess s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ForEachStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(FormalParameter s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(FormalParameterByReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ForStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(GlobalStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(IfStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(IgnoreError s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(Include s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(InfixExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(InstanceOfExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(InterfaceDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ListVariable s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PHPCallArgumentsList s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PHPCallExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PHPDocBlock s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PHPDocTag s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PHPFieldDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PHPMethodDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PostfixExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(PrefixExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(Quote s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ReferenceExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ReflectionArrayVariableReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ReflectionCallExpression s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ReflectionStaticMethodInvocation s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ReflectionVariableReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ReturnStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(Scalar s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(SimpleReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(StaticConstantAccess s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(StaticDispatch s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(StaticFieldAccess s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(StaticMethodInvocation s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(StaticStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(SwitchCase s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(SwitchStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(ThrowStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(TryStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(TypeReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(UnaryOperation s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(VariableReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(WhileStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(UseStatement s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(UsePart s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(NamespaceDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(NamespaceReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(FullyQualifiedReference s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(GotoLabel s) throws Exception {
		return visitGeneral(s);
	}

	public boolean visit(GotoStatement s) throws Exception {
		return visitGeneral(s);
	}
	
	public boolean visit(LambdaFunctionDeclaration s) throws Exception {
		return visitGeneral(s);
	}

	public boolean endvisit(ASTNode s) throws Exception {
		Class<? extends ASTNode> nodeClass = s.getClass();
		if (nodeClass.equals(ArrayCreation.class)) {
			return endvisit((ArrayCreation) s);
		}
		if (nodeClass.equals(ArrayElement.class)) {
			return endvisit((ArrayElement) s);
		}
		if (nodeClass.equals(ArrayVariableReference.class)) {
			return endvisit((ArrayVariableReference) s);
		}
		if (nodeClass.equals(Assignment.class)) {
			return endvisit((Assignment) s);
		}
		if (nodeClass.equals(ASTError.class)) {
			return endvisit((ASTError) s);
		}
		if (nodeClass.equals(BackTickExpression.class)) {
			return endvisit((BackTickExpression) s);
		}
		if (nodeClass.equals(BreakStatement.class)) {
			return endvisit((BreakStatement) s);
		}
		if (nodeClass.equals(CastExpression.class)) {
			return endvisit((CastExpression) s);
		}
		if (nodeClass.equals(CatchClause.class)) {
			return endvisit((CatchClause) s);
		}
		if (nodeClass.equals(ConstantDeclaration.class)) {
			return endvisit((ConstantDeclaration) s);
		}
		if (nodeClass.equals(ClassDeclaration.class)) {
			return endvisit((ClassDeclaration) s);
		}
		if (nodeClass.equals(ClassInstanceCreation.class)) {
			return endvisit((ClassInstanceCreation) s);
		}
		if (nodeClass.equals(CloneExpression.class)) {
			return endvisit((CloneExpression) s);
		}
		if (nodeClass.equals(Comment.class)) {
			return endvisit((Comment) s);
		}
		if (nodeClass.equals(ConditionalExpression.class)) {
			return endvisit((ConditionalExpression) s);
		}
		if (nodeClass.equals(ConstantReference.class)) {
			return endvisit((ConstantReference) s);
		}
		if (nodeClass.equals(ContinueStatement.class)) {
			return endvisit((ContinueStatement) s);
		}
		if (nodeClass.equals(DeclareStatement.class)) {
			return endvisit((DeclareStatement) s);
		}
		if (nodeClass.equals(FieldAccess.class)) {
			return endvisit((FieldAccess) s);
		}
		if (nodeClass.equals(StaticFieldAccess.class)) {
			return endvisit((StaticFieldAccess) s);
		}
		if (nodeClass.equals(Dispatch.class)) {
			return endvisit((Dispatch) s);
		}
		if (nodeClass.equals(DoStatement.class)) {
			return endvisit((DoStatement) s);
		}
		if (nodeClass.equals(EchoStatement.class)) {
			return endvisit((EchoStatement) s);
		}
		if (nodeClass.equals(EmptyStatement.class)) {
			return endvisit((EmptyStatement) s);
		}
		if (nodeClass.equals(ExpressionStatement.class)) {
			return endvisit((ExpressionStatement) s);
		}
		if (nodeClass.equals(ForEachStatement.class)) {
			return endvisit((ForEachStatement) s);
		}
		if (nodeClass.equals(FormalParameter.class)) {
			return endvisit((FormalParameter) s);
		}
		if (nodeClass.equals(FormalParameterByReference.class)) {
			return endvisit((FormalParameterByReference) s);
		}
		if (nodeClass.equals(ForStatement.class)) {
			return endvisit((ForStatement) s);
		}
		if (nodeClass.equals(GlobalStatement.class)) {
			return endvisit((GlobalStatement) s);
		}
		if (nodeClass.equals(IfStatement.class)) {
			return endvisit((IfStatement) s);
		}
		if (nodeClass.equals(IgnoreError.class)) {
			return endvisit((IgnoreError) s);
		}
		if (nodeClass.equals(Include.class)) {
			return endvisit((Include) s);
		}
		if (nodeClass.equals(InfixExpression.class)) {
			return endvisit((InfixExpression) s);
		}
		if (nodeClass.equals(InstanceOfExpression.class)) {
			return endvisit((InstanceOfExpression) s);
		}
		if (nodeClass.equals(InterfaceDeclaration.class)) {
			return endvisit((InterfaceDeclaration) s);
		}
		if (nodeClass.equals(ListVariable.class)) {
			return endvisit((ListVariable) s);
		}
		if (nodeClass.equals(PHPCallArgumentsList.class)) {
			return endvisit((PHPCallArgumentsList) s);
		}
		if (nodeClass.equals(PHPCallExpression.class)) {
			return endvisit((PHPCallExpression) s);
		}
		if (nodeClass.equals(PHPFieldDeclaration.class)) {
			return endvisit((PHPFieldDeclaration) s);
		}
		if (nodeClass.equals(PHPDocBlock.class)) {
			return endvisit((PHPDocBlock) s);
		}
		if (nodeClass.equals(PHPDocTag.class)) {
			return endvisit((PHPDocTag) s);
		}
		if (nodeClass.equals(PHPMethodDeclaration.class)) {
			return endvisit((PHPMethodDeclaration) s);
		}
		if (nodeClass.equals(PostfixExpression.class)) {
			return endvisit((PostfixExpression) s);
		}
		if (nodeClass.equals(PrefixExpression.class)) {
			return endvisit((PrefixExpression) s);
		}
		if (nodeClass.equals(Quote.class)) {
			return endvisit((Quote) s);
		}
		if (nodeClass.equals(ReferenceExpression.class)) {
			return endvisit((ReferenceExpression) s);
		}
		if (nodeClass.equals(ReflectionArrayVariableReference.class)) {
			return endvisit((ReflectionArrayVariableReference) s);
		}
		if (nodeClass.equals(ReflectionCallExpression.class)) {
			return endvisit((ReflectionCallExpression) s);
		}
		if (nodeClass.equals(ReflectionStaticMethodInvocation.class)) {
			return endvisit((ReflectionStaticMethodInvocation) s);
		}
		if (nodeClass.equals(ReflectionVariableReference.class)) {
			return endvisit((ReflectionVariableReference) s);
		}
		if (nodeClass.equals(ReturnStatement.class)) {
			return endvisit((ReturnStatement) s);
		}
		if (nodeClass.equals(Scalar.class)) {
			return endvisit((Scalar) s);
		}
		if (nodeClass.equals(StaticConstantAccess.class)) {
			return endvisit((StaticConstantAccess) s);
		}
		if (nodeClass.equals(StaticDispatch.class)) {
			return endvisit((StaticDispatch) s);
		}
		if (nodeClass.equals(StaticMethodInvocation.class)) {
			return endvisit((StaticMethodInvocation) s);
		}
		if (nodeClass.equals(StaticStatement.class)) {
			return endvisit((StaticStatement) s);
		}
		if (nodeClass.equals(SwitchCase.class)) {
			return endvisit((SwitchCase) s);
		}
		if (nodeClass.equals(SwitchStatement.class)) {
			return endvisit((SwitchStatement) s);
		}
		if (nodeClass.equals(ThrowStatement.class)) {
			return endvisit((ThrowStatement) s);
		}
		if (nodeClass.equals(TryStatement.class)) {
			return endvisit((TryStatement) s);
		}
		if (nodeClass.equals(TypeReference.class)) {
			return endvisit((TypeReference) s);
		}
		if (nodeClass.equals(UnaryOperation.class)) {
			return endvisit((UnaryOperation) s);
		}
		if (nodeClass.equals(VariableReference.class)) {
			return endvisit((VariableReference) s);
		}
		if (nodeClass.equals(WhileStatement.class)) {
			return endvisit((WhileStatement) s);
		}
		if (nodeClass.equals(SimpleReference.class)) {
			return endvisit((SimpleReference) s);
		}
		if (nodeClass.equals(UseStatement.class)) {
			return endvisit((UseStatement) s);
		}
		if (nodeClass.equals(UsePart.class)) {
			return endvisit((UsePart) s);
		}
		if (nodeClass.equals(NamespaceReference.class)) {
			return endvisit((NamespaceReference) s);
		}
		if (nodeClass.equals(FullyQualifiedReference.class)) {
			return endvisit((FullyQualifiedReference) s);
		}
		if (nodeClass.equals(GotoLabel.class)) {
			return endvisit((GotoLabel) s);
		}
		if (nodeClass.equals(GotoStatement.class)) {
			return endvisit((GotoStatement) s);
		}
		if (nodeClass.equals(LambdaFunctionDeclaration.class)) {
			return endvisit((LambdaFunctionDeclaration) s);
		}
		return true;
	}

	public boolean endvisit(Expression s) throws Exception {
		return endvisit((ASTNode) s);
	}

	public boolean endvisit(MethodDeclaration s) throws Exception {
		if (s instanceof PHPMethodDeclaration) {
			return endvisit((PHPMethodDeclaration) s);
		}
		return true;
	}

	public boolean endvisit(Statement s) throws Exception {
		return endvisit((ASTNode) s);
	}

	public boolean endvisit(TypeDeclaration s) throws Exception {
		if (s instanceof ClassDeclaration) {
			return endvisit((ClassDeclaration) s);
		}
		if (s instanceof InterfaceDeclaration) {
			return endvisit((InterfaceDeclaration) s);
		}
		if (s instanceof NamespaceDeclaration) {
			return endvisit((NamespaceDeclaration) s);
		}
		return true;
	}

	public boolean visit(ASTNode s) throws Exception {
		Class<? extends ASTNode> nodeClass = s.getClass();
		if (nodeClass.equals(ArrayCreation.class)) {
			return visit((ArrayCreation) s);
		}
		if (nodeClass.equals(ArrayElement.class)) {
			return visit((ArrayElement) s);
		}
		if (nodeClass.equals(ArrayVariableReference.class)) {
			return visit((ArrayVariableReference) s);
		}
		if (nodeClass.equals(Assignment.class)) {
			return visit((Assignment) s);
		}
		if (nodeClass.equals(ASTError.class)) {
			return visit((ASTError) s);
		}
		if (nodeClass.equals(BackTickExpression.class)) {
			return visit((BackTickExpression) s);
		}
		if (nodeClass.equals(BreakStatement.class)) {
			return visit((BreakStatement) s);
		}
		if (nodeClass.equals(CastExpression.class)) {
			return visit((CastExpression) s);
		}
		if (nodeClass.equals(CatchClause.class)) {
			return visit((CatchClause) s);
		}
		if (nodeClass.equals(ConstantDeclaration.class)) {
			return visit((ConstantDeclaration) s);
		}
		if (nodeClass.equals(ClassDeclaration.class)) {
			return visit((ClassDeclaration) s);
		}
		if (nodeClass.equals(ClassInstanceCreation.class)) {
			return visit((ClassInstanceCreation) s);
		}
		if (nodeClass.equals(CloneExpression.class)) {
			return visit((CloneExpression) s);
		}
		if (nodeClass.equals(Comment.class)) {
			return visit((Comment) s);
		}
		if (nodeClass.equals(ConditionalExpression.class)) {
			return visit((ConditionalExpression) s);
		}
		if (nodeClass.equals(ConstantReference.class)) {
			return visit((ConstantReference) s);
		}
		if (nodeClass.equals(ContinueStatement.class)) {
			return visit((ContinueStatement) s);
		}
		if (nodeClass.equals(DeclareStatement.class)) {
			return visit((DeclareStatement) s);
		}
		if (nodeClass.equals(StaticFieldAccess.class)) {
			return visit((StaticFieldAccess) s);
		}
		if (nodeClass.equals(FieldAccess.class)) {
			return visit((FieldAccess) s);
		}
		if (nodeClass.equals(Dispatch.class)) {
			return visit((Dispatch) s);
		}
		if (nodeClass.equals(DoStatement.class)) {
			return visit((DoStatement) s);
		}
		if (nodeClass.equals(EchoStatement.class)) {
			return visit((EchoStatement) s);
		}
		if (nodeClass.equals(EmptyStatement.class)) {
			return visit((EmptyStatement) s);
		}
		if (nodeClass.equals(ExpressionStatement.class)) {
			return visit((ExpressionStatement) s);
		}
		if (nodeClass.equals(ForEachStatement.class)) {
			return visit((ForEachStatement) s);
		}
		if (nodeClass.equals(FormalParameter.class)) {
			return visit((FormalParameter) s);
		}
		if (nodeClass.equals(FormalParameterByReference.class)) {
			return visit((FormalParameterByReference) s);
		}
		if (nodeClass.equals(ForStatement.class)) {
			return visit((ForStatement) s);
		}
		if (nodeClass.equals(GlobalStatement.class)) {
			return visit((GlobalStatement) s);
		}
		if (nodeClass.equals(IfStatement.class)) {
			return visit((IfStatement) s);
		}
		if (nodeClass.equals(IgnoreError.class)) {
			return visit((IgnoreError) s);
		}
		if (nodeClass.equals(Include.class)) {
			return visit((Include) s);
		}
		if (nodeClass.equals(InfixExpression.class)) {
			return visit((InfixExpression) s);
		}
		if (nodeClass.equals(InstanceOfExpression.class)) {
			return visit((InstanceOfExpression) s);
		}
		if (nodeClass.equals(InterfaceDeclaration.class)) {
			return visit((InterfaceDeclaration) s);
		}
		if (nodeClass.equals(ListVariable.class)) {
			return visit((ListVariable) s);
		}
		if (nodeClass.equals(PHPCallArgumentsList.class)) {
			return visit((PHPCallArgumentsList) s);
		}
		if (nodeClass.equals(PHPCallExpression.class)) {
			return visit((PHPCallExpression) s);
		}
		if (nodeClass.equals(PHPFieldDeclaration.class)) {
			return visit((PHPFieldDeclaration) s);
		}
		if (nodeClass.equals(PHPDocBlock.class)) {
			return visit((PHPDocBlock) s);
		}
		if (nodeClass.equals(PHPDocTag.class)) {
			return visit((PHPDocTag) s);
		}
		if (nodeClass.equals(PHPMethodDeclaration.class)) {
			return visit((PHPMethodDeclaration) s);
		}
		if (nodeClass.equals(PostfixExpression.class)) {
			return visit((PostfixExpression) s);
		}
		if (nodeClass.equals(PrefixExpression.class)) {
			return visit((PrefixExpression) s);
		}
		if (nodeClass.equals(Quote.class)) {
			return visit((Quote) s);
		}
		if (nodeClass.equals(ReferenceExpression.class)) {
			return visit((ReferenceExpression) s);
		}
		if (nodeClass.equals(ReflectionArrayVariableReference.class)) {
			return visit((ReflectionArrayVariableReference) s);
		}
		if (nodeClass.equals(ReflectionCallExpression.class)) {
			return visit((ReflectionCallExpression) s);
		}
		if (nodeClass.equals(ReflectionStaticMethodInvocation.class)) {
			return visit((ReflectionStaticMethodInvocation) s);
		}
		if (nodeClass.equals(ReflectionVariableReference.class)) {
			return visit((ReflectionVariableReference) s);
		}
		if (nodeClass.equals(ReturnStatement.class)) {
			return visit((ReturnStatement) s);
		}
		if (nodeClass.equals(Scalar.class)) {
			return visit((Scalar) s);
		}
		if (nodeClass.equals(StaticConstantAccess.class)) {
			return visit((StaticConstantAccess) s);
		}
		if (nodeClass.equals(StaticDispatch.class)) {
			return visit((StaticDispatch) s);
		}
		if (nodeClass.equals(StaticMethodInvocation.class)) {
			return visit((StaticMethodInvocation) s);
		}
		if (nodeClass.equals(StaticStatement.class)) {
			return visit((StaticStatement) s);
		}
		if (nodeClass.equals(SwitchCase.class)) {
			return visit((SwitchCase) s);
		}
		if (nodeClass.equals(SwitchStatement.class)) {
			return visit((SwitchStatement) s);
		}
		if (nodeClass.equals(ThrowStatement.class)) {
			return visit((ThrowStatement) s);
		}
		if (nodeClass.equals(TryStatement.class)) {
			return visit((TryStatement) s);
		}
		if (nodeClass.equals(TypeReference.class)) {
			return visit((TypeReference) s);
		}
		if (nodeClass.equals(UnaryOperation.class)) {
			return visit((UnaryOperation) s);
		}
		if (nodeClass.equals(VariableReference.class)) {
			return visit((VariableReference) s);
		}
		if (nodeClass.equals(WhileStatement.class)) {
			return visit((WhileStatement) s);
		}
		if (nodeClass.equals(SimpleReference.class)) {
			return visit((SimpleReference) s);
		}
		if (nodeClass.equals(UseStatement.class)) {
			return visit((UseStatement) s);
		}
		if (nodeClass.equals(UsePart.class)) {
			return visit((UsePart) s);
		}
		if (nodeClass.equals(NamespaceReference.class)) {
			return visit((NamespaceReference) s);
		}
		if (nodeClass.equals(FullyQualifiedReference.class)) {
			return visit((FullyQualifiedReference) s);
		}
		if (nodeClass.equals(GotoLabel.class)) {
			return visit((GotoLabel) s);
		}
		if (nodeClass.equals(GotoStatement.class)) {
			return visit((GotoStatement) s);
		}
		if (nodeClass.equals(LambdaFunctionDeclaration.class)) {
			return visit((LambdaFunctionDeclaration) s);
		}
		return true;
	}

	public boolean visit(Expression s) throws Exception {
		return visit((ASTNode) s);
	}

	public boolean visit(MethodDeclaration s) throws Exception {
		if (s instanceof PHPMethodDeclaration) {
			return visit((PHPMethodDeclaration) s);
		}
		return true;
	}

	public boolean visit(Statement s) throws Exception {
		return visit((ASTNode) s);
	}

	public boolean visit(TypeDeclaration s) throws Exception {
		if (s instanceof ClassDeclaration) {
			return visit((ClassDeclaration) s);
		}
		if (s instanceof InterfaceDeclaration) {
			return visit((InterfaceDeclaration) s);
		}
		if (s instanceof NamespaceDeclaration) {
			return visit((NamespaceDeclaration) s);
		}
		return true;
	}
}
