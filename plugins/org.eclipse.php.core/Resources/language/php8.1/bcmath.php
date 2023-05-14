<?php

// Start of bcmath v.8.1.19

/**
 * Add two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcadd.php
 * @param string $num1 The left operand, as a string.
 * @param string $num2 The right operand, as a string.
 * @param mixed $scale [optional] 
 * @return string The sum of the two operands, as a string.
 */
function bcadd (string $num1, string $num2, $scale = null): string {}

/**
 * Subtract one arbitrary precision number from another
 * @link http://www.php.net/manual/en/function.bcsub.php
 * @param string $num1 The left operand, as a string.
 * @param string $num2 The right operand, as a string.
 * @param mixed $scale [optional] 
 * @return string The result of the subtraction, as a string.
 */
function bcsub (string $num1, string $num2, $scale = null): string {}

/**
 * Multiply two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcmul.php
 * @param string $num1 The left operand, as a string.
 * @param string $num2 The right operand, as a string.
 * @param mixed $scale [optional] 
 * @return string the result as a string.
 */
function bcmul (string $num1, string $num2, $scale = null): string {}

/**
 * Divide two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcdiv.php
 * @param string $num1 The dividend, as a string.
 * @param string $num2 The divisor, as a string.
 * @param mixed $scale [optional] 
 * @return string the result of the division as a string, or null if 
 * num2 is 0.
 */
function bcdiv (string $num1, string $num2, $scale = null): string {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcmod.php
 * @param string $num1 The dividend, as a string.
 * @param string $num2 The divisor, as a string.
 * @param mixed $scale [optional] 
 * @return string the modulus as a string, or null if 
 * num2 is 0.
 */
function bcmod (string $num1, string $num2, $scale = null): string {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://www.php.net/manual/en/function.bcpowmod.php
 * @param string $num The base, as an integral string (i.e. the scale has to be zero).
 * @param string $exponent The exponent, as an non-negative, integral string (i.e. the scale has to be zero).
 * @param string $modulus The modulus, as an integral string (i.e. the scale has to be zero).
 * @param mixed $scale [optional] 
 * @return string the result as a string, or false if modulus
 * is 0 or exponent is negative.
 */
function bcpowmod (string $num, string $exponent, string $modulus, $scale = null): string {}

/**
 * Raise an arbitrary precision number to another
 * @link http://www.php.net/manual/en/function.bcpow.php
 * @param string $num The base, as a string.
 * @param string $exponent The exponent, as a string. If the exponent is non-integral, it is truncated.
 * The valid range of the exponent is platform specific, but is at least
 * -2147483648 to 2147483647.
 * @param mixed $scale [optional] 
 * @return string the result as a string.
 */
function bcpow (string $num, string $exponent, $scale = null): string {}

/**
 * Get the square root of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcsqrt.php
 * @param string $num The operand, as a well-formed BCMath numeric string.
 * @param mixed $scale [optional] 
 * @return string the square root as a well-formed BCMath numeric string.
 */
function bcsqrt (string $num, $scale = null): string {}

/**
 * Compare two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bccomp.php
 * @param string $num1 The left operand, as a string.
 * @param string $num2 The right operand, as a string.
 * @param mixed $scale [optional] The optional scale parameter is used to set the
 * number of digits after the decimal place which will be used in the
 * comparison.
 * @return int 0 if the two operands are equal, 1 if the
 * num1 is larger than the 
 * num2, -1 otherwise.
 */
function bccomp (string $num1, string $num2, $scale = null): int {}

/**
 * Set or get default scale parameter for all bc math functions
 * @link http://www.php.net/manual/en/function.bcscale.php
 * @param int $scale The scale factor.
 * @return int the old scale when used as setter. Otherwise the current scale is returned.
 */
function bcscale (int $scale): int {}

// End of bcmath v.8.1.19
