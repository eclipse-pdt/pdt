/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.php.core.validation.IProblemPreferences;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class ProblemPreferences implements IProblemPreferences {
	@Override
	public String getPreferenceQualifier(IProblemIdentifier identifier) {
		return new StringBuilder(identifier.contributor()).append('/')
				.append(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID).append('/')
				.append(identifier.getClass().getName()).append('/').append(identifier.name()).toString();
	}

	@Override
	public IScopeContext[] getScopeContexts(IProject project) {
		if (hasProjectSettings(project)) {
			return new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		}
		return new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
	}

	@Override
	public ProblemSeverity getSeverity(IProblemIdentifier identifier, IScopeContext[] contexts) {
		return ProblemSeverity.valueOf(Platform.getPreferencesService().getString(getPreferenceQualifier(identifier),
				PHPCoreConstants.SEVERITY, ProblemSeverity.DEFAULT.name(), contexts));
	}

	@Override
	public boolean hasProjectSettings(IProject project) {
		return new ProjectScope(project).getNode(PHPCorePlugin.ID).node(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID)
				.getBoolean(PHPCoreConstants.ENABLED, false);
	}

	@Override
	public void setSeverity(IProblemIdentifier identifier, ProblemSeverity severity, IScopeContext context) {
		context.getNode(getPreferenceQualifier(identifier)).put(PHPCoreConstants.SEVERITY, severity.name());
	}
}
