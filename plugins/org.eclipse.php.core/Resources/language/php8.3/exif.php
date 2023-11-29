<?php

// Start of exif v.8.3.0

/**
 * {@inheritdoc}
 * @param int $index
 */
function exif_tagname (int $index): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $file
 * @param string|null $required_sections [optional]
 * @param bool $as_arrays [optional]
 * @param bool $read_thumbnail [optional]
 */
function exif_read_data ($file = null, ?string $required_sections = NULL, bool $as_arrays = false, bool $read_thumbnail = false): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $file
 * @param mixed $width [optional]
 * @param mixed $height [optional]
 * @param mixed $image_type [optional]
 */
function exif_thumbnail ($file = null, &$width = NULL, &$height = NULL, &$image_type = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 */
function exif_imagetype (string $filename): int|false {}

define ('EXIF_USE_MBSTRING', 1);

// End of exif v.8.3.0
