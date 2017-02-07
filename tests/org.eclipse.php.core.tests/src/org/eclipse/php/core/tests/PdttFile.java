/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.tests;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Scripts are small units that provide a specific scenario to test
 * 
 * Example:
 * 
 * <pre>
 * --TEST--
 * Tests a simple class name completion
 * --CONFIG--
 * Custom configuration
 * --PREFERENCES--
 * Eclipse preference options to set before running this test
 * --FILE--
 * &lt;? some PHP code ?&gt;
 * --EXPECT--
 * Expected result
 * </pre>
 */
public class PdttFile {

	protected enum STATES {

		TEST("--TEST--"), CONFIG("--CONFIG--"), PREFERENCES("--PREFERENCES--"), FILE("--FILE--"), EXPECT(
				"--EXPECT--"), OTHER("--OTHER--"), OTHER_FILE("--FILE([0-9]+)--");

		private static class Names {
			private static Map<String, STATES> map = new HashMap<String, STATES>();
		}

		String name;

		STATES(String name) {
			this.name = name;
			Names.map.put(name.toLowerCase(), this);
		}

		public String getName() {
			return name;
		}

		public static STATES byName(String name) {
			return Names.map.get(name.toLowerCase());
		}
	}

	private String fileName;
	private Bundle testBundle;
	private Map<String, String> config = new HashMap<String, String>();
	private String preferences;
	private String description;
	private String file = "";
	/**
	 * @since 3.5 List of additional files
	 */
	private List<CharSequence> otherFiles = new LinkedList<CharSequence>();
	private int currentFile;
	private String expected = "";
	private String other;

	/**
	 * Constructs new PdttFile using default bundle: {@link PHPCoreTests}
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public PdttFile(String fileName) throws Exception {
		this(PHPCoreTests.getDefault().getBundle(), fileName, null);
	}

	/**
	 * Constructs new PdttFile
	 * 
	 * @param testBundle
	 *            The testing plug-in
	 * @param fileName
	 * @throws Exception
	 */
	public PdttFile(Bundle testBundle, String fileName) throws Exception {
		this(testBundle, fileName, null);
	}

	/**
	 * Constructs new PdttFile
	 * 
	 * @param testBundle
	 *            The testing plug-in
	 * @param fileName
	 * @param charsetName
	 *            charset to use (if null, platform's default charset will be
	 *            used)
	 * @throws Exception
	 */
	public PdttFile(Bundle testBundle, String fileName, String charsetName) throws Exception {
		this.testBundle = testBundle;
		this.fileName = fileName;
		InputStream reader = openResource(fileName);
		try {
			parse(reader, charsetName);
		} finally {
			if (reader != null) {
				reader.close();
			}
			List<CharSequence> tmp = new ArrayList<CharSequence>(otherFiles.size());
			int i = 0;
			for (CharSequence seq : otherFiles) {
				tmp.add(i, seq.toString());
			}
			otherFiles = tmp;
		}
	}

	/**
	 * Returns the test description (--TEST-- section contents)
	 * 
	 * @return
	 */
	public String getDescription() throws Exception {
		assertNotNull("File: " + fileName + " doesn't contain --TEST-- section", description);
		return description;
	}

	/**
	 * Returns the configuration entries (--CONFIG-- section contents in format
	 * key:value)
	 * 
	 * @return
	 */
	public Map<String, String> getConfig() {
		return config;
	}

	/**
	 * Returns the preferences (--PREFERENCES-- section contents)
	 * 
	 * @return
	 */
	public String getPreferences() {
		return preferences;
	}

	/**
	 * Applies Eclipse preferences specified in --PREFERENCES-- option.
	 * 
	 * @throws CoreException
	 */
	public void applyPreferences() throws CoreException {
		if (preferences != null) {
			Platform.getPreferencesService().importPreferences(new ByteArrayInputStream(preferences.getBytes()));
		}
	}

	/**
	 * Returns the PHP file contents (--FILE-- section contents)
	 * 
	 * @return
	 */
	public String getFile() {
		assertNotNull("File: " + fileName + " doesn't contain --FILE-- section", file);
		return file;
	}

	/**
	 * Returns the PDTT file name.
	 * 
	 * @return the PDTT file name
	 */
	public String getFileName() {
		return fileName;
	}

	public String getOtherFile(int index) {
		assertNotNull(new StringBuilder("File:").append(fileName).append('[').append(index)
				.append("] doesn't contain --FILE").append(index).append("-- section").toString(),
				otherFiles.get(index));

		return otherFiles.get(index).toString();
	}

	public String[] getOtherFiles() {
		return otherFiles.toArray(new String[otherFiles.size()]);
	}

	/**
	 * Returns the expected result (--EXPECT-- section contents)
	 * 
	 * @return
	 */
	public String getExpected() {
		assertNotNull("File: " + fileName + " doesn't contain --EXPECT-- section", expected);
		return expected;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	protected InputStream openResource(String path) throws IOException {
		File localFile = new File(path);
		if (localFile.exists()) {
			return new FileInputStream(localFile);
		}
		URL url = testBundle.getEntry(path);
		return new BufferedInputStream(url.openStream());
	}

	/**
	 * Internal method for parsing a .pdtt test file
	 * 
	 * @param inputStream
	 * @param charsetName
	 *            charset to use (if null, platform's default charset will be
	 *            used)
	 * @throws Exception
	 */
	protected void parse(InputStream inputStream, String charsetName) throws Exception {
		BufferedReader bReader = charsetName != null
				? new BufferedReader(new InputStreamReader(inputStream, charsetName))
				: new BufferedReader(new InputStreamReader(inputStream));

		String line = bReader.readLine();
		STATES state = null;
		while (line != null) {
			if (line.matches("--[A-Z0-9]+--")) {
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

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	/**
	 * Dumps this file back to the disk
	 * 
	 * @throws Exception
	 */
	public void write() throws Exception {
		PrintWriter w = new PrintWriter(new FileWriter(fileName));
		writeStates(w);
		w.close();
	}

	protected void writeStates(PrintWriter w) {
		w.println(STATES.TEST.getName());
		w.println(description.trim());
		if (config != null && config.size() > 0) {
			w.println(STATES.CONFIG.getName());
			for (String key : config.keySet()) {
				w.print(key);
				w.print(':');
				w.println(config.get(key));
			}
		}
		if (preferences != null) {
			w.println(STATES.PREFERENCES.getName());
			w.println(preferences.trim());
		}
		w.println(STATES.FILE.getName());
		w.println(file.trim());
		w.println(STATES.EXPECT.getName());
		w.println(expected.trim());
		if (other != null) {
			w.println(STATES.OTHER.getName());
			w.println(other.trim());
		}
		if (otherFiles.size() > 0) {
			int i = 0;
			for (CharSequence seq : otherFiles) {
				w.println(STATES.OTHER_FILE.getName() + i);
				w.println(seq.toString().trim());
			}
		}
	}

	/**
	 * Detects the state from the line
	 * 
	 * @param line
	 * @return STATE
	 */
	protected STATES parseStateLine(String line) {
		STATES state = STATES.byName(line);
		if (state == null) {
			Pattern compile = Pattern.compile(STATES.OTHER_FILE.name);
			Matcher matcher = compile.matcher(line);
			if (matcher.matches()) {
				state = STATES.OTHER_FILE;
				currentFile = Integer.valueOf(matcher.group(1));
				otherFiles.add(currentFile, new StringBuilder());
			}
		}

		return state;
	}

	/**
	 * This callback is called while processing state section
	 * 
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
		case OTHER_FILE:
			((StringBuilder) this.otherFiles.get(currentFile)).append(line).append('\n');
			break;
		case EXPECT:
			this.expected += (line + "\n");
			break;
		case OTHER:
			if (this.other == null) {
				this.other = "";
			}
			this.other += (line + "\n");
			break;
		case CONFIG:
			int i = line.indexOf(':');
			if (i == -1) {
				throw new Exception("Wrong option in " + STATES.CONFIG.getName() + " section: " + line);
			}
			String key = line.substring(0, i);
			String value = line.substring(i + 1);
			this.config.put(key.trim(), value.trim());
			break;
		case PREFERENCES:
			if (line != null && line.length() > 0) {
				if (preferences == null) {
					this.preferences = (line + "\n");
				} else {
					this.preferences += (line + "\n");
				}
			}
			break;
		default:
			break;
		}
	}
}
