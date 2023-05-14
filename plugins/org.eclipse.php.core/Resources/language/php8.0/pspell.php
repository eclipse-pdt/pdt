<?php

// Start of pspell v.8.0.28

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
 * @return mixed an PSpell\Dictionary instance on success, or false on failure.
 */
function pspell_new (string $language, string $spelling = null, string $jargon = null, string $encoding = null, int $mode = null): int|false {}

/**
 * Load a new dictionary with personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-new-personal.php
 * @param string $filename The file where words added to the personal list will be stored.
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
 * @return mixed an PSpell\Dictionary instance on success, or false on failure.
 */
function pspell_new_personal (string $filename, string $language, string $spelling = null, string $jargon = null, string $encoding = null, int $mode = null): int|false {}

/**
 * Load a new dictionary with settings based on a given config
 * @link http://www.php.net/manual/en/function.pspell-new-config.php
 * @param PSpell\Config $config The config parameter is the one returned by
 * pspell_config_create when the config was created.
 * @return mixed an PSpell\Dictionary instance on success, or false on failure
 */
function pspell_new_config ($config): int|false {}

/**
 * Check a word
 * @link http://www.php.net/manual/en/function.pspell-check.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @param string $word The tested word.
 * @return bool true if the spelling is correct, false if not.
 */
function pspell_check ($dictionary, string $word): bool {}

/**
 * Suggest spellings of a word
 * @link http://www.php.net/manual/en/function.pspell-suggest.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @param string $word The tested word.
 * @return mixed an array of possible spellings.
 */
function pspell_suggest ($dictionary, string $word): array|false {}

/**
 * Store a replacement pair for a word
 * @link http://www.php.net/manual/en/function.pspell-store-replacement.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @param string $misspelled The misspelled word.
 * @param string $correct The fixed spelling for the misspelled word.
 * @return bool true on success or false on failure
 */
function pspell_store_replacement ($dictionary, string $misspelled, string $correct): bool {}

/**
 * Add the word to a personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-add-to-personal.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @param string $word The added word.
 * @return bool true on success or false on failure
 */
function pspell_add_to_personal ($dictionary, string $word): bool {}

/**
 * Add the word to the wordlist in the current session
 * @link http://www.php.net/manual/en/function.pspell-add-to-session.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @param string $word The added word.
 * @return bool true on success or false on failure
 */
function pspell_add_to_session ($dictionary, string $word): bool {}

/**
 * Clear the current session
 * @link http://www.php.net/manual/en/function.pspell-clear-session.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @return bool true on success or false on failure
 */
function pspell_clear_session ($dictionary): bool {}

/**
 * Save the personal wordlist to a file
 * @link http://www.php.net/manual/en/function.pspell-save-wordlist.php
 * @param PSpell\Dictionary $dictionary pspell.parameter.pspell-dictionary
 * @return bool true on success or false on failure
 */
function pspell_save_wordlist ($dictionary): bool {}

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
 * @return PSpell\Config an PSpell\Config instance.
 */
function pspell_config_create (string $language, string $spelling = null, string $jargon = null, string $encoding = null): int {}

/**
 * Consider run-together words as valid compounds
 * @link http://www.php.net/manual/en/function.pspell-config-runtogether.php
 * @param PSpell\Config $config pspell.parameter.pspell-config
 * @param bool $allow true if run-together words should be treated as legal compounds,
 * false otherwise.
 * @return bool true on success or false on failure
 */
function pspell_config_runtogether ($config, bool $allow): bool {}

/**
 * Change the mode number of suggestions returned
 * @link http://www.php.net/manual/en/function.pspell-config-mode.php
 * @param PSpell\Config $config pspell.parameter.pspell-config
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
function pspell_config_mode ($config, int $mode): bool {}

/**
 * Ignore words less than N characters long
 * @link http://www.php.net/manual/en/function.pspell-config-ignore.php
 * @param PSpell\Config $config pspell.parameter.pspell-config
 * @param int $min_length Words less than min_length characters will be skipped.
 * @return bool true on success or false on failure
 */
function pspell_config_ignore ($config, int $min_length): bool {}

/**
 * Set a file that contains personal wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-personal.php
 * @param PSpell\Config $config pspell.parameter.pspell-config
 * @param string $filename The personal wordlist. If the file does not exist, it will be created.
 * The file should be writable by whoever PHP runs as (e.g. nobody).
 * @return bool true on success or false on failure
 */
function pspell_config_personal ($config, string $filename): bool {}

/**
 * Location of the main word list
 * @link http://www.php.net/manual/en/function.pspell-config-dict-dir.php
 * @param PSpell\Config $config 
 * @param string $directory 
 * @return bool true on success or false on failure
 */
function pspell_config_dict_dir ($config, string $directory): bool {}

/**
 * Location of language data files
 * @link http://www.php.net/manual/en/function.pspell-config-data-dir.php
 * @param PSpell\Config $config 
 * @param string $directory 
 * @return bool true on success or false on failure
 */
function pspell_config_data_dir ($config, string $directory): bool {}

/**
 * Set a file that contains replacement pairs
 * @link http://www.php.net/manual/en/function.pspell-config-repl.php
 * @param PSpell\Config $config pspell.parameter.pspell-config
 * @param string $filename The file should be writable by whoever PHP runs as (e.g. nobody).
 * @return bool true on success or false on failure
 */
function pspell_config_repl ($config, string $filename): bool {}

/**
 * Determine whether to save a replacement pairs list
 * along with the wordlist
 * @link http://www.php.net/manual/en/function.pspell-config-save-repl.php
 * @param PSpell\Config $config pspell.parameter.pspell-config
 * @param bool $save true if replacement pairs should be saved, false otherwise.
 * @return bool true on success or false on failure
 */
function pspell_config_save_repl ($config, bool $save): bool {}


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

// End of pspell v.8.0.28
