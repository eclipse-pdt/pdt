/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
	@Override
	public int getType() {
		return 10002;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.zend.debugger.messages.
	 * FileContentRequest#serialize(java.io.DataOutputStream)
	 */
	@Override
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
	@Override
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