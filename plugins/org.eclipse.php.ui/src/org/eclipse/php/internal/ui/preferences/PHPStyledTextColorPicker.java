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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPhp;
import org.eclipse.php.internal.ui.util.PHPColorHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.sse.core.internal.document.DocumentReader;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.preferences.ui.StyledTextColorPicker;

public class PHPStyledTextColorPicker extends StyledTextColorPicker {

	protected IStructuredDocumentRegion fNodes = null;
	protected IPreferenceStore fPreferenceStore;
	protected Button fUnderline;
	protected SelectionListener buttonListener;
	protected LineStyleProviderForPhp fStyleProvider;

	public PHPStyledTextColorPicker(Composite parent, int style) {
		super(parent, style);
	}

	public void setLineStyleProvider(LineStyleProviderForPhp styleProvider) {
		fStyleProvider = styleProvider;
		if (fPreferenceStore != null) {
			fStyleProvider.setColorPreferences(fPreferenceStore);
		}
	}
	
	public LineStyleProviderForPhp getLineStyleProvider() {
		return fStyleProvider;
	}
	
	protected RGB changeColor(RGB startValue) {
		ColorDialog colorDlg = new ColorDialog(getShell());
		if (startValue != null) {
			colorDlg.setRGB(startValue);
		}
		colorDlg.open();
		RGB newRGB = colorDlg.getRGB();
		if (newRGB != null) {
			return newRGB;
		}
		return startValue;
	}

	protected String changeColor(String rgb) {
		String changedColor = "null"; //$NON-NLS-1$
		RGB newColor = changeColor(PHPColorHelper.toRGB(rgb));
		// null check to see if using default value
		if (newColor != null) {
			changedColor = PHPColorHelper.toRGBString(newColor);
		}
		return changedColor;
	}

	protected SelectionListener initButtonListener() {
		if (buttonListener != null) {
			return buttonListener;
		}

		buttonListener = new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				String namedStyle = getStyleName(fStyleCombo.getItem(fStyleCombo.getSelectionIndex()));
				if (namedStyle != null) {
					if (e.widget == fForeground) {
						// get current (newly old) style
						String prefString = getPreferenceStore().getString(namedStyle);
						String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
						if (stylePrefs != null) {
							String oldValue = stylePrefs[0];
							// open color dialog to get new color
							String newValue = changeColor(oldValue);

							if (!newValue.equals(oldValue)) {
								stylePrefs[0] = newValue;
								String newPrefString = PHPColorHelper.packStylePreferences(stylePrefs);
								getPreferenceStore().setValue(namedStyle, newPrefString);
								refresh();
							}
						}
					} else if (e.widget == fBackground) {
						// get current (newly old) style
						String prefString = getPreferenceStore().getString(namedStyle);
						String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
						if (stylePrefs != null) {
							String oldValue = stylePrefs[1];
							// open color dialog to get new color
							String newValue = changeColor(oldValue);

							if (!newValue.equals(oldValue)) {
								stylePrefs[1] = newValue;
								String newPrefString = PHPColorHelper.packStylePreferences(stylePrefs);
								getPreferenceStore().setValue(namedStyle, newPrefString);
								refresh();
							}
						}
					} else if (e.widget == fBold) {
						// get current (newly old) style
						String prefString = getPreferenceStore().getString(namedStyle);
						String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
						if (stylePrefs != null) {
							String oldValue = stylePrefs[2];
							String newValue = String.valueOf(fBold.getSelection());
							if (!newValue.equals(oldValue)) {
								stylePrefs[2] = newValue;
								String newPrefString = PHPColorHelper.packStylePreferences(stylePrefs);
								getPreferenceStore().setValue(namedStyle, newPrefString);
								refresh();
							}
						}
					} else if (e.widget == fItalic) {
						// get current (newly old) style
						String prefString = getPreferenceStore().getString(namedStyle);
						String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
						if (stylePrefs != null) {
							String oldValue = stylePrefs[3];
							String newValue = String.valueOf(fItalic.getSelection());
							if (!newValue.equals(oldValue)) {
								stylePrefs[3] = newValue;
								String newPrefString = PHPColorHelper.packStylePreferences(stylePrefs);
								getPreferenceStore().setValue(namedStyle, newPrefString);
								refresh();
							}
						}
					} else if (e.widget == fUnderline) {
						String prefString = getPreferenceStore().getString(namedStyle);
						String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
						if (stylePrefs != null) {
							String oldValue = stylePrefs[4];
							String newValue = String.valueOf(fUnderline.getSelection());
							if (!newValue.equals(oldValue)) {
								stylePrefs[4] = newValue;
								String newPrefString = PHPColorHelper.packStylePreferences(stylePrefs);
								getPreferenceStore().setValue(namedStyle, newPrefString);
								refresh();
							}
						}
					} else if (e.widget == fClearStyle) {
						getPreferenceStore().setToDefault(namedStyle);
						refresh();
					}
				}
			}
		};

		return buttonListener;
	}

	protected String getStyleName(String description) {
		if (description == null) {
			return null;
		}
		String styleName = null;
		java.util.Enumeration keys = getDescriptions().keys();
		while (keys.hasMoreElements()) {
			String test = keys.nextElement().toString();
			if (getDescriptions().get(test).equals(description)) {
				styleName = test;
				break;
			}
		}
		return styleName;
	}

	protected void activate(String namedStyle) {
		super.activate(namedStyle);
		if (namedStyle == null) {
			fItalic.setEnabled(false);
			fUnderline.setEnabled(false);
		} else {
			fItalic.setEnabled(true);
			fUnderline.setEnabled(true);
		}
		TextAttribute attribute = getAttribute(namedStyle);
		fItalic.setSelection((attribute.getStyle() & SWT.ITALIC) != 0);
		fUnderline.setSelection((attribute.getStyle() & TextAttribute.UNDERLINE) != 0);
	}

	protected Button createCheckBox(Composite group, String label) {
		Button button = new Button(group, SWT.CHECK | SWT.CENTER);
		if (label != null) {
			button.setText(label);
		}
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
		button.setLayoutData(data);
		return button;
	}

	protected void createControls(Composite parent) {
		super.createControls(parent);

		initButtonListener();

		// Remove old listeners
		fForeground.removeSelectionListener(super.buttonListener);
		fBackground.removeSelectionListener(super.buttonListener);
		fClearStyle.removeSelectionListener(super.buttonListener);
		fBold.removeSelectionListener(super.buttonListener);
		if (showItalic) {
			fItalic.removeSelectionListener(super.buttonListener);
		}

		fForeground.addSelectionListener(buttonListener);
		fBackground.addSelectionListener(buttonListener);
		fClearStyle.addSelectionListener(buttonListener);
		fBold.addSelectionListener(buttonListener);

		// Add Italic (if doesn't exist) and Underline checkboxes
		Composite styleRow = fBold.getParent();
		if (!showItalic) {
			((GridLayout) styleRow.getLayout()).numColumns++;
			fItalic = createCheckBox(styleRow, SSEUIMessages.Italics_UI); ////$NON-NLS-1$
			fItalic.setEnabled(false);
			fItalic.addSelectionListener(buttonListener);
		}
		((GridLayout) styleRow.getLayout()).numColumns++;
		fUnderline = createCheckBox(styleRow, PHPUIMessages.ColorPage_Underline);
		fUnderline.setEnabled(false);
		fUnderline.addSelectionListener(buttonListener);
	}

	protected TextAttribute getAttribute(String namedStyle) {
		TextAttribute ta = new TextAttribute(getDefaultForeground(), getDefaultBackground(), SWT.NORMAL);

		if (namedStyle != null && getPreferenceStore() != null) {
			String prefString = getPreferenceStore().getString(namedStyle);
			String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
			if (stylePrefs != null) {
				ta = PHPColorHelper.createTextAttribute(stylePrefs);
			}
		}
		return ta;
	}

	protected void applyStyles() {
		if (fText == null || fText.isDisposed() || fInput == null || fInput.length() == 0) {
			return;
		}
		
		fStyleProvider.loadColors();
		
		IStructuredDocumentRegion node = fNodes;
		final Collection holdResults = new ArrayList();
		fStyleProvider.prepareTextRegions(node, 0, fNodes.getEnd(), holdResults);
		
		for (Iterator iter = holdResults.iterator(); iter.hasNext();) {
			StyleRange element = (StyleRange) iter.next();
			fText.setStyleRange(element);			
		}
	}

	protected String getNamedStyleAtOffset(int offset) {
		// ensure the offset is clean
		if (offset >= fInput.length()) {
			return getNamedStyleAtOffset(fInput.length() - 1);
		} else if (offset < 0) {
			return getNamedStyleAtOffset(0);
		}
		// find the ITextRegion at this offset
		if (fNodes == null) {
			return null;
		}

		String regionContext;
		ITextRegion interest = fNodes.getRegionAtCharacterOffset(offset);
		
		ITextRegionCollection container = fNodes;
		if (interest instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) interest;
			interest = container.getRegionAtCharacterOffset(offset);
		}
		
		if (interest.getType() == PHPRegionContext.PHP_CONTENT) {
			PhpScriptRegion phpScript = (PhpScriptRegion) interest;
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
		String namedStyle = (String) getContextStyleMap().get(regionContext);
		return namedStyle;
	}

	/*
	 * This method is overriden, because parent's setText() creates wrong type of input reader
	 * @see org.eclipse.wst.sse.ui.internal.preferences.ui.StyledTextColorPicker#setText(java.lang.String)
	 */
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

	public IPreferenceStore getPreferenceStore() {
		return fPreferenceStore;
	}

	public void setPreferenceStore(IPreferenceStore preferenceStore) {
		fPreferenceStore = preferenceStore;
		if (fStyleProvider != null) {
			fStyleProvider.setColorPreferences(preferenceStore);
		}
		super.setPreferenceStore(preferenceStore);
	}
}
