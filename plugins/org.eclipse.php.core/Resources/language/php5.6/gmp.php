<?php

// Start of gmp v.

class GMP  {
}

/**
 * Create GMP number
 * @link http://www.php.net/manual/en/function.gmp-init.php
 * @param number mixed <p>
 * An integer or a string. The string representation can be decimal, 
 * hexadecimal or octal.
 * </p>
 * @param base int[optional] <p>
 * The base.
 * </p>
 * <p>
 * The base may vary from 2 to 36. If base is 0 (default value), the
 * actual base is determined from the leading characters: if the first
 * two characters are 0x or 0X,
 * hexadecimal is assumed, otherwise if the first character is "0",
 * octal is assumed, otherwise decimal is assumed.
 * </p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_init ($number, $base = null) {}

/**
 * Convert GMP number to integer
 * @link http://www.php.net/manual/en/function.gmp-intval.php
 * @param gmpnumber GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return int The integer value of gmpnumber.
 */
function gmp_intval (GMP $gmpnumber) {}

/**
 * Convert GMP number to string
 * @link http://www.php.net/manual/en/function.gmp-strval.php
 * @param gmpnumber GMP <p>
 * The GMP number that will be converted to a string.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param base int[optional] <p>
 * The base of the returned number. The default base is 10. 
 * Allowed values for the base are from 2 to 62 and -2 to -36.
 * </p>
 * @return string The number, as a string.
 */
function gmp_strval (GMP $gmpnumber, $base = null) {}

/**
 * Add numbers
 * @link http://www.php.net/manual/en/function.gmp-add.php
 * @param a GMP <p>
 * A number that will be added.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP <p>
 * A number that will be added.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number representing the sum of the arguments.
 */
function gmp_add (GMP $a, GMP $b) {}

/**
 * Subtract numbers
 * @link http://www.php.net/manual/en/function.gmp-sub.php
 * @param a GMP <p>
 * The number being subtracted from.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP <p>
 * The number subtracted from a.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_sub (GMP $a, GMP $b) {}

/**
 * Multiply numbers
 * @link http://www.php.net/manual/en/function.gmp-mul.php
 * @param a GMP <p>
 * A number that will be multiplied by b.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP <p>
 * A number that will be multiplied by a.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_mul (GMP $a, GMP $b) {}

/**
 * Divide numbers and get quotient and remainder
 * @link http://www.php.net/manual/en/function.gmp-div-qr.php
 * @param n GMP <p>
 * The number being divided.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param d GMP <p>
 * The number that n is being divided by.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param round int[optional] <p>
 * See the gmp_div_q function for description
 * of the round argument.
 * </p>
 * @return array an array, with the first
 * element being [n/d] (the integer result of the
 * division) and the second being (n - [n/d] * d)
 * (the remainder of the division).
 */
function gmp_div_qr (GMP $n, GMP $d, $round = null) {}

/**
 * Divide numbers
 * @link http://www.php.net/manual/en/function.gmp-div-q.php
 * @param a GMP <p>
 * The number being divided.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP <p>
 * The number that a is being divided by.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param round int[optional] <p>
 * The result rounding is defined by the
 * round, which can have the following
 * values:
 * GMP_ROUND_ZERO: The result is truncated
 * towards 0.
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_div_q (GMP $a, GMP $b, $round = null) {}

/**
 * Remainder of the division of numbers
 * @link http://www.php.net/manual/en/function.gmp-div-r.php
 * @param n GMP <p>
 * The number being divided.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param d GMP <p>
 * The number that n is being divided by.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param round int[optional] <p>
 * See the gmp_div_q function for description
 * of the round argument.
 * </p>
 * @return GMP The remainder, as a GMP number.
 */
function gmp_div_r (GMP $n, GMP $d, $round = null) {}

/**
 * &Alias; <function>gmp_div_q</function>
 * @link http://www.php.net/manual/en/function.gmp-div.php
 * @param a
 * @param b
 * @param round[optional]
 */
function gmp_div ($a, $b, $round = null) {}

/**
 * Modulo operation
 * @link http://www.php.net/manual/en/function.gmp-mod.php
 * @param n GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param d GMP <p>
 * The modulo that is being evaluated.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_mod (GMP $n, GMP $d) {}

/**
 * Exact division of numbers
 * @link http://www.php.net/manual/en/function.gmp-divexact.php
 * @param n GMP <p>
 * The number being divided.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param d GMP <p>
 * The number that a is being divided by.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_divexact (GMP $n, GMP $d) {}

/**
 * Negate number
 * @link http://www.php.net/manual/en/function.gmp-neg.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP -a, as a GMP number.
 */
function gmp_neg (GMP $a) {}

/**
 * Absolute value
 * @link http://www.php.net/manual/en/function.gmp-abs.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP the absolute value of a, as a GMP number.
 */
function gmp_abs (GMP $a) {}

/**
 * Factorial
 * @link http://www.php.net/manual/en/function.gmp-fact.php
 * @param a mixed <p>
 * The factorial number.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_fact ($a) {}

/**
 * Calculate square root
 * @link http://www.php.net/manual/en/function.gmp-sqrt.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP The integer portion of the square root, as a GMP number.
 */
function gmp_sqrt (GMP $a) {}

/**
 * Square root with remainder
 * @link http://www.php.net/manual/en/function.gmp-sqrtrem.php
 * @param a GMP <p>
 * The number being square rooted.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return array array where first element is the integer square root of
 * a and the second is the remainder
 * (i.e., the difference between a and the
 * first element squared).
 */
function gmp_sqrtrem (GMP $a) {}

/**
 * Take the integer part of nth root
 * @link http://www.php.net/manual/en/function.gmp-root.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param nth int <p>
 * The positive root to take of a.
 * </p>
 * @return GMP The integer component of the resultant root, as a GMP number.
 */
function gmp_root (GMP $a, $nth) {}

/**
 * Take the integer part and remainder of nth root
 * @link http://www.php.net/manual/en/function.gmp-rootrem.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param nth int <p>
 * The positive root to take of a.
 * </p>
 * @return array A two element array, where the first element is the integer component of
 * the root, and the second element is the remainder, both represented as GMP
 * numbers.
 */
function gmp_rootrem (GMP $a, $nth) {}

/**
 * Raise number into power
 * @link http://www.php.net/manual/en/function.gmp-pow.php
 * @param base GMP <p>
 * The base number.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param exp int <p>
 * The positive power to raise the base.
 * </p>
 * @return GMP The new (raised) number, as a GMP number. The case of 
 * 0^0 yields 1.
 */
function gmp_pow (GMP $base, $exp) {}

/**
 * Raise number into power with modulo
 * @link http://www.php.net/manual/en/function.gmp-powm.php
 * @param base GMP <p>
 * The base number.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param exp GMP <p>
 * The positive power to raise the base.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param mod GMP <p>
 * The modulo.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP The new (raised) number, as a GMP number.
 */
function gmp_powm (GMP $base, GMP $exp, GMP $mod) {}

/**
 * Perfect square check
 * @link http://www.php.net/manual/en/function.gmp-perfect-square.php
 * @param a GMP <p>
 * The number being checked as a perfect square.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return bool true if a is a perfect square,
 * false otherwise.
 */
function gmp_perfect_square (GMP $a) {}

/**
 * Check if number is "probably prime"
 * @link http://www.php.net/manual/en/function.gmp-prob-prime.php
 * @param a GMP <p>
 * The number being checked as a prime.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param reps int[optional] <p>
 * Reasonable values
 * of reps vary from 5 to 10 (default being
 * 10); a higher value lowers the probability for a non-prime to
 * pass as a "probable" prime.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return int If this function returns 0, a is
 * definitely not prime. If it returns 1, then
 * a is "probably" prime. If it returns 2,
 * then a is surely prime.
 */
function gmp_prob_prime (GMP $a, $reps = null) {}

/**
 * Calculate GCD
 * @link http://www.php.net/manual/en/function.gmp-gcd.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A positive GMP number that divides into both
 * a and b.
 */
function gmp_gcd (GMP $a, GMP $b) {}

/**
 * Calculate GCD and multipliers
 * @link http://www.php.net/manual/en/function.gmp-gcdext.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return array An array of GMP numbers.
 */
function gmp_gcdext (GMP $a, GMP $b) {}

/**
 * Inverse by modulo
 * @link http://www.php.net/manual/en/function.gmp-invert.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number on success or false if an inverse does not exist.
 */
function gmp_invert (GMP $a, GMP $b) {}

/**
 * Jacobi symbol
 * @link http://www.php.net/manual/en/function.gmp-jacobi.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param p GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p> 
 * <p>
 * Should be odd and must be positive.
 * </p>
 * @return int A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_jacobi (GMP $a, GMP $p) {}

/**
 * Legendre symbol
 * @link http://www.php.net/manual/en/function.gmp-legendre.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param p GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p> 
 * <p>
 * Should be odd and must be positive.
 * </p>
 * @return int A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_legendre (GMP $a, GMP $p) {}

/**
 * Compare numbers
 * @link http://www.php.net/manual/en/function.gmp-cmp.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return int a positive value if a &gt; b, zero if
 * a = b and a negative value if a &lt;
 * b.
 */
function gmp_cmp (GMP $a, GMP $b) {}

/**
 * Sign of number
 * @link http://www.php.net/manual/en/function.gmp-sign.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return int 1 if a is positive,
 * -1 if a is negative,
 * and 0 if a is zero.
 */
function gmp_sign (GMP $a) {}

/**
 * Random number
 * @link http://www.php.net/manual/en/function.gmp-random.php
 * @param limiter int[optional] <p>
 * The limiter.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A random GMP number.
 */
function gmp_random ($limiter = null) {}

/**
 * Bitwise AND
 * @link http://www.php.net/manual/en/function.gmp-and.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number representing the bitwise AND comparison.
 */
function gmp_and (GMP $a, GMP $b) {}

/**
 * Bitwise OR
 * @link http://www.php.net/manual/en/function.gmp-or.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_or (GMP $a, GMP $b) {}

/**
 * Calculates one's complement
 * @link http://www.php.net/manual/en/function.gmp-com.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP the one's complement of a, as a GMP number.
 */
function gmp_com (GMP $a) {}

/**
 * Bitwise XOR
 * @link http://www.php.net/manual/en/function.gmp-xor.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_xor (GMP $a, GMP $b) {}

/**
 * Set bit
 * @link http://www.php.net/manual/en/function.gmp-setbit.php
 * @param a GMP <p>
 * The value to modify.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param index int <p>
 * The index of the bit to set. Index 0 represents the least significant bit.
 * </p>
 * @param bit_on bool[optional] <p>
 * True to set the bit (set it to 1/on); false to clear the bit (set it to 0/off).
 * </p>
 * @return void A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_setbit (GMP &$a, $index, $bit_on = null) {}

/**
 * Clear bit
 * @link http://www.php.net/manual/en/function.gmp-clrbit.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param index int <p>
 * The index of the bit to clear. Index 0 represents the least significant bit.
 * </p>
 * @return void A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_clrbit (GMP $a, $index) {}

/**
 * Tests if a bit is set
 * @link http://www.php.net/manual/en/function.gmp-testbit.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param index int <p>
 * The bit to test
 * </p>
 * @return bool true if the bit is set in resource $a, 
 * otherwise false.
 */
function gmp_testbit (GMP $a, $index) {}

/**
 * Scan for 0
 * @link http://www.php.net/manual/en/function.gmp-scan0.php
 * @param a GMP <p>
 * The number to scan.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param start int <p>
 * The starting bit.
 * </p>
 * @return int the index of the found bit, as an integer. The
 * index starts from 0.
 */
function gmp_scan0 (GMP $a, $start) {}

/**
 * Scan for 1
 * @link http://www.php.net/manual/en/function.gmp-scan1.php
 * @param a GMP <p>
 * The number to scan.
 * </p>
 * Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @param start int <p>
 * The starting bit.
 * </p>
 * @return int the index of the found bit, as an integer.
 * If no set bit is found, -1 is returned.
 */
function gmp_scan1 (GMP $a, $start) {}

/**
 * Population count
 * @link http://www.php.net/manual/en/function.gmp-popcount.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return int The population count of a, as an integer.
 */
function gmp_popcount (GMP $a) {}

/**
 * Hamming distance
 * @link http://www.php.net/manual/en/function.gmp-hamdist.php
 * @param a GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p> 
 * <p>
 * It should be positive.
 * </p>
 * @param b GMP Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p> 
 * <p>
 * It should be positive.
 * </p>
 * @return int A GMP number resource in PHP 5.5 and earlier, or a GMP object in PHP 5.6 and later.
 */
function gmp_hamdist (GMP $a, GMP $b) {}

/**
 * Find next prime number
 * @link http://www.php.net/manual/en/function.gmp-nextprime.php
 * @param a int Either a GMP number resource in PHP 5.5 and earlier, a GMP object in PHP 5.6 and later, or a numeric string provided that it is possible to convert the latter to a number.</p>
 * @return GMP Return the next prime number greater than a,
 * as a GMP number.
 */
function gmp_nextprime ($a) {}

define ('GMP_ROUND_ZERO', 0);
define ('GMP_ROUND_PLUSINF', 1);
define ('GMP_ROUND_MINUSINF', 2);

/**
 * The GMP library version
 * @link http://www.php.net/manual/en/gmp.constants.php
 */
define ('GMP_VERSION', "5.1.3");

// End of gmp v.
