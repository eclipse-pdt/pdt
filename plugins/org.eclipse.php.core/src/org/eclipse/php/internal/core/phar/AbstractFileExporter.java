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

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractFileExporter implements IFileExporter {

	protected PharPackage pharPackage;
	protected SignatureBufferedOutputStream fileContentStream;

	public AbstractFileExporter(PharPackage pharPackage) throws IOException {
		this.pharPackage = pharPackage;
		fileContentStream = new SignatureBufferedOutputStream(
				new FileOutputStream(pharPackage.getAbsolutePharLocation()
						.toString()), pharPackage);
	}

	public void writeSignature() throws IOException {
		if (pharPackage.isUseSignature()) {
			doWriteSignature();
		}
	}

	public abstract void doWriteSignature() throws IOException;
}
