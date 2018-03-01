/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class SharedPartWithButtons extends SharedPart {
	private String[] fButtonLabels;
	private Button[] fButtons;
	protected Composite fButtonContainer;

	private class SelectionHandler implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			buttonSelected(e);
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			buttonSelected(e);
		}

		private void buttonSelected(SelectionEvent e) {
			Integer index = (Integer) e.widget.getData();
			SharedPartWithButtons.this.buttonSelected((Button) e.widget, index.intValue());
		}
	}

	public SharedPartWithButtons(String[] buttonLabels) {
		fButtonLabels = buttonLabels;
	}

	public void setButtonEnabled(int index, boolean enabled) {
		if (fButtons != null && index >= 0 && fButtons.length > index) {
			fButtons[index].setEnabled(enabled);
		}
	}

	/**
	 * Set the specified button's visibility. Fix for defect 190717.
	 * 
	 * @param index
	 *            The index of the button to be changed
	 * @param visible
	 *            true if the button is to be shown, false if hidden
	 */
	public void setButtonVisible(int index, boolean visible) {
		if (fButtons != null && index >= 0 && fButtons.length > index) {
			fButtons[index].setVisible(visible);
		}
	}

	protected abstract void createMainControl(Composite parent, int style, int span, FormToolkit toolkit);

	protected abstract void buttonSelected(Button button, int index);

	/*
	 * @see SharedPart#createControl(Composite, FormWidgetFactory)
	 */
	@Override
	public void createControl(Composite parent, int style, int span, FormToolkit toolkit) {
		createMainLabel(parent, span, toolkit);
		createMainControl(parent, style, span - 1, toolkit);
		createButtons(parent, toolkit);
	}

	protected void createButtons(Composite parent, FormToolkit toolkit) {
		if (fButtonLabels != null && fButtonLabels.length > 0) {
			fButtonContainer = createComposite(parent, toolkit);
			GridData gd = new GridData(GridData.FILL_VERTICAL);
			fButtonContainer.setLayoutData(gd);
			fButtonContainer.setLayout(createButtonsLayout());
			fButtons = new Button[fButtonLabels.length];
			SelectionHandler listener = new SelectionHandler();
			for (int i = 0; i < fButtonLabels.length; i++) {
				String label = fButtonLabels[i];
				if (label != null) {
					Button button = createButton(fButtonContainer, label, i, toolkit);
					button.addSelectionListener(listener);
					fButtons[i] = button;
				} else {
					createEmptySpace(fButtonContainer, 1, toolkit);
				}
			}
		}
	}

	protected GridLayout createButtonsLayout() {
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		return layout;
	}

	protected Button createButton(Composite parent, String label, int index, FormToolkit toolkit) {
		Button button;
		if (toolkit != null) {
			button = toolkit.createButton(parent, label, SWT.PUSH);
		} else {
			button = new Button(parent, SWT.PUSH);
			button.setText(label);
		}
		GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
		button.setLayoutData(gd);
		button.setData(new Integer(index));

		// Set the default button size
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter = new PixelConverter(button);
		int widthHint = converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		gd.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);

		return button;
	}

	@Override
	protected void updateEnabledState() {
		for (int i = 0; i < fButtons.length; i++) {
			fButtons[i].setEnabled(isEnabled());
		}
	}

	protected void createMainLabel(Composite parent, int span, FormToolkit toolkit) {
	}

	public Button getButton(int index) {
		//
		if ((fButtons == null) || (index < 0) || (index >= fButtons.length)) {
			return null;
		}
		//
		return fButtons[index];
	}
}