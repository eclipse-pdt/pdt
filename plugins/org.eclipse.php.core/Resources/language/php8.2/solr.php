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
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Sets the value for a property
	 * @link http://www.php.net/manual/en/solrobject.offsetset.php
	 * @param string $property_name 
	 * @param string $property_value 
	 * @return void None.
	 */
	public function offsetSet (string $property_name, string $property_value): void {}

	/**
	 * Used to retrieve a property
	 * @link http://www.php.net/manual/en/solrobject.offsetget.php
	 * @param string $property_name 
	 * @return mixed Returns the property value.
	 */
	public function offsetGet (string $property_name): mixed {}

	/**
	 * Checks if the property exists
	 * @link http://www.php.net/manual/en/solrobject.offsetexists.php
	 * @param string $property_name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function offsetExists (string $property_name): bool {}

	/**
	 * Unsets the value for the property
	 * @link http://www.php.net/manual/en/solrobject.offsetunset.php
	 * @param string $property_name 
	 * @return void Returns true on success or false on failure.
	 */
	public function offsetUnset (string $property_name): void {}

	/**
	 * Returns an array of all the names of the properties
	 * @link http://www.php.net/manual/en/solrobject.getpropertynames.php
	 * @return array Returns an array.
	 */
	public function getPropertyNames (): array {}

}

/**
 * Represents a Solr document retrieved from a query response.
 * @link http://www.php.net/manual/en/class.solrdocument.php
 */
final class SolrDocument implements ArrayAccess, Iterator, Traversable, Serializable {
	/**
	 * Default mode for sorting fields within the document.
	const SORT_DEFAULT = 1;
	/**
	 * Sorts the fields in ascending order
	const SORT_ASC = 1;
	/**
	 * Sorts the fields in descending order
	const SORT_DESC = 2;
	/**
	 * Sorts the fields by field name.
	const SORT_FIELD_NAME = 1;
	/**
	 * Sorts the fields by number of values in each field.
	const SORT_FIELD_VALUE_COUNT = 2;
	/**
	 * Sorts the fields by thier boost values.
	const SORT_FIELD_BOOST_VALUE = 4;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrdocument.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Creates a copy of a SolrDocument object
	 * @link http://www.php.net/manual/en/solrdocument.clone.php
	 * @return void None.
	 */
	public function __clone (): void {}

	/**
	 * Adds another field to the document
	 * @link http://www.php.net/manual/en/solrdocument.set.php
	 * @param string $fieldName 
	 * @param string $fieldValue 
	 * @return bool Returns true on success or false on failure.
	 */
	public function __set (string $fieldName, string $fieldValue): bool {}

	/**
	 * Access the field as a property
	 * @link http://www.php.net/manual/en/solrdocument.get.php
	 * @param string $fieldName 
	 * @return SolrDocumentField Returns a SolrDocumentField instance.
	 */
	public function __get (string $fieldName): SolrDocumentField {}

	/**
	 * Checks if a field exists
	 * @link http://www.php.net/manual/en/solrdocument.isset.php
	 * @param string $fieldName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function __isset (string $fieldName): bool {}

	/**
	 * Removes a field from the document
	 * @link http://www.php.net/manual/en/solrdocument.unset.php
	 * @param string $fieldName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function __unset (string $fieldName): bool {}

	/**
	 * Adds a field to the document
	 * @link http://www.php.net/manual/en/solrdocument.offsetset.php
	 * @param string $fieldName 
	 * @param string $fieldValue 
	 * @return void Returns true on success or false on failure.
	 */
	public function offsetSet (string $fieldName, string $fieldValue): void {}

	/**
	 * Retrieves a field
	 * @link http://www.php.net/manual/en/solrdocument.offsetget.php
	 * @param string $fieldName 
	 * @return SolrDocumentField Returns a SolrDocumentField object.
	 */
	public function offsetGet (string $fieldName): SolrDocumentField {}

	/**
	 * Checks if a particular field exists
	 * @link http://www.php.net/manual/en/solrdocument.offsetexists.php
	 * @param string $fieldName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function offsetExists (string $fieldName): bool {}

	/**
	 * Removes a field
	 * @link http://www.php.net/manual/en/solrdocument.offsetunset.php
	 * @param string $fieldName 
	 * @return void No return value.
	 */
	public function offsetUnset (string $fieldName): void {}

	/**
	 * Retrieves the current field
	 * @link http://www.php.net/manual/en/solrdocument.current.php
	 * @return SolrDocumentField Returns the field
	 */
	public function current (): SolrDocumentField {}

	/**
	 * Retrieves the current key
	 * @link http://www.php.net/manual/en/solrdocument.key.php
	 * @return string Returns the current key.
	 */
	public function key (): string {}

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
	 * @return bool Returns true on success and false if the current position is no longer valid.
	 */
	public function valid (): bool {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrdocument.serialize.php
	 * @return string Returns a string representing the serialized Solr document.
	 */
	public function serialize (): string {}

	/**
	 * Custom serialization of SolrDocument objects
	 * @link http://www.php.net/manual/en/solrdocument.unserialize.php
	 * @param string $serialized 
	 * @return void None.
	 */
	public function unserialize (string $serialized): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Drops all the fields in the document
	 * @link http://www.php.net/manual/en/solrdocument.clear.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function clear (): bool {}

	/**
	 * Alias of SolrDocument::clear
	 * @link http://www.php.net/manual/en/solrdocument.reset.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function reset (): bool {}

	/**
	 * Adds a field to the document
	 * @link http://www.php.net/manual/en/solrdocument.addfield.php
	 * @param string $fieldName 
	 * @param string $fieldValue 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addField (string $fieldName, string $fieldValue): bool {}

	/**
	 * Returns an array of fields names in the document
	 * @link http://www.php.net/manual/en/solrdocument.getfieldnames.php
	 * @return array Returns an array containing the names of the fields in this document.
	 */
	public function getFieldNames (): array {}

	/**
	 * Returns the number of fields in this document
	 * @link http://www.php.net/manual/en/solrdocument.getfieldcount.php
	 * @return int Returns an integer on success and false on failure.
	 */
	public function getFieldCount (): int {}

	/**
	 * Retrieves a field by name
	 * @link http://www.php.net/manual/en/solrdocument.getfield.php
	 * @param string $fieldName 
	 * @return SolrDocumentField Returns a SolrDocumentField on success and false on failure.
	 */
	public function getField (string $fieldName): SolrDocumentField {}

	/**
	 * Returns an array representation of the document
	 * @link http://www.php.net/manual/en/solrdocument.toarray.php
	 * @return array Returns an array representation of the document.
	 */
	public function toArray (): array {}

	/**
	 * Checks if a field exists in the document
	 * @link http://www.php.net/manual/en/solrdocument.fieldexists.php
	 * @param string $fieldName 
	 * @return bool Returns true if the field is present and false if it does not.
	 */
	public function fieldExists (string $fieldName): bool {}

	/**
	 * Removes a field from the document
	 * @link http://www.php.net/manual/en/solrdocument.deletefield.php
	 * @param string $fieldName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteField (string $fieldName): bool {}

	/**
	 * Sorts the fields in the document
	 * @link http://www.php.net/manual/en/solrdocument.sort.php
	 * @param int $sortOrderBy 
	 * @param int $sortDirection [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sort (int $sortOrderBy, int $sortDirection = \SolrDocument::SORT_ASC): bool {}

	/**
	 * Merges source to the current SolrDocument
	 * @link http://www.php.net/manual/en/solrdocument.merge.php
	 * @param SolrDocument $sourceDoc 
	 * @param bool $overwrite [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function merge (SolrDocument $sourceDoc, bool $overwrite = true): bool {}

	/**
	 * Returns a SolrInputDocument equivalent of the object
	 * @link http://www.php.net/manual/en/solrdocument.getinputdocument.php
	 * @return SolrInputDocument Returns a SolrInputDocument on success and null on failure.
	 */
	public function getInputDocument (): SolrInputDocument {}

	/**
	 * Returns the number of child documents
	 * @link http://www.php.net/manual/en/solrdocument.getchilddocumentscount.php
	 * @return int 
	 */
	public function getChildDocumentsCount (): int {}

	/**
	 * Checks whether the document has any child documents
	 * @link http://www.php.net/manual/en/solrdocument.haschilddocuments.php
	 * @return bool 
	 */
	public function hasChildDocuments (): bool {}

	/**
	 * Returns an array of child documents (SolrDocument)
	 * @link http://www.php.net/manual/en/solrdocument.getchilddocuments.php
	 * @return array 
	 */
	public function getChildDocuments (): array {}

}

/**
 * This represents a field in a Solr document. All its properties are read-only.
 * @link http://www.php.net/manual/en/class.solrdocumentfield.php
 */
final class SolrDocumentField  {

	/**
	 * The name of the field.
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrdocumentfield.php#solrdocumentfield.props.name
	 */
	public readonly string $name;

	/**
	 * The boost value for the field
	 * @var float
	 * @link http://www.php.net/manual/en/class.solrdocumentfield.php#solrdocumentfield.props.boost
	 */
	public readonly float $boost;

	/**
	 * An array of values for this field
	 * @var array
	 * @link http://www.php.net/manual/en/class.solrdocumentfield.php#solrdocumentfield.props.values
	 */
	public readonly array $values;

	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrdocumentfield.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

}

/**
 * This class represents a Solr document that is about to be submitted to the Solr index.
 * @link http://www.php.net/manual/en/class.solrinputdocument.php
 */
final class SolrInputDocument  {
	/**
	 * Sorts the fields in ascending order.
	const SORT_DEFAULT = 1;
	/**
	 * Sorts the fields in ascending order.
	const SORT_ASC = 1;
	/**
	 * Sorts the fields in descending order.
	const SORT_DESC = 2;
	/**
	 * Sorts the fields by name
	const SORT_FIELD_NAME = 1;
	/**
	 * Sorts the fields by number of values.
	const SORT_FIELD_VALUE_COUNT = 2;
	/**
	 * Sorts the fields by boost value.
	const SORT_FIELD_BOOST_VALUE = 4;
	const UPDATE_MODIFIER_ADD = 1;
	const UPDATE_MODIFIER_SET = 2;
	const UPDATE_MODIFIER_INC = 3;
	const UPDATE_MODIFIER_REMOVE = 4;
	const UPDATE_MODIFIER_REMOVEREGEX = 5;
	const VERSION_ASSERT_NONE = 0;
	const VERSION_ASSERT_EXISTS = 1;
	const VERSION_ASSERT_NOT_EXISTS = -1;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrinputdocument.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Creates a copy of a SolrDocument
	 * @link http://www.php.net/manual/en/solrinputdocument.clone.php
	 * @return void Creates a new SolrInputDocument instance.
	 */
	public function __clone (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Sets the boost value for this document
	 * @link http://www.php.net/manual/en/solrinputdocument.setboost.php
	 * @param float $documentBoostValue 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setBoost (float $documentBoostValue): bool {}

	/**
	 * Retrieves the current boost value for the document
	 * @link http://www.php.net/manual/en/solrinputdocument.getboost.php
	 * @return float Returns the boost value on success and false on failure.
	 */
	public function getBoost (): float {}

	/**
	 * Resets the input document
	 * @link http://www.php.net/manual/en/solrinputdocument.clear.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function clear (): bool {}

	/**
	 * Alias of SolrInputDocument::clear
	 * @link http://www.php.net/manual/en/solrinputdocument.reset.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function reset (): bool {}

	/**
	 * Adds a field to the document
	 * @link http://www.php.net/manual/en/solrinputdocument.addfield.php
	 * @param string $fieldName 
	 * @param string $fieldValue 
	 * @param float $fieldBoostValue [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addField (string $fieldName, string $fieldValue, float $fieldBoostValue = 0.0): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $fieldName
	 * @param mixed $modifier
	 * @param mixed $value
	 */
	public function updateField ($fieldName = null, $modifier = null, $value = null) {}

	/**
	 * Retrieves the boost value for a particular field
	 * @link http://www.php.net/manual/en/solrinputdocument.getfieldboost.php
	 * @param string $fieldName 
	 * @return float Returns the boost value for the field or false if there was an error.
	 */
	public function getFieldBoost (string $fieldName): float {}

	/**
	 * Sets the index-time boost value for a field
	 * @link http://www.php.net/manual/en/solrinputdocument.setfieldboost.php
	 * @param string $fieldName 
	 * @param float $fieldBoostValue 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setFieldBoost (string $fieldName, float $fieldBoostValue): bool {}

	/**
	 * Returns an array containing all the fields in the document
	 * @link http://www.php.net/manual/en/solrinputdocument.getfieldnames.php
	 * @return array Returns an array on success and false on failure.
	 */
	public function getFieldNames (): array {}

	/**
	 * Returns the number of fields in the document
	 * @link http://www.php.net/manual/en/solrinputdocument.getfieldcount.php
	 * @return int|false Returns an integer on success or false on failure.
	 */
	public function getFieldCount (): int|false {}

	/**
	 * Retrieves a field by name
	 * @link http://www.php.net/manual/en/solrinputdocument.getfield.php
	 * @param string $fieldName 
	 * @return SolrDocumentField Returns a SolrDocumentField object on success and false on failure.
	 */
	public function getField (string $fieldName): SolrDocumentField {}

	/**
	 * Returns an array representation of the input document
	 * @link http://www.php.net/manual/en/solrinputdocument.toarray.php
	 * @return array Returns an array containing the fields. It returns false on failure.
	 */
	public function toArray (): array {}

	/**
	 * Checks if a field exists
	 * @link http://www.php.net/manual/en/solrinputdocument.fieldexists.php
	 * @param string $fieldName 
	 * @return bool Returns true if the field was found and false if it was not found.
	 */
	public function fieldExists (string $fieldName): bool {}

	/**
	 * Removes a field from the document
	 * @link http://www.php.net/manual/en/solrinputdocument.deletefield.php
	 * @param string $fieldName 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteField (string $fieldName): bool {}

	/**
	 * Sorts the fields within the document
	 * @link http://www.php.net/manual/en/solrinputdocument.sort.php
	 * @param int $sortOrderBy 
	 * @param int $sortDirection [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function sort (int $sortOrderBy, int $sortDirection = \SolrInputDocument::SORT_ASC): bool {}

	/**
	 * Merges one input document into another
	 * @link http://www.php.net/manual/en/solrinputdocument.merge.php
	 * @param SolrInputDocument $sourceDoc 
	 * @param bool $overwrite [optional] 
	 * @return bool Returns true on success or false on failure. In the future, this will be modified to return the number of fields in the new document.
	 */
	public function merge (SolrInputDocument $sourceDoc, bool $overwrite = true): bool {}

	/**
	 * Adds a child document for block indexing
	 * @link http://www.php.net/manual/en/solrinputdocument.addchilddocument.php
	 * @param SolrInputDocument $child A SolrInputDocument object.
	 * @return void No value is returned.
	 */
	public function addChildDocument (SolrInputDocument $child): void {}

	/**
	 * Returns an array of child documents (SolrInputDocument)
	 * @link http://www.php.net/manual/en/solrinputdocument.getchilddocuments.php
	 * @return array 
	 */
	public function getChildDocuments (): array {}

	/**
	 * Returns true if the document has any child documents
	 * @link http://www.php.net/manual/en/solrinputdocument.haschilddocuments.php
	 * @return bool 
	 */
	public function hasChildDocuments (): bool {}

	/**
	 * Returns the number of child documents
	 * @link http://www.php.net/manual/en/solrinputdocument.getchilddocumentscount.php
	 * @return int 
	 */
	public function getChildDocumentsCount (): int {}

	/**
	 * Adds an array of child documents
	 * @link http://www.php.net/manual/en/solrinputdocument.addchilddocuments.php
	 * @param array $docs An array of SolrInputDocument objects.
	 * @return void No value is returned.
	 */
	public function addChildDocuments (array &$docs): void {}

	/**
	 * {@inheritdoc}
	 * @param mixed $version
	 */
	public function setVersion ($version = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getVersion () {}

}

/**
 * Used to send requests to a Solr server. Currently, cloning and serialization of SolrClient instances is not supported.
 * @link http://www.php.net/manual/en/class.solrclient.php
 */
class SolrClient  {
	/**
	 * Used when updating the search servlet.
	const SEARCH_SERVLET_TYPE = 1;
	/**
	 * Used when updating the update servlet.
	const UPDATE_SERVLET_TYPE = 2;
	/**
	 * Used when updating the threads servlet.
	const THREADS_SERVLET_TYPE = 4;
	/**
	 * Used when updating the ping servlet.
	const PING_SERVLET_TYPE = 8;
	/**
	 * Used when updating the terms servlet.
	const TERMS_SERVLET_TYPE = 16;
	/**
	 * Used when retrieving system information from the system servlet.
	const SYSTEM_SERVLET_TYPE = 32;
	/**
	 * This is the initial value for the search servlet.
	const DEFAULT_SEARCH_SERVLET = "select";
	/**
	 * This is the initial value for the update servlet.
	const DEFAULT_UPDATE_SERVLET = "update";
	/**
	 * This is the initial value for the threads servlet.
	const DEFAULT_THREADS_SERVLET = "admin/threads";
	/**
	 * This is the initial value for the ping servlet.
	const DEFAULT_PING_SERVLET = "admin/ping";
	/**
	 * This is the initial value for the terms servlet used for the TermsComponent
	const DEFAULT_TERMS_SERVLET = "terms";
	/**
	 * This is the initial value for the system servlet used to obtain Solr Server information
	const DEFAULT_SYSTEM_SERVLET = "admin/system";


	/**
	 * Constructor for the SolrClient object
	 * @link http://www.php.net/manual/en/solrclient.construct.php
	 * @param array $clientOptions 
	 * @return array 
	 */
	public function __construct (array $clientOptions): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	public function __clone () {}

	/**
	 * Returns the client options set internally
	 * @link http://www.php.net/manual/en/solrclient.getoptions.php
	 * @return array Returns an array containing all the options for the SolrClient object set internally.
	 */
	public function getOptions (): array {}

	/**
	 * Returns the debug data for the last connection attempt
	 * @link http://www.php.net/manual/en/solrclient.getdebug.php
	 * @return string Returns a string on success and null if there is nothing to return.
	 */
	public function getDebug (): string {}

	/**
	 * Changes the specified servlet type to a new value
	 * @link http://www.php.net/manual/en/solrclient.setservlet.php
	 * @param int $type 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setServlet (int $type, string $value): bool {}

	/**
	 * Sends a query to the server
	 * @link http://www.php.net/manual/en/solrclient.query.php
	 * @param SolrParams $query 
	 * @return SolrQueryResponse Returns a SolrQueryResponse object on success and throws an exception on failure.
	 */
	public function query (SolrParams $query): SolrQueryResponse {}

	/**
	 * Adds a document to the index
	 * @link http://www.php.net/manual/en/solrclient.adddocument.php
	 * @param SolrInputDocument $doc 
	 * @param bool $overwrite [optional] 
	 * @param int $commitWithin [optional] 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse object or throws an Exception on failure.
	 */
	public function addDocument (SolrInputDocument $doc, bool $overwrite = true, int $commitWithin = null): SolrUpdateResponse {}

	/**
	 * Adds a collection of SolrInputDocument instances to the index
	 * @link http://www.php.net/manual/en/solrclient.adddocuments.php
	 * @param array $docs 
	 * @param bool $overwrite [optional] 
	 * @param int $commitWithin [optional] 
	 * @return void Returns a SolrUpdateResponse object or throws an exception on failure.
	 */
	public function addDocuments (array $docs, bool $overwrite = true, int $commitWithin = null): void {}

	/**
	 * {@inheritdoc}
	 * @param SolrExtractRequest $request
	 */
	public function sendUpdateStream (SolrExtractRequest &$request) {}

	/**
	 * Sends a raw update request
	 * @link http://www.php.net/manual/en/solrclient.request.php
	 * @param string $raw_request 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success. Throws an exception on failure.
	 */
	public function request (string $raw_request): SolrUpdateResponse {}

	/**
	 * Sets the response writer used to prepare the response from Solr
	 * @link http://www.php.net/manual/en/solrclient.setresponsewriter.php
	 * @param string $responseWriter 
	 * @return void No value is returned.
	 */
	public function setResponseWriter (string $responseWriter): void {}

	/**
	 * Delete by Id
	 * @link http://www.php.net/manual/en/solrclient.deletebyid.php
	 * @param string $id 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success and throws an exception on failure.
	 */
	public function deleteById (string $id): SolrUpdateResponse {}

	/**
	 * Deletes by Ids
	 * @link http://www.php.net/manual/en/solrclient.deletebyids.php
	 * @param array $ids 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success and throws an exception on failure.
	 */
	public function deleteByIds (array $ids): SolrUpdateResponse {}

	/**
	 * Deletes all documents matching the given query
	 * @link http://www.php.net/manual/en/solrclient.deletebyquery.php
	 * @param string $query 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success and throws an exception on failure.
	 */
	public function deleteByQuery (string $query): SolrUpdateResponse {}

	/**
	 * Removes all documents matching any of the queries
	 * @link http://www.php.net/manual/en/solrclient.deletebyqueries.php
	 * @param array $queries 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success and throws a SolrClientException on failure.
	 */
	public function deleteByQueries (array $queries): SolrUpdateResponse {}

	/**
	 * Get Document By Id. Utilizes Solr Realtime Get (RTG)
	 * @link http://www.php.net/manual/en/solrclient.getbyid.php
	 * @param string $id Document ID
	 * @return SolrQueryResponse SolrQueryResponse
	 */
	public function getById (string $id): SolrQueryResponse {}

	/**
	 * Get Documents by their Ids. Utilizes Solr Realtime Get (RTG)
	 * @link http://www.php.net/manual/en/solrclient.getbyids.php
	 * @param array $ids Document ids
	 * @return SolrQueryResponse SolrQueryResponse
	 */
	public function getByIds (array $ids): SolrQueryResponse {}

	/**
	 * Finalizes all add/deletes made to the index
	 * @link http://www.php.net/manual/en/solrclient.commit.php
	 * @param bool $softCommit [optional] 
	 * @param bool $waitSearcher [optional] 
	 * @param bool $expungeDeletes [optional] 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse object on success or throws an exception on failure.
	 */
	public function commit (bool $softCommit = false, bool $waitSearcher = true, bool $expungeDeletes = false): SolrUpdateResponse {}

	/**
	 * Defragments the index
	 * @link http://www.php.net/manual/en/solrclient.optimize.php
	 * @param int $maxSegments [optional] 
	 * @param bool $softCommit [optional] 
	 * @param bool $waitSearcher [optional] 
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success or throws an exception on failure.
	 */
	public function optimize (int $maxSegments = 1, bool $softCommit = true, bool $waitSearcher = true): SolrUpdateResponse {}

	/**
	 * Rollbacks all add/deletes made to the index since the last commit
	 * @link http://www.php.net/manual/en/solrclient.rollback.php
	 * @return SolrUpdateResponse Returns a SolrUpdateResponse on success or throws a SolrClientException on failure.
	 */
	public function rollback (): SolrUpdateResponse {}

	/**
	 * Checks if Solr server is still up
	 * @link http://www.php.net/manual/en/solrclient.ping.php
	 * @return SolrPingResponse Returns a SolrPingResponse object on success and throws an exception on failure.
	 */
	public function ping (): SolrPingResponse {}

	/**
	 * Retrieve Solr Server information
	 * @link http://www.php.net/manual/en/solrclient.system.php
	 * @return void Returns a SolrGenericResponse object on success.
	 */
	public function system (): void {}

	/**
	 * Checks the threads status
	 * @link http://www.php.net/manual/en/solrclient.threads.php
	 * @return void Returns a SolrGenericResponse object.
	 */
	public function threads (): void {}

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
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value): SolrParams {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value): SolrParams {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] 
	 * @return string Returns a string on success and false on failure.
	 */
	public function toString (bool $url_encode = false): string {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array Returns an array of non URL-encoded parameters
	 */
	public function getParams (): array {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] 
	 * @return mixed Returns a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null): mixed {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array Returns an array on URL-encoded parameters
	 */
	public function getPreparedParams (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize (): string {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized 
	 * @return void None
	 */
	public function unserialize (string $serialized): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias of SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParams instance on success
	 */
	public function add (string $name, string $value): SolrParams {}

	/**
	 * Alias of SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name 
	 * @param string $value 
	 * @return void Returns an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value): void {}

	/**
	 * Alias of SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name 
	 * @return mixed Returns an array or string depending on the type of parameter
	 */
	public function get (string $param_name): mixed {}

}

/**
 * Represents a collection of name-value pairs sent to the Solr server during a request.
 * @link http://www.php.net/manual/en/class.solrmodifiableparams.php
 */
class SolrModifiableParams extends SolrParams implements Serializable, Stringable {

	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrmodifiableparams.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value): SolrParams {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value): SolrParams {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] 
	 * @return string Returns a string on success and false on failure.
	 */
	public function toString (bool $url_encode = false): string {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array Returns an array of non URL-encoded parameters
	 */
	public function getParams (): array {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] 
	 * @return mixed Returns a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null): mixed {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array Returns an array on URL-encoded parameters
	 */
	public function getPreparedParams (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize (): string {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized 
	 * @return void None
	 */
	public function unserialize (string $serialized): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias of SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParams instance on success
	 */
	public function add (string $name, string $value): SolrParams {}

	/**
	 * Alias of SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name 
	 * @param string $value 
	 * @return void Returns an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value): void {}

	/**
	 * Alias of SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name 
	 * @return mixed Returns an array or string depending on the type of parameter
	 */
	public function get (string $param_name): mixed {}

}

/**
 * Represents a collection of name-value pairs sent to the Solr server during a request.
 * @link http://www.php.net/manual/en/class.solrquery.php
 */
class SolrQuery extends SolrModifiableParams implements Stringable, Serializable {
	/**
	 * Used to specify that the sorting should be in acending order
	const ORDER_ASC = 0;
	/**
	 * Used to specify that the sorting should be in descending order
	const ORDER_DESC = 1;
	/**
	 * Used to specify that the facet should sort by index
	const FACET_SORT_INDEX = 0;
	/**
	 * Used to specify that the facet should sort by count
	const FACET_SORT_COUNT = 1;
	/**
	 * Used in the TermsComponent
	const TERMS_SORT_INDEX = 0;
	/**
	 * Used in the TermsComponent
	const TERMS_SORT_COUNT = 1;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrquery.construct.php
	 * @param string $q [optional] 
	 * @return string None
	 */
	public function __construct (string $q = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Sets the search query
	 * @link http://www.php.net/manual/en/solrquery.setquery.php
	 * @param string $query 
	 * @return SolrQuery Returns the current SolrQuery object
	 */
	public function setQuery (string $query): SolrQuery {}

	/**
	 * Returns the main query
	 * @link http://www.php.net/manual/en/solrquery.getquery.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getQuery (): string {}

	/**
	 * Specifies the number of rows to skip
	 * @link http://www.php.net/manual/en/solrquery.setstart.php
	 * @param int $start 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function setStart (int $start): SolrQuery {}

	/**
	 * Returns the offset in the complete result set
	 * @link http://www.php.net/manual/en/solrquery.getstart.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getStart (): int {}

	/**
	 * Specifies the maximum number of rows to return in the result
	 * @link http://www.php.net/manual/en/solrquery.setrows.php
	 * @param int $rows 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function setRows (int $rows): SolrQuery {}

	/**
	 * Returns the maximum number of documents
	 * @link http://www.php.net/manual/en/solrquery.getrows.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getRows (): int {}

	/**
	 * Specifies which fields to return in the result
	 * @link http://www.php.net/manual/en/solrquery.addfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object
	 */
	public function addField (string $field): SolrQuery {}

	/**
	 * Removes a field from the list of fields
	 * @link http://www.php.net/manual/en/solrquery.removefield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeField (string $field): SolrQuery {}

	/**
	 * Returns the list of fields that will be returned in the response
	 * @link http://www.php.net/manual/en/solrquery.getfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFields (): array {}

	/**
	 * Used to control how the results should be sorted
	 * @link http://www.php.net/manual/en/solrquery.addsortfield.php
	 * @param string $field 
	 * @param int $order [optional] 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function addSortField (string $field, int $order = \SolrQuery::ORDER_DESC): SolrQuery {}

	/**
	 * Removes one of the sort fields
	 * @link http://www.php.net/manual/en/solrquery.removesortfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeSortField (string $field): SolrQuery {}

	/**
	 * Returns all the sort fields
	 * @link http://www.php.net/manual/en/solrquery.getsortfields.php
	 * @return array Returns an array on success and null if none of the parameters was set.
	 */
	public function getSortFields (): array {}

	/**
	 * Specifies a filter query
	 * @link http://www.php.net/manual/en/solrquery.addfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function addFilterQuery (string $fq): SolrQuery {}

	/**
	 * Removes a filter query
	 * @link http://www.php.net/manual/en/solrquery.removefilterquery.php
	 * @param string $fq 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFilterQuery (string $fq): SolrQuery {}

	/**
	 * Returns an array of filter queries
	 * @link http://www.php.net/manual/en/solrquery.getfilterqueries.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFilterQueries (): array {}

	/**
	 * Flag to show debug information
	 * @link http://www.php.net/manual/en/solrquery.setshowdebuginfo.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setShowDebugInfo (bool $flag): SolrQuery {}

	/**
	 * Sets the explainOther common query parameter
	 * @link http://www.php.net/manual/en/solrquery.setexplainother.php
	 * @param string $query 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setExplainOther (string $query): SolrQuery {}

	/**
	 * The time allowed for search to finish
	 * @link http://www.php.net/manual/en/solrquery.settimeallowed.php
	 * @param int $timeAllowed 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTimeAllowed (int $timeAllowed): SolrQuery {}

	/**
	 * Returns the time in milliseconds allowed for the query to finish
	 * @link http://www.php.net/manual/en/solrquery.gettimeallowed.php
	 * @return int Returns and integer on success and null if it is not set.
	 */
	public function getTimeAllowed (): int {}

	/**
	 * Exclude the header from the returned results
	 * @link http://www.php.net/manual/en/solrquery.setomitheader.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setOmitHeader (bool $flag): SolrQuery {}

	/**
	 * Toggles the echoHandler parameter
	 * @link http://www.php.net/manual/en/solrquery.setechohandler.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setEchoHandler (bool $flag): SolrQuery {}

	/**
	 * Determines what kind of parameters to include in the response
	 * @link http://www.php.net/manual/en/solrquery.setechoparams.php
	 * @param string $type 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setEchoParams (string $type): SolrQuery {}

	/**
	 * Maps to the facet parameter. Enables or disables facetting
	 * @link http://www.php.net/manual/en/solrquery.setfacet.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacet (bool $flag): SolrQuery {}

	/**
	 * Returns the value of the facet parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacet.php
	 * @return bool Returns a boolean on success and null if not set
	 */
	public function getFacet (): bool {}

	/**
	 * Adds another field to the facet
	 * @link http://www.php.net/manual/en/solrquery.addfacetfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addFacetField (string $field): SolrQuery {}

	/**
	 * Removes one of the facet.date parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetField (string $field): SolrQuery {}

	/**
	 * Returns all the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetfields.php
	 * @return array Returns an array of all the fields and null if none was set
	 */
	public function getFacetFields (): array {}

	/**
	 * Adds a facet query
	 * @link http://www.php.net/manual/en/solrquery.addfacetquery.php
	 * @param string $facetQuery 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addFacetQuery (string $facetQuery): SolrQuery {}

	/**
	 * Removes one of the facet.query parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetquery.php
	 * @param string $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetQuery (string $value): SolrQuery {}

	/**
	 * Returns all the facet queries
	 * @link http://www.php.net/manual/en/solrquery.getfacetqueries.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFacetQueries (): array {}

	/**
	 * Specifies a string prefix with which to limits the terms on which to facet
	 * @link http://www.php.net/manual/en/solrquery.setfacetprefix.php
	 * @param string $prefix 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetPrefix (string $prefix, string $field_override = null): SolrQuery {}

	/**
	 * Returns the facet prefix
	 * @link http://www.php.net/manual/en/solrquery.getfacetprefix.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getFacetPrefix (string $field_override = null): string {}

	/**
	 * Determines the ordering of the facet field constraints
	 * @link http://www.php.net/manual/en/solrquery.setfacetsort.php
	 * @param int $facetSort 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetSort (int $facetSort, string $field_override = null): SolrQuery {}

	/**
	 * Returns the facet sort type
	 * @link http://www.php.net/manual/en/solrquery.getfacetsort.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer (SolrQuery::FACET_SORT_INDEX or SolrQuery::FACET_SORT_COUNT) on success or null if not set.
	 */
	public function getFacetSort (string $field_override = null): int {}

	/**
	 * Maps to facet.limit
	 * @link http://www.php.net/manual/en/solrquery.setfacetlimit.php
	 * @param int $limit 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetLimit (int $limit, string $field_override = null): SolrQuery {}

	/**
	 * Returns the maximum number of constraint counts that should be returned for the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetlimit.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set
	 */
	public function getFacetLimit (string $field_override = null): int {}

	/**
	 * Sets the offset into the list of constraints to allow for pagination
	 * @link http://www.php.net/manual/en/solrquery.setfacetoffset.php
	 * @param int $offset 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetOffset (int $offset, string $field_override = null): SolrQuery {}

	/**
	 * Returns an offset into the list of constraints to be used for pagination
	 * @link http://www.php.net/manual/en/solrquery.getfacetoffset.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set
	 */
	public function getFacetOffset (string $field_override = null): int {}

	/**
	 * Maps to facet.mincount
	 * @link http://www.php.net/manual/en/solrquery.setfacetmincount.php
	 * @param int $mincount 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMinCount (int $mincount, string $field_override = null): SolrQuery {}

	/**
	 * Returns the minimum counts for facet fields should be included in the response
	 * @link http://www.php.net/manual/en/solrquery.getfacetmincount.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set
	 */
	public function getFacetMinCount (string $field_override = null): int {}

	/**
	 * Maps to facet.missing
	 * @link http://www.php.net/manual/en/solrquery.setfacetmissing.php
	 * @param bool $flag 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMissing (bool $flag, string $field_override = null): SolrQuery {}

	/**
	 * Returns the current state of the facet.missing parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmissing.php
	 * @param string $field_override [optional] 
	 * @return bool Returns a boolean on success and null if not set
	 */
	public function getFacetMissing (string $field_override = null): bool {}

	/**
	 * Specifies the type of algorithm to use when faceting a field
	 * @link http://www.php.net/manual/en/solrquery.setfacetmethod.php
	 * @param string $method 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMethod (string $method, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value of the facet.method parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmethod.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetMethod (string $field_override = null): string {}

	/**
	 * Sets the minimum document frequency used for determining term count
	 * @link http://www.php.net/manual/en/solrquery.setfacetenumcachemindefaultfrequency.php
	 * @param int $frequency 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetEnumCacheMinDefaultFrequency (int $frequency, string $field_override = null): SolrQuery {}

	/**
	 * Maps to facet.date
	 * @link http://www.php.net/manual/en/solrquery.addfacetdatefield.php
	 * @param string $dateField 
	 * @return SolrQuery Returns a SolrQuery object.
	 */
	public function addFacetDateField (string $dateField): SolrQuery {}

	/**
	 * Removes one of the facet date fields
	 * @link http://www.php.net/manual/en/solrquery.removefacetdatefield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateField (string $field): SolrQuery {}

	/**
	 * Returns all the facet.date fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatefields.php
	 * @return array Returns all the facet.date fields as an array or null if none was set
	 */
	public function getFacetDateFields (): array {}

	/**
	 * Maps to facet.date.start
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatestart.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateStart (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the lower bound for the first date range for all date faceting on this field
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatestart.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateStart (string $field_override = null): string {}

	/**
	 * Maps to facet.date.end
	 * @link http://www.php.net/manual/en/solrquery.setfacetdateend.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateEnd (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value for the facet.date.end parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateend.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateEnd (string $field_override = null): string {}

	/**
	 * Maps to facet.date.gap
	 * @link http://www.php.net/manual/en/solrquery.setfacetdategap.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateGap (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value of the facet.date.gap parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdategap.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateGap (string $field_override = null): string {}

	/**
	 * Maps to facet.date.hardend
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatehardend.php
	 * @param bool $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateHardEnd (bool $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value of the facet.date.hardend parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatehardend.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateHardEnd (string $field_override = null): string {}

	/**
	 * Adds another facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.addfacetdateother.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addFacetDateOther (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Removes one of the facet.date.other parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetdateother.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateOther (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value for the facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateother.php
	 * @param string $field_override [optional] 
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFacetDateOther (string $field_override = null): array {}

	/**
	 * Enable/Disable result grouping (group parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroup.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroup (bool $value): SolrQuery {}

	/**
	 * Returns true if grouping is enabled
	 * @link http://www.php.net/manual/en/solrquery.getgroup.php
	 * @return bool 
	 */
	public function getGroup (): bool {}

	/**
	 * Add a field to be used to group results
	 * @link http://www.php.net/manual/en/solrquery.addgroupfield.php
	 * @param string $value The name of the field.
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function addGroupField (string $value): SolrQuery {}

	/**
	 * Returns group fields (group.field parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfields.php
	 * @return array 
	 */
	public function getGroupFields (): array {}

	/**
	 * Allows grouping results based on the unique values of a function query (group.func parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupfunction.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupFunction (string $value): SolrQuery {}

	/**
	 * Returns group functions (group.func parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfunctions.php
	 * @return array 
	 */
	public function getGroupFunctions (): array {}

	/**
	 * Allows grouping of documents that match the given query
	 * @link http://www.php.net/manual/en/solrquery.addgroupquery.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupQuery (string $value): SolrQuery {}

	/**
	 * Returns all the group.query parameter values
	 * @link http://www.php.net/manual/en/solrquery.getgroupqueries.php
	 * @return array array
	 */
	public function getGroupQueries (): array {}

	/**
	 * Specifies the number of results to return for each group. The server default value is 1
	 * @link http://www.php.net/manual/en/solrquery.setgrouplimit.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupLimit (int $value): SolrQuery {}

	/**
	 * Returns the group.limit value
	 * @link http://www.php.net/manual/en/solrquery.getgrouplimit.php
	 * @return int 
	 */
	public function getGroupLimit (): int {}

	/**
	 * Sets the group.offset parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupoffset.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupOffset (int $value): SolrQuery {}

	/**
	 * Returns the group.offset value
	 * @link http://www.php.net/manual/en/solrquery.getgroupoffset.php
	 * @return int 
	 */
	public function getGroupOffset (): int {}

	/**
	 * Add a group sort field (group.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupsortfield.php
	 * @param string $field Field name
	 * @param int $order [optional] Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants
	 * @return SolrQuery 
	 */
	public function addGroupSortField (string $field, int $order = null): SolrQuery {}

	/**
	 * Returns the group.sort value
	 * @link http://www.php.net/manual/en/solrquery.getgroupsortfields.php
	 * @return array 
	 */
	public function getGroupSortFields (): array {}

	/**
	 * Sets the group format, result structure (group.format parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroupformat.php
	 * @param string $value 
	 * @return SolrQuery 
	 */
	public function setGroupFormat (string $value): SolrQuery {}

	/**
	 * Returns the group.format value
	 * @link http://www.php.net/manual/en/solrquery.getgroupformat.php
	 * @return string 
	 */
	public function getGroupFormat (): string {}

	/**
	 * If true, the result of the first field grouping command is used as the main result list in the response, using group.format=simple
	 * @link http://www.php.net/manual/en/solrquery.setgroupmain.php
	 * @param string $value If true, the result of the first field grouping command is used as the main result list in the response.
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function setGroupMain (string $value): SolrQuery {}

	/**
	 * Returns the group.main value
	 * @link http://www.php.net/manual/en/solrquery.getgroupmain.php
	 * @return bool 
	 */
	public function getGroupMain (): bool {}

	/**
	 * If true, Solr includes the number of groups that have matched the query in the results
	 * @link http://www.php.net/manual/en/solrquery.setgroupngroups.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupNGroups (bool $value): SolrQuery {}

	/**
	 * Returns the group.ngroups value
	 * @link http://www.php.net/manual/en/solrquery.getgroupngroups.php
	 * @return bool 
	 */
	public function getGroupNGroups (): bool {}

	/**
	 * If true, facet counts are based on the most relevant document of each group matching the query
	 * @link http://www.php.net/manual/en/solrquery.setgrouptruncate.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupTruncate (bool $value): SolrQuery {}

	/**
	 * Returns the group.truncate value
	 * @link http://www.php.net/manual/en/solrquery.getgrouptruncate.php
	 * @return bool 
	 */
	public function getGroupTruncate (): bool {}

	/**
	 * Sets group.facet parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupfacet.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupFacet (bool $value): SolrQuery {}

	/**
	 * Returns the group.facet parameter value
	 * @link http://www.php.net/manual/en/solrquery.getgroupfacet.php
	 * @return bool 
	 */
	public function getGroupFacet (): bool {}

	/**
	 * Enables caching for result grouping
	 * @link http://www.php.net/manual/en/solrquery.setgroupcachepercent.php
	 * @param int $percent 
	 * @return SolrQuery 
	 */
	public function setGroupCachePercent (int $percent): SolrQuery {}

	/**
	 * Returns group cache percent value
	 * @link http://www.php.net/manual/en/solrquery.getgroupcachepercent.php
	 * @return int 
	 */
	public function getGroupCachePercent (): int {}

	/**
	 * Collapses the result set to a single document per group
	 * @link http://www.php.net/manual/en/solrquery.collapse.php
	 * @param SolrCollapseFunction $collapseFunction 
	 * @return SolrQuery Returns the current SolrQuery object
	 */
	public function collapse (SolrCollapseFunction $collapseFunction): SolrQuery {}

	/**
	 * Enables/Disables the Expand Component
	 * @link http://www.php.net/manual/en/solrquery.setexpand.php
	 * @param bool $value Bool flag
	 * @return SolrQuery SolrQuery
	 */
	public function setExpand (bool $value): SolrQuery {}

	/**
	 * Returns true if group expanding is enabled
	 * @link http://www.php.net/manual/en/solrquery.getexpand.php
	 * @return bool Returns whether group expanding is enabled.
	 */
	public function getExpand (): bool {}

	/**
	 * Orders the documents within the expanded groups (expand.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addexpandsortfield.php
	 * @param string $field field name
	 * @param string $order [optional] Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants.
	 * <p>Default: SolrQuery::ORDER_DESC</p>
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandSortField (string $field, string $order = null): SolrQuery {}

	/**
	 * Removes an expand sort field from the expand.sort parameter
	 * @link http://www.php.net/manual/en/solrquery.removeexpandsortfield.php
	 * @param string $field field name
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandSortField (string $field): SolrQuery {}

	/**
	 * Returns an array of fields
	 * @link http://www.php.net/manual/en/solrquery.getexpandsortfields.php
	 * @return array Returns an array of fields.
	 */
	public function getExpandSortFields (): array {}

	/**
	 * Sets the number of rows to display in each group (expand.rows). Server Default 5
	 * @link http://www.php.net/manual/en/solrquery.setexpandrows.php
	 * @param int $value 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandRows (int $value): SolrQuery {}

	/**
	 * Returns The number of rows to display in each group (expand.rows)
	 * @link http://www.php.net/manual/en/solrquery.getexpandrows.php
	 * @return int Returns the number of rows.
	 */
	public function getExpandRows (): int {}

	/**
	 * Sets the expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.setexpandquery.php
	 * @param string $q 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandQuery (string $q): SolrQuery {}

	/**
	 * Returns the expand query expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.getexpandquery.php
	 * @return array Returns the expand query.
	 */
	public function getExpandQuery (): array {}

	/**
	 * Overrides main filter query, determines which documents to include in the main group
	 * @link http://www.php.net/manual/en/solrquery.addexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandFilterQuery (string $fq): SolrQuery {}

	/**
	 * Removes an expand filter query
	 * @link http://www.php.net/manual/en/solrquery.removeexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandFilterQuery (string $fq): SolrQuery {}

	/**
	 * Returns the expand filter queries
	 * @link http://www.php.net/manual/en/solrquery.getexpandfilterqueries.php
	 * @return array Returns the expand filter queries.
	 */
	public function getExpandFilterQueries (): array {}

	/**
	 * Enables or disables highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlight.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlight (bool $flag): SolrQuery {}

	/**
	 * Returns the state of the hl parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlight.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlight (): bool {}

	/**
	 * Maps to hl.fl
	 * @link http://www.php.net/manual/en/solrquery.addhighlightfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addHighlightField (string $field): SolrQuery {}

	/**
	 * Removes one of the fields used for highlighting
	 * @link http://www.php.net/manual/en/solrquery.removehighlightfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeHighlightField (string $field): SolrQuery {}

	/**
	 * Returns all the fields that Solr should generate highlighted snippets for
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getHighlightFields (): array {}

	/**
	 * Sets the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsnippets.php
	 * @param int $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSnippets (int $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsnippets.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightSnippets (string $field_override = null): int {}

	/**
	 * The size of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragsize.php
	 * @param int $size 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragsize (int $size, string $field_override = null): SolrQuery {}

	/**
	 * Returns the number of characters of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragsize.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success or null if not set.
	 */
	public function getHighlightFragsize (string $field_override = null): int {}

	/**
	 * Whether or not to collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmergecontiguous.php
	 * @param bool $flag 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMergeContiguous (bool $flag, string $field_override = null): SolrQuery {}

	/**
	 * Returns whether or not the collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmergecontiguous.php
	 * @param string $field_override [optional] 
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightMergeContiguous (string $field_override = null): bool {}

	/**
	 * Require field matching during highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightrequirefieldmatch.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRequireFieldMatch (bool $flag): SolrQuery {}

	/**
	 * Returns if a field will only be highlighted if the query matched in this particular field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightrequirefieldmatch.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightRequireFieldMatch (): bool {}

	/**
	 * Specifies the number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxanalyzedchars.php
	 * @param int $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAnalyzedChars (int $value): SolrQuery {}

	/**
	 * Returns the maximum number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxanalyzedchars.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightMaxAnalyzedChars (): int {}

	/**
	 * Specifies the backup field to use
	 * @link http://www.php.net/manual/en/solrquery.sethighlightalternatefield.php
	 * @param string $field 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightAlternateField (string $field, string $field_override = null): SolrQuery {}

	/**
	 * Returns the highlight field to use as backup or default
	 * @link http://www.php.net/manual/en/solrquery.gethighlightalternatefield.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightAlternateField (string $field_override = null): string {}

	/**
	 * Sets the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxalternatefieldlength.php
	 * @param int $fieldLength 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAlternateFieldLength (int $fieldLength, string $field_override = null): SolrQuery {}

	/**
	 * Returns the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxalternatefieldlength.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightMaxAlternateFieldLength (string $field_override = null): int {}

	/**
	 * Specify a formatter for the highlight output
	 * @link http://www.php.net/manual/en/solrquery.sethighlightformatter.php
	 * @param string $formatter 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function setHighlightFormatter (string $formatter, string $field_override = null): SolrQuery {}

	/**
	 * Returns the formatter for the highlighted output
	 * @link http://www.php.net/manual/en/solrquery.gethighlightformatter.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightFormatter (string $field_override = null): string {}

	/**
	 * Sets the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepre.php
	 * @param string $simplePre 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSimplePre (string $simplePre, string $field_override = null): SolrQuery {}

	/**
	 * Returns the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepre.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightSimplePre (string $field_override = null): string {}

	/**
	 * Sets the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepost.php
	 * @param string $simplePost 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function setHighlightSimplePost (string $simplePost, string $field_override = null): SolrQuery {}

	/**
	 * Returns the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepost.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightSimplePost (string $field_override = null): string {}

	/**
	 * Sets a text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragmenter.php
	 * @param string $fragmenter 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragmenter (string $fragmenter, string $field_override = null): SolrQuery {}

	/**
	 * Returns the text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragmenter.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightFragmenter (string $field_override = null): string {}

	/**
	 * Whether to highlight phrase terms only when they appear within the query phrase
	 * @link http://www.php.net/manual/en/solrquery.sethighlightusephrasehighlighter.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightUsePhraseHighlighter (bool $flag): SolrQuery {}

	/**
	 * Returns the state of the hl.usePhraseHighlighter parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightusephrasehighlighter.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightUsePhraseHighlighter (): bool {}

	/**
	 * Use SpanScorer to highlight phrase terms
	 * @link http://www.php.net/manual/en/solrquery.sethighlighthighlightmultiterm.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightHighlightMultiTerm (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not to enable highlighting for range/wildcard/fuzzy/prefix queries
	 * @link http://www.php.net/manual/en/solrquery.gethighlighthighlightmultiterm.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightHighlightMultiTerm (): bool {}

	/**
	 * Sets the factor by which the regex fragmenter can stray from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexslop.php
	 * @param float $factor 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexSlop (float $factor): SolrQuery {}

	/**
	 * Returns the deviation factor from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexslop.php
	 * @return float Returns a float on success and null if not set.
	 */
	public function getHighlightRegexSlop (): float {}

	/**
	 * Specify the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexpattern.php
	 * @param string $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexPattern (string $value): SolrQuery {}

	/**
	 * Returns the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexpattern.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightRegexPattern (): string {}

	/**
	 * Specify the maximum number of characters to analyze
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexmaxanalyzedchars.php
	 * @param int $maxAnalyzedChars 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexMaxAnalyzedChars (int $maxAnalyzedChars): SolrQuery {}

	/**
	 * Returns the maximum number of characters from a field when using the regex fragmenter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexmaxanalyzedchars.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightRegexMaxAnalyzedChars (): int {}

	/**
	 * Enables or disables the Stats component
	 * @link http://www.php.net/manual/en/solrquery.setstats.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setStats (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not stats is enabled
	 * @link http://www.php.net/manual/en/solrquery.getstats.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getStats (): bool {}

	/**
	 * Maps to stats.field parameter
	 * @link http://www.php.net/manual/en/solrquery.addstatsfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addStatsField (string $field): SolrQuery {}

	/**
	 * Removes one of the stats.field parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsField (string $field): SolrQuery {}

	/**
	 * Returns all the statistics fields
	 * @link http://www.php.net/manual/en/solrquery.getstatsfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getStatsFields (): array {}

	/**
	 * Requests a return of sub results for values within the given facet
	 * @link http://www.php.net/manual/en/solrquery.addstatsfacet.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addStatsFacet (string $field): SolrQuery {}

	/**
	 * Removes one of the stats.facet parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfacet.php
	 * @param string $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsFacet (string $value): SolrQuery {}

	/**
	 * Returns all the stats facets that were set
	 * @link http://www.php.net/manual/en/solrquery.getstatsfacets.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getStatsFacets (): array {}

	/**
	 * Enables or disables moreLikeThis
	 * @link http://www.php.net/manual/en/solrquery.setmlt.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMlt (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not MoreLikeThis results should be enabled
	 * @link http://www.php.net/manual/en/solrquery.getmlt.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getMlt (): bool {}

	/**
	 * Set the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.setmltcount.php
	 * @param int $count 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltCount (int $count): SolrQuery {}

	/**
	 * Returns the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.getmltcount.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltCount (): int {}

	/**
	 * Sets a field to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.addmltfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addMltField (string $field): SolrQuery {}

	/**
	 * Removes one of the moreLikeThis fields
	 * @link http://www.php.net/manual/en/solrquery.removemltfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeMltField (string $field): SolrQuery {}

	/**
	 * Returns all the fields to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.getmltfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getMltFields (): array {}

	/**
	 * Maps to mlt.qf
	 * @link http://www.php.net/manual/en/solrquery.addmltqueryfield.php
	 * @param string $field 
	 * @param float $boost 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addMltQueryField (string $field, float $boost): SolrQuery {}

	/**
	 * Removes one of the moreLikeThis query fields
	 * @link http://www.php.net/manual/en/solrquery.removemltqueryfield.php
	 * @param string $queryField 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeMltQueryField (string $queryField): SolrQuery {}

	/**
	 * Returns the query fields and their boosts
	 * @link http://www.php.net/manual/en/solrquery.getmltqueryfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getMltQueryFields (): array {}

	/**
	 * Sets the frequency below which terms will be ignored in the source docs
	 * @link http://www.php.net/manual/en/solrquery.setmltmintermfrequency.php
	 * @param int $minTermFrequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinTermFrequency (int $minTermFrequency): SolrQuery {}

	/**
	 * Returns the frequency below which terms will be ignored in the source document
	 * @link http://www.php.net/manual/en/solrquery.getmltmintermfrequency.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMinTermFrequency (): int {}

	/**
	 * Sets the mltMinDoc frequency
	 * @link http://www.php.net/manual/en/solrquery.setmltmindocfrequency.php
	 * @param int $minDocFrequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinDocFrequency (int $minDocFrequency): SolrQuery {}

	/**
	 * Returns the treshold frequency at which words will be ignored which do not occur in at least this many docs
	 * @link http://www.php.net/manual/en/solrquery.getmltmindocfrequency.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMinDocFrequency (): int {}

	/**
	 * Sets the minimum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltminwordlength.php
	 * @param int $minWordLength 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinWordLength (int $minWordLength): SolrQuery {}

	/**
	 * Returns the minimum word length below which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltminwordlength.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMinWordLength (): int {}

	/**
	 * Sets the maximum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxwordlength.php
	 * @param int $maxWordLength 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxWordLength (int $maxWordLength): SolrQuery {}

	/**
	 * Returns the maximum word length above which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxwordlength.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMaxWordLength (): int {}

	/**
	 * Specifies the maximum number of tokens to parse
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumtokens.php
	 * @param int $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumTokens (int $value): SolrQuery {}

	/**
	 * Returns the maximum number of tokens to parse in each document field that is not stored with TermVector support
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumtokens.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMaxNumTokens (): int {}

	/**
	 * Sets the maximum number of query terms included
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumqueryterms.php
	 * @param int $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumQueryTerms (int $value): SolrQuery {}

	/**
	 * Returns the maximum number of query terms that will be included in any generated query
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumqueryterms.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMaxNumQueryTerms (): int {}

	/**
	 * Set if the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.setmltboost.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltBoost (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.getmltboost.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getMltBoost (): bool {}

	/**
	 * Enables or disables the TermsComponent
	 * @link http://www.php.net/manual/en/solrquery.setterms.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTerms (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not the TermsComponent is enabled
	 * @link http://www.php.net/manual/en/solrquery.getterms.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTerms (): bool {}

	/**
	 * Sets the name of the field to get the Terms from
	 * @link http://www.php.net/manual/en/solrquery.settermsfield.php
	 * @param string $fieldname 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsField (string $fieldname): SolrQuery {}

	/**
	 * Returns the field from which the terms are retrieved
	 * @link http://www.php.net/manual/en/solrquery.gettermsfield.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsField (): string {}

	/**
	 * Specifies the Term to start from
	 * @link http://www.php.net/manual/en/solrquery.settermslowerbound.php
	 * @param string $lowerBound 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLowerBound (string $lowerBound): SolrQuery {}

	/**
	 * Returns the term to start at
	 * @link http://www.php.net/manual/en/solrquery.gettermslowerbound.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsLowerBound (): string {}

	/**
	 * Sets the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.settermsupperbound.php
	 * @param string $upperBound 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsUpperBound (string $upperBound): SolrQuery {}

	/**
	 * Returns the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.gettermsupperbound.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsUpperBound (): string {}

	/**
	 * Include the lower bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludelowerbound.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeLowerBound (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not to include the lower bound in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludelowerbound.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTermsIncludeLowerBound (): bool {}

	/**
	 * Include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludeupperbound.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeUpperBound (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not to include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludeupperbound.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTermsIncludeUpperBound (): bool {}

	/**
	 * Sets the minimum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmincount.php
	 * @param int $frequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMinCount (int $frequency): SolrQuery {}

	/**
	 * Returns the minimum document frequency to return in order to be included
	 * @link http://www.php.net/manual/en/solrquery.gettermsmincount.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsMinCount (): int {}

	/**
	 * Sets the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmaxcount.php
	 * @param int $frequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMaxCount (int $frequency): SolrQuery {}

	/**
	 * Returns the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.gettermsmaxcount.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsMaxCount (): int {}

	/**
	 * Restrict matches to terms that start with the prefix
	 * @link http://www.php.net/manual/en/solrquery.settermsprefix.php
	 * @param string $prefix 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsPrefix (string $prefix): SolrQuery {}

	/**
	 * Returns the term prefix
	 * @link http://www.php.net/manual/en/solrquery.gettermsprefix.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsPrefix (): string {}

	/**
	 * Sets the maximum number of terms to return
	 * @link http://www.php.net/manual/en/solrquery.settermslimit.php
	 * @param int $limit 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLimit (int $limit): SolrQuery {}

	/**
	 * Returns the maximum number of terms Solr should return
	 * @link http://www.php.net/manual/en/solrquery.gettermslimit.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsLimit (): int {}

	/**
	 * Return the raw characters of the indexed term
	 * @link http://www.php.net/manual/en/solrquery.settermsreturnraw.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsReturnRaw (bool $flag): SolrQuery {}

	/**
	 * Whether or not to return raw characters
	 * @link http://www.php.net/manual/en/solrquery.gettermsreturnraw.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTermsReturnRaw (): bool {}

	/**
	 * Specifies how to sort the returned terms
	 * @link http://www.php.net/manual/en/solrquery.settermssort.php
	 * @param int $sortType 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsSort (int $sortType): SolrQuery {}

	/**
	 * Returns an integer indicating how terms are sorted
	 * @link http://www.php.net/manual/en/solrquery.gettermssort.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsSort (): int {}

	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value): SolrParams {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value): SolrParams {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] 
	 * @return string Returns a string on success and false on failure.
	 */
	public function toString (bool $url_encode = false): string {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array Returns an array of non URL-encoded parameters
	 */
	public function getParams (): array {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] 
	 * @return mixed Returns a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null): mixed {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array Returns an array on URL-encoded parameters
	 */
	public function getPreparedParams (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize (): string {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized 
	 * @return void None
	 */
	public function unserialize (string $serialized): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias of SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParams instance on success
	 */
	public function add (string $name, string $value): SolrParams {}

	/**
	 * Alias of SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name 
	 * @param string $value 
	 * @return void Returns an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value): void {}

	/**
	 * Alias of SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name 
	 * @return mixed Returns an array or string depending on the type of parameter
	 */
	public function get (string $param_name): mixed {}

}

/**
 * @link http://www.php.net/manual/en/class.solrdismaxquery.php
 */
class SolrDisMaxQuery extends SolrQuery implements Serializable, Stringable {

	/**
	 * Class Constructor
	 * @link http://www.php.net/manual/en/solrdismaxquery.construct.php
	 * @param string $q [optional] Search Query (q parameter)
	 * @return string 
	 */
	public function __construct (string $q = null): string {}

	/**
	 * Set Query Alternate (q.alt parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setqueryalt.php
	 * @param string $q Query String
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setQueryAlt (string $q): SolrDisMaxQuery {}

	/**
	 * Add a query field with optional boost (qf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addqueryfield.php
	 * @param string $field field name
	 * @param string $boost [optional] Boost value. Boosts documents with matching terms.
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addQueryField (string $field, string $boost = null): SolrDisMaxQuery {}

	/**
	 * Removes a Query Field (qf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removequeryfield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeQueryField (string $field): SolrDisMaxQuery {}

	/**
	 * Adds a Phrase Field (pf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addphrasefield.php
	 * @param string $field field name
	 * @param string $boost 
	 * @param string $slop [optional] 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addPhraseField (string $field, string $boost, string $slop = null): SolrDisMaxQuery {}

	/**
	 * Removes a Phrase Field (pf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removephrasefield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removePhraseField (string $field): SolrDisMaxQuery {}

	/**
	 * Sets Phrase Fields and their boosts (and slops) using pf2 parameter
	 * @link http://www.php.net/manual/en/solrdismaxquery.setphrasefields.php
	 * @param string $fields Fields, boosts [, slops]
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setPhraseFields (string $fields): SolrDisMaxQuery {}

	/**
	 * Sets the default slop on phrase queries (ps parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setphraseslop.php
	 * @param string $slop 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setPhraseSlop (string $slop): SolrDisMaxQuery {}

	/**
	 * Specifies the amount of slop permitted on phrase queries explicitly included in the user's query string (qf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setqueryphraseslop.php
	 * @param string $slop Amount of slop
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setQueryPhraseSlop (string $slop): SolrDisMaxQuery {}

	/**
	 * Directly Sets Boost Query Parameter (bq)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setboostquery.php
	 * @param string $q query
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBoostQuery (string $q): SolrDisMaxQuery {}

	/**
	 * Adds a boost query field with value and optional boost (bq parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addboostquery.php
	 * @param string $field 
	 * @param string $value 
	 * @param string $boost [optional] 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addBoostQuery (string $field, string $value, string $boost = null): SolrDisMaxQuery {}

	/**
	 * Removes a boost query partial by field name (bq)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removeboostquery.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeBoostQuery (string $field): SolrDisMaxQuery {}

	/**
	 * Sets a Boost Function (bf parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setboostfunction.php
	 * @param string $function 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBoostFunction (string $function): SolrDisMaxQuery {}

	/**
	 * Set Minimum "Should" Match (mm)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setminimummatch.php
	 * @param string $value Minimum match value/expression
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setMinimumMatch (string $value): SolrDisMaxQuery {}

	/**
	 * Sets Tie Breaker parameter (tie parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.settiebreaker.php
	 * @param string $tieBreaker The tie parameter specifies a float value (which should be something much less than 1) to use as tiebreaker in DisMax queries.
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setTieBreaker (string $tieBreaker): SolrDisMaxQuery {}

	/**
	 * Switch QueryParser to be DisMax Query Parser
	 * @link http://www.php.net/manual/en/solrdismaxquery.usedismaxqueryparser.php
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function useDisMaxQueryParser (): SolrDisMaxQuery {}

	/**
	 * Switch QueryParser to be EDisMax
	 * @link http://www.php.net/manual/en/solrdismaxquery.useedismaxqueryparser.php
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function useEDisMaxQueryParser (): SolrDisMaxQuery {}

	/**
	 * Sets Bigram Phrase Fields and their boosts (and slops) using pf2 parameter
	 * @link http://www.php.net/manual/en/solrdismaxquery.setbigramphrasefields.php
	 * @param string $fields Fields boosts (slops)
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBigramPhraseFields (string $fields): SolrDisMaxQuery {}

	/**
	 * Adds a Phrase Bigram Field (pf2 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addbigramphrasefield.php
	 * @param string $field 
	 * @param string $boost 
	 * @param string $slop [optional] 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addBigramPhraseField (string $field, string $boost, string $slop = null): SolrDisMaxQuery {}

	/**
	 * Removes phrase bigram field (pf2 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removebigramphrasefield.php
	 * @param string $field The Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeBigramPhraseField (string $field): SolrDisMaxQuery {}

	/**
	 * Sets Bigram Phrase Slop (ps2 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setbigramphraseslop.php
	 * @param string $slop 
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setBigramPhraseSlop (string $slop): SolrDisMaxQuery {}

	/**
	 * Directly Sets Trigram Phrase Fields (pf3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.settrigramphrasefields.php
	 * @param string $fields Trigram Phrase Fields
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setTrigramPhraseFields (string $fields): SolrDisMaxQuery {}

	/**
	 * Adds a Trigram Phrase Field (pf3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.addtrigramphrasefield.php
	 * @param string $field Field Name
	 * @param string $boost Field Boost
	 * @param string $slop [optional] Field Slop
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addTrigramPhraseField (string $field, string $boost, string $slop = null): SolrDisMaxQuery {}

	/**
	 * Removes a Trigram Phrase Field (pf3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removetrigramphrasefield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeTrigramPhraseField (string $field): SolrDisMaxQuery {}

	/**
	 * Sets Trigram Phrase Slop (ps3 parameter)
	 * @link http://www.php.net/manual/en/solrdismaxquery.settrigramphraseslop.php
	 * @param string $slop Phrase slop
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setTrigramPhraseSlop (string $slop): SolrDisMaxQuery {}

	/**
	 * Adds a field to User Fields Parameter (uf)
	 * @link http://www.php.net/manual/en/solrdismaxquery.adduserfield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function addUserField (string $field): SolrDisMaxQuery {}

	/**
	 * Removes a field from The User Fields Parameter (uf)
	 * @link http://www.php.net/manual/en/solrdismaxquery.removeuserfield.php
	 * @param string $field Field Name
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function removeUserField (string $field): SolrDisMaxQuery {}

	/**
	 * Sets User Fields parameter (uf)
	 * @link http://www.php.net/manual/en/solrdismaxquery.setuserfields.php
	 * @param string $fields Fields names separated by space
	 * <p>This parameter supports wildcards.</p>
	 * @return SolrDisMaxQuery SolrDisMaxQuery
	 */
	public function setUserFields (string $fields): SolrDisMaxQuery {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Sets the search query
	 * @link http://www.php.net/manual/en/solrquery.setquery.php
	 * @param string $query 
	 * @return SolrQuery Returns the current SolrQuery object
	 */
	public function setQuery (string $query): SolrQuery {}

	/**
	 * Returns the main query
	 * @link http://www.php.net/manual/en/solrquery.getquery.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getQuery (): string {}

	/**
	 * Specifies the number of rows to skip
	 * @link http://www.php.net/manual/en/solrquery.setstart.php
	 * @param int $start 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function setStart (int $start): SolrQuery {}

	/**
	 * Returns the offset in the complete result set
	 * @link http://www.php.net/manual/en/solrquery.getstart.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getStart (): int {}

	/**
	 * Specifies the maximum number of rows to return in the result
	 * @link http://www.php.net/manual/en/solrquery.setrows.php
	 * @param int $rows 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function setRows (int $rows): SolrQuery {}

	/**
	 * Returns the maximum number of documents
	 * @link http://www.php.net/manual/en/solrquery.getrows.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getRows (): int {}

	/**
	 * Specifies which fields to return in the result
	 * @link http://www.php.net/manual/en/solrquery.addfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object
	 */
	public function addField (string $field): SolrQuery {}

	/**
	 * Removes a field from the list of fields
	 * @link http://www.php.net/manual/en/solrquery.removefield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeField (string $field): SolrQuery {}

	/**
	 * Returns the list of fields that will be returned in the response
	 * @link http://www.php.net/manual/en/solrquery.getfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFields (): array {}

	/**
	 * Used to control how the results should be sorted
	 * @link http://www.php.net/manual/en/solrquery.addsortfield.php
	 * @param string $field 
	 * @param int $order [optional] 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function addSortField (string $field, int $order = \SolrQuery::ORDER_DESC): SolrQuery {}

	/**
	 * Removes one of the sort fields
	 * @link http://www.php.net/manual/en/solrquery.removesortfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeSortField (string $field): SolrQuery {}

	/**
	 * Returns all the sort fields
	 * @link http://www.php.net/manual/en/solrquery.getsortfields.php
	 * @return array Returns an array on success and null if none of the parameters was set.
	 */
	public function getSortFields (): array {}

	/**
	 * Specifies a filter query
	 * @link http://www.php.net/manual/en/solrquery.addfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery Returns the current SolrQuery object.
	 */
	public function addFilterQuery (string $fq): SolrQuery {}

	/**
	 * Removes a filter query
	 * @link http://www.php.net/manual/en/solrquery.removefilterquery.php
	 * @param string $fq 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFilterQuery (string $fq): SolrQuery {}

	/**
	 * Returns an array of filter queries
	 * @link http://www.php.net/manual/en/solrquery.getfilterqueries.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFilterQueries (): array {}

	/**
	 * Flag to show debug information
	 * @link http://www.php.net/manual/en/solrquery.setshowdebuginfo.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setShowDebugInfo (bool $flag): SolrQuery {}

	/**
	 * Sets the explainOther common query parameter
	 * @link http://www.php.net/manual/en/solrquery.setexplainother.php
	 * @param string $query 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setExplainOther (string $query): SolrQuery {}

	/**
	 * The time allowed for search to finish
	 * @link http://www.php.net/manual/en/solrquery.settimeallowed.php
	 * @param int $timeAllowed 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTimeAllowed (int $timeAllowed): SolrQuery {}

	/**
	 * Returns the time in milliseconds allowed for the query to finish
	 * @link http://www.php.net/manual/en/solrquery.gettimeallowed.php
	 * @return int Returns and integer on success and null if it is not set.
	 */
	public function getTimeAllowed (): int {}

	/**
	 * Exclude the header from the returned results
	 * @link http://www.php.net/manual/en/solrquery.setomitheader.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setOmitHeader (bool $flag): SolrQuery {}

	/**
	 * Toggles the echoHandler parameter
	 * @link http://www.php.net/manual/en/solrquery.setechohandler.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setEchoHandler (bool $flag): SolrQuery {}

	/**
	 * Determines what kind of parameters to include in the response
	 * @link http://www.php.net/manual/en/solrquery.setechoparams.php
	 * @param string $type 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setEchoParams (string $type): SolrQuery {}

	/**
	 * Maps to the facet parameter. Enables or disables facetting
	 * @link http://www.php.net/manual/en/solrquery.setfacet.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacet (bool $flag): SolrQuery {}

	/**
	 * Returns the value of the facet parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacet.php
	 * @return bool Returns a boolean on success and null if not set
	 */
	public function getFacet (): bool {}

	/**
	 * Adds another field to the facet
	 * @link http://www.php.net/manual/en/solrquery.addfacetfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addFacetField (string $field): SolrQuery {}

	/**
	 * Removes one of the facet.date parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetField (string $field): SolrQuery {}

	/**
	 * Returns all the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetfields.php
	 * @return array Returns an array of all the fields and null if none was set
	 */
	public function getFacetFields (): array {}

	/**
	 * Adds a facet query
	 * @link http://www.php.net/manual/en/solrquery.addfacetquery.php
	 * @param string $facetQuery 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addFacetQuery (string $facetQuery): SolrQuery {}

	/**
	 * Removes one of the facet.query parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetquery.php
	 * @param string $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetQuery (string $value): SolrQuery {}

	/**
	 * Returns all the facet queries
	 * @link http://www.php.net/manual/en/solrquery.getfacetqueries.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFacetQueries (): array {}

	/**
	 * Specifies a string prefix with which to limits the terms on which to facet
	 * @link http://www.php.net/manual/en/solrquery.setfacetprefix.php
	 * @param string $prefix 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetPrefix (string $prefix, string $field_override = null): SolrQuery {}

	/**
	 * Returns the facet prefix
	 * @link http://www.php.net/manual/en/solrquery.getfacetprefix.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getFacetPrefix (string $field_override = null): string {}

	/**
	 * Determines the ordering of the facet field constraints
	 * @link http://www.php.net/manual/en/solrquery.setfacetsort.php
	 * @param int $facetSort 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetSort (int $facetSort, string $field_override = null): SolrQuery {}

	/**
	 * Returns the facet sort type
	 * @link http://www.php.net/manual/en/solrquery.getfacetsort.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer (SolrQuery::FACET_SORT_INDEX or SolrQuery::FACET_SORT_COUNT) on success or null if not set.
	 */
	public function getFacetSort (string $field_override = null): int {}

	/**
	 * Maps to facet.limit
	 * @link http://www.php.net/manual/en/solrquery.setfacetlimit.php
	 * @param int $limit 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetLimit (int $limit, string $field_override = null): SolrQuery {}

	/**
	 * Returns the maximum number of constraint counts that should be returned for the facet fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetlimit.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set
	 */
	public function getFacetLimit (string $field_override = null): int {}

	/**
	 * Sets the offset into the list of constraints to allow for pagination
	 * @link http://www.php.net/manual/en/solrquery.setfacetoffset.php
	 * @param int $offset 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetOffset (int $offset, string $field_override = null): SolrQuery {}

	/**
	 * Returns an offset into the list of constraints to be used for pagination
	 * @link http://www.php.net/manual/en/solrquery.getfacetoffset.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set
	 */
	public function getFacetOffset (string $field_override = null): int {}

	/**
	 * Maps to facet.mincount
	 * @link http://www.php.net/manual/en/solrquery.setfacetmincount.php
	 * @param int $mincount 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMinCount (int $mincount, string $field_override = null): SolrQuery {}

	/**
	 * Returns the minimum counts for facet fields should be included in the response
	 * @link http://www.php.net/manual/en/solrquery.getfacetmincount.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set
	 */
	public function getFacetMinCount (string $field_override = null): int {}

	/**
	 * Maps to facet.missing
	 * @link http://www.php.net/manual/en/solrquery.setfacetmissing.php
	 * @param bool $flag 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMissing (bool $flag, string $field_override = null): SolrQuery {}

	/**
	 * Returns the current state of the facet.missing parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmissing.php
	 * @param string $field_override [optional] 
	 * @return bool Returns a boolean on success and null if not set
	 */
	public function getFacetMissing (string $field_override = null): bool {}

	/**
	 * Specifies the type of algorithm to use when faceting a field
	 * @link http://www.php.net/manual/en/solrquery.setfacetmethod.php
	 * @param string $method 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetMethod (string $method, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value of the facet.method parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetmethod.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetMethod (string $field_override = null): string {}

	/**
	 * Sets the minimum document frequency used for determining term count
	 * @link http://www.php.net/manual/en/solrquery.setfacetenumcachemindefaultfrequency.php
	 * @param int $frequency 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetEnumCacheMinDefaultFrequency (int $frequency, string $field_override = null): SolrQuery {}

	/**
	 * Maps to facet.date
	 * @link http://www.php.net/manual/en/solrquery.addfacetdatefield.php
	 * @param string $dateField 
	 * @return SolrQuery Returns a SolrQuery object.
	 */
	public function addFacetDateField (string $dateField): SolrQuery {}

	/**
	 * Removes one of the facet date fields
	 * @link http://www.php.net/manual/en/solrquery.removefacetdatefield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateField (string $field): SolrQuery {}

	/**
	 * Returns all the facet.date fields
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatefields.php
	 * @return array Returns all the facet.date fields as an array or null if none was set
	 */
	public function getFacetDateFields (): array {}

	/**
	 * Maps to facet.date.start
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatestart.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateStart (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the lower bound for the first date range for all date faceting on this field
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatestart.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateStart (string $field_override = null): string {}

	/**
	 * Maps to facet.date.end
	 * @link http://www.php.net/manual/en/solrquery.setfacetdateend.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateEnd (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value for the facet.date.end parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateend.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateEnd (string $field_override = null): string {}

	/**
	 * Maps to facet.date.gap
	 * @link http://www.php.net/manual/en/solrquery.setfacetdategap.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateGap (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value of the facet.date.gap parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdategap.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateGap (string $field_override = null): string {}

	/**
	 * Maps to facet.date.hardend
	 * @link http://www.php.net/manual/en/solrquery.setfacetdatehardend.php
	 * @param bool $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setFacetDateHardEnd (bool $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value of the facet.date.hardend parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdatehardend.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set
	 */
	public function getFacetDateHardEnd (string $field_override = null): string {}

	/**
	 * Adds another facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.addfacetdateother.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addFacetDateOther (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Removes one of the facet.date.other parameters
	 * @link http://www.php.net/manual/en/solrquery.removefacetdateother.php
	 * @param string $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeFacetDateOther (string $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the value for the facet.date.other parameter
	 * @link http://www.php.net/manual/en/solrquery.getfacetdateother.php
	 * @param string $field_override [optional] 
	 * @return array Returns an array on success and null if not set.
	 */
	public function getFacetDateOther (string $field_override = null): array {}

	/**
	 * Enable/Disable result grouping (group parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroup.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroup (bool $value): SolrQuery {}

	/**
	 * Returns true if grouping is enabled
	 * @link http://www.php.net/manual/en/solrquery.getgroup.php
	 * @return bool 
	 */
	public function getGroup (): bool {}

	/**
	 * Add a field to be used to group results
	 * @link http://www.php.net/manual/en/solrquery.addgroupfield.php
	 * @param string $value The name of the field.
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function addGroupField (string $value): SolrQuery {}

	/**
	 * Returns group fields (group.field parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfields.php
	 * @return array 
	 */
	public function getGroupFields (): array {}

	/**
	 * Allows grouping results based on the unique values of a function query (group.func parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupfunction.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupFunction (string $value): SolrQuery {}

	/**
	 * Returns group functions (group.func parameter values)
	 * @link http://www.php.net/manual/en/solrquery.getgroupfunctions.php
	 * @return array 
	 */
	public function getGroupFunctions (): array {}

	/**
	 * Allows grouping of documents that match the given query
	 * @link http://www.php.net/manual/en/solrquery.addgroupquery.php
	 * @param string $value 
	 * @return SolrQuery SolrQuery
	 */
	public function addGroupQuery (string $value): SolrQuery {}

	/**
	 * Returns all the group.query parameter values
	 * @link http://www.php.net/manual/en/solrquery.getgroupqueries.php
	 * @return array array
	 */
	public function getGroupQueries (): array {}

	/**
	 * Specifies the number of results to return for each group. The server default value is 1
	 * @link http://www.php.net/manual/en/solrquery.setgrouplimit.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupLimit (int $value): SolrQuery {}

	/**
	 * Returns the group.limit value
	 * @link http://www.php.net/manual/en/solrquery.getgrouplimit.php
	 * @return int 
	 */
	public function getGroupLimit (): int {}

	/**
	 * Sets the group.offset parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupoffset.php
	 * @param int $value 
	 * @return SolrQuery 
	 */
	public function setGroupOffset (int $value): SolrQuery {}

	/**
	 * Returns the group.offset value
	 * @link http://www.php.net/manual/en/solrquery.getgroupoffset.php
	 * @return int 
	 */
	public function getGroupOffset (): int {}

	/**
	 * Add a group sort field (group.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addgroupsortfield.php
	 * @param string $field Field name
	 * @param int $order [optional] Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants
	 * @return SolrQuery 
	 */
	public function addGroupSortField (string $field, int $order = null): SolrQuery {}

	/**
	 * Returns the group.sort value
	 * @link http://www.php.net/manual/en/solrquery.getgroupsortfields.php
	 * @return array 
	 */
	public function getGroupSortFields (): array {}

	/**
	 * Sets the group format, result structure (group.format parameter)
	 * @link http://www.php.net/manual/en/solrquery.setgroupformat.php
	 * @param string $value 
	 * @return SolrQuery 
	 */
	public function setGroupFormat (string $value): SolrQuery {}

	/**
	 * Returns the group.format value
	 * @link http://www.php.net/manual/en/solrquery.getgroupformat.php
	 * @return string 
	 */
	public function getGroupFormat (): string {}

	/**
	 * If true, the result of the first field grouping command is used as the main result list in the response, using group.format=simple
	 * @link http://www.php.net/manual/en/solrquery.setgroupmain.php
	 * @param string $value If true, the result of the first field grouping command is used as the main result list in the response.
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function setGroupMain (string $value): SolrQuery {}

	/**
	 * Returns the group.main value
	 * @link http://www.php.net/manual/en/solrquery.getgroupmain.php
	 * @return bool 
	 */
	public function getGroupMain (): bool {}

	/**
	 * If true, Solr includes the number of groups that have matched the query in the results
	 * @link http://www.php.net/manual/en/solrquery.setgroupngroups.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupNGroups (bool $value): SolrQuery {}

	/**
	 * Returns the group.ngroups value
	 * @link http://www.php.net/manual/en/solrquery.getgroupngroups.php
	 * @return bool 
	 */
	public function getGroupNGroups (): bool {}

	/**
	 * If true, facet counts are based on the most relevant document of each group matching the query
	 * @link http://www.php.net/manual/en/solrquery.setgrouptruncate.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupTruncate (bool $value): SolrQuery {}

	/**
	 * Returns the group.truncate value
	 * @link http://www.php.net/manual/en/solrquery.getgrouptruncate.php
	 * @return bool 
	 */
	public function getGroupTruncate (): bool {}

	/**
	 * Sets group.facet parameter
	 * @link http://www.php.net/manual/en/solrquery.setgroupfacet.php
	 * @param bool $value 
	 * @return SolrQuery 
	 */
	public function setGroupFacet (bool $value): SolrQuery {}

	/**
	 * Returns the group.facet parameter value
	 * @link http://www.php.net/manual/en/solrquery.getgroupfacet.php
	 * @return bool 
	 */
	public function getGroupFacet (): bool {}

	/**
	 * Enables caching for result grouping
	 * @link http://www.php.net/manual/en/solrquery.setgroupcachepercent.php
	 * @param int $percent 
	 * @return SolrQuery 
	 */
	public function setGroupCachePercent (int $percent): SolrQuery {}

	/**
	 * Returns group cache percent value
	 * @link http://www.php.net/manual/en/solrquery.getgroupcachepercent.php
	 * @return int 
	 */
	public function getGroupCachePercent (): int {}

	/**
	 * Collapses the result set to a single document per group
	 * @link http://www.php.net/manual/en/solrquery.collapse.php
	 * @param SolrCollapseFunction $collapseFunction 
	 * @return SolrQuery Returns the current SolrQuery object
	 */
	public function collapse (SolrCollapseFunction $collapseFunction): SolrQuery {}

	/**
	 * Enables/Disables the Expand Component
	 * @link http://www.php.net/manual/en/solrquery.setexpand.php
	 * @param bool $value Bool flag
	 * @return SolrQuery SolrQuery
	 */
	public function setExpand (bool $value): SolrQuery {}

	/**
	 * Returns true if group expanding is enabled
	 * @link http://www.php.net/manual/en/solrquery.getexpand.php
	 * @return bool Returns whether group expanding is enabled.
	 */
	public function getExpand (): bool {}

	/**
	 * Orders the documents within the expanded groups (expand.sort parameter)
	 * @link http://www.php.net/manual/en/solrquery.addexpandsortfield.php
	 * @param string $field field name
	 * @param string $order [optional] Order ASC/DESC, utilizes SolrQuery::ORDER_&#42; constants.
	 * <p>Default: SolrQuery::ORDER_DESC</p>
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandSortField (string $field, string $order = null): SolrQuery {}

	/**
	 * Removes an expand sort field from the expand.sort parameter
	 * @link http://www.php.net/manual/en/solrquery.removeexpandsortfield.php
	 * @param string $field field name
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandSortField (string $field): SolrQuery {}

	/**
	 * Returns an array of fields
	 * @link http://www.php.net/manual/en/solrquery.getexpandsortfields.php
	 * @return array Returns an array of fields.
	 */
	public function getExpandSortFields (): array {}

	/**
	 * Sets the number of rows to display in each group (expand.rows). Server Default 5
	 * @link http://www.php.net/manual/en/solrquery.setexpandrows.php
	 * @param int $value 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandRows (int $value): SolrQuery {}

	/**
	 * Returns The number of rows to display in each group (expand.rows)
	 * @link http://www.php.net/manual/en/solrquery.getexpandrows.php
	 * @return int Returns the number of rows.
	 */
	public function getExpandRows (): int {}

	/**
	 * Sets the expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.setexpandquery.php
	 * @param string $q 
	 * @return SolrQuery SolrQuery
	 */
	public function setExpandQuery (string $q): SolrQuery {}

	/**
	 * Returns the expand query expand.q parameter
	 * @link http://www.php.net/manual/en/solrquery.getexpandquery.php
	 * @return array Returns the expand query.
	 */
	public function getExpandQuery (): array {}

	/**
	 * Overrides main filter query, determines which documents to include in the main group
	 * @link http://www.php.net/manual/en/solrquery.addexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function addExpandFilterQuery (string $fq): SolrQuery {}

	/**
	 * Removes an expand filter query
	 * @link http://www.php.net/manual/en/solrquery.removeexpandfilterquery.php
	 * @param string $fq 
	 * @return SolrQuery SolrQuery
	 */
	public function removeExpandFilterQuery (string $fq): SolrQuery {}

	/**
	 * Returns the expand filter queries
	 * @link http://www.php.net/manual/en/solrquery.getexpandfilterqueries.php
	 * @return array Returns the expand filter queries.
	 */
	public function getExpandFilterQueries (): array {}

	/**
	 * Enables or disables highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlight.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlight (bool $flag): SolrQuery {}

	/**
	 * Returns the state of the hl parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlight.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlight (): bool {}

	/**
	 * Maps to hl.fl
	 * @link http://www.php.net/manual/en/solrquery.addhighlightfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addHighlightField (string $field): SolrQuery {}

	/**
	 * Removes one of the fields used for highlighting
	 * @link http://www.php.net/manual/en/solrquery.removehighlightfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeHighlightField (string $field): SolrQuery {}

	/**
	 * Returns all the fields that Solr should generate highlighted snippets for
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getHighlightFields (): array {}

	/**
	 * Sets the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsnippets.php
	 * @param int $value 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSnippets (int $value, string $field_override = null): SolrQuery {}

	/**
	 * Returns the maximum number of highlighted snippets to generate per field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsnippets.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightSnippets (string $field_override = null): int {}

	/**
	 * The size of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragsize.php
	 * @param int $size 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragsize (int $size, string $field_override = null): SolrQuery {}

	/**
	 * Returns the number of characters of fragments to consider for highlighting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragsize.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success or null if not set.
	 */
	public function getHighlightFragsize (string $field_override = null): int {}

	/**
	 * Whether or not to collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmergecontiguous.php
	 * @param bool $flag 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMergeContiguous (bool $flag, string $field_override = null): SolrQuery {}

	/**
	 * Returns whether or not the collapse contiguous fragments into a single fragment
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmergecontiguous.php
	 * @param string $field_override [optional] 
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightMergeContiguous (string $field_override = null): bool {}

	/**
	 * Require field matching during highlighting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightrequirefieldmatch.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRequireFieldMatch (bool $flag): SolrQuery {}

	/**
	 * Returns if a field will only be highlighted if the query matched in this particular field
	 * @link http://www.php.net/manual/en/solrquery.gethighlightrequirefieldmatch.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightRequireFieldMatch (): bool {}

	/**
	 * Specifies the number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxanalyzedchars.php
	 * @param int $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAnalyzedChars (int $value): SolrQuery {}

	/**
	 * Returns the maximum number of characters into a document to look for suitable snippets
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxanalyzedchars.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightMaxAnalyzedChars (): int {}

	/**
	 * Specifies the backup field to use
	 * @link http://www.php.net/manual/en/solrquery.sethighlightalternatefield.php
	 * @param string $field 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightAlternateField (string $field, string $field_override = null): SolrQuery {}

	/**
	 * Returns the highlight field to use as backup or default
	 * @link http://www.php.net/manual/en/solrquery.gethighlightalternatefield.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightAlternateField (string $field_override = null): string {}

	/**
	 * Sets the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.sethighlightmaxalternatefieldlength.php
	 * @param int $fieldLength 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightMaxAlternateFieldLength (int $fieldLength, string $field_override = null): SolrQuery {}

	/**
	 * Returns the maximum number of characters of the field to return
	 * @link http://www.php.net/manual/en/solrquery.gethighlightmaxalternatefieldlength.php
	 * @param string $field_override [optional] 
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightMaxAlternateFieldLength (string $field_override = null): int {}

	/**
	 * Specify a formatter for the highlight output
	 * @link http://www.php.net/manual/en/solrquery.sethighlightformatter.php
	 * @param string $formatter 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function setHighlightFormatter (string $formatter, string $field_override = null): SolrQuery {}

	/**
	 * Returns the formatter for the highlighted output
	 * @link http://www.php.net/manual/en/solrquery.gethighlightformatter.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightFormatter (string $field_override = null): string {}

	/**
	 * Sets the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepre.php
	 * @param string $simplePre 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightSimplePre (string $simplePre, string $field_override = null): SolrQuery {}

	/**
	 * Returns the text which appears before a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepre.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightSimplePre (string $field_override = null): string {}

	/**
	 * Sets the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.sethighlightsimplepost.php
	 * @param string $simplePost 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns an instance of SolrQuery.
	 */
	public function setHighlightSimplePost (string $simplePost, string $field_override = null): SolrQuery {}

	/**
	 * Returns the text which appears after a highlighted term
	 * @link http://www.php.net/manual/en/solrquery.gethighlightsimplepost.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightSimplePost (string $field_override = null): string {}

	/**
	 * Sets a text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.sethighlightfragmenter.php
	 * @param string $fragmenter 
	 * @param string $field_override [optional] 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightFragmenter (string $fragmenter, string $field_override = null): SolrQuery {}

	/**
	 * Returns the text snippet generator for highlighted text
	 * @link http://www.php.net/manual/en/solrquery.gethighlightfragmenter.php
	 * @param string $field_override [optional] 
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightFragmenter (string $field_override = null): string {}

	/**
	 * Whether to highlight phrase terms only when they appear within the query phrase
	 * @link http://www.php.net/manual/en/solrquery.sethighlightusephrasehighlighter.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightUsePhraseHighlighter (bool $flag): SolrQuery {}

	/**
	 * Returns the state of the hl.usePhraseHighlighter parameter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightusephrasehighlighter.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightUsePhraseHighlighter (): bool {}

	/**
	 * Use SpanScorer to highlight phrase terms
	 * @link http://www.php.net/manual/en/solrquery.sethighlighthighlightmultiterm.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightHighlightMultiTerm (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not to enable highlighting for range/wildcard/fuzzy/prefix queries
	 * @link http://www.php.net/manual/en/solrquery.gethighlighthighlightmultiterm.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getHighlightHighlightMultiTerm (): bool {}

	/**
	 * Sets the factor by which the regex fragmenter can stray from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexslop.php
	 * @param float $factor 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexSlop (float $factor): SolrQuery {}

	/**
	 * Returns the deviation factor from the ideal fragment size
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexslop.php
	 * @return float Returns a float on success and null if not set.
	 */
	public function getHighlightRegexSlop (): float {}

	/**
	 * Specify the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexpattern.php
	 * @param string $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexPattern (string $value): SolrQuery {}

	/**
	 * Returns the regular expression for fragmenting
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexpattern.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getHighlightRegexPattern (): string {}

	/**
	 * Specify the maximum number of characters to analyze
	 * @link http://www.php.net/manual/en/solrquery.sethighlightregexmaxanalyzedchars.php
	 * @param int $maxAnalyzedChars 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setHighlightRegexMaxAnalyzedChars (int $maxAnalyzedChars): SolrQuery {}

	/**
	 * Returns the maximum number of characters from a field when using the regex fragmenter
	 * @link http://www.php.net/manual/en/solrquery.gethighlightregexmaxanalyzedchars.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getHighlightRegexMaxAnalyzedChars (): int {}

	/**
	 * Enables or disables the Stats component
	 * @link http://www.php.net/manual/en/solrquery.setstats.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setStats (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not stats is enabled
	 * @link http://www.php.net/manual/en/solrquery.getstats.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getStats (): bool {}

	/**
	 * Maps to stats.field parameter
	 * @link http://www.php.net/manual/en/solrquery.addstatsfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addStatsField (string $field): SolrQuery {}

	/**
	 * Removes one of the stats.field parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsField (string $field): SolrQuery {}

	/**
	 * Returns all the statistics fields
	 * @link http://www.php.net/manual/en/solrquery.getstatsfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getStatsFields (): array {}

	/**
	 * Requests a return of sub results for values within the given facet
	 * @link http://www.php.net/manual/en/solrquery.addstatsfacet.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addStatsFacet (string $field): SolrQuery {}

	/**
	 * Removes one of the stats.facet parameters
	 * @link http://www.php.net/manual/en/solrquery.removestatsfacet.php
	 * @param string $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeStatsFacet (string $value): SolrQuery {}

	/**
	 * Returns all the stats facets that were set
	 * @link http://www.php.net/manual/en/solrquery.getstatsfacets.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getStatsFacets (): array {}

	/**
	 * Enables or disables moreLikeThis
	 * @link http://www.php.net/manual/en/solrquery.setmlt.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMlt (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not MoreLikeThis results should be enabled
	 * @link http://www.php.net/manual/en/solrquery.getmlt.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getMlt (): bool {}

	/**
	 * Set the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.setmltcount.php
	 * @param int $count 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltCount (int $count): SolrQuery {}

	/**
	 * Returns the number of similar documents to return for each result
	 * @link http://www.php.net/manual/en/solrquery.getmltcount.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltCount (): int {}

	/**
	 * Sets a field to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.addmltfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addMltField (string $field): SolrQuery {}

	/**
	 * Removes one of the moreLikeThis fields
	 * @link http://www.php.net/manual/en/solrquery.removemltfield.php
	 * @param string $field 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeMltField (string $field): SolrQuery {}

	/**
	 * Returns all the fields to use for similarity
	 * @link http://www.php.net/manual/en/solrquery.getmltfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getMltFields (): array {}

	/**
	 * Maps to mlt.qf
	 * @link http://www.php.net/manual/en/solrquery.addmltqueryfield.php
	 * @param string $field 
	 * @param float $boost 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function addMltQueryField (string $field, float $boost): SolrQuery {}

	/**
	 * Removes one of the moreLikeThis query fields
	 * @link http://www.php.net/manual/en/solrquery.removemltqueryfield.php
	 * @param string $queryField 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function removeMltQueryField (string $queryField): SolrQuery {}

	/**
	 * Returns the query fields and their boosts
	 * @link http://www.php.net/manual/en/solrquery.getmltqueryfields.php
	 * @return array Returns an array on success and null if not set.
	 */
	public function getMltQueryFields (): array {}

	/**
	 * Sets the frequency below which terms will be ignored in the source docs
	 * @link http://www.php.net/manual/en/solrquery.setmltmintermfrequency.php
	 * @param int $minTermFrequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinTermFrequency (int $minTermFrequency): SolrQuery {}

	/**
	 * Returns the frequency below which terms will be ignored in the source document
	 * @link http://www.php.net/manual/en/solrquery.getmltmintermfrequency.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMinTermFrequency (): int {}

	/**
	 * Sets the mltMinDoc frequency
	 * @link http://www.php.net/manual/en/solrquery.setmltmindocfrequency.php
	 * @param int $minDocFrequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinDocFrequency (int $minDocFrequency): SolrQuery {}

	/**
	 * Returns the treshold frequency at which words will be ignored which do not occur in at least this many docs
	 * @link http://www.php.net/manual/en/solrquery.getmltmindocfrequency.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMinDocFrequency (): int {}

	/**
	 * Sets the minimum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltminwordlength.php
	 * @param int $minWordLength 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMinWordLength (int $minWordLength): SolrQuery {}

	/**
	 * Returns the minimum word length below which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltminwordlength.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMinWordLength (): int {}

	/**
	 * Sets the maximum word length
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxwordlength.php
	 * @param int $maxWordLength 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxWordLength (int $maxWordLength): SolrQuery {}

	/**
	 * Returns the maximum word length above which words will be ignored
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxwordlength.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMaxWordLength (): int {}

	/**
	 * Specifies the maximum number of tokens to parse
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumtokens.php
	 * @param int $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumTokens (int $value): SolrQuery {}

	/**
	 * Returns the maximum number of tokens to parse in each document field that is not stored with TermVector support
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumtokens.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMaxNumTokens (): int {}

	/**
	 * Sets the maximum number of query terms included
	 * @link http://www.php.net/manual/en/solrquery.setmltmaxnumqueryterms.php
	 * @param int $value 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltMaxNumQueryTerms (int $value): SolrQuery {}

	/**
	 * Returns the maximum number of query terms that will be included in any generated query
	 * @link http://www.php.net/manual/en/solrquery.getmltmaxnumqueryterms.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getMltMaxNumQueryTerms (): int {}

	/**
	 * Set if the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.setmltboost.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setMltBoost (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not the query will be boosted by the interesting term relevance
	 * @link http://www.php.net/manual/en/solrquery.getmltboost.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getMltBoost (): bool {}

	/**
	 * Enables or disables the TermsComponent
	 * @link http://www.php.net/manual/en/solrquery.setterms.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTerms (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not the TermsComponent is enabled
	 * @link http://www.php.net/manual/en/solrquery.getterms.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTerms (): bool {}

	/**
	 * Sets the name of the field to get the Terms from
	 * @link http://www.php.net/manual/en/solrquery.settermsfield.php
	 * @param string $fieldname 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsField (string $fieldname): SolrQuery {}

	/**
	 * Returns the field from which the terms are retrieved
	 * @link http://www.php.net/manual/en/solrquery.gettermsfield.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsField (): string {}

	/**
	 * Specifies the Term to start from
	 * @link http://www.php.net/manual/en/solrquery.settermslowerbound.php
	 * @param string $lowerBound 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLowerBound (string $lowerBound): SolrQuery {}

	/**
	 * Returns the term to start at
	 * @link http://www.php.net/manual/en/solrquery.gettermslowerbound.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsLowerBound (): string {}

	/**
	 * Sets the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.settermsupperbound.php
	 * @param string $upperBound 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsUpperBound (string $upperBound): SolrQuery {}

	/**
	 * Returns the term to stop at
	 * @link http://www.php.net/manual/en/solrquery.gettermsupperbound.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsUpperBound (): string {}

	/**
	 * Include the lower bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludelowerbound.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeLowerBound (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not to include the lower bound in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludelowerbound.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTermsIncludeLowerBound (): bool {}

	/**
	 * Include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.settermsincludeupperbound.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsIncludeUpperBound (bool $flag): SolrQuery {}

	/**
	 * Returns whether or not to include the upper bound term in the result set
	 * @link http://www.php.net/manual/en/solrquery.gettermsincludeupperbound.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTermsIncludeUpperBound (): bool {}

	/**
	 * Sets the minimum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmincount.php
	 * @param int $frequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMinCount (int $frequency): SolrQuery {}

	/**
	 * Returns the minimum document frequency to return in order to be included
	 * @link http://www.php.net/manual/en/solrquery.gettermsmincount.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsMinCount (): int {}

	/**
	 * Sets the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.settermsmaxcount.php
	 * @param int $frequency 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsMaxCount (int $frequency): SolrQuery {}

	/**
	 * Returns the maximum document frequency
	 * @link http://www.php.net/manual/en/solrquery.gettermsmaxcount.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsMaxCount (): int {}

	/**
	 * Restrict matches to terms that start with the prefix
	 * @link http://www.php.net/manual/en/solrquery.settermsprefix.php
	 * @param string $prefix 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsPrefix (string $prefix): SolrQuery {}

	/**
	 * Returns the term prefix
	 * @link http://www.php.net/manual/en/solrquery.gettermsprefix.php
	 * @return string Returns a string on success and null if not set.
	 */
	public function getTermsPrefix (): string {}

	/**
	 * Sets the maximum number of terms to return
	 * @link http://www.php.net/manual/en/solrquery.settermslimit.php
	 * @param int $limit 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsLimit (int $limit): SolrQuery {}

	/**
	 * Returns the maximum number of terms Solr should return
	 * @link http://www.php.net/manual/en/solrquery.gettermslimit.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsLimit (): int {}

	/**
	 * Return the raw characters of the indexed term
	 * @link http://www.php.net/manual/en/solrquery.settermsreturnraw.php
	 * @param bool $flag 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsReturnRaw (bool $flag): SolrQuery {}

	/**
	 * Whether or not to return raw characters
	 * @link http://www.php.net/manual/en/solrquery.gettermsreturnraw.php
	 * @return bool Returns a boolean on success and null if not set.
	 */
	public function getTermsReturnRaw (): bool {}

	/**
	 * Specifies how to sort the returned terms
	 * @link http://www.php.net/manual/en/solrquery.settermssort.php
	 * @param int $sortType 
	 * @return SolrQuery Returns the current SolrQuery object, if the return value is used.
	 */
	public function setTermsSort (int $sortType): SolrQuery {}

	/**
	 * Returns an integer indicating how terms are sorted
	 * @link http://www.php.net/manual/en/solrquery.gettermssort.php
	 * @return int Returns an integer on success and null if not set.
	 */
	public function getTermsSort (): int {}

	/**
	 * Sets the parameter to the specified value
	 * @link http://www.php.net/manual/en/solrparams.setparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on value.
	 */
	public function setParam (string $name, string $value): SolrParams {}

	/**
	 * Adds a parameter to the object
	 * @link http://www.php.net/manual/en/solrparams.addparam.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParam object on success and false on failure.
	 */
	public function addParam (string $name, string $value): SolrParams {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * Returns all the name-value pair parameters in the object
	 * @link http://www.php.net/manual/en/solrparams.tostring.php
	 * @param bool $url_encode [optional] 
	 * @return string Returns a string on success and false on failure.
	 */
	public function toString (bool $url_encode = false): string {}

	/**
	 * Returns an array of non URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getparams.php
	 * @return array Returns an array of non URL-encoded parameters
	 */
	public function getParams (): array {}

	/**
	 * Returns a parameter value
	 * @link http://www.php.net/manual/en/solrparams.getparam.php
	 * @param string $param_name [optional] 
	 * @return mixed Returns a string or an array depending on the type of the parameter
	 */
	public function getParam (string $param_name = null): mixed {}

	/**
	 * Returns an array of URL-encoded parameters
	 * @link http://www.php.net/manual/en/solrparams.getpreparedparams.php
	 * @return array Returns an array on URL-encoded parameters
	 */
	public function getPreparedParams (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function __clone () {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.serialize.php
	 * @return string Used for custom serialization
	 */
	public function serialize (): string {}

	/**
	 * Used for custom serialization
	 * @link http://www.php.net/manual/en/solrparams.unserialize.php
	 * @param string $serialized 
	 * @return void None
	 */
	public function unserialize (string $serialized): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __serialize () {}

	/**
	 * {@inheritdoc}
	 * @param array $data
	 */
	public function __unserialize (array $data) {}

	/**
	 * Alias of SolrParams::addParam
	 * @link http://www.php.net/manual/en/solrparams.add.php
	 * @param string $name 
	 * @param string $value 
	 * @return SolrParams Returns a SolrParams instance on success
	 */
	public function add (string $name, string $value): SolrParams {}

	/**
	 * Alias of SolrParams::setParam
	 * @link http://www.php.net/manual/en/solrparams.set.php
	 * @param string $name 
	 * @param string $value 
	 * @return void Returns an instance of the SolrParams object on success
	 */
	public function set (string $name, string $value): void {}

	/**
	 * Alias of SolrParams::getParam
	 * @link http://www.php.net/manual/en/solrparams.get.php
	 * @param string $param_name 
	 * @return mixed Returns an array or string depending on the type of parameter
	 */
	public function get (string $param_name): mixed {}

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


	/**
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $filename
	 * @param SolrModifiableParams $params
	 */
	public static function createFromFile ($filename = null, SolrModifiableParams &$params) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $content
	 * @param mixed $mime_type
	 * @param SolrModifiableParams $params
	 */
	public static function createFromStream ($content = null, $mime_type = null, SolrModifiableParams &$params) {}

	/**
	 * {@inheritdoc}
	 */
	public function __clone () {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
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
	 * @param string $field [optional] The field name to collapse on.
	 * <p>In order to collapse a result. The field type must be a single valued String, Int or Float.</p>
	 * @return string 
	 */
	public function __construct (string $field = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Sets the field to collapse on
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setfield.php
	 * @param string $fieldName 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setField (string $fieldName): SolrCollapseFunction {}

	/**
	 * Returns the field that is being collapsed on
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getfield.php
	 * @return string 
	 */
	public function getField (): string {}

	/**
	 * Selects the group heads by the max value of a numeric field or function query
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setmax.php
	 * @param string $max 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setMax (string $max): SolrCollapseFunction {}

	/**
	 * Returns max parameter
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getmax.php
	 * @return string 
	 */
	public function getMax (): string {}

	/**
	 * Sets the initial size of the collapse data structures when collapsing on a numeric field only
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setmin.php
	 * @param string $min 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setMin (string $min): SolrCollapseFunction {}

	/**
	 * Returns min parameter
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getmin.php
	 * @return string 
	 */
	public function getMin (): string {}

	/**
	 * Sets the initial size of the collapse data structures when collapsing on a numeric field only
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setsize.php
	 * @param int $size 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setSize (int $size): SolrCollapseFunction {}

	/**
	 * Returns size parameter
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getsize.php
	 * @return int 
	 */
	public function getSize (): int {}

	/**
	 * Sets collapse hint
	 * @link http://www.php.net/manual/en/solrcollapsefunction.sethint.php
	 * @param string $hint Currently there is only one hint available "top_fc", which stands for top level FieldCache
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setHint (string $hint): SolrCollapseFunction {}

	/**
	 * Returns collapse hint
	 * @link http://www.php.net/manual/en/solrcollapsefunction.gethint.php
	 * @return string 
	 */
	public function getHint (): string {}

	/**
	 * Sets the NULL Policy
	 * @link http://www.php.net/manual/en/solrcollapsefunction.setnullpolicy.php
	 * @param string $nullPolicy 
	 * @return SolrCollapseFunction SolrCollapseFunction
	 */
	public function setNullPolicy (string $nullPolicy): SolrCollapseFunction {}

	/**
	 * Returns null policy
	 * @link http://www.php.net/manual/en/solrcollapsefunction.getnullpolicy.php
	 * @return string 
	 */
	public function getNullPolicy (): string {}

	/**
	 * Returns a string representing the constructed collapse function
	 * @link http://www.php.net/manual/en/solrcollapsefunction.tostring.php
	 * @return string 
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __sleep () {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

}

/**
 * Represents a response from the Solr server.
 * @link http://www.php.net/manual/en/class.solrresponse.php
 */
abstract class SolrResponse  {
	/**
	 * Documents should be parsed as SolrObject instances
	const PARSE_SOLR_OBJ = 0;
	/**
	 * Documents should be parsed as SolrDocument instances.
	const PARSE_SOLR_DOC = 1;


	/**
	 * The http status of the response.
	 * @var int
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_status
	 */
	protected int $http_status;

	/**
	 * Whether to parse the solr documents as SolrObject or SolrDocument instances.
	 * @var int
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.parser_mode
	 */
	protected int $parser_mode;

	/**
	 * Was there an error during the request
	 * @var bool
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.success
	 */
	protected bool $success;

	/**
	 * Detailed message on http status
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_status_message
	 */
	protected string $http_status_message;

	/**
	 * The request URL
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_request_url
	 */
	protected string $http_request_url;

	/**
	 * A string of raw headers sent during the request.
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_raw_request_headers
	 */
	protected string $http_raw_request_headers;

	/**
	 * The raw request sent to the server
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_raw_request
	 */
	protected string $http_raw_request;

	/**
	 * Response headers from the Solr server.
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_raw_response_headers
	 */
	protected string $http_raw_response_headers;

	/**
	 * The response message from the server.
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_raw_response
	 */
	protected string $http_raw_response;

	/**
	 * The response in PHP serialized format.
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrresponse.php#solrresponse.props.http_digested_response
	 */
	protected string $http_digested_response;

	protected $response_writer;

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int Returns the HTTP status of the response.
	 */
	public function getHttpStatus (): int {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string Returns more details on the HTTP status
	 */
	public function getHttpStatusMessage (): string {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool Returns true if it was successful and false if it was not.
	 */
	public function success (): bool {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string Returns the full URL the request was sent to
	 */
	public function getRequestUrl (): string {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string Returns the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders (): string {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string Returns the raw request sent to the Solr server
	 */
	public function getRawRequest (): string {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string Returns the raw response headers from the server.
	 */
	public function getRawResponseHeaders (): string {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string Returns the raw response from the server.
	 */
	public function getRawResponse (): string {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string Returns the XML response as serialized PHP data
	 */
	public function getDigestedResponse (): string {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParseMode (int $parser_mode = null): bool {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject Returns a SolrObject representing the XML response from the server
	 */
	public function getResponse (): SolrObject {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayResponse () {}

}

/**
 * Represents a response to a query request.
 * @link http://www.php.net/manual/en/class.solrqueryresponse.php
 */
final class SolrQueryResponse extends SolrResponse  {
	/**
	 * Documents should be parsed as SolrObject instances
	const PARSE_SOLR_OBJ = 0;
	/**
	 * Documents should be parsed as SolrDocument instances.
	const PARSE_SOLR_DOC = 1;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrqueryresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int Returns the HTTP status of the response.
	 */
	public function getHttpStatus (): int {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string Returns more details on the HTTP status
	 */
	public function getHttpStatusMessage (): string {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool Returns true if it was successful and false if it was not.
	 */
	public function success (): bool {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string Returns the full URL the request was sent to
	 */
	public function getRequestUrl (): string {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string Returns the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders (): string {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string Returns the raw request sent to the Solr server
	 */
	public function getRawRequest (): string {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string Returns the raw response headers from the server.
	 */
	public function getRawResponseHeaders (): string {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string Returns the raw response from the server.
	 */
	public function getRawResponse (): string {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string Returns the XML response as serialized PHP data
	 */
	public function getDigestedResponse (): string {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParseMode (int $parser_mode = null): bool {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject Returns a SolrObject representing the XML response from the server
	 */
	public function getResponse (): SolrObject {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayResponse () {}

}

/**
 * Represents a response to an update request.
 * @link http://www.php.net/manual/en/class.solrupdateresponse.php
 */
final class SolrUpdateResponse extends SolrResponse  {
	/**
	 * Documents should be parsed as SolrObject instances
	const PARSE_SOLR_OBJ = 0;
	/**
	 * Documents should be parsed as SolrDocument instances.
	const PARSE_SOLR_DOC = 1;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrupdateresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int Returns the HTTP status of the response.
	 */
	public function getHttpStatus (): int {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string Returns more details on the HTTP status
	 */
	public function getHttpStatusMessage (): string {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool Returns true if it was successful and false if it was not.
	 */
	public function success (): bool {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string Returns the full URL the request was sent to
	 */
	public function getRequestUrl (): string {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string Returns the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders (): string {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string Returns the raw request sent to the Solr server
	 */
	public function getRawRequest (): string {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string Returns the raw response headers from the server.
	 */
	public function getRawResponseHeaders (): string {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string Returns the raw response from the server.
	 */
	public function getRawResponse (): string {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string Returns the XML response as serialized PHP data
	 */
	public function getDigestedResponse (): string {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParseMode (int $parser_mode = null): bool {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject Returns a SolrObject representing the XML response from the server
	 */
	public function getResponse (): SolrObject {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayResponse () {}

}

/**
 * Represents a response to a ping request to the server
 * @link http://www.php.net/manual/en/class.solrpingresponse.php
 */
final class SolrPingResponse extends SolrResponse  {
	/**
	 * Documents should be parsed as SolrObject instances
	const PARSE_SOLR_OBJ = 0;
	/**
	 * Documents should be parsed as SolrDocument instances.
	const PARSE_SOLR_DOC = 1;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrpingresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Returns the response from the server
	 * @link http://www.php.net/manual/en/solrpingresponse.getresponse.php
	 * @return string Returns an empty string.
	 */
	public function getResponse (): string {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int Returns the HTTP status of the response.
	 */
	public function getHttpStatus (): int {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string Returns more details on the HTTP status
	 */
	public function getHttpStatusMessage (): string {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool Returns true if it was successful and false if it was not.
	 */
	public function success (): bool {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string Returns the full URL the request was sent to
	 */
	public function getRequestUrl (): string {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string Returns the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders (): string {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string Returns the raw request sent to the Solr server
	 */
	public function getRawRequest (): string {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string Returns the raw response headers from the server.
	 */
	public function getRawResponseHeaders (): string {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string Returns the raw response from the server.
	 */
	public function getRawResponse (): string {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string Returns the XML response as serialized PHP data
	 */
	public function getDigestedResponse (): string {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParseMode (int $parser_mode = null): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getArrayResponse () {}

}

/**
 * Represents a response from the solr server.
 * @link http://www.php.net/manual/en/class.solrgenericresponse.php
 */
final class SolrGenericResponse extends SolrResponse  {
	/**
	 * Documents should be parsed as SolrObject instances
	const PARSE_SOLR_OBJ = 0;
	/**
	 * Documents should be parsed as SolrDocument instances.
	const PARSE_SOLR_DOC = 1;


	/**
	 * Constructor
	 * @link http://www.php.net/manual/en/solrgenericresponse.construct.php
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Returns the HTTP status of the response
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatus.php
	 * @return int Returns the HTTP status of the response.
	 */
	public function getHttpStatus (): int {}

	/**
	 * Returns more details on the HTTP status
	 * @link http://www.php.net/manual/en/solrresponse.gethttpstatusmessage.php
	 * @return string Returns more details on the HTTP status
	 */
	public function getHttpStatusMessage (): string {}

	/**
	 * Was the request a success
	 * @link http://www.php.net/manual/en/solrresponse.success.php
	 * @return bool Returns true if it was successful and false if it was not.
	 */
	public function success (): bool {}

	/**
	 * Returns the full URL the request was sent to
	 * @link http://www.php.net/manual/en/solrresponse.getrequesturl.php
	 * @return string Returns the full URL the request was sent to
	 */
	public function getRequestUrl (): string {}

	/**
	 * Returns the raw request headers sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequestheaders.php
	 * @return string Returns the raw request headers sent to the Solr server
	 */
	public function getRawRequestHeaders (): string {}

	/**
	 * Returns the raw request sent to the Solr server
	 * @link http://www.php.net/manual/en/solrresponse.getrawrequest.php
	 * @return string Returns the raw request sent to the Solr server
	 */
	public function getRawRequest (): string {}

	/**
	 * Returns the raw response headers from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponseheaders.php
	 * @return string Returns the raw response headers from the server.
	 */
	public function getRawResponseHeaders (): string {}

	/**
	 * Returns the raw response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getrawresponse.php
	 * @return string Returns the raw response from the server.
	 */
	public function getRawResponse (): string {}

	/**
	 * Returns the XML response as serialized PHP data
	 * @link http://www.php.net/manual/en/solrresponse.getdigestedresponse.php
	 * @return string Returns the XML response as serialized PHP data
	 */
	public function getDigestedResponse (): string {}

	/**
	 * Sets the parse mode
	 * @link http://www.php.net/manual/en/solrresponse.setparsemode.php
	 * @param int $parser_mode [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setParseMode (int $parser_mode = null): bool {}

	/**
	 * Returns a SolrObject representing the XML response from the server
	 * @link http://www.php.net/manual/en/solrresponse.getresponse.php
	 * @return SolrObject Returns a SolrObject representing the XML response from the server
	 */
	public function getResponse (): SolrObject {}

	/**
	 * {@inheritdoc}
	 */
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
	 * @param string $str 
	 * @return string|false Returns the escaped string or false on failure.
	 */
	public static function escapeQueryChars (string $str): string|false {}

	/**
	 * Prepares a phrase from an unescaped lucene string
	 * @link http://www.php.net/manual/en/solrutils.queryphrase.php
	 * @param string $str 
	 * @return string Returns the phrase contained in double quotes.
	 */
	public static function queryPhrase (string $str): string {}

	/**
	 * Parses an response XML string into a SolrObject
	 * @link http://www.php.net/manual/en/solrutils.digestxmlresponse.php
	 * @param string $xmlresponse 
	 * @param int $parse_mode [optional] 
	 * @return SolrObject Returns the SolrObject representing the XML response.
	 * <p>If the parse_mode parameter is set to SolrResponse::PARSE_SOLR_OBJ Solr documents will be parses as SolrObject instances.</p>
	 * <p>If it is set to SolrResponse::PARSE_SOLR_DOC, they will be parsed as SolrDocument instances.</p>
	 */
	public static function digestXmlResponse (string $xmlresponse, int $parse_mode = null): SolrObject {}

	/**
	 * {@inheritdoc}
	 * @param mixed $jsonResponse
	 */
	public static function digestJsonResponse ($jsonResponse = null) {}

	/**
	 * Returns the current version of the Solr extension
	 * @link http://www.php.net/manual/en/solrutils.getsolrversion.php
	 * @return string The current version of the Apache Solr extension.
	 */
	public static function getSolrVersion (): string {}

	/**
	 * {@inheritdoc}
	 */
	public static function getSolrStats () {}

}

/**
 * This is the base class for all exception thrown by the Solr extension classes.
 * @link http://www.php.net/manual/en/class.solrexception.php
 */
class SolrException extends Exception implements Throwable, Stringable {

	/**
	 * The line in c-space source file where exception was generated
	 * @var int
	 * @link http://www.php.net/manual/en/class.solrexception.php#solrexception.props.sourceline
	 */
	protected int $sourceline;

	/**
	 * The c-space source file where exception was generated
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrexception.php#solrexception.props.sourcefile
	 */
	protected string $sourcefile;

	/**
	 * The c-space function where exception was generated
	 * @var string
	 * @link http://www.php.net/manual/en/class.solrexception.php#solrexception.props.zif_name
	 */
	protected string $zif_name;

	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrexception.getinternalinfo.php
	 * @return array Returns an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo (): array {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * This object is thrown when an illegal or unsupported operation is performed on an object.
 * @link http://www.php.net/manual/en/class.solrillegaloperationexception.php
 */
class SolrIllegalOperationException extends SolrException implements Stringable, Throwable {

	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrillegaloperationexception.getinternalinfo.php
	 * @return array Returns an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo (): array {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * This object is thrown when an illegal or invalid argument is passed to a method.
 * @link http://www.php.net/manual/en/class.solrillegalargumentexception.php
 */
class SolrIllegalArgumentException extends SolrException implements Stringable, Throwable {

	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrillegalargumentexception.getinternalinfo.php
	 * @return array Returns an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo (): array {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * An exception thrown when there is an error while making a request to the server from the client.
 * @link http://www.php.net/manual/en/class.solrclientexception.php
 */
class SolrClientException extends SolrException implements Stringable, Throwable {

	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrclientexception.getinternalinfo.php
	 * @return array Returns an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo (): array {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * An exception thrown when there is an error produced by the Solr Server itself.
 * @link http://www.php.net/manual/en/class.solrserverexception.php
 */
class SolrServerException extends SolrException implements Stringable, Throwable {

	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrserverexception.getinternalinfo.php
	 * @return array Returns an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo (): array {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * @link http://www.php.net/manual/en/class.solrmissingmandatoryparameterexception.php
 */
class SolrMissingMandatoryParameterException extends SolrException implements Stringable, Throwable {

	/**
	 * Returns internal information where the Exception was thrown
	 * @link http://www.php.net/manual/en/solrexception.getinternalinfo.php
	 * @return array Returns an array containing internal information where the error was thrown. Used only for debugging by extension developers.
	 */
	public function getInternalInfo (): array {}

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

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
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

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
 * Returns the current version of the Apache Solr extension
 * @link http://www.php.net/manual/en/function.solr-get-version.php
 * @return string It returns a string on success and false on failure.
 */
function solr_get_version (): string {}


/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 * @var int
 */
define ('SOLR_MAJOR_VERSION', 2);

/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 * @var int
 */
define ('SOLR_MINOR_VERSION', 6);

/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 * @var int
 */
define ('SOLR_PATCH_VERSION', 0);

/**
 * 
 * @link http://www.php.net/manual/en/solr.constants.php
 * @var string
 */
define ('SOLR_EXTENSION_VERSION', "2.6.0");

// End of solr v.2.6.0
