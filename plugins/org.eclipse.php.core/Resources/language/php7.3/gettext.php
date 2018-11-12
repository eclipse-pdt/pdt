<?php

// Start of gettext v.7.3.0

/**
 * Sets the default domain
 * @link http://www.php.net/manual/en/function.textdomain.php
 * @param string $text_domain The new message domain, or null to get the current setting without
 * changing it
 * @return string If successful, this function returns the current message
 * domain, after possibly changing it.
 */
function textdomain (string $text_domain) {}

/**
 * Lookup a message in the current domain
 * @link http://www.php.net/manual/en/function.gettext.php
 * @param string $message The message being translated.
 * @return string a translated string if one is found in the 
 * translation table, or the submitted message if not found.
 */
function gettext (string $message) {}

/**
 * @param mixed $msgid
 */
function _ ($msgid) {}

/**
 * Override the current domain
 * @link http://www.php.net/manual/en/function.dgettext.php
 * @param string $domain The domain
 * @param string $message The message
 * @return string A string on success.
 */
function dgettext (string $domain, string $message) {}

/**
 * Overrides the domain for a single lookup
 * @link http://www.php.net/manual/en/function.dcgettext.php
 * @param string $domain The domain
 * @param string $message The message
 * @param int $category The category
 * @return string A string on success.
 */
function dcgettext (string $domain, string $message, int $category) {}

/**
 * Sets the path for a domain
 * @link http://www.php.net/manual/en/function.bindtextdomain.php
 * @param string $domain The domain
 * @param string $directory The directory path
 * @return string The full pathname for the domain currently being set.
 */
function bindtextdomain (string $domain, string $directory) {}

/**
 * Plural version of gettext
 * @link http://www.php.net/manual/en/function.ngettext.php
 * @param string $msgid1 The singular message ID.
 * @param string $msgid2 The plural message ID.
 * @param int $n The number (e.g. item count) to determine the translation for the
 * respective grammatical number.
 * @return string correct plural form of message identified by 
 * msgid1 and msgid2
 * for count n.
 */
function ngettext (string $msgid1, string $msgid2, int $n) {}

/**
 * Plural version of dgettext
 * @link http://www.php.net/manual/en/function.dngettext.php
 * @param string $domain The domain
 * @param string $msgid1 
 * @param string $msgid2 
 * @param int $n 
 * @return string A string on success.
 */
function dngettext (string $domain, string $msgid1, string $msgid2, int $n) {}

/**
 * Plural version of dcgettext
 * @link http://www.php.net/manual/en/function.dcngettext.php
 * @param string $domain The domain
 * @param string $msgid1 
 * @param string $msgid2 
 * @param int $n 
 * @param int $category 
 * @return string A string on success.
 */
function dcngettext (string $domain, string $msgid1, string $msgid2, int $n, int $category) {}

/**
 * Specify the character encoding in which the messages from the DOMAIN message catalog will be returned
 * @link http://www.php.net/manual/en/function.bind-textdomain-codeset.php
 * @param string $domain The domain
 * @param string $codeset The code set
 * @return string A string on success.
 */
function bind_textdomain_codeset (string $domain, string $codeset) {}

// End of gettext v.7.3.0
