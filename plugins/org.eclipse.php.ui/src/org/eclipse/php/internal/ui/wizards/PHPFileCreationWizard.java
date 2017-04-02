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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.ast.util.Util;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.PHPTemplateStore;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

public class PHPFileCreationWizard extends Wizard implements INewWizard {

	protected PHPFileCreationWizardPage phpFileCreationWizardPage;
	protected ISelection selection;
	protected NewPhpTemplatesWizardPage newPhpTemplatesWizardPage;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public PHPFileCreationWizard() {
		super();
		setWindowTitle(PHPUIMessages.PHPFileCreationWizard_5);
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages() {
		phpFileCreationWizardPage = new PHPFileCreationWizardPage(selection);
		addPage(phpFileCreationWizardPage);

		newPhpTemplatesWizardPage = new NewPhpTemplatesWizardPage();
		addPage(newPhpTemplatesWizardPage);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	@Override
	public boolean performFinish() {
		final String containerName = phpFileCreationWizardPage.getContainerFullPath().toString();
		final String fileName = phpFileCreationWizardPage.getFileName();
		newPhpTemplatesWizardPage.resetTableViewerInput();
		IScriptProject project = null;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(phpFileCreationWizardPage.getContainerFullPath());
		if (!resource.exists() || !(resource instanceof IContainer)) {
			project = DLTKCore.create(resource.getProject());
		}
		final IFile file = phpFileCreationWizardPage.createNewFile();
		if (file == null) {
			return false;
		}

		String lineSeparator = Util.getLineSeparator(null, project);
		final PHPTemplateStore.CompiledTemplate template = this.newPhpTemplatesWizardPage.compileTemplate(containerName,
				fileName, lineSeparator);

		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					new FileCreator().createFile(PHPFileCreationWizard.this, file, monitor, template.string,
							template.offset);
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
			MessageDialog.openError(getShell(), PHPUIMessages.PHPFileCreationWizard_0, realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * A static nested class for the creation of a new PHP File.
	 * 
	 * @author yaronm
	 * 
	 */
	public static class FileCreator {

		/**
		 * The worker method. It will find the container, create the file if
		 * missing or just replace its contents, and open the editor on the
		 * newly created file. This method does not take an editor id to use
		 * when opening the file.
		 * 
		 * @param wizard
		 * @param containerName
		 * @param fileName
		 * @param monitor
		 * @param contents
		 * @throws CoreException
		 * @see {@link #createFile(Wizard, IFile, IProgressMonitor, String, String)}
		 */
		public void createFile(Wizard wizard, IFile file, IProgressMonitor monitor, String contents)
				throws CoreException {
			createFile(wizard, file, monitor, contents, 0, null);
		}

		public void createFile(Wizard wizard, IFile file, IProgressMonitor monitor, String contents, int offset)
				throws CoreException {
			createFile(wizard, file, monitor, contents, offset, null);
		}

		/**
		 * The worker method. It will find the container, create the file if
		 * missing or just replace its contents, and open the editor on the
		 * newly created file.
		 * 
		 * @param wizard
		 * @param containerName
		 * @param fileName
		 * @param monitor
		 * @param contents
		 * @param editorID
		 *            An optional editor ID to use when opening the file (can be
		 *            null).
		 * @throws CoreException
		 */
		public void createFile(Wizard wizard, final IFile file, IProgressMonitor monitor, String contents,
				final int offset, final String editorID) throws CoreException {
			// create a sample file
			IContainer container = file.getParent();
			if (file != null) {
				if (!file.isLinked()) {
					// adopt project's/workspace's line delimiter (separator)
					String lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
							Platform.PREF_LINE_SEPARATOR, null,
							new IScopeContext[] { new ProjectScope(container.getProject()) });
					if (lineSeparator == null)
						lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
								Platform.PREF_LINE_SEPARATOR, null, new IScopeContext[] { InstanceScope.INSTANCE });
					if (lineSeparator == null)
						lineSeparator = System.getProperty(Platform.PREF_LINE_SEPARATOR);
					if (contents != null) {
						contents = contents.replaceAll("(\n\r?|\r\n?)", lineSeparator); //$NON-NLS-1$
					}

					try {
						InputStream stream = openContentStream(contents, getCharSetValue(container));
						if (file.exists()) {
							file.setContents(stream, true, true, monitor);
						} else {
							file.create(stream, true, monitor);
						}
						stream.close();
					} catch (IOException e) {
						Logger.logException(e);
						return;
					}

				}
				monitor.worked(1);
				monitor.setTaskName(NLS.bind(PHPUIMessages.newPhpFile_openning, file.getName()));
				wizard.getShell().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try {
							normalizeFile(file);
							IEditorPart editor;
							if (editorID == null) {
								editor = IDE.openEditor(page, file, true);
							} else {
								editor = IDE.openEditor(page, file, editorID, true);
							}
							if (editor instanceof PHPStructuredEditor) {
								StructuredTextViewer textViewer = ((PHPStructuredEditor) editor).getTextViewer();
								textViewer.setSelectedRange(offset, 0);
							}
						} catch (PartInitException e) {
						}
					}
				});
				monitor.worked(1);
			}

		}

		/**
		 * We will initialize file contents with a sample text.
		 */
		private static InputStream openContentStream(String contents, String charSet) {
			if (contents == null) {
				contents = ""; //$NON-NLS-1$
			}
			byte[] bytes;
			try {
				bytes = contents.getBytes(charSet);
			} catch (UnsupportedEncodingException e) {
				bytes = contents.getBytes();
			}

			return new ByteArrayInputStream(bytes);
		}

		protected String getCharSetValue(IContainer container) {
			try {
				return container.getDefaultCharset(true);
			} catch (CoreException e) {// If there is an error return the
										// default
				return WorkbenchEncoding.getWorkbenchDefaultEncoding();
			}

		}

		/**
		 * @param file
		 */
		protected void normalizeFile(IFile file) {
		}

	}

	public IProject getProject() {
		if (this.phpFileCreationWizardPage != null) {
			return phpFileCreationWizardPage.getProject();
		}
		return null;
	}

}