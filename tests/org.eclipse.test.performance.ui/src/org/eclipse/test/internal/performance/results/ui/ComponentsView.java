/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.test.internal.performance.results.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.test.internal.performance.results.model.BuildResultsElement;
import org.eclipse.test.internal.performance.results.model.ComponentResultsElement;
import org.eclipse.test.internal.performance.results.model.ConfigResultsElement;
import org.eclipse.test.internal.performance.results.model.ResultsElement;
import org.eclipse.test.internal.performance.results.model.ScenarioResultsElement;
import org.eclipse.test.internal.performance.results.utils.IPerformancesConstants;
import org.eclipse.test.internal.performance.results.utils.Util;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * View to see the performance results of all the components in a hierarchical tree.
 * <p>
 * A component defines several performance scenarios which are run on several
 * machines (aka config). All builds results are stored onto each configuration
 * and 2 dimensions have been stored for each result: the "Elapsed Process Time"
 * and the "CPU Time".
 * </p><p>
 * There's only one available action from this view: read the local data files. This
 * populates the hierarchy with the numbers stored in these files.
 * </p><p>
 * There's also the possibility to filter the results:
 * 	<ul>
 *	<li>Filter for builds:
 *		<ul>
 *		<li>Filter baselines:	hide the baselines (starting with R-3.x)</li>
 *		<li>Filter nightly:	hide the nightly builds (starting with 'N')</li>
 *		<li>Filter non-important builds:	hide all non-important builds, which means non-milestone builds and those after the last milestone</li>
 *		</ul>
 *	</li>
 *	</li>Filter for scenarios:
 *		<ul>
 *		<li>Filter non-fingerprints: hide the scenarios which are not in the fingerprints</li>
 *		</ul>
 *	</li>
 *	</ul>
 * </p>
 * @see ComponentResultsView
 */
public class ComponentsView extends PerformancesView {

	// Viewer filters
	final static ViewerFilter FILTER_ADVANCED_SCENARIOS = new ViewerFilter() {
		@Override
		public boolean select(Viewer v, Object parentElement, Object element) {
			if (element instanceof ScenarioResultsElement) {
				ScenarioResultsElement scenarioElement = (ScenarioResultsElement) element;
				return scenarioElement.hasSummary();
			}
	        return true;
        }
	};

	// Views
	PerformancesView buildsView;
	ComponentResultsView componentResultsView = null;

	// Internal
	Set<ResultsElement> expandedComponents = new HashSet<>();

	// Actions
	Action filterAdvancedScenarios;

	// SWT resources
	Font boldFont;

/**
 * Default constructor.
 */
public ComponentsView() {
//	this.onlyFingerprintsImageDescriptor = ImageDescriptor.createFromFile(getClass(), "filter_ps.gif");
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#createPartControl(org.eclipse.swt.widgets.Composite)
 */
@Override
public void createPartControl(Composite parent) {
	super.createPartControl(parent);

	// Create the viewer
	this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

	// Set the content provider: first level is components list
	WorkbenchContentProvider contentProvider = new WorkbenchContentProvider() {
		@Override
		public Object[] getElements(Object o) {
			return ComponentsView.this.getElements();
		}
	};
	this.viewer.setContentProvider(contentProvider);

	// Set the label provider
	WorkbenchLabelProvider labelProvider = new WorkbenchLabelProvider() {

		@Override
		protected String decorateText(String input, Object element) {
			String text = super.decorateText(input, element);
			if (element instanceof BuildResultsElement) {
				BuildResultsElement buildElement = (BuildResultsElement) element;
				if (buildElement.isMilestone()) {
					text = Util.getMilestoneName(buildElement.getName()) + " - "+text;
				}
			}
			return text;
		}

		// When all scenarios are displayed, then set fingerprints one in bold.
		@Override
		public Font getFont(Object element) {
			Font font = super.getFont(element);
			if (element instanceof ScenarioResultsElement) {
//				Action fingerprints = ComponentsView.this.filterNonFingerprints;
//				if (fingerprints != null && !fingerprints.isChecked()) {
				boolean fingerprints = ComponentsView.this.preferences.getBoolean(IPerformancesConstants.PRE_FILTER_ADVANCED_SCENARIOS, IPerformancesConstants.DEFAULT_FILTER_ADVANCED_SCENARIOS);
				if (!fingerprints) {
					ScenarioResultsElement scenarioElement = (ScenarioResultsElement) element;
					if (scenarioElement.hasSummary()) {
						return getBoldFont(font);
					}
				}
			}
			if (element instanceof BuildResultsElement) {
				BuildResultsElement buildElement = (BuildResultsElement) element;
				if (Util.isMilestone(buildElement.getName())) {
					return getBoldFont(font);
				}
			}
			return font;
		}
	};
	this.viewer.setLabelProvider(labelProvider);

	// Set the children sorter
	ViewerSorter nameSorter = new ViewerSorter() {

		// Sort children using specific comparison (see the implementation
		// of the #compareTo(Object) in the ResultsElement hierarchy
		@Override
		public int compare(Viewer view, Object e1, Object e2) {
			// Config and Build results are sorted in reverse order
			if (e1 instanceof BuildResultsElement) {
				ResultsElement element = (ResultsElement) e2;
				return element.compareTo(e1);
			}
			if (e1 instanceof ResultsElement) {
				ResultsElement element = (ResultsElement) e1;
				return element.compareTo(e2);
			}
			return super.compare(view, e1, e2);
		}
	};
	this.viewer.setSorter(nameSorter);

	// Add results view as listener to viewer selection changes
	Display.getDefault().asyncExec(new Runnable() {
		@Override
		public void run() {
			ISelectionChangedListener listener = getResultsView();
			if (listener != null) {
				ComponentsView.this.viewer.addSelectionChangedListener(listener);
			}
		}
	});

	// Finalize viewer initialization
	PlatformUI.getWorkbench().getHelpSystem().setHelp(this.viewer.getControl(), "org.eclipse.test.performance.ui.components");
	finalizeViewerCreation();
}

/*
 * (non-Javadoc)
 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
 */
@Override
public void dispose() {
	if (this.boldFont != null) {
		this.boldFont.dispose();
	}
//	JFaceResources.getResources().destroyImage(this.onlyFingerprintsImageDescriptor);
	super.dispose();
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#fillLocalPullDown(org.eclipse.jface.action.IMenuManager)
 */
@Override
void fillFiltersDropDown(IMenuManager manager) {
	super.fillFiltersDropDown(manager);
	manager.add(this.filterOldBuilds);
	manager.add(new Separator());
	manager.add(this.filterAdvancedScenarios);
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#fillLocalToolBar(org.eclipse.jface.action.IToolBarManager)
 */
@Override
void fillLocalToolBar(IToolBarManager manager) {
	super.fillLocalToolBar(manager);
}

/*
 * Filter non fingerprints scenarios action run.
 */
void filterAdvancedScenarios(boolean fingerprints, boolean updatePreference) {
	this.results.setFingerprints(fingerprints);
	if (fingerprints) {
		this.viewFilters.add(FILTER_ADVANCED_SCENARIOS);
	} else {
		this.viewFilters.remove(FILTER_ADVANCED_SCENARIOS);
	}
	this.preferences.putBoolean(IPerformancesConstants.PRE_FILTER_ADVANCED_SCENARIOS, fingerprints);
	updateFilters();
}

/*
 * Returns the bold font.
 */
Font getBoldFont(Font font) {
	if (this.boldFont == null) {
		FontData[] fontData = (font==null ? JFaceResources.getDefaultFont() : font).getFontData();
		FontData boldFontData = new FontData(fontData[0].getName(), fontData[0].getHeight(), SWT.BOLD);
		this.boldFont = new Font(this.display, boldFontData);
	}
	return this.boldFont;
}

/*
 * Get all the components from the model.
 */
Object[] getElements() {
	if (this.results == null) {
		initResults();
		if (this.filterAdvancedScenarios != null) {
			this.results.setFingerprints(this.filterAdvancedScenarios.isChecked());
		}
	}
	return this.results.getElements();
}

/*
 * Return the components results view.
 */
ComponentResultsView getResultsView() {
	if (this.componentResultsView == null) {
		this.componentResultsView = (ComponentResultsView) getWorkbenchView("org.eclipse.test.internal.performance.results.ui.ComponentsResultsView");
	}
	return this.componentResultsView;
}

/*
 * Return the builds view.
 */
@Override
PerformancesView getSiblingView() {
	if (this.buildsView == null) {
		this.buildsView = (PerformancesView) getWorkbenchView("org.eclipse.test.internal.performance.results.ui.BuildsView");
	}
	return this.buildsView;
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#makeActions()
 */
@Override
void makeActions() {

	super.makeActions();

	// Filter non-fingerprints action
	this.filterAdvancedScenarios = new Action("Advanced &Scenarios", IAction.AS_CHECK_BOX) {
		@Override
		public void run() {
			filterAdvancedScenarios(isChecked(), true/*update preference*/);
        }
	};
	this.filterAdvancedScenarios.setChecked(true);
	this.filterAdvancedScenarios.setToolTipText("Filter advanced scenarios (i.e. not fingerprint ones)");

	// Set filters default
	this.filterBaselineBuilds.setChecked(true);
	this.filterNightlyBuilds.setChecked(false);
}

/* (non-Javadoc)
 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener#preferenceChange(org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent)
 */
@Override
public void preferenceChange(PreferenceChangeEvent event) {
	String propertyName = event.getKey();
	Object newValue = event.getNewValue();

	// Filter non-fingerprints change
	if (propertyName.equals(IPerformancesConstants.PRE_FILTER_ADVANCED_SCENARIOS)) {
		boolean checked = newValue == null ? IPerformancesConstants.DEFAULT_FILTER_ADVANCED_SCENARIOS : "true".equals(newValue);
		filterAdvancedScenarios(checked, false/*do not update preference*/);
		this.filterAdvancedScenarios.setChecked(checked);
	}

	// Filter non-milestone change
	if (propertyName.equals(IPerformancesConstants.PRE_FILTER_OLD_BUILDS)) {
		boolean checked = newValue == null ? IPerformancesConstants.DEFAULT_FILTER_OLD_BUILDS : "true".equals(newValue);
		filterOldBuilds(checked, false/*do not update preference*/);
		this.filterOldBuilds.setChecked(checked);
	}

	// Filter nightly builds change
	if (propertyName.equals(IPerformancesConstants.PRE_FILTER_NIGHTLY_BUILDS)) {
		boolean checked = newValue == null ? IPerformancesConstants.DEFAULT_FILTER_NIGHTLY_BUILDS : "true".equals(newValue);
		filterNightlyBuilds(checked, false/*do not update preference*/);
		this.filterNightlyBuilds.setChecked(checked);
	}
}

@Override
void restoreState() {
	super.restoreState();

	// Filter baselines action default
	if (this.viewState == null) {
		this.filterBaselineBuilds.setChecked(true);
		this.viewFilters.add(FILTER_BASELINE_BUILDS);
	}

	// Filter non fingerprints action state
	boolean checked = this.preferences.getBoolean(IPerformancesConstants.PRE_FILTER_ADVANCED_SCENARIOS, IPerformancesConstants.DEFAULT_FILTER_ADVANCED_SCENARIOS);
	this.filterAdvancedScenarios.setChecked(checked);
	if (checked) {
		this.viewFilters.add(FILTER_ADVANCED_SCENARIOS);
	}
}

/**
 * Select a results element in the tree.
 */
public void select(ComponentResultsElement componentResults, String configName, String scenarioName, String buildName) {

	// Collapse previous expanded components except the requested one
	// TODO (frederic) also collapse expanded components children elements
	this.expandedComponents.remove(componentResults);
	Iterator<ResultsElement> iterator = this.expandedComponents.iterator();
	while (iterator.hasNext()) {
		this.viewer.collapseToLevel(iterator.next(), AbstractTreeViewer.ALL_LEVELS);
	}
	this.expandedComponents.clear();

	// Set the tree selection
	ScenarioResultsElement scenarioResultsElement = (ScenarioResultsElement) componentResults.getResultsElement(scenarioName);
	if (scenarioResultsElement != null) {
		ConfigResultsElement configResultsElement = (ConfigResultsElement) scenarioResultsElement.getResultsElement(configName);
		if (configResultsElement != null) {
			BuildResultsElement buildResultsElement = (BuildResultsElement) configResultsElement.getResultsElement(buildName);
			if (buildResultsElement != null) {
				this.viewer.setSelection(new StructuredSelection(buildResultsElement), true);
				this.setFocus();
			}
		}
	}
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
 */
@Override
public void selectionChanged(SelectionChangedEvent event) {
	super.selectionChanged(event);
	ResultsElement eventResultsElement = (ResultsElement) ((StructuredSelection)event.getSelection()).getFirstElement();
	if (eventResultsElement != null) {
		ResultsElement eventComponentElement = eventResultsElement;
		if (!(eventComponentElement instanceof ComponentResultsElement)) {
			while (!(eventComponentElement instanceof ComponentResultsElement)) {
				eventComponentElement = (ResultsElement) eventComponentElement.getParent(null);
			}
			this.expandedComponents.add(eventComponentElement);
		}
	}
}
}