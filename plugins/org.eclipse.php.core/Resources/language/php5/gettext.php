<?php

// Start of gettext v.

/**
 * Sets the default domain
 * @link http://php.net/manual/en/function.textdomain.php
 * @param text_domain string
 * @return string 
 */
function textdomain ($text_domain) {}

/**
 * Lookup a message in the current domain
 * @link http://php.net/manual/en/function.gettext.php
 * @param message string
 * @return string a translated string if one is found in the
 */
function gettext ($message) {}

/**
 * @param msgid
 */
function _ ($msgid) {}

/**
 * Override the current domain
 * @link http://php.net/manual/en/function.dgettext.php
 * @param domain string
 * @param message string
 * @return string 
 */
function dgettext ($domain, $message) {}

/**
 * Overrides the domain for a single lookup
 * @link http://php.net/manual/en/function.dcgettext.php
 * @param domain string
 * @param message string
 * @param category int
 * @return string 
 */
function dcgettext ($domain, $message, $category) {}

/**
 * Sets the path for a domain
 * @link http://php.net/manual/en/function.bindtextdomain.php
 * @param domain string
 * @param directory string
 * @return string 
 */
function bindtextdomain ($domain, $directory) {}

/**
 * Plural version of gettext
 * @link http://php.net/manual/en/function.ngettext.php
 * @param msgid1 string
 * @param msgid2 string
 * @param n int
 * @return string correct plural form of message identified by
 */
function ngettext ($msgid1, $msgid2, $n) {}

/**
 * Plural version of dgettext
 * @link http://php.net/manual/en/function.dngettext.php
 * @param domain string
 * @param msgid1 string
 * @param msgid2 string
 * @param n int
 * @return string 
 */
function dngettext ($domain, $msgid1, $msgid2, $n) {}

/**
 * Plural version of dcgettext
 * @link http://php.net/manual/en/function.dcngettext.php
 * @param domain string
 * @param msgid1 string
 * @param msgid2 string
 * @param n int
 * @param category int
 * @return string 
 */
function dcngettext ($domain, $msgid1, $msgid2, $n, $category) {}

/**
 * Specify the character encoding in which the messages from the DOMAIN message catalog will be returned
 * @link http://php.net/manual/en/function.bind-textdomain-codeset.php
 * @param domain string
 * @param codeset string
 * @return string 
 */
function bind_textdomain_codeset ($domain, $codeset) {}

// End of gettext v.
?>
