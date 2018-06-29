/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view;

import java.text.MessageFormat;

import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * A panel with counters for the number of Runs, Errors and Failures.
 */
public class CounterPanel extends Composite {
	private final Image fErrorIcon = PHPUnitPlugin.createImage("ovr16/error_ovr.png"); //$NON-NLS-1$
	private final Image fFailureIcon = PHPUnitPlugin.createImage("ovr16/failed_ovr.png"); //$NON-NLS-1$
	protected Text fNumberOfErrors;
	protected Text fNumberOfFailures;

	protected Text fNumberOfRuns;
	protected int fTotal;

	public CounterPanel(final Composite parent) {
		super(parent, SWT.WRAP);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 9;
		gridLayout.makeColumnsEqualWidth = false;
		gridLayout.marginWidth = 0;
		setLayout(gridLayout);

		fNumberOfRuns = createLabel(PHPUnitMessages.CounterPanel_Runs, null, " 0/0  "); //$NON-NLS-1$
		fNumberOfErrors = createLabel(PHPUnitMessages.CounterPanel_Errors, fErrorIcon, " 0 "); //$NON-NLS-1$
		fNumberOfFailures = createLabel(PHPUnitMessages.CounterPanel_Failures, fFailureIcon, " 0 "); //$NON-NLS-1$

		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(final DisposeEvent e) {
				disposeIcons();
			}
		});
	}

	private Text createLabel(final String name, final Image image, final String init) {
		Label label = new Label(this, SWT.NONE);
		if (image != null) {
			image.setBackground(label.getBackground());
			label.setImage(image);
		}
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		label = new Label(this, SWT.NONE);
		label.setText(name);
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		final Text value = new Text(this, SWT.READ_ONLY);
		value.setText(init);

		value.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		value.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_BEGINNING));
		return value;
	}

	private void disposeIcons() {
		fErrorIcon.dispose();
		fFailureIcon.dispose();
	}

	public int getTotal() {
		return fTotal;
	}

	public void reset() {
		fTotal = 0;
		setErrorValue(0);
		setFailureValue(0);
		setRunValue(0);
	}

	public void setErrorValue(final int value) {
		fNumberOfErrors.setText(Integer.toString(value));
		redraw();
	}

	public void setFailureValue(final int value) {
		fNumberOfFailures.setText(Integer.toString(value));
		redraw();
	}

	public void setRunValue(final int value) {
		final String runString = MessageFormat.format("{0}/{1}", new Object[] { value, fTotal }); //$NON-NLS-1$
		fNumberOfRuns.setText(runString);

		fNumberOfRuns.redraw();
		redraw();
	}

	public void setTotal(final int value) {
		fTotal = value;
	}
}
