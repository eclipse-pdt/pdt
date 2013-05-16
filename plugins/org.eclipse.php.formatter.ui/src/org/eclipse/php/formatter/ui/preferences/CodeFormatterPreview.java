/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/

package org.eclipse.php.formatter.ui.preferences;

import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.core.CodeFormatterVisitor;
import org.eclipse.php.formatter.core.ReplaceEdit;
import org.eclipse.php.formatter.ui.Logger;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.swt.widgets.Composite;

public class CodeFormatterPreview extends PhpPreview {

	private final IDocument fPreviewDocument;
	protected String fOriInput;

	/**
	 * @param workingValues
	 * @param parent
	 */
	public CodeFormatterPreview(
			CodeFormatterPreferences codeFormatterPreferences, Composite parent) {
		super(codeFormatterPreferences, parent);

		fPreviewDocument = new Document();
	}

	protected void doFormatPreview() {
		fText.setRedraw(false);
		try {
			fPreviewDocument.set(fOriInput);
			IRegion region = new Region(0, fPreviewDocument.getLength());
			CodeFormatterVisitor codeFormatter = new CodeFormatterVisitor(
					fPreviewDocument, codeFormatterPreferences,
					System.getProperty(Platform.PREF_LINE_SEPARATOR),
					PHPVersion.PHP5_3, true, region);
			List<?> changes = codeFormatter.getChanges();
			fInput = applyChanges(fOriInput, changes);

			setText(fInput);
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			fText.setRedraw(true);
		}
	}

	public void setPreviewText(String previewText) {
		assert previewText != null;
		fOriInput = previewText;
		fInput = previewText;
		update();
	}

	private String applyChanges(String content, List<?> changes) {
		StringBuffer result = new StringBuffer(content);
		for (int i = changes.size() - 1; i >= 0; i--) {
			ReplaceEdit replace = (ReplaceEdit) changes.get(i);
			result = result.replace(replace.offset, replace.getEnd(),
					replace.content);
		}
		return result.toString();
	}
}
