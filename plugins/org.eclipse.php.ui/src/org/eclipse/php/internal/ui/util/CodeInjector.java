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
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * This class handles both as a code injector and code formatter of injected
 * code. If the code injector is not used to inject code in a PHP file, such as
 * in new PHP file, then it can be used only to format the code.
 * 
 * @author yaronm
 */
public class CodeInjector {
	private int offset = -1;
	private IStructuredDocument document = null;
	private IStructuredModel model = null;
	private ISourceModule existingPHPFile = null;

	public CodeInjector() {
	}

	/**
	 * @param existingPHPFile
	 *            - The existing php file object in which we want to inject the
	 *            new element
	 * @param offset
	 *            - The StructuredDocument offset from which we inject the code
	 */
	public CodeInjector(ISourceModule existingPHPFile, int offset) {
		this.existingPHPFile = existingPHPFile;
		this.offset = offset;
	}

	public CodeInjector(ISourceModule existingPHPFile) {
		this.existingPHPFile = existingPHPFile;
	}

	// prepares the document
	private void init() {

		try {
			// fixed bug when working on untitled php docs
			// openInEditor opened the wrong editor (regular php editor)
			IEditorPart editor = org.eclipse.dltk.internal.ui.editor.EditorUtility
					.openInEditor(existingPHPFile, true);
			document = (IStructuredDocument) ((PHPStructuredEditor) editor)
					.getDocument();
			// The model is fetched for editing and must be released
			model = StructuredModelManager.getModelManager().getModelForEdit(
					document);
		} catch (PartInitException e) {
			Logger.logException(e);
		} catch (ModelException e) {
			Logger.logException(e);
		} finally {
			model.releaseFromEdit();
		}
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Performs code injection
	 * 
	 * @param doFormat
	 *            - Whether to perform formatting after code injection
	 * @param saveChanges
	 *            - Whether to save the PHP file after code injection
	 */
	public void inject(String contents, boolean doFormat, boolean saveChanges) {
		init();
		if (offset == -1) {
			offset = document.getLength();
		}
		// inject
		document.replaceText(this, offset, 0, contents);

		// format
		if (doFormat) {
			formatDocument(document, offset, contents.length() + 1);
		}

		// save file
		if (saveChanges) {
			try {
				model.save();
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	/**
	 * Format the given document according to the given offset and length fixed
	 * bug 14448 - Change the formatter - used the new formatter based on the
	 * AST
	 * 
	 * @param document
	 * @param offset
	 * @param length
	 *            TODO - Today, we perform format on the code AFTER it is
	 *            injected. This issue needs to be reviewed again when OFFLINE
	 *            formatting will be available.
	 */
	public void formatDocument(IStructuredDocument document, int offset,
			int length) {
		IContentFormatter formatter = null;
		if (formatter == null) {
			formatter = PHPUiPlugin.getDefault().getActiveFormatter();
		}
		try {
			formatter.format(document, new Region(offset, length));
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public int getOffset() {
		return offset;
	}
}
