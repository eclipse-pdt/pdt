package org.eclipse.php.internal.core.compiler.ast.visitor;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
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

	public boolean endvisit(ClassConstantDeclaration s) throws Exception {
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

	public boolean endvisit(CommentsStatement s) throws Exception {
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

	public boolean endvisit(Program s) throws Exception {
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

	public boolean visit(ClassConstantDeclaration s) throws Exception {
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

	public boolean visit(CommentsStatement s) throws Exception {
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

	public boolean visit(Program s) throws Exception {
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

	public boolean endvisit(ASTNode s) throws Exception {
		if (s.getClass().equals(ArrayCreation.class)) {
			return endvisit((ArrayCreation) s);
		}
		if (s.getClass().equals(ArrayElement.class)) {
			return endvisit((ArrayElement) s);
		}
		if (s.getClass().equals(ArrayVariableReference.class)) {
			return endvisit((ArrayVariableReference) s);
		}
		if (s.getClass().equals(Assignment.class)) {
			return endvisit((Assignment) s);
		}
		if (s.getClass().equals(ASTError.class)) {
			return endvisit((ASTError) s);
		}
		if (s.getClass().equals(BackTickExpression.class)) {
			return endvisit((BackTickExpression) s);
		}
		if (s.getClass().equals(BreakStatement.class)) {
			return endvisit((BreakStatement) s);
		}
		if (s.getClass().equals(CastExpression.class)) {
			return endvisit((CastExpression) s);
		}
		if (s.getClass().equals(CatchClause.class)) {
			return endvisit((CatchClause) s);
		}
		if (s.getClass().equals(ClassConstantDeclaration.class)) {
			return endvisit((ClassConstantDeclaration) s);
		}
		if (s.getClass().equals(ClassDeclaration.class)) {
			return endvisit((ClassDeclaration) s);
		}
		if (s.getClass().equals(ClassInstanceCreation.class)) {
			return endvisit((ClassInstanceCreation) s);
		}
		if (s.getClass().equals(CloneExpression.class)) {
			return endvisit((CloneExpression) s);
		}
		if (s.getClass().equals(Comment.class)) {
			return endvisit((Comment) s);
		}
		if (s.getClass().equals(CommentsStatement.class)) {
			return endvisit((CommentsStatement) s);
		}
		if (s.getClass().equals(ConditionalExpression.class)) {
			return endvisit((ConditionalExpression) s);
		}
		if (s.getClass().equals(ConstantReference.class)) {
			return endvisit((ConstantReference) s);
		}
		if (s.getClass().equals(ContinueStatement.class)) {
			return endvisit((ContinueStatement) s);
		}
		if (s.getClass().equals(DeclareStatement.class)) {
			return endvisit((DeclareStatement) s);
		}
		if (s.getClass().equals(FieldAccess.class)) {
			return endvisit((FieldAccess) s);
		}
		if (s.getClass().equals(StaticFieldAccess.class)) {
			return endvisit((StaticFieldAccess) s);
		}
		if (s.getClass().equals(Dispatch.class)) {
			return endvisit((Dispatch) s);
		}
		if (s.getClass().equals(DoStatement.class)) {
			return endvisit((DoStatement) s);
		}
		if (s.getClass().equals(EchoStatement.class)) {
			return endvisit((EchoStatement) s);
		}
		if (s.getClass().equals(EmptyStatement.class)) {
			return endvisit((EmptyStatement) s);
		}
		if (s.getClass().equals(ExpressionStatement.class)) {
			return endvisit((ExpressionStatement) s);
		}
		if (s.getClass().equals(ForEachStatement.class)) {
			return endvisit((ForEachStatement) s);
		}
		if (s.getClass().equals(FormalParameter.class)) {
			return endvisit((FormalParameter) s);
		}
		if (s.getClass().equals(FormalParameterByReference.class)) {
			return endvisit((FormalParameterByReference) s);
		}
		if (s.getClass().equals(ForStatement.class)) {
			return endvisit((ForStatement) s);
		}
		if (s.getClass().equals(GlobalStatement.class)) {
			return endvisit((GlobalStatement) s);
		}
		if (s.getClass().equals(IfStatement.class)) {
			return endvisit((IfStatement) s);
		}
		if (s.getClass().equals(IgnoreError.class)) {
			return endvisit((IgnoreError) s);
		}
		if (s.getClass().equals(Include.class)) {
			return endvisit((Include) s);
		}
		if (s.getClass().equals(InfixExpression.class)) {
			return endvisit((InfixExpression) s);
		}
		if (s.getClass().equals(InstanceOfExpression.class)) {
			return endvisit((InstanceOfExpression) s);
		}
		if (s.getClass().equals(InterfaceDeclaration.class)) {
			return endvisit((InterfaceDeclaration) s);
		}
		if (s.getClass().equals(ListVariable.class)) {
			return endvisit((ListVariable) s);
		}
		if (s.getClass().equals(PHPCallArgumentsList.class)) {
			return endvisit((PHPCallArgumentsList) s);
		}
		if (s.getClass().equals(PHPCallExpression.class)) {
			return endvisit((PHPCallExpression) s);
		}
		if (s.getClass().equals(PHPFieldDeclaration.class)) {
			return endvisit((PHPFieldDeclaration) s);
		}
		if (s.getClass().equals(PHPDocBlock.class)) {
			return endvisit((PHPDocBlock) s);
		}
		if (s.getClass().equals(PHPDocTag.class)) {
			return endvisit((PHPDocTag) s);
		}
		if (s.getClass().equals(PHPMethodDeclaration.class)) {
			return endvisit((PHPMethodDeclaration) s);
		}
		if (s.getClass().equals(PostfixExpression.class)) {
			return endvisit((PostfixExpression) s);
		}
		if (s.getClass().equals(PrefixExpression.class)) {
			return endvisit((PrefixExpression) s);
		}
		if (s.getClass().equals(Program.class)) {
			return endvisit((Program) s);
		}
		if (s.getClass().equals(Quote.class)) {
			return endvisit((Quote) s);
		}
		if (s.getClass().equals(ReferenceExpression.class)) {
			return endvisit((ReferenceExpression) s);
		}
		if (s.getClass().equals(ReflectionArrayVariableReference.class)) {
			return endvisit((ReflectionArrayVariableReference) s);
		}
		if (s.getClass().equals(ReflectionCallExpression.class)) {
			return endvisit((ReflectionCallExpression) s);
		}
		if (s.getClass().equals(ReflectionStaticMethodInvocation.class)) {
			return endvisit((ReflectionStaticMethodInvocation) s);
		}
		if (s.getClass().equals(ReflectionVariableReference.class)) {
			return endvisit((ReflectionVariableReference) s);
		}
		if (s.getClass().equals(ReturnStatement.class)) {
			return endvisit((ReturnStatement) s);
		}
		if (s.getClass().equals(Scalar.class)) {
			return endvisit((Scalar) s);
		}
		if (s.getClass().equals(StaticConstantAccess.class)) {
			return endvisit((StaticConstantAccess) s);
		}
		if (s.getClass().equals(StaticDispatch.class)) {
			return endvisit((StaticDispatch) s);
		}
		if (s.getClass().equals(StaticMethodInvocation.class)) {
			return endvisit((StaticMethodInvocation) s);
		}
		if (s.getClass().equals(StaticStatement.class)) {
			return endvisit((StaticStatement) s);
		}
		if (s.getClass().equals(SwitchCase.class)) {
			return endvisit((SwitchCase) s);
		}
		if (s.getClass().equals(SwitchStatement.class)) {
			return endvisit((SwitchStatement) s);
		}
		if (s.getClass().equals(ThrowStatement.class)) {
			return endvisit((ThrowStatement) s);
		}
		if (s.getClass().equals(TryStatement.class)) {
			return endvisit((TryStatement) s);
		}
		if (s.getClass().equals(TypeReference.class)) {
			return endvisit((TypeReference) s);
		}
		if (s.getClass().equals(UnaryOperation.class)) {
			return endvisit((UnaryOperation) s);
		}
		if (s.getClass().equals(VariableReference.class)) {
			return endvisit((VariableReference) s);
		}
		if (s.getClass().equals(WhileStatement.class)) {
			return endvisit((WhileStatement) s);
		}
		if (s.getClass().equals(SimpleReference.class)) {
			return endvisit((SimpleReference) s);
		}
		return true;
	}

	public boolean endvisit(Expression s) throws Exception {
		return endvisit((ASTNode)s);
	}

	public boolean endvisit(MethodDeclaration s) throws Exception {
		if (s instanceof PHPMethodDeclaration) {
			return endvisit((PHPMethodDeclaration) s);
		}
		return true;
	}

	public boolean endvisit(ModuleDeclaration s) throws Exception {
		if (s instanceof Program) {
			return endvisit((Program) s);
		}
		return true;
	}

	public boolean endvisit(Statement s) throws Exception {
		return endvisit((ASTNode)s);
	}

	public boolean endvisit(TypeDeclaration s) throws Exception {
		if (s instanceof ClassDeclaration) {
			return endvisit((ClassDeclaration) s);
		}
		if (s instanceof InterfaceDeclaration) {
			return endvisit((InterfaceDeclaration) s);
		}
		return true;
	}

	public boolean visit(ASTNode s) throws Exception {
		if (s.getClass().equals(ArrayCreation.class)) {
			return visit((ArrayCreation) s);
		}
		if (s.getClass().equals(ArrayElement.class)) {
			return visit((ArrayElement) s);
		}
		if (s.getClass().equals(ArrayVariableReference.class)) {
			return visit((ArrayVariableReference) s);
		}
		if (s.getClass().equals(Assignment.class)) {
			return visit((Assignment) s);
		}
		if (s.getClass().equals(ASTError.class)) {
			return visit((ASTError) s);
		}
		if (s.getClass().equals(BackTickExpression.class)) {
			return visit((BackTickExpression) s);
		}
		if (s.getClass().equals(BreakStatement.class)) {
			return visit((BreakStatement) s);
		}
		if (s.getClass().equals(CastExpression.class)) {
			return visit((CastExpression) s);
		}
		if (s.getClass().equals(CatchClause.class)) {
			return visit((CatchClause) s);
		}
		if (s.getClass().equals(ClassConstantDeclaration.class)) {
			return visit((ClassConstantDeclaration) s);
		}
		if (s.getClass().equals(ClassDeclaration.class)) {
			return visit((ClassDeclaration) s);
		}
		if (s.getClass().equals(ClassInstanceCreation.class)) {
			return visit((ClassInstanceCreation) s);
		}
		if (s.getClass().equals(CloneExpression.class)) {
			return visit((CloneExpression) s);
		}
		if (s.getClass().equals(Comment.class)) {
			return visit((Comment) s);
		}
		if (s.getClass().equals(CommentsStatement.class)) {
			return visit((CommentsStatement) s);
		}
		if (s.getClass().equals(ConditionalExpression.class)) {
			return visit((ConditionalExpression) s);
		}
		if (s.getClass().equals(ConstantReference.class)) {
			return visit((ConstantReference) s);
		}
		if (s.getClass().equals(ContinueStatement.class)) {
			return visit((ContinueStatement) s);
		}
		if (s.getClass().equals(DeclareStatement.class)) {
			return visit((DeclareStatement) s);
		}
		if (s.getClass().equals(StaticFieldAccess.class)) {
			return visit((StaticFieldAccess) s);
		}
		if (s.getClass().equals(FieldAccess.class)) {
			return visit((FieldAccess) s);
		}
		if (s.getClass().equals(Dispatch.class)) {
			return visit((Dispatch) s);
		}
		if (s.getClass().equals(DoStatement.class)) {
			return visit((DoStatement) s);
		}
		if (s.getClass().equals(EchoStatement.class)) {
			return visit((EchoStatement) s);
		}
		if (s.getClass().equals(EmptyStatement.class)) {
			return visit((EmptyStatement) s);
		}
		if (s.getClass().equals(ExpressionStatement.class)) {
			return visit((ExpressionStatement) s);
		}
		if (s.getClass().equals(ForEachStatement.class)) {
			return visit((ForEachStatement) s);
		}
		if (s.getClass().equals(FormalParameter.class)) {
			return visit((FormalParameter) s);
		}
		if (s.getClass().equals(FormalParameterByReference.class)) {
			return visit((FormalParameterByReference) s);
		}
		if (s.getClass().equals(ForStatement.class)) {
			return visit((ForStatement) s);
		}
		if (s.getClass().equals(GlobalStatement.class)) {
			return visit((GlobalStatement) s);
		}
		if (s.getClass().equals(IfStatement.class)) {
			return visit((IfStatement) s);
		}
		if (s.getClass().equals(IgnoreError.class)) {
			return visit((IgnoreError) s);
		}
		if (s.getClass().equals(Include.class)) {
			return visit((Include) s);
		}
		if (s.getClass().equals(InfixExpression.class)) {
			return visit((InfixExpression) s);
		}
		if (s.getClass().equals(InstanceOfExpression.class)) {
			return visit((InstanceOfExpression) s);
		}
		if (s.getClass().equals(InterfaceDeclaration.class)) {
			return visit((InterfaceDeclaration) s);
		}
		if (s.getClass().equals(ListVariable.class)) {
			return visit((ListVariable) s);
		}
		if (s.getClass().equals(PHPCallArgumentsList.class)) {
			return visit((PHPCallArgumentsList) s);
		}
		if (s.getClass().equals(PHPCallExpression.class)) {
			return visit((PHPCallExpression) s);
		}
		if (s.getClass().equals(PHPFieldDeclaration.class)) {
			return visit((PHPFieldDeclaration) s);
		}
		if (s.getClass().equals(PHPDocBlock.class)) {
			return visit((PHPDocBlock) s);
		}
		if (s.getClass().equals(PHPDocTag.class)) {
			return visit((PHPDocTag) s);
		}
		if (s.getClass().equals(PHPMethodDeclaration.class)) {
			return visit((PHPMethodDeclaration) s);
		}
		if (s.getClass().equals(PostfixExpression.class)) {
			return visit((PostfixExpression) s);
		}
		if (s.getClass().equals(PrefixExpression.class)) {
			return visit((PrefixExpression) s);
		}
		if (s.getClass().equals(Program.class)) {
			return visit((Program) s);
		}
		if (s.getClass().equals(Quote.class)) {
			return visit((Quote) s);
		}
		if (s.getClass().equals(ReferenceExpression.class)) {
			return visit((ReferenceExpression) s);
		}
		if (s.getClass().equals(ReflectionArrayVariableReference.class)) {
			return visit((ReflectionArrayVariableReference) s);
		}
		if (s.getClass().equals(ReflectionCallExpression.class)) {
			return visit((ReflectionCallExpression) s);
		}
		if (s.getClass().equals(ReflectionStaticMethodInvocation.class)) {
			return visit((ReflectionStaticMethodInvocation) s);
		}
		if (s.getClass().equals(ReflectionVariableReference.class)) {
			return visit((ReflectionVariableReference) s);
		}
		if (s.getClass().equals(ReturnStatement.class)) {
			return visit((ReturnStatement) s);
		}
		if (s.getClass().equals(Scalar.class)) {
			return visit((Scalar) s);
		}
		if (s.getClass().equals(StaticConstantAccess.class)) {
			return visit((StaticConstantAccess) s);
		}
		if (s.getClass().equals(StaticDispatch.class)) {
			return visit((StaticDispatch) s);
		}
		if (s.getClass().equals(StaticMethodInvocation.class)) {
			return visit((StaticMethodInvocation) s);
		}
		if (s.getClass().equals(StaticStatement.class)) {
			return visit((StaticStatement) s);
		}
		if (s.getClass().equals(SwitchCase.class)) {
			return visit((SwitchCase) s);
		}
		if (s.getClass().equals(SwitchStatement.class)) {
			return visit((SwitchStatement) s);
		}
		if (s.getClass().equals(ThrowStatement.class)) {
			return visit((ThrowStatement) s);
		}
		if (s.getClass().equals(TryStatement.class)) {
			return visit((TryStatement) s);
		}
		if (s.getClass().equals(TypeReference.class)) {
			return visit((TypeReference) s);
		}
		if (s.getClass().equals(UnaryOperation.class)) {
			return visit((UnaryOperation) s);
		}
		if (s.getClass().equals(VariableReference.class)) {
			return visit((VariableReference) s);
		}
		if (s.getClass().equals(WhileStatement.class)) {
			return visit((WhileStatement) s);
		}
		if (s.getClass().equals(SimpleReference.class)) {
			return visit((SimpleReference) s);
		}
		return true;
	}

	public boolean visit(Expression s) throws Exception {
		return visit((ASTNode)s);
	}

	public boolean visit(MethodDeclaration s) throws Exception {
		if (s instanceof PHPMethodDeclaration) {
			return visit((PHPMethodDeclaration) s);
		}
		return true;
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		if (s instanceof Program) {
			return visit((Program) s);
		}
		return true;
	}

	public boolean visit(Statement s) throws Exception {
		return visit((ASTNode)s);
	}

	public boolean visit(TypeDeclaration s) throws Exception {
		if (s instanceof ClassDeclaration) {
			return visit((ClassDeclaration) s);
		}
		if (s instanceof InterfaceDeclaration) {
			return visit((InterfaceDeclaration) s);
		}
		return true;
	}
}
