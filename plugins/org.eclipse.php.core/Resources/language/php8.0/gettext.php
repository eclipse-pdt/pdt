<?php

// Start of gettext v.8.0.28

/**
 * Sets the default domain
 * @link http://www.php.net/manual/en/function.textdomain.php
 * @param mixed $domain The new message domain, or null to get the current setting without
 * changing it
 * @return string If successful, this function returns the current message
 * domain, after possibly changing it.
 */
function textdomain ($domain): string {}

/**
 * Lookup a message in the current domain
 * @link http://www.php.net/manual/en/function.gettext.php
 * @param string $message The message being translated.
 * @return string a translated string if one is found in the 
 * translation table, or the submitted message if not found.
 */
function gettext (string $message): string {}

/**
 * @param string $message
 */
function _ (string $message): string {}

/**
 * Override the current domain
 * @link http://www.php.net/manual/en/function.dgettext.php
 * @param string $domain The domain
 * @param string $message The message
 * @return string A string on success.
 */
function dgettext (string $domain, string $message): string {}

/**
 * Overrides the domain for a single lookup
 * @link http://www.php.net/manual/en/function.dcgettext.php
 * @param string $domain The domain
 * @param string $message The message
 * @param int $category The category
 * @return string A string on success.
 */
function dcgettext (string $domain, string $message, int $category): string {}

/**
 * Sets or gets the path for a domain
 * @link http://www.php.net/manual/en/function.bindtextdomain.php
 * @param string $domain The domain.
 * @param mixed $directory The directory path.
 * An empty string means the current directory.
 * If null, the currently set directory is returned.
 * @return mixed The full pathname for the domain currently being set,
 * or false on failure.
 */
function bindtextdomain (string $domain, $directory): string|false {}

/**
 * Plural version of gettext
 * @link http://www.php.net/manual/en/function.ngettext.php
 * @param string $singular The singular message ID.
 * @param string $plural The plural message ID.
 * @param int $count The number (e.g. item count) to determine the translation for the
 * respective grammatical number.
 * @return string correct plural form of message identified by 
 * singular and plural
 * for count count.
 */
function ngettext (string $singular, string $plural, int $count): string {}

/**
 * Plural version of dgettext
 * @link http://www.php.net/manual/en/function.dngettext.php
 * @param string $domain The domain
 * @param string $singular 
 * @param string $plural 
 * @param int $count 
 * @return string A string on success.
 */
function dngettext (string $domain, string $singular, string $plural, int $count): string {}

/**
 * Plural version of dcgettext
 * @link http://www.php.net/manual/en/function.dcngettext.php
 * @param string $domain The domain
 * @param string $singular 
 * @param string $plural 
 * @param int $count 
 * @param int $category 
 * @return string A string on success.
 */
function dcngettext (string $domain, string $singular, string $plural, int $count, int $category): string {}

/**
 * Specify or get the character encoding in which the messages from the DOMAIN message catalog will be returned
 * @link http://www.php.net/manual/en/function.bind-textdomain-codeset.php
 * @param string $domain The domain.
 * @param mixed $codeset The code set.
 * If null, the currently set encoding is returned.
 * @return mixed A string on success.
 */
function bind_textdomain_codeset (string $domain, $codeset): string|false {}

// End of gettext v.8.0.28