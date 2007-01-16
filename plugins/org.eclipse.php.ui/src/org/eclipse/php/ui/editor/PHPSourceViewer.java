/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.editor;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TextChangeListener;
import org.eclipse.swt.custom.TextChangedEvent;
import org.eclipse.swt.custom.TextChangingEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wst.sse.core.internal.document.DocumentReader;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;

public class PHPSourceViewer extends Composite {

	private Dictionary fContextStyleMap = null;
	private Color fDefaultBackground = getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	private Color fDefaultForeground = getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
	private String fInput = ""; //$NON-NLS-1$

	private IStructuredDocumentRegion fNodes = null;
	private RegionParser fParser = null;

	private StyledText fText = null;
	private LineStyleProviderForPhp styleProvider;

	public PHPSourceViewer(Composite parent, int style) {
		super(parent, style);
		FillLayout layout = new FillLayout();
		setLayout(layout);
		//		GridData data = new GridData(GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL);
		//		setLayoutData(data);
		createControls(this);
		setupViewer();
	}

	public void createControls(Composite parent) {
		fText = new StyledText(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		fText.getContent().addTextChangeListener(new TextChangeListener() {

			DocumentReader docReader;
			Document document;

			public void textChanging(TextChangingEvent event) {
			}

			public void textChanged(TextChangedEvent event) {
				if (docReader == null) {
					document = new Document();
					docReader = new DocumentReader();
				}
				fInput = getText();
				document.set(fInput);
				docReader.reset(document, 0);
				getParser().reset(docReader);
				fNodes = getParser().getDocumentRegions();
				applyStyles();

			}

			public void textSet(TextChangedEvent event) {
			}

		});
		fText.setBackground(fDefaultBackground);
		fText.setFont(JFaceResources.getTextFont());
		setAccessible(fText, SSEUIMessages.Sample_text__UI_); //$NON-NLS-1$ = "&Sample text:"
	}

	public void setEditable(boolean editable) {
		fText.setEditable(editable);
	}

	/**
	 * @return java.util.Dictionary
	 */
	public Dictionary getContextStyleMap() {
		return fContextStyleMap;
	}

	/**
	 * @return org.eclipse.swt.graphics.Color
	 */
	public Color getDefaultBackground() {
		return fDefaultBackground;
	}

	/**
	 * @return org.eclipse.swt.graphics.Color
	 */
	public Color getDefaultForeground() {
		return fDefaultForeground;
	}

	public Font getFont() {
		return fText.getFont();
	}

	public RegionParser getParser() {
		return fParser;
	}

	public String getText() {
		return fText.getText();
	}

	// refresh the GUI after a color change
	public void refresh() {
		fText.setRedraw(false);
		// update Font
		fText.setFont(JFaceResources.getTextFont());
		// reapplyStyles
		applyStyles();
		fText.setRedraw(true);
	}

	public void releasePickerResources() {
	}

	/**
	 * Specifically set the reporting name of a control for accessibility
	 */
	private void setAccessible(Control control, String name) {
		if (control == null)
			return;
		final String n = name;
		control.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				if (e.childID == ACC.CHILDID_SELF)
					e.result = n;
			}
		});
	}

	/**
	 * @param newContextStyleMap
	 *            java.util.Dictionary
	 */
	public void setContextStyleMap(Dictionary newContextStyleMap) {
		fContextStyleMap = newContextStyleMap;
	}

	/**
	 * @param newDefaultBackground
	 *            org.eclipse.swt.graphics.Color
	 */
	public void setDefaultBackground(Color newDefaultBackground) {
		fDefaultBackground = newDefaultBackground;
	}

	/**
	 * @param newDefaultForeground
	 *            org.eclipse.swt.graphics.Color
	 */
	public void setDefaultForeground(Color newDefaultForeground) {
		fDefaultForeground = newDefaultForeground;
	}

	public void setFont(Font font) {
		fText.setFont(font);
		fText.redraw();
	}

	/**
	 * @param newParser
	 */
	public void setParser(RegionParser newParser) {
		fParser = newParser;
	}

	public TextAttribute getAttribute(String namedStyle) {
		TextAttribute ta = new TextAttribute(getDefaultForeground(), getDefaultBackground(), SWT.NORMAL);
		if (namedStyle != null && styleProvider != null) {
			ta = styleProvider.getTextAttributeForColor(namedStyle);
		}
		return ta;
	}

	public void applyStyles() {
		if (fText == null || fText.isDisposed() || fInput == null || fInput.length() == 0) {
			return;
		}
		IStructuredDocumentRegion node = fNodes;
		while (node != null) {
			ITextRegionList regions = node.getRegions();
			for (int i = 0; i < regions.size(); i++) {
				ITextRegion currentRegion = regions.get(i);
				// lookup the local coloring type and apply it
				String namedStyle = (String) getContextStyleMap().get(currentRegion.getType());
				if (namedStyle == null) {
					continue;
				}
				TextAttribute attribute = getAttribute(namedStyle);
				if (attribute == null) {
					continue;
				}

				StyleRange style = new StyleRange(node.getStartOffset(currentRegion), currentRegion.getLength(), attribute.getForeground(), attribute.getBackground(), attribute.getStyle());

				if ((attribute.getStyle() & TextAttribute.UNDERLINE) != 0) {
					style.underline = true;
					style.fontStyle &= ~TextAttribute.UNDERLINE;
				}

				fText.setStyleRange(style);
			}
			node = node.getNext();
		}
	}

	public void setText(String s) {
		fInput = s;
		DocumentReader docReader = new DocumentReader(new Document(fInput));
		getParser().reset(docReader);
		fNodes = getParser().getDocumentRegions();
		if (fText != null) {
			fText.setText(s);
		}
		applyStyles();
	}

	public void setupViewer() {
		IModelManager mmanager = StructuredModelManager.getModelManager();
		setParser(mmanager.createStructuredDocumentFor(ContentTypeIdForPHP.ContentTypeID_PHP).getParser());

		styleProvider = new LineStyleProviderForPhp();
		Dictionary contextStyleMap = new Hashtable(styleProvider.getColorTypesMap());
		setContextStyleMap(contextStyleMap);
	}

	public StyledText getTextWidget() {
		return fText;
	}

}
