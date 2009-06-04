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
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.*;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.*;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.compiler.ISourceElementRequestor.TypeInfo;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.php.core.PHPSourceElementRequestorExtension;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.IPHPModifiers;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

/**
 * This visitor builds DLTK model source elements.
 * @author michael
 */
public class PHPSourceElementRequestor extends SourceElementRequestVisitor {

	private static final String CONSTRUCTOR_NAME = "__construct";
	private static final Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+");;

	/**
	 * This should replace the need for fInClass, fInMethod and fCurrentMethod
	 * since in php the type declarations can be nested.
	 */
	protected Stack<Declaration> declarations = new Stack<Declaration>();
	private PHPSourceElementRequestorExtension[] extensions;

	/**
	 * Deferred elements that where declared in method/function but should
	 * belong to the global scope. 
	 */
	protected List<ASTNode> deferredDeclarations = new LinkedList<ASTNode>();

	/**
	 * This stack contains a set per method, where each set contains all global variables
	 * names delcared through 'global' keyword inside this method.
	 */
	protected Stack<Set<String>> methodGlobalVars = new Stack<Set<String>>();
	
	protected NamespaceDeclaration fLastNamespace;

	public PHPSourceElementRequestor(ISourceElementRequestor requestor, ISourceModule sourceModule) {
		super(requestor);
		
		// Load PHP source element requester extensions
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCorePlugin.ID, "phpSourceElementRequestors");
		List<PHPSourceElementRequestorExtension> requestors = new ArrayList<PHPSourceElementRequestorExtension>(elements.length);
		for (IConfigurationElement element : elements) {
			try {
				PHPSourceElementRequestorExtension extension = (PHPSourceElementRequestorExtension) element.createExecutableExtension("class");
				extension.setRequestor(fRequestor);
				extension.setSourceModule(sourceModule);
				requestors.add(extension);	
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		extensions = requestors.toArray(new PHPSourceElementRequestorExtension[requestors.size()]);
	}

	protected ISourceElementRequestor getRequestor() {
		return fRequestor;
	}

	public MethodDeclaration getCurrentMethod() {
		Declaration currDecleration = declarations.peek();
		if (currDecleration instanceof MethodDeclaration) {
			return (MethodDeclaration) currDecleration;
		}
		return null;
	}

	public boolean endvisit(MethodDeclaration method) throws Exception {
		methodGlobalVars.pop();
		declarations.pop();

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(method);
		}
		return super.endvisit(method);
	}

	public boolean endvisit(TypeDeclaration type) throws Exception {
		if (type instanceof NamespaceDeclaration) {
			NamespaceDeclaration namespaceDecl = (NamespaceDeclaration) type;
			fLastNamespace = null; // there are no nested namespaces
			if (namespaceDecl.isGlobal()) {
				return true;
			}
		}

		declarations.pop();

		// resolve more type member declarations
		resolveMagicMembers(type);

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(type);
		}

		return super.endvisit(type);
	}

	public boolean visit(MethodDeclaration method) throws Exception {

		methodGlobalVars.add(new HashSet<String>());

		Declaration parentDeclaration = null;
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}

		// In case we are entering a nested element - just add to the deferred list
		// and get out of the nested element visiting process
		if (parentDeclaration instanceof MethodDeclaration) {
			deferredDeclarations.add(method);
			return false;
		}

		if (parentDeclaration instanceof InterfaceDeclaration) {
			method.setModifier(Modifiers.AccAbstract);
		}

		declarations.push(method);

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(method);
		}

		boolean visit = super.visit(method);

		if (visit) {
			// Process method argument (local variable) declarations:
			List<Argument> arguments = method.getArguments();
			for (Argument arg : arguments) {
				ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
				info.name = arg.getName();
				info.modifiers = Modifiers.AccPublic;
				info.nameSourceStart = arg.getNameStart();
				info.nameSourceEnd = arg.getNameEnd() - 1;
				info.declarationStart = arg.sourceStart();
				fRequestor.enterField(info);
				fRequestor.exitField(arg.sourceEnd() - 1);
			}
		}
		return visit;
	}

	protected void modifyMethodInfo(MethodDeclaration methodDeclaration, ISourceElementRequestor.MethodInfo mi) {
		Declaration parentDeclaration = null;

		// find declaration that was before this method:
		declarations.pop();
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}
		declarations.push(methodDeclaration);

		mi.isConstructor = mi.name.equalsIgnoreCase(CONSTRUCTOR_NAME) || (parentDeclaration instanceof ClassDeclaration && mi.name.equalsIgnoreCase(((ClassDeclaration) parentDeclaration).getName()));

		// Check whether this method is marked as @internal
		if (methodDeclaration instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration phpDocAwareDeclaration = (IPHPDocAwareDeclaration) methodDeclaration;
			PHPDocBlock phpDoc = phpDocAwareDeclaration.getPHPDoc();
			if (phpDoc != null && phpDoc.getTags(PHPDocTag.INTERNAL).length > 0) {
				mi.modifiers |= IPHPModifiers.Internal;
			}
		}

		if (fCurrentClass == null || fCurrentClass == fLastNamespace) {
			mi.modifiers |= Modifiers.AccGlobal;
		}
	}

	public boolean visit(TypeDeclaration type) throws Exception {
		if (type instanceof NamespaceDeclaration) {
			NamespaceDeclaration namespaceDecl = (NamespaceDeclaration) type;
			fLastNamespace = namespaceDecl;
			if (namespaceDecl.isGlobal()) {
				return true;
			}
		}

		// In case we are entering a nested element 
		if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration) {
			deferredDeclarations.add(type);
			return false;
		}

		declarations.push(type);

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(type);
		}
		return super.visit(type);
	}

	protected String[] processSuperClasses(TypeDeclaration type) {
		ASTListNode superClasses = type.getSuperClasses();
		if (superClasses == null) {
			return new String[] {};
		}
		List superClassNames = superClasses.getChilds();
		List<String> result = new ArrayList<String>(superClassNames.size());
		Iterator iterator = superClassNames.iterator();
		while (iterator.hasNext()) {
			Object nameNode = iterator.next();

			String name;
			if (nameNode instanceof FullyQualifiedReference) {
				FullyQualifiedReference fullyQualifiedName = (FullyQualifiedReference) nameNode;
				name = fullyQualifiedName.getFullyQualifiedName();
				if (fullyQualifiedName.getNamespace() != null) {
					if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
						name = name.substring(1);
					}
				} else {
					if (fLastNamespace != null) {
						name = new StringBuilder(fLastNamespace.getName()).append(NamespaceReference.NAMESPACE_SEPARATOR).append(name).toString();
					}
				}
				result.add(name);
			} else if (nameNode instanceof SimpleReference) {
				result.add(((SimpleReference)nameNode).getName());
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	protected void modifyClassInfo(TypeDeclaration typeDeclaration, TypeInfo ti) {
		// Check whether this class is marked as @internal
		if (typeDeclaration instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration phpDocAwareDeclaration = (IPHPDocAwareDeclaration) typeDeclaration;
			PHPDocBlock phpDoc = phpDocAwareDeclaration.getPHPDoc();
			if (phpDoc != null && phpDoc.getTags(PHPDocTag.INTERNAL).length > 0) {
				ti.modifiers |= IPHPModifiers.Internal;
			}
		}

		// check whether this is a namespace
		if (typeDeclaration instanceof NamespaceDeclaration) {
			ti.modifiers |= Modifiers.AccNameSpace;
		}

		// modify class info if needed by extensions
		for (PHPSourceElementRequestorExtension extension : extensions) {
			extension.modifyClassInfo(typeDeclaration, ti);
		}
	}

	/**
	 * Resolve class members that were defined using the @property tag  
	 * @param type declaration for wich we add the magic variables
	 */
	private void resolveMagicMembers(TypeDeclaration type) {
		if (type instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration declaration = (IPHPDocAwareDeclaration) type;
			final PHPDocBlock doc = declaration.getPHPDoc();
			if (doc != null) {
				final PHPDocTag[] tags = doc.getTags();
				for (PHPDocTag docTag : tags) {
					final int tagKind = docTag.getTagKind();
					if (tagKind == PHPDocTag.PROPERTY || tagKind == PHPDocTag.PROPERTY_READ || tagKind == PHPDocTag.PROPERTY_WRITE) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.property.pkg.html
						final String[] split = WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}
						ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
						info.modifiers = Modifiers.AccPublic;
						info.name = split[1];
						SimpleReference var = new SimpleReference(docTag.sourceStart(), docTag.sourceStart() + 9, removeParenthesis(split));
						info.nameSourceStart = var.sourceStart();
						info.nameSourceEnd = var.sourceEnd();
						info.declarationStart = info.nameSourceStart;
						fRequestor.enterField(info);
						fRequestor.exitField(info.nameSourceEnd);

					} else if (tagKind == PHPDocTag.METHOD) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.method.pkg.html
						final String[] split = WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}

						ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
						mi.parameterNames = null;
						mi.name = removeParenthesis(split);
						SimpleReference var = new SimpleReference(docTag.sourceStart(), docTag.sourceStart() + 6, removeParenthesis(split));
						mi.modifiers = Modifiers.AccPublic;
						mi.nameSourceStart = var.sourceStart();
						mi.nameSourceEnd = var.sourceEnd();
						mi.declarationStart = mi.nameSourceStart;
						mi.isConstructor = false;

						this.fRequestor.enterMethod(mi);
						this.fRequestor.exitMethod(mi.nameSourceEnd);
					}
				}
			}
		}
	}

	private String removeParenthesis(final String[] split) {
		final String name = split[1];
		return name.endsWith("()") ? name.substring(0, name.length() - 2) : name;
	}

	public boolean visit(FieldDeclaration declaration) throws Exception {
		// This is constant declaration:
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccConstant | Modifiers.AccPublic | Modifiers.AccFinal;
		info.name = declaration.getName();
		info.nameSourceStart = declaration.getNameStart();
		info.nameSourceEnd = declaration.getNameEnd() - 1;
		info.declarationStart = declaration.sourceStart();
		fRequestor.enterField(info);
		fRequestor.exitField(declaration.sourceEnd() - 1);
		return true;
	}

	public boolean endvisit(FieldDeclaration declaration) throws Exception {
		return true;
	}

	public boolean visit(PHPFieldDeclaration declaration) throws Exception {
		// This is variable declaration:
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = declaration.getModifiers();
		info.name = declaration.getName();
		SimpleReference var = declaration.getRef();
		info.nameSourceEnd = var.sourceEnd() - 1;
		info.nameSourceStart = var.sourceStart();
		info.declarationStart = declaration.getDeclarationStart();
		fRequestor.enterField(info);
		return true;
	}

	public boolean visit(CatchClause catchClause) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccPublic;
		SimpleReference var = catchClause.getVariable();
		info.name = var.getName();
		info.nameSourceEnd = var.sourceEnd() - 1;
		info.nameSourceStart = var.sourceStart();
		info.declarationStart = catchClause.sourceStart();
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(CatchClause catchClause) throws Exception {
		fRequestor.exitField(catchClause.sourceEnd() - 1);
		return true;
	}

	public boolean visit(ForEachStatement foreachStatement) throws Exception {
		if (foreachStatement.getKey() instanceof VariableReference) {
			SimpleReference var = (SimpleReference) foreachStatement.getKey();
			ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
			info.modifiers = Modifiers.AccPublic;
			info.name = var.getName();
			info.nameSourceEnd = var.sourceEnd() - 1;
			info.nameSourceStart = var.sourceStart();
			info.declarationStart = var.sourceStart();
			fRequestor.enterField(info);
			fRequestor.exitField(var.sourceEnd() - 1);
		}
		if (foreachStatement.getValue() instanceof VariableReference) {
			SimpleReference var = (SimpleReference) foreachStatement.getValue();
			ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
			info.modifiers = Modifiers.AccPublic;
			info.name = var.getName();
			info.nameSourceEnd = var.sourceEnd() - 1;
			info.nameSourceStart = var.sourceStart();
			info.declarationStart = var.sourceStart();
			fRequestor.enterField(info);
			fRequestor.exitField(var.sourceEnd() - 1);
		}
		return true;
	}

	public boolean endvisit(ForEachStatement foreachStatement) throws Exception {
		return true;
	}

	public boolean endvisit(PHPFieldDeclaration declaration) throws Exception {
		fRequestor.exitField(declaration.sourceEnd() - 1);
		return true;
	}

	public boolean visit(CallExpression call) throws Exception {
		FieldDeclaration constantDecl = ASTUtils.getConstantDeclaration(call);
		if (constantDecl != null) {
			// In case we are entering a nested element 
			if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration) {
				deferredDeclarations.add(constantDecl);
				return false;
			}

			visit((FieldDeclaration) constantDecl);

		} else {
			int argsCount = 0;
			CallArgumentsList args = call.getArgs();
			if (args != null && args.getChilds() != null) {
				argsCount = args.getChilds().size();
			}
			fRequestor.acceptMethodReference(call.getName().toCharArray(), argsCount, call.sourceStart(), call.sourceEnd());
		}
		return true;
	}

	public boolean visit(Include include) throws Exception {
		// special case for include statements; we need to cache this information in order to access it quickly:
		if (include.getExpr() instanceof Scalar) {
			Scalar filePath = (Scalar) include.getExpr();
			fRequestor.acceptMethodReference("include".toCharArray(), 0, filePath.sourceStart(), filePath.sourceEnd());
		}
		return true;
	}

	public boolean visit(ConstantDeclaration declaration) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccConstant | Modifiers.AccPublic | Modifiers.AccFinal;
		ConstantReference constantName = declaration.getConstantName();
		info.name = ASTUtils.stripQuotes(constantName.getName());
		info.nameSourceEnd = constantName.sourceEnd() - 1;
		info.nameSourceStart = constantName.sourceStart();
		info.declarationStart = declaration.sourceStart();
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(ConstantDeclaration declaration) throws Exception {
		fRequestor.exitField(declaration.sourceEnd() - 1);
		return true;
	}

	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof FieldAccess) { // class variable ($this->a = .)
			FieldAccess fieldAccess = (FieldAccess) left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference ){//&& "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					SimpleReference ref = (SimpleReference) field;
					ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
					info.modifiers = Modifiers.AccPublic;
					info.name = '$' + ref.getName();
					info.nameSourceEnd = ref.sourceEnd() - 1;
					info.nameSourceStart = ref.sourceStart();
					info.declarationStart = assignment.sourceStart();
					fRequestor.enterField(info);
					fNodes.push(assignment);
				}
			}
		} else if (left instanceof VariableReference) {

			if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration) {
				if (methodGlobalVars.peek().contains(((VariableReference) left).getName())) {
					deferredDeclarations.add(assignment);
					return false;
				}
			}

			ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
			info.modifiers = Modifiers.AccPublic;
			info.name = ((VariableReference) left).getName();
			info.nameSourceEnd = left.sourceEnd() - 1;
			info.nameSourceStart = left.sourceStart();
			info.declarationStart = assignment.sourceStart();
			fRequestor.enterField(info);
			fNodes.push(assignment);
		}
		return true;
	}

	public boolean endvisit(Assignment assignment) throws Exception {
		if (!fNodes.isEmpty() && fNodes.peek() == assignment) {
			fRequestor.exitField(assignment.sourceEnd() - 1);
			fNodes.pop();
		}
		return true;
	}

	public boolean visit(GlobalStatement s) throws Exception {
		if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration) {
			for (Expression var : s.getVariables()) {
				if (var instanceof ReferenceExpression) {
					var = ((ReferenceExpression) var).getVariable();
				}
				if (var instanceof SimpleReference) {
					methodGlobalVars.peek().add(((SimpleReference) var).getName());
				}
			}
		}
		return true;
	}

	public boolean visit(TypeReference reference) throws Exception {
		fRequestor.acceptTypeReference(reference.getName().toCharArray(), reference.sourceStart());
		return true;
	}

	public boolean visit(Statement node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(node);
		}

		Class<?> statementClass = node.getClass();
		if (statementClass.equals(PHPFieldDeclaration.class)) {
			return visit((PHPFieldDeclaration) node);
		}
		if (statementClass.equals(FieldDeclaration.class)) {
			return visit((FieldDeclaration) node);
		}
		if (statementClass.equals(ConstantDeclaration.class)) {
			return visit((ConstantDeclaration) node);
		}
		if (statementClass.equals(CatchClause.class)) {
			return visit((CatchClause) node);
		}
		if (statementClass.equals(ForEachStatement.class)) {
			return visit((ForEachStatement) node);
		}
		if (statementClass.equals(GlobalStatement.class)) {
			return visit((GlobalStatement) node);
		}
		return true;
	}

	public boolean endvisit(Statement node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(node);
		}

		Class<?> statementClass = node.getClass();
		if (statementClass.equals(PHPFieldDeclaration.class)) {
			return endvisit((PHPFieldDeclaration) node);
		}
		if (statementClass.equals(FieldDeclaration.class)) {
			return endvisit((FieldDeclaration) node);
		}
		if (statementClass.equals(ConstantDeclaration.class)) {
			return endvisit((ConstantDeclaration) node);
		}
		if (statementClass.equals(CatchClause.class)) {
			return endvisit((CatchClause) node);
		}
		if (statementClass.equals(ForEachStatement.class)) {
			return endvisit((ForEachStatement) node);
		}
		return true;
	}

	public boolean visit(Expression node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(node);
		}

		Class<?> expressionClass = node.getClass();
		if (expressionClass.equals(Assignment.class)) {
			return visit((Assignment) node);
		}
		if (expressionClass.equals(TypeReference.class)) {
			return visit((TypeReference) node);
		}
		if (expressionClass.equals(Include.class)) {
			return visit((Include) node);
		}
		if (expressionClass.equals(PHPCallExpression.class)) {
			return visit((PHPCallExpression) node);
		}
		return true;
	}

	public boolean endvisit(Expression node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(node);
		}

		Class<?> expressionClass = node.getClass();
		if (expressionClass.equals(Assignment.class)) {
			return endvisit((Assignment) node);
		}
		return true;
	}

	public boolean endvisit(ModuleDeclaration declaration) throws Exception {
		while (deferredDeclarations != null && !deferredDeclarations.isEmpty()) {
			final ASTNode[] declarations = deferredDeclarations.toArray(new ASTNode[deferredDeclarations.size()]);
			deferredDeclarations.clear();

			for (ASTNode deferred : declarations) {
				deferred.traverse(this);
			}
		}
		return super.endvisit(declaration);
	}
}
