/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Corporation and IBM Corporation.
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

import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.IDocumentorLexer;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTagKinds;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;

%%

%class DocumentorLexer
%public
%implements IDocumentorLexer
%unicode
%line

%eofclose

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
	private int currTagId;
	private int tagPosition;
	private String matchedTag;
	private StringBuffer sBuffer;
	private int numOfLines;
	private List<Scalar> textList;

	public PHPDocBlock parse() {
		shortDesc = "";
		longDesc = "";
		tagList = new ArrayList<PHPDocTag>();
		currTagId = 0;
		tagPosition = 0;
		matchedTag = "";
		sBuffer = new StringBuffer();
		numOfLines = 1;
		textList = new ArrayList<Scalar>();

		int start = zzStartRead - _zzPushbackPos;

		// start parsing
		try {
			next_token();
		} catch (IOException e) {
			Logger.logException(e);
		}
		if (!tagList.isEmpty() && !textList.isEmpty()) {
			// lastText is empty if the last line only contains '*/' and white
			// spaces
			Scalar lastText = textList.get(textList.size() - 1);
			PHPDocTag lastTag = tagList.get(tagList.size() - 1);
			if (lastText.sourceEnd() >= lastTag.sourceEnd()) {
				textList.remove(textList.size() - 1);
				if (!isBlank(lastText.getValue())) {
					lastTag.getTexts().add(lastText);
				}
			}
		}

		PHPDocTag[] tags = new PHPDocTag[tagList.size()];
		tagList.toArray(tags);

		PHPDocBlock rv = new PHPDocBlock(start, zzMarkedPos - _zzPushbackPos,
				shortDesc, longDesc, tags, textList);

		return rv;

	}

	private boolean isBlank(String value) {
		char[] line = value.toCharArray();
		for (int i = 0; i < line.length; i++) {
			char c = line[i];
			if (c != '\t' && c != ' ') {
				return false;
			}
		}
		return true;
	}

	private void startTagsState(int firstState) {
		updateStartPos();
		hendleDesc();
		currTagId = firstState;
		tagPosition = findTagPosition();
		matchedTag = tagPosition > -1 ? new String(zzBuffer, tagPosition
				+ _zzPushbackPos, zzMarkedPos - (tagPosition + _zzPushbackPos))
		: "";
		sBuffer = new StringBuffer();
		yybegin(ST_IN_TAGS);
	}

	private int findTagPosition() {
		for (int i = zzStartRead; i < zzMarkedPos; i++) {
			if (zzBuffer[i] == '@' || zzBuffer[i] == '{') {
				return i - _zzPushbackPos;
			}
		}
		return -1;
	}

	private void setNewTag(int newTag) {
		updateStartPos();
		setTagValue();

		sBuffer = new StringBuffer();
		currTagId = newTag;
		tagPosition = findTagPosition();
		matchedTag = tagPosition > -1 ? new String(zzBuffer, tagPosition
				+ _zzPushbackPos, zzMarkedPos - (tagPosition + _zzPushbackPos))
		: "";
	}

	private void setTagValue() {
		String value = sBuffer.toString();
		// special case for backward compatibility
		if (currTagId == PHPDocTagKinds.DESC) {
			shortDesc = shortDesc + value;
			return;
		}

		PHPDocTag basicPHPDocTag = new PHPDocTag(tagPosition, zzStartRead
				- _zzPushbackPos, currTagId, matchedTag, value, getTexts(
						tagPosition, zzStartRead, true));
		tagList.add(basicPHPDocTag);
	}

	private List<Scalar> getTexts(int start, int end, boolean remove) {
		List<Scalar> result = new ArrayList<Scalar>();
		for (Iterator iterator = textList.iterator(); iterator.hasNext();) {
			Scalar scalar = (Scalar) iterator.next();
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
		StringBuffer sb = new StringBuffer();
		sb.append(zzBuffer, startPos, zzMarkedPos - startPos);
		addText(sb.toString());
		sBuffer.append(sb);
		updateStartPos();
	}

	private void addText(String string) {
		textList.add(new Scalar(startPos, startPos + string.length(), string,
				Scalar.TYPE_STRING));
	}

	private void hendleDesc() {
		if(zzLexicalState == ST_IN_SHORT_DESC || zzLexicalState == ST_IN_FIRST_LINE) {
			shortDesc = sBuffer.toString().trim();
		}
		else{
			longDesc = sBuffer.toString().trim();
		}

		sBuffer = new StringBuffer();
	}

	private void startLongDescState(boolean withNewLine) {
		hendleDesc();
		updateStartPos();
		if (!withNewLine) {
			addText("");
		}
		yybegin(ST_IN_LONG_DESC);
	}

	private void hendleNewLine() {
		appendText();
		if (numOfLines == 4) {
			int firstLineEnd = sBuffer.indexOf("\n", 1);
			shortDesc = sBuffer.substring(0, firstLineEnd);
			shortDesc = shortDesc.trim();
			sBuffer.delete(0, firstLineEnd);
			yybegin(ST_IN_LONG_DESC);
		} else {
			numOfLines++;
		}
	}
	private void appendLastText() {
		StringBuffer sb = new StringBuffer();
		sb.append(zzBuffer, startPos, zzMarkedPos - startPos - 2);
		addText(sb.toString());
		sBuffer.append(sb);
		updateStartPos();
	}

	int maxNumberofLines = 4;

	private void handleDocEnd_shortDesc() {
		appendLastText();
		if(numOfLines==maxNumberofLines) {
			int firstLineEnd = sBuffer.indexOf("\n",1);
			shortDesc = sBuffer.substring(0,firstLineEnd);
			shortDesc = shortDesc.trim();
			sBuffer.delete(0,firstLineEnd);
			longDesc = sBuffer.toString().trim();
		}
		else{
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

		// NB: zzAtBOL and zzLexicalState won't be set using (directly) the
		// array "parameters", we always restart this scanner with the YYINITIAL
		// state
		this.zzAtBOL = this.zzAtEOF ? false : true;
		this.zzLexicalState = YYINITIAL;
	}

	public int[] getParamenters() {
		return new int[] { zzMarkedPos, _zzPushbackPos, zzCurrentPos,
				zzStartRead, zzEndRead, yyline, zzAtBOL ? 1 : 0,
				zzAtEOF ? 1 : 0, zzEOFDone ? 1 : 0, zzFinalHighSurrogate };
	}

	public char[] getBuffer() {
		return zzBuffer;
	}

%}

TABS_AND_SPACES=[ \t]*
ANY_CHAR=(.|[\n])
NEWLINE=("\r"|"\n"|"\r\n")
LINESTART=({TABS_AND_SPACES}"*"?{TABS_AND_SPACES})
EMPTYLINE=({LINESTART}{TABS_AND_SPACES}{NEWLINE})
PHPDOCSTART="/**"{TABS_AND_SPACES}


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

<YYINITIAL>{ANY_CHAR}   {}

<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@abstract")       {startTagsState(PHPDocTagKinds.ABSTRACT);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@access")         {startTagsState(PHPDocTagKinds.ACCESS);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@author")         {startTagsState(PHPDocTagKinds.AUTHOR);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@category")       {startTagsState(PHPDocTagKinds.CATEGORY);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@copyright")      {startTagsState(PHPDocTagKinds.COPYRIGHT);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@deprecated")     {startTagsState(PHPDocTagKinds.DEPRECATED);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@desc")           {startTagsState(PHPDocTagKinds.DESC);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@example")        {startTagsState(PHPDocTagKinds.EXAMPLE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@final")          {startTagsState(PHPDocTagKinds.FINAL);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@filesource")     {startTagsState(PHPDocTagKinds.FILESOURCE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@global")         {startTagsState(PHPDocTagKinds.GLOBAL);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@ignore")         {startTagsState(PHPDocTagKinds.IGNORE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@internal")       {startTagsState(PHPDocTagKinds.INTERNAL);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@license")        {startTagsState(PHPDocTagKinds.LICENSE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@link")           {startTagsState(PHPDocTagKinds.LINK);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@method")         {startTagsState(PHPDocTagKinds.METHOD);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@namespace")      {startTagsState(PHPDocTagKinds.NAMESPACE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@name")           {startTagsState(PHPDocTagKinds.NAME);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@package")        {startTagsState(PHPDocTagKinds.PACKAGE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@param")          {startTagsState(PHPDocTagKinds.PARAM);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@return")         {startTagsState(PHPDocTagKinds.RETURN);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@see")            {startTagsState(PHPDocTagKinds.SEE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@since")          {startTagsState(PHPDocTagKinds.SINCE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@static")         {startTagsState(PHPDocTagKinds.STATIC);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@staticvar")      {startTagsState(PHPDocTagKinds.STATICVAR);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@subpackage")     {startTagsState(PHPDocTagKinds.SUBPACKAGE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@throws")         {startTagsState(PHPDocTagKinds.THROWS);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@todo")           {startTagsState(PHPDocTagKinds.TODO);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@tutorial")       {startTagsState(PHPDocTagKinds.TUTORIAL);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@uses")           {startTagsState(PHPDocTagKinds.USES);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@var")            {startTagsState(PHPDocTagKinds.VAR);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@uses")           {startTagsState(PHPDocTagKinds.USES);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@property")       {startTagsState(PHPDocTagKinds.PROPERTY);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@property-read")  {startTagsState(PHPDocTagKinds.PROPERTY_READ);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@property-write") {startTagsState(PHPDocTagKinds.PROPERTY_WRITE);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}("@version")        {startTagsState(PHPDocTagKinds.VERSION);}
<ST_IN_FIRST_LINE>^{PHPDOCSTART}(\{@[iI][nN][hH][eE][rR][iI][tT][dD][oO][cC]\})   {startTagsState(PHPDocTagKinds.INHERITDOC);}

<ST_IN_FIRST_LINE>^{PHPDOCSTART} {
    // https://bugs.eclipse.org/bugs/show_bug.cgi?id=474332
    // no @tags were found on first line, continue normal
    // processing...
    updateStartPos();
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

<ST_IN_SHORT_DESC>^{EMPTYLINE}  {startLongDescState(false);}

<ST_IN_SHORT_DESC>(([ \t]+)"."|"."([ \t]+)|"."{NEWLINE}) {
    appendText();
    startLongDescState(true);
}

<ST_IN_SHORT_DESC>{NEWLINE}     {hendleNewLine();}
<ST_IN_SHORT_DESC>.             {}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@abstract")         {startTagsState(PHPDocTagKinds.ABSTRACT);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@access")           {startTagsState(PHPDocTagKinds.ACCESS);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@author")           {startTagsState(PHPDocTagKinds.AUTHOR);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@category")         {startTagsState(PHPDocTagKinds.CATEGORY);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@copyright")        {startTagsState(PHPDocTagKinds.COPYRIGHT);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@deprecated")       {startTagsState(PHPDocTagKinds.DEPRECATED);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@desc")             {startTagsState(PHPDocTagKinds.DESC);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@example")          {startTagsState(PHPDocTagKinds.EXAMPLE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@final")            {startTagsState(PHPDocTagKinds.FINAL);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@filesource")       {startTagsState(PHPDocTagKinds.FILESOURCE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@global")           {startTagsState(PHPDocTagKinds.GLOBAL);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@ignore")           {startTagsState(PHPDocTagKinds.IGNORE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@internal")         {startTagsState(PHPDocTagKinds.INTERNAL);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@license")          {startTagsState(PHPDocTagKinds.LICENSE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@link")             {startTagsState(PHPDocTagKinds.LINK);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@method")           {startTagsState(PHPDocTagKinds.METHOD);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@namespace")        {startTagsState(PHPDocTagKinds.NAMESPACE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@name")             {startTagsState(PHPDocTagKinds.NAME);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@package")          {startTagsState(PHPDocTagKinds.PACKAGE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@param")            {startTagsState(PHPDocTagKinds.PARAM);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@return")           {startTagsState(PHPDocTagKinds.RETURN);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@see")              {startTagsState(PHPDocTagKinds.SEE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@since")            {startTagsState(PHPDocTagKinds.SINCE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@static")           {startTagsState(PHPDocTagKinds.STATIC);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@staticvar")        {startTagsState(PHPDocTagKinds.STATICVAR);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@subpackage")       {startTagsState(PHPDocTagKinds.SUBPACKAGE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@throws")           {startTagsState(PHPDocTagKinds.THROWS);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@todo")             {startTagsState(PHPDocTagKinds.TODO);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@tutorial")         {startTagsState(PHPDocTagKinds.TUTORIAL);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@uses")             {startTagsState(PHPDocTagKinds.USES);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@var")              {startTagsState(PHPDocTagKinds.VAR);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@uses")             {startTagsState(PHPDocTagKinds.USES);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@property")         {startTagsState(PHPDocTagKinds.PROPERTY);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@property-read")    {startTagsState(PHPDocTagKinds.PROPERTY_READ);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@property-write")   {startTagsState(PHPDocTagKinds.PROPERTY_WRITE);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@version")          {startTagsState(PHPDocTagKinds.VERSION);}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}(\{@[iI][nN][hH][eE][rR][iI][tT][dD][oO][cC]\})     {startTagsState(PHPDocTagKinds.INHERITDOC);}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC,ST_IN_TAGS>^{LINESTART}       {updateStartPos();}

<ST_IN_LONG_DESC>{TABS_AND_SPACES}("*/") {handleDocEnd_longDesc();return -1;}

<ST_IN_LONG_DESC>{NEWLINE}   {appendText();}

<ST_IN_LONG_DESC>.             {}


<ST_IN_TAGS>^{LINESTART}("@abstract")       {setNewTag(PHPDocTagKinds.ABSTRACT);}
<ST_IN_TAGS>^{LINESTART}("@access")         {setNewTag(PHPDocTagKinds.ACCESS);}
<ST_IN_TAGS>^{LINESTART}("@author")         {setNewTag(PHPDocTagKinds.AUTHOR);}
<ST_IN_TAGS>^{LINESTART}("@category")       {setNewTag(PHPDocTagKinds.CATEGORY);}
<ST_IN_TAGS>^{LINESTART}("@copyright")      {setNewTag(PHPDocTagKinds.COPYRIGHT);}
<ST_IN_TAGS>^{LINESTART}("@deprecated")     {setNewTag(PHPDocTagKinds.DEPRECATED);}
<ST_IN_TAGS>^{LINESTART}("@desc")           {setNewTag(PHPDocTagKinds.DESC);}
<ST_IN_TAGS>^{LINESTART}("@example")        {setNewTag(PHPDocTagKinds.EXAMPLE);}
<ST_IN_TAGS>^{LINESTART}("@final")          {setNewTag(PHPDocTagKinds.FINAL);}
<ST_IN_TAGS>^{LINESTART}("@filesource")     {setNewTag(PHPDocTagKinds.FILESOURCE);}
<ST_IN_TAGS>^{LINESTART}("@global")         {setNewTag(PHPDocTagKinds.GLOBAL);}
<ST_IN_TAGS>^{LINESTART}("@ignore")         {setNewTag(PHPDocTagKinds.IGNORE);}
<ST_IN_TAGS>^{LINESTART}("@internal")       {setNewTag(PHPDocTagKinds.INTERNAL);}
<ST_IN_TAGS>^{LINESTART}("@license")        {setNewTag(PHPDocTagKinds.LICENSE);}
<ST_IN_TAGS>^{LINESTART}("@link")           {setNewTag(PHPDocTagKinds.LINK);}
<ST_IN_TAGS>^{LINESTART}("@method")         {setNewTag(PHPDocTagKinds.METHOD);}
<ST_IN_TAGS>^{LINESTART}("@namespace")      {setNewTag(PHPDocTagKinds.NAMESPACE);}
<ST_IN_TAGS>^{LINESTART}("@name")           {setNewTag(PHPDocTagKinds.NAME);}
<ST_IN_TAGS>^{LINESTART}("@package")        {setNewTag(PHPDocTagKinds.PACKAGE);}
<ST_IN_TAGS>^{LINESTART}("@param")          {setNewTag(PHPDocTagKinds.PARAM);}
<ST_IN_TAGS>^{LINESTART}("@return")         {setNewTag(PHPDocTagKinds.RETURN);}
<ST_IN_TAGS>^{LINESTART}("@see")            {setNewTag(PHPDocTagKinds.SEE);}
<ST_IN_TAGS>^{LINESTART}("@since")          {setNewTag(PHPDocTagKinds.SINCE);}
<ST_IN_TAGS>^{LINESTART}("@static")         {setNewTag(PHPDocTagKinds.STATIC);}
<ST_IN_TAGS>^{LINESTART}("@staticvar")      {setNewTag(PHPDocTagKinds.STATICVAR);}
<ST_IN_TAGS>^{LINESTART}("@subpackage")     {setNewTag(PHPDocTagKinds.SUBPACKAGE);}
<ST_IN_TAGS>^{LINESTART}("@throws")         {setNewTag(PHPDocTagKinds.THROWS);}
<ST_IN_TAGS>^{LINESTART}("@todo")           {setNewTag(PHPDocTagKinds.TODO);}
<ST_IN_TAGS>^{LINESTART}("@tutorial")       {setNewTag(PHPDocTagKinds.TUTORIAL);}
<ST_IN_TAGS>^{LINESTART}("@uses")           {setNewTag(PHPDocTagKinds.USES);}
<ST_IN_TAGS>^{LINESTART}("@property")       {setNewTag(PHPDocTagKinds.PROPERTY);}
<ST_IN_TAGS>^{LINESTART}("@property-read")  {setNewTag(PHPDocTagKinds.PROPERTY_READ);}
<ST_IN_TAGS>^{LINESTART}("@property-write") {setNewTag(PHPDocTagKinds.PROPERTY_WRITE);}
<ST_IN_TAGS>^{LINESTART}("@var")            {setNewTag(PHPDocTagKinds.VAR);}
<ST_IN_TAGS>^{LINESTART}("@version")        {setNewTag(PHPDocTagKinds.VERSION);}
<ST_IN_TAGS>^{LINESTART}(\{@[iI][nN][hH][eE][rR][iI][tT][dD][oO][cC]\})   {setNewTag(PHPDocTagKinds.INHERITDOC);}

<ST_IN_TAGS>{TABS_AND_SPACES}("*/") {handleDocEnd_inTags();return -1;}

<ST_IN_TAGS>{NEWLINE}     {appendText();}

<ST_IN_TAGS>.             {}
