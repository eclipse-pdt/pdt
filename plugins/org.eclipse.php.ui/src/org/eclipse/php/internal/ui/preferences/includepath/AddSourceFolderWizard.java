/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.AddSourceFolderWizardPage;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathWizard;
import org.eclipse.dltk.ui.DLTKPluginImages;

public class AddSourceFolderWizard extends BuildPathWizard {

	private AddSourceFolderWizardPage fAddFolderPage;
	private final boolean fLinkedMode;
	private boolean fAllowConflict;
	private final boolean fAllowRemoveProjectFolder;
	private final boolean fAllowAddExclusionPatterns;
	private final boolean fCanCommitConflict;
	private final IContainer fParent;

	public AddSourceFolderWizard(BPListElement[] existingEntries,
			BPListElement newEntry, boolean linkedMode,
			boolean canCommitConflict, boolean allowConflict,
			boolean allowRemoveProjectFolder, boolean allowAddExclusionPatterns) {
		this(existingEntries, newEntry, linkedMode, canCommitConflict,
				allowConflict, allowRemoveProjectFolder,
				allowAddExclusionPatterns, newEntry.getScriptProject()
						.getProject());
	}

	public AddSourceFolderWizard(BPListElement[] existingEntries,
			BPListElement newEntry, boolean linkedMode,
			boolean canCommitConflict, boolean allowConflict,
			boolean allowRemoveProjectFolder,
			boolean allowAddExclusionPatterns, IContainer parent) {
		super(existingEntries, newEntry, getTitel(newEntry, linkedMode),
				DLTKPluginImages.DESC_WIZBAN_NEWSRCFOLDR);
		fLinkedMode = linkedMode;
		fCanCommitConflict = canCommitConflict;
		fAllowConflict = allowConflict;
		fAllowRemoveProjectFolder = allowRemoveProjectFolder;
		fAllowAddExclusionPatterns = allowAddExclusionPatterns;
		fParent = parent;
	}

	private static String getTitel(BPListElement newEntry, boolean linkedMode) {
		if (newEntry.getPath() == null) {
			if (linkedMode) {
				return NewWizardMessages.NewSourceFolderCreationWizard_link_title;
			} else {
				return NewWizardMessages.NewSourceFolderCreationWizard_title;
			}
		} else {
			return NewWizardMessages.NewSourceFolderCreationWizard_edit_title;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addPages() {
		super.addPages();

		fAddFolderPage = new AddSourceFolderWizardPage(getEntryToEdit(),
				getExistingEntries(), fLinkedMode, fCanCommitConflict,
				fAllowConflict, fAllowRemoveProjectFolder,
				fAllowAddExclusionPatterns, fParent);
		addPage(fAddFolderPage);

	}

	/**
	 * {@inheritDoc}
	 */
	public List getInsertedElements() {
		List result = super.getInsertedElements();
		if (getEntryToEdit().getOrginalPath() == null)
			result.add(getEntryToEdit());

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public List getRemovedElements() {
		return fAddFolderPage.getRemovedElements();
	}

	/**
	 * {@inheritDoc}
	 */
	public List getModifiedElements() {
		return fAddFolderPage.getModifiedElements();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean performFinish() {

		boolean res = super.performFinish();
		if (res) {
			selectAndReveal(fAddFolderPage.getCorrespondingResource());
		}
		return res;
	}

	public void cancel() {
		fAddFolderPage.restore();
	}
}
