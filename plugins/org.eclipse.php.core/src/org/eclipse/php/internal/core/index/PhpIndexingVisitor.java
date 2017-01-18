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
package org.eclipse.php.internal.core.index;

import java.util.*;
import java.util.Map.Entry;

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
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.IIndexingRequestor;
import org.eclipse.dltk.core.index2.IIndexingRequestor.DeclarationInfo;
import org.eclipse.dltk.core.index2.IIndexingRequestor.ReferenceInfo;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.core.index.PhpIndexingVisitorExtension;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ReturnDetector;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.util.MagicMemberUtil;

/**
 * PHP indexing visitor for H2 database
 * 
 * @author michael
 * 
 */
public class PhpIndexingVisitor extends PhpIndexingVisitorExtension {

	private static final String DOLLAR = "$"; //$NON-NLS-1$
	private static final String EXTENSION_POINT = "phpIndexingVisitors"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$
	public static final String CONSTRUCTOR_NAME = "__construct"; //$NON-NLS-1$
	public static final String PARAMETER_SEPERATOR = "|"; //$NON-NLS-1$
	public static final String NULL_VALUE = "#"; //$NON-NLS-1$
	public static final char QUALIFIER_SEPERATOR = ';';
	public static final char RETURN_TYPE_SEPERATOR = ':';
	public static final String DEFAULT_VALUE = " "; //$NON-NLS-1$
	public static final String EMPTY_ARRAY_VALUE = "[]"; //$NON-NLS-1$
	public static final String ARRAY_VALUE = "[...]"; //$NON-NLS-1$
	/**
	 * This should replace the need for fInClass, fInMethod and fCurrentMethod
	 * since in php the type declarations can be nested.
	 */
	protected Stack<Declaration> declarations = new Stack<Declaration>();

	/**
	 * Deferred elements that where declared in method/function but should
	 * belong to the global scope.
	 */
	protected List<ASTNode> deferredDeclarations = new LinkedList<ASTNode>();

	/**
	 * Deferred elements that where declared in method/function but should
	 * belong to the namespaced scope.
	 */
	protected List<ASTNode> deferredNamespacedDeclarations = new LinkedList<ASTNode>();

	/**
	 * This stack contains a set per method, where each set contains all global
	 * variable names declared through 'global' keyword inside this method.
	 */
	protected Stack<Set<String>> methodGlobalVars = new Stack<Set<String>>();

	/**
	 * This set contains all variable names having global scope. NB: this set
	 * has nothing to do with stack "methodGlobalVars" which only stores
	 * variable names declared through 'global' keyword.
	 */
	protected Set<String> globalScopeVars = new HashSet<String>();

	/**
	 * Extensions indexing visitor extensions
	 */
	private PhpIndexingVisitorExtension[] extensions;
	private static IConfigurationElement[] extensionElements = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(PHPCorePlugin.ID, EXTENSION_POINT);

	protected NamespaceDeclaration fCurrentNamespace;
	protected Map<String, UsePart> fLastUseParts = new HashMap<String, UsePart>();
	protected String fCurrentQualifier;
	protected Map<String, Integer> fCurrentQualifierCounts = new HashMap<String, Integer>();
	protected String fCurrentParent;
	protected Stack<ASTNode> fNodes = new Stack<ASTNode>();

	public PhpIndexingVisitor(IIndexingRequestor requestor, ISourceModule module) {
		this.requestor = requestor;

		List<PhpIndexingVisitorExtension> extensions = new ArrayList<PhpIndexingVisitorExtension>(
				extensionElements.length);
		for (IConfigurationElement element : extensionElements) {
			try {
				PhpIndexingVisitorExtension ext = (PhpIndexingVisitorExtension) element
						.createExecutableExtension(CLASS_ATTR);
				ext.setRequestor(requestor);
				// pass the ISourceModule over to the extension
				// in case it needs it during indexing
				ext.setSourceModule(module);
				extensions.add(ext);
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
		this.extensions = extensions.toArray(new PhpIndexingVisitorExtension[extensions.size()]);
	}

	public void modifyDeclaration(ASTNode node, DeclarationInfo info) {
		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.modifyDeclaration(node, info);
		}
		if (info.elementType != IModelElement.PACKAGE_DECLARATION) {
			if (info.parent == null) {
				info.parent = PHPCoreConstants.FILE_PARENT;
			}
			if (info.qualifier == null) {
				info.qualifier = PHPCoreConstants.GLOBAL_NAMESPACE;
			}
		}

		if (node != null && (info.flags & IPHPModifiers.AccReturn) == 0 && node instanceof MethodDeclaration) {
			MethodDeclaration m = (MethodDeclaration) node;
			if (m.getBody() != null) {
				ReturnDetector detector = new ReturnDetector();
				try {
					m.getBody().traverse(detector);
					if (detector.hasReturn()) {
						info.flags |= IPHPModifiers.AccReturn;
					}
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}

		requestor.addDeclaration(info);
	}

	public void modifyReference(ASTNode node, ReferenceInfo info) {
		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.modifyReference(node, info);
		}
		requestor.addReference(info);
	}

	/**
	 * See {@link PhpElementResolver#decodeDocInfo(String)} for the decoding
	 * routine.
	 * 
	 * @param declaration
	 *            Declaration ASTNode
	 * @return decoded PHPDoc info, or <code>null</code> if there's no PHPDoc
	 *         info to store.
	 */
	protected static String encodeDocInfo(Declaration declaration) {
		if (declaration instanceof IPHPDocAwareDeclaration) {
			PHPDocBlock docBlock = ((IPHPDocAwareDeclaration) declaration).getPHPDoc();
			if (docBlock != null) {
				Map<String, String> info = new HashMap<String, String>();
				for (PHPDocTag tag : docBlock.getTags()) {
					if (tag.getTagKind() == TagKind.DEPRECATED) {
						info.put("d", null); //$NON-NLS-1$
					} else if (tag.getTagKind() == TagKind.RETURN) {
						StringBuilder buf = new StringBuilder();
						for (TypeReference ref : tag.getTypeReferences()) {
							String type = ref.getName().replaceAll(",", "~"); //$NON-NLS-1$ //$NON-NLS-2$
							if (buf.length() > 0) {
								buf.append(',');
							}
							buf.append(type);
						}
						info.put("r", buf.toString()); //$NON-NLS-1$
					} else if (tag.getTagKind() == TagKind.VAR) {
						if (tag.getTypeReferences().size() > 0) {
							String typeNames = PHPModelUtils.appendTypeReferenceNames(tag.getTypeReferences());
							typeNames = typeNames.replace(Constants.TYPE_SEPERATOR_CHAR, Constants.DOT);

							info.put("v", typeNames); //$NON-NLS-1$
						}
					}
				}
				return encodeDocInfo(info);
			}
		}
		return null;
	}

	protected static String encodeDocInfo(Map<String, String> info) {
		if (info == null) {
			return null;
		}

		StringBuilder buf = new StringBuilder();
		for (Entry<String, String> e : info.entrySet()) {
			if (buf.length() > 0) {
				buf.append(';');
			}
			buf.append(e.getKey());
			if (e.getValue() != null) {
				buf.append(':').append(e.getValue());
			}
		}
		return buf.length() > 0 ? buf.toString() : null;
	}

	public boolean endvisit(MethodDeclaration method) throws Exception {
		methodGlobalVars.pop();
		declarations.pop();

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.endvisit(method);
		}

		endvisitGeneral(method);
		return true;
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

			// resolve more type member declarations
			resolveMagicMembers(type);

			fCurrentNamespace = null; // there are no nested namespaces
			fCurrentQualifier = null;
			fLastUseParts.clear();
			if (namespaceDecl.isGlobal()) {
				return visitGeneral(type);
			}
		} else {
			// resolve more type member declarations
			resolveMagicMembers(type);

			fCurrentParent = null;
		}
		declarations.pop();

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.endvisit(type);
		}

		endvisitGeneral(type);
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration method) throws Exception {
		fNodes.push(method);
		methodGlobalVars.add(new HashSet<String>());
		int modifiers = method.getModifiers();
		PHPDocBlock doc = null;
		if (method instanceof IPHPDocAwareDeclaration) {
			IPHPDocAwareDeclaration declaration = (IPHPDocAwareDeclaration) method;
			doc = declaration.getPHPDoc();
		}
		Declaration parentDeclaration = null;
		if (!declarations.empty()) {
			parentDeclaration = declarations.peek();
		}
		declarations.push(method);

		// In case we are entering a nested element - just add to the deferred
		// list
		// and get out of the nested element visiting process
		if (parentDeclaration instanceof MethodDeclaration) {
			if (fCurrentNamespace == null) {
				deferredDeclarations.add(method);
			} else {
				deferredNamespacedDeclarations.add(method);
			}
			return visitGeneral(method);
		}

		if (parentDeclaration instanceof InterfaceDeclaration) {
			method.setModifier(Modifiers.AccAbstract);
		}

		String methodName = method.getName();

		// Determine whether this method represents constructor:
		if (methodName.equalsIgnoreCase(CONSTRUCTOR_NAME) || (parentDeclaration instanceof ClassDeclaration
				&& methodName.equalsIgnoreCase(((ClassDeclaration) parentDeclaration).getName()))) {
			modifiers |= IPHPModifiers.Constructor;
		}

		if (parentDeclaration == null
				|| (parentDeclaration instanceof TypeDeclaration && parentDeclaration == fCurrentNamespace)) {
			modifiers |= Modifiers.AccGlobal;
		}
		if (!Flags.isPrivate(modifiers) && !Flags.isProtected(modifiers) && !Flags.isPublic(modifiers)) {
			modifiers |= Modifiers.AccPublic;
		}
		if (doc != null && doc.getTags(TagKind.INHERITDOC).length != 0) {
			modifiers |= IPHPModifiers.AccInheritdoc;
		}

		modifiers = markAsDeprecated(modifiers, method);

		StringBuilder metadata = new StringBuilder();
		metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
		metadata.append(QUALIFIER_SEPERATOR);
		if (method instanceof PHPMethodDeclaration) {
			TypeReference returnType = ((PHPMethodDeclaration) method).getReturnType();
			if (returnType != null) {
				metadata.append(returnType.getName());
				modifiers |= IPHPModifiers.AccReturn;

				if (returnType instanceof FullyQualifiedReference) {
					if (((FullyQualifiedReference) returnType).isNullable()) {
						modifiers |= IPHPModifiers.AccNullable;
					}
				}
			}
		}
		metadata.append(RETURN_TYPE_SEPERATOR);

		List<Argument> arguments = method.getArguments();
		if (arguments != null) {
			Iterator<Argument> i = arguments.iterator();
			while (i.hasNext()) {
				Argument arg = (Argument) i.next();

				String type = NULL_VALUE;
				if (arg instanceof FormalParameter) {
					FormalParameter fp = (FormalParameter) arg;
					if (fp.getParameterType() != null) {
						if (fp.getParameterType().getName() != null) {
							type = fp.getParameterType().getName();
						}
					}
					if (fp.isVariadic()) {
						modifiers |= IPHPModifiers.AccVariadic;
					}
				}
				if (type == NULL_VALUE && doc != null) {
					type = getParamType(doc, arg.getName(), type);
				}

				metadata.append(type);
				metadata.append(PARAMETER_SEPERATOR);
				metadata.append(arg.getName());
				metadata.append(PARAMETER_SEPERATOR);
				String defaultValue = NULL_VALUE;
				if (arg.getInitialization() != null) {
					if (arg.getInitialization() instanceof Literal) {
						Literal scalar = (Literal) arg.getInitialization();
						// we need to encode all pipe characters inside string
						// literals
						defaultValue = scalar.getValue().replace("&", "&a").replace(PARAMETER_SEPERATOR, "&p"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					} else if (arg.getInitialization() instanceof ArrayCreation) {
						ArrayCreation arrayCreation = (ArrayCreation) arg.getInitialization();
						if (arrayCreation.getElements().isEmpty()) {
							defaultValue = EMPTY_ARRAY_VALUE;
						} else {
							defaultValue = ARRAY_VALUE;
						}
					} else {
						defaultValue = DEFAULT_VALUE;
					}
				}
				metadata.append(defaultValue);
				int paramModifiers = 0;

				if (arg instanceof FormalParameter) {
					FormalParameter fp = (FormalParameter) arg;
					if (fp.getParameterType() instanceof FullyQualifiedReference) {
						if (((FullyQualifiedReference) fp.getParameterType()).isNullable()) {
							paramModifiers |= IPHPModifiers.AccNullable;
						}
					}
				}
				if (arg instanceof FormalParameterByReference) {
					paramModifiers |= IPHPModifiers.AccReference;
				}

				if (paramModifiers != 0) {
					metadata.append(PARAMETER_SEPERATOR);
					metadata.append(String.valueOf(paramModifiers));
				}

				if (i.hasNext()) {
					metadata.append(","); //$NON-NLS-1$
				}
			}
		}

		// Add method declaration:
		modifyDeclaration(method,
				new DeclarationInfo(IModelElement.METHOD, modifiers, method.sourceStart(),
						method.sourceEnd() - method.sourceStart(), method.getNameStart(),
						method.getNameEnd() - method.getNameStart(), methodName, metadata.toString(),
						encodeDocInfo(method), fCurrentQualifier, fCurrentParent));

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.visit(method);
		}

		return visitGeneral(method);
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

	private String getParamType(PHPDocBlock docBlock, String paramName, String defaultType) {
		String result = defaultType;
		if (docBlock != null) {
			for (PHPDocTag tag : docBlock.getTags(TagKind.PARAM)) {
				if (tag.isValidParamTag() && tag.getVariableReference().getName().equals(paramName)) {
					String typeNames = tag.getSingleTypeReference().getName();
					result = typeNames.replace(Constants.TYPE_SEPERATOR_CHAR, Constants.DOT);
					break;
				}
			}
		}
		return result;
	}

	public boolean visit(TypeDeclaration type) throws Exception {
		if (type instanceof NamespaceDeclaration) {
			NamespaceDeclaration namespaceDecl = (NamespaceDeclaration) type;
			fCurrentNamespace = namespaceDecl;
			fLastUseParts.clear();
			if (namespaceDecl.isGlobal()) {
				return visitGeneral(type);
			}
			declarations.push(type);

			int modifiers = type.getModifiers() | Modifiers.AccNameSpace;
			fCurrentQualifier = type.getName();
			Integer count = fCurrentQualifierCounts.get(fCurrentQualifier);
			count = count != null ? count + 1 : 1;
			fCurrentQualifierCounts.put(fCurrentQualifier, count);

			modifiers = markAsDeprecated(modifiers, type);
			StringBuilder metadata = new StringBuilder();
			metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
			metadata.append(QUALIFIER_SEPERATOR); // $NON-NLS-1$
			modifyDeclaration(type,
					new DeclarationInfo(IModelElement.PACKAGE_DECLARATION, modifiers, type.sourceStart(),
							type.sourceEnd() - type.sourceStart(), type.getNameStart(),
							type.getNameEnd() - type.getNameStart(), type.getName(), metadata.toString(),
							encodeDocInfo(type), null, null));
		} else {
			Declaration parentDeclaration = null;
			if (!declarations.empty()) {
				parentDeclaration = declarations.peek();
			}
			declarations.push(type);

			if (!(parentDeclaration instanceof NamespaceDeclaration)) {
				type.setModifier(Modifiers.AccGlobal);
			}

			// In case we are entering a nested element
			if (parentDeclaration instanceof MethodDeclaration) {
				if (fCurrentNamespace == null) {
					deferredDeclarations.add(type);
				} else {
					deferredNamespacedDeclarations.add(type);
				}
				return visitGeneral(type);
			}

			int modifiers = type.getModifiers();
			fCurrentParent = type.getName();

			String[] superClasses = processSuperClasses(type);
			StringBuilder metadata = new StringBuilder();
			metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
			metadata.append(QUALIFIER_SEPERATOR);
			for (int i = 0; i < superClasses.length; ++i) {
				metadata.append(superClasses[i]);
				if (i < superClasses.length - 1) {
					metadata.append(","); //$NON-NLS-1$
				}
			}
			modifiers = markAsDeprecated(modifiers, type);
			modifyDeclaration(type, new DeclarationInfo(IModelElement.TYPE, modifiers, type.sourceStart(),
					type.sourceEnd() - type.sourceStart(), type.getNameStart(), type.getNameEnd() - type.getNameStart(),
					type.getName(), metadata.toString(), encodeDocInfo(type), fCurrentQualifier, null));
		}

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.visit(type);
		}

		return visitGeneral(type);
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
			ASTNode nameNode = iterator.next();
			String name;
			if (nameNode instanceof FullyQualifiedReference) {
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
					} else if (fCurrentNamespace != null) {
						name = new StringBuilder(fCurrentNamespace.getName())
								.append(NamespaceReference.NAMESPACE_SEPARATOR).append(name).toString();
					}
				} else if (fLastUseParts.containsKey(name)) {
					name = fLastUseParts.get(name).getNamespace().getFullyQualifiedName();
					if (name.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
						name = name.substring(1);
					}
				} else {
					if (fCurrentNamespace != null) {
						name = new StringBuilder(fCurrentNamespace.getName())
								.append(NamespaceReference.NAMESPACE_SEPARATOR).append(name).toString();
					}
				}
				result.add(name);
			} else if (nameNode instanceof SimpleReference) {
				result.add(((SimpleReference) nameNode).getName());
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
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
				for (PHPDocTag docTag : doc.getTags()) {
					final TagKind tagKind = docTag.getTagKind();
					if (tagKind == TagKind.PROPERTY || tagKind == TagKind.PROPERTY_READ
							|| tagKind == TagKind.PROPERTY_WRITE) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.property.pkg.html
						final String[] split = MagicMemberUtil.WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}

						String name = removeParenthesis(split);
						int offset = docTag.sourceStart();
						int length = docTag.sourceStart() + 9;

						Map<String, String> info = new HashMap<String, String>();
						info.put("v", split[0]); //$NON-NLS-1$

						StringBuilder metadata = new StringBuilder();
						metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
						metadata.append(QUALIFIER_SEPERATOR);

						modifyDeclaration(null,
								new DeclarationInfo(IModelElement.FIELD, Modifiers.AccPublic, offset, length, offset,
										length, name, metadata.toString(), encodeDocInfo(info), fCurrentQualifier,
										fCurrentParent));

					} else if (tagKind == TagKind.METHOD) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.method.pkg.html
						String[] split = MagicMemberUtil.WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}
						int methodModifiers = Modifiers.AccPublic;
						if (Constants.STATIC.equals(split[0].trim())) {
							if (split.length < 3) {
								break;
							}
							methodModifiers |= Modifiers.AccStatic;
							split = Arrays.copyOfRange(split, 1, split.length);
						}

						String name = removeParenthesis(split);
						int index = name.indexOf('(');
						if (index > 0) {
							name = name.substring(0, index);
						}
						int offset = docTag.sourceStart();
						int length = docTag.sourceStart() + 6;
						Map<String, String> info = new HashMap<String, String>();
						info.put("r", split[0]); //$NON-NLS-1$

						StringBuilder metadata = new StringBuilder();
						metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
						metadata.append(QUALIFIER_SEPERATOR); // $NON-NLS-1$

						modifyDeclaration(null,
								new DeclarationInfo(IModelElement.METHOD, methodModifiers, offset, length, offset,
										length, name, metadata.toString(), encodeDocInfo(info), fCurrentQualifier,
										fCurrentParent));
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
			fLastUseParts.put(name, part);
		}
		return visitGeneral(declaration);
	}

	public boolean visit(FieldDeclaration decl) throws Exception {
		// This is constant declaration:
		int modifiers = decl.getModifiers();
		modifiers = markAsDeprecated(modifiers, decl);

		modifyDeclaration(decl, new DeclarationInfo(IModelElement.FIELD, modifiers, decl.sourceStart(),
				decl.sourceEnd() - decl.sourceStart(), decl.getNameStart(), decl.getNameEnd() - decl.getNameStart(),
				decl.getName(), null, encodeDocInfo(decl), null, null));

		return visitGeneral(decl);
	}

	public boolean endvisit(FieldDeclaration declaration) throws Exception {
		endvisitGeneral(declaration);
		return true;
	}

	public boolean visit(PHPFieldDeclaration decl) throws Exception {
		// This is variable declaration:
		int modifiers = markAsDeprecated(decl.getModifiers(), decl);

		StringBuilder metadata = new StringBuilder();
		metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
		metadata.append(QUALIFIER_SEPERATOR);

		modifyDeclaration(decl, new DeclarationInfo(IModelElement.FIELD, modifiers, decl.sourceStart(),
				decl.sourceEnd() - decl.sourceStart(), decl.getNameStart(), decl.getNameEnd() - decl.getNameStart(),
				decl.getName(), metadata.toString(), encodeDocInfo(decl), fCurrentQualifier, fCurrentParent));

		return visitGeneral(decl);
	}

	public boolean endvisit(PHPFieldDeclaration declaration) throws Exception {
		endvisitGeneral(declaration);
		return true;
	}

	public boolean visit(CallExpression call) throws Exception {
		FieldDeclaration constantDecl = ASTUtils.getConstantDeclaration(call);
		if (constantDecl != null) {
			// In case we are entering a nested element
			if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration) {
				deferredDeclarations.add(constantDecl);
				return visitGeneral(call);
			}

			visit((FieldDeclaration) constantDecl);

		} else {
			int argsCount = 0;
			CallArgumentsList args = call.getArgs();
			if (args != null && args.getChilds() != null) {
				argsCount = args.getChilds().size();
			}

			modifyReference(call, new ReferenceInfo(IModelElement.METHOD, call.sourceStart(),
					call.sourceEnd() - call.sourceStart(), call.getName(), Integer.toString(argsCount), null));
		}

		return visitGeneral(call);
	}

	public boolean visit(Include include) throws Exception {
		// special case for include statements; we need to cache this
		// information in order to access it quickly:
		if (include.getExpr() instanceof Scalar) {
			Scalar filePath = (Scalar) include.getExpr();
			modifyReference(include,
					new ReferenceInfo(IModelElement.METHOD, filePath.sourceStart(),
							filePath.sourceEnd() - filePath.sourceStart(), "include", Integer //$NON-NLS-1$
									.toString(1),
							null));

			String fullPath = ASTUtils.stripQuotes(((Scalar) filePath).getValue());
			int idx = Math.max(fullPath.lastIndexOf('/'), fullPath.lastIndexOf('\\'));

			String lastSegment = fullPath;
			if (idx != -1) {
				lastSegment = lastSegment.substring(idx + 1);
			}
			StringBuilder metadata = new StringBuilder();
			// Fake occurrenceCount, because we do always need one
			// when metadata != null to make PhpElementResolver#resolve() happy
			metadata.append(1);
			metadata.append(QUALIFIER_SEPERATOR);
			metadata.append(fullPath);
			modifyDeclaration(include, new DeclarationInfo(IModelElement.IMPORT_DECLARATION, 0, include.sourceStart(),
					include.sourceEnd() - include.sourceStart(), filePath.sourceStart(),
					filePath.sourceEnd() - filePath.sourceStart(), lastSegment, metadata.toString(), null, null, null));
		}

		return visitGeneral(include);
	}

	public boolean visit(ConstantDeclaration declaration) throws Exception {
		int accessModifier = declaration.getModifiers() == 0 ? Modifiers.AccPublic : declaration.getModifiers();
		int modifiers = Modifiers.AccConstant | Modifiers.AccFinal | accessModifier;
		if (fCurrentParent != null) {
			modifiers = modifiers | PHPCoreConstants.AccClassField;
		}
		modifiers = markAsDeprecated(modifiers, declaration);
		ConstantReference constantName = declaration.getConstantName();
		int offset = constantName.sourceStart();
		int length = constantName.sourceEnd();
		StringBuilder metadata = new StringBuilder();
		metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
		metadata.append(QUALIFIER_SEPERATOR);
		modifyDeclaration(declaration,
				new DeclarationInfo(IModelElement.FIELD, modifiers, offset, length, offset, length,
						ASTUtils.stripQuotes(constantName.getName()), metadata.toString(), encodeDocInfo(declaration),
						fCurrentQualifier, fCurrentParent));
		return visitGeneral(declaration);
	}

	public boolean endvisit(ConstantDeclaration declaration) throws Exception {
		endvisitGeneral(declaration);
		return true;
	}

	public boolean visit(Assignment assignment) throws Exception {
		Expression left = assignment.getVariable();
		if (left instanceof FieldAccess) { // class variable ($this->a = .)
			FieldAccess fieldAccess = (FieldAccess) left;
			Expression dispatcher = fieldAccess.getDispatcher();
			if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					SimpleReference var = (SimpleReference) field;
					int modifiers = Modifiers.AccPublic;
					int offset = var.sourceStart();
					int length = var.sourceEnd() - offset;
					StringBuilder metadata = new StringBuilder();
					metadata.append(fCurrentQualifier != null ? fCurrentQualifierCounts.get(fCurrentQualifier) : 1);
					metadata.append(QUALIFIER_SEPERATOR);
					modifyDeclaration(assignment,
							new DeclarationInfo(IModelElement.FIELD, modifiers, offset, length, offset, length,
									'$' + var.getName(), metadata.toString(), null, fCurrentQualifier, fCurrentParent));
				}
			}
		} else if (left instanceof VariableReference) {
			int modifiers = Modifiers.AccPublic | Modifiers.AccGlobal;
			String variableName = ((VariableReference) left).getName();
			if (!declarations.empty() && declarations.peek() instanceof MethodDeclaration
					&& !methodGlobalVars.peek().contains(variableName)) {
				return visitGeneral(assignment);
			}
			int offset = left.sourceStart();
			int length = left.sourceEnd() - offset;
			if (!globalScopeVars.contains(variableName)) {
				globalScopeVars.add(variableName);
				modifyDeclaration(assignment, new DeclarationInfo(IModelElement.FIELD, modifiers, offset, length,
						offset, length, ((VariableReference) left).getName(), null, null, null, null));
			}
		}
		return visitGeneral(assignment);
	}

	public boolean endvisit(Assignment assignment) throws Exception {
		endvisitGeneral(assignment);
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
		return visitGeneral(s);
	}

	public boolean visit(TypeReference reference) throws Exception {
		modifyReference(reference, new ReferenceInfo(IModelElement.TYPE, reference.sourceStart(),
				reference.sourceEnd() - reference.sourceStart(), reference.getName(), null, null));
		return visitGeneral(reference);
	}

	public boolean visit(Statement node) throws Exception {
		if (node instanceof PHPFieldDeclaration) {
			return visit((PHPFieldDeclaration) node);
		}
		if (node instanceof FieldDeclaration) {
			return visit((FieldDeclaration) node);
		}
		if (node instanceof ConstantDeclaration) {
			return visit((ConstantDeclaration) node);
		}
		if (node instanceof GlobalStatement) {
			return visit((GlobalStatement) node);
		}
		if (node instanceof UseStatement) {
			return visit((UseStatement) node);
		}
		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.visit(node);
		}

		return visitGeneral(node);
	}

	public boolean endvisit(Statement node) throws Exception {
		if (node instanceof PHPFieldDeclaration) {
			return endvisit((PHPFieldDeclaration) node);
		}
		if (node instanceof FieldDeclaration) {
			return endvisit((FieldDeclaration) node);
		}
		if (node instanceof ConstantDeclaration) {
			return endvisit((ConstantDeclaration) node);
		}

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.endvisit(node);
		}

		endvisitGeneral(node);
		return true;
	}

	public boolean visit(Expression node) throws Exception {
		if (node instanceof Assignment) {
			return visit((Assignment) node);
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
		if (node instanceof FieldAccess) {
			return visit((FieldAccess) node);
		}
		if (node instanceof StaticConstantAccess) {
			return visit((StaticConstantAccess) node);
		}

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.visit(node);
		}

		return visitGeneral(node);
	}

	public boolean endvisit(Expression node) throws Exception {
		if (node instanceof Assignment) {
			return endvisit((Assignment) node);
		}

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.endvisit(node);
		}

		endvisitGeneral(node);
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

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.endvisit(declaration);
		}

		fLastUseParts.clear();
		globalScopeVars.clear();
		endvisitGeneral(declaration);
		return true;
	}

	public void endvisitGeneral(ASTNode node) throws Exception {
		fNodes.pop();
	}

	public boolean visitGeneral(ASTNode node) throws Exception {
		fNodes.push(node);
		return true;
	}

	public boolean endvisit(FieldAccess declaration) throws Exception {
		endvisitGeneral(declaration);
		return true;
	}

	public boolean visit(FieldAccess access) throws Exception {
		// This is variable field access:
		if (access.getField() instanceof SimpleReference) {
			SimpleReference simpleReference = (SimpleReference) access.getField();

			String name = simpleReference.getName();
			if (!name.startsWith(DOLLAR)) {
				name = DOLLAR + name;
			}
			modifyReference(access, new ReferenceInfo(IModelElement.FIELD, simpleReference.sourceStart(),
					simpleReference.sourceEnd() - simpleReference.sourceStart(), name, null, null));
		}

		return visitGeneral(access);
	}

	public boolean visit(StaticConstantAccess access) throws Exception {
		// This is constant field access:
		if (access.getConstant() != null) {
			final ConstantReference constantReference = access.getConstant();
			final String name = constantReference.getName();

			modifyReference(access, new ReferenceInfo(IModelElement.FIELD, constantReference.sourceStart(),
					constantReference.sourceEnd() - constantReference.sourceStart(), name, null, null));
		}

		return visitGeneral(access);
	}

}
