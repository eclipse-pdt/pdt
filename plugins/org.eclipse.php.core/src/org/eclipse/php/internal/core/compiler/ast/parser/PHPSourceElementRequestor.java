/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.compiler.ast.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
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
import org.eclipse.php.core.PHPSourceElementRequestorExtension;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.Assignment;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassConstantDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.ClassDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPModifiers;
import org.eclipse.php.internal.core.compiler.ast.nodes.Include;
import org.eclipse.php.internal.core.compiler.ast.nodes.InterfaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPFieldDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;

public class PHPSourceElementRequestor extends SourceElementRequestVisitor {

	private static final String CONSTRUCTOR_NAME = "__construct";
	/*
	 * This should replace the need for fInClass, fInMethod and fCurrentMethod
	 * since in php the type declarations can be nested.
	 */
	protected Stack<Declaration> declarations = new Stack<Declaration>();
	private PHPSourceElementRequestorExtension[] extensions;
	
	/**
	 * Deferred elements that are 
	 */
	protected List<Declaration> deferredDeclarations = new LinkedList<Declaration>();
	
	private static final Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+");;

	public PHPSourceElementRequestor(ISourceElementRequestor requestor, char[] contents, char[] filename) {
		super(requestor);

		// Load PHP source element requester extensions
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCorePlugin.ID, "phpSourceElementRequestors");
		List<PHPSourceElementRequestorExtension> requestors = new ArrayList<PHPSourceElementRequestorExtension>(elements.length);
		for (IConfigurationElement element : elements) {
			try {
				PHPSourceElementRequestorExtension extension = (PHPSourceElementRequestorExtension) element.createExecutableExtension("class");
				extension.setRequestor(fRequestor);
				extension.setContents(contents);
				extension.setFilename(new String(filename));

				requestors.add(extension);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		extensions = requestors.toArray(new PHPSourceElementRequestorExtension[requestors.size()]);
	}

	public MethodDeclaration getCurrentMethod() {
		Declaration currDecleration = declarations.peek();
		if (currDecleration instanceof MethodDeclaration) {
			return (MethodDeclaration) currDecleration;
		}
		return null;
	}

	public boolean endvisit(MethodDeclaration method) throws Exception {
		declarations.pop();

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(method);
		}
		return super.endvisit(method);
	}

	public boolean endvisit(TypeDeclaration type) throws Exception {
		declarations.pop();

		// resolve more type member declarations
		resolveMagicMembers(type);

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(type);
		}

		return super.endvisit(type);
	}

	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration method) throws Exception {
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
		
		mi.isConstructor = mi.name.equalsIgnoreCase(CONSTRUCTOR_NAME)
			|| (parentDeclaration instanceof ClassDeclaration && mi.name.equalsIgnoreCase(((ClassDeclaration) parentDeclaration).getName()));
		
		// Check whether this method is marked as @internal
		if (methodDeclaration instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration phpDocAwareDeclaration = (IPHPDocAwareDeclaration) methodDeclaration;
			PHPDocBlock phpDoc = phpDocAwareDeclaration.getPHPDoc();
			if (phpDoc != null && phpDoc.getTags(PHPDocTag.INTERNAL).length > 0) {
				mi.modifiers |= IPHPModifiers.Internal;
			}
		}
	}

	public boolean visit(TypeDeclaration type) throws Exception {
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
	
	protected void modifyClassInfo(TypeDeclaration typeDeclaration, TypeInfo ti) {
		// Check whether this class is marked as @internal
		if (typeDeclaration instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration phpDocAwareDeclaration = (IPHPDocAwareDeclaration) typeDeclaration;
			PHPDocBlock phpDoc = phpDocAwareDeclaration.getPHPDoc();
			if (phpDoc != null && phpDoc.getTags(PHPDocTag.INTERNAL).length > 0) {
				ti.modifiers |= IPHPModifiers.Internal;
			}
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
						info.nameSourceEnd = var.sourceEnd() - 1;
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
						SimpleReference var = new SimpleReference(docTag.sourceStart(), docTag.sourceStart() + 5, removeParenthesis(split));
						mi.modifiers = Modifiers.AccPublic;
						mi.nameSourceStart = var.sourceStart();
						mi.nameSourceEnd = var.sourceEnd() - 1;
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
		info.modifiers = Modifiers.AccConstant;
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
			
			visit((FieldDeclaration)constantDecl);
			
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

	public boolean visit(ClassConstantDeclaration declaration) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccConstant;
		ConstantReference constantName = declaration.getConstantName();
		info.name = ASTUtils.stripQuotes(constantName.getName());
		info.nameSourceEnd = constantName.sourceEnd() - 1;
		info.nameSourceStart = constantName.sourceStart();
		info.declarationStart = declaration.sourceStart();
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(ClassConstantDeclaration declaration) throws Exception {
		fRequestor.exitField(declaration.sourceEnd() - 1);
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof FieldAccess) { // class variable ($this->a = .)
			FieldAccess fieldAccess = (FieldAccess) left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					SimpleReference ref = (SimpleReference) field;
					ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
					info.modifiers = Modifiers.AccDefault;
					info.name = '$' + ref.getName();
					info.nameSourceEnd = ref.sourceEnd() - 1;
					info.nameSourceStart = ref.sourceStart();
					info.declarationStart = assignment.sourceStart();
					fRequestor.enterField(info);
					fNodes.push(assignment);
				}
			}
		} else if (left instanceof VariableReference) {
			ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
			info.modifiers = Modifiers.AccDefault;
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

	public boolean visit(TypeReference reference) throws Exception {
		fRequestor.acceptTypeReference(reference.getName().toCharArray(), reference.sourceStart());
		return true;
	}

	public boolean visit(Statement node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(PHPFieldDeclaration.class.getName())) {
			return visit((PHPFieldDeclaration) node);
		}
		if (clasName.equals(FieldDeclaration.class.getName())) {
			return visit((FieldDeclaration) node);
		}
		if (clasName.equals(ClassConstantDeclaration.class.getName())) {
			return visit((ClassConstantDeclaration) node);
		}
		return true;
	}

	public boolean endvisit(Statement node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(PHPFieldDeclaration.class.getName())) {
			return endvisit((PHPFieldDeclaration) node);
		}
		if (clasName.equals(FieldDeclaration.class.getName())) {
			return endvisit((FieldDeclaration) node);
		}
		if (clasName.equals(ClassConstantDeclaration.class.getName())) {
			return endvisit((ClassConstantDeclaration) node);
		}
		return true;
	}

	public boolean visit(Expression node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(Assignment.class.getName())) {
			return visit((Assignment) node);
		}
		if (clasName.equals(TypeReference.class.getName())) {
			return visit((TypeReference) node);
		}
		if (clasName.equals(Include.class.getName())) {
			return visit((Include) node);
		}
		if (clasName.equals(PHPCallExpression.class.getName())) {
			return visit((PHPCallExpression) node);
		}
		return true;
	}

	public boolean endvisit(Expression node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(node);
		}

		String clasName = node.getClass().getName();
		if (clasName.equals(Assignment.class.getName())) {
			return endvisit((Assignment) node);
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dltk.compiler.SourceElementRequestVisitor#endvisit(org.eclipse.dltk.ast.declarations.ModuleDeclaration)
	 */
	@Override
	public boolean endvisit(ModuleDeclaration declaration) throws Exception {
		while (deferredDeclarations != null && !deferredDeclarations.isEmpty()) {
			final Declaration[] declarations = deferredDeclarations.toArray(new Declaration[deferredDeclarations.size()]);
			deferredDeclarations.clear();
			
			for (Declaration deferred : declarations) {
				deferred.traverse(this);
			}
		}
		
		return super.endvisit(declaration);
	}
	

}
