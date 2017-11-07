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
		private final IScriptProject project;

		public SeverityTranslator(IScriptProject project) {
			this.project = project;
		}

		@Override
		public ProblemSeverity getSeverity(IProblemIdentifier problemId, ProblemSeverity defaultServerity) {
			ProblemSeverity severity = PHPCorePlugin.getDefault().getProblemPreferences().getSeverity(project,
					problemId);
			if (severity == ProblemSeverity.DEFAULT) {
				return IProblemSeverityTranslator.IDENTITY.getSeverity(problemId, defaultServerity);
			}

			return severity;
		}

	}

}