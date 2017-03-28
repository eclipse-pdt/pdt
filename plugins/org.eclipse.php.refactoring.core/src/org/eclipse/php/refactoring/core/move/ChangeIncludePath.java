/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.move;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.TextEditChangeGroup;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.Include;
import org.eclipse.php.core.ast.nodes.ParenthesisExpression;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

public class ChangeIncludePath extends AbstractVisitor {

	private IFile participateFile;
	private boolean isIncluded;
	/**
	 * {@link TextEditGroup}
	 */
	protected final List<TextEditGroup> groups = new LinkedList<TextEditGroup>();
	private IFile file;
	private IPath destinationPath;
	private IResource[] selectedResource;

	public ChangeIncludePath(IFile file, IFile participateFile,
			IPath fMainDestinationPath, boolean isIncluded,
			IResource[] selectedResources) {
		this.file = file;
		this.participateFile = participateFile;
		this.isIncluded = isIncluded;
		this.destinationPath = fMainDestinationPath;
		this.selectedResource = selectedResources;
	}

	public boolean visit(Include include) {
		Scalar scalar = null;
		if (include.getExpression() instanceof ParenthesisExpression) {
			ParenthesisExpression exp = (ParenthesisExpression) include
					.getExpression();

			Expression expression = exp.getExpression();
			if (expression instanceof Scalar) {
				scalar = (Scalar) expression;
			}
		}
		if (include.getExpression() instanceof Scalar) {
			scalar = (Scalar) include.getExpression();
		}

		if (scalar != null) {
			String stringValue = scalar.getStringValue();
			if (isScalarNeedChange(scalar, stringValue)) {
				addChange(scalar, stringValue,
						PhpRefactoringCoreMessages
								.getString("RenameIncludeAndClassName.1")); //$NON-NLS-1$
			}
		}

		return false;
	}

	private boolean isScalarNeedChange(Scalar scalar, final String stringValue) {
		String value = getUnQuotedString(stringValue);
		IPath includePath = new Path(value);
		if (includePath.toString().startsWith("..")) { //$NON-NLS-1$
			includePath = participateFile.getParent().getFullPath()
					.append(includePath)
					.makeRelativeTo(file.getProject().getFullPath());
		}

		IPath fileDirectory = file.getParent().getProjectRelativePath();
		if (isIncluded) {
			return true;
		}

		if (fileDirectory.isPrefixOf(includePath)) {
			return includePath.equals(file.getProjectRelativePath());
		} else {
			IPath fullPath = participateFile.getParent()
					.getProjectRelativePath().append(includePath);

			return fullPath.equals(file.getProjectRelativePath());
		}

	}

	private void addChange(Scalar scalar, String oldString, String description) {
		final char charAt = scalar.getStringValue().charAt(0);
		final int isQuotedOffset = charAt == '"' || charAt == '\'' ? 1 : 0;
		addChange(scalar.getStart() + isQuotedOffset, oldString, description);
	}

	/**
	 * Adds the scalar to the list
	 * 
	 * @param scalar
	 */
	protected void addChange(int start, String oldString, String description) {
		final TextEditGroup textEditGroup = new TextEditGroup(description);

		String value = getUnQuotedString(oldString);

		String newValue = getNewPath(value);

		if (!value.equals(newValue)) {
			final ReplaceEdit replaceEdit = new ReplaceEdit(start,
					value.length(), newValue);
			textEditGroup.addTextEdit(replaceEdit);
			groups.add(textEditGroup);
		}
	}

	private String getUnQuotedString(String oldString) {
		String value = oldString;
		if (oldString.charAt(0) == '"'
				&& oldString.charAt(oldString.length() - 1) == '"') {
			value = oldString.substring(1, oldString.length() - 1);
		}

		if (oldString.charAt(0) == '\''
				&& oldString.charAt(oldString.length() - 1) == '\'') {
			value = oldString.substring(1, oldString.length() - 1);
		}

		return value;

	}

	private String getNewPath(String value) {
		if (!isIncluded) {
			return MoveUtils.getMovedIncludingString(file, destinationPath,
					participateFile, value, selectedResource);
		} else {
			return MoveUtils.getMovedIncludedString(file, destinationPath,
					value, selectedResource);
		}
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
				// avoid overlapping edits
				if (existingTextEdit.getOffset() == textEdit.getOffset()) {
					continue OUTER;
				}
			}
			change.addTextEditChangeGroup(textEditChangeGroup);
			change.addEdit(textEdit);
		}
	}

}
