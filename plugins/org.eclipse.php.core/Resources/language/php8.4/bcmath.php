<?php

// Start of bcmath v.8.4.7

namespace BcMath {

final readonly class Number implements \Stringable {

	public readonly string $value;

	public readonly int $scale;

	/**
	 * Creates a BcMath\Number object
	 * @link http://www.php.net/manual/en/bcmath-number.construct.php
	 * @param string|int $num An int or string value.
	 * If num is a int,
	 * the BcMath\Number::scale is always set to 0.
	 * If num is a string, it must be a valid number,
	 * and the BcMath\Number::scale is automatically set by parsing the string.
	 * @return string|int 
	 */
	public function __construct (string|int $num): string|int {}

	/**
	 * Adds an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.add.php
	 * @param \BcMath\Number|string|int $num The value to add.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the result of addition as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the greater BcMath\Number::scale of the two numbers used for addition is used.
	 * That is, if the BcMath\Number::scales of two values are 2
	 * and 5 respectively, the BcMath\Number::scale of the result
	 * will be 5.
	 */
	public function add (\BcMath\Number|string|int $num, ?int $scale = null): \BcMath\Number {}

	/**
	 * Subtracts an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.sub.php
	 * @param \BcMath\Number|string|int $num The value to subtract.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the result of subtraction as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the greater BcMath\Number::scale of the two numbers used for subtraction is used.
	 * >
	 * That is, if the BcMath\Number::scales of two values are 2
	 * and 5 respectively, the BcMath\Number::scale of the result
	 * will be 5.
	 */
	public function sub (\BcMath\Number|string|int $num, ?int $scale = null): \BcMath\Number {}

	/**
	 * Multiplies an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.mul.php
	 * @param \BcMath\Number|string|int $num The multiplier.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the result of multiplication as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the sum of the BcMath\Number::scales of the two values used for multiplication is used.
	 * That is, if the BcMath\Number::scales of two values are 2
	 * and 5 respectively, the BcMath\Number::scale of the result
	 * will be 7.
	 */
	public function mul (\BcMath\Number|string|int $num, ?int $scale = null): \BcMath\Number {}

	/**
	 * Divides by an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.div.php
	 * @param \BcMath\Number|string|int $num The divisor.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the result of division as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the BcMath\Number::scale of the dividend is used. However, in cases such
	 * as indivisible division, the BcMath\Number::scale of the result is expanded.
	 * Expansion is done only as needed, up to a maximum of +10.
	 * That is, if the BcMath\Number::scale of the dividend is 5,
	 * the BcMath\Number::scale of the result is between 5 and
	 * 15.
	 * Even in indivisible calculations, the BcMath\Number::scale will not always be
	 * +10.
	 * A 0 at the end of the result is considered not to need expansion, so the
	 * BcMath\Number::scale is reduced by that amount.
	 * The BcMath\Number::scale will never be less than the
	 * BcMath\Number::scale before expansion.
	 * See also the code example.
	 */
	public function div (\BcMath\Number|string|int $num, ?int $scale = null): \BcMath\Number {}

	/**
	 * Gets the modulus of an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.mod.php
	 * @param \BcMath\Number|string|int $num The divisor.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the modulus as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the greater BcMath\Number::scale of the two numbers used for modulus operation is used.
	 * >
	 * That is, if the BcMath\Number::scales of two values are 2
	 * and 5 respectively, the BcMath\Number::scale of the result
	 * will be 5.
	 */
	public function mod (\BcMath\Number|string|int $num, ?int $scale = null): \BcMath\Number {}

	/**
	 * Gets the quotient and modulus of an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.divmod.php
	 * @param \BcMath\Number|string|int $num The divisor.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return array Returns an indexed array where the first element is the quotient as a new
	 * BcMath\Number object and the second element is the remainder as a new
	 * BcMath\Number object.
	 * The quotient is always an integer value, so BcMath\Number::scale of the quotient will
	 * always be 0, regardless of whether explicitly specify scale.
	 * If scale is explicitly specified, BcMath\Number::scale of the
	 * remainder will be the specified value.
	 * When the BcMath\Number::scale of the result's remainder object is automatically set,
	 * the greater BcMath\Number::scale of the two numbers used for modulus operation is used.
	 * That is, if the BcMath\Number::scales of two values are 2
	 * and 5 respectively, the BcMath\Number::scale of the remainder
	 * will be 5.
	 */
	public function divmod (\BcMath\Number|string|int $num, ?int $scale = null): array {}

	/**
	 * Raises an arbitrary precision number, reduced by a specified modulus
	 * @link http://www.php.net/manual/en/bcmath-number.powmod.php
	 * @param \BcMath\Number|string|int $exponent The exponent, as an non-negative and integral (i.e. the scale has to be zero).
	 * @param \BcMath\Number|string|int $modulus The modulus, as an integral (i.e. the scale has to be zero).
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the result as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the BcMath\Number::scale of the result object will always be 0.
	 */
	public function powmod (\BcMath\Number|string|int $exponent, \BcMath\Number|string|int $modulus, ?int $scale = null): \BcMath\Number {}

	/**
	 * Raises an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.pow.php
	 * @param \BcMath\Number|string|int $exponent The exponent. Must be a value with no fractional part.
	 * The valid range of the exponent is platform specific,
	 * but it is at least -2147483648 to 2147483647.
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number When the BcMath\Number::scale of the result object is automatically set,
	 * depending on the value of exponent, the BcMath\Number::scale
	 * of result will be as follows:
	 * <table>
	 * <tr valign="top">
	 * <td>exponent</td>
	 * <td>BcMath\Number::scale of result</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>positive</td>
	 * <td)<BcMath\Number::scale of power base) &#42; (exponent's value)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>0</td>
	 * <td>0</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>negative</td>
	 * <td>Between (BcMath\Number::scale of power base) and (BcMath\Number::scale
	 * of power base + 10)</td>
	 * </tr>
	 * </table>
	 */
	public function pow (\BcMath\Number|string|int $exponent, ?int $scale = null): \BcMath\Number {}

	/**
	 * Gets the square root of an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.sqrt.php
	 * @param int|null $scale [optional] BcMath\Number::scale explicitly specified for calculation results.
	 * If null, the BcMath\Number::scale of the calculation result will be set automatically.
	 * @return \BcMath\Number Returns the square root as a new BcMath\Number object.
	 * When the BcMath\Number::scale of the result object is automatically set,
	 * the BcMath\Number::scale of $this is used. However, in cases such
	 * as indivisible division, the BcMath\Number::scale of the result is expanded.
	 * Expansion is done only as needed, up to a maximum of +10.
	 * This behavior is the same as BcMath\Number::div, so please see that for details.
	 * That is, if the BcMath\Number::scale of $this is 5,
	 * the BcMath\Number::scale of the result is between 5 and
	 * 15.
	 */
	public function sqrt (?int $scale = null): \BcMath\Number {}

	/**
	 * Rounds down an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.floor.php
	 * @return \BcMath\Number Returns the result as a new BcMath\Number object.
	 * The BcMath\Number::scale of the result is always 0.
	 */
	public function floor (): \BcMath\Number {}

	/**
	 * Rounds up an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.ceil.php
	 * @return \BcMath\Number Returns the result as a new BcMath\Number object.
	 * The BcMath\Number::scale of the result is always 0.
	 */
	public function ceil (): \BcMath\Number {}

	/**
	 * Rounds an arbitrary precision number
	 * @link http://www.php.net/manual/en/bcmath-number.round.php
	 * @param int $precision [optional] The optional number of decimal digits to round to.
	 * <p>If the precision is positive, num is
	 * rounded to precision significant digits after the decimal point.</p>
	 * <p>If the precision is negative, num is
	 * rounded to precision significant digits before the decimal point,
	 * i.e. to the nearest multiple of pow(10, -$precision), e.g. for a
	 * precision of -1 num is rounded to tens,
	 * for a precision of -2 to hundreds, etc.</p>
	 * @param \RoundingMode $mode [optional] Specifies the rounding mode. For more information about modes, see RoundingMode.
	 * @return \BcMath\Number Returns the result as a new BcMath\Number object.
	 */
	public function round (int $precision = null, \RoundingMode $mode = \RoundingMode::HalfAwayFromZero): \BcMath\Number {}

	/**
	 * Compares two arbitrary precision numbers
	 * @link http://www.php.net/manual/en/bcmath-number.compare.php
	 * @param \BcMath\Number|string|int $num The value to be compared to.
	 * @param int|null $scale [optional] Specify the scale to use for comparison.
	 * If null, all digits are used in the comparison.
	 * @return int Returns 0 if the two numbers are equal,
	 * 1 if $this is greater than num,
	 * -1 otherwise.
	 */
	public function compare (\BcMath\Number|string|int $num, ?int $scale = null): int {}

	/**
	 * Converts BcMath\Number to string
	 * @link http://www.php.net/manual/en/bcmath-number.tostring.php
	 * @return string Returns BcMath\Number::value as a string.
	 */
	public function __toString (): string {}

	/**
	 * Serializes a BcMath\Number object
	 * @link http://www.php.net/manual/en/bcmath-number.serialize.php
	 * @return array 
	 */
	public function __serialize (): array {}

	/**
	 * Deserializes a data parameter into a BcMath\Number object
	 * @link http://www.php.net/manual/en/bcmath-number.unserialize.php
	 * @param array $data The serialized data parameter as an associative array
	 * @return void 
	 */
	public function __unserialize (array $data): void {}

}


}


namespace {

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
 * @return string Returns the result of the division as a string.
 */
function bcdiv (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Get modulus of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcmod.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return string Returns the modulus as a string.
 */
function bcmod (string $num1, string $num2, ?int $scale = null): string {}

/**
 * Get the quotient and modulus of an arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcdivmod.php
 * @param string $num1 
 * @param string $num2 
 * @param int|null $scale [optional] 
 * @return array Returns an indexed array where the first element is the quotient as a string
 * and the second element is the remainder as a string.
 */
function bcdivmod (string $num1, string $num2, ?int $scale = null): array {}

/**
 * Raise an arbitrary precision number to another, reduced by a specified modulus
 * @link http://www.php.net/manual/en/function.bcpowmod.php
 * @param string $num 
 * @param string $exponent 
 * @param string $modulus 
 * @param int|null $scale [optional] 
 * @return string Returns the result as a string.
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
 * @return int Returns 0 if both operands are equal,
 * 1 if num1 is greater than
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

/**
 * Round down arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcfloor.php
 * @param string $num The value to round.
 * @return string Returns a numeric string representing num rounded down to the nearest integer.
 */
function bcfloor (string $num): string {}

/**
 * Round up arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcceil.php
 * @param string $num The value to round.
 * @return string Returns a numeric string representing num rounded up to the nearest integer.
 */
function bcceil (string $num): string {}

/**
 * Round arbitrary precision number
 * @link http://www.php.net/manual/en/function.bcround.php
 * @param string $num The value to round.
 * @param int $precision [optional] The optional number of decimal digits to round to.
 * <p>If the precision is positive, num is
 * rounded to precision significant digits after the decimal point.</p>
 * <p>If the precision is negative, num is
 * rounded to precision significant digits before the decimal point,
 * i.e. to the nearest multiple of pow(10, -$precision), e.g. for a
 * precision of -1 num is rounded to tens,
 * for a precision of -2 to hundreds, etc.</p>
 * @param RoundingMode $mode [optional] Specifies the rounding mode. For more information about modes, see RoundingMode.
 * @return string Returns a numeric string representing num rounded to the given precision.
 */
function bcround (string $num, int $precision = null, RoundingMode $mode = \RoundingMode::HalfAwayFromZero): string {}


}

// End of bcmath v.8.4.7
