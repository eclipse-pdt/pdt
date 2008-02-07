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
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.mixin.IMixinRequestor;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.mixin.IMixinRequestor.ElementInfo;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

public class PHPMixinBuildVisitor extends ASTVisitor {

	private static final String CLASS_SUFFIX = "%"; //$NON-NLS-1$

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

		public abstract String reportConstant(String name, IField object);

		public abstract String reportType(String name, IType object, boolean isInterface);

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

		public String reportType(String name, IType object, boolean isInterface) {
			return report(name + CLASS_SUFFIX, isInterface ? PHPMixinElementInfo.createInterface(object) : PHPMixinElementInfo.createClass(object));
		}

		public String reportVariable(String name, IField object) {
			// Report global variable:
			return report(name, PHPMixinElementInfo.createVariable(object));
		}

		public String reportConstant(String name, IField object) {
			// Report global constant:
			return report(name, PHPMixinElementInfo.createConstant(object));
		}

		public String getKey() {
			return "Object";
		}

		public String reportInclude(String object) {
			// Report include(), require(), require_once() and include_once():
			return report(object, new PHPMixinElementInfo(PHPMixinElementInfo.K_INCLUDE, object));
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

		public String reportType(String name, IType obj, boolean isInterface) {
			// There's no nested classes in PHP
			return null;
		}

		public String reportVariable(String name, IField object) {
			// Report class field:
			PHPMixinElementInfo info = PHPMixinElementInfo.createVariable(object);
			return report(getClassKey() + MixinModel.SEPARATOR + name, info);
		}

		public String reportConstant(String name, IField object) {
			// Report class constant:
			return report(getClassKey() + MixinModel.SEPARATOR + name, PHPMixinElementInfo.createConstant(object));
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

		public String reportType(String name, IType obj, boolean isInterface) {
			// Class defined in m(ethod belongs to the global scope:
			return sourceModuleScope.reportType(name, obj, isInterface);
		}

		public String reportVariable(String name, IField obj) {
			PHPMixinElementInfo info = PHPMixinElementInfo.createVariable(obj);
			if (name.charAt(0) == '$') { // Method or function scope local variable (example: $a = ...)
				return report(getKey() + MixinModel.SEPARATOR + name, info);
			}
			// Otherwise - it's a class variable ($this->a = ...):
			return report(getClassKey() + MixinModel.SEPARATOR + '$' + name, info);
		}

		public String reportConstant(String name, IField object) {
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

	private void reportVariableDeclaration(SimpleReference var) throws Exception {
		reportVariableDeclaration(var, scopes.peek());
	}

	private void reportVariableDeclaration(SimpleReference var, Scope scope) throws Exception {
		if(scope == null){
			throw new Exception("Scope should not be null");
		}
		IField obj = null;
		String name = var.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(var);
			obj = (IField) element;
		}
		scope.reportVariable(name, obj);
	}

	private static String stripQuotes(String name) {
		int len = name.length();
		if (len > 1 && (name.charAt(0) == '\'' && name.charAt(len - 1) == '\'' || name.charAt(0) == '"' && name.charAt(len - 1) == '"')) {
			name = name.substring(1, len - 1);
		}
		return name;
	}

	private IModelElement findModelElementFor(ASTNode decl) throws ModelException {
		return sourceModule.getElementAt(decl.sourceStart() + 1);
	}

	public boolean visit(Statement node) throws Exception {
		if (node instanceof ClassConstantDeclaration) {
			return visit((ClassConstantDeclaration) node);
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
		return true;
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
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(CallExpression expr) throws Exception {
		if ("define".equals(expr.getName())) { //$NON-NLS-1$
			// report global constant:
			IField obj = null;
			List args = expr.getArgs().getChilds();
			if (args.size() > 1) {
				ASTNode firstArg = (ASTNode) args.get(0);
				if (firstArg instanceof Scalar) {
					Scalar constant = (Scalar) firstArg;
					String name = constant.getValue();
					if (moduleAvailable) {
						IModelElement element = findModelElementFor(constant);
						obj = (IField) element;
					}
					Scope scope = scopes.peek();
					scope.reportConstant(stripQuotes(name), obj);
				}
			}
		}
		return true;
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

	public boolean visit(FormalParameter parameter) throws Exception {
		IField obj = null;
		VariableReference varReference = parameter.getParameterName();
		String name = varReference.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(varReference);
			obj = (IField) element;
		}
		Scope scope = scopes.peek();
		scope.reportVariable(name, obj);

		return true;
	}

	public boolean visit(ClassConstantDeclaration decl) throws Exception {
		IField obj = null;
		String name = decl.getConstantName().getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(decl);
			obj = (IField) element;
		}
		Scope scope = scopes.peek();
		scope.reportConstant(stripQuotes(name), obj);

		return true;
	}

	public boolean visit(CatchClause clause) throws Exception {
		VariableReference variable = clause.getVariable();
		reportVariableDeclaration(variable); // catch(Exception $a)
		return true;
	}

	public boolean visit(GlobalStatement statement) throws Exception {
		for (Expression variable : statement.getVariables()) {
			if (variable instanceof VariableReference) { // global $a, $b
				reportVariableDeclaration((VariableReference) variable, sourceModuleScope);
			}
		}
		return true;
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
		return true;
	}

	public boolean visit(PHPFieldDeclaration decl) throws Exception {
		IField obj = null;
		String name = decl.getName();
		if (moduleAvailable) {
			IModelElement element = findModelElementFor(decl);
			obj = (IField) element;
		}
		Scope scope = scopes.peek();
		scope.reportVariable(name, obj);

		return true;
	}

	public boolean visit(ModuleDeclaration s) throws Exception {
		sourceModuleScope = new SourceModuleScope(s);
		scopes.push(sourceModuleScope);
		return true;
	}

	public boolean endvisit(ModuleDeclaration s) throws Exception {
		scopes.pop();
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

	public boolean endvisit(MethodDeclaration decl) throws Exception {
		scopes.pop();
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
		String newKey = scope.reportType(name, obj, decl.getKind() == ASTNodeKinds.INTERFACE_DECLARATION);
		scopes.push(new ClassScope(decl, newKey));

		return true;
	}

	public boolean endvisit(TypeDeclaration decl) throws Exception {
		scopes.pop();
		return true;
	}
}
