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

import java.io.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class Stub implements IStub {

	private PharPackage jarPackage;

	public Stub(PharPackage jarPackage) {
		this.jarPackage = jarPackage;
	}

	public void write(OutputStream os) throws CoreException, IOException {
		if (jarPackage.isStubGenerated()) {
			os.write(PharConstants.PHP_START);

			os.write(PharConstants.Line_Seperator);

			os.write(PharConstants.STUB_ENDS);
			os.write(PharConstants.STUB_TAIL);

			os.write(PharConstants.Line_Seperator);
		} else {
			InputStream contentStream = getStubInputStream();
			try {
				PharUtil.checkStubVilidaty(contentStream);
			} finally {
				if (contentStream != null) {
					contentStream.close();
				}
			}
			contentStream = getStubInputStream();
			try {
				int n;
				byte[] readBuffer = new byte[4096];
				while ((n = contentStream.read(readBuffer)) > 0) {
					os.write(readBuffer, 0, n);
				}
			} finally {
				if (contentStream != null) {
					contentStream.close();
				}
			}
		}

	}

	private InputStream getStubInputStream() throws CoreException,
			FileNotFoundException {
		InputStream contentStream = null;
		IFile stubFile = jarPackage.getStubFile();
		if (stubFile.exists()) {
			contentStream = stubFile.getContents(false);
		} else {
			contentStream = new BufferedInputStream(new FileInputStream(
					jarPackage.getStubLocation().toFile()));
		}
		return contentStream;
	}
}
