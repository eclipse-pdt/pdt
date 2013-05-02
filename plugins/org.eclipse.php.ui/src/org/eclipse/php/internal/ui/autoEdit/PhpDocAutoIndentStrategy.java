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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.util.MethodOverrideTester;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.util.SuperTypeHierarchyCache;
import org.eclipse.php.ui.CodeGeneration;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditorExtension3;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

/**
 * TODO : move this auto strategy to DLTK? Auto indent strategy for Script doc
 * comments.
 */
public class PhpDocAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {
	private static final String PHPDOC_COMMENT_BLOCK_START = "/**"; //$NON-NLS-1$
	private static final String PHP_COMMENT_BLOCK_START = "/*"; //$NON-NLS-1$
	private static final String PHP_COMMENT_BLOCK_MID = " *"; //$NON-NLS-1$
	private static final String PHP_COMMENT_BLOCK_END = " */"; //$NON-NLS-1$

	/** The partitioning that this strategy operates on. */
	private final String fPartitioning;

	/**
	 * Creates a new Scriptdoc auto indent strategy for the given document
	 * partitioning.
	 * 
	 * @param partitioning
	 *            the document partitioning
	 */
	public PhpDocAutoIndentStrategy(String partitioning) {
		fPartitioning = partitioning;
	}

	/**
	 * Creates a new Scriptdoc auto indent strategy for the given document
	 * partitioning.
	 * 
	 * @param partitioning
	 *            the document partitioning
	 */
	public PhpDocAutoIndentStrategy() {
		this(PHPPartitionTypes.PHP_DEFAULT);
	}

	/**
	 * Copies the indentation of the previous line and adds a star. If the
	 * Scriptdoc just started on this line add standard method tags and close
	 * the Scriptdoc.
	 * 
	 * @param d
	 *            the document to work on
	 * @param c
	 *            the command to deal with
	 */
	private void indentAfterNewLine(IDocument d, DocumentCommand c) {

		int offset = c.offset;
		if (offset == -1 || d.getLength() == 0)
			return;

		try {
			int p = (offset == d.getLength() ? offset - 1 : offset);
			IRegion line = d.getLineInformationOfOffset(p);

			int lineOffset = line.getOffset();
			int firstNonWS = findEndOfWhiteSpace(d, lineOffset, offset);
			Assert.isTrue(firstNonWS >= lineOffset,
					"indentation must not be negative"); //$NON-NLS-1$

			StringBuffer buf = new StringBuffer(c.text);
			IRegion prefix = findPrefixRange(d, line);
			String indentation = d.get(prefix.getOffset(), prefix.getLength());
			int lengthToAdd = Math.min(offset - prefix.getOffset(),
					prefix.getLength());

			buf.append(indentation.substring(0, lengthToAdd));

			if (firstNonWS < offset) {
				if (d.getChar(firstNonWS) == '/') {
					// phpdoc started on this line
					buf.append(" * "); //$NON-NLS-1$

					if (TypingPreferences.closePhpdoc
							&& isNewComment(d, offset)) {
						c.shiftsCaret = false;
						c.caretOffset = c.offset + buf.length();
						String lineDelimiter = TextUtilities
								.getDefaultLineDelimiter(d);

						int replacementLength = 0;
						String restOfLine = d.get(p, replacementLength);
						String endTag = lineDelimiter + indentation + " */"; //$NON-NLS-1$

						// Check if we need end tag
						if (getCommentEnd(d, offset) > 0) {
							endTag = ""; //$NON-NLS-1$
						}
						if (TypingPreferences.addDocTags) {
							// we need to close the comment before computing
							// the correct tags in order to get the method
							d.replace(offset, replacementLength, endTag);

							// evaluate method signature
							ISourceModule unit = getCompilationUnit();

							if (unit != null) {
								try {
									ScriptModelUtil.reconcile(unit);
									// PHPUiPlugin.getDefault().getASTProvider().reconciled(null,
									// unit, new NullProgressMonitor());
									String partitionType = FormatterUtils
											.getPartitionType(
													(IStructuredDocument) d,
													c.offset);
									String commentBlockBody;
									if (partitionType
											.equals(PHPPartitionTypes.PHP_DOC)) {
										commentBlockBody = createScriptdocTags(
												d, c, indentation,
												lineDelimiter, unit);
									} else {// Multiline comment
										commentBlockBody = PHP_COMMENT_BLOCK_MID;
									}
									buf.append(restOfLine);
									// only add tags if they are non-empty - the
									// empty line has already been added above.
									if (commentBlockBody != null
											&& !commentBlockBody.trim().equals(
													"*")) //$NON-NLS-1$
										buf.append(commentBlockBody);
								} catch (CoreException e) {
									Logger.logException(e);
								}
							}
						} else {
							c.length = replacementLength;
							buf.append(restOfLine);
							buf.append(endTag);
						}
					}

				}
			}

			// move the caret behind the prefix, even if we do not have to
			// insert it.
			if (lengthToAdd < prefix.getLength())
				c.caretOffset = offset + prefix.getLength() - lengthToAdd;
			c.text = buf.toString();

		} catch (BadLocationException excp) {
			Logger.logException(excp);
		}
	}

	/**
	 * Returns the value of the given boolean-typed preference.
	 * 
	 * @param preference
	 *            the preference to look up
	 * @return the value of the given preference in the PHP plug-in's default
	 *         preference store
	 */
	private boolean isPreferenceTrue(String preference) {
		return PHPUiPlugin.getDefault().getPreferenceStore()
				.getBoolean(preference);
	}

	/**
	 * Returns the range of the Scriptdoc prefix on the given line in
	 * <code>document</code>. The prefix greedily matches the following regex
	 * pattern: <code>\w*\*\w*</code>, that is, any number of whitespace
	 * characters, followed by an asterisk ('*'), followed by any number of
	 * whitespace characters.
	 * 
	 * @param document
	 *            the document to which <code>line</code> refers
	 * @param line
	 *            the line from which to extract the prefix range
	 * @return an <code>IRegion</code> describing the range of the prefix on the
	 *         given line
	 * @throws BadLocationException
	 *             if accessing the document fails
	 */
	private IRegion findPrefixRange(IDocument document, IRegion line)
			throws BadLocationException {
		int lineOffset = line.getOffset();
		int lineEnd = lineOffset + line.getLength();
		int indentEnd = findEndOfWhiteSpace(document, lineOffset, lineEnd);
		if (indentEnd < lineEnd && document.getChar(indentEnd) == '*') {
			indentEnd++;
			while (indentEnd < lineEnd && document.getChar(indentEnd) == ' ')
				indentEnd++;
		}
		return new Region(lineOffset, indentEnd - lineOffset);
	}

	/**
	 * 
	 * Returns the range of the Scriptdoc prefix on the given line in
	 * <code>document</code>. The prefix greedily matches the following regex
	 * pattern: <code>\w*</code>, that is, any number of whitespace characters,
	 * until non first non white space character.
	 * 
	 * @param document
	 *            the document to which <code>line</code> refers
	 * @param line
	 *            the line from which to extract the prefix range
	 * @return an <code>IRegion</code> describing the range of the prefix on the
	 *         given line
	 * @throws BadLocationException
	 *             if accessing the document fails
	 */
	private IRegion findCommentBlockStartPrefixRange(IDocument document,
			IRegion line) throws BadLocationException {
		int lineOffset = line.getOffset();
		int lineEnd = lineOffset + line.getLength();
		int indentEnd = findEndOfWhiteSpace(document, lineOffset, lineEnd);
		return new Region(lineOffset, indentEnd - lineOffset);
	}

	/**
	 * Creates the Scriptdoc tags for newly inserted comments.
	 * 
	 * @param document
	 *            the document
	 * @param command
	 *            the command
	 * @param indentation
	 *            the base indentation to use
	 * @param lineDelimiter
	 *            the line delimiter to use
	 * @param unit
	 *            the compilation unit shown in the editor
	 * @return the tags to add to the document
	 * @throws CoreException
	 *             if accessing the PHP model fails
	 * @throws BadLocationException
	 *             if accessing the document fails
	 */

	private String createScriptdocTags(IDocument document,
			DocumentCommand command, String indentation, String lineDelimiter,
			ISourceModule unit) throws CoreException, BadLocationException {
		// searching for next element's start point
		int nextElementOffset = getEndOfWhiteSpacesOffset(document,
				command.caretOffset, document.getLength());

		IModelElement element = unit.getElementAt(nextElementOffset);
		if (element == null)
			return null;

		// Checking the element we got is not the element within the "/**" was
		// typed
		if (getCodeDataOffset(element) <= command.caretOffset) {
			return null;
		}
		int type = element != null ? element.getElementType() : -1;
		if (type != IModelElement.METHOD && type != IModelElement.TYPE
				&& type != IModelElement.FIELD) {
			assert false;
			return null;
		}

		String comment = null;
		try {
			switch (type) {
			case IModelElement.TYPE:
				comment = createTypeTags(document, command, indentation,
						lineDelimiter, (IType) element);
				break;
			case IModelElement.FIELD:
				comment = creatFieldTags(document, command, indentation,
						lineDelimiter, (IField) element);
				break;
			case IModelElement.METHOD:
				comment = createMethodTags(document, command, indentation,
						lineDelimiter, (IMethod) element);
				break;
			default:
				comment = createDefaultComment(lineDelimiter);
			}
		} catch (CoreException e) {
			comment = createDefaultComment(lineDelimiter);
			Logger.logException(e);
		}

		return indentPattern(comment, indentation, lineDelimiter);
	}

	private int getEndOfWhiteSpacesOffset(IDocument document, int offset,
			int end) throws BadLocationException {
		while (offset < end) {
			if (!Character.isWhitespace(document.getChar(offset))) {
				return offset;
			}
			offset++;
		}
		return -1;
	}

	/**
	 * Removes start and end of a comment and corrects indentation and line
	 * delimiters.
	 * 
	 * @param comment
	 *            the computed comment
	 * @param indentation
	 *            the base indentation
	 * @param project
	 *            the PHP project for the formatter settings, or
	 *            <code>null</code> for global preferences
	 * @param lineDelimiter
	 *            the line delimiter
	 * @return a trimmed version of <code>comment</code>
	 */
	private String prepareTemplateComment(String comment, String indentation,
			IScriptProject project, String lineDelimiter) {
		// trim comment start and end if any
		if (comment.endsWith("*/")) //$NON-NLS-1$
			comment = comment.substring(0, comment.length() - 2);
		comment = comment.trim();
		if (comment.startsWith("/*")) { //$NON-NLS-1$
			if (comment.length() > 2 && comment.charAt(2) == '*') {
				comment = comment.substring(3); // remove '/**'
			} else {
				comment = comment.substring(2); // remove '/*'
			}
		}
		// trim leading spaces, but not new lines
		int nonSpace = 0;
		int len = comment.length();
		while (nonSpace < len
				&& Character.getType(comment.charAt(nonSpace)) == Character.SPACE_SEPARATOR)
			nonSpace++;
		comment = comment.substring(nonSpace);

		return comment;
		// TODO : should fix the formatter step
		// return Strings.changeIndent(comment, 0, project, indentation,
		// lineDelimiter);
	}

	private String createTypeTags(IDocument document, DocumentCommand command,
			String indentation, String lineDelimiter, IType type)
			throws CoreException, BadLocationException {
		String comment = createTypeComment(type, lineDelimiter);
		if (comment != null) {
			comment = comment.trim();
			return prepareTemplateComment(comment.trim(), indentation,
					type.getScriptProject(), lineDelimiter);
		}
		return null;
	}

	private String creatFieldTags(IDocument document, DocumentCommand command,
			String indentation, String lineDelimiter, IField field)
			throws CoreException, BadLocationException {
		String comment = createFieldComment(field, lineDelimiter);
		if (comment != null) {
			comment = comment.trim();
			return prepareTemplateComment(comment.trim(), indentation,
					field.getScriptProject(), lineDelimiter);
		}
		return null;
	}

	private String createMethodTags(IDocument document,
			DocumentCommand command, String indentation, String lineDelimiter,
			IMethod method) throws CoreException, BadLocationException {
		// IMethod inheritedMethod = getInheritedMethod(method);
		String comment = createMethodComment(method, lineDelimiter);// CodeGeneration.getMethodComment(method,
																	// inheritedMethod,
																	// lineDelimiter);
		if (comment != null) {
			comment = comment.trim();
			return prepareTemplateComment(comment, indentation,
					method.getScriptProject(), lineDelimiter);
		}
		return null;
	}

	/**
	 * Unindents a typed slash ('/') if it forms the end of a comment.
	 * 
	 * @param d
	 *            the document
	 * @param c
	 *            the command
	 */
	private void indentAfterCommentEnd(IDocument d, DocumentCommand c) {
		if (c.offset < 2 || d.getLength() == 0) {
			return;
		}

		// Check the case that '/' is printed inside of a comment block
		try {
			if ("* ".equals(d.get(c.offset - 2, 2))) { //$NON-NLS-1$

				int offset = c.offset;

				int fixedOffset = offset;
				if (offset == d.getLength()) {
					fixedOffset -= 1;
				}
				IStructuredDocumentRegion sdRegion = ((IStructuredDocument) d)
						.getRegionAtCharacterOffset(fixedOffset);

				ITextRegion tRegion = sdRegion
						.getRegionAtCharacterOffset(fixedOffset);

				int regionOffset = offset;
				if (tRegion instanceof ITextRegionContainer) {
					tRegion = ((ITextRegionContainer) tRegion)
							.getRegionAtCharacterOffset(fixedOffset);
					regionOffset -= (sdRegion.getStartOffset(tRegion) + tRegion
							.getStart());
				}
				int regionStart = sdRegion.getStartOffset(tRegion);

				if (tRegion != null && tRegion instanceof IPhpScriptRegion) {
					IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
					regionOffset -= scriptRegion.getStart();

					ITextRegion commentRegion = scriptRegion
							.getPhpToken(regionOffset);
					int phpScriptEndOffset = scriptRegion.getLength();
					boolean isSpaceDeletionNeeded = false;
					do {
						int currentRegionEndOffset = commentRegion.getEnd();
						commentRegion = scriptRegion
								.getPhpToken(currentRegionEndOffset);
						String tokenType = commentRegion.getType();
						if (tokenType.equals(PHPRegionTypes.PHP_COMMENT_END)
								|| PHPRegionTypes.PHPDOC_COMMENT_END
										.equals(tokenType)) {
							break;
						} else if (currentRegionEndOffset >= phpScriptEndOffset) {
							isSpaceDeletionNeeded = true;
							break;
						}
					} while (true);

					if (isSpaceDeletionNeeded) {
						// perform the actual work
						c.length++;
						c.offset--;
						return;
					}
				}
			}
		} catch (BadLocationException excp) {
			Logger.logException(excp);
		}
	}

	/**
	 * Guesses if the command operates within a newly created Scriptdoc comment
	 * or not. If in doubt, it will assume that the Scriptdoc is new.
	 * 
	 * @param document
	 *            the document
	 * @param commandOffset
	 *            the command offset
	 * @return <code>true</code> if the comment should be closed,
	 *         <code>false</code> if not
	 */
	private boolean isNewComment(IDocument document, int commandOffset) {

		try {
			int lineIndex = document.getLineOfOffset(commandOffset) + 1;
			if (lineIndex >= document.getNumberOfLines())
				return true;

			IRegion line = document.getLineInformation(lineIndex);
			ITypedRegion partition = TextUtilities.getPartition(document,
					fPartitioning, commandOffset, false);
			int partitionEnd = partition.getOffset() + partition.getLength();
			if (line.getOffset() >= partitionEnd)
				return false;

			if (document.getLength() == partitionEnd)
				return true; // partition goes to end of document - probably a
								// new comment

			String comment = document.get(partition.getOffset(),
					partition.getLength());
			if (comment.indexOf("/*", 2) != -1) //$NON-NLS-1$
				return true; // enclosed another comment -> probably a new
								// comment

			return false;

		} catch (BadLocationException e) {
			return false;
		}
	}

	private boolean isSmartMode() {
		IWorkbenchPage page = PHPUiPlugin.getActivePage();
		if (page != null) {
			IEditorPart part = page.getActiveEditor();
			if (part instanceof ITextEditorExtension3) {
				ITextEditorExtension3 extension = (ITextEditorExtension3) part;
				return extension.getInsertMode() == ITextEditorExtension3.SMART_INSERT;
			}
		}
		return false;
	}

	/*
	 * @see IAutoIndentStrategy#customizeDocumentCommand
	 */
	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {

		if (!isSmartMode())
			return;

		if (command.text != null) {
			if (command.length == 0) {
				// get legal end of line set
				String[] lineDelimiters = document.getLegalLineDelimiters();
				// get the the last index of end of line in the command
				int index = TextUtilities
						.endsWith(lineDelimiters, command.text);
				int offset = command.offset;

				// if end of line exists in the command
				if (index > -1) {
					// ends with line delimiter
					if (lineDelimiters[index].equals(command.text)) {
						try {
							IStructuredDocumentRegion sdRegion = ((IStructuredDocument) document)
									.getRegionAtCharacterOffset(offset);
							// in case we're at the end of file, go on char back
							// to be in region
							int fixedOffset = offset;
							if (offset == document.getLength()) {
								fixedOffset -= 1;
							}
							ITextRegion tRegion = sdRegion
									.getRegionAtCharacterOffset(fixedOffset);

							int regionOffset = offset;
							// if netsed html/php structure exists
							if (tRegion instanceof ITextRegionContainer) {
								tRegion = ((ITextRegionContainer) tRegion)
										.getRegionAtCharacterOffset(fixedOffset);
								// update region offset for in order to get
								// phpdoc region later
								regionOffset -= (sdRegion
										.getStartOffset(tRegion) + tRegion
										.getStart());
							}

							if (tRegion != null
									&& tRegion instanceof IPhpScriptRegion) {
								IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;

								// update region offset for in order to get
								// phpdoc region later
								regionOffset -= scriptRegion.getStart();

								ITextRegion commentRegion = scriptRegion
										.getPhpToken(regionOffset);

								String tokenType = commentRegion.getType();
								if (document.getLength() == offset
										&& (tokenType
												.equals(PHPRegionTypes.PHPDOC_COMMENT_END) || tokenType
												.equals(PHPRegionTypes.PHP_COMMENT_END))
										&& document.get(offset - 2, 2).equals(
												"*/")) { //$NON-NLS-1$

									ITextRegion region = commentRegion;
									// go up in document and search for the
									// first line containing
									// PHPDOC_COMMENT_START/PHP_COMMENT_START
									// tag
									do {
										if (region.getType() == PHPRegionTypes.PHPDOC_COMMENT_START
												|| region.getType() == PHPRegionTypes.PHP_COMMENT_START
												|| region.getStart() <= scriptRegion
														.getStart()) {
											break;
										}
										// get previous region
										region = scriptRegion
												.getPhpToken(region.getStart()
														- lineDelimiters[index]
																.length());

									} while (true);

									// get the line region
									IRegion line = document
											.getLineInformationOfOffset(region
													.getStart());
									StringBuffer buf = new StringBuffer(
											command.text);
									// extract indentation from the found line
									IRegion prefix = findCommentBlockStartPrefixRange(
											document, line);
									// build indentation based on the prefix
									// length
									String indentation = document.get(
											prefix.getOffset(),
											prefix.getLength());
									buf.append(indentation);
									// perform the actual work
									command.shiftsCaret = false;
									command.caretOffset = command.offset
											+ buf.length();
									command.text = buf.toString();
									return;
								}
							}
						} catch (BadLocationException e) {
							// May be caused by concurrent threads. Can be
							// ignored.
						}

						// just the line delimiter
						if (lineDelimiters[index].equals(command.text)) {
							indentAfterNewLine(document, command);
						}
						return;
					}
				}
			}

			if (command.text.equals("/")) { //$NON-NLS-1$
				indentAfterCommentEnd(document, command);
				return;
			}
		}
	}

	/**
	 * Returns the method inherited from, <code>null</code> if method is newly
	 * defined.
	 * 
	 * @param method
	 *            the method being written
	 * @return the ancestor method, or <code>null</code> if none
	 * @throws ModelException
	 *             if accessing the PHP model fails
	 */
	private static IMethod getInheritedMethod(IMethod method)
			throws ModelException {
		IType declaringType = method.getDeclaringType();
		if (null == declaringType)
			return null;
		MethodOverrideTester tester = SuperTypeHierarchyCache
				.getMethodOverrideTester(declaringType);
		return tester.findOverriddenMethod(method, true);
	}

	/**
	 * Returns the compilation unit of the compilation unit editor invoking the
	 * <code>AutoIndentStrategy</code>, might return <code>null</code> on error.
	 * 
	 * @return the compilation unit represented by the document
	 */
	private static ISourceModule getCompilationUnit() {

		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null)
			return null;

		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			return null;

		IEditorPart editor = page.getActiveEditor();
		if (editor == null)
			return null;

		IWorkingCopyManager manager = PHPUiPlugin.getWorkingCopyManager();
		ISourceModule unit = manager.getWorkingCopy(editor.getEditorInput());
		if (unit == null)
			return null;

		return unit;
	}

	/**
	 * Finds the offset of the closing bracket of the comment block which starts
	 * on "offset" The method search until EOF or another comment block begins
	 * 
	 * @return the closing bracket offset, or negative number if no relevant
	 *         closing bracket was found TODO when there are // at the end of
	 *         line this method will throw BadLocationException
	 */

	private int getCommentEnd(IDocument d, int offset)
			throws BadLocationException {
		int endOfDoc = d.getLength();
		while (offset + 1 < endOfDoc) {
			if (d.getChar(offset) == '*' && d.getChar(offset + 1) == '/') {
				return offset + 1;
			} else if (d.getChar(offset) == '/' && d.getChar(offset + 1) == '*') {
				return -1;
			}
			offset++;
		}
		return -2;
	}

	private String createTypeComment(IType type, String lineDelimiter)
			throws CoreException {
		// String[] typeParameterNames=
		// StubUtility.getTypeParameterNames(type.getFields());
		return CodeGeneration.getTypeComment(type.getScriptProject(),
				type.getTypeQualifiedName(), /* typeParameterNames */null,
				lineDelimiter);
	}

	private String createMethodComment(IMethod meth, String lineDelimiter)
			throws CoreException {
		IType declaringType = meth.getDeclaringType();
		IMethod overridden = null;

		if (!meth.isConstructor() && null != declaringType) {
			try {
				ITypeHierarchy hierarchy = SuperTypeHierarchyCache
						.getTypeHierarchy(declaringType);
				MethodOverrideTester tester = new MethodOverrideTester(
						declaringType, hierarchy);
				overridden = tester.findOverriddenMethod(meth, true);
			} catch (CoreException e) {
				Logger.logException(e);
			}

		}
		return CodeGeneration.getMethodComment(meth, overridden, lineDelimiter);
	}

	private String createFieldComment(IField field, String lineDelimiter)
			throws ModelException, CoreException {
		return CodeGeneration.getFieldComment(field.getScriptProject(), field,
				lineDelimiter);
	}

	private String createDefaultComment(String lineDelimiter) {
		return PHPDOC_COMMENT_BLOCK_START + lineDelimiter
				+ PHP_COMMENT_BLOCK_MID + lineDelimiter + PHP_COMMENT_BLOCK_END;
	}

	/**
	 * Calculates the leading string to be used as indentation prefix
	 * 
	 * @param document
	 *            The IStructuredDocument that we are working on
	 * @param modelElem
	 *            A PHPFileData that need to be documented
	 * 
	 * @return String to be used as leading indentation
	 */
	private String getIndentString(IDocument document, IModelElement modelElem)
			throws BadLocationException {
		int elementOffset = 0;
		String leadingString = null;
		try {
			elementOffset = getCodeDataOffset(modelElem);
			int lineStartOffset = document.getLineInformationOfOffset(
					elementOffset).getOffset();
			leadingString = document.get(lineStartOffset, elementOffset
					- lineStartOffset);
		} catch (ModelException e) {
			Logger.logException(e);
			return null;
		}
		// replacing all non-spaces/tabs to single-space, in order to get
		// "char-clean" prefix
		leadingString = leadingString.replaceAll("[^\\s]", " "); //$NON-NLS-1$ //$NON-NLS-2$

		return leadingString;
	}

	private int getCodeDataOffset(IModelElement modelElem)
			throws ModelException {
		if (modelElem instanceof ISourceModule) {
			ISourceReference primaryModelElem = (ISourceReference) (((ISourceModule) modelElem)
					.getPrimaryElement());// .getPHPBlocks();
			return primaryModelElem != null ? primaryModelElem.getSourceRange()
					.getOffset()
					+ primaryModelElem.getSourceRange().getLength()/*
																	 * getPHPStartTag
																	 * ( ).
																	 * getEndPosition
																	 * ()
																	 */: -1;
		}
		if (modelElem instanceof ISourceReference) {
			int dataOffset = ((ISourceReference) modelElem).getSourceRange()
					.getOffset();
			return dataOffset;
		}
		assert false;
		return -1;
	}

	private String indentPattern(String originalPattern, String indentation,
			String lineDelim) {
		String delimPlusIndent = lineDelim + indentation;
		String indentedPattern = originalPattern.replaceAll(lineDelim,
				delimPlusIndent);

		return indentedPattern;
	}
}
