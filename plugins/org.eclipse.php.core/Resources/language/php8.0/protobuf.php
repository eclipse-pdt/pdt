<?php

// Start of protobuf v.3.23.1

namespace Google\Protobuf\Internal {

final class Arena  {
}

final class RepeatedField implements \ArrayAccess, \IteratorAggregate, \Traversable, \Countable {

	/**
	 * @param mixed $type
	 * @param mixed $class [optional]
	 */
	public function __construct ($type = null, $class = null) {}

	/**
	 * @param mixed $newval
	 */
	public function append ($newval = null) {}

	/**
	 * @param mixed $index [optional]
	 */
	public function offsetExists ($index = null): bool {}

	/**
	 * @param mixed $index
	 */
	public function offsetGet ($index = null) {}

	/**
	 * @param mixed $index
	 * @param mixed $newval
	 */
	public function offsetSet ($index = null, $newval = null): void {}

	/**
	 * @param mixed $index [optional]
	 */
	public function offsetUnset ($index = null): void {}

	public function count (): int {}

	public function getIterator (): Traversable {}

}

final class RepeatedFieldIter implements \Iterator, \Traversable {

	public function rewind (): void {}

	public function current () {}

	public function key () {}

	public function next (): void {}

	public function valid (): bool {}

}

class GPBUtil  {
	const TYPE_URL_PREFIX = "type.googleapis.com/";


	/**
	 * @param mixed $value
	 */
	public static function checkInt32 ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkUint32 ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkInt64 ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkUint64 ($value = null) {}

	/**
	 * @param mixed $value
	 * @param mixed $class
	 */
	public static function checkEnum ($value = null, $class = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkFloat ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkDouble ($value = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkBool ($value = null) {}

	/**
	 * @param mixed $value
	 * @param mixed $check_utf8 [optional]
	 */
	public static function checkString ($value = null, $check_utf8 = null) {}

	/**
	 * @param mixed $value
	 */
	public static function checkBytes ($value = null) {}

	/**
	 * @param mixed $value
	 * @param mixed $class
	 */
	public static function checkMessage ($value = null, $class = null) {}

	/**
	 * @param mixed $value
	 * @param mixed $key_type
	 * @param mixed $value_type
	 * @param mixed $value_class [optional]
	 */
	public static function checkMapField ($value = null, $key_type = null, $value_type = null, $value_class = null) {}

	/**
	 * @param mixed $value
	 * @param mixed $type
	 * @param mixed $class [optional]
	 */
	public static function checkRepeatedField ($value = null, $type = null, $class = null) {}

}


}


namespace Google\Protobuf {

final class OneofDescriptor  {

	public function getName () {}

	/**
	 * @param mixed $index
	 */
	public function getField ($index = null) {}

	public function getFieldCount () {}

}

final class EnumValueDescriptor  {

	public function getName () {}

	public function getNumber () {}

}

final class EnumDescriptor  {

	public function getPublicDescriptor () {}

	public function getValueCount () {}

	/**
	 * @param mixed $index
	 */
	public function getValue ($index = null) {}

}

final class Descriptor  {

	public function getClass () {}

	public function getFullName () {}

	/**
	 * @param mixed $index
	 */
	public function getField ($index = null) {}

	public function getFieldCount () {}

	/**
	 * @param mixed $index
	 */
	public function getOneofDecl ($index = null) {}

	public function getOneofDeclCount () {}

	public function getPublicDescriptor () {}

}

final class FieldDescriptor  {

	public function getName () {}

	public function getNumber () {}

	public function getLabel () {}

	public function getType () {}

	public function isMap () {}

	public function getEnumType () {}

	public function getContainingOneof () {}

	public function getRealContainingOneof () {}

	public function getMessageType () {}

}

final class DescriptorPool  {

	public static function getGeneratedPool () {}

	/**
	 * @param mixed $name
	 */
	public function getDescriptorByClassName ($name = null) {}

	/**
	 * @param mixed $name
	 */
	public function getDescriptorByProtoName ($name = null) {}

	/**
	 * @param mixed $name
	 */
	public function getEnumDescriptorByClassName ($name = null) {}

	/**
	 * @param mixed $data
	 * @param mixed $data_len
	 */
	public function internalAddGeneratedFile ($data = null, $data_len = null) {}

}


}


namespace Google\Protobuf\Internal {

class DescriptorPool  {

	public static function getGeneratedPool () {}

}

class GPBType  {
	const DOUBLE = 1;
	const FLOAT = 2;
	const INT64 = 3;
	const UINT64 = 4;
	const INT32 = 5;
	const FIXED64 = 6;
	const FIXED32 = 7;
	const BOOL = 8;
	const STRING = 9;
	const GROUP = 10;
	const MESSAGE = 11;
	const BYTES = 12;
	const UINT32 = 13;
	const ENUM = 14;
	const SFIXED32 = 15;
	const SFIXED64 = 16;
	const SINT32 = 17;
	const SINT64 = 18;

}

final class MapField implements \ArrayAccess, \IteratorAggregate, \Traversable, \Countable {

	/**
	 * @param mixed $key_type
	 * @param mixed $value_type
	 * @param mixed $value_class [optional]
	 */
	public function __construct ($key_type = null, $value_type = null, $value_class = null) {}

	/**
	 * @param mixed $index [optional]
	 */
	public function offsetExists ($index = null): bool {}

	/**
	 * @param mixed $index
	 */
	public function offsetGet ($index = null) {}

	/**
	 * @param mixed $index
	 * @param mixed $newval
	 */
	public function offsetSet ($index = null, $newval = null): void {}

	/**
	 * @param mixed $index [optional]
	 */
	public function offsetUnset ($index = null): void {}

	public function count (): int {}

	public function getIterator (): Traversable {}

}

final class MapFieldIter implements \Iterator, \Traversable {

	public function rewind (): void {}

	public function current () {}

	public function key () {}

	public function next (): void {}

	public function valid (): bool {}

}

class Message  {

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

	/**
	 * @param mixed $data [optional]
	 */
	protected function __construct ($data = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Any  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class Any extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getTypeUrl () {}

	/**
	 * @param mixed $value
	 */
	public function setTypeUrl ($value = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	/**
	 * @param mixed $proto
	 */
	public function is ($proto = null) {}

	/**
	 * @param mixed $value
	 */
	public function pack ($value = null) {}

	public function unpack () {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Api  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class Api extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getMethods () {}

	/**
	 * @param mixed $value
	 */
	public function setMethods ($value = null) {}

	public function getOptions () {}

	/**
	 * @param mixed $value
	 */
	public function setOptions ($value = null) {}

	public function getVersion () {}

	/**
	 * @param mixed $value
	 */
	public function setVersion ($value = null) {}

	public function getSourceContext () {}

	/**
	 * @param mixed $value
	 */
	public function setSourceContext ($value = null) {}

	public function getMixins () {}

	/**
	 * @param mixed $value
	 */
	public function setMixins ($value = null) {}

	public function getSyntax () {}

	/**
	 * @param mixed $value
	 */
	public function setSyntax ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class Method extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getRequestTypeUrl () {}

	/**
	 * @param mixed $value
	 */
	public function setRequestTypeUrl ($value = null) {}

	public function getRequestStreaming () {}

	/**
	 * @param mixed $value
	 */
	public function setRequestStreaming ($value = null) {}

	public function getResponseTypeUrl () {}

	/**
	 * @param mixed $value
	 */
	public function setResponseTypeUrl ($value = null) {}

	public function getResponseStreaming () {}

	/**
	 * @param mixed $value
	 */
	public function setResponseStreaming ($value = null) {}

	public function getOptions () {}

	/**
	 * @param mixed $value
	 */
	public function setOptions ($value = null) {}

	public function getSyntax () {}

	/**
	 * @param mixed $value
	 */
	public function setSyntax ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class Mixin extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getRoot () {}

	/**
	 * @param mixed $value
	 */
	public function setRoot ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Duration  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class Duration extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getSeconds () {}

	/**
	 * @param mixed $value
	 */
	public function setSeconds ($value = null) {}

	public function getNanos () {}

	/**
	 * @param mixed $value
	 */
	public function setNanos ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class GPBEmpty  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class GPBEmpty extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class FieldMask  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class FieldMask extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getPaths () {}

	/**
	 * @param mixed $value
	 */
	public function setPaths ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class SourceContext  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class SourceContext extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getFileName () {}

	/**
	 * @param mixed $value
	 */
	public function setFileName ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Struct  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class Struct extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getFields () {}

	/**
	 * @param mixed $value
	 */
	public function setFields ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace Google\Protobuf\Struct {

final class FieldsEntry extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getKey () {}

	/**
	 * @param mixed $value
	 */
	public function setKey ($value = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace Google\Protobuf {

final class Value extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getNullValue () {}

	/**
	 * @param mixed $value
	 */
	public function setNullValue ($value = null) {}

	public function getNumberValue () {}

	/**
	 * @param mixed $value
	 */
	public function setNumberValue ($value = null) {}

	public function getStringValue () {}

	/**
	 * @param mixed $value
	 */
	public function setStringValue ($value = null) {}

	public function getBoolValue () {}

	/**
	 * @param mixed $value
	 */
	public function setBoolValue ($value = null) {}

	public function getStructValue () {}

	/**
	 * @param mixed $value
	 */
	public function setStructValue ($value = null) {}

	public function getListValue () {}

	/**
	 * @param mixed $value
	 */
	public function setListValue ($value = null) {}

	public function getKind () {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class ListValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValues () {}

	/**
	 * @param mixed $value
	 */
	public function setValues ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

class NullValue  {
	const NULL_VALUE = 0;


	/**
	 * @param mixed $key
	 */
	public static function name ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public static function value ($key = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Type  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class Type extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getFields () {}

	/**
	 * @param mixed $value
	 */
	public function setFields ($value = null) {}

	public function getOneofs () {}

	/**
	 * @param mixed $value
	 */
	public function setOneofs ($value = null) {}

	public function getOptions () {}

	/**
	 * @param mixed $value
	 */
	public function setOptions ($value = null) {}

	public function getSourceContext () {}

	/**
	 * @param mixed $value
	 */
	public function setSourceContext ($value = null) {}

	public function getSyntax () {}

	/**
	 * @param mixed $value
	 */
	public function setSyntax ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class Field extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getKind () {}

	/**
	 * @param mixed $value
	 */
	public function setKind ($value = null) {}

	public function getCardinality () {}

	/**
	 * @param mixed $value
	 */
	public function setCardinality ($value = null) {}

	public function getNumber () {}

	/**
	 * @param mixed $value
	 */
	public function setNumber ($value = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getTypeUrl () {}

	/**
	 * @param mixed $value
	 */
	public function setTypeUrl ($value = null) {}

	public function getOneofIndex () {}

	/**
	 * @param mixed $value
	 */
	public function setOneofIndex ($value = null) {}

	public function getPacked () {}

	/**
	 * @param mixed $value
	 */
	public function setPacked ($value = null) {}

	public function getOptions () {}

	/**
	 * @param mixed $value
	 */
	public function setOptions ($value = null) {}

	public function getJsonName () {}

	/**
	 * @param mixed $value
	 */
	public function setJsonName ($value = null) {}

	public function getDefaultValue () {}

	/**
	 * @param mixed $value
	 */
	public function setDefaultValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace Google\Protobuf\Field {

class Kind  {
	const TYPE_UNKNOWN = 0;
	const TYPE_DOUBLE = 1;
	const TYPE_FLOAT = 2;
	const TYPE_INT64 = 3;
	const TYPE_UINT64 = 4;
	const TYPE_INT32 = 5;
	const TYPE_FIXED64 = 6;
	const TYPE_FIXED32 = 7;
	const TYPE_BOOL = 8;
	const TYPE_STRING = 9;
	const TYPE_GROUP = 10;
	const TYPE_MESSAGE = 11;
	const TYPE_BYTES = 12;
	const TYPE_UINT32 = 13;
	const TYPE_ENUM = 14;
	const TYPE_SFIXED32 = 15;
	const TYPE_SFIXED64 = 16;
	const TYPE_SINT32 = 17;
	const TYPE_SINT64 = 18;


	/**
	 * @param mixed $key
	 */
	public static function name ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public static function value ($key = null) {}

}

class Cardinality  {
	const CARDINALITY_UNKNOWN = 0;
	const CARDINALITY_OPTIONAL = 1;
	const CARDINALITY_REQUIRED = 2;
	const CARDINALITY_REPEATED = 3;


	/**
	 * @param mixed $key
	 */
	public static function name ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public static function value ($key = null) {}

}


}


namespace Google\Protobuf {

final class Enum extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getEnumvalue () {}

	/**
	 * @param mixed $value
	 */
	public function setEnumvalue ($value = null) {}

	public function getOptions () {}

	/**
	 * @param mixed $value
	 */
	public function setOptions ($value = null) {}

	public function getSourceContext () {}

	/**
	 * @param mixed $value
	 */
	public function setSourceContext ($value = null) {}

	public function getSyntax () {}

	/**
	 * @param mixed $value
	 */
	public function setSyntax ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class EnumValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getNumber () {}

	/**
	 * @param mixed $value
	 */
	public function setNumber ($value = null) {}

	public function getOptions () {}

	/**
	 * @param mixed $value
	 */
	public function setOptions ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class Option extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getName () {}

	/**
	 * @param mixed $value
	 */
	public function setName ($value = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

class Syntax  {
	const SYNTAX_PROTO2 = 0;
	const SYNTAX_PROTO3 = 1;


	/**
	 * @param mixed $key
	 */
	public static function name ($key = null) {}

	/**
	 * @param mixed $key
	 */
	public static function value ($key = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Timestamp  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class Timestamp extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getSeconds () {}

	/**
	 * @param mixed $value
	 */
	public function setSeconds ($value = null) {}

	public function getNanos () {}

	/**
	 * @param mixed $value
	 */
	public function setNanos ($value = null) {}

	/**
	 * @param mixed $datetime
	 */
	public function fromDateTime ($datetime = null) {}

	public function toDateTime () {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}


}


namespace GPBMetadata\Google\Protobuf {

class Wrappers  {

	public static function initOnce () {}

}


}


namespace Google\Protobuf {

final class DoubleValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class FloatValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class Int64Value extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class UInt64Value extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class Int32Value extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class UInt32Value extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class BoolValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class StringValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

final class BytesValue extends \Google\Protobuf\Internal\Message  {

	/**
	 * @param mixed $data [optional]
	 */
	public function __construct ($data = null) {}

	public function getValue () {}

	/**
	 * @param mixed $value
	 */
	public function setValue ($value = null) {}

	public function clear () {}

	public function discardUnknownFields () {}

	public function serializeToString () {}

	/**
	 * @param mixed $data
	 */
	public function mergeFromString ($data = null) {}

	public function serializeToJsonString () {}

	/**
	 * @param mixed $data
	 * @param mixed $arg [optional]
	 */
	public function mergeFromJsonString ($data = null, $arg = null) {}

	/**
	 * @param mixed $data
	 */
	public function mergeFrom ($data = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readWrapperValue ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeWrapperValue ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function hasOneof ($field = null) {}

	/**
	 * @param mixed $field
	 */
	protected function readOneof ($field = null) {}

	/**
	 * @param mixed $field
	 * @param mixed $value
	 */
	protected function writeOneof ($field = null, $value = null) {}

	/**
	 * @param mixed $field
	 */
	protected function whichOneof ($field = null) {}

}

}

// End of protobuf v.3.23.1
