<?php

// Start of bcmath v.

/**
 * Add two arbitrary precision numbers
 * @link http://php.net/manual/en/function.bcadd.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string 
 */
function bcadd ($left_operand, $right_operand, $scale = null) {}

/**
 * Subtract one arbitrary precision number from another
 * @link http://php.net/manual/en/function.bcsub.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string 
 */
function bcsub ($left_operand, $right_operand, $scale = null) {}

/**
 * Multiply two arbitrary precision number
 * @link http://php.net/manual/en/function.bcmul.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string the result as a string.
 */
function bcmul ($left_operand, $right_operand, $scale = null) {}

/**
 * Divide two arbitrary precision numbers
 * @link http://php.net/manual/en/function.bcdiv.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string the result of the division as a string, or &null; if
 */
function bcdiv ($left_operand, $right_operand, $scale = null) {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://php.net/manual/en/function.bcmod.php
 * @param left_operand string
 * @param modulus string
 * @return string the modulus as a string, or &null; if
 */
function bcmod ($left_operand, $modulus) {}

/**
 * Raise an arbitrary precision number to another
 * @link http://php.net/manual/en/function.bcpow.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return string the result as a string.
 */
function bcpow ($left_operand, $right_operand, $scale = null) {}

/**
 * Get the square root of an arbitrary precision number
 * @link http://php.net/manual/en/function.bcsqrt.php
 * @param operand string
 * @param scale int[optional]
 * @return string the square root as a string, or &null; if
 */
function bcsqrt ($operand, $scale = null) {}

/**
 * Set default scale parameter for all bc math functions
 * @link http://php.net/manual/en/function.bcscale.php
 * @param scale int
 * @return bool 
 */
function bcscale ($scale) {}

/**
 * Compare two arbitrary precision numbers
 * @link http://php.net/manual/en/function.bccomp.php
 * @param left_operand string
 * @param right_operand string
 * @param scale int[optional]
 * @return int 0 if the two operands are equal, 1 if the
 */
function bccomp ($left_operand, $right_operand, $scale = null) {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://php.net/manual/en/function.bcpowmod.php
 * @param left_operand string
 * @param right_operand string
 * @param modulus string
 * @param scale int[optional]
 * @return string the result as a string, or &null; if modulus
 */
function bcpowmod ($left_operand, $right_operand, $modulus, $scale = null) {}

// End of bcmath v.
?>
