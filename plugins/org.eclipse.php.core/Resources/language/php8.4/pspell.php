<?php

// Start of pspell v.8.3.0

namespace PSpell {

final class Dictionary  {
}

final class Config  {
}


}


namespace {

/**
 * {@inheritdoc}
 * @param string $language
 * @param string $spelling [optional]
 * @param string $jargon [optional]
 * @param string $encoding [optional]
 * @param int $mode [optional]
 */
function pspell_new (string $language, string $spelling = '', string $jargon = '', string $encoding = '', int $mode = 0): PSpell\Dictionary|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param string $language
 * @param string $spelling [optional]
 * @param string $jargon [optional]
 * @param string $encoding [optional]
 * @param int $mode [optional]
 */
function pspell_new_personal (string $filename, string $language, string $spelling = '', string $jargon = '', string $encoding = '', int $mode = 0): PSpell\Dictionary|false {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 */
function pspell_new_config (PSpell\Config $config): PSpell\Dictionary|false {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 * @param string $word
 */
function pspell_check (PSpell\Dictionary $dictionary, string $word): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 * @param string $word
 */
function pspell_suggest (PSpell\Dictionary $dictionary, string $word): array|false {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 * @param string $misspelled
 * @param string $correct
 */
function pspell_store_replacement (PSpell\Dictionary $dictionary, string $misspelled, string $correct): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 * @param string $word
 */
function pspell_add_to_personal (PSpell\Dictionary $dictionary, string $word): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 * @param string $word
 */
function pspell_add_to_session (PSpell\Dictionary $dictionary, string $word): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 */
function pspell_clear_session (PSpell\Dictionary $dictionary): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Dictionary $dictionary
 */
function pspell_save_wordlist (PSpell\Dictionary $dictionary): bool {}

/**
 * {@inheritdoc}
 * @param string $language
 * @param string $spelling [optional]
 * @param string $jargon [optional]
 * @param string $encoding [optional]
 */
function pspell_config_create (string $language, string $spelling = '', string $jargon = '', string $encoding = ''): PSpell\Config {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param bool $allow
 */
function pspell_config_runtogether (PSpell\Config $config, bool $allow): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param int $mode
 */
function pspell_config_mode (PSpell\Config $config, int $mode): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param int $min_length
 */
function pspell_config_ignore (PSpell\Config $config, int $min_length): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param string $filename
 */
function pspell_config_personal (PSpell\Config $config, string $filename): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param string $directory
 */
function pspell_config_dict_dir (PSpell\Config $config, string $directory): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param string $directory
 */
function pspell_config_data_dir (PSpell\Config $config, string $directory): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param string $filename
 */
function pspell_config_repl (PSpell\Config $config, string $filename): bool {}

/**
 * {@inheritdoc}
 * @param PSpell\Config $config
 * @param bool $save
 */
function pspell_config_save_repl (PSpell\Config $config, bool $save): bool {}

define ('PSPELL_FAST', 1);
define ('PSPELL_NORMAL', 2);
define ('PSPELL_BAD_SPELLERS', 3);
define ('PSPELL_RUN_TOGETHER', 8);


}

// End of pspell v.8.3.0
