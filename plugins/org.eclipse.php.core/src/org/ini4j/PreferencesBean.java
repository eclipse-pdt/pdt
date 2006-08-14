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

import java.lang.reflect.*;
import java.util.prefs.*;

public class PreferencesBean {
	public static/*<T> T*/Object newInstance(final Class/*<T>*/clazz, final Preferences prefs) {
		return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new AbstractBeanInvocationHandler() {
			protected Object getPropertySpi(final String property, final Class/*<?>*/clazz) {
				return prefs.get(property, null);
			}

			protected boolean hasPropertySpi(final String property) {
				return prefs.get(property, null) != null;
			}

			protected void setPropertySpi(final String property, final Object value, final Class/*<?>*/clazz) {
				prefs.put(property, value.toString());
			}
		}));
	}
}
