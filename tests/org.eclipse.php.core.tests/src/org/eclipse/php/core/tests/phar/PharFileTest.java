/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests.phar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.internal.core.phar.PharConstants;
import org.eclipse.php.internal.core.phar.PharEntry;
import org.eclipse.php.internal.core.phar.PharFile;
import org.eclipse.php.internal.core.phar.PharFileExporter;
import org.eclipse.php.internal.core.phar.PharPackage;
import org.eclipse.php.internal.core.phar.Stub;
import org.eclipse.php.internal.core.phar.digest.Digest;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Phar Unit tests Each folder under PHAR_PHARS_FOLDER is scanned and produces
 * three tests for different signatures
 * 
 * @author zaho
 */

@RunWith(Parameterized.class)
public class PharFileTest {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	private static final String PHAR_PHARS_FOLDER = "/workspace/phar";
	protected static final String[] TESTS = new String[] { PHAR_PHARS_FOLDER };
	private static final String[] DIGEST_TYPES = new String[] { // nl
			Digest.SHA1_TYPE, // nl
			Digest.SHA256_TYPE, // nl
			Digest.SHA512_TYPE, // nl
			Digest.MD5_TYPE };
	private static final int[] COMPRESS_MODES = new int[] { // nl
			PharConstants.NONE_COMPRESSED, // nl
			PharConstants.GZ_COMPRESSED, // nl
			PharConstants.BZ2_COMPRESSED };

	@Parameters(name = "{1} - {2} - {3} - {4}")
	public static Iterable<Object[]> data() throws Exception {
		List<Object[]> list = new LinkedList<>();
		for (String testsDirectory : TESTS) {
			File folder = getResourceFolder(testsDirectory).toFile();
			for (final File pharFolder : folder.listFiles()) {
				if (pharFolder.getName().equalsIgnoreCase("CVS")) {
					continue;
				}
				if (pharFolder.isDirectory()) {
					IPath stubLocation = new Path(pharFolder.getAbsolutePath()).append(PharConstants.STUB_PATH);
					PharPackage pharPackage = new PharPackage();

					if (stubLocation.toFile().exists()) {
						pharPackage.setStubGenerated(false);
						pharPackage.setStubLocation(stubLocation);
					}
					pharPackage.setExportType(PharConstants.PHAR);
					for (String digestType : DIGEST_TYPES) {
						for (int iCompressType : COMPRESS_MODES) {
							list.add(new Object[] { pharFolder.getName(), pharFolder.getAbsolutePath(), pharPackage,
									iCompressType, digestType });
						}
					}
				}
			}
		}
		return list;
	}

	private static IPath getResourceFolder(String fileName) {
		IPath path = null;
		try {
			path = new Path(FileLocator.getBundleFile(PHPCoreTests.getDefault().getBundle()).getAbsolutePath())
					.append(fileName);
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		return path;
	}

	public void compareContent(String pharFileFolder, PharFile pharFile) throws Exception {
		Map<String, PharEntry> pharEntryMap = pharFile.getPharEntryMap();
		for (Entry<String, PharEntry> entry : pharEntryMap.entrySet()) {
			String filename = entry.getKey();

			if (PharConstants.SIGNATURE_PATH.endsWith(filename) || PharConstants.STUB_PATH.endsWith(filename)) {
				continue;
			}
			File file = new File(pharFileFolder, filename);
			assertTrue(inputStreamEquals(new BufferedInputStream(new FileInputStream(file)),
					pharFile.getInputStream(entry.getValue())));
		}
	}

	public static boolean inputStreamEquals(InputStream is1, InputStream is2) throws IOException {
		byte[] buffer1 = new byte[512];
		byte[] buffer2 = new byte[512];
		int n1, n2;
		try {
			while ((n1 = is1.read(buffer1, 0, buffer1.length)) != -1) {
				n2 = is2.read(buffer2, 0, buffer2.length);
				if (n1 != n2) {
					return false;
				}
				if (!byteArrayEquals(buffer1, buffer2, n1)) {
					return false;
				}
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
				if (b1[i] != b2[i]) {
					return false;
				}
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

	private final String pharFileFolder;
	private final IPath path;
	private final int compressionType;
	private final String signature;
	private PharPackage pharPackage;

	public PharFileTest(String description, String resourceFolder, PharPackage pharPackage, int compressionType,
			String signature) {
		pharFileFolder = resourceFolder;
		path = new Path(pharFileFolder);
		this.pharPackage = pharPackage;
		this.compressionType = compressionType;
		this.signature = signature;
	}

	@Before
	public void before() {
		pharPackage.setCompressType(this.compressionType);
		pharPackage.setSignature(this.signature);
	}

	@Test
	public void runTest() throws Throwable {
		validateSignatureIsSupported(pharPackage.getSignature());
		File tempPhar = exportTempPhar(pharFileFolder);
		compareContent(pharFileFolder, new PharFile(tempPhar));
		tempPhar.delete();
	}

	private void validateSignatureIsSupported(String signatureName) {
		Digest digest = Digest.DIGEST_MAP.get(signatureName);
		assertTrue(digest != null);
		assertFalse(Digest.NULL_DIGEST.equals(digest.getDigest()));
	}

	private File exportTempPhar(String pharFileFolder) throws IOException, CoreException {
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

	private void export(PharFileExporter exporter, File file) throws IOException, CoreException {
		if (file.isFile()) {
			exportFile(exporter, file);
		} else {
			exportFolder(exporter, file);
		}
	}

	private void exportFolder(PharFileExporter exporter, File file) throws IOException, CoreException {
		if (file.getName().equalsIgnoreCase("CVS")) {
			return;
		}
		File[] children = file.listFiles();
		for (int i = 0; i < children.length; i++) {
			export(exporter, children[i]);
		}
	}

	private void exportFile(PharFileExporter exporter, File file) throws IOException, CoreException {
		String destinationPath = getDestinationPath(file);
		if (destinationPath.equals(PharConstants.STUB_PATH) || destinationPath.equals(PharConstants.SIGNATURE_PATH)) {
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
