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

	public long getCompressedSize() {
		return csize;
	}

	public long getCrc() {
		return crc;
	}

	public int getMethod() {
		return method;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public long getTime() {
		return time;
	}

	public boolean isDirectory() {
		return name.endsWith("/"); //$NON-NLS-1$
	}

	public void setCompressedSize(long csize) {
		this.csize = csize;
	}

	public void setCrc(long crc) {
		this.crc = crc;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
