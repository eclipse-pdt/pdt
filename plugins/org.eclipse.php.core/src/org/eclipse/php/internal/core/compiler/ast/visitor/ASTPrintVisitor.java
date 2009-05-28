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
 * @author michael
 */
public class ASTPrintVisitor extends PHPASTVisitor {

	private XMLWriter xmlWriter;

	/**
	 * Constructs new {@link ASTPrintVisitor}
	 * @param out Output stream to print the XML to
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
			return out.toString("UTF-8");

		} catch (Exception e) {
			return e.toString();
		}
	}

	protected Map<String, String> createInitialParameters(ASTNode s) throws Exception {
		Map<String, String> parameters = new LinkedHashMap<String, String>();

		// Print offset information:
		parameters.put("start", Integer.toString(s.sourceStart()));
		parameters.put("end", Integer.toString(s.sourceEnd()));

		// Print modifiers:
		if (s instanceof Declaration) {
			Declaration declaration = (Declaration) s;
			StringBuilder buf = new StringBuilder();
			if (declaration.isAbstract()) {
				buf.append(",abstract");
			}
			if (declaration.isFinal()) {
				buf.append(",final");
			}
			if (declaration.isPrivate()) {
				buf.append(",private");
			}
			if (declaration.isProtected()) {
				buf.append(",protected");
			}
			if (declaration.isPublic()) {
				buf.append(",public");
			}
			if (declaration.isStatic()) {
				buf.append(",static");
			}
			String modifiers = buf.toString();
			parameters.put("modifiers", modifiers.length() > 0 ? modifiers.substring(1) : modifiers);
		}

		return parameters;
	}

	public boolean endvisit(ArrayCreation s) throws Exception {
		xmlWriter.endTag("ArrayCreation");
		return true;
	}

	public boolean endvisit(ArrayElement s) throws Exception {
		xmlWriter.endTag("ArrayElement");
		return true;
	}

	public boolean endvisit(ArrayVariableReference s) throws Exception {
		xmlWriter.endTag("ArrayVariableReference");
		return true;
	}

	public boolean endvisit(Assignment s) throws Exception {
		xmlWriter.endTag("Assignment");
		return true;
	}

	public boolean endvisit(ASTError s) throws Exception {
		xmlWriter.endTag("ASTError");
		return true;
	}

	public boolean endvisit(BackTickExpression s) throws Exception {
		xmlWriter.endTag("BackTickExpression");
		return true;
	}

	public boolean endvisit(BreakStatement s) throws Exception {
		xmlWriter.endTag("BreakStatement");
		return true;
	}

	public boolean endvisit(CastExpression s) throws Exception {
		xmlWriter.endTag("CastExpression");
		return true;
	}

	public boolean endvisit(CatchClause s) throws Exception {
		xmlWriter.endTag("CatchClause");
		return true;
	}

	public boolean endvisit(ConstantDeclaration s) throws Exception {
		xmlWriter.endTag("ConstantDeclaration");
		return true;
	}

	public boolean endvisit(ClassDeclaration s) throws Exception {
		xmlWriter.endTag("ClassDeclaration");
		return true;
	}

	public boolean endvisit(ClassInstanceCreation s) throws Exception {
		xmlWriter.endTag("ClassInstanceCreation");
		return true;
	}

	public boolean endvisit(CloneExpression s) throws Exception {
		xmlWriter.endTag("CloneExpression");
		return true;
	}

	public boolean endvisit(Comment s) throws Exception {
		xmlWriter.endTag("Comment");
		return true;
	}

	public boolean endvisit(ConditionalExpression s) throws Exception {
		xmlWriter.endTag("ConditionalExpression");
		return true;
	}

	public boolean endvisit(ConstantReference s) throws Exception {
		xmlWriter.endTag("ConstantReference");
		return true;
	}

	public boolean endvisit(ContinueStatement s) throws Exception {
		xmlWriter.endTag("ContinueStatement");
		return true;
	}

	public boolean endvisit(DeclareStatement s) throws Exception {
		xmlWriter.endTag("DeclareStatement");
		return true;
	}

	public boolean endvisit(Dispatch s) throws Exception {
		xmlWriter.endTag("Dispatch");
		return true;
	}

	public boolean endvisit(DoStatement s) throws Exception {
		xmlWriter.endTag("DoStatement");
		return true;
	}

	public boolean endvisit(EchoStatement s) throws Exception {
		xmlWriter.endTag("EchoStatement");
		return true;
	}

	public boolean endvisit(EmptyStatement s) throws Exception {
		xmlWriter.endTag("EmptyStatement");
		return true;
	}

	public boolean endvisit(ExpressionStatement s) throws Exception {
		xmlWriter.endTag("ExpressionStatement");
		return true;
	}

	public boolean endvisit(FieldAccess s) throws Exception {
		xmlWriter.endTag("FieldAccess");
		return true;
	}

	public boolean endvisit(ForEachStatement s) throws Exception {
		xmlWriter.endTag("ForEachStatement");
		return true;
	}

	public boolean endvisit(FormalParameter s) throws Exception {
		xmlWriter.endTag("FormalParameter");
		return true;
	}

	public boolean endvisit(FormalParameterByReference s) throws Exception {
		xmlWriter.endTag("FormalParameterByReference");
		return true;
	}

	public boolean endvisit(ForStatement s) throws Exception {
		xmlWriter.endTag("ForStatement");
		return true;
	}

	public boolean endvisit(GlobalStatement s) throws Exception {
		xmlWriter.endTag("GlobalStatement");
		return true;
	}

	public boolean endvisit(IfStatement s) throws Exception {
		xmlWriter.endTag("IfStatement");
		return true;
	}

	public boolean endvisit(IgnoreError s) throws Exception {
		xmlWriter.endTag("IgnoreError");
		return true;
	}

	public boolean endvisit(Include s) throws Exception {
		xmlWriter.endTag("Include");
		return true;
	}

	public boolean endvisit(InfixExpression s) throws Exception {
		xmlWriter.endTag("InfixExpression");
		return true;
	}

	public boolean endvisit(InstanceOfExpression s) throws Exception {
		xmlWriter.endTag("InstanceOfExpression");
		return true;
	}

	public boolean endvisit(InterfaceDeclaration s) throws Exception {
		xmlWriter.endTag("InterfaceDeclaration");
		return true;
	}

	public boolean endvisit(ListVariable s) throws Exception {
		xmlWriter.endTag("ListVariable");
		return true;
	}

	public boolean endvisit(PHPCallArgumentsList s) throws Exception {
		xmlWriter.endTag("PHPCallArgumentsList");
		return true;
	}

	public boolean endvisit(PHPCallExpression s) throws Exception {
		xmlWriter.endTag("PHPCallExpression");
		return true;
	}

	public boolean endvisit(PHPDocBlock s) throws Exception {
		xmlWriter.endTag("PHPDocBlock");
		return true;
	}

	public boolean endvisit(PHPDocTag s) throws Exception {
		xmlWriter.endTag("PHPDocTag");
		return true;
	}

	public boolean endvisit(PHPFieldDeclaration s) throws Exception {
		xmlWriter.endTag("PHPFieldDeclaration");
		return true;
	}

	public boolean endvisit(PHPMethodDeclaration s) throws Exception {
		xmlWriter.endTag("PHPMethodDeclaration");
		return true;
	}

	public boolean endvisit(PostfixExpression s) throws Exception {
		xmlWriter.endTag("PostfixExpression");
		return true;
	}

	public boolean endvisit(PrefixExpression s) throws Exception {
		xmlWriter.endTag("PrefixExpression");
		return true;
	}

	public boolean endvisit(Quote s) throws Exception {
		xmlWriter.endTag("Quote");
		return true;
	}

	public boolean endvisit(ReferenceExpression s) throws Exception {
		xmlWriter.endTag("ReferenceExpression");
		return true;
	}

	public boolean endvisit(ReflectionArrayVariableReference s) throws Exception {
		xmlWriter.endTag("ReflectionArrayVariableReference");
		return true;
	}

	public boolean endvisit(ReflectionCallExpression s) throws Exception {
		xmlWriter.endTag("ReflectionCallExpression");
		return true;
	}

	public boolean endvisit(ReflectionStaticMethodInvocation s) throws Exception {
		xmlWriter.endTag("ReflectionStaticMethodInvocation");
		return true;
	}

	public boolean endvisit(ReflectionVariableReference s) throws Exception {
		xmlWriter.endTag("ReflectionVariableReference");
		return true;
	}

	public boolean endvisit(ReturnStatement s) throws Exception {
		xmlWriter.endTag("ReturnStatement");
		return true;
	}

	public boolean endvisit(Scalar s) throws Exception {
		xmlWriter.endTag("Scalar");
		return true;
	}

	public boolean endvisit(SimpleReference s) throws Exception {
		xmlWriter.endTag("SimpleReference");
		return true;
	}

	public boolean endvisit(StaticConstantAccess s) throws Exception {
		xmlWriter.endTag("StaticConstantAccess");
		return true;
	}

	public boolean endvisit(StaticDispatch s) throws Exception {
		xmlWriter.endTag("StaticDispatch");
		return true;
	}

	public boolean endvisit(StaticFieldAccess s) throws Exception {
		xmlWriter.endTag("StaticFieldAccess");
		return true;
	}

	public boolean endvisit(StaticMethodInvocation s) throws Exception {
		xmlWriter.endTag("StaticMethodInvocation");
		return true;
	}

	public boolean endvisit(StaticStatement s) throws Exception {
		xmlWriter.endTag("StaticStatement");
		return true;
	}

	public boolean endvisit(SwitchCase s) throws Exception {
		xmlWriter.endTag("SwitchCase");
		return true;
	}

	public boolean endvisit(SwitchStatement s) throws Exception {
		xmlWriter.endTag("SwitchStatement");
		return true;
	}

	public boolean endvisit(ThrowStatement s) throws Exception {
		xmlWriter.endTag("ThrowStatement");
		return true;
	}

	public boolean endvisit(TryStatement s) throws Exception {
		xmlWriter.endTag("TryStatement");
		return true;
	}

	public boolean endvisit(TypeReference s) throws Exception {
		xmlWriter.endTag("TypeReference");
		return true;
	}
	
	public boolean endvisit(FullyQualifiedReference s) throws Exception {
		xmlWriter.endTag("FullyQualifiedReference");
		return true;
	}

	public boolean endvisit(NamespaceReference s) throws Exception {
		xmlWriter.endTag("NamespaceReference");
		return true;
	}

	public boolean endvisit(UnaryOperation s) throws Exception {
		xmlWriter.endTag("UnaryOperation");
		return true;
	}

	public boolean endvisit(VariableReference s) throws Exception {
		xmlWriter.endTag("VariableReference");
		return true;
	}

	public boolean endvisit(WhileStatement s) throws Exception {
		xmlWriter.endTag("WhileStatement");
		return true;
	}

	public boolean endvisit(ModuleDeclaration s) throws Exception {
		List<ASTError> errors = ((PHPModuleDeclaration) s).getErrors();
		if (!errors.isEmpty()) {
			xmlWriter.startTag("Errors", null);
			for (ASTError error : errors) {
				error.traverse(this);
			}
			xmlWriter.endTag("Errors");
		}
		xmlWriter.endTag("ModuleDeclaration");
		return true;
	}
	
	public boolean endvisit(NamespaceDeclaration s) throws Exception {
		xmlWriter.endTag("NamespaceDeclaration");
		return true;
	}
	
	public boolean endvisit(UseStatement s) throws Exception {
		xmlWriter.endTag("UseStatement");
		return true;
	}
	
	public boolean endvisit(GotoLabel s) throws Exception {
		xmlWriter.endTag("GotoLabel");
		return true;
	}
	
	public boolean endvisit(GotoStatement s) throws Exception {
		xmlWriter.endTag("GotoStatement");
		return true;
	}
	
	public boolean endvisit(LambdaFunctionDeclaration s) throws Exception {
		xmlWriter.endTag("LambdaFunctionDeclaration");
		return true;
	}

	public boolean visit(ArrayCreation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ArrayCreation", parameters);
		return true;
	}

	public boolean visit(ArrayElement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ArrayElement", parameters);
		return true;
	}

	public boolean visit(ArrayVariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", ArrayVariableReference.getArrayType(s.getArrayType()));
		parameters.put("name", s.getName());
		xmlWriter.startTag("ArrayVariableReference", parameters);
		return true;
	}

	public boolean visit(Assignment s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator());
		xmlWriter.startTag("Assignment", parameters);
		return true;
	}

	public boolean visit(ASTError s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ASTError", parameters);
		return true;
	}

	public boolean visit(BackTickExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("BackTickExpression", parameters);
		return true;
	}

	public boolean visit(BreakStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("BreakStatement", parameters);
		return true;
	}

	public boolean visit(CastExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", CastExpression.getCastType(s.getCastType()));
		xmlWriter.startTag("CastExpression", parameters);
		return true;
	}

	public boolean visit(CatchClause s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("CatchClause", parameters);
		return true;
	}

	public boolean visit(ConstantDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ConstantDeclaration", parameters);
		return true;
	}

	public boolean visit(ClassDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("ClassDeclaration", parameters);
		return true;
	}

	public boolean visit(ClassInstanceCreation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ClassInstanceCreation", parameters);
		return true;
	}

	public boolean visit(CloneExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("CloneExpression", parameters);
		return true;
	}

	public boolean visit(Comment s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", Comment.getCommentType(s.getCommentType()));
		xmlWriter.startTag("Comment", parameters);
		return true;
	}

	public boolean visit(ConditionalExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ConditionalExpression", parameters);

		xmlWriter.startTag("Condition", new HashMap<String, String>());
		s.getCondition().traverse(this);
		xmlWriter.endTag("Condition");

		Expression ifTrue = s.getIfTrue();
		if (ifTrue != null) {
			xmlWriter.startTag("IfTrue", new HashMap<String, String>());
			ifTrue.traverse(this);
			xmlWriter.endTag("IfTrue");
		}

		Expression falseExp = s.getIfFalse();
		if (falseExp != null) {
			xmlWriter.startTag("IfFalse", new HashMap<String, String>());
			falseExp.traverse(this);
			xmlWriter.endTag("IfFalse");
		}

		return false;
	}

	public boolean visit(ConstantReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("ConstantReference", parameters);
		return true;
	}

	public boolean visit(ContinueStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ContinueStatement", parameters);
		return true;
	}

	public boolean visit(DeclareStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("DeclareStatement", parameters);
		return true;
	}

	public boolean visit(Dispatch s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("Dispatch", parameters);
		return true;
	}

	public boolean visit(DoStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("DoStatement", parameters);
		return true;
	}

	public boolean visit(EchoStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("EchoStatement", parameters);
		return true;
	}

	public boolean visit(EmptyStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("EmptyStatement", parameters);
		return true;
	}

	public boolean visit(ExpressionStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ExpressionStatement", parameters);
		return true;
	}

	public boolean visit(FieldAccess s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("FieldAccess", parameters);
		return true;
	}

	public boolean visit(ForEachStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ForEachStatement", parameters);
		return true;
	}

	public boolean visit(FormalParameter s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("isMandatory", Boolean.toString(s.isMandatory()));
		xmlWriter.startTag("FormalParameter", parameters);
		return true;
	}

	public boolean visit(FormalParameterByReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("FormalParameterByReference", parameters);
		return true;
	}

	public boolean visit(ForStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ForStatement", parameters);

		xmlWriter.startTag("Initializations", new HashMap<String, String>());
		for (Expression initialization : s.getInitializations()) {
			initialization.traverse(this);
		}
		xmlWriter.endTag("Initializations");

		xmlWriter.startTag("Conditions", new HashMap<String, String>());
		for (Expression condition : s.getConditions()) {
			condition.traverse(this);
		}
		xmlWriter.endTag("Conditions");

		xmlWriter.startTag("Increasements", new HashMap<String, String>());
		for (Expression increasement : s.getIncreasements()) {
			increasement.traverse(this);
		}
		xmlWriter.endTag("Increasements");

		s.getAction().traverse(this);

		return false;
	}

	public boolean visit(GlobalStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("GlobalStatement", parameters);
		return true;
	}

	public boolean visit(IfStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("IfStatement", parameters);

		xmlWriter.startTag("Condition", new HashMap<String, String>());
		s.getCondition().traverse(this);
		xmlWriter.endTag("Condition");

		xmlWriter.startTag("TrueStatement", new HashMap<String, String>());
		s.getTrueStatement().traverse(this);
		xmlWriter.endTag("TrueStatement");

		Statement falseStatement = s.getFalseStatement();
		if (falseStatement != null) {
			xmlWriter.startTag("FalseStatement", new HashMap<String, String>());
			falseStatement.traverse(this);
			xmlWriter.endTag("FalseStatement");
		}

		return false;
	}

	public boolean visit(IgnoreError s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("IgnoreError", parameters);
		return true;
	}

	public boolean visit(Include s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", s.getType());
		xmlWriter.startTag("Include", parameters);
		return true;
	}

	public boolean visit(InfixExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator());
		xmlWriter.startTag("InfixExpression", parameters);
		return true;
	}

	public boolean visit(InstanceOfExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("InstanceOfExpression", parameters);
		return true;
	}

	public boolean visit(InterfaceDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("InterfaceDeclaration", parameters);
		return true;
	}

	public boolean visit(ListVariable s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ListVariable", parameters);
		return true;
	}

	public boolean visit(PHPCallArgumentsList s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPCallArgumentsList", parameters);
		return true;
	}

	public boolean visit(PHPCallExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPCallExpression", parameters);
		return true;
	}

	public boolean visit(PHPDocBlock s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("shortDescription", s.getShortDescription());
		xmlWriter.startTag("PHPDocBlock", parameters);
		return true;
	}

	public boolean visit(PHPDocTag s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("tagKind", PHPDocTag.getTagKind(s.getTagKind()));
		parameters.put("value", s.getValue());
		xmlWriter.startTag("PHPDocTag", parameters);
		return true;
	}

	public boolean visit(PHPFieldDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("PHPFieldDeclaration", parameters);
		return true;
	}

	public boolean visit(PHPMethodDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("PHPMethodDeclaration", parameters);
		return true;
	}

	public boolean visit(PostfixExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator());
		xmlWriter.startTag("PostfixExpression", parameters);
		return true;
	}

	public boolean visit(PrefixExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator());
		xmlWriter.startTag("PrefixExpression", parameters);
		return true;
	}

	public boolean visit(Quote s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", Quote.getType(s.getQuoteType()));
		xmlWriter.startTag("Quote", parameters);
		return true;
	}

	public boolean visit(ReferenceExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReferenceExpression", parameters);
		return true;
	}

	public boolean visit(ReflectionArrayVariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionArrayVariableReference", parameters);
		return true;
	}

	public boolean visit(ReflectionCallExpression s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionCallExpression", parameters);
		return true;
	}

	public boolean visit(ReflectionStaticMethodInvocation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionStaticMethodInvocation", parameters);
		return true;
	}

	public boolean visit(ReflectionVariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReflectionVariableReference", parameters);
		return true;
	}

	public boolean visit(ReturnStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ReturnStatement", parameters);
		return true;
	}

	public boolean visit(Scalar s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("type", s.getType());
		parameters.put("value", s.getValue());
		xmlWriter.startTag("Scalar", parameters);
		return true;
	}

	public boolean visit(SimpleReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("SimpleReference", parameters);
		return true;
	}

	public boolean visit(StaticConstantAccess s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticConstantAccess", parameters);
		return true;
	}

	public boolean visit(StaticDispatch s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticDispatch", parameters);
		return true;
	}

	public boolean visit(StaticFieldAccess s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticFieldAccess", parameters);
		return true;
	}

	public boolean visit(StaticMethodInvocation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticMethodInvocation", parameters);
		return true;
	}

	public boolean visit(StaticStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("StaticStatement", parameters);
		return true;
	}

	public boolean visit(SwitchCase s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("SwitchCase", parameters);
		return true;
	}

	public boolean visit(SwitchStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("SwitchStatement", parameters);
		return true;
	}

	public boolean visit(ThrowStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ThrowStatement", parameters);
		return true;
	}

	public boolean visit(TryStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("TryStatement", parameters);
		return true;
	}

	public boolean visit(TypeReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("TypeReference", parameters);
		return true;
	}
	
	public boolean visit(FullyQualifiedReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getFullyQualifiedName());
		xmlWriter.startTag("FullyQualifiedReference", parameters);
		return true;
	}
	
	public boolean visit(NamespaceReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		parameters.put("global", Boolean.toString(s.isGlobal()));
		parameters.put("local", Boolean.toString(s.isGlobal()));
		xmlWriter.startTag("NamespaceReference", parameters);
		return true;
	}

	public boolean visit(UnaryOperation s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("operator", s.getOperator());
		xmlWriter.startTag("UnaryOperation", parameters);
		return true;
	}

	public boolean visit(VariableReference s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("VariableReference", parameters);
		return true;
	}

	public boolean visit(WhileStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("WhileStatement", parameters);
		return true;
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("ModuleDeclaration", parameters);
		return true;
	}

	public boolean visit(NamespaceDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("name", s.getName());
		xmlWriter.startTag("NamespaceDeclaration", parameters);
		return true;
	}

	public boolean visit(UseStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("UseStatement", parameters);

		xmlWriter.startTag("Parts", new HashMap<String, String>());
		for (UsePart p : s.getParts()) {
			p.traverse(this);
		}
		xmlWriter.endTag("Parts");
		return true;
	}
	
	public boolean visit(UsePart s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		xmlWriter.startTag("UsePart", parameters);
		s.getNamespace().traverse(this);
		if (s.getAlias() != null) {
			s.getAlias().traverse(this);
		}
		xmlWriter.endTag("UsePart");
		return true;
	}
	
	public boolean visit(GotoLabel s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("label", s.getLabel());
		xmlWriter.startTag("GotoLabel", parameters);
		return true;
	}

	public boolean visit(GotoStatement s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("label", s.getLabel());
		xmlWriter.startTag("GotoStatement", parameters);
		return true;
	}

	public boolean visit(LambdaFunctionDeclaration s) throws Exception {
		Map<String, String> parameters = createInitialParameters(s);
		parameters.put("isReference", Boolean.toString(s.isReference()));
		xmlWriter.startTag("LambdaFunctionDeclaration", parameters);
		
		xmlWriter.startTag("Arguments", new HashMap<String, String>());
		for (FormalParameter p : s.getArguments()) {
			p.traverse(this);
		}
		xmlWriter.endTag("Arguments");
		
		Collection<? extends Expression> lexicalVars = s.getLexicalVars();
		if (lexicalVars != null) {
			xmlWriter.startTag("LexicalVars", new HashMap<String, String>());
			for (Expression var : lexicalVars) {
				var.traverse(this);
			}
			xmlWriter.endTag("LexicalVars");
		}
		
		s.getBody().traverse(this);
		
		return false;
	}
}
