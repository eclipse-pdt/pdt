package org.eclipse.php.internal.core.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;

public interface IModelAccess {

	/**
	 * Finds methods by name in the class hierarchy
	 * 
	 * @param type
	 *            Type element
	 * @param name
	 *            Element name
	 * @param matchRule
	 *            Search match rule
	 * @param flags
	 *            Filter by flags if greater than 0.
	 * @param monitor
	 *            Progress monitor
	 * @return method element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public IMethod[] findMethodsInTypeHierarchy(IType type, String name,
			MatchRule matchRule, int flags, IProgressMonitor monitor);

	/**
	 * Finds fields by name in the class hierarchy
	 * 
	 * @param type
	 *            Type element
	 * @param name
	 *            Element name
	 * @param matchRule
	 *            Search match rule
	 * @param flags
	 *            Filter by flags if greater than 0.
	 * @param monitor
	 *            Progress monitor
	 * @return method element or <code>null</code> in case it couldn't be found
	 * @throws CoreException
	 */
	public IField[] findFieldsInTypeHierarchy(IType type, String name,
			MatchRule matchRule, int flags, IProgressMonitor monitor);

	/**
	 * Finds all types.
	 * 
	 * @param name
	 *            Element name
	 * @param matchRule
	 *            Search match rule
	 * @param flags
	 *            Filter by flags if greater than 0.
	 * @param scope
	 *            Search scope
	 * @return type element array
	 */
	public IType[] findTypes(String name, MatchRule matchRule, int flags,
			IDLTKSearchScope scope);

	/**
	 * Finds all global functions.
	 * 
	 * @param name
	 *            Element name
	 * @param matchRule
	 *            Search match rule
	 * @param flags
	 *            Filter by flags if greater than 0.
	 * @param scope
	 *            Search scope
	 * @return type element array
	 */
	public IMethod[] findFunctions(String name, MatchRule matchRule, int flags,
			IDLTKSearchScope scope);

	/**
	 * Finds all global fields.
	 * 
	 * @param name
	 *            Element name
	 * @param matchRule
	 *            Search match rule
	 * @param flags
	 *            Filter by flags if greater than 0.
	 * @param scope
	 *            Search scope
	 * @return type element array
	 */
	public IField[] findFields(String name, MatchRule matchRule, int flags,
			IDLTKSearchScope scope);

	/**
	 * Finds all includes to the given file (only the last path segment must be
	 * specified)
	 * 
	 * @param name
	 *            File name
	 * @param scope
	 *            Search scope
	 * @return type element array
	 */
	public IField[] findIncludes(String name, IDLTKSearchScope scope);
}
