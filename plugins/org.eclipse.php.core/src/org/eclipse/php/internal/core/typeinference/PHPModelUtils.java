/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import static org.apache.commons.lang3.StringUtils.startsWithIgnoreCase;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;
import org.eclipse.dltk.internal.core.SourceRefElement;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.NamespaceName;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.*;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.typeinference.DeclarationSearcher.DeclarationType;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

public class PHPModelUtils {

	public static final String ENCLOSING_TYPE_SEPARATOR = new String(
			new char[] { NamespaceReference.NAMESPACE_SEPARATOR }); // $NON-NLS-1$

	private static final IType[] EMPTY_TYPES = new IType[0];

	/**
	 * Concatenates all type names (without their namespace prefixes) together
	 * 
	 * @param references
	 *            list of type references
	 * @return concatenated type names
	 */
	@NonNull
	public static String appendTypeReferenceNames(List<TypeReference> references) {
		if (references.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		if (references.size() == 1) {
			return references.get(0).getName();
		}

		StringBuilder sb = new StringBuilder();
		sb.append(references.get(0).getName());
		for (int i = 1; i < references.size(); i++) {
			TypeReference reference = references.get(i);
			sb.append(Constants.TYPE_SEPERATOR_CHAR);
			sb.append(reference.getName());
		}

		return sb.toString();
	}

	/**
	 * Extracts the element name from the given fully qualified name
	 * 
	 * @param element
	 *            Element name
	 * @return element name without the namespace prefix
	 */
	@Nullable
	public static String extractElementName(@Nullable String element) {
		if (element != null) {
			int i = element.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			if (i != -1) {
				element = element.substring(i + 1).trim();
			}
		}
		return element;
	}

	/**
	 * Extracts the name space name from the given fully qualified name
	 * 
	 * @param element
	 *            Element name
	 * @return namespace prefix
	 */
	@Nullable
	public static String extractNameSpaceName(@Nullable String element) {
		String nameSpaceName = null;
		if (element != null) {
			int i = element.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			if (i != -1) {
				nameSpaceName = element.substring(0, i).trim();
			}
		}
		return nameSpaceName;
	}

	/**
	 * Concatenate FQN parameters into one string e.g. 'A\B' + 'C\D' = 'A\B\C\D'
	 * 
	 * @param fqns
	 *            names to concat
	 * @return concatenated names
	 */
	@NonNull
	public static String concatFullyQualifiedNames(String... fqns) {
		StringBuilder builder = new StringBuilder();
		for (String fqn : fqns) {
			if (fqn != null) {
				if (builder.length() != 0
						&& builder.charAt(builder.length() - 1) != NamespaceReference.NAMESPACE_SEPARATOR
						&& !fqn.isEmpty() && fqn.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
					builder.append(NamespaceReference.NAMESPACE_SEPARATOR);
				}
				builder.append(fqn);
			}
		}
		return builder.toString();
	}

	/**
	 * if the elementName is a class alias for a namespace class, we get its
	 * original name from its alias
	 * 
	 * @param elementName
	 * @param sourceModule
	 * @param offset
	 * @param defaultClassName
	 * @return
	 */
	public static String getRealName(String elementName, ISourceModule sourceModule, final int offset,
			String defaultClassName) {

		// Check class name aliasing:
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		UsePart usePart = ASTUtils.findUseStatementByAlias(moduleDeclaration, elementName, offset);
		if (usePart != null) {
			elementName = usePart.getNamespace().getFullyQualifiedName();
			int nsIndex = elementName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			if (nsIndex != -1) {
				defaultClassName = elementName.substring(nsIndex + 1);
			} else {
				defaultClassName = elementName;
			}
		}
		return defaultClassName;
	}

	/**
	 * Extracts the namespace name from the specified element name and resolves
	 * it using USE statements that present in the file.
	 * 
	 * @param elementName
	 *            The name of the element, like: \A\B or A\B\C.
	 * @param sourceModule
	 *            Source module where the element is referenced
	 * @param offset
	 *            The offset where element is referenced
	 * @return namespace name:
	 * 
	 *         <pre>
	 *   1. &lt;code&gt;&quot;&quot;&lt;/code&gt; (empty string) indicates global namespace
	 *   2. non-empty string indicates a real namespace
	 *   3. &lt;code&gt;null&lt;/code&gt; indicates that there's no namespace prefix in element name
	 *         </pre>
	 */
	@Nullable
	public static String extractNamespaceName(String elementName, ISourceModule sourceModule, final int offset) {

		// Check class name aliasing:
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		UsePart usePart = ASTUtils.findUseStatementByAlias(moduleDeclaration, elementName, offset);
		if (usePart != null) {
			elementName = usePart.getNamespace().getFullyQualifiedName();
			if (elementName != null && elementName.length() > 0
					&& elementName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
				elementName = NamespaceReference.NAMESPACE_SEPARATOR + elementName;
			}
		}

		boolean isGlobal = false;
		int nsIndex = -1;
		if (elementName != null) {
			nsIndex = elementName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			if (elementName.length() > 0 && elementName.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
				isGlobal = true;
			}
		}
		if (nsIndex != -1) {
			String namespace = elementName == null ? "" : elementName.substring(0, nsIndex); //$NON-NLS-1$
			if (isGlobal && namespace.length() > 0) {
				namespace = namespace.substring(1);
			}

			if (!isGlobal) {
				// 1. It can be a special 'namespace' keyword, which points to
				// the current namespace:
				if ("namespace".equalsIgnoreCase(namespace)) { //$NON-NLS-1$
					IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=470673
					return currentNamespace != null ? currentNamespace.getElementName() : null;
				}

				// 2. it can be an alias - try to find relevant USE statement
				if (namespace.indexOf('\\') == -1) {
					usePart = ASTUtils.findUseStatementByAlias(moduleDeclaration, namespace, offset);
					if (usePart != null) {
						return usePart.getNamespace().getFullyQualifiedName();
					}
				} else {
					nsIndex = namespace.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
					String alias = namespace.substring(0, nsIndex);
					usePart = ASTUtils.findUseStatementByAlias(moduleDeclaration, alias, offset);
					if (usePart != null) {
						return usePart.getNamespace().getFullyQualifiedName() + NamespaceReference.NAMESPACE_SEPARATOR
								+ namespace.substring(nsIndex + 1);
					}
				}

				// 3. it can be a sub-namespace of the current namespace:
				IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
				if (currentNamespace != null) {
					return new StringBuilder(currentNamespace.getElementName())
							.append(NamespaceReference.NAMESPACE_SEPARATOR).append(namespace).toString();
				}
			}

			// global namespace:
			return namespace;
		}

		return null;
	}

	/**
	 * Filters model elements using file network.
	 * 
	 * @param sourceModule
	 *            Source module
	 * @param elements
	 *            Model elements to filter
	 * @param cache
	 *            Temporary model cache instance
	 * @param monitor
	 *            Progress monitor
	 * @return
	 */
	@Nullable
	public static <T extends IModelElement> Collection<T> fileNetworkFilter(ISourceModule sourceModule,
			@Nullable Collection<T> elements, IModelAccessCache cache, IProgressMonitor monitor) {

		// If it's just one element (or less) - return it
		if (elements != null && elements.size() > 1) {
			List<T> filteredElements = new LinkedList<T>();

			// If some of elements belong to current file just return it:
			for (T element : elements) {
				if (sourceModule.equals(element.getOpenable())) {
					filteredElements.add(element);
				}
			}
			if (filteredElements.size() == 0) {
				ReferenceTree referenceTree;
				if (cache != null) {
					referenceTree = cache.getFileHierarchy(sourceModule, monitor);
				} else {
					// Filter by includes network
					referenceTree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, monitor);
				}
				for (T element : elements) {
					if (LanguageModelInitializer.isLanguageModelElement(element)
							|| referenceTree.find(((ModelElement) element).getSourceModule())) {
						filteredElements.add(element);
					}
				}
			}
			if (filteredElements.size() > 0) {
				elements = filteredElements;
			}
		}
		return elements;
	}

	@Nullable
	public static <T extends IModelElement> Collection<T> fileNetworkFilterTypes(ISourceModule sourceModule,
			@Nullable Collection<T> elements, IModelAccessCache cache, boolean isNs, IProgressMonitor monitor) {

		if (elements != null && elements.size() > 0) {
			List<T> filteredElements = new LinkedList<T>();

			// If some of elements belong to current file return just it:
			for (T element : elements) {
				try {
					if (sourceModule.equals(element.getOpenable())
							&& (isNs && PHPFlags.isNamespace(((IType) element).getFlags())
									|| !isNs && !PHPFlags.isNamespace(((IType) element).getFlags()))) {
						filteredElements.add(element);
					}
				} catch (ModelException e) {
				}
			}
			if (filteredElements.size() == 0) {
				ReferenceTree referenceTree;
				if (cache != null) {
					referenceTree = cache.getFileHierarchy(sourceModule, monitor);
				} else {
					// Filter by includes network
					referenceTree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, monitor);
				}
				for (T element : elements) {
					if (LanguageModelInitializer.isLanguageModelElement(element)
							|| referenceTree.find(((ModelElement) element).getSourceModule())) {
						try {
							if ((isNs && PHPFlags.isNamespace(((IType) element).getFlags())
									|| !isNs && !PHPFlags.isNamespace(((IType) element).getFlags()))) {
								filteredElements.add(element);
							}
						} catch (ModelException e) {
						}

					}
				}
			}
			if (filteredElements.size() > 0) {
				elements = filteredElements;
			}
		}
		return elements;
	}

	/**
	 * Determine whether given elements represent the same type, name and
	 * namespace, but declared in different files (determine whether file
	 * network filtering can be used)
	 * 
	 * @param elements
	 *            Model elements list
	 * @return
	 */
	private static <T extends IModelElement> boolean canUseFileNetworkFilter(Collection<T> elements) {
		int elementType = 0;
		String elementName = null;
		String namespaceName = null;
		for (T element : elements) {
			if (element == null) {
				continue;
			}
			IType namespaceElement = getCurrentNamespace(element);
			String namespaceNameElement = namespaceElement != null ? namespaceElement.getElementName() : null;
			if (elementName == null) {
				elementType = element.getElementType();
				elementName = element.getElementName();
				namespaceName = namespaceNameElement;
				continue;
			}
			if (!elementName.equalsIgnoreCase(element.getElementName()) || elementType != element.getElementType()
					|| !StringUtils.equalsIgnoreCase(namespaceName, namespaceNameElement)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Leaves most 'suitable' for current source module elements
	 * 
	 * @param sourceModule
	 * @param elements
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 *            Progress monitor
	 * @return
	 */
	public static <T extends IModelElement> Collection<T> filterElements(ISourceModule sourceModule,
			@Nullable Collection<T> elements, IModelAccessCache cache, IProgressMonitor monitor) {
		if (elements == null) {
			return null;
		}
		if (canUseFileNetworkFilter(elements)) {
			return fileNetworkFilter(sourceModule, elements, cache, monitor);
		}
		return elements;
	}

	/**
	 * Returns the current method or function by the specified file and offset
	 * 
	 * @param sourceModule
	 *            The file where current namespace is requested
	 * @param offset
	 *            The offset where current namespace is requested
	 * @return method element, or <code>null</code> if the scope not a method
	 *         scope
	 */
	@Nullable
	public static IMethod getCurrentMethod(ISourceModule sourceModule, int offset) {
		try {
			IModelElement currentMethod = sourceModule.getElementAt(offset);
			while (currentMethod != null) {
				if (currentMethod instanceof IMethod) {
					return (IMethod) currentMethod;
				}
				if (!(currentMethod instanceof IField)) {
					break;
				}
				currentMethod = currentMethod.getParent();
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	/**
	 * Returns the current namespace by the specified model element
	 * 
	 * @param element
	 *            Model element
	 * @return namespace element, or <code>null</code> if the scope is global
	 *         under the specified cursor position
	 */
	@Nullable
	public static IType getCurrentNamespace(IModelElement element) {
		try {
			IModelElement currentNs = element;
			while (currentNs != null) {
				if (currentNs instanceof IType && PHPFlags.isNamespace(((IType) currentNs).getFlags())) {
					return (IType) currentNs;
				}
				currentNs = currentNs.getParent();
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	/**
	 * Returns the current namespace by the specified file and offset
	 * 
	 * @param sourceModule
	 *            The file where current namespace is requested
	 * @param sourceModule
	 *            The offset where current namespace is requested
	 * @return namespace element, or <code>null</code> if the scope is global
	 *         under the specified cursor position
	 */
	@Nullable
	public static IType getCurrentNamespace(ISourceModule sourceModule, int offset) {
		try {
			IModelElement currentNs = sourceModule.getElementAt(offset);
			while (currentNs instanceof IField) {
				currentNs = sourceModule.getElementAt(((IField) currentNs).getSourceRange().getOffset() - 1);
			}
			while (currentNs != null) {
				if (currentNs instanceof IType && PHPFlags.isNamespace(((IType) currentNs).getFlags())) {
					return (IType) currentNs;
				}
				currentNs = currentNs.getParent();
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	@Nullable
	public static IType getCurrentNamespaceIfAny(ISourceModule sourceModule, int offset) {
		IType result = getCurrentNamespace(sourceModule, offset);
		if (result == null) {
			try {
				IModelElement[] elements = sourceModule.getChildren();
				for (IModelElement modelElement : elements) {

					if (modelElement instanceof IType && PHPFlags.isNamespace(((IType) modelElement).getFlags())) {
						result = (IType) modelElement;
					}

					if (modelElement instanceof SourceRefElement) {
						SourceRefElement child = (SourceRefElement) modelElement;
						ISourceRange range = child.getSourceRange();
						int start = range.getOffset();
						int end = start + range.getLength();
						if (start <= offset && offset <= end) {
							return result;
						}
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return result;
	}

	/**
	 * if there are error in the php file,the parser can not be parse the ast
	 * correctly
	 * 
	 * @param sourceModule
	 * @param offset
	 * @return
	 */
	@Nullable
	public static IType getPossibleCurrentNamespace(ISourceModule sourceModule, int offset) {
		try {
			IType result = getCurrentNamespace(sourceModule, offset);
			if (result == null) {
				IType[] types = sourceModule.getTypes();
				if (types != null && types.length > 0 && PHPFlags.isNamespace(types[0].getFlags())) {
					for (int i = 0; i < types.length; i++) {
						if (types[i].getSourceRange().getOffset() <= offset
								&& PHPFlags.isNamespace(types[i].getFlags())) {
							result = types[i];
						} else {
							return result;
						}

					}
				}

			}
			return result;

		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	/**
	 * Returns the current class or interface by the specified file and offset
	 * 
	 * @param sourceModule
	 *            The file where current namespace is requested
	 * @param offset
	 *            The offset where current namespace is requested
	 * @return type element, or <code>null</code> if the scope not a class or
	 *         interface scope
	 */
	@Nullable
	public static IType getCurrentType(IModelElement element) {
		try {
			while (element != null) {
				if (element instanceof IType) {
					if (!PHPFlags.isNamespace(((IType) element).getFlags())) {
						return (IType) element;
					}
					break;
				}
				element = element.getParent();
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	/**
	 * Returns the current class or interface by the specified file and offset
	 * 
	 * @param sourceModule
	 *            The file where current namespace is requested
	 * @param offset
	 *            The offset where current namespace is requested
	 * @return type element, or <code>null</code> if the scope not a class or
	 *         interface scope
	 */
	@Nullable
	public static IType getCurrentType(ISourceModule sourceModule, int offset) {
		try {
			return getCurrentType(sourceModule.getElementAt(offset));
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	/**
	 * Returns PHPDoc block associated with the given IField element
	 * 
	 * @param field
	 * @return
	 */
	@Nullable
	public static PHPDocBlock getDocBlock(IField field) {
		if (field == null) {
			return null;
		}
		try {
			ISourceModule sourceModule = field.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			ASTNode fieldDeclaration = PHPModelUtils.getNodeByField(moduleDeclaration, field);
			if (fieldDeclaration instanceof IPHPDocAwareDeclaration) {
				return ((IPHPDocAwareDeclaration) fieldDeclaration).getPHPDoc();
			} else if (fieldDeclaration == null) {
				return DefineMethodUtils.getDefinePHPDocBlockByField(moduleDeclaration, field);
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return null;
	}

	/**
	 * Returns PHPDoc block associated with the given IMethod element
	 * 
	 * @param method
	 * @return
	 */
	@Nullable
	public static PHPDocBlock getDocBlock(IMethod method) {
		if (method == null) {
			return null;
		}
		try {
			ISourceModule sourceModule = method.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			MethodDeclaration methodDeclaration = PHPModelUtils.getNodeByMethod(moduleDeclaration, method);
			if (methodDeclaration instanceof IPHPDocAwareDeclaration) {
				return ((IPHPDocAwareDeclaration) methodDeclaration).getPHPDoc();
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return null;
	}

	/**
	 * Returns PHPDoc block associated with the given IType element
	 * 
	 * @param type
	 * @return
	 */
	@Nullable
	public static PHPDocBlock getDocBlock(IType type) {
		if (type == null) {
			return null;
		}
		try {
			ISourceModule sourceModule = type.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(moduleDeclaration, type);
			if (typeDeclaration instanceof IPHPDocAwareDeclaration) {
				return ((IPHPDocAwareDeclaration) typeDeclaration).getPHPDoc();
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return null;
	}

	/**
	 * This method returns field corresponding to its name and the file where it
	 * was referenced. The field name may contain also the namespace part, like:
	 * A\B\C or \A\B\C
	 * 
	 * @param fieldName
	 *            Tye fully qualified field name
	 * @param sourceModule
	 *            The file where the element is referenced
	 * @param offset
	 *            The offset where the element is referenced
	 * @param monitor
	 *            Progress monitor
	 * @return a list of relevant IField elements
	 * @throws ModelException
	 */
	@NonNull
	public static IField[] getFields(String fieldName, ISourceModule sourceModule, int offset, IProgressMonitor monitor)
			throws ModelException {
		return getFields(fieldName, sourceModule, offset, null, monitor);
	}

	/**
	 * This method returns field corresponding to its name and the file where it
	 * was referenced. The field name may contain also the namespace part, like:
	 * A\B\C or \A\B\C
	 * 
	 * @param fieldName
	 *            Tye fully qualified field name
	 * @param sourceModule
	 *            The file where the element is referenced
	 * @param offset
	 *            The offset where the element is referenced
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 *            Progress monitor
	 * @return a list of relevant IField elements
	 * @throws ModelException
	 */
	@NonNull
	public static IField[] getFields(String fieldName, ISourceModule sourceModule, int offset, IModelAccessCache cache,
			IProgressMonitor monitor) throws ModelException {
		if (fieldName == null || fieldName.length() == 0) {
			return PhpModelAccess.NULL_FIELDS;
		}
		if (!fieldName.startsWith("$")) { // variables are not //$NON-NLS-1$
											// supported by
			// namespaces in PHP 5.3
			String namespace = extractNamespaceName(fieldName, sourceModule, offset);
			fieldName = extractElementName(fieldName);
			if (namespace != null) {
				if (namespace.length() > 0) {
					IField[] fields = getNamespaceField(namespace, fieldName, true, sourceModule, cache, monitor);
					if (fields.length > 0) {
						return fields;
					}
					return PhpModelAccess.NULL_FIELDS;
				}
				// it's a global reference: \C
			} else {
				// look for the element in current namespace:
				IType currentNamespace = getCurrentNamespace(sourceModule, offset);
				if (currentNamespace != null) {
					namespace = currentNamespace.getElementName();
					IField[] fields = getNamespaceField(namespace, fieldName, true, sourceModule, cache, monitor);
					if (fields.length > 0) {
						return fields;
					}

					// For functions and constants, PHP will fall back to global
					// functions or constants if a namespaced function or
					// constant does not exist:
					IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
					fields = PhpModelAccess.getDefault().findFields(fieldName, MatchRule.EXACT,
							Modifiers.AccConstant | Modifiers.AccGlobal, 0, scope, null);

					Collection<IField> filteredElements = filterElements(sourceModule, Arrays.asList(fields), cache,
							monitor);
					return filteredElements.toArray(new IField[filteredElements.size()]);
				}
			}
		}
		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		IField[] fields = PhpModelAccess.getDefault().findFields(fieldName, MatchRule.EXACT, Modifiers.AccGlobal, 0,
				scope, null);

		Collection<IField> filteredElements = null;
		if (fields != null) {
			filteredElements = filterElements(sourceModule, Arrays.asList(fields), cache, monitor);
			return filteredElements.toArray(new IField[filteredElements.size()]);
		}
		return PhpModelAccess.NULL_FIELDS;
	}

	/**
	 * This method returns function corresponding to its name and the file where
	 * it was referenced. The function name may contain also the namespace part,
	 * like: A\B\foo() or \A\B\foo()
	 * 
	 * @param functionName
	 *            The fully qualified function name
	 * @param sourceModule
	 *            The file where the element is referenced
	 * @param offset
	 *            The offset where the element is referenced
	 * @param monitor
	 *            Progress monitor
	 * @return a list of relevant IMethod elements
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getFunctions(String functionName, ISourceModule sourceModule, int offset,
			IProgressMonitor monitor) throws ModelException {
		return getFunctions(functionName, sourceModule, offset, null, monitor);
	}

	/**
	 * This method returns function corresponding to its name and the file where
	 * it was referenced. The function name may contain also the namespace part,
	 * like: A\B\foo() or \A\B\foo()
	 * 
	 * @param functionName
	 *            The fully qualified function name
	 * @param sourceModule
	 *            The file where the element is referenced
	 * @param offset
	 *            The offset where the element is referenced
	 * @param cache
	 *            Temporary model cache instance
	 * @param monitor
	 *            Progress monitor
	 * @return a list of relevant IMethod elements
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getFunctions(String functionName, ISourceModule sourceModule, int offset,
			IModelAccessCache cache, IProgressMonitor monitor) throws ModelException {

		if (functionName == null || functionName.length() == 0) {
			return PhpModelAccess.NULL_METHODS;
		}
		String namespace = extractNamespaceName(functionName, sourceModule, offset);
		functionName = extractElementName(functionName);
		if (namespace != null) {
			if (namespace.length() > 0) {
				IMethod[] functions = getNamespaceFunction(namespace, functionName, true, sourceModule, cache, monitor);
				if (functions.length > 0) {
					return functions;
				}
				return PhpModelAccess.NULL_METHODS;
			}
			// it's a global reference: \foo()
		} else {
			// look for the element in current namespace:
			IType currentNamespace = getCurrentNamespace(sourceModule, offset);
			if (currentNamespace != null) {
				namespace = currentNamespace.getElementName();
				IMethod[] functions = getNamespaceFunction(namespace, functionName, true, sourceModule, cache, monitor);
				if (functions.length > 0) {
					return functions;
				}
				// For functions and constants, PHP will fall back to global
				// functions or constants if a namespaced function or constant
				// does not exist:
				return getGlobalFunctions(sourceModule, functionName, cache, monitor);
			}
		}
		return getGlobalFunctions(sourceModule, functionName, cache, monitor);
	};

	@NonNull
	private static IMethod[] getGlobalFunctions(ISourceModule sourceModule, String functionName,
			IModelAccessCache cache, IProgressMonitor monitor) throws ModelException {
		if (cache != null) {
			Collection<IMethod> functions = cache.getGlobalFunctions(sourceModule, functionName, monitor);
			if (functions == null) {
				return PhpModelAccess.NULL_METHODS;
			}
			functions = filterTrueGlobal(functions);
			return functions.toArray(new IMethod[functions.size()]);
		}

		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		IMethod[] functions = PhpModelAccess.getDefault().findMethods(functionName, MatchRule.EXACT,
				Modifiers.AccGlobal, 0, scope, null);

		Collection<IMethod> filteredElements = filterElements(sourceModule, filterTrueGlobal(Arrays.asList(functions)),
				null, monitor);
		return filteredElements.toArray(new IMethod[filteredElements.size()]);
	}

	@NonNull
	private static Collection<IMethod> filterTrueGlobal(Collection<IMethod> functions) {
		List<IMethod> result = new ArrayList<IMethod>();
		for (IMethod method : functions) {
			if (method.getParent().getElementType() != IModelElement.TYPE) {
				result.add(method);
			}
		}
		return result;
	}

	/**
	 * This method searches for all fields that where declared in the specified
	 * method (including global variables that where introduced to this method
	 * using 'global' keyword)
	 * 
	 * @param method
	 *            Method to look at
	 * @param prefix
	 *            Field name
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 */
	@NonNull
	public static IModelElement[] getMethodFields(final IMethod method, final String prefix, final boolean exactName,
			IProgressMonitor monitor) {

		final List<IField> elements = new LinkedList<IField>();
		final Set<String> processedVars = new HashSet<String>();

		try {
			getMethodFields(method, prefix, exactName, elements, processedVars);

			// collect global variables
			ModuleDeclaration rootNode = SourceParserUtil.getModuleDeclaration(method.getSourceModule());
			MethodDeclaration methodDeclaration = PHPModelUtils.getNodeByMethod(rootNode, method);
			if (methodDeclaration != null) {
				methodDeclaration.traverse(new ASTVisitor() {
					public boolean visit(Statement s) throws Exception {
						if (s instanceof GlobalStatement) {
							GlobalStatement globalStatement = (GlobalStatement) s;
							for (Expression e : globalStatement.getVariables()) {
								if (e instanceof VariableReference) {
									VariableReference varReference = (VariableReference) e;
									String varName = varReference.getName();
									if (!processedVars.contains(varName)
											&& (exactName && varName.equalsIgnoreCase(prefix)
													|| !exactName && startsWithIgnoreCase(varName, prefix))) {
										elements.add(new FakeField((ModelElement) method, varName, e.sourceStart(),
												e.sourceEnd() - e.sourceStart()));
										processedVars.add(varName);
									}
								}
							}
						}
						return super.visit(s);
					}
				});
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}

		return elements.toArray(new IModelElement[elements.size()]);
	}

	public static void getMethodFields(final IMethod method, final String prefix, final boolean exactName,
			final List<IField> elements, final Set<String> processedVars) throws ModelException {
		IModelElement[] children = method.getChildren();
		for (IModelElement child : children) {
			if (child.getElementType() == IModelElement.FIELD) {
				String elementName = child.getElementName();
				if (exactName && elementName.equalsIgnoreCase(prefix)
						|| !exactName && startsWithIgnoreCase(elementName, prefix)) {

					IField field = (IField) child;
					if (!isSameFileExisting(elements, field)) {
						elements.add((IField) child);
						processedVars.add(elementName);
					}
				}
			}
		}
		if (isNestedAnonymousMethod(method)) {
			getMethodFields((IMethod) method.getParent().getParent(), prefix, exactName, elements, processedVars);
		}
	}

	public static boolean isNestedAnonymousMethod(final IMethod method) {
		return PHPCoreConstants.ANONYMOUS.equals(method.getElementName()) && method.getParent() instanceof IField
				&& method.getParent().getParent() instanceof IMethod;
	};

	private static boolean isSameFileExisting(List<IField> elements, IField field) {

		for (IField current : elements) {
			if (isSameField(current, field)) {
				return true;
			}
		}
		return false;

	}

	public static boolean isSameField(IField current, IField field) {

		if (!(field instanceof SourceField)) {
			return false;
		}
		if (current == field) {
			return true;
		}

		return current.getElementName().equals(field.getElementName()) && current.getParent() != null
				&& current.getParent().equals(field.getParent());

	}

	/**
	 * This method searches for all global fields that where declared in the
	 * specified file.
	 * 
	 * @param sourceModule
	 *            Source module to look at
	 * @param prefix
	 *            Field name
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 */
	@NonNull
	public static IField[] getFileFields(final ISourceModule sourceModule, final String prefix, final boolean exactName,
			IProgressMonitor monitor) {

		final List<IField> elements = new LinkedList<IField>();
		try {
			IField[] sourceModuleFields = sourceModule.getFields();
			for (IField field : sourceModuleFields) {
				String elementName = field.getElementName();
				if (exactName && elementName.equalsIgnoreCase(prefix)
						|| !exactName && startsWithIgnoreCase(elementName, prefix)) {
					elements.add(field);
				}
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}

		return elements.toArray(new IField[elements.size()]);
	}

	/**
	 * This method returns field declared under specified namespace
	 * 
	 * @param namespace
	 *            Namespace name
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param sourceModule
	 *            Source module where the field is referenced
	 * @param monitor
	 *            Progress monitor
	 * @return field declared in the specified namespace, or null if there is
	 *         none
	 * @throws ModelException
	 */
	@NonNull
	public static IField[] getNamespaceField(String namespace, String prefix, boolean exactName,
			ISourceModule sourceModule, IProgressMonitor monitor) throws ModelException {
		return getNamespaceField(namespace, prefix, exactName, sourceModule, null, monitor);
	}

	/**
	 * This method returns field declared under specified namespace
	 * 
	 * @param namespace
	 *            Namespace name
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param sourceModule
	 *            Source module where the field is referenced
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 *            Progress monitor
	 * @return field declared in the specified namespace, or null if there is
	 *         none
	 * @throws ModelException
	 */
	@NonNull
	public static IField[] getNamespaceField(String namespace, String prefix, boolean exactName,
			ISourceModule sourceModule, IModelAccessCache cache, IProgressMonitor monitor) throws ModelException {

		IType[] namespaces = getNamespaces(sourceModule, namespace, cache, monitor);
		Collection<IField> result = new LinkedList<IField>();
		for (IType ns : namespaces) {
			result.addAll(Arrays.asList(PHPModelUtils.getTypeField(ns, prefix, exactName)));
		}
		if (cache != null) {
			result = cache.filterModelElements(sourceModule, result, monitor);
		}
		return result.toArray(new IField[result.size()]);
	}

	/**
	 * This method returns method declared under specified namespace
	 * 
	 * @param namespace
	 *            Namespace name
	 * @param prefix
	 *            Function name or prefix
	 * @param exactName
	 *            Whether the type name is exact or it is prefix
	 * @param sourceModule
	 *            Source module where the function is referenced
	 * @param monitor
	 *            Progress monitor
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getNamespaceFunction(String namespace, String prefix, boolean exactName,
			ISourceModule sourceModule, IProgressMonitor monitor) throws ModelException {
		return getNamespaceFunction(namespace, prefix, exactName, sourceModule, null, monitor);
	}

	/**
	 * This method returns method declared under specified namespace
	 * 
	 * @param namespace
	 *            Namespace name
	 * @param prefix
	 *            Function name or prefix
	 * @param exactName
	 *            Whether the type name is exact or it is prefix
	 * @param sourceModule
	 *            Source module where the function is referenced
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 *            Progress monitor
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getNamespaceFunction(String namespace, String prefix, boolean exactName,
			ISourceModule sourceModule, IModelAccessCache cache, IProgressMonitor monitor) throws ModelException {

		IType[] namespaces = getNamespaces(sourceModule, namespace, cache, monitor);
		Collection<IMethod> result = new LinkedList<IMethod>();
		for (IType ns : namespaces) {
			result.addAll(Arrays.asList(PHPModelUtils.getTypeMethod(ns, prefix, exactName)));
		}
		if (cache != null) {
			result = cache.filterModelElements(sourceModule, result, monitor);
		}
		return result.toArray(new IMethod[result.size()]);
	}

	/**
	 * Guess the namespace where the specified element is declared.
	 * 
	 * @param elementName
	 *            The name of the element, like: \A\B, A\B, namespace\B, \B,
	 *            etc...
	 * @param sourceModule
	 *            Source module where the element is referenced
	 * @param offset
	 *            The offset in file where the element is referenced
	 * @param monitor
	 * @return model elements of found namespace, otherwise <code>null</code>
	 *         (global namespace)
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getNamespaceOf(String elementName, ISourceModule sourceModule, int offset,
			IProgressMonitor monitor) throws ModelException {
		return getNamespaceOf(elementName, sourceModule, offset, null, monitor);
	}

	/**
	 * Guess the namespace where the specified element is declared.
	 * 
	 * @param elementName
	 *            The name of the element, like: \A\B, A\B, namespace\B, \B,
	 *            etc...
	 * @param sourceModule
	 *            Source module where the element is referenced
	 * @param offset
	 *            The offset in file where the element is referenced
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 * @return model elements of found namespace, otherwise <code>null</code>
	 *         (global namespace)
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getNamespaceOf(String elementName, ISourceModule sourceModule, int offset,
			IModelAccessCache cache, IProgressMonitor monitor) throws ModelException {
		String namespace = extractNamespaceName(elementName, sourceModule, offset);
		if (namespace != null && namespace.length() > 0) {
			IType[] namespaces = getNamespaces(sourceModule, namespace, cache, monitor);
			if (cache != null) {
				Collection<IType> result = Arrays.asList(namespaces);
				result = cache.filterModelElements(sourceModule, result, monitor);
				return result.toArray(new IType[result.size()]);
			}
			return namespaces;
		}
		return PhpModelAccess.NULL_TYPES;
	}

	/**
	 * This method returns type declared under specified namespace
	 * 
	 * @param namespace
	 *            Namespace name
	 * @param prefix
	 *            Type name or prefix
	 * @param exactName
	 *            Whether the type name is exact or it is prefix
	 * @param sourceModule
	 *            Source module where the type is referenced
	 * @param monitor
	 *            Progress monitor
	 * @return type declared in the specified namespace, or null if there is
	 *         none
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getNamespaceType(String namespace, String prefix, boolean exactName,
			ISourceModule sourceModule, IProgressMonitor monitor, boolean isType) throws ModelException {
		return getNamespaceType(namespace, prefix, exactName, sourceModule, null, monitor, isType);
	}

	/**
	 * This method returns type declared under specified namespace
	 * 
	 * @param namespace
	 *            Namespace name
	 * @param prefix
	 *            Type name or prefix
	 * @param exactName
	 *            Whether the type name is exact or it is prefix
	 * @param sourceModule
	 *            Source module where the type is referenced
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 *            Progress monitor
	 * @param isType
	 * @return type declared in the specified namespace, or null if there is
	 *         none
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getNamespaceType(String namespace, String prefix, boolean exactName,
			ISourceModule sourceModule, IModelAccessCache cache, IProgressMonitor monitor, boolean isType)
			throws ModelException {

		IType[] namespaces = getNamespaces(sourceModule, namespace, cache, monitor);
		Collection<IType> result = new LinkedList<IType>();
		for (IType ns : namespaces) {
			result.addAll(Arrays.asList(PHPModelUtils.getTypeType(ns, prefix, exactName, isType)));
		}
		if (cache != null) {
			result = cache.filterModelElements(sourceModule, result, monitor);
		}
		return result.toArray(new IType[result.size()]);
	}

	@NonNull
	private static IType[] getNamespaces(ISourceModule sourceModule, String namespaceName, IModelAccessCache cache,
			IProgressMonitor monitor) throws ModelException {
		if (cache != null) {
			Collection<IType> namespaces = cache.getNamespaces(sourceModule, namespaceName, monitor);
			if (namespaces == null) {
				return PhpModelAccess.NULL_TYPES;
			}
			return namespaces.toArray(new IType[namespaces.size()]);
		}
		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		IType[] namespaces = PhpModelAccess.getDefault().findNamespaces(null, namespaceName, MatchRule.EXACT, 0, 0,
				scope, monitor);
		return namespaces;
	}

	public static TypeDeclaration getNodeByClass(ModuleDeclaration rootNode, IType type) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, type, DeclarationType.CLASS);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return (TypeDeclaration) visitor.getResult();
	}

	public static ASTNode getNodeByElement(ModuleDeclaration rootNode, IModelElement element) throws ModelException {
		switch (element.getElementType()) {
		case IModelElement.TYPE:
			return getNodeByClass(rootNode, (IType) element);
		case IModelElement.METHOD:
			return getNodeByMethod(rootNode, (IMethod) element);
		case IModelElement.FIELD:
			return getNodeByField(rootNode, (IField) element);
		default:
			throw new IllegalArgumentException("Unsupported element type: " //$NON-NLS-1$
					+ element.getClass().getName());
		}
	}

	public static ASTNode getNodeByField(ModuleDeclaration rootNode, IField field) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, field, DeclarationType.FIELD);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return (ASTNode) visitor.getResult();
	}

	public static MethodDeclaration getNodeByMethod(ModuleDeclaration rootNode, IMethod method) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, method, DeclarationType.METHOD);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return (MethodDeclaration) visitor.getResult();
	}

	/**
	 * Returns all super classes filtered using file hierarchy
	 * 
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getSuperClasses(IType type, ITypeHierarchy hierarchy) throws ModelException {
		if (hierarchy == null) {
			if (!PHPToolkitUtil.isFromPHPProject(type)) {
				return EMPTY_TYPES;
			} else {
				hierarchy = type.newSupertypeHierarchy(null);
			}
		}
		Collection<IType> filtered = filterElements(type.getSourceModule(),
				Arrays.asList(hierarchy.getAllSuperclasses(type)), null, null);
		return filtered.toArray(new IType[filtered.size()]);
	}

	/**
	 * Finds field in the super class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @throws CoreException
	 */
	@NonNull
	public static IField[] getSuperTypeHierarchyField(IType type, String prefix, boolean exactName,
			IProgressMonitor monitor) throws CoreException {
		return getSuperTypeHierarchyField(type, null, prefix, exactName, monitor);
	}

	/**
	 * Finds field in the super class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param hierarchy
	 *            Cached type hierarchy (<code>null</code> to build a new one)
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @throws CoreException
	 */
	@NonNull
	public static IField[] getSuperTypeHierarchyField(IType type, ITypeHierarchy hierarchy, String prefix,
			boolean exactName, IProgressMonitor monitor) throws CoreException {

		IType[] allSuperclasses = getSuperClasses(type, hierarchy);
		return getTypesField(allSuperclasses, prefix, exactName);
	}

	/**
	 * Finds method in the super class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @throws CoreException
	 */
	@NonNull
	public static IMethod[] getSuperTypeHierarchyMethod(IType type, String prefix, boolean exactName,
			IProgressMonitor monitor) throws CoreException {
		return getSuperTypeHierarchyMethod(type, null, prefix, exactName, monitor);
	}

	/**
	 * Finds method in the super class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param hierarchy
	 *            Cached type hierarchy (<code>null</code> to build a new one)
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @throws CoreException
	 */
	@NonNull
	public static IMethod[] getSuperTypeHierarchyMethod(IType type, ITypeHierarchy hierarchy, String prefix,
			boolean exactName, IProgressMonitor monitor) throws CoreException {

		IType[] allSuperclasses = getSuperClasses(type, hierarchy);
		return getTypesMethod(allSuperclasses, prefix, exactName);
	}

	/**
	 * Returns the type field element by name
	 * 
	 * @param type
	 *            Type
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact name or prefix
	 * @throws ModelException
	 */
	@NonNull
	public static IField[] getTypeField(IType type, String prefix, boolean exactName) throws ModelException {

		List<IField> result = new LinkedList<IField>();
		if (type.exists()) {
			Set<String> nameSet = new HashSet<String>();
			IField[] fields = type.getFields();
			for (IField field : fields) {
				String elementName = field.getElementName();

				if (elementName.startsWith("$")) { //$NON-NLS-1$
					nameSet.add(elementName.substring(1));
				}
				if (elementName.startsWith("$") && !prefix.startsWith("$")) { //$NON-NLS-1$ //$NON-NLS-2$
					elementName = elementName.substring(1);
				}
				if (exactName && elementName.equalsIgnoreCase(prefix)
						|| !exactName && startsWithIgnoreCase(elementName, prefix)) {
					result.add(field);
				}
			}
			fields = TraitUtils.getTraitFields(type, nameSet);
			for (IField field : fields) {
				String elementName = field.getElementName();
				if (elementName.startsWith("$") && !prefix.startsWith("$")) { //$NON-NLS-1$ //$NON-NLS-2$
					elementName = elementName.substring(1);
				}
				if (exactName && elementName.equalsIgnoreCase(prefix)
						|| !exactName && startsWithIgnoreCase(elementName, prefix)) {
					result.add(field);
				}
			}
		}
		return result.toArray(new IField[result.size()]);
	}

	/**
	 * Finds field by name in the class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param hierarchy
	 *            Cached type hierarchy
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static IField[] getTypeHierarchyField(IType type, ITypeHierarchy hierarchy, String prefix, boolean exactName,
			IProgressMonitor monitor) throws CoreException {
		if (prefix == null) {
			throw new NullPointerException();
		}
		final List<IField> fields = new LinkedList<IField>();
		fields.addAll(Arrays.asList(getTypeField(type, prefix, exactName)));
		if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
			fields.addAll(Arrays.asList(getSuperTypeHierarchyField(type, hierarchy, prefix, exactName, monitor)));
		}
		return fields.toArray(new IField[fields.size()]);
	}

	/**
	 * Finds field by name in the class hierarchy (including the class itself)
	 * 
	 * @param type
	 *            Class element
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static IField[] getTypeHierarchyField(IType type, String prefix, boolean exactName, IProgressMonitor monitor)
			throws CoreException {
		return getTypeHierarchyField(type, null, prefix, exactName, monitor);
	}

	/**
	 * Finds field documentation by field name in the class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param name
	 *            Field name
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static PHPDocBlock[] getTypeHierarchyFieldDoc(IType type, String name, boolean exactName,
			IProgressMonitor monitor) throws CoreException {

		if (name == null) {
			throw new NullPointerException();
		}
		final List<PHPDocBlock> docs = new LinkedList<PHPDocBlock>();
		for (IField field : getTypeHierarchyField(type, name, exactName, monitor)) {
			PHPDocBlock docBlock = getDocBlock(field);
			if (docBlock != null) {
				docs.add(docBlock);
			}
		}
		return docs.toArray(new PHPDocBlock[docs.size()]);
	}

	/**
	 * Finds method by name in the class hierarchy (including the class itself)
	 * 
	 * @param type
	 *            Class element
	 * @param hierarchy
	 *            Cached type hierarchy
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static IMethod[] getTypeHierarchyMethod(IType type, ITypeHierarchy hierarchy, String prefix,
			boolean exactName, IProgressMonitor monitor) throws CoreException {

		if (prefix == null) {
			throw new NullPointerException();
		}
		final List<IMethod> methods = new LinkedList<IMethod>();
		methods.addAll(Arrays.asList(getTypeMethod(type, prefix, exactName)));
		if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
			methods.addAll(Arrays.asList(getSuperTypeHierarchyMethod(type, hierarchy, prefix, exactName, monitor)));
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * Finds the first method by name in the class hierarchy (including the
	 * class itself)
	 * 
	 * @param type
	 *            Class element
	 * @param hierarchy
	 *            Cached type hierarchy
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static IMethod[] getFirstTypeHierarchyMethod(IType type, ITypeHierarchy hierarchy, String prefix,
			boolean exactName, IProgressMonitor monitor) throws CoreException {

		if (prefix == null) {
			throw new NullPointerException();
		}
		final List<IMethod> methods = new LinkedList<IMethod>();
		methods.addAll(Arrays.asList(getTypeMethod(type, prefix, exactName)));
		if (type.getSuperClasses() != null && type.getSuperClasses().length > 0 && methods.size() == 0) {
			IType[] allSuperclasses = getSuperClasses(type, hierarchy);
			for (IType superClass : allSuperclasses) {
				IMethod[] method = getTypeMethod(superClass, prefix, exactName);
				if (method != null && method.length > 0) {
					methods.addAll(Arrays.asList(method));
					break;
				}
			}

		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * Finds method by name in the class hierarchy (including the class itself)
	 * 
	 * @param type
	 *            Class element
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static IMethod[] getTypeHierarchyMethod(IType type, String prefix, boolean exactName,
			IProgressMonitor monitor) throws CoreException {
		return getTypeHierarchyMethod(type, null, prefix, exactName, monitor);
	}

	@NonNull
	public static PHPDocBlock[] getTypeHierarchyMethodDoc(IType type, String prefix, boolean exactName,
			IProgressMonitor monitor) throws CoreException {
		return getTypeHierarchyMethodDoc(type, null, prefix, exactName, monitor);
	}

	/**
	 * Finds method documentation by method name in the class hierarchy
	 * 
	 * @param type
	 *            Class element
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param monitor
	 *            Progress monitor
	 * @throws CoreException
	 */
	@NonNull
	public static PHPDocBlock[] getTypeHierarchyMethodDoc(IType type, ITypeHierarchy hierarchy, String prefix,
			boolean exactName, IProgressMonitor monitor) throws CoreException {

		if (prefix == null) {
			throw new NullPointerException();
		}
		final List<PHPDocBlock> docs = new LinkedList<PHPDocBlock>();
		for (IMethod method : getTypeHierarchyMethod(type, hierarchy, prefix, exactName, monitor)) {
			PHPDocBlock docBlock = getDocBlock(method);
			if (docBlock != null) {
				docs.add(docBlock);
			}
		}
		return docs.toArray(new PHPDocBlock[docs.size()]);
	}

	/**
	 * Returns the type method element by name
	 * 
	 * @param type
	 *            Type
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact name or prefix
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getTypeMethod(IType type, String prefix, boolean exactName) throws ModelException {

		List<IMethod> result = new LinkedList<IMethod>();
		if (type.exists()) {
			Set<String> nameSet = new HashSet<String>();
			IMethod[] methods = type.getMethods();
			for (IMethod method : methods) {
				String elementName = method.getElementName();
				nameSet.add(elementName);
				if (exactName && elementName.equalsIgnoreCase(prefix)
						|| !exactName && startsWithIgnoreCase(elementName, prefix)) {
					result.add(method);
				}
			}
			methods = TraitUtils.getTraitMethods(type, nameSet);
			for (IMethod method : methods) {
				String elementName = method.getElementName();
				if (exactName && elementName.equalsIgnoreCase(prefix)
						|| !exactName && startsWithIgnoreCase(elementName, prefix)) {
					result.add(method);
				}
			}
		}
		return result.toArray(new IMethod[result.size()]);
	}

	/**
	 * This method returns type corresponding to its name and the file where it
	 * was referenced. The type name may contain also the namespace part, like:
	 * A\B\C or \A\B\C
	 * 
	 * @param typeName
	 *            Tye fully qualified type name
	 * @param sourceModule
	 *            The file where the element is referenced
	 * @param offset
	 *            The offset where the element is referenced
	 * @param monitor
	 *            Progress monitor
	 * @return a list of relevant IType elements
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getTypes(String typeName, ISourceModule sourceModule, int offset, IProgressMonitor monitor)
			throws ModelException {
		return getTypes(typeName, sourceModule, offset, null, monitor);
	}

	/**
	 * This method returns type corresponding to its name and the file where it
	 * was referenced. The type name may contain also the namespace part, like:
	 * A\B\C or \A\B\C
	 * 
	 * @param typeName
	 *            Tye fully qualified type name
	 * @param sourceModule
	 *            The file where the element is referenced
	 * @param offset
	 *            The offset where the element is referenced
	 * @param cache
	 *            Model access cache if available
	 * @param monitor
	 *            Progress monitor
	 * @return a list of relevant IType elements
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getTypes(String typeName, ISourceModule sourceModule, int offset, IModelAccessCache cache,
			IProgressMonitor monitor) throws ModelException {
		return getTypes(typeName, sourceModule, offset, cache, monitor, true, false);
	}

	@NonNull
	public static IType[] getTypes(String typeName, ISourceModule sourceModule, int offset, IModelAccessCache cache,
			IProgressMonitor monitor, boolean isType) throws ModelException {
		return getTypes(typeName, sourceModule, offset, cache, monitor, isType, false);
	}

	@NonNull
	public static IType[] getTypes(String typeName, ISourceModule sourceModule, int offset, IModelAccessCache cache,
			IProgressMonitor monitor, boolean isType, boolean isGlobal) throws ModelException {

		if (typeName == null || typeName.length() == 0) {
			return PhpModelAccess.NULL_TYPES;
		}

		if (!isGlobal) {
			String namespace = extractNamespaceName(typeName, sourceModule, offset);
			typeName = extractElementName(typeName);
			if (namespace != null) {
				if (namespace.length() > 0) {
					typeName = getRealName(typeName, sourceModule, offset, typeName);

					IType[] types = getNamespaceType(namespace, typeName, true, sourceModule, cache, monitor, isType);
					if (types.length > 0) {
						return types;
					}
					return PhpModelAccess.NULL_TYPES;
				}
				// it's a global reference: \A
			} else {
				// look for the element in current namespace:
				IType currentNamespace = getCurrentNamespace(sourceModule, offset);
				if (currentNamespace != null) {
					namespace = currentNamespace.getElementName();
					IType[] types = getNamespaceType(namespace, typeName, true, sourceModule, cache, monitor, isType);
					if (types.length > 0) {
						return types;
					}
				}
			}
		}

		Collection<IType> types;
		if (cache == null) {
			IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
			if (isType) {
				types = Arrays
						.asList(PhpModelAccess.getDefault().findTypes(typeName, MatchRule.EXACT, 0, 0, scope, null));
			} else {
				types = Arrays
						.asList(PhpModelAccess.getDefault().findTraits(typeName, MatchRule.EXACT, 0, 0, scope, null));
			}
		} else {
			// Cached types will already be filtered by method
			// PHPModelUtils.filterElements() when cache is an instance of
			// PerFileModelAccessCache. The filtering is too early here but it's
			// ok so long it doesn't change the results of bottom
			// getCurrentNamespace(type) and filterElements(sourceModule,
			// result, null, monitor) filtering for types without namespace.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=497003 and
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=496530
			if (isType) {
				types = cache.getTypes(sourceModule, typeName, null, monitor);
			} else {
				types = cache.getTraits(sourceModule, typeName, null, monitor);
			}
			if (types == null) {
				return PhpModelAccess.NULL_TYPES;
			}
		}
		List<IType> result = new ArrayList<IType>();
		for (IType type : types) {
			if (getCurrentNamespace(type) == null) {
				result.add(type);
			}
		}
		types = filterElements(sourceModule, result, null, monitor);
		return result.toArray(new IType[result.size()]);
	}

	@NonNull
	public static IType[] getTraits(String typeName, ISourceModule sourceModule, int offset, IModelAccessCache cache,
			IProgressMonitor monitor) throws ModelException {
		return getTypes(typeName, sourceModule, offset, cache, monitor, false, false);
	}

	/**
	 * Finds field in the list of given types
	 * 
	 * @param types
	 *            List of types
	 * @param prefix
	 *            Field name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @throws ModelException
	 */
	@NonNull
	public static IField[] getTypesField(IType[] types, String prefix, boolean exactName) throws ModelException {
		List<IField> result = new LinkedList<IField>();
		for (IType type : types) {
			result.addAll(Arrays.asList(getTypeField(type, prefix, exactName)));
		}
		return result.toArray(new IField[result.size()]);
	}

	/**
	 * Finds method in the list of given types
	 * 
	 * @param types
	 *            List of types
	 * @param prefix
	 *            Method name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getTypesMethod(IType[] types, String prefix, boolean exactName) throws ModelException {
		List<IMethod> result = new LinkedList<IMethod>();
		for (IType type : types) {
			result.addAll(Arrays.asList(getTypeMethod(type, prefix, exactName)));
		}
		return result.toArray(new IMethod[result.size()]);
	}

	/**
	 * Returns the type inner type element by name
	 * 
	 * @param type
	 *            Type
	 * @param prefix
	 *            Enclosed type name or prefix
	 * @param exactName
	 *            Whether the name is exact or it is prefix
	 * @param isType
	 * @throws ModelException
	 */
	@NonNull
	public static IType[] getTypeType(IType type, String prefix, boolean exactName, boolean isType)
			throws ModelException {
		List<IType> result = new LinkedList<IType>();
		IType[] types = type.getTypes();
		for (IType t : types) {
			if (isType && PHPFlags.isTrait(t.getFlags()) || !isType && !PHPFlags.isTrait(t.getFlags())) {
				continue;
			}
			String elementName = t.getElementName();
			if (exactName && elementName.equalsIgnoreCase(prefix)
					|| !exactName && startsWithIgnoreCase(elementName, prefix)) {
				result.add(t);
			}
		}
		return result.toArray(new IType[result.size()]);
	}

	@NonNull
	public static IType[] getTypeType(IType type, String prefix, boolean exactName) throws ModelException {
		return getTypeType(type, prefix, exactName, true);
	}

	/**
	 * Returns methods that must be overridden in first non-abstract class in
	 * hierarchy.
	 * 
	 * @param type
	 *            Type to start the search from
	 * @param monitor
	 *            Progress monitor
	 * @return unimplemented methods
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getUnimplementedMethods(IType type, IProgressMonitor monitor) throws ModelException {

		return getUnimplementedMethods(type, null, monitor);
	}

	/**
	 * Returns methods that must be overridden in first non-abstract class in
	 * hierarchy.
	 * 
	 * @param type
	 *            Type to start the search from
	 * @param cache
	 *            Temporary model cache instance
	 * @param monitor
	 *            Progress monitor
	 * @return unimplemented methods
	 * @throws ModelException
	 */
	@NonNull
	public static IMethod[] getUnimplementedMethods(IType type, IModelAccessCache cache, IProgressMonitor monitor)
			throws ModelException {

		HashMap<String, IMethod> abstractMethods = new HashMap<String, IMethod>();
		HashSet<String> nonAbstractMethods = new HashSet<String>();

		internalGetUnimplementedMethods(type, nonAbstractMethods, abstractMethods, new HashSet<String>(), cache,
				monitor, true);

		for (String methodName : nonAbstractMethods) {
			Iterator<Map.Entry<String, IMethod>> iterator = abstractMethods.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, IMethod> entry = iterator.next();
				if (methodName.equalsIgnoreCase(entry.getKey())) {
					iterator.remove();
				}
			}
		}

		Collection<IMethod> unimplementedMethods = abstractMethods.values();
		return unimplementedMethods.toArray(new IMethod[unimplementedMethods.size()]);
	}

	private static void internalGetUnimplementedMethods(IType type, HashSet<String> nonAbstractMethods,
			HashMap<String, IMethod> abstractMethods, Set<String> processedTypes, IModelAccessCache cache,
			IProgressMonitor monitor, boolean checkConstructor) throws ModelException {

		int typeFlags = type.getFlags();
		IMethod[] methods = getTypeMethod(type, "", false); //$NON-NLS-1$
		for (IMethod method : methods) {
			String methodName = method.getElementName();
			int methodFlags = method.getFlags();
			boolean isAbstract = PHPFlags.isAbstract(methodFlags);
			if (/* !PHPFlags.isInterface(typeFlags)&& */isConstructor(method)) {
				if (checkConstructor) {
					checkConstructor = false;
				} else {
					continue;
				}
			}
			if (isAbstract || PHPFlags.isInterface(typeFlags)) {
				if (!abstractMethods.containsKey(methodName)) {
					abstractMethods.put(methodName, method);
				}
			} else if (!isAbstract) {
				nonAbstractMethods.add(methodName);
			}
		}

		String[] superClasses = type.getSuperClasses();
		if (superClasses != null) {
			for (String superClass : superClasses) {
				if (!processedTypes.add(superClass)) {
					continue;
				}

				Collection<IType> types = null;
				if (cache == null) {
					IDLTKSearchScope scope = SearchEngine.createSearchScope(type.getScriptProject());
					IType[] superTypes = PhpModelAccess.getDefault().findTypes(superClass, MatchRule.EXACT, 0,
							Modifiers.AccNameSpace, scope, null);
					types = fileNetworkFilter(type.getSourceModule(), Arrays.asList(superTypes), null, monitor);
				} else {
					String namespaceName = null;
					int i = superClass.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
					if (i != -1) {
						namespaceName = superClass.substring(0, i);
						superClass = superClass.substring(i + 1);
					}
					types = cache.getClassesOrInterfaces(type.getSourceModule(), superClass, namespaceName, monitor);
				}
				if (types != null) {
					for (IType superType : types) {
						internalGetUnimplementedMethods(superType, nonAbstractMethods, abstractMethods, processedTypes,
								cache, monitor, checkConstructor);
					}
				}
			}
		}
	}

	public static boolean isConstructor(IMethod method) {
		String methodName = method.getElementName();
		if (methodName.equals("__construct") //$NON-NLS-1$
				|| methodName.equals(method.getDeclaringType().getElementName())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param type
	 *            the given type
	 * @return if the given type has static member
	 */
	public static boolean hasStaticOrConstMember(IType type) {
		try {
			if (PHPFlags.isNamespace(type.getFlags())) {
				return false;
			}
			if (PHPVersion.PHP5_4.isLessThan(ProjectOptions.getPHPVersion(type))) {
				return true; // class constant always available
			}
			ITypeHierarchy hierarchy = type.newSupertypeHierarchy(null);
			IModelElement[] members = PHPModelUtils.getTypeHierarchyField(type, hierarchy, "", false, null); //$NON-NLS-1$
			if (hasStaticOrConstMember(members)) {
				return true;
			}
			members = PHPModelUtils.getTypeHierarchyMethod(type, hierarchy, "", //$NON-NLS-1$
					false, null);
			if (hasStaticOrConstMember(members)) {
				return true;
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}
		return false;
	}

	public static boolean hasStaticOrConstMember(IModelElement[] elements) throws ModelException {
		for (int i = 0; i < elements.length; i++) {
			IModelElement modelElement = elements[i];
			if (modelElement instanceof IMember) {
				IMember member = (IMember) modelElement;
				int flags = member.getFlags();
				if (Flags.isStatic(flags) || ((modelElement instanceof IField) && Flags.isFinal(flags))) {
					return true;
				}

			}
		}
		return false;
	}

	@NonNull
	public static Map<String, UsePart> getAliasToNSMap(final String prefix, ModuleDeclaration moduleDeclaration,
			final int offset, IType namespace, final boolean exactMatch) {
		final Map<String, UsePart> result = new HashMap<String, UsePart>();
		try {
			int start = 0;
			if (namespace != null) {
				start = namespace.getSourceRange().getOffset();
			}
			final int searchStart = start;

			moduleDeclaration.traverse(new ASTVisitor() {

				public boolean visit(Statement s) throws Exception {
					if (s instanceof UseStatement) {
						UseStatement useStatement = (UseStatement) s;
						for (UsePart usePart : useStatement.getParts()) {
							if (usePart.getAlias() != null && usePart.getAlias().getName() != null) {
								String name = usePart.getAlias().getName();
								if (startsWithIgnoreCase(name, prefix)) {
									result.put(name, usePart);
								}
							} else {
								String name = usePart.getNamespace().getFullyQualifiedName();
								int index = name.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
								if (index >= 0) {
									name = name.substring(index + 1);
								}
								if (exactMatch && name.equals(prefix) || !exactMatch && name.startsWith(prefix)) {
									result.put(name, usePart);

								}
							}
						}
					}
					return visitGeneral(s);
				}

				public boolean visitGeneral(ASTNode node) throws Exception {
					if (node.sourceStart() > offset || node.sourceEnd() < searchStart) {
						return false;
					}
					return super.visitGeneral(node);
				}
			});
		} catch (Exception e) {
			Logger.logException(e);
		}
		return result;
	}

	@Nullable
	public static String getClassNameForNewStatement(TextSequence newClassStatementText, PHPVersion phpVersion) {
		if (phpVersion.isGreaterThan(PHPVersion.PHP5_3)) {
			// TextSequence newClassStatementText =
			// statementText.subTextSequence(
			// functionNameStart + 1, propertyEndPosition - 1);
			String newClassName = newClassStatementText.toString().trim();
			if (newClassName.startsWith("new") && newClassName.endsWith(")")) { //$NON-NLS-1$ //$NON-NLS-2$
				int newClassNameEnd = getFunctionNameEndOffset(newClassStatementText,
						newClassStatementText.length() - 1);
				int newClassNameStart = PHPTextSequenceUtilities.readIdentifierStartIndex(phpVersion,
						newClassStatementText, newClassNameEnd, false);
				if (newClassNameStart > 3 && newClassNameStart < newClassNameEnd) {// should
																					// have
																					// blank
																					// chars
																					// after
					// 'new'
					newClassName = newClassStatementText.subSequence(newClassNameStart, newClassNameEnd).toString();
					return newClassName.trim();
				}
			} else if (newClassName.startsWith("new")) { //$NON-NLS-1$
				return newClassName.substring(3).trim();
			}
		}
		return null;
	}

	/**
	 * this function searches the sequence from the right closing bracket ")"
	 * and finding the position of the left "(" the offset has to be the offset
	 * of the "("
	 */
	public static int getFunctionNameEndOffset(TextSequence statementText, int offset) {
		if (statementText.charAt(offset) != ')') {
			return 0;
		}
		int currChar = offset;
		int bracketsNum = 1;
		char inStringMode = 0;
		while (bracketsNum != 0 && currChar > 0) {
			currChar--;
			// get the current char
			final char charAt = statementText.charAt(currChar);
			// if it is string close / open - update state
			if (charAt == '\'' || charAt == '"') {
				inStringMode = inStringMode == 0 ? charAt : inStringMode == charAt ? 0 : inStringMode;
			}

			if (inStringMode != 0)
				continue;

			if (charAt == ')') {
				bracketsNum++;
			} else if (charAt == '(') {
				bracketsNum--;
			}
		}
		return currChar;
	}

	public static String getFullName(IType declaringType) {
		try {
			if (PHPFlags.isNamespace(declaringType.getFlags())) {
				return declaringType.getElementName();
			}
			IModelElement parent = declaringType.getParent();
			if (parent.getElementType() == IModelElement.SOURCE_MODULE) {
				return declaringType.getElementName();
			} else if (parent.getElementType() == IModelElement.TYPE) {
				int parentFlags = ((IType) parent).getFlags();
				if (PHPFlags.isNamespace(parentFlags)
						&& parent.getParent().getElementType() == IModelElement.SOURCE_MODULE) {
					StringBuilder b = new StringBuilder(parent.getElementName());
					b.append(NamespaceReference.NAMESPACE_SEPARATOR);
					b.append(declaringType.getElementName());

					return b.toString();
				}
			}

			return getFullName(declaringType.getElementName(), declaringType.getSourceModule(),
					declaringType.getSourceRange().getOffset());
		} catch (ModelException e) {
			return declaringType.getElementName();
		}
	}

	@NonNull
	public static String getFullName(@NonNull String typeName, ISourceModule sourceModule, final int offset) {
		String namespace = extractNamespaceName(typeName, sourceModule, offset);
		String elementName = extractElementName(typeName);
		if (namespace != null) {
			if (namespace.length() > 0) {
				elementName = getRealName(elementName, sourceModule, offset, elementName);
				elementName = namespace + NamespaceReference.NAMESPACE_SEPARATOR + elementName;
			}
		} else {
			// look for the element in current namespace:
			IType currentNamespace = getCurrentNamespace(sourceModule, offset);
			if (currentNamespace != null) {
				namespace = currentNamespace.getElementName();
				elementName = namespace + NamespaceReference.NAMESPACE_SEPARATOR + elementName;
			}
		}
		return elementName != null ? elementName : ""; //$NON-NLS-1$
	}

	@NonNull
	public static String getFullName(NamespaceName namespaceName) {

		StringBuilder sb = new StringBuilder();
		if (namespaceName.isGlobal()) {
			sb.append(NamespaceReference.NAMESPACE_SEPARATOR);
		}
		List<Identifier> segments = namespaceName.segments();
		for (Identifier identifier : segments) {
			if (sb.length() == 0 && namespaceName.isGlobal()) {
				sb.append(NamespaceReference.NAMESPACE_SEPARATOR);
			} else if (sb.length() > 0) {
				sb.append(NamespaceReference.NAMESPACE_SEPARATOR);
			}
			sb.append(identifier.getName());
		}
		return sb.toString();
	}

	@Nullable
	public static String getLineSeparator(IProject project) {
		String lineSeparator = null;
		if (project != null) {
			lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
					Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { new ProjectScope(project) });
		}
		if (lineSeparator == null) {
			lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
					Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { InstanceScope.INSTANCE });
		}
		if (lineSeparator == null) {
			lineSeparator = System.getProperty(Platform.PREF_LINE_SEPARATOR);
		}
		return lineSeparator;
	}

	public static boolean isInUseTraitStatement(ModuleDeclaration rootNode, final int offset) {
		final boolean[] found = new boolean[1];
		found[0] = false;
		try {
			rootNode.traverse(new PHPASTVisitor() {
				public boolean visit(TraitUseStatement s) throws Exception {
					if (s.sourceStart() <= offset && s.sourceEnd() >= offset) {
						found[0] = true;
					}
					return false;
				}

				@Override
				public boolean visitGeneral(ASTNode node) throws Exception {
					if (node.sourceEnd() < offset || node.sourceStart() > offset) {
						return false;
					}

					return super.visitGeneral(node);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return found[0];
	}

	/**
	 * Strips single or double quotes from the start and from the end of the
	 * given string
	 * 
	 * @param name
	 *            String
	 * @return
	 */
	@NonNull
	public static String stripQuotes(String name) {
		int len = name.length();
		if (len > 1 && (name.charAt(0) == '\'' && name.charAt(len - 1) == '\''
				|| name.charAt(0) == '"' && name.charAt(len - 1) == '"')) {
			name = name.substring(1, len - 1);
		}
		return name;
	}

	public static boolean isQuotesString(String name) {
		int len = name.length();
		if (len > 1 && (name.charAt(0) == '\'' && name.charAt(len - 1) == '\''
				|| name.charAt(0) == '"' && name.charAt(len - 1) == '"')) {
			return true;
		}
		return false;
	}

	@Nullable
	public static IModelElement[] getTypeInString(ISourceModule sourceModule, IRegion wordRegion) {
		IModelElement[] elements = null;
		ModuleDeclaration parsedUnit = SourceParserUtil.getModuleDeclaration(sourceModule, null);

		ASTNode node = ASTUtils.findMinimalNode(parsedUnit, wordRegion.getOffset(),
				wordRegion.getOffset() + wordRegion.getLength());
		if (node instanceof Scalar) {
			Scalar scalar = (Scalar) node;
			if (PHPModelUtils.isQuotesString(scalar.getValue()) && scalar.getScalarType() == Scalar.TYPE_STRING) {
				try {
					elements = PHPModelUtils.getTypes(PHPModelUtils.stripQuotes(scalar.getValue()), sourceModule,
							scalar.sourceStart(), null, null);
				} catch (Exception e) {
				}
			}
		}
		return elements;
	}

}
