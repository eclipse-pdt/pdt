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
package org.eclipse.test.internal.performance.results.model;

import java.io.File;
import java.util.Arrays;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.test.internal.performance.results.db.*;
import org.eclipse.test.internal.performance.results.utils.Util;

public class PerformanceResultsElement extends ResultsElement {

// Singleton pattern
public static PerformanceResultsElement PERF_RESULTS_MODEL = new PerformanceResultsElement();

	String[] buildNames;
	boolean fingerprints = true;

public PerformanceResultsElement() {
	super();
}

ResultsElement createChild(AbstractResults testResults) {
	return new ComponentResultsElement(testResults, this);
}

public String[] getBaselines() {
	getBuildNames();
	if (this.buildNames == null) {
		return new String[0];
	}
	int length = this.buildNames.length;
	String[] baselines = new String[length];
	int count = 0;
	for (int i=0; i<length; i++) {
		if (this.buildNames[i].startsWith("R-")) {
			baselines[count++] = this.buildNames[i];
		}
	}
	if (count < length) {
		System.arraycopy(baselines, 0, baselines = new String [count], 0, count);
	}
	return baselines;
}

String[] getBuildNames() {
	if (this.buildNames == null) {
		this.buildNames = DB_Results.DB_CONNECTION
			? DB_Results.getBuilds()
			: this.results == null
				? new String[0]
				: getPerformanceResults().getAllBuildNames();
	}
	return this.buildNames;
}

public Object[] getBuilds() {
	getBuildNames();
	int length = this.buildNames == null ? 0 : this.buildNames.length;
	BuildResultsElement[] elements = new BuildResultsElement[length];
	for (int i=0; i<length; i++) {
		elements[i] = new BuildResultsElement(this.buildNames[i], this);
	}
	return elements;
}

public String[] getComponents() {
	if (!isInitialized()) {
		String[] components = DB_Results.getComponents();
		int length = components.length;
		if (length == 0) {
			DB_Results.queryAllScenarios();
			components = DB_Results.getComponents();
		}
		return components;
	}
	return getPerformanceResults().getComponents();
}

/**
 * Returns the names of the configurations.
 *
 * @return An array of String
 */
public String[] getConfigs() {
	if (!isInitialized()) {
		String[] configs = DB_Results.getConfigs();
		int length = configs.length;
		if (length == 0) {
			DB_Results.queryAllScenarios();
			configs = DB_Results.getConfigs();
		}
		return configs;
	}
	return getPerformanceResults().getConfigNames(false);
}

/**
 * Returns the descriptions of the configurations.
 *
 * @return An array of String
 */
public String[] getConfigDescriptions() {
	if (!isInitialized()) {
		String[] descriptions = DB_Results.getConfigDescriptions();
		int length = descriptions.length;
		if (length == 0) {
			DB_Results.queryAllScenarios();
			descriptions = DB_Results.getConfigDescriptions();
		}
		return descriptions;
	}
	return getPerformanceResults().getConfigBoxes(false);
}

public Object[] getElements() {
	if (!isInitialized()) {
		String[] components = getComponents();
		int length = components.length;
		ComponentResultsElement[] elements = new ComponentResultsElement[length];
		for (int i=0; i<length; i++) {
			elements[i] = new ComponentResultsElement(components[i], this);
		}
		return elements;
	}
	return getChildren(null);
}

public PerformanceResults getPerformanceResults() {
	return (PerformanceResults) this.results;
}

boolean hasRead(BuildResultsElement buildResultsElement) {
	String[] builds = this.results == null ? getBuildNames() : getPerformanceResults().getAllBuildNames();
	if (Arrays.binarySearch(builds, buildResultsElement.getName(), Util.BUILD_DATE_COMPARATOR) < 0) {
		return false;
	}
	return true;
}

public boolean isInitialized() {
	return super.isInitialized() && this.results.size() > 0;
}

public void readLocal(File dataDir, IProgressMonitor monitor) {
	reset(null);
	getPerformanceResults().readLocal(dataDir, monitor);
}

public void reset(String buildName) {
	if (buildName == null) {
		this.results = new PerformanceResults(System.out);
	} else {
		this.results = new PerformanceResults(buildName, null, null, System.out);
	}
	this.children = null;
	this.buildNames = null;
}

public void resetBuildNames() {
	this.buildNames = null;
}

public void updateBuild(String buildName, boolean force, File dataDir, IProgressMonitor monitor) {
	if (this.results == null) {
		reset(buildName);
	}
	getPerformanceResults().updateBuild(buildName, force, dataDir, monitor);
}

public void updateBuilds(String[] builds, boolean force, File dataDir, IProgressMonitor monitor) {
	if (this.results == null) {
		reset(null);
	}
	getPerformanceResults().updateBuilds(builds, force, dataDir, monitor);
}

/**
 * Set whether only fingerprints should be taken into account or not.
 *
 * @param fingerprints
 */
public void setFingerprints(boolean fingerprints) {
	this.fingerprints = fingerprints;
	resetStatus();
}

}
