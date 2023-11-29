<?php

// Start of xsl v.8.3.0

class XSLTProcessor  {

	/**
	 * {@inheritdoc}
	 * @param object $stylesheet
	 */
	public function importStylesheet (object $stylesheet) {}

	/**
	 * {@inheritdoc}
	 * @param object $document
	 * @param string|null $returnClass [optional]
	 */
	public function transformToDoc (object $document, ?string $returnClass = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param object $document
	 * @param string $uri
	 */
	public function transformToUri (object $document, string $uri) {}

	/**
	 * {@inheritdoc}
	 * @param object $document
	 */
	public function transformToXml (object $document) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param array|string $name
	 * @param string|null $value [optional]
	 */
	public function setParameter (string $namespace, array|string $name, ?string $value = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param string $name
	 */
	public function getParameter (string $namespace, string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param string $name
	 */
	public function removeParameter (string $namespace, string $name) {}

	/**
	 * {@inheritdoc}
	 */
	public function hasExsltSupport () {}

	/**
	 * {@inheritdoc}
	 * @param array|string|null $functions [optional]
	 */
	public function registerPHPFunctions (array|string|null $functions = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $filename
	 */
	public function setProfiling (?string $filename = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $preferences
	 */
	public function setSecurityPrefs (int $preferences) {}

	/**
	 * {@inheritdoc}
	 */
	public function getSecurityPrefs () {}

}
define ('XSL_CLONE_AUTO', 0);
define ('XSL_CLONE_NEVER', -1);
define ('XSL_CLONE_ALWAYS', 1);
define ('XSL_SECPREF_NONE', 0);
define ('XSL_SECPREF_READ_FILE', 2);
define ('XSL_SECPREF_WRITE_FILE', 4);
define ('XSL_SECPREF_CREATE_DIRECTORY', 8);
define ('XSL_SECPREF_READ_NETWORK', 16);
define ('XSL_SECPREF_WRITE_NETWORK', 32);
define ('XSL_SECPREF_DEFAULT', 44);
define ('LIBXSLT_VERSION', 10135);
define ('LIBXSLT_DOTTED_VERSION', "1.1.35");
define ('LIBEXSLT_VERSION', 820);
define ('LIBEXSLT_DOTTED_VERSION', "0.8.20");

// End of xsl v.8.3.0
