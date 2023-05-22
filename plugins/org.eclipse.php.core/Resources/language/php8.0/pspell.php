<?php

// Start of pspell v.8.0.28

/**
 * Load a new dictionary
 * @link http://www.php.net/manual/en/function.pspell-new.php
 * @param string $language 
 * @param string $spelling [optional] 
 * @param string $jargon [optional] 
 * @param string $encoding [optional] 
 * @param int $mode [optional] 
 * @return resource|false Returns an PSpell\Dictionary instance on success, or false on failure.
 */
function pspell_new (string $language, string $spelling = '""', string $jargon = '""', string $encoding = '""', int $mode = null): int {}

/**
 * Load a new dictionary with personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-new-personal.php
 * @param string $filename 
 * @param string $language 
 * @param string $spelling [optional] 
 * @param string $jargon [optional] 
 * @param string $encoding [optional] 
 * @param int $mode [optional] 
 * @return resource|false Returns an PSpell\Dictionary instance on success, or false on failure.
 */
function pspell_new_personal (string $filename, string $language, string $spelling = '""', string $jargon = '""', string $encoding = '""', int $mode = null): int {}

/**
 * Load a new dictionary with settings based on a given config
 * @link http://www.php.net/manual/en/function.pspell-new-config.php
 * @param resource $config 
 * @return resource|false Returns an PSpell\Dictionary instance on success, or false on failure
 */
function pspell_new_config ($config): int {}

/**
 * Check a word
 * @link http://www.php.net/manual/en/function.pspell-check.php
 * @param resource $dictionary 
 * @param string $word 
 * @return bool Returns true if the spelling is correct, false if not.
 */
function pspell_check ($dictionary, string $word): bool {}

/**
 * Suggest spellings of a word
 * @link http://www.php.net/manual/en/function.pspell-suggest.php
 * @param resource $dictionary 
 * @param string $word 
 * @return array|false Returns an array of possible spellings.
 */
function pspell_suggest ($dictionary, string $word): array|int {}

/**
 * Store a replacement pair for a word
 * @link http://www.php.net/manual/en/function.pspell-store-replacement.php
 * @param resource $dictionary 
 * @param string $misspelled 
 * @param string $correct 
 * @return bool Returns true on success or false on failure.
 */
function pspell_store_replacement ($dictionary, string $misspelled, string $correct): bool {}

/**
 * Add the word to a personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-add-to-personal.php
 * @param resource $dictionary 
 * @param string $word 
 * @return bool Returns true on success or false on failure.
 */
function pspell_add_to_personal ($dictionary, string $word): bool {}

/**
 * Add the word to the wordlist in the current session
 * @link http://www.php.net/manual/en/function.pspell-add-to-session.php
 * @param resource $dictionary 
 * @param string $word 
 * @return bool Returns true on success or false on failure.
 */
function pspell_add_to_session ($dictionary, string $word): bool {}

/**
 * Clear the current session
 * @link http://www.php.net/manual/en/function.pspell-clear-session.php
 * @param resource $dictionary 
 * @return bool Returns true on success or false on failure.
 */
function pspell_clear_session ($dictionary): bool {}

/**
 * Save the personal wordlist to a file
 * @link http://www.php.net/manual/en/function.pspell-save-wordlist.php
 * @param resource $dictionary 
 * @return bool Returns true on success or false on failure.
 */
function pspell_save_wordlist ($dictionary): bool {}

/**
 * Create a config used to open a dictionary
 * @link http://www.php.net/manual/en/function.pspell-config-create.php
 * @param string $language 
 * @param string $spelling [optional] 
 * @param string $jargon [optional] 
 * @param string $encoding [optional] 
 * @return resource Returns an PSpell\Config instance.
 */
function pspell_config_create (string $language, string $spelling = '""', string $jargon = '""', string $encoding = '""'): int {}

/**
 * Consider run-together words as valid compounds
 * @link http://www.php.net/manual/en/function.pspell-config-runtogether.php
 * @param resource $config 
 * @param bool $allow 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_runtogether ($config, bool $allow): bool {}

/**
 * Change the mode number of suggestions returned
 * @link http://www.php.net/manual/en/function.pspell-config-mode.php
 * @param resource $config 
 * @param int $mode 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_mode ($config, int $mode): bool {}

/**
 * Ignore words less than N characters long
 * @link http://www.php.net/manual/en/function.pspell-config-ignore.php
 * @param resource $config 
 * @param int $min_length 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_ignore ($config, int $min_length): bool {}

/**
 * Set a file that contains personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-personal.php
 * @param resource $config 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_personal ($config, string $filename): bool {}

/**
 * Location of the main word list
 * @link http://www.php.net/manual/en/function.pspell-config-dict-dir.php
 * @param resource $config 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_dict_dir ($config, string $directory): bool {}

/**
 * Location of language data files
 * @link http://www.php.net/manual/en/function.pspell-config-data-dir.php
 * @param resource $config 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_data_dir ($config, string $directory): bool {}

/**
 * Set a file that contains replacement pairs
 * @link http://www.php.net/manual/en/function.pspell-config-repl.php
 * @param resource $config 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_repl ($config, string $filename): bool {}

/**
 * Determine whether to save a replacement pairs list
 * along with the wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-save-repl.php
 * @param resource $config 
 * @param bool $save 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_save_repl ($config, bool $save): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 * @var int
 */
define ('PSPELL_FAST', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 * @var int
 */
define ('PSPELL_NORMAL', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 * @var int
 */
define ('PSPELL_BAD_SPELLERS', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 * @var int
 */
define ('PSPELL_RUN_TOGETHER', 8);

// End of pspell v.8.0.28
