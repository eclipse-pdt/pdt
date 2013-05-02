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
package org.eclipse.php.internal.core.index;

import java.util.*;
import java.util.Map.Entry;
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
import org.eclipse.php.internal.core.compiler.ast.nodes.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.evaluators.phpdoc.PHPDocClassVariableEvaluator;

/**
 * PHP indexing visitor for H2 database
 * 
 * @author michael
 * 
 */
public class PhpIndexingVisitor extends PhpIndexingVisitorExtension {

	private static final String DOLOR = "$"; //$NON-NLS-1$
	private static final String CONSTRUCTOR_NAME = "__construct"; //$NON-NLS-1$
	private static final Pattern WHITESPACE_SEPERATOR = Pattern.compile("\\s+"); //$NON-NLS-1$
	private static final String EXTENSION_POINT = "phpIndexingVisitors"; //$NON-NLS-1$
	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$
	public static final String PARAMETER_SEPERATOR = "|"; //$NON-NLS-1$
	public static final String NULL_VALUE = "#"; //$NON-NLS-1$
	private static final String DEFAULT_VALUE = " "; //$NON-NLS-1$
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
	 * This stack contains a set per method, where each set contains all global
	 * variables names delcared through 'global' keyword inside this method.
	 */
	protected Stack<Set<String>> methodGlobalVars = new Stack<Set<String>>();

	/**
	 * Extensions indexing visitor extensions
	 */
	private PhpIndexingVisitorExtension[] extensions;
	private static IConfigurationElement[] extensionElements = Platform
			.getExtensionRegistry().getConfigurationElementsFor(
					PHPCorePlugin.ID, EXTENSION_POINT);

	protected NamespaceDeclaration fCurrentNamespace;
	protected Map<String, UsePart> fLastUseParts = new HashMap<String, UsePart>();;
	protected String fCurrentQualifier;
	protected String fCurrentParent;
	protected Stack<ASTNode> fNodes = new Stack<ASTNode>();

	protected IIndexingRequestor requestor;

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
		this.extensions = extensions
				.toArray(new PhpIndexingVisitorExtension[extensions.size()]);
	}

	public void modifyDeclaration(ASTNode node, DeclarationInfo info) {
		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.modifyDeclaration(node, info);
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
			PHPDocBlock docBlock = ((IPHPDocAwareDeclaration) declaration)
					.getPHPDoc();
			if (docBlock != null) {
				Map<String, String> info = new HashMap<String, String>();
				for (PHPDocTag tag : docBlock.getTags()) {
					if (tag.getTagKind() == PHPDocTag.DEPRECATED) {
						info.put("d", null); //$NON-NLS-1$
					} else if (tag.getTagKind() == PHPDocTag.RETURN) {
						StringBuilder buf = new StringBuilder();
						for (SimpleReference ref : tag.getReferences()) {
							String type = ref.getName().replaceAll(",", "~"); //$NON-NLS-1$ //$NON-NLS-2$
							if (buf.length() > 0) {
								buf.append(',');
							}
							buf.append(type);
						}
						info.put("r", buf.toString()); //$NON-NLS-1$
					}
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
		}
		return null;
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
			fCurrentNamespace = null; // there are no nested namespaces
			fCurrentQualifier = null;
			fLastUseParts.clear();
			if (namespaceDecl.isGlobal()) {
				return visitGeneral(type);
			}
		} else {
			fCurrentParent = null;
		}
		declarations.pop();

		// resolve more type member declarations
		resolveMagicMembers(type);

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
			deferredDeclarations.add(method);
			return visitGeneral(method);
		}

		if (parentDeclaration instanceof InterfaceDeclaration) {
			method.setModifier(Modifiers.AccAbstract);
		}

		int modifiers = method.getModifiers();
		String methodName = method.getName();

		// Determine whether this method represents constructor:
		if (methodName.equalsIgnoreCase(CONSTRUCTOR_NAME)
				|| (parentDeclaration instanceof ClassDeclaration && methodName
						.equalsIgnoreCase(((ClassDeclaration) parentDeclaration)
								.getName()))) {
			modifiers |= IPHPModifiers.Constructor;
		}

		if (parentDeclaration == null
				|| (parentDeclaration instanceof TypeDeclaration && parentDeclaration == fCurrentNamespace)) {
			modifiers |= Modifiers.AccGlobal;
		}
		if (!Flags.isPrivate(modifiers) && !Flags.isProtected(modifiers)
				&& !Flags.isPublic(modifiers)) {
			modifiers |= Modifiers.AccPublic;
		}

		StringBuilder metadata = new StringBuilder();
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
						defaultValue = scalar.getValue();
					} else {
						defaultValue = DEFAULT_VALUE;
					}
				}
				metadata.append(defaultValue);
				if (i.hasNext()) {
					metadata.append(","); //$NON-NLS-1$
				}
			}
		}

		// Add method declaration:
		modifyDeclaration(
				method,
				new DeclarationInfo(IModelElement.METHOD, modifiers, method
						.sourceStart(), method.sourceEnd()
						- method.sourceStart(), method.getNameStart(), method
						.getNameEnd() - method.getNameStart(), methodName,
						metadata.length() == 0 ? null : metadata.toString(),
						encodeDocInfo(method), fCurrentQualifier,
						fCurrentParent));

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.visit(method);
		}

		return visitGeneral(method);
	}

	private String getParamType(PHPDocBlock docBlock, String paramName,
			String defaultType) {
		String result = defaultType;
		if (docBlock != null) {
			for (PHPDocTag tag : docBlock.getTags()) {
				if (tag.getTagKind() == PHPDocTag.PARAM) {
					SimpleReference[] references = tag.getReferences();
					if (references.length == 2) {
						if (references[0].getName().equals(paramName)) {
							String typeName = references[1].getName();
							if (typeName
									.endsWith(PHPDocClassVariableEvaluator.BRACKETS)) {
								typeName = typeName.substring(0,
										typeName.length() - 2);
							}
							result = typeName.replace(
									Constants.TYPE_SEPERATOR_CHAR,
									Constants.DOT);
						}
					}
				}
			}
		}
		return result;
	}

	public boolean visit(TypeDeclaration type) throws Exception {
		boolean isNamespace = false;
		if (type instanceof NamespaceDeclaration) {
			NamespaceDeclaration namespaceDecl = (NamespaceDeclaration) type;
			fCurrentNamespace = namespaceDecl;
			fLastUseParts.clear();
			if (namespaceDecl.isGlobal()) {
				return visitGeneral(type);
			}
			isNamespace = true;
		}

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
			deferredDeclarations.add(type);
			return visitGeneral(type);
		}

		int modifiers = type.getModifiers();

		// check whether this is a namespace
		if (isNamespace) {
			modifiers |= Modifiers.AccNameSpace;
			fCurrentQualifier = type.getName();
		} else {
			fCurrentParent = type.getName();
		}

		String[] superClasses = processSuperClasses(type);
		StringBuilder metadata = new StringBuilder();
		for (int i = 0; i < superClasses.length; ++i) {
			metadata.append(superClasses[i]);
			if (i < superClasses.length - 1) {
				metadata.append(","); //$NON-NLS-1$
			}
		}

		modifyDeclaration(
				type,
				new DeclarationInfo(IModelElement.TYPE, modifiers, type
						.sourceStart(), type.sourceEnd() - type.sourceStart(),
						type.getNameStart(), type.getNameEnd()
								- type.getNameStart(), type.getName(), metadata
								.length() == 0 ? null : metadata.toString(),
						encodeDocInfo(type), isNamespace ? null
								: fCurrentQualifier, null));

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.visit(type);
		}

		return visitGeneral(type);
	}

	@SuppressWarnings("unchecked")
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
					} else if (fCurrentNamespace != null) {
						name = new StringBuilder(fCurrentNamespace.getName())
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
					if (fCurrentNamespace != null) {
						name = new StringBuilder(fCurrentNamespace.getName())
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

						String name = removeParenthesis(split);
						int offset = docTag.sourceStart();
						int length = docTag.sourceStart() + 9;
						modifyDeclaration(null, new DeclarationInfo(
								IModelElement.FIELD, Modifiers.AccPublic,
								offset, length, offset, length, name, null,
								null, fCurrentQualifier, fCurrentParent));

					} else if (tagKind == PHPDocTag.METHOD) {
						// http://manual.phpdoc.org/HTMLSmartyConverter/HandS/phpDocumentor/tutorial_tags.method.pkg.html
						final String[] split = WHITESPACE_SEPERATOR
								.split(docTag.getValue().trim());
						if (split.length < 2) {
							break;
						}

						String name = removeParenthesis(split);
						int index = name.indexOf('(');
						if (index > 0) {
							name = name.substring(0, index);
						}
						int offset = docTag.sourceStart();
						int length = docTag.sourceStart() + 6;
						modifyDeclaration(null, new DeclarationInfo(
								IModelElement.METHOD, Modifiers.AccPublic,
								offset, length, offset, length, name, null,
								null, fCurrentQualifier, fCurrentParent));
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
				int index = name
						.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
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
		modifyDeclaration(
				decl,
				new DeclarationInfo(IModelElement.FIELD, modifiers, decl
						.sourceStart(), decl.sourceEnd() - decl.sourceStart(),
						decl.getNameStart(), decl.getNameEnd()
								- decl.getNameStart(), decl.getName(), null,
						encodeDocInfo(decl), null, null));

		return visitGeneral(decl);
	}

	public boolean endvisit(FieldDeclaration declaration) throws Exception {
		endvisitGeneral(declaration);
		return true;
	}

	public boolean visit(PHPFieldDeclaration decl) throws Exception {
		// This is variable declaration:
		int modifiers = decl.getModifiers();

		modifyDeclaration(
				decl,
				new DeclarationInfo(IModelElement.FIELD, modifiers, decl
						.sourceStart(), decl.sourceEnd() - decl.sourceStart(),
						decl.getNameStart(), decl.getNameEnd()
								- decl.getNameStart(), decl.getName(), null,
						encodeDocInfo(decl), fCurrentQualifier, fCurrentParent));

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
			if (!declarations.empty()
					&& declarations.peek() instanceof MethodDeclaration) {
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

			modifyReference(
					call,
					new ReferenceInfo(IModelElement.METHOD, call.sourceStart(),
							call.sourceEnd() - call.sourceStart(), call
									.getName(), Integer.toString(argsCount),
							null));
		}

		return visitGeneral(call);
	}

	public boolean visit(Include include) throws Exception {
		// special case for include statements; we need to cache this
		// information in order to access it quickly:
		if (include.getExpr() instanceof Scalar) {
			Scalar filePath = (Scalar) include.getExpr();
			modifyReference(
					include,
					new ReferenceInfo(IModelElement.METHOD, filePath
							.sourceStart(), filePath.sourceEnd()
							- filePath.sourceStart(), "include", Integer //$NON-NLS-1$
							.toString(1), null));

			String fullPath = ASTUtils.stripQuotes(((Scalar) filePath)
					.getValue());
			int idx = Math.max(fullPath.lastIndexOf('/'),
					fullPath.lastIndexOf('\\'));

			String lastSegment = fullPath;
			if (idx != -1) {
				lastSegment = lastSegment.substring(idx + 1);
			}
			modifyDeclaration(
					include,
					new DeclarationInfo(IModelElement.IMPORT_DECLARATION, 0,
							include.sourceStart(), include.sourceEnd()
									- include.sourceStart(), filePath
									.sourceStart(), filePath.sourceEnd()
									- filePath.sourceStart(), lastSegment,
							fullPath, null, null, null));
		}

		return visitGeneral(include);
	}

	public boolean visit(ConstantDeclaration declaration) throws Exception {
		int modifiers = Modifiers.AccConstant | Modifiers.AccPublic
				| Modifiers.AccFinal;
		if (fCurrentParent != null) {
			modifiers = modifiers | PHPCoreConstants.AccClassField;
		}
		ConstantReference constantName = declaration.getConstantName();
		int offset = constantName.sourceStart();
		int length = constantName.sourceEnd();
		modifyDeclaration(
				declaration,
				new DeclarationInfo(IModelElement.FIELD, modifiers, offset,
						length, offset, length, ASTUtils
								.stripQuotes(constantName.getName()), null,
						encodeDocInfo(declaration), fCurrentQualifier,
						fCurrentParent));
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
			if (dispatcher instanceof VariableReference
					&& "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
				Expression field = fieldAccess.getField();
				if (field instanceof SimpleReference) {
					SimpleReference var = (SimpleReference) field;
					int modifiers = Modifiers.AccPublic;
					int offset = var.sourceStart();
					int length = var.sourceEnd() - offset;
					modifyDeclaration(assignment, new DeclarationInfo(
							IModelElement.FIELD, modifiers, offset, length,
							offset, length, '$' + var.getName(), null, null,
							fCurrentQualifier, fCurrentParent));
				}
			}
		} else if (left instanceof VariableReference) {
			int modifiers = Modifiers.AccPublic | Modifiers.AccGlobal;
			if (!declarations.empty()
					&& declarations.peek() instanceof MethodDeclaration
					&& !methodGlobalVars.peek().contains(
							((VariableReference) left).getName())) {
				return visitGeneral(assignment);
			}
			int offset = left.sourceStart();
			int length = left.sourceEnd() - offset;
			modifyDeclaration(assignment, new DeclarationInfo(
					IModelElement.FIELD, modifiers, offset, length, offset,
					length, ((VariableReference) left).getName(), null, null,
					null, null));
		}
		return visitGeneral(assignment);
	}

	public boolean endvisit(Assignment assignment) throws Exception {
		endvisitGeneral(assignment);
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
		return visitGeneral(s);
	}

	public boolean visit(TypeReference reference) throws Exception {
		modifyReference(reference,
				new ReferenceInfo(IModelElement.TYPE, reference.sourceStart(),
						reference.sourceEnd() - reference.sourceStart(),
						reference.getName(), null, null));
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
			final ASTNode[] declarations = deferredDeclarations
					.toArray(new ASTNode[deferredDeclarations.size()]);
			deferredDeclarations.clear();

			for (ASTNode deferred : declarations) {
				deferred.traverse(this);
			}
		}

		for (PhpIndexingVisitorExtension visitor : extensions) {
			visitor.endvisit(declaration);
		}

		fLastUseParts.clear();
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
			SimpleReference simpleReference = (SimpleReference) access
					.getField();

			String name = simpleReference.getName();
			if (!name.startsWith(DOLOR)) {
				name = DOLOR + name;
			}
			modifyReference(access, new ReferenceInfo(IModelElement.FIELD,
					simpleReference.sourceStart(), simpleReference.sourceEnd()
							- simpleReference.sourceStart(), name, null, null));
		}

		return visitGeneral(access);
	}

}
