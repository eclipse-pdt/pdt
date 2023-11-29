<?php

// Start of libxml v.8.3.0

class LibXMLError  {

	public int $level;

	public int $code;

	public int $column;

	public string $message;

	public string $file;

	public int $line;
}

/**
 * {@inheritdoc}
 * @param mixed $context
 */
function libxml_set_streams_context ($context = null): void {}

/**
 * {@inheritdoc}
 * @param bool|null $use_errors [optional]
 */
function libxml_use_internal_errors (?bool $use_errors = NULL): bool {}

/**
 * {@inheritdoc}
 */
function libxml_get_last_error (): LibXMLError|false {}

/**
 * {@inheritdoc}
 */
function libxml_get_errors (): array {}

/**
 * {@inheritdoc}
 */
function libxml_clear_errors (): void {}

/**
 * {@inheritdoc}
 * @param bool $disable [optional]
 * @deprecated 
 */
function libxml_disable_entity_loader (bool $disable = true): bool {}

/**
 * {@inheritdoc}
 * @param callable|null $resolver_function
 */
function libxml_set_external_entity_loader (?callable $resolver_function = null): bool {}

/**
 * {@inheritdoc}
 */
function libxml_get_external_entity_loader (): ?callable {}

define ('LIBXML_VERSION', 20913);
define ('LIBXML_DOTTED_VERSION', "2.9.13");
define ('LIBXML_LOADED_VERSION', 20913);
define ('LIBXML_NOENT', 2);
define ('LIBXML_DTDLOAD', 4);
define ('LIBXML_DTDATTR', 8);
define ('LIBXML_DTDVALID', 16);
define ('LIBXML_NOERROR', 32);
define ('LIBXML_NOWARNING', 64);
define ('LIBXML_NOBLANKS', 256);
define ('LIBXML_XINCLUDE', 1024);
define ('LIBXML_NSCLEAN', 8192);
define ('LIBXML_NOCDATA', 16384);
define ('LIBXML_NONET', 2048);
define ('LIBXML_PEDANTIC', 128);
define ('LIBXML_COMPACT', 65536);
define ('LIBXML_NOXMLDECL', 2);
define ('LIBXML_PARSEHUGE', 524288);
define ('LIBXML_BIGLINES', 4194304);
define ('LIBXML_NOEMPTYTAG', 4);
define ('LIBXML_SCHEMA_CREATE', 1);
define ('LIBXML_HTML_NOIMPLIED', 8192);
define ('LIBXML_HTML_NODEFDTD', 4);
define ('LIBXML_ERR_NONE', 0);
define ('LIBXML_ERR_WARNING', 1);
define ('LIBXML_ERR_ERROR', 2);
define ('LIBXML_ERR_FATAL', 3);

// End of libxml v.8.3.0
