<?php

// Start of bz2 v.8.3.0

/**
 * {@inheritdoc}
 * @param mixed $file
 * @param string $mode
 */
function bzopen ($file = null, string $mode) {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 * @param int $length [optional]
 */
function bzread ($bz = null, int $length = 1024): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 * @param string $data
 * @param int|null $length [optional]
 */
function bzwrite ($bz = null, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 */
function bzflush ($bz = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 */
function bzclose ($bz = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 */
function bzerrno ($bz = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 */
function bzerrstr ($bz = null): string {}

/**
 * {@inheritdoc}
 * @param mixed $bz
 */
function bzerror ($bz = null): array {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param int $block_size [optional]
 * @param int $work_factor [optional]
 */
function bzcompress (string $data, int $block_size = 4, int $work_factor = 0): string|int {}

/**
 * {@inheritdoc}
 * @param string $data
 * @param bool $use_less_memory [optional]
 */
function bzdecompress (string $data, bool $use_less_memory = false): string|int|false {}

// End of bz2 v.8.3.0
