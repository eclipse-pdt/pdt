<?php

// Start of imap v.

/**
 * Open an IMAP stream to a mailbox
 * @link http://php.net/manual/en/function.imap-open.php
 * @param mailbox string
 * @param username string
 * @param password string
 * @param options int[optional]
 * @param n_retries int[optional]
 * @return resource an IMAP stream on success or false on error.
 */
function imap_open ($mailbox, $username, $password, $options = null, $n_retries = null) {}

/**
 * Reopen IMAP stream to new mailbox
 * @link http://php.net/manual/en/function.imap-reopen.php
 * @param imap_stream resource
 * @param mailbox string
 * @param options int[optional]
 * @param n_retries int[optional]
 * @return bool 
 */
function imap_reopen ($imap_stream, $mailbox, $options = null, $n_retries = null) {}

/**
 * Close an IMAP stream
 * @link http://php.net/manual/en/function.imap-close.php
 * @param imap_stream resource
 * @param flag int[optional]
 * @return bool 
 */
function imap_close ($imap_stream, $flag = null) {}

/**
 * Gets the number of messages in the current mailbox
 * @link http://php.net/manual/en/function.imap-num-msg.php
 * @param imap_stream resource
 * @return int 
 */
function imap_num_msg ($imap_stream) {}

/**
 * Gets the number of recent messages in current mailbox
 * @link http://php.net/manual/en/function.imap-num-recent.php
 * @param imap_stream resource
 * @return int the number of recent messages in the current mailbox, as an
 */
function imap_num_recent ($imap_stream) {}

/**
 * Returns headers for all messages in a mailbox
 * @link http://php.net/manual/en/function.imap-headers.php
 * @param imap_stream resource
 * @return array an array of string formatted with header info. One
 */
function imap_headers ($imap_stream) {}

/**
 * Read the header of the message
 * @link http://php.net/manual/en/function.imap-headerinfo.php
 * @param imap_stream resource
 * @param msg_number int
 * @param fromlength int[optional]
 * @param subjectlength int[optional]
 * @param defaulthost string[optional]
 * @return object the information in an object with following properties:
 */
function imap_headerinfo ($imap_stream, $msg_number, $fromlength = null, $subjectlength = null, $defaulthost = null) {}

/**
 * Parse mail headers from a string
 * @link http://php.net/manual/en/function.imap-rfc822-parse-headers.php
 * @param headers string
 * @param defaulthost string[optional]
 * @return object an object similar to the one returned by
 */
function imap_rfc822_parse_headers ($headers, $defaulthost = null) {}

/**
 * Returns a properly formatted email address given the mailbox, host, and personal info
 * @link http://php.net/manual/en/function.imap-rfc822-write-address.php
 * @param mailbox string
 * @param host string
 * @param personal string
 * @return string a string properly formatted email address as defined in
 */
function imap_rfc822_write_address ($mailbox, $host, $personal) {}

/**
 * Parses an address string
 * @link http://php.net/manual/en/function.imap-rfc822-parse-adrlist.php
 * @param address string
 * @param default_host string
 * @return array an array of objects. The objects properties are:
 */
function imap_rfc822_parse_adrlist ($address, $default_host) {}

/**
 * Read the message body
 * @link http://php.net/manual/en/function.imap-body.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return string the body of the specified message, as a string.
 */
function imap_body ($imap_stream, $msg_number, $options = null) {}

/**
 * Read the structure of a specified body section of a specific message
 * @link http://php.net/manual/en/function.imap-bodystruct.php
 * @param imap_stream resource
 * @param msg_number int
 * @param section string
 * @return object the information in an object, for a detailed description
 */
function imap_bodystruct ($imap_stream, $msg_number, $section) {}

/**
 * Fetch a particular section of the body of the message
 * @link http://php.net/manual/en/function.imap-fetchbody.php
 * @param imap_stream resource
 * @param msg_number int
 * @param part_number string
 * @param options int[optional]
 * @return string a particular section of the body of the specified messages as a
 */
function imap_fetchbody ($imap_stream, $msg_number, $part_number, $options = null) {}

/**
 * Save a specific body section to a file
 * @link http://php.net/manual/en/function.imap-savebody.php
 * @param imap_stream resource
 * @param file mixed
 * @param msg_number int
 * @param part_number string[optional]
 * @param options int[optional]
 * @return bool 
 */
function imap_savebody ($imap_stream, $file, $msg_number, $part_number = null, $options = null) {}

/**
 * Returns header for a message
 * @link http://php.net/manual/en/function.imap-fetchheader.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return string the header of the specified message as a text string.
 */
function imap_fetchheader ($imap_stream, $msg_number, $options = null) {}

/**
 * Read the structure of a particular message
 * @link http://php.net/manual/en/function.imap-fetchstructure.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return object an object includes the envelope, internal date, size, flags and
 */
function imap_fetchstructure ($imap_stream, $msg_number, $options = null) {}

/**
 * Delete all messages marked for deletion
 * @link http://php.net/manual/en/function.imap-expunge.php
 * @param imap_stream resource
 * @return bool true.
 */
function imap_expunge ($imap_stream) {}

/**
 * Mark a message for deletion from current mailbox
 * @link http://php.net/manual/en/function.imap-delete.php
 * @param imap_stream resource
 * @param msg_number int
 * @param options int[optional]
 * @return bool true.
 */
function imap_delete ($imap_stream, $msg_number, $options = null) {}

/**
 * Unmark the message which is marked deleted
 * @link http://php.net/manual/en/function.imap-undelete.php
 * @param imap_stream resource
 * @param msg_number int
 * @param flags int[optional]
 * @return bool 
 */
function imap_undelete ($imap_stream, $msg_number, $flags = null) {}

/**
 * Check current mailbox
 * @link http://php.net/manual/en/function.imap-check.php
 * @param imap_stream resource
 * @return object the information in an object with following properties:
 */
function imap_check ($imap_stream) {}

/**
 * Copy specified messages to a mailbox
 * @link http://php.net/manual/en/function.imap-mail-copy.php
 * @param imap_stream resource
 * @param msglist string
 * @param mailbox string
 * @param options int[optional]
 * @return bool 
 */
function imap_mail_copy ($imap_stream, $msglist, $mailbox, $options = null) {}

/**
 * Move specified messages to a mailbox
 * @link http://php.net/manual/en/function.imap-mail-move.php
 * @param imap_stream resource
 * @param msglist string
 * @param mailbox string
 * @param options int[optional]
 * @return bool 
 */
function imap_mail_move ($imap_stream, $msglist, $mailbox, $options = null) {}

/**
 * Create a MIME message based on given envelope and body sections
 * @link http://php.net/manual/en/function.imap-mail-compose.php
 * @param envelope array
 * @param body array
 * @return string the MIME message.
 */
function imap_mail_compose (array $envelope, array $body) {}

/**
 * Create a new mailbox
 * @link http://php.net/manual/en/function.imap-createmailbox.php
 * @param imap_stream resource
 * @param mailbox string
 * @return bool 
 */
function imap_createmailbox ($imap_stream, $mailbox) {}

/**
 * Rename an old mailbox to new mailbox
 * @link http://php.net/manual/en/function.imap-renamemailbox.php
 * @param imap_stream resource
 * @param old_mbox string
 * @param new_mbox string
 * @return bool 
 */
function imap_renamemailbox ($imap_stream, $old_mbox, $new_mbox) {}

/**
 * Delete a mailbox
 * @link http://php.net/manual/en/function.imap-deletemailbox.php
 * @param imap_stream resource
 * @param mailbox string
 * @return bool 
 */
function imap_deletemailbox ($imap_stream, $mailbox) {}

/**
 * Subscribe to a mailbox
 * @link http://php.net/manual/en/function.imap-subscribe.php
 * @param imap_stream resource
 * @param mailbox string
 * @return bool 
 */
function imap_subscribe ($imap_stream, $mailbox) {}

/**
 * Unsubscribe from a mailbox
 * @link http://php.net/manual/en/function.imap-unsubscribe.php
 * @param imap_stream string
 * @param mailbox string
 * @return bool 
 */
function imap_unsubscribe ($imap_stream, $mailbox) {}

/**
 * Append a string message to a specified mailbox
 * @link http://php.net/manual/en/function.imap-append.php
 * @param imap_stream resource
 * @param mailbox string
 * @param message string
 * @param options string[optional]
 * @return bool 
 */
function imap_append ($imap_stream, $mailbox, $message, $options = null) {}

/**
 * Check if the IMAP stream is still active
 * @link http://php.net/manual/en/function.imap-ping.php
 * @param imap_stream resource
 * @return bool true if the stream is still alive, false otherwise.
 */
function imap_ping ($imap_stream) {}

/**
 * Decode BASE64 encoded text
 * @link http://php.net/manual/en/function.imap-base64.php
 * @param text string
 * @return string the decoded message as a string.
 */
function imap_base64 ($text) {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://php.net/manual/en/function.imap-qprint.php
 * @param string string
 * @return string an 8 bits string.
 */
function imap_qprint ($string) {}

/**
 * Convert an 8bit string to a quoted-printable string
 * @link http://php.net/manual/en/function.imap-8bit.php
 * @param string string
 * @return string a quoted-printable string.
 */
function imap_8bit ($string) {}

/**
 * Convert an 8bit string to a base64 string
 * @link http://php.net/manual/en/function.imap-binary.php
 * @param string string
 * @return string a base64 encoded string.
 */
function imap_binary ($string) {}

/**
 * Converts MIME-encoded text to UTF-8
 * @link http://php.net/manual/en/function.imap-utf8.php
 * @param mime_encoded_text string
 * @return string an UTF-8 encoded string.
 */
function imap_utf8 ($mime_encoded_text) {}

/**
 * Returns status information on a mailbox
 * @link http://php.net/manual/en/function.imap-status.php
 * @param imap_stream resource
 * @param mailbox string
 * @param options int
 * @return object 
 */
function imap_status ($imap_stream, $mailbox, $options) {}

/**
 * Get information about the current mailbox
 * @link http://php.net/manual/en/function.imap-mailboxmsginfo.php
 * @param imap_stream resource
 * @return object the information in an object with following properties:
 */
function imap_mailboxmsginfo ($imap_stream) {}

/**
 * Sets flags on messages
 * @link http://php.net/manual/en/function.imap-setflag-full.php
 * @param imap_stream resource
 * @param sequence string
 * @param flag string
 * @param options int[optional]
 * @return bool 
 */
function imap_setflag_full ($imap_stream, $sequence, $flag, $options = null) {}

/**
 * Clears flags on messages
 * @link http://php.net/manual/en/function.imap-clearflag-full.php
 * @param imap_stream resource
 * @param sequence string
 * @param flag string
 * @param options string[optional]
 * @return bool 
 */
function imap_clearflag_full ($imap_stream, $sequence, $flag, $options = null) {}

/**
 * Gets and sort messages
 * @link http://php.net/manual/en/function.imap-sort.php
 * @param imap_stream resource
 * @param criteria int
 * @param reverse int
 * @param options int[optional]
 * @param search_criteria string[optional]
 * @param charset string[optional]
 * @return array an array of message numbers sorted by the given
 */
function imap_sort ($imap_stream, $criteria, $reverse, $options = null, $search_criteria = null, $charset = null) {}

/**
 * This function returns the UID for the given message sequence number
 * @link http://php.net/manual/en/function.imap-uid.php
 * @param imap_stream resource
 * @param msg_number int
 * @return int 
 */
function imap_uid ($imap_stream, $msg_number) {}

/**
 * Gets the message sequence number for the given UID
 * @link http://php.net/manual/en/function.imap-msgno.php
 * @param imap_stream resource
 * @param uid int
 * @return int the message sequence number for the given
 */
function imap_msgno ($imap_stream, $uid) {}

/**
 * Read the list of mailboxes
 * @link http://php.net/manual/en/function.imap-list.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array containing the names of the mailboxes.
 */
function imap_list ($imap_stream, $ref, $pattern) {}

/**
 * List all the subscribed mailboxes
 * @link http://php.net/manual/en/function.imap-lsub.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array of all the subscribed mailboxes.
 */
function imap_lsub ($imap_stream, $ref, $pattern) {}

/**
 * Read an overview of the information in the headers of the given message
 * @link http://php.net/manual/en/function.imap-fetch-overview.php
 * @param imap_stream resource
 * @param sequence string
 * @param options int[optional]
 * @return array an array of objects describing one message header each.
 */
function imap_fetch_overview ($imap_stream, $sequence, $options = null) {}

/**
 * Returns all IMAP alert messages that have occurred
 * @link http://php.net/manual/en/function.imap-alerts.php
 * @return array an array of all of the IMAP alert messages generated or false if
 */
function imap_alerts () {}

/**
 * Returns all of the IMAP errors that have occured
 * @link http://php.net/manual/en/function.imap-errors.php
 * @return array 
 */
function imap_errors () {}

/**
 * Gets the last IMAP error that occurred during this page request
 * @link http://php.net/manual/en/function.imap-last-error.php
 * @return string the full text of the last IMAP error message that occurred on the
 */
function imap_last_error () {}

/**
 * This function returns an array of messages matching the given search criteria
 * @link http://php.net/manual/en/function.imap-search.php
 * @param imap_stream resource
 * @param criteria string
 * @param options int[optional]
 * @param charset string[optional]
 * @return array an array of message numbers or UIDs.
 */
function imap_search ($imap_stream, $criteria, $options = null, $charset = null) {}

/**
 * Decodes a modified UTF-7 encoded string
 * @link http://php.net/manual/en/function.imap-utf7-decode.php
 * @param text string
 * @return string a string that is encoded in ISO-8859-1 and consists of the same
 */
function imap_utf7_decode ($text) {}

/**
 * Converts ISO-8859-1 string to modified UTF-7 text
 * @link http://php.net/manual/en/function.imap-utf7-encode.php
 * @param data string
 * @return string data encoded with the modified UTF-7
 */
function imap_utf7_encode ($data) {}

/**
 * Decode MIME header elements
 * @link http://php.net/manual/en/function.imap-mime-header-decode.php
 * @param text string
 * @return array 
 */
function imap_mime_header_decode ($text) {}

/**
 * Returns a tree of threaded message
 * @link http://php.net/manual/en/function.imap-thread.php
 * @param imap_stream resource
 * @param options int[optional]
 * @return array 
 */
function imap_thread ($imap_stream, $options = null) {}

/**
 * Set or fetch imap timeout
 * @link http://php.net/manual/en/function.imap-timeout.php
 * @param timeout_type int
 * @param timeout int[optional]
 * @return mixed 
 */
function imap_timeout ($timeout_type, $timeout = null) {}

/**
 * Retrieve the quota level settings, and usage statics per mailbox
 * @link http://php.net/manual/en/function.imap-get-quota.php
 * @param imap_stream resource
 * @param quota_root string
 * @return array an array with integer values limit and usage for the given
 */
function imap_get_quota ($imap_stream, $quota_root) {}

/**
 * Retrieve the quota settings per user
 * @link http://php.net/manual/en/function.imap-get-quotaroot.php
 * @param imap_stream resource
 * @param quota_root string
 * @return array an array of integer values pertaining to the specified user
 */
function imap_get_quotaroot ($imap_stream, $quota_root) {}

/**
 * Sets a quota for a given mailbox
 * @link http://php.net/manual/en/function.imap-set-quota.php
 * @param imap_stream resource
 * @param quota_root string
 * @param quota_limit int
 * @return bool 
 */
function imap_set_quota ($imap_stream, $quota_root, $quota_limit) {}

/**
 * Sets the ACL for a giving mailbox
 * @link http://php.net/manual/en/function.imap-setacl.php
 * @param imap_stream resource
 * @param mailbox string
 * @param id string
 * @param rights string
 * @return bool 
 */
function imap_setacl ($imap_stream, $mailbox, $id, $rights) {}

/**
 * Gets the ACL for a given mailbox
 * @link http://php.net/manual/en/function.imap-getacl.php
 * @param imap_stream resource
 * @param mailbox string
 * @return array an associative array of "folder" => "acl" pairs.
 */
function imap_getacl ($imap_stream, $mailbox) {}

/**
 * Send an email message
 * @link http://php.net/manual/en/function.imap-mail.php
 * @param to string
 * @param subject string
 * @param message string
 * @param additional_headers string[optional]
 * @param cc string[optional]
 * @param bcc string[optional]
 * @param rpath string[optional]
 * @return bool 
 */
function imap_mail ($to, $subject, $message, $additional_headers = null, $cc = null, $bcc = null, $rpath = null) {}

/**
 * &Alias; <function>imap_headerinfo</function>
 * @link http://php.net/manual/en/function.imap-header.php
 */
function imap_header () {}

/**
 * &Alias; <function>imap_list</function>
 * @link http://php.net/manual/en/function.imap-listmailbox.php
 */
function imap_listmailbox () {}

/**
 * Read the list of mailboxes, returning detailed information on each one
 * @link http://php.net/manual/en/function.imap-getmailboxes.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array of objects containing mailbox information. Each
 */
function imap_getmailboxes ($imap_stream, $ref, $pattern) {}

/**
 * &Alias; <function>imap_listscan</function>
 * @link http://php.net/manual/en/function.imap-scanmailbox.php
 */
function imap_scanmailbox () {}

/**
 * &Alias; <function>imap_lsub</function>
 * @link http://php.net/manual/en/function.imap-listsubscribed.php
 */
function imap_listsubscribed () {}

/**
 * List all the subscribed mailboxes
 * @link http://php.net/manual/en/function.imap-getsubscribed.php
 * @param imap_stream resource
 * @param ref string
 * @param pattern string
 * @return array an array of objects containing mailbox information. Each
 */
function imap_getsubscribed ($imap_stream, $ref, $pattern) {}

function imap_fetchtext () {}

function imap_scan () {}

function imap_create () {}

function imap_rename () {}

define ('NIL', 0);
define ('IMAP_OPENTIMEOUT', 1);
define ('IMAP_READTIMEOUT', 2);
define ('IMAP_WRITETIMEOUT', 3);
define ('IMAP_CLOSETIMEOUT', 4);
define ('OP_DEBUG', 1);

/**
 * Open mailbox read-only
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('OP_READONLY', 2);

/**
 * Don't use or update a .newsrc for news 
 * (NNTP only)
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('OP_ANONYMOUS', 4);
define ('OP_SHORTCACHE', 8);
define ('OP_SILENT', 16);
define ('OP_PROTOTYPE', 32);

/**
 * For IMAP and NNTP names, open a connection but don't open a mailbox.
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('OP_HALFOPEN', 64);
define ('OP_EXPUNGE', 128);
define ('OP_SECURE', 256);

/**
 * silently expunge the mailbox before closing when
 * calling imap_close
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('CL_EXPUNGE', 32768);

/**
 * The parameter is a UID
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('FT_UID', 1);

/**
 * Do not set the \Seen flag if not already set
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('FT_PEEK', 2);
define ('FT_NOT', 4);

/**
 * The return string is in internal format, will not canonicalize to CRLF.
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('FT_INTERNAL', 8);
define ('FT_PREFETCHTEXT', 32);

/**
 * The sequence argument contains UIDs instead of sequence numbers
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('ST_UID', 1);
define ('ST_SILENT', 2);
define ('ST_SET', 4);

/**
 * the sequence numbers contain UIDS
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('CP_UID', 1);

/**
 * Delete the messages from the current mailbox after copying
 * with imap_mail_copy
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('CP_MOVE', 2);

/**
 * Return UIDs instead of sequence numbers
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SE_UID', 1);
define ('SE_FREE', 2);

/**
 * Don't prefetch searched messages
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SE_NOPREFETCH', 4);
define ('SO_FREE', 8);
define ('SO_NOSERVER', 16);
define ('SA_MESSAGES', 1);
define ('SA_RECENT', 2);
define ('SA_UNSEEN', 4);
define ('SA_UIDNEXT', 8);
define ('SA_UIDVALIDITY', 16);
define ('SA_ALL', 31);

/**
 * This mailbox has no "children" (there are no
 * mailboxes below this one).
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('LATT_NOINFERIORS', 1);

/**
 * This is only a container, not a mailbox - you
 * cannot open it.
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('LATT_NOSELECT', 2);

/**
 * This mailbox is marked. Only used by UW-IMAPD.
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('LATT_MARKED', 4);

/**
 * This mailbox is not marked. Only used by
 * UW-IMAPD.
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('LATT_UNMARKED', 8);
define ('LATT_REFERRAL', 16);
define ('LATT_HASCHILDREN', 32);
define ('LATT_HASNOCHILDREN', 64);

/**
 * Sort criteria for imap_sort:
 * message Date
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SORTDATE', 0);

/**
 * Sort criteria for imap_sort:
 * arrival date
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SORTARRIVAL', 1);

/**
 * Sort criteria for imap_sort:
 * mailbox in first From address
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SORTFROM', 2);

/**
 * Sort criteria for imap_sort:
 * message subject
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SORTSUBJECT', 3);

/**
 * Sort criteria for imap_sort:
 * mailbox in first To address
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SORTTO', 4);

/**
 * Sort criteria for imap_sort:
 * mailbox in first cc address
 * @link http://php.net/manual/en/imap.constants.php
 */
define ('SORTCC', 5);

/**
 * Sort criteria for imap_sort:
 * size of message in octets
 * @link http://php.net/manual/en/imap.constants.php
 */
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

// End of imap v.
?>
