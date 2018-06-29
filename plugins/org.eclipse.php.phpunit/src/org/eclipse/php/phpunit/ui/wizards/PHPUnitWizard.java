/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.php.internal.ui.wizards.PHPFileCreationWizard;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.PHPUnitSearchEngine;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public abstract class PHPUnitWizard extends PHPFileCreationWizard {

	protected static final String DIALOG_SETTINGS_KEY = "PHPUnitWizards"; //$NON-NLS-1$

	private IStructuredSelection fSelection;
	private IWorkbench fWorkbench;
	protected PHPUnitWizardPage page;

	public PHPUnitWizard() {
		super();
		initDialogSettings();
	}

	protected boolean finishPage(final IRunnableWithProgress runnable) {
		final IRunnableWithProgress op = new WorkspaceModifyDelegatingOperation(runnable);
		try {
			PlatformUI.getWorkbench().getProgressService().runInUI(getContainer(), op,
					ResourcesPlugin.getWorkspace().getRoot());

		} catch (final InvocationTargetException e) {
			final Shell shell = getShell();
			final String title = PHPUnitMessages.PHPUnitWizard_Title;
			final String message = PHPUnitMessages.PHPUnitWizard_Error;
			ExceptionHandler.handle(e, shell, title, message);
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	abstract String generateFile();

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		fWorkbench = workbench;
		fSelection = selection;
	}

	protected void initDialogSettings() {
		final IDialogSettings pluginSettings = PHPUnitPlugin.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = pluginSettings.getSection(DIALOG_SETTINGS_KEY);
		if (wizardSettings == null) {
			wizardSettings = new DialogSettings(DIALOG_SETTINGS_KEY);
			pluginSettings.addSection(wizardSettings);
		}
		setDialogSettings(wizardSettings);
	}

	@Override
	public boolean performFinish() {
		final IRunnableWithProgress op = monitor -> {
			IProject project = page.getTestContainer().getProject();
			final IScriptProject scriptProject = DLTKCore.create(project);

			PHPUnitSearchEngine fSearchEngine = new PHPUnitSearchEngine(scriptProject);
			if (!fSearchEngine.hasTestCaseClass()) {
				ModelManager.getModelManager().getIndexManager().waitUntilReady();
			}

			Display.getDefault().syncExec(() -> page.superClassChanged());
			String generatedFile = generateFile();

			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final IFile file = root.getFile(page.getTestContainer().getFullPath().append(page.getFileName()));
			try {
				new FileCreator() {
					@Override
					protected void normalizeFile(IFile file) {
						super.normalizeFile(file);
						IContentFormatter formatter = PHPUiPlugin.getDefault().getActiveFormatter();
						try {
							IStructuredModel structuredModel = null;

							structuredModel = StructuredModelManager.getModelManager().getModelForEdit(file);
							if (structuredModel == null) {
								return;
							}
							try {
								// setup structuredModel
								// Note: We are getting model for edit. Will
								// save model if model
								// changed.
								IStructuredDocument structuredDocument = structuredModel.getStructuredDocument();

								IRegion region = new Region(0, structuredDocument.getLength());

								formatter.format(structuredDocument, region);
								structuredModel.save();
							} finally {
								// release from model manager
								if (structuredModel != null) {
									structuredModel.releaseFromEdit();
								}
							}
						} catch (IOException e) {
							PHPUnitPlugin.log(e);
						} catch (CoreException e) {
							PHPUnitPlugin.log(e);
						}
					}
				}.createFile(PHPUnitWizard.this, file, monitor, generatedFile);
			} catch (CoreException e) {
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			final Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), PHPUnitMessages.PHPUnitWizard_Error_Title, realException.getMessage());
			return false;
		}
		return true;
	}

	protected void selectAndReveal(final IResource newResource) {
		BasicNewResourceWizard.selectAndReveal(newResource, fWorkbench.getActiveWorkbenchWindow());
	}

	public IStructuredSelection getSelection() {
		return fSelection;
	}
}
