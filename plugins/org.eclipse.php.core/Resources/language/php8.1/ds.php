<?php

// Start of ds v.1.4.0

namespace Ds {

interface Hashable  {

	abstract public function hash ()

	/**
	 * @param mixed $obj
	 */
	abstract public function equals ($obj = null): bool

}

interface Collection extends \IteratorAggregate, \Traversable, \Countable, \JsonSerializable {

	abstract public function clear ()

	abstract public function copy (): Ds\Collection

	abstract public function isEmpty (): bool

	abstract public function toArray (): array

	abstract public function getIterator ()

	/**
	 * Count elements of an object
	 * @link http://www.php.net/manual/en/countable.count.php
	 * @return int The custom count as an int.
	 * <p>
	 * The return value is cast to an int.
	 * </p>
	 */
	abstract public function count ()

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize ()

}

interface Sequence extends \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {

	/**
	 * @param int $capacity
	 */
	abstract public function allocate (int $capacity)

	abstract public function capacity (): int

	/**
	 * @param mixed $values [optional]
	 */
	abstract public function contains (...$values): bool

	/**
	 * @param callable|null $callback [optional]
	 */
	abstract public function filter (callable|null $callback = null): Ds\Sequence

	/**
	 * @param mixed $value
	 */
	abstract public function find ($value = null)

	abstract public function first ()

	/**
	 * @param int $index
	 */
	abstract public function get (int $index)

	/**
	 * @param int $index
	 * @param mixed $values [optional]
	 */
	abstract public function insert (int $index, ...$values)

	/**
	 * @param string $glue [optional]
	 */
	abstract public function join (string $glue = null): string

	abstract public function last ()

	/**
	 * @param callable $callback
	 */
	abstract public function map (callable $callback): Ds\Sequence

	/**
	 * @param mixed $values
	 */
	abstract public function merge ($values = null): Ds\Sequence

	abstract public function pop ()

	/**
	 * @param mixed $values [optional]
	 */
	abstract public function push (...$values)

	/**
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	abstract public function reduce (callable $callback, $initial = null)

	/**
	 * @param int $index
	 */
	abstract public function remove (int $index)

	abstract public function reverse ()

	/**
	 * @param int $rotations
	 */
	abstract public function rotate (int $rotations)

	/**
	 * @param int $index
	 * @param mixed $value
	 */
	abstract public function set (int $index, $value = null)

	abstract public function shift ()

	/**
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	abstract public function slice (int $index, int|null $length = null): Ds\Sequence

	/**
	 * @param callable|null $comparator [optional]
	 */
	abstract public function sort (callable|null $comparator = null)

	/**
	 * @param mixed $values [optional]
	 */
	abstract public function unshift (...$values)

	abstract public function clear ()

	abstract public function copy (): Ds\Collection

	abstract public function isEmpty (): bool

	abstract public function toArray (): array

	abstract public function getIterator ()

	/**
	 * Count elements of an object
	 * @link http://www.php.net/manual/en/countable.count.php
	 * @return int The custom count as an int.
	 * <p>
	 * The return value is cast to an int.
	 * </p>
	 */
	abstract public function count ()

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize ()

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetExists (mixed $offset = null)

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetGet (mixed $offset = null)

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	abstract public function offsetSet (mixed $offset = null, mixed $value = null)

	/**
	 * @param mixed $offset
	 */
	abstract public function offsetUnset (mixed $offset = null)

}

final class Vector implements \Ds\Sequence, \ArrayAccess, \IteratorAggregate, \Traversable, \Countable, \JsonSerializable, \Ds\Collection {
	const MIN_CAPACITY = 8;


	/**
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = null) {}

	public function getIterator (): Traversable {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * @param callable $callback
	 */
	public function apply (callable $callback) {}

	public function capacity (): int {}

	/**
	 * @param mixed $values [optional]
	 */
	public function contains (...$values): bool {}

	/**
	 * @param callable|null $callback [optional]
	 */
	public function filter (callable|null $callback = null): Ds\Sequence {}

	/**
	 * @param mixed $value
	 */
	public function find ($value = null) {}

	public function first () {}

	/**
	 * @param int $index
	 */
	public function get (int $index) {}

	/**
	 * @param int $index
	 * @param mixed $values [optional]
	 */
	public function insert (int $index, ...$values) {}

	/**
	 * @param string $glue [optional]
	 */
	public function join (string $glue = null): string {}

	public function last () {}

	/**
	 * @param callable $callback
	 */
	public function map (callable $callback): Ds\Sequence {}

	/**
	 * @param mixed $values
	 */
	public function merge ($values = null): Ds\Sequence {}

	/**
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	public function pop () {}

	/**
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	/**
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = null) {}

	/**
	 * @param int $index
	 */
	public function remove (int $index) {}

	public function reverse () {}

	public function reversed (): Ds\Sequence {}

	/**
	 * @param int $rotations
	 */
	public function rotate (int $rotations) {}

	/**
	 * @param int $index
	 * @param mixed $value
	 */
	public function set (int $index, $value = null) {}

	public function shift () {}

	/**
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, int|null $length = null): Ds\Sequence {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sort (callable|null $comparator = null) {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (callable|null $comparator = null): Ds\Sequence {}

	public function sum () {}

	/**
	 * @param mixed $values [optional]
	 */
	public function unshift (...$values) {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

final class Deque implements \Ds\Sequence, \ArrayAccess, \IteratorAggregate, \Traversable, \Countable, \JsonSerializable, \Ds\Collection {
	const MIN_CAPACITY = 8;


	/**
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = null) {}

	public function getIterator (): Traversable {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * @param callable $callback
	 */
	public function apply (callable $callback) {}

	public function capacity (): int {}

	/**
	 * @param mixed $values [optional]
	 */
	public function contains (...$values): bool {}

	/**
	 * @param callable|null $callback [optional]
	 */
	public function filter (callable|null $callback = null): Ds\Sequence {}

	/**
	 * @param mixed $value
	 */
	public function find ($value = null) {}

	public function first () {}

	/**
	 * @param int $index
	 */
	public function get (int $index) {}

	/**
	 * @param int $index
	 * @param mixed $values [optional]
	 */
	public function insert (int $index, ...$values) {}

	/**
	 * @param string $glue [optional]
	 */
	public function join (string $glue = null): string {}

	public function last () {}

	/**
	 * @param callable $callback
	 */
	public function map (callable $callback): Ds\Sequence {}

	/**
	 * @param mixed $values
	 */
	public function merge ($values = null): Ds\Sequence {}

	/**
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	public function pop () {}

	/**
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	/**
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = null) {}

	/**
	 * @param int $index
	 */
	public function remove (int $index) {}

	public function reverse () {}

	public function reversed (): Ds\Sequence {}

	/**
	 * @param int $rotations
	 */
	public function rotate (int $rotations) {}

	/**
	 * @param int $index
	 * @param mixed $value
	 */
	public function set (int $index, $value = null) {}

	public function shift () {}

	/**
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, int|null $length = null): Ds\Sequence {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sort (callable|null $comparator = null) {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (callable|null $comparator = null): Ds\Sequence {}

	public function sum () {}

	/**
	 * @param mixed $values [optional]
	 */
	public function unshift (...$values) {}

}

final class Stack implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {

	/**
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = null) {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	public function capacity (): int {}

	public function peek () {}

	public function pop () {}

	/**
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	public function getIterator (): Traversable {}

	/**
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

final class Queue implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = null) {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	public function capacity (): int {}

	public function peek () {}

	public function pop () {}

	/**
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	public function getIterator (): Traversable {}

	/**
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

final class Map implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = null) {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * @param callable $callback
	 */
	public function apply (callable $callback) {}

	public function capacity (): int {}

	/**
	 * @param \Ds\Map $map
	 */
	public function diff (\Ds\Map $map): Ds\Map {}

	/**
	 * @param callable|null $callback [optional]
	 */
	public function filter (callable|null $callback = null): Ds\Map {}

	public function first (): Ds\Pair {}

	/**
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	public function get ($key = null, $default = null) {}

	/**
	 * @param mixed $key
	 */
	public function hasKey ($key = null): bool {}

	/**
	 * @param mixed $value
	 */
	public function hasValue ($value = null): bool {}

	/**
	 * @param \Ds\Map $map
	 */
	public function intersect (\Ds\Map $map): Ds\Map {}

	public function keys (): Ds\Set {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function ksort (callable|null $comparator = null) {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function ksorted (callable|null $comparator = null): Ds\Map {}

	public function last (): Ds\Pair {}

	/**
	 * @param callable $callback
	 */
	public function map (callable $callback): Ds\Map {}

	/**
	 * @param mixed $values
	 */
	public function merge ($values = null): Ds\Map {}

	public function pairs (): Ds\Sequence {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function put ($key = null, $value = null) {}

	/**
	 * @param mixed $values
	 */
	public function putAll ($values = null) {}

	/**
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = null) {}

	/**
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	public function remove ($key = null, $default = null) {}

	public function reverse () {}

	public function reversed (): Ds\Map {}

	/**
	 * @param int $position
	 */
	public function skip (int $position): Ds\Pair {}

	/**
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, int|null $length = null): Ds\Map {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sort (callable|null $comparator = null) {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (callable|null $comparator = null): Ds\Map {}

	public function sum () {}

	/**
	 * @param mixed $map
	 */
	public function union ($map = null): Ds\Map {}

	public function values (): Ds\Sequence {}

	/**
	 * @param \Ds\Map $map
	 */
	public function xor (\Ds\Map $map): Ds\Map {}

	public function getIterator (): Traversable {}

	/**
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

final class Set implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = null) {}

	/**
	 * @param mixed $values [optional]
	 */
	public function add (...$values) {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	public function capacity (): int {}

	/**
	 * @param mixed $values [optional]
	 */
	public function contains (...$values): bool {}

	/**
	 * @param \Ds\Set $set
	 */
	public function diff (\Ds\Set $set): Ds\Set {}

	/**
	 * @param callable|null $predicate [optional]
	 */
	public function filter (callable|null $predicate = null): Ds\Set {}

	public function first () {}

	/**
	 * @param int $index
	 */
	public function get (int $index) {}

	/**
	 * @param \Ds\Set $set
	 */
	public function intersect (\Ds\Set $set): Ds\Set {}

	/**
	 * @param string $glue [optional]
	 */
	public function join (string $glue = null) {}

	public function last () {}

	/**
	 * @param callable $callback
	 */
	public function map (callable $callback): Ds\Set {}

	/**
	 * @param mixed $values
	 */
	public function merge ($values = null): Ds\Set {}

	/**
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = null) {}

	/**
	 * @param mixed $values [optional]
	 */
	public function remove (...$values) {}

	public function reverse () {}

	public function reversed (): Ds\Set {}

	/**
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, int|null $length = null): Ds\Set {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sort (callable|null $comparator = null) {}

	/**
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (callable|null $comparator = null): Ds\Set {}

	public function sum () {}

	/**
	 * @param \Ds\Set $set
	 */
	public function union (\Ds\Set $set): Ds\Set {}

	/**
	 * @param \Ds\Set $set
	 */
	public function xor (\Ds\Set $set): Ds\Set {}

	public function getIterator (): Traversable {}

	/**
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

final class PriorityQueue implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate {
	const MIN_CAPACITY = 8;


	public function __construct () {}

	/**
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	public function capacity (): int {}

	public function peek () {}

	public function pop () {}

	/**
	 * @param mixed $value
	 * @param mixed $priority
	 */
	public function push ($value = null, $priority = null) {}

	public function getIterator (): Traversable {}

	public function clear () {}

	public function copy (): Ds\Collection {}

	public function count (): int {}

	public function isEmpty (): bool {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

final class Pair implements \JsonSerializable {
	public $key;
	public $value;


	/**
	 * @param mixed $key [optional]
	 * @param mixed $value [optional]
	 */
	public function __construct ($key = null, $value = null) {}

	public function copy (): Ds\Pair {}

	public function jsonSerialize () {}

	public function toArray (): array {}

}

}

// End of ds v.1.4.0
