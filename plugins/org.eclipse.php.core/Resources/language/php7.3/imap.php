<?php

// Start of imap v.7.3.0

/**
 * Open an IMAP stream to a mailbox
 * @link http://www.php.net/manual/en/function.imap-open.php
 * @param string $mailbox <p>
 * A mailbox name consists of a server and a mailbox path on this server.
 * The special name INBOX stands for the current users
 * personal mailbox. Mailbox names that contain international characters
 * besides those in the printable ASCII space have to be encoded with
 * imap_utf7_encode.
 * </p>
 * <p>
 * The server part, which is enclosed in '{' and '}', consists of the servers
 * name or ip address, an optional port (prefixed by ':'), and an optional
 * protocol specification (prefixed by '/'). 
 * </p>
 * <p>
 * The server part is mandatory in all mailbox
 * parameters. 
 * </p>
 * <p>
 * All names which start with { are remote names, and are
 * in the form "{" remote_system_name [":" port] [flags] "}"
 * [mailbox_name] where:
 * <p>
 * <br>
 * remote_system_name - Internet domain name or
 * bracketed IP address of server.
 * <br>
 * port - optional TCP port number, default is the
 * default port for that service
 * <br>
 * flags - optional flags, see following table.
 * <br>
 * mailbox_name - remote mailbox name, default is INBOX
 * </p>
 * </p>
 * <p>
 * <table>
 * Optional flags for names
 * <table>
 * <tr valign="top">
 * <td>Flag</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>/service=service</td>
 * <td>mailbox access service, default is "imap"</td>
 * </tr>
 * <tr valign="top">
 * <td>/user=user</td>
 * <td>remote user name for login on the server</td>
 * </tr>
 * <tr valign="top">
 * <td>/authuser=user</td>
 * <td>remote authentication user; if specified this is the user name
 * whose password is used (e.g. administrator)</td>
 * </tr>
 * <tr valign="top">
 * <td>/anonymous</td>
 * <td>remote access as anonymous user</td>
 * </tr>
 * <tr valign="top">
 * <td>/debug</td>
 * <td>record protocol telemetry in application's debug log</td>
 * </tr>
 * <tr valign="top">
 * <td>/secure</td>
 * <td>do not transmit a plaintext password over the network</td>
 * </tr>
 * <tr valign="top">
 * <td>/imap, /imap2,
 * /imap2bis, /imap4,
 * /imap4rev1</td>
 * <td>equivalent to /service=imap</td>
 * </tr>
 * <tr valign="top">
 * <td>/pop3</td>
 * <td>equivalent to /service=pop3</td>
 * </tr>
 * <tr valign="top">
 * <td>/nntp</td>
 * <td>equivalent to /service=nntp</td>
 * </tr>
 * <tr valign="top">
 * <td>/norsh</td>
 * <td>do not use rsh or ssh to establish a preauthenticated IMAP
 * session</td>
 * </tr>
 * <tr valign="top">
 * <td>/ssl</td>
 * <td>use the Secure Socket Layer to encrypt the session</td>
 * </tr>
 * <tr valign="top">
 * <td>/validate-cert</td>
 * <td>validate certificates from TLS/SSL server (this is the default
 * behavior)</td>
 * </tr>
 * <tr valign="top">
 * <td>/novalidate-cert</td>
 * <td>do not validate certificates from TLS/SSL server, needed if
 * server uses self-signed certificates</td>
 * </tr>
 * <tr valign="top">
 * <td>/tls</td>
 * <td>force use of start-TLS to encrypt the session, and reject
 * connection to servers that do not support it</td>
 * </tr>
 * <tr valign="top">
 * <td>/notls</td>
 * <td>do not do start-TLS to encrypt the session, even with servers
 * that support it</td>
 * </tr>
 * <tr valign="top">
 * <td>/readonly</td>
 * <td>request read-only mailbox open (IMAP only; ignored on NNTP, and
 * an error with SMTP and POP3)</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * @param string $username The user name
 * @param string $password The password associated with the username
 * @param int $options [optional] <p>
 * The options are a bit mask with one or more of
 * the following:
 * <p>
 * <br>
 * OP_READONLY - Open mailbox read-only
 * <br>
 * OP_ANONYMOUS - Don't use or update a
 * .newsrc for news (NNTP only)
 * <br>
 * OP_HALFOPEN - For IMAP
 * and NNTP names, open a connection but
 * don't open a mailbox.
 * <br>
 * CL_EXPUNGE - Expunge mailbox automatically upon mailbox close
 * (see also imap_delete and
 * imap_expunge)
 * <br>
 * OP_DEBUG - Debug protocol negotiations
 * <br>
 * OP_SHORTCACHE - Short (elt-only) caching
 * <br>
 * OP_SILENT - Don't pass up events (internal use)
 * <br>
 * OP_PROTOTYPE - Return driver prototype
 * <br>
 * OP_SECURE - Don't do non-secure authentication
 * </p>
 * </p>
 * @param int $n_retries [optional] Number of maximum connect attempts
 * @param array $params [optional] <p>
 * Connection parameters, the following (string) keys maybe used 
 * to set one or more connection parameters:
 * <p>
 * <br>
 * DISABLE_AUTHENTICATOR - Disable authentication properties
 * </p>
 * </p>
 * @return resource an IMAP stream on success or false on error.
 */
function imap_open (string $mailbox, string $username, string $password, int $options = null, int $n_retries = null, array $params = null) {}

/**
 * Reopen IMAP stream to new mailbox
 * @link http://www.php.net/manual/en/function.imap-reopen.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param int $options [optional] <p>
 * The options are a bit mask with one or more of
 * the following:
 * <p>
 * <br>
 * OP_READONLY - Open mailbox read-only
 * <br>
 * OP_ANONYMOUS - Don't use or update a
 * .newsrc for news (NNTP only)
 * <br>
 * OP_HALFOPEN - For IMAP
 * and NNTP names, open a connection but
 * don't open a mailbox.
 * <br>
 * OP_EXPUNGE - Silently expunge recycle stream
 * <br>
 * CL_EXPUNGE - Expunge mailbox automatically upon mailbox close
 * (see also imap_delete and
 * imap_expunge)
 * </p>
 * </p>
 * @param int $n_retries [optional] Number of maximum connect attempts
 * @return bool true if the stream is reopened, false otherwise.
 */
function imap_reopen ($imap_stream, string $mailbox, int $options = null, int $n_retries = null) {}

/**
 * Close an IMAP stream
 * @link http://www.php.net/manual/en/function.imap-close.php
 * @param resource $imap_stream 
 * @param int $flag [optional] If set to CL_EXPUNGE, the function will silently
 * expunge the mailbox before closing, removing all messages marked for
 * deletion. You can achieve the same thing by using
 * imap_expunge
 * @return bool true on success or false on failure
 */
function imap_close ($imap_stream, int $flag = null) {}

/**
 * Gets the number of messages in the current mailbox
 * @link http://www.php.net/manual/en/function.imap-num-msg.php
 * @param resource $imap_stream 
 * @return int Return the number of messages in the current mailbox, as an integer, or false on error.
 */
function imap_num_msg ($imap_stream) {}

/**
 * Gets the number of recent messages in current mailbox
 * @link http://www.php.net/manual/en/function.imap-num-recent.php
 * @param resource $imap_stream 
 * @return int the number of recent messages in the current mailbox, as an
 * integer.
 */
function imap_num_recent ($imap_stream) {}

/**
 * Returns headers for all messages in a mailbox
 * @link http://www.php.net/manual/en/function.imap-headers.php
 * @param resource $imap_stream 
 * @return array an array of string formatted with header info. One
 * element per mail message.
 */
function imap_headers ($imap_stream) {}

/**
 * Read the header of the message
 * @link http://www.php.net/manual/en/function.imap-headerinfo.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param int $fromlength [optional] Number of characters for the fetchfrom property.
 * Must be greater than or equal to zero.
 * @param int $subjectlength [optional] Number of characters for the fetchsubject property
 * Must be greater than or equal to zero.
 * @param string $defaulthost [optional] 
 * @return object false on error or, if successful, the information in an object with following properties:
 * <p>
 * <br>
 * toaddress - full to: line, up to 1024 characters
 * <br>
 * to - an array of objects from the To: line, with the following 
 * properties: personal, adl,
 * mailbox, and host
 * <br>
 * fromaddress - full from: line, up to 1024 characters
 * <br>
 * from - an array of objects from the From: line, with the following 
 * properties: personal, adl,
 * mailbox, and host
 * <br>
 * ccaddress - full cc: line, up to 1024 characters
 * <br>
 * cc - an array of objects from the Cc: line, with the following 
 * properties: personal, adl,
 * mailbox, and host
 * <br>
 * bccaddress - full bcc: line, up to 1024 characters
 * <br>
 * bcc - an array of objects from the Bcc: line, with the following 
 * properties: personal, adl,
 * mailbox, and host
 * <br>
 * reply_toaddress - full Reply-To: line, up to 1024 characters
 * <br>
 * reply_to - an array of objects from the Reply-To: line, with the following
 * properties: personal, adl,
 * mailbox, and host
 * <br>
 * senderaddress - full sender: line, up to 1024 characters
 * <br>
 * sender - an array of objects from the Sender: line, with the following 
 * properties: personal, adl,
 * mailbox, and host
 * <br>
 * return_pathaddress - full Return-Path: line, up to 1024 characters
 * <br>
 * return_path - an array of objects from the Return-Path: line, with the
 * following properties: personal, 
 * adl, mailbox, and 
 * host
 * <br>
 * remail - 
 * <br>
 * date - The message date as found in its headers
 * <br>
 * Date - Same as date
 * <br>
 * subject - The message subject
 * <br>
 * Subject - Same as subject 
 * <br>
 * in_reply_to - 
 * <br>
 * message_id - 
 * <br>
 * newsgroups - 
 * <br>
 * followup_to - 
 * <br>
 * references - 
 * <br>
 * Recent - R if recent and seen, N
 * if recent and not seen, ' ' if not recent.
 * <br>
 * Unseen - U if not seen AND not recent, ' ' if seen
 * OR not seen and recent
 * <br>
 * Flagged - F if flagged, ' ' if not flagged
 * <br>
 * Answered - A if answered, ' ' if unanswered
 * <br>
 * Deleted - D if deleted, ' ' if not deleted
 * <br>
 * Draft - X if draft, ' ' if not draft
 * <br>
 * Msgno - The message number
 * <br>
 * MailDate - 
 * <br>
 * Size - The message size
 * <br>
 * udate - mail message date in Unix time
 * <br>
 * fetchfrom - from line formatted to fit fromlength
 * characters
 * <br>
 * fetchsubject - subject line formatted to fit 
 * subjectlength characters
 * </p>
 */
function imap_headerinfo ($imap_stream, int $msg_number, int $fromlength = null, int $subjectlength = null, string $defaulthost = null) {}

/**
 * Parse mail headers from a string
 * @link http://www.php.net/manual/en/function.imap-rfc822-parse-headers.php
 * @param string $headers The parsed headers data
 * @param string $defaulthost [optional] The default host name
 * @return object an object similar to the one returned by
 * imap_header, except for the flags and other 
 * properties that come from the IMAP server.
 */
function imap_rfc822_parse_headers (string $headers, string $defaulthost = null) {}

/**
 * Returns a properly formatted email address given the mailbox, host, and personal info
 * @link http://www.php.net/manual/en/function.imap-rfc822-write-address.php
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param string $host The email host part
 * @param string $personal The name of the account owner
 * @return string a string properly formatted email address as defined in RFC2822.
 */
function imap_rfc822_write_address (string $mailbox, string $host, string $personal) {}

/**
 * Parses an address string
 * @link http://www.php.net/manual/en/function.imap-rfc822-parse-adrlist.php
 * @param string $address A string containing addresses
 * @param string $default_host The default host name
 * @return array an array of objects. The objects properties are:
 * <p>
 * <p>
 * <br>
 * mailbox - the mailbox name (username)
 * <br>
 * host - the host name
 * <br>
 * personal - the personal name
 * <br>
 * adl - at domain source route
 * </p>
 * </p>
 */
function imap_rfc822_parse_adrlist (string $address, string $default_host) {}

/**
 * Read the message body
 * @link http://www.php.net/manual/en/function.imap-body.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param int $options [optional] <p>
 * The optional options are a bit mask
 * with one or more of the following:
 * <p>
 * <br>
 * FT_UID - The msg_number is a UID
 * <br>
 * FT_PEEK - Do not set the \Seen flag if not already set
 * <br>
 * FT_INTERNAL - The return string is in internal format, will
 * not canonicalize to CRLF.
 * </p>
 * </p>
 * @return string the body of the specified message, as a string.
 */
function imap_body ($imap_stream, int $msg_number, int $options = null) {}

/**
 * Read the structure of a specified body section of a specific message
 * @link http://www.php.net/manual/en/function.imap-bodystruct.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param string $section The body section to read
 * @return object the information in an object, for a detailed description
 * of the object structure and properties see 
 * imap_fetchstructure.
 */
function imap_bodystruct ($imap_stream, int $msg_number, string $section) {}

/**
 * Fetch a particular section of the body of the message
 * @link http://www.php.net/manual/en/function.imap-fetchbody.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param string $section The part number. It is a string of integers delimited by period which
 * index into a body part list as per the IMAP4 specification
 * @param int $options [optional] <p>
 * A bitmask with one or more of the following:
 * <p>
 * <br>
 * FT_UID - The msg_number is a UID
 * <br>
 * FT_PEEK - Do not set the \Seen flag if
 * not already set
 * <br>
 * FT_INTERNAL - The return string is in
 * internal format, will not canonicalize to CRLF.
 * </p>
 * </p>
 * @return string a particular section of the body of the specified messages as a
 * text string.
 */
function imap_fetchbody ($imap_stream, int $msg_number, string $section, int $options = null) {}

/**
 * Fetch MIME headers for a particular section of the message
 * @link http://www.php.net/manual/en/function.imap-fetchmime.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param string $section The part number. It is a string of integers delimited by period which
 * index into a body part list as per the IMAP4 specification
 * @param int $options [optional] <p>
 * A bitmask with one or more of the following:
 * <p>
 * <br>
 * FT_UID - The msg_number is a UID
 * <br>
 * FT_PEEK - Do not set the \Seen flag if
 * not already set
 * <br>
 * FT_INTERNAL - The return string is in
 * internal format, will not canonicalize to CRLF.
 * </p>
 * </p>
 * @return string the MIME headers of a particular section of the body of the specified messages as a
 * text string.
 */
function imap_fetchmime ($imap_stream, int $msg_number, string $section, int $options = null) {}

/**
 * Save a specific body section to a file
 * @link http://www.php.net/manual/en/function.imap-savebody.php
 * @param resource $imap_stream 
 * @param mixed $file The path to the saved file as a string, or a valid file descriptor
 * returned by fopen.
 * @param int $msg_number The message number
 * @param string $part_number [optional] The part number. It is a string of integers delimited by period which
 * index into a body part list as per the IMAP4 specification
 * @param int $options [optional] <p>
 * A bitmask with one or more of the following:
 * <p>
 * <br>
 * FT_UID - The msg_number is a UID
 * <br>
 * FT_PEEK - Do not set the \Seen flag if
 * not already set
 * <br>
 * FT_INTERNAL - The return string is in
 * internal format, will not canonicalize to CRLF.
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imap_savebody ($imap_stream, $file, int $msg_number, string $part_number = null, int $options = null) {}

/**
 * Returns header for a message
 * @link http://www.php.net/manual/en/function.imap-fetchheader.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param int $options [optional] <p>
 * The possible options are:
 * <p>
 * <br>
 * FT_UID - The msgno
 * argument is a UID
 * <br>
 * FT_INTERNAL - The return string
 * is in "internal" format, without any attempt to
 * canonicalize to CRLF newlines
 * <br>
 * FT_PREFETCHTEXT - The RFC822.TEXT
 * should be pre-fetched at the same time. This avoids an
 * extra RTT on an IMAP connection if a full message text is
 * desired (e.g. in a "save to local file" operation)
 * </p>
 * </p>
 * @return string the header of the specified message as a text string.
 */
function imap_fetchheader ($imap_stream, int $msg_number, int $options = null) {}

/**
 * Read the structure of a particular message
 * @link http://www.php.net/manual/en/function.imap-fetchstructure.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param int $options [optional] This optional parameter only has a single option, 
 * FT_UID, which tells the function to treat the
 * msg_number argument as a 
 * UID.
 * @return object an object includes the envelope, internal date, size, flags and
 * body structure along with a similar object for each mime attachment. The
 * structure of the returned objects is as follows:
 * <p>
 * <table>
 * Returned Objects for imap_fetchstructure
 * <table>
 * <tr valign="top">
 * <td>type</td>
 * <td>Primary body type</td>
 * </tr>
 * <tr valign="top">
 * <td>encoding</td>
 * <td>Body transfer encoding</td>
 * </tr>
 * <tr valign="top">
 * <td>ifsubtype</td>
 * <td>true if there is a subtype string</td>
 * </tr>
 * <tr valign="top">
 * <td>subtype</td>
 * <td>MIME subtype</td>
 * </tr>
 * <tr valign="top">
 * <td>ifdescription</td>
 * <td>true if there is a description string</td>
 * </tr>
 * <tr valign="top">
 * <td>description</td>
 * <td>Content description string</td>
 * </tr>
 * <tr valign="top">
 * <td>ifid</td>
 * <td>true if there is an identification string</td>
 * </tr>
 * <tr valign="top">
 * <td>id</td>
 * <td>Identification string</td>
 * </tr>
 * <tr valign="top">
 * <td>lines</td>
 * <td>Number of lines</td>
 * </tr>
 * <tr valign="top">
 * <td>bytes</td>
 * <td>Number of bytes</td>
 * </tr>
 * <tr valign="top">
 * <td>ifdisposition</td>
 * <td>true if there is a disposition string</td>
 * </tr>
 * <tr valign="top">
 * <td>disposition</td>
 * <td>Disposition string</td>
 * </tr>
 * <tr valign="top">
 * <td>ifdparameters</td>
 * <td>true if the dparameters array exists</td>
 * </tr>
 * <tr valign="top">
 * <td>dparameters</td>
 * <td>An array of objects where each object has an
 * "attribute" and a "value"
 * property corresponding to the parameters on the
 * Content-disposition MIME
 * header.</td>
 * </tr>
 * <tr valign="top">
 * <td>ifparameters</td>
 * <td>true if the parameters array exists</td>
 * </tr>
 * <tr valign="top">
 * <td>parameters</td>
 * <td>An array of objects where each object has an
 * "attribute" and a "value"
 * property.</td>
 * </tr>
 * <tr valign="top">
 * <td>parts</td>
 * <td>An array of objects identical in structure to the top-level
 * object, each of which corresponds to a MIME body
 * part.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * <table>
 * Primary body type (value may vary with used library, use of constants is recommended)
 * <table>
 * <tr valign="top"><td>Value</td><td>Type</td><td>Constant</td></tr>
 * <tr valign="top"><td>0</td><td>text</td><td>TYPETEXT</td></tr>
 * <tr valign="top"><td>1</td><td>multipart</td><td>TYPEMULTIPART</td></tr>
 * <tr valign="top"><td>2</td><td>message</td><td>TYPEMESSAGE</td></tr>
 * <tr valign="top"><td>3</td><td>application</td><td>TYPEAPPLICATION</td></tr>
 * <tr valign="top"><td>4</td><td>audio</td><td>TYPEAUDIO</td></tr>
 * <tr valign="top"><td>5</td><td>image</td><td>TYPEIMAGE</td></tr>
 * <tr valign="top"><td>6</td><td>video</td><td>TYPEVIDEO</td></tr>
 * <tr valign="top"><td>7</td><td>model</td><td>TYPEMODEL</td></tr>
 * <tr valign="top"><td>8</td><td>other</td><td>TYPEOTHER</td></tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * <table>
 * Transfer encodings (value may vary with used library, use of constants is recommended)
 * <table>
 * <tr valign="top"><td>Value</td><td>Type</td><td>Constant</td></tr>
 * <tr valign="top"><td>0</td><td>7bit</td><td>ENC7BIT</td></tr>
 * <tr valign="top"><td>1</td><td>8bit</td><td>ENC8BIT</td></tr>
 * <tr valign="top"><td>2</td><td>Binary</td><td>ENCBINARY</td></tr>
 * <tr valign="top"><td>3</td><td>Base64</td><td>ENCBASE64</td></tr>
 * <tr valign="top"><td>4</td><td>Quoted-Printable</td><td>ENCQUOTEDPRINTABLE</td></tr>
 * <tr valign="top"><td>5</td><td>other</td><td>ENCOTHER</td></tr>
 * </table>
 * </table>
 * </p>
 */
function imap_fetchstructure ($imap_stream, int $msg_number, int $options = null) {}

/**
 * Clears IMAP cache
 * @link http://www.php.net/manual/en/function.imap-gc.php
 * @param resource $imap_stream 
 * @param int $caches Specifies the cache to purge. It may one or a combination
 * of the following constants: 
 * IMAP_GC_ELT (message cache elements), 
 * IMAP_GC_ENV (envelope and bodies),
 * IMAP_GC_TEXTS (texts).
 * @return bool true on success or false on failure
 */
function imap_gc ($imap_stream, int $caches) {}

/**
 * Delete all messages marked for deletion
 * @link http://www.php.net/manual/en/function.imap-expunge.php
 * @param resource $imap_stream 
 * @return bool true.
 */
function imap_expunge ($imap_stream) {}

/**
 * Mark a message for deletion from current mailbox
 * @link http://www.php.net/manual/en/function.imap-delete.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param int $options [optional] You can set the FT_UID which tells the function
 * to treat the msg_number argument as an
 * UID.
 * @return bool true.
 */
function imap_delete ($imap_stream, int $msg_number, int $options = null) {}

/**
 * Unmark the message which is marked deleted
 * @link http://www.php.net/manual/en/function.imap-undelete.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number
 * @param int $flags [optional] 
 * @return bool true on success or false on failure
 */
function imap_undelete ($imap_stream, int $msg_number, int $flags = null) {}

/**
 * Check current mailbox
 * @link http://www.php.net/manual/en/function.imap-check.php
 * @param resource $imap_stream 
 * @return object the information in an object with following properties:
 * <p>
 * <br>
 * Date - current system time formatted according to RFC2822
 * <br>
 * Driver - protocol used to access this mailbox:
 * POP3, IMAP, NNTP
 * <br>
 * Mailbox - the mailbox name
 * <br>
 * Nmsgs - number of messages in the mailbox
 * <br>
 * Recent - number of recent messages in the mailbox
 * </p>
 * <p>
 * Returns false on failure.
 * </p>
 */
function imap_check ($imap_stream) {}

/**
 * Returns the list of mailboxes that matches the given text
 * @link http://www.php.net/manual/en/function.imap-listscan.php
 * @param resource $imap_stream 
 * @param string $ref ref should normally be just the server 
 * specification as described in imap_open
 * @param string $pattern imap.pattern
 * @param string $content The searched string
 * @return array an array containing the names of the mailboxes that have
 * content in the text of the mailbox.
 */
function imap_listscan ($imap_stream, string $ref, string $pattern, string $content) {}

/**
 * Copy specified messages to a mailbox
 * @link http://www.php.net/manual/en/function.imap-mail-copy.php
 * @param resource $imap_stream 
 * @param string $msglist msglist is a range not just message
 * numbers (as described in RFC2060).
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param int $options [optional] <p>
 * options is a bitmask of one or more of
 * <p>
 * <br>
 * CP_UID - the sequence numbers contain UIDS
 * <br>
 * CP_MOVE - Delete the messages from
 * the current mailbox after copying
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imap_mail_copy ($imap_stream, string $msglist, string $mailbox, int $options = null) {}

/**
 * Move specified messages to a mailbox
 * @link http://www.php.net/manual/en/function.imap-mail-move.php
 * @param resource $imap_stream 
 * @param string $msglist msglist is a range not just message numbers
 * (as described in RFC2060).
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param int $options [optional] <p>
 * options is a bitmask and may contain the single option:
 * <p>
 * <br>
 * CP_UID - the sequence numbers contain UIDS
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imap_mail_move ($imap_stream, string $msglist, string $mailbox, int $options = null) {}

/**
 * Create a MIME message based on given envelope and body sections
 * @link http://www.php.net/manual/en/function.imap-mail-compose.php
 * @param array $envelope An associative array of headers fields. Valid keys are: "remail",
 * "return_path", "date", "from", "reply_to", "in_reply_to", "subject",
 * "to", "cc", "bcc", "message_id" and "custom_headers" (which contains
 * associative array of other headers).
 * @param array $body <p>
 * An indexed array of bodies
 * </p>
 * <p>
 * A body is an associative array which can consist of the following keys:
 * "type", "encoding", "charset", "type.parameters", "subtype", "id",
 * "description", "disposition.type", "disposition", "contents.data",
 * "lines", "bytes" and "md5".
 * </p>
 * @return string the MIME message.
 */
function imap_mail_compose (array $envelope, array $body) {}

/**
 * Create a new mailbox
 * @link http://www.php.net/manual/en/function.imap-createmailbox.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information. Names containing international characters should be
 * encoded by imap_utf7_encode
 * @return bool true on success or false on failure
 */
function imap_createmailbox ($imap_stream, string $mailbox) {}

/**
 * Rename an old mailbox to new mailbox
 * @link http://www.php.net/manual/en/function.imap-renamemailbox.php
 * @param resource $imap_stream 
 * @param string $old_mbox The old mailbox name, see imap_open for more
 * information
 * @param string $new_mbox The new mailbox name, see imap_open for more
 * information
 * @return bool true on success or false on failure
 */
function imap_renamemailbox ($imap_stream, string $old_mbox, string $new_mbox) {}

/**
 * Delete a mailbox
 * @link http://www.php.net/manual/en/function.imap-deletemailbox.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @return bool true on success or false on failure
 */
function imap_deletemailbox ($imap_stream, string $mailbox) {}

/**
 * Subscribe to a mailbox
 * @link http://www.php.net/manual/en/function.imap-subscribe.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @return bool true on success or false on failure
 */
function imap_subscribe ($imap_stream, string $mailbox) {}

/**
 * Unsubscribe from a mailbox
 * @link http://www.php.net/manual/en/function.imap-unsubscribe.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @return bool true on success or false on failure
 */
function imap_unsubscribe ($imap_stream, string $mailbox) {}

/**
 * Append a string message to a specified mailbox
 * @link http://www.php.net/manual/en/function.imap-append.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param string $message <p>
 * The message to be append, as a string
 * </p>
 * <p>
 * When talking to the Cyrus IMAP server, you must use "\r\n" as
 * your end-of-line terminator instead of "\n" or the operation will
 * fail
 * </p>
 * @param string $options [optional] If provided, the options will also be written
 * to the mailbox
 * @param string $internal_date [optional] If this parameter is set, it will set the INTERNALDATE on the appended message. The parameter should be a date string that conforms to the rfc2060 specifications for a date_time value.
 * @return bool true on success or false on failure
 */
function imap_append ($imap_stream, string $mailbox, string $message, string $options = null, string $internal_date = null) {}

/**
 * Check if the IMAP stream is still active
 * @link http://www.php.net/manual/en/function.imap-ping.php
 * @param resource $imap_stream 
 * @return bool true if the stream is still alive, false otherwise.
 */
function imap_ping ($imap_stream) {}

/**
 * Decode BASE64 encoded text
 * @link http://www.php.net/manual/en/function.imap-base64.php
 * @param string $text The encoded text
 * @return string the decoded message as a string.
 */
function imap_base64 (string $text) {}

/**
 * Convert a quoted-printable string to an 8 bit string
 * @link http://www.php.net/manual/en/function.imap-qprint.php
 * @param string $string A quoted-printable string
 * @return string an 8 bits string.
 */
function imap_qprint (string $string) {}

/**
 * Convert an 8bit string to a quoted-printable string
 * @link http://www.php.net/manual/en/function.imap-8bit.php
 * @param string $string The 8bit string to convert
 * @return string a quoted-printable string.
 */
function imap_8bit (string $string) {}

/**
 * Convert an 8bit string to a base64 string
 * @link http://www.php.net/manual/en/function.imap-binary.php
 * @param string $string The 8bit string
 * @return string a base64 encoded string.
 */
function imap_binary (string $string) {}

/**
 * Converts MIME-encoded text to UTF-8
 * @link http://www.php.net/manual/en/function.imap-utf8.php
 * @param string $mime_encoded_text A MIME encoded string. MIME encoding method and the UTF-8 
 * specification are described in RFC2047 and RFC2044 respectively.
 * @return string an UTF-8 encoded string.
 */
function imap_utf8 (string $mime_encoded_text) {}

/**
 * Returns status information on a mailbox
 * @link http://www.php.net/manual/en/function.imap-status.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param int $options <p>
 * Valid flags are:
 * <p>
 * <br>
 * SA_MESSAGES - set $status->messages to the
 * number of messages in the mailbox
 * <br>
 * SA_RECENT - set $status->recent to the number
 * of recent messages in the mailbox
 * <br>
 * SA_UNSEEN - set $status->unseen to the number
 * of unseen (new) messages in the mailbox
 * <br>
 * SA_UIDNEXT - set $status->uidnext to the next
 * uid to be used in the mailbox
 * <br>
 * SA_UIDVALIDITY - set $status->uidvalidity to a
 * constant that changes when uids for the mailbox may no longer be
 * valid
 * <br>
 * SA_ALL - set all of the above
 * </p>
 * </p>
 * @return object This function returns an object containing status information.
 * The object has the following properties: messages,
 * recent, unseen, 
 * uidnext, and uidvalidity. 
 * <p>
 * flags is also set, which contains a bitmask which can
 * be checked against any of the above constants.
 * </p>
 */
function imap_status ($imap_stream, string $mailbox, int $options) {}

/**
 * Get information about the current mailbox
 * @link http://www.php.net/manual/en/function.imap-mailboxmsginfo.php
 * @param resource $imap_stream 
 * @return object the information in an object with following properties:
 * <table>
 * Mailbox properties
 * <table>
 * <tr valign="top">
 * <td>Date</td>
 * <td>date of last change (current datetime)</td>
 * </tr>
 * <tr valign="top">
 * <td>Driver</td>
 * <td>driver</td>
 * </tr>
 * <tr valign="top">
 * <td>Mailbox</td>
 * <td>name of the mailbox</td>
 * </tr>
 * <tr valign="top">
 * <td>Nmsgs</td>
 * <td>number of messages</td>
 * </tr>
 * <tr valign="top">
 * <td>Recent</td>
 * <td>number of recent messages</td>
 * </tr>
 * <tr valign="top">
 * <td>Unread</td>
 * <td>number of unread messages</td>
 * </tr>
 * <tr valign="top">
 * <td>Deleted</td>
 * <td>number of deleted messages</td>
 * </tr>
 * <tr valign="top">
 * <td>Size</td>
 * <td>mailbox size</td>
 * </tr>
 * </table>
 * </table>
 * <p>
 * Returns false on failure.
 * </p>
 */
function imap_mailboxmsginfo ($imap_stream) {}

/**
 * Sets flags on messages
 * @link http://www.php.net/manual/en/function.imap-setflag-full.php
 * @param resource $imap_stream 
 * @param string $sequence A sequence of message numbers. You can enumerate desired messages
 * with the X,Y syntax, or retrieve all messages 
 * within an interval with the X:Y syntax
 * @param string $flag The flags which you can set are \Seen, 
 * \Answered, \Flagged,
 * \Deleted, and \Draft as
 * defined by RFC2060.
 * @param int $options [optional] <p>
 * A bit mask that may contain the single option:
 * <p>
 * <br>
 * ST_UID - The sequence argument contains UIDs
 * instead of sequence numbers
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imap_setflag_full ($imap_stream, string $sequence, string $flag, int $options = null) {}

/**
 * Clears flags on messages
 * @link http://www.php.net/manual/en/function.imap-clearflag-full.php
 * @param resource $imap_stream 
 * @param string $sequence A sequence of message numbers. You can enumerate desired messages
 * with the X,Y syntax, or retrieve all messages 
 * within an interval with the X:Y syntax
 * @param string $flag The flags which you can unset are "\\Seen", "\\Answered", "\\Flagged",
 * "\\Deleted", and "\\Draft" (as defined by RFC2060)
 * @param int $options [optional] <p>
 * options are a bit mask and may contain
 * the single option:
 * <p>
 * <br>
 * ST_UID - The sequence argument contains UIDs
 * instead of sequence numbers
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function imap_clearflag_full ($imap_stream, string $sequence, string $flag, int $options = null) {}

/**
 * Gets and sort messages
 * @link http://www.php.net/manual/en/function.imap-sort.php
 * @param resource $imap_stream 
 * @param int $criteria <p>
 * Criteria can be one (and only one) of the following:
 * <p>
 * <br>
 * SORTDATE - message Date
 * <br>
 * SORTARRIVAL - arrival date
 * <br>
 * SORTFROM - mailbox in first From address
 * <br>
 * SORTSUBJECT - message subject
 * <br>
 * SORTTO - mailbox in first To address
 * <br>
 * SORTCC - mailbox in first cc address
 * <br>
 * SORTSIZE - size of message in octets
 * </p>
 * </p>
 * @param int $reverse Set this to 1 for reverse sorting
 * @param int $options [optional] <p>
 * The options are a bitmask of one or more of the
 * following:
 * <p>
 * <br>
 * SE_UID - Return UIDs instead of sequence numbers
 * <br>
 * SE_NOPREFETCH - Don't prefetch searched messages
 * </p>
 * </p>
 * @param string $search_criteria [optional] IMAP2-format search criteria string. For details see
 * imap_search.
 * @param string $charset [optional] MIME character set to use when sorting strings.
 * @return array an array of message numbers sorted by the given
 * parameters.
 */
function imap_sort ($imap_stream, int $criteria, int $reverse, int $options = null, string $search_criteria = null, string $charset = null) {}

/**
 * This function returns the UID for the given message sequence number
 * @link http://www.php.net/manual/en/function.imap-uid.php
 * @param resource $imap_stream 
 * @param int $msg_number The message number.
 * @return int The UID of the given message.
 */
function imap_uid ($imap_stream, int $msg_number) {}

/**
 * Gets the message sequence number for the given UID
 * @link http://www.php.net/manual/en/function.imap-msgno.php
 * @param resource $imap_stream 
 * @param int $uid The message UID
 * @return int the message sequence number for the given 
 * uid.
 */
function imap_msgno ($imap_stream, int $uid) {}

/**
 * Read the list of mailboxes
 * @link http://www.php.net/manual/en/function.imap-list.php
 * @param resource $imap_stream 
 * @param string $ref ref should normally be just the server
 * specification as described in imap_open.
 * @param string $pattern imap.pattern
 * @return array an array containing the names of the mailboxes or false in case of failure.
 */
function imap_list ($imap_stream, string $ref, string $pattern) {}

/**
 * List all the subscribed mailboxes
 * @link http://www.php.net/manual/en/function.imap-lsub.php
 * @param resource $imap_stream 
 * @param string $ref ref should normally be just the server 
 * specification as described in imap_open
 * @param string $pattern imap.pattern
 * @return array an array of all the subscribed mailboxes.
 */
function imap_lsub ($imap_stream, string $ref, string $pattern) {}

/**
 * Read an overview of the information in the headers of the given message
 * @link http://www.php.net/manual/en/function.imap-fetch-overview.php
 * @param resource $imap_stream 
 * @param string $sequence A message sequence description. You can enumerate desired messages
 * with the X,Y syntax, or retrieve all messages 
 * within an interval with the X:Y syntax
 * @param int $options [optional] sequence will contain a sequence of message
 * indices or UIDs, if this parameter is set to 
 * FT_UID.
 * @return array an array of objects describing one message header each.
 * The object will only define a property if it exists. The possible
 * properties are:
 * <p>
 * <br>
 * subject - the messages subject
 * <br>
 * from - who sent it
 * <br>
 * to - recipient
 * <br>
 * date - when was it sent
 * <br>
 * message_id - Message-ID
 * <br>
 * references - is a reference to this message id
 * <br>
 * in_reply_to - is a reply to this message id
 * <br>
 * size - size in bytes
 * <br>
 * uid - UID the message has in the mailbox
 * <br>
 * msgno - message sequence number in the mailbox
 * <br>
 * recent - this message is flagged as recent
 * <br>
 * flagged - this message is flagged
 * <br>
 * answered - this message is flagged as answered
 * <br>
 * deleted - this message is flagged for deletion
 * <br>
 * seen - this message is flagged as already read
 * <br>
 * draft - this message is flagged as being a draft
 * <br>
 * udate - the UNIX timestamp of the arrival date
 * </p>
 */
function imap_fetch_overview ($imap_stream, string $sequence, int $options = null) {}

/**
 * Returns all IMAP alert messages that have occurred
 * @link http://www.php.net/manual/en/function.imap-alerts.php
 * @return array an array of all of the IMAP alert messages generated or false if
 * no alert messages are available.
 */
function imap_alerts () {}

/**
 * Returns all of the IMAP errors that have occurred
 * @link http://www.php.net/manual/en/function.imap-errors.php
 * @return array This function returns an array of all of the IMAP error messages
 * generated since the last imap_errors call,
 * or the beginning of the page. Returns false if no error messages are
 * available.
 */
function imap_errors () {}

/**
 * Gets the last IMAP error that occurred during this page request
 * @link http://www.php.net/manual/en/function.imap-last-error.php
 * @return string the full text of the last IMAP error message that occurred on the
 * current page. Returns false if no error messages are available.
 */
function imap_last_error () {}

/**
 * This function returns an array of messages matching the given search criteria
 * @link http://www.php.net/manual/en/function.imap-search.php
 * @param resource $imap_stream 
 * @param string $criteria <p>
 * A string, delimited by spaces, in which the following keywords are
 * allowed. Any multi-word arguments (e.g.
 * FROM "joey smith") must be quoted. Results will match
 * all criteria entries.
 * <p>
 * <br>
 * ALL - return all messages matching the rest of the criteria
 * <br>
 * ANSWERED - match messages with the \\ANSWERED flag set
 * <br>
 * BCC "string" - match messages with "string" in the Bcc: field
 * <br>
 * BEFORE "date" - match messages with Date: before "date"
 * <br>
 * BODY "string" - match messages with "string" in the body of the message
 * <br>
 * CC "string" - match messages with "string" in the Cc: field
 * <br>
 * DELETED - match deleted messages
 * <br>
 * FLAGGED - match messages with the \\FLAGGED (sometimes
 * referred to as Important or Urgent) flag set
 * <br>
 * FROM "string" - match messages with "string" in the From: field
 * <br>
 * KEYWORD "string" - match messages with "string" as a keyword
 * <br>
 * NEW - match new messages
 * <br>
 * OLD - match old messages
 * <br>
 * ON "date" - match messages with Date: matching "date"
 * <br>
 * RECENT - match messages with the \\RECENT flag set
 * <br>
 * SEEN - match messages that have been read (the \\SEEN flag is set)
 * <br>
 * SINCE "date" - match messages with Date: after "date"
 * <br>
 * SUBJECT "string" - match messages with "string" in the Subject:
 * <br>
 * TEXT "string" - match messages with text "string"
 * <br>
 * TO "string" - match messages with "string" in the To:
 * <br>
 * UNANSWERED - match messages that have not been answered
 * <br>
 * UNDELETED - match messages that are not deleted
 * <br>
 * UNFLAGGED - match messages that are not flagged
 * <br>
 * UNKEYWORD "string" - match messages that do not have the
 * keyword "string"
 * <br>
 * UNSEEN - match messages which have not been read yet
 * </p>
 * </p>
 * @param int $options [optional] Valid values for options are 
 * SE_UID, which causes the returned array to
 * contain UIDs instead of messages sequence numbers.
 * @param string $charset [optional] MIME character set to use when searching strings.
 * @return array an array of message numbers or UIDs.
 * <p>
 * Return false if it does not understand the search
 * criteria or no messages have been found.
 * </p>
 */
function imap_search ($imap_stream, string $criteria, int $options = null, string $charset = null) {}

/**
 * Decodes a modified UTF-7 encoded string
 * @link http://www.php.net/manual/en/function.imap-utf7-decode.php
 * @param string $text A modified UTF-7 encoding string, as defined in RFC 2060, section 5.1.3 (original UTF-7
 * was defined in RFC1642).
 * @return string a string that is encoded in ISO-8859-1 and consists of the same
 * sequence of characters in text, or false
 * if text contains invalid modified UTF-7 sequence
 * or text contains a character that is not part of
 * ISO-8859-1 character set.
 */
function imap_utf7_decode (string $text) {}

/**
 * Converts ISO-8859-1 string to modified UTF-7 text
 * @link http://www.php.net/manual/en/function.imap-utf7-encode.php
 * @param string $data An ISO-8859-1 string.
 * @return string data encoded with the modified UTF-7
 * encoding as defined in RFC 2060, 
 * section 5.1.3 (original UTF-7 was defined in RFC1642).
 */
function imap_utf7_encode (string $data) {}

/**
 * Encode a UTF-8 string to modified UTF-7
 * @link http://www.php.net/manual/en/function.imap-utf8-to-mutf7.php
 * @param string $in A UTF-8 encoded string.
 * @return string in converted to modified UTF-7,
 * or false on failure.
 */
function imap_utf8_to_mutf7 (string $in) {}

/**
 * Decode a modified UTF-7 string to UTF-8
 * @link http://www.php.net/manual/en/function.imap-mutf7-to-utf8.php
 * @param string $in A string encoded in modified UTF-7.
 * @return string in converted to UTF-8,
 * or false on failure.
 */
function imap_mutf7_to_utf8 (string $in) {}

/**
 * Decode MIME header elements
 * @link http://www.php.net/manual/en/function.imap-mime-header-decode.php
 * @param string $text The MIME text
 * @return array The decoded elements are returned in an array of objects, where each
 * object has two properties, charset and 
 * text.
 * <p>
 * If the element hasn't been encoded, and in other words is in
 * plain US-ASCII, the charset property of that element is
 * set to default.
 * </p>
 */
function imap_mime_header_decode (string $text) {}

/**
 * Returns a tree of threaded message
 * @link http://www.php.net/manual/en/function.imap-thread.php
 * @param resource $imap_stream 
 * @param int $options [optional] 
 * @return array imap_thread returns an associative array containing
 * a tree of messages threaded by REFERENCES, or false
 * on error.
 * <p>
 * Every message in the current mailbox will be represented by three entries
 * in the resulting array:
 * <p>
 * <br><p>
 * $thread["XX.num"] - current message number
 * </p>
 * <br><p>
 * $thread["XX.next"]
 * </p>
 * <br><p>
 * $thread["XX.branch"]
 * </p>
 * </p>
 * </p>
 */
function imap_thread ($imap_stream, int $options = null) {}

/**
 * Set or fetch imap timeout
 * @link http://www.php.net/manual/en/function.imap-timeout.php
 * @param int $timeout_type One of the following:
 * IMAP_OPENTIMEOUT,
 * IMAP_READTIMEOUT,
 * IMAP_WRITETIMEOUT, or
 * IMAP_CLOSETIMEOUT.
 * @param int $timeout [optional] The timeout, in seconds.
 * @return mixed If the timeout parameter is set, this function
 * returns true on success and false on failure.
 * <p>
 * If timeout is not provided or evaluates to -1,
 * the current timeout value of timeout_type is
 * returned as an integer.
 * </p>
 */
function imap_timeout (int $timeout_type, int $timeout = null) {}

/**
 * Retrieve the quota level settings, and usage statics per mailbox
 * @link http://www.php.net/manual/en/function.imap-get-quota.php
 * @param resource $imap_stream 
 * @param string $quota_root quota_root should normally be in the form of
 * user.name where name is the mailbox you wish to
 * retrieve information about.
 * @return array an array with integer values limit and usage for the given
 * mailbox. The value of limit represents the total amount of space
 * allowed for this mailbox. The usage value represents the mailboxes
 * current level of capacity. Will return false in the case of failure.
 * <p>
 * As of PHP 4.3, the function more properly reflects the
 * functionality as dictated by the RFC2087.
 * The array return value has changed to support an unlimited number of returned 
 * resources (i.e. messages, or sub-folders) with each named resource receiving
 * an individual array key. Each key value then contains an another array with
 * the usage and limit values within it.
 * </p>
 * <p>
 * For backwards compatibility reasons, the original access methods are
 * still available for use, although it is suggested to update.
 * </p>
 */
function imap_get_quota ($imap_stream, string $quota_root) {}

/**
 * Retrieve the quota settings per user
 * @link http://www.php.net/manual/en/function.imap-get-quotaroot.php
 * @param resource $imap_stream 
 * @param string $quota_root quota_root should normally be in the form of
 * which mailbox (i.e. INBOX).
 * @return array an array of integer values pertaining to the specified user
 * mailbox. All values contain a key based upon the resource name, and a
 * corresponding array with the usage and limit values within.
 * <p>
 * This function will return false in the case of call failure, and an
 * array of information about the connection upon an un-parsable response
 * from the server.
 * </p>
 */
function imap_get_quotaroot ($imap_stream, string $quota_root) {}

/**
 * Sets a quota for a given mailbox
 * @link http://www.php.net/manual/en/function.imap-set-quota.php
 * @param resource $imap_stream 
 * @param string $quota_root The mailbox to have a quota set. This should follow the IMAP standard
 * format for a mailbox: user.name.
 * @param int $quota_limit The maximum size (in KB) for the quota_root
 * @return bool true on success or false on failure
 */
function imap_set_quota ($imap_stream, string $quota_root, int $quota_limit) {}

/**
 * Sets the ACL for a given mailbox
 * @link http://www.php.net/manual/en/function.imap-setacl.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @param string $id The user to give the rights to.
 * @param string $rights The rights to give to the user. Passing an empty string will delete
 * acl.
 * @return bool true on success or false on failure
 */
function imap_setacl ($imap_stream, string $mailbox, string $id, string $rights) {}

/**
 * Gets the ACL for a given mailbox
 * @link http://www.php.net/manual/en/function.imap-getacl.php
 * @param resource $imap_stream 
 * @param string $mailbox The mailbox name, see imap_open for more
 * information
 * @return array an associative array of "folder" => "acl" pairs.
 */
function imap_getacl ($imap_stream, string $mailbox) {}

/**
 * Send an email message
 * @link http://www.php.net/manual/en/function.imap-mail.php
 * @param string $to The receiver
 * @param string $subject The mail subject
 * @param string $message The mail body, see imap_mail_compose
 * @param string $additional_headers [optional] As string with additional headers to be set on the mail
 * @param string $cc [optional] 
 * @param string $bcc [optional] The receivers specified in bcc will get the
 * mail, but are excluded from the headers.
 * @param string $rpath [optional] Use this parameter to specify return path upon mail delivery failure.
 * This is useful when using PHP as a mail client for multiple users.
 * @return bool true on success or false on failure
 */
function imap_mail (string $to, string $subject, string $message, string $additional_headers = null, string $cc = null, string $bcc = null, string $rpath = null) {}

/**
 * Alias: imap_headerinfo
 * @link http://www.php.net/manual/en/function.imap-header.php
 * @param mixed $stream_id
 * @param mixed $msg_no
 * @param mixed $from_length [optional]
 * @param mixed $subject_length [optional]
 * @param mixed $default_host [optional]
 */
function imap_header ($stream_id, $msg_no, $from_length = null, $subject_length = null, $default_host = null) {}

/**
 * Alias: imap_list
 * @link http://www.php.net/manual/en/function.imap-listmailbox.php
 * @param mixed $stream_id
 * @param mixed $ref
 * @param mixed $pattern
 */
function imap_listmailbox ($stream_id, $ref, $pattern) {}

/**
 * Read the list of mailboxes, returning detailed information on each one
 * @link http://www.php.net/manual/en/function.imap-getmailboxes.php
 * @param resource $imap_stream 
 * @param string $ref ref should normally be just the server
 * specification as described in imap_open
 * @param string $pattern imap.pattern
 * @return array an array of objects containing mailbox information. Each
 * object has the attributes name, specifying
 * the full name of the mailbox; delimiter,
 * which is the hierarchy delimiter for the part of the hierarchy
 * this mailbox is in; and
 * attributes. Attributes
 * is a bitmask that can be tested against:
 * <p>
 * <br>
 * <p>
 * LATT_NOINFERIORS - This mailbox not contains, and may not contain any
 * "children" (there are no mailboxes below this one). Calling 
 * imap_createmailbox will not work on this mailbox.
 * <br>
 * <p>
 * LATT_NOSELECT - This is only a container,
 * not a mailbox - you cannot open it.
 * </p>
 * <br>
 * <p>
 * LATT_MARKED - This mailbox is marked. This means that it may 
 * contain new messages since the last time it was checked. Not provided by all IMAP
 * servers.
 * </p>
 * <br>
 * <p>
 * LATT_UNMARKED - This mailbox is not marked, does not contain new
 * messages. If either MARKED or UNMARKED is
 * provided, you can assume the IMAP server supports this feature for this mailbox.
 * </p>
 * <br>
 * <p>
 * LATT_REFERRAL - This container has a referral to a remote mailbox.
 * </p>
 * <br>
 * <p>
 * LATT_HASCHILDREN - This mailbox has selectable inferiors.
 * </p>
 * <br>
 * <p>
 * LATT_HASNOCHILDREN - This mailbox has no selectable inferiors.
 * </p>
 * </p>
 * </p>
 */
function imap_getmailboxes ($imap_stream, string $ref, string $pattern) {}

/**
 * Alias: imap_listscan
 * @link http://www.php.net/manual/en/function.imap-scanmailbox.php
 * @param mixed $stream_id
 * @param mixed $ref
 * @param mixed $pattern
 * @param mixed $content
 */
function imap_scanmailbox ($stream_id, $ref, $pattern, $content) {}

/**
 * Alias: imap_lsub
 * @link http://www.php.net/manual/en/function.imap-listsubscribed.php
 * @param mixed $stream_id
 * @param mixed $ref
 * @param mixed $pattern
 */
function imap_listsubscribed ($stream_id, $ref, $pattern) {}

/**
 * List all the subscribed mailboxes
 * @link http://www.php.net/manual/en/function.imap-getsubscribed.php
 * @param resource $imap_stream 
 * @param string $ref ref should normally be just the server
 * specification as described in imap_open
 * @param string $pattern imap.pattern
 * @return array an array of objects containing mailbox information. Each
 * object has the attributes name, specifying
 * the full name of the mailbox; delimiter,
 * which is the hierarchy delimiter for the part of the hierarchy
 * this mailbox is in; and
 * attributes. Attributes
 * is a bitmask that can be tested against:
 * <p>
 * <br>
 * LATT_NOINFERIORS - This mailbox has no
 * "children" (there are no mailboxes below this one).
 * <br>
 * LATT_NOSELECT - This is only a container,
 * not a mailbox - you cannot open it.
 * <br>
 * LATT_MARKED - This mailbox is marked.
 * Only used by UW-IMAPD.
 * <br>
 * LATT_UNMARKED - This mailbox is not marked.
 * Only used by UW-IMAPD.
 * <br>
 * LATT_REFERRAL - This container has a referral to a remote mailbox.
 * <br>
 * LATT_HASCHILDREN - This mailbox has selectable inferiors.
 * <br>
 * LATT_HASNOCHILDREN - This mailbox has no selectable inferiors.
 * </p>
 */
function imap_getsubscribed ($imap_stream, string $ref, string $pattern) {}

/**
 * Alias: imap_body
 * @link http://www.php.net/manual/en/function.imap-fetchtext.php
 * @param mixed $stream_id
 * @param mixed $msg_no
 * @param mixed $options [optional]
 */
function imap_fetchtext ($stream_id, $msg_no, $options = null) {}

/**
 * Alias: imap_listscan
 * @link http://www.php.net/manual/en/function.imap-scan.php
 * @param mixed $stream_id
 * @param mixed $ref
 * @param mixed $pattern
 * @param mixed $content
 */
function imap_scan ($stream_id, $ref, $pattern, $content) {}

/**
 * Alias: imap_createmailbox
 * @link http://www.php.net/manual/en/function.imap-create.php
 * @param mixed $stream_id
 * @param mixed $mailbox
 */
function imap_create ($stream_id, $mailbox) {}

/**
 * Alias: imap_renamemailbox
 * @link http://www.php.net/manual/en/function.imap-rename.php
 * @param mixed $stream_id
 * @param mixed $old_name
 * @param mixed $new_name
 */
function imap_rename ($stream_id, $old_name, $new_name) {}


/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('NIL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_OPENTIMEOUT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_READTIMEOUT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_WRITETIMEOUT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_CLOSETIMEOUT', 4);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_DEBUG', 1);

/**
 * Open mailbox read-only
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_READONLY', 2);

/**
 * Don't use or update a .newsrc for news 
 * (NNTP only)
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_ANONYMOUS', 4);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_SHORTCACHE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_SILENT', 16);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_PROTOTYPE', 32);

/**
 * For IMAP and NNTP
 * names, open a connection but don't open a mailbox.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_HALFOPEN', 64);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_EXPUNGE', 128);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('OP_SECURE', 256);

/**
 * silently expunge the mailbox before closing when
 * calling imap_close
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('CL_EXPUNGE', 32768);

/**
 * The parameter is a UID
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('FT_UID', 1);

/**
 * Do not set the \Seen flag if not already set
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('FT_PEEK', 2);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('FT_NOT', 4);

/**
 * The return string is in internal format, will not canonicalize to CRLF.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('FT_INTERNAL', 8);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('FT_PREFETCHTEXT', 32);

/**
 * The sequence argument contains UIDs instead of sequence numbers
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ST_UID', 1);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ST_SILENT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ST_SET', 4);

/**
 * the sequence numbers contain UIDS
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('CP_UID', 1);

/**
 * Delete the messages from the current mailbox after copying
 * with imap_mail_copy
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('CP_MOVE', 2);

/**
 * Return UIDs instead of sequence numbers
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SE_UID', 1);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SE_FREE', 2);

/**
 * Don't prefetch searched messages
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SE_NOPREFETCH', 4);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SO_FREE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SO_NOSERVER', 16);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SA_MESSAGES', 1);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SA_RECENT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SA_UNSEEN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SA_UIDNEXT', 8);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SA_UIDVALIDITY', 16);

/**
 * 
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SA_ALL', 31);

/**
 * This mailbox has no "children" (there are no
 * mailboxes below this one).
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_NOINFERIORS', 1);

/**
 * This is only a container, not a mailbox - you
 * cannot open it.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_NOSELECT', 2);

/**
 * This mailbox is marked. Only used by UW-IMAPD.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_MARKED', 4);

/**
 * This mailbox is not marked. Only used by
 * UW-IMAPD.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_UNMARKED', 8);

/**
 * This container has a referral to a remote mailbox.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_REFERRAL', 16);

/**
 * This mailbox has selectable inferiors.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_HASCHILDREN', 32);

/**
 * This mailbox has no selectable inferiors.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('LATT_HASNOCHILDREN', 64);

/**
 * Sort criteria for imap_sort:
 * message Date
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTDATE', 0);

/**
 * Sort criteria for imap_sort:
 * arrival date
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTARRIVAL', 1);

/**
 * Sort criteria for imap_sort:
 * mailbox in first From address
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTFROM', 2);

/**
 * Sort criteria for imap_sort:
 * message subject
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTSUBJECT', 3);

/**
 * Sort criteria for imap_sort:
 * mailbox in first To address
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTTO', 4);

/**
 * Sort criteria for imap_sort:
 * mailbox in first cc address
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTCC', 5);

/**
 * Sort criteria for imap_sort:
 * size of message in octets
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('SORTSIZE', 6);

/**
 * Primary body type: unformatted text
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPETEXT', 0);

/**
 * Primary body type: multiple part
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEMULTIPART', 1);

/**
 * Primary body type: encapsulated message
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEMESSAGE', 2);

/**
 * Primary body type: application data
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEAPPLICATION', 3);

/**
 * Primary body type: audio
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEAUDIO', 4);

/**
 * Primary body type: static image
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEIMAGE', 5);

/**
 * Primary body type: video
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEVIDEO', 6);

/**
 * Primary body type: model
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEMODEL', 7);

/**
 * Primary body type: unknown
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('TYPEOTHER', 8);

/**
 * Body encoding: 7 bit SMTP semantic data
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ENC7BIT', 0);

/**
 * Body encoding: 8 bit SMTP semantic data
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ENC8BIT', 1);

/**
 * Body encoding: 8 bit binary data
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ENCBINARY', 2);

/**
 * Body encoding: base-64 encoded data
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ENCBASE64', 3);

/**
 * Body encoding: human-readable 8-as-7 bit data
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ENCQUOTEDPRINTABLE', 4);

/**
 * Body encoding: unknown
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('ENCOTHER', 5);

/**
 * Garbage collector, clear message cache elements.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_GC_ELT', 1);

/**
 * Garbage collector, clear envelopes and bodies.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_GC_ENV', 2);

/**
 * Garbage collector, clear texts.
 * @link http://www.php.net/manual/en/imap.constants.php
 */
define ('IMAP_GC_TEXTS', 4);

// End of imap v.7.3.0
