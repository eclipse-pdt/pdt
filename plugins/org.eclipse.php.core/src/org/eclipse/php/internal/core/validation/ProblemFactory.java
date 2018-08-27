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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.dltk.compiler.problem.*;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class ProblemFactory extends DefaultProblemFactory {

	@Override
	public IProblemSeverityTranslator createSeverityTranslator(IScriptProject project) {
		return new SeverityTranslator(project);
	}

	private class SeverityTranslator implements IProblemSeverityTranslator {

		final private Map<IProblemIdentifier, ProblemSeverity> cache = new HashMap<>();
		final private IScopeContext[] contexts;

		public SeverityTranslator(IScriptProject project) {
			this.contexts = PHPCorePlugin.getDefault().getProblemPreferences().getScopeContexts(project.getProject());
		}

		@Override
		public ProblemSeverity getSeverity(IProblemIdentifier problemId, ProblemSeverity defaultServerity) {
			if (problemId == null) {
				return IProblemSeverityTranslator.IDENTITY.getSeverity(problemId, defaultServerity);
			}
			if (cache.containsKey(problemId)) {
				return cache.get(problemId);
			}
			ProblemSeverity severity = PHPCorePlugin.getDefault().getProblemPreferences().getSeverity(problemId,
					contexts);
			if (severity == ProblemSeverity.DEFAULT) {
				severity = IProblemSeverityTranslator.IDENTITY.getSeverity(problemId, defaultServerity);
			}
			cache.put(problemId, severity);

			return severity;
		}

	}

	@Override
	public String getMarkerType(IProblem problem) {
		if (problem.isTask()) {
			return PHPCoreConstants.PHP_MARKER_TYPE;
		}
		return super.getMarkerType(problem);
	}

}