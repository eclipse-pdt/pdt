/*******************************************************************************
 * Copyright (c) 2009, 2017, 2018 IBM Corporation and others.
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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 *
 */
public class MainAutoEditStrategy implements IAutoEditStrategy {

	private static IAutoEditStrategy indentLineAutoEditStrategy = new IndentLineAutoEditStrategy();
	private static IAppliedAutoEditStrategy caseDefaultAutoEditStrategy = new CaseDefaultAutoEditStrategy();
	private static IAutoEditStrategy docBlockAutoEditStrategy = new PHPDocAutoIndentStrategy();
	private static IAutoEditStrategy autoIndentStrategy = new PHPAutoIndentStrategy();

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (command.text == null) {
			return;
		}
		String partitionType = FormatterUtils.getPartitionType((IStructuredDocument) document, command.offset);

		if (partitionType == PHPPartitionTypes.PHP_DOC || partitionType == PHPPartitionTypes.PHP_MULTI_LINE_COMMENT) {
			// case of multi line comment or php doc
			docBlockAutoEditStrategy.customizeDocumentCommand(document, command);
			return;
		}

		boolean isPreviousQuotesState = command.offset > 0
				? FormatterUtils.isPHPQuotesState((IStructuredDocument) document, command.offset - 1, false)
				: false;

		if (isPreviousQuotesState) {
			boolean isCurrentQuotesState = FormatterUtils.isPHPQuotesState((IStructuredDocument) document,
					command.offset, true);
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=512891
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=533901
			// At this point we only know that command.offset is inside or just
			// after some "quoted string".
			// Do an additional check to be sure we handle lines only when
			// command.offset is lying inside "quoted strings".
			if (isCurrentQuotesState) {
				return;
			}
		}

		if (partitionType == PHPPartitionTypes.PHP_DEFAULT || partitionType == PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT
				|| partitionType == PHPPartitionTypes.PHP_QUOTED_STRING) {
			caseDefaultAutoEditStrategy.customizeDocumentCommand(document, command);
			if (caseDefaultAutoEditStrategy.wasApplied()) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=499818
				// "case"/"default" was found and indented, we stop here
				return;
			}
			indentLineAutoEditStrategy.customizeDocumentCommand(document, command);
			autoIndentStrategy.customizeDocumentCommand(document, command);
		}

	}

}
