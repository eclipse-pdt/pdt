/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakula and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Dawid Pakula - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.profile.core.PHPProfileCoreMessages;
import org.eclipse.php.profile.core.PHPProfileCorePlugin;
import org.eclipse.php.profile.core.data.ProfilerData;
import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindModelParser;

public class PHPLaunchListener implements ILaunchListener {

	@Override
	public void launchRemoved(ILaunch launch) {

	}

	@Override
	public void launchAdded(ILaunch launch) {
	}

	@Override
	public void launchChanged(ILaunch launch) {
		if (launch.getLaunchMode().equals(ILaunchManager.PROFILE_MODE)
				&& launch.getAttribute(IPHPDebugConstants.CacheGrind_File) != null) {
			new Job(PHPProfileCoreMessages.PHPLaunchListener_0 + launch.getLaunchConfiguration().getName()) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					SubMonitor sub = SubMonitor.convert(monitor, 2);
					sub.beginTask(PHPProfileCoreMessages.PHPLaunchListener_1, 2);
					sub.setBlocked(new Status(IStatus.OK, PHPProfileCorePlugin.ID,
							PHPProfileCoreMessages.PHPLaunchListener_2));
					while (!monitor.isCanceled() && !launch.isTerminated()) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							return Status.CANCEL_STATUS;
						}
					}
					sub.clearBlocked();
					sub.worked(1);
					sub.beginTask(PHPProfileCoreMessages.PHPLaunchListener_3, 1);
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					File f = new File(launch.getAttribute(IPHPDebugConstants.CacheGrind_File));
					if (f.exists()) {
						if (f.length() > 0) {
							try {
								CacheGrindModelParser tmp = new CacheGrindModelParser(new FileInputStream(f));
								ProfilerData[] models = tmp.buildModel();
								if (monitor.isCanceled()) {
									return Status.CANCEL_STATUS;
								}
								for (ProfilerData model : models) {
									ProfileSessionsManager.addSession(new DefaultProfilerDB(model, new Date()));
								}
								f.delete();
								if (models.length == 0) {
									return new Status(IStatus.ERROR, PHPProfileCorePlugin.ID,
											PHPProfileCoreMessages.PHPLaunchListener_4);
								}
							} catch (FileNotFoundException e) {
								// ignore, already checked
							} catch (IOException e) {
								return new Status(IStatus.ERROR, PHPProfileCorePlugin.ID,
										NLS.bind(PHPProfileCoreMessages.PHPLaunchListener_5, e.getMessage()));
							}
						} else {
							return new Status(IStatus.ERROR, PHPProfileCorePlugin.ID,
									PHPProfileCoreMessages.PHPLaunchListener_6);
						}
					} else {
						return new Status(IStatus.ERROR, PHPProfileCorePlugin.ID,
								PHPProfileCoreMessages.PHPLaunchListener_7);
					}
					return Status.OK_STATUS;
				}
			}.schedule();
		}
	}

}
