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
public abstract class AbstractMultiBlockPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private IPHPPreferencePageBlock[] fConfigurationBlocks;

	public AbstractMultiBlockPreferencePage() {
		setDescription();
		setPreferenceStore();
		createConfigurationBlocks();
	}

	private void createConfigurationBlocks() {
		try {
			fConfigurationBlocks = PHPPreferencePageBlocksRegistry.getPHPPreferencePageBlock(getPreferencePageID());
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
		if (fConfigurationBlocks == null) {
			fConfigurationBlocks = new IPHPPreferencePageBlock[0];
		}
		Map<String, IPHPPreferencePageBlock> map = new HashMap<>();
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			map.put(block.getComparableName(), block);
		}
		Set<String> keys = map.keySet();

		fConfigurationBlocks = new IPHPPreferencePageBlock[keys.size()];
		List<String> keysList = new ArrayList<>();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String comperableName = iter.next();
			keysList.add(comperableName);
		}
		Collections.sort(keysList);
		int i = 0;
		for (Iterator<String> iter = keysList.iterator(); iter.hasNext(); i++) {
			String comperableName = iter.next();
			fConfigurationBlocks[i] = map.get(comperableName);
		}
	}

	@Override
	protected Control createContents(Composite composite) {
		ScrolledCompositeImpl scrolledCompositeImpl = new ScrolledCompositeImpl(composite, SWT.V_SCROLL | SWT.H_SCROLL);
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

	@Override
	public void init(IWorkbench workbench) {
	}

	protected abstract void setDescription();

	protected abstract void setPreferenceStore();

	protected abstract String getPreferencePageID();

	@Override
	public boolean performCancel() {
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			block.performCancel();
		}
		return super.performCancel();
	}

	@Override
	protected void performDefaults() {
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			block.performDefaults();
		}
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		for (int i = 0; i < fConfigurationBlocks.length; i++) {
			IPHPPreferencePageBlock block = fConfigurationBlocks[i];
			block.performOK(false);
		}
		return super.performOk();
	}

}
