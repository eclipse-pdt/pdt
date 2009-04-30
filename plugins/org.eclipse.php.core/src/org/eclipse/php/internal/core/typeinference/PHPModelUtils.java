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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.nodes.IPHPDocAwareDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.mixin.PHPMixinBuildVisitor;
import org.eclipse.php.internal.core.mixin.PHPMixinElementInfo;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.DeclarationSearcher.DeclarationType;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPModelUtils {

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

		IDLTKSearchScope scope = SearchEngine.createSuperHierarchyScope(type);
		SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());

		final List<IMethod> methods = new LinkedList<IMethod>();
		new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				methods.add((IMethod) match.getElement());
			}
		}, monitor);

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

		final IDLTKSearchScope scope = SearchEngine.createSuperHierarchyScope(type);
		SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());

		final List<PHPDocBlock> docs = new LinkedList<PHPDocBlock>();
		new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				IMethod method = (IMethod) match.getElement();
				PHPDocBlock docBlock = PHPModelUtils.getDocBlock(method);
				if (docBlock != null) {
					docs.add(docBlock);
				}
			}
		}, monitor);

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
		// but declared in different files (determine filtering purpose):
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
	 * Returns the current namespace by the specified file and offset
	 * @param sourceModule The file where current namespace is requested 
	 * @param offset The offset where current namespace is requested
	 * @return namespace element, or <code>null</code> if the scope is global under the specified cursor position
	 */
	public static IType getCurrentNamespace(ISourceModule sourceModule, int offset) {
		try {
			return getCurrentNamespace(sourceModule.getElementAt(offset));
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
}
