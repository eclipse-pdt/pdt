<?php

// Start of pspell v.7.1.1

/**
 * Load a new dictionary
 * @link http://www.php.net/manual/en/function.pspell-new.php
 * @param string $language The language parameter is the language code which consists of the
 * two letter ISO 639 language code and an optional two letter ISO
 * 3166 country code after a dash or underscore.
 * @param string $spelling [optional] The spelling parameter is the requested spelling for languages
 * with more than one spelling such as English. Known values are
 * 'american', 'british', and 'canadian'.
 * @param string $jargon [optional] The jargon parameter contains extra information to distinguish
 * two different words lists that have the same language and
 * spelling parameters.
 * @param string $encoding [optional] The encoding parameter is the encoding that words are expected to
 * be in. Valid values are 'utf-8', 'iso8859-&#42;', 'koi8-r',
 * 'viscii', 'cp1252', 'machine unsigned 16', 'machine unsigned
 * 32'. This parameter is largely untested, so be careful when
 * using.
 * @param int $mode [optional] <p>
 * The mode parameter is the mode in which spellchecker will work.
 * There are several modes available:
 * <p>
 * <br>
 * PSPELL_FAST - Fast mode (least number of
 * suggestions)
 * <br>
 * PSPELL_NORMAL - Normal mode (more suggestions)
 * <br>
 * PSPELL_BAD_SPELLERS - Slow mode (a lot of
 * suggestions)
 * <br>
 * PSPELL_RUN_TOGETHER - Consider run-together words
 * as legal compounds. That is, "thecat" will be a legal compound,
 * although there should be a space between the two words. Changing this
 * setting only affects the results returned by
 * pspell_check; pspell_suggest
 * will still return suggestions.
 * </p>
 * Mode is a bitmask constructed from different constants listed above.
 * However, PSPELL_FAST,
 * PSPELL_NORMAL and
 * PSPELL_BAD_SPELLERS are mutually exclusive, so you
 * should select only one of them.
 * </p>
 * @return int the dictionary link identifier on success or false on failure.
 */
function pspell_new (string $language, string $spelling = null, string $jargon = null, string $encoding = null, int $mode = null) {}

/**
 * Load a new dictionary with personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-new-personal.php
 * @param string $personal The file where words added to the personal list will be stored.
 * It should be an absolute filename beginning with '/' because otherwise
 * it will be relative to $HOME, which is "/root" for most systems, and
 * is probably not what you want.
 * @param string $language The language code which consists of the two letter ISO 639 language
 * code and an optional two letter ISO 3166 country code after a dash
 * or underscore.
 * @param string $spelling [optional] The requested spelling for languages with more than one spelling such
 * as English. Known values are 'american', 'british', and 'canadian'.
 * @param string $jargon [optional] Extra information to distinguish two different words lists that have
 * the same language and spelling parameters.
 * @param string $encoding [optional] The encoding that words are expected to be in. Valid values are
 * utf-8, iso8859-&#42;, 
 * koi8-r, viscii, 
 * cp1252, machine unsigned 16, 
 * machine unsigned 32.
 * @param int $mode [optional] <p>
 * The mode in which spellchecker will work. There are several modes available:
 * <p>
 * <br>
 * PSPELL_FAST - Fast mode (least number of
 * suggestions)
 * <br>
 * PSPELL_NORMAL - Normal mode (more suggestions)
 * <br>
 * PSPELL_BAD_SPELLERS - Slow mode (a lot of
 * suggestions)
 * <br>
 * PSPELL_RUN_TOGETHER - Consider run-together words
 * as legal compounds. That is, "thecat" will be a legal compound,
 * although there should be a space between the two words. Changing this
 * setting only affects the results returned by
 * pspell_check; pspell_suggest
 * will still return suggestions.
 * </p>
 * Mode is a bitmask constructed from different constants listed above.
 * However, PSPELL_FAST,
 * PSPELL_NORMAL and
 * PSPELL_BAD_SPELLERS are mutually exclusive, so you
 * should select only one of them.
 * </p>
 * @return int the dictionary link identifier for use in other pspell functions.
 */
function pspell_new_personal (string $personal, string $language, string $spelling = null, string $jargon = null, string $encoding = null, int $mode = null) {}

/**
 * Load a new dictionary with settings based on a given config
 * @link http://www.php.net/manual/en/function.pspell-new-config.php
 * @param int $config The config parameter is the one returned by
 * pspell_config_create when the config was created.
 * @return int a dictionary link identifier on success.
 */
function pspell_new_config (int $config) {}

/**
 * Check a word
 * @link http://www.php.net/manual/en/function.pspell-check.php
 * @param int $dictionary_link 
 * @param string $word The tested word.
 * @return bool true if the spelling is correct, false if not.
 */
function pspell_check (int $dictionary_link, string $word) {}

/**
 * Suggest spellings of a word
 * @link http://www.php.net/manual/en/function.pspell-suggest.php
 * @param int $dictionary_link 
 * @param string $word The tested word.
 * @return array an array of possible spellings.
 */
function pspell_suggest (int $dictionary_link, string $word) {}

/**
 * Store a replacement pair for a word
 * @link http://www.php.net/manual/en/function.pspell-store-replacement.php
 * @param int $dictionary_link A dictionary link identifier, opened with
 * pspell_new_personal
 * @param string $misspelled The misspelled word.
 * @param string $correct The fixed spelling for the misspelled word.
 * @return bool true on success or false on failure
 */
function pspell_store_replacement (int $dictionary_link, string $misspelled, string $correct) {}

/**
 * Add the word to a personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-add-to-personal.php
 * @param int $dictionary_link 
 * @param string $word The added word.
 * @return bool true on success or false on failure
 */
function pspell_add_to_personal (int $dictionary_link, string $word) {}

/**
 * Add the word to the wordlist in the current session
 * @link http://www.php.net/manual/en/function.pspell-add-to-session.php
 * @param int $dictionary_link 
 * @param string $word The added word.
 * @return bool true on success or false on failure
 */
function pspell_add_to_session (int $dictionary_link, string $word) {}

/**
 * Clear the current session
 * @link http://www.php.net/manual/en/function.pspell-clear-session.php
 * @param int $dictionary_link 
 * @return bool true on success or false on failure
 */
function pspell_clear_session (int $dictionary_link) {}

/**
 * Save the personal wordlist to a file
 * @link http://www.php.net/manual/en/function.pspell-save-wordlist.php
 * @param int $dictionary_link A dictionary link identifier opened with
 * pspell_new_personal.
 * @return bool true on success or false on failure
 */
function pspell_save_wordlist (int $dictionary_link) {}

/**
 * Create a config used to open a dictionary
 * @link http://www.php.net/manual/en/function.pspell-config-create.php
 * @param string $language The language parameter is the language code which consists of the
 * two letter ISO 639 language code and an optional two letter ISO
 * 3166 country code after a dash or underscore.
 * @param string $spelling [optional] The spelling parameter is the requested spelling for languages
 * with more than one spelling such as English. Known values are
 * 'american', 'british', and 'canadian'.
 * @param string $jargon [optional] The jargon parameter contains extra information to distinguish
 * two different words lists that have the same language and
 * spelling parameters.
 * @param string $encoding [optional] The encoding parameter is the encoding that words are expected to
 * be in. Valid values are 'utf-8', 'iso8859-&#42;', 'koi8-r',
 * 'viscii', 'cp1252', 'machine unsigned 16', 'machine unsigned
 * 32'. This parameter is largely untested, so be careful when
 * using.
 * @return int Retuns a pspell config identifier, or false on error.
 */
function pspell_config_create (string $language, string $spelling = null, string $jargon = null, string $encoding = null) {}

/**
 * Consider run-together words as valid compounds
 * @link http://www.php.net/manual/en/function.pspell-config-runtogether.php
 * @param int $dictionary_link 
 * @param bool $flag true if run-together words should be treated as legal compounds,
 * false otherwise.
 * @return bool true on success or false on failure
 */
function pspell_config_runtogether (int $dictionary_link, bool $flag) {}

/**
 * Change the mode number of suggestions returned
 * @link http://www.php.net/manual/en/function.pspell-config-mode.php
 * @param int $dictionary_link 
 * @param int $mode <p>
 * The mode parameter is the mode in which spellchecker will work.
 * There are several modes available:
 * <p>
 * <br>
 * PSPELL_FAST - Fast mode (least number of
 * suggestions)
 * <br>
 * PSPELL_NORMAL - Normal mode (more suggestions)
 * <br>
 * PSPELL_BAD_SPELLERS - Slow mode (a lot of
 * suggestions)
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function pspell_config_mode (int $dictionary_link, int $mode) {}

/**
 * Ignore words less than N characters long
 * @link http://www.php.net/manual/en/function.pspell-config-ignore.php
 * @param int $dictionary_link 
 * @param int $n Words less than n characters will be skipped.
 * @return bool true on success or false on failure
 */
function pspell_config_ignore (int $dictionary_link, int $n) {}

/**
 * Set a file that contains personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-personal.php
 * @param int $dictionary_link 
 * @param string $file The personal wordlist. If the file does not exist, it will be created.
 * The file should be writable by whoever PHP runs as (e.g. nobody).
 * @return bool true on success or false on failure
 */
function pspell_config_personal (int $dictionary_link, string $file) {}

/**
 * Location of the main word list
 * @link http://www.php.net/manual/en/function.pspell-config-dict-dir.php
 * @param int $conf 
 * @param string $directory 
 * @return bool true on success or false on failure
 */
function pspell_config_dict_dir (int $conf, string $directory) {}

/**
 * location of language data files
 * @link http://www.php.net/manual/en/function.pspell-config-data-dir.php
 * @param int $conf 
 * @param string $directory 
 * @return bool true on success or false on failure
 */
function pspell_config_data_dir (int $conf, string $directory) {}

/**
 * Set a file that contains replacement pairs
 * @link http://www.php.net/manual/en/function.pspell-config-repl.php
 * @param int $dictionary_link 
 * @param string $file The file should be writable by whoever PHP runs as (e.g. nobody).
 * @return bool true on success or false on failure
 */
function pspell_config_repl (int $dictionary_link, string $file) {}

/**
 * Determine whether to save a replacement pairs list
 * along with the wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-save-repl.php
 * @param int $dictionary_link 
 * @param bool $flag true if replacement pairs should be saved, false otherwise.
 * @return bool true on success or false on failure
 */
function pspell_config_save_repl (int $dictionary_link, bool $flag) {}


/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 */
define ('PSPELL_FAST', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 */
define ('PSPELL_NORMAL', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 */
define ('PSPELL_BAD_SPELLERS', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pspell.constants.php
 */
define ('PSPELL_RUN_TOGETHER', 8);

// End of pspell v.7.1.1
