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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class ServiceFinder {

	protected static Object findService(final String serviceId, final String defaultService) throws IllegalArgumentException {
		try {
			return findServiceClass(serviceId, defaultService).newInstance();
		} catch (final Exception x) {
			throw (IllegalArgumentException) new IllegalArgumentException("Provider " + serviceId + " could not be instantiated: " + x).initCause(x); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}


	protected static Class findServiceClass(final String serviceId, final String defaultService) throws IllegalArgumentException {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final String serviceClassName = findServiceClassName(serviceId, defaultService);

		try {
			return classLoader == null ? Class.forName(serviceClassName) : classLoader.loadClass(serviceClassName);
		} catch (final ClassNotFoundException x) {
			throw (IllegalArgumentException) new IllegalArgumentException("Provider " + serviceClassName + " not found").initCause(x); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected static String findServiceClassName(final String serviceId, final String defaultService) throws IllegalArgumentException {
		if (defaultService == null)
			throw new IllegalArgumentException("Provider for " + serviceId + " cannot be found"); //$NON-NLS-1$ //$NON-NLS-2$

		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String serviceClassName = null;

		// Use the system property first
		try {
			final String systemProp = System.getProperty(serviceId);

			if (systemProp != null)
				serviceClassName = systemProp;
		} catch (final SecurityException x) {
			;
		}

		if (serviceClassName == null) {
			final String servicePath = "META-INF/services/" + serviceId; //$NON-NLS-1$

			// try to find services in CLASSPATH
			try {
				InputStream is = null;

				if (classLoader == null)
					is = ClassLoader.getSystemResourceAsStream(servicePath);
				else
					is = classLoader.getResourceAsStream(servicePath);

				if (is != null) {
					final BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8")); //$NON-NLS-1$

					String line = rd.readLine();
					rd.close();

					if (line != null && !"".equals(line = line.trim())) //$NON-NLS-1$
						serviceClassName = line.split("\\s|#")[0]; //$NON-NLS-1$
				}
			} catch (final Exception x) {
				;
			}
		}

		if (serviceClassName == null)
			serviceClassName = defaultService;

		return serviceClassName;
	}
}
