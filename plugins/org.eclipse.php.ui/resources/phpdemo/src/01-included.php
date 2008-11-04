<?php
/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

// this file is included by other files

class Included_MyClass {

	function foo($a) {
		return 5;
	}

	function goo() {
		return new Included_MyClass();
	}

}