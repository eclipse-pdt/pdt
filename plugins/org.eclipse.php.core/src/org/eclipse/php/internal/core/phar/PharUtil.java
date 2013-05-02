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
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.phar.digest.Digest;

public class PharUtil {

	public static ByteArrayInputStream getStubInputStream(IStub stub)
			throws IOException, CoreException {
		ByteArrayOutputStream stubOutput = new ByteArrayOutputStream();
		stub.write(stubOutput);
		ByteArrayInputStream fileInput = new ByteArrayInputStream(stubOutput
				.toByteArray());
		return fileInput;
	}

	public static ByteArrayInputStream getInputStream(byte[] signature)
			throws IOException {
		ByteArrayInputStream fileInput = new ByteArrayInputStream(signature);
		return fileInput;
	}

	public static byte[] getStubVersionBytes(String version) {
		int versionNumberLength = 4;
		String[] s = version.split("\\."); //$NON-NLS-1$

		List<Integer> versionNumbers = new ArrayList<Integer>();

		for (int i = 0; i < s.length; i++) {
			versionNumbers.add(Integer.valueOf(s[i]));
		}

		for (int i = 0; i < versionNumberLength; i++) {
			versionNumbers.add(Integer.valueOf(0));
		}
		versionNumbers = versionNumbers.subList(0, versionNumberLength);
		// int number = 0;
		// for (Iterator<Integer> iterator = versionNumbers.iterator(); iterator
		// .hasNext();) {
		// Integer versionNumber = iterator.next();
		// number = number * 16 + versionNumber.intValue();
		// }
		byte[] versionBytes = new byte[versionNumberLength / 2];

		for (int i = 0; i < versionBytes.length; i++) {
			versionBytes[i] = (byte) (versionNumbers.get(i * 2).intValue() * 16 + versionNumbers
					.get(i * 2 + 1).intValue());
		}
		return versionBytes;
	}

	public static int getPositive(byte b) {
		return (256 + b) % 256;
	}

	public static void throwPharException(String string) throws PharException {
		throw new PharException(string);
	}

	public static String getVersion(byte[] subBytes) {
		StringBuffer sb = new StringBuffer();
		sb.append(subBytes[0] >> 4 & 0xf);
		sb.append(subBytes[0] & 15);
		sb.append(subBytes[1] >> 4 & 0xf);
		return sb.toString();
	}

	public static void checkStubVilidaty(InputStream bis) throws IOException {
		boolean stubHasBeenFound = false;
		int n = -1;
		// this is record for whether read a byte from the stream or not
		int currentByte = -1;
		// if currentByte is equal to char '_',we will not read the next
		// byte
		while (!stubHasBeenFound
				&& (currentByte == PharConstants.Underline || (n = bis.read()) != -1)) {
			if (n == PharConstants.Underline) {
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
					if (bis.available() > 0) {
						j = 0;
						match = false;
						for (; j < PharConstants.STUB_TAIL.length && n != -1; j++) {
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
						} else if (isEndOfLine(bis)) {
							stubHasBeenFound = match;
						}
					} else {
						stubHasBeenFound = match;
					}

				}
			}
		}
		if (!stubHasBeenFound) {
			PharUtil.throwIOException(Messages.Stub_Invalid);
		}

	}

	private static boolean isEndOfLine(InputStream bis) throws IOException {
		final int available = bis.available();
		if (available == 1) {
			final int first = bis.read();
			return first == PharConstants.R || first == PharConstants.N;
		}
		if (available == 2) {
			final int first = bis.read();
			final int second = bis.read();
			return first == PharConstants.R && second == PharConstants.N
					|| first == PharConstants.N && second == PharConstants.R;
		}
		return false;
	}

	private static void throwIOException(String string) throws IOException {
		throw new IOException(string);
	}

	public static byte[] getWholeSignature(byte[] signature,
			PharPackage pharPackage) {
		byte[] tmp = comcat(signature, Digest.DIGEST_MAP.get(
				pharPackage.getSignature()).getBitMap());
		tmp = comcat(tmp, PharConstants.GBMB);
		return tmp;
	}

	public static byte[] comcat(byte[] bs, byte[] content) {
		byte[] tmp = new byte[bs.length + content.length];
		System.arraycopy(bs, 0, tmp, 0, bs.length);
		System.arraycopy(content, 0, tmp, bs.length, content.length);
		// for (int k = 0; k < result.length; k++) {
		// result[k] = bytes[i + k];
		// }
		return tmp;
	}

	public static boolean byteArrayEquals(byte[] b1, byte[] b2) {
		if (b1 != null && b2 != null && b1.length == b2.length) {
			for (int i = 0; i < b1.length; i++) {
				if (b1[i] != b2[i])
					return false;
			}
			return true;
		} else {
			return false;
		}

	}

	public static boolean checkSignature(File file, Digest digest, int end)
			throws IOException {
		MessageDigest messageDigest = digest.getDigest();
		messageDigest.reset();
		int length = 0;

		InputStream contentStream = new BufferedInputStream(
				new FileInputStream(file));
		try {
			int n;
			int size = 4096;
			byte[] readBuffer;
			if (end < size) {
				readBuffer = new byte[end];
			} else {
				readBuffer = new byte[4096];
			}
			while ((n = contentStream.read(readBuffer)) > 0) {
				length += n;
				// if (length > end) {
				// // n = n - (length - end);
				// messageDigest.update(readBuffer, 0, n - (length - end));
				// contentStream.skip(-(length - end));
				// break;
				// } else {
				messageDigest.update(readBuffer, 0, n);
				// }
				if (length == end) {
					break;
				}
				if (length + readBuffer.length > end) {
					readBuffer = new byte[end - length];
				}
			}
			readBuffer = new byte[contentStream.available() - 8];
			contentStream.read(readBuffer);
			if (PharUtil.byteArrayEquals(messageDigest.digest(), readBuffer)) {
				return true;
			}
		} finally {
			if (contentStream != null) {
				contentStream.close();
			}
		}

		return false;
	}

	public static byte[] getCopy(byte[] bytes) {
		byte[] result = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			result[i] = bytes[i];
		}
		return result;
	}

	public static byte[] getGlobalBitmap(PharPackage pharPackage) {
		byte[] globalBitmap = PharUtil
				.getCopy(PharConstants.Default_Global_Bitmap);
		// if(pharData.)
		switch (pharPackage.getCompressType()) {
		case PharConstants.BZ2_COMPRESSED:
			globalBitmap[1] = 32;
			break;
		case PharConstants.GZ_COMPRESSED:
			globalBitmap[1] = 16;
			break;

		default:
			break;
		}
		if (pharPackage.isUseSignature()) {
			globalBitmap[2] = 1;
		}
		return globalBitmap;
	}

	public static byte[] getBitmapBytes(PharAchiveOutputEntry entry) {
		byte[] bitmap = PharUtil.getCopy(PharConstants.Default_Entry_Bitmap);
		switch (entry.getMethod()) {
		case PharConstants.BZ2_COMPRESSED:
			bitmap[1] = (byte) (bitmap[1] + 32);
			break;
		case PharConstants.GZ_COMPRESSED:
			bitmap[1] = (byte) (bitmap[1] + 16);
			break;

		default:
			break;
		}
		return bitmap;
	}

	// if the length is too length,it will not skip that long
	public static void skip(BufferedInputStream bis, long length)
			throws IOException {
		long n;
		while ((n = bis.skip(length)) != 0) {
			length = length - n;
		}
	}
}
