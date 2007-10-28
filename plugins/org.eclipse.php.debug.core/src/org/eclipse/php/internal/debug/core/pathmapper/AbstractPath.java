/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.pathmapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents abstract storage for path-style entities (file-system paths, URLs)
 * @author michael
 *
 */
public class AbstractPath implements Cloneable {

	private final Pattern VOLNAME = Pattern.compile("([A-Za-z]:)[/\\\\](.*)");
	private final Pattern PROTOCOL = Pattern.compile("([A-Za-z]*://)(.*)");
	private LinkedList<String> segments;
	private String device;
	private char sepChar;

	/**
	 * Constructs new abstract path instance
	 * @param path Full path
	 */
	public AbstractPath(String path) {
		if (path == null) {
			throw new NullPointerException();
		}
		if (path.startsWith("\\\\")) { // Network path
			sepChar = '\\';
			device = "\\\\";
			path = path.substring(2);
		} else {
			Matcher m = VOLNAME.matcher(path);
			if (m.matches()) { // Windows path
				sepChar = '\\';
				device = m.group(1) + "\\"; // correct path from C:/ to C:\
				path = m.group(2);
			} else if (path.startsWith("/")) { // Unix path
				sepChar = '/';
				device = "/";
			} else {
				m = PROTOCOL.matcher(path);
				if (m.matches()) { // URL
					sepChar = '/';
					device = m.group(1);
					path = m.group(2);
				} else {
					throw new IllegalArgumentException("Illegal or not full path: " + path);
				}
			}
		}

		StringTokenizer st = new StringTokenizer(path, "/\\");
		segments = new LinkedList<String>();
		while (st.hasMoreTokens()) {
			String segment = st.nextToken();
			if (segment.length() > 0) {
				segments.add(segment);
			}
		}
	}

	protected AbstractPath(String device, char sepChar, LinkedList<String> segments) {
		this.device = device;
		this.sepChar = sepChar;
		this.segments = segments;
	}

	public String getLastSegment() {
		return segments.getLast();
	}

	public int getSegmentsCount() {
		return segments.size();
	}

	public String removeLastSegment() {
		return segments.removeLast();
	}

	public void addLastSegment(String segment) {
		segments.addLast(segment);
	}

	public String[] getSegments() {
		return segments.toArray(new String[segments.size()]);
	}

	public boolean isPrefixOf(AbstractPath path) {
		Iterator<String> i1 = segments.iterator();
		Iterator<String> i2 = path.segments.iterator();
		while (i1.hasNext() && i2.hasNext()) {
			if (!i1.next().equals(i2.next())) {
				return false;
			}
		}
		return !i1.hasNext();
	}

	public String toString() {
		StringBuilder buf = new StringBuilder(device);
		Iterator<String> i = segments.iterator();
		while (i.hasNext()) {
			buf.append(i.next());
			if (i.hasNext()) {
				buf.append(sepChar);
			}
		}
		return buf.toString();
	}

	protected AbstractPath clone() {
		LinkedList<String> segments = new LinkedList<String>();
		Iterator<String> i = this.segments.iterator();
		while (i.hasNext()) {
			segments.add(i.next());
		}
		AbstractPath path = new AbstractPath(device, sepChar, segments);
		return path;
	}

	public int hashCode() {
		return device.hashCode() + 13 * segments.hashCode() + 31 * sepChar;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractPath)) {
			return false;
		}
		AbstractPath other = (AbstractPath) obj;
		return other.device.equals(device) && other.segments.equals(segments) && other.sepChar == sepChar;
	}
}