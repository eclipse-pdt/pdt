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
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.mixin.PHPDocField;
import org.eclipse.php.internal.core.mixin.PHPMixinBuildVisitor;
import org.eclipse.php.internal.core.mixin.PHPMixinElementInfo;
import org.eclipse.php.internal.core.mixin.PHPMixinModel;
import org.eclipse.php.internal.core.typeinference.DeclarationSearcher.DeclarationType;

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
					IModelElement element =  (IModelElement) ((PHPMixinElementInfo) obj).getObject();
					if (!(element instanceof PHPDocField)) { // skip PHPDoc fields
						return element;
					}
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
	public static IMethod[] getClassMethod(IType type, String name, IProgressMonitor monitor) throws CoreException {
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
	public static PHPDocField[] getClassMethodDoc(IType type, String name, IProgressMonitor monitor) throws CoreException {
		if (name == null) {
			throw new NullPointerException();
		}

		final IDLTKSearchScope scope = SearchEngine.createSuperHierarchyScope(type);
		SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH, PHPLanguageToolkit.getDefault());

		final List<PHPDocField> docs = new LinkedList<PHPDocField>();
		new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				IMethod method = (IMethod) match.getElement();
				IScriptProject scriptProject = method.getScriptProject();
				IModelElement[] methodDoc = PHPMixinModel.getInstance(scriptProject).getMethodDoc(method.getDeclaringType().getElementName(), method.getElementName(), SearchEngine.createSearchScope(scriptProject));
				for (IModelElement doc : methodDoc) {
					docs.add((PHPDocField) doc);
				}
			}
		}, monitor);

		return docs.toArray(new PHPDocField[docs.size()]);
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
	public static IModelElement[] filterElements(ISourceModule sourceModule, IModelElement[] elements) {
		if (elements == null) {
			return null;
		}
		
		// Determine whether givent elements represent the same type and name,
		// but declared in different files (determine filtering purpose):
		int elementType = 0;
		String elementName = null;
		boolean fileNetworkFilter = true;
		for (IModelElement element : elements) {
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
	private static IModelElement[] fileNetworkFilter(ISourceModule sourceModule, IModelElement[] elements) {
		
		if (elements != null && elements.length > 0) {
			List<IModelElement> filteredElements = new LinkedList<IModelElement>();
			
			// If some of elements belong to current file return just it:
			for (IModelElement element : elements) {
				if (sourceModule.equals(element.getOpenable())) {
					filteredElements.add(element);
				}
			}
			if (filteredElements.size() == 0) {
				// Filter by includes network
				ReferenceTree referenceTree = FileNetworkUtility.buildReferencedFilesTree(sourceModule, null);
				for (IModelElement element : elements) {
					if (LanguageModelInitializer.isLanguageModelElement(element) || referenceTree.find(((ModelElement)element).getSourceModule())) {
						filteredElements.add(element);
					}
				}
			}
			if (filteredElements.size() > 0) {
				elements = filteredElements.toArray(new IModelElement[filteredElements.size()]);
			}
		}
		return elements;
	}
}
