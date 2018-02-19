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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.dltk.compiler.problem.DefaultProblemFactory;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemSeverityTranslator;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class ProblemFactory extends DefaultProblemFactory {

	@Override
	public IProblemSeverityTranslator createSeverityTranslator(IScriptProject project) {
		return new SeverityTranslator(project);
	}

	private class SeverityTranslator implements IProblemSeverityTranslator {

		final private Map<IProblemIdentifier, ProblemSeverity> cache = new HashMap<IProblemIdentifier, ProblemSeverity>();
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

}