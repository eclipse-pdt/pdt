<?php

// Start of mongo v.1.2.7

class Mongo  {
	const DEFAULT_HOST = "localhost";
	const DEFAULT_PORT = 27017;
	const VERSION = "1.2.7";

	public $connected;
	public $status;
	protected $server;
	protected $persistent;


	/**
	 * Creates a new database connection object
	 * @link http://www.php.net/manual/en/mongo.construct.php
	 */
	public function __construct () {}

	/**
	 * Connects to a database server
	 * @link http://www.php.net/manual/en/mongo.connect.php
	 * @return bool If the connection was successful.
	 */
	public function connect () {}

	public function pairConnect () {}

	public function persistConnect () {}

	public function pairPersistConnect () {}

	/**
	 * Connects with a database server
	 * @link http://www.php.net/manual/en/mongo.connectutil.php
	 * @return bool If the connection was successful.
	 */
	protected function connectUtil () {}

	/**
	 * String representation of this connection
	 * @link http://www.php.net/manual/en/mongo.tostring.php
	 * @return string hostname and port for this connection.
	 */
	public function __toString () {}

	/**
	 * Gets a database
	 * @link http://www.php.net/manual/en/mongo.get.php
	 * @param dbname string <p>
	 * The database name.
	 * </p>
	 * @return MongoDB a new db object.
	 */
	public function __get ($dbname) {}

	/**
	 * Gets a database
	 * @link http://www.php.net/manual/en/mongo.selectdb.php
	 * @param name string <p>
	 * The database name.
	 * </p>
	 * @return MongoDB a new db object.
	 */
	public function selectDB ($name) {}

	/**
	 * Gets a database collection
	 * @link http://www.php.net/manual/en/mongo.selectcollection.php
	 * @param db string <p>
	 * The database name.
	 * </p>
	 * @param collection string <p>
	 * The collection name.
	 * </p>
	 * @return MongoCollection a new collection object.
	 */
	public function selectCollection ($db, $collection) {}

	/**
	 * Get slaveOkay setting for this connection
	 * @link http://www.php.net/manual/en/mongo.getslaveokay.php
	 * @return bool the value of slaveOkay for this instance.
	 */
	public function getSlaveOkay () {}

	/**
	 * Change slaveOkay setting for this connection
	 * @link http://www.php.net/manual/en/mongo.setslaveokay.php
	 * @param ok bool[optional] <p>
	 * If reads should be sent to secondary members of a replica set for all 
	 * possible queries using this Mongo instance.
	 * </p>
	 * @return bool the former value of slaveOkay for this instance.
	 */
	public function setSlaveOkay ($ok = null) {}

	/**
	 * Drops a database [deprecated]
	 * @link http://www.php.net/manual/en/mongo.dropdb.php
	 * @param db mixed <p>
	 * The database to drop. Can be a MongoDB object or the name of the database.
	 * </p>
	 * @return array the database response.
	 */
	public function dropDB ($db) {}

	public function lastError () {}

	public function prevError () {}

	public function resetError () {}

	public function forceError () {}

	/**
	 * Lists all of the databases available.
	 * @link http://www.php.net/manual/en/mongo.listdbs.php
	 * @return array an associative array containing three fields. The first field is 
	 * databases, which in turn contains an array. Each element 
	 * of the array is an associative array corresponding to a database, giving the
	 * database's name, size, and if it's empty. The other two fields are 
	 * totalSize (in bytes) and ok, which is 1
	 * if this method ran successfully.
	 */
	public function listDBs () {}

	/**
	 * Updates status for all hosts associated with this
	 * @link http://www.php.net/manual/en/mongo.gethosts.php
	 * @return array an array of information about the hosts in the set. Includes each
	 * host's hostname, its health (1 is healthy), its state (1 is primary, 2 is
	 * secondary, 0 is anything else), the amount of time it took to ping the
	 * server, and when the last ping occurred. For example, on a three-member
	 * replica set, it might look something like:
	 * </p>
	 * array(4) {
	 * ["health"]=>
	 * int(1)
	 * ["state"]=>
	 * int(2)
	 * ["ping"]=>
	 * int(369)
	 * ["lastPing"]=>
	 * int(1309470644)
	 * }
	 * ["B:27017"]=>
	 * array(4) {
	 * ["health"]=>
	 * int(1)
	 * ["state"]=>
	 * int(1)
	 * ["ping"]=>
	 * int(139)
	 * ["lastPing"]=>
	 * int(1309470644)
	 * }
	 * ["C:27017"]=>
	 * array(4) {
	 * ["health"]=>
	 * int(1)
	 * ["state"]=>
	 * int(2)
	 * ["ping"]=>
	 * int(1012)
	 * ["lastPing"]=>
	 * int(1309470644)
	 * }
	 * }
	 * ]]>
	 * <p>
	 * In the example above, B and C are secondaries (state 2). B is likely to be
	 * selected for queries if slaveOkay is set, as it has a lower ping time (and
	 * thus is likely closer or handling less load) than C.
	 */
	public function getHosts () {}

	/**
	 * Returns the address being used by this for slaveOkay reads
	 * @link http://www.php.net/manual/en/mongo.getslave.php
	 * @return string The address of the slave this connection is using for reads.
	 * </p>
	 * <p>
	 * This returns &null; if this is not connected to a replica set or not yet
	 * initialized.
	 */
	public function getSlave () {}

	/**
	 * Choose a new slave for slaveOkay reads
	 * @link http://www.php.net/manual/en/mongo.switchslave.php
	 * @return string The address of the slave this connection is using for reads. This may be the
	 * same as the previous address as addresses are randomly chosen. It may return
	 * only one address if only one secondary (or only the primary) is available. 
	 * </p>
	 * <p>
	 * For example, if we had a three member replica set with a primary, secondary, 
	 * and arbiter this method would always return the address of the secondary. 
	 * If the secondary became unavailable, this method would always return the 
	 * address of the primary. If the primary also became unavailable, this method
	 * would throw an exception, as an arbiter cannot handle reads.
	 */
	public function switchSlave () {}

	/**
	 * Closes this connection
	 * @link http://www.php.net/manual/en/mongo.close.php
	 * @return bool if the connection was successfully closed.
	 */
	public function close () {}

	/**
	 * Set the size for future connection pools.
	 * @link http://www.php.net/manual/en/mongo.setpoolsize.php
	 * @param size int <p>
	 * The max number of connections future pools will be able to create.
	 * Negative numbers mean that the pool will spawn an infinite number of
	 * connections.
	 * </p>
	 * @return bool the former value of pool size.
	 */
	public static function setPoolSize ($size) {}

	/**
	 * Get pool size for connection pools
	 * @link http://www.php.net/manual/en/mongo.getpoolsize.php
	 * @return int the current pool size.
	 */
	public static function getPoolSize () {}

	/**
	 * Returns information about all connection pools.
	 * @link http://www.php.net/manual/en/mongo.pooldebug.php
	 * @return array Each connection pool has an identifier, which starts with the host. For each
	 * pool, this function shows the following fields:
	 * in use
	 * <p>
	 * The number of connections currently being used by
	 * Mongo instances.
	 * </p>
	 * in pool
	 * <p>
	 * The number of connections currently in the pool (not being used).
	 * </p>
	 * remaining
	 * <p>
	 * The number of connections that could be created by this pool. For
	 * example, suppose a pool had 5 connections remaining and 3 connections in
	 * the pool. We could create 8 new instances of
	 * Mongo before we exhausted this pool (assuming no
	 * instances of Mongo went out of scope, returning
	 * their connections to the pool).
	 * </p>
	 * <p>
	 * A negative number means that this pool will spawn unlimited connections.
	 * </p>
	 * <p>
	 * Before a pool is created, you can change the max number of connections by
	 * calling Mongo::setPoolSize. Once a pool is showing
	 * up in the output of this function, its size cannot be changed.
	 * </p>
	 * timeout
	 * <p>
	 * The socket timeout for connections in this pool. This is how long
	 * connections in this pool will attempt to connect to a server before
	 * giving up.
	 * </p>
	 */
	public static function poolDebug () {}

	public static function serverInfo () {}

}

class MongoDB  {
	const PROFILING_OFF = 0;
	const PROFILING_SLOW = 1;
	const PROFILING_ON = 2;

	public $w;
	public $wtimeout;


	/**
	 * Creates a new database
	 * @link http://www.php.net/manual/en/mongodb.construct.php
	 */
	public function __construct () {}

	/**
	 * The name of this database
	 * @link http://www.php.net/manual/en/mongodb.--tostring.php
	 * @return string this database&apos;s name.
	 */
	public function __toString () {}

	/**
	 * Gets a collection
	 * @link http://www.php.net/manual/en/mongodb.get.php
	 * @param name string <p>
	 * The name of the collection.
	 * </p>
	 * @return MongoCollection the collection.
	 */
	public function __get ($name) {}

	/**
	 * Fetches toolkit for dealing with files stored in this database
	 * @link http://www.php.net/manual/en/mongodb.getgridfs.php
	 * @param prefix string[optional] <p>
	 * The prefix for the files and chunks collections.
	 * </p>
	 * @return MongoGridFS a new gridfs object for this database.
	 */
	public function getGridFS ($prefix = null) {}

	/**
	 * Get slaveOkay setting for this database
	 * @link http://www.php.net/manual/en/mongodb.getslaveokay.php
	 * @return bool the value of slaveOkay for this instance.
	 */
	public function getSlaveOkay () {}

	/**
	 * Change slaveOkay setting for this database
	 * @link http://www.php.net/manual/en/mongodb.setslaveokay.php
	 * @param ok bool[optional] <p>
	 * If reads should be sent to secondary members of a replica set for all 
	 * possible queries using this MongoDB instance.
	 * </p>
	 * @return bool the former value of slaveOkay for this instance.
	 */
	public function setSlaveOkay ($ok = null) {}

	/**
	 * Gets this database&apos;s profiling level
	 * @link http://www.php.net/manual/en/mongodb.getprofilinglevel.php
	 * @return int the profiling level.
	 */
	public function getProfilingLevel () {}

	/**
	 * Sets this database&apos;s profiling level
	 * @link http://www.php.net/manual/en/mongodb.setprofilinglevel.php
	 * @param level int <p>
	 * Profiling level.
	 * </p>
	 * @return int the previous profiling level.
	 */
	public function setProfilingLevel ($level) {}

	/**
	 * Drops this database
	 * @link http://www.php.net/manual/en/mongodb.drop.php
	 * @return array the database response.
	 */
	public function drop () {}

	/**
	 * Repairs and compacts this database
	 * @link http://www.php.net/manual/en/mongodb.repair.php
	 * @param preserve_cloned_files bool[optional] <p>
	 * If cloned files should be kept if the repair fails.
	 * </p>
	 * @param backup_original_files bool[optional] <p>
	 * If original files should be backed up.
	 * </p>
	 * @return array db response.
	 */
	public function repair ($preserve_cloned_files = null, $backup_original_files = null) {}

	/**
	 * Gets a collection
	 * @link http://www.php.net/manual/en/mongodb.selectcollection.php
	 * @param name string <p>
	 * The name of the collection.
	 * </p>
	 * @return MongoCollection the collection.
	 */
	public function selectCollection ($name) {}

	/**
	 * Creates a collection
	 * @link http://www.php.net/manual/en/mongodb.createcollection.php
	 * @param name string <p>
	 * The name of the collection.
	 * </p>
	 * @param capped bool[optional] <p>
	 * If the collection should be a fixed size.
	 * </p>
	 * @param size int[optional] <p>
	 * If the collection is fixed size, its size in bytes.
	 * </p>
	 * @param max int[optional] <p>
	 * If the collection is fixed size, the maximum number of elements to store in the collection.
	 * </p>
	 * @return MongoCollection a collection object representing the new collection.
	 */
	public function createCollection ($name, $capped = null, $size = null, $max = null) {}

	/**
	 * Drops a collection [deprecated]
	 * @link http://www.php.net/manual/en/mongodb.dropcollection.php
	 * @param coll mixed <p>
	 * MongoCollection or name of collection to drop.
	 * </p>
	 * @return array the database response.
	 */
	public function dropCollection ($coll) {}

	/**
	 * Get a list of collections in this database
	 * @link http://www.php.net/manual/en/mongodb.listcollections.php
	 * @return array a list of MongoCollections.
	 */
	public function listCollections () {}

	/**
	 * Creates a database reference
	 * @link http://www.php.net/manual/en/mongodb.createdbref.php
	 * @param collection string <p>
	 * The collection to which the database reference will point.
	 * </p>
	 * @param a mixed <p>
	 * Object or _id to which to create a reference. If an object or
	 * associative array is given, this will create a reference using
	 * the _id field.
	 * </p>
	 * @return array a database reference array.
	 */
	public function createDBRef ($collection, $a) {}

	/**
	 * Fetches the document pointed to by a database reference
	 * @link http://www.php.net/manual/en/mongodb.getdbref.php
	 * @param ref array <p>
	 * A database reference.
	 * </p>
	 * @return array the document pointed to by the reference.
	 */
	public function getDBRef (array $ref) {}

	/**
	 * Runs JavaScript code on the database server.
	 * @link http://www.php.net/manual/en/mongodb.execute.php
	 * @param code mixed <p>
	 * MongoCode or string to execute.
	 * </p>
	 * @param args array[optional] <p>
	 * Arguments to be passed to code.
	 * </p>
	 * @return array the result of the evaluation.
	 */
	public function execute ($code, array $args = null) {}

	/**
	 * Execute a database command
	 * @link http://www.php.net/manual/en/mongodb.command.php
	 * @param command array <p>
	 * The query to send.
	 * </p>
	 * @param options array[optional] <p>
	 * This parameter is an associative array of the form 
	 * array("optionname" => &lt;boolean&gt;, ...). Currently 
	 * supported options are: 
	 * <p>
	 * "timeout"
	 * </p>
	 * <p>
	 * Integer, defaults to Mongo::$timeout. If 
	 * "safe" is set, this sets how long (in milliseconds) for the client to
	 * wait for a database response. If the database does not respond within
	 * the timeout period, a MongoCursorTimeoutException
	 * will be thrown.
	 * </p>
	 * @return array database response.
	 */
	public function command (array $command, array $options = null) {}

	/**
	 * Check if there was an error on the most recent db operation performed
	 * @link http://www.php.net/manual/en/mongodb.lasterror.php
	 * @return array the error, if there was one.
	 */
	public function lastError () {}

	/**
	 * Checks for the last error thrown during a database operation
	 * @link http://www.php.net/manual/en/mongodb.preverror.php
	 * @return array the error and the number of operations ago it occurred.
	 */
	public function prevError () {}

	/**
	 * Clears any flagged errors on the database
	 * @link http://www.php.net/manual/en/mongodb.reseterror.php
	 * @return array the database response.
	 */
	public function resetError () {}

	/**
	 * Creates a database error
	 * @link http://www.php.net/manual/en/mongodb.forceerror.php
	 * @return bool the database response.
	 */
	public function forceError () {}

	/**
	 * Log in to this database
	 * @link http://www.php.net/manual/en/mongodb.authenticate.php
	 * @param username string <p>
	 * The username.
	 * </p>
	 * @param password string <p>
	 * The password (in plaintext).
	 * </p>
	 * @return array database response. If the login was successful, it will return
	 * 1);
	 * ?>
	 * ]]>
	 * If something went wrong, it will return
	 * 0, "errmsg" => "auth fails");
	 * ?>
	 * ]]>
	 * ("auth fails" could be another message, depending on database version and what
	 * when wrong).
	 */
	public function authenticate ($username, $password) {}

}

class MongoCollection  {
	const ASCENDING = 1;
	const DESCENDING = -1;

	public $w;
	public $wtimeout;


	/**
	 * Creates a new collection
	 * @link http://www.php.net/manual/en/mongocollection.construct.php
	 */
	public function __construct () {}

	/**
	 * String representation of this collection
	 * @link http://www.php.net/manual/en/mongocollection.--tostring.php
	 * @return string the full name of this collection.
	 */
	public function __toString () {}

	/**
	 * Gets a collection
	 * @link http://www.php.net/manual/en/mongocollection.get.php
	 * @param name string <p>
	 * The next string in the collection name.
	 * </p>
	 * @return MongoCollection the collection.
	 */
	public function __get ($name) {}

	/**
	 * Returns this collection&apos;s name
	 * @link http://www.php.net/manual/en/mongocollection.getname.php
	 * @return string the name of this collection.
	 */
	public function getName () {}

	/**
	 * Get slaveOkay setting for this collection
	 * @link http://www.php.net/manual/en/mongocollection.getslaveokay.php
	 * @return bool the value of slaveOkay for this instance.
	 */
	public function getSlaveOkay () {}

	/**
	 * Change slaveOkay setting for this collection
	 * @link http://www.php.net/manual/en/mongocollection.setslaveokay.php
	 * @param ok bool[optional] <p>
	 * If reads should be sent to secondary members of a replica set for all 
	 * possible queries using this MongoCollection 
	 * instance.
	 * </p>
	 * @return bool the former value of slaveOkay for this instance.
	 */
	public function setSlaveOkay ($ok = null) {}

	/**
	 * Drops this collection
	 * @link http://www.php.net/manual/en/mongocollection.drop.php
	 * @return array the database response.
	 */
	public function drop () {}

	/**
	 * Validates this collection
	 * @link http://www.php.net/manual/en/mongocollection.validate.php
	 * @param scan_data bool[optional] <p>
	 * Only validate indices, not the base collection.
	 * </p>
	 * @return array the database&apos;s evaluation of this object.
	 */
	public function validate ($scan_data = null) {}

	/**
	 * Inserts an array into the collection
	 * @link http://www.php.net/manual/en/mongocollection.insert.php
	 * @param a array <p>
	 * An array.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the insert.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Can be a boolean or integer, defaults to false. If false, the 
	 * program continues executing without waiting for a database response. 
	 * If true, the program will wait for the database response and throw a
	 * MongoCursorException if the insert did not 
	 * succeed. 
	 * </p>
	 * <p>
	 * If you are using replication and the master has changed, using "safe" 
	 * will make the driver disconnect from the master, throw an exception, 
	 * and attempt to find a new master on the next operation (your 
	 * application must decide whether or not to retry the operation on the
	 * new master). 
	 * </p>
	 * <p>
	 * If you do not use "safe" with a replica set and 
	 * the master changes, there will be no way for the driver to know about 
	 * the change so it will continuously and silently fail to write.
	 * </p>
	 * <p>
	 * If safe is an integer, will replicate the
	 * insert to that many machines before returning success (or throw an
	 * exception if the replication times out, see wtimeout). This overrides
	 * the w variable set on the collection.
	 * </p>
	 * @return bool|array If safe was set, returns an array containing the
	 * status of the insert. Otherwise, returns a boolean representing if the
	 * array was not empty (an empty array will not be inserted).
	 * </p>
	 * <p>
	 * If an array is returned, the following keys can be present:
	 * ok
	 * <p>
	 * This should almost be 1 (unless last_error itself failed).
	 * </p>
	 * err
	 * <p>
	 * If this field is non-null, an error occurred on the previous operation.
	 * If this field is set, it will be a string describing the error that
	 * occurred. 
	 * </p>
	 * code
	 * <p>
	 * If a database error occurred, the relevant error code will be passed
	 * back to the client. 
	 * </p>
	 * errmsg
	 * <p>
	 * This field is set if something goes wrong with a database command. It
	 * is coupled with ok being 0. For example, if
	 * w is set and times out, errmsg will be set to "timed
	 * out waiting for slaves" and ok will be 0. If this
	 * field is set, it will be a string describing the error that occurred. 
	 * </p>
	 * n
	 * <p>
	 * If the last operation was an insert, an update or a remove, the number
	 * of objects affected will be returned. 
	 * </p>
	 * wtimeout
	 * <p>
	 * If the previous option timed out waiting for replication. 
	 * </p>
	 * waiter
	 * <p>
	 * How long the operation waited before timing out. 
	 * </p>
	 * wtime
	 * <p>
	 * If w was set and the operation succeeded, how long it took to
	 * replicate to w servers. 
	 * </p>
	 * upserted
	 * <p>
	 * If an upsert occured, this field will contain the new record's
	 * _id field. For upserts, either this field or
	 * updatedExisting will be present (unless an error
	 * occurred). 
	 * </p>
	 * updatedExisting
	 * <p>
	 * If an upsert updated an existing element, this field will be true. For
	 * upserts, either this field or upserted will be present (unless an error
	 * occurred). 
	 * </p>
	 */
	public function insert (array $a, array $options = null) {}

	/**
	 * Inserts multiple documents into this collection
	 * @link http://www.php.net/manual/en/mongocollection.batchinsert.php
	 * @param a array <p>
	 * An array of arrays.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the inserts.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Can be a boolean or integer, defaults to false. If false, the 
	 * program continues executing without waiting for a database response. 
	 * If true, the program will wait for the database response and throw a
	 * MongoCursorException if the insert did not 
	 * succeed. 
	 * </p>
	 * <p>
	 * If safe is an integer, will replicate the
	 * insert to that many machines before returning success (or throw an
	 * exception if the replication times out, see wtimeout). This overrides
	 * the w variable set on the collection.
	 * </p>
	 * @return mixed If "safe" is set, returns an associative array with the status of the inserts
	 * ("ok") and any error that may have occured ("err"). Otherwise, returns 
	 * true if the batch insert was successfully sent, false otherwise.
	 */
	public function batchInsert (array $a, array $options = null) {}

	/**
	 * Update records based on a given criteria
	 * @link http://www.php.net/manual/en/mongocollection.update.php
	 * @param criteria array <p>
	 * Description of the objects to update.
	 * </p>
	 * @param new_object array <p>
	 * The object with which to update the matching records.
	 * </p>
	 * @param options array[optional] <p>
	 * This parameter is an associative array of the form 
	 * array("optionname" => &lt;boolean&gt;, ...). Currently 
	 * supported options are: 
	 * <p>
	 * "upsert"
	 * </p>
	 * <p>
	 * If no document matches $criteria, a new
	 * document will be created from $criteria and
	 * $new_object (see upsert example below).
	 * </p>
	 * @return bool|array If safe was set, returns an array containing the
	 * status of the update. Otherwise, returns a boolean representing if the
	 * array was not empty (an empty array will not be inserted). The fields in
	 * this array are decribed in the documentation for
	 * MongoCollection::insert.
	 */
	public function update (array $criteria, array $new_object, array $options = null) {}

	/**
	 * Remove records from this collection
	 * @link http://www.php.net/manual/en/mongocollection.remove.php
	 * @param criteria array[optional] <p>
	 * Description of records to remove.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for remove.
	 * <p>
	 * "justOne"
	 * </p>
	 * <p>
	 * Remove at most one record matching this criteria.
	 * </p>
	 * @return bool|array If safe was set, returns an array containing the
	 * status of the remove. Otherwise, returns a boolean representing if the
	 * array was not empty (an empty array will not be inserted). The fields in
	 * this array are decribed in the documentation for
	 * MongoCollection::insert.
	 */
	public function remove (array $criteria = null, array $options = null) {}

	/**
	 * Querys this collection, returning a <classname>MongoCursor</classname>
  for the result set
	 * @link http://www.php.net/manual/en/mongocollection.find.php
	 * @param query array[optional] <p>
	 * The fields for which to search. MongoDB's query language is quite
	 * extensive. The PHP driver will in almost all cases pass the query
	 * straight through to the server, so reading the MongoDB core docs on
	 * find is a good idea.
	 * </p>
	 * <p>
	 * Please make sure that for all special query operaters (starting with
	 * $) you use single quotes so that PHP doesn't try to
	 * replace "$exists" with the value of the variable
	 * $exists.
	 * </p>
	 * @param fields array[optional] <p>
	 * Fields of the results to return. The array is in the format
	 * array('fieldname' => true, 'fieldname2' => true).
	 * The _id field is always returned.
	 * </p>
	 * @return MongoCursor a cursor for the search results.
	 */
	public function find (array $query = null, array $fields = null) {}

	/**
	 * Querys this collection, returning a single element
	 * @link http://www.php.net/manual/en/mongocollection.findone.php
	 * @param query array[optional] <p>
	 * The fields for which to search. MongoDB's query language is quite
	 * extensive. The PHP driver will in almost all cases pass the query
	 * straight through to the server, so reading the MongoDB core docs on
	 * find is a good idea.
	 * </p>
	 * <p>
	 * Please make sure that for all special query operaters (starting with
	 * $) you use single quotes so that PHP doesn't try to
	 * replace "$exists" with the value of the variable
	 * $exists.
	 * </p>
	 * @param fields array[optional] <p>
	 * Fields of the results to return. The array is in the format
	 * array('fieldname' => true, 'fieldname2' => true).
	 * The _id field is always returned.
	 * </p>
	 * @return array record matching the search or &null;.
	 */
	public function findOne (array $query = null, array $fields = null) {}

	/**
	 * Creates an index on the given field(s), or does nothing if the index 
   already exists
	 * @link http://www.php.net/manual/en/mongocollection.ensureindex.php
	 * @param key_keys string|array 
	 * @param options array[optional] <p>
	 * This parameter is an associative array of the form 
	 * array("optionname" => &lt;boolean&gt;, ...). Currently 
	 * supported options are: 
	 * <p>
	 * "unique"
	 * </p>
	 * <p>
	 * Create a unique index.
	 * </p>
	 * <p>
	 * A unique index cannot be created on a field if multiple existing documents do
	 * not contain the field. The field is effectively &null; for these documents 
	 * and thus already non-unique.
	 * </p>
	 * @return bool true.
	 */
	public function ensureIndex ($key_keys, array $options = null) {}

	/**
	 * Deletes an index from this collection
	 * @link http://www.php.net/manual/en/mongocollection.deleteindex.php
	 * @param keys string|array <p>
	 * Field or fields from which to delete the index.
	 * </p>
	 * @return array the generated name of the key if successful, or &null; otherwise.
	 */
	public function deleteIndex ($keys) {}

	/**
	 * Delete all indices for this collection
	 * @link http://www.php.net/manual/en/mongocollection.deleteindexes.php
	 * @return array the database response.
	 */
	public function deleteIndexes () {}

	/**
	 * Returns information about indexes on this collection
	 * @link http://www.php.net/manual/en/mongocollection.getindexinfo.php
	 * @return array This function returns an array in which each elements describes an array.
	 * The elements contain the values name for the name of
	 * the index, ns for the namespace (the name of the 
	 * collection), key containing a list of all the keys
	 * and their sort order that make up the index and _id
	 * containing a MongoID object with the ID of this index.
	 */
	public function getIndexInfo () {}

	/**
	 * Counts the number of documents in this collection
	 * @link http://www.php.net/manual/en/mongocollection.count.php
	 * @param query array[optional] <p>
	 * Associative array or object with fields to match.
	 * </p>
	 * @param limit int[optional] <p>
	 * Specifies an upper limit to the number returned.
	 * </p>
	 * @param skip int[optional] <p>
	 * Specifies a number of results to skip before starting the count.
	 * </p>
	 * @return int the number of documents matching the query.
	 */
	public function count (array $query = null, $limit = null, $skip = null) {}

	/**
	 * Saves an object to this collection
	 * @link http://www.php.net/manual/en/mongocollection.save.php
	 * @param a array <p>
	 * Array to save.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the save.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Can be a boolean or integer, defaults to false. If false, the 
	 * program continues executing without waiting for a database response. 
	 * If true, the program will wait for the database response and throw a
	 * MongoCursorException if the insert did not 
	 * succeed. 
	 * </p>
	 * <p>
	 * If you are using replication and the master has changed, using "safe" 
	 * will make the driver disconnect from the master, throw and exception, 
	 * and attempt to find a new master on the next operation (your 
	 * application must decide whether or not to retry the operation on the
	 * new master). 
	 * </p>
	 * <p>
	 * If you do not use "safe" with a replica set and 
	 * the master changes, there will be no way for the driver to know about 
	 * the change so it will continuously and silently fail to write.
	 * </p>
	 * <p>
	 * If safe is an integer, will replicate the
	 * insert to that many machines before returning success (or throw an
	 * exception if the replication times out, see wtimeout). This overrides
	 * the w variable set on the collection.
	 * </p>
	 * @return mixed If safe was set, returns an array containing the status of the save.
	 * Otherwise, returns a boolean representing if the array was not empty (an empty array will not 
	 * be inserted).
	 */
	public function save (array $a, array $options = null) {}

	/**
	 * Creates a database reference
	 * @link http://www.php.net/manual/en/mongocollection.createdbref.php
	 * @param a array <p>
	 * Object to which to create a reference.
	 * </p>
	 * @return array a database reference array.
	 */
	public function createDBRef (array $a) {}

	/**
	 * Fetches the document pointed to by a database reference
	 * @link http://www.php.net/manual/en/mongocollection.getdbref.php
	 * @param ref array <p>
	 * A database reference.
	 * </p>
	 * @return array the database document pointed to by the reference.
	 */
	public function getDBRef (array $ref) {}

	/**
	 * Converts keys specifying an index to its identifying string
	 * @link http://www.php.net/manual/en/mongocollection.toindexstring.php
	 * @param keys mixed <p>
	 * Field or fields to convert to the identifying string
	 * </p>
	 * @return string a string that describes the index.
	 */
	protected static function toIndexString ($keys) {}

	/**
	 * Performs an operation similar to SQL's GROUP BY command
	 * @link http://www.php.net/manual/en/mongocollection.group.php
	 * @param keys mixed <p>
	 * Fields to group by. If an array or non-code object is passed, it will be
	 * the key used to group results. 
	 * </p>
	 * <p>1.0.4+: If keys is an instance of 
	 * MongoCode, keys will be treated as
	 * a function that returns the key to group by (see the "Passing a 
	 * keys function" example below). 
	 * </p>
	 * @param initial array <p>
	 * Initial value of the aggregation counter object.
	 * </p>
	 * @param reduce MongoCode <p>
	 * A function that takes two arguments (the current document and the 
	 * aggregation to this point) and does the aggregation.
	 * </p>
	 * @param options array[optional] <p>
	 * Optional parameters to the group command. Valid options include:
	 * </p>
	 * <p>
	 * "condition"
	 * </p>
	 * <p>
	 * Criteria for including a document in the aggregation.
	 * </p>
	 * @return array an array containing the result.
	 */
	public function group ($keys, array $initial, MongoCode $reduce, array $options = null) {}

}

class MongoCursor implements Iterator, Traversable {
	public static $slaveOkay;
	public static $timeout;


	/**
	 * Create a new cursor
	 * @link http://www.php.net/manual/en/mongocursor.construct.php
	 */
	public function __construct () {}

	/**
	 * Checks if there are any more elements in this cursor
	 * @link http://www.php.net/manual/en/mongocursor.hasnext.php
	 * @return bool if there is another element.
	 */
	public function hasNext () {}

	/**
	 * Return the next object to which this cursor points, and advance the cursor
	 * @link http://www.php.net/manual/en/mongocursor.getnext.php
	 * @return array the next object.
	 */
	public function getNext () {}

	/**
	 * Limits the number of results returned
	 * @link http://www.php.net/manual/en/mongocursor.limit.php
	 * @param num int <p>
	 * The number of results to return.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function limit ($num) {}

	/**
	 * Sets the number of results returned per result set
	 * @link http://www.php.net/manual/en/mongocursor.batchsize.php
	 * @param num int <p>
	 * The number of results to return in the next batch.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function batchSize ($num) {}

	/**
	 * Skips a number of results
	 * @link http://www.php.net/manual/en/mongocursor.skip.php
	 * @param num int <p>
	 * The number of results to skip.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function skip ($num) {}

	/**
	 * Sets the fields for a query
	 * @link http://www.php.net/manual/en/mongocursor.fields.php
	 * @param f array <p>
	 * Fields to return (or not return).
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function fields (array $f) {}

	/**
	 * Adds a top-level key/value pair to a query
	 * @link http://www.php.net/manual/en/mongocursor.addoption.php
	 * @param key string <p>
	 * Fieldname to add.
	 * </p>
	 * @param value mixed <p>
	 * Value to add.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function addOption ($key, $value) {}

	/**
	 * Use snapshot mode for the query
	 * @link http://www.php.net/manual/en/mongocursor.snapshot.php
	 * @return MongoCursor this cursor.
	 */
	public function snapshot () {}

	/**
	 * Sorts the results by given fields
	 * @link http://www.php.net/manual/en/mongocursor.sort.php
	 * @param fields array <p>
	 * An array of fields by which to sort. Each element in the array has as
	 * key the field name, and as value either 1 for
	 * ascending sort, or -1 for descending sort.
	 * </p>
	 * <p>
	 * Each result is first sorted on the first field in the array, then (if
	 * it exists) on the second field in the array, etc. This means that the
	 * order of the fields in the fields array is
	 * important. See also the examples section.
	 * </p>
	 * @return MongoCursor the same cursor that this method was called on.
	 */
	public function sort (array $fields) {}

	/**
	 * Gives the database a hint about the query
	 * @link http://www.php.net/manual/en/mongocursor.hint.php
	 * @param key_pattern array <p>
	 * Indexes to use for the query.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function hint (array $key_pattern) {}

	/**
	 * Return an explanation of the query, often useful for optimization and debugging
	 * @link http://www.php.net/manual/en/mongocursor.explain.php
	 * @return array an explanation of the query.
	 */
	public function explain () {}

	/**
	 * Sets whether this query can be done on a slave
	 * @link http://www.php.net/manual/en/mongocursor.slaveokay.php
	 * @param okay bool[optional] <p>
	 * If it is okay to query the slave.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function slaveOkay ($okay = null) {}

	/**
	 * Sets whether this cursor will be left open after fetching the last results
	 * @link http://www.php.net/manual/en/mongocursor.tailable.php
	 * @param tail bool[optional] <p>
	 * If the cursor should be tailable.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function tailable ($tail = null) {}

	/**
	 * Sets whether this cursor will timeout
	 * @link http://www.php.net/manual/en/mongocursor.immortal.php
	 * @param liveForever bool[optional] <p>
	 * If the cursor should be immortal.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function immortal ($liveForever = null) {}

	/**
	 * If this query should fetch partial results from <emphasis>mongos</emphasis> if a shard is down
	 * @link http://www.php.net/manual/en/mongocursor.partial.php
	 * @param okay bool[optional] <p>
	 * If receiving partial results is okay.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function partial ($okay = null) {}

	/**
	 * Sets a client-side timeout for this query
	 * @link http://www.php.net/manual/en/mongocursor.timeout.php
	 * @param ms int <p>
	 * The number of milliseconds for the cursor to wait for a response. By 
	 * default, the cursor will wait forever.
	 * </p>
	 * @return MongoCursor This cursor.
	 */
	public function timeout ($ms) {}

	/**
	 * Execute the query.
	 * @link http://www.php.net/manual/en/mongocursor.doquery.php
	 * @return void &null;.
	 */
	protected function doQuery () {}

	/**
	 * Gets the query, fields, limit, and skip for this cursor
	 * @link http://www.php.net/manual/en/mongocursor.info.php
	 * @return array the namespace, limit, skip, query, and fields for this cursor.
	 */
	public function info () {}

	/**
	 * Checks if there are documents that have not been sent yet from the database for this cursor
	 * @link http://www.php.net/manual/en/mongocursor.dead.php
	 * @return bool if there are more results that have not been sent to the client, yet.
	 */
	public function dead () {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/mongocursor.current.php
	 * @return array The current result as an associative array.
	 */
	public function current () {}

	/**
	 * Returns the current result&apos;s _id
	 * @link http://www.php.net/manual/en/mongocursor.key.php
	 * @return string The current result&apos;s _id as a string.
	 */
	public function key () {}

	/**
	 * Advances the cursor to the next result
	 * @link http://www.php.net/manual/en/mongocursor.next.php
	 * @return void &null;.
	 */
	public function next () {}

	/**
	 * Returns the cursor to the beginning of the result set
	 * @link http://www.php.net/manual/en/mongocursor.rewind.php
	 * @return void &null;.
	 */
	public function rewind () {}

	/**
	 * Checks if the cursor is reading a valid result.
	 * @link http://www.php.net/manual/en/mongocursor.valid.php
	 * @return bool If the current result is not null.
	 */
	public function valid () {}

	/**
	 * Clears the cursor
	 * @link http://www.php.net/manual/en/mongocursor.reset.php
	 * @return void &null;.
	 */
	public function reset () {}

	/**
	 * Counts the number of results for this query
	 * @link http://www.php.net/manual/en/mongocursor.count.php
	 * @param foundOnly bool[optional] <p>
	 * Send cursor limit and skip information to the count function, if applicable.
	 * </p>
	 * @return int The number of documents returned by this cursor's query.
	 */
	public function count ($foundOnly = null) {}

}

class MongoGridFS extends MongoCollection  {
	const ASCENDING = 1;
	const DESCENDING = -1;

	public $w;
	public $wtimeout;
	public $chunks;
	protected $filesName;
	protected $chunksName;


	/**
	 * Creates new file collections
	 * @link http://www.php.net/manual/en/mongogridfs.construct.php
	 */
	public function __construct () {}

	/**
	 * Drops the files and chunks collections
	 * @link http://www.php.net/manual/en/mongogridfs.drop.php
	 * @return array The database response.
	 */
	public function drop () {}

	/**
	 * Queries for files
	 * @link http://www.php.net/manual/en/mongogridfs.find.php
	 * @param query array[optional] <p>
	 * The query.
	 * </p>
	 * @param fields array[optional] <p>
	 * Fields to return.
	 * </p>
	 * @return MongoGridFSCursor A MongoGridFSCursor.
	 */
	public function find (array $query = null, array $fields = null) {}

	/**
	 * Stores a file in the database
	 * @link http://www.php.net/manual/en/mongogridfs.storefile.php
	 * @param filename string <p>
	 * The name of the file.
	 * </p>
	 * @param extra array[optional] <p>
	 * Other metadata to add to the file saved.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the store.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Check that this store succeeded.
	 * </p>
	 * @return mixed the _id of the saved object.
	 */
	public function storeFile ($filename, array $extra = null, array $options = null) {}

	/**
	 * Chunkifies and stores bytes in the database
	 * @link http://www.php.net/manual/en/mongogridfs.storebytes.php
	 * @param bytes string <p>
	 * A string of bytes to store.
	 * </p>
	 * @param extra array[optional] <p>
	 * Other metadata to add to the file saved.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the store.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Check that this store succeeded.
	 * </p>
	 * @return mixed The _id of the object saved.
	 */
	public function storeBytes ($bytes, array $extra = null, array $options = null) {}

	/**
	 * Returns a single file matching the criteria
	 * @link http://www.php.net/manual/en/mongogridfs.findone.php
	 * @param query mixed[optional] <p>
	 * The filename or criteria for which to search.
	 * </p>
	 * @param fields mixed[optional] 
	 * @return MongoGridFSFile a MongoGridFSFile or &null;.
	 */
	public function findOne ($query = null, $fields = null) {}

	/**
	 * Removes files from the collections
	 * @link http://www.php.net/manual/en/mongogridfs.remove.php
	 * @param criteria array[optional] 
	 * @param options array[optional] <p>
	 * Options for the remove. Valid options are:
	 * </p>
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Check that the remove succeeded.
	 * </p>
	 * @return bool if the removal was successfully sent to the database.
	 */
	public function remove (array $criteria = null, array $options = null) {}

	/**
	 * Saves an uploaded file to the database
	 * @link http://www.php.net/manual/en/mongogridfs.storeupload.php
	 * @param name string <p>
	 * The name field of the uploaded file.
	 * </p>
	 * @param metadata array[optional] <p>
	 * An array of extra fields for the uploaded file.
	 * </p>
	 * @return mixed the _id of the uploaded file.
	 */
	public function storeUpload ($name, array $metadata = null) {}

	/**
	 * Delete a file from the database
	 * @link http://www.php.net/manual/en/mongogridfs.delete.php
	 * @param id mixed <p>
	 * _id of the file to remove.
	 * </p>
	 * @return bool if the remove was successfully sent to the database.
	 */
	public function delete ($id) {}

	/**
	 * Retrieve a file from the database
	 * @link http://www.php.net/manual/en/mongogridfs.get.php
	 * @param id mixed <p>
	 * _id of the file to find.
	 * </p>
	 * @return MongoGridFSFile the file, if found, or &null;.
	 */
	public function get ($id) {}

	/**
	 * Stores a file in the database
	 * @link http://www.php.net/manual/en/mongogridfs.put.php
	 * @param filename string <p>
	 * The name of the file.
	 * </p>
	 * @param extra array[optional] <p>
	 * Other metadata to add to the file saved.
	 * </p>
	 * @return mixed the _id of the saved object.
	 */
	public function put ($filename, array $extra = null) {}

	/**
	 * String representation of this collection
	 * @link http://www.php.net/manual/en/mongocollection.--tostring.php
	 * @return string the full name of this collection.
	 */
	public function __toString () {}

	/**
	 * Gets a collection
	 * @link http://www.php.net/manual/en/mongocollection.get.php
	 * @param name string <p>
	 * The next string in the collection name.
	 * </p>
	 * @return MongoCollection the collection.
	 */
	public function __get ($name) {}

	/**
	 * Returns this collection&apos;s name
	 * @link http://www.php.net/manual/en/mongocollection.getname.php
	 * @return string the name of this collection.
	 */
	public function getName () {}

	/**
	 * Get slaveOkay setting for this collection
	 * @link http://www.php.net/manual/en/mongocollection.getslaveokay.php
	 * @return bool the value of slaveOkay for this instance.
	 */
	public function getSlaveOkay () {}

	/**
	 * Change slaveOkay setting for this collection
	 * @link http://www.php.net/manual/en/mongocollection.setslaveokay.php
	 * @param ok bool[optional] <p>
	 * If reads should be sent to secondary members of a replica set for all 
	 * possible queries using this MongoCollection 
	 * instance.
	 * </p>
	 * @return bool the former value of slaveOkay for this instance.
	 */
	public function setSlaveOkay ($ok = null) {}

	/**
	 * Validates this collection
	 * @link http://www.php.net/manual/en/mongocollection.validate.php
	 * @param scan_data bool[optional] <p>
	 * Only validate indices, not the base collection.
	 * </p>
	 * @return array the database&apos;s evaluation of this object.
	 */
	public function validate ($scan_data = null) {}

	/**
	 * Inserts an array into the collection
	 * @link http://www.php.net/manual/en/mongocollection.insert.php
	 * @param a array <p>
	 * An array.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the insert.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Can be a boolean or integer, defaults to false. If false, the 
	 * program continues executing without waiting for a database response. 
	 * If true, the program will wait for the database response and throw a
	 * MongoCursorException if the insert did not 
	 * succeed. 
	 * </p>
	 * <p>
	 * If you are using replication and the master has changed, using "safe" 
	 * will make the driver disconnect from the master, throw an exception, 
	 * and attempt to find a new master on the next operation (your 
	 * application must decide whether or not to retry the operation on the
	 * new master). 
	 * </p>
	 * <p>
	 * If you do not use "safe" with a replica set and 
	 * the master changes, there will be no way for the driver to know about 
	 * the change so it will continuously and silently fail to write.
	 * </p>
	 * <p>
	 * If safe is an integer, will replicate the
	 * insert to that many machines before returning success (or throw an
	 * exception if the replication times out, see wtimeout). This overrides
	 * the w variable set on the collection.
	 * </p>
	 * @return bool|array If safe was set, returns an array containing the
	 * status of the insert. Otherwise, returns a boolean representing if the
	 * array was not empty (an empty array will not be inserted).
	 * </p>
	 * <p>
	 * If an array is returned, the following keys can be present:
	 * ok
	 * <p>
	 * This should almost be 1 (unless last_error itself failed).
	 * </p>
	 * err
	 * <p>
	 * If this field is non-null, an error occurred on the previous operation.
	 * If this field is set, it will be a string describing the error that
	 * occurred. 
	 * </p>
	 * code
	 * <p>
	 * If a database error occurred, the relevant error code will be passed
	 * back to the client. 
	 * </p>
	 * errmsg
	 * <p>
	 * This field is set if something goes wrong with a database command. It
	 * is coupled with ok being 0. For example, if
	 * w is set and times out, errmsg will be set to "timed
	 * out waiting for slaves" and ok will be 0. If this
	 * field is set, it will be a string describing the error that occurred. 
	 * </p>
	 * n
	 * <p>
	 * If the last operation was an insert, an update or a remove, the number
	 * of objects affected will be returned. 
	 * </p>
	 * wtimeout
	 * <p>
	 * If the previous option timed out waiting for replication. 
	 * </p>
	 * waiter
	 * <p>
	 * How long the operation waited before timing out. 
	 * </p>
	 * wtime
	 * <p>
	 * If w was set and the operation succeeded, how long it took to
	 * replicate to w servers. 
	 * </p>
	 * upserted
	 * <p>
	 * If an upsert occured, this field will contain the new record's
	 * _id field. For upserts, either this field or
	 * updatedExisting will be present (unless an error
	 * occurred). 
	 * </p>
	 * updatedExisting
	 * <p>
	 * If an upsert updated an existing element, this field will be true. For
	 * upserts, either this field or upserted will be present (unless an error
	 * occurred). 
	 * </p>
	 */
	public function insert (array $a, array $options = null) {}

	/**
	 * Inserts multiple documents into this collection
	 * @link http://www.php.net/manual/en/mongocollection.batchinsert.php
	 * @param a array <p>
	 * An array of arrays.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the inserts.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Can be a boolean or integer, defaults to false. If false, the 
	 * program continues executing without waiting for a database response. 
	 * If true, the program will wait for the database response and throw a
	 * MongoCursorException if the insert did not 
	 * succeed. 
	 * </p>
	 * <p>
	 * If safe is an integer, will replicate the
	 * insert to that many machines before returning success (or throw an
	 * exception if the replication times out, see wtimeout). This overrides
	 * the w variable set on the collection.
	 * </p>
	 * @return mixed If "safe" is set, returns an associative array with the status of the inserts
	 * ("ok") and any error that may have occured ("err"). Otherwise, returns 
	 * true if the batch insert was successfully sent, false otherwise.
	 */
	public function batchInsert (array $a, array $options = null) {}

	/**
	 * Update records based on a given criteria
	 * @link http://www.php.net/manual/en/mongocollection.update.php
	 * @param criteria array <p>
	 * Description of the objects to update.
	 * </p>
	 * @param new_object array <p>
	 * The object with which to update the matching records.
	 * </p>
	 * @param options array[optional] <p>
	 * This parameter is an associative array of the form 
	 * array("optionname" => &lt;boolean&gt;, ...). Currently 
	 * supported options are: 
	 * <p>
	 * "upsert"
	 * </p>
	 * <p>
	 * If no document matches $criteria, a new
	 * document will be created from $criteria and
	 * $new_object (see upsert example below).
	 * </p>
	 * @return bool|array If safe was set, returns an array containing the
	 * status of the update. Otherwise, returns a boolean representing if the
	 * array was not empty (an empty array will not be inserted). The fields in
	 * this array are decribed in the documentation for
	 * MongoCollection::insert.
	 */
	public function update (array $criteria, array $new_object, array $options = null) {}

	/**
	 * Creates an index on the given field(s), or does nothing if the index 
   already exists
	 * @link http://www.php.net/manual/en/mongocollection.ensureindex.php
	 * @param key_keys string|array 
	 * @param options array[optional] <p>
	 * This parameter is an associative array of the form 
	 * array("optionname" => &lt;boolean&gt;, ...). Currently 
	 * supported options are: 
	 * <p>
	 * "unique"
	 * </p>
	 * <p>
	 * Create a unique index.
	 * </p>
	 * <p>
	 * A unique index cannot be created on a field if multiple existing documents do
	 * not contain the field. The field is effectively &null; for these documents 
	 * and thus already non-unique.
	 * </p>
	 * @return bool true.
	 */
	public function ensureIndex ($key_keys, array $options = null) {}

	/**
	 * Deletes an index from this collection
	 * @link http://www.php.net/manual/en/mongocollection.deleteindex.php
	 * @param keys string|array <p>
	 * Field or fields from which to delete the index.
	 * </p>
	 * @return array the generated name of the key if successful, or &null; otherwise.
	 */
	public function deleteIndex ($keys) {}

	/**
	 * Delete all indices for this collection
	 * @link http://www.php.net/manual/en/mongocollection.deleteindexes.php
	 * @return array the database response.
	 */
	public function deleteIndexes () {}

	/**
	 * Returns information about indexes on this collection
	 * @link http://www.php.net/manual/en/mongocollection.getindexinfo.php
	 * @return array This function returns an array in which each elements describes an array.
	 * The elements contain the values name for the name of
	 * the index, ns for the namespace (the name of the 
	 * collection), key containing a list of all the keys
	 * and their sort order that make up the index and _id
	 * containing a MongoID object with the ID of this index.
	 */
	public function getIndexInfo () {}

	/**
	 * Counts the number of documents in this collection
	 * @link http://www.php.net/manual/en/mongocollection.count.php
	 * @param query array[optional] <p>
	 * Associative array or object with fields to match.
	 * </p>
	 * @param limit int[optional] <p>
	 * Specifies an upper limit to the number returned.
	 * </p>
	 * @param skip int[optional] <p>
	 * Specifies a number of results to skip before starting the count.
	 * </p>
	 * @return int the number of documents matching the query.
	 */
	public function count (array $query = null, $limit = null, $skip = null) {}

	/**
	 * Saves an object to this collection
	 * @link http://www.php.net/manual/en/mongocollection.save.php
	 * @param a array <p>
	 * Array to save.
	 * </p>
	 * @param options array[optional] <p>
	 * Options for the save.
	 * <p>
	 * "safe"
	 * </p>
	 * <p>
	 * Can be a boolean or integer, defaults to false. If false, the 
	 * program continues executing without waiting for a database response. 
	 * If true, the program will wait for the database response and throw a
	 * MongoCursorException if the insert did not 
	 * succeed. 
	 * </p>
	 * <p>
	 * If you are using replication and the master has changed, using "safe" 
	 * will make the driver disconnect from the master, throw and exception, 
	 * and attempt to find a new master on the next operation (your 
	 * application must decide whether or not to retry the operation on the
	 * new master). 
	 * </p>
	 * <p>
	 * If you do not use "safe" with a replica set and 
	 * the master changes, there will be no way for the driver to know about 
	 * the change so it will continuously and silently fail to write.
	 * </p>
	 * <p>
	 * If safe is an integer, will replicate the
	 * insert to that many machines before returning success (or throw an
	 * exception if the replication times out, see wtimeout). This overrides
	 * the w variable set on the collection.
	 * </p>
	 * @return mixed If safe was set, returns an array containing the status of the save.
	 * Otherwise, returns a boolean representing if the array was not empty (an empty array will not 
	 * be inserted).
	 */
	public function save (array $a, array $options = null) {}

	/**
	 * Creates a database reference
	 * @link http://www.php.net/manual/en/mongocollection.createdbref.php
	 * @param a array <p>
	 * Object to which to create a reference.
	 * </p>
	 * @return array a database reference array.
	 */
	public function createDBRef (array $a) {}

	/**
	 * Fetches the document pointed to by a database reference
	 * @link http://www.php.net/manual/en/mongocollection.getdbref.php
	 * @param ref array <p>
	 * A database reference.
	 * </p>
	 * @return array the database document pointed to by the reference.
	 */
	public function getDBRef (array $ref) {}

	/**
	 * Converts keys specifying an index to its identifying string
	 * @link http://www.php.net/manual/en/mongocollection.toindexstring.php
	 * @param keys mixed <p>
	 * Field or fields to convert to the identifying string
	 * </p>
	 * @return string a string that describes the index.
	 */
	protected static function toIndexString ($keys) {}

	/**
	 * Performs an operation similar to SQL's GROUP BY command
	 * @link http://www.php.net/manual/en/mongocollection.group.php
	 * @param keys mixed <p>
	 * Fields to group by. If an array or non-code object is passed, it will be
	 * the key used to group results. 
	 * </p>
	 * <p>1.0.4+: If keys is an instance of 
	 * MongoCode, keys will be treated as
	 * a function that returns the key to group by (see the "Passing a 
	 * keys function" example below). 
	 * </p>
	 * @param initial array <p>
	 * Initial value of the aggregation counter object.
	 * </p>
	 * @param reduce MongoCode <p>
	 * A function that takes two arguments (the current document and the 
	 * aggregation to this point) and does the aggregation.
	 * </p>
	 * @param options array[optional] <p>
	 * Optional parameters to the group command. Valid options include:
	 * </p>
	 * <p>
	 * "condition"
	 * </p>
	 * <p>
	 * Criteria for including a document in the aggregation.
	 * </p>
	 * @return array an array containing the result.
	 */
	public function group ($keys, array $initial, MongoCode $reduce, array $options = null) {}

}

class MongoGridFSFile  {
	public $file;
	protected $gridfs;


	/**
	 * Create a new GridFS file
	 * @link http://www.php.net/manual/en/mongogridfsfile.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns this file&apos;s filename
	 * @link http://www.php.net/manual/en/mongogridfsfile.getfilename.php
	 * @return string the filename.
	 */
	public function getFilename () {}

	/**
	 * Returns this file&apos;s size
	 * @link http://www.php.net/manual/en/mongogridfsfile.getsize.php
	 * @return int this file's size
	 */
	public function getSize () {}

	/**
	 * Writes this file to the filesystem
	 * @link http://www.php.net/manual/en/mongogridfsfile.write.php
	 * @param filename string[optional] <p>
	 * The location to which to write the file. If none is given,
	 * the stored filename will be used.
	 * </p>
	 * @return int the number of bytes written.
	 */
	public function write ($filename = null) {}

	/**
	 * Returns this file&apos;s contents as a string of bytes
	 * @link http://www.php.net/manual/en/mongogridfsfile.getbytes.php
	 * @return string a string of the bytes in the file.
	 */
	public function getBytes () {}

}

class MongoGridFSCursor extends MongoCursor implements Traversable, Iterator {
	public static $slaveOkay;
	public static $timeout;
	protected $gridfs;


	/**
	 * Create a new cursor
	 * @link http://www.php.net/manual/en/mongogridfscursor.construct.php
	 */
	public function __construct () {}

	/**
	 * Return the next file to which this cursor points, and advance the cursor
	 * @link http://www.php.net/manual/en/mongogridfscursor.getnext.php
	 * @return MongoGridFSFile the next file.
	 */
	public function getNext () {}

	/**
	 * Returns the current file
	 * @link http://www.php.net/manual/en/mongogridfscursor.current.php
	 * @return MongoGridFSFile The current file.
	 */
	public function current () {}

	/**
	 * Returns the current result&apos;s filename
	 * @link http://www.php.net/manual/en/mongogridfscursor.key.php
	 * @return string The current result&apos;s filename.
	 */
	public function key () {}

	/**
	 * Checks if there are any more elements in this cursor
	 * @link http://www.php.net/manual/en/mongocursor.hasnext.php
	 * @return bool if there is another element.
	 */
	public function hasNext () {}

	/**
	 * Limits the number of results returned
	 * @link http://www.php.net/manual/en/mongocursor.limit.php
	 * @param num int <p>
	 * The number of results to return.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function limit ($num) {}

	/**
	 * Sets the number of results returned per result set
	 * @link http://www.php.net/manual/en/mongocursor.batchsize.php
	 * @param num int <p>
	 * The number of results to return in the next batch.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function batchSize ($num) {}

	/**
	 * Skips a number of results
	 * @link http://www.php.net/manual/en/mongocursor.skip.php
	 * @param num int <p>
	 * The number of results to skip.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function skip ($num) {}

	/**
	 * Sets the fields for a query
	 * @link http://www.php.net/manual/en/mongocursor.fields.php
	 * @param f array <p>
	 * Fields to return (or not return).
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function fields (array $f) {}

	/**
	 * Adds a top-level key/value pair to a query
	 * @link http://www.php.net/manual/en/mongocursor.addoption.php
	 * @param key string <p>
	 * Fieldname to add.
	 * </p>
	 * @param value mixed <p>
	 * Value to add.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function addOption ($key, $value) {}

	/**
	 * Use snapshot mode for the query
	 * @link http://www.php.net/manual/en/mongocursor.snapshot.php
	 * @return MongoCursor this cursor.
	 */
	public function snapshot () {}

	/**
	 * Sorts the results by given fields
	 * @link http://www.php.net/manual/en/mongocursor.sort.php
	 * @param fields array <p>
	 * An array of fields by which to sort. Each element in the array has as
	 * key the field name, and as value either 1 for
	 * ascending sort, or -1 for descending sort.
	 * </p>
	 * <p>
	 * Each result is first sorted on the first field in the array, then (if
	 * it exists) on the second field in the array, etc. This means that the
	 * order of the fields in the fields array is
	 * important. See also the examples section.
	 * </p>
	 * @return MongoCursor the same cursor that this method was called on.
	 */
	public function sort (array $fields) {}

	/**
	 * Gives the database a hint about the query
	 * @link http://www.php.net/manual/en/mongocursor.hint.php
	 * @param key_pattern array <p>
	 * Indexes to use for the query.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function hint (array $key_pattern) {}

	/**
	 * Return an explanation of the query, often useful for optimization and debugging
	 * @link http://www.php.net/manual/en/mongocursor.explain.php
	 * @return array an explanation of the query.
	 */
	public function explain () {}

	/**
	 * Sets whether this query can be done on a slave
	 * @link http://www.php.net/manual/en/mongocursor.slaveokay.php
	 * @param okay bool[optional] <p>
	 * If it is okay to query the slave.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function slaveOkay ($okay = null) {}

	/**
	 * Sets whether this cursor will be left open after fetching the last results
	 * @link http://www.php.net/manual/en/mongocursor.tailable.php
	 * @param tail bool[optional] <p>
	 * If the cursor should be tailable.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function tailable ($tail = null) {}

	/**
	 * Sets whether this cursor will timeout
	 * @link http://www.php.net/manual/en/mongocursor.immortal.php
	 * @param liveForever bool[optional] <p>
	 * If the cursor should be immortal.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function immortal ($liveForever = null) {}

	/**
	 * If this query should fetch partial results from <emphasis>mongos</emphasis> if a shard is down
	 * @link http://www.php.net/manual/en/mongocursor.partial.php
	 * @param okay bool[optional] <p>
	 * If receiving partial results is okay.
	 * </p>
	 * @return MongoCursor this cursor.
	 */
	public function partial ($okay = null) {}

	/**
	 * Sets a client-side timeout for this query
	 * @link http://www.php.net/manual/en/mongocursor.timeout.php
	 * @param ms int <p>
	 * The number of milliseconds for the cursor to wait for a response. By 
	 * default, the cursor will wait forever.
	 * </p>
	 * @return MongoCursor This cursor.
	 */
	public function timeout ($ms) {}

	/**
	 * Execute the query.
	 * @link http://www.php.net/manual/en/mongocursor.doquery.php
	 * @return void &null;.
	 */
	protected function doQuery () {}

	/**
	 * Gets the query, fields, limit, and skip for this cursor
	 * @link http://www.php.net/manual/en/mongocursor.info.php
	 * @return array the namespace, limit, skip, query, and fields for this cursor.
	 */
	public function info () {}

	/**
	 * Checks if there are documents that have not been sent yet from the database for this cursor
	 * @link http://www.php.net/manual/en/mongocursor.dead.php
	 * @return bool if there are more results that have not been sent to the client, yet.
	 */
	public function dead () {}

	/**
	 * Advances the cursor to the next result
	 * @link http://www.php.net/manual/en/mongocursor.next.php
	 * @return void &null;.
	 */
	public function next () {}

	/**
	 * Returns the cursor to the beginning of the result set
	 * @link http://www.php.net/manual/en/mongocursor.rewind.php
	 * @return void &null;.
	 */
	public function rewind () {}

	/**
	 * Checks if the cursor is reading a valid result.
	 * @link http://www.php.net/manual/en/mongocursor.valid.php
	 * @return bool If the current result is not null.
	 */
	public function valid () {}

	/**
	 * Clears the cursor
	 * @link http://www.php.net/manual/en/mongocursor.reset.php
	 * @return void &null;.
	 */
	public function reset () {}

	/**
	 * Counts the number of results for this query
	 * @link http://www.php.net/manual/en/mongocursor.count.php
	 * @param foundOnly bool[optional] <p>
	 * Send cursor limit and skip information to the count function, if applicable.
	 * </p>
	 * @return int The number of documents returned by this cursor's query.
	 */
	public function count ($foundOnly = null) {}

}

class MongoId  {
	public $$id;


	/**
	 * Creates a new id
	 * @link http://www.php.net/manual/en/mongoid.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns a hexidecimal representation of this id
	 * @link http://www.php.net/manual/en/mongoid.tostring.php
	 * @return string This id.
	 */
	public function __toString () {}

	/**
	 * Create a dummy MongoId
	 * @link http://www.php.net/manual/en/mongoid.set-state.php
	 * @param props array <p>
	 * Theoretically, an array of properties used to create the new id. 
	 * However, as MongoId instances have no properties, this is not used.
	 * </p>
	 * @return MongoId A new id with the value "000000000000000000000000".
	 */
	public static function __set_state (array $props) {}

	/**
	 * Gets the number of seconds since the epoch that this id was created
	 * @link http://www.php.net/manual/en/mongoid.gettimestamp.php
	 * @return int the number of seconds since the epoch that this id was created. There are only 
	 * four bytes of timestamp stored, so MongoDate is a better choice 
	 * for storing exact or wide-ranging times.
	 */
	public function getTimestamp () {}

	/**
	 * Gets the hostname being used for this machine's ids
	 * @link http://www.php.net/manual/en/mongoid.gethostname.php
	 * @return string the hostname.
	 */
	public static function getHostname () {}

	/**
	 * Gets the process id used to create this
	 * @link http://www.php.net/manual/en/mongoid.getpid.php
	 * @return int the PID used to create this MongoId.
	 */
	public function getPID () {}

	/**
	 * Gets the incremented value to create this id
	 * @link http://www.php.net/manual/en/mongoid.getinc.php
	 * @return int the incremented value used to create this MongoId.
	 */
	public function getInc () {}

}

class MongoCode  {
	public $code;
	public $scope;


	/**
	 * Creates a new code object
	 * @link http://www.php.net/manual/en/mongocode.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns this code as a string
	 * @link http://www.php.net/manual/en/mongocode.tostring.php
	 * @return string This code, the scope is not returned.
	 */
	public function __toString () {}

}

class MongoRegex  {
	public $regex;
	public $flags;


	/**
	 * Creates a new regular expression
	 * @link http://www.php.net/manual/en/mongoregex.construct.php
	 */
	public function __construct () {}

	/**
	 * A string representation of this regular expression
	 * @link http://www.php.net/manual/en/mongoregex.tostring.php
	 * @return string This regular expression in the form "/expr/flags".
	 */
	public function __toString () {}

}

class MongoDate  {
	public $sec;
	public $usec;


	/**
	 * Creates a new date.
	 * @link http://www.php.net/manual/en/mongodate.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns a string representation of this date
	 * @link http://www.php.net/manual/en/mongodate.tostring.php
	 * @return string This date.
	 */
	public function __toString () {}

}

class MongoBinData  {
	const FUNC = 1;
	const BYTE_ARRAY = 2;
	const UUID = 3;
	const MD5 = 5;
	const CUSTOM = 128;

	public $bin;
	public $type;


	/**
	 * Creates a new binary data object.
	 * @link http://www.php.net/manual/en/mongobindata.construct.php
	 */
	public function __construct () {}

	/**
	 * The string representation of this binary data object.
	 * @link http://www.php.net/manual/en/mongobindata.tostring.php
	 * @return string the string "&lt;Mongo Binary Data&gt;". To access the contents of a 
	 * MongoBinData, use the bin field.
	 */
	public function __toString () {}

}

class MongoDBRef  {
	protected static $refKey;
	protected static $idKey;


	/**
	 * Creates a new database reference
	 * @link http://www.php.net/manual/en/mongodbref.create.php
	 * @param collection string <p>
	 * Collection name (without the database name).
	 * </p>
	 * @param id mixed <p>
	 * The _id field of the object to which to link.
	 * </p>
	 * @param database string[optional] <p>
	 * Database name.
	 * </p>
	 * @return array the reference.
	 */
	public static function create ($collection, $id, $database = null) {}

	/**
	 * Checks if an array is a database reference
	 * @link http://www.php.net/manual/en/mongodbref.isref.php
	 * @param ref mixed <p>
	 * Array or object to check.
	 * </p>
	 * @return bool if ref is a reference.
	 */
	public static function isRef ($ref) {}

	/**
	 * Fetches the object pointed to by a reference
	 * @link http://www.php.net/manual/en/mongodbref.get.php
	 * @param db MongoDB <p>
	 * Database to use.
	 * </p>
	 * @param ref array <p>
	 * Reference to fetch.
	 * </p>
	 * @return array the document to which the reference refers or &null; if the document
	 * does not exist (the reference is broken).
	 */
	public static function get (MongoDB $db, array $ref) {}

}

class MongoException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class MongoCursorException extends MongoException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	private $host;
	private $fd;


	public function getHost () {}

	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class MongoCursorTimeoutException extends MongoCursorException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getHost () {}

	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class MongoConnectionException extends MongoException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class MongoGridFSException extends MongoException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class MongoTimestamp  {
	public $sec;
	public $inc;


	/**
	 * Creates a new timestamp.
	 * @link http://www.php.net/manual/en/mongotimestamp.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns a string representation of this timestamp
	 * @link http://www.php.net/manual/en/mongotimestamp.tostring.php
	 * @return string The seconds since epoch represented by this timestamp.
	 */
	public function __toString () {}

}

class MongoInt32  {
	public $value;


	/**
	 * Creates a new 32-bit integer.
	 * @link http://www.php.net/manual/en/mongoint32.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns the string representation of this 32-bit integer.
	 * @link http://www.php.net/manual/en/mongoint32.tostring.php
	 * @return string the string representation of this integer.
	 */
	public function __toString () {}

}

class MongoInt64  {
	public $value;


	/**
	 * Creates a new 64-bit integer.
	 * @link http://www.php.net/manual/en/mongoint64.construct.php
	 */
	public function __construct () {}

	/**
	 * Returns the string representation of this 64-bit integer.
	 * @link http://www.php.net/manual/en/mongoint64.tostring.php
	 * @return string the string representation of this integer.
	 */
	public function __toString () {}

}

class MongoLog  {
	const NONE = 0;
	const WARNING = 1;
	const INFO = 2;
	const FINE = 4;
	const RS = 1;
	const POOL = 2;
	const IO = 4;
	const SERVER = 8;
	const ALL = 15;

	private static $level;
	private static $module;


	/**
	 * Sets logging level
	 * @link http://www.php.net/manual/en/mongolog.setlevel.php
	 * @param level int <p>
	 * The levels you would like to log.
	 * </p>
	 * @return void 
	 */
	public static function setLevel ($level) {}

	/**
	 * Gets the log level
	 * @link http://www.php.net/manual/en/mongolog.getlevel.php
	 * @return int the current level.
	 */
	public static function getLevel () {}

	/**
	 * Sets driver functionality to log
	 * @link http://www.php.net/manual/en/mongolog.setmodule.php
	 * @param module int <p>
	 * The module(s) you would like to log.
	 * </p>
	 * @return void 
	 */
	public static function setModule ($module) {}

	/**
	 * Gets the modules currently being logged
	 * @link http://www.php.net/manual/en/mongolog.getmodule.php
	 * @return int the modules currently being logged.
	 */
	public static function getModule () {}

}

class MongoPool  {

	/**
	 * Returns information about all connection pools.
	 * @link http://www.php.net/manual/en/mongopool.info.php
	 * @return array Each connection pool has an identifier, which starts with the host. For each
	 * pool, this function shows the following fields:
	 * in use
	 * <p>
	 * The number of connections currently being used by
	 * Mongo instances.
	 * </p>
	 * in pool
	 * <p>
	 * The number of connections currently in the pool (not being used).
	 * </p>
	 * remaining
	 * <p>
	 * The number of connections that could be created by this pool. For
	 * example, suppose a pool had 5 connections remaining and 3 connections in
	 * the pool. We could create 8 new instances of
	 * Mongo before we exhausted this pool (assuming no
	 * instances of Mongo went out of scope, returning
	 * their connections to the pool).
	 * </p>
	 * <p>
	 * A negative number means that this pool will spawn unlimited connections.
	 * </p>
	 * <p>
	 * Before a pool is created, you can change the max number of connections by
	 * calling Mongo::setPoolSize. Once a pool is showing
	 * up in the output of this function, its size cannot be changed.
	 * </p>
	 * total
	 * <p>
	 * The total number of connections allowed for this pool. This should be
	 * greater than or equal to "in use" + "in pool" (or -1).
	 * </p>
	 * timeout
	 * <p>
	 * The socket timeout for connections in this pool. This is how long
	 * connections in this pool will attempt to connect to a server before
	 * giving up.
	 * </p>
	 * waiting
	 * <p>
	 * If you have capped the pool size, workers requesting connections from
	 * the pool may block until other workers return their connections. This
	 * field shows how many milliseconds workers have blocked for connections to
	 * be released. If this number keeps increasing, you may want to use
	 * MongoPool::setSize to add more connections to your
	 * pool.
	 * </p>
	 */
	public static function info () {}

	/**
	 * Set the size for future connection pools.
	 * @link http://www.php.net/manual/en/mongopool.setsize.php
	 * @param size int <p>
	 * The max number of connections future pools will be able to create.
	 * Negative numbers mean that the pool will spawn an infinite number of
	 * connections.
	 * </p>
	 * @return bool the former value of pool size.
	 */
	public static function setSize ($size) {}

	/**
	 * Get pool size for connection pools
	 * @link http://www.php.net/manual/en/mongopool.getsize.php
	 * @return int the current pool size.
	 */
	public static function getSize () {}

}

class MongoMaxKey  {
}

class MongoMinKey  {
}

/**
 * Serializes a PHP variable into a BSON string
 * @link http://www.php.net/manual/en/function.bson-encode.php
 * @param anything mixed <p>
 * The variable to be serialized.
 * </p>
 * @return string the serialized string.
 */
function bson_encode ($anything) {}

/**
 * Deserializes a BSON object into a PHP array
 * @link http://www.php.net/manual/en/function.bson-decode.php
 * @param bson string <p>
 * The BSON to be deserialized.
 * </p>
 * @return array the deserialized BSON object.
 */
function bson_decode ($bson) {}

// End of mongo v.1.2.7
?>
