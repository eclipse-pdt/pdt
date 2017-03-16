/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016 IBM Corporation and others.
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
import org.eclipse.dltk.compiler.IElementRequestor;
import org.eclipse.dltk.compiler.IElementRequestor.ElementInfo;
import org.eclipse.dltk.compiler.IElementRequestor.ImportInfo;
import org.eclipse.dltk.compiler.IElementRequestor.TypeInfo;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestVisitor;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.Flags;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.core.compiler.PHPSourceElementRequestorExtension;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.index.PhpIndexingVisitor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.core.util.MagicMemberUtil.MagicMethod;

/**
 * This visitor builds DLTK structured model elements.
 * 
 * @author michael
 */
public class PHPSourceElementRequestor extends SourceElementRequestVisitor {

	private static final String VOID_RETURN_TYPE = MagicMemberUtil.VOID_RETURN_TYPE;
	private static final String GLOBAL_NAMESPACE_CONTAINER_NAME = "global namespace"; //$NON-NLS-1$
	private static final String ANONYMOUS_CLASS_TEMPLATE = "new %s() {...}"; //$NON-NLS-1$
	/**
	 * This should replace the need for fInClass, fInMethod and fCurrentMethod
	 * since in php the type declarations can be nested.
	 */
	protected Stack<ASTNode> declarations = new Stack<ASTNode>();
	private PHPSourceElementRequestorExtension[] extensions;

	/**
	 * Deferred elements that where declared in method/function but should
	 * belong to the global scope.
	 */
	protected List<ASTNode> deferredDeclarations = new LinkedList<ASTNode>();

	/**
	 * Deferred elements that where declared in method/function but should
	 * belong to current namespace scope
	 */
	protected List<ASTNode> deferredNamespacedDeclarations = new LinkedList<ASTNode>();

	protected Stack<ISourceElementRequestor.ElementInfo> fInfoStack = new Stack<IElementRequestor.ElementInfo>();

	protected Map<ISourceElementRequestor.ElementInfo, List<Assignment>> fDeferredVariables = new HashMap<IElementRequestor.ElementInfo, List<Assignment>>();

	/**
	 * This stack contains a set per method, where each set contains all global
	 * variables names delcared through 'global' keyword inside this method.
	 */
	protected Stack<Set<String>> methodGlobalVars = new Stack<Set<String>>();

	protected NamespaceDeclaration fLastNamespace;
	protected Map<String, UsePart> fLastUseParts = new HashMap<String, UsePart>();
	protected ClassInstanceCreation fLastInstanceCreation = null;

	public PHPSourceElementRequestor(ISourceElementRequestor requestor, IModuleSource sourceModule) {
		super(requestor);

		// Load PHP source element requester extensions
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCorePlugin.ID,
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
		extensions = requestors.toArray(new PHPSourceElementRequestorExtension[requestors.size()]);
	}

	protected IElementRequestor getRequestor() {
		return fRequestor;
	}

	public MethodDeclaration getCurrentMethod() {
		ASTNode currDecleration = declarations.peek();
		if (currDecleration instanceof MethodDeclaration) {
			return (MethodDeclaration) currDecleration;
		}
		return null;
	}

	public boolean endvisit(LambdaFunctionDeclaration lambdaMethod) throws Exception {

		methodGlobalVars.pop();
		this.fInMethod = false;

		if (!fNodes.isEmpty() && fNodes.peek() == lambdaMethod) {
			fRequestor.exitMethod(lambdaMethod.sourceEnd() - 1);
			fInfoStack.pop();
			fNodes.pop();
		}
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(lambdaMethod);
		}
		return true;
	}

	public boolean endvisit(AnonymousClassDeclaration anonymousClassDeclaration) throws Exception {
		this.fInClass = false;

		if (!fNodes.isEmpty() && fNodes.peek() == anonymousClassDeclaration) {
			fRequestor.exitType(anonymousClassDeclaration.sourceEnd() - 1);
			fInfoStack.pop();
			fNodes.pop();
		}

		declarations.pop();

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(anonymousClassDeclaration);
		}
		return true;
	}

	public boolean endvisit(ClassInstanceCreation cic) throws Exception {
		this.fLastInstanceCreation = null;
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
			while (deferredNamespacedDeclarations != null && !deferredNamespacedDeclarations.isEmpty()) {
				final ASTNode[] declarations = deferredNamespacedDeclarations
						.toArray(new ASTNode[deferredNamespacedDeclarations.size()]);
				deferredNamespacedDeclarations.clear();

				for (ASTNode deferred : declarations) {
					deferred.traverse(this);
				}
			}
			fLastNamespace = null; // there are no nested namespaces
			if (namespaceDecl.isGlobal()) {
				return true;
			}
		}

		// resolve more type member declarations
		resolveMagicMembers(type);

		declarations.pop();

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(type);
		}

		return super.endvisit(type);
	}

	public boolean visit(LambdaFunctionDeclaration lambdaMethod) throws Exception {

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
		String[] parameters;
		ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
		mi.modifiers = Modifiers.AccPublic;
		if (arguments != null) {
			parameters = new String[arguments.size()];
			Iterator<FormalParameter> i = arguments.iterator();
			int indx = 0;
			while (i.hasNext()) {
				FormalParameter arg = i.next();
				metadata.append(arg.getName());
				parameters[indx] = arg.getName();
				if (arg.isVariadic()) {
					mi.modifiers |= IPHPModifiers.AccVariadic;
				}
				indx++;
				if (i.hasNext()) {
					metadata.append(","); //$NON-NLS-1$
				}
			}
		} else {
			parameters = new String[0];
		}

		// Add method declaration:
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(lambdaMethod);
		}

		mi.parameterNames = parameters;
		mi.name = PHPCoreConstants.ANONYMOUS;

		if (lambdaMethod.isStatic()) {
			mi.modifiers |= Modifiers.AccStatic;
		}
		mi.nameSourceStart = lambdaMethod.sourceStart();
		mi.nameSourceEnd = lambdaMethod.sourceEnd();
		mi.declarationStart = mi.nameSourceStart;
		mi.isConstructor = false;

		fInfoStack.push(mi);
		this.fRequestor.enterMethod(mi);
		this.fInMethod = true;

		if (arguments != null) {
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

		return true;
	}

	public boolean visit(AnonymousClassDeclaration anonymousClassDeclaration) throws Exception {
		ASTNode parentDeclaration = null;
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}

		if (parentDeclaration instanceof TypeDeclaration) {
			return false;
		}

		fNodes.push(anonymousClassDeclaration);
		declarations.push(anonymousClassDeclaration);

		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.visit(anonymousClassDeclaration);
		}

		List<String> superClasses = new ArrayList<String>();
		String name = null;
		if (anonymousClassDeclaration.getSuperClass() != null) {
			name = String.format(ANONYMOUS_CLASS_TEMPLATE, anonymousClassDeclaration.getSuperClass().getName());

			String superClass = processNameNode(anonymousClassDeclaration.getSuperClass());
			if (superClass != null) {
				superClasses.add(superClass);
			}
		}
		if (anonymousClassDeclaration.getInterfaceList() != null
				&& !anonymousClassDeclaration.getInterfaceList().isEmpty()) {
			if (name == null) {
				name = String.format(ANONYMOUS_CLASS_TEMPLATE,
						anonymousClassDeclaration.getInterfaceList().get(0).getName());
			}

			for (TypeReference reference : anonymousClassDeclaration.getInterfaceList()) {
				String interfaceName = processNameNode(reference);
				if (interfaceName != null) {
					superClasses.add(interfaceName);
				}
			}
		}
		if (name == null) {
			name = String.format(ANONYMOUS_CLASS_TEMPLATE, PHPCoreConstants.ANONYMOUS);
		}

		ISourceElementRequestor.TypeInfo mi = new ISourceElementRequestor.TypeInfo();
		mi.name = name;
		mi.modifiers = Modifiers.AccPrivate | IPHPModifiers.AccAnonymous;

		if (fLastInstanceCreation != null) {
			Expression className = fLastInstanceCreation.getClassName();
			mi.nameSourceStart = className.sourceStart();
			mi.nameSourceEnd = className.sourceEnd() - 1;
		}
		mi.declarationStart = mi.nameSourceStart;

		mi.superclasses = superClasses.toArray(new String[0]);

		fInfoStack.push(mi);
		this.fRequestor.enterType(mi);
		this.fInClass = true;

		return true;
	}

	public boolean visit(ClassInstanceCreation cic) throws Exception {
		fLastInstanceCreation = cic;
		return true;
	}

	public boolean visit(MethodDeclaration method) throws Exception {

		methodGlobalVars.add(new HashSet<String>());

		ASTNode parentDeclaration = null;
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}

		// In case we are entering a nested element - just add to the deferred
		// list
		// and get out of the nested element visiting process
		if (parentDeclaration instanceof MethodDeclaration) {
			if (fLastNamespace == null) {
				deferredDeclarations.add(method);
			} else {
				deferredNamespacedDeclarations.add(method);
			}
			return false;
		}

		if (parentDeclaration instanceof InterfaceDeclaration) {
			method.setModifier(Modifiers.AccAbstract);
		}

		method.setModifiers(markAsDeprecated(method.getModifiers(), method));

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

	private boolean visitMethodDeclaration(MethodDeclaration method) throws Exception {
		this.fNodes.push(method);
		List<?> args = method.getArguments();

		String[] parameter = new String[args.size()];
		String[] initializers = new String[args.size()];
		int[] flags = new int[args.size()];
		ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
		mi.modifiers = method.getModifiers();
		for (int a = 0; a < args.size(); a++) {
			Argument arg = (Argument) args.get(a);
			parameter[a] = arg.getName();
			if (arg.getInitialization() != null) {
				if (arg.getInitialization() instanceof Literal) {
					Literal scalar = (Literal) arg.getInitialization();
					initializers[a] = scalar.getValue();
				} else if (arg.getInitialization() instanceof ArrayCreation) {
					ArrayCreation arrayCreation = (ArrayCreation) arg.getInitialization();
					if (arrayCreation.getElements().isEmpty()) {
						initializers[a] = PhpIndexingVisitor.EMPTY_ARRAY_VALUE;
					} else {
						initializers[a] = PhpIndexingVisitor.ARRAY_VALUE;
					}
				} else {
					initializers[a] = PhpIndexingVisitor.DEFAULT_VALUE;
				}
			}
			if (arg instanceof FormalParameterByReference) {
				flags[a] = IPHPModifiers.AccReference;
			}
			if (arg instanceof FormalParameter) {
				FormalParameter fp = (FormalParameter) arg;
				if (fp.isVariadic()) {
					mi.modifiers |= IPHPModifiers.AccVariadic;
				}
				if (fp.getParameterType() instanceof FullyQualifiedReference) {
					if (((FullyQualifiedReference) fp.getParameterType()).isNullable()) {
						flags[a] |= IPHPModifiers.AccNullable;
					}
				}
			}
		}

		mi.parameterNames = parameter;
		mi.name = method.getName();
		mi.nameSourceStart = method.getNameStart();
		mi.nameSourceEnd = method.getNameEnd() - 1;
		mi.declarationStart = method.sourceStart();
		mi.parameterInitializers = initializers;
		mi.parameterFlags = flags;

		modifyMethodInfo(method, mi);

		fInfoStack.push(mi);
		this.fRequestor.enterMethod(mi);

		this.fInMethod = true;
		this.fCurrentMethod = method;
		return true;
	}

	protected void modifyMethodInfo(MethodDeclaration methodDeclaration, ISourceElementRequestor.MethodInfo mi) {
		ASTNode parentDeclaration = null;

		// find declaration that was before this method:
		declarations.pop();
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}
		declarations.push(methodDeclaration);

		mi.isConstructor = mi.name.equalsIgnoreCase(PhpIndexingVisitor.CONSTRUCTOR_NAME)
				|| (parentDeclaration instanceof ClassDeclaration
						&& mi.name.equalsIgnoreCase(((ClassDeclaration) parentDeclaration).getName()));

		if (fCurrentClass == null || fCurrentClass == fLastNamespace) {
			mi.modifiers |= Modifiers.AccGlobal;
		}
		if (!Flags.isPrivate(mi.modifiers) && !Flags.isProtected(mi.modifiers) && !Flags.isPublic(mi.modifiers)) {
			mi.modifiers |= Modifiers.AccPublic;
		}
		if (hasInheritdocTag(methodDeclaration)) {
			mi.modifiers |= IPHPModifiers.AccInheritdoc;
		}

		mi.parameterTypes = processParameterTypes(methodDeclaration);
		modifyReturnTypeInfo(methodDeclaration, mi);

		// modify method info if needed by extensions
		for (PHPSourceElementRequestorExtension extension : extensions) {
			extension.modifyMethodInfo(methodDeclaration, mi);
		}
		if (mi.returnType != null) {
			mi.modifiers |= IPHPModifiers.AccReturn;
		} else if (methodDeclaration.getBody() != null) {
			// check
			ReturnDetector detector = new ReturnDetector();
			try {
				methodDeclaration.getBody().traverse(detector);
				if (detector.hasReturn()) {
					mi.modifiers |= IPHPModifiers.AccReturn;
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	private boolean hasInheritdocTag(MethodDeclaration methodDeclaration) {
		PHPMethodDeclaration phpMethodDeclaration = (PHPMethodDeclaration) methodDeclaration;
		PHPDocBlock docBlock = phpMethodDeclaration.getPHPDoc();
		if (docBlock == null) {
			return false;
		}
		return docBlock.getTags(TagKind.INHERITDOC).length != 0;
	}

	private String[] processParameterTypes(MethodDeclaration methodDeclaration) {
		List<?> args = methodDeclaration.getArguments();
		PHPDocBlock docBlock = ((PHPMethodDeclaration) methodDeclaration).getPHPDoc();
		String[] parameterType = new String[args.size()];
		for (int a = 0; a < args.size(); a++) {
			Argument arg = (Argument) args.get(a);
			if (arg instanceof FormalParameter) {
				SimpleReference type = ((FormalParameter) arg).getParameterType();
				if (type != null) {
					parameterType[a] = type.getName();
				} else if (docBlock != null) {
					for (PHPDocTag tag : docBlock.getTags(TagKind.PARAM)) {
						if (tag.isValidParamTag() && tag.getVariableReference().getName().equals(arg.getName())) {
							parameterType[a] = tag.getSingleTypeReference().getName();
							break;
						}
					}
				}
			}
		}
		return parameterType;
	}

	private void modifyReturnTypeInfo(MethodDeclaration methodDeclaration, ISourceElementRequestor.MethodInfo mi) {
		PHPMethodDeclaration phpMethodDeclaration = (PHPMethodDeclaration) methodDeclaration;
		PHPDocBlock docBlock = phpMethodDeclaration.getPHPDoc();
		TypeReference returnType = phpMethodDeclaration.getReturnType();
		if (returnType != null) {
			mi.returnType = returnType.getName();
			if (returnType instanceof FullyQualifiedReference) {
				if (((FullyQualifiedReference) returnType).isNullable()) {
					mi.modifiers |= IPHPModifiers.AccNullable;
				}
			}
		} else if (docBlock != null) {
			for (PHPDocTag tag : docBlock.getTags(TagKind.RETURN)) {
				if (tag.getTypeReferences().size() > 0) {
					mi.returnType = PHPModelUtils.appendTypeReferenceNames(tag.getTypeReferences());
				}
			}
		}
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
		type.setModifiers(markAsDeprecated(type.getModifiers(), type));

		// In case we are entering a nested element
		if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration) {
			if (fLastNamespace == null) {
				deferredDeclarations.add(type);
			} else {
				deferredNamespacedDeclarations.add(type);
			}
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
		List<ASTNode> superClassNames = superClasses.getChilds();
		List<String> result = new ArrayList<String>(superClassNames.size());
		Iterator<ASTNode> iterator = superClassNames.iterator();
		while (iterator.hasNext()) {
			String name = processNameNode(iterator.next());
			if (name != null) {
				result.add(name);
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	private String processNameNode(ASTNode nameNode) {
		if (nameNode instanceof FullyQualifiedReference) {
			String name;
			FullyQualifiedReference fullyQualifiedName = (FullyQualifiedReference) nameNode;
			name = fullyQualifiedName.getFullyQualifiedName();
			if (fullyQualifiedName.getNamespace() != null) {
				String namespace = fullyQualifiedName.getNamespace().getName();

				String subnamespace = ""; //$NON-NLS-1$
				if (namespace.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR
						&& namespace.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
					int firstNSLocation = namespace.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
					subnamespace = namespace.substring(firstNSLocation);
					namespace = namespace.substring(0, firstNSLocation);
				}
				if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
					name = name.substring(1);
				} else if (fLastUseParts.containsKey(namespace)) {
					name = new StringBuilder(fLastUseParts.get(namespace).getNamespace().getFullyQualifiedName())
							.append(subnamespace).append(NamespaceReference.NAMESPACE_SEPARATOR)
							.append(fullyQualifiedName.getName()).toString();
				} else if (fLastNamespace != null) {
					name = new StringBuilder(fLastNamespace.getName()).append(NamespaceReference.NAMESPACE_SEPARATOR)
							.append(name).toString();
				}
			} else if (fLastUseParts.containsKey(name)) {
				name = fLastUseParts.get(name).getNamespace().getFullyQualifiedName();
				if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
					name = name.substring(1);
				}
			} else {
				if (fLastNamespace != null) {
					name = new StringBuilder(fLastNamespace.getName()).append(NamespaceReference.NAMESPACE_SEPARATOR)
							.append(name).toString();
				}
			}
			return name;
		} else if (nameNode instanceof SimpleReference) {
			return ((SimpleReference) nameNode).getName();
		}
		return null;
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
				Pattern WHITESPACE_SEPERATOR = MagicMemberUtil.WHITESPACE_SEPERATOR;
				final PHPDocTag[] tags = doc.getTags();
				for (PHPDocTag docTag : tags) {
					final TagKind tagKind = docTag.getTagKind();
					if (tagKind == TagKind.PROPERTY || tagKind == TagKind.PROPERTY_READ
							|| tagKind == TagKind.PROPERTY_WRITE) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.property.pkg.html
						final String[] split = WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
						if (split.length < 2) {
							continue;
						}
						ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
						info.modifiers = Modifiers.AccPublic | IPHPModifiers.AccMagicProperty;
						info.name = split[1];
						info.type = split[0];

						SimpleReference var = new SimpleReference(docTag.sourceStart(), docTag.sourceStart() + 9,
								removeParenthesis(split));
						info.nameSourceStart = var.sourceStart();
						info.nameSourceEnd = var.sourceEnd();
						info.declarationStart = info.nameSourceStart;

						fRequestor.enterField(info);
						fRequestor.exitField(info.nameSourceEnd);

					} else if (tagKind == TagKind.METHOD) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.method.pkg.html

						// workaround for lack of method return type
						int methodModifiers = Modifiers.AccPublic;
						String docTagValue = docTag.getValue().trim();
						int index = docTagValue.indexOf('(');
						if (index != -1) {
							String[] split = WHITESPACE_SEPERATOR.split(docTagValue.substring(0, index).trim());
							if (split.length == 1) {
								docTagValue = new StringBuilder(VOID_RETURN_TYPE).append(Constants.SPACE)
										.append(docTagValue).toString();
							} else if (split.length == 2 && Constants.STATIC.equals(split[0])) {
								StringBuilder sb = new StringBuilder(Constants.STATIC);
								sb.append(Constants.SPACE).append(VOID_RETURN_TYPE);
								sb.append(docTagValue.substring(6));
								docTagValue = sb.toString();
							}
						}
						String[] split = WHITESPACE_SEPERATOR.split(docTagValue);
						if (split.length < 2) {
							continue;
						}
						if (Constants.STATIC.equals(split[0])) {
							methodModifiers |= Modifiers.AccStatic;
							split = Arrays.copyOfRange(split, 1, split.length);
							docTagValue = docTagValue.substring(7).trim();
							if (split.length < 2) {
								continue;
							}
						}

						ISourceElementRequestor.MethodInfo mi = new ISourceElementRequestor.MethodInfo();
						mi.parameterNames = null;
						mi.name = removeParenthesis(split);
						SimpleReference var = new SimpleReference(docTag.sourceStart(), docTag.sourceStart() + 6,
								removeParenthesis(split));
						mi.modifiers = methodModifiers;
						mi.nameSourceStart = var.sourceStart();
						mi.nameSourceEnd = var.sourceEnd();
						mi.declarationStart = mi.nameSourceStart;
						mi.isConstructor = false;
						mi.returnType = split[0];

						MagicMethod magicMethod;
						if (mi.name != null && mi.name.indexOf('(') > 0) {
							magicMethod = MagicMemberUtil.getMagicMethod2(docTagValue);
							mi.name = magicMethod.name;
						} else {
							magicMethod = MagicMemberUtil.getMagicMethod(docTagValue);
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
		info.modifiers = markAsDeprecated(info.modifiers, declaration);
		PHPDocBlock doc = declaration.getPHPDoc();
		if (doc != null) {
			for (PHPDocTag tag : doc.getTags(TagKind.VAR)) {
				// do it like for
				// PHPDocumentationContentAccess#handleBlockTags(List tags):
				// variable name can be optional, but if present keep only
				// the good ones
				if (tag.getVariableReference() != null
						&& !tag.getVariableReference().getName().equals(declaration.getName())) {
					continue;
				}

				if (tag.getTypeReferences().size() > 0) {
					info.type = PHPModelUtils.appendTypeReferenceNames(tag.getTypeReferences());
					break;
				}
			}
		}
		fInfoStack.push(info);
		fRequestor.enterField(info);
		return true;
	}

	/**
	 * Update modifiers for "deprecated"
	 * 
	 * @param modifiers
	 * @param phpDoc
	 * @return
	 */
	private int markAsDeprecated(int modifiers, PHPDocBlock phpDoc) {
		if (phpDoc != null && phpDoc.getTags(TagKind.DEPRECATED).length > 0) {
			return modifiers | IPHPModifiers.AccDeprecated;
		}

		return modifiers;
	}

	private int markAsDeprecated(int modifiers, ASTNode node) {
		if (node instanceof IPHPDocAwareDeclaration) {
			return markAsDeprecated(modifiers, ((IPHPDocAwareDeclaration) node).getPHPDoc());
		}
		return modifiers;
	}

	public boolean visit(CatchClause catchClause) throws Exception {
		ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
		info.modifiers = Modifiers.AccPublic;
		SimpleReference var = catchClause.getVariable();
		info.name = var.getName();
		info.nameSourceEnd = var.sourceEnd() - 1;
		info.nameSourceStart = var.sourceStart();
		info.declarationStart = catchClause.sourceStart();

		fInfoStack.push(info);
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(CatchClause catchClause) throws Exception {
		fRequestor.exitField(catchClause.sourceEnd() - 1);
		fInfoStack.pop();
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
		fInfoStack.pop();
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
			fRequestor.acceptMethodReference(call.getName(), argsCount, call.getCallName().sourceStart(),
					call.getCallName().sourceEnd());
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
		int accessModifier = declaration.getModifiers() == 0 ? Modifiers.AccPublic : declaration.getModifiers();
		info.modifiers = Modifiers.AccConstant | Modifiers.AccFinal | accessModifier;
		ConstantReference constantName = declaration.getConstantName();
		info.name = ASTUtils.stripQuotes(constantName.getName());
		info.nameSourceEnd = constantName.sourceEnd() - 1;
		info.nameSourceStart = constantName.sourceStart();
		info.declarationStart = declaration.sourceStart();
		info.modifiers = markAsDeprecated(info.modifiers, declaration);

		fInfoStack.push(info);
		fRequestor.enterField(info);
		return true;
	}

	public boolean endvisit(ConstantDeclaration declaration) throws Exception {
		fRequestor.exitField(declaration.sourceEnd() - 1);
		fInfoStack.pop();
		return true;
	}

	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof FieldAccess) { // class variable ($this->a = .)
			FieldAccess fieldAccess = (FieldAccess) left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference && "$this" //$NON-NLS-1$
					.equals(((VariableReference) dispatcher).getName())) {
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					SimpleReference ref = (SimpleReference) field;
					ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
					info.modifiers = Modifiers.AccPublic;
					info.name = '$' + ref.getName();
					info.nameSourceEnd = ref.sourceEnd() - 1;
					info.nameSourceStart = ref.sourceStart();
					info.declarationStart = assignment.sourceStart();
					fInfoStack.push(info);
					fRequestor.enterField(info);
					fNodes.push(assignment);
				}
			}
		} else if (left instanceof VariableReference) {
			if (!declarations.empty()) {
				ASTNode parentDeclaration = declarations.peek();
				if (parentDeclaration instanceof MethodDeclaration
						&& methodGlobalVars.peek().contains(((VariableReference) left).getName())
						|| parentDeclaration == fLastNamespace) {
					deferredDeclarations.add(assignment);
					return false;
				}
			}
			if (!fInfoStack.isEmpty() && fInfoStack.peek() instanceof ISourceElementRequestor.FieldInfo) {
				if (!fDeferredVariables.containsKey(fInfoStack.peek())) {
					fDeferredVariables.put(fInfoStack.peek(), new LinkedList<Assignment>());
				}
				fDeferredVariables.get(fInfoStack.peek()).add(assignment);
				return false;
			}
			ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
			info.modifiers = Modifiers.AccPublic;
			info.name = ((VariableReference) left).getName();
			info.nameSourceEnd = left.sourceEnd() - 1;
			info.nameSourceStart = left.sourceStart();
			info.declarationStart = assignment.sourceStart();

			fInfoStack.push(info);
			ISourceElementRequestor sourceElementRequestor = (ISourceElementRequestor) fRequestor;
			sourceElementRequestor.enterFieldCheckDuplicates(info);
			fNodes.push(assignment);
		}
		return true;

	}

	public boolean endvisit(Assignment assignment) throws Exception {
		if (!fNodes.isEmpty() && fNodes.peek() == assignment) {
			fRequestor.exitField(assignment.sourceEnd() - 1);
			fNodes.pop();
			ElementInfo currentField = fInfoStack.pop();
			if (fDeferredVariables.containsKey(currentField)) {
				for (Assignment assign : fDeferredVariables.get(currentField)) {
					assign.traverse(this);
				}

				fDeferredVariables.remove(currentField);
			}
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
				int index = name.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
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
			if (declaration.getNamespace() == null) {
				info.name = part.getNamespace().getFullyQualifiedName();
			} else {
				info.name = PHPModelUtils.concatFullyQualifiedNames(declaration.getNamespace().getFullyQualifiedName(),
						part.getNamespace().getFullyQualifiedName());
			}
			if (part.getAlias() != null) {
				info.alias = part.getAlias().getName();
			}
			info.type = part.getStatementType();
			info.sourceStart = part.getNamespace().sourceStart();
			info.sourceEnd = part.getNamespace().sourceEnd();
			fRequestor.acceptImport(info);
			fLastUseParts.put(name, part);
		}
		return true;
	}

	public boolean visit(ListVariable listVariable) throws Exception {
		final Collection<? extends Expression> variables = ((ListVariable) listVariable).getVariables();
		for (Expression expression : variables) {

			VariableReference varReference = null;
			if (expression instanceof VariableReference) {
				varReference = (VariableReference) expression;
			} else if (expression instanceof ArrayElement) {
				ArrayElement arrayElement = (ArrayElement) expression;
				if (arrayElement.getValue() instanceof VariableReference) {
					varReference = (VariableReference) arrayElement.getValue();
				}
			}

			if (varReference != null) {
				ISourceElementRequestor.FieldInfo info = new ISourceElementRequestor.FieldInfo();
				info.modifiers = Modifiers.AccPublic;
				info.name = varReference.getName();
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
		fRequestor.acceptTypeReference(reference.getName(), reference.sourceStart());
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
		if (node instanceof AnonymousClassDeclaration) {
			return visit((AnonymousClassDeclaration) node);
		}
		if (node instanceof ClassInstanceCreation) {
			return visit((ClassInstanceCreation) node);
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
		if (node instanceof AnonymousClassDeclaration) {
			return endvisit((AnonymousClassDeclaration) node);
		}
		if (node instanceof ClassInstanceCreation) {
			return endvisit((ClassInstanceCreation) node);
		}
		return true;
	}

	public boolean endvisit(ModuleDeclaration declaration) throws Exception {
		for (PHPSourceElementRequestorExtension visitor : extensions) {
			visitor.endvisit(declaration);
		}

		while (deferredDeclarations != null && !deferredDeclarations.isEmpty()) {
			final ASTNode[] declarations = deferredDeclarations.toArray(new ASTNode[deferredDeclarations.size()]);
			deferredDeclarations.clear();

			for (ASTNode deferred : declarations) {
				deferred.traverse(this);
			}
		}
		fLastUseParts.clear();
		return super.endvisit(declaration);
	}
}
