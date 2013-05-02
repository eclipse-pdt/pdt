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
package org.eclipse.php.internal.core.compiler;

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
import org.eclipse.dltk.ast.expressions.Literal;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.*;
import org.eclipse.dltk.compiler.IElementRequestor.ImportInfo;
import org.eclipse.dltk.compiler.IElementRequestor.TypeInfo;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.Flags;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.core.compiler.PHPSourceElementRequestorExtension;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.core.util.MagicMemberUtil.MagicMethod;

/**
 * This visitor builds DLTK structured model elements.
 * 
 * @author michael
 */
public class PHPSourceElementRequestor extends SourceElementRequestVisitor {

	private static final String MAGIC_PROPERTY_TYPE = "MagicPropertyType"; //$NON-NLS-1$
	private static final String CONSTRUCTOR_NAME = "__construct"; //$NON-NLS-1$
	private static final String VOID_RETURN_TYPE = "void"; //$NON-NLS-1$
	private static final Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+"); //$NON-NLS-1$
	private static final String GLOBAL_NAMESPACE_CONTAINER_NAME = "global namespace"; //$NON-NLS-1$
	private static final String DEFAULT_VALUE = " "; //$NON-NLS-1$
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
	 * This stack contains a set per method, where each set contains all global
	 * variables names delcared through 'global' keyword inside this method.
	 */
	protected Stack<Set<String>> methodGlobalVars = new Stack<Set<String>>();

	protected NamespaceDeclaration fLastNamespace;
	protected Map<String, UsePart> fLastUseParts = new HashMap<String, UsePart>();

	public PHPSourceElementRequestor(ISourceElementRequestor requestor,
			IModuleSource sourceModule) {
		super(requestor);

		// Load PHP source element requester extensions
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PHPCorePlugin.ID,
						"phpSourceElementRequestors"); //$NON-NLS-1$
		List<PHPSourceElementRequestorExtension> requestors = new ArrayList<PHPSourceElementRequestorExtension>(
				elements.length);
		for (IConfigurationElement element : elements) {
			try {
				PHPSourceElementRequestorExtension extension = (PHPSourceElementRequestorExtension) element
						.createExecutableExtension("class"); //$NON-NLS-1$
				extension.setRequestor(fRequestor);
				extension.setSourceModule(sourceModule);
				requestors.add(extension);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		extensions = requestors
				.toArray(new PHPSourceElementRequestorExtension[requestors
						.size()]);
	}

	protected IElementRequestor getRequestor() {
		return fRequestor;
	}

	public MethodDeclaration getCurrentMethod() {
		Declaration currDecleration = declarations.peek();
		if (currDecleration instanceof MethodDeclaration) {
			return (MethodDeclaration) currDecleration;
		}
		return null;
	}

	public boolean endvisit(LambdaFunctionDeclaration lambdaMethod)
			throws Exception {

		methodGlobalVars.pop();
		this.fInMethod = false;

		if (!fNodes.isEmpty() && fNodes.peek() == lambdaMethod) {
			fRequestor.exitMethod(lambdaMethod.sourceEnd() - 1);
			fNodes.pop();
		}
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(lambdaMethod);
		}
		return true;
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

	public boolean visit(LambdaFunctionDeclaration lambdaMethod)
			throws Exception {

		fNodes.push(lambdaMethod);
		methodGlobalVars.add(new HashSet<String>());

		// Declaration parentDeclaration = null;
		// if (!declarations.empty()
		// && declarations.peek() instanceof MethodDeclaration) {
		// parentDeclaration = declarations.peek();
		// // In case we are entering a nested element - just add to the
		// // deferred list and get out of the nested element visiting process
		// deferredDeclarations.add(lambdaMethod);
		// return visitGeneral(lambdaMethod);
		// }

		Collection<FormalParameter> arguments = lambdaMethod.getArguments();
		StringBuilder metadata = new StringBuilder();
		String[] parameters = new String[arguments.size()];
		if (arguments != null) {
			Iterator<FormalParameter> i = arguments.iterator();
			int indx = 0;
			while (i.hasNext()) {
				Argument arg = (Argument) i.next();
				metadata.append(arg.getName());
				parameters[indx] = arg.getName();
				indx++;
				if (i.hasNext()) {
					metadata.append(","); //$NON-NLS-1$
				}
			}
		}

		// Add method declaration:
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(lambdaMethod);
		}

		ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
		mi.parameterNames = parameters;
		mi.name = PHPCoreConstants.ANONYMOUS;
		mi.modifiers = Modifiers.AccPublic;
		if (lambdaMethod.isStatic()) {
			mi.modifiers |= Modifiers.AccStatic;
		}
		mi.nameSourceStart = lambdaMethod.sourceStart();
		mi.nameSourceEnd = lambdaMethod.sourceEnd();
		mi.declarationStart = mi.nameSourceStart;
		mi.isConstructor = false;

		this.fRequestor.enterMethod(mi);
		this.fInMethod = true;

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

		return true;
	}

	public boolean visit(MethodDeclaration method) throws Exception {

		methodGlobalVars.add(new HashSet<String>());

		Declaration parentDeclaration = null;
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}

		// In case we are entering a nested element - just add to the deferred
		// list
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

		boolean visit = visitMethodDeclaration(method);

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

	private boolean visitMethodDeclaration(MethodDeclaration method)
			throws Exception {
		this.fNodes.push(method);
		List args = method.getArguments();

		String[] parameter = new String[args.size()];
		String[] initializers = new String[args.size()];
		for (int a = 0; a < args.size(); a++) {
			Argument arg = (Argument) args.get(a);
			parameter[a] = arg.getName();
			if (arg.getInitialization() != null) {
				if (arg.getInitialization() instanceof Literal) {
					Literal scalar = (Literal) arg.getInitialization();
					initializers[a] = scalar.getValue();
				} else {
					initializers[a] = DEFAULT_VALUE;
				}
			}
		}

		ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
		mi.parameterNames = parameter;
		mi.name = method.getName();
		mi.modifiers = method.getModifiers();
		mi.nameSourceStart = method.getNameStart();
		mi.nameSourceEnd = method.getNameEnd() - 1;
		mi.declarationStart = method.sourceStart();
		mi.parameterInitializers = initializers;

		modifyMethodInfo(method, mi);

		this.fRequestor.enterMethod(mi);

		this.fInMethod = true;
		this.fCurrentMethod = method;
		return true;
	}

	protected void modifyMethodInfo(MethodDeclaration methodDeclaration,
			ISourceElementRequestor.MethodInfo mi) {
		Declaration parentDeclaration = null;

		// find declaration that was before this method:
		declarations.pop();
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}
		declarations.push(methodDeclaration);

		mi.isConstructor = mi.name.equalsIgnoreCase(CONSTRUCTOR_NAME)
				|| (parentDeclaration instanceof ClassDeclaration && mi.name
						.equalsIgnoreCase(((ClassDeclaration) parentDeclaration)
								.getName()));

		if (fCurrentClass == null || fCurrentClass == fLastNamespace) {
			mi.modifiers |= Modifiers.AccGlobal;
		}
		if (!Flags.isPrivate(mi.modifiers) && !Flags.isProtected(mi.modifiers)
				&& !Flags.isPublic(mi.modifiers)) {
			mi.modifiers |= Modifiers.AccPublic;
		}

		mi.parameterTypes = processParamterTypes(methodDeclaration);
		mi.returnType = processReturnType(methodDeclaration);

		// modify method info if needed by extensions
		for (PHPSourceElementRequestorExtension extension : extensions) {
			extension.modifyMethodInfo(methodDeclaration, mi);
		}
	}

	private String[] processParamterTypes(MethodDeclaration methodDeclaration) {
		List args = methodDeclaration.getArguments();
		PHPDocBlock docBlock = ((PHPMethodDeclaration) methodDeclaration)
				.getPHPDoc();
		String[] parameterType = new String[args.size()];
		for (int a = 0; a < args.size(); a++) {
			Argument arg = (Argument) args.get(a);
			if (arg instanceof FormalParameter) {
				SimpleReference type = ((FormalParameter) arg)
						.getParameterType();
				if (type != null) {
					parameterType[a] = type.getName();
				} else if (docBlock != null) {
					for (PHPDocTag tag : docBlock.getTags(PHPDocTag.PARAM)) {
						SimpleReference[] refs = tag.getReferences();
						if (refs.length == 2) {
							if (refs[0].getName().equals(arg.getName())) {
								parameterType[a] = refs[1].getName();
							}
						}
					}
				}
			}
		}
		return parameterType;
	}

	private String processReturnType(MethodDeclaration methodDeclaration) {
		PHPDocBlock docBlock = ((PHPMethodDeclaration) methodDeclaration)
				.getPHPDoc();
		String type = VOID_RETURN_TYPE;
		if (docBlock != null) {
			for (PHPDocTag tag : docBlock.getTags(PHPDocTag.RETURN)) {
				for (SimpleReference reference : tag.getReferences()) {
					return PHPModelUtils
							.extractElementName(reference.getName());
				}
			}
		}
		return type;
	}

	public boolean visit(TypeDeclaration type) throws Exception {
		if (type instanceof NamespaceDeclaration) {
			NamespaceDeclaration namespaceDecl = (NamespaceDeclaration) type;
			fLastNamespace = namespaceDecl;
			fLastUseParts.clear();
			if (namespaceDecl.isGlobal()) {
				return true;
			}
		}

		// In case we are entering a nested element
		if (!declarations.empty()
				&& declarations.peek() instanceof MethodDeclaration) {
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
					String namespace = fullyQualifiedName.getNamespace()
							.getName();

					String subnamespace = ""; //$NON-NLS-1$
					if (namespace.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR
							&& namespace
									.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
						int firstNSLocation = namespace
								.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
						subnamespace = namespace.substring(firstNSLocation);
						namespace = namespace.substring(0, firstNSLocation);
					}
					if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
						name = name.substring(1);
					} else if (fLastUseParts.containsKey(namespace)) {
						name = new StringBuilder(fLastUseParts.get(namespace)
								.getNamespace().getFullyQualifiedName())
								.append(subnamespace)
								.append(NamespaceReference.NAMESPACE_SEPARATOR)
								.append(fullyQualifiedName.getName())
								.toString();
					} else if (fLastNamespace != null) {
						name = new StringBuilder(fLastNamespace.getName())
								.append(NamespaceReference.NAMESPACE_SEPARATOR)
								.append(name).toString();
					}
				} else if (fLastUseParts.containsKey(name)) {
					name = fLastUseParts.get(name).getNamespace()
							.getFullyQualifiedName();
					if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
						name = name.substring(1);
					}
				} else {
					if (fLastNamespace != null) {
						name = new StringBuilder(fLastNamespace.getName())
								.append(NamespaceReference.NAMESPACE_SEPARATOR)
								.append(name).toString();
					}
				}
				result.add(name);
			} else if (nameNode instanceof SimpleReference) {
				result.add(((SimpleReference) nameNode).getName());
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	protected void modifyClassInfo(TypeDeclaration typeDeclaration, TypeInfo ti) {
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
	 * 
	 * @param type
	 *            declaration for wich we add the magic variables
	 */
	private void resolveMagicMembers(TypeDeclaration type) {
		if (type instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration declaration = (IPHPDocAwareDeclaration) type;
			final PHPDocBlock doc = declaration.getPHPDoc();
			if (doc != null) {
				final PHPDocTag[] tags = doc.getTags();
				for (PHPDocTag docTag : tags) {
					final int tagKind = docTag.getTagKind();
					if (tagKind == PHPDocTag.PROPERTY
							|| tagKind == PHPDocTag.PROPERTY_READ
							|| tagKind == PHPDocTag.PROPERTY_WRITE) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.property.pkg.html
						final String[] split = WHITESPACE_SEPERATOR
								.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}
						ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
						info.modifiers = Modifiers.AccPublic
								| IPHPModifiers.AccMagicProperty;
						info.name = split[1];
						info.type = split[0];

						SimpleReference var = new SimpleReference(
								docTag.sourceStart(), docTag.sourceStart() + 9,
								removeParenthesis(split));
						info.nameSourceStart = var.sourceStart();
						info.nameSourceEnd = var.sourceEnd();
						info.declarationStart = info.nameSourceStart;

						fRequestor.enterField(info);
						fRequestor.exitField(info.nameSourceEnd);

					} else if (tagKind == PHPDocTag.METHOD) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.method.pkg.html
						final String[] split = WHITESPACE_SEPERATOR
								.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}

						ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
						mi.parameterNames = null;
						mi.name = removeParenthesis(split);
						SimpleReference var = new SimpleReference(
								docTag.sourceStart(), docTag.sourceStart() + 6,
								removeParenthesis(split));
						mi.modifiers = Modifiers.AccPublic;
						mi.nameSourceStart = var.sourceStart();
						mi.nameSourceEnd = var.sourceEnd();
						mi.declarationStart = mi.nameSourceStart;
						mi.isConstructor = false;
						mi.returnType = split[0];

						MagicMethod magicMethod;
						if (mi.name != null && mi.name.indexOf('(') > 0) {
							magicMethod = MagicMemberUtil
									.getMagicMethod2(docTag.getValue());
							mi.name = magicMethod.name;
						} else {
							magicMethod = MagicMemberUtil.getMagicMethod(docTag
									.getValue());
						}
						if (magicMethod != null) {
							mi.parameterNames = magicMethod.parameterNames;
							mi.parameterTypes = magicMethod.parameterTypes;
							mi.parameterInitializers = magicMethod.parameterInitializers;
						}

						this.fRequestor.enterMethod(mi);
						this.fRequestor.exitMethod(mi.nameSourceEnd);
					}
				}
			}
		}
	}

	private String removeParenthesis(final String[] split) {
		final String name = split[1];
		return name.endsWith("()") ? name.substring(0, name.length() - 2) //$NON-NLS-1$
				: name;
	}

	public boolean visit(FieldDeclaration declaration) throws Exception {
		// This is constant declaration:
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccConstant | Modifiers.AccPublic
				| Modifiers.AccFinal;
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
		PHPDocBlock doc = declaration.getPHPDoc();
		if (doc != null) {
			for (PHPDocTag tag : doc.getTags(PHPDocTag.VAR)) {
				SimpleReference[] references = tag.getReferences();
				if (references.length > 0) {
					info.type = PHPModelUtils.extractElementName(references[0]
							.getName());
					// info.type = references[0].getName();
				}
			}
		}
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
			if (!declarations.empty()
					&& declarations.peek() instanceof MethodDeclaration) {
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
			fRequestor.acceptMethodReference(call.getName(), argsCount, call
					.getCallName().sourceStart(), call.getCallName()
					.sourceEnd());
		}
		return true;
	}

	public boolean visit(Include include) throws Exception {
		// special case for include statements; we need to cache this
		// information in order to access it quickly:
		if (include.getExpr() instanceof Scalar) {
			Scalar filePath = (Scalar) include.getExpr();
			fRequestor.acceptMethodReference("include", 0, //$NON-NLS-1$
					filePath.sourceStart(), filePath.sourceEnd());
		}
		return true;
	}

	public boolean visit(ConstantDeclaration declaration) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccConstant | Modifiers.AccPublic
				| Modifiers.AccFinal;
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
			if (dispatcher instanceof VariableReference
					&& "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
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
			if (!declarations.empty()) {
				Declaration parentDeclaration = declarations.peek();
				if (parentDeclaration instanceof MethodDeclaration
						&& methodGlobalVars.peek().contains(
								((VariableReference) left).getName())
						|| parentDeclaration == fLastNamespace) {
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

	public boolean visit(UseStatement declaration) throws Exception {
		Collection<UsePart> parts = declaration.getParts();
		for (UsePart part : parts) {
			String name = null;
			if (part.getAlias() != null) {
				name = part.getAlias().getName();
			} else {
				name = part.getNamespace().getName();
				int index = name
						.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
				if (index >= 0) {
					name = name.substring(index + 1);
				}
			}
			ImportInfo info = new ImportInfo();
			String containerName;
			if (fLastNamespace == null) {
				containerName = GLOBAL_NAMESPACE_CONTAINER_NAME;
			} else {
				containerName = fLastNamespace.getName();
			}
			info.containerName = containerName;
			info.name = part.getNamespace().getFullyQualifiedName();
			info.sourceStart = part.getNamespace().sourceStart();
			info.sourceEnd = part.getNamespace().sourceEnd();
			fRequestor.acceptImport(info);
			fLastUseParts.put(name, part);
		}
		return true;
	}

	public boolean visit(ListVariable listVariable) throws Exception {
		final Collection<? extends Expression> variables = ((ListVariable) listVariable)
				.getVariables();
		for (Expression expression : variables) {

			if (expression instanceof VariableReference) {
				ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
				info.modifiers = Modifiers.AccPublic;
				info.name = ((VariableReference) expression).getName();
				info.nameSourceEnd = expression.sourceEnd() - 1;
				info.nameSourceStart = expression.sourceStart();
				info.declarationStart = expression.sourceStart();
				fRequestor.enterField(info);
				fRequestor.exitField(expression.sourceEnd() - 1);
			}
		}
		return true;
	}

	public boolean endvisit(ListVariable listVariable) throws Exception {
		return true;
	}

	public boolean visit(GlobalStatement s) throws Exception {
		if (!declarations.empty()
				&& declarations.peek() instanceof MethodDeclaration) {
			for (Expression var : s.getVariables()) {
				if (var instanceof ReferenceExpression) {
					var = ((ReferenceExpression) var).getVariable();
				}
				if (var instanceof SimpleReference) {
					methodGlobalVars.peek().add(
							((SimpleReference) var).getName());
				}
			}
		}
		return true;
	}

	public boolean visit(TypeReference reference) throws Exception {
		fRequestor.acceptTypeReference(reference.getName(),
				reference.sourceStart());
		return true;
	}

	public boolean visit(Statement node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(node);
		}
		if (node instanceof PHPFieldDeclaration) {
			return visit((PHPFieldDeclaration) node);
		}
		if (node instanceof FieldDeclaration) {
			return visit((FieldDeclaration) node);
		}
		if (node instanceof ConstantDeclaration) {
			return visit((ConstantDeclaration) node);
		}
		if (node instanceof CatchClause) {
			return visit((CatchClause) node);
		}
		if (node instanceof ForEachStatement) {
			return visit((ForEachStatement) node);
		}
		if (node instanceof GlobalStatement) {
			return visit((GlobalStatement) node);
		}
		if (node instanceof UseStatement) {
			return visit((UseStatement) node);
		}
		return true;
	}

	public boolean endvisit(Statement node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(node);
		}
		if (node instanceof PHPFieldDeclaration) {
			return endvisit((PHPFieldDeclaration) node);
		}
		if (node instanceof FieldDeclaration) {
			return endvisit((FieldDeclaration) node);
		}
		if (node instanceof ConstantDeclaration) {
			return endvisit((ConstantDeclaration) node);
		}
		if (node instanceof CatchClause) {
			return endvisit((CatchClause) node);
		}
		if (node instanceof ForEachStatement) {
			return endvisit((ForEachStatement) node);
		}
		return true;
	}

	public boolean visit(Expression node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(node);
		}
		if (node instanceof Assignment) {
			return visit((Assignment) node);
		}
		if (node instanceof ListVariable) {
			return visit((ListVariable) node);
		}
		if (node instanceof TypeReference) {
			return visit((TypeReference) node);
		}
		if (node instanceof Include) {
			return visit((Include) node);
		}
		if (node instanceof PHPCallExpression) {
			return visit((PHPCallExpression) node);
		}
		if (node instanceof LambdaFunctionDeclaration) {
			return visit((LambdaFunctionDeclaration) node);
		}
		return true;
	}

	public boolean endvisit(Expression node) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(node);
		}
		if (node instanceof Assignment) {
			return endvisit((Assignment) node);
		}
		if (node instanceof ListVariable) {
			return endvisit((ListVariable) node);
		}
		if (node instanceof LambdaFunctionDeclaration) {
			return endvisit((LambdaFunctionDeclaration) node);
		}
		return true;
	}

	public boolean endvisit(ModuleDeclaration declaration) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(declaration);
		}

		while (deferredDeclarations != null && !deferredDeclarations.isEmpty()) {
			final ASTNode[] declarations = deferredDeclarations
					.toArray(new ASTNode[deferredDeclarations.size()]);
			deferredDeclarations.clear();

			for (ASTNode deferred : declarations) {
				deferred.traverse(this);
			}
		}
		fLastUseParts.clear();
		return super.endvisit(declaration);
	}
}
