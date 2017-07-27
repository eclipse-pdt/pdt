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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.test.internal.performance.results.db.AbstractResults;
import org.eclipse.test.internal.performance.results.db.ComponentResults;
import org.eclipse.test.internal.performance.results.db.PerformanceResults;
import org.eclipse.test.internal.performance.results.db.ScenarioResults;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ComponentResultsElement extends ResultsElement {

	// Property descriptors
	static final String P_ID_NAME = "ComponentResultsElement.name"; //$NON-NLS-1$
	static final String P_ID_CURRENT_BUILD = "ComponentResultsElement.currentbuild"; //$NON-NLS-1$
	static final String P_ID_BASELINE_BUILD = "ComponentResultsElement.baselinebuild"; //$NON-NLS-1$

	static final String P_STR_NAME = "name"; //$NON-NLS-1$
	static final String P_STR_CURRENT_BUILD = "current build"; //$NON-NLS-1$
	static final String P_STR_BASELINE_BUILD = "baseline build"; //$NON-NLS-1$

	private static final TextPropertyDescriptor NAME_DESCRIPTOR = new TextPropertyDescriptor(P_ID_NAME, P_STR_NAME);
	private static final PropertyDescriptor CURRENT_BUILD_DESCRIPTOR = new PropertyDescriptor(P_ID_CURRENT_BUILD, P_STR_CURRENT_BUILD);
	private static final PropertyDescriptor BASELINE_BUILD_DESCRIPTOR = new PropertyDescriptor(P_ID_BASELINE_BUILD, P_STR_BASELINE_BUILD);

    private static Vector<PropertyDescriptor> DESCRIPTORS;
    static Vector<PropertyDescriptor> initDescriptors(int status) {
        DESCRIPTORS = new Vector<>();
		// Status category
		DESCRIPTORS.add(getInfosDescriptor(status));
		DESCRIPTORS.add(getWarningsDescriptor(status));
		DESCRIPTORS.add(ERROR_DESCRIPTOR);
		ERROR_DESCRIPTOR.setCategory("Status");
		// Results category
		DESCRIPTORS.addElement(NAME_DESCRIPTOR);
		NAME_DESCRIPTOR.setCategory("Results");
		DESCRIPTORS.addElement(CURRENT_BUILD_DESCRIPTOR);
		CURRENT_BUILD_DESCRIPTOR.setCategory("Results");
		DESCRIPTORS.addElement(BASELINE_BUILD_DESCRIPTOR);
		BASELINE_BUILD_DESCRIPTOR.setCategory("Results");
		// Survey category
		DESCRIPTORS.add(COMMENT_DESCRIPTOR);
		COMMENT_DESCRIPTOR.setCategory("Survey");
        return DESCRIPTORS;
	}
    static Vector<PropertyDescriptor> getDescriptors() {
    	return DESCRIPTORS;
	}

public ComponentResultsElement(String name, ResultsElement parent) {
	super(name, parent);
}

public ComponentResultsElement(AbstractResults results, ResultsElement parent) {
	super(results, parent);
}

/*
 * Do not create non-fingerprint child when only fingerprint is specified.
 *
 * @see org.eclipse.test.internal.performance.results.model.ResultsElement#createChild(org.eclipse.test.internal.performance.results.db.AbstractResults)
 */
@Override
ResultsElement createChild(AbstractResults testResults) {
//	if (onlyFingerprints()) {
//		ScenarioResults scenarioResults = (ScenarioResults) testResults;
//		if (!scenarioResults.hasSummary()) {
//			return null;
//		}
//	}
	return new ScenarioResultsElement(testResults, this);
}

/**
 * Get all results numbers for a given machine of the current component.
 *
 * @param configName The name of the configuration to get numbers
 * @param fingerprints Set whether only fingerprints scenario should be taken into account
 * @return A list of lines. Each line represent a build and is a list of either strings or values.
 */
public List<?> getConfigNumbers(String configName, boolean fingerprints) {
	if (this.results == null) return null;
	return ((ComponentResults)this.results).getConfigNumbers(configName, fingerprints, new ArrayList<>());
}

/* (non-Javadoc)
 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
 */
@Override
public IPropertyDescriptor[] getPropertyDescriptors() {
	Vector<PropertyDescriptor> descriptors = getDescriptors();
	if (descriptors == null) {
		descriptors = initDescriptors(getStatus());
	}
	int size = descriptors.size();
	IPropertyDescriptor[] descriptorsArray = new IPropertyDescriptor[size];
	descriptorsArray[0] = getInfosDescriptor(getStatus());
	descriptorsArray[1] = getWarningsDescriptor(getStatus());
	for (int i=2; i<size; i++) {
		descriptorsArray[i] = descriptors.get(i);
	}
	return descriptorsArray;
}

/* (non-Javadoc)
 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
 */
@Override
public Object getPropertyValue(Object propKey) {
	if (propKey.equals(P_ID_NAME)) {
		return getName();
	}
	if (propKey.equals(P_ID_CURRENT_BUILD)) {
		if (this.results == null) {
			PerformanceResultsElement performanceResultsElement = (PerformanceResultsElement) getParent(null);
			return performanceResultsElement.getName();
		}
		PerformanceResults performanceResults = (PerformanceResults) this.results.getParent();
		return performanceResults.getName();
	}
	if (propKey.equals(P_ID_BASELINE_BUILD)) {
		if (this.results == null) {
			return "?";
		}
		PerformanceResults performanceResults = (PerformanceResults) this.results.getParent();
		return performanceResults.getBaselineName();
	}
    return super.getPropertyValue(propKey);
}

/**
 * Get the list of the scenarios results from the model. Put only fingerprint ones if specified.
 *
 * @param fingerprint Tell whether only fingerprint scenarios are expected or not.
 * @return A list of {@link ScenarioResults}.
 */
public List<AbstractResults> getScenarios(boolean fingerprint) {
	if (!fingerprint) {
		return Arrays.asList(this.results.getChildren());
	}
	List<AbstractResults> scenarios = new ArrayList<>();
	if (this.results != null) {
		Iterator<?> iterator = this.results.getResults();
		while (iterator.hasNext()) {
			ScenarioResults scenarioResults = (ScenarioResults) iterator.next();
			if (scenarioResults.hasSummary()) {
				scenarios.add(scenarioResults);
			}
		}
	}
	return scenarios;
}

/**
 * Get the list of the scenarios names. Put only fingerprint ones if specified.
 *
 * @param fingerprint Tell whether only fingerprint scenarios are expected or not.
 * @return A list of {@link String}.
 */
public List<String> getScenariosLabels(boolean fingerprint) {
	List<String> labels = new ArrayList<>();
	if (this.results != null) {
		AbstractResults[] scenarios = this.results.getChildren();
		int length = scenarios.length;
		for (int i=0; i<length; i++) {
			ScenarioResults scenarioResults = (ScenarioResults) scenarios[i];
			if (!fingerprint || scenarioResults.hasSummary()) {
				labels.add(scenarioResults.getLabel());
			}
		}
	}
	return labels;
}

/*
 * (non-Javadoc)
 * @see org.eclipse.test.internal.performance.results.model.ResultsElement#initStatus()
 */
@Override
void initStatus() {
	if (this.results == null) {
		this.status = UNREAD;
	} else {
		super.initStatus();
	}
}

}
