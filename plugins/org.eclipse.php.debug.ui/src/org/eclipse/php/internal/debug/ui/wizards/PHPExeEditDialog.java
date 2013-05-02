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
package org.eclipse.php.internal.debug.ui.wizards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.internal.ui.wizards.CompositeFragment;
import org.eclipse.php.internal.ui.wizards.IControlHandler;
import org.eclipse.php.internal.ui.wizards.WizardFragmentsFactoryRegistry;
import org.eclipse.php.ui.wizards.ICompositeFragmentFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class PHPExeEditDialog extends TitleAreaDialog implements
		IControlHandler {

	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.debug.ui.phpExeWizardCompositeFragment"; //$NON-NLS-1$
	private List<CompositeFragment> runtimeComposites;
	private PHPexeItem phpExeItem;
	private PHPexeItem[] existingItems;

	public PHPExeEditDialog(Shell shell, PHPexeItem phpExeItem,
			PHPexeItem[] existingItems) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);

		this.existingItems = existingItems;
		this.phpExeItem = phpExeItem;
		runtimeComposites = new ArrayList<CompositeFragment>(3);
	}

	public void setDescription(String desc) {
		super.setMessage(desc);
	}

	public PHPexeItem[] getExistingItems() {
		return existingItems;
	}

	public void setPHPExeItem(PHPexeItem phpExeItem) {
		this.phpExeItem = phpExeItem;
	}

	public PHPexeItem getPHPExeItem() {
		return phpExeItem;
	}

	public void setImageDescriptor(ImageDescriptor image) {
		super.setTitleImage(image.createImage());
	}

	protected Control createDialogArea(Composite parent) {
		// Create a tabbed container that will hold all the fragments
		CTabFolder tabs = SWTUtil.createTabFolder(parent);
		ICompositeFragmentFactory[] factories = WizardFragmentsFactoryRegistry
				.getFragmentsFactories(FRAGMENT_GROUP_ID);
		for (ICompositeFragmentFactory element : factories) {
			CTabItem tabItem = new CTabItem(tabs, SWT.BORDER);
			CompositeFragment fragment = element.createComposite(tabs, this);
			fragment.setData(phpExeItem);
			if (fragment instanceof IPHPExeCompositeFragment) {
				((IPHPExeCompositeFragment) fragment)
						.setExistingItems(existingItems);
			}
			tabItem.setText(fragment.getDisplayName());
			tabItem.setControl(fragment);
			runtimeComposites.add(fragment);
		}
		getShell().setText(Messages.PHPExeEditDialog_1);
		tabs.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CTabItem item = (CTabItem) e.item;
				CompositeFragment fragment = (CompositeFragment) item
						.getControl();
				setTitle(fragment.getTitle());
				setDescription(fragment.getDescription());
			}
		});
		return tabs;
	}

	protected void cancelPressed() {
		Iterator<CompositeFragment> composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			composites.next().performCancel();
		}
		super.cancelPressed();
	}

	protected void okPressed() {
		Iterator<CompositeFragment> composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			composites.next().performOk();
		}
		super.okPressed();
	}

	public void update() {
		Button button = getButton(IDialogConstants.OK_ID);
		if (button != null) {
			Iterator<CompositeFragment> composites = runtimeComposites
					.iterator();
			while (composites.hasNext()) {
				if (!composites.next().isComplete()) {
					button.setEnabled(false);
					return;
				}
			}
			button.setEnabled(true);
		}
	}

	public void setMessage(String newMessage, int newType) {
		// Override the WARNING with an INFORMATION.
		// We have a bug that cause the warning to be displayed in all the tabs
		// and not
		// only in the selected one. (TODO - Fix this)
		if (newType == IMessageProvider.WARNING) {
			newType = IMessageProvider.INFORMATION;
		}
		super.setMessage(newMessage, newType);
	}
}
