/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	private static IAutoEditStrategy curlyOpenAutoEditStrategy = new CurlyOpenAutoEditStrategy();
	private static IAutoEditStrategy curlyCloseAutoEditStrategy = new CurlyCloseAutoEditStrategy();
	private static IAutoEditStrategy matchingBracketAutoEditStrategy = new MatchingBracketAutoEditStrategy();
	private static IAutoEditStrategy quotesAutoEditStrategy = new QuotesAutoEditStrategy();
	private static IAutoEditStrategy caseDefaultAutoEditStrategy = new CaseDefaultAutoEditStrategy();
	private static IAutoEditStrategy docBlockAutoEditStrategy = new PhpDocAutoIndentStrategy();
	private static IAutoEditStrategy autoIndentStrategy = new PHPAutoIndentStrategy();

	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if (command.text == null) {
			return;
		}
		String partitionType = FormatterUtils.getPartitionType(
				(IStructuredDocument) document, command.offset);

		if (partitionType.equals(PHPPartitionTypes.PHP_DOC)
				|| partitionType
						.equals(PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)) {
			// case of multi line comment or php doc
			docBlockAutoEditStrategy
					.customizeDocumentCommand(document, command);
		} else if (partitionType.equals(PHPPartitionTypes.PHP_QUOTED_STRING)) {
			indentLineAutoEditStrategy.customizeDocumentCommand(document,
					command);
			quotesAutoEditStrategy.customizeDocumentCommand(document, command);
		} else if (partitionType.equals(PHPPartitionTypes.PHP_DEFAULT)
				|| partitionType
						.equals(PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT)) {
			caseDefaultAutoEditStrategy.customizeDocumentCommand(document,
					command);
			matchingBracketAutoEditStrategy.customizeDocumentCommand(document,
					command);
			curlyOpenAutoEditStrategy.customizeDocumentCommand(document,
					command);
			curlyCloseAutoEditStrategy.customizeDocumentCommand(document,
					command);
			indentLineAutoEditStrategy.customizeDocumentCommand(document,
					command);
			quotesAutoEditStrategy.customizeDocumentCommand(document, command);
			autoIndentStrategy.customizeDocumentCommand(document, command);
		}

	}

}
