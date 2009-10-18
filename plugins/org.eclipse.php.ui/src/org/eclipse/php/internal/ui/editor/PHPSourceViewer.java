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
package org.eclipse.php.internal.ui.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.wst.html.ui.internal.style.LineStyleProviderForHTML;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.document.DocumentReader;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;

public class PHPSourceViewer extends Composite implements
		IPropertyChangeListener {

	private Color fDefaultBackground;
	private Color fDefaultForeground;
	private String fInput = ""; //$NON-NLS-1$

	private IStructuredDocumentRegion fNodes = null;
	private RegionParser fParser = null;

	private StyledText fText = null;
	private LineStyleProviderForPhp styleProvider;

	private IPreferenceStore editorStore;

	public PHPSourceViewer(Composite parent, int style) {
		super(parent, style);

		editorStore = EditorsPlugin.getDefault().getPreferenceStore();

		fDefaultBackground = editorStore
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT) ? null
				: new Color(getDisplay(), PreferenceConverter.getColor(
						editorStore,
						AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND));
		fDefaultForeground = editorStore
				.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT) ? null
				: new Color(getDisplay(), PreferenceConverter.getColor(
						editorStore,
						AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND));

		editorStore.addPropertyChangeListener(this);

		FillLayout layout = new FillLayout();
		setLayout(layout);
		// GridData data = new GridData(GridData.FILL_BOTH |
		// GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL);
		// setLayoutData(data);
		createControls(this);
		setupViewer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse
	 * .jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(final PropertyChangeEvent event) {
		if (AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT
				.equals(event.getProperty())) {
			if (editorStore
					.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT))
				fDefaultBackground = null;
			else
				fDefaultBackground = new Color(Display.getCurrent(),
						PreferenceConverter.getColor(editorStore,
								AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND));
			refresh();
		} else if (AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT
				.equals(event.getProperty())) {
			if (editorStore
					.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT))
				fDefaultForeground = null;
			else
				fDefaultForeground = new Color(Display.getCurrent(),
						PreferenceConverter.getColor(editorStore,
								AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND));
			refresh();
		} else if (AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND.equals(event
				.getProperty())) {
			fDefaultBackground = new Color(Display.getCurrent(),
					PreferenceConverter.getColor(editorStore,
							AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND));
			refresh();
		} else if (AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND.equals(event
				.getProperty())) {
			fDefaultForeground = new Color(Display.getCurrent(),
					PreferenceConverter.getColor(editorStore,
							AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND));
			refresh();
		}
	}

	public void createControls(Composite parent) {
		fText = new StyledText(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
				| SWT.BORDER);
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
		fText.setForeground(fDefaultForeground);
		fText.setFont(JFaceResources.getTextFont());
		setAccessible(fText, SSEUIMessages.Sample_text__UI_); //$NON-NLS-1$ = "&Sample text:"
	}

	public void setEditable(boolean editable) {
		fText.setEditable(editable);
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
		if (!fText.isDisposed()) {
			fText.setRedraw(false);
			fText.setBackground(fDefaultBackground);
			fText.setForeground(fDefaultForeground);
			// update Font
			fText.setFont(JFaceResources.getTextFont());
			// reapplyStyles
			applyStyles();
			fText.setRedraw(true);
		}
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
		TextAttribute ta = new TextAttribute(getDefaultForeground(),
				getDefaultBackground(), SWT.NORMAL);
		if (namedStyle != null && styleProvider != null) {
			ta = styleProvider.getTextAttributeForColor(namedStyle);
		}
		return ta;
	}

	public void applyStyles() {
		if (fText == null || fText.isDisposed() || fInput == null
				|| fInput.length() == 0) {
			return;
		}

		styleProvider.loadColors();
		LineStyleProviderForHTML lineStyleProviderForHTML = new LineStyleProviderForHTML();

		IStructuredDocumentRegion documentRegion = fNodes;
		while (documentRegion != null) {
			final Collection holdResults = new ArrayList();
			styleProvider.prepareTextRegions(documentRegion, 0, documentRegion
					.getEnd(), holdResults);

			for (Iterator iter = holdResults.iterator(); iter.hasNext();) {
				StyleRange element = (StyleRange) iter.next();
				fText.setStyleRange(element);
			}
			documentRegion = documentRegion.getNext();
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
		setParser(mmanager.createStructuredDocumentFor(
				ContentTypeIdForPHP.ContentTypeID_PHP).getParser());

		styleProvider = new LineStyleProviderForPhp();
	}

	public StyledText getTextWidget() {
		return fText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		editorStore.removePropertyChangeListener(this);
		fDefaultBackground.dispose();
		fDefaultForeground.dispose();
		super.dispose();
	}

}
