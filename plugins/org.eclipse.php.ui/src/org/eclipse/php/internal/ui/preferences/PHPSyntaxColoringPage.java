package org.eclipse.php.internal.ui.preferences;

import java.util.*;
import java.util.List;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
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
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore;
import org.eclipse.wst.sse.ui.internal.preferences.OverlayPreferenceStore.OverlayKey;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;
import org.eclipse.wst.xml.ui.internal.XMLUIMessages;

import com.ibm.icu.text.Collator;

/**
 * A preference page to configure our XML syntax color. It resembles the JDT
 * and CDT pages far more than our original color page while retaining the
 * extra "click-to-find" functionality.
 */
public final class PHPSyntaxColoringPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button fBold;
	private Label fForegroundLabel;
	private Label fBackgroundLabel;
	private Button fClearStyle;
	private Map fContextToStyleMap;
	private Color fDefaultForeground = null;
	private Color fDefaultBackground = null;
	private IStructuredDocument fDocument;
	private ColorSelector fForegroundColorEditor;
	private ColorSelector fBackgroundColorEditor;
	private Button fItalic;
	private OverlayPreferenceStore fOverlayStore;
	private Button fStrike;
	private Collection fStylePreferenceKeys;
	private StructuredViewer fStylesViewer = null;
	private Map fStyleToDescriptionMap;
	private StyledText fText;
	private Button fUnderline;
	private final LineStyleProviderForPhp fStyleProvider;

	public PHPSyntaxColoringPage() {
		fStyleProvider = new LineStyleProviderForPhp();
	}

	// activate controls based on the given local color type
	private void activate(String namedStyle) {
		Color foreground = fDefaultForeground;
		Color background = fDefaultBackground;
		if (namedStyle == null) {
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
			fClearStyle.setEnabled(true);
			fBold.setEnabled(true);
			fItalic.setEnabled(true);
			fStrike.setEnabled(true);
			fUnderline.setEnabled(true);
			fForegroundLabel.setEnabled(true);
			fBackgroundLabel.setEnabled(true);
			fForegroundColorEditor.setEnabled(true);
			fBackgroundColorEditor.setEnabled(true);
			fBold.setSelection((attribute.getStyle() & SWT.BOLD) != 0);
			fItalic.setSelection((attribute.getStyle() & SWT.ITALIC) != 0);
			fStrike.setSelection((attribute.getStyle() & TextAttribute.STRIKETHROUGH) != 0);
			fUnderline.setSelection((attribute.getStyle() & TextAttribute.UNDERLINE) != 0);
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

		IStructuredDocumentRegion documentRegion = fDocument.getFirstStructuredDocumentRegion();
		while (documentRegion != null) {
			final Collection holdResults = new ArrayList();
			fStyleProvider.prepareTextRegions(documentRegion, 0, documentRegion.getEnd(), holdResults);

			for (Iterator iter = holdResults.iterator(); iter.hasNext();) {
				StyleRange element = (StyleRange) iter.next();
				fText.setStyleRange(element);
			}
			documentRegion = documentRegion.getNext();
		}
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

		fDefaultForeground = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
		fDefaultBackground = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		Composite pageComponent = createComposite(parent, 2);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(pageComponent, IPHPHelpContextIds.PHP_SYNTAX_COLORING_PAGE);

		Link link = new Link(pageComponent, SWT.WRAP);
		link.setText(SSEUIMessages.SyntaxColoring_Link);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(parent.getShell(), e.text, null, null);
			}
		});

		GridData linkData = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1);
		linkData.widthHint = 150; // only expand further if anyone else requires it
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
		Iterator iterator = fStyleToDescriptionMap.values().iterator();
		while (iterator.hasNext()) {
			gridData.widthHint = Math.max(gridData.widthHint, convertWidthInCharsToPixels(iterator.next().toString().length()));
		}
		gridData.heightHint = convertHeightInCharsToPixels(5);
		fStylesViewer.getControl().setLayoutData(gridData);

		Composite editingComposite = createComposite(top, 1);
		((GridLayout) styleEditor.getLayout()).marginLeft = 5;
		createLabel(editingComposite, ""); //$NON-NLS-1$
		Button enabler = createCheckbox(editingComposite, XMLUIMessages.SyntaxColoringPage_2);
		enabler.setEnabled(false);
		enabler.setSelection(true);
		Composite editControls = createComposite(editingComposite, 2);
		((GridLayout) editControls.getLayout()).marginLeft = 20;

		fForegroundLabel = createLabel(editControls, SSEUIMessages.Foreground_UI_);
		((GridData) fForegroundLabel.getLayoutData()).verticalAlignment = SWT.CENTER;
		fForegroundLabel.setEnabled(false);

		fForegroundColorEditor = new ColorSelector(editControls);
		Button fForegroundColor = fForegroundColorEditor.getButton();
		GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		fForegroundColor.setLayoutData(gd);
		fForegroundColorEditor.setEnabled(false);

		fBackgroundLabel = createLabel(editControls, SSEUIMessages.Background_UI_);
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
		fItalic = createCheckbox(editControls, XMLUIMessages.SyntaxColoringPage_4);
		fItalic.setEnabled(false);
		((GridData) fItalic.getLayoutData()).horizontalSpan = 2;
		fStrike = createCheckbox(editControls, XMLUIMessages.SyntaxColoringPage_5);
		fStrike.setEnabled(false);
		((GridData) fStrike.getLayoutData()).horizontalSpan = 2;
		fUnderline = createCheckbox(editControls, XMLUIMessages.SyntaxColoringPage_6);
		fUnderline.setEnabled(false);
		((GridData) fUnderline.getLayoutData()).horizontalSpan = 2;
		fClearStyle = new Button(editingComposite, SWT.PUSH);
		fClearStyle.setText(SSEUIMessages.Restore_Default_UI_); //$NON-NLS-1$ = "Restore Default"
		fClearStyle.setLayoutData(new GridData(SWT.BEGINNING));
		((GridData) fClearStyle.getLayoutData()).horizontalIndent = 20;
		fClearStyle.setEnabled(false);

		Composite sampleArea = createComposite(editor, 1);

		((GridLayout) sampleArea.getLayout()).marginLeft = 5;
		((GridLayout) sampleArea.getLayout()).marginTop = 5;
		createLabel(sampleArea, SSEUIMessages.Sample_text__UI_); //$NON-NLS-1$ = "&Sample text:"
		SourceViewer viewer = new SourceViewer(sampleArea, null, SWT.BORDER | SWT.LEFT_TO_RIGHT | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
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
		fDocument = StructuredModelManager.getModelManager().createStructuredDocumentFor(ContentTypeIdForPHP.ContentTypeID_PHP);
		fDocument.set(getExampleText());
		viewer.setDocument(fDocument);

		top.setWeights(new int[] { 2, 1 });
		editor.setWeights(new int[] { 1, 1 });
		PlatformUI.getWorkbench().getHelpSystem().setHelp(pageComponent, IPHPHelpContextIds.PHP_SYNTAX_COLORING_PAGE);

		fStylesViewer.setInput(getStylePreferenceKeys());

		applyStyles();

		fStylesViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					Object o = ((IStructuredSelection) event.getSelection()).getFirstElement();
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
					Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
					String namedStyle = o.toString();
					String prefString = getOverlayStore().getString(namedStyle);
					String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
					if (stylePrefs != null) {
						String oldValue = stylePrefs[0];
						// open color dialog to get new color
						String newValue = ColorHelper.toRGBString(fForegroundColorEditor.getColorValue());

						if (!newValue.equals(oldValue)) {
							stylePrefs[0] = newValue;
							String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
							getOverlayStore().setValue(namedStyle, newPrefString);
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
					Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
					String namedStyle = o.toString();
					String prefString = getOverlayStore().getString(namedStyle);
					String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
					if (stylePrefs != null) {
						String oldValue = stylePrefs[1];
						// open color dialog to get new color
						String newValue = ColorHelper.toRGBString(fBackgroundColorEditor.getColorValue());

						if (!newValue.equals(oldValue)) {
							stylePrefs[1] = newValue;
							String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
							getOverlayStore().setValue(namedStyle, newPrefString);
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
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[2];
					String newValue = String.valueOf(fBold.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[2] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
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
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[3];
					String newValue = String.valueOf(fItalic.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[3] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
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
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[4];
					String newValue = String.valueOf(fStrike.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[4] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
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
				Object o = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement();
				String namedStyle = o.toString();
				String prefString = getOverlayStore().getString(namedStyle);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					String oldValue = stylePrefs[5];
					String newValue = String.valueOf(fUnderline.getSelection());
					if (!newValue.equals(oldValue)) {
						stylePrefs[5] = newValue;
						String newPrefString = ColorHelper.packStylePreferences(stylePrefs);
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
				String namedStyle = ((IStructuredSelection) fStylesViewer.getSelection()).getFirstElement().toString();
				getOverlayStore().setToDefault(namedStyle);
				applyStyles();
				fText.redraw();
				activate(namedStyle);
			}
		});

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
		List overlayKeys = new ArrayList();

		Iterator i = getStylePreferenceKeys().iterator();
		while (i.hasNext()) {
			overlayKeys.add(new OverlayPreferenceStore.OverlayKey(OverlayPreferenceStore.STRING, (String) i.next()));
		}

		OverlayPreferenceStore.OverlayKey[] keys = new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
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
		StructuredViewer stylesViewer = new ListViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		stylesViewer.setComparator(new ViewerComparator(Collator.getInstance()));
		stylesViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Object description = fStyleToDescriptionMap.get(element);
				if (description != null)
					return description.toString();
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

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
		return stylesViewer;
	}

	@Override
	public void dispose() {
		if (fOverlayStore != null) {
			fOverlayStore.stop();
		}
		super.dispose();
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return PreferenceConstants.getPreferenceStore();
	}

	private TextAttribute getAttributeFor(String namedStyle) {
		TextAttribute ta = new TextAttribute(fDefaultForeground, fDefaultBackground, SWT.NORMAL);

		if (namedStyle != null && fOverlayStore != null) {
			// note: "namedStyle" *is* the preference key
			String prefString = getOverlayStore().getString(namedStyle);
			String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
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
						fontModifier = fontModifier | TextAttribute.STRIKETHROUGH;
				}
				if (stylePrefs.length > 5) {
					boolean on = Boolean.valueOf(stylePrefs[5]).booleanValue();
					if (on)
						fontModifier = fontModifier | TextAttribute.UNDERLINE;
				}

				ta = new TextAttribute((foreground != null) ? EditorUtility.getColor(foreground) : null, (background != null) ? EditorUtility.getColor(background) : null, fontModifier);
			}
		}
		return ta;
	}

	private String getExampleText() {
		return PHPUIMessages.getString("ColorPage_CodeExample_0");
	}

	private String getNamedStyleAtOffset(int offset) {
		// ensure the offset is clean
		if (offset >= fDocument.getLength())
			return getNamedStyleAtOffset(fDocument.getLength() - 1);
		else if (offset < 0)
			return getNamedStyleAtOffset(0);

		IStructuredDocumentRegion documentRegion = fDocument.getFirstStructuredDocumentRegion();
		while (documentRegion != null && !documentRegion.containsOffset(offset)) {
			documentRegion = documentRegion.getNext();
		}

		if (documentRegion != null) {
			String regionContext;
			ITextRegion interest = documentRegion.getRegionAtCharacterOffset(offset);

			ITextRegionCollection container = documentRegion;
			if (interest instanceof ITextRegionContainer) {
				container = (ITextRegionContainer) interest;
				interest = container.getRegionAtCharacterOffset(offset);
			}

			if (interest.getType() == PHPRegionContext.PHP_CONTENT) {
				IPhpScriptRegion phpScript = (IPhpScriptRegion) interest;
				try {
					regionContext = phpScript.getPhpTokenType(offset - container.getStartOffset() - phpScript.getStart());
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
			String namedStyle = (String) fContextToStyleMap.get(regionContext);
			return namedStyle;
		}
		return null;
	}

	private OverlayPreferenceStore getOverlayStore() {
		return fOverlayStore;
	}

	private Collection getStylePreferenceKeys() {
		if (fStylePreferenceKeys == null) {
			List styles = new ArrayList();
			styles.add(PreferenceConstants.EDITOR_NORMAL_COLOR);
			styles.add(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
			styles.add(PreferenceConstants.EDITOR_KEYWORD_COLOR);
			styles.add(PreferenceConstants.EDITOR_VARIABLE_COLOR);
			styles.add(PreferenceConstants.EDITOR_STRING_COLOR);
			styles.add(PreferenceConstants.EDITOR_NUMBER_COLOR);
			styles.add(PreferenceConstants.EDITOR_HEREDOC_COLOR);
			styles.add(PreferenceConstants.EDITOR_COMMENT_COLOR);
			styles.add(PreferenceConstants.EDITOR_PHPDOC_COLOR);
			styles.add(PreferenceConstants.EDITOR_TASK_COLOR);
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
					if ((e.detail == SWT.TRAVERSE_TAB_NEXT) || (e.detail == SWT.TRAVERSE_TAB_PREVIOUS))
						e.doit = true;
				}
			}
		};
	}

	public void init(IWorkbench workbench) {
		setDescription(SSEUIMessages.SyntaxColoring_Description);

		fStyleToDescriptionMap = new HashMap();
		fContextToStyleMap = new HashMap();

		initStyleToDescriptionMap();
		initRegionContextToStyleMap();

		fOverlayStore = new OverlayPreferenceStore(getPreferenceStore(), createOverlayStoreKeys());
		fOverlayStore.load();
		fOverlayStore.start();

		fStyleProvider.setColorPreferences(fOverlayStore);
	}

	private void initRegionContextToStyleMap() {
		fContextToStyleMap = fStyleProvider.getColorTypesMap();
	}

	private void initStyleToDescriptionMap() {
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_NORMAL_COLOR, PHPUIMessages.getString("ColorPage_Normal"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR, PHPUIMessages.getString("ColorPage_BoundryMaker"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_KEYWORD_COLOR, PHPUIMessages.getString("ColorPage_Keyword"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_VARIABLE_COLOR, PHPUIMessages.getString("ColorPage_Variable"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_STRING_COLOR, PHPUIMessages.getString("ColorPage_String"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_NUMBER_COLOR, PHPUIMessages.getString("ColorPage_Number"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_HEREDOC_COLOR, PHPUIMessages.getString("ColorPage_Heredoc"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_COMMENT_COLOR, PHPUIMessages.getString("ColorPage_Comment"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_PHPDOC_COLOR, PHPUIMessages.getString("ColorPage_Phpdoc"));
		fStyleToDescriptionMap.put(PreferenceConstants.EDITOR_TASK_COLOR, PHPUIMessages.getString("ColorPage_TaskTag"));
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
}
