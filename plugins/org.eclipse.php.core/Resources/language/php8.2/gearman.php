<?php

// Start of gearman v.2.1.0

/**
 * Represents a class for connecting to a Gearman job server and making requests to perform
 * some function on provided data. The function performed must be one registered by a Gearman
 * worker and the data passed is opaque to the job server.
 * @link http://www.php.net/manual/en/class.gearmanclient.php
 */
class GearmanClient  {

	/**
	 * Create a GearmanClient instance
	 * @link http://www.php.net/manual/en/gearmanclient.construct.php
	 * @return void A GearmanClient object.
	 */
	public function __construct (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Get the last Gearman return code
	 * @link http://www.php.net/manual/en/gearmanclient.returncode.php
	 * @return int A valid Gearman return code.
	 */
	public function returnCode (): int {}

	/**
	 * Returns an error string for the last error encountered
	 * @link http://www.php.net/manual/en/gearmanclient.error.php
	 * @return string A human readable error string.
	 */
	public function error (): string {}

	/**
	 * Get an errno value
	 * @link http://www.php.net/manual/en/gearmanclient.geterrno.php
	 * @return int A valid Gearman errno.
	 */
	public function getErrno (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function options (): int {}

	/**
	 * Set client options
	 * @link http://www.php.net/manual/en/gearmanclient.setoptions.php
	 * @param int $options 
	 * @return bool Always returns true.
	 */
	public function setOptions (int $options): bool {}

	/**
	 * Add client options
	 * @link http://www.php.net/manual/en/gearmanclient.addoptions.php
	 * @param int $options 
	 * @return bool Always returns true.
	 */
	public function addOptions (int $options): bool {}

	/**
	 * Remove client options
	 * @link http://www.php.net/manual/en/gearmanclient.removeoptions.php
	 * @param int $options 
	 * @return bool Always returns true.
	 */
	public function removeOptions (int $options): bool {}

	/**
	 * Get current socket I/O activity timeout value
	 * @link http://www.php.net/manual/en/gearmanclient.timeout.php
	 * @return int Timeout in milliseconds to wait for I/O activity. A negative value means an infinite timeout.
	 */
	public function timeout (): int {}

	/**
	 * Set socket I/O activity timeout
	 * @link http://www.php.net/manual/en/gearmanclient.settimeout.php
	 * @param int $timeout 
	 * @return bool Always returns true.
	 */
	public function setTimeout (int $timeout): bool {}

	/**
	 * Add a job server to the client
	 * @link http://www.php.net/manual/en/gearmanclient.addserver.php
	 * @param string $host [optional] 
	 * @param int $port [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServer (string $host = '127.0.0.1', int $port = 4730): bool {}

	/**
	 * Add a list of job servers to the client
	 * @link http://www.php.net/manual/en/gearmanclient.addservers.php
	 * @param string $servers [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServers (string $servers = '127.0.0.1:4730'): bool {}

	/**
	 * Wait for I/O activity on all connections in a client
	 * @link http://www.php.net/manual/en/gearmanclient.wait.php
	 * @return bool true on success false on an error.
	 */
	public function wait (): bool {}

	/**
	 * Run a single task and return a result
	 * @link http://www.php.net/manual/en/gearmanclient.donormal.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param string $unique [optional] 
	 * @return string A string representing the results of running a task.
	 */
	public function doNormal (string $function_name, string $workload, string $unique = null): string {}

	/**
	 * Run a single high priority task
	 * @link http://www.php.net/manual/en/gearmanclient.dohigh.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param string $unique [optional] 
	 * @return string A string representing the results of running a task.
	 */
	public function doHigh (string $function_name, string $workload, string $unique = null): string {}

	/**
	 * Run a single low priority task
	 * @link http://www.php.net/manual/en/gearmanclient.dolow.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param string $unique [optional] 
	 * @return string A string representing the results of running a task.
	 */
	public function dolow (string $function_name, string $workload, string $unique = null): string {}

	/**
	 * Run a task in the background
	 * @link http://www.php.net/manual/en/gearmanclient.dobackground.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param string $unique [optional] 
	 * @return string The job handle for the submitted task.
	 */
	public function doBackground (string $function_name, string $workload, string $unique = null): string {}

	/**
	 * Run a high priority task in the background
	 * @link http://www.php.net/manual/en/gearmanclient.dohighbackground.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param string $unique [optional] 
	 * @return string The job handle for the submitted task.
	 */
	public function doHighBackground (string $function_name, string $workload, string $unique = null): string {}

	/**
	 * Run a low priority task in the background
	 * @link http://www.php.net/manual/en/gearmanclient.dolowbackground.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param string $unique [optional] 
	 * @return string The job handle for the submitted task.
	 */
	public function doLowBackground (string $function_name, string $workload, string $unique = null): string {}

	/**
	 * Get the job handle for the running task
	 * @link http://www.php.net/manual/en/gearmanclient.dojobhandle.php
	 * @return string The job handle for the running task.
	 */
	public function doJobHandle (): string {}

	/**
	 * Get the status for the running task
	 * @link http://www.php.net/manual/en/gearmanclient.dostatus.php
	 * @return array An array representing the percentage completion given as a fraction, with the
	 * first element the numerator and the second element the denomintor.
	 */
	public function doStatus (): array {}

	/**
	 * Get the status of a background job
	 * @link http://www.php.net/manual/en/gearmanclient.jobstatus.php
	 * @param string $job_handle 
	 * @return array An array containing status information for the job corresponding to the supplied
	 * job handle. The first array element is a boolean indicating whether the job is
	 * even known, the second is a boolean indicating whether the job is still running,
	 * and the third and fourth elements correspond to the numerator and denominator
	 * of the fractional completion percentage, respectively.
	 */
	public function jobStatus (string $job_handle): array {}

	/**
	 * {@inheritdoc}
	 * @param string $unique_key
	 */
	public function jobStatusByUniqueKey (string $unique_key): array {}

	/**
	 * Send data to all job servers to see if they echo it back
	 * @link http://www.php.net/manual/en/gearmanclient.ping.php
	 * @param string $workload 
	 * @return bool Returns true on success or false on failure.
	 */
	public function ping (string $workload): bool {}

	/**
	 * Add a task to be run in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.addtask.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param mixed $context [optional] 
	 * @param string $unique [optional] 
	 * @return GearmanTask A GearmanTask object or false if the task could not be added.
	 */
	public function addTask (string $function_name, string $workload, mixed &$context = null, string $unique = null): GearmanTask {}

	/**
	 * Add a high priority task to run in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.addtaskhigh.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param mixed $context [optional] 
	 * @param string $unique [optional] 
	 * @return GearmanTask A GearmanTask object or false if the task could not be added.
	 */
	public function addTaskHigh (string $function_name, string $workload, mixed &$context = null, string $unique = null): GearmanTask {}

	/**
	 * Add a low priority task to run in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.addtasklow.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param mixed $context [optional] 
	 * @param string $unique [optional] 
	 * @return GearmanTask A GearmanTask object or false if the task could not be added.
	 */
	public function addTaskLow (string $function_name, string $workload, mixed &$context = null, string $unique = null): GearmanTask {}

	/**
	 * Add a background task to be run in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.addtaskbackground.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param mixed $context [optional] 
	 * @param string $unique [optional] 
	 * @return GearmanTask A GearmanTask object or false if the task could not be added.
	 */
	public function addTaskBackground (string $function_name, string $workload, mixed &$context = null, string $unique = null): GearmanTask {}

	/**
	 * Add a high priority background task to be run in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.addtaskhighbackground.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param mixed $context [optional] 
	 * @param string $unique [optional] 
	 * @return GearmanTask A GearmanTask object or false if the task could not be added.
	 */
	public function addTaskHighBackground (string $function_name, string $workload, mixed &$context = null, string $unique = null): GearmanTask {}

	/**
	 * Add a low priority background task to be run in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.addtasklowbackground.php
	 * @param string $function_name 
	 * @param string $workload 
	 * @param mixed $context [optional] 
	 * @param string $unique [optional] 
	 * @return GearmanTask A GearmanTask object or false if the task could not be added.
	 */
	public function addTaskLowBackground (string $function_name, string $workload, mixed &$context = null, string $unique = null): GearmanTask {}

	/**
	 * Run a list of tasks in parallel
	 * @link http://www.php.net/manual/en/gearmanclient.runtasks.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function runTasks (): bool {}

	/**
	 * Add a task to get status
	 * @link http://www.php.net/manual/en/gearmanclient.addtaskstatus.php
	 * @param string $job_handle 
	 * @param string $context [optional] 
	 * @return GearmanTask A GearmanTask object.
	 */
	public function addTaskStatus (string $job_handle, string &$context = null): GearmanTask {}

	/**
	 * Set a callback for accepting incremental data updates
	 * @link http://www.php.net/manual/en/gearmanclient.setworkloadcallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setWorkloadCallback (callable $callback): bool {}

	/**
	 * Set a callback for when a task is queued
	 * @link http://www.php.net/manual/en/gearmanclient.setcreatedcallback.php
	 * @param string $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCreatedCallback (string $callback): bool {}

	/**
	 * Callback function when there is a data packet for a task
	 * @link http://www.php.net/manual/en/gearmanclient.setdatacallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setDataCallback (callable $callback): bool {}

	/**
	 * Set a callback for worker warnings
	 * @link http://www.php.net/manual/en/gearmanclient.setwarningcallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setWarningCallback (callable $callback): bool {}

	/**
	 * Set a callback for collecting task status
	 * @link http://www.php.net/manual/en/gearmanclient.setstatuscallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setStatusCallback (callable $callback): bool {}

	/**
	 * Set a function to be called on task completion
	 * @link http://www.php.net/manual/en/gearmanclient.setcompletecallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCompleteCallback (callable $callback): bool {}

	/**
	 * Set a callback for worker exceptions
	 * @link http://www.php.net/manual/en/gearmanclient.setexceptioncallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setExceptionCallback (callable $callback): bool {}

	/**
	 * Set callback for job failure
	 * @link http://www.php.net/manual/en/gearmanclient.setfailcallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setFailCallback (callable $callback): bool {}

	/**
	 * Clear all task callback functions
	 * @link http://www.php.net/manual/en/gearmanclient.clearcallbacks.php
	 * @return bool Always returns true.
	 */
	public function clearCallbacks (): bool {}

	/**
	 * Get the application context
	 * @link http://www.php.net/manual/en/gearmanclient.context.php
	 * @return string The same context data structure set with GearmanClient::setContext
	 */
	public function context (): string {}

	/**
	 * Set application context
	 * @link http://www.php.net/manual/en/gearmanclient.setcontext.php
	 * @param string $context 
	 * @return bool Always returns true.
	 */
	public function setContext (string $context): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function enableExceptionHandler (): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.gearmantask.php
 */
class GearmanTask  {

	/**
	 * Create a GearmanTask instance
	 * @link http://www.php.net/manual/en/gearmantask.construct.php
	 * @return void A GearmanTask object.
	 */
	public function __construct (): void {}

	/**
	 * Get the last return code
	 * @link http://www.php.net/manual/en/gearmantask.returncode.php
	 * @return int A valid Gearman return code.
	 */
	public function returnCode (): int {}

	/**
	 * Get associated function name
	 * @link http://www.php.net/manual/en/gearmantask.functionname.php
	 * @return string A function name.
	 */
	public function functionName (): string {}

	/**
	 * Get the unique identifier for a task
	 * @link http://www.php.net/manual/en/gearmantask.unique.php
	 * @return string The unique identifier, or false if no identifier is assigned.
	 */
	public function unique (): string {}

	/**
	 * Get the job handle
	 * @link http://www.php.net/manual/en/gearmantask.jobhandle.php
	 * @return string The opaque job handle.
	 */
	public function jobHandle (): string {}

	/**
	 * Determine if task is known
	 * @link http://www.php.net/manual/en/gearmantask.isknown.php
	 * @return bool true if the task is known, false otherwise.
	 */
	public function isKnown (): bool {}

	/**
	 * Test whether the task is currently running
	 * @link http://www.php.net/manual/en/gearmantask.isrunning.php
	 * @return bool true if the task is running, false otherwise.
	 */
	public function isRunning (): bool {}

	/**
	 * Get completion percentage numerator
	 * @link http://www.php.net/manual/en/gearmantask.tasknumerator.php
	 * @return int A number between 0 and 100, or false if cannot be determined.
	 */
	public function taskNumerator (): int {}

	/**
	 * Get completion percentage denominator
	 * @link http://www.php.net/manual/en/gearmantask.taskdenominator.php
	 * @return int A number between 0 and 100, or false if cannot be determined.
	 */
	public function taskDenominator (): int {}

	/**
	 * Get data returned for a task
	 * @link http://www.php.net/manual/en/gearmantask.data.php
	 * @return string The serialized data, or false if no data is present.
	 */
	public function data (): string {}

	/**
	 * Get the size of returned data
	 * @link http://www.php.net/manual/en/gearmantask.datasize.php
	 * @return int The data size, or false if there is no data.
	 */
	public function dataSize (): int {}

	/**
	 * Send data for a task
	 * @link http://www.php.net/manual/en/gearmantask.sendworkload.php
	 * @param string $data 
	 * @return int The length of data sent, or false if the send failed.
	 */
	public function sendWorkload (string $data): int {}

	/**
	 * Read work or result data into a buffer for a task
	 * @link http://www.php.net/manual/en/gearmantask.recvdata.php
	 * @param int $data_len 
	 * @return array An array whose first element is the length of data read and the second is the data buffer.
	 * Returns false if the read failed.
	 */
	public function recvData (int $data_len): array {}

}

/**
 * @link http://www.php.net/manual/en/class.gearmanworker.php
 */
class GearmanWorker  {

	/**
	 * Create a GearmanWorker instance
	 * @link http://www.php.net/manual/en/gearmanworker.construct.php
	 * @return void A GearmanWorker object
	 */
	public function __construct (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Get last Gearman return code
	 * @link http://www.php.net/manual/en/gearmanworker.returncode.php
	 * @return int A valid Gearman return code.
	 */
	public function returnCode (): int {}

	/**
	 * Get the last error encountered
	 * @link http://www.php.net/manual/en/gearmanworker.error.php
	 * @return string An error string.
	 */
	public function error (): string {}

	/**
	 * Get errno
	 * @link http://www.php.net/manual/en/gearmanworker.geterrno.php
	 * @return int A valid errno.
	 */
	public function getErrno (): int {}

	/**
	 * Get worker options
	 * @link http://www.php.net/manual/en/gearmanworker.options.php
	 * @return int The options currently set for the worker.
	 */
	public function options (): int {}

	/**
	 * Set worker options
	 * @link http://www.php.net/manual/en/gearmanworker.setoptions.php
	 * @param int $option 
	 * @return bool Always returns true.
	 */
	public function setOptions (int $option): bool {}

	/**
	 * Add worker options
	 * @link http://www.php.net/manual/en/gearmanworker.addoptions.php
	 * @param int $option 
	 * @return bool Always returns true.
	 */
	public function addOptions (int $option): bool {}

	/**
	 * Remove worker options
	 * @link http://www.php.net/manual/en/gearmanworker.removeoptions.php
	 * @param int $option 
	 * @return bool Always returns true.
	 */
	public function removeOptions (int $option): bool {}

	/**
	 * Get socket I/O activity timeout
	 * @link http://www.php.net/manual/en/gearmanworker.timeout.php
	 * @return int A time period is milliseconds. A negative value indicates an infinite timeout.
	 */
	public function timeout (): int {}

	/**
	 * Set socket I/O activity timeout
	 * @link http://www.php.net/manual/en/gearmanworker.settimeout.php
	 * @param int $timeout 
	 * @return bool Always returns true.
	 */
	public function setTimeout (int $timeout): bool {}

	/**
	 * Give the worker an identifier so it can be tracked when asking gearmand for the list of available workers
	 * @link http://www.php.net/manual/en/gearmanworker.setid.php
	 * @param string $id A string identifier.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setId (string $id): bool {}

	/**
	 * Add a job server
	 * @link http://www.php.net/manual/en/gearmanworker.addserver.php
	 * @param string $host [optional] 
	 * @param int $port [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServer (string $host = '127.0.0.1', int $port = 4730): bool {}

	/**
	 * Add job servers
	 * @link http://www.php.net/manual/en/gearmanworker.addservers.php
	 * @param string $servers 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServers (string $servers): bool {}

	/**
	 * Wait for activity from one of the job servers
	 * @link http://www.php.net/manual/en/gearmanworker.wait.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function wait (): bool {}

	/**
	 * Register a function with the job server
	 * @link http://www.php.net/manual/en/gearmanworker.register.php
	 * @param string $function_name 
	 * @param int $timeout [optional] 
	 * @return bool A standard Gearman return value.
	 */
	public function register (string $function_name, int $timeout = null): bool {}

	/**
	 * Unregister a function name with the job servers
	 * @link http://www.php.net/manual/en/gearmanworker.unregister.php
	 * @param string $function_name 
	 * @return bool A standard Gearman return value.
	 */
	public function unregister (string $function_name): bool {}

	/**
	 * Unregister all function names with the job servers
	 * @link http://www.php.net/manual/en/gearmanworker.unregisterall.php
	 * @return bool A standard Gearman return value.
	 */
	public function unregisterAll (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function grabJob (): GearmanWorker|false {}

	/**
	 * Register and add callback function
	 * @link http://www.php.net/manual/en/gearmanworker.addfunction.php
	 * @param string $function_name 
	 * @param callable $function 
	 * @param mixed $context [optional] 
	 * @param int $timeout [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addFunction (string $function_name, callable $function, mixed &$context = null, int $timeout = null): bool {}

	/**
	 * Wait for and perform jobs
	 * @link http://www.php.net/manual/en/gearmanworker.work.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function work (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function ping (string $data): bool {}

}

/**
 * @link http://www.php.net/manual/en/class.gearmanjob.php
 */
class GearmanJob  {

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Get last return code
	 * @link http://www.php.net/manual/en/gearmanjob.returncode.php
	 * @return int A valid Gearman return code.
	 */
	public function returnCode (): int {}

	/**
	 * Set a return value
	 * @link http://www.php.net/manual/en/gearmanjob.setreturn.php
	 * @param int $gearman_return_t 
	 * @return bool Description...
	 */
	public function setReturn (int $gearman_return_t): bool {}

	/**
	 * Send data for a running job
	 * @link http://www.php.net/manual/en/gearmanjob.senddata.php
	 * @param string $data 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sendData (string $data): bool {}

	/**
	 * Send a warning
	 * @link http://www.php.net/manual/en/gearmanjob.sendwarning.php
	 * @param string $warning 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sendWarning (string $warning): bool {}

	/**
	 * Send status
	 * @link http://www.php.net/manual/en/gearmanjob.sendstatus.php
	 * @param int $numerator 
	 * @param int $denominator 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sendStatus (int $numerator, int $denominator): bool {}

	/**
	 * Send the result and complete status
	 * @link http://www.php.net/manual/en/gearmanjob.sendcomplete.php
	 * @param string $result 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sendComplete (string $result): bool {}

	/**
	 * Send exception for running job (exception)
	 * @link http://www.php.net/manual/en/gearmanjob.sendexception.php
	 * @param string $exception 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sendException (string $exception): bool {}

	/**
	 * Send fail status
	 * @link http://www.php.net/manual/en/gearmanjob.sendfail.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function sendFail (): bool {}

	/**
	 * Get the job handle
	 * @link http://www.php.net/manual/en/gearmanjob.handle.php
	 * @return string An opaque job handle.
	 */
	public function handle (): string {}

	/**
	 * Get function name
	 * @link http://www.php.net/manual/en/gearmanjob.functionname.php
	 * @return string The name of a function.
	 */
	public function functionName (): string {}

	/**
	 * Get the unique identifier
	 * @link http://www.php.net/manual/en/gearmanjob.unique.php
	 * @return string An opaque unique identifier.
	 */
	public function unique (): string {}

	/**
	 * Get workload
	 * @link http://www.php.net/manual/en/gearmanjob.workload.php
	 * @return string Serialized data.
	 */
	public function workload (): string {}

	/**
	 * Get size of work load
	 * @link http://www.php.net/manual/en/gearmanjob.workloadsize.php
	 * @return int The size in bytes.
	 */
	public function workloadSize (): int {}

}

/**
 * @link http://www.php.net/manual/en/class.gearmanexception.php
 */
final class GearmanException extends Exception implements Throwable, Stringable {

	/**
	 * The exception code
	 * @var int
	 * @link http://www.php.net/manual/en/class.gearmanexception.php#gearmanexception.props.code
	 */
	protected int $code;

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

/**
 * {@inheritdoc}
 */
function gearman_version (): string {}

/**
 * {@inheritdoc}
 */
function gearman_bugreport (): string {}

/**
 * {@inheritdoc}
 * @param int $verbose
 */
function gearman_verbose_name (int $verbose): ?string {}

/**
 * {@inheritdoc}
 */
function gearman_client_create (): GearmanClient|false {}

/**
 * {@inheritdoc}
 */
function gearman_worker_create (): GearmanWorker|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_return_code (GearmanClient $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_error (GearmanClient $obj): string|false|null {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_get_errno (GearmanClient $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_options (GearmanClient $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param int $option
 */
function gearman_client_set_options (GearmanClient $obj, int $option): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param int $option
 */
function gearman_client_add_options (GearmanClient $obj, int $option): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param int $option
 */
function gearman_client_remove_options (GearmanClient $obj, int $option): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_timeout (GearmanClient $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param int $timeout
 */
function gearman_client_set_timeout (GearmanClient $obj, int $timeout): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $host [optional]
 * @param int $port [optional]
 * @param bool $setupExceptionHandler [optional]
 */
function gearman_client_add_server (GearmanClient $obj, string $host = NULL, int $port = 0, bool $setupExceptionHandler = true): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $servers [optional]
 * @param bool $setupExceptionHandler [optional]
 */
function gearman_client_add_servers (GearmanClient $obj, string $servers = NULL, bool $setupExceptionHandler = true): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_wait (GearmanClient $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function
 * @param string $workload
 * @param string|null $unique [optional]
 */
function gearman_client_do_normal (GearmanClient $obj, string $function, string $workload, ?string $unique = NULL): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function
 * @param string $workload
 * @param string|null $unique [optional]
 */
function gearman_client_do_high (GearmanClient $obj, string $function, string $workload, ?string $unique = NULL): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function
 * @param string $workload
 * @param string|null $unique [optional]
 */
function gearman_client_do_low (GearmanClient $obj, string $function, string $workload, ?string $unique = NULL): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function
 * @param string $workload
 * @param string|null $unique [optional]
 */
function gearman_client_do_background (GearmanClient $obj, string $function, string $workload, ?string $unique = NULL): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function
 * @param string $workload
 * @param string|null $unique [optional]
 */
function gearman_client_do_high_background (GearmanClient $obj, string $function, string $workload, ?string $unique = NULL): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function
 * @param string $workload
 * @param string|null $unique [optional]
 */
function gearman_client_do_low_background (GearmanClient $obj, string $function, string $workload, ?string $unique = NULL): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_do_job_handle (GearmanClient $obj): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_do_status (GearmanClient $obj): array {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $job_handle
 */
function gearman_client_job_status (GearmanClient $obj, string $job_handle): array {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $unique_key
 */
function gearman_client_job_status_by_unique_key (GearmanClient $obj, string $unique_key): array {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $workload
 */
function gearman_client_ping (GearmanClient $obj, string $workload): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function_name
 * @param string|int|float $workload
 * @param mixed $context [optional]
 * @param string|null $unique_key [optional]
 */
function gearman_client_add_task (GearmanClient $obj, string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function_name
 * @param string|int|float $workload
 * @param mixed $context [optional]
 * @param string|null $unique_key [optional]
 */
function gearman_client_add_task_high (GearmanClient $obj, string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function_name
 * @param string|int|float $workload
 * @param mixed $context [optional]
 * @param string|null $unique_key [optional]
 */
function gearman_client_add_task_low (GearmanClient $obj, string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function_name
 * @param string|int|float $workload
 * @param mixed $context [optional]
 * @param string|null $unique_key [optional]
 */
function gearman_client_add_task_background (GearmanClient $obj, string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function_name
 * @param string|int|float $workload
 * @param mixed $context [optional]
 * @param string|null $unique_key [optional]
 */
function gearman_client_add_task_high_background (GearmanClient $obj, string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $function_name
 * @param string|int|float $workload
 * @param mixed $context [optional]
 * @param string|null $unique_key [optional]
 */
function gearman_client_add_task_low_background (GearmanClient $obj, string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_run_tasks (GearmanClient $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $job_handle
 * @param mixed $context [optional]
 */
function gearman_client_add_task_status (GearmanClient $obj, string $job_handle, mixed $context = NULL): GearmanTask {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_workload_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_created_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_data_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_warning_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_status_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_complete_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_exception_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param callable $function
 */
function gearman_client_set_fail_callback (GearmanClient $obj, callable $function): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_clear_callbacks (GearmanClient $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_context (GearmanClient $obj): string {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 * @param string $data
 */
function gearman_client_set_context (GearmanClient $obj, string $data): bool {}

/**
 * {@inheritdoc}
 * @param GearmanClient $obj
 */
function gearman_client_enable_exception_handler (GearmanClient $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_return_code (GearmanJob $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 * @param int $gearman_return_t
 */
function gearman_job_set_return (GearmanJob $obj, int $gearman_return_t): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 * @param string $data
 */
function gearman_job_send_data (GearmanJob $obj, string $data): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 * @param string $warning
 */
function gearman_job_send_warning (GearmanJob $obj, string $warning): bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 * @param int $numerator
 * @param int $denominator
 */
function gearman_job_send_status (GearmanJob $obj, int $numerator, int $denominator): bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 * @param string $result
 */
function gearman_job_send_complete (GearmanJob $obj, string $result): bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 * @param string $exception
 */
function gearman_job_send_exception (GearmanJob $obj, string $exception): bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_send_fail (GearmanJob $obj): bool {}

/**
 * Get the job handle
 * @link http://www.php.net/manual/en/gearmantask.jobhandle.php
 * @param GearmanJob $obj
 * @return string The opaque job handle.
 */
function gearman_job_handle (GearmanJob $obj): string {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_function_name (GearmanJob $obj): string|bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_unique (GearmanJob $obj): string|bool {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_workload (GearmanJob $obj): string {}

/**
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_workload_size (GearmanJob $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_return_code (GearmanTask $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_function_name (GearmanTask $obj): string|bool|null {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_unique (GearmanTask $obj): string|bool|null {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_job_handle (GearmanTask $obj): string|bool|null {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_is_known (GearmanTask $obj): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_is_running (GearmanTask $obj): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_numerator (GearmanTask $obj): int|bool|null {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_denominator (GearmanTask $obj): int|bool|null {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_data (GearmanTask $obj): string|bool|null {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 */
function gearman_task_data_size (GearmanTask $obj): int|false {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 * @param string $data
 */
function gearman_task_send_workload (GearmanTask $obj, string $data): int|false {}

/**
 * {@inheritdoc}
 * @param GearmanTask $obj
 * @param int $data_len
 */
function gearman_task_recv_data (GearmanTask $obj, int $data_len): array|bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_return_code (GearmanWorker $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_error (GearmanWorker $obj): string|false {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_errno (GearmanWorker $obj): int|false {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_options (GearmanWorker $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param int $option
 */
function gearman_worker_set_options (GearmanWorker $obj, int $option): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param int $option
 */
function gearman_worker_add_options (GearmanWorker $obj, int $option): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param int $option
 */
function gearman_worker_remove_options (GearmanWorker $obj, int $option): ?bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_timeout (GearmanWorker $obj): ?int {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param int $timeout
 */
function gearman_worker_set_timeout (GearmanWorker $obj, int $timeout): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $id
 */
function gearman_worker_set_id (GearmanWorker $obj, string $id): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $host [optional]
 * @param int $port [optional]
 */
function gearman_worker_add_server (GearmanWorker $obj, string $host = NULL, int $port = 0): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $servers [optional]
 */
function gearman_worker_add_servers (GearmanWorker $obj, string $servers = NULL): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_wait (GearmanWorker $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $function_name
 * @param int $timeout [optional]
 */
function gearman_worker_register (GearmanWorker $obj, string $function_name, int $timeout = 0): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $function_name
 */
function gearman_worker_unregister (GearmanWorker $obj, string $function_name): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_unregister_all (GearmanWorker $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_grab_job (GearmanWorker $obj): GearmanWorker|false {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $function_name
 * @param callable $function
 * @param mixed $context [optional]
 * @param int $timeout [optional]
 */
function gearman_worker_add_function (GearmanWorker $obj, string $function_name, callable $function, mixed $context = NULL, int $timeout = 0): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 */
function gearman_worker_work (GearmanWorker $obj): bool {}

/**
 * {@inheritdoc}
 * @param GearmanWorker $obj
 * @param string $data
 */
function gearman_worker_ping (GearmanWorker $obj, string $data): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var string
 */
define ('GEARMAN_DEFAULT_TCP_HOST', "localhost");

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_DEFAULT_TCP_PORT', 4730);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_DEFAULT_SOCKET_TIMEOUT', 10);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_DEFAULT_SOCKET_SEND_SIZE', 32768);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_DEFAULT_SOCKET_RECV_SIZE', 32768);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_MAX_ERROR_SIZE', 2048);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_PACKET_HEADER_SIZE', 12);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_JOB_HANDLE_SIZE', 64);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_OPTION_SIZE', 64);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_UNIQUE_SIZE', 64);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_MAX_COMMAND_ARGS', 8);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_ARGS_BUFFER_SIZE', 128);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_SEND_BUFFER_SIZE', 8192);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_RECV_BUFFER_SIZE', 8192);

/**
 * 
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORKER_WAIT_TIMEOUT', 10000);

/**
 * Whatever action was taken was successful.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_SUCCESS', 0);

/**
 * When in non-blocking mode, an event is hit that would have blocked.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_IO_WAIT', 1);
define ('GEARMAN_SHUTDOWN', 2);
define ('GEARMAN_SHUTDOWN_GRACEFUL', 3);

/**
 * A system error. Check GearmanClient::errno or
 * GearmanWorker::errno for the system error code that
 * was returned.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_ERRNO', 4);
define ('GEARMAN_EVENT', 5);
define ('GEARMAN_TOO_MANY_ARGS', 6);

/**
 * GearmanClient::wait or GearmanWorker was
 * called with no connections.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_NO_ACTIVE_FDS', 7);
define ('GEARMAN_INVALID_MAGIC', 8);
define ('GEARMAN_INVALID_COMMAND', 9);
define ('GEARMAN_INVALID_PACKET', 10);

/**
 * Indicates something going very wrong in gearmand. Applies only to
 * GearmanWorker.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_UNEXPECTED_PACKET', 11);

/**
 * DNS resolution failed (invalid host, port, etc).
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_GETADDRINFO', 12);

/**
 * Did not call GearmanClient::addServer before submitting jobs
 * or tasks.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_NO_SERVERS', 13);

/**
 * Lost a connection during a request.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_LOST_CONNECTION', 14);

/**
 * Memory allocation failed (ran out of memory).
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_MEMORY_ALLOCATION_FAILURE', 15);
define ('GEARMAN_JOB_EXISTS', 16);
define ('GEARMAN_JOB_QUEUE_FULL', 17);

/**
 * Something went wrong in the Gearman server and it could not handle the
 * request gracefully.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_SERVER_ERROR', 18);
define ('GEARMAN_WORK_ERROR', 19);

/**
 * Notice return code obtained with GearmanClient::returnCode
 * when using GearmanClient::do. Sent to update the client
 * with data from a running job. A worker uses this when it needs to send updates,
 * send partial results, or flush data during long running jobs.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORK_DATA', 20);

/**
 * Notice return code obtained with GearmanClient::returnCode
 * when using GearmanClient::do. Updates the client with
 * a warning. The behavior is just like GEARMAN_WORK_DATA, but
 * should be treated as a warning instead of normal response data.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORK_WARNING', 21);

/**
 * Notice return code obtained with GearmanClient::returnCode
 * when using GearmanClient::do. Sent to update the status
 * of a long running job. Use GearmanClient::doStatus to obtain
 * the percentage complete of the task.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORK_STATUS', 22);

/**
 * Notice return code obtained with GearmanClient::returnCode
 * when using GearmanClient::do. Indicates that a job failed
 * with a given exception.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORK_EXCEPTION', 23);

/**
 * Notice return code obtained with GearmanClient::returnCode
 * when using GearmanClient::do. Indicates that the job failed.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORK_FAIL', 24);
define ('GEARMAN_NOT_CONNECTED', 25);

/**
 * Failed to connect to servers.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_COULD_NOT_CONNECT', 26);
define ('GEARMAN_SEND_IN_PROGRESS', 27);
define ('GEARMAN_RECV_IN_PROGRESS', 28);
define ('GEARMAN_NOT_FLUSHING', 29);
define ('GEARMAN_DATA_TOO_LARGE', 30);

/**
 * Trying to register a function name of NULL or using the callback interface
 * without specifying callbacks.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_INVALID_FUNCTION_NAME', 31);

/**
 * Trying to register a function with a NULL callback function.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_INVALID_WORKER_FUNCTION', 32);

/**
 * When a worker gets a job for a function it did not register.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_NO_REGISTERED_FUNCTIONS', 34);

/**
 * For a non-blocking worker, when GearmanWorker::work does not have
 * any active jobs.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_NO_JOBS', 35);

/**
 * After GearmanClient::echo or GearmanWorker::echo
 * the data returned doesn't match the data sent.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_ECHO_DATA_CORRUPTION', 36);

/**
 * When the client opted to stream the workload of a task, but did not
 * specify a workload callback function.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_NEED_WORKLOAD_FN', 37);

/**
 * For the non-blocking client task interface, can be returned from the task callback
 * to "pause" the call and return from GearmanClient::runTasks.
 * Call GearmanClient::runTasks again to continue.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_PAUSE', 38);

/**
 * Internal client/worker state error.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_UNKNOWN_STATE', 39);
define ('GEARMAN_PTHREAD', 40);
define ('GEARMAN_PIPE_EOF', 41);
define ('GEARMAN_QUEUE_ERROR', 42);
define ('GEARMAN_FLUSH_DATA', 43);

/**
 * Internal error: trying to flush more data in one atomic chunk than is possible
 * due to hard-coded buffer sizes.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_SEND_BUFFER_TOO_SMALL', 44);
define ('GEARMAN_IGNORE_PACKET', 45);
define ('GEARMAN_UNKNOWN_OPTION', 46);

/**
 * Hit the timeout limit set by the client/worker.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_TIMEOUT', 47);
define ('GEARMAN_MAX_RETURN', 53);
define ('GEARMAN_VERBOSE_FATAL', 1);
define ('GEARMAN_VERBOSE_ERROR', 2);
define ('GEARMAN_VERBOSE_INFO', 3);
define ('GEARMAN_VERBOSE_DEBUG', 4);
define ('GEARMAN_VERBOSE_CRAZY', 5);
define ('GEARMAN_VERBOSE_MAX', 6);
define ('GEARMAN_NON_BLOCKING', 0);
define ('GEARMAN_DONT_TRACK_PACKETS', 1);
define ('GEARMAN_CON_READY', 0);
define ('GEARMAN_CON_PACKET_IN_USE', 1);
define ('GEARMAN_CON_EXTERNAL_FD', 2);
define ('GEARMAN_CON_IGNORE_LOST_CONNECTION', 3);
define ('GEARMAN_CON_CLOSE_AFTER_FLUSH', 4);
define ('GEARMAN_CON_SEND_STATE_NONE', 0);
define ('GEARMAN_CON_RECV_STATE_READ_DATA', 2);
define ('GEARMAN_COMMAND_TEXT', 0);
define ('GEARMAN_COMMAND_CAN_DO', 1);
define ('GEARMAN_COMMAND_CANT_DO', 2);
define ('GEARMAN_COMMAND_RESET_ABILITIES', 3);
define ('GEARMAN_COMMAND_PRE_SLEEP', 4);
define ('GEARMAN_COMMAND_UNUSED', 5);
define ('GEARMAN_COMMAND_NOOP', 6);
define ('GEARMAN_COMMAND_SUBMIT_JOB', 7);
define ('GEARMAN_COMMAND_JOB_CREATED', 8);
define ('GEARMAN_COMMAND_GRAB_JOB', 9);
define ('GEARMAN_COMMAND_NO_JOB', 10);
define ('GEARMAN_COMMAND_JOB_ASSIGN', 11);
define ('GEARMAN_COMMAND_WORK_STATUS', 12);
define ('GEARMAN_COMMAND_WORK_COMPLETE', 13);
define ('GEARMAN_COMMAND_WORK_FAIL', 14);
define ('GEARMAN_COMMAND_GET_STATUS', 15);
define ('GEARMAN_COMMAND_ECHO_REQ', 16);
define ('GEARMAN_COMMAND_ECHO_RES', 17);
define ('GEARMAN_COMMAND_SUBMIT_JOB_BG', 18);
define ('GEARMAN_COMMAND_ERROR', 19);
define ('GEARMAN_COMMAND_STATUS_RES', 20);
define ('GEARMAN_COMMAND_SUBMIT_JOB_HIGH', 21);
define ('GEARMAN_COMMAND_SET_CLIENT_ID', 22);
define ('GEARMAN_COMMAND_CAN_DO_TIMEOUT', 23);
define ('GEARMAN_COMMAND_ALL_YOURS', 24);
define ('GEARMAN_COMMAND_WORK_EXCEPTION', 25);
define ('GEARMAN_COMMAND_OPTION_REQ', 26);
define ('GEARMAN_COMMAND_OPTION_RES', 27);
define ('GEARMAN_COMMAND_WORK_DATA', 28);
define ('GEARMAN_COMMAND_WORK_WARNING', 29);
define ('GEARMAN_COMMAND_GRAB_JOB_UNIQ', 30);
define ('GEARMAN_COMMAND_JOB_ASSIGN_UNIQ', 31);
define ('GEARMAN_COMMAND_SUBMIT_JOB_HIGH_BG', 32);
define ('GEARMAN_COMMAND_SUBMIT_JOB_LOW', 33);
define ('GEARMAN_COMMAND_SUBMIT_JOB_LOW_BG', 34);
define ('GEARMAN_COMMAND_SUBMIT_JOB_SCHED', 35);
define ('GEARMAN_COMMAND_SUBMIT_JOB_EPOCH', 36);
define ('GEARMAN_COMMAND_MAX', 43);
define ('GEARMAN_TASK_STATE_NEW', 0);
define ('GEARMAN_TASK_STATE_SUBMIT', 1);
define ('GEARMAN_TASK_STATE_WORKLOAD', 2);
define ('GEARMAN_TASK_STATE_WORK', 3);
define ('GEARMAN_TASK_STATE_CREATED', 4);
define ('GEARMAN_TASK_STATE_DATA', 5);
define ('GEARMAN_TASK_STATE_WARNING', 6);
define ('GEARMAN_TASK_STATE_STATUS', 7);
define ('GEARMAN_TASK_STATE_COMPLETE', 8);
define ('GEARMAN_TASK_STATE_EXCEPTION', 9);
define ('GEARMAN_TASK_STATE_FAIL', 10);
define ('GEARMAN_TASK_STATE_FINISHED', 11);
define ('GEARMAN_JOB_PRIORITY_HIGH', 0);
define ('GEARMAN_JOB_PRIORITY_NORMAL', 1);
define ('GEARMAN_JOB_PRIORITY_LOW', 2);
define ('GEARMAN_JOB_PRIORITY_MAX', 3);
define ('GEARMAN_CLIENT_ALLOCATED', 1);

/**
 * Run the cient in a non-blocking mode.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_CLIENT_NON_BLOCKING', 2);
define ('GEARMAN_CLIENT_TASK_IN_USE', 4);

/**
 * Allow the client to read data in chunks rather than have the library
 * buffer the entire data result and pass that back.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_CLIENT_UNBUFFERED_RESULT', 8);
define ('GEARMAN_CLIENT_NO_NEW', 16);

/**
 * Automatically free task objects once they are complete. This is the default
 * setting in this extension to prevent memory leaks.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_CLIENT_FREE_TASKS', 32);
define ('GEARMAN_CLIENT_STATE_IDLE', 0);
define ('GEARMAN_CLIENT_STATE_NEW', 1);
define ('GEARMAN_CLIENT_STATE_SUBMIT', 2);
define ('GEARMAN_CLIENT_STATE_PACKET', 3);
define ('GEARMAN_WORKER_ALLOCATED', 1);

/**
 * Run the worker in non-blocking mode.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORKER_NON_BLOCKING', 2);
define ('GEARMAN_WORKER_PACKET_INIT', 4);
define ('GEARMAN_WORKER_GRAB_JOB_IN_USE', 8);
define ('GEARMAN_WORKER_PRE_SLEEP_IN_USE', 16);
define ('GEARMAN_WORKER_WORK_JOB_IN_USE', 32);
define ('GEARMAN_WORKER_CHANGE', 64);

/**
 * Return the client assigned unique ID in addition to the job handle.
 * @link http://www.php.net/manual/en/gearman.constants.php
 * @var int
 */
define ('GEARMAN_WORKER_GRAB_UNIQ', 128);
define ('GEARMAN_WORKER_TIMEOUT_RETURN', 256);
define ('GEARMAN_WORKER_STATE_START', 0);
define ('GEARMAN_WORKER_STATE_FUNCTION_SEND', 1);
define ('GEARMAN_WORKER_STATE_CONNECT', 2);
define ('GEARMAN_WORKER_STATE_GRAB_JOB_SEND', 3);
define ('GEARMAN_WORKER_STATE_GRAB_JOB_RECV', 4);
define ('GEARMAN_WORKER_STATE_PRE_SLEEP', 5);

// End of gearman v.2.1.0
