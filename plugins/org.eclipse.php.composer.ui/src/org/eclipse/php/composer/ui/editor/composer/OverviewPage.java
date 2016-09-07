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
/**
 * 
 */
package org.eclipse.php.composer.ui.editor.composer;

import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * @author Thomas Gossmann
 * 
 */
public class OverviewPage extends ComposerFormPage {

	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.OverviewPage";

	protected ComposerFormEditor editor;

	private Composite left;
	private Composite right;

	private GeneralSection generalSection;
	private AuthorSection authorSection;
	private SupportSection supportSection;

	protected IHyperlinkListener linkListener = new HyperlinkAdapter() {
		public void linkActivated(HyperlinkEvent event) {
			String[] chunks = event.getHref().toString().split(":");
			String type = chunks[0];
			String target = chunks[1];
			if (type.equals("page")) {
				editor.setActivePage(target);
			} else if (type.equals("view")) {
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
			editor.getHeaderForm().getForm().setText("Overview");
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
		section.setText("Configuration");

		Composite client = toolkit.createComposite(section);
		section.setClient(client);
		client.setLayout(new TableWrapLayout());

		FormText config = toolkit.createFormText(client, false);
		config.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		config.setText(
				"<form>\n<p>Configure your package:" + "</p>\n\n<li style=\"image\" value=\"page\"><a href=\"page:"
						+ AutoloadPage.ID + "\">Autoload</a>: manage psr-0, classmap and files "
						+ "this package may have.</li>\n<li style=\"image\" value=\"page\">" + "<a href=\"page:"
						+ ConfigurationPage.ID + "\">Configuration</a>: "
						+ "Configs, Scripts and Repositories can be set here.</li>\n</form>",
				true, false);
		config.setImage("page", ComposerUIPluginImages.PAGE.createImage());
		config.addHyperlinkListener(linkListener);
	}

	private void createDependenciesSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setText("Dependencies");

		Composite client = toolkit.createComposite(section);
		section.setClient(client);
		client.setLayout(new TableWrapLayout());

		FormText dependencies = toolkit.createFormText(client, false);
		dependencies.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		dependencies.setText("<form>\n<p>This packages dependencies are made up in two pages:"
				+ "</p>\n\n<li style=\"image\" value=\"page\"><a href=\"page:" + DependenciesPage.ID
				+ "\">Dependencies</a>: declares the dependencies "
				+ "this package may have.</li>\n<li style=\"image\" value=\"page\">" + "<a href=\"view:"
				+ DependencyGraphPage.ID + "\">Dependency Graph</a>: "
				+ "shows the dependencies in a nice graph.</li>\n</form>", true, false);
		dependencies.setImage("page", ComposerUIPluginImages.PAGE.createImage());
		dependencies.addHyperlinkListener(linkListener);
	}

	private void createComposerSection(Composite parent, FormToolkit toolkit) {
		Section section = toolkit.createSection(parent, Section.EXPANDED | Section.TITLE_BAR);
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		section.setText("Composer Information");

		Composite client = toolkit.createComposite(section);
		section.setClient(client);
		client.setLayout(new TableWrapLayout());

		FormText composer = toolkit.createFormText(client, false);
		composer.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		composer.setText(
				"<form>\n<p>Composer is a dependency manager for php.</p>\n\n<li "
						+ "style=\"image\" value=\"url\"><a href=\"http://getcomposer.org\">Composer</a>: "
						+ "Composer Homepage</li>\n<li style=\"image\" value=\"url\">"
						+ "<a href=\"http://getcomposer.org/doc/00-intro.md\">Getting Started</a> "
						+ "with Composer</li>\n<li style=\"image\" value=\"url\">"
						+ "<a href=\"http://getcomposer.org/doc/\">Documentation</a></li>\n"
						+ "<li style=\"image\" value=\"url\">"
						+ "<a href=\"http://getcomposer.org/doc/04-schema.md\">Schema Reference</a></li>\n"
						+ "<li style=\"image\" value=\"url\">"
						+ "<a href=\"http://github.com/composer/composer/issues\">Issues</a>: "
						+ "Report Issues</li>\n<li style=\"image\" value=\"url\">"
						+ "<a href=\"http://packagist.org\">Packagist</a>: " + "Browse Packages</li>\n</form>",
				true, false);
		composer.setImage("url", ComposerUIPluginImages.BROWSER.createImage());
		composer.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Program.launch(e.getHref().toString());
			}
		});
	}

}
