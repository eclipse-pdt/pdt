<?php

// Start of pspell v.8.2.6

namespace PSpell {

/**
 * A fully opaque class which replaces a pspell resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.pspell-dictionary.php
 */
final class Dictionary  {
}

/**
 * A fully opaque class which replaces a pspell config resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.pspell-config.php
 */
final class Config  {
}


}


namespace {

/**
 * Load a new dictionary
 * @link http://www.php.net/manual/en/function.pspell-new.php
 * @param string $language 
 * @param string $spelling [optional] 
 * @param string $jargon [optional] 
 * @param string $encoding [optional] 
 * @param int $mode [optional] 
 * @return PSpell\Dictionary|false Returns an PSpell\Dictionary instance on success, or false on failure.
 */
function pspell_new (string $language, string $spelling = '""', string $jargon = '""', string $encoding = '""', int $mode = null): PSpell\Dictionary|false {}

/**
 * Load a new dictionary with personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-new-personal.php
 * @param string $filename 
 * @param string $language 
 * @param string $spelling [optional] 
 * @param string $jargon [optional] 
 * @param string $encoding [optional] 
 * @param int $mode [optional] 
 * @return PSpell\Dictionary|false Returns an PSpell\Dictionary instance on success, or false on failure.
 */
function pspell_new_personal (string $filename, string $language, string $spelling = '""', string $jargon = '""', string $encoding = '""', int $mode = null): PSpell\Dictionary|false {}

/**
 * Load a new dictionary with settings based on a given config
 * @link http://www.php.net/manual/en/function.pspell-new-config.php
 * @param PSpell\Config $config 
 * @return PSpell\Dictionary|false Returns an PSpell\Dictionary instance on success, or false on failure
 */
function pspell_new_config (PSpell\Config $config): PSpell\Dictionary|false {}

/**
 * Check a word
 * @link http://www.php.net/manual/en/function.pspell-check.php
 * @param PSpell\Dictionary $dictionary 
 * @param string $word 
 * @return bool Returns true if the spelling is correct, false if not.
 */
function pspell_check (PSpell\Dictionary $dictionary, string $word): bool {}

/**
 * Suggest spellings of a word
 * @link http://www.php.net/manual/en/function.pspell-suggest.php
 * @param PSpell\Dictionary $dictionary 
 * @param string $word 
 * @return array|false Returns an array of possible spellings.
 */
function pspell_suggest (PSpell\Dictionary $dictionary, string $word): array|false {}

/**
 * Store a replacement pair for a word
 * @link http://www.php.net/manual/en/function.pspell-store-replacement.php
 * @param PSpell\Dictionary $dictionary 
 * @param string $misspelled 
 * @param string $correct 
 * @return bool Returns true on success or false on failure.
 */
function pspell_store_replacement (PSpell\Dictionary $dictionary, string $misspelled, string $correct): bool {}

/**
 * Add the word to a personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-add-to-personal.php
 * @param PSpell\Dictionary $dictionary 
 * @param string $word 
 * @return bool Returns true on success or false on failure.
 */
function pspell_add_to_personal (PSpell\Dictionary $dictionary, string $word): bool {}

/**
 * Add the word to the wordlist in the current session
 * @link http://www.php.net/manual/en/function.pspell-add-to-session.php
 * @param PSpell\Dictionary $dictionary 
 * @param string $word 
 * @return bool Returns true on success or false on failure.
 */
function pspell_add_to_session (PSpell\Dictionary $dictionary, string $word): bool {}

/**
 * Clear the current session
 * @link http://www.php.net/manual/en/function.pspell-clear-session.php
 * @param PSpell\Dictionary $dictionary 
 * @return bool Returns true on success or false on failure.
 */
function pspell_clear_session (PSpell\Dictionary $dictionary): bool {}

/**
 * Save the personal wordlist to a file
 * @link http://www.php.net/manual/en/function.pspell-save-wordlist.php
 * @param PSpell\Dictionary $dictionary 
 * @return bool Returns true on success or false on failure.
 */
function pspell_save_wordlist (PSpell\Dictionary $dictionary): bool {}

/**
 * Create a config used to open a dictionary
 * @link http://www.php.net/manual/en/function.pspell-config-create.php
 * @param string $language 
 * @param string $spelling [optional] 
 * @param string $jargon [optional] 
 * @param string $encoding [optional] 
 * @return PSpell\Config Returns an PSpell\Config instance.
 */
function pspell_config_create (string $language, string $spelling = '""', string $jargon = '""', string $encoding = '""'): PSpell\Config {}

/**
 * Consider run-together words as valid compounds
 * @link http://www.php.net/manual/en/function.pspell-config-runtogether.php
 * @param PSpell\Config $config 
 * @param bool $allow 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_runtogether (PSpell\Config $config, bool $allow): bool {}

/**
 * Change the mode number of suggestions returned
 * @link http://www.php.net/manual/en/function.pspell-config-mode.php
 * @param PSpell\Config $config 
 * @param int $mode 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_mode (PSpell\Config $config, int $mode): bool {}

/**
 * Ignore words less than N characters long
 * @link http://www.php.net/manual/en/function.pspell-config-ignore.php
 * @param PSpell\Config $config 
 * @param int $min_length 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_ignore (PSpell\Config $config, int $min_length): bool {}

/**
 * Set a file that contains personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-personal.php
 * @param PSpell\Config $config 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_personal (PSpell\Config $config, string $filename): bool {}

/**
 * Location of the main word list
 * @link http://www.php.net/manual/en/function.pspell-config-dict-dir.php
 * @param PSpell\Config $config 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_dict_dir (PSpell\Config $config, string $directory): bool {}

/**
 * Location of language data files
 * @link http://www.php.net/manual/en/function.pspell-config-data-dir.php
 * @param PSpell\Config $config 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_data_dir (PSpell\Config $config, string $directory): bool {}

/**
 * Set a file that contains replacement pairs
 * @link http://www.php.net/manual/en/function.pspell-config-repl.php
 * @param PSpell\Config $config 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_repl (PSpell\Config $config, string $filename): bool {}

/**
 * Determine whether to save a replacement pairs list
 * along with the wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-save-repl.php
 * @param PSpell\Config $config 
 * @param bool $save 
 * @return bool Returns true on success or false on failure.
 */
function pspell_config_save_repl (PSpell\Config $config, bool $save): bool {}


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


}

// End of pspell v.8.2.6
