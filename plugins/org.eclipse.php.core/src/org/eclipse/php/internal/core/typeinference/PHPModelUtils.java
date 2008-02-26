package org.eclipse.php.internal.core.typeinference;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.DeclarationSearcher.DeclarationType;

public class PHPModelUtils {

	/**
	 * Finds method by name in the class hierarchy
	 * @param type Class element
	 * @param name Method name
	 * @param monitor Progress monitor
	 * @return method element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public static IMethod getClassMethod(IType type, String name, IProgressMonitor monitor) throws CoreException {
		if (name == null) {
			throw new NullPointerException();
		}

		LinkedList<IType> superTypesQ = new LinkedList<IType>();
		superTypesQ.add(type);

		while (!superTypesQ.isEmpty()) {
			IType superType = superTypesQ.removeFirst();
			IMethod method = superType.getMethod(name);
			if (method.exists()) {
				return method;
			}

			String[] superClassNames = superType.getSuperClasses();
			IDLTKSearchScope searchScope = SearchEngine.createSearchScope(new IModelElement[] { type.getScriptProject() });

			for (String superClassName : superClassNames) {

				SearchPattern pattern = SearchPattern.createPattern(superClassName, IDLTKSearchConstants.TYPE, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
				final List<IType> types = new LinkedList<IType>();

				SearchEngine searchEngine = new SearchEngine();
				searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, searchScope, new SearchRequestor() {
					public void acceptSearchMatch(SearchMatch match) throws CoreException {
						types.add((IType) match.getElement());
					}
				}, monitor);

				superTypesQ.addAll(types);
			}
		}
		return null;

//		ITypeHierarchy supertypeHierarchy = type.newSupertypeHierarchy(monitor);
//		IDLTKSearchScope scope = SearchEngine.createSearchScope(supertypeHierarchy.getAllClasses());
//		SearchPattern pattern = SearchPattern.createPattern(name, IDLTKSearchConstants.METHOD, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
//
//		final List<IMethod> methods = new LinkedList<IMethod>();
//		new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
//			public void acceptSearchMatch(SearchMatch match) throws CoreException {
//				methods.add((IMethod) match.getElement());
//			}
//		}, monitor);
//
//		return methods.isEmpty() ? null : methods.get(0);
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
