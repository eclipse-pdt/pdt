/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol;

import java.io.UnsupportedEncodingException;

/* Base64 definition

 5.2.  Base64 Content-Transfer-Encoding

 The Base64 Content-Transfer-Encoding is designed to represent
 arbitrary sequences of octets in a form that need not be humanly
 readable.  The encoding and decoding algorithms are simple, but the
 encoded data are consistently only about 33 percent larger than the
 unencoded data.  This encoding is virtually identical to the one used
 in Privacy Enhanced Mail (PEM) applications, as defined in RFC 1421.
 The base64 encoding is adapted from RFC 1421, with one change: base64
 eliminates the "*" mechanism for embedded clear text.

 A 65-character subset of US-ASCII is used, enabling 6 bits to be
 represented per printable character. (The extra 65th character, "=",
 is used to signify a special processing function.)

 NOTE: This subset has the important property that it is
 represented identically in all versions of ISO 646, including US
 ASCII, and all characters in the subset are also represented
 identically in all versions of EBCDIC.  Other popular encodings,
 such as the encoding used by the uuencode utility and the base85
 encoding specified as part of Level 2 PostScript, do not share
 these properties, and thus do not fulfill the portability
 requirements a binary transport encoding for mail must meet.

 The encoding process represents 24-bit groups of input bits as output
 strings of 4 encoded characters. Proceeding from left to right, a
 24-bit input group is formed by concatenating 3 8-bit input groups.
 These 24 bits are then treated as 4 concatenated 6-bit groups, each
 of which is translated into a single digit in the base64 alphabet.
 When encoding a bit stream via the base64 encoding, the bit stream
 must be presumed to be ordered with the most-significant-bit first.



 Borenstein & Freed                                             [Page 21]

 RFC 1521                          MIME                    September 1993


 That is, the first bit in the stream will be the high-order bit in
 the first byte, and the eighth bit will be the low-order bit in the
 first byte, and so on.

 Each 6-bit group is used as an index into an array of 64 printable
 characters. The character referenced by the index is placed in the
 output string. These characters, identified in Table 1, below, are
 selected so as to be universally representable, and the set excludes
 characters with particular significance to SMTP (e.g., ".", CR, LF)
 and to the encapsulation boundaries defined in this document (e.g.,
 "-").

 Table 1: The Base64 Alphabet

 Value Encoding  Value Encoding  Value Encoding  Value Encoding
 0 A            17 R            34 i            51 z
 1 B            18 S            35 j            52 0
 2 C            19 T            36 k            53 1
 3 D            20 U            37 l            54 2
 4 E            21 V            38 m            55 3
 5 F            22 W            39 n            56 4
 6 G            23 X            40 o            57 5
 7 H            24 Y            41 p            58 6
 8 I            25 Z            42 q            59 7
 9 J            26 a            43 r            60 8
 10 K            27 b            44 s            61 9
 11 L            28 c            45 t            62 +
 12 M            29 d            46 u            63 /
 13 N            30 e            47 v
 14 O            31 f            48 w         (pad) =
 15 P            32 g            49 x
 16 Q            33 h            50 y

 The output stream (encoded bytes) must be represented in lines of no
 more than 76 characters each.  All line breaks or other characters
 not found in Table 1 must be ignored by decoding software.  In base64
 data, characters other than those in Table 1, line breaks, and other
 white space probably indicate a transmission error, about which a
 warning message or even a message rejection might be appropriate
 under some circumstances.

 Special processing is performed if fewer than 24 bits are available
 at the end of the data being encoded.  A full encoding quantum is
 always completed at the end of a body.  When fewer than 24 input bits
 are available in an input group, zero bits are added (on the right)
 to form an integral number of 6-bit groups.  Padding at the end of
 the data is performed using the '=' character.  Since all base64
 input is an integral number of octets, only the following cases can



 Borenstein & Freed                                             [Page 22]

 RFC 1521                          MIME                    September 1993


 arise: (1) the final quantum of encoding input is an integral
 multiple of 24 bits; here, the final unit of encoded output will be
 an integral multiple of 4 characters with no "=" padding, (2) the
 final quantum of encoding input is exactly 8 bits; here, the final
 unit of encoded output will be two characters followed by two "="
 padding characters, or (3) the final quantum of encoding input is
 exactly 16 bits; here, the final unit of encoded output will be three
 characters followed by one "=" padding character.

 Because it is used only for padding at the end of the data, the
 occurrence of any '=' characters may be taken as evidence that the
 end of the data has been reached (without truncation in transit).  No
 such assurance is possible, however, when the number of octets
 transmitted was a multiple of three.

 Any characters outside of the base64 alphabet are to be ignored in
 base64-encoded data.  The same applies to any illegal sequence of
 characters in the base64 encoding, such as "====="

 Care must be taken to use the proper octets for line breaks if base64
 encoding is applied directly to text material that has not been
 converted to canonical form.  In particular, text line breaks must be
 converted into CRLF sequences prior to base64 encoding. The important
 thing to note is that this may be done directly by the encoder rather
 than in a prior canonicalization step in some implementations.

 NOTE: There is no need to worry about quoting apparent
 encapsulation boundaries within base64-encoded parts of multipart
 entities because no hyphen characters are used in the base64
 encoding.

 */

public class Base64 {

	/**
	 * 
	 * 
	 */
	private Base64() {

	}

	// TODO: not a great way to do this, should work with chars not bytes.
	// We convert utf-16 to ASCII and create conversion tables based on ASCII
	// code points
	private static final String INTERNAL_ENCODING = "ASCII"; //$NON-NLS-1$

	private static String base64CharSetSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="; //$NON-NLS-1$

	// Table to convert a number from 0-64 to an (Base64) ASCII byte equivalent
	private static byte[] valToBase64CharTable;

	// Table to convert an ascii byte to the Base64 number equivalent
	private static int[] base64ToValTable;

	static {
		try {
			valToBase64CharTable = base64CharSetSequence
					.getBytes(INTERNAL_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}

		base64ToValTable = new int[256];
		for (int i = 0; i < 256; i++) {
			base64ToValTable[i] = -1;
		}

		for (int i = 0; i < valToBase64CharTable.length; i++) {
			base64ToValTable[valToBase64CharTable[i] & 0xFF] = i;
		}
	}

	private static int[] three2four(byte a, byte b, byte c) {

		/*
		 * aaaaaa|aabbbb|bbbbcc|cccccc
		 */
		int[] result = new int[4];
		result[0] = a >>> 2 & 0x3f & 0xFF;
		result[1] = ((a & 0x03) << 4) | ((b & 0xf0) >>> 4) & 0xFF;
		result[2] = ((b & 0x0f) << 2) | ((c & 0xc0) >>> 6) & 0xFF;
		result[3] = c & 0x3f;
		return result;

	}

	public static String encode(byte[] input) {
		byte[] result = encodeToBytes(input);
		;
		String strResult = null;
		try {
			strResult = new String(result, INTERNAL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			strResult = new String(result);
		}
		return strResult;
	}

	public static byte[] decode(String input) {
		byte[] byteInput = null;
		try {
			byteInput = input.getBytes(INTERNAL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			byteInput = input.getBytes();
		}
		byte[] result = decode(byteInput);
		return result;
	}

	private static byte[] encodeToBytes(byte[] input) {
		int outsize = input.length / 3 * 4;
		if (input.length % 3 != 0) {
			outsize += 4;
		}

		byte[] encoded = new byte[outsize];
		byte zero = (byte) 0;
		byte[] data = new byte[4];

		int pos = 0;
		for (int i = 0; i < input.length; i += 3) {

			switch (input.length - i) {
			case 1:
				// ok encode 1 byte into 2 and add '=='
				int[] result = three2four(input[i], zero, zero);
				data[0] = valToBase64CharTable[result[0]];
				data[1] = valToBase64CharTable[result[1]];
				data[2] = valToBase64CharTable[64];
				data[3] = valToBase64CharTable[64];

				break;
			case 2:
				result = three2four(input[i], input[i + 1], zero);
				data[0] = valToBase64CharTable[result[0]];
				data[1] = valToBase64CharTable[result[1]];
				data[2] = valToBase64CharTable[result[2]];
				data[3] = valToBase64CharTable[64];
				// ok encode 2 bytes into 3 and add '='
				break;
			default:
				result = three2four(input[i], input[i + 1], input[i + 2]);
				data[0] = valToBase64CharTable[result[0]];
				data[1] = valToBase64CharTable[result[1]];
				data[2] = valToBase64CharTable[result[2]];
				data[3] = valToBase64CharTable[result[3]];
				// ok encode 3 bytes into 4
				break;
			}
			for (int j = 0; j < 4; j++) {
				encoded[pos] = data[j];
				pos++;
			}
		}
		return encoded;
	}

	/**
	 * decode a base64 stream.
	 * 
	 * @param input
	 *            base64 encoded byte array
	 * @return decoded byte array
	 */
	private static byte[] decode(byte[] input) {

		// if this is a pure base64 encoded stream then
		// the longest result is outsize (including the
		// possibility of 2-3 extra bytes (and missing
		// padding) will result in at most an extra 2 decoded
		// bytes
		int outsize = input.length / 4 * 3;

		// if we have a dodgy stream (ie not multiples of 4 bytes)
		// we will attempt to cater for this.
		if (input.length % 4 != 0) {
			outsize += input.length % 4 - 1;
		} else {
			// we have a proper base64 stream so we can adjust futher.
			// note that if there are 2 pads at the end, reduce outsize by 2
			// if there is 1 pad at end, reduce outsize by 1. Also need to
			// handle
			// situation where we have added crlfs to the output.
			// if (input[input.length - 1] == valToBase64CharTable[64]) {
			// outsize--;
			// }
			// if (input[input.length - 2] == valToBase64CharTable[64]) {
			// outsize--;
			// }
		}

		// return nothing if we have no allocation.
		if (outsize == 0) {
			return new byte[0];
		}

		// outsize could be too large. Will reduce the size at the end.
		byte[] decoded = new byte[outsize];
		int[] base64set = new int[4];

		int outputPos = 0;
		int decodepos = 0;
		for (int i = 0; i < input.length; i++) {
			// Get byte value and mask of any sign bit if byte > 0x7F
			int byteVal = (int) input[i] & 0xFF;
			if (base64ToValTable[byteVal] == -1) {
				continue;
			} else {
				base64set[decodepos] = base64ToValTable[byteVal];
				decodepos++;

				// check to see if we have 4 entries now
				if (decodepos == 4) {
					decodepos = 0;
					decoded[outputPos] = (byte) ((base64set[0] << 2) | ((base64set[1] & 0x30) >>> 4));
					outputPos++;
					if (base64set[2] != 64) {
						decoded[outputPos] = (byte) (((base64set[1] & 0x0f) << 4) | ((base64set[2] & 0x3c) >>> 2));
						outputPos++;
						if (base64set[3] != 64) {
							decoded[outputPos] = (byte) (((base64set[2] & 0x03) << 6 | base64set[3]));
							outputPos++;
						}
					}
				}
			}
		}

		if (decodepos > 1) {
			// we have some bits left over
			decoded[outputPos] = (byte) ((base64set[0] << 2) | ((base64set[1] & 0x30) >>> 4));
			outputPos++;
			if (decodepos > 2 && base64set[2] != 64) {
				decoded[outputPos] = (byte) (((base64set[1] & 0x0f) << 4) | ((base64set[2] & 0x3c) >>> 2));
				outputPos++;
			}
		}
		byte[] finalDecoded = decoded;

		// reduce the returned byte array to contain just the relevant entries.
		// outputPos contains the final length of the information
		if (outsize > outputPos) {
			if (outputPos > 0) {
				finalDecoded = new byte[outputPos];
				System.arraycopy(decoded, 0, finalDecoded, 0, outputPos);
			} else {
				return new byte[0];
			}
		}

		return finalDecoded;
	}

	// private static byte[] decode(byte[] input) {
	// int outsize = input.length;
	// outsize = outsize / 4 * 3;
	//
	// if (input[input.length - 1] == valToBase64CharTable[64]) {
	// outsize--;
	// }
	// if (input[input.length - 2] == valToBase64CharTable[64]) {
	// outsize--;
	// }
	//
	// // note that if there are 2 pads at the end, reduce outsize by 2
	// // if there is 1 pad at end, reduce outsize by 1. Also need to handle
	// // situation where we have added crlfs to the output.
	//
	// if (input.length % 4 != 0) {
	// // error, but need to handle 76 character lines where crlfs have been
	// added
	// }
	//
	// byte[] decoded = new byte[outsize];
	// for (int j = 0; j < outsize; j++) {
	// decoded[j] = '?';
	// }
	// int[] base64set = new int[4];
	//
	// int outputPos = 0;
	// int decodepos = 0;
	//
	// for (int i = 0; i < input.length; i++) {
	// // Get byte value and mask of any sign bit if byte > 0x7F
	// int byteVal = (int) input[i] & 0xFF;
	// if (base64ToValTable[byteVal] == -1) {
	// continue;
	// } else {
	// base64set[decodepos] = base64ToValTable[byteVal];
	// decodepos++;
	//
	// // check to see if we have 4 entries now
	// if (decodepos == 4) {
	// decodepos = 0;
	// decoded[outputPos] = (byte) ((base64set[0] << 2) | ((base64set[1] & 0x30)
	// >>> 4));
	// if (base64set[2] != 64) {
	// decoded[outputPos + 1] = (byte) (((base64set[1] & 0x0f) << 4) |
	// ((base64set[2] & 0x3c) >>> 2));
	// if (base64set[3] != 64) {
	// decoded[outputPos + 2] = (byte) (((base64set[2] & 0x03) << 6 |
	// base64set[3]));
	// }
	// }
	// outputPos += 3;
	// }
	// }
	// }
	// return decoded;
	// }
}
