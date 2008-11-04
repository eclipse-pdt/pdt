<?php
/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

class father {
	function f0() {}
	function f1() {}
	public function f2() {}
	protected function f3() {}
	private function f4() {}
}

class same extends father {

	// overload fn with same visibility
	function f0() {}
	public function f1() {}
	public function f2() {}
	protected function f3() {}
	private function f4() {}
}

class fail extends same {
	function f0() {}
}

echo "Done\n"; // shouldn't be displayed
?>
