<?php

// Start of gmp v.

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
 * @return resource A GMP number resource.
 */
function gmp_init ($number, $base = null) {}

/**
 * Convert GMP number to integer
 * @link http://www.php.net/manual/en/function.gmp-intval.php
 * @param gmpnumber resource <p>
 * A GMP number.
 * </p>
 * @return int An integer value of gmpnumber.
 */
function gmp_intval ($gmpnumber) {}

/**
 * Convert GMP number to string
 * @link http://www.php.net/manual/en/function.gmp-strval.php
 * @param gmpnumber resource <p>
 * The GMP number that will be converted to a string.
 * </p>
 * &gmp.parameter;
 * @param base int[optional] <p>
 * The base of the returned number. The default base is 10. 
 * Allowed values for the base are from 2 to 62 and -2 to -36.
 * </p>
 * @return string The number, as a string.
 */
function gmp_strval ($gmpnumber, $base = null) {}

/**
 * Add numbers
 * @link http://www.php.net/manual/en/function.gmp-add.php
 * @param a resource <p>
 * A number that will be added.
 * </p>
 * &gmp.parameter;
 * @param b resource <p>
 * A number that will be added.
 * </p>
 * &gmp.parameter;
 * @return resource A GMP number representing the sum of the arguments.
 */
function gmp_add ($a, $b) {}

/**
 * Subtract numbers
 * @link http://www.php.net/manual/en/function.gmp-sub.php
 * @param a resource <p>
 * The number being subtracted from.
 * </p>
 * &gmp.parameter;
 * @param b resource <p>
 * The number subtracted from a.
 * </p>
 * &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_sub ($a, $b) {}

/**
 * Multiply numbers
 * @link http://www.php.net/manual/en/function.gmp-mul.php
 * @param a resource <p>
 * A number that will be multiplied by b.
 * </p>
 * &gmp.parameter;
 * @param b resource <p>
 * A number that will be multiplied by a.
 * </p>
 * &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_mul ($a, $b) {}

/**
 * Divide numbers and get quotient and remainder
 * @link http://www.php.net/manual/en/function.gmp-div-qr.php
 * @param n resource <p>
 * The number being divided.
 * </p>
 * &gmp.parameter;
 * @param d resource <p>
 * The number that n is being divided by.
 * </p>
 * &gmp.parameter;
 * @param round int[optional] <p>
 * See the gmp_div_q function for description
 * of the round argument.
 * </p>
 * @return array an array, with the first
 * element being [n/d] (the integer result of the
 * division) and the second being (n - [n/d] * d)
 * (the remainder of the division).
 */
function gmp_div_qr ($n, $d, $round = null) {}

/**
 * Divide numbers
 * @link http://www.php.net/manual/en/function.gmp-div-q.php
 * @param a resource <p>
 * The number being divided.
 * </p>
 * &gmp.parameter;
 * @param b resource <p>
 * The number that a is being divided by.
 * </p>
 * &gmp.parameter;
 * @param round int[optional] <p>
 * The result rounding is defined by the
 * round, which can have the following
 * values:
 * GMP_ROUND_ZERO: The result is truncated
 * towards 0.
 * @return resource A GMP number resource.
 */
function gmp_div_q ($a, $b, $round = null) {}

/**
 * Remainder of the division of numbers
 * @link http://www.php.net/manual/en/function.gmp-div-r.php
 * @param n resource <p>
 * The number being divided.
 * </p>
 * &gmp.parameter;
 * @param d resource <p>
 * The number that n is being divided by.
 * </p>
 * &gmp.parameter;
 * @param round int[optional] <p>
 * See the gmp_div_q function for description
 * of the round argument.
 * </p>
 * @return resource The remainder, as a GMP number.
 */
function gmp_div_r ($n, $d, $round = null) {}

/**
 * &Alias; <function>gmp_div_q</function>
 * @link http://www.php.net/manual/en/function.gmp-div.php
 * @param a
 * @param b
 * @param round[optional]
 */
function gmp_div ($a, $b, $round) {}

/**
 * Modulo operation
 * @link http://www.php.net/manual/en/function.gmp-mod.php
 * @param n resource &gmp.parameter;
 * @param d resource <p>
 * The modulo that is being evaluated.
 * </p>
 * &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_mod ($n, $d) {}

/**
 * Exact division of numbers
 * @link http://www.php.net/manual/en/function.gmp-divexact.php
 * @param n resource <p>
 * The number being divided.
 * </p>
 * &gmp.parameter;
 * @param d resource <p>
 * The number that a is being divided by.
 * </p>
 * &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_divexact ($n, $d) {}

/**
 * Negate number
 * @link http://www.php.net/manual/en/function.gmp-neg.php
 * @param a resource &gmp.parameter;
 * @return resource -a, as a GMP number.
 */
function gmp_neg ($a) {}

/**
 * Absolute value
 * @link http://www.php.net/manual/en/function.gmp-abs.php
 * @param a resource &gmp.parameter;
 * @return resource the absolute value of a, as a GMP number.
 */
function gmp_abs ($a) {}

/**
 * Factorial
 * @link http://www.php.net/manual/en/function.gmp-fact.php
 * @param a mixed <p>
 * The factorial number.
 * </p>
 * &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_fact ($a) {}

/**
 * Calculate square root
 * @link http://www.php.net/manual/en/function.gmp-sqrt.php
 * @param a resource &gmp.parameter;
 * @return resource The integer portion of the square root, as a GMP number.
 */
function gmp_sqrt ($a) {}

/**
 * Square root with remainder
 * @link http://www.php.net/manual/en/function.gmp-sqrtrem.php
 * @param a resource <p>
 * The number being square rooted.
 * </p>
 * &gmp.parameter;
 * @return array array where first element is the integer square root of
 * a and the second is the remainder
 * (i.e., the difference between a and the
 * first element squared).
 */
function gmp_sqrtrem ($a) {}

/**
 * Raise number into power
 * @link http://www.php.net/manual/en/function.gmp-pow.php
 * @param base resource <p>
 * The base number.
 * </p>
 * &gmp.parameter;
 * @param exp int <p>
 * The positive power to raise the base.
 * </p>
 * @return resource The new (raised) number, as a GMP number. The case of 
 * 0^0 yields 1.
 */
function gmp_pow ($base, $exp) {}

/**
 * Raise number into power with modulo
 * @link http://www.php.net/manual/en/function.gmp-powm.php
 * @param base resource <p>
 * The base number.
 * </p>
 * &gmp.parameter;
 * @param exp resource <p>
 * The positive power to raise the base.
 * </p>
 * &gmp.parameter;
 * @param mod resource <p>
 * The modulo.
 * </p>
 * &gmp.parameter;
 * @return resource The new (raised) number, as a GMP number.
 */
function gmp_powm ($base, $exp, $mod) {}

/**
 * Perfect square check
 * @link http://www.php.net/manual/en/function.gmp-perfect-square.php
 * @param a resource <p>
 * The number being checked as a perfect square.
 * </p>
 * &gmp.parameter;
 * @return bool true if a is a perfect square,
 * false otherwise.
 */
function gmp_perfect_square ($a) {}

/**
 * Check if number is "probably prime"
 * @link http://www.php.net/manual/en/function.gmp-prob-prime.php
 * @param a resource <p>
 * The number being checked as a prime.
 * </p>
 * &gmp.parameter;
 * @param reps int[optional] <p>
 * Reasonable values
 * of reps vary from 5 to 10 (default being
 * 10); a higher value lowers the probability for a non-prime to
 * pass as a "probable" prime.
 * </p>
 * &gmp.parameter;
 * @return int If this function returns 0, a is
 * definitely not prime. If it returns 1, then
 * a is "probably" prime. If it returns 2,
 * then a is surely prime.
 */
function gmp_prob_prime ($a, $reps = null) {}

/**
 * Calculate GCD
 * @link http://www.php.net/manual/en/function.gmp-gcd.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return resource A positive GMP number that divides into both
 * a and b.
 */
function gmp_gcd ($a, $b) {}

/**
 * Calculate GCD and multipliers
 * @link http://www.php.net/manual/en/function.gmp-gcdext.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return array An array of GMP numbers.
 */
function gmp_gcdext ($a, $b) {}

/**
 * Inverse by modulo
 * @link http://www.php.net/manual/en/function.gmp-invert.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return resource A GMP number on success or false if an inverse does not exist.
 */
function gmp_invert ($a, $b) {}

/**
 * Jacobi symbol
 * @link http://www.php.net/manual/en/function.gmp-jacobi.php
 * @param a resource &gmp.parameter;
 * @param p resource &gmp.parameter; 
 * <p>
 * Should be odd and must be positive.
 * </p>
 * @return int A GMP number resource.
 */
function gmp_jacobi ($a, $p) {}

/**
 * Legendre symbol
 * @link http://www.php.net/manual/en/function.gmp-legendre.php
 * @param a resource &gmp.parameter;
 * @param p resource &gmp.parameter; 
 * <p>
 * Should be odd and must be positive.
 * </p>
 * @return int A GMP number resource.
 */
function gmp_legendre ($a, $p) {}

/**
 * Compare numbers
 * @link http://www.php.net/manual/en/function.gmp-cmp.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return int a positive value if a &gt; b, zero if
 * a = b and a negative value if a &lt;
 * b.
 */
function gmp_cmp ($a, $b) {}

/**
 * Sign of number
 * @link http://www.php.net/manual/en/function.gmp-sign.php
 * @param a resource &gmp.parameter;
 * @return int 1 if a is positive,
 * -1 if a is negative,
 * and 0 if a is zero.
 */
function gmp_sign ($a) {}

/**
 * Random number
 * @link http://www.php.net/manual/en/function.gmp-random.php
 * @param limiter int[optional] <p>
 * The limiter.
 * </p>
 * &gmp.parameter;
 * @return resource A random GMP number.
 */
function gmp_random ($limiter = null) {}

/**
 * Bitwise AND
 * @link http://www.php.net/manual/en/function.gmp-and.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return resource A GMP number representing the bitwise AND comparison.
 */
function gmp_and ($a, $b) {}

/**
 * Bitwise OR
 * @link http://www.php.net/manual/en/function.gmp-or.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_or ($a, $b) {}

/**
 * Calculates one's complement
 * @link http://www.php.net/manual/en/function.gmp-com.php
 * @param a resource &gmp.parameter;
 * @return resource the one's complement of a, as a GMP number.
 */
function gmp_com ($a) {}

/**
 * Bitwise XOR
 * @link http://www.php.net/manual/en/function.gmp-xor.php
 * @param a resource &gmp.parameter;
 * @param b resource &gmp.parameter;
 * @return resource A GMP number resource.
 */
function gmp_xor ($a, $b) {}

/**
 * Set bit
 * @link http://www.php.net/manual/en/function.gmp-setbit.php
 * @param a resource <p>
 * The value to modify.
 * </p>
 * &gmp.parameter;
 * @param index int <p>
 * The index of the bit to set. Index 0 represents the least significant bit.
 * </p>
 * @param bit_on bool[optional] <p>
 * True to set the bit (set it to 1/on); false to clear the bit (set it to 0/off).
 * </p>
 * @return void A GMP number resource.
 */
function gmp_setbit (&$a, $index, $bit_on = null) {}

/**
 * Clear bit
 * @link http://www.php.net/manual/en/function.gmp-clrbit.php
 * @param a resource &gmp.parameter;
 * @param index int <p>
 * The index of the bit to clear. Index 0 represents the least significant bit.
 * </p>
 * @return void A GMP number resource.
 */
function gmp_clrbit ($a, $index) {}

/**
 * Scan for 0
 * @link http://www.php.net/manual/en/function.gmp-scan0.php
 * @param a resource <p>
 * The number to scan.
 * </p>
 * &gmp.parameter;
 * @param start int <p>
 * The starting bit.
 * </p>
 * @return int the index of the found bit, as an integer. The
 * index starts from 0.
 */
function gmp_scan0 ($a, $start) {}

/**
 * Scan for 1
 * @link http://www.php.net/manual/en/function.gmp-scan1.php
 * @param a resource <p>
 * The number to scan.
 * </p>
 * &gmp.parameter;
 * @param start int <p>
 * The starting bit.
 * </p>
 * @return int the index of the found bit, as an integer.
 * If no set bit is found, -1 is returned.
 */
function gmp_scan1 ($a, $start) {}

/**
 * Tests if a bit is set
 * @link http://www.php.net/manual/en/function.gmp-testbit.php
 * @param a resource &gmp.parameter;
 * @param index int <p>
 * The bit to test
 * </p>
 * @return bool true if the bit is set in resource $a, 
 * otherwise false.
 */
function gmp_testbit ($a, $index) {}

/**
 * Population count
 * @link http://www.php.net/manual/en/function.gmp-popcount.php
 * @param a resource &gmp.parameter;
 * @return int The population count of a, as an integer.
 */
function gmp_popcount ($a) {}

/**
 * Hamming distance
 * @link http://www.php.net/manual/en/function.gmp-hamdist.php
 * @param a resource &gmp.parameter; 
 * <p>
 * It should be positive.
 * </p>
 * @param b resource &gmp.parameter; 
 * <p>
 * It should be positive.
 * </p>
 * @return int A GMP number resource.
 */
function gmp_hamdist ($a, $b) {}

/**
 * Find next prime number
 * @link http://www.php.net/manual/en/function.gmp-nextprime.php
 * @param a int &gmp.parameter;
 * @return resource Return the next prime number greater than a,
 * as a GMP number.
 */
function gmp_nextprime ($a) {}

define ('GMP_ROUND_ZERO', 0);
define ('GMP_ROUND_PLUSINF', 1);
define ('GMP_ROUND_MINUSINF', 2);
define ('GMP_VERSION', "4.1 (VC7)");

// End of gmp v.
