package org.eclipse.php.internal.ui.preferences;

/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.SemanticHighlightingManager;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.php.internal.ui.editor.highlighters.*;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.ui.ISemanticHighlighting;
import org.eclipse.wst.sse.ui.ISemanticHighlightingExtension2;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;
import org.eclipse.wst.xml.ui.internal.XMLUIMessages;

import com.ibm.icu.text.Collator;

/**
 * A preference page to configure our XML syntax color. It resembles the JDT and
 * CDT pages far more than our original color page while retaining the extra
 * "click-to-find" functionality.
 */
public final class PHPSyntaxColoringPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Button fBold;
	private Label fForegroundLabel;
	private Label fBackgroundLabel;
	private Button fClearStyle;
	private Map<String, String> fContextToStyleMap;
	private Color fDefaultForeground = null;
	private Color fDefaultBackground = null;
	private IStructuredDocument fDocument;
	private ColorSelector fForegroundColorEditor;
	private ColorSelector fBackgroundColorEditor;
	private Button fItalic;
	private OverlayPreferenceStore fOverlayStore;
	private Button fStrike;
	private Collection<String> fStylePreferenceKeys;
	private StructuredViewer fStylesViewer = null;
	private Map<String, String> fStyleToDescriptionMap;
	private StyledText fText;
	private Button fUnderline;
	private final LineStyleProviderForPhp fStyleProvider;
	private Button fEnabler;
	private static Map<String, Position[]> highlightingPositionMap;
	private static Map<String, HighlightingStyle> highlightingStyleMap;

	public PHPSyntaxColoringPage() {
		fStyleProvider = new LineStyleProviderForPhp();
	}

	// activate controls based on the given local color type
	private void activate(String namedStyle) {
		Color foreground = fDefaultForeground;
		Color background = fDefaultBackground;
		if (namedStyle == null) {
			fEnabler.setEnabled(false);
			fClearStyle.setEnabled(false);
			fBold.setEnabled(false);
			fItalic.setEnabled(false);
			fStrike.setEnabled(false);
			fUnderline.setEnabled(false);
			fForegroundLabel.setEnabled(false);
			fBackgroundLabel.setEnabled(false);
			fForegroundColorEditor.setEnabled(false);
			fBackgroundColorEditor.setEnabled(false);
			fBold.setSelection(false);
			fItalic.setSelection(false);
			fStrike.setSelection(false);
			fUnderline.setSelection(false);
		} else {
			TextAttribute attribute = getAttributeFor(namedStyle);
			AbstractSemanticHighlighting semanticType = SemanticHighlightingManager
					.getInstance().getSemanticHighlightings().get(namedStyle);
			boolean enabled = true;
			if (semanticType != null) {
				enabled = getOverlayStore().getBoolean(
						semanticType.getEnabledPreferenceKey());
			} else {
				enabled = getOverlayStore()
						.getBoolean(
								PreferenceConstants
										.getEnabledPreferenceKey(namedStyle));
			}
			fEnabler.setSelection(enabled);
			fEnabler.setEnabled(true);
			fClearStyle.setEnabled(true);
			fBold.setEnabled(enabled);
			fItalic.setEnabled(enabled);
			fStrike.setEnabled(enabled);
			fUnderline.setEnabled(enabled);
			fForegroundLabel.setEnabled(enabled);
			fBackgroundLabel.setEnabled(enabled);
			fForegroundColorEditor.setEnabled(enabled);
			fBackgroundColorEditor.setEnabled(enabled);
			fBold.setSelection((attribute.getStyle() & SWT.BOLD) != 0);
			fItalic.setSelection((attribute.getStyle() & SWT.ITALIC) != 0);
			fStrike.setSelection((attribute.getStyle() & TextAttribute.STRIKETHROUGH) != 0);
			fUnderline
					.setSelection((attribute.getStyle() & TextAttribute.UNDERLINE) != 0);
			if (attribute.getForeground() != null) {
				foreground = attribute.getForeground();
			}
			if (attribute.getBackground() != null) {
				background = attribute.getBackground();
			}
		}

		fForegroundColorEditor.setColorValue(foreground.getRGB());
		fBackgroundColorEditor.setColorValue(background.getRGB());

	}

	/**
	 * Color the text in the sample area according to the current preferences
	 */
	void applyStyles() {
		if (fText == null || fText.isDisposed())
			return;

		fStyleProvider.loadColors();

		IStructuredDocumentRegion documentRegion = fDocument
				.getFirstStructuredDocumentRegion();
		while (documentRegion != null) {
			final Collection<StyleRange> holdResults = new ArrayList<StyleRange>();
			fStyleProvider.prepareTextRegions(documentRegion, 0,
					documentRegion.getEnd(), holdResults);

			for (Iterator<StyleRange> iter = holdResults.iterator(); iter
					.hasNext();) {
				StyleRange element = iter.next();

				fText.setStyleRange(element);
			}

			for (Iterator iterator = SemanticHighlightingManager.getInstance()
					.getSemanticHighlightings().keySet().iterator(); iterator
					.hasNext();) {
				String type = (String) iterator.next();
				ISemanticHighlighting highlighting = SemanticHighlightingManager
						.getInstance().getSemanticHighlightings().get(type);
				HighlightingStyle highlightingStyle = highlightingStyleMap
						.get(type);
				if (highlightingStyle.isEnabled()) {
					Position[] positions = highlightingPositionMap.get(type);
					if (positions != null) {
						for (int i = 0; i < positions.length; i++) {
							Position position = positions[i];
							StyleRange styleRange = createStyleRange(
									highlightingStyle.getTextAttribute(),
									position);
							fText.setStyleRange(styleRange);
						}
					}
				}

			}
			documentRegion = documentRegion.getNext();
		}
	}

	private StyleRange createStyleRange(TextAttribute attr, Position position) {
		StyleRange result = new StyleRange(position.getOffset(),
				position.getLength(), attr.getForeground(),
				attr.getBackground(), attr.getStyle());
		if ((attr.getStyle() & TextAttribute.UNDERLINE) != 0) {
			result.underline = true;
			result.fontStyle &= ~TextAttribute.UNDERLINE;
		}
		if ((attr.getStyle() & TextAttribute.STRIKETHROUGH) != 0) {
			result.strikeout = true;
			result.fontStyle &= ~TextAttribute.STRIKETHROUGH;
		}
		return result;
	}

	Button createCheckbox(Composite parent, String label) {
		Button button = new Button(parent, SWT.CHECK);
		button.setText(label);
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		return button;
	}

	/**
	 * Creates composite control and sets the default layout data.
	 */
	private Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);

		// GridLayout
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		layout.makeColumnsEqualWidth = false;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		// GridData
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		composite.setLayoutData(data);
		return composite;
	}

	@Override
	protected Control createContents(final Composite parent) {
		initializeDialogUnits(parent);

		fDefaultForeground = parent.getDisplay().getSystemColor(
				SWT.COLOR_LIST_FOREGROUND);
		fDefaultBackground = parent.getDisplay().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND);
		Composite pageComponent = createComposite(parent, 2);
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(pageComponent,
						IPHPHelpContextIds.SYNTAX_COLORING_PREFERENCES);

		Link link = new Link(pageComponent, SWT.WRAP);
		link.setText(SSEUIMessages.SyntaxColoring_Link);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(parent.getShell(),
						e.text, null, null);
			}
		});

		GridData linkData = new GridData(SWT.FILL, SWT.BEGINNING, true, false,
				2, 1);
		linkData.widthHint = 150; // only expand further if anyone else requires
		// it
		link.setLayoutData(linkData);

		new Label(pageComponent, SWT.NONE).setLayoutData(new GridData());
		new Label(pageComponent, SWT.NONE).setLayoutData(new GridData());

		SashForm editor = new SashForm(pageComponent, SWT.VERTICAL);
		GridData gridData2 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData2.horizontalSpan = 2;
		editor.setLayoutData(gridData2);
		SashForm top = new SashForm(editor, SWT.HORIZONTAL);
		Composite styleEditor = createComposite(top, 1);
		((GridLayout) styleEditor.getLayout()).marginRight = 5;
		((GridLayout) styleEditor.getLayout()).marginLeft = 0;
		createLabel(styleEditor, XMLUIMessages.SyntaxColoringPage_0);
		fStylesViewer = createStylesViewer(styleEditor);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalIndent = 0;
		Iterator<String> iterator = fStyleToDescriptionMap.values().iterator();
		while (iterator.hasNext()) {
			gridData.widthHint = Math.max(gridData.widthHint,
					convertWidthInCharsToPixels(iterator.next().toString()
							.length()));
		}
		gridData.heightHint = convertHeightInCharsToPixels(5);
		fStylesViewer.getControl().setLayoutData(gridData);

		Composite editingComposite = createComposite(top, 1);
		((GridLayout) styleEditor.getLayout()).marginLeft = 5;
		createLabel(editingComposite, ""); //$NON-NLS-1$

		fEnabler = createCheckbox(editingComposite,
				XMLUIMessages.SyntaxColoringPage_2);
		fEnabler.setEnabled(false);

		Composite editControls = createComposite(editingComposite, 2);
		((GridLayout) editControls.getLayout()).marginLeft = 20;

		fForegroundLabel = createLabel(editControls,
				SSEUIMessages.Foreground_UI_);
		((GridData) fForegroundLabel.getLayoutData()).verticalAlignment = SWT.CENTER;
		fForegroundLabel.setEnabled(false);

		fForegroundColorEditor = new ColorSelector(editControls);
		Button fForegroundColor = fForegroundColorEditor.getButton();
		GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		fForegroundColor.setLayoutData(gd);
		fForegroundColorEditor.setEnabled(false);

		fBackgroundLabel = createLabel(editControls,
				SSEUIMessages.Background_UI_);
		((GridData) fBackgroundLabel.getLayoutData()).verticalAlignment = SWT.CENTER;
		fBackgroundLabel.setEnabled(false);

		fBackgroundColorEditor = new ColorSelector(editControls);
		Button fBackgroundColor = fBackgroundColorEditor.getButton();
		gd = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		fBackgroundColor.setLayoutData(gd);
		fBackgroundColorEditor.setEnabled(false);

		fBold = createCheckbox(editControls, XMLUIMessages.SyntaxColoringPage_3);
		fBold.setEnabled(false);
		((GridData) fBold.getLayoutData()).horizontalSpan = 2;
		fItalic = createCheckbox(editControls,
				XMLUIMessages.SyntaxColoringPage_4);
		fItalic.setEnabled(false);
		((GridData) fItalic.getLayoutData()).horizontalSpan = 2;
		fStrike = createCheckbox(editControls,
				XMLUIMessages.SyntaxColoringPage_5);
		fStrike.setEnabled(false);
		((GridData) fStrike.getLayoutData()).horizontalSpan = 2;
		fUnderline = createCheckbox(editControls,
				XMLUIMessages.SyntaxColoringPage_6);
		fUnderline.setEnabled(false);
		((GridData) fUnderline.getLayoutData()).horizontalSpan = 2;
		fClearStyle = new Button(editingComposite, SWT.PUSH);
		fClearStyle.setText(SSEUIMessages.Restore_Default_UI_); // =
																// "Restore Default"
		fClearStyle.setLayoutData(new GridData(SWT.BEGINNING));
		((GridData) fClearStyle.getLayoutData()).horizontalIndent = 20;
		fClearStyle.setEnabled(false);

		Composite sampleArea = createComposite(editor, 1);

		((GridLayout) sampleArea.getLayout()).marginLeft = 5;
		((GridLayout) sampleArea.getLayout()).marginTop = 5;
		createLabel(sampleArea, SSEUIMessages.Sample_text__UI_); // =
																	// "&Sample text:"
		SourceViewer viewer = new SourceViewer(sampleArea, null, SWT.BORDER
				| SWT.LEFT_TO_RIGHT | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL
				| SWT.READ_ONLY);
		fText = viewer.getTextWidget();
		GridData gridData3 = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData3.widthHint = convertWidthInCharsToPixels(20);
		gridData3.heightHint = convertHeightInCharsToPixels(5);
		gridData3.horizontalSpan = 2;
		fText.setLayoutData(gridData3);
		fText.setEditable(false);
		fText.setFont(JFaceResources.getFont("org.eclipse.wst.sse.ui.textfont")); //$NON-NLS-1$ 
		fText.addKeyListener(getTextKeyListener());
		fText.addSelectionListener(getTextSelectionListener());
		fText.addMouseListener(getTextMouseListener());
		fText.addTraverseListener(getTraverseListener());
		setAccessible(fText, SSEUIMessages.Sample_text__UI_);
		fDocument = StructuredModelManager.getModelManager()
				.createStructuredDocumentFor(
						ContentTypeIdForPHP.ContentTypeID_PHP);
		fDocument.set(getExampleText());
		viewer.setDocument(fDocument);

		top.setWeights(new int[] { 2, 1 });
		editor.setWeights(new int[] { 1, 1 });
		PlatformUI
				.getWorkbench()
				.getHelpSystem()
				.setHelp(pageComponent,
						IPHPHelpContextIds.SYNTAX_COLORING_PREFERENCES);

		fStylesViewer.setInput(getStylePreferenceKeys());

		fOverlayStore.addPropertyChangeListener(fHighlightingChangeListener);
		initHighlightingPositions();
		initHighlightingStyles();
		applyStyles();

		fStylesViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						if (!event.getSelection().isEmpty()) {
							Object o = ((IStructuredSelection) event
									.getSelection()).getFirstElement();
							String namedStyle = o.toString();
							activate(namedStyle);
							if (namedStyle == null)
								return;
						}
					}
				});

		fForegroundColorEditor.addListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(ColorSelector.PROP_COLORCHANGE)) {
					Object o = ((IStructuredSelection) fStylesViewer
							.getSelection()).getFirstElement();
					String namedStyle = o.toString();

					if (SemanticHighlightingManager.getInstance()
							.getSemanticHighlightings().containsKey(namedStyle)) {
						AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
								.getInstance().getSemanticHighlightings()
								.get(namedStyle);
						String oldValue = getOverlayStore().getString(
								semanticHighlighting.getColorPreferenceKey());
						String newValue = ColorHelper
								.toRGBString(fForegroundColorEditor
										.getColorValue());

						if (!newValue.equals(oldValue)) {
							getOverlayStore().setValue(
									semanticHighlighting
											.getColorPreferenceKey(), newValue);
							applyStyles();
							fText.redraw();
						}
						return;
					}

					String prefString = getOverlayStore().getString(namedStyle);
					String[] stylePrefs = ColorHelper
							.unpackStylePreferences(prefString);
					if (stylePrefs != null) {
						String oldValue = stylePrefs[0];
						// open color dialog to get new color
						String newValue = ColorHelper
								.toRGBString(fForegroundColorEditor
										.getColorValue());

						if (!newValue.equals(oldValue)) {
							stylePrefs[0] = newValue;
							String newPrefString = ColorHelper
									.packStylePreferences(stylePrefs);
							getOverlayStore().setValue(namedStyle,
									newPrefString);
							applyStyles();
							fText.redraw();
						}
					}
				}
			}
		});

		fBackgroundColorEditor.addListener(new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(ColorSelector.PROP_COLORCHANGE)) {
					Object o = ((IStructuredSelection) fStylesViewer
							.getSelection()).getFirstElement();
					String namedStyle = o.toString();

					if (SemanticHighlightingManager.getInstance()
							.getSemanticHighlightings().containsKey(namedStyle)) {
						AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
								.getInstance().getSemanticHighlightings()
								.get(namedStyle);
						String oldValue = getOverlayStore().getString(
								semanticHighlighting
										.getBackgroundColorPreferenceKey());
						String newValue = ColorHelper
								.toRGBString(fBackgroundColorEditor
										.getColorValue());

						if (!newValue.equals(oldValue)) {
							getOverlayStore().setValue(
									semanticHighlighting
											.getBackgroundColorPreferenceKey(),
									newValue);
							applyStyles();
							fText.redraw();
						}
						return;
					}

					String prefString = getOverlayStore().getString(namedStyle);
					String[] stylePrefs = ColorHelper
							.unpackStylePreferences(prefString);
					if (stylePrefs != null) {
						String oldValue = stylePrefs[1];
						// open color dialog to get new color
						String newValue = ColorHelper
								.toRGBString(fBackgroundColorEditor
										.getColorValue());

						if (!newValue.equals(oldValue)) {
							stylePrefs[1] = newValue;
							String newPrefString = ColorHelper
									.packStylePreferences(stylePrefs);
							getOverlayStore().setValue(namedStyle,
									newPrefString);
							applyStyles();
							fText.redraw();
							activate(namedStyle);
						}
					}
				}
			}
		});

		fBold.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection())
						.getFirstElement();
				String namedStyle = o.toString();

				if (SemanticHighlightingManager.getInstance()
						.getSemanticHighlightings().containsKey(namedStyle)) {
					AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
							.getInstance().getSemanticHighlightings()
							.get(namedStyle);
					String oldValue = getOverlayStore().getString(
							semanticHighlighting.getBoldPreferenceKey());
					String newValue = String.valueOf(fBold.getSelection());

					if (!newValue.equals(oldValue)) {
						getOverlayStore().setValue(
								semanticHighlighting.getBoldPreferenceKey(),
								newValue);
						applyStyles();
						fText.redraw();
					}
					return;
				}

				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper
						.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[2];
					String newValue = String.valueOf(fBold.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[2] = newValue;
						String newPrefString = ColorHelper
								.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fItalic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection())
						.getFirstElement();
				String namedStyle = o.toString();
				if (SemanticHighlightingManager.getInstance()
						.getSemanticHighlightings().containsKey(namedStyle)) {
					AbstractSemanticHighlighting semanticHighlightingType = SemanticHighlightingManager
							.getInstance().getSemanticHighlightings()
							.get(namedStyle);
					String oldValue = getOverlayStore().getString(
							semanticHighlightingType.getItalicPreferenceKey());
					String newValue = String.valueOf(fItalic.getSelection());

					if (!newValue.equals(oldValue)) {
						getOverlayStore().setValue(
								semanticHighlightingType
										.getItalicPreferenceKey(), newValue);
						applyStyles();
						fText.redraw();
					}
					return;
				}
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper
						.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[3];
					String newValue = String.valueOf(fItalic.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[3] = newValue;
						String newPrefString = ColorHelper
								.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fStrike.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection())
						.getFirstElement();
				String namedStyle = o.toString();
				if (SemanticHighlightingManager.getInstance()
						.getSemanticHighlightings().containsKey(namedStyle)) {
					AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
							.getInstance().getSemanticHighlightings()
							.get(namedStyle);
					String oldValue = getOverlayStore().getString(
							semanticHighlighting
									.getStrikethroughPreferenceKey());
					String newValue = String.valueOf(fStrike.getSelection());

					if (!newValue.equals(oldValue)) {
						getOverlayStore().setValue(
								semanticHighlighting
										.getStrikethroughPreferenceKey(),
								newValue);
						applyStyles();
						fText.redraw();
					}
					return;
				}
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper
						.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[4];
					String newValue = String.valueOf(fStrike.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[4] = newValue;
						String newPrefString = ColorHelper
								.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fUnderline.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection())
						.getFirstElement();
				String namedStyle = o.toString();

				if (SemanticHighlightingManager.getInstance()
						.getSemanticHighlightings().containsKey(namedStyle)) {
					AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
							.getInstance().getSemanticHighlightings()
							.get(namedStyle);
					String oldValue = getOverlayStore().getString(
							semanticHighlighting.getUnderlinePreferenceKey());
					String newValue = String.valueOf(fUnderline.getSelection());

					if (!newValue.equals(oldValue)) {
						getOverlayStore().setValue(
								semanticHighlighting
										.getUnderlinePreferenceKey(), newValue);
						applyStyles();
						fText.redraw();
					}
					return;
				}

				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper
						.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[5];
					String newValue = String.valueOf(fUnderline.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[5] = newValue;
						String newPrefString = ColorHelper
								.packStylePreferences(stylePrefs);
						getOverlayStore().setValue(namedStyle, newPrefString);
						applyStyles();
						fText.redraw();
					}
				}
			}
		});

		fClearStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fStylesViewer.getSelection().isEmpty())
					return;
				String namedStyle = ((IStructuredSelection) fStylesViewer
						.getSelection()).getFirstElement().toString();
				if (SemanticHighlightingManager.getInstance()
						.getSemanticHighlightings().containsKey(namedStyle)) {
					AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
							.getInstance().getSemanticHighlightings()
							.get(namedStyle);
					getOverlayStore().setToDefault(
							semanticHighlighting.getBoldPreferenceKey());
					getOverlayStore().setToDefault(
							semanticHighlighting.getColorPreferenceKey());
					getOverlayStore().setToDefault(
							semanticHighlighting
									.getBackgroundColorPreferenceKey());
					getOverlayStore().setToDefault(
							semanticHighlighting.getEnabledPreferenceKey());
					getOverlayStore().setToDefault(
							semanticHighlighting.getItalicPreferenceKey());
					getOverlayStore().setToDefault(
							semanticHighlighting
									.getStrikethroughPreferenceKey());
					getOverlayStore().setToDefault(
							semanticHighlighting.getUnderlinePreferenceKey());
					boolean enablement = getOverlayStore().getDefaultBoolean(
							semanticHighlighting.getEnabledPreferenceKey());
					switchEnablement(enablement);
				} else {
					getOverlayStore().setToDefault(namedStyle);
				}
				applyStyles();
				fText.redraw();
				activate(namedStyle);
			}
		});

		fEnabler.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				// get current (newly old) style
				Object o = ((IStructuredSelection) fStylesViewer.getSelection())
						.getFirstElement();
				String namedStyle = o.toString();

				Map<String, AbstractSemanticHighlighting> highlightingMap = SemanticHighlightingManager
						.getInstance().getSemanticHighlightings();
				if (highlightingMap.containsKey(namedStyle)) {
					AbstractSemanticHighlighting semantic = highlightingMap
							.get(namedStyle);
					boolean enablement = fEnabler.getSelection();
					semantic.getStyle().setEnabledByDefault(enablement);
					switchEnablement(enablement);
					getOverlayStore().setValue(
							semantic.getEnabledPreferenceKey(), enablement);
					applyStyles();
					fText.redraw();

				} else if (getStylePreferenceKeys().contains(namedStyle)) {
					boolean enablement = fEnabler.getSelection();
					switchEnablement(enablement);
					getOverlayStore().setValue(
							PreferenceConstants
									.getEnabledPreferenceKey(namedStyle),
							enablement);
					applyStyles();
					fText.redraw();
				}
			}

		});

		switchEnablement(false);
		return pageComponent;
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.WRAP);
		label.setText(text);
		GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
		label.setLayoutData(data);
		label.setBackground(parent.getBackground());
		return label;
	}

	// protected Label createDescriptionLabel(Composite parent) {
	// return null;
	// }

	/**
	 * Set up all the style preference keys in the overlay store
	 */
	private OverlayKey[] createOverlayStoreKeys() {
		List<OverlayKey> overlayKeys = new ArrayList<OverlayKey>();

		Iterator<String> i = getStylePreferenceKeys().iterator();
		while (i.hasNext()) {
			String key = i.next();
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.STRING, key));
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.BOOLEAN, PreferenceConstants
							.getEnabledPreferenceKey(key)));
		}

		for (AbstractSemanticHighlighting rule : SemanticHighlightingManager
				.getInstance().getSemanticHighlightings().values()) {
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.STRING, rule
							.getEnabledPreferenceKey()));
			overlayKeys
					.add(new OverlayPreferenceStore.OverlayKey(
							OverlayPreferenceStore.STRING, rule
									.getColorPreferenceKey()));

			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.STRING, rule
							.getBackgroundColorPreferenceKey()));
			overlayKeys
					.add(new OverlayPreferenceStore.OverlayKey(
							OverlayPreferenceStore.STRING, rule
									.getBoldPreferenceKey()));
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.STRING, rule
							.getItalicPreferenceKey()));
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.STRING, rule
							.getStrikethroughPreferenceKey()));
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
					OverlayPreferenceStore.STRING, rule
							.getUnderlinePreferenceKey()));
		}

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys
				.size()];

		overlayKeys.toArray(keys);
		return keys;
	}

	/**
	 * Creates the List viewer where we see the various syntax element display
	 * names--would it ever be a Tree like JDT's?
	 * 
	 * @param parent
	 * @return
	 */
	private StructuredViewer createStylesViewer(Composite parent) {
		StructuredViewer stylesViewer = new ListViewer(parent, SWT.SINGLE
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		stylesViewer
				.setComparator(new ViewerComparator(Collator.getInstance()));
		stylesViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Object description = fStyleToDescriptionMap.get(element);
				if (description != null)
					return description.toString();
				else if (SemanticHighlightingManager.getInstance()
						.getSemanticHighlightings().containsKey(element)) {
					AbstractSemanticHighlighting semanticHighlighting = SemanticHighlightingManager
							.getInstance().getSemanticHighlightings()
							.get(element);
					return semanticHighlighting.getDisplayName();
				}
				return super.getText(element);
			}
		});
		stylesViewer.setContentProvider(new ITreeContentProvider() {
			public void dispose() {
			}

			public Object[] getChildren(Object parentElement) {
				return getStylePreferenceKeys().toArray();
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			public Object getParent(Object element) {
				return getStylePreferenceKeys();
			}

			public boolean hasChildren(Object element) {
				return false;
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
		return stylesViewer;
	}

	@Override
	public void dispose() {
		if (fOverlayStore != null) {
			fOverlayStore
					.removePropertyChangeListener(fHighlightingChangeListener);
			fOverlayStore.stop();
		}
		super.dispose();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	private TextAttribute getAttributeFor(String namedStyle) {
		TextAttribute ta = new TextAttribute(fDefaultForeground,
				fDefaultBackground, SWT.NORMAL);

		if (namedStyle != null && fOverlayStore != null) {

			if (SemanticHighlightingManager.getInstance()
					.getSemanticHighlightings().containsKey(namedStyle)) {
				AbstractSemanticHighlighting semanticHighlightingType = SemanticHighlightingManager
						.getInstance().getSemanticHighlightings()
						.get(namedStyle);

				int fontModifier = SWT.NORMAL;
				boolean on = getOverlayStore().getBoolean(
						semanticHighlightingType.getBoldPreferenceKey());
				if (on)
					fontModifier = fontModifier | SWT.BOLD;

				on = getOverlayStore().getBoolean(
						semanticHighlightingType.getItalicPreferenceKey());
				if (on)
					fontModifier = fontModifier | SWT.ITALIC;

				on = getOverlayStore().getBoolean(
						semanticHighlightingType
								.getStrikethroughPreferenceKey());
				if (on)
					fontModifier = fontModifier | TextAttribute.STRIKETHROUGH;

				on = getOverlayStore().getBoolean(
						semanticHighlightingType.getUnderlinePreferenceKey());
				if (on)
					fontModifier = fontModifier | TextAttribute.UNDERLINE;

				String color = getOverlayStore().getString(
						semanticHighlightingType.getColorPreferenceKey());
				RGB foreground = ColorHelper.toRGB(color);
				String bgcolor = getOverlayStore().getString(
						semanticHighlightingType
								.getBackgroundColorPreferenceKey());
				RGB bgforeground = ColorHelper.toRGB(bgcolor);
				ta = new TextAttribute(
						(foreground != null) ? EditorUtility.getColor(foreground)
								: null,
						(bgforeground != null) ? EditorUtility
								.getColor(bgforeground) : null, fontModifier);
				return ta;
			}

			// note: "namedStyle" *is* the preference key
			String prefString = getOverlayStore().getString(namedStyle);
			String[] stylePrefs = ColorHelper
					.unpackStylePreferences(prefString);
			if (stylePrefs != null) {
				RGB foreground = ColorHelper.toRGB(stylePrefs[0]);
				RGB background = ColorHelper.toRGB(stylePrefs[1]);

				int fontModifier = SWT.NORMAL;

				if (stylePrefs.length > 2) {
					boolean on = Boolean.valueOf(stylePrefs[2]).booleanValue();
					if (on)
						fontModifier = fontModifier | SWT.BOLD;
				}
				if (stylePrefs.length > 3) {
					boolean on = Boolean.valueOf(stylePrefs[3]).booleanValue();
					if (on)
						fontModifier = fontModifier | SWT.ITALIC;
				}
				if (stylePrefs.length > 4) {
					boolean on = Boolean.valueOf(stylePrefs[4]).booleanValue();
					if (on)
						fontModifier = fontModifier
								| TextAttribute.STRIKETHROUGH;
				}
				if (stylePrefs.length > 5) {
					boolean on = Boolean.valueOf(stylePrefs[5]).booleanValue();
					if (on)
						fontModifier = fontModifier | TextAttribute.UNDERLINE;
				}

				ta = new TextAttribute(
						(foreground != null) ? EditorUtility.getColor(foreground)
								: null,
						(background != null) ? EditorUtility
								.getColor(background) : null, fontModifier);
			}
		}
		return ta;
	}

	private String getExampleText() {
		return PHPUIMessages.ColorPage_CodeExample_0;
	}

	private String getNamedStyleAtOffset(int offset) {
		// ensure the offset is clean
		if (offset >= fDocument.getLength())
			return getNamedStyleAtOffset(fDocument.getLength() - 1);
		else if (offset < 0)
			return getNamedStyleAtOffset(0);

		if (highlightingPositionMap == null) {
			initHighlightingPositions();
		}

		for (Iterator iterator = highlightingPositionMap.keySet().iterator(); iterator
				.hasNext();) {
			String type = (String) iterator.next();
			Position[] positions = highlightingPositionMap.get(type);
			for (int i = 0; i < positions.length; i++) {
				if (offset >= positions[i].offset
						&& offset < positions[i].offset + positions[i].length) {
					return type;
				}
			}
		}

		IStructuredDocumentRegion documentRegion = fDocument
				.getFirstStructuredDocumentRegion();
		while (documentRegion != null && !documentRegion.containsOffset(offset)) {
			documentRegion = documentRegion.getNext();
		}

		if (documentRegion != null) {
			String regionContext;
			ITextRegion interest = documentRegion
					.getRegionAtCharacterOffset(offset);

			ITextRegionCollection container = documentRegion;
			if (interest instanceof ITextRegionContainer) {
				container = (ITextRegionContainer) interest;
				interest = container.getRegionAtCharacterOffset(offset);
			}

			if (interest.getType() == PHPRegionContext.PHP_CONTENT) {
				IPhpScriptRegion phpScript = (IPhpScriptRegion) interest;
				try {
					regionContext = phpScript
							.getPhpTokenType(offset
									- container.getStartOffset()
									- phpScript.getStart());
				} catch (BadLocationException e) {
					assert false;
					return null;
				}
			} else if (interest.getType() == PHPRegionContext.PHP_OPEN) {
				regionContext = PHPRegionTypes.PHP_OPENTAG;
			} else if (interest.getType() == PHPRegionContext.PHP_CLOSE) {
				regionContext = PHPRegionTypes.PHP_CLOSETAG;
			} else {
				regionContext = interest.getType();
			}

			// find the named style (internal/selectable name) for that
			// context
			String namedStyle = fContextToStyleMap.get(regionContext);
			return namedStyle;
		}
		return null;
	}

	protected void initHighlightingPositions() {
		highlightingPositionMap = new HashMap<String, Position[]>();
		IPath stateLocation = PHPUiPlugin.getDefault().getStateLocation();
		IPath path = stateLocation.append("/_" + "PHPSyntax"); //$NON-NLS-1$ //$NON-NLS-2$ 
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(path);

		NonExistingPHPFileEditorInput input = new NonExistingPHPFileEditorInput(
				fileStore, "PHPSyntax"); //$NON-NLS-1$

		File realFile = ((NonExistingPHPFileEditorInput) input).getPath(input)
				.toFile();

		try {
			FileOutputStream fos = new FileOutputStream(realFile);
			fos.write(fDocument.get().getBytes());
			fos.close();
			DLTKUIPlugin.getDocumentProvider().connect(input);
			final ISourceModule sourceModule = DLTKUIPlugin
					.getDocumentProvider().getWorkingCopy(input);
			if (sourceModule != null) {
				ASTParser parser = ASTParser.newParser(PHPVersion.PHP5_4,
						sourceModule);
				parser.setSource(fDocument.get().toCharArray());

				final Program program = parser.createAST(null);
				List<AbstractSemanticHighlighting> highlightings = new ArrayList<AbstractSemanticHighlighting>();

				highlightings.add(new StaticFieldHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return StaticFieldHighlighting.class.getName();
					}
				});
				highlightings.add(new StaticMethodHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return StaticMethodHighlighting.class.getName();
					}
				});
				highlightings.add(new ConstantHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return ConstantHighlighting.class.getName();
					}
				});
				highlightings.add(new FieldHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return FieldHighlighting.class.getName();
					}
				});
				highlightings.add(new FunctionHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return FunctionHighlighting.class.getName();
					}
				});
				highlightings.add(new MethodHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return MethodHighlighting.class.getName();
					}
				});
				highlightings.add(new ClassHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return ClassHighlighting.class.getName();
					}
				});
				highlightings.add(new InternalClassHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return InternalClassHighlighting.class.getName();
					}
				});
				highlightings.add(new InternalFunctionHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return InternalFunctionHighlighting.class.getName();
					}
				});
				highlightings.add(new ParameterVariableHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return ParameterVariableHighlighting.class.getName();
					}
				});
				highlightings.add(new SuperGlobalHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return SuperGlobalHighlighting.class.getName();
					}
				});
				highlightings.add(new InternalConstantHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return InternalConstantHighlighting.class.getName();
					}
				});
				highlightings.add(new DeprecatedHighlighting() {
					@Override
					protected Program getProgram(
							IStructuredDocumentRegion region) {
						return program;
					}

					@Override
					public ISourceModule getSourceModule() {
						return sourceModule;
					}

					@Override
					public String getPreferenceKey() {
						return DeprecatedHighlighting.class.getName();
					}
				});

				Collections.sort(highlightings);

				for (Iterator iterator = highlightings.iterator(); iterator
						.hasNext();) {
					AbstractSemanticHighlighting abstractSemanticHighlighting = (AbstractSemanticHighlighting) iterator
							.next();
					Position[] positions = abstractSemanticHighlighting
							.consumes(program);

					if (positions != null && positions.length > 0) {
						highlightingPositionMap
								.put(abstractSemanticHighlighting
										.getPreferenceKey(), positions);

					}
				}
			}
			DLTKUIPlugin.getDocumentProvider().disconnect(input);
		} catch (CoreException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		realFile.delete();
	}

	private OverlayPreferenceStore getOverlayStore() {
		return fOverlayStore;
	}

	private Collection<String> getStylePreferenceKeys() {
		if (fStylePreferenceKeys == null) {
			List<String> styles = new ArrayList<String>();
			styles.add(PreferenceConstants.EDITOR_NORMAL_COLOR);
			styles.add(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
			styles.add(PreferenceConstants.EDITOR_KEYWORD_COLOR);
			styles.add(PreferenceConstants.EDITOR_VARIABLE_COLOR);
			styles.add(PreferenceConstants.EDITOR_STRING_COLOR);
			styles.add(PreferenceConstants.EDITOR_NUMBER_COLOR);
			styles.add(PreferenceConstants.EDITOR_HEREDOC_COLOR);
			styles.add(PreferenceConstants.EDITOR_COMMENT_COLOR);
			styles.add(PreferenceConstants.EDITOR_LINE_COMMENT_COLOR);
			styles.add(PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR);
			styles.add(PreferenceConstants.EDITOR_PHPDOC_COLOR);
			styles.add(PreferenceConstants.EDITOR_TASK_COLOR);

			styles.addAll(SemanticHighlightingManager.getInstance()
					.getSemanticHighlightings().keySet());

			fStylePreferenceKeys = styles;
		}
		return fStylePreferenceKeys;
	}

	private KeyListener getTextKeyListener() {
		return new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.widget instanceof StyledText) {
					int x = ((StyledText) e.widget).getCaretOffset();
					selectColorAtOffset(x);
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.widget instanceof StyledText) {
					int x = ((StyledText) e.widget).getCaretOffset();
					selectColorAtOffset(x);
				}
			}
		};
	}

	private MouseListener getTextMouseListener() {
		return new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
				if (e.widget instanceof StyledText) {
					int x = ((StyledText) e.widget).getCaretOffset();
					selectColorAtOffset(x);
				}
			}
		};
	}

	private SelectionListener getTextSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				selectColorAtOffset(e.x);
				if (e.widget instanceof StyledText) {
					((StyledText) e.widget).setSelection(e.x);
				}
			}

			public void widgetSelected(SelectionEvent e) {
				selectColorAtOffset(e.x);
				if (e.widget instanceof StyledText) {
					((StyledText) e.widget).setSelection(e.x);
				}
			}
		};
	}

	private TraverseListener getTraverseListener() {
		return new TraverseListener() {
			/**
			 * @see org.eclipse.swt.events.TraverseListener#keyTraversed(TraverseEvent)
			 */
			public void keyTraversed(TraverseEvent e) {
				if (e.widget instanceof StyledText) {
					if ((e.detail == SWT.TRAVERSE_TAB_NEXT)
							|| (e.detail == SWT.TRAVERSE_TAB_PREVIOUS))
						e.doit = true;
				}
			}
		};
	}

	public void init(IWorkbench workbench) {
		setDescription(SSEUIMessages.SyntaxColoring_Description);

		fStyleToDescriptionMap = new HashMap<String, String>();
		fContextToStyleMap = new HashMap<String, String>();

		initStyleToDescriptionMap();
		initRegionContextToStyleMap();

		fOverlayStore = new OverlayPreferenceStore(getPreferenceStore(),
				createOverlayStoreKeys());
		fOverlayStore.load();
		fOverlayStore.start();

		fStyleProvider.setColorPreferences(fOverlayStore);
	}

	private void initRegionContextToStyleMap() {
		fContextToStyleMap = fStyleProvider.getColorTypesMap();
	}

	private void initStyleToDescriptionMap() {
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_NORMAL_COLOR,
				PHPUIMessages.ColorPage_Normal);
		fStyleToDescriptionMap.put(
				PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR,
				PHPUIMessages.ColorPage_BoundryMaker);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_KEYWORD_COLOR,
				PHPUIMessages.ColorPage_Keyword);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_VARIABLE_COLOR,
				PHPUIMessages.ColorPage_Variable);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_STRING_COLOR,
				PHPUIMessages.ColorPage_String);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_NUMBER_COLOR,
				PHPUIMessages.ColorPage_Number);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_HEREDOC_COLOR,
				PHPUIMessages.ColorPage_Heredoc);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_COMMENT_COLOR,
				PHPUIMessages.ColorPage_Comment);
		fStyleToDescriptionMap.put(
				PreferenceConstants.EDITOR_LINE_COMMENT_COLOR,
				PHPUIMessages.ColorPage_LineComment);
		fStyleToDescriptionMap.put(
				PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR,
				PHPUIMessages.ColorPage_PHPDOCComment);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_PHPDOC_COLOR,
				PHPUIMessages.ColorPage_Phpdoc);
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_TASK_COLOR,
				PHPUIMessages.ColorPage_TaskTag);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		getOverlayStore().loadDefaults();
		applyStyles();
		fStylesViewer.setSelection(StructuredSelection.EMPTY);
		activate(null);
		fText.redraw();
	}

	@Override
	public boolean performOk() {
		getOverlayStore().propagate();
		PHPUiPlugin.getDefault().savePluginPreferences();
		return true;
	}

	private void selectColorAtOffset(int offset) {
		String namedStyle = getNamedStyleAtOffset(offset);
		if (namedStyle != null) {
			fStylesViewer.setSelection(new StructuredSelection(namedStyle));
			fStylesViewer.reveal(namedStyle);
		} else {
			fStylesViewer.setSelection(StructuredSelection.EMPTY);
		}
		activate(namedStyle);
	}

	/**
	 * Specifically set the reporting name of a control for accessibility
	 */
	private void setAccessible(Control control, String name) {
		if (control == null)
			return;
		final String n = name;
		control.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				if (e.childID == ACC.CHILDID_SELF)
					e.result = n;
			}
		});
	}

	private void switchEnablement(boolean b) {
		fBold.setEnabled(b);
		fItalic.setEnabled(b);
		fUnderline.setEnabled(b);
		fStrike.setEnabled(b);
		fBackgroundColorEditor.setEnabled(b);
		fForegroundColorEditor.setEnabled(b);
	}

	private void initHighlightingStyles() {
		highlightingStyleMap = new HashMap<String, PHPSyntaxColoringPage.HighlightingStyle>();
		for (Iterator iterator = SemanticHighlightingManager.getInstance()
				.getSemanticHighlightings().keySet().iterator(); iterator
				.hasNext();) {
			String type = (String) iterator.next();
			ISemanticHighlighting highlighting = SemanticHighlightingManager
					.getInstance().getSemanticHighlightings().get(type);
			highlightingStyleMap.put(type,
					createHighlightingStyle(highlighting));
		}
	}

	/**
	 * Creates a highlighting style based on the preferences defined in the
	 * semantic highlighting
	 * 
	 * @param highlighting
	 *            the semantic highlighting
	 * @return a highlighting style based on the preferences of the semantic
	 *         highlighting
	 */
	private HighlightingStyle createHighlightingStyle(
			ISemanticHighlighting highlighting) {
		IPreferenceStore store = highlighting.getPreferenceStore();
		HighlightingStyle highlightingStyle = null;
		if (store != null) {
			TextAttribute attribute = null;
			int style = getBoolean(store, highlighting.getBoldPreferenceKey()) ? SWT.BOLD
					: SWT.NORMAL;

			if (getBoolean(store, highlighting.getItalicPreferenceKey()))
				style |= SWT.ITALIC;
			if (getBoolean(store, highlighting.getStrikethroughPreferenceKey()))
				style |= TextAttribute.STRIKETHROUGH;
			if (getBoolean(store, highlighting.getUnderlinePreferenceKey()))
				style |= TextAttribute.UNDERLINE;

			String rgbString = getString(store,
					highlighting.getColorPreferenceKey());
			Color color = null;

			if (rgbString != null)
				color = EditorUtility.getColor(ColorHelper.toRGB(rgbString));

			String bgrgbString = null;
			if (highlighting instanceof ISemanticHighlightingExtension2)
				bgrgbString = getString(store,
						((ISemanticHighlightingExtension2) highlighting)
								.getBackgroundColorPreferenceKey());
			Color bgcolor = null;

			if (bgrgbString != null)
				bgcolor = EditorUtility
						.getColor(ColorHelper.toRGB(bgrgbString));

			attribute = new TextAttribute(color, bgcolor, style);

			boolean isEnabled = getBoolean(store,
					highlighting.getEnabledPreferenceKey());
			highlightingStyle = new HighlightingStyle(attribute, isEnabled);
		}
		return highlightingStyle;
	}

	/**
	 * Looks up a boolean preference by <code>key</code> from the preference
	 * store
	 * 
	 * @param store
	 *            the preference store to lookup the preference from
	 * @param key
	 *            the key the preference is stored under
	 * @return the preference value from the preference store iff key is not
	 *         null
	 */
	private boolean getBoolean(IPreferenceStore store, String key) {
		return (key == null) ? false : store.getBoolean(key);
	}

	/**
	 * Looks up a String preference by <code>key</code> from the preference
	 * store
	 * 
	 * @param store
	 *            the preference store to lookup the preference from
	 * @param key
	 *            the key the preference is stored under
	 * @return the preference value from the preference store iff key is not
	 *         null
	 */
	private String getString(IPreferenceStore store, String key) {
		return (key == null) ? null : store.getString(key);
	}

	/**
	 * HighlightingStyle.
	 */
	static class HighlightingStyle {

		/** Text attribute */
		private TextAttribute fTextAttribute;
		/** Enabled state */
		private boolean fIsEnabled;

		/**
		 * Initialize with the given text attribute.
		 * 
		 * @param textAttribute
		 *            The text attribute
		 * @param isEnabled
		 *            the enabled state
		 */
		public HighlightingStyle(TextAttribute textAttribute, boolean isEnabled) {
			setTextAttribute(textAttribute);
			setEnabled(isEnabled);
		}

		/**
		 * @return Returns the text attribute.
		 */
		public TextAttribute getTextAttribute() {
			return fTextAttribute;
		}

		/**
		 * @param textAttribute
		 *            The background to set.
		 */
		public void setTextAttribute(TextAttribute textAttribute) {
			fTextAttribute = textAttribute;
		}

		/**
		 * @return the enabled state
		 */
		public boolean isEnabled() {
			return fIsEnabled;
		}

		/**
		 * @param isEnabled
		 *            the new enabled state
		 */
		public void setEnabled(boolean isEnabled) {
			fIsEnabled = isEnabled;
		}
	}

	private IPropertyChangeListener fHighlightingChangeListener = new IPropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent event) {
			handleHighlightingPropertyChange(event);
		}
	};

	/**
	 * Handles property change events for individual semantic highlightings.
	 * 
	 * @param event
	 */
	private void handleHighlightingPropertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if (property == null)
			return;
		for (Iterator iterator = SemanticHighlightingManager.getInstance()
				.getSemanticHighlightings().keySet().iterator(); iterator
				.hasNext();) {
			String type = (String) iterator.next();
			ISemanticHighlighting highlighting = SemanticHighlightingManager
					.getInstance().getSemanticHighlightings().get(type);
			HighlightingStyle style = highlightingStyleMap.get(type);
			if (property.equals(highlighting.getBoldPreferenceKey())) {
				adaptToTextStyleChange(style, event, SWT.BOLD);
			} else if (property.equals(highlighting.getColorPreferenceKey())) {
				adaptToTextForegroundChange(style, event);
			} else if ((highlighting instanceof ISemanticHighlightingExtension2)
					&& property
							.equals(((ISemanticHighlightingExtension2) highlighting)
									.getBackgroundColorPreferenceKey())) {
				adaptToTextBackgroundChange(style, event);
			} else if (property.equals(highlighting.getEnabledPreferenceKey())) {
				adaptToEnablementChange(style, event);
			} else if (property.equals(highlighting.getItalicPreferenceKey())) {
				adaptToTextStyleChange(style, event, SWT.ITALIC);
			} else if (property.equals(highlighting
					.getStrikethroughPreferenceKey())) {
				adaptToTextStyleChange(style, event,
						TextAttribute.STRIKETHROUGH);
			} else if (property
					.equals(highlighting.getUnderlinePreferenceKey())) {
				adaptToTextStyleChange(style, event, TextAttribute.UNDERLINE);
			}
		}
	}

	private void adaptToEnablementChange(HighlightingStyle highlighting,
			PropertyChangeEvent event) {
		Object value = event.getNewValue();
		boolean eventValue;
		if (value instanceof Boolean)
			eventValue = ((Boolean) value).booleanValue();
		else if (IPreferenceStore.TRUE.equals(value))
			eventValue = true;
		else
			eventValue = false;
		highlighting.setEnabled(eventValue);
	}

	private void adaptToTextForegroundChange(HighlightingStyle highlighting,
			PropertyChangeEvent event) {
		RGB rgb = null;

		Object value = event.getNewValue();
		if (value instanceof RGB)
			rgb = (RGB) value;
		else if (value instanceof String)
			rgb = ColorHelper.toRGB((String) value);

		if (rgb != null) {
			Color color = EditorUtility.getColor(rgb);
			TextAttribute oldAttr = highlighting.getTextAttribute();
			highlighting.setTextAttribute(new TextAttribute(color, oldAttr
					.getBackground(), oldAttr.getStyle()));
		}
	}

	private void adaptToTextBackgroundChange(HighlightingStyle highlighting,
			PropertyChangeEvent event) {
		RGB rgb = null;

		Object value = event.getNewValue();
		if (value instanceof RGB)
			rgb = (RGB) value;
		else if (value instanceof String)
			rgb = ColorHelper.toRGB((String) value);

		if (rgb != null) {
			Color color = EditorUtility.getColor(rgb);
			TextAttribute oldAttr = highlighting.getTextAttribute();
			highlighting.setTextAttribute(new TextAttribute(oldAttr
					.getForeground(), color, oldAttr.getStyle()));
		}
	}

	private void adaptToTextStyleChange(HighlightingStyle highlighting,
			PropertyChangeEvent event, int styleAttribute) {
		boolean eventValue = false;
		Object value = event.getNewValue();
		if (value instanceof Boolean)
			eventValue = ((Boolean) value).booleanValue();
		else if (IPreferenceStore.TRUE.equals(value))
			eventValue = true;

		TextAttribute oldAttr = highlighting.getTextAttribute();
		boolean activeValue = (oldAttr.getStyle() & styleAttribute) == styleAttribute;

		if (activeValue != eventValue)
			highlighting.setTextAttribute(new TextAttribute(oldAttr
					.getForeground(), oldAttr.getBackground(),
					eventValue ? oldAttr.getStyle() | styleAttribute : oldAttr
							.getStyle() & ~styleAttribute));
	}

}
