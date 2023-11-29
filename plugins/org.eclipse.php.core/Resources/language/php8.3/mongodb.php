<?php

// Start of mongodb v.1.17.0

namespace MongoDB\BSON {

interface Type  {
}

interface Serializable extends \MongoDB\BSON\Type {

	/**
	 * {@inheritdoc}
	 */
	abstract public function bsonSerialize ();

}

interface Unserializable  {

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	abstract public function bsonUnserialize (array $data);

}

interface BinaryInterface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getData ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getType ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString (): string;

}

interface Decimal128Interface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString ();

}

interface JavascriptInterface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getCode ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getScope ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString ();

}

interface MaxKeyInterface  {
}

interface MinKeyInterface  {
}

interface ObjectIdInterface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTimestamp ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString ();

}

interface RegexInterface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getPattern ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getFlags ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString ();

}

interface TimestampInterface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTimestamp ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getIncrement ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString ();

}

interface UTCDateTimeInterface extends \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function toDateTime ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString ();

}

final class Iterator implements \Iterator, \Traversable {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function current (): mixed {}

	/**
	 * {@inheritdoc}
	 */
	final public function key (): string|int {}

	/**
	 * {@inheritdoc}
	 */
	final public function next (): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function rewind (): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function valid (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class PackedArray implements \Stringable, \IteratorAggregate, \Traversable, \Serializable, \ArrayAccess, \MongoDB\BSON\Type {

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param array $value
	 */
	final public static function fromPHP (array $value): \MongoDB\BSON\PackedArray {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	final public function get (int $index): mixed {}

	/**
	 * {@inheritdoc}
	 */
	final public function getIterator (): \MongoDB\BSON\Iterator {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	final public function has (int $index): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $typeMap [optional]
	 */
	final public function toPHP (?array $typeMap = NULL): object|array {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists (mixed $key = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet (mixed $key = null): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet (mixed $key = null, mixed $value = null): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset (mixed $key = null): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\PackedArray {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class Document implements \Stringable, \IteratorAggregate, \Traversable, \Serializable, \ArrayAccess, \MongoDB\BSON\Type {

	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param string $bson
	 */
	final public static function fromBSON (string $bson): \MongoDB\BSON\Document {}

	/**
	 * {@inheritdoc}
	 * @param string $json
	 */
	final public static function fromJSON (string $json): \MongoDB\BSON\Document {}

	/**
	 * {@inheritdoc}
	 * @param object|array $value
	 */
	final public static function fromPHP (object|array $value): \MongoDB\BSON\Document {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	final public function get (string $key): mixed {}

	/**
	 * {@inheritdoc}
	 */
	final public function getIterator (): \MongoDB\BSON\Iterator {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 */
	final public function has (string $key): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $typeMap [optional]
	 */
	final public function toPHP (?array $typeMap = NULL): object|array {}

	/**
	 * {@inheritdoc}
	 */
	final public function toCanonicalExtendedJSON (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function toRelaxedExtendedJSON (): string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetExists (mixed $key = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetGet (mixed $key = null): mixed {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function offsetSet (mixed $key = null, mixed $value = null): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function offsetUnset (mixed $key = null): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Document {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class Binary implements \Stringable, \MongoDB\BSON\BinaryInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {
	const TYPE_GENERIC = 0;
	const TYPE_FUNCTION = 1;
	const TYPE_OLD_BINARY = 2;
	const TYPE_OLD_UUID = 3;
	const TYPE_UUID = 4;
	const TYPE_MD5 = 5;
	const TYPE_ENCRYPTED = 6;
	const TYPE_COLUMN = 7;
	const TYPE_SENSITIVE = 8;
	const TYPE_USER_DEFINED = 128;


	/**
	 * {@inheritdoc}
	 * @param string $data
	 * @param int $type [optional]
	 */
	final public function __construct (string $data, int $type = 0) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getData (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getType (): int {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Binary {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class DBPointer implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\DBPointer {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class Decimal128 implements \Stringable, \MongoDB\BSON\Decimal128Interface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param string $value
	 */
	final public function __construct (string $value) {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Decimal128 {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class Int64 implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param string|int $value
	 */
	final public function __construct (string|int $value) {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Int64 {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class Javascript implements \Stringable, \MongoDB\BSON\JavascriptInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param string $code
	 * @param object|array|null $scope [optional]
	 */
	final public function __construct (string $code, object|array|null $scope = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Javascript {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getScope (): ?object {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class MaxKey implements \MongoDB\BSON\MaxKeyInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\MaxKey {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class MinKey implements \MongoDB\BSON\MinKeyInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\MinKey {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class ObjectId implements \Stringable, \MongoDB\BSON\ObjectIdInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param string|null $id [optional]
	 */
	final public function __construct (?string $id = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTimestamp (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

interface Persistable extends \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \MongoDB\BSON\Unserializable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function bsonSerialize ();

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	abstract public function bsonUnserialize (array $data);

}

final class Regex implements \Stringable, \MongoDB\BSON\RegexInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 * @param string $flags [optional]
	 */
	final public function __construct (string $pattern, string $flags = '') {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPattern (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFlags (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Regex {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class Symbol implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Symbol {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class Timestamp implements \Stringable, \MongoDB\BSON\TimestampInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param string|int $increment
	 * @param string|int $timestamp
	 */
	final public function __construct (string|int $increment, string|int $timestamp) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTimestamp (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getIncrement (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Timestamp {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class Undefined implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Undefined {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}

final class UTCDateTime implements \Stringable, \MongoDB\BSON\UTCDateTimeInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param \DateTimeInterface|string|int|float|null $milliseconds [optional]
	 */
	final public function __construct (\DateTimeInterface|string|int|float|null $milliseconds = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function toDateTime (): \DateTime {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\UTCDateTime {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function jsonSerialize (): mixed {}

}


}


namespace MongoDB\Driver {

interface CursorInterface extends \Traversable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getId ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getServer ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function isDead ();

	/**
	 * {@inheritdoc}
	 * @param array $typemap
	 */
	abstract public function setTypeMap (array $typemap);

	/**
	 * {@inheritdoc}
	 */
	abstract public function toArray ();

}

final class BulkWrite implements \Countable {

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	final public function __construct (?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 * @param object|array $filter
	 * @param array|null $deleteOptions [optional]
	 */
	public function delete (object|array $filter, ?array $deleteOptions = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param object|array $document
	 */
	final public function insert (object|array $document): mixed {}

	/**
	 * {@inheritdoc}
	 * @param object|array $filter
	 * @param object|array $newObj
	 * @param array|null $updateOptions [optional]
	 */
	public function update (object|array $filter, object|array $newObj, ?array $updateOptions = NULL): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ClientEncryption  {
	const AEAD_AES_256_CBC_HMAC_SHA_512_DETERMINISTIC = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic";
	const AEAD_AES_256_CBC_HMAC_SHA_512_RANDOM = "AEAD_AES_256_CBC_HMAC_SHA_512-Random";
	const ALGORITHM_INDEXED = "Indexed";
	const ALGORITHM_UNINDEXED = "Unindexed";
	const ALGORITHM_RANGE_PREVIEW = "RangePreview";
	const QUERY_TYPE_EQUALITY = "equality";
	const QUERY_TYPE_RANGE_PREVIEW = "rangePreview";


	/**
	 * {@inheritdoc}
	 * @param array $options
	 */
	final public function __construct (array $options) {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\BSON\Binary $keyId
	 * @param string $keyAltName
	 */
	final public function addKeyAltName (\MongoDB\BSON\Binary $keyId, string $keyAltName): ?object {}

	/**
	 * {@inheritdoc}
	 * @param string $kmsProvider
	 * @param array|null $options [optional]
	 */
	final public function createDataKey (string $kmsProvider, ?array $options = NULL): \MongoDB\BSON\Binary {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\BSON\Binary $value
	 */
	final public function decrypt (\MongoDB\BSON\Binary $value): mixed {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\BSON\Binary $keyId
	 */
	final public function deleteKey (\MongoDB\BSON\Binary $keyId): object {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 * @param array|null $options [optional]
	 */
	final public function encrypt (mixed $value = null, ?array $options = NULL): \MongoDB\BSON\Binary {}

	/**
	 * {@inheritdoc}
	 * @param object|array $expr
	 * @param array|null $options [optional]
	 */
	final public function encryptExpression (object|array $expr, ?array $options = NULL): object {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\BSON\Binary $keyId
	 */
	final public function getKey (\MongoDB\BSON\Binary $keyId): ?object {}

	/**
	 * {@inheritdoc}
	 * @param string $keyAltName
	 */
	final public function getKeyByAltName (string $keyAltName): ?object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getKeys (): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\BSON\Binary $keyId
	 * @param string $keyAltName
	 */
	final public function removeKeyAltName (\MongoDB\BSON\Binary $keyId, string $keyAltName): ?object {}

	/**
	 * {@inheritdoc}
	 * @param object|array $filter
	 * @param array|null $options [optional]
	 */
	final public function rewrapManyDataKey (object|array $filter, ?array $options = NULL): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class Command  {

	/**
	 * {@inheritdoc}
	 * @param object|array $document
	 * @param array|null $commandOptions [optional]
	 */
	final public function __construct (object|array $document, ?array $commandOptions = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class Cursor implements \Iterator, \Traversable, \MongoDB\Driver\CursorInterface {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function current (): object|array|null {}

	/**
	 * {@inheritdoc}
	 */
	final public function getId (): \MongoDB\Driver\CursorId {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 */
	final public function isDead (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function key (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function next (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind (): void {}

	/**
	 * {@inheritdoc}
	 * @param array $typemap
	 */
	final public function setTypeMap (array $typemap): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function toArray (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function valid (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class CursorId implements \Stringable, \Serializable {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\CursorId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class Manager  {

	/**
	 * {@inheritdoc}
	 * @param string|null $uri [optional]
	 * @param array|null $uriOptions [optional]
	 * @param array|null $driverOptions [optional]
	 */
	final public function __construct (?string $uri = NULL, ?array $uriOptions = NULL, ?array $driverOptions = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber
	 */
	final public function addSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

	/**
	 * {@inheritdoc}
	 * @param array $options
	 */
	final public function createClientEncryption (array $options): \MongoDB\Driver\ClientEncryption {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param \MongoDB\Driver\BulkWrite $bulk
	 * @param \MongoDB\Driver\WriteConcern|array|null $options [optional]
	 */
	final public function executeBulkWrite (string $namespace, \MongoDB\Driver\BulkWrite $bulk, \MongoDB\Driver\WriteConcern|array|null $options = NULL): \MongoDB\Driver\WriteResult {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param \MongoDB\Driver\ReadPreference|array|null $options [optional]
	 */
	final public function executeCommand (string $db, \MongoDB\Driver\Command $command, \MongoDB\Driver\ReadPreference|array|null $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param \MongoDB\Driver\Query $query
	 * @param \MongoDB\Driver\ReadPreference|array|null $options [optional]
	 */
	final public function executeQuery (string $namespace, \MongoDB\Driver\Query $query, \MongoDB\Driver\ReadPreference|array|null $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null $options [optional]
	 */
	final public function executeReadCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null $options [optional]
	 */
	final public function executeReadWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null $options [optional]
	 */
	final public function executeWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 */
	final public function getEncryptedFieldsMap (): object|array|null {}

	/**
	 * {@inheritdoc}
	 */
	final public function getReadConcern (): \MongoDB\Driver\ReadConcern {}

	/**
	 * {@inheritdoc}
	 */
	final public function getReadPreference (): \MongoDB\Driver\ReadPreference {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServers (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getWriteConcern (): \MongoDB\Driver\WriteConcern {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber
	 */
	final public function removeSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\ReadPreference|null $readPreference [optional]
	 */
	final public function selectServer (?\MongoDB\Driver\ReadPreference $readPreference = NULL): \MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	final public function startSession (?array $options = NULL): \MongoDB\Driver\Session {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class Query  {

	/**
	 * {@inheritdoc}
	 * @param object|array $filter
	 * @param array|null $queryOptions [optional]
	 */
	final public function __construct (object|array $filter, ?array $queryOptions = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ReadConcern implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const LINEARIZABLE = "linearizable";
	const LOCAL = "local";
	const MAJORITY = "majority";
	const AVAILABLE = "available";
	const SNAPSHOT = "snapshot";


	/**
	 * {@inheritdoc}
	 * @param string|null $level [optional]
	 */
	final public function __construct (?string $level = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLevel (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	final public function isDefault (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\ReadConcern {}

	/**
	 * {@inheritdoc}
	 */
	final public function bsonSerialize (): \stdClass {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class ReadPreference implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const RP_PRIMARY = 1;
	const RP_PRIMARY_PREFERRED = 5;
	const RP_SECONDARY = 2;
	const RP_SECONDARY_PREFERRED = 6;
	const RP_NEAREST = 10;
	const PRIMARY = "primary";
	const PRIMARY_PREFERRED = "primaryPreferred";
	const SECONDARY = "secondary";
	const SECONDARY_PREFERRED = "secondaryPreferred";
	const NEAREST = "nearest";
	const NO_MAX_STALENESS = -1;
	const SMALLEST_MAX_STALENESS_SECONDS = 90;


	/**
	 * {@inheritdoc}
	 * @param string|int $mode
	 * @param array|null $tagSets [optional]
	 * @param array|null $options [optional]
	 */
	final public function __construct (string|int $mode, ?array $tagSets = NULL, ?array $options = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHedge (): ?object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMaxStalenessSeconds (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMode (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getModeString (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTagSets (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\ReadPreference {}

	/**
	 * {@inheritdoc}
	 */
	final public function bsonSerialize (): \stdClass {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class Server  {
	const TYPE_UNKNOWN = 0;
	const TYPE_STANDALONE = 1;
	const TYPE_MONGOS = 2;
	const TYPE_POSSIBLE_PRIMARY = 3;
	const TYPE_RS_PRIMARY = 4;
	const TYPE_RS_SECONDARY = 5;
	const TYPE_RS_ARBITER = 6;
	const TYPE_RS_OTHER = 7;
	const TYPE_RS_GHOST = 8;
	const TYPE_LOAD_BALANCER = 9;


	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param \MongoDB\Driver\BulkWrite $bulkWrite
	 * @param \MongoDB\Driver\WriteConcern|array|null $options [optional]
	 */
	final public function executeBulkWrite (string $namespace, \MongoDB\Driver\BulkWrite $bulkWrite, \MongoDB\Driver\WriteConcern|array|null $options = NULL): \MongoDB\Driver\WriteResult {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param \MongoDB\Driver\ReadPreference|array|null $options [optional]
	 */
	final public function executeCommand (string $db, \MongoDB\Driver\Command $command, \MongoDB\Driver\ReadPreference|array|null $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $namespace
	 * @param \MongoDB\Driver\Query $query
	 * @param \MongoDB\Driver\ReadPreference|array|null $options [optional]
	 */
	final public function executeQuery (string $namespace, \MongoDB\Driver\Query $query, \MongoDB\Driver\ReadPreference|array|null $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null $options [optional]
	 */
	final public function executeReadCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null $options [optional]
	 */
	final public function executeReadWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null $options [optional]
	 */
	final public function executeWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = NULL): \MongoDB\Driver\Cursor {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getInfo (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLatency (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServerDescription (): \MongoDB\Driver\ServerDescription {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTags (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getType (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function isArbiter (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function isHidden (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function isPassive (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function isPrimary (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function isSecondary (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ServerApi implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const V1 = 1;


	/**
	 * {@inheritdoc}
	 * @param string $version
	 * @param bool|null $strict [optional]
	 * @param bool|null $deprecationErrors [optional]
	 */
	final public function __construct (string $version, ?bool $strict = NULL, ?bool $deprecationErrors = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\ServerApi {}

	/**
	 * {@inheritdoc}
	 */
	final public function bsonSerialize (): \stdClass {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class ServerDescription  {
	const TYPE_UNKNOWN = "Unknown";
	const TYPE_STANDALONE = "Standalone";
	const TYPE_MONGOS = "Mongos";
	const TYPE_POSSIBLE_PRIMARY = "PossiblePrimary";
	const TYPE_RS_PRIMARY = "RSPrimary";
	const TYPE_RS_SECONDARY = "RSSecondary";
	const TYPE_RS_ARBITER = "RSArbiter";
	const TYPE_RS_OTHER = "RSOther";
	const TYPE_RS_GHOST = "RSGhost";
	const TYPE_LOAD_BALANCER = "LoadBalancer";


	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHelloResponse (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLastUpdateTime (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getRoundTripTime (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getType (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class TopologyDescription  {
	const TYPE_UNKNOWN = "Unknown";
	const TYPE_SINGLE = "Single";
	const TYPE_SHARDED = "Sharded";
	const TYPE_REPLICA_SET_NO_PRIMARY = "ReplicaSetNoPrimary";
	const TYPE_REPLICA_SET_WITH_PRIMARY = "ReplicaSetWithPrimary";
	const TYPE_LOAD_BALANCED = "LoadBalanced";


	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServers (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getType (): string {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\ReadPreference|null $readPreference [optional]
	 */
	final public function hasReadableServer (?\MongoDB\Driver\ReadPreference $readPreference = NULL): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function hasWritableServer (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class Session  {
	const TRANSACTION_NONE = "none";
	const TRANSACTION_STARTING = "starting";
	const TRANSACTION_IN_PROGRESS = "in_progress";
	const TRANSACTION_COMMITTED = "committed";
	const TRANSACTION_ABORTED = "aborted";


	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function abortTransaction (): void {}

	/**
	 * {@inheritdoc}
	 * @param object|array $clusterTime
	 */
	final public function advanceClusterTime (object|array $clusterTime): void {}

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\BSON\TimestampInterface $operationTime
	 */
	final public function advanceOperationTime (\MongoDB\BSON\TimestampInterface $operationTime): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function commitTransaction (): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function endSession (): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function getClusterTime (): ?object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLogicalSessionId (): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getOperationTime (): ?\MongoDB\BSON\Timestamp {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServer (): ?\MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTransactionOptions (): ?array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTransactionState (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function isDirty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function isInTransaction (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array|null $options [optional]
	 */
	final public function startTransaction (?array $options = NULL): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class WriteConcern implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const MAJORITY = "majority";


	/**
	 * {@inheritdoc}
	 * @param string|int $w
	 * @param int|null $wtimeout [optional]
	 * @param bool|null $journal [optional]
	 */
	final public function __construct (string|int $w, ?int $wtimeout = NULL, ?bool $journal = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	final public function getJournal (): ?bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function getW (): string|int|null {}

	/**
	 * {@inheritdoc}
	 */
	final public function getWtimeout (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function isDefault (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\WriteConcern {}

	/**
	 * {@inheritdoc}
	 */
	final public function bsonSerialize (): \stdClass {}

	/**
	 * {@inheritdoc}
	 */
	final public function serialize (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	final public function unserialize (string $data): void {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	final public function __unserialize (array $data): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __serialize (): array {}

}

final class WriteConcernError  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getInfo (): ?object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class WriteError  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getIndex (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getInfo (): ?object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class WriteResult  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getInsertedCount (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMatchedCount (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getModifiedCount (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getDeletedCount (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getUpsertedCount (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 */
	final public function getUpsertedIds (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getWriteConcernError (): ?\MongoDB\Driver\WriteConcernError {}

	/**
	 * {@inheritdoc}
	 */
	final public function getWriteErrors (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getErrorReplies (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function isAcknowledged (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}


}


namespace MongoDB\Driver\Exception {

interface Exception extends \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function getMessage (): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getCode ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function getFile (): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getLine (): int;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTrace (): array;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getPrevious (): ?\Throwable;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getTraceAsString (): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function __toString (): string;

}

class RuntimeException extends \RuntimeException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	protected $errorLabels;

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ServerException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class ConnectionException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

abstract class WriteException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	protected $writeResult;

	/**
	 * {@inheritdoc}
	 */
	final public function getWriteResult (): \MongoDB\Driver\WriteResult {}

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class AuthenticationException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class BulkWriteException extends \MongoDB\Driver\Exception\WriteException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 */
	final public function getWriteResult (): \MongoDB\Driver\WriteResult {}

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class CommandException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	protected $resultDocument;

	/**
	 * {@inheritdoc}
	 */
	final public function getResultDocument (): object {}

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

final class ConnectionTimeoutException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class EncryptionException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

final class ExecutionTimeoutException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class LogicException extends \LogicException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

final class SSLConnectionException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

class UnexpectedValueException extends \UnexpectedValueException implements \Throwable, \Stringable, \MongoDB\Driver\Exception\Exception {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param \Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?\Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?\Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}


}


namespace MongoDB\Driver\Monitoring {

interface Subscriber  {
}

interface CommandSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\CommandStartedEvent $event
	 */
	abstract public function commandStarted (\MongoDB\Driver\Monitoring\CommandStartedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\CommandSucceededEvent $event
	 */
	abstract public function commandSucceeded (\MongoDB\Driver\Monitoring\CommandSucceededEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\CommandFailedEvent $event
	 */
	abstract public function commandFailed (\MongoDB\Driver\Monitoring\CommandFailedEvent $event);

}

final class CommandFailedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCommandName (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getDurationMicros (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getError (): \Exception {}

	/**
	 * {@inheritdoc}
	 */
	final public function getOperationId (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getReply (): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getRequestId (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServiceId (): ?\MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServerConnectionId (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class CommandStartedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCommand (): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCommandName (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getDatabaseName (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getOperationId (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getRequestId (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServiceId (): ?\MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServerConnectionId (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class CommandSucceededEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCommandName (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getDurationMicros (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getOperationId (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getReply (): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getRequestId (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServiceId (): ?\MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function getServerConnectionId (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

interface LogSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {
	const LEVEL_ERROR = 0;
	const LEVEL_CRITICAL = 1;
	const LEVEL_WARNING = 2;
	const LEVEL_MESSAGE = 3;
	const LEVEL_INFO = 4;
	const LEVEL_DEBUG = 5;


	/**
	 * {@inheritdoc}
	 * @param int $level
	 * @param string $domain
	 * @param string $message
	 */
	abstract public function log (int $level, string $domain, string $message): void;

}

interface SDAMSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\ServerChangedEvent $event
	 */
	abstract public function serverChanged (\MongoDB\Driver\Monitoring\ServerChangedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\ServerClosedEvent $event
	 */
	abstract public function serverClosed (\MongoDB\Driver\Monitoring\ServerClosedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\ServerOpeningEvent $event
	 */
	abstract public function serverOpening (\MongoDB\Driver\Monitoring\ServerOpeningEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent $event
	 */
	abstract public function serverHeartbeatFailed (\MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent $event
	 */
	abstract public function serverHeartbeatStarted (\MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent $event
	 */
	abstract public function serverHeartbeatSucceeded (\MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\TopologyChangedEvent $event
	 */
	abstract public function topologyChanged (\MongoDB\Driver\Monitoring\TopologyChangedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\TopologyClosedEvent $event
	 */
	abstract public function topologyClosed (\MongoDB\Driver\Monitoring\TopologyClosedEvent $event);

	/**
	 * {@inheritdoc}
	 * @param \MongoDB\Driver\Monitoring\TopologyOpeningEvent $event
	 */
	abstract public function topologyOpening (\MongoDB\Driver\Monitoring\TopologyOpeningEvent $event);

}

final class ServerChangedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getNewDescription (): \MongoDB\Driver\ServerDescription {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPreviousDescription (): \MongoDB\Driver\ServerDescription {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ServerClosedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ServerHeartbeatFailedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getDurationMicros (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getError (): \Exception {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function isAwaited (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ServerHeartbeatStartedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function isAwaited (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ServerHeartbeatSucceededEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getDurationMicros (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getReply (): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function isAwaited (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class ServerOpeningEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class TopologyChangedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getNewDescription (): \MongoDB\Driver\TopologyDescription {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPreviousDescription (): \MongoDB\Driver\TopologyDescription {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class TopologyClosedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

final class TopologyOpeningEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}


}


namespace mongodb\bson {

/**
 * {@inheritdoc}
 * @param string $json
 */
function fromJSON (string $json): string {}

/**
 * {@inheritdoc}
 * @param object|array $value
 */
function fromPHP (object|array $value): string {}

/**
 * {@inheritdoc}
 * @param string $bson
 */
function toCanonicalExtendedJSON (string $bson): string {}

/**
 * {@inheritdoc}
 * @param string $bson
 */
function toJSON (string $bson): string {}

/**
 * {@inheritdoc}
 * @param string $bson
 * @param array|null $typemap [optional]
 */
function toPHP (string $bson, ?array $typemap = NULL): object|array {}

/**
 * {@inheritdoc}
 * @param string $bson
 */
function toRelaxedExtendedJSON (string $bson): string {}


}


namespace mongodb\driver\monitoring {

/**
 * {@inheritdoc}
 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber
 */
function addSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

/**
 * {@inheritdoc}
 * @param int $level
 * @param string $domain
 * @param string $message
 */
function mongoc_log (int $level, string $domain, string $message): void {}

/**
 * {@inheritdoc}
 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber
 */
function removeSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}


}


namespace {

define ('MONGODB_VERSION', "1.17.0");
define ('MONGODB_STABILITY', "stable");


}

// End of mongodb v.1.17.0
