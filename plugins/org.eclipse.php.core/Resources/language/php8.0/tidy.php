<?php

// Start of tidy v.8.0.28

/**
 * An HTML node in an HTML file, as detected by tidy.
 * @link http://www.php.net/manual/en/class.tidy.php
 */
class tidy  {

	public $errorBuffer;

	/**
	 * The HTML representation of the node, including the surrounding tags.
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.tidy.php#tidy.props.value
	 */
	public $value;

	/**
	 * Constructs a new tidy object
	 * @link http://www.php.net/manual/en/tidy.construct.php
	 * @param ?string|null $filename [optional]
	 * @param array|string|null|null $config [optional]
	 * @param ?string|null $encoding [optional]
	 * @param bool $useIncludePath [optional]
	 */
	public function __construct (?string|null $filename = nullarray|string|null|null , $config = null?string|null , $encoding = nullbool , $useIncludePath = '') {}

	/**
	 * Returns the value of the specified configuration option for the tidy document
	 * @link http://www.php.net/manual/en/tidy.getopt.php
	 * @param string $option You will find a list with each configuration option and their types
	 * at: url.tidy.conf.
	 * @return mixed the value of the specified option.
	 * The return type depends on the type of the specified one.
	 */
	public function getOpt (string $option) {}

	/**
	 * Execute configured cleanup and repair operations on parsed markup
	 * @link http://www.php.net/manual/en/tidy.cleanrepair.php
	 * @return bool true on success or false on failure
	 */
	public function cleanRepair () {}

	/**
	 * Parse markup in file or URI
	 * @link http://www.php.net/manual/en/tidy.parsefile.php
	 * @param string $filename If the filename parameter is given, this function
	 * will also read that file and initialize the object with the file,
	 * acting like tidy_parse_file.
	 * @param mixed $config [optional] <p>
	 * The config config can be passed either as an
	 * array or as a string. If a string is passed, it is interpreted as the
	 * name of the configuration file, otherwise, it is interpreted as the
	 * options themselves.
	 * </p>
	 * <p>
	 * For an explanation about each option, see
	 * url.tidy.conf.
	 * </p>
	 * @param mixed $encoding [optional] The encoding parameter sets the encoding for
	 * input/output documents. The possible values for encoding are: 
	 * ascii, latin0, latin1,
	 * raw, utf8, iso2022,
	 * mac, win1252, ibm858,
	 * utf16, utf16le, utf16be,
	 * big5, and shiftjis.
	 * @param bool $useIncludePath [optional] Search for the file in the include_path.
	 * @return bool tidy::parseFile returns true on success.
	 * tidy_parse_file returns a new tidy
	 * instance on success.
	 * Both, the method and the function return false on failure.
	 */
	public function parseFile (string $filename, $config = null, $encoding = null, bool $useIncludePath = null) {}

	/**
	 * Parse a document stored in a string
	 * @link http://www.php.net/manual/en/tidy.parsestring.php
	 * @param string $string The data to be parsed.
	 * @param mixed $config [optional] <p>
	 * The config config can be passed either as an
	 * array or as a string. If a string is passed, it is interpreted as the
	 * name of the configuration file, otherwise, it is interpreted as the
	 * options themselves.
	 * </p>
	 * <p>
	 * For an explanation about each option, visit url.tidy.conf.
	 * </p>
	 * @param mixed $encoding [optional] The encoding parameter sets the encoding for
	 * input/output documents. The possible values for encoding are: 
	 * ascii, latin0, latin1,
	 * raw, utf8, iso2022,
	 * mac, win1252, ibm858,
	 * utf16, utf16le, utf16be,
	 * big5, and shiftjis.
	 * @return bool tidy::parseString returns true on success.
	 * tidy_parse_string returns a new tidy
	 * instance on success.
	 * Both, the method and the function return false on failure.
	 */
	public function parseString (string $string, $config = null, $encoding = null) {}

	/**
	 * Repair a string using an optionally provided configuration file
	 * @link http://www.php.net/manual/en/tidy.repairstring.php
	 * @param string $string The data to be repaired.
	 * @param mixed $config [optional] <p>
	 * The config config can be passed either as an
	 * array or as a string. If a string is passed, it is interpreted as the
	 * name of the configuration file, otherwise, it is interpreted as the
	 * options themselves.
	 * </p>
	 * <p>
	 * Check url.tidy.conf for
	 * an explanation about each option.
	 * </p>
	 * @param mixed $encoding [optional] The encoding parameter sets the encoding for
	 * input/output documents. The possible values for encoding are: 
	 * ascii, latin0, latin1,
	 * raw, utf8, iso2022,
	 * mac, win1252, ibm858,
	 * utf16, utf16le, utf16be,
	 * big5, and shiftjis.
	 * @return mixed the repaired string, or false on failure.
	 */
	public static function repairString (string $string, $config = null, $encoding = null) {}

	/**
	 * Repair a file and return it as a string
	 * @link http://www.php.net/manual/en/tidy.repairfile.php
	 * @param string $filename The file to be repaired.
	 * @param mixed $config [optional] <p>
	 * The config config can be passed either as an
	 * array or as a string. If a string is passed, it is interpreted as the
	 * name of the configuration file, otherwise, it is interpreted as the
	 * options themselves.
	 * </p>
	 * <p>
	 * Check http://tidy.sourceforge.net/docs/quickref.html for an
	 * explanation about each option.
	 * </p>
	 * @param mixed $encoding [optional] The encoding parameter sets the encoding for
	 * input/output documents. The possible values for encoding are: 
	 * ascii, latin0, latin1,
	 * raw, utf8, iso2022,
	 * mac, win1252, ibm858,
	 * utf16, utf16le, utf16be,
	 * big5, and shiftjis.
	 * @param bool $useIncludePath [optional] Search for the file in the include_path.
	 * @return mixed the repaired contents as a string, or false on failure.
	 */
	public static function repairFile (string $filename, $config = null, $encoding = null, bool $useIncludePath = null) {}

	/**
	 * Run configured diagnostics on parsed and repaired markup
	 * @link http://www.php.net/manual/en/tidy.diagnose.php
	 * @return bool true on success or false on failure
	 */
	public function diagnose () {}

	/**
	 * Get release date (version) for Tidy library
	 * @link http://www.php.net/manual/en/tidy.getrelease.php
	 * @return string a string with the release date of the Tidy library,
	 * which may be 'unknown'.
	 */
	public function getRelease () {}

	/**
	 * Get current Tidy configuration
	 * @link http://www.php.net/manual/en/tidy.getconfig.php
	 * @return array an array of configuration options.
	 * <p>
	 * For an explanation about each option, visit url.tidy.conf.
	 * </p>
	 */
	public function getConfig () {}

	/**
	 * Get status of specified document
	 * @link http://www.php.net/manual/en/tidy.getstatus.php
	 * @return int 0 if no error/warning was raised, 1 for warnings or accessibility
	 * errors, or 2 for errors.
	 */
	public function getStatus () {}

	/**
	 * Get the Detected HTML version for the specified document
	 * @link http://www.php.net/manual/en/tidy.gethtmlver.php
	 * @return int the detected HTML version.
	 * <p>
	 * This function is not yet implemented in the Tidylib itself, so it always
	 * return 0.
	 * </p>
	 */
	public function getHtmlVer () {}

	/**
	 * Returns the documentation for the given option name
	 * @link http://www.php.net/manual/en/tidy.getoptdoc.php
	 * @param string $option The option name
	 * @return mixed a string if the option exists and has documentation available, or
	 * false otherwise.
	 */
	public function getOptDoc (string $option) {}

	/**
	 * Indicates if the document is a XHTML document
	 * @link http://www.php.net/manual/en/tidy.isxhtml.php
	 * @return bool This function returns true if the specified tidy
	 * tidy is a XHTML document, or false otherwise.
	 * <p>
	 * This function is not yet implemented in the Tidylib itself, so it always
	 * return false.
	 * </p>
	 */
	public function isXhtml () {}

	/**
	 * Indicates if the document is a generic (non HTML/XHTML) XML document
	 * @link http://www.php.net/manual/en/tidy.isxml.php
	 * @return bool This function returns true if the specified tidy
	 * tidy is a generic XML document (non HTML/XHTML),
	 * or false otherwise.
	 * <p>
	 * This function is not yet implemented in the Tidylib itself, so it always
	 * return false.
	 * </p>
	 */
	public function isXml () {}

	/**
	 * Returns a tidyNode object representing the root of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.root.php
	 * @return mixed the tidyNode object.
	 */
	public function root () {}

	/**
	 * Returns a tidyNode object starting from the &lt;head&gt; tag of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.head.php
	 * @return mixed the tidyNode object.
	 */
	public function head () {}

	/**
	 * Returns a tidyNode object starting from the &lt;html&gt; tag of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.html.php
	 * @return mixed the tidyNode object.
	 */
	public function html () {}

	/**
	 * Returns a tidyNode object starting from the &lt;body&gt; tag of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.body.php
	 * @return mixed a tidyNode object starting from the 
	 * &lt;body&gt; tag of the tidy parse tree.
	 */
	public function body () {}

}

/**
 * An HTML node in an HTML file, as detected by tidy.
 * @link http://www.php.net/manual/en/class.tidynode.php
 */
final class tidyNode  {

	/**
	 * The HTML representation of the node, including the surrounding tags.
	 * @var string
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.value
	 */
	public $value;

	/**
	 * The name of the HTML node
	 * @var string
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.name
	 */
	public $name;

	/**
	 * The type of the node (one of the nodetype constants, e.g. TIDY_NODETYPE_PHP)
	 * @var int
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.type
	 */
	public $type;

	/**
	 * The line number at which the tags is located in the file
	 * @var int
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.line
	 */
	public $line;

	/**
	 * The column number at which the tags is located in the file
	 * @var int
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.column
	 */
	public $column;

	/**
	 * Indicates if the node is a proprietary tag
	 * @var bool
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.proprietary
	 */
	public $proprietary;

	/**
	 * The ID of the node (one of the tag constants, e.g. TIDY_TAG_FRAME)
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.id
	 */
	public $id;

	/**
	 * An array of string, representing
	 * the attributes names (as keys) of the current node.
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.attribute
	 */
	public $attribute;

	/**
	 * An array of tidyNode, representing
	 * the children of the current node.
	 * @var mixed
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.child
	 */
	public $child;

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/tidynode.construct.php
	 */
	private function __construct () {}

	/**
	 * Checks if a node has children
	 * @link http://www.php.net/manual/en/tidynode.haschildren.php
	 * @return bool true if the node has children, false otherwise.
	 */
	public function hasChildren (): bool {}

	/**
	 * Checks if a node has siblings
	 * @link http://www.php.net/manual/en/tidynode.hassiblings.php
	 * @return bool true if the node has siblings, false otherwise.
	 */
	public function hasSiblings (): bool {}

	/**
	 * Checks if a node represents a comment
	 * @link http://www.php.net/manual/en/tidynode.iscomment.php
	 * @return bool true if the node is a comment, false otherwise.
	 */
	public function isComment (): bool {}

	/**
	 * Checks if a node is an element node
	 * @link http://www.php.net/manual/en/tidynode.ishtml.php
	 * @return bool true if the node is an element node, but not the root node of the document, false otherwise.
	 */
	public function isHtml (): bool {}

	/**
	 * Checks if a node represents text (no markup)
	 * @link http://www.php.net/manual/en/tidynode.istext.php
	 * @return bool true if the node represent a text, false otherwise.
	 */
	public function isText (): bool {}

	/**
	 * Checks if this node is JSTE
	 * @link http://www.php.net/manual/en/tidynode.isjste.php
	 * @return bool true if the node is JSTE, false otherwise.
	 */
	public function isJste (): bool {}

	/**
	 * Checks if this node is ASP
	 * @link http://www.php.net/manual/en/tidynode.isasp.php
	 * @return bool true if the node is ASP, false otherwise.
	 */
	public function isAsp (): bool {}

	/**
	 * Checks if a node is PHP
	 * @link http://www.php.net/manual/en/tidynode.isphp.php
	 * @return bool true if the current node is PHP code, false otherwise.
	 */
	public function isPhp (): bool {}

	/**
	 * Returns the parent node of the current node
	 * @link http://www.php.net/manual/en/tidynode.getparent.php
	 * @return mixed a tidyNode if the node has a parent, or null
	 * otherwise.
	 */
	public function getParent (): ??tidyNode {}

}

/**
 * @param string $string
 * @param array|string|null|null $config [optional]
 * @param ?string|null $encoding [optional]
 */
function tidy_parse_string (string $stringarray|string|null|null , $config = null?string|null , $encoding = null): tidy|false {}

/**
 * @param tidy $tidy
 */
function tidy_get_error_buffer (tidy $tidy): string|false {}

/**
 * Return a string representing the parsed tidy markup
 * @link http://www.php.net/manual/en/function.tidy-get-output.php
 * @param tidy $tidy The Tidy object.
 * @return string the parsed tidy markup.
 */
function tidy_get_output (tidy $tidy): string {}

/**
 * @param string $filename
 * @param array|string|null|null $config [optional]
 * @param ?string|null $encoding [optional]
 * @param bool $useIncludePath [optional]
 */
function tidy_parse_file (string $filenamearray|string|null|null , $config = null?string|null , $encoding = nullbool , $useIncludePath = ''): tidy|false {}

/**
 * @param tidy $tidy
 */
function tidy_clean_repair (tidy $tidy): bool {}

/**
 * @param string $string
 * @param array|string|null|null $config [optional]
 * @param ?string|null $encoding [optional]
 */
function tidy_repair_string (string $stringarray|string|null|null , $config = null?string|null , $encoding = null): string|false {}

/**
 * @param string $filename
 * @param array|string|null|null $config [optional]
 * @param ?string|null $encoding [optional]
 * @param bool $useIncludePath [optional]
 */
function tidy_repair_file (string $filenamearray|string|null|null , $config = null?string|null , $encoding = nullbool , $useIncludePath = ''): string|false {}

/**
 * @param tidy $tidy
 */
function tidy_diagnose (tidy $tidy): bool {}

function tidy_get_release (): string {}

/**
 * @param tidy $tidy
 * @param string $option
 */
function tidy_get_opt_doc (tidy $tidystring , $option): string|false {}

/**
 * @param tidy $tidy
 */
function tidy_get_config (tidy $tidy): array {}

/**
 * @param tidy $tidy
 */
function tidy_get_status (tidy $tidy): int {}

/**
 * @param tidy $tidy
 */
function tidy_get_html_ver (tidy $tidy): int {}

/**
 * @param tidy $tidy
 */
function tidy_is_xhtml (tidy $tidy): bool {}

/**
 * @param tidy $tidy
 */
function tidy_is_xml (tidy $tidy): bool {}

/**
 * Returns the Number of Tidy errors encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-error-count.php
 * @param tidy $tidy The Tidy object.
 * @return int the number of errors.
 */
function tidy_error_count (tidy $tidy): int {}

/**
 * Returns the Number of Tidy warnings encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-warning-count.php
 * @param tidy $tidy The Tidy object.
 * @return int the number of warnings.
 */
function tidy_warning_count (tidy $tidy): int {}

/**
 * Returns the Number of Tidy accessibility warnings encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-access-count.php
 * @param tidy $tidy The Tidy object.
 * @return int the number of warnings.
 */
function tidy_access_count (tidy $tidy): int {}

/**
 * Returns the Number of Tidy configuration errors encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-config-count.php
 * @param tidy $tidy The Tidy object.
 * @return int the number of errors.
 */
function tidy_config_count (tidy $tidy): int {}

/**
 * @param tidy $tidy
 * @param string $option
 */
function tidy_getopt (tidy $tidystring , $option): string|int|bool {}

/**
 * @param tidy $tidy
 */
function tidy_get_root (tidy $tidy): ??tidyNode {}

/**
 * @param tidy $tidy
 */
function tidy_get_html (tidy $tidy): ??tidyNode {}

/**
 * @param tidy $tidy
 */
function tidy_get_head (tidy $tidy): ??tidyNode {}

/**
 * @param tidy $tidy
 */
function tidy_get_body (tidy $tidy): ??tidyNode {}


/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_UNKNOWN', 0);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_A', 1);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ABBR', 2);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ACRONYM', 3);
define ('TIDY_TAG_ADDRESS', 4);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ALIGN', 5);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_APPLET', 6);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_AREA', 7);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_B', 8);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BASE', 9);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BASEFONT', 10);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BDO', 11);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BGSOUND', 12);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BIG', 13);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BLINK', 14);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BLOCKQUOTE', 15);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BODY', 16);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BR', 17);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BUTTON', 18);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_CAPTION', 19);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_CENTER', 20);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_CITE', 21);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_CODE', 22);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_COL', 23);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_COLGROUP', 24);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_COMMENT', 25);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DD', 26);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DEL', 27);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DFN', 28);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DIR', 29);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DIV', 30);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DL', 31);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DT', 32);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_EM', 33);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_EMBED', 34);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FIELDSET', 35);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FONT', 36);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FORM', 37);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FRAME', 38);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FRAMESET', 39);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_H1', 40);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_H2', 41);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_H3', 42);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_H4', 43);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_H5', 44);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_H6', 45);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_HEAD', 46);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_HR', 47);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_HTML', 48);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_I', 49);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_IFRAME', 50);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ILAYER', 51);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_IMG', 52);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_INPUT', 53);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_INS', 54);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ISINDEX', 55);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_KBD', 56);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_KEYGEN', 57);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_LABEL', 58);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_LAYER', 59);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_LEGEND', 60);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_LI', 61);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_LINK', 62);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_LISTING', 63);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MAP', 64);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MARQUEE', 66);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MENU', 67);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_META', 68);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MULTICOL', 69);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NOBR', 70);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NOEMBED', 71);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NOFRAMES', 72);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NOLAYER', 73);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NOSAVE', 74);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NOSCRIPT', 75);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_OBJECT', 76);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_OL', 77);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_OPTGROUP', 78);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_OPTION', 79);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_P', 80);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_PARAM', 81);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_PLAINTEXT', 83);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_PRE', 84);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_Q', 85);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_RB', 86);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_RBC', 87);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_RP', 88);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_RT', 89);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_RTC', 90);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_RUBY', 91);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_S', 92);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SAMP', 93);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SCRIPT', 94);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SELECT', 95);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SERVER', 96);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SERVLET', 97);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SMALL', 98);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SPACER', 99);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SPAN', 100);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_STRIKE', 101);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_STRONG', 102);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_STYLE', 103);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SUB', 104);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SUP', 105);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TABLE', 107);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TBODY', 108);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TD', 109);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TEXTAREA', 110);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TFOOT', 111);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TH', 112);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_THEAD', 113);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TITLE', 114);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TR', 115);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TT', 116);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_U', 117);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_UL', 118);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_VAR', 119);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_WBR', 120);

/**
 * 
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_XMP', 121);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ARTICLE', 123);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_ASIDE', 124);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_AUDIO', 125);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_BDI', 126);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_CANVAS', 127);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_COMMAND', 128);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DATALIST', 130);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DETAILS', 131);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_DIALOG', 132);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FIGCAPTION', 133);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FIGURE', 134);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_FOOTER', 135);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_HEADER', 136);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_HGROUP', 137);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MAIN', 138);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MARK', 139);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_MENUITEM', 140);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_METER', 141);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_NAV', 142);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_OUTPUT', 143);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_PROGRESS', 144);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SECTION', 145);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SOURCE', 146);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_SUMMARY', 147);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TEMPLATE', 148);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TIME', 149);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_TRACK', 150);

/**
 * Added in libtidy 5.0.0. Available as of PHP 7.4.0.
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_TAG_VIDEO', 151);

/**
 * root node
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_ROOT', 0);

/**
 * doctype
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_DOCTYPE', 1);

/**
 * HTML comment
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_COMMENT', 2);

/**
 * Processing Instruction
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_PROCINS', 3);

/**
 * Text
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_TEXT', 4);

/**
 * start tag
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_START', 5);

/**
 * end tag
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_END', 6);

/**
 * empty tag
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_STARTEND', 7);

/**
 * CDATA
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_CDATA', 8);

/**
 * XML section
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_SECTION', 9);

/**
 * ASP code
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_ASP', 10);

/**
 * JSTE code
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_JSTE', 11);

/**
 * PHP code
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_PHP', 12);

/**
 * XML declaration
 * @link http://www.php.net/manual/en/tidy.constants.php
 */
define ('TIDY_NODETYPE_XMLDECL', 13);

// End of tidy v.8.0.28
