<?php

// Start of expect v.0.4.0

/**
 * {@inheritdoc}
 * @param mixed $command
 */
function expect_popen ($command = null) {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 * @param mixed $expect_cases
 * @param mixed $match [optional]
 */
function expect_expectl ($stream = null, $expect_cases = null, &$match = NULL) {}

define ('EXP_GLOB', 1);
define ('EXP_EXACT', 2);
define ('EXP_REGEXP', 3);
define ('EXP_EOF', -11);
define ('EXP_TIMEOUT', -2);
define ('EXP_FULLBUFFER', -5);

// End of expect v.0.4.0
