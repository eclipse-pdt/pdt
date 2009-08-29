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
