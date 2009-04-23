package org.eclipse.php.core.tests;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

/**
 * Scripts are small units that provide a specific scenario to test
 * 
 * Example:
 * <pre>
 * --TEST--
 * Tests a simple class name completion
 * --CONFIG--
 * Custom configuration
 * --FILE--
 * <? some PHP code ?>
 * --EXPECT--
 * Expected result
 * </pre>
 */
public class PdttFile {

	protected enum STATES {
		TEST, CONFIG, FILE, EXPECT
	}

	private String fileName;
	private Map<String, String> config = new HashMap<String, String>();
	private String description;
	private String file = "";
	private String expected = "";

	/**
	 * Constructs new PdttFile
	 * @param fileName
	 * @throws Exception
	 */
	public PdttFile(String fileName) throws Exception {
		this.fileName = fileName;
		parse();
	}
	
	/**
	 * Constructs new PdttFile
	 * @param fileName .pdtt file name 
	 * @param description Test description
	 * @param config Config section
	 * @param file PHP source code
	 * @param expected Expected result
	 */
	public PdttFile(String fileName, String description, Map<String, String> config, String file, String expected) {
		if (fileName == null || description == null || file == null || expected == null) {
			throw new IllegalArgumentException();
		}
		this.fileName = fileName;
		this.description = description;
		if (config != null) {
			this.config = config;
		}
		this.file = file;
		this.expected = expected;
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
	 * Returns the configuration entries (--CONFIG-- section contents in format key:value)
	 * @return
	 */
	public Map<String, String> getConfig() {
		return config;
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
		BufferedReader bReader = new BufferedReader(new InputStreamReader(PHPCoreTests.openResource(fileName)));

		String line = bReader.readLine();
		STATES state = null;
		while (line != null) {
			if (line.matches("--[A-Z]+--")) {
				state = parseStateLine(line);
				if (state == null) {
					throw new Exception("Wrong state: " + line);
				}
			} else {
				if (state != null) {
					onState(state, line);
				}
			}
			line = bReader.readLine();
		}
	}
	
	/**
	 * Dumps this file back to the disk
	 * @throws Exception
	 */
	public void write() throws Exception {
		PrintWriter w = new PrintWriter(new FileWriter(fileName));
		writeStates(w);
		w.close();
	}
	
	protected void writeStates(PrintWriter w) {
		w.println("--TEST--");
		w.println(description.trim());
		if (config != null && config.size() > 0) {
			w.println("--CONFIG--");
			for (String key : config.keySet()) {
				w.print(key);
				w.print(':');
				w.println(config.get(key));
			}
		}
		w.println("--FILE--");
		w.println(file.trim());
		w.println("--EXPECT--");
		w.println(expected.trim());
	}
	
	/**
	 * Detects the state from the line
	 * @param line
	 * @return STATE
	 */
	protected STATES parseStateLine(String line) {
		if (line.equalsIgnoreCase("--TEST--")) {
			return STATES.TEST;
		}
		if (line.equalsIgnoreCase("--FILE--")) {
			return STATES.FILE;
		}
		if (line.equalsIgnoreCase("--EXPECT--")) {
			return STATES.EXPECT;
		}
		if (line.equalsIgnoreCase("--CONFIG--")) {
			return STATES.CONFIG;
		}
		return null;
	}

	/**
	 * This callback is called while processing state section
	 * @param state
	 * @param line
	 * @throws Exception 
	 */
	protected void onState(STATES state, String line) throws Exception {
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
			case CONFIG:
				int i = line.indexOf(':');
				if (i == -1) {
					throw new Exception("Wrong option in --CONFIG-- section: " + line);
				}
				String key = line.substring(0, i);
				String value = line.substring(i + 1);
				this.config.put(key.trim(), value.trim());
				break;
			default:
				break;
		}
	}
}
