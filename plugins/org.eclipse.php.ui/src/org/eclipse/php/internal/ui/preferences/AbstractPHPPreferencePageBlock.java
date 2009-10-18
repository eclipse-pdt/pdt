/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.ui.preferences.IPHPPreferencePageBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * An AbstractPHPPreferencePageBlock supplies some of the basic functionalities
 * to ease up the creation of subclassing {@link IPHPPreferencePageBlock}.
 * 
 * @author shalom
 */
public abstract class AbstractPHPPreferencePageBlock implements
		IPHPPreferencePageBlock {

	private String comparableName;

	protected Composite addPageContents(Composite composite) {
		composite = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return composite;
	}

	protected Composite createSubsection(Composite parent, String label) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		return group;
	}

	protected Button addCheckBox(Composite parent, String label,
			String prefKey, int horizontalIndent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(label);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalIndent = horizontalIndent;
		gd.horizontalSpan = 3;

		checkBox.setLayoutData(gd);
		checkBox.setData(prefKey);
		return checkBox;
	}

	protected Label addLabelControl(Composite parent, String label, String key) {
		Label labelControl = new Label(parent, SWT.WRAP);
		labelControl.setText(label);
		labelControl.setData(key);
		labelControl.setLayoutData(new GridData());
		return labelControl;
	}

	protected IScopeContext[] createPreferenceScopes(PreferencePage propertyPage) {
		IProject project = getProject(propertyPage);
		if (project != null) {
			return new IScopeContext[] { new ProjectScope(project),
					new InstanceScope(), new DefaultScope() };
		}
		return new IScopeContext[] { new InstanceScope(), new DefaultScope() };
	}

	protected IProject getProject(PreferencePage preferencePage) {
		if (preferencePage == null) {
			return null;
		}
		PropertyPage propertyPage = (PropertyPage) preferencePage;
		IAdaptable element = propertyPage.getElement();
		if (element != null) {
			if (element instanceof IProject) {
				return (IProject) element;
			} else if (element instanceof IScriptProject) {
				return ((IScriptProject) element).getProject();
			}
		}
		return null;
	}

	public void setComparableName(String name) {
		this.comparableName = name;
	}

	public String getComparableName() {
		return comparableName;
	}
}
