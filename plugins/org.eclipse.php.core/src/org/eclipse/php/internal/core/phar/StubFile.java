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

import java.io.*;

public class StubFile {
	private File file;

	public StubFile(File file) throws IOException, PharException {
		this.file = file;
		checkVilidaty();
	}

	private void checkVilidaty() throws IOException, PharException {
		InputStream bis = new BufferedInputStream(new FileInputStream(file));

		boolean stubHasBeenFound = false;
		int n = -1;
		// this is record for whether read a byte from the stream or not
		int currentByte = -1;
		try {
			// if currentByte is equal to char '_',we will not read the next
			// byte
			while (!stubHasBeenFound
					&& (currentByte == '_' || (n = bis.read()) != -1)) {
				if (n == '_') {
					boolean match = false;
					int j = 1;
					for (; j < PharConstants.STUB_ENDS.length && n != -1; j++) {
						if ((n = bis.read()) == PharConstants.STUB_ENDS[j]) {
							if (j == PharConstants.STUB_ENDS.length - 1) {
								match = true;
							}
						} else {
							break;
						}
					}

					if (match) {
						// i = i + ENDS.length;
						if (bis.available() > 0) {
							j = 0;
							match = false;
							for (; j < PharConstants.STUB_TAIL.length
									&& n != -1; j++) {
								n = bis.read();
								if (n == PharConstants.STUB_TAIL[j]) {
									if (j == PharConstants.STUB_TAIL.length - 1) {
										match = true;
									}
								} else {
									break;
								}
							}
							if (bis.available() == 0) {
								stubHasBeenFound = match;
							}
						} else {
							stubHasBeenFound = match;
						}

					}
				}
			}
			if (!stubHasBeenFound) {
				PharUtil
						.throwPharException("the file is not a well formated stub"); //$NON-NLS-1$
			}
		} finally {
			bis.close();
		}
	}
}
