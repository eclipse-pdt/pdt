/**********************************************************************
 Copyright (c) 2000, 2002 IBM Corp. and others.
 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Common Public License v1.0
 which accompanies this distribution, and is available at
 http://www.eclipse.org/legal/cpl-v10.html

 Contributors:
 IBM Corporation - Initial implementation
 www.phpeclipse.de
 **********************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.PhpFormatter;
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

	/*
	 * (non-Javadoc) Method declared on IAutoIndentStrategy
	 */
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		// when user typing c.text.length()==1 except enter key,
		// if user type enter key,we may add some indentation spaces/tabs for
		// it,so we use c.text.trim().length() > 0 to filter it
		if (c.text != null
				&& c.text.length() > 1
				&& c.text.trim().length() > 1
				&& getPreferenceStore().getBoolean(
						PreferenceConstants.EDITOR_SMART_PASTE)) {
			try {
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
		if (command.offset == -1 || document.getLength() == 0)
			return;
		StringBuffer helpBuffer = new StringBuffer();
		try {
			if (document instanceof IStructuredDocument) {

				defaultStrategy.placeMatchingBlanksForStructuredDocument(
						(IStructuredDocument) document, helpBuffer,
						document.getLineOfOffset(command.offset),
						command.offset);
				IRegion region = document.getLineInformation(document
						.getLineOfOffset(command.offset));
				if (document.get(region.getOffset(), region.getLength()).trim()
						.length() == 0) {// blank line
					if (command.offset != region.getOffset()) {
						document.replace(region.getOffset(),
								region.getLength(), ""); //$NON-NLS-1$
						// adjust the offset
						command.offset = region.getOffset();
					}
				} else {
					return;
				}
			}
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}

		String newline = PHPModelUtils.getLineSeparator(null);
		IStructuredModel structuredModel = null;
		try {
			IProject project = null;
			structuredModel = StructuredModelManager.getModelManager()
					.getExistingModelForRead(document);
			DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;
			project = getProject(doModelForPHP);
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
		StringBuffer tempsb = new StringBuffer();
		try {
			for (int i = 0; i < lines; i++) {
				IRegion region = tempdocument.getLineInformation(i);
				if (tempsb.length() > 0) {
					tempsb.append(newline);
				}
				String currentLine = tempdocument.get(region.getOffset(),
						region.getLength());
				if (tempsb.length() == 0) {
					if (currentLine.trim().length() == 0) {
						startingEmptyLines++;
					} else {
						tempsb.append(currentLine.trim());
					}

				} else {
					tempsb.append(tempdocument.get(region.getOffset(),
							region.getLength()));
				}
			}
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
		JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(
				new PhpSourceParser());
		String start = "<?php"; //$NON-NLS-1$
		newdocument.set(start + newline + tempsb.toString());
		PhpFormatter formatter = new PhpFormatter(0, newdocument.getLength(),
				true);
		formatter.format(newdocument.getFirstStructuredDocumentRegion());

		List<String> list = new ArrayList<String>();
		try {
			int lineNumber = newdocument.getNumberOfLines();
			for (int i = 0; i < lineNumber; i++) {
				if (i == 0) {
					continue;
				}
				IRegion region = newdocument.getLineInformation(i);
				String line = newdocument.get(region.getOffset(),
						region.getLength());
				list.add(line);
			}
		} catch (BadLocationException e) {
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < startingEmptyLines; i++) {
			String lineDelimiter = newline;
			try {
				lineDelimiter = tempdocument.getLineDelimiter(i);
			} catch (BadLocationException e) {
			}
			sb.append(lineDelimiter);
		}
		for (int i = 0; i < list.size(); i++) {
			if (!formatter.getIgnoreLines().contains(i + 1)) {
				sb.append(helpBuffer.toString());
			}
			sb.append(list.get(i));
			if (i == list.size() - 1) {
			} else {
				String lineDelimiter = newline;
				try {
					lineDelimiter = tempdocument
							.getLineDelimiter(startingEmptyLines + i);
				} catch (BadLocationException e) {
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
	private final static IProject getProject(DOMModelForPHP doModelForPHP) {
		final String id = doModelForPHP.getId();
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
