<?php

/**
 * @return  object  
 * @version Buran 
 * @desc    Java Exception object, if there was an exception, false otherwise 
 */
function java_last_exception_get() { }

/**
 * @return  void 
 * @version Buran 
 * @desc    Clear last Java exception object record
 */
function java_last_exception_clear() { }

/**
 * @return  void  
 * @param   ignore bool  
 * @version Buran 
 * @desc    Set case sensitivity for Java calls. if set, Java attribute and method names would be resolved disregarding case. 
 */
function java_set_ignore_case($ignore) { }

/**
 * @return  array  
 * @version Buran 
 * @desc    Set encoding for strings received by Java from PHP. Default is UTF-8. 
 */
function zds_license_info($encoding) { }

/**
 * @return  void 
 * @param   throw  int 
 * @version Buran 
 * @desc    Control if exceptions are thrown on Java exception.
 * @since   PHP5. 
 */
function java_throw_exceptions($throw) { }

class Java {

	/**
	 * Returns a new Java object 
	 *
	 * @return object
	 * @param  class_name string
     * @param  parameters mixed[optional]
	 * @vararg ... mixed 
	 */
	
    function __construct($class_name, $parameters) { }
	
};
?>