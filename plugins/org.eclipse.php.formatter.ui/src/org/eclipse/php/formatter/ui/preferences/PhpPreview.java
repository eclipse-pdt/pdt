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

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

public abstract class PhpPreview {
	protected String fInput;
	protected final StyledText fText;
	protected IStructuredDocumentRegion fNodes;
	protected RegionParser fParser;
	protected IPreferenceStore fPreferenceStore;
	protected LineStyleProviderForPhp fStyleProvider = new LineStyleProviderForPhp();

	protected CodeFormatterPreferences codeFormatterPreferences;

	/**
	 * Create a new Java preview
	 * 
	 * @param workingValues
	 * @param parent
	 */
	public PhpPreview(CodeFormatterPreferences codeFormatterPreferences,
			Composite parent) {
		// set the PHP parser
		IModelManager mmanager = StructuredModelManager.getModelManager();
		fParser = mmanager.createStructuredDocumentFor(
				ContentTypeIdForPHP.ContentTypeID_PHP).getParser();

		this.codeFormatterPreferences = codeFormatterPreferences;

		// create the text area
		fText = new StyledText(parent, SWT.LEFT_TO_RIGHT | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_BOTH);
		fText.setLayoutData(data);
		fText.setEditable(false);
		fText.setFont(JFaceResources.getTextFont());
	}

	public Control getControl() {
		return fText;
	}

	public void update() {
		final int height = fText.getClientArea().height;
		final int top0 = fText.getTopPixel();

		final int totalPixels0 = getHeightOfAllLines(fText);
		final int topPixelRange0 = totalPixels0 > height ? totalPixels0
				- height : 0;

		fText.setRedraw(false);
		doFormatPreview();

		final int totalPixels1 = getHeightOfAllLines(fText);
		final int topPixelRange1 = totalPixels1 > height ? totalPixels1
				- height : 0;

		final int top1 = topPixelRange0 > 0 ? (int) (topPixelRange1 * top0 / (double) topPixelRange0)
				: 0;
		fText.setTopPixel(top1);
		fText.setRedraw(true);
	}

	private int getHeightOfAllLines(StyledText styledText) {
		int height = 0;
		int lineCount = styledText.getLineCount();
		for (int i = 0; i < lineCount; i++) {
			height = height
					+ styledText.getLineHeight(styledText.getOffsetAtLine(i));
		}
		return height;
	}

	protected abstract void doFormatPreview();

	protected void applyStyles() {
		if (fText == null || fText.isDisposed() || fInput == null
				|| fInput.length() == 0) {
			return;
		}

		final IPreferenceStore editorStore = EditorsPlugin.getDefault()
				.getPreferenceStore();
		fText.setBackground(editorStore
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT) ? null
				: new Color(fText.getDisplay(), PreferenceConverter.getColor(
						editorStore,
						AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND)));

		fStyleProvider.loadColors();

		IStructuredDocumentRegion node = fNodes;
		final Collection holdResults = new ArrayList();
		fStyleProvider
				.prepareTextRegions(node, 0, fInput.length(), holdResults);

		for (Iterator iter = holdResults.iterator(); iter.hasNext();) {
			StyleRange element = (StyleRange) iter.next();
			fText.setStyleRange(element);
		}
	}

	public void setText(String s) {
		fInput = s;
		getParser().reset(new CharArrayReader(fInput.toCharArray()));
		fNodes = getParser().getDocumentRegions();
		if (fText != null) {
			fText.setText(s);
		}
		applyStyles();
	}

	public RegionParser getParser() {
		return fParser;
	}

	public final CodeFormatterPreferences getPreferences() {
		return codeFormatterPreferences;
	}

	public final void setPreferences(
			CodeFormatterPreferences codeFormatterPreferences) {
		this.codeFormatterPreferences = codeFormatterPreferences;
	}

}
