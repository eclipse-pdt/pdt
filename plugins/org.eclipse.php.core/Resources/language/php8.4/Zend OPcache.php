<?php

// Start of Zend OPcache v.8.3.0

/**
 * {@inheritdoc}
 */
function opcache_reset (): bool {}

/**
 * {@inheritdoc}
 * @param bool $include_scripts [optional]
 */
function opcache_get_status (bool $include_scripts = true): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function opcache_compile_file (string $filename): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param bool $force [optional]
 */
function opcache_invalidate (string $filename, bool $force = false): bool {}

/**
 * {@inheritdoc}
 */
function opcache_get_configuration (): array|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function opcache_is_script_cached (string $filename): bool {}

// End of Zend OPcache v.8.3.0
