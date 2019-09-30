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

import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.swt.widgets.Composite;

public class ClassMapSection extends AutoloadArraySection {

	public ClassMapSection(AbstractAutoloadPage page, Composite parent) {
		super(page, parent);
	}

	@Override
	protected JsonArray getList() {
		return getAutoload().getClassMap();
	}

	@Override
	String getName() {
		return "Classmap";
	}

	@Override
	String getDescription() {
		return "";
	}

}
