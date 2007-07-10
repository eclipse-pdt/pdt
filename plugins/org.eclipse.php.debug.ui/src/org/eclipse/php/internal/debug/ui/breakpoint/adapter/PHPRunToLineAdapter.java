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
package org.eclipse.php.internal.debug.ui.breakpoint.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ISuspendResume;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.actions.RunToLineHandler;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.model.PHPRunToLineBreakpoint;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.breakpoint.provider.PHPBreakpointProvider;
import org.eclipse.php.internal.ui.util.StatusLineMessageTimerManager;
import org.eclipse.ui.*;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Run to line target for the PHP debugger
 */
public class PHPRunToLineAdapter implements IRunToLineTarget {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.actions.IRunToLineTarget#runToLine(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection,
	 *      org.eclipse.debug.core.model.ISuspendResume)
	 */
	public void runToLine(IWorkbenchPart part, ISelection selection, ISuspendResume target) throws CoreException {
		IEditorPart editorPart = (IEditorPart) part;
		IEditorInput input = editorPart.getEditorInput();
		String errorMessage = null;
		if (input == null) {
			errorMessage = PHPDebugUIMessages.PHPRunToLineAdapter_0;
		} else {
			ITextEditor textEditor = (ITextEditor) editorPart;
			IDocument document = textEditor.getDocumentProvider().getDocument(input);
			if (document == null) {
				errorMessage = PHPDebugUIMessages.PHPRunToLineAdapter_1;
			} else {
				ITextSelection textSelection = (ITextSelection) selection;

				int lineNumber = 0;
				try {
					lineNumber = document.getLineOfOffset(textSelection.getOffset()) + 1;
				} catch (BadLocationException e) {
				}
				// Figure out if the selected line is a valid line to place a temporary breakpoint for the run-to-line
				int validLinePosition = PHPBreakpointProvider.getValidPosition(document, lineNumber);
				if (validLinePosition < 0) {
					StatusLineMessageTimerManager.setErrorMessage(PHPDebugUIMessages.CannotRunToLine, 1000, true); // hide message after 1 second
					return;
				} else {
					int validLineNumber = 0;
					try {
						validLineNumber = document.getLineOfOffset(validLinePosition) + 1;
						if (validLineNumber != lineNumber) {
							StatusLineMessageTimerManager.setErrorMessage(PHPDebugUIMessages.CannotRunToLine, 1000, true); // hide message after 1 second
							return;
						}
					} catch (BadLocationException ble) {
						StatusLineMessageTimerManager.setErrorMessage(PHPDebugUIMessages.CannotRunToLine, 1000, true); // hide message after 1 second
						return;
					}
				}
				if (lineNumber > 0) {
					if (getValidPosition(document, lineNumber) != -1) {
						if (target instanceof IAdaptable) {
							IDebugTarget debugTarget = (IDebugTarget) ((IAdaptable) target).getAdapter(IDebugTarget.class);
							if (debugTarget != null) {
								IFile file = getFile(textEditor);
								IBreakpoint breakpoint = new PHPRunToLineBreakpoint(file, lineNumber);
								RunToLineHandler handler = new RunToLineHandler(debugTarget, target, breakpoint);
								handler.run(new NullProgressMonitor());
								return;
							}
						}
					} else {
						errorMessage = PHPDebugUIMessages.PHPRunToLineAdapter_2;
					}
				} else {
					errorMessage = PHPDebugUIMessages.PHPRunToLineAdapter_2;
				}
			}
		}
		throw new CoreException(new Status(IStatus.ERROR, PHPDebugUIPlugin.getID(), IPHPConstants.INTERNAL_ERROR, errorMessage, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.actions.IRunToLineTarget#canRunToLine(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection,
	 *      org.eclipse.debug.core.model.ISuspendResume)
	 */
	public boolean canRunToLine(IWorkbenchPart part, ISelection selection, ISuspendResume target) {
		if (target instanceof PHPDebugElement) {
			// allow running to the line only when the target is suspended.
			return target.isSuspended();
		}
		return false;
	}

	/**
	 * The file input for the TextEditor
	 * 
	 * @return the IFile that this strategy is operating on
	 */
	protected IFile getFile(ITextEditor textEditor) {
		if (textEditor == null)
			return null;
		IEditorInput input = textEditor.getEditorInput();
		if (input instanceof IFileEditorInput) {
			return ((IFileEditorInput) input).getFile();
		}
		if (input instanceof IURIEditorInput) {
			String filePath = ((IURIEditorInput)input).getURI().getPath();
			IFile result = ExternalFilesRegistry.getInstance().getFileEntry(filePath);
			if (result == null && filePath.length() > 0 && filePath.charAt(0) == '/') {
				return ExternalFilesRegistry.getInstance().getFileEntry(filePath.substring(1));
			}
			return result;
		}
		return null;
		
	}

	/**
	 * Finds a valid position somewhere on lineNumber in document, idoc, where a
	 * breakpoint can be set and returns that position. -1 is returned if a
	 * position could not be found.
	 * 
	 * @param idoc
	 * @param editorLineNumber
	 * @return position to set breakpoint or -1 if no position could be found
	 */
	private int getValidPosition(IDocument idoc, int editorLineNumber) {
		int result = -1;
		if (idoc != null) {

			int startOffset = 0;
			int endOffset = 0;
			try {
				IRegion line = idoc.getLineInformation(editorLineNumber - 1);
				startOffset = line.getOffset();
				endOffset = Math.max(line.getOffset(), line.getOffset() + line.getLength());

				String lineText = idoc.get(startOffset, endOffset - startOffset).trim();

				// blank lines or lines with only an open PHP
				// tags cannot have a breakpoint

				if (lineText.equals("") || lineText.equals("<%") || //$NON-NLS-1$ //$NON-NLS-2$ 
					lineText.equals("%>") || lineText.equals("<?php") || lineText.equals("?>") || (lineText.trim()).startsWith("//")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					result = -1;
				} else {

					// get all partitions for current line
					ITypedRegion[] partitions = null;

					partitions = idoc.computePartitioning(startOffset, endOffset - startOffset);

					for (int i = 0; i < partitions.length; ++i) {
						String type = partitions[i].getType();
						// if found PHP
						// return that position
						if (PHPStructuredTextPartitioner.isPHPPartitionType(type)) {
							result = partitions[i].getOffset();
						}
					}
				}
			} catch (BadLocationException e) {
				result = -1;
			}
		}

		return result;
	}
}
