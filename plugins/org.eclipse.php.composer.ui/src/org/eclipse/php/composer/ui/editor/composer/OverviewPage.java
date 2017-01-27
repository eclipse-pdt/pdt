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

import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.*;

/**
 * @author Thomas Gossmann
 * 
 */
public class OverviewPage extends ComposerFormPage {

	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.OverviewPage"; //$NON-NLS-1$

	protected ComposerFormEditor editor;

	private Composite left;
	private Composite right;

	private GeneralSection generalSection;
	private AuthorSection authorSection;
	private SupportSection supportSection;

	protected IHyperlinkListener linkListener = new HyperlinkAdapter() {
		public void linkActivated(HyperlinkEvent event) {
			String[] chunks = event.getHref().toString().split(":"); //$NON-NLS-1$
			String type = chunks[0];
			String target = chunks[1];
			if (type.equals("page")) { //$NON-NLS-1$
				editor.setActivePage(target);
			} else if (type.equals("view")) { //$NON-NLS-1$
				editor.setActivePage(target);
			}
		}
	};

	/**
	 * @param editor
	 * @param id
	 * @param title
	 */
	public OverviewPage(ComposerFormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor = editor;
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);

		if (active) {
			editor.getHeaderForm().getForm().setText(Messages.OverviewPage_Title);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		generalSection.setEnabled(enabled);
		authorSection.setEnabled(enabled);
		supportSection.setEnabled(enabled);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		TableWrapLayout layout = new TableWrapLayout();
		layout.makeColumnsEqualWidth = true;
		layout.numColumns = 2;
		form.getBody().setLayout(layout);

		left = toolkit.createComposite(form.getBody());
		left.setLayout(new TableWrapLayout());
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		generalSection = new GeneralSection(this, left);
		authorSection = new AuthorSection(this, left);
		supportSection = new SupportSection(this, left);

		right = toolkit.createComposite(form.getBody());
		right.setLayout(new TableWrapLayout());
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		createConfigurationSection(right, toolkit);
		createDependenciesSection(right, toolkit);
		createComposerSection(right, toolkit);
	}

	private void createConfigurationSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setText(Messages.OverviewPage_ConfigurationSectionTitle);

		Composite client = toolkit.createComposite(section);
		section.setClient(client);
		client.setLayout(new TableWrapLayout());

		FormText config = toolkit.createFormText(client, false);
		config.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		config.setText(NLS.bind(Messages.OverviewPage_ConfigurationSectionBody, AutoloadPage.ID, ConfigurationPage.ID),
				true, false);
		config.setImage("page", ComposerUIPluginImages.PAGE.createImage()); //$NON-NLS-1$
		config.addHyperlinkListener(linkListener);
	}

	private void createDependenciesSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setText(Messages.OverviewPage_DependenciesSectionTitle);

		Composite client = toolkit.createComposite(section);
		section.setClient(client);
		client.setLayout(new TableWrapLayout());

		FormText dependencies = toolkit.createFormText(client, false);
		dependencies.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		dependencies.setText(
				NLS.bind(Messages.OverviewPage_DependenciesSectionBody, DependenciesPage.ID, DependencyGraphPage.ID),
				true, false);
		dependencies.setImage("page", ComposerUIPluginImages.PAGE.createImage()); //$NON-NLS-1$
		dependencies.addHyperlinkListener(linkListener);
	}

	private void createComposerSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setText(Messages.OverviewPage_ComposerInfoSectionTitle);

		Composite client = toolkit.createComposite(section);
		section.setClient(client);
		client.setLayout(new TableWrapLayout());

		FormText composer = toolkit.createFormText(client, false);
		composer.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		composer.setText(Messages.OverviewPage_ComposerInfoSectionBody, true, false);
		composer.setImage("url", ComposerUIPluginImages.BROWSER.createImage()); //$NON-NLS-1$
		composer.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Program.launch(e.getHref().toString());
			}
		});
	}

}
