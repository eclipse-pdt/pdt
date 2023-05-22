<?php

// Start of tidy v.8.2.6

/**
 * An HTML node in an HTML file, as detected by tidy.
 * @link http://www.php.net/manual/en/class.tidy.php
 */
class tidy  {

	/**
	 * Return warnings and errors which occurred parsing the specified document
	 * @var string
	 * @link http://www.php.net/manual/en/class.tidy.php#tidy.props.errorbuffer
	 */
	public string $errorBuffer;

	/**
	 * The HTML representation of the node, including the surrounding tags.
	 * @var string|null
	 * @link http://www.php.net/manual/en/class.tidy.php#tidy.props.value
	 */
	public ?string $value;

	/**
	 * Constructs a new tidy object
	 * @link http://www.php.net/manual/en/tidy.construct.php
	 * @param string|null $filename [optional] 
	 * @param array|string|null $config [optional] 
	 * @param string|null $encoding [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @return string|null 
	 */
	public function __construct (?string $filename = null, array|string|null $config = null, ?string $encoding = null, bool $useIncludePath = false): ?string {}

	/**
	 * Returns the value of the specified configuration option for the tidy document
	 * @link http://www.php.net/manual/en/tidy.getopt.php
	 * @param string $option 
	 * @return string|int|bool Returns the value of the specified option.
	 * The return type depends on the type of the specified one.
	 */
	public function getOpt (string $option): string|int|bool {}

	/**
	 * Execute configured cleanup and repair operations on parsed markup
	 * @link http://www.php.net/manual/en/tidy.cleanrepair.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function cleanRepair (): bool {}

	/**
	 * Parse markup in file or URI
	 * @link http://www.php.net/manual/en/tidy.parsefile.php
	 * @param string $filename 
	 * @param array|string|null $config [optional] 
	 * @param string|null $encoding [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @return bool tidy::parseFile returns true on success.
	 * tidy_parse_file returns a new tidy
	 * instance on success.
	 * Both, the method and the function return false on failure.
	 */
	public function parseFile (string $filename, array|string|null $config = null, ?string $encoding = null, bool $useIncludePath = false): bool {}

	/**
	 * Parse a document stored in a string
	 * @link http://www.php.net/manual/en/tidy.parsestring.php
	 * @param string $string 
	 * @param array|string|null $config [optional] 
	 * @param string|null $encoding [optional] 
	 * @return bool tidy::parseString returns true on success.
	 * tidy_parse_string returns a new tidy
	 * instance on success.
	 * Both, the method and the function return false on failure.
	 */
	public function parseString (string $string, array|string|null $config = null, ?string $encoding = null): bool {}

	/**
	 * Repair a string using an optionally provided configuration file
	 * @link http://www.php.net/manual/en/tidy.repairstring.php
	 * @param string $string 
	 * @param array|string|null $config [optional] 
	 * @param string|null $encoding [optional] 
	 * @return string|false Returns the repaired string, or false on failure.
	 */
	public static function repairString (string $string, array|string|null $config = null, ?string $encoding = null): string|false {}

	/**
	 * Repair a file and return it as a string
	 * @link http://www.php.net/manual/en/tidy.repairfile.php
	 * @param string $filename 
	 * @param array|string|null $config [optional] 
	 * @param string|null $encoding [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @return string|false Returns the repaired contents as a string, or false on failure.
	 */
	public static function repairFile (string $filename, array|string|null $config = null, ?string $encoding = null, bool $useIncludePath = false): string|false {}

	/**
	 * Run configured diagnostics on parsed and repaired markup
	 * @link http://www.php.net/manual/en/tidy.diagnose.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function diagnose (): bool {}

	/**
	 * Get release date (version) for Tidy library
	 * @link http://www.php.net/manual/en/tidy.getrelease.php
	 * @return string Returns a string with the release date of the Tidy library,
	 * which may be 'unknown'.
	 */
	public function getRelease (): string {}

	/**
	 * Get current Tidy configuration
	 * @link http://www.php.net/manual/en/tidy.getconfig.php
	 * @return array Returns an array of configuration options.
	 * <p>For an explanation about each option, visit http://api.html-tidy.org/#quick-reference.</p>
	 */
	public function getConfig (): array {}

	/**
	 * Get status of specified document
	 * @link http://www.php.net/manual/en/tidy.getstatus.php
	 * @return int Returns 0 if no error/warning was raised, 1 for warnings or accessibility
	 * errors, or 2 for errors.
	 */
	public function getStatus (): int {}

	/**
	 * Get the Detected HTML version for the specified document
	 * @link http://www.php.net/manual/en/tidy.gethtmlver.php
	 * @return int Returns the detected HTML version.
	 * <p>This function is not yet implemented in the Tidylib itself, so it always
	 * return 0.</p>
	 */
	public function getHtmlVer (): int {}

	/**
	 * Returns the documentation for the given option name
	 * @link http://www.php.net/manual/en/tidy.getoptdoc.php
	 * @param string $option 
	 * @return string|false Returns a string if the option exists and has documentation available, or
	 * false otherwise.
	 */
	public function getOptDoc (string $option): string|false {}

	/**
	 * Indicates if the document is a XHTML document
	 * @link http://www.php.net/manual/en/tidy.isxhtml.php
	 * @return bool This function returns true if the specified tidy
	 * tidy is a XHTML document, or false otherwise.
	 * <p>This function is not yet implemented in the Tidylib itself, so it always
	 * return false.</p>
	 */
	public function isXhtml (): bool {}

	/**
	 * Indicates if the document is a generic (non HTML/XHTML) XML document
	 * @link http://www.php.net/manual/en/tidy.isxml.php
	 * @return bool This function returns true if the specified tidy
	 * tidy is a generic XML document (non HTML/XHTML),
	 * or false otherwise.
	 * <p>This function is not yet implemented in the Tidylib itself, so it always
	 * return false.</p>
	 */
	public function isXml (): bool {}

	/**
	 * Returns a tidyNode object representing the root of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.root.php
	 * @return tidyNode|null Returns the tidyNode object.
	 */
	public function root (): ?tidyNode {}

	/**
	 * Returns a tidyNode object starting from the tag of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.head.php
	 * @return tidyNode|null Returns the tidyNode object.
	 */
	public function head (): ?tidyNode {}

	/**
	 * Returns a tidyNode object starting from the tag of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.html.php
	 * @return tidyNode|null Returns the tidyNode object.
	 */
	public function html (): ?tidyNode {}

	/**
	 * Returns a tidyNode object starting from the tag of the tidy parse tree
	 * @link http://www.php.net/manual/en/tidy.body.php
	 * @return tidyNode|null Returns a tidyNode object starting from the 
	 * &lt;body&gt; tag of the tidy parse tree.
	 */
	public function body (): ?tidyNode {}

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
	public readonly string $value;

	/**
	 * The name of the HTML node
	 * @var string
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.name
	 */
	public readonly string $name;

	/**
	 * The type of the node (one of the nodetype constants, e.g. TIDY_NODETYPE_PHP)
	 * @var int
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.type
	 */
	public readonly int $type;

	/**
	 * The line number at which the tags is located in the file
	 * @var int
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.line
	 */
	public readonly int $line;

	/**
	 * The column number at which the tags is located in the file
	 * @var int
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.column
	 */
	public readonly int $column;

	/**
	 * Indicates if the node is a proprietary tag
	 * @var bool
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.proprietary
	 */
	public readonly bool $proprietary;

	/**
	 * The ID of the node (one of the tag constants, e.g. TIDY_TAG_FRAME)
	 * @var int|null
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.id
	 */
	public readonly ?int $id;

	/**
	 * An array of string, representing
	 * the attributes names (as keys) of the current node.
	 * @var array|null
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.attribute
	 */
	public readonly ?array $attribute;

	/**
	 * An array of tidyNode, representing
	 * the children of the current node.
	 * @var array|null
	 * @link http://www.php.net/manual/en/class.tidynode.php#tidynode.props.child
	 */
	public readonly ?array $child;

	/**
	 * Private constructor to disallow direct instantiation
	 * @link http://www.php.net/manual/en/tidynode.construct.php
	 */
	private function __construct () {}

	/**
	 * Checks if a node has children
	 * @link http://www.php.net/manual/en/tidynode.haschildren.php
	 * @return bool Returns true if the node has children, false otherwise.
	 */
	public function hasChildren (): bool {}

	/**
	 * Checks if a node has siblings
	 * @link http://www.php.net/manual/en/tidynode.hassiblings.php
	 * @return bool Returns true if the node has siblings, false otherwise.
	 */
	public function hasSiblings (): bool {}

	/**
	 * Checks if a node represents a comment
	 * @link http://www.php.net/manual/en/tidynode.iscomment.php
	 * @return bool Returns true if the node is a comment, false otherwise.
	 */
	public function isComment (): bool {}

	/**
	 * Checks if a node is an element node
	 * @link http://www.php.net/manual/en/tidynode.ishtml.php
	 * @return bool Returns true if the node is an element node, but not the root node of the document, false otherwise.
	 */
	public function isHtml (): bool {}

	/**
	 * Checks if a node represents text (no markup)
	 * @link http://www.php.net/manual/en/tidynode.istext.php
	 * @return bool Returns true if the node represent a text, false otherwise.
	 */
	public function isText (): bool {}

	/**
	 * Checks if this node is JSTE
	 * @link http://www.php.net/manual/en/tidynode.isjste.php
	 * @return bool Returns true if the node is JSTE, false otherwise.
	 */
	public function isJste (): bool {}

	/**
	 * Checks if this node is ASP
	 * @link http://www.php.net/manual/en/tidynode.isasp.php
	 * @return bool Returns true if the node is ASP, false otherwise.
	 */
	public function isAsp (): bool {}

	/**
	 * Checks if a node is PHP
	 * @link http://www.php.net/manual/en/tidynode.isphp.php
	 * @return bool Returns true if the current node is PHP code, false otherwise.
	 */
	public function isPhp (): bool {}

	/**
	 * Returns the parent node of the current node
	 * @link http://www.php.net/manual/en/tidynode.getparent.php
	 * @return tidyNode|null Returns a tidyNode if the node has a parent, or null
	 * otherwise.
	 */
	public function getParent (): ?tidyNode {}

}

/**
 * Parse a document stored in a string
 * @link http://www.php.net/manual/en/tidy.parsestring.php
 * @param string $string 
 * @param array|string|null $config [optional] 
 * @param string|null $encoding [optional] 
 * @return tidy|false tidy::parseString returns true on success.
 * tidy_parse_string returns a new tidy
 * instance on success.
 * Both, the method and the function return false on failure.
 */
function tidy_parse_string (string $string, array|string|null $config = null, ?string $encoding = null): tidy|false {}

/**
 * Return warnings and errors which occurred parsing the specified document
 * @link http://www.php.net/manual/en/tidy.props.errorbuffer.php
 * @param tidy $tidy 
 * @return string|false Returns the error buffer as a string, or false if the buffer is empty.
 */
function tidy_get_error_buffer (tidy $tidy): string|false {}

/**
 * Return a string representing the parsed tidy markup
 * @link http://www.php.net/manual/en/function.tidy-get-output.php
 * @param tidy $tidy 
 * @return string Returns the parsed tidy markup.
 */
function tidy_get_output (tidy $tidy): string {}

/**
 * Parse markup in file or URI
 * @link http://www.php.net/manual/en/tidy.parsefile.php
 * @param string $filename 
 * @param array|string|null $config [optional] 
 * @param string|null $encoding [optional] 
 * @param bool $useIncludePath [optional] 
 * @return tidy|false tidy::parseFile returns true on success.
 * tidy_parse_file returns a new tidy
 * instance on success.
 * Both, the method and the function return false on failure.
 */
function tidy_parse_file (string $filename, array|string|null $config = null, ?string $encoding = null, bool $useIncludePath = false): tidy|false {}

/**
 * Execute configured cleanup and repair operations on parsed markup
 * @link http://www.php.net/manual/en/tidy.cleanrepair.php
 * @param tidy $tidy 
 * @return bool Returns true on success or false on failure.
 */
function tidy_clean_repair (tidy $tidy): bool {}

/**
 * Repair a string using an optionally provided configuration file
 * @link http://www.php.net/manual/en/tidy.repairstring.php
 * @param string $string 
 * @param array|string|null $config [optional] 
 * @param string|null $encoding [optional] 
 * @return string|false Returns the repaired string, or false on failure.
 */
function tidy_repair_string (string $string, array|string|null $config = null, ?string $encoding = null): string|false {}

/**
 * Repair a file and return it as a string
 * @link http://www.php.net/manual/en/tidy.repairfile.php
 * @param string $filename 
 * @param array|string|null $config [optional] 
 * @param string|null $encoding [optional] 
 * @param bool $useIncludePath [optional] 
 * @return string|false Returns the repaired contents as a string, or false on failure.
 */
function tidy_repair_file (string $filename, array|string|null $config = null, ?string $encoding = null, bool $useIncludePath = false): string|false {}

/**
 * Run configured diagnostics on parsed and repaired markup
 * @link http://www.php.net/manual/en/tidy.diagnose.php
 * @param tidy $tidy 
 * @return bool Returns true on success or false on failure.
 */
function tidy_diagnose (tidy $tidy): bool {}

/**
 * Get release date (version) for Tidy library
 * @link http://www.php.net/manual/en/tidy.getrelease.php
 * @return string Returns a string with the release date of the Tidy library,
 * which may be 'unknown'.
 */
function tidy_get_release (): string {}

/**
 * Returns the documentation for the given option name
 * @link http://www.php.net/manual/en/tidy.getoptdoc.php
 * @param tidy $tidy 
 * @param string $option 
 * @return string|false Returns a string if the option exists and has documentation available, or
 * false otherwise.
 */
function tidy_get_opt_doc (tidy $tidy, string $option): string|false {}

/**
 * Get current Tidy configuration
 * @link http://www.php.net/manual/en/tidy.getconfig.php
 * @param tidy $tidy 
 * @return array Returns an array of configuration options.
 * <p>For an explanation about each option, visit http://api.html-tidy.org/#quick-reference.</p>
 */
function tidy_get_config (tidy $tidy): array {}

/**
 * Get status of specified document
 * @link http://www.php.net/manual/en/tidy.getstatus.php
 * @param tidy $tidy 
 * @return int Returns 0 if no error/warning was raised, 1 for warnings or accessibility
 * errors, or 2 for errors.
 */
function tidy_get_status (tidy $tidy): int {}

/**
 * Get the Detected HTML version for the specified document
 * @link http://www.php.net/manual/en/tidy.gethtmlver.php
 * @param tidy $tidy 
 * @return int Returns the detected HTML version.
 * <p>This function is not yet implemented in the Tidylib itself, so it always
 * return 0.</p>
 */
function tidy_get_html_ver (tidy $tidy): int {}

/**
 * Indicates if the document is a XHTML document
 * @link http://www.php.net/manual/en/tidy.isxhtml.php
 * @param tidy $tidy 
 * @return bool This function returns true if the specified tidy
 * tidy is a XHTML document, or false otherwise.
 * <p>This function is not yet implemented in the Tidylib itself, so it always
 * return false.</p>
 */
function tidy_is_xhtml (tidy $tidy): bool {}

/**
 * Indicates if the document is a generic (non HTML/XHTML) XML document
 * @link http://www.php.net/manual/en/tidy.isxml.php
 * @param tidy $tidy 
 * @return bool This function returns true if the specified tidy
 * tidy is a generic XML document (non HTML/XHTML),
 * or false otherwise.
 * <p>This function is not yet implemented in the Tidylib itself, so it always
 * return false.</p>
 */
function tidy_is_xml (tidy $tidy): bool {}

/**
 * Returns the Number of Tidy errors encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-error-count.php
 * @param tidy $tidy 
 * @return int Returns the number of errors.
 */
function tidy_error_count (tidy $tidy): int {}

/**
 * Returns the Number of Tidy warnings encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-warning-count.php
 * @param tidy $tidy 
 * @return int Returns the number of warnings.
 */
function tidy_warning_count (tidy $tidy): int {}

/**
 * Returns the Number of Tidy accessibility warnings encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-access-count.php
 * @param tidy $tidy 
 * @return int Returns the number of warnings.
 */
function tidy_access_count (tidy $tidy): int {}

/**
 * Returns the Number of Tidy configuration errors encountered for specified document
 * @link http://www.php.net/manual/en/function.tidy-config-count.php
 * @param tidy $tidy 
 * @return int Returns the number of errors.
 */
function tidy_config_count (tidy $tidy): int {}

/**
 * Returns the value of the specified configuration option for the tidy document
 * @link http://www.php.net/manual/en/tidy.getopt.php
 * @param tidy $tidy 
 * @param string $option 
 * @return string|int|bool Returns the value of the specified option.
 * The return type depends on the type of the specified one.
 */
function tidy_getopt (tidy $tidy, string $option): string|int|bool {}

/**
 * Returns a tidyNode object representing the root of the tidy parse tree
 * @link http://www.php.net/manual/en/tidy.root.php
 * @param tidy $tidy 
 * @return tidyNode|null Returns the tidyNode object.
 */
function tidy_get_root (tidy $tidy): ?tidyNode {}

/**
 * Returns a tidyNode object starting from the tag of the tidy parse tree
 * @link http://www.php.net/manual/en/tidy.html.php
 * @param tidy $tidy 
 * @return tidyNode|null Returns the tidyNode object.
 */
function tidy_get_html (tidy $tidy): ?tidyNode {}

/**
 * Returns a tidyNode object starting from the tag of the tidy parse tree
 * @link http://www.php.net/manual/en/tidy.head.php
 * @param tidy $tidy 
 * @return tidyNode|null Returns the tidyNode object.
 */
function tidy_get_head (tidy $tidy): ?tidyNode {}

/**
 * Returns a tidyNode object starting from the tag of the tidy parse tree
 * @link http://www.php.net/manual/en/tidy.body.php
 * @param tidy $tidy 
 * @return tidyNode|null Returns a tidyNode object starting from the 
 * &lt;body&gt; tag of the tidy parse tree.
 */
function tidy_get_body (tidy $tidy): ?tidyNode {}

define ('TIDY_NODETYPE_ROOT', 0);
define ('TIDY_NODETYPE_DOCTYPE', 1);
define ('TIDY_NODETYPE_COMMENT', 2);
define ('TIDY_NODETYPE_PROCINS', 3);
define ('TIDY_NODETYPE_TEXT', 4);
define ('TIDY_NODETYPE_START', 5);
define ('TIDY_NODETYPE_END', 6);
define ('TIDY_NODETYPE_STARTEND', 7);
define ('TIDY_NODETYPE_CDATA', 8);
define ('TIDY_NODETYPE_SECTION', 9);
define ('TIDY_NODETYPE_ASP', 10);
define ('TIDY_NODETYPE_JSTE', 11);
define ('TIDY_NODETYPE_PHP', 12);
define ('TIDY_NODETYPE_XMLDECL', 13);
define ('TIDY_TAG_UNKNOWN', 0);
define ('TIDY_TAG_A', 1);
define ('TIDY_TAG_ABBR', 2);
define ('TIDY_TAG_ACRONYM', 3);
define ('TIDY_TAG_ADDRESS', 4);
define ('TIDY_TAG_ALIGN', 5);
define ('TIDY_TAG_APPLET', 6);
define ('TIDY_TAG_AREA', 7);
define ('TIDY_TAG_B', 8);
define ('TIDY_TAG_BASE', 9);
define ('TIDY_TAG_BASEFONT', 10);
define ('TIDY_TAG_BDO', 11);
define ('TIDY_TAG_BGSOUND', 12);
define ('TIDY_TAG_BIG', 13);
define ('TIDY_TAG_BLINK', 14);
define ('TIDY_TAG_BLOCKQUOTE', 15);
define ('TIDY_TAG_BODY', 16);
define ('TIDY_TAG_BR', 17);
define ('TIDY_TAG_BUTTON', 18);
define ('TIDY_TAG_CAPTION', 19);
define ('TIDY_TAG_CENTER', 20);
define ('TIDY_TAG_CITE', 21);
define ('TIDY_TAG_CODE', 22);
define ('TIDY_TAG_COL', 23);
define ('TIDY_TAG_COLGROUP', 24);
define ('TIDY_TAG_COMMENT', 25);
define ('TIDY_TAG_DD', 26);
define ('TIDY_TAG_DEL', 27);
define ('TIDY_TAG_DFN', 28);
define ('TIDY_TAG_DIR', 29);
define ('TIDY_TAG_DIV', 30);
define ('TIDY_TAG_DL', 31);
define ('TIDY_TAG_DT', 32);
define ('TIDY_TAG_EM', 33);
define ('TIDY_TAG_EMBED', 34);
define ('TIDY_TAG_FIELDSET', 35);
define ('TIDY_TAG_FONT', 36);
define ('TIDY_TAG_FORM', 37);
define ('TIDY_TAG_FRAME', 38);
define ('TIDY_TAG_FRAMESET', 39);
define ('TIDY_TAG_H1', 40);
define ('TIDY_TAG_H2', 41);
define ('TIDY_TAG_H3', 42);
define ('TIDY_TAG_H4', 43);
define ('TIDY_TAG_H5', 44);
define ('TIDY_TAG_H6', 45);
define ('TIDY_TAG_HEAD', 46);
define ('TIDY_TAG_HR', 47);
define ('TIDY_TAG_HTML', 48);
define ('TIDY_TAG_I', 49);
define ('TIDY_TAG_IFRAME', 50);
define ('TIDY_TAG_ILAYER', 51);
define ('TIDY_TAG_IMG', 52);
define ('TIDY_TAG_INPUT', 53);
define ('TIDY_TAG_INS', 54);
define ('TIDY_TAG_ISINDEX', 55);
define ('TIDY_TAG_KBD', 56);
define ('TIDY_TAG_KEYGEN', 57);
define ('TIDY_TAG_LABEL', 58);
define ('TIDY_TAG_LAYER', 59);
define ('TIDY_TAG_LEGEND', 60);
define ('TIDY_TAG_LI', 61);
define ('TIDY_TAG_LINK', 62);
define ('TIDY_TAG_LISTING', 63);
define ('TIDY_TAG_MAP', 64);
define ('TIDY_TAG_MARQUEE', 66);
define ('TIDY_TAG_MENU', 67);
define ('TIDY_TAG_META', 68);
define ('TIDY_TAG_MULTICOL', 69);
define ('TIDY_TAG_NOBR', 70);
define ('TIDY_TAG_NOEMBED', 71);
define ('TIDY_TAG_NOFRAMES', 72);
define ('TIDY_TAG_NOLAYER', 73);
define ('TIDY_TAG_NOSAVE', 74);
define ('TIDY_TAG_NOSCRIPT', 75);
define ('TIDY_TAG_OBJECT', 76);
define ('TIDY_TAG_OL', 77);
define ('TIDY_TAG_OPTGROUP', 78);
define ('TIDY_TAG_OPTION', 79);
define ('TIDY_TAG_P', 80);
define ('TIDY_TAG_PARAM', 81);
define ('TIDY_TAG_PLAINTEXT', 83);
define ('TIDY_TAG_PRE', 84);
define ('TIDY_TAG_Q', 85);
define ('TIDY_TAG_RB', 86);
define ('TIDY_TAG_RBC', 87);
define ('TIDY_TAG_RP', 88);
define ('TIDY_TAG_RT', 89);
define ('TIDY_TAG_RTC', 90);
define ('TIDY_TAG_RUBY', 91);
define ('TIDY_TAG_S', 92);
define ('TIDY_TAG_SAMP', 93);
define ('TIDY_TAG_SCRIPT', 94);
define ('TIDY_TAG_SELECT', 95);
define ('TIDY_TAG_SERVER', 96);
define ('TIDY_TAG_SERVLET', 97);
define ('TIDY_TAG_SMALL', 98);
define ('TIDY_TAG_SPACER', 99);
define ('TIDY_TAG_SPAN', 100);
define ('TIDY_TAG_STRIKE', 101);
define ('TIDY_TAG_STRONG', 102);
define ('TIDY_TAG_STYLE', 103);
define ('TIDY_TAG_SUB', 104);
define ('TIDY_TAG_SUP', 105);
define ('TIDY_TAG_TABLE', 107);
define ('TIDY_TAG_TBODY', 108);
define ('TIDY_TAG_TD', 109);
define ('TIDY_TAG_TEXTAREA', 110);
define ('TIDY_TAG_TFOOT', 111);
define ('TIDY_TAG_TH', 112);
define ('TIDY_TAG_THEAD', 113);
define ('TIDY_TAG_TITLE', 114);
define ('TIDY_TAG_TR', 115);
define ('TIDY_TAG_TT', 116);
define ('TIDY_TAG_U', 117);
define ('TIDY_TAG_UL', 118);
define ('TIDY_TAG_VAR', 119);
define ('TIDY_TAG_WBR', 120);
define ('TIDY_TAG_XMP', 121);
define ('TIDY_TAG_ARTICLE', 123);
define ('TIDY_TAG_ASIDE', 124);
define ('TIDY_TAG_AUDIO', 125);
define ('TIDY_TAG_BDI', 126);
define ('TIDY_TAG_CANVAS', 127);
define ('TIDY_TAG_COMMAND', 128);
define ('TIDY_TAG_DATALIST', 130);
define ('TIDY_TAG_DETAILS', 131);
define ('TIDY_TAG_DIALOG', 132);
define ('TIDY_TAG_FIGCAPTION', 133);
define ('TIDY_TAG_FIGURE', 134);
define ('TIDY_TAG_FOOTER', 135);
define ('TIDY_TAG_HEADER', 136);
define ('TIDY_TAG_HGROUP', 137);
define ('TIDY_TAG_MAIN', 138);
define ('TIDY_TAG_MARK', 139);
define ('TIDY_TAG_MENUITEM', 140);
define ('TIDY_TAG_METER', 141);
define ('TIDY_TAG_NAV', 142);
define ('TIDY_TAG_OUTPUT', 143);
define ('TIDY_TAG_PROGRESS', 144);
define ('TIDY_TAG_SECTION', 145);
define ('TIDY_TAG_SOURCE', 146);
define ('TIDY_TAG_SUMMARY', 147);
define ('TIDY_TAG_TEMPLATE', 148);
define ('TIDY_TAG_TIME', 149);
define ('TIDY_TAG_TRACK', 150);
define ('TIDY_TAG_VIDEO', 151);

// End of tidy v.8.2.6
