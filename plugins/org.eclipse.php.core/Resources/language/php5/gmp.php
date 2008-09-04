<?php

// Start of gmp v.

/**
 * Create GMP number
 * @link http://php.net/manual/en/function.gmp-init.php
 * @param number mixed
 * @param base int[optional]
 * @return resource 
 */
function gmp_init ($number, $base = null) {}

/**
 * Convert GMP number to integer
 * @link http://php.net/manual/en/function.gmp-intval.php
 * @param gmpnumber resource
 * @return int 
 */
function gmp_intval ($gmpnumber) {}

/**
 * Convert GMP number to string
 * @link http://php.net/manual/en/function.gmp-strval.php
 * @param gmpnumber resource
 * @param base int[optional]
 * @return string 
 */
function gmp_strval ($gmpnumber, $base = null) {}

/**
 * Add numbers
 * @link http://php.net/manual/en/function.gmp-add.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_add ($a, $b) {}

/**
 * Subtract numbers
 * @link http://php.net/manual/en/function.gmp-sub.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_sub ($a, $b) {}

/**
 * Multiply numbers
 * @link http://php.net/manual/en/function.gmp-mul.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_mul ($a, $b) {}

/**
 * Divide numbers and get quotient and remainder
 * @link http://php.net/manual/en/function.gmp-div-qr.php
 * @param n resource
 * @param d resource
 * @param round int[optional]
 * @return array an array, with the first
 */
function gmp_div_qr ($n, $d, $round = null) {}

/**
 * Divide numbers
 * @link http://php.net/manual/en/function.gmp-div-q.php
 * @param a resource
 * @param b resource
 * @param round int[optional]
 * @return resource 
 */
function gmp_div_q ($a, $b, $round = null) {}

/**
 * Remainder of the division of numbers
 * @link http://php.net/manual/en/function.gmp-div-r.php
 * @param n resource
 * @param d resource
 * @param round int[optional]
 * @return resource 
 */
function gmp_div_r ($n, $d, $round = null) {}

/**
 * &Alias; <function>gmp_div_q</function>
 * @link http://php.net/manual/en/function.gmp-div.php
 * @param a
 * @param b
 * @param round[optional]
 */
function gmp_div ($a, $b, $round) {}

/**
 * Modulo operation
 * @link http://php.net/manual/en/function.gmp-mod.php
 * @param n resource
 * @param d resource
 * @return resource 
 */
function gmp_mod ($n, $d) {}

/**
 * Exact division of numbers
 * @link http://php.net/manual/en/function.gmp-divexact.php
 * @param n resource
 * @param d resource
 * @return resource 
 */
function gmp_divexact ($n, $d) {}

/**
 * Negate number
 * @link http://php.net/manual/en/function.gmp-neg.php
 * @param a resource
 * @return resource -a, as a GMP number.
 */
function gmp_neg ($a) {}

/**
 * Absolute value
 * @link http://php.net/manual/en/function.gmp-abs.php
 * @param a resource
 * @return resource the absolute value of a, as a GMP number.
 */
function gmp_abs ($a) {}

/**
 * Factorial
 * @link http://php.net/manual/en/function.gmp-fact.php
 * @param a int
 * @return resource 
 */
function gmp_fact ($a) {}

/**
 * Calculate square root
 * @link http://php.net/manual/en/function.gmp-sqrt.php
 * @param a resource
 * @return resource 
 */
function gmp_sqrt ($a) {}

/**
 * Square root with remainder
 * @link http://php.net/manual/en/function.gmp-sqrtrem.php
 * @param a resource
 * @return array array where first element is the integer square root of
 */
function gmp_sqrtrem ($a) {}

/**
 * Raise number into power
 * @link http://php.net/manual/en/function.gmp-pow.php
 * @param base resource
 * @param exp int
 * @return resource 
 */
function gmp_pow ($base, $exp) {}

/**
 * Raise number into power with modulo
 * @link http://php.net/manual/en/function.gmp-powm.php
 * @param base resource
 * @param exp resource
 * @param mod resource
 * @return resource 
 */
function gmp_powm ($base, $exp, $mod) {}

/**
 * Perfect square check
 * @link http://php.net/manual/en/function.gmp-perfect-square.php
 * @param a resource
 * @return bool true if a is a perfect square,
 */
function gmp_perfect_square ($a) {}

/**
 * Check if number is "probably prime"
 * @link http://php.net/manual/en/function.gmp-prob-prime.php
 * @param a resource
 * @param reps int[optional]
 * @return int 
 */
function gmp_prob_prime ($a, $reps = null) {}

/**
 * Calculate GCD
 * @link http://php.net/manual/en/function.gmp-gcd.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_gcd ($a, $b) {}

/**
 * Calculate GCD and multipliers
 * @link http://php.net/manual/en/function.gmp-gcdext.php
 * @param a resource
 * @param b resource
 * @return array 
 */
function gmp_gcdext ($a, $b) {}

/**
 * Inverse by modulo
 * @link http://php.net/manual/en/function.gmp-invert.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_invert ($a, $b) {}

/**
 * Jacobi symbol
 * @link http://php.net/manual/en/function.gmp-jacobi.php
 * @param a resource
 * @param p resource
 * @return int 
 */
function gmp_jacobi ($a, $p) {}

/**
 * Legendre symbol
 * @link http://php.net/manual/en/function.gmp-legendre.php
 * @param a resource
 * @param p resource
 * @return int 
 */
function gmp_legendre ($a, $p) {}

/**
 * Compare numbers
 * @link http://php.net/manual/en/function.gmp-cmp.php
 * @param a resource
 * @param b resource
 * @return int a positive value if a &gt; b, zero if
 */
function gmp_cmp ($a, $b) {}

/**
 * Sign of number
 * @link http://php.net/manual/en/function.gmp-sign.php
 * @param a resource
 * @return int 1 if a is positive,
 */
function gmp_sign ($a) {}

/**
 * Random number
 * @link http://php.net/manual/en/function.gmp-random.php
 * @param limiter int
 * @return resource 
 */
function gmp_random ($limiter) {}

/**
 * Bitwise AND
 * @link http://php.net/manual/en/function.gmp-and.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_and ($a, $b) {}

/**
 * Bitwise OR
 * @link http://php.net/manual/en/function.gmp-or.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_or ($a, $b) {}

/**
 * Calculates one's complement
 * @link http://php.net/manual/en/function.gmp-com.php
 * @param a resource
 * @return resource the one's complement of a, as a GMP number.
 */
function gmp_com ($a) {}

/**
 * Bitwise XOR
 * @link http://php.net/manual/en/function.gmp-xor.php
 * @param a resource
 * @param b resource
 * @return resource 
 */
function gmp_xor ($a, $b) {}

/**
 * Set bit
 * @link http://php.net/manual/en/function.gmp-setbit.php
 * @param a resource
 * @param index int
 * @param set_clear bool[optional]
 * @return void 
 */
function gmp_setbit (&$a, $index, $set_clear = null) {}

/**
 * Clear bit
 * @link http://php.net/manual/en/function.gmp-clrbit.php
 * @param a resource
 * @param index int
 * @return void 
 */
function gmp_clrbit (&$a, $index) {}

/**
 * Scan for 0
 * @link http://php.net/manual/en/function.gmp-scan0.php
 * @param a resource
 * @param start int
 * @return int the index of the found bit, as an integer. The
 */
function gmp_scan0 ($a, $start) {}

/**
 * Scan for 1
 * @link http://php.net/manual/en/function.gmp-scan1.php
 * @param a resource
 * @param start int
 * @return int the index of the found bit, as an integer.
 */
function gmp_scan1 ($a, $start) {}

/**
 * Population count
 * @link http://php.net/manual/en/function.gmp-popcount.php
 * @param a resource
 * @return int 
 */
function gmp_popcount ($a) {}

/**
 * Hamming distance
 * @link http://php.net/manual/en/function.gmp-hamdist.php
 * @param a resource
 * @param b resource
 * @return int 
 */
function gmp_hamdist ($a, $b) {}

/**
 * Find next prime number
 * @link http://php.net/manual/en/function.gmp-nextprime.php
 * @param a int
 * @return resource 
 */
function gmp_nextprime ($a) {}

define ('GMP_ROUND_ZERO', 0);
define ('GMP_ROUND_PLUSINF', 1);
define ('GMP_ROUND_MINUSINF', 2);

/**
 * The GMP library version
 * @link http://php.net/manual/en/gmp.constants.php
 */
define ('GMP_VERSION', "4.2.2");

// End of gmp v.
?>
