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
package org.eclipse.php.server.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * A fragmented wizard page.
 */
public class FragmentedWizardPage extends WizardPage implements IWizardHandle {
	protected WizardFragment fragment;

	protected boolean isEmptyError = false;

	public FragmentedWizardPage(WizardFragment fragment) {
		super(fragment.toString());
		this.fragment = fragment;
	}

	public void createControl(Composite parentComp) {
		Composite comp = null;
		try {
			comp = fragment.createComposite(parentComp, this);
		} catch (Exception e) {
			Logger.log(Logger.WARNING, "Could not create wizard page composite", e);
		}
		if (comp == null) {
			comp = new Composite(parentComp, SWT.NONE);
			comp.setLayout(new FillLayout(SWT.VERTICAL));
			Label label = new Label(comp, SWT.NONE);
			label.setText("Internal error");
		}
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.widthHint = convertHorizontalDLUsToPixels(150);
		//data.heightHint = convertVerticalDLUsToPixels(350);
		comp.setLayoutData(data);
		setControl(comp);
	}

	public boolean isPageComplete() {
		//if (isEmptyError)
		//	return false;
		try {
			if (!fragment.isComplete())
				return false;
		} catch (Exception e) {
			return false;
		}
		//return (getMessage() == null || getMessageType() != ERROR);
		return true;
	}

	public boolean canFlipToNextPage() {
		if (getNextPage() == null)
			return false;
		//if (isEmptyError)
		//	return false;
		try {
			if (!fragment.isComplete())
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
		//return (getMessage() == null || getMessageType() != ERROR);
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (visible) {
			FragmentedWizard wizard = (FragmentedWizard) getWizard();
			wizard.switchWizardFragment(fragment);

			if (getContainer().getCurrentPage() != null)
				getContainer().updateButtons();
		}
	}

	public void setMessage(String message, int type) {
		if (type == IMessageProvider.ERROR && "".equals(message)) {
			isEmptyError = true;
			message = null;
		} else
			isEmptyError = false;
		super.setMessage(message, type);
		WizardFragment frag = ((FragmentedWizard) getWizard()).getCurrentWizardFragment();
		if (!fragment.equals(frag))
			return;
		getContainer().updateButtons();
	}

	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws InterruptedException, InvocationTargetException {
		getWizard().getContainer().run(fork, cancelable, runnable);
	}

	public void update() {
		fragment.updateChildFragments();
		((FragmentedWizard) getWizard()).updatePages();

		final IWizardContainer container = getContainer();
		if (container.getCurrentPage() != null) {
			getShell().getDisplay().syncExec(new Runnable() {
				public void run() {
					container.updateButtons();
				}
			});
		}
	}
}