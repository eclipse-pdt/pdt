/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.php.composer.api.collection.Scripts;
import org.eclipse.php.composer.api.objects.Script;
import org.eclipse.php.composer.api.objects.Script.HandlerValue;
import org.eclipse.php.composer.ui.ComposerUIPluginConstants;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ScriptDialog extends Dialog {

	private Combo eventControl;
	private Text handlerControl;

	private Script script;
	private HandlerValue handlerValue;

	private boolean handlerEnabled = true;
	private boolean eventEnabled = true;

	/**
	 * @wbp.parser.constructor
	 */
	public ScriptDialog(Shell parentShell, Script script, HandlerValue handlerValue) {
		super(parentShell);
		this.script = script;
		this.handlerValue = handlerValue;
	}

	public ScriptDialog(IShellProvider parentShell, Script script, HandlerValue handlerValue) {
		super(parentShell);
		this.script = script;
		this.handlerValue = handlerValue;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText(Messages.ScriptDialog_Title);
		getShell().setImage(ComposerUIPluginImages.EVENT.createImage());

		Composite contents = new Composite(parent, SWT.NONE);
		contents.setLayout(new GridLayout(2, false));

		Label lblEvent = new Label(contents, SWT.NONE);
		GridData gd_lblEvent = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblEvent.widthHint = ComposerUIPluginConstants.DIALOG_LABEL_WIDTH;
		lblEvent.setLayoutData(gd_lblEvent);
		lblEvent.setText(Messages.ScriptDialog_EventLabel);

		eventControl = new Combo(contents, SWT.READ_ONLY);
		eventControl.setEnabled(eventEnabled);
		GridData gd_eventControl = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_eventControl.widthHint = ComposerUIPluginConstants.DIALOG_CONTROL_WIDTH;
		eventControl.setLayoutData(gd_eventControl);
		eventControl.setItems(Scripts.getEvents());
		eventControl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				script.setScript(eventControl.getText());
			}
		});

		if (script.getScript() != null) {
			eventControl.setText(script.getScript());
		} else {
			// leave it null to indicate that there is no combo box selection
		}

		Label lblHandler = new Label(contents, SWT.NONE);
		lblHandler.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblHandler.setText(Messages.ScriptDialog_HandlerLabel);

		handlerControl = new Text(contents, SWT.BORDER);
		handlerControl.setEnabled(handlerEnabled);
		handlerControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		handlerControl.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handlerValue.setValue(handlerControl.getText());
			}
		});

		String text = handlerValue.getAsString();
		if (text != null) {
			handlerControl.setText(text);
		} else {
			// must never be null, so at least be sure to always return an empty
			// string
			handlerControl.setText(""); //$NON-NLS-1$
		}

		return contents;
	}

	public void setHandlerEnabled(boolean enabled) {
		handlerEnabled = enabled;
		if (handlerControl != null) {
			handlerControl.setEnabled(handlerEnabled);
		}
	}

	public void setEventEnabled(boolean enabled) {
		eventEnabled = enabled;
		if (eventControl != null) {
			eventControl.setEnabled(eventEnabled);
		}
	}

	public Script getScript() {
		return script;
	}

	public HandlerValue getHandlerValue() {
		return handlerValue;
	}
}
