/*******************************************************************************
 * Copyright (c) 2012 NumberFour AG
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.php.core.tests.filenetwork;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

public class WorkspaceAutoBuild {

	public static void disable() throws CoreException {
		setAutoBuilding(false);
	}

	public static void enable() throws CoreException {
		setAutoBuilding(true);
	}

	private static void setAutoBuilding(boolean value) throws CoreException {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceDescription description = workspace.getDescription();
		if (description.isAutoBuilding() != value) {
			description.setAutoBuilding(value);
			workspace.setDescription(description);
		}
	}

	/**
	 * Wait for autobuild notification to occur
	 */
	public static void waitFor() {
		boolean wasInterrupted = false;
		do {
			try {
				IJobManager jobManager = Job.getJobManager();
				Job[] jobs = Job.getJobManager().find(
						ResourcesPlugin.FAMILY_AUTO_BUILD);
				jobManager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				jobs = Job.getJobManager().find(
						ResourcesPlugin.FAMILY_AUTO_BUILD);
				for (int j = 0; j < jobs.length; j++) {
					System.out.println("#2" + jobs[j]);
				}
				wasInterrupted = false;
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted);
	}

}
