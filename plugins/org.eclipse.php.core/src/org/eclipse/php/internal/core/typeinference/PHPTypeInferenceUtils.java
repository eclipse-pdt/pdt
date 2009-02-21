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
package org.eclipse.php.internal.core.typeinference;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.ModelClassType;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.internal.core.ScriptProject;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPTypeInferenceUtils {

	public static IEvaluatedType combineMultiType(Collection<IEvaluatedType> evaluatedTypes) {
		MultiTypeType multiTypeType = new MultiTypeType();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type == null) {
				type = PHPSimpleTypes.NULL;
			}
			multiTypeType.addType(type);
		}
		return multiTypeType;
	}

	private static Collection<IEvaluatedType> resolveAmbiguousTypes(Collection<IEvaluatedType> evaluatedTypes) {
		List<IEvaluatedType> resolved = new LinkedList<IEvaluatedType>();
		for (IEvaluatedType type : evaluatedTypes) {
			if (type instanceof AmbiguousType) {
				AmbiguousType ambType = (AmbiguousType) type;
				resolved.addAll(resolveAmbiguousTypes(Arrays.asList(ambType.getPossibleTypes())));
			} else {
				resolved.add(type);
			}
		}
		return resolved;
	}

	public static IEvaluatedType combineTypes(Collection<IEvaluatedType> evaluatedTypes) {
		Set<IEvaluatedType> types = new HashSet<IEvaluatedType>(resolveAmbiguousTypes(evaluatedTypes));
		if (types.contains(null)) {
			types.remove(null);
			types.add(PHPSimpleTypes.NULL);
		}
		if (types.size() == 0) {
			return UnknownType.INSTANCE;
		}
		if (types.size() == 1) {
			return types.iterator().next();
		}
		return new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
	}

	public static IEvaluatedType resolveExpression(ISourceModule sourceModule, ASTNode expression) {
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		IContext context = ASTUtils.findContext(sourceModule, moduleDeclaration, expression);
		return resolveExpression(sourceModule, moduleDeclaration, context, expression);
	}

	public static IEvaluatedType resolveExpression(ISourceModule sourceModule, ModuleDeclaration moduleDeclaration, IContext context, ASTNode expression) {
		if (context != null) {
			PHPTypeInferencer typeInferencer = new PHPTypeInferencer();
			return typeInferencer.evaluateType(new ExpressionTypeGoal(context, expression));
		}
		return null;
	}

	/**
	 * Converts IEvaluatedType to IModelElement, if found. This method filters elements using file network dependencies.
	 * @param evaluatedType Evaluated type
	 * @param context
	 * @param offset
	 * @return model elements or <code>null</code> in case no element could be found
	 */
	public static IType[] getModelElements(IEvaluatedType evaluatedType, ISourceModuleContext context, int offset) {
		ISourceModule sourceModule = context.getSourceModule();

		IType[] elements = internalGetModelElements(evaluatedType, context, offset);
		if (elements == null) {
			return null;
		}

		Collection<IType> filterElements = PHPModelUtils.filterElements(sourceModule, Arrays.asList(elements));
		return filterElements.toArray(new IType[filterElements.size()]);
	}

	private static IType[] internalGetModelElements(IEvaluatedType evaluatedType, ISourceModuleContext context, int offset) {
		ISourceModule sourceModule = context.getSourceModule();

		if (evaluatedType instanceof ModelClassType) {
			return new IType[] { ((ModelClassType) evaluatedType).getTypeDeclaration() };
		}
		if (evaluatedType instanceof PHPClassType) {
			IScriptProject scriptProject = sourceModule.getScriptProject();
			if (!ScriptProject.hasScriptNature(scriptProject.getProject())) {
				List<IType> result = new LinkedList<IType>();
				try {
					IType[] types = sourceModule.getTypes();
					for (IType t : types) {
						if (t.getElementName().equalsIgnoreCase(evaluatedType.getTypeName())) {
							result.add(t);
							break;
						}
					}
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
				return result.toArray(new IType[result.size()]);
			} else {
				try {
					return getTypes(evaluatedType.getTypeName(), sourceModule, offset);
				} catch (ModelException e) {
					if (DLTKCore.DEBUG) {
						e.printStackTrace();
					}
				}
			}
		} else if (evaluatedType instanceof AmbiguousType) {
			List<IType> tmpList = new LinkedList<IType>();
			IEvaluatedType[] possibleTypes = ((AmbiguousType) evaluatedType).getPossibleTypes();
			for (IEvaluatedType possibleType : possibleTypes) {
				IType[] tmpArray = internalGetModelElements(possibleType, context, offset);
				if (tmpArray != null) {
					tmpList.addAll(Arrays.asList(tmpArray));
				}
			}
			// the elements are filtered already
			return tmpList.toArray(new IType[tmpList.size()]);
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
		return getGlobalTypes(typeName, sourceModule);
	}

	/**
	 * This method returns method corresponding to its name and the file where it was referenced.
	 * The method name may contain also the namespace part, like: A\B\foo() or \A\B\foo()
	 * @param methodName Tye fully qualified method name
	 * @param sourceModule The file where the element is referenced
	 * @param offset The offset where the element is referenced
	 * @return a list of relevant IMethod elements, or <code>null</code> in case there's no IMethod found
	 * @throws ModelException 
	 */
	public static IMethod[] getMethods(String methodName, ISourceModule sourceModule, int offset) throws ModelException {
		if (methodName == null || methodName.length() == 0) {
			return null;
		}
		String namespace = extractNamespaceName(methodName, sourceModule, offset);
		methodName = extractElementName(methodName);
		if (namespace != null) {
			if (namespace.length() > 0) {
				IMethod namespaceMethod = getNamespaceMethod(namespace, methodName, sourceModule);
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
				IMethod namespaceMethod = getNamespaceMethod(namespace, methodName, sourceModule);
				if (namespaceMethod != null) {
					return new IMethod[] { namespaceMethod };
				}
				// For functions and constants, PHP will fall back to global functions or constants if a namespaced function or constant does not exist:
				return getGlobalMethods(methodName, sourceModule);
			}
		}
		return getGlobalMethods(methodName, sourceModule);
	}

	/**
	 * This method returns field corresponding to its name and the file where it was referenced.
	 * The field name may contain also the namespace part, like: A\B\C or \A\B\C
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
					return getGlobalFields(fieldName, sourceModule);
				}
			}
		}
		return getGlobalFields(fieldName, sourceModule);
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
			return getNamespaces(namespace, sourceModule);
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

		boolean isGlobal = false;
		if (elementName.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			isGlobal = true;
			elementName = elementName.substring(1);
		}

		int nsIndex = elementName.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
		if (nsIndex != -1) {
			String namespace = elementName.substring(0, nsIndex);

			if (!isGlobal) {
				// 1. It can be a special 'namespace' keyword, which points to the current namespace:
				if ("namespace".equalsIgnoreCase(namespace)) {
					IType currentNamespace = PHPModelUtils.getCurrentNamespace(sourceModule, offset);
					return currentNamespace.getElementName();
				}

				// 2. it can be an alias - try to find relevant USE statement
				if (namespace.indexOf('\\') == -1) {
					ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
					UsePart usePart = ASTUtils.findUseStatement(moduleDeclaration, namespace, offset);
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

		// no namespace prefix in element name:
		return null;
	}

	/**
	 * This method returns type declared unders specified namespace
	 * @param namespace Namespace name
	 * @param typeName Type name
	 * @param sourceModule Source module where the type is referenced
	 * @return type declarated in the specified namespace, or null if there is none
	 * @throws ModelException 
	 */
	public static IType getNamespaceType(String namespace, String typeName, ISourceModule sourceModule) throws ModelException {
		for (IType ns : getNamespaces(namespace, sourceModule)) {
			IType type = PHPModelUtils.getTypeType(ns, typeName);
			if (type != null) {
				return type;
			}
		}
		return null;
	}

	/**
	 * This method returns method declared unders specified namespace
	 * @param namespace Namespace name
	 * @param methodName Method name
	 * @param sourceModule Source module where the method is referenced
	 * @return method declarated in the specified namespace, or null if there is none
	 * @throws ModelException 
	 */
	public static IMethod getNamespaceMethod(String namespace, String methodName, ISourceModule sourceModule) throws ModelException {
		for (IType ns : getNamespaces(namespace, sourceModule)) {
			IMethod method = PHPModelUtils.getTypeMethod(ns, methodName);
			if (method != null) {
				return method;
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
		for (IType ns : getNamespaces(namespace, sourceModule)) {
			IField field = PHPModelUtils.getTypeField(ns, fieldName);
			if (field != null) {
				return field;
			}
		}
		return null;
	}

	/**
	 * This method returns namespace elements (IType) by specified name. The name must be fully qualified name (not alias)
	 * @param namespace Namespace name
	 * @param sourceModule The file where the namespace is referenced from
	 * @return namespace element array
	 */
	public static IType[] getNamespaces(String namespace, ISourceModule sourceModule) {
		final List<IType> namespaces = new LinkedList<IType>();
		SearchEngine searchEngine = new SearchEngine();
		SearchPattern pattern = SearchPattern.createPattern(namespace, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
		try {
			IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					IType element = (IType) match.getElement();
					if (PHPFlags.isNamespace(element.getFlags())) {
						namespaces.add(element);
					}
				}
			}, null);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return (IType[]) namespaces.toArray(new IType[namespaces.size()]);
	}

	/**
	 * This method returns type elements (IType) by the specified name. Namespaces are excluded.
	 * The element must be declared in a global scope.
	 * 
	 * @param typeName Type name
	 * @param sourceModule The file where the type is referenced from
	 * @return type element array
	 */
	public static IType[] getGlobalTypes(String typeName, ISourceModule sourceModule) {
		final List<IType> types = new LinkedList<IType>();
		SearchEngine searchEngine = new SearchEngine();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		SearchPattern pattern = SearchPattern.createPattern(typeName, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					IType element = (IType) match.getElement();
					if (!PHPFlags.isNamespace(element.getFlags())) {
						if (PHPModelUtils.getCurrentNamespace(element) == null) {
							types.add(element);
						}
					}
				}
			}, null);
		} catch (CoreException e) {
			Logger.logException(e);
			return null;
		}
		return (IType[]) types.toArray(new IType[types.size()]);
	}

	/**
	 * This method returns method elements (IMethod) by specified name.
	 * The element must be declared in a global scope.
	 * 
	 * @param methodName Method name
	 * @param sourceModule The file where the method is referenced from
	 * @return method element array
	 */
	public static IMethod[] getGlobalMethods(String methodName, ISourceModule sourceModule) {
		final List<IMethod> methods = new LinkedList<IMethod>();
		SearchEngine searchEngine = new SearchEngine();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		SearchPattern pattern = SearchPattern.createPattern(methodName, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					IMethod element = (IMethod) match.getElement();
					IModelElement parent = element.getParent();
					if (parent instanceof ISourceModule || parent instanceof IMethod || parent instanceof IType && PHPFlags.isNamespace(((IType) parent).getFlags())) {
						methods.add(element);
					}
				}
			}, null);
		} catch (CoreException e) {
			Logger.logException(e);
			return null;
		}
		return (IMethod[]) methods.toArray(new IMethod[methods.size()]);
	}

	/**
	 * This method returns field elements (IField) by specified name.
	 * The element must be declared in a global scope.
	 * 
	 * @param fieldName Field name
	 * @param sourceModule The file where the field is referenced from
	 * @return field element array
	 */
	public static IField[] getGlobalFields(String fieldName, ISourceModule sourceModule) {
		final List<IField> fields = new LinkedList<IField>();
		SearchEngine searchEngine = new SearchEngine();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(sourceModule.getScriptProject());
		SearchPattern pattern = SearchPattern.createPattern(fieldName, IDLTKSearchConstants.FIELD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE, PHPLanguageToolkit.getDefault());
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
