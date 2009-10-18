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
