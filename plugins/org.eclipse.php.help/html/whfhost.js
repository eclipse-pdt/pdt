//	WebHelp 5.10.003
var gsSK2=null;
var gsSK=null;
var gsFtsBreakChars="\t\r\n\"\\ .,!@#$%^&*()~'`:;<>?/{}[]|+-=\x85\x92\x93\x94\x95\x96\x97\x99\xA9\xAE\xB7";
var gnCLF=0;
var gsHelpCannotSearch="Cannot search for that phrase.";
var gsNoTopics="No Topics Found.";
var gsLoadingDivID="LoadingDiv";
var gsLoadingMsg="Loading data, please wait...";
var gsSearchMsg="Searching...";
var gsResultDivID="ResultDiv";
var gaaFCD=new Array();
var gaaFTCD=new Array();
var goCF=null;
var goCTF=null;
var gaTI=null;
var gnCurrentOp=0;
var gbNot=false;
var gbReady=false;
var gnLoadFts=1;
var gnCacheLimits=5;
var gaCCD=new Array();
var gbXML=false;
var gaData=new Array();
var gsBgColor="#ffffff";
var gsBgImage="";
var gsMargin="0pt";
var gsIndent="0pt";
var gsCheckKey=null;
var gnIndexNum=0;
var gaFtsContentsCon=null;
var gaTopicCheckInfo=null;
var gnTopicCheck=0;
var goFont=null;
var goErrFont=null;
var goHoverFont=null;
var gsABgColor="#cccccc";
var gbWhFHost=false;
var gbFirst=false;

function setBackground(sBgImage)
{
	gsBgImage=sBgImage;
}

function setBackgroundcolor(sBgColor)
{
	gsBgColor=sBgColor;
}

function setFont(sType,sFontName,sFontSize,sFontColor,sFontStyle,sFontWeight,sFontDecoration)
{
	var vFont=new whFont(sFontName,sFontSize,sFontColor,sFontStyle,sFontWeight,sFontDecoration);
	if(sType=="Normal")
		goFont=vFont;
	else if(sType=="Error")
		goErrFont=vFont;
	else if(sType=="Hover")
		goHoverFont=vFont;
}

function setActiveBgColor(sBgColor)
{
	gsABgColor=sBgColor;
}

function setMargin(sMargin)
{
	gsMargin=sMargin;
}

function setIndent(sIndent)
{
	gsIndent=sIndent;
}

function updateCache(oCF)
{
	var len=gaCCD.length;
	if(len<gnCacheLimits*gnCLF)
		gaCCD[len]=oCF;
	else{
		gaCCD[0].aTopics=null;
		gaCCD[0].aFtsKeys=null;
		removeItemFromArray(gaCCD,0);
		gaCCD[len-1]=oCF;
	}
}

function addFtsInfo(sPPath,sDPath,sFtsFile)
{
	gaData[gaData.length]=new ftsInfo(sPPath,sDPath,sFtsFile);
}

function onLoadXMLError()
{
	if(gnLoadFts==1)
	{
		var aFCD=new Array();
		var aFTCD=new Array();
		ftsReady(aFCD,aFTCD);
	}
	else if(gnLoadFts==3)
	{
		var aTopics=new Array();
		putFtsTData(aTopics);
	}
	else if(gnLoadFts==2)
	{
		putFtsWData(aFtsContents);
		var aFtsContents=new Array();
	}
}

function putDataXML(xmlDoc,sDocPath)
{
	if(gnLoadFts==1)
	{
		var node=xmlDoc.lastChild;
		if(node)
		{
			var oChild=node.firstChild;
			var aFCD=new Array();
			var aFTCD=new Array();
			while(oChild)
			{
				if(oChild.nodeName=="chunkinfo")
				{
					var sURL=oChild.getAttribute("url");
					var sFirst=oChild.getAttribute("first");
					var sLast=oChild.getAttribute("last");
					if(sURL&&sFirst&&sLast)
					{
						item=new Object();
						item.sStartKey=sFirst;
						item.sEndKey=sLast;
						item.sFileName=sURL;
						aFCD[aFCD.length]=item;
					}
				}
				else if(oChild.nodeName=="tchunkinfo")
				{
					var sURL=oChild.getAttribute("url");
					var nB=parseInt(oChild.getAttribute("first"));
					var nE=parseInt(oChild.getAttribute("last"));
					if(sURL&&sFirst&&sLast)
					{
						item=new Object();
						item.nBegin=nB;
						item.nEnd=nE;
						item.sFileName=sURL;
						aFTCD[aFTCD.length]=item;
					}

				}
				oChild=oChild.nextSibling;
			}
			ftsReady(aFCD,aFTCD);
		}
	}
	else if(gnLoadFts==3)
	{
		var node=xmlDoc.lastChild;
		if(node)
		{
			var oChild=node.firstChild;
			var aTopics=new Array();
			while(oChild)
			{
				if(oChild.nodeName=="topic")
				{
					var name=oChild.getAttribute("name");
					var sURL=oChild.getAttribute("url");
					if(name&&name.length>0&&sURL)
					{
						var topic=new Object();
						topic.sTopicTitle=name;
						topic.sTopicURL=sURL;
						aTopics[aTopics.length]=topic;
					}
				}
				oChild=oChild.nextSibling;
			}
			putFtsTData(aTopics);
		}
	}
	else if(gnLoadFts==2)
	{
		var node=xmlDoc.lastChild;
		if(node)
		{
			var oChild=node.firstChild;
			var aFtsContents=new Array();
			while(oChild)
			{
				if(oChild.nodeName=="key")
				{
					var name=oChild.getAttribute("name");
					if(name&&name.length>0)
					{
						var item=new Object();
						item.sItemName=name;
						aFtsContents[aFtsContents.length]=item;
						var oChildChild=oChild.firstChild;
						while(oChildChild)
						{
							if(oChildChild.nodeName=="#text")
							{
								var sIDs=oChildChild.nodeValue;
								if(sIDs)
								{
									var nBPos=0;
									do
									{
										var nPos=sIDs.indexOf(",",nBPos);
										var sID=null;
										if(nPos!=-1)
											sID=sIDs.substring(nBPos,nPos);
										else
											sID=sIDs.substring(nBPos);

										if(sID)
										{
											var id=parseInt(sID);
											if(!isNaN(id))
											{
												if(!item.aTopics)
													item.aTopics=new Array();
												item.aTopics[item.aTopics.length]=id;
											}
										}
										nBPos=nPos+1;
									}while(nBPos!=0&&nBPos<sIDs.length);
								}
							}
							oChildChild=oChildChild.nextSibling;
						}
					}
				}
				oChild=oChild.nextSibling;
			}
			putFtsWData(aFtsContents);
		}
	}
}

function ftsInfo(sPPath,sDPath,sFtsFile)
{
	this.sPPath=sPPath;
	this.sDPath=sDPath;
	this.sFtsFile=sFtsFile;
}

function window_OnLoad()
{
	if(gsBgImage&&gsBgImage.length>0)
	{
		document.body.background=gsBgImage;
	}
	if(gsBgColor&&gsBgColor.length>0)
	{
		document.body.bgColor=gsBgColor;
	}
	writeResultDiv();
	loadFts();
	var oMsg=new whMessage(WH_MSG_SHOWFTS,this,1,null)
	SendMessage(oMsg);
}

function writeResultDiv()
{
	var sHTML="<div id=\""+gsResultDivID+"\" style=\"POSITION:absolute;\"></div>";
	document.body.insertAdjacentHTML("beforeEnd",sHTML);
}

function loadFts()
{
	if(!gbReady)
	{
		var oResMsg=new whMessage(WH_MSG_GETPROJINFO,this,1,null);
		if(SendMessage(oResMsg)&&oResMsg.oParam)
		{
			gbReady=true;
			var oProj=oResMsg.oParam;
			var aProj=oProj.aProj;
			gbXML=oProj.bXML;
			if(aProj.length>0)
			{
				var sLangId=aProj[0].sLangId;
				for(var i=0;i<aProj.length;i++)
				{
					if(aProj[i].sFts&&aProj[i].sLangId==sLangId)
					{
						addFtsInfo(aProj[i].sPPath,aProj[i].sDPath,aProj[i].sFts);
					}
				}
			}
			loadFD();

		}				
	}
}

function loadFD()
{
	if(gnCLF<gaData.length)
	{
		gnLoadFts=1;
		loadData2(gaData[gnCLF].sPPath+gaData[gnCLF].sDPath+gaData[gnCLF].sFtsFile);
	}
	else 
	{
		var oMsg = new whMessage(WH_MSG_BACKUPSEARCH, this, 1, null);
		if (SendMessage(oMsg))
		{
			if (oMsg.oParam)
			{
				gsSK=oMsg.oParam.toLowerCase();
				findFTSKey();
			}
		}
		RegisterListener2(this, WH_MSG_SEARCHFTSKEY);
	}
}

function loadData2(sFile)
{
	if(gbXML)
		loadDataXML(sFile);
	else
		loadData(sFile);
}

function findFTSKey()
{
	gaTI=new Array();
	gnCurrentOp=1;
	gbNot=false;
	displayMsg(gsSearchMsg);
	if(gsSK!="")
	{
		gbFirst=true;
		findOneKey();
	}
}

function findOneKey()
{
	if(gsSK && gsSK!="")
	{
		var sInput=gsSK;
		var sCW="";
		var nS=-1;
		var nSep=-1;
		for(var nChar=0;nChar<gsFtsBreakChars.length;nChar++){
			var nFound=sInput.indexOf(gsFtsBreakChars.charAt(nChar));
			if((nFound!=-1)&&((nS==-1)||(nFound<nS))){
				nS=nFound;
				nSep=nChar;
			}
		}
		if(nS==-1){
			sCW=sInput;
			sInput="";
		}else{
			sCW=sInput.substring(0,nS);
			sInput=sInput.substring(nS+1);
		}

		gsSK=sInput;
		
		if((sCW=="or")||((nSep>=0)&&(gsFtsBreakChars.charAt(nSep)=="|"))){
			gnCurrentOp=0;
			gbNot=false;
		}else if((sCW=="and")||((nSep>=0)&&(gsFtsBreakChars.charAt(nSep)=="&"))){
			gnCurrentOp=1;
			gbNot=false;
		}else if((sCW=="not")||
			((nSep>=0)&&(gsFtsBreakChars.charAt(nSep)=="~"))){
			gbNot=!gbNot;
		}else if(sCW!=""&&!IsStopWord(sCW,gaFtsStop)){
			var sCurrentStem=GetStem(sCW);
			gsCW=sCurrentStem;
			ftsFindKeyword();
			return;
		}
		findOneKey();
	}
	else{
		displayTopics();
		checkAgain();
	}
}

function checkAgain()
{
	gsCheckKey = "";
	gnIndexNum = 0;
	gsSK=gsSK2;
	gsSK2=null;
	if(gsSK!=null)
		setTimeout("findFTSKey();",1);
}

function displayTopics()
{
	var sHTML="";
	var sLine="";
	for(var i=0;i<gaTI.length;i++){
		sLine+="<dt><nobr><a href='"+gaTI[i].sTopicURL+"'>"+_textToHtml(gaTI[i].sTopicTitle)+"</a></nobr></dt>";
		if(i>>4<<4==i)
		{
			sHTML+=sLine;
			sLine="";
		}
	}
	if(sLine.length>0)
		sHTML+=sLine;

	if(sHTML.length==0)
		sHTML="<P>"+gsNoTopics+"</P>"
	else
		sHTML="<dl>"+sHTML+"</dl>";

	var resultDiv=getElement(gsResultDivID);
	if(resultDiv)
		resultDiv.innerHTML=sHTML;
}

function displayMsg(sErrorMsg)
{
	var sHTML="<P>"+sErrorMsg+"</P>";

	var resultDiv=getElement(gsResultDivID);
	if(resultDiv)
		resultDiv.innerHTML=sHTML;
}

function ftsFindKeyword()
{
	var sKey=gsCW;
	var bNeedLoad=false;
	var aFtsContentsCon=null;
	var s=0;
	if(sKey==null) return;
	if(!gsCheckKey||sKey!=gsCheckKey||gnIndexNum==0)
	{
		aFtsContentsCon=new Array();
		gnCheck=0;
		gsCheckKey=sKey;
		gnTopicCheck=0;
		gaTopicCheckInfo=null;
	}
	else{
		s=gnIndexNum;
		aFtsContentsCon=gaFtsContentsCon;
	}
	for(var i=gnCheck;i<gaaFCD.length;i++)
	{
		var oCF=getChunkedFts(i,sKey);
		if(oCF)
		{
			if(!oCF.aFtsKeys&&oCF.sFileName!=null)
			{
				bNeedLoad=true;
				oCF.nProjId=i;
				goCF=oCF;
				gnIndexNum=s;
				gnCheck=i;
				gaFtsContentsCon=aFtsContentsCon;
				gnLoadFts=2;
				beginLoading();
				loadData2(gaData[i].sPPath+gaData[i].sDPath+oCF.sFileName);
				break;
			}
			else{
				aFtsContentsCon[s++]=oCF;
			}
		}
	}
	if(!bNeedLoad)
	{
		var aTI=gaTopicCheckInfo;
		for(var m=gnTopicCheck;m<aFtsContentsCon.length;m++)
		{
			var aTIPart=getTopics(aFtsContentsCon[m],sKey);
			if(aTIPart==null)
			{
				gnCheck=gaaFCD.length;
				gnTopicCheck=m;
				gaTopicCheckInfo=aTI;
				gaFtsContentsCon=aFtsContentsCon;
				return;
			}
			if(m==0)
				aTI=aTIPart;
			else
				aTI=mergeTopics(aTI,aTIPart);
		}
		if(mergewithPreviousResult(aTI))
		{
			gbFirst=false;
			findOneKey();
		}
		else
			checkAgain();
	}
}

function mergewithPreviousResult(aTI)
{
	if(aTI!=null&&aTI.length!=0)
	{
		var nNumTopics=aTI.length;
		if(gnCurrentOp==0||gbFirst){
			if(gbNot){
				displayMsg(gsHelpCannotSearch);
				return false;
			}else{
				var aLS,aSS;
				if(gaTI.length>=aTI.length)
				{
					aLS=gaTI;
					aSS=aTI;
				}
				else
				{
					aLS=aTI;
					aSS=gaTI;
				}
				var s=0;
				for(var i=0;i<aSS.length;i++)
				{
					var bAlreadyThere=false;
					for(var j=s;j<aLS.length;j++)
					{
						if(aSS[i].equalTo(aLS[j]))
						{
							bAlreadyThere=true;
							s=j;
							break;
						}
					}
					if(!bAlreadyThere)
					{
						insertTopic(aLS,aSS[i]);
					}
				}
				gaTI=aLS;
			}
		}else if(gnCurrentOp==1){
			if(gbNot){
				var s=0;
				for(var i=0;i<aTI.length;i++)
				{
					for(var j=s;j<gaTI.length;j++)
					{
						if(aTI[i].equalTo(gaTI[j]))
						{
							removeItemFromArray(gaTI,j);
							s=j;
							break;
						}
					}
				}
			}else{
				var s=0;
				for(var i=0;i<gaTI.length;i++)
				{
					var bFound=false;
					for(var j=s;j<aTI.length;j++)
					{
						if(gaTI[i].equalTo(aTI[j]))
						{
							bFound=true;
							s=j;
						}
					}
					if(!bFound)
					{
						removeItemFromArray(gaTI,i);
						i--;
					}
				}				
			}
		}
	}else{
		if((gnCurrentOp==1)&&(!gbNot)){
			gaTI.length=0;
		}else if((gnCurrentOp==0)&&(gbNot)){
			displayMsg(gsHelpCannotSearch);
			return false;
		}
	}
	return true;
}

function insertTopic(aTI,oTI)
{
	var nB=0;
	var nE=aTI.length-1;
	if(nE>=0)
	{
		var nM;
		var bFound=false;
		do{
			nM=(nB+nE)>>1;
			if(compare(aTI[nM].sTopicTitle,oTI.sTopicTitle)>0)
				nE=nM-1;
			else if(compare(aTI[nM].sTopicTitle,oTI.sTopicTitle)<0)
				nB=nM+1;
			else
			{
				bFound=true;
				break;
			}
		}while(nB<=nE);
		if(bFound)
			insertItemIntoArray(aTI,nM,oTI);
		else
		{
			if(compare(aTI[nM].sTopicTitle,oTI.sTopicTitle)<0)
				insertItemIntoArray(aTI,nM+1,oTI);
			else
				insertItemIntoArray(aTI,nM,oTI);
		}
	}
	else
		aTI[0]=oTI;
}

function mergeTopics(aTI1,aTI2)
{
	var i1=0;
	var i2=0;
	var len1=aTI1.length;
	var len2=aTI2.length;
	var aTopicNew=new Array();
	var i=0;
	while(i1<len1||i2<len2)
	{
		if(i1<len1&&i2<len2)
		{
			if(compare(aTI1[i1].sTopicTitle,aTI2[i2].sTopicTitle)<0)
				aTopicNew[i++]=aTI1[i1++];
			else
				aTopicNew[i++]=aTI2[i2++];
		}
		else if(i1<len1)
			aTopicNew[i++]=aTI1[i1++];
		else if(i2<len2)
			aTopicNew[i++]=aTI2[i2++];
	}
	return aTopicNew;
}

function getTopics(oCF,sKey)
{
	var aTIPart=new Array();
	if(oCF&&oCF.aFtsKeys)
	{
		var keys=oCF.aFtsKeys;
		var nB=0;
		var nE=keys.length-1;
		var nM=-1;
		var bFound=false;
		do{
			nM=(nB+nE)>>1;
			if(compare(keys[nM].sItemName,sKey)>0)
				nE=nM-1;
			else if(compare(keys[nM].sItemName,sKey)<0)
				nB=nM+1;
			else{
				bFound=true;
				break;
			}
		}while(nB<=nE);
		if(bFound)
		{
			if(keys[nM].aTopics)
			{
				for(var i=0;i<keys[nM].aTopics.length;i++)
				{
					var oTC=getTopicChunk(gaaFTCD[oCF.nProjId],keys[nM].aTopics[i]);
					if(oTC.aTopics)
					{
						aTIPart[aTIPart.length]=
							new topicInfo(oTC.aTopics[keys[nM].aTopics[i]-oTC.nBegin].sTopicTitle,
								gaData[oCF.nProjId].sPPath+oTC.aTopics[keys[nM].aTopics[i]-oTC.nBegin].sTopicURL);
					}
					else{
						goCTF=oTC;
						gnLoadFts=3;
						beginLoading();
						loadData2(gaData[oCF.nProjId].sPPath+gaData[oCF.nProjId].sDPath+oTC.sFileName);
						return null;
					}
				}
			}
		}
	}
	return aTIPart;
}

function getTopicChunk(aFTCD,nTopicId)
{
	if(aFTCD&&aFTCD.length)
	{
		var nB=0;
		var nE=aFTCD.length-1;
		var nM=-1;
		var bFound=false;
		do{
			nM=(nB+nE)>>1;
			if(aFTCD[nM].nBegin>nTopicId)
				nE=nM-1;
			else if(aFTCD[nM].nEnd<nTopicId)
				nB=nM+1;
			else{
				bFound=true;
				break;
			}
		}while(nB<=nE);
		if(bFound)
			return aFTCD[nM];
	}
	return null;
}

function endLoading()
{
	var oDiv=getElement(gsLoadingDivID);
	if(oDiv)
		oDiv.style.visibility="hidden";
}

function beginLoading()
{
	var oDiv=getElement(gsLoadingDivID);
	if(!oDiv)
	{
		document.body.insertAdjacentHTML("afterBegin",writeLoadingDiv());
		oDiv=getElement(gsLoadingDivID);
	}
	
	if(oDiv)
	{
		oDiv.style.top=document.body.scrollTop;
		oDiv.style.visibility="visible";
	}
}

function writeLoadingDiv(nIIdx)
{
	return "<div id=\""+gsLoadingDivID+"\" style=\"position:absolute;top:0;left:0;z-index:600;visibility:hidden;padding-left:4px;background-color:ivory;border-width:1;border-style:solid;border-color:black;width:150px;\">"+gsLoadingMsg+"</div>";
}

function topicInfo(sTopicTitle,sTopicURL)
{
	this.sTopicTitle=sTopicTitle;
	this.sTopicURL=sTopicURL;
	this.equalTo=function(oTI)
	{
		return ((this.sTopicTitle==oTI.sTopicTitle)&&
			(this.sTopicURL==oTI.sTopicURL));
	}
}

function getChunkedFts(nIndex,sKey)
{
	var oCF=null;
	if(nIndex<gaaFCD.length)
	{
		var len=gaaFCD[nIndex].length;
		if(len>0)
		{
			var nB=0;
			var nE=len-1;
			var bFound=false;
			do{
				var nM=(nB+nE)>>1;
				if(compare(sKey,gaaFCD[nIndex][nM].sEndKey)>0)
				{
					nB=nM+1;
				}
				else if(compare(sKey,gaaFCD[nIndex][nM].sStartKey)<0)
				{
					nE=nM-1;
				}
				else{
					bFound=true;
					break;
				}
			}while(nE>=nB);
			if(bFound)
				oCF=gaaFCD[nIndex][nM];
		}
	}
	return oCF;
}

function ftsReady(aFCD,aFTCD)
{
	endLoading();
	gaaFTCD[gnCLF]=aFTCD;
	gaaFCD[gnCLF++]=aFCD;
	setTimeout("loadFD();",1);
}

function putFtsTData(aTopics)
{
	endLoading();
	var oCTF=goCTF;
	if(oCTF)
	{
		oCTF.aTopics=aTopics;
		setTimeout("ftsFindKeyword();",1);
	}
}

function putFtsWData(aFtsContents)
{
	endLoading();
	var oCF=goCF;
	if(oCF)
	{
		updateCache(oCF);
		oCF.aFtsKeys=aFtsContents;
		setTimeout("ftsFindKeyword();",1);
	}
}

function IsStopWord(sCW,aFtsStopArray)
{
	var nStopArrayLen=aFtsStopArray.length;
	var nB=0;
	var nE=nStopArrayLen-1;
	var nM=0;
	var bFound=false;
	var sStopWord="";
	while(nB<=nE){
		nM=(nB+nE);
		nM>>=1;
		sStopWord=aFtsStopArray[nM];
		if(compare(sCW,sStopWord)>0){
			nB=(nB==nM)?nM+1:nM;
		}else{
			if(compare(sCW,sStopWord)<0){
				nE=(nE==nM)?nM-1:nM;
			}else{
				bFound=true;
				break;
			}
		}
	}
	return bFound;
}
function IsNonAscii(szWord)
{
    var temp;
    for(var iCount=0; iCount<szWord.length;iCount++)
    {
        temp = szWord.charCodeAt(iCount);
        if(temp>128)
            return true;
    }
    return false;

}
function GetStem(szWord)
{
	if(gaFtsStem==null||gaFtsStem.length==0)return szWord;
	if(IsNonAscii(szWord))             return szWord;
	var aStems=gaFtsStem;

	var nStemPos=0;
	var csStem="";
	for(var iStem=0;iStem<aStems.length;iStem++){

		if(aStems[iStem].length>=szWord.length-1)	continue;
		nStemPos=szWord.lastIndexOf(aStems[iStem]);
		if(nStemPos>0){
			var cssub=szWord.substring(nStemPos);
			if(cssub==aStems[iStem]){
				csStem=szWord;
				if(szWord.charAt(nStemPos-2)==szWord.charAt(nStemPos-1)){
					csStem=csStem.substring(0,nStemPos-1);
				}else{
					csStem=csStem.substring(0,nStemPos);
				}
				return csStem;
			}
		}
	}
	return szWord;
}

function FtsWriteClassStyle()
{
	var sStyle="<STYLE TYPE='text/css'>\n";
	if (gsBgImage)
		sStyle+="body {border-top:"+gsBgColor+" 1px solid;}\n";
	else
		sStyle+="body {border-top:black 1px solid;}\n";
	sStyle+="P {"+getFontStyle(goErrFont)+"margin-left:"+gsIndent+";margin-top:"+gsMargin+";}\n";
	sStyle+="dl {"+getFontStyle(goFont)+"margin-left:"+gsIndent+";margin-top:"+gsMargin+";}\n";
	sStyle+="A:link {"+getFontStyle(goFont)+"}\n";
	sStyle+="A:visited {"+getFontStyle(goFont)+"}\n";
	sStyle+="A:active {background-color:"+gsABgColor+";}\n";
	sStyle+="A:hover {"+getFontStyle(goHoverFont)+"}\n";
	sStyle+="</STYLE>";
	document.write(sStyle);
}

function window_Unload()
{
	UnRegisterListener2(this,WH_MSG_PROJECTREADY);
	UnRegisterListener2(this, WH_MSG_SEARCHFTSKEY);
}


function onSendMessage(oMsg)
{
	var nMsgId=oMsg.nMessageId;
	if(nMsgId==WH_MSG_SEARCHFTSKEY)
	{
		if(oMsg.oParam)
		{
			if(gsSK==null||gsSK=="")
			{
				gsSK=oMsg.oParam.toLowerCase();
				findFTSKey();
			}
			else
				gsSK2=oMsg.oParam.toLowerCase();
		}
	}
	else if(nMsgId==WH_MSG_PROJECTREADY)
	{
		loadFts();
	}
	return true;
}

if (window.gbWhUtil&&window.gbWhLang&&window.gbWhProxy&&window.gbWhVer&&window.gbWhMsg)
{
	goFont=new whFont("Verdana","8pt","#000000","normal","normal","none");
	goErrFont=new whFont("Verdana","8pt","#000000","normal","bold","none");
	goHoverFont=new whFont("Verdana","8pt","#007f00","normal","normal","underline");
	window.onload=window_OnLoad;
	window.onbeforeunload=window_BUnload;
	window.onunload=window_Unload;
	RegisterListener2(this,WH_MSG_PROJECTREADY);
	gbWhFHost=true;
}
else
	document.location.reload();