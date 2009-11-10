/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import org.eclipse.core.runtime.Platform;

public class PharConstants {
	public static final boolean WINDOWS = Platform.getOS() == Platform.WS_WIN32;
	public static final int PHAR = 0;
	public static final int TAR = 1;
	public static final int ZIP = 2;

	public static final int NONE_COMPRESSED = 0;
	public static final int GZ_COMPRESSED = 1;
	public static final int ZIP_COMPRESSED = 1;
	public static final int BZ2_COMPRESSED = 2;
	// public static final String GZ = "zlib";
	// public static final String BZ2 = "bzip";
	public static final byte[] PHP_START = "<?php".getBytes();
	public static final byte[] STUB_ENDS = "__HALT_COMPILER();".getBytes();
	public static final byte[] STUB_TAIL = " ?>".getBytes();
	public static final byte[] GBMB = "GBMB".getBytes();
	public static final byte[] Default_Entry_Bitmap = { -74, 1, 0, 0 };
	public static final byte[] Default_Global_Bitmap = { 0, 0, 0, 0 };
	public static final String PHAR_PREFIX = "phar:";
	public static final String STUB_PATH = ".phar/stub.php";
	public static final String STUB_NAME = "stub.php";
	public static final String SIGNATURE_PATH = ".phar/signature.php";

	public static final byte R = '\r';
	public static final byte N = '\n';
	public static final byte[] Line_Seperator = System.getProperty(
			"line.separator").getBytes();
	public static final byte Underline = '_';

	public static final String EMPTY_STRING = "";
	public static final String DOT = ".";
	public static final String SPLASH = "/";
	public static final String DOUBLE_SPLASH = "//";
	public static final String PHAR_EXTENSION = "phar";
	public static final String PHAR_EXTENSION_WITH_DOT = ".phar";
	public static final String PHAR_EXTENSION_ZIP = "zip";
	public static final String PHAR_EXTENSION_TAR1 = "tar";
	public static final String PHAR_EXTENSION_TAR2 = ".tar.gz";
	public static final String PHAR_EXTENSION_TAR3 = "tar.gz";
	public static final String PHAR_EXTENSION_TAR4 = ".tar.bz2";
	public static final String PHAR_EXTENSION_TAR5 = "tar.bz2";
}
