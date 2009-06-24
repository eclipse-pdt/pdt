/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.language;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IScriptProject;

/**
 * Provides PHP stub files for buiding PHP Language Library.
 * @author michael
 *
 */
public interface ILanguageModelProvider {

	/**
	 * Returns path to directory that contains PHP stubs
	 * used for building PHP Language Library.
	 * 
	 * @param project Script project
	 * @return
	 */
	public IPath getPath(IScriptProject project);
}
