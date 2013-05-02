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
package org.eclipse.php.internal.server.ui.launching.zend;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

public class DefaultServerTestMessageDialog extends MessageDialog {

	@Override
	protected Control createMessageArea(Composite composite) {
		// create composite
		// create image
		Image image = getImage();
		if (image != null) {
			imageLabel = new Label(composite, SWT.NULL);
			image.setBackground(imageLabel.getBackground());
			imageLabel.setImage(image);
			addAccessibleListeners(imageLabel, image);
			GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING)
					.applyTo(imageLabel);
		}
		// create message
		if (message != null) {
			Composite labelComposite = new Composite(composite, SWT.NULL);
			labelComposite.setLayout(new GridLayout(1, true));
			messageLabel = new Label(labelComposite, getMessageLabelStyle());
			messageLabel.setText(message);

			addLinksToMessage(labelComposite);
		}
		return composite;

	}

	protected void addLinksToMessage(Composite labelComposite) {
		Link link = new Link(labelComposite, SWT.NONE);
		link.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		link
				.setText(org.eclipse.php.internal.server.ui.launching.zend.JFaceResources.DefaultServerTestMessageDialog_0); 
		link.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				PlatformUI.getWorkbench().getHelpSystem().displayHelp(
						IPHPHelpContextIds.SETTING_UP_REMOTE_DEBUGGING);
			}
		});
	}

	public DefaultServerTestMessageDialog(Shell parentShell,
			String dialogTitle, Image dialogTitleImage, String dialogMessage,
			int dialogImageType, String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
	}

	/**
	 * Add an accessible listener to the label if it can be inferred from the
	 * image.
	 * 
	 * @param label
	 * @param image
	 */
	private void addAccessibleListeners(Label label, final Image image) {
		label.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent event) {
				final String accessibleMessage = getAccessibleMessageFor(image);
				if (accessibleMessage == null) {
					return;
				}
				event.result = accessibleMessage;
			}
		});
	}

	private String getAccessibleMessageFor(Image image) {
		if (image.equals(getErrorImage())) {
			return JFaceResources.getString("error"); //$NON-NLS-1$
		}

		if (image.equals(getWarningImage())) {
			return JFaceResources.getString("warning"); //$NON-NLS-1$
		}

		if (image.equals(getInfoImage())) {
			return JFaceResources.getString("info"); //$NON-NLS-1$
		}

		if (image.equals(getQuestionImage())) {
			return JFaceResources.getString("question"); //$NON-NLS-1$
		}

		return null;
	}

}
