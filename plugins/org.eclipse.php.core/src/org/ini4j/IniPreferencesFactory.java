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

import java.util.prefs.PreferencesFactory;
import java.util.prefs.Preferences;
import java.util.Properties;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

public class IniPreferencesFactory implements PreferencesFactory {
	public static final String KEY_SYSTEM = "org.ini4j.prefs.system"; //$NON-NLS-1$
	public static final String KEY_USER = "org.ini4j.prefs.user"; //$NON-NLS-1$
	public static final String PROPERTIES = "ini4j.properties"; //$NON-NLS-1$

	private Preferences _system;
	private Preferences _user;

	protected String getIniLocation(final String key) {
		String location = System.getProperty(key);

		if (location == null)
			try {
				final Properties props = new Properties();
				props.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES));
				location = props.getProperty(key);
			} catch (final Exception x) {
				;
			}

		return location;
	}

	protected URL getResource(final String location) throws IllegalArgumentException {
		try {
			final URI uri = new URI(location);
			URL url;

			if (uri.getScheme() == null)
				url = getClass().getClassLoader().getResource(location);
			else
				url = uri.toURL();

			return url;
		} catch (final Exception x) {
			throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
		}
	}

	protected InputStream getResourceAsStream(final String location) throws IllegalArgumentException {
		try {
			return getResource(location).openStream();
		} catch (final Exception x) {
			throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
		}
	}

	protected Preferences newIniPreferences(final String key) {
		final Ini ini = new Ini();

		final String location = getIniLocation(key);

		if (location != null)
			try {
				ini.load(getResourceAsStream(location), 0);
			} catch (final Exception x) {
				throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
			}

		return new IniPreferences(ini);
	}

	public synchronized Preferences systemRoot() {
		if (_system == null)
			_system = newIniPreferences(KEY_SYSTEM);

		return _system;
	}

	public synchronized Preferences userRoot() {
		if (_user == null)
			_user = newIniPreferences(KEY_USER);

		return _user;
	}
}
