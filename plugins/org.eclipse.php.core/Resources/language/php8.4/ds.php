<?php

// Start of ds v.1.4.0

namespace Ds {

interface Hashable  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function hash ();

	/**
	 * {@inheritdoc}
	 * @param mixed $obj
	 */
	abstract public function equals ($obj = null): bool;

}

interface Collection extends \IteratorAggregate, \Traversable, \Countable, \JsonSerializable {

	/**
	 * {@inheritdoc}
	 */
	abstract public function clear ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function copy (): \Ds\Collection;

	/**
	 * {@inheritdoc}
	 */
	abstract public function isEmpty (): bool;

	/**
	 * {@inheritdoc}
	 */
	abstract public function toArray (): array;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getIterator ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function count ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function jsonSerialize ();

}

interface Sequence extends \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	abstract public function allocate (int $capacity);

	/**
	 * {@inheritdoc}
	 */
	abstract public function capacity (): int;

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	abstract public function contains (...$values): bool;

	/**
	 * {@inheritdoc}
	 * @param callable|null $callback [optional]
	 */
	abstract public function filter (?callable $callback = NULL): \Ds\Sequence;

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	abstract public function find ($value = null);

	/**
	 * {@inheritdoc}
	 */
	abstract public function first ();

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	abstract public function get (int $index);

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $values [optional]
	 */
	abstract public function insert (int $index, ...$values);

	/**
	 * {@inheritdoc}
	 * @param string $glue [optional]
	 */
	abstract public function join (string $glue = NULL): string;

	/**
	 * {@inheritdoc}
	 */
	abstract public function last ();

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	abstract public function map (callable $callback): \Ds\Sequence;

	/**
	 * {@inheritdoc}
	 * @param mixed $values
	 */
	abstract public function merge ($values = null): \Ds\Sequence;

	/**
	 * {@inheritdoc}
	 */
	abstract public function pop ();

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	abstract public function push (...$values);

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	abstract public function reduce (callable $callback, $initial = NULL);

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	abstract public function remove (int $index);

	/**
	 * {@inheritdoc}
	 */
	abstract public function reverse ();

	/**
	 * {@inheritdoc}
	 * @param int $rotations
	 */
	abstract public function rotate (int $rotations);

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $value
	 */
	abstract public function set (int $index, $value = null);

	/**
	 * {@inheritdoc}
	 */
	abstract public function shift ();

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	abstract public function slice (int $index, ?int $length = NULL): \Ds\Sequence;

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	abstract public function sort (?callable $comparator = NULL);

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	abstract public function unshift (...$values);

	/**
	 * {@inheritdoc}
	 */
	abstract public function clear ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function copy (): \Ds\Collection;

	/**
	 * {@inheritdoc}
	 */
	abstract public function isEmpty (): bool;

	/**
	 * {@inheritdoc}
	 */
	abstract public function toArray (): array;

	/**
	 * {@inheritdoc}
	 */
	abstract public function getIterator ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function count ();

	/**
	 * {@inheritdoc}
	 */
	abstract public function jsonSerialize ();

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	abstract public function offsetExists (mixed $offset = null);

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	abstract public function offsetGet (mixed $offset = null);

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	abstract public function offsetSet (mixed $offset = null, mixed $value = null);

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	abstract public function offsetUnset (mixed $offset = null);

}

final class Vector implements \Ds\Sequence, \ArrayAccess, \IteratorAggregate, \Traversable, \Countable, \JsonSerializable, \Ds\Collection {
	const MIN_CAPACITY = 8;


	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function apply (callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function contains (...$values): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $callback [optional]
	 */
	public function filter (?callable $callback = NULL): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function find ($value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function first () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function get (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $values [optional]
	 */
	public function insert (int $index, ...$values) {}

	/**
	 * {@inheritdoc}
	 * @param string $glue [optional]
	 */
	public function join (string $glue = NULL): string {}

	/**
	 * {@inheritdoc}
	 */
	public function last () {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function map (callable $callback): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values
	 */
	public function merge ($values = null): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function remove (int $index) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 */
	public function reversed (): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param int $rotations
	 */
	public function rotate (int $rotations) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $value
	 */
	public function set (int $index, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function shift () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, ?int $length = NULL): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sort (?callable $comparator = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (?callable $comparator = NULL): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 */
	public function sum () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function unshift (...$values) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

final class Deque implements \Ds\Sequence, \ArrayAccess, \IteratorAggregate, \Traversable, \Countable, \JsonSerializable, \Ds\Collection {
	const MIN_CAPACITY = 8;


	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function apply (callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function contains (...$values): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $callback [optional]
	 */
	public function filter (?callable $callback = NULL): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function find ($value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function first () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function get (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $values [optional]
	 */
	public function insert (int $index, ...$values) {}

	/**
	 * {@inheritdoc}
	 * @param string $glue [optional]
	 */
	public function join (string $glue = NULL): string {}

	/**
	 * {@inheritdoc}
	 */
	public function last () {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function map (callable $callback): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values
	 */
	public function merge ($values = null): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function remove (int $index) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 */
	public function reversed (): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param int $rotations
	 */
	public function rotate (int $rotations) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $value
	 */
	public function set (int $index, $value = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function shift () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, ?int $length = NULL): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sort (?callable $comparator = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (?callable $comparator = NULL): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 */
	public function sum () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function unshift (...$values) {}

}

final class Stack implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function peek () {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

final class Queue implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function peek () {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function push (...$values) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

final class Map implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function apply (callable $callback) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Map $map
	 */
	public function diff (\Ds\Map $map): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $callback [optional]
	 */
	public function filter (?callable $callback = NULL): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function first (): \Ds\Pair {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	public function get ($key = null, $default = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hasKey ($key = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function hasValue ($value = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Map $map
	 */
	public function intersect (\Ds\Map $map): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function keys (): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function ksort (?callable $comparator = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function ksorted (?callable $comparator = NULL): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function last (): \Ds\Pair {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function map (callable $callback): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values
	 */
	public function merge ($values = null): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function pairs (): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function put ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values
	 */
	public function putAll ($values = null) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $default [optional]
	 */
	public function remove ($key = null, $default = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 */
	public function reversed (): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 * @param int $position
	 */
	public function skip (int $position): \Ds\Pair {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, ?int $length = NULL): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sort (?callable $comparator = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (?callable $comparator = NULL): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function sum () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $map
	 */
	public function union ($map = null): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function values (): \Ds\Sequence {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Map $map
	 */
	public function xor (\Ds\Map $map): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

final class Set implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function __construct ($values = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function add (...$values) {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function contains (...$values): bool {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Set $set
	 */
	public function diff (\Ds\Set $set): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $predicate [optional]
	 */
	public function filter (?callable $predicate = NULL): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 */
	public function first () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function get (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Set $set
	 */
	public function intersect (\Ds\Set $set): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param string $glue [optional]
	 */
	public function join (string $glue = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function last () {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function map (callable $callback): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values
	 */
	public function merge ($values = null): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $initial [optional]
	 */
	public function reduce (callable $callback, $initial = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $values [optional]
	 */
	public function remove (...$values) {}

	/**
	 * {@inheritdoc}
	 */
	public function reverse () {}

	/**
	 * {@inheritdoc}
	 */
	public function reversed (): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int|null $length [optional]
	 */
	public function slice (int $index, ?int $length = NULL): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sort (?callable $comparator = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $comparator [optional]
	 */
	public function sorted (?callable $comparator = NULL): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 */
	public function sum () {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Set $set
	 */
	public function union (\Ds\Set $set): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 * @param \Ds\Set $set
	 */
	public function xor (\Ds\Set $set): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetExists ($offset = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetGet (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 * @param mixed $value
	 */
	public function offsetSet (mixed $offset = null, mixed $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $offset
	 */
	public function offsetUnset (mixed $offset = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

final class PriorityQueue implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate {
	const MIN_CAPACITY = 8;


	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param int $capacity
	 */
	public function allocate (int $capacity) {}

	/**
	 * {@inheritdoc}
	 */
	public function capacity (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function peek () {}

	/**
	 * {@inheritdoc}
	 */
	public function pop () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 * @param mixed $priority
	 */
	public function push ($value = null, $priority = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * {@inheritdoc}
	 */
	public function clear () {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Collection {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

final class Pair implements \JsonSerializable {

	public $key;

	public $value;

	/**
	 * {@inheritdoc}
	 * @param mixed $key [optional]
	 * @param mixed $value [optional]
	 */
	public function __construct ($key = NULL, $value = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function copy (): \Ds\Pair {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * {@inheritdoc}
	 */
	public function toArray (): array {}

}

}

// End of ds v.1.4.0
