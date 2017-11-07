/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;

public interface IProblemPreferences {
	/**
	 * Calculate preference qualifier for {@link IScopeContext#getNode(String)} and
	 * {@link IPreferencesService#getString(String, String, String, IScopeContext[])}
	 * 
	 * @param identifier
	 * @return
	 */
	public String getPreferenceQualifier(IProblemIdentifier identifier);

	/**
	 * Calculate preference scopes based on project settings
	 * 
	 * @param project
	 * @return
	 */
	public IScopeContext[] getScopeContexts(IProject project);

	public ProblemSeverity getSeverity(IProblemIdentifier identifier, IScopeContext[] contexts);

	public void setSeverity(IProblemIdentifier identifier, ProblemSeverity severity, IScopeContext context);

	public boolean hasProjectSettings(IProject project);

}
