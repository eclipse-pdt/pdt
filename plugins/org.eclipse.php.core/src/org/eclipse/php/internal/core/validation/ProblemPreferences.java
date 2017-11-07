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
package org.eclipse.php.internal.core.validation;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.core.validation.IProblemPreferences;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

@Singleton
public class ProblemPreferences implements IProblemPreferences {
	@Inject
	protected IPreferencesService preferences;

	protected IScopeContext[] getScopes(IScriptProject project) {
		return new IScopeContext[] { new ProjectScope(project.getProject()), InstanceScope.INSTANCE,
				DefaultScope.INSTANCE };
	}

	protected String getQualifier(IProblemIdentifier identifier) {
		return new StringBuilder(identifier.contributor()).append(PHPCoreConstants.SLASH)
				.append(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID).append(PHPCoreConstants.SLASH)
				.append(identifier.getClass().getName()).append(PHPCoreConstants.SLASH).append(identifier.name())
				.toString();
	}

	@Override
	public boolean isEnabled(IScriptProject project) {
		return preferences.getBoolean(
				new StringBuilder(PHPCoreConstants.PLUGIN_ID).append(PHPCoreConstants.SLASH)
						.append(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID).toString(),
				PHPCoreConstants.ENABLED, true, getScopes(project));
	}

	@Override
	public boolean isEnabled(IScriptProject project, IProblemIdentifier identifier) {
		if (!isEnabled(project)) {
			return false;
		}

		return getSeverity(project, identifier) != ProblemSeverity.IGNORE;
	}

	@Override
	public String getString(IScriptProject project, IProblemIdentifier identifier, String key) {
		return preferences.getString(getQualifier(identifier), key, null, getScopes(project));
	}

	@Override
	public boolean getBoolean(IScriptProject project, IProblemIdentifier identifier, String key) {
		return preferences.getBoolean(getQualifier(identifier), key, false, getScopes(project));
	}

	@Override
	public ProblemSeverity getSeverity(IScriptProject project, IProblemIdentifier identifier) {
		String val = getString(project, identifier, PHPCoreConstants.SEVERITY);
		try {
			if (val != null) {
				return ProblemSeverity.valueOf(val);
			}
		} catch (IllegalArgumentException e) {
			Logger.logException(e);
		}

		return ProblemSeverity.DEFAULT;
	}

	protected Preferences getPreferences(IScriptProject project) {
		try {
			if (preferences.getRootNode().node(ProjectScope.SCOPE).node(project.getProject().getName())
					.node(PHPCoreConstants.PLUGIN_ID).nodeExists(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID)) {
				return preferences.getRootNode().node(ProjectScope.SCOPE).node(project.getProject().getName());
			}
		} catch (BackingStoreException e) {
			Logger.logException(e);
		}
		return preferences.getRootNode().node(InstanceScope.SCOPE);
	}

	public Preferences getProblemPreferences(IScriptProject project, IProblemIdentifier identifier) {
		return getProblemPreferences(getPreferences(project), identifier);
	}

	protected Preferences getProblemPreferences(Preferences root, IProblemIdentifier identifier) {
		return root.node(identifier.contributor()).node(PHPCoreConstants.VALIDATOR_PREFERENCES_NODE_ID)
				.node(identifier.getClass().getName()).node(identifier.name());
	}

	@Override
	public void setSeverity(IScriptProject project, IProblemIdentifier identifier, ProblemSeverity severity) {
		getProblemPreferences(project, identifier).put(PHPCoreConstants.SEVERITY, severity.name());
	}

	@Override
	public void setDefaultSeverity(IProblemIdentifier identifier, ProblemSeverity severity) {
		getProblemPreferences(Platform.getPreferencesService().getRootNode().node(DefaultScope.SCOPE), identifier)
				.put(PHPCoreConstants.SEVERITY, severity.name());
	}

}
