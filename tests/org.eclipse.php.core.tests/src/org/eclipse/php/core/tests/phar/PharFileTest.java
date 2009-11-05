package org.eclipse.php.core.tests.phar;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.phar.PharConstants;
import org.eclipse.php.internal.core.phar.PharEntry;
import org.eclipse.php.internal.core.phar.PharFile;
import org.eclipse.php.internal.core.phar.PharFileExporter;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.php.internal.core.phar.Stub;
import org.eclipse.php.internal.core.phar.digest.Digest;

/**
 * Phar Unit tests Each folder under PHAR_PHARS_FOLDER is scanned and produces
 * three tests for different signatures
 * 
 * @author zaho
 */

public class PharFileTest extends AbstractPDTTTest {

	private static final String PHAR_PHARS_FOLDER = "/workspace/phar";
	protected static final String[] TESTS = new String[] { PHAR_PHARS_FOLDER };

	public PharFileTest(String description) {
		super(description);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("PHAR Tests");

		for (String testsDirectory : TESTS) {
			File folder = getResourceFolder(testsDirectory).toFile();
			try {
				for (final File pharFolder : folder.listFiles()) {
					if (pharFolder.getName().equalsIgnoreCase("CVS"))
						continue;
					if (pharFolder.isDirectory()) {
						IPath stubLocation = new Path(pharFolder
								.getAbsolutePath())
								.append(PharConstants.STUB_PATH);
						PharPackage pharPackage = new PharPackage();

						if (stubLocation.toFile().exists()) {
							pharPackage.setStubGenerated(false);
							pharPackage.setStubLocation(stubLocation);
						}
						pharPackage.setExportType(PharConstants.PHAR);
						pharPackage
								.setCompressType(PharConstants.NONE_COMPRESSED);

						pharPackage.setSignature(Digest.SHA1_TYPE);
						suite.addTest(new PharTest(pharFolder.getName(),
								pharFolder.getAbsolutePath(), pharPackage));

						pharPackage
								.setCompressType(PharConstants.BZ2_COMPRESSED);

						pharPackage.setSignature(Digest.SHA1_TYPE);
						suite.addTest(new PharTest(pharFolder.getName(),
								pharFolder.getAbsolutePath(), pharPackage));

						pharPackage
								.setCompressType(PharConstants.GZ_COMPRESSED);

						pharPackage.setSignature(Digest.MD5_TYPE);
						suite.addTest(new PharTest(pharFolder.getName(),
								pharFolder.getAbsolutePath(), pharPackage));
					}

				}
			} catch (final Exception e) {
				suite.addTest(new TestCase(folder.getAbsolutePath()) { // dummy
							// test
							// indicating
							// PDTT
							// file
							// parsing
							// failure
							protected void runTest() throws Throwable {
								throw e;
							}
						});
			}
		}

		// Create a setup wrapper
		TestSetup setup = new TestSetup(suite) {
			protected void setUp() throws Exception {
				setUpSuite();
			}

			protected void tearDown() throws Exception {
				tearDownSuite();
			}
		};
		return setup;
	}

	public static void setUpSuite() throws Exception {
	}

	public static void tearDownSuite() throws Exception {
	}

	private static IPath getResourceFolder(String fileName) {
		IPath path = null;
		try {
			path = new Path(FileLocator.getBundleFile(
					PHPCoreTests.getDefault().getBundle()).getAbsolutePath())
					.append(fileName);
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		return path;
	}

	public void compareContent(String pharFileFolder, PharFile pharFile)
			throws Exception {
		Map<String, PharEntry> pharEntryMap = pharFile.getPharEntryMap();
		for (Iterator<String> iterator = pharEntryMap.keySet().iterator(); iterator
				.hasNext();) {
			String filename = iterator.next();

			if (PharConstants.SIGNATURE_PATH.endsWith(filename)
					|| PharConstants.STUB_PATH.endsWith(filename))
				continue;
			File file = new File(pharFileFolder, filename);
			Assert.assertTrue(inputStreamEquals(new BufferedInputStream(
					new FileInputStream(file)), pharFile
					.getInputStream(pharEntryMap.get(filename))));
		}
	}

	public static boolean inputStreamEquals(InputStream is1, InputStream is2)
			throws IOException {
		byte[] buffer1 = new byte[512];
		byte[] buffer2 = new byte[512];
		int n1, n2;
		try {
			while ((n1 = is1.read(buffer1, 0, buffer1.length)) != -1) {
				n2 = is2.read(buffer2, 0, buffer2.length);
				if (n1 != n2) {
					return false;
				}
				if (!byteArrayEquals(buffer1, buffer2, n1))
					return false;
			}
			n2 = is2.read(buffer2, 0, buffer2.length);
			if (n1 != n2) {// both should be -1
				return false;
			}
		} finally {
			is1.close();
			is2.close();
		}
		return true;
	}

	public static boolean byteArrayEquals(byte[] b1, byte[] b2, int n) {
		if (b1 != null && b2 != null && b1.length == b2.length) {
			for (int i = 0; i < n; i++) {
				if (b1[i] != b2[i])
					return false;
			}
			return true;
		} else {
			return false;
		}

	}

	public static byte[] getBytes(InputStream is) throws IOException {
		byte[] buffer = new byte[512];
		ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);

		int n;

		baos.reset();
		try {
			while ((n = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}

		return baos.toByteArray();
	}

	private static final class PharTest extends PharFileTest {

		private final String pharFileFolder;
		private final IPath path;
		private PharPackage pharPackage;

		private PharTest(String description, String resourceFolder,
				PharPackage pharPackage) {
			super(description);
			pharFileFolder = resourceFolder;
			path = new Path(pharFileFolder);
			this.pharPackage = pharPackage;
		}

		protected void setUp() throws Exception {
		}

		protected void tearDown() throws Exception {
		}

		protected void runTest() throws Throwable {
			File tempPhar = exportTempPhar(pharFileFolder);
			compareContent(pharFileFolder, new PharFile(tempPhar));
			tempPhar.delete();
		}

		private File exportTempPhar(String pharFileFolder) throws IOException,
				CoreException {
			File result = File.createTempFile("temp", ".phar");
			// result.deleteOnExit();

			pharPackage.setPharLocation(new Path(result.getAbsolutePath()));
			PharFileExporter exporter = new PharFileExporter(pharPackage);
			Stub stub = new Stub(pharPackage);
			exporter.writeStub(stub);
			File file = new File(pharFileFolder);

			File[] children = file.listFiles();
			for (int i = 0; i < children.length; i++) {
				export(exporter, children[i]);
			}
			exporter.writeSignature();
			exporter.finished();
			return result;
		}

		private void export(PharFileExporter exporter, File file)
				throws IOException, CoreException {
			if (file.isFile()) {
				exportFile(exporter, file);
			} else {
				exportFolder(exporter, file);
			}
		}

		private void exportFolder(PharFileExporter exporter, File file)
				throws IOException, CoreException {
			if (file.getName().equalsIgnoreCase("CVS"))
				return;
			File[] children = file.listFiles();
			for (int i = 0; i < children.length; i++) {
				export(exporter, children[i]);
			}
		}

		private void exportFile(PharFileExporter exporter, File file)
				throws IOException, CoreException {
			String destinationPath = getDestinationPath(file);
			if (destinationPath.equals(PharConstants.STUB_PATH)
					|| destinationPath.equals(PharConstants.SIGNATURE_PATH)) {
				return;
			}
			exporter.write(file, destinationPath);
		}

		private String getDestinationPath(File file) {
			IPath filePath = new Path(file.getAbsolutePath());
			filePath = filePath.removeFirstSegments(path.segmentCount());
			filePath = filePath.setDevice(null);
			return filePath.toString();
		}
	}

}
