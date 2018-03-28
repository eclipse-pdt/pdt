<?php

// Start of enchant v.1.1.0

/**
 * create a new broker object capable of requesting
 * @link http://www.php.net/manual/en/function.enchant-broker-init.php
 * @return resource a broker resource on success or false.
 */
function enchant_broker_init () {}

/**
 * Free the broker resource and its dictionnaries
 * @link http://www.php.net/manual/en/function.enchant-broker-free.php
 * @param resource $broker Broker resource
 * @return bool true on success or false on failure
 */
function enchant_broker_free ($broker) {}

/**
 * Returns the last error of the broker
 * @link http://www.php.net/manual/en/function.enchant-broker-get-error.php
 * @param resource $broker Broker resource.
 * @return string Return the msg string if an error was found or false
 */
function enchant_broker_get_error ($broker) {}

/**
 * Set the directory path for a given backend
 * @link http://www.php.net/manual/en/function.enchant-broker-set-dict-path.php
 * @param resource $broker Broker resource.
 * @param int $dict_type The type of the dictionaries, i.e. ENCHANT_MYSPELL
 * or ENCHANT_ISPELL.
 * @param string $value The path of the dictionary directory.
 * @return bool true on success or false on failure
 */
function enchant_broker_set_dict_path ($broker, int $dict_type, string $value) {}

/**
 * Get the directory path for a given backend
 * @link http://www.php.net/manual/en/function.enchant-broker-get-dict-path.php
 * @param resource $broker Broker resource.
 * @param int $dict_type The type of the dictionaries, i.e. ENCHANT_MYSPELL
 * or ENCHANT_ISPELL.
 * @return bool the path of the dictionary directory on
 * success or false on failure.
 */
function enchant_broker_get_dict_path ($broker, int $dict_type) {}

/**
 * Returns a list of available dictionaries
 * @link http://www.php.net/manual/en/function.enchant-broker-list-dicts.php
 * @param resource $broker Broker resource
 * @return mixed true on success or false on failure
 */
function enchant_broker_list_dicts ($broker) {}

/**
 * create a new dictionary using a tag
 * @link http://www.php.net/manual/en/function.enchant-broker-request-dict.php
 * @param resource $broker Broker resource
 * @param string $tag A tag describing the locale, for example en_US, de_DE
 * @return resource a dictionary resource on success or false on failure.
 */
function enchant_broker_request_dict ($broker, string $tag) {}

/**
 * creates a dictionary using a PWL file
 * @link http://www.php.net/manual/en/function.enchant-broker-request-pwl-dict.php
 * @param resource $broker Broker resource
 * @param string $filename Path to the PWL file.
 * If there is no such file, a new one will be created if possible.
 * @return resource a dictionary resource on success or false on failure.
 */
function enchant_broker_request_pwl_dict ($broker, string $filename) {}

/**
 * Free a dictionary resource
 * @link http://www.php.net/manual/en/function.enchant-broker-free-dict.php
 * @param resource $dict Dictionary resource.
 * @return bool true on success or false on failure
 */
function enchant_broker_free_dict ($dict) {}

/**
 * Whether a dictionary exists or not. Using non-empty tag
 * @link http://www.php.net/manual/en/function.enchant-broker-dict-exists.php
 * @param resource $broker Broker resource
 * @param string $tag non-empty tag in the LOCALE format, ex: us_US, ch_DE, etc.
 * @return bool true when the tag exist or false when not.
 */
function enchant_broker_dict_exists ($broker, string $tag) {}

/**
 * Declares a preference of dictionaries to use for the language
 * @link http://www.php.net/manual/en/function.enchant-broker-set-ordering.php
 * @param resource $broker Broker resource
 * @param string $tag Language tag. The special "&#42;" tag can be used as a language tag
 * to declare a default ordering for any language that does not
 * explicitly declare an ordering.
 * @param string $ordering Comma delimited list of provider names
 * @return bool true on success or false on failure
 */
function enchant_broker_set_ordering ($broker, string $tag, string $ordering) {}

/**
 * Enumerates the Enchant providers
 * @link http://www.php.net/manual/en/function.enchant-broker-describe.php
 * @param resource $broker Broker resource
 * @return array true on success or false on failure
 */
function enchant_broker_describe ($broker) {}

/**
 * Check whether a word is correctly spelled or not
 * @link http://www.php.net/manual/en/function.enchant-dict-check.php
 * @param resource $dict Dictionary resource
 * @param string $word The word to check
 * @return bool true if the word is spelled correctly, false if not.
 */
function enchant_dict_check ($dict, string $word) {}

/**
 * Will return a list of values if any of those pre-conditions are not met
 * @link http://www.php.net/manual/en/function.enchant-dict-suggest.php
 * @param resource $dict Dictionary resource
 * @param string $word Word to use for the suggestions.
 * @return array Will returns an array of suggestions if the word is bad spelled.
 */
function enchant_dict_suggest ($dict, string $word) {}

/**
 * add a word to personal word list
 * @link http://www.php.net/manual/en/function.enchant-dict-add-to-personal.php
 * @param resource $dict Dictionary resource
 * @param string $word The word to add
 * @return void true on success or false on failure
 */
function enchant_dict_add_to_personal ($dict, string $word) {}

/**
 * add 'word' to this spell-checking session
 * @link http://www.php.net/manual/en/function.enchant-dict-add-to-session.php
 * @param resource $dict Dictionary resource
 * @param string $word The word to add
 * @return void 
 */
function enchant_dict_add_to_session ($dict, string $word) {}

/**
 * whether or not 'word' exists in this spelling-session
 * @link http://www.php.net/manual/en/function.enchant-dict-is-in-session.php
 * @param resource $dict Dictionary resource
 * @param string $word The word to lookup
 * @return bool true if the word exists or false
 */
function enchant_dict_is_in_session ($dict, string $word) {}

/**
 * Add a correction for a word
 * @link http://www.php.net/manual/en/function.enchant-dict-store-replacement.php
 * @param resource $dict Dictionary resource
 * @param string $mis The work to fix
 * @param string $cor The correct word
 * @return void true on success or false on failure
 */
function enchant_dict_store_replacement ($dict, string $mis, string $cor) {}

/**
 * Returns the last error of the current spelling-session
 * @link http://www.php.net/manual/en/function.enchant-dict-get-error.php
 * @param resource $dict Dictinaray resource
 * @return string the error message as string or false if no error occurred.
 */
function enchant_dict_get_error ($dict) {}

/**
 * Describes an individual dictionary
 * @link http://www.php.net/manual/en/function.enchant-dict-describe.php
 * @param resource $dict Dictionary resource
 * @return mixed true on success or false on failure
 */
function enchant_dict_describe ($dict) {}

/**
 * Check the word is correctly spelled and provide suggestions
 * @link http://www.php.net/manual/en/function.enchant-dict-quick-check.php
 * @param resource $dict Dictionary resource
 * @param string $word The word to check
 * @param array $suggestions [optional] If the word is not correctly spelled, this variable will
 * contain an array of suggestions.
 * @return bool true if the word is correctly spelled or false
 */
function enchant_dict_quick_check ($dict, string $word, array &$suggestions = null) {}


/**
 * Dictionary type for MySpell. Used with
 * enchant_broker_get_dict_path and
 * enchant_broker_set_dict_path.
 * @link http://www.php.net/manual/en/enchant.constants.php
 */
define ('ENCHANT_MYSPELL', 1);

/**
 * Dictionary type for Ispell. Used with
 * enchant_broker_get_dict_path and
 * enchant_broker_set_dict_path.
 * @link http://www.php.net/manual/en/enchant.constants.php
 */
define ('ENCHANT_ISPELL', 2);

// End of enchant v.1.1.0
