/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.rewrite;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.ASTError;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.ast.nodes.Assignment;
import org.eclipse.php.internal.core.ast.nodes.BackTickExpression;
import org.eclipse.php.internal.core.ast.nodes.Block;
import org.eclipse.php.internal.core.ast.nodes.BreakStatement;
import org.eclipse.php.internal.core.ast.nodes.CastExpression;
import org.eclipse.php.internal.core.ast.nodes.CatchClause;
import org.eclipse.php.internal.core.ast.nodes.ClassConstantDeclaration;
import org.eclipse.php.internal.core.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.ast.nodes.ClassInstanceCreation;
import org.eclipse.php.internal.core.ast.nodes.ClassName;
import org.eclipse.php.internal.core.ast.nodes.CloneExpression;
import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.core.ast.nodes.ConditionalExpression;
import org.eclipse.php.internal.core.ast.nodes.ContinueStatement;
import org.eclipse.php.internal.core.ast.nodes.DeclareStatement;
import org.eclipse.php.internal.core.ast.nodes.DoStatement;
import org.eclipse.php.internal.core.ast.nodes.EchoStatement;
import org.eclipse.php.internal.core.ast.nodes.EmptyStatement;
import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.ExpressionStatement;
import org.eclipse.php.internal.core.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.internal.core.ast.nodes.ForEachStatement;
import org.eclipse.php.internal.core.ast.nodes.ForStatement;
import org.eclipse.php.internal.core.ast.nodes.FormalParameter;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.FunctionInvocation;
import org.eclipse.php.internal.core.ast.nodes.FunctionName;
import org.eclipse.php.internal.core.ast.nodes.GlobalStatement;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.IfStatement;
import org.eclipse.php.internal.core.ast.nodes.IgnoreError;
import org.eclipse.php.internal.core.ast.nodes.InLineHtml;
import org.eclipse.php.internal.core.ast.nodes.Include;
import org.eclipse.php.internal.core.ast.nodes.InfixExpression;
import org.eclipse.php.internal.core.ast.nodes.InstanceOfExpression;
import org.eclipse.php.internal.core.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.internal.core.ast.nodes.ListVariable;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodInvocation;
import org.eclipse.php.internal.core.ast.nodes.ParenthesisExpression;
import org.eclipse.php.internal.core.ast.nodes.PostfixExpression;
import org.eclipse.php.internal.core.ast.nodes.PrefixExpression;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Quote;
import org.eclipse.php.internal.core.ast.nodes.Reference;
import org.eclipse.php.internal.core.ast.nodes.ReflectionVariable;
import org.eclipse.php.internal.core.ast.nodes.ReturnStatement;
import org.eclipse.php.internal.core.ast.nodes.Scalar;
import org.eclipse.php.internal.core.ast.nodes.Statement;
import org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticMethodInvocation;
import org.eclipse.php.internal.core.ast.nodes.StaticStatement;
import org.eclipse.php.internal.core.ast.nodes.StructuralPropertyDescriptor;
import org.eclipse.php.internal.core.ast.nodes.SwitchCase;
import org.eclipse.php.internal.core.ast.nodes.SwitchStatement;
import org.eclipse.php.internal.core.ast.nodes.ThrowStatement;
import org.eclipse.php.internal.core.ast.nodes.TryStatement;
import org.eclipse.php.internal.core.ast.nodes.UnaryOperation;
import org.eclipse.php.internal.core.ast.nodes.Variable;
import org.eclipse.php.internal.core.ast.nodes.VariableBase;
import org.eclipse.php.internal.core.ast.nodes.WhileStatement;
import org.eclipse.php.internal.core.ast.nodes.BodyDeclaration.Modifier;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;

public class ASTRewriteFlattener extends AbstractVisitor {
	
	/**
	 * Internal synonynm for deprecated constant AST.JSL2
	 * to alleviate deprecation warnings.
	 * @deprecated
	 */
	/*package*/ static final String PHP4_INTERNAL = PHPVersion.PHP4;
	
	public static String asString(ASTNode node, RewriteEventStore store) {
		ASTRewriteFlattener flattener= new ASTRewriteFlattener(store);
		node.accept(flattener);
		return flattener.getResult();
	}

	protected StringBuffer result;
	private RewriteEventStore store;

	public ASTRewriteFlattener(RewriteEventStore store) {
		this.store= store;
		this.result= new StringBuffer();
	}
	
	/**
	 * Returns the string accumulated in the visit.
	 *
	 * @return the serialized 
	 */
	public String getResult() {
		// convert to a string, but lose any extra space in the string buffer by copying
		return new String(this.result.toString());
	}
	
	/**
	 * Resets this printer so that it can be used again.
	 */
	public void reset() {
		this.result.setLength(0);
	}
	
	/**
	 * Appends the text representation of the given modifier flags, followed by a single space.
	 * 
	 * @param modifiers the modifiers
	 * @param buf The <code>StringBuffer</code> to write the result to.
	 */
	public static void printModifiers(int modifiers, StringBuffer buf) {
		if (Modifier.isPublic(modifiers)) {
			buf.append("public "); //$NON-NLS-1$
		}
		if (Modifier.isProtected(modifiers)) {
			buf.append("protected "); //$NON-NLS-1$
		}
		if (Modifier.isPrivate(modifiers)) {
			buf.append("private "); //$NON-NLS-1$
		}
		if (Modifier.isStatic(modifiers)) {
			buf.append("static "); //$NON-NLS-1$
		}
		if (Modifier.isAbstract(modifiers)) {
			buf.append("abstract "); //$NON-NLS-1$
		}
		if (Modifier.isFinal(modifiers)) {
			buf.append("final "); //$NON-NLS-1$
		}
	}
		
	protected List getChildList(ASTNode parent, StructuralPropertyDescriptor childProperty) {
		return (List) getAttribute(parent, childProperty);
	}
	
	protected ASTNode getChildNode(ASTNode parent, StructuralPropertyDescriptor childProperty) {
		return (ASTNode) getAttribute(parent, childProperty);
	}
	
	protected int getIntAttribute(ASTNode parent, StructuralPropertyDescriptor childProperty) {
		return ((Integer) getAttribute(parent, childProperty)).intValue();
	}
	
	protected boolean getBooleanAttribute(ASTNode parent, StructuralPropertyDescriptor childProperty) {
		return ((Boolean) getAttribute(parent, childProperty)).booleanValue();
	}
	
	protected Object getAttribute(ASTNode parent, StructuralPropertyDescriptor childProperty) {
		return this.store.getNewValue(parent, childProperty);
	}
	
	protected void visitList(ASTNode parent, StructuralPropertyDescriptor childProperty, String separator) {
		List list= getChildList(parent, childProperty);
		for (int i= 0; i < list.size(); i++) {
			if (separator != null && i > 0) {
				this.result.append(separator);
			}
			((ASTNode) list.get(i)).accept(this);
		}
	}
	
	protected void visitList(ASTNode parent, StructuralPropertyDescriptor childProperty, String separator, String lead, String post) {
		List list= getChildList(parent, childProperty);
		if (!list.isEmpty()) {
			this.result.append(lead);
			for (int i= 0; i < list.size(); i++) {
				if (separator != null && i > 0) {
					this.result.append(separator);
				}
				((ASTNode) list.get(i)).accept(this);
			}
			this.result.append(post);
		}
	}

	public boolean visit(ArrayCreation arrayCreation) {
		result.append("array("); //$NON-NLS-1$
		ArrayElement[] elements = arrayCreation.getElements();
		for (int i = 0; i < elements.length; i++) {
			elements[i].accept(this);
			result.append(","); //$NON-NLS-1$
		}
		result.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(ArrayElement arrayElement) {
		if (arrayElement.getKey() != null) {
			arrayElement.getKey().accept(this);
			result.append("=>"); //$NON-NLS-1$
		}
		arrayElement.getValue().accept(this);
		return false;
	}

	public boolean visit(Assignment assignment) {
		assignment.getLeftHandSide().accept(this);
		result.append(Assignment.getOperator(assignment.getOperator()));
		assignment.getRightHandSide().accept(this);
		return false;
	}

	public boolean visit(ASTError astError) {
		// cant flatten, needs source		
		return false;
	}

	public boolean visit(BackTickExpression backTickExpression) {
		result.append("`"); //$NON-NLS-1$
		Expression[] expressions = backTickExpression.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
		result.append("`"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(Block block) {
		if (block.isCurly()) {
			result.append("{\n"); //$NON-NLS-1$
		} else {
			result.append(":\n"); //$NON-NLS-1$
		}

		Statement[] statements = block.getStatements();
		for (int i = 0; i < statements.length; i++) {
			statements[i].accept(this);
		}

		if (block.isCurly()) {
			result.append("\n}\n"); //$NON-NLS-1$
		} else {
			result.append("end;\n"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(BreakStatement breakStatement) {
		result.append("break "); //$NON-NLS-1$
		if (breakStatement.getExpr() != null) {
			breakStatement.getExpr().accept(this);
		}
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(CastExpression castExpression) {
		result.append("("); //$NON-NLS-1$
		result.append(CastExpression.getCastType(castExpression.getCastType()));
		result.append(")"); //$NON-NLS-1$
		castExpression.getExpr().accept(this);
		return false;
	}

	public boolean visit(CatchClause catchClause) {
		result.append("catch ("); //$NON-NLS-1$
		catchClause.getClassName().accept(this);
		result.append(" "); //$NON-NLS-1$
		catchClause.getVariable().accept(this);
		result.append(") "); //$NON-NLS-1$
		catchClause.getStatement().accept(this);
		return false;
	}

	public boolean visit(ClassConstantDeclaration classConstantDeclaration) {
		result.append("const "); //$NON-NLS-1$
		boolean isFirst = true;
		Identifier[] variableNames = classConstantDeclaration.getVariableNames();
		Expression[] constantValues = classConstantDeclaration.getConstantValues();
		for (int i = 0; i < variableNames.length; i++) {
			if (!isFirst) {
				result.append(","); //$NON-NLS-1$
			}
			variableNames[i].accept(this);
			result.append(" = "); //$NON-NLS-1$
			constantValues[i].accept(this);
			isFirst = false;
		}
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		result.append("class "); //$NON-NLS-1$
		classDeclaration.getName().accept(this);
		if (classDeclaration.getSuperClass() != null) {
			result.append(" extends "); //$NON-NLS-1$
			classDeclaration.getSuperClass().accept(this);
		}
		Identifier[] interfaces = classDeclaration.getInterfaces();
		if (interfaces != null && interfaces.length != 0) {
			result.append(" implements "); //$NON-NLS-1$
			interfaces[0].accept(this);
			for (int i = 1; i < interfaces.length; i++) {
				result.append(" , "); //$NON-NLS-1$
				interfaces[i].accept(this);
			}
		}
		classDeclaration.getBody().accept(this);
		return false;
	}

	public boolean visit(ClassInstanceCreation classInstanceCreation) {
		result.append("new "); //$NON-NLS-1$
		classInstanceCreation.getClassName().accept(this);
		Expression[] ctorParams = classInstanceCreation.getCtorParams();
		if (ctorParams.length != 0) {
			result.append("("); //$NON-NLS-1$
			ctorParams[0].accept(this);
			for (int i = 1; i < ctorParams.length; i++) {
				result.append(","); //$NON-NLS-1$
				ctorParams[i].accept(this);
			}
			result.append(")"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(ClassName className) {
		className.getClassName().accept(this);
		return false;
	}

	public boolean visit(CloneExpression cloneExpression) {
		result.append("clone "); //$NON-NLS-1$
		cloneExpression.getExpr().accept(this);
		return false;
	}

	public boolean visit(Comment comment) {
		// can't flatten, needs source
		return false;
	}

	public boolean visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.getCondition().accept(this);
		result.append(" ? "); //$NON-NLS-1$
		conditionalExpression.getIfTrue().accept(this);
		result.append(" : "); //$NON-NLS-1$
		conditionalExpression.getIfFalse().accept(this);
		return false;
	}

	public boolean visit(ContinueStatement continueStatement) {
		result.append("continue "); //$NON-NLS-1$
		if (continueStatement.getExpr() != null) {
			continueStatement.getExpr().accept(this);
		}
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(DeclareStatement declareStatement) {
		result.append("declare ("); //$NON-NLS-1$
		boolean isFirst = true;
		Identifier[] directiveNames = declareStatement.getDirectiveNames();
		Expression[] directiveValues = declareStatement.getDirectiveValues();
		for (int i = 0; i < directiveNames.length; i++) {
			if (!isFirst) {
				result.append(","); //$NON-NLS-1$
			}
			directiveNames[i].accept(this);
			result.append(" = "); //$NON-NLS-1$
			directiveValues[i].accept(this);
			isFirst = false;
		}
		result.append(")"); //$NON-NLS-1$
		declareStatement.getAction().accept(this);
		return false;
	}

	public boolean visit(DoStatement doStatement) {
		result.append("do "); //$NON-NLS-1$
		doStatement.getAction().accept(this);
		result.append("while ("); //$NON-NLS-1$
		doStatement.getCondition().accept(this);
		result.append(");\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(EchoStatement echoStatement) {
		result.append("echo "); //$NON-NLS-1$
		Expression[] expressions = echoStatement.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
		result.append(";\n "); //$NON-NLS-1$
		return false;
	}

	public boolean visit(EmptyStatement emptyStatement) {
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(ExpressionStatement expressionStatement) {
		expressionStatement.getExpr().accept(this);
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(FieldAccess fieldAccess) {
		fieldAccess.getDispatcher().accept(this);
		result.append("->"); //$NON-NLS-1$
		fieldAccess.getField().accept(this);
		return false;
	}

	public boolean visit(FieldsDeclaration fieldsDeclaration) {
		Variable[] variableNames = fieldsDeclaration.getVariableNames();
		Expression[] initialValues = fieldsDeclaration.getInitialValues();
		for (int i = 0; i < variableNames.length; i++) {
			result.append(fieldsDeclaration.getModifierString() + " "); //$NON-NLS-1$
			variableNames[i].accept(this);
			if (initialValues[i] != null) {
				result.append(" = "); //$NON-NLS-1$
				initialValues[i].accept(this);
			}
			result.append(";\n"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(ForEachStatement forEachStatement) {
		result.append("foreach ("); //$NON-NLS-1$
		forEachStatement.getExpression().accept(this);
		result.append(" as "); //$NON-NLS-1$
		if (forEachStatement.getKey() != null) {
			forEachStatement.getKey().accept(this);
			result.append(" => "); //$NON-NLS-1$
		}
		forEachStatement.getValue().accept(this);
		result.append(")"); //$NON-NLS-1$
		forEachStatement.getStatement().accept(this);
		return false;
	}

	public boolean visit(FormalParameter formalParameter) {
		if (formalParameter.getParameterType() != null) {
			formalParameter.getParameterType().accept(this);
		}
		formalParameter.getParameterName().accept(this);
		if (formalParameter.getDefaultValue() != null) {
			formalParameter.getDefaultValue().accept(this);
		}
		return false;
	}

	public boolean visit(ForStatement forStatement) {
		boolean isFirst = true;
		result.append("for ("); //$NON-NLS-1$
		Expression[] initializations = forStatement.getInitializations();
		Expression[] conditions = forStatement.getConditions();
		Expression[] increasements = forStatement.getIncreasements();
		for (int i = 0; i < initializations.length; i++) {
			if (!isFirst) {
				result.append(","); //$NON-NLS-1$
			}
			initializations[i].accept(this);
			isFirst = false;
		}
		isFirst = true;
		result.append(" ; "); //$NON-NLS-1$
		for (int i = 0; i < conditions.length; i++) {
			if (!isFirst) {
				result.append(","); //$NON-NLS-1$
			}
			conditions[i].accept(this);
			isFirst = false;
		}
		isFirst = true;
		result.append(" ; "); //$NON-NLS-1$
		for (int i = 0; i < increasements.length; i++) {
			if (!isFirst) {
				result.append(","); //$NON-NLS-1$
			}
			increasements[i].accept(this);
			isFirst = false;
		}
		result.append(" ) "); //$NON-NLS-1$
		forStatement.getAction().accept(this);
		return false;
	}

	public boolean visit(FunctionDeclaration functionDeclaration) {
		result.append(" function ");
		functionDeclaration.getFunctionName().accept(this);
		result.append("("); //$NON-NLS-1$
		FormalParameter[] formalParameters = functionDeclaration.getFormalParameters();
		if (formalParameters.length != 0) {
			formalParameters[0].accept(this);
			for (int i = 1; i < formalParameters.length; i++) {
				result.append(","); //$NON-NLS-1$
				formalParameters[i].accept(this);
			}

		}
		result.append(")"); //$NON-NLS-1$
		if (functionDeclaration.getBody() != null) {
			functionDeclaration.getBody().accept(this);
		}
		return false;
	}

	public boolean visit(FunctionInvocation functionInvocation) {
		functionInvocation.getFunctionName().accept(this);
		result.append("("); //$NON-NLS-1$
		Expression[] parameters = functionInvocation.getParameters();
		if (parameters.length != 0) {
			parameters[0].accept(this);
			for (int i = 1; i < parameters.length; i++) {
				result.append(","); //$NON-NLS-1$
				parameters[i].accept(this);
			}
		}
		result.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(FunctionName functionName) {
		functionName.getFunctionName().accept(this);
		return false;
	}

	public boolean visit(GlobalStatement globalStatement) {
		result.append("global "); //$NON-NLS-1$
		boolean isFirst = true;
		Variable[] variables = globalStatement.getVariables();
		for (int i = 0; i < variables.length; i++) {
			if (!isFirst) {
				result.append(", "); //$NON-NLS-1$
			}
			variables[i].accept(this);
			isFirst = false;
		}
		result.append(";\n "); //$NON-NLS-1$
		return false;
	}

	public boolean visit(Identifier identifier) {
		result.append(identifier.getName());
		return false;
	}

	public boolean visit(IfStatement ifStatement) {
		result.append("if("); //$NON-NLS-1$
		ifStatement.getCondition().accept(this);
		result.append(")"); //$NON-NLS-1$
		ifStatement.getTrueStatement().accept(this);
		if (ifStatement.getFalseStatement() != null) {
			result.append("else"); //$NON-NLS-1$
			ifStatement.getFalseStatement().accept(this);
		}
		return false;
	}

	public boolean visit(IgnoreError ignoreError) {
		result.append("@"); //$NON-NLS-1$
		ignoreError.getExpr().accept(this);
		return false;
	}

	public boolean visit(Include include) {
		result.append(Include.getType(include.getIncludeType()));
		result.append(" ("); //$NON-NLS-1$
		include.getExpr().accept(this);
		result.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(InfixExpression infixExpression) {
		infixExpression.getLeft().accept(this);
		result.append(InfixExpression.getOperator(infixExpression.getOperator()));
		infixExpression.getRight().accept(this);
		return false;
	}

	public boolean visit(InLineHtml inLineHtml) {
		// cant flatten, needs source
		return false;
	}

	public boolean visit(InstanceOfExpression instanceOfExpression) {
		instanceOfExpression.getExpr().accept(this);
		result.append(" instanceof "); //$NON-NLS-1$
		instanceOfExpression.getClassName().accept(this);
		return false;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		result.append("interface "); //$NON-NLS-1$
		interfaceDeclaration.getName().accept(this);
		result.append(" extends "); //$NON-NLS-1$
		boolean isFirst = true;
		Identifier[] interfaces = interfaceDeclaration.getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			if (!isFirst) {
				result.append(", "); //$NON-NLS-1$
			}
			interfaces[i].accept(this);
			isFirst = false;
		}
		interfaceDeclaration.getBody().accept(this);
		return false;
	}

	public boolean visit(ListVariable listVariable) {
		result.append("list("); //$NON-NLS-1$
		boolean isFirst = true;
		VariableBase[] variables = listVariable.getVariables();
		for (int i = 0; i < variables.length; i++) {
			if (!isFirst) {
				result.append(", "); //$NON-NLS-1$
			}
			variables[i].accept(this);
			isFirst = false;
		}
		result.append(")"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(MethodDeclaration methodDeclaration) {
		result.append(methodDeclaration.getModifierString());
		methodDeclaration.getFunction().accept(this);
		return false;
	}

	public boolean visit(MethodInvocation methodInvocation) {
		methodInvocation.getDispatcher().accept(this);
		result.append("->"); //$NON-NLS-1$
		methodInvocation.getMethod().accept(this);
		return false;
	}

	public boolean visit(ParenthesisExpression parenthesisExpression) {
		result.append("("); //$NON-NLS-1$
		if (parenthesisExpression.getExpr() != null) {
			parenthesisExpression.getExpr().accept(this);
		}
		result.append(")"); //$NON-NLS-1$

		return false;
	}

	public boolean visit(PostfixExpression postfixExpressions) {
		postfixExpressions.getVariable().accept(this);
		result.append(PostfixExpression.getOperator(postfixExpressions.getOperator()));
		return false;
	}

	public boolean visit(PrefixExpression prefixExpression) {
		prefixExpression.getVariable().accept(this);
		result.append(PrefixExpression.getOperator(prefixExpression.getOperator()));
		return false;
	}

	public boolean visit(Program program) {
		boolean isPhpState = false;
		Statement[] statements = program.getStatements();
		for (int i = 0; i < statements.length; i++) {
			boolean isHtml = statements[i] instanceof InLineHtml;

			if (!isHtml && !isPhpState) {
				// html -> php
				result.append("<?php\n"); //$NON-NLS-1$
				statements[i].accept(this);
				isPhpState = true;
			} else if (!isHtml && isPhpState) {
				// php -> php
				statements[i].accept(this);
				result.append("\n"); //$NON-NLS-1$
			} else if (isHtml && isPhpState) {
				// php -> html
				result.append("?>\n"); //$NON-NLS-1$
				statements[i].accept(this);
				result.append("\n"); //$NON-NLS-1$
				isPhpState = false;
			} else {
				// html first
				statements[i].accept(this);
				result.append("\n"); //$NON-NLS-1$
			}
		}

		if (isPhpState) {
			result.append("?>\n"); //$NON-NLS-1$
		}

		Collection comments = program.getComments();
		for (Iterator iter = comments.iterator(); iter.hasNext();) {
			Comment comment = (Comment) iter.next();
			comment.accept(this);
		}
		return false;
	}

	public boolean visit(Quote quote) {
		switch (quote.getQuoteType()) {
			case 0:
				result.append("\""); //$NON-NLS-1$
				acceptQuoteExpression(quote.getExpressions());
				result.append("\""); //$NON-NLS-1$
				break;
			case 1:
				result.append("\'"); //$NON-NLS-1$
				acceptQuoteExpression(quote.getExpressions());
				result.append("\'"); //$NON-NLS-1$
				break;
			case 2:
				result.append("<<<Heredoc\n"); //$NON-NLS-1$
				acceptQuoteExpression(quote.getExpressions());
				result.append("\nHeredoc"); //$NON-NLS-1$
		}
		return false;
	}

	public boolean visit(Reference reference) {
		result.append("&"); //$NON-NLS-1$
		reference.getExpression().accept(this);
		return false;
	}

	public boolean visit(ReflectionVariable reflectionVariable) {
		result.append("$"); //$NON-NLS-1$
		reflectionVariable.getVariableName().accept(this);
		return false;
	}

	public boolean visit(ReturnStatement returnStatement) {
		result.append("return "); //$NON-NLS-1$
		if (returnStatement.getExpr() != null) {
			returnStatement.getExpr().accept(this);
		}
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(Scalar scalar) {
		if (scalar.getScalarType() == Scalar.TYPE_UNKNOWN) {
			// cant flatten, needs source
		} else {
			result.append(scalar.getStringValue());
		}
		return false;
	}

	public boolean visit(StaticConstantAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		result.append("::"); //$NON-NLS-1$
		staticFieldAccess.getConstant().accept(this);
		return false;
	}

	public boolean visit(StaticFieldAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		result.append("::"); //$NON-NLS-1$
		staticFieldAccess.getField().accept(this);
		return false;
	}

	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		staticMethodInvocation.getClassName().accept(this);
		result.append("::"); //$NON-NLS-1$
		staticMethodInvocation.getMethod().accept(this);
		return false;
	}

	public boolean visit(StaticStatement staticStatement) {
		result.append("static "); //$NON-NLS-1$
		boolean isFirst = true;
		Expression[] expressions = staticStatement.getExpressions();
		for (int i = 0; i < expressions.length; i++) {
			if (!isFirst) {
				result.append(", "); //$NON-NLS-1$
			}
			expressions[i].accept(this);
			isFirst = false;
		}
		result.append(";\n"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(SwitchCase switchCase) {

		if (switchCase.getValue() != null) {
			switchCase.getValue().accept(this);
			result.append(":\n"); //$NON-NLS-1$
		}
		Statement[] actions = switchCase.getActions();
		for (int i = 0; i < actions.length; i++) {
			actions[i].accept(this);
		}
		return false;
	}

	public boolean visit(SwitchStatement switchStatement) {
		result.append("switch ("); //$NON-NLS-1$
		switchStatement.getExpr().accept(this);
		result.append(")"); //$NON-NLS-1$
		switchStatement.getStatement().accept(this);
		return false;
	}

	public boolean visit(ThrowStatement throwStatement) {
		throwStatement.getExpr().accept(this);
		return false;
	}

	public boolean visit(TryStatement tryStatement) {
		result.append("try "); //$NON-NLS-1$
		tryStatement.getTryStatement().accept(this);
		CatchClause[] catchClauses = tryStatement.getCatchClauses();
		for (int i = 0; i < catchClauses.length; i++) {
			catchClauses[i].accept(this);
		}
		return false;
	}

	public boolean visit(UnaryOperation unaryOperation) {
		result.append(UnaryOperation.getOperator(unaryOperation.getOperator()));
		unaryOperation.getExpr().accept(this);
		return false;
	}

	public boolean visit(Variable variable) {
		result.append("$");
		variable.getVariableName().accept(this);
		return false;
	}

	public boolean visit(WhileStatement whileStatement) {
		result.append("while ("); //$NON-NLS-1$
		whileStatement.getCondition().accept(this);
		result.append(")\n"); //$NON-NLS-1$
		whileStatement.getAction().accept(this);
		return false;
	}
	
	private void acceptQuoteExpression(Expression[] expressions) {
		for (int i = 0; i < expressions.length; i++) {
			expressions[i].accept(this);
		}
	}

}
