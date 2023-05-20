<?php

// Start of pcov v.1.0.11

namespace pcov {

function start () {}

function stop () {}

/**
 * @param int $type [optional]
 * @param array[] $filter [optional]
 */
function collect (int $type = null, array $filter = null) {}

/**
 * @param bool $files [optional]
 */
function clear (bool $files = null) {}

function waiting () {}

function memory () {}


}


namespace {

define ('pcov\all', 0);
define ('pcov\inclusive', 1);
define ('pcov\exclusive', 2);
define ('pcov\version', "1.0.11");


}

// End of pcov v.1.0.11
