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

package org.eclipse.php.internal.core.phpModel.parser.phpdoc;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.phpModel.phpElementData.BasicPHPDocTag;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlock;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlockImp;
%%

%class DocumentorLexer
%public
%unicode
%line

%eofclose

%caseless
%function next_token

%standalone

%state ST_IN_SHORT_DESC
%state ST_IN_LONG_DESC
%state ST_IN_TAGS


%{
    private String shortDesc = null;
    private String longDesc = null;
    private LinkedList tagList = null;
    private int currTagId = 0;
    private StringBuilder sBuffer = null;
    private int numOfLines = 0;
    private int startPos = 0;

    public PHPDocBlock parse (){

        longDesc = "";
        tagList = new LinkedList();
        sBuffer = new StringBuilder();
        numOfLines = 1;

        //start parsing
        while ( !zzAtEOF )
            try {
                next_token();
            } catch (IOException e) {
                Logger.logException(e);
            }

        BasicPHPDocTag[] tags = new BasicPHPDocTag[tagList.size()];
        tagList.toArray(tags);

        PHPDocBlockImp rv = new PHPDocBlockImp(shortDesc, longDesc, tags);

        return rv;

    }

    private void startTagsState(String firstState){
        updateStartPos();
        hendleDesc();
        currTagId = BasicPHPDocTag.getID(firstState);
        sBuffer = new StringBuilder();
        yybegin(ST_IN_TAGS);
    }

    private void setNewTag(String newTag){
       updateStartPos();
       setTagValue();

       sBuffer = new StringBuilder();
       currTagId = BasicPHPDocTag.getID(newTag);
    }

    private void setTagValue(){
        String value = sBuffer.toString().trim();
        // special case for backward compatibility
        if (currTagId == BasicPHPDocTag.DESC) {
            shortDesc = shortDesc + value;
            return;
        }

        BasicPHPDocTag basicPHPDocTag = new BasicPHPDocTag(currTagId,value);
        tagList.add(basicPHPDocTag);
    }

    private void appendText(){
       sBuffer.append(zzBuffer, startPos, zzMarkedPos-startPos);
       updateStartPos();
    }

    private void hendleDesc() {
        if(zzLexicalState == ST_IN_SHORT_DESC){
            shortDesc = sBuffer.toString().trim();
        }
        else{
            longDesc = sBuffer.toString().trim();
        }

        sBuffer = new StringBuilder();
    }

    private void startLongDescState() {
        hendleDesc();
        updateStartPos();
        yybegin(ST_IN_LONG_DESC);
    }

    private void hendleNewLine() {
        appendText();
        if(numOfLines==4){
            int firstLineEnd = sBuffer.indexOf("\n",1);
            shortDesc = sBuffer.substring(0,firstLineEnd);
            shortDesc = shortDesc.trim();
            sBuffer.delete(0,firstLineEnd);
            yybegin(ST_IN_LONG_DESC);
        }
        else{
          numOfLines++;
        }
    }

    private void appendLastText(){
       sBuffer.append(zzBuffer, startPos, zzMarkedPos-startPos-2);
       updateStartPos();
    }

    int maxNumberofLines = 4;

    private void handleDocEnd_shortDesc() {
        appendLastText();
        if(numOfLines==maxNumberofLines){
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


    private void updateStartPos(){
        startPos = zzMarkedPos;
    }
%}

TABS_AND_SPACES=[ \t]*
ANY_CHAR=(.|[\n])
NEWLINE=("\r"|"\n"|"\r\n")
LINESTART=({TABS_AND_SPACES}"*"?{TABS_AND_SPACES})
EMPTYLINE=({LINESTART}{TABS_AND_SPACES}{NEWLINE})


%%

<YYINITIAL> {
    ^"/**"{TABS_AND_SPACES}({NEWLINE}) {
        updateStartPos();
        yybegin(ST_IN_SHORT_DESC);
    }
    ^"/**"{TABS_AND_SPACES} {
        updateStartPos();
        yybegin(ST_IN_SHORT_DESC);
    }
}

<YYINITIAL>{ANY_CHAR}   {}

<ST_IN_SHORT_DESC>^{TABS_AND_SPACES}("*/") {
    maxNumberofLines = 5;
    handleDocEnd_shortDesc();
}
<ST_IN_SHORT_DESC>{TABS_AND_SPACES}("*/") {
    maxNumberofLines = 4;
    handleDocEnd_shortDesc();
}

<ST_IN_SHORT_DESC>^{EMPTYLINE}  {startLongDescState();}

<ST_IN_SHORT_DESC>(([ \t]+)"."|"."([ \t]+)|"."{NEWLINE}) {
    appendText();
    startLongDescState();
}

<ST_IN_SHORT_DESC>{NEWLINE}     {hendleNewLine();}
<ST_IN_SHORT_DESC>.             {}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@abstract")     {startTagsState("abstract");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@access")       {startTagsState("access");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@author")       {startTagsState("author");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@category")     {startTagsState("category");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@copyright")    {startTagsState("copyright");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@deprecated")   {startTagsState("deprecated");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@desc")         {startTagsState("desc");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@example")      {startTagsState("example");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@final")        {startTagsState("final");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@filesource")   {startTagsState("filesource");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@global")       {startTagsState("global");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@ignore")       {startTagsState("ignore");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@internal")     {startTagsState("internal");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@license")      {startTagsState("license");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@link")         {startTagsState("link");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@name")         {startTagsState("name");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@package")      {startTagsState("package");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@param")        {startTagsState("param");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@return")       {startTagsState("return");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@see")          {startTagsState("see");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@since")        {startTagsState("since");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@static")       {startTagsState("static");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@staticvar")    {startTagsState("staticvar");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@subpackage")   {startTagsState("subpackage");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@throws")       {startTagsState("throws");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@todo")         {startTagsState("todo");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@tutorial")     {startTagsState("tutorial");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@uses")         {startTagsState("uses");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@var")          {startTagsState("var");}
<ST_IN_SHORT_DESC,ST_IN_LONG_DESC>^{LINESTART}("@version")      {startTagsState("version");}

<ST_IN_SHORT_DESC,ST_IN_LONG_DESC,ST_IN_TAGS>^{LINESTART}       {updateStartPos();}

<ST_IN_LONG_DESC>{TABS_AND_SPACES}("*/") {handleDocEnd_longDesc();}

<ST_IN_LONG_DESC>{NEWLINE}   {appendText();}

<ST_IN_LONG_DESC>.             {}


<ST_IN_TAGS>^{LINESTART}("@abstract")   {setNewTag("abstract");}
<ST_IN_TAGS>^{LINESTART}("@access")     {setNewTag("access");}
<ST_IN_TAGS>^{LINESTART}("@author")     {setNewTag("author");}
<ST_IN_TAGS>^{LINESTART}("@category")   {setNewTag("category");}
<ST_IN_TAGS>^{LINESTART}("@copyright")  {setNewTag("copyright");}
<ST_IN_TAGS>^{LINESTART}("@deprecated") {setNewTag("deprecated");}
<ST_IN_TAGS>^{LINESTART}("@desc")       {setNewTag("desc");}
<ST_IN_TAGS>^{LINESTART}("@example")    {setNewTag("example");}
<ST_IN_TAGS>^{LINESTART}("@final")      {setNewTag("final");}
<ST_IN_TAGS>^{LINESTART}("@filesource") {setNewTag("filesource");}
<ST_IN_TAGS>^{LINESTART}("@global")     {setNewTag("global");}
<ST_IN_TAGS>^{LINESTART}("@ignore")     {setNewTag("ignore");}
<ST_IN_TAGS>^{LINESTART}("@internal")   {setNewTag("internal");}
<ST_IN_TAGS>^{LINESTART}("@license")    {setNewTag("license");}
<ST_IN_TAGS>^{LINESTART}("@link")       {setNewTag("link");}
<ST_IN_TAGS>^{LINESTART}("@name")       {setNewTag("name");}
<ST_IN_TAGS>^{LINESTART}("@package")    {setNewTag("package");}
<ST_IN_TAGS>^{LINESTART}("@param")      {setNewTag("param");}
<ST_IN_TAGS>^{LINESTART}("@return")     {setNewTag("return");}
<ST_IN_TAGS>^{LINESTART}("@see")        {setNewTag("see");}
<ST_IN_TAGS>^{LINESTART}("@since")      {setNewTag("since");}
<ST_IN_TAGS>^{LINESTART}("@static")     {setNewTag("static");}
<ST_IN_TAGS>^{LINESTART}("@staticvar")  {setNewTag("staticvar");}
<ST_IN_TAGS>^{LINESTART}("@subpackage") {setNewTag("subpackage");}
<ST_IN_TAGS>^{LINESTART}("@throws")     {setNewTag("throws");}
<ST_IN_TAGS>^{LINESTART}("@todo")       {setNewTag("todo");}
<ST_IN_TAGS>^{LINESTART}("@tutorial")   {setNewTag("tutorial");}
<ST_IN_TAGS>^{LINESTART}("@uses")       {setNewTag("uses");}
<ST_IN_TAGS>^{LINESTART}("@var")        {setNewTag("var");}
<ST_IN_TAGS>^{LINESTART}("@version")    {setNewTag("version");}

<ST_IN_TAGS>{TABS_AND_SPACES}("*/") {handleDocEnd_inTags();}

<ST_IN_TAGS>{NEWLINE}     {appendText();}

<ST_IN_TAGS>.             {}
