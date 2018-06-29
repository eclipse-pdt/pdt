/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.regex.Pattern;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;

/**
 * This context represents the state when staying after the 'var' tag in a
 * VarComment block. This class extends PHPDocTagContext, since VarComment
 * blocks work like PHPDoc blocks. <br/>
 * Examples:
 * 
 * <pre>
 *   1. /*
 *       * @var |
 *   2. /*return
 *       * @var Ty|
 * </pre>
 */
public class PHPVarCommentContext extends PHPDocTagContext {
	private static final String LABEL = "([a-zA-Z_\\\\u007F-\\\\uFFFF][a-zA-Z0-9_\\\\u007F-\\\\uFFFF]*)"; //$NON-NLS-1$
	private static final String TYPE_PART = "(" + LABEL + "|[\\[\\]|\\\\])"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String WHITESPACES = "([ \\n\\r\\t]+)"; //$NON-NLS-1$
	// See also the ast_scanner.flex files to get full VarComment patterns.
	// Note that getStatementText() will probably also contain some text that is
	// located before the VarComment.
	private static final Pattern VAR_COMMENT_PATTERN = Pattern.compile("^.*/[*]" + WHITESPACES + "?@var" + WHITESPACES //$NON-NLS-1$ //$NON-NLS-2$
			+ "([$]" + LABEL + WHITESPACES + TYPE_PART + "*|" + TYPE_PART + "*)$", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	@Override
	protected boolean isPHPTag(String tagName) {
		// we do not use task patterns inside VarComments, so skip them to go faster
		return false;
	}

	@Override
	protected boolean isValidStatementText() {
		// fast path: check if we are in a multi-line comment that is also a valid
		// VarComment (at least valid for all comment text before the cursor position)
		return VAR_COMMENT_PATTERN.matcher(getStatementText()).matches();
	}

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		return "var".equalsIgnoreCase(getTagName()); //$NON-NLS-1$
	}

	@Override
	protected boolean isRightPartitionType() {
		return getCompanion().getPartitionType() == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT;
	}
}
