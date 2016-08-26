package org.eclipse.php.internal.ui.editor.saveparticipant;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.core.manipulation.SourceModuleChange;
import org.eclipse.dltk.ui.editor.saveparticipant.IPostSaveListener;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.saveparticipant.SaveParticipantDescriptor;

public class RemoveTrailingWhitespacesSaveParticipant implements IPostSaveListener {

	public String getName() {
		return "Remove trailing whitespaces";
	}

	public String getId() {
		return ID;
	}

	public static final String ID = "RemoveTrailingWhitespaces";

	/**
	 * Preference prefix that is appended to the id of
	 * {@link SaveParticipantDescriptor save participants}.
	 * 
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String EDITOR_SAVE_PARTICIPANT_PREFIX = "editor_save_participant_"; //$NON-NLS-1$

	private boolean removeTrailingWhitespacesEnabled = false;
	private boolean ignoreEmptyLines = false;

	/*
	 * Gets the preferences set for this editor in the Save Actions section
	 */
	public void updateSaveActionsState(@Nullable IProject project) {
		PreferencesSupport prefSupport = new PreferencesSupport(PHPUiPlugin.ID);
		String doCleanupPref = prefSupport.getPreferencesValue(PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES,
				null, project);
		String ignoreEmptyPref = prefSupport.getPreferencesValue(
				PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY, null, project);

		removeTrailingWhitespacesEnabled = Boolean.parseBoolean(doCleanupPref);
		ignoreEmptyLines = Boolean.parseBoolean(ignoreEmptyPref);
	}

	public boolean isEnabled(ISourceModule compilationUnit) {
		return new PreferencesLookupDelegate(compilationUnit.getScriptProject().getProject()).getBoolean(PHPUiPlugin.ID,
				EDITOR_SAVE_PARTICIPANT_PREFIX + ID);
	}

	public boolean needsChangedRegions(ISourceModule compilationUnit) throws CoreException {
		return false;
	}

	protected MultiTextEdit computeTextEdit(IDocument document) throws BadLocationException {
		int lineCount = document.getNumberOfLines();

		MultiTextEdit multiEdit = new MultiTextEdit();

		for (int i = 0; i < lineCount; i++) {
			IRegion region = document.getLineInformation(i);
			if (region.getLength() == 0) {
				continue;
			}
			int lineStart = region.getOffset();
			int lineExclusiveEnd = lineStart + region.getLength();
			int j = lineExclusiveEnd - 1;
			while (j >= lineStart && Character.isWhitespace(document.getChar(j))) {
				--j;
			}
			++j;
			// A flag for skipping empty lines, if required
			if (ignoreEmptyLines && j == lineStart) {
				continue;
			}
			if (j < lineExclusiveEnd) {
				multiEdit.addChild(new DeleteEdit(j, lineExclusiveEnd - j));
			}
		}

		return multiEdit;
	}

	public void saved(ISourceModule compilationUnit, IRegion[] changedRegions, IProgressMonitor monitor)
			throws CoreException {
		IScriptProject project = compilationUnit.getScriptProject();
		updateSaveActionsState(project.getProject());

		if (!removeTrailingWhitespacesEnabled) {
			return;
		}

		try {
			IDocument document = new Document(compilationUnit.getSource());
			MultiTextEdit edits = computeTextEdit(document);
			if (edits.getChildrenSize() > 0) {
				final SourceModuleChange change = new SourceModuleChange("Format " + compilationUnit.getElementName(),
						compilationUnit);
				change.setSaveMode(TextFileChange.LEAVE_DIRTY);
				change.setEdit(edits);
				change.perform(monitor);
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, e.toString(), e));
		}
	}

}
