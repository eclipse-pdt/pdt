<?php

// Start of couchbase v.4.1.3

namespace Couchbase\Exception {

class CouchbaseException extends \Exception implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	private $context;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TimeoutException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class UnambiguousTimeoutException extends \Couchbase\Exception\TimeoutException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class AmbiguousTimeoutException extends \Couchbase\Exception\TimeoutException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class AuthenticationFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class BucketExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class BucketNotFlushableException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class BucketNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class CasMismatchException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class CollectionExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class CollectionNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class CompilationFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ConsistencyMismatchException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DatasetExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DatasetNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DataverseExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DataverseNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DecodingFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DeltaInvalidException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DesignDocumentNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DocumentExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DocumentIrretrievableException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DocumentLockedException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DocumentNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DocumentNotJsonException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DurabilityAmbiguousException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DurabilityImpossibleException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DurabilityLevelNotAvailableException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DurableWriteInProgressException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class DurableWriteReCommitInProgressException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class EncodingFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class FeatureNotAvailableException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class GroupNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class IndexExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class IndexFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class IndexNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class IndexNotReadyException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class InternalServerFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class InvalidArgumentException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class JobQueueFullException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class LinkExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class LinkNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class NumberTooBigException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ParsingFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PathExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PathInvalidException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PathMismatchException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PathNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PathTooBigException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PathTooDeepException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PlanningFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class PreparedStatementFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class RequestCanceledException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ScopeExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ScopeNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ServiceNotAvailableException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TemporaryFailureException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class UnsupportedOperationException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class UserExistsException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class UserNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ValueInvalidException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ValueTooDeepException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ValueTooLargeException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class ViewNotFoundException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class XattrCannotModifyVirtualAttributeException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class XattrInvalidKeyComboException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class XattrUnknownMacroException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class XattrUnknownVirtualAttributeException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TransactionException extends \Couchbase\Exception\CouchbaseException implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TransactionOperationFailedException extends \Couchbase\Exception\TransactionException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TransactionFailedException extends \Couchbase\Exception\TransactionException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TransactionExpiredException extends \Couchbase\Exception\TransactionException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class TransactionCommitAmbiguousException extends \Couchbase\Exception\TransactionException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	public function getContext (): array {}

	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace couchbase\extension {

function version () {}

/**
 * @param mixed $connection
 * @param string $bucketName
 */
function clusterVersion ($connection = null, string $bucketName) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 */
function replicasConfiguredForBucket ($connection = null, string $bucketName) {}

/**
 * @param string $connectionHash
 * @param string $connectionString
 * @param array[] $options
 */
function createConnection (string $connectionHash, string $connectionString, array $options) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 */
function openBucket ($connection = null, string $bucketName) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 */
function closeBucket ($connection = null, string $bucketName) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param string $value
 * @param int $flags
 * @param array|null[] $options [optional]
 */
function documentUpsert ($connection = null, string $bucket, string $scope, string $collection, string $id, string $value, int $flags, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param string $value
 * @param int $flags
 * @param array|null[] $options [optional]
 */
function documentInsert ($connection = null, string $bucket, string $scope, string $collection, string $id, string $value, int $flags, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param string $value
 * @param int $flags
 * @param array|null[] $options [optional]
 */
function documentReplace ($connection = null, string $bucket, string $scope, string $collection, string $id, string $value, int $flags, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param string $value
 * @param array|null[] $options [optional]
 */
function documentAppend ($connection = null, string $bucket, string $scope, string $collection, string $id, string $value, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param string $value
 * @param array|null[] $options [optional]
 */
function documentPrepend ($connection = null, string $bucket, string $scope, string $collection, string $id, string $value, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentIncrement ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentDecrement ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentGet ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentGetAnyReplica ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentGetAllReplicas ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param int $expirySeconds
 * @param array|null[] $options [optional]
 */
function documentGetAndTouch ($connection = null, string $bucket, string $scope, string $collection, string $id, int $expirySeconds, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param int $lockTimeSeconds
 * @param array|null[] $options [optional]
 */
function documentGetAndLock ($connection = null, string $bucket, string $scope, string $collection, string $id, int $lockTimeSeconds, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param string $cas
 * @param array|null[] $options [optional]
 */
function documentUnlock ($connection = null, string $bucket, string $scope, string $collection, string $id, string $cas, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentRemove ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param int $expirySeconds
 * @param array|null[] $options [optional]
 */
function documentTouch ($connection = null, string $bucket, string $scope, string $collection, string $id, int $expirySeconds, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array|null[] $options [optional]
 */
function documentExists ($connection = null, string $bucket, string $scope, string $collection, string $id, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array[] $specs
 * @param array|null[] $options [optional]
 */
function documentMutateIn ($connection = null, string $bucket, string $scope, string $collection, string $id, array $specs, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param string $id
 * @param array[] $specs
 * @param array|null[] $options [optional]
 */
function documentLookupIn ($connection = null, string $bucket, string $scope, string $collection, string $id, array $specs, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param array[] $ids
 * @param array|null[] $options [optional]
 */
function documentGetMulti ($connection = null, string $bucket, string $scope, string $collection, array $ids, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param array[] $entries
 * @param array|null[] $options [optional]
 */
function documentRemoveMulti ($connection = null, string $bucket, string $scope, string $collection, array $entries, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucket
 * @param string $scope
 * @param string $collection
 * @param array[] $entries
 * @param array|null[] $options [optional]
 */
function documentUpsertMulti ($connection = null, string $bucket, string $scope, string $collection, array $entries, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $statement
 * @param array|null[] $options [optional]
 */
function query ($connection = null, string $statement, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $statement
 * @param array|null[] $options [optional]
 */
function analyticsQuery ($connection = null, string $statement, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $designDocumentName
 * @param string $viewName
 * @param int $nameSpace
 * @param array|null[] $options [optional]
 */
function viewQuery ($connection = null, string $bucketName, string $designDocumentName, string $viewName, int $nameSpace, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $indexName
 * @param string $query
 * @param array|null[] $options [optional]
 */
function searchQuery ($connection = null, string $indexName, string $query, array $options = null) {}

/**
 * @param mixed $connection
 * @param array|null[] $options [optional]
 */
function ping ($connection = null, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $reportId
 * @param array|null[] $options [optional]
 */
function diagnostics ($connection = null, string $reportId, array $options = null) {}

/**
 * @param mixed $connection [optional]
 * @param array|null[] $configuration [optional]
 */
function createTransactions ($connection = null, array $configuration = null): null {}

/**
 * @param mixed $transactions [optional]
 * @param array|null[] $configuration [optional]
 */
function createTransactionContext ($transactions = null, array $configuration = null): null {}

/**
 * @param mixed $transactions
 */
function transactionNewAttempt ($transactions = null) {}

/**
 * @param mixed $transactions
 */
function transactionCommit ($transactions = null) {}

/**
 * @param mixed $transactions
 */
function transactionRollback ($transactions = null) {}

/**
 * @param mixed $transactions
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param string $id
 */
function transactionGet ($transactions = null, string $bucketName, string $scopeName, string $collectionName, string $id) {}

/**
 * @param mixed $transactions
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param string $id
 * @param string $value
 */
function transactionInsert ($transactions = null, string $bucketName, string $scopeName, string $collectionName, string $id, string $value) {}

/**
 * @param mixed $transactions
 * @param array[] $document
 * @param string $value
 */
function transactionReplace ($transactions = null, array $document, string $value) {}

/**
 * @param mixed $transactions
 * @param array[] $document
 */
function transactionRemove ($transactions = null, array $document) {}

/**
 * @param mixed $transactions
 * @param string $statement
 * @param array|null[] $options [optional]
 */
function transactionQuery ($transactions = null, string $statement, array $options = null) {}

/**
 * @param mixed $connection
 * @param array[] $index
 * @param array|null[] $options [optional]
 */
function searchIndexUpsert ($connection = null, array $index, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array[] $index
 * @param int $nameSpace
 * @param array|null[] $options [optional]
 */
function viewIndexUpsert ($connection = null, string $bucketName, array $index, int $nameSpace, array $options = null) {}

/**
 * @param mixed $connection
 * @param array[] $bucketSettings
 * @param array|null[] $options [optional]
 */
function bucketCreate ($connection = null, array $bucketSettings, array $options = null) {}

/**
 * @param mixed $connection
 * @param array[] $bucketSettings
 * @param array|null[] $options [optional]
 */
function bucketUpdate ($connection = null, array $bucketSettings, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function bucketGet ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param array|null[] $options [optional]
 */
function bucketGetAll ($connection = null, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function bucketDrop ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function bucketFlush ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function scopeGetAll ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param array|null[] $options [optional]
 */
function scopeCreate ($connection = null, string $bucketName, string $scopeName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param array|null[] $options [optional]
 */
function scopeDrop ($connection = null, string $bucketName, string $scopeName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array[] $collectionSpec
 * @param array|null[] $options [optional]
 */
function collectionCreate ($connection = null, string $bucketName, array $collectionSpec, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array[] $collectionSpec
 * @param array|null[] $options [optional]
 */
function collectionDrop ($connection = null, string $bucketName, array $collectionSpec, array $options = null) {}

/**
 * @param mixed $connection
 * @param array[] $user
 * @param array|null[] $options [optional]
 */
function userUpsert ($connection = null, array $user, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function userGet ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param array|null[] $options [optional]
 */
function userGetAll ($connection = null, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function userDrop ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $new_password
 * @param array|null[] $options [optional]
 */
function passwordChange ($connection = null, string $new_password, array $options = null) {}

/**
 * @param mixed $connection
 * @param array[] $group
 * @param array|null[] $options [optional]
 */
function groupUpsert ($connection = null, array $group, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function groupGet ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param array|null[] $options [optional]
 */
function groupGetAll ($connection = null, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $name
 * @param array|null[] $options [optional]
 */
function groupDrop ($connection = null, string $name, array $options = null) {}

/**
 * @param mixed $connection
 * @param array|null[] $options [optional]
 */
function roleGetAll ($connection = null, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array|null[] $options [optional]
 */
function queryIndexGetAll ($connection = null, string $bucketName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $indexName
 * @param array[] $fields
 * @param array|null[] $options [optional]
 */
function queryIndexCreate ($connection = null, string $bucketName, string $indexName, array $fields, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array|null[] $options [optional]
 */
function queryIndexCreatePrimary ($connection = null, string $bucketName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $indexName
 * @param array|null[] $options [optional]
 */
function queryIndexDrop ($connection = null, string $bucketName, string $indexName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array|null[] $options [optional]
 */
function queryIndexDropPrimary ($connection = null, string $bucketName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param array|null[] $options [optional]
 */
function queryIndexBuildDeferred ($connection = null, string $bucketName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param array|null[] $options [optional]
 */
function collectionQueryIndexGetAll ($connection = null, string $bucketName, string $scopeName, string $collectionName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param string $indexName
 * @param array[] $fields
 * @param array|null[] $options [optional]
 */
function collectionQueryIndexCreate ($connection = null, string $bucketName, string $scopeName, string $collectionName, string $indexName, array $fields, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param array|null[] $options [optional]
 */
function collectionQueryIndexCreatePrimary ($connection = null, string $bucketName, string $scopeName, string $collectionName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param string $indexName
 * @param array|null[] $options [optional]
 */
function collectionQueryIndexDrop ($connection = null, string $bucketName, string $scopeName, string $collectionName, string $indexName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param array|null[] $options [optional]
 */
function collectionQueryIndexDropPrimary ($connection = null, string $bucketName, string $scopeName, string $collectionName, array $options = null) {}

/**
 * @param mixed $connection
 * @param string $bucketName
 * @param string $scopeName
 * @param string $collectionName
 * @param array|null[] $options [optional]
 */
function collectionQueryIndexBuildDeferred ($connection = null, string $bucketName, string $scopeName, string $collectionName, array $options = null) {}


}

// End of couchbase v.4.1.3
