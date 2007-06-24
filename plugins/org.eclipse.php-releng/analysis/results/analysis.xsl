<xsl:stylesheet	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes"/>
<xsl:decimal-format decimal-separator="." grouping-separator="," />

<xsl:template match="analysissuites">
	<HTML>
		<HEAD>
    <style type="text/css">
      body {
      	font:normal 68% verdana,arial,helvetica;
      	color:#000000;
      }
      table tr td, table tr th {
          font-size: 68%;
      }
      table.details tr th{
      	font-weight: bold;
      	text-align:left;
      	background:#E77573;
      }
      table.details tr td{
      	background:#EEE8E2;
      }
      
      p {
      	line-height:1.5em;
      	margin-top:0.5em; margin-bottom:1.0em;
      }
      h1 {
      	margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
      }
      h2 {
      	margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
      }
      h3 {
      	margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
      }
      h4 {
      	margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
      }
      h5 {
      	margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
      }
      h6 {
      	margin-bottom: 0.5em; font: bold 100% verdana,arial,helvetica
      }
      .Error {
      	font-weight:bold; color:red;
      }
      .Warning {
      	font-weight:bold; color:purple;
      }
      .Properties {
      	text-align:right;
      }
	input.btn{
	   font: bold 90% verdana,arial,helvetica; margin-bottom: 0.5em;
	   }

      </style>
		</HEAD>
		<body>
			<a name="top"></a>
			<xsl:call-template name="pageHeader"/>	

			<!-- Summary part -->
			<xsl:call-template name="summary"/>
			<hr size="1" width="95%" align="left"/>


			<!-- Search for class -->
			<script language="JavaScript"><![CDATA[
			var TRange=null
			
			function findString (str) {
			 if (parseInt(navigator.appVersion)<4) return;
			 var strFound;
			 if (navigator.appName=="Netscape") {
			
			  // NAVIGATOR-SPECIFIC CODE
			
			  strFound=self.find(str);
			  if (!strFound) {
			   strFound=self.find(str,0,1)
			   while (self.find(str,0,1)) continue
			  }
			 }
			 if (navigator.appName.indexOf("Microsoft")!=-1) {
			
			  // EXPLORER-SPECIFIC CODE
			
			  if (TRange!=null) {
			   TRange.collapse(false)
			   strFound=TRange.findText(str)
			   if (strFound) TRange.select()
			  }
			  if (TRange==null || strFound==0) {
			   TRange=self.document.body.createTextRange()
			   strFound=TRange.findText(str)
			   if (strFound) TRange.select()
			  }
			 }
			 if (!strFound) alert ("String '"+str+"' not found!")
			}
			]]>  
			</script>

			<!-- Package List part -->
			<xsl:call-template name="packagelist"/>
			<hr size="1" width="95%" align="left"/>
			
			<!-- For each package create its part -->
			<xsl:call-template name="packages"/>
			<hr size="1" width="95%" align="left"/>
			
			<!-- For each class create the  part -->
			<xsl:call-template name="classes"/>
			
		</body>
	</HTML>
</xsl:template>
	
	
	
	<!-- ================================================================== -->
	<!-- Write a list of all packages with an hyperlink to the anchor of    -->
	<!-- of the package name.                                               -->
	<!-- ================================================================== -->
	<xsl:template name="packagelist">	
		<h2>Packages</h2>
		<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
			<xsl:call-template name="analysissuite.test.header"/>
			<!-- list all packages recursively -->
			<xsl:for-each select="./analysissuite[not(./@package = preceding-sibling::analysissuite/@package)]">
				<xsl:sort select="@package"/>
				<xsl:variable name="analysissuites-in-package" select="/analysissuites/analysissuite[./@package = current()/@package]"/>
				<xsl:variable name="analysisCount" select="sum($analysissuites-in-package/@results)"/>
				<xsl:variable name="severeCount" select="sum($analysissuites-in-package/@severes)"/>
				<xsl:variable name="warningCount" select="sum($analysissuites-in-package/@warnings)"/>
				<xsl:variable name="recommendationCount" select="sum($analysissuites-in-package/@recommendations)"/>
				
				<!-- write a summary for the package -->
				<tr valign="top">
					<xsl:attribute name="class">
						<xsl:choose>
							<xsl:when test="$severeCount &gt; 0">Error</xsl:when>
							<xsl:when test="$analysisCount &gt; 0">Failure</xsl:when>			
						</xsl:choose>
					</xsl:attribute>

					<td><a href="#{@package}"><xsl:value-of select="@package"/></a></td>
					<td><xsl:value-of select="$analysisCount"/></td>
					<td><xsl:value-of select="$severeCount"/></td>
					<td><xsl:value-of select="$warningCount"/></td>
					<td><xsl:value-of select="$recommendationCount"/></td>
				</tr>
			</xsl:for-each>
		</table>		
	</xsl:template>
	
	
	<!-- ================================================================== -->
	<!-- Write a package level report                                       -->
	<!-- It creates a table with values from the document:                  -->
	<!-- Name | Tests | Errors | Failures | Time                            -->
	<!-- ================================================================== -->
	<xsl:template name="packages">
		<!-- create an anchor to this package name -->
		<xsl:for-each select="/analysissuites/analysissuite[not(./@package = preceding-sibling::analysissuite/@package)]">
			<xsl:sort select="@package"/>
				<a name="{@package}"></a>
				<h3>Package <xsl:value-of select="@package"/></h3>
				
				<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
					<xsl:call-template name="analysissuite.test.header"/>
			
					<!-- match the testsuites of this package -->
					<xsl:apply-templates select="/analysissuites/analysissuite[./@package = current()/@package]" mode="print.test"/>
				</table>
				<a href="#top">Back to top</a>
				<p/>
				<p/>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="classes">
		<xsl:for-each select="analysissuite">
			<xsl:sort select="@name"/>
			<!-- create an anchor to this class name -->
			<a name="{@name}"></a>
			<h3>Analysis Case for <xsl:value-of select="@name"/></h3>
			
			<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
			  <xsl:call-template name="analysiscase.test.header"/>
   			  <xsl:apply-templates select="./analysiscase" mode="print.test"/>
			</table>
			<p/>
			<a href="#top">Back to top</a>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template name="summary">
		<h2>Summary</h2>
		<xsl:variable name="resultsCount" select="sum(analysissuite/@results)"/>
		<xsl:variable name="severesCount" select="sum(analysissuite/@severes)"/>
		<xsl:variable name="warningsCount" select="sum(analysissuite/@warnings)"/>
		<xsl:variable name="recommendationsCount" select="sum(analysissuite/@recommendations)"/>
		<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
		<tr valign="top">
			<th width="25%">Results</th>
			<th width="25%">Severes</th>
			<th width="25%">Warnings</th>
			<th width="25%">Recommendations</th>
		</tr>
		<tr valign="top">
			<td><xsl:value-of select="$resultsCount"/></td>
			<td><xsl:value-of select="$severesCount"/></td>
			<td><xsl:value-of select="$warningsCount"/></td>
			<td><xsl:value-of select="$recommendationsCount"/></td>
		</tr>
		</table>
		<table border="0" width="95%">
		<tr>
		<td	style="text-align: justify;">
		 Note: There are three basic modes of severity: <i>recommendation</i>, <i>warning</i> and <i>severe</i>.  They can be used to indicate to the user how important it is to address the results produced.
		</td>
		</tr>
		</table>
	</xsl:template>
		
<!-- Page HEADER -->
<xsl:template name="pageHeader">
	
	<table width="100%">
	<tr>
		<td align="left"><h1>Analysis Results</h1></td>
		<td align="right">Designed for use with <a href='http://www.eclipse.org/pdt'>PDT</a> and <a href='http://www.eclipse.org/tptp'>TPTP</a>.
			<form name="f1" action="" onSubmit="if(this.t1.value!=null &amp;&amp; this.t1.value!='') findString(this.t1.value);return false">
			<input type="text" name="t1" value="" size="26" class="btn"/>   
			<input type="submit" name="b1" value="Find" ACCESSKEY="f" class="btn"/>
			</form>
		</td>
	</tr>
	</table>
	<hr size="1"/>
</xsl:template>

<xsl:template match="testsuite" mode="header">
	<tr valign="top">
		<th width="80%">Name</th>
		<th>Tests</th>
		<th>Errors</th>
		<th>Failures</th>
		<th nowrap="nowrap">Time(s)</th>
	</tr>
</xsl:template>

<!-- class header -->
<xsl:template name="analysissuite.test.header">
	<tr valign="top">
		<th width="80%">Name</th>
		<th>Results</th>
		<th>Severes</th>
		<th>Warnings</th>
		<th>Recommendations</th>
	</tr>
</xsl:template>

<!-- method header -->
<xsl:template name="analysiscase.test.header">
	<tr valign="top">
		<th>Severity</th>
		<th>Line</th>
		<th width="80%">Type</th>
	</tr>
</xsl:template>


<!-- class information -->
<xsl:template match="analysissuite" mode="print.test">

	<xsl:variable name="severesCount" select="@severes"/>
	<xsl:variable name="resultsCount" select="@results"/>	

	<tr valign="top">
		<xsl:attribute name="class">
			<xsl:choose>
				<xsl:when test="$severesCount &gt; 0">Error</xsl:when>
				<xsl:when test="$resultsCount &gt; 0">Failure</xsl:when>			
			</xsl:choose>
		</xsl:attribute>
	
		<!-- print testsuite information -->
		<td><a href="#{@name}"><xsl:value-of select="@name"/></a></td>
		<td><xsl:value-of select="@results"/></td>
		<td><xsl:value-of select="@severes"/></td>
		<td><xsl:value-of select="@warnings"/></td>
		<td><xsl:value-of select="@recommendations"/></td>
	</tr>
</xsl:template>

<xsl:template match="analysiscase" mode="print.test">
	<tr valign="top">
		<xsl:attribute name="class">
			<xsl:choose>
				<xsl:when test="severe">Error</xsl:when>
			</xsl:choose>
		</xsl:attribute>
		<xsl:choose>
			<xsl:when test="severe">
				<td>Severe</td>
				<td><xsl:value-of select="@line"/></td>
				<td><xsl:apply-templates select="severe"/></td>
			</xsl:when>
			<xsl:when test="warning">
				<td>Warning</td>
				<td><xsl:value-of select="@line"/></td>
				<td><xsl:apply-templates select="warning"/></td>
			</xsl:when>
			<xsl:when test="recommendation">
				<td>Recommendation</td>
				<td><xsl:value-of select="@line"/></td>
				<td><xsl:apply-templates select="recommendation"/></td>
			</xsl:when>
		</xsl:choose>
	</tr>
</xsl:template>


<xsl:template match="severe">
	<xsl:call-template name="display-failures"/>
</xsl:template>

<xsl:template match="warning">
	<xsl:call-template name="display-failures"/>
</xsl:template>

<xsl:template match="recommendation">
	<xsl:call-template name="display-failures"/>
</xsl:template>

<!-- Style for the error and failure in the tescase template -->
<xsl:template name="display-failures">
	<a>
		<xsl:attribute name="href">http://download.eclipse.org/tools/pdt/downloads/reports/rules.html#<xsl:value-of select="@type"/>
		</xsl:attribute>
	<xsl:choose>
		<xsl:when test="not(@message)">N/A</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="@message"/>
		</xsl:otherwise>
	</xsl:choose>
	</a>
	<!-- display the stacktrace -->
	<code>
		<p/>
		<xsl:call-template name="br-replace">
			<xsl:with-param name="word" select="."/>
		</xsl:call-template>
	</code>
	<!-- the later is better but might be problematic for non-21" monitors... -->
	<!--pre><xsl:value-of select="."/></pre-->
</xsl:template>

<xsl:template name="JS-escape">
	<xsl:param name="string"/>
	<xsl:choose><!-- something isn't right here, basically all single quotes need to be replaced with backslash-single-quote
		<xsl:when test="contains($string,'&apos;')">
			<xsl:value-of select="substring-before($string,'&apos;')"/>
			\&apos;
			<xsl:call-template name="JS-escape">
				<xsl:with-param name="string" select="substring-after($string,'&apos;')"/>
			</xsl:call-template>
		</xsl:when> -->
		<xsl:when test="contains($string,'\')">
			<xsl:value-of select="substring-before($string,'\')"/>\\<xsl:call-template name="JS-escape">
				<xsl:with-param name="string" select="substring-after($string,'\')"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$string"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>


<!--
	template that will convert a carriage return into a br tag
	@param word the text from which to convert CR to BR tag
-->
<xsl:template name="br-replace">
	<xsl:param name="word"/>
	<xsl:choose>
		<xsl:when test="contains($word,'&#xA;')">
			<xsl:value-of select="substring-before($word,'&#xA;')"/>
			<br/>
			<xsl:call-template name="br-replace">
				<xsl:with-param name="word" select="substring-after($word,'&#xA;')"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$word"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>

