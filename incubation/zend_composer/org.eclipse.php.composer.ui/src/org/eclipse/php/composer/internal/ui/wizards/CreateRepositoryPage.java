/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.wizards;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.php.composer.core.PackageVersion;
import org.eclipse.php.composer.core.model.*;
import org.eclipse.php.composer.core.utils.ExtraAttributes;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class CreateRepositoryPage extends WizardPage {

	private final static String[] DIST_TYPES = new String[] { "zip" }; //$NON-NLS-1$
	private final static String[] SOURCE_TYPES = new String[] { "git", "svn" }; //$NON-NLS-1$ //$NON-NLS-2$

	private final static String[] PACKAGE_SOURCES = new String[] { "dist", //$NON-NLS-1$
			"source" }; //$NON-NLS-1$

	private Composite defaultTypeGroup;
	private Composite packageGroup;

	private Text urlText;
	private Combo typeCombo;

	private Text extraParamsText;
	private Text packageNameText;
	private Text packageVersionText;
	private Combo packageSourceCombo;
	private Text packageUrlText;
	private Combo packageTypeCombo;
	private Text packageReferenceText;
	private Group attributesGroup;

	private IRepository repository;
	private IRepositories repositories;

	/**
	 * Create the wizard.
	 * 
	 * @param repository
	 * @param repositories
	 */
	public CreateRepositoryPage(IRepository repository,
			IRepositories repositories) {
		super("defineRepositoryPage"); //$NON-NLS-1$
		setTitle(Messages.CreateRepositoryPage_Title);
		setDescription(Messages.CreateRepositoryPage_Desc);
		this.repository = repository;
		this.repositories = repositories;
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));

		Label typeLabel = new Label(container, SWT.NONE);
		typeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false,
				1, 1));
		typeLabel.setText(Messages.CreateRepositoryPage_Type);

		typeCombo = new Combo(container, SWT.READ_ONLY);
		typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		typeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RepositoryType type = RepositoryType.byName(typeCombo.getText());
				if (type != null) {
					switch (type) {
					case PACKAGE:
						setControlVisibility(defaultTypeGroup, false);
						setControlVisibility(packageGroup, true);
						attributesGroup.layout(true);
						break;
					default:
						setControlVisibility(defaultTypeGroup, true);
						setControlVisibility(packageGroup, false);
						attributesGroup.layout(true);
						break;
					}
				}
				setPageComplete(validatePage());
			}
		});
		RepositoryType[] types = RepositoryType.values();
		for (RepositoryType type : types) {
			typeCombo.add(type.getName());
		}
		typeCombo.select(0);

		attributesGroup = new Group(container, SWT.BORDER);
		attributesGroup.setText(Messages.CreateRepositoryPage_Attributes);
		attributesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		attributesGroup.setLayout(new GridLayout(1, false));

		defaultTypeGroup = createDefaultGroup(attributesGroup);
		packageGroup = createPackageGroup(attributesGroup);

		Label label = new Label(attributesGroup, SWT.WRAP);
		label.setText(Messages.CreateRepositoryPage_ExtraAttributesDesc);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		extraParamsText = new Text(attributesGroup, SWT.MULTI | SWT.BORDER);
		extraParamsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		extraParamsText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				setPageComplete(validatePage());
			}
		});
		initalizeFields();
		attributesGroup.layout(true);
		setPageComplete(false);
	}

	public IRepository getRepository() {
		RepositoryType type = RepositoryType.byName(typeCombo.getText());
		IRepository repo = ModelFactory.createRepository(type);
		switch (repo.getType()) {
		case PACKAGE:
			IRepositoryPackage rp = ModelFactory.createRepositoryPackage();
			rp.setName(packageNameText.getText());
			rp.setVersion(packageVersionText.getText());
			if (packageSourceCombo.getSelectionIndex() == 0) {
				rp.setDist(ModelFactory.createDist(packageUrlText.getText(),
						packageTypeCombo.getText()));
			} else {
				rp.setSource(ModelFactory.createSource(
						packageUrlText.getText(), packageTypeCombo.getText(),
						packageReferenceText.getText()));
			}
			repo.setRepositoryPackage(rp);
			break;
		default:
			repo.setUrl(urlText.getText());
			break;
		}
		String extra = extraParamsText.getText();
		try {
			ExtraAttributes extraObj = ExtraAttributes.toModel(extra);
			Map<String, Object> all = extraObj.any();
			Set<String> keys = all.keySet();
			for (String key : keys) {
				repo.setParameter(key, all.get(key));
			}
		} catch (IOException e) {
			ComposerUIPlugin.logError(e);
		}
		return repo;
	}

	private void initalizeFields() {
		if (repository != null) {
			String[] types = typeCombo.getItems();
			for (int i = 0; i < types.length; i++) {
				if (RepositoryType.byName(types[i]) == repository.getType()) {
					typeCombo.select(i);
					switch (repository.getType()) {
					case PACKAGE:
						setControlVisibility(defaultTypeGroup, false);
						IRepositoryPackage rp = repository
								.getRepositoryPackage();
						if (rp != null) {
							packageNameText.setText(rp.getName());
							packageVersionText.setText(rp.getVersion());
							if (rp.getDist() != null) {
								IDist dist = rp.getDist();
								packageSourceCombo.select(0);
								packageUrlText.setText(dist.getUrl());
								packageTypeCombo.setText(dist.getType());
							} else if (rp.getSource() != null) {
								ISource source = rp.getSource();
								packageSourceCombo.select(1);
								packageUrlText.setText(source.getUrl());
								packageTypeCombo.setText(source.getType());
								packageReferenceText.setText(source
										.getReference());
							}
						}
						break;
					default:
						setControlVisibility(packageGroup, false);
						if (repository.getUrl() != null) {
							urlText.setText(repository.getUrl());
						}
						break;
					}
					break;
				}
			}
			ExtraAttributes extraObj = new ExtraAttributes();
			Map<String, Object> parameters = repository.getParameters();
			if (parameters != null && parameters.size() > 0) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {
					extraObj.set(key, parameters.get(key));
				}
			}
			try {
				extraParamsText.setText(extraObj.toList());
			} catch (IOException e) {
				setErrorMessage(Messages.CreateRepositoryPage_CannotParseError);
				ComposerUIPlugin.logError(e);
			}
		} else {
			setControlVisibility(packageGroup, false);
		}
		setMessage(Messages.CreateRepositoryPage_Desc);
		setErrorMessage(null);
		setPageComplete(false);
	}

	private boolean validatePage() {
		setErrorMessage(null);
		switch (RepositoryType.byName(typeCombo.getText())) {
		case PACKAGE:
			if (packageNameText != null) {
				String packageName = packageNameText.getText();
				if (packageName.isEmpty()) {
					setMessage(Messages.CreateRepositoryPage_SpecifyName);
					return false;
				}
			}
			if (packageVersionText != null) {
				String version = packageVersionText.getText();
				if (version.isEmpty()) {
					setMessage(Messages.CreateRepositoryPage_SpecifyVersion);
					return false;
				}
				PackageVersion v = PackageVersion.byName(version);
				if (v == PackageVersion.UNKNOWN
						|| (v.getMajor() < 0 || v.getMinor() < 0
								|| v.getBuild() < 0 || v.getRevision() < 0)) {
					setErrorMessage(Messages.CreateRepositoryPage_MissingVersion);
					return false;
				}
			}
			String urlString = packageUrlText.getText();
			if (urlString.isEmpty()) {
				setMessage(Messages.CreateRepositoryPage_SpecifyUrl);
				return false;
			} else {
				try {
					new URL(urlString);
				} catch (MalformedURLException e) {
					setErrorMessage(Messages.CreateRepositoryPage_InvalidUrl);
					return false;
				}
			}
			if (packageSourceCombo.getSelectionIndex() == 1) {
				if (packageReferenceText != null) {
					if (packageReferenceText.getText().isEmpty()) {
						setMessage(Messages.CreateRepositoryPage_SpecifyReference);
						return false;
					}
				}
			}
			break;
		default:
			urlString = urlText.getText();
			if (urlString.isEmpty()) {
				setMessage(Messages.CreateRepositoryPage_MissingURLError);
				return false;
			} else {
				try {
					new URL(urlString);
				} catch (MalformedURLException e) {
					setErrorMessage(Messages.CreateRepositoryPage_InvalidURLError);
					return false;
				}
			}
			break;
		}
		if (!extraParamsText.getText().isEmpty()) {
			String extra = extraParamsText.getText();
			try {
				ExtraAttributes.toModel(extra);
			} catch (IOException e) {
				setErrorMessage(Messages.CreateRepositoryPage_InvalidExtraError);
				return false;
			}
		}
		if (repository == null) {
			IRepository repo = getRepository();
			if (repositories.getRepositories().contains(repo)) {
				setErrorMessage(Messages.CreateRepositoryPage_ConflictRepository);
				return false;
			}
		}
		setErrorMessage(null);
		setMessage(Messages.CreateRepositoryPage_Desc);
		return true;
	}

	private Composite createDefaultGroup(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1,
				1));
		urlText = createTextField(Messages.CreateRepositoryPage_Url, container);

		return container;
	}

	private Composite createPackageGroup(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1,
				1));

		packageNameText = createTextField(
				Messages.CreateRepositoryPage_PackageName, container);
		packageVersionText = createTextField(
				Messages.CreateRepositoryPage_PackageVersion, container);
		packageSourceCombo = createComboField(
				Messages.CreateRepositoryPage_PackageSource, container, false);
		fillCombo(packageSourceCombo, PACKAGE_SOURCES);
		packageSourceCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = packageSourceCombo.getSelectionIndex();
				switch (index) {
				case 0:
					fillCombo(packageTypeCombo, DIST_TYPES);
					packageReferenceText.setEnabled(false);
					break;
				case 1:
					fillCombo(packageTypeCombo, SOURCE_TYPES);
					packageReferenceText.setEnabled(true);
					break;
				default:
					break;
				}
			}
		});
		packageUrlText = createTextField(
				Messages.CreateRepositoryPage_PackageUrl, container);
		packageTypeCombo = createComboField(
				Messages.CreateRepositoryPage_PackageType, container, true);
		fillCombo(packageTypeCombo, DIST_TYPES);
		packageReferenceText = createTextField(
				Messages.CreateRepositoryPage_PackageReference, container);
		packageReferenceText.setEnabled(false);
		return container;
	}

	private void fillCombo(Combo combo, String[] elements) {
		combo.removeAll();
		for (String element : elements) {
			combo.add(element);
		}
		combo.select(0);
	}

	private Text createTextField(String label, Composite container) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1,
				1));
		lbl.setText(label);

		Text text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent event) {
				setPageComplete(validatePage());
			}
		});
		return text;
	}

	private Combo createComboField(String label, Composite container,
			boolean editable) {
		Label lbl = new Label(container, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1,
				1));
		lbl.setText(label);

		int style = SWT.BORDER;
		if (!editable) {
			style |= SWT.READ_ONLY;
		}
		Combo combo = new Combo(container, style);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		combo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent event) {
				setPageComplete(validatePage());
			}
		});
		return combo;
	}

	private void setControlVisibility(Control control, boolean show) {
		control.setVisible(show);
		GridData data = (GridData) control.getLayoutData();
		if (data == null) {
			data = new GridData();
			control.setLayoutData(data);
		}
		data.exclude = !show;
	}

}
