<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns="http://www.w3.org/1999/xhtml">
	<xsl:template match="/testsuites/testsuite">
	<![CDATA[&lt;b&gt;]]><xsl:value-of select="@name"/><![CDATA[&lt;/b&gt;]]> 
	Tests: <xsl:value-of select="@tests"/>		
	<![CDATA[&lt;font color="red"&gt;]]> Errors: <xsl:value-of select="@errors"/> 
	Failures: <xsl:value-of select="@failures"/><![CDATA[&lt;/font&gt;]]>
	Time: <xsl:value-of select="@time"/><![CDATA[&lt;br&gt;]]>

	</xsl:template>
</xsl:stylesheet>
