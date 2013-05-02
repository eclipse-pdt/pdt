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
package org.eclipse.php.internal.debug.core.phpIni;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for modifying INI file. At the end {@link #close()} must
 * be called, otherwise the file will be leaved unmodified.
 * 
 * @author michael
 */
public class INIFileModifier {

	private static final String GLOBAL_SECTION = "__global__"; //$NON-NLS-1$
	private static final Pattern SECTION_PATTERN = Pattern
			.compile("\\[([^\\]]+)\\]"); //$NON-NLS-1$
	private static final Pattern NAME_VAL_PATTERN = Pattern
			.compile("([\\w]+)\\s*=\\s*(.*)"); //$NON-NLS-1$

	class INIFileSection {
		String name;
		List<String> lines;

		public INIFileSection(String name) {
			this.name = name;
			this.lines = new LinkedList<String>();
		}
	}

	private File configFile;
	private List<INIFileSection> sections;

	/**
	 * Create new INI file modifier class instance. If provided INI file doesn't
	 * exist - it will be created.
	 * 
	 * @param configFile
	 *            INI file path
	 * @throws IOException
	 */
	public INIFileModifier(String configFile) throws IOException {
		this(new File(configFile));
	}

	/**
	 * Create new INI file modifier class instance. If provided INI file doesn't
	 * exist - it will be created.
	 * 
	 * @param configFile
	 *            INI file object
	 * @throws IOException
	 */
	public INIFileModifier(File configFile) throws IOException {
		this.configFile = configFile;
		this.sections = new LinkedList<INIFileSection>();

		read();
	}

	/**
	 * Adds new entry to the INI file. New entry will be added to the default
	 * (unnamed) section
	 * 
	 * @param name
	 *            Entry name
	 * @param value
	 *            Value name
	 * @param replace
	 *            Whether to replace the old entry
	 */
	public void addEntry(String name, String value, boolean replace) {
		addEntry(GLOBAL_SECTION, name, value, replace, null);
	}

	/**
	 * Adds new entry to the INI file. New entry will be added to the default
	 * (unnamed) section, no old entries will be replaced
	 * 
	 * @param name
	 *            Entry name
	 * @param value
	 *            Value name
	 */
	public void addEntry(String name, String value) {
		addEntry(GLOBAL_SECTION, name, value, false, null);
	}

	/**
	 * Adds new entry to the INI file. New entry will be added to the given
	 * section, no old entries will be replaced
	 * 
	 * @param sectionName
	 *            Section name
	 * @param name
	 *            Entry name
	 * @param value
	 *            Value name
	 */
	public void addEntry(String sectionName, String name, String value) {
		addEntry(sectionName, name, value, false, null);
	}

	/**
	 * Adds new entry to the INI file. If <code>replace</code> is
	 * <code>true</code> the old entry will be replaced, otherwise - add a new
	 * one.
	 * 
	 * @param sectionName
	 *            Section name
	 * @param name
	 *            Entry name
	 * @param value
	 *            Value name
	 * @param replace
	 *            Whether to replace the old entry or add a new one
	 * @param replacePattern
	 *            Pattern to check against existing entry value, before
	 *            replacing it. If <code>replacePattern</code> is
	 *            <code>null</code> - every entry that matches the given name
	 *            will be replaced
	 */
	public void addEntry(String sectionName, String name, String value,
			boolean replace, String replacePattern) {
		if (sectionName == null || name == null || value == null) {
			throw new NullPointerException();
		}
		for (INIFileSection section : sections) {
			if (section.name.equals(sectionName)) {
				if (replace) {
					for (int i = 0; i < section.lines.size(); ++i) {
						Matcher m = NAME_VAL_PATTERN.matcher(section.lines
								.get(i));
						if (m.matches()) {
							String oldName = m.group(1);
							String oldValue = m.group(2);
							if (oldName.equals(name)
									&& (replacePattern == null || oldValue
											.matches(replacePattern))) {
								section.lines.set(i, name + '='
										+ quoteString(value));
							}
						}
					}
				} else {
					section.lines.add(name + '=' + quoteString(value));
				}
				break;
			}
		}
	}

	/**
	 * Puts quotes around the given string
	 * 
	 * @param str
	 *            String
	 * @return result string
	 */
	private String quoteString(String str) {
		if (str.startsWith("\"") && str.endsWith("\"") || str.startsWith("'") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				&& str.endsWith("'")) { //$NON-NLS-1$
			return str;
		}
		return '"' + str + '"';
	}

	/**
	 * Removes entry from the INI file from the global section.
	 * 
	 * @param name
	 *            Entry name
	 * @param removePattern
	 *            Pattern to check against existing entry value, before removing
	 *            it. If <code>removePattern</code> is <code>null</code> every
	 *            entry that matches the given name will be removed.
	 * @return <code>true</code> if some entry was removed, otherwise - false
	 */
	public boolean removeEntry(String name, String removePattern) {
		return removeEntry(GLOBAL_SECTION, name, removePattern);
	}

	/**
	 * Removes all entries from the INI file from all sections. Same as
	 * <code>removeEntry(null, name, null)</code>.
	 * 
	 * @param name
	 *            Entry name
	 * @return <code>true</code> if some entry was removed, otherwise - false
	 * @see #removeEntry(String, String, String)
	 */
	public boolean removeAllEntries(String name) {
		return removeEntry(null, name, null);
	}

	/**
	 * Removes all entries from the INI file from all sections. Same as
	 * <code>removeEntry(null, name, removePattern)</code>.
	 * 
	 * @param name
	 *            Entry name
	 * @param removePattern
	 *            Pattern to check against existing entry value, before removing
	 *            it. If <code>removePattern</code> is <code>null</code> every
	 *            entry that matches the given name will be removed.
	 * 
	 * @return <code>true</code> if some entry was removed, otherwise - false
	 * @see #removeEntry(String, String, String)
	 */
	public boolean removeAllEntries(String name, String removePattern) {
		return removeEntry(null, name, removePattern);
	}

	/**
	 * Removes entry from the INI file from the given section.
	 * 
	 * @param sectionName
	 *            Section name. If <code>sectionName</code> is <code>null</code>
	 *            , matching entries from all sections will be removed.
	 * 
	 * @param name
	 *            Entry name
	 * @param removePattern
	 *            Pattern to check against existing entry value, before removing
	 *            it. If <code>removePattern</code> is <code>null</code> every
	 *            entry that matches the given name will be removed.
	 * @return <code>true</code> if some entry was removed, otherwise - false
	 */
	public boolean removeEntry(String sectionName, String name,
			String removePattern) {
		if (name == null) {
			throw new NullPointerException();
		}
		boolean removed = false;
		for (INIFileSection section : sections) {
			if (sectionName == null || section.name.equals(sectionName)) {
				for (int i = 0; i < section.lines.size(); ++i) {
					Matcher m = NAME_VAL_PATTERN.matcher(section.lines.get(i));
					if (m.matches()) {
						String oldName = m.group(1);
						String oldValue = m.group(2);
						if (oldName.equals(name)
								&& (removePattern == null || oldValue
										.matches(removePattern))) {
							section.lines.remove(i--);
							removed = true;
						}
					}
				}
				if (sectionName != null) {
					break;
				}
			}
		}
		return removed;
	}

	/**
	 * Removes entry from the INI file from the global section.
	 * 
	 * @param name
	 *            Entry name
	 * @param commentPattern
	 *            Pattern to check against existing entry value, before removing
	 *            it. If <code>commentPattern</code> is <code>null</code> every
	 *            entry that matches the given name will be commented.
	 */
	public void commentEntry(String name, String commentPattern) {
		commentEntry(GLOBAL_SECTION, name, commentPattern);
	}

	/**
	 * Removes entry from the INI file from all sections. Same as
	 * <code>commentEntry(null, name, commentPattern)</code>.
	 * 
	 * @param name
	 *            Entry name
	 * @param commentPattern
	 *            Pattern to check against existing entry value, before removing
	 *            it. If <code>commentPattern</code> is <code>null</code> every
	 *            entry that matches the given name will be commented.
	 * 
	 * @see #commentEntry(String, String, String)
	 */
	public void commentAllEntries(String name, String commentPattern) {
		commentEntry(null, name, commentPattern);
	}

	/**
	 * Removes entry from the INI file from the given section.
	 * 
	 * @param sectionName
	 *            Section name. If <code>sectionName</code> is <code>null</code>
	 *            , matching entries from all sections will be commented.
	 * 
	 * @param name
	 *            Entry name
	 * @param commentPattern
	 *            Pattern to check against existing entry value, before removing
	 *            it. If <code>commentPattern</code> is <code>null</code> every
	 *            entry that matches the given name will be commented.
	 */
	public void commentEntry(String sectionName, String name,
			String commentPattern) {
		if (name == null) {
			throw new NullPointerException();
		}
		for (INIFileSection section : sections) {
			if (sectionName == null || section.name.equals(sectionName)) {
				for (int i = 0; i < section.lines.size(); ++i) {
					String line = section.lines.get(i);
					Matcher m = NAME_VAL_PATTERN.matcher(line);
					if (m.matches()) {
						String oldName = m.group(1);
						String oldValue = m.group(2);
						if (!line.startsWith(";") //$NON-NLS-1$
								&& oldName.equals(name)
								&& (commentPattern == null || oldValue
										.matches(commentPattern))) {
							section.lines.set(i, ';' + line);
						}
					}
				}
				if (sectionName != null) {
					break;
				}
			}
		}
	}

	/**
	 * Writes all changes to the INI configuration file
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		flush();
	}

	/**
	 * Reads INI file contents
	 * 
	 * @throws IOException
	 */
	protected void read() throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(configFile));
		String line;
		INIFileSection currentSection = new INIFileSection(GLOBAL_SECTION);
		sections.add(currentSection);
		while ((line = r.readLine()) != null) {
			line = line.trim();
			Matcher m = SECTION_PATTERN.matcher(line);
			if (m.matches()) {
				String sectionName = m.group(1);
				currentSection = new INIFileSection(sectionName);
				sections.add(currentSection);
			} else {
				currentSection.lines.add(line);
			}
		}
		r.close();
	}

	/**
	 * Writes INI file contents back to the file
	 * 
	 * @throws IOException
	 */
	protected void flush() throws IOException {
		PrintWriter w = new PrintWriter(new FileWriter(configFile));
		for (INIFileSection section : sections) {
			if (section.name != GLOBAL_SECTION) {
				w.println('[' + section.name + ']');
			}
			for (String line : section.lines) {
				w.println(line);
			}
		}
		w.close();
	}
}
