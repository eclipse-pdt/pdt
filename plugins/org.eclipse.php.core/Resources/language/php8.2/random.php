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

	/**
	 * Generates randomness
	 * @link http://www.php.net/manual/en/random-engine.generate.php
	 * @return string A non-empty string containing random bytes.
	 */
	abstract public function generate (): string;

}

/**
 * A marker interface indicating that the Random\Engine returns cryptographically secure randomness.
 * @link http://www.php.net/manual/en/class.random-cryptosafeengine.php
 */
interface CryptoSafeEngine extends \Random\Engine {

	/**
	 * Generates randomness
	 * @link http://www.php.net/manual/en/random-engine.generate.php
	 * @return string A non-empty string containing random bytes.
	 */
	abstract public function generate (): string;

}

/**
 * The base class for Errors that occur during generation or use of randomness.
 * @link http://www.php.net/manual/en/class.random-randomerror.php
 */
class RandomError extends \Error implements \Throwable, \Stringable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * Indicates that the used Random\Engine is broken, e.g. because it is severely biased.
 * @link http://www.php.net/manual/en/class.random-brokenrandomengineerror.php
 */
class BrokenRandomEngineError extends \Random\RandomError implements \Stringable, \Throwable {

	/**
	 * Construct the error object
	 * @link http://www.php.net/manual/en/error.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the error message
	 * @link http://www.php.net/manual/en/error.getmessage.php
	 * @return string Returns the error message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the error code
	 * @link http://www.php.net/manual/en/error.getcode.php
	 * @return int Returns the error code as int
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the error occurred
	 * @link http://www.php.net/manual/en/error.getfile.php
	 * @return string Returns the filename in which the error occurred.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the error occurred
	 * @link http://www.php.net/manual/en/error.getline.php
	 * @return int Returns the line number where the error occurred.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/error.gettrace.php
	 * @return array Returns the stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/error.getprevious.php
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/error.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the error
	 * @link http://www.php.net/manual/en/error.tostring.php
	 * @return string Returns the string representation of the error.
	 */
	public function __toString (): string {}

}

/**
 * The base class for Exceptions that occur during generation or use of randomness.
 * @link http://www.php.net/manual/en/class.random-randomexception.php
 */
class RandomException extends \Exception implements \Throwable, \Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

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
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

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


}


namespace Random\Engine {

/**
 * Implements the Mt19937 (“Mersenne Twister”) algorithm.
 * @link http://www.php.net/manual/en/class.random-engine-mt19937.php
 */
final class Mt19937 implements \Random\Engine {

	/**
	 * Constructs a new Mt19937 engine
	 * @link http://www.php.net/manual/en/random-engine-mt19937.construct.php
	 * @param int|null $seed [optional] Fills the state with values generated with a linear congruential generator
	 * that was seeded with seed interpreted as an unsigned
	 * 32 bit integer.
	 * <p>If seed is omitted or null, a random unsigned
	 * 32 bit integer will be used.</p>
	 * @param int $mode [optional] Use one of the following constants to specify the implementation of the algorithm to use.
	 * <p>
	 * MT_RAND_MT19937:
	 * The correct Mt19937 implementation.
	 * MT_RAND_PHP:
	 * An incorrect implementation for backwards compatibility with mt_srand prior to
	 * PHP 7.1.0.
	 * </p>
	 * @return int|null 
	 */
	public function __construct (?int $seed = null, int $mode = MT_RAND_MT19937): ?int {}

	/**
	 * Generate 32 bits of randomness
	 * @link http://www.php.net/manual/en/random-engine-mt19937.generate.php
	 * @return string A string representing an unsigned 32 bit integer in little-endian order.
	 */
	public function generate (): string {}

	/**
	 * Serializes the Mt19937 object
	 * @link http://www.php.net/manual/en/random-engine-mt19937.serialize.php
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a Mt19937 object
	 * @link http://www.php.net/manual/en/random-engine-mt19937.unserialize.php
	 * @param array $data 
	 * @return void No value is returned.
	 */
	public function __unserialize (array $data): void {}

	/**
	 * Returns the internal state of the engine
	 * @link http://www.php.net/manual/en/random-engine-mt19937.debuginfo.php
	 * @return array 
	 */
	public function __debugInfo (): array {}

}

/**
 * Implements a Permuted congruential generator (PCG) with
 * 128 bits of state, XSL and RR output transformations, and 64 bits of output.
 * @link http://www.php.net/manual/en/class.random-engine-pcgoneseq128xslrr64.php
 */
final class PcgOneseq128XslRr64 implements \Random\Engine {

	/**
	 * Constructs a new PCG Oneseq 128 XSL RR 64 engine
	 * @link http://www.php.net/manual/en/random-engine-pcgoneseq128xslrr64.construct.php
	 * @param string|int|null $seed [optional] How the internal 128 bit (16 byte) state consisting of one unsigned 128 bit integer is
	 * seeded depends on the type used as the seed.
	 * <table>
	 * <tr valign="top">
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>null</td>
	 * <td>
	 * Fills the state with 16 random bytes generated using the CSPRNG.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>int</td>
	 * <td>
	 * Fills the state by setting the state to 0, advancing the engine one step,
	 * adding the value of seed interpreted as an unsigned 64 bit integer,
	 * and advancing the engine another step.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>string</td>
	 * <td>
	 * Fills the state by interpreting a 16 byte string as a little-endian unsigned
	 * 128 bit integer.
	 * </td>
	 * </tr>
	 * </table>
	 * @return string|int|null 
	 */
	public function __construct (string|int|null $seed = null): string|int|null {}

	/**
	 * Generate 64 bits of randomness
	 * @link http://www.php.net/manual/en/random-engine-pcgoneseq128xslrr64.generate.php
	 * @return string A string representing an unsigned 64 bit integer in little-endian order.
	 */
	public function generate (): string {}

	/**
	 * Efficiently move the engine ahead multiple steps
	 * @link http://www.php.net/manual/en/random-engine-pcgoneseq128xslrr64.jump.php
	 * @param int $advance The number of steps to move ahead; must be 0 or greater.
	 * @return void No value is returned.
	 */
	public function jump (int $advance): void {}

	/**
	 * Serializes the PcgOneseq128XslRr64 object
	 * @link http://www.php.net/manual/en/random-engine-pcgoneseq128xslrr64.serialize.php
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a PcgOneseq128XslRr64 object
	 * @link http://www.php.net/manual/en/random-engine-pcgoneseq128xslrr64.unserialize.php
	 * @param array $data 
	 * @return void No value is returned.
	 */
	public function __unserialize (array $data): void {}

	/**
	 * Returns the internal state of the engine
	 * @link http://www.php.net/manual/en/random-engine-pcgoneseq128xslrr64.debuginfo.php
	 * @return array 
	 */
	public function __debugInfo (): array {}

}

/**
 * Implements the xoshiro256&#42;&#42; algorithm.
 * @link http://www.php.net/manual/en/class.random-engine-xoshiro256starstar.php
 */
final class Xoshiro256StarStar implements \Random\Engine {

	/**
	 * Constructs a new xoshiro256&#42;&#42; engine
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.construct.php
	 * @param string|int|null $seed [optional] How the internal 256 bit (32 byte) state consisting of four unsigned 64 bit integers is
	 * seeded depends on the type used as the seed.
	 * <table>
	 * <tr valign="top">
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>null</td>
	 * <td>
	 * Fills the state with 32 random bytes generated using the CSPRNG.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>int</td>
	 * <td>
	 * Fills the state with four consecutive values generated with the SplitMix64 algorithm
	 * that was seeded with seed interpreted as an unsigned 64 bit integer.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>string</td>
	 * <td>
	 * Fills the state by interpreting a 32 byte string as four little-endian unsigned
	 * 64 bit integers.
	 * </td>
	 * </tr>
	 * </table>
	 * @return string|int|null 
	 */
	public function __construct (string|int|null $seed = null): string|int|null {}

	/**
	 * Generate 64 bits of randomness
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.generate.php
	 * @return string A string representing an unsigned 64 bit integer in little-endian order.
	 */
	public function generate (): string {}

	/**
	 * Efficiently move the engine ahead by 2^128 steps
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.jump.php
	 * @return void No value is returned.
	 */
	public function jump (): void {}

	/**
	 * Efficiently move the engine ahead by 2^192 steps
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.jumplong.php
	 * @return void No value is returned.
	 */
	public function jumpLong (): void {}

	/**
	 * Serializes the Xoshiro256StarStar object
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.serialize.php
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a Xoshiro256StarStar object
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.unserialize.php
	 * @param array $data 
	 * @return void No value is returned.
	 */
	public function __unserialize (array $data): void {}

	/**
	 * Returns the internal state of the engine
	 * @link http://www.php.net/manual/en/random-engine-xoshiro256starstar.debuginfo.php
	 * @return array 
	 */
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

	/**
	 * Generate cryptographically secure randomness
	 * @link http://www.php.net/manual/en/random-engine-secure.generate.php
	 * @return string A string containing PHP_INT_SIZE cryptographically secure random bytes.
	 */
	public function generate (): string {}

}


}


namespace Random {

/**
 * Provides a high-level API to the randomness provided by an Random\Engine.
 * @link http://www.php.net/manual/en/class.random-randomizer.php
 */
final class Randomizer  {

	/**
	 * The low-level source of randomness for the Random\Randomizer’s methods.
	 * @var \Random\Engine
	 * @link http://www.php.net/manual/en/class.random-randomizer.php#random\randomizer.props.engine
	 */
	public readonly \Random\Engine $engine;

	/**
	 * Constructs a new Randomizer
	 * @link http://www.php.net/manual/en/random-randomizer.construct.php
	 * @param \Random\Engine|null $engine [optional] The Random\Engine to use to generate randomness.
	 * <p>If engine is omitted or null, a new Random\Engine\Secure object will be used.</p>
	 * @return \Random\Engine|null 
	 */
	public function __construct (?\Random\Engine $engine = null): ?\Random\Engine {}

	/**
	 * Get a positive integer
	 * @link http://www.php.net/manual/en/random-randomizer.nextint.php
	 * @return int 
	 */
	public function nextInt (): int {}

	/**
	 * Get a uniformly selected integer
	 * @link http://www.php.net/manual/en/random-randomizer.getint.php
	 * @param int $min The lowest value to be returned.
	 * @param int $max The highest value to be returned.
	 * @return int A uniformly selected integer from the closed interval
	 * [min, max]. Both
	 * min and max are
	 * possible return values.
	 */
	public function getInt (int $min, int $max): int {}

	/**
	 * Get random bytes
	 * @link http://www.php.net/manual/en/random-randomizer.getbytes.php
	 * @param int $length The length of the random string that should be returned in bytes; must be 1 or greater.
	 * @return string A string containing the requested number of random bytes.
	 */
	public function getBytes (int $length): string {}

	/**
	 * Get a permutation of an array
	 * @link http://www.php.net/manual/en/random-randomizer.shufflearray.php
	 * @param array $array The array whose values are shuffled.
	 * <p>The input array will not be modified.</p>
	 * @return array A permutation of the values of array.
	 * <p>Array keys of the input array will not be preserved;
	 * the returned <p>array_is_list).</p>
	 */
	public function shuffleArray (array $array): array {}

	/**
	 * Get a byte-wise permutation of a string
	 * @link http://www.php.net/manual/en/random-randomizer.shufflebytes.php
	 * @param string $bytes The string whose bytes are shuffled.
	 * <p>The input string will not be modified.</p>
	 * @return string A permutation of the bytes of bytes.
	 */
	public function shuffleBytes (string $bytes): string {}

	/**
	 * Select random array keys
	 * @link http://www.php.net/manual/en/random-randomizer.pickarraykeys.php
	 * @param array $array The array whose array keys are selected.
	 * @param int $num The number of array keys to return; must be between 1
	 * and the number of elements in array.
	 * @return array An array containing num distinct array keys of array.
	 * <p>The returned <p>array_is_list). It will be a subset
	 * of the array returned by array_keys.</p>
	 */
	public function pickArrayKeys (array $array, int $num): array {}

	/**
	 * Serializes the Randomizer object
	 * @link http://www.php.net/manual/en/random-randomizer.serialize.php
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a Randomizer object
	 * @link http://www.php.net/manual/en/random-randomizer.unserialize.php
	 * @param array $data 
	 * @return void No value is returned.
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
 * @param int $seed [optional] 
 * @param int $mode [optional] 
 * @return void No value is returned.
 */
function mt_srand (int $seed = null, int $mode = MT_RAND_MT19937): void {}

/**
 * Seed the random number generator
 * @link http://www.php.net/manual/en/function.srand.php
 * @param int $seed [optional] 
 * @param int $mode [optional] 
 * @return void No value is returned.
 */
function srand (int $seed = null, int $mode = MT_RAND_MT19937): void {}

/**
 * Generate a random integer
 * @link http://www.php.net/manual/en/function.rand.php
 * @param int $min [optional]
 * @param int $max [optional]
 * @return int A pseudo random value between min
 * (or 0) and max (or getrandmax, inclusive).
 */
function rand (int $min = NULL, int $max = NULL): int {}

/**
 * Generate a random value via the Mersenne Twister Random Number Generator
 * @link http://www.php.net/manual/en/function.mt-rand.php
 * @param int $min [optional]
 * @param int $max [optional]
 * @return int A random integer value between min (or 0)
 * and max (or mt_getrandmax, inclusive),
 * or false if max is less than min.
 */
function mt_rand (int $min = NULL, int $max = NULL): int {}

/**
 * Show largest possible random value
 * @link http://www.php.net/manual/en/function.mt-getrandmax.php
 * @return int Returns the maximum random value returned by a call to
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
 * @var integer
 */
define ('MT_RAND_MT19937', 0);

/**
 * Indicates that an incorrect Mersenne Twister implementation will be used by the algorithm, when
 * creating a Random\Engine\Mt19937 instance using Random\Engine\Mt19937::__construct
 * or seeding the global Mersenne Twister with mt_srand.
 * The incorrect implementation is available for backwards compatibility with
 * mt_srand prior to PHP 7.1.0.
 * @link http://www.php.net/manual/en/random.constants.php
 * @var integer
 */
define ('MT_RAND_PHP', 1);


}

// End of random v.8.2.6
