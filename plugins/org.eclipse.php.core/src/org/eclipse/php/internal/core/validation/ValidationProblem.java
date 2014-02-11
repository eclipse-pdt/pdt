/*******************************************************************************
 * Copyright (c) 2014 Dawid Pakuła
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial api and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;

public class ValidationProblem extends DefaultProblem {
	private int priority = 0;

	public ValidationProblem(String message, IProblemIdentifier id,
			String[] stringArguments, ProblemSeverity severity,
			int startPosition, int endPosition, int line) {
		super(message, id, stringArguments, severity, startPosition,
				endPosition, line);
	}

	public ValidationProblem(String message, IProblemIdentifier id,
			int priority, int startPosition, int endPosition, int line) {
		super(message, id, new String[0], ProblemSeverity.DEFAULT,
				startPosition, endPosition, line);
		this.priority = priority;
	}

	@Override
	public boolean isTask() {
		return this.getID() == ProblemIdentifier.TASK;
	}

	@Override
	public String[] getExtraMarkerAttributeNames() {
		if (isTask()) {
			return new String[] { IMarker.PRIORITY };
		}
		return super.getExtraMarkerAttributeNames();
	}

	@Override
	public Object[] getExtraMarkerAttributeValues() {
		if (isTask()) {
			return new String[] { String.valueOf(this.priority) };
		}
		return super.getExtraMarkerAttributeValues();
	}

	public int getPriority() {
		return priority;
	}

}
