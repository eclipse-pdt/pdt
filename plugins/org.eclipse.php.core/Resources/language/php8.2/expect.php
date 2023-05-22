<?php

// Start of expect v.0.4.0

/**
 * Execute command via Bourne shell, and open the PTY stream to
 * the process
 * @link http://www.php.net/manual/en/function.expect-popen.php
 * @param string $command 
 * @return resource Returns an open PTY stream to the processes stdio,
 * stdout, and stderr.
 * <p>On failure this function returns false.</p>
 */
function expect_popen (string $command) {}

/**
 * Waits until the output from a process matches one
 * of the patterns, a specified time period has passed, or an EOF is seen
 * @link http://www.php.net/manual/en/function.expect-expectl.php
 * @param resource $expect 
 * @param array $cases 
 * @param array $match [optional] 
 * @return int Returns value associated with the pattern that was matched.
 * <p>On failure this function returns:
 * EXP_EOF,
 * EXP_TIMEOUT
 * or
 * EXP_FULLBUFFER</p>
 */
function expect_expectl ($expect, array $cases, array &$match = null): int {}


/**
 * Indicates that the pattern is a glob-style string pattern.
 * @link http://www.php.net/manual/en/expect.constants.php
 * @var int
 */
define ('EXP_GLOB', 1);

/**
 * Indicates that the pattern is an exact string.
 * @link http://www.php.net/manual/en/expect.constants.php
 * @var int
 */
define ('EXP_EXACT', 2);

/**
 * Indicates that the pattern is a regexp-style string pattern.
 * @link http://www.php.net/manual/en/expect.constants.php
 * @var int
 */
define ('EXP_REGEXP', 3);

/**
 * Value, returned by expect_expectl, when EOF is
 * reached.
 * @link http://www.php.net/manual/en/expect.constants.php
 * @var int
 */
define ('EXP_EOF', -11);

/**
 * Value, returned by expect_expectl upon timeout of
 * seconds, specified in value of expect.timeout
 * @link http://www.php.net/manual/en/ini.expect.timeout.php
 * @var int
 */
define ('EXP_TIMEOUT', -2);

/**
 * Value, returned by expect_expectl if no pattern have
 * been matched.
 * @link http://www.php.net/manual/en/expect.constants.php
 * @var int
 */
define ('EXP_FULLBUFFER', -5);

// End of expect v.0.4.0
