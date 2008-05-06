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
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchMatch;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.php.internal.core.Logger;
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
			IMixinElement[] elements = PHPMixinModel.getRawInstance().find(key);
			if (elements.length > 0) {
				Object[] allObjects = elements[0].getAllObjects();
				if (allObjects.length > 0) {
					return (IModelElement) ((PHPMixinElementInfo)allObjects[0]).getObject();
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

		IDLTKSearchScope scope = SearchEngine.createHierarchyScope(type);
		SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);

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

		IDLTKSearchScope scope = SearchEngine.createHierarchyScope(type);
		SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);

		final List<PHPDocField> docs = new LinkedList<PHPDocField>();
		new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				IMethod method = (IMethod) match.getElement();
				IModelElement[] methodDoc = PHPMixinModel.getInstance().getMethodDoc(method.getDeclaringType().getElementName(), method.getElementName());
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
			Logger.logException(e);
		}
		return (MethodDeclaration) visitor.getResult();
	}

	public static TypeDeclaration getNodeByClass(ModuleDeclaration rootNode, IType type) throws ModelException {
		DeclarationSearcher visitor = new DeclarationSearcher(rootNode, type, DeclarationType.CLASS);
		try {
			rootNode.traverse(visitor);
		} catch (Exception e) {
			Logger.logException(e);
		}
		return (TypeDeclaration) visitor.getResult();
	};

}
