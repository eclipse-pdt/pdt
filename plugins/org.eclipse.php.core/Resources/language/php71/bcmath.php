<?php

// Start of bcmath v.7.1.1

/**
 * Add two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcadd.php
 * @param string $left_operand <p>
 * The left operand, as a string.
 * </p>
 * @param string $right_operand <p>
 * The right operand, as a string.
 * </p>
 * @param int $scale [optional] 
 * @return string The sum of the two operands, as a string.
 */
function bcadd ($left_operand, $right_operand, $scale = null) {}

/**
 * Subtract one arbitrary precision number from another
 * @link http://www.php.net/manual/en/function.bcsub.php
 * @param string $left_operand <p>
 * The left operand, as a string.
 * </p>
 * @param string $right_operand <p>
 * The right operand, as a string.
 * </p>
 * @param int $scale [optional] 
 * @return string The result of the subtraction, as a string.
 */
function bcsub ($left_operand, $right_operand, $scale = null) {}

/**
 * Multiply two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcmul.php
 * @param string $left_operand <p>
 * The left operand, as a string.
 * </p>
 * @param string $right_operand <p>
 * The right operand, as a string.
 * </p>
 * @param int $scale [optional] 
 * @return string the result as a string.
 */
function bcmul ($left_operand, $right_operand, $scale = null) {}

/**
 * Divide two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcdiv.php
 * @param string $dividend <p>
 * The dividend, as a string.
 * </p>
 * @param string $divisor <p>
 * The divisor, as a string.
 * </p>
 * @param int $scale [optional] 
 * @return string the result of the division as a string, or null if 
 * divisor is 0.
 */
function bcdiv ($dividend, $divisor, $scale = null) {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcmod.php
 * @param string $dividend <p>
 * The dividend, as a string.
 * </p>
 * @param string $modulus <p>
 * The modulus, as a string.
 * </p>
 * @return string the modulus as a string, or null if 
 * modulus is 0.
 */
function bcmod ($dividend, $modulus) {}

/**
 * Raise an arbitrary precision number to another
 * @link http://www.php.net/manual/en/function.bcpow.php
 * @param string $base <p>
 * The base, as a string.
 * </p>
 * @param string $exponent <p>
 * The exponent, as a string. If the exponent is non-integral, it is truncated.
 * The valid range of the exponent is platform specific, but is at least
 * -2147483648 to 2147483647.
 * </p>
 * @param int $scale [optional] 
 * @return string the result as a string.
 */
function bcpow ($base, $exponent, $scale = null) {}

/**
 * Get the square root of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcsqrt.php
 * @param string $operand <p>
 * The operand, as a string.
 * </p>
 * @param int $scale [optional] 
 * @return string the square root as a string, or null if 
 * operand is negative.
 */
function bcsqrt ($operand, $scale = null) {}

/**
 * Set default scale parameter for all bc math functions
 * @link http://www.php.net/manual/en/function.bcscale.php
 * @param int $scale <p>
 * The scale factor.
 * </p>
 * @return bool true on success or false on failure
 */
function bcscale ($scale) {}

/**
 * Compare two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bccomp.php
 * @param string $left_operand <p>
 * The left operand, as a string.
 * </p>
 * @param string $right_operand <p>
 * The right operand, as a string.
 * </p>
 * @param int $scale [optional] <p>
 * The optional scale parameter is used to set the
 * number of digits after the decimal place which will be used in the
 * comparison. 
 * </p>
 * @return int 0 if the two operands are equal, 1 if the
 * left_operand is larger than the 
 * right_operand, -1 otherwise.
 */
function bccomp ($left_operand, $right_operand, $scale = null) {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://www.php.net/manual/en/function.bcpowmod.php
 * @param string $base <p>
 * The base, as an integral string (i.e. the scale has to be zero).
 * </p>
 * @param string $exponent <p>
 * The exponent, as an non-negative, integral string (i.e. the scale has to be zero).
 * </p>
 * @param string $modulus <p>
 * The modulus, as an integral string (i.e. the scale has to be zero).
 * </p>
 * @param int $scale [optional] 
 * @return string the result as a string, or null if modulus
 * is 0 or exponent is negative.
 */
function bcpowmod ($base, $exponent, $modulus, $scale = null) {}

// End of bcmath v.7.1.1
