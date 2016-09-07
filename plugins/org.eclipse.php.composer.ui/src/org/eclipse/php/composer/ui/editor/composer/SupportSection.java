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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.ComposerSection;
import org.eclipse.php.composer.ui.editor.FormEntryAdapter;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.parts.FormEntry;
import org.eclipse.php.composer.ui.parts.WeblinkFormEntry;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import org.eclipse.php.composer.api.objects.Support;

public class SupportSection extends ComposerSection {

	Support support;

	private FormEntry emailEntry;
	private FormEntry issuesEntry;
	private FormEntry forumEntry;
	private FormEntry wikiEntry;
	private FormEntry ircEntry;
	private FormEntry sourceEntry;

	public SupportSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION);
		support = composerPackage.getSupport();
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText("Support");
		section.setDescription("Provide support options to your end-users.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		Composite client = toolkit.createComposite(section);
		client.setLayout(FormLayoutFactory.createSectionClientTableWrapLayout(false, 2));
		section.setClient(client);

		createEmailEntry(client, toolkit);
		createIssuesEntry(client, toolkit);
		createForumEntry(client, toolkit);
		createWikiEntry(client, toolkit);
		createIrcEntry(client, toolkit);
		createSourceEntry(client, toolkit);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		emailEntry.setEnabled(enabled);
		issuesEntry.setEnabled(enabled);
		forumEntry.setEnabled(enabled);
		wikiEntry.setEnabled(enabled);
		ircEntry.setEnabled(enabled);
		sourceEntry.setEnabled(enabled);
	}

	private void createEmailEntry(Composite client, FormToolkit toolkit) {
		emailEntry = new FormEntry(client, toolkit, "Email", null, false);
		emailEntry.setValue(support.getEmail(), true);

		emailEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				support.setEmail(entry.getValue());
			}
		});
		support.addPropertyChangeListener("email", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				emailEntry.setValue(support.getEmail(), true);
			}
		});
	}

	private void createIssuesEntry(Composite client, FormToolkit toolkit) {
		issuesEntry = new WeblinkFormEntry(client, toolkit, "Issues");
		issuesEntry.setValue(support.getIssues());

		issuesEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				support.setIssues(entry.getValue());
			}
		});
		support.addPropertyChangeListener("issues", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				issuesEntry.setValue(support.getIssues(), true);
			}
		});
	}

	private void createForumEntry(Composite client, FormToolkit toolkit) {
		forumEntry = new WeblinkFormEntry(client, toolkit, "Forum");
		forumEntry.setValue(support.getForum());

		forumEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				support.setForum(entry.getValue());
			}
		});
		support.addPropertyChangeListener("forum", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				forumEntry.setValue(support.getForum(), true);
			}
		});
	}

	private void createWikiEntry(Composite client, FormToolkit toolkit) {
		wikiEntry = new WeblinkFormEntry(client, toolkit, "Wiki");
		wikiEntry.setValue(support.getWiki());

		wikiEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				support.setWiki(entry.getValue());
			}
		});
		support.addPropertyChangeListener("wiki", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				wikiEntry.setValue(support.getWiki(), true);
			}
		});
	}

	private void createIrcEntry(Composite client, FormToolkit toolkit) {
		ircEntry = new WeblinkFormEntry(client, toolkit, "Irc");
		ircEntry.setValue(support.getIrc());

		ircEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				support.setIrc(entry.getValue());
			}
		});
		support.addPropertyChangeListener("irc", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				ircEntry.setValue(support.getIrc(), true);
			}
		});
	}

	private void createSourceEntry(Composite client, FormToolkit toolkit) {
		sourceEntry = new WeblinkFormEntry(client, toolkit, "Source");
		sourceEntry.setValue(support.getSource());

		sourceEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				support.setSource(entry.getValue());
			}
		});
		support.addPropertyChangeListener("source", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				sourceEntry.setValue(support.getSource(), true);
			}
		});
	}
}
