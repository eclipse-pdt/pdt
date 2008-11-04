<?php
/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

class MyClass {
    static $member = 1;
    const constMember = 2;
    static function method() {
        return 3;
    }
}
class MyClass2 extends MyClass {}

interface YourClassAbstract {
}

class YourClassConcrete implements YourClassAbstract {
}
?>