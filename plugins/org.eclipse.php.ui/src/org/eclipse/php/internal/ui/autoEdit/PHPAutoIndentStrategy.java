/**********************************************************************
 Copyright (c) 2000, 2015 IBM Corp. and others.
 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Common Public License v1.0
 which accompanies this distribution, and is available at
 http://www.eclipse.org/legal/cpl-v10.html

 Contributors:
 IBM Corporation - Initial implementation
 www.phpeclipse.de
 Dawid Pakuła [459462]
 **********************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.IndentationObject;
import org.eclipse.php.internal.core.format.PhpIndentationFormatter;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.text.JobSafeStructuredDocument;

/**
 * Auto indent strategy sensitive to brackets.
 */
public class PHPAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	private DefaultIndentationStrategy defaultStrategy;

	public PHPAutoIndentStrategy() {
		defaultStrategy = new DefaultIndentationStrategy();
	}

	@Override
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		// when user typing c.text.length()==1 except enter key,
		// if user type enter key,we may add some indentation spaces/tabs for
		// it,so we use c.text.trim().length() > 0 to filter it
		if (c.text != null && c.text.length() > 1 && c.text.trim().length() > 1
				&& getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_SMART_PASTE)) {
			try {
				// bug 459462
				defaultStrategy.setIndentationObject(null);

				smartPaste(d, c);
			} catch (Exception e) {
				PHPUiPlugin.log(e);
			}

		}
	}

	private static IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	/**
	 * Set the indent of a bracket based on the command provided in the supplied
	 * document.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param command
	 *            - the command being performed
	 */
	protected void smartPaste(IDocument document, DocumentCommand command) {
		if (command.offset == -1 || document.getLength() == 0) {
			return;
		}
		StringBuffer helpBuffer = new StringBuffer();
		IndentationObject indentationObject = null;
		try {
			if (document instanceof IStructuredDocument) {
				indentationObject = new IndentationObject((IStructuredDocument) document);
				defaultStrategy.placeMatchingBlanksForStructuredDocument((IStructuredDocument) document, helpBuffer,
						document.getLineOfOffset(command.offset), command.offset);
				IRegion region = document.getLineInformation(document.getLineOfOffset(command.offset));

				if (command.offset == region.getOffset()) {
					// nothing to do
				} else if (StringUtils.isBlank(document.get(region.getOffset(), command.offset - region.getOffset()))) {
					document.replace(region.getOffset(), command.offset - region.getOffset(), ""); //$NON-NLS-1$
					// adjust the offset
					command.offset = region.getOffset();
				} else {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=495295
					// there are non-blank characters before cursor position,
					// we can't apply auto-indenting in this case
					return;
				}

				// be smart and remove remaining blank characters after
				// selection
				int selectionEndOffset = command.offset + command.length;
				region = document.getLineInformation(document.getLineOfOffset(selectionEndOffset));
				for (int i = selectionEndOffset, lineEndOffset = region.getOffset()
						+ region.getLength(); i < lineEndOffset
								&& (document.getChar(i) == ' ' || document.getChar(i) == '\t'); i++) {
					// adjust the length to include the blank character
					command.length++;
				}
			}
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}

		String newline = PHPModelUtils.getLineSeparator(null);
		IStructuredModel structuredModel = null;
		try {
			IProject project = null;
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			project = getProject(structuredModel);
			newline = PHPModelUtils.getLineSeparator(project);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}
		Document tempdocument = new Document(command.text);
		int lines = tempdocument.getNumberOfLines();
		// starting empty lines of pasted code.
		int startingEmptyLines = 0;
		StringBuilder tempsb = new StringBuilder();
		try {
			for (int i = 0; i < lines; i++) {
				IRegion region = tempdocument.getLineInformation(i);
				if (tempsb.length() > 0) {
					tempsb.append(newline);
				}
				String currentLine = tempdocument.get(region.getOffset(), region.getLength());
				if (tempsb.length() == 0) {
					if (StringUtils.isBlank(currentLine)) {
						startingEmptyLines++;
					} else {
						tempsb.append(currentLine.trim());
					}

				} else {
					tempsb.append(tempdocument.get(region.getOffset(), region.getLength()));
				}
			}
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
		JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(new PhpSourceParser());
		String start = "<?php"; //$NON-NLS-1$
		newdocument.set(start + newline + tempsb.toString());
		PhpIndentationFormatter formatter = new PhpIndentationFormatter(0, newdocument.getLength(), indentationObject);
		formatter.format(newdocument.getFirstStructuredDocumentRegion());

		List<String> list = new ArrayList<>();
		try {
			int lineNumber = newdocument.getNumberOfLines();
			for (int i = 0; i < lineNumber; i++) {
				if (i == 0) {
					continue;
				}
				IRegion region = newdocument.getLineInformation(i);
				String line = newdocument.get(region.getOffset(), region.getLength());
				list.add(line);
			}
		} catch (BadLocationException e) {
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < startingEmptyLines; i++) {
			String lineDelimiter = null;
			try {
				lineDelimiter = tempdocument.getLineDelimiter(i);
			} catch (BadLocationException e) {
			}
			if (lineDelimiter == null) {
				lineDelimiter = newline;
			}
			sb.append(lineDelimiter);
		}
		for (int i = 0; i < list.size(); i++) {
			if (!formatter.getIgnoreLines().contains(i + 1)) {
				sb.append(helpBuffer);
			}
			sb.append(list.get(i));
			if (i == list.size() - 1) {
			} else {
				String lineDelimiter = null;
				try {
					lineDelimiter = tempdocument.getLineDelimiter(startingEmptyLines + i);
				} catch (BadLocationException e) {
				}
				if (lineDelimiter == null) {
					lineDelimiter = newline;
				}
				sb.append(lineDelimiter);
			}

		}
		command.text = sb.toString();
	}

	/**
	 * @param doModelForPHP
	 * @return project from document
	 */
	private final static IProject getProject(IStructuredModel doModelForPHP) {
		final String id = doModelForPHP != null ? doModelForPHP.getId() : null;
		if (id != null) {
			final IFile file = getFile(id);
			if (file != null) {
				return file.getProject();
			}
		}
		return null;
	}

	/**
	 * @param id
	 * @return the file from document
	 */
	private static IFile getFile(final String id) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(id));
	}

}
