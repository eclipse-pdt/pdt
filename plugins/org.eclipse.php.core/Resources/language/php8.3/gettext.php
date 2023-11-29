<?php

// Start of gettext v.8.3.0

/**
 * {@inheritdoc}
 * @param string|null $domain
 */
function textdomain (?string $domain = null): string {}

/**
 * {@inheritdoc}
 * @param string $message
 */
function gettext (string $message): string {}

/**
 * {@inheritdoc}
 * @param string $message
 */
function _ (string $message): string {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param string $message
 */
function dgettext (string $domain, string $message): string {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param string $message
 * @param int $category
 */
function dcgettext (string $domain, string $message, int $category): string {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param string|null $directory
 */
function bindtextdomain (string $domain, ?string $directory = null): string|false {}

/**
 * {@inheritdoc}
 * @param string $singular
 * @param string $plural
 * @param int $count
 */
function ngettext (string $singular, string $plural, int $count): string {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param string $singular
 * @param string $plural
 * @param int $count
 */
function dngettext (string $domain, string $singular, string $plural, int $count): string {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param string $singular
 * @param string $plural
 * @param int $count
 * @param int $category
 */
function dcngettext (string $domain, string $singular, string $plural, int $count, int $category): string {}

/**
 * {@inheritdoc}
 * @param string $domain
 * @param string|null $codeset
 */
function bind_textdomain_codeset (string $domain, ?string $codeset = null): string|false {}

// End of gettext v.8.3.0
