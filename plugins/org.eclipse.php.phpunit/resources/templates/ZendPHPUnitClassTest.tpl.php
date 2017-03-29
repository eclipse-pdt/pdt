<?php

/*Requires:*/
require_once '#RequireLocation#';
/*:Requires*/

/**
 * #MasterElementName# test case.
 */
class #TestClassName# extends #TestSuperClassName#
{
	/*MasterElementDefinition:*/
	/**
	 * @var #MasterElementName#
	 */
	private $#MasterElementNameVar#;
	/*:MasterElementDefinition*/

	/**
	 * Prepares the environment before running a test.
	 */
	protected function setUp()
	{
		parent::setUp();

		// TODO Auto-generated #TestClassName#::setUp()
		/*MasterElementConstructor:*/
		$this->#MasterElementNameVar# = new #MasterElementName#(/* parameters */);
		/*:MasterElementConstructor*/
	}

	/**
	 * Cleans up the environment after running a test.
	 */
	protected function tearDown()
	{
		// TODO Auto-generated #TestClassName#::tearDown()
		/*MasterElementDestructor:*/
		$this->#MasterElementNameVar# = null;
		/*:MasterElementDestructor*/
		parent::tearDown();
	}

	/**
	 * Constructs the test case.
	 */
	public function __construct()
	{
		// TODO Auto-generated constructor
	}

	/*Methods:*/
	/*DynamicMethod:*/
	/**
	 * Tests #MasterElementName#->#MethodName#()
	 */
	public function test#MethodNameCamelized#()
	{
		// TODO Auto-generated #TestClassName#->test#MethodNameCamelized#()
		$this->markTestIncomplete("#MethodName# test not implemented");
		/*MasterElementDynamicMethod:*/
		$this->#MasterElementNameVar#->#MethodName#(/* parameters */);
		/*:MasterElementDynamicMethod*/
	}
	/*:DynamicMethod*/
	/*StaticMethod:*/
	/**
	 * Tests #MasterElementName#::#MethodName#()
	 */
	public function test#MethodNameCamelized#()
	{
		// TODO Auto-generated #TestClassName#::test#MethodNameCamelized#()
		$this->markTestIncomplete("#MethodName# test not implemented");
		/*MasterElementStaticMethod:*/
		#MasterElementName#::#MethodName#(/* parameters */);
		/*:MasterElementStaticMethod*/
	}
	/*:StaticMethod*/
	/*:Methods*/
}
/*Ignore:*/
/*
	Single Variables:
		MasterElementName
		TestClassLocation
		TestClassName
		TestSuperClassLocation
		TestSuperClassName
	Multiple Variables:
		#MethodName#
	Blocks:
		Requires
		Methods
		DynamicMethod
		StaticMethod
		Ignore
		MasterElementDefinition
		MasterElementConstructor
		MasterElementDynamicMethod
		MasterElementStaticMethod
		MasterElementDestructor
*/
/*:Ignore*/
