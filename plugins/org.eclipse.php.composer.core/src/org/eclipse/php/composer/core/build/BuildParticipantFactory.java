/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.build;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildParticipantFactory;
import org.eclipse.php.composer.core.facet.FacetManager;

/**
 * This BuildParticipant parses the autoload_namespaces.php files to store the
 * namespace information for providing psr-0 Autoloading information (for
 * example in the PEX-Core Class/Interface wizards.)
 * 
 * Other than this, it does nothing.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class BuildParticipantFactory implements IBuildParticipantFactory {
	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {
		if (!project.getProject().isAccessible()) {
			return null;
		}
		if (!FacetManager.hasComposerFacet(project.getProject())) {
			return null;
		}

		return new ComposerBuildParticipant();
	}

}
