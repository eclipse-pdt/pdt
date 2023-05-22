<?php

// Start of ldap v.8.2.6

namespace LDAP {

/**
 * A fully opaque class which replaces a ldap resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.ldap-connection.php
 */
final class Connection  {
}

/**
 * A fully opaque class which replaces a ldap result resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.ldap-result.php
 */
final class Result  {
}

/**
 * A fully opaque class which replaces a ldap result entry resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.ldap-resultentry.php
 */
final class ResultEntry  {
}


}


namespace {

/**
 * Connect to an LDAP server
 * @link http://www.php.net/manual/en/function.ldap-connect.php
 * @param string|null $uri [optional] 
 * @return LDAP\Connection|false Returns an LDAP\Connection instance when the provided LDAP URI
 * seems plausible. It's a syntactic check of the provided parameter but the server(s) will not
 * be contacted! If the syntactic check fails it returns false.
 * ldap_connect will otherwise
 * return a LDAP\Connection instance as it does not actually connect but just
 * initializes the connecting parameters. The actual connect happens with
 * the next calls to ldap_&#42; functions, usually with
 * ldap_bind.
 * <p>If no argument is specified then the LDAP\Connection instance of the already
 * opened connection will be returned.</p>
 */
function ldap_connect (?string $uri = null): LDAP\Connection|false {}

/**
 * Unbind from LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-unbind.php
 * @param LDAP\Connection $ldap 
 * @return bool Returns true on success or false on failure.
 */
function ldap_unbind (LDAP\Connection $ldap): bool {}

/**
 * Alias of ldap_unbind
 * @link http://www.php.net/manual/en/function.ldap-close.php
 * @param LDAP\Connection $ldap 
 * @return bool Returns true on success or false on failure.
 */
function ldap_close (LDAP\Connection $ldap): bool {}

/**
 * Bind to LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-bind.php
 * @param LDAP\Connection $ldap 
 * @param string|null $dn [optional] 
 * @param string|null $password [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_bind (LDAP\Connection $ldap, ?string $dn = null, ?string $password = null): bool {}

/**
 * Bind to LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-bind-ext.php
 * @param LDAP\Connection $ldap 
 * @param string|null $dn [optional] 
 * @param string|null $password [optional] 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_bind_ext (LDAP\Connection $ldap, ?string $dn = null, ?string $password = null, ?array $controls = null): LDAP\Result|false {}

/**
 * Bind to LDAP directory using SASL
 * @link http://www.php.net/manual/en/function.ldap-sasl-bind.php
 * @param LDAP\Connection $ldap 
 * @param string|null $dn [optional] 
 * @param string|null $password [optional] 
 * @param string|null $mech [optional] 
 * @param string|null $realm [optional] 
 * @param string|null $authc_id [optional] 
 * @param string|null $authz_id [optional] 
 * @param string|null $props [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_sasl_bind (LDAP\Connection $ldap, ?string $dn = null, ?string $password = null, ?string $mech = null, ?string $realm = null, ?string $authc_id = null, ?string $authz_id = null, ?string $props = null): bool {}

/**
 * Read an entry
 * @link http://www.php.net/manual/en/function.ldap-read.php
 * @param LDAP\Connection|array $ldap 
 * @param array|string $base 
 * @param array|string $filter 
 * @param array $attributes [optional] 
 * @param int $attributes_only [optional] 
 * @param int $sizelimit [optional] 
 * @param int $timelimit [optional] 
 * @param int $deref [optional] 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|array|false Returns an LDAP\Result instance, an array of LDAP\Result instances, or false on failure.
 */
function ldap_read (LDAP\Connection|array $ldap, array|string $base, array|string $filter, array $attributes = '[]', int $attributes_only = null, int $sizelimit = -1, int $timelimit = -1, int $deref = LDAP_DEREF_NEVER, ?array $controls = null): LDAP\Result|array|false {}

/**
 * Single-level search
 * @link http://www.php.net/manual/en/function.ldap-list.php
 * @param LDAP\Connection|array $ldap 
 * @param array|string $base 
 * @param array|string $filter 
 * @param array $attributes [optional] 
 * @param int $attributes_only [optional] 
 * @param int $sizelimit [optional] 
 * @param int $timelimit [optional] 
 * @param int $deref [optional] 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|array|false Returns an LDAP\Result instance, an array of LDAP\Result instances, or false on failure.
 */
function ldap_list (LDAP\Connection|array $ldap, array|string $base, array|string $filter, array $attributes = '[]', int $attributes_only = null, int $sizelimit = -1, int $timelimit = -1, int $deref = LDAP_DEREF_NEVER, ?array $controls = null): LDAP\Result|array|false {}

/**
 * Search LDAP tree
 * @link http://www.php.net/manual/en/function.ldap-search.php
 * @param LDAP\Connection|array $ldap 
 * @param array|string $base 
 * @param array|string $filter 
 * @param array $attributes [optional] 
 * @param int $attributes_only [optional] 
 * @param int $sizelimit [optional] 
 * @param int $timelimit [optional] 
 * @param int $deref [optional] 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|array|false Returns an LDAP\Result instance, an array of LDAP\Result instances, or false on failure.
 */
function ldap_search (LDAP\Connection|array $ldap, array|string $base, array|string $filter, array $attributes = '[]', int $attributes_only = null, int $sizelimit = -1, int $timelimit = -1, int $deref = LDAP_DEREF_NEVER, ?array $controls = null): LDAP\Result|array|false {}

/**
 * Free result memory
 * @link http://www.php.net/manual/en/function.ldap-free-result.php
 * @param LDAP\Result $result 
 * @return bool Returns true on success or false on failure.
 */
function ldap_free_result (LDAP\Result $result): bool {}

/**
 * Count the number of entries in a search
 * @link http://www.php.net/manual/en/function.ldap-count-entries.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\Result $result 
 * @return int Returns number of entries in the result, or false on failure.
 */
function ldap_count_entries (LDAP\Connection $ldap, LDAP\Result $result): int {}

/**
 * Return first result id
 * @link http://www.php.net/manual/en/function.ldap-first-entry.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\Result $result 
 * @return LDAP\ResultEntry|false Returns an LDAP\ResultEntry instance, or false on failure.
 */
function ldap_first_entry (LDAP\Connection $ldap, LDAP\Result $result): LDAP\ResultEntry|false {}

/**
 * Get next result entry
 * @link http://www.php.net/manual/en/function.ldap-next-entry.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @return LDAP\ResultEntry|false Returns an LDAP\ResultEntry instance for the next entry in the result whose entries
 * are being read starting with ldap_first_entry. If
 * there are no more entries in the result then it returns false.
 */
function ldap_next_entry (LDAP\Connection $ldap, LDAP\ResultEntry $entry): LDAP\ResultEntry|false {}

/**
 * Get all result entries
 * @link http://www.php.net/manual/en/function.ldap-get-entries.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\Result $result 
 * @return array|false Returns a complete result information in a multi-dimensional array on
 * success, or false on failure.
 * <p>The structure of the array is as follows.
 * The attribute index is converted to lowercase. (Attributes are
 * case-insensitive for directory servers, but not when used as
 * array indices.)
 * <pre>
 * return_value["count"] = number of entries in the result
 * return_value[0] : refers to the details of first entry
 * return_value[i]["dn"] = DN of the ith entry in the result
 * return_value[i]["count"] = number of attributes in ith entry
 * return_value[i][j] = NAME of the jth attribute in the ith entry in the result
 * return_value[i]["attribute"]["count"] = number of values for
 * attribute in ith entry
 * return_value[i]["attribute"][j] = jth value of attribute in ith entry
 * </pre></p>
 */
function ldap_get_entries (LDAP\Connection $ldap, LDAP\Result $result): array|false {}

/**
 * Return first attribute
 * @link http://www.php.net/manual/en/function.ldap-first-attribute.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @return string|false Returns the first attribute in the entry on success and false on
 * error.
 */
function ldap_first_attribute (LDAP\Connection $ldap, LDAP\ResultEntry $entry): string|false {}

/**
 * Get the next attribute in result
 * @link http://www.php.net/manual/en/function.ldap-next-attribute.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @return string|false Returns the next attribute in an entry on success and false on
 * error.
 */
function ldap_next_attribute (LDAP\Connection $ldap, LDAP\ResultEntry $entry): string|false {}

/**
 * Get attributes from a search result entry
 * @link http://www.php.net/manual/en/function.ldap-get-attributes.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @return array Returns a complete entry information in a multi-dimensional array.
 */
function ldap_get_attributes (LDAP\Connection $ldap, LDAP\ResultEntry $entry): array {}

/**
 * Get all binary values from a result entry
 * @link http://www.php.net/manual/en/function.ldap-get-values-len.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @param string $attribute 
 * @return array|false Returns an array of values for the attribute on success and false on
 * error. Individual values are accessed by integer index in the array. The
 * first index is 0. The number of values can be found by indexing "count"
 * in the resultant array.
 */
function ldap_get_values_len (LDAP\Connection $ldap, LDAP\ResultEntry $entry, string $attribute): array|false {}

/**
 * Get all values from a result entry
 * @link http://www.php.net/manual/en/function.ldap-get-values.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @param string $attribute 
 * @return array|false Returns an array of values for the attribute on success and false on
 * error. The number of values can be found by indexing "count" in the
 * resultant array. Individual values are accessed by integer index in the
 * array. The first index is 0.
 * <p>LDAP allows more than one entry for an attribute, so it can, for example,
 * store a number of email addresses for one person's directory entry all
 * labeled with the attribute "mail"
 * return_value["count"] = number of values for attribute
 * return_value[0] = first value of attribute
 * return_value[i] = ith value of attribute</p>
 */
function ldap_get_values (LDAP\Connection $ldap, LDAP\ResultEntry $entry, string $attribute): array|false {}

/**
 * Get the DN of a result entry
 * @link http://www.php.net/manual/en/function.ldap-get-dn.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @return string|false Returns the DN of the result entry and false on error.
 */
function ldap_get_dn (LDAP\Connection $ldap, LDAP\ResultEntry $entry): string|false {}

/**
 * Splits DN into its component parts
 * @link http://www.php.net/manual/en/function.ldap-explode-dn.php
 * @param string $dn 
 * @param int $with_attrib 
 * @return array|false Returns an array of all DN components, or false on failure.
 * The first element in the array has count key and
 * represents the number of returned values, next elements are numerically
 * indexed DN components.
 */
function ldap_explode_dn (string $dn, int $with_attrib): array|false {}

/**
 * Convert DN to User Friendly Naming format
 * @link http://www.php.net/manual/en/function.ldap-dn2ufn.php
 * @param string $dn 
 * @return string|false Returns the user friendly name, or false on failure.
 */
function ldap_dn2ufn (string $dn): string|false {}

/**
 * Add entries to LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-add.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_add (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): bool {}

/**
 * Add entries to LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-add-ext.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_add_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): LDAP\Result|false {}

/**
 * Delete an entry from a directory
 * @link http://www.php.net/manual/en/function.ldap-delete.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_delete (LDAP\Connection $ldap, string $dn, ?array $controls = null): bool {}

/**
 * Delete an entry from a directory
 * @link http://www.php.net/manual/en/function.ldap-delete-ext.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_delete_ext (LDAP\Connection $ldap, string $dn, ?array $controls = null): LDAP\Result|false {}

/**
 * Batch and execute modifications on an LDAP entry
 * @link http://www.php.net/manual/en/function.ldap-modify-batch.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $modifications_info 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_modify_batch (LDAP\Connection $ldap, string $dn, array $modifications_info, ?array $controls = null): bool {}

/**
 * Add attribute values to current attributes
 * @link http://www.php.net/manual/en/function.ldap-mod-add.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_mod_add (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): bool {}

/**
 * Add attribute values to current attributes
 * @link http://www.php.net/manual/en/function.ldap-mod_add-ext.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_mod_add_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): LDAP\Result|false {}

/**
 * Replace attribute values with new ones
 * @link http://www.php.net/manual/en/function.ldap-mod-replace.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_mod_replace (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): bool {}

/**
 * Alias of ldap_mod_replace
 * @link http://www.php.net/manual/en/function.ldap-modify.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_modify (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): bool {}

/**
 * Replace attribute values with new ones
 * @link http://www.php.net/manual/en/function.ldap-mod_replace-ext.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_mod_replace_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): LDAP\Result|false {}

/**
 * Delete attribute values from current attributes
 * @link http://www.php.net/manual/en/function.ldap-mod-del.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_mod_del (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): bool {}

/**
 * Delete attribute values from current attributes
 * @link http://www.php.net/manual/en/function.ldap-mod_del-ext.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param array $entry 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_mod_del_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = null): LDAP\Result|false {}

/**
 * Return the LDAP error number of the last LDAP command
 * @link http://www.php.net/manual/en/function.ldap-errno.php
 * @param LDAP\Connection $ldap 
 * @return int Return the LDAP error number of the last LDAP command for this
 * link.
 */
function ldap_errno (LDAP\Connection $ldap): int {}

/**
 * Return the LDAP error message of the last LDAP command
 * @link http://www.php.net/manual/en/function.ldap-error.php
 * @param LDAP\Connection $ldap 
 * @return string Returns string error message.
 */
function ldap_error (LDAP\Connection $ldap): string {}

/**
 * Convert LDAP error number into string error message
 * @link http://www.php.net/manual/en/function.ldap-err2str.php
 * @param int $errno 
 * @return string Returns the error message, as a string.
 */
function ldap_err2str (int $errno): string {}

/**
 * Compare value of attribute found in entry specified with DN
 * @link http://www.php.net/manual/en/function.ldap-compare.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param string $attribute 
 * @param string $value 
 * @param array|null $controls [optional] 
 * @return bool|int Returns true if value matches otherwise returns
 * false. Returns -1 on error.
 */
function ldap_compare (LDAP\Connection $ldap, string $dn, string $attribute, string $value, ?array $controls = null): bool|int {}

/**
 * Modify the name of an entry
 * @link http://www.php.net/manual/en/function.ldap-rename.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param string $new_rdn 
 * @param string $new_parent 
 * @param bool $delete_old_rdn 
 * @param array|null $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_rename (LDAP\Connection $ldap, string $dn, string $new_rdn, string $new_parent, bool $delete_old_rdn, ?array $controls = null): bool {}

/**
 * Modify the name of an entry
 * @link http://www.php.net/manual/en/function.ldap-rename-ext.php
 * @param LDAP\Connection $ldap 
 * @param string $dn 
 * @param string $new_rdn 
 * @param string $new_parent 
 * @param bool $delete_old_rdn 
 * @param array|null $controls [optional] 
 * @return LDAP\Result|false Returns an LDAP\Result instance, or false on failure.
 */
function ldap_rename_ext (LDAP\Connection $ldap, string $dn, string $new_rdn, string $new_parent, bool $delete_old_rdn, ?array $controls = null): LDAP\Result|false {}

/**
 * Get the current value for given option
 * @link http://www.php.net/manual/en/function.ldap-get-option.php
 * @param LDAP\Connection $ldap 
 * @param int $option 
 * @param array|string|int $value [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_get_option (LDAP\Connection $ldap, int $option, array|string|int &$value = null): bool {}

/**
 * Set the value of the given option
 * @link http://www.php.net/manual/en/function.ldap-set-option.php
 * @param LDAP\Connection|null $ldap 
 * @param int $option 
 * @param array|string|int|bool $value 
 * @return bool Returns true on success or false on failure.
 */
function ldap_set_option (?LDAP\Connection $ldap, int $option, array|string|int|bool $value): bool {}

/**
 * Counts the number of references in a search result
 * @link http://www.php.net/manual/en/function.ldap-count-references.php
 * @param LDAP\Connection $ldap An LDAP\Connection instance, returned by ldap_connect.
 * @param LDAP\Result $result An LDAP\Result instance, returned by ldap_list or ldap_search.
 * @return int Returns the number of references in a search result.
 */
function ldap_count_references (LDAP\Connection $ldap, LDAP\Result $result): int {}

/**
 * Return first reference
 * @link http://www.php.net/manual/en/function.ldap-first-reference.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\Result $result 
 * @return LDAP\ResultEntry|false 
 */
function ldap_first_reference (LDAP\Connection $ldap, LDAP\Result $result): LDAP\ResultEntry|false {}

/**
 * Get next reference
 * @link http://www.php.net/manual/en/function.ldap-next-reference.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @return LDAP\ResultEntry|false 
 */
function ldap_next_reference (LDAP\Connection $ldap, LDAP\ResultEntry $entry): LDAP\ResultEntry|false {}

/**
 * Extract information from reference entry
 * @link http://www.php.net/manual/en/function.ldap-parse-reference.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\ResultEntry $entry 
 * @param array $referrals 
 * @return bool 
 */
function ldap_parse_reference (LDAP\Connection $ldap, LDAP\ResultEntry $entry, array &$referrals): bool {}

/**
 * Extract information from result
 * @link http://www.php.net/manual/en/function.ldap-parse-result.php
 * @param LDAP\Connection $ldap 
 * @param LDAP\Result $result 
 * @param int $error_code 
 * @param string $matched_dn [optional] 
 * @param string $error_message [optional] 
 * @param array $referrals [optional] 
 * @param array $controls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ldap_parse_result (LDAP\Connection $ldap, LDAP\Result $result, int &$error_code, string &$matched_dn = null, string &$error_message = null, array &$referrals = null, array &$controls = null): bool {}

/**
 * Set a callback function to do re-binds on referral chasing
 * @link http://www.php.net/manual/en/function.ldap-set-rebind-proc.php
 * @param LDAP\Connection $ldap 
 * @param callable|null $callback 
 * @return bool 
 */
function ldap_set_rebind_proc (LDAP\Connection $ldap, ?callable $callback): bool {}

/**
 * Start TLS
 * @link http://www.php.net/manual/en/function.ldap-start-tls.php
 * @param LDAP\Connection $ldap 
 * @return bool 
 */
function ldap_start_tls (LDAP\Connection $ldap): bool {}

/**
 * Escape a string for use in an LDAP filter or DN
 * @link http://www.php.net/manual/en/function.ldap-escape.php
 * @param string $value 
 * @param string $ignore [optional] 
 * @param int $flags [optional] 
 * @return string Returns the escaped string.
 */
function ldap_escape (string $value, string $ignore = '""', int $flags = null): string {}

/**
 * Performs an extended operation
 * @link http://www.php.net/manual/en/function.ldap-exop.php
 * @param LDAP\Connection $ldap An LDAP\Connection instance, returned by ldap_connect.
 * @param string $request_oid The extended operation request OID. You may use one of LDAP_EXOP_START_TLS, LDAP_EXOP_MODIFY_PASSWD, LDAP_EXOP_REFRESH, LDAP_EXOP_WHO_AM_I, LDAP_EXOP_TURN, or a string with the OID of the operation you want to send.
 * @param string $request_data [optional] The extended operation request data. May be NULL for some operations like LDAP_EXOP_WHO_AM_I, may also need to be BER encoded.
 * @param array $controls [optional] Array of LDAP Controls to send with the request.
 * @param string $response_data [optional] Will be filled with the extended operation response data if provided.
 * If not provided you may use ldap_parse_exop on the result object
 * later to get this data.
 * @param string $response_oid [optional] Will be filled with the response OID if provided, usually equal to the request OID.
 * @return mixed When used with response_data, returns true on success or false on error.
 * When used without response_data, returns a result identifier or false on error.
 */
function ldap_exop (LDAP\Connection $ldap, string $request_oid, string $request_data = null, array $controls = null, string &$response_data = null, string &$response_oid = null): mixed {}

/**
 * PASSWD extended operation helper
 * @link http://www.php.net/manual/en/function.ldap-exop-passwd.php
 * @param LDAP\Connection $ldap An LDAP\Connection instance, returned by ldap_connect.
 * @param string $user [optional] dn of the user to change the password of.
 * @param string $old_password [optional] The old password of this user. May be ommited depending of server configuration.
 * @param string $new_password [optional] The new password for this user. May be omitted or empty to have a generated password.
 * @param array $controls [optional] If provided, a password policy request control is send with the request and this is
 * filled with an array of LDAP Controls
 * returned with the request.
 * @return string|bool Returns the generated password if new_password is empty or omitted.
 * Otherwise returns true on success and false on failure.
 */
function ldap_exop_passwd (LDAP\Connection $ldap, string $user = '""', string $old_password = '""', string $new_password = '""', array &$controls = null): string|bool {}

/**
 * WHOAMI extended operation helper
 * @link http://www.php.net/manual/en/function.ldap-exop-whoami.php
 * @param LDAP\Connection $ldap An LDAP\Connection instance, returned by ldap_connect.
 * @return string|false The data returned by the server, or false on error.
 */
function ldap_exop_whoami (LDAP\Connection $ldap): string|false {}

/**
 * Refresh extended operation helper
 * @link http://www.php.net/manual/en/function.ldap-exop-refresh.php
 * @param LDAP\Connection $ldap An LDAP\Connection instance, returned by ldap_connect.
 * @param string $dn dn of the entry to refresh.
 * @param int $ttl Time in seconds (between 1 and 31557600) that the
 * client requests that the entry exists in the directory before being
 * automatically removed.
 * @return int|false From RFC:
 * The responseTtl field is the time in seconds which the server chooses
 * to have as the time-to-live field for that entry. It must not be any
 * smaller than that which the client requested, and it may be larger.
 * However, to allow servers to maintain a relatively accurate
 * directory, and to prevent clients from abusing the dynamic
 * extensions, servers are permitted to shorten a client-requested
 * time-to-live value, down to a minimum of 86400 seconds (one day).
 * false will be returned on error.
 */
function ldap_exop_refresh (LDAP\Connection $ldap, string $dn, int $ttl): int|false {}

/**
 * Parse result object from an LDAP extended operation
 * @link http://www.php.net/manual/en/function.ldap-parse-exop.php
 * @param LDAP\Connection $ldap An LDAP\Connection instance, returned by ldap_connect.
 * @param LDAP\Result $result An LDAP\Result instance, returned by ldap_list or ldap_search.
 * @param string $response_data [optional] Will be filled by the response data.
 * @param string $response_oid [optional] Will be filled by the response OID.
 * @return bool Returns true on success or false on failure.
 */
function ldap_parse_exop (LDAP\Connection $ldap, LDAP\Result $result, string &$response_data = null, string &$response_oid = null): bool {}


/**
 * Alias dereferencing rule - Never.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_DEREF_NEVER', 0);

/**
 * Alias dereferencing rule - Searching.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_DEREF_SEARCHING', 1);

/**
 * Alias dereferencing rule - Finding.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_DEREF_FINDING', 2);

/**
 * Alias dereferencing rule - Always.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_DEREF_ALWAYS', 3);
define ('LDAP_MODIFY_BATCH_ADD', 1);
define ('LDAP_MODIFY_BATCH_REMOVE', 2);
define ('LDAP_MODIFY_BATCH_REMOVE_ALL', 18);
define ('LDAP_MODIFY_BATCH_REPLACE', 3);
define ('LDAP_MODIFY_BATCH_ATTRIB', "attrib");
define ('LDAP_MODIFY_BATCH_MODTYPE', "modtype");
define ('LDAP_MODIFY_BATCH_VALUES', "values");

/**
 * Specifies alternative rules for following aliases at the server.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_DEREF', 2);

/**
 * Specifies the maximum number of entries that can be
 * returned on a search operation.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_SIZELIMIT', 3);

/**
 * Specifies the number of seconds to wait for search results.
 * The actual time limit for operations is also bounded
 * by the server's configured maximum time.
 * The lesser of these two settings is the actual time limit.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_TIMELIMIT', 4);

/**
 * Option for ldap_set_option to allow setting network timeout.
 * (Available as of PHP 5.3.0)
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_NETWORK_TIMEOUT', 20485);
define ('LDAP_OPT_TIMEOUT', 20482);

/**
 * Specifies the LDAP protocol to be used (V2 or V3).
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_PROTOCOL_VERSION', 17);

/**
 * Latest session error number.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_ERROR_NUMBER', 49);

/**
 * Specifies whether to automatically follow referrals returned
 * by the LDAP server.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_REFERRALS', 8);

/**
 * Determines whether or not the connection should be implicitly restarted.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_RESTART', 9);

/**
 * Sets/gets a space-separated of hosts when trying to connect.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_HOST_NAME', 48);

/**
 * Alias of LDAP_OPT_DIAGNOSTIC_MESSAGE.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_ERROR_STRING', 50);

/**
 * Sets/gets the matched DN associated with the connection.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_MATCHED_DN', 51);

/**
 * Specifies a default list of server controls to be sent with each request.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_SERVER_CONTROLS', 18);

/**
 * Specifies a default list of client controls to be processed with each request.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_CLIENT_CONTROLS', 19);

/**
 * Specifies a bitwise level for debug traces.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_DEBUG_LEVEL', 20481);

/**
 * Gets the latest session error message.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_DIAGNOSTIC_MESSAGE', 50);
define ('LDAP_OPT_X_SASL_MECH', 24832);
define ('LDAP_OPT_X_SASL_REALM', 24833);
define ('LDAP_OPT_X_SASL_AUTHCID', 24834);
define ('LDAP_OPT_X_SASL_AUTHZID', 24835);
define ('LDAP_OPT_X_SASL_NOCANON', 24843);
define ('LDAP_OPT_X_SASL_USERNAME', 24844);

/**
 * Specifies the certificate checking strategy. This must be one of: LDAP_OPT_X_TLS_NEVER,LDAP_OPT_X_TLS_HARD, LDAP_OPT_X_TLS_DEMAND,
 * LDAP_OPT_X_TLS_ALLOW, LDAP_OPT_X_TLS_TRY.
 * (Available as of PHP 7.0.0)
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_X_TLS_REQUIRE_CERT', 24582);
define ('LDAP_OPT_X_TLS_NEVER', 0);
define ('LDAP_OPT_X_TLS_HARD', 1);
define ('LDAP_OPT_X_TLS_DEMAND', 2);
define ('LDAP_OPT_X_TLS_ALLOW', 3);
define ('LDAP_OPT_X_TLS_TRY', 4);

/**
 * Specifies the path of the directory containing CA certificates.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_CACERTDIR', 24579);

/**
 * Specifies the full-path of the CA certificate file.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_CACERTFILE', 24578);

/**
 * Specifies the full-path of the certificate file.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_CERTFILE', 24580);

/**
 * Specifies the allowed cipher suite.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_CIPHER_SUITE', 24584);

/**
 * Specifies the full-path of the certificate key file.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_KEYFILE', 24581);

/**
 * Sets/gets the random file when one of the system default ones are not available.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_RANDOM_FILE', 24585);

/**
 * Specifies the CRL evaluation strategy. This must be one of: LDAP_OPT_X_TLS_CRL_NONE,LDAP_OPT_X_TLS_CRL_PEER, LDAP_OPT_X_TLS_CRL_ALL.
 * This option is only valid for OpenSSL.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_X_TLS_CRLCHECK', 24587);
define ('LDAP_OPT_X_TLS_CRL_NONE', 0);
define ('LDAP_OPT_X_TLS_CRL_PEER', 1);
define ('LDAP_OPT_X_TLS_CRL_ALL', 2);

/**
 * Specifies the full-path of the file containing the parameters for Diffie-Hellman ephemeral key exchange.
 * This option is ignored by GnuTLS and Mozilla NSS.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_DHFILE', 24590);

/**
 * Specifies the full-path of the CRL file.
 * This option is only valid for GnuTLS.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_OPT_X_TLS_CRLFILE', 24592);

/**
 * Specifies the minimum protocol version. This can be one of: LDAP_OPT_X_TLS_PROTOCOL_SSL2,LDAP_OPT_X_TLS_PROTOCOL_SSL3, LDAP_OPT_X_TLS_PROTOCOL_TLS1_0, LDAP_OPT_X_TLS_PROTOCOL_TLS1_1, LDAP_OPT_X_TLS_PROTOCOL_TLS1_2
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_X_TLS_PROTOCOL_MIN', 24583);
define ('LDAP_OPT_X_TLS_PROTOCOL_SSL2', 512);
define ('LDAP_OPT_X_TLS_PROTOCOL_SSL3', 768);
define ('LDAP_OPT_X_TLS_PROTOCOL_TLS1_0', 769);
define ('LDAP_OPT_X_TLS_PROTOCOL_TLS1_1', 770);
define ('LDAP_OPT_X_TLS_PROTOCOL_TLS1_2', 771);
define ('LDAP_OPT_X_TLS_PACKAGE', 24593);

/**
 * Specifies the number of seconds a connection needs to remain idle before TCP starts sending keepalive probes.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_X_KEEPALIVE_IDLE', 25344);

/**
 * Specifies the maximum number of keepalive probes TCP should send before dropping the connection.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_X_KEEPALIVE_PROBES', 25345);

/**
 * Specifies the interval in seconds between individual keepalive probes.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var int
 */
define ('LDAP_OPT_X_KEEPALIVE_INTERVAL', 25346);
define ('LDAP_ESCAPE_FILTER', 1);
define ('LDAP_ESCAPE_DN', 2);

/**
 * Extended Operation constant - Start TLS (RFC 4511).
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_EXOP_START_TLS', "1.3.6.1.4.1.1466.20037");

/**
 * Extended Operation constant - Modify password (RFC 3062).
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_EXOP_MODIFY_PASSWD', "1.3.6.1.4.1.4203.1.11.1");

/**
 * Extended Operation Constant - Refresh (RFC 2589).
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_EXOP_REFRESH', "1.3.6.1.4.1.1466.101.119.1");

/**
 * Extended Operation Constant - WHOAMI (RFC 4532).
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_EXOP_WHO_AM_I', "1.3.6.1.4.1.4203.1.11.3");

/**
 * Extended Operation Constant - Turn (RFC 4531).
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_EXOP_TURN', "1.3.6.1.1.19");

/**
 * Control Constant - Manage DSA IT (RFC 3296).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_MANAGEDSAIT', "2.16.840.1.113730.3.4.2");

/**
 * Control Constant - Proxied Authorization (RFC 4370).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_PROXY_AUTHZ', "2.16.840.1.113730.3.4.18");

/**
 * Control Constant - Subentries (RFC 3672).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_SUBENTRIES', "1.3.6.1.4.1.4203.1.10.1");

/**
 * Control Constant - Filter returned values (RFC 3876).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_VALUESRETURNFILTER', "1.2.826.0.1.3344810.2.3");

/**
 * Control Constant - Assertion (RFC 4528).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_ASSERT', "1.3.6.1.1.12");

/**
 * Control Constant - Pre read (RFC 4527).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_PRE_READ', "1.3.6.1.1.13.1");

/**
 * Control Constant - Post read (RFC 4527).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_POST_READ', "1.3.6.1.1.13.2");

/**
 * Control Constant - Sort request (RFC 2891).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_SORTREQUEST', "1.2.840.113556.1.4.473");

/**
 * Control Constant - Sort response (RFC 2891).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_SORTRESPONSE', "1.2.840.113556.1.4.474");

/**
 * Control Constant - Paged results (RFC 2696).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_PAGEDRESULTS', "1.2.840.113556.1.4.319");

/**
 * Control Constant - Authorization Identity Request (RFC 3829).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_AUTHZID_REQUEST', "2.16.840.1.113730.3.4.16");

/**
 * Control Constant - Authorization Identity Response (RFC 3829).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_AUTHZID_RESPONSE', "2.16.840.1.113730.3.4.15");

/**
 * Control Constant - Content Synchronization Operation (RFC 4533).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_SYNC', "1.3.6.1.4.1.4203.1.9.1.1");

/**
 * Control Constant - Content Synchronization Operation State (RFC 4533).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_SYNC_STATE', "1.3.6.1.4.1.4203.1.9.1.2");

/**
 * Control Constant - Content Synchronization Operation Done (RFC 4533).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_SYNC_DONE', "1.3.6.1.4.1.4203.1.9.1.3");

/**
 * Control Constant - Don't Use Copy (RFC 6171).
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_DONTUSECOPY', "1.3.6.1.1.22");

/**
 * Control Constant - Password Policy Request.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_PASSWORDPOLICYREQUEST', "1.3.6.1.4.1.42.2.27.8.5.1");

/**
 * Control Constant - Password Policy Response.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_PASSWORDPOLICYRESPONSE', "1.3.6.1.4.1.42.2.27.8.5.1");

/**
 * Control Constant - Active Directory Incremental Values.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_X_INCREMENTAL_VALUES', "1.2.840.113556.1.4.802");

/**
 * Control Constant - Active Directory Domain Scope.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_X_DOMAIN_SCOPE', "1.2.840.113556.1.4.1339");

/**
 * Control Constant - Active Directory Permissive Modify.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_X_PERMISSIVE_MODIFY', "1.2.840.113556.1.4.1413");

/**
 * Control Constant - Active Directory Search Options.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_X_SEARCH_OPTIONS', "1.2.840.113556.1.4.1340");

/**
 * Control Constant - Active Directory Tree Delete.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_X_TREE_DELETE', "1.2.840.113556.1.4.805");

/**
 * Control Constant - Active Directory Extended DN.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_X_EXTENDED_DN', "1.2.840.113556.1.4.529");

/**
 * Control Constant - Virtual List View Request.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_VLVREQUEST', "2.16.840.1.113730.3.4.9");

/**
 * Control Constant - Virtual List View Response.
 * Available as of PHP 7.3.0.
 * @link http://www.php.net/manual/en/ldap.constants.php
 * @var string
 */
define ('LDAP_CONTROL_VLVRESPONSE', "2.16.840.1.113730.3.4.10");


}

// End of ldap v.8.2.6
