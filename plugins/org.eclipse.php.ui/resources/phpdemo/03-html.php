<!--
	/*******************************************************************************
	 * Copyright (c) 2000, 2008 Zend Technologies and others.
	 * All rights reserved. This program and the accompanying materials
	 * are made available under the terms of the Eclipse Public License v1.0
	 * which accompanies this distribution, and is available at
	 * http://www.eclipse.org/legal/epl-v10.html
	 *******************************************************************************/
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<frameset>
    <frame>
    <frame>
    <noframes>
    <body>
    <p>This page uses frames. The current browser you are using does not support frames.</p>
	<?php
    /**
     * HelloWorldClass
     */
    class HelloWorldClass {

    	/**
    	 * helloWorldFunction
    	 */
    	function helloWorldFunction() {
    		return "HelloWorld!";
    	}
    }

    // Prints the message to the screen
    $aclass = new HelloWorldClass();
    echo ($aclass->helloWorldFunction());
    ?>
    </body>
    </noframes>
</frameset>
</html>