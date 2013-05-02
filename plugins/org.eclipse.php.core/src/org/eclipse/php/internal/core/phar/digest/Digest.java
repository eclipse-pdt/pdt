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
package org.eclipse.php.internal.core.phar.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Digest {
	public static final String MD5_TYPE = "MD5"; //$NON-NLS-1$
	public static final String SHA1_TYPE = "SHA1"; //$NON-NLS-1$
	public static final Digest MD5 = new Digest(MD5_TYPE,
			new byte[] { 1, 0, 0, 0 });
	public static final Digest SHA1 = new Digest(SHA1_TYPE, new byte[] { 2, 0, 0,
			0 });
	// public static final Digest SHA256 = new Digest("SHA256","0004");
	// public static final Digest SHA512 = new Digest("SHA512 ","0008");
	public static final NullMessageDigest NULL_DIGEST = new NullMessageDigest(
			"NULL"); //$NON-NLS-1$
	public static final Map<String, Digest> DIGEST_MAP = new HashMap<String, Digest>();
	static {
		DIGEST_MAP.put(MD5.name, MD5);
		DIGEST_MAP.put(SHA1.name, SHA1);
		// DIGEST_MAP.put(SHA256.name, SHA256);
		// DIGEST_MAP.put(SHA512.name, SHA512);
	}
	byte[] bitMap;
	String name;
	private MessageDigest digest;

	public Digest(String name, byte[] bitMap) {
		this.name = name;
		this.bitMap = bitMap;
	}

	public MessageDigest getDigest() {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance(name);
			} catch (NoSuchAlgorithmException e) {
				digest = NULL_DIGEST;
			}
		}
		return digest;
	}

	public byte[] getBitMap() {
		return bitMap;
	}

	public static class NullMessageDigest extends MessageDigest {

		public NullMessageDigest(String algorithm) {
			super(algorithm);
		}

		@Override
		protected byte[] engineDigest() {
			return null;
		}

		@Override
		protected void engineReset() {

		}

		@Override
		protected void engineUpdate(byte input) {

		}

		@Override
		protected void engineUpdate(byte[] input, int offset, int len) {

		}

		@Override
		public byte[] digest() {
			return null;
		}
	}
}
