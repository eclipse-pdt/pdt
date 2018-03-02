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
package org.eclipse.php.composer.test;

import org.eclipse.dltk.core.IScriptProject;

public abstract class ComposerModelTests extends AbstractModelTests {

	public ComposerModelTests(String name) {
		super(ComposerCoreTestPlugin.PLUGIN_ID, name);
	}

	protected IScriptProject ensureScriptProject(String name) {
		IScriptProject prj = null;
		try {
			deleteProject(name);
			prj = setUpScriptProject(name);
		} catch (Exception e) {
		}

		return prj;
	}
}
