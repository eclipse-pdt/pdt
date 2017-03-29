<?php

/*Requires:*/
require_once '#RequireLocation#';
/*:Requires*/

/**
 * #MasterElementName#() test case.
 */
class #TestClassName# extends #TestSuperClassName#
{

	/**
	 * Prepares the environment before running a test.
	 */
	protected function setUp()
	{
		parent::setUp();
		
		// TODO Auto-generated #TestClassName#::setUp()
	}
	
	/**
	 * Cleans up the environment after running a test.
	 */
	protected function tearDown()
	{
		// TODO Auto-generated #TestClassName#::tearDown()

		parent::tearDown();
	}

	/**
	 * Constructs the test case.
	 */
	public function __construct()
	{
		// TODO Auto-generated constructor
	}
	
	/**
	 * Tests #MasterElementName#()
	 */
	public function test#MasterElementNameCamelized#()
	{
		// TODO Auto-generated #TestClassName#->test#MasterElementNameCamelized#
		$this->markTestIncomplete("#MasterElementName#() test not implemented");

		#MasterElementName#(/* parameters */);
	}
}
/*Ignore:*/
/*
	Single Variables:
		MasterElementName
		TestCaseClassLocation
		TestCaseClassName
		TestCaseSuperClassLocation
		TestCaseSuperClassName
	Blocks:
		Requires
		Ignore
*/
/*:Ignore*/