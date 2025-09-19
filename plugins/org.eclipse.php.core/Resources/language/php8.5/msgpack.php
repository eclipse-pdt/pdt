<?php

// Start of msgpack v.2.2.0RC1

class MessagePack  {
	const OPT_PHPONLY = -1001;


	/**
	 * {@inheritdoc}
	 * @param mixed $opt [optional]
	 */
	public function __construct ($opt = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setOption ($option = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $value
	 */
	public function pack ($value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str
	 * @param mixed $object [optional]
	 */
	public function unpack ($str = null, $object = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function unpacker () {}

}

class MessagePackUnpacker  {

	/**
	 * {@inheritdoc}
	 * @param mixed $opt [optional]
	 */
	public function __construct ($opt = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $option
	 * @param mixed $value
	 */
	public function setOption ($option = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str
	 */
	public function feed ($str = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $str [optional]
	 * @param mixed $offset [optional]
	 */
	public function execute ($str = NULL, &$offset = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $object [optional]
	 */
	public function data ($object = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function reset () {}

}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function msgpack_serialize ($value = null) {}

/**
 * {@inheritdoc}
 * @param mixed $str
 * @param mixed $object [optional]
 */
function msgpack_unserialize ($str = null, $object = NULL) {}

/**
 * {@inheritdoc}
 * @param mixed $value
 */
function msgpack_pack ($value = null) {}

/**
 * {@inheritdoc}
 * @param mixed $str
 * @param mixed $object [optional]
 */
function msgpack_unpack ($str = null, $object = NULL) {}

define ('MESSAGEPACK_OPT_PHPONLY', -1001);

// End of msgpack v.2.2.0RC1
