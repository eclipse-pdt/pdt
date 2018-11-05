<?php

// Start of bcmath v.7.3.0

/**
 * Add two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcadd.php
 * @param string $left_operand The left operand, as a string.
 * @param string $right_operand The right operand, as a string.
 * @param int $scale [optional] 
 * @return string The sum of the two operands, as a string.
 */
function bcadd (string $left_operand, string $right_operand, int $scale = null) {}

/**
 * Subtract one arbitrary precision number from another
 * @link http://www.php.net/manual/en/function.bcsub.php
 * @param string $left_operand The left operand, as a string.
 * @param string $right_operand The right operand, as a string.
 * @param int $scale [optional] 
 * @return string The result of the subtraction, as a string.
 */
function bcsub (string $left_operand, string $right_operand, int $scale = null) {}

/**
 * Multiply two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcmul.php
 * @param string $left_operand The left operand, as a string.
 * @param string $right_operand The right operand, as a string.
 * @param int $scale [optional] 
 * @return string the result as a string.
 */
function bcmul (string $left_operand, string $right_operand, int $scale = null) {}

/**
 * Divide two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcdiv.php
 * @param string $dividend The dividend, as a string.
 * @param string $divisor The divisor, as a string.
 * @param int $scale [optional] 
 * @return string the result of the division as a string, or null if 
 * divisor is 0.
 */
function bcdiv (string $dividend, string $divisor, int $scale = null) {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcmod.php
 * @param string $dividend The dividend, as a string.
 * @param string $divisor The divisor, as a string.
 * @param int $scale [optional] 
 * @return string the modulus as a string, or null if 
 * divisor is 0.
 */
function bcmod (string $dividend, string $divisor, int $scale = null) {}

/**
 * Raise an arbitrary precision number to another
 * @link http://www.php.net/manual/en/function.bcpow.php
 * @param string $base The base, as a string.
 * @param string $exponent The exponent, as a string. If the exponent is non-integral, it is truncated.
 * The valid range of the exponent is platform specific, but is at least
 * -2147483648 to 2147483647.
 * @param int $scale [optional] 
 * @return string the result as a string.
 */
function bcpow (string $base, string $exponent, int $scale = null) {}

/**
 * Get the square root of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcsqrt.php
 * @param string $operand The operand, as a string.
 * @param int $scale [optional] 
 * @return string the square root as a string, or null if 
 * operand is negative.
 */
function bcsqrt (string $operand, int $scale = null) {}

/**
 * Set default scale parameter for all bc math functions
 * @link http://www.php.net/manual/en/function.bcscale.php
 * @param int $scale [optional] The scale factor.
 * @return int the old scale.
 */
function bcscale (int $scale = null) {}

/**
 * Compare two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bccomp.php
 * @param string $left_operand The left operand, as a string.
 * @param string $right_operand The right operand, as a string.
 * @param int $scale [optional] The optional scale parameter is used to set the
 * number of digits after the decimal place which will be used in the
 * comparison.
 * @return int 0 if the two operands are equal, 1 if the
 * left_operand is larger than the 
 * right_operand, -1 otherwise.
 */
function bccomp (string $left_operand, string $right_operand, int $scale = null) {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://www.php.net/manual/en/function.bcpowmod.php
 * @param string $base The base, as an integral string (i.e. the scale has to be zero).
 * @param string $exponent The exponent, as an non-negative, integral string (i.e. the scale has to be zero).
 * @param string $modulus The modulus, as an integral string (i.e. the scale has to be zero).
 * @param int $scale [optional] 
 * @return string the result as a string, or null if modulus
 * is 0 or exponent is negative.
 */
function bcpowmod (string $base, string $exponent, string $modulus, int $scale = null) {}

// End of bcmath v.7.3.0
