<?php

// Start of gmp v.8.1.19

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
	 * Serializes the GMP object
	 * @link http://www.php.net/manual/en/gmp.serialize.php
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes the data parameter into a GMP object
	 * @link http://www.php.net/manual/en/gmp.unserialize.php
	 * @param array $data The value being deserialized.
	 * @return void 
	 */
	public function __unserialize (array $data): void {}

}

/**
 * Create GMP number
 * @link http://www.php.net/manual/en/function.gmp-init.php
 * @param mixed $num An integer or a string. The string representation can be decimal, 
 * hexadecimal or octal.
 * @param int $base [optional] <p>
 * The base.
 * </p>
 * <p>
 * The base may vary from 2 to 62. If base is 0 (default value), the
 * actual base is determined from the leading characters: if the first
 * two characters are 0x or 0X,
 * hexadecimal is assumed, if the first two characters are 0b or 0B,
 * binary is assumed, otherwise if the first character is 0,
 * octal is assumed, otherwise decimal is assumed.
 * For bases up to 36, case is ignored; upper-case and lower-case letters have the same value.
 * For bases 37 to 62, upper-case letter represent the usual 10 to 35 while lower-case letter represent 36 to 61.
 * </p>
 * @return GMP A GMP object.
 */
function gmp_init ($num, int $base = null): GMP {}

/**
 * Import from a binary string
 * @link http://www.php.net/manual/en/function.gmp-import.php
 * @param string $data The binary string being imported
 * @param int $word_size [optional] Default value is 1. The number of bytes in each chunk of binary data. This is mainly used in conjunction with the options parameter.
 * @param int $flags [optional] Default value is GMP_MSW_FIRST | GMP_NATIVE_ENDIAN.
 * @return GMP a GMP number.
 */
function gmp_import (string $data, int $word_size = null, int $flags = null): GMP {}

/**
 * Export to a binary string
 * @link http://www.php.net/manual/en/function.gmp-export.php
 * @param mixed $num The GMP number being exported
 * @param int $word_size [optional] Default value is 1. The number of bytes in each chunk of binary data. This is mainly used in conjunction with the options parameter.
 * @param int $flags [optional] Default value is GMP_MSW_FIRST | GMP_NATIVE_ENDIAN.
 * @return string a string.
 */
function gmp_export ($num, int $word_size = null, int $flags = null): string {}

/**
 * Convert GMP number to integer
 * @link http://www.php.net/manual/en/function.gmp-intval.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return int The int value of num.
 */
function gmp_intval ($num): int {}

/**
 * Convert GMP number to string
 * @link http://www.php.net/manual/en/function.gmp-strval.php
 * @param mixed $num <p>
 * The GMP number that will be converted to a string.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $base [optional] The base of the returned number. The default base is 10. 
 * Allowed values for the base are from 2 to 62 and -2 to -36.
 * @return string The number, as a string.
 */
function gmp_strval ($num, int $base = null): string {}

/**
 * Add numbers
 * @link http://www.php.net/manual/en/function.gmp-add.php
 * @param mixed $num1 <p>
 * The first summand (augent).
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * The second summand (addend).
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP number representing the sum of the arguments.
 */
function gmp_add ($num1, $num2): GMP {}

/**
 * Subtract numbers
 * @link http://www.php.net/manual/en/function.gmp-sub.php
 * @param mixed $num1 <p>
 * The number being subtracted from.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * The number subtracted from num1.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP object.
 */
function gmp_sub ($num1, $num2): GMP {}

/**
 * Multiply numbers
 * @link http://www.php.net/manual/en/function.gmp-mul.php
 * @param mixed $num1 <p>
 * A number that will be multiplied by num2.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * A number that will be multiplied by num1.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP object.
 */
function gmp_mul ($num1, $num2): GMP {}

/**
 * Divide numbers and get quotient and remainder
 * @link http://www.php.net/manual/en/function.gmp-div-qr.php
 * @param mixed $num1 <p>
 * The number being divided.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * The number that num1 is being divided by.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $rounding_mode [optional] See the gmp_div_q function for description
 * of the rounding_mode argument.
 * @return array an array, with the first
 * element being [n/d] (the integer result of the
 * division) and the second being (n - [n/d] &#42; d)
 * (the remainder of the division).
 */
function gmp_div_qr ($num1, $num2, int $rounding_mode = null): array {}

/**
 * Divide numbers
 * @link http://www.php.net/manual/en/function.gmp-div-q.php
 * @param mixed $num1 <p>
 * The number being divided.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * The number that num1 is being divided by.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $rounding_mode [optional] <p>
 * The result rounding is defined by the
 * rounding_mode, which can have the following
 * values:
 * <p>
 * <br>
 * GMP_ROUND_ZERO: The result is truncated
 * towards 0.
 * <br>
 * GMP_ROUND_PLUSINF: The result is
 * rounded towards +infinity.
 * <br>
 * GMP_ROUND_MINUSINF: The result is
 * rounded towards -infinity.
 * </p>
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP object.
 */
function gmp_div_q ($num1, $num2, int $rounding_mode = null): GMP {}

/**
 * Remainder of the division of numbers
 * @link http://www.php.net/manual/en/function.gmp-div-r.php
 * @param mixed $num1 <p>
 * The number being divided.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * The number that num1 is being divided by.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $rounding_mode [optional] See the gmp_div_q function for description
 * of the rounding_mode argument.
 * @return GMP The remainder, as a GMP number.
 */
function gmp_div_r ($num1, $num2, int $rounding_mode = null): GMP {}

/**
 * Alias: gmp_div_q
 * @link http://www.php.net/manual/en/function.gmp-div.php
 * @param GMP|string|int $num1
 * @param GMP|string|int $num2
 * @param int $rounding_mode [optional]
 */
function gmp_div (GMP|string|int $num1, GMP|string|int $num2, int $rounding_mode = 0): GMP {}

/**
 * Modulo operation
 * @link http://www.php.net/manual/en/function.gmp-mod.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 <p>
 * The modulo that is being evaluated.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP object.
 */
function gmp_mod ($num1, $num2): GMP {}

/**
 * Exact division of numbers
 * @link http://www.php.net/manual/en/function.gmp-divexact.php
 * @param mixed $num1 <p>
 * The number being divided.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $num2 <p>
 * The number that a is being divided by.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP object.
 */
function gmp_divexact ($num1, $num2): GMP {}

/**
 * Negate number
 * @link http://www.php.net/manual/en/function.gmp-neg.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return GMP -num, as a GMP number.
 */
function gmp_neg ($num): GMP {}

/**
 * Absolute value
 * @link http://www.php.net/manual/en/function.gmp-abs.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return GMP the absolute value of num, as a GMP number.
 */
function gmp_abs ($num): GMP {}

/**
 * Factorial
 * @link http://www.php.net/manual/en/function.gmp-fact.php
 * @param mixed $num <p>
 * The factorial number.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP A GMP object.
 */
function gmp_fact ($num): GMP {}

/**
 * Calculate square root
 * @link http://www.php.net/manual/en/function.gmp-sqrt.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return GMP The integer portion of the square root, as a GMP number.
 */
function gmp_sqrt ($num): GMP {}

/**
 * Square root with remainder
 * @link http://www.php.net/manual/en/function.gmp-sqrtrem.php
 * @param mixed $num <p>
 * The number being square rooted.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return array array where first element is the integer square root of
 * num and the second is the remainder
 * (i.e., the difference between num and the
 * first element squared).
 */
function gmp_sqrtrem ($num): array {}

/**
 * Take the integer part of nth root
 * @link http://www.php.net/manual/en/function.gmp-root.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @param int $nth The positive root to take of num.
 * @return GMP The integer component of the resultant root, as a GMP number.
 */
function gmp_root ($num, int $nth): GMP {}

/**
 * Take the integer part and remainder of nth root
 * @link http://www.php.net/manual/en/function.gmp-rootrem.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @param int $nth The positive root to take of num.
 * @return array A two element array, where the first element is the integer component of
 * the root, and the second element is the remainder, both represented as GMP
 * numbers.
 */
function gmp_rootrem ($num, int $nth): array {}

/**
 * Raise number into power
 * @link http://www.php.net/manual/en/function.gmp-pow.php
 * @param mixed $num <p>
 * The base number.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $exponent The positive power to raise the num.
 * @return GMP The new (raised) number, as a GMP number. The case of 
 * 0^0 yields 1.
 */
function gmp_pow ($num, int $exponent): GMP {}

/**
 * Raise number into power with modulo
 * @link http://www.php.net/manual/en/function.gmp-powm.php
 * @param mixed $num <p>
 * The base number.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $exponent <p>
 * The positive power to raise the num.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param mixed $modulus <p>
 * The modulo.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return GMP The new (raised) number, as a GMP number.
 */
function gmp_powm ($num, $exponent, $modulus): GMP {}

/**
 * Perfect square check
 * @link http://www.php.net/manual/en/function.gmp-perfect-square.php
 * @param mixed $num <p>
 * The number being checked as a perfect square.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return bool true if num is a perfect square,
 * false otherwise.
 */
function gmp_perfect_square ($num): bool {}

/**
 * Perfect power check
 * @link http://www.php.net/manual/en/function.gmp-perfect-power.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return bool true if num is a perfect power, false otherwise.
 */
function gmp_perfect_power ($num): bool {}

/**
 * Check if number is "probably prime"
 * @link http://www.php.net/manual/en/function.gmp-prob-prime.php
 * @param mixed $num <p>
 * The number being checked as a prime.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $repetitions [optional] <p>
 * Reasonable values
 * of repetitions vary from 5 to 10 (default being
 * 10); a higher value lowers the probability for a non-prime to
 * pass as a "probable" prime.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return int If this function returns 0, num is
 * definitely not prime. If it returns 1, then
 * num is "probably" prime. If it returns 2,
 * then num is surely prime.
 */
function gmp_prob_prime ($num, int $repetitions = null): int {}

/**
 * Calculate GCD
 * @link http://www.php.net/manual/en/function.gmp-gcd.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return GMP A positive GMP number that divides into both
 * num1 and num2.
 */
function gmp_gcd ($num1, $num2): GMP {}

/**
 * Calculate GCD and multipliers
 * @link http://www.php.net/manual/en/function.gmp-gcdext.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return array An array of GMP numbers.
 */
function gmp_gcdext ($num1, $num2): array {}

/**
 * Calculate LCM
 * @link http://www.php.net/manual/en/function.gmp-lcm.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return GMP A GMP object.
 */
function gmp_lcm ($num1, $num2): GMP {}

/**
 * Inverse by modulo
 * @link http://www.php.net/manual/en/function.gmp-invert.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return mixed A GMP number on success or false if an inverse does not exist.
 */
function gmp_invert ($num1, $num2): GMP|false {}

/**
 * Jacobi symbol
 * @link http://www.php.net/manual/en/function.gmp-jacobi.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 <p>A GMP object, an integer or a numeric string.</p> 
 * <p>
 * Should be odd and must be positive.
 * </p>
 * @return int A GMP object.
 */
function gmp_jacobi ($num1, $num2): int {}

/**
 * Legendre symbol
 * @link http://www.php.net/manual/en/function.gmp-legendre.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 <p>A GMP object, an integer or a numeric string.</p> 
 * <p>
 * Should be odd and must be positive.
 * </p>
 * @return int A GMP object.
 */
function gmp_legendre ($num1, $num2): int {}

/**
 * Kronecker symbol
 * @link http://www.php.net/manual/en/function.gmp-kronecker.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return int the Kronecker symbol of num1 and
 * num2
 */
function gmp_kronecker ($num1, $num2): int {}

/**
 * Compare numbers
 * @link http://www.php.net/manual/en/function.gmp-cmp.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return int a positive value if a &gt; b, zero if
 * a = b and a negative value if a &lt;
 * b.
 */
function gmp_cmp ($num1, $num2): int {}

/**
 * Sign of number
 * @link http://www.php.net/manual/en/function.gmp-sign.php
 * @param mixed $num Either a GMP object, or a numeric
 * string provided that it is possible to convert the latter to an
 * int.
 * @return int 1 if num is positive,
 * -1 if num is negative,
 * and 0 if num is zero.
 */
function gmp_sign ($num): int {}

/**
 * Sets the RNG seed
 * @link http://www.php.net/manual/en/function.gmp-random-seed.php
 * @param mixed $seed <p>
 * The seed to be set for the gmp_random,
 * gmp_random_bits, and
 * gmp_random_range functions.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @return void 
 */
function gmp_random_seed ($seed): void {}

/**
 * Random number
 * @link http://www.php.net/manual/en/function.gmp-random-bits.php
 * @param int $bits The number of bits.
 * @return GMP A random GMP number.
 */
function gmp_random_bits (int $bits): GMP {}

/**
 * Random number
 * @link http://www.php.net/manual/en/function.gmp-random-range.php
 * @param mixed $min A GMP number representing the lower bound for the random number
 * @param mixed $max A GMP number representing the upper bound for the random number
 * @return GMP A random GMP number.
 */
function gmp_random_range ($min, $max): GMP {}

/**
 * Bitwise AND
 * @link http://www.php.net/manual/en/function.gmp-and.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return GMP A GMP number representing the bitwise AND comparison.
 */
function gmp_and ($num1, $num2): GMP {}

/**
 * Bitwise OR
 * @link http://www.php.net/manual/en/function.gmp-or.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return GMP A GMP object.
 */
function gmp_or ($num1, $num2): GMP {}

/**
 * Calculates one's complement
 * @link http://www.php.net/manual/en/function.gmp-com.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return GMP the one's complement of num, as a GMP number.
 */
function gmp_com ($num): GMP {}

/**
 * Bitwise XOR
 * @link http://www.php.net/manual/en/function.gmp-xor.php
 * @param mixed $num1 A GMP object, an integer or a numeric string.
 * @param mixed $num2 A GMP object, an integer or a numeric string.
 * @return GMP A GMP object.
 */
function gmp_xor ($num1, $num2): GMP {}

/**
 * Set bit
 * @link http://www.php.net/manual/en/function.gmp-setbit.php
 * @param GMP $num A GMP object.
 * @param int $index The index of the bit to set. Index 0 represents the least significant bit.
 * @param bool $value [optional] True to set the bit (set it to 1/on); false to clear the bit (set it to 0/off).
 * @return void A GMP object.
 */
function gmp_setbit (GMP $num, int $index, bool $value = null): void {}

/**
 * Clear bit
 * @link http://www.php.net/manual/en/function.gmp-clrbit.php
 * @param GMP $num A GMP object.
 * @param int $index The index of the bit to clear. Index 0 represents the least significant bit.
 * @return void A GMP object.
 */
function gmp_clrbit (GMP $num, int $index): void {}

/**
 * Tests if a bit is set
 * @link http://www.php.net/manual/en/function.gmp-testbit.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @param int $index The bit to test
 * @return bool true if the bit is set in num, 
 * otherwise false.
 */
function gmp_testbit ($num, int $index): bool {}

/**
 * Scan for 0
 * @link http://www.php.net/manual/en/function.gmp-scan0.php
 * @param mixed $num1 <p>
 * The number to scan.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $start The starting bit.
 * @return int the index of the found bit, as an int. The
 * index starts from 0.
 */
function gmp_scan0 ($num1, int $start): int {}

/**
 * Scan for 1
 * @link http://www.php.net/manual/en/function.gmp-scan1.php
 * @param mixed $num1 <p>
 * The number to scan.
 * </p>
 * <p>A GMP object, an integer or a numeric string.</p>
 * @param int $start The starting bit.
 * @return int the index of the found bit, as an int.
 * If no set bit is found, -1 is returned.
 */
function gmp_scan1 ($num1, int $start): int {}

/**
 * Population count
 * @link http://www.php.net/manual/en/function.gmp-popcount.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return int The population count of num, as an int.
 */
function gmp_popcount ($num): int {}

/**
 * Hamming distance
 * @link http://www.php.net/manual/en/function.gmp-hamdist.php
 * @param mixed $num1 <p>A GMP object, an integer or a numeric string.</p> 
 * <p>
 * It should be positive.
 * </p>
 * @param mixed $num2 <p>A GMP object, an integer or a numeric string.</p> 
 * <p>
 * It should be positive.
 * </p>
 * @return int The hamming distance between num1 and num2, as an int.
 */
function gmp_hamdist ($num1, $num2): int {}

/**
 * Find next prime number
 * @link http://www.php.net/manual/en/function.gmp-nextprime.php
 * @param mixed $num A GMP object, an integer or a numeric string.
 * @return GMP Return the next prime number greater than num,
 * as a GMP number.
 */
function gmp_nextprime ($num): GMP {}

/**
 * Calculates binomial coefficient
 * @link http://www.php.net/manual/en/function.gmp-binomial.php
 * @param mixed $n A GMP object, an integer or a numeric string.
 * @param int $k 
 * @return GMP the binomial coefficient C(n, k).
 */
function gmp_binomial ($n, int $k): GMP {}


/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_ROUND_ZERO', 0);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_ROUND_PLUSINF', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_ROUND_MINUSINF', 2);

/**
 * The GMP library version
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_VERSION', "6.2.1");

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_MSW_FIRST', 1);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_LSW_FIRST', 2);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_LITTLE_ENDIAN', 4);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_BIG_ENDIAN', 8);

/**
 * 
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_NATIVE_ENDIAN', 16);

// End of gmp v.8.1.19
