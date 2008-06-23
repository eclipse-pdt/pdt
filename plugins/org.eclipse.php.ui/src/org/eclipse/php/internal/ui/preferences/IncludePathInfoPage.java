/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences;

import org.eclipse.core.runtime.IPath;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPIncludePathModel;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.internal.dialogs.AdaptableForwarder;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

public class IncludePathInfoPage extends PropertyPage implements IWorkbenchPropertyPage {


	private static String TYPE_TITLE = IDEWorkbenchMessages.ResourceInfo_type;

	private static String LOCATION_TITLE = IDEWorkbenchMessages.ResourceInfo_location;

	private static String PATH_TITLE = PHPUIMessages.getString("IncludePathInfoPage.Identifier");

	// Max value width in characters before wrapping
	private static final int MAX_VALUE_WIDTH = 80;

	/**
	 * Create the group that shows the name, location, size and type.
	 * 
	 * @param parent
	 *            the composite the group will be created in
	 * @param ipModel
	 *            the resource the information is being taken from.
	 * @return the composite for the group
	 */
	private Composite createBasicInfoGroup(Composite parent, PHPIncludePathModel ipModel) {

		Font font = parent.getFont();

		Composite basicInfoComposite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		basicInfoComposite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		basicInfoComposite.setLayoutData(data);
		basicInfoComposite.setFont(font);

		// The group for path
		Label pathLabel = new Label(basicInfoComposite, SWT.NONE);
		pathLabel.setText(PATH_TITLE);
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		pathLabel.setLayoutData(gd);
		pathLabel.setFont(font);

		// path value label
		Text pathValueText = new Text(basicInfoComposite, SWT.WRAP | SWT.READ_ONLY);
		IPath modelLocation = PHPModelUtil.getIncludeModelLocation(ipModel);

		String pathString = TextProcessor.process(modelLocation.toString());

		pathValueText.setText(ipModel.getID());
		gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(MAX_VALUE_WIDTH);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		pathValueText.setLayoutData(gd);
		pathValueText.setFont(font);
		pathValueText.setBackground(pathValueText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		// The group for types
		Label typeTitle = new Label(basicInfoComposite, SWT.LEFT);
		typeTitle.setText(TYPE_TITLE);
		typeTitle.setFont(font);

		Text typeValue = new Text(basicInfoComposite, SWT.LEFT | SWT.READ_ONLY);
		typeValue.setText(ipModel.getType().toString());
		typeValue.setBackground(typeValue.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		typeValue.setFont(font);

		// The group for location
		Label locationTitle = new Label(basicInfoComposite, SWT.LEFT);
		locationTitle.setText(LOCATION_TITLE);
		gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		locationTitle.setLayoutData(gd);
		locationTitle.setFont(font);

		Text locationValue = new Text(basicInfoComposite, SWT.WRAP | SWT.READ_ONLY);
		String locationStr = TextProcessor.process(pathString);
		locationValue.setText(locationStr);
		gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(MAX_VALUE_WIDTH);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		locationValue.setLayoutData(gd);
		locationValue.setFont(font);
		locationValue.setBackground(locationValue.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

		gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(MAX_VALUE_WIDTH);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;

		return basicInfoComposite;
	}

	protected Control createContents(Composite parent) {

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IIDEHelpContextIds.RESOURCE_INFO_PROPERTY_PAGE);

		// layout the page
		AdaptableForwarder adapter = (AdaptableForwarder) getElement();
		final PHPIncludePathModel ipModel = (PHPIncludePathModel) adapter.getAdapter(PHPIncludePathModel.class);

		if (ipModel == null) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(IDEWorkbenchMessages.ResourceInfoPage_noResource);
			return label;
		}

		// top level group
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());

		createBasicInfoGroup(composite, ipModel);
		createSeparator(composite);
		new Label(composite, SWT.NONE); // a vertical spacer

		return composite;
	}


	/**
	 * Create a separator that goes across the entire page
	 * 
	 * @param composite
	 *            The parent of the seperator
	 */
	private void createSeparator(Composite composite) {

		Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	/**
	 * Apply nothing
	 */
	public boolean performOk() {
		return true;
	}

}
