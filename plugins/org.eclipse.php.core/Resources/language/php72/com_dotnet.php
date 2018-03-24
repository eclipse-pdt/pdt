<?php

// Start of com_dotnet v.7.2.3

final class COMPersistHelper  {

	public function __construct () {}

	public function GetCurFileName () {}

	public function SaveToFile () {}

	public function LoadFromFile () {}

	public function GetMaxStreamSize () {}

	public function InitNew () {}

	public function LoadFromStream () {}

	public function SaveToStream () {}

}

final class com_exception extends Exception implements Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param $message [optional]
	 * @param $code [optional]
	 * @param $previous [optional]
	 */
	public function __construct ($message = null, $code = null, $previous = null) {}

	public function __wakeup () {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

final class com_safearray_proxy  {
}

/**
 * @link http://www.php.net/manual/en/class.variant.php
 */
class variant  {
}

/**
 * @link http://www.php.net/manual/en/class.com.php
 */
class com extends variant  {
}

/**
 * @link http://www.php.net/manual/en/class.dotnet.php
 */
class dotnet extends variant  {
}

/**
 * Assigns a new value for a variant object
 * @link http://www.php.net/manual/en/function.variant-set.php
 * @param variant $variant The variant.
 * @param mixed $value 
 * @return void 
 */
function variant_set (variant $variant, $value) {}

/**
 * "Adds" two variant values together and returns the result
 * @link http://www.php.net/manual/en/function.variant-add.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed the result.
 */
function variant_add ($left, $right) {}

/**
 * Concatenates two variant values together and returns the result
 * @link http://www.php.net/manual/en/function.variant-cat.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed the result of the concatenation.
 */
function variant_cat ($left, $right) {}

/**
 * Subtracts the value of the right variant from the left variant value
 * @link http://www.php.net/manual/en/function.variant-sub.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant Subtraction Rules
 * <table>
 * <tr valign="top">
 * <td>If</td>
 * <td>Then</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are of the string type</td>
 * <td>Subtraction</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is a string type and the other a
 * character</td>
 * <td>Subtraction</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is numeric and the other is a string</td>
 * <td>Subtraction.</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are numeric</td>
 * <td>Subtraction</td>
 * </tr>
 * <tr valign="top">
 * <td>Either expression is NULL</td>
 * <td>NULL is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are empty</td>
 * <td>Empty string is returned</td>
 * </tr>
 * </table>
 */
function variant_sub ($left, $right) {}

/**
 * Multiplies the values of the two variants
 * @link http://www.php.net/manual/en/function.variant-mul.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant Multiplication Rules
 * <table>
 * <tr valign="top">
 * <td>If</td>
 * <td>Then</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are of the string, date, character, boolean type</td>
 * <td>Multiplication</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is a string type and the other a
 * character</td>
 * <td>Multiplication</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is numeric and the other is a string</td>
 * <td>Multiplication</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are numeric</td>
 * <td>Multiplication</td>
 * </tr>
 * <tr valign="top">
 * <td>Either expression is NULL</td>
 * <td>NULL is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are empty</td>
 * <td>Empty string is returned</td>
 * </tr>
 * </table>
 */
function variant_mul ($left, $right) {}

/**
 * Performs a bitwise AND operation between two variants
 * @link http://www.php.net/manual/en/function.variant-and.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant AND Rules
 * <table>
 * <tr valign="top">
 * <td>If left is</td>
 * <td>If right is</td>
 * <td>then the result is</td>
 * </tr>
 * <tr valign="top"><td>true</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>true</td><td>false</td><td>false</td></tr>
 * <tr valign="top"><td>true</td><td>null</td><td>null</td></tr>
 * <tr valign="top"><td>false</td><td>true</td><td>false</td></tr>
 * <tr valign="top"><td>false</td><td>false</td><td>false</td></tr>
 * <tr valign="top"><td>false</td><td>null</td><td>false</td></tr>
 * <tr valign="top"><td>null</td><td>true</td><td>null</td></tr>
 * <tr valign="top"><td>null</td><td>false</td><td>false</td></tr>
 * <tr valign="top"><td>null</td><td>null</td><td>null</td></tr>
 * </table>
 */
function variant_and ($left, $right) {}

/**
 * Returns the result from dividing two variants
 * @link http://www.php.net/manual/en/function.variant-div.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant Division Rules
 * <table>
 * <tr valign="top">
 * <td>If</td>
 * <td>Then</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are of the string, date, character, boolean type</td>
 * <td>Double is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is a string type and the other a
 * character</td>
 * <td>Division and a double is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is numeric and the other is a string</td>
 * <td>Division and a double is returned.</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are numeric</td>
 * <td>Division and a double is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>Either expression is NULL</td>
 * <td>NULL is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>right is empty and
 * left is anything but empty</td>
 * <td>A com_exception with code DISP_E_DIVBYZERO
 * is thrown</td>
 * </tr>
 * <tr valign="top">
 * <td>left is empty and
 * right is anything but empty.</td>
 * <td>0 as type double is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are empty</td>
 * <td>A com_exception with code DISP_E_OVERFLOW
 * is thrown</td>
 * </tr>
 * </table>
 */
function variant_div ($left, $right) {}

/**
 * Performs a bitwise equivalence on two variants
 * @link http://www.php.net/manual/en/function.variant-eqv.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed If each bit in left is equal to the corresponding
 * bit in right then true is returned, otherwise
 * false is returned.
 */
function variant_eqv ($left, $right) {}

/**
 * Converts variants to integers and then returns the result from dividing them
 * @link http://www.php.net/manual/en/function.variant-idiv.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant Integer Division Rules
 * <table>
 * <tr valign="top">
 * <td>If</td>
 * <td>Then</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are of the string, date, character, boolean type</td>
 * <td>Division and integer is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is a string type and the other a
 * character</td>
 * <td>Division</td>
 * </tr>
 * <tr valign="top">
 * <td>One expression is numeric and the other is a string</td>
 * <td>Division</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are numeric</td>
 * <td>Division</td>
 * </tr>
 * <tr valign="top">
 * <td>Either expression is NULL</td>
 * <td>NULL is returned</td>
 * </tr>
 * <tr valign="top">
 * <td>Both expressions are empty</td>
 * <td>A com_exception with code DISP_E_DIVBYZERO
 * is thrown</td>
 * </tr>
 * </table>
 */
function variant_idiv ($left, $right) {}

/**
 * Performs a bitwise implication on two variants
 * @link http://www.php.net/manual/en/function.variant-imp.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant Implication Table
 * <table>
 * <tr valign="top">
 * <td>If left is</td>
 * <td>If right is</td>
 * <td>then the result is</td>
 * </tr>
 * <tr valign="top"><td>true</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>true</td><td>false</td><td>true</td></tr>
 * <tr valign="top"><td>true</td><td>null</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>false</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>null</td><td>true</td></tr>
 * <tr valign="top"><td>null</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>null</td><td>false</td><td>null</td></tr>
 * <tr valign="top"><td>null</td><td>null</td><td>null</td></tr>
 * </table>
 */
function variant_imp ($left, $right) {}

/**
 * Divides two variants and returns only the remainder
 * @link http://www.php.net/manual/en/function.variant-mod.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed the remainder of the division.
 */
function variant_mod ($left, $right) {}

/**
 * Performs a logical disjunction on two variants
 * @link http://www.php.net/manual/en/function.variant-or.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant OR Rules
 * <table>
 * <tr valign="top">
 * <td>If left is</td>
 * <td>If right is</td>
 * <td>then the result is</td>
 * </tr>
 * <tr valign="top"><td>true</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>true</td><td>false</td><td>true</td></tr>
 * <tr valign="top"><td>true</td><td>null</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>false</td><td>false</td></tr>
 * <tr valign="top"><td>false</td><td>null</td><td>null</td></tr>
 * <tr valign="top"><td>null</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>null</td><td>false</td><td>null</td></tr>
 * <tr valign="top"><td>null</td><td>null</td><td>null</td></tr>
 * </table>
 */
function variant_or ($left, $right) {}

/**
 * Returns the result of performing the power function with two variants
 * @link http://www.php.net/manual/en/function.variant-pow.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed the result of left to the power of
 * right.
 */
function variant_pow ($left, $right) {}

/**
 * Performs a logical exclusion on two variants
 * @link http://www.php.net/manual/en/function.variant-xor.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @return mixed <table>
 * Variant XOR Rules
 * <table>
 * <tr valign="top">
 * <td>If left is</td>
 * <td>If right is</td>
 * <td>then the result is</td>
 * </tr>
 * <tr valign="top"><td>true</td><td>true</td><td>false</td></tr>
 * <tr valign="top"><td>true</td><td>false</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>true</td><td>true</td></tr>
 * <tr valign="top"><td>false</td><td>false</td><td>false</td></tr>
 * <tr valign="top"><td>null</td><td>null</td><td>null</td></tr>
 * </table>
 */
function variant_xor ($left, $right) {}

/**
 * Returns the absolute value of a variant
 * @link http://www.php.net/manual/en/function.variant-abs.php
 * @param mixed $val The variant.
 * @return mixed the absolute value of val.
 */
function variant_abs ($val) {}

/**
 * Returns the integer portion of a variant
 * @link http://www.php.net/manual/en/function.variant-fix.php
 * @param mixed $variant The variant.
 * @return mixed If variant is negative, then the first negative
 * integer greater than or equal to the variant is returned, otherwise
 * returns the integer portion of the value of
 * variant.
 */
function variant_fix ($variant) {}

/**
 * Returns the integer portion of a variant
 * @link http://www.php.net/manual/en/function.variant-int.php
 * @param mixed $variant The variant.
 * @return mixed If variant is negative, then the first negative
 * integer greater than or equal to the variant is returned, otherwise
 * returns the integer portion of the value of
 * variant.
 */
function variant_int ($variant) {}

/**
 * Performs logical negation on a variant
 * @link http://www.php.net/manual/en/function.variant-neg.php
 * @param mixed $variant The variant.
 * @return mixed the result of the logical negation.
 */
function variant_neg ($variant) {}

/**
 * Performs bitwise not negation on a variant
 * @link http://www.php.net/manual/en/function.variant-not.php
 * @param mixed $variant The variant.
 * @return mixed the bitwise not negation. If variant is
 * null, the result will also be null.
 */
function variant_not ($variant) {}

/**
 * Rounds a variant to the specified number of decimal places
 * @link http://www.php.net/manual/en/function.variant-round.php
 * @param mixed $variant The variant.
 * @param int $decimals Number of decimal places.
 * @return mixed the rounded value.
 */
function variant_round ($variant, int $decimals) {}

/**
 * Compares two variants
 * @link http://www.php.net/manual/en/function.variant-cmp.php
 * @param mixed $left The left operand.
 * @param mixed $right The right operand.
 * @param int $lcid [optional] A valid Locale Identifier to use when comparing strings (this affects
 * string collation).
 * @param int $flags [optional] flags can be one or more of the following values
 * OR'd together, and affects string comparisons:
 * <table>
 * Variant Comparision Flags
 * <table>
 * <tr valign="top">
 * <td>value</td>
 * <td>meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>NORM_IGNORECASE</td>
 * <td>Compare case insensitively</td>
 * </tr>
 * <tr valign="top">
 * <td>NORM_IGNORENONSPACE</td>
 * <td>Ignore nonspacing characters</td>
 * </tr>
 * <tr valign="top">
 * <td>NORM_IGNORESYMBOLS</td>
 * <td>Ignore symbols</td>
 * </tr>
 * <tr valign="top">
 * <td>NORM_IGNOREWIDTH</td>
 * <td>Ignore string width</td>
 * </tr>
 * <tr valign="top">
 * <td>NORM_IGNOREKANATYPE</td>
 * <td>Ignore Kana type</td>
 * </tr>
 * <tr valign="top">
 * <td>NORM_IGNOREKASHIDA</td>
 * <td>Ignore Arabic kashida characters</td>
 * </tr>
 * </table>
 * @return int one of the following:
 * <table>
 * Variant Comparision Results
 * <table>
 * <tr valign="top">
 * <td>value</td>
 * <td>meaning</td>
 * </tr>
 * <tr valign="top">
 * <td>VARCMP_LT</td>
 * <td>left is less than
 * right
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>VARCMP_EQ</td>
 * <td>left is equal to
 * right
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>VARCMP_GT</td>
 * <td>left is greater than
 * right
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>VARCMP_NULL</td>
 * <td>Either left,
 * right or both are null
 * </td>
 * </tr>
 * </table>
 */
function variant_cmp ($left, $right, int $lcid = null, int $flags = null) {}

/**
 * Converts a variant date/time value to Unix timestamp
 * @link http://www.php.net/manual/en/function.variant-date-to-timestamp.php
 * @param variant $variant The variant.
 * @return int a unix timestamp.
 */
function variant_date_to_timestamp (variant $variant) {}

/**
 * Returns a variant date representation of a Unix timestamp
 * @link http://www.php.net/manual/en/function.variant-date-from-timestamp.php
 * @param int $timestamp A unix timestamp.
 * @return variant a VT_DATE variant.
 */
function variant_date_from_timestamp (int $timestamp) {}

/**
 * Returns the type of a variant object
 * @link http://www.php.net/manual/en/function.variant-get-type.php
 * @param variant $variant The variant object.
 * @return int This function returns an integer value that indicates the type of
 * variant, which can be an instance of
 * , or
 * classes. The return value can be compared
 * to one of the VT_XXX constants.
 * <p>
 * The return value for COM and DOTNET objects will usually be
 * VT_DISPATCH; the only reason this function works for
 * those classes is because COM and DOTNET are descendants of VARIANT.
 * </p>
 * <p>
 * In PHP versions prior to 5, you could obtain this information from
 * instances of the VARIANT class ONLY, by reading a fake
 * type property. See the class for more information on
 * this.
 * </p>
 */
function variant_get_type (variant $variant) {}

/**
 * Convert a variant into another type "in-place"
 * @link http://www.php.net/manual/en/function.variant-set-type.php
 * @param variant $variant The variant.
 * @param int $type 
 * @return void 
 */
function variant_set_type (variant $variant, int $type) {}

/**
 * Convert a variant into a new variant object of another type
 * @link http://www.php.net/manual/en/function.variant-cast.php
 * @param variant $variant The variant.
 * @param int $type type should be one of the
 * VT_XXX constants.
 * @return variant a variant of given type.
 */
function variant_cast (variant $variant, int $type) {}

/**
 * Generate a globally unique identifier (GUID)
 * @link http://www.php.net/manual/en/function.com-create-guid.php
 * @return string the GUID as a string.
 */
function com_create_guid () {}

/**
 * Connect events from a COM object to a PHP object
 * @link http://www.php.net/manual/en/function.com-event-sink.php
 * @param variant $comobject 
 * @param object $sinkobject sinkobject should be an instance of a class with
 * methods named after those of the desired dispinterface; you may use
 * com_print_typeinfo to help generate a template class
 * for this purpose.
 * @param mixed $sinkinterface [optional] PHP will attempt to use the default dispinterface type specified by
 * the typelibrary associated with comobject, but
 * you may override this choice by setting
 * sinkinterface to the name of the dispinterface
 * that you want to use.
 * @return bool true on success or false on failure
 */
function com_event_sink (variant $comobject, $sinkobject, $sinkinterface = null) {}

/**
 * Print out a PHP class definition for a dispatchable interface
 * @link http://www.php.net/manual/en/function.com-print-typeinfo.php
 * @param object $comobject comobject should be either an instance of a COM
 * object, or be the name of a typelibrary (which will be resolved according
 * to the rules set out in com_load_typelib).
 * @param string $dispinterface [optional] The name of an IDispatch descendant interface that you want to display.
 * @param bool $wantsink [optional] If set to true, the corresponding sink interface will be displayed
 * instead.
 * @return bool true on success or false on failure
 */
function com_print_typeinfo ($comobject, string $dispinterface = null, bool $wantsink = null) {}

/**
 * Process COM messages, sleeping for up to timeoutms milliseconds
 * @link http://www.php.net/manual/en/function.com-message-pump.php
 * @param int $timeoutms [optional] <p>
 * The timeout, in milliseconds.
 * </p>
 * <p>
 * If you do not specify a value for timeoutms,
 * then 0 will be assumed. A 0 value means that no waiting will be
 * performed; if there are messages pending they will be dispatched as
 * before; if there are no messages pending, the function will return
 * false immediately without sleeping.
 * </p>
 * @return bool If a message or messages arrives before the timeout, they will be
 * dispatched, and the function will return true. If the timeout occurs and
 * no messages were processed, the return value will be false.
 */
function com_message_pump (int $timeoutms = null) {}

/**
 * Loads a Typelib
 * @link http://www.php.net/manual/en/function.com-load-typelib.php
 * @param string $typelib_name <p>
 * typelib_name can be one of the following:
 * <p>
 * <br>
 * <p>
 * The filename of a .tlb file or the executable module
 * that contains the type library.
 * </p>
 * <br>
 * <p>
 * The type library GUID, followed by its version number, for example
 * {00000200-0000-0010-8000-00AA006D2EA4},2,0.
 * </p>
 * <br>
 * <p>
 * The type library name, e.g. Microsoft OLE DB ActiveX Data
 * Objects 1.0 Library.
 * </p>
 * </p>
 * PHP will attempt to resolve the type library in this order, as the
 * process gets more and more expensive as you progress down the list;
 * searching for the type library by name is handled by physically
 * enumerating the registry until we find a match.
 * </p>
 * @param bool $case_insensitive [optional] The case_insensitive behaves in the same way as
 * the parameter with the same name in the define
 * function.
 * @return bool true on success or false on failure
 */
function com_load_typelib (string $typelib_name, bool $case_insensitive = null) {}

/**
 * Returns a handle to an already running instance of a COM object
 * @link http://www.php.net/manual/en/function.com-get-active-object.php
 * @param string $progid progid must be either the ProgID or CLSID for
 * the object that you want to access (for example
 * Word.Application).
 * @param int $code_page [optional] Acts in precisely the same way that it does for the class.
 * @return variant If the requested object is running, it will be returned to your script
 * just like any other COM object.
 */
function com_get_active_object (string $progid, int $code_page = null) {}


/**
 * The code that creates and manages objects of this class is 
 * a DLL that runs in the same process as the caller of the 
 * function specifying the class context.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CLSCTX_INPROC_SERVER', 1);

/**
 * The code that manages objects of this class is an in-process 
 * handler. This is a DLL that runs in the client process and 
 * implements client-side structures of this class when instances 
 * of the class are accessed remotely.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CLSCTX_INPROC_HANDLER', 2);

/**
 * The EXE code that creates and manages objects of this class runs on 
 * same machine but is loaded in a separate process space.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CLSCTX_LOCAL_SERVER', 4);

/**
 * A remote context. The code that creates and manages objects of this 
 * class is run on a different computer.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CLSCTX_REMOTE_SERVER', 16);

/**
 * Indicates server code, whether in-process, local, or remote. This 
 * definition ORs CLSCTX_INPROC_SERVER, 
 * CLSCTX_LOCAL_SERVER, and 
 * CLSCTX_REMOTE_SERVER.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CLSCTX_SERVER', 21);

/**
 * Indicates all class contexts. This definition ORs 
 * CLSCTX_INPROC_HANDLER and 
 * CLSCTX_SERVER.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CLSCTX_ALL', 23);

/**
 * NULL pointer reference.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_NULL', 1);

/**
 * A property with a type indicator of VT_EMPTY has 
 * no data associated with it; that is, the size of the value is zero.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_EMPTY', 0);

/**
 * 1-byte unsigned integer.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_UI1', 17);

/**
 * 1-byte signed integer.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_I1', 16);

/**
 * 2-byte unsigned integer.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_UI2', 18);

/**
 * Two bytes representing a 2-byte signed integer value.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_I2', 2);

/**
 * 4-byte unsigned integer.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_UI4', 19);

/**
 * 4-byte signed integer value.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_I4', 3);

/**
 * 32-bit IEEE floating point value.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_R4', 4);

/**
 * 64-bit IEEE floating point value.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_R8', 5);

/**
 * Boolean value.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_BOOL', 11);

/**
 * Error code; containing the status code associated with the 
 * error.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_ERROR', 10);

/**
 * 8-byte two's complement integer (scaled by 10,000).
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_CY', 6);

/**
 * A 64-bit floating point number representing the number of days 
 * (not seconds) since December 31, 1899. For example, 
 * January 1, 1900, is 2.0, January 2, 1900, 
 * is 3.0, and so on). This is stored in the same representation as 
 * VT_R8.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_DATE', 7);

/**
 * Pointer to a null-terminated Unicode string.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_BSTR', 8);

/**
 * A decimal structure.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_DECIMAL', 14);

/**
 * A pointer to an object that implements the IUnknown interface.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_UNKNOWN', 13);

/**
 * A pointer to a pointer to an object was specified.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_DISPATCH', 9);

/**
 * A type indicator followed by the corresponding value. 
 * VT_VARIANT can be used only with 
 * VT_BYREF.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_VARIANT', 12);

/**
 * 4-byte signed integer value (equivalent to 
 * VT_I4).
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_INT', 22);

/**
 * 4-byte unsigned integer (equivalent to 
 * VT_UI4).
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_UINT', 23);

/**
 * If the type indicator is combined with 
 * VT_ARRAY by an OR operator, the value is a pointer to a 
 * SAFEARRAY. VT_ARRAY 
 * can use the OR with the following data types: VT_I1, 
 * VT_UI1, VT_I2, VT_UI2, 
 * VT_I4, VT_UI4, VT_INT, 
 * VT_UINT, VT_R4, VT_R8, 
 * VT_BOOL, VT_DECIMAL, VT_ERROR, 
 * VT_CY, VT_DATE, VT_BSTR, 
 * VT_DISPATCH, VT_UNKNOWN and 
 * VT_VARIANT.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_ARRAY', 8192);

/**
 * If the type indicator is combined with VT_BYREF 
 * by an OR operator, the value is a reference. Reference types are 
 * interpreted as a reference to data, similar to the reference type in 
 * C++.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VT_BYREF', 16384);

/**
 * Default to ANSI code page.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_ACP', 0);

/**
 * Macintosh code page.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_MACCP', 2);

/**
 * Default to OEM code page.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_OEMCP', 1);

/**
 * Unicode (UTF-7).
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_UTF7', 65000);

/**
 * Unicode (UTF-8).
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_UTF8', 65001);

/**
 * SYMBOL translations.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_SYMBOL', 42);

/**
 * Current thread's ANSI code page
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('CP_THREAD_ACP', 3);

/**
 * The left bstr is less than right 
 * bstr.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VARCMP_LT', 0);

/**
 * The two parameters are equal.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VARCMP_EQ', 1);

/**
 * The left bstr is greater than right 
 * bstr.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VARCMP_GT', 2);

/**
 * Either expression is NULL.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('VARCMP_NULL', 3);

/**
 * Ignore case sensitivity.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('NORM_IGNORECASE', 1);

/**
 * Ignore nonspacing characters.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('NORM_IGNORENONSPACE', 2);

/**
 * Ignore symbols.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('NORM_IGNORESYMBOLS', 4);

/**
 * Ignore string width.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('NORM_IGNOREWIDTH', 131072);

/**
 * Ignore Kana type.
 * @link http://www.php.net/manual/en/com.constants.php
 */
define ('NORM_IGNOREKANATYPE', 65536);
define ('DISP_E_DIVBYZERO', -1);
define ('DISP_E_OVERFLOW', -1);
define ('DISP_E_BADINDEX', -1);
define ('MK_E_UNAVAILABLE', -1);

// End of com_dotnet v.7.2.3
