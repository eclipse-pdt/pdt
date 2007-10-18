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
package org.eclipse.php.internal.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class PHPFileCreationWizard extends Wizard implements INewWizard {

	protected PHPFileCreationWizardPage phpFileCreationWizardPage;
	private ISelection selection;
	protected NewPhpTemplatesWizardPage newPhpTemplatesWizardPage;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public PHPFileCreationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		phpFileCreationWizardPage = new PHPFileCreationWizardPage(selection);
		addPage(phpFileCreationWizardPage);

		newPhpTemplatesWizardPage = new NewPhpTemplatesWizardPage();
		addPage(newPhpTemplatesWizardPage);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = phpFileCreationWizardPage.getContainerName();
		final String fileName = phpFileCreationWizardPage.getFileName();
		final String contents = this.newPhpTemplatesWizardPage.getTemplateString();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					FileCreator.createFile(PHPFileCreationWizard.this, containerName, fileName, monitor, contents);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), PHPUIMessages.getString("PHPFileCreationWizard.0"), realException.getMessage()); //$NON-NLS-1$
			return false;
		}
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * A static nested class for the creation of a new PHP File.
	 * @author yaronm
	 *
	 */
	public static class FileCreator {

		/**
		 * The worker method. It will find the container, create the
		 * file if missing or just replace its contents, and open
		 * the editor on the newly created file.
		 * This method does not take an editor id to use when opening the file.
		 * 
		 * @param wizard
		 * @param containerName
		 * @param fileName
		 * @param monitor
		 * @param contents
		 * @throws CoreException
		 * @see {@link #createFile(Wizard, String, String, IProgressMonitor, String, String)}
		 */
		public static void createFile(Wizard wizard, String containerName, String fileName, IProgressMonitor monitor, String contents) throws CoreException {
			createFile(wizard, containerName, fileName, monitor, null);
		}

		/**
		 * The worker method. It will find the container, create the
		 * file if missing or just replace its contents, and open
		 * the editor on the newly created file.
		 * 
		 * @param wizard
		 * @param containerName
		 * @param fileName
		 * @param monitor
		 * @param contents
		 * @param editorID An optional editor ID to use when opening the file (can be null).
		 * @throws CoreException
		 */
		public static void createFile(Wizard wizard, String containerName, String fileName, IProgressMonitor monitor, String contents, final String editorID) throws CoreException {
			// create a sample file
			monitor.beginTask(NLS.bind(PHPUIMessages.getString("newPhpFile_create"), fileName), 2);
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(containerName));
			if (!resource.exists() || !(resource instanceof IContainer)) {
				throwCoreException(PHPUIMessages.getString("PHPFileCreationWizard.1") + containerName + PHPUIMessages.getString("PHPFileCreationWizard.2")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			IContainer container = (IContainer) resource;
			final IFile file = container.getFile(new Path(fileName));

			// adopt project's/workspace's line delimiter (separator)
			String lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { new ProjectScope(container.getProject()) });
			if (lineSeparator == null)
				lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { new InstanceScope() });
			if (lineSeparator == null)
				lineSeparator = System.getProperty(Platform.PREF_LINE_SEPARATOR);
			if (contents != null) {
				contents = contents.replaceAll("(\n\r?|\r\n?)", lineSeparator); //$NON-NLS-1$ //$NON-NLS-2$	
			}

			try {
				InputStream stream = openContentStream(contents);
				if (file.exists()) {
					file.setContents(stream, true, true, monitor);
				} else {
					file.create(stream, true, monitor);
				}
				stream.close();
			} catch (IOException e) {
			}

			// Change file encoding:
			/*if (container instanceof IProject) {
				PHPProjectOptions options = PHPProjectOptions.forProject((IProject) container);
				if (options != null) {
					String defaultEncoding = (String) options.getOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
					if (defaultEncoding == null || defaultEncoding.length() == 0) {
						defaultEncoding = container.getDefaultCharset();
					}
					file.setCharset(defaultEncoding, monitor);
				}
			}*/

			monitor.worked(1);
			monitor.setTaskName(NLS.bind(PHPUIMessages.getString("newPhpFile_openning"), fileName));
			wizard.getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						if (editorID == null) {
							IDE.openEditor(page, file, true);
						} else {
							IDE.openEditor(page, file, editorID, true);
						}
					} catch (PartInitException e) {
					}
				}
			});
			monitor.worked(1);
		}

		/**
		 * We will initialize file contents with a sample text.
		 */
		private static InputStream openContentStream(String contents) {
			if (contents == null) {
				contents = ""; //$NON-NLS-1$
			}

			return new ByteArrayInputStream(contents.getBytes());
		}

		private static void throwCoreException(String message) throws CoreException {
			IStatus status = new Status(IStatus.ERROR, PHPUIMessages.getString("PHPFileCreationWizard.4"), IStatus.OK, message, null); //$NON-NLS-1$
			throw new CoreException(status);
		}
	}

}