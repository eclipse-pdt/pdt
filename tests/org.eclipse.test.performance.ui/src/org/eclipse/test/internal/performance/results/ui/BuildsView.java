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


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.test.internal.performance.results.db.DB_Results;
import org.eclipse.test.internal.performance.results.model.BuildResultsElement;
import org.eclipse.test.internal.performance.results.model.ResultsElement;
import org.eclipse.test.internal.performance.results.utils.IPerformancesConstants;
import org.eclipse.test.internal.performance.results.utils.Util;
import org.eclipse.test.performance.ui.GenerateResults;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;


/**
 * View to see all the builds which have performance results stored in the database.
 * <p>
 * Typical actions from this view are update local data files with builds results
 * and generated the HTML pages.
 * </p>
 */
public class BuildsView extends PerformancesView {

	/**
	 * Action to generate results.
	 */
	final class GenerateAction extends Action {
		IStatus status;

		public void run() {

			// Ask for output directory
			String resultGenerationDir = BuildsView.this.preferences.get(IPerformancesConstants.PRE_RESULTS_GENERATION_DIR, "");
			String pathFilter = (BuildsView.this.outputDir == null) ? resultGenerationDir : BuildsView.this.outputDir.getPath();
			File dir = changeDir(pathFilter, "Select directory to write comparison files");
			if (dir == null) {
				return;
			}
			BuildsView.this.outputDir = dir;
			BuildsView.this.preferences.put(IPerformancesConstants.PRE_RESULTS_GENERATION_DIR, dir.getAbsolutePath());

			// Select the reference
			String[] baselines = BuildsView.this.results.getBaselines();
			int bLength = baselines.length;
			String selectedBaseline;
			switch (bLength) {
				case 0:
					// no baseline, nothing to do...
					selectedBaseline = BuildsView.this.results.getPerformanceResults().getBaselineName();
					break;
				case 1:
					// only one baseline, no selection to do
					selectedBaseline = baselines[0];
					break;
				default:
					// select the baseline from list
					ElementListSelectionDialog dialog = new ElementListSelectionDialog(getSite().getShell(), new LabelProvider());
					dialog.setTitle(getTitleToolTip());
					dialog.setMessage("Select the baseline to use while generating results:");
					String[] defaultBaseline = new String[] { baselines[baselines.length - 1] };
					dialog.setInitialSelections(defaultBaseline);
					dialog.setElements(baselines);
					dialog.open();
					Object[] selected = dialog.getResult();
					if (selected == null)
						return;
					selectedBaseline = (String) selected[0];
					break;
			}
			final String baselineName = selectedBaseline;
			BuildsView.this.results.getPerformanceResults().setBaselineName(baselineName);

			// Ask for fingerprints
			final boolean fingerprints = MessageDialog.openQuestion(BuildsView.this.shell, getTitleToolTip(), "Generate only fingerprints?");

			// Generate all selected builds
			int length = BuildsView.this.buildsResults.length;
			for (int i = 0; i < length; i++) {
				generate(i, baselineName, fingerprints);
			}
		}

		/*
		 * Generate the HTML pages.
		 */
		private void generate(int i, final String baselineName, final boolean fingerprints) {
			// Create output directory
			final String buildName = BuildsView.this.buildsResults[i].getName();
			final File genDir = new File(BuildsView.this.outputDir, buildName);
			if (!genDir.exists() && !genDir.mkdir()) {
				MessageDialog.openError(BuildsView.this.shell, getTitleToolTip(), "Cannot create " + genDir.getPath() + " to generate results!");
				return;
			}

			// Create runnable
			IRunnableWithProgress runnable = new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						monitor.beginTask("Generate performance results", 10000);
						GenerateResults generation = new GenerateResults(BuildsView.this.results.getPerformanceResults(),
						    buildName,
						    baselineName,
						    fingerprints,
						    BuildsView.this.dataDir,
						    genDir);
						GenerateAction.this.status = generation.run(monitor);
						monitor.done();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			// Run with progress monitor
			ProgressMonitorDialog readProgress = new ProgressMonitorDialog(getSite().getShell());
			try {
				readProgress.run(true, true, runnable);
			} catch (InvocationTargetException e) {
				// skip
			} catch (InterruptedException e) {
				// skip
			}

			// Results
			if (!this.status.isOK()) {
				StringWriter swriter = new StringWriter();
				PrintWriter pwriter = new PrintWriter(swriter);
				swriter.write(this.status.getMessage());
				Throwable ex = this.status.getException();
				if (ex != null) {
					swriter.write(": ");
					swriter.write(ex.getMessage());
					swriter.write('\n');
					ex.printStackTrace(pwriter);
				}
				MessageDialog.open(this.status.getSeverity(),
				    BuildsView.this.shell,
				    getTitleToolTip(),
				    swriter.toString(),
				    SWT.NONE);
			}
		}
	}

	/**
	 * Action to update local data files with the performance results of a build.
	 *
	 * This may be done lazily (i.e. not done if the local data already knows
	 * the build) or forced (i.e. done whatever the local data files contain).
	 */
	class UpdateBuildAction extends Action {

		boolean force;

		UpdateBuildAction(boolean force) {
			super();
			this.force = force;
		}

		public void run() {

			// Verify that directories are set
			if (BuildsView.this.dataDir == null) {
				if (changeDataDir() == null) {
					if (!MessageDialog.openConfirm(BuildsView.this.shell, getTitleToolTip(), "No local files directory is set, hence the update could not be written! OK to continue?")) {
						return;
					}
				}
			}

			// Progress dialog
			IRunnableWithProgress runnable = new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						updateBuilds(monitor);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			ProgressMonitorDialog readProgress = new ProgressMonitorDialog(getSite().getShell());
			try {
				readProgress.run(true, true, runnable);
			} catch (InvocationTargetException e) {
				return;
			} catch (InterruptedException e) {
				return;
			}

			// Reset Components and Builds views input
			refreshInput();
			getSiblingView().refreshInput();
		}

		void updateBuilds(IProgressMonitor monitor) {
			BuildsView.this.updateBuilds(monitor, this.force);
		}
	}

	/**
	 * Action to update local data files with the performance results of all builds.
	 *
	 * This may be done lazily (i.e. not done if the local data already knows
	 * the build) or forced (i.e. done whatever the local data files contain).
	 */
	class UpdateAllBuildsAction extends UpdateBuildAction {

		UpdateAllBuildsAction(boolean force) {
			super(force);
		}
//
//		public boolean isEnabled() {
//			String[] elements = buildsToUpdate();
//			return elements != null;
//		}

		void updateBuilds(IProgressMonitor monitor) {
			BuildsView.this.updateAllBuilds(monitor, this.force);
		}
	}

	/**
	 * Class to compare builds regarding their date instead of their name.
	 *
	 * @see Util#getBuildDate(String)
	 */
	class BuildDateComparator implements Comparator {
		public int compare(Object o1, Object o2) {
	        String s1 = (String) o1;
	        String s2 = (String) o2;
	        return Util.getBuildDate(s1).compareTo(Util.getBuildDate(s2));
	    }
	}

	// Views
	PerformancesView componentsView;

	// Results model
	BuildResultsElement[] buildsResults;

	// Generation info
	File outputDir;

	// Actions
	Action generate;
	UpdateBuildAction updateBuild, updateAllBuilds;
//	UpdateBuildAction forceUpdateBuild, forceUpdateAllBuilds;

	// SWT resources
	Font italicFont;

/*
 * Default constructor.
 */
public BuildsView() {
	this.preferences = new InstanceScope().getNode(IPerformancesConstants.PLUGIN_ID);
	this.preferences.addPreferenceChangeListener(this);
}

/*
 * Compute the list of builds to update based on their status.
 */
String[] buildsToUpdate() {
	Object[] elements = this.results.getBuilds();
	int length = elements.length;
	String[] buildsToUpdate = new String[length];
	int count = 0;
	for (int i=0; i<length; i++) {
		BuildResultsElement element = (BuildResultsElement) elements[i];
		if (element.getStatus() == 0) {
	        buildsToUpdate[count++] = element.getName();
		}
	}
	if (count == 0) return null;
	if (count < length) {
		System.arraycopy(buildsToUpdate, 0, buildsToUpdate = new String[count], 0, count);
	}
	return buildsToUpdate;
}

/* (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#createPartControl(org.eclipse.swt.widgets.Composite)
 */
public void createPartControl(Composite parent) {
	super.createPartControl(parent);

	// Create the viewer
	this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

	// Set the content provider: first level is builds list
	WorkbenchContentProvider contentProvider = new WorkbenchContentProvider() {
		public Object[] getElements(Object o) {
			return getBuilds();
		}
	};
	this.viewer.setContentProvider(contentProvider);

	// Set the label provider
	WorkbenchLabelProvider labelProvider = new WorkbenchLabelProvider() {

		// Set an italic font when no local data have been read
		public Font getFont(Object element) {
			Font font = super.getFont(element);
			if (element instanceof BuildResultsElement) {
				if (((BuildResultsElement) element).isUnknown()) {
					if (BuildsView.this.italicFont == null) {
						FontData[] defaultFont = JFaceResources.getDefaultFont().getFontData();
						FontData italicFontData = new FontData(defaultFont[0].getName(), defaultFont[0].getHeight(), SWT.ITALIC);
						BuildsView.this.italicFont = new Font(DEFAULT_DISPLAY, italicFontData);
					}
					return BuildsView.this.italicFont;
				}
			}
			return font;
		}

		// Set font in gray when no local data is available (i.e. local data needs to be updated)
		public Color getForeground(Object element) {
			Color color = super.getForeground(element);
			if (element instanceof BuildResultsElement) {
				if (!((BuildResultsElement) element).isRead()) {
					color = DARK_GRAY;
				}
			}
			return color;
		}
	};
	this.viewer.setLabelProvider(labelProvider);

	// Set the children sorter
	ViewerSorter nameSorter = new ViewerSorter() {

		// Sort children using specific comparison (see the implementation
		// of the #compareTo(Object) in the ResultsElement hierarchy
		public int compare(Viewer view, Object e1, Object e2) {
			if (e2 instanceof ResultsElement) {
				return ((ResultsElement) e2).compareTo(e1);
			}
			return super.compare(view, e1, e2);
		}
	};
	this.viewer.setSorter(nameSorter);

	// Finalize viewer initialization
	PlatformUI.getWorkbench().getHelpSystem().setHelp(this.viewer.getControl(), "org.eclipse.test.performance.ui.builds");
	finalizeViewerCreation();
}

/* (non-Javadoc)
 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
 */
public void dispose() {
	if (this.italicFont != null) {
		this.italicFont.dispose();
	}
	super.dispose();
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#fillContextMenu(org.eclipse.jface.action.IMenuManager)
 */
void fillContextMenu(IMenuManager manager) {
	super.fillContextMenu(manager);
	manager.add(this.generate);
	manager.add(this.updateBuild);
//	manager.add(this.forceUpdateBuild);
}

/*
 * Fill the local data drop-down menu
 */
void fillLocalDataDropDown(IMenuManager manager) {
	super.fillLocalDataDropDown(manager);
	manager.add(new Separator());
	manager.add(this.updateAllBuilds);
//	manager.add(this.forceUpdateAllBuilds);
}

/*
 * Get all builds from the model.
 */
Object[] getBuilds() {
	if (this.results == null) {
		initResults();
	}
	return this.results.getBuilds();
}

/*
 * Return the components view.
 */
PerformancesView getSiblingView() {
	if (this.componentsView == null) {
		this.componentsView = (PerformancesView) getWorkbenchView("org.eclipse.test.internal.performance.results.ui.ComponentsView");
	}
	return this.componentsView;
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#makeActions()
 */
void makeActions() {

	super.makeActions();

	// Generate action
	this.generate = new GenerateAction();
	this.generate.setText("&Generate");

	// Update build actions
	boolean connected = this.preferences.getBoolean(IPerformancesConstants.PRE_DATABASE_CONNECTION, IPerformancesConstants.DEFAULT_DATABASE_CONNECTION);
	this.updateBuild = new UpdateBuildAction(false);
	this.updateBuild.setText("&Update from DB");
	this.updateBuild.setEnabled(connected);
//	this.forceUpdateBuild = new UpdateBuildAction(true);
//	this.forceUpdateBuild.setText("Force Update");

	// Update build action
	this.updateAllBuilds = new UpdateAllBuildsAction(false);
	this.updateAllBuilds.setText("&Update from DB (all)");
	this.updateAllBuilds.setEnabled(connected);
//	this.forceUpdateAllBuilds = new UpdateAllBuildsAction(true);
//	this.forceUpdateAllBuilds.setText("Force Update all");

	// Set filters default
	this.filterBaselineBuilds.setChecked(false);
	this.filterNightlyBuilds.setChecked(false);
}

/**
 * Reset the views.
 */
public void resetView() {

	// Look whether database constants has changed or not
	int eclipseVersion = this.preferences.getInt(IPerformancesConstants.PRE_ECLIPSE_VERSION, IPerformancesConstants.DEFAULT_ECLIPSE_VERSION);
	boolean connected = this.preferences.getBoolean(IPerformancesConstants.PRE_DATABASE_CONNECTION, IPerformancesConstants.DEFAULT_DATABASE_CONNECTION);
	String databaseLocation = this.preferences.get(IPerformancesConstants.PRE_DATABASE_LOCATION, IPerformancesConstants.NETWORK_DATABASE_LOCATION);
	final boolean sameVersion = DB_Results.getDbVersion().endsWith(Integer.toString(eclipseVersion));
	final boolean sameConnection = connected == DB_Results.DB_CONNECTION;
	final boolean sameDB = sameVersion && databaseLocation.equals(DB_Results.getDbLocation());
	if (sameConnection && sameDB) {
		// No database preferences has changed do nothing
		return;
	}

	// Update database constants
	boolean updated = DB_Results.updateDbConstants(connected, eclipseVersion, databaseLocation);
	if (!connected) {
		if (!updated) {
			MessageDialog.openError(this.shell, getTitleToolTip(), "Error while updating database results constants!\nOpen error log to see more details on this error");
		}
	} else if (updated) {
		StringBuffer message = new StringBuffer("Database connection has been correctly ");
		message.append( connected ? "opened." : "closed.");
		MessageDialog.openInformation(this.shell, getTitleToolTip(), message.toString());
	} else {
		MessageDialog.openError(this.shell, getTitleToolTip(), "The database connection cannot be established!\nOpen error log to see more details on this error");
		DB_Results.updateDbConstants(false, eclipseVersion, databaseLocation);
	}
	setTitleToolTip();
	getSiblingView().setTitleToolTip();

	// Refresh view
	if (sameVersion) {
		// Refresh only builds view as the sibling view (Components) contents is based on local data files contents
		this.results.resetBuildNames();
		refreshInput();
	} else {
		// Reset views content
		resetInput();
		getSiblingView().resetInput();

		// May be read local data now
		if (MessageDialog.openQuestion(this.shell, getTitleToolTip(), "Do you want to read local data right now?")) {
			changeDataDir();
		} else {
			this.dataDir = null;
			getSiblingView().dataDir = null;
		}
	}

	// Update actions
	this.updateBuild.setEnabled(connected);
	this.updateAllBuilds.setEnabled(connected);
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.ui.PerformancesView#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
 */
public void selectionChanged(SelectionChangedEvent event) {
	super.selectionChanged(event);

	// Update selected element
	Object selection = this.viewer.getSelection();
	int length = 0;
	if (selection instanceof IStructuredSelection) {
		Object[] elements = ((IStructuredSelection)selection).toArray();
		length = elements == null ? 0 : elements.length;
		this.buildsResults = new BuildResultsElement[length];
		if (length == 0) {
			this.updateAllBuilds.setText("&Update from DB (all)");
			return;
		}
		for (int i=0; i<length; i++) {
			this.buildsResults[i] = (BuildResultsElement) elements[i];
		}
	} else {
		return;
	}

	// Update update build action
//	boolean enableUpdateBuild = true;
//	boolean enableGenerate = true;
	int readBuilds = 0;
	for (int i=0; i<length; i++) {
		if (this.buildsResults[i].isRead()) {
//			enableUpdateBuild = false;
			readBuilds++;
		} else {
//			enableGenerate = false;
		}
	}
//	this.updateBuild.setEnabled(enableUpdateBuild);
//	this.forceUpdateBuild.setEnabled(!enableUpdateBuild);
	final boolean force = readBuilds < length;
	this.updateBuild.force = force;
	this.updateAllBuilds.force = force;
	this.updateAllBuilds.setText("&Update from DB");

	// Update generate action
	boolean enableGenerate = !force;
	if (enableGenerate) {
		for (int i=0; i<length; i++) {
			if (this.buildsResults[i].getName().startsWith(DB_Results.getDbBaselinePrefix())) {
				enableGenerate = false;
				break;
			}
		}
	}
	this.generate.setEnabled(enableGenerate);
}

void updateAllBuilds(IProgressMonitor monitor, boolean force) {
	if (this.dataDir == null) {
		changeDataDir();
	}
	String[] builds = buildsToUpdate();
	if (builds == null) {
		this.results.updateBuild(null, true, this.dataDir, monitor);
	} else {
		this.results.updateBuilds(builds, force, this.dataDir, monitor);
	}
}

void updateBuilds(IProgressMonitor monitor, boolean force) {
	if (this.dataDir == null) {
		changeDataDir();
	}
	int length = this.buildsResults.length;
	String[] builds = new String[length];
	for (int i = 0; i < length; i++) {
		builds[i] = this.buildsResults[i].getName();
	}
	this.results.updateBuilds(builds, force, this.dataDir, monitor);
}

}