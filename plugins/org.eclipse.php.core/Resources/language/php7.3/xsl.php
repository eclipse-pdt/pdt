<?php

// Start of xsl v.7.3.0

/**
 * @link http://www.php.net/manual/en/class.xsltprocessor.php
 */
class XSLTProcessor  {

	/**
	 * Import stylesheet
	 * @link http://www.php.net/manual/en/xsltprocessor.importstylesheet.php
	 * @param object $stylesheet The imported style sheet as a DOMDocument or
	 * SimpleXMLElement object.
	 * @return bool true on success or false on failure
	 */
	public function importStylesheet ($stylesheet) {}

	/**
	 * Transform to a DOMDocument
	 * @link http://www.php.net/manual/en/xsltprocessor.transformtodoc.php
	 * @param DOMNode $doc The node to be transformed.
	 * @return DOMDocument The resulting DOMDocument or false on error.
	 */
	public function transformToDoc (DOMNode $doc) {}

	/**
	 * Transform to URI
	 * @link http://www.php.net/manual/en/xsltprocessor.transformtouri.php
	 * @param DOMDocument $doc The document to transform.
	 * @param string $uri The target URI for the transformation.
	 * @return int the number of bytes written or false if an error occurred.
	 */
	public function transformToUri (DOMDocument $doc, string $uri) {}

	/**
	 * Transform to XML
	 * @link http://www.php.net/manual/en/xsltprocessor.transformtoxml.php
	 * @param object $doc The DOMDocument or SimpleXMLElement object to
	 * be transformed.
	 * @return string The result of the transformation as a string or false on error.
	 */
	public function transformToXml ($doc) {}

	/**
	 * Set value for a parameter
	 * @link http://www.php.net/manual/en/xsltprocessor.setparameter.php
	 * @param string $namespace The namespace URI of the XSLT parameter.
	 * @param string $name The local name of the XSLT parameter.
	 * @param string $value The new value of the XSLT parameter.
	 * @return bool true on success or false on failure
	 */
	public function setParameter (string $namespace, string $name, string $value) {}

	/**
	 * Get value of a parameter
	 * @link http://www.php.net/manual/en/xsltprocessor.getparameter.php
	 * @param string $namespaceURI The namespace URI of the XSLT parameter.
	 * @param string $localName The local name of the XSLT parameter.
	 * @return string The value of the parameter (as a string), or false if it's not set.
	 */
	public function getParameter (string $namespaceURI, string $localName) {}

	/**
	 * Remove parameter
	 * @link http://www.php.net/manual/en/xsltprocessor.removeparameter.php
	 * @param string $namespaceURI The namespace URI of the XSLT parameter.
	 * @param string $localName The local name of the XSLT parameter.
	 * @return bool true on success or false on failure
	 */
	public function removeParameter (string $namespaceURI, string $localName) {}

	/**
	 * Determine if PHP has EXSLT support
	 * @link http://www.php.net/manual/en/xsltprocessor.hasexsltsupport.php
	 * @return bool true on success or false on failure
	 */
	public function hasExsltSupport () {}

	/**
	 * Enables the ability to use PHP functions as XSLT functions
	 * @link http://www.php.net/manual/en/xsltprocessor.registerphpfunctions.php
	 * @param mixed $restrict [optional] <p>
	 * Use this parameter to only allow certain functions to be called from 
	 * XSLT.
	 * </p>
	 * <p>
	 * This parameter can be either a string (a function name) or an array of
	 * functions.
	 * </p>
	 * @return void 
	 */
	public function registerPHPFunctions ($restrict = null) {}

	/**
	 * Sets profiling output file
	 * @link http://www.php.net/manual/en/xsltprocessor.setprofiling.php
	 * @param string $filename Path to the file to dump profiling information.
	 * @return bool true on success or false on failure
	 */
	public function setProfiling (string $filename) {}

	/**
	 * Set security preferences
	 * @link http://www.php.net/manual/en/xsltprocessor.setsecurityprefs.php
	 * @param int $securityPrefs The new security preferences. The following constants can be ORed:
	 * XSL_SECPREF_READ_FILE,
	 * XSL_SECPREF_WRITE_FILE,
	 * XSL_SECPREF_CREATE_DIRECTORY,
	 * XSL_SECPREF_READ_NETWORK,
	 * XSL_SECPREF_WRITE_NETWORK. Alternatively,
	 * XSL_SECPREF_NONE or
	 * XSL_SECPREF_DEFAULT can be passed.
	 * @return int the old security preferences.
	 */
	public function setSecurityPrefs (int $securityPrefs) {}

	/**
	 * Get security preferences
	 * @link http://www.php.net/manual/en/xsltprocessor.getsecurityprefs.php
	 * @return int A bitmask consisting of XSL_SECPREF_READ_FILE,
	 * XSL_SECPREF_WRITE_FILE,
	 * XSL_SECPREF_CREATE_DIRECTORY,
	 * XSL_SECPREF_READ_NETWORK,
	 * XSL_SECPREF_WRITE_NETWORK.
	 */
	public function getSecurityPrefs () {}

}

/**
 * 
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_CLONE_AUTO', 0);

/**
 * 
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_CLONE_NEVER', -1);

/**
 * 
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_CLONE_ALWAYS', 1);

/**
 * Deactivate all security restrictions.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_NONE', 0);

/**
 * Disallows reading files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_READ_FILE', 2);

/**
 * Disallows writing files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_WRITE_FILE', 4);

/**
 * Disallows creating directories.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_CREATE_DIRECTORY', 8);

/**
 * Disallows reading network files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_READ_NETWORK', 16);

/**
 * Disallows writing network files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_WRITE_NETWORK', 32);

/**
 * Disallows all write access, i.e. a bitmask of
 * XSL_SECPREF_WRITE_NETWORK |
 * XSL_SECPREF_CREATE_DIRECTORY |
 * XSL_SECPREF_WRITE_FILE.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('XSL_SECPREF_DEFAULT', 44);

/**
 * libxslt version like 10117. Available as of PHP 5.1.2.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('LIBXSLT_VERSION', 10132);

/**
 * libxslt version like 1.1.17. Available as of PHP 5.1.2.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('LIBXSLT_DOTTED_VERSION', "1.1.32");

/**
 * libexslt version like 813. Available as of PHP 5.1.2.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('LIBEXSLT_VERSION', 820);

/**
 * libexslt version like 1.1.17. Available as of PHP 5.1.2.
 * @link http://www.php.net/manual/en/xsl.constants.php
 */
define ('LIBEXSLT_DOTTED_VERSION', "0.8.20");

// End of xsl v.7.3.0
