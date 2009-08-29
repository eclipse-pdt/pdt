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

public class PharEntryBufferedOutputStream extends OutputStream {

	protected OutputStream innerStream;
	private boolean beforeWrited = false;

	public PharEntryBufferedOutputStream(OutputStream innerStream) {
		this.innerStream = innerStream;
	}

	@Override
	public void write(int b) throws IOException {
		if (!beforeWrited) {
			beforeWrite();
		}

		innerStream.write(b);
	}

	protected void beforeWrite() {
		beforeWrited = true;
	}

	@Override
	public void write(byte[] b) throws IOException {
		if (!beforeWrited) {
			beforeWrite();
		}

		innerStream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if (!beforeWrited) {
			beforeWrite();
		}

		innerStream.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		afterWrite();
		// innerStream.close();
	}

	protected void afterWrite() {

	}

}
