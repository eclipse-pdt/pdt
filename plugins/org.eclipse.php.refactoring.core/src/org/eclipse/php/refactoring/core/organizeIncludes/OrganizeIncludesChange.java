/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.ltk.core.refactoring.*;
import org.eclipse.php.refactoring.core.PHPRefactoringCoreMessages;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;

class OrganizeIncludesChange extends TextFileChange {
	private OrganizeIncludesProcessorDelegate processorDelegate;

	private Map<String, TextEditChangeGroup> editGroups = new LinkedHashMap<>();

	private int includeInsertionOffset;

	public OrganizeIncludesChange(OrganizeIncludesProcessorDelegate processorDelegate) {
		super(MessageFormat.format(PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_In_File"), //$NON-NLS-1$
				new Object[] { processorDelegate.getFile().getFullPath().toString() }), processorDelegate.getFile());
		// 0. Initialize the change:
		setTextType("php"); //$NON-NLS-1$
		setEdit(new MultiTextEdit());
		this.processorDelegate = processorDelegate;
	}

	// public void addEdits(BucketMap<String, CodeData> existingIncludes,
	// BucketMap<PHPFileData, CodeData> missingHardIncludes,
	// BucketMap<PHPFileData, CodeData> missingSoftIncludes,
	// List<PHPIncludeFileData> unneededIncludes, List<PHPIncludeFileData>
	// unresolvedIncludes) {
	// // 1. Calculate insertion offset:
	// includeInsertionOffset =
	// OrganizeIncludesUtils.getFirstIncludeOffset(processorDelegate.fileData);
	// if (includeInsertionOffset < 0)
	// return;
	//
	// // 2. Add necessary edits:
	// addMissingIncludes(missingHardIncludes, true);
	// deleteUnneededIncludes(unresolvedIncludes, true);
	//
	// // 3. Add optional edits:
	// addMissingIncludes(missingSoftIncludes, false);
	// deleteUnneededIncludes(unneededIncludes, false);
	// moveExistingIncludes(existingIncludes);
	//
	// // 4. Commit the edits:
	// commitEditGroups();
	//
	// }

	private void commitEditGroups() {
		for (TextEditChangeGroup editChangeGroup : editGroups.values()) {
			TextEdit[] edits = editChangeGroup.getTextEdits();
			for (TextEdit edit : edits) {
				addEdit(edit);
			}
			addTextEditChangeGroup(editChangeGroup);
		}
	}

	// private DeleteEdit createDeleteIncludeEdit(PHPIncludeFileData include) {
	// int startOffset = include.getUserData().getStartPosition();
	// int endOffset = include.getUserData().getEndPosition();
	//
	// TextSequence currentStatement =
	// PHPTextSequenceUtilities.getStatement(endOffset,
	// processorDelegate.document.getRegionAtCharacterOffset(endOffset), false);
	// TextSequence nextStatement = currentStatement;
	// // find next statement:
	// for (; nextStatement != null && endOffset <
	// processorDelegate.document.getLength() &&
	// currentStatement.getOriginalOffset(0) ==
	// nextStatement.getOriginalOffset(0); ++endOffset) {
	// nextStatement = PHPTextSequenceUtilities.getStatement(endOffset,
	// processorDelegate.document.getRegionAtCharacterOffset(endOffset), false);
	// }
	// if (nextStatement != null) {
	// for (; endOffset < processorDelegate.document.getLength(); endOffset++) {
	// try {
	// if
	// (!Character.isWhitespace(processorDelegate.document.getChar(endOffset)))
	// {
	// break;
	// }
	// } catch (BadLocationException e) {
	// endOffset--;
	// }
	// }
	// }
	//
	// return new DeleteEdit(startOffset, endOffset - startOffset);
	// }
	//
	// private TextEditChangeGroup addIncludeRelocation(String insertionLabel,
	// String includeString, List<PHPIncludeFileData> existingIncludes) {
	// // create and add the edit:
	// List<TextEdit> allEdits = new ArrayList<TextEdit>();
	// allEdits.add(createInsertIncludeEdit(includeString));
	// for (PHPIncludeFileData include : existingIncludes) {
	// allEdits.add(createDeleteIncludeEdit(include));
	// }
	// // add the group edit:
	// CategorizedTextEditGroup editGroup = new
	// CategorizedTextEditGroup(insertionLabel, new GroupCategorySet(new
	// GroupCategory("relocateIncludes",
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Movements"),
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Movements"))));
	// //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	//
	// return createChangeEditGroup(allEdits.toArray(new
	// TextEdit[allEdits.size()]), editGroup, false);
	// }
	//
	// private void moveExistingIncludes(BucketMap<String, CodeData>
	// existingIncludes) {
	// boolean changeNeeded = false;
	// if (existingIncludes.getAll().size() > 1)
	// changeNeeded = true;
	// PHPIncludeFileData[] includes =
	// processorDelegate.fileNode.getFile().getIncludeFiles();
	// for (String existingInclude : existingIncludes.getKeys()) {
	// String includeLocation =
	// PHPModelUtil.getRelativeLocation(processorDelegate.getProject(),
	// existingInclude);
	// String editGroupName =
	// OrganizeIncludesUtils.getEditGroupName(PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Move_Up"),
	// includeLocation, existingIncludes.get(existingInclude)); //$NON-NLS-1$
	// Collection<IPath> includePaths =
	// processorDelegate.fileNode.getIncludePaths(processorDelegate.network.getNode(processorDelegate.projectModel.getFileData(existingInclude)));
	// List<PHPIncludeFileData> identicalIncludes = new
	// ArrayList<PHPIncludeFileData>();
	// String finalIncludeLocation = null;
	// if (includes.length > 1)
	// changeNeeded = true;
	// for (PHPIncludeFileData element : includes) {
	// for (IPath path : includePaths) {
	// String includeString = IncludeStringHack.unhack(path.toString());
	// String firstSegment = path.segment(0);
	// if (finalIncludeLocation == null && !"||".equals(firstSegment) &&
	// !"|".equals(firstSegment)) // not relative //$NON-NLS-1$ //$NON-NLS-2$
	// finalIncludeLocation = includeString;
	// if (new Path(element.getName()).equals(new Path(includeString)))
	// identicalIncludes.add(element);
	// }
	// }
	// if (!changeNeeded)
	// return;
	// if (finalIncludeLocation == null)
	// finalIncludeLocation = includeLocation;
	// TextEditChangeGroup editChangeGroup = addIncludeRelocation(editGroupName,
	// finalIncludeLocation, identicalIncludes);
	// editGroups.put(includeLocation, editChangeGroup);
	// }
	// }
	//
	// private void addMissingIncludes(BucketMap<PHPFileData, CodeData>
	// missingIncludes, boolean groupEnabled) {
	// for (PHPFileData missingInclude : missingIncludes.getKeys()) {
	// String includeLocation =
	// PHPModelUtil.getRelativeLocation(processorDelegate.getProject(),
	// missingInclude.getName());
	// String editGroupName =
	// OrganizeIncludesUtils.getEditGroupName(PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Add"),
	// includeLocation, missingIncludes.get(missingInclude)); //$NON-NLS-1$
	// TextEditChangeGroup editChangeGroup =
	// createIncludeAddition(editGroupName, includeLocation, groupEnabled);
	// editGroups.put(includeLocation, editChangeGroup);
	// }
	// }

	private TextEditChangeGroup createIncludeAddition(String insertionLabel, String includeString, boolean enabled) {
		InsertEdit edit = createInsertIncludeEdit(includeString);
		CategorizedTextEditGroup editGroup = new CategorizedTextEditGroup(insertionLabel,
				new GroupCategorySet(new GroupCategory(PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_5"), //$NON-NLS-1$
						PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Additions"), //$NON-NLS-1$
						PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Additions")))); //$NON-NLS-1$
		return createChangeEditGroup(new TextEdit[] { edit }, editGroup, enabled);
	}

	private TextEditChangeGroup createChangeEditGroup(TextEdit[] edits, CategorizedTextEditGroup editGroup,
			boolean enabled) {
		for (TextEdit element : edits) {
			editGroup.addTextEdit(element);
		}
		TextEditChangeGroup editChangeGroup = new TextEditChangeGroup(this, editGroup);
		editChangeGroup.setEnabled(enabled);
		return editChangeGroup;
	}

	private InsertEdit createInsertIncludeEdit(String includeString) {
		return new InsertEdit(includeInsertionOffset, MessageFormat.format("require_once ''{0}'';{1}", new Object[] { //$NON-NLS-1$
				includeString, processorDelegate.getDocument().getLineDelimiter() }));
	}

	// private void deleteUnneededIncludes(List<PHPIncludeFileData>
	// unneededIncludes, boolean unresolved) {
	// for (PHPIncludeFileData unneededInclude : unneededIncludes) {
	// String includeLocation = unneededInclude.getName();
	// String editGroupName = OrganizeIncludesUtils.getEditGroupName(
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Remove"),
	// includeLocation, unresolved ?
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Unresolved") :
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Possibly_Unused"));
	// //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	// TextEditChangeGroup editChangeGroup = editGroups.get(includeLocation);
	// if (editChangeGroup == null) {
	// editChangeGroup = createIncludeDeletion(editGroupName, unneededInclude,
	// unresolved);
	// editGroups.put(includeLocation, editChangeGroup);
	// } else {
	// addIncludeDeletion(editChangeGroup, unneededInclude);
	// }
	// }
	// }
	//
	// private void addIncludeDeletion(TextEditChangeGroup editChangeGroup,
	// PHPIncludeFileData unneededInclude) {
	// // create and add the edit:
	// TextEdit edit = createDeleteIncludeEdit(unneededInclude);
	// // add the group edit:
	// addEditsToGroup(editChangeGroup, new TextEdit[] { edit });
	// }
	//
	// private TextEditChangeGroup createIncludeDeletion(String insertionLabel,
	// PHPIncludeFileData unneededInclude, boolean groupEnabled) {
	// // create and add the edit:
	// TextEdit edit = createDeleteIncludeEdit(unneededInclude);
	// // add the group edit:
	// CategorizedTextEditGroup editGroup = new
	// CategorizedTextEditGroup(insertionLabel, new GroupCategorySet(new
	// GroupCategory("deleteIncludes",
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Deletions"),
	// PHPRefactoringCoreMessages.getString("OrganizeIncludesChange_Deletions"))));
	// //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	// return createChangeEditGroup(new TextEdit[] { edit }, editGroup,
	// groupEnabled);
	// }

	private static void addEditsToGroup(TextEditChangeGroup editChangeGroup, TextEdit[] edits) {
		for (TextEdit element : edits) {
			editChangeGroup.getTextEditGroup().addTextEdit(element);
		}
	}
}