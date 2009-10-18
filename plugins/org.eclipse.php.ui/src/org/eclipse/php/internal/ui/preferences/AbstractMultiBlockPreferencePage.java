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
package org.eclipse.php.internal.ui.preferences;

import java.util.*;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.IPHPPreferencePageBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * 
 * @author guy.g
 * 
 */
public abstract class AbstractMultiBlockPreferencePage extends PreferencePage
		implements IWorkbenchPreferencePage {

	private IPHPPreferencePageBlock[] fConfigurationBlocks;

	public AbstractMultiBlockPreferencePage() {
		setDescription();
		setPreferenceStore();
		createConfigurationBlocks();
	}

	private void createConfigurationBlocks() {
		try {
			fConfigurationBlocks = PHPPreferencePageBlocksRegistry
					.getPHPPreferencePageBlock(getPreferencePageID());
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		if (fConfigurationBlocks == null) {
			fConfigurationBlocks = new IPHPPreferencePageBlock[0];
		}
		HashMap map = new HashMap();
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			map.put(block.getComparableName(), block);
		}
		Set keys = map.keySet();

		fConfigurationBlocks = new IPHPPreferencePageBlock[keys.size()];
		ArrayList keysList = new ArrayList();
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String comperableName = (String) iter.next();
			keysList.add(comperableName);
		}
		Collections.sort(keysList);
		int i = 0;
		for (Iterator iter = keysList.iterator(); iter.hasNext(); i++) {
			String comperableName = (String) iter.next();
			fConfigurationBlocks[i] = (IPHPPreferencePageBlock) map
					.get(comperableName);
		}
	}

	protected Control createContents(Composite composite) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(
				composite, SWT.V_SCROLL | SWT.H_SCROLL);
		Composite group = new Composite(scrolledCompositeImpl, SWT.NONE);
		group.setLayout(new GridLayout());
		try {
			for (int i = 0; i < fConfigurationBlocks.length; i++) {
				fConfigurationBlocks[i].setCompositeAddon(group);
				fConfigurationBlocks[i].initializeValues(this);
			}
			scrolledCompositeImpl.setContent(group);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		return scrolledCompositeImpl;
	}

	public void init(IWorkbench workbench) {
	}

	protected abstract void setDescription();

	protected abstract void setPreferenceStore();

	protected abstract String getPreferencePageID();

	public boolean performCancel() {
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			block.performCancel();
		}
		return super.performCancel();
	}

	protected void performDefaults() {
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			block.performDefaults();
		}
		super.performDefaults();
	}

	public boolean performOk() {
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			block.performOK(false);
		}
		return super.performOk();
	}

}
