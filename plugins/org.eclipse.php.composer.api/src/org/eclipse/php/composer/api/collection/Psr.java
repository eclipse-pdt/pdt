/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
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
import org.eclipse.php.composer.api.objects.Namespace;

/**
 * Represents a psr-0 entity in a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#psr-0
 * @author Thomas Gossmann <gos.si>
 */
public class Psr extends AbstractJsonObject<Namespace> implements Iterable<Namespace> {

	private transient PropertyChangeListener listener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
		}
	};

	public Psr() {
	}

	public Psr(String json) throws ParseException {
		fromJson(json);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedHashMap) {
			LinkedHashMap json = (LinkedHashMap) obj;

			for (Entry<String, Object> entry : ((Map<String, Object>) json).entrySet()) {
				Namespace nmspc = new Namespace();
				nmspc.setNamespace(entry.getKey());

				if (entry.getValue() instanceof LinkedList) {
					for (Object path : (LinkedList) entry.getValue()) {
						nmspc.add((String) path);
					}
				} else {
					nmspc.add((String) entry.getValue());
				}
				add(nmspc);
			}
		}
	}

	@Override
	protected Object buildJson() {
		LinkedHashMap<String, Object> out = new LinkedHashMap<String, Object>();
		for (Namespace nmspc : this) {
			Object value = ""; //$NON-NLS-1$

			if (nmspc.size() > 1) {
				value = getJsonValue(nmspc.getPaths());
			} else if (nmspc.size() == 1) {
				value = nmspc.getFirst();
			}

			out.put(nmspc.getNamespace(), value);
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
	public void add(Namespace namespace) {
		if (has(namespace)) {
			get(namespace.getNamespace()).addPaths(namespace.getPaths());
		} else {
			namespace.addPropertyChangeListener(listener);
			set(namespace.getNamespace(), namespace);
		}
	}

	/**
	 * Removes a dependency.
	 * 
	 * @param dependency
	 *            the dependency to remove
	 */
	public void remove(Namespace namespace) {
		namespace.removePropertyChangeListener(listener);
		super.remove(namespace.getNamespace());
	}

	public Collection<Namespace> getNamespaces() {
		return properties.values();
	}

	public Iterator<Namespace> iterator() {
		return (Iterator<Namespace>) properties.values().iterator();
	}

	public Namespace getFirst() {
		if (properties.values().iterator().hasNext()) {
			return properties.values().iterator().next();
		}

		return null;
	}

	public int size() {
		return properties.keySet().size();
	}

	public boolean has(String namespace) {
		return properties.containsKey(namespace);
	}

	public boolean has(Namespace namespace) {
		return has(namespace.getNamespace());
	}

	public boolean hasPath(String path) {
		for (Namespace nmspc : properties.values()) {
			if (nmspc.has(path)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the namespace for a given path or null if the path isn't found
	 * 
	 * @param path
	 *            the path
	 * @return the related namespace
	 */
	public Namespace getNamespaceForPath(String path) {
		for (Namespace nmspc : properties.values()) {
			if (nmspc.has(path)) {
				return nmspc;
			}
		}

		return null;
	}
}
