/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.build;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.core.builder.IBuildParticipantFactory;
import org.eclipse.php.composer.core.ComposerNature;

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
		if (project.getProject().hasNature(ComposerNature.NATURE_ID) == false) {
			return null;
		}

		return new ComposerBuildParticipant();
	}

}
