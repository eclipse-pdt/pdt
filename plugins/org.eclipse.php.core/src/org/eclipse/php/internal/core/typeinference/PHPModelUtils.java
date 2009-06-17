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
package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.Openable;
import org.eclipse.dltk.internal.core.util.HandleFactory;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.mixin.PHPMixinBuildVisitor;
import org.eclipse.php.internal.core.mixin.PHPMixinElementInfo;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.DeclarationSearcher.DeclarationType;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPModelUtils {
	
	private static final int MODE_MASK = SearchPattern.R_EXACT_MATCH | SearchPattern.R_PREFIX_MATCH | SearchPattern.R_PATTERN_MATCH | SearchPattern.R_REGEXP_MATCH;
	private static final String WILDCARD = "*"; //$NON-NLS-1$

	/**
	 * Finds model element that corresponds to the given AST node
	 * @param sourceModule Source module (file model element)
	 * @param unit AST nodes root
	 * @param node Requested node
	 * @return model element or <code>null</code> in case of error
	 */
	public static IModelElement getModelElementByNode(ISourceModule sourceModule, ModuleDeclaration unit, ASTNode node) {
		Assert.isNotNull(node);

		String key = PHPMixinBuildVisitor.restoreKeyByNode(sourceModule, unit, node);
		if (key != null) {
			IMixinElement[] elements = PHPMixinModel.getInstance(sourceModule.getScriptProject()).getRawModel().find(key);
			if (elements.length > 0) {
				Object[] allObjects = elements[0].getAllObjects();
				for (Object obj : allObjects) {
					IModelElement element = (IModelElement) ((PHPMixinElementInfo) obj).getObject();
					return element;
				}
			}
		}
		return null;
	}

	/**
	 * Finds method by name in the class hierarchy
	 * @param type Class element
	 * @param name Method name
	 * @param monitor Progress monitor
	 * @return method element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public static IMethod[] getTypeHierarchyMethod(IType type, String name, IProgressMonitor monitor) throws CoreException {
		if (name == null) {
			throw new NullPointerException();
		}

		final List<IMethod> methods = new LinkedList<IMethod>();
		if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
			IDLTKSearchScope scope = SearchEngine.createSuperHierarchyScope(type);
			SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
	
			new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					methods.add((IMethod) match.getElement());
				}
			}, monitor);
		} else {
			IMethod method = type.getMethod(name);
			if (method.exists()) {
				methods.add(method);
			}
		}

		return methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * Finds method documentation by method name in the class hierarchy
	 * @param type Class element
	 * @param name Method name
	 * @param monitor Progress monitor
	 * @return method phpdoc element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public static PHPDocBlock[] getTypeHierarchyMethodDoc(IType type, String name, IProgressMonitor monitor) throws CoreException {
		if (name == null) {
			throw new NullPointerException();
		}

		final List<PHPDocBlock> docs = new LinkedList<PHPDocBlock>();
		
		for (IMethod method : getTypeHierarchyMethod(type, name, monitor)) {
			PHPDocBlock docBlock = getDocBlock(method);
			if (docBlock != null) {
				docs.add(docBlock);
			}
		}
		return docs.toArray(new PHPDocBlock[docs.size()]);
	}
	

	/**
	 * Finds field by name in the class hierarchy
	 * @param type Class element
	 * @param name Field name
	 * @param monitor Progress monitor
	 * @return field element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public static IField[] getTypeHierarchyField(IType type, String name, IProgressMonitor monitor) throws CoreException {
		if (name == null) {
			throw new NullPointerException();
		}

		final List<IField> fields = new LinkedList<IField>();
		if (type.getSuperClasses() != null && type.getSuperClasses().length > 0) {
			IDLTKSearchScope scope = SearchEngine.createSuperHierarchyScope(type);
			SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
	
			new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					fields.add((IField) match.getElement());
				}
			}, monitor);
		} else {
			IField field = type.getField(name);
			if (field.exists()) {
				fields.add(field);
			}
		}

		return fields.toArray(new IField[fields.size()]);
	}

	/**
	 * Finds field documentation by field name in the class hierarchy
	 * @param type Class element
	 * @param name Field name
	 * @param monitor Progress monitor
	 * @return field phpdoc element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public static PHPDocBlock[] getTypeHierarchyFieldDoc(IType type, String name, IProgressMonitor monitor) throws CoreException {
		if (name == null) {
			throw new NullPointerException();
		}

		final List<PHPDocBlock> docs = new LinkedList<PHPDocBlock>();
		
		for (IField field : getTypeHierarchyField(type, name, monitor)) {
			PHPDocBlock docBlock = getDocBlock(field);
			if (docBlock != null) {
				docs.add(docBlock);
			}
		}
		return docs.toArray(new PHPDocBlock[docs.size()]);
	}

	/**
	 * Returns PHPDoc block associated with the given IType element
	 * @param type
	 * @return
	 */
	public static PHPDocBlock getDocBlock(IType type) {
		if (type == null) {
			return null;
		}
		try {
			ISourceModule sourceModule = type.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			TypeDeclaration typeDeclaration = PHPModelUtils.getNodeByClass(moduleDeclaration, type);
			if (typeDeclaration instanceof IPHPDocAwareDeclaration) {
				return ((IPHPDocAwareDeclaration)typeDeclaration).getPHPDoc();
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Returns PHPDoc block associated with the given IField element
	 * @param field
	 * @return
	 */
	public static PHPDocBlock getDocBlock(IField field) {
		if (field == null) {
			return null;
		}
		try {
			ISourceModule sourceModule = field.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			ASTNode fieldDeclaration = PHPModelUtils.getNodeByField(moduleDeclaration, field);
			if (fieldDeclaration instanceof IPHPDocAwareDeclaration) {
				return ((IPHPDocAwareDeclaration)fieldDeclaration).getPHPDoc();
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Returns PHPDoc block associated with the given IMethod element
	 * @param method
	 * @return
	 */
	public static PHPDocBlock getDocBlock(IMethod method) {
		if (method == null) {
			return null;
		}
		try {
			ISourceModule sourceModule = method.getSourceModule();
			ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
			MethodDeclaration methodDeclaration = PHPModelUtils.getNodeByMethod(moduleDeclaration, method);
			if (methodDeclaration instanceof IPHPDocAwareDeclaration) {
				return ((IPHPDocAwareDeclaration)methodDeclaration).getPHPDoc();
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static MethodDeclaration getNodeByMethod(ModuleDeclaration rootNode, IMethod method) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, method, DeclarationType.METHOD);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return (MethodDeclaration) visitor.getResult();
	}

	public static TypeDeclaration getNodeByClass(ModuleDeclaration rootNode, IType type) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, type, DeclarationType.CLASS);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return (TypeDeclaration) visitor.getResult();
	};

	public static ASTNode getNodeByField(ModuleDeclaration rootNode, IField field) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, field, DeclarationType.FIELD);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return (ASTNode) visitor.getResult();
	};
	
	public static ASTNode getNodeByElement(ModuleDeclaration rootNode, IModelElement element) throws ModelException {
		switch (element.getElementType()) {
			case IModelElement.TYPE:
				return getNodeByClass(rootNode, (IType) element);
			case IModelElement.METHOD:
				return getNodeByMethod(rootNode, (IMethod) element);
			case IModelElement.FIELD:
				return getNodeByField(rootNode, (IField) element);
			default:
				throw new IllegalArgumentException("Unsupported element type: " + element.getClass().getName());	
		}
	}

	public static IDLTKSearchScope createProjectSearchScope(IScriptProject project) {
		int includeMask = IDLTKSearchScope.SOURCES | IDLTKSearchScope.APPLICATION_LIBRARIES | IDLTKSearchScope.REFERENCED_PROJECTS | IDLTKSearchScope.SYSTEM_LIBRARIES;
		return SearchEngine.createSearchScope(project, includeMask);
	}

	/**
	 * Leaves most 'suitable' for current source module elements
	 * @param sourceModule
	 * @param elements
	 * @return
	 */
	public static <T extends IModelElement> Collection<T> filterElements(ISourceModule sourceModule, Collection<T> elements) {
		if (elements == null) {
			return null;
		}

		// Determine whether givent elements represent the same type and name,
		// but declared in different files (determine whether filtering is needed):
		int elementType = 0;
		String elementName = null;
		boolean fileNetworkFilter = true;
		for (T element : elements) {
			if (elementName == null) {
				elementType = element.getElementType();
				elementName = element.getElementName();
				continue;
			}
			if (!elementName.equals(element.getElementName()) || elementType != element.getElementType()) {
				fileNetworkFilter = false;
				break;
			}
		}

		if (fileNetworkFilter) {
			return fileNetworkFilter(sourceModule, elements);
		}
		return elements;
	}

	/**
	 * Filters model elements using file network.
	 * @param sourceModule
	 * @param elements
	 * @return
	 */
	private static <T extends IModelElement> Collection<T> fileNetworkFilter(ISourceModule sourceModule, Collection<T> elements) {

		if (elements != null && elements.size() > 0) {
			List<T> filteredElements = new LinkedList<T>();

			// If some of elements belong to current file return just it:
			for (T element : elements) {
				if (sourceModule.equals(element.getOpenable())) {
					filteredElements.add(element);
				}
			}
			if (filteredElements.size() == 0) {
				// Filter by includes network
				ReferenceTree referenceTree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, null);
				for (T element : elements) {
					if (LanguageModelInitializer.isLanguageModelElement(element) || referenceTree.find(((ModelElement) element).getSourceModule())) {
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
	

	/**
	 * Returns the current namespace by the specified model element
	 * @param element Model element
	 * @return namespace element, or <code>null</code> if the scope is global under the specified cursor position
	 */
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
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Returns the current class or interface by the specified file and offset
	 * @param sourceModule The file where current namespace is requested 
	 * @param offset The offset where current namespace is requested
	 * @return type element, or <code>null</code> if the scope not a class or interface scope
	 */
	public static IType getCurrentType(ISourceModule sourceModule, int offset) {
		try {
			return getCurrentType(sourceModule.getElementAt(offset));
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return null;
	}
	
	/**
	 * Returns the current class or interface by the specified file and offset
	 * @param sourceModule The file where current namespace is requested 
	 * @param offset The offset where current namespace is requested
	 * @return type element, or <code>null</code> if the scope not a class or interface scope
	 */
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
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Returns the current method or function by the specified file and offset
	 * @param sourceModule The file where current namespace is requested 
	 * @param offset The offset where current namespace is requested
	 * @return method element, or <code>null</code> if the scope not a method scope
	 */
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
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Returns the type field element by name
	 * @param type
	 * @param elementName
	 * @return a field or <code>null</code> in case there are no one
	 * @throws ModelException
	 */
	public static IField getTypeField(IType type, String elementName) throws ModelException {
		IField[] fields = type.getFields();
		for (IField field : fields) {
			if (field.getElementName().equalsIgnoreCase(elementName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Returns the type method element by name
	 * @param type
	 * @param elementName
	 * @return a method or <code>null</code> in case there are no one
	 * @throws ModelException
	 */
	public static IMethod getTypeMethod(IType type, String elementName) throws ModelException {
		IMethod[] methods = type.getMethods();
		for (IMethod method : methods) {
			if (method.getElementName().equalsIgnoreCase(elementName)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * Returns the type inner type element by name
	 * @param type
	 * @param elementName
	 * @return a type or <code>null</code> in case there are no one
	 * @throws ModelException
	 */
	public static IType getTypeType(IType type, String elementName) throws ModelException {
		IType[] types = type.getTypes();
		for (IType t : types) {
			if (t.getElementName().equalsIgnoreCase(elementName)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * This method returns type corresponding to its name and the file where it was referenced.
	 * The type name may contain also the namespace part, like: A\B\C or \A\B\C
	 * @param typeName Tye fully qualified type name
	 * @param sourceModule The file where the element is referenced
	 * @param offset The offset where the element is referenced
	 * @return a list of relevant IType elements, or <code>null</code> in case there's no IType found
	 * @throws ModelException 
	 */
	public static IType[] getTypes(String typeName, ISourceModule sourceModule, int offset) throws ModelException {
		if (typeName == null || typeName.length() == 0) {
			return null;
		}
	
		String namespace = extractNamespaceName(typeName, sourceModule, offset);
		typeName = extractElementName(typeName);
		if (namespace != null) {
			if (namespace.length() > 0) {
				IType namespaceType = getNamespaceType(namespace, typeName, sourceModule);
				if (namespaceType != null) {
					return new IType[] { namespaceType };
				}
				return null;
			}
			// it's a global reference: \A
		} else {
			// look for the element in current namespace:
			IType currentNamespace = getCurrentNamespace(sourceModule, offset);
			if (currentNamespace != null) {
				namespace = currentNamespace.getElementName();
				IType namespaceType = getNamespaceType(namespace, typeName, sourceModule);
				if (namespaceType != null) {
					return new IType[] { namespaceType };
				}
				return null;
			}
		}
	
		IType[] types = getTypes(typeName, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
		List<IType> result = new ArrayList<IType>(types.length);
		for (IType type : types) {
			if (getCurrentNamespace(type) == null) {
				result.add(type);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}
	
	/**
	 * This method returns function corresponding to its name and the file where it was referenced.
	 * The function name may contain also the namespace part, like: A\B\foo() or \A\B\foo()
	 * 
	 * @param functionName The fully qualified function name
	 * @param sourceModule The file where the element is referenced
	 * @param offset The offset where the element is referenced
	 * @return a list of relevant IMethod elements, or <code>null</code> in case there's no IMethod found
	 * @throws ModelException 
	 */
	public static IMethod[] getFunctions(String functionName, ISourceModule sourceModule, int offset) throws ModelException {
		if (functionName == null || functionName.length() == 0) {
			return null;
		}
		String namespace = extractNamespaceName(functionName, sourceModule, offset);
		functionName = extractElementName(functionName);
		if (namespace != null) {
			if (namespace.length() > 0) {
				IMethod namespaceMethod = getNamespaceFunction(namespace, functionName, sourceModule);
				if (namespaceMethod != null) {
					return new IMethod[] { namespaceMethod };
				}
				return null;
			}
			// it's a global reference: \foo()
		} else {
			// look for the element in current namespace:
			IType currentNamespace = getCurrentNamespace(sourceModule, offset);
			if (currentNamespace != null) {
				namespace = currentNamespace.getElementName();
				IMethod namespaceMethod = getNamespaceFunction(namespace, functionName, sourceModule);
				if (namespaceMethod != null) {
					return new IMethod[] { namespaceMethod };
				}
				// For functions and constants, PHP will fall back to global functions or constants if a namespaced function or constant does not exist:
				return getFunctions(functionName, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
			}
		}
		return getFunctions(functionName, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
	}

	/**
	 * This method returns field corresponding to its name and the file where it was referenced.
	 * The field name may contain also the namespace part, like: A\B\C or \A\B\C
	 * 
	 * @param fieldName Tye fully qualified field name
	 * @param sourceModule The file where the element is referenced
	 * @param offset The offset where the element is referenced
	 * @return a list of relevant IField elements, or <code>null</code> in case there's no IField found
	 * @throws ModelException 
	 */
	public static IField[] getFields(String fieldName, ISourceModule sourceModule, int offset) throws ModelException {
		if (fieldName == null || fieldName.length() == 0) {
			return null;
		}
		if (!fieldName.startsWith("$")) { // variables are not supported by namespaces in PHP 5.3
			String namespace = extractNamespaceName(fieldName, sourceModule, offset);
			fieldName = extractElementName(fieldName);
			if (namespace != null) {
				if (namespace.length() > 0) {
					IField namespaceField = getNamespaceField(namespace, fieldName, sourceModule);
					if (namespaceField != null) {
						return new IField[] { namespaceField };
					}
					return null;
				}
				// it's a global reference: \C
			} else {
				// look for the element in current namespace:
				IType currentNamespace = getCurrentNamespace(sourceModule, offset);
				if (currentNamespace != null) {
					namespace = currentNamespace.getElementName();
					IField namespaceField = getNamespaceField(namespace, fieldName, sourceModule);
					if (namespaceField != null) {
						return new IField[] { namespaceField };
					}
					// For functions and constants, PHP will fall back to global functions or constants if a namespaced function or constant does not exist:
					return getFields(fieldName, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
				}
			}
		}
		return getFields(fieldName, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
	}

	/**
	 * Returns the current namespace by the specified file and offset
	 * @param sourceModule The file where current namespace is requested 
	 * @param sourceModule The offset where current namespace is requested
	 * @return namespace element, or <code>null</code> if the scope is global under the specified cursor position
	 */
	public static IType getCurrentNamespace(ISourceModule sourceModule, int offset) {
		try {
			IModelElement currentNs = sourceModule.getElementAt(offset);
			while (currentNs != null) {
				if (currentNs instanceof IType && PHPFlags.isNamespace(((IType) currentNs).getFlags())) {
					return (IType) currentNs;
				}
				currentNs = currentNs.getParent();
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Extracts the element name from the given fully qualified name
	 * @param element Element name
	 * @return element name without the namespace prefix
	 */
	public static String extractElementName(String element) {
		int i = element.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		if (i != -1) {
			element = element.substring(i + 1).trim();
		}
		return element;
	}

	/**
	 * Guess the namespace where the specified element is declared.
	 * @param elementName The name of the element, like: \A\B, A\B, namespace\B, \B, etc...
	 * @param sourceModule Source module where the element is referenced
	 * @param offset The offset in file where the element is referenced
	 * @return model elements of found namespace, otherwise <code>null</code> (global namespace)
	 */
	public static IType[] getNamespaceOf(String elementName, ISourceModule sourceModule, int offset) {
		String namespace = extractNamespaceName(elementName, sourceModule, offset);
		if (namespace != null && namespace.length() > 0) {
			return getNamespaces(namespace, SearchPattern.R_EXACT_MATCH, SearchEngine.createSearchScope(sourceModule.getScriptProject()));
		}
		return null;
	}

	/**
	 * Extracts the namespace name from the specified element name and resolves it using USE statements that present in the file.
	 * @param elementName The name of the element, like: \A\B or A\B\C.
	 * @param sourceModule Source module where the element is referenced
	 * @param offset The offset where element is referenced
	 * @return namespace name:
	 * 	<pre>
	 *   1. <code>""</code> (empty string) indicates global namespace
	 *   2. non-empty string indicates a real namespace
	 *   3. <code>null</code> indicates that there's no namespace prefix in element name
	 *  </pre>
	 */
	public static String extractNamespaceName(String elementName, ISourceModule sourceModule, final int offset) {

		// Check class name aliasing:
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		UsePart usePart = ASTUtils.findUseStatementByAlias(moduleDeclaration, elementName, offset);
		if (usePart != null) {
			elementName = usePart.getNamespace().getFullyQualifiedName();
			if (elementName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
				elementName = NamespaceReference.NAMESPACE_SEPARATOR + elementName;
			}
		}

		boolean isGlobal = false;
		if (elementName.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			isGlobal = true;
		}

		int nsIndex = elementName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		if (nsIndex != -1) {
			String namespace = elementName.substring(0, nsIndex);
			if (isGlobal && namespace.length() > 0) {
				namespace = namespace.substring(1);
			}

			if (!isGlobal) {
				// 1. It can be a special 'namespace' keyword, which points to the current namespace:
				if ("namespace".equalsIgnoreCase(namespace)) {
					IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
					return currentNamespace.getElementName();
				}

				// 2. it can be an alias - try to find relevant USE statement
				if (namespace.indexOf('\\') == -1) {
					usePart = ASTUtils.findUseStatementByAlias(moduleDeclaration, namespace, offset);
					if (usePart != null) {
						return usePart.getNamespace().getFullyQualifiedName();
					}
				}

				// 3. it can be a sub-namespace of the current namespace:
				IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
				if (currentNamespace != null) {
					return new StringBuilder(currentNamespace.getElementName()).append(NamespaceReference.NAMESPACE_SEPARATOR).append(namespace).toString();
				}
			}

			// global namespace:
			return namespace;
		}

		return null;
	}

	/**
	 * This method returns type declared unders specified namespace
	 * 
	 * @param namespace Namespace name
	 * @param typeName Type name
	 * @param sourceModule Source module where the type is referenced
	 * @return type declarated in the specified namespace, or null if there is none
	 * @throws ModelException 
	 */
	public static IType getNamespaceType(String namespace, String typeName, ISourceModule sourceModule) throws ModelException {
		for (IType ns : getNamespaces(namespace, SearchPattern.R_EXACT_MATCH, SearchEngine.createSearchScope(sourceModule.getScriptProject()))) {
			IType type = PHPModelUtils.getTypeType(ns, typeName);
			if (type != null) {
				return type;
			}
		}
		return null;
	}

	/**
	 * This method returns method declared unders specified namespace
	 * 
	 * @param namespace Namespace name
	 * @param functionName Function name
	 * @param sourceModule Source module where the function is referenced
	 * @return function declarated in the specified namespace, or null if there is none
	 * @throws ModelException 
	 */
	public static IMethod getNamespaceFunction(String namespace, String functionName, ISourceModule sourceModule) throws ModelException {
		for (IType ns : getNamespaces(namespace, SearchPattern.R_EXACT_MATCH, SearchEngine.createSearchScope(sourceModule.getScriptProject()))) {
			IMethod function = PHPModelUtils.getTypeMethod(ns, functionName);
			if (function != null) {
				return function;
			}
		}
		return null;
	}

	/**
	 * This method returns field declared unders specified namespace
	 * @param namespace Namespace name
	 * @param fieldName Field name
	 * @param sourceModule Source module where the field is referenced
	 * @return field declarated in the specified namespace, or null if there is none
	 * @throws ModelException 
	 */
	public static IField getNamespaceField(String namespace, String fieldName, ISourceModule sourceModule) throws ModelException {
		for (IType ns : getNamespaces(namespace, SearchPattern.R_EXACT_MATCH, SearchEngine.createSearchScope(sourceModule.getScriptProject()))) {
			IField field = PHPModelUtils.getTypeField(ns, fieldName);
			if (field != null) {
				return field;
			}
		}
		return null;
	}

	/**
	 * This method returns type elements (IType) by the specified name (classes, interfaces and namespaces).
	 * The element must be declared in a global scope.
	 * 
	 * @param typeName Type name
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getTypes(String typeName, IDLTKSearchScope scope) {
		return getTypes(typeName, SearchPattern.R_EXACT_MATCH, scope);
	}

	/**
	 * This method returns all types (classes, interfaces and namespaces).
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getAllTypes(final IDLTKSearchScope scope) {
		return getTypes(WILDCARD, SearchPattern.R_PATTERN_MATCH, scope);
	}

	/**
	 * This method returns types (classes, interfaces and namespaces).
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getTypes(String prefix, int matchRule, final IDLTKSearchScope scope) {

		final Collection<IType> elements = new LinkedList<IType>();
		SearchEngine searchEngine = new SearchEngine();

		if ((matchRule & MODE_MASK) == SearchPattern.R_EXACT_MATCH) {
			try {
				SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						IType element = (IType) match.getElement();
						elements.add(element);
					}
				}, null);
			} catch (CoreException e) {
				Logger.logException(e);
				return null;
			}
		} else {
			try {
				final HandleFactory handleFactory = new HandleFactory();
				searchEngine.searchAllTypeNames("*".toCharArray(), SearchPattern.R_PATTERN_MATCH , prefix.toCharArray(), matchRule, IDLTKSearchConstants.DECLARATIONS, scope, new TypeNameRequestor() {
					public void acceptType(int modifiers, char[] packageName, char[] simpleTypeName, char[][] enclosingTypeNames, char[][] superTypes, String path) {
						Openable openable = handleFactory.createOpenable(path, scope);
						ModelElement parent = openable;
						if (enclosingTypeNames.length > 0) {
							parent = new FakeType(openable, new String(enclosingTypeNames[0]), Modifiers.AccNameSpace, null);
						}
						if (parent != null) {
							elements.add(new FakeType(parent, new String(simpleTypeName), modifiers, superTypes.length == 0 ? null : CharOperation.charArrayToStringArray(superTypes)));
						}
					}
				}, IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, null);
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		return (IType[]) elements.toArray(new IType[elements.size()]);
	}

	/**
	 * This method returns classes and interfaces
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getClassesAndInterfaces(String prefix, int matchRule, final IDLTKSearchScope scope) {
		IType[] types = getTypes(prefix, matchRule, scope);
		List<IType> result = new LinkedList<IType>();
		for (IType type : types) {
			try {
				if (!PHPFlags.isNamespace(type.getFlags())) {
					result.add(type);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	/**
	 * This method returns all classes and interfaces
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getAllClassesAndInterfaces(final IDLTKSearchScope scope) {
		return getClassesAndInterfaces(WILDCARD, SearchPattern.R_PATTERN_MATCH, scope);
	}
	
	/**
	 * This method returns classes by names (namespaces and interfaces are excluded).
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getClasses(String prefix, int matchRule, final IDLTKSearchScope scope) {
		IType[] types = getTypes(prefix, matchRule, scope);
		List<IType> result = new LinkedList<IType>();
		for (IType type : types) {
			try {
				if (!PHPFlags.isNamespace(type.getFlags()) && !PHPFlags.isInterface(type.getFlags())) {
					result.add(type);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	/**
	 * This method returns all classes (namespaces and interfaces are excluded)
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getAllClasses(final IDLTKSearchScope scope) {
		return getClasses(WILDCARD, SearchPattern.R_PATTERN_MATCH, scope);
	}
	
	/**
	 * This method returns interfaces by names (namespaces and classes are excluded).
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getInterfaces(String prefix, int matchRule, final IDLTKSearchScope scope) {
		IType[] types = getTypes(prefix, matchRule, scope);
		List<IType> result = new LinkedList<IType>();
		for (IType type : types) {
			try {
				if (PHPFlags.isInterface(type.getFlags())) {
					result.add(type);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	/**
	 * This method returns all interfaces (namespaces and classes are excluded)
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Type prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return type element array
	 */
	public static IType[] getAllInterfaces(final IDLTKSearchScope scope) {
		return getInterfaces(WILDCARD, SearchPattern.R_PATTERN_MATCH, scope);
	}
	
	/**
	 * This method returns namespaces by prefix.
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param prefix Namespace prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return namespace element array
	 */
	public static IType[] getNamespaces(String prefix, int matchRule, final IDLTKSearchScope scope) {
		IType[] types = getTypes(prefix, matchRule, scope);
		List<IType> result = new LinkedList<IType>();
		for (IType type : types) {
			try {
				if (PHPFlags.isNamespace(type.getFlags())) {
					result.add(type);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	/**
	 * This method returns all namespaces
	 * As this method can be heavy FakeType is returned.
	 * 
	 * @param scope Search scope
	 * @return namespace element array
	 */
	public static IType[] getAllNamespaces(final IDLTKSearchScope scope) {
		return getNamespaces(WILDCARD, SearchPattern.R_PATTERN_MATCH, scope);
	}

	/**
	 * This method returns method elements (IMethod) by specified name.
	 * 
	 * @param functionName Method name
	 * @param scope Search scope
	 * @return method element array
	 */
	public static IMethod[] getFunctions(String functionName, IDLTKSearchScope scope) {
		return getFunctions(functionName, SearchPattern.R_EXACT_MATCH, scope);
	}

	/**
	 * Returns all global method elements (IMethod).
	 * Warning: this method is heavy!
	 * 
	 * @param scope Search scope
	 * @return method element array
	 */
	public static IMethod[] getAllFunctions(IDLTKSearchScope scope) {
		return getFunctions(new String(WILDCARD), SearchPattern.R_PATTERN_MATCH, scope);
	}

	/**
	 * This method returns method elements (IMethod).
	 * 
	 * @param prefix Method prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return method element array
	 */
	public static IMethod[] getFunctions(String prefix, int matchRule, final IDLTKSearchScope scope) {

		final List<IMethod> methods = new LinkedList<IMethod>();
		SearchEngine searchEngine = new SearchEngine();

		if ((matchRule & MODE_MASK) == SearchPattern.R_EXACT_MATCH) {
			try {
				SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, matchRule, PHPLanguageToolkit.getDefault());
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						IMethod element = (IMethod) match.getElement();
						IModelElement parent = element.getParent();
						if (parent instanceof ISourceModule || parent instanceof IMethod || parent instanceof IType && PHPFlags.isNamespace(((IType) parent).getFlags())) {
							methods.add(element);
						}
					}
				}, new NullProgressMonitor());
			} catch (CoreException e) {
				Logger.logException(e);
				return null;
			}
		} else {
			final HandleFactory handleFactory = new HandleFactory();
			try {
				searchEngine.searchAllMethodNames(prefix.toCharArray(), matchRule, IDLTKSearchConstants.DECLARATIONS, scope, new MethodNameRequestor() {
					public void acceptMethod(int modifiers, char[] packageName, char[] simpleMethodName, char[][] enclosingTypeNames, char[][] parameterNames, String path) {
						if ((modifiers & Modifiers.AccGlobal) == 0) {
							return;
						}

						Openable openable = handleFactory.createOpenable(path, scope);
						ModelElement parent = openable;
						if (enclosingTypeNames.length > 0) {
							parent = new FakeType(openable, new String(enclosingTypeNames[0]), Modifiers.AccNameSpace, null);
						}
						FakeMethod method = new FakeMethod(parent, new String(simpleMethodName), modifiers);
						if (parameterNames != null) {
							method.setParameters(CharOperation.charArrayToStringArray(parameterNames));
						}
						methods.add(method);
					}
				}, IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, new NullProgressMonitor());
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		return (IMethod[]) methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * This method returns field elements (IField) by specified name.
	 * 
	 * @param fieldName Field name
	 * @param scope Search scope
	 * @return field element array
	 */
	public static IField[] getFields(String fieldName, IDLTKSearchScope scope) {
		return getFields(fieldName, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE, scope);
	}

	/**
	 * Returns all global fields. Warning: this method is heavy!
	 * 
	 * @param scope Search scope
	 * @return field element array
	 */
	public static IField[] getAllFields(IDLTKSearchScope scope) {
		return getFields(new String(WILDCARD), SearchPattern.R_PATTERN_MATCH, scope);
	}

	/**
	 * This method returns field elements (IField).
	 * 
	 * @param prefix Field prefix
	 * @param matchRule Search match rule
	 * @param scope Search scope
	 * @return field element array
	 */
	public static IField[] getFields(String prefix, int matchRule, IDLTKSearchScope scope) {
		final List<IField> fields = new LinkedList<IField>();

		SearchEngine searchEngine = new SearchEngine();
		SearchPattern pattern = SearchPattern.createPattern(prefix, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, matchRule, PHPLanguageToolkit.getDefault());
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					IField element = (IField) match.getElement();
					IModelElement parent = element.getParent();
					if (parent instanceof ISourceModule || parent instanceof IType && PHPFlags.isNamespace(((IType) parent).getFlags())) {
						fields.add(element);
					}
				}
			}, null);
		} catch (CoreException e) {
			Logger.logException(e);
			return null;
		}
		return (IField[]) fields.toArray(new IField[fields.size()]);
	}
}
