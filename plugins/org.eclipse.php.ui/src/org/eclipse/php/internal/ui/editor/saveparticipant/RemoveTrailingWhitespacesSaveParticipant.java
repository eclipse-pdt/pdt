/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.saveparticipant;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.annotations.NonNull;
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
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.ast.nodes.Quote;
import org.eclipse.php.core.ast.visitor.ApplyAll;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.saveparticipant.SaveParticipantDescriptor;

public class RemoveTrailingWhitespacesSaveParticipant implements IPostSaveListener {

	private static class TopMostHeredocFinder extends ApplyAll {

		private List<Quote> topMostHeredocs = new ArrayList<>();

		@Override
		protected boolean apply(ASTNode node) {
			if (node instanceof Quote && (((Quote) node).getQuoteType() == Quote.QT_NOWDOC
					|| ((Quote) node).getQuoteType() == Quote.QT_HEREDOC)) {
				topMostHeredocs.add((Quote) node);
				// do not look for nested heredocs
				return false;
			}
			return true;
		}

		public List<Quote> getTopMostHeredocs() {
			return topMostHeredocs;
		}

	}

	@Override
	public String getName() {
		return "Remove trailing whitespaces"; //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return ID;
	}

	public static final String ID = "RemoveTrailingWhitespaces"; //$NON-NLS-1$

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

	@Override
	public boolean isEnabled(ISourceModule compilationUnit) {
		return new PreferencesLookupDelegate(compilationUnit.getScriptProject().getProject()).getBoolean(PHPUiPlugin.ID,
				EDITOR_SAVE_PARTICIPANT_PREFIX + ID);
	}

	@Override
	public boolean needsChangedRegions(ISourceModule compilationUnit) throws CoreException {
		return false;
	}

	protected @NonNull MultiTextEdit computeTextEdit(@NonNull IDocument document, @Nullable Program astRoot)
			throws BadLocationException {
		List<Quote> topMostHeredocs;

		if (astRoot != null) {
			/*
			 * It is important that the "topMostHeredocs" list is sorted by
			 * increasing position, or this method won't work correctly.
			 */
			TopMostHeredocFinder visitor = new TopMostHeredocFinder();
			astRoot.accept(visitor);
			topMostHeredocs = visitor.getTopMostHeredocs();
		} else {
			topMostHeredocs = new ArrayList<>();
		}

		int lineCount = document.getNumberOfLines();
		MultiTextEdit multiEdit = new MultiTextEdit();

		for (int i = 0; i < lineCount; i++) {
			IRegion region = document.getLineInformation(i);
			if (region.getLength() == 0) {
				continue;
			}
			int lineStart = region.getOffset();
			int lineExclusiveEnd = lineStart + region.getLength();
			int lineExclusiveDelimiterEnd = lineStart + document.getLineLength(i);
			boolean isInHeredoc = false;

			for (int j = 0, len = topMostHeredocs.size(); j < len; j++) {
				Quote heredoc = topMostHeredocs.get(j);
				if (lineExclusiveDelimiterEnd <= heredoc.getStart()) {
					// the next heredocs/nowdocs will all start after
					// lineExclusiveDelimiterEnd (if the topMostHeredocs list
					// is correctly sorted), so we're sure current line isn't
					// part of any heredocs/nowdoc
					break;
				}
				if (heredoc.getEnd() <= lineStart) {
					// at this point of the document, current heredoc/nowdoc
					// will never be matched again, so remove it from the
					// topMostHeredocs list
					topMostHeredocs.remove(j);
					len--;
					// will be re-incremented afterwards
					j--;
					continue;
				}
				// check if last character of current line (i.e.
				// lineExclusiveDelimiterEnd - 1) is enclosed in a
				// heredoc/nowdoc
				if (heredoc.getStart() < lineExclusiveDelimiterEnd && heredoc.getEnd() >= lineExclusiveDelimiterEnd) {
					isInHeredoc = true;
					break;
				}
			}
			if (isInHeredoc) {
				// don't remove trailing whitespaces inside heredocs or nowdocs
				continue;
			}

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

	@Override
	public void saved(ISourceModule compilationUnit, IRegion[] changedRegions, IProgressMonitor monitor)
			throws CoreException {
		IScriptProject project = compilationUnit.getScriptProject();
		updateSaveActionsState(project != null ? project.getProject() : null);

		if (!removeTrailingWhitespacesEnabled) {
			return;
		}

		try {
			ASTParser parser = ASTParser.newParser(compilationUnit);
			Program astRoot = parser.createAST(monitor);

			IDocument document = new Document(compilationUnit.getSource());
			MultiTextEdit edits = computeTextEdit(document, astRoot);
			if (edits.hasChildren()) {
				final SourceModuleChange change = new SourceModuleChange(
						"Remove trailing whitespaces from " + compilationUnit.getElementName(), compilationUnit); //$NON-NLS-1$
				change.setSaveMode(TextFileChange.LEAVE_DIRTY);
				change.setEdit(edits);
				change.perform(monitor);
			}
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, e.toString(), e));
		}
	}

}
