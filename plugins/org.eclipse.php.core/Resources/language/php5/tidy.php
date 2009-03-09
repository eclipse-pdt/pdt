<?php

// Start of tidy v.2.0

class tidy  {

	public function getOpt () {}

	public function cleanRepair () {}

	public function parseFile () {}

	public function parseString () {}

	public function repairString () {}

	public function repairFile () {}

	public function diagnose () {}

	public function getRelease () {}

	public function getConfig () {}

	public function getStatus () {}

	public function getHtmlVer () {}

	public function getOptDoc () {}

	public function isXhtml () {}

	public function isXml () {}

	public function root () {}

	public function head () {}

	public function html () {}

	public function body () {}

	/**
	 * Constructs a new tidy object
	 * @link http://php.net/manual/en/function.tidy-construct.php
	 */
	public function __construct () {}

}

/**
 * @link http://php.net/manual/en/ref.tidy.php
 */
final class tidyNode  {

	/**
	 * Returns true if this node has children
	 * @link http://php.net/manual/en/function.tidynode-haschildren.php
	 */
	public function hasChildren () {}

	/**
	 * Returns true if this node has siblings
	 * @link http://php.net/manual/en/function.tidynode-hassiblings.php
	 */
	public function hasSiblings () {}

	/**
	 * Returns true if this node represents a comment
	 * @link http://php.net/manual/en/function.tidynode-iscomment.php
	 */
	public function isComment () {}

	/**
	 * Returns true if this node is part of a HTML document
	 * @link http://php.net/manual/en/function.tidynode-ishtml.php
	 */
	public function isHtml () {}

	/**
	 * Returns true if this node represents text (no markup)
	 * @link http://php.net/manual/en/function.tidynode-istext.php
	 */
	public function isText () {}

	/**
	 * Returns true if this node is JSTE
	 * @link http://php.net/manual/en/function.tidynode-isjste.php
	 */
	public function isJste () {}

	/**
	 * Returns true if this node is ASP
	 * @link http://php.net/manual/en/function.tidynode-isasp.php
	 */
	public function isAsp () {}

	/**
	 * Returns true if this node is PHP
	 * @link http://php.net/manual/en/function.tidynode-isphp.php
	 */
	public function isPhp () {}

	/**
	 * returns the parent node of the current node
	 * @link http://php.net/manual/en/function.tidynode-getparent.php
	 * @return tidyNode a tidyNode if the node has a parent, or &null;
	 * otherwise.
	 */
	public function getParent () {}

}

/**
 * Returns the value of the specified configuration option for the tidy document
 * @link http://php.net/manual/en/function.tidy-getopt.php
 * @param option
 */
function tidy_getopt ($option) {}

/**
 * Parse a document stored in a string
 * @link http://php.net/manual/en/function.tidy-parse-string.php
 * @param input
 * @param config_options[optional]
 * @param encoding[optional]
 */
function tidy_parse_string ($input, $config_options, $encoding) {}

/**
 * Parse markup in file or URI
 * @link http://php.net/manual/en/function.tidy-parse-file.php
 * @param file
 * @param config_options[optional]
 * @param encoding[optional]
 * @param use_include_path[optional]
 */
function tidy_parse_file ($file, $config_options, $encoding, $use_include_path) {}

/**
 * Return a string representing the parsed tidy markup
 * @link http://php.net/manual/en/function.tidy-get-output.php
 * @param object tidy <p>
 * The tidy object.
 * </p>
 * @return string the parsed tidy markup.
 */
function tidy_get_output (tidy $object) {}

/**
 * Return warnings and errors which occurred parsing the specified document
 * @link http://php.net/manual/en/function.tidy-get-error-buffer.php
 * @param detailed[optional]
 */
function tidy_get_error_buffer ($detailed) {}

/**
 * Execute configured cleanup and repair operations on parsed markup
 * @link http://php.net/manual/en/function.tidy-clean-repair.php
 */
function tidy_clean_repair () {}

/**
 * Repair a string using an optionally provided configuration file
 * @link http://php.net/manual/en/function.tidy-repair-string.php
 * @param data
 * @param config_file[optional]
 * @param encoding[optional]
 */
function tidy_repair_string ($data, $config_file, $encoding) {}

/**
 * Repair a file and return it as a string
 * @link http://php.net/manual/en/function.tidy-repair-file.php
 * @param filename
 * @param config_file[optional]
 * @param encoding[optional]
 * @param use_include_path[optional]
 */
function tidy_repair_file ($filename, $config_file, $encoding, $use_include_path) {}

/**
 * Run configured diagnostics on parsed and repaired markup
 * @link http://php.net/manual/en/function.tidy-diagnose.php
 */
function tidy_diagnose () {}

/**
 * Get release date (version) for Tidy library
 * @link http://php.net/manual/en/function.tidy-get-release.php
 * @return string a string with the release date of the Tidy library.
 */
function tidy_get_release () {}

/**
 * Get current Tidy configuration
 * @link http://php.net/manual/en/function.tidy-get-config.php
 */
function tidy_get_config () {}

/**
 * Get status of specified document
 * @link http://php.net/manual/en/function.tidy-get-status.php
 */
function tidy_get_status () {}

/**
 * Get the Detected HTML version for the specified document
 * @link http://php.net/manual/en/function.tidy-get-html-ver.php
 */
function tidy_get_html_ver () {}

/**
 * Indicates if the document is a XHTML document
 * @link http://php.net/manual/en/function.tidy-is-xhtml.php
 */
function tidy_is_xhtml () {}

/**
 * Indicates if the document is a generic (non HTML/XHTML) XML document
 * @link http://php.net/manual/en/function.tidy-is-xml.php
 */
function tidy_is_xml () {}

/**
 * Returns the Number of Tidy errors encountered for specified document
 * @link http://php.net/manual/en/function.tidy-error-count.php
 */
function tidy_error_count () {}

/**
 * Returns the Number of Tidy warnings encountered for specified document
 * @link http://php.net/manual/en/function.tidy-warning-count.php
 * @param object tidy <p>
 * The Tidy object.
 * </p>
 * @return int the number of warnings.
 */
function tidy_warning_count (tidy $object) {}

/**
 * Returns the Number of Tidy accessibility warnings encountered for specified document
 * @link http://php.net/manual/en/function.tidy-access-count.php
 * @param object tidy <p>
 * The Tidy object.
 * </p>
 * @return int the number of warnings.
 */
function tidy_access_count (tidy $object) {}

/**
 * Returns the Number of Tidy configuration errors encountered for specified document
 * @link http://php.net/manual/en/function.tidy-config-count.php
 */
function tidy_config_count () {}

/**
 * Returns the documentation for the given option name
 * @link http://php.net/manual/en/function.tidy-get-opt-doc.php
 * @param object tidy <p>
 * A tidy object
 * </p>
 * @param optname string <p>
 * The option name
 * </p>
 * @return string a string if the option exists and has documentation available, or
 * false otherwise.
 */
function tidy_get_opt_doc (tidy $object, $optname) {}

/**
 * Returns a tidyNode object representing the root of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-root.php
 */
function tidy_get_root () {}

/**
 * Returns a tidyNode Object starting from the &lt;head&gt; tag of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-head.php
 */
function tidy_get_head () {}

/**
 * Returns a tidyNode Object starting from the &lt;html&gt; tag of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-html.php
 */
function tidy_get_html () {}

/**
 * Returns a tidyNode Object starting from the &lt;body&gt; tag of the tidy parse tree
 * @link http://php.net/manual/en/function.tidy-get-body.php
 * @param tidy
 */
function tidy_get_body ($tidy) {}

/**
 * ob_start callback function to repair the buffer
 * @link http://php.net/manual/en/function.ob-tidyhandler.php
 * @param input string <p>
 * The buffer.
 * </p>
 * @param mode int[optional] <p>
 * The buffer mode.
 * </p>
 * @return string the modified buffer.
 */
function ob_tidyhandler ($input, $mode = null) {}

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
define ('TIDY_TAG_MARQUEE', 65);
define ('TIDY_TAG_MENU', 66);
define ('TIDY_TAG_META', 67);
define ('TIDY_TAG_MULTICOL', 68);
define ('TIDY_TAG_NOBR', 69);
define ('TIDY_TAG_NOEMBED', 70);
define ('TIDY_TAG_NOFRAMES', 71);
define ('TIDY_TAG_NOLAYER', 72);
define ('TIDY_TAG_NOSAVE', 73);
define ('TIDY_TAG_NOSCRIPT', 74);
define ('TIDY_TAG_OBJECT', 75);
define ('TIDY_TAG_OL', 76);
define ('TIDY_TAG_OPTGROUP', 77);
define ('TIDY_TAG_OPTION', 78);
define ('TIDY_TAG_P', 79);
define ('TIDY_TAG_PARAM', 80);
define ('TIDY_TAG_PLAINTEXT', 81);
define ('TIDY_TAG_PRE', 82);
define ('TIDY_TAG_Q', 83);
define ('TIDY_TAG_RB', 84);
define ('TIDY_TAG_RBC', 85);
define ('TIDY_TAG_RP', 86);
define ('TIDY_TAG_RT', 87);
define ('TIDY_TAG_RTC', 88);
define ('TIDY_TAG_RUBY', 89);
define ('TIDY_TAG_S', 90);
define ('TIDY_TAG_SAMP', 91);
define ('TIDY_TAG_SCRIPT', 92);
define ('TIDY_TAG_SELECT', 93);
define ('TIDY_TAG_SERVER', 94);
define ('TIDY_TAG_SERVLET', 95);
define ('TIDY_TAG_SMALL', 96);
define ('TIDY_TAG_SPACER', 97);
define ('TIDY_TAG_SPAN', 98);
define ('TIDY_TAG_STRIKE', 99);
define ('TIDY_TAG_STRONG', 100);
define ('TIDY_TAG_STYLE', 101);
define ('TIDY_TAG_SUB', 102);
define ('TIDY_TAG_SUP', 103);
define ('TIDY_TAG_TABLE', 104);
define ('TIDY_TAG_TBODY', 105);
define ('TIDY_TAG_TD', 106);
define ('TIDY_TAG_TEXTAREA', 107);
define ('TIDY_TAG_TFOOT', 108);
define ('TIDY_TAG_TH', 109);
define ('TIDY_TAG_THEAD', 110);
define ('TIDY_TAG_TITLE', 111);
define ('TIDY_TAG_TR', 112);
define ('TIDY_TAG_TT', 113);
define ('TIDY_TAG_U', 114);
define ('TIDY_TAG_UL', 115);
define ('TIDY_TAG_VAR', 116);
define ('TIDY_TAG_WBR', 117);
define ('TIDY_TAG_XMP', 118);
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

// End of tidy v.2.0
?>
