/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - integration with CorrectionCommandInstaller
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.correction.proposals;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.text.correction.CorrectionMessages;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;

/**
 * Implementation of a PHP completion proposal to be used for quick fix and
 * quick assist proposals that invoke a {@link Change}. The proposal offers a
 * proposal information but no context information.
 * 
 * @since 3.2
 */
public class ChangeCorrectionProposal extends AbstractCorrectionProposal {

	private Change fChange;

	public ChangeCorrectionProposal(String name, Change change, int relevance, Image image) {
		this(name, change, relevance, image, null);
	}

	/**
	 * Constructs a change correction proposal.
	 * 
	 * @param name
	 *            The name that is displayed in the proposal selection dialog.
	 * @param change
	 *            The change that is executed when the proposal is applied or
	 *            <code>null</code> if the change will be created by
	 *            implementors of {@link #createChange()}.
	 * @param relevance
	 *            The relevance of this proposal.
	 * @param image
	 *            The image that is displayed for this proposal or
	 *            <code>null</code> if no image is desired.
	 * @param commandId
	 * 
	 * @since 3.6
	 */
	public ChangeCorrectionProposal(String name, Change change, int relevance, Image image, String commandId) {
		super(name, relevance, image, commandId);
		fChange = change;
	}

	@Override
	public void apply(IDocument document) {
		try {
			performChange(PHPUiPlugin.getActivePage().getActiveEditor(), document);
		} catch (CoreException e) {
			ExceptionHandler.handle(e, CorrectionMessages.ChangeCorrectionProposal_error_title,
					CorrectionMessages.ChangeCorrectionProposal_error_message);
		}
	}

	/**
	 * Performs the change associated with this proposal.
	 * 
	 * @param activeEditor
	 *            The editor currently active or <code>null</code> if no editor
	 *            is active.
	 * @param document
	 *            The document of the editor currently active or
	 *            <code>null</code> if no editor is visible.
	 * @throws CoreException
	 *             Thrown when the invocation of the change failed.
	 */
	protected void performChange(IEditorPart activeEditor, IDocument document) throws CoreException {
		Change change = null;
		IRewriteTarget rewriteTarget = null;
		try {
			change = getChange();
			if (change != null) {
				if (document != null) {
					LinkedModeModel.closeAllModels(document);
				}
				if (activeEditor != null) {
					rewriteTarget = (IRewriteTarget) activeEditor.getAdapter(IRewriteTarget.class);
					if (rewriteTarget != null) {
						rewriteTarget.beginCompoundChange();
					}
				}

				change.initializeValidationData(new NullProgressMonitor());
				RefactoringStatus valid = change.isValid(new NullProgressMonitor());
				if (valid.hasFatalError()) {
					IStatus status = new Status(IStatus.ERROR, PHPUiPlugin.ID, IStatus.ERROR,
							valid.getMessageMatchingSeverity(RefactoringStatus.FATAL), null);
					throw new CoreException(status);
				} else {
					IUndoManager manager = RefactoringCore.getUndoManager();
					Change undoChange;
					boolean successful = false;
					try {
						manager.aboutToPerformChange(change);
						undoChange = change.perform(new NullProgressMonitor());
						successful = true;
					} finally {
						manager.changePerformed(change, successful);
					}
					if (undoChange != null) {
						undoChange.initializeValidationData(new NullProgressMonitor());
						manager.addUndo(getName(), undoChange);
					}
				}
			}
		} finally {
			if (rewriteTarget != null) {
				rewriteTarget.endCompoundChange();
			}

			if (change != null) {
				change.dispose();
			}
		}
	}

	@Override
	public String getAdditionalProposalInfo() {
		Object info = getAdditionalProposalInfo(new NullProgressMonitor());
		return info == null ? null : info.toString();
	}

	/**
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposalExtension5#
	 *      getAdditionalProposalInfo(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		StringBuilder buf = new StringBuilder();
		buf.append("<p>"); //$NON-NLS-1$
		try {
			Change change = getChange();
			if (change != null) {
				String name = change.getName();
				if (name.length() == 0) {
					return null;
				}
				buf.append(name);
			} else {
				return null;
			}
		} catch (CoreException e) {
			buf.append(CorrectionMessages.ChangeCorrectionProposal_2);
			buf.append(e.getLocalizedMessage());
			buf.append("</pre>"); //$NON-NLS-1$
		}
		buf.append("</p>"); //$NON-NLS-1$
		return buf.toString();
	}

	/**
	 * Returns the change that will be executed when the proposal is applied.
	 * 
	 * @return returns the change for this proposal.
	 * @throws CoreException
	 *             thrown when the change could not be created
	 */
	public final Change getChange() throws CoreException {
		if (fChange == null) {
			fChange = createChange();
		}
		return fChange;
	}

	/**
	 * Creates the text change for this proposal. This method is only called
	 * once and only when no text change has been passed in
	 * {@link #ChangeCorrectionProposal(String, Change, int, Image)}.
	 * 
	 * @return returns the created change.
	 * @throws CoreException
	 *             thrown if the creation of the change failed.
	 */
	protected Change createChange() throws CoreException {
		return new NullChange();
	}
}
