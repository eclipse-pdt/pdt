package org.eclipse.php.internal.ui.editor.saveparticipant;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.IFileBufferStatusCodes;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.manipulation.MultiTextEditWithProgress;
import org.eclipse.core.filebuffers.manipulation.RemoveTrailingWhitespaceOperation;
import org.eclipse.core.internal.filebuffers.Progress;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.core.manipulation.SourceModuleChange;
import org.eclipse.dltk.ui.editor.saveparticipant.IPostSaveListener;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.saveparticipant.SaveParticipantDescriptor;

public class CodeFormatSaveParticipant implements IPostSaveListener {

	public String getName() {
		return "Format source code";
	}

	public String getId() {
		return ID;
	}

	public static final String ID = "CodeFormat";

	/**
	 * Preference prefix that is appended to the id of
	 * {@link SaveParticipantDescriptor save participants}.
	 * 
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 */
	public static final String EDITOR_SAVE_PARTICIPANT_PREFIX = "editor_save_participant_"; //$NON-NLS-1$

	private boolean saveActionsEnabled = false;
	private boolean saveActionsIgnoreEmptyLines = false;
	private boolean formatOnSaveEnabled = false;

	/*
	 * Operation used for removing whitepsaces from line ends
	 */
	private static class ExtendedRemoveTrailingWhitespaceOperation extends RemoveTrailingWhitespaceOperation {

		// skip empty lines when removing whitespaces
		private boolean fIgnoreEmptyLines;

		public ExtendedRemoveTrailingWhitespaceOperation(boolean ignoreEmptyLines) {
			super();
			fIgnoreEmptyLines = ignoreEmptyLines;
		}

		/*
		 * Same as in parent, with the addition of the ability to ignore empty
		 * lines - depending on the value of fIgnoreEmptyLines
		 */
		@Override
		protected MultiTextEditWithProgress computeTextEdit(ITextFileBuffer fileBuffer,
				IProgressMonitor progressMonitor) throws CoreException {
			IDocument document = fileBuffer.getDocument();
			int lineCount = document.getNumberOfLines();

			progressMonitor = Progress.getMonitor(progressMonitor);
			progressMonitor.beginTask(PHPUIMessages.RemoveTrailingWhitespaceOperation_task_generatingChanges,
					lineCount);
			try {

				MultiTextEditWithProgress multiEdit = new MultiTextEditWithProgress(
						PHPUIMessages.RemoveTrailingWhitespaceOperation_task_applyingChanges);

				for (int i = 0; i < lineCount; i++) {
					if (progressMonitor.isCanceled()) {
						throw new OperationCanceledException();
					}
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
					if (fIgnoreEmptyLines && j == lineStart) {
						continue;
					}
					if (j < lineExclusiveEnd) {
						multiEdit.addChild(new DeleteEdit(j, lineExclusiveEnd - j));
					}
					progressMonitor.worked(1);
				}

				return multiEdit.getChildrenSize() <= 0 ? null : multiEdit;

			} catch (BadLocationException x) {
				throw new CoreException(
						new Status(IStatus.ERROR, PHPUiPlugin.ID, IFileBufferStatusCodes.CONTENT_CHANGE_FAILED, "", x)); //$NON-NLS-1$
			} finally {
				progressMonitor.done();
			}
		}
	}

	/*
	 * Gets the preferences set for this editor in the Save Actions section
	 */
	public void updateSaveActionsState(@Nullable IProject project) {
		PreferencesSupport prefSupport = new PreferencesSupport(PHPUiPlugin.ID);
		String doCleanupPref = prefSupport.getPreferencesValue(PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES,
				null, project);
		String ignoreEmptyPref = prefSupport.getPreferencesValue(
				PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY, null, project);
		String formatOnSavePref = prefSupport.getPreferencesValue(PreferenceConstants.FORMAT_ON_SAVE, null, project);

		saveActionsEnabled = Boolean.parseBoolean(doCleanupPref);
		saveActionsIgnoreEmptyLines = Boolean.parseBoolean(ignoreEmptyPref);
		formatOnSaveEnabled = Boolean.parseBoolean(formatOnSavePref);
	}

	public boolean isEnabled(ISourceModule compilationUnit) {
		return new PreferencesLookupDelegate(compilationUnit.getScriptProject().getProject()).getBoolean(PHPUiPlugin.ID,
				EDITOR_SAVE_PARTICIPANT_PREFIX + getId());
	}

	public boolean needsChangedRegions(ISourceModule compilationUnit) throws CoreException {
		return false;
	}

	private void format(IDocument document, IProgressMonitor progressMonitor) {
		RemoveTrailingWhitespaceOperation op = new ExtendedRemoveTrailingWhitespaceOperation(
				saveActionsIgnoreEmptyLines);
		try {
			op.run(FileBuffers.getTextFileBufferManager().getTextFileBuffer(document), progressMonitor);
		} catch (OperationCanceledException e) {
			Logger.logException(e);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	public void saved(ISourceModule compilationUnit, IRegion[] changedRegions, IProgressMonitor monitor)
			throws CoreException {
		IScriptProject project = compilationUnit.getScriptProject();
		updateSaveActionsState(project.getProject());

		IContentFormatter formatter = PHPUiPlugin.getDefault().getActiveFormatter();

		if (formatter instanceof IFormatterProcessorFactory) {
			try {
				IDocument document = new Document(compilationUnit.getSource());
				ICodeFormattingProcessor processor = ((IFormatterProcessorFactory) formatter)
						.getCodeFormattingProcessor(document, ProjectOptions.getPhpVersion(project),
								ProjectOptions.useShortTags(project), new Region(0, document.getLength()));
				MultiTextEdit edits = processor.getTextEdits();
				if (edits.getLength() > 0) {
					final SourceModuleChange change = new SourceModuleChange(
							"Format " + compilationUnit.getElementName(), compilationUnit);
					change.setSaveMode(TextFileChange.LEAVE_DIRTY);
					change.setEdit(edits);
					change.perform(monitor);
				}
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, e.toString(), e));
			}
		}
	}

}
