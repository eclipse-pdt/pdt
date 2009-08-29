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

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.php.internal.core.tar.CBZip2OutputStream;

public class BZ2PharEntryBufferedOutputStream extends
		PharEntryBufferedOutputStream {

	public BZ2PharEntryBufferedOutputStream(OutputStream innerStream)
			throws IOException {
		super(new CBZip2OutputStream(innerStream, 4));
	}

	@Override
	protected void beforeWrite() {
		super.beforeWrite();
		try {
			write(new byte[] { 104, 57 });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
