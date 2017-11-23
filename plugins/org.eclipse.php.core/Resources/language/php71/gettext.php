<?php

// Start of gettext v.7.1.1

/**
 * Sets the default domain
 * @link http://www.php.net/manual/en/function.textdomain.php
 * @param string $text_domain <p>
 * The new message domain, or null to get the current setting without
 * changing it
 * </p>
 * @return string If successful, this function returns the current message
 * domain, after possibly changing it.
 */
function textdomain (string $text_domain) {}

/**
 * Lookup a message in the current domain
 * @link http://www.php.net/manual/en/function.gettext.php
 * @param string $message <p>
 * The message being translated.
 * </p>
 * @return string a translated string if one is found in the 
 * translation table, or the submitted message if not found.
 */
function gettext (string $message) {}

/**
 * @param $msgid
 */
function _ ($msgid) {}

/**
 * Override the current domain
 * @link http://www.php.net/manual/en/function.dgettext.php
 * @param string $domain <p>
 * The domain
 * </p>
 * @param string $message <p>
 * The message
 * </p>
 * @return string A string on success.
 */
function dgettext (string $domain, string $message) {}

/**
 * Overrides the domain for a single lookup
 * @link http://www.php.net/manual/en/function.dcgettext.php
 * @param string $domain <p>
 * The domain
 * </p>
 * @param string $message <p>
 * The message
 * </p>
 * @param int $category <p>
 * The category
 * </p>
 * @return string A string on success.
 */
function dcgettext (string $domain, string $message, int $category) {}

/**
 * Sets the path for a domain
 * @link http://www.php.net/manual/en/function.bindtextdomain.php
 * @param string $domain <p>
 * The domain
 * </p>
 * @param string $directory <p>
 * The directory path
 * </p>
 * @return string The full pathname for the domain currently being set.
 */
function bindtextdomain (string $domain, string $directory) {}

/**
 * Plural version of gettext
 * @link http://www.php.net/manual/en/function.ngettext.php
 * @param string $msgid1 <p>
 * The singular message ID.
 * </p>
 * @param string $msgid2 <p>
 * The plural message ID.
 * </p>
 * @param int $n <p>
 * The number (e.g. item count) to determine the translation for the
 * respective grammatical number.
 * </p>
 * @return string correct plural form of message identified by 
 * msgid1 and msgid2
 * for count n.
 */
function ngettext (string $msgid1, string $msgid2, int $n) {}

/**
 * Plural version of dgettext
 * @link http://www.php.net/manual/en/function.dngettext.php
 * @param string $domain <p>
 * The domain
 * </p>
 * @param string $msgid1 <p>
 * </p>
 * @param string $msgid2 <p>
 * </p>
 * @param int $n <p>
 * </p>
 * @return string A string on success.
 */
function dngettext (string $domain, string $msgid1, string $msgid2, int $n) {}

/**
 * Plural version of dcgettext
 * @link http://www.php.net/manual/en/function.dcngettext.php
 * @param string $domain <p>
 * The domain
 * </p>
 * @param string $msgid1 <p>
 * </p>
 * @param string $msgid2 <p>
 * </p>
 * @param int $n <p>
 * </p>
 * @param int $category <p>
 * </p>
 * @return string A string on success.
 */
function dcngettext (string $domain, string $msgid1, string $msgid2, int $n, int $category) {}

/**
 * Specify the character encoding in which the messages from the DOMAIN message catalog will be returned
 * @link http://www.php.net/manual/en/function.bind-textdomain-codeset.php
 * @param string $domain <p>
 * The domain
 * </p>
 * @param string $codeset <p>
 * The code set
 * </p>
 * @return string A string on success.
 */
function bind_textdomain_codeset (string $domain, string $codeset) {}

// End of gettext v.7.1.1
