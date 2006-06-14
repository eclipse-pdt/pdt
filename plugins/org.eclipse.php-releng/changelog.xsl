<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>

	<xsl:output method="html" indent="yes" encoding="US-ASCII" doctype-public="-//W3C//DTD HTML 4.01//EN" doctype-system="http://www.w3.org/TR/html401/strict.dtd"/>

	<xsl:key name="date-id" match="entry" use="date"/>
	<xsl:key name="msg-id" match="entry" use="msg"/>

	<xsl:param name="bts-url" select="'https://bugs.eclipse.org/bugs/show_bug.cgi?id='"/>

	<xsl:template match="changelog">
		<html>
		<head>
			<title>PHP IDE ChangeLog</title>
			<style type="text/css">
			* {
				font-family: "Verdana", "Arial", "Helvetica";
				font-size: 14px;
			}
			a {
				text-decoration: none;
			}
			ul {
				padding-top: 0px;;
				padding-bottom: 0px;
			}
			</style>
		</head>
		<body>
			<xsl:for-each select="entry[count(. | key('date-id', date)[1]) = 1]">
				<xsl:sort select="date" data-type="text" order="descending"/>
				<b><xsl:value-of select="date"/></b>
				<ul>
					<xsl:for-each select="key('date-id', date)[count(. | key('msg-id', msg)[1]) = 1]">
						<xsl:sort select="time" data-type="text" order="descending"/>
						<li><xsl:apply-templates select="msg"/><xsl:text> (</xsl:text><xsl:value-of select="author"/><xsl:text>)</xsl:text></li>
					</xsl:for-each>
				</ul>
			</xsl:for-each>
		</body>
		</html>
	</xsl:template>

	<xsl:template match="msg">
		<xsl:choose>
			<xsl:when test="contains(., '#')">
				<xsl:value-of select="substring-before(., '#')"/>
				<xsl:param name="s" select="substring-after(., '#')"/>
				<xsl:choose>
					<xsl:when test="string(number(substring($s, 1, 10))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 10))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 10)"/></a><xsl:value-of select="substring($s, 11)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 9))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 9))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 9)"/></a><xsl:value-of select="substring($s, 10)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 8))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 8))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 8)"/></a><xsl:value-of select="substring($s, 9)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 7))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 7))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 7)"/></a><xsl:value-of select="substring($s, 8)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 6))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 6))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 6)"/></a><xsl:value-of select="substring($s, 7)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 5))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 5))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 5)"/></a><xsl:value-of select="substring($s, 6)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 4))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 4))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 4)"/></a><xsl:value-of select="substring($s, 5)"/>
					</xsl:when>
					<xsl:when test="string(number(substring($s, 1, 3))) != 'NaN'">
						<a href="{concat($bts-url, string(number(substring($s, 1, 3))))}"><xsl:text>#</xsl:text>
						<xsl:value-of select="substring($s, 1, 3)"/></a><xsl:value-of select="substring($s, 4)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$s"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="."/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
