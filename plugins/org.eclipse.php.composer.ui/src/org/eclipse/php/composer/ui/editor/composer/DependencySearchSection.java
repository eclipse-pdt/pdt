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
import org.eclipse.php.composer.ui.editor.ComposerSection;
import org.eclipse.php.composer.ui.parts.composer.DependencySearch;
import org.eclipse.php.composer.ui.parts.composer.DependencySelectionFinishedListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class DependencySearchSection extends ComposerSection {

	private DependencySearch dependencySearch;

	public DependencySearchSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION);
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText("Packagist Search");
		section.setDescription("Search for packages and add the selected packages to the opened section on the left.");
		section.setLayoutData(new GridData(GridData.FILL_BOTH));

		dependencySearch = new DependencySearch(section, composerPackage, toolkit, "Add Dependencies");

		section.setClient(dependencySearch.getBody());
	}

	public void addDependencySelectionFinishedListener(DependencySelectionFinishedListener listener) {
		dependencySearch.addDependencySelectionFinishedListener(listener);
	}

	public void removeDependencySelectionFinishedListener(DependencySelectionFinishedListener listener) {
		dependencySearch.removeDependencySelectionFinishedListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		dependencySearch.setEnabled(enabled);
	}
}
