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
package org.eclipse.php.internal.core.tar;

import java.io.IOException;
import java.io.InputStream;

public class CBZip2InputStreamForPhar extends CBZip2InputStream {

	public CBZip2InputStreamForPhar(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected void init() throws IOException {
		in.skip(2);
		super.init();
	}
}
