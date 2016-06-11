/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.phar.wizard;

import java.io.IOException;

import org.eclipse.php.internal.core.phar.*;

public class PharExportHelper {

	public static IFileExporter createFileExporter(PharPackage pharPackage) throws IOException {
		if (pharPackage.getExportType() == PharConstants.PHAR) {
			return new PharFileExporter(pharPackage);
		} else if (pharPackage.getExportType() == PharConstants.TAR) {
			return new TarFileExporter(pharPackage);
		} else if (pharPackage.getExportType() == PharConstants.ZIP) {
			return new ZipFileExporter(pharPackage);
		}
		return null;
	}

	public static IPharBuilder createPlainPharBuilder(PharPackage fJarPackage) {
		return new PlainPharBuilder();
	}

}
