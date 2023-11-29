<?php

// Start of mbstring v.8.3.0

/**
 * {@inheritdoc}
 * @param string|null $language [optional]
 */
function mb_language (?string $language = NULL): string|bool {}

/**
 * {@inheritdoc}
 * @param string|null $encoding [optional]
 */
function mb_internal_encoding (?string $encoding = NULL): string|bool {}

/**
 * {@inheritdoc}
 * @param string|null $type [optional]
 */
function mb_http_input (?string $type = NULL): array|string|false {}

/**
 * {@inheritdoc}
 * @param string|null $encoding [optional]
 */
function mb_http_output (?string $encoding = NULL): string|bool {}

/**
 * {@inheritdoc}
 * @param array|string|null $encoding [optional]
 */
function mb_detect_order (array|string|null $encoding = NULL): array|bool {}

/**
 * {@inheritdoc}
 * @param string|int|null $substitute_character [optional]
 */
function mb_substitute_character (string|int|null $substitute_character = NULL): string|int|bool {}

/**
 * {@inheritdoc}
 * @param string $encoding
 */
function mb_preferred_mime_name (string $encoding): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param mixed $result
 */
function mb_parse_str (string $string, &$result = null): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $status
 */
function mb_output_handler (string $string, int $status): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $length [optional]
 * @param string|null $encoding [optional]
 */
function mb_str_split (string $string, int $length = 1, ?string $encoding = NULL): array {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function mb_strlen (string $string, ?string $encoding = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 * @param string|null $encoding [optional]
 */
function mb_strpos (string $haystack, string $needle, int $offset = 0, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 * @param string|null $encoding [optional]
 */
function mb_strrpos (string $haystack, string $needle, int $offset = 0, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 * @param string|null $encoding [optional]
 */
function mb_stripos (string $haystack, string $needle, int $offset = 0, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param int $offset [optional]
 * @param string|null $encoding [optional]
 */
function mb_strripos (string $haystack, string $needle, int $offset = 0, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 * @param string|null $encoding [optional]
 */
function mb_strstr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 * @param string|null $encoding [optional]
 */
function mb_strrchr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 * @param string|null $encoding [optional]
 */
function mb_stristr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param bool $before_needle [optional]
 * @param string|null $encoding [optional]
 */
function mb_strrichr (string $haystack, string $needle, bool $before_needle = false, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $haystack
 * @param string $needle
 * @param string|null $encoding [optional]
 */
function mb_substr_count (string $haystack, string $needle, ?string $encoding = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $start
 * @param int|null $length [optional]
 * @param string|null $encoding [optional]
 */
function mb_substr (string $string, int $start, ?int $length = NULL, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $start
 * @param int|null $length [optional]
 * @param string|null $encoding [optional]
 */
function mb_strcut (string $string, int $start, ?int $length = NULL, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function mb_strwidth (string $string, ?string $encoding = NULL): int {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $start
 * @param int $width
 * @param string $trim_marker [optional]
 * @param string|null $encoding [optional]
 */
function mb_strimwidth (string $string, int $start, int $width, string $trim_marker = '', ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param array|string $string
 * @param string $to_encoding
 * @param array|string|null $from_encoding [optional]
 */
function mb_convert_encoding (array|string $string, string $to_encoding, array|string|null $from_encoding = NULL): array|string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $mode
 * @param string|null $encoding [optional]
 */
function mb_convert_case (string $string, int $mode, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function mb_strtoupper (string $string, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function mb_strtolower (string $string, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array|string|null $encodings [optional]
 * @param bool $strict [optional]
 */
function mb_detect_encoding (string $string, array|string|null $encodings = NULL, bool $strict = false): string|false {}

/**
 * {@inheritdoc}
 */
function mb_list_encodings (): array {}

/**
 * {@inheritdoc}
 * @param string $encoding
 */
function mb_encoding_aliases (string $encoding): array {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $charset [optional]
 * @param string|null $transfer_encoding [optional]
 * @param string $newline [optional]
 * @param int $indent [optional]
 */
function mb_encode_mimeheader (string $string, ?string $charset = NULL, ?string $transfer_encoding = NULL, string $newline = '
', int $indent = 0): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function mb_decode_mimeheader (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $mode [optional]
 * @param string|null $encoding [optional]
 */
function mb_convert_kana (string $string, string $mode = 'KV', ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $to_encoding
 * @param array|string $from_encoding
 * @param mixed $var
 * @param mixed $vars [optional]
 */
function mb_convert_variables (string $to_encoding, array|string $from_encoding, mixed &$var = null, mixed &...$vars): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array $map
 * @param string|null $encoding [optional]
 * @param bool $hex [optional]
 */
function mb_encode_numericentity (string $string, array $map, ?string $encoding = NULL, bool $hex = false): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param array $map
 * @param string|null $encoding [optional]
 */
function mb_decode_numericentity (string $string, array $map, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $to
 * @param string $subject
 * @param string $message
 * @param array|string $additional_headers [optional]
 * @param string|null $additional_params [optional]
 */
function mb_send_mail (string $to, string $subject, string $message, array|string $additional_headers = array (
), ?string $additional_params = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $type [optional]
 */
function mb_get_info (string $type = 'all'): array|string|int|false {}

/**
 * {@inheritdoc}
 * @param array|string|null $value [optional]
 * @param string|null $encoding [optional]
 */
function mb_check_encoding (array|string|null $value = NULL, ?string $encoding = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function mb_scrub (string $string, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $encoding [optional]
 */
function mb_ord (string $string, ?string $encoding = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param int $codepoint
 * @param string|null $encoding [optional]
 */
function mb_chr (int $codepoint, ?string $encoding = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param int $length
 * @param string $pad_string [optional]
 * @param int $pad_type [optional]
 * @param string|null $encoding [optional]
 */
function mb_str_pad (string $string, int $length, string $pad_string = ' ', int $pad_type = 1, ?string $encoding = NULL): string {}

/**
 * {@inheritdoc}
 * @param string|null $encoding [optional]
 */
function mb_regex_encoding (?string $encoding = NULL): string|bool {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $string
 * @param mixed $matches [optional]
 */
function mb_ereg (string $pattern, string $string, &$matches = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $string
 * @param mixed $matches [optional]
 */
function mb_eregi (string $pattern, string $string, &$matches = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $replacement
 * @param string $string
 * @param string|null $options [optional]
 */
function mb_ereg_replace (string $pattern, string $replacement, string $string, ?string $options = NULL): string|false|null {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $replacement
 * @param string $string
 * @param string|null $options [optional]
 */
function mb_eregi_replace (string $pattern, string $replacement, string $string, ?string $options = NULL): string|false|null {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param callable $callback
 * @param string $string
 * @param string|null $options [optional]
 */
function mb_ereg_replace_callback (string $pattern, callable $callback, string $string, ?string $options = NULL): string|false|null {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $string
 * @param int $limit [optional]
 */
function mb_split (string $pattern, string $string, int $limit = -1): array|false {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $string
 * @param string|null $options [optional]
 */
function mb_ereg_match (string $pattern, string $string, ?string $options = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string|null $pattern [optional]
 * @param string|null $options [optional]
 */
function mb_ereg_search (?string $pattern = NULL, ?string $options = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string|null $pattern [optional]
 * @param string|null $options [optional]
 */
function mb_ereg_search_pos (?string $pattern = NULL, ?string $options = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param string|null $pattern [optional]
 * @param string|null $options [optional]
 */
function mb_ereg_search_regs (?string $pattern = NULL, ?string $options = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string|null $pattern [optional]
 * @param string|null $options [optional]
 */
function mb_ereg_search_init (string $string, ?string $pattern = NULL, ?string $options = NULL): bool {}

/**
 * {@inheritdoc}
 */
function mb_ereg_search_getregs (): array|false {}

/**
 * {@inheritdoc}
 */
function mb_ereg_search_getpos (): int {}

/**
 * {@inheritdoc}
 * @param int $offset
 */
function mb_ereg_search_setpos (int $offset): bool {}

/**
 * {@inheritdoc}
 * @param string|null $options [optional]
 */
function mb_regex_set_options (?string $options = NULL): string {}

define ('MB_ONIGURUMA_VERSION', "6.9.9");
define ('MB_CASE_UPPER', 0);
define ('MB_CASE_LOWER', 1);
define ('MB_CASE_TITLE', 2);
define ('MB_CASE_FOLD', 3);
define ('MB_CASE_UPPER_SIMPLE', 4);
define ('MB_CASE_LOWER_SIMPLE', 5);
define ('MB_CASE_TITLE_SIMPLE', 6);
define ('MB_CASE_FOLD_SIMPLE', 7);

// End of mbstring v.8.3.0
