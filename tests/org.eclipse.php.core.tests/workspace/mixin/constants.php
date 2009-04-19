<?php

define ('GLOBAL_CONST', "Hello");

class A {
	const CLASS_CONST = 0;

	function foo() {
		function bar() {
			define ('GLOBAL_CONST2', 0);
		}
	}
}

/* MIXIN
{GLOBAL_CONST@
{GLOBAL_CONST2@
*/

?>