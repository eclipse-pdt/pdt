/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;

/**
 * An <code>IPHPSearchScope</code> defines where search result should be found by a
 * <code>SearchEngine</code>. Clients must pass an instance of this interface
 * to the <code>search(...)</code> methods. Such an instance can be created using the
 * following factory methods on <code>SearchEngine</code>: <code>createHierarchyScope(IType)</code>,
 * <code>createJavaSearchScope(IResource[])</code>, <code>createWorkspaceScope()</code>, or
 * clients may choose to implement this interface.
 */
public interface IPHPSearchScope {

	/**
	 * Returns the paths to all of the enclosing projects for this search scope.
	 * The path is a union of getPartialScopeProjects() with getFullScopeProjects().
	 * <ul>
	 * <li> If the path is a project path, this is the full path of the project
	 *       (see <code>IResource.getFullPath()</code>).
	 *        For example, /MyProject
	 * </li>
	 * </ul>
	 * 
	 * @return an array of paths to the enclosing projects.
	 * @see #getFullScopeProjects()
	 * @see #getPartialScopeProjects()
	 */
	public IProject[] getAllEnclosingProjects();

	/**
	 * Returns the projects that are in the scope of the search because at least one of their
	 * resources was in the selection for the search.
	 * 
	 * @return An array of IProjects that are partially in the search scope.
	 */
	public IProject[] getPartialScopeProjects();

	/**
	 * Returns the projects that are in the scope of the search and the search should be done in
	 * all of their resources. 
	 * 
	 * @return An array of IProjects that are fully in the search scope.
	 */
	public IProject[] getFullScopeProjects();

	/**
	 * Returns true if the given CodeData is in the search scope.
	 * 
	 * @param codeData The CodeData to check for.
	 * @return True, if the CodeData should take part in the search; False, otherwise.
	 */
	public boolean isInScope(CodeData codeData);

	/**
	 * Returns an array of IPaths to the resources that span to other projects.
	 * 
	 * @param project The container project.
	 * @return An array of IPaths.
	 */
	public IPath[] getPartialResourcesPaths(IProject project);

	/**
	 * Returns the scope of the search. 
	 * The returned value is one of <code>IPHPSearchConstants</code> constants. 
	 * 
	 * @return The scope of the search, as selected by the user.
	 * @see IPHPSearchConstants#CLASS
	 * @see IPHPSearchConstants#FUNCTION
	 * @see IPHPSearchConstants#CONSTANT
	 * @see IPHPSearchConstants#VARIABLE
	 */
	public int getSearchFor();
}
