<?php

// Start of ldap v.8.3.0

namespace LDAP {

final class Connection  {
}

final class Result  {
}

final class ResultEntry  {
}


}


namespace {

/**
 * {@inheritdoc}
 * @param string|null $uri [optional]
 * @param int $port [optional]
 */
function ldap_connect (?string $uri = NULL, int $port = 389): LDAP\Connection|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 */
function ldap_unbind (LDAP\Connection $ldap): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 */
function ldap_close (LDAP\Connection $ldap): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string|null $dn [optional]
 * @param string|null $password [optional]
 */
function ldap_bind (LDAP\Connection $ldap, ?string $dn = NULL, ?string $password = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string|null $dn [optional]
 * @param string|null $password [optional]
 * @param array|null $controls [optional]
 */
function ldap_bind_ext (LDAP\Connection $ldap, ?string $dn = NULL, ?string $password = NULL, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string|null $dn [optional]
 * @param string|null $password [optional]
 * @param string|null $mech [optional]
 * @param string|null $realm [optional]
 * @param string|null $authc_id [optional]
 * @param string|null $authz_id [optional]
 * @param string|null $props [optional]
 */
function ldap_sasl_bind (LDAP\Connection $ldap, ?string $dn = NULL, ?string $password = NULL, ?string $mech = NULL, ?string $realm = NULL, ?string $authc_id = NULL, ?string $authz_id = NULL, ?string $props = NULL): bool {}

/**
 * {@inheritdoc}
 * @param mixed $ldap
 * @param array|string $base
 * @param array|string $filter
 * @param array $attributes [optional]
 * @param int $attributes_only [optional]
 * @param int $sizelimit [optional]
 * @param int $timelimit [optional]
 * @param int $deref [optional]
 * @param array|null $controls [optional]
 */
function ldap_read ($ldap = null, array|string $base, array|string $filter, array $attributes = array (
), int $attributes_only = 0, int $sizelimit = -1, int $timelimit = -1, int $deref = 0, ?array $controls = NULL): LDAP\Result|array|false {}

/**
 * {@inheritdoc}
 * @param mixed $ldap
 * @param array|string $base
 * @param array|string $filter
 * @param array $attributes [optional]
 * @param int $attributes_only [optional]
 * @param int $sizelimit [optional]
 * @param int $timelimit [optional]
 * @param int $deref [optional]
 * @param array|null $controls [optional]
 */
function ldap_list ($ldap = null, array|string $base, array|string $filter, array $attributes = array (
), int $attributes_only = 0, int $sizelimit = -1, int $timelimit = -1, int $deref = 0, ?array $controls = NULL): LDAP\Result|array|false {}

/**
 * {@inheritdoc}
 * @param mixed $ldap
 * @param array|string $base
 * @param array|string $filter
 * @param array $attributes [optional]
 * @param int $attributes_only [optional]
 * @param int $sizelimit [optional]
 * @param int $timelimit [optional]
 * @param int $deref [optional]
 * @param array|null $controls [optional]
 */
function ldap_search ($ldap = null, array|string $base, array|string $filter, array $attributes = array (
), int $attributes_only = 0, int $sizelimit = -1, int $timelimit = -1, int $deref = 0, ?array $controls = NULL): LDAP\Result|array|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Result $result
 */
function ldap_free_result (LDAP\Result $result): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 */
function ldap_count_entries (LDAP\Connection $ldap, LDAP\Result $result): int {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 */
function ldap_first_entry (LDAP\Connection $ldap, LDAP\Result $result): LDAP\ResultEntry|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 */
function ldap_next_entry (LDAP\Connection $ldap, LDAP\ResultEntry $entry): LDAP\ResultEntry|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 */
function ldap_get_entries (LDAP\Connection $ldap, LDAP\Result $result): array|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 */
function ldap_first_attribute (LDAP\Connection $ldap, LDAP\ResultEntry $entry): string|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 */
function ldap_next_attribute (LDAP\Connection $ldap, LDAP\ResultEntry $entry): string|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 */
function ldap_get_attributes (LDAP\Connection $ldap, LDAP\ResultEntry $entry): array {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 * @param string $attribute
 */
function ldap_get_values_len (LDAP\Connection $ldap, LDAP\ResultEntry $entry, string $attribute): array|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 * @param string $attribute
 */
function ldap_get_values (LDAP\Connection $ldap, LDAP\ResultEntry $entry, string $attribute): array|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 */
function ldap_get_dn (LDAP\Connection $ldap, LDAP\ResultEntry $entry): string|false {}

/**
 * {@inheritdoc}
 * @param string $dn
 * @param int $with_attrib
 */
function ldap_explode_dn (string $dn, int $with_attrib): array|false {}

/**
 * {@inheritdoc}
 * @param string $dn
 */
function ldap_dn2ufn (string $dn): string|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_add (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_add_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array|null $controls [optional]
 */
function ldap_delete (LDAP\Connection $ldap, string $dn, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array|null $controls [optional]
 */
function ldap_delete_ext (LDAP\Connection $ldap, string $dn, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $modifications_info
 * @param array|null $controls [optional]
 */
function ldap_modify_batch (LDAP\Connection $ldap, string $dn, array $modifications_info, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_mod_add (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_mod_add_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_mod_replace (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_modify (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_mod_replace_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_mod_del (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param array $entry
 * @param array|null $controls [optional]
 */
function ldap_mod_del_ext (LDAP\Connection $ldap, string $dn, array $entry, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 */
function ldap_errno (LDAP\Connection $ldap): int {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 */
function ldap_error (LDAP\Connection $ldap): string {}

/**
 * {@inheritdoc}
 * @param int $errno
 */
function ldap_err2str (int $errno): string {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param string $attribute
 * @param string $value
 * @param array|null $controls [optional]
 */
function ldap_compare (LDAP\Connection $ldap, string $dn, string $attribute, string $value, ?array $controls = NULL): int|bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param string $new_rdn
 * @param string $new_parent
 * @param bool $delete_old_rdn
 * @param array|null $controls [optional]
 */
function ldap_rename (LDAP\Connection $ldap, string $dn, string $new_rdn, string $new_parent, bool $delete_old_rdn, ?array $controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param string $new_rdn
 * @param string $new_parent
 * @param bool $delete_old_rdn
 * @param array|null $controls [optional]
 */
function ldap_rename_ext (LDAP\Connection $ldap, string $dn, string $new_rdn, string $new_parent, bool $delete_old_rdn, ?array $controls = NULL): LDAP\Result|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param int $option
 * @param mixed $value [optional]
 */
function ldap_get_option (LDAP\Connection $ldap, int $option, &$value = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection|null $ldap
 * @param int $option
 * @param mixed $value
 */
function ldap_set_option (?LDAP\Connection $ldap = null, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 */
function ldap_count_references (LDAP\Connection $ldap, LDAP\Result $result): int {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 */
function ldap_first_reference (LDAP\Connection $ldap, LDAP\Result $result): LDAP\ResultEntry|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 */
function ldap_next_reference (LDAP\Connection $ldap, LDAP\ResultEntry $entry): LDAP\ResultEntry|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\ResultEntry $entry
 * @param mixed $referrals
 */
function ldap_parse_reference (LDAP\Connection $ldap, LDAP\ResultEntry $entry, &$referrals = null): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 * @param mixed $error_code
 * @param mixed $matched_dn [optional]
 * @param mixed $error_message [optional]
 * @param mixed $referrals [optional]
 * @param mixed $controls [optional]
 */
function ldap_parse_result (LDAP\Connection $ldap, LDAP\Result $result, &$error_code = null, &$matched_dn = NULL, &$error_message = NULL, &$referrals = NULL, &$controls = NULL): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param callable|null $callback
 */
function ldap_set_rebind_proc (LDAP\Connection $ldap, ?callable $callback = null): bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 */
function ldap_start_tls (LDAP\Connection $ldap): bool {}

/**
 * {@inheritdoc}
 * @param string $value
 * @param string $ignore [optional]
 * @param int $flags [optional]
 */
function ldap_escape (string $value, string $ignore = '', int $flags = 0): string {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $request_oid
 * @param string|null $request_data [optional]
 * @param array|null $controls [optional]
 * @param mixed $response_data [optional]
 * @param mixed $response_oid [optional]
 */
function ldap_exop (LDAP\Connection $ldap, string $request_oid, ?string $request_data = NULL, ?array $controls = NULL, &$response_data = NULL, &$response_oid = NULL): LDAP\Result|bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $request_oid
 * @param string|null $request_data [optional]
 * @param array|null $controls [optional]
 * @param mixed $response_data [optional]
 * @param mixed $response_oid [optional]
 */
function ldap_exop_sync (LDAP\Connection $ldap, string $request_oid, ?string $request_data = NULL, ?array $controls = NULL, &$response_data = NULL, &$response_oid = NULL): LDAP\Result|bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $user [optional]
 * @param string $old_password [optional]
 * @param string $new_password [optional]
 * @param mixed $controls [optional]
 */
function ldap_exop_passwd (LDAP\Connection $ldap, string $user = '', string $old_password = '', string $new_password = '', &$controls = NULL): string|bool {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 */
function ldap_exop_whoami (LDAP\Connection $ldap): string|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param string $dn
 * @param int $ttl
 */
function ldap_exop_refresh (LDAP\Connection $ldap, string $dn, int $ttl): int|false {}

/**
 * {@inheritdoc}
 * @param LDAP\Connection $ldap
 * @param LDAP\Result $result
 * @param mixed $response_data [optional]
 * @param mixed $response_oid [optional]
 */
function ldap_parse_exop (LDAP\Connection $ldap, LDAP\Result $result, &$response_data = NULL, &$response_oid = NULL): bool {}

define ('LDAP_DEREF_NEVER', 0);
define ('LDAP_DEREF_SEARCHING', 1);
define ('LDAP_DEREF_FINDING', 2);
define ('LDAP_DEREF_ALWAYS', 3);
define ('LDAP_MODIFY_BATCH_ADD', 1);
define ('LDAP_MODIFY_BATCH_REMOVE', 2);
define ('LDAP_MODIFY_BATCH_REMOVE_ALL', 18);
define ('LDAP_MODIFY_BATCH_REPLACE', 3);
define ('LDAP_MODIFY_BATCH_ATTRIB', "attrib");
define ('LDAP_MODIFY_BATCH_MODTYPE', "modtype");
define ('LDAP_MODIFY_BATCH_VALUES', "values");
define ('LDAP_OPT_DEREF', 2);
define ('LDAP_OPT_SIZELIMIT', 3);
define ('LDAP_OPT_TIMELIMIT', 4);
define ('LDAP_OPT_NETWORK_TIMEOUT', 20485);
define ('LDAP_OPT_TIMEOUT', 20482);
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
define ('LDAP_OPT_DIAGNOSTIC_MESSAGE', 50);
define ('LDAP_OPT_X_SASL_MECH', 24832);
define ('LDAP_OPT_X_SASL_REALM', 24833);
define ('LDAP_OPT_X_SASL_AUTHCID', 24834);
define ('LDAP_OPT_X_SASL_AUTHZID', 24835);
define ('LDAP_OPT_X_SASL_NOCANON', 24843);
define ('LDAP_OPT_X_SASL_USERNAME', 24844);
define ('LDAP_OPT_X_TLS_REQUIRE_CERT', 24582);
define ('LDAP_OPT_X_TLS_NEVER', 0);
define ('LDAP_OPT_X_TLS_HARD', 1);
define ('LDAP_OPT_X_TLS_DEMAND', 2);
define ('LDAP_OPT_X_TLS_ALLOW', 3);
define ('LDAP_OPT_X_TLS_TRY', 4);
define ('LDAP_OPT_X_TLS_CACERTDIR', 24579);
define ('LDAP_OPT_X_TLS_CACERTFILE', 24578);
define ('LDAP_OPT_X_TLS_CERTFILE', 24580);
define ('LDAP_OPT_X_TLS_CIPHER_SUITE', 24584);
define ('LDAP_OPT_X_TLS_KEYFILE', 24581);
define ('LDAP_OPT_X_TLS_RANDOM_FILE', 24585);
define ('LDAP_OPT_X_TLS_CRLCHECK', 24587);
define ('LDAP_OPT_X_TLS_CRL_NONE', 0);
define ('LDAP_OPT_X_TLS_CRL_PEER', 1);
define ('LDAP_OPT_X_TLS_CRL_ALL', 2);
define ('LDAP_OPT_X_TLS_DHFILE', 24590);
define ('LDAP_OPT_X_TLS_CRLFILE', 24592);
define ('LDAP_OPT_X_TLS_PROTOCOL_MIN', 24583);
define ('LDAP_OPT_X_TLS_PROTOCOL_SSL2', 512);
define ('LDAP_OPT_X_TLS_PROTOCOL_SSL3', 768);
define ('LDAP_OPT_X_TLS_PROTOCOL_TLS1_0', 769);
define ('LDAP_OPT_X_TLS_PROTOCOL_TLS1_1', 770);
define ('LDAP_OPT_X_TLS_PROTOCOL_TLS1_2', 771);
define ('LDAP_OPT_X_TLS_PACKAGE', 24593);
define ('LDAP_OPT_X_KEEPALIVE_IDLE', 25344);
define ('LDAP_OPT_X_KEEPALIVE_PROBES', 25345);
define ('LDAP_OPT_X_KEEPALIVE_INTERVAL', 25346);
define ('LDAP_ESCAPE_FILTER', 1);
define ('LDAP_ESCAPE_DN', 2);
define ('LDAP_EXOP_START_TLS', "1.3.6.1.4.1.1466.20037");
define ('LDAP_EXOP_MODIFY_PASSWD', "1.3.6.1.4.1.4203.1.11.1");
define ('LDAP_EXOP_REFRESH', "1.3.6.1.4.1.1466.101.119.1");
define ('LDAP_EXOP_WHO_AM_I', "1.3.6.1.4.1.4203.1.11.3");
define ('LDAP_EXOP_TURN', "1.3.6.1.1.19");
define ('LDAP_CONTROL_MANAGEDSAIT', "2.16.840.1.113730.3.4.2");
define ('LDAP_CONTROL_PROXY_AUTHZ', "2.16.840.1.113730.3.4.18");
define ('LDAP_CONTROL_SUBENTRIES', "1.3.6.1.4.1.4203.1.10.1");
define ('LDAP_CONTROL_VALUESRETURNFILTER', "1.2.826.0.1.3344810.2.3");
define ('LDAP_CONTROL_ASSERT', "1.3.6.1.1.12");
define ('LDAP_CONTROL_PRE_READ', "1.3.6.1.1.13.1");
define ('LDAP_CONTROL_POST_READ', "1.3.6.1.1.13.2");
define ('LDAP_CONTROL_SORTREQUEST', "1.2.840.113556.1.4.473");
define ('LDAP_CONTROL_SORTRESPONSE', "1.2.840.113556.1.4.474");
define ('LDAP_CONTROL_PAGEDRESULTS', "1.2.840.113556.1.4.319");
define ('LDAP_CONTROL_AUTHZID_REQUEST', "2.16.840.1.113730.3.4.16");
define ('LDAP_CONTROL_AUTHZID_RESPONSE', "2.16.840.1.113730.3.4.15");
define ('LDAP_CONTROL_SYNC', "1.3.6.1.4.1.4203.1.9.1.1");
define ('LDAP_CONTROL_SYNC_STATE', "1.3.6.1.4.1.4203.1.9.1.2");
define ('LDAP_CONTROL_SYNC_DONE', "1.3.6.1.4.1.4203.1.9.1.3");
define ('LDAP_CONTROL_DONTUSECOPY', "1.3.6.1.1.22");
define ('LDAP_CONTROL_PASSWORDPOLICYREQUEST', "1.3.6.1.4.1.42.2.27.8.5.1");
define ('LDAP_CONTROL_PASSWORDPOLICYRESPONSE', "1.3.6.1.4.1.42.2.27.8.5.1");
define ('LDAP_CONTROL_X_INCREMENTAL_VALUES', "1.2.840.113556.1.4.802");
define ('LDAP_CONTROL_X_DOMAIN_SCOPE', "1.2.840.113556.1.4.1339");
define ('LDAP_CONTROL_X_PERMISSIVE_MODIFY', "1.2.840.113556.1.4.1413");
define ('LDAP_CONTROL_X_SEARCH_OPTIONS', "1.2.840.113556.1.4.1340");
define ('LDAP_CONTROL_X_TREE_DELETE', "1.2.840.113556.1.4.805");
define ('LDAP_CONTROL_X_EXTENDED_DN', "1.2.840.113556.1.4.529");
define ('LDAP_CONTROL_VLVREQUEST', "2.16.840.1.113730.3.4.9");
define ('LDAP_CONTROL_VLVRESPONSE', "2.16.840.1.113730.3.4.10");


}

// End of ldap v.8.3.0
