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
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.swt.widgets.Composite;

public class Psr0Section extends PsrSection {

	public Psr0Section(AbstractAutoloadPage page, Composite parent) {
		super(page, parent);
	}

	@Override
	protected Psr getPsr() {
		return getAutoload().getPsr0();
	}

	@Override
	protected String getPsrName() {
		return Messages.Psr0Section_Title;
	}

}
