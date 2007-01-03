<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns="http://www.w3.org/1999/xhtml">
	<xsl:template match="/testsuites/testsuite">
	**[<xsl:value-of select="@name"/>
	Tests: <xsl:value-of select="@tests"/>		
	Errors: <xsl:value-of select="@errors"/>
	Failures: <xsl:value-of select="@failures"/>
	Time: <xsl:value-of select="@time"/>]**
	</xsl:template>
</xsl:stylesheet>
