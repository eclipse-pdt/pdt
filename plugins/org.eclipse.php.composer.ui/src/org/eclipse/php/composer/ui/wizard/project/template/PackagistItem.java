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
package org.eclipse.php.composer.ui.wizard.project.template;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.equinox.internal.p2.ui.discovery.wizards.AbstractDiscoveryItem;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.DiscoveryResources;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.MinimalPackage;
import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.collection.Versions;
import org.eclipse.php.composer.api.entities.JsonValue;
import org.eclipse.php.composer.api.packages.AsyncPackagistDownloader;
import org.eclipse.php.composer.api.packages.PackageListenerInterface;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

/**
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class PackagistItem extends AbstractDiscoveryItem<PackageFilterItem> {

	private MinimalPackage item;
	private Label nameLabel;
	private Label description;
	private Button downloadButton;
	private final PackageFilterItem filterItem;
	private Button favorButton;
	private Combo versionCombo;
	private boolean isLoadingVersions = false;
	private List<PackageFilterChangedListener> listeners = new ArrayList<PackageFilterChangedListener>();

	public PackagistItem(Composite parent, int style, DiscoveryResources resources, PackageFilterItem element) {
		super(parent, style, resources, element);
		this.filterItem = element;
		this.item = element.getPackage();
		createContent();
	}

	@Override
	protected void refresh() {

	}

	private void createContent() {

		GridLayout layout = new GridLayout(3, false);
		layout.marginLeft = 7;
		layout.marginTop = 2;
		layout.marginBottom = 2;
		setLayout(layout);

		nameLabel = new Label(this, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).align(SWT.BEGINNING, SWT.CENTER).applyTo(nameLabel);
		nameLabel.setFont(resources.getSmallHeaderFont());
		nameLabel.setText(item.getName());

		description = new Label(this, SWT.NULL | SWT.WRAP);

		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				/* .indent(45, 0).hint(100, SWT.DEFAULT) */.applyTo(description);
		String descriptionText = item.getDescription();
		int maxDescriptionLength = 162;
		if (descriptionText == null) {
			descriptionText = ""; //$NON-NLS-1$
		}
		if (descriptionText.length() > maxDescriptionLength) {
			descriptionText = descriptionText.substring(0, maxDescriptionLength);
		}
		description.setText(descriptionText.replaceAll("(\\r\\n)|\\n|\\r", " ")); //$NON-NLS-1$ //$NON-NLS-2$

		createStatsPart();
		initializeListeners();
		initState();
	}

	protected void initState() {

		if (filterItem.isChecked()) {
			setBackground(resources.getGradientEndColor());
			if (filterItem.getVersions() != null) {
				loadVersionsFromCache();
			} else {
				loadVersionCombo();
			}
		} else {
			versionCombo.setVisible(false);
		}
	}

	protected void createStatsPart() {

		favorButton = new Button(this, SWT.PUSH);
		favorButton.setToolTipText("Favorites on packagist.org");
		favorButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		favorButton.setImage(ComposerUIPluginImages.STAR.createImage());

		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).span(1, 2).applyTo(favorButton);

		downloadButton = new Button(this, SWT.TOGGLE);
		downloadButton.setToolTipText("Select this package for your new project.");

		if (filterItem.isChecked()) {
			downloadButton.setSelection(true);
		}

		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER).span(1, 2).applyTo(downloadButton);
		JsonValue downloads = item.get("downloads");
		JsonValue favorites = item.get("favers");
		if (downloads != null && favorites != null) {
			downloadButton.setText(downloads.getAsString());
			downloadButton.setImage(ComposerUIPluginImages.DOWNLOAD.createImage());
			favorButton.setText(favorites.getAsString());
		}

		versionCombo = new Combo(this, SWT.READ_ONLY);

		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).span(1, 2).hint(200, SWT.DEFAULT)
				.applyTo(versionCombo);
	}

	protected void initializeListeners() {

		downloadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean selected = downloadButton.getSelection();
				filterItem.setChecked(selected);

				for (PackageFilterChangedListener listener : listeners) {
					listener.filterChanged(filterItem);
				}
			}
		});

		favorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					final IWebBrowser browser = PlatformUI.getWorkbench().getBrowserSupport().createBrowser(null);
					browser.openURL(new URL(item.getAsString("url")));
				} catch (Exception e1) {
					Logger.logException(e1);
				}
			}
		});

		versionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filterItem.setSelectedVersion(versionCombo.getText());
				for (PackageFilterChangedListener listener : listeners) {
					listener.filterChanged(filterItem);
				}
			}
		});
	}

	protected void loadVersionsFromCache() {
		versionCombo.setItems(filterItem.getVersions());
		versionCombo.select(0);
		versionCombo.setVisible(true);
		filterItem.setSelectedVersion(versionCombo.getText());

		for (PackageFilterChangedListener listener : listeners) {
			listener.filterChanged(filterItem);
		}
	}

	protected void loadVersionCombo() {

		versionCombo.setItems(new String[] { "Loading versions..." });
		versionCombo.select(0);

		AsyncPackagistDownloader dl = new AsyncPackagistDownloader();
		dl.addPackageListener(new PackageListenerInterface() {

			@Override
			public void errorOccured(Exception e) {
				isLoadingVersions = false;
			}

			@Override
			public void aborted(String url) {
				isLoadingVersions = false;
			}

			@Override
			public void packageLoaded(RepositoryPackage repositoryPackage) {
				Versions versions = repositoryPackage.getVersions();
				final List<String> versionNames = new ArrayList<String>();
				for (Entry<String, ComposerPackage> version : versions) {
					versionNames.add(version.getValue().getVersion());
				}

				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						filterItem.setVersions(versionNames.toArray(new String[versionNames.size()]));
						loadVersionsFromCache();
					}
				});
				isLoadingVersions = false;
			}
		});

		dl.loadPackage(item.getName());
		isLoadingVersions = true;
	}

	public void addFilterChangedListener(PackageFilterChangedListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
}
