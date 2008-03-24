<?php

// Start of pcre v.

/**
 * Perform a regular expression match
 * @link http://php.net/manual/en/function.preg-match.php
 * @param pattern string
 * @param subject string
 * @param matches array[optional]
 * @param flags int[optional]
 * @param offset int[optional]
 * @return int 
 */
function preg_match ($pattern, $subject, array &$matches = null, $flags = null, $offset = null) {}

/**
 * Perform a global regular expression match
 * @link http://php.net/manual/en/function.preg-match-all.php
 * @param pattern string
 * @param subject string
 * @param matches array
 * @param flags int[optional]
 * @param offset int[optional]
 * @return int the number of full pattern matches (which might be zero),
 */
function preg_match_all ($pattern, $subject, array &$matches, $flags = null, $offset = null) {}

/**
 * Perform a regular expression search and replace
 * @link http://php.net/manual/en/function.preg-replace.php
 * @param pattern mixed
 * @param replacement mixed
 * @param subject mixed
 * @param limit int[optional]
 * @param count int[optional]
 * @return mixed 
 */
function preg_replace ($pattern, $replacement, $subject, $limit = null, &$count = null) {}

/**
 * Perform a regular expression search and replace using a callback
 * @link http://php.net/manual/en/function.preg-replace-callback.php
 * @param pattern mixed
 * @param callback callback
 * @param subject mixed
 * @param limit int[optional]
 * @param count int[optional]
 * @return mixed 
 */
function preg_replace_callback ($pattern, $callback, $subject, $limit = null, &$count = null) {}

/**
 * Split string by a regular expression
 * @link http://php.net/manual/en/function.preg-split.php
 * @param pattern string
 * @param subject string
 * @param limit int[optional]
 * @param flags int[optional]
 * @return array an array containing substrings of subject
 */
function preg_split ($pattern, $subject, $limit = null, $flags = null) {}

/**
 * Quote regular expression characters
 * @link http://php.net/manual/en/function.preg-quote.php
 * @param str string
 * @param delimiter string[optional]
 * @return string the quoted string.
 */
function preg_quote ($str, $delimiter = null) {}

/**
 * Return array entries that match the pattern
 * @link http://php.net/manual/en/function.preg-grep.php
 * @param pattern string
 * @param input array
 * @param flags int[optional]
 * @return array an array indexed using the keys from the
 */
function preg_grep ($pattern, array $input, $flags = null) {}

/**
 * Returns the error code of the last PCRE regex execution
 * @link http://php.net/manual/en/function.preg-last-error.php
 * @return int one of the following constants (
 */
function preg_last_error () {}


/**
 * Orders results so that $matches[0] is an array of full pattern
 * matches, $matches[1] is an array of strings matched by the first
 * parenthesized subpattern, and so on. This flag is only used with
 * preg_match_all.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_PATTERN_ORDER', 1);

/**
 * Orders results so that $matches[0] is an array of first set of
 * matches, $matches[1] is an array of second set of matches, and so
 * on. This flag is only used with preg_match_all.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_SET_ORDER', 2);

/**
 * See the description of
 * PREG_SPLIT_OFFSET_CAPTURE. This flag is
 * available since PHP 4.3.0.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_OFFSET_CAPTURE', 256);

/**
 * This flag tells preg_split to return only non-empty
 * pieces.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_SPLIT_NO_EMPTY', 1);

/**
 * This flag tells preg_split to capture
 * parenthesized expression in the delimiter pattern as well. This flag
 * is available since PHP 4.0.5.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_SPLIT_DELIM_CAPTURE', 2);

/**
 * If this flag is set, for every occurring match the appendant string
 * offset will also be returned. Note that this changes the return
 * values in an array where every element is an array consisting of the
 * matched string at offset 0 and its string offset within subject at
 * offset 1. This flag is available since PHP 4.3.0
 * and is only used for preg_split.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_SPLIT_OFFSET_CAPTURE', 4);
define ('PREG_GREP_INVERT', 1);

/**
 * Returned by preg_last_error if there were no
 * errors. Available since PHP 5.2.0.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_NO_ERROR', 0);

/**
 * Returned by preg_last_error if there was an
 * internal PCRE error. Available since PHP 5.2.0.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_INTERNAL_ERROR', 1);

/**
 * Returned by preg_last_error if backtrack limit was exhausted.
 * Available since PHP 5.2.0.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_BACKTRACK_LIMIT_ERROR', 2);

/**
 * Returned by preg_last_error if recursion limit was exhausted.
 * Available since PHP 5.2.0.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_RECURSION_LIMIT_ERROR', 3);

/**
 * Returned by preg_last_error if the last error was
 * caused by malformed UTF-8 data (only when running a regex in UTF-8 mode). Available
 * since PHP 5.2.0.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PREG_BAD_UTF8_ERROR', 4);

/**
 * PCRE version and release date (e.g. "7.0 18-Dec-2006"). Available since
 * PHP 5.2.4.
 * @link http://php.net/manual/en/pcre.constants.php
 */
define ('PCRE_VERSION', "7.3 2007-08-28");

// End of pcre v.
?>
