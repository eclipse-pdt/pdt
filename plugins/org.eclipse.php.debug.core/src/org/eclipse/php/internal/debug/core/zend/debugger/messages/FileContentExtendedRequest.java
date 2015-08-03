/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileContentExtendedRequest extends FileContentRequest {

	protected int size;
	protected int checkSum;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.debugger.messages.
	 * FileContentRequest#getType()
	 */
	public int getType() {
		return 10002;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.debugger.messages.
	 * FileContentRequest#serialize(java.io.DataOutputStream)
	 */
	public void serialize(DataOutputStream out) throws IOException {
		super.serialize(out);
		out.writeInt(getSize());
		out.writeInt(getCheckSum());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.debugger.messages.
	 * FileContentRequest#deserialize(java.io.DataInputStream)
	 */
	public void deserialize(DataInputStream in) throws IOException {
		super.deserialize(in);
		setSize(in.readInt());
		setCheckSum(in.readInt());
	}

	/**
	 * Returns size.
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets size.
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Returns checksum.
	 * 
	 * @return checksum
	 */
	public int getCheckSum() {
		return checkSum;
	}

	/**
	 * Sets checksum.
	 * 
	 * @param checkSum
	 */
	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

}