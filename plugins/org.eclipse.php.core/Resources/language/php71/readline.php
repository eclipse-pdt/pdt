<?php

// Start of readline v.7.1.1

/**
 * Reads a line
 * @link http://www.php.net/manual/en/function.readline.php
 * @param string $prompt [optional] You may specify a string with which to prompt the user.
 * @return string a single string from the user. The line returned has the ending
 * newline removed.
 */
function readline (string $prompt = null) {}

/**
 * Gets/sets various internal readline variables
 * @link http://www.php.net/manual/en/function.readline-info.php
 * @param string $varname [optional] A variable name.
 * @param string $newvalue [optional] If provided, this will be the new value of the setting.
 * @return mixed If called with no parameters, this function returns an array of
 * values for all the setting readline uses. The elements will
 * be indexed by the following values: done, end, erase_empty_line,
 * library_version, line_buffer, mark, pending_input, point, prompt,
 * readline_name, and terminal_name.
 * <p>
 * If called with one or two parameters, the old value is returned.
 * </p>
 */
function readline_info (string $varname = null, string $newvalue = null) {}

/**
 * Adds a line to the history
 * @link http://www.php.net/manual/en/function.readline-add-history.php
 * @param string $line The line to be added in the history.
 * @return bool true on success or false on failure
 */
function readline_add_history (string $line) {}

/**
 * Clears the history
 * @link http://www.php.net/manual/en/function.readline-clear-history.php
 * @return bool true on success or false on failure
 */
function readline_clear_history () {}

/**
 * Reads the history
 * @link http://www.php.net/manual/en/function.readline-read-history.php
 * @param string $filename [optional] Path to the filename containing the command history.
 * @return bool true on success or false on failure
 */
function readline_read_history (string $filename = null) {}

/**
 * Writes the history
 * @link http://www.php.net/manual/en/function.readline-write-history.php
 * @param string $filename [optional] Path to the saved file.
 * @return bool true on success or false on failure
 */
function readline_write_history (string $filename = null) {}

/**
 * Registers a completion function
 * @link http://www.php.net/manual/en/function.readline-completion-function.php
 * @param callable $function You must supply the name of an existing function which accepts a
 * partial command line and returns an array of possible matches.
 * @return bool true on success or false on failure
 */
function readline_completion_function (callable $function) {}

/**
 * Initializes the readline callback interface and terminal, prints the prompt and returns immediately
 * @link http://www.php.net/manual/en/function.readline-callback-handler-install.php
 * @param string $prompt The prompt message.
 * @param callable $callback The callback function takes one parameter; the
 * user input returned.
 * @return bool true on success or false on failure
 */
function readline_callback_handler_install (string $prompt, callable $callback) {}

/**
 * Reads a character and informs the readline callback interface when a line is received
 * @link http://www.php.net/manual/en/function.readline-callback-read-char.php
 * @return void 
 */
function readline_callback_read_char () {}

/**
 * Removes a previously installed callback handler and restores terminal settings
 * @link http://www.php.net/manual/en/function.readline-callback-handler-remove.php
 * @return bool true if a previously installed callback handler was removed, or
 * false if one could not be found.
 */
function readline_callback_handler_remove () {}

/**
 * Redraws the display
 * @link http://www.php.net/manual/en/function.readline-redisplay.php
 * @return void 
 */
function readline_redisplay () {}

/**
 * Inform readline that the cursor has moved to a new line
 * @link http://www.php.net/manual/en/function.readline-on-new-line.php
 * @return void 
 */
function readline_on_new_line () {}

define ('READLINE_LIB', "libedit");

// End of readline v.7.1.1
