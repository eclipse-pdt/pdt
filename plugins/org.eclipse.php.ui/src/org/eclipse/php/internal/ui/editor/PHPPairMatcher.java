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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;
import org.eclipse.php.internal.core.format.Symbols;

/**
 * Helper class for match pairs of characters. (Formerly used WTP's
 * JavePairMatcher)
 */
public final class PHPPairMatcher implements ICharacterPairMatcher {

	private char[] fPairs;
	private IDocument fDocument;
	private int fOffset;

	private int fStartPos;
	private int fEndPos;
	private int fAnchor;

	/**
	 * Stores the source version state.
	 * 
	 * @since 3.1
	 */
	private boolean fHighlightAngularBrackets = false;

	public PHPPairMatcher(char[] pairs) {
		fPairs = pairs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.source.ICharacterPairMatcher#match(org.eclipse
	 * .jface.text.IDocument, int)
	 */
	public IRegion match(IDocument document, int offset) {

		fOffset = offset;

		if (fOffset < 0)
			return null;

		fDocument = document;

		if (fDocument != null && matchPairsAt() && fStartPos != fEndPos)
			return new Region(fStartPos, fEndPos - fStartPos + 1);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.ICharacterPairMatcher#getAnchor()
	 */
	public int getAnchor() {
		return fAnchor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.source.ICharacterPairMatcher#dispose()
	 */
	public void dispose() {
		clear();
		fDocument = null;
	}

	/*
	 * @see org.eclipse.jface.text.source.ICharacterPairMatcher#clear()
	 */
	public void clear() {
	}

	private boolean matchPairsAt() {

		int i;
		int pairIndex1 = fPairs.length;
		int pairIndex2 = fPairs.length;

		fStartPos = -1;
		fEndPos = -1;

		// get the char preceding the start position
		try {

			char prevChar = fDocument.getChar(Math.max(fOffset - 1, 0));
			// search for opening peer character next to the activation point
			for (i = 0; i < fPairs.length; i = i + 2) {
				if (prevChar == fPairs[i]) {
					fStartPos = fOffset - 1;
					pairIndex1 = i;
				}
			}

			// search for closing peer character next to the activation point
			for (i = 1; i < fPairs.length; i = i + 2) {
				if (prevChar == fPairs[i]) {
					fEndPos = fOffset - 1;
					pairIndex2 = i;
				}
			}

			if (fEndPos > -1) {
				fAnchor = RIGHT;
				fStartPos = searchForOpeningPeer(fEndPos,
						fPairs[pairIndex2 - 1], fPairs[pairIndex2], fDocument);
				if (fStartPos > -1)
					return true;
				fEndPos = -1;
			} else if (fStartPos > -1) {
				fAnchor = LEFT;
				fEndPos = searchForClosingPeer(fStartPos, fPairs[pairIndex1],
						fPairs[pairIndex1 + 1], fDocument);
				if (fEndPos > -1)
					return true;
				fStartPos = -1;
			}

		} catch (BadLocationException x) {
		}

		return false;
	}

	private int searchForClosingPeer(int offset, char openingPeer,
			char closingPeer, IDocument document) throws BadLocationException {
		boolean useGenericsHeuristic = openingPeer == '<';
		if (useGenericsHeuristic && !fHighlightAngularBrackets)
			return -1;
		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, offset, false);
		if (useGenericsHeuristic
				&& !isTypeParameterBracket(offset, document, scanner))
			return -1;

		return scanner.findClosingPeer(offset + 1, openingPeer, closingPeer);
	}

	private int searchForOpeningPeer(int offset, char openingPeer,
			char closingPeer, IDocument document) throws BadLocationException {
		boolean useGenericsHeuristic = openingPeer == '<';
		if (useGenericsHeuristic && !fHighlightAngularBrackets)
			return -1;

		PHPHeuristicScanner scanner = PHPHeuristicScanner
				.createHeuristicScanner(document, offset, false);
		int peer = scanner.findOpeningPeer(offset - 1,
				PHPHeuristicScanner.UNBOUND, openingPeer, closingPeer);
		if (peer == PHPHeuristicScanner.NOT_FOUND)
			return -1;
		if (useGenericsHeuristic
				&& !isTypeParameterBracket(peer, document, scanner))
			return -1;
		return peer;
	}

	/**
	 * Checks if the angular bracket at <code>offset</code> is a type parameter
	 * bracket.
	 * 
	 * @param offset
	 *            the offset of the opening bracket
	 * @param document
	 *            the document
	 * @param scanner
	 *            a java heuristic scanner on <code>document</code>
	 * @return <code>true</code> if the bracket is part of a type parameter,
	 *         <code>false</code> otherwise
	 * @since 3.1
	 */
	private boolean isTypeParameterBracket(int offset, IDocument document,
			PHPHeuristicScanner scanner) {
		/*
		 * type parameter come after braces (closing or opening), semicolons, or
		 * after a Type name (heuristic: starts with capital character, or after
		 * a modifier keyword in a method declaration (visibility, static,
		 * synchronized, final)
		 */

		try {
			IRegion line = document.getLineInformationOfOffset(offset);

			int prevToken = scanner.previousToken(offset - 1, line.getOffset());
			int prevTokenOffset = scanner.getPosition() + 1;
			String previous = prevToken == Symbols.TokenEOF ? null : document
					.get(prevTokenOffset, offset - prevTokenOffset).trim();

			if (prevToken == Symbols.TokenLBRACE
					|| prevToken == Symbols.TokenRBRACE
					|| prevToken == Symbols.TokenSEMICOLON
					|| prevToken == Symbols.TokenSYNCHRONIZED
					|| prevToken == Symbols.TokenSTATIC
					|| (prevToken == Symbols.TokenIDENT && isTypeParameterIntroducer(previous))
					|| prevToken == Symbols.TokenEOF)
				return true;
		} catch (BadLocationException e) {
			return false;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if <code>identifier</code> is an identifier
	 * that could come right before a type parameter list. It uses a heuristic:
	 * if the identifier starts with an upper case, it is assumed a type name.
	 * Also, if <code>identifier</code> is a method modifier, it is assumed that
	 * the angular bracket is part of the generic type parameter of a method.
	 * 
	 * @param identifier
	 *            the identifier to check
	 * @return <code>true</code> if the identifier could introduce a type
	 *         parameter list
	 * @since 3.1
	 */
	private boolean isTypeParameterIntroducer(String identifier) {
		return identifier.length() > 0
				&& (Character.isUpperCase(identifier.charAt(0))
						|| identifier.startsWith("final") //$NON-NLS-1$
						|| identifier.startsWith("public") //$NON-NLS-1$
						|| identifier.startsWith("public") //$NON-NLS-1$
						|| identifier.startsWith("protected") //$NON-NLS-1$
				|| identifier.startsWith("private")); //$NON-NLS-1$
	}
}
