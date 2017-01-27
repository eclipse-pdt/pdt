/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.explorer;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.scriptview.BuildPathContainer;
import org.eclipse.php.composer.core.model.ModelAccess;

@SuppressWarnings("restriction")
public class ComposerBuildpathContainer extends BuildPathContainer {
	private IScriptProject iScriptProject;

	public ComposerBuildpathContainer(IScriptProject parent) {
		super(parent, DLTKCore.newContainerEntry(parent.getPath()));
		this.iScriptProject = parent;
	}

	public String getLabel() {
		return Messages.ComposerBuildpathContainer_Label;
	}

	@Override
	public IAdaptable[] getChildren() {
		return ModelAccess.getInstance().getPackageManager().getPackagePaths(iScriptProject);
	}
}