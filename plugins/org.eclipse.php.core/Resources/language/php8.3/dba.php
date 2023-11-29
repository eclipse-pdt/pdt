<?php

// Start of dba v.8.3.0

/**
 * {@inheritdoc}
 * @param string $path
 * @param string $mode
 * @param string|null $handler [optional]
 * @param int $permission [optional]
 * @param int $map_size [optional]
 * @param int|null $flags [optional]
 */
function dba_popen (string $path, string $mode, ?string $handler = NULL, int $permission = 420, int $map_size = 0, ?int $flags = NULL) {}

/**
 * {@inheritdoc}
 * @param string $path
 * @param string $mode
 * @param string|null $handler [optional]
 * @param int $permission [optional]
 * @param int $map_size [optional]
 * @param int|null $flags [optional]
 */
function dba_open (string $path, string $mode, ?string $handler = NULL, int $permission = 420, int $map_size = 0, ?int $flags = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $dba
 */
function dba_close ($dba = null): void {}

/**
 * {@inheritdoc}
 * @param array|string $key
 * @param mixed $dba
 */
function dba_exists (array|string $key, $dba = null): bool {}

/**
 * {@inheritdoc}
 * @param array|string $key
 * @param mixed $dba
 * @param mixed $skip [optional]
 */
function dba_fetch (array|string $key, $dba = null, $skip = 0): string|false {}

/**
 * {@inheritdoc}
 * @param string|false|null $key
 */
function dba_key_split (string|false|null $key = null): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $dba
 */
function dba_firstkey ($dba = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $dba
 */
function dba_nextkey ($dba = null): string|false {}

/**
 * {@inheritdoc}
 * @param array|string $key
 * @param mixed $dba
 */
function dba_delete (array|string $key, $dba = null): bool {}

/**
 * {@inheritdoc}
 * @param array|string $key
 * @param string $value
 * @param mixed $dba
 */
function dba_insert (array|string $key, string $value, $dba = null): bool {}

/**
 * {@inheritdoc}
 * @param array|string $key
 * @param string $value
 * @param mixed $dba
 */
function dba_replace (array|string $key, string $value, $dba = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $dba
 */
function dba_optimize ($dba = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $dba
 */
function dba_sync ($dba = null): bool {}

/**
 * {@inheritdoc}
 * @param bool $full_info [optional]
 */
function dba_handlers (bool $full_info = false): array {}

/**
 * {@inheritdoc}
 */
function dba_list (): array {}

// End of dba v.8.3.0
