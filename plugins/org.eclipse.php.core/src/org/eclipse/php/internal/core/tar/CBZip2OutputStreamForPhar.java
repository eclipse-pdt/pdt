/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.tar;

import java.io.IOException;
import java.io.OutputStream;

public class CBZip2OutputStreamForPhar extends CBZip2OutputStream implements CRCable {

	public CBZip2OutputStreamForPhar(OutputStream inStream) throws IOException {
		this(inStream, 4);
	}

	public CBZip2OutputStreamForPhar(OutputStream inStream, int inBlockSize) throws IOException {
		super(inStream, inBlockSize);
	}

	@Override
	protected void writeMigicBytes() throws IOException {

		bsPutUChar('B');
		bsPutUChar('Z');

		super.writeMigicBytes();
	}

	@Override
	public long getCrc() {
		// TODO Auto-generated method stub
		return mCrc.getFinalCRC();
	}
}
