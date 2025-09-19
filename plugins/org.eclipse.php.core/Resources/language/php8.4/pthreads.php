<?php

/**
 * <i>Pthreads implementation stub</i>
 *
 * pthreads is an object-orientated API that provides all of the tools needed for multi-threading in PHP. PHP applications can create, read, write, execute and synchronize with Threads, Workers and Threaded objects.
 *
 * @link http://php.net/manual/en/intro.pthreads.php
 */

/**
 * Predefined constants
 */
/**
 * Do not inherit anything when new Threads are started
 */
define('PTHREADS_INHERIT_NONE', 0);
/**
 * Inherit INI entries when new Threads are started
 */
define('PTHREADS_INHERIT_INI', 0x1);
/**
 * Inherit user declared constants when new Threads are started
 */
define('PTHREADS_INHERIT_CONSTANTS', 0x10);
/**
 * Inherit user declared functions when new Threads are started
 */
define('PTHREADS_INHERIT_FUNCTIONS', 0x100);
/**
 * Inherit user declared classes when new Threads are started
 */
define('PTHREADS_INHERIT_CLASSES', 0x1000);
/**
 * Inherit included file information when new Threads are started
 */
define('PTHREADS_INHERIT_INCLUDES', 0x10000);
/**
 * Inherit all comments when new Threads are started
 */
define('PTHREADS_INHERIT_COMMENTS', 0x100000);
/**
 * The default options for all Threads, causes pthreads to copy the environment when new Threads are started
 */
define('PTHREADS_INHERIT_ALL', 0x111111);

/**
 * <i>Thread class stub</i>
 *
 * Threaded objects form the basis of pthreads ability to execute user code in parallel; they expose synchronization methods and various useful interfaces.
 *
 * Threaded objects, most importantly, provide implicit safety for the programmer; all operations on the object scope are safe.
 *
 * @link http://php.net/manual/en/class.threaded.php
 */
class Threaded implements Collectable, Traversable, Countable, ArrayAccess
{

    /**
     * Fetches a chunk of the objects property table of the given size, optionally preserving keys
     *
     * @param int $size
     *            The number of items to fetch
     * @param bool $preserve
     *            Preserve the keys of members, by default false
     * @return array An array of items from the objects property table
     *
     * @link http://php.net/manual/en/threaded.chunk.php
     */
    public function chunk(int $size, bool $preserve = false): array
    {}

    /**
     *
     * {@inheritdoc}
     * @see Countable::count()
     *
     * @return int the number of properties for this object
     *
     * @link http://php.net/manual/en/threaded.count.php
     */
    public function count(): int
    {}

    /**
     * Makes thread safe standard class at runtime
     *
     * @param string $class
     *            The class to extend
     * @return bool A boolean indication of success
     *
     * @link http://php.net/manual/en/threaded.extend.php
     */
    public function extend(string $class): bool
    {}

    /**
     * Creates an anonymous Threaded object from closures
     *
     * @deprecated This method has been removed in pthreads v3. With the introduction of anonymous classes in PHP 7, these can now be used instead.
     * @param Closure $run
     *            The closure to use for ::run
     * @param Closure $construct
     *            The constructor to use for anonymous object
     * @param array $args
     *            The arguments to pass to constructor
     * @return Threaded A new anonymous Threaded object
     *
     * @link http://php.net/manual/en/threaded.from.php
     */
    public function from(Closure $run, Closure $construct = null, array $args = array()): Threaded
    {}

    /**
     * Retrieves terminal error information from the referenced object
     *
     * @deprecated This method has been removed in pthreads v3. Instead, the body of Threaded::run() can be wrapped in a try...catch block to detect errors (since most errors in PHP 7 have been converted to exceptions).
     * @return array containing the termination conditions of the referenced object
     *
     * @link http://php.net/manual/en/threaded.getterminationinfo.php
     */
    public function getTerminationInfo(): array
    {}

    /**
     *
     * {@inheritdoc}
     * @see Collectable::isGarbage()
     */
    public function isGarbage(): bool
    {}

    /**
     * Tell if the referenced object is executing
     *
     * @return bool A boolean indication of state
     *
     * @link http://php.net/manual/en/threaded.isrunning.php
     */
    public function isRunning(): bool
    {}

    /**
     * Tell if the referenced object was terminated during execution; suffered fatal errors, or threw uncaught exceptions
     *
     * @return bool A boolean indication of state
     *
     * @link http://php.net/manual/en/threaded.isterminated.php
     */
    public function isTerminated(): bool
    {}

    /**
     * Tell if the referenced object is waiting for notification
     *
     * @return bool A boolean indication of state
     * @deprecated This method has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/threaded.iswaiting.php
     */
    public function isWaiting(): bool
    {}

    /**
     * Lock the referenced objects property table
     *
     * @return bool A boolean indication of success
     * @deprecated This method has been removed in pthreads v3. The Threaded::synchronized() method should now be used.
     * @see synchronized()
     *
     * @link http://php.net/manual/en/threaded.lock.php
     */
    public function lock(): bool
    {}

    /**
     * Merges data into the current object
     *
     * @param mixed $from
     *            The data to merge
     * @param bool $overwrite
     *            Overwrite existing keys, by default true
     * @return bool A boolean indication of success
     *
     * @link http://php.net/manual/en/threaded.merge.php
     */
    public function merge($from, bool $overwrite = true): bool
    {}

    /**
     * Send notification to the referenced object
     *
     * @return bool A boolean indication of success
     *
     * @link http://php.net/manual/en/threaded.notify.php
     */
    public function notify(): bool
    {}

    /**
     * Send notification to the referenced object.
     * This unblocks at least one of the blocked threads (as opposed to unblocking all of them, as seen with Threaded::notify()).
     *
     * @return bool A boolean indication of success
     * @see notify()
     *
     * @link http://php.net/manual/en/threaded.notifyone.php
     */
    public function notifyOne(): bool
    {}

    /**
     *
     * {@inheritdoc}
     * @see ArrayAccess::offsetExists()
     */
    public function offsetExists($offset): bool
    {}

    /**
     *
     * {@inheritdoc}
     * @see ArrayAccess::offsetGet()
     */
    public function offsetGet($offset)
    {}

    /**
     *
     * {@inheritdoc}
     * @see ArrayAccess::offsetSet()
     */
    public function offsetSet($offset, $value)
    {}

    /**
     *
     * {@inheritdoc}
     * @see ArrayAccess::offsetUnset()
     */
    public function offsetUnset($offset)
    {}

    /**
     * Pops an item from the objects property table
     *
     * @return bool The last item from the objects property table
     *
     * @link http://php.net/manual/en/threaded.pop.php
     */
    public function pop(): bool
    {}

    /**
     * The programmer should always implement the run method for objects that are intended for execution.
     *
     * @link http://php.net/manual/en/threaded.run.php
     */
    public function run()
    {}

    /**
     *
     * {@inheritdoc}
     * @see Collectable::setGarbage()
     */
    public function setGarbage(): void
    {}

    /**
     * Shifts an item from the objects property table
     *
     * @return mixed The first item from the objects property table
     *
     * @link http://php.net/manual/en/threaded.shift.php
     */
    public function shift()
    {}

    /**
     * Executes the block while retaining the referenced objects synchronization lock for the calling context
     *
     * @param Closure $block
     *            The block of code to execute
     * @param mixed $_
     *            Variable length list of arguments to use as function arguments to the block
     * @return mixed The return value from the block
     *
     * @link http://php.net/manual/en/threaded.synchronized.php
     */
    public function synchronized(Closure $block, $_ = null)
    {}

    /**
     * Unlock the referenced objects storage for the calling context
     *
     * @return bool Unlock the referenced objects storage for the calling context
     * @deprecated This method has been removed in pthreads v3. The Threaded::synchronized() method should now be used.
     *
     * @link http://php.net/manual/en/threaded.unlock.php
     */
    public function unlock(): bool
    {}

    /**
     * Will cause the calling context to wait for notification from the referenced object
     *
     * @param int $timout
     *            An optional timeout in microseconds
     * @return bool A boolean indication of success
     *
     * @link http://php.net/manual/en/threaded.wait.php
     */
    public function wait(int $timout): bool
    {}
}

/**
 * <i>Thread class stub</i>
 *
 * <p>
 * When the start method of a Thread is invoked, the run method code will be executed in separate Thread, in parallel.
 *
 * After the run method is executed the Thread will exit immediately, it will be joined with the creating Thread at the appropriate time.
 * </p>
 * <b>WARNING</b> Relying on the engine to determine when a Thread should join may cause undesirable behaviour; the programmer should be explicit, where possible.
 *
 * @link http://php.net/manual/en/class.thread.php
 */
class Thread extends Threaded implements Countable, Traversable, ArrayAccess
{

    /**
     * Detaches the referenced Thread from the calling context, dangerous!
     *
     * <b>WARNING</b> This method can cause undefined, unsafe behaviour. It should not usually be used, it is present for completeness and advanced use cases.
     *
     * @deprecated This method has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/thread.detach.php
     */
    public function detach(): void
    {}

    /**
     * Will return the identity of the Thread that created the referenced Thread
     *
     * @return int A numeric identity
     *
     * @link http://php.net/manual/en/thread.getcreatorid.php
     */
    public function getCreatorId(): int
    {}

    /**
     * Return a reference to the currently executing Thread
     *
     * @return Thread An object representing the currently executing Thread
     *
     * @link http://php.net/manual/en/thread.getcurrentthread.php
     */
    public function getCurrentThread(): Thread
    {}

    /**
     * Will return the identity of the currently executing Thread
     *
     * @return int A numeric identity
     *
     * @link http://php.net/manual/en/thread.getcurrentthreadid.php
     */
    public function getCurrentThreadId(): int
    {}

    /**
     * Will return the identity of the referenced Thread
     *
     * @return int A numeric identity
     *
     * @link http://php.net/manual/en/thread.getthreadid.php
     */
    public function getThreadId(): int
    {}

    /**
     * Will execute a Callable in the global scope
     *
     * @return mixed The return value of the Callable
     * @deprecated This method has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/thread.globally.php
     */
    public static function globally()
    {}

    /**
     * Tell if the referenced Thread has been joined
     *
     * @return bool A boolean indication of state
     *
     * @link http://php.net/manual/en/thread.isjoined.php
     */
    public function isJoined(): bool
    {}

    /**
     * Tell if the referenced Thread was started
     *
     * @return bool boolean indication of state
     *
     * @link http://php.net/manual/en/thread.isstarted.php
     */
    public function isStarted(): bool
    {}

    /**
     * Causes the calling context to wait for the referenced Thread to finish executing
     *
     * @return bool A boolean indication of success
     *
     * @link http://php.net/manual/en/thread.join.php
     */
    public function join(): bool
    {}

    /**
     * Forces the referenced Thread to terminate
     * <b>WARNING</b> The programmer should not ordinarily kill Threads by force
     *
     * @deprecated This method has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/thread.kill.php
     */
    public function kill(): void
    {}

    /**
     * Will start a new Thread to execute the implemented run method
     *
     * @param int $options
     *            An optional mask of inheritance constants, by default PTHREADS_INHERIT_ALL
     * @return bool A boolean indication of success
     *
     * @link http://php.net/manual/en/thread.start.php
     */
    public function start(int $options = PTHREADS_INHERIT_ALL): bool
    {}
}

/**
 * <i>Worker class stub</i>
 *
 * Worker Threads have a persistent context, as such should be used over Threads in most cases.
 *
 * When a Worker is started, the run method will be executed, but the Thread will not leave until one of the following conditions are met:
 * <ul>
 * <li>the Worker goes out of scope (no more references remain)</li>
 * <li>the programmer calls shutdown</li>
 * <li>the script dies</li>
 * </ul>
 *
 * This means the programmer can reuse the context throughout execution; placing objects on the stack of the Worker will cause the Worker to execute the stacked objects run method.
 *
 * @link http://php.net/manual/en/class.worker.php
 */
class Worker extends Thread implements Traversable, Countable, ArrayAccess
{

    /**
     * Allows the worker to collect references determined to be garbage by the optionally given collector.
     *
     * @param callable $collector
     *            A Callable collector that returns a boolean on whether the task can be collected or not. Only in rare cases should a custom collector need to be used.
     * @return int The number of remaining tasks on the worker's stack to be collected.
     *
     * @link http://php.net/manual/en/worker.collect.php
     */
    public function collect(Callable $collector = null): int
    {}

    /**
     * Returns the number of tasks left on the stack
     *
     * @return int Returns the number of tasks currently waiting to be executed by the worker
     *
     * @link http://php.net/manual/en/worker.getstacked.php
     */
    public function getStacked(): int
    {}

    /**
     * Whether the worker has been shutdown or not.
     *
     * @return bool Returns whether the worker has been shutdown or not.
     *
     * @link http://php.net/manual/en/worker.isshutdown.php
     */
    public function isShutdown(): bool
    {}

    /**
     * Tell if a Worker is executing Stackables
     *
     * @return bool A boolean indication of state
     * @deprecated This method has been removed in pthreads v3. The Worker::getStacked() method should be used to see if the worker still has tasks to execute.
     *
     * @see Worker::getStacked()
     *
     * @link http://php.net/manual/en/worker.isworking.php
     */
    public function isWorking(): bool
    {}

    /**
     * Shuts down the worker after executing all of the stacked tasks.
     *
     * @return bool Whether the worker was successfully shutdown or not.
     *
     * @link http://php.net/manual/en/worker.shutdown.php
     */
    public function shutdown(): bool
    {}

    /**
     * Appends the new work to the stack of the referenced worker.
     *
     * @param Threaded $worker
     *            A Threaded object to be executed by the worker.
     * @return int The new size of the stack.
     *
     * @link http://php.net/manual/en/worker.stack.php
     */
    public function stack(Threaded $worker): int
    {}

    /**
     * Removes the first task (the oldest one) in the stack.
     *
     * @return int The new size of the stack.
     *
     * @link http://php.net/manual/en/worker.unstack.php
     */
    public function unstack(): int
    {}
}

/**
 * <i>Collectable interface stub</i>
 *
 * Represents a garbage-collectable object.
 *
 * <b>CAUTION</b> Collectable was previously a class (in pthreads v2 and below). Now, it is an interface in pthreads v3 that is implemented by the Threaded class.
 *
 * @link http://php.net/manual/en/class.collectable.php
 */
interface Collectable
{

    /**
     * Determine whether an object has been marked as garbage
     *
     * Can be called in Pool::collect() to determine if this object is garbage.
     *
     * @see Pool::collect()
     *
     * @return bool A boolean indication of state
     *
     * @link http://php.net/manual/en/collectable.isgarbage.php
     */
    function isGarbage(): bool;

    /**
     * Mark an object as garbage
     *
     * Should be called once per object when the object is finished being executed or referenced.
     *
     * @deprecated This method has been removed in pthreads v3.
     *
     * @see Collectable::isGarbage()
     *
     * @link http://php.net/manual/en/collectable.setgarbage.php
     */
    function setGarbage(): void;
}

/**
 * <i>Pool class stub</i>
 *
 * A Pool is a container for, and controller of, an adjustable number of Workers.
 *
 * Pooling provides a higher level abstraction of the Worker functionality, including the management of references in the way required by pthreads.
 *
 * @link http://php.net/manual/en/class.pool.php
 */
class Pool
{

    /**
     * maximum number of Workers this Pool can use
     *
     * @var int
     */
    protected $size;

    /**
     * the class of the Worker
     *
     * @var stdClass
     */
    protected $class;

    /**
     * references to Workers
     *
     * @var array
     */
    protected $workers;

    /**
     * references to Threaded objects submitted to the Pool
     *
     * @var array
     */
    protected $work;

    /**
     * the arguments for constructor of new Workers
     *
     * @var array
     */
    protected $ctor;

    /**
     * offset in workers of the last Worker used
     *
     * @var int
     */
    protected $last;

    /**
     * Collect references to completed tasks
     *
     * @param callable $collector
     *            A Callable collector that returns a boolean on whether the task can be collected or not. Only in rare cases should a custom collector need to be used.
     * @return int The number of remaining tasks in the pool to be collected.
     *
     * @link http://php.net/manual/en/pool.collect.php
     */
    public function collect(Callable $collector = null): int
    {}

    /**
     * Construct a new pool of workers.
     * Pools lazily create their threads, which means new threads will only be spawned when they are required to execute tasks.
     *
     * @param int $size
     *            The maximum number of workers for this pool to create
     * @param string $class
     *            The class for new Workers. If no class is given, then it defaults to the Worker class.
     * @param array $ctor
     *            An array of arguments to be passed to new workers' constructors
     *
     * @link http://php.net/manual/en/pool.construct.php
     */
    public function __construct(int $size, string $class = "", array $ctor = array())
    {}

    /**
     * Resize the Pool
     *
     * @param int $size
     *            The maximum number of Workers this Pool can create
     *
     * @link http://php.net/manual/en/pool.resize.php
     */
    public function resize(int $size): void
    {}

    /**
     * Shuts down all of the workers in the pool.
     * This will block until all submitted tasks have been executed.
     *
     * @link http://php.net/manual/en/pool.shutdown.php
     */
    public function shutdown(): void
    {}

    /**
     * Submit the task to the next Worker in the Pool
     *
     * @param Threaded $task
     *            The task for execution
     * @return int the identifier of the Worker executing the object
     *
     * @link http://php.net/manual/en/pool.submit.php
     */
    public function submit(Threaded $task): int
    {}

    /**
     * Submit a task to the specified worker in the pool.
     * The workers are indexed from 0, and will only exist if the pool has needed to create them (since threads are lazily spawned).
     *
     * @param int $worker
     *            The worker to stack the task onto, indexed from 0.
     * @param Threaded $task
     *            The task for execution.
     * @return int The identifier of the worker that accepted the task.
     *
     * @link http://php.net/manual/en/pool.submitTo.php
     */
    public function submitTo(int $worker, Threaded $task): int
    {}
}

/**
 * <i>Mutex class stub</i>
 *
 * @deprecated The Mutex class has been removed in pthreads v3.
 *
 * @link http://php.net/manual/en/class.mutex.php
 */
class Mutex
{

    /**
     * Create, and optionally lock a new Mutex for the caller
     *
     * @param bool $lock
     *            Setting lock to true will lock the Mutex for the caller before returning the handle
     * @return int
     * @deprecated The Mutex class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/mutex.create.php
     */
    public static final function create(bool $lock = false): int
    {}

    /**
     * Destroying Mutex handles must be carried out explicitly by the programmer when they are finished with the Mutex handle.
     *
     * @param int $mutex
     *            A handle returned by a previous call to Mutex::create(). The handle should not be locked by any Thread when Mutex::destroy() is called.
     * @return bool A boolean indication of success
     * @deprecated The Mutex class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/mutex.destroy.php
     */
    public static final function destroy(int $mutex): bool
    {}

    /**
     * Attempt to lock the Mutex for the caller.
     *
     * An attempt to lock a Mutex owned (locked) by another Thread will result in blocking.
     *
     * @param int $mutex
     *            A handle returned by a previous call to Mutex::create().
     * @return bool A boolean indication of success.
     * @deprecated The Mutex class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/mutex.lock.php
     */
    public static final function lock(int $mutex): bool
    {}

    /**
     * Attempt to lock the Mutex for the caller without blocking if the Mutex is owned (locked) by another Thread.
     *
     * @param int $mutex
     *            A handle returned by a previous call to Mutex::create().
     * @return bool A boolean indication of success.
     *
     * @deprecated The Mutex class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/mutex.trylock.php
     */
    public static final function trylock(int $mutex): bool
    {}

    /**
     * Attempts to unlock the Mutex for the caller, optionally destroying the Mutex handle.
     * The calling thread should own the Mutex at the time of the call.
     *
     * @param int $mutex
     *            A handle returned by a previous call to Mutex::create().
     * @param bool $destroy
     *            When true pthreads will destroy the Mutex after a successful unlock.
     * @return bool A boolean indication of success.
     *
     * @deprecated The Mutex class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/mutex.unlock.php
     */
    public static final function unlock(int $mutex, bool $destroy = true): bool
    {}
}

/**
 * <i>Cond class stub</i>
 *
 * The static methods contained in the Cond class provide direct access to Posix Condition Variables.
 *
 * @deprecated The Cond class has been removed in pthreads v3.
 *
 * @link http://php.net/manual/en/class.cond.php
 */
class Cond
{

    /**
     * Broadcast to all Threads blocking on a call to Cond::wait().
     *
     * @param int $condition
     *            A handle to a Condition Variable returned by a previous call to Cond::create()
     * @return bool A boolean indication of success.
     *
     * @deprecated The Cond class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/cond.broadcast.php
     */
    public static final function broadcast(int $condition): bool
    {}

    /**
     * Creates a new Condition Variable for the caller.
     *
     * @return int A handle to a Condition Variable
     *
     * @deprecated The Cond class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/cond.create.php
     */
    public static final function create(): int
    {}

    /**
     * Destroying Condition Variable handles must be carried out explicitly by the programmer when they are finished with the Condition Variable.
     * No Threads should be blocking on a call to Cond::wait() when the call to Cond::destroy() takes place.
     *
     * @param int $condition
     *            A handle to a Condition Variable returned by a previous call to Cond::create()
     * @return bool A boolean indication of success.
     *
     * @deprecated The Cond class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/cond.destroy.php
     */
    public static final function destroy(int $condition): bool
    {}

    /**
     * Signal a Condition
     *
     * @param int $condition
     *            A handle returned by a previous call to Cond::create()
     * @return bool A boolean indication of success.
     *
     * @deprecated The Cond class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/cond.signal.php
     */
    public static final function signal(int $condition): bool
    {}

    /**
     * Wait for a signal on a Condition Variable, optionally specifying a timeout to limit waiting time.
     *
     * @param int $condition
     *            A handle returned by a previous call to Cond::create().
     * @param int $mutex
     *            A handle returned by a previous call to Mutex::create() and owned (locked) by the caller.
     * @param int $timeout
     *            An optional timeout, in microseconds ( millionths of a second ).
     * @return bool A boolean indication of success.
     *
     * @deprecated The Cond class has been removed in pthreads v3.
     *
     * @link http://php.net/manual/en/cond.wait.php
     */
    public static final function wait(int $condition, int $mutex, int $timeout = 0): bool
    {}
}

/**
 * The Volatile class is new to pthreads v3.
 * Its introduction is a consequence of the new immutability semantics of Threaded members of Threaded classes. The Volatile class enables for mutability of its Threaded members, and is also used to store PHP arrays in Threaded contexts.
 *
 * @link http://php.net/manual/en/class.volatile.php
 */
class Volatile extends Threaded implements Collectable, Traversable
{
}