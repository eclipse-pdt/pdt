<?php

// Start of msgpack v.2.2.0RC1

class MessagePack  {
	const OPT_PHPONLY = -1001;


	/**
	 * @param mixed $opt [optional]
	 */
	public function __construct ($opt = null) {}

	/**
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setOption ($option = null, $value = null) {}

	/**
	 * @param mixed $value
	 */
	public function pack ($value = null) {}

	/**
	 * @param mixed $str
	 * @param mixed $object [optional]
	 */
	public function unpack ($str = null, $object = null) {}

	public function unpacker () {}

}

class MessagePackUnpacker  {

	/**
	 * @param mixed $opt [optional]
	 */
	public function __construct ($opt = null) {}

	public function __destruct () {}

	/**
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setOption ($option = null, $value = null) {}

	/**
	 * @param mixed $str
	 */
	public function feed ($str = null) {}

	/**
	 * @param mixed $str [optional]
	 * @param mixed $offset [optional]
	 */
	public function execute ($str = null, &$offset = null) {}

	/**
	 * @param mixed $object [optional]
	 */
	public function data ($object = null) {}

	public function reset () {}

}

/**
 * @param mixed $value
 */
function msgpack_serialize ($value = null) {}

/**
 * @param mixed $str
 * @param mixed $object [optional]
 */
function msgpack_unserialize ($str = null, $object = null) {}

/**
 * @param mixed $value
 */
function msgpack_pack ($value = null) {}

/**
 * @param mixed $str
 * @param mixed $object [optional]
 */
function msgpack_unpack ($str = null, $object = null) {}

define ('MESSAGEPACK_OPT_PHPONLY', -1001);

// End of msgpack v.2.2.0RC1
