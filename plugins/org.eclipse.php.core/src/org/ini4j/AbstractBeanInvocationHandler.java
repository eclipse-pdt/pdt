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

import java.beans.*;
import java.lang.reflect.*;
import java.io.*;
import java.net.URL;
import java.net.URI;
import java.util.*;

abstract class AbstractBeanInvocationHandler implements InvocationHandler {
	private static final String ADD_PREFIX = "add"; //$NON-NLS-1$
	private static final int ADD_PREFIX_LEN = ADD_PREFIX.length();
	private static final String HAS_PREFIX = "has"; //$NON-NLS-1$
	private static final int HAS_PREFIX_LEN = HAS_PREFIX.length();
	private static final String PARSE_METHOD = "valueOf"; //$NON-NLS-1$
	private static final String PROPERTY_CHANGE_LISTENER = "PropertyChangeListener"; //$NON-NLS-1$
	private static final String READ_BOOLEAN_PREFIX = "is"; //$NON-NLS-1$
	private static final int READ_BOOLEAN_PREFIX_LEN = READ_BOOLEAN_PREFIX.length();
	private static final String READ_PREFIX = "get"; //$NON-NLS-1$
	private static final int READ_PREFIX_LEN = READ_PREFIX.length();
	private static final String REMOVE_PREFIX = "remove"; //$NON-NLS-1$
	private static final int REMOVE_PREFIX_LEN = REMOVE_PREFIX.length();
	private static final String VETOABLE_CHANGE_LISTENER = "VetoableChangeListener"; //$NON-NLS-1$
	private static final String WRITE_PREFIX = "set"; //$NON-NLS-1$
	private static final int WRITE_PREFIX_LEN = WRITE_PREFIX.length();

	protected static Object parseSpecialValue(final String value, final Class clazz) throws IllegalArgumentException {
		Object o;

		try {
			if (clazz == File.class)
				o = new File(value);
			else if (clazz == URL.class)
				o = new URL(value);
			else if (clazz == URI.class)
				o = new URI(value);
			else if (clazz == Class.class)
				o = Class.forName(value);
			else if (clazz == TimeZone.class)
				o = TimeZone.getTimeZone(value);
			else {
				// look for "valueOf" converter method
				final Method parser = clazz.getMethod(PARSE_METHOD, new Class[] { String.class });
				o = parser.invoke(null, new Object[] { value });
			}
		} catch (final Exception x) {
			throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
		}
		return o;
	}

	protected static Object parseValue(final String value, final Class clazz) throws IllegalArgumentException {
		if (clazz == null)
			throw new IllegalArgumentException("null argument"); //$NON-NLS-1$

		Object o = null;

		if (value == null)
			o = zero(clazz);
		else if (clazz.isPrimitive())
			try {
				if (clazz == Boolean.TYPE)
					o = Boolean.valueOf(value);
				else if (clazz == Byte.TYPE)
					o = Byte.valueOf(value);
				else if (clazz == Character.TYPE)
					o = new Character(value.charAt(0));
				else if (clazz == Double.TYPE)
					o = Double.valueOf(value);
				else if (clazz == Float.TYPE)
					o = Float.valueOf(value);
				else if (clazz == Integer.TYPE)
					o = Integer.valueOf(value);
				else if (clazz == Long.TYPE)
					o = Long.valueOf(value);
				else if (clazz == Short.TYPE)
					o = Short.valueOf(value);
			} catch (final Exception x) {
				throw (IllegalArgumentException) new IllegalArgumentException().initCause(x);
			}
		else if (clazz == String.class)
			o = value;
		else if (clazz == Character.class)
			o = new Character(value.charAt(0));
		else
			o = parseSpecialValue(value, clazz);

		return o;
	}

	protected static Object zero(final Class clazz) {
		Object o = null;

		if (clazz.isPrimitive())
			if (clazz == Boolean.TYPE)
				o = Boolean.FALSE;
			else if (clazz == Byte.TYPE)
				o = new Byte((byte) 0);
			else if (clazz == Character.TYPE)
				o = new Character('\0');
			else if (clazz == Double.TYPE)
				o = new Double(0.0);
			else if (clazz == Float.TYPE)
				o = new Float(0.0f);
			else if (clazz == Integer.TYPE)
				o = new Integer(0);
			else if (clazz == Long.TYPE)
				o = new Long(0L);
			else if (clazz == Short.TYPE)
				o = new Short((short) 0);
		return o;
	}

	private PropertyChangeSupport _pcSupport;

	private Object _proxy;

	private VetoableChangeSupport _vcSupport;

	protected AbstractBeanInvocationHandler() {
		;
	}

	protected synchronized void addPropertyChangeListener(final String property, final PropertyChangeListener listener) {
		if (_pcSupport == null)
			_pcSupport = new PropertyChangeSupport(_proxy);

		_pcSupport.addPropertyChangeListener(property, listener);
	}

	protected synchronized void addVetoableChangeListener(final String property, final VetoableChangeListener listener) {
		if (_vcSupport == null)
			_vcSupport = new VetoableChangeSupport(_proxy);
		_vcSupport.addVetoableChangeListener(property, listener);
	}

	protected synchronized void firePropertyChange(final String property, final Object oldValue, final Object newValue) {
		if (_pcSupport != null)
			_pcSupport.firePropertyChange(property, oldValue, newValue);
	}

	protected synchronized void fireVetoableChange(final String property, final Object oldValue, final Object newValue) throws PropertyVetoException {
		if (_vcSupport != null)
			_vcSupport.fireVetoableChange(property, oldValue, newValue);
	}

	protected synchronized Object getProperty(final String property, final Class/*<?>*/clazz) {
		Object o;

		try {
			o = getPropertySpi(property, clazz);

			if (o == null)
				o = zero(clazz);
			else if (o instanceof String && !clazz.equals(String.class))
				o = parseValue((String) o, clazz);
		} catch (final Exception x) {
			o = zero(clazz);
		}

		return o;
	}

	protected abstract Object getPropertySpi(String property, Class/*<?>*/clazz);

	protected synchronized Object getProxy() {
		return _proxy;
	}

	protected synchronized boolean hasProperty(final String property) {
		boolean ret;

		try {
			ret = hasPropertySpi(property);
		} catch (final Exception x) {
			ret = false;
		}

		return ret;
	}

	protected abstract boolean hasPropertySpi(String property);

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws PropertyVetoException {
		Object ret = null;
		final String name = method.getName();
		String property;

		synchronized (this) {
			if (_proxy == null)
				_proxy = proxy;
		}

		if (name.startsWith(READ_PREFIX)) {
			property = Introspector.decapitalize(name.substring(READ_PREFIX_LEN));
			ret = getProperty(property, method.getReturnType());
		} else if (name.startsWith(READ_BOOLEAN_PREFIX)) {
			property = Introspector.decapitalize(name.substring(READ_BOOLEAN_PREFIX_LEN));
			ret = getProperty(property, method.getReturnType());
		} else if (name.startsWith(WRITE_PREFIX)) {
			property = Introspector.decapitalize(name.substring(WRITE_PREFIX_LEN));
			setProperty(property, args[0], method.getParameterTypes()[0]);
		} else if (name.startsWith(ADD_PREFIX)) {
			final String listener = name.substring(ADD_PREFIX_LEN);

			if (listener.equals(PROPERTY_CHANGE_LISTENER))
				addPropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
			else if (listener.equals(VETOABLE_CHANGE_LISTENER))
				addVetoableChangeListener((String) args[0], (VetoableChangeListener) args[1]);
		} else if (name.startsWith(REMOVE_PREFIX)) {
			final String listener = name.substring(REMOVE_PREFIX_LEN);

			if (listener.equals(PROPERTY_CHANGE_LISTENER))
				removePropertyChangeListener((String) args[0], (PropertyChangeListener) args[1]);
			else if (listener.equals(VETOABLE_CHANGE_LISTENER))
				removeVetoableChangeListener((String) args[0], (VetoableChangeListener) args[1]);
		} else if (name.startsWith(HAS_PREFIX)) {
			property = Introspector.decapitalize(name.substring(HAS_PREFIX_LEN));
			ret = Boolean.valueOf(hasProperty(property));
		}

		return ret;
	}

	protected synchronized void removePropertyChangeListener(final String property, final PropertyChangeListener listener) {
		if (_pcSupport != null)
			_pcSupport.removePropertyChangeListener(property, listener);
	}

	protected synchronized void removeVetoableChangeListener(final String property, final VetoableChangeListener listener) {
		if (_vcSupport != null)
			_vcSupport.removeVetoableChangeListener(property, listener);
	}

	protected synchronized void setProperty(final String property, Object value, final Class/*<?>*/clazz) throws PropertyVetoException {
		try {
			final boolean pc = _pcSupport != null && _pcSupport.hasListeners(property);
			final boolean vc = _vcSupport != null && _vcSupport.hasListeners(property);
			final Object old = pc || vc ? getProperty(property, clazz) : null;

			if (vc)
				fireVetoableChange(property, old, value);

			if (clazz.equals(String.class) && !(value instanceof String))
				value = value.toString();

			setPropertySpi(property, value, clazz);

			if (pc)
				firePropertyChange(property, old, value);
		} catch (final PropertyVetoException x) {
			throw x;
		} catch (final Exception x) {
			;
		}
	}

	protected abstract void setPropertySpi(String property, Object value, Class/*<?>*/clazz);
}
