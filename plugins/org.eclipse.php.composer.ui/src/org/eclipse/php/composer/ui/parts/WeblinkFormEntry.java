/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.php.composer.ui.editor.FormEntryAdapter;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class WeblinkFormEntry extends FormEntry {

	public WeblinkFormEntry(Composite parent, FormToolkit toolkit, String labelText) {
		super(parent, toolkit, labelText, null, true);

		addFormEntryListener(new FormEntryAdapter() {
			@Override
			public void textValueChanged(FormEntry entry) {
				try {
					Hyperlink link = (Hyperlink) entry.getLabel();
					URL url = new URL(entry.getValue());
					link.setHref(url);
				} catch (MalformedURLException e) {
					// e.printStackTrace();
				}
			}

			@Override
			public void linkActivated(HyperlinkEvent e) {
				if (e.getHref() != null && e.getHref().toString() != null) {
					Program.launch(e.getHref().toString());
				}
			}

		});
	}
}
