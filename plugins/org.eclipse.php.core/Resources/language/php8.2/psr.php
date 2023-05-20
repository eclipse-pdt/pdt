<?php

// Start of psr v.1.2.0

namespace PsrExt\Cache {

interface CacheException  {
}

interface CacheException  {
}

interface CacheItemInterface  {

	abstract public function getKey ()

	abstract public function get ()

	abstract public function isHit ()

	/**
	 * @param mixed $value
	 */
	abstract public function set ($value = null)

	/**
	 * @param mixed $expiration
	 */
	abstract public function expiresAt ($expiration = null)

	/**
	 * @param mixed $time
	 */
	abstract public function expiresAfter ($time = null)

}

interface CacheItemInterface  {

	abstract public function getKey ()

	abstract public function get ()

	abstract public function isHit ()

	/**
	 * @param mixed $value
	 */
	abstract public function set ($value = null)

	/**
	 * @param mixed $expiration
	 */
	abstract public function expiresAt ($expiration = null)

	/**
	 * @param mixed $time
	 */
	abstract public function expiresAfter ($time = null)

}

interface CacheItemPoolInterface  {

	/**
	 * @param mixed $key
	 */
	abstract public function getItem ($key = null)

	/**
	 * @param array[] $keys [optional]
	 */
	abstract public function getItems (array $keys = null)

	/**
	 * @param mixed $key
	 */
	abstract public function hasItem ($key = null)

	abstract public function clear ()

	/**
	 * @param mixed $key
	 */
	abstract public function deleteItem ($key = null)

	/**
	 * @param array[] $keys
	 */
	abstract public function deleteItems (array $keys)

	/**
	 * @param Psr\Cache\CacheItemInterface $logger
	 */
	abstract public function save (Psr\Cache\CacheItemInterface $logger)

	/**
	 * @param Psr\Cache\CacheItemInterface $logger
	 */
	abstract public function saveDeferred (Psr\Cache\CacheItemInterface $logger)

	abstract public function commit ()

}

interface CacheItemPoolInterface  {

	/**
	 * @param mixed $key
	 */
	abstract public function getItem ($key = null)

	/**
	 * @param array[] $keys [optional]
	 */
	abstract public function getItems (array $keys = null)

	/**
	 * @param mixed $key
	 */
	abstract public function hasItem ($key = null)

	abstract public function clear ()

	/**
	 * @param mixed $key
	 */
	abstract public function deleteItem ($key = null)

	/**
	 * @param array[] $keys
	 */
	abstract public function deleteItems (array $keys)

	/**
	 * @param Psr\Cache\CacheItemInterface $logger
	 */
	abstract public function save (Psr\Cache\CacheItemInterface $logger)

	/**
	 * @param Psr\Cache\CacheItemInterface $logger
	 */
	abstract public function saveDeferred (Psr\Cache\CacheItemInterface $logger)

	abstract public function commit ()

}

interface InvalidArgumentException extends \PsrExt\Cache\CacheException {
}

interface InvalidArgumentException extends \PsrExt\Cache\CacheException {
}


}


namespace PsrExt\Container {

interface ContainerExceptionInterface  {
}

interface ContainerExceptionInterface  {
}

interface ContainerInterface  {

	/**
	 * @param string $id
	 */
	abstract public function get (string $id)

	/**
	 * @param string $id
	 */
	abstract public function has (string $id)

}

interface ContainerInterface  {

	/**
	 * @param string $id
	 */
	abstract public function get (string $id)

	/**
	 * @param string $id
	 */
	abstract public function has (string $id)

}

interface NotFoundExceptionInterface extends \PsrExt\Container\ContainerExceptionInterface {
}

interface NotFoundExceptionInterface extends \PsrExt\Container\ContainerExceptionInterface {
}


}


namespace PsrExt\Http\Message {

interface MessageInterface  {

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface MessageInterface  {

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface RequestInterface extends \PsrExt\Http\Message\MessageInterface {

	abstract public function getRequestTarget ()

	/**
	 * @param mixed $requestTarget
	 */
	abstract public function withRequestTarget ($requestTarget = null)

	abstract public function getMethod ()

	/**
	 * @param mixed $method
	 */
	abstract public function withMethod ($method = null)

	abstract public function getUri ()

	/**
	 * @param Psr\Http\Message\UriInterface $uri
	 * @param mixed $preserveHost [optional]
	 */
	abstract public function withUri (Psr\Http\Message\UriInterface $uri, $preserveHost = null)

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface RequestInterface extends \PsrExt\Http\Message\MessageInterface {

	abstract public function getRequestTarget ()

	/**
	 * @param mixed $requestTarget
	 */
	abstract public function withRequestTarget ($requestTarget = null)

	abstract public function getMethod ()

	/**
	 * @param mixed $method
	 */
	abstract public function withMethod ($method = null)

	abstract public function getUri ()

	/**
	 * @param Psr\Http\Message\UriInterface $uri
	 * @param mixed $preserveHost [optional]
	 */
	abstract public function withUri (Psr\Http\Message\UriInterface $uri, $preserveHost = null)

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface ResponseInterface extends \PsrExt\Http\Message\MessageInterface {

	abstract public function getStatusCode ()

	/**
	 * @param mixed $code
	 * @param mixed $reasonPhrase [optional]
	 */
	abstract public function withStatus ($code = null, $reasonPhrase = null)

	abstract public function getReasonPhrase ()

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface ResponseInterface extends \PsrExt\Http\Message\MessageInterface {

	abstract public function getStatusCode ()

	/**
	 * @param mixed $code
	 * @param mixed $reasonPhrase [optional]
	 */
	abstract public function withStatus ($code = null, $reasonPhrase = null)

	abstract public function getReasonPhrase ()

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface ServerRequestInterface extends \PsrExt\Http\Message\RequestInterface, \PsrExt\Http\Message\MessageInterface {

	abstract public function getServerParams ()

	abstract public function getCookieParams ()

	/**
	 * @param array[] $cookies
	 */
	abstract public function withCookieParams (array $cookies)

	abstract public function getQueryParams ()

	/**
	 * @param array[] $query
	 */
	abstract public function withQueryParams (array $query)

	abstract public function getUploadedFiles ()

	/**
	 * @param array[] $uploadedFiles
	 */
	abstract public function withUploadedFiles (array $uploadedFiles)

	abstract public function getParsedBody ()

	/**
	 * @param mixed $parsedBody
	 */
	abstract public function withParsedBody ($parsedBody = null)

	abstract public function getAttributes ()

	/**
	 * @param mixed $name
	 * @param mixed $default [optional]
	 */
	abstract public function getAttribute ($name = null, $default = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAttribute ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutAttribute ($name = null)

	abstract public function getRequestTarget ()

	/**
	 * @param mixed $requestTarget
	 */
	abstract public function withRequestTarget ($requestTarget = null)

	abstract public function getMethod ()

	/**
	 * @param mixed $method
	 */
	abstract public function withMethod ($method = null)

	abstract public function getUri ()

	/**
	 * @param Psr\Http\Message\UriInterface $uri
	 * @param mixed $preserveHost [optional]
	 */
	abstract public function withUri (Psr\Http\Message\UriInterface $uri, $preserveHost = null)

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface ServerRequestInterface extends \PsrExt\Http\Message\RequestInterface, \PsrExt\Http\Message\MessageInterface {

	abstract public function getServerParams ()

	abstract public function getCookieParams ()

	/**
	 * @param array[] $cookies
	 */
	abstract public function withCookieParams (array $cookies)

	abstract public function getQueryParams ()

	/**
	 * @param array[] $query
	 */
	abstract public function withQueryParams (array $query)

	abstract public function getUploadedFiles ()

	/**
	 * @param array[] $uploadedFiles
	 */
	abstract public function withUploadedFiles (array $uploadedFiles)

	abstract public function getParsedBody ()

	/**
	 * @param mixed $parsedBody
	 */
	abstract public function withParsedBody ($parsedBody = null)

	abstract public function getAttributes ()

	/**
	 * @param mixed $name
	 * @param mixed $default [optional]
	 */
	abstract public function getAttribute ($name = null, $default = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAttribute ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutAttribute ($name = null)

	abstract public function getRequestTarget ()

	/**
	 * @param mixed $requestTarget
	 */
	abstract public function withRequestTarget ($requestTarget = null)

	abstract public function getMethod ()

	/**
	 * @param mixed $method
	 */
	abstract public function withMethod ($method = null)

	abstract public function getUri ()

	/**
	 * @param Psr\Http\Message\UriInterface $uri
	 * @param mixed $preserveHost [optional]
	 */
	abstract public function withUri (Psr\Http\Message\UriInterface $uri, $preserveHost = null)

	abstract public function getProtocolVersion ()

	/**
	 * @param mixed $version
	 */
	abstract public function withProtocolVersion ($version = null)

	abstract public function getHeaders ()

	/**
	 * @param mixed $name
	 */
	abstract public function hasHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeader ($name = null)

	/**
	 * @param mixed $name
	 */
	abstract public function getHeaderLine ($name = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 * @param mixed $value
	 */
	abstract public function withAddedHeader ($name = null, $value = null)

	/**
	 * @param mixed $name
	 */
	abstract public function withoutHeader ($name = null)

	abstract public function getBody ()

	/**
	 * @param Psr\Http\Message\StreamInterface $body
	 */
	abstract public function withBody (Psr\Http\Message\StreamInterface $body)

}

interface StreamInterface extends \Stringable {

	abstract public function __toString (): string

	abstract public function close ()

	abstract public function detach ()

	abstract public function getSize ()

	abstract public function tell ()

	abstract public function eof ()

	abstract public function isSeekable ()

	/**
	 * @param mixed $offset
	 * @param mixed $whence [optional]
	 */
	abstract public function seek ($offset = null, $whence = null)

	abstract public function rewind ()

	abstract public function isWritable ()

	/**
	 * @param mixed $string
	 */
	abstract public function write ($string = null)

	abstract public function isReadable ()

	/**
	 * @param mixed $length
	 */
	abstract public function read ($length = null)

	abstract public function getContents ()

	/**
	 * @param mixed $key [optional]
	 */
	abstract public function getMetadata ($key = null)

}

interface StreamInterface extends \Stringable {

	abstract public function __toString (): string

	abstract public function close ()

	abstract public function detach ()

	abstract public function getSize ()

	abstract public function tell ()

	abstract public function eof ()

	abstract public function isSeekable ()

	/**
	 * @param mixed $offset
	 * @param mixed $whence [optional]
	 */
	abstract public function seek ($offset = null, $whence = null)

	abstract public function rewind ()

	abstract public function isWritable ()

	/**
	 * @param mixed $string
	 */
	abstract public function write ($string = null)

	abstract public function isReadable ()

	/**
	 * @param mixed $length
	 */
	abstract public function read ($length = null)

	abstract public function getContents ()

	/**
	 * @param mixed $key [optional]
	 */
	abstract public function getMetadata ($key = null)

}

interface UploadedFileInterface  {

	abstract public function getStream ()

	/**
	 * @param mixed $targetPath
	 */
	abstract public function moveTo ($targetPath = null)

	abstract public function getSize ()

	abstract public function getError ()

	abstract public function getClientFilename ()

	abstract public function getClientMediaType ()

}

interface UploadedFileInterface  {

	abstract public function getStream ()

	/**
	 * @param mixed $targetPath
	 */
	abstract public function moveTo ($targetPath = null)

	abstract public function getSize ()

	abstract public function getError ()

	abstract public function getClientFilename ()

	abstract public function getClientMediaType ()

}

interface UriInterface extends \Stringable {

	abstract public function getScheme ()

	abstract public function getAuthority ()

	abstract public function getUserInfo ()

	abstract public function getHost ()

	abstract public function getPort ()

	abstract public function getPath ()

	abstract public function getQuery ()

	abstract public function getFragment ()

	/**
	 * @param mixed $scheme
	 */
	abstract public function withScheme ($scheme = null)

	/**
	 * @param mixed $user
	 * @param mixed $password [optional]
	 */
	abstract public function withUserInfo ($user = null, $password = null)

	/**
	 * @param mixed $host
	 */
	abstract public function withHost ($host = null)

	/**
	 * @param mixed $port
	 */
	abstract public function withPort ($port = null)

	/**
	 * @param mixed $path
	 */
	abstract public function withPath ($path = null)

	/**
	 * @param mixed $query
	 */
	abstract public function withQuery ($query = null)

	/**
	 * @param mixed $fragment
	 */
	abstract public function withFragment ($fragment = null)

	abstract public function __toString (): string

}

interface UriInterface extends \Stringable {

	abstract public function getScheme ()

	abstract public function getAuthority ()

	abstract public function getUserInfo ()

	abstract public function getHost ()

	abstract public function getPort ()

	abstract public function getPath ()

	abstract public function getQuery ()

	abstract public function getFragment ()

	/**
	 * @param mixed $scheme
	 */
	abstract public function withScheme ($scheme = null)

	/**
	 * @param mixed $user
	 * @param mixed $password [optional]
	 */
	abstract public function withUserInfo ($user = null, $password = null)

	/**
	 * @param mixed $host
	 */
	abstract public function withHost ($host = null)

	/**
	 * @param mixed $port
	 */
	abstract public function withPort ($port = null)

	/**
	 * @param mixed $path
	 */
	abstract public function withPath ($path = null)

	/**
	 * @param mixed $query
	 */
	abstract public function withQuery ($query = null)

	/**
	 * @param mixed $fragment
	 */
	abstract public function withFragment ($fragment = null)

	abstract public function __toString (): string

}


}


namespace PsrExt\Link {

interface LinkInterface  {

	abstract public function getHref ()

	abstract public function isTemplated ()

	abstract public function getRels ()

	abstract public function getAttributes ()

}

interface LinkInterface  {

	abstract public function getHref ()

	abstract public function isTemplated ()

	abstract public function getRels ()

	abstract public function getAttributes ()

}

interface LinkProviderInterface  {

	abstract public function getLinks ()

	/**
	 * @param mixed $rel
	 */
	abstract public function getLinksByRel ($rel = null)

}

interface LinkProviderInterface  {

	abstract public function getLinks ()

	/**
	 * @param mixed $rel
	 */
	abstract public function getLinksByRel ($rel = null)

}

interface EvolvableLinkInterface extends \PsrExt\Link\LinkInterface {

	/**
	 * @param mixed $href
	 */
	abstract public function withHref ($href = null)

	/**
	 * @param mixed $rel
	 */
	abstract public function withRel ($rel = null)

	/**
	 * @param mixed $rel
	 */
	abstract public function withoutRel ($rel = null)

	/**
	 * @param mixed $attribute
	 * @param mixed $value
	 */
	abstract public function withAttribute ($attribute = null, $value = null)

	/**
	 * @param mixed $attribute
	 */
	abstract public function withoutAttribute ($attribute = null)

	abstract public function getHref ()

	abstract public function isTemplated ()

	abstract public function getRels ()

	abstract public function getAttributes ()

}

interface EvolvableLinkInterface extends \PsrExt\Link\LinkInterface {

	/**
	 * @param mixed $href
	 */
	abstract public function withHref ($href = null)

	/**
	 * @param mixed $rel
	 */
	abstract public function withRel ($rel = null)

	/**
	 * @param mixed $rel
	 */
	abstract public function withoutRel ($rel = null)

	/**
	 * @param mixed $attribute
	 * @param mixed $value
	 */
	abstract public function withAttribute ($attribute = null, $value = null)

	/**
	 * @param mixed $attribute
	 */
	abstract public function withoutAttribute ($attribute = null)

	abstract public function getHref ()

	abstract public function isTemplated ()

	abstract public function getRels ()

	abstract public function getAttributes ()

}

interface EvolvableLinkProviderInterface extends \PsrExt\Link\LinkProviderInterface {

	/**
	 * @param Psr\Link\LinkInterface $link
	 */
	abstract public function withLink (Psr\Link\LinkInterface $link)

	/**
	 * @param Psr\Link\LinkInterface $link
	 */
	abstract public function withoutLink (Psr\Link\LinkInterface $link)

	abstract public function getLinks ()

	/**
	 * @param mixed $rel
	 */
	abstract public function getLinksByRel ($rel = null)

}

interface EvolvableLinkProviderInterface extends \PsrExt\Link\LinkProviderInterface {

	/**
	 * @param Psr\Link\LinkInterface $link
	 */
	abstract public function withLink (Psr\Link\LinkInterface $link)

	/**
	 * @param Psr\Link\LinkInterface $link
	 */
	abstract public function withoutLink (Psr\Link\LinkInterface $link)

	abstract public function getLinks ()

	/**
	 * @param mixed $rel
	 */
	abstract public function getLinksByRel ($rel = null)

}


}


namespace PsrExt\Log {

class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

class LogLevel  {
	const EMERGENCY = "emergency";
	const ALERT = "alert";
	const CRITICAL = "critical";
	const ERROR = "error";
	const WARNING = "warning";
	const NOTICE = "notice";
	const INFO = "info";
	const DEBUG = "debug";

}

class LogLevel  {
	const EMERGENCY = "emergency";
	const ALERT = "alert";
	const CRITICAL = "critical";
	const ERROR = "error";
	const WARNING = "warning";
	const NOTICE = "notice";
	const INFO = "info";
	const DEBUG = "debug";

}

interface LoggerInterface  {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function emergency ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function alert ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function critical ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function error ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function warning ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function notice ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function info ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function debug ($message = null, array $context = 'Array')

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

interface LoggerInterface  {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function emergency ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function alert ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function critical ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function error ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function warning ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function notice ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function info ($message = null, array $context = 'Array')

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function debug ($message = null, array $context = 'Array')

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

interface LoggerAwareInterface  {

	/**
	 * @param Psr\Log\LoggerInterface $logger
	 */
	abstract public function setLogger (Psr\Log\LoggerInterface $logger)

}

interface LoggerAwareInterface  {

	/**
	 * @param Psr\Log\LoggerInterface $logger
	 */
	abstract public function setLogger (Psr\Log\LoggerInterface $logger)

}

abstract class AbstractLogger implements \PsrExt\Log\LoggerInterface {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

abstract class AbstractLogger implements \PsrExt\Log\LoggerInterface {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

class NullLogger extends \PsrExt\Log\AbstractLogger implements \PsrExt\Log\LoggerInterface {

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function log ($level = null, $message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

}

class NullLogger extends \PsrExt\Log\AbstractLogger implements \PsrExt\Log\LoggerInterface {

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function log ($level = null, $message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

}

abstract trait LoggerTrait  {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

abstract trait LoggerTrait  {

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function emergency ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function alert ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function critical ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function error ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function warning ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function notice ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function info ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	public function debug ($message = null, array $context = 'Array') {}

	/**
	 * @param mixed $level
	 * @param mixed $message
	 * @param array[] $context [optional]
	 */
	abstract public function log ($level = null, $message = null, array $context = 'Array')

}

trait LoggerAwareTrait  {
	protected $logger;


	/**
	 * @param Psr\Log\LoggerInterface $logger
	 */
	public function setLogger (Psr\Log\LoggerInterface $logger) {}

}

trait LoggerAwareTrait  {
	protected $logger;


	/**
	 * @param Psr\Log\LoggerInterface $logger
	 */
	public function setLogger (Psr\Log\LoggerInterface $logger) {}

}


}


namespace PsrExt\SimpleCache {

interface CacheException  {
}

interface CacheException  {
}

interface CacheInterface  {

	/**
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	abstract public function get ($key = null, $default = null)

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $ttl [optional]
	 */
	abstract public function set ($key = null, $value = null, $ttl = null)

	/**
	 * @param mixed $key
	 */
	abstract public function delete ($key = null)

	abstract public function clear ()

	/**
	 * @param mixed $keys
	 * @param mixed $default [optional]
	 */
	abstract public function getMultiple ($keys = null, $default = null)

	/**
	 * @param mixed $values
	 * @param mixed $ttl [optional]
	 */
	abstract public function setMultiple ($values = null, $ttl = null)

	/**
	 * @param mixed $keys
	 */
	abstract public function deleteMultiple ($keys = null)

	/**
	 * @param mixed $key
	 */
	abstract public function has ($key = null)

}

interface CacheInterface  {

	/**
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	abstract public function get ($key = null, $default = null)

	/**
	 * @param mixed $key
	 * @param mixed $value
	 * @param mixed $ttl [optional]
	 */
	abstract public function set ($key = null, $value = null, $ttl = null)

	/**
	 * @param mixed $key
	 */
	abstract public function delete ($key = null)

	abstract public function clear ()

	/**
	 * @param mixed $keys
	 * @param mixed $default [optional]
	 */
	abstract public function getMultiple ($keys = null, $default = null)

	/**
	 * @param mixed $values
	 * @param mixed $ttl [optional]
	 */
	abstract public function setMultiple ($values = null, $ttl = null)

	/**
	 * @param mixed $keys
	 */
	abstract public function deleteMultiple ($keys = null)

	/**
	 * @param mixed $key
	 */
	abstract public function has ($key = null)

}

interface InvalidArgumentException extends \PsrExt\SimpleCache\CacheException {
}

interface InvalidArgumentException extends \PsrExt\SimpleCache\CacheException {
}


}


namespace PsrExt\Http\Server {

interface RequestHandlerInterface  {

	/**
	 * @param Psr\Http\Message\ServerRequestInterface $request
	 */
	abstract public function handle (Psr\Http\Message\ServerRequestInterface $request): Psr\Http\Message\ResponseInterface

}

interface RequestHandlerInterface  {

	/**
	 * @param Psr\Http\Message\ServerRequestInterface $request
	 */
	abstract public function handle (Psr\Http\Message\ServerRequestInterface $request): Psr\Http\Message\ResponseInterface

}

interface MiddlewareInterface  {

	/**
	 * @param Psr\Http\Message\ServerRequestInterface $request
	 * @param Psr\Http\Server\RequestHandlerInterface $handler
	 */
	abstract public function process (Psr\Http\Message\ServerRequestInterface $request, Psr\Http\Server\RequestHandlerInterface $handler): Psr\Http\Message\ResponseInterface

}

interface MiddlewareInterface  {

	/**
	 * @param Psr\Http\Message\ServerRequestInterface $request
	 * @param Psr\Http\Server\RequestHandlerInterface $handler
	 */
	abstract public function process (Psr\Http\Message\ServerRequestInterface $request, Psr\Http\Server\RequestHandlerInterface $handler): Psr\Http\Message\ResponseInterface

}


}


namespace PsrExt\Http\Message {

interface RequestFactoryInterface  {

	/**
	 * @param string $method
	 * @param mixed $uri
	 */
	abstract public function createRequest (string $method, $uri = null): Psr\Http\Message\RequestInterface

}

interface RequestFactoryInterface  {

	/**
	 * @param string $method
	 * @param mixed $uri
	 */
	abstract public function createRequest (string $method, $uri = null): Psr\Http\Message\RequestInterface

}

interface ResponseFactoryInterface  {

	/**
	 * @param int $code [optional]
	 * @param string $reasonPhrase [optional]
	 */
	abstract public function createResponse (int $code = null, string $reasonPhrase = null): Psr\Http\Message\ResponseInterface

}

interface ResponseFactoryInterface  {

	/**
	 * @param int $code [optional]
	 * @param string $reasonPhrase [optional]
	 */
	abstract public function createResponse (int $code = null, string $reasonPhrase = null): Psr\Http\Message\ResponseInterface

}

interface ServerRequestFactoryInterface  {

	/**
	 * @param string $method
	 * @param mixed $uri
	 * @param array[] $serverParams [optional]
	 */
	abstract public function createServerRequest (string $method, $uri = null, array $serverParams = null): Psr\Http\Message\ServerRequestInterface

}

interface ServerRequestFactoryInterface  {

	/**
	 * @param string $method
	 * @param mixed $uri
	 * @param array[] $serverParams [optional]
	 */
	abstract public function createServerRequest (string $method, $uri = null, array $serverParams = null): Psr\Http\Message\ServerRequestInterface

}

interface StreamFactoryInterface  {

	/**
	 * @param string $content [optional]
	 */
	abstract public function createStream (string $content = null): Psr\Http\Message\StreamInterface

	/**
	 * @param string $filename
	 * @param string $mode [optional]
	 */
	abstract public function createStreamFromFile (string $filename, string $mode = null): Psr\Http\Message\StreamInterface

	/**
	 * @param mixed $resouce
	 */
	abstract public function createStreamFromResource ($resouce = null): Psr\Http\Message\StreamInterface

}

interface StreamFactoryInterface  {

	/**
	 * @param string $content [optional]
	 */
	abstract public function createStream (string $content = null): Psr\Http\Message\StreamInterface

	/**
	 * @param string $filename
	 * @param string $mode [optional]
	 */
	abstract public function createStreamFromFile (string $filename, string $mode = null): Psr\Http\Message\StreamInterface

	/**
	 * @param mixed $resouce
	 */
	abstract public function createStreamFromResource ($resouce = null): Psr\Http\Message\StreamInterface

}

interface UploadedFileFactoryInterface  {

	/**
	 * @param Psr\Http\Message\StreamInterface $stream
	 * @param int|null $size [optional]
	 * @param int $error [optional]
	 * @param string|null $clientFilename [optional]
	 * @param string|null $clientMediaType [optional]
	 */
	abstract public function createUploadedFile (Psr\Http\Message\StreamInterface $stream, int|null $size = null, int $error = null, string|null $clientFilename = null, string|null $clientMediaType = null): Psr\Http\Message\UploadedFileInterface

}

interface UploadedFileFactoryInterface  {

	/**
	 * @param Psr\Http\Message\StreamInterface $stream
	 * @param int|null $size [optional]
	 * @param int $error [optional]
	 * @param string|null $clientFilename [optional]
	 * @param string|null $clientMediaType [optional]
	 */
	abstract public function createUploadedFile (Psr\Http\Message\StreamInterface $stream, int|null $size = null, int $error = null, string|null $clientFilename = null, string|null $clientMediaType = null): Psr\Http\Message\UploadedFileInterface

}

interface UriFactoryInterface  {

	/**
	 * @param string $uri [optional]
	 */
	abstract public function createUri (string $uri = null): Psr\Http\Message\UriInterface

}

interface UriFactoryInterface  {

	/**
	 * @param string $uri [optional]
	 */
	abstract public function createUri (string $uri = null): Psr\Http\Message\UriInterface

}


}


namespace PsrExt\Http\Client {

interface ClientInterface  {

	/**
	 * @param Psr\Http\Message\RequestInterface $request
	 */
	abstract public function sendRequest (Psr\Http\Message\RequestInterface $request): Psr\Http\Message\ResponseInterface

}

interface ClientInterface  {

	/**
	 * @param Psr\Http\Message\RequestInterface $request
	 */
	abstract public function sendRequest (Psr\Http\Message\RequestInterface $request): Psr\Http\Message\ResponseInterface

}

interface ClientExceptionInterface extends \Throwable, \Stringable {

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface ClientExceptionInterface extends \Throwable, \Stringable {

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface NetworkExceptionInterface extends \PsrExt\Http\Client\ClientExceptionInterface, \Stringable, \Throwable {

	abstract public function getRequest (): Psr\Http\Message\RequestInterface

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface NetworkExceptionInterface extends \PsrExt\Http\Client\ClientExceptionInterface, \Stringable, \Throwable {

	abstract public function getRequest (): Psr\Http\Message\RequestInterface

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface RequestExceptionInterface extends \PsrExt\Http\Client\ClientExceptionInterface, \Stringable, \Throwable {

	abstract public function getRequest (): Psr\Http\Message\RequestInterface

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

interface RequestExceptionInterface extends \PsrExt\Http\Client\ClientExceptionInterface, \Stringable, \Throwable {

	abstract public function getRequest (): Psr\Http\Message\RequestInterface

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}


}


namespace PsrExt\EventDispatcher {

interface EventDispatcherInterface  {

	/**
	 * @param object $event
	 */
	abstract public function dispatch (object $event)

}

interface EventDispatcherInterface  {

	/**
	 * @param object $event
	 */
	abstract public function dispatch (object $event)

}

interface ListenerProviderInterface  {

	/**
	 * @param object $event
	 */
	abstract public function getListenersForEvent (object $event): iterable

}

interface ListenerProviderInterface  {

	/**
	 * @param object $event
	 */
	abstract public function getListenersForEvent (object $event): iterable

}

interface StoppableEventInterface  {

	abstract public function isPropagationStopped (): bool

}

interface StoppableEventInterface  {

	abstract public function isPropagationStopped (): bool

}

}

// End of psr v.1.2.0
