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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.format.PHPFormatProcessorProxy;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.text.JobSafeStructuredDocument;

/**
 * Auto indent strategy sensitive to brackets.
 */
public class PHPAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	public PHPAutoIndentStrategy() {
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
			smartPaste(d, c);
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
				DefaultIndentationStrategy
						.placeMatchingBlanksForStructuredDocument(
								(IStructuredDocument) document, helpBuffer,
								document.getLineOfOffset(command.offset),
								command.offset);
				IRegion region = document.getLineInformation(document
						.getLineOfOffset(command.offset));
				if (document.get(region.getOffset(), region.getLength()).trim()
						.length() == 0) {// blank line
					if (command.offset != region.getOffset()) {
						document.replace(region.getOffset(),
								region.getLength(), "");
						// adjust the offset
						command.offset = region.getOffset();
					}
				} else {
					return;
				}
			}
		} catch (BadLocationException e) {
		}
		IStructuredModel structuredModel = null;
		try {
			IProject project = null;
			IContentFormatter contentFormatter = PHPFormatProcessorProxy
					.getFormatter();
			if (contentFormatter instanceof IFormatterProcessorFactory) {
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(document);
				DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;
				project = getProject(doModelForPHP);
				((IFormatterProcessorFactory) contentFormatter)
						.setDefaultProject(project);
			}
			String lineSeparator = PHPModelUtils.getLineSeparator(project);
			String beforeText = document.get(0, command.offset);
			beforeText = beforeText.trim() + lineSeparator;
			String afterText = lineSeparator
					+ document.get(command.offset,
							document.getLength() - command.offset).trim();
			String newPhpText = beforeText + command.text + afterText;
			JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(
					new PhpSourceParser());
			newdocument.set(newPhpText);
			contentFormatter.format(newdocument, new Region(beforeText.length()
					- lineSeparator.length(), command.text.length()
					+ lineSeparator.length()));
			command.text = newdocument.get(
					beforeText.length(),
					newdocument.getLength()
							- (beforeText.length() + afterText.length()));
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}
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
