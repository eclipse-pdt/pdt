/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;

public class PackagePath implements IAdaptable {
	private IBuildpathEntry entry;

	private String name;

	private IScriptProject scriptProject;

	public PackagePath(IBuildpathEntry entry, IScriptProject scriptProject) {
		this.entry = entry;
		this.scriptProject = scriptProject;
		IPath path = entry.getPath();
		int num = path.segmentCount() - 1;
		StringBuilder builder = new StringBuilder();
		builder.append(path.segment(num - 2));
		builder.append("/"); //$NON-NLS-1$
		builder.append(path.segment(num - 1));
		builder.append(" ("); //$NON-NLS-1$
		builder.append(path.segment(num));
		builder.append(")"); //$NON-NLS-1$
		name = builder.toString();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class adapter) {
		return null;
	}

	public String getPackageName() {
		return name;
	}

	public IBuildpathEntry getEntry() {
		return entry;
	}

	public IScriptProject getProject() {
		return scriptProject;
	}
}
