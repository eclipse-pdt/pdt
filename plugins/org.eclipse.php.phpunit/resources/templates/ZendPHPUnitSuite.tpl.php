<?php

/*Requires:*/
require_once '#RequireLocation#';
/*:Requires*/

/**
 * Static test suite.
 */
class #TestClassName# extends #TestSuperClassName#
{

	/**
	 * Constructs the test suite handler.
	 */
	public function __construct()
	{
		$this->setName('#TestClassName#');
		/*Tests:*/
        $this->addTestSuite('#TestName#');
        /*:Tests*/
	}

	/**
	 * Creates the suite.
	 */
    public static function suite()
    {
        return new self();
    }
}
/*Ignore:*/
/*
	Single Variables:
		TestClassName
		TestSuperClassName
	Multiple Variables:
		TestName
	Blocks:
		Requires
		Tests
		Ignore
*/
/*:Ignore*/