<?php

// Start of ldap v.7.1.1

/**
 * Connect to an LDAP server
 * @link http://www.php.net/manual/en/function.ldap-connect.php
 * @param string $host [optional] <p>
 * This field supports using a hostname or, with OpenLDAP 2.x.x and
 * later, a full LDAP URI of the form ldap://hostname:port
 * or ldaps://hostname:port for SSL encryption.
 * </p>
 * <p>
 * You can also provide multiple LDAP-URIs separated by a space as one string
 * </p>
 * <p>
 * Note that hostname:port is not a supported LDAP URI as the schema is missing.
 * </p>
 * @param int $port [optional] The port to connect to. Not used when using LDAP URIs.
 * @return resource a positive LDAP link identifier when the provided hostname/port combination or LDAP URI
 * seems plausible. It's a syntactic check of the provided parameters but the server(s) will not
 * be contacted! If the syntactic check fails it returns false.
 * When OpenLDAP 2.x.x is used, ldap_connect will always
 * return a resource as it does not actually connect but just
 * initializes the connecting parameters. The actual connect happens with
 * the next calls to ldap_&#42; funcs, usually with
 * ldap_bind.
 * <p>
 * If no arguments are specified then the link identifier of the already
 * opened link will be returned.
 * </p>
 */
function ldap_connect (string $host = null, int $port = null) {}

/**
 * Alias: ldap_unbind
 * @link http://www.php.net/manual/en/function.ldap-close.php
 * @param $link_identifier
 */
function ldap_close ($link_identifier) {}

/**
 * Bind to LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-bind.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $bind_rdn [optional] 
 * @param string $bind_password [optional] 
 * @return bool true on success or false on failure
 */
function ldap_bind ($link_identifier, string $bind_rdn = null, string $bind_password = null) {}

/**
 * Bind to LDAP directory using SASL
 * @link http://www.php.net/manual/en/function.ldap-sasl-bind.php
 * @param resource $link 
 * @param string $binddn [optional] 
 * @param string $password [optional] 
 * @param string $sasl_mech [optional] 
 * @param string $sasl_realm [optional] 
 * @param string $sasl_authc_id [optional] 
 * @param string $sasl_authz_id [optional] 
 * @param string $props [optional] 
 * @return bool true on success or false on failure
 */
function ldap_sasl_bind ($link, string $binddn = null, string $password = null, string $sasl_mech = null, string $sasl_realm = null, string $sasl_authc_id = null, string $sasl_authz_id = null, string $props = null) {}

/**
 * Unbind from LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-unbind.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @return bool true on success or false on failure
 */
function ldap_unbind ($link_identifier) {}

/**
 * Read an entry
 * @link http://www.php.net/manual/en/function.ldap-read.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $base_dn The base DN for the directory.
 * @param string $filter An empty filter is not allowed. If you want to retrieve absolutely all
 * information for this entry, use a filter of 
 * objectClass=&#42;. If you know which entry types are
 * used on the directory server, you might use an appropriate filter such
 * as objectClass=inetOrgPerson.
 * @param array $attributes [optional] <p>
 * An array of the required attributes, e.g. array("mail", "sn", "cn").
 * Note that the "dn" is always returned irrespective of which attributes
 * types are requested.
 * </p>
 * <p>
 * Using this parameter is much more efficient than the default action
 * (which is to return all attributes and their associated values). 
 * The use of this parameter should therefore be considered good
 * practice.
 * </p>
 * @param int $attrsonly [optional] Should be set to 1 if only attribute types are wanted. If set to 0
 * both attributes types and attribute values are fetched which is the
 * default behaviour.
 * @param int $sizelimit [optional] <p>
 * Enables you to limit the count of entries fetched. Setting this to 0
 * means no limit.
 * </p>
 * <p>
 * This parameter can NOT override server-side preset sizelimit. You can
 * set it lower though.
 * </p>
 * <p>
 * Some directory server hosts will be configured to return no more than
 * a preset number of entries. If this occurs, the server will indicate
 * that it has only returned a partial results set. This also occurs if
 * you use this parameter to limit the count of fetched entries.
 * </p>
 * @param int $timelimit [optional] <p>
 * Sets the number of seconds how long is spend on the search. Setting
 * this to 0 means no limit.
 * </p>
 * <p>
 * This parameter can NOT override server-side preset timelimit. You can
 * set it lower though.
 * </p>
 * @param int $deref [optional] <p>
 * Specifies how aliases should be handled during the search. It can be
 * one of the following:
 * <p>
 * <br>
 * LDAP_DEREF_NEVER - (default) aliases are never
 * dereferenced.
 * <br>
 * LDAP_DEREF_SEARCHING - aliases should be
 * dereferenced during the search but not when locating the base object
 * of the search.
 * <br>
 * LDAP_DEREF_FINDING - aliases should be
 * dereferenced when locating the base object but not during the search.
 * <br>
 * LDAP_DEREF_ALWAYS - aliases should be dereferenced
 * always.
 * </p>
 * </p>
 * @return resource a search result identifier or false on error.
 */
function ldap_read ($link_identifier, string $base_dn, string $filter, array $attributes = null, int $attrsonly = null, int $sizelimit = null, int $timelimit = null, int $deref = null) {}

/**
 * Single-level search
 * @link http://www.php.net/manual/en/function.ldap-list.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $base_dn The base DN for the directory.
 * @param string $filter 
 * @param array $attributes [optional] <p>
 * An array of the required attributes, e.g. array("mail", "sn", "cn").
 * Note that the "dn" is always returned irrespective of which attributes
 * types are requested.
 * </p>
 * <p>
 * Using this parameter is much more efficient than the default action
 * (which is to return all attributes and their associated values). 
 * The use of this parameter should therefore be considered good
 * practice.
 * </p>
 * @param int $attrsonly [optional] Should be set to 1 if only attribute types are wanted. If set to 0
 * both attributes types and attribute values are fetched which is the
 * default behaviour.
 * @param int $sizelimit [optional] <p>
 * Enables you to limit the count of entries fetched. Setting this to 0
 * means no limit.
 * </p>
 * <p>
 * This parameter can NOT override server-side preset sizelimit. You can
 * set it lower though.
 * </p>
 * <p>
 * Some directory server hosts will be configured to return no more than
 * a preset number of entries. If this occurs, the server will indicate
 * that it has only returned a partial results set. This also occurs if
 * you use this parameter to limit the count of fetched entries.
 * </p>
 * @param int $timelimit [optional] <p>
 * Sets the number of seconds how long is spend on the search. Setting
 * this to 0 means no limit.
 * </p>
 * <p>
 * This parameter can NOT override server-side preset timelimit. You can
 * set it lower though.
 * </p>
 * @param int $deref [optional] <p>
 * Specifies how aliases should be handled during the search. It can be
 * one of the following:
 * <p>
 * <br>
 * LDAP_DEREF_NEVER - (default) aliases are never
 * dereferenced.
 * <br>
 * LDAP_DEREF_SEARCHING - aliases should be
 * dereferenced during the search but not when locating the base object
 * of the search.
 * <br>
 * LDAP_DEREF_FINDING - aliases should be
 * dereferenced when locating the base object but not during the search.
 * <br>
 * LDAP_DEREF_ALWAYS - aliases should be dereferenced
 * always.
 * </p>
 * </p>
 * @return resource a search result identifier or false on error.
 */
function ldap_list ($link_identifier, string $base_dn, string $filter, array $attributes = null, int $attrsonly = null, int $sizelimit = null, int $timelimit = null, int $deref = null) {}

/**
 * Search LDAP tree
 * @link http://www.php.net/manual/en/function.ldap-search.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $base_dn The base DN for the directory.
 * @param string $filter The search filter can be simple or advanced, using boolean operators in
 * the format described in the LDAP documentation (see the Netscape Directory SDK or
 * RFC4515 for full
 * information on filters).
 * @param array $attributes [optional] <p>
 * An array of the required attributes, e.g. array("mail", "sn", "cn").
 * Note that the "dn" is always returned irrespective of which attributes
 * types are requested.
 * </p>
 * <p>
 * Using this parameter is much more efficient than the default action
 * (which is to return all attributes and their associated values). 
 * The use of this parameter should therefore be considered good
 * practice.
 * </p>
 * @param int $attrsonly [optional] Should be set to 1 if only attribute types are wanted. If set to 0
 * both attributes types and attribute values are fetched which is the
 * default behaviour.
 * @param int $sizelimit [optional] <p>
 * Enables you to limit the count of entries fetched. Setting this to 0
 * means no limit.
 * </p>
 * <p>
 * This parameter can NOT override server-side preset sizelimit. You can
 * set it lower though.
 * </p>
 * <p>
 * Some directory server hosts will be configured to return no more than
 * a preset number of entries. If this occurs, the server will indicate
 * that it has only returned a partial results set. This also occurs if
 * you use this parameter to limit the count of fetched entries.
 * </p>
 * @param int $timelimit [optional] <p>
 * Sets the number of seconds how long is spend on the search. Setting
 * this to 0 means no limit.
 * </p>
 * <p>
 * This parameter can NOT override server-side preset timelimit. You can
 * set it lower though.
 * </p>
 * @param int $deref [optional] <p>
 * Specifies how aliases should be handled during the search. It can be
 * one of the following:
 * <p>
 * <br>
 * LDAP_DEREF_NEVER - (default) aliases are never
 * dereferenced.
 * <br>
 * LDAP_DEREF_SEARCHING - aliases should be
 * dereferenced during the search but not when locating the base object
 * of the search.
 * <br>
 * LDAP_DEREF_FINDING - aliases should be
 * dereferenced when locating the base object but not during the search.
 * <br>
 * LDAP_DEREF_ALWAYS - aliases should be dereferenced
 * always.
 * </p>
 * </p>
 * @return resource a search result identifier or false on error.
 */
function ldap_search ($link_identifier, string $base_dn, string $filter, array $attributes = null, int $attrsonly = null, int $sizelimit = null, int $timelimit = null, int $deref = null) {}

/**
 * Free result memory
 * @link http://www.php.net/manual/en/function.ldap-free-result.php
 * @param resource $result_identifier 
 * @return bool true on success or false on failure
 */
function ldap_free_result ($result_identifier) {}

/**
 * Count the number of entries in a search
 * @link http://www.php.net/manual/en/function.ldap-count-entries.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_identifier The internal LDAP result.
 * @return int number of entries in the result or false on error.
 */
function ldap_count_entries ($link_identifier, $result_identifier) {}

/**
 * Return first result id
 * @link http://www.php.net/manual/en/function.ldap-first-entry.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_identifier 
 * @return resource the result entry identifier for the first entry on success and
 * false on error.
 */
function ldap_first_entry ($link_identifier, $result_identifier) {}

/**
 * Get next result entry
 * @link http://www.php.net/manual/en/function.ldap-next-entry.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @return resource entry identifier for the next entry in the result whose entries
 * are being read starting with ldap_first_entry. If
 * there are no more entries in the result then it returns false.
 */
function ldap_next_entry ($link_identifier, $result_entry_identifier) {}

/**
 * Get all result entries
 * @link http://www.php.net/manual/en/function.ldap-get-entries.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_identifier 
 * @return array a complete result information in a multi-dimensional array on
 * success and false on error.
 * <p>
 * The structure of the array is as follows.
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
 * </pre>
 * </p>
 */
function ldap_get_entries ($link_identifier, $result_identifier) {}

/**
 * Return first attribute
 * @link http://www.php.net/manual/en/function.ldap-first-attribute.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @return string the first attribute in the entry on success and false on
 * error.
 */
function ldap_first_attribute ($link_identifier, $result_entry_identifier) {}

/**
 * Get the next attribute in result
 * @link http://www.php.net/manual/en/function.ldap-next-attribute.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @return string the next attribute in an entry on success and false on
 * error.
 */
function ldap_next_attribute ($link_identifier, $result_entry_identifier) {}

/**
 * Get attributes from a search result entry
 * @link http://www.php.net/manual/en/function.ldap-get-attributes.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @return array a complete entry information in a multi-dimensional array
 * on success and false on error.
 */
function ldap_get_attributes ($link_identifier, $result_entry_identifier) {}

/**
 * Get all values from a result entry
 * @link http://www.php.net/manual/en/function.ldap-get-values.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @param string $attribute 
 * @return array an array of values for the attribute on success and false on
 * error. The number of values can be found by indexing "count" in the
 * resultant array. Individual values are accessed by integer index in the
 * array. The first index is 0.
 * <p>
 * LDAP allows more than one entry for an attribute, so it can, for example,
 * store a number of email addresses for one person's directory entry all
 * labeled with the attribute "mail"
 * return_value["count"] = number of values for attribute
 * return_value[0] = first value of attribute
 * return_value[i] = ith value of attribute
 * </p>
 */
function ldap_get_values ($link_identifier, $result_entry_identifier, string $attribute) {}

/**
 * Get all binary values from a result entry
 * @link http://www.php.net/manual/en/function.ldap-get-values-len.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @param string $attribute 
 * @return array an array of values for the attribute on success and false on
 * error. Individual values are accessed by integer index in the array. The
 * first index is 0. The number of values can be found by indexing "count"
 * in the resultant array.
 */
function ldap_get_values_len ($link_identifier, $result_entry_identifier, string $attribute) {}

/**
 * Get the DN of a result entry
 * @link http://www.php.net/manual/en/function.ldap-get-dn.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param resource $result_entry_identifier 
 * @return string the DN of the result entry and false on error.
 */
function ldap_get_dn ($link_identifier, $result_entry_identifier) {}

/**
 * Splits DN into its component parts
 * @link http://www.php.net/manual/en/function.ldap-explode-dn.php
 * @param string $dn The distinguished name of an LDAP entity.
 * @param int $with_attrib Used to request if the RDNs are returned with only values or their
 * attributes as well. To get RDNs with the attributes (i.e. in
 * attribute=value format) set with_attrib to 0
 * and to get only values set it to 1.
 * @return array an array of all DN components, or false on failure.
 * The first element in the array has count key and
 * represents the number of returned values, next elements are numerically
 * indexed DN components.
 */
function ldap_explode_dn (string $dn, int $with_attrib) {}

/**
 * Convert DN to User Friendly Naming format
 * @link http://www.php.net/manual/en/function.ldap-dn2ufn.php
 * @param string $dn The distinguished name of an LDAP entity.
 * @return string the user friendly name.
 */
function ldap_dn2ufn (string $dn) {}

/**
 * Add entries to LDAP directory
 * @link http://www.php.net/manual/en/function.ldap-add.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param array $entry An array that specifies the information about the entry. The values in
 * the entries are indexed by individual attributes.
 * In case of multiple values for an attribute, they are indexed using
 * integers starting with 0.
 * <pre>
 * <code>&lt;?php
 * $entry[&quot;attribute1&quot;] = &quot;value&quot;;
 * $entry[&quot;attribute2&quot;][0] = &quot;value1&quot;;
 * $entry[&quot;attribute2&quot;][1] = &quot;value2&quot;;
 * ?&gt;</code>
 * </pre>
 * @return bool true on success or false on failure
 */
function ldap_add ($link_identifier, string $dn, array $entry) {}

/**
 * Delete an entry from a directory
 * @link http://www.php.net/manual/en/function.ldap-delete.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @return bool true on success or false on failure
 */
function ldap_delete ($link_identifier, string $dn) {}

/**
 * Batch and execute modifications on an LDAP entry
 * @link http://www.php.net/manual/en/function.ldap-modify-batch.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param array $entry <p>
 * An array that specifies the modifications to make. Each entry in this
 * array is an associative array with two or three keys:
 * attrib maps to the name of the attribute to modify,
 * modtype maps to the type of modification to perform,
 * and (depending on the type of modification) values
 * maps to an array of attribute values relevant to the modification.
 * </p>
 * <p>
 * Possible values for modtype include:
 * <p>
 * LDAP_MODIFY_BATCH_ADD
 * <br>
 * <p>
 * Each value specified through values is added (as
 * an additional value) to the attribute named by
 * attrib.
 * </p>
 * LDAP_MODIFY_BATCH_REMOVE
 * <br>
 * <p>
 * Each value specified through values is removed
 * from the attribute named by attrib. Any value of
 * the attribute not contained in the values array
 * will remain untouched.
 * </p>
 * LDAP_MODIFY_BATCH_REMOVE_ALL
 * <br>
 * <p>
 * All values are removed from the attribute named by
 * attrib. A values entry must
 * not be provided.
 * </p>
 * LDAP_MODIFY_BATCH_REPLACE
 * <br>
 * <p>
 * All current values of the attribute named by
 * attrib are replaced with the values specified
 * through values.
 * </p>
 * </p>
 * </p>
 * <p>
 * Note that any value for attrib must be a string, any
 * value for values must be an array of strings, and
 * any value for modtype must be one of the
 * LDAP_MODIFY_BATCH_&#42; constants listed above.
 * </p>
 * @return bool true on success or false on failure
 */
function ldap_modify_batch ($link_identifier, string $dn, array $entry) {}

/**
 * Alias: ldap_mod_replace
 * @link http://www.php.net/manual/en/function.ldap-modify.php
 * @param $link_identifier
 * @param $dn
 * @param $entry
 */
function ldap_modify ($link_identifier, $dn, $entry) {}

/**
 * Add attribute values to current attributes
 * @link http://www.php.net/manual/en/function.ldap-mod-add.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param array $entry An associative array listing the attirbute values to add. If an attribute was not existing yet it will be added. If an attribute is existing you can only add values to it if it supports multiple values.
 * @return bool true on success or false on failure
 */
function ldap_mod_add ($link_identifier, string $dn, array $entry) {}

/**
 * Replace attribute values with new ones
 * @link http://www.php.net/manual/en/function.ldap-mod-replace.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param array $entry An associative array listing the attributes to replace. Sending an empty array as value will remove the attribute, while sending an attribute not existing yet on this entry will add it.
 * @return bool true on success or false on failure
 */
function ldap_mod_replace ($link_identifier, string $dn, array $entry) {}

/**
 * Delete attribute values from current attributes
 * @link http://www.php.net/manual/en/function.ldap-mod-del.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param array $entry 
 * @return bool true on success or false on failure
 */
function ldap_mod_del ($link_identifier, string $dn, array $entry) {}

/**
 * Return the LDAP error number of the last LDAP command
 * @link http://www.php.net/manual/en/function.ldap-errno.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @return int Return the LDAP error number of the last LDAP command for this
 * link.
 */
function ldap_errno ($link_identifier) {}

/**
 * Convert LDAP error number into string error message
 * @link http://www.php.net/manual/en/function.ldap-err2str.php
 * @param int $errno The error number.
 * @return string the error message, as a string.
 */
function ldap_err2str (int $errno) {}

/**
 * Return the LDAP error message of the last LDAP command
 * @link http://www.php.net/manual/en/function.ldap-error.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @return string string error message.
 */
function ldap_error ($link_identifier) {}

/**
 * Compare value of attribute found in entry specified with DN
 * @link http://www.php.net/manual/en/function.ldap-compare.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param string $attribute The attribute name.
 * @param string $value The compared value.
 * @return mixed true if value matches otherwise returns
 * false. Returns -1 on error.
 */
function ldap_compare ($link_identifier, string $dn, string $attribute, string $value) {}

/**
 * Sort LDAP result entries on the client side
 * @link http://www.php.net/manual/en/function.ldap-sort.php
 * @param resource $link An LDAP link identifier, returned by ldap_connect.
 * @param resource $result An search result identifier, returned by
 * ldap_search.
 * @param string $sortfilter The attribute to use as a key in the sort.
 * @return bool 
 * @deprecated 
 */
function ldap_sort ($link, $result, string $sortfilter) {}

/**
 * Modify the name of an entry
 * @link http://www.php.net/manual/en/function.ldap-rename.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param string $dn The distinguished name of an LDAP entity.
 * @param string $newrdn The new RDN.
 * @param string $newparent The new parent/superior entry.
 * @param bool $deleteoldrdn If true the old RDN value(s) is removed, else the old RDN value(s)
 * is retained as non-distinguished values of the entry.
 * @return bool true on success or false on failure
 */
function ldap_rename ($link_identifier, string $dn, string $newrdn, string $newparent, bool $deleteoldrdn) {}

/**
 * Get the current value for given option
 * @link http://www.php.net/manual/en/function.ldap-get-option.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param int $option The parameter option can be one of:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Type</td>
 * <td>since</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_DEREF</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_SIZELIMIT</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_TIMELIMIT</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_NETWORK_TIMEOUT</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_PROTOCOL_VERSION</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_ERROR_NUMBER</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_REFERRALS</td>
 * <td>bool</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_RESTART</td>
 * <td>bool</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_HOST_NAME</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_ERROR_STRING</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_MATCHED_DN</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_SERVER_CONTROLS</td>
 * <td>array</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_CLIENT_CONTROLS</td>
 * <td>array</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_KEEPALIVE_IDLE</td>
 * <td>int</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_KEEPALIVE_PROBES</td>
 * <td>int</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_KEEPALIVE_INTERVAL</td>
 * <td>int</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CACERTDIR</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CACERTFILE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CERTFILE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CIPHER_SUITE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRLCHECK</td>
 * <td>integer</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRL_NONE</td>
 * <td>integer</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRL_PEER</td>
 * <td>integer</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRL_ALL</td>
 * <td>integer</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRLFILE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_DHFILE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_KEYILE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_PACKAGE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_PROTOCOL_MIN</td>
 * <td>integer</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_RANDOM_FILE</td>
 * <td>string</td>
 * <td>7.1</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_REQUIRE_CERT</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * </table>
 * @param mixed $retval This will be set to the option value.
 * @return bool true on success or false on failure
 */
function ldap_get_option ($link_identifier, int $option, &$retval) {}

/**
 * Set the value of the given option
 * @link http://www.php.net/manual/en/function.ldap-set-option.php
 * @param resource $link_identifier An LDAP link identifier, returned by ldap_connect.
 * @param int $option <p>
 * The parameter option can be one of:
 * <table>
 * <tr valign="top">
 * <td>Option</td>
 * <td>Type</td>
 * <td>Available since</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_DEREF</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_SIZELIMIT</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_TIMELIMIT</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_NETWORK_TIMEOUT</td>
 * <td>integer</td>
 * <td>PHP 5.3.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_PROTOCOL_VERSION</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_ERROR_NUMBER</td>
 * <td>integer</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_REFERRALS</td>
 * <td>bool</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_RESTART</td>
 * <td>bool</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_HOST_NAME</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_ERROR_STRING</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_DIAGNOSTIC_MESSAGE</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_MATCHED_DN</td>
 * <td>string</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_SERVER_CONTROLS</td>
 * <td>array</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_CLIENT_CONTROLS</td>
 * <td>array</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_KEEPALIVE_IDLE</td>
 * <td>int</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_KEEPALIVE_PROBES</td>
 * <td>int</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_KEEPALIVE_INTERVAL</td>
 * <td>int</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CACERTDIR</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CACERTFILE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CERTFILE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CIPHER_SUITE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRLCHECK</td>
 * <td>integer</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_CRLFILE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_DHFILE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_KEYFILE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_PROTOCOL_MIN</td>
 * <td>integer</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_RANDOM_FILE</td>
 * <td>string</td>
 * <td>PHP 7.1.0</td>
 * </tr>
 * <tr valign="top">
 * <td>LDAP_OPT_X_TLS_REQUIRE_CERT</td>
 * <td>integer</td>
 * <td>PHP 7.0.5</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * LDAP_OPT_SERVER_CONTROLS and 
 * <p>oid identifying the control,
 * an optional value, and an optional flag for
 * criticality. In PHP a control is given by an
 * array containing an element with the key oid
 * and string value, and two optional elements. The optional
 * elements are key value with string value
 * and key iscritical with boolean value.
 * iscritical defaults to false
 * if not supplied. See draft-ietf-ldapext-ldap-c-api-xx.txt
 * for details. See also the second example below.
 * </p>
 * @param mixed $newval The new value for the specified option.
 * @return bool true on success or false on failure
 */
function ldap_set_option ($link_identifier, int $option, $newval) {}

/**
 * Return first reference
 * @link http://www.php.net/manual/en/function.ldap-first-reference.php
 * @param resource $link 
 * @param resource $result 
 * @return resource 
 */
function ldap_first_reference ($link, $result) {}

/**
 * Get next reference
 * @link http://www.php.net/manual/en/function.ldap-next-reference.php
 * @param resource $link 
 * @param resource $entry 
 * @return resource 
 */
function ldap_next_reference ($link, $entry) {}

/**
 * Extract information from reference entry
 * @link http://www.php.net/manual/en/function.ldap-parse-reference.php
 * @param resource $link 
 * @param resource $entry 
 * @param array $referrals 
 * @return bool 
 */
function ldap_parse_reference ($link, $entry, array &$referrals) {}

/**
 * Extract information from result
 * @link http://www.php.net/manual/en/function.ldap-parse-result.php
 * @param resource $link An LDAP link identifier, returned by ldap_connect.
 * @param resource $result 
 * @param int $errcode A reference to a variable that will be set to the LDAP error code in
 * the result, or 0 if no error occurred.
 * @param string $matcheddn [optional] A reference to a variable that will be set to a matched DN if one was
 * recognised within the request, otherwise it will be set to null.
 * @param string $errmsg [optional] A reference to a variable that will be set to the LDAP error message in
 * the result, or an empty string if no error occurred.
 * @param array $referrals [optional] A reference to a variable that will be set to an array set
 * to all of the referral strings in the result, or an empty array if no
 * referrals were returned.
 * @return bool true on success or false on failure
 */
function ldap_parse_result ($link, $result, int &$errcode, string &$matcheddn = null, string &$errmsg = null, array &$referrals = null) {}

/**
 * Start TLS
 * @link http://www.php.net/manual/en/function.ldap-start-tls.php
 * @param resource $link 
 * @return bool 
 */
function ldap_start_tls ($link) {}

/**
 * Set a callback function to do re-binds on referral chasing
 * @link http://www.php.net/manual/en/function.ldap-set-rebind-proc.php
 * @param resource $link 
 * @param callable $callback 
 * @return bool 
 */
function ldap_set_rebind_proc ($link, callable $callback) {}

/**
 * Escape a string for use in an LDAP filter or DN
 * @link http://www.php.net/manual/en/function.ldap-escape.php
 * @param string $value The value to escape.
 * @param string $ignore [optional] Characters to ignore when escaping.
 * @param int $flags [optional] The context the escaped string will be used in:
 * LDAP_ESCAPE_FILTER for filters to be used with
 * ldap_search, or
 * LDAP_ESCAPE_DN for DNs.
 * @return string the escaped string.
 */
function ldap_escape (string $value, string $ignore = null, int $flags = null) {}

/**
 * Send LDAP pagination control
 * @link http://www.php.net/manual/en/function.ldap-control-paged-result.php
 * @param resource $link An LDAP link identifier, returned by ldap_connect.
 * @param int $pagesize The number of entries by page.
 * @param bool $iscritical [optional] Indicates whether the pagination is critical or not. 
 * If true and if the server doesn't support pagination, the search
 * will return no result.
 * @param string $cookie [optional] An opaque structure sent by the server 
 * (ldap_control_paged_result_response).
 * @return bool true on success or false on failure
 */
function ldap_control_paged_result ($link, int $pagesize, bool $iscritical = null, string $cookie = null) {}

/**
 * Retrieve the LDAP pagination cookie
 * @link http://www.php.net/manual/en/function.ldap-control-paged-result-response.php
 * @param resource $link An LDAP link identifier, returned by ldap_connect.
 * @param resource $result 
 * @param string $cookie [optional] An opaque structure sent by the server.
 * @param int $estimated [optional] The estimated number of entries to retrieve.
 * @return bool true on success or false on failure
 */
function ldap_control_paged_result_response ($link, $result, string &$cookie = null, int &$estimated = null) {}


/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_DEREF_NEVER', 0);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_DEREF_SEARCHING', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_DEREF_FINDING', 2);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
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
 */
define ('LDAP_OPT_DEREF', 2);

/**
 * <p>
 * Specifies the maximum number of entries that can be
 * returned on a search operation.
 * </p>
 * The actual size limit for operations is also bounded
 * by the server's configured maximum number of return entries.
 * The lesser of these two settings is the actual size limit.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_SIZELIMIT', 3);

/**
 * Specifies the number of seconds to wait for search results.
 * The actual time limit for operations is also bounded
 * by the server's configured maximum time.
 * The lesser of these two settings is the actual time limit.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_TIMELIMIT', 4);

/**
 * Option for ldap_set_option to allow setting network timeout.
 * (Available as of PHP 5.3.0)
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_NETWORK_TIMEOUT', 20485);
define ('LDAP_OPT_TIMEOUT', 20482);

/**
 * Specifies the LDAP protocol to be used (V2 or V3).
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_PROTOCOL_VERSION', 17);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_ERROR_NUMBER', 49);

/**
 * Specifies whether to automatically follow referrals returned
 * by the LDAP server.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_REFERRALS', 8);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_RESTART', 9);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_HOST_NAME', 48);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_ERROR_STRING', 50);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_MATCHED_DN', 51);

/**
 * Specifies a default list of server controls to be sent with each request.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_SERVER_CONTROLS', 18);

/**
 * Specifies a default list of client controls to be processed with each request.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_CLIENT_CONTROLS', 19);

/**
 * Specifies a bitwise level for debug traces.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_DEBUG_LEVEL', 20481);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_DIAGNOSTIC_MESSAGE', 50);
define ('LDAP_OPT_X_SASL_MECH', 24832);
define ('LDAP_OPT_X_SASL_REALM', 24833);
define ('LDAP_OPT_X_SASL_AUTHCID', 24834);
define ('LDAP_OPT_X_SASL_AUTHZID', 24835);
define ('LDAP_OPT_X_SASL_NOCANON', 24843);
define ('LDAP_OPT_X_SASL_USERNAME', 24844);

/**
 * Specifies the certificate checking checking strategy. This must be one of: LDAP_OPT_X_TLS_NEVER,LDAP_OPT_X_TLS_HARD, LDAP_OPT_X_TLS_DEMAND,
 * LDAP_OPT_X_TLS_ALLOW, LDAP_OPT_X_TLS_TRY.
 * (Available as of PHP 7.0.0)
 * @link http://www.php.net/manual/en/ldap.constants.php
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
 */
define ('LDAP_OPT_X_TLS_CACERTDIR', 24579);

/**
 * Specifies the full-path of the CA certificate file.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_CACERTFILE', 24578);

/**
 * Specifies the full-path of the certificate file.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_CERTFILE', 24580);

/**
 * Specifies the allowed cipher suite.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_CIPHER_SUITE', 24584);

/**
 * Specifies the full-path of the certificate key file.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_KEYFILE', 24581);

/**
 * 
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_RANDOM_FILE', 24585);

/**
 * Specifies the CRL evaluation strategy. This must be one of: LDAP_OPT_X_TLS_CRL_NONE,LDAP_OPT_X_TLS_CRL_PEER, LDAP_OPT_X_TLS_CRL_ALL.
 * This option is only valid for OpenSSL.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_CRLCHECK', 24587);
define ('LDAP_OPT_X_TLS_CRL_NONE', 0);
define ('LDAP_OPT_X_TLS_CRL_PEER', 1);
define ('LDAP_OPT_X_TLS_CRL_ALL', 2);

/**
 * Specifies the full-path of the file containing the parameters for Diffie-Hellman ephemeral key exchange.
 * This option is ignored by GnuTLS and Mozilla NSS.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_DHFILE', 24590);

/**
 * Specifies the full-path of the CRL file.
 * This option is only valid for GnuTLS.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_TLS_CRLFILE', 24592);

/**
 * Specifies the minimum protocol version. This can be one of: LDAP_OPT_X_TLS_PROTOCOL_SSL2,LDAP_OPT_X_TLS_PROTOCOL_SSL3, LDAP_OPT_X_TLS_PROTOCOL_TLS1_0, LDAP_OPT_X_TLS_PROTOCOL_TLS1_1, LDAP_OPT_X_TLS_PROTOCOL_TLS1_2
 * @link http://www.php.net/manual/en/ldap.constants.php
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
 */
define ('LDAP_OPT_X_KEEPALIVE_IDLE', 25344);

/**
 * Specifies the maximum number of keepalive probes TCP should send before dropping the connection.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_KEEPALIVE_PROBES', 25345);

/**
 * Specifies the interval in seconds between individual keepalive probes.
 * @link http://www.php.net/manual/en/ldap.constants.php
 */
define ('LDAP_OPT_X_KEEPALIVE_INTERVAL', 25346);
define ('LDAP_ESCAPE_FILTER', 1);
define ('LDAP_ESCAPE_DN', 2);

// End of ldap v.7.1.1
