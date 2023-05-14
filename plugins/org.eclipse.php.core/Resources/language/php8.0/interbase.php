<?php

// Start of interbase v.7.3.0

/**
 * Open a connection to a database
 * @link http://www.php.net/manual/en/function.ibase-connect.php
 * @param string $database [optional] The database argument has to be a valid path to
 * database file on the server it resides on. If the server is not local,
 * it must be prefixed with either 'hostname:' (TCP/IP), 'hostname/port:'
 * (TCP/IP with interbase server on custom TCP port), '//hostname/'
 * (NetBEUI), depending on the connection
 * protocol used.
 * @param string $username [optional] The user name. Can be set with the
 * ibase.default_user php.ini directive.
 * @param string $password [optional] The password for username. Can be set with the
 * ibase.default_password php.ini directive.
 * @param string $charset [optional] charset is the default character set for a
 * database.
 * @param int $buffers [optional] buffers is the number of database buffers to
 * allocate for the server-side cache. If 0 or omitted, server chooses
 * its own default.
 * @param int $dialect [optional] dialect selects the default SQL dialect for any
 * statement executed within a connection, and it defaults to the highest
 * one supported by client libraries.
 * @param string $role [optional] Functional only with InterBase 5 and up.
 * @param int $sync [optional] 
 * @return resource an Firebird/InterBase link identifier on success, or false on error.
 */
function ibase_connect (string $database = null, string $username = null, string $password = null, string $charset = null, int $buffers = null, int $dialect = null, string $role = null, int $sync = null) {}

/**
 * Open a persistent connection to an InterBase database
 * @link http://www.php.net/manual/en/function.ibase-pconnect.php
 * @param string $database [optional] The database argument has to be a valid path to
 * database file on the server it resides on. If the server is not local,
 * it must be prefixed with either 'hostname:' (TCP/IP), '//hostname/'
 * (NetBEUI) or 'hostname@' (IPX/SPX), depending on the connection
 * protocol used.
 * @param string $username [optional] The user name. Can be set with the
 * ibase.default_user php.ini directive.
 * @param string $password [optional] The password for username. Can be set with the
 * ibase.default_password php.ini directive.
 * @param string $charset [optional] charset is the default character set for a
 * database.
 * @param int $buffers [optional] buffers is the number of database buffers to
 * allocate for the server-side cache. If 0 or omitted, server chooses
 * its own default.
 * @param int $dialect [optional] dialect selects the default SQL dialect for any
 * statement executed within a connection, and it defaults to the highest
 * one supported by client libraries. Functional only with InterBase 6
 * and up.
 * @param string $role [optional] Functional only with InterBase 5 and up.
 * @param int $sync [optional] 
 * @return resource an InterBase link identifier on success, or false on error.
 */
function ibase_pconnect (string $database = null, string $username = null, string $password = null, string $charset = null, int $buffers = null, int $dialect = null, string $role = null, int $sync = null) {}

/**
 * Close a connection to an InterBase database
 * @link http://www.php.net/manual/en/function.ibase-close.php
 * @param resource $connection_id [optional] An InterBase link identifier returned from
 * ibase_connect. If omitted, the last opened link
 * is assumed.
 * @return bool true on success or false on failure
 */
function ibase_close ($connection_id = null) {}

/**
 * Drops a database
 * @link http://www.php.net/manual/en/function.ibase-drop-db.php
 * @param resource $connection [optional] An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @return bool true on success or false on failure
 */
function ibase_drop_db ($connection = null) {}

/**
 * Execute a query on an InterBase database
 * @link http://www.php.net/manual/en/function.ibase-query.php
 * @param resource $link_identifier [optional] An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @param string $query An InterBase query.
 * @param int $bind_args [optional] 
 * @return resource If the query raises an error, returns false. If it is successful and
 * there is a (possibly empty) result set (such as with a SELECT query),
 * returns a result identifier. If the query was successful and there were
 * no results, returns true.
 * <p>
 * In PHP 5.0.0 and up, this function will return the number of rows
 * affected by the query for INSERT, UPDATE and DELETE statements. In order
 * to retain backward compatibility, it will return true for these
 * statements if the query succeeded without affecting any rows.
 * </p>
 */
function ibase_query ($link_identifier = null, string $query, int $bind_args = null) {}

/**
 * Fetch a row from an InterBase database
 * @link http://www.php.net/manual/en/function.ibase-fetch-row.php
 * @param resource $result_identifier An InterBase result identifier.
 * @param int $fetch_flag [optional] fetch_flag is a combination of the constants
 * IBASE_TEXT and IBASE_UNIXTIME
 * ORed together. Passing IBASE_TEXT will cause this
 * function to return BLOB contents instead of BLOB ids. Passing
 * IBASE_UNIXTIME will cause this function to return
 * date/time values as Unix timestamps instead of as formatted strings.
 * @return array an array that corresponds to the fetched row, or false if there
 * are no more rows. Each result column is stored in an array offset,
 * starting at offset 0.
 */
function ibase_fetch_row ($result_identifier, int $fetch_flag = null) {}

/**
 * Fetch a result row from a query as an associative array
 * @link http://www.php.net/manual/en/function.ibase-fetch-assoc.php
 * @param resource $result The result handle.
 * @param int $fetch_flag [optional] fetch_flag is a combination of the constants
 * IBASE_TEXT and IBASE_UNIXTIME
 * ORed together. Passing IBASE_TEXT will cause this
 * function to return BLOB contents instead of BLOB ids. Passing
 * IBASE_UNIXTIME will cause this function to return
 * date/time values as Unix timestamps instead of as formatted strings.
 * @return array an associative array that corresponds to the fetched row.
 * Subsequent calls will return the next row in the result set, or false if
 * there are no more rows.
 */
function ibase_fetch_assoc ($result, int $fetch_flag = null) {}

/**
 * Get an object from a InterBase database
 * @link http://www.php.net/manual/en/function.ibase-fetch-object.php
 * @param resource $result_id An InterBase result identifier obtained either by
 * ibase_query or ibase_execute.
 * @param int $fetch_flag [optional] fetch_flag is a combination of the constants
 * IBASE_TEXT and IBASE_UNIXTIME
 * ORed together. Passing IBASE_TEXT will cause this
 * function to return BLOB contents instead of BLOB ids. Passing
 * IBASE_UNIXTIME will cause this function to return
 * date/time values as Unix timestamps instead of as formatted strings.
 * @return object an object with the next row information, or false if there are
 * no more rows.
 */
function ibase_fetch_object ($result_id, int $fetch_flag = null) {}

/**
 * Free a result set
 * @link http://www.php.net/manual/en/function.ibase-free-result.php
 * @param resource $result_identifier A result set created by ibase_query or
 * ibase_execute.
 * @return bool true on success or false on failure
 */
function ibase_free_result ($result_identifier) {}

/**
 * Assigns a name to a result set
 * @link http://www.php.net/manual/en/function.ibase-name-result.php
 * @param resource $result An InterBase result set.
 * @param string $name The name to be assigned.
 * @return bool true on success or false on failure
 */
function ibase_name_result ($result, string $name) {}

/**
 * Prepare a query for later binding of parameter placeholders and execution
 * @link http://www.php.net/manual/en/function.ibase-prepare.php
 * @param string $query An InterBase query.
 * @return resource a prepared query handle, or false on error.
 */
function ibase_prepare (string $query) {}

/**
 * Execute a previously prepared query
 * @link http://www.php.net/manual/en/function.ibase-execute.php
 * @param resource $query An InterBase query prepared by ibase_prepare.
 * @param mixed $bind_arg [optional] 
 * @param mixed $_ [optional] 
 * @return resource If the query raises an error, returns false. If it is successful and
 * there is a (possibly empty) result set (such as with a SELECT query),
 * returns a result identifier. If the query was successful and there were
 * no results, returns true.
 * <p>
 * This function returns the number of rows affected by
 * the query (if > 0 and applicable to the statement type). A query that
 * succeeded, but did not affect any rows (e.g. an UPDATE of a non-existent
 * record) will return true.
 * </p>
 */
function ibase_execute ($query, $bind_arg = null, $_ = null) {}

/**
 * Free memory allocated by a prepared query
 * @link http://www.php.net/manual/en/function.ibase-free-query.php
 * @param resource $query A query prepared with ibase_prepare.
 * @return bool true on success or false on failure
 */
function ibase_free_query ($query) {}

/**
 * Increments the named generator and returns its new value
 * @link http://www.php.net/manual/en/function.ibase-gen-id.php
 * @param string $generator 
 * @param int $increment [optional] 
 * @param resource $link_identifier [optional] 
 * @return mixed new generator value as integer, or as string if the value is too big.
 */
function ibase_gen_id (string $generator, int $increment = null, $link_identifier = null) {}

/**
 * Get the number of fields in a result set
 * @link http://www.php.net/manual/en/function.ibase-num-fields.php
 * @param resource $result_id An InterBase result identifier.
 * @return int the number of fields as an integer.
 */
function ibase_num_fields ($result_id) {}

/**
 * Return the number of parameters in a prepared query
 * @link http://www.php.net/manual/en/function.ibase-num-params.php
 * @param resource $query The prepared query handle.
 * @return int the number of parameters as an integer.
 */
function ibase_num_params ($query) {}

/**
 * Return the number of rows that were affected by the previous query
 * @link http://www.php.net/manual/en/function.ibase-affected-rows.php
 * @param resource $link_identifier [optional] A transaction context. If link_identifier is a
 * connection resource, its default transaction is used.
 * @return int the number of rows as an integer.
 */
function ibase_affected_rows ($link_identifier = null) {}

/**
 * Get information about a field
 * @link http://www.php.net/manual/en/function.ibase-field-info.php
 * @param resource $result An InterBase result identifier.
 * @param int $field_number Field offset.
 * @return array an array with the following keys: name,
 * alias, relation,
 * length and type.
 */
function ibase_field_info ($result, int $field_number) {}

/**
 * Return information about a parameter in a prepared query
 * @link http://www.php.net/manual/en/function.ibase-param-info.php
 * @param resource $query An InterBase prepared query handle.
 * @param int $param_number Parameter offset.
 * @return array an array with the following keys: name,
 * alias, relation,
 * length and type.
 */
function ibase_param_info ($query, int $param_number) {}

/**
 * Begin a transaction
 * @link http://www.php.net/manual/en/function.ibase-trans.php
 * @param int $trans_args [optional] trans_args can be a combination of
 * IBASE_READ,
 * IBASE_WRITE,
 * IBASE_COMMITTED, 
 * IBASE_CONSISTENCY,
 * IBASE_CONCURRENCY, 
 * IBASE_REC_VERSION, 
 * IBASE_REC_NO_VERSION,
 * IBASE_WAIT and 
 * IBASE_NOWAIT.
 * @param resource $link_identifier [optional] An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @return resource a transaction handle, or false on error.
 */
function ibase_trans (int $trans_args = null, $link_identifier = null) {}

/**
 * Commit a transaction
 * @link http://www.php.net/manual/en/function.ibase-commit.php
 * @param resource $link_or_trans_identifier [optional] If called without an argument, this function commits the default
 * transaction of the default link. If the argument is a connection
 * identifier, the default transaction of the corresponding connection
 * will be committed. If the argument is a transaction identifier, the
 * corresponding transaction will be committed.
 * @return bool true on success or false on failure
 */
function ibase_commit ($link_or_trans_identifier = null) {}

/**
 * Roll back a transaction
 * @link http://www.php.net/manual/en/function.ibase-rollback.php
 * @param resource $link_or_trans_identifier [optional] If called without an argument, this function rolls back the default
 * transaction of the default link. If the argument is a connection
 * identifier, the default transaction of the corresponding connection
 * will be rolled back. If the argument is a transaction identifier, the
 * corresponding transaction will be rolled back.
 * @return bool true on success or false on failure
 */
function ibase_rollback ($link_or_trans_identifier = null) {}

/**
 * Commit a transaction without closing it
 * @link http://www.php.net/manual/en/function.ibase-commit-ret.php
 * @param resource $link_or_trans_identifier [optional] If called without an argument, this function commits the default
 * transaction of the default link. If the argument is a connection
 * identifier, the default transaction of the corresponding connection
 * will be committed. If the argument is a transaction identifier, the
 * corresponding transaction will be committed. The transaction context
 * will be retained, so statements executed from within this transaction
 * will not be invalidated.
 * @return bool true on success or false on failure
 */
function ibase_commit_ret ($link_or_trans_identifier = null) {}

/**
 * Roll back a transaction without closing it
 * @link http://www.php.net/manual/en/function.ibase-rollback-ret.php
 * @param resource $link_or_trans_identifier [optional] If called without an argument, this function rolls back the default
 * transaction of the default link. If the argument is a connection
 * identifier, the default transaction of the corresponding connection
 * will be rolled back. If the argument is a transaction identifier, the
 * corresponding transaction will be rolled back. The transaction context
 * will be retained, so statements executed from within this transaction
 * will not be invalidated.
 * @return bool true on success or false on failure
 */
function ibase_rollback_ret ($link_or_trans_identifier = null) {}

/**
 * Return blob length and other useful info
 * @link http://www.php.net/manual/en/function.ibase-blob-info.php
 * @param resource $link_identifier An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @param string $blob_id A BLOB id.
 * @return array an array containing information about a BLOB. The information returned
 * consists of the length of the BLOB, the number of segments it contains, the size
 * of the largest segment, and whether it is a stream BLOB or a segmented BLOB.
 */
function ibase_blob_info ($link_identifier, string $blob_id) {}

/**
 * Create a new blob for adding data
 * @link http://www.php.net/manual/en/function.ibase-blob-create.php
 * @param resource $link_identifier [optional] An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @return resource a BLOB handle for later use with
 * ibase_blob_add or false on failure.
 */
function ibase_blob_create ($link_identifier = null) {}

/**
 * Add data into a newly created blob
 * @link http://www.php.net/manual/en/function.ibase-blob-add.php
 * @param resource $blob_handle A blob handle opened with ibase_blob_create.
 * @param string $data The data to be added.
 * @return void 
 */
function ibase_blob_add ($blob_handle, string $data) {}

/**
 * Cancel creating blob
 * @link http://www.php.net/manual/en/function.ibase-blob-cancel.php
 * @param resource $blob_handle A BLOB handle opened with ibase_blob_create.
 * @return bool true on success or false on failure
 */
function ibase_blob_cancel ($blob_handle) {}

/**
 * Close blob
 * @link http://www.php.net/manual/en/function.ibase-blob-close.php
 * @param resource $blob_handle A BLOB handle opened with ibase_blob_create or
 * ibase_blob_open.
 * @return mixed If the BLOB was being read, this function returns true on success, if
 * the BLOB was being written to, this function returns a string containing
 * the BLOB id that has been assigned to it by the database. On failure, this
 * function returns false.
 */
function ibase_blob_close ($blob_handle) {}

/**
 * Open blob for retrieving data parts
 * @link http://www.php.net/manual/en/function.ibase-blob-open.php
 * @param resource $link_identifier An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @param string $blob_id A BLOB id.
 * @return resource a BLOB handle for later use with 
 * ibase_blob_get or false on failure.
 */
function ibase_blob_open ($link_identifier, string $blob_id) {}

/**
 * Get len bytes data from open blob
 * @link http://www.php.net/manual/en/function.ibase-blob-get.php
 * @param resource $blob_handle A BLOB handle opened with ibase_blob_open.
 * @param int $len Size of returned data.
 * @return string at most len bytes from the BLOB, or false
 * on failure.
 */
function ibase_blob_get ($blob_handle, int $len) {}

/**
 * Output blob contents to browser
 * @link http://www.php.net/manual/en/function.ibase-blob-echo.php
 * @param string $blob_id 
 * @return bool true on success or false on failure
 */
function ibase_blob_echo (string $blob_id) {}

/**
 * Create blob, copy file in it, and close it
 * @link http://www.php.net/manual/en/function.ibase-blob-import.php
 * @param resource $link_identifier An InterBase link identifier. If omitted, the last opened link is
 * assumed.
 * @param resource $file_handle The file handle is a handle returned by fopen.
 * @return string the BLOB id on success, or false on error.
 */
function ibase_blob_import ($link_identifier, $file_handle) {}

/**
 * Return error messages
 * @link http://www.php.net/manual/en/function.ibase-errmsg.php
 * @return string the error message as a string, or false if no error occurred.
 */
function ibase_errmsg () {}

/**
 * Return an error code
 * @link http://www.php.net/manual/en/function.ibase-errcode.php
 * @return int the error code as an integer, or false if no error occurred.
 */
function ibase_errcode () {}

/**
 * Add a user to a security database
 * @link http://www.php.net/manual/en/function.ibase-add-user.php
 * @param resource $service_handle The handle on the database server service.
 * @param string $user_name The login name of the new database user.
 * @param string $password The password of the new user.
 * @param string $first_name [optional] The first name of the new database user.
 * @param string $middle_name [optional] The middle name of the new database user.
 * @param string $last_name [optional] The last name of the new database user.
 * @return bool true on success or false on failure
 */
function ibase_add_user ($service_handle, string $user_name, string $password, string $first_name = null, string $middle_name = null, string $last_name = null) {}

/**
 * Modify a user to a security database
 * @link http://www.php.net/manual/en/function.ibase-modify-user.php
 * @param resource $service_handle The handle on the database server service.
 * @param string $user_name The login name of the database user to modify.
 * @param string $password The user's new password.
 * @param string $first_name [optional] The user's new first name.
 * @param string $middle_name [optional] The user's new middle name.
 * @param string $last_name [optional] The user's new last name.
 * @return bool true on success or false on failure
 */
function ibase_modify_user ($service_handle, string $user_name, string $password, string $first_name = null, string $middle_name = null, string $last_name = null) {}

/**
 * Delete a user from a security database
 * @link http://www.php.net/manual/en/function.ibase-delete-user.php
 * @param resource $service_handle The handle on the database server service.
 * @param string $user_name The login name of the user you want to delete from the database.
 * @return bool true on success or false on failure
 */
function ibase_delete_user ($service_handle, string $user_name) {}

/**
 * Connect to the service manager
 * @link http://www.php.net/manual/en/function.ibase-service-attach.php
 * @param string $host 
 * @param string $dba_username 
 * @param string $dba_password 
 * @return resource 
 */
function ibase_service_attach (string $host, string $dba_username, string $dba_password) {}

/**
 * Disconnect from the service manager
 * @link http://www.php.net/manual/en/function.ibase-service-detach.php
 * @param resource $service_handle 
 * @return bool true on success or false on failure
 */
function ibase_service_detach ($service_handle) {}

/**
 * Initiates a backup task in the service manager and returns immediately
 * @link http://www.php.net/manual/en/function.ibase-backup.php
 * @param resource $service_handle 
 * @param string $source_db 
 * @param string $dest_file 
 * @param int $options [optional] 
 * @param bool $verbose [optional] 
 * @return mixed 
 */
function ibase_backup ($service_handle, string $source_db, string $dest_file, int $options = null, bool $verbose = null) {}

/**
 * Initiates a restore task in the service manager and returns immediately
 * @link http://www.php.net/manual/en/function.ibase-restore.php
 * @param resource $service_handle 
 * @param string $source_file 
 * @param string $dest_db 
 * @param int $options [optional] 
 * @param bool $verbose [optional] 
 * @return mixed 
 */
function ibase_restore ($service_handle, string $source_file, string $dest_db, int $options = null, bool $verbose = null) {}

/**
 * Execute a maintenance command on the database server
 * @link http://www.php.net/manual/en/function.ibase-maintain-db.php
 * @param resource $service_handle 
 * @param string $db 
 * @param int $action 
 * @param int $argument [optional] 
 * @return bool true on success or false on failure
 */
function ibase_maintain_db ($service_handle, string $db, int $action, int $argument = null) {}

/**
 * Request statistics about a database
 * @link http://www.php.net/manual/en/function.ibase-db-info.php
 * @param resource $service_handle 
 * @param string $db 
 * @param int $action 
 * @param int $argument [optional] 
 * @return string 
 */
function ibase_db_info ($service_handle, string $db, int $action, int $argument = null) {}

/**
 * Request information about a database server
 * @link http://www.php.net/manual/en/function.ibase-server-info.php
 * @param resource $service_handle 
 * @param int $action 
 * @return string 
 */
function ibase_server_info ($service_handle, int $action) {}

/**
 * Wait for an event to be posted by the database
 * @link http://www.php.net/manual/en/function.ibase-wait-event.php
 * @param string $event_name1 The event name.
 * @param string $event_name2 [optional] 
 * @param string $_ [optional] 
 * @return string the name of the event that was posted.
 */
function ibase_wait_event (string $event_name1, string $event_name2 = null, string $_ = null) {}

/**
 * Register a callback function to be called when events are posted
 * @link http://www.php.net/manual/en/function.ibase-set-event-handler.php
 * @param callable $event_handler <p>
 * The callback is called with the event name and the link resource as
 * arguments whenever one of the specified events is posted by the
 * database.
 * </p>
 * <p>
 * The callback must return false if the event handler should be
 * canceled. Any other return value is ignored. This function accepts up
 * to 15 event arguments.
 * </p>
 * @param string $event_name1 An event name.
 * @param string $event_name2 [optional] At most 15 events allowed.
 * @param string $_ [optional] 
 * @return resource The return value is an event resource. This resource can be used to free
 * the event handler using ibase_free_event_handler.
 */
function ibase_set_event_handler (callable $event_handler, string $event_name1, string $event_name2 = null, string $_ = null) {}

/**
 * Cancels a registered event handler
 * @link http://www.php.net/manual/en/function.ibase-free-event-handler.php
 * @param resource $event An event resource, created by
 * ibase_set_event_handler.
 * @return bool true on success or false on failure
 */
function ibase_free_event_handler ($event) {}

/**
 * @param mixed $database [optional]
 * @param mixed $username [optional]
 * @param mixed $password [optional]
 * @param mixed $charset [optional]
 * @param mixed $buffers [optional]
 * @param mixed $dialect [optional]
 * @param mixed $role [optional]
 */
function fbird_connect ($database = null, $username = null, $password = null, $charset = null, $buffers = null, $dialect = null, $role = null) {}

/**
 * @param mixed $database [optional]
 * @param mixed $username [optional]
 * @param mixed $password [optional]
 * @param mixed $charset [optional]
 * @param mixed $buffers [optional]
 * @param mixed $dialect [optional]
 * @param mixed $role [optional]
 */
function fbird_pconnect ($database = null, $username = null, $password = null, $charset = null, $buffers = null, $dialect = null, $role = null) {}

/**
 * @param mixed $link_identifier [optional]
 */
function fbird_close ($link_identifier = null) {}

/**
 * @param mixed $link_identifier [optional]
 */
function fbird_drop_db ($link_identifier = null) {}

/**
 * @param mixed $link_identifier [optional]
 * @param mixed $link_identifier [optional]
 * @param mixed $query [optional]
 * @param mixed $bind_arg [optional]
 * @param mixed $bind_arg [optional]
 */
function fbird_query ($link_identifier = null, $link_identifier = null, $query = null, $bind_arg = null, $bind_arg = null) {}

/**
 * @param mixed $result
 * @param mixed $fetch_flags [optional]
 */
function fbird_fetch_row ($result, $fetch_flags = null) {}

/**
 * @param mixed $result
 * @param mixed $fetch_flags [optional]
 */
function fbird_fetch_assoc ($result, $fetch_flags = null) {}

/**
 * @param mixed $result
 * @param mixed $fetch_flags [optional]
 */
function fbird_fetch_object ($result, $fetch_flags = null) {}

/**
 * @param mixed $result
 */
function fbird_free_result ($result) {}

/**
 * @param mixed $result
 * @param mixed $name
 */
function fbird_name_result ($result, $name) {}

/**
 * @param mixed $link_identifier [optional]
 * @param mixed $query [optional]
 */
function fbird_prepare ($link_identifier = null, $query = null) {}

/**
 * @param mixed $query
 * @param mixed $bind_arg [optional]
 * @param mixed $bind_arg [optional]
 */
function fbird_execute ($query, $bind_arg = null, $bind_arg = null) {}

/**
 * @param mixed $query
 */
function fbird_free_query ($query) {}

/**
 * @param mixed $generator
 * @param mixed $increment [optional]
 * @param mixed $link_identifier [optional]
 */
function fbird_gen_id ($generator, $increment = null, $link_identifier = null) {}

/**
 * @param mixed $query_result
 */
function fbird_num_fields ($query_result) {}

/**
 * @param mixed $query
 */
function fbird_num_params ($query) {}

/**
 * @param mixed $link_identifier [optional]
 */
function fbird_affected_rows ($link_identifier = null) {}

/**
 * @param mixed $query_result
 * @param mixed $field_number
 */
function fbird_field_info ($query_result, $field_number) {}

/**
 * @param mixed $query
 * @param mixed $field_number
 */
function fbird_param_info ($query, $field_number) {}

/**
 * @param mixed $trans_args [optional]
 * @param mixed $link_identifier [optional]
 * @param mixed $trans_args [optional]
 * @param mixed $link_identifier [optional]
 */
function fbird_trans ($trans_args = null, $link_identifier = null, $trans_args = null, $link_identifier = null) {}

/**
 * @param mixed $link_identifier
 */
function fbird_commit ($link_identifier) {}

/**
 * @param mixed $link_identifier
 */
function fbird_rollback ($link_identifier) {}

/**
 * @param mixed $link_identifier
 */
function fbird_commit_ret ($link_identifier) {}

/**
 * @param mixed $link_identifier
 */
function fbird_rollback_ret ($link_identifier) {}

/**
 * @param mixed $link_identifier [optional]
 * @param mixed $blob_id [optional]
 */
function fbird_blob_info ($link_identifier = null, $blob_id = null) {}

/**
 * @param mixed $link_identifier [optional]
 */
function fbird_blob_create ($link_identifier = null) {}

/**
 * @param mixed $blob_handle
 * @param mixed $data
 */
function fbird_blob_add ($blob_handle, $data) {}

/**
 * @param mixed $blob_handle
 */
function fbird_blob_cancel ($blob_handle) {}

/**
 * @param mixed $blob_handle
 */
function fbird_blob_close ($blob_handle) {}

/**
 * @param mixed $link_identifier [optional]
 * @param mixed $blob_id [optional]
 */
function fbird_blob_open ($link_identifier = null, $blob_id = null) {}

/**
 * @param mixed $blob_handle
 * @param mixed $len
 */
function fbird_blob_get ($blob_handle, $len) {}

/**
 * @param mixed $link_identifier [optional]
 * @param mixed $blob_id [optional]
 */
function fbird_blob_echo ($link_identifier = null, $blob_id = null) {}

/**
 * @param mixed $link_identifier [optional]
 * @param mixed $file [optional]
 */
function fbird_blob_import ($link_identifier = null, $file = null) {}

function fbird_errmsg () {}

function fbird_errcode () {}

/**
 * @param mixed $service_handle
 * @param mixed $user_name
 * @param mixed $password
 * @param mixed $first_name [optional]
 * @param mixed $middle_name [optional]
 * @param mixed $last_name [optional]
 */
function fbird_add_user ($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * @param mixed $service_handle
 * @param mixed $user_name
 * @param mixed $password
 * @param mixed $first_name [optional]
 * @param mixed $middle_name [optional]
 * @param mixed $last_name [optional]
 */
function fbird_modify_user ($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * @param mixed $service_handle
 * @param mixed $user_name
 * @param mixed $password
 * @param mixed $first_name [optional]
 * @param mixed $middle_name [optional]
 * @param mixed $last_name [optional]
 */
function fbird_delete_user ($service_handle, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * @param mixed $host
 * @param mixed $dba_username
 * @param mixed $dba_password
 */
function fbird_service_attach ($host, $dba_username, $dba_password) {}

/**
 * @param mixed $service_handle
 */
function fbird_service_detach ($service_handle) {}

/**
 * @param mixed $service_handle
 * @param mixed $source_db
 * @param mixed $dest_file
 * @param mixed $options [optional]
 * @param mixed $verbose [optional]
 */
function fbird_backup ($service_handle, $source_db, $dest_file, $options = null, $verbose = null) {}

/**
 * @param mixed $service_handle
 * @param mixed $source_file
 * @param mixed $dest_db
 * @param mixed $options [optional]
 * @param mixed $verbose [optional]
 */
function fbird_restore ($service_handle, $source_file, $dest_db, $options = null, $verbose = null) {}

/**
 * @param mixed $service_handle
 * @param mixed $db
 * @param mixed $action
 * @param mixed $argument [optional]
 */
function fbird_maintain_db ($service_handle, $db, $action, $argument = null) {}

/**
 * @param mixed $service_handle
 * @param mixed $db
 * @param mixed $action
 * @param mixed $argument [optional]
 */
function fbird_db_info ($service_handle, $db, $action, $argument = null) {}

/**
 * @param mixed $service_handle
 * @param mixed $action
 */
function fbird_server_info ($service_handle, $action) {}

/**
 * @param mixed $link_identifier
 * @param mixed $event [optional]
 * @param mixed $event2 [optional]
 */
function fbird_wait_event ($link_identifier, $event = null, $event2 = null) {}

/**
 * @param mixed $link_identifier
 * @param mixed $handler
 * @param mixed $event [optional]
 * @param mixed $event2 [optional]
 */
function fbird_set_event_handler ($link_identifier, $handler, $event = null, $event2 = null) {}

/**
 * @param mixed $event
 */
function fbird_free_event_handler ($event) {}

define ('IBASE_DEFAULT', 0);
define ('IBASE_CREATE', 0);
define ('IBASE_TEXT', 1);
define ('IBASE_FETCH_BLOBS', 1);
define ('IBASE_FETCH_ARRAYS', 2);
define ('IBASE_UNIXTIME', 4);
define ('IBASE_WRITE', 1);
define ('IBASE_READ', 2);
define ('IBASE_COMMITTED', 8);
define ('IBASE_CONSISTENCY', 16);
define ('IBASE_CONCURRENCY', 4);
define ('IBASE_REC_VERSION', 64);
define ('IBASE_REC_NO_VERSION', 32);
define ('IBASE_NOWAIT', 256);
define ('IBASE_WAIT', 128);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_IGNORE_CHECKSUMS', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_IGNORE_LIMBO', 2);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_METADATA_ONLY', 4);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_NO_GARBAGE_COLLECT', 8);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_OLD_DESCRIPTIONS', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_NON_TRANSPORTABLE', 32);

/**
 * Options to ibase_backup
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_BKP_CONVERT', 64);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_DEACTIVATE_IDX', 256);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_NO_SHADOW', 512);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_NO_VALIDITY', 1024);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_ONE_AT_A_TIME', 2048);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_REPLACE', 4096);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_CREATE', 8192);

/**
 * Options to ibase_restore
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RES_USE_ALL_SPACE', 16384);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_PAGE_BUFFERS', 5);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_SWEEP_INTERVAL', 6);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_SHUTDOWN_DB', 7);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_DENY_NEW_TRANSACTIONS', 10);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_DENY_NEW_ATTACHMENTS', 9);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_RESERVE_SPACE', 11);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_RES_USE_FULL', 35);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_RES', 36);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_WRITE_MODE', 12);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_WM_ASYNC', 37);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_WM_SYNC', 38);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_ACCESS_MODE', 13);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_AM_READONLY', 39);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_AM_READWRITE', 40);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_SET_SQL_DIALECT', 14);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_ACTIVATE', 256);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_PRP_DB_ONLINE', 512);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_CHECK_DB', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_IGNORE_CHECKSUM', 32);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_KILL_SHADOWS', 64);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_MEND_DB', 4);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_VALIDATE_DB', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_FULL', 128);

/**
 * Options to ibase_maintain_db
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_RPR_SWEEP_DB', 2);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_STS_DATA_PAGES', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_STS_DB_LOG', 2);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_STS_HDR_PAGES', 4);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_STS_IDX_PAGES', 8);

/**
 * Options to ibase_db_info
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_STS_SYS_RELATIONS', 16);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_SERVER_VERSION', 55);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_IMPLEMENTATION', 56);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_GET_ENV', 59);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_GET_ENV_LOCK', 60);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_GET_ENV_MSG', 61);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_USER_DBPATH', 58);

/**
 * 
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_SVR_DB_INFO', 50);

/**
 * Options to ibase_server_info
 * @link http://www.php.net/manual/en/ibase.constants.php
 */
define ('IBASE_SVC_GET_USERS', 68);

// End of interbase v.7.3.0
