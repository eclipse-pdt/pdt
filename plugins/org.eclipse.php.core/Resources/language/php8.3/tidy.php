<?php

// Start of tidy v.8.3.0

class tidy  {

	public ?string $errorBuffer;

	public ?string $value;

	/**
	 * {@inheritdoc}
	 * @param string|null $filename [optional]
	 * @param array|string|null $config [optional]
	 * @param string|null $encoding [optional]
	 * @param bool $useIncludePath [optional]
	 */
	public function __construct (?string $filename = NULL, array|string|null $config = NULL, ?string $encoding = NULL, bool $useIncludePath = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $option
	 */
	public function getOpt (string $option) {}

	/**
	 * {@inheritdoc}
	 */
	public function cleanRepair () {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param array|string|null $config [optional]
	 * @param string|null $encoding [optional]
	 * @param bool $useIncludePath [optional]
	 */
	public function parseFile (string $filename, array|string|null $config = NULL, ?string $encoding = NULL, bool $useIncludePath = false) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param array|string|null $config [optional]
	 * @param string|null $encoding [optional]
	 */
	public function parseString (string $string, array|string|null $config = NULL, ?string $encoding = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param array|string|null $config [optional]
	 * @param string|null $encoding [optional]
	 */
	public static function repairString (string $string, array|string|null $config = NULL, ?string $encoding = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param array|string|null $config [optional]
	 * @param string|null $encoding [optional]
	 * @param bool $useIncludePath [optional]
	 */
	public static function repairFile (string $filename, array|string|null $config = NULL, ?string $encoding = NULL, bool $useIncludePath = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function diagnose () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRelease () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConfig () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStatus () {}

	/**
	 * {@inheritdoc}
	 */
	public function getHtmlVer () {}

	/**
	 * {@inheritdoc}
	 * @param string $option
	 */
	public function getOptDoc (string $option) {}

	/**
	 * {@inheritdoc}
	 */
	public function isXhtml () {}

	/**
	 * {@inheritdoc}
	 */
	public function isXml () {}

	/**
	 * {@inheritdoc}
	 */
	public function root () {}

	/**
	 * {@inheritdoc}
	 */
	public function head () {}

	/**
	 * {@inheritdoc}
	 */
	public function html () {}

	/**
	 * {@inheritdoc}
	 */
	public function body () {}

}

final class tidyNode  {

	public readonly string $value;

	public readonly string $name;

	public readonly int $type;

	public readonly int $line;

	public readonly int $column;

	public readonly bool $proprietary;

	public readonly ?int $id;

	public readonly ?array $attribute;

	public readonly ?array $child;

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasChildren (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function hasSiblings (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isComment (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isHtml (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isText (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isJste (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isAsp (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isPhp (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getParent (): ?tidyNode {}

}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array|string|null $config [optional]
 * @param string|null $encoding [optional]
 */
function tidy_parse_string (string $string, array|string|null $config = NULL, ?string $encoding = NULL): tidy|false {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_error_buffer (tidy $tidy): string|false {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_output (tidy $tidy): string {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param array|string|null $config [optional]
 * @param string|null $encoding [optional]
 * @param bool $useIncludePath [optional]
 */
function tidy_parse_file (string $filename, array|string|null $config = NULL, ?string $encoding = NULL, bool $useIncludePath = false): tidy|false {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_clean_repair (tidy $tidy): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array|string|null $config [optional]
 * @param string|null $encoding [optional]
 */
function tidy_repair_string (string $string, array|string|null $config = NULL, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param array|string|null $config [optional]
 * @param string|null $encoding [optional]
 * @param bool $useIncludePath [optional]
 */
function tidy_repair_file (string $filename, array|string|null $config = NULL, ?string $encoding = NULL, bool $useIncludePath = false): string|false {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_diagnose (tidy $tidy): bool {}

/**
 * {@inheritdoc}
 */
function tidy_get_release (): string {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 * @param string $option
 */
function tidy_get_opt_doc (tidy $tidy, string $option): string|false {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_config (tidy $tidy): array {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_status (tidy $tidy): int {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_html_ver (tidy $tidy): int {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_is_xhtml (tidy $tidy): bool {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_is_xml (tidy $tidy): bool {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_error_count (tidy $tidy): int {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_warning_count (tidy $tidy): int {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_access_count (tidy $tidy): int {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_config_count (tidy $tidy): int {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 * @param string $option
 */
function tidy_getopt (tidy $tidy, string $option): string|int|bool {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_root (tidy $tidy): ?tidyNode {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_html (tidy $tidy): ?tidyNode {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
 */
function tidy_get_head (tidy $tidy): ?tidyNode {}

/**
 * {@inheritdoc}
 * @param tidy $tidy
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

// End of tidy v.8.3.0
