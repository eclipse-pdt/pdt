<?php

// Start of imap v.8.3.0

namespace IMAP {

final class Connection  {
}


}


namespace {

/**
 * {@inheritdoc}
 * @param string $mailbox
 * @param string $user
 * @param string $password
 * @param int $flags [optional]
 * @param int $retries [optional]
 * @param array $options [optional]
 */
function imap_open (string $mailbox, string $user, string $password, int $flags = 0, int $retries = 0, array $options = array (
)): IMAP\Connection|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 * @param int $flags [optional]
 * @param int $retries [optional]
 */
function imap_reopen (IMAP\Connection $imap, string $mailbox, int $flags = 0, int $retries = 0): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $flags [optional]
 */
function imap_close (IMAP\Connection $imap, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_is_open (IMAP\Connection $imap): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_num_msg (IMAP\Connection $imap): int|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_num_recent (IMAP\Connection $imap): int {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_headers (IMAP\Connection $imap): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param int $from_length [optional]
 * @param int $subject_length [optional]
 */
function imap_headerinfo (IMAP\Connection $imap, int $message_num, int $from_length = 0, int $subject_length = 0): stdClass|false {}

/**
 * {@inheritdoc}
 * @param string $headers
 * @param string $default_hostname [optional]
 */
function imap_rfc822_parse_headers (string $headers, string $default_hostname = 'UNKNOWN'): stdClass {}

/**
 * {@inheritdoc}
 * @param string $mailbox
 * @param string $hostname
 * @param string $personal
 */
function imap_rfc822_write_address (string $mailbox, string $hostname, string $personal): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 * @param string $default_hostname
 */
function imap_rfc822_parse_adrlist (string $string, string $default_hostname): array {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param int $flags [optional]
 */
function imap_body (IMAP\Connection $imap, int $message_num, int $flags = 0): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param int $flags [optional]
 */
function imap_fetchtext (IMAP\Connection $imap, int $message_num, int $flags = 0): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param string $section
 */
function imap_bodystruct (IMAP\Connection $imap, int $message_num, string $section): stdClass|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param string $section
 * @param int $flags [optional]
 */
function imap_fetchbody (IMAP\Connection $imap, int $message_num, string $section, int $flags = 0): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param string $section
 * @param int $flags [optional]
 */
function imap_fetchmime (IMAP\Connection $imap, int $message_num, string $section, int $flags = 0): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param mixed $file
 * @param int $message_num
 * @param string $section [optional]
 * @param int $flags [optional]
 */
function imap_savebody (IMAP\Connection $imap, $file = null, int $message_num, string $section = '', int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param int $flags [optional]
 */
function imap_fetchheader (IMAP\Connection $imap, int $message_num, int $flags = 0): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 * @param int $flags [optional]
 */
function imap_fetchstructure (IMAP\Connection $imap, int $message_num, int $flags = 0): stdClass|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $flags
 */
function imap_gc (IMAP\Connection $imap, int $flags): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_expunge (IMAP\Connection $imap): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $message_nums
 * @param int $flags [optional]
 */
function imap_delete (IMAP\Connection $imap, string $message_nums, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $message_nums
 * @param int $flags [optional]
 */
function imap_undelete (IMAP\Connection $imap, string $message_nums, int $flags = 0): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_check (IMAP\Connection $imap): stdClass|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 * @param string $content
 */
function imap_listscan (IMAP\Connection $imap, string $reference, string $pattern, string $content): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 * @param string $content
 */
function imap_scan (IMAP\Connection $imap, string $reference, string $pattern, string $content): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 * @param string $content
 */
function imap_scanmailbox (IMAP\Connection $imap, string $reference, string $pattern, string $content): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $message_nums
 * @param string $mailbox
 * @param int $flags [optional]
 */
function imap_mail_copy (IMAP\Connection $imap, string $message_nums, string $mailbox, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $message_nums
 * @param string $mailbox
 * @param int $flags [optional]
 */
function imap_mail_move (IMAP\Connection $imap, string $message_nums, string $mailbox, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param array $envelope
 * @param array $bodies
 */
function imap_mail_compose (array $envelope, array $bodies): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_createmailbox (IMAP\Connection $imap, string $mailbox): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_create (IMAP\Connection $imap, string $mailbox): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $from
 * @param string $to
 */
function imap_renamemailbox (IMAP\Connection $imap, string $from, string $to): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $from
 * @param string $to
 */
function imap_rename (IMAP\Connection $imap, string $from, string $to): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_deletemailbox (IMAP\Connection $imap, string $mailbox): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_subscribe (IMAP\Connection $imap, string $mailbox): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_unsubscribe (IMAP\Connection $imap, string $mailbox): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $folder
 * @param string $message
 * @param string|null $options [optional]
 * @param string|null $internal_date [optional]
 */
function imap_append (IMAP\Connection $imap, string $folder, string $message, ?string $options = NULL, ?string $internal_date = NULL): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_ping (IMAP\Connection $imap): bool {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_base64 (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_qprint (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_8bit (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_binary (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $mime_encoded_text
 */
function imap_utf8 (string $mime_encoded_text): string {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 * @param int $flags
 */
function imap_status (IMAP\Connection $imap, string $mailbox, int $flags): stdClass|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 */
function imap_mailboxmsginfo (IMAP\Connection $imap): stdClass {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $sequence
 * @param string $flag
 * @param int $options [optional]
 */
function imap_setflag_full (IMAP\Connection $imap, string $sequence, string $flag, int $options = 0): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $sequence
 * @param string $flag
 * @param int $options [optional]
 */
function imap_clearflag_full (IMAP\Connection $imap, string $sequence, string $flag, int $options = 0): true {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $criteria
 * @param bool $reverse
 * @param int $flags [optional]
 * @param string|null $search_criteria [optional]
 * @param string|null $charset [optional]
 */
function imap_sort (IMAP\Connection $imap, int $criteria, bool $reverse, int $flags = 0, ?string $search_criteria = NULL, ?string $charset = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_num
 */
function imap_uid (IMAP\Connection $imap, int $message_num): int|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $message_uid
 */
function imap_msgno (IMAP\Connection $imap, int $message_uid): int {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 */
function imap_list (IMAP\Connection $imap, string $reference, string $pattern): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 */
function imap_listmailbox (IMAP\Connection $imap, string $reference, string $pattern): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 */
function imap_lsub (IMAP\Connection $imap, string $reference, string $pattern): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 */
function imap_listsubscribed (IMAP\Connection $imap, string $reference, string $pattern): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 */
function imap_getsubscribed (IMAP\Connection $imap, string $reference, string $pattern): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $reference
 * @param string $pattern
 */
function imap_getmailboxes (IMAP\Connection $imap, string $reference, string $pattern): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $sequence
 * @param int $flags [optional]
 */
function imap_fetch_overview (IMAP\Connection $imap, string $sequence, int $flags = 0): array|false {}

/**
 * {@inheritdoc}
 */
function imap_alerts (): array|false {}

/**
 * {@inheritdoc}
 */
function imap_errors (): array|false {}

/**
 * {@inheritdoc}
 */
function imap_last_error (): string|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $criteria
 * @param int $flags [optional]
 * @param string $charset [optional]
 */
function imap_search (IMAP\Connection $imap, string $criteria, int $flags = 2, string $charset = ''): array|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_utf7_decode (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_utf7_encode (string $string): string {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_utf8_to_mutf7 (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_mutf7_to_utf8 (string $string): string|false {}

/**
 * {@inheritdoc}
 * @param string $string
 */
function imap_mime_header_decode (string $string): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param int $flags [optional]
 */
function imap_thread (IMAP\Connection $imap, int $flags = 2): array|false {}

/**
 * {@inheritdoc}
 * @param int $timeout_type
 * @param int $timeout [optional]
 */
function imap_timeout (int $timeout_type, int $timeout = -1): int|bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $quota_root
 */
function imap_get_quota (IMAP\Connection $imap, string $quota_root): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_get_quotaroot (IMAP\Connection $imap, string $mailbox): array|false {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $quota_root
 * @param int $mailbox_size
 */
function imap_set_quota (IMAP\Connection $imap, string $quota_root, int $mailbox_size): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 * @param string $user_id
 * @param string $rights
 */
function imap_setacl (IMAP\Connection $imap, string $mailbox, string $user_id, string $rights): bool {}

/**
 * {@inheritdoc}
 * @param IMAP\Connection $imap
 * @param string $mailbox
 */
function imap_getacl (IMAP\Connection $imap, string $mailbox): array|false {}

/**
 * {@inheritdoc}
 * @param string $to
 * @param string $subject
 * @param string $message
 * @param string|null $additional_headers [optional]
 * @param string|null $cc [optional]
 * @param string|null $bcc [optional]
 * @param string|null $return_path [optional]
 */
function imap_mail (string $to, string $subject, string $message, ?string $additional_headers = NULL, ?string $cc = NULL, ?string $bcc = NULL, ?string $return_path = NULL): bool {}

define ('NIL', 0);
define ('IMAP_OPENTIMEOUT', 1);
define ('IMAP_READTIMEOUT', 2);
define ('IMAP_WRITETIMEOUT', 3);
define ('IMAP_CLOSETIMEOUT', 4);
define ('OP_DEBUG', 1);
define ('OP_READONLY', 2);
define ('OP_ANONYMOUS', 4);
define ('OP_SHORTCACHE', 8);
define ('OP_SILENT', 16);
define ('OP_PROTOTYPE', 32);
define ('OP_HALFOPEN', 64);
define ('OP_EXPUNGE', 128);
define ('OP_SECURE', 256);
define ('CL_EXPUNGE', 32768);
define ('FT_UID', 1);
define ('FT_PEEK', 2);
define ('FT_NOT', 4);
define ('FT_INTERNAL', 8);
define ('FT_PREFETCHTEXT', 32);
define ('ST_UID', 1);
define ('ST_SILENT', 2);
define ('ST_SET', 4);
define ('CP_UID', 1);
define ('CP_MOVE', 2);
define ('SE_UID', 1);
define ('SE_FREE', 2);
define ('SE_NOPREFETCH', 4);
define ('SO_FREE', 8);
define ('SO_NOSERVER', 8);
define ('SA_MESSAGES', 1);
define ('SA_RECENT', 2);
define ('SA_UNSEEN', 4);
define ('SA_UIDNEXT', 8);
define ('SA_UIDVALIDITY', 16);
define ('SA_ALL', 31);
define ('LATT_NOINFERIORS', 1);
define ('LATT_NOSELECT', 2);
define ('LATT_MARKED', 4);
define ('LATT_UNMARKED', 8);
define ('LATT_REFERRAL', 16);
define ('LATT_HASCHILDREN', 32);
define ('LATT_HASNOCHILDREN', 64);
define ('SORTDATE', 0);
define ('SORTARRIVAL', 1);
define ('SORTFROM', 2);
define ('SORTSUBJECT', 3);
define ('SORTTO', 4);
define ('SORTCC', 5);
define ('SORTSIZE', 6);
define ('TYPETEXT', 0);
define ('TYPEMULTIPART', 1);
define ('TYPEMESSAGE', 2);
define ('TYPEAPPLICATION', 3);
define ('TYPEAUDIO', 4);
define ('TYPEIMAGE', 5);
define ('TYPEVIDEO', 6);
define ('TYPEMODEL', 7);
define ('TYPEOTHER', 8);
define ('ENC7BIT', 0);
define ('ENC8BIT', 1);
define ('ENCBINARY', 2);
define ('ENCBASE64', 3);
define ('ENCQUOTEDPRINTABLE', 4);
define ('ENCOTHER', 5);
define ('IMAP_GC_ELT', 1);
define ('IMAP_GC_ENV', 2);
define ('IMAP_GC_TEXTS', 4);


}

// End of imap v.8.3.0
