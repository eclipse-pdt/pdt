/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.ui.util;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * Abstract implementation of a message dialog that gives possibility to provide
 * message text with support for links.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class LinkMessageDialog extends MessageDialog {

	private String dialogMessage;

	/**
	 * Creates new dialog.
	 * 
	 * @param parentShell
	 * @param dialogTitle
	 * @param dialogTitleImage
	 * @param dialogMessage
	 * @param dialogImageType
	 * @param dialogButtonLabels
	 * @param defaultIndex
	 */
	public LinkMessageDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage,
			int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex);
		this.dialogMessage = dialogMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IconAndMessageDialog#createMessageArea(org.
	 * eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createMessageArea(Composite composite) {
		// Create image
		Image image = getImage();
		if (image != null) {
			imageLabel = new Label(composite, SWT.NULL);
			image.setBackground(imageLabel.getBackground());
			imageLabel.setImage(image);
			imageLabel
					.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_BEGINNING));
		}
		// Create message with link
		if (message != null) {
			Link messageLabel = new Link(composite, getMessageLabelStyle());
			messageLabel.setFont(composite.getFont());
			messageLabel.setText(dialogMessage);
			messageLabel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					linkActivated();
				}
			});
			GridData data = new GridData(
					GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			messageLabel.setLayoutData(data);
		}
		return composite;
	}

	/**
	 * Implementors should perform an appropriate action for activated link.
	 */
	protected abstract void linkActivated();

}
