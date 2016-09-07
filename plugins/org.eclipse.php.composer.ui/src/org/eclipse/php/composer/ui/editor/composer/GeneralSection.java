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

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.php.composer.ui.converter.Keywords2StringConverter;
import org.eclipse.php.composer.ui.converter.License2StringConverter;
import org.eclipse.php.composer.ui.converter.String2KeywordsConverter;
import org.eclipse.php.composer.ui.converter.String2LicenseConverter;
import org.eclipse.php.composer.ui.editor.ComboFormEntryAdapter;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.ComposerSection;
import org.eclipse.php.composer.ui.editor.FormEntryAdapter;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.parts.ComboFormEntry;
import org.eclipse.php.composer.ui.parts.FormEntry;
import org.eclipse.php.composer.ui.parts.WeblinkFormEntry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import org.eclipse.php.composer.api.ComposerConstants;

public class GeneralSection extends ComposerSection {

	private FormEntry nameEntry;
	private FormEntry descriptionEntry;
	private FormEntry typeEntry;
	private FormEntry keywordsEntry;
	private FormEntry homepageEntry;
	private FormEntry licenseEntry;
	private ComboFormEntry minimumStabilityEntry;

	public GeneralSection(ComposerFormPage page, Composite parent) {
		super(page, parent, Section.DESCRIPTION);
		createClient(getSection(), page.getManagedForm().getToolkit());
	}

	@Override
	protected void createClient(Section section, FormToolkit toolkit) {
		section.setText("General Information");
		section.setDescription("This section describes general information about this package.");
		section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		Composite client = toolkit.createComposite(section);
		client.setLayout(FormLayoutFactory.createSectionClientTableWrapLayout(false, 2));
		section.setClient(client);

		createNameEntry(client, toolkit);
		createDescriptionEntry(client, toolkit);
		createTypeEntry(client, toolkit);
		createKeywordsEntry(client, toolkit);
		createHomepageEntry(client, toolkit);
		createLicenseEntry(client, toolkit);
		createStabilityEntry(client, toolkit);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);

		nameEntry.setEnabled(enabled);
		descriptionEntry.setEnabled(enabled);
		typeEntry.setEnabled(enabled);
		keywordsEntry.setEnabled(enabled);
		homepageEntry.setEnabled(enabled);
		licenseEntry.setEnabled(enabled);
		minimumStabilityEntry.setEditable(enabled);
	}

	private void createNameEntry(Composite client, FormToolkit toolkit) {
		nameEntry = new FormEntry(client, toolkit, "Name", null, false);
		nameEntry.setValue(composerPackage.getName(), true);

		nameEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				composerPackage.set("name", entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener("name", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				nameEntry.setValue(composerPackage.getName(), true);
			}
		});
	}

	private void createDescriptionEntry(Composite client, FormToolkit toolkit) {
		descriptionEntry = new FormEntry(client, toolkit, "Description", null, false);
		descriptionEntry.setValue(composerPackage.getDescription(), true);

		descriptionEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				composerPackage.set("description", entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener("description", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				descriptionEntry.setValue(composerPackage.getDescription(), true);
			}
		});
	}

	private void createTypeEntry(Composite client, FormToolkit toolkit) {
		typeEntry = new FormEntry(client, toolkit, "Type", null, false);
		typeEntry.setValue(composerPackage.getType(), true);

		ControlDecoration decoration = new ControlDecoration(typeEntry.getText(), SWT.TOP | SWT.LEFT);

		FieldDecoration indicator = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);

		decoration.setImage(indicator.getImage());
		decoration.setDescriptionText(indicator.getDescription() + "(Ctrl+Space)");
		decoration.setShowOnlyOnFocus(true);

		new AutoCompleteField(typeEntry.getText(), new TextContentAdapter(), ComposerConstants.TYPES);

		typeEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				composerPackage.set("type", entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener("type", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				typeEntry.setValue(composerPackage.getType(), true);
			}
		});
	}

	private void createKeywordsEntry(Composite client, FormToolkit toolkit) {
		keywordsEntry = new FormEntry(client, toolkit, "Keywords", null, false);

		final Keywords2StringConverter converter = new Keywords2StringConverter();
		keywordsEntry.setValue(converter.convert(composerPackage.getKeywords()), true);

		keywordsEntry.addFormEntryListener(new FormEntryAdapter() {
			String2KeywordsConverter converter;

			public void focusGained(FormEntry entry) {
				converter = new String2KeywordsConverter(composerPackage);
			}

			public void focusLost(FormEntry entry) {
				converter.convert(entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if (e.getPropertyName().startsWith("keywords")) {
					keywordsEntry.setValue(converter.convert(composerPackage.getKeywords()), true);
				}
			}
		});
	}

	private void createHomepageEntry(Composite client, FormToolkit toolkit) {
		homepageEntry = new WeblinkFormEntry(client, toolkit, "Homepage");
		homepageEntry.setValue(composerPackage.getHomepage());

		homepageEntry.addFormEntryListener(new FormEntryAdapter() {
			public void textValueChanged(FormEntry entry) {
				composerPackage.set("homepage", entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener("homepage", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				homepageEntry.setValue(composerPackage.getHomepage(), true);
			}
		});
	}

	private void createLicenseEntry(Composite client, FormToolkit toolkit) {
		licenseEntry = new FormEntry(client, toolkit, "License", null, false);

		ControlDecoration decoration = new ControlDecoration(licenseEntry.getText(), SWT.TOP | SWT.LEFT);

		FieldDecoration indicator = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);

		decoration.setImage(indicator.getImage());
		decoration.setDescriptionText(indicator.getDescription() + "(Ctrl+Space)");
		decoration.setShowOnlyOnFocus(true);

		new AutoCompleteField(licenseEntry.getText(), new LicenseContentAdapter(), ComposerConstants.LICENSES);

		final License2StringConverter converter = new License2StringConverter();
		licenseEntry.setValue(converter.convert(composerPackage.getLicense()), true);

		licenseEntry.addFormEntryListener(new FormEntryAdapter() {
			String2LicenseConverter converter;

			public void focusGained(FormEntry entry) {
				converter = new String2LicenseConverter(composerPackage);
			}

			public void focusLost(FormEntry entry) {
				converter.convert(entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if (e.getPropertyName().startsWith("license")) {
					licenseEntry.setValue(converter.convert(composerPackage.getLicense()), true);
				}
			}
		});
	}

	private void createStabilityEntry(Composite client, FormToolkit toolkit) {
		minimumStabilityEntry = new ComboFormEntry(client, toolkit, "Minimum Stability", SWT.FLAT | SWT.READ_ONLY);
		minimumStabilityEntry.getComboPart().setItems(ComposerConstants.STABILITIES);
		minimumStabilityEntry.setValue(composerPackage.getMinimumStability(), true);

		minimumStabilityEntry.addComboFormEntryListener(new ComboFormEntryAdapter() {
			public void selectionChanged(ComboFormEntry entry) {
				composerPackage.set("minimum-stability", entry.getValue());
			}
		});
		composerPackage.addPropertyChangeListener("minimum-stability", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				minimumStabilityEntry.setValue(composerPackage.getMinimumStability(), true);
			}
		});
	}
}
