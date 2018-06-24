/*******************************************************************************
 * Copyright (c) 2009, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.hover;

import java.util.*;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPHP;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.document.DocumentReader;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;

/**
 * Source viewer based implementation of <code>IInformationControl</code>.
 * Displays information in a source viewer.
 * 
 * @since 3.0
 */
public class PHPSourceViewerInformationControl implements IInformationControl, IInformationControlExtension,
		IInformationControlExtension2, IInformationControlExtension3, IInformationControlExtension5, DisposeListener {

	/** Border thickness in pixels. */
	private static final int BORDER = 1;
	/** The control's shell */
	private Shell fShell;
	/** The control's text widget */
	private StyledText fText;
	/** The text font (do not dispose!) */
	private Font fTextFont;
	/** The control's source viewer */
	private SourceViewer fViewer;
	/**
	 * The optional status field.
	 * 
	 * @since 3.0
	 */
	private Label fStatusField;
	/**
	 * The separator for the optional status field.
	 * 
	 * @since 3.0
	 */
	private Label fSeparator;
	/**
	 * The font of the optional status text label.
	 * 
	 * @since 3.0
	 */
	private Font fStatusTextFont;
	/**
	 * The width size constraint.
	 * 
	 * @since 3.2
	 */
	private int fMaxWidth = SWT.DEFAULT;
	/**
	 * The height size constraint.
	 * 
	 * @since 3.2
	 */
	private int fMaxHeight = SWT.DEFAULT;

	private Dictionary<?, ?> fContextStyleMap = null;
	private IStructuredDocumentRegion fNodes = null;
	private RegionParser fParser = null;
	private LineStyleProviderForPHP styleProvider;
	private String fInput = ""; //$NON-NLS-1$
	private Color fBackgroundColor;
	private boolean fIsSystemBackgroundColor = false;

	/**
	 * @param newParser
	 */
	public void setParser(RegionParser newParser) {
		fParser = newParser;
	}

	/**
	 * @param newContextStyleMap
	 *            java.util.Dictionary
	 */
	public void setContextStyleMap(Dictionary<?, ?> newContextStyleMap) {
		fContextStyleMap = newContextStyleMap;
	}

	/**
	 * @return java.util.Dictionary
	 */
	public Dictionary<?, ?> getContextStyleMap() {
		return fContextStyleMap;
	}

	public RegionParser getParser() {
		return fParser;
	}

	public TextAttribute getAttribute(String namedStyle) {
		TextAttribute ta = new TextAttribute(fText.getBackground(), fText.getForeground(), SWT.NORMAL);
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
		final LineStyleProviderForPHP styler = new LineStyleProviderForPHP();
		final Collection<StyleRange> holdResults = new ArrayList<>();
		styler.prepareTextRegions(node, 0, fNodes.getEnd(), holdResults);

		for (Iterator<StyleRange> iter = holdResults.iterator(); iter.hasNext();) {
			StyleRange element = iter.next();
			fText.setStyleRange(element);
		}
	}

	public void initColorsMap() {
		IModelManager mmanager = StructuredModelManager.getModelManager();
		setParser(mmanager.createStructuredDocumentFor(ContentTypeIdForPHP.ContentTypeID_PHP).getParser());

		styleProvider = new LineStyleProviderForPHP();
		Dictionary<?, ?> contextStyleMap = new Hashtable<Object, Object>(styleProvider.getColorTypesMap());

		setContextStyleMap(contextStyleMap);
	}

	/**
	 * Creates a default information control with the given shell as parent. The
	 * given information presenter is used to process the information to be
	 * displayed. The given styles are applied to the created styled text
	 * widget.
	 * 
	 * @param parent
	 *            the parent shell
	 * @param shellStyle
	 *            the additional styles for the shell
	 * @param style
	 *            the additional styles for the styled text widget
	 */
	public PHPSourceViewerInformationControl(Shell parent, int shellStyle, int style) {
		this(parent, shellStyle, style, null);
	}

	/**
	 * Creates a default information control with the given shell as parent. The
	 * given information presenter is used to process the information to be
	 * displayed. The given styles are applied to the created styled text
	 * widget.
	 * 
	 * @param parent
	 *            the parent shell
	 * @param shellStyle
	 *            the additional styles for the shell
	 * @param style
	 *            the additional styles for the styled text widget
	 * @param statusFieldText
	 *            the text to be used in the optional status field or
	 *            <code>null</code> if the status field should be hidden
	 * @since 3.0
	 */
	public PHPSourceViewerInformationControl(Shell parent, int shellStyle, int style, String statusFieldText) {
		GridLayout layout;
		GridData gd;

		fShell = new Shell(parent, SWT.NO_FOCUS | SWT.ON_TOP | shellStyle);
		Display display = fShell.getDisplay();
		// fShell.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		style = style | SWT.V_SCROLL | SWT.H_SCROLL;
		initializeColors();

		Composite composite = fShell;
		layout = new GridLayout(1, false);
		int border = ((shellStyle & SWT.NO_TRIM) == 0) ? 0 : BORDER;
		layout.marginHeight = border;
		layout.marginWidth = border;
		composite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gd);

		if (statusFieldText != null) {
			composite = new Composite(composite, SWT.NONE);
			layout = new GridLayout(1, false);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.verticalSpacing = 1;
			composite.setLayout(layout);
			gd = new GridData(GridData.FILL_BOTH);
			composite.setLayoutData(gd);
			composite.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
			composite.setBackground(fBackgroundColor);
		}

		// Source viewer
		fViewer = new ProjectionViewer(composite, null, null, false, style);
		fViewer.setEditable(false);

		fText = fViewer.getTextWidget();
		gd = new GridData(GridData.BEGINNING | GridData.FILL_BOTH);
		fText.setLayoutData(gd);
		fText.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		fText.setBackground(fBackgroundColor);

		initializeFont();

		fText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.character == 0x1B) {
					fShell.dispose();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		// Status field
		if (statusFieldText != null) {

			// Horizontal separator line
			fSeparator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.LINE_DOT);
			fSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			// Status field label
			fStatusField = new Label(composite, SWT.RIGHT);
			fStatusField.setText(statusFieldText);
			Font font = fStatusField.getFont();
			FontData[] fontDatas = font.getFontData();
			for (int i = 0; i < fontDatas.length; i++) {
				fontDatas[i].setHeight(fontDatas[i].getHeight() * 9 / 10);
			}
			fStatusTextFont = new Font(fStatusField.getDisplay(), fontDatas);
			fStatusField.setFont(fStatusTextFont);
			GridData gd2 = new GridData(GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL
					| GridData.HORIZONTAL_ALIGN_BEGINNING | GridData.VERTICAL_ALIGN_BEGINNING);
			fStatusField.setLayoutData(gd2);

			// Regarding the color see bug 41128
			fStatusField.setForeground(display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));

			fStatusField.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		}

		initColorsMap();
		addDisposeListener(this);
	}

	private void initializeColors() {

		final IPreferenceStore editorStore = EditorsPlugin.getDefault().getPreferenceStore();
		RGB bgRGB;
		if (editorStore.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)) {
			bgRGB = null;
		} else {
			bgRGB = PreferenceConverter.getColor(editorStore, AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
		}
		if (bgRGB != null) {
			fBackgroundColor = new Color(fShell.getDisplay(), bgRGB);
			fIsSystemBackgroundColor = false;
		} else {
			fBackgroundColor = fShell.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
			fIsSystemBackgroundColor = true;
		}
	}

	/**
	 * Creates a default information control with the given shell as parent. The
	 * given information presenter is used to process the information to be
	 * displayed. The given styles are applied to the created styled text
	 * widget.
	 * 
	 * @param parent
	 *            the parent shell
	 * @param style
	 *            the additional styles for the styled text widget
	 */
	public PHPSourceViewerInformationControl(Shell parent, int style) {
		this(parent, SWT.NO_TRIM | SWT.TOOL, style);
	}

	/**
	 * Creates a default information control with the given shell as parent. The
	 * given information presenter is used to process the information to be
	 * displayed. The given styles are applied to the created styled text
	 * widget.
	 * 
	 * @param parent
	 *            the parent shell
	 * @param style
	 *            the additional styles for the styled text widget
	 * @param statusFieldText
	 *            the text to be used in the optional status field or
	 *            <code>null</code> if the status field should be hidden
	 * @since 3.0
	 */
	public PHPSourceViewerInformationControl(Shell parent, int style, String statusFieldText) {
		this(parent, SWT.NO_TRIM | SWT.TOOL, style, statusFieldText);
	}

	/**
	 * Creates a default information control with the given shell as parent. No
	 * information presenter is used to process the information to be displayed.
	 * No additional styles are applied to the styled text widget.
	 * 
	 * @param parent
	 *            the parent shell
	 */
	public PHPSourceViewerInformationControl(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Creates a default information control with the given shell as parent. No
	 * information presenter is used to process the information to be displayed.
	 * No additional styles are applied to the styled text widget.
	 * 
	 * @param parent
	 *            the parent shell
	 * @param statusFieldText
	 *            the text to be used in the optional status field or
	 *            <code>null</code> if the status field should be hidden
	 * @since 3.0
	 */
	public PHPSourceViewerInformationControl(Shell parent, String statusFieldText) {
		this(parent, SWT.NONE, statusFieldText);
	}

	/**
	 * Initialize the font to the SSE editor font.
	 * 
	 * @since 3.2
	 */
	private void initializeFont() {
		fTextFont = JFaceResources.getFont("org.eclipse.wst.sse.ui.textfont"); //$NON-NLS-1$
		StyledText styledText = getViewer().getTextWidget();
		styledText.setFont(fTextFont);
	}

	public void setInput(Object input) {
		if (input instanceof String) {
			setInformation((String) input);
		} else {
			setInformation(null);
		}
	}

	@Override
	public void setInformation(String content) {
		if (content == null) {
			fViewer.setInput(null);
			return;
		}

		// Add open and close PHP tags in order to enable PHP parser on content:
		StringBuilder buf = new StringBuilder();
		buf.append("<?"); //$NON-NLS-1$
		buf.append(content);
		buf.append("?>"); //$NON-NLS-1$

		IDocument doc = new Document(buf.toString());
		DocumentReader docReader = new DocumentReader(doc);
		getParser().reset(docReader);
		IStructuredDocumentRegion sdRegion = getParser().getDocumentRegions();

		// This hack is needed in order to remove the open and close PHP tags we
		// added before.
		ITextRegionList phpRegionsList = sdRegion.getRegions();
		phpRegionsList.remove(0); // remove open PHP tag
		phpRegionsList.remove(phpRegionsList.size() - 1); // remove close PHP
															// tag

		// Update starting location of PHP token regions:
		Iterator<?> i = phpRegionsList.iterator();
		while (i.hasNext()) {
			ITextRegion tokenRegion = (ITextRegion) i.next();
			tokenRegion.adjustStart(-2);
		}
		sdRegion.setRegions(phpRegionsList);

		fInput = content;
		fNodes = sdRegion;

		doc = new Document(fInput);
		doc.setDocumentPartitioner(new PHPStructuredTextPartitioner());
		fViewer.setInput(doc);
		applyStyles();
	}

	@Override
	public void setVisible(boolean visible) {
		fShell.setVisible(visible);
	}

	@Override
	public void widgetDisposed(DisposeEvent event) {
		if (fStatusTextFont != null && !fStatusTextFont.isDisposed()) {
			fStatusTextFont.dispose();
		}

		if (fBackgroundColor != null && !fBackgroundColor.isDisposed() && !fIsSystemBackgroundColor) {
			fBackgroundColor.dispose();
		}

		fStatusTextFont = null;
		fShell = null;
		fText = null;
	}

	@Override
	public final void dispose() {
		if (fBackgroundColor != null && !fBackgroundColor.isDisposed() && !fIsSystemBackgroundColor) {
			fBackgroundColor.dispose();
		}
		if (fShell != null && !fShell.isDisposed()) {
			fShell.dispose();
		} else {
			widgetDisposed(null);
		}
	}

	@Override
	public void setSize(int width, int height) {

		if (fStatusField != null) {
			GridData gd = (GridData) fViewer.getTextWidget().getLayoutData();
			Point statusSize = fStatusField.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			Point separatorSize = fSeparator.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			gd.heightHint = height - statusSize.y - separatorSize.y;
		}
		fShell.setSize(width, height);

		if (fStatusField != null) {
			fShell.pack(true);
		}
	}

	@Override
	public void setLocation(Point location) {
		fShell.setLocation(location);
	}

	@Override
	public void setSizeConstraints(int maxWidth, int maxHeight) {
		fMaxWidth = maxWidth;
		fMaxHeight = maxHeight;
	}

	@Override
	public Point computeSizeHint() {
		// compute the preferred size
		int x = SWT.DEFAULT;
		int y = SWT.DEFAULT;
		Point size = fShell.computeSize(x, y);
		if (size.x > fMaxWidth) {
			x = fMaxWidth;
		}
		if (size.y > fMaxHeight) {
			y = fMaxHeight;
		}

		// recompute using the constraints if the preferred size is larger than
		// the constraints
		if (x != SWT.DEFAULT || y != SWT.DEFAULT) {
			size = fShell.computeSize(x, y, false);
		}

		return size;
	}

	@Override
	public void addDisposeListener(DisposeListener listener) {
		fShell.addDisposeListener(listener);
	}

	@Override
	public void removeDisposeListener(DisposeListener listener) {
		fShell.removeDisposeListener(listener);
	}

	@Override
	public void setForegroundColor(Color foreground) {
		fText.setForeground(foreground);
	}

	@Override
	public void setBackgroundColor(Color background) {
		fText.setBackground(background);
	}

	@Override
	public boolean isFocusControl() {
		return fText.isFocusControl();
	}

	@Override
	public void setFocus() {
		fShell.forceFocus();
		fText.setFocus();
	}

	@Override
	public void addFocusListener(FocusListener listener) {
		fText.addFocusListener(listener);
	}

	@Override
	public void removeFocusListener(FocusListener listener) {
		fText.removeFocusListener(listener);
	}

	@Override
	public boolean hasContents() {
		return fText.getCharCount() > 0;
	}

	protected ISourceViewer getViewer() {
		return fViewer;
	}

	@Override
	public boolean containsControl(Control control) {
		do {
			if (control == fShell)
				return true;
			if (control instanceof Shell)
				return false;
			control = control.getParent();
		} while (control != null);
		return false;
	}

	@Override
	public boolean isVisible() {
		return fShell != null && !fShell.isDisposed() && fShell.isVisible();
	}

	@Override
	public Point computeSizeConstraints(int widthInChars, int heightInChars) {
		GC gc = new GC(fText);
		gc.setFont(fTextFont);
		double width = gc.getFontMetrics().getAverageCharacterWidth();
		int height = fText.getLineHeight(); // https://bugs.eclipse.org/bugs/show_bug.cgi?id=377109
		gc.dispose();

		return new Point((int) (widthInChars * width), heightInChars * height);
	}

	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			@Override
			public IInformationControl createInformationControl(Shell parent) {
				return new PHPSourceViewerInformationControl(parent);
			}
		};
	}

	@Override
	public Rectangle getBounds() {
		return fShell.getBounds();
	}

	@Override
	public Rectangle computeTrim() {
		Rectangle trim = fShell.computeTrim(0, 0, 0, 0);
		addInternalTrim(trim);
		return trim;
	}

	private void addInternalTrim(Rectangle trim) {
		Rectangle textTrim = fText.computeTrim(0, 0, 0, 0);
		trim.x += textTrim.x;
		trim.y += textTrim.y;
		trim.width += textTrim.width;
		trim.height += textTrim.height;

		if (fStatusField != null) {
			trim.height += fSeparator.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			trim.height += fStatusField.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
			trim.height += 1; // verticalSpacing
		}
	}

	@Override
	public boolean restoresSize() {
		return false;
	}

	@Override
	public boolean restoresLocation() {
		return false;
	}
}
