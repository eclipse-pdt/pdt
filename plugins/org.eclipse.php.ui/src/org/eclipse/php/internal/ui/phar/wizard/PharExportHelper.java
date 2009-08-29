package org.eclipse.php.internal.ui.phar.wizard;

import java.io.IOException;

import org.eclipse.php.internal.core.phar.*;

public class PharExportHelper {

	public static IFileExporter createFileExporter(PharPackage pharPackage)
			throws IOException {
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
