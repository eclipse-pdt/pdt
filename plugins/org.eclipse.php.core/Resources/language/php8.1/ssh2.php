<?php

// Start of ssh2 v.1.4

/**
 * Connect to an SSH server
 * @link http://www.php.net/manual/en/function.ssh2-connect.php
 * @param string $host 
 * @param int $port [optional] 
 * @param array $methods [optional] <p>
 * methods may be an associative array with up to four parameters
 * as described below.
 * </p>
 * <p>
 * <table>
 * methods may be an associative array
 * with any or all of the following parameters.
 * <table>
 * <tr valign="top">
 * <td>Index</td>
 * <td>Meaning</td>
 * <td>Supported Values&#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>kex</td>
 * <td>
 * List of key exchange methods to advertise, comma separated
 * in order of preference.
 * </td>
 * <td>
 * diffie-hellman-group1-sha1,
 * diffie-hellman-group14-sha1, and
 * diffie-hellman-group-exchange-sha1
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>hostkey</td>
 * <td>
 * List of hostkey methods to advertise, comma separated
 * in order of preference.
 * </td>
 * <td>
 * ssh-rsa and
 * ssh-dss
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>client_to_server</td>
 * <td>
 * Associative array containing crypt, compression, and
 * message authentication code (MAC) method preferences
 * for messages sent from client to server.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>server_to_client</td>
 * <td>
 * Associative array containing crypt, compression, and
 * message authentication code (MAC) method preferences
 * for messages sent from server to client.
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * &#42; - Supported Values are dependent on methods supported by underlying library.
 * See libssh2 documentation for additional
 * information.
 * </p>
 * <p>
 * <table>
 * client_to_server and
 * server_to_client may be an associative array
 * with any or all of the following parameters.
 * <table>
 * <tr valign="top">
 * <td>Index</td>
 * <td>Meaning</td>
 * <td>Supported Values&#42;</td>
 * </tr>
 * <tr valign="top">
 * <td>crypt</td>
 * <td>List of crypto methods to advertise, comma separated
 * in order of preference.</td>
 * <td>
 * rijndael-cbc@lysator.liu.se,
 * aes256-cbc,
 * aes192-cbc,
 * aes128-cbc,
 * 3des-cbc,
 * blowfish-cbc,
 * cast128-cbc,
 * arcfour, and
 * none&#42;&#42;
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>comp</td>
 * <td>List of compression methods to advertise, comma separated
 * in order of preference.</td>
 * <td>
 * zlib and
 * none
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>mac</td>
 * <td>List of MAC methods to advertise, comma separated
 * in order of preference.</td>
 * <td>
 * hmac-sha1,
 * hmac-sha1-96,
 * hmac-ripemd160,
 * hmac-ripemd160@openssh.com, and
 * none&#42;&#42;
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * Crypt and MAC method "none"
 * <p>
 * For security reasons, none is disabled by the underlying
 * libssh2 library unless explicitly enabled
 * during build time by using the appropriate ./configure options. See documentation
 * for the underlying library for more information.
 * </p>
 * </p>
 * @param array $callbacks [optional] callbacks may be an associative array with any
 * or all of the following parameters.
 * <table>
 * Callbacks parameters
 * <table>
 * <tr valign="top">
 * <td>Index</td>
 * <td>Meaning</td>
 * <td>Prototype</td>
 * </tr>
 * <tr valign="top">
 * <td>ignore</td>
 * <td>
 * Name of function to call when an
 * SSH2_MSG_IGNORE packet is received
 * </td>
 * <td>void ignore_cb($message)</td>
 * </tr>
 * <tr valign="top">
 * <td>debug</td>
 * <td>
 * Name of function to call when an
 * SSH2_MSG_DEBUG packet is received
 * </td>
 * <td>void debug_cb($message, $language, $always_display)</td>
 * </tr>
 * <tr valign="top">
 * <td>macerror</td>
 * <td>
 * Name of function to call when a packet is received but the
 * message authentication code failed. If the callback returns
 * true, the mismatch will be ignored, otherwise the connection
 * will be terminated.
 * </td>
 * <td>bool macerror_cb($packet)</td>
 * </tr>
 * <tr valign="top">
 * <td>disconnect</td>
 * <td>
 * Name of function to call when an
 * SSH2_MSG_DISCONNECT packet is received
 * </td>
 * <td>void disconnect_cb($reason, $message, $language)</td>
 * </tr>
 * </table>
 * </table>
 * @return mixed a resource on success, or false on error.
 */
function ssh2_connect (string $host, int $port = null, array $methods = null, array $callbacks = null) {}

/**
 * Close a connection to a remote SSH server
 * @link http://www.php.net/manual/en/function.ssh2-disconnect.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @return bool true on success or false on failure
 */
function ssh2_disconnect ($session) {}

/**
 * Return list of negotiated methods
 * @link http://www.php.net/manual/en/function.ssh2-methods-negotiated.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @return array 
 */
function ssh2_methods_negotiated ($session) {}

/**
 * Retrieve fingerprint of remote server
 * @link http://www.php.net/manual/en/function.ssh2-fingerprint.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param int $flags [optional] flags may be either of
 * SSH2_FINGERPRINT_MD5 or
 * SSH2_FINGERPRINT_SHA1 logically ORed with
 * SSH2_FINGERPRINT_HEX or
 * SSH2_FINGERPRINT_RAW.
 * @return string the hostkey hash as a string.
 */
function ssh2_fingerprint ($session, int $flags = null) {}

/**
 * Authenticate as "none"
 * @link http://www.php.net/manual/en/function.ssh2-auth-none.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $username Remote user name.
 * @return mixed true if the server does accept "none" as an authentication
 * method, or an array of accepted authentication methods on failure.
 */
function ssh2_auth_none ($session, string $username) {}

/**
 * Authenticate over SSH using a plain password
 * @link http://www.php.net/manual/en/function.ssh2-auth-password.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $username Remote user name.
 * @param string $password Password for username
 * @return bool true on success or false on failure
 */
function ssh2_auth_password ($session, string $username, string $password) {}

/**
 * Authenticate using a public key
 * @link http://www.php.net/manual/en/function.ssh2-auth-pubkey-file.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $username 
 * @param string $pubkeyfile <p>
 * The public key file needs to be in OpenSSH's format. It should look something like:
 * </p>
 * <p>
 * ssh-rsa AAAAB3NzaC1yc2EAAA....NX6sqSnHA8= rsa-key-20121110
 * </p>
 * @param string $privkeyfile 
 * @param string $passphrase [optional] If privkeyfile is encrypted (which it should
 * be), the passphrase must be provided.
 * @return bool true on success or false on failure
 */
function ssh2_auth_pubkey_file ($session, string $username, string $pubkeyfile, string $privkeyfile, string $passphrase = null) {}

/**
 * @param mixed $session
 * @param mixed $username
 * @param mixed $pubkey
 * @param mixed $privkey
 * @param mixed $passphrase [optional]
 */
function ssh2_auth_pubkey ($session = null, $username = null, $pubkey = null, $privkey = null, $passphrase = null) {}

/**
 * Authenticate using a public hostkey
 * @link http://www.php.net/manual/en/function.ssh2-auth-hostbased-file.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $username 
 * @param string $hostname 
 * @param string $pubkeyfile 
 * @param string $privkeyfile 
 * @param string $passphrase [optional] If privkeyfile is encrypted (which it should
 * be), the passphrase must be provided.
 * @param string $local_username [optional] If local_username is omitted, then the value
 * for username will be used for it.
 * @return bool true on success or false on failure
 */
function ssh2_auth_hostbased_file ($session, string $username, string $hostname, string $pubkeyfile, string $privkeyfile, string $passphrase = null, string $local_username = null) {}

/**
 * Bind a port on the remote server and listen for connections
 * @link http://www.php.net/manual/en/function.ssh2-forward-listen.php
 * @param resource $session An SSH Session resource, obtained from a call to ssh2_connect.
 * @param int $port The port of the remote server.
 * @param string $host [optional] 
 * @param int $max_connections [optional] 
 * @return mixed an SSH2 Listener, or false on failure.
 */
function ssh2_forward_listen ($session, int $port, string $host = null, int $max_connections = null) {}

/**
 * Accept a connection created by a listener
 * @link http://www.php.net/manual/en/function.ssh2-forward-accept.php
 * @param resource $listener 
 * @return mixed a stream resource, or false on failure.
 */
function ssh2_forward_accept ($listener) {}

/**
 * Request an interactive shell
 * @link http://www.php.net/manual/en/function.ssh2-shell.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $termtype [optional] termtype should correspond to one of the
 * entries in the target system's /etc/termcap file.
 * @param mixed $env [optional] env may be passed as an associative array of
 * name/value pairs to set in the target environment.
 * @param int $width [optional] Width of the virtual terminal.
 * @param int $height [optional] Height of the virtual terminal.
 * @param int $width_height_type [optional] width_height_type should be one of
 * SSH2_TERM_UNIT_CHARS or
 * SSH2_TERM_UNIT_PIXELS.
 * @return mixed a stream resource on success, or false on failure.
 */
function ssh2_shell ($session, string $termtype = null, $env = null, int $width = null, int $height = null, int $width_height_type = null) {}

/**
 * Execute a command on a remote server
 * @link http://www.php.net/manual/en/function.ssh2-exec.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $command 
 * @param string $pty [optional] 
 * @param array $env [optional] env may be passed as an associative array of
 * name/value pairs to set in the target environment.
 * @param int $width [optional] Width of the virtual terminal.
 * @param int $height [optional] Height of the virtual terminal.
 * @param int $width_height_type [optional] width_height_type should be one of
 * SSH2_TERM_UNIT_CHARS or
 * SSH2_TERM_UNIT_PIXELS.
 * @return mixed a stream on success or false on failure.
 */
function ssh2_exec ($session, string $command, string $pty = null, array $env = null, int $width = null, int $height = null, int $width_height_type = null) {}

/**
 * Open a tunnel through a remote server
 * @link http://www.php.net/manual/en/function.ssh2-tunnel.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $host 
 * @param int $port 
 * @return resource 
 */
function ssh2_tunnel ($session, string $host, int $port) {}

/**
 * Request a file via SCP
 * @link http://www.php.net/manual/en/function.ssh2-scp-recv.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $remote_file Path to the remote file.
 * @param string $local_file Path to the local file.
 * @return bool true on success or false on failure
 */
function ssh2_scp_recv ($session, string $remote_file, string $local_file) {}

/**
 * Send a file via SCP
 * @link http://www.php.net/manual/en/function.ssh2-scp-send.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $local_file Path to the local file.
 * @param string $remote_file Path to the remote file.
 * @param int $create_mode [optional] The file will be created with the mode specified by
 * create_mode.
 * @return bool true on success or false on failure
 */
function ssh2_scp_send ($session, string $local_file, string $remote_file, int $create_mode = null) {}

/**
 * Fetch an extended data stream
 * @link http://www.php.net/manual/en/function.ssh2-fetch-stream.php
 * @param resource $channel 
 * @param int $streamid An SSH2 channel stream.
 * @return resource the requested stream resource.
 */
function ssh2_fetch_stream ($channel, int $streamid) {}

/**
 * Poll the channels/listeners/streams for events
 * @link http://www.php.net/manual/en/function.ssh2-poll.php
 * @param array $desc An indexed array of subarrays with the keys
 * 'resource' and 'events'.
 * The value of the resource is a (channel) stream or an SSH2 Listener resource.
 * The value of the event are SSH2_POLL&#42; flags bitwise ORed together.
 * Each subarray will be populated with an 'revents' element on return,
 * whose values are SSH2_POLL&#42; flags bitwise ORed together of the events that occurred.
 * @param int $timeout [optional] The timeout in seconds.
 * @return int the number of descriptors which returned non-zero revents.
 */
function ssh2_poll (array &$desc, int $timeout = null) {}

/**
 * Send EOF to stream
 * @link http://www.php.net/manual/en/function.ssh2-send-eof.php
 * @param resource $channel An SSH stream; can be acquired through functions like ssh2_fetch_stream
 * or ssh2_connect.
 * @return bool true on success or false on failure
 */
function ssh2_send_eof ($channel) {}

/**
 * @param mixed $session
 * @param mixed $width
 * @param mixed $height
 */
function ssh2_shell_resize ($session = null, $width = null, $height = null) {}

/**
 * Initialize SFTP subsystem
 * @link http://www.php.net/manual/en/function.ssh2-sftp.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @return mixed This method returns an SSH2 SFTP resource for use with
 * all other ssh2_sftp_&#42;() methods and the
 * ssh2.sftp:// fopen wrapper,
 * or false on failure.
 */
function ssh2_sftp ($session) {}

/**
 * Rename a remote file
 * @link http://www.php.net/manual/en/function.ssh2-sftp-rename.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $from The current file that is being renamed.
 * @param string $to The new file name that replaces from.
 * @return bool true on success or false on failure
 */
function ssh2_sftp_rename ($sftp, string $from, string $to) {}

/**
 * Delete a file
 * @link http://www.php.net/manual/en/function.ssh2-sftp-unlink.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $filename 
 * @return bool true on success or false on failure
 */
function ssh2_sftp_unlink ($sftp, string $filename) {}

/**
 * Create a directory
 * @link http://www.php.net/manual/en/function.ssh2-sftp-mkdir.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $dirname Path of the new directory.
 * @param int $mode [optional] Permissions on the new directory.
 * The actual mode is affected by the current umask.
 * @param bool $recursive [optional] If recursive is true any parent directories
 * required for dirname will be automatically created as well.
 * @return bool true on success or false on failure
 */
function ssh2_sftp_mkdir ($sftp, string $dirname, int $mode = null, bool $recursive = null) {}

/**
 * Remove a directory
 * @link http://www.php.net/manual/en/function.ssh2-sftp-rmdir.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $dirname 
 * @return bool true on success or false on failure
 */
function ssh2_sftp_rmdir ($sftp, string $dirname) {}

/**
 * Changes file mode
 * @link http://www.php.net/manual/en/function.ssh2-sftp-chmod.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $filename Path to the file.
 * @param int $mode Permissions on the file. See the chmod for more details on this parameter.
 * @return bool true on success or false on failure
 */
function ssh2_sftp_chmod ($sftp, string $filename, int $mode) {}

/**
 * Stat a file on a remote filesystem
 * @link http://www.php.net/manual/en/function.ssh2-sftp-stat.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $path 
 * @return array See the documentation for stat for details on the
 * values which may be returned.
 */
function ssh2_sftp_stat ($sftp, string $path) {}

/**
 * Stat a symbolic link
 * @link http://www.php.net/manual/en/function.ssh2-sftp-lstat.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $path Path to the remote symbolic link.
 * @return array See the documentation for stat for details on the
 * values which may be returned.
 */
function ssh2_sftp_lstat ($sftp, string $path) {}

/**
 * Create a symlink
 * @link http://www.php.net/manual/en/function.ssh2-sftp-symlink.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $target Target of the symbolic link.
 * @param string $link 
 * @return bool true on success or false on failure
 */
function ssh2_sftp_symlink ($sftp, string $target, string $link) {}

/**
 * Return the target of a symbolic link
 * @link http://www.php.net/manual/en/function.ssh2-sftp-readlink.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $link Path of the symbolic link.
 * @return string the target of the symbolic link.
 */
function ssh2_sftp_readlink ($sftp, string $link) {}

/**
 * Resolve the realpath of a provided path string
 * @link http://www.php.net/manual/en/function.ssh2-sftp-realpath.php
 * @param resource $sftp An SSH2 SFTP resource opened by ssh2_sftp.
 * @param string $filename 
 * @return string the real path as a string.
 */
function ssh2_sftp_realpath ($sftp, string $filename) {}

/**
 * Initialize Publickey subsystem
 * @link http://www.php.net/manual/en/function.ssh2-publickey-init.php
 * @param resource $session 
 * @return mixed an SSH2 Publickey Subsystem resource for use
 * with all other ssh2_publickey_&#42;() methods or false on failure.
 */
function ssh2_publickey_init ($session) {}

/**
 * Add an authorized publickey
 * @link http://www.php.net/manual/en/function.ssh2-publickey-add.php
 * @param resource $pkey Publickey Subsystem resource created by ssh2_publickey_init.
 * @param string $algoname Publickey algorithm (e.g.): ssh-dss, ssh-rsa
 * @param string $blob Publickey blob as raw binary data
 * @param bool $overwrite [optional] If the specified key already exists, should it be overwritten?
 * @param array $attributes [optional] Associative array of attributes to assign to this public key.
 * Refer to ietf-secsh-publickey-subsystem for a list of supported attributes.
 * To mark an attribute as mandatory, precede its name with an asterisk.
 * If the server is unable to support an attribute marked mandatory,
 * it will abort the add process.
 * @return bool true on success or false on failure
 */
function ssh2_publickey_add ($pkey, string $algoname, string $blob, bool $overwrite = null, array $attributes = null) {}

/**
 * Remove an authorized publickey
 * @link http://www.php.net/manual/en/function.ssh2-publickey-remove.php
 * @param resource $pkey Publickey Subsystem Resource
 * @param string $algoname Publickey algorithm (e.g.): ssh-dss, ssh-rsa
 * @param string $blob Publickey blob as raw binary data
 * @return bool true on success or false on failure
 */
function ssh2_publickey_remove ($pkey, string $algoname, string $blob) {}

/**
 * List currently authorized publickeys
 * @link http://www.php.net/manual/en/function.ssh2-publickey-list.php
 * @param resource $pkey Publickey Subsystem resource
 * @return array a numerically indexed array of keys,
 * each of which is an associative array containing:
 * name, blob, and attrs elements.
 * <p>
 * <table>
 * Publickey elements
 * <table>
 * <tr valign="top">
 * <td>Array Key</td>
 * <td>Meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>Name of algorithm used by this publickey, for example:
 * ssh-dss or ssh-rsa.</td>
 * </tr>
 * <tr valign="top">
 * <td>blob</td>
 * <td>Publickey blob as raw binary data.</td>
 * </tr>
 * <tr valign="top">
 * <td>attrs</td>
 * <td>Attributes assigned to this publickey. The most common
 * attribute, and the only one supported by publickey version 1
 * servers, is comment, which may be any freeform
 * string.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 */
function ssh2_publickey_list ($pkey) {}

/**
 * Authenticate over SSH using the ssh agent
 * @link http://www.php.net/manual/en/function.ssh2-auth-agent.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @param string $username Remote user name.
 * @return bool true on success or false on failure
 */
function ssh2_auth_agent ($session, string $username) {}


/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as an MD5 hash.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_FINGERPRINT_MD5', 0);

/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as an SHA1 hash.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_FINGERPRINT_SHA1', 1);

/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as a string of hexits.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_FINGERPRINT_HEX', 0);

/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as a raw string of 8-bit characters.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_FINGERPRINT_RAW', 2);

/**
 * Flag to ssh2_shell specifying that
 * width and height
 * are provided as character sizes.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_TERM_UNIT_CHARS', 0);

/**
 * Flag to ssh2_shell specifying that
 * width and height
 * are provided in pixel units.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_TERM_UNIT_PIXELS', 1);

/**
 * Default terminal type (e.g. vt102, ansi, xterm, vanilla) requested
 * by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_DEFAULT_TERMINAL', "vanilla");

/**
 * Default terminal width requested by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_DEFAULT_TERM_WIDTH', 80);

/**
 * Default terminal height requested by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_DEFAULT_TERM_HEIGHT', 25);

/**
 * Default terminal units requested by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_DEFAULT_TERM_UNIT', 0);

/**
 * Flag to ssh2_fetch_stream requesting STDIO subchannel.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_STREAM_STDIO', 0);

/**
 * Flag to ssh2_fetch_stream requesting STDERR subchannel.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_STREAM_STDERR', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLLIN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLLEXT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLLOUT', 4);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLLERR', 8);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLLHUP', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLLNVAL', 32);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLL_SESSION_CLOSED', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLL_CHANNEL_CLOSED', 128);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 */
define ('SSH2_POLL_LISTENER_CLOSED', 128);

// End of ssh2 v.1.4
