<?php

// Start of bcmath v.8.2.6

/**
 * Add two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcadd.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return string The sum of the two operands, as a string.
 */
function bcadd (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Subtract one arbitrary precision number from another
 * @link http://www.php.net/manual/en/function.bcsub.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return string The result of the subtraction, as a string.
 */
function bcsub (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Multiply two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcmul.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return string Returns the result as a string.
 */
function bcmul (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Divide two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bcdiv.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return string Returns the result of the division as a string, or null if 
 * num2 is 0.
 */
function bcdiv (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcmod.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return string Returns the modulus as a string, or null if 
 * num2 is 0.
 */
function bcmod (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://www.php.net/manual/en/function.bcpowmod.php
 * @param string $num 
 * @param string $exponent 
 * @param string $modulus 
 * @param int|null $scale [optional] 
 * @return string Returns the result as a string, or false if modulus
 * is 0 or exponent is negative.
 */
function bcpowmod (string $num, string $exponent, string $modulus, ?int $scale = null): string {}

/**
 * Raise an arbitrary precision number to another
 * @link http://www.php.net/manual/en/function.bcpow.php
 * @param string $num 
 * @param string $exponent 
 * @param int|null $scale [optional] 
 * @return string Returns the result as a string.
 */
function bcpow (string $num, string $exponent, ?int $scale = null): string {}

/**
 * Get the square root of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcsqrt.php
 * @param string $num 
 * @param int|null $scale [optional] 
 * @return string Returns the square root as a well-formed BCMath numeric string.
 */
function bcsqrt (string $num, ?int $scale = null): string {}

/**
 * Compare two arbitrary precision numbers
 * @link http://www.php.net/manual/en/function.bccomp.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return int Returns 0 if the two operands are equal, 1 if the
 * num1 is larger than the 
 * num2, -1 otherwise.
 */
function bccomp (string $num1, string $num2, ?int $scale = null): int {}

/**
 * Set or get default scale parameter for all bc math functions
 * @link http://www.php.net/manual/en/function.bcscale.php
 * @param int $scale 
 * @return int Returns the old scale when used as setter. Otherwise the current scale is returned.
 */
function bcscale (int $scale): int {}

// End of bcmath v.8.2.6
