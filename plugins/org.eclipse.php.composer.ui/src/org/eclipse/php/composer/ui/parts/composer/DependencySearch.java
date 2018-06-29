/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts.composer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Twistie;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.collection.Dependencies;

public class DependencySearch extends PackageSearch {

	protected List<DependencySelectionFinishedListener> dependencyListeners = new ArrayList<>();
	private boolean collapsing = false;

	public DependencySearch(Composite parent, ComposerPackage composerPackage, FormToolkit toolkit, String buttonText) {
		super(parent, composerPackage, toolkit, buttonText);
	}

	public DependencySearch(Composite parent, ComposerPackage composerPackage, FormToolkit toolkit) {
		super(parent, composerPackage, toolkit);
	}

	public DependencySearch(Composite parent, ComposerPackage composerPackage, String buttonText) {
		super(parent, composerPackage, buttonText);
	}

	public DependencySearch(Composite parent, ComposerPackage composerPackage) {
		super(parent, composerPackage);
	}

	public void addDependencySelectionFinishedListener(DependencySelectionFinishedListener listener) {
		if (!dependencyListeners.contains(listener)) {
			dependencyListeners.add(listener);
		}
	}

	public void removeDependencySelectionFinishedListener(DependencySelectionFinishedListener listener) {
		dependencyListeners.remove(listener);
	}

	@Override
	protected void create(Composite parent, FormToolkit toolkit, String buttonText) {
		super.create(parent, toolkit, buttonText);

		if (addButton != null) {
			addButton.removeSelectionListener(addButtonListener);
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					notifyDependencySelectionFinishedListener();
				}
			});
		}
	}

	protected void notifyDependencySelectionFinishedListener() {
		Dependencies deps = getDependencies();
		for (DependencySelectionFinishedListener listener : dependencyListeners) {
			listener.dependenciesSelected(deps);
		}
		clear();
	}

	public Dependencies getDependencies() {
		Dependencies deps = new Dependencies();
		for (PackageSearchPart psp : packageControls.values()) {
			VersionedPackage pkg = ((DependencySearchPart) psp).getPackage();

			if (!pkg.getVersion().isEmpty()) {
				deps.add(pkg);
			}
		}
		return deps;
	}

	@Override
	protected DependencySearchPart createPackagePart(Composite parent, String name) {
		DependencySearchPart dsp = new DependencySearchPart(parent, composerPackage, toolkit, name);
		dsp.addToggleListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				if (!collapsing) {
					Twistie toggle = (Twistie) e.getSource();
					DependencySearchPart dsp = (DependencySearchPart) toggle.getData();
					setExpanded(dsp, toggle.isExpanded());
				}
			}
		});
		dsp.getVersionControl().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				Text version = (Text) e.getSource();
				DependencySearchPart dsp = (DependencySearchPart) version.getData();
				setExpanded(dsp, true);
			}
		});

		dsp.getVersionControl().addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				boolean canFinish = true;
				for (PackageSearchPart psp : packageControls.values()) {
					canFinish = canFinish && !((DependencySearchPart) psp).getVersionControl().getText().isEmpty();
				}

				addButton.setEnabled(canFinish && enabled);
			}
		});

		return dsp;
	}

	private void setExpanded(DependencySearchPart dsp, boolean expanded) {

		// if it goes expanded
		// collapse all dsp's first
		if (expanded) {
			collapsing = true;
			for (PackageSearchPart psp : packageControls.values()) {
				((DependencySearchPart) psp).setExpanded(false);
			}
			collapsing = false;
		}

		dsp.setExpanded(expanded);

		// update layout
		getBody().layout(true, true);
	}
}