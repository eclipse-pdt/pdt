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

import java.io.IOException;

public interface IAchiveOutputStream {

	public void putNextEntry(IAchiveOutputEntry output) throws IOException;

	public void write(byte[] b, int off, int len) throws IOException;

	public void close() throws IOException;

}
