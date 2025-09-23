/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts.composer;

import java.util.*;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.MinimalPackage;
import org.eclipse.php.composer.api.packages.AsyncPackageSearch;
import org.eclipse.php.composer.api.packages.AsyncPackagistSearch;
import org.eclipse.php.composer.api.packages.PackageSearchListenerInterface;
import org.eclipse.php.composer.api.packages.SearchResult;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.controller.IPackageCheckStateChangedListener;
import org.eclipse.php.composer.ui.controller.PackageController;
import org.eclipse.php.composer.ui.editor.FormLayoutFactory;
import org.eclipse.php.composer.ui.utils.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class PackageSearch implements PackageSearchListenerInterface, IPackageCheckStateChangedListener {

	protected static final long QUERY_DELAY_MS = 300;
	protected static final long RESET_QUERY_DELAY_MS = 1500;

	protected FormToolkit toolkit;
	protected WidgetFactory factory;

	protected Text searchField;
	protected CheckboxTableViewer searchResults;
	protected PackageController searchController;
	protected Composite body;
	protected Composite pickedResults;
	protected Map<String, PackageSearchPart> packageControls = new HashMap<>();
	protected Button addButton;

	protected AsyncPackageSearch downloader = new AsyncPackagistSearch();
	protected String currentQuery;
	protected String lastQuery;
	protected String shownQuery;
	protected String foundQuery;

	protected Thread resetThread;
	protected Thread queryThread;

	protected boolean clearing = false;
	protected SelectionAdapter addButtonListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			notifyPackageSelectionFinishedListener();
		}
	};

	protected boolean enabled = true;

	protected ComposerPackage composerPackage;

	protected Set<PackageSelectionFinishedListener> packageListeners = new HashSet<>();

	public PackageSearch(Composite parent, ComposerPackage composerPackage, FormToolkit toolkit, String buttonText) {
		this.composerPackage = composerPackage;
		create(parent, toolkit, buttonText);
	}

	public PackageSearch(Composite parent, ComposerPackage composerPackage, FormToolkit toolkit) {
		this(parent, composerPackage, toolkit, null);
	}

	public PackageSearch(Composite parent, ComposerPackage composerPackage, String buttonText) {
		this(parent, composerPackage, null, buttonText);
	}

	public PackageSearch(Composite parent, ComposerPackage composerPackage) {
		this(parent, composerPackage, null, null);
	}

	public void addPackageCheckStateChangedListener(IPackageCheckStateChangedListener listener) {
		if (searchController != null) {
			searchController.addPackageCheckStateChangedListener(listener);
		}
	}

	public void removePackageCheckStateChangedListener(IPackageCheckStateChangedListener listener) {
		if (searchController != null) {
			searchController.removePackageCheckStateChangedListener(listener);
		}
	}

	public void addPackageSelectionFinishedListener(PackageSelectionFinishedListener listener) {
		packageListeners.add(listener);
	}

	public void removePackageSelectionFinishedListener(PackageSelectionFinishedListener listener) {
		packageListeners.remove(listener);
	}

	protected void create(Composite parent, FormToolkit toolkit, String buttonText) {
		this.toolkit = toolkit;
		factory = new WidgetFactory(toolkit);

		body = factory.createComposite(parent);
		body.setLayout(new GridLayout());

		searchField = factory.createText(body,
				SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		searchField.setEnabled(enabled);
		searchField.setEditable(enabled);
		searchField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		searchField.addModifyListener(e -> searchTextChanged());

		// create search results viewer
		int style = SWT.H_SCROLL | SWT.V_SCROLL;
		if (toolkit == null) {
			style |= SWT.BORDER;
		} else {
			style |= toolkit.getBorderStyle();
		}

		searchController = getSearchResultsController();
		searchController.addPackageCheckStateChangedListener(this);
		searchResults = CheckboxTableViewer.newCheckList(body, style);
		searchResults.getTable().setEnabled(enabled);
		searchResults.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		searchResults.setCheckStateProvider(searchController);
		searchResults.addCheckStateListener(searchController);
		searchResults.setContentProvider(searchController);
		searchResults.setLabelProvider(searchController);
		searchResults.setInput(new ArrayList<ComposerPackage>());

		pickedResults = factory.createComposite(body);
		pickedResults.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		GridLayout layout = new GridLayout();
		layout.marginTop = 0;
		layout.marginRight = -5;
		layout.marginBottom = 0;
		layout.marginLeft = -5;
		layout.verticalSpacing = FormLayoutFactory.SECTION_CLIENT_MARGIN_TOP;
		layout.horizontalSpacing = 0;
		pickedResults.setLayout(layout);

		if (buttonText != null) {
			addButton = factory.createButton(body);
			addButton.setText(buttonText);
			addButton.setEnabled(false);
			addButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
			addButton.addSelectionListener(addButtonListener);
		}

		// create downloader
		downloader.addPackageSearchListener(this);
	}

	protected void notifyPackageSelectionFinishedListener() {
		List<String> packages = getPackages();
		for (PackageSelectionFinishedListener listener : packageListeners) {
			listener.packagesSelected(packages);
		}
		clear();
	}

	protected void clearSearchText() {
		if (clearing) {
			return;
		}

		clearing = true;
		searchResults.setInput(null);
		searchField.setText(""); //$NON-NLS-1$
		downloader.abort();

		shownQuery = null;
		queryThread.interrupt();
		resetThread.interrupt();
		clearing = false;
	}

	@Override
	public void aborted(String url) {

	}

	@Override
	public void packagesFound(final List<MinimalPackage> packages, String query, SearchResult result) {
		foundQuery = query;
		Logger.debug("Found Packages for: " + query + " => " + packages.size()); //$NON-NLS-1$ //$NON-NLS-2$

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (currentQuery.isEmpty()) {
					return;
				}

				if (shownQuery == null || (!shownQuery.equals(foundQuery) && currentQuery.equals(foundQuery))) {
					searchResults.setInput(packages);
					shownQuery = foundQuery;
				}

				else if (shownQuery.equals(foundQuery)) {
					searchController.addPackages(packages);
					searchResults.refresh();
				}
			}
		});
	}

	@Override
	public void errorOccured(Exception e) {
		// something happend during package search ...
		e.printStackTrace();
	}

	protected void searchTextChanged() {
		currentQuery = searchField.getText();

		if (currentQuery.trim().isEmpty()) {
			clearSearchText();
			return;
		}

		// kill previous downloader
		downloader.abort();

		// run a new one
		if (queryThread == null || !queryThread.isAlive() || queryThread.isInterrupted()) {
			startQuery();
			queryThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(QUERY_DELAY_MS);

						synchronized (PackageSearch.this) {
							startQuery();
							queryThread.interrupt();
						}
					} catch (InterruptedException e) {
					}
				}
			});
			queryThread.start();
		}
	}

	protected synchronized void startQuery() {
		if (lastQuery.equals(currentQuery)) {
			return;
		}
		downloader.search(currentQuery);

		if (resetThread != null) {
			resetThread.interrupt();
		}
		resetThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(RESET_QUERY_DELAY_MS);

					synchronized (PackageSearch.this) {
						if (currentQuery.equals(shownQuery)) {
							shownQuery = null;
						}
					}
				} catch (InterruptedException e) {
				}
			}
		});
		resetThread.start();
		lastQuery = currentQuery;
	}

	protected PackageController getSearchResultsController() {
		return new PackageController();
	}

	public Composite getBody() {
		return body;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		searchField.setEnabled(enabled);
		searchField.setEditable(enabled);
		searchResults.getTable().setEnabled(enabled);

		if (addButton != null) {
			addButton.setEnabled(addButton.getEnabled() && enabled);
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void packageCheckStateChanged(String name, boolean checked) {
		if (checked) {
			packageControls.put(name, connectPackagePart(createPackagePart(pickedResults, name)));
		} else {
			packageControls.remove(name).dispose();
		}
		if (addButton != null) {
			addButton.setEnabled(searchController.getCheckedPackagesCount() > 0 && enabled);
		}
		getBody().layout(true, true);
	}

	private PackageSearchPart connectPackagePart(final PackageSearchPart psp) {
		psp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchController.setChecked(psp.getName(), psp.isChecked());
				searchResults.refresh();
			}
		});
		return psp;
	}

	protected PackageSearchPart createPackagePart(Composite parent, final String name) {
		return new PackageSearchPart(parent, composerPackage, toolkit, name);
	}

	public List<String> getPackages() {
		return searchController.getCheckedPackages();
	}

	public void clear() {
		clearSearchText();
		searchController.clear();
		packageControls.clear();
		for (Control child : pickedResults.getChildren()) {
			child.dispose();
		}
		if (addButton != null) {
			addButton.setEnabled(false);
		}
		searchResults.refresh();
		getBody().layout(true, true);
	}

}
