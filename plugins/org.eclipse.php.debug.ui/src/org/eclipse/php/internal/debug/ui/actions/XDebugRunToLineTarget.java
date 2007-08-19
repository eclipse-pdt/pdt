/*******************************************************************************
 * Copyright (c) 2006 Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ISuspendResume;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.jface.text.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpElement;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.breakpoint.provider.PHPBreakpointProvider;
import org.eclipse.php.internal.ui.util.StatusLineMessageTimerManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class XDebugRunToLineTarget implements IRunToLineTarget {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.debug.ui.breakpoint.adapter.PHPRunToLineAdapter#canRunToLine(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection, org.eclipse.debug.core.model.ISuspendResume)
	 */
	public boolean canRunToLine(IWorkbenchPart part, ISelection selection, ISuspendResume target) {
		if (target instanceof DBGpElement) {
			//TODO: Improvement: Check part and selection
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.debug.ui.breakpoint.adapter.PHPRunToLineAdapter#runToLine(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection, org.eclipse.debug.core.model.ISuspendResume)
	 */
	public void runToLine(IWorkbenchPart part, ISelection selection, ISuspendResume target) throws CoreException {
		IEditorPart editorPart = (IEditorPart) part;
		IEditorInput input = editorPart.getEditorInput();
		String errorMessage = null;
		if (input == null) {
			//PHPDebugUIMessages.PHPRunToLineAdapter_0         
			errorMessage = "Empty editor";
		} else {
			ITextEditor textEditor = (ITextEditor) editorPart;
			IDocument document = textEditor.getDocumentProvider().getDocument(input);
			if (document == null) {
				//PHPDebugUIMessages.PHPRunToLineAdapter_1            
				errorMessage = "Missing document";
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
					// PHPDebugUIMessages.CannotRunToLine
					StatusLineMessageTimerManager.setErrorMessage("Cannot run to the specified line.", 1000, true); // hide message after 1 second  
					return;
				} else {
					int validLineNumber = 0;
					try {
						validLineNumber = document.getLineOfOffset(validLinePosition) + 1;
						if (validLineNumber != lineNumber) {
							// PHPDebugUIMessages.CannotRunToLine                     
							StatusLineMessageTimerManager.setErrorMessage("Cannot run to the specified line.", 1000, true); // hide message after 1 second
							return;
						}
					} catch (BadLocationException ble) {
						// PHPDebugUIMessages.CannotRunToLine                  
						StatusLineMessageTimerManager.setErrorMessage("Cannot run to the specified line.", 1000, true); // hide message after 1 second
						return;
					}
				}
				if (lineNumber > 0) {
					if (getValidPosition(document, lineNumber) != -1) {
						if (target instanceof IAdaptable) {
							IDebugTarget debugTarget = (IDebugTarget) ((IAdaptable) target).getAdapter(IDebugTarget.class);
							if (debugTarget != null) {
								IFile file = getFile(textEditor);
								DBGpTarget t = (DBGpTarget) debugTarget;
								t.runToLine(file, lineNumber);
								// here we need to set a temporary breakpoint and resume the debugTarget
								return;
							}
						}
					} else {
						errorMessage = "Selected line is not a valid location to run to"; //PHPDebugUIMessages.PHPRunToLineAdapter_2
					}
				} else {
					errorMessage = "Selected line is not a valid location to run to"; //PHPDebugUIMessages.PHPRunToLineAdapter_2
				}
			}
		}
		throw new CoreException(new Status(IStatus.ERROR, PHPDebugUIPlugin.ID, IPHPConstants.INTERNAL_ERROR, errorMessage, null));

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
		if (!(input instanceof IFileEditorInput))
			return null;
		return ((IFileEditorInput) input).getFile();
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
