<?php

// Start of pcre v.7.3.0

/**
 * Perform a regular expression match
 * @link http://www.php.net/manual/en/function.preg-match.php
 * @param string $pattern The pattern to search for, as a string.
 * @param string $subject The input string.
 * @param array $matches [optional] If matches is provided, then it is filled with
 * the results of search. $matches[0] will contain the
 * text that matched the full pattern, $matches[1]
 * will have the text that matched the first captured parenthesized
 * subpattern, and so on.
 * @param int $flags [optional] <p>
 * flags can be a combination of the following flags:
 * <p>
 * PREG_OFFSET_CAPTURE
 * <br>
 * <p>
 * If this flag is passed, for every occurring match the appendant string
 * offset (in bytes) will also be returned. Note that this changes the value of
 * matches into an array where every element is an
 * array consisting of the matched string at offset 0
 * and its string offset into subject at offset
 * 1.
 * <pre>
 * <code>&lt;?php
 * preg_match('&#47;(foo)(bar)(baz)&#47;', 'foobarbaz', $matches, PREG_OFFSET_CAPTURE);
 * print_r($matches);
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * Array
 * (
 * [0] => Array
 * (
 * [0] => foobarbaz
 * [1] => 0
 * )
 * [1] => Array
 * (
 * [0] => foo
 * [1] => 0
 * )
 * [2] => Array
 * (
 * [0] => bar
 * [1] => 3
 * )
 * [3] => Array
 * (
 * [0] => baz
 * [1] => 6
 * )
 * )
 * </pre>
 * </p>
 * PREG_UNMATCHED_AS_NULL
 * <br>
 * <p>
 * If this flag is passed, unmatched subpatterns are reported as null;
 * otherwise they are reported as an empty string.
 * <pre>
 * <code>&lt;?php
 * preg_match('&#47;(a)(b)&#42;(c)&#47;', 'ac', $matches);
 * var_dump($matches);
 * preg_match('&#47;(a)(b)&#42;(c)&#47;', 'ac', $matches, PREG_UNMATCHED_AS_NULL);
 * var_dump($matches);
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * array(4) {
 * [0]=>
 * string(2) "ac"
 * [1]=>
 * string(1) "a"
 * [2]=>
 * string(0) ""
 * [3]=>
 * string(1) "c"
 * }
 * array(4) {
 * [0]=>
 * string(2) "ac"
 * [1]=>
 * string(1) "a"
 * [2]=>
 * NULL
 * [3]=>
 * string(1) "c"
 * }
 * </pre>
 * </p>
 * </p>
 * </p>
 * @param int $offset [optional] <p>
 * Normally, the search starts from the beginning of the subject string.
 * The optional parameter offset can be used to
 * specify the alternate place from which to start the search (in bytes).
 * </p>
 * <p>
 * Using offset is not equivalent to passing
 * substr($subject, $offset) to
 * preg_match in place of the subject string,
 * because pattern can contain assertions such as
 * ^, $ or
 * (?&lt;=x). Compare:
 * <pre>
 * <code>&lt;?php
 * $subject = &quot;abcdef&quot;;
 * $pattern = '&#47;^def&#47;';
 * preg_match($pattern, $subject, $matches, PREG_OFFSET_CAPTURE, 3);
 * print_r($matches);
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * Array
 * (
 * )
 * </pre>
 * <p>
 * while this example
 * </p>
 * <pre>
 * <code>&lt;?php
 * $subject = &quot;abcdef&quot;;
 * $pattern = '&#47;^def&#47;';
 * preg_match($pattern, substr($subject,3), $matches, PREG_OFFSET_CAPTURE);
 * print_r($matches);
 * ?&gt;</code>
 * </pre>
 * <p>
 * will produce
 * </p>
 * <pre>
 * Array
 * (
 * [0] => Array
 * (
 * [0] => def
 * [1] => 0
 * )
 * )
 * </pre>
 * <p>
 * Alternatively, to avoid using substr, use the
 * \G assertion rather than the ^ anchor, or
 * the A modifier instead, both of which work with
 * the offset parameter.
 * </p>
 * </p>
 * @return int preg_match returns 1 if the pattern
 * matches given subject, 0 if it does not, or false
 * if an error occurred.
 */
function preg_match (string $pattern, string $subject, array &$matches = null, int $flags = null, int $offset = null) {}

/**
 * Perform a global regular expression match
 * @link http://www.php.net/manual/en/function.preg-match-all.php
 * @param string $pattern The pattern to search for, as a string.
 * @param string $subject The input string.
 * @param array $matches [optional] Array of all matches in multi-dimensional array ordered according to
 * flags.
 * @param int $flags [optional] <p>
 * Can be a combination of the following flags (note that it doesn't make
 * sense to use PREG_PATTERN_ORDER together with
 * PREG_SET_ORDER):
 * <p>
 * PREG_PATTERN_ORDER
 * <br>
 * <p>
 * Orders results so that $matches[0] is an array of full
 * pattern matches, $matches[1] is an array of strings matched by
 * the first parenthesized subpattern, and so on.
 * </p>
 * <p>
 * <pre>
 * <code>&lt;?php
 * preg_match_all(&quot;|&lt;[^&gt;]+&gt;(.&#42;)&lt;&#47;[^&gt;]+&gt;|U&quot;,
 * &quot;&lt;b&gt;example: &lt;&#47;b&gt;&lt;div align=left&gt;this is a test&lt;&#47;div&gt;&quot;,
 * $out, PREG_PATTERN_ORDER);
 * echo $out[0][0] . &quot;, &quot; . $out[0][1] . &quot;\n&quot;;
 * echo $out[1][0] . &quot;, &quot; . $out[1][1] . &quot;\n&quot;;
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * example: , this is a test
 * example: , this is a test
 * </pre>
 * <p>
 * So, $out[0] contains array of strings that matched full pattern,
 * and $out[1] contains array of strings enclosed by tags.
 * </p>
 * </p>
 * <p>
 * If the pattern contains named subpatterns, $matches
 * additionally contains entries for keys with the subpattern name.
 * </p>
 * <p>
 * If the pattern contains duplicate named subpatterns, only the rightmost
 * subpattern is stored in $matches[NAME].
 * <pre>
 * <code>&lt;?php
 * preg_match_all(
 * '&#47;(?J)(?&lt;match&gt;foo)|(?&lt;match&gt;bar)&#47;',
 * 'foo bar',
 * $matches,
 * PREG_PATTERN_ORDER
 * );
 * print_r($matches['match']);
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * Array
 * (
 * [0] => 
 * [1] => bar
 * )
 * </pre>
 * </p>
 * PREG_SET_ORDER
 * <br>
 * <p>
 * Orders results so that $matches[0] is an array of first set
 * of matches, $matches[1] is an array of second set of matches,
 * and so on.
 * <pre>
 * <code>&lt;?php
 * preg_match_all(&quot;|&lt;[^&gt;]+&gt;(.&#42;)&lt;&#47;[^&gt;]+&gt;|U&quot;,
 * &quot;&lt;b&gt;example: &lt;&#47;b&gt;&lt;div align=\&quot;left\&quot;&gt;this is a test&lt;&#47;div&gt;&quot;,
 * $out, PREG_SET_ORDER);
 * echo $out[0][0] . &quot;, &quot; . $out[0][1] . &quot;\n&quot;;
 * echo $out[1][0] . &quot;, &quot; . $out[1][1] . &quot;\n&quot;;
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * example: , example:
 * this is a test, this is a test
 * </pre>
 * </p>
 * PREG_OFFSET_CAPTURE
 * <br>
 * <p>
 * If this flag is passed, for every occurring match the appendant string
 * offset will also be returned. Note that this changes the value of
 * matches into an array of arrays where every element is an
 * array consisting of the matched string at offset 0
 * and its string offset into subject at offset
 * 1.
 * <pre>
 * <code>&lt;?php
 * preg_match_all('&#47;(foo)(bar)(baz)&#47;', 'foobarbaz', $matches, PREG_OFFSET_CAPTURE);
 * print_r($matches);
 * ?&gt;</code>
 * </pre>
 * <p>The above example will output:</p>
 * <pre>
 * Array
 * (
 * [0] => Array
 * (
 * [0] => Array
 * (
 * [0] => foobarbaz
 * [1] => 0
 * )
 * )
 * [1] => Array
 * (
 * [0] => Array
 * (
 * [0] => foo
 * [1] => 0
 * )
 * )
 * [2] => Array
 * (
 * [0] => Array
 * (
 * [0] => bar
 * [1] => 3
 * )
 * )
 * [3] => Array
 * (
 * [0] => Array
 * (
 * [0] => baz
 * [1] => 6
 * )
 * )
 * )
 * </pre>
 * </p>
 * PREG_UNMATCHED_AS_NULL
 * <br>
 * <p>
 * If this flag is passed, unmatched subpatterns are reported as null;
 * otherwise they are reported as an empty string.
 * </p>
 * </p>
 * </p>
 * <p>
 * If no order flag is given, PREG_PATTERN_ORDER is
 * assumed.
 * </p>
 * @param int $offset [optional] <p>
 * Normally, the search starts from the beginning of the subject string.
 * The optional parameter offset can be used to
 * specify the alternate place from which to start the search (in bytes).
 * </p>
 * <p>
 * Using offset is not equivalent to passing
 * substr($subject, $offset) to
 * preg_match_all in place of the subject string,
 * because pattern can contain assertions such as
 * ^, $ or
 * (?&lt;=x). See preg_match
 * for examples.
 * </p>
 * @return int the number of full pattern matches (which might be zero),
 * or false if an error occurred.
 */
function preg_match_all (string $pattern, string $subject, array &$matches = null, int $flags = null, int $offset = null) {}

/**
 * Perform a regular expression search and replace
 * @link http://www.php.net/manual/en/function.preg-replace.php
 * @param mixed $pattern <p>
 * The pattern to search for. It can be either a string or an array with
 * strings.
 * </p>
 * <p>
 * Several PCRE modifiers
 * are also available.
 * </p>
 * @param mixed $replacement <p>
 * The string or an array with strings to replace. If this parameter is a
 * string and the pattern parameter is an array,
 * all patterns will be replaced by that string. If both
 * pattern and replacement
 * parameters are arrays, each pattern will be
 * replaced by the replacement counterpart. If
 * there are fewer elements in the replacement
 * array than in the pattern array, any extra
 * patterns will be replaced by an empty string.
 * </p>
 * <p>
 * replacement may contain references of the form
 * \\n or
 * $n, with the latter form
 * being the preferred one. Every such reference will be replaced by the text
 * captured by the n'th parenthesized pattern.
 * n can be from 0 to 99, and
 * \\0 or $0 refers to the text matched
 * by the whole pattern. Opening parentheses are counted from left to right
 * (starting from 1) to obtain the number of the capturing subpattern.
 * To use backslash in replacement, it must be doubled
 * ("\\\\" PHP string).
 * </p>
 * <p>
 * When working with a replacement pattern where a backreference is 
 * immediately followed by another number (i.e.: placing a literal number
 * immediately after a matched pattern), you cannot use the familiar 
 * \\1 notation for your backreference. 
 * \\11, for example, would confuse
 * preg_replace since it does not know whether you
 * want the \\1 backreference followed by a literal 
 * 1, or the \\11 backreference
 * followed by nothing. In this case the solution is to use 
 * ${1}1. This creates an isolated
 * $1 backreference, leaving the 1
 * as a literal.
 * </p>
 * <p>
 * When using the deprecated e modifier, this function escapes
 * some characters (namely ', ",
 * \ and NULL) in the strings that replace the
 * backreferences. This is done to ensure that no syntax errors arise
 * from backreference usage with either single or double quotes (e.g.
 * 'strlen(\'$1\')+strlen("$2")'). Make sure you are
 * aware of PHP's string
 * syntax to know exactly how the interpreted string will look.
 * </p>
 * @param mixed $subject <p>
 * The string or an array with strings to search and replace.
 * </p>
 * <p>
 * If subject is an array, then the search and
 * replace is performed on every entry of subject,
 * and the return value is an array as well.
 * </p>
 * @param int $limit [optional] The maximum possible replacements for each pattern in each
 * subject string. Defaults to
 * -1 (no limit).
 * @param int $count [optional] If specified, this variable will be filled with the number of
 * replacements done.
 * @return mixed preg_replace returns an array if the
 * subject parameter is an array, or a string
 * otherwise.
 * <p>
 * If matches are found, the new subject will
 * be returned, otherwise subject will be
 * returned unchanged or null if an error occurred.
 * </p>
 */
function preg_replace ($pattern, $replacement, $subject, int $limit = null, int &$count = null) {}

/**
 * Perform a regular expression search and replace using a callback
 * @link http://www.php.net/manual/en/function.preg-replace-callback.php
 * @param mixed $pattern The pattern to search for. It can be either a string or an array with
 * strings.
 * @param callable $callback <p>
 * A callback that will be called and passed an array of matched elements
 * in the subject string. The callback should
 * return the replacement string. This is the callback signature:
 * </p>
 * <p>
 * stringhandler
 * arraymatches
 * </p>
 * <p>
 * You'll often need the callback function
 * for a preg_replace_callback in just one place.
 * In this case you can use an
 * anonymous function to
 * declare the callback within the call to
 * preg_replace_callback. By doing it this way
 * you have all information for the call in one place and do not
 * clutter the function namespace with a callback function's name
 * not used anywhere else.
 * </p>
 * <p>
 * preg_replace_callback and 
 * anonymous function
 * <pre>
 * <code>&lt;?php
 * &#47;&#42; a unix-style command line filter to convert uppercase
 * &#42; letters at the beginning of paragraphs to lowercase &#42;&#47;
 * $fp = fopen(&quot;php:&#47;&#47;stdin&quot;, &quot;r&quot;) or die(&quot;can't read stdin&quot;);
 * while (!feof($fp)) {
 * $line = fgets($fp);
 * $line = preg_replace_callback(
 * '|&lt;p&gt;\s&#42;\w|',
 * function ($matches) {
 * return strtolower($matches[0]);
 * },
 * $line
 * );
 * echo $line;
 * }
 * fclose($fp);
 * ?&gt;</code>
 * </pre>
 * </p>
 * @param mixed $subject The string or an array with strings to search and replace.
 * @param int $limit [optional] The maximum possible replacements for each pattern in each
 * subject string. Defaults to
 * -1 (no limit).
 * @param int $count [optional] If specified, this variable will be filled with the number of
 * replacements done.
 * @return mixed preg_replace_callback returns an array if the
 * subject parameter is an array, or a string
 * otherwise. On errors the return value is null
 * <p>
 * If matches are found, the new subject will be returned, otherwise
 * subject will be returned unchanged. 
 * </p>
 */
function preg_replace_callback ($pattern, callable $callback, $subject, int $limit = null, int &$count = null) {}

/**
 * Perform a regular expression search and replace using callbacks
 * @link http://www.php.net/manual/en/function.preg-replace-callback-array.php
 * @param array $patterns_and_callbacks An associative array mapping patterns (keys) to callbacks (values).
 * @param mixed $subject The string or an array with strings to search and replace.
 * @param int $limit [optional] The maximum possible replacements for each pattern in each
 * subject string. Defaults to
 * -1 (no limit).
 * @param int $count [optional] If specified, this variable will be filled with the number of
 * replacements done.
 * @return mixed preg_replace_callback_array returns an array if the
 * subject parameter is an array, or a string
 * otherwise. On errors the return value is null
 * <p>
 * If matches are found, the new subject will be returned, otherwise
 * subject will be returned unchanged. 
 * </p>
 */
function preg_replace_callback_array (array $patterns_and_callbacks, $subject, int $limit = null, int &$count = null) {}

/**
 * Perform a regular expression search and replace
 * @link http://www.php.net/manual/en/function.preg-filter.php
 * @param mixed $pattern 
 * @param mixed $replacement 
 * @param mixed $subject 
 * @param int $limit [optional] 
 * @param int $count [optional] 
 * @return mixed an array if the subject
 * parameter is an array, or a string otherwise.
 * <p>
 * If no matches are found or an error occurred, an empty array 
 * is returned when subject is an array
 * or null otherwise.
 * </p>
 */
function preg_filter ($pattern, $replacement, $subject, int $limit = null, int &$count = null) {}

/**
 * Split string by a regular expression
 * @link http://www.php.net/manual/en/function.preg-split.php
 * @param string $pattern The pattern to search for, as a string.
 * @param string $subject The input string.
 * @param int $limit [optional] If specified, then only substrings up to limit
 * are returned with the rest of the string being placed in the last
 * substring. A limit of -1 or 0 means "no limit"
 * and, as is standard across PHP, you can use null to skip to the 
 * flags parameter.
 * @param int $flags [optional] <p>
 * flags can be any combination of the following
 * flags (combined with the | bitwise operator):
 * <p>
 * PREG_SPLIT_NO_EMPTY
 * <br>
 * If this flag is set, only non-empty pieces will be returned by
 * preg_split.
 * PREG_SPLIT_DELIM_CAPTURE
 * <br>
 * If this flag is set, parenthesized expression in the delimiter pattern
 * will be captured and returned as well.
 * PREG_SPLIT_OFFSET_CAPTURE
 * <br>
 * <p>
 * If this flag is set, for every occurring match the appendant string
 * offset will also be returned. Note that this changes the return
 * value in an array where every element is an array consisting of the
 * matched string at offset 0 and its string offset
 * into subject at offset 1.
 * </p>
 * </p>
 * </p>
 * @return array an array containing substrings of subject
 * split along boundaries matched by pattern, or false on failure.
 */
function preg_split (string $pattern, string $subject, int $limit = null, int $flags = null) {}

/**
 * Quote regular expression characters
 * @link http://www.php.net/manual/en/function.preg-quote.php
 * @param string $str The input string.
 * @param string $delimiter [optional] If the optional delimiter is specified, it
 * will also be escaped. This is useful for escaping the delimiter
 * that is required by the PCRE functions. The / is the most commonly
 * used delimiter.
 * @return string the quoted (escaped) string.
 */
function preg_quote (string $str, string $delimiter = null) {}

/**
 * Return array entries that match the pattern
 * @link http://www.php.net/manual/en/function.preg-grep.php
 * @param string $pattern The pattern to search for, as a string.
 * @param array $input The input array.
 * @param int $flags [optional] If set to PREG_GREP_INVERT, this function returns
 * the elements of the input array that do not match
 * the given pattern.
 * @return array an array indexed using the keys from the
 * input array.
 */
function preg_grep (string $pattern, array $input, int $flags = null) {}

/**
 * Returns the error code of the last PCRE regex execution
 * @link http://www.php.net/manual/en/function.preg-last-error.php
 * @return int one of the following constants (explained on their own page):
 * <p>
 * PREG_NO_ERROR
 * PREG_INTERNAL_ERROR
 * PREG_BACKTRACK_LIMIT_ERROR (see also pcre.backtrack_limit)
 * PREG_RECURSION_LIMIT_ERROR (see also pcre.recursion_limit)
 * PREG_BAD_UTF8_ERROR
 * PREG_BAD_UTF8_OFFSET_ERROR (since PHP 5.3.0)
 * PREG_JIT_STACKLIMIT_ERROR (since PHP 7.0.0)
 * </p>
 */
function preg_last_error () {}


/**
 * Orders results so that $matches[0] is an array of full pattern
 * matches, $matches[1] is an array of strings matched by the first
 * parenthesized subpattern, and so on. This flag is only used with
 * preg_match_all.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_PATTERN_ORDER', 1);

/**
 * Orders results so that $matches[0] is an array of first set of
 * matches, $matches[1] is an array of second set of matches, and so
 * on. This flag is only used with preg_match_all.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_SET_ORDER', 2);

/**
 * See the description of
 * PREG_SPLIT_OFFSET_CAPTURE.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_OFFSET_CAPTURE', 256);

/**
 * This flag tells preg_match and
 * preg_match_all to include unmatched subpatterns in
 * $matches as null values. Without this flag, unmatched
 * subpatterns are reported as empty strings, as if they were empty matches.
 * Setting this flag allows to distinguish between these two cases.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_UNMATCHED_AS_NULL', 512);

/**
 * This flag tells preg_split to return only non-empty
 * pieces.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_SPLIT_NO_EMPTY', 1);

/**
 * This flag tells preg_split to capture
 * parenthesized expression in the delimiter pattern as well.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_SPLIT_DELIM_CAPTURE', 2);

/**
 * If this flag is set, for every occurring match the appendant string
 * offset will also be returned. Note that this changes the return
 * values in an array where every element is an array consisting of the
 * matched string at offset 0 and its string offset within subject at
 * offset 1. This flag is only used for preg_split.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_SPLIT_OFFSET_CAPTURE', 4);
define ('PREG_GREP_INVERT', 1);

/**
 * Returned by preg_last_error if there were no
 * errors.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_NO_ERROR', 0);

/**
 * Returned by preg_last_error if there was an
 * internal PCRE error.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_INTERNAL_ERROR', 1);

/**
 * Returned by preg_last_error if backtrack limit was exhausted.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_BACKTRACK_LIMIT_ERROR', 2);

/**
 * Returned by preg_last_error if recursion limit was exhausted.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_RECURSION_LIMIT_ERROR', 3);

/**
 * Returned by preg_last_error if the last error was
 * caused by malformed UTF-8 data (only when running a regex in UTF-8 mode).
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_BAD_UTF8_ERROR', 4);

/**
 * Returned by preg_last_error if the offset didn't
 * correspond to the begin of a valid UTF-8 code point (only when running
 * a regex in UTF-8
 * mode).
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_BAD_UTF8_OFFSET_ERROR', 5);

/**
 * Returned by preg_last_error if the last PCRE function
 * failed due to limited JIT stack space.
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PREG_JIT_STACKLIMIT_ERROR', 6);

/**
 * PCRE version and release date (e.g. "7.0 18-Dec-2006").
 * @link http://www.php.net/manual/en/pcre.constants.php
 */
define ('PCRE_VERSION', "10.32 2018-09-10");
define ('PCRE_VERSION_MAJOR', 10);
define ('PCRE_VERSION_MINOR', 32);
define ('PCRE_JIT_SUPPORT', true);

// End of pcre v.7.3.0
