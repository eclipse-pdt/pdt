<?php

// Start of uri v.8.5.0-dev

namespace Uri\Rfc3986 {

final readonly class Uri  {

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param \Uri\Rfc3986\Uri|null $baseUrl [optional]
	 */
	public static function parse (string $uri, ?\Uri\Rfc3986\Uri $baseUrl = NULL): ?static {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param \Uri\Rfc3986\Uri|null $baseUrl [optional]
	 */
	public function __construct (string $uri, ?\Uri\Rfc3986\Uri $baseUrl = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getScheme (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawScheme (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $scheme
	 */
	public function withScheme (?string $scheme = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getUserInfo (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawUserInfo (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $userinfo
	 */
	public function withUserInfo (?string $userinfo = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getUsername (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawUsername (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPassword (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawPassword (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getHost (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawHost (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $host
	 */
	public function withHost (?string $host = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getPort (): ?int {}

	/**
	 * {@inheritdoc}
	 * @param int|null $port
	 */
	public function withPort (?int $port = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawPath (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $path
	 */
	public function withPath (string $path): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getQuery (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawQuery (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $query
	 */
	public function withQuery (?string $query = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getFragment (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRawFragment (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $fragment
	 */
	public function withFragment (?string $fragment = null): static {}

	/**
	 * {@inheritdoc}
	 * @param \Uri\Rfc3986\Uri $uri
	 * @param \Uri\UriComparisonMode $comparisonMode [optional]
	 */
	public function equals (\Uri\Rfc3986\Uri $uri, \Uri\UriComparisonMode $comparisonMode = \Uri\UriComparisonMode::ExcludeFragment): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function toRawString (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 */
	public function resolve (string $uri): static {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo (): array {}

}


}


namespace Uri\WhatWg {

final readonly class Url  {

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param \Uri\WhatWg\Url|null $baseUrl [optional]
	 * @param mixed $errors [optional]
	 */
	public static function parse (string $uri, ?\Uri\WhatWg\Url $baseUrl = NULL, &$errors = NULL): ?static {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param \Uri\WhatWg\Url|null $baseUrl [optional]
	 * @param mixed $softErrors [optional]
	 */
	public function __construct (string $uri, ?\Uri\WhatWg\Url $baseUrl = NULL, &$softErrors = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getScheme (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $scheme
	 */
	public function withScheme (string $scheme): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getUsername (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $username
	 */
	public function withUsername (?string $username = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getPassword (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $password
	 */
	public function withPassword (?string $password = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getAsciiHost (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getUnicodeHost (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $host
	 */
	public function withHost (?string $host = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getPort (): ?int {}

	/**
	 * {@inheritdoc}
	 * @param int|null $port
	 */
	public function withPort (?int $port = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $path
	 */
	public function withPath (string $path): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getQuery (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $query
	 */
	public function withQuery (?string $query = null): static {}

	/**
	 * {@inheritdoc}
	 */
	public function getFragment (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $fragment
	 */
	public function withFragment (?string $fragment = null): static {}

	/**
	 * {@inheritdoc}
	 * @param \Uri\WhatWg\Url $url
	 * @param \Uri\UriComparisonMode $comparisonMode [optional]
	 */
	public function equals (\Uri\WhatWg\Url $url, \Uri\UriComparisonMode $comparisonMode = \Uri\UriComparisonMode::ExcludeFragment): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function toAsciiString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function toUnicodeString (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $uri
	 * @param mixed $softErrors [optional]
	 */
	public function resolve (string $uri, &$softErrors = NULL): static {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo (): array {}

}


}


namespace Uri {

enum UriComparisonMode implements \UnitEnum {
	const IncludeFragment = ;
	const ExcludeFragment = ;


	public readonly string $name;

	/**
	 * {@inheritdoc}
	 */
	public static function cases (): array {}

}

class UriException extends \Exception implements \Throwable, \Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

class InvalidUriException extends \Uri\UriException implements \Stringable, \Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param \Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?\Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}


}


namespace Uri\WhatWg {

class InvalidUrlException extends \Uri\InvalidUriException implements \Throwable, \Stringable {

	public readonly array $errors;

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param array $errors [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', array $errors = array (
), int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return \Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

final readonly class UrlValidationError  {

	public readonly string $context;

	public readonly \Uri\WhatWg\UrlValidationErrorType $type;

	public readonly bool $failure;

	/**
	 * {@inheritdoc}
	 * @param string $context
	 * @param \Uri\WhatWg\UrlValidationErrorType $type
	 * @param bool $failure
	 */
	public function __construct (string $context, \Uri\WhatWg\UrlValidationErrorType $type, bool $failure) {}

}

enum UrlValidationErrorType implements \UnitEnum {
	const DomainToAscii = ;
	const DomainToUnicode = ;
	const DomainInvalidCodePoint = ;
	const HostInvalidCodePoint = ;
	const Ipv4EmptyPart = ;
	const Ipv4TooManyParts = ;
	const Ipv4NonNumericPart = ;
	const Ipv4NonDecimalPart = ;
	const Ipv4OutOfRangePart = ;
	const Ipv6Unclosed = ;
	const Ipv6InvalidCompression = ;
	const Ipv6TooManyPieces = ;
	const Ipv6MultipleCompression = ;
	const Ipv6InvalidCodePoint = ;
	const Ipv6TooFewPieces = ;
	const Ipv4InIpv6TooManyPieces = ;
	const Ipv4InIpv6InvalidCodePoint = ;
	const Ipv4InIpv6OutOfRangePart = ;
	const Ipv4InIpv6TooFewParts = ;
	const InvalidUrlUnit = ;
	const SpecialSchemeMissingFollowingSolidus = ;
	const MissingSchemeNonRelativeUrl = ;
	const InvalidReverseSoldius = ;
	const InvalidCredentials = ;
	const HostMissing = ;
	const PortOutOfRange = ;
	const PortInvalid = ;
	const FileInvalidWindowsDriveLetter = ;
	const FileInvalidWindowsDriveLetterHost = ;


	public readonly string $name;

	/**
	 * {@inheritdoc}
	 */
	public static function cases (): array {}

}

}

// End of uri v.8.5.0-dev
