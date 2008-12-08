/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.ui.util.IStatusChangeListener;
import org.eclipse.dltk.ui.wizards.BuildpathsBlock;
import org.eclipse.dltk.ui.wizards.NewElementWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Standard wizard page for creating new script projects. This page can be used
 * in project creation wizards. The page shows UI to configure the project with
 * a script build path and output location. On finish the page will also
 * configure the script nature.
 * <p>
 * This is a replacement for <code>NewScriptProjectWizardPage</code> with a
 * cleaner API.
 * </p>
 * <p>
 * Clients may instantiate or subclass.
 * </p>
 * 
 */

public abstract class CapabilityConfigurationPage extends NewElementWizardPage {
	//FIXME - this is a temp solution,
	// need to remove this class when DLTK integration is released 
	private static final String PAGE_NAME = "DLTKCapabilityConfigurationPage"; //$NON-NLS-1$

	private IScriptProject fScriptProject;
	private BuildpathsBlock fBuildPathsBlock;

	/**
	 * Creates a wizard page that can be used in a script project creation
	 * wizard. It contains UI to configure a the buildpath and the output
	 * folder.
	 * 
	 * <p>
	 * After constructing, a call to
	 * {@link #init(IScriptProject, IPath, IBuildpathEntry[], boolean)} is
	 * required.
	 * </p>
	 */
	public CapabilityConfigurationPage() {
		super(PAGE_NAME);
		fScriptProject = null;

		setTitle(NewWizardMessages.ScriptCapabilityConfigurationPage_title);
		setDescription(NewWizardMessages.ScriptCapabilityConfigurationPage_description);
	}

	protected BuildpathsBlock getBuildPathsBlock() {
		if (fBuildPathsBlock == null) {
			IStatusChangeListener listener = new IStatusChangeListener() {
				public void statusChanged(IStatus status) {
					updateStatus(status);
				}
			};
			fBuildPathsBlock = createBuildpathBlock(listener);
		}
		return fBuildPathsBlock;
	}

	protected abstract BuildpathsBlock createBuildpathBlock(
			IStatusChangeListener listener);

	// {
	// return new BuildPathsBlock(new BusyIndicatorRunnableContext(), listener,
	// 0, useNewSourcePage(), null);
	// }

	/**
	 * Clients can override this method to choose if the new source page is
	 * used. The new source page requires that the project is already created as
	 * script project. The page will directly manipulate the buildpath. By
	 * default <code>false</code> is returned.
	 * 
	 * @return Returns <code>true</code> if the new source page should be used.
	 * 
	 */
	protected boolean useNewSourcePage() {
		return false;
	}

	/**
	 * Initializes the page with the project and default buildpath.
	 * <p>
	 * The default buildpath entries must correspond the given project.
	 * </p>
	 * <p>
	 * The caller of this method is responsible for creating the underlying
	 * project. The page will create the output, source and library folders if
	 * required.
	 * </p>
	 * <p>
	 * The project does not have to exist at the time of initialization, but
	 * must exist when executing the runnable obtained by
	 * <code>getRunnable()</code>.
	 * </p>
	 * 
	 * @param jproject
	 *            The script project.
	 * @param defaultOutputLocation
	 *            The default buildpath entries or <code>null</code> to let the
	 *            page choose the default
	 * @param defaultEntries
	 *            The folder to be taken as the default output path or
	 *            <code>null</code> to let the page choose the default
	 * @param defaultsOverrideExistingBuildpath
	 *            If set to <code>true</code>, an existing '.buildpath' file is
	 *            ignored. If set to <code>false</code> the given default
	 *            buildpath and output location is only used if no '.buildpath'
	 *            exists.
	 */
	public void init(IScriptProject jproject, IBuildpathEntry[] defaultEntries,
			boolean defaultsOverrideExistingBuildpath) {
		if (!defaultsOverrideExistingBuildpath && jproject.exists()
				&& jproject.getProject().getFile(".buildpath").exists()) { //$NON-NLS-1$			
			defaultEntries = null;
		}
		getBuildPathsBlock().init(jproject, defaultEntries);
		fScriptProject = jproject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see WizardPage#createControl
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayout(new GridLayout(1, false));
		Control control = getBuildPathsBlock().createControl(composite);
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Dialog.applyDialogFont(composite);
		if (DLTKCore.DEBUG) {
			System.err.println("Add help support here..."); //$NON-NLS-1$
		}

		// PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
		// IDLTKHelpContextIds.NEW_JAVAPROJECT_WIZARD_PAGE);
		setControl(composite);
	}

	/**
	 * Returns the currently configured buildpath. Note that the buildpath might
	 * not be valid.
	 * 
	 * @return the currently configured buildpath
	 */
	public IBuildpathEntry[] getRawBuildPath() {
		return getBuildPathsBlock().getRawBuildPath();
	}

	/**
	 * Returns the DLTK project that was passed in
	 * {@link #init(IScriptProject, IPath, IBuildpathEntry[], boolean)} or
	 * <code>null</code> if the page has not been initialized yet.
	 * 
	 * @return the managed script project or <code>null</code>
	 */
	public IScriptProject getScriptProject() {
		return fScriptProject;
	}

	protected abstract String getScriptNature();

	/**
	 * Returns the runnable that will create the script project or
	 * <code>null</code> if the page has not been initialized. The runnable sets
	 * the project's buildpath and output location to the values configured in
	 * the page and adds the script nature if not set yet. The method requires
	 * that the project is created and opened.
	 * 
	 * @return the runnable that creates the new script project
	 */
	public IRunnableWithProgress getRunnable() {
		if (getScriptProject() != null) {
			return new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					try {
						configureScriptProject(monitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
				}
			};
		}
		return null;
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
	public static void createProject(IProject project, URI locationURI,
			IProgressMonitor monitor) throws CoreException {
		BuildpathsBlock.createProject(project, locationURI, monitor);
	}

	/**
	 * Adds the script nature to the project (if not set yet) and configures the
	 * build buildpath.
	 * 
	 * @param monitor
	 *            a progress monitor to report progress or <code>null</code> if
	 *            progress reporting is not desired
	 * @throws CoreException
	 *             Thrown when the configuring the script project failed.
	 * @throws InterruptedException
	 *             Thrown when the operation has been canceled.
	 */
	public void configureScriptProject(IProgressMonitor monitor)
			throws CoreException, InterruptedException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		int nSteps = 6;
		monitor
				.beginTask(
						NewWizardMessages.ScriptCapabilityConfigurationPage_op_desc_Script,
						nSteps);

		try {
			IProject project = getScriptProject().getProject();
			BuildpathsBlock.addScriptNature(project, new SubProgressMonitor(
					monitor, 1), getScriptNature());
			getBuildPathsBlock().configureScriptProject(
					new SubProgressMonitor(monitor, 5));
		} catch (OperationCanceledException e) {
			throw new InterruptedException();
		} finally {
			monitor.done();
		}
	}
}
