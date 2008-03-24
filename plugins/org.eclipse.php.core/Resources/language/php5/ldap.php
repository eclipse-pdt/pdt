<?php

// Start of ldap v.

/**
 * Connect to an LDAP server
 * @link http://php.net/manual/en/function.ldap-connect.php
 * @param hostname string[optional]
 * @param port int[optional]
 * @return resource a positive LDAP link identifier on success, or false on error.
 */
function ldap_connect ($hostname = null, $port = null) {}

/**
 * &Alias; <function>ldap_unbind</function>
 * @link http://php.net/manual/en/function.ldap-close.php
 */
function ldap_close () {}

/**
 * Bind to LDAP directory
 * @link http://php.net/manual/en/function.ldap-bind.php
 * @param link_identifier resource
 * @param bind_rdn string[optional]
 * @param bind_password string[optional]
 * @return bool 
 */
function ldap_bind ($link_identifier, $bind_rdn = null, $bind_password = null) {}

/**
 * Unbind from LDAP directory
 * @link http://php.net/manual/en/function.ldap-unbind.php
 * @param link_identifier resource
 * @return bool 
 */
function ldap_unbind ($link_identifier) {}

/**
 * Read an entry
 * @link http://php.net/manual/en/function.ldap-read.php
 * @param link_identifier resource
 * @param base_dn string
 * @param filter string
 * @param attributes array[optional]
 * @param attrsonly int[optional]
 * @param sizelimit int[optional]
 * @param timelimit int[optional]
 * @param deref int[optional]
 * @return resource a search result identifier or false on error.
 */
function ldap_read ($link_identifier, $base_dn, $filter, array $attributes = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Single-level search
 * @link http://php.net/manual/en/function.ldap-list.php
 * @param link_identifier resource
 * @param base_dn string
 * @param filter string
 * @param attributes array[optional]
 * @param attrsonly int[optional]
 * @param sizelimit int[optional]
 * @param timelimit int[optional]
 * @param deref int[optional]
 * @return resource a search result identifier or false on error.
 */
function ldap_list ($link_identifier, $base_dn, $filter, array $attributes = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Search LDAP tree
 * @link http://php.net/manual/en/function.ldap-search.php
 * @param link_identifier resource
 * @param base_dn string
 * @param filter string
 * @param attributes array[optional]
 * @param attrsonly int[optional]
 * @param sizelimit int[optional]
 * @param timelimit int[optional]
 * @param deref int[optional]
 * @return resource a search result identifier or false on error.
 */
function ldap_search ($link_identifier, $base_dn, $filter, array $attributes = null, $attrsonly = null, $sizelimit = null, $timelimit = null, $deref = null) {}

/**
 * Free result memory
 * @link http://php.net/manual/en/function.ldap-free-result.php
 * @param result_identifier resource
 * @return bool 
 */
function ldap_free_result ($result_identifier) {}

/**
 * Count the number of entries in a search
 * @link http://php.net/manual/en/function.ldap-count-entries.php
 * @param link_identifier resource
 * @param result_identifier resource
 * @return int number of entries in the result or false on error.
 */
function ldap_count_entries ($link_identifier, $result_identifier) {}

/**
 * Return first result id
 * @link http://php.net/manual/en/function.ldap-first-entry.php
 * @param link_identifier resource
 * @param result_identifier resource
 * @return resource the result entry identifier for the first entry on success and
 */
function ldap_first_entry ($link_identifier, $result_identifier) {}

/**
 * Get next result entry
 * @link http://php.net/manual/en/function.ldap-next-entry.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return resource entry identifier for the next entry in the result whose entries
 */
function ldap_next_entry ($link_identifier, $result_entry_identifier) {}

/**
 * Get all result entries
 * @link http://php.net/manual/en/function.ldap-get-entries.php
 * @param link_identifier resource
 * @param result_identifier resource
 * @return array a complete result information in a multi-dimensional array on
 */
function ldap_get_entries ($link_identifier, $result_identifier) {}

/**
 * Return first attribute
 * @link http://php.net/manual/en/function.ldap-first-attribute.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return string the first attribute in the entry on success and false on
 */
function ldap_first_attribute ($link_identifier, $result_entry_identifier) {}

/**
 * Get the next attribute in result
 * @link http://php.net/manual/en/function.ldap-next-attribute.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return string the next attribute in an entry on success and false on
 */
function ldap_next_attribute ($link_identifier, $result_entry_identifier) {}

/**
 * Get attributes from a search result entry
 * @link http://php.net/manual/en/function.ldap-get-attributes.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return array a complete entry information in a multi-dimensional array
 */
function ldap_get_attributes ($link_identifier, $result_entry_identifier) {}

/**
 * Get all values from a result entry
 * @link http://php.net/manual/en/function.ldap-get-values.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @param attribute string
 * @return array an array of values for the attribute on success and false on
 */
function ldap_get_values ($link_identifier, $result_entry_identifier, $attribute) {}

/**
 * Get all binary values from a result entry
 * @link http://php.net/manual/en/function.ldap-get-values-len.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @param attribute string
 * @return array an array of values for the attribute on success and false on
 */
function ldap_get_values_len ($link_identifier, $result_entry_identifier, $attribute) {}

/**
 * Get the DN of a result entry
 * @link http://php.net/manual/en/function.ldap-get-dn.php
 * @param link_identifier resource
 * @param result_entry_identifier resource
 * @return string the DN of the result entry and false on error.
 */
function ldap_get_dn ($link_identifier, $result_entry_identifier) {}

/**
 * Splits DN into its component parts
 * @link http://php.net/manual/en/function.ldap-explode-dn.php
 * @param dn string
 * @param with_attrib int
 * @return array an array of all DN components.
 */
function ldap_explode_dn ($dn, $with_attrib) {}

/**
 * Convert DN to User Friendly Naming format
 * @link http://php.net/manual/en/function.ldap-dn2ufn.php
 * @param dn string
 * @return string the user friendly name.
 */
function ldap_dn2ufn ($dn) {}

/**
 * Add entries to LDAP directory
 * @link http://php.net/manual/en/function.ldap-add.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool 
 */
function ldap_add ($link_identifier, $dn, array $entry) {}

/**
 * Delete an entry from a directory
 * @link http://php.net/manual/en/function.ldap-delete.php
 * @param link_identifier resource
 * @param dn string
 * @return bool 
 */
function ldap_delete ($link_identifier, $dn) {}

/**
 * Modify an LDAP entry
 * @link http://php.net/manual/en/function.ldap-modify.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool 
 */
function ldap_modify ($link_identifier, $dn, array $entry) {}

/**
 * Add attribute values to current attributes
 * @link http://php.net/manual/en/function.ldap-mod-add.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool 
 */
function ldap_mod_add ($link_identifier, $dn, array $entry) {}

/**
 * Replace attribute values with new ones
 * @link http://php.net/manual/en/function.ldap-mod-replace.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool 
 */
function ldap_mod_replace ($link_identifier, $dn, array $entry) {}

/**
 * Delete attribute values from current attributes
 * @link http://php.net/manual/en/function.ldap-mod-del.php
 * @param link_identifier resource
 * @param dn string
 * @param entry array
 * @return bool 
 */
function ldap_mod_del ($link_identifier, $dn, array $entry) {}

/**
 * Return the LDAP error number of the last LDAP command
 * @link http://php.net/manual/en/function.ldap-errno.php
 * @param link_identifier resource
 * @return int 
 */
function ldap_errno ($link_identifier) {}

/**
 * Convert LDAP error number into string error message
 * @link http://php.net/manual/en/function.ldap-err2str.php
 * @param errno int
 * @return string the error message, as a string.
 */
function ldap_err2str ($errno) {}

/**
 * Return the LDAP error message of the last LDAP command
 * @link http://php.net/manual/en/function.ldap-error.php
 * @param link_identifier resource
 * @return string string error message.
 */
function ldap_error ($link_identifier) {}

/**
 * Compare value of attribute found in entry specified with DN
 * @link http://php.net/manual/en/function.ldap-compare.php
 * @param link_identifier resource
 * @param dn string
 * @param attribute string
 * @param value string
 * @return mixed true if value matches otherwise returns
 */
function ldap_compare ($link_identifier, $dn, $attribute, $value) {}

/**
 * Sort LDAP result entries
 * @link http://php.net/manual/en/function.ldap-sort.php
 * @param link resource
 * @param result resource
 * @param sortfilter string
 * @return bool 
 */
function ldap_sort ($link, $result, $sortfilter) {}

/**
 * Modify the name of an entry
 * @link http://php.net/manual/en/function.ldap-rename.php
 * @param link_identifier resource
 * @param dn string
 * @param newrdn string
 * @param newparent string
 * @param deleteoldrdn bool
 * @return bool 
 */
function ldap_rename ($link_identifier, $dn, $newrdn, $newparent, $deleteoldrdn) {}

/**
 * Get the current value for given option
 * @link http://php.net/manual/en/function.ldap-get-option.php
 * @param link_identifier resource
 * @param option int
 * @param retval mixed
 * @return bool 
 */
function ldap_get_option ($link_identifier, $option, &$retval) {}

/**
 * Set the value of the given option
 * @link http://php.net/manual/en/function.ldap-set-option.php
 * @param link_identifier resource
 * @param option int
 * @param newval mixed
 * @return bool 
 */
function ldap_set_option ($link_identifier, $option, $newval) {}

/**
 * Return first reference
 * @link http://php.net/manual/en/function.ldap-first-reference.php
 * @param link resource
 * @param result resource
 * @return resource 
 */
function ldap_first_reference ($link, $result) {}

/**
 * Get next reference
 * @link http://php.net/manual/en/function.ldap-next-reference.php
 * @param link resource
 * @param entry resource
 * @return resource 
 */
function ldap_next_reference ($link, $entry) {}

/**
 * Extract information from reference entry
 * @link http://php.net/manual/en/function.ldap-parse-reference.php
 * @param link resource
 * @param entry resource
 * @param referrals array
 * @return bool 
 */
function ldap_parse_reference ($link, $entry, array &$referrals) {}

/**
 * Extract information from result
 * @link http://php.net/manual/en/function.ldap-parse-result.php
 * @param link resource
 * @param result resource
 * @param errcode int
 * @param matcheddn string[optional]
 * @param errmsg string[optional]
 * @param referrals array[optional]
 * @return bool 
 */
function ldap_parse_result ($link, $result, &$errcode, &$matcheddn = null, &$errmsg = null, array &$referrals = null) {}

/**
 * Start TLS
 * @link http://php.net/manual/en/function.ldap-start-tls.php
 * @param link resource
 * @return bool 
 */
function ldap_start_tls ($link) {}

/**
 * Set a callback function to do re-binds on referral chasing
 * @link http://php.net/manual/en/function.ldap-set-rebind-proc.php
 * @param link resource
 * @param callback callback
 * @return bool 
 */
function ldap_set_rebind_proc ($link, $callback) {}

define ('LDAP_DEREF_NEVER', 0);
define ('LDAP_DEREF_SEARCHING', 1);
define ('LDAP_DEREF_FINDING', 2);
define ('LDAP_DEREF_ALWAYS', 3);
define ('LDAP_OPT_DEREF', 2);
define ('LDAP_OPT_SIZELIMIT', 3);
define ('LDAP_OPT_TIMELIMIT', 4);
define ('LDAP_OPT_PROTOCOL_VERSION', 17);
define ('LDAP_OPT_ERROR_NUMBER', 49);
define ('LDAP_OPT_REFERRALS', 8);
define ('LDAP_OPT_RESTART', 9);
define ('LDAP_OPT_HOST_NAME', 48);
define ('LDAP_OPT_ERROR_STRING', 50);
define ('LDAP_OPT_MATCHED_DN', 51);
define ('LDAP_OPT_SERVER_CONTROLS', 18);
define ('LDAP_OPT_CLIENT_CONTROLS', 19);
define ('LDAP_OPT_DEBUG_LEVEL', 20481);

// End of ldap v.
?>
