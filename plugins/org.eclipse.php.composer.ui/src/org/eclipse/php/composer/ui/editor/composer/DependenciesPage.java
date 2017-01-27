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
/**
 * 
 */
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.Dependencies;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.parts.composer.DependencySelectionFinishedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * @author Thomas Gossmann
 * 
 */
public class DependenciesPage extends ComposerFormPage {

	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.DependencyPage"; //$NON-NLS-1$

	private ComposerPackage composerPackage;
	protected ComposerFormEditor editor;

	protected Composite left;
	protected Composite right;
	protected DependencySection activeSection;

	protected DependencySection requireSection = null;
	protected TableViewer requireView;
	protected Button requireEdit;
	protected Button requireRemove;

	protected DependencySection requireDevSection;
	protected TableViewer requireDevView;
	protected Button requireDevEdit;
	protected Button requireDevRemove;

	protected DependencySearchSection searchSection;

	/**
	 * @param editor
	 * @param id
	 * @param title
	 */
	public DependenciesPage(ComposerFormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor = editor;
		composerPackage = editor.getComposerPackge();
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		if (active) {
			editor.getHeaderForm().getForm().setText(Messages.DependenciesPage_Title);
		}
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		form.getBody().setLayout(FormLayoutFactory.createFormGridLayout(true, 2));

		left = toolkit.createComposite(form.getBody(), SWT.NONE);
		left.setLayout(FormLayoutFactory.createFormPaneGridLayout(false, 1));
		left.setLayoutData(new GridData(GridData.FILL_BOTH));

		requireSection = new DependencySection(this, left, composerPackage.getRequire(),
				Messages.DependenciesPage_RequireSectionTitle, Messages.DependenciesPage_RequireSectionDescription,
				true);
		requireDevSection = new DependencySection(this, left, composerPackage.getRequireDev(),
				Messages.DependenciesPage_RequireDevSectionTitle,
				Messages.DependenciesPage_RequireDevSectionDescription, false);

		requireSection.setEnabled(enabled);
		requireSection.getSection().addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanging(ExpansionEvent e) {
				activeSection = e.getState() ? requireSection : requireDevSection;
				requireDevSection.getSection().setExpanded(!e.getState());
				((GridData) requireSection.getSection().getLayoutData()).grabExcessVerticalSpace = e.getState();
				((GridData) requireDevSection.getSection().getLayoutData()).grabExcessVerticalSpace = !e.getState();
			}
		});

		requireDevSection.setEnabled(enabled);
		requireDevSection.getSection().addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanging(ExpansionEvent e) {
				activeSection = e.getState() ? requireDevSection : requireSection;
				requireSection.getSection().setExpanded(!e.getState());
				((GridData) requireDevSection.getSection().getLayoutData()).grabExcessVerticalSpace = e.getState();
				((GridData) requireSection.getSection().getLayoutData()).grabExcessVerticalSpace = !e.getState();
			}
		});
		activeSection = requireSection;

		right = toolkit.createComposite(form.getBody(), SWT.NONE);
		right.setLayout(FormLayoutFactory.createFormPaneGridLayout(false, 1));
		right.setLayoutData(new GridData(GridData.FILL_BOTH));

		searchSection = new DependencySearchSection(this, right);
		searchSection.setEnabled(enabled);
		searchSection.addDependencySelectionFinishedListener(new DependencySelectionFinishedListener() {
			public void dependenciesSelected(Dependencies dependencies) {
				Dependencies deps = activeSection == requireSection ? composerPackage.getRequire()
						: composerPackage.getRequireDev();

				deps.addAll(dependencies);
				activeSection.setFocus();
			}
		});

	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		if (requireSection != null) {
			requireSection.setEnabled(enabled);
			requireDevSection.setEnabled(enabled);
			searchSection.setEnabled(enabled);
		}
	}
}
