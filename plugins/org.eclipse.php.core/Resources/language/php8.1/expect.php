<?php

// Start of expect v.0.4.0

/**
 * Execute command via Bourne shell, and open the PTY stream to
 * the process
 * @link http://www.php.net/manual/en/function.expect-popen.php
 * @param string $command Command to execute.
 * @return resource an open PTY stream to the processes stdio,
 * stdout, and stderr.
 * <p>
 * On failure this function returns false.
 * </p>
 */
function expect_popen (string $command) {}

/**
 * Waits until the output from a process matches one
 * of the patterns, a specified time period has passed, or an EOF is seen
 * @link http://www.php.net/manual/en/function.expect-expectl.php
 * @param resource $expect An Expect stream, previously opened with
 * expect_popen.
 * @param array $cases An array of expect cases. Each expect case is an indexed array,
 * as described in the following table:
 * <table>
 * Expect Case Array
 * <table>
 * <tr valign="top">
 * <td>Index Key</td>
 * <td>Value Type</td>
 * <td>Description</td>
 * <td>Is Mandatory</td>
 * <td>Default Value</td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td>string</td>
 * <td>pattern, that will be matched against the output from the stream</td>
 * <td>yes</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>1</td>
 * <td>mixed</td>
 * <td>value, that will be returned by this function, if the pattern matches</td>
 * <td>yes</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>2</td>
 * <td>integer</td>
 * <td>
 * pattern type, one of:
 * EXP_GLOB,
 * EXP_EXACT
 * or
 * EXP_REGEXP
 * </td>
 * <td>no</td>
 * <td>EXP_GLOB</td>
 * </tr>
 * </table>
 * </table>
 * @param array $match [optional] 
 * @return int value associated with the pattern that was matched.
 * <p>
 * On failure this function returns:
 * EXP_EOF,
 * EXP_TIMEOUT
 * or
 * EXP_FULLBUFFER
 * </p>
 */
function expect_expectl ($expect, array $cases, array &$match = null) {}


/**
 * Indicates that the pattern is a glob-style string pattern.
 * @link http://www.php.net/manual/en/expect.constants.php
 */
define ('EXP_GLOB', 1);

/**
 * Indicates that the pattern is an exact string.
 * @link http://www.php.net/manual/en/expect.constants.php
 */
define ('EXP_EXACT', 2);

/**
 * Indicates that the pattern is a regexp-style string pattern.
 * @link http://www.php.net/manual/en/expect.constants.php
 */
define ('EXP_REGEXP', 3);

/**
 * Value, returned by expect_expectl, when EOF is
 * reached.
 * @link http://www.php.net/manual/en/expect.constants.php
 */
define ('EXP_EOF', -11);

/**
 * Value, returned by expect_expectl upon timeout of
 * seconds, specified in value of expect.timeout
 * @link http://www.php.net/manual/en/expect.constants.php
 */
define ('EXP_TIMEOUT', -2);

/**
 * Value, returned by expect_expectl if no pattern have
 * been matched.
 * @link http://www.php.net/manual/en/expect.constants.php
 */
define ('EXP_FULLBUFFER', -5);

// End of expect v.0.4.0
