/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial api and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.core.resources.IFile;
import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.swt.widgets.Composite;

public class FilesSection extends AutoloadArraySection {

	public FilesSection(AbstractAutoloadPage page, Composite parent) {
		super(page, parent);
	}

	@Override
	protected JsonArray getList() {
		return getAutoload().getFiles();
	}

	@Override
	String getName() {
		return "Files";
	}

	@Override
	String getDescription() {
		return "Files";
	}

	@Override
	protected Class[] getFilter() {
		return new Class[] { IFile.class };
	}

}
