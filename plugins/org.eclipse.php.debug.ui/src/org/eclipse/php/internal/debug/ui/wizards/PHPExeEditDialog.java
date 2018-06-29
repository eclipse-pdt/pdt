/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class PHPExeEditDialog extends TitleAreaDialog implements IControlHandler {

	protected static final String FRAGMENT_GROUP_ID = "org.eclipse.php.debug.ui.phpExeWizardCompositeFragment"; //$NON-NLS-1$
	private List<CompositeFragment> runtimeComposites;
	private PHPexeItem phpExeItem;
	private PHPexeItem[] existingItems;
	private String tabID;
	private CTabFolder tabs;

	public PHPExeEditDialog(Shell shell, PHPexeItem phpExeItem, PHPexeItem[] existingItems) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		// Work on a simple working copy
		this.phpExeItem = phpExeItem.makeCopy();
		this.existingItems = existingItems;
		runtimeComposites = new ArrayList<>(3);
	}

	public PHPExeEditDialog(Shell shell, PHPexeItem phpExeItem, PHPexeItem[] existingItems, String tabID) {
		this(shell, phpExeItem, existingItems);
		this.tabID = tabID;
	}

	@Override
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

	@Override
	public void setImageDescriptor(ImageDescriptor image) {
		super.setTitleImage(image.createImage());
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// Create a tabbed container that will hold all the fragments
		tabs = SWTUtil.createTabFolder(parent);
		Map<String, ICompositeFragmentFactory> factories = WizardFragmentsFactoryRegistry
				.getFragmentsFactories(FRAGMENT_GROUP_ID);
		Collection<ICompositeFragmentFactory> factoriesList = factories.values();
		for (ICompositeFragmentFactory element : factoriesList) {
			CTabItem tabItem = new CTabItem(tabs, SWT.BORDER);
			CompositeFragment fragment = element.createComposite(tabs, this);
			fragment.setData(phpExeItem);
			if (fragment instanceof IPHPExeCompositeFragment) {
				((IPHPExeCompositeFragment) fragment).setExistingItems(existingItems);
			}
			tabItem.setText(fragment.getDisplayName());
			tabItem.setControl(fragment);
			tabItem.setData(fragment.getId());
			runtimeComposites.add(fragment);
		}
		tabs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CTabItem item = (CTabItem) e.item;
				CompositeFragment fragment = (CompositeFragment) item.getControl();
				setTitle(fragment.getTitle());
				setDescription(fragment.getDescription());
				setImageDescriptor(fragment.getImageDescriptor());
				fragment.setData(phpExeItem);
			}
		});
		getShell().setText(Messages.PHPExeEditDialog_1);
		// set the init selection of tabitem.
		if (tabID != null) {
			setSelect(tabID);
		}
		return tabs;
	}

	@Override
	protected void cancelPressed() {
		Iterator<CompositeFragment> composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			composites.next().performCancel();
		}
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		Iterator<CompositeFragment> composites = runtimeComposites.iterator();
		while (composites.hasNext()) {
			composites.next().performOk();
		}
		PHPexeItem original = PHPexes.getInstance().findItem(phpExeItem.getUniqueId());
		// Update original item
		PHPexes.getInstance().updateItem(original, phpExeItem);
		// Save changes
		PHPexes.getInstance().save();
		super.okPressed();
	}

	@Override
	protected void handleShellCloseEvent() {
		cancelPressed();
		super.handleShellCloseEvent();
	}

	@Override
	public void update() {
		Button button = getButton(IDialogConstants.OK_ID);
		if (button != null) {
			Iterator<CompositeFragment> composites = runtimeComposites.iterator();
			while (composites.hasNext()) {
				if (!composites.next().isComplete()) {
					button.setEnabled(false);
					return;
				}
			}
			button.setEnabled(true);
		}
	}

	@Override
	protected Point getInitialSize() {
		Point size = super.getInitialSize();
		size.y += 100;
		return size;
	}

	@Override
	public Kind getKind() {
		return Kind.EDITOR;
	}

	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		// not supported
	}

	private void setSelect(String id) {
		if (id == null) {
			return;
		}
		for (int i = 0; i < tabs.getItemCount(); i++) {
			if (id.equals(tabs.getItem(i).getData())) {
				tabs.setSelection(i);
				// Update tab
				CompositeFragment fragment = (CompositeFragment) tabs.getItem(i).getControl();
				setTitle(fragment.getTitle());
				setImageDescriptor(fragment.getImageDescriptor());
				setDescription(fragment.getDescription());
				fragment.validate();
				break;
			}
		}
	}

}
