<?php

// Start of xsl v.0.1

class XSLTProcessor  {

	/**
	 * @param doc
	 */
	public function importStylesheet ($doc) {}

	/**
	 * @param doc
	 */
	public function transformToDoc ($doc) {}

	/**
	 * @param doc
	 * @param uri
	 */
	public function transformToUri ($doc, $uri) {}

	/**
	 * @param doc
	 */
	public function transformToXml ($doc) {}

	/**
	 * @param namespace
	 * @param name
	 * @param value[optional]
	 */
	public function setParameter ($namespace, $name, $value) {}

	/**
	 * @param namespace
	 * @param name
	 */
	public function getParameter ($namespace, $name) {}

	/**
	 * @param namespace
	 * @param name
	 */
	public function removeParameter ($namespace, $name) {}

	public function hasExsltSupport () {}

	/**
	 * @param restrict[optional]
	 */
	public function registerPHPFunctions ($restrict) {}

	/**
	 * @param filename
	 */
	public function setProfiling ($filename) {}

}
define ('XSL_CLONE_AUTO', 0);
define ('XSL_CLONE_NEVER', -1);
define ('XSL_CLONE_ALWAYS', 1);

/**
 * libxslt version like 10117. Available as of PHP 5.1.2.
 * @link http://php.net/manual/en/xsl.constants.php
 */
define ('LIBXSLT_VERSION', 10124);

/**
 * libxslt version like 1.1.17. Available as of PHP 5.1.2.
 * @link http://php.net/manual/en/xsl.constants.php
 */
define ('LIBXSLT_DOTTED_VERSION', "1.1.24");

/**
 * libexslt version like 813. Available as of PHP 5.1.2.
 * @link http://php.net/manual/en/xsl.constants.php
 */
define ('LIBEXSLT_VERSION', 813);

/**
 * libexslt version like 1.1.17. Available as of PHP 5.1.2.
 * @link http://php.net/manual/en/xsl.constants.php
 */
define ('LIBEXSLT_DOTTED_VERSION', "1.1.24");

// End of xsl v.0.1
?>
