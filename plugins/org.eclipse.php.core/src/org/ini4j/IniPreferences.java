/*
 * Copyright 2005 [ini4j] Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ini4j;

import java.util.prefs.*;
import java.io.*;
import java.net.URL;

public class IniPreferences extends AbstractPreferences {
	protected class SectionPreferences extends AbstractPreferences {
		/** underlaying <code>Section</code> implementation */
		private Ini.Section _section;

		/**
		 * Constructs a new SectionPreferences instance on top of Ini.Section instance.
		 *
		 * @param parent parent preferences node
		 * @parem section underlaying Ini.Section instance
		 * @param isNew indicate is this a new node or already existing one
		 */
		SectionPreferences(final IniPreferences parent, final Ini.Section section, final boolean isNew) {
			super(parent, section.getName());
			_section = section;
			newNode = isNew;
		}

		/**
		 * Implements the <CODE>childrenNamesSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#childrenNamesSpi()}.
		 *
		 * This implementation allways returns an empty array.
		 *
		 * @return an emty array.
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		protected String[] childrenNamesSpi() {
			return EMPTY;
		}

		/**
		 * Implements the <CODE>childSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#childSpi(String)}.
		 *
		 * This implementation doesn't support this operation.
		 *
		 * @throws UnsupportedOperationException this implementation allways throws this exception
		 * @param name child name
		 * @return child node
		 */
		protected AbstractPreferences childSpi(final String name) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

		/**
		 * Implements the <CODE>flush</CODE> method as per the specification in
		 * {@link java.util.prefs.Preferences#flush()}.
		 *
		 * This implementation just call parent's <code>flush()</code> method.
		 *
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		public void flush() throws BackingStoreException {
			parent().flush();
		}

		/**
		 * Implements the <CODE>flushSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#flushSpi()}.
		 *
		 * This implementation does nothing.
		 *
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		protected void flushSpi() {
			;
		}

		/**
		 * Implements the <CODE>getSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#getSpi(String)}.
		 * @return if the value associated with the specified key at this preference node, or null if there is no association for this key, or the association cannot be determined at this time.
		 * @param key key to getvalue for
		 */
		protected String getSpi(final String key) {
			return _section.fetch(key)[0];
		}

		/**
		 * Implements the <CODE>keysSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#keysSpi()}.
		 *
		 * @return an array of the keys that have an associated value in this preference node.
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		protected String[] keysSpi() {
			return (String[]) _section.keySet().toArray(EMPTY);
		}

		/**
		 * Implements the <CODE>putSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#putSpi(String,String)}.
		 *
		 * @param key key to set value for
		 * @param value new value of key
		 */
		protected void putSpi(final String key, final String value) {
			_section.put(key, value);
		}

		/**
		 * Implements the <CODE>removeNodeSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#removeNodeSpi()}.
		 *
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		protected void removeNodeSpi() {
			_ini.remove(_section);
		}

		/**
		 * Implements the <CODE>removeSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#removeSpi(String)}.
		 * @param key key to remove
		 */
		protected void removeSpi(final String key) {
			_section.remove(key);
		}

		/**
		 * Implements the <CODE>sync</CODE> method as per the specification in
		 * {@link java.util.prefs.Preferences#sync()}.
		 *
		 * This implementation just call parent's <code>sync()</code> method.
		 *
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		public void sync() throws BackingStoreException {
			parent().sync();
		}

		/**
		 * Implements the <CODE>syncSpi</CODE> method as per the specification in
		 * {@link java.util.prefs.AbstractPreferences#syncSpi()}.
		 *
		 * This implementation does nothing.
		 *
		 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
		 */
		protected void syncSpi() {
			;
		}
	}

	/** frequently used empty String array */
	private static final String[] EMPTY = {};

	/** underlaying <code>Ini</code> implementation */
	private Ini _ini;

	/**
	 * Constructs a new preferences node on top of <code>Ini</code> instance.
	 *
	 * @param ini underlaying <code>Ini</code> instance
	 */
	public IniPreferences(final Ini ini) {
		super(null, ""); //$NON-NLS-1$
		_ini = ini;
	}

	/**
	 * Constructs a new preferences node based on newly loaded <code>Ini</code> instance.
	 *
	 * This is just a helper constructor, to make simpler constructing <code>IniPreferences</code>
	 * directly from <code>InputStream</code>.
	 *
	 * @param input the <code>InputStream</code> containing <code>Ini</code> data
	 * @throws IOException if an I/O error occured
	 * @throws InvalidIniFormatException if <code>Ini</code> parsing error occured
	 */
	public IniPreferences(final InputStream input) throws IOException, InvalidIniFormatException {
		super(null, ""); //$NON-NLS-1$
		_ini = new Ini(input, 0);
	}

	/**
	 * Constructs a new preferences node based on newly loaded <code>Ini</code> instance.
	 *
	 * This is just a helper constructor, to make simpler constructing <code>IniPreferences</code>
	 * directly from <code>Reader</code>.
	 *
	 * @param input the <code>Reader</code> containing <code>Ini</code> data
	 * @throws IOException if an I/O error occured
	 * @throws InvalidIniFormatException if <code>Ini</code> parsing error occured
	 */
	public IniPreferences(final Reader input) throws IOException, InvalidIniFormatException {
		super(null, ""); //$NON-NLS-1$
		_ini = new Ini(input, 0);
	}

	/**
	 * Constructs a new preferences node based on newly loaded <code>Ini</code> instance.
	 *
	 * This is just a helper constructor, to make simpler constructing <code>IniPreferences</code>
	 * directly from <code>URL</code>.
	 *
	 * @param input the <code>URL</code> containing <code>Ini</code> data
	 * @throws IOException if an I/O error occured
	 * @throws InvalidIniFormatException if <code>Ini</code> parsing error occured
	 */
	public IniPreferences(final URL input) throws IOException, InvalidIniFormatException {
		super(null, ""); //$NON-NLS-1$
		_ini = new Ini(input, 0);
	}

	/**
	 * Implements the <CODE>childrenNamesSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#childrenNamesSpi()}.
	 * @return an array containing the names of the children of this preference node.
	 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
	 */
	protected String[] childrenNamesSpi() {
		return (String[]) _ini.keySet().toArray(EMPTY);
	}

	/**
	 * Implements the <CODE>childSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#childSpi(String)}.
	 * @param name child name
	 * @return child node
	 */
	protected AbstractPreferences childSpi(final String name) {
		Ini.Section sec = (Ini.Section) _ini.get(name);
		final boolean isNew = sec == null;

		if (isNew)
			sec = _ini.add(name);

		return new SectionPreferences(this, sec, isNew);
	}

	/**
	 * Implements the <CODE>flushSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#flushSpi()}.
	 *
	 * This implementation does nothing.
	 *
	 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
	 */
	protected void flushSpi() {
		;
	}

	/**
	 * Provide access to underlaying {@link org.ini4j.Ini} implementation.
	 *
	 * @return <code>Ini</code> implementation
	 */
	protected Ini getIni() {
		return _ini;
	}

	/**
	 * Implements the <CODE>getSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#getSpi(String)}.
	 *
	 * This implementation doesn't support this operation, so allways throws UnsupportedOperationException.
	 *
	 * @return if the value associated with the specified key at this preference node, or null if there is no association for this key, or the association cannot be determined at this time.
	 * @param key key to getvalue for
	 * @throws UnsupportedOperationException this implementation allways throws this exception
	 */
	protected String getSpi(final String key) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Implements the <CODE>keysSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#keysSpi()}.
	 *
	 * This implementation allways return an empty array.
	 *
	 * @return an empty array.
	 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
	 */
	protected String[] keysSpi() {
		return EMPTY;
	}

	/**
	 * Implements the <CODE>putSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#putSpi(String,String)}.
	 *
	 * This implementation doesn;t support this operation, so allways throws UnsupportedOperationException.
	 *
	 * @param key key to set value for
	 * @param value new value for key
	 * @throws UnsupportedOperationException this implementation allways throws this exception
	 */
	protected void putSpi(final String key, final String value) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Implements the <CODE>removeNodeSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#removeNodeSpi()}.
	 *
	 * This implementation doesn;t support this operation, so allways throws UnsupportedOperationException.
	 * @throws UnsupportedOperationException this implementation allways throws this exception
	 * @throws BackingStoreException this implementation never throws this exception
	 */
	protected void removeNodeSpi() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Implements the <CODE>removeSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#removeSpi(String)}.
	 * @param key key to remove
	 * @throws UnsupportedOperationException this implementation allways throws this exception
	 */
	protected void removeSpi(final String key) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Implements the <CODE>syncSpi</CODE> method as per the specification in
	 * {@link java.util.prefs.AbstractPreferences#syncSpi()}.
	 *
	 * This implementation does nothing.
	 *
	 * @throws BackingStoreException if this operation cannot be completed due to a failure in the backing store, or inability to communicate with it.
	 */
	protected void syncSpi() {
		;
	}
}
