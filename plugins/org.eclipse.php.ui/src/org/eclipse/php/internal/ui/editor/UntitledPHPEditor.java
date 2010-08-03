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
package org.eclipse.php.internal.ui.editor;

import java.io.File;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.dialogs.saveFiles.SaveAsDialog;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.editors.text.NLSUtility;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * This editor was created to handle editing of untitled PHP documents (with no
 * existing files)
 * 
 * @author yaronm
 */
public class UntitledPHPEditor extends PHPStructuredEditor {

	public static final String ID = "org.eclipse.php.untitledPhpEditor"; //$NON-NLS-1$

	/**
	 * Overrides
	 */
	public void doSave(IProgressMonitor progressMonitor) {
		performSaveAs(progressMonitor);
	}

	/**
	 * Overrides
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		final IEditorInput input = getEditorInput();

		IDocumentProvider provider = getDocumentProvider();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalName(input.getName());
		dialog.create();

		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		IPath newPath = dialog.getResult();
		if (newPath == null) {
			if (progressMonitor != null)
				progressMonitor.setCanceled(true);
			return;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(newPath);
		final IEditorInput newInput = new FileEditorInput(file);

		if (provider == null) {
			// editor has programmatically been closed while the dialog was open
			return;
		}

		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			provider.saveDocument(progressMonitor, newInput, provider
					.getDocument(input), true);
			success = true;

		} catch (CoreException x) {
			Logger.logException(x);
			final IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				String title = PHPUIMessages.UntitledPHPEditor_saveError; //$NON-NLS-1$
				String msg = NLSUtility.format(
						PHPUIMessages.UntitledPHPEditor_documentCannotBeSaved,
						x.getMessage()); //$NON-NLS-1$
				MessageDialog.openError(shell, title, msg);
			}
		} finally {
			// 1. close the untitled document
			// 2. open the saved file from its new path
			close(false);
			try {
				IWorkbenchPage p = DLTKUIPlugin.getActivePage();
				if (p != null) {
					IDE.openEditor(p, newInput, PHPUiConstants.PHP_EDITOR_ID);
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}

		deleteUntitledStorageFile(file);

		if (progressMonitor != null)
			progressMonitor.setCanceled(!success);

		return;
	}

	@Override
	public void dispose() {
		deleteUntitledStorageFile(null);
		super.dispose();
	}

	/**
	 * This method removes file created as an input for untitled php file, from
	 * external storage
	 * 
	 * @param newFile
	 *            New file in the workspace
	 */
	private void deleteUntitledStorageFile(IFile newFile) {
		// delete temporary file
		IPath oldPath = ((NonExistingPHPFileEditorInput) getEditorInput())
				.getPath(getEditorInput());
		File oldFile = new File(oldPath.toOSString());
		if (oldFile.exists() && oldFile.canWrite()) {
			if (!oldFile.delete()) {
				Logger.log(Logger.WARNING,
						PHPUIMessages.UntitledPHPEditor_deleteFailed); //$NON-NLS-1$
			}
		}

		// delete unneeded editor input:
		NonExistingPHPFileEditorInput.dispose(oldPath);

		// copy markers
		IWorkspaceRoot resource = ResourcesPlugin.getWorkspace().getRoot();
		try {
			IMarker[] markers = resource.findMarkers(null, true,
					IResource.DEPTH_ZERO);
			final IBreakpointManager breakpointManager = DebugPlugin
					.getDefault().getBreakpointManager();
			for (IMarker marker : markers) {
				String markerType = MarkerUtilities.getMarkerType(marker);
				if (markerType != null) {
					String fileName = (String) marker
							.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
					if (fileName != null && new File(fileName).equals(oldFile)) {
						IBreakpoint breakpoint = breakpointManager
								.getBreakpoint(marker);
						if (breakpoint != null) {
							if (newFile != null) {
								IMarker createdMarker = newFile
										.createMarker(markerType);
								createdMarker.setAttributes(breakpoint
										.getMarker().getAttributes());
								breakpointManager.removeBreakpoint(breakpoint,
										true);
								breakpoint.setMarker(createdMarker);
								breakpointManager.addBreakpoint(breakpoint);
							} else {
								breakpointManager.removeBreakpoint(breakpoint,
										true);
							}
						} else {
							if (newFile != null) {
								MarkerUtilities.createMarker(newFile, marker
										.getAttributes(), markerType);
							}
						}
					}
				}
				marker.delete();
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}
}