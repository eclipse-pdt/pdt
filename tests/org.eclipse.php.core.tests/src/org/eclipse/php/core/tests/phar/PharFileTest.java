package org.eclipse.php.core.tests.phar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.php.core.tests.AbstractPDTTTest;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.internal.core.phar.PharConstants;
import org.eclipse.php.internal.core.phar.PharEntry;
import org.eclipse.php.internal.core.phar.PharFile;
import org.eclipse.php.internal.core.project.PHPNature;

public class PharFileTest extends AbstractPDTTTest {

	private static final class SamplePharTest extends PharFileTest {
		private final PharFile pharFile;

		private SamplePharTest(String description, PharFile pharFile) {
			super(description);
			this.pharFile = pharFile;
		}

		protected void setUp() throws Exception {
		}

		protected void tearDown() throws Exception {
			if (testFile != null) {
				testFile.delete(true, null);
				testFile = null;
			}
		}

		protected void runTest() throws Throwable {
			compareContent(File_To_Content, pharFile);
		}
	}

	protected static final char OFFSET_CHAR = '|';
	protected static final String[] TESTS = new String[] { "/workspace/phar" };

	protected static IProject project;
	protected static IFile testFile;
	protected static final String Line_Seperator = "\n";
	protected static final String Index_Php = "index.php";
	protected static final String Index_Php_Content = "<?php if (!file_exists(\"config.xml\")) {"
			+ Line_Seperator
			+ "	include \"install.php\";"
			+ Line_Seperator
			+ "	exit;"
			+ Line_Seperator
			+ "}"
			+ Line_Seperator
			+ "var_dump(str_replace(\"\\r\\n\", \"\\n\", file_get_contents(\"config.xml\")));"
			+ Line_Seperator + "?>";
	protected static final String Install_Php = "install.php";
	protected static final String Install_Php_Content = "<?php echo \"install\\n\"; ?>";
	protected static final String Stub_Content = "<?php"
			+ Line_Seperator
			+ "Phar::interceptFileFuncs();"
			+ Line_Seperator
			+ "if(file_exists(dirname(__FILE__) . \"/files/config.xml\")) {"
			+ Line_Seperator
			+ "    Phar::mount(\"config.xml\", dirname(__FILE__) . \"/files/config.xml\");"
			+ Line_Seperator + "}" + Line_Seperator
			+ "Phar::webPhar(\"blog\", \"index.php\");" + Line_Seperator
			+ "__HALT_COMPILER(); ?>" + Line_Seperator;

	protected static final Map<String, String> File_To_Content = new HashMap<String, String>();
	static {
		File_To_Content.put(Index_Php, Index_Php_Content);
		File_To_Content.put(Install_Php, Install_Php_Content);
		File_To_Content.put(PharConstants.STUB_PATH, Stub_Content);
	}

	public static void setUpSuite() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"PharFileTest");
		if (project.exists()) {
			return;
		}

		project.create(null);
		project.open(null);

		// configure nature
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID });
		project.setDescription(desc, null);
	}

	public static void tearDownSuite() throws Exception {
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public PharFileTest(String description) {
		super(description);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("PHAR Tests");

		for (String testsDirectory : TESTS) {
			for (final String fileName : getFiles(testsDirectory, ".phar")) {
				try {
					final PharFile pharFile = new PharFile(new File(FileLocator
							.getBundleFile(PHPCoreTests.getDefault()
									.getBundle()), fileName));
					suite.addTest(new SamplePharTest(fileName, pharFile));
				} catch (final Exception e) {
					suite.addTest(new TestCase(fileName) { // dummy
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

	public void compareContent(Map<String, String> proposals, PharFile pharFile)
			throws Exception {
		Map<String, PharEntry> pharEntryMap = pharFile.getPharEntryMap();
		for (Iterator<String> iterator = proposals.keySet().iterator(); iterator
				.hasNext();) {
			String filename = iterator.next();
			byte[] bytes = getBytes(pharFile.getInputStream(pharEntryMap
					.get(filename)));
			String expected = proposals.get(filename);

			assertContents(expected, new String(bytes));
		}
	}

	public static boolean byteArrayEquals(byte[] b1, byte[] b2) {
		if (b1 != null && b2 != null && b1.length == b2.length) {
			for (int i = 0; i < b1.length; i++) {
				if (b1[i] != b2[i])
					return false;
			}
			return true;
		} else {
			return false;
		}

	}

	public static byte[] getBytes(InputStream is) throws IOException {
		byte[] buffer = new byte[8192];
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

}
