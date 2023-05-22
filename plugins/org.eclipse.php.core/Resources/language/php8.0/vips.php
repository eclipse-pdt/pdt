<?php

// Start of vips v.1.0.13

/**
 * {@inheritdoc}
 * @param string $filename
 * @param array|null $options [optional]
 */
function vips_image_new_from_file (string $filename, ?array $options = array (
)): array|int {}

/**
 * {@inheritdoc}
 * @param string $buffer
 * @param string|null $option_string [optional]
 * @param array|null $options [optional]
 */
function vips_image_new_from_buffer (string $buffer, ?string $option_string = '', ?array $options = array (
)): array|int {}

/**
 * {@inheritdoc}
 * @param array $array
 * @param float|null $scale [optional]
 * @param float|null $offset [optional]
 */
function vips_image_new_from_array (array $array, ?float $scale = 1.0, ?float $offset = 0.0) {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string $filename
 * @param array|null $options [optional]
 */
function vips_image_write_to_file ($image = null, string $filename, ?array $options = array (
)): array|int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string $suffix
 * @param array|null $options [optional]
 */
function vips_image_write_to_buffer ($image = null, string $suffix, ?array $options = array (
)): array|int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 */
function vips_image_copy_memory ($image = null): array|int {}

/**
 * {@inheritdoc}
 * @param string $memory
 * @param int $width
 * @param int $height
 * @param int $bands
 * @param string $format
 */
function vips_image_new_from_memory (string $memory, int $width, int $height, int $bands, string $format): array|int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 */
function vips_image_write_to_memory ($image = null): string|int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 */
function vips_image_write_to_array ($image = null): array|int {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function vips_foreign_find_load (string $filename): string|int {}

/**
 * {@inheritdoc}
 * @param string $buffer
 */
function vips_foreign_find_load_buffer (string $buffer): string|int {}

/**
 * {@inheritdoc}
 * @param string $name
 */
function vips_interpolate_new (string $name) {}

/**
 * {@inheritdoc}
 * @param string $operation_name
 * @param mixed $instance
 * @param mixed $args [optional]
 */
function vips_call (string $operation_name, $instance = null, mixed ...$args): array|int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string $field
 */
function vips_image_get ($image = null, string $field): array|int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string $field
 */
function vips_image_get_typeof ($image = null, string $field): int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string $field
 * @param mixed $value
 */
function vips_image_set ($image = null, string $field, mixed $value = null): int {}

/**
 * {@inheritdoc}
 * @param string $name
 */
function vips_type_from_name (string $name): int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string|int $type
 * @param string $field
 * @param mixed $value
 */
function vips_image_set_type ($image = null, string|int $type, string $field, mixed $value = null): int {}

/**
 * {@inheritdoc}
 * @param mixed $image
 * @param string $field
 */
function vips_image_remove ($image = null, string $field): int {}

/**
 * {@inheritdoc}
 */
function vips_error_buffer (): string {}

/**
 * {@inheritdoc}
 * @param int $value
 */
function vips_cache_set_max (int $value): void {}

/**
 * {@inheritdoc}
 * @param int $value
 */
function vips_cache_set_max_mem (int $value): void {}

/**
 * {@inheritdoc}
 * @param int $value
 */
function vips_cache_set_max_files (int $value): void {}

/**
 * {@inheritdoc}
 * @param int $value
 */
function vips_concurrency_set (int $value): void {}

/**
 * {@inheritdoc}
 */
function vips_cache_get_max (): int {}

/**
 * {@inheritdoc}
 */
function vips_cache_get_max_mem (): int {}

/**
 * {@inheritdoc}
 */
function vips_cache_get_max_files (): int {}

/**
 * {@inheritdoc}
 */
function vips_cache_get_size (): int {}

/**
 * {@inheritdoc}
 */
function vips_concurrency_get (): int {}

/**
 * {@inheritdoc}
 */
function vips_version (): string {}

// End of vips v.1.0.13
