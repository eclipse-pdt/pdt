<?php

// Start of pthreads v.3.1.6

interface Collectable  {

	abstract public function isGarbage ();

}

class Threaded implements Traversable, Collectable {

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function notifyOne () {}

	public function isRunning () {}

	public function isTerminated () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	/**
	 * @param $from
	 * @param $overwrite [optional]
	 */
	public function merge ($from, $overwrite = null) {}

	public function shift () {}

	/**
	 * @param $size
	 * @param $preserve [optional]
	 */
	public function chunk ($size, $preserve = null) {}

	public function pop () {}

	public function count () {}

	public function isGarbage () {}

	public function addRef () {}

	public function delRef () {}

	public function getRefCount () {}

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

}

class Volatile extends Threaded implements Collectable, Traversable {

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function notifyOne () {}

	public function isRunning () {}

	public function isTerminated () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	/**
	 * @param $from
	 * @param $overwrite [optional]
	 */
	public function merge ($from, $overwrite = null) {}

	public function shift () {}

	/**
	 * @param $size
	 * @param $preserve [optional]
	 */
	public function chunk ($size, $preserve = null) {}

	public function pop () {}

	public function count () {}

	public function isGarbage () {}

	public function addRef () {}

	public function delRef () {}

	public function getRefCount () {}

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

}

class Thread extends Threaded implements Collectable, Traversable {

	/**
	 * @param $options [optional]
	 */
	public function start ($options = null) {}

	public function join () {}

	public function isStarted () {}

	public function isJoined () {}

	public function getThreadId () {}

	public function getCreatorId () {}

	public static function getCurrentThreadId () {}

	public static function getCurrentThread () {}

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function notifyOne () {}

	public function isRunning () {}

	public function isTerminated () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	/**
	 * @param $from
	 * @param $overwrite [optional]
	 */
	public function merge ($from, $overwrite = null) {}

	public function shift () {}

	/**
	 * @param $size
	 * @param $preserve [optional]
	 */
	public function chunk ($size, $preserve = null) {}

	public function pop () {}

	public function count () {}

	public function isGarbage () {}

	public function addRef () {}

	public function delRef () {}

	public function getRefCount () {}

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

}

class Worker extends Thread implements Traversable, Collectable {

	public function shutdown () {}

	/**
	 * @param Threaded $work
	 */
	public function stack (Threaded $work) {}

	public function unstack () {}

	public function getStacked () {}

	public function isShutdown () {}

	/**
	 * @param Closure $function [optional]
	 */
	public function collect (Closure $function = null) {}

	/**
	 * @param Collectable $collectable
	 */
	public function collector (Collectable $collectable) {}

	/**
	 * @param $options [optional]
	 */
	public function start ($options = null) {}

	public function join () {}

	public function isStarted () {}

	public function isJoined () {}

	public function getThreadId () {}

	public function getCreatorId () {}

	public static function getCurrentThreadId () {}

	public static function getCurrentThread () {}

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function notifyOne () {}

	public function isRunning () {}

	public function isTerminated () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	/**
	 * @param $from
	 * @param $overwrite [optional]
	 */
	public function merge ($from, $overwrite = null) {}

	public function shift () {}

	/**
	 * @param $size
	 * @param $preserve [optional]
	 */
	public function chunk ($size, $preserve = null) {}

	public function pop () {}

	public function count () {}

	public function isGarbage () {}

	public function addRef () {}

	public function delRef () {}

	public function getRefCount () {}

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

}

class Pool  {
	protected $size;
	protected $class;
	protected $workers;
	protected $ctor;
	protected $last;


	/**
	 * @param $size
	 * @param $class [optional]
	 * @param $ctor [optional]
	 */
	public function __construct ($size, $class = nullarray , $ctor = null) {}

	/**
	 * @param $size
	 */
	public function resize ($size) {}

	/**
	 * @param Threaded $task
	 */
	public function submit (Threaded $task) {}

	/**
	 * @param $worker
	 * @param Threaded $task
	 */
	public function submitTo ($workerThreaded , $task) {}

	/**
	 * @param Closure $collector
	 */
	public function collect (Closure $collector) {}

	public function shutdown () {}

}

/**
 * @param $timeout
 */
function pthreads_no_sleeping ($timeout) {}

define ('PTHREADS_INHERIT_ALL', 1118481);
define ('PTHREADS_INHERIT_NONE', 0);
define ('PTHREADS_INHERIT_INI', 1);
define ('PTHREADS_INHERIT_CONSTANTS', 16);
define ('PTHREADS_INHERIT_CLASSES', 4096);
define ('PTHREADS_INHERIT_FUNCTIONS', 256);
define ('PTHREADS_INHERIT_INCLUDES', 65536);
define ('PTHREADS_INHERIT_COMMENTS', 1048576);
define ('PTHREADS_ALLOW_HEADERS', 268435456);

// End of pthreads v.3.1.6
