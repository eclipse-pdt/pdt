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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

import org.eclipse.php.internal.core.phar.digest.Digest;

public class SignatureBufferedOutputStream extends OutputStream {

	private MessageDigest digest;

	private BufferedOutputStream innerOutputStream;

	public SignatureBufferedOutputStream(OutputStream out) {
		innerOutputStream = new BufferedOutputStream(out);
	}

	public SignatureBufferedOutputStream(OutputStream out, int size) {
		innerOutputStream = new BufferedOutputStream(out, size);
	}

	public SignatureBufferedOutputStream(OutputStream out, PharPackage pharData) {
		this(out);
		//		Assert.isNotNull(pharData.getSignature(), "The PHAR's signature is null"); //$NON-NLS-1$
		if (!pharData.isUseSignature()
				|| !Digest.DIGEST_MAP.containsKey(pharData.getSignature())) {
			digest = Digest.NULL_DIGEST;
		} else {
			digest = Digest.DIGEST_MAP.get(pharData.getSignature()).getDigest();
		}
		digest.reset();
	}

	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public synchronized void write(byte[] b, int off, int len)
			throws IOException {
		innerOutputStream.write(b, off, len);
		digest.update(b, off, len);
	}

	@Override
	public synchronized void write(int b) throws IOException {
		innerOutputStream.write(b);
		digest.update((byte) b);
	}

	@Override
	public void close() throws IOException {
		innerOutputStream.close();
	}

	public byte[] getSignature() {
		return digest.digest();
	}
}
