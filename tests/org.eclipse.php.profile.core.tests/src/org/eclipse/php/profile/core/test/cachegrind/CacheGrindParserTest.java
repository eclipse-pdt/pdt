/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Dawid Pakuła - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.test.cachegrind;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindParser;
import org.eclipse.php.profile.core.engine.cachegrind.CacheGrindParser.CacheGrindParserListener;
import org.eclipse.php.profile.core.test.Activator;
import org.junit.Test;

public class CacheGrindParserTest {

	private InputStream getFile(String path) throws IOException {
		return Activator.getDefault().getBundle().getEntry(path).openStream();
	}

	private class Name {
		public int index;
		public String name;

		public Name(int index, String name) {
			this.index = index;
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Name) {
				Name tmp = (Name) obj;
				if (tmp.index != this.index) {
					return false;
				}
				if (tmp.name == null && name != null || name == null && tmp.name != null) {
					return false;
				} else if (tmp.name != null) {
					return tmp.name.equals(name);
				}
				return true;

			}
			return super.equals(obj);
		}

		@Override
		public String toString() {
			return new StringBuilder("Name(").append(index).append(',').append(name == null ? "null" : name).append(')')
					.toString();
		}
	}

	private class Nums {
		public int first;
		public int[] other;

		public Nums(int first, int[] other) {
			this.first = first;
			this.other = other;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Nums) {
				Nums tmp = (Nums) obj;
				if (first != tmp.first) {
					return false;
				}

				if (tmp.other == null && other != null || other == null && tmp.other != null) {
					return false;
				} else if (tmp.other != null) {
					if (tmp.other.length != other.length) {
						return false;
					}
					int index = 0;
					for (int val : tmp.other) {
						if (other[index++] != val) {
							return false;
						}
					}
				}
				return true;
			}
			return super.equals(obj);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("Nums(").append(first).append(',');
			if (other == null) {
				sb.append("null");
			} else {
				sb.append('[');
				Arrays.stream(other).forEach(i -> sb.append(i).append(','));
				sb.append(']');
			}
			return sb.append(')').toString();

		}
	}

	private class Listener implements CacheGrindParserListener {
		public String[] events;
		public String[] positions;
		public String creator;
		public String version;
		public String cmd;
		public int part;
		public List<String> unknown = new ArrayList<>();
		public List<Name> files = new ArrayList<>();
		public List<Name> functions = new ArrayList<>();
		public List<Name> nextFiles = new ArrayList<>();
		public List<Name> nextFunctions = new ArrayList<>();

		public List<Nums> costs = new ArrayList<>();
		public List<Nums> calls = new ArrayList<>();

		public int[] totals;
		public int[] summary;
		public int pid;

		@Override
		public void version(String version) {
			this.version = version;
		}

		@Override
		public void creator(String creator) {
			this.creator = creator;
		}

		@Override
		public void events(String[] events) {
			this.events = events;
		}

		@Override
		public void unknown(String line) {
			unknown.add(line);
		}

		@Override
		public void cmd(String cmd) {
			this.cmd = cmd;
		}

		@Override
		public void part(int part) {
			this.part = part;
		}

		@Override
		public void totals(int[] sum) {
			this.totals = sum;
		}

		@Override
		public void summary(int[] sum) {
			this.summary = sum;
		}

		@Override
		public void pid(int pid) {
			this.pid = pid;
		}

		@Override
		public void positions(String[] type) {
			this.positions = type;
		}

		@Override
		public void function(int id, String name) {
			functions.add(new Name(id, name));
		}

		@Override
		public void file(int id, String name) {
			files.add(new Name(id, name));
		}

		@Override
		public void nextFunction(int id, String name) {
			nextFunctions.add(new Name(id, name));
		}

		@Override
		public void nextFile(int id, String name) {
			nextFiles.add(new Name(id, name));
		}

		@Override
		public void calls(int count, int[] values) {
			calls.add(new Nums(count, values));
		}

		@Override
		public void cost(int pos, int[] events) {
			costs.add(new Nums(pos, events));
		}

	}

	@Test
	public void test() throws IOException {
		InputStream file = getFile("/resources/cachegrind/profile_header.01");

		CacheGrindParser parser = new CacheGrindParser(file);
		Listener listener = new Listener();
		parser.parse(listener);

		assertEquals("xdebug 2.6.0 (PHP 7.1.13)", listener.creator);
		assertEquals("1", listener.version);
		assertEquals("/src/web/app_dev.php", listener.cmd);
		assertEquals(10, listener.pid);
		assertEquals(1, listener.part);

		assertArrayEquals(new String[] { "instr", "line" }, listener.positions);
		assertArrayEquals(new String[] { "Cycles", "Instructions", "Flops" }, listener.events);

		assertArrayEquals(new int[] { 10, 11 }, listener.totals);
		assertArrayEquals(new int[] { 10, 12 }, listener.summary);
		assertArrayEquals(new String[] { "desc: unsupported" }, listener.unknown.toArray(new String[0]));

		// files
		assertEquals(5, listener.files.size());
		assertEquals(new Name(-1, "file1.c"), listener.files.get(0));
		assertEquals(new Name(-1, "file2.c"), listener.files.get(1));
		assertEquals(new Name(-1, "file3.c"), listener.files.get(2));
		assertEquals(new Name(1, "file1.c"), listener.files.get(3));
		assertEquals(new Name(1, null), listener.files.get(4));

		// functions
		assertEquals(2, listener.functions.size());
		assertEquals(new Name(-1, "main"), listener.functions.get(0));
		assertEquals(new Name(1, "main"), listener.functions.get(1));

		// next files
		assertEquals(3, listener.nextFiles.size());
		assertEquals(new Name(-1, "cfile1.c"), listener.nextFiles.get(0));
		assertEquals(new Name(-1, "cfile2.c"), listener.nextFiles.get(1));
		assertEquals(new Name(2, null), listener.nextFiles.get(2));

		// next functions
		assertEquals(1, listener.nextFunctions.size());
		assertEquals(new Name(-1, "main"), listener.nextFunctions.get(0));

		// calls
		assertEquals(1, listener.calls.size());
		assertEquals(new Nums(23, new int[] { -3, 4 }), listener.calls.get(0));

		// cost
		assertEquals(1, listener.costs.size());
		assertEquals(new Nums(23, new int[] { 0, 0, 3 }), listener.costs.get(0));

		file.close();

	}
}
