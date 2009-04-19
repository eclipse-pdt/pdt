package org.eclipse.php.core.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import junit.framework.Assert;

/**
 * Scripts are small units that provide a specific scenario to test
 * 
 * Example:
 * <pre>
 * --TEST--
 * Tests a simple class name completion
 * --FILE--
 * <? some PHP code ?>
 * --EXPECT--
 * Expected result
 * </pre>
 */
public class PdttFile {

	enum STATES {
		TEST, FILE, EXPECT
	}

	private String fileName;
	private String description;
	private String file = "";
	private String expected = "";

	/**
	 * Constructs new PdttFileReader
	 * @param fileName
	 * @throws Exception
	 */
	public PdttFile(String fileName) throws Exception {
		this.fileName = fileName;
		parse();
	}

	/**
	 * Returns the test description (--TEST-- section contents)
	 * @return
	 */
	public String getDescription() throws Exception {
		Assert.assertNotNull("File: " + fileName + " doesn't contain --TEST-- section", description);
		return description;
	}

	/**
	 * Returns the PHP file contents (--FILE-- section contents)
	 * @return
	 */
	public String getFile() {
		Assert.assertNotNull("File: " + fileName + " doesn't contain --FILE-- section", file);
		return file;
	}

	/**
	 * Returns the expected result (--EXPECT-- section contents)
	 * @return
	 */
	public String getExpected() {
		Assert.assertNotNull("File: " + fileName + " doesn't contain --EXPECT-- section", expected);
		return expected;
	}

	/**
	 * Internal method for parsing a .pdtt test file
	 * @throws Exception 
	 */
	protected void parse() throws Exception {
		BufferedReader bReader = new BufferedReader(new InputStreamReader(Activator.openResource(fileName)));

		String line = bReader.readLine();
		STATES state = null;
		while (line != null) {
			if (line.startsWith("--")) {
				if (line.equalsIgnoreCase("--TEST--")) {
					state = STATES.TEST;
				} else if (line.equalsIgnoreCase("--FILE--")) {
					state = STATES.FILE;
				} else if (line.equalsIgnoreCase("--EXPECT--")) {
					state = STATES.EXPECT;
				} else {
					throw new Exception("Wrong state: " + line);
				}
			} else {
				if (state != null) {
					switch (state) {
						case TEST:
							this.description = line;
							break;
						case FILE:
							this.file += (line + "\n");
							break;
						case EXPECT:
							this.expected += (line + "\n");
							break;
						default:
							break;
					}
				}
			}
			line = bReader.readLine();
		}
	}
}
