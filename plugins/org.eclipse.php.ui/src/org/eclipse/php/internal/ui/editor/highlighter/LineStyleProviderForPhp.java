/*******************************************************************************
 * Copyright (c) 2006, 2016 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.core.internal.util.Debug;
import org.eclipse.wst.sse.ui.internal.preferences.ui.ColorHelper;
import org.eclipse.wst.sse.ui.internal.provisional.style.AbstractLineStyleProvider;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;
import org.eclipse.wst.sse.ui.internal.util.EditorUtility;

/**
 * Coloring mechanism for PHP partitions
 */
public class LineStyleProviderForPhp extends AbstractLineStyleProvider implements LineStyleProvider {

	private IPreferenceStore fColorPreferences;

	/** Contains region to style mapping */
	protected static final Map<String, String> fColorTypes = new HashMap<String, String>(); // String
	// (token
	// type),
	// String
	// (color)

	static {
		// Normal text:
		fColorTypes.put(PHPRegionTypes.PHP_LABEL, PreferenceConstants.EDITOR_NORMAL_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_TOKEN, PreferenceConstants.EDITOR_NORMAL_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_SEMICOLON, PreferenceConstants.EDITOR_NORMAL_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_OPERATOR, PreferenceConstants.EDITOR_NORMAL_COLOR);

		// Boundary Markers
		fColorTypes.put(PHPRegionTypes.PHP_OPENTAG, PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CLOSETAG, PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);

		// Keywords
		fColorTypes.put(PHPRegionTypes.PHP_LOGICAL_AND, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ARRAY, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_STRING, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_INT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FLOAT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_BOOL, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_VOID, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_AS, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_BREAK, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CASE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CATCH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_FINALLY, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CLASS, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CONTINUE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_GOTO, PreferenceConstants.EDITOR_KEYWORD_COLOR);
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
		fColorTypes.put(PHPRegionTypes.PHP_PUBLIC, PreferenceConstants.EDITOR_KEYWORD_PUBLIC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PRIVATE, PreferenceConstants.EDITOR_KEYWORD_PRIVATE_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_PROTECTED, PreferenceConstants.EDITOR_KEYWORD_PROTECTED_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_REQUIRE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_REQUIRE_ONCE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_RETURN, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_YIELD, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_STATIC, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_SWITCH, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_TRY, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_THROW, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_NAMESPACE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_USE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_VAR, PreferenceConstants.EDITOR_KEYWORD_PUBLIC_COLOR);
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
		fColorTypes.put(PHPRegionTypes.PHP_TRAIT, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_INSTEADOF, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_CALLABLE, PreferenceConstants.EDITOR_KEYWORD_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_KEYWORD, PreferenceConstants.EDITOR_KEYWORD_COLOR);

		// Variables
		fColorTypes.put(PHPRegionTypes.PHP_VARIABLE, PreferenceConstants.EDITOR_VARIABLE_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENCAPSED_VARIABLE, PreferenceConstants.EDITOR_VARIABLE_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_THIS, PreferenceConstants.EDITOR_VARIABLE_COLOR);

		// Strings
		fColorTypes.put(PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING, PreferenceConstants.EDITOR_STRING_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_ENCAPSED_AND_WHITESPACE, PreferenceConstants.EDITOR_STRING_COLOR);

		// Comments
		fColorTypes.put(PHPRegionTypes.PHP_COMMENT, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_COMMENT_START, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_COMMENT_END, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_LINE_COMMENT, PreferenceConstants.EDITOR_LINE_COMMENT_COLOR);

		// Numbers
		fColorTypes.put(PHPRegionTypes.PHP_NUMBER, PreferenceConstants.EDITOR_NUMBER_COLOR);

		// Heredocs/nowdocs
		fColorTypes.put(PHPRegionTypes.PHP_HEREDOC_START_TAG, PreferenceConstants.EDITOR_HEREDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_HEREDOC_CLOSE_TAG, PreferenceConstants.EDITOR_HEREDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_NOWDOC_START_TAG, PreferenceConstants.EDITOR_HEREDOC_COLOR);
		fColorTypes.put(PHPRegionTypes.PHP_NOWDOC_CLOSE_TAG, PreferenceConstants.EDITOR_HEREDOC_COLOR);

		// PHPDoc
		// fColorTypes.put(PHPRegionTypes.PHPDOC_TODO,
		// PreferenceConstants.EDITOR_TASK_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_GENERIC_TAG, PreferenceConstants.EDITOR_PHPDOC_COLOR);

		fColorTypes.put(PHPRegionTypes.PHPDOC_COMMENT, PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_COMMENT_START, PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR);
		fColorTypes.put(PHPRegionTypes.PHPDOC_COMMENT_END, PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR);

		// fColorTypes.put(PHPRegionTypes.TASK,
		// PreferenceConstants.EDITOR_TASK_COLOR);
	}

	/**
	 * Returns the attribute for simple php regions (open/close) not PHP_CONTENT
	 * regions
	 * 
	 * @param region
	 * @return the text attribute
	 */
	@Override
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

		// return the default attributes if there is not highlight color for the
		// region
		if (result == null) {
			result = (TextAttribute) getTextAttributes().get(PreferenceConstants.EDITOR_NORMAL_COLOR);
		}
		return result;
	}

	/**
	 * Look up the TextAttribute for the given region context. Might return null
	 * for unusual text.
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
	@Override
	protected void addTextAttribute(String colorKey) {
		if (getColorPreferences() != null) {
			String enableKey = PreferenceConstants.getEnabledPreferenceKey(colorKey);
			boolean enabled = getColorPreferences().getBoolean(enableKey);
			if (enabled) {
				String prefString = getColorPreferences().getString(colorKey);
				String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
				if (stylePrefs != null) {
					getTextAttributes().put(colorKey, createTextAttribute(stylePrefs));
				}
			} else {
				addDefaultTextAttribute(colorKey);
			}
		}
	}

	protected void addDefaultTextAttribute(String colorKey) {
		if (getColorPreferences() != null) {
			String prefString = PreferenceConstants.EDITOR_NORMAL_DEFAULT_COLOR;
			String[] stylePrefs = ColorHelper.unpackStylePreferences(prefString);
			if (stylePrefs != null) {
				getTextAttributes().put(colorKey, createTextAttribute(stylePrefs));
			}
		}
	}

	/*
	 * Creates TextAttribute from the given style description array string
	 */
	protected TextAttribute createTextAttribute(String[] stylePrefs) {
		int fontModifier = SWT.NORMAL;
		if (Boolean.valueOf(stylePrefs[2]).booleanValue()) { // bold
			fontModifier |= SWT.BOLD;
		}
		if (Boolean.valueOf(stylePrefs[3]).booleanValue()) { // italic
			fontModifier |= SWT.ITALIC;
		}
		if (Boolean.valueOf(stylePrefs[4]).booleanValue()) { // strikethrough
			fontModifier |= TextAttribute.STRIKETHROUGH;
		}
		if (Boolean.valueOf(stylePrefs[5]).booleanValue()) { // underline
			fontModifier |= TextAttribute.UNDERLINE;
		}
		return new TextAttribute(EditorUtility.getColor(ColorHelper.toRGB(stylePrefs[0])),
				EditorUtility.getColor(ColorHelper.toRGB(stylePrefs[1])), fontModifier);
	}

	public TextAttribute getTextAttributeForColor(String colorKey) {
		return (TextAttribute) getTextAttributes().get(colorKey);
	}

	/**
	 * this version does "trim" regions to match request
	 */
	protected StyleRange createStyleRange(ITextRegionCollection flatNode, ITextRegion region, TextAttribute attr,
			int startOffset, int length) {
		int start = flatNode.getStartOffset(region);
		if (start < startOffset)
			start = startOffset;
		int maxOffset = startOffset + length;
		int end = flatNode.getEndOffset(region); // use get length directly
		// instead of end-start?
		if (end > maxOffset)
			end = maxOffset;
		StyleRange result = new StyleRange(start, end - start, attr.getForeground(), attr.getBackground(),
				attr.getStyle());
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

	@Override
	public boolean prepareRegions(ITypedRegion typedRegion, int lineRequestStart, int lineRequestLength,
			Collection holdResults) {
		final int partitionStartOffset = typedRegion.getOffset();
		final int partitionLength = typedRegion.getLength();
		IStructuredDocumentRegion structuredDocumentRegion = getDocument()
				.getRegionAtCharacterOffset(partitionStartOffset);
		final boolean prepareTextRegions = prepareTextRegions(structuredDocumentRegion, partitionStartOffset,
				partitionLength, holdResults);
		return prepareTextRegions;
	}

	public IStructuredDocumentRegion getDamagedRegion(ITypedRegion typedRegion) {
		final int partitionStartOffset = typedRegion.getOffset();
		return getDocument().getRegionAtCharacterOffset(partitionStartOffset);
	}

	/**
	 * @param blockedRegion
	 * @param partitionStartOffset
	 * @param partitionLength
	 * @param holdResults
	 * @return
	 */
	public boolean prepareTextRegion(ITextRegionCollection blockedRegion, int partitionStartOffset, int partitionLength,
			Collection<StyleRange> holdResults) {
		if (blockedRegion == null) {
			return false;
		}

		assert blockedRegion.getLength() > 0 && partitionStartOffset >= 0 && partitionLength >= 0
				&& holdResults != null;

		if (partitionLength == 0) {
			return true;
		}

		boolean handled = false;
		final int partitionEndOffset = partitionStartOffset + partitionLength;
		ITextRegion region = null;
		ITextRegionList regions = blockedRegion.getRegions();
		int nRegions = regions.size();
		StyleRange styleRange = null;
		TextAttribute previousAttr = null;
		TextAttribute attr = null;
		int previousEndOffset = -1;

		for (int i = 0; i < nRegions; i++) {
			region = regions.get(i);
			final int startOffset = blockedRegion.getStartOffset(region);
			if (startOffset >= partitionEndOffset)
				break;
			if (blockedRegion.getEndOffset(region) <= partitionStartOffset)
				continue;

			if (region instanceof ITextRegionCollection) {
				handled = prepareTextRegion((ITextRegionCollection) region, partitionStartOffset, partitionLength,
						holdResults);
				previousAttr = null;
				styleRange = null;
				previousEndOffset = -1;
			} else {

				if (region.getType() == PHPRegionContext.PHP_CONTENT) {
					handled = preparePhpRegions(holdResults, (IPhpScriptRegion) region, startOffset,
							partitionStartOffset, partitionLength);
					previousAttr = null;
					styleRange = null;
					previousEndOffset = -1;
				} else {
					attr = getAttributeFor(region);
					if (attr != null) {
						handled = true;
						// if this region's attr is the same as previous one,
						// then just adjust the previous style range
						// instead of creating a new instance of one
						// note: to use 'equals' in this case is important,
						// since sometimes different instances of attributes are
						// associated with a region, even if the attribute has
						// the same values.
						// TODO: this needs to be improved to handle readonly
						// regions correctly
						if (styleRange != null && previousAttr != null && previousAttr.equals(attr)
								&& previousEndOffset == startOffset) {
							// extends the prev styleRange with the current
							// region length
							styleRange.length += region.getLength();
							if (styleRange.start + styleRange.length > partitionEndOffset) {
								styleRange.length -= styleRange.start + styleRange.length - partitionEndOffset;
							}
						} else {
							styleRange = createStyleRange(blockedRegion, region, attr, partitionStartOffset,
									partitionLength);
							holdResults.add(styleRange);
						}
						previousAttr = attr;
						previousEndOffset = blockedRegion.getEndOffset(region);
					} else {
						previousAttr = null;
						styleRange = null;
						previousEndOffset = -1;
					}
				}
			}
		}
		return handled;
	}

	public boolean prepareTextRegions(IStructuredDocumentRegion structuredDocumentRegion, int partitionStartOffset,
			int partitionLength, Collection<StyleRange> holdResults) {
		if (structuredDocumentRegion == null) {
			return false;
		}

		assert structuredDocumentRegion.getLength() > 0 && partitionStartOffset >= 0 && partitionLength >= 0
				&& holdResults != null;

		if (partitionLength == 0) {
			return true;
		}

		boolean handled = false;
		final int partitionEndOffset = partitionStartOffset + partitionLength;

		while (structuredDocumentRegion != null && structuredDocumentRegion.getStartOffset() < partitionEndOffset) {
			ITextRegion region = null;
			ITextRegionList regions = structuredDocumentRegion.getRegions();
			int nRegions = regions.size();
			StyleRange styleRange = null;
			TextAttribute previousAttr = null;
			TextAttribute attr = null;
			int previousEndOffset = -1;

			for (int i = 0; i < nRegions; i++) {
				region = regions.get(i);
				final int startOffset = structuredDocumentRegion.getStartOffset(region);
				if (startOffset >= partitionEndOffset)
					break;
				if (structuredDocumentRegion.getEndOffset(region) <= partitionStartOffset)
					continue;

				if (region instanceof ITextRegionCollection) {
					handled = prepareTextRegion((ITextRegionCollection) region, partitionStartOffset, partitionLength,
							holdResults);
					previousAttr = null;
					styleRange = null;
					previousEndOffset = -1;
				} else {

					if (region.getType() == PHPRegionContext.PHP_CONTENT) {
						handled = preparePhpRegions(holdResults, (IPhpScriptRegion) region, startOffset,
								partitionStartOffset, partitionLength);
						previousAttr = null;
						styleRange = null;
						previousEndOffset = -1;
					} else {

						attr = getAttributeFor(region);
						if (attr != null) {
							handled = true;
							// if this region's attr is the same as previous
							// one, then just adjust the previous style range
							// instead of creating a new instance of one
							// note: to use 'equals' in this case is important,
							// since sometimes different instances of attributes
							// are associated with a region, even if the
							// attribute has the same values.
							// TODO: this needs to be improved to handle
							// readonly regions correctly
							if (styleRange != null && previousAttr != null && previousAttr.equals(attr)
									&& previousEndOffset == startOffset) {
								// extends the prev styleRange with the current
								// region length
								styleRange.length += region.getLength();
								if (styleRange.start + styleRange.length > partitionEndOffset) {
									styleRange.length -= styleRange.start + styleRange.length - partitionEndOffset;
								}
							} else {
								styleRange = createStyleRange(structuredDocumentRegion, region, attr,
										partitionStartOffset, partitionLength);
								holdResults.add(styleRange);
							}
							previousAttr = attr;
							previousEndOffset = structuredDocumentRegion.getEndOffset(region);
						} else {
							previousAttr = null;
							styleRange = null;
							previousEndOffset = -1;
						}
					}
				}

				if (Debug.syntaxHighlighting) {
					if (!handled) {
						System.out.println("not handled in prepareRegions"); //$NON-NLS-1$
					}
				}
			}
			structuredDocumentRegion = structuredDocumentRegion.getNext();
		}
		return handled;
	}

	/**
	 * Prepares php regions for the line highlighter
	 * 
	 * @param holdResults
	 *            - results
	 * @param region
	 *            - php region
	 * @param regionStart
	 * @param partitionStartOffset
	 * @param partitionLength
	 */
	private boolean preparePhpRegions(Collection<StyleRange> holdResults, IPhpScriptRegion region, int regionStart,
			int partitionStartOffset, int partitionLength) {
		assert holdResults != null && region != null && region.getType() == PHPRegionContext.PHP_CONTENT
				&& region.getLength() > 0 && regionStart >= 0 && partitionStartOffset >= 0 && partitionLength > 0;

		ITextRegion[] phpTokens;
		StyleRange styleRange = null;
		TextAttribute previousAttr = null;
		TextAttribute attr = null;
		ITextRegion prevElement = null;

		try {

			if (!region.isFullReparsed() && (regionStart == partitionStartOffset)) {
				phpTokens = region.getUpdatedPhpTokens();
				int from = region.getUpdatedTokensStart();
				partitionStartOffset = from + regionStart;
				partitionLength = region.getUpdatedTokensLength();
				assert (phpTokens.length > 0 && partitionStartOffset >= 0 && partitionLength > 0)
						|| (phpTokens.length == 0 && partitionStartOffset >= 0 && partitionLength == 0);
			} else {
				// compute interval intersection between region and partition
				int from = Math.max(0, partitionStartOffset - regionStart);
				int length = Math.min(regionStart + region.getLength(), partitionStartOffset + partitionLength)
						- Math.max(regionStart, partitionStartOffset);
				if (length == 0) {
					// empty region and partition intersection, there's nothing
					// to do (happens when partitionLength is = 0, so we should
					// never land here)
					return true;
				}
				// if length < 0 (i.e. when partitionLength < 0), we'll throw a
				// BadLocationException
				phpTokens = region.getPhpTokens(from, length);
			}

			for (int i = 0; i < phpTokens.length; i++) {
				ITextRegion element = phpTokens[i];
				attr = getAttributeFor(element);
				// Check that the elements are different, otherwise the
				// coloring is not valid
				if (prevElement == element) {
					continue;
				}
				if (styleRange != null && previousAttr != null && previousAttr.equals(attr) && prevElement != null
						&& prevElement.getStart() + prevElement.getLength() == element.getStart()) {
					// extends the prev styleRange with the current element
					// length
					styleRange.length += element.getLength();
					if (styleRange.start + styleRange.length > partitionStartOffset + partitionLength) {
						styleRange.length -= styleRange.start + styleRange.length
								- (partitionStartOffset + partitionLength);
					}
				} else {
					// create new styleRange
					int styleStart = regionStart + element.getStart();
					int styleLength = element.getLength();
					int blankStart = regionStart + element.getTextEnd();
					int blankLength = element.getEnd() - element.getTextEnd();
					assert styleStart + styleLength == blankStart + blankLength;
					if (styleStart >= partitionStartOffset + partitionLength) {
						// if the range starts after the requested partition,
						// stop now
						break;
					} else if (styleStart + styleLength <= partitionStartOffset) {
						// if the range ends before the requested
						// starting position, ignore it
						continue;
					} else if (styleStart < partitionStartOffset) {
						// if the region starts before the requested
						// starting position, adjust the style start
						// position
						styleLength -= (partitionStartOffset - styleStart);
						styleStart = partitionStartOffset;
						if (blankStart < partitionStartOffset) {
							blankLength -= partitionStartOffset - blankStart;
						}
					} else if (styleStart + styleLength > partitionStartOffset + partitionLength) {
						// if the region ends after the requested end position,
						// making it shorter
						styleLength -= styleStart + styleLength - (partitionStartOffset + partitionLength);
						if (blankStart < partitionStartOffset + partitionLength) {
							blankLength = partitionStartOffset + partitionLength - blankStart;
						} else {
							blankLength = 0;
						}
					}
					if (attr.getBackground() != null && element.getTextEnd() != element.getEnd()) {
						// in case of background color make sure the
						// highlighting will not paint the whitespaces
						styleRange = new StyleRange(styleStart, styleLength - blankLength, attr.getForeground(),
								attr.getBackground(), attr.getStyle());
						if ((attr.getStyle() & TextAttribute.UNDERLINE) != 0) {
							styleRange.underline = true;
							styleRange.fontStyle &= ~TextAttribute.UNDERLINE;
						}
						if ((attr.getStyle() & TextAttribute.STRIKETHROUGH) != 0) {
							styleRange.strikeout = true;
							styleRange.fontStyle &= ~TextAttribute.STRIKETHROUGH;
						}
						holdResults.add(styleRange);
						previousAttr = attr;
						if (blankLength > 0) {
							// apply style to the whitespace (important for
							// the refresh of the specific range
							styleRange = new StyleRange(styleStart + styleLength - blankLength, blankLength,
									attr.getForeground(), null, attr.getStyle());
							holdResults.add(styleRange);
							previousAttr = null;
						}
					} else {
						styleRange = new StyleRange(styleStart, styleLength, attr.getForeground(), attr.getBackground(),
								attr.getStyle());
						if ((attr.getStyle() & TextAttribute.UNDERLINE) != 0) {
							styleRange.underline = true;
							styleRange.fontStyle &= ~TextAttribute.UNDERLINE;
						}
						if ((attr.getStyle() & TextAttribute.STRIKETHROUGH) != 0) {
							styleRange.strikeout = true;
							styleRange.fontStyle &= ~TextAttribute.STRIKETHROUGH;
						}
						holdResults.add(styleRange);
						previousAttr = attr;
					}
				}
				prevElement = element;
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
	public Map<String, String> getColorTypesMap() {
		return fColorTypes;
	}

	@Override
	public void loadColors() {
		addTextAttribute(PreferenceConstants.EDITOR_NORMAL_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_KEYWORD_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_VARIABLE_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_STRING_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_COMMENT_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_LINE_COMMENT_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_PHPDOC_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_NUMBER_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_HEREDOC_COLOR);
		// addTextAttribute(PreferenceConstants.EDITOR_TASK_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_KEYWORD_PRIVATE_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_KEYWORD_PUBLIC_COLOR);
		addTextAttribute(PreferenceConstants.EDITOR_KEYWORD_PROTECTED_COLOR);
	}

	protected void clearColors() {
		getTextAttributes().clear();
	}

	public void setColorPreferences(IPreferenceStore preferenceStore) {
		fColorPreferences = preferenceStore;
	}

	@Override
	public IPreferenceStore getColorPreferences() {
		if (fColorPreferences != null) {
			return fColorPreferences;
		}
		return PreferenceConstants.getPreferenceStore();
	}

	/*
	 * Handle preference changes
	 */
	@Override
	protected void handlePropertyChange(PropertyChangeEvent event) {
		if (event != null) {
			String prefKey = event.getProperty();
			if (PreferenceConstants.EDITOR_NORMAL_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_KEYWORD_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_VARIABLE_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_STRING_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_COMMENT_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_LINE_COMMENT_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_PHPDOC_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_NUMBER_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_HEREDOC_COLOR.equals(prefKey)
					// || PreferenceConstants.EDITOR_TASK_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_KEYWORD_PRIVATE_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_KEYWORD_PROTECTED_COLOR.equals(prefKey)
					|| PreferenceConstants.EDITOR_KEYWORD_PUBLIC_COLOR.equals(prefKey)) {
				addTextAttribute(prefKey);
			} else
				if (PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_NORMAL_COLOR).equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_KEYWORD_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_VARIABLE_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_STRING_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_COMMENT_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_LINE_COMMENT_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_PHPDOC_COMMENT_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_PHPDOC_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_NUMBER_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_HEREDOC_COLOR)
								.equals(prefKey)
								// ||
								// PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_TASK_COLOR)
								// .equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_KEYWORD_PRIVATE_COLOR)
								.equals(prefKey)
						|| PreferenceConstants
								.getEnabledPreferenceKey(PreferenceConstants.EDITOR_KEYWORD_PROTECTED_COLOR)
								.equals(prefKey)
						|| PreferenceConstants.getEnabledPreferenceKey(PreferenceConstants.EDITOR_KEYWORD_PUBLIC_COLOR)
								.equals(prefKey)) {
				boolean enabled = getColorPreferences().getBoolean(prefKey);
				prefKey = prefKey.split("\\.")[1]; //$NON-NLS-1$
				if (enabled) {
					addTextAttribute(prefKey);
				} else {
					addDefaultTextAttribute(prefKey);
				}
			}
		} else {
			loadColors();
		}

		if (fRecHighlighter != null) {
			fRecHighlighter.refreshDisplay();
		}
	}
}
