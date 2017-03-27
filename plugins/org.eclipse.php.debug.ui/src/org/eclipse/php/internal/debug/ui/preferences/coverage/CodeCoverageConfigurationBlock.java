/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.preferences.coverage;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.views.coverage.CodeCoverageTextViewer;
import org.eclipse.php.internal.ui.preferences.IPreferenceConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.ScrolledCompositeImpl;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PreferenceLinkArea;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * Code coverage preferences configuration block.
 */
public class CodeCoverageConfigurationBlock implements IPreferenceConfigurationBlock {

	private PreferencePage fPreferencePage;
	private CodeCoverageTextViewer fCodeCoverageViewer;

	public CodeCoverageConfigurationBlock(PreferencePage preferencePage) {
		fPreferencePage = preferencePage;
	}

	public Control createControl(Composite parent) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite composite = new Composite(scrolledCompositeImpl, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		scrolledCompositeImpl.setContent(composite);
		scrolledCompositeImpl.setLayout(layout);
		scrolledCompositeImpl.setFont(parent.getFont());
		PreferenceLinkArea colorsFontsLink = new PreferenceLinkArea(composite, SWT.NONE,
				"org.eclipse.ui.preferencePages.ColorsAndFonts", PHPDebugUIMessages.CodeCoverageConfigurationBlock_1,
				(IWorkbenchPreferenceContainer) fPreferencePage.getContainer(), null);
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		colorsFontsLink.getControl().setLayoutData(data);
		Composite previewComposite = new Composite(composite, SWT.NONE);
		layout = new GridLayout();
		layout.marginTop = 20;
		previewComposite.setLayout(layout);
		previewComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label label = new Label(previewComposite, SWT.NONE);
		label.setText(PHPDebugUIMessages.CodeCoverageConfigurationBlock_2);
		fCodeCoverageViewer = new CodeCoverageTextViewer(previewComposite, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = new PixelConverter(previewComposite).convertHeightInCharsToPixels(15);
		fCodeCoverageViewer.setLayoutData(data);
		fCodeCoverageViewer.setCoverageBitmask(new byte[] { 33, 0 });
		fCodeCoverageViewer.setSingificanceBitmask(new byte[] { 40, 0 });
		fCodeCoverageViewer.setText(PHPDebugUIMessages.CodeCoverageConfigurationBlock_3);
		return scrolledCompositeImpl;
	}

	public void dispose() {
	}

	public void initialize() {
	}

	public void performDefaults() {
	}

	public void performOk() {
	}
}
