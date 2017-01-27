/*******************************************************************************
 * Copyright (c) 2016, 2017 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.facet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.api.ComposerConstants;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectValidator;

public class FacetedProjectValidator implements IFacetedProjectValidator {

	@Override
	public void validate(IFacetedProject fproj) throws CoreException {
		if (!FacetManager.hasComposerFacet(fproj.getProject())) {
			return;
		}
		if (!fproj.getProject().getFile(ComposerConstants.COMPOSER_JSON).exists()) {
			fproj.createErrorMarker(ComposerConstants.PROBLEM_MARKER_TYPE,
					Messages.FacetedProjectValidator_MissingComposerJsonFile);
		}
	}

}
