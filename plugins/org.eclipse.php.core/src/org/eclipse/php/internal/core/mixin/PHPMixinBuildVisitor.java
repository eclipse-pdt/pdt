package org.eclipse.php.internal.core.mixin;

import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.mixin.IMixinRequestor.ElementInfo;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassConstantDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.compiler.ast.nodes.ListVariable;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.sourceModel.IConstant;

public class PHPMixinBuildVisitor extends ASTVisitor {

	private static final String CLASS_SUFFIX = "%"; //$NON-NLS-1$
	private static final String INCLUDE_SUFFIX = "@"; //$NON-NLS-1$
	private static final String CONSTANT_SUFFIX = "#"; //$NON-NLS-1$

//	private ModuleDeclaration module;
	private ISourceModule sourceModule;
	private boolean moduleAvailable;
	private IMixinRequestor requestor;
	private Stack<Scope> scopes = new Stack<Scope>();
	private SourceModuleScope sourceModuleScope;

	private abstract class Scope {
		private final ASTNode node;

		public Scope(ASTNode node) {
			super();
			this.node = node;
		}

		public ASTNode getNode() {
			return node;
		}

		public abstract String reportMethod(String name, IMethod object);
		public abstract String reportVariable(String name, IField object);
		public abstract String reportConstant(String name, IConstant object);
		public abstract String reportType(String name, IType object);
		public abstract String reportInclude(String object);
		public abstract String getClassKey();
		public abstract String getKey();
	}

	private class SourceModuleScope extends Scope {

		public SourceModuleScope(ModuleDeclaration node) {
			super(node);
		}

		public String getClassKey() {
			return "Object";
		}

		public String reportMethod(String name, IMethod object) {
			// Report global function:
			return report(name, PHPMixinElementInfo.createMethod(object));
		}

		public String reportType(String name, IType object) {
			return report(name + CLASS_SUFFIX, PHPMixinElementInfo.createClass(object));
		}

		public String reportVariable(String name, IField object) {
			// Report global variable:
			return report(name, PHPMixinElementInfo.createVariable(object));
		}

		public String reportConstant(String name, IConstant object) {
			// Report global constant:
			return report(name + CONSTANT_SUFFIX, PHPMixinElementInfo.createConstant(object));
		}

		public String getKey() {
			return "Object";
		}

		public String reportInclude(String object) {
			// Report include(), require(), require_once() and include_once():
			return report(object + INCLUDE_SUFFIX, new PHPMixinElementInfo(PHPMixinElementInfo.K_INCLUDE, object));
		}
	}

	private class ClassScope extends Scope {

		private final String classKey;

		public ClassScope(ASTNode node, String classKey) {
			super(node);
			this.classKey = classKey;
		}

		public String reportMethod(String name, IMethod object) {
			// Report class method:
			return report(getClassKey() + MixinModel.SEPARATOR + name, PHPMixinElementInfo.createMethod(object));
		}

		public String reportType(String name, IType obj) {
			// There's no nested classes in PHP
			return null;
		}

		public String reportVariable(String name, IField object) {
			// Report class field:
			PHPMixinElementInfo info = PHPMixinElementInfo.createVariable(object);
			return report(getClassKey() + MixinModel.SEPARATOR + name, info);
		}

		public String reportConstant(String name, IConstant object) {
			// Report class constant:
			return report(getClassKey() + MixinModel.SEPARATOR + CONSTANT_SUFFIX, PHPMixinElementInfo.createConstant(object));
		}

		public String getClassKey() {
			return classKey;
		}

		public String getKey() {
			return classKey;
		}

		public String reportInclude(String object) {
			// There's no possibility to include other files from within a class
			return null;
		}
	}

	private class MethodScope extends Scope {

		private final Scope classScope;
		private final String methodKey;

		public MethodScope(ASTNode node, Scope classScope, String methodKey) {
			super(node);
			this.classScope = classScope;
			this.methodKey = methodKey;
		}

		public String reportMethod(String name, IMethod object) {
			// Method defined in method belongs to the global scope:
			return sourceModuleScope.reportMethod(name, object);
		}

		public String reportType(String name, IType obj) {
			// Class defined in m(ethod belongs to the global scope:
			return sourceModuleScope.reportType(name, obj);
		}

		public String reportVariable(String name, IField obj) {
			PHPMixinElementInfo info = PHPMixinElementInfo.createVariable(obj);
			if (name.charAt(0) == '$') { // Method or function scope local variable (example: $a = ...)
				return report(getKey() + MixinModel.SEPARATOR + name, info);
			}
			// Otherwise - it's a class variable ($this->a = ...):
			return report(getClassKey() + MixinModel.SEPARATOR + '$' + name, info);
		}

		public String reportConstant(String name, IConstant object) {
			// Constant defined inside of method is a part of global scope:
			return sourceModuleScope.reportConstant(name, object);
		}

		public String getClassKey() {
			return classScope.getClassKey();
		}

		public String getKey() {
			return methodKey;
		}

		public String reportInclude(String object) {
			// Included file in the method belongs to the global scope:
			return sourceModuleScope.reportInclude(object);
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

	private String report(String key, PHPMixinElementInfo object) {
		ElementInfo info = new IMixinRequestor.ElementInfo();
		info.key = key;
		info.object = object;
		if (requestor != null) {
			requestor.reportElement(info);
		}
		return key;
	}

	private IModelElement findModelElementFor(ASTNode decl) throws ModelException {
		return sourceModule.getElementAt(decl.sourceStart() + 1);
	}

	public boolean visit(ASTNode node) throws Exception {
		if (node instanceof ClassConstantDeclaration) {
			return visit((ClassConstantDeclaration)node);
		}
		return true;
	}

	public boolean visit(Expression expr) throws Exception {
		if (expr instanceof CallExpression) {
			return visit((CallExpression)expr);
		}
		if (expr instanceof Assignment) {
			return visit((Assignment)expr);
		}
		if (expr instanceof ListVariable) {
			return visit((ListVariable)expr);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(CallExpression expr) throws Exception {
		if ("define".equals(expr.getName())) { //$NON-NLS-1$
			// report global constant:
			IConstant obj = null;
			List args = expr.getArgs().getChilds();
			if (args.size() > 1) {
				ASTNode firstArg =  (ASTNode)args.get(0);
				if (firstArg instanceof Scalar) {
					Scalar constant = (Scalar)firstArg;
					String name = constant.getValue();
					if (moduleAvailable) {
						IModelElement element = findModelElementFor(constant);
						obj = (IConstant) element;
					}
					Scope scope = scopes.peek();
					scope.reportConstant(name, obj);
				}
			}
		}
		return true;
	}

	protected void reportVariableDeclaration(VariableReference var) throws Exception {
		IField obj = null;
		String name = var.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(var);
			obj = (IField) element;
		}
		Scope scope = scopes.peek();
		scope.reportVariable(name, obj);
	}

	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof VariableReference) { // local variable ($a = ...)
			reportVariableDeclaration((VariableReference) left);
		}
		else if (left instanceof FieldAccess) { // class variable ($this->a)
			FieldAccess fieldAccess = (FieldAccess)left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference)dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof VariableReference) {
					reportVariableDeclaration((VariableReference)field);
				}
			}
		}
		return true;
	}

	public boolean visit(ListVariable list) throws Exception {
		for (Expression variable : list.getVariables()) {
			if (variable instanceof VariableReference) { // list variable (list($a, ...) = ...)
				reportVariableDeclaration((VariableReference) variable);
			}
		}
		return true;
	}

	public boolean visit(ClassConstantDeclaration decl) throws Exception {
		IConstant obj = null;
		String name = decl.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(decl);
			obj = (IConstant) element;
		}
		Scope scope = scopes.peek();
		scope.reportConstant(name, obj);

		return true;
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		sourceModuleScope = new SourceModuleScope(s);
		scopes.add(sourceModuleScope);
		return true;
	}

	public boolean visit(MethodDeclaration decl) throws Exception {
		IMethod obj = null;
		String name = decl.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(decl);
			obj = (IMethod) element;
		}

		Scope scope = scopes.peek();
		String method = scope.reportMethod(name, obj);
		scopes.push(new MethodScope(decl, scope, method));

		return true;
	}

	public boolean visit(TypeDeclaration decl) throws Exception {
		IType obj = null;
		if (moduleAvailable) {
			IModelElement elementFor = findModelElementFor(decl);
			obj = (IType) elementFor;
		}

		String name = decl.getName();
		Scope scope = scopes.peek();
		String newKey = scope.reportType(name, obj);
		scopes.push(new ClassScope(decl, newKey));

		return true;
	}
}
