/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.ltk.core.refactoring.TextEditChangeGroup;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.ui.search.text.TextSearcher;
import org.eclipse.php.internal.ui.search.text.TextSearcherFactory;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.php.refactoring.core.changes.ProgramFileChange;
import org.eclipse.search.internal.ui.text.FileMatch;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

/**
 * Base class for all logics rename
 * 
 * @author Roy, 2007
 */
@SuppressWarnings("restriction")
public abstract class AbstractRename extends AbstractVisitor {

	/**
	 * The global variable name to change
	 */
	protected String oldName;

	/**
	 * {@link TextEditGroup}
	 */
	protected final List<TextEditGroup> groups = new LinkedList<TextEditGroup>();

	/**
	 * The file we are working on
	 */
	protected final IFile changedFile;

	/**
	 * The new name of the variable
	 */
	protected final String newName;

	/**
	 * Wether or not to search strings and comments
	 */
	protected final boolean searchTextual;

	/**
	 * Represents the global context
	 */
	protected final static String GLOBAL = "Global"; //$NON-NLS-1$

	/**
	 * Pattern used in scalar search
	 */
	private final Pattern pattern;

	/**
	 * @param file
	 * @param oldName
	 * @param newName
	 */
	public AbstractRename(IFile file, String oldName, String newName,
			boolean searchTextual) {
		if (newName == null || file == null || oldName == null) {
			throw new IllegalArgumentException();
		}
		this.oldName = oldName;
		this.newName = newName;
		this.changedFile = file;
		this.searchTextual = searchTextual;
		this.pattern = Pattern.compile(getTextualSearchPattern());
	}

	/**
	 * Adds the identifier to the list
	 * 
	 * @param identifier
	 */
	protected void addChange(Identifier identifier) {
		addChange(identifier.getStart());
	}

	/**
	 * Adds the scalar to the list
	 * 
	 * @param scalar
	 */
	protected void addChange(Scalar scalar) {
		final char charAt = scalar.getStringValue().charAt(0);
		final int isQuotedOffset = charAt == '"' || charAt == '\'' ? 1 : 0;
		addChange(scalar.getStart() + isQuotedOffset);
	}

	/**
	 * Adds the scalar to the list
	 * 
	 * @param scalar
	 */
	protected void addChange(int start) {
		addChange(start, getRenameDescription());
	}

	public boolean hasChanges() {
		return groups.size() != 0;
	}

	public void updateChange(TextFileChange change) {
		// check for empty changes
		if (!hasChanges()) {
			return;
		}

		addGroups(change, groups);
	}

	/**
	 * Adds the edit groups to an existing change
	 * 
	 * @param change
	 *            - the change that will be used as a container
	 * @param groups
	 *            - the groups to add
	 */
	private final static void addGroups(TextFileChange change,
			List<TextEditGroup> groups) {
		assert change != null && groups != null;

		TextEditChangeGroup[] textEditChangeGroups = change
				.getTextEditChangeGroups();
		OUTER: for (TextEditGroup editGroup : groups) {
			TextEditChangeGroup textEditChangeGroup = new TextEditChangeGroup(
					change, editGroup);
			final TextEdit textEdit = editGroup.getTextEdits()[0];
			for (TextEditChangeGroup existingTextEditChangeGroup : textEditChangeGroups) {
				TextEdit existingTextEdit = existingTextEditChangeGroup
						.getTextEdits()[0];
				if (existingTextEdit.getOffset() == textEdit.getOffset()) { // avoid
					// overlapping
					// edits
					continue OUTER;
				}
			}
			change.addTextEditChangeGroup(textEditChangeGroup);
			change.addEdit(textEdit);
		}
	}

	/**
	 * Merges the groups to an existing change
	 * 
	 * @param change
	 */
	public final void mergeGroups(ProgramFileChange change) {
		addGroups(change, this.groups);
	}

	/**
	 *
	 */
	public boolean visit(Program program) {
		final List<Statement> statements = program.statements();
		for (Statement element : statements) {
			element.accept(this);
		}

		if (this.searchTextual) {
			searchTextualOccurrences(program);
		}
		return false;
	}

	protected void searchTextualOccurrences(Program program) {
		TextSearcher searcher = TextSearcherFactory.createSearcher(changedFile,
				getTextualSearchPattern());
		searcher.search(null);

		/**
		 * an iterator of @link FileMatch
		 */
		final Iterator<?> searchIterator = searcher.getResults().iterator();
		FileMatch currentMatch = (FileMatch) (searchIterator.hasNext() ? searchIterator
				.next() : null);
		/**
		 * an iterator of @link Comment
		 */
		final Iterator<Comment> commentIterator = program.comments().iterator();
		Comment currentComment = (Comment) (commentIterator.hasNext() ? commentIterator
				.next() : null);

		while (currentComment != null && currentMatch != null) {

			while (isInside(currentComment, currentMatch)) {
				// if the comment abs the text - add change
				if (textInsideComment(currentComment, currentMatch)) {
					addChange(currentMatch.getOffset() + 1,
							PhpRefactoringCoreMessages
									.getString("AbstractRename_0")); //$NON-NLS-1$
				}
				currentMatch = (FileMatch) (searchIterator.hasNext() ? searchIterator
						.next() : null);
			}

			while (isCommentBefore(currentMatch, currentComment)) {
				currentComment = (Comment) (commentIterator.hasNext() ? commentIterator
						.next() : null);
			}

			while (isMatchBefore(currentMatch, currentComment)) {
				currentMatch = (FileMatch) (searchIterator.hasNext() ? searchIterator
						.next() : null);
			}
		}
	}

	protected void addChange(int start, String name) {
		final TextEditGroup textEditGroup = new TextEditGroup(name);
		final ReplaceEdit replaceEdit = new ReplaceEdit(start,
				oldName.length(), this.newName);
		textEditGroup.addTextEdit(replaceEdit);
		groups.add(textEditGroup);

	}

	protected String getTextualSearchPattern() {
		return "\\W" + oldName + "\\W"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private final boolean isMatchBefore(FileMatch currentMatch,
			Comment currentComment) {
		return currentComment != null
				&& currentMatch != null
				&& currentMatch.getOffset() + currentMatch.getLength() <= currentComment
						.getStart();
	}

	private final boolean isCommentBefore(FileMatch currentMatch,
			Comment currentComment) {
		return currentComment != null && currentMatch != null
				&& currentComment.getEnd() <= currentMatch.getOffset();
	}

	protected final boolean isInside(Comment currentComment,
			FileMatch currentMatch) {
		return currentComment != null && currentMatch != null
				&& currentComment.getStart() <= currentMatch.getOffset()
				&& currentComment.getEnd() >= currentMatch.getOffset();
	}

	protected boolean textInsideComment(final Comment currentComment,
			final FileMatch currentMatch) {
		return currentComment.getStart() <= currentMatch.getOffset() + 1
				&& currentComment.getEnd() > currentMatch.getOffset() + 1;
	}

	public boolean visit(Scalar scalar) {
		if (searchTextual) {
			final String stringValue = scalar.getStringValue();
			if (scalar.getScalarType() == Scalar.TYPE_STRING
					&& stringValue != null) {
				final Matcher matcher = pattern.matcher(stringValue);
				while (matcher.find()) {
					addChange(scalar.getStart() + matcher.start()
							+ matcher.group().indexOf(oldName),
							PhpRefactoringCoreMessages
									.getString("AbstractRename_0")); //$NON-NLS-1$
				}
			}
		}
		return true;
	}

	public abstract String getRenameDescription();
}
