/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

import java.util.Vector;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.sse.core.internal.encoding.CommonCharsetNames;
import org.eclipse.wst.xml.ui.internal.Logger;
import org.eclipse.wst.xml.ui.internal.preferences.EncodingSettings;

public class PhpEncodingSettings extends EncodingSettings {

	private class ComboListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			int i = encodingCombo.getSelectionIndex();
			if (i >= 0 && i < ianaVector.size())
				ianaText.setText((String) (ianaVector.elementAt(encodingCombo.getSelectionIndex())));
		}
	}

	private ModifyListener comboListener1;
	private Composite parent;

	public PhpEncodingSettings(Composite parent, String encodingLabel) {
		super(parent, encodingLabel);
	}

	protected void init(String ianaLabelStr, String encodingLabelStr) {
//		super.init(ianaLabelStr, encodingLabelStr);
//		((GridLayout)getLayout()).marginHeight = 0;
//		((GridLayout)getLayout()).marginWidth = 0;
		encodingLabel = createLabel(this.getParent(), encodingLabelStr);
		encodingCombo = createComboBox(this.getParent(), true);
		ianaLabel = createLabel(this.getParent(), ianaLabelStr);
		ianaText = createTextField(this.getParent(), 20);
		ianaVector = new Vector();
		fillCombo();
		resetToDefaultEncoding();
		comboListener1 = new ComboListener();
		encodingCombo.addModifyListener(comboListener1);
		
	}

	public void dispose() {
		encodingCombo.removeModifyListener(comboListener1);
		super.dispose();
	}

	private void fillCombo() {
		try {
			String[] ianaTags = CommonCharsetNames.getCommonCharsetNames();
			int totalNum = ianaTags.length;
			for (int i = 0; i < totalNum; i++) {
				String iana = ianaTags[i];
				String enc = CommonCharsetNames.getDisplayString(iana);

				if (enc != null) {
					encodingCombo.add(enc);
				} else {
					Logger.log(Logger.WARNING, "CommonCharsetNames.getDisplayString(" + iana + ") returned null"); //$NON-NLS-1$ //$NON-NLS-2$
					encodingCombo.add(iana);
				}
				ianaVector.add(iana);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//MessageDialog.openError(getShell(), "Resource exception",
			// "Unable to obtain encoding strings. Check resource file");
			//XMLEncodingPlugin.getPlugin().getMsgLogger().write(e.toString());
			//XMLEncodingPlugin.getPlugin().getMsgLogger().writeCurrentThread();
			Logger.log(Logger.ERROR, "Exception", e); //$NON-NLS-1$
		}
	}
}
