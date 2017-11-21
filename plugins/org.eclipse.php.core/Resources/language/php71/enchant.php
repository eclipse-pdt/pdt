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
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @return bool true on success or false on failure
 */
function enchant_broker_free ($broker) {}

/**
 * Returns the last error of the broker
 * @link http://www.php.net/manual/en/function.enchant-broker-get-error.php
 * @param resource $broker <p>
 * Broker resource.
 * </p>
 * @return string Return the msg string if an error was found or false
 */
function enchant_broker_get_error ($broker) {}

/**
 * Set the directory path for a given backend
 * @link http://www.php.net/manual/en/function.enchant-broker-set-dict-path.php
 * @param resource $broker <p>
 * Broker resource.
 * </p>
 * @param int $dict_type <p>
 * The type of the dictionaries, i.e. ENCHANT_MYSPELL
 * or ENCHANT_ISPELL.
 * </p>
 * @param string $value <p>
 * The path of the dictionary directory.
 * </p>
 * @return bool true on success or false on failure
 */
function enchant_broker_set_dict_path ($broker, $dict_type, $value) {}

/**
 * Get the directory path for a given backend
 * @link http://www.php.net/manual/en/function.enchant-broker-get-dict-path.php
 * @param resource $broker <p>
 * Broker resource.
 * </p>
 * @param int $dict_type <p>
 * The type of the dictionaries, i.e. ENCHANT_MYSPELL
 * or ENCHANT_ISPELL.
 * </p>
 * @return bool the path of the dictionary directory on
 * success or false on failure.
 */
function enchant_broker_get_dict_path ($broker, $dict_type) {}

/**
 * Returns a list of available dictionaries
 * @link http://www.php.net/manual/en/function.enchant-broker-list-dicts.php
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @return mixed true on success or false on failure
 */
function enchant_broker_list_dicts ($broker) {}

/**
 * create a new dictionary using a tag
 * @link http://www.php.net/manual/en/function.enchant-broker-request-dict.php
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @param string $tag <p>
 * A tag describing the locale, for example en_US, de_DE
 * </p>
 * @return resource a dictionary resource on success or false on failure.
 */
function enchant_broker_request_dict ($broker, $tag) {}

/**
 * creates a dictionary using a PWL file
 * @link http://www.php.net/manual/en/function.enchant-broker-request-pwl-dict.php
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @param string $filename <p>
 * Path to the PWL file.
 * If there is no such file, a new one will be created if possible.
 * </p>
 * @return resource a dictionary resource on success or false on failure.
 */
function enchant_broker_request_pwl_dict ($broker, $filename) {}

/**
 * Free a dictionary resource
 * @link http://www.php.net/manual/en/function.enchant-broker-free-dict.php
 * @param resource $dict <p>
 * Dictionary resource.
 * </p>
 * @return bool true on success or false on failure
 */
function enchant_broker_free_dict ($dict) {}

/**
 * Whether a dictionary exists or not. Using non-empty tag
 * @link http://www.php.net/manual/en/function.enchant-broker-dict-exists.php
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @param string $tag <p>
 * non-empty tag in the LOCALE format, ex: us_US, ch_DE, etc.
 * </p>
 * @return bool true when the tag exist or false when not.
 */
function enchant_broker_dict_exists ($broker, $tag) {}

/**
 * Declares a preference of dictionaries to use for the language
 * @link http://www.php.net/manual/en/function.enchant-broker-set-ordering.php
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @param string $tag <p>
 * Language tag. The special "*" tag can be used as a language tag
 * to declare a default ordering for any language that does not
 * explicitly declare an ordering.
 * </p>
 * @param string $ordering <p>
 * Comma delimited list of provider names
 * </p>
 * @return bool true on success or false on failure
 */
function enchant_broker_set_ordering ($broker, $tag, $ordering) {}

/**
 * Enumerates the Enchant providers
 * @link http://www.php.net/manual/en/function.enchant-broker-describe.php
 * @param resource $broker <p>
 * Broker resource
 * </p>
 * @return array true on success or false on failure
 */
function enchant_broker_describe ($broker) {}

/**
 * Check whether a word is correctly spelled or not
 * @link http://www.php.net/manual/en/function.enchant-dict-check.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $word <p>
 * The word to check
 * </p>
 * @return bool true if the word is spelled correctly, false if not.
 */
function enchant_dict_check ($dict, $word) {}

/**
 * Will return a list of values if any of those pre-conditions are not met
 * @link http://www.php.net/manual/en/function.enchant-dict-suggest.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $word <p>
 * Word to use for the suggestions.
 * </p>
 * @return array Will returns an array of suggestions if the word is bad spelled.
 */
function enchant_dict_suggest ($dict, $word) {}

/**
 * add a word to personal word list
 * @link http://www.php.net/manual/en/function.enchant-dict-add-to-personal.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $word <p>
 * The word to add
 * </p>
 * @return void true on success or false on failure
 */
function enchant_dict_add_to_personal ($dict, $word) {}

/**
 * add 'word' to this spell-checking session
 * @link http://www.php.net/manual/en/function.enchant-dict-add-to-session.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $word <p>
 * The word to add
 * </p>
 * @return void 
 */
function enchant_dict_add_to_session ($dict, $word) {}

/**
 * whether or not 'word' exists in this spelling-session
 * @link http://www.php.net/manual/en/function.enchant-dict-is-in-session.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $word <p>
 * The word to lookup
 * </p>
 * @return bool true if the word exists or false
 */
function enchant_dict_is_in_session ($dict, $word) {}

/**
 * Add a correction for a word
 * @link http://www.php.net/manual/en/function.enchant-dict-store-replacement.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $mis <p>
 * The work to fix
 * </p>
 * @param string $cor <p>
 * The correct word
 * </p>
 * @return void true on success or false on failure
 */
function enchant_dict_store_replacement ($dict, $mis, $cor) {}

/**
 * Returns the last error of the current spelling-session
 * @link http://www.php.net/manual/en/function.enchant-dict-get-error.php
 * @param resource $dict <p>
 * Dictinaray resource
 * </p>
 * @return string the error message as string or false if no error occurred.
 */
function enchant_dict_get_error ($dict) {}

/**
 * Describes an individual dictionary
 * @link http://www.php.net/manual/en/function.enchant-dict-describe.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @return mixed true on success or false on failure
 */
function enchant_dict_describe ($dict) {}

/**
 * Check the word is correctly spelled and provide suggestions
 * @link http://www.php.net/manual/en/function.enchant-dict-quick-check.php
 * @param resource $dict <p>
 * Dictionary resource
 * </p>
 * @param string $word <p>
 * The word to check
 * </p>
 * @param array $suggestions [optional] <p>
 * If the word is not correctly spelled, this variable will
 * contain an array of suggestions.
 * </p>
 * @return bool true if the word is correctly spelled or false
 */
function enchant_dict_quick_check ($dict, $word, array &$suggestions = null) {}


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
