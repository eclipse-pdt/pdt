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

import java.util.Vector;

import org.eclipse.test.internal.performance.results.db.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ScenarioResultsElement extends ResultsElement {

	// Property descriptors
    static final String P_ID_SCENARIO_LABEL = "ScenarioResultsElement.label"; //$NON-NLS-1$
    static final String P_ID_SCENARIO_FILE_NAME = "ScenarioResultsElement.filename"; //$NON-NLS-1$
    static final String P_ID_SCENARIO_SHORT_NAME = "ScenarioResultsElement.shortname"; //$NON-NLS-1$

    static final String P_STR_SCENARIO_LABEL = "label"; //$NON-NLS-1$
    static final String P_STR_SCENARIO_FILE_NAME = "file name"; //$NON-NLS-1$
    static final String P_STR_SCENARIO_SHORT_NAME = "short name"; //$NON-NLS-1$

    private static final TextPropertyDescriptor SCENARIO_LABEL_DESCRIPTOR = new TextPropertyDescriptor(P_ID_SCENARIO_LABEL, P_STR_SCENARIO_LABEL);
	private static final TextPropertyDescriptor SCENARIO_FILE_NAME_DESCRIPTOR = new TextPropertyDescriptor(P_ID_SCENARIO_FILE_NAME, P_STR_SCENARIO_FILE_NAME);
	private static final TextPropertyDescriptor SCENARIO_SHORT_NAME_DESCRIPTOR = new TextPropertyDescriptor(P_ID_SCENARIO_SHORT_NAME, P_STR_SCENARIO_SHORT_NAME);

    private static Vector<PropertyDescriptor> DESCRIPTORS;
    static Vector<PropertyDescriptor> initDescriptors(int status) {
        DESCRIPTORS = new Vector<>();
		// Status category
		DESCRIPTORS.add(getInfosDescriptor(status));
		DESCRIPTORS.add(getWarningsDescriptor(status));
		DESCRIPTORS.add(ERROR_DESCRIPTOR);
		ERROR_DESCRIPTOR.setCategory("Status");
		// Results category
        DESCRIPTORS.addElement(SCENARIO_LABEL_DESCRIPTOR);
		SCENARIO_LABEL_DESCRIPTOR.setCategory("Results");
        DESCRIPTORS.addElement(SCENARIO_FILE_NAME_DESCRIPTOR);
		SCENARIO_FILE_NAME_DESCRIPTOR.setCategory("Results");
        DESCRIPTORS.addElement(SCENARIO_SHORT_NAME_DESCRIPTOR);
		SCENARIO_SHORT_NAME_DESCRIPTOR.setCategory("Results");
		// Survey category
		DESCRIPTORS.add(COMMENT_DESCRIPTOR);
		COMMENT_DESCRIPTOR.setCategory("Survey");
        return DESCRIPTORS;
	}
    static Vector<PropertyDescriptor> getDescriptors() {
    	return DESCRIPTORS;
	}

ScenarioResultsElement(AbstractResults results, ResultsElement parent) {
    super(results, parent);
}

@Override
ResultsElement createChild(AbstractResults testResults) {
	return new ConfigResultsElement(testResults, this);
}

@Override
public String getLabel(Object o) {
	return ((ScenarioResults) this.results).getShortName();
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
	ScenarioResults scenarioResults = (ScenarioResults) this.results;
    if (propKey.equals(P_ID_SCENARIO_LABEL))
        return scenarioResults.getLabel();
    if (propKey.equals(P_ID_SCENARIO_FILE_NAME))
        return scenarioResults.getFileName();
    if (propKey.equals(P_ID_SCENARIO_SHORT_NAME))
        return scenarioResults.getShortName();
    return super.getPropertyValue(propKey);
}

@Override
void initStatus() {
	if (onlyFingerprints()) {
		if (hasSummary()) {
			super.initStatus();
		} else {
			this.status = READ;
		}
	} else {
		super.initStatus();
	}
}
/**
 * Returns whether one of the scenario's config has a summary or not.
 *
 * @return <code>true</code> if one of the scenario's config has a summary
 * 	<code>false</code> otherwise.
 */
public boolean hasSummary() {
	if (this.results == null) return false;
	return ((ScenarioResults)this.results).hasSummary();
}

}
