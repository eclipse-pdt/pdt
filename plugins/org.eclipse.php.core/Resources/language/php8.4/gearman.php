<?php

// Start of gearman v.2.1.0

class GearmanClient  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function error (): string|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrno (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function options (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function setOptions (int $option): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function addOptions (int $option): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function removeOptions (int $option): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function timeout (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $timeout
	 */
	public function setTimeout (int $timeout): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host [optional]
	 * @param int $port [optional]
	 * @param bool $setupExceptionHandler [optional]
	 */
	public function addServer (string $host = NULL, int $port = 0, bool $setupExceptionHandler = true): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $servers [optional]
	 * @param bool $setupExceptionHandler [optional]
	 */
	public function addServers (string $servers = NULL, bool $setupExceptionHandler = true): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function wait (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $function
	 * @param string $workload
	 * @param string|null $unique [optional]
	 */
	public function doNormal (string $function, string $workload, ?string $unique = NULL): string {}

	/**
	 * {@inheritdoc}
	 * @param string $function
	 * @param string $workload
	 * @param string|null $unique [optional]
	 */
	public function doHigh (string $function, string $workload, ?string $unique = NULL): string {}

	/**
	 * {@inheritdoc}
	 * @param string $function
	 * @param string $workload
	 * @param string|null $unique [optional]
	 */
	public function dolow (string $function, string $workload, ?string $unique = NULL): string {}

	/**
	 * {@inheritdoc}
	 * @param string $function
	 * @param string $workload
	 * @param string|null $unique [optional]
	 */
	public function doBackground (string $function, string $workload, ?string $unique = NULL): string {}

	/**
	 * {@inheritdoc}
	 * @param string $function
	 * @param string $workload
	 * @param string|null $unique [optional]
	 */
	public function doHighBackground (string $function, string $workload, ?string $unique = NULL): string {}

	/**
	 * {@inheritdoc}
	 * @param string $function
	 * @param string $workload
	 * @param string|null $unique [optional]
	 */
	public function doLowBackground (string $function, string $workload, ?string $unique = NULL): string {}

	/**
	 * {@inheritdoc}
	 */
	public function doJobHandle (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function doStatus (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $job_handle
	 */
	public function jobStatus (string $job_handle): array {}

	/**
	 * {@inheritdoc}
	 * @param string $unique_key
	 */
	public function jobStatusByUniqueKey (string $unique_key): array {}

	/**
	 * {@inheritdoc}
	 * @param string $workload
	 */
	public function ping (string $workload): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param string|int|float $workload
	 * @param mixed $context [optional]
	 * @param string|null $unique_key [optional]
	 */
	public function addTask (string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param string|int|float $workload
	 * @param mixed $context [optional]
	 * @param string|null $unique_key [optional]
	 */
	public function addTaskHigh (string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param string|int|float $workload
	 * @param mixed $context [optional]
	 * @param string|null $unique_key [optional]
	 */
	public function addTaskLow (string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param string|int|float $workload
	 * @param mixed $context [optional]
	 * @param string|null $unique_key [optional]
	 */
	public function addTaskBackground (string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param string|int|float $workload
	 * @param mixed $context [optional]
	 * @param string|null $unique_key [optional]
	 */
	public function addTaskHighBackground (string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param string|int|float $workload
	 * @param mixed $context [optional]
	 * @param string|null $unique_key [optional]
	 */
	public function addTaskLowBackground (string $function_name, string|int|float $workload, mixed $context = NULL, ?string $unique_key = NULL): GearmanTask|false {}

	/**
	 * {@inheritdoc}
	 */
	public function runTasks (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $job_handle
	 * @param mixed $context [optional]
	 */
	public function addTaskStatus (string $job_handle, mixed $context = NULL): GearmanTask {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setWorkloadCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setCreatedCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setDataCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setWarningCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setStatusCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setCompleteCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setExceptionCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $function
	 */
	public function setFailCallback (callable $function): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clearCallbacks (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function context (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function setContext (string $data): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function enableExceptionHandler (): bool {}

}

class GearmanTask  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function functionName (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function unique (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jobHandle (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isKnown (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function isRunning (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function taskNumerator (): int|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function taskDenominator (): int|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function data (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function dataSize (): int|false {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function sendWorkload (string $data): int|false {}

	/**
	 * {@inheritdoc}
	 * @param int $data_len
	 */
	public function recvData (int $data_len): array|bool {}

}

class GearmanWorker  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnCode (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function error (): string|false {}

	/**
	 * {@inheritdoc}
	 */
	public function getErrno (): int|false {}

	/**
	 * {@inheritdoc}
	 */
	public function options (): ?int {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function setOptions (int $option): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function addOptions (int $option): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param int $option
	 */
	public function removeOptions (int $option): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	public function timeout (): ?int {}

	/**
	 * {@inheritdoc}
	 * @param int $timeout
	 */
	public function setTimeout (int $timeout): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $id
	 */
	public function setId (string $id): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $host [optional]
	 * @param int $port [optional]
	 */
	public function addServer (string $host = NULL, int $port = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $servers [optional]
	 */
	public function addServers (string $servers = NULL): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function wait (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param int $timeout [optional]
	 */
	public function register (string $function_name, int $timeout = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 */
	public function unregister (string $function_name): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function unregisterAll (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function grabJob (): GearmanWorker|false {}

	/**
	 * {@inheritdoc}
	 * @param string $function_name
	 * @param callable $function
	 * @param mixed $context [optional]
	 * @param int $timeout [optional]
	 */
	public function addFunction (string $function_name, callable $function, mixed $context = NULL, int $timeout = 0): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function work (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function ping (string $data): bool {}

}

class GearmanJob  {

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 */
	public function returnCode (): ?int {}

	/**
	 * {@inheritdoc}
	 * @param int $gearman_return_t
	 */
	public function setReturn (int $gearman_return_t): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function sendData (string $data): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param string $warning
	 */
	public function sendWarning (string $warning): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $numerator
	 * @param int $denominator
	 */
	public function sendStatus (int $numerator, int $denominator): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $result
	 */
	public function sendComplete (string $result): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $exception
	 */
	public function sendException (string $exception): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function sendFail (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function handle (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function functionName (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function unique (): string|bool {}

	/**
	 * {@inheritdoc}
	 */
	public function workload (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function workloadSize (): ?int {}

}

final class GearmanException extends Exception implements Throwable, Stringable {

	public $code;

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
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
 * {@inheritdoc}
 * @param GearmanJob $obj
 */
function gearman_job_handle (GearmanJob $obj): string|bool {}

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

define ('GEARMAN_DEFAULT_TCP_HOST', "localhost");
define ('GEARMAN_DEFAULT_TCP_PORT', 4730);
define ('GEARMAN_DEFAULT_SOCKET_TIMEOUT', 10);
define ('GEARMAN_DEFAULT_SOCKET_SEND_SIZE', 32768);
define ('GEARMAN_DEFAULT_SOCKET_RECV_SIZE', 32768);
define ('GEARMAN_MAX_ERROR_SIZE', 2048);
define ('GEARMAN_PACKET_HEADER_SIZE', 12);
define ('GEARMAN_JOB_HANDLE_SIZE', 64);
define ('GEARMAN_OPTION_SIZE', 64);
define ('GEARMAN_UNIQUE_SIZE', 64);
define ('GEARMAN_MAX_COMMAND_ARGS', 8);
define ('GEARMAN_ARGS_BUFFER_SIZE', 128);
define ('GEARMAN_SEND_BUFFER_SIZE', 8192);
define ('GEARMAN_RECV_BUFFER_SIZE', 8192);
define ('GEARMAN_WORKER_WAIT_TIMEOUT', 10000);
define ('GEARMAN_SUCCESS', 0);
define ('GEARMAN_IO_WAIT', 1);
define ('GEARMAN_SHUTDOWN', 2);
define ('GEARMAN_SHUTDOWN_GRACEFUL', 3);
define ('GEARMAN_ERRNO', 4);
define ('GEARMAN_EVENT', 5);
define ('GEARMAN_TOO_MANY_ARGS', 6);
define ('GEARMAN_NO_ACTIVE_FDS', 7);
define ('GEARMAN_INVALID_MAGIC', 8);
define ('GEARMAN_INVALID_COMMAND', 9);
define ('GEARMAN_INVALID_PACKET', 10);
define ('GEARMAN_UNEXPECTED_PACKET', 11);
define ('GEARMAN_GETADDRINFO', 12);
define ('GEARMAN_NO_SERVERS', 13);
define ('GEARMAN_LOST_CONNECTION', 14);
define ('GEARMAN_MEMORY_ALLOCATION_FAILURE', 15);
define ('GEARMAN_JOB_EXISTS', 16);
define ('GEARMAN_JOB_QUEUE_FULL', 17);
define ('GEARMAN_SERVER_ERROR', 18);
define ('GEARMAN_WORK_ERROR', 19);
define ('GEARMAN_WORK_DATA', 20);
define ('GEARMAN_WORK_WARNING', 21);
define ('GEARMAN_WORK_STATUS', 22);
define ('GEARMAN_WORK_EXCEPTION', 23);
define ('GEARMAN_WORK_FAIL', 24);
define ('GEARMAN_NOT_CONNECTED', 25);
define ('GEARMAN_COULD_NOT_CONNECT', 26);
define ('GEARMAN_SEND_IN_PROGRESS', 27);
define ('GEARMAN_RECV_IN_PROGRESS', 28);
define ('GEARMAN_NOT_FLUSHING', 29);
define ('GEARMAN_DATA_TOO_LARGE', 30);
define ('GEARMAN_INVALID_FUNCTION_NAME', 31);
define ('GEARMAN_INVALID_WORKER_FUNCTION', 32);
define ('GEARMAN_NO_REGISTERED_FUNCTIONS', 34);
define ('GEARMAN_NO_JOBS', 35);
define ('GEARMAN_ECHO_DATA_CORRUPTION', 36);
define ('GEARMAN_NEED_WORKLOAD_FN', 37);
define ('GEARMAN_PAUSE', 38);
define ('GEARMAN_UNKNOWN_STATE', 39);
define ('GEARMAN_PTHREAD', 40);
define ('GEARMAN_PIPE_EOF', 41);
define ('GEARMAN_QUEUE_ERROR', 42);
define ('GEARMAN_FLUSH_DATA', 43);
define ('GEARMAN_SEND_BUFFER_TOO_SMALL', 44);
define ('GEARMAN_IGNORE_PACKET', 45);
define ('GEARMAN_UNKNOWN_OPTION', 46);
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
define ('GEARMAN_CLIENT_NON_BLOCKING', 2);
define ('GEARMAN_CLIENT_TASK_IN_USE', 4);
define ('GEARMAN_CLIENT_UNBUFFERED_RESULT', 8);
define ('GEARMAN_CLIENT_NO_NEW', 16);
define ('GEARMAN_CLIENT_FREE_TASKS', 32);
define ('GEARMAN_CLIENT_STATE_IDLE', 0);
define ('GEARMAN_CLIENT_STATE_NEW', 1);
define ('GEARMAN_CLIENT_STATE_SUBMIT', 2);
define ('GEARMAN_CLIENT_STATE_PACKET', 3);
define ('GEARMAN_WORKER_ALLOCATED', 1);
define ('GEARMAN_WORKER_NON_BLOCKING', 2);
define ('GEARMAN_WORKER_PACKET_INIT', 4);
define ('GEARMAN_WORKER_GRAB_JOB_IN_USE', 8);
define ('GEARMAN_WORKER_PRE_SLEEP_IN_USE', 16);
define ('GEARMAN_WORKER_WORK_JOB_IN_USE', 32);
define ('GEARMAN_WORKER_CHANGE', 64);
define ('GEARMAN_WORKER_GRAB_UNIQ', 128);
define ('GEARMAN_WORKER_TIMEOUT_RETURN', 256);
define ('GEARMAN_WORKER_STATE_START', 0);
define ('GEARMAN_WORKER_STATE_FUNCTION_SEND', 1);
define ('GEARMAN_WORKER_STATE_CONNECT', 2);
define ('GEARMAN_WORKER_STATE_GRAB_JOB_SEND', 3);
define ('GEARMAN_WORKER_STATE_GRAB_JOB_RECV', 4);
define ('GEARMAN_WORKER_STATE_PRE_SLEEP', 5);

// End of gearman v.2.1.0
