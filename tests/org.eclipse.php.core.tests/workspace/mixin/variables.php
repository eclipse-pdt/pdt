<?php

class A {
	var $v;
	static $s;
	
	function __construct() {
		$this->v2 = '';
	}
	
	function foo($param) {
		$l = '';
		
		class M {
			public $p;
		}
	}
}

function bar() {
	global $g;
	$g = '';
		
	if (true) {
		global $g2;
	}
	$g2 = '';
	
	$l2 = 0;
}

/* MIXIN
{$g
{$g2
*/

?>