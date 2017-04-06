/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.ui.builtin;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.server.core.IRuntimeWorkingCopy;
import org.eclipse.wst.server.core.TaskModel;
import org.eclipse.wst.server.ui.wizard.IWizardHandle;
import org.eclipse.wst.server.ui.wizard.WizardFragment;

/**
 * 
 */
public class PHPRuntimeWizardFragment extends WizardFragment {
	protected PHPRuntimeComposite comp;

	public PHPRuntimeWizardFragment() {
		// do nothing
	}

	@Override
	public boolean hasComposite() {
		return true;
	}

	@Override
	public boolean isComplete() {
		IRuntimeWorkingCopy runtime = (IRuntimeWorkingCopy) getTaskModel().getObject(TaskModel.TASK_RUNTIME);

		if (runtime == null)
			return false;
		IStatus status = runtime.validate(null);
		return (status == null || status.getSeverity() != IStatus.ERROR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.server.ui.task.WizardFragment#createComposite()
	 */
	@Override
	public Composite createComposite(Composite parent, IWizardHandle wizard) {
		comp = new PHPRuntimeComposite(parent, wizard);
		return comp;
	}

	@Override
	public void enter() {
		if (comp != null) {
			IRuntimeWorkingCopy runtime = (IRuntimeWorkingCopy) getTaskModel().getObject(TaskModel.TASK_RUNTIME);
			comp.setRuntime(runtime);
		}
	}

}