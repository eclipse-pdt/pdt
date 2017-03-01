/*******************************************************************************
 * Copyright (c) 2009, 2016, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.format;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class FormatterUtils {
	private static final String FORMATTER_COMMON_PREFERENCE_EXT = "org.eclipse.php.core.phpFormatterCommonPreferences"; //$NON-NLS-1$
	private static PHPStructuredTextPartitioner partitioner = new PHPStructuredTextPartitioner();
	private static IFormatterCommonPrferences usedFormatter;

	public static final String PARTITION_CSS_STYLE = "org.eclipse.wst.css.STYLE"; //$NON-NLS-1$
	public static final String PARTITION_JS_SCRIPT = "org.eclipse.wst.html.SCRIPT"; //$NON-NLS-1$

	public static @Nullable String getRegionType(IStructuredDocument document, int offset) {
		try {
			IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
			if (sdRegion == null) {
				return null;
			}

			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			if (tRegion == null && offset == document.getLength()) {
				offset -= 1;
				tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			}
			// in case the cursor on the beginning of '?>' tag
			// we decrease the offset to get the PhpScriptRegion
			if (tRegion != null && tRegion.getType().equals(PHPRegionContext.PHP_CLOSE)) {
				tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
			}
			if (tRegion != null) {
				int regionStart = sdRegion.getStartOffset(tRegion);

				// in case of container we have to extract the PhpScriptRegion
				if (tRegion instanceof ITextRegionContainer) {
					ITextRegionContainer container = (ITextRegionContainer) tRegion;
					tRegion = container.getRegionAtCharacterOffset(offset);
					regionStart += tRegion.getStart();
				}

				if (tRegion != null && tRegion instanceof IPhpScriptRegion) {
					IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
					int regionOffset = offset - regionStart;
					ITextRegion innerRegion = scriptRegion.getPhpToken(regionOffset);
					return innerRegion.getType();
				}
			}
		} catch (final BadLocationException e) {
		}

		return null;
	}

	public static @Nullable String getPartitionType(IStructuredDocument document, int offset,
			boolean preferNonWhitespacePartitions) {
		try {
			IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(offset);
			if (sdRegion == null) {
				return null;
			}

			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			if (tRegion == null && offset == document.getLength()) {
				offset -= 1;
				tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			}
			// in case the cursor on the beginning of '?>' tag
			// we decrease the offset to get the PhpScriptRegion
			if (tRegion != null && tRegion.getType().equals(PHPRegionContext.PHP_CLOSE)) {
				tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
			}

			int regionStart = sdRegion.getStartOffset(tRegion);

			// in case of container we have to extract the PhpScriptRegion
			if (tRegion != null && tRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) tRegion;
				tRegion = container.getRegionAtCharacterOffset(offset);
				regionStart += tRegion.getStart();
			}

			if (tRegion != null && tRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
				if (preferNonWhitespacePartitions
						&& scriptRegion.getPhpToken(offset - regionStart).getTextEnd() <= offset - regionStart) {
					return scriptRegion.getPartition(scriptRegion.getPhpToken(offset - regionStart).getEnd());
				}
				return scriptRegion.getPartition(offset - regionStart);
			}
		} catch (final BadLocationException e) {
		}

		partitioner.connect(document);
		return partitioner.getContentType(offset);
	}

	public static @Nullable String getPartitionType(IStructuredDocument document, int offset) {
		return getPartitionType(document, offset, false);
	}

	private static StringBuilder helpBuffer = new StringBuilder(50);

	/**
	 * Return the blanks at the start of the line.
	 */
	public static String getLineBlanks(IDocument document, IRegion lineInfo) throws BadLocationException {
		helpBuffer.setLength(0);
		int startOffset = lineInfo.getOffset();
		int length = lineInfo.getLength();
		char[] line = document.get(startOffset, length).toCharArray();
		for (int i = 0; i < length; i++) {
			char c = line[i];
			if (Character.isWhitespace(c)) {
				helpBuffer.append(c);
			} else {
				break;
			}
		}
		return helpBuffer.toString();
	}

	/**
	 * Returns the previous php structured document. Special cases : 1) previous
	 * is null - returns null 2) previous is not PHP region - returns the last
	 * region of the last php block
	 * 
	 * @param currentStructuredDocumentRegion
	 */
	public static IStructuredDocumentRegion getLastPhpStructuredDocumentRegion(
			IStructuredDocumentRegion currentStructuredDocumentRegion) {
		assert currentStructuredDocumentRegion != null;

		// get last region
		currentStructuredDocumentRegion = currentStructuredDocumentRegion.getPrevious();

		// search for last php block (then returns the last region)
		while (currentStructuredDocumentRegion != null
				&& currentStructuredDocumentRegion.getType() != PHPRegionContext.PHP_CONTENT) {
			currentStructuredDocumentRegion = currentStructuredDocumentRegion.getPrevious();
		}

		return currentStructuredDocumentRegion;
	}

	public static IFormatterCommonPrferences getFormatterCommonPrferences() {

		if (usedFormatter == null) {
			IConfigurationElement[] elements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor(FORMATTER_COMMON_PREFERENCE_EXT);
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement element = elements[i];
				if (element.getName().equals("processor")) { //$NON-NLS-1$
					try {
						usedFormatter = (IFormatterCommonPrferences) element.createExecutableExtension("class"); //$NON-NLS-1$
					} catch (CoreException e) {
					}
					;
				}
			}

			if (usedFormatter == null) {
				usedFormatter = FormatPreferencesSupport.getInstance();
			}
		}

		return usedFormatter;

	}
}
