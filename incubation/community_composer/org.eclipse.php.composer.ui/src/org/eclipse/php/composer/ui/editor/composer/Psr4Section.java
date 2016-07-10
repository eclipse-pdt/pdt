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
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.php.composer.api.collection.Psr;

public class Psr4Section extends PsrSection {

	public Psr4Section(ComposerFormPage page, Composite parent) {
		super(page, parent);
	}

	@Override
	protected Psr getPsr() {
		return composerPackage.getAutoload().getPsr4();
	}

	@Override
	protected String getPsrName() {
		return "psr-4";
	}

}
