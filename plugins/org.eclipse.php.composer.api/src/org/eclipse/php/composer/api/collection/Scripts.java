/*******************************************************************************
 * Copyright (c) 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.collection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.php.composer.api.entities.AbstractJsonObject;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.objects.Script;

/**
 * Represents a scripts entity in a composer package.
 */
public class Scripts extends AbstractJsonObject<Script> implements Iterable<Script> {

	public static String[] getEvents() {
		return new String[] { "pre-install-cmd", "post-install-cmd", "pre-update-cmd", "post-update-cmd",
				"pre-package-install", "post-package-install", "pre-package-update", "post-package-update",
				"pre-package-uninstall", "post-package-uninstall" };
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
						script.add((String) handler);
					}
				} else {
					script.add((String) entry.getValue());
				}
				add(script);
			}
		}
	}

	@Override
	protected Object buildJson() {
		LinkedHashMap<String, Object> out = new LinkedHashMap<String, Object>();
		for (Script script : this) {
			Object value = "";

			if (script.size() > 1) {
				value = getJsonValue(script.getHandlers());
			} else if (script.size() == 1) {
				value = script.getFirst();
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
			get(script.getScript()).addHandlers(script.getHandlers());
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
		for (Script script : properties.values()) {
			if (script.has(handler)) {
				return true;
			}
		}

		return false;
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

	private JsonArray getCmd(String script) {
		if (!has(script)) {
			Script s = new Script();
			s.setScript(script);
			add(s);
		}

		return properties.get(script).getHandlers();
	}

	/**
	 * Gets scripts that will occur before the
	 * 
	 * <pre>
	 * install
	 * </pre>
	 * 
	 * command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPreInstallCmd() {
		return getCmd("pre-install-cmd");
	}

	/**
	 * Gets scripts that will occur after the
	 * 
	 * <pre>
	 * install
	 * </pre>
	 * 
	 * command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostInstallCmd() {
		return getCmd("post-install-cmd");
	}

	/**
	 * Gets scripts that will occur before the
	 * 
	 * <pre>
	 * update
	 * </pre>
	 * 
	 * command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPreUpdateCmd() {
		return getCmd("pre-update-cmd");
	}

	/**
	 * Gets scripts that will occur after the
	 * 
	 * <pre>
	 * update
	 * </pre>
	 * 
	 * command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostUpdateCmd() {
		return getCmd("post-update-cmd");
	}

	/**
	 * Gets scripts that will occur before a package is installed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPrePackageInstall() {
		return getCmd("pre-package-install");
	}

	/**
	 * Gets scripts that will occur after a package is installed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostPackageInstall() {
		return getCmd("post-package-install");
	}

	/**
	 * Gets scripts that will occur before a package is updated.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPrePackageUpdate() {
		return getCmd("pre-package-update");
	}

	/**
	 * Gets scripts that will occur after a package is updated.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostPackageUpdate() {
		return getCmd("post-package-update");
	}

	/**
	 * Gets scripts that will occur before a package is uninstalled.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPrePackageUninstall() {
		return getCmd("pre-package-uninstall");
	}

	/**
	 * Gets scripts that will occur after a package is uninstalled.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostPackageUninstall() {
		return getCmd("post-package-uninstall");
	}
}
