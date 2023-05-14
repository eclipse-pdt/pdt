<?php

// Start of random v.8.2.6

namespace Random {

/**
 * A Random\Engine provides a low-level source of randomness by
 * returning random bytes that are consumed by high-level APIs to perform their operations.
 * The Random\Engine interface allows swapping out the algorithm
 * used to generate randomness, because each algorithm makes different tradeoffs to fit
 * specific use-cases. Some algorithms are very fast, but generate lower-quality randomness,
 * whereas other algorithms are slower, but generate better randomness, up to
 * cryptographically secure randomness as provided by the Random\Engine\Secure
 * engine.
 * <p>PHP provides several Random\Engines out of the box to accomodate
 * different use-cases. The Random\Engine\Secure engine that is
 * backed by a CSPRNG is the recommended safe default choice, unless
 * the application requires either reproducible sequences or very high performance.</p>
 * @link http://www.php.net/manual/en/class.random-engine.php
 */
interface Engine  {

	abstract public function generate (): string

}

/**
 * A marker interface indicating that the Random\Engine returns cryptographically secure randomness.
 * @link http://www.php.net/manual/en/class.random-cryptosafeengine.php
 */
interface CryptoSafeEngine extends \Random\Engine {

	abstract public function generate (): string

}

/**
 * The base class for Errors that occur during generation or use of randomness.
 * @link http://www.php.net/manual/en/class.random-randomerror.php
 */
class RandomError extends \Error implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * Indicates that the used Random\Engine is broken, e.g. because it is severely biased.
 * @link http://www.php.net/manual/en/class.random-brokenrandomengineerror.php
 */
class BrokenRandomEngineError extends \Random\RandomError implements \Stringable, \Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * The base class for Exceptions that occur during generation or use of randomness.
 * @link http://www.php.net/manual/en/class.random-randomexception.php
 */
class RandomException extends \Exception implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}


}


namespace Random\Engine {

/**
 * Implements the Mt19937 (“Mersenne Twister”) algorithm.
 * @link http://www.php.net/manual/en/class.random-engine-mt19937.php
 */
final class Mt19937 implements \Random\Engine {

	/**
	 * @param ?int|null $seed [optional]
	 * @param int $mode [optional]
	 */
	public function __construct (?int|null $seed = nullint , $mode = 0) {}

	public function generate (): string {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __debugInfo (): array {}

}

/**
 * Implements a Permuted congruential generator (PCG) with
 * 128 bits of state, XSL and RR output transformations, and 64 bits of output.
 * @link http://www.php.net/manual/en/class.random-engine-pcgoneseq128xslrr64.php
 */
final class PcgOneseq128XslRr64 implements \Random\Engine {

	/**
	 * @param string|int|null|null $seed [optional]
	 */
	public function __construct (string|int|null|null $seed = null) {}

	public function generate (): string {}

	/**
	 * @param int $advance
	 */
	public function jump (int $advance): void {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __debugInfo (): array {}

}

/**
 * Implements the xoshiro256&#42;&#42; algorithm.
 * @link http://www.php.net/manual/en/class.random-engine-xoshiro256starstar.php
 */
final class Xoshiro256StarStar implements \Random\Engine {

	/**
	 * @param string|int|null|null $seed [optional]
	 */
	public function __construct (string|int|null|null $seed = null) {}

	public function generate (): string {}

	public function jump (): void {}

	public function jumpLong (): void {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

	public function __debugInfo (): array {}

}

/**
 * Generates cryptographically secure randomness using the operating system’s CSPRNG.
 * <p>The randomness generated by this Random\Engine is suitable
 * for all applications, including the generation of long-term secrets, such as
 * encryption keys.</p>
 * <p>The Random\Engine\Secure engine is the recommended safe default choice,
 * unless the application requires either reproducible sequences or very high performance.</p>
 * @link http://www.php.net/manual/en/class.random-engine-secure.php
 */
final class Secure implements \Random\CryptoSafeEngine, \Random\Engine {

	public function generate (): string {}

}


}


namespace Random {

/**
 * Provides a high-level API to the randomness provided by an Random\Engine.
 * @link http://www.php.net/manual/en/class.random-randomizer.php
 */
final class Randomizer  {
	public readonly $engine;


	/**
	 * @param ?Random\Engine|null $engine [optional]
	 */
	public function __construct (?Random\Engine|null $engine = null) {}

	public function nextInt (): int {}

	/**
	 * @param int $min
	 * @param int $max
	 */
	public function getInt (int $minint , $max): int {}

	/**
	 * @param int $length
	 */
	public function getBytes (int $length): string {}

	/**
	 * @param array[] $array
	 */
	public function shuffleArray (array $array): array {}

	/**
	 * @param string $bytes
	 */
	public function shuffleBytes (string $bytes): string {}

	/**
	 * @param array[] $array
	 * @param int $num
	 */
	public function pickArrayKeys (array $arrayint , $num): array {}

	public function __serialize (): array {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data): void {}

}


}


namespace {

/**
 * Combined linear congruential generator
 * @link http://www.php.net/manual/en/function.lcg-value.php
 * @return float A pseudo random float value between 0.0 and 1.0, inclusive.
 */
function lcg_value (): float {}

/**
 * Seeds the Mersenne Twister Random Number Generator
 * @link http://www.php.net/manual/en/function.mt-srand.php
 * @param int $seed [optional] An arbitrary int seed value.
 * @param int $mode [optional] <p>
 * Use one of the following constants to specify the implementation of the algorithm to use.
 * <p>
 * MT_RAND_MT19937:
 * The correct Mt19937 implementation, available as of PHP 7.1.0.
 * MT_RAND_PHP
 * Uses an incorrect Mersenne Twister implementation which was used as the default up till PHP 7.1.0.
 * This mode is available for backward compatibility.
 * </p>
 * </p>
 * @return void 
 */
function mt_srand (int $seed = null, int $mode = null): void {}

/**
 * Seed the random number generator
 * @link http://www.php.net/manual/en/function.srand.php
 * @param int $seed [optional] An arbitrary int seed value.
 * @param int $mode [optional] 
 * @return void 
 */
function srand (int $seed = null, int $mode = null): void {}

/**
 * Generate a random integer
 * @link http://www.php.net/manual/en/function.rand.php
 * @param int $min [optional]
 * @param int $max [optional]
 * @return int A pseudo random value between min
 * (or 0) and max (or getrandmax, inclusive).
 */
function rand (int $min = nullint , $max = null): int {}

/**
 * Generate a random value via the Mersenne Twister Random Number Generator
 * @link http://www.php.net/manual/en/function.mt-rand.php
 * @param int $min [optional]
 * @param int $max [optional]
 * @return int A random integer value between min (or 0)
 * and max (or mt_getrandmax, inclusive),
 * or false if max is less than min.
 */
function mt_rand (int $min = nullint , $max = null): int {}

/**
 * Show largest possible random value
 * @link http://www.php.net/manual/en/function.mt-getrandmax.php
 * @return int the maximum random value returned by a call to
 * mt_rand without arguments, which is the maximum value
 * that can be used for its max parameter without the
 * result being scaled up (and therefore less random).
 */
function mt_getrandmax (): int {}

/**
 * Show largest possible random value
 * @link http://www.php.net/manual/en/function.getrandmax.php
 * @return int The largest possible random value returned by rand
 */
function getrandmax (): int {}

/**
 * Get cryptographically secure random bytes
 * @link http://www.php.net/manual/en/function.random-bytes.php
 * @param int $length The length of the random string that should be returned in bytes; must be 1 or greater.
 * @return string A string containing the requested number of cryptographically
 * secure random bytes.
 */
function random_bytes (int $length): string {}

/**
 * Get a cryptographically secure, uniformly selected integer
 * @link http://www.php.net/manual/en/function.random-int.php
 * @param int $min The lowest value to be returned.
 * @param int $max The highest value to be returned.
 * @return int A cryptographically secure, uniformly selected integer from the closed interval
 * [min, max]. Both
 * min and max are
 * possible return values.
 */
function random_int (int $min, int $max): int {}


/**
 * Indicates that the correct Mt19937 (Mersenne Twister)
 * implementation will be used by the algorithm, when creating a Random\Engine\Mt19937 instance
 * using Random\Engine\Mt19937::__construct or seeding the global Mersenne Twister
 * with mt_srand.
 * @link http://www.php.net/manual/en/random.constants.php
 */
define ('MT_RAND_MT19937', 0);

/**
 * Indicates that an incorrect Mersenne Twister implementation will be used by the algorithm, when
 * creating a Random\Engine\Mt19937 instance using Random\Engine\Mt19937::__construct
 * or seeding the global Mersenne Twister with mt_srand.
 * The incorrect implementation is available for backwards compatibility with
 * mt_srand prior to PHP 7.1.0.
 * @link http://www.php.net/manual/en/random.constants.php
 */
define ('MT_RAND_PHP', 1);


}

// End of random v.8.2.6
