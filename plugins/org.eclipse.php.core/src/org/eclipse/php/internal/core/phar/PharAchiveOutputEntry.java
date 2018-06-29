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
package org.eclipse.php.internal.core.phar;

public class PharAchiveOutputEntry implements IAchiveOutputEntry {
	String name; // entry name
	long time = -1; // modification time (in DOS time)
	long crc = -1; // crc-32 of entry data
	long size = -1; // uncompressed size of entry data
	long csize = -1; // compressed size of entry data
	int method = -1; // compression method

	public PharAchiveOutputEntry(String destinationPath) {
		// TODO Auto-generated constructor stub
		name = destinationPath;
	}

	@Override
	public long getCompressedSize() {
		return csize;
	}

	@Override
	public long getCrc() {
		return crc;
	}

	@Override
	public int getMethod() {
		return method;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public boolean isDirectory() {
		return name.endsWith("/"); //$NON-NLS-1$
	}

	@Override
	public void setCompressedSize(long csize) {
		this.csize = csize;
	}

	@Override
	public void setCrc(long crc) {
		this.crc = crc;
	}

	@Override
	public void setMethod(int method) {
		this.method = method;
	}

	@Override
	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public void setTime(long time) {
		this.time = time;
	}

}
