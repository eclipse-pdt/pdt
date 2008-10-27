package org.eclipse.php.internal.ui.projectoutlineview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.ui.Logger;

public class OutlineUtils {
	private static final IModelElement[] EMPTY = new IModelElement[0];
	private static final String WILDCARD = "*"; //$NON-NLS-1$
	
	
	/**
	 * Retrieves all classes from the global scope by the given prefix.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix
	 * @param exactName
	 * @return
	 */
	public static IType[] getOnlyClasses(IModelElement projectFragment, String prefix, boolean exactName) {
		IModelElement[] classes = getGlobalClasses(projectFragment, prefix, exactName);
		List<IType> onlyClasses = new LinkedList<IType>();
		for (IModelElement c : classes) {
			IType type = (IType) c;
			try {
				if ((type.getFlags() & Modifiers.AccInterface) == 0) {
					onlyClasses.add(type);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG_COMPLETION) {
					e.printStackTrace();
				}
			}
		}
		return onlyClasses.toArray(new IType[onlyClasses.size()]);
	}
	/**
	 * This method searches for all classes in the project scope that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Field name
	 * @param exactName Whether the prefix is an exact name of a class
	 */
	public static IModelElement[] getGlobalClasses(IModelElement projectFragment, String prefix, boolean exactName) {
		return getGlobalElements( projectFragment, prefix, exactName, IDLTKSearchConstants.TYPE);
	}
	
	public static IModelElement[] getGlobalFunctions(IModelElement projectFragment, String prefix, boolean exactName) {
		return getGlobalElements( projectFragment, prefix, exactName, IDLTKSearchConstants.METHOD);
	}
	
	public static IModelElement[] getGlobalConstants(IModelElement projectFragment, String prefix, boolean exactName) {
		ArrayList<IModelElement> constantsElements = new ArrayList<IModelElement>();
		
		IModelElement[] globalElements = getGlobalElements( projectFragment, prefix, exactName, IDLTKSearchConstants.FIELD);
		for (IModelElement modelElement : globalElements) {
			if ((modelElement instanceof IField && modelElement.getParent() instanceof ISourceModule)){
				
				int flags = 0;
				try {
					flags = ((IField)modelElement).getFlags();
				} catch (ModelException e) {
					Logger.logException(e);
				}
				if ((flags & Modifiers.AccConstant) != 0) 
					constantsElements.add(modelElement);
			}
		}
		
		return constantsElements.toArray(new IModelElement[constantsElements.size()]);
	}
	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @return
	 */
	private static IModelElement[] getGlobalElements(IModelElement projectFragment, String prefix, boolean exactName, int elementType) {
		return getGlobalElements( projectFragment, prefix, exactName, elementType, false);
	}
	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @return
	 */
	private static IModelElement[] getGlobalElements(IModelElement projectFragment, String prefix, boolean exactName, int elementType, boolean currentFileOnly) {
		return getGlobalElements( projectFragment, prefix, exactName, elementType, currentFileOnly, false);
	}
	
	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If currentFileOnly parameter is <code>true</code>, the search scope for variables will contain only the source module.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param sourceModule Current source module
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @param currentFileOnly Whether to search elements in current file only
	 * @return
	 */
	private static IModelElement[] getGlobalElements(IModelElement projectFragment, String prefix, boolean exactName, int elementType, boolean currentFileOnly, boolean caseSensitive) {
		
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		
		IDLTKSearchScope scope;
		if (projectFragment != null) {
				scope = SearchEngine.createSearchScope(projectFragment);
			} else {
				scope = SearchEngine.createWorkspaceScope(toolkit);
			}
		
		return getGlobalElements(scope, prefix, exactName, elementType, caseSensitive);
	}
	
	/**
	 * This method searches in the project scope for all elements of specified type that match the given prefix.
	 * If the project doesn't exist, workspace scope is used.
	 * 
	 * @param scope Search scope
	 * @param prefix Element name or prefix
	 * @param exactName Whether the prefix is an exact name of the element
	 * @param elementType Element type from {@link IDLTKSearchConstants}
	 * @return
	 */
	private static IModelElement[] getGlobalElements(IDLTKSearchScope scope, String prefix, boolean exactName, int elementType, boolean caseSensitive) {
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		
		int matchRule;
		if (prefix.length() == 0 && !exactName) {
			prefix = WILDCARD;
			matchRule = SearchPattern.R_PATTERN_MATCH;
			if (caseSensitive) {
				matchRule |= SearchPattern.R_CASE_SENSITIVE;
			}
		} else {
			if (caseSensitive) {
				matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_PREFIX_MATCH;
				matchRule |= SearchPattern.R_CASE_SENSITIVE;
			} else {
				matchRule = exactName ? SearchPattern.R_EXACT_MATCH : SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_PREFIX_MATCH;
			}
		}
		
		SearchEngine searchEngine = new SearchEngine();
		SearchPattern pattern = SearchPattern.createPattern(prefix, elementType, IDLTKSearchConstants.DECLARATIONS, matchRule, toolkit);

		final List<IModelElement> elements = new LinkedList<IModelElement>();
		try {
			searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, new SearchRequestor() {
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					
					IModelElement element = (IModelElement) match.getElement();
					
					// sometimes method reference is found instead of declaration (seems to be a bug in search engine):
					if (element instanceof SourceModule) {
						return;
					}
					
					IModelElement parent = element.getParent();
					
					// Global scope elements in PHP are those, which are not defined in class body,
					// or it is a variable, and its parent - source module
					if ((element instanceof IField && parent instanceof org.eclipse.dltk.core.ISourceModule)
							|| (!(element instanceof IField) && !(parent instanceof IType))) {
						elements.add(element);
					}
				}
			}, null);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG_COMPLETION) {
				e.printStackTrace();
			}
		}
		return elements.toArray(new IModelElement[elements.size()]);
	}
	
}
