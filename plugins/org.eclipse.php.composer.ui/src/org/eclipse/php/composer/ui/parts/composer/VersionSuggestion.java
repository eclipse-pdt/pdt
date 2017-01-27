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
package org.eclipse.php.composer.ui.parts.composer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.composer.api.ComposerConstants;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.RepositoryPackage;
import org.eclipse.php.composer.api.collection.Versions;
import org.eclipse.php.composer.api.entities.Version;
import org.eclipse.php.composer.api.packages.AsyncPackageDownloader;
import org.eclipse.php.composer.api.packages.AsyncPackagistDownloader;
import org.eclipse.php.composer.api.packages.PackageListenerInterface;
import org.eclipse.php.composer.ui.utils.WidgetFactory;
import org.eclipse.php.composer.ui.utils.WidgetHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class VersionSuggestion {

	private static final int RECENT = 0;
	private static final int CUSTOM = 1;

	private FormToolkit toolkit = null;
	private WidgetFactory factory = null;
	private Text target;
	private Composite body;

	private Button recentMinor;
	private Button recentMajor;
	private Button noConstraint;
	private Map<String, Button> constraintButtons = new HashMap<String, Button>();

	private boolean uiFinished = false;
	private boolean dataArrived = false;
	private RepositoryPackage pkg;

	private String majorVersion;
	private String minorVersion;
	private Version version = new Version();
	private Version customVersion = new Version();
	private int lastUpdate;
	private boolean updatingTarget = false;

	private TableViewer versions;
	private Combo stabilityOverride;
	private Group custom;
	private Composite right;

	private ComposerPackage composerPackage;

	public VersionSuggestion(String name, Composite parent, Text target, ComposerPackage composerPackage,
			FormToolkit toolkit) {
		this(name, parent, target, composerPackage, new WidgetFactory(toolkit));
		this.toolkit = toolkit;
	}

	public VersionSuggestion(String name, Composite parent, Text target, ComposerPackage composerPackage,
			WidgetFactory factory) {
		this.factory = factory;
		this.target = target;
		this.composerPackage = composerPackage;

		// load package with versions
		AsyncPackageDownloader downloader = new AsyncPackagistDownloader();
		downloader.addPackageListener(new PackageListenerInterface() {
			public void packageLoaded(final RepositoryPackage repositoryPackage) {
				dataArrived = true;
				pkg = repositoryPackage;

				// get major and minor
				Versions versions = pkg.getVersions();

				majorVersion = versions.getRecentMajor();
				minorVersion = versions.getRecentMinor(majorVersion);

				if (!updatingTarget) {
					updateUI();
				}
			}

			public void errorOccured(Exception e) {
				e.printStackTrace();
			}

			public void aborted(String url) {
			}
		});
		downloader.loadPackage(name);

		create(parent, factory);
	}

	private void create(Composite parent, WidgetFactory factory) {
		body = factory.createComposite(parent);
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		body.setLayout(new GridLayout());
		body.setBackground(parent.getBackground());
		WidgetHelper.trimComposite(body, 0, 0, 0, 0, 0, 0);

		// suggestions
		Group suggestions = new Group(body, SWT.SHADOW_IN);
		suggestions.setText(Messages.VersionSuggestion_SuggestionsLabel);
		suggestions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		suggestions.setLayout(new GridLayout(2, true));
		suggestions.setBackgroundMode(SWT.INHERIT_DEFAULT);
		WidgetHelper.trimComposite(suggestions, 0, 0, 0, 0, 0, 5);

		// major
		recentMajor = factory.createButton(suggestions);
		recentMajor.setAlignment(SWT.CENTER);
		recentMajor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		recentMajor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				lastUpdate = RECENT;
				version.setVersion("~" + recentMajor.getData()); //$NON-NLS-1$
			}
		});

		// minor
		recentMinor = factory.createButton(suggestions);
		recentMinor.setAlignment(SWT.CENTER);
		recentMinor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		recentMinor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				lastUpdate = RECENT;
				version.setVersion("~" + recentMajor.getData() + "." + recentMinor.getData()); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});

		// custom
		custom = new Group(body, SWT.SHADOW_ETCHED_IN);
		custom.setText(Messages.VersionSuggestion_CustomLabel);
		custom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		custom.setLayout(new GridLayout(2, false));
		custom.setBackgroundMode(SWT.INHERIT_DEFAULT);
		WidgetHelper.trimComposite(custom, 0, 0, 0, 0, 0, 5);

		VersionController controller = new VersionController();
		versions = new TableViewer(custom, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER);
		versions.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		versions.setContentProvider(controller);
		versions.setLabelProvider(controller);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.heightHint = 65;
		versions.getTable().setLayoutData(gd);
		versions.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Object elem = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (elem instanceof Version) {
					prepareCustomVersion();
					Version v = (Version) elem;
					customVersion.setMajor(v.getMajor());
					customVersion.setMinor(v.getMinor());
					customVersion.setFix(v.getFix());
					customVersion.setDevPosition(v.getDevPosition());
					customVersion.setStability(v.getStability());
					customVersion.setSuffix(v.getSuffix());
					version.from(customVersion);
				}
			}
		});
		versions.setComparator(new VersionSorter());

		// constraints
		right = factory.createComposite(custom, SWT.NO_BACKGROUND);
		right.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		right.setLayout(new GridLayout(3, false));
		right.setBackgroundMode(SWT.INHERIT_DEFAULT);
		WidgetHelper.trimComposite(right, -5, -5, -5, -5, 0, 0);

		Label constraintsLbl = factory.createLabel(right, SWT.NO_BACKGROUND | SWT.TRANSPARENT);
		constraintsLbl.setText(Messages.VersionSuggestion_ConstraintsLabel);
		constraintsLbl.setBackground(custom.getBackground());
		constraintsLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		noConstraint = factory.createButton(right, SWT.RADIO | SWT.NO_BACKGROUND);
		noConstraint.setText(Messages.VersionSuggestion_NoneLabel);
		noConstraint.setBackground(custom.getBackground());
		noConstraint.setSelection(true);
		noConstraint.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prepareCustomVersion()) {
					customVersion.setConstraint(""); //$NON-NLS-1$
					version.from(customVersion);
				}
			}
		});

		for (String constraint : new String[] { "~", ">", ">=", "!=", "<", ">=" }) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			Button c = factory.createButton(right, SWT.RADIO | SWT.TRANSPARENT);
			c.setText(constraint);
			c.setBackground(custom.getBackground());
			c.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (prepareCustomVersion()) {
						customVersion.setConstraint(((Button) e.getSource()).getText());
						version.from(customVersion);
					}
				}
			});
			constraintButtons.put(constraint, c);
		}

		// Stability Override
		Label stabilityLbl = factory.createLabel(right);
		stabilityLbl.setText(Messages.VersionSuggestion_StabilityLabel);
		stabilityLbl.setBackground(custom.getBackground());
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd.verticalIndent = 10;
		stabilityLbl.setLayoutData(gd);

		stabilityOverride = factory.createCombo(right, SWT.READ_ONLY | SWT.FLAT);
		stabilityOverride.setItems((String[]) ArrayUtils.addAll(new String[] { "" }, ComposerConstants.STABILITIES)); //$NON-NLS-1$
		stabilityOverride.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		stabilityOverride.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (prepareCustomVersion()) {
					customVersion
							.setStabilityModifier(stabilityOverride.getItem(stabilityOverride.getSelectionIndex()));
					version.from(customVersion);
				}
			}
		});

		uiFinished = true;
		updateUI();

		// add listener to update target when version changes
		version.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateTarget();
			}
		});

	}

	private void updateUI() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (uiFinished) {
					recentMajor.setText(Messages.VersionSuggestion_MajorLabel + (dataArrived && majorVersion != null
							? "~" + majorVersion : Messages.VersionSuggestion_NAValue)); //$NON-NLS-1$
					recentMajor.setEnabled(dataArrived && majorVersion != null);
					recentMajor.setData(majorVersion);

					recentMinor.setText(Messages.VersionSuggestion_MinorLabel
							+ (dataArrived && majorVersion != null && minorVersion != null
									? "~" + majorVersion + "." + minorVersion : Messages.VersionSuggestion_NAValue)); //$NON-NLS-1$ //$NON-NLS-2$
					recentMinor.setEnabled(dataArrived && minorVersion != null);
					recentMinor.setData(minorVersion);

					custom.setEnabled(dataArrived);
					versions.getTable().setEnabled(dataArrived);
					stabilityOverride.setEnabled(dataArrived);

					// enable state for constraints
					for (Control child : right.getChildren()) {
						if (child instanceof Button) {
							((Button) child).setEnabled(dataArrived);
						}
					}

					if (dataArrived) {
						versions.setInput(pkg.getVersions());
					}
				}
			}
		});
	}

	private void updateTarget() {
		updatingTarget = true;
		if (lastUpdate == RECENT
				|| (version.getMajor() != null && !version.getMajor().isEmpty() && version.getMajor() != "null")) { //$NON-NLS-1$

			String v = version.toString();
			if (v != null) {
				target.setText(v);
			}
		}

		updatingTarget = false;
	}

	private boolean prepareCustomVersion() {
		if (updatingTarget) {
			return false;
		}

		if (lastUpdate == RECENT) {
			version.clear();
		}
		lastUpdate = CUSTOM;

		return true;
	}

	public Composite getBody() {
		return body;
	}

	private class VersionController extends StyledCellLabelProvider implements IStructuredContentProvider {

		private Versions versions;

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			versions = (Versions) newInput;
		}

		public Object[] getElements(Object inputElement) {
			return versions.getDetailedVersions().toArray();
		}

		public void update(ViewerCell cell) {
			Object obj = cell.getElement();

			if (obj instanceof Version) {
				Version v = (Version) obj;

				StyledString styledString = new StyledString(v.toString());
				styledString.append(" : " + v.getStability(), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$

				cell.setText(styledString.toString());
				cell.setStyleRanges(styledString.getStyleRanges());

				super.update(cell);
			}
		}

	}

	private class VersionSorter extends ViewerComparator {
		public int compare(Viewer viewer, Object e1, Object e2) {
			if (e1 instanceof Version && e2 instanceof Version) {
				return ((Version) e1).compareTo((Version) e2) * -1;
			}

			return 0;
		}
	}
}
