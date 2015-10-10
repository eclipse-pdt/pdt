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
/**
 * 
 */
package org.eclipse.php.internal.debug.core.preferences;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A basic abstract implementation of a debugger configuration dialog. Debugger
 * configuration dialogs can extend this class and use its functionalities.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public abstract class AbstractDebuggerConfigurationDialog extends TitleAreaDialog {

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 */
	public AbstractDebuggerConfigurationDialog(Shell parentShell) {
		super(parentShell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.TitleAreaDialog#setMessage(java.lang.String,
	 * int)
	 */
	@Override
	public void setMessage(String newMessage, int newType) {
		super.setMessage(newMessage, newType);
		boolean enableOK = true;
		switch (newType) {
		case IMessageProvider.ERROR:
			enableOK = false;
			break;
		default:
			break;
		}
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (okButton != null) {
			okButton.setEnabled(enableOK);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#getInitialSize()
	 */
	protected Point getInitialSize() {
		Point p = super.getInitialSize();
		p.x -= 200;
		return p;
	}

	/**
	 * Creates a subsection group.
	 * 
	 * @param parent
	 * @param label
	 * @return
	 */
	protected Composite createSubsection(Composite parent, String label) {
		// A cosmetic composite that will add a basic indent
		parent = new Composite(parent, SWT.NONE);
		parent.setLayout(new GridLayout(1, true));
		GridData data = new GridData(GridData.FILL_BOTH);
		parent.setLayoutData(data);

		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(label);
		data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		group.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		return group;
	}

	/**
	 * Adds a {@link Label} control.
	 * 
	 * @param parent
	 * @param label
	 * @param key
	 *            A preferences key that will be stored in the control's data.
	 * @return
	 */
	protected Label addLabelControl(Composite parent, String label, String key) {
		Label labelControl = new Label(parent, SWT.WRAP);
		labelControl.setText(label);
		labelControl.setData(key);
		labelControl.setLayoutData(new GridData());
		return labelControl;
	}

	/**
	 * Adds a text field.
	 * 
	 * @param parent
	 * @param key
	 *            A preferences key that will be added to the control's data.
	 * @param textlimit
	 * @param horizontalIndent
	 * 
	 * @return
	 */
	protected Text addTextField(Composite parent, String key, int textlimit, int horizontalIndent) {
		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		if (textlimit != 0) {
			textBox.setTextLimit(textlimit);
		}
		data.horizontalIndent = horizontalIndent;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.minimumWidth = 60;
		textBox.setLayoutData(data);
		return textBox;
	}

	/**
	 * Adds a checkbox control.
	 * 
	 * @param parent
	 * @param label
	 * @param prefKey
	 *            A preferences key that will be stored in the control's data.
	 * @param horizontalIndent
	 * @return
	 */
	protected Button addCheckBox(Composite parent, String label, String prefKey, int horizontalIndent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(label);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalIndent = horizontalIndent;
		gd.horizontalSpan = 3;

		checkBox.setLayoutData(gd);
		checkBox.setData(prefKey);
		return checkBox;
	}

	/**
	 * Creates a composite with a highlighted Note entry and a message text.
	 * This is designed to take up the full width of the page.
	 * 
	 * @param font
	 *            the font to use
	 * @param composite
	 *            the parent composite
	 * @param title
	 *            the title of the note
	 * @param message
	 *            the message for the note
	 * @return the composite for the note
	 */
	protected Composite createNoteComposite(Font font, Composite composite, String title, String message,
			int horizontalSpan) {
		Composite messageComposite = new Composite(composite, SWT.NONE);
		GridLayout messageLayout = new GridLayout();
		messageLayout.numColumns = 2;
		messageLayout.marginWidth = 0;
		messageLayout.marginHeight = 0;
		messageLayout.marginTop = 10;
		messageComposite.setLayout(messageLayout);
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = horizontalSpan;
		gridData.widthHint = convertWidthInCharsToPixels(title.length() + message.length() + 1);
		messageComposite.setLayoutData(gridData);
		messageComposite.setFont(font);

		final Label noteLabel = new Label(messageComposite, SWT.BOLD);
		noteLabel.setText(title);
		noteLabel.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		noteLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		Label messageLabel = new Label(messageComposite, SWT.WRAP);
		messageLabel.setText(message);
		messageLabel.setFont(font);
		return messageComposite;
	}

}
