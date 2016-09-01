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
package org.eclipse.php.composer.ui.editor;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import org.eclipse.php.composer.api.ComposerPackage;


public abstract class ComposerSection extends SectionPart {

	private ComposerFormPage page;
	protected ComposerPackage composerPackage;
	protected boolean enabled = true;
	
	public ComposerSection(ComposerFormPage page, Composite parent, int style) {
		this(page, parent, style, true);
	}
	
	public ComposerSection(ComposerFormPage page, Composite parent, int style, boolean titleBar) {
		super(parent, page.getManagedForm().getToolkit(), titleBar ? (ExpandableComposite.TITLE_BAR | style) : style);
		this.page = page;
		composerPackage = page.getComposerEditor().getComposerPackge();
		initialize(page.getManagedForm());
	}
	
	public ComposerFormPage getPage() {
		return page;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	protected abstract void createClient(Section section, FormToolkit toolkit);
}
