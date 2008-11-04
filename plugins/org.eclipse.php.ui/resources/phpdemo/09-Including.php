<?php
/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

require_once '05-Inherit.php';
require_once 'src/01-included.php';

$instance = new MyClass();
$instance->classFunc();

$instance = new Included_MyClass();
$instance->goo()->foo();



