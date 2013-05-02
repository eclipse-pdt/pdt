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

public class PharEntry implements IAchiveOutputEntry {

	private String name;
	private int csize;
	private int size;
	private int position;
	private byte[] sizeByte;
	private long time;
	private byte[] crc;
	private byte[] bitMappedFlag;
	private String content;
	private String metadata;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public byte[] getSizeByte() {
		return sizeByte;
	}

	public void setSizeByte(byte[] sizeByte) {
		this.sizeByte = PharUtil.getCopy(sizeByte);

	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCsize() {
		return csize;
	}

	public void setCsize(int csize) {
		this.csize = csize;
	}

	public long getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getCrcByte() {
		return crc;
	}

	public void setCrcByte(byte[] crc) {
		this.crc = PharUtil.getCopy(crc);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public byte[] getBitMappedFlag() {
		return bitMappedFlag;
	}

	public void setBitMappedFlag(byte[] bitMappedFlag) {
		this.bitMappedFlag = PharUtil.getCopy(bitMappedFlag);
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public boolean isDirectory() {

		return name.endsWith("/"); //$NON-NLS-1$
	}

	public boolean isCompressed() {
		return getCompressedType() != PharConstants.NONE_COMPRESSED;
	}

	public int getCompressedType() {
		int result = PharConstants.NONE_COMPRESSED;
		if ((bitMappedFlag[1] & 16) != 0) {
			result = PharConstants.GZ_COMPRESSED;
		} else if ((bitMappedFlag[1] & 32) != 0) {
			result = PharConstants.BZ2_COMPRESSED;
		}
		return result;
	}

	public int getEnd() {
		return position + csize;
	}

	public long getCompressedSize() {
		return csize;
	}

	public long getCrc() {
		return toLong(crc);
	}

	private long toLong(byte[] crc2) {
		return PharFile.getInt(crc2);
	}

	public int getMethod() {
		return getCompressedType();
	}

	public void setCompressedSize(long csize) {
		setCsize((int) csize);
	}

	public void setCrc(long crc) {
		this.crc = toByte(crc);
	}

	public static byte[] toByte(long crc2) {
		byte[] result = new byte[4];
		result[0] = (byte) (crc2 & 0x000000ff);
		result[1] = (byte) ((crc2 >> 8) & 0xff);
		result[2] = (byte) ((crc2 >> 16) & 0xff);
		result[3] = (byte) ((crc2 >> 24) & 0xff);
		return result;
	}

	public void setMethod(int method) {
		if (bitMappedFlag == null) {
			bitMappedFlag = new byte[4];
		}
		bitMappedFlag[1] = (byte) (bitMappedFlag[1] & (255 - 32 - 64));
		if (PharConstants.BZ2_COMPRESSED == method) {
			bitMappedFlag[1] = (byte) (bitMappedFlag[1] & 32);
		} else if (PharConstants.GZ_COMPRESSED == method) {
			bitMappedFlag[1] = (byte) (bitMappedFlag[1] & 16);
		}

	}

	public void setSize(long size) {
		setSize((int) size);
	}

}
