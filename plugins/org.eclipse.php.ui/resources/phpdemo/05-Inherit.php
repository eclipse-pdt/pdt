<?php
/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

class MyClass {
    // definitions:
    var $member = 1;          // dynamic member
    static $staticMember = 2; // static member
    const constMember = 3;    // constant member
    function classFunc() {
        // static internal references:
        echo self::$staticMember;
        echo self::constMember;
        // dynamic internal reference:
        echo $this->member;
    }
}
interface MyInterface {
    // definition:
    const iConstMember = 4;
}
class MyClass2 extends MyClass implements MyInterface {
   function classFunc2() {
        // static internal references:
        echo self::$staticMember;
        echo parent::$staticMember;
        echo self::constMember;
        echo parent::constMember;
        echo self::iConstMember;
        // dynamic internal reference:
        echo $this->member;
    }
}
// static external references:
echo MyClass2::$staticMember;
echo MyClass2::constMember;
echo MyClass2::iConstMember;
// dynamic external references:
$obj2 = new MyClass2();
echo $obj2->member;
?>