<?php

// Start of xsl v.8.2.6

/**
 * @link http://www.php.net/manual/en/class.xsltprocessor.php
 */
class XSLTProcessor  {

	/**
	 * Import stylesheet
	 * @link http://www.php.net/manual/en/xsltprocessor.importstylesheet.php
	 * @param object $stylesheet 
	 * @return bool Returns true on success or false on failure.
	 */
	public function importStylesheet (object $stylesheet): bool {}

	/**
	 * Transform to a DOMDocument
	 * @link http://www.php.net/manual/en/xsltprocessor.transformtodoc.php
	 * @param object $document 
	 * @param string|null $returnClass [optional] 
	 * @return DOMDocument|false The resulting DOMDocument or false on error.
	 */
	public function transformToDoc (object $document, ?string $returnClass = null): DOMDocument|false {}

	/**
	 * Transform to URI
	 * @link http://www.php.net/manual/en/xsltprocessor.transformtouri.php
	 * @param DOMDocument $doc 
	 * @param string $uri 
	 * @return int Returns the number of bytes written or false if an error occurred.
	 */
	public function transformToUri (DOMDocument $doc, string $uri): int {}

	/**
	 * Transform to XML
	 * @link http://www.php.net/manual/en/xsltprocessor.transformtoxml.php
	 * @param object $document 
	 * @return string|null|false The result of the transformation as a string or false on error.
	 */
	public function transformToXml (object $document): string|null|false {}

	/**
	 * Set value for a parameter
	 * @link http://www.php.net/manual/en/xsltprocessor.setparameter.php
	 * @param string $namespace 
	 * @param string $name 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParameter (string $namespace, string $name, string $value): bool {}

	/**
	 * Get value of a parameter
	 * @link http://www.php.net/manual/en/xsltprocessor.getparameter.php
	 * @param string $namespace 
	 * @param string $name 
	 * @return string|false The value of the parameter (as a string), or false if it's not set.
	 */
	public function getParameter (string $namespace, string $name): string|false {}

	/**
	 * Remove parameter
	 * @link http://www.php.net/manual/en/xsltprocessor.removeparameter.php
	 * @param string $namespace 
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function removeParameter (string $namespace, string $name): bool {}

	/**
	 * Determine if PHP has EXSLT support
	 * @link http://www.php.net/manual/en/xsltprocessor.hasexsltsupport.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function hasExsltSupport (): bool {}

	/**
	 * Enables the ability to use PHP functions as XSLT functions
	 * @link http://www.php.net/manual/en/xsltprocessor.registerphpfunctions.php
	 * @param array|string|null $functions [optional] 
	 * @return void No value is returned.
	 */
	public function registerPHPFunctions (array|string|null $functions = null): void {}

	/**
	 * Sets profiling output file
	 * @link http://www.php.net/manual/en/xsltprocessor.setprofiling.php
	 * @param string|null $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setProfiling (?string $filename): bool {}

	/**
	 * Set security preferences
	 * @link http://www.php.net/manual/en/xsltprocessor.setsecurityprefs.php
	 * @param int $preferences 
	 * @return int Returns the old security preferences.
	 */
	public function setSecurityPrefs (int $preferences): int {}

	/**
	 * Get security preferences
	 * @link http://www.php.net/manual/en/xsltprocessor.getsecurityprefs.php
	 * @return int A bitmask consisting of XSL_SECPREF_READ_FILE,
	 * XSL_SECPREF_WRITE_FILE,
	 * XSL_SECPREF_CREATE_DIRECTORY,
	 * XSL_SECPREF_READ_NETWORK,
	 * XSL_SECPREF_WRITE_NETWORK.
	 */
	public function getSecurityPrefs (): int {}

}

/**
 * 
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_CLONE_AUTO', 0);

/**
 * 
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_CLONE_NEVER', -1);

/**
 * 
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_CLONE_ALWAYS', 1);

/**
 * Deactivate all security restrictions.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_NONE', 0);

/**
 * Disallows reading files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_READ_FILE', 2);

/**
 * Disallows writing files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_WRITE_FILE', 4);

/**
 * Disallows creating directories.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_CREATE_DIRECTORY', 8);

/**
 * Disallows reading network files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_READ_NETWORK', 16);

/**
 * Disallows writing network files.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_WRITE_NETWORK', 32);

/**
 * Disallows all write access, i.e. a bitmask of
 * XSL_SECPREF_WRITE_NETWORK |
 * XSL_SECPREF_CREATE_DIRECTORY |
 * XSL_SECPREF_WRITE_FILE.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('XSL_SECPREF_DEFAULT', 44);

/**
 * libxslt version like 10117.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('LIBXSLT_VERSION', 10135);

/**
 * libxslt version like 1.1.17.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var string
 */
define ('LIBXSLT_DOTTED_VERSION', "1.1.35");

/**
 * libexslt version like 813.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var int
 */
define ('LIBEXSLT_VERSION', 820);

/**
 * libexslt version like 1.1.17.
 * @link http://www.php.net/manual/en/xsl.constants.php
 * @var string
 */
define ('LIBEXSLT_DOTTED_VERSION', "0.8.20");

// End of xsl v.8.2.6
