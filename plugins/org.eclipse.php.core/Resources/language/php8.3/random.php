<?php

// Start of random v.8.3.0

namespace Random {

interface Engine  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function generate (): string;

}

interface CryptoSafeEngine extends \Random\Engine {

	/**
	 * {@inheritdoc}
	 */
	abstract public function generate (): string;

}

class RandomError extends \Error implements \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

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
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class BrokenRandomEngineError extends \Random\RandomError implements \Stringable, \Throwable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

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
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class RandomException extends \Exception implements \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

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
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}


}


namespace Random\Engine {

final class Mt19937 implements \Random\Engine {

	/**
	 * {@inheritdoc}
	 * @param int|null $seed [optional]
	 * @param int $mode [optional]
	 */
	public function __construct (?int $seed = NULL, int $mode = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function generate (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo (): array {}

}

final class PcgOneseq128XslRr64 implements \Random\Engine {

	/**
	 * {@inheritdoc}
	 * @param string|int|null $seed [optional]
	 */
	public function __construct (string|int|null $seed = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function generate (): string {}

	/**
	 * {@inheritdoc}
	 * @param int $advance
	 */
	public function jump (int $advance): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo (): array {}

}

final class Xoshiro256StarStar implements \Random\Engine {

	/**
	 * {@inheritdoc}
	 * @param string|int|null $seed [optional]
	 */
	public function __construct (string|int|null $seed = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function generate (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function jump (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function jumpLong (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo (): array {}

}

final class Secure implements \Random\CryptoSafeEngine, \Random\Engine {

	/**
	 * {@inheritdoc}
	 */
	public function generate (): string {}

}


}


namespace Random {

final class Randomizer  {

	public readonly \Random\Engine $engine;

	/**
	 * {@inheritdoc}
	 * @param \Random\Engine|null $engine [optional]
	 */
	public function __construct (?\Random\Engine $engine = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function nextInt (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function nextFloat (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $min
	 * @param float $max
	 * @param \Random\IntervalBoundary $boundary [optional]
	 */
	public function getFloat (float $min, float $max, \Random\IntervalBoundary $boundary = \Random\IntervalBoundary::ClosedOpen): float {}

	/**
	 * {@inheritdoc}
	 * @param int $min
	 * @param int $max
	 */
	public function getInt (int $min, int $max): int {}

	/**
	 * {@inheritdoc}
	 * @param int $length
	 */
	public function getBytes (int $length): string {}

	/**
	 * {@inheritdoc}
	 * @param string $string
	 * @param int $length
	 */
	public function getBytesFromString (string $string, int $length): string {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 */
	public function shuffleArray (array $array): array {}

	/**
	 * {@inheritdoc}
	 * @param string $bytes
	 */
	public function shuffleBytes (string $bytes): string {}

	/**
	 * {@inheritdoc}
	 * @param array $array
	 * @param int $num
	 */
	public function pickArrayKeys (array $array, int $num): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

}

enum IntervalBoundary implements \UnitEnum {
	const ClosedOpen = ;
	const ClosedClosed = ;
	const OpenClosed = ;
	const OpenOpen = ;


	public readonly string $name;

	/**
	 * {@inheritdoc}
	 */
	public static function cases (): array {}

}


}


namespace {

/**
 * {@inheritdoc}
 */
function lcg_value (): float {}

/**
 * {@inheritdoc}
 * @param int|null $seed [optional]
 * @param int $mode [optional]
 */
function mt_srand (?int $seed = NULL, int $mode = 0): void {}

/**
 * {@inheritdoc}
 * @param int|null $seed [optional]
 * @param int $mode [optional]
 */
function srand (?int $seed = NULL, int $mode = 0): void {}

/**
 * {@inheritdoc}
 * @param int $min [optional]
 * @param int $max [optional]
 */
function rand (int $min = NULL, int $max = NULL): int {}

/**
 * {@inheritdoc}
 * @param int $min [optional]
 * @param int $max [optional]
 */
function mt_rand (int $min = NULL, int $max = NULL): int {}

/**
 * {@inheritdoc}
 */
function mt_getrandmax (): int {}

/**
 * {@inheritdoc}
 */
function getrandmax (): int {}

/**
 * {@inheritdoc}
 * @param int $length
 */
function random_bytes (int $length): string {}

/**
 * {@inheritdoc}
 * @param int $min
 * @param int $max
 */
function random_int (int $min, int $max): int {}

define ('MT_RAND_MT19937', 0);
define ('MT_RAND_PHP', 1);


}

// End of random v.8.3.0
