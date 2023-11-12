<?php

// Start of readline v.8.2.6

/**
 * Reads a line
 * @link http://www.php.net/manual/en/function.readline.php
 * @param string|null $prompt [optional] 
 * @return string|false Returns a single string from the user. The line returned has the ending
 * newline removed.
 * If there is no more data to read, then false is returned.
 */
function readline (?string $prompt = null): string|false {}

/**
 * Gets/sets various internal readline variables
 * @link http://www.php.net/manual/en/function.readline-info.php
 * @param string|null $var_name [optional] 
 * @param int|string|bool|null $value [optional] 
 * @return mixed If called with no parameters, this function returns an array of
 * values for all the settings readline uses. The elements will
 * be indexed by the following values: done, end, erase_empty_line,
 * library_version, line_buffer, mark, pending_input, point, prompt,
 * readline_name, and terminal_name.
 * The array will only contain those elements which are supported by the library
 * used to built the readline extension.
 * <p>If called with one or two parameters, the old value is returned.</p>
 */
function readline_info (?string $var_name = null, int|string|bool|null $value = null): mixed {}

/**
 * Adds a line to the history
 * @link http://www.php.net/manual/en/function.readline-add-history.php
 * @param string $prompt 
 * @return bool Returns true on success or false on failure.
 */
function readline_add_history (string $prompt): bool {}

/**
 * Clears the history
 * @link http://www.php.net/manual/en/function.readline-clear-history.php
 * @return bool Returns true on success or false on failure.
 */
function readline_clear_history (): bool {}

/**
 * Reads the history
 * @link http://www.php.net/manual/en/function.readline-read-history.php
 * @param string|null $filename [optional] 
 * @return bool Returns true on success or false on failure.
 */
function readline_read_history (?string $filename = null): bool {}

/**
 * Writes the history
 * @link http://www.php.net/manual/en/function.readline-write-history.php
 * @param string|null $filename [optional] 
 * @return bool Returns true on success or false on failure.
 */
function readline_write_history (?string $filename = null): bool {}

/**
 * Registers a completion function
 * @link http://www.php.net/manual/en/function.readline-completion-function.php
 * @param callable $callback 
 * @return bool Returns true on success or false on failure.
 */
function readline_completion_function (callable $callback): bool {}

/**
 * Initializes the readline callback interface and terminal, prints the prompt and returns immediately
 * @link http://www.php.net/manual/en/function.readline-callback-handler-install.php
 * @param string $prompt 
 * @param callable $callback 
 * @return bool Returns true on success or false on failure.
 */
function readline_callback_handler_install (string $prompt, callable $callback): bool {}

/**
 * Reads a character and informs the readline callback interface when a line is received
 * @link http://www.php.net/manual/en/function.readline-callback-read-char.php
 * @return void No value is returned.
 */
function readline_callback_read_char (): void {}

/**
 * Removes a previously installed callback handler and restores terminal settings
 * @link http://www.php.net/manual/en/function.readline-callback-handler-remove.php
 * @return bool Returns true if a previously installed callback handler was removed, or
 * false if one could not be found.
 */
function readline_callback_handler_remove (): bool {}

/**
 * Redraws the display
 * @link http://www.php.net/manual/en/function.readline-redisplay.php
 * @return void No value is returned.
 */
function readline_redisplay (): void {}

/**
 * Inform readline that the cursor has moved to a new line
 * @link http://www.php.net/manual/en/function.readline-on-new-line.php
 * @return void No value is returned.
 */
function readline_on_new_line (): void {}


/**
 * The library which is used for readline support; currently either
 * readline or libedit.
 * @link http://www.php.net/manual/en/readline.constants.php
 * @var string
 */
define ('READLINE_LIB', "libedit");

// End of readline v.8.2.6
