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

	private static final String DEFAULT_LINE_DELIMITER = "\r\n";
	private final static String UNTITLED_PHP_DOC_PREFIX = "PHPDocument"; //$NON-NLS-1$

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

		// IPath stateLocation = PHPUiPlugin.getDefault().getStateLocation();
		// IPath path = stateLocation
		//				.append("/formatter_" + new Object().hashCode()); //$NON-NLS-1$
		// IFileStore fileStore = EFS.getLocalFileSystem().getStore(path);
		//
		// NonExistingPHPFileEditorInput input = new
		// NonExistingPHPFileEditorInput(
		// fileStore, UNTITLED_PHP_DOC_PREFIX);
		//
		// File realFile = ((NonExistingPHPFileEditorInput)
		// input).getPath(input)
		// .toFile();
		// realFile.deleteOnExit();
		// final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
		// .getRoot();
		// IProject project = workspaceRoot.getProject("/ ");
		// IFile file = project.getFile(new Path(realFile.getName()));
		IStructuredModel structuredModel = null;
		// IStructuredModel modelForEdit = StructuredModelManager
		// .getModelManager()
		// .getModelForEdit(realFile);
		try {
			String beforeText = document.get(0, command.offset);
			String afterText = document.get(command.offset,
					document.getLength() - command.offset);
			String newPhpText = beforeText + command.text + afterText;
			// file.create(new ByteArrayInputStream(newPhpText.getBytes()),
			// true,
			// null);
			JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(
					new PhpSourceParser());
			newdocument.set(newPhpText);
			// structuredModel = StructuredModelManager.getModelManager()
			// .getExistingModelForRead(file);
			IContentFormatter contentFormatter = PHPFormatProcessorProxy
					.getFormatter();
			if (contentFormatter instanceof IFormatterProcessorFactory) {
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(document);
				DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;
				IProject project = getProject(doModelForPHP);
				((IFormatterProcessorFactory) contentFormatter)
						.setDefaultProject(project);
			}
			contentFormatter.format(newdocument, new Region(command.offset,
					command.text.length()));
			command.text = newdocument.get(command.offset,
					newdocument.getLength() - document.getLength());
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}

		// Document tempdocument = new Document(command.text);
		// String newline = DEFAULT_LINE_DELIMITER;
		// int lines = tempdocument.getNumberOfLines();
		// StringBuffer tempsb = new StringBuffer();
		// try {
		// for (int i = 0; i < lines; i++) {
		// IRegion region = tempdocument.getLineInformation(i);
		// if (i > 0) {
		// if (tempdocument.getLineDelimiter(i - 1) != null) {
		// tempsb.append(tempdocument.getLineDelimiter(i - 1));
		// } else {
		// tempsb.append(newline);
		// }
		// }
		// if (i == 0) {
		// tempsb.append(tempdocument.get(region.getOffset(),
		// region.getLength()).trim());
		// } else {
		// tempsb.append(tempdocument.get(region.getOffset(),
		// region.getLength()));
		// }
		// }
		// } catch (BadLocationException e) {
		// }
		// JobSafeStructuredDocument newdocument = new
		// JobSafeStructuredDocument(
		// new PhpSourceParser());
		// String start = "<?php";
		// newdocument.set(start + newline + tempsb.toString());
		// IContentFormatter contentFormatter = PHPFormatProcessorProxy
		// .getFormatter();
		// if (contentFormatter instanceof IFormatterProcessorFactory) {
		// IStructuredModel structuredModel = null;
		// try {
		// if (document instanceof IStructuredDocument) {
		// IStructuredDocument structuredDocument = (IStructuredDocument)
		// document;
		// structuredModel = StructuredModelManager.getModelManager()
		// .getExistingModelForRead(document);
		// DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;
		//
		// IProject project = getProject(doModelForPHP);
		// ((IFormatterProcessorFactory) contentFormatter)
		// .setDefaultProject(project);
		// }
		// } catch (Exception e) {
		// } finally {
		// if (structuredModel != null) {
		// structuredModel.releaseFromRead();
		// }
		// }
		//
		// }
		// contentFormatter.format(newdocument,
		// new Region(0, newdocument.getLength()));
		//
		// List<String> list = new ArrayList<String>();
		// List<String> lineEndList = new ArrayList<String>();
		// try {
		// int lineNumber = newdocument.getNumberOfLines();
		// for (int i = 0; i < lineNumber; i++) {
		// if (i == 0) {
		// continue;
		// }
		// IRegion region = newdocument.getLineInformation(i);
		// String line = newdocument.get(region.getOffset(),
		// region.getLength());
		// list.add(line);
		// if (tempdocument.getLineDelimiter(i) != null) {
		// lineEndList.add(tempdocument.getLineDelimiter(i));
		// }
		// }
		// } catch (BadLocationException e) {
		// }
		// String defaultLineDelimiter = newdocument.getDefaultLineDelimiter();
		// PhpFormatter formatter = new PhpFormatter(0,
		// newdocument.getLength());
		// formatter.format(newdocument.getFirstStructuredDocumentRegion());
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < list.size(); i++) {
		// if (!formatter.getIgnoreLines().contains(i + 1)) {
		// sb.append(helpBuffer.toString());
		// }
		// sb.append(list.get(i));
		// if (i != list.size() - 1) {
		// if (i < lineEndList.size()) {
		// sb.append(lineEndList.get(i));
		// } else {
		// if (!lineEndList.isEmpty()) {
		// sb.append(lineEndList.get(0));
		// } else {
		// sb.append(defaultLineDelimiter);
		// }
		// }
		// }
		// }
		// command.text = sb.toString();
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
