/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.composer.api.packages.PharDownloader;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.execution.ExecutionResponseListener;
import org.eclipse.php.composer.core.log.Logger;

public class CreateProjectJob extends ComposerJob {

	private final String projectName;
	private final String packageName;
	private IPath composerPath;
	private IPath path;
	private JobListener initListener;
	private String packageVersion;
	private boolean startNotified = false;
	private boolean existed;
	private File composerFile;

	public CreateProjectJob(IPath path, String projectName, String packageName) {
		this(path, projectName, packageName, null);
	}

	public CreateProjectJob(IPath path, String projectName, String packageName, String packageVersion) {
		super(Messages.CreateProjectJob_Name);
		this.projectName = projectName;
		this.packageName = packageName;
		this.packageVersion = packageVersion;
		this.path = path;

		if (Logger.isDebugging()) {
			String msg = "Creating new project " + projectName + " from package " + packageName; //$NON-NLS-1$ //$NON-NLS-2$
			if (packageVersion != null) {
				msg += " / " + packageVersion; //$NON-NLS-1$
			}
			Logger.debug(msg);
		}

		ResourcesPlugin.getWorkspace();
		DummyProject project = new DummyProject(path);
		setProject(project);

		try {
			// TODO: cache the phar file locally
			composerPath = path.append("composer.phar"); //$NON-NLS-1$
			composerFile = composerPath.toFile();
			existed = true;
			if (!composerFile.exists()) {
				existed = false;
				PharDownloader downloader = new PharDownloader();
				InputStream resource = downloader.download();
				FileUtils.copyInputStreamToFile(resource, composerFile);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	protected void doOnLauncherRunException(Exception e) {
		// to avoid deadlocks in
		// PackageProjectWizardSecondPage#beforeFinish() at
		// latch.await()
		notifyOnFail();
	}

	@Override
	protected void launch(ScriptLauncher launcher) throws ExecuteException, IOException, InterruptedException {

		// cloning large projects can take a long time...
		// TODO: make this configurable via preferences
		launcher.setTimeout(5 * 60000);
		launcher.addResponseListener(new ExecutionResponseListener() {

			@Override
			public void executionStarted() {

			}

			@Override
			public void executionMessage(String message) {
				try {
					if (composerExists() /*
											 * message != null && message.
											 * equals("Loading composer repositories with package information"
											 * )
											 */) {
						notifyOnStart();
					}
				} catch (Exception e) {
					Logger.logException(e);
				}
			}

			@Override
			public void executionFinished(String response, int exitValue) {
				notifyOnFinish();
			}

			@Override
			public void executionFailed(String response, Exception exception) {
				notifyOnFail();
			}

			@Override
			public void executionError(String message) {
				notifyOnFail();
			}

			@Override
			public void executionAboutToStart() {
			}
		});

		List<String> params = new ArrayList<>();
		// workaround for incorrect progress displaying on Windows
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			params.add("--no-progress"); //$NON-NLS-1$
		}

		params.add(packageName);
		params.add(projectName);
		if (packageVersion != null) {
			params.add(packageVersion);
		}

		launcher.launch("create-project", params.toArray(new String[params.size()])); //$NON-NLS-1$
	}

	protected class DummyProject extends Project {

		public DummyProject(IPath path) {
			this(path, (Workspace) ResourcesPlugin.getWorkspace());
		}

		protected DummyProject(IPath path, Workspace container) {
			super(path, container);
		}

		@Override
		public IResource findMember(String path) {
			return new DummyResource();
		}

		@Override
		public IPath getLocation() {
			return path;
		}
	}

	private boolean composerExists() {
		IPath projectPath = path.append(projectName).append("composer.json"); //$NON-NLS-1$
		return projectPath.toFile().exists();
	}

	public class DummyResource extends org.eclipse.core.internal.resources.File {

		protected DummyResource() {
			super(new Path("/"), null); //$NON-NLS-1$
		}

		public IPath getFullPath() {
			return new Path("/dummy/composer.phar"); //$NON-NLS-1$
		}
	}

	public void setJobListener(JobListener latch) {
		this.initListener = latch;
	}

	private void notifyOnStart() {
		try {
			if (startNotified || initListener == null) {
				return;
			}
			initListener.jobStarted();
			startNotified = true;
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private void notifyOnFinish() {
		try {
			if (initListener == null) {
				return;
			}
			initListener.jobFinished(projectName);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private void notifyOnFail() {

		try {
			if (initListener == null) {
				return;
			}
			initListener.jobFailed();
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public interface JobListener {
		void jobStarted();

		void jobFinished(String projectName);

		void jobFailed();
	}

	protected void cleanup() {
		if (existed == false && composerFile != null) {
			composerFile.delete();
		}
	}
}
