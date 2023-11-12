<?php

// Start of ssh2 v.1.4

/**
 * Connect to an SSH server
 * @link http://www.php.net/manual/en/function.ssh2-connect.php
 * @param string $host 
 * @param int $port [optional] 
 * @param array $methods [optional] 
 * @param array $callbacks [optional] 
 * @return resource|false Returns a resource on success, or false on error.
 */
function ssh2_connect (string $host, int $port = 22, array $methods = null, array $callbacks = null) {}

/**
 * Close a connection to a remote SSH server
 * @link http://www.php.net/manual/en/function.ssh2-disconnect.php
 * @param resource $session An SSH connection link identifier, obtained from a call to
 * ssh2_connect.
 * @return bool Returns true on success or false on failure.
 */
function ssh2_disconnect ($session): bool {}

/**
 * Return list of negotiated methods
 * @link http://www.php.net/manual/en/function.ssh2-methods-negotiated.php
 * @param resource $session 
 * @return array 
 */
function ssh2_methods_negotiated ($session): array {}

/**
 * Retrieve fingerprint of remote server
 * @link http://www.php.net/manual/en/function.ssh2-fingerprint.php
 * @param resource $session 
 * @param int $flags [optional] 
 * @return string Returns the hostkey hash as a string.
 */
function ssh2_fingerprint ($session, int $flags = 'SSH2_FINGERPRINT_MD5 | SSH2_FINGERPRINT_HEX'): string {}

/**
 * Authenticate as "none"
 * @link http://www.php.net/manual/en/function.ssh2-auth-none.php
 * @param resource $session 
 * @param string $username 
 * @return mixed Returns true if the server does accept "none" as an authentication
 * method, or an array of accepted authentication methods on failure.
 */
function ssh2_auth_none ($session, string $username): mixed {}

/**
 * Authenticate over SSH using a plain password
 * @link http://www.php.net/manual/en/function.ssh2-auth-password.php
 * @param resource $session 
 * @param string $username 
 * @param string $password 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_auth_password ($session, string $username, string $password): bool {}

/**
 * Authenticate using a public key
 * @link http://www.php.net/manual/en/function.ssh2-auth-pubkey-file.php
 * @param resource $session 
 * @param string $username 
 * @param string $pubkeyfile 
 * @param string $privkeyfile 
 * @param string $passphrase [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_auth_pubkey_file ($session, string $username, string $pubkeyfile, string $privkeyfile, string $passphrase = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $session
 * @param mixed $username
 * @param mixed $pubkey
 * @param mixed $privkey
 * @param mixed $passphrase [optional]
 */
function ssh2_auth_pubkey ($session = null, $username = null, $pubkey = null, $privkey = null, $passphrase = NULL) {}

/**
 * Authenticate using a public hostkey
 * @link http://www.php.net/manual/en/function.ssh2-auth-hostbased-file.php
 * @param resource $session 
 * @param string $username 
 * @param string $hostname 
 * @param string $pubkeyfile 
 * @param string $privkeyfile 
 * @param string $passphrase [optional] 
 * @param string $local_username [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_auth_hostbased_file ($session, string $username, string $hostname, string $pubkeyfile, string $privkeyfile, string $passphrase = null, string $local_username = null): bool {}

/**
 * Bind a port on the remote server and listen for connections
 * @link http://www.php.net/manual/en/function.ssh2-forward-listen.php
 * @param resource $session An SSH Session resource, obtained from a call to ssh2_connect.
 * @param int $port The port of the remote server.
 * @param string $host [optional] 
 * @param int $max_connections [optional] 
 * @return resource|false Returns an SSH2 Listener, or false on failure.
 */
function ssh2_forward_listen ($session, int $port, string $host = null, int $max_connections = 16) {}

/**
 * Accept a connection created by a listener
 * @link http://www.php.net/manual/en/function.ssh2-forward-accept.php
 * @param resource $listener 
 * @return resource|false Returns a stream resource, or false on failure.
 */
function ssh2_forward_accept ($listener) {}

/**
 * Request an interactive shell
 * @link http://www.php.net/manual/en/function.ssh2-shell.php
 * @param resource $session 
 * @param string $termtype [optional] 
 * @param array|null $env [optional] 
 * @param int $width [optional] 
 * @param int $height [optional] 
 * @param int $width_height_type [optional] 
 * @return resource|false Returns a stream resource on success, or false on failure.
 */
function ssh2_shell ($session, string $termtype = '"vanilla"', ?array $env = null, int $width = 80, int $height = 25, int $width_height_type = SSH2_TERM_UNIT_CHARS) {}

/**
 * Execute a command on a remote server
 * @link http://www.php.net/manual/en/function.ssh2-exec.php
 * @param resource $session 
 * @param string $command 
 * @param string $pty [optional] 
 * @param array $env [optional] 
 * @param int $width [optional] 
 * @param int $height [optional] 
 * @param int $width_height_type [optional] 
 * @return resource|false Returns a stream on success or false on failure.
 */
function ssh2_exec ($session, string $command, string $pty = null, array $env = null, int $width = 80, int $height = 25, int $width_height_type = SSH2_TERM_UNIT_CHARS) {}

/**
 * Open a tunnel through a remote server
 * @link http://www.php.net/manual/en/function.ssh2-tunnel.php
 * @param resource $session 
 * @param string $host 
 * @param int $port 
 * @return resource 
 */
function ssh2_tunnel ($session, string $host, int $port) {}

/**
 * Request a file via SCP
 * @link http://www.php.net/manual/en/function.ssh2-scp-recv.php
 * @param resource $session 
 * @param string $remote_file 
 * @param string $local_file 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_scp_recv ($session, string $remote_file, string $local_file): bool {}

/**
 * Send a file via SCP
 * @link http://www.php.net/manual/en/function.ssh2-scp-send.php
 * @param resource $session 
 * @param string $local_file 
 * @param string $remote_file 
 * @param int $create_mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_scp_send ($session, string $local_file, string $remote_file, int $create_mode = 0644): bool {}

/**
 * Fetch an extended data stream
 * @link http://www.php.net/manual/en/function.ssh2-fetch-stream.php
 * @param resource $channel 
 * @param int $streamid 
 * @return resource Returns the requested stream resource.
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
 * @return int Returns the number of descriptors which returned non-zero revents.
 */
function ssh2_poll (array &$desc, int $timeout = 30): int {}

/**
 * Send EOF to stream
 * @link http://www.php.net/manual/en/function.ssh2-send-eof.php
 * @param resource $channel An SSH stream; can be acquired through functions like ssh2_fetch_stream
 * or ssh2_connect.
 * @return bool Returns true on success or false on failure.
 */
function ssh2_send_eof ($channel): bool {}

/**
 * {@inheritdoc}
 * @param mixed $session
 * @param mixed $width
 * @param mixed $height
 */
function ssh2_shell_resize ($session = null, $width = null, $height = null) {}

/**
 * Initialize SFTP subsystem
 * @link http://www.php.net/manual/en/function.ssh2-sftp.php
 * @param resource $session 
 * @return resource|false This method returns an SSH2 SFTP resource for use with
 * all other ssh2_sftp_&#42;() methods and the
 * ssh2.sftp:// fopen wrapper,
 * or false on failure.
 */
function ssh2_sftp ($session) {}

/**
 * Rename a remote file
 * @link http://www.php.net/manual/en/function.ssh2-sftp-rename.php
 * @param resource $sftp 
 * @param string $from 
 * @param string $to 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_sftp_rename ($sftp, string $from, string $to): bool {}

/**
 * Delete a file
 * @link http://www.php.net/manual/en/function.ssh2-sftp-unlink.php
 * @param resource $sftp 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_sftp_unlink ($sftp, string $filename): bool {}

/**
 * Create a directory
 * @link http://www.php.net/manual/en/function.ssh2-sftp-mkdir.php
 * @param resource $sftp 
 * @param string $dirname 
 * @param int $mode [optional] 
 * @param bool $recursive [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_sftp_mkdir ($sftp, string $dirname, int $mode = 0777, bool $recursive = false): bool {}

/**
 * Remove a directory
 * @link http://www.php.net/manual/en/function.ssh2-sftp-rmdir.php
 * @param resource $sftp 
 * @param string $dirname 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_sftp_rmdir ($sftp, string $dirname): bool {}

/**
 * Changes file mode
 * @link http://www.php.net/manual/en/function.ssh2-sftp-chmod.php
 * @param resource $sftp 
 * @param string $filename 
 * @param int $mode 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_sftp_chmod ($sftp, string $filename, int $mode): bool {}

/**
 * Stat a file on a remote filesystem
 * @link http://www.php.net/manual/en/function.ssh2-sftp-stat.php
 * @param resource $sftp 
 * @param string $path 
 * @return array See the documentation for stat for details on the
 * values which may be returned.
 */
function ssh2_sftp_stat ($sftp, string $path): array {}

/**
 * Stat a symbolic link
 * @link http://www.php.net/manual/en/function.ssh2-sftp-lstat.php
 * @param resource $sftp 
 * @param string $path 
 * @return array See the documentation for stat for details on the
 * values which may be returned.
 */
function ssh2_sftp_lstat ($sftp, string $path): array {}

/**
 * Create a symlink
 * @link http://www.php.net/manual/en/function.ssh2-sftp-symlink.php
 * @param resource $sftp 
 * @param string $target 
 * @param string $link 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_sftp_symlink ($sftp, string $target, string $link): bool {}

/**
 * Return the target of a symbolic link
 * @link http://www.php.net/manual/en/function.ssh2-sftp-readlink.php
 * @param resource $sftp 
 * @param string $link 
 * @return string Returns the target of the symbolic link.
 */
function ssh2_sftp_readlink ($sftp, string $link): string {}

/**
 * Resolve the realpath of a provided path string
 * @link http://www.php.net/manual/en/function.ssh2-sftp-realpath.php
 * @param resource $sftp 
 * @param string $filename 
 * @return string Returns the real path as a string.
 */
function ssh2_sftp_realpath ($sftp, string $filename): string {}

/**
 * Initialize Publickey subsystem
 * @link http://www.php.net/manual/en/function.ssh2-publickey-init.php
 * @param resource $session 
 * @return resource|false Returns an SSH2 Publickey Subsystem resource for use
 * with all other ssh2_publickey_&#42;() methods or false on failure.
 */
function ssh2_publickey_init ($session) {}

/**
 * Add an authorized publickey
 * @link http://www.php.net/manual/en/function.ssh2-publickey-add.php
 * @param resource $pkey 
 * @param string $algoname 
 * @param string $blob 
 * @param bool $overwrite [optional] 
 * @param array $attributes [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_publickey_add ($pkey, string $algoname, string $blob, bool $overwrite = false, array $attributes = null): bool {}

/**
 * Remove an authorized publickey
 * @link http://www.php.net/manual/en/function.ssh2-publickey-remove.php
 * @param resource $pkey 
 * @param string $algoname 
 * @param string $blob 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_publickey_remove ($pkey, string $algoname, string $blob): bool {}

/**
 * List currently authorized publickeys
 * @link http://www.php.net/manual/en/function.ssh2-publickey-list.php
 * @param resource $pkey 
 * @return array Returns a numerically indexed array of keys,
 * each of which is an associative array containing:
 * name, blob, and attrs elements.
 * <p><table>
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
 * </table></p>
 */
function ssh2_publickey_list ($pkey): array {}

/**
 * Authenticate over SSH using the ssh agent
 * @link http://www.php.net/manual/en/function.ssh2-auth-agent.php
 * @param resource $session 
 * @param string $username 
 * @return bool Returns true on success or false on failure.
 */
function ssh2_auth_agent ($session, string $username): bool {}


/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as an MD5 hash.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_FINGERPRINT_MD5', 0);

/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as an SHA1 hash.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_FINGERPRINT_SHA1', 1);

/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as a string of hexits.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_FINGERPRINT_HEX', 0);

/**
 * Flag to ssh2_fingerprint requesting hostkey
 * fingerprint as a raw string of 8-bit characters.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_FINGERPRINT_RAW', 2);

/**
 * Flag to ssh2_shell specifying that
 * width and height
 * are provided as character sizes.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_TERM_UNIT_CHARS', 0);

/**
 * Flag to ssh2_shell specifying that
 * width and height
 * are provided in pixel units.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_TERM_UNIT_PIXELS', 1);

/**
 * Default terminal type (e.g. vt102, ansi, xterm, vanilla) requested
 * by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var string
 */
define ('SSH2_DEFAULT_TERMINAL', "vanilla");

/**
 * Default terminal width requested by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_DEFAULT_TERM_WIDTH', 80);

/**
 * Default terminal height requested by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_DEFAULT_TERM_HEIGHT', 25);

/**
 * Default terminal units requested by ssh2_shell.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_DEFAULT_TERM_UNIT', 0);

/**
 * Flag to ssh2_fetch_stream requesting STDIO subchannel.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_STREAM_STDIO', 0);

/**
 * Flag to ssh2_fetch_stream requesting STDERR subchannel.
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_STREAM_STDERR', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLLIN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLLEXT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLLOUT', 4);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLLERR', 8);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLLHUP', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLLNVAL', 32);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLL_SESSION_CLOSED', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLL_CHANNEL_CLOSED', 128);

/**
 * 
 * @link http://www.php.net/manual/en/ssh2.constants.php
 * @var int
 */
define ('SSH2_POLL_LISTENER_CLOSED', 128);

// End of ssh2 v.1.4
