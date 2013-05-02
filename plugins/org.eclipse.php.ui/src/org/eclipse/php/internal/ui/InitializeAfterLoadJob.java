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
package org.eclipse.php.internal.ui;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.ui.progress.UIJob;

public class InitializeAfterLoadJob extends UIJob {

	private final class RealJob extends Job {
		public RealJob(String name) {
			super(name);
		}

		protected IStatus run(IProgressMonitor monitor) {
			monitor.beginTask("", 10); //$NON-NLS-1$
			try {
				PHPCorePlugin.initializeAfterLoad(new SubProgressMonitor(
						monitor, 6));
				PHPUiPlugin.initializeAfterLoad(new SubProgressMonitor(monitor,
						4));
			} catch (CoreException e) {
				PHPCorePlugin.log(e);
				return e.getStatus();
			}
			return new Status(IStatus.OK, PHPCorePlugin.getPluginId(),
					IStatus.OK, "", null); //$NON-NLS-1$
		}

		public boolean belongsTo(Object family) {
			return PHPUiPlugin.ID.equals(family);
		}
	}

	public InitializeAfterLoadJob() {
		super(PHPUIMessages.InitializeAfterLoadJob_0); 
		setSystem(true);
	}

	public IStatus runInUIThread(IProgressMonitor monitor) {
		Job job = new RealJob(PHPUIMessages.InitializeAfterLoadJob_1); 
		job.setPriority(Job.SHORT);
		job.schedule();
		return new Status(IStatus.OK, PHPCorePlugin.getPluginId(), IStatus.OK,
				"", null); //$NON-NLS-1$
	}
}