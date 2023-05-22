<?php

// Start of ds v.1.4.0

namespace Ds {

interface Hashable  {

	/**
	 * Returns a scalar value to be used as a hash value
	 * @link http://www.php.net/manual/en/ds-hashable.hash.php
	 * @return mixed A scalar value to be used as this object's hash value.
	 */
	abstract public function hash (): mixed;

	/**
	 * Determines whether an object is equal to the current instance
	 * @link http://www.php.net/manual/en/ds-hashable.equals.php
	 * @param object $obj The object to compare the current instance to, which is always an instance of
	 * the same class.
	 * @return bool true if equal, false otherwise.
	 */
	abstract public function equals (object $obj): bool;

}

interface Collection extends \IteratorAggregate, \Traversable, \Countable, \JsonSerializable {

	/**
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-collection.clear.php
	 * @return void No value is returned.
	 */
	abstract public function clear (): void;

	/**
	 * Returns a shallow copy of the collection
	 * @link http://www.php.net/manual/en/ds-collection.copy.php
	 * @return \Ds\Collection Returns a shallow copy of the collection.
	 */
	abstract public function copy (): \Ds\Collection;

	/**
	 * Returns whether the collection is empty
	 * @link http://www.php.net/manual/en/ds-collection.isempty.php
	 * @return bool Returns true if the collection is empty, false otherwise.
	 */
	abstract public function isEmpty (): bool;

	/**
	 * Converts the collection to an array
	 * @link http://www.php.net/manual/en/ds-collection.toarray.php
	 * @return array An array containing all the values in the same order as the collection.
	 */
	abstract public function toArray (): array;

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/iteratoraggregate.getiterator.php
	 * @return \Traversable An instance of an object implementing Iterator or
	 * Traversable
	 */
	abstract public function getIterator (): \Traversable;

	/**
	 * Count elements of an object
	 * @link http://www.php.net/manual/en/countable.count.php
	 * @return int The custom count as an int.
	 * <p>The return value is cast to an int.</p>
	 */
	abstract public function count (): int;

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize (): mixed;

}

interface Sequence extends \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-sequence.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * @return void No value is returned.
	 */
	abstract public function allocate (int $capacity): void;

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-sequence.capacity.php
	 * @return int The current capacity.
	 */
	abstract public function capacity (): int;

	/**
	 * Determines if the sequence contains given values
	 * @link http://www.php.net/manual/en/ds-sequence.contains.php
	 * @param mixed $values Values to check.
	 * @return bool false if any of the provided values are not in the
	 * sequence, true otherwise.
	 */
	abstract public function contains (mixed ...$values): bool;

	/**
	 * Creates a new sequence using a callable to
	 * determine which values to include
	 * @link http://www.php.net/manual/en/ds-sequence.filter.php
	 * @param callable $callback [optional] bool
	 * callback
	 * mixedvalue
	 * <p>Optional callable which returns true if the value should be included, false otherwise.</p>
	 * <p>If a callback is not provided, only values which are true
	 * (see converting to boolean)
	 * will be included.</p>
	 * @return \Ds\Sequence A new sequence containing all the values for which
	 * either the callback returned true, or all values that
	 * convert to true if a callback was not provided.
	 */
	abstract public function filter (callable $callback = null): \Ds\Sequence;

	/**
	 * Attempts to find a value's index
	 * @link http://www.php.net/manual/en/ds-sequence.find.php
	 * @param mixed $value The value to find.
	 * @return mixed The index of the value, or false if not found.
	 * <p>Values will be compared by value and by type.</p>
	 */
	abstract public function find (mixed $value): mixed;

	/**
	 * Returns the first value in the sequence
	 * @link http://www.php.net/manual/en/ds-sequence.first.php
	 * @return mixed The first value in the sequence.
	 */
	abstract public function first (): mixed;

	/**
	 * Returns the value at a given index
	 * @link http://www.php.net/manual/en/ds-sequence.get.php
	 * @param int $index The index to access, starting at 0.
	 * @return mixed The value at the requested index.
	 */
	abstract public function get (int $index): mixed;

	/**
	 * Inserts values at a given index
	 * @link http://www.php.net/manual/en/ds-sequence.insert.php
	 * @param int $index The index at which to insert. 0 &lt;= index &lt;= count
	 * <p>You can insert at the index equal to the number of values.</p>
	 * @param mixed $values The value or values to insert.
	 * @return void No value is returned.
	 */
	abstract public function insert (int $index, mixed ...$values): void;

	/**
	 * Joins all values together as a string
	 * @link http://www.php.net/manual/en/ds-sequence.join.php
	 * @param string $glue [optional] An optional string to separate each value.
	 * @return string All values of the sequence joined together as a string.
	 */
	abstract public function join (string $glue = null): string;

	/**
	 * Returns the last value
	 * @link http://www.php.net/manual/en/ds-sequence.last.php
	 * @return mixed The last value in the sequence.
	 */
	abstract public function last (): mixed;

	/**
	 * Returns the result of applying a callback to each value
	 * @link http://www.php.net/manual/en/ds-sequence.map.php
	 * @param callable $callback mixed
	 * callback
	 * mixedvalue
	 * <p>A callable to apply to each value in the sequence.</p>
	 * <p>The callable should return what the new value will be in the new sequence.</p>
	 * @return \Ds\Sequence The result of applying a callback to each value in
	 * the sequence.
	 * <p>The values of the current instance won't be affected.</p>
	 */
	abstract public function map (callable $callback): \Ds\Sequence;

	/**
	 * Returns the result of adding all given values to the sequence
	 * @link http://www.php.net/manual/en/ds-sequence.merge.php
	 * @param mixed $values A traversable object or an array.
	 * @return \Ds\Sequence The result of adding all given values to the sequence,
	 * effectively the same as adding the values to a copy, then returning that copy.
	 * <p>The current instance won't be affected.</p>
	 */
	abstract public function merge (mixed $values): \Ds\Sequence;

	/**
	 * Removes and returns the last value
	 * @link http://www.php.net/manual/en/ds-sequence.pop.php
	 * @return mixed The removed last value.
	 */
	abstract public function pop (): mixed;

	/**
	 * Adds values to the end of the sequence
	 * @link http://www.php.net/manual/en/ds-sequence.push.php
	 * @param mixed $values The values to add.
	 * @return void No value is returned.
	 */
	abstract public function push (mixed ...$values): void;

	/**
	 * Reduces the sequence to a single value using a callback function
	 * @link http://www.php.net/manual/en/ds-sequence.reduce.php
	 * @param callable $callback The return value of the previous callback, or initial if
	 * it's the first iteration.
	 * <p>The value of the current iteration.</p>
	 * @param mixed $initial [optional] The initial value of the carry value. Can be null.
	 * @return mixed The return value of the final callback.
	 */
	abstract public function reduce (callable $callback, mixed $initial = null): mixed;

	/**
	 * Removes and returns a value by index
	 * @link http://www.php.net/manual/en/ds-sequence.remove.php
	 * @param int $index The index of the value to remove.
	 * @return mixed The value that was removed.
	 */
	abstract public function remove (int $index): mixed;

	/**
	 * Reverses the sequence in-place
	 * @link http://www.php.net/manual/en/ds-sequence.reverse.php
	 * @return void No value is returned.
	 */
	abstract public function reverse (): void;

	/**
	 * Rotates the sequence by a given number of rotations
	 * @link http://www.php.net/manual/en/ds-sequence.rotate.php
	 * @param int $rotations The number of times the sequence should be rotated.
	 * @return void No value is returned.. The sequence of the current instance will be rotated.
	 */
	abstract public function rotate (int $rotations): void;

	/**
	 * Updates a value at a given index
	 * @link http://www.php.net/manual/en/ds-sequence.set.php
	 * @param int $index The index of the value to update.
	 * @param mixed $value The new value.
	 * @return void No value is returned.
	 */
	abstract public function set (int $index, mixed $value): void;

	/**
	 * Removes and returns the first value
	 * @link http://www.php.net/manual/en/ds-sequence.shift.php
	 * @return mixed The first value, which was removed.
	 */
	abstract public function shift (): mixed;

	/**
	 * Returns a sub-sequence of a given range
	 * @link http://www.php.net/manual/en/ds-sequence.slice.php
	 * @param int $index The index at which the sub-sequence starts.
	 * <p>If positive, the sequence will start at that index in the sequence.
	 * If negative, the sequence will start that far from the end.</p>
	 * @param int $length [optional] If a length is given and is positive, the resulting
	 * sequence will have up to that many values in it.
	 * If the length results in an overflow, only
	 * values up to the end of the sequence will be included.
	 * If a length is given and is negative, the sequence
	 * will stop that many values from the end.
	 * If a length is not provided, the resulting sequence
	 * will contain all values between the index and the
	 * end of the sequence.
	 * @return \Ds\Sequence A sub-sequence of the given range.
	 */
	abstract public function slice (int $index, int $length = null): \Ds\Sequence;

	/**
	 * Sorts the sequence in-place
	 * @link http://www.php.net/manual/en/ds-sequence.sort.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return void No value is returned.
	 */
	abstract public function sort (callable $comparator = null): void;

	/**
	 * Adds values to the front of the sequence
	 * @link http://www.php.net/manual/en/ds-sequence.unshift.php
	 * @param mixed $values [optional] The values to add to the front of the sequence.
	 * <p>
	 * Multiple values will be added in the same order that they are
	 * passed.
	 * </p>
	 * <p>Multiple values will be added in the same order that they are
	 * passed.</p>
	 * @return void No value is returned.
	 */
	abstract public function unshift (mixed $values = null): void;

	/**
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-collection.clear.php
	 * @return void No value is returned.
	 */
	abstract public function clear (): void;

	/**
	 * Returns a shallow copy of the collection
	 * @link http://www.php.net/manual/en/ds-collection.copy.php
	 * @return \Ds\Collection Returns a shallow copy of the collection.
	 */
	abstract public function copy (): \Ds\Collection;

	/**
	 * Returns whether the collection is empty
	 * @link http://www.php.net/manual/en/ds-collection.isempty.php
	 * @return bool Returns true if the collection is empty, false otherwise.
	 */
	abstract public function isEmpty (): bool;

	/**
	 * Converts the collection to an array
	 * @link http://www.php.net/manual/en/ds-collection.toarray.php
	 * @return array An array containing all the values in the same order as the collection.
	 */
	abstract public function toArray (): array;

	/**
	 * Retrieve an external iterator
	 * @link http://www.php.net/manual/en/iteratoraggregate.getiterator.php
	 * @return \Traversable An instance of an object implementing Iterator or
	 * Traversable
	 */
	abstract public function getIterator (): \Traversable;

	/**
	 * Count elements of an object
	 * @link http://www.php.net/manual/en/countable.count.php
	 * @return int The custom count as an int.
	 * <p>The return value is cast to an int.</p>
	 */
	abstract public function count (): int;

	/**
	 * Specify data which should be serialized to JSON
	 * @link http://www.php.net/manual/en/jsonserializable.jsonserialize.php
	 * @return mixed Returns data which can be serialized by json_encode,
	 * which is a value of any type other than a resource.
	 */
	abstract public function jsonSerialize (): mixed;

	/**
	 * Whether an offset exists
	 * @link http://www.php.net/manual/en/arrayaccess.offsetexists.php
	 * @param mixed $offset 
	 * @return bool Returns true on success or false on failure.
	 * <p>The return value will be casted to bool if non-boolean was returned.</p>
	 */
	abstract public function offsetExists (mixed $offset): bool;

	/**
	 * Offset to retrieve
	 * @link http://www.php.net/manual/en/arrayaccess.offsetget.php
	 * @param mixed $offset 
	 * @return mixed Can return all value types.
	 */
	abstract public function offsetGet (mixed $offset): mixed;

	/**
	 * Assign a value to the specified offset
	 * @link http://www.php.net/manual/en/arrayaccess.offsetset.php
	 * @param mixed $offset 
	 * @param mixed $value 
	 * @return void No value is returned.
	 */
	abstract public function offsetSet (mixed $offset, mixed $value): void;

	/**
	 * Unset an offset
	 * @link http://www.php.net/manual/en/arrayaccess.offsetunset.php
	 * @param mixed $offset 
	 * @return void No value is returned.
	 */
	abstract public function offsetUnset (mixed $offset): void;

}

final class Vector implements \Ds\Sequence, \ArrayAccess, \IteratorAggregate, \Traversable, \Countable, \JsonSerializable, \Ds\Collection {
	const MIN_CAPACITY = 8;


	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-vector.construct.php
	 * @param mixed $values [optional] A traversable object or an array to use for the initial values.
	 * @return mixed 
	 */
	public function __construct (mixed $values = null): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-vector.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Updates all values by applying a callback function to each value
	 * @link http://www.php.net/manual/en/ds-vector.apply.php
	 * @param callable $callback mixed
	 * callback
	 * mixedvalue
	 * <p>A callable to apply to each value in the vector.</p>
	 * <p>The callback should return what the value should be replaced by.</p>
	 * @return void No value is returned.
	 */
	public function apply (callable $callback): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-vector.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Determines if the vector contains given values
	 * @link http://www.php.net/manual/en/ds-vector.contains.php
	 * @param mixed $values Values to check.
	 * @return bool false if any of the provided values are not in the
	 * vector, true otherwise.
	 */
	public function contains (mixed ...$values): bool {}

	/**
	 * Creates a new vector using a callable to
	 * determine which values to include
	 * @link http://www.php.net/manual/en/ds-vector.filter.php
	 * @param callable $callback [optional] bool
	 * callback
	 * mixedvalue
	 * <p>Optional callable which returns true if the value should be included, false otherwise.</p>
	 * <p>If a callback is not provided, only values which are true
	 * (see converting to boolean)
	 * will be included.</p>
	 * @return \Ds\Vector A new vector containing all the values for which
	 * either the callback returned true, or all values that
	 * convert to true if a callback was not provided.
	 */
	public function filter (callable $callback = null): \Ds\Vector {}

	/**
	 * Attempts to find a value's index
	 * @link http://www.php.net/manual/en/ds-vector.find.php
	 * @param mixed $value The value to find.
	 * @return mixed The index of the value, or false if not found.
	 * <p>Values will be compared by value and by type.</p>
	 */
	public function find (mixed $value): mixed {}

	/**
	 * Returns the first value in the vector
	 * @link http://www.php.net/manual/en/ds-vector.first.php
	 * @return mixed The first value in the vector.
	 */
	public function first (): mixed {}

	/**
	 * Returns the value at a given index
	 * @link http://www.php.net/manual/en/ds-vector.get.php
	 * @param int $index The index to access, starting at 0.
	 * @return mixed The value at the requested index.
	 */
	public function get (int $index): mixed {}

	/**
	 * Inserts values at a given index
	 * @link http://www.php.net/manual/en/ds-vector.insert.php
	 * @param int $index The index at which to insert. 0 &lt;= index &lt;= count
	 * <p>You can insert at the index equal to the number of values.</p>
	 * @param mixed $values The value or values to insert.
	 * @return void No value is returned.
	 */
	public function insert (int $index, mixed ...$values): void {}

	/**
	 * Joins all values together as a string
	 * @link http://www.php.net/manual/en/ds-vector.join.php
	 * @param string $glue [optional] An optional string to separate each value.
	 * @return string All values of the vector joined together as a string.
	 */
	public function join (string $glue = null): string {}

	/**
	 * Returns the last value
	 * @link http://www.php.net/manual/en/ds-vector.last.php
	 * @return mixed The last value in the vector.
	 */
	public function last (): mixed {}

	/**
	 * Returns the result of applying a callback to each value
	 * @link http://www.php.net/manual/en/ds-vector.map.php
	 * @param callable $callback mixed
	 * callback
	 * mixedvalue
	 * <p>A callable to apply to each value in the vector.</p>
	 * <p>The callable should return what the new value will be in the new vector.</p>
	 * @return \Ds\Vector The result of applying a callback to each value in
	 * the vector.
	 * <p>The values of the current instance won't be affected.</p>
	 */
	public function map (callable $callback): \Ds\Vector {}

	/**
	 * Returns the result of adding all given values to the vector
	 * @link http://www.php.net/manual/en/ds-vector.merge.php
	 * @param mixed $values A traversable object or an array.
	 * @return \Ds\Vector The result of adding all given values to the vector,
	 * effectively the same as adding the values to a copy, then returning that copy.
	 * <p>The current instance won't be affected.</p>
	 */
	public function merge (mixed $values): \Ds\Vector {}

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
	 * Removes and returns the last value
	 * @link http://www.php.net/manual/en/ds-vector.pop.php
	 * @return mixed The removed last value.
	 */
	public function pop (): mixed {}

	/**
	 * Adds values to the end of the vector
	 * @link http://www.php.net/manual/en/ds-vector.push.php
	 * @param mixed $values The values to add.
	 * @return void No value is returned.
	 */
	public function push (mixed ...$values): void {}

	/**
	 * Reduces the vector to a single value using a callback function
	 * @link http://www.php.net/manual/en/ds-vector.reduce.php
	 * @param callable $callback The return value of the previous callback, or initial if
	 * it's the first iteration.
	 * <p>The value of the current iteration.</p>
	 * @param mixed $initial [optional] The initial value of the carry value. Can be null.
	 * @return mixed The return value of the final callback.
	 */
	public function reduce (callable $callback, mixed $initial = null): mixed {}

	/**
	 * Removes and returns a value by index
	 * @link http://www.php.net/manual/en/ds-vector.remove.php
	 * @param int $index The index of the value to remove.
	 * @return mixed The value that was removed.
	 */
	public function remove (int $index): mixed {}

	/**
	 * Reverses the vector in-place
	 * @link http://www.php.net/manual/en/ds-vector.reverse.php
	 * @return void No value is returned.
	 */
	public function reverse (): void {}

	/**
	 * Returns a reversed copy
	 * @link http://www.php.net/manual/en/ds-vector.reversed.php
	 * @return \Ds\Vector A reversed copy of the vector.
	 * <p>
	 * The current instance is not affected.
	 * </p>
	 * <p>The current instance is not affected.</p>
	 */
	public function reversed (): \Ds\Vector {}

	/**
	 * Rotates the vector by a given number of rotations
	 * @link http://www.php.net/manual/en/ds-vector.rotate.php
	 * @param int $rotations The number of times the vector should be rotated.
	 * @return void No value is returned.. The vector of the current instance will be rotated.
	 */
	public function rotate (int $rotations): void {}

	/**
	 * Updates a value at a given index
	 * @link http://www.php.net/manual/en/ds-vector.set.php
	 * @param int $index The index of the value to update.
	 * @param mixed $value The new value.
	 * @return void No value is returned.
	 */
	public function set (int $index, mixed $value): void {}

	/**
	 * Removes and returns the first value
	 * @link http://www.php.net/manual/en/ds-vector.shift.php
	 * @return mixed The first value, which was removed.
	 */
	public function shift (): mixed {}

	/**
	 * Returns a sub-vector of a given range
	 * @link http://www.php.net/manual/en/ds-vector.slice.php
	 * @param int $index The index at which the sub-vector starts.
	 * <p>If positive, the vector will start at that index in the vector.
	 * If negative, the vector will start that far from the end.</p>
	 * @param int $length [optional] If a length is given and is positive, the resulting
	 * vector will have up to that many values in it.
	 * If the length results in an overflow, only
	 * values up to the end of the vector will be included.
	 * If a length is given and is negative, the vector
	 * will stop that many values from the end.
	 * If a length is not provided, the resulting vector
	 * will contain all values between the index and the
	 * end of the vector.
	 * @return \Ds\Vector A sub-vector of the given range.
	 */
	public function slice (int $index, int $length = null): \Ds\Vector {}

	/**
	 * Sorts the vector in-place
	 * @link http://www.php.net/manual/en/ds-vector.sort.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return void No value is returned.
	 */
	public function sort (callable $comparator = null): void {}

	/**
	 * Returns a sorted copy
	 * @link http://www.php.net/manual/en/ds-vector.sorted.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return \Ds\Vector Returns a sorted copy of the vector.
	 */
	public function sorted (callable $comparator = null): \Ds\Vector {}

	/**
	 * Returns the sum of all values in the vector
	 * @link http://www.php.net/manual/en/ds-vector.sum.php
	 * @return int|float The sum of all the values in the vector as either a float or int
	 * depending on the values in the vector.
	 */
	public function sum (): int|float {}

	/**
	 * Adds values to the front of the vector
	 * @link http://www.php.net/manual/en/ds-vector.unshift.php
	 * @param mixed $values [optional] The values to add to the front of the vector.
	 * <p>
	 * Multiple values will be added in the same order that they are
	 * passed.
	 * </p>
	 * <p>Multiple values will be added in the same order that they are
	 * passed.</p>
	 * @return void No value is returned.
	 */
	public function unshift (mixed $values = null): void {}

	/**
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-vector.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the vector
	 * @link http://www.php.net/manual/en/ds-vector.copy.php
	 * @return \Ds\Vector Returns a shallow copy of the vector.
	 */
	public function copy (): \Ds\Vector {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the vector is empty
	 * @link http://www.php.net/manual/en/ds-vector.isempty.php
	 * @return bool Returns true if the vector is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the vector to an array
	 * @link http://www.php.net/manual/en/ds-vector.toarray.php
	 * @return array An array containing all the values in the same order as the vector.
	 */
	public function toArray (): array {}

}

final class Deque implements \Ds\Sequence, \ArrayAccess, \IteratorAggregate, \Traversable, \Countable, \JsonSerializable, \Ds\Collection {
	const MIN_CAPACITY = 8;


	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-deque.construct.php
	 * @param mixed $values [optional] A traversable object or an array to use for the initial values.
	 * @return mixed 
	 */
	public function __construct (mixed $values = null): mixed {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * Removes all values from the deque
	 * @link http://www.php.net/manual/en/ds-deque.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the deque
	 * @link http://www.php.net/manual/en/ds-deque.copy.php
	 * @return \Ds\Deque A shallow copy of the deque.
	 */
	public function copy (): \Ds\Deque {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the deque is empty
	 * @link http://www.php.net/manual/en/ds-deque.isempty.php
	 * @return bool Returns true if the deque is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the deque to an array
	 * @link http://www.php.net/manual/en/ds-deque.toarray.php
	 * @return array An array containing all the values in the same order as the deque.
	 */
	public function toArray (): array {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-deque.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * <p>Capacity will always be rounded up to the nearest power of 2.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Updates all values by applying a callback function to each value
	 * @link http://www.php.net/manual/en/ds-deque.apply.php
	 * @param callable $callback mixed
	 * callback
	 * mixedvalue
	 * <p>A callable to apply to each value in the deque.</p>
	 * <p>The callback should return what the value should be replaced by.</p>
	 * @return void No value is returned.
	 */
	public function apply (callable $callback): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-deque.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Determines if the deque contains given values
	 * @link http://www.php.net/manual/en/ds-deque.contains.php
	 * @param mixed $values Values to check.
	 * @return bool false if any of the provided values are not in the
	 * deque, true otherwise.
	 */
	public function contains (mixed ...$values): bool {}

	/**
	 * Creates a new deque using a callable to
	 * determine which values to include
	 * @link http://www.php.net/manual/en/ds-deque.filter.php
	 * @param callable $callback [optional] bool
	 * callback
	 * mixedvalue
	 * <p>Optional callable which returns true if the value should be included, false otherwise.</p>
	 * <p>If a callback is not provided, only values which are true
	 * (see converting to boolean)
	 * will be included.</p>
	 * @return \Ds\Deque A new deque containing all the values for which
	 * either the callback returned true, or all values that
	 * convert to true if a callback was not provided.
	 */
	public function filter (callable $callback = null): \Ds\Deque {}

	/**
	 * Attempts to find a value's index
	 * @link http://www.php.net/manual/en/ds-deque.find.php
	 * @param mixed $value The value to find.
	 * @return mixed The index of the value, or false if not found.
	 * <p>Values will be compared by value and by type.</p>
	 */
	public function find (mixed $value): mixed {}

	/**
	 * Returns the first value in the deque
	 * @link http://www.php.net/manual/en/ds-deque.first.php
	 * @return mixed The first value in the deque.
	 */
	public function first (): mixed {}

	/**
	 * Returns the value at a given index
	 * @link http://www.php.net/manual/en/ds-deque.get.php
	 * @param int $index The index to access, starting at 0.
	 * @return mixed The value at the requested index.
	 */
	public function get (int $index): mixed {}

	/**
	 * Inserts values at a given index
	 * @link http://www.php.net/manual/en/ds-deque.insert.php
	 * @param int $index The index at which to insert. 0 &lt;= index &lt;= count
	 * <p>You can insert at the index equal to the number of values.</p>
	 * @param mixed $values The value or values to insert.
	 * @return void No value is returned.
	 */
	public function insert (int $index, mixed ...$values): void {}

	/**
	 * Joins all values together as a string
	 * @link http://www.php.net/manual/en/ds-deque.join.php
	 * @param string $glue [optional] An optional string to separate each value.
	 * @return string All values of the deque joined together as a string.
	 */
	public function join (string $glue = null): string {}

	/**
	 * Returns the last value
	 * @link http://www.php.net/manual/en/ds-deque.last.php
	 * @return mixed The last value in the deque.
	 */
	public function last (): mixed {}

	/**
	 * Returns the result of applying a callback to each value
	 * @link http://www.php.net/manual/en/ds-deque.map.php
	 * @param callable $callback mixed
	 * callback
	 * mixedvalue
	 * <p>A callable to apply to each value in the deque.</p>
	 * <p>The callable should return what the new value will be in the new deque.</p>
	 * @return \Ds\Deque The result of applying a callback to each value in
	 * the deque.
	 * <p>The values of the current instance won't be affected.</p>
	 */
	public function map (callable $callback): \Ds\Deque {}

	/**
	 * Returns the result of adding all given values to the deque
	 * @link http://www.php.net/manual/en/ds-deque.merge.php
	 * @param mixed $values A traversable object or an array.
	 * @return \Ds\Deque The result of adding all given values to the deque,
	 * effectively the same as adding the values to a copy, then returning that copy.
	 * <p>The current instance won't be affected.</p>
	 */
	public function merge (mixed $values): \Ds\Deque {}

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
	 * Removes and returns the last value
	 * @link http://www.php.net/manual/en/ds-deque.pop.php
	 * @return mixed The removed last value.
	 */
	public function pop (): mixed {}

	/**
	 * Adds values to the end of the deque
	 * @link http://www.php.net/manual/en/ds-deque.push.php
	 * @param mixed $values The values to add.
	 * @return void No value is returned.
	 */
	public function push (mixed ...$values): void {}

	/**
	 * Reduces the deque to a single value using a callback function
	 * @link http://www.php.net/manual/en/ds-deque.reduce.php
	 * @param callable $callback The return value of the previous callback, or initial if
	 * it's the first iteration.
	 * <p>The value of the current iteration.</p>
	 * @param mixed $initial [optional] The initial value of the carry value. Can be null.
	 * @return mixed The return value of the final callback.
	 */
	public function reduce (callable $callback, mixed $initial = null): mixed {}

	/**
	 * Removes and returns a value by index
	 * @link http://www.php.net/manual/en/ds-deque.remove.php
	 * @param int $index The index of the value to remove.
	 * @return mixed The value that was removed.
	 */
	public function remove (int $index): mixed {}

	/**
	 * Reverses the deque in-place
	 * @link http://www.php.net/manual/en/ds-deque.reverse.php
	 * @return void No value is returned.
	 */
	public function reverse (): void {}

	/**
	 * Returns a reversed copy
	 * @link http://www.php.net/manual/en/ds-deque.reversed.php
	 * @return \Ds\Deque A reversed copy of the deque.
	 * <p>
	 * The current instance is not affected.
	 * </p>
	 * <p>The current instance is not affected.</p>
	 */
	public function reversed (): \Ds\Deque {}

	/**
	 * Rotates the deque by a given number of rotations
	 * @link http://www.php.net/manual/en/ds-deque.rotate.php
	 * @param int $rotations The number of times the deque should be rotated.
	 * @return void No value is returned.. The deque of the current instance will be rotated.
	 */
	public function rotate (int $rotations): void {}

	/**
	 * Updates a value at a given index
	 * @link http://www.php.net/manual/en/ds-deque.set.php
	 * @param int $index The index of the value to update.
	 * @param mixed $value The new value.
	 * @return void No value is returned.
	 */
	public function set (int $index, mixed $value): void {}

	/**
	 * Removes and returns the first value
	 * @link http://www.php.net/manual/en/ds-deque.shift.php
	 * @return mixed The first value, which was removed.
	 */
	public function shift (): mixed {}

	/**
	 * Returns a sub-deque of a given range
	 * @link http://www.php.net/manual/en/ds-deque.slice.php
	 * @param int $index The index at which the sub-deque starts.
	 * <p>If positive, the deque will start at that index in the deque.
	 * If negative, the deque will start that far from the end.</p>
	 * @param int $length [optional] If a length is given and is positive, the resulting
	 * deque will have up to that many values in it.
	 * If the length results in an overflow, only
	 * values up to the end of the deque will be included.
	 * If a length is given and is negative, the deque
	 * will stop that many values from the end.
	 * If a length is not provided, the resulting deque
	 * will contain all values between the index and the
	 * end of the deque.
	 * @return \Ds\Deque A sub-deque of the given range.
	 */
	public function slice (int $index, int $length = null): \Ds\Deque {}

	/**
	 * Sorts the deque in-place
	 * @link http://www.php.net/manual/en/ds-deque.sort.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return void No value is returned.
	 */
	public function sort (callable $comparator = null): void {}

	/**
	 * Returns a sorted copy
	 * @link http://www.php.net/manual/en/ds-deque.sorted.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return \Ds\Deque Returns a sorted copy of the deque.
	 */
	public function sorted (callable $comparator = null): \Ds\Deque {}

	/**
	 * Returns the sum of all values in the deque
	 * @link http://www.php.net/manual/en/ds-deque.sum.php
	 * @return int|float The sum of all the values in the deque as either a float or int
	 * depending on the values in the deque.
	 */
	public function sum (): int|float {}

	/**
	 * Adds values to the front of the deque
	 * @link http://www.php.net/manual/en/ds-deque.unshift.php
	 * @param mixed $values [optional] The values to add to the front of the deque.
	 * <p>
	 * Multiple values will be added in the same order that they are
	 * passed.
	 * </p>
	 * <p>Multiple values will be added in the same order that they are
	 * passed.</p>
	 * @return void No value is returned.
	 */
	public function unshift (mixed $values = null): void {}

}

final class Stack implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {

	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-stack.construct.php
	 * @param mixed $values [optional] A traversable object or an array to use for the initial values.
	 * @return mixed 
	 */
	public function __construct (mixed $values = null): mixed {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-stack.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-stack.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Returns the value at the top of the stack
	 * @link http://www.php.net/manual/en/ds-stack.peek.php
	 * @return mixed The value at the top of the stack.
	 */
	public function peek (): mixed {}

	/**
	 * Removes and returns the value at the top of the stack
	 * @link http://www.php.net/manual/en/ds-stack.pop.php
	 * @return mixed The removed value which was at the top of the stack.
	 */
	public function pop (): mixed {}

	/**
	 * Pushes values onto the stack
	 * @link http://www.php.net/manual/en/ds-stack.push.php
	 * @param mixed $values The values to push onto the stack.
	 * @return void No value is returned.
	 */
	public function push (mixed ...$values): void {}

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
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-stack.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the stack
	 * @link http://www.php.net/manual/en/ds-stack.copy.php
	 * @return \Ds\Stack Returns a shallow copy of the stack.
	 */
	public function copy (): \Ds\Stack {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the stack is empty
	 * @link http://www.php.net/manual/en/ds-stack.isempty.php
	 * @return bool Returns true if the stack is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the stack to an array
	 * @link http://www.php.net/manual/en/ds-stack.toarray.php
	 * @return array An array containing all the values in the same order as the stack.
	 */
	public function toArray (): array {}

}

final class Queue implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-queue.construct.php
	 * @param mixed $values [optional] A traversable object or an array to use for the initial values.
	 * @return mixed 
	 */
	public function __construct (mixed $values = null): mixed {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-queue.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * <p>Capacity will always be rounded up to the nearest power of 2.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-queue.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Returns the value at the front of the queue
	 * @link http://www.php.net/manual/en/ds-queue.peek.php
	 * @return mixed The value at the front of the queue.
	 */
	public function peek (): mixed {}

	/**
	 * Removes and returns the value at the front of the queue
	 * @link http://www.php.net/manual/en/ds-queue.pop.php
	 * @return mixed The removed value which was at the front of the queue.
	 */
	public function pop (): mixed {}

	/**
	 * Pushes values into the queue
	 * @link http://www.php.net/manual/en/ds-queue.push.php
	 * @param mixed $values The values to push into the queue.
	 * @return void No value is returned.
	 */
	public function push (mixed ...$values): void {}

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
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-queue.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the queue
	 * @link http://www.php.net/manual/en/ds-queue.copy.php
	 * @return \Ds\Queue Returns a shallow copy of the queue.
	 */
	public function copy (): \Ds\Queue {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the queue is empty
	 * @link http://www.php.net/manual/en/ds-queue.isempty.php
	 * @return bool Returns true if the queue is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the queue to an array
	 * @link http://www.php.net/manual/en/ds-queue.toarray.php
	 * @return array An array containing all the values in the same order as the queue.
	 */
	public function toArray (): array {}

}

final class Map implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-map.construct.php
	 * @param mixed $values A traversable object or an array to use for the initial values.
	 * @return mixed 
	 */
	public function __construct (mixed ...$values): mixed {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-map.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * <p>Capacity will always be rounded up to the nearest power of 2.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Updates all values by applying a callback function to each value
	 * @link http://www.php.net/manual/en/ds-map.apply.php
	 * @param callable $callback mixed
	 * callback
	 * mixedkey
	 * mixedvalue
	 * <p>A callable to apply to each value in the map.</p>
	 * <p>The callback should return what the value should be replaced by.</p>
	 * @return void No value is returned.
	 */
	public function apply (callable $callback): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-map.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Creates a new map using keys that aren't in another map
	 * @link http://www.php.net/manual/en/ds-map.diff.php
	 * @param \Ds\Map $map The map containing the keys to exclude in the resulting map.
	 * @return \Ds\Map The result of removing all keys from the current instance that are
	 * present in a given map.
	 */
	public function diff (\Ds\Map $map): \Ds\Map {}

	/**
	 * Creates a new map using a callable to determine which pairs to include
	 * @link http://www.php.net/manual/en/ds-map.filter.php
	 * @param callable $callback [optional] bool
	 * callback
	 * mixedkey
	 * mixedvalue
	 * <p>Optional callable which returns true if the pair should be included, false otherwise.</p>
	 * <p>If a callback is not provided, only values which are true
	 * (see converting to boolean)
	 * will be included.</p>
	 * @return \Ds\Map A new map containing all the pairs for which
	 * either the callback returned true, or all values that
	 * convert to true if a callback was not provided.
	 */
	public function filter (callable $callback = null): \Ds\Map {}

	/**
	 * Returns the first pair in the map
	 * @link http://www.php.net/manual/en/ds-map.first.php
	 * @return \Ds\Pair The first pair in the map.
	 */
	public function first (): \Ds\Pair {}

	/**
	 * Returns the value for a given key
	 * @link http://www.php.net/manual/en/ds-map.get.php
	 * @param mixed $key The key to look up.
	 * @param mixed $default [optional] The optional default value, returned if the key could not be found.
	 * @return mixed The value mapped to the given key, or the default
	 * value if provided and the key could not be found in the map.
	 */
	public function get (mixed $key, mixed $default = null): mixed {}

	/**
	 * Determines whether the map contains a given key
	 * @link http://www.php.net/manual/en/ds-map.haskey.php
	 * @param mixed $key The key to look for.
	 * @return bool Returns true if the key could found, false otherwise.
	 */
	public function hasKey (mixed $key): bool {}

	/**
	 * Determines whether the map contains a given value
	 * @link http://www.php.net/manual/en/ds-map.hasvalue.php
	 * @param mixed $value The value to look for.
	 * @return bool Returns true if the value could found, false otherwise.
	 */
	public function hasValue (mixed $value): bool {}

	/**
	 * Creates a new map by intersecting keys with another map
	 * @link http://www.php.net/manual/en/ds-map.intersect.php
	 * @param \Ds\Map $map The other map, containing the keys to intersect with.
	 * @return \Ds\Map The key intersection of the current instance and another map.
	 */
	public function intersect (\Ds\Map $map): \Ds\Map {}

	/**
	 * Returns a set of the map's keys
	 * @link http://www.php.net/manual/en/ds-map.keys.php
	 * @return \Ds\Set A Ds\Set containing all the keys of the map.
	 */
	public function keys (): \Ds\Set {}

	/**
	 * Sorts the map in-place by key
	 * @link http://www.php.net/manual/en/ds-map.ksort.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return void No value is returned.
	 */
	public function ksort (callable $comparator = null): void {}

	/**
	 * Returns a copy, sorted by key
	 * @link http://www.php.net/manual/en/ds-map.ksorted.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return \Ds\Map Returns a copy of the map, sorted by key.
	 */
	public function ksorted (callable $comparator = null): \Ds\Map {}

	/**
	 * Returns the last pair of the map
	 * @link http://www.php.net/manual/en/ds-map.last.php
	 * @return \Ds\Pair The last pair of the map.
	 */
	public function last (): \Ds\Pair {}

	/**
	 * Returns the result of applying a callback to each value
	 * @link http://www.php.net/manual/en/ds-map.map.php
	 * @param callable $callback mixed
	 * callback
	 * mixedkey
	 * mixedvalue
	 * <p>A callable to apply to each value in the map.</p>
	 * <p>The callable should return what the key will be mapped to in the
	 * resulting map.</p>
	 * @return \Ds\Map The result of applying a callback to each value in
	 * the map.
	 * <p>The keys and values of the current instance won't be affected.</p>
	 */
	public function map (callable $callback): \Ds\Map {}

	/**
	 * Returns the result of adding all given associations
	 * @link http://www.php.net/manual/en/ds-map.merge.php
	 * @param mixed $values A traversable object or an array.
	 * @return \Ds\Map The result of associating all keys of a given traversable
	 * object or array with their corresponding values, combined with the current instance.
	 * <p>The current instance won't be affected.</p>
	 */
	public function merge (mixed $values): \Ds\Map {}

	/**
	 * Returns a sequence containing all the pairs of the map
	 * @link http://www.php.net/manual/en/ds-map.pairs.php
	 * @return \Ds\Sequence Ds\Sequence containing all the pairs of the map.
	 */
	public function pairs (): \Ds\Sequence {}

	/**
	 * Associates a key with a value
	 * @link http://www.php.net/manual/en/ds-map.put.php
	 * @param mixed $key The key to associate the value with.
	 * @param mixed $value The value to be associated with the key.
	 * @return void No value is returned.
	 */
	public function put (mixed $key, mixed $value): void {}

	/**
	 * Associates all key-value pairs of a traversable object or array
	 * @link http://www.php.net/manual/en/ds-map.putall.php
	 * @param mixed $pairs traversable object or array.
	 * @return void No value is returned.
	 */
	public function putAll (mixed $pairs): void {}

	/**
	 * Reduces the map to a single value using a callback function
	 * @link http://www.php.net/manual/en/ds-map.reduce.php
	 * @param callable $callback The return value of the previous callback, or initial if
	 * it's the first iteration.
	 * <p>The key of the current iteration.</p>
	 * <p>The value of the current iteration.</p>
	 * @param mixed $initial [optional] The initial value of the carry value. Can be null.
	 * @return mixed The return value of the final callback.
	 */
	public function reduce (callable $callback, mixed $initial = null): mixed {}

	/**
	 * Removes and returns a value by key
	 * @link http://www.php.net/manual/en/ds-map.remove.php
	 * @param mixed $key The key to remove.
	 * @param mixed $default [optional] The optional default value, returned if the key could not be found.
	 * @return mixed The value that was removed, or the default
	 * value if provided and the key could not be found in the map.
	 */
	public function remove (mixed $key, mixed $default = null): mixed {}

	/**
	 * Reverses the map in-place
	 * @link http://www.php.net/manual/en/ds-map.reverse.php
	 * @return void No value is returned.
	 */
	public function reverse (): void {}

	/**
	 * Returns a reversed copy
	 * @link http://www.php.net/manual/en/ds-map.reversed.php
	 * @return \Ds\Map A reversed copy of the map.
	 * <p>
	 * The current instance is not affected.
	 * </p>
	 * <p>The current instance is not affected.</p>
	 */
	public function reversed (): \Ds\Map {}

	/**
	 * Returns the pair at a given positional index
	 * @link http://www.php.net/manual/en/ds-map.skip.php
	 * @param int $position The zero-based positional index to return.
	 * @return \Ds\Pair Returns the Ds\Pair at the given position.
	 */
	public function skip (int $position): \Ds\Pair {}

	/**
	 * Returns a subset of the map defined by a starting index and length
	 * @link http://www.php.net/manual/en/ds-map.slice.php
	 * @param int $index The index at which the range starts.
	 * <p>If positive, the range will start at that index in the map.
	 * If negative, the range will start that far from the end.</p>
	 * @param int $length [optional] If a length is given and is positive, the resulting
	 * map will have up to that many pairs in it.
	 * If a length is given and is negative, the range
	 * will stop that many pairs from the end.
	 * If the length results in an overflow, only
	 * pairs up to the end of the map will be included.
	 * If a length is not provided, the resulting map
	 * will contain all pairs between the index and the
	 * end of the map.
	 * @return \Ds\Map A subset of the map defined by a starting index and length.
	 */
	public function slice (int $index, int $length = null): \Ds\Map {}

	/**
	 * Sorts the map in-place by value
	 * @link http://www.php.net/manual/en/ds-map.sort.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return void No value is returned.
	 */
	public function sort (callable $comparator = null): void {}

	/**
	 * Returns a copy, sorted by value
	 * @link http://www.php.net/manual/en/ds-map.sorted.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return \Ds\Map Returns a copy of the map, sorted by value.
	 */
	public function sorted (callable $comparator = null): \Ds\Map {}

	/**
	 * Returns the sum of all values in the map
	 * @link http://www.php.net/manual/en/ds-map.sum.php
	 * @return int|float The sum of all the values in the map as either a float or int
	 * depending on the values in the map.
	 */
	public function sum (): int|float {}

	/**
	 * Creates a new map using values from the current instance and another map
	 * @link http://www.php.net/manual/en/ds-map.union.php
	 * @param \Ds\Map $map The other map, to combine with the current instance.
	 * @return \Ds\Map A new map containing all the pairs of the current instance as well as another map.
	 */
	public function union (\Ds\Map $map): \Ds\Map {}

	/**
	 * Returns a sequence of the map's values
	 * @link http://www.php.net/manual/en/ds-map.values.php
	 * @return \Ds\Sequence A Ds\Sequence containing all the values of the map.
	 */
	public function values (): \Ds\Sequence {}

	/**
	 * Creates a new map using keys of either the current instance or of another map, but not of both
	 * @link http://www.php.net/manual/en/ds-map.xor.php
	 * @param \Ds\Map $map The other map.
	 * @return \Ds\Map A new map containing keys in the current instance as well as another map,
	 * but not in both.
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
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-map.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the map
	 * @link http://www.php.net/manual/en/ds-map.copy.php
	 * @return \Ds\Map Returns a shallow copy of the map.
	 */
	public function copy (): \Ds\Map {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the map is empty
	 * @link http://www.php.net/manual/en/ds-map.isempty.php
	 * @return bool Returns true if the map is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the map to an array
	 * @link http://www.php.net/manual/en/ds-map.toarray.php
	 * @return array An array containing all the values in the same order as the map.
	 */
	public function toArray (): array {}

}

final class Set implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate, \ArrayAccess {
	const MIN_CAPACITY = 8;


	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-set.construct.php
	 * @param mixed $values [optional] A traversable object or an array to use for the initial values.
	 * @return mixed 
	 */
	public function __construct (mixed $values = '[]'): mixed {}

	/**
	 * Adds values to the set
	 * @link http://www.php.net/manual/en/ds-set.add.php
	 * @param mixed $values Values to add to the set.
	 * @return void No value is returned.
	 */
	public function add (mixed ...$values): void {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-set.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * <p>Capacity will always be rounded up to the nearest power of 2.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-set.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Determines if the set contains all values
	 * @link http://www.php.net/manual/en/ds-set.contains.php
	 * @param mixed $values Values to check.
	 * @return bool false if any of the provided values are not in the
	 * set, true otherwise.
	 */
	public function contains (mixed ...$values): bool {}

	/**
	 * Creates a new set using values that aren't in another set
	 * @link http://www.php.net/manual/en/ds-set.diff.php
	 * @param \Ds\Set $set Set containing the values to exclude.
	 * @return \Ds\Set A new set containing all values that were not in the other set.
	 */
	public function diff (\Ds\Set $set): \Ds\Set {}

	/**
	 * Creates a new set using a callable to
	 * determine which values to include
	 * @link http://www.php.net/manual/en/ds-set.filter.php
	 * @param callable $callback [optional] bool
	 * callback
	 * mixedvalue
	 * <p>Optional callable which returns true if the value should be included, false otherwise.</p>
	 * <p>If a callback is not provided, only values which are true
	 * (see converting to boolean)
	 * will be included.</p>
	 * @return \Ds\Set A new set containing all the values for which
	 * either the callback returned true, or all values that
	 * convert to true if a callback was not provided.
	 */
	public function filter (callable $callback = null): \Ds\Set {}

	/**
	 * Returns the first value in the set
	 * @link http://www.php.net/manual/en/ds-set.first.php
	 * @return mixed The first value in the set.
	 */
	public function first (): mixed {}

	/**
	 * Returns the value at a given index
	 * @link http://www.php.net/manual/en/ds-set.get.php
	 * @param int $index The index to access, starting at 0.
	 * @return mixed The value at the requested index.
	 */
	public function get (int $index): mixed {}

	/**
	 * Creates a new set by intersecting values with another set
	 * @link http://www.php.net/manual/en/ds-set.intersect.php
	 * @param \Ds\Set $set The other set.
	 * @return \Ds\Set The intersection of the current instance and another set.
	 */
	public function intersect (\Ds\Set $set): \Ds\Set {}

	/**
	 * Joins all values together as a string
	 * @link http://www.php.net/manual/en/ds-set.join.php
	 * @param string $glue [optional] An optional string to separate each value.
	 * @return string All values of the set joined together as a string.
	 */
	public function join (string $glue = null): string {}

	/**
	 * Returns the last value in the set
	 * @link http://www.php.net/manual/en/ds-set.last.php
	 * @return mixed The last value in the set.
	 */
	public function last (): mixed {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function map (callable $callback): \Ds\Set {}

	/**
	 * Returns the result of adding all given values to the set
	 * @link http://www.php.net/manual/en/ds-set.merge.php
	 * @param mixed $values A traversable object or an array.
	 * @return \Ds\Set The result of adding all given values to the set,
	 * effectively the same as adding the values to a copy, then returning that copy.
	 * <p>The current instance won't be affected.</p>
	 */
	public function merge (mixed $values): \Ds\Set {}

	/**
	 * Reduces the set to a single value using a callback function
	 * @link http://www.php.net/manual/en/ds-set.reduce.php
	 * @param callable $callback The return value of the previous callback, or initial if
	 * it's the first iteration.
	 * <p>The value of the current iteration.</p>
	 * @param mixed $initial [optional] The initial value of the carry value. Can be null.
	 * @return mixed The return value of the final callback.
	 */
	public function reduce (callable $callback, mixed $initial = null): mixed {}

	/**
	 * Removes all given values from the set
	 * @link http://www.php.net/manual/en/ds-set.remove.php
	 * @param mixed $values The values to remove.
	 * @return void No value is returned.
	 */
	public function remove (mixed ...$values): void {}

	/**
	 * Reverses the set in-place
	 * @link http://www.php.net/manual/en/ds-set.reverse.php
	 * @return void No value is returned.
	 */
	public function reverse (): void {}

	/**
	 * Returns a reversed copy
	 * @link http://www.php.net/manual/en/ds-set.reversed.php
	 * @return \Ds\Set A reversed copy of the set.
	 * <p>
	 * The current instance is not affected.
	 * </p>
	 * <p>The current instance is not affected.</p>
	 */
	public function reversed (): \Ds\Set {}

	/**
	 * Returns a sub-set of a given range
	 * @link http://www.php.net/manual/en/ds-set.slice.php
	 * @param int $index The index at which the sub-set starts.
	 * <p>If positive, the set will start at that index in the set.
	 * If negative, the set will start that far from the end.</p>
	 * @param int $length [optional] If a length is given and is positive, the resulting
	 * set will have up to that many values in it.
	 * If the length results in an overflow, only
	 * values up to the end of the set will be included.
	 * If a length is given and is negative, the set
	 * will stop that many values from the end.
	 * If a length is not provided, the resulting set
	 * will contain all values between the index and the
	 * end of the set.
	 * @return \Ds\Set A sub-set of the given range.
	 */
	public function slice (int $index, int $length = null): \Ds\Set {}

	/**
	 * Sorts the set in-place
	 * @link http://www.php.net/manual/en/ds-set.sort.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return void No value is returned.
	 */
	public function sort (callable $comparator = null): void {}

	/**
	 * Returns a sorted copy
	 * @link http://www.php.net/manual/en/ds-set.sorted.php
	 * @param callable $comparator [optional] >
	 * The comparison function must return an integer less than, equal to, or greater than zero if the first argument is considered to be respectively less than, equal to, or greater than the second.
	 * <p>Returning non-integer values from the comparison
	 * function, such as float, will result in an internal cast to
	 * int of the callback's return value. So values such as
	 * 0.99 and 0.1 will both be cast to an
	 * integer value of 0, which will compare such values as equal.</p>
	 * @return \Ds\Set Returns a sorted copy of the set.
	 */
	public function sorted (callable $comparator = null): \Ds\Set {}

	/**
	 * Returns the sum of all values in the set
	 * @link http://www.php.net/manual/en/ds-set.sum.php
	 * @return int|float The sum of all the values in the set as either a float or int
	 * depending on the values in the set.
	 */
	public function sum (): int|float {}

	/**
	 * Creates a new set using values from the current instance and another set
	 * @link http://www.php.net/manual/en/ds-set.union.php
	 * @param \Ds\Set $set The other set, to combine with the current instance.
	 * @return \Ds\Set A new set containing all the values of the current instance as well as another set.
	 */
	public function union (\Ds\Set $set): \Ds\Set {}

	/**
	 * Creates a new set using values in either the current instance or in another set, but not in both
	 * @link http://www.php.net/manual/en/ds-set.xor.php
	 * @param \Ds\Set $set The other set.
	 * @return \Ds\Set A new set containing values in the current instance as well as another set,
	 * but not in both.
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
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-set.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the set
	 * @link http://www.php.net/manual/en/ds-set.copy.php
	 * @return \Ds\Set Returns a shallow copy of the set.
	 */
	public function copy (): \Ds\Set {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the set is empty
	 * @link http://www.php.net/manual/en/ds-set.isempty.php
	 * @return bool Returns true if the set is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the set to an array
	 * @link http://www.php.net/manual/en/ds-set.toarray.php
	 * @return array An array containing all the values in the same order as the set.
	 */
	public function toArray (): array {}

}

final class PriorityQueue implements \Ds\Collection, \JsonSerializable, \Countable, \Traversable, \IteratorAggregate {
	const MIN_CAPACITY = 8;


	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-priorityqueue.construct.php
	 */
	public function __construct () {}

	/**
	 * Allocates enough memory for a required capacity
	 * @link http://www.php.net/manual/en/ds-priorityqueue.allocate.php
	 * @param int $capacity The number of values for which capacity should be allocated.
	 * <p>Capacity will stay the same if this value is less than or equal to the
	 * current capacity.</p>
	 * <p>Capacity will always be rounded up to the nearest power of 2.</p>
	 * @return void No value is returned.
	 */
	public function allocate (int $capacity): void {}

	/**
	 * Returns the current capacity
	 * @link http://www.php.net/manual/en/ds-priorityqueue.capacity.php
	 * @return int The current capacity.
	 */
	public function capacity (): int {}

	/**
	 * Returns the value at the front of the queue
	 * @link http://www.php.net/manual/en/ds-priorityqueue.peek.php
	 * @return mixed The value at the front of the queue.
	 */
	public function peek (): mixed {}

	/**
	 * Removes and returns the value with the highest priority
	 * @link http://www.php.net/manual/en/ds-priorityqueue.pop.php
	 * @return mixed The removed value which was at the front of the queue.
	 */
	public function pop (): mixed {}

	/**
	 * Pushes values into the queue
	 * @link http://www.php.net/manual/en/ds-priorityqueue.push.php
	 * @param mixed $value The value to push into the queue.
	 * @param int $priority The priority associated with the value.
	 * @return void No value is returned.
	 */
	public function push (mixed $value, int $priority): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getIterator (): \Traversable {}

	/**
	 * Removes all values
	 * @link http://www.php.net/manual/en/ds-priorityqueue.clear.php
	 * @return void No value is returned.
	 */
	public function clear (): void {}

	/**
	 * Returns a shallow copy of the queue
	 * @link http://www.php.net/manual/en/ds-priorityqueue.copy.php
	 * @return \Ds\PriorityQueue Returns a shallow copy of the queue.
	 */
	public function copy (): \Ds\PriorityQueue {}

	/**
	 * {@inheritdoc}
	 */
	public function count (): int {}

	/**
	 * Returns whether the queue is empty
	 * @link http://www.php.net/manual/en/ds-priorityqueue.isempty.php
	 * @return bool Returns true if the queue is empty, false otherwise.
	 */
	public function isEmpty (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the queue to an array
	 * @link http://www.php.net/manual/en/ds-priorityqueue.toarray.php
	 * @return array An array containing all the values in the same order as the queue.
	 */
	public function toArray (): array {}

}

final class Pair implements \JsonSerializable {

	public $key;

	public $value;

	/**
	 * Creates a new instance
	 * @link http://www.php.net/manual/en/ds-pair.construct.php
	 * @param mixed $key [optional] The key.
	 * @param mixed $value [optional] The value.
	 * @return mixed 
	 */
	public function __construct (mixed $key = null, mixed $value = null): mixed {}

	/**
	 * Returns a shallow copy of the pair
	 * @link http://www.php.net/manual/en/ds-pair.copy.php
	 * @return \Ds\Pair Returns a shallow copy of the pair.
	 */
	public function copy (): \Ds\Pair {}

	/**
	 * {@inheritdoc}
	 */
	public function jsonSerialize () {}

	/**
	 * Converts the pair to an array
	 * @link http://www.php.net/manual/en/ds-pair.toarray.php
	 * @return array An array containing all the values in the same order as the pair.
	 */
	public function toArray (): array {}

}

}

// End of ds v.1.4.0
