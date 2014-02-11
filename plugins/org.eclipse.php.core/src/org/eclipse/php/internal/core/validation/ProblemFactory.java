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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.DefaultProblemFactory;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemFactory;
import org.eclipse.php.internal.core.PHPCoreConstants;

/**
 * @author Dawid zulus Pakula <zulus@w3des.net>
 */
public class ProblemFactory extends DefaultProblemFactory implements
		IProblemFactory {
	@Override
	public IMarker createMarker(IResource resource, IProblem problem)
			throws CoreException {
		IMarker marker = super.createMarker(resource, problem);

		if (problem instanceof ValidationProblem && problem.isTask()) {
			marker.setAttribute(IMarker.PRIORITY,
					((ValidationProblem) problem).getPriority());
		}

		return marker;
	}

	@Override
	protected boolean isValidMarkerType(String markerType) {
		return markerType.startsWith(PHPCoreConstants.PHP_MARKER_PREFIX);
	}

	@Override
	protected String getTaskMarkerType() {
		return PHPCoreConstants.PHP_MARKER_PREFIX;
	}
}
