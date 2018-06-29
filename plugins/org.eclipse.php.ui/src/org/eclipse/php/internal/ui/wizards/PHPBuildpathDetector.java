/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.internal.ui.wizards.BuildpathDetector;

/**
 * Buildpath detector for PHP project TODO: make it smarter so folders without
 * any php source will be excluded from the buil dpath
 */
public class PHPBuildpathDetector extends BuildpathDetector {

	public PHPBuildpathDetector(IProject project, IDLTKLanguageToolkit toolkit) throws CoreException {
		super(project, toolkit);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void addInterpreterContainer(ArrayList cpEntries) {
		// do nothing, we don't want to add any interpreter container (we use
		// PHP language container)
	}

}