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

public interface IAchiveOutputEntry {

	public String getName();

	public void setTime(long time);

	public long getTime();

	public void setSize(long size);

	public long getSize();

	public long getCompressedSize();

	public void setCompressedSize(long csize);

	public void setCrc(long crc);

	public long getCrc();

	public void setMethod(int method);

	public int getMethod();

	public boolean isDirectory();
}
