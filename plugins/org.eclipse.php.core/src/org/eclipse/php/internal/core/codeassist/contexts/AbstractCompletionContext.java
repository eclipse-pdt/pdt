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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.CompletionCompanion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * This is an abstract completion context containing all common utilities.
 * 
 * @author michael
 */
public abstract class AbstractCompletionContext implements ICompletionContext {

	public static final int NONE = 0;
	public static final int TRAIT_NAME = 1;
	public static final int TRAIT_KEYWORD = 2;
	private CompletionCompanion companion;
	private CompletionRequestor requestor;
	private ISourceModule sourceModule;
	private int offset;

	private boolean namesCalculated = false;
	private String memberName = null;
	private String namespaceName = null;
	private String resolvedNamespaceName = null;
	private boolean absolute = false;

	@Override
	public void init(CompletionCompanion companion) {
		this.companion = companion;
	}

	public CompletionCompanion getCompanion() {
		return companion;
	}

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		this.requestor = requestor;
		this.sourceModule = sourceModule;
		this.offset = offset;

		try {
			if (companion.getPartitionType() != null) {
				return true;
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		return false;
	}

	@Override
	public boolean isExclusive() {
		return false;
	}

	/**
	 * Returns whether there are whitespace characters before the cursor where
	 * code assist was being invoked
	 * 
	 * @return <code>true</code> if there are whitespace characters before the
	 *         cursor
	 */
	public boolean hasWhitespaceBeforeCursor() {
		TextSequence statementText = getStatementText();

		// determine whether there are whitespaces before the cursor
		int statementLength = statementText.length();
		int statementEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength);
		return statementLength != statementEnd;
	}

	/**
	 * Returns whether there is a space character at offset position or not.<br>
	 * <b>IMPORTANT</b>: note that while {@link #getNextChar()} and
	 * {@link #getChar(int offset)} will return a space when cursor is at end of
	 * document, this method will return false when cursor is at end of
	 * companion.getDocument().
	 * 
	 * @return <code>true</code> if there is a space character at offset
	 *         position, false otherwise or false when cursor is at end of
	 *         document
	 */
	public boolean hasSpaceAtPosition(int offset) {
		try {
			return offset >= 0 && offset < companion.getDocument().getLength()
					&& companion.getDocument().getChar(offset) == ' ';
		} catch (BadLocationException e) {
			return false;
		}
	}

	/**
	 * Returns completion requestor
	 * 
	 * @return completion requestor (see {@link CompletionRequestor})
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	public final CompletionRequestor getCompletionRequestor() {
		return requestor;
	}

	/**
	 * Returns previous word before the cursor position
	 * 
	 * @throws BadLocationException
	 */
	public String getPreviousWord() throws BadLocationException {
		TextSequence statementText = getStatementText();

		int statementLength = statementText.length();
		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																									// whitespace
		int wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText,
				wordEnd, true);
		if (wordStart < 0 || wordEnd < 0) {
			return ""; //$NON-NLS-1$
		}
		String previousWord = statementText.subSequence(wordStart, wordEnd).toString();

		if (hasWhitespaceBeforeCursor()) {
			return previousWord;
		}

		wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, wordStart - 1); // read
																								// whitespace
		wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText, wordEnd,
				true);
		if (wordStart < 0 || wordEnd < 0) {
			return ""; //$NON-NLS-1$
		}
		previousWord = statementText.subSequence(wordStart, wordEnd).toString();

		return previousWord;
	}

	/**
	 * Returns previous word before the cursor position
	 * 
	 * @throws BadLocationException
	 */
	public String getPreviousWord(int times) throws BadLocationException {
		TextSequence statementText = getStatementText();

		int statementLength = statementText.length();
		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																									// whitespace
		int wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText,
				wordEnd, true);

		for (int i = 0; i < times - 1; i++) {
			statementLength = wordStart;
			wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																									// whitespace
			wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText,
					wordEnd, true);

		}
		if (wordStart < 0 || wordEnd < 0) {
			return ""; //$NON-NLS-1$
		}
		String previousWord = statementText.subSequence(wordStart, wordEnd).toString();

		if (hasWhitespaceBeforeCursor()) {
			return previousWord;
		}

		wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, wordStart - 1); // read
																								// whitespace
		wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText, wordEnd,
				true);
		if (wordStart < 0 || wordEnd < 0) {
			return ""; //$NON-NLS-1$
		}
		previousWord = statementText.subSequence(wordStart, wordEnd).toString();

		return previousWord;
	}

	/**
	 * Returns previous word before the cursor position
	 * 
	 * @throws BadLocationException
	 */
	public int getPreviousWordOffset(int times) throws BadLocationException {
		TextSequence statementText = getStatementText();

		int statementLength = statementText.length();
		int wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																									// whitespace
		int wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText,
				wordEnd, true);

		for (int i = 0; i < times - 1; i++) {
			statementLength = wordStart;
			wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																									// whitespace
			wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText,
					wordEnd, true);

		}
		if (wordStart < 0 || wordEnd < 0) {
			return wordStart;
		}

		if (hasWhitespaceBeforeCursor()) {
			return wordStart;
		}

		wordEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, wordStart - 1); // read
																								// whitespace
		wordStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText, wordEnd,
				true);
		// if (wordStart < 0 || wordEnd < 0) {
		// return wordStart;
		// }

		return wordStart;
	}

	/**
	 * Returns the word on which code assist was invoked
	 * 
	 * @return prefix (will never be null)
	 * @throws BadLocationException
	 */
	public String getPrefix() throws BadLocationException {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		TextSequence statementText = getStatementText();
		int statementLength = statementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(statementText, statementLength); // read
																										// whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(companion.getPHPVersion(), statementText,
				prefixEnd, true);
		return prefixStart < 0 ? "" : statementText.subSequence(prefixStart, prefixEnd).toString(); //$NON-NLS-1$
	}

	/**
	 * Returns the start of the word on which code assist was invoked
	 * 
	 * @return
	 * @throws BadLocationException
	 */
	public int getReplacementStart() throws BadLocationException {
		return offset - getPrefix().length();
	}

	/**
	 * Returns the end of the word on which code assist was invoked
	 * 
	 * @return
	 * @throws BadLocationException
	 */
	public int getReplacementEnd() throws BadLocationException {
		ITextRegion phpToken = companion.getPHPToken();
		int endOffset = companion.getRegionCollection().getStartOffset() + companion.getPHPScriptRegion().getStart()
				+ phpToken.getTextEnd();
		if (PHPPartitionTypes.isPHPQuotesState(phpToken.getType())) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=475671
			for (int index = offset; index < endOffset; index++) {
				char charAt = companion.getDocument().getChar(index);
				// Stop on quote (even in a heredoc section) or on whitespace:
				if (Character.isWhitespace(charAt) || charAt == '\'' || charAt == '"') {
					return index;
				}
			}
		}
		return endOffset;
	}

	/**
	 * Returns next PHP token after offset
	 * 
	 * @return PHP token
	 * @throws BadLocationException
	 */
	public ITextRegion getNextPHPToken() throws BadLocationException {
		ITextRegion phpToken = companion.getPHPToken();
		do {
			phpToken = companion.getPHPScriptRegion().getPHPToken(phpToken.getEnd());
			if (!PHPPartitionTypes.isPHPCommentState(phpToken.getType())
					&& phpToken.getType() != PHPRegionTypes.WHITESPACE) {
				break;
			}
		} while (phpToken.getEnd() < companion.getPHPScriptRegion().getLength());

		return phpToken;
	}

	public ITextRegion getNextPHPToken(int times) throws BadLocationException {
		ITextRegion phpToken = null;
		int offset = this.offset;
		while (times-- > 0) {
			phpToken = companion.getPHPToken(offset);
			do {
				phpToken = companion.getPHPScriptRegion().getPHPToken(phpToken.getEnd());
				if (!PHPPartitionTypes.isPHPCommentState(phpToken.getType())
						&& phpToken.getType() != PHPRegionTypes.WHITESPACE) {
					break;
				}
			} while (phpToken.getEnd() < companion.getPHPScriptRegion().getLength());
			if (phpToken == null) {
				return null;
			} else {
				offset = companion.getRegionCollection().getStartOffset() + companion.getPHPScriptRegion().getStart()
						+ phpToken.getEnd();
			}
		}

		return phpToken;
	}

	/**
	 * Returns next word after the cursor position
	 * 
	 * @throws BadLocationException
	 */
	public String getNextWord() throws BadLocationException {
		ITextRegion nextPHPToken = getNextPHPToken();
		return companion.getDocument().get(companion.getRegionCollection().getStartOffset()
				+ companion.getPHPScriptRegion().getStart() + nextPHPToken.getStart(), nextPHPToken.getTextLength());
	}

	public String getNextWord(int times) throws BadLocationException {
		ITextRegion nextPHPToken = getNextPHPToken(times);
		return companion.getDocument().get(companion.getRegionCollection().getStartOffset()
				+ companion.getPHPScriptRegion().getStart() + nextPHPToken.getStart(), nextPHPToken.getTextLength());
	}

	/**
	 * Returns next character after the cursor position (or ' ' if cursor
	 * position is at end of document)
	 * 
	 * @throws BadLocationException
	 */
	public char getNextChar() throws BadLocationException {
		return getChar(offset);
	}

	/**
	 * Returns character at the given offset (or ' ' if offset is at end of
	 * document)
	 * 
	 * @throws BadLocationException
	 */
	public char getChar(int offset) throws BadLocationException {
		// if the location is the end of the document, we return ' ' to
		// avoid the BadLocationException
		if (companion.getDocument().getLength() == offset) {
			return ' ';
		}
		return companion.getDocument().getChar(offset);
	}

	public int getUseTraitStatementContext() {
		return getUseTraitStatementContext(offset, companion.getStructuredDocumentRegion());
	}

	public int getUseTraitStatementContext(int offset, IStructuredDocumentRegion sdRegion) {
		List<String> types = new ArrayList<>();
		if (sdRegion == null) {
			sdRegion = companion.getStructuredDocumentRegion();
		}
		int documentOffset = offset;
		if (documentOffset == sdRegion.getEndOffset()) {
			documentOffset -= 1;
		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(documentOffset);

		ITextRegionCollection container = sdRegion;

		if (tRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
		}
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			tRegion = container.getRegionAtCharacterOffset(container.getStartOffset() + tRegion.getStart() - 1);
		}

		// This text region must be of type PhpScriptRegion:
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			IPHPScriptRegion phpScriptRegion = (IPHPScriptRegion) tRegion;

			try {
				// Set default starting position to the beginning of the
				// PhpScriptRegion:
				int startOffset = container.getStartOffset() + phpScriptRegion.getStart();

				// Now, search backwards for the statement start (in this
				// PhpScriptRegion):
				ITextRegion startTokenRegion;
				if (documentOffset == startOffset) {
					startTokenRegion = phpScriptRegion.getPHPToken(0);
				} else {
					startTokenRegion = phpScriptRegion.getPHPToken(offset - startOffset - 1);
				}
				// If statement start is at the beginning of the PHP script
				// region:
				while (true) {
					if (startTokenRegion.getStart() == 0) {
						return NONE;
					}
					if (!PHPPartitionTypes.isPHPCommentState(startTokenRegion.getType())
							&& startTokenRegion.getType() != PHPRegionTypes.WHITESPACE) {
						types.add(startTokenRegion.getType());
					}
					if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_OPEN
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_INSTEADOF
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_SEMICOLON
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_AS) {
						break;
					}

					startTokenRegion = phpScriptRegion.getPHPToken(startTokenRegion.getStart() - 1);
				}

			} catch (BadLocationException e) {
			}
		}
		if (types.size() == 1) {
			String type = types.get(0);
			if (type == PHPRegionTypes.PHP_CURLY_OPEN || type == PHPRegionTypes.PHP_INSTEADOF
					|| type == PHPRegionTypes.PHP_SEMICOLON) {
				return TRAIT_NAME;
			} else if (type == PHPRegionTypes.PHP_AS) {
				return TRAIT_KEYWORD;
			}
			if (type == PHPRegionTypes.PHP_INSTEADOF) {
				return TRAIT_KEYWORD;
			}
		} else if (types.size() == 2) {
			String type1 = types.get(0);
			String type = types.get(1);
			try {
				if (type == PHPRegionTypes.PHP_SEMICOLON && type1 == PHPRegionTypes.PHP_LABEL
						&& Character.isWhitespace(companion.getDocument().getChar(offset - 1))) {

					return TRAIT_KEYWORD;
				}
			} catch (BadLocationException e) {
			}
			if (type == PHPRegionTypes.PHP_CURLY_OPEN || type == PHPRegionTypes.PHP_INSTEADOF
					|| type == PHPRegionTypes.PHP_SEMICOLON || type1 == PHPRegionTypes.PHP_LABEL) {
				return TRAIT_NAME;
			}
		} else if (types.size() == 3) {
			String type = types.get(0);
			String type1 = types.get(1);
			String type2 = types.get(2);
			if (type == PHPRegionTypes.PHP_LABEL && type1 == PHPRegionTypes.PHP_LABEL
					&& type2 == PHPRegionTypes.PHP_SEMICOLON) {
				return TRAIT_KEYWORD;
			}
		} else if (types.size() == 4) {
			String type = types.get(0);
			String type1 = types.get(1);
			String type2 = types.get(2);
			if (type == PHPRegionTypes.PHP_LABEL && type1 == PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM
					&& type2 == PHPRegionTypes.PHP_LABEL) {
				return TRAIT_KEYWORD;
			}
		}

		return NONE;
	}

	public boolean isInUseTraitStatement() {
		return isInUseTraitStatement(offset, companion.getStructuredDocumentRegion());
	}

	private List<String> useTypes;

	public boolean isInUseTraitStatement(int offset, IStructuredDocumentRegion sdRegion) {
		if (companion.getPHPVersion().isLessThan(PHPVersion.PHP5_4)) {
			return false;
		}
		if (useTypes != null) {
			return true;
		}
		if (sdRegion == null) {
			sdRegion = companion.getStructuredDocumentRegion();
		}
		int documentOffset = offset;
		if (documentOffset == sdRegion.getEndOffset()) {
			documentOffset -= 1;
		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(documentOffset);

		ITextRegionCollection container = sdRegion;

		if (tRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
		}
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			tRegion = container.getRegionAtCharacterOffset(container.getStartOffset() + tRegion.getStart() - 1);
		}

		// This text region must be of type PhpScriptRegion:
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			IPHPScriptRegion phpScriptRegion = (IPHPScriptRegion) tRegion;

			try {
				// Set default starting position to the beginning of the
				// PhpScriptRegion:
				int startOffset = container.getStartOffset() + phpScriptRegion.getStart();

				// Now, search backwards for the statement start (in this
				// PhpScriptRegion):
				ITextRegion startTokenRegion;
				if (documentOffset == startOffset) {
					startTokenRegion = phpScriptRegion.getPHPToken(0);
				} else {
					startTokenRegion = phpScriptRegion.getPHPToken(offset - startOffset - 1);
				}
				// If statement start is at the beginning of the PHP script
				// region:
				while (true) {
					if (startTokenRegion.getStart() == 0) {
						return false;
					}
					if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_OPEN) {
						// Calculate starting position of the statement (it
						// should go right after this startTokenRegion):
						// startOffset += startTokenRegion.getEnd();
						TextSequence statementText1 = PHPTextSequenceUtilities.getStatement(
								startOffset + startTokenRegion.getStart() - 1, companion.getStructuredDocumentRegion(),
								true);
						startTokenRegion = phpScriptRegion
								.getPHPToken(startTokenRegion.getStart() - statementText1.length());
						if (startTokenRegion.getType() == PHPRegionTypes.PHP_USE) {
							String[] types = statementText1.toString().trim().substring(3).trim().split(","); //$NON-NLS-1$
							useTypes = new ArrayList<>();
							for (String type : types) {
								useTypes.add(type.trim());
							}
							return true;
						} else {
							return false;
						}
					} else if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_CLOSE) {
						return false;
					}

					startTokenRegion = phpScriptRegion.getPHPToken(startTokenRegion.getStart() - 1);
				}

			} catch (BadLocationException e) {
			}
		}

		return false;
	}

	/**
	 * This method get enclosing type element. It completely ignore statements
	 * without {
	 */
	protected IModelElement getEnclosingElement() {
		try {
			int offset = this.offset;
			PHPHeuristicScanner heuristicScanner = PHPHeuristicScanner.createHeuristicScanner(companion.getDocument(),
					offset, true);
			if (offset >= companion.getDocument().getLength()) {
				offset = companion.getDocument().getLength() - 1;
			}
			int open = heuristicScanner.findOpeningPeer(offset, PHPHeuristicScanner.UNBOUND, PHPHeuristicScanner.LBRACE,
					PHPHeuristicScanner.RBRACE);
			if (open > -1) {
				int close = heuristicScanner.findOpeningPeer(offset, PHPHeuristicScanner.UNBOUND,
						PHPHeuristicScanner.RBRACE, PHPHeuristicScanner.LBRACE);
				if (close > open && open == heuristicScanner.findOpeningPeer(close - 1, PHPHeuristicScanner.UNBOUND,
						PHPHeuristicScanner.LBRACE, PHPHeuristicScanner.RBRACE)) {
					open = heuristicScanner.findOpeningPeer(open - 1, PHPHeuristicScanner.UNBOUND,
							PHPHeuristicScanner.LBRACE, PHPHeuristicScanner.RBRACE);
				}
			}
			TextSequence statementText = getStatementText();
			int statementStart = statementText.length() > 0 ? statementText.getOriginalOffset(0) + 1 : -1;
			if (open < 0 && statementStart < 0) {
				return sourceModule.getElementAt(offset);
			}
			IModelElement elementAt = sourceModule.getElementAt(open);
			IModelElement elementAt2 = sourceModule.getElementAt(statementStart);
			if (elementAt == null && elementAt2 == null) {
				return sourceModule.getElementAt(offset);
			}

			if (elementAt instanceof ISourceReference && elementAt2 instanceof ISourceReference) {
				if (((ISourceReference) elementAt).getSourceRange().getOffset() > ((ISourceReference) elementAt2)
						.getSourceRange().getOffset()) {
					return elementAt;
				} else {
					return elementAt2;
				}
			} else if (elementAt != null) {
				return elementAt;
			}

			return elementAt2;
		} catch (BadLocationException e) {
			Logger.logException(e);
		} catch (ModelException e) {
			Logger.logException(e);
		}

		return null;
	}

	public List<String> getUseTypes() {
		return useTypes;
	}

	private void calculateNames() throws BadLocationException {
		if (namesCalculated) {
			return;
		}
		namesCalculated = true;
		String prefix = getPrefix();
		namespaceName = extractNamespace(prefix);
		memberName = extractMemberName(prefix);
		resolvedNamespaceName = null;

		if (prefix.length() > 0 && prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			absolute = true;
		} else {
			absolute = false;
		}

		if (absolute || isAbsolute()) {
			resolvedNamespaceName = namespaceName;
		} else if (namespaceName != null) {
			resolvedNamespaceName = resolveNamespace(namespaceName);
		}
	}

	/**
	 * Extracts namespace name.
	 * 
	 * @param prefix
	 * @return non-empty namespace name, null otherwise
	 */
	@Nullable
	private String extractNamespace(String prefix) {
		prefix = realPrefix(prefix);

		int pos = prefix.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
		if (pos == -1) {
			return null;
		}

		String name = prefix.substring(0, pos);
		if (name.length() == 0) {
			return null;
		}

		return name;
	}

	@NonNull
	private String extractMemberName(String prefix) {
		prefix = realPrefix(prefix);

		int pos = prefix.lastIndexOf(NamespaceReference.NAMESPACE_DELIMITER);
		if (pos == -1) {
			return prefix;
		}

		return prefix.substring(pos + 1);
	}

	public String getMemberName() throws BadLocationException {
		calculateNames();
		return memberName;
	}

	public String getNamespaceName() throws BadLocationException {
		calculateNames();
		return namespaceName;
	}

	public String getResolvedNamespaceName() throws BadLocationException {
		calculateNames();
		return resolvedNamespaceName;
	}

	public boolean isAbsoluteName() throws BadLocationException {
		calculateNames();
		return absolute;
	}

	/**
	 * Returns the qualifier. For global namespaces, it either returns null or
	 * <code>PHPCoreConstants.GLOBAL_NAMESPACE</code> depending on the value of
	 * parameter <code>useGlobal</code>. Other qualifiers will never be empty
	 * and never be prefixed by a backslash.
	 * 
	 * @param useGlobal
	 * @return non-empty qualifier or null
	 * @throws BadLocationException
	 */
	@Nullable
	public String getQualifier(boolean useGlobal) throws BadLocationException {
		if (isAbsoluteName() /* also recomputes absolute name */ || isAbsolute()) {
			if (resolvedNamespaceName == null && useGlobal) {
				return PHPCoreConstants.GLOBAL_NAMESPACE;
			} else {
				return resolvedNamespaceName;
			}
		} else {
			return resolvedNamespaceName;
		}
	}

	public boolean isAbsolute() {
		return false;
	}

	private String resolveNamespace(String name) throws BadLocationException {
		if (name == null) {
			return companion.getCurrentNamespace();
		}
		ISourceModule sourceModule = companion.getSourceModule();
		final ISourceRange validRange = companion.getCurrentNamespaceRange();

		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
		try {
			int searchEnd = name.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			String search;
			if (searchEnd < 1) {
				search = name;
			} else {
				search = name.substring(0, searchEnd);
			}
			if ("namespace".equalsIgnoreCase(search)) { // $NON-NLS-1$
				search = companion.getCurrentNamespace();
				if (searchEnd < 1) {
					return search;
				} else {
					return search + NamespaceReference.NAMESPACE_SEPARATOR + name.substring(searchEnd + 1);
				}
			}
			AliasResolver aliasResolver = new AliasResolver(validRange, search);
			moduleDeclaration.traverse(aliasResolver);
			if (aliasResolver.stop) {
				if (searchEnd < 1) {
					name = aliasResolver.found;
				} else {
					name = aliasResolver.found + NamespaceReference.NAMESPACE_SEPARATOR + name.substring(searchEnd + 1);
				}

				return name;
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		String current = companion.getCurrentNamespace();

		if (current != null) {
			return new StringBuilder(current).append(NamespaceReference.NAMESPACE_SEPARATOR).append(name).toString();
		}

		return name;
	}

	private String realPrefix(String prefix) {
		prefix = prefix.replaceAll("\\p{javaWhitespace}+", ""); //$NON-NLS-1$ //$NON-NLS-2$
		if (prefix.length() > 0 && prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			return prefix.substring(1);
		}

		return prefix;
	}

	class AliasResolver extends PHPASTVisitor {
		boolean stop = false;
		String found = null;
		ISourceRange validRange;
		String search;

		public AliasResolver(ISourceRange validRange, String search) {
			super();
			this.validRange = validRange;
			this.search = search;
		}

		@Override
		public boolean visitGeneral(ASTNode node) throws Exception {
			if (stop || node.sourceEnd() < validRange.getOffset()
					|| node.sourceStart() > validRange.getOffset() + validRange.getLength()) {
				return false;
			}
			return super.visitGeneral(node);
		}

		@Override
		public boolean visit(UseStatement s) throws Exception {

			if (s.getStatementType() != UseStatement.T_NONE) {
				return false;
			}
			for (UsePart part : s.getParts()) {
				if (part.getStatementType() != UseStatement.T_NONE) {
					continue;
				}
				String name = part.getAlias() != null ? part.getAlias().getName() : part.getNamespace().getName();
				if (name.equalsIgnoreCase(search)) {
					stop = true;
					found = part.getFullUseStatementName();
					break;
				}
			}

			return false;
		}
	}

	/**
	 * Returns the statement text that is before the cursor
	 * 
	 * @return statement text
	 * @see #isValid(ISourceModule, int, CompletionRequestor)
	 */
	@NonNull
	public TextSequence getStatementText() {
		return PHPTextSequenceUtilities.getStatement(offset, companion.getStructuredDocumentRegion(), true);
	}

	@Deprecated
	public final int getOffset() {
		return getCompanion().getOffset();
	}

	@Deprecated
	public final ISourceModule getSourceModule() {
		return getCompanion().getSourceModule();
	}

	@Deprecated
	public final IStructuredDocument getDocument() {
		return getCompanion().getDocument();
	}

	@Deprecated
	public final PHPVersion getPHPVersion() {
		return getCompanion().getPHPVersion();
	}

	@Deprecated
	public final IPHPScriptRegion getPHPScriptRegion() {
		return getCompanion().getPHPScriptRegion();
	}

	@Deprecated
	public final ITextRegion getPHPToken() throws BadLocationException {
		return getCompanion().getPHPToken();
	}

	@Deprecated
	public final ITextRegion getPHPToken(int offset) throws BadLocationException {
		return getCompanion().getPHPToken(offset);
	}

	@Deprecated
	public final String getCurrentNamespace() {
		return getCompanion().getCurrentNamespace();
	}

	@Deprecated
	public final boolean isGlobalNamespace() {
		return getCompanion().isGlobalNamespace();
	}

	@Deprecated
	public final ISourceRange getCurrentNamespaceRange() {
		return getCompanion().getCurrentNamespaceRange();
	}

	@Deprecated
	public final String getPartitionType() {
		return getCompanion().getPartitionType();
	}

	@Deprecated
	public final IStructuredDocumentRegion getStructuredDocumentRegion() {
		return getCompanion().getStructuredDocumentRegion();
	}
}