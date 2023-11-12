<?php

// Start of mongodb v.1.15.3

namespace MongoDB\BSON {

/**
 * Abstract base interface that should not be implemented directly.
 * @link http://www.php.net/manual/en/class.mongodb-bson-type.php
 */
interface Type  {
}

/**
 * Classes that implement this interface may return data to be serialized as a
 * BSON array or document in lieu of the object's public properties.
 * @link http://www.php.net/manual/en/class.mongodb-bson-serializable.php
 */
interface Serializable extends \MongoDB\BSON\Type {

	/**
	 * Provides an array or document to serialize as BSON
	 * @link http://www.php.net/manual/en/mongodb-bson-serializable.bsonserialize.php
	 * @return array|object An array or stdClass to be serialized as
	 * a BSON array or document.
	 */
	abstract public function bsonSerialize (): array|object;

}

/**
 * Classes that implement this interface may be specified in a
 * type map for
 * unserializing BSON arrays and documents (both root and embedded).
 * @link http://www.php.net/manual/en/class.mongodb-bson-unserializable.php
 */
interface Unserializable  {

	/**
	 * Constructs the object from a BSON array or document
	 * @link http://www.php.net/manual/en/mongodb-bson-unserializable.bsonunserialize.php
	 * @param array $data Properties within the BSON array or document.
	 * @return void The return value from this method is ignored.
	 */
	abstract public function bsonUnserialize (array $data): void;

}

/**
 * This interface is implemented by MongoDB\BSON\Binary
 * to be used as a parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-binaryinterface.php
 */
interface BinaryInterface extends \Stringable {

	/**
	 * Returns the BinaryInterface's data
	 * @link http://www.php.net/manual/en/mongodb-bson-binaryinterface.getdata.php
	 * @return string Returns the BinaryInterface's data.
	 */
	abstract public function getData (): string;

	/**
	 * Returns the BinaryInterface's type
	 * @link http://www.php.net/manual/en/mongodb-bson-binaryinterface.gettype.php
	 * @return int Returns the BinaryInterface's type.
	 */
	abstract public function getType (): int;

	/**
	 * Returns the BinaryInterface's data
	 * @link http://www.php.net/manual/en/mongodb-bson-binaryinterface.tostring.php
	 * @return string Returns the BinaryInterface's data.
	 */
	abstract public function __toString (): string;

}

/**
 * This interface is implemented by
 * MongoDB\BSON\Decimal128 to be used as a parameter,
 * return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-decimal128interface.php
 */
interface Decimal128Interface extends \Stringable {

	/**
	 * Returns the string representation of this Decimal128Interface
	 * @link http://www.php.net/manual/en/mongodb-bson-decimal128interface.tostring.php
	 * @return string Returns the string representation of this Decimal128Interface.
	 */
	abstract public function __toString (): string;

}

/**
 * This interface is implemented by
 * MongoDB\BSON\Javascript to be used as a parameter,
 * return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-javascriptinterface.php
 */
interface JavascriptInterface extends \Stringable {

	/**
	 * Returns the JavascriptInterface's code
	 * @link http://www.php.net/manual/en/mongodb-bson-javascriptinterface.getcode.php
	 * @return string Returns the JavascriptInterface's code.
	 */
	abstract public function getCode (): string;

	/**
	 * Returns the JavascriptInterface's scope document
	 * @link http://www.php.net/manual/en/mongodb-bson-javascriptinterface.getscope.php
	 * @return object|null Returns the JavascriptInterface's scope document.
	 */
	abstract public function getScope (): ?object;

	/**
	 * Returns the JavascriptInterface's code
	 * @link http://www.php.net/manual/en/mongodb-bson-javascriptinterface.tostring.php
	 * @return string Returns the JavascriptInterface's code.
	 */
	abstract public function __toString (): string;

}

/**
 * This interface is implemented by MongoDB\BSON\MaxKey
 * to be used as a parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-maxkeyinterface.php
 */
interface MaxKeyInterface  {
}

/**
 * This interface is implemented by MongoDB\BSON\MinKey
 * to be used as a parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-minkeyinterface.php
 */
interface MinKeyInterface  {
}

/**
 * This interface is implemented by
 * MongoDB\BSON\ObjectId to be used as a parameter,
 * return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-objectidinterface.php
 */
interface ObjectIdInterface extends \Stringable {

	/**
	 * Returns the timestamp component of this ObjectIdInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-objectidinterface.gettimestamp.php
	 * @return int Returns the timestamp component of this ObjectIdInterface.
	 */
	abstract public function getTimestamp (): int;

	/**
	 * Returns the hexidecimal representation of this ObjectIdInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-objectidinterface.tostring.php
	 * @return string Returns the hexidecimal representation of this ObjectIdInterface.
	 */
	abstract public function __toString (): string;

}

/**
 * This interface is implemented by MongoDB\BSON\Regex
 * to be used as a parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-regexinterface.php
 */
interface RegexInterface extends \Stringable {

	/**
	 * Returns the RegexInterface's pattern
	 * @link http://www.php.net/manual/en/mongodb-bson-regexinterface.getpattern.php
	 * @return string Returns the RegexInterface's pattern.
	 */
	abstract public function getPattern (): string;

	/**
	 * Returns the RegexInterface's flags
	 * @link http://www.php.net/manual/en/mongodb-bson-regexinterface.getflags.php
	 * @return string Returns the RegexInterface's flags.
	 */
	abstract public function getFlags (): string;

	/**
	 * Returns the string representation of this RegexInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-regexinterface.tostring.php
	 * @return string Returns the string representation of this RegexInterface.
	 */
	abstract public function __toString (): string;

}

/**
 * This interface is implemented by
 * MongoDB\BSON\Timestamp to be used as a
 * parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-timestampinterface.php
 */
interface TimestampInterface extends \Stringable {

	/**
	 * Returns the timestamp component of this TimestampInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-timestampinterface.gettimestamp.php
	 * @return int Returns the timestamp component of this TimestampInterface.
	 */
	abstract public function getTimestamp (): int;

	/**
	 * Returns the increment component of this TimestampInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-timestampinterface.getincrement.php
	 * @return int Returns the increment component of this TimestampInterface.
	 */
	abstract public function getIncrement (): int;

	/**
	 * Returns the string representation of this TimestampInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-timestampinterface.tostring.php
	 * @return string Returns the string representation of this TimestampInterface.
	 */
	abstract public function __toString (): string;

}

/**
 * This interface is implemented by
 * MongoDB\BSON\UTCDateTime to be used as a
 * parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-bson-utcdatetimeinterface.php
 */
interface UTCDateTimeInterface extends \Stringable {

	/**
	 * Returns the DateTime representation of this UTCDateTimeInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetimeinterface.todatetime.php
	 * @return \DateTime Returns the DateTime representation of this
	 * UTCDateTimeInterface. The returned DateTime should use
	 * the UTC time zone.
	 */
	abstract public function toDateTime (): \DateTime;

	/**
	 * Returns the string representation of this UTCDateTimeInterface
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetimeinterface.tostring.php
	 * @return string Returns the string representation of this UTCDateTimeInterface.
	 */
	abstract public function __toString (): string;

}

/**
 * BSON type for binary data (i.e. array of bytes). Binary values also have a
 * subtype, which is used to indicate what kind of data is in the byte array.
 * Subtypes from zero to 127 are predefined or reserved. Subtypes from 128-255
 * are user-defined.
 * @link http://www.php.net/manual/en/class.mongodb-bson-binary.php
 */
final class Binary implements \Stringable, \MongoDB\BSON\BinaryInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {
	/**
	 * Generic binary data.
	const TYPE_GENERIC = 0;
	/**
	 * Function.
	const TYPE_FUNCTION = 1;
	/**
	 * Generic binary data (deprecated in favor of MongoDB\BSON\Binary::TYPE_GENERIC).
	const TYPE_OLD_BINARY = 2;
	/**
	 * Universally unique identifier (deprecated in favor of MongoDB\BSON\Binary::TYPE_UUID). When using this type, the Binary's data should be 16 bytes in length.
	 * <p>Historically, other drivers encoded values with this type based on their language conventions (e.g. varying endianness), which makes it non-portable. The PHP driver applies no special handling for encoding or decoding data with this type.</p>
	const TYPE_OLD_UUID = 3;
	/**
	 * Universally unique identifier. When using this type, the Binary's data should be 16 bytes in length and encoded according to RFC 4122.
	const TYPE_UUID = 4;
	/**
	 * MD5 hash. When using this type, the Binary's data should be 16 bytes in length.
	const TYPE_MD5 = 5;
	/**
	 * Encrypted value. This subtype is used for client-side encryption.
	const TYPE_ENCRYPTED = 6;
	/**
	 * Column data. This subtype is used for time-series collections.
	const TYPE_COLUMN = 7;
	/**
	 * User-defined type. While types between 0 and 127 are predefined or reserved, types between 128 and 255 are user-defined and may be used for anything.
	const TYPE_USER_DEFINED = 128;


	/**
	 * Construct a new Binary
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.construct.php
	 * @param string $data Binary data.
	 * @param int $type [optional] Unsigned 8-bit integer denoting the data's type. Defaults to MongoDB\BSON\Binary::TYPE_GENERIC if not specified.
	 * @return string 
	 */
	final public function __construct (string $data, int $type = \MongoDB\BSON\Binary::TYPE_GENERIC): string {}

	/**
	 * Returns the Binary's data
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.getdata.php
	 * @return string Returns the Binary's data.
	 */
	final public function getData (): string {}

	/**
	 * Returns the Binary's type
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.gettype.php
	 * @return int Returns the Binary's type.
	 */
	final public function getType (): int {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Binary {}

	/**
	 * Returns the Binary's data
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.tostring.php
	 * @return string Returns the Binary's data.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize a Binary
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Binary.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Binary
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-binary.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Binary.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for the "DBPointer" type. This BSON type is deprecated, and this
 * class can not be instantiated. It will be created from a BSON DBPointer
 * type while converting BSON to PHP, and can also be converted back into
 * BSON while storing documents in the database.
 * @link http://www.php.net/manual/en/class.mongodb-bson-dbpointer.php
 */
final class DBPointer implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new DBPointer (unused)
	 * @link http://www.php.net/manual/en/mongodb-bson-dbpointer.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Returns an empty string
	 * @link http://www.php.net/manual/en/mongodb-bson-dbpointer.tostring.php
	 * @return string Returns an empty string.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize a DBPointer
	 * @link http://www.php.net/manual/en/mongodb-bson-dbpointer.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\DBPointer.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a DBPointer
	 * @link http://www.php.net/manual/en/mongodb-bson-dbpointer.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-dbpointer.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\DBPointer.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for the
 * Decimal128 floating-point format,
 * which supports numbers with up to 34 decimal digits (i.e. significant
 * digits) and an exponent range of −6143 to +6144.
 * <p>Unlike the double BSON type (i.e. float in PHP), which only
 * stores an approximation of the decimal values, the decimal data type stores
 * the exact value. For example, MongoDB\BSON\Decimal128('9.99')
 * has a precise value of 9.99 where as a double 9.99 would have an approximate
 * value of 9.9900000000000002131628….</p>
 * @link http://www.php.net/manual/en/class.mongodb-bson-decimal128.php
 */
final class Decimal128 implements \Stringable, \MongoDB\BSON\Decimal128Interface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Decimal128
	 * @link http://www.php.net/manual/en/mongodb-bson-decimal128.construct.php
	 * @param string $value A decimal string.
	 * @return string 
	 */
	final public function __construct (string $value): string {}

	/**
	 * Returns the string representation of this Decimal128
	 * @link http://www.php.net/manual/en/mongodb-bson-decimal128.tostring.php
	 * @return string Returns the string representation of this Decimal128.
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Decimal128 {}

	/**
	 * Serialize a Decimal128
	 * @link http://www.php.net/manual/en/mongodb-bson-decimal128.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Decimal128.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Decimal128
	 * @link http://www.php.net/manual/en/mongodb-bson-decimal128.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-decimal128.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Decimal128.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for a 64-bit integer. This class cannot be instantiated and is
 * only created during BSON decoding when a 64-bit integer cannot be
 * represented as a PHP integer on a 32-bit platform. Versions of the driver
 * before 1.5.0 would throw an exception when attempting to decode a 64-bit
 * integer on a 32-bit platform.
 * <p>During BSON encoding, objects of this class will convert back to a 64-bit
 * integer type. This allows 64-bit integers to be roundtripped through a
 * 32-bit PHP environment without any loss of precision. The
 * __toString() method allows the 64-bit
 * integer value to be accessed as a string.</p>
 * @link http://www.php.net/manual/en/class.mongodb-bson-int64.php
 */
final class Int64 implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Int64 (unused)
	 * @link http://www.php.net/manual/en/mongodb-bson-int64.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Returns the string representation of this Int64
	 * @link http://www.php.net/manual/en/mongodb-bson-int64.tostring.php
	 * @return string Returns the string representation of this Int64.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize an Int64
	 * @link http://www.php.net/manual/en/mongodb-bson-int64.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Int64.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize an Int64
	 * @link http://www.php.net/manual/en/mongodb-bson-int64.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-int64.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Int64.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for Javascript code. An optional scope document may be specified
 * that maps identifiers to values and defines the scope in which the code
 * should be evaluated by the server.
 * @link http://www.php.net/manual/en/class.mongodb-bson-javascript.php
 */
final class Javascript implements \Stringable, \MongoDB\BSON\JavascriptInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Javascript
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.construct.php
	 * @param string $code Javascript code.
	 * @param array|object|null $scope [optional] Javascript scope.
	 * @return string 
	 */
	final public function __construct (string $code, array|object|null $scope = null): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Javascript {}

	/**
	 * Returns the Javascript's code
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.getcode.php
	 * @return string Returns the Javascript's code.
	 */
	final public function getCode (): string {}

	/**
	 * Returns the Javascript's scope document
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.getscope.php
	 * @return object|null Returns the Javascript's scope document, or null if the is no scope.
	 */
	final public function getScope (): ?object {}

	/**
	 * Returns the Javascript's code
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.tostring.php
	 * @return string Returns the Javascript's code.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize a Javascript
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Javascript.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Javascript
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-javascript.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Javascript.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * Special BSON type which compares higher than all other possible BSON element
 * values.
 * @link http://www.php.net/manual/en/class.mongodb-bson-maxkey.php
 */
final class MaxKey implements \MongoDB\BSON\MaxKeyInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\MaxKey {}

	/**
	 * Serialize a MaxKey
	 * @link http://www.php.net/manual/en/mongodb-bson-maxkey.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\MaxKey.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a MaxKey
	 * @link http://www.php.net/manual/en/mongodb-bson-maxkey.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-maxkey.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\MaxKey.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * Special BSON type which compares lower than all other possible BSON element
 * values.
 * @link http://www.php.net/manual/en/class.mongodb-bson-minkey.php
 */
final class MinKey implements \MongoDB\BSON\MinKeyInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\MinKey {}

	/**
	 * Serialize a MinKey
	 * @link http://www.php.net/manual/en/mongodb-bson-minkey.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\MinKey.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a MinKey
	 * @link http://www.php.net/manual/en/mongodb-bson-minkey.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-minkey.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\MinKey.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for an
 * ObjectId. The
 * value consists of 12 bytes, where the first four bytes are a timestamp
 * that reflect the ObjectId's creation. Specifically, the value consists of:
 * <p>In MongoDB, each document stored in a collection requires a unique
 * _id field that acts as a primary key. If an inserted
 * document omits the _id field, the driver automatically
 * generates an ObjectId for the _id field.</p>
 * <p>Using ObjectIds for the _id field provides the following
 * additional benefits:</p>
 * @link http://www.php.net/manual/en/class.mongodb-bson-objectid.php
 */
final class ObjectId implements \Stringable, \MongoDB\BSON\ObjectIdInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new ObjectId
	 * @link http://www.php.net/manual/en/mongodb-bson-objectid.construct.php
	 * @param string|null $id [optional] A 24-character hexadecimal string. If not provided, the driver will
	 * generate an ObjectId.
	 * @return string|null 
	 */
	final public function __construct (?string $id = null): ?string {}

	/**
	 * Returns the timestamp component of this ObjectId
	 * @link http://www.php.net/manual/en/mongodb-bson-objectid.gettimestamp.php
	 * @return int Returns the timestamp component of this ObjectId.
	 */
	final public function getTimestamp (): int {}

	/**
	 * Returns the hexidecimal representation of this ObjectId
	 * @link http://www.php.net/manual/en/mongodb-bson-objectid.tostring.php
	 * @return string Returns the hexidecimal representation of this ObjectId.
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\ObjectId {}

	/**
	 * Serialize an ObjectId
	 * @link http://www.php.net/manual/en/mongodb-bson-objectid.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\ObjectId.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize an ObjectId
	 * @link http://www.php.net/manual/en/mongodb-bson-objectid.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-objectid.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\ObjectId.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * Classes may implement this interface to take advantage of automatic ODM
 * (object document mapping) behavior in the driver. During serialization, the
 * driver will inject a __pclass property containing the
 * PHP class name into the data returned by
 * MongoDB\BSON\Serializable::bsonSerialize. During
 * unserialization, the same __pclass property will then
 * be used to infer the PHP class (independent of any
 * type map configuration)
 * to be constructed before
 * MongoDB\BSON\Unserializable::bsonUnserialize is
 * invoked. See for additional
 * information.
 * @link http://www.php.net/manual/en/class.mongodb-bson-persistable.php
 */
interface Persistable extends \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \MongoDB\BSON\Unserializable {

	/**
	 * Provides an array or document to serialize as BSON
	 * @link http://www.php.net/manual/en/mongodb-bson-serializable.bsonserialize.php
	 * @return array|object An array or stdClass to be serialized as
	 * a BSON array or document.
	 */
	abstract public function bsonSerialize (): array|object;

	/**
	 * Constructs the object from a BSON array or document
	 * @link http://www.php.net/manual/en/mongodb-bson-unserializable.bsonunserialize.php
	 * @param array $data Properties within the BSON array or document.
	 * @return void The return value from this method is ignored.
	 */
	abstract public function bsonUnserialize (array $data): void;

}

/**
 * BSON type for a regular expression pattern and optional
 * flags.
 * @link http://www.php.net/manual/en/class.mongodb-bson-regex.php
 */
final class Regex implements \Stringable, \MongoDB\BSON\RegexInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Regex
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.construct.php
	 * @param string $pattern The regular expression pattern.
	 * @param string $flags [optional] The regular
	 * expression flags. Characters in this argument will be sorted
	 * alphabetically.
	 * @return string 
	 */
	final public function __construct (string $pattern, string $flags = '""'): string {}

	/**
	 * Returns the Regex's pattern
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.getpattern.php
	 * @return string Returns the Regex's pattern.
	 */
	final public function getPattern (): string {}

	/**
	 * Returns the Regex's flags
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.getflags.php
	 * @return string Returns the Regex's flags.
	 */
	final public function getFlags (): string {}

	/**
	 * Returns the string representation of this Regex
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.tostring.php
	 * @return string Returns the string representation of this Regex.
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Regex {}

	/**
	 * Serialize a Regex
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Regex.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Regex
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-regex.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Regex.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for the "Symbol" type. This BSON type is deprecated, and this
 * class can not be instantiated. It will be created from a BSON symbol
 * type while converting BSON to PHP, and can also be converted back into
 * BSON while storing documents in the database.
 * @link http://www.php.net/manual/en/class.mongodb-bson-symbol.php
 */
final class Symbol implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Symbol (unused)
	 * @link http://www.php.net/manual/en/mongodb-bson-symbol.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Returns the Symbol as a string
	 * @link http://www.php.net/manual/en/mongodb-bson-symbol.tostring.php
	 * @return string Returns the string representation of this Symbol.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize a Symbol
	 * @link http://www.php.net/manual/en/mongodb-bson-symbol.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Symbol.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Symbol
	 * @link http://www.php.net/manual/en/mongodb-bson-symbol.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-symbol.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Symbol.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * Represents a
 * BSON timestamp,
 * The value consists of a 4-byte timestamp (i.e. seconds since the epoch) and
 * a 4-byte increment.
 * @link http://www.php.net/manual/en/class.mongodb-bson-timestamp.php
 */
final class Timestamp implements \Stringable, \MongoDB\BSON\TimestampInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Timestamp
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.construct.php
	 * @param int $increment 32-bit integer denoting the incrementing ordinal for operations within a
	 * given second.
	 * @param int $timestamp 32-bit integer denoting seconds since the Unix epoch.
	 * @return int 
	 */
	final public function __construct (int $increment, int $timestamp): int {}

	/**
	 * Returns the timestamp component of this Timestamp
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.gettimestamp.php
	 * @return int Returns the timestamp component of this Timestamp.
	 */
	final public function getTimestamp (): int {}

	/**
	 * Returns the increment component of this Timestamp
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.getincrement.php
	 * @return int Returns the increment component of this Timestamp.
	 */
	final public function getIncrement (): int {}

	/**
	 * Returns the string representation of this Timestamp
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.tostring.php
	 * @return string Returns the string representation of this Timestamp.
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\Timestamp {}

	/**
	 * Serialize a Timestamp
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Timestamp.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Timestamp
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-timestamp.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Timestamp.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * BSON type for the "Undefined" type. This BSON type is deprecated, and this
 * class can not be instantiated. It will be created from a BSON undefined
 * type while converting BSON to PHP, and can also be converted back into
 * BSON while storing documents in the database.
 * @link http://www.php.net/manual/en/class.mongodb-bson-undefined.php
 */
final class Undefined implements \Stringable, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new Undefined (unused)
	 * @link http://www.php.net/manual/en/mongodb-bson-undefined.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Returns an empty string
	 * @link http://www.php.net/manual/en/mongodb-bson-undefined.tostring.php
	 * @return string Returns an empty string.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize a Undefined
	 * @link http://www.php.net/manual/en/mongodb-bson-undefined.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\Undefined.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a Undefined
	 * @link http://www.php.net/manual/en/mongodb-bson-undefined.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-undefined.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\Undefined.
	 */
	final public function jsonSerialize (): mixed {}

}

/**
 * Represents a
 * BSON date. The value
 * is a 64-bit integer that represents the number of milliseconds since the
 * Unix epoch (Jan 1, 1970). Negative values represent dates before 1970.
 * @link http://www.php.net/manual/en/class.mongodb-bson-utcdatetime.php
 */
final class UTCDateTime implements \Stringable, \MongoDB\BSON\UTCDateTimeInterface, \JsonSerializable, \MongoDB\BSON\Type, \Serializable {

	/**
	 * Construct a new UTCDateTime
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetime.construct.php
	 * @param int|float|string|\DateTimeInterface|null $milliseconds [optional] Number of milliseconds since the Unix epoch (Jan 1, 1970). Negative values
	 * represent dates before 1970. This value may be provided as a 64-bit
	 * int. For compatibility on 32-bit systems, this parameter
	 * may also be provided as a float or string.
	 * <p>If the argument is a DateTimeInterface, the number
	 * of milliseconds since the Unix epoch will be derived from that value.</p>
	 * <p>If this argument is null, the current time will be used by default.</p>
	 * @return int|float|string|\DateTimeInterface|null 
	 */
	final public function __construct (int|float|string|\DateTimeInterface|null $milliseconds = null): int|float|string|\DateTimeInterface|null {}

	/**
	 * Returns the DateTime representation of this UTCDateTime
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetime.todatetime.php
	 * @return \DateTime Returns the DateTime representation of this
	 * UTCDateTime. The returned DateTime will use the UTC
	 * time zone.
	 */
	final public function toDateTime (): \DateTime {}

	/**
	 * Returns the string representation of this UTCDateTime
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetime.tostring.php
	 * @return string Returns the string representation of this UTCDateTime.
	 */
	final public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\BSON\UTCDateTime {}

	/**
	 * Serialize a UTCDateTime
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetime.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\BSON\UTCDateTime.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a UTCDateTime
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetime.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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
	 * Returns a representation that can be converted to JSON
	 * @link http://www.php.net/manual/en/mongodb-bson-utcdatetime.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode to
	 * produce an extended JSON representation of the
	 * MongoDB\BSON\UTCDateTime.
	 */
	final public function jsonSerialize (): mixed {}

}


}


namespace MongoDB\Driver {

/**
 * This interface is implemented by
 * MongoDB\Driver\Cursor to be used as
 * a parameter, return, or property type in userland classes.
 * @link http://www.php.net/manual/en/class.mongodb-driver-cursorinterface.php
 */
interface CursorInterface extends \Traversable {

	/**
	 * Returns the ID for this cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorinterface.getid.php
	 * @return \MongoDB\Driver\CursorId Returns the MongoDB\Driver\CursorId for this cursor.
	 */
	abstract public function getId (): \MongoDB\Driver\CursorId;

	/**
	 * Returns the server associated with this cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorinterface.getserver.php
	 * @return \MongoDB\Driver\Server Returns the MongoDB\Driver\Server associated with this
	 * cursor.
	 */
	abstract public function getServer (): \MongoDB\Driver\Server;

	/**
	 * Checks if the cursor may have additional results
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorinterface.isdead.php
	 * @return bool Returns true if additional results are not available, and false
	 * otherwise.
	 */
	abstract public function isDead (): bool;

	/**
	 * Sets a type map to use for BSON unserialization
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorinterface.settypemap.php
	 * @param array $typemap 
	 * @return void No value is returned.
	 */
	abstract public function setTypeMap (array $typemap): void;

	/**
	 * Returns an array containing all results for this cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorinterface.toarray.php
	 * @return array Returns an array containing all results for this cursor.
	 */
	abstract public function toArray (): array;

}

/**
 * The MongoDB\Driver\BulkWrite collects one or more
 * write operations that should be sent to the server. After adding any
 * number of insert, update, and delete operations, the collection may be
 * executed via
 * MongoDB\Driver\Manager::executeBulkWrite.
 * <p>Write operations may either be ordered (default) or unordered. Ordered write
 * operations are sent to the server, in the order provided, for serial
 * execution. If a write fails, any remaining operations will be aborted.
 * Unordered operations are sent to the server in an arbitrary order
 * where they may be executed in parallel. Any errors that occur are reported
 * after all operations have been attempted.</p>
 * @link http://www.php.net/manual/en/class.mongodb-driver-bulkwrite.php
 */
final class BulkWrite implements \Countable {

	/**
	 * Create a new BulkWrite
	 * @link http://www.php.net/manual/en/mongodb-driver-bulkwrite.construct.php
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * <td>Default</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>bypassDocumentValidation</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, allows insert and update operations to circumvent
	 * document level validation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and is ignored for older
	 * server versions, which do not support document level validation.
	 * </p>
	 * </td>
	 * <td>false</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>comment</td>
	 * <td>mixed</td>
	 * <td>
	 * <p>
	 * An arbitrary comment to help trace the operation through the
	 * database profiler, currentOp output, and logs.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 4.4+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>let</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Map of parameter names and values. Values must be constant or closed expressions that do not reference document fields. Parameters can then be accessed as variables in an aggregate expression context (e.g. $$var).
	 * </p>
	 * <p>
	 * This option is available in MongoDB 5.0+ and will result in an exception at execution time if specified for an older server version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ordered</td>
	 * <td>bool</td>
	 * <td>
	 * Ordered operations (true) are executed serially on the MongoDB
	 * server, while unordered operations (false) are sent to the server
	 * in an arbitrary order and may be executed in parallel.
	 * </td>
	 * <td>true</td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>If true, allows insert and update operations to circumvent
	 * document level validation.</p>
	 * <p>This option is available in MongoDB 3.2+ and is ignored for older
	 * server versions, which do not support document level validation.</p>
	 * <p>An arbitrary comment to help trace the operation through the
	 * database profiler, currentOp output, and logs.</p>
	 * <p>This option is available in MongoDB 4.4+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>Map of parameter names and values. Values must be constant or closed expressions that do not reference document fields. Parameters can then be accessed as variables in an aggregate expression context (e.g. $$var).</p>
	 * <p>This option is available in MongoDB 5.0+ and will result in an exception at execution time if specified for an older server version.</p>
	 * @return array|null 
	 */
	final public function __construct (?array $options = null): ?array {}

	/**
	 * Count number of write operations in the bulk
	 * @link http://www.php.net/manual/en/mongodb-driver-bulkwrite.count.php
	 * @return int Returns number of write operations added to the
	 * MongoDB\Driver\BulkWrite object.
	 */
	public function count (): int {}

	/**
	 * Add a delete operation to the bulk
	 * @link http://www.php.net/manual/en/mongodb-driver-bulkwrite.delete.php
	 * @param array|object $filter The query predicate.
	 * An empty predicate will match all documents in the collection.
	 * @param array|null $deleteOptions [optional] <table>
	 * deleteOptions
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * <td>Default</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>collation</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Collation allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks. When specifying collation, the "locale" field is mandatory; all other collation fields are optional. For descriptions of the fields, see Collation Document.
	 * </p>
	 * <p>
	 * If the collation is unspecified but the collection has a default collation, the operation uses the collation specified for the collection. If no collation is specified for the collection or for the operation, MongoDB uses the simple binary comparison used in prior versions for string comparisons.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.4+ and will result in an exception at execution time if specified for an older server version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>hint</td>
	 * <td>stringarrayobject</td>
	 * <td>
	 * <p>
	 * Index specification. Specify either the index name as a string or
	 * the index key pattern. If specified, then the query system will only
	 * consider plans using the hinted index.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 4.4+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>limit</td>
	 * <td>bool</td>
	 * <td>Delete all matching documents (false), or only the first matching document (true)</td>
	 * <td>false</td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Collation allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks. When specifying collation, the "locale" field is mandatory; all other collation fields are optional. For descriptions of the fields, see Collation Document.</p>
	 * <p>If the collation is unspecified but the collection has a default collation, the operation uses the collation specified for the collection. If no collation is specified for the collection or for the operation, MongoDB uses the simple binary comparison used in prior versions for string comparisons.</p>
	 * <p>This option is available in MongoDB 3.4+ and will result in an exception at execution time if specified for an older server version.</p>
	 * <p>Index specification. Specify either the index name as a string or
	 * the index key pattern. If specified, then the query system will only
	 * consider plans using the hinted index.</p>
	 * <p>This option is available in MongoDB 4.4+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * @return void No value is returned.
	 */
	public function delete (array|object $filter, ?array $deleteOptions = null): void {}

	/**
	 * Add an insert operation to the bulk
	 * @link http://www.php.net/manual/en/mongodb-driver-bulkwrite.insert.php
	 * @param array|object $document A document to insert.
	 * @return mixed Returns the _id of the inserted document. If the
	 * document did not have an _id, the
	 * MongoDB\BSON\ObjectId generated for the insert will
	 * be returned.
	 */
	final public function insert (array|object $document): mixed {}

	/**
	 * Add an update operation to the bulk
	 * @link http://www.php.net/manual/en/mongodb-driver-bulkwrite.update.php
	 * @param array|object $filter The query predicate.
	 * An empty predicate will match all documents in the collection.
	 * @param array|object $newObj A document containing either update operators (e.g.
	 * $set), a replacement document (i.e.
	 * only field:value expressions), or
	 * an aggregation pipeline.
	 * @param array|null $updateOptions [optional] <table>
	 * updateOptions
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * <td>Default</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>arrayFilters</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * An array of filter documents that determines which array elements to
	 * modify for an update operation on an array field. See
	 * Specify arrayFilters for Array Update Operations
	 * in the MongoDB manual for more information.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.6+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>collation</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Collation allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks. When specifying collation, the "locale" field is mandatory; all other collation fields are optional. For descriptions of the fields, see Collation Document.
	 * </p>
	 * <p>
	 * If the collation is unspecified but the collection has a default collation, the operation uses the collation specified for the collection. If no collation is specified for the collection or for the operation, MongoDB uses the simple binary comparison used in prior versions for string comparisons.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.4+ and will result in an exception at execution time if specified for an older server version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>hint</td>
	 * <td>stringarrayobject</td>
	 * <td>
	 * <p>
	 * Index specification. Specify either the index name as a string or
	 * the index key pattern. If specified, then the query system will only
	 * consider plans using the hinted index.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 4.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>multi</td>
	 * <td>bool</td>
	 * <td>
	 * Update only the first matching document if false, or all
	 * matching documents true. This option cannot be true if
	 * newObj is a replacement document.
	 * </td>
	 * <td>false</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>upsert</td>
	 * <td>bool</td>
	 * <td>
	 * If filter does not match an existing document,
	 * insert a single document. The document will be
	 * created from newObj if it is a replacement
	 * document (i.e. no update operators); otherwise, the operators in
	 * newObj will be applied to
	 * filter to create the new document.
	 * </td>
	 * <td>false</td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>An array of filter documents that determines which array elements to
	 * modify for an update operation on an array field. See
	 * Specify arrayFilters for Array Update Operations
	 * in the MongoDB manual for more information.</p>
	 * <p>This option is available in MongoDB 3.6+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>Collation allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks. When specifying collation, the "locale" field is mandatory; all other collation fields are optional. For descriptions of the fields, see Collation Document.</p>
	 * <p>If the collation is unspecified but the collection has a default collation, the operation uses the collation specified for the collection. If no collation is specified for the collection or for the operation, MongoDB uses the simple binary comparison used in prior versions for string comparisons.</p>
	 * <p>This option is available in MongoDB 3.4+ and will result in an exception at execution time if specified for an older server version.</p>
	 * <p>Index specification. Specify either the index name as a string or
	 * the index key pattern. If specified, then the query system will only
	 * consider plans using the hinted index.</p>
	 * <p>This option is available in MongoDB 4.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * @return void No value is returned.
	 */
	public function update (array|object $filter, array|object $newObj, ?array $updateOptions = null): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\ClientEncryption class handles
 * creation of data keys for client-side encryption, as well as manually
 * encrypting and decrypting values.
 * @link http://www.php.net/manual/en/class.mongodb-driver-clientencryption.php
 */
final class ClientEncryption  {
	/**
	 * Specifies an algorithm for deterministic encryption, which is suitable for querying.
	const AEAD_AES_256_CBC_HMAC_SHA_512_DETERMINISTIC = "AEAD_AES_256_CBC_HMAC_SHA_512-Deterministic";
	/**
	 * Specifies an algorithm for randomized encryption
	const AEAD_AES_256_CBC_HMAC_SHA_512_RANDOM = "AEAD_AES_256_CBC_HMAC_SHA_512-Random";
	/**
	 * Specifies an algorithm for an indexed, encrypted payload, which can be used with queryable encryption.
	 * <p>To insert or query with an indexed, encrypted payload, the MongoDB\Driver\Manager must be configured with the "autoEncryption" driver option. The "bypassQueryAnalysis" auto encryption option may be true. The "bypassAutoEncryption" auto encryption option must be false.</p>
	const ALGORITHM_INDEXED = "Indexed";
	/**
	 * Specifies an algorithm for an unindexed, encrypted payload.
	const ALGORITHM_UNINDEXED = "Unindexed";
	/**
	 * Specifies an equality query type, which is used in conjunction with MongoDB\Driver\ClientEncryption::ALGORITHM_INDEXED.
	const QUERY_TYPE_EQUALITY = "equality";


	/**
	 * Create a new ClientEncryption object
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.construct.php
	 * @param array $options <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultClient</td>
	 * <td>MongoDB\Driver\Manager</td>
	 * <td>The Manager used to route data key queries. This option is required (unlike with MongoDB\Driver\Manager::createClientEncryption).</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultNamespace</td>
	 * <td>string</td>
	 * <td>A fully qualified namespace (e.g. "databaseName.collectionName") denoting the collection that contains all data keys used for encryption and decryption. This option is required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>kmsProviders</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.
	 * </p>
	 * <p>
	 * The format for "aws" is as follows:
	 * </p>
	 * <pre>
	 * aws: {
	 * accessKeyId: ,
	 * secretAccessKey: ,
	 * sessionToken: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "azure" is as follows:
	 * </p>
	 * <pre>
	 * azure: {
	 * tenantId: ,
	 * clientId: ,
	 * clientSecret: ,
	 * identityPlatformEndpoint: // Defaults to "login.microsoftonline.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "gcp" is as follows:
	 * </p>
	 * <pre>
	 * gcp: {
	 * email: ,
	 * privateKey: |,
	 * endpoint: // Defaults to "oauth2.googleapis.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "kmip" is as follows:
	 * </p>
	 * <pre>
	 * kmip: {
	 * endpoint: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "local" is as follows:
	 * </p>
	 * <pre>
	 * local: {
	 * // 96-byte master key used to encrypt/decrypt data keys
	 * key: |
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:
	 * </p>
	 * <pre>
	 * : {
	 * tlsCaFile: ,
	 * tlsCertificateKeyFile: ,
	 * tlsCertificateKeyFilePassword: ,
	 * tlsDisableOCSPEndpointCheck: 
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.</p>
	 * <p>The format for "aws" is as follows:</p>
	 * <p>The format for "azure" is as follows:</p>
	 * <p>The format for "gcp" is as follows:</p>
	 * <p>The format for "kmip" is as follows:</p>
	 * <p>The format for "local" is as follows:</p>
	 * <p>A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:</p>
	 * @return array 
	 */
	final public function __construct (array $options): array {}

	/**
	 * Adds an alternate name to a key document
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.addkeyaltname.php
	 * @param \MongoDB\BSON\Binary $keyId A MongoDB\BSON\Binary instance with subtype 4
	 * (UUID) identifying the key document.
	 * @param string $keyAltName Alternate name to add to the key document.
	 * @return object|null Returns the previous version of the key document, or null if no document
	 * matched.
	 */
	final public function addKeyAltName (\MongoDB\BSON\Binary $keyId, string $keyAltName): ?object {}

	/**
	 * Creates a key document
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.createdatakey.php
	 * @param string $kmsProvider The KMS provider (e.g. "local",
	 * "aws") that will be used to encrypt the new data key.
	 * @param array|null $options [optional] <table>
	 * Data key options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>masterKey</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * The masterKey document identifies a KMS-specific key used to encrypt
	 * the new data key. This option is required unless
	 * kmsProvider is "local".
	 * </p>
	 * <p>
	 * <table>
	 * "aws" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>region</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>key</td>
	 * <td>string</td>
	 * <td>Required. The Amazon Resource Name (ARN) to the AWS customer master key (CMK).</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. An alternate host identifier to send KMS requests to. May include port number.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * <table>
	 * "azure" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultEndpoint</td>
	 * <td>string</td>
	 * <td>Required. Host with optional port (e.g. "example.vault.azure.net").</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * <table>
	 * "gcp" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>projectId</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>location</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyRing</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port. Defaults to "cloudkms.googleapis.com".</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * <table>
	 * "kmip" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyId</td>
	 * <td>string</td>
	 * <td>Optional. Unique identifier to a 96-byte KMIP secret data managed object. If unspecified, the driver creates a random 96-byte KMIP secret data managed object.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyAltNames</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * An optional list of string alternate names used to reference a key.
	 * If a key is created with alternate names, then encryption may refer
	 * to the key by the unique alternate name instead of by
	 * _id.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyMaterial</td>
	 * <td>MongoDB\BSON\Binary</td>
	 * <td>
	 * <p>
	 * An optional 96-byte value to use as custom key material for the data
	 * key being created. If keyMaterial is given, the custom key material
	 * is used for encrypting and decrypting data. Otherwise, the key
	 * material for the new data key is generated from a cryptographically
	 * secure random device.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>The masterKey document identifies a KMS-specific key used to encrypt
	 * the new data key. This option is required unless
	 * kmsProvider is "local".</p>
	 * <p>>
	 * <table>
	 * "aws" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>region</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>key</td>
	 * <td>string</td>
	 * <td>Required. The Amazon Resource Name (ARN) to the AWS customer master key (CMK).</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. An alternate host identifier to send KMS requests to. May include port number.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>>
	 * <table>
	 * "azure" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultEndpoint</td>
	 * <td>string</td>
	 * <td>Required. Host with optional port (e.g. "example.vault.azure.net").</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>>
	 * <table>
	 * "gcp" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>projectId</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>location</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyRing</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port. Defaults to "cloudkms.googleapis.com".</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>>
	 * <table>
	 * "kmip" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyId</td>
	 * <td>string</td>
	 * <td>Optional. Unique identifier to a 96-byte KMIP secret data managed object. If unspecified, the driver creates a random 96-byte KMIP secret data managed object.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>An optional list of string alternate names used to reference a key.
	 * If a key is created with alternate names, then encryption may refer
	 * to the key by the unique alternate name instead of by
	 * _id.</p>
	 * <p>An optional 96-byte value to use as custom key material for the data
	 * key being created. If keyMaterial is given, the custom key material
	 * is used for encrypting and decrypting data. Otherwise, the key
	 * material for the new data key is generated from a cryptographically
	 * secure random device.</p>
	 * @return \MongoDB\BSON\Binary Returns the identifier of the new key as a
	 * MongoDB\BSON\Binary object with subtype 4 (UUID).
	 */
	final public function createDataKey (string $kmsProvider, ?array $options = null): \MongoDB\BSON\Binary {}

	/**
	 * Decrypt a value
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.decrypt.php
	 * @param \MongoDB\BSON\Binary $value A MongoDB\BSON\Binary instance with subtype 6
	 * containing the encrypted value.
	 * @return mixed Returns the decrypted value as it was passed to
	 * MongoDB\Driver\ClientEncryption::encrypt.
	 */
	final public function decrypt (\MongoDB\BSON\Binary $value): mixed {}

	/**
	 * Deletes a key document
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.deletekey.php
	 * @param \MongoDB\BSON\Binary $keyId A MongoDB\BSON\Binary instance with subtype 4
	 * (UUID) identifying the key document.
	 * @return object Returns the result of the internal deleteOne operation on
	 * the key vault collection.
	 */
	final public function deleteKey (\MongoDB\BSON\Binary $keyId): object {}

	/**
	 * Encrypt a value
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.encrypt.php
	 * @param mixed $value The value to be encrypted. Any value that can be inserted into MongoDB can
	 * be encrypted using this method.
	 * @param array|null $options [optional] <table>
	 * Encryption options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>algorithm</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * The encryption algorithm to be used. This option is required.
	 * Specify one of the following
	 * ClientEncryption constants:
	 * </p>
	 * <p>
	 * MongoDB\Driver\ClientEncryption::AEAD_AES_256_CBC_HMAC_SHA_512_DETERMINISTIC
	 * MongoDB\Driver\ClientEncryption::AEAD_AES_256_CBC_HMAC_SHA_512_RANDOM
	 * MongoDB\Driver\ClientEncryption::ALGORITHM_INDEXED
	 * MongoDB\Driver\ClientEncryption::ALGORITHM_UNINDEXED
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>contentionFactor</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The contention factor for evaluating queries with indexed, encrypted
	 * payloads.
	 * </p>
	 * <p>
	 * This option only applies and may only be specified when
	 * algorithm is
	 * MongoDB\Driver\ClientEncryption::ALGORITHM_INDEXED.
	 * </p>
	 * Queryable Encryption is in public preview and available for evaluation
	 * purposes. It is not yet recommended for production deployments as breaking
	 * changes may be introduced. See the Queryable Encryption Preview blog post for more information.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyAltName</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Identifies a key vault collection document by
	 * keyAltName. This option is mutually exclusive
	 * with keyId and exactly one is required.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyId</td>
	 * <td>MongoDB\BSON\Binary</td>
	 * <td>
	 * <p>
	 * Identifies a data key by _id. The value is a UUID
	 * (binary subtype 4). This option is mutually exclusive with
	 * keyAltName and exactly one is required.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>queryType</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The query type for evaluating queries with indexed, encrypted
	 * payloads. Specify one of the following
	 * ClientEncryption constants:
	 * </p>
	 * <p>
	 * MongoDB\Driver\ClientEncryption::QUERY_TYPE_EQUALITY
	 * </p>
	 * <p>This option only applies and may only be specified when
	 * algorithm is
	 * MongoDB\Driver\ClientEncryption::ALGORITHM_INDEXED.
	 * </p>
	 * Queryable Encryption is in public preview and available for evaluation
	 * purposes. It is not yet recommended for production deployments as breaking
	 * changes may be introduced. See the Queryable Encryption Preview blog post for more information.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>The encryption algorithm to be used. This option is required.
	 * Specify one of the following
	 * ClientEncryption constants:</p>
	 * <p>The contention factor for evaluating queries with indexed, encrypted
	 * payloads.</p>
	 * <p>This option only applies and may only be specified when
	 * algorithm is
	 * MongoDB\Driver\ClientEncryption::ALGORITHM_INDEXED.</p>
	 * <p>Identifies a key vault collection document by
	 * keyAltName. This option is mutually exclusive
	 * with keyId and exactly one is required.</p>
	 * <p>Identifies a data key by _id. The value is a UUID
	 * (binary subtype 4). This option is mutually exclusive with
	 * keyAltName and exactly one is required.</p>
	 * <p>The query type for evaluating queries with indexed, encrypted
	 * payloads. Specify one of the following
	 * ClientEncryption constants:</p>
	 * <p>This option only applies and may only be specified when
	 * algorithm is
	 * MongoDB\Driver\ClientEncryption::ALGORITHM_INDEXED.</p>
	 * @return \MongoDB\BSON\Binary Returns the encrypted value as
	 * MongoDB\BSON\Binary object with subtype 6.
	 */
	final public function encrypt (mixed $value, ?array $options = null): \MongoDB\BSON\Binary {}

	/**
	 * Gets a key document
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.getkey.php
	 * @param \MongoDB\BSON\Binary $keyId A MongoDB\BSON\Binary instance with subtype 4
	 * (UUID) identifying the key document.
	 * @return object|null Returns the key document, or null if no document matched.
	 */
	final public function getKey (\MongoDB\BSON\Binary $keyId): ?object {}

	/**
	 * Gets a key document by an alternate name
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.getkeybyaltname.php
	 * @param string $keyAltName Alternate name for the key document.
	 * @return object|null Returns the key document, or null if no document matched.
	 */
	final public function getKeyByAltName (string $keyAltName): ?object {}

	/**
	 * Gets all key documents
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.getkeys.php
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function getKeys (): \MongoDB\Driver\Cursor {}

	/**
	 * Removes an alternate name from a key document
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.removekeyaltname.php
	 * @param \MongoDB\BSON\Binary $keyId A MongoDB\BSON\Binary instance with subtype 4
	 * (UUID) identifying the key document.
	 * @param string $keyAltName Alternate name to remove from the key document.
	 * @return object|null Returns the previous version of the key document, or null if no document
	 * matched.
	 */
	final public function removeKeyAltName (\MongoDB\BSON\Binary $keyId, string $keyAltName): ?object {}

	/**
	 * Rewraps data keys
	 * @link http://www.php.net/manual/en/mongodb-driver-clientencryption.rewrapmanydatakey.php
	 * @param array|object $filter The query predicate.
	 * An empty predicate will match all documents in the collection.
	 * @param array|null $options [optional] <table>
	 * RewrapManyDataKey options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>provider</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * The KMS provider (e.g. "local",
	 * "aws") that will be used to re-encrypt the
	 * matched data keys.
	 * </p>
	 * <p>
	 * If a KMS provider is not specified, matched data keys will be
	 * re-encrypted with their current KMS provider.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>masterKey</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * The masterKey identifies a KMS-specific key used to encrypt the new
	 * data key. This option should not be specified without the
	 * "provider" option. This option is required if
	 * "provider" is specified and not
	 * "local".
	 * </p>
	 * <p>
	 * <table>
	 * "aws" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>region</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>key</td>
	 * <td>string</td>
	 * <td>Required. The Amazon Resource Name (ARN) to the AWS customer master key (CMK).</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. An alternate host identifier to send KMS requests to. May include port number.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * <table>
	 * "azure" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultEndpoint</td>
	 * <td>string</td>
	 * <td>Required. Host with optional port (e.g. "example.vault.azure.net").</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * <table>
	 * "gcp" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>projectId</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>location</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyRing</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port. Defaults to "cloudkms.googleapis.com".</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * <table>
	 * "kmip" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyId</td>
	 * <td>string</td>
	 * <td>Optional. Unique identifier to a 96-byte KMIP secret data managed object. If unspecified, the driver creates a random 96-byte KMIP secret data managed object.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>The KMS provider (e.g. "local",
	 * "aws") that will be used to re-encrypt the
	 * matched data keys.</p>
	 * <p>If a KMS provider is not specified, matched data keys will be
	 * re-encrypted with their current KMS provider.</p>
	 * <p>The masterKey identifies a KMS-specific key used to encrypt the new
	 * data key. This option should not be specified without the
	 * "provider" option. This option is required if
	 * "provider" is specified and not
	 * "local".</p>
	 * <p>>
	 * <table>
	 * "aws" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>region</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>key</td>
	 * <td>string</td>
	 * <td>Required. The Amazon Resource Name (ARN) to the AWS customer master key (CMK).</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. An alternate host identifier to send KMS requests to. May include port number.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>>
	 * <table>
	 * "azure" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultEndpoint</td>
	 * <td>string</td>
	 * <td>Required. Host with optional port (e.g. "example.vault.azure.net").</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>>
	 * <table>
	 * "gcp" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>projectId</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>location</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyRing</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyName</td>
	 * <td>string</td>
	 * <td>Required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVersion</td>
	 * <td>string</td>
	 * <td>Optional. A specific version of the named key. Defaults to using the key's primary version.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port. Defaults to "cloudkms.googleapis.com".</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>>
	 * <table>
	 * "kmip" provider options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyId</td>
	 * <td>string</td>
	 * <td>Optional. Unique identifier to a 96-byte KMIP secret data managed object. If unspecified, the driver creates a random 96-byte KMIP secret data managed object.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>endpoint</td>
	 * <td>string</td>
	 * <td>Optional. Host with optional port.</td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * @return object Returns an object, which will have an optional
	 * bulkWriteResult property containing the result of the
	 * internal bulkWrite operation as an object. If no data keys
	 * matched the filter or the write was unacknowledged, the
	 * bulkWriteResult property will be null.
	 */
	final public function rewrapManyDataKey (array|object $filter, ?array $options = null): object {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Command class is a value object
 * that represents a database command.
 * <p>To provide Command Helpers the MongoDB\Driver\Command object should be composed.</p>
 * @link http://www.php.net/manual/en/class.mongodb-driver-command.php
 */
final class Command  {

	/**
	 * Create a new Command
	 * @link http://www.php.net/manual/en/mongodb-driver-command.construct.php
	 * @param array|object $document The complete command document, which will be sent to the server.
	 * @param array|null $commandOptions [optional] <table>
	 * commandOptions
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxAwaitTimeMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Positive integer denoting the time limit in milliseconds for the
	 * server to block a getMore operation if no data is available. This
	 * option should only be used in conjunction with commands that return
	 * a tailable cursor (e.g. Change
	 * Streams).
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Positive integer denoting the time limit in milliseconds for the
	 * server to block a getMore operation if no data is available. This
	 * option should only be used in conjunction with commands that return
	 * a tailable cursor (e.g. Change
	 * Streams).</p>
	 * @return array|object 
	 */
	final public function __construct (array|object $document, ?array $commandOptions = null): array|object {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Cursor class encapsulates
 * the results of a MongoDB command or query and may be returned by
 * MongoDB\Driver\Manager::executeCommand or
 * MongoDB\Driver\Manager::executeQuery, respectively.
 * @link http://www.php.net/manual/en/class.mongodb-driver-cursor.php
 */
final class Cursor implements \Iterator, \Traversable, \MongoDB\Driver\CursorInterface {

	/**
	 * Create a new Cursor (not used)
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Returns the current element
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.current.php
	 * @return array|object|null Returns the current result document as an array or object, depending on the
	 * cursor's type map. If iteration has not started or the current position
	 * is not valid, null will be returned.
	 */
	public function current (): array|object|null {}

	/**
	 * Returns the ID for this cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.getid.php
	 * @return \MongoDB\Driver\CursorId Returns the MongoDB\Driver\CursorId for this cursor.
	 */
	final public function getId (): \MongoDB\Driver\CursorId {}

	/**
	 * Returns the server associated with this cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.getserver.php
	 * @return \MongoDB\Driver\Server Returns the MongoDB\Driver\Server associated with this
	 * cursor.
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * Checks if the cursor is exhausted or may have additional results
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.isdead.php
	 * @return bool Returns true if there are definitely no additional results available on the
	 * cursor, and false otherwise.
	 */
	final public function isDead (): bool {}

	/**
	 * Returns the current result's index within the cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.key.php
	 * @return int The current result's numeric index within the cursor.
	 */
	public function key (): int {}

	/**
	 * Advances the cursor to the next result
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.next.php
	 * @return void Moves the current position to the next element in the cursor.
	 */
	public function next (): void {}

	/**
	 * Rewind the cursor to the first result
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.rewind.php
	 * @return void null.
	 */
	public function rewind (): void {}

	/**
	 * Sets a type map to use for BSON unserialization
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.settypemap.php
	 * @param array $typemap 
	 * @return void No value is returned.
	 */
	final public function setTypeMap (array $typemap): void {}

	/**
	 * Returns an array containing all results for this cursor
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.toarray.php
	 * @return array Returns an array containing all results for this cursor.
	 */
	final public function toArray (): array {}

	/**
	 * Checks if the current position in the cursor is valid
	 * @link http://www.php.net/manual/en/mongodb-driver-cursor.valid.php
	 * @return bool true if the current cursor position is valid, false otherwise.
	 */
	public function valid (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\CursorID class is a value object
 * that represents a cursor ID. Instances of this class are returned by
 * MongoDB\Driver\Cursor::getId.
 * @link http://www.php.net/manual/en/class.mongodb-driver-cursorid.php
 */
final class CursorId implements \Stringable, \Serializable {

	/**
	 * Create a new CursorId (not used)
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorid.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\CursorId {}

	/**
	 * String representation of the cursor ID
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorid.tostring.php
	 * @return string Returns the string representation of the cursor ID.
	 */
	final public function __toString (): string {}

	/**
	 * Serialize a CursorId
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorid.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\Driver\CursorId.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a CursorId
	 * @link http://www.php.net/manual/en/mongodb-driver-cursorid.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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

/**
 * The MongoDB\Driver\Manager is the main entry point
 * to the extension. It is responsible for maintaining connections to MongoDB
 * (be it standalone server, replica set, or sharded cluster).
 * <p>No connection to MongoDB is made upon instantiating the Manager.
 * This means the MongoDB\Driver\Manager can always be
 * constructed, even though one or more MongoDB servers are down.</p>
 * <p>Any write or query can throw connection exceptions as connections are created lazily.
 * A MongoDB server may also become unavailable during the life time of the script.
 * It is therefore important that all actions on the Manager to be wrapped in try/catch statements.</p>
 * @link http://www.php.net/manual/en/class.mongodb-driver-manager.php
 */
final class Manager  {

	/**
	 * Create new MongoDB Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.construct.php
	 * @param string|null $uri [optional] A mongodb:// connection URI:
	 * <pre>
	 * mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[defaultAuthDb][?options]]
	 * </pre>
	 * <p>For details on supported URI options, see
	 * Connection String Options
	 * in the MongoDB manual.
	 * Connection pool options
	 * are not supported, as the PHP driver does not implement connection pools.</p>
	 * <p>The uri is a URL, hence any special characters in
	 * its components need to be URL encoded according to
	 * RFC 3986. This is particularly
	 * relevant to the username and password, which can often include special
	 * characters such as @, :, or
	 * %. When connecting via a Unix domain socket, the socket
	 * path may contain special characters such as slashes and must be encoded.
	 * The rawurlencode function may be used to encode
	 * constituent parts of the URI.</p>
	 * <p>The defaultAuthDb component may be used to specify the
	 * database name associated with the user's credentials; however the
	 * authSource URI option will take priority if specified.
	 * If neither defaultAuthDb nor
	 * authSource are specified, the admin
	 * database will be used by default. The defaultAuthDb
	 * component has no effect in the absence of user credentials.</p>
	 * @param array|null $uriOptions [optional] Additional
	 * connection string options,
	 * which will overwrite any options with the same name in the
	 * uri parameter.
	 * <p><table>
	 * uriOptions
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>appname</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * MongoDB 3.4+ has the ability to annotate connections with metadata
	 * provided by the connecting client. This metadata is included in the
	 * server's logs upon establishing a connection and also recorded in
	 * slow query logs when database profiling is enabled.
	 * </p>
	 * <p>
	 * This option may be used to specify an application name, which will
	 * be included in the metadata. The value cannot exceed 128 characters
	 * in length.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>authMechanism</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * The authentication mechanism that MongoDB will use to authenticate
	 * the connection. For additional details and a list of supported
	 * values, see
	 * Authentication Options
	 * in the MongoDB manual.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>authMechanismProperties</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * Properties for the selected authentication mechanism. For additional
	 * details and a list of supported properties, see the
	 * Driver Authentication Specification.
	 * </p>
	 * When not specified in the URI string, this option is expressed as
	 * an array of key/value pairs. The keys and values in this array
	 * should be strings.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>authSource</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * The database name associated with the user's credentials. Defaults
	 * to the database component of the connection URI, or the
	 * admin database if both are unspecified.
	 * </p>
	 * <p>
	 * For authentication mechanisms that delegate credential storage to
	 * other services (e.g. GSSAPI), this should be
	 * "$external".
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>canonicalizeHostname</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, the driver will resolve the real hostname for the server
	 * IP address before authenticating via SASL. Some underlying GSSAPI
	 * layers already do this, but the functionality may be disabled in
	 * their config (e.g. krb.conf). Defaults to
	 * false.
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "CANONICALIZE_HOST_NAME" property of the
	 * "authMechanismProperties" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>compressors</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * A prioritized, comma-delimited list of compressors that the client
	 * wants to use. Messages are only compressed if the client and server
	 * share any compressors in common, and the compressor used in each
	 * direction will depend on the individual configuration of the server
	 * or driver. See the
	 * Driver Compression Specification
	 * for more information.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>connectTimeoutMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The time in milliseconds to attempt a connection before timing out.
	 * Defaults to 10,000 milliseconds.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>directConnection</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * This option can be used to control replica set discovery behavior
	 * when only a single host is provided in the connection string. By
	 * default, providing a single member in the connection string will
	 * establish a direct connection or discover additional members
	 * depending on whether the "replicaSet" URI option
	 * is omitted or present, respectively. Specify false to force
	 * discovery to occur (if "replicaSet" is omitted)
	 * or specify true to force a direct connection (if
	 * "replicaSet" is present).
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>gssapiServiceName</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Set the Kerberos service name when connecting to Kerberized MongoDB
	 * instances. This value must match the service name set on MongoDB
	 * instances (i.e.
	 * saslServiceName
	 * server parameter). Defaults to "mongodb".
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "SERVICE_NAME" property of the
	 * "authMechanismProperties" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>heartbeatFrequencyMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Specifies the interval in milliseconds between the driver's checks
	 * of the MongoDB topology, counted from the end of the previous check
	 * until the beginning of the next one. Defaults to 60,000
	 * milliseconds.
	 * </p>
	 * <p>
	 * Per the
	 * Server Discovery and Monitoring Specification,
	 * this value cannot be less than 500 milliseconds.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>journal</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Corresponds to the default write concern's
	 * journal parameter. If true, writes will
	 * require acknowledgement from MongoDB that the operation has been
	 * written to the journal. For details, see
	 * MongoDB\Driver\WriteConcern.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>loadBalanced</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Specifies whether the driver is connecting to a MongoDB cluster
	 * through a load balancer. If true, the driver may only connect to a
	 * single host (specified by either the connection string or SRV
	 * lookup), the "directConnection" URI option
	 * cannot be true, and the "replicaSet" URI option
	 * must be omitted. Defaults to false.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>localThresholdMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The size in milliseconds of the latency window for selecting among
	 * multiple suitable MongoDB instances while resolving a read
	 * preference. Defaults to 15 milliseconds.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxStalenessSeconds</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Corresponds to the read preference's
	 * "maxStalenessSeconds". Specifies, in seconds, how
	 * stale a secondary can be before the client stops using it for read
	 * operations. By default, there is no maximum staleness and clients
	 * will not consider a secondary’s lag when choosing where to direct a
	 * read operation. For details, see
	 * MongoDB\Driver\ReadPreference.
	 * </p>
	 * <p>
	 * If specified, the max staleness must be a signed 32-bit integer
	 * greater than or equal to
	 * MongoDB\Driver\ReadPreference::SMALLEST_MAX_STALENESS_SECONDS
	 * (i.e. 90 seconds).
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>password</td>
	 * <td>string</td>
	 * <td>
	 * The password for the user being authenticated. This option is useful
	 * if the password contains special characters, which would otherwise
	 * need to be URL encoded for the connection URI.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcernLevel</td>
	 * <td>string</td>
	 * <td>
	 * Corresponds to the read concern's level
	 * parameter. Specifies the level of read isolation. For details, see
	 * MongoDB\Driver\ReadConcern.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Corresponds to the read preference's mode
	 * parameter. Defaults to "primary". For details,
	 * see MongoDB\Driver\ReadPreference.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreferenceTags</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * Corresponds to the read preference's tagSets
	 * parameter. Tag sets allow you to target read operations to specific
	 * members of a replica set. For details, see
	 * MongoDB\Driver\ReadPreference.
	 * </p>
	 * When not specified in the URI string, this option is expressed as
	 * an array consistent with the format expected by
	 * MongoDB\Driver\ReadPreference::__construct.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>replicaSet</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Specifies the name of the replica set.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>retryReads</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Specifies whether or not the driver should automatically retry
	 * certain read operations that fail due to transient network errors
	 * or replica set elections. This functionality requires MongoDB 3.6+.
	 * Defaults to true.
	 * </p>
	 * <p>
	 * See the
	 * Retryable Reads Specification
	 * for more information.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>retryWrites</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Specifies whether or not the driver should automatically retry
	 * certain write operations that fail due to transient network errors
	 * or replica set elections. This functionality requires MongoDB 3.6+.
	 * Defaults to true.
	 * </p>
	 * <p>
	 * See
	 * Retryable Writes
	 * in the MongoDB manual for more information.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>safe</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, specifies 1 for the default write
	 * concern's w parameter. If false,
	 * 0 is specified. For details, see
	 * MongoDB\Driver\WriteConcern.
	 * </p>
	 * <p>
	 * This option is deprecated and should not be used.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>serverSelectionTimeoutMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Specifies how long in milliseconds to block for server selection
	 * before throwing an exception. Defaults to 30,000 milliseconds.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>serverSelectionTryOnce</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * When true, instructs the driver to scan the MongoDB deployment
	 * exactly once after server selection fails and then either select a
	 * server or raise an error. When false, the driver blocks and
	 * searches for a server up to the
	 * "serverSelectionTimeoutMS" value. Defaults to
	 * true.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>socketCheckIntervalMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * If a socket has not been used recently, the driver must check it via
	 * a hello command before using it for any
	 * operation. Defaults to 5,000 milliseconds.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>socketTimeoutMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The time in milliseconds to attempt a send or receive on a socket
	 * before timing out. Defaults to 300,000 milliseconds (i.e. five
	 * minutes).
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>srvMaxHosts</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The maximum number of SRV results to randomly select when initially
	 * populating the seedlist or, during SRV polling, adding new hosts to
	 * the topology. Defaults to 0 (i.e. no maximum).
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>srvServiceName</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * The service name to use for SRV lookup in initial DNS seedlist
	 * discovery and SRV polling. Defaults to "mongodb".
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ssl</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Initiates the connection with TLS/SSL if true. Defaults to
	 * false.
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the "tls"
	 * URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tls</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Initiates the connection with TLS/SSL if true. Defaults to
	 * false.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsAllowInvalidCertificates</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Specifies whether or not the driver should error when the server's
	 * TLS certificate is invalid. Defaults to false.
	 * </p>
	 * Disabling certificate validation creates a vulnerability.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsAllowInvalidHostnames</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Specifies whether or not the driver should error when there is a
	 * mismatch between the server's hostname and the hostname specified by
	 * the TLS certificate. Defaults to false.
	 * </p>
	 * Disabling certificate validation creates a vulnerability. Allowing
	 * invalid hostnames may expose the driver to a
	 * man-in-the-middle attack.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsCAFile</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Path to file with either a single or bundle of certificate
	 * authorities to be considered trusted when making a TLS connection.
	 * The system certificate store will be used by default.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsCertificateKeyFile</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Path to the client certificate file or the client private key file;
	 * in the case that they both are needed, the files should be
	 * concatenated.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsCertificateKeyFilePassword</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Password to decrypt the client private key (i.e.
	 * "tlsCertificateKeyFile" URI option) to be used
	 * for TLS connections.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsDisableCertificateRevocationCheck</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, the driver will not attempt to check certificate
	 * revocation status (e.g. OCSP, CRL). Defaults to false.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsDisableOCSPEndpointCheck</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, the driver will not attempt to contact an OCSP responder
	 * endpoint if needed (i.e. an OCSP response is not stapled). Defaults
	 * to false.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsInsecure</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Relax TLS constraints as much as possible. Specifying true for
	 * this option has the same effect as specifying true for both the
	 * "tlsAllowInvalidCertificates" and
	 * "tlsAllowInvalidHostnames" URI options. Defaults
	 * to false.
	 * </p>
	 * Disabling certificate validation creates a vulnerability. Allowing
	 * invalid hostnames may expose the driver to a
	 * man-in-the-middle attack.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>username</td>
	 * <td>string</td>
	 * <td>
	 * The username for the user being authenticated. This option is useful
	 * if the username contains special characters, which would otherwise
	 * need to be URL encoded for the connection URI.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>w</td>
	 * <td>intstring</td>
	 * <td>
	 * <p>
	 * Corresponds to the default write concern's w
	 * parameter. For details, see
	 * MongoDB\Driver\WriteConcern.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>wTimeoutMS</td>
	 * <td>intstring</td>
	 * <td>
	 * <p>
	 * Corresponds to the default write concern's
	 * wtimeout parameter. Specifies a time limit,
	 * in milliseconds, for the write concern. For details, see
	 * MongoDB\Driver\WriteConcern.
	 * </p>
	 * <p>
	 * If specified, wTimeoutMS must be a signed 32-bit
	 * integer greater than or equal to zero.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>zlibCompressionLevel</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Specifies the compression level to use for the zlib compressor. This
	 * option has no effect if zlib is not included in
	 * the "compressors" URI option. See the
	 * Driver Compression Specification
	 * for more information.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>MongoDB 3.4+ has the ability to annotate connections with metadata
	 * provided by the connecting client. This metadata is included in the
	 * server's logs upon establishing a connection and also recorded in
	 * slow query logs when database profiling is enabled.</p>
	 * <p>This option may be used to specify an application name, which will
	 * be included in the metadata. The value cannot exceed 128 characters
	 * in length.</p>
	 * <p>The authentication mechanism that MongoDB will use to authenticate
	 * the connection. For additional details and a list of supported
	 * values, see
	 * Authentication Options
	 * in the MongoDB manual.</p>
	 * <p>Properties for the selected authentication mechanism. For additional
	 * details and a list of supported properties, see the
	 * Driver Authentication Specification.</p>
	 * <p>The database name associated with the user's credentials. Defaults
	 * to the database component of the connection URI, or the
	 * admin database if both are unspecified.</p>
	 * <p>For authentication mechanisms that delegate credential storage to
	 * other services (e.g. GSSAPI), this should be
	 * "$external".</p>
	 * <p>If true, the driver will resolve the real hostname for the server
	 * IP address before authenticating via SASL. Some underlying GSSAPI
	 * layers already do this, but the functionality may be disabled in
	 * their config (e.g. krb.conf). Defaults to
	 * false.</p>
	 * <p>This option is a deprecated alias for the
	 * "CANONICALIZE_HOST_NAME" property of the
	 * "authMechanismProperties" URI option.</p>
	 * <p>A prioritized, comma-delimited list of compressors that the client
	 * wants to use. Messages are only compressed if the client and server
	 * share any compressors in common, and the compressor used in each
	 * direction will depend on the individual configuration of the server
	 * or driver. See the
	 * Driver Compression Specification
	 * for more information.</p>
	 * <p>The time in milliseconds to attempt a connection before timing out.
	 * Defaults to 10,000 milliseconds.</p>
	 * <p>This option can be used to control replica set discovery behavior
	 * when only a single host is provided in the connection string. By
	 * default, providing a single member in the connection string will
	 * establish a direct connection or discover additional members
	 * depending on whether the "replicaSet" URI option
	 * is omitted or present, respectively. Specify false to force
	 * discovery to occur (if "replicaSet" is omitted)
	 * or specify true to force a direct connection (if
	 * "replicaSet" is present).</p>
	 * <p>Set the Kerberos service name when connecting to Kerberized MongoDB
	 * instances. This value must match the service name set on MongoDB
	 * instances (i.e.
	 * saslServiceName
	 * server parameter). Defaults to "mongodb".</p>
	 * <p>This option is a deprecated alias for the
	 * "SERVICE_NAME" property of the
	 * "authMechanismProperties" URI option.</p>
	 * <p>Specifies the interval in milliseconds between the driver's checks
	 * of the MongoDB topology, counted from the end of the previous check
	 * until the beginning of the next one. Defaults to 60,000
	 * milliseconds.</p>
	 * <p>Per the
	 * Server Discovery and Monitoring Specification,
	 * this value cannot be less than 500 milliseconds.</p>
	 * <p>Corresponds to the default write concern's
	 * journal parameter. If true, writes will
	 * require acknowledgement from MongoDB that the operation has been
	 * written to the journal. For details, see
	 * MongoDB\Driver\WriteConcern.</p>
	 * <p>Specifies whether the driver is connecting to a MongoDB cluster
	 * through a load balancer. If true, the driver may only connect to a
	 * single host (specified by either the connection string or SRV
	 * lookup), the "directConnection" URI option
	 * cannot be true, and the "replicaSet" URI option
	 * must be omitted. Defaults to false.</p>
	 * <p>The size in milliseconds of the latency window for selecting among
	 * multiple suitable MongoDB instances while resolving a read
	 * preference. Defaults to 15 milliseconds.</p>
	 * <p>Corresponds to the read preference's
	 * "maxStalenessSeconds". Specifies, in seconds, how
	 * stale a secondary can be before the client stops using it for read
	 * operations. By default, there is no maximum staleness and clients
	 * will not consider a secondary’s lag when choosing where to direct a
	 * read operation. For details, see
	 * MongoDB\Driver\ReadPreference.</p>
	 * <p>If specified, the max staleness must be a signed 32-bit integer
	 * greater than or equal to
	 * MongoDB\Driver\ReadPreference::SMALLEST_MAX_STALENESS_SECONDS
	 * (i.e. 90 seconds).</p>
	 * <p>Corresponds to the read preference's mode
	 * parameter. Defaults to "primary". For details,
	 * see MongoDB\Driver\ReadPreference.</p>
	 * <p>Corresponds to the read preference's tagSets
	 * parameter. Tag sets allow you to target read operations to specific
	 * members of a replica set. For details, see
	 * MongoDB\Driver\ReadPreference.</p>
	 * <p>Specifies the name of the replica set.</p>
	 * <p>Specifies whether or not the driver should automatically retry
	 * certain read operations that fail due to transient network errors
	 * or replica set elections. This functionality requires MongoDB 3.6+.
	 * Defaults to true.</p>
	 * <p>See the
	 * Retryable Reads Specification
	 * for more information.</p>
	 * <p>Specifies whether or not the driver should automatically retry
	 * certain write operations that fail due to transient network errors
	 * or replica set elections. This functionality requires MongoDB 3.6+.
	 * Defaults to true.</p>
	 * <p>See
	 * Retryable Writes
	 * in the MongoDB manual for more information.</p>
	 * <p>If true, specifies 1 for the default write
	 * concern's w parameter. If false,
	 * 0 is specified. For details, see
	 * MongoDB\Driver\WriteConcern.</p>
	 * <p>This option is deprecated and should not be used.</p>
	 * <p>Specifies how long in milliseconds to block for server selection
	 * before throwing an exception. Defaults to 30,000 milliseconds.</p>
	 * <p>When true, instructs the driver to scan the MongoDB deployment
	 * exactly once after server selection fails and then either select a
	 * server or raise an error. When false, the driver blocks and
	 * searches for a server up to the
	 * "serverSelectionTimeoutMS" value. Defaults to
	 * true.</p>
	 * <p>If a socket has not been used recently, the driver must check it via
	 * a hello command before using it for any
	 * operation. Defaults to 5,000 milliseconds.</p>
	 * <p>The time in milliseconds to attempt a send or receive on a socket
	 * before timing out. Defaults to 300,000 milliseconds (i.e. five
	 * minutes).</p>
	 * <p>The maximum number of SRV results to randomly select when initially
	 * populating the seedlist or, during SRV polling, adding new hosts to
	 * the topology. Defaults to 0 (i.e. no maximum).</p>
	 * <p>The service name to use for SRV lookup in initial DNS seedlist
	 * discovery and SRV polling. Defaults to "mongodb".</p>
	 * <p>Initiates the connection with TLS/SSL if true. Defaults to
	 * false.</p>
	 * <p>This option is a deprecated alias for the "tls"
	 * URI option.</p>
	 * <p>Initiates the connection with TLS/SSL if true. Defaults to
	 * false.</p>
	 * <p>Specifies whether or not the driver should error when the server's
	 * TLS certificate is invalid. Defaults to false.</p>
	 * <p>Specifies whether or not the driver should error when there is a
	 * mismatch between the server's hostname and the hostname specified by
	 * the TLS certificate. Defaults to false.</p>
	 * <p>Path to file with either a single or bundle of certificate
	 * authorities to be considered trusted when making a TLS connection.
	 * The system certificate store will be used by default.</p>
	 * <p>Path to the client certificate file or the client private key file;
	 * in the case that they both are needed, the files should be
	 * concatenated.</p>
	 * <p>Password to decrypt the client private key (i.e.
	 * "tlsCertificateKeyFile" URI option) to be used
	 * for TLS connections.</p>
	 * <p>If true, the driver will not attempt to check certificate
	 * revocation status (e.g. OCSP, CRL). Defaults to false.</p>
	 * <p>If true, the driver will not attempt to contact an OCSP responder
	 * endpoint if needed (i.e. an OCSP response is not stapled). Defaults
	 * to false.</p>
	 * <p>Relax TLS constraints as much as possible. Specifying true for
	 * this option has the same effect as specifying true for both the
	 * "tlsAllowInvalidCertificates" and
	 * "tlsAllowInvalidHostnames" URI options. Defaults
	 * to false.</p>
	 * <p>Corresponds to the default write concern's w
	 * parameter. For details, see
	 * MongoDB\Driver\WriteConcern.</p>
	 * <p>Corresponds to the default write concern's
	 * wtimeout parameter. Specifies a time limit,
	 * in milliseconds, for the write concern. For details, see
	 * MongoDB\Driver\WriteConcern.</p>
	 * <p>If specified, wTimeoutMS must be a signed 32-bit
	 * integer greater than or equal to zero.</p>
	 * <p>Specifies the compression level to use for the zlib compressor. This
	 * option has no effect if zlib is not included in
	 * the "compressors" URI option. See the
	 * Driver Compression Specification
	 * for more information.</p>
	 * @param array|null $driverOptions [optional] <table>
	 * driverOptions
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>allow_invalid_hostname</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Disables hostname validation if true. Defaults to false.
	 * </p>
	 * <p>
	 * Allowing invalid hostnames may expose the driver to a
	 * man-in-the-middle attack.
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "tlsAllowInvalidHostnames" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>autoEncryption</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * Provides options to enable automatic client-side field level
	 * encryption.
	 * </p>
	 * <p>
	 * Automatic encryption is an enterprise-only feature that only
	 * applies to operations on a collection. Automatic encryption is not
	 * supported for operations on a database or view, and operations that
	 * are not bypassed will result in error (see
	 * libmongocrypt: Auto Encryption Allow-List). To bypass automatic encryption
	 * for all operations, set bypassAutoEncryption to
	 * true.
	 * </p>
	 * <p>
	 * Automatic encryption requires the authenticated user to have the
	 * listCollections
	 * privilege action.
	 * </p>
	 * <p>
	 * Explicit encryption/decryption and automatic decryption is a
	 * community feature. The driver can still automatically decrypt when
	 * bypassAutoEncryption is true.
	 * </p>
	 * <p>
	 * The following options are supported:
	 * <table>
	 * Options for automatic encryption
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultClient</td>
	 * <td>MongoDB\Driver\Manager</td>
	 * <td>The Manager used to route data key queries to a separate MongoDB cluster. By default, the current Manager and cluster is used.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultNamespace</td>
	 * <td>string</td>
	 * <td>A fully qualified namespace (e.g. "databaseName.collectionName") denoting the collection that contains all data keys used for encryption and decryption. This option is required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>kmsProviders</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.
	 * </p>
	 * <p>
	 * The format for "aws" is as follows:
	 * </p>
	 * <pre>
	 * aws: {
	 * accessKeyId: ,
	 * secretAccessKey: ,
	 * sessionToken: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "azure" is as follows:
	 * </p>
	 * <pre>
	 * azure: {
	 * tenantId: ,
	 * clientId: ,
	 * clientSecret: ,
	 * identityPlatformEndpoint: // Defaults to "login.microsoftonline.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "gcp" is as follows:
	 * </p>
	 * <pre>
	 * gcp: {
	 * email: ,
	 * privateKey: |,
	 * endpoint: // Defaults to "oauth2.googleapis.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "kmip" is as follows:
	 * </p>
	 * <pre>
	 * kmip: {
	 * endpoint: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "local" is as follows:
	 * </p>
	 * <pre>
	 * local: {
	 * // 96-byte master key used to encrypt/decrypt data keys
	 * key: |
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:
	 * </p>
	 * <pre>
	 * : {
	 * tlsCaFile: ,
	 * tlsCertificateKeyFile: ,
	 * tlsCertificateKeyFilePassword: ,
	 * tlsDisableOCSPEndpointCheck: 
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>schemaMap</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Map of collection namespaces to a local JSON schema. This is
	 * used to configure automatic encryption. See
	 * Automatic Encryption Rules
	 * in the MongoDB manual for more information. It is an error to
	 * specify a collection in both schemaMap and
	 * encryptedFieldsMap.
	 * </p>
	 * Supplying a schemaMap provides more
	 * security than relying on JSON schemas obtained from the
	 * server. It protects against a malicious server advertising a
	 * false JSON schema, which could trick the client into sending
	 * unencrypted data that should be encrypted.
	 * Schemas supplied in the schemaMap only
	 * apply to configuring automatic encryption for client side
	 * encryption. Other validation rules in the JSON schema will
	 * not be enforced by the driver and will result in an error.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>bypassAutoEncryption</td>
	 * <td>bool</td>
	 * <td>
	 * If true, mongocryptd will not be spawned
	 * automatically. This is used to disable automatic encryption.
	 * Defaults to false.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>bypassQueryAnalysis</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, automatic analysis of outgoing commands will be
	 * disabled and mongocryptd will not be
	 * spawned automatically. This enables the use case of explicit
	 * encryption for querying indexed fields without requiring the
	 * enterprise licensed crypt_shared library or
	 * mongocryptd process. Defaults to false.
	 * </p>
	 * Queryable Encryption is in public preview and available for evaluation
	 * purposes. It is not yet recommended for production deployments as breaking
	 * changes may be introduced. See the Queryable Encryption Preview blog post for more information.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>encryptedFieldsMap</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Map of collection namespaces to an
	 * encryptedFields document. This is used to
	 * configure queryable encryption. See
	 * Field Encryption and Queryability
	 * in the MongoDB manual for more information. It is an error to
	 * specify a collection in both
	 * encryptedFieldsMap and
	 * schemaMap.
	 * </p>
	 * Supplying an encryptedFieldsMap provides
	 * more security than relying on an
	 * encryptedFields obtained from the server.
	 * It protects against a malicious server advertising a false
	 * encryptedFields.
	 * Queryable Encryption is in public preview and available for evaluation
	 * purposes. It is not yet recommended for production deployments as breaking
	 * changes may be introduced. See the Queryable Encryption Preview blog post for more information.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>extraOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * The extraOptions relate to the
	 * mongocryptd process. The following options
	 * are supported:
	 * </p>
	 * <p>
	 * mongocryptdURI (string): URI to connect to an existing mongocryptd process. Defaults to "mongodb://localhost:27020".
	 * mongocryptdBypassSpawn (bool): If true, prevent the driver from spawning mongocryptd. Defaults to false.
	 * mongocryptdSpawnPath (string): Absolute path to search for mongocryptd binary. Defaults to empty string and consults system paths.
	 * mongocryptdSpawnArgs (array): Array of string arguments to pass to mongocryptd when spawning. Defaults to ["--idleShutdownTimeoutSecs=60"].
	 * cryptSharedLibPath (string): Absolute path to crypt_shared shared library. Defaults to empty string and consults system paths.
	 * cryptSharedLibRequired (bool): If true, require the driver to load crypt_shared. Defaults to false.
	 * </p>
	 * <p>
	 * See the Client-Side Encryption Specification for more information.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * Automatic encryption is an enterprise only feature that only
	 * applies to operations on a collection. Automatic encryption is not
	 * supported for operations on a database or view, and operations that
	 * are not bypassed will result in error. To bypass automatic
	 * encryption for all operations, set bypassAutoEncryption=true
	 * in autoEncryption. For more information on
	 * allowed operations, see the
	 * Client-Side Encryption Specification.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ca_dir</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Path to a correctly hashed certificate directory. The system
	 * certificate store will be used by default.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ca_file</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Path to file with either a single or bundle of certificate
	 * authorities to be considered trusted when making a TLS connection.
	 * The system certificate store will be used by default.
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "tlsCAFile" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>context</td>
	 * <td>resource</td>
	 * <td>
	 * <p>
	 * SSL context options to be used as
	 * fallbacks if a driver option or its equivalent URI option, if any,
	 * is not specified. Note that the driver does not consult the default
	 * stream context (i.e.
	 * stream_context_get_default). The following
	 * context options are supported:
	 * </p>
	 * <table>
	 * SSL context option fallbacks
	 * <table>
	 * <tr valign="top">
	 * <td>Driver option</td>
	 * <td>Context option (fallback)</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ca_dir</td>
	 * <td>capath</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>ca_file</td>
	 * <td>cafile</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>pem_file</td>
	 * <td>local_cert</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>pem_pwd</td>
	 * <td>passphrase</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>weak_cert_validation</td>
	 * <td>allow_self_signed</td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>
	 * This option is supported for backwards compatibility, but should be
	 * considered deprecated.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>crl_file</td>
	 * <td>string</td>
	 * <td>Path to a certificate revocation list file.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>disableClientPersistence</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, this Manager will use a new libmongoc client, which will
	 * not be persisted or shared with other Manager objects. When this
	 * Manager object is freed, its client will be destroyed and any
	 * connections will be closed. Defaults to false.
	 * </p>
	 * Disabling client persistence is not generally recommended.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>driver</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * Allows custom drivers to append their own metadata to the server
	 * handshake. By default, the driver submits its own name, version, and
	 * platform (i.e. PHP version) in the handshake. Custom drivers can
	 * specify strings for the "name",
	 * "version", and
	 * "platform" keys of this array, which will be
	 * appended to the respective field(s) in the handshake document.
	 * </p>
	 * Handshake information is limited to 512 bytes. The driver will
	 * truncate handshake data to fit within this 512-byte string. Drivers
	 * and ODMs are encouraged to keep their own metadata concise.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>pem_file</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Path to a PEM encoded certificate to use for client authentication.
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "tlsCertificateKeyFile" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>pem_pwd</td>
	 * <td>string</td>
	 * <td>
	 * <p>
	 * Passphrase for the PEM encoded certificate (if applicable).
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "tlsCertificateKeyFilePassword" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>serverApi</td>
	 * <td>MongoDB\Driver\ServerApi</td>
	 * <td>
	 * <p>
	 * This option is used to declare a server API version for the manager.
	 * If omitted, no API version is declared.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>weak_cert_validation</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Disables certificate validation if true. Defaults to false
	 * </p>
	 * <p>
	 * This option is a deprecated alias for the
	 * "tlsAllowInvalidCertificates" URI option.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Disables hostname validation if true. Defaults to false.</p>
	 * <p>Allowing invalid hostnames may expose the driver to a
	 * man-in-the-middle attack.</p>
	 * <p>This option is a deprecated alias for the
	 * "tlsAllowInvalidHostnames" URI option.</p>
	 * <p>Provides options to enable automatic client-side field level
	 * encryption.</p>
	 * <p>Automatic encryption is an enterprise-only feature that only
	 * applies to operations on a collection. Automatic encryption is not
	 * supported for operations on a database or view, and operations that
	 * are not bypassed will result in error (see
	 * libmongocrypt: Auto Encryption Allow-List). To bypass automatic encryption
	 * for all operations, set bypassAutoEncryption to
	 * true.</p>
	 * <p>Automatic encryption requires the authenticated user to have the
	 * listCollections
	 * privilege action.</p>
	 * <p>Explicit encryption/decryption and automatic decryption is a
	 * community feature. The driver can still automatically decrypt when
	 * bypassAutoEncryption is true.</p>
	 * <p>The following options are supported:
	 * <table>
	 * Options for automatic encryption
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultClient</td>
	 * <td>MongoDB\Driver\Manager</td>
	 * <td>The Manager used to route data key queries to a separate MongoDB cluster. By default, the current Manager and cluster is used.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultNamespace</td>
	 * <td>string</td>
	 * <td>A fully qualified namespace (e.g. "databaseName.collectionName") denoting the collection that contains all data keys used for encryption and decryption. This option is required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>kmsProviders</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.
	 * </p>
	 * <p>
	 * The format for "aws" is as follows:
	 * </p>
	 * <pre>
	 * aws: {
	 * accessKeyId: ,
	 * secretAccessKey: ,
	 * sessionToken: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "azure" is as follows:
	 * </p>
	 * <pre>
	 * azure: {
	 * tenantId: ,
	 * clientId: ,
	 * clientSecret: ,
	 * identityPlatformEndpoint: // Defaults to "login.microsoftonline.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "gcp" is as follows:
	 * </p>
	 * <pre>
	 * gcp: {
	 * email: ,
	 * privateKey: |,
	 * endpoint: // Defaults to "oauth2.googleapis.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "kmip" is as follows:
	 * </p>
	 * <pre>
	 * kmip: {
	 * endpoint: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "local" is as follows:
	 * </p>
	 * <pre>
	 * local: {
	 * // 96-byte master key used to encrypt/decrypt data keys
	 * key: |
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:
	 * </p>
	 * <pre>
	 * : {
	 * tlsCaFile: ,
	 * tlsCertificateKeyFile: ,
	 * tlsCertificateKeyFilePassword: ,
	 * tlsDisableOCSPEndpointCheck: 
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>schemaMap</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Map of collection namespaces to a local JSON schema. This is
	 * used to configure automatic encryption. See
	 * Automatic Encryption Rules
	 * in the MongoDB manual for more information. It is an error to
	 * specify a collection in both schemaMap and
	 * encryptedFieldsMap.
	 * </p>
	 * Supplying a schemaMap provides more
	 * security than relying on JSON schemas obtained from the
	 * server. It protects against a malicious server advertising a
	 * false JSON schema, which could trick the client into sending
	 * unencrypted data that should be encrypted.
	 * Schemas supplied in the schemaMap only
	 * apply to configuring automatic encryption for client side
	 * encryption. Other validation rules in the JSON schema will
	 * not be enforced by the driver and will result in an error.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>bypassAutoEncryption</td>
	 * <td>bool</td>
	 * <td>
	 * If true, mongocryptd will not be spawned
	 * automatically. This is used to disable automatic encryption.
	 * Defaults to false.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>bypassQueryAnalysis</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, automatic analysis of outgoing commands will be
	 * disabled and mongocryptd will not be
	 * spawned automatically. This enables the use case of explicit
	 * encryption for querying indexed fields without requiring the
	 * enterprise licensed crypt_shared library or
	 * mongocryptd process. Defaults to false.
	 * </p>
	 * Queryable Encryption is in public preview and available for evaluation
	 * purposes. It is not yet recommended for production deployments as breaking
	 * changes may be introduced. See the Queryable Encryption Preview blog post for more information.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>encryptedFieldsMap</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Map of collection namespaces to an
	 * encryptedFields document. This is used to
	 * configure queryable encryption. See
	 * Field Encryption and Queryability
	 * in the MongoDB manual for more information. It is an error to
	 * specify a collection in both
	 * encryptedFieldsMap and
	 * schemaMap.
	 * </p>
	 * Supplying an encryptedFieldsMap provides
	 * more security than relying on an
	 * encryptedFields obtained from the server.
	 * It protects against a malicious server advertising a false
	 * encryptedFields.
	 * Queryable Encryption is in public preview and available for evaluation
	 * purposes. It is not yet recommended for production deployments as breaking
	 * changes may be introduced. See the Queryable Encryption Preview blog post for more information.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>extraOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * The extraOptions relate to the
	 * mongocryptd process. The following options
	 * are supported:
	 * </p>
	 * <p>
	 * mongocryptdURI (string): URI to connect to an existing mongocryptd process. Defaults to "mongodb://localhost:27020".
	 * mongocryptdBypassSpawn (bool): If true, prevent the driver from spawning mongocryptd. Defaults to false.
	 * mongocryptdSpawnPath (string): Absolute path to search for mongocryptd binary. Defaults to empty string and consults system paths.
	 * mongocryptdSpawnArgs (array): Array of string arguments to pass to mongocryptd when spawning. Defaults to ["--idleShutdownTimeoutSecs=60"].
	 * cryptSharedLibPath (string): Absolute path to crypt_shared shared library. Defaults to empty string and consults system paths.
	 * cryptSharedLibRequired (bool): If true, require the driver to load crypt_shared. Defaults to false.
	 * </p>
	 * <p>
	 * See the Client-Side Encryption Specification for more information.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.</p>
	 * <p>The format for "aws" is as follows:</p>
	 * <p>The format for "azure" is as follows:</p>
	 * <p>The format for "gcp" is as follows:</p>
	 * <p>The format for "kmip" is as follows:</p>
	 * <p>The format for "local" is as follows:</p>
	 * <p>A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:</p>
	 * <p>Map of collection namespaces to a local JSON schema. This is
	 * used to configure automatic encryption. See
	 * Automatic Encryption Rules
	 * in the MongoDB manual for more information. It is an error to
	 * specify a collection in both schemaMap and
	 * encryptedFieldsMap.</p>
	 * <p>If true, automatic analysis of outgoing commands will be
	 * disabled and mongocryptd will not be
	 * spawned automatically. This enables the use case of explicit
	 * encryption for querying indexed fields without requiring the
	 * enterprise licensed crypt_shared library or
	 * mongocryptd process. Defaults to false.</p>
	 * <p>Map of collection namespaces to an
	 * encryptedFields document. This is used to
	 * configure queryable encryption. See
	 * Field Encryption and Queryability
	 * in the MongoDB manual for more information. It is an error to
	 * specify a collection in both
	 * encryptedFieldsMap and
	 * schemaMap.</p>
	 * <p>The extraOptions relate to the
	 * mongocryptd process. The following options
	 * are supported:</p>
	 * <p>See the Client-Side Encryption Specification for more information.</p>
	 * <p>Path to a correctly hashed certificate directory. The system
	 * certificate store will be used by default.</p>
	 * <p>Path to file with either a single or bundle of certificate
	 * authorities to be considered trusted when making a TLS connection.
	 * The system certificate store will be used by default.</p>
	 * <p>This option is a deprecated alias for the
	 * "tlsCAFile" URI option.</p>
	 * <p>SSL context options to be used as
	 * fallbacks if a driver option or its equivalent URI option, if any,
	 * is not specified. Note that the driver does not consult the default
	 * stream context (i.e.
	 * stream_context_get_default). The following
	 * context options are supported:</p>
	 * <p>This option is supported for backwards compatibility, but should be
	 * considered deprecated.</p>
	 * <p>If true, this Manager will use a new libmongoc client, which will
	 * not be persisted or shared with other Manager objects. When this
	 * Manager object is freed, its client will be destroyed and any
	 * connections will be closed. Defaults to false.</p>
	 * <p>Allows custom drivers to append their own metadata to the server
	 * handshake. By default, the driver submits its own name, version, and
	 * platform (i.e. PHP version) in the handshake. Custom drivers can
	 * specify strings for the "name",
	 * "version", and
	 * "platform" keys of this array, which will be
	 * appended to the respective field(s) in the handshake document.</p>
	 * <p>Path to a PEM encoded certificate to use for client authentication.</p>
	 * <p>This option is a deprecated alias for the
	 * "tlsCertificateKeyFile" URI option.</p>
	 * <p>Passphrase for the PEM encoded certificate (if applicable).</p>
	 * <p>This option is a deprecated alias for the
	 * "tlsCertificateKeyFilePassword" URI option.</p>
	 * <p>This option is used to declare a server API version for the manager.
	 * If omitted, no API version is declared.</p>
	 * <p>Disables certificate validation if true. Defaults to false</p>
	 * <p>This option is a deprecated alias for the
	 * "tlsAllowInvalidCertificates" URI option.</p>
	 * @return string|null 
	 */
	final public function __construct (?string $uri = null, ?array $uriOptions = null, ?array $driverOptions = null): ?string {}

	/**
	 * Registers a monitoring event subscriber with this Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.addsubscriber.php
	 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber A monitoring event subscriber to register with this Manager.
	 * @return void No value is returned.
	 */
	final public function addSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

	/**
	 * Create a new ClientEncryption object
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.createclientencryption.php
	 * @param array $options <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultClient</td>
	 * <td>MongoDB\Driver\Manager</td>
	 * <td>The Manager used to route data key queries to a separate MongoDB cluster. By default, the current Manager and cluster is used.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>keyVaultNamespace</td>
	 * <td>string</td>
	 * <td>A fully qualified namespace (e.g. "databaseName.collectionName") denoting the collection that contains all data keys used for encryption and decryption. This option is required.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>kmsProviders</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.
	 * </p>
	 * <p>
	 * The format for "aws" is as follows:
	 * </p>
	 * <pre>
	 * aws: {
	 * accessKeyId: ,
	 * secretAccessKey: ,
	 * sessionToken: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "azure" is as follows:
	 * </p>
	 * <pre>
	 * azure: {
	 * tenantId: ,
	 * clientId: ,
	 * clientSecret: ,
	 * identityPlatformEndpoint: // Defaults to "login.microsoftonline.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "gcp" is as follows:
	 * </p>
	 * <pre>
	 * gcp: {
	 * email: ,
	 * privateKey: |,
	 * endpoint: // Defaults to "oauth2.googleapis.com"
	 * }
	 * </pre>
	 * <p>
	 * The format for "kmip" is as follows:
	 * </p>
	 * <pre>
	 * kmip: {
	 * endpoint: 
	 * }
	 * </pre>
	 * <p>
	 * The format for "local" is as follows:
	 * </p>
	 * <pre>
	 * local: {
	 * // 96-byte master key used to encrypt/decrypt data keys
	 * key: |
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tlsOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:
	 * </p>
	 * <pre>
	 * : {
	 * tlsCaFile: ,
	 * tlsCertificateKeyFile: ,
	 * tlsCertificateKeyFilePassword: ,
	 * tlsDisableOCSPEndpointCheck: 
	 * }
	 * </pre>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A document containing the configuration for one or more KMS providers, which are used to encrypt data keys. Supported providers include "aws", "azure", "gcp", "kmip", and "local" and at least one must be specified.</p>
	 * <p>The format for "aws" is as follows:</p>
	 * <p>The format for "azure" is as follows:</p>
	 * <p>The format for "gcp" is as follows:</p>
	 * <p>The format for "kmip" is as follows:</p>
	 * <p>The format for "local" is as follows:</p>
	 * <p>A document containing the TLS configuration for one or more KMS providers. Supported providers include "aws", "azure", "gcp", and "kmip". All providers support the following options:</p>
	 * @return \MongoDB\Driver\ClientEncryption Returns a new MongoDB\Driver\ClientEncryption instance.
	 */
	final public function createClientEncryption (array $options): \MongoDB\Driver\ClientEncryption {}

	/**
	 * Execute one or more write operations
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.executebulkwrite.php
	 * @param string $namespace A fully qualified namespace (e.g. "databaseName.collectionName").
	 * @param \MongoDB\Driver\BulkWrite $bulk The write(s) to execute.
	 * @param array|\MongoDB\Driver\WriteConcern|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * @return \MongoDB\Driver\WriteResult >Returns MongoDB\Driver\WriteResult on success.
	 */
	final public function executeBulkWrite (string $namespace, \MongoDB\Driver\BulkWrite $bulk, array|\MongoDB\Driver\WriteConcern|null $options = null): \MongoDB\Driver\WriteResult {}

	/**
	 * Execute a database command
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.executecommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|\MongoDB\Driver\ReadPreference|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeCommand (string $db, \MongoDB\Driver\Command $command, array|\MongoDB\Driver\ReadPreference|null $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database query
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.executequery.php
	 * @param string $namespace A fully qualified namespace (e.g. "databaseName.collectionName").
	 * @param \MongoDB\Driver\Query $query The query to execute.
	 * @param array|\MongoDB\Driver\ReadPreference|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A session to associate with the operation.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeQuery (string $namespace, \MongoDB\Driver\Query $query, array|\MongoDB\Driver\ReadPreference|null $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database command that reads
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.executereadcommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A session to associate with the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeReadCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database command that reads and writes
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.executereadwritecommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeReadWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database command that writes
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.executewritecommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Return the encryptedFieldsMap auto encryption option for the Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.getencryptedfieldsmap.php
	 * @return array|object|null The encryptedFieldsMap auto encryption option for the
	 * Manager, or null if it was not specified.
	 */
	final public function getEncryptedFieldsMap (): array|object|null {}

	/**
	 * Return the ReadConcern for the Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.getreadconcern.php
	 * @return \MongoDB\Driver\ReadConcern The MongoDB\Driver\ReadConcern for the Manager.
	 */
	final public function getReadConcern (): \MongoDB\Driver\ReadConcern {}

	/**
	 * Return the ReadPreference for the Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.getreadpreference.php
	 * @return \MongoDB\Driver\ReadPreference The MongoDB\Driver\ReadPreference for the Manager.
	 */
	final public function getReadPreference (): \MongoDB\Driver\ReadPreference {}

	/**
	 * Return the servers to which this manager is connected
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.getservers.php
	 * @return array Returns an array of MongoDB\Driver\Server
	 * instances to which this manager is connected.
	 */
	final public function getServers (): array {}

	/**
	 * Return the WriteConcern for the Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.getwriteconcern.php
	 * @return \MongoDB\Driver\WriteConcern The MongoDB\Driver\WriteConcern for the Manager.
	 */
	final public function getWriteConcern (): \MongoDB\Driver\WriteConcern {}

	/**
	 * Unregisters a monitoring event subscriber with this Manager
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.removesubscriber.php
	 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber A monitoring event subscriber to unregister with this Manager.
	 * @return void No value is returned.
	 */
	final public function removeSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

	/**
	 * Select a server matching a read preference
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.selectserver.php
	 * @param \MongoDB\Driver\ReadPreference|null $readPreference [optional] The read preference to use for selecting a server. If null or omitted,
	 * the primary server will be selected by default.
	 * @return \MongoDB\Driver\Server Returns a MongoDB\Driver\Server matching the read
	 * preference.
	 */
	final public function selectServer (?\MongoDB\Driver\ReadPreference $readPreference = null): \MongoDB\Driver\Server {}

	/**
	 * Start a new client session for use with this client
	 * @link http://www.php.net/manual/en/mongodb-driver-manager.startsession.php
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * <td>Default</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>causalConsistency</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Configure causal consistency in a session. If true, each operation
	 * in the session will be causally ordered after the previous read or
	 * write operation. Set to false to disable causal consistency.
	 * </p>
	 * <p>
	 * See
	 * Casual Consistency
	 * in the MongoDB manual for more information.
	 * </p>
	 * </td>
	 * <td>true</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>defaultTransactionOptions</td>
	 * <td>array</td>
	 * <td>
	 * <p>
	 * Default options to apply to newly created transactions. These
	 * options are used unless they are overridden when a transaction is
	 * started with different value for each option.
	 * </p>
	 * <p>
	 * <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxCommitTimeMS</td>
	 * <td>integer</td>
	 * <td>
	 * <p>
	 * The maximum amount of time in milliseconds to allow a single
	 * commitTransaction command to run.
	 * </p>
	 * <p>
	 * If specified, maxCommitTimeMS must be a signed
	 * 32-bit integer greater than or equal to zero.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * </p>
	 * <p>
	 * This option is available in MongoDB 4.0+.
	 * </p>
	 * </td>
	 * <td>[]</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>snapshot</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Configure snapshot reads in a session. If true, a timestamp will
	 * be obtained from the first supported read operation in the session
	 * (i.e. find, aggregate, or
	 * unsharded distinct). Subsequent read operations
	 * within the session will then utilize a "snapshot"
	 * read concern level to read majority-committed data from that
	 * timestamp. Set to false to disable snapshot reads.
	 * </p>
	 * <p>
	 * Snapshot reads require MongoDB 5.0+ and cannot be used with causal
	 * consistency, transactions, or write operations. If
	 * "snapshot" is true,
	 * "causalConsistency" will default to false.
	 * </p>
	 * <p>
	 * See
	 * Read Concern "snapshot"
	 * in the MongoDB manual for more information.
	 * </p>
	 * </td>
	 * <td>false</td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Configure causal consistency in a session. If true, each operation
	 * in the session will be causally ordered after the previous read or
	 * write operation. Set to false to disable causal consistency.</p>
	 * <p>See
	 * Casual Consistency
	 * in the MongoDB manual for more information.</p>
	 * <p>Default options to apply to newly created transactions. These
	 * options are used unless they are overridden when a transaction is
	 * started with different value for each option.</p>
	 * <p><table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxCommitTimeMS</td>
	 * <td>integer</td>
	 * <td>
	 * <p>
	 * The maximum amount of time in milliseconds to allow a single
	 * commitTransaction command to run.
	 * </p>
	 * <p>
	 * If specified, maxCommitTimeMS must be a signed
	 * 32-bit integer greater than or equal to zero.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>The maximum amount of time in milliseconds to allow a single
	 * commitTransaction command to run.</p>
	 * <p>If specified, maxCommitTimeMS must be a signed
	 * 32-bit integer greater than or equal to zero.</p>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 4.0+.</p>
	 * <p>Configure snapshot reads in a session. If true, a timestamp will
	 * be obtained from the first supported read operation in the session
	 * (i.e. find, aggregate, or
	 * unsharded distinct). Subsequent read operations
	 * within the session will then utilize a "snapshot"
	 * read concern level to read majority-committed data from that
	 * timestamp. Set to false to disable snapshot reads.</p>
	 * <p>Snapshot reads require MongoDB 5.0+ and cannot be used with causal
	 * consistency, transactions, or write operations. If
	 * "snapshot" is true,
	 * "causalConsistency" will default to false.</p>
	 * <p>See
	 * Read Concern "snapshot"
	 * in the MongoDB manual for more information.</p>
	 * @return \MongoDB\Driver\Session Returns a MongoDB\Driver\Session.
	 */
	final public function startSession (?array $options = null): \MongoDB\Driver\Session {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Query class is a value object that
 * represents a database query.
 * @link http://www.php.net/manual/en/class.mongodb-driver-query.php
 */
final class Query  {

	/**
	 * Create a new Query
	 * @link http://www.php.net/manual/en/mongodb-driver-query.construct.php
	 * @param array|object $filter The query predicate.
	 * An empty predicate will match all documents in the collection.
	 * @param array|null $queryOptions [optional] <table>
	 * queryOptions
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>allowDiskUse</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Allows MongoDB to use temporary disk files to store data exceeding
	 * the 100 megabyte system memory limit while processing a blocking
	 * sort operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>allowPartialResults</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * For queries against a sharded collection, returns partial results
	 * from the mongos if some shards are unavailable instead of throwing
	 * an error.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "partial" option if
	 * not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>awaitData</td>
	 * <td>bool</td>
	 * <td>
	 * Use in conjunction with the "tailable" option to
	 * block a getMore operation on the cursor temporarily if at the end of
	 * data rather than returning no data. After a timeout period, the query
	 * returns as normal.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>batchSize</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The number of documents to return in the first batch. Defaults to
	 * 101. A batch size of 0 means that the cursor will be established,
	 * but no documents will be returned in the first batch.
	 * </p>
	 * <p>
	 * In versions of MongoDB before 3.2, where queries use the legacy wire
	 * protocol OP_QUERY, a batch size of 1 will close the cursor
	 * irrespective of the number of matched documents.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>collation</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Collation allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks. When specifying collation, the "locale" field is mandatory; all other collation fields are optional. For descriptions of the fields, see Collation Document.
	 * </p>
	 * <p>
	 * If the collation is unspecified but the collection has a default collation, the operation uses the collation specified for the collection. If no collation is specified for the collection or for the operation, MongoDB uses the simple binary comparison used in prior versions for string comparisons.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.4+ and will result in an exception at execution time if specified for an older server version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>comment</td>
	 * <td>mixed</td>
	 * <td>
	 * <p>
	 * An arbitrary comment to help trace the operation through the
	 * database profiler, currentOp output, and logs.
	 * </p>
	 * <p>
	 * The comment can be any valid BSON type for MongoDB 4.4+. Earlier
	 * server versions only support string values.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$comment" modifier
	 * if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>exhaust</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Stream the data down full blast in multiple "more" packages, on the
	 * assumption that the client will fully read all data queried. Faster
	 * when you are pulling a lot of data and know you want to pull it all
	 * down. Note: the client is not allowed to not read all the data
	 * unless it closes the connection.
	 * </p>
	 * <p>
	 * This option is not supported by the find command in MongoDB 3.2+ and
	 * will force the driver to use the legacy wire protocol version (i.e.
	 * OP_QUERY).
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>explain</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, the returned MongoDB\Driver\Cursor
	 * will contain a single document that describes the process and
	 * indexes used to return the query.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$explain" modifier
	 * if not specified.
	 * </p>
	 * <p>
	 * This option is not supported by the find command in MongoDB 3.2+ and
	 * will only be respected when using the legacy wire protocol version
	 * (i.e. OP_QUERY). The
	 * explain
	 * command should be used on MongoDB 3.0+.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>hint</td>
	 * <td>stringarrayobject</td>
	 * <td>
	 * <p>
	 * Index specification. Specify either the index name as a string or
	 * the index key pattern. If specified, then the query system will only
	 * consider plans using the hinted index.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "hint" option if not
	 * specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>let</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * Map of parameter names and values. Values must be constant or closed expressions that do not reference document fields. Parameters can then be accessed as variables in an aggregate expression context (e.g. $$var).
	 * </p>
	 * <p>
	 * This option is available in MongoDB 5.0+ and will result in an exception at execution time if specified for an older server version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>limit</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The maximum number of documents to return. If unspecified, then
	 * defaults to no limit. A limit of 0 is equivalent to setting no
	 * limit.
	 * </p>
	 * <p>
	 * A negative limit is will be interpreted as a positive limit with the
	 * "singleBatch" option set to true. This behavior
	 * is supported for backwards compatibility, but should be considered
	 * deprecated.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>max</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * The exclusive upper bound for a specific index.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$max" modifier if
	 * not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxAwaitTimeMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Positive integer denoting the time limit in milliseconds for the
	 * server to block a getMore operation if no data is available. This
	 * option should only be used in conjunction with the
	 * "tailable" and "awaitData"
	 * options.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxScan</td>
	 * <td>int</td>
	 * <td>
	 * This option is deprecated and should not be used.
	 * <p>
	 * Positive integer denoting the maximum number of documents or index
	 * keys to scan when executing the query.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$maxScan" modifier
	 * if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxTimeMS</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * The cumulative time limit in milliseconds for processing operations
	 * on the cursor. MongoDB aborts the operation at the earliest
	 * following interrupt point.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$maxTimeMS"
	 * modifier if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>min</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * The inclusive lower bound for a specific index.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$min" modifier if
	 * not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>modifiers</td>
	 * <td>array</td>
	 * <td>
	 * Meta operators
	 * modifying the output or behavior of a query. Use of these operators
	 * is deprecated in favor of named options.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>noCursorTimeout</td>
	 * <td>bool</td>
	 * <td>
	 * Prevents the server from timing out idle cursors after an inactivity
	 * period (10 minutes).
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>oplogReplay</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Internal use for replica sets. To use oplogReplay, you must include
	 * the following condition in the filter:
	 * </p>
	 * <p>
	 * <pre>
	 * [ 'ts' => [ '$gte' => ] ]
	 * </pre>
	 * </p>
	 * This option is deprecated as of the 1.8.0 release.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>projection</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>
	 * The projection specification
	 * to determine which fields to include in the returned documents.
	 * </p>
	 * <p>
	 * If you are using the ODM
	 * functionality to deserialise documents as their original
	 * PHP class, make sure that you include the
	 * __pclass field in the projection. This is
	 * required for the deserialization to work and without it, the
	 * driver will return (by default) a stdClass
	 * object instead.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation. By default, the read
	 * concern from the
	 * MongoDB
	 * Connection URI will be used.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>returnKey</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * If true, returns only the index keys in the resulting documents.
	 * Default value is false. If true and the find command does not
	 * use an index, the returned documents will be empty.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$returnKey"
	 * modifier if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>showRecordId</td>
	 * <td>bool</td>
	 * <td>
	 * <p>
	 * Determines whether to return the record identifier for each
	 * document. If true, adds a top-level "$recordId"
	 * field to the returned documents.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$showDiskLoc"
	 * modifier if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>singleBatch</td>
	 * <td>bool</td>
	 * <td>
	 * Determines whether to close the cursor after the first batch.
	 * Defaults to false.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>skip</td>
	 * <td>int</td>
	 * <td>Number of documents to skip. Defaults to 0.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>snapshot</td>
	 * <td>bool</td>
	 * <td>
	 * This option is deprecated and should not be used.
	 * <p>
	 * Prevents the cursor from returning a document more than once because
	 * of an intervening write operation.
	 * </p>
	 * <p>
	 * Falls back to the deprecated "$snapshot" modifier
	 * if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>sort</td>
	 * <td>arrayobject</td>
	 * <td>
	 * <p>The sort specification for the ordering of the results.</p>
	 * <p>
	 * Falls back to the deprecated "$orderby" modifier
	 * if not specified.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>tailable</td>
	 * <td>bool</td>
	 * <td>Returns a tailable cursor for a capped collection.</td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Allows MongoDB to use temporary disk files to store data exceeding
	 * the 100 megabyte system memory limit while processing a blocking
	 * sort operation.</p>
	 * <p>For queries against a sharded collection, returns partial results
	 * from the mongos if some shards are unavailable instead of throwing
	 * an error.</p>
	 * <p>Falls back to the deprecated "partial" option if
	 * not specified.</p>
	 * <p>The number of documents to return in the first batch. Defaults to
	 * 101. A batch size of 0 means that the cursor will be established,
	 * but no documents will be returned in the first batch.</p>
	 * <p>In versions of MongoDB before 3.2, where queries use the legacy wire
	 * protocol OP_QUERY, a batch size of 1 will close the cursor
	 * irrespective of the number of matched documents.</p>
	 * <p>Collation allows users to specify language-specific rules for string comparison, such as rules for lettercase and accent marks. When specifying collation, the "locale" field is mandatory; all other collation fields are optional. For descriptions of the fields, see Collation Document.</p>
	 * <p>If the collation is unspecified but the collection has a default collation, the operation uses the collation specified for the collection. If no collation is specified for the collection or for the operation, MongoDB uses the simple binary comparison used in prior versions for string comparisons.</p>
	 * <p>This option is available in MongoDB 3.4+ and will result in an exception at execution time if specified for an older server version.</p>
	 * <p>An arbitrary comment to help trace the operation through the
	 * database profiler, currentOp output, and logs.</p>
	 * <p>The comment can be any valid BSON type for MongoDB 4.4+. Earlier
	 * server versions only support string values.</p>
	 * <p>Falls back to the deprecated "$comment" modifier
	 * if not specified.</p>
	 * <p>Stream the data down full blast in multiple "more" packages, on the
	 * assumption that the client will fully read all data queried. Faster
	 * when you are pulling a lot of data and know you want to pull it all
	 * down. Note: the client is not allowed to not read all the data
	 * unless it closes the connection.</p>
	 * <p>This option is not supported by the find command in MongoDB 3.2+ and
	 * will force the driver to use the legacy wire protocol version (i.e.
	 * OP_QUERY).</p>
	 * <p>If true, the returned MongoDB\Driver\Cursor
	 * will contain a single document that describes the process and
	 * indexes used to return the query.</p>
	 * <p>Falls back to the deprecated "$explain" modifier
	 * if not specified.</p>
	 * <p>This option is not supported by the find command in MongoDB 3.2+ and
	 * will only be respected when using the legacy wire protocol version
	 * (i.e. OP_QUERY). The
	 * explain
	 * command should be used on MongoDB 3.0+.</p>
	 * <p>Index specification. Specify either the index name as a string or
	 * the index key pattern. If specified, then the query system will only
	 * consider plans using the hinted index.</p>
	 * <p>Falls back to the deprecated "hint" option if not
	 * specified.</p>
	 * <p>Map of parameter names and values. Values must be constant or closed expressions that do not reference document fields. Parameters can then be accessed as variables in an aggregate expression context (e.g. $$var).</p>
	 * <p>This option is available in MongoDB 5.0+ and will result in an exception at execution time if specified for an older server version.</p>
	 * <p>The maximum number of documents to return. If unspecified, then
	 * defaults to no limit. A limit of 0 is equivalent to setting no
	 * limit.</p>
	 * <p>A negative limit is will be interpreted as a positive limit with the
	 * "singleBatch" option set to true. This behavior
	 * is supported for backwards compatibility, but should be considered
	 * deprecated.</p>
	 * <p>The exclusive upper bound for a specific index.</p>
	 * <p>Falls back to the deprecated "$max" modifier if
	 * not specified.</p>
	 * <p>Positive integer denoting the time limit in milliseconds for the
	 * server to block a getMore operation if no data is available. This
	 * option should only be used in conjunction with the
	 * "tailable" and "awaitData"
	 * options.</p>
	 * <p>Positive integer denoting the maximum number of documents or index
	 * keys to scan when executing the query.</p>
	 * <p>Falls back to the deprecated "$maxScan" modifier
	 * if not specified.</p>
	 * <p>The cumulative time limit in milliseconds for processing operations
	 * on the cursor. MongoDB aborts the operation at the earliest
	 * following interrupt point.</p>
	 * <p>Falls back to the deprecated "$maxTimeMS"
	 * modifier if not specified.</p>
	 * <p>The inclusive lower bound for a specific index.</p>
	 * <p>Falls back to the deprecated "$min" modifier if
	 * not specified.</p>
	 * <p>Internal use for replica sets. To use oplogReplay, you must include
	 * the following condition in the filter:</p>
	 * <p><pre>
	 * [ 'ts' => [ '$gte' => ] ]
	 * </pre></p>
	 * <p>The projection specification
	 * to determine which fields to include in the returned documents.</p>
	 * <p>If you are using the ODM
	 * functionality to deserialise documents as their original
	 * PHP class, make sure that you include the
	 * __pclass field in the projection. This is
	 * required for the deserialization to work and without it, the
	 * driver will return (by default) a stdClass
	 * object instead.</p>
	 * <p>A read concern to apply to the operation. By default, the read
	 * concern from the
	 * MongoDB
	 * Connection URI will be used.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>If true, returns only the index keys in the resulting documents.
	 * Default value is false. If true and the find command does not
	 * use an index, the returned documents will be empty.</p>
	 * <p>Falls back to the deprecated "$returnKey"
	 * modifier if not specified.</p>
	 * <p>Determines whether to return the record identifier for each
	 * document. If true, adds a top-level "$recordId"
	 * field to the returned documents.</p>
	 * <p>Falls back to the deprecated "$showDiskLoc"
	 * modifier if not specified.</p>
	 * <p>Prevents the cursor from returning a document more than once because
	 * of an intervening write operation.</p>
	 * <p>Falls back to the deprecated "$snapshot" modifier
	 * if not specified.</p>
	 * <p>The sort specification for the ordering of the results.</p>
	 * <p>Falls back to the deprecated "$orderby" modifier
	 * if not specified.</p>
	 * @return array|object 
	 */
	final public function __construct (array|object $filter, ?array $queryOptions = null): array|object {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * MongoDB\Driver\ReadConcern controls the level of
 * isolation for read operations for replica sets and replica set shards. This
 * option requires MongoDB 3.2 or later.
 * @link http://www.php.net/manual/en/class.mongodb-driver-readconcern.php
 */
final class ReadConcern implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	/**
	 * The query returns data that reflects all successful writes issued with a
	 * write concern of "majority" and
	 * acknowledged prior to the start of the read operation. For replica sets
	 * that run with writeConcernMajorityJournalDefault set
	 * to true, linearizable read concern returns data that will never be
	 * rolled back.
	 * <p>With writeConcernMajorityJournalDefault set to
	 * false, MongoDB will not wait for w: "majority"
	 * writes to be durable before acknowledging the writes. As such,
	 * "majority" write operations could possibly roll back
	 * in the event of a loss of a replica set member.</p>
	 * <p>You can specify linearizable read concern for read operations on the
	 * primary only.</p>
	 * <p>Linearizable read concern guarantees only apply if read
	 * operations specify a query filter that uniquely identifies a single
	 * document.</p>
	 * <p>Linearizable read concern requires MongoDB 3.4.</p>
	const LINEARIZABLE = "linearizable";
	/**
	 * Default for reads against primary if level is
	 * unspecified and for reads against secondaries if level
	 * is unspecified but afterClusterTime is specified.
	 * <p>The query returns the instance's most recent data. Provides no
	 * guarantee that the data has been written to a majority of the replica set
	 * members (i.e. may be rolled back).</p>
	const LOCAL = "local";
	/**
	 * The query returns the instance's most recent data acknowledged as
	 * having been written to a majority of members in the replica set.
	 * <p>To use read concern level of "majority", replica sets
	 * must use WiredTiger storage engine and election protocol version 1.</p>
	const MAJORITY = "majority";
	/**
	 * Default for reads against secondaries when
	 * afterClusterTimeand level are
	 * unspecified.
	 * <p>The query returns the instance's most recent data. Provides no
	 * guarantee that the data has been written to a majority of the replica set
	 * members (i.e. may be rolled back).</p>
	 * <p>For unsharded collections (including collections in a standalone
	 * deployment or a replica set deployment), "local" and
	 * "available" read concerns behave identically.</p>
	 * <p>For a sharded cluster, "available" read concern
	 * provides greater tolerance for partitions since it does not wait to
	 * ensure consistency guarantees. However, a query with
	 * "available" read concern may return orphan documents
	 * if the shard is undergoing chunk migrations since the
	 * "available" read concern, unlike
	 * "local" read concern, does not contact the
	 * shard's primary nor the config servers for updated metadata.</p>
	const AVAILABLE = "available";
	/**
	 * Read concern "snapshot" is available for
	 * multi-document transactions, and starting in MongoDB 5.0, certain read
	 * operations outside of multi-document transactions.
	 * <p>If the transaction is not part of a causally consistent session, upon
	 * transaction commit with write concern "majority", the
	 * transaction operations are guaranteed to have read from a snapshot of
	 * majority-committed data.</p>
	 * <p>If the transaction is part of a causally consistent session, upon
	 * transaction commit with write concern "majority", the
	 * transaction operations are guaranteed to have read from a snapshot of
	 * majority-committed data that provides causal consistency with the
	 * operation immediately preceding the transaction start.</p>
	 * <p>Outside of multi-document transactions, read concern
	 * "snapshot" is available on primaries and secondaries
	 * for the following read operations: find,
	 * aggregate, and distinct (on
	 * unsharded collections). All other read commands prohibit
	 * "snapshot".</p>
	const SNAPSHOT = "snapshot";


	/**
	 * Create a new ReadConcern
	 * @link http://www.php.net/manual/en/mongodb-driver-readconcern.construct.php
	 * @param string|null $level [optional] The read concern level.
	 * You may use, but are not limited to, one of the
	 * class constants.
	 * @return string|null 
	 */
	final public function __construct (?string $level = null): ?string {}

	/**
	 * Returns the ReadConcern's "level" option
	 * @link http://www.php.net/manual/en/mongodb-driver-readconcern.getlevel.php
	 * @return string|null Returns the ReadConcern's "level" option.
	 */
	final public function getLevel (): ?string {}

	/**
	 * Checks if this is the default read concern
	 * @link http://www.php.net/manual/en/mongodb-driver-readconcern.isdefault.php
	 * @return bool Returns true if this is the default read concern and false otherwise.
	 */
	final public function isDefault (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\ReadConcern {}

	/**
	 * Returns an object for BSON serialization
	 * @link http://www.php.net/manual/en/mongodb-driver-readconcern.bsonserialize.php
	 * @return object Returns an object for serializing the ReadConcern as BSON.
	 */
	final public function bsonSerialize (): object {}

	/**
	 * Serialize a ReadConcern
	 * @link http://www.php.net/manual/en/mongodb-driver-readconcern.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\Driver\ReadConcern.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a ReadConcern
	 * @link http://www.php.net/manual/en/mongodb-driver-readconcern.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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

/**
 * @link http://www.php.net/manual/en/class.mongodb-driver-readpreference.php
 */
final class ReadPreference implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	/**
	 * All operations read from the current replica set primary. This is the
	 * default read preference for MongoDB.
	const RP_PRIMARY = 1;
	/**
	 * In most situations, operations read from the primary but if it is
	 * unavailable, operations read from secondary members.
	const RP_PRIMARY_PREFERRED = 5;
	/**
	 * All operations read from the secondary members of the replica set.
	const RP_SECONDARY = 2;
	/**
	 * In most situations, operations read from secondary members but if no
	 * secondary members are available, operations read from the primary.
	const RP_SECONDARY_PREFERRED = 6;
	/**
	 * Operations read from member of the replica set with the least network
	 * latency, irrespective of the member's type.
	const RP_NEAREST = 10;
	/**
	 * All operations read from the current replica set primary. This is the
	 * default read preference for MongoDB.
	const PRIMARY = "primary";
	/**
	 * In most situations, operations read from the primary but if it is
	 * unavailable, operations read from secondary members.
	const PRIMARY_PREFERRED = "primaryPreferred";
	/**
	 * All operations read from the secondary members of the replica set.
	const SECONDARY = "secondary";
	/**
	 * In most situations, operations read from secondary members but if no
	 * secondary members are available, operations read from the primary.
	const SECONDARY_PREFERRED = "secondaryPreferred";
	/**
	 * Operations read from member of the replica set with the least network
	 * latency, irrespective of the member's type.
	const NEAREST = "nearest";
	/**
	 * The default value for the "maxStalenessSeconds"
	 * option is to specify no limit on maximum staleness, which means that the
	 * driver will not consider a secondary's lag when choosing where to
	 * direct a read operation.
	const NO_MAX_STALENESS = -1;
	/**
	 * The minimum value for the "maxStalenessSeconds" option
	 * is 90 seconds. The driver estimates secondaries' staleness by
	 * periodically checking the latest write date of each replica set member.
	 * Since these checks are infrequent, the staleness estimate is coarse.
	 * Thus, the driver cannot enforce a max staleness value of less than 90
	 * seconds.
	const SMALLEST_MAX_STALENESS_SECONDS = 90;


	/**
	 * Create a new ReadPreference
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.construct.php
	 * @param string|int $mode <table>
	 * Read preference mode
	 * <table>
	 * <tr valign="top">
	 * <td>Value</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MongoDB\Driver\ReadPreference::RP_PRIMARY or "primary"</td>
	 * <td>
	 * <p>
	 * All operations read from the current replica set primary. This is
	 * the default read preference for MongoDB.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MongoDB\Driver\ReadPreference::RP_PRIMARY_PREFERRED or "primaryPreferred"</td>
	 * <td>
	 * <p>
	 * In most situations, operations read from the primary but if it is
	 * unavailable, operations read from secondary members.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MongoDB\Driver\ReadPreference::RP_SECONDARY or "secondary"</td>
	 * <td>
	 * <p>
	 * All operations read from the secondary members of the replica set.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MongoDB\Driver\ReadPreference::RP_SECONDARY_PREFERRED or "secondaryPreferred"</td>
	 * <td>
	 * <p>
	 * In most situations, operations read from secondary members but if no
	 * secondary members are available, operations read from the primary.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MongoDB\Driver\ReadPreference::RP_NEAREST or "nearest"</td>
	 * <td>
	 * <p>
	 * Operations read from member of the replica set with the least
	 * network latency, irrespective of the member's type.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>All operations read from the current replica set primary. This is
	 * the default read preference for MongoDB.</p>
	 * <p>In most situations, operations read from the primary but if it is
	 * unavailable, operations read from secondary members.</p>
	 * <p>All operations read from the secondary members of the replica set.</p>
	 * <p>In most situations, operations read from secondary members but if no
	 * secondary members are available, operations read from the primary.</p>
	 * <p>Operations read from member of the replica set with the least
	 * network latency, irrespective of the member's type.</p>
	 * @param array|null $tagSets [optional] Tag sets allow you to target read operations to specific members of a
	 * replica set. This parameter should be an array of associative arrays, each
	 * of which contain zero or more key/value pairs. When selecting a server for
	 * a read operation, the driver attempt to select a node having all tags in a
	 * set (i.e. the associative array of key/value pairs). If selection fails,
	 * the driver will attempt subsequent sets. An empty tag set
	 * (array()) will match any node and may be used as a
	 * fallback.
	 * <p>Tags are not compatible with the
	 * MongoDB\Driver\ReadPreference::RP_PRIMARY mode and,
	 * in general, only apply when selecting a secondary member of a set for a
	 * read operation. However, the
	 * MongoDB\Driver\ReadPreference::RP_NEAREST mode, when
	 * combined with a tag set, selects the matching member with the lowest
	 * network latency. This member may be a primary or secondary.</p>
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>hedge</td>
	 * <td>objectarray</td>
	 * <td>
	 * <p>Specifies whether to use hedged reads, which are supported by MongoDB 4.4+ for sharded queries.</p>
	 * <p>
	 * Server hedged reads are available for all non-primary read preferences
	 * and are enabled by default when using the "nearest"
	 * mode. This option allows explicitly enabling server hedged reads for
	 * non-primary read preferences by specifying
	 * ['enabled' =&gt; true], or explicitly disabling
	 * server hedged reads for the "nearest" read
	 * preference by specifying ['enabled' =&gt; false].
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxStalenessSeconds</td>
	 * <td>int</td>
	 * <td>
	 * <p>
	 * Specifies a maximum replication lag, or "staleness", for reads from
	 * secondaries. When a secondary's estimated staleness exceeds
	 * this value, the driver stops using it for read operations.
	 * </p>
	 * <p>
	 * If specified, the max staleness must be a signed 32-bit integer
	 * greater than or equal to
	 * MongoDB\Driver\ReadPreference::SMALLEST_MAX_STALENESS_SECONDS.
	 * </p>
	 * <p>
	 * Defaults to
	 * MongoDB\Driver\ReadPreference::NO_MAX_STALENESS,
	 * which means that the driver will not consider a secondary's lag
	 * when choosing where to direct a read operation.
	 * </p>
	 * <p>
	 * This option is not compatible with the
	 * MongoDB\Driver\ReadPreference::RP_PRIMARY mode.
	 * Specifying a max staleness also requires all MongoDB instances in
	 * the deployment to be using MongoDB 3.4+. An exception will be thrown
	 * at execution time if any MongoDB instances in the deployment are of
	 * an older server version.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Specifies whether to use hedged reads, which are supported by MongoDB 4.4+ for sharded queries.</p>
	 * <p>Server hedged reads are available for all non-primary read preferences
	 * and are enabled by default when using the "nearest"
	 * mode. This option allows explicitly enabling server hedged reads for
	 * non-primary read preferences by specifying
	 * ['enabled' =&gt; true], or explicitly disabling
	 * server hedged reads for the "nearest" read
	 * preference by specifying ['enabled' =&gt; false].</p>
	 * <p>Specifies a maximum replication lag, or "staleness", for reads from
	 * secondaries. When a secondary's estimated staleness exceeds
	 * this value, the driver stops using it for read operations.</p>
	 * <p>If specified, the max staleness must be a signed 32-bit integer
	 * greater than or equal to
	 * MongoDB\Driver\ReadPreference::SMALLEST_MAX_STALENESS_SECONDS.</p>
	 * <p>Defaults to
	 * MongoDB\Driver\ReadPreference::NO_MAX_STALENESS,
	 * which means that the driver will not consider a secondary's lag
	 * when choosing where to direct a read operation.</p>
	 * <p>This option is not compatible with the
	 * MongoDB\Driver\ReadPreference::RP_PRIMARY mode.
	 * Specifying a max staleness also requires all MongoDB instances in
	 * the deployment to be using MongoDB 3.4+. An exception will be thrown
	 * at execution time if any MongoDB instances in the deployment are of
	 * an older server version.</p>
	 * @return string|int 
	 */
	final public function __construct (string|int $mode, ?array $tagSets = null, ?array $options = null): string|int {}

	/**
	 * Returns the ReadPreference's "hedge" option
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.gethedge.php
	 * @return object|null Returns the ReadPreference's "hedge" option.
	 */
	final public function getHedge (): ?object {}

	/**
	 * Returns the ReadPreference's "maxStalenessSeconds" option
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.getmaxstalenessseconds.php
	 * @return int Returns the ReadPreference's "maxStalenessSeconds" option. If no max
	 * staleness has been specified,
	 * MongoDB\Driver\ReadPreference::NO_MAX_STALENESS will be
	 * returned.
	 */
	final public function getMaxStalenessSeconds (): int {}

	/**
	 * Returns the ReadPreference's "mode" option
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.getmode.php
	 * @return int Returns the ReadPreference's "mode" option.
	 */
	final public function getMode (): int {}

	/**
	 * Returns the ReadPreference's "mode" option as a string
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.getmodestring.php
	 * @return string Returns the ReadPreference's "mode" option as a string.
	 */
	final public function getModeString (): string {}

	/**
	 * Returns the ReadPreference's "tagSets" option
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.gettagsets.php
	 * @return array Returns the ReadPreference's "tagSets" option.
	 */
	final public function getTagSets (): array {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\ReadPreference {}

	/**
	 * Returns an object for BSON serialization
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.bsonserialize.php
	 * @return object Returns an object for serializing the ReadPreference as BSON.
	 */
	final public function bsonSerialize (): object {}

	/**
	 * Serialize a ReadPreference
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\Driver\ReadPreference.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a ReadPreference
	 * @link http://www.php.net/manual/en/mongodb-driver-readpreference.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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

/**
 * @link http://www.php.net/manual/en/class.mongodb-driver-server.php
 */
final class Server  {
	/**
	 * Unknown server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_UNKNOWN = 0;
	/**
	 * Standalone server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_STANDALONE = 1;
	/**
	 * Mongos server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_MONGOS = 2;
	/**
	 * Replica set possible primary server type, returned by MongoDB\Driver\Server::getType.
	 * <p>A server may be identified as a possible primary if it has not yet been checked but another memory of the replica set thinks it is the primary.</p>
	const TYPE_POSSIBLE_PRIMARY = 3;
	/**
	 * Replica set primary server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_RS_PRIMARY = 4;
	/**
	 * Replica set secondary server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_RS_SECONDARY = 5;
	/**
	 * Replica set arbiter server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_RS_ARBITER = 6;
	/**
	 * Replica set other server type, returned by MongoDB\Driver\Server::getType.
	 * <p>Such servers may be hidden, starting up, or recovering. They cannot be queried, but their hosts lists are useful for discovering the current replica set configuration.</p>
	const TYPE_RS_OTHER = 7;
	/**
	 * Replica set ghost server type, returned by MongoDB\Driver\Server::getType.
	 * <p>Servers may be identified as such in at least three situations: briefly during server startup; in an uninitialized replica set; or when the server is shunned (i.e. removed from the replica set config). They cannot be queried, nor can their host list be used to discover the current replica set configuration; however, the client may monitor this server in hope that it transitions to a more useful state.</p>
	const TYPE_RS_GHOST = 8;
	/**
	 * Load balancer server type, returned by MongoDB\Driver\Server::getType.
	const TYPE_LOAD_BALANCER = 9;


	/**
	 * Create a new Server (not used)
	 * @link http://www.php.net/manual/en/mongodb-driver-server.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Execute one or more write operations on this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.executebulkwrite.php
	 * @param string $namespace A fully qualified namespace (e.g. "databaseName.collectionName").
	 * @param \MongoDB\Driver\BulkWrite $bulk The write(s) to execute.
	 * @param array|\MongoDB\Driver\WriteConcern|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * @return \MongoDB\Driver\WriteResult >Returns MongoDB\Driver\WriteResult on success.
	 */
	final public function executeBulkWrite (string $namespace, \MongoDB\Driver\BulkWrite $bulk, array|\MongoDB\Driver\WriteConcern|null $options = null): \MongoDB\Driver\WriteResult {}

	/**
	 * Execute a database command on this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.executecommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|\MongoDB\Driver\ReadPreference|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeCommand (string $db, \MongoDB\Driver\Command $command, array|\MongoDB\Driver\ReadPreference|null $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database query on this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.executequery.php
	 * @param string $namespace A fully qualified namespace (e.g. "databaseName.collectionName").
	 * @param \MongoDB\Driver\Query $query The query to execute.
	 * @param array|\MongoDB\Driver\ReadPreference|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A session to associate with the operation.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeQuery (string $namespace, \MongoDB\Driver\Query $query, array|\MongoDB\Driver\ReadPreference|null $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database command that reads on this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.executereadcommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A session to associate with the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeReadCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database command that reads and writes on this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.executereadwritecommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeReadWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Execute a database command that writes on this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.executewritecommand.php
	 * @param string $db The name of the database on which to execute the command.
	 * @param \MongoDB\Driver\Command $command The command to execute.
	 * @param array|null $options [optional] <table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>session</td>
	 * <td>MongoDB\Driver\Session</td>
	 * <td>
	 * <p>
	 * A session to associate with the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>A session to associate with the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * <p>If you are using a "session" which has a transaction
	 * in progress, you cannot specify a "readConcern" or
	 * "writeConcern" option. This will result in an
	 * MongoDB\Driver\Exception\InvalidArgumentException
	 * being thrown. Instead, you should set these two options when you create
	 * the transaction with
	 * MongoDB\Driver\Session::startTransaction.</p>
	 * @return \MongoDB\Driver\Cursor >Returns MongoDB\Driver\Cursor on success.
	 */
	final public function executeWriteCommand (string $db, \MongoDB\Driver\Command $command, ?array $options = null): \MongoDB\Driver\Cursor {}

	/**
	 * Returns the hostname of this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.gethost.php
	 * @return string Returns the hostname of this server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns an array of information describing this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.getinfo.php
	 * @return array Returns an array of information describing this server.
	 */
	final public function getInfo (): array {}

	/**
	 * Returns the latency of this server in milliseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-server.getlatency.php
	 * @return integer|null Returns the latency of this server in milliseconds, or null if no latency
	 * has been measured (e.g. client is connected to a load balancer).
	 */
	final public function getLatency (): ?int {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-server.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns a ServerDescription for this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.getserverdescription.php
	 * @return \MongoDB\Driver\ServerDescription Returns a MongoDB\Driver\ServerDescription for this
	 * server.
	 */
	final public function getServerDescription (): \MongoDB\Driver\ServerDescription {}

	/**
	 * Returns an array of tags describing this server in a replica set
	 * @link http://www.php.net/manual/en/mongodb-driver-server.gettags.php
	 * @return array Returns an array of tags used to describe this server in a
	 * replica set.
	 */
	final public function getTags (): array {}

	/**
	 * Returns an integer denoting the type of this server
	 * @link http://www.php.net/manual/en/mongodb-driver-server.gettype.php
	 * @return int Returns an int denoting the type of this server.
	 */
	final public function getType (): int {}

	/**
	 * Checks if this server is an arbiter member of a replica set
	 * @link http://www.php.net/manual/en/mongodb-driver-server.isarbiter.php
	 * @return bool Returns true if this server is an arbiter member of a replica set, and
	 * false otherwise.
	 */
	final public function isArbiter (): bool {}

	/**
	 * Checks if this server is a hidden member of a replica set
	 * @link http://www.php.net/manual/en/mongodb-driver-server.ishidden.php
	 * @return bool Returns true if this server is a hidden member of a replica set, and
	 * false otherwise.
	 */
	final public function isHidden (): bool {}

	/**
	 * Checks if this server is a passive member of a replica set
	 * @link http://www.php.net/manual/en/mongodb-driver-server.ispassive.php
	 * @return bool Returns true if this server is a passive member of a replica set, and
	 * false otherwise.
	 */
	final public function isPassive (): bool {}

	/**
	 * Checks if this server is a primary member of a replica set
	 * @link http://www.php.net/manual/en/mongodb-driver-server.isprimary.php
	 * @return bool Returns true if this server is a primary member of a replica set, and
	 * false otherwise.
	 */
	final public function isPrimary (): bool {}

	/**
	 * Checks if this server is a secondary member of a replica set
	 * @link http://www.php.net/manual/en/mongodb-driver-server.issecondary.php
	 * @return bool Returns true if this server is a secondary member of a replica set, and
	 * false otherwise.
	 */
	final public function isSecondary (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * @link http://www.php.net/manual/en/class.mongodb-driver-serverapi.php
 */
final class ServerApi implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	/**
	 * Server API version 1.
	const V1 = 1;


	/**
	 * Create a new ServerApi instance
	 * @link http://www.php.net/manual/en/mongodb-driver-serverapi.construct.php
	 * @param string $version A server API version.
	 * <p>Supported API versions are provided as constants in
	 * MongoDB\Driver\ServerApi. The only supported API
	 * version is MongoDB\Driver\ServerApi::V1.</p>
	 * @param bool|null $strict [optional] If the strict parameter is set to true, the
	 * server will yield an error for any command that is not part of the
	 * specified API version. If no value is provided, the server default value
	 * (false) is used.
	 * @param bool|null $deprecationErrors [optional] If the deprecationErrors parameter is set to true,
	 * the server will yield an error when using a command that is deprecated in
	 * the specified API version. If no value is provided, the server default value
	 * (false) is used.
	 * @return string 
	 */
	final public function __construct (string $version, ?bool $strict = null, ?bool $deprecationErrors = null): string {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\ServerApi {}

	/**
	 * Returns an object for BSON serialization
	 * @link http://www.php.net/manual/en/mongodb-driver-serverapi.bsonserialize.php
	 * @return object Returns an object for serializing the ServerApi as BSON.
	 */
	final public function bsonSerialize (): object {}

	/**
	 * Serialize a ServerApi
	 * @link http://www.php.net/manual/en/mongodb-driver-serverapi.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\Driver\ServerApi.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a ServerApi
	 * @link http://www.php.net/manual/en/mongodb-driver-serverapi.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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

/**
 * The MongoDB\Driver\ServerDescription class is a value
 * object that represents a server to which the driver is connected. Instances
 * of this class are returned by
 * MongoDB\Driver\Server::getServerDescription and
 * MongoDB\Driver\Monitoring\ServerChangedEvent methods.
 * @link http://www.php.net/manual/en/class.mongodb-driver-serverdescription.php
 */
final class ServerDescription  {
	/**
	 * Unknown server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_UNKNOWN = "Unknown";
	/**
	 * Standalone server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_STANDALONE = "Standalone";
	/**
	 * Mongos server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_MONGOS = "Mongos";
	/**
	 * Replica set possible primary server type, returned by MongoDB\Driver\ServerDescription::getType.
	 * <p>A server may be identified as a possible primary if it has not yet been checked but another memory of the replica set thinks it is the primary.</p>
	const TYPE_POSSIBLE_PRIMARY = "PossiblePrimary";
	/**
	 * Replica set primary server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_RS_PRIMARY = "RSPrimary";
	/**
	 * Replica set secondary server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_RS_SECONDARY = "RSSecondary";
	/**
	 * Replica set arbiter server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_RS_ARBITER = "RSArbiter";
	/**
	 * Replica set other server type, returned by MongoDB\Driver\ServerDescription::getType.
	 * <p>Such servers may be hidden, starting up, or recovering. They cannot be queried, but their hosts lists are useful for discovering the current replica set configuration.</p>
	const TYPE_RS_OTHER = "RSOther";
	/**
	 * Replica set ghost server type, returned by MongoDB\Driver\ServerDescription::getType.
	 * <p>Servers may be identified as such in at least three situations: briefly during server startup; in an uninitialized replica set; or when the server is shunned (i.e. removed from the replica set config). They cannot be queried, nor can their host list be used to discover the current replica set configuration; however, the client may monitor this server in hope that it transitions to a more useful state.</p>
	const TYPE_RS_GHOST = "RSGhost";
	/**
	 * Load balancer server type, returned by MongoDB\Driver\ServerDescription::getType.
	const TYPE_LOAD_BALANCER = "LoadBalancer";


	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the server's most recent "hello" response
	 * @link http://www.php.net/manual/en/mongodb-driver-serverdescription.gethelloresponse.php
	 * @return array Returns an array of information describing this server.
	 */
	final public function getHelloResponse (): array {}

	/**
	 * Returns the hostname of this server
	 * @link http://www.php.net/manual/en/mongodb-driver-serverdescription.gethost.php
	 * @return string Returns the hostname of this server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns the server's last update time in microseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-serverdescription.getlastupdatetime.php
	 * @return int Returns the server's last update time in microseconds.
	 */
	final public function getLastUpdateTime (): int {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-serverdescription.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the server's round trip time in milliseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-serverdescription.getroundtriptime.php
	 * @return int|null Returns the server's round trip time in milliseconds.
	 */
	final public function getRoundTripTime (): ?int {}

	/**
	 * Returns a string denoting the type of this server
	 * @link http://www.php.net/manual/en/mongodb-driver-serverdescription.gettype.php
	 * @return string Returns a string denoting the type of this server.
	 */
	final public function getType (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\TopologyDescription class is a
 * value object that represents a topology to which the driver is connected.
 * Instances of this class are returned by
 * MongoDB\Driver\Monitoring\TopologyChangedEvent
 * methods.
 * @link http://www.php.net/manual/en/class.mongodb-driver-topologydescription.php
 */
final class TopologyDescription  {
	/**
	 * Unknown topology type, returned by MongoDB\Driver\TopologyDescription::getType.
	const TYPE_UNKNOWN = "Unknown";
	/**
	 * Single server (i.e. direct connection), returned by MongoDB\Driver\TopologyDescription::getType.
	const TYPE_SINGLE = "Single";
	/**
	 * Sharded cluster, returned by MongoDB\Driver\TopologyDescription::getType.
	const TYPE_SHARDED = "Sharded";
	/**
	 * Replica set with no primary server, returned by MongoDB\Driver\TopologyDescription::getType.
	const TYPE_REPLICA_SET_NO_PRIMARY = "ReplicaSetNoPrimary";
	/**
	 * Replica set with a primary server, returned by MongoDB\Driver\TopologyDescription::getType.
	const TYPE_REPLICA_SET_WITH_PRIMARY = "ReplicaSetWithPrimary";
	/**
	 * Load balanced topology, returned by MongoDB\Driver\TopologyDescription::getType.
	const TYPE_LOAD_BALANCED = "LoadBalanced";


	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the servers in the topology
	 * @link http://www.php.net/manual/en/mongodb-driver-topologydescription.getservers.php
	 * @return array Returns an array of MongoDB\Driver\ServerDescription
	 * objects corresponding to the known servers in the topology.
	 */
	final public function getServers (): array {}

	/**
	 * Returns a string denoting the type of this topology
	 * @link http://www.php.net/manual/en/mongodb-driver-topologydescription.gettype.php
	 * @return string Returns a string denoting the type of this topology.
	 */
	final public function getType (): string {}

	/**
	 * Returns whether the topology has a readable server
	 * @link http://www.php.net/manual/en/mongodb-driver-topologydescription.hasreadableserver.php
	 * @param \MongoDB\Driver\ReadPreference|null $readPreference [optional] 
	 * @return bool Returns whether the topology has a readable server or, if
	 * readPreference is specified, a server matching the
	 * specified read preference.
	 */
	final public function hasReadableServer (?\MongoDB\Driver\ReadPreference $readPreference = null): bool {}

	/**
	 * Returns whether the topology has a writable server
	 * @link http://www.php.net/manual/en/mongodb-driver-topologydescription.haswritableserver.php
	 * @return bool Returns whether the topology has a writable server.
	 */
	final public function hasWritableServer (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Session class represents a
 * client session and is returned by
 * MongoDB\Driver\Manager::startSession. Commands,
 * queries, and write operations may then be associated the session.
 * @link http://www.php.net/manual/en/class.mongodb-driver-session.php
 */
final class Session  {
	/**
	 * There is no transaction in progress.
	const TRANSACTION_NONE = "none";
	/**
	 * A transaction has been started, but no operation has been sent to the server.
	const TRANSACTION_STARTING = "starting";
	/**
	 * A transaction is in progress.
	const TRANSACTION_IN_PROGRESS = "in_progress";
	/**
	 * The transaction was committed.
	const TRANSACTION_COMMITTED = "committed";
	/**
	 * The transaction was aborted.
	const TRANSACTION_ABORTED = "aborted";


	/**
	 * Create a new Session (not used)
	 * @link http://www.php.net/manual/en/mongodb-driver-session.construct.php
	 * @return void 
	 */
	final private function __construct (): void {}

	/**
	 * Aborts a transaction
	 * @link http://www.php.net/manual/en/mongodb-driver-session.aborttransaction.php
	 * @return void No value is returned.
	 */
	final public function abortTransaction (): void {}

	/**
	 * Advances the cluster time for this session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.advanceclustertime.php
	 * @param array|object $clusterTime The cluster time is a document containing a logical timestamp and server
	 * signature. Typically, this value will be obtained by calling
	 * MongoDB\Driver\Session::getClusterTime on another
	 * session object.
	 * @return void No value is returned.
	 */
	final public function advanceClusterTime (array|object $clusterTime): void {}

	/**
	 * Advances the operation time for this session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.advanceoperationtime.php
	 * @param \MongoDB\BSON\TimestampInterface $operationTime The operation time is a logical timestamp. Typically, this value will be
	 * obtained by calling
	 * MongoDB\Driver\Session::getOperationTime on
	 * another session object.
	 * @return void No value is returned.
	 */
	final public function advanceOperationTime (\MongoDB\BSON\TimestampInterface $operationTime): void {}

	/**
	 * Commits a transaction
	 * @link http://www.php.net/manual/en/mongodb-driver-session.committransaction.php
	 * @return void No value is returned.
	 */
	final public function commitTransaction (): void {}

	/**
	 * Terminates a session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.endsession.php
	 * @return void No value is returned.
	 */
	final public function endSession (): void {}

	/**
	 * Returns the cluster time for this session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.getclustertime.php
	 * @return object|null Returns the cluster time for this session, or null if the session has no
	 * cluster time.
	 */
	final public function getClusterTime (): ?object {}

	/**
	 * Returns the logical session ID for this session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.getlogicalsessionid.php
	 * @return object Returns the logical session ID for this session.
	 */
	final public function getLogicalSessionId (): object {}

	/**
	 * Returns the operation time for this session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.getoperationtime.php
	 * @return \MongoDB\BSON\Timestamp|null Returns the operation time for this session, or null if the session has no
	 * operation time.
	 */
	final public function getOperationTime (): ?\MongoDB\BSON\Timestamp {}

	/**
	 * Returns the server to which this session is pinned
	 * @link http://www.php.net/manual/en/mongodb-driver-session.getserver.php
	 * @return \MongoDB\Driver\Server|null Returns the MongoDB\Driver\Server to which this
	 * session is pinned, or null if the session is not pinned to any server.
	 */
	final public function getServer (): ?\MongoDB\Driver\Server {}

	/**
	 * Returns options for the currently running transaction
	 * @link http://www.php.net/manual/en/mongodb-driver-session.gettransactionoptions.php
	 * @return array|null Returns a array containing current transaction options, or
	 * null if no transaction is running.
	 */
	final public function getTransactionOptions (): ?array {}

	/**
	 * Returns the current transaction state for this session
	 * @link http://www.php.net/manual/en/mongodb-driver-session.gettransactionstate.php
	 * @return string Returns the current transaction state for this session.
	 */
	final public function getTransactionState (): string {}

	/**
	 * Returns whether the session has been marked as dirty
	 * @link http://www.php.net/manual/en/mongodb-driver-session.isdirty.php
	 * @return bool Returns whether the session has been marked as dirty.
	 */
	final public function isDirty (): bool {}

	/**
	 * Returns whether a multi-document transaction is in progress
	 * @link http://www.php.net/manual/en/mongodb-driver-session.isintransaction.php
	 * @return bool Returns true if a transaction is currently in progress for this session,
	 * and false otherwise.
	 */
	final public function isInTransaction (): bool {}

	/**
	 * Starts a transaction
	 * @link http://www.php.net/manual/en/mongodb-driver-session.starttransaction.php
	 * @param array|null $options [optional] Options can be passed as argument to this method. Each element in this
	 * options array overrides the corresponding option from the
	 * "defaultTransactionOptions" option, if set when
	 * starting the session with
	 * MongoDB\Driver\Manager::startSession.
	 * <p><table>
	 * options
	 * <table>
	 * <tr valign="top">
	 * <td>Option</td>
	 * <td>Type</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>maxCommitTimeMS</td>
	 * <td>integer</td>
	 * <td>
	 * <p>
	 * The maximum amount of time in milliseconds to allow a single
	 * commitTransaction command to run.
	 * </p>
	 * <p>
	 * If specified, maxCommitTimeMS must be a signed
	 * 32-bit integer greater than or equal to zero.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readConcern</td>
	 * <td>MongoDB\Driver\ReadConcern</td>
	 * <td>
	 * <p>
	 * A read concern to apply to the operation.
	 * </p>
	 * <p>
	 * This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>readPreference</td>
	 * <td>MongoDB\Driver\ReadPreference</td>
	 * <td>
	 * <p>
	 * A read preference to use for selecting a server for the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>writeConcern</td>
	 * <td>MongoDB\Driver\WriteConcern</td>
	 * <td>
	 * <p>
	 * A write concern to apply to the operation.
	 * </p>
	 * </td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * <p>The maximum amount of time in milliseconds to allow a single
	 * commitTransaction command to run.</p>
	 * <p>If specified, maxCommitTimeMS must be a signed
	 * 32-bit integer greater than or equal to zero.</p>
	 * <p>A read concern to apply to the operation.</p>
	 * <p>This option is available in MongoDB 3.2+ and will result in an
	 * exception at execution time if specified for an older server
	 * version.</p>
	 * <p>A read preference to use for selecting a server for the operation.</p>
	 * <p>A write concern to apply to the operation.</p>
	 * @return void No value is returned.
	 */
	final public function startTransaction (?array $options = null): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * MongoDB\Driver\WriteConcern describes the level of
 * acknowledgement requested from MongoDB for write operations to a standalone
 * mongod or to replica sets or to sharded clusters. In
 * sharded clusters, mongos instances will pass the write
 * concern on to the shards.
 * @link http://www.php.net/manual/en/class.mongodb-driver-writeconcern.php
 */
final class WriteConcern implements \MongoDB\BSON\Serializable, \MongoDB\BSON\Type, \Serializable {
	/**
	 * Majority of all the members in the set; arbiters, non-voting members,
	 * passive members, hidden members and delayed members are all included in
	 * the definition of majority write concern.
	const MAJORITY = "majority";


	/**
	 * Create a new WriteConcern
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.construct.php
	 * @param string|int $w <table>
	 * Write concern
	 * <table>
	 * <tr valign="top">
	 * <td>Value</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>1</td>
	 * <td>
	 * Requests acknowledgement that the write operation has propagated to
	 * the standalone mongod or the primary in a replica
	 * set. This is the default write concern for MongoDB.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>0</td>
	 * <td>
	 * Requests no acknowledgment of the write operation. However, this may
	 * return information about socket exceptions and networking errors to
	 * the application.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>&lt;integer greater than 1&gt;</td>
	 * <td>
	 * Numbers greater than 1 are valid only for replica sets to request
	 * acknowledgement from specified number of members, including the
	 * primary.
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>MongoDB\Driver\WriteConcern::MAJORITY</td>
	 * <td>
	 * <p>
	 * Requests acknowledgment that write operations have propagated to the
	 * majority of voting nodes, including the primary, and have been
	 * written to the on-disk journal for these nodes.
	 * </p>
	 * <p>
	 * Prior to MongoDB 3.0, this refers to the majority of replica set
	 * members (not just voting nodes).
	 * </p>
	 * </td>
	 * </tr>
	 * <tr valign="top">
	 * <td>string</td>
	 * <td>
	 * A string value is interpereted as a tag set. Requests acknowledgement
	 * that the write operations have propagated to a replica set member
	 * with the specified tag.
	 * </td>
	 * </tr>
	 * </table>
	 * </table>
	 * <p>Requests acknowledgment that write operations have propagated to the
	 * majority of voting nodes, including the primary, and have been
	 * written to the on-disk journal for these nodes.</p>
	 * <p>Prior to MongoDB 3.0, this refers to the majority of replica set
	 * members (not just voting nodes).</p>
	 * @param int|null $wtimeout [optional] How long to wait (in milliseconds) for secondaries before failing.
	 * <p>wtimeout causes write operations to return with an
	 * error (WriteConcernError) after the specified
	 * limit, even if the required write concern will eventually succeed. When
	 * these write operations return, MongoDB does not undo successful data
	 * modifications performed before the write concern exceeded the
	 * wtimeout time limit.</p>
	 * <p>If specified, wtimeout must be a signed 64-bit integer
	 * greater than or equal to zero.</p>
	 * <p><table>
	 * Write concern timeout
	 * <table>
	 * <tr valign="top">
	 * <td>Value</td>
	 * <td>Description</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>0</td>
	 * <td>Block indefinitely. This is the default.</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>&lt;integer greater than 0&gt;</td>
	 * <td>
	 * Milliseconds to wait until returning.
	 * </td>
	 * </tr>
	 * </table>
	 * </table></p>
	 * @param bool|null $journal [optional] Wait until mongod has applied the write to the journal.
	 * @return string|int 
	 */
	final public function __construct (string|int $w, ?int $wtimeout = null, ?bool $journal = null): string|int {}

	/**
	 * Returns the WriteConcern's "journal" option
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.getjournal.php
	 * @return bool|null Returns the WriteConcern's "journal" option.
	 */
	final public function getJournal (): ?bool {}

	/**
	 * Returns the WriteConcern's "w" option
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.getw.php
	 * @return string|int|null Returns the WriteConcern's "w" option.
	 */
	final public function getW (): string|int|null {}

	/**
	 * Returns the WriteConcern's "wtimeout" option
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.getwtimeout.php
	 * @return int Returns the WriteConcern's "wtimeout" option.
	 */
	final public function getWtimeout (): int {}

	/**
	 * Checks if this is the default write concern
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.isdefault.php
	 * @return bool Returns true if this is the default write concern and false otherwise.
	 */
	final public function isDefault (): bool {}

	/**
	 * {@inheritdoc}
	 * @param array $properties
	 */
	final public static function __set_state (array $properties): \MongoDB\Driver\WriteConcern {}

	/**
	 * Returns an object for BSON serialization
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.bsonserialize.php
	 * @return object Returns an object for serializing the WriteConcern as BSON.
	 */
	final public function bsonSerialize (): object {}

	/**
	 * Serialize a WriteConcern
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.serialize.php
	 * @return string Returns the serialized representation of the
	 * MongoDB\Driver\WriteConcern.
	 */
	final public function serialize (): string {}

	/**
	 * Unserialize a WriteConcern
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcern.unserialize.php
	 * @param string $serialized 
	 * @return void No value is returned.
	 */
	final public function unserialize (string $serialized): void {}

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

/**
 * The MongoDB\Driver\WriteConcernError class
 * encapsulates information about a write concern error and may be returned by
 * MongoDB\Driver\WriteResult::getWriteConcernError.
 * @link http://www.php.net/manual/en/class.mongodb-driver-writeconcernerror.php
 */
final class WriteConcernError  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the WriteConcernError's error code
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcernerror.getcode.php
	 * @return int Returns the WriteConcernError's error code.
	 */
	final public function getCode (): int {}

	/**
	 * Returns metadata document for the WriteConcernError
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcernerror.getinfo.php
	 * @return object|null Returns the metadata document for the WriteConcernError, or null if
	 * no metadata is available.
	 */
	final public function getInfo (): ?object {}

	/**
	 * Returns the WriteConcernError's error message
	 * @link http://www.php.net/manual/en/mongodb-driver-writeconcernerror.getmessage.php
	 * @return string Returns the WriteConcernError's error message.
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\WriteError class encapsulates
 * information about a write error and may be returned as an array element from
 * MongoDB\Driver\WriteResult::getWriteErrors.
 * @link http://www.php.net/manual/en/class.mongodb-driver-writeerror.php
 */
final class WriteError  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the WriteError's error code
	 * @link http://www.php.net/manual/en/mongodb-driver-writeerror.getcode.php
	 * @return int Returns the WriteError's error code.
	 */
	final public function getCode (): int {}

	/**
	 * Returns the index of the write operation corresponding to this WriteError
	 * @link http://www.php.net/manual/en/mongodb-driver-writeerror.getindex.php
	 * @return int Returns the index of the write operation (from
	 * MongoDB\Driver\BulkWrite) corresponding to this
	 * WriteError.
	 */
	final public function getIndex (): int {}

	/**
	 * Returns metadata document for the WriteError
	 * @link http://www.php.net/manual/en/mongodb-driver-writeerror.getinfo.php
	 * @return object|null Returns the metadata document for the WriteError, or null if no metadata is
	 * available.
	 */
	final public function getInfo (): ?object {}

	/**
	 * Returns the WriteError's error message
	 * @link http://www.php.net/manual/en/mongodb-driver-writeerror.getmessage.php
	 * @return string Returns the WriteError's error message.
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\WriteResult class encapsulates
 * information about an executed
 * MongoDB\Driver\BulkWrite and may be returned by
 * MongoDB\Driver\Manager::executeBulkWrite.
 * @link http://www.php.net/manual/en/class.mongodb-driver-writeresult.php
 */
final class WriteResult  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the number of documents inserted (excluding upserts)
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getinsertedcount.php
	 * @return int|null Returns the number of documents inserted (excluding upserts), or null if
	 * the write was not acknowledged.
	 */
	final public function getInsertedCount (): ?int {}

	/**
	 * Returns the number of documents selected for update
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getmatchedcount.php
	 * @return int|null Returns the number of documents selected for update, or null if the write
	 * was not acknowledged.
	 */
	final public function getMatchedCount (): ?int {}

	/**
	 * Returns the number of existing documents updated
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getmodifiedcount.php
	 * @return int|null Returns the number of existing documents updated, or null if the write was
	 * not acknowledged.
	 * <p>The modified count is not available on versions of MongoDB before 2.6, which
	 * used the legacy wire protocol version (i.e. OP_UPDATE). If this is the case,
	 * the modified count will also be null.</p>
	 */
	final public function getModifiedCount (): ?int {}

	/**
	 * Returns the number of documents deleted
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getdeletedcount.php
	 * @return int|null Returns the number of documents deleted, or null if the write was not
	 * acknowledged.
	 */
	final public function getDeletedCount (): ?int {}

	/**
	 * Returns the number of documents inserted by an upsert
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getupsertedcount.php
	 * @return int|null Returns the number of documents inserted by an upsert.
	 */
	final public function getUpsertedCount (): ?int {}

	/**
	 * Returns the server associated with this write result
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getserver.php
	 * @return \MongoDB\Driver\Server Returns the MongoDB\Driver\Server associated with this
	 * write result.
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * Returns an array of identifiers for upserted documents
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getupsertedids.php
	 * @return array Returns an array of identifiers (i.e. "_id" field values)
	 * for upserted documents. The array keys will correspond to the index of the
	 * write operation (from MongoDB\Driver\BulkWrite)
	 * responsible for the upsert.
	 */
	final public function getUpsertedIds (): array {}

	/**
	 * Returns any write concern error that occurred
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getwriteconcernerror.php
	 * @return \MongoDB\Driver\WriteConcernError|null Returns a MongoDB\Driver\WriteConcernError if a write
	 * concern error was encountered during the write operation, and null
	 * otherwise.
	 */
	final public function getWriteConcernError (): ?\MongoDB\Driver\WriteConcernError {}

	/**
	 * Returns any write errors that occurred
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.getwriteerrors.php
	 * @return array Returns an array of MongoDB\Driver\WriteError objects
	 * for any write errors encountered during the write operation. The array will
	 * be empty if no write errors occurred.
	 */
	final public function getWriteErrors (): array {}

	/**
	 * Returns whether the write was acknowledged
	 * @link http://www.php.net/manual/en/mongodb-driver-writeresult.isacknowledged.php
	 * @return bool Returns true if the write was acknowledged, and false otherwise.
	 */
	final public function isAcknowledged (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}


}


namespace MongoDB\Driver\Exception {

/**
 * Common interface for all driver exceptions. This may be used to catch only
 * exceptions originating from the driver itself.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-exception.php
 */
interface Exception extends \Throwable, \Stringable {

	/**
	 * Gets the message
	 * @link http://www.php.net/manual/en/throwable.getmessage.php
	 * @return string Returns the message associated with the thrown object.
	 */
	abstract public function getMessage (): string;

	/**
	 * Gets the exception code
	 * @link http://www.php.net/manual/en/throwable.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	abstract public function getCode (): int;

	/**
	 * Gets the file in which the object was created
	 * @link http://www.php.net/manual/en/throwable.getfile.php
	 * @return string Returns the filename in which the thrown object was created.
	 */
	abstract public function getFile (): string;

	/**
	 * Gets the line on which the object was instantiated
	 * @link http://www.php.net/manual/en/throwable.getline.php
	 * @return int Returns the line number where the thrown object was instantiated.
	 */
	abstract public function getLine (): int;

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/throwable.gettrace.php
	 * @return array Returns the stack trace as an array in the same format as
	 * debug_backtrace.
	 */
	abstract public function getTrace (): array;

	/**
	 * Returns the previous Throwable
	 * @link http://www.php.net/manual/en/throwable.getprevious.php
	 * @return \Throwable|null Returns the previous Throwable if available, or
	 * null otherwise.
	 */
	abstract public function getPrevious (): ?\Throwable;

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/throwable.gettraceasstring.php
	 * @return string Returns the stack trace as a string.
	 */
	abstract public function getTraceAsString (): string;

	/**
	 * Gets a string representation of the object
	 * @link http://www.php.net/manual/en/stringable.tostring.php
	 * @return string Returns the string representation of the object.
	 */
	abstract public function __toString (): string;

}

/**
 * Thrown when the driver encounters a runtime error (e.g. internal error from
 * libmongoc).
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-runtimeexception.php
 */
class RuntimeException extends \RuntimeException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * Contains an array of error labels to go with an exception. For example,
	 * error labels can be used to detect whether a transaction can be retried
	 * safely if the TransientTransactionError label is
	 * present. The existence of a specific error label should be tested for
	 * with the
	 * MongoDB\Driver\Exception\RuntimeException::hasErrorLabel,
	 * instead of interpreting this errorLabels property
	 * manually.
	 * @var array|null
	 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-runtimeexception.php#mongodb\driver\exception\runtimeexception.props.errorlabels
	 */
	protected ?array $errorLabels;

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Base class for exceptions thrown by the server. The code of this exception and its subclasses will correspond to the original error code from the server.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-serverexception.php
 */
class ServerException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Base class for exceptions thrown when the driver fails to establish a
 * database connection.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-connectionexception.php
 */
class ConnectionException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Base class for exceptions thrown by a failed write operation. The exception
 * encapsulates a MongoDB\Driver\WriteResult object.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-writeexception.php
 */
abstract class WriteException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * The MongoDB\Driver\WriteResult associated with
	 * the failed write operation.
	 * @var \MongoDB\Driver\WriteResult
	 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-writeexception.php#mongodb\driver\exception\writeexception.props.writeresult
	 */
	protected \MongoDB\Driver\WriteResult $writeResult;

	/**
	 * Returns the WriteResult for the failed write operation
	 * @link http://www.php.net/manual/en/mongodb-driver-writeexception.getwriteresult.php
	 * @return \MongoDB\Driver\WriteResult The MongoDB\Driver\WriteResult for the failed write
	 * operation.
	 */
	final public function getWriteResult (): \MongoDB\Driver\WriteResult {}

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when the driver fails to authenticate with the server.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-authenticationexception.php
 */
class AuthenticationException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when a bulk write operation fails.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-bulkwriteexception.php
 */
class BulkWriteException extends \MongoDB\Driver\Exception\WriteException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * Returns the WriteResult for the failed write operation
	 * @link http://www.php.net/manual/en/mongodb-driver-writeexception.getwriteresult.php
	 * @return \MongoDB\Driver\WriteResult The MongoDB\Driver\WriteResult for the failed write
	 * operation.
	 */
	final public function getWriteResult (): \MongoDB\Driver\WriteResult {}

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when a command fails.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-commandexception.php
 */
class CommandException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * The result document associated with the failed command.
	 * @var object
	 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-commandexception.php#mongodb\driver\exception\commandexception.props.resultdocument
	 */
	protected object $resultDocument;

	/**
	 * Returns the result document for the failed command
	 * @link http://www.php.net/manual/en/mongodb-driver-commandexception.getresultdocument.php
	 * @return object The result document for the failed command.
	 */
	final public function getResultDocument (): object {}

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when the driver fails to establish a database connection within a
 * specified time limit
 * (connectTimeoutMS)
 * or server selection fails
 * (serverSelectionTimeoutMS).
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-connectiontimeoutexception.php
 */
final class ConnectionTimeoutException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Base class for exceptions thrown during client-side encryption.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-encryptionexception.php
 */
class EncryptionException extends \MongoDB\Driver\Exception\RuntimeException implements \MongoDB\Driver\Exception\Exception, \Throwable, \Stringable {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when a query or command fails to complete within a specified time
 * limit (e.g.
 * maxTimeMS).
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-executiontimeoutexception.php
 */
final class ExecutionTimeoutException extends \MongoDB\Driver\Exception\ServerException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when a driver method is given invalid arguments (e.g. invalid option
 * types).
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-invalidargumentexception.php
 */
class InvalidArgumentException extends \InvalidArgumentException implements \Throwable, \Stringable, \MongoDB\Driver\Exception\Exception {

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

/**
 * Thrown when the driver is incorrectly used (e.g. rewinding a cursor).
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-logicexception.php
 */
class LogicException extends \LogicException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

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

/**
 * Thrown when the driver fails to establish an SSL connection with the server.
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-sslconnectionexception.php
 */
final class SSLConnectionException extends \MongoDB\Driver\Exception\ConnectionException implements \Stringable, \Throwable, \MongoDB\Driver\Exception\Exception {

	/**
	 * Returns whether an error label is associated with an exception
	 * @link http://www.php.net/manual/en/mongodb-driver-runtimeexception.haserrorlabel.php
	 * @param string $errorLabel The name of the errorLabel to test for.
	 * @return bool Whether the given errorLabel is associated with this
	 * exception.
	 */
	final public function hasErrorLabel (string $errorLabel): bool {}

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

/**
 * Thrown when the driver encounters an unexpected value (e.g. during BSON
 * serialization or deserialization).
 * @link http://www.php.net/manual/en/class.mongodb-driver-exception-unexpectedvalueexception.php
 */
class UnexpectedValueException extends \UnexpectedValueException implements \Throwable, \Stringable, \MongoDB\Driver\Exception\Exception {

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


namespace MongoDB\Driver\Monitoring {

/**
 * Base interface for event subscribers. This is used as a parameter type in the functions
 * MongoDB\Driver\Monitoring\addSubscriber and
 * MongoDB\Driver\Monitoring\removeSubscriber and should
 * not be implemented directly.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-subscriber.php
 */
interface Subscriber  {
}

/**
 * Classes may implement this interface to register an event subscriber that is
 * notified for each started, successful, and failed command event. See
 * for additional information.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-commandsubscriber.php
 */
interface CommandSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {

	/**
	 * Notification method for a started command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsubscriber.commandstarted.php
	 * @param \MongoDB\Driver\Monitoring\CommandStartedEvent $event An event object encapsulating information about the started command.
	 * @return void No value is returned.
	 */
	abstract public function commandStarted (\MongoDB\Driver\Monitoring\CommandStartedEvent $event): void;

	/**
	 * Notification method for a successful command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsubscriber.commandsucceeded.php
	 * @param \MongoDB\Driver\Monitoring\CommandSucceededEvent $event An event object encapsulating information about the successful command.
	 * @return void No value is returned.
	 */
	abstract public function commandSucceeded (\MongoDB\Driver\Monitoring\CommandSucceededEvent $event): void;

	/**
	 * Notification method for a failed command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsubscriber.commandfailed.php
	 * @param \MongoDB\Driver\Monitoring\CommandFailedEvent $event An event object encapsulating information about the failed command.
	 * @return void No value is returned.
	 */
	abstract public function commandFailed (\MongoDB\Driver\Monitoring\CommandFailedEvent $event): void;

}

/**
 * The MongoDB\Driver\Monitoring\CommandFailedEvent
 * class encapsulates information about a failed command.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-commandfailedevent.php
 */
final class CommandFailedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the command name
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getcommandname.php
	 * @return string Returns the command name.
	 */
	final public function getCommandName (): string {}

	/**
	 * Returns the command's duration in microseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getdurationmicros.php
	 * @return int Returns the command's duration in microseconds.
	 */
	final public function getDurationMicros (): int {}

	/**
	 * Returns the Exception associated with the failed command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.geterror.php
	 * @return \Exception Returns the Exception associated with the failed command.
	 */
	final public function getError (): \Exception {}

	/**
	 * Returns the command's operation ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getoperationid.php
	 * @return string Returns the command's operation ID.
	 */
	final public function getOperationId (): string {}

	/**
	 * Returns the command reply document
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getreply.php
	 * @return object Returns the command reply document as a stdClass
	 * object.
	 */
	final public function getReply (): object {}

	/**
	 * Returns the command's request ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getrequestid.php
	 * @return string Returns the command's request ID.
	 */
	final public function getRequestId (): string {}

	/**
	 * Returns the Server on which the command was executed
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getserver.php
	 * @return \MongoDB\Driver\Server Returns the MongoDB\Driver\Server on which the command
	 * was executed.
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * Returns the load balancer service ID for the command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getserviceid.php
	 * @return \MongoDB\BSON\ObjectId|null Returns the load balancer service ID, or null if the driver is not
	 * connected to a load balancer.
	 */
	final public function getServiceId (): ?\MongoDB\BSON\ObjectId {}

	/**
	 * Returns the server connection ID for the command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandfailedevent.getserverconnectionid.php
	 * @return int|null Returns the server connection ID, or null if it is not available.
	 */
	final public function getServerConnectionId (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\CommandStartedEvent
 * class encapsulates information about a started command.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-commandstartedevent.php
 */
final class CommandStartedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the command document
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getcommand.php
	 * @return object Returns the command document as a stdClass object.
	 */
	final public function getCommand (): object {}

	/**
	 * Returns the command name
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getcommandname.php
	 * @return string Returns the command name.
	 */
	final public function getCommandName (): string {}

	/**
	 * Returns the database on which the command was executed
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getdatabasename.php
	 * @return string Returns the database on which the command was executed.
	 */
	final public function getDatabaseName (): string {}

	/**
	 * Returns the command's operation ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getoperationid.php
	 * @return string Returns the command's operation ID.
	 */
	final public function getOperationId (): string {}

	/**
	 * Returns the command's request ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getrequestid.php
	 * @return string Returns the command's request ID.
	 */
	final public function getRequestId (): string {}

	/**
	 * Returns the Server on which the command was executed
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getserver.php
	 * @return \MongoDB\Driver\Server Returns the MongoDB\Driver\Server on which the command
	 * was executed.
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * Returns the load balancer service ID for the command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getserviceid.php
	 * @return \MongoDB\BSON\ObjectId|null Returns the load balancer service ID, or null if the driver is not
	 * connected to a load balancer.
	 */
	final public function getServiceId (): ?\MongoDB\BSON\ObjectId {}

	/**
	 * Returns the server connection ID for the command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandstartedevent.getserverconnectionid.php
	 * @return int|null Returns the server connection ID, or null if it is not available.
	 */
	final public function getServerConnectionId (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\CommandSucceededEvent
 * class encapsulates information about a successful command.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-commandsucceededevent.php
 */
final class CommandSucceededEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the command name
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getcommandname.php
	 * @return string Returns the command name.
	 */
	final public function getCommandName (): string {}

	/**
	 * Returns the command's duration in microseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getdurationmicros.php
	 * @return int Returns the command's duration in microseconds.
	 */
	final public function getDurationMicros (): int {}

	/**
	 * Returns the command's operation ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getoperationid.php
	 * @return string Returns the command's operation ID.
	 */
	final public function getOperationId (): string {}

	/**
	 * Returns the command reply document
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getreply.php
	 * @return object Returns the command reply document as a stdClass
	 * object.
	 */
	final public function getReply (): object {}

	/**
	 * Returns the command's request ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getrequestid.php
	 * @return string Returns the command's request ID.
	 */
	final public function getRequestId (): string {}

	/**
	 * Returns the Server on which the command was executed
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getserver.php
	 * @return \MongoDB\Driver\Server Returns the MongoDB\Driver\Server on which the command
	 * was executed.
	 */
	final public function getServer (): \MongoDB\Driver\Server {}

	/**
	 * Returns the load balancer service ID for the command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getserviceid.php
	 * @return \MongoDB\BSON\ObjectId|null Returns the load balancer service ID, or null if the driver is not
	 * connected to a load balancer.
	 */
	final public function getServiceId (): ?\MongoDB\BSON\ObjectId {}

	/**
	 * Returns the server connection ID for the command
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-commandsucceededevent.getserverconnectionid.php
	 * @return int|null Returns the server connection ID, or null if it is not available.
	 */
	final public function getServerConnectionId (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * Classes may implement this interface to register an event subscriber that is
 * notified for various SDAM events. See the
 * Server Discovery and Monitoring
 * and SDAM Monitoring
 * specifications for additional information.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-sdamsubscriber.php
 */
interface SDAMSubscriber extends \MongoDB\Driver\Monitoring\Subscriber {

	/**
	 * Notification method for a server description change
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.serverchanged.php
	 * @param \MongoDB\Driver\Monitoring\ServerChangedEvent $event An event object encapsulating information about the changed server
	 * description.
	 * @return void No value is returned.
	 */
	abstract public function serverChanged (\MongoDB\Driver\Monitoring\ServerChangedEvent $event): void;

	/**
	 * Notification method for closing a server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.serverclosed.php
	 * @param \MongoDB\Driver\Monitoring\ServerClosedEvent $event An event object encapsulating information about the closed server.
	 * @return void No value is returned.
	 */
	abstract public function serverClosed (\MongoDB\Driver\Monitoring\ServerClosedEvent $event): void;

	/**
	 * Notification method for opening a server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.serveropening.php
	 * @param \MongoDB\Driver\Monitoring\ServerOpeningEvent $event An event object encapsulating information about the opened server.
	 * @return void No value is returned.
	 */
	abstract public function serverOpening (\MongoDB\Driver\Monitoring\ServerOpeningEvent $event): void;

	/**
	 * Notification method for a failed server heartbeat
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.serverheartbeatfailed.php
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent $event An event object encapsulating information about the failed server
	 * heartbeat.
	 * @return void No value is returned.
	 */
	abstract public function serverHeartbeatFailed (\MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent $event): void;

	/**
	 * Notification method for a started server heartbeat
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.serverheartbeatstarted.php
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent $event An event object encapsulating information about the started server
	 * heartbeat.
	 * @return void No value is returned.
	 */
	abstract public function serverHeartbeatStarted (\MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent $event): void;

	/**
	 * Notification method for a successful server heartbeat
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.serverheartbeatsucceeded.php
	 * @param \MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent $event An event object encapsulating information about the successful server
	 * heartbeat.
	 * @return void No value is returned.
	 */
	abstract public function serverHeartbeatSucceeded (\MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent $event): void;

	/**
	 * Notification method for a topology description change
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.topologychanged.php
	 * @param \MongoDB\Driver\Monitoring\TopologyChangedEvent $event An event object encapsulating information about the changed topology
	 * description.
	 * @return void No value is returned.
	 */
	abstract public function topologyChanged (\MongoDB\Driver\Monitoring\TopologyChangedEvent $event): void;

	/**
	 * Notification method for closing the topology
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.topologyclosed.php
	 * @param \MongoDB\Driver\Monitoring\TopologyClosedEvent $event An event object encapsulating information about the closed topology.
	 * @return void No value is returned.
	 */
	abstract public function topologyClosed (\MongoDB\Driver\Monitoring\TopologyClosedEvent $event): void;

	/**
	 * Notification method for opening the topology
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-sdamsubscriber.topologyopening.php
	 * @param \MongoDB\Driver\Monitoring\TopologyOpeningEvent $event An event object encapsulating information about the opened topology.
	 * @return void No value is returned.
	 */
	abstract public function topologyOpening (\MongoDB\Driver\Monitoring\TopologyOpeningEvent $event): void;

}

/**
 * The MongoDB\Driver\Monitoring\ServerChangedEvent
 * class encapsulates information about a changed server description.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-serverchangedevent.php
 */
final class ServerChangedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverchangedevent.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the hostname of the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverchangedevent.gethost.php
	 * @return string Returns the hostname of the server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns the new description for the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverchangedevent.getnewdescription.php
	 * @return \MongoDB\Driver\ServerDescription Returns the new MongoDB\Driver\ServerDescription
	 * for the server.
	 */
	final public function getNewDescription (): \MongoDB\Driver\ServerDescription {}

	/**
	 * Returns the previous description for the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverchangedevent.getpreviousdescription.php
	 * @return \MongoDB\Driver\ServerDescription Returns the previous MongoDB\Driver\ServerDescription
	 * for the server.
	 */
	final public function getPreviousDescription (): \MongoDB\Driver\ServerDescription {}

	/**
	 * Returns the topology ID associated with this server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverchangedevent.gettopologyid.php
	 * @return \MongoDB\BSON\ObjectId Returns the topology ID.
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\ServerClosedEvent
 * class encapsulates information about a closed server.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-serverclosedevent.php
 */
final class ServerClosedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverclosedevent.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the hostname of the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverclosedevent.gethost.php
	 * @return string Returns the hostname of the server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns the topology ID associated with this server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverclosedevent.gettopologyid.php
	 * @return \MongoDB\BSON\ObjectId Returns the topology ID.
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\ServerHeartbeatFailedEvent
 * class encapsulates information about a failed server heartbeat.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-serverheartbeatfailedevent.php
 */
final class ServerHeartbeatFailedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the heartbeat's duration in microseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatfailedevent.getdurationmicros.php
	 * @return int Returns the heartbeat's duration in microseconds.
	 */
	final public function getDurationMicros (): int {}

	/**
	 * Returns the Exception associated with the failed heartbeat
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatfailedevent.geterror.php
	 * @return \Exception Returns the Exception associated with the failed
	 * heartbeat.
	 */
	final public function getError (): \Exception {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatfailedevent.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the hostname of the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatfailedevent.gethost.php
	 * @return string Returns the hostname of the server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns whether the heartbeat used a streaming protocol
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatfailedevent.isawaited.php
	 * @return bool Returns false.
	 */
	final public function isAwaited (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\ServerHeartbeatStartedEvent
 * class encapsulates information about a started server heartbeat.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-serverheartbeatstartedevent.php
 */
final class ServerHeartbeatStartedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatstartedevent.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the hostname of the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatstartedevent.gethost.php
	 * @return string Returns the hostname of the server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns whether the heartbeat used a streaming protocol
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatstartedevent.isawaited.php
	 * @return bool Returns false.
	 */
	final public function isAwaited (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\ServerHeartbeatSucceededEvent
 * class encapsulates information about a successful server heartbeat.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-serverheartbeatsucceededevent.php
 */
final class ServerHeartbeatSucceededEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the heartbeat's duration in microseconds
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatsucceededevent.getdurationmicros.php
	 * @return int Returns the heartbeat's duration in microseconds.
	 */
	final public function getDurationMicros (): int {}

	/**
	 * Returns the heartbeat reply document
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatsucceededevent.getreply.php
	 * @return object Returns the heartbeat reply document as a stdClass
	 * object.
	 */
	final public function getReply (): object {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatsucceededevent.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the hostname of the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatsucceededevent.gethost.php
	 * @return string Returns the hostname of the server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns whether the heartbeat used a streaming protocol
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serverheartbeatsucceededevent.isawaited.php
	 * @return bool Returns false.
	 */
	final public function isAwaited (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\ServerOpeningEvent
 * class encapsulates information about an opened server.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-serveropeningevent.php
 */
final class ServerOpeningEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the port on which this server is listening
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serveropeningevent.getport.php
	 * @return int Returns the port on which this server is listening.
	 */
	final public function getPort (): int {}

	/**
	 * Returns the hostname of the server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serveropeningevent.gethost.php
	 * @return string Returns the hostname of the server.
	 */
	final public function getHost (): string {}

	/**
	 * Returns the topology ID associated with this server
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-serveropeningevent.gettopologyid.php
	 * @return \MongoDB\BSON\ObjectId Returns the topology ID.
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\TopologyChangedEvent
 * class encapsulates information about a changed topology description.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-topologychangedevent.php
 */
final class TopologyChangedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the new description for the topology
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-topologychangedevent.getnewdescription.php
	 * @return \MongoDB\Driver\TopologyDescription Returns the new MongoDB\Driver\TopologyDescription
	 * for the topology.
	 */
	final public function getNewDescription (): \MongoDB\Driver\TopologyDescription {}

	/**
	 * Returns the previous description for the topology
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-topologychangedevent.getpreviousdescription.php
	 * @return \MongoDB\Driver\TopologyDescription Returns the previous MongoDB\Driver\TopologyDescription
	 * for the topology.
	 */
	final public function getPreviousDescription (): \MongoDB\Driver\TopologyDescription {}

	/**
	 * Returns the topology ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-topologychangedevent.gettopologyid.php
	 * @return \MongoDB\BSON\ObjectId Returns the topology ID.
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\TopologyClosedEvent
 * class encapsulates information about a closed topology.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-topologyclosedevent.php
 */
final class TopologyClosedEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the topology ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-topologyclosedevent.gettopologyid.php
	 * @return \MongoDB\BSON\ObjectId Returns the topology ID.
	 */
	final public function getTopologyId (): \MongoDB\BSON\ObjectId {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

}

/**
 * The MongoDB\Driver\Monitoring\TopologyOpeningEvent
 * class encapsulates information about an opened topology.
 * @link http://www.php.net/manual/en/class.mongodb-driver-monitoring-topologyopeningevent.php
 */
final class TopologyOpeningEvent  {

	/**
	 * {@inheritdoc}
	 */
	final private function __construct () {}

	/**
	 * Returns the topology ID
	 * @link http://www.php.net/manual/en/mongodb-driver-monitoring-topologyopeningevent.gettopologyid.php
	 * @return \MongoDB\BSON\ObjectId Returns the topology ID.
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
 * Returns the BSON representation of a JSON value
 * @link http://www.php.net/manual/en/function.mongodb.bson-fromjson.php
 * @param string $json JSON value to be converted.
 * @return string The serialized BSON document as a binary string.
 */
function fromJSON (string $json): string {}

/**
 * Returns the BSON representation of a PHP value
 * @link http://www.php.net/manual/en/function.mongodb.bson-fromphp.php
 * @param array|object $value PHP value to be serialized.
 * @return string The serialized BSON document as a binary string.
 */
function fromPHP (array|object $value): string {}

/**
 * Returns the Canonical Extended JSON representation of a BSON value
 * @link http://www.php.net/manual/en/function.mongodb.bson-tocanonicalextendedjson.php
 * @param string $bson BSON value to be converted.
 * @return string The converted JSON value.
 */
function toCanonicalExtendedJSON (string $bson): string {}

/**
 * Returns the Legacy Extended JSON representation of a BSON value
 * @link http://www.php.net/manual/en/function.mongodb.bson-tojson.php
 * @param string $bson BSON value to be converted.
 * @return string The converted JSON value.
 */
function toJSON (string $bson): string {}

/**
 * Returns the PHP representation of a BSON value
 * @link http://www.php.net/manual/en/function.mongodb.bson-tophp.php
 * @param string $bson BSON value to be unserialized.
 * @param array $typeMap [optional] Type map configuration.
 * @return array|object The unserialized PHP value.
 */
function toPHP (string $bson, array $typeMap = 'array()'): array|object {}

/**
 * Returns the Relaxed Extended JSON representation of a BSON value
 * @link http://www.php.net/manual/en/function.mongodb.bson-torelaxedextendedjson.php
 * @param string $bson BSON value to be converted.
 * @return string The converted JSON value.
 */
function toRelaxedExtendedJSON (string $bson): string {}


}


namespace mongodb\driver\monitoring {

/**
 * Registers a monitoring event subscriber globally
 * @link http://www.php.net/manual/en/function.mongodb.driver.monitoring.addsubscriber.php
 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber A monitoring event subscriber to register globally.
 * @return void No value is returned.
 */
function addSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}

/**
 * Unregisters a monitoring event subscriber globally
 * @link http://www.php.net/manual/en/function.mongodb.driver.monitoring.removesubscriber.php
 * @param \MongoDB\Driver\Monitoring\Subscriber $subscriber A monitoring event subscriber to unregister globally.
 * @return void No value is returned.
 */
function removeSubscriber (\MongoDB\Driver\Monitoring\Subscriber $subscriber): void {}


}


namespace {


/**
 * x.y.z style version number of the extension
 * @link http://www.php.net/manual/en/mongodb.constants.php
 * @var string
 */
define ('MONGODB_VERSION', "1.15.3");

/**
 * Current stability (alpha/beta/stable)
 * @link http://www.php.net/manual/en/mongodb.constants.php
 * @var string
 */
define ('MONGODB_STABILITY', "stable");


}

// End of mongodb v.1.15.3
