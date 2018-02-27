/*******************************************************************************
 * Copyright (c) 2006, 2015, 2018 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.compiler.ast.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.eclipse.php.internal.core.Logger;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.php.core.ast.nodes.IDocumentorLexer;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.core.compiler.ast.nodes.Scalar;

@SuppressWarnings({"unused", "nls"})

%%

%class DocumentorLexer
%public
%implements IDocumentorLexer
%unicode
%line

%eofclose

%caseless

%function next_token

%standalone

%state ST_IN_FIRST_LINE
%state ST_IN_SHORT_DESC
%state ST_IN_LONG_DESC
%state ST_IN_TAGS


%{
	private String shortDesc;
	private String longDesc;
	private ArrayList<PHPDocTag> tagList;
	private TagKind currTagKind;
	private int tagPosition;
	private String matchedTagName;
	private Scalar indentedTagName;
	private StringBuilder sBuffer;
	private int numOfLines;
	private List<Scalar> textList;

	@Override
	public PHPDocBlock parse() {
		oldString = null;
		shortDesc = "";
		longDesc = "";
		tagList = new ArrayList<>();
		currTagKind = null;
		tagPosition = 0;
		matchedTagName = "";
		indentedTagName = null;
		sBuffer = new StringBuilder();
		numOfLines = 1;
		textList = new ArrayList<>();

		useOldString = true;
		int start = zzStartRead - _zzPushbackPos;

		// start parsing
		try {
			next_token();
		} catch (IOException e) {
			Logger.logException(e);
		}
		if (!tagList.isEmpty() && !textList.isEmpty()) {
			Scalar lastText = textList.get(textList.size() - 1);
			PHPDocTag lastTag = tagList.get(tagList.size() - 1);
			if (lastText.sourceEnd() >= lastTag.sourceEnd()) {
				textList.remove(textList.size() - 1);
				lastTag.getTexts().add(lastText);
				// replace last tag by a new one with updated source range and internal references
				tagList.set(tagList.size() - 1, new PHPDocTag(lastTag.sourceStart(), lastText.sourceEnd(),
						lastTag.getTagKind(), lastTag.getMatchedTag(), lastTag.getValue(), lastTag.getTagText(), lastTag.getTexts()));
			}
		}

		PHPDocTag[] tags = new PHPDocTag[tagList.size()];
		tagList.toArray(tags);

		PHPDocBlock rv = new PHPDocBlock(start, zzMarkedPos - _zzPushbackPos,
				shortDesc, longDesc, tags, textList);

		return rv;
	}

	private void startTagsState(TagKind firstState, int position, String tagName, Scalar indentedTag) {
		updateStartPos();
		handleDesc();
		currTagKind = firstState;
		tagPosition = position - _zzPushbackPos;
		matchedTagName = tagName;
		indentedTagName = indentedTag;
		sBuffer = new StringBuilder();
		yybegin(ST_IN_TAGS);
	}

	private int findTagPosition() {
		for (int i = zzStartRead; i < zzMarkedPos; i++) {
			if (zzBuffer[i] == '@' || zzBuffer[i] == '{') {
				return i;
			}
		}
		return -1;
	}

	// should only be used with ^{LINESTART}... rules:
	private int findIndentedLineStartTagPosition() {
		// we want to keep all blanks after
		// '*', see also token LINESTART
		for (int i = zzStartRead; i < zzMarkedPos; i++) {
			if (zzBuffer[i] == '*') {
				return i + 1;
			}
			if (zzBuffer[i] == '@' || zzBuffer[i] == '{') {
				break;
			}
		}
		return zzStartRead;
	}

	private void setNewTag(TagKind newTag, int position, String tagName, Scalar indentedTag) {
		updateStartPos();
		setTagValue();

		sBuffer = new StringBuilder();
		currTagKind = newTag;
		tagPosition = position - _zzPushbackPos;
		matchedTagName = tagName;
		indentedTagName = indentedTag;
	}

	private void setTagValue() {
		String value = sBuffer.toString();
		// special case for backward compatibility
		if (currTagKind == TagKind.DESC) {
			shortDesc = shortDesc + value;
			shortDesc = shortDesc.trim();
			return;
		}

		PHPDocTag basicPHPDocTag = new PHPDocTag(tagPosition, zzStartRead - _zzPushbackPos, currTagKind, matchedTagName,
				value, indentedTagName, getTexts(tagPosition, zzStartRead - _zzPushbackPos, true));
		tagList.add(basicPHPDocTag);
	}

	@NonNull
	private List<Scalar> getTexts(int start, int end, boolean remove) {
		List<Scalar> result = new ArrayList<>();
		for (Iterator<Scalar> iterator = textList.iterator(); iterator.hasNext();) {
			Scalar scalar = iterator.next();
			if (scalar.sourceStart() >= start && scalar.sourceEnd() <= end) {
				result.add(scalar);
				if (remove) {
					iterator.remove();
				}
			}
		}
		// Scalar[] texts = new Scalar[result.size()];
		// result.toArray(texts);
		return result;
	}

	private void appendText() {
		if (oldString != null) {
			sBuffer.append(oldString);
		}
		String sb = new String(zzBuffer, startPos, zzMarkedPos - startPos);
		if (oldString != null) {
			addText(oldString, sb);
		} else {
			addText(sb);
		}
		sBuffer.append(sb);
		updateStartPos();
	}

	private void addText(String oldString, String string) {
		textList.add(new Scalar(startPos - _zzPushbackPos - oldString.length(), startPos - _zzPushbackPos + string.length(), oldString + string,
				Scalar.TYPE_STRING));
	}

	private void addText(String string) {
		textList.add(new Scalar(startPos - _zzPushbackPos, startPos - _zzPushbackPos + string.length(), string,
				Scalar.TYPE_STRING));
	}

	private void handleDesc() {
		if (zzLexicalState == ST_IN_SHORT_DESC || zzLexicalState == ST_IN_FIRST_LINE) {
			shortDesc = sBuffer.toString().trim();
		} else {
			longDesc = sBuffer.toString().trim();
		}

		sBuffer = new StringBuilder();
	}

	private void startLongDescState(boolean withNewLine) {
		handleDesc();
		updateStartPos();
		if (!withNewLine) {
			addText("");
		}
		yybegin(ST_IN_LONG_DESC);
	}

	private void handleNewLine() {
		appendText();
		if (numOfLines == 4) {
			int firstLineEnd = sBuffer.indexOf("\n", 1);
			if (firstLineEnd == -1) {
				firstLineEnd = sBuffer.indexOf("\r", 1);
			}
			shortDesc = sBuffer.substring(0, firstLineEnd);
			shortDesc = shortDesc.trim();
			sBuffer.delete(0, firstLineEnd);
			yybegin(ST_IN_LONG_DESC);
		} else {
			numOfLines++;
		}
	}
	private void appendLastText() {
		String sb = new String(zzBuffer, startPos, zzMarkedPos - startPos - 2);
		// ignore last text if it only contains white spaces
		if (StringUtils.isNotBlank(sb)) {
			addText(sb);
			sBuffer.append(sb);
		}
		updateStartPos();
	}

	int maxNumberofLines = 4;

	private void handleDocEnd_shortDesc() {
		appendLastText();
		if (numOfLines == maxNumberofLines) {
			int firstLineEnd = sBuffer.indexOf("\n", 1);
			if (firstLineEnd == -1) {
				firstLineEnd = sBuffer.indexOf("\r", 1);
			}
			shortDesc = sBuffer.substring(0, firstLineEnd);
			shortDesc = shortDesc.trim();
			sBuffer.delete(0, firstLineEnd);
			longDesc = sBuffer.toString().trim();
		} else {
			shortDesc = sBuffer.toString().trim();
		}
	}

	private void handleDocEnd_longDesc() {
		appendLastText();
		longDesc = sBuffer.toString().trim();
	}

	private void handleDocEnd_inTags() {
		appendLastText();
		setTagValue();
	}

	/**
	 * Resets the {@code PhpAstLexer} properties to given parameters. Be
	 * careful, method {@link #next_token()} also caches those properties using
	 * internal variables (zzCurrentPosL, zzMarkedPosL, zzBufferL, zzEndReadL)
	 * that should be accordingly resetted by the lexical rules calling
	 * {@link #reset(java.io.Reader, char[], int[])}. Also be careful that those
	 * internal variables could change from one version of JFlex to another.
	 *
	 * @param reader
	 * @param buffer
	 * @param parameters
	 */
	@Override
	public void reset(java.io.Reader reader, char[] buffer, int[] parameters) {
		// (re)set all properties like in yyreset(java.io.Reader reader)

		this.zzReader = reader;
		this.zzBuffer = buffer;
		this.zzMarkedPos = parameters[0];
		this._zzPushbackPos = parameters[1];
		this.zzCurrentPos = parameters[2];
		this.zzStartRead = parameters[3];
		this.zzEndRead = parameters[4];
		this.yyline = parameters[5];
		// XXX: never used
		this.yychar = 0;
		// XXX: never used
		this.yycolumn = 0;
		this.zzAtEOF = parameters[7] != 0;
		this.zzEOFDone = parameters[8] != 0;
		this.zzFinalHighSurrogate = parameters[9];
		oldString = null;

		// NB: zzAtBOL and zzLexicalState won't be set using (directly) the
		// array "parameters", we always restart this scanner with the YYINITIAL
		// state
		this.zzAtBOL = this.zzAtEOF ? false : true;
		this.zzLexicalState = YYINITIAL;
	}

	@Override
	public int[] getParameters() {
		return new int[] { zzMarkedPos, _zzPushbackPos, zzCurrentPos,
			zzStartRead, zzEndRead, yyline, zzAtBOL ? 1 : 0,
			zzAtEOF ? 1 : 0, zzEOFDone ? 1 : 0, zzFinalHighSurrogate };
	}

	@Override
	public char[] getBuffer() {
		return zzBuffer;
	}

%}

TABS_AND_SPACES=[ \t]*
ANY_CHAR=(.|[\n])
NEWLINE=("\r"|"\n"|"\r\n")
LINESTART=({TABS_AND_SPACES}"*"?{TABS_AND_SPACES})
EMPTYLINE=({LINESTART}{TABS_AND_SPACES})
PHPDOCSTART="/**"{TABS_AND_SPACES}
TAG_NAME_CHARS=[^ \n\r\t*]+


%%

<YYINITIAL> {
	^{PHPDOCSTART}({NEWLINE}) {
		updateStartPos();
		yybegin(ST_IN_SHORT_DESC);
	}
	^{PHPDOCSTART} {
		yypushback(yylength());
		updateStartPos();
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=474332
		// look for @tags on the first line of this PHPDoc block
		yybegin(ST_IN_FIRST_LINE);
	}
}

<YYINITIAL>{ANY_CHAR}     {}

<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@"{TAG_NAME_CHARS}) {
	int position = findTagPosition();
	// NB: no need to check if "value" contains the closing "*/" PHPDoc string
	// since character '*' is not allowed in {TAG_NAME_CHARS}
	String value = new String(zzBuffer, position, zzMarkedPos - position);

	TagKind tagkind = TagKind.getTagKindFromValue(value);
	int indentedTagPosition = zzStartRead + 3; // skip "/**"
	String indentedText = new String(zzBuffer, indentedTagPosition, zzMarkedPos - indentedTagPosition);
	Scalar indentedTag = new Scalar(indentedTagPosition - _zzPushbackPos, indentedTagPosition - _zzPushbackPos + indentedText.length(),
			indentedText, Scalar.TYPE_STRING);
	if (tagkind != null && tagkind != TagKind.UNKNOWN) {
		startTagsState(tagkind, position, tagkind.getValue(), indentedTag);
	} else {
		startTagsState(TagKind.UNKNOWN, position, value, indentedTag);
	}
}

<ST_IN_FIRST_LINE>^{PHPDOCSTART}("{@"[a-zA-Z-]+"}") {
	int position = findTagPosition();
	String value = new String(zzBuffer, position, zzMarkedPos - position);
	TagKind tagkind = TagKind.getTagKindFromValue(value);
	int indentedTagPosition = zzStartRead + 3; // skip "/**"
	if (tagkind != null && tagkind != TagKind.UNKNOWN) {
		String indentedText = new String(zzBuffer, indentedTagPosition, zzMarkedPos - indentedTagPosition);
		Scalar indentedTag = new Scalar(indentedTagPosition - _zzPushbackPos, indentedTagPosition - _zzPushbackPos + indentedText.length(),
				indentedText, Scalar.TYPE_STRING);
		startTagsState(tagkind, position, tagkind.getValue(), indentedTag);
	} else {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=474332
		// no @tags were found on first line, continue normal
		// processing...
		updateStartPos(indentedTagPosition);
		// no need to call yypushback(zzMarkedPos - indentedTagPosition) here...
		yybegin(ST_IN_SHORT_DESC);
	}
}

<ST_IN_FIRST_LINE>^{PHPDOCSTART} {
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=474332
	// no @tags were found on first line, continue normal
	// processing...
	updateStartPos(zzStartRead + 3); // skip "/**"
	yybegin(ST_IN_SHORT_DESC);
}

<ST_IN_SHORT_DESC>^{TABS_AND_SPACES}("*/") {
	maxNumberofLines = 5;
	handleDocEnd_shortDesc();
	return -1;
}
<ST_IN_SHORT_DESC>{TABS_AND_SPACES}("*/") {
	maxNumberofLines = 4;
	handleDocEnd_shortDesc();
	return -1;
}

<ST_IN_SHORT_DESC>^{EMPTYLINE}{NEWLINE}     {startLongDescState(false);}

<ST_IN_SHORT_DESC>([.]+[ \t]+{NEWLINE}?)|([.]+{NEWLINE}) {
	appendText();
	startLongDescState(true);
}

<ST_IN_SHORT_DESC>{NEWLINE}     {handleNewLine();}
<ST_IN_SHORT_DESC>.             {}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@"{TAG_NAME_CHARS}) {
	int position = findTagPosition();
	// NB: no need to check if "value" contains the closing "*/" PHPDoc string
	// since character '*' is not allowed in {TAG_NAME_CHARS}
	String value = new String(zzBuffer, position, zzMarkedPos - position);

	TagKind tagkind = TagKind.getTagKindFromValue(value);
	int indentedTagPosition = findIndentedLineStartTagPosition();
	String indentedText = new String(zzBuffer, indentedTagPosition, zzMarkedPos - indentedTagPosition);
	Scalar indentedTag = new Scalar(indentedTagPosition - _zzPushbackPos, indentedTagPosition - _zzPushbackPos + indentedText.length(),
			indentedText, Scalar.TYPE_STRING);
	if (tagkind != null && tagkind != TagKind.UNKNOWN) {
		startTagsState(tagkind, position, tagkind.getValue(), indentedTag);
	} else {
		startTagsState(TagKind.UNKNOWN, position, value, indentedTag);
	}
}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("{@"[a-zA-Z-]+"}") {
	int position = findTagPosition();
	String value = new String(zzBuffer, position, zzMarkedPos - position);
	TagKind tagkind = TagKind.getTagKindFromValue(value);
	int indentedTagPosition = findIndentedLineStartTagPosition();
	if (tagkind != null && tagkind != TagKind.UNKNOWN) {
		String indentedText = new String(zzBuffer, indentedTagPosition, zzMarkedPos - indentedTagPosition);
		Scalar indentedTag = new Scalar(indentedTagPosition - _zzPushbackPos, indentedTagPosition - _zzPushbackPos + indentedText.length(),
				indentedText, Scalar.TYPE_STRING);
		startTagsState(tagkind, position, tagkind.getValue(), indentedTag);
	} else {
		updateStartPos(indentedTagPosition);
		// no need to call yypushback(zzMarkedPos - indentedTagPosition) here...
	}
}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC,ST_IN_TAGS>^{LINESTART}     {updateStartPos(findIndentedLineStartTagPosition());}

<ST_IN_LONG_DESC>{TABS_AND_SPACES}("*/")     {handleDocEnd_longDesc();return -1;}

<ST_IN_LONG_DESC>{NEWLINE}     {appendText();}

<ST_IN_LONG_DESC>.             {}

<ST_IN_TAGS>^{LINESTART}("@"{TAG_NAME_CHARS}) {
	int position = findTagPosition();
	// NB: no need to check if "value" contains the closing "*/" PHPDoc string
	// since character '*' is not allowed in {TAG_NAME_CHARS}
	String value = new String(zzBuffer, position, zzMarkedPos - position);

	TagKind tagkind = TagKind.getTagKindFromValue(value);
	int indentedTagPosition = findIndentedLineStartTagPosition();
	String indentedText = new String(zzBuffer, indentedTagPosition, zzMarkedPos - indentedTagPosition);
	Scalar indentedTag = new Scalar(indentedTagPosition - _zzPushbackPos, indentedTagPosition - _zzPushbackPos + indentedText.length(),
			indentedText, Scalar.TYPE_STRING);
	if (tagkind != null && tagkind != TagKind.UNKNOWN) {
		setNewTag(tagkind, position, tagkind.getValue(), indentedTag);
	} else {
		setNewTag(TagKind.UNKNOWN, position, value, indentedTag);
	}
}

<ST_IN_TAGS>^{LINESTART}("{@"[a-zA-Z-]+"}") {
	int position = findTagPosition();
	String value = new String(zzBuffer, position, zzMarkedPos - position);
	TagKind tagkind = TagKind.getTagKindFromValue(value);
	int indentedTagPosition = findIndentedLineStartTagPosition();
	if (tagkind != null && tagkind != TagKind.UNKNOWN) {
		String indentedText = new String(zzBuffer, indentedTagPosition, zzMarkedPos - indentedTagPosition);
		Scalar indentedTag = new Scalar(indentedTagPosition - _zzPushbackPos, indentedTagPosition - _zzPushbackPos + indentedText.length(),
				indentedText, Scalar.TYPE_STRING);
		setNewTag(tagkind, position, tagkind.getValue(), indentedTag);
	} else {
		updateStartPos(indentedTagPosition);
		// no need to call yypushback(zzMarkedPos - indentedTagPosition) here...
	}
}

<ST_IN_TAGS>{TABS_AND_SPACES}("*/")     {handleDocEnd_inTags();return -1;}

<ST_IN_TAGS>{NEWLINE}     {appendText();}

<ST_IN_TAGS>.             {}
