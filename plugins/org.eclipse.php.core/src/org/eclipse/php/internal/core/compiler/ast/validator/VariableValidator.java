/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.validator;

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.validator.IValidatorExtension;
import org.eclipse.php.core.compiler.ast.validator.IValidatorVisitor;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.parser.PHPProblemIdentifier;
import org.eclipse.php.internal.core.language.PHPVariables;

public class VariableValidator implements IValidatorExtension {
	private IBuildContext context;

	private IValidatorVisitor validator;

	private String[] globals = null;

	private List<VarComment> varCommentList;

	private List<PHPDocBlock> docBlocks;

	private Deque<Scope> scopes = new ArrayDeque<>();
	private Deque<Operation> operations = new ArrayDeque<>();
	private Scope current;
	private int depth = 0;
	private Deque<Integer> classDeclarations = new ArrayDeque<>();
	private int inClassDecl = -1;

	final private static String THIS_VAR = "$this"; //$NON-NLS-1$
	final private static String VALUE_VAR = "$value"; //$NON-NLS-1$
	final private static char DOLLAR = '$';
	final private static char AMP = '&';
	final private static String COMPACT = "compact"; // $NON-NLS-N$

	@Override
	public void init(IBuildContext buildContext, IValidatorVisitor validator) {
		this.context = buildContext;
		this.validator = validator;
	}

	@Override
	public void visit(ASTNode s) throws Exception {
		if (s instanceof PHPModuleDeclaration) {
			s.traverse(new VariableValidatorVisitor());
		}
	}

	@Override
	public void endvisit(ASTNode s) throws Exception {

	}

	@Override
	public boolean isSupported(IBuildContext buildContext) {
		return true;
	}

	@Override
	public boolean skipProblem(int start, int end, String message, IProblemIdentifier id) {
		return false;
	}

	@Override
	public boolean skipProblem(ASTNode node, String message, IProblemIdentifier id) {
		return false;
	}

	private enum Operation {
		ASSIGN, USE, USE_FIELD;
	}

	private class Scope {

		public Map<String, Variable> variables = new HashMap<>();
		public int start;
		public int end;
		public boolean global;

		public Scope(int start, int end, boolean global) {
			this.start = start;
			this.end = end;
			this.global = global;
		}

		void copy(Scope scope) {
			for (Entry<String, Variable> entry : scope.variables.entrySet()) {
				variables.put(entry.getKey(), new ImportedVariable(entry.getValue()));
			}
		}

		boolean contains(String name, int offset) {
			if (variables.containsKey(name)) {
				return true;
			}

			for (VarComment varComment : varCommentList) {
				if (varComment.sourceEnd() < current.start || varComment.sourceStart() > current.end) {
					continue;
				}
				if (varComment.sourceStart() > offset || end < varComment.sourceStart()) {
					continue;
				}
				if (varComment.getVariableReference().getName().equals(name)) {
					this.variables.put(name, new DocVariable(varComment));

					return true;
				}
			}

			for (PHPDocBlock block : docBlocks) {
				if (block.sourceEnd() < current.start || block.sourceStart() > current.end) {
					continue;
				}
				if (block.sourceStart() > offset || end < block.sourceStart()) {
					continue;
				}

				for (PHPDocTag tag : block.getTags(PHPDocTag.TagKind.VAR)) {
					if (tag.isValidVarTag() && tag.getVariableReference() != null
							&& tag.getVariableReference().getName().equals(name)) {
						this.variables.put(name, new DocVariable(block));
						return true;
					}
				}
			}

			return false;
		}
	}

	private class Variable {
		public int start;
		public int end;
		private int initialized = -1;
		private int used = -1;
		public TreeMap<Integer, Integer> other = new TreeMap<>();

		Variable() {
		}

		Variable(ASTNode node) {
			start = node.start();
			end = node.end();
		}

		Variable(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public int initialized() {
			return initialized;
		}

		public void setInitialized(int init) {
			initialized = init;
		}

		public int used() {
			return used;
		}

		public void setUsed(int used) {
			this.used = used;
		}

		public void addAddress(ASTNode node) {
			other.put(node.start(), node.end());
		}

		public void addAddress(int start, int end) {
			other.put(start, end);
		}
	}

	private class DocVariable extends Variable {
		public DocVariable(Comment varComment) {
			super(varComment.sourceStart(), varComment.sourceEnd());
			setInitialized(start);
		}

	}

	private class ImportedVariable extends Variable {
		public Variable parent;

		ImportedVariable(Variable variable) {
			parent = variable;
			start = variable.start;
			end = variable.end;
		}

		ImportedVariable(ASTNode node) {
			parent = new Variable(node);
			start = parent.start;
			end = parent.end;
		}

		@Override
		public int used() {
			return parent.used();
		}

		@Override
		public void setUsed(int used) {
			parent.setUsed(used);
		}

		@Override
		public int initialized() {
			return parent.initialized();
		}

		@Override
		public void setInitialized(int init) {
			parent.setInitialized(init);
		}

		@Override
		public void addAddress(ASTNode node) {
			parent.addAddress(node);
		}

	}

	private class VariableValidatorVisitor extends PHPASTVisitor {
		@Override
		public boolean visit(ModuleDeclaration s) throws Exception {
			PHPModuleDeclaration module = (PHPModuleDeclaration) s;
			pushScope(0, module.end(), true);
			List<VarComment> varComments = module.getVarComments();
			varCommentList = new ArrayList<>(module.getVarComments().size());
			varCommentList.addAll(varComments);
			Collections.sort(varCommentList, new Comparator<VarComment>() {

				@Override
				public int compare(VarComment o1, VarComment o2) {
					return o2.sourceStart() - o1.sourceStart();
				}
			});

			docBlocks = new ArrayList<>(module.getPHPDocBlocks().size());
			docBlocks.addAll(module.getPHPDocBlocks());
			Collections.sort(docBlocks, new Comparator<PHPDocBlock>() {

				@Override
				public int compare(PHPDocBlock o1, PHPDocBlock o2) {
					return o2.sourceStart() - o1.sourceStart();
				}
			});
			return true;
		}

		@Override
		public boolean endvisit(ModuleDeclaration s) throws Exception {
			popScope();
			return false;
		}

		@Override
		public boolean visit(PropertyHook s) throws Exception {
			if (s.getBody() == null) {
				return false;
			}
			pushScope(s.sourceStart(), s.sourceEnd());
			Variable v = new ImportedVariable(s);
			v.setInitialized(s.start());
			current.variables.put(THIS_VAR, v);

			if (s.getArguments() != null) {
				for (FormalParameter par : s.getArguments()) {
					v = new ImportedVariable(par);
					v.setInitialized(par.start());
					current.variables.put(par.getName(), v);
				}
			} else {
				v = new ImportedVariable(s);
				v.setInitialized(s.start());
				current.variables.put(VALUE_VAR, v);
			}
			s.getBody().traverse(this);
			popScope();
			return false;
		}

		@Override
		public boolean visit(PHPMethodDeclaration node) throws Exception {
			if (node.isAbstract() || node.getBody() == null) {
				return false;
			}
			pushScope(node.sourceStart(), node.sourceEnd());
			for (Object o : node.getArguments()) {
				if (o instanceof FormalParameter) {
					VariableReference parameterName = ((FormalParameter) o).getParameterName();
					Variable v = new ImportedVariable(parameterName);
					v.setInitialized(((FormalParameter) o).start());
					current.variables.put(parameterName.getName(), v);
				}
			}
			if (inClassDecl + 1 == depth && !node.isStatic()) {
				Variable v = new ImportedVariable(node);
				v.setInitialized(node.start());
				current.variables.put(THIS_VAR, v);
			}
			if (node.getBody() != null) {
				node.getBody().traverse(this);
			}
			popScope();
			return false;
		}

		@Override
		public boolean visit(ArrowFunctionDeclaration decl) throws Exception {
			Scope prev = current;
			pushScope(decl.sourceStart(), decl.sourceEnd());
			for (Entry<String, Variable> v : prev.variables.entrySet()) {
				current.variables.put(v.getKey(), new ImportedVariable(v.getValue()));
			}
			for (Object o : decl.getArguments()) {
				if (o instanceof FormalParameter) {
					VariableReference parameterName = ((FormalParameter) o).getParameterName();
					Variable v = new ImportedVariable(parameterName);
					v.setInitialized(((FormalParameter) o).start());
					current.variables.put(parameterName.getName(), v);
				}
			}

			if (inClassDecl < depth && prev.contains(THIS_VAR, 0)) { // $NON-NLS-1$
				current.variables.put(THIS_VAR, prev.variables.get(THIS_VAR)); // $NON-NLS-1$
			}
			if (decl.getBody() != null) {
				decl.getBody().traverse(this);
			}

			popScope();

			return false;
		}

		@Override
		public boolean visit(LambdaFunctionDeclaration decl) throws Exception {
			Scope prev = current;
			pushScope(decl.sourceStart(), decl.sourceEnd());
			for (Object o : decl.getArguments()) {
				if (o instanceof FormalParameter) {
					VariableReference parameterName = ((FormalParameter) o).getParameterName();
					Variable v = new ImportedVariable(parameterName);
					v.setInitialized(((FormalParameter) o).start());
					current.variables.put(parameterName.getName(), v);
				}
			}
			if (decl.getLexicalVars() != null) {
				for (Expression var : decl.getLexicalVars()) {
					ReferenceExpression isRef = null;
					if (var instanceof ReferenceExpression) {
						isRef = (ReferenceExpression) var;
						var = isRef.getVariable();
					}
					if (var instanceof VariableReference) {
						final String name = ((VariableReference) var).getName();
						if (prev.contains(name, var.sourceStart())) {
							current.variables.put(name, new ImportedVariable(prev.variables.get(name)));
						} else {
							if (isRef == null) {
								validator.reportProblem(var, NLS.bind(Messages.VariableValidator_IsUndefined, name),
										PHPProblemIdentifier.UndefinedVariable, ProblemSeverity.WARNING);
							} else {
								Variable tmp = new Variable(var);
								tmp.setInitialized(isRef.start());
								prev.variables.put(name, tmp);
								current.variables.put(name, new ImportedVariable(tmp));
							}

						}
					}
				}
			}
			if (inClassDecl < depth && prev.contains(THIS_VAR, 0)) { // $NON-NLS-1$
				current.variables.put(THIS_VAR, prev.variables.get(THIS_VAR)); // $NON-NLS-1$
			}
			if (decl.getBody() != null) {
				decl.getBody().traverse(this);
			}

			popScope();

			return false;
		}

		public boolean visit(ReferenceExpression ref) throws Exception {

			if (ref.getVariable() instanceof VariableReference) {
				VariableReference var = (VariableReference) ref.getVariable();
				if (!current.variables.containsKey(var.getName())) {
					current.variables.put(var.getName(), new Variable(ref));
				}
			}

			return true;
		}

		@Override
		public boolean visit(ReflectionCallExpression node) throws Exception {
			operations.push(Operation.USE);

			return super.visit(node);
		}

		@Override
		public boolean endvisit(ReflectionCallExpression s) throws Exception {
			operations.pop();
			return super.endvisit(s);
		}

		@Override
		public boolean visit(PHPCallExpression ex) throws Exception {
			operations.push(Operation.USE);

			if (ex.getReceiver() == null && ex.getCallName() != null && ex.getCallName().getName().equals(COMPACT)
					&& ex.getArgs() != null) {
				for (ASTNode node : ex.getArgs().getChilds()) {
					if (node instanceof Scalar && ((Scalar) node).getScalarType() == Scalar.TYPE_STRING) {
						String name = DOLLAR + ASTUtils.stripQuotes(((Scalar) node).getValue());
						if (current.contains(name, node.sourceStart())) {
							current.variables.get(name).setUsed(node.start());
						}
					}
				}
			}

			return super.visit(ex);
		}

		@Override
		public boolean endvisit(PHPCallExpression ex) throws Exception {
			operations.pop();

			return super.endvisit(ex);
		}

		private void endScope(Scope scope) {
			for (Entry<String, Variable> entry : scope.variables.entrySet()) {
				Variable value = entry.getValue();
				if (value.used() < 0 && !(value instanceof ImportedVariable) && !(value instanceof DocVariable)) {
					String mess = NLS.bind(Messages.VariableValidator_IsNeverUsed, entry.getKey());
					validator.reportProblem(value.start, value.end, mess, PHPProblemIdentifier.UnusedVariable,
							ProblemSeverity.WARNING);
				}
			}
		}

		@Override
		public boolean visit(NamespaceDeclaration s) throws Exception {
			pushScope(s.sourceStart(), s.sourceEnd(), true);
			return true;
		}

		@Override
		public boolean endvisit(NamespaceDeclaration s) throws Exception {
			popScope();
			return false;
		}

		@Override
		public boolean visit(ForEachStatement s) throws Exception {
			if (s.getExpression() != null) {
				s.getExpression().traverse(this);
			}
			operations.push(Operation.ASSIGN);
			if (s.getKey() != null) {
				s.getKey().traverse(this);
			}
			if (s.getValue() != null) {
				s.getValue().traverse(this);
			}
			operations.pop();

			if (s.getStatement() != null) {
				s.getStatement().traverse(this);
			}

			return false;
		}

		private String getName(String name) {
			if (name.length() > 0 && name.charAt(0) == AMP) {
				name = name.substring(1);
			}

			return name;
		}

		@Override
		public boolean visit(VariableReference s) throws Exception {

			String name = getName(s.getName());
			if (name.charAt(0) != DOLLAR || isSuperGlobal(name)) {
				return false;
			}
			if (s.getVariableKind() == PHPVariableKind.GLOBAL && isGlobal(name)) {
				return false;
			} else if (s.getVariableKind() != PHPVariableKind.LOCAL && s.getVariableKind() != PHPVariableKind.GLOBAL
					&& s.getVariableKind() != PHPVariableKind.UNKNOWN) {
				return false;
			}

			check(name, s.start(), s.end());

			return true;
		}

		@Override
		public boolean visit(StaticStatement s) throws Exception {
			operations.push(Operation.ASSIGN);
			for (Expression expr : s.getExpressions()) {
				expr.traverse(this);
			}
			operations.pop();
			return false;
		}

		protected void check(String name, int start, int end) {
			if (current.global && isGlobal(name)) {
				return;
			}
			current.contains(name, start);
			Variable var = current.variables.get(name);

			if (isInit()) {
				if (var == null) {
					var = new Variable(start, end);
					current.variables.put(name, var);
				} else {
					var.addAddress(start, end);
				}
				if (var.initialized() < 0) {
					var.setInitialized(start);
				}
			}
			if (var == null) {
				validator.reportProblem(start, end, NLS.bind(Messages.VariableValidator_IsUndefined, name),
						PHPProblemIdentifier.UndefinedVariable, ProblemSeverity.WARNING);
			} else if (!isInit()) {
				var.setUsed(start);
			}
		}

		@Override
		public boolean visit(ArrayVariableReference s) throws Exception {
			String name = getName(s.getName());
			if (name.charAt(0) != DOLLAR || isSuperGlobal(name)
					|| (s.getVariableKind() == PHPVariableKind.GLOBAL && isGlobal(name))) {
				if (s.getIndex() != null) {
					operations.push(Operation.USE);
					s.getIndex().traverse(this);
					operations.pop();
				}
				return false;
			}
			check(name, s.start(), s.start() + s.getName().length());
			if (s.getIndex() != null) {
				operations.push(Operation.USE);
				s.getIndex().traverse(this);
				operations.pop();
			}

			return false;
		}

		@Override
		public boolean visit(ReflectionArrayVariableReference s) throws Exception {
			/*
			 * if (s.getExpression() instanceof Scalar) { Scalar name = (Scalar)
			 * s.getExpression(); if (name.getScalarType() ==
			 * Scalar.TYPE_STRING) { String dolarName = DOLLAR +
			 * name.getValue(); check(dolarName, name.start(), name.end()); } }
			 */
			operations.push(Operation.USE);

			return true;
		}

		@Override
		public boolean endvisit(ReflectionArrayVariableReference s) throws Exception {
			operations.pop();

			return super.endvisit(s);
		}

		@Override
		public boolean visit(ReflectionVariableReference s) throws Exception {
			if (s.getExpression() instanceof Scalar && operations.peekFirst() != Operation.USE_FIELD) {
				Scalar name = (Scalar) s.getExpression();
				if (name.getScalarType() == Scalar.TYPE_STRING) {
					String dolarName = DOLLAR + ASTUtils.stripQuotes(name.getValue());
					check(dolarName, name.start(), name.end());
				}
			}
			operations.push(Operation.USE);

			return true;
		}

		@Override
		public boolean endvisit(ReflectionVariableReference s) throws Exception {
			operations.pop();
			return super.endvisit(s);
		}

		@Override
		public boolean visit(FieldAccess s) throws Exception {
			if (s.getDispatcher() != null) {
				operations.push(Operation.USE);
				s.getDispatcher().traverse(this);
				operations.pop();
			}
			if (s.getField() != null) {
				operations.push(Operation.USE_FIELD);
				s.getField().traverse(this);
				operations.pop();
			}

			return false;
		}

		@Override
		public boolean visit(StaticFieldAccess s) throws Exception {
			if (s.getDispatcher() != null) {
				operations.push(Operation.USE);
				s.getDispatcher().traverse(this);
				operations.pop();
			}
			if (s.getField() != null) {
				if (s.getField() instanceof ArrayVariableReference) {
					ArrayVariableReference ref = (ArrayVariableReference) s.getField();
					if (ref.getIndex() != null) {
						operations.push(Operation.USE);
						ref.getIndex().traverse(this);
						operations.pop();
					}
				} else if (!(s.getField() instanceof VariableReference)) {
					s.getField().traverse(this);
				}
			}
			return false;
		}

		@Override
		public boolean visit(CatchClause s) throws Exception {
			Scope prev = current;
			pushScope(s.sourceStart(), s.sourceEnd());
			current.copy(prev);
			try {
				if (s.getVariable() != null) {
					Variable v = new Variable(s.getVariable());
					v.setInitialized(s.start());
					v.setUsed(s.start());
					current.variables.put(s.getVariable().getName(), v);
				}
				if (s.getStatement() != null) {
					s.getStatement().traverse(this);
				}
			} finally {
				popScope();
			}

			return false;
		}

		@Override
		public boolean visit(Assignment s) throws Exception {
			operations.push(Operation.USE);
			s.getValue().traverse(this);
			operations.pop();
			operations.push(Operation.ASSIGN);
			s.getVariable().traverse(this);
			operations.pop();
			return false;
		};

		private boolean isInit() {
			return !operations.isEmpty() && operations.peekFirst() == Operation.ASSIGN;
		}

		@Override
		public boolean visit(GlobalStatement s) throws Exception {
			for (Expression el : s.getVariables()) {
				if (el instanceof VariableReference) {
					VariableReference ref = (VariableReference) el;
					if (scopes.size() > 1) {
						Scope parentScope = scopes.peekLast();
						if (parentScope.contains(ref.getName(), ref.sourceStart())) {
							current.variables.put(ref.getName(),
									new ImportedVariable(parentScope.variables.get(ref.getName())));
							continue;
						}
					}
					Variable var = current.variables.get(ref.getName());
					if (var == null) {
						var = new Variable(ref);
						current.variables.put(ref.getName(), var);
					}
					if (var.initialized < 0) {
						var.initialized = ref.start();
					}
				} else {
					operations.push(Operation.ASSIGN);
					el.traverse(this);
					operations.pop();
				}
			}
			return false;
		}

		private void enterType() {
			inClassDecl = depth;
			classDeclarations.push(depth);
		}

		private void exitType() {
			classDeclarations.pop();
			inClassDecl = classDeclarations.isEmpty() ? -1 : classDeclarations.peekFirst();
		}

		@Override
		public boolean visit(EnumDeclaration s) throws Exception {
			enterType();
			return true;
		}

		@Override
		public boolean endvisit(EnumDeclaration s) throws Exception {
			exitType();
			return false;
		}

		@Override
		public boolean visit(ClassDeclaration s) throws Exception {
			if (s.isInterface()) {
				return false;
			}
			enterType();
			return true;
		}

		@Override
		public boolean endvisit(ClassDeclaration s) throws Exception {
			exitType();
			return false;
		}

		@Override
		public boolean visit(TraitDeclaration s) throws Exception {
			enterType();
			return true;
		}

		@Override
		public boolean endvisit(TraitDeclaration s) throws Exception {
			exitType();
			return false;
		}

		@Override
		public boolean visit(AnonymousClassDeclaration s) throws Exception {
			enterType();
			return true;
		}

		@Override
		public boolean endvisit(AnonymousClassDeclaration s) throws Exception {
			exitType();
			return false;
		}

		@Override
		public boolean visit(InfixExpression s) throws Exception {
			operations.push(Operation.USE);
			return true;
		}

		@Override
		public boolean endvisit(InfixExpression s) throws Exception {
			operations.pop();
			return false;
		}

		@Override
		public boolean visit(ConditionalExpression s) throws Exception {
			operations.push(Operation.USE);

			return true;
		}

		@Override
		public boolean endvisit(ConditionalExpression s) throws Exception {
			operations.pop();

			return false;
		}

		private Scope pushScope(int start, int end) {
			return pushScope(start, end, false);
		}

		private Scope pushScope(int start, int end, boolean global) {
			current = new Scope(start, end, global);
			scopes.push(current);
			depth++;
			return scopes.peekFirst();
		}

		private Scope popScope() {
			Scope tmp = scopes.pop();
			endScope(tmp);
			if (!scopes.isEmpty()) {
				current = scopes.peekFirst();
			} else {
				current = null;
			}
			depth--;
			return tmp;
		}

		/**
		 * @param name
		 * @return
		 */
		private boolean isSuperGlobal(String name) {
			if ("$GLOBALS".equals(name)) { //$NON-NLS-1$
				return true;
			}
			if (!name.startsWith("$_")) { //$NON-NLS-1$
				return false;
			}

			return isGlobal(name);
		}

		/**
		 * @param name
		 * @return
		 */
		private boolean isGlobal(String name) {
			if (globals == null) {
				globals = PHPVariables.getVariables(validator.getPHPVersion());
			}
			for (String global : globals) {
				if (global.equals(name)) {
					return true;
				}
			}
			return false;
		}
	}

}
