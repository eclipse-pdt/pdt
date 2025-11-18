<?php

// Start of tokenizer v.8.5.0-dev

class PhpToken implements Stringable {

	public int $id;

	public string $text;

	public int $line;

	public int $pos;

	/**
	 * Splits given source into PHP tokens, represented by PhpToken objects.
	 * @link http://www.php.net/manual/en/phptoken.tokenize.php
	 * @param string $code The PHP source to parse.
	 * @param int $flags [optional] Valid flags:
	 * <p>
	 * <br>
	 * TOKEN_PARSE - Recognises the ability to use
	 * reserved words in specific contexts.
	 * </p>
	 * @return array An array of PHP tokens represented by instances of PhpToken or its descendants.
	 * This method returns static[] so that PhpToken can be seamlessly extended.
	 */
	public static function tokenize (string $code, int $flags = null): array {}

	/**
	 * Returns a new PhpToken object
	 * @link http://www.php.net/manual/en/phptoken.construct.php
	 * @param int $id One of the T_&#42; constants (see ), or an ASCII codepoint representing a single-char token.
	 * @param string $text The textual content of the token.
	 * @param int $line [optional] The starting line number (1-based) of the token.
	 * @param int $pos [optional] The starting position (0-based) in the tokenized string (the number of bytes).
	 * @return int 
	 */
	final public function __construct (int $id, string $text, int $line = -1, int $pos = -1): int {}

	/**
	 * Tells whether the token is of given kind.
	 * @link http://www.php.net/manual/en/phptoken.is.php
	 * @param int|string|array $kind Either a single value to match the token's id or textual content, or an array thereof.
	 * @return bool A boolean value whether the token is of given kind.
	 */
	public function is (int|string|array $kind): bool {}

	/**
	 * Tells whether the token would be ignored by the PHP parser.
	 * @link http://www.php.net/manual/en/phptoken.isignorable.php
	 * @return bool A boolean value whether the token would be ignored by the PHP parser (such as whitespace or comments).
	 */
	public function isIgnorable (): bool {}

	/**
	 * Returns the name of the token.
	 * @link http://www.php.net/manual/en/phptoken.gettokenname.php
	 * @return string|null An ASCII character for single-char tokens,
	 * or one of T_&#42; constant names for known tokens (see ),
	 * or null for unknown tokens.
	 */
	public function getTokenName (): ?string {}

	/**
	 * Returns the textual content of the token.
	 * @link http://www.php.net/manual/en/phptoken.tostring.php
	 * @return string A textual content of the token.
	 */
	public function __toString (): string {}

}

/**
 * Split given source into PHP tokens
 * @link http://www.php.net/manual/en/function.token-get-all.php
 * @param string $code 
 * @param int $flags [optional] 
 * @return array An array of token identifiers. Each individual token identifier is either
 * a single character (i.e.: ;, ., 
 * &gt;, !, etc...),
 * or a three element array containing the token index in element 0, the string
 * content of the original token in element 1 and the line number in element 2.
 */
function token_get_all (string $code, int $flags = null): array {}

/**
 * Get the symbolic name of a given PHP token
 * @link http://www.php.net/manual/en/function.token-name.php
 * @param int $id 
 * @return string The symbolic name of the given id.
 */
function token_name (int $id): string {}

define ('T_LNUMBER', 260);
define ('T_DNUMBER', 261);
define ('T_STRING', 262);
define ('T_NAME_FULLY_QUALIFIED', 263);
define ('T_NAME_RELATIVE', 264);
define ('T_NAME_QUALIFIED', 265);
define ('T_VARIABLE', 266);
define ('T_INLINE_HTML', 267);
define ('T_ENCAPSED_AND_WHITESPACE', 268);
define ('T_CONSTANT_ENCAPSED_STRING', 269);
define ('T_STRING_VARNAME', 270);
define ('T_NUM_STRING', 271);
define ('T_INCLUDE', 272);
define ('T_INCLUDE_ONCE', 273);
define ('T_EVAL', 274);
define ('T_REQUIRE', 275);
define ('T_REQUIRE_ONCE', 276);
define ('T_LOGICAL_OR', 277);
define ('T_LOGICAL_XOR', 278);
define ('T_LOGICAL_AND', 279);
define ('T_PRINT', 280);
define ('T_YIELD', 281);
define ('T_YIELD_FROM', 282);
define ('T_INSTANCEOF', 283);
define ('T_NEW', 284);
define ('T_CLONE', 285);
define ('T_EXIT', 286);
define ('T_IF', 287);
define ('T_ELSEIF', 288);
define ('T_ELSE', 289);
define ('T_ENDIF', 290);
define ('T_ECHO', 291);
define ('T_DO', 292);
define ('T_WHILE', 293);
define ('T_ENDWHILE', 294);
define ('T_FOR', 295);
define ('T_ENDFOR', 296);
define ('T_FOREACH', 297);
define ('T_ENDFOREACH', 298);
define ('T_DECLARE', 299);
define ('T_ENDDECLARE', 300);
define ('T_AS', 301);
define ('T_SWITCH', 302);
define ('T_ENDSWITCH', 303);
define ('T_CASE', 304);
define ('T_DEFAULT', 305);
define ('T_MATCH', 306);
define ('T_BREAK', 307);
define ('T_CONTINUE', 308);
define ('T_GOTO', 309);
define ('T_FUNCTION', 310);
define ('T_FN', 311);
define ('T_CONST', 312);
define ('T_RETURN', 313);
define ('T_TRY', 314);
define ('T_CATCH', 315);
define ('T_FINALLY', 316);
define ('T_THROW', 317);
define ('T_USE', 318);
define ('T_INSTEADOF', 319);
define ('T_GLOBAL', 320);
define ('T_STATIC', 321);
define ('T_ABSTRACT', 322);
define ('T_FINAL', 323);
define ('T_PRIVATE', 324);
define ('T_PROTECTED', 325);
define ('T_PUBLIC', 326);
define ('T_PRIVATE_SET', 327);
define ('T_PROTECTED_SET', 328);
define ('T_PUBLIC_SET', 329);
define ('T_READONLY', 330);
define ('T_VAR', 331);
define ('T_UNSET', 332);
define ('T_ISSET', 333);
define ('T_EMPTY', 334);
define ('T_HALT_COMPILER', 335);
define ('T_CLASS', 336);
define ('T_TRAIT', 337);
define ('T_INTERFACE', 338);
define ('T_ENUM', 339);
define ('T_EXTENDS', 340);
define ('T_IMPLEMENTS', 341);
define ('T_NAMESPACE', 342);
define ('T_LIST', 343);
define ('T_ARRAY', 344);
define ('T_CALLABLE', 345);
define ('T_LINE', 346);
define ('T_FILE', 347);
define ('T_DIR', 348);
define ('T_CLASS_C', 349);
define ('T_TRAIT_C', 350);
define ('T_METHOD_C', 351);
define ('T_FUNC_C', 352);
define ('T_PROPERTY_C', 353);
define ('T_NS_C', 354);
define ('T_ATTRIBUTE', 355);
define ('T_PLUS_EQUAL', 356);
define ('T_MINUS_EQUAL', 357);
define ('T_MUL_EQUAL', 358);
define ('T_DIV_EQUAL', 359);
define ('T_CONCAT_EQUAL', 360);
define ('T_MOD_EQUAL', 361);
define ('T_AND_EQUAL', 362);
define ('T_OR_EQUAL', 363);
define ('T_XOR_EQUAL', 364);
define ('T_SL_EQUAL', 365);
define ('T_SR_EQUAL', 366);
define ('T_COALESCE_EQUAL', 367);
define ('T_BOOLEAN_OR', 368);
define ('T_BOOLEAN_AND', 369);
define ('T_IS_EQUAL', 370);
define ('T_IS_NOT_EQUAL', 371);
define ('T_IS_IDENTICAL', 372);
define ('T_IS_NOT_IDENTICAL', 373);
define ('T_IS_SMALLER_OR_EQUAL', 374);
define ('T_IS_GREATER_OR_EQUAL', 375);
define ('T_SPACESHIP', 376);
define ('T_SL', 377);
define ('T_SR', 378);
define ('T_INC', 379);
define ('T_DEC', 380);
define ('T_INT_CAST', 381);
define ('T_DOUBLE_CAST', 382);
define ('T_STRING_CAST', 383);
define ('T_ARRAY_CAST', 384);
define ('T_OBJECT_CAST', 385);
define ('T_BOOL_CAST', 386);
define ('T_UNSET_CAST', 387);
define ('T_VOID_CAST', 388);
define ('T_OBJECT_OPERATOR', 389);
define ('T_NULLSAFE_OBJECT_OPERATOR', 390);
define ('T_DOUBLE_ARROW', 391);
define ('T_COMMENT', 392);
define ('T_DOC_COMMENT', 393);
define ('T_OPEN_TAG', 394);
define ('T_OPEN_TAG_WITH_ECHO', 395);
define ('T_CLOSE_TAG', 396);
define ('T_WHITESPACE', 397);
define ('T_START_HEREDOC', 398);
define ('T_END_HEREDOC', 399);
define ('T_DOLLAR_OPEN_CURLY_BRACES', 400);
define ('T_CURLY_OPEN', 401);
define ('T_PAAMAYIM_NEKUDOTAYIM', 402);
define ('T_NS_SEPARATOR', 403);
define ('T_ELLIPSIS', 404);
define ('T_COALESCE', 405);
define ('T_POW', 406);
define ('T_POW_EQUAL', 407);
define ('T_PIPE', 408);
define ('T_AMPERSAND_FOLLOWED_BY_VAR_OR_VARARG', 409);
define ('T_AMPERSAND_NOT_FOLLOWED_BY_VAR_OR_VARARG', 410);
define ('T_BAD_CHARACTER', 411);
define ('T_DOUBLE_COLON', 402);

/**
 * Recognises the ability to use reserved words in specific contexts.
 * @link http://www.php.net/manual/en/tokenizer.constants.php
 * @var int
 */
define ('TOKEN_PARSE', 1);

// End of tokenizer v.8.5.0-dev
