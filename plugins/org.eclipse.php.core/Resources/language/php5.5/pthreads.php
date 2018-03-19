<?php

// Start of pthreads v.2.0.10

class Threaded implements Traversable, Countable {

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function isRunning () {}

	public function isWaiting () {}

	public function isTerminated () {}

	public function getTerminationInfo () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	public function lock () {}

	public function unlock () {}

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

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

	/**
	 * @param Closure $run
	 * @param Closure $construct [optional]
	 * @param $args [optional]
	 */
	public static function from (Closure $runClosure , $construct = null, $args = null) {}

}

class Threaded implements Traversable, Countable {

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function isRunning () {}

	public function isWaiting () {}

	public function isTerminated () {}

	public function getTerminationInfo () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	public function lock () {}

	public function unlock () {}

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

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

	/**
	 * @param Closure $run
	 * @param Closure $construct [optional]
	 * @param $args [optional]
	 */
	public static function from (Closure $runClosure , $construct = null, $args = null) {}

}

class Thread extends Threaded implements Countable, Traversable {

	/**
	 * @param $options [optional]
	 */
	public function start ($options = null) {}

	public function join () {}

	public function detach () {}

	public function isStarted () {}

	public function isJoined () {}

	public function getThreadId () {}

	public function getCreatorId () {}

	public static function getCurrentThreadId () {}

	public static function getCurrentThread () {}

	public function kill () {}

	/**
	 * @param $block
	 * @param $args [optional]
	 */
	public static function globally ($block, $args = null) {}

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function isRunning () {}

	public function isWaiting () {}

	public function isTerminated () {}

	public function getTerminationInfo () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	public function lock () {}

	public function unlock () {}

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

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

	/**
	 * @param Closure $run
	 * @param Closure $construct [optional]
	 * @param $args [optional]
	 */
	public static function from (Closure $runClosure , $construct = null, $args = null) {}

}

class Worker extends Thread implements Traversable, Countable {

	public function shutdown () {}

	/**
	 * @param $work
	 */
	public function stack (&$work) {}

	/**
	 * @param $work [optional]
	 */
	public function unstack (&$work = null) {}

	public function getStacked () {}

	public function isShutdown () {}

	public function isWorking () {}

	/**
	 * @param $options [optional]
	 */
	public function start ($options = null) {}

	public function join () {}

	public function detach () {}

	public function isStarted () {}

	public function isJoined () {}

	public function getThreadId () {}

	public function getCreatorId () {}

	public static function getCurrentThreadId () {}

	public static function getCurrentThread () {}

	public function kill () {}

	/**
	 * @param $block
	 * @param $args [optional]
	 */
	public static function globally ($block, $args = null) {}

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function isRunning () {}

	public function isWaiting () {}

	public function isTerminated () {}

	public function getTerminationInfo () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	public function lock () {}

	public function unlock () {}

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

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

	/**
	 * @param Closure $run
	 * @param Closure $construct [optional]
	 * @param $args [optional]
	 */
	public static function from (Closure $runClosure , $construct = null, $args = null) {}

}

class Mutex  {

	final public function __construct () {}

	/**
	 * @param $lock [optional]
	 */
	final public static function create ($lock = null) {}

	/**
	 * @param $mutex
	 */
	final public static function lock ($mutex) {}

	/**
	 * @param $mutex
	 */
	final public static function trylock ($mutex) {}

	/**
	 * @param $mutex
	 * @param $destroy [optional]
	 */
	final public static function unlock ($mutex, $destroy = null) {}

	/**
	 * @param $mutex
	 */
	final public static function destroy ($mutex) {}

}

class Cond  {

	final public function __construct () {}

	final public static function create () {}

	/**
	 * @param $condition
	 */
	final public static function signal ($condition) {}

	/**
	 * @param $condition
	 * @param $mutex
	 * @param $timeout [optional]
	 */
	final public static function wait ($condition, $mutex, $timeout = null) {}

	/**
	 * @param $condition
	 */
	final public static function broadcast ($condition) {}

	/**
	 * @param $condition
	 */
	final public static function destroy ($condition) {}

}

class Collectable extends Threaded implements Countable, Traversable {
	protected $garbage;


	public function isGarbage () {}

	public function setGarbage () {}

	public function run () {}

	/**
	 * @param $timeout [optional]
	 */
	public function wait ($timeout = null) {}

	public function notify () {}

	public function isRunning () {}

	public function isWaiting () {}

	public function isTerminated () {}

	public function getTerminationInfo () {}

	/**
	 * @param $function
	 */
	public function synchronized ($function) {}

	public function lock () {}

	public function unlock () {}

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

	/**
	 * @param $class
	 */
	public static function extend ($class) {}

	/**
	 * @param Closure $run
	 * @param Closure $construct [optional]
	 * @param $args [optional]
	 */
	public static function from (Closure $runClosure , $construct = null, $args = null) {}

}

class Pool  {
	protected $size;
	protected $class;
	protected $workers;
	protected $work;
	protected $ctor;
	protected $last;


	/**
	 * @param $size
	 * @param $class [optional]
	 * @param $ctor [optional]
	 */
	public function __construct ($size, $class = null, $ctor = null) {}

	/**
	 * @param $size
	 */
	public function resize ($size) {}

	/**
	 * @param $task
	 */
	public function submit ($task) {}

	/**
	 * @param $worker
	 * @param $task
	 */
	public function submitTo ($worker, $task) {}

	/**
	 * @param $collector
	 */
	public function collect ($collector) {}

	public function shutdown () {}

}
define ('PTHREADS_INHERIT_ALL', 1118481);
define ('PTHREADS_INHERIT_NONE', 0);
define ('PTHREADS_INHERIT_INI', 1);
define ('PTHREADS_INHERIT_CONSTANTS', 16);
define ('PTHREADS_INHERIT_CLASSES', 4096);
define ('PTHREADS_INHERIT_FUNCTIONS', 256);
define ('PTHREADS_INHERIT_INCLUDES', 65536);
define ('PTHREADS_INHERIT_COMMENTS', 1048576);
define ('PTHREADS_ALLOW_HEADERS', 268435456);
define ('PTHREADS_ALLOW_GLOBALS', 16777216);

// End of pthreads v.2.0.10
