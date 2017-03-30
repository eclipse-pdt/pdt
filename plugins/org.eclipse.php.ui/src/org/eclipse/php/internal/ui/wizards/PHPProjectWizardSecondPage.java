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
package org.eclipse.php.internal.ui.wizards;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.corext.util.Messages;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.wizards.BuildpathDetector;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.dltk.ui.wizards.CapabilityConfigurationPage;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.core.language.LanguageModelInitializer;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.preferences.includepath.PHPIncludePathsBlock;
import org.eclipse.php.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.php.ui.util.PHPProjectUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.web.core.internal.project.JsWebNature;

/**
 * As addition to the DLTKCapabilityConfigurationPage, the wizard does an early
 * project creation (so that linked folders can be defined) and, if an existing
 * external location was specified, offers to do a include detection
 */
public class PHPProjectWizardSecondPage extends CapabilityConfigurationPage implements IPHPProjectCreateWizardPage {

	private static final String FILENAME_PROJECT = ".project"; //$NON-NLS-1$
	protected static final String FILENAME_BUILDPATH = ".buildpath"; //$NON-NLS-1$

	protected final PHPProjectWizardFirstPage fFirstPage;

	protected URI fCurrProjectLocation; // null if location is platform location

	private boolean fKeepContent;

	private File fDotProjectBackup;
	private File fDotBuildpathBackup;
	private Boolean fIsAutobuild;

	/**
	 * Constructor for ScriptProjectWizardSecondPage.
	 */
	public PHPProjectWizardSecondPage(PHPProjectWizardFirstPage mainPage) {
		fFirstPage = mainPage;
		fCurrProjectLocation = null;
		fKeepContent = false;

		fDotProjectBackup = null;
		fDotBuildpathBackup = null;
		fIsAutobuild = null;
	}

	public PHPProjectWizardFirstPage getFirstPage() {
		return fFirstPage;
	}

	@Override
	protected boolean useNewSourcePage() {
		return true;
	}

	private void changeToNewProject() {
		fKeepContent = fFirstPage.getDetect();

		final IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					if (fIsAutobuild == null) {
						fIsAutobuild = fFirstPage.getIsAutobuild();
						if (fIsAutobuild == null) {
							fIsAutobuild = Boolean.valueOf(CoreUtility.enableAutoBuild(false));
						}
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

	protected void updateProject(IProgressMonitor monitor) throws CoreException, InterruptedException {

		IProject projectHandle = fFirstPage.getProjectHandle();
		IScriptProject create = DLTKCore.create(projectHandle);
		super.init(create, null, false);
		fCurrProjectLocation = getProjectLocationURI();

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			monitor.beginTask(NewWizardMessages.ScriptProjectWizardSecondPage_operation_initialize, 70);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			createProject(getProject(), fCurrProjectLocation, new SubProgressMonitor(monitor, 20));

			IBuildpathEntry[] buildpathEntries = null;
			IncludePath[] includepathEntries = null;

			if (fFirstPage.getDetect()) {
				includepathEntries = setProjectBaseIncludepath();
				if (!getProject().getFile(FILENAME_BUILDPATH).exists()) {

					IDLTKLanguageToolkit toolkit = DLTKLanguageManager.getLanguageToolkit(getScriptNature());
					final BuildpathDetector detector = createBuildpathDetector(monitor, toolkit);
					buildpathEntries = detector.getBuildpath();

				} else {
					monitor.worked(20);
				}
			} else if (fFirstPage.hasPhpSourceFolder()) {
				// need to create sub-folders and set special build/include
				// paths
				IPreferenceStore store = getPreferenceStore();
				IPath srcPath = new Path(store.getString(PreferenceConstants.SRCBIN_SRCNAME));
				IPath binPath = new Path(store.getString(PreferenceConstants.SRCBIN_BINNAME));

				if (srcPath.segmentCount() > 0) {
					IFolder folder = getProject().getFolder(srcPath);
					CoreUtility.createFolder(folder, true, true, new SubProgressMonitor(monitor, 10));
				} else {
					monitor.worked(10);
				}

				if (binPath.segmentCount() > 0) {
					IFolder folder = getProject().getFolder(binPath);
					CoreUtility.createFolder(folder, true, true, new SubProgressMonitor(monitor, 10));
				} else {
					monitor.worked(10);
				}

				final IPath projectPath = getProject().getFullPath();

				// configure the buildpath entries, including the default
				// InterpreterEnvironment library.
				List cpEntries = new ArrayList();
				cpEntries.add(DLTKCore.newSourceEntry(projectPath.append(srcPath)));

				buildpathEntries = (IBuildpathEntry[]) cpEntries.toArray(new IBuildpathEntry[cpEntries.size()]);
				includepathEntries = new IncludePath[] {
						new IncludePath(getProject().getFolder(srcPath), getProject()) };
			} else {
				// flat project layout
				IPath projectPath = getProject().getFullPath();
				List cpEntries = new ArrayList();
				cpEntries.add(DLTKCore.newSourceEntry(projectPath));

				buildpathEntries = (IBuildpathEntry[]) cpEntries.toArray(new IBuildpathEntry[cpEntries.size()]);
				includepathEntries = setProjectBaseIncludepath();

				monitor.worked(20);
			}
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}

			init(DLTKCore.create(getProject()), buildpathEntries, false);

			// setting PHP4/5 and ASP-Tags :
			setPhpLangOptions();

			configureScriptProject(new SubProgressMonitor(monitor, 30));

			// checking and adding JS nature,libs, include path if needed
			if (fFirstPage.fJavaScriptSupportGroup.shouldSupportJavaScript()) {
				addJavaScriptNature(monitor);
			}

			// adding build paths, and language-Container:
			getScriptProject().setRawBuildpath(buildpathEntries, new NullProgressMonitor());
			LanguageModelInitializer.enableLanguageModelFor(getScriptProject());

			// init, and adding include paths:
			getBuildPathsBlock().init(getScriptProject(), new IBuildpathEntry[] {});
			IncludePathManager.getInstance().setIncludePath(getProject(), includepathEntries);

		} finally {
			monitor.done();
		}
	}

	private IProject getProject() {
		IScriptProject scriptProject = getScriptProject();
		if (scriptProject != null) {
			return scriptProject.getProject();
		}
		return null;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		setHelpContext(getControl());
	}

	@Override
	protected void setHelpContext(Control control) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control,
				IPHPHelpContextIds.ADDING_ELEMENTS_TO_A_PROJECT_S_INCLUDE_PATH);
	}

	/**
	 * new project settings, helper
	 * 
	 * @return array of IncludePath's, size=1, and includes only the project's
	 *         root-dir include path
	 */
	protected IncludePath[] setProjectBaseIncludepath() {
		return new IncludePath[] { new IncludePath(getProject(), getProject()) };
	}

	/**
	 * @param monitor
	 * @throws CoreException
	 * @throws JavaScriptModelException
	 */
	protected void addJavaScriptNature(IProgressMonitor monitor) throws CoreException, JavaScriptModelException {
		JsWebNature jsWebNature = new JsWebNature(getProject(), new SubProgressMonitor(monitor, 1));
		jsWebNature.configure();
	}

	@Override
	public void configureScriptProject(IProgressMonitor monitor) throws CoreException, InterruptedException {
		String scriptNature = getScriptNature();
		setScriptNature(monitor, scriptNature);
	}

	/**
	 * @param monitor
	 * @param scriptNature
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	protected void setScriptNature(IProgressMonitor monitor, String scriptNature)
			throws CoreException, InterruptedException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		int nSteps = 6;
		monitor.beginTask(NewWizardMessages.ScriptCapabilityConfigurationPage_op_desc_Script, nSteps);

		try {
			IProject project = getProject();
			BuildpathsBlock.addScriptNature(project, new SubProgressMonitor(monitor, 1), scriptNature);
			// getBuildPathsBlock().configureScriptProject(new
			// SubProgressMonitor(monitor, 5));
		} catch (OperationCanceledException e) {
			throw new InterruptedException();
		} finally {
			monitor.done();
		}
	}

	protected BuildpathDetector createBuildpathDetector(IProgressMonitor monitor, IDLTKLanguageToolkit toolkit)
			throws CoreException {
		BuildpathDetector detector = new PHPBuildpathDetector(getProject(), toolkit);
		detector.detectBuildpath(new SubProgressMonitor(monitor, 20));
		return detector;
	}

	@Override
	protected String getScriptNature() {
		return PHPNature.ID;
	}

	protected IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	protected URI getProjectLocationURI() throws CoreException {
		if (fFirstPage.isInWorkspace()) {
			return null;
		}
		return fFirstPage.getLocationURI();
	}

	protected void rememberExistingFiles(URI projectLocation) throws CoreException {
		fDotProjectBackup = null;
		fDotBuildpathBackup = null;

		IFileStore file = EFS.getStore(projectLocation);
		if (file.fetchInfo().exists()) {
			IFileStore projectFile = file.getChild(FILENAME_PROJECT);
			if (projectFile.fetchInfo().exists()) {
				fDotProjectBackup = createBackup(projectFile, "project-desc"); //$NON-NLS-1$
			}
			IFileStore buildpathFile = file.getChild(FILENAME_BUILDPATH);
			if (buildpathFile.fetchInfo().exists()) {
				fDotBuildpathBackup = createBackup(buildpathFile, "buildpath-desc"); //$NON-NLS-1$
			}
		}
	}

	private void restoreExistingFiles(URI projectLocation, IProgressMonitor monitor) throws CoreException {
		int ticks = ((fDotProjectBackup != null ? 1 : 0) + (fDotBuildpathBackup != null ? 1 : 0)) * 2;
		monitor.beginTask("", ticks); //$NON-NLS-1$
		try {
			if (fDotProjectBackup != null) {
				IFileStore projectFile = EFS.getStore(projectLocation).getChild(FILENAME_PROJECT);
				projectFile.delete(EFS.NONE, new SubProgressMonitor(monitor, 1));
				copyFile(fDotProjectBackup, projectFile, new SubProgressMonitor(monitor, 1));
			}
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, DLTKUIPlugin.PLUGIN_ID, IStatus.ERROR,
					NewWizardMessages.ScriptProjectWizardSecondPage_problem_restore_project, e);
			throw new CoreException(status);
		}
		try {
			if (fDotBuildpathBackup != null) {
				IFileStore buildpathFile = EFS.getStore(projectLocation).getChild(FILENAME_BUILDPATH);
				buildpathFile.delete(EFS.NONE, new SubProgressMonitor(monitor, 1));
				copyFile(fDotBuildpathBackup, buildpathFile, new SubProgressMonitor(monitor, 1));
			}
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, DLTKUIPlugin.PLUGIN_ID, IStatus.ERROR,
					NewWizardMessages.ScriptProjectWizardSecondPage_problem_restore_buildpath, e);
			throw new CoreException(status);
		}
	}

	private File createBackup(IFileStore source, String name) throws CoreException {
		try {
			File bak = File.createTempFile("eclipse-" + name, ".bak"); //$NON-NLS-1$ //$NON-NLS-2$
			copyFile(source, bak);
			return bak;
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, DLTKUIPlugin.PLUGIN_ID, IStatus.ERROR,
					Messages.format(NewWizardMessages.ScriptProjectWizardSecondPage_problem_backup, name), e);
			throw new CoreException(status);
		}
	}

	private void copyFile(IFileStore source, File target) throws IOException, CoreException {
		InputStream is = source.openInputStream(EFS.NONE, null);
		FileOutputStream os = new FileOutputStream(target);
		copyFile(is, os);
	}

	private void copyFile(File source, IFileStore target, IProgressMonitor monitor) throws IOException, CoreException {
		FileInputStream is = new FileInputStream(source);
		OutputStream os = target.openOutputStream(EFS.NONE, monitor);
		copyFile(is, os);
	}

	private void copyFile(InputStream is, OutputStream os) throws IOException {
		try {
			byte[] buffer = new byte[8192];
			while (true) {
				int bytesRead = is.read(buffer);
				if (bytesRead == -1)
					break;

				os.write(buffer, 0, bytesRead);
			}
		} finally {
			try {
				is.close();
			} finally {
				os.close();
			}
		}
	}

	/**
	 * Called from the wizard on finish.
	 */
	public void performFinish(IProgressMonitor monitor) throws CoreException, InterruptedException {
		try {
			monitor.beginTask(NewWizardMessages.ScriptProjectWizardSecondPage_operation_create, 3);
			if (getProject() == null || !getProject().exists()) {
				updateProject(new SubProgressMonitor(monitor, 1));
			}

			if (!fKeepContent) {
				if (DLTKCore.DEBUG) {
					System.err.println("Add compiler compilance options here..."); //$NON-NLS-1$
				}
			}

			// flushing includepath changes in wizard page
			IWizardPage currentPage = getContainer().getCurrentPage();
			if (!(currentPage instanceof PHPProjectWizardFirstPage)) {
				getBuildPathsBlock().configureScriptProject(monitor);
			}

		} finally {
			monitor.done();
			if (fIsAutobuild != null) {

				CoreUtility.enableAutoBuild(fIsAutobuild.booleanValue());
				fIsAutobuild = null;
			}
		}
	}

	protected void setPhpLangOptions() {
		if (fFirstPage.fVersionGroup.fDefaultValues.isSelected()) {
			return;
		}
		boolean useASPTags = fFirstPage.fVersionGroup.fConfigurationBlock.getUseAspTagsValue();
		PHPVersion phpVersion = fFirstPage.fVersionGroup.fConfigurationBlock.getPHPVersionValue();
		ProjectOptions.setSupportingASPTags(useASPTags, getProject());
		ProjectOptions.setPHPVersion(phpVersion, getProject());
	}

	/**
	 * Helper method to create and open a IProject. The project location is
	 * configured. No natures are added.
	 * 
	 * @param project
	 *            The handle of the project to create.
	 * @param locationURI
	 *            The location of the project or <code>null</code> to create the
	 *            project in the workspace
	 * @param monitor
	 *            a progress monitor to report progress or <code>null</code> if
	 *            progress reporting is not desired
	 * @throws CoreException
	 *             if the project couldn't be created
	 * @see org.eclipse.core.resources.IProjectDescription#setLocationURI(java.net.URI)
	 * 
	 */
	public void createProject(IProject project, URI locationURI, IProgressMonitor monitor) throws CoreException {
		PHPProjectUtils.createProjectAt(project, locationURI, monitor);
	}

	public IProject getCurrProject() {
		return getProject();
	}

	@Override
	protected BuildpathsBlock createBuildpathBlock(IStatusChangeListener listener) {
		return new PHPIncludePathsBlock(new BusyIndicatorRunnableContext(), listener, 0, useNewSourcePage(), null);
	}

	@Override
	public void initPage() {
		// to be called from previous page setVisible(false) only !
		changeToNewProject();
	}
}
