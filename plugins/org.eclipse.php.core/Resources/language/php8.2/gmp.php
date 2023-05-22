<?php

// Start of gmp v.8.2.6

/**
 * A GMP number. These objects support overloaded
 * arithmetic,
 * bitwise and
 * comparison operators.
 * <p>No object-oriented interface is provided to manipulate
 * GMP objects. Please use the
 * procedural GMP API.</p>
 * @link http://www.php.net/manual/en/class.gmp.php
 */
class GMP  {

	/**
	 * {@inheritdoc}
	 * @param string|int $num [optional]
	 * @param int $base [optional]
	 */
	public function __construct (string|int $num = 0, int $base = 0) {}

	/**
	 * Serializes the GMP object
	 * @link http://www.php.net/manual/en/gmp.serialize.php
	 * @return array No value is returned.
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a GMP object
	 * @link http://www.php.net/manual/en/gmp.unserialize.php
	 * @param array $data The value being deserialized.
	 * @return void No value is returned.
	 */
	public function __unserialize (array $data): void {}

}

/**
 * Create GMP number
 * @link http://www.php.net/manual/en/function.gmp-init.php
 * @param int|string $num 
 * @param int $base [optional] 
 * @return GMP A GMP object.
 */
function gmp_init (int|string $num, int $base = null): GMP {}

/**
 * Import from a binary string
 * @link http://www.php.net/manual/en/function.gmp-import.php
 * @param string $data 
 * @param int $word_size [optional] 
 * @param int $flags [optional] 
 * @return GMP Returns a GMP number.
 */
function gmp_import (string $data, int $word_size = 1, int $flags = 'GMP_MSW_FIRST | GMP_NATIVE_ENDIAN'): GMP {}

/**
 * Export to a binary string
 * @link http://www.php.net/manual/en/function.gmp-export.php
 * @param GMP|int|string $num 
 * @param int $word_size [optional] 
 * @param int $flags [optional] 
 * @return string Returns a string.
 */
function gmp_export (GMP|int|string $num, int $word_size = 1, int $flags = 'GMP_MSW_FIRST | GMP_NATIVE_ENDIAN'): string {}

/**
 * Convert GMP number to integer
 * @link http://www.php.net/manual/en/function.gmp-intval.php
 * @param GMP|int|string $num 
 * @return int The int value of num.
 */
function gmp_intval (GMP|int|string $num): int {}

/**
 * Convert GMP number to string
 * @link http://www.php.net/manual/en/function.gmp-strval.php
 * @param GMP|int|string $num 
 * @param int $base [optional] 
 * @return string The number, as a string.
 */
function gmp_strval (GMP|int|string $num, int $base = 10): string {}

/**
 * Add numbers
 * @link http://www.php.net/manual/en/function.gmp-add.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP number representing the sum of the arguments.
 */
function gmp_add (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Subtract numbers
 * @link http://www.php.net/manual/en/function.gmp-sub.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP object.
 */
function gmp_sub (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Multiply numbers
 * @link http://www.php.net/manual/en/function.gmp-mul.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP object.
 */
function gmp_mul (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Divide numbers and get quotient and remainder
 * @link http://www.php.net/manual/en/function.gmp-div-qr.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @param int $rounding_mode [optional] 
 * @return array Returns an array, with the first
 * element being [n/d] (the integer result of the
 * division) and the second being (n - [n/d] &#42; d)
 * (the remainder of the division).
 */
function gmp_div_qr (GMP|int|string $num1, GMP|int|string $num2, int $rounding_mode = GMP_ROUND_ZERO): array {}

/**
 * Divide numbers
 * @link http://www.php.net/manual/en/function.gmp-div-q.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @param int $rounding_mode [optional] 
 * @return GMP A GMP object.
 */
function gmp_div_q (GMP|int|string $num1, GMP|int|string $num2, int $rounding_mode = GMP_ROUND_ZERO): GMP {}

/**
 * Remainder of the division of numbers
 * @link http://www.php.net/manual/en/function.gmp-div-r.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @param int $rounding_mode [optional] 
 * @return GMP The remainder, as a GMP number.
 */
function gmp_div_r (GMP|int|string $num1, GMP|int|string $num2, int $rounding_mode = GMP_ROUND_ZERO): GMP {}

/**
 * Alias of gmp_div_q
 * @link http://www.php.net/manual/en/function.gmp-div.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @param int $rounding_mode [optional] 
 * @return GMP A GMP object.
 */
function gmp_div (GMP|int|string $num1, GMP|int|string $num2, int $rounding_mode = GMP_ROUND_ZERO): GMP {}

/**
 * Modulo operation
 * @link http://www.php.net/manual/en/function.gmp-mod.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP object.
 */
function gmp_mod (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Exact division of numbers
 * @link http://www.php.net/manual/en/function.gmp-divexact.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP object.
 */
function gmp_divexact (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Negate number
 * @link http://www.php.net/manual/en/function.gmp-neg.php
 * @param GMP|int|string $num 
 * @return GMP Returns -num, as a GMP number.
 */
function gmp_neg (GMP|int|string $num): GMP {}

/**
 * Absolute value
 * @link http://www.php.net/manual/en/function.gmp-abs.php
 * @param GMP|int|string $num 
 * @return GMP Returns the absolute value of num, as a GMP number.
 */
function gmp_abs (GMP|int|string $num): GMP {}

/**
 * Factorial
 * @link http://www.php.net/manual/en/function.gmp-fact.php
 * @param GMP|int|string $num 
 * @return GMP A GMP object.
 */
function gmp_fact (GMP|int|string $num): GMP {}

/**
 * Calculate square root
 * @link http://www.php.net/manual/en/function.gmp-sqrt.php
 * @param GMP|int|string $num 
 * @return GMP The integer portion of the square root, as a GMP number.
 */
function gmp_sqrt (GMP|int|string $num): GMP {}

/**
 * Square root with remainder
 * @link http://www.php.net/manual/en/function.gmp-sqrtrem.php
 * @param GMP|int|string $num 
 * @return array Returns array where first element is the integer square root of
 * num and the second is the remainder
 * (i.e., the difference between num and the
 * first element squared).
 */
function gmp_sqrtrem (GMP|int|string $num): array {}

/**
 * Take the integer part of nth root
 * @link http://www.php.net/manual/en/function.gmp-root.php
 * @param GMP|int|string $num 
 * @param int $nth 
 * @return GMP The integer component of the resultant root, as a GMP number.
 */
function gmp_root (GMP|int|string $num, int $nth): GMP {}

/**
 * Take the integer part and remainder of nth root
 * @link http://www.php.net/manual/en/function.gmp-rootrem.php
 * @param GMP|int|string $num 
 * @param int $nth 
 * @return array A two element array, where the first element is the integer component of
 * the root, and the second element is the remainder, both represented as GMP
 * numbers.
 */
function gmp_rootrem (GMP|int|string $num, int $nth): array {}

/**
 * Raise number into power
 * @link http://www.php.net/manual/en/function.gmp-pow.php
 * @param GMP|int|string $num 
 * @param int $exponent 
 * @return GMP The new (raised) number, as a GMP number. The case of 
 * 0^0 yields 1.
 */
function gmp_pow (GMP|int|string $num, int $exponent): GMP {}

/**
 * Raise number into power with modulo
 * @link http://www.php.net/manual/en/function.gmp-powm.php
 * @param GMP|int|string $num 
 * @param GMP|int|string $exponent 
 * @param GMP|int|string $modulus 
 * @return GMP The new (raised) number, as a GMP number.
 */
function gmp_powm (GMP|int|string $num, GMP|int|string $exponent, GMP|int|string $modulus): GMP {}

/**
 * Perfect square check
 * @link http://www.php.net/manual/en/function.gmp-perfect-square.php
 * @param GMP|int|string $num 
 * @return bool Returns true if num is a perfect square,
 * false otherwise.
 */
function gmp_perfect_square (GMP|int|string $num): bool {}

/**
 * Perfect power check
 * @link http://www.php.net/manual/en/function.gmp-perfect-power.php
 * @param GMP|int|string $num >A GMP object, an int or a numeric string.
 * @return bool Returns true if num is a perfect power, false otherwise.
 */
function gmp_perfect_power (GMP|int|string $num): bool {}

/**
 * Check if number is "probably prime"
 * @link http://www.php.net/manual/en/function.gmp-prob-prime.php
 * @param GMP|int|string $num 
 * @param int $repetitions [optional] 
 * @return int If this function returns 0, num is
 * definitely not prime. If it returns 1, then
 * num is "probably" prime. If it returns 2,
 * then num is surely prime.
 */
function gmp_prob_prime (GMP|int|string $num, int $repetitions = 10): int {}

/**
 * Calculate GCD
 * @link http://www.php.net/manual/en/function.gmp-gcd.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A positive GMP number that divides into both
 * num1 and num2.
 */
function gmp_gcd (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Calculate GCD and multipliers
 * @link http://www.php.net/manual/en/function.gmp-gcdext.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return array An array of GMP numbers.
 */
function gmp_gcdext (GMP|int|string $num1, GMP|int|string $num2): array {}

/**
 * Calculate LCM
 * @link http://www.php.net/manual/en/function.gmp-lcm.php
 * @param GMP|int|string $num1 >A GMP object, an int or a numeric string.
 * @param GMP|int|string $num2 >A GMP object, an int or a numeric string.
 * @return GMP A GMP object.
 */
function gmp_lcm (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Inverse by modulo
 * @link http://www.php.net/manual/en/function.gmp-invert.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP|false A GMP number on success or false if an inverse does not exist.
 */
function gmp_invert (GMP|int|string $num1, GMP|int|string $num2): GMP|false {}

/**
 * Jacobi symbol
 * @link http://www.php.net/manual/en/function.gmp-jacobi.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return int A GMP object.
 */
function gmp_jacobi (GMP|int|string $num1, GMP|int|string $num2): int {}

/**
 * Legendre symbol
 * @link http://www.php.net/manual/en/function.gmp-legendre.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return int A GMP object.
 */
function gmp_legendre (GMP|int|string $num1, GMP|int|string $num2): int {}

/**
 * Kronecker symbol
 * @link http://www.php.net/manual/en/function.gmp-kronecker.php
 * @param GMP|int|string $num1 >A GMP object, an int or a numeric string.
 * @param GMP|int|string $num2 >A GMP object, an int or a numeric string.
 * @return int Returns the Kronecker symbol of num1 and
 * num2
 */
function gmp_kronecker (GMP|int|string $num1, GMP|int|string $num2): int {}

/**
 * Compare numbers
 * @link http://www.php.net/manual/en/function.gmp-cmp.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return int Returns a positive value if a &gt; b, zero if
 * a = b and a negative value if a &lt;
 * b.
 */
function gmp_cmp (GMP|int|string $num1, GMP|int|string $num2): int {}

/**
 * Sign of number
 * @link http://www.php.net/manual/en/function.gmp-sign.php
 * @param GMP|int|string $num 
 * @return int Returns 1 if num is positive,
 * -1 if num is negative,
 * and 0 if num is zero.
 */
function gmp_sign (GMP|int|string $num): int {}

/**
 * Sets the RNG seed
 * @link http://www.php.net/manual/en/function.gmp-random-seed.php
 * @param GMP|int|string $seed 
 * @return void No value is returned.
 */
function gmp_random_seed (GMP|int|string $seed): void {}

/**
 * Random number
 * @link http://www.php.net/manual/en/function.gmp-random-bits.php
 * @param int $bits 
 * @return GMP A random GMP number.
 */
function gmp_random_bits (int $bits): GMP {}

/**
 * Random number
 * @link http://www.php.net/manual/en/function.gmp-random-range.php
 * @param GMP|int|string $min 
 * @param GMP|int|string $max 
 * @return GMP A random GMP number.
 */
function gmp_random_range (GMP|int|string $min, GMP|int|string $max): GMP {}

/**
 * Bitwise AND
 * @link http://www.php.net/manual/en/function.gmp-and.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP number representing the bitwise AND comparison.
 */
function gmp_and (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Bitwise OR
 * @link http://www.php.net/manual/en/function.gmp-or.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP object.
 */
function gmp_or (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Calculates one's complement
 * @link http://www.php.net/manual/en/function.gmp-com.php
 * @param GMP|int|string $num 
 * @return GMP Returns the one's complement of num, as a GMP number.
 */
function gmp_com (GMP|int|string $num): GMP {}

/**
 * Bitwise XOR
 * @link http://www.php.net/manual/en/function.gmp-xor.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return GMP A GMP object.
 */
function gmp_xor (GMP|int|string $num1, GMP|int|string $num2): GMP {}

/**
 * Set bit
 * @link http://www.php.net/manual/en/function.gmp-setbit.php
 * @param GMP $num 
 * @param int $index 
 * @param bool $value [optional] 
 * @return void A GMP object.
 */
function gmp_setbit (GMP $num, int $index, bool $value = true): void {}

/**
 * Clear bit
 * @link http://www.php.net/manual/en/function.gmp-clrbit.php
 * @param GMP $num 
 * @param int $index 
 * @return void A GMP object.
 */
function gmp_clrbit (GMP $num, int $index): void {}

/**
 * Tests if a bit is set
 * @link http://www.php.net/manual/en/function.gmp-testbit.php
 * @param GMP|int|string $num 
 * @param int $index 
 * @return bool Returns true if the bit is set in num, 
 * otherwise false.
 */
function gmp_testbit (GMP|int|string $num, int $index): bool {}

/**
 * Scan for 0
 * @link http://www.php.net/manual/en/function.gmp-scan0.php
 * @param GMP|int|string $num1 
 * @param int $start 
 * @return int Returns the index of the found bit, as an int. The
 * index starts from 0.
 */
function gmp_scan0 (GMP|int|string $num1, int $start): int {}

/**
 * Scan for 1
 * @link http://www.php.net/manual/en/function.gmp-scan1.php
 * @param GMP|int|string $num1 
 * @param int $start 
 * @return int Returns the index of the found bit, as an int.
 * If no set bit is found, -1 is returned.
 */
function gmp_scan1 (GMP|int|string $num1, int $start): int {}

/**
 * Population count
 * @link http://www.php.net/manual/en/function.gmp-popcount.php
 * @param GMP|int|string $num 
 * @return int The population count of num, as an int.
 */
function gmp_popcount (GMP|int|string $num): int {}

/**
 * Hamming distance
 * @link http://www.php.net/manual/en/function.gmp-hamdist.php
 * @param GMP|int|string $num1 
 * @param GMP|int|string $num2 
 * @return int The hamming distance between num1 and num2, as an int.
 */
function gmp_hamdist (GMP|int|string $num1, GMP|int|string $num2): int {}

/**
 * Find next prime number
 * @link http://www.php.net/manual/en/function.gmp-nextprime.php
 * @param GMP|int|string $num 
 * @return GMP Return the next prime number greater than num,
 * as a GMP number.
 */
function gmp_nextprime (GMP|int|string $num): GMP {}

/**
 * Calculates binomial coefficient
 * @link http://www.php.net/manual/en/function.gmp-binomial.php
 * @param GMP|int|string $n 
 * @param int $k 
 * @return GMP Returns the binomial coefficient C(n, k).
 */
function gmp_binomial (GMP|int|string $n, int $k): GMP {}


/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_ROUND_ZERO', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_ROUND_PLUSINF', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_ROUND_MINUSINF', 2);

/**
 * The GMP library version
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var string
 */
define ('GMP_VERSION', "6.2.1");

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_MSW_FIRST', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_LSW_FIRST', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_LITTLE_ENDIAN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_BIG_ENDIAN', 8);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 * @var int
 */
define ('GMP_NATIVE_ENDIAN', 16);

// End of gmp v.8.2.6
