/*******************************************************************************
 * Copyright (c) 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api.collection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.php.composer.api.entities.AbstractJsonObject;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.objects.Script;
import org.eclipse.php.composer.api.objects.Script.HandlerValue;

/**
 * Represents a scripts entity in a composer package.
 */
public class Scripts extends AbstractJsonObject<Script> implements Iterable<Script> {

	public static String[] getEvents() {
		return new String[] { "pre-install-cmd", "post-install-cmd", "pre-update-cmd", "post-update-cmd", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"pre-package-install", "post-package-install", "pre-package-update", "post-package-update", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"pre-package-uninstall", "post-package-uninstall" }; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private transient PropertyChangeListener listener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
		}
	};

	public Scripts() {
	}

	public Scripts(String json) throws ParseException {
		fromJson(json);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedHashMap) {
			LinkedHashMap json = (LinkedHashMap) obj;

			for (Entry<String, Object> entry : ((Map<String, Object>) json).entrySet()) {
				Script script = new Script();
				script.setScript(entry.getKey());

				if (entry.getValue() instanceof LinkedList) {
					for (Object handler : (LinkedList) entry.getValue()) {
						script.add(new HandlerValue((String) handler));
					}
				} else {
					script.add(new HandlerValue((String) entry.getValue()));
				}
				add(script);
			}
		}
	}

	@Override
	protected Object buildJson() {
		LinkedHashMap<String, Object> out = new LinkedHashMap<String, Object>();
		for (Script script : this) {
			Object value = ""; //$NON-NLS-1$

			if (script.size() > 1) {
				value = script.getHandlersJsonValue();
			} else if (script.size() == 1) {
				value = script.getFirst().getAsString();
			}

			out.put(script.getScript(), value);
		}

		return out;
	}

	/**
	 * Adds a new dependency.
	 * 
	 * @param dependency
	 *            the new dependency
	 * @return this
	 */
	public void add(Script script) {
		if (has(script)) {
			get(script.getScript()).addHandlers(script);
		} else {
			script.addPropertyChangeListener(listener);
			set(script.getScript(), script);
		}
	}

	/**
	 * Removes a dependency.
	 * 
	 * @param dependency
	 *            the dependency to remove
	 */
	public void remove(Script script) {
		script.removePropertyChangeListener(listener);
		super.remove(script.getScript());
	}

	public Collection<Script> getScripts() {
		return properties.values();
	}

	public Iterator<Script> iterator() {
		return (Iterator<Script>) properties.values().iterator();
	}

	public Script getFirst() {
		if (properties.values().iterator().hasNext()) {
			return properties.values().iterator().next();
		}

		return null;
	}

	public int size() {
		return properties.keySet().size();
	}

	public boolean has(String script) {
		return properties.containsKey(script);
	}

	public boolean has(Script script) {
		return has(script.getScript());
	}

	public boolean hasHandler(String handler) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the script for a given handler or null if the handler isn't found
	 * 
	 * @param handler
	 *            the handler
	 * @return the related script
	 */
	public Script getScriptForHandler(String handler) {
		for (Script script : properties.values()) {
			if (script.has(handler)) {
				return script;
			}
		}

		return null;
	}
}
