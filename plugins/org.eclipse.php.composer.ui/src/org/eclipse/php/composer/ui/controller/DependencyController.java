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
package org.eclipse.php.composer.ui.controller;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.composer.api.MinimalPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;

public class DependencyController extends PackageController {

	private Dependencies deps;

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		deps = (Dependencies) newInput;
	}

	public Object[] getElements(Object inputElement) {
		return deps.toArray();
	}

	public void updateText(MinimalPackage pkg, StyledString styledString) {
		if (pkg instanceof VersionedPackage) {
			VersionedPackage vpkg = (VersionedPackage) pkg;

			super.updateText(pkg, styledString);

			if (vpkg.getVersion() != null && !vpkg.getVersion().trim().isEmpty()) {
				styledString.append(" : " + vpkg.getVersion().trim(), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			}
		}
	}
}