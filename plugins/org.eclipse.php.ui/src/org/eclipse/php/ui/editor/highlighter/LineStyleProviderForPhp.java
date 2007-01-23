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
package org.eclipse.php.ui.editor.highlighter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.core.Logger;
import org.eclipse.php.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.util.PHPColorHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.core.internal.util.Debug;
import org.eclipse.wst.sse.ui.internal.provisional.style.Highlighter;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;

/**
 */
public class LineStyleProviderForPhp implements LineStyleProvider {
	private IStructuredDocument fDocument;
	private Highlighter fHighlighter;
	private boolean fInitialized;
	private PropertyChangeListener fPreferenceListener = new PropertyChangeListener();
	private Map fTextAttributes;

	/** Contains region to style mapping */
	private static final Map fColorTypes = new HashMap(); // String (token type), String (color)
	static {
		// Normal text:
		fColorTypes.put(PHPRegionTypes.PHP_STRING, PreferenceConstants.EDITOR_NORMAL_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_TOKEN, PreferenceConstants.EDITOR_NORMAL_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_SEMICOLON, PreferenceConstants.EDITOR_NORMAL_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_OPERATOR, PreferenceConstants.EDITOR_NORMAL_COLOR);

		// Boundary Markers
		fColorTypes.put(PHPRegionTypes.PHP_OPENTAG, PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CLOSETAG, PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);

		// Keywords
		fColorTypes.put(PHPRegionTypes.PHP_LOGICAL_AND, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ARRAY, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_AS, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_BREAK, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CASE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CATCH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CLASS, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CONTINUE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_DECLARE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_DEFAULT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_DO, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ECHO, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ELSE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ELSEIF, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_EMPTY, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENDDECLARE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENDFOR, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENDFOREACH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENDIF, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENDSWITCH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_EVAL, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_EXIT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_EXTENDS, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FOR, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FOREACH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FUNCTION, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_IF, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_HALT_COMPILER, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_INCLUDE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_INCLUDE_ONCE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_GLOBAL, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_LIST, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_NEW, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_NOT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_LOGICAL_OR, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PRINT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PUBLIC, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PRIVATE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PROTECTED, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_REQUIRE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_REQUIRE_ONCE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_RETURN, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_STATIC, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_SWITCH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_TRY, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_THROW, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_USE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_VAR, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_WHILE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_LOGICAL_XOR, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ABSTRACT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CLONE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FALSE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FINAL, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_DIE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_TRUE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_INSTANCEOF, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_UNSET, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_SELF, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ISSET, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PARENT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENDWHILE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FROM, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_IMPLEMENTS, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_INTERFACE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CONST, PreferenceConstants.EDITOR_KEYWORD_COLOR);

		// Variables
		fColorTypes.put(PHPRegionTypes.PHP_VARIABLE, PreferenceConstants.EDITOR_VARIABLE_COLOR);

		// Strings
		fColorTypes.put(PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING, PreferenceConstants.EDITOR_STRING_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENCAPSED_AND_WHITESPACE, PreferenceConstants.EDITOR_STRING_COLOR);

		// Comments
		fColorTypes.put(PHPRegionTypes.PHP_COMMENT, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_COMMENT_START, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_COMMENT_END, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_LINE_COMMENT, PreferenceConstants.EDITOR_COMMENT_COLOR);

		// Numbers
		fColorTypes.put(PHPRegionTypes.PHP_NUMBER, PreferenceConstants.EDITOR_NUMBER_COLOR);

		// Heredocs
		fColorTypes.put(PHPRegionTypes.PHP_HEREDOC_TAG, PreferenceConstants.EDITOR_HEREDOC_COLOR);

		// PHPDoc
		fColorTypes.put(PHPRegionTypes.PHPDOC_VAR, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_SEE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_NAME, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_DESC, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_TODO, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_LINK, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_EXAMPLE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_LICENSE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_PACKAGE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_VERSION, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_ABSTRACT, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_INTERNAL, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_TUTORIAL, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_CATEGORY, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_FINAL, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_SINCE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_PARAM, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_MAGIC, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_RETURN, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_AUTHOR, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_ACCESS, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_IGNORE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_THROWS, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_STATIC, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_GLOBAL, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_SUBPACKAGE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_FILESOURCE, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_EXCEPTION, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_COPYRIGHT, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_STATICVAR, PreferenceConstants.EDITOR_PHPDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_DEPRECATED, PreferenceConstants.EDITOR_PHPDOC_COLOR);

		fColorTypes.put(PHPRegionTypes.PHPDOC_COMMENT, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_COMMENT_START, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_COMMENT_END, PreferenceConstants.EDITOR_COMMENT_COLOR);

		fColorTypes.put(PHPRegionTypes.TASK, PreferenceConstants.EDITOR_TASK_COLOR);
	}

	public void init(IStructuredDocument structuredDocument, Highlighter highlighter) {
		commonInit(structuredDocument, highlighter);

		if (isInitialized())
			return;

		registerPreferenceManager();

		setInitialized(true);
	}

	public void release() {
		unRegisterPreferenceManager();
	}

	/**
	 * Returns the hashtable containing all the text attributes for this line
	 * style provider. Lazily creates a hashtable if one has not already been
	 * created.
	 * 
	 * @return
	 */
	protected Map getTextAttributes() {
		if (fTextAttributes == null) {
			fTextAttributes = new HashMap();
			loadColors();
		}
		return fTextAttributes;
	}

	/**
	 * Returns the attribute for simple php regions (open /close)
	 * not PHP_CONTENT regions 
	 * @param region
	 * @return the text attribute 
	 */
	protected TextAttribute getAttributeFor(ITextRegion region) {
		TextAttribute result = null;

		if (region != null) {
			final String type = region.getType();
			if (type == PHPRegionContext.PHP_OPEN) {
				result = getAttributeFor(PHPRegionTypes.PHP_OPENTAG);
			} else if (type == PHPRegionContext.PHP_CLOSE) {
				result = getAttributeFor(PHPRegionTypes.PHP_CLOSETAG);
			} else {
				result = getAttributeFor(region.getType());
			}
		}

		//return the defalt attributes if there is not highlight color for the region
		if (result == null) {
			result = (TextAttribute) getTextAttributes().get(PreferenceConstants.EDITOR_NORMAL_COLOR);
		}
		return result;
	}

	/**
	 * Look up the TextAttribute for the given region context. Might return
	 * null for unusual text.
	 * 
	 * @param type
	 * @return
	 */
	protected TextAttribute getAttributeFor(String type) {
		return (TextAttribute) getTextAttributes().get(fColorTypes.get(type));
	}

	/**
	 * Looks up the colorKey in the preference store and adds the style
	 * information to list of TextAttributes
	 * 
	 * @param colorKey
	 */
	protected void addTextAttribute(String colorKey) {
		if (getColorPreferences() != null) {
			String prefString = getColorPreferences().getString(colorKey);
			String[] stylePrefs = PHPColorHelper.unpackStylePreferences(prefString);
			if (stylePrefs != null) {
				getTextAttributes().put(colorKey, PHPColorHelper.createTextAttribute(stylePrefs));
			}
		}
	}

	public TextAttribute getTextAttributeForColor(String colorKey) {
		return (TextAttribute) getTextAttributes().get(colorKey);
	}

	/**
	 * this version does "trim" regions to match request
	 */
	protected StyleRange createStyleRange(ITextRegionCollection flatNode, ITextRegion region, TextAttribute attr, int startOffset, int length) {
		int start = flatNode.getStartOffset(region);
		if (start < startOffset)
			start = startOffset;
		int maxOffset = startOffset + length;
		int end = flatNode.getEndOffset(region); // use get length directly
		// instead of end-start?
		if (end > maxOffset)
			end = maxOffset;
		StyleRange result = new StyleRange(start, end - start, attr.getForeground(), attr.getBackground(), attr.getStyle());
		if ((attr.getStyle() & TextAttribute.UNDERLINE) != 0) {
			result.underline = true;
			result.fontStyle &= ~TextAttribute.UNDERLINE;
		}
		return result;
	}

	public boolean prepareRegions(ITypedRegion typedRegion, int lineRequestStart, int lineRequestLength, Collection holdResults) {
		final long currentTimeMillis = System.currentTimeMillis();
		final int partitionStartOffset = typedRegion.getOffset();
		final int partitionLength = typedRegion.getLength();
		IStructuredDocumentRegion structuredDocumentRegion = getDocument().getRegionAtCharacterOffset(partitionStartOffset);
		
		long delta2 = System.currentTimeMillis() - currentTimeMillis;
		final boolean prepareTextRegions = prepareTextRegions(structuredDocumentRegion, partitionStartOffset, partitionLength, holdResults);
		long delta = System.currentTimeMillis() - currentTimeMillis; 
		System.out.println("prepare regions " + delta + "(" + delta2 + ")");
		
		
		return prepareTextRegions;
	}

	/**
	 * @param region
	 * @param start
	 * @param length
	 * @param holdResults
	 * @return
	 */
	protected boolean prepareTextRegion(ITextRegionCollection blockedRegion, int partitionStartOffset, int partitionLength, Collection holdResults) {
		boolean handled = false;
		final int partitionEndOffset = partitionStartOffset + partitionLength - 1;
		ITextRegion region = null;
		ITextRegionList regions = blockedRegion.getRegions();
		int nRegions = regions.size();
		StyleRange styleRange = null;
		for (int i = 0; i < nRegions; i++) {
			region = regions.get(i);
			TextAttribute attr = null;
			TextAttribute previousAttr = null;
			final int startOffset = blockedRegion.getStartOffset(region);
			if (startOffset > partitionEndOffset)
				break;
			if (blockedRegion.getEndOffset(region) <= partitionStartOffset)
				continue;

			if (region instanceof ITextRegionCollection) {
				handled = prepareTextRegion((ITextRegionCollection) region, partitionStartOffset, partitionLength, holdResults);
			} else {

				if (region.getType() == PHPRegionContext.PHP_CONTENT) {
					handled = preparePhpRegions(holdResults, (PhpScriptRegion) region, startOffset, partitionStartOffset, partitionLength);
				} else {
					attr = getAttributeFor(region);
					if (attr != null) {
						handled = true;
						// if this region's attr is the same as previous one, then
						// just adjust the previous style range
						// instead of creating a new instance of one
						// note: to use 'equals' in this case is important, since
						// sometimes
						// different instances of attributes are associated with a
						// region, even the
						// the attribute has the same values.
						// TODO: this needs to be improved to handle readonly
						// regions correctly
						if ((styleRange != null) && (previousAttr != null) && (previousAttr.equals(attr))) {
							styleRange.length += region.getLength();
						} else {
							styleRange = createStyleRange(blockedRegion, region, attr, partitionStartOffset, partitionLength);
							holdResults.add(styleRange);
							// technically speaking, we don't need to update
							// previousAttr
							// in the other case, because the other case is when
							// it hasn't changed
							previousAttr = attr;
						}
					} else {
						previousAttr = null;
					}
				}
			}
		}
		return handled;
	}

	protected boolean prepareTextRegions(IStructuredDocumentRegion structuredDocumentRegion, int partitionStartOffset, int partitionLength, Collection holdResults) {
		boolean handled = false;
		final int partitionEndOffset = partitionStartOffset + partitionLength - 1;
		while (structuredDocumentRegion != null && structuredDocumentRegion.getStartOffset() <= partitionEndOffset) {
			ITextRegion region = null;
			ITextRegionList regions = structuredDocumentRegion.getRegions();
			int nRegions = regions.size();
			StyleRange styleRange = null;
			for (int i = 0; i < nRegions; i++) {
				region = regions.get(i);
				TextAttribute attr = null;
				TextAttribute previousAttr = null;
				final int startOffset = structuredDocumentRegion.getStartOffset(region);
				if (startOffset > partitionEndOffset)
					break;
				if (structuredDocumentRegion.getEndOffset(region) <= partitionStartOffset)
					continue;

				if (region instanceof ITextRegionCollection) {
					handled = prepareTextRegion((ITextRegionCollection) region, partitionStartOffset, partitionLength, holdResults);
				} else {

					if (region.getType() == PHPRegionContext.PHP_CONTENT) {
						handled = preparePhpRegions(holdResults, (PhpScriptRegion) region, startOffset, partitionStartOffset, partitionLength);
					} else {

						attr = getAttributeFor(region);
						if (attr != null) {
							handled = true;
							// if this region's attr is the same as previous one,
							// then just adjust the previous style range
							// instead of creating a new instance of one
							// note: to use 'equals' in this case is important,
							// since sometimes
							// different instances of attributes are associated
							// with a region, even the
							// the attribute has the same values.
							// TODO: this needs to be improved to handle readonly
							// regions correctly
							if ((styleRange != null) && (previousAttr != null) && (previousAttr.equals(attr))) {
								styleRange.length += region.getLength();
							} else {
								styleRange = createStyleRange(structuredDocumentRegion, region, attr, partitionStartOffset, partitionLength);
								holdResults.add(styleRange);
								// technically speaking, we don't need to update
								// previousAttr
								// in the other case, because the other case is
								// when it hasn't changed
								previousAttr = attr;
							}
						} else {
							previousAttr = null;
						}
					}

				}

				if (Debug.syntaxHighlighting && !handled) {
					System.out.println("not handled in prepareRegions"); //$NON-NLS-1$
				}
			}
			structuredDocumentRegion = structuredDocumentRegion.getNext();
		}
		return handled;
	}

	/**
	 * Prepares php regions to the line highliter
	 * @param holdResults - results
	 * @param region - php region
	 * @param partitionLength 
	 * @param partitionStartOffset 
	 */
	private boolean preparePhpRegions(Collection holdResults, PhpScriptRegion region, int regionStart, int partitionStartOffset, int partitionLength) {
		assert region.getType() == PHPRegionContext.PHP_CONTENT;

		StyleRange styleRange = null;
		TextAttribute attr;
		TextAttribute previousAttr = null;

		ITextRegion[] phpTokens = null;
		try {
			phpTokens = region.getPhpTokens(partitionStartOffset - regionStart, partitionLength);
			for (int i = 0; i < phpTokens.length; i++) {
				ITextRegion element = phpTokens[i];
				attr = getAttributeFor(element);
				if ((styleRange != null) && (previousAttr != null) && (previousAttr.equals(attr))) {
					styleRange.length += element.getLength();
				} else {
					styleRange =new StyleRange(regionStart + element.getStart(), element.getLength(), attr.getForeground(), attr.getBackground(), attr.getStyle());
					holdResults.add(styleRange);
					// technically speaking, we don't need to update
					// previousAttr
					// in the other case, because the other case is when
					// it hasn't changed
					previousAttr = attr;
				}
			}
			return true;
		} catch (BadLocationException e) {
			Logger.logException(e);
			return false;			
		}		
	}
	
	
	/*
	 * Returns hash of color attributes
	 */
	public Map getColorTypesMap() {
		return fColorTypes;
	}

	/*
	 * Handle changes to the preferences
	 */
	protected void handlePropertyChange(PropertyChangeEvent event) {
		if (event != null) {
			String prefKey = event.getProperty();
			if (PreferenceConstants.EDITOR_NORMAL_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_KEYWORD_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_VARIABLE_COLOR.equals(prefKey)
				|| PreferenceConstants.EDITOR_STRING_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_COMMENT_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_PHPDOC_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_NUMBER_COLOR.equals(prefKey)
				|| PreferenceConstants.EDITOR_HEREDOC_COLOR.equals(prefKey) || PreferenceConstants.EDITOR_TASK_COLOR.equals(prefKey)) {
				addTextAttribute(prefKey);
			}
		} else {
			loadColors();
		}
		fHighlighter.refreshDisplay();
	}

	public void loadColors() {
		assert getTextAttributes() != null && getTextAttributes().size() == 0;

		addTextAttribute(PreferenceConstants.EDITOR_NORMAL_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_KEYWORD_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_VARIABLE_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_STRING_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_COMMENT_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_PHPDOC_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_NUMBER_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_HEREDOC_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_TASK_COLOR);
	}

	protected void clearColors() {
		getTextAttributes().clear();
	}

	protected IPreferenceStore getColorPreferences() {
		return PreferenceConstants.getPreferenceStore();
	}

	private class PropertyChangeListener implements IPropertyChangeListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			// have to do it this way so others can override the method
			handlePropertyChange(event);
		}
	}

	protected void commonInit(IStructuredDocument document, Highlighter highlighter) {
		fDocument = document;
		fHighlighter = highlighter;
	}

	protected TextAttribute createTextAttribute(RGB foreground, RGB background, boolean bold) {
		return new TextAttribute((foreground != null) ? EditorUtility.getColor(foreground) : null, (background != null) ? EditorUtility.getColor(background) : null, bold ? SWT.BOLD : SWT.NORMAL);
	}

	protected IStructuredDocument getDocument() {
		return fDocument;
	}

	/**
	 */
	protected Highlighter getHighlighter() {
		return fHighlighter;
	}

	/**
	 * Returns the initialized.
	 * 
	 * @return boolean
	 */
	public boolean isInitialized() {
		return fInitialized;
	}

	private void registerPreferenceManager() {
		IPreferenceStore pref = getColorPreferences();
		if (pref != null) {
			pref.addPropertyChangeListener(fPreferenceListener);
		}
	}

	/**
	 * Sets the initialized.
	 * 
	 * @param initialized
	 *            The initialized to set
	 */
	private void setInitialized(boolean initialized) {
		this.fInitialized = initialized;
	}

	private void unRegisterPreferenceManager() {
		IPreferenceStore pref = getColorPreferences();
		if (pref != null) {
			pref.removePropertyChangeListener(fPreferenceListener);
		}
	}
}
