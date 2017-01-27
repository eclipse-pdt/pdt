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
package org.eclipse.php.composer.ui.wizard;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.BuildpathDetector;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.dltk.ui.wizards.CapabilityConfigurationPage;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.api.packages.PharDownloader;
import org.eclipse.php.composer.core.ComposerPluginConstants;
import org.eclipse.php.composer.core.ComposerPreferenceConstants;
import org.eclipse.php.composer.core.launch.ExecutableNotFoundException;
import org.eclipse.php.composer.core.launch.ScriptLauncher;
import org.eclipse.php.composer.core.launch.ScriptLauncherManager;
import org.eclipse.php.composer.core.launch.environment.ComposerEnvironmentFactory;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.ui.ComposerUIPlugin;
import org.eclipse.php.composer.ui.handler.ConsoleResponseHandler;
import org.eclipse.php.composer.ui.job.runner.MissingExecutableRunner;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.wizards.IPHPProjectCreateWizardPage;
import org.eclipse.php.internal.ui.wizards.PHPBuildpathDetector;
import org.eclipse.php.ui.util.PHPProjectUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

@SuppressWarnings("restriction")
public abstract class AbstractWizardSecondPage extends CapabilityConfigurationPage
		implements IPHPProjectCreateWizardPage, Observer {

	protected final AbstractWizardFirstPage firstPage;
	protected Boolean fIsAutobuild;
	protected ScriptLauncher launcher;
	protected PharDownloader downloader;
	protected URI currentProjectLocation; // null if location is platform
											// locatio

	@Inject
	protected ScriptLauncherManager launchManager;

	public AbstractWizardSecondPage(AbstractWizardFirstPage mainPage, String title) {
		super(title);
		setPageComplete(false);
		setTitle(getPageTitle());
		setDescription(getPageDescription());
		firstPage = mainPage;
		fIsAutobuild = null;
		currentProjectLocation = null;
		ContextInjectionFactory.inject(this, ComposerUIPlugin.getDefault().getEclipseContext());
	}

	@Override
	public void initPage() {
		changeToNewProject();
	}

	@Override
	protected String getScriptNature() {
		return PHPNature.ID;
	}

	protected void changeToNewProject() {
		firstPage.getDetect();

		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					if (fIsAutobuild == null) {
						fIsAutobuild = Boolean.valueOf(CoreUtility.enableAutoBuild(false));
					}
					updateProject(monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} catch (OperationCanceledException e) {
					throw new InterruptedException();
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, false, new WorkspaceModifyDelegatingOperation(op));
		} catch (InvocationTargetException e) {
			final String title = NewWizardMessages.ScriptProjectWizardSecondPage_error_title;
			final String message = NewWizardMessages.ScriptProjectWizardSecondPage_error_message;
			ExceptionHandler.handle(e, getShell(), title, message);
		} catch (InterruptedException e) {
			// cancel pressed
		}
	}

	protected void dumpAutoload(final IProgressMonitor monitor) throws Exception {

		try {
			launcher = launchManager.getLauncher(ComposerEnvironmentFactory.FACTORY_ID, getProject());
		} catch (ExecutableNotFoundException e) {
			Display.getDefault().asyncExec(new MissingExecutableRunner());
			return;
		}
		launcher.addResponseListener(new ConsoleResponseHandler());
		try {
			launcher.launch("dumpautoload"); //$NON-NLS-1$
			getProject().refreshLocal(IProject.DEPTH_INFINITE, monitor);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	protected void installComposer(IProgressMonitor monitor) throws CoreException {
		// only download composer.phar when config is set to use project phar
		IPreferenceStore prefs = ComposerUIPlugin.getDefault().getCorePreferenceStore();
		if (prefs.getBoolean(ComposerPreferenceConstants.USE_PROJECT_PHAR)) {
			downloader = new PharDownloader();
			InputStream resource = downloader.download();
			IFile file = getProject().getFile("composer.phar"); //$NON-NLS-1$
			file.create(resource, true, monitor);
			file.refreshLocal(IResource.DEPTH_ZERO, monitor);
		}
	}

	protected void addComposerJson(IProgressMonitor monitor) throws CoreException {

		IFile file = getProject().getFile(org.eclipse.php.composer.api.ComposerConstants.COMPOSER_JSON);
		Namespace ns = firstPage.getPackage().getAutoload().getPsr4().getFirst();

		if (ns != null) {
			// if (ns.getNamespace().contains("\\")) {
			// String[] split = ns.getNamespace().split("\\\\");
			// IPath path = new
			// Path(org.eclipse.php.composer.core.ComposerPluginConstants.DEFAULT_SRC_FOLDER);
			// for (String segment : split) {
			// path = path.append(segment);
			// IFolder folder = getProject().getFolder(path);
			// if (!folder.exists()) {
			// folder.create(false, true, monitor);
			// }
			// }
			// } else {
			// IPath path = new
			// Path(org.eclipse.php.composer.core.ComposerPluginConstants.DEFAULT_SRC_FOLDER).append(ns
			// .getNamespace());
			IPath path = new Path(org.eclipse.php.composer.core.ComposerPluginConstants.DEFAULT_SRC_FOLDER);
			IFolder folder = getProject().getFolder(path);
			if (!folder.exists()) {
				folder.create(false, true, monitor);
			}
			// }
		}

		if (file.exists()) {
			Logger.debug("composer.json already exists in the location"); //$NON-NLS-1$
			return;
		}

		ComposerPackage composerPackage = firstPage.getPackage();
		VersionedPackage phpVersion = new VersionedPackage();
		phpVersion.setName("php"); //$NON-NLS-1$
		phpVersion.setVersion(">=" + firstPage.getPHPVersionValue().getAlias().replace("php", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		composerPackage.getRequire().add(phpVersion);

		ByteArrayInputStream bis = new ByteArrayInputStream(composerPackage.toJson().getBytes());
		file.create(bis, true, monitor);
		getProject().refreshLocal(0, monitor);
	}

	protected void setPhpLangOptions() {
		boolean useASPTags = false;
		PHPVersion phpVersion = PHPVersion.fromApi(firstPage.versionGroup.fConfigurationBlock.getPHPVersionValue());
		ProjectOptions.setSupportingAspTags(useASPTags, getProject());
		ProjectOptions.setPhpVersion(phpVersion, getProject());
	}

	protected URI getProjectLocationURI() throws CoreException {
		if (firstPage.isInWorkspace()) {
			return null;
		}
		return firstPage.getLocationURI();
	}

	protected IProject getProject() {
		IScriptProject scriptProject = getScriptProject();
		if (scriptProject != null) {
			return scriptProject.getProject();
		}
		return null;
	}

	public void createProject(IProject project, URI locationURI, IProgressMonitor monitor) throws CoreException {
		PHPProjectUtils.createProjectAt(project, locationURI, monitor);
	}

	public IProject getCurrProject() {
		return getProject();
	}

	protected IncludePath[] setProjectBaseIncludepath() {
		return new IncludePath[] { new IncludePath(getProject(), getProject()) };
	}

	protected BuildpathDetector createBuildpathDetector(IProgressMonitor monitor, IDLTKLanguageToolkit toolkit)
			throws CoreException {
		BuildpathDetector detector = new PHPBuildpathDetector(getProject(), toolkit);
		detector.detectBuildpath(new SubProgressMonitor(monitor, 20));
		return detector;
	}

	public void cancel() {
		if (downloader != null) {
			downloader.abort();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void updateProject(IProgressMonitor monitor) throws CoreException, InterruptedException {

		IProject projectHandle = firstPage.getProjectHandle();
		IScriptProject create = DLTKCore.create(projectHandle);
		super.init(create, null, false);
		currentProjectLocation = getProjectLocationURI();

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			monitor.beginTask(Messages.AbstractWizardSecondPage_InitializingProjectTaskName, 70);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			createProject(getProject(), currentProjectLocation, new SubProgressMonitor(monitor, 20));

			IBuildpathEntry[] buildpathEntries = null;

			// TODO: see
			// https://github.com/pulse00/Composer-Eclipse-Plugin/issues/37
			IPath srcPath = new Path(ComposerPluginConstants.DEFAULT_SRC_FOLDER);

			if (srcPath.segmentCount() > 0) {
				IFolder folder = getProject().getFolder(srcPath);
				CoreUtility.createFolder(folder, true, true, new SubProgressMonitor(monitor, 10));
			} else {
				monitor.worked(10);
			}

			final IPath projectPath = getProject().getFullPath();

			// configure the buildpath entries, including the default
			// InterpreterEnvironment library.
			List cpEntries = new ArrayList();
			cpEntries.add(DLTKCore.newSourceEntry(projectPath.append(srcPath)));
			cpEntries.add(DLTKCore.newContainerEntry(LanguageModelInitializer.LANGUAGE_CONTAINER_PATH));
			cpEntries.add(DLTKCore.newSourceEntry(projectPath.append("vendor").append("composer"))); //$NON-NLS-1$ //$NON-NLS-2$

			buildpathEntries = (IBuildpathEntry[]) cpEntries.toArray(new IBuildpathEntry[cpEntries.size()]);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			init(DLTKCore.create(getProject()), buildpathEntries, false);
			setPhpLangOptions();
			configureScriptProject(new SubProgressMonitor(monitor, 30));

			// adding build paths, and language-Container:
			getScriptProject().setRawBuildpath(buildpathEntries, new NullProgressMonitor());
			LanguageModelInitializer.enableLanguageModelFor(getScriptProject());
		} finally {
			monitor.done();
		}
	}

	public void performFinish(IProgressMonitor monitor) throws CoreException, InterruptedException {
		try {
			beforeFinish(monitor);
			monitor.beginTask(Messages.AbstractWizardSecondPage_InitializingBuildPathsTaskName, 10);
			if (getProject() == null || !getProject().exists()) {
				updateProject(new SubProgressMonitor(monitor, 3));
			}
			finishPage(monitor);
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			monitor.done();
			if (fIsAutobuild != null) {
				CoreUtility.enableAutoBuild(fIsAutobuild.booleanValue());
				fIsAutobuild = null;
			}
		}
	}

	protected void refreshProject(String projectName) {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project == null) {
			Logger.log(ERROR, "Error finishing create-project installation. Could not obtain project from workspace: " //$NON-NLS-1$
					+ projectName);
			return;
		}

		new WorkspaceJob(NLS.bind(Messages.AbstractWizardSecondPage_RefreshingProjectJobName, projectName)) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				return Status.OK_STATUS;
			}
		}.schedule();
	}

	abstract public void update(Observable o, Object arg);

	abstract protected String getPageTitle();

	abstract protected String getPageDescription();

	/**
	 * Run any logic before the actual project is being created.
	 * 
	 * @param monitor
	 * @throws Exception
	 */
	protected abstract void beforeFinish(IProgressMonitor monitor) throws Exception;

	/**
	 * Run any logic after the project has been created and is ready to use.
	 * 
	 * @param monitor
	 * @throws Exception
	 */
	protected abstract void finishPage(IProgressMonitor monitor) throws Exception;

}
