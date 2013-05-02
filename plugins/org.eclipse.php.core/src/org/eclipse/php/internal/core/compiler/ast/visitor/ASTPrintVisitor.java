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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.util.XMLWriter;

/**
 * This visitor is used for printing AST nodes in an XML format
 * 
 * @author michael
 */
public class ASTPrintVisitor extends PHPASTVisitor {

	private XMLWriter xmlWriter;

	/**
	 * Constructs new {@link ASTPrintVisitor}
	 * 
	 * @param out
	 *            Output stream to print the XML to
	 * @throws Exception
	 */
	private ASTPrintVisitor(OutputStream out) throws Exception {
		xmlWriter = new XMLWriter(out, false);
	}

	private void close() {
		xmlWriter.flush();
		xmlWriter.close();
	}

	public static String toXMLString(ASTNode node) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ASTPrintVisitor printVisitor = new ASTPrintVisitor(out);
			node.traverse(printVisitor);
			printVisitor.close();
			return out.toString("UTF-8"); //$NON-NLS-1$

		} catch (Exception e) {
			return e.toString();
		}
	}

	protected Map<String, String> createInitialParameters(ASTNode s)
			throws Exception {
		Map<String, String> parameters = new LinkedHashMap<String, String>();

		// Print offset information:
		parameters.put("start", Integer.toString(s.sourceStart())); //$NON-NLS-1$
		parameters.put("end", Integer.toString(s.sourceEnd())); //$NON-NLS-1$

		// Print modifiers:
		if (s instanceof Declaration) {
			Declaration declaration = (Declaration) s;
			StringBuilder buf = new StringBuilder();
			if (declaration.isAbstract()) {
				buf.append(",abstract"); //$NON-NLS-1$
			}
			if (declaration.isFinal()) {
				buf.append(",final"); //$NON-NLS-1$
			}
			if (declaration.isPrivate()) {
				buf.append(",private"); //$NON-NLS-1$
			}
			if (declaration.isProtected()) {
				buf.append(",protected"); //$NON-NLS-1$
			}
			if (declaration.isPublic()) {
				buf.append(",public"); //$NON-NLS-1$
			}
			if (declaration.isStatic()) {
				buf.append(",static"); //$NON-NLS-1$
			}
			String modifiers = buf.toString();
			parameters
					.put("modifiers", //$NON-NLS-1$
							modifiers.length() > 0 ? modifiers.substring(1)
									: modifiers);
		}

		return parameters;
	}

	public boolean endvisit(ArrayCreation s) throws Exception {
		xmlWriter.endTag("ArrayCreation"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ArrayElement s) throws Exception {
		xmlWriter.endTag("ArrayElement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ArrayVariableReference s) throws Exception {
		xmlWriter.endTag("ArrayVariableReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(Assignment s) throws Exception {
		xmlWriter.endTag("Assignment"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ASTError s) throws Exception {
		xmlWriter.endTag("ASTError"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(BackTickExpression s) throws Exception {
		xmlWriter.endTag("BackTickExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(BreakStatement s) throws Exception {
		xmlWriter.endTag("BreakStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(CastExpression s) throws Exception {
		xmlWriter.endTag("CastExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(CatchClause s) throws Exception {
		xmlWriter.endTag("CatchClause"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ConstantDeclaration s) throws Exception {
		xmlWriter.endTag("ConstantDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ClassDeclaration s) throws Exception {
		xmlWriter.endTag("ClassDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ClassInstanceCreation s) throws Exception {
		xmlWriter.endTag("ClassInstanceCreation"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(CloneExpression s) throws Exception {
		xmlWriter.endTag("CloneExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(Comment s) throws Exception {
		xmlWriter.endTag("Comment"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ConditionalExpression s) throws Exception {
		xmlWriter.endTag("ConditionalExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ConstantReference s) throws Exception {
		xmlWriter.endTag("ConstantReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ContinueStatement s) throws Exception {
		xmlWriter.endTag("ContinueStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(DeclareStatement s) throws Exception {
		xmlWriter.endTag("DeclareStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(Dispatch s) throws Exception {
		xmlWriter.endTag("Dispatch"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(DoStatement s) throws Exception {
		xmlWriter.endTag("DoStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(EchoStatement s) throws Exception {
		xmlWriter.endTag("EchoStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(EmptyStatement s) throws Exception {
		xmlWriter.endTag("EmptyStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ExpressionStatement s) throws Exception {
		xmlWriter.endTag("ExpressionStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(FieldAccess s) throws Exception {
		xmlWriter.endTag("FieldAccess"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ForEachStatement s) throws Exception {
		xmlWriter.endTag("ForEachStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(FormalParameter s) throws Exception {
		xmlWriter.endTag("FormalParameter"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(FormalParameterByReference s) throws Exception {
		xmlWriter.endTag("FormalParameterByReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ForStatement s) throws Exception {
		xmlWriter.endTag("ForStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(GlobalStatement s) throws Exception {
		xmlWriter.endTag("GlobalStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(IfStatement s) throws Exception {
		xmlWriter.endTag("IfStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(IgnoreError s) throws Exception {
		xmlWriter.endTag("IgnoreError"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(Include s) throws Exception {
		xmlWriter.endTag("Include"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(InfixExpression s) throws Exception {
		xmlWriter.endTag("InfixExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(InstanceOfExpression s) throws Exception {
		xmlWriter.endTag("InstanceOfExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(InterfaceDeclaration s) throws Exception {
		xmlWriter.endTag("InterfaceDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ListVariable s) throws Exception {
		xmlWriter.endTag("ListVariable"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PHPCallArgumentsList s) throws Exception {
		xmlWriter.endTag("PHPCallArgumentsList"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PHPCallExpression s) throws Exception {
		xmlWriter.endTag("PHPCallExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PHPDocBlock s) throws Exception {
		xmlWriter.endTag("PHPDocBlock"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PHPDocTag s) throws Exception {
		xmlWriter.endTag("PHPDocTag"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PHPFieldDeclaration s) throws Exception {
		xmlWriter.endTag("PHPFieldDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PHPMethodDeclaration s) throws Exception {
		xmlWriter.endTag("PHPMethodDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PostfixExpression s) throws Exception {
		xmlWriter.endTag("PostfixExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(PrefixExpression s) throws Exception {
		xmlWriter.endTag("PrefixExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(Quote s) throws Exception {
		xmlWriter.endTag("Quote"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ReferenceExpression s) throws Exception {
		xmlWriter.endTag("ReferenceExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ReflectionArrayVariableReference s)
			throws Exception {
		xmlWriter.endTag("ReflectionArrayVariableReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ReflectionCallExpression s) throws Exception {
		xmlWriter.endTag("ReflectionCallExpression"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ReflectionStaticMethodInvocation s)
			throws Exception {
		xmlWriter.endTag("ReflectionStaticMethodInvocation"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ReflectionVariableReference s) throws Exception {
		xmlWriter.endTag("ReflectionVariableReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ReturnStatement s) throws Exception {
		xmlWriter.endTag("ReturnStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(Scalar s) throws Exception {
		xmlWriter.endTag("Scalar"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(SimpleReference s) throws Exception {
		xmlWriter.endTag("SimpleReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(StaticConstantAccess s) throws Exception {
		xmlWriter.endTag("StaticConstantAccess"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(StaticDispatch s) throws Exception {
		xmlWriter.endTag("StaticDispatch"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(StaticFieldAccess s) throws Exception {
		xmlWriter.endTag("StaticFieldAccess"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(StaticMethodInvocation s) throws Exception {
		xmlWriter.endTag("StaticMethodInvocation"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(StaticStatement s) throws Exception {
		xmlWriter.endTag("StaticStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(SwitchCase s) throws Exception {
		xmlWriter.endTag("SwitchCase"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(SwitchStatement s) throws Exception {
		xmlWriter.endTag("SwitchStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ThrowStatement s) throws Exception {
		xmlWriter.endTag("ThrowStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(TryStatement s) throws Exception {
		xmlWriter.endTag("TryStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(TypeReference s) throws Exception {
		xmlWriter.endTag("TypeReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(FullyQualifiedReference s) throws Exception {
		xmlWriter.endTag("FullyQualifiedReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(NamespaceReference s) throws Exception {
		xmlWriter.endTag("NamespaceReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(UnaryOperation s) throws Exception {
		xmlWriter.endTag("UnaryOperation"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(VariableReference s) throws Exception {
		xmlWriter.endTag("VariableReference"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(WhileStatement s) throws Exception {
		xmlWriter.endTag("WhileStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ModuleDeclaration s) throws Exception {
		List<ASTError> errors = ((PHPModuleDeclaration) s).getErrors();
		if (!errors.isEmpty()) {
			xmlWriter.startTag("Errors", null); //$NON-NLS-1$
			for (ASTError error : errors) {
				error.traverse(this);
			}
			xmlWriter.endTag("Errors"); //$NON-NLS-1$
		}
		xmlWriter.endTag("ModuleDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(NamespaceDeclaration s) throws Exception {
		xmlWriter.endTag("NamespaceDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(GotoLabel s) throws Exception {
		xmlWriter.endTag("GotoLabel"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(GotoStatement s) throws Exception {
		xmlWriter.endTag("GotoStatement"); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(LambdaFunctionDeclaration s) throws Exception {
		xmlWriter.endTag("LambdaFunctionDeclaration"); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ArrayCreation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ArrayCreation", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ArrayElement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ArrayElement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ArrayVariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", //$NON-NLS-1$
				ArrayVariableReference.getArrayType(s.getArrayType()));
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("ArrayVariableReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(Assignment s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator()); //$NON-NLS-1$
		xmlWriter.startTag("Assignment", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ASTError s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ASTError", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(BackTickExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("BackTickExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(BreakStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("BreakStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(CastExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", CastExpression.getCastType(s.getCastType())); //$NON-NLS-1$
		xmlWriter.startTag("CastExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(CatchClause s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("CatchClause", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ConstantDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ConstantDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ClassDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("ClassDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ClassInstanceCreation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ClassInstanceCreation", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(CloneExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("CloneExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(Comment s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", Comment.getCommentType(s.getCommentType())); //$NON-NLS-1$
		xmlWriter.startTag("Comment", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ConditionalExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ConditionalExpression", parameters); //$NON-NLS-1$

		xmlWriter.startTag("Condition", new HashMap<String, String>()); //$NON-NLS-1$
		s.getCondition().traverse(this);
		xmlWriter.endTag("Condition"); //$NON-NLS-1$

		Expression ifTrue = s.getIfTrue();
		if (ifTrue != null) {
			xmlWriter.startTag("IfTrue", new HashMap<String, String>()); //$NON-NLS-1$
			ifTrue.traverse(this);
			xmlWriter.endTag("IfTrue"); //$NON-NLS-1$
		}

		Expression falseExp = s.getIfFalse();
		if (falseExp != null) {
			xmlWriter.startTag("IfFalse", new HashMap<String, String>()); //$NON-NLS-1$
			falseExp.traverse(this);
			xmlWriter.endTag("IfFalse"); //$NON-NLS-1$
		}

		return false;
	}

	public boolean visit(ConstantReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("ConstantReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ContinueStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ContinueStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(DeclareStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("DeclareStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(Dispatch s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("Dispatch", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(DoStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("DoStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(EchoStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("EchoStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(EmptyStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("EmptyStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ExpressionStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ExpressionStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(FieldAccess s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("FieldAccess", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ForEachStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ForEachStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(FormalParameter s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("isMandatory", Boolean.toString(s.isMandatory())); //$NON-NLS-1$
		xmlWriter.startTag("FormalParameter", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(FormalParameterByReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("FormalParameterByReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ForStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ForStatement", parameters); //$NON-NLS-1$

		xmlWriter.startTag("Initializations", new HashMap<String, String>()); //$NON-NLS-1$
		for (Expression initialization : s.getInitializations()) {
			initialization.traverse(this);
		}
		xmlWriter.endTag("Initializations"); //$NON-NLS-1$

		xmlWriter.startTag("Conditions", new HashMap<String, String>()); //$NON-NLS-1$
		for (Expression condition : s.getConditions()) {
			condition.traverse(this);
		}
		xmlWriter.endTag("Conditions"); //$NON-NLS-1$

		xmlWriter.startTag("Increasements", new HashMap<String, String>()); //$NON-NLS-1$
		for (Expression increasement : s.getIncreasements()) {
			increasement.traverse(this);
		}
		xmlWriter.endTag("Increasements"); //$NON-NLS-1$

		s.getAction().traverse(this);

		return false;
	}

	public boolean visit(GlobalStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("GlobalStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(IfStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("IfStatement", parameters); //$NON-NLS-1$

		xmlWriter.startTag("Condition", new HashMap<String, String>()); //$NON-NLS-1$
		s.getCondition().traverse(this);
		xmlWriter.endTag("Condition"); //$NON-NLS-1$

		xmlWriter.startTag("TrueStatement", new HashMap<String, String>()); //$NON-NLS-1$
		s.getTrueStatement().traverse(this);
		xmlWriter.endTag("TrueStatement"); //$NON-NLS-1$

		Statement falseStatement = s.getFalseStatement();
		if (falseStatement != null) {
			xmlWriter.startTag("FalseStatement", new HashMap<String, String>()); //$NON-NLS-1$
			falseStatement.traverse(this);
			xmlWriter.endTag("FalseStatement"); //$NON-NLS-1$
		}

		return false;
	}

	public boolean visit(IgnoreError s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("IgnoreError", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(Include s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", s.getType()); //$NON-NLS-1$
		xmlWriter.startTag("Include", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(InfixExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator()); //$NON-NLS-1$
		xmlWriter.startTag("InfixExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(InstanceOfExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("InstanceOfExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(InterfaceDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("InterfaceDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ListVariable s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ListVariable", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PHPCallArgumentsList s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPCallArgumentsList", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PHPCallExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPCallExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PHPDocBlock s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("shortDescription", s.getShortDescription()); //$NON-NLS-1$
		xmlWriter.startTag("PHPDocBlock", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PHPDocTag s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("tagKind", PHPDocTag.getTagKind(s.getTagKind())); //$NON-NLS-1$
		parameters.put("value", s.getValue()); //$NON-NLS-1$
		xmlWriter.startTag("PHPDocTag", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PHPFieldDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPFieldDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PHPMethodDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("PHPMethodDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PostfixExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator()); //$NON-NLS-1$
		xmlWriter.startTag("PostfixExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(PrefixExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator()); //$NON-NLS-1$
		xmlWriter.startTag("PrefixExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(Quote s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", Quote.getType(s.getQuoteType())); //$NON-NLS-1$
		xmlWriter.startTag("Quote", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ReferenceExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReferenceExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ReflectionArrayVariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionArrayVariableReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ReflectionCallExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionCallExpression", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ReflectionStaticMethodInvocation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionStaticMethodInvocation", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ReflectionVariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionVariableReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ReturnStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReturnStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(Scalar s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", s.getType()); //$NON-NLS-1$
		parameters.put("value", s.getValue()); //$NON-NLS-1$
		xmlWriter.startTag("Scalar", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(SimpleReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("SimpleReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(StaticConstantAccess s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticConstantAccess", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(StaticDispatch s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticDispatch", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(StaticFieldAccess s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticFieldAccess", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(StaticMethodInvocation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticMethodInvocation", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(StaticStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(SwitchCase s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("SwitchCase", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(SwitchStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("SwitchStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ThrowStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ThrowStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TryStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("TryStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TypeReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("TypeReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(FullyQualifiedReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getFullyQualifiedName()); //$NON-NLS-1$
		xmlWriter.startTag("FullyQualifiedReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(NamespaceReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		parameters.put("global", Boolean.toString(s.isGlobal())); //$NON-NLS-1$
		parameters.put("local", Boolean.toString(s.isLocal())); //$NON-NLS-1$
		xmlWriter.startTag("NamespaceReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(UnaryOperation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator()); //$NON-NLS-1$
		xmlWriter.startTag("UnaryOperation", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(VariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("VariableReference", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(WhileStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("WhileStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ModuleDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(NamespaceDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("NamespaceDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(UseStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("UseStatement", parameters); //$NON-NLS-1$

		xmlWriter.startTag("Parts", new HashMap<String, String>()); //$NON-NLS-1$
		for (UsePart p : s.getParts()) {
			p.traverse(this);
		}
		xmlWriter.endTag("Parts"); //$NON-NLS-1$
		xmlWriter.endTag("UseStatement"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(UsePart s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("UsePart", parameters); //$NON-NLS-1$
		s.getNamespace().traverse(this);
		if (s.getAlias() != null) {
			s.getAlias().traverse(this);
		}
		xmlWriter.endTag("UsePart"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(GotoLabel s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("label", s.getLabel()); //$NON-NLS-1$
		xmlWriter.startTag("GotoLabel", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(GotoStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("label", s.getLabel()); //$NON-NLS-1$
		xmlWriter.startTag("GotoStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(LambdaFunctionDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("isReference", Boolean.toString(s.isReference())); //$NON-NLS-1$
		if (s.isStatic()) {
			parameters.put("isStatic", Boolean.toString(s.isStatic())); //$NON-NLS-1$
		}
		xmlWriter.startTag("LambdaFunctionDeclaration", parameters); //$NON-NLS-1$

		xmlWriter.startTag("Arguments", new HashMap<String, String>()); //$NON-NLS-1$
		for (FormalParameter p : s.getArguments()) {
			p.traverse(this);
		}
		xmlWriter.endTag("Arguments"); //$NON-NLS-1$

		Collection<? extends Expression> lexicalVars = s.getLexicalVars();
		if (lexicalVars != null) {
			xmlWriter.startTag("LexicalVars", new HashMap<String, String>()); //$NON-NLS-1$
			for (Expression var : lexicalVars) {
				var.traverse(this);
			}
			xmlWriter.endTag("LexicalVars"); //$NON-NLS-1$
		}

		s.getBody().traverse(this);

		return false;
	}

	// php5.4 starts
	Map<String, String> EMPTY_MAP = new HashMap<String, String>();

	public boolean visit(ChainingInstanceCall s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ChainingInstanceCall", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(ChainingMethodPropertyList s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ChainingMethodPropertyList", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(DereferenceNode s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("DereferenceNode", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(FullyQualifiedTraitMethodReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("functionName", s.getFunctionName()); //$NON-NLS-1$
		xmlWriter.startTag("FullyQualifiedTraitMethodReference", parameters); //$NON-NLS-1$
		xmlWriter.startTag("className", EMPTY_MAP); //$NON-NLS-1$
		s.getClassName().traverse(this);
		xmlWriter.endTag("className"); //$NON-NLS-1$
		xmlWriter.endTag("FullyQualifiedTraitMethodReference"); //$NON-NLS-1$
		return false;
	}

	public boolean visit(PHPArrayDereferenceList s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPArrayDereferenceList", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TraitAlias s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		if (s.getMethodName() != null) {
			parameters.put("methodName", s.getMethodName().getName()); //$NON-NLS-1$
		}

		xmlWriter.startTag("TraitAlias", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TraitAliasStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("TraitAliasStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TraitPrecedence s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("TraitPrecedence", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TraitPrecedenceStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("TraitPrecedenceStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TraitUseStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("TraitUseStatement", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean visit(TraitDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName()); //$NON-NLS-1$
		xmlWriter.startTag("TraitDeclaration", parameters); //$NON-NLS-1$
		return true;
	}

	public boolean endvisit(ChainingInstanceCall s) throws Exception {
		xmlWriter.endTag("ChainingInstanceCall"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(ChainingMethodPropertyList s) throws Exception {
		xmlWriter.endTag("ChainingMethodPropertyList"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(DereferenceNode s) throws Exception {
		xmlWriter.endTag("DereferenceNode"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(FullyQualifiedTraitMethodReference s)
			throws Exception {
		xmlWriter.endTag("FullyQualifiedTraitMethodReference"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(PHPArrayDereferenceList s) throws Exception {
		xmlWriter.endTag("PHPArrayDereferenceList"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(TraitAlias s) throws Exception {
		xmlWriter.endTag("TraitAlias"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(TraitAliasStatement s) throws Exception {
		xmlWriter.endTag("TraitAliasStatement"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(TraitPrecedence s) throws Exception {
		xmlWriter.endTag("TraitPrecedence"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(TraitPrecedenceStatement s) throws Exception {
		xmlWriter.endTag("TraitPrecedenceStatement"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(TraitUseStatement s) throws Exception {
		xmlWriter.endTag("TraitUseStatement"); //$NON-NLS-1$
		return false;
	}

	public boolean endvisit(TraitDeclaration s) throws Exception {
		xmlWriter.endTag("TraitDeclaration"); //$NON-NLS-1$
		return false;
	}
	// php5.4 ends
}
