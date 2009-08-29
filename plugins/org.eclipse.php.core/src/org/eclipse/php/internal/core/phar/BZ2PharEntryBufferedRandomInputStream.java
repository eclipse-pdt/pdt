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

import java.io.File;
import java.io.IOException;

public class BZ2PharEntryBufferedRandomInputStream extends
		PharEntryBufferedRandomInputStream {
	// byte[] header = new byte[]{104, 57};
	public BZ2PharEntryBufferedRandomInputStream(File file, PharEntry pharEntry)
			throws IOException {
		super(file, pharEntry);
		// skip first bytes
		skip(2);
	}

	@Override
	public int read() throws IOException {
		int result = super.read();
		// if(currentIndex == 2){
		// return 57;
		// }
		return result;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int result = super.read(b, off, len);

		// if it has not read or only read 1 byte before,and this time it has
		// read the second byte
		// so we need to replace that byte
		// if(currentIndex >= 2 && currentIndex - result < 2){
		// // b[1 - (currentIndex - result)] = 57;it will be easy to understand
		// using the following code
		// if(currentIndex - result == 0){
		// b[1] = 57;
		// }else{
		// b[0] = 57;
		// }
		// }
		return result;
	}
}
