<?php

// Start of memcache v.0.1

class Memcache  {

	/**
	 * Open memcached server connection
	 * @link http://php.net/manual/en/function.Memcache-connect.php
	 * @param host string
	 * @param port int[optional]
	 * @param timeout int[optional]
	 * @return bool 
	 */
	public function connect ($host, $port = null, $timeout = null) {}

	/**
	 * Open memcached server persistent connection
	 * @link http://php.net/manual/en/function.Memcache-pconnect.php
	 * @param host string
	 * @param port int[optional]
	 * @param timeout int[optional]
	 * @return bool 
	 */
	public function pconnect ($host, $port = null, $timeout = null) {}

	/**
	 * Return version of the server
	 * @link http://php.net/manual/en/function.Memcache-getVersion.php
	 * @return string a string of server version number or false on failure.
	 */
	public function getversion () {}

	/**
	 * Add an item to the server
	 * @link http://php.net/manual/en/function.Memcache-add.php
	 * @param key string
	 * @param var mixed
	 * @param flag int[optional]
	 * @param expire int[optional]
	 * @return bool 
	 */
	public function add ($key, $var, $flag = null, $expire = null) {}

	/**
	 * Store data at the server
	 * @link http://php.net/manual/en/function.Memcache-set.php
	 * @param key string
	 * @param var mixed
	 * @param flag int[optional]
	 * @param expire int[optional]
	 * @return bool 
	 */
	public function set ($key, $var, $flag = null, $expire = null) {}

	/**
	 * Replace value of the existing item
	 * @link http://php.net/manual/en/function.Memcache-replace.php
	 * @param key string
	 * @param var mixed
	 * @param flag int[optional]
	 * @param expire int[optional]
	 * @return bool 
	 */
	public function replace ($key, $var, $flag = null, $expire = null) {}

	/**
	 * Retrieve item from the server
	 * @link http://php.net/manual/en/function.Memcache-get.php
	 * @param key string
	 * @param flags int[optional]
	 * @return string the string associated with the key or
	 */
	public function get ($key, &$flags = null) {}

	/**
	 * Delete item from the server
	 * @link http://php.net/manual/en/function.Memcache-delete.php
	 * @param key string
	 * @param timeout int[optional]
	 * @return bool 
	 */
	public function delete ($key, $timeout = null) {}

	/**
	 * Get statistics of the server
	 * @link http://php.net/manual/en/function.Memcache-getStats.php
	 * @param type string[optional]
	 * @param slabid int[optional]
	 * @param limit int[optional]
	 * @return array an associative array of server statistics or false on failure.
	 */
	public function getstats ($type = null, $slabid = null, $limit = null) {}

	/**
	 * Increment item's value
	 * @link http://php.net/manual/en/function.Memcache-increment.php
	 * @param key string
	 * @param value int[optional]
	 * @return int new item's value on success or false on failure.
	 */
	public function increment ($key, $value = null) {}

	/**
	 * Decrement item's value
	 * @link http://php.net/manual/en/function.Memcache-decrement.php
	 * @param key string
	 * @param value int[optional]
	 * @return int item's new value on success or false on failure.
	 */
	public function decrement ($key, $value = null) {}

	/**
	 * Close memcached server connection
	 * @link http://php.net/manual/en/function.Memcache-close.php
	 * @return bool 
	 */
	public function close () {}

	/**
	 * Flush all existing items at the server
	 * @link http://php.net/manual/en/function.Memcache-flush.php
	 * @return bool 
	 */
	public function flush () {}

}

function memcache_connect () {}

function memcache_pconnect () {}

function memcache_get_version () {}

function memcache_add () {}

function memcache_set () {}

function memcache_replace () {}

function memcache_get () {}

function memcache_delete () {}

/**
 * Turn debug output on/off
 * @link http://php.net/manual/en/function.memcache-debug.php
 * @param on_off bool
 * @return bool true if PHP was built with --enable-debug option, otherwise
 */
function memcache_debug ($on_off) {}

function memcache_get_stats () {}

function memcache_increment () {}

function memcache_decrement () {}

function memcache_close () {}

function memcache_flush () {}

define ('MEMCACHE_COMPRESSED', 2);

// End of memcache v.0.1
?>
