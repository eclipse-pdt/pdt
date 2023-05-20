<?php

// Start of solr v.2.6.0

/**
 * This is an object whose properties can also by accessed using the array syntax. All its properties are read-only.
 * @link http://www.php.net/manual/en/class.solrobject.php
 */
final class SolrObject implements ArrayAccess {

	/**
	 * Creates Solr object
	 * @link http://www.php.net/manual/en/solrobject.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrobject.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Sets the value for a property
	 * @link http://www.php.net/manual/en/solrobject.offsetset.php
	 * @param string $property_name The name of the property.
	 * @param string $property_value The new value.
	 * @return void None.
	 */
	public function offsetSet (string $property_name, string $property_value): void {}

	/**
	 * Used to retrieve a property
	 * @link http://www.php.net/manual/en/solrobject.offsetget.php
	 * @param string $property_name Name of the property.
	 * @return mixed the property value.
	 */
	public function offsetGet (string $property_name) {}

	/**
	 * Checks if the property exists
	 * @link http://www.php.net/manual/en/solrobject.offsetexists.php
	 * @param string $property_name The name of the property.
	 * @return bool true on success or false on failure
	 */
	public function offsetExists (string $property_name): bool {}

	/**
	 * Unsets the value for the property
	 * @link http://www.php.net/manual/en/solrobject.offsetunset.php
	 * @param string $property_name The name of the property.
	 * @return void true on success or false on failure
	 */
	public function offsetUnset (string $property_name): void {}

	/**
	 * Returns an array of all the names of the properties
	 * @link http://www.php.net/manual/en/solrobject.getpropertynames.php
	 * @return array an array.
	 */
	public function getPropertyNames () {}

}

/**
 * Represents a Solr document retrieved from a query response.
 * @link http://www.php.net/manual/en/class.solrdocument.php
 */
final class SolrDocument implements ArrayAccess, Iterator, Traversable, Serializable {
	const SORT_DEFAULT = 1;
	const SORT_ASC = 1;
	const SORT_DESC = 2;
	const SORT_FIELD_NAME = 1;
	const SORT_FIELD_VALUE_COUNT = 2;
	const SORT_FIELD_BOOST_VALUE = 4;

	private $_hashtable_index;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrdocument.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrdocument.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Creates a copy of a SolrDocument object
	 * @link http://www.php.net/manual/en/solrdocument.clone.php
	 * @return void None.
	 */
	public function __clone () {}

	/**
	 * Adds another field to the document
	 * @link http://www.php.net/manual/en/solrdocument.set.php
	 * @param string $fieldName Name of the field.
	 * @param string $fieldValue Field value.
	 * @return bool true on success or false on failure
	 */
	public function __set (string $fieldName, string $fieldValue) {}

	/**
	 * Access the field as a property
	 * @link http://www.php.net/manual/en/solrdocument.get.php
	 * @param string $fieldName The name of the field.
	 * @return SolrDocumentField a SolrDocumentField instance.
	 */
	public function __get (string $fieldName) {}

	/**
	 * Checks if a field exists
	 * @link http://www.php.net/manual/en/solrdocument.isset.php
	 * @param string $fieldName Name of the field.
	 * @return bool true on success or false on failure
	 */
	public function __isset (string $fieldName) {}

	/**
	 * Removes a field from the document
	 * @link http://www.php.net/manual/en/solrdocument.unset.php
	 * @param string $fieldName The name of the field.
	 * @return bool true on success or false on failure
	 */
	public function __unset (string $fieldName) {}

	/**
	 * Adds a field to the document
	 * @link http://www.php.net/manual/en/solrdocument.offsetset.php
	 * @param string $fieldName The name of the field.
	 * @param string $fieldValue The value for this field.
	 * @return void true on success or false on failure
	 */
	public function offsetSet (string $fieldName, string $fieldValue): void {}

	/**
	 * Retrieves a field
	 * @link http://www.php.net/manual/en/solrdocument.offsetget.php
	 * @param string $fieldName The name of the field.
	 * @return SolrDocumentField a SolrDocumentField object.
	 */
	public function offsetGet (string $fieldName) {}

	/**
	 * Checks if a particular field exists
	 * @link http://www.php.net/manual/en/solrdocument.offsetexists.php
	 * @param string $fieldName The name of the field.
	 * @return bool true on success or false on failure
	 */
	public function offsetExists (string $fieldName): bool {}

	/**
	 * Removes a field
	 * @link http://www.php.net/manual/en/solrdocument.offsetunset.php
	 * @param string $fieldName The name of the field.
	 * @return void No return value.
	 */
	public function offsetUnset (string $fieldName): void {}

	/**
	 * Retrieves the current field
	 * @link http://www.php.net/manual/en/solrdocument.current.php
	 * @return SolrDocumentField the field
	 */
	public function current () {}

	/**
	 * Retrieves the current key
	 * @link http://www.php.net/manual/en/solrdocument.key.php
	 * @return string the current key.
	 */
	public function key () {}

	/**
	 * Moves the internal pointer to the next field
	 * @link http://www.php.net/manual/en/solrdocument.next.php
	 * @return void This method has no return value.
	 */
	public function next (): void {}

	/**
	 * Resets the internal pointer to the beginning
	 * @link http://www.php.net/manual/en/solrdocument.rewind.php
	 * @return void This method has no return value.
	 */
	public function rewind (): void {}

	/**
	 * Checks if the current position internally is still valid
	 * @link http://www.php.net/manual/en/solrdocument.valid.php
	 * @return bool true on success and false if the current position is no longer valid.
	 */
	public function valid (): bool {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrdocument.serialize.php
	 * @return string a string representing the serialized Solr document.
	 */
	public function serialize () {}

	/**
	 * Custom serialization of SolrDocument objects
	 * @link http://www.php.net/manual/en/solrdocument.unserialize.php
	 * @param string $serialized An XML representation of the document.
	 * @return void None.
	 */
	public function unserialize (string $serialized) {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Drops all the fields in the document
	 * @link http://www.php.net/manual/en/solrdocument.clear.php
	 * @return bool true on success or false on failure
	 */
	public function clear () {}

	/**
	 * Alias: SolrDocument::clear
	 * @link http://www.php.net/manual/en/solrdocument.reset.php
	 * @return bool true on success or false on failure
	 */
	public function reset () {}

	/**
	 * Adds a field to the document
	 * @link http://www.php.net/manual/en/solrdocument.addfield.php
	 * @param string $fieldName The name of the field
	 * @param string $fieldValue The value of the field.
	 * @return bool true on success or false on failure
	 */
	public function addField (string $fieldName, string $fieldValue) {}

	/**
	 * Returns an array of fields names in the document
	 * @link http://www.php.net/manual/en/solrdocument.getfieldnames.php
	 * @return array an array containing the names of the fields in this document.
	 */
	public function getFieldNames () {}

	/**
	 * Returns the number of fields in this document
	 * @link http://www.php.net/manual/en/solrdocument.getfieldcount.php
	 * @return int an integer on success and false on failure.
	 */
	public function getFieldCount () {}

	/**
	 * Retrieves a field by name
	 * @link http://www.php.net/manual/en/solrdocument.getfield.php
	 * @param string $fieldName Name of the field.
	 * @return SolrDocumentField a SolrDocumentField on success and false on failure.
	 */
	public function getField (string $fieldName) {}

	/**
	 * Returns an array representation of the document
	 * @link http://www.php.net/manual/en/solrdocument.toarray.php
	 * @return array an array representation of the document.
	 */
	public function toArray () {}

	/**
	 * Checks if a field exists in the document
	 * @link http://www.php.net/manual/en/solrdocument.fieldexists.php
	 * @param string $fieldName The name of the field.
	 * @return bool true if the field is present and false if it does not.
	 */
	public function fieldExists (string $fieldName) {}

	/**
	 * Removes a field from the document
	 * @link http://www.php.net/manual/en/solrdocument.deletefield.php
	 * @param string $fieldName Name of the field
	 * @return bool true on success or false on failure
	 */
	public function deleteField (string $fieldName) {}

	/**
	 * Sorts the fields in the document
	 * @link http://www.php.net/manual/en/solrdocument.sort.php
	 * @param int $sortOrderBy The sort criteria.
	 * @param int $sortDirection [optional] The sort direction.
	 * @return bool true on success or false on failure
	 */
	public function sort (int $sortOrderBy, int $sortDirection = null) {}

	/**
	 * Merges source to the current SolrDocument
	 * @link http://www.php.net/manual/en/solrdocument.merge.php
	 * @param SolrDocument $sourceDoc The source document.
	 * @param bool $overwrite [optional] If this is true then fields with the same name in the destination document will be overwritten.
	 * @return bool true on success or false on failure
	 */
	public function merge (SolrDocument $sourceDoc, bool $overwrite = null) {}

	/**
	 * Returns a SolrInputDocument equivalent of the object
	 * @link http://www.php.net/manual/en/solrdocument.getinputdocument.php
	 * @return SolrInputDocument a SolrInputDocument on success and null on failure.
	 */
	public function getInputDocument () {}

	/**
	 * Returns the number of child documents
	 * @link http://www.php.net/manual/en/solrdocument.getchilddocumentscount.php
	 * @return int 
	 */
	public function getChildDocumentsCount () {}

	/**
	 * Checks whether the document has any child documents
	 * @link http://www.php.net/manual/en/solrdocument.haschilddocuments.php
	 * @return bool 
	 */
	public function hasChildDocuments () {}

	/**
	 * Returns an array of child documents (SolrDocument)
	 * @link http://www.php.net/manual/en/solrdocument.getchilddocuments.php
	 * @return array 
	 */
	public function getChildDocuments () {}

}

/**
 * This represents a field in a Solr document. All its properties are read-only.
 * @link http://www.php.net/manual/en/class.solrdocumentfield.php
 */
final class SolrDocumentField  {
	public $name;
	public $boost;
	public $values;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrdocumentfield.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrdocumentfield.destruct.php
	 */
	public function __destruct () {}

}

/**
 * This class represents a Solr document that is about to be submitted to the Solr index.
 * @link http://www.php.net/manual/en/class.solrinputdocument.php
 */
final class SolrInputDocument  {
	const SORT_DEFAULT = 1;
	const SORT_ASC = 1;
	const SORT_DESC = 2;
	const SORT_FIELD_NAME = 1;
	const SORT_FIELD_VALUE_COUNT = 2;
	const SORT_FIELD_BOOST_VALUE = 4;
	const UPDATE_MODIFIER_ADD = 1;
	const UPDATE_MODIFIER_SET = 2;
	const UPDATE_MODIFIER_INC = 3;
	const UPDATE_MODIFIER_REMOVE = 4;
	const UPDATE_MODIFIER_REMOVEREGEX = 5;
	const VERSION_ASSERT_NONE = 0;
	const VERSION_ASSERT_EXISTS = 1;
	const VERSION_ASSERT_NOT_EXISTS = -1;

	private $_hashtable_index;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrinputdocument.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrinputdocument.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Creates a copy of a SolrDocument
	 * @link http://www.php.net/manual/en/solrinputdocument.clone.php
	 * @return void Creates a new SolrInputDocument instance.
	 */
	public function __clone () {}

	public function __sleep () {}

	public function __wakeup () {}

	/**
	 * Sets the boost value for this document
	 * @link http://www.php.net/manual/en/solrinputdocument.setboost.php
	 * @param float $documentBoostValue The index-time boost value for this document.
	 * @return bool true on success or false on failure
	 */
	public function setBoost (float $documentBoostValue) {}

	/**
	 * Retrieves the current boost value for the document
	 * @link http://www.php.net/manual/en/solrinputdocument.getboost.php
	 * @return float the boost value on success and false on failure.
	 */
	public function getBoost () {}

	/**
	 * Resets the input document
	 * @link http://www.php.net/manual/en/solrinputdocument.clear.php
	 * @return bool true on success or false on failure
	 */
	public function clear () {}

	/**
	 * Alias: SolrInputDocument::clear
	 * @link http://www.php.net/manual/en/solrinputdocument.reset.php
	 * @return bool true on success or false on failure
	 */
	public function reset () {}

	/**
	 * Adds a field to the document
	 * @link http://www.php.net/manual/en/solrinputdocument.addfield.php
	 * @param string $fieldName The name of the field
	 * @param string $fieldValue The value for the field.
	 * @param float $fieldBoostValue [optional] The index time boost for the field. Though this cannot be negative, you can still pass values less than 1.0 but they must be greater than zero.
	 * @return bool true on success or false on failure
	 */
	public function addField (string $fieldName, string $fieldValue, float $fieldBoostValue = null) {}

	/**
	 * @param mixed $fieldName
	 * @param mixed $modifier
	 * @param mixed $value
	 */
	public function updateField ($fieldName = null, $modifier = null, $value = null) {}

	/**
	 * Retrieves the boost value for a particular field
	 * @link http://www.php.net/manual/en/solrinputdocument.getfieldboost.php
	 * @param string $fieldName The name of the field.
	 * @return float the boost value for the field or false if there was an error.
	 */
	public function getFieldBoost (string $fieldName) {}

	/**
	 * Sets the index-time boost value for a field
	 * @link http://www.php.net/manual/en/solrinputdocument.setfieldboost.php
	 * @param string $fieldName The name of the field.
	 * @param float $fieldBoostValue The index time boost value.
	 * @return bool true on success or false on failure
	 */
	public function setFieldBoost (string $fieldName, float $fieldBoostValue) {}

	/**
	 * Returns an array containing all the fields in the document
	 * @link http://www.php.net/manual/en/solrinputdocument.getfieldnames.php
	 * @return array an array on success and false on failure.
	 */
	public function getFieldNames () {}

	/**
	 * Returns the number of fields in the document
	 * @link http://www.php.net/manual/en/solrinputdocument.getfieldcount.php
	 * @return mixed an integer on success or false on failure.
	 */
	public function getFieldCount () {}

	/**
	 * Retrieves a field by name
	 * @link http://www.php.net/manual/en/solrinputdocument.getfield.php
	 * @param string $fieldName The name of the field.
	 * @return SolrDocumentField a SolrDocumentField object on success and false on failure.
	 */
	public function getField (string $fieldName) {}

	/**
	 * Returns an array representation of the input document
	 * @link http://www.php.net/manual/en/solrinputdocument.toarray.php
	 * @return array an array containing the fields. It returns false on failure.
	 */
	public function toArray () {}

	/**
	 * Checks if a field exists
	 * @link http://www.php.net/manual/en/solrinputdocument.fieldexists.php
	 * @param string $fieldName Name of the field.
	 * @return bool true if the field was found and false if it was not found.
	 */
	public function fieldExists (string $fieldName) {}

	/**
	 * Removes a field from the document
	 * @link http://www.php.net/manual/en/solrinputdocument.deletefield.php
	 * @param string $fieldName The name of the field.
	 * @return bool true on success or false on failure
	 */
	public function deleteField (string $fieldName) {}

	/**
	 * Sorts the fields within the document
	 * @link http://www.php.net/manual/en/solrinputdocument.sort.php
	 * @param int $sortOrderBy The sort criteria
	 * @param int $sortDirection [optional] The sort direction
	 * @return bool true on success or false on failure
	 */
	public function sort (int $sortOrderBy, int $sortDirection = null) {}

	/**
	 * Merges one input document into another
	 * @link http://www.php.net/manual/en/solrinputdocument.merge.php
	 * @param SolrInputDocument $sourceDoc The source document.
	 * @param bool $overwrite [optional] If this is true it will replace matching fields in the destination document.
	 * @return bool true on success or false on failure In the future, this will be modified to return the number of fields in the new document.
	 */
	public function merge (SolrInputDocument $sourceDoc, bool $overwrite = null) {}

	/**
	 * Adds a child document for block indexing
	 * @link http://www.php.net/manual/en/solrinputdocument.addchilddocument.php
	 * @param SolrInputDocument $child A SolrInputDocument object.
	 * @return void 
	 */
	public function addChildDocument (SolrInputDocument $child) {}

	/**
	 * Returns an array of child documents (SolrInputDocument)
	 * @link http://www.php.net/manual/en/solrinputdocument.getchilddocuments.php
	 * @return array 
	 */
	public function getChildDocuments () {}

	/**
	 * Returns true if the document has any child documents
	 * @link http://www.php.net/manual/en/solrinputdocument.haschilddocuments.php
	 * @return bool 
	 */
	public function hasChildDocuments () {}

	/**
	 * Returns the number of child documents
	 * @link http://www.php.net/manual/en/solrinputdocument.getchilddocumentscount.php
	 * @return int 
	 */
	public function getChildDocumentsCount () {}

	/**
	 * Adds an array of child documents
	 * @link http://www.php.net/manual/en/solrinputdocument.addchilddocuments.php
	 * @param array $docs An array of SolrInputDocument objects.
	 * @return void 
	 */
	public function addChildDocuments (array &$docs) {}

	/**
	 * @param mixed $version
	 */
	public function setVersion ($version = null) {}

	public function getVersion () {}

}

/**
 * Used to send requests to a Solr server. Currently, cloning and serialization of SolrClient instances is not supported.
 * @link http://www.php.net/manual/en/class.solrclient.php
 */
class SolrClient  {
	const SEARCH_SERVLET_TYPE = 1;
	const UPDATE_SERVLET_TYPE = 2;
	const THREADS_SERVLET_TYPE = 4;
	const PING_SERVLET_TYPE = 8;
	const TERMS_SERVLET_TYPE = 16;
	const SYSTEM_SERVLET_TYPE = 32;
	const DEFAULT_SEARCH_SERVLET = "select";
	const DEFAULT_UPDATE_SERVLET = "update";
	const DEFAULT_THREADS_SERVLET = "admin/threads";
	const DEFAULT_PING_SERVLET = "admin/ping";
	const DEFAULT_TERMS_SERVLET = "terms";
	const DEFAULT_SYSTEM_SERVLET = "admin/system";

	private $_hashtable_index;


	/**
	 * Constructor for the SolrClient object
	 * @link http://www.php.net/manual/en/solrclient.construct.php
	 * @param array[] $clientOptions
	 */
	public function __construct (array $clientOptions) {}

	/**
	 * Destructor for SolrClient
	 * @link http://www.php.net/manual/en/solrclient.destruct.php
	 */
	public function __destruct () {}

	public function __sleep () {}

	public function __wakeup () {}

	public function __clone () {}

	/**
	 * Returns the client options set internally
	 * @link http://www.php.net/manual/en/solrclient.getoptions.php
	 * @return array an array containing all the options for the SolrClient object set internally.
	 */
	public function getOptions () {}

	/**
	 * Returns the debug data for the last connection attempt
	 * @link http://www.php.net/manual/en/solrclient.getdebug.php
	 * @return string a string on success and null if there is nothing to return.
	 */
	public function getDebug () {}

	/**
	 * Changes the specified servlet type to a new value
	 * @link http://www.php.net/manual/en/solrclient.setservlet.php
	 * @param int $type <p>One of the following :</p>
	 * <p>
	 * <pre>
	 * - SolrClient::SEARCH_SERVLET_TYPE
	 * - SolrClient::UPDATE_SERVLET_TYPE
	 * - SolrClient::THREADS_SERVLET_TYPE
	 * - SolrClient::PING_SERVLET_TYPE
	 * - SolrClient::TERMS_SERVLET_TYPE
	 * </pre>
	 * </p>
	 * @param string $value The new value for the servlet
	 * @return bool true on success or false on failure
	 */
	public function setServlet (int $type, string $value) {}

	/**
	 * Sends a query to the server
	 * @link http://www.php.net/manual/en/solrclient.query.php
	 * @param SolrParams $query A SolrParams object. It is recommended to use SolrQuery for advanced queries.
	 * @return SolrQueryResponse a SolrQueryResponse object on success and throws an exception on failure.
	 */
	public function query (SolrParams $query) {}

	/**
	 * Adds a document to the index
	 * @link http://www.php.net/manual/en/solrclient.adddocument.php
	 * @param SolrInputDocument $doc The SolrInputDocument instance.
	 * @param bool $overwrite [optional] <p>
	 * Whether to overwrite existing document or not. If false there will be duplicates (several documents with the same ID).
	 * </p>
	 * <p>
	 * PECL Solr &lt; 2.0 $allowDups was used instead of $overwrite, which does the same functionality with exact opposite bool flag.
	 * </p>
	 * <p>
	 * $allowDups = false is the same as $overwrite = true
	 * </p>
	 * @param int $commitWithin [optional] <p>
	 * Number of milliseconds within which to auto commit this document. Available since Solr 1.4 . Default (0) means disabled.
	 * </p>
	 * <p>
	 * When this value specified, it leaves the control of when to do the commit
	 * to Solr itself, optimizing number of commits to a minimum while still
	 * fulfilling
	 * the update latency requirements, and Solr will automatically do a commit
	 * when the oldest add in the buffer is due.
	 * </p>
	 * @return SolrUpdateResponse a SolrUpdateResponse object or throws an Exception on failure.
	 */
	public function addDocument (SolrInputDocument $doc, bool $overwrite = null, int $commitWithin = null) {}

	/**
	 * Adds a collection of SolrInputDocument instances to the index
	 * @link http://www.php.net/manual/en/solrclient.adddocuments.php
	 * @param array $docs An array containing the collection of SolrInputDocument instances. This array must be an actual variable.
	 * @param bool $overwrite [optional] <p>
	 * Whether to overwrite existing documents or not. If false there will be duplicates (several documents with the same ID).
	 * </p>
	 * <p>
	 * PECL Solr &lt; 2.0 $allowDups was used instead of $overwrite, which does the same functionality with exact opposite bool flag.
	 * </p>
	 * <p>
	 * $allowDups = false is the same as $overwrite = true
	 * </p>
	 * @param int $commitWithin [optional] <p>
	 * Number of milliseconds within which to auto commit this document. Available since Solr 1.4 . Default (0) means disabled.
	 * </p>
	 * <p>
	 * When this value specified, it leaves the control of when to do the commit
	 * to Solr itself, optimizing number of commits to a minimum while still
	 * fulfilling
	 * the update latency requirements, and Solr will automatically do a commit
	 * when the oldest add in the buffer is due.
	 * </p>
	 * @return void a SolrUpdateResponse object or throws an exception on failure.
	 */
	public function addDocuments (array $docs, bool $overwrite = null, int $commitWithin = null) {}

	/**
	 * @param SolrExtractRequest $request
	 */
	public function sendUpdateStream (SolrExtractRequest &$request) {}

	/**
	 * Sends a raw update request
	 * @link http://www.php.net/manual/en/solrclient.request.php
	 * @param string $raw_request An XML string with the raw request to the server.
	 * @return SolrUpdateResponse a SolrUpdateResponse on success. Throws an exception on failure.
	 */
	public function request (string $raw_request) {}

	/**
	 * Sets the response writer used to prepare the response from Solr
	 * @link http://www.php.net/manual/en/solrclient.setresponsewriter.php
	 * @param string $responseWriter <p>One of the following:</p>
	 * <p>
	 * json
	 * phps
	 * xml
	 * </p>
	 * @return void 
	 */
	public function setResponseWriter (string $responseWriter) {}

	/**
	 * Delete by Id
	 * @link http://www.php.net/manual/en/solrclient.deletebyid.php
	 * @param string $id The value of the uniqueKey field declared in the schema
	 * @return SolrUpdateResponse a SolrUpdateResponse on success and throws an exception on failure.
	 */
	public function deleteById (string $id) {}

	/**
	 * Deletes by Ids
	 * @link http://www.php.net/manual/en/solrclient.deletebyids.php
	 * @param array $ids An array of IDs representing the uniqueKey field declared in the schema for each document to be deleted. This must be an actual php variable.
	 * @return SolrUpdateResponse a SolrUpdateResponse on success and throws an exception on failure.
	 */
	public function deleteByIds (array $ids) {}

	/**
	 * Deletes all documents matching the given query
	 * @link http://www.php.net/manual/en/solrclient.deletebyquery.php
	 * @param string $query The query
	 * @return SolrUpdateResponse a SolrUpdateResponse on success and throws an exception on failure.
	 */
	public function deleteByQuery (string $query) {}

	/**
	 * Removes all documents matching any of the queries
	 * @link http://www.php.net/manual/en/solrclient.deletebyqueries.php
	 * @param array $queries The array of queries. This must be an actual php variable.
	 * @return SolrUpdateResponse a SolrUpdateResponse on success and throws a SolrClientException on failure.
	 */
	public function deleteByQueries (array $queries) {}

	/**
	 * Get Document By Id. Utilizes Solr Realtime Get (RTG)
	 * @link http://www.php.net/manual/en/solrclient.getbyid.php
	 * @param string $id Document ID
	 * @return SolrQueryResponse SolrQueryResponse
	 */
	public function getById (string $id) {}

	/**
	 * Get Documents by their Ids. Utilizes Solr Realtime Get (RTG)
	 * @link http://www.php.net/manual/en/solrclient.getbyids.php
	 * @param array $ids Document ids
	 * @return SolrQueryResponse SolrQueryResponse
	 */
	public function getByIds (array $ids) {}

	/**
	 * Finalizes all add/deletes made to the index
	 * @link http://www.php.net/manual/en/solrclient.commit.php
	 * @param bool $softCommit [optional] <p>
	 * This will refresh the 'view' of the index in a more performant manner, but without "on-disk" guarantees. (Solr4.0+)
	 * </p>
	 * <p>
	 * A soft commit is much faster since it only makes index changes visible and does not fsync index files or write a new index descriptor. 
	 * If the JVM crashes or there is a loss of power, changes that occurred after the last hard commit will be lost. 
	 * Search collections that have near-real-time requirements (that want index changes to be quickly visible to searches) will want to soft commit often but hard commit less frequently. 
	 * </p>
	 * @param bool $waitSearcher [optional] block until a new searcher is opened and registered as the main query searcher, making the changes visible.
	 * @param bool $expungeDeletes [optional] Merge segments with deletes away. (Solr1.4+)
	 * @return SolrUpdateResponse a SolrUpdateResponse object on success or throws an exception on failure.
	 */
	public function commit (bool $softCommit = null, bool $waitSearcher = null, bool $expungeDeletes = null) {}

	/**
	 * Defragments the index
	 * @link http://www.php.net/manual/en/solrclient.optimize.php
	 * @param int $maxSegments [optional] Optimizes down to at most this number of segments. Since Solr 1.3
	 * @param bool $softCommit [optional] This will refresh the 'view' of the index in a more performant manner, but without "on-disk" guarantees. (Solr4.0+)
	 * @param bool $waitSearcher [optional] Block until a new searcher is opened and registered as the main query searcher, making the changes visible.
	 * @return SolrUpdateResponse a SolrUpdateResponse on success or throws an exception on failure.
	 */
	public function optimize (int $maxSegments = null, bool $softCommit = null, bool $waitSearcher = null) {}

	/**
	 * Rollbacks all add/deletes made to the index since the last commit
	 * @link http://www.php.net/manual/en/solrclient.rollback.php
	 * @return SolrUpdateResponse a SolrUpdateResponse on success or throws a SolrClientException on failure.
	 */
	public function rollback () {}

	/**
	 * Checks if Solr server is still up
	 * @link http://www.php.net/manual/en/solrclient.ping.php
	 * @return SolrPingResponse a SolrPingResponse object on success and throws an exception on failure.
	 */
	public function ping () {}

	/**
	 * Retrieve Solr Server information
	 * @link http://www.php.net/manual/en/solrclient.system.php
	 * @return void a SolrGenericResponse object on success.
	 */
	public function system () {}

	/**
	 * Checks the threads status
	 * @link http://www.php.net/manual/en/solrclient.threads.php
	 * @return void a SolrGenericResponse object.
	 */
	public function threads () {}

}

/**
 * Represents a collection of name-value pairs sent to the Solr server during a request.
 * @link http://www.php.net/manual/en/class.solrparams.php
 */
abstract class SolrParams implements Stringable, Serializable {
	protected $_hashtable_index;


	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name Name of the parameter
	 * @param string $value Value of the parameter
	 * @return SolrParams a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value) {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name Name of parameter
	 * @param string $value Value of parameter
	 * @return SolrParams a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value) {}

	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] Whether to return URL-encoded values
	 * @return string a string on success and false on failure.
	 */
	public function toString (bool $url_encode = null) {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array an array of non URL-encoded parameters
	 */
	public function getParams () {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] The name of the parameter
	 * @return mixed a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null) {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array an array on URL-encoded parameters
	 */
	public function getPreparedParams () {}

	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized The serialized representation of the object
	 * @return void None
	 */
	public function unserialize (string $serialized) {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias: SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name The name of the parameter
	 * @param string $value The value of the parameter
	 * @return SolrParams a SolrParams instance on success
	 */
	public function add (string $name, string $value) {}

	/**
	 * Alias: SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name Then name of the parameter
	 * @param string $value The parameter value
	 * @return void an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value) {}

	/**
	 * Alias: SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name Then name of the parameter
	 * @return mixed an array or string depending on the type of parameter
	 */
	public function get (string $param_name) {}

}

/**
 * Represents a collection of name-value pairs sent to the Solr server during a request.
 * @link http://www.php.net/manual/en/class.solrmodifiableparams.php
 */
class SolrModifiableParams extends SolrParams implements Serializable, Stringable {
	protected $_hashtable_index;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrmodifiableparams.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrmodifiableparams.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name Name of the parameter
	 * @param string $value Value of the parameter
	 * @return SolrParams a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value) {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name Name of parameter
	 * @param string $value Value of parameter
	 * @return SolrParams a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value) {}

	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] Whether to return URL-encoded values
	 * @return string a string on success and false on failure.
	 */
	public function toString (bool $url_encode = null) {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array an array of non URL-encoded parameters
	 */
	public function getParams () {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] The name of the parameter
	 * @return mixed a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null) {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array an array on URL-encoded parameters
	 */
	public function getPreparedParams () {}

	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized The serialized representation of the object
	 * @return void None
	 */
	public function unserialize (string $serialized) {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias: SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name The name of the parameter
	 * @param string $value The value of the parameter
	 * @return SolrParams a SolrParams instance on success
	 */
	public function add (string $name, string $value) {}

	/**
	 * Alias: SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name Then name of the parameter
	 * @param string $value The parameter value
	 * @return void an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value) {}

	/**
	 * Alias: SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name Then name of the parameter
	 * @return mixed an array or string depending on the type of parameter
	 */
	public function get (string $param_name) {}

}

/**
 * Represents a collection of name-value pairs sent to the Solr server during a request.
 * @link http://www.php.net/manual/en/class.solrquery.php
 */
class SolrQuery extends SolrModifiableParams implements Stringable, Serializable {
	const ORDER_ASC = 0;
	const ORDER_DESC = 1;
	const FACET_SORT_INDEX = 0;
	const FACET_SORT_COUNT = 1;
	const TERMS_SORT_INDEX = 0;
	const TERMS_SORT_COUNT = 1;

	protected $_hashtable_index;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrquery.construct.php
	 * @param mixed $q [optional]
	 */
	public function __construct ($q = null) {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrquery.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Sets the search query
	 * @link http://www.php.net/manual/en/solrquery.setquery.php
	 * @param string $query The search query
	 * @return SolrQuery the current SolrQuery object
	 */
	public function setQuery (string $query) {}

	/**
	 * Returns the main query
	 * @link http://www.php.net/manual/en/solrquery.getquery.php
	 * @return string a string on success and null if not set.
	 */
	public function getQuery () {}

	/**
	 * Specifies the number of rows to skip
	 * @link http://www.php.net/manual/en/solrquery.setstart.php
	 * @param int $start The number of rows to skip.
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function setStart (int $start) {}

	/**
	 * Returns the offset in the complete result set
	 * @link http://www.php.net/manual/en/solrquery.getstart.php
	 * @return int an integer on success and null if not set.
	 */
	public function getStart () {}

	/**
	 * Specifies the maximum number of rows to return in the result
	 * @link http://www.php.net/manual/en/solrquery.setrows.php
	 * @param int $rows The maximum number of rows to return
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function setRows (int $rows) {}

	/**
	 * Returns the maximum number of documents
	 * @link http://www.php.net/manual/en/solrquery.getrows.php
	 * @return int an integer on success and null if not set.
	 */
	public function getRows () {}

	/**
	 * Specifies which fields to return in the result
	 * @link http://www.php.net/manual/en/solrquery.addfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object
	 */
	public function addField (string $field) {}

	/**
	 * Removes a field from the list of fields
	 * @link http://www.php.net/manual/en/solrquery.removefield.php
	 * @param string $field Name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeField (string $field) {}

	/**
	 * Returns the list of fields that will be returned in the response
	 * @link http://www.php.net/manual/en/solrquery.getfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getFields () {}

	/**
	 * Used to control how the results should be sorted
	 * @link http://www.php.net/manual/en/solrquery.addsortfield.php
	 * @param string $field The name of the field
	 * @param int $order [optional] The sort direction. This should be either SolrQuery::ORDER_ASC or SolrQuery::ORDER_DESC.
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function addSortField (string $field, int $order = null) {}

	/**
	 * Removes one of the sort fields
	 * @link http://www.php.net/manual/en/solrquery.removesortfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeSortField (string $field) {}

	/**
	 * Returns all the sort fields
	 * @link http://www.php.net/manual/en/solrquery.getsortfields.php
	 * @return array an array on success and null if none of the parameters was set.
	 */
	public function getSortFields () {}

	/**
	 * Specifies a filter query
	 * @link http://www.php.net/manual/en/solrquery.addfilterquery.php
	 * @param string $fq The filter query
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function addFilterQuery (string $fq) {}

	/**
	 * Removes a filter query
	 * @link http://www.php.net/manual/en/solrquery.removefilterquery.php
	 * @param string $fq The filter query to remove
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFilterQuery (string $fq) {}

	/**
	 * Returns an array of filter queries
	 * @link http://www.php.net/manual/en/solrquery.getfilterqueries.php
	 * @return array an array on success and null if not set.
	 */
	public function getFilterQueries () {}

	/**
	 * Flag to show debug information
	 * @link http://www.php.net/manual/en/solrquery.setshowdebuginfo.php
	 * @param bool $flag Whether to show debug info. true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setShowDebugInfo (bool $flag) {}

	/**
	 * Sets the explainOther common query parameter
	 * @link http://www.php.net/manual/en/solrquery.setexplainother.php
	 * @param string $query The Lucene query to identify a set of documents
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setExplainOther (string $query) {}

	/**
	 * The time allowed for search to finish
	 * @link http://www.php.net/manual/en/solrquery.settimeallowed.php
	 * @param int $timeAllowed The time allowed for a search to finish.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTimeAllowed (int $timeAllowed) {}

	/**
	 * Returns the time in milliseconds allowed for the query to finish
	 * @link http://www.php.net/manual/en/solrquery.gettimeallowed.php
	 * @return int and integer on success and null if it is not set.
	 */
	public function getTimeAllowed () {}

	/**
	 * Exclude the header from the returned results
	 * @link http://www.php.net/manual/en/solrquery.setomitheader.php
	 * @param bool $flag true excludes the header from the result.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setOmitHeader (bool $flag) {}

	/**
	 * Toggles the echoHandler parameter
	 * @link http://www.php.net/manual/en/solrquery.setechohandler.php
	 * @param bool $flag true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setEchoHandler (bool $flag) {}

	/**
	 * Determines what kind of parameters to include in the response
	 * @link http://www.php.net/manual/en/solrquery.setechoparams.php
	 * @param string $type The type of parameters to include
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setEchoParams (string $type) {}

	/**
	 * Maps to the facet parameter. Enables or disables facetting
	 * @link http://www.php.net/manual/en/solrquery.setfacet.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacet (bool $flag) {}

	/**
	 * Returns the value of the facet parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacet.php
	 * @return bool a boolean on success and null if not set
	 */
	public function getFacet () {}

	/**
	 * Adds another field to the facet
	 * @link http://www.php.net/manual/en/solrquery.addfacetfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addFacetField (string $field) {}

	/**
	 * Removes one of the facet.date parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetField (string $field) {}

	/**
	 * Returns all the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetfields.php
	 * @return array an array of all the fields and null if none was set
	 */
	public function getFacetFields () {}

	/**
	 * Adds a facet query
	 * @link http://www.php.net/manual/en/solrquery.addfacetquery.php
	 * @param string $facetQuery The facet query
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addFacetQuery (string $facetQuery) {}

	/**
	 * Removes one of the facet.query parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetquery.php
	 * @param string $value The value
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetQuery (string $value) {}

	/**
	 * Returns all the facet queries
	 * @link http://www.php.net/manual/en/solrquery.getfacetqueries.php
	 * @return array an array on success and null if not set.
	 */
	public function getFacetQueries () {}

	/**
	 * Specifies a string prefix with which to limits the terms on which to facet
	 * @link http://www.php.net/manual/en/solrquery.setfacetprefix.php
	 * @param string $prefix The prefix string
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetPrefix (string $prefix, string $field_override = null) {}

	/**
	 * Returns the facet prefix
	 * @link http://www.php.net/manual/en/solrquery.getfacetprefix.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getFacetPrefix (string $field_override = null) {}

	/**
	 * Determines the ordering of the facet field constraints
	 * @link http://www.php.net/manual/en/solrquery.setfacetsort.php
	 * @param int $facetSort Use SolrQuery::FACET_SORT_INDEX for sorting by index order or SolrQuery::FACET_SORT_COUNT for sorting by count.
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetSort (int $facetSort, string $field_override = null) {}

	/**
	 * Returns the facet sort type
	 * @link http://www.php.net/manual/en/solrquery.getfacetsort.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer (SolrQuery::FACET_SORT_INDEX or SolrQuery::FACET_SORT_COUNT) on success or null if not set.
	 */
	public function getFacetSort (string $field_override = null) {}

	/**
	 * Maps to facet.limit
	 * @link http://www.php.net/manual/en/solrquery.setfacetlimit.php
	 * @param int $limit The maximum number of constraint counts
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetLimit (int $limit, string $field_override = null) {}

	/**
	 * Returns the maximum number of constraint counts that should be returned for the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetlimit.php
	 * @param string $field_override [optional] The name of the field to override for
	 * @return int an integer on success and null if not set
	 */
	public function getFacetLimit (string $field_override = null) {}

	/**
	 * Sets the offset into the list of constraints to allow for pagination
	 * @link http://www.php.net/manual/en/solrquery.setfacetoffset.php
	 * @param int $offset The offset
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetOffset (int $offset, string $field_override = null) {}

	/**
	 * Returns an offset into the list of constraints to be used for pagination
	 * @link http://www.php.net/manual/en/solrquery.getfacetoffset.php
	 * @param string $field_override [optional] The name of the field to override for.
	 * @return int an integer on success and null if not set
	 */
	public function getFacetOffset (string $field_override = null) {}

	/**
	 * Maps to facet.mincount
	 * @link http://www.php.net/manual/en/solrquery.setfacetmincount.php
	 * @param int $mincount The minimum count
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMinCount (int $mincount, string $field_override = null) {}

	/**
	 * Returns the minimum counts for facet fields should be included in the response
	 * @link http://www.php.net/manual/en/solrquery.getfacetmincount.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success and null if not set
	 */
	public function getFacetMinCount (string $field_override = null) {}

	/**
	 * Maps to facet.missing
	 * @link http://www.php.net/manual/en/solrquery.setfacetmissing.php
	 * @param bool $flag true turns this feature on. false disables it.
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMissing (bool $flag, string $field_override = null) {}

	/**
	 * Returns the current state of the facet.missing parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmissing.php
	 * @param string $field_override [optional] The name of the field
	 * @return bool a boolean on success and null if not set
	 */
	public function getFacetMissing (string $field_override = null) {}

	/**
	 * Specifies the type of algorithm to use when faceting a field
	 * @link http://www.php.net/manual/en/solrquery.setfacetmethod.php
	 * @param string $method The method to use.
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMethod (string $method, string $field_override = null) {}

	/**
	 * Returns the value of the facet.method parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmethod.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetMethod (string $field_override = null) {}

	/**
	 * Sets the minimum document frequency used for determining term count
	 * @link http://www.php.net/manual/en/solrquery.setfacetenumcachemindefaultfrequency.php
	 * @param int $frequency 
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetEnumCacheMinDefaultFrequency (int $frequency, string $field_override = null) {}

	/**
	 * Maps to facet.date
	 * @link http://www.php.net/manual/en/solrquery.addfacetdatefield.php
	 * @param string $dateField The name of the date field.
	 * @return SolrQuery a SolrQuery object.
	 */
	public function addFacetDateField (string $dateField) {}

	/**
	 * Removes one of the facet date fields
	 * @link http://www.php.net/manual/en/solrquery.removefacetdatefield.php
	 * @param string $field The name of the date field to remove
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateField (string $field) {}

	/**
	 * Returns all the facet.date fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatefields.php
	 * @return array all the facet.date fields as an array or null if none was set
	 */
	public function getFacetDateFields () {}

	/**
	 * Maps to facet.date.start
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatestart.php
	 * @param string $value See facet.date.start
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateStart (string $value, string $field_override = null) {}

	/**
	 * Returns the lower bound for the first date range for all date faceting on this field
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatestart.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateStart (string $field_override = null) {}

	/**
	 * Maps to facet.date.end
	 * @link http://www.php.net/manual/en/solrquery.setfacetdateend.php
	 * @param string $value See facet.date.end
	 * @param string $field_override [optional] Name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateEnd (string $value, string $field_override = null) {}

	/**
	 * Returns the value for the facet.date.end parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateend.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateEnd (string $field_override = null) {}

	/**
	 * Maps to facet.date.gap
	 * @link http://www.php.net/manual/en/solrquery.setfacetdategap.php
	 * @param string $value See facet.date.gap
	 * @param string $field_override [optional] The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateGap (string $value, string $field_override = null) {}

	/**
	 * Returns the value of the facet.date.gap parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdategap.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateGap (string $field_override = null) {}

	/**
	 * Maps to facet.date.hardend
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatehardend.php
	 * @param bool $value See facet.date.hardend
	 * @param string $field_override [optional] The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateHardEnd (bool $value, string $field_override = null) {}

	/**
	 * Returns the value of the facet.date.hardend parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatehardend.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateHardEnd (string $field_override = null) {}

	/**
	 * Adds another facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.addfacetdateother.php
	 * @param string $value The value to use.
	 * @param string $field_override [optional] The field name for the override.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addFacetDateOther (string $value, string $field_override = null) {}

	/**
	 * Removes one of the facet.date.other parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetdateother.php
	 * @param string $value The value
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateOther (string $value, string $field_override = null) {}

	/**
	 * Returns the value for the facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateother.php
	 * @param string $field_override [optional] The name of the field
	 * @return array an array on success and null if not set.
	 */
	public function getFacetDateOther (string $field_override = null) {}

	/**
	 * Enable/Disable result grouping (group parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroup.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroup (bool $value) {}

	/**
	 * Returns true if grouping is enabled
	 * @link http://www.php.net/manual/en/solrquery.getgroup.php
	 * @return bool 
	 */
	public function getGroup () {}

	/**
	 * Add a field to be used to group results
	 * @link http://www.php.net/manual/en/solrquery.addgroupfield.php
	 * @param string $value The name of the field.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function addGroupField (string $value) {}

	/**
	 * Returns group fields (group.field parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfields.php
	 * @return array 
	 */
	public function getGroupFields () {}

	/**
	 * Allows grouping results based on the unique values of a function query (group.func parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupfunction.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupFunction (string $value) {}

	/**
	 * Returns group functions (group.func parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfunctions.php
	 * @return array 
	 */
	public function getGroupFunctions () {}

	/**
	 * Allows grouping of documents that match the given query
	 * @link http://www.php.net/manual/en/solrquery.addgroupquery.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupQuery (string $value) {}

	/**
	 * Returns all the group.query parameter values
	 * @link http://www.php.net/manual/en/solrquery.getgroupqueries.php
	 * @return array array
	 */
	public function getGroupQueries () {}

	/**
	 * Specifies the number of results to return for each group. The server default value is 1
	 * @link http://www.php.net/manual/en/solrquery.setgrouplimit.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupLimit (int $value) {}

	/**
	 * Returns the group.limit value
	 * @link http://www.php.net/manual/en/solrquery.getgrouplimit.php
	 * @return int 
	 */
	public function getGroupLimit () {}

	/**
	 * Sets the group.offset parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupoffset.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupOffset (int $value) {}

	/**
	 * Returns the group.offset value
	 * @link http://www.php.net/manual/en/solrquery.getgroupoffset.php
	 * @return int 
	 */
	public function getGroupOffset () {}

	/**
	 * Add a group sort field (group.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupsortfield.php
	 * @param string $field Field name
	 * @param int $order [optional] Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants
	 * @return SolrQuery 
	 */
	public function addGroupSortField (string $field, int $order = null) {}

	/**
	 * Returns the group.sort value
	 * @link http://www.php.net/manual/en/solrquery.getgroupsortfields.php
	 * @return array 
	 */
	public function getGroupSortFields () {}

	/**
	 * Sets the group format, result structure (group.format parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroupformat.php
	 * @param string $value 
	 * @return SolrQuery 
	 */
	public function setGroupFormat (string $value) {}

	/**
	 * Returns the group.format value
	 * @link http://www.php.net/manual/en/solrquery.getgroupformat.php
	 * @return string 
	 */
	public function getGroupFormat () {}

	/**
	 * If true, the result of the first field grouping command is used as the main result list in the response, using group.format=simple
	 * @link http://www.php.net/manual/en/solrquery.setgroupmain.php
	 * @param string $value If true, the result of the first field grouping command is used as the main result list in the response.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function setGroupMain (string $value) {}

	/**
	 * Returns the group.main value
	 * @link http://www.php.net/manual/en/solrquery.getgroupmain.php
	 * @return bool 
	 */
	public function getGroupMain () {}

	/**
	 * If true, Solr includes the number of groups that have matched the query in the results
	 * @link http://www.php.net/manual/en/solrquery.setgroupngroups.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupNGroups (bool $value) {}

	/**
	 * Returns the group.ngroups value
	 * @link http://www.php.net/manual/en/solrquery.getgroupngroups.php
	 * @return bool 
	 */
	public function getGroupNGroups () {}

	/**
	 * If true, facet counts are based on the most relevant document of each group matching the query
	 * @link http://www.php.net/manual/en/solrquery.setgrouptruncate.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupTruncate (bool $value) {}

	/**
	 * Returns the group.truncate value
	 * @link http://www.php.net/manual/en/solrquery.getgrouptruncate.php
	 * @return bool 
	 */
	public function getGroupTruncate () {}

	/**
	 * Sets group.facet parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupfacet.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupFacet (bool $value) {}

	/**
	 * Returns the group.facet parameter value
	 * @link http://www.php.net/manual/en/solrquery.getgroupfacet.php
	 * @return bool 
	 */
	public function getGroupFacet () {}

	/**
	 * Enables caching for result grouping
	 * @link http://www.php.net/manual/en/solrquery.setgroupcachepercent.php
	 * @param int $percent 
	 * @return SolrQuery 
	 */
	public function setGroupCachePercent (int $percent) {}

	/**
	 * Returns group cache percent value
	 * @link http://www.php.net/manual/en/solrquery.getgroupcachepercent.php
	 * @return int 
	 */
	public function getGroupCachePercent () {}

	/**
	 * Collapses the result set to a single document per group
	 * @link http://www.php.net/manual/en/solrquery.collapse.php
	 * @param SolrCollapseFunction $collapseFunction 
	 * @return SolrQuery the current SolrQuery object
	 */
	public function collapse (SolrCollapseFunction $collapseFunction) {}

	/**
	 * Enables/Disables the Expand Component
	 * @link http://www.php.net/manual/en/solrquery.setexpand.php
	 * @param bool $value Bool flag
	 * @return SolrQuery SolrQuery
	 */
	public function setExpand (bool $value) {}

	/**
	 * Returns true if group expanding is enabled
	 * @link http://www.php.net/manual/en/solrquery.getexpand.php
	 * @return bool whether group expanding is enabled.
	 */
	public function getExpand () {}

	/**
	 * Orders the documents within the expanded groups (expand.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addexpandsortfield.php
	 * @param string $field field name
	 * @param string $order [optional] <p>
	 * Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants.
	 * </p>
	 * <p>
	 * Default: SolrQuery::ORDER_DESC
	 * </p>
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandSortField (string $field, string $order = null) {}

	/**
	 * Removes an expand sort field from the expand.sort parameter
	 * @link http://www.php.net/manual/en/solrquery.removeexpandsortfield.php
	 * @param string $field field name
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandSortField (string $field) {}

	/**
	 * Returns an array of fields
	 * @link http://www.php.net/manual/en/solrquery.getexpandsortfields.php
	 * @return array an array of fields.
	 */
	public function getExpandSortFields () {}

	/**
	 * Sets the number of rows to display in each group (expand.rows). Server Default 5
	 * @link http://www.php.net/manual/en/solrquery.setexpandrows.php
	 * @param int $value 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandRows (int $value) {}

	/**
	 * Returns The number of rows to display in each group (expand.rows)
	 * @link http://www.php.net/manual/en/solrquery.getexpandrows.php
	 * @return int the number of rows.
	 */
	public function getExpandRows () {}

	/**
	 * Sets the expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.setexpandquery.php
	 * @param string $q 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandQuery (string $q) {}

	/**
	 * Returns the expand query expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.getexpandquery.php
	 * @return array the expand query.
	 */
	public function getExpandQuery () {}

	/**
	 * Overrides main filter query, determines which documents to include in the main group
	 * @link http://www.php.net/manual/en/solrquery.addexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandFilterQuery (string $fq) {}

	/**
	 * Removes an expand filter query
	 * @link http://www.php.net/manual/en/solrquery.removeexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandFilterQuery (string $fq) {}

	/**
	 * Returns the expand filter queries
	 * @link http://www.php.net/manual/en/solrquery.getexpandfilterqueries.php
	 * @return array the expand filter queries.
	 */
	public function getExpandFilterQueries () {}

	/**
	 * Enables or disables highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlight.php
	 * @param bool $flag Enable or disable highlighting
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlight (bool $flag) {}

	/**
	 * Returns the state of the hl parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlight.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlight () {}

	/**
	 * Maps to hl.fl
	 * @link http://www.php.net/manual/en/solrquery.addhighlightfield.php
	 * @param string $field Name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addHighlightField (string $field) {}

	/**
	 * Removes one of the fields used for highlighting
	 * @link http://www.php.net/manual/en/solrquery.removehighlightfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeHighlightField (string $field) {}

	/**
	 * Returns all the fields that Solr should generate highlighted snippets for
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getHighlightFields () {}

	/**
	 * Sets the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsnippets.php
	 * @param int $value The maximum number of highlighted snippets to generate per field
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSnippets (int $value, string $field_override = null) {}

	/**
	 * Returns the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsnippets.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightSnippets (string $field_override = null) {}

	/**
	 * The size of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragsize.php
	 * @param int $size The size, in characters, of fragments to consider for highlighting
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragsize (int $size, string $field_override = null) {}

	/**
	 * Returns the number of characters of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragsize.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success or null if not set.
	 */
	public function getHighlightFragsize (string $field_override = null) {}

	/**
	 * Whether or not to collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmergecontiguous.php
	 * @param bool $flag 
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMergeContiguous (bool $flag, string $field_override = null) {}

	/**
	 * Returns whether or not the collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmergecontiguous.php
	 * @param string $field_override [optional] The name of the field
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightMergeContiguous (string $field_override = null) {}

	/**
	 * Require field matching during highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightrequirefieldmatch.php
	 * @param bool $flag true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRequireFieldMatch (bool $flag) {}

	/**
	 * Returns if a field will only be highlighted if the query matched in this particular field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightrequirefieldmatch.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightRequireFieldMatch () {}

	/**
	 * Specifies the number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxanalyzedchars.php
	 * @param int $value The number of characters into a document to look for suitable snippets
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAnalyzedChars (int $value) {}

	/**
	 * Returns the maximum number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxanalyzedchars.php
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightMaxAnalyzedChars () {}

	/**
	 * Specifies the backup field to use
	 * @link http://www.php.net/manual/en/solrquery.sethighlightalternatefield.php
	 * @param string $field The name of the backup field
	 * @param string $field_override [optional] The name of the field we are overriding this setting for.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightAlternateField (string $field, string $field_override = null) {}

	/**
	 * Returns the highlight field to use as backup or default
	 * @link http://www.php.net/manual/en/solrquery.gethighlightalternatefield.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightAlternateField (string $field_override = null) {}

	/**
	 * Sets the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxalternatefieldlength.php
	 * @param int $fieldLength The length of the field
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAlternateFieldLength (int $fieldLength, string $field_override = null) {}

	/**
	 * Returns the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxalternatefieldlength.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightMaxAlternateFieldLength (string $field_override = null) {}

	/**
	 * Specify a formatter for the highlight output
	 * @link http://www.php.net/manual/en/solrquery.sethighlightformatter.php
	 * @param string $formatter Currently the only legal value is "simple"
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function setHighlightFormatter (string $formatter, string $field_override = null) {}

	/**
	 * Returns the formatter for the highlighted output
	 * @link http://www.php.net/manual/en/solrquery.gethighlightformatter.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightFormatter (string $field_override = null) {}

	/**
	 * Sets the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepre.php
	 * @param string $simplePre The text which appears before a highlighted term
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSimplePre (string $simplePre, string $field_override = null) {}

	/**
	 * Returns the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepre.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightSimplePre (string $field_override = null) {}

	/**
	 * Sets the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepost.php
	 * @param string $simplePost <p>
	 * Sets the text which appears after a highlighted term
	 * </p>
	 * <p><pre> The default is </pre></p>
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function setHighlightSimplePost (string $simplePost, string $field_override = null) {}

	/**
	 * Returns the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepost.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightSimplePost (string $field_override = null) {}

	/**
	 * Sets a text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragmenter.php
	 * @param string $fragmenter The standard fragmenter is gap. Another option is regex, which tries to create fragments that resembles a certain regular expression
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragmenter (string $fragmenter, string $field_override = null) {}

	/**
	 * Returns the text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragmenter.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightFragmenter (string $field_override = null) {}

	/**
	 * Whether to highlight phrase terms only when they appear within the query phrase
	 * @link http://www.php.net/manual/en/solrquery.sethighlightusephrasehighlighter.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightUsePhraseHighlighter (bool $flag) {}

	/**
	 * Returns the state of the hl.usePhraseHighlighter parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightusephrasehighlighter.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightUsePhraseHighlighter () {}

	/**
	 * Use SpanScorer to highlight phrase terms
	 * @link http://www.php.net/manual/en/solrquery.sethighlighthighlightmultiterm.php
	 * @param bool $flag Whether or not to use SpanScorer to highlight phrase terms only when they appear within the query phrase in the document.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightHighlightMultiTerm (bool $flag) {}

	/**
	 * Returns whether or not to enable highlighting for range/wildcard/fuzzy/prefix queries
	 * @link http://www.php.net/manual/en/solrquery.gethighlighthighlightmultiterm.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightHighlightMultiTerm () {}

	/**
	 * Sets the factor by which the regex fragmenter can stray from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexslop.php
	 * @param float $factor The factor by which the regex fragmenter can stray from the ideal fragment size
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexSlop (float $factor) {}

	/**
	 * Returns the deviation factor from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexslop.php
	 * @return float a float on success and null if not set.
	 */
	public function getHighlightRegexSlop () {}

	/**
	 * Specify the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexpattern.php
	 * @param string $value The regular expression for fragmenting. This could be used to extract sentences
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexPattern (string $value) {}

	/**
	 * Returns the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexpattern.php
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightRegexPattern () {}

	/**
	 * Specify the maximum number of characters to analyze
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexmaxanalyzedchars.php
	 * @param int $maxAnalyzedChars The maximum number of characters to analyze from a field when using the regex fragmenter
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexMaxAnalyzedChars (int $maxAnalyzedChars) {}

	/**
	 * Returns the maximum number of characters from a field when using the regex fragmenter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexmaxanalyzedchars.php
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightRegexMaxAnalyzedChars () {}

	/**
	 * Enables or disables the Stats component
	 * @link http://www.php.net/manual/en/solrquery.setstats.php
	 * @param bool $flag true turns on the stats component and false disables it.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setStats (bool $flag) {}

	/**
	 * Returns whether or not stats is enabled
	 * @link http://www.php.net/manual/en/solrquery.getstats.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getStats () {}

	/**
	 * Maps to stats.field parameter
	 * @link http://www.php.net/manual/en/solrquery.addstatsfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addStatsField (string $field) {}

	/**
	 * Removes one of the stats.field parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfield.php
	 * @param string $field The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsField (string $field) {}

	/**
	 * Returns all the statistics fields
	 * @link http://www.php.net/manual/en/solrquery.getstatsfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getStatsFields () {}

	/**
	 * Requests a return of sub results for values within the given facet
	 * @link http://www.php.net/manual/en/solrquery.addstatsfacet.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addStatsFacet (string $field) {}

	/**
	 * Removes one of the stats.facet parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfacet.php
	 * @param string $value The value
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsFacet (string $value) {}

	/**
	 * Returns all the stats facets that were set
	 * @link http://www.php.net/manual/en/solrquery.getstatsfacets.php
	 * @return array an array on success and null if not set.
	 */
	public function getStatsFacets () {}

	/**
	 * Enables or disables moreLikeThis
	 * @link http://www.php.net/manual/en/solrquery.setmlt.php
	 * @param bool $flag true enables it and false turns it off.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMlt (bool $flag) {}

	/**
	 * Returns whether or not MoreLikeThis results should be enabled
	 * @link http://www.php.net/manual/en/solrquery.getmlt.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getMlt () {}

	/**
	 * Set the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.setmltcount.php
	 * @param int $count The number of similar documents to return for each result
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltCount (int $count) {}

	/**
	 * Returns the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.getmltcount.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltCount () {}

	/**
	 * Sets a field to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.addmltfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addMltField (string $field) {}

	/**
	 * Removes one of the moreLikeThis fields
	 * @link http://www.php.net/manual/en/solrquery.removemltfield.php
	 * @param string $field Name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeMltField (string $field) {}

	/**
	 * Returns all the fields to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.getmltfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getMltFields () {}

	/**
	 * Maps to mlt.qf
	 * @link http://www.php.net/manual/en/solrquery.addmltqueryfield.php
	 * @param string $field The name of the field
	 * @param float $boost Its boost value
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addMltQueryField (string $field, float $boost) {}

	/**
	 * Removes one of the moreLikeThis query fields
	 * @link http://www.php.net/manual/en/solrquery.removemltqueryfield.php
	 * @param string $queryField The query field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeMltQueryField (string $queryField) {}

	/**
	 * Returns the query fields and their boosts
	 * @link http://www.php.net/manual/en/solrquery.getmltqueryfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getMltQueryFields () {}

	/**
	 * Sets the frequency below which terms will be ignored in the source docs
	 * @link http://www.php.net/manual/en/solrquery.setmltmintermfrequency.php
	 * @param int $minTermFrequency The frequency below which terms will be ignored in the source docs
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinTermFrequency (int $minTermFrequency) {}

	/**
	 * Returns the frequency below which terms will be ignored in the source document
	 * @link http://www.php.net/manual/en/solrquery.getmltmintermfrequency.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMinTermFrequency () {}

	/**
	 * Sets the mltMinDoc frequency
	 * @link http://www.php.net/manual/en/solrquery.setmltmindocfrequency.php
	 * @param int $minDocFrequency Sets the frequency at which words will be ignored which do not occur in at least this many docs.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinDocFrequency (int $minDocFrequency) {}

	/**
	 * Returns the treshold frequency at which words will be ignored which do not occur in at least this many docs
	 * @link http://www.php.net/manual/en/solrquery.getmltmindocfrequency.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMinDocFrequency () {}

	/**
	 * Sets the minimum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltminwordlength.php
	 * @param int $minWordLength The minimum word length below which words will be ignored
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinWordLength (int $minWordLength) {}

	/**
	 * Returns the minimum word length below which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltminwordlength.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMinWordLength () {}

	/**
	 * Sets the maximum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxwordlength.php
	 * @param int $maxWordLength The maximum word length above which words will be ignored
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxWordLength (int $maxWordLength) {}

	/**
	 * Returns the maximum word length above which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxwordlength.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMaxWordLength () {}

	/**
	 * Specifies the maximum number of tokens to parse
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumtokens.php
	 * @param int $value The maximum number of tokens to parse
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumTokens (int $value) {}

	/**
	 * Returns the maximum number of tokens to parse in each document field that is not stored with TermVector support
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumtokens.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMaxNumTokens () {}

	/**
	 * Sets the maximum number of query terms included
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumqueryterms.php
	 * @param int $value The maximum number of query terms that will be included in any generated query
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumQueryTerms (int $value) {}

	/**
	 * Returns the maximum number of query terms that will be included in any generated query
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumqueryterms.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMaxNumQueryTerms () {}

	/**
	 * Set if the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.setmltboost.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltBoost (bool $flag) {}

	/**
	 * Returns whether or not the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.getmltboost.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getMltBoost () {}

	/**
	 * Enables or disables the TermsComponent
	 * @link http://www.php.net/manual/en/solrquery.setterms.php
	 * @param bool $flag true enables it. false turns it off
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTerms (bool $flag) {}

	/**
	 * Returns whether or not the TermsComponent is enabled
	 * @link http://www.php.net/manual/en/solrquery.getterms.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTerms () {}

	/**
	 * Sets the name of the field to get the Terms from
	 * @link http://www.php.net/manual/en/solrquery.settermsfield.php
	 * @param string $fieldname The field name
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsField (string $fieldname) {}

	/**
	 * Returns the field from which the terms are retrieved
	 * @link http://www.php.net/manual/en/solrquery.gettermsfield.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsField () {}

	/**
	 * Specifies the Term to start from
	 * @link http://www.php.net/manual/en/solrquery.settermslowerbound.php
	 * @param string $lowerBound The lower bound Term
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLowerBound (string $lowerBound) {}

	/**
	 * Returns the term to start at
	 * @link http://www.php.net/manual/en/solrquery.gettermslowerbound.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsLowerBound () {}

	/**
	 * Sets the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.settermsupperbound.php
	 * @param string $upperBound The term to stop at
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsUpperBound (string $upperBound) {}

	/**
	 * Returns the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.gettermsupperbound.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsUpperBound () {}

	/**
	 * Include the lower bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludelowerbound.php
	 * @param bool $flag Include the lower bound term in the result set
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeLowerBound (bool $flag) {}

	/**
	 * Returns whether or not to include the lower bound in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludelowerbound.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTermsIncludeLowerBound () {}

	/**
	 * Include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludeupperbound.php
	 * @param bool $flag true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeUpperBound (bool $flag) {}

	/**
	 * Returns whether or not to include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludeupperbound.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTermsIncludeUpperBound () {}

	/**
	 * Sets the minimum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmincount.php
	 * @param int $frequency The minimum frequency
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMinCount (int $frequency) {}

	/**
	 * Returns the minimum document frequency to return in order to be included
	 * @link http://www.php.net/manual/en/solrquery.gettermsmincount.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsMinCount () {}

	/**
	 * Sets the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmaxcount.php
	 * @param int $frequency The maximum document frequency.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMaxCount (int $frequency) {}

	/**
	 * Returns the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.gettermsmaxcount.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsMaxCount () {}

	/**
	 * Restrict matches to terms that start with the prefix
	 * @link http://www.php.net/manual/en/solrquery.settermsprefix.php
	 * @param string $prefix Restrict matches to terms that start with the prefix
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsPrefix (string $prefix) {}

	/**
	 * Returns the term prefix
	 * @link http://www.php.net/manual/en/solrquery.gettermsprefix.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsPrefix () {}

	/**
	 * Sets the maximum number of terms to return
	 * @link http://www.php.net/manual/en/solrquery.settermslimit.php
	 * @param int $limit The maximum number of terms to return. All the terms will be returned if the limit is negative.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLimit (int $limit) {}

	/**
	 * Returns the maximum number of terms Solr should return
	 * @link http://www.php.net/manual/en/solrquery.gettermslimit.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsLimit () {}

	/**
	 * Return the raw characters of the indexed term
	 * @link http://www.php.net/manual/en/solrquery.settermsreturnraw.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsReturnRaw (bool $flag) {}

	/**
	 * Whether or not to return raw characters
	 * @link http://www.php.net/manual/en/solrquery.gettermsreturnraw.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTermsReturnRaw () {}

	/**
	 * Specifies how to sort the returned terms
	 * @link http://www.php.net/manual/en/solrquery.settermssort.php
	 * @param int $sortType SolrQuery::TERMS_SORT_INDEX or SolrQuery::TERMS_SORT_COUNT
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsSort (int $sortType) {}

	/**
	 * Returns an integer indicating how terms are sorted
	 * @link http://www.php.net/manual/en/solrquery.gettermssort.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsSort () {}

	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name Name of the parameter
	 * @param string $value Value of the parameter
	 * @return SolrParams a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value) {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name Name of parameter
	 * @param string $value Value of parameter
	 * @return SolrParams a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value) {}

	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] Whether to return URL-encoded values
	 * @return string a string on success and false on failure.
	 */
	public function toString (bool $url_encode = null) {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array an array of non URL-encoded parameters
	 */
	public function getParams () {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] The name of the parameter
	 * @return mixed a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null) {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array an array on URL-encoded parameters
	 */
	public function getPreparedParams () {}

	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized The serialized representation of the object
	 * @return void None
	 */
	public function unserialize (string $serialized) {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias: SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name The name of the parameter
	 * @param string $value The value of the parameter
	 * @return SolrParams a SolrParams instance on success
	 */
	public function add (string $name, string $value) {}

	/**
	 * Alias: SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name Then name of the parameter
	 * @param string $value The parameter value
	 * @return void an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value) {}

	/**
	 * Alias: SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name Then name of the parameter
	 * @return mixed an array or string depending on the type of parameter
	 */
	public function get (string $param_name) {}

}

/**
 * @link http://www.php.net/manual/en/class.solrdismaxquery.php
 */
class SolrDisMaxQuery extends SolrQuery implements Serializable, Stringable {
	protected $_hashtable_index;


	/**
	 * Class Constructor
	 * @link http://www.php.net/manual/en/solrdismaxquery.construct.php
	 * @param mixed $q [optional]
	 */
	public function __construct ($q = null) {}

	/**
	 * Set Query Alternate (q.alt parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setqueryalt.php
	 * @param string $q Query String
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setQueryAlt (string $q) {}

	/**
	 * Add a query field with optional boost (qf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addqueryfield.php
	 * @param string $field field name
	 * @param string $boost [optional] Boost value. Boosts documents with matching terms.
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addQueryField (string $field, string $boost = null) {}

	/**
	 * Removes a Query Field (qf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removequeryfield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeQueryField (string $field) {}

	/**
	 * Adds a Phrase Field (pf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addphrasefield.php
	 * @param string $field field name
	 * @param string $boost 
	 * @param string $slop [optional] 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addPhraseField (string $field, string $boost, string $slop = null) {}

	/**
	 * Removes a Phrase Field (pf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removephrasefield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removePhraseField (string $field) {}

	/**
	 * Sets Phrase Fields and their boosts (and slops) using pf2 parameter
	 * @link http://www.php.net/manual/en/solrdismaxquery.setphrasefields.php
	 * @param string $fields Fields, boosts [, slops]
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setPhraseFields (string $fields) {}

	/**
	 * Sets the default slop on phrase queries (ps parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setphraseslop.php
	 * @param string $slop 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setPhraseSlop (string $slop) {}

	/**
	 * Specifies the amount of slop permitted on phrase queries explicitly included in the user's query string (qf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setqueryphraseslop.php
	 * @param string $slop Amount of slop
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setQueryPhraseSlop (string $slop) {}

	/**
	 * Directly Sets Boost Query Parameter (bq)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setboostquery.php
	 * @param string $q query
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBoostQuery (string $q) {}

	/**
	 * Adds a boost query field with value and optional boost (bq parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addboostquery.php
	 * @param string $field 
	 * @param string $value 
	 * @param string $boost [optional] 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addBoostQuery (string $field, string $value, string $boost = null) {}

	/**
	 * Removes a boost query partial by field name (bq)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removeboostquery.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeBoostQuery (string $field) {}

	/**
	 * Sets a Boost Function (bf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setboostfunction.php
	 * @param string $function 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBoostFunction (string $function) {}

	/**
	 * Set Minimum "Should" Match (mm)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setminimummatch.php
	 * @param string $value Minimum match value/expression
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setMinimumMatch (string $value) {}

	/**
	 * Sets Tie Breaker parameter (tie parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.settiebreaker.php
	 * @param string $tieBreaker The tie parameter specifies a float value (which should be something much less than 1) to use as tiebreaker in DisMax queries.
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setTieBreaker (string $tieBreaker) {}

	/**
	 * Switch QueryParser to be DisMax Query Parser
	 * @link http://www.php.net/manual/en/solrdismaxquery.usedismaxqueryparser.php
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function useDisMaxQueryParser () {}

	/**
	 * Switch QueryParser to be EDisMax
	 * @link http://www.php.net/manual/en/solrdismaxquery.useedismaxqueryparser.php
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function useEDisMaxQueryParser () {}

	/**
	 * Sets Bigram Phrase Fields and their boosts (and slops) using pf2 parameter
	 * @link http://www.php.net/manual/en/solrdismaxquery.setbigramphrasefields.php
	 * @param string $fields Fields boosts (slops)
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBigramPhraseFields (string $fields) {}

	/**
	 * Adds a Phrase Bigram Field (pf2 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addbigramphrasefield.php
	 * @param string $field 
	 * @param string $boost 
	 * @param string $slop [optional] 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addBigramPhraseField (string $field, string $boost, string $slop = null) {}

	/**
	 * Removes phrase bigram field (pf2 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removebigramphrasefield.php
	 * @param string $field The Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeBigramPhraseField (string $field) {}

	/**
	 * Sets Bigram Phrase Slop (ps2 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setbigramphraseslop.php
	 * @param string $slop 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBigramPhraseSlop (string $slop) {}

	/**
	 * Directly Sets Trigram Phrase Fields (pf3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.settrigramphrasefields.php
	 * @param string $fields Trigram Phrase Fields
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setTrigramPhraseFields (string $fields) {}

	/**
	 * Adds a Trigram Phrase Field (pf3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addtrigramphrasefield.php
	 * @param string $field Field Name
	 * @param string $boost Field Boost
	 * @param string $slop [optional] Field Slop
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addTrigramPhraseField (string $field, string $boost, string $slop = null) {}

	/**
	 * Removes a Trigram Phrase Field (pf3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removetrigramphrasefield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeTrigramPhraseField (string $field) {}

	/**
	 * Sets Trigram Phrase Slop (ps3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.settrigramphraseslop.php
	 * @param string $slop Phrase slop
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setTrigramPhraseSlop (string $slop) {}

	/**
	 * Adds a field to User Fields Parameter (uf)
	 * @link http://www.php.net/manual/en/solrdismaxquery.adduserfield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addUserField (string $field) {}

	/**
	 * Removes a field from The User Fields Parameter (uf)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removeuserfield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeUserField (string $field) {}

	/**
	 * Sets User Fields parameter (uf)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setuserfields.php
	 * @param string $fields <p>
	 * Fields names separated by space
	 * </p>
	 * <p>
	 * This parameter supports wildcards. 
	 * </p>
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setUserFields (string $fields) {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrquery.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Sets the search query
	 * @link http://www.php.net/manual/en/solrquery.setquery.php
	 * @param string $query The search query
	 * @return SolrQuery the current SolrQuery object
	 */
	public function setQuery (string $query) {}

	/**
	 * Returns the main query
	 * @link http://www.php.net/manual/en/solrquery.getquery.php
	 * @return string a string on success and null if not set.
	 */
	public function getQuery () {}

	/**
	 * Specifies the number of rows to skip
	 * @link http://www.php.net/manual/en/solrquery.setstart.php
	 * @param int $start The number of rows to skip.
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function setStart (int $start) {}

	/**
	 * Returns the offset in the complete result set
	 * @link http://www.php.net/manual/en/solrquery.getstart.php
	 * @return int an integer on success and null if not set.
	 */
	public function getStart () {}

	/**
	 * Specifies the maximum number of rows to return in the result
	 * @link http://www.php.net/manual/en/solrquery.setrows.php
	 * @param int $rows The maximum number of rows to return
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function setRows (int $rows) {}

	/**
	 * Returns the maximum number of documents
	 * @link http://www.php.net/manual/en/solrquery.getrows.php
	 * @return int an integer on success and null if not set.
	 */
	public function getRows () {}

	/**
	 * Specifies which fields to return in the result
	 * @link http://www.php.net/manual/en/solrquery.addfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object
	 */
	public function addField (string $field) {}

	/**
	 * Removes a field from the list of fields
	 * @link http://www.php.net/manual/en/solrquery.removefield.php
	 * @param string $field Name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeField (string $field) {}

	/**
	 * Returns the list of fields that will be returned in the response
	 * @link http://www.php.net/manual/en/solrquery.getfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getFields () {}

	/**
	 * Used to control how the results should be sorted
	 * @link http://www.php.net/manual/en/solrquery.addsortfield.php
	 * @param string $field The name of the field
	 * @param int $order [optional] The sort direction. This should be either SolrQuery::ORDER_ASC or SolrQuery::ORDER_DESC.
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function addSortField (string $field, int $order = null) {}

	/**
	 * Removes one of the sort fields
	 * @link http://www.php.net/manual/en/solrquery.removesortfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeSortField (string $field) {}

	/**
	 * Returns all the sort fields
	 * @link http://www.php.net/manual/en/solrquery.getsortfields.php
	 * @return array an array on success and null if none of the parameters was set.
	 */
	public function getSortFields () {}

	/**
	 * Specifies a filter query
	 * @link http://www.php.net/manual/en/solrquery.addfilterquery.php
	 * @param string $fq The filter query
	 * @return SolrQuery the current SolrQuery object.
	 */
	public function addFilterQuery (string $fq) {}

	/**
	 * Removes a filter query
	 * @link http://www.php.net/manual/en/solrquery.removefilterquery.php
	 * @param string $fq The filter query to remove
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFilterQuery (string $fq) {}

	/**
	 * Returns an array of filter queries
	 * @link http://www.php.net/manual/en/solrquery.getfilterqueries.php
	 * @return array an array on success and null if not set.
	 */
	public function getFilterQueries () {}

	/**
	 * Flag to show debug information
	 * @link http://www.php.net/manual/en/solrquery.setshowdebuginfo.php
	 * @param bool $flag Whether to show debug info. true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setShowDebugInfo (bool $flag) {}

	/**
	 * Sets the explainOther common query parameter
	 * @link http://www.php.net/manual/en/solrquery.setexplainother.php
	 * @param string $query The Lucene query to identify a set of documents
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setExplainOther (string $query) {}

	/**
	 * The time allowed for search to finish
	 * @link http://www.php.net/manual/en/solrquery.settimeallowed.php
	 * @param int $timeAllowed The time allowed for a search to finish.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTimeAllowed (int $timeAllowed) {}

	/**
	 * Returns the time in milliseconds allowed for the query to finish
	 * @link http://www.php.net/manual/en/solrquery.gettimeallowed.php
	 * @return int and integer on success and null if it is not set.
	 */
	public function getTimeAllowed () {}

	/**
	 * Exclude the header from the returned results
	 * @link http://www.php.net/manual/en/solrquery.setomitheader.php
	 * @param bool $flag true excludes the header from the result.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setOmitHeader (bool $flag) {}

	/**
	 * Toggles the echoHandler parameter
	 * @link http://www.php.net/manual/en/solrquery.setechohandler.php
	 * @param bool $flag true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setEchoHandler (bool $flag) {}

	/**
	 * Determines what kind of parameters to include in the response
	 * @link http://www.php.net/manual/en/solrquery.setechoparams.php
	 * @param string $type The type of parameters to include
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setEchoParams (string $type) {}

	/**
	 * Maps to the facet parameter. Enables or disables facetting
	 * @link http://www.php.net/manual/en/solrquery.setfacet.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacet (bool $flag) {}

	/**
	 * Returns the value of the facet parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacet.php
	 * @return bool a boolean on success and null if not set
	 */
	public function getFacet () {}

	/**
	 * Adds another field to the facet
	 * @link http://www.php.net/manual/en/solrquery.addfacetfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addFacetField (string $field) {}

	/**
	 * Removes one of the facet.date parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetField (string $field) {}

	/**
	 * Returns all the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetfields.php
	 * @return array an array of all the fields and null if none was set
	 */
	public function getFacetFields () {}

	/**
	 * Adds a facet query
	 * @link http://www.php.net/manual/en/solrquery.addfacetquery.php
	 * @param string $facetQuery The facet query
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addFacetQuery (string $facetQuery) {}

	/**
	 * Removes one of the facet.query parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetquery.php
	 * @param string $value The value
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetQuery (string $value) {}

	/**
	 * Returns all the facet queries
	 * @link http://www.php.net/manual/en/solrquery.getfacetqueries.php
	 * @return array an array on success and null if not set.
	 */
	public function getFacetQueries () {}

	/**
	 * Specifies a string prefix with which to limits the terms on which to facet
	 * @link http://www.php.net/manual/en/solrquery.setfacetprefix.php
	 * @param string $prefix The prefix string
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetPrefix (string $prefix, string $field_override = null) {}

	/**
	 * Returns the facet prefix
	 * @link http://www.php.net/manual/en/solrquery.getfacetprefix.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getFacetPrefix (string $field_override = null) {}

	/**
	 * Determines the ordering of the facet field constraints
	 * @link http://www.php.net/manual/en/solrquery.setfacetsort.php
	 * @param int $facetSort Use SolrQuery::FACET_SORT_INDEX for sorting by index order or SolrQuery::FACET_SORT_COUNT for sorting by count.
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetSort (int $facetSort, string $field_override = null) {}

	/**
	 * Returns the facet sort type
	 * @link http://www.php.net/manual/en/solrquery.getfacetsort.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer (SolrQuery::FACET_SORT_INDEX or SolrQuery::FACET_SORT_COUNT) on success or null if not set.
	 */
	public function getFacetSort (string $field_override = null) {}

	/**
	 * Maps to facet.limit
	 * @link http://www.php.net/manual/en/solrquery.setfacetlimit.php
	 * @param int $limit The maximum number of constraint counts
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetLimit (int $limit, string $field_override = null) {}

	/**
	 * Returns the maximum number of constraint counts that should be returned for the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetlimit.php
	 * @param string $field_override [optional] The name of the field to override for
	 * @return int an integer on success and null if not set
	 */
	public function getFacetLimit (string $field_override = null) {}

	/**
	 * Sets the offset into the list of constraints to allow for pagination
	 * @link http://www.php.net/manual/en/solrquery.setfacetoffset.php
	 * @param int $offset The offset
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetOffset (int $offset, string $field_override = null) {}

	/**
	 * Returns an offset into the list of constraints to be used for pagination
	 * @link http://www.php.net/manual/en/solrquery.getfacetoffset.php
	 * @param string $field_override [optional] The name of the field to override for.
	 * @return int an integer on success and null if not set
	 */
	public function getFacetOffset (string $field_override = null) {}

	/**
	 * Maps to facet.mincount
	 * @link http://www.php.net/manual/en/solrquery.setfacetmincount.php
	 * @param int $mincount The minimum count
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMinCount (int $mincount, string $field_override = null) {}

	/**
	 * Returns the minimum counts for facet fields should be included in the response
	 * @link http://www.php.net/manual/en/solrquery.getfacetmincount.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success and null if not set
	 */
	public function getFacetMinCount (string $field_override = null) {}

	/**
	 * Maps to facet.missing
	 * @link http://www.php.net/manual/en/solrquery.setfacetmissing.php
	 * @param bool $flag true turns this feature on. false disables it.
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMissing (bool $flag, string $field_override = null) {}

	/**
	 * Returns the current state of the facet.missing parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmissing.php
	 * @param string $field_override [optional] The name of the field
	 * @return bool a boolean on success and null if not set
	 */
	public function getFacetMissing (string $field_override = null) {}

	/**
	 * Specifies the type of algorithm to use when faceting a field
	 * @link http://www.php.net/manual/en/solrquery.setfacetmethod.php
	 * @param string $method The method to use.
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMethod (string $method, string $field_override = null) {}

	/**
	 * Returns the value of the facet.method parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmethod.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetMethod (string $field_override = null) {}

	/**
	 * Sets the minimum document frequency used for determining term count
	 * @link http://www.php.net/manual/en/solrquery.setfacetenumcachemindefaultfrequency.php
	 * @param int $frequency 
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetEnumCacheMinDefaultFrequency (int $frequency, string $field_override = null) {}

	/**
	 * Maps to facet.date
	 * @link http://www.php.net/manual/en/solrquery.addfacetdatefield.php
	 * @param string $dateField The name of the date field.
	 * @return SolrQuery a SolrQuery object.
	 */
	public function addFacetDateField (string $dateField) {}

	/**
	 * Removes one of the facet date fields
	 * @link http://www.php.net/manual/en/solrquery.removefacetdatefield.php
	 * @param string $field The name of the date field to remove
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateField (string $field) {}

	/**
	 * Returns all the facet.date fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatefields.php
	 * @return array all the facet.date fields as an array or null if none was set
	 */
	public function getFacetDateFields () {}

	/**
	 * Maps to facet.date.start
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatestart.php
	 * @param string $value See facet.date.start
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateStart (string $value, string $field_override = null) {}

	/**
	 * Returns the lower bound for the first date range for all date faceting on this field
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatestart.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateStart (string $field_override = null) {}

	/**
	 * Maps to facet.date.end
	 * @link http://www.php.net/manual/en/solrquery.setfacetdateend.php
	 * @param string $value See facet.date.end
	 * @param string $field_override [optional] Name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateEnd (string $value, string $field_override = null) {}

	/**
	 * Returns the value for the facet.date.end parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateend.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateEnd (string $field_override = null) {}

	/**
	 * Maps to facet.date.gap
	 * @link http://www.php.net/manual/en/solrquery.setfacetdategap.php
	 * @param string $value See facet.date.gap
	 * @param string $field_override [optional] The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateGap (string $value, string $field_override = null) {}

	/**
	 * Returns the value of the facet.date.gap parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdategap.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateGap (string $field_override = null) {}

	/**
	 * Maps to facet.date.hardend
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatehardend.php
	 * @param bool $value See facet.date.hardend
	 * @param string $field_override [optional] The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateHardEnd (bool $value, string $field_override = null) {}

	/**
	 * Returns the value of the facet.date.hardend parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatehardend.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set
	 */
	public function getFacetDateHardEnd (string $field_override = null) {}

	/**
	 * Adds another facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.addfacetdateother.php
	 * @param string $value The value to use.
	 * @param string $field_override [optional] The field name for the override.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addFacetDateOther (string $value, string $field_override = null) {}

	/**
	 * Removes one of the facet.date.other parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetdateother.php
	 * @param string $value The value
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateOther (string $value, string $field_override = null) {}

	/**
	 * Returns the value for the facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateother.php
	 * @param string $field_override [optional] The name of the field
	 * @return array an array on success and null if not set.
	 */
	public function getFacetDateOther (string $field_override = null) {}

	/**
	 * Enable/Disable result grouping (group parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroup.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroup (bool $value) {}

	/**
	 * Returns true if grouping is enabled
	 * @link http://www.php.net/manual/en/solrquery.getgroup.php
	 * @return bool 
	 */
	public function getGroup () {}

	/**
	 * Add a field to be used to group results
	 * @link http://www.php.net/manual/en/solrquery.addgroupfield.php
	 * @param string $value The name of the field.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function addGroupField (string $value) {}

	/**
	 * Returns group fields (group.field parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfields.php
	 * @return array 
	 */
	public function getGroupFields () {}

	/**
	 * Allows grouping results based on the unique values of a function query (group.func parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupfunction.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupFunction (string $value) {}

	/**
	 * Returns group functions (group.func parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfunctions.php
	 * @return array 
	 */
	public function getGroupFunctions () {}

	/**
	 * Allows grouping of documents that match the given query
	 * @link http://www.php.net/manual/en/solrquery.addgroupquery.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupQuery (string $value) {}

	/**
	 * Returns all the group.query parameter values
	 * @link http://www.php.net/manual/en/solrquery.getgroupqueries.php
	 * @return array array
	 */
	public function getGroupQueries () {}

	/**
	 * Specifies the number of results to return for each group. The server default value is 1
	 * @link http://www.php.net/manual/en/solrquery.setgrouplimit.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupLimit (int $value) {}

	/**
	 * Returns the group.limit value
	 * @link http://www.php.net/manual/en/solrquery.getgrouplimit.php
	 * @return int 
	 */
	public function getGroupLimit () {}

	/**
	 * Sets the group.offset parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupoffset.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupOffset (int $value) {}

	/**
	 * Returns the group.offset value
	 * @link http://www.php.net/manual/en/solrquery.getgroupoffset.php
	 * @return int 
	 */
	public function getGroupOffset () {}

	/**
	 * Add a group sort field (group.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupsortfield.php
	 * @param string $field Field name
	 * @param int $order [optional] Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants
	 * @return SolrQuery 
	 */
	public function addGroupSortField (string $field, int $order = null) {}

	/**
	 * Returns the group.sort value
	 * @link http://www.php.net/manual/en/solrquery.getgroupsortfields.php
	 * @return array 
	 */
	public function getGroupSortFields () {}

	/**
	 * Sets the group format, result structure (group.format parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroupformat.php
	 * @param string $value 
	 * @return SolrQuery 
	 */
	public function setGroupFormat (string $value) {}

	/**
	 * Returns the group.format value
	 * @link http://www.php.net/manual/en/solrquery.getgroupformat.php
	 * @return string 
	 */
	public function getGroupFormat () {}

	/**
	 * If true, the result of the first field grouping command is used as the main result list in the response, using group.format=simple
	 * @link http://www.php.net/manual/en/solrquery.setgroupmain.php
	 * @param string $value If true, the result of the first field grouping command is used as the main result list in the response.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function setGroupMain (string $value) {}

	/**
	 * Returns the group.main value
	 * @link http://www.php.net/manual/en/solrquery.getgroupmain.php
	 * @return bool 
	 */
	public function getGroupMain () {}

	/**
	 * If true, Solr includes the number of groups that have matched the query in the results
	 * @link http://www.php.net/manual/en/solrquery.setgroupngroups.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupNGroups (bool $value) {}

	/**
	 * Returns the group.ngroups value
	 * @link http://www.php.net/manual/en/solrquery.getgroupngroups.php
	 * @return bool 
	 */
	public function getGroupNGroups () {}

	/**
	 * If true, facet counts are based on the most relevant document of each group matching the query
	 * @link http://www.php.net/manual/en/solrquery.setgrouptruncate.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupTruncate (bool $value) {}

	/**
	 * Returns the group.truncate value
	 * @link http://www.php.net/manual/en/solrquery.getgrouptruncate.php
	 * @return bool 
	 */
	public function getGroupTruncate () {}

	/**
	 * Sets group.facet parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupfacet.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupFacet (bool $value) {}

	/**
	 * Returns the group.facet parameter value
	 * @link http://www.php.net/manual/en/solrquery.getgroupfacet.php
	 * @return bool 
	 */
	public function getGroupFacet () {}

	/**
	 * Enables caching for result grouping
	 * @link http://www.php.net/manual/en/solrquery.setgroupcachepercent.php
	 * @param int $percent 
	 * @return SolrQuery 
	 */
	public function setGroupCachePercent (int $percent) {}

	/**
	 * Returns group cache percent value
	 * @link http://www.php.net/manual/en/solrquery.getgroupcachepercent.php
	 * @return int 
	 */
	public function getGroupCachePercent () {}

	/**
	 * Collapses the result set to a single document per group
	 * @link http://www.php.net/manual/en/solrquery.collapse.php
	 * @param SolrCollapseFunction $collapseFunction 
	 * @return SolrQuery the current SolrQuery object
	 */
	public function collapse (SolrCollapseFunction $collapseFunction) {}

	/**
	 * Enables/Disables the Expand Component
	 * @link http://www.php.net/manual/en/solrquery.setexpand.php
	 * @param bool $value Bool flag
	 * @return SolrQuery SolrQuery
	 */
	public function setExpand (bool $value) {}

	/**
	 * Returns true if group expanding is enabled
	 * @link http://www.php.net/manual/en/solrquery.getexpand.php
	 * @return bool whether group expanding is enabled.
	 */
	public function getExpand () {}

	/**
	 * Orders the documents within the expanded groups (expand.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addexpandsortfield.php
	 * @param string $field field name
	 * @param string $order [optional] <p>
	 * Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants.
	 * </p>
	 * <p>
	 * Default: SolrQuery::ORDER_DESC
	 * </p>
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandSortField (string $field, string $order = null) {}

	/**
	 * Removes an expand sort field from the expand.sort parameter
	 * @link http://www.php.net/manual/en/solrquery.removeexpandsortfield.php
	 * @param string $field field name
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandSortField (string $field) {}

	/**
	 * Returns an array of fields
	 * @link http://www.php.net/manual/en/solrquery.getexpandsortfields.php
	 * @return array an array of fields.
	 */
	public function getExpandSortFields () {}

	/**
	 * Sets the number of rows to display in each group (expand.rows). Server Default 5
	 * @link http://www.php.net/manual/en/solrquery.setexpandrows.php
	 * @param int $value 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandRows (int $value) {}

	/**
	 * Returns The number of rows to display in each group (expand.rows)
	 * @link http://www.php.net/manual/en/solrquery.getexpandrows.php
	 * @return int the number of rows.
	 */
	public function getExpandRows () {}

	/**
	 * Sets the expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.setexpandquery.php
	 * @param string $q 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandQuery (string $q) {}

	/**
	 * Returns the expand query expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.getexpandquery.php
	 * @return array the expand query.
	 */
	public function getExpandQuery () {}

	/**
	 * Overrides main filter query, determines which documents to include in the main group
	 * @link http://www.php.net/manual/en/solrquery.addexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandFilterQuery (string $fq) {}

	/**
	 * Removes an expand filter query
	 * @link http://www.php.net/manual/en/solrquery.removeexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandFilterQuery (string $fq) {}

	/**
	 * Returns the expand filter queries
	 * @link http://www.php.net/manual/en/solrquery.getexpandfilterqueries.php
	 * @return array the expand filter queries.
	 */
	public function getExpandFilterQueries () {}

	/**
	 * Enables or disables highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlight.php
	 * @param bool $flag Enable or disable highlighting
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlight (bool $flag) {}

	/**
	 * Returns the state of the hl parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlight.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlight () {}

	/**
	 * Maps to hl.fl
	 * @link http://www.php.net/manual/en/solrquery.addhighlightfield.php
	 * @param string $field Name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addHighlightField (string $field) {}

	/**
	 * Removes one of the fields used for highlighting
	 * @link http://www.php.net/manual/en/solrquery.removehighlightfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeHighlightField (string $field) {}

	/**
	 * Returns all the fields that Solr should generate highlighted snippets for
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getHighlightFields () {}

	/**
	 * Sets the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsnippets.php
	 * @param int $value The maximum number of highlighted snippets to generate per field
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSnippets (int $value, string $field_override = null) {}

	/**
	 * Returns the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsnippets.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightSnippets (string $field_override = null) {}

	/**
	 * The size of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragsize.php
	 * @param int $size The size, in characters, of fragments to consider for highlighting
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragsize (int $size, string $field_override = null) {}

	/**
	 * Returns the number of characters of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragsize.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success or null if not set.
	 */
	public function getHighlightFragsize (string $field_override = null) {}

	/**
	 * Whether or not to collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmergecontiguous.php
	 * @param bool $flag 
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMergeContiguous (bool $flag, string $field_override = null) {}

	/**
	 * Returns whether or not the collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmergecontiguous.php
	 * @param string $field_override [optional] The name of the field
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightMergeContiguous (string $field_override = null) {}

	/**
	 * Require field matching during highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightrequirefieldmatch.php
	 * @param bool $flag true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRequireFieldMatch (bool $flag) {}

	/**
	 * Returns if a field will only be highlighted if the query matched in this particular field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightrequirefieldmatch.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightRequireFieldMatch () {}

	/**
	 * Specifies the number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxanalyzedchars.php
	 * @param int $value The number of characters into a document to look for suitable snippets
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAnalyzedChars (int $value) {}

	/**
	 * Returns the maximum number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxanalyzedchars.php
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightMaxAnalyzedChars () {}

	/**
	 * Specifies the backup field to use
	 * @link http://www.php.net/manual/en/solrquery.sethighlightalternatefield.php
	 * @param string $field The name of the backup field
	 * @param string $field_override [optional] The name of the field we are overriding this setting for.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightAlternateField (string $field, string $field_override = null) {}

	/**
	 * Returns the highlight field to use as backup or default
	 * @link http://www.php.net/manual/en/solrquery.gethighlightalternatefield.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightAlternateField (string $field_override = null) {}

	/**
	 * Sets the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxalternatefieldlength.php
	 * @param int $fieldLength The length of the field
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAlternateFieldLength (int $fieldLength, string $field_override = null) {}

	/**
	 * Returns the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxalternatefieldlength.php
	 * @param string $field_override [optional] The name of the field
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightMaxAlternateFieldLength (string $field_override = null) {}

	/**
	 * Specify a formatter for the highlight output
	 * @link http://www.php.net/manual/en/solrquery.sethighlightformatter.php
	 * @param string $formatter Currently the only legal value is "simple"
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function setHighlightFormatter (string $formatter, string $field_override = null) {}

	/**
	 * Returns the formatter for the highlighted output
	 * @link http://www.php.net/manual/en/solrquery.gethighlightformatter.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightFormatter (string $field_override = null) {}

	/**
	 * Sets the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepre.php
	 * @param string $simplePre The text which appears before a highlighted term
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSimplePre (string $simplePre, string $field_override = null) {}

	/**
	 * Returns the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepre.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightSimplePre (string $field_override = null) {}

	/**
	 * Sets the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepost.php
	 * @param string $simplePost <p>
	 * Sets the text which appears after a highlighted term
	 * </p>
	 * <p><pre> The default is </pre></p>
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery an instance of SolrQuery.
	 */
	public function setHighlightSimplePost (string $simplePost, string $field_override = null) {}

	/**
	 * Returns the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepost.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightSimplePost (string $field_override = null) {}

	/**
	 * Sets a text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragmenter.php
	 * @param string $fragmenter The standard fragmenter is gap. Another option is regex, which tries to create fragments that resembles a certain regular expression
	 * @param string $field_override [optional] The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragmenter (string $fragmenter, string $field_override = null) {}

	/**
	 * Returns the text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragmenter.php
	 * @param string $field_override [optional] The name of the field
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightFragmenter (string $field_override = null) {}

	/**
	 * Whether to highlight phrase terms only when they appear within the query phrase
	 * @link http://www.php.net/manual/en/solrquery.sethighlightusephrasehighlighter.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightUsePhraseHighlighter (bool $flag) {}

	/**
	 * Returns the state of the hl.usePhraseHighlighter parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightusephrasehighlighter.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightUsePhraseHighlighter () {}

	/**
	 * Use SpanScorer to highlight phrase terms
	 * @link http://www.php.net/manual/en/solrquery.sethighlighthighlightmultiterm.php
	 * @param bool $flag Whether or not to use SpanScorer to highlight phrase terms only when they appear within the query phrase in the document.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightHighlightMultiTerm (bool $flag) {}

	/**
	 * Returns whether or not to enable highlighting for range/wildcard/fuzzy/prefix queries
	 * @link http://www.php.net/manual/en/solrquery.gethighlighthighlightmultiterm.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getHighlightHighlightMultiTerm () {}

	/**
	 * Sets the factor by which the regex fragmenter can stray from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexslop.php
	 * @param float $factor The factor by which the regex fragmenter can stray from the ideal fragment size
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexSlop (float $factor) {}

	/**
	 * Returns the deviation factor from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexslop.php
	 * @return float a float on success and null if not set.
	 */
	public function getHighlightRegexSlop () {}

	/**
	 * Specify the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexpattern.php
	 * @param string $value The regular expression for fragmenting. This could be used to extract sentences
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexPattern (string $value) {}

	/**
	 * Returns the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexpattern.php
	 * @return string a string on success and null if not set.
	 */
	public function getHighlightRegexPattern () {}

	/**
	 * Specify the maximum number of characters to analyze
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexmaxanalyzedchars.php
	 * @param int $maxAnalyzedChars The maximum number of characters to analyze from a field when using the regex fragmenter
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexMaxAnalyzedChars (int $maxAnalyzedChars) {}

	/**
	 * Returns the maximum number of characters from a field when using the regex fragmenter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexmaxanalyzedchars.php
	 * @return int an integer on success and null if not set.
	 */
	public function getHighlightRegexMaxAnalyzedChars () {}

	/**
	 * Enables or disables the Stats component
	 * @link http://www.php.net/manual/en/solrquery.setstats.php
	 * @param bool $flag true turns on the stats component and false disables it.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setStats (bool $flag) {}

	/**
	 * Returns whether or not stats is enabled
	 * @link http://www.php.net/manual/en/solrquery.getstats.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getStats () {}

	/**
	 * Maps to stats.field parameter
	 * @link http://www.php.net/manual/en/solrquery.addstatsfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addStatsField (string $field) {}

	/**
	 * Removes one of the stats.field parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfield.php
	 * @param string $field The name of the field.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsField (string $field) {}

	/**
	 * Returns all the statistics fields
	 * @link http://www.php.net/manual/en/solrquery.getstatsfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getStatsFields () {}

	/**
	 * Requests a return of sub results for values within the given facet
	 * @link http://www.php.net/manual/en/solrquery.addstatsfacet.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addStatsFacet (string $field) {}

	/**
	 * Removes one of the stats.facet parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfacet.php
	 * @param string $value The value
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsFacet (string $value) {}

	/**
	 * Returns all the stats facets that were set
	 * @link http://www.php.net/manual/en/solrquery.getstatsfacets.php
	 * @return array an array on success and null if not set.
	 */
	public function getStatsFacets () {}

	/**
	 * Enables or disables moreLikeThis
	 * @link http://www.php.net/manual/en/solrquery.setmlt.php
	 * @param bool $flag true enables it and false turns it off.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMlt (bool $flag) {}

	/**
	 * Returns whether or not MoreLikeThis results should be enabled
	 * @link http://www.php.net/manual/en/solrquery.getmlt.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getMlt () {}

	/**
	 * Set the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.setmltcount.php
	 * @param int $count The number of similar documents to return for each result
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltCount (int $count) {}

	/**
	 * Returns the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.getmltcount.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltCount () {}

	/**
	 * Sets a field to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.addmltfield.php
	 * @param string $field The name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addMltField (string $field) {}

	/**
	 * Removes one of the moreLikeThis fields
	 * @link http://www.php.net/manual/en/solrquery.removemltfield.php
	 * @param string $field Name of the field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeMltField (string $field) {}

	/**
	 * Returns all the fields to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.getmltfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getMltFields () {}

	/**
	 * Maps to mlt.qf
	 * @link http://www.php.net/manual/en/solrquery.addmltqueryfield.php
	 * @param string $field The name of the field
	 * @param float $boost Its boost value
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function addMltQueryField (string $field, float $boost) {}

	/**
	 * Removes one of the moreLikeThis query fields
	 * @link http://www.php.net/manual/en/solrquery.removemltqueryfield.php
	 * @param string $queryField The query field
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function removeMltQueryField (string $queryField) {}

	/**
	 * Returns the query fields and their boosts
	 * @link http://www.php.net/manual/en/solrquery.getmltqueryfields.php
	 * @return array an array on success and null if not set.
	 */
	public function getMltQueryFields () {}

	/**
	 * Sets the frequency below which terms will be ignored in the source docs
	 * @link http://www.php.net/manual/en/solrquery.setmltmintermfrequency.php
	 * @param int $minTermFrequency The frequency below which terms will be ignored in the source docs
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinTermFrequency (int $minTermFrequency) {}

	/**
	 * Returns the frequency below which terms will be ignored in the source document
	 * @link http://www.php.net/manual/en/solrquery.getmltmintermfrequency.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMinTermFrequency () {}

	/**
	 * Sets the mltMinDoc frequency
	 * @link http://www.php.net/manual/en/solrquery.setmltmindocfrequency.php
	 * @param int $minDocFrequency Sets the frequency at which words will be ignored which do not occur in at least this many docs.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinDocFrequency (int $minDocFrequency) {}

	/**
	 * Returns the treshold frequency at which words will be ignored which do not occur in at least this many docs
	 * @link http://www.php.net/manual/en/solrquery.getmltmindocfrequency.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMinDocFrequency () {}

	/**
	 * Sets the minimum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltminwordlength.php
	 * @param int $minWordLength The minimum word length below which words will be ignored
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinWordLength (int $minWordLength) {}

	/**
	 * Returns the minimum word length below which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltminwordlength.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMinWordLength () {}

	/**
	 * Sets the maximum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxwordlength.php
	 * @param int $maxWordLength The maximum word length above which words will be ignored
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxWordLength (int $maxWordLength) {}

	/**
	 * Returns the maximum word length above which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxwordlength.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMaxWordLength () {}

	/**
	 * Specifies the maximum number of tokens to parse
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumtokens.php
	 * @param int $value The maximum number of tokens to parse
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumTokens (int $value) {}

	/**
	 * Returns the maximum number of tokens to parse in each document field that is not stored with TermVector support
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumtokens.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMaxNumTokens () {}

	/**
	 * Sets the maximum number of query terms included
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumqueryterms.php
	 * @param int $value The maximum number of query terms that will be included in any generated query
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumQueryTerms (int $value) {}

	/**
	 * Returns the maximum number of query terms that will be included in any generated query
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumqueryterms.php
	 * @return int an integer on success and null if not set.
	 */
	public function getMltMaxNumQueryTerms () {}

	/**
	 * Set if the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.setmltboost.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setMltBoost (bool $flag) {}

	/**
	 * Returns whether or not the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.getmltboost.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getMltBoost () {}

	/**
	 * Enables or disables the TermsComponent
	 * @link http://www.php.net/manual/en/solrquery.setterms.php
	 * @param bool $flag true enables it. false turns it off
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTerms (bool $flag) {}

	/**
	 * Returns whether or not the TermsComponent is enabled
	 * @link http://www.php.net/manual/en/solrquery.getterms.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTerms () {}

	/**
	 * Sets the name of the field to get the Terms from
	 * @link http://www.php.net/manual/en/solrquery.settermsfield.php
	 * @param string $fieldname The field name
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsField (string $fieldname) {}

	/**
	 * Returns the field from which the terms are retrieved
	 * @link http://www.php.net/manual/en/solrquery.gettermsfield.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsField () {}

	/**
	 * Specifies the Term to start from
	 * @link http://www.php.net/manual/en/solrquery.settermslowerbound.php
	 * @param string $lowerBound The lower bound Term
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLowerBound (string $lowerBound) {}

	/**
	 * Returns the term to start at
	 * @link http://www.php.net/manual/en/solrquery.gettermslowerbound.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsLowerBound () {}

	/**
	 * Sets the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.settermsupperbound.php
	 * @param string $upperBound The term to stop at
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsUpperBound (string $upperBound) {}

	/**
	 * Returns the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.gettermsupperbound.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsUpperBound () {}

	/**
	 * Include the lower bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludelowerbound.php
	 * @param bool $flag Include the lower bound term in the result set
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeLowerBound (bool $flag) {}

	/**
	 * Returns whether or not to include the lower bound in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludelowerbound.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTermsIncludeLowerBound () {}

	/**
	 * Include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludeupperbound.php
	 * @param bool $flag true or false
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeUpperBound (bool $flag) {}

	/**
	 * Returns whether or not to include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludeupperbound.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTermsIncludeUpperBound () {}

	/**
	 * Sets the minimum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmincount.php
	 * @param int $frequency The minimum frequency
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMinCount (int $frequency) {}

	/**
	 * Returns the minimum document frequency to return in order to be included
	 * @link http://www.php.net/manual/en/solrquery.gettermsmincount.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsMinCount () {}

	/**
	 * Sets the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmaxcount.php
	 * @param int $frequency The maximum document frequency.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMaxCount (int $frequency) {}

	/**
	 * Returns the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.gettermsmaxcount.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsMaxCount () {}

	/**
	 * Restrict matches to terms that start with the prefix
	 * @link http://www.php.net/manual/en/solrquery.settermsprefix.php
	 * @param string $prefix Restrict matches to terms that start with the prefix
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsPrefix (string $prefix) {}

	/**
	 * Returns the term prefix
	 * @link http://www.php.net/manual/en/solrquery.gettermsprefix.php
	 * @return string a string on success and null if not set.
	 */
	public function getTermsPrefix () {}

	/**
	 * Sets the maximum number of terms to return
	 * @link http://www.php.net/manual/en/solrquery.settermslimit.php
	 * @param int $limit The maximum number of terms to return. All the terms will be returned if the limit is negative.
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLimit (int $limit) {}

	/**
	 * Returns the maximum number of terms Solr should return
	 * @link http://www.php.net/manual/en/solrquery.gettermslimit.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsLimit () {}

	/**
	 * Return the raw characters of the indexed term
	 * @link http://www.php.net/manual/en/solrquery.settermsreturnraw.php
	 * @param bool $flag 
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsReturnRaw (bool $flag) {}

	/**
	 * Whether or not to return raw characters
	 * @link http://www.php.net/manual/en/solrquery.gettermsreturnraw.php
	 * @return bool a boolean on success and null if not set.
	 */
	public function getTermsReturnRaw () {}

	/**
	 * Specifies how to sort the returned terms
	 * @link http://www.php.net/manual/en/solrquery.settermssort.php
	 * @param int $sortType SolrQuery::TERMS_SORT_INDEX or SolrQuery::TERMS_SORT_COUNT
	 * @return SolrQuery the current SolrQuery object, if the return value is used.
	 */
	public function setTermsSort (int $sortType) {}

	/**
	 * Returns an integer indicating how terms are sorted
	 * @link http://www.php.net/manual/en/solrquery.gettermssort.php
	 * @return int an integer on success and null if not set.
	 */
	public function getTermsSort () {}

	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name Name of the parameter
	 * @param string $value Value of the parameter
	 * @return SolrParams a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value) {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name Name of parameter
	 * @param string $value Value of parameter
	 * @return SolrParams a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value) {}

	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] Whether to return URL-encoded values
	 * @return string a string on success and false on failure.
	 */
	public function toString (bool $url_encode = null) {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array an array of non URL-encoded parameters
	 */
	public function getParams () {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] The name of the parameter
	 * @return mixed a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null) {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array an array on URL-encoded parameters
	 */
	public function getPreparedParams () {}

	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized The serialized representation of the object
	 * @return void None
	 */
	public function unserialize (string $serialized) {}

	public function __serialize () {}

	/**
	 * @param array[] $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias: SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name The name of the parameter
	 * @param string $value The value of the parameter
	 * @return SolrParams a SolrParams instance on success
	 */
	public function add (string $name, string $value) {}

	/**
	 * Alias: SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name Then name of the parameter
	 * @param string $value The parameter value
	 * @return void an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value) {}

	/**
	 * Alias: SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name Then name of the parameter
	 * @return mixed an array or string depending on the type of parameter
	 */
	public function get (string $param_name) {}

}

final class SolrExtractRequest  {
	const CAPTURE_ELEMENTS = "capture";
	const CAPTURE_ATTRIBUTES = "captureAttr";
	const COMMIT_WITHIN = "commitWithin";
	const DATE_FORMATS = "date.formats";
	const DEFAULT_FIELD = "defaultField";
	const EXTRACT_ONLY = "extractOnly";
	const EXTRACT_FORMAT = "extractFormat";
	const IGNORE_TIKA_EXCEPTION = "ignoreTikaException";
	const LITERALS_OVERRIDE = "literalsOverride";
	const LOWERNAMES = "lowernames";
	const MULTIPART_UPLOAD_LIMIT = "multipartUploadLimitInKB";
	const PASSWORD_MAP_FILE = "passwordsFile";
	const RESOURCE_NAME = "resource.name";
	const RESOURCE_PASSWORD = "resource.password";
	const TIKE_CONFIG = "tika.config";
	const UNKNOWN_FIELD_PREFIX = "uprefix";
	const XPATH_EXPRESSION = "xpath";
	const FIELD_MAPPING_PREFIX = "fmap.";
	const FIELD_BOOST_PREFIX = "boost.";
	const LITERALS_PREFIX = "literal.";

	private $_hashtable_index;


	private function __construct () {}

	public function __destruct () {}

	/**
	 * @param mixed $filename
	 * @param SolrModifiableParams $params
	 */
	public static function createFromFile ($filename = null, SolrModifiableParams &$params) {}

	/**
	 * @param mixed $content
	 * @param mixed $mime_type
	 * @param SolrModifiableParams $params
	 */
	public static function createFromStream ($content = null, $mime_type = null, SolrModifiableParams &$params) {}

	public function __clone () {}

	public function __sleep () {}

	public function __wakeup () {}

}

/**
 * @link http://www.php.net/manual/en/class.solrcollapsefunction.php
 */
class SolrCollapseFunction implements Stringable {
	const NULLPOLICY_IGNORE = "ignore";
	const NULLPOLICY_EXPAND = "expand";
	const NULLPOLICY_COLLAPSE = "collapse";

	protected $_hashtable_index;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrcollapsefunction.construct.php
	 * @param mixed $field [optional]
	 */
	public function __construct ($field = null) {}

	public function __destruct () {}

	/**
	 * Sets the field to collapse on
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setfield.php
	 * @param string $fieldName 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setField (string $fieldName) {}

	/**
	 * Returns the field that is being collapsed on
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getfield.php
	 * @return string 
	 */
	public function getField () {}

	/**
	 * Selects the group heads by the max value of a numeric field or function query
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setmax.php
	 * @param string $max 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setMax (string $max) {}

	/**
	 * Returns max parameter
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getmax.php
	 * @return string 
	 */
	public function getMax () {}

	/**
	 * Sets the initial size of the collapse data structures when collapsing on a numeric field only
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setmin.php
	 * @param string $min 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setMin (string $min) {}

	/**
	 * Returns min parameter
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getmin.php
	 * @return string 
	 */
	public function getMin () {}

	/**
	 * Sets the initial size of the collapse data structures when collapsing on a numeric field only
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setsize.php
	 * @param int $size 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setSize (int $size) {}

	/**
	 * Returns size parameter
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getsize.php
	 * @return int 
	 */
	public function getSize () {}

	/**
	 * Sets collapse hint
	 * @link http://www.php.net/manual/en/solrcollapsefunction.sethint.php
	 * @param string $hint Currently there is only one hint available "top_fc", which stands for top level FieldCache
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setHint (string $hint) {}

	/**
	 * Returns collapse hint
	 * @link http://www.php.net/manual/en/solrcollapsefunction.gethint.php
	 * @return string 
	 */
	public function getHint () {}

	/**
	 * Sets the NULL Policy
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setnullpolicy.php
	 * @param string $nullPolicy 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setNullPolicy (string $nullPolicy) {}

	/**
	 * Returns null policy
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getnullpolicy.php
	 * @return string 
	 */
	public function getNullPolicy () {}

	/**
	 * Returns a string representing the constructed collapse function
	 * @link http://www.php.net/manual/en/solrcollapsefunction.tostring.php
	 * @return string 
	 */
	public function __toString (): string {}

	public function __sleep () {}

	public function __wakeup () {}

}

/**
 * Represents a response from the Solr server.
 * @link http://www.php.net/manual/en/class.solrresponse.php
 */
abstract class SolrResponse  {
	const PARSE_SOLR_OBJ = 0;
	const PARSE_SOLR_DOC = 1;

	protected $http_status;
	protected $parser_mode;
	protected $success;
	protected $response_writer;
	protected $http_status_message;
	protected $http_request_url;
	protected $http_raw_request_headers;
	protected $http_raw_request;
	protected $http_raw_response_headers;
	protected $http_raw_response;
	protected $http_digested_response;


	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int the HTTP status of the response.
	 */
	public function getHttpStatus () {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string more details on the HTTP status
	 */
	public function getHttpStatusMessage () {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool true if it was successful and false if it was not.
	 */
	public function success () {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string the full URL the request was sent to
	 */
	public function getRequestUrl () {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders () {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string the raw request sent to the Solr server
	 */
	public function getRawRequest () {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string the raw response headers from the server.
	 */
	public function getRawResponseHeaders () {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string the raw response from the server.
	 */
	public function getRawResponse () {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string the XML response as serialized PHP data
	 */
	public function getDigestedResponse () {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] SolrResponse::PARSE_SOLR_DOC parses documents in SolrDocument instances. SolrResponse::PARSE_SOLR_OBJ parses document into SolrObjects.
	 * @return bool true on success or false on failure
	 */
	public function setParseMode (int $parser_mode = null) {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject a SolrObject representing the XML response from the server
	 */
	public function getResponse () {}

	public function getArrayResponse () {}

}

/**
 * Represents a response to a query request.
 * @link http://www.php.net/manual/en/class.solrqueryresponse.php
 */
final class SolrQueryResponse extends SolrResponse  {
	const PARSE_SOLR_OBJ = 0;
	const PARSE_SOLR_DOC = 1;

	protected $http_status;
	protected $parser_mode;
	protected $success;
	protected $response_writer;
	protected $http_status_message;
	protected $http_request_url;
	protected $http_raw_request_headers;
	protected $http_raw_request;
	protected $http_raw_response_headers;
	protected $http_raw_response;
	protected $http_digested_response;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrqueryresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrqueryresponse.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int the HTTP status of the response.
	 */
	public function getHttpStatus () {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string more details on the HTTP status
	 */
	public function getHttpStatusMessage () {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool true if it was successful and false if it was not.
	 */
	public function success () {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string the full URL the request was sent to
	 */
	public function getRequestUrl () {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders () {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string the raw request sent to the Solr server
	 */
	public function getRawRequest () {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string the raw response headers from the server.
	 */
	public function getRawResponseHeaders () {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string the raw response from the server.
	 */
	public function getRawResponse () {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string the XML response as serialized PHP data
	 */
	public function getDigestedResponse () {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] SolrResponse::PARSE_SOLR_DOC parses documents in SolrDocument instances. SolrResponse::PARSE_SOLR_OBJ parses document into SolrObjects.
	 * @return bool true on success or false on failure
	 */
	public function setParseMode (int $parser_mode = null) {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject a SolrObject representing the XML response from the server
	 */
	public function getResponse () {}

	public function getArrayResponse () {}

}

/**
 * Represents a response to an update request.
 * @link http://www.php.net/manual/en/class.solrupdateresponse.php
 */
final class SolrUpdateResponse extends SolrResponse  {
	const PARSE_SOLR_OBJ = 0;
	const PARSE_SOLR_DOC = 1;

	protected $http_status;
	protected $parser_mode;
	protected $success;
	protected $response_writer;
	protected $http_status_message;
	protected $http_request_url;
	protected $http_raw_request_headers;
	protected $http_raw_request;
	protected $http_raw_response_headers;
	protected $http_raw_response;
	protected $http_digested_response;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrupdateresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrupdateresponse.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int the HTTP status of the response.
	 */
	public function getHttpStatus () {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string more details on the HTTP status
	 */
	public function getHttpStatusMessage () {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool true if it was successful and false if it was not.
	 */
	public function success () {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string the full URL the request was sent to
	 */
	public function getRequestUrl () {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders () {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string the raw request sent to the Solr server
	 */
	public function getRawRequest () {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string the raw response headers from the server.
	 */
	public function getRawResponseHeaders () {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string the raw response from the server.
	 */
	public function getRawResponse () {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string the XML response as serialized PHP data
	 */
	public function getDigestedResponse () {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] SolrResponse::PARSE_SOLR_DOC parses documents in SolrDocument instances. SolrResponse::PARSE_SOLR_OBJ parses document into SolrObjects.
	 * @return bool true on success or false on failure
	 */
	public function setParseMode (int $parser_mode = null) {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject a SolrObject representing the XML response from the server
	 */
	public function getResponse () {}

	public function getArrayResponse () {}

}

/**
 * Represents a response to a ping request to the server
 * @link http://www.php.net/manual/en/class.solrpingresponse.php
 */
final class SolrPingResponse extends SolrResponse  {
	const PARSE_SOLR_OBJ = 0;
	const PARSE_SOLR_DOC = 1;

	protected $http_status;
	protected $parser_mode;
	protected $success;
	protected $response_writer;
	protected $http_status_message;
	protected $http_request_url;
	protected $http_raw_request_headers;
	protected $http_raw_request;
	protected $http_raw_response_headers;
	protected $http_raw_response;
	protected $http_digested_response;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrpingresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrpingresponse.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Returns the response from the server
	 * @link http://www.php.net/manual/en/solrpingresponse.getresponse.php
	 * @return string an empty string.
	 */
	public function getResponse () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int the HTTP status of the response.
	 */
	public function getHttpStatus () {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string more details on the HTTP status
	 */
	public function getHttpStatusMessage () {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool true if it was successful and false if it was not.
	 */
	public function success () {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string the full URL the request was sent to
	 */
	public function getRequestUrl () {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders () {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string the raw request sent to the Solr server
	 */
	public function getRawRequest () {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string the raw response headers from the server.
	 */
	public function getRawResponseHeaders () {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string the raw response from the server.
	 */
	public function getRawResponse () {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string the XML response as serialized PHP data
	 */
	public function getDigestedResponse () {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] SolrResponse::PARSE_SOLR_DOC parses documents in SolrDocument instances. SolrResponse::PARSE_SOLR_OBJ parses document into SolrObjects.
	 * @return bool true on success or false on failure
	 */
	public function setParseMode (int $parser_mode = null) {}

	public function getArrayResponse () {}

}

/**
 * Represents a response from the solr server.
 * @link http://www.php.net/manual/en/class.solrgenericresponse.php
 */
final class SolrGenericResponse extends SolrResponse  {
	const PARSE_SOLR_OBJ = 0;
	const PARSE_SOLR_DOC = 1;

	protected $http_status;
	protected $parser_mode;
	protected $success;
	protected $response_writer;
	protected $http_status_message;
	protected $http_request_url;
	protected $http_raw_request_headers;
	protected $http_raw_request;
	protected $http_raw_response_headers;
	protected $http_raw_response;
	protected $http_digested_response;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrgenericresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * Destructor
	 * @link http://www.php.net/manual/en/solrgenericresponse.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int the HTTP status of the response.
	 */
	public function getHttpStatus () {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string more details on the HTTP status
	 */
	public function getHttpStatusMessage () {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool true if it was successful and false if it was not.
	 */
	public function success () {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string the full URL the request was sent to
	 */
	public function getRequestUrl () {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders () {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string the raw request sent to the Solr server
	 */
	public function getRawRequest () {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string the raw response headers from the server.
	 */
	public function getRawResponseHeaders () {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string the raw response from the server.
	 */
	public function getRawResponse () {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string the XML response as serialized PHP data
	 */
	public function getDigestedResponse () {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] SolrResponse::PARSE_SOLR_DOC parses documents in SolrDocument instances. SolrResponse::PARSE_SOLR_OBJ parses document into SolrObjects.
	 * @return bool true on success or false on failure
	 */
	public function setParseMode (int $parser_mode = null) {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject a SolrObject representing the XML response from the server
	 */
	public function getResponse () {}

	public function getArrayResponse () {}

}

/**
 * Contains utility methods for retrieving the current extension version and preparing query phrases.
 * <p>Also contains method for escaping query strings and parsing XML responses.</p>
 * @link http://www.php.net/manual/en/class.solrutils.php
 */
abstract class SolrUtils  {

	/**
	 * Escapes a lucene query string
	 * @link http://www.php.net/manual/en/solrutils.escapequerychars.php
	 * @param string $str This is the query string to be escaped.
	 * @return mixed the escaped string or false on failure.
	 */
	public static function escapeQueryChars (string $str) {}

	/**
	 * Prepares a phrase from an unescaped lucene string
	 * @link http://www.php.net/manual/en/solrutils.queryphrase.php
	 * @param string $str The lucene phrase.
	 * @return string the phrase contained in double quotes.
	 */
	public static function queryPhrase (string $str) {}

	/**
	 * Parses an response XML string into a SolrObject
	 * @link http://www.php.net/manual/en/solrutils.digestxmlresponse.php
	 * @param string $xmlresponse The XML response string from the Solr server.
	 * @param int $parse_mode [optional] Use SolrResponse::PARSE_SOLR_OBJ or SolrResponse::PARSE_SOLR_DOC
	 * @return SolrObject the SolrObject representing the XML response.
	 * <p>If the parse_mode parameter is set to SolrResponse::PARSE_SOLR_OBJ Solr documents will be parses as SolrObject instances.</p>
	 * <p>If it is set to SolrResponse::PARSE_SOLR_DOC, they will be parsed as SolrDocument instances.</p>
	 */
	public static function digestXmlResponse (string $xmlresponse, int $parse_mode = null) {}

	/**
	 * @param mixed $jsonResponse
	 */
	public static function digestJsonResponse ($jsonResponse = null) {}

	/**
	 * Returns the current version of the Solr extension
	 * @link http://www.php.net/manual/en/solrutils.getsolrversion.php
	 * @return string The current version of the Apache Solr extension.
	 */
	public static function getSolrVersion () {}

	public static function getSolrStats () {}

}

/**
 * This is the base class for all exception thrown by the Solr extension classes.
 * @link http://www.php.net/manual/en/class.solrexception.php
 */
class SolrException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sourceline;
	protected $sourcefile;
	protected $zif_name;


	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrexception.getinternalinfo.php
	 * @return array an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo () {}

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

/**
 * This object is thrown when an illegal or unsupported operation is performed on an object.
 * @link http://www.php.net/manual/en/class.solrillegaloperationexception.php
 */
class SolrIllegalOperationException extends SolrException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sourceline;
	protected $sourcefile;
	protected $zif_name;


	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrillegaloperationexception.getinternalinfo.php
	 * @return array an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo () {}

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

/**
 * This object is thrown when an illegal or invalid argument is passed to a method.
 * @link http://www.php.net/manual/en/class.solrillegalargumentexception.php
 */
class SolrIllegalArgumentException extends SolrException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sourceline;
	protected $sourcefile;
	protected $zif_name;


	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrillegalargumentexception.getinternalinfo.php
	 * @return array an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo () {}

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

/**
 * An exception thrown when there is an error while making a request to the server from the client.
 * @link http://www.php.net/manual/en/class.solrclientexception.php
 */
class SolrClientException extends SolrException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sourceline;
	protected $sourcefile;
	protected $zif_name;


	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrclientexception.getinternalinfo.php
	 * @return array an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo () {}

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

/**
 * An exception thrown when there is an error produced by the Solr Server itself.
 * @link http://www.php.net/manual/en/class.solrserverexception.php
 */
class SolrServerException extends SolrException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sourceline;
	protected $sourcefile;
	protected $zif_name;


	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrserverexception.getinternalinfo.php
	 * @return array an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo () {}

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

/**
 * @link http://www.php.net/manual/en/class.solrmissingmandatoryparameterexception.php
 */
class SolrMissingMandatoryParameterException extends SolrException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	protected $sourceline;
	protected $sourcefile;
	protected $zif_name;


	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrexception.getinternalinfo.php
	 * @return array an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo () {}

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

/**
 * Returns the current version of the Apache Solr extension
 * @link http://www.php.net/manual/en/function.solr-get-version.php
 * @return string It returns a string on success and false on failure.
 */
function solr_get_version () {}


/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 */
define ('SOLR_MAJOR_VERSION', 2);

/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 */
define ('SOLR_MINOR_VERSION', 6);

/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 */
define ('SOLR_PATCH_VERSION', 0);

/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 */
define ('SOLR_EXTENSION_VERSION', "2.6.0");

// End of solr v.2.6.0
