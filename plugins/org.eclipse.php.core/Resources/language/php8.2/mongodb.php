<?php

// Start of mongodb v.1.15.3

namespace MongoDB\BSON {

interface Type  {
}

interface Serializable extends \MongoDB\BSON\Type {

	abstract public function bsonSerialize ()

}

interface Unserializable  {

	/**
	 * @param array[] $data
	 */
	abstract public function bsonUnserialize (array $data)

}

interface BinaryInterface extends \Stringable {

	abstract public function getData ()

	abstract public function getType ()

	abstract public function __toString (): string

}

interface Decimal128Interface extends \Stringable {

	abstract public function __toString ()

}

interface JavascriptInterface extends \Stringable {

	abstract public function getCode ()

	abstract public function getScope ()

	abstract public function __toString ()

}

interface MaxKeyInterface  {
}

interface MinKeyInterface  {
}

interface ObjectIdInterface extends \Stringable {

	abstract public function getTimestamp ()

	abstract public function __toString ()

}

interface RegexInterface extends \Stringable {

	abstract public function getPattern ()

	abstract public function getFlags ()

	abstract public function __toString ()

}

interface TimestampInterface extends \Stringable {

	abstract public function getTimestamp ()

	abstract public function getIncrement ()

	abstract public function __toString ()

}

interface UTCDateTimeInterface extends \Stringable {

	abstract public function toDateTime ()

	abstract public function __toString ()

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
	const TYPE_USER_DEFINED = 128;


	/**
	 * @param string $data
	 * @param int $type [optional]
	 */
	final public function __construct (string $data, int $type = 0) {}

	final public function getData (): string {}

	final public function getType (): int {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\Binary {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class DBPointer implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	final private function __construct () {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class Decimal128 implements \Stringable, \MongoDB\BSON\Decimal128Interface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param string $value
	 */
	final public function __construct (string $value) {}

	final public function __toString (): string {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\Decimal128 {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class Int64 implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	final private function __construct () {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class Javascript implements \Stringable, \MongoDB\BSON\JavascriptInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param string $code
	 * @param object|array|null $scope [optional]
	 */
	final public function __construct (string $code, object|array|null $scope = null) {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\Javascript {}

	final public function getCode (): string {}

	final public function getScope (): ?object {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class MaxKey implements \MongoDB\BSON\MaxKeyInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\MaxKey {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class MinKey implements \MongoDB\BSON\MinKeyInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\MinKey {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class ObjectId implements \Stringable, \MongoDB\BSON\ObjectIdInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param string|null $id [optional]
	 */
	final public function __construct (string|null $id = null) {}

	final public function getTimestamp (): int {}

	final public function __toString (): string {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\ObjectId {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

interface Persistable extends \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \MongoDB\BSON\Unserializable {

	abstract public function bsonSerialize ()

	/**
	 * @param array[] $data
	 */
	abstract public function bsonUnserialize (array $data)

}

final class Regex implements \Stringable, \MongoDB\BSON\RegexInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param string $pattern
	 * @param string $flags [optional]
	 */
	final public function __construct (string $pattern, string $flags = '') {}

	final public function getPattern (): string {}

	final public function getFlags (): string {}

	final public function __toString (): string {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\Regex {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class Symbol implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	final private function __construct () {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class Timestamp implements \Stringable, \MongoDB\BSON\TimestampInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param string|int $increment
	 * @param string|int $timestamp
	 */
	final public function __construct (string|int $increment, string|int $timestamp) {}

	final public function getTimestamp (): int {}

	final public function getIncrement (): int {}

	final public function __toString (): string {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\Timestamp {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class Undefined implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	final private function __construct () {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}

final class UTCDateTime implements \Stringable, \MongoDB\BSON\UTCDateTimeInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * @param DateTimeInterface|string|int|float|null $milliseconds [optional]
	 */
	final public function __construct (DateTimeInterface|string|int|float|null $milliseconds = null) {}

	final public function toDateTime (): DateTime {}

	final public function __toString (): string {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\BSON\UTCDateTime {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

	final public function jsonSerialize (): mixed {}

}


}


namespace MongoDB\Driver {

interface CursorInterface extends \Traversable {

	abstract public function getId ()

	abstract public function getServer ()

	abstract public function isDead ()

	/**
	 * @param array[] $typemap
	 */
	abstract public function setTypeMap (array $typemap)

	abstract public function toArray ()

}

final class BulkWrite implements \Countable {

	/**
	 * @param array|null[] $options [optional]
	 */
	final public function __construct (array $options = null) {}

	public function count (): int {}

	/**
	 * @param object|array $filter
	 * @param array|null[] $deleteOptions [optional]
	 */
	public function delete (object|array $filter, array $deleteOptions = null): void {}

	/**
	 * @param object|array $document
	 */
	final public function insert (object|array $document): mixed {}

	/**
	 * @param object|array $filter
	 * @param object|array $newObj
	 * @param array|null[] $updateOptions [optional]
	 */
	public function update (object|array $filter, object|array $newObj, array $updateOptions = null): void {}

	final public function __wakeup (): void {}

}

final class ClientEncryption  {
	const AEAD_AES_256_CBC_HMAC_SHA_512_DETERMINISTIC = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic";
	const AEAD_AES_256_CBC_HMAC_SHA_512_RANDOM = "AEAD_AES_256_CBC_HMAC_SHA_512-Random";
	const ALGORITHM_INDEXED = "Indexed";
	const ALGORITHM_UNINDEXED = "Unindexed";
	const QUERY_TYPE_EQUALITY = "equality";


	/**
	 * @param array[] $options
	 */
	final public function __construct (array $options) {}

	/**
	 * @param \MongoDB\BSON\Binary $keyId
	 * @param string $keyAltName
	 */
	final public function addKeyAltName (\MongoDB\BSON\Binary $keyId, string $keyAltName): ?object {}

	/**
	 * @param string $kmsProvider
	 * @param array|null[] $options [optional]
	 */
	final public function createDataKey (string $kmsProvider, array $options = null): MongoDB\BSON\Binary {}

	/**
	 * @param \MongoDB\BSON\Binary $value
	 */
	final public function decrypt (\MongoDB\BSON\Binary $value): mixed {}

	/**
	 * @param \MongoDB\BSON\Binary $keyId
	 */
	final public function deleteKey (\MongoDB\BSON\Binary $keyId): object {}

	/**
	 * @param mixed $value
	 * @param array|null[] $options [optional]
	 */
	final public function encrypt (mixed $value = null, array $options = null): MongoDB\BSON\Binary {}

	/**
	 * @param \MongoDB\BSON\Binary $keyId
	 */
	final public function getKey (\MongoDB\BSON\Binary $keyId): ?object {}

	/**
	 * @param string $keyAltName
	 */
	final public function getKeyByAltName (string $keyAltName): ?object {}

	final public function getKeys (): MongoDB\Driver\Cursor {}

	/**
	 * @param \MongoDB\BSON\Binary $keyId
	 * @param string $keyAltName
	 */
	final public function removeKeyAltName (\MongoDB\BSON\Binary $keyId, string $keyAltName): ?object {}

	/**
	 * @param object|array $filter
	 * @param array|null[] $options [optional]
	 */
	final public function rewrapManyDataKey (object|array $filter, array $options = null): object {}

	final public function __wakeup (): void {}

}

final class Command  {

	/**
	 * @param object|array $document
	 * @param array|null[] $commandOptions [optional]
	 */
	final public function __construct (object|array $document, array $commandOptions = null) {}

	final public function __wakeup (): void {}

}

final class Cursor implements \Iterator, \Traversable, \MongoDB\Driver\CursorInterface {

	final private function __construct () {}

	public function current (): object|array|null {}

	final public function getId (): MongoDB\Driver\CursorId {}

	final public function getServer (): MongoDB\Driver\Server {}

	final public function isDead (): bool {}

	public function key (): ?int {}

	public function next (): void {}

	public function rewind (): void {}

	/**
	 * @param array[] $typemap
	 */
	final public function setTypeMap (array $typemap): void {}

	final public function toArray (): array {}

	public function valid (): bool {}

	final public function __wakeup (): void {}

}

final class CursorId implements \Stringable, \Serializable {

	final private function __construct () {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\Driver\CursorId {}

	final public function __toString (): string {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

}

final class Manager  {

	/**
	 * @param string|null $uri [optional]
	 * @param array|null[] $uriOptions [optional]
	 * @param array|null[] $driverOptions [optional]
	 */
	final public function __construct (string|null $uri = null, array $uriOptions = null, array $driverOptions = null) {}

	/**
	 * @param MongoDB\Driver\Monitoring\Subscriber $subscriber
	 */
	final public function addSubscriber (MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

	/**
	 * @param array[] $options
	 */
	final public function createClientEncryption (array $options): MongoDB\Driver\ClientEncryption {}

	/**
	 * @param string $namespace
	 * @param \MongoDB\Driver\BulkWrite $bulk
	 * @param MongoDB\Driver\WriteConcern|array|null[] $options [optional]
	 */
	final public function executeBulkWrite (string $namespace, \MongoDB\Driver\BulkWrite $bulk, array $options = null): MongoDB\Driver\WriteResult {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param MongoDB\Driver\ReadPreference|array|null[] $options [optional]
	 */
	final public function executeCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $namespace
	 * @param \MongoDB\Driver\Query $query
	 * @param MongoDB\Driver\ReadPreference|array|null[] $options [optional]
	 */
	final public function executeQuery (string $namespace, \MongoDB\Driver\Query $query, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null[] $options [optional]
	 */
	final public function executeReadCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null[] $options [optional]
	 */
	final public function executeReadWriteCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null[] $options [optional]
	 */
	final public function executeWriteCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	final public function getEncryptedFieldsMap (): object|array|null {}

	final public function getReadConcern (): MongoDB\Driver\ReadConcern {}

	final public function getReadPreference (): MongoDB\Driver\ReadPreference {}

	final public function getServers (): array {}

	final public function getWriteConcern (): MongoDB\Driver\WriteConcern {}

	/**
	 * @param MongoDB\Driver\Monitoring\Subscriber $subscriber
	 */
	final public function removeSubscriber (MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

	/**
	 * @param \MongoDB\Driver\ReadPreference|null $readPreference [optional]
	 */
	final public function selectServer (\MongoDB\Driver\ReadPreference|null $readPreference = null): MongoDB\Driver\Server {}

	/**
	 * @param array|null[] $options [optional]
	 */
	final public function startSession (array $options = null): MongoDB\Driver\Session {}

	final public function __wakeup (): void {}

}

final class Query  {

	/**
	 * @param object|array $filter
	 * @param array|null[] $queryOptions [optional]
	 */
	final public function __construct (object|array $filter, array $queryOptions = null) {}

	final public function __wakeup (): void {}

}

final class ReadConcern implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const LINEARIZABLE = "linearizable";
	const LOCAL = "local";
	const MAJORITY = "majority";
	const AVAILABLE = "available";
	const SNAPSHOT = "snapshot";


	/**
	 * @param string|null $level [optional]
	 */
	final public function __construct (string|null $level = null) {}

	final public function getLevel (): ?string {}

	final public function isDefault (): bool {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\Driver\ReadConcern {}

	final public function bsonSerialize (): object|array {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

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
	 * @param string|int $mode
	 * @param array|null[] $tagSets [optional]
	 * @param array|null[] $options [optional]
	 */
	final public function __construct (string|int $mode, array $tagSets = null, array $options = null) {}

	final public function getHedge (): ?object {}

	final public function getMaxStalenessSeconds (): int {}

	final public function getMode (): int {}

	final public function getModeString (): string {}

	final public function getTagSets (): array {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\Driver\ReadPreference {}

	final public function bsonSerialize (): object|array {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

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


	final private function __construct () {}

	/**
	 * @param string $namespace
	 * @param \MongoDB\Driver\BulkWrite $bulkWrite
	 * @param MongoDB\Driver\WriteConcern|array|null[] $options [optional]
	 */
	final public function executeBulkWrite (string $namespace, \MongoDB\Driver\BulkWrite $bulkWrite, array $options = null): MongoDB\Driver\WriteResult {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param MongoDB\Driver\ReadPreference|array|null[] $options [optional]
	 */
	final public function executeCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $namespace
	 * @param \MongoDB\Driver\Query $query
	 * @param MongoDB\Driver\ReadPreference|array|null[] $options [optional]
	 */
	final public function executeQuery (string $namespace, \MongoDB\Driver\Query $query, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null[] $options [optional]
	 */
	final public function executeReadCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null[] $options [optional]
	 */
	final public function executeReadWriteCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	/**
	 * @param string $db
	 * @param \MongoDB\Driver\Command $command
	 * @param array|null[] $options [optional]
	 */
	final public function executeWriteCommand (string $db, \MongoDB\Driver\Command $command, array $options = null): MongoDB\Driver\Cursor {}

	final public function getHost (): string {}

	final public function getInfo (): array {}

	final public function getLatency (): ?int {}

	final public function getPort (): int {}

	final public function getServerDescription (): MongoDB\Driver\ServerDescription {}

	final public function getTags (): array {}

	final public function getType (): int {}

	final public function isArbiter (): bool {}

	final public function isHidden (): bool {}

	final public function isPassive (): bool {}

	final public function isPrimary (): bool {}

	final public function isSecondary (): bool {}

	final public function __wakeup (): void {}

}

final class ServerApi implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const V1 = 1;


	/**
	 * @param string $version
	 * @param bool|null $strict [optional]
	 * @param bool|null $deprecationErrors [optional]
	 */
	final public function __construct (string $version, bool|null $strict = null, bool|null $deprecationErrors = null) {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\Driver\ServerApi {}

	final public function bsonSerialize (): object|array {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

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


	final private function __construct () {}

	final public function getHelloResponse (): array {}

	final public function getHost (): string {}

	final public function getLastUpdateTime (): int {}

	final public function getPort (): int {}

	final public function getRoundTripTime (): ?int {}

	final public function getType (): string {}

	final public function __wakeup (): void {}

}

final class TopologyDescription  {
	const TYPE_UNKNOWN = "Unknown";
	const TYPE_SINGLE = "Single";
	const TYPE_SHARDED = "Sharded";
	const TYPE_REPLICA_SET_NO_PRIMARY = "ReplicaSetNoPrimary";
	const TYPE_REPLICA_SET_WITH_PRIMARY = "ReplicaSetWithPrimary";
	const TYPE_LOAD_BALANCED = "LoadBalanced";


	final private function __construct () {}

	final public function getServers (): array {}

	final public function getType (): string {}

	/**
	 * @param \MongoDB\Driver\ReadPreference|null $readPreference [optional]
	 */
	final public function hasReadableServer (\MongoDB\Driver\ReadPreference|null $readPreference = null): bool {}

	final public function hasWritableServer (): bool {}

	final public function __wakeup (): void {}

}

final class Session  {
	const TRANSACTION_NONE = "none";
	const TRANSACTION_STARTING = "starting";
	const TRANSACTION_IN_PROGRESS = "in_progress";
	const TRANSACTION_COMMITTED = "committed";
	const TRANSACTION_ABORTED = "aborted";


	final private function __construct () {}

	final public function abortTransaction (): void {}

	/**
	 * @param object|array $clusterTime
	 */
	final public function advanceClusterTime (object|array $clusterTime): void {}

	/**
	 * @param MongoDB\BSON\TimestampInterface $operationTime
	 */
	final public function advanceOperationTime (MongoDB\BSON\TimestampInterface $operationTime): void {}

	final public function commitTransaction (): void {}

	final public function endSession (): void {}

	final public function getClusterTime (): ?object {}

	final public function getLogicalSessionId (): object {}

	final public function getOperationTime (): ?MongoDB\BSON\Timestamp {}

	final public function getServer (): ?MongoDB\Driver\Server {}

	final public function getTransactionOptions (): ?array {}

	final public function getTransactionState (): string {}

	final public function isDirty (): bool {}

	final public function isInTransaction (): bool {}

	/**
	 * @param array|null[] $options [optional]
	 */
	final public function startTransaction (array $options = null): void {}

	final public function __wakeup (): void {}

}

final class WriteConcern implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	const MAJORITY = "majority";


	/**
	 * @param string|int $w
	 * @param int|null $wtimeout [optional]
	 * @param bool|null $journal [optional]
	 */
	final public function __construct (string|int $w, int|null $wtimeout = null, bool|null $journal = null) {}

	final public function getJournal (): ?bool {}

	final public function getW (): string|int|null {}

	final public function getWtimeout (): int {}

	final public function isDefault (): bool {}

	/**
	 * @param array[] $properties
	 */
	final public static function __set_state (array $properties): MongoDB\Driver\WriteConcern {}

	final public function bsonSerialize (): object|array {}

	final public function serialize (): string {}

	/**
	 * @param mixed $serialized
	 */
	final public function unserialize ($serialized = null): void {}

	/**
	 * @param array[] $data
	 */
	final public function __unserialize (array $data): void {}

	final public function __serialize (): array {}

}

final class WriteConcernError  {

	final private function __construct () {}

	final public function getCode (): int {}

	final public function getInfo (): ?object {}

	final public function getMessage (): string {}

	final public function __wakeup (): void {}

}

final class WriteError  {

	final private function __construct () {}

	final public function getCode (): int {}

	final public function getIndex (): int {}

	final public function getInfo (): ?object {}

	final public function getMessage (): string {}

	final public function __wakeup (): void {}

}

final class WriteResult  {

	final private function __construct () {}

	final public function getInsertedCount (): ?int {}

	final public function getMatchedCount (): ?int {}

	final public function getModifiedCount (): ?int {}

	final public function getDeletedCount (): ?int {}

	final public function getUpsertedCount (): ?int {}

	final public function getServer (): MongoDB\Driver\Server {}

	final public function getUpsertedIds (): array {}

	final public function getWriteConcernError (): ?MongoDB\Driver\WriteConcernError {}

	final public function getWriteErrors (): array {}

	final public function isAcknowledged (): bool {}

	final public function __wakeup (): void {}

}


}


namespace MongoDB\Driver\Exception {

interface Exception extends \Throwable, \Stringable {

	abstract public function getMessage (): string

	abstract public function getCode ()

	abstract public function getFile (): string

	abstract public function getLine (): int

	abstract public function getTrace (): array

	abstract public function getPrevious (): ?Throwable

	abstract public function getTraceAsString (): string

	abstract public function __toString (): string

}

class RuntimeException extends \RuntimeException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class ServerException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class ConnectionException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

abstract class WriteException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;
	protected $writeResult;


	final public function getWriteResult (): MongoDB\Driver\WriteResult {}

	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class AuthenticationException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class BulkWriteException extends \MongoDB\Driver\Exception\WriteException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;
	protected $writeResult;


	final public function getWriteResult (): MongoDB\Driver\WriteResult {}

	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class CommandException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;
	protected $resultDocument;


	final public function getResultDocument (): object {}

	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

final class ConnectionTimeoutException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class EncryptionException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

final class ExecutionTimeoutException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable, \MongoDB\Driver\Exception\Exception {
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

class LogicException extends \LogicException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
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

final class SSLConnectionException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $errorLabels;


	/**
	 * @param string $errorLabel
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

class UnexpectedValueException extends \UnexpectedValueException implements \Throwable, \Stringable, \MongoDB\Driver\Exception\Exception {
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


}


namespace MongoDB\Driver\Monitoring {

interface Subscriber  {
}

interface CommandSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {

	/**
	 * @param \MongoDB\Driver\Monitoring\CommandStartedEvent $event
	 */
	abstract public function commandStarted (\MongoDB\Driver\Monitoring\CommandStartedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\CommandSucceededEvent $event
	 */
	abstract public function commandSucceeded (\MongoDB\Driver\Monitoring\CommandSucceededEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\CommandFailedEvent $event
	 */
	abstract public function commandFailed (\MongoDB\Driver\Monitoring\CommandFailedEvent $event)

}

final class CommandFailedEvent  {

	final private function __construct () {}

	final public function getCommandName (): string {}

	final public function getDurationMicros (): int {}

	final public function getError (): Exception {}

	final public function getOperationId (): string {}

	final public function getReply (): object {}

	final public function getRequestId (): string {}

	final public function getServer (): MongoDB\Driver\Server {}

	final public function getServiceId (): ?MongoDB\BSON\ObjectId {}

	final public function getServerConnectionId (): ?int {}

	final public function __wakeup (): void {}

}

final class CommandStartedEvent  {

	final private function __construct () {}

	final public function getCommand (): object {}

	final public function getCommandName (): string {}

	final public function getDatabaseName (): string {}

	final public function getOperationId (): string {}

	final public function getRequestId (): string {}

	final public function getServer (): MongoDB\Driver\Server {}

	final public function getServiceId (): ?MongoDB\BSON\ObjectId {}

	final public function getServerConnectionId (): ?int {}

	final public function __wakeup (): void {}

}

final class CommandSucceededEvent  {

	final private function __construct () {}

	final public function getCommandName (): string {}

	final public function getDurationMicros (): int {}

	final public function getOperationId (): string {}

	final public function getReply (): object {}

	final public function getRequestId (): string {}

	final public function getServer (): MongoDB\Driver\Server {}

	final public function getServiceId (): ?MongoDB\BSON\ObjectId {}

	final public function getServerConnectionId (): ?int {}

	final public function __wakeup (): void {}

}

interface SDAMSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {

	/**
	 * @param \MongoDB\Driver\Monitoring\ServerChangedEvent $event
	 */
	abstract public function serverChanged (\MongoDB\Driver\Monitoring\ServerChangedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\ServerClosedEvent $event
	 */
	abstract public function serverClosed (\MongoDB\Driver\Monitoring\ServerClosedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\ServerOpeningEvent $event
	 */
	abstract public function serverOpening (\MongoDB\Driver\Monitoring\ServerOpeningEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent $event
	 */
	abstract public function serverHeartbeatFailed (\MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent $event
	 */
	abstract public function serverHeartbeatStarted (\MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent $event
	 */
	abstract public function serverHeartbeatSucceeded (\MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\TopologyChangedEvent $event
	 */
	abstract public function topologyChanged (\MongoDB\Driver\Monitoring\TopologyChangedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\TopologyClosedEvent $event
	 */
	abstract public function topologyClosed (\MongoDB\Driver\Monitoring\TopologyClosedEvent $event)

	/**
	 * @param \MongoDB\Driver\Monitoring\TopologyOpeningEvent $event
	 */
	abstract public function topologyOpening (\MongoDB\Driver\Monitoring\TopologyOpeningEvent $event)

}

final class ServerChangedEvent  {

	final private function __construct () {}

	final public function getPort (): int {}

	final public function getHost (): string {}

	final public function getNewDescription (): MongoDB\Driver\ServerDescription {}

	final public function getPreviousDescription (): MongoDB\Driver\ServerDescription {}

	final public function getTopologyId (): MongoDB\BSON\ObjectId {}

	final public function __wakeup (): void {}

}

final class ServerClosedEvent  {

	final private function __construct () {}

	final public function getPort (): int {}

	final public function getHost (): string {}

	final public function getTopologyId (): MongoDB\BSON\ObjectId {}

	final public function __wakeup (): void {}

}

final class ServerHeartbeatFailedEvent  {

	final private function __construct () {}

	final public function getDurationMicros (): int {}

	final public function getError (): Exception {}

	final public function getPort (): int {}

	final public function getHost (): string {}

	final public function isAwaited (): bool {}

	final public function __wakeup (): void {}

}

final class ServerHeartbeatStartedEvent  {

	final private function __construct () {}

	final public function getPort (): int {}

	final public function getHost (): string {}

	final public function isAwaited (): bool {}

	final public function __wakeup (): void {}

}

final class ServerHeartbeatSucceededEvent  {

	final private function __construct () {}

	final public function getDurationMicros (): int {}

	final public function getReply (): object {}

	final public function getPort (): int {}

	final public function getHost (): string {}

	final public function isAwaited (): bool {}

	final public function __wakeup (): void {}

}

final class ServerOpeningEvent  {

	final private function __construct () {}

	final public function getPort (): int {}

	final public function getHost (): string {}

	final public function getTopologyId (): MongoDB\BSON\ObjectId {}

	final public function __wakeup (): void {}

}

final class TopologyChangedEvent  {

	final private function __construct () {}

	final public function getNewDescription (): MongoDB\Driver\TopologyDescription {}

	final public function getPreviousDescription (): MongoDB\Driver\TopologyDescription {}

	final public function getTopologyId (): MongoDB\BSON\ObjectId {}

	final public function __wakeup (): void {}

}

final class TopologyClosedEvent  {

	final private function __construct () {}

	final public function getTopologyId (): MongoDB\BSON\ObjectId {}

	final public function __wakeup (): void {}

}

final class TopologyOpeningEvent  {

	final private function __construct () {}

	final public function getTopologyId (): MongoDB\BSON\ObjectId {}

	final public function __wakeup (): void {}

}


}


namespace mongodb\bson {

/**
 * @param string $json
 */
function fromJSON (string $json): string {}

/**
 * @param object|array $value
 */
function fromPHP (object|array $value): string {}

/**
 * @param string $bson
 */
function toCanonicalExtendedJSON (string $bson): string {}

/**
 * @param string $bson
 */
function toJSON (string $bson): string {}

/**
 * @param string $bson
 * @param array|null[] $typemap [optional]
 */
function toPHP (string $bson, array $typemap = null): object|array {}

/**
 * @param string $bson
 */
function toRelaxedExtendedJSON (string $bson): string {}


}


namespace mongodb\driver\monitoring {

/**
 * @param MongoDB\Driver\Monitoring\Subscriber $subscriber
 */
function addSubscriber (MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

/**
 * @param MongoDB\Driver\Monitoring\Subscriber $subscriber
 */
function removeSubscriber (MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}


}


namespace {


/**
 * x.y.z style version number of the extension
 * @link http://www.php.net/manual/en/mongodb.constants.php
 */
define ('MONGODB_VERSION', "1.15.3");

/**
 * Current stability (alpha/beta/stable)
 * @link http://www.php.net/manual/en/mongodb.constants.php
 */
define ('MONGODB_STABILITY', "stable");


}

// End of mongodb v.1.15.3
