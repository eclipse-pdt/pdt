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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
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
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
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
		 * @param contents 
		 */
		public static void createFile(Wizard wizard, String containerName, String fileName, IProgressMonitor monitor, String contents) throws CoreException {
			// create a sample file
			monitor.beginTask(NLS.bind(PHPUIMessages.newPhpFile_create, fileName), 2);
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(containerName));
			if (!resource.exists() || !(resource instanceof IContainer)) {
				throwCoreException("Container \"" + containerName + "\" does not exist.");
			}
			IContainer container = (IContainer) resource;
			final IFile file = container.getFile(new Path(fileName));
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
			if (container instanceof IProject) {
				PHPProjectOptions options = PHPProjectOptions.forProject((IProject)container);
				if (options != null) {
					String defaultEncoding = (String) options.getOption(PHPCoreConstants.PHPOPTION_DEFAULT_ENCODING);
					if (defaultEncoding == null || defaultEncoding.length() == 0) {
						defaultEncoding = container.getDefaultCharset();
					}
					file.setCharset(defaultEncoding, monitor);
				}
			}
			
			monitor.worked(1);
			monitor.setTaskName(NLS.bind(PHPUIMessages.newPhpFile_openning, fileName));
			wizard.getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IDE.openEditor(page, file, true);
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
				contents = "";
			}
				
			return new ByteArrayInputStream(contents.getBytes());
		}

		private static void throwCoreException(String message) throws CoreException {
			IStatus status = new Status(IStatus.ERROR, "TestProject", IStatus.OK, message, null);
			throw new CoreException(status);
		}
	}
	
}