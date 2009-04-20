<?php

class A {
	function foo() {
		class II {
		}
		
		class AA {
		}
	}
}

A::foo();

interface I {
	const C = 1;
}

echo I::C;

function bar() {
	interface III {
	}
	
	class AAA {
	}
}

?>