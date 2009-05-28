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
package org.eclipse.php.internal.core.mixin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.mixin.IMixinRequestor.ElementInfo;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * <p>This class builds mixin model. The keys format is the following:</p>
 * <ul>
 * 	<li><b>{function</b>			Global Function</li>
 * 	<li><b>{constant@</b>			Global Constant</li>
 * 	<li><b>{$variable</b>			Global Variable</li>
 * 	<li><b>class%</b>				Class Declaration</li>
 * 	<li><b>interface&gt;</b>		Interface Declaration</li>
 * 	<li><b>{class%{method</b>		Class or Interface Method</li>
 * 	<li><b>{class%{$field</b>		Class or Interface Variable</li>
 * 	<li><b>{class%{constant@</b>	Class or Interface Constant</li>
 * </ul>
 * 
 * @author michael
 */
public class PHPMixinBuildVisitor extends ASTVisitor {

	//	private ModuleDeclaration module;
	private ISourceModule sourceModule;
	private boolean moduleAvailable;
	private IMixinRequestor requestor;
	private SourceModuleScope sourceModuleScope;
	private Stack<ASTNode> parentStack = new Stack<ASTNode>();

	/** Global variables stack */
	private Stack<Set<String>> globalVariables = new Stack<Set<String>>();

	private abstract class Scope {

		public Scope() {
			super();
		}

		public abstract void reportVariable(String name, IField object);

		public abstract void reportConstant(String name, IField object);

		public abstract void reportInclude(String filePath, Include object);
	}

	private class SourceModuleScope extends Scope {

		public void reportVariable(String name, IField object) {
			// Report global variable:
			report(new StringBuilder(MixinModel.SEPARATOR).append(name).toString(), PHPMixinElementInfo.createVariable(object));
		}

		public void reportConstant(String name, IField object) {
			// Report global constant:
			report(new StringBuilder(MixinModel.SEPARATOR).append(name).append(PHPMixinParser.CONSTANT_SUFFIX).toString(), PHPMixinElementInfo.createConstant(object));
		}

		public void reportInclude(String filePath, Include include) {
			// Report include(), require(), require_once() and include_once():

			IncludeField object = new IncludeField((ModelElement) sourceModule, filePath);
			object.setOffset(include.getExpr().sourceStart());
			object.setLength(include.getExpr().sourceEnd() - include.getExpr().sourceStart());

			int i = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
			if (i >= 0) {
				filePath = filePath.substring(i + 1);
			}
			report(new StringBuilder(filePath).append(PHPMixinParser.INCLUDE_SUFFIX).toString(), PHPMixinElementInfo.createInclude(object));
		}
	}

	/**
	 * Constructs mixin build visitor
	 * @param module
	 * @param sourceModule
	 * @param moduleAvailable
	 * @param requestor
	 */
	public PHPMixinBuildVisitor(ModuleDeclaration module, ISourceModule sourceModule, boolean moduleAvailable, IMixinRequestor requestor) {
		//		this.module = module;
		this.sourceModule = sourceModule;
		this.moduleAvailable = moduleAvailable;
		this.requestor = requestor;
	}

	protected String report(String key, PHPMixinElementInfo object) {
		PHPMixinModel.clearKeysCache(key);
		ElementInfo info = new IMixinRequestor.ElementInfo();
		info.key = key;
		info.object = object;
		if (requestor != null) {
			requestor.reportElement(info);
		}
		return key;
	}

	/**
	 * Report variable declaration in the given scope
	 * @param var Variable declaration. Can either contain dollar or not (in case of field access declaration)
	 * @return new model key
	 * @throws Exception
	 */
	protected void reportVariableDeclaration(SimpleReference var) throws Exception {
		// Check whether this variable is global (that means it was previously declared as global):
		boolean globalVariable = false;
		if (!parentStack.isEmpty()) {
			ASTNode parent = parentStack.peek();
			if (parent instanceof MethodDeclaration) {
				if (var instanceof VariableReference) {
					Set<String> globalVars = globalVariables.peek();
					if (globalVars.contains(var.getName())) {
						globalVariable = true;
					}
				}
			} else if (parent instanceof ModuleDeclaration) {
				globalVariable = true;
			}
		}
		if (!globalVariable) {
			return;
		}

		IField obj = null;
		String name = var.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(var);
			if (element instanceof IField) {
				obj = (IField) element;
			}
		}
		if (obj == null && sourceModule != null) {
			obj = new FakeField((ModelElement) sourceModule, name, var.sourceStart(), var.sourceEnd() - var.sourceStart());
		}

		sourceModuleScope.reportVariable(name, obj);
	}

	protected IModelElement findModelElementFor(ASTNode decl) throws ModelException {
		return sourceModule.getElementAt(decl.sourceStart() + 1);
	}

	public boolean visit(Statement node) throws Exception {
		if (node instanceof ConstantDeclaration) {
			return visit((ConstantDeclaration) node);
		}
		if (node instanceof PHPFieldDeclaration) {
			return visit((PHPFieldDeclaration) node);
		}
		if (node instanceof FormalParameter) {
			return visit((FormalParameter) node);
		}
		if (node instanceof CatchClause) {
			return visit((CatchClause) node);
		}
		if (node instanceof GlobalStatement) {
			return visit((GlobalStatement) node);
		}
		if (node instanceof ForEachStatement) {
			return visit((ForEachStatement) node);
		}
		return visitGeneral(node);
	}

	public boolean visit(Expression expr) throws Exception {
		if (expr instanceof CallExpression) {
			return visit((CallExpression) expr);
		}
		if (expr instanceof Assignment) {
			return visit((Assignment) expr);
		}
		if (expr instanceof ListVariable) {
			return visit((ListVariable) expr);
		}
		if (expr instanceof Include) {
			return visit((Include) expr);
		}
		return visitGeneral(expr);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(CallExpression expr) throws Exception {
		if ("define".equalsIgnoreCase(expr.getName())) { //$NON-NLS-1$
			// report global constant:
			IField obj = null;
			List args = expr.getArgs().getChilds();
			if (args.size() > 1) {
				ASTNode firstArg = (ASTNode) args.get(0);
				if (firstArg instanceof Scalar) {
					Scalar constant = (Scalar) firstArg;
					String name = ASTUtils.stripQuotes(constant.getValue());
					if (moduleAvailable) {
						IModelElement element = findModelElementFor(constant);
						if (element instanceof IField) {
							obj = (IField) element;
						}
					}
					if (sourceModule != null) {
						obj = new FakeField((ModelElement) sourceModule, name, constant.sourceStart(), constant.sourceEnd() - constant.sourceStart(), Modifiers.AccConstant);
					}
					sourceModuleScope.reportConstant(name, obj);
				}
			}
		}
		return visitGeneral(expr);
	}

	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof VariableReference) { // local variable ($a = ...)
			reportVariableDeclaration((VariableReference) left);
		} else if (left instanceof FieldAccess) { // class variable ($this->a)
			FieldAccess fieldAccess = (FieldAccess) left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					reportVariableDeclaration((SimpleReference) field);
				}
			}
		}
		return visitGeneral(assignment);
	}

	public boolean visit(ListVariable list) throws Exception {
		for (Expression variable : list.getVariables()) {
			if (variable instanceof VariableReference) { // list variable (list($a, ...) = ...)
				reportVariableDeclaration((VariableReference) variable);
			}
		}
		return visitGeneral(list);
	}

	public boolean visit(FormalParameter parameter) throws Exception {
		reportVariableDeclaration(parameter.getParameterName());
		return visitGeneral(parameter);
	}

	public boolean visit(ConstantDeclaration decl) throws Exception {
		IField obj = null;
		String name = decl.getConstantName().getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(decl);
			obj = (IField) element;
		}
		sourceModuleScope.reportConstant(ASTUtils.stripQuotes(name), obj);
		return visitGeneral(decl);
	}

	public boolean visit(CatchClause clause) throws Exception {
		VariableReference variable = clause.getVariable();
		reportVariableDeclaration(variable); // catch(Exception $a)
		return visitGeneral(clause);
	}

	public boolean visit(GlobalStatement statement) throws Exception {
		for (Expression variable : statement.getVariables()) {
			if (variable instanceof VariableReference) { // global $a, $b

				VariableReference var = (VariableReference) variable;

				// Add this variable reference to the list of current global variables
				Set<String> globalVars = globalVariables.peek();
				globalVars.add(var.getName());

				// We do not handle global statement as variable declaration:
				//	reportVariableDeclaration(var, sourceModuleScope);
			}
		}
		return visitGeneral(statement);
	}

	public boolean visit(ForEachStatement statement) throws Exception {
		Expression key = statement.getKey();//foreach ($list as $key => $value)
		if (key instanceof VariableReference) {
			reportVariableDeclaration((VariableReference) key);
		}
		Expression value = statement.getValue();
		if (value instanceof VariableReference) {
			reportVariableDeclaration((VariableReference) value);
		}
		return visitGeneral(statement);
	}

	public boolean visit(PHPFieldDeclaration decl) throws Exception {
		reportVariableDeclaration(decl.getRef());
		return visitGeneral(decl);
	}

	public boolean visit(Include include) throws Exception {
		Expression expr = include.getExpr();
		if (expr instanceof Scalar) {
			sourceModuleScope.reportInclude(ASTUtils.stripQuotes(((Scalar) expr).getValue()), include);
		}
		return visitGeneral(include);
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		sourceModuleScope = new SourceModuleScope();
		parentStack.push(s);
		globalVariables.push(new HashSet<String>());
		return visitGeneral(s);
	}

	public boolean endvisit(ModuleDeclaration s) throws Exception {
		parentStack.pop();
		globalVariables.pop();
		endvisitGeneral(s);
		return true;
	}

	public boolean visit(MethodDeclaration decl) throws Exception {
		parentStack.push(decl);
		globalVariables.push(new HashSet<String>());
		return visitGeneral(decl);
	}

	public boolean endvisit(MethodDeclaration decl) throws Exception {
		parentStack.pop();
		globalVariables.pop();
		endvisitGeneral(decl);
		return true;
	}

	public boolean visit(TypeDeclaration decl) throws Exception {
		parentStack.push(decl);
		return visitGeneral(decl);
	}

	public boolean endvisit(TypeDeclaration decl) throws Exception {
		parentStack.pop();
		endvisitGeneral(decl);
		return true;
	}

	public static String restoreKeyByNode(ISourceModule sourceModule, ModuleDeclaration unit, final ASTNode node) {
		final String elementKey[] = new String[1];

		PHPMixinBuildVisitor visitor = new PHPMixinBuildVisitor(unit, sourceModule, false, null) {
			private String tmpKey;

			protected String report(String key, PHPMixinElementInfo object) {
				tmpKey = key;
				return super.report(key, object);
			}

			public boolean visitGeneral(ASTNode n) throws Exception {
				if (elementKey[0] != null) {
					return false;
				}
				if (node == n) {
					elementKey[0] = tmpKey;
				}
				return super.visitGeneral(node);
			}
		};
		try {
			unit.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}

		return elementKey[0];
	}
}
