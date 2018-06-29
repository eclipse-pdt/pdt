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

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractFileExporter implements IFileExporter {

	protected PharPackage pharPackage;
	protected SignatureBufferedOutputStream fileContentStream;

	public AbstractFileExporter(PharPackage pharPackage) throws IOException {
		this.pharPackage = pharPackage;
		fileContentStream = new SignatureBufferedOutputStream(
				new FileOutputStream(pharPackage.getAbsolutePharLocation().toString()), pharPackage);
	}

	@Override
	public void writeSignature() throws IOException {
		if (pharPackage.isUseSignature()) {
			doWriteSignature();
		}
	}

	public abstract void doWriteSignature() throws IOException;
}
