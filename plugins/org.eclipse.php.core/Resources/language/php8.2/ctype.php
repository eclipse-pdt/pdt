<?php

// Start of ctype v.8.2.6

/**
 * Check for alphanumeric character(s)
 * @link http://www.php.net/manual/en/function.ctype-alnum.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is either
 * a letter or a digit, false otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_alnum (mixed $text): bool {}

/**
 * Check for alphabetic character(s)
 * @link http://www.php.net/manual/en/function.ctype-alpha.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is 
 * a letter from the current locale, false otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_alpha (mixed $text): bool {}

/**
 * Check for control character(s)
 * @link http://www.php.net/manual/en/function.ctype-cntrl.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is 
 * a control character from the current locale, false otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_cntrl (mixed $text): bool {}

/**
 * Check for numeric character(s)
 * @link http://www.php.net/manual/en/function.ctype-digit.php
 * @param mixed $text 
 * @return bool Returns true if every character in the string
 * text is a decimal digit, false otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_digit (mixed $text): bool {}

/**
 * Check for lowercase character(s)
 * @link http://www.php.net/manual/en/function.ctype-lower.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is 
 * a lowercase letter in the current locale.
 * When called with an empty string the result will always be false.
 */
function ctype_lower (mixed $text): bool {}

/**
 * Check for any printable character(s) except space
 * @link http://www.php.net/manual/en/function.ctype-graph.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is 
 * printable and actually creates visible output (no white space), false
 * otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_graph (mixed $text): bool {}

/**
 * Check for printable character(s)
 * @link http://www.php.net/manual/en/function.ctype-print.php
 * @param mixed $text 
 * @return bool Returns true if every character in text 
 * will actually create output (including blanks). Returns false if 
 * text contains control characters or characters 
 * that do not have any output or control function at all.
 * When called with an empty string the result will always be false.
 */
function ctype_print (mixed $text): bool {}

/**
 * Check for any printable character which is not whitespace or an
 * alphanumeric character
 * @link http://www.php.net/manual/en/function.ctype-punct.php
 * @param mixed $text 
 * @return bool Returns true if every character in text 
 * is printable, but neither letter, digit or blank, false otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_punct (mixed $text): bool {}

/**
 * Check for whitespace character(s)
 * @link http://www.php.net/manual/en/function.ctype-space.php
 * @param mixed $text 
 * @return bool Returns true if every character in text 
 * creates some sort of white space, false otherwise. Besides the 
 * blank character this also includes tab, vertical tab, line feed,
 * carriage return and form feed characters.
 * When called with an empty string the result will always be false.
 */
function ctype_space (mixed $text): bool {}

/**
 * Check for uppercase character(s)
 * @link http://www.php.net/manual/en/function.ctype-upper.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is 
 * an uppercase letter in the current locale.
 * When called with an empty string the result will always be false.
 */
function ctype_upper (mixed $text): bool {}

/**
 * Check for character(s) representing a hexadecimal digit
 * @link http://www.php.net/manual/en/function.ctype-xdigit.php
 * @param mixed $text 
 * @return bool Returns true if every character in text is 
 * a hexadecimal 'digit', that is a decimal digit or a character from 
 * [A-Fa-f] , false otherwise.
 * When called with an empty string the result will always be false.
 */
function ctype_xdigit (mixed $text): bool {}

// End of ctype v.8.2.6
