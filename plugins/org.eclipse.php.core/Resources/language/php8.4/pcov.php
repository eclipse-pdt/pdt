<?php

// Start of pcov v.1.0.11

namespace pcov {

/**
 * {@inheritdoc}
 */
function start () {}

/**
 * {@inheritdoc}
 */
function stop () {}

/**
 * {@inheritdoc}
 * @param int $type [optional]
 * @param array $filter [optional]
 */
function collect (int $type = NULL, array $filter = NULL) {}

/**
 * {@inheritdoc}
 * @param bool $files [optional]
 */
function clear (bool $files = NULL) {}

/**
 * {@inheritdoc}
 */
function waiting () {}

/**
 * {@inheritdoc}
 */
function memory () {}


}


namespace {

define ('pcov\all', 0);
define ('pcov\inclusive', 1);
define ('pcov\exclusive', 2);
define ('pcov\version', "1.0.11");


}

// End of pcov v.1.0.11
