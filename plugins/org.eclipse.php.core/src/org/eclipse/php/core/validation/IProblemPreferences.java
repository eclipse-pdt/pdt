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

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.IScriptProject;

public interface IProblemPreferences {
	public ProblemSeverity getSeverity(IScriptProject project, IProblemIdentifier identifier);

	public String getString(IScriptProject project, IProblemIdentifier identifier, String key);

	public boolean getBoolean(IScriptProject project, IProblemIdentifier identifier, String key);

	public boolean isEnabled(IScriptProject project);

	public boolean isEnabled(IScriptProject project, IProblemIdentifier identifier);

	public void setSeverity(IScriptProject project, IProblemIdentifier identifier, ProblemSeverity severity);

	public void setDefaultSeverity(IProblemIdentifier identifier, ProblemSeverity severity);
}
