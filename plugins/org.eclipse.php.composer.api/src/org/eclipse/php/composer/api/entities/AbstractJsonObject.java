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
package org.eclipse.php.composer.api.entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.objects.JsonObject;

public abstract class AbstractJsonObject<V> extends JsonEntity implements JsonCollection {

	private transient Map<String, PropertyChangeListener> listeners = new HashMap<String, PropertyChangeListener>();
	protected transient Map<String, V> properties = new LinkedHashMap<String, V>();
	private transient Log log = LogFactory.getLog(HttpClient.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedHashMap) {
			List<String> fields = getFieldNames(this.getClass());
			LinkedHashMap json = (LinkedHashMap) obj;
			for (Entry<String, Object> entry : ((Map<String, Object>) obj).entrySet()) {
				String property = entry.getKey();

				if (fields.contains(property)) {
					parseField(json, property);
				} else {
					Object value = null;
					if (json.containsKey(property)) {
						value = json.get(property);

						if (value instanceof LinkedList) {
							value = new JsonArray(value);
						} else if (value instanceof LinkedHashMap) {
							value = new JsonObject(value);
						}
					}
					set(property, value);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void parseField(LinkedHashMap json, String property) {
		if (json.containsKey(property)) {
			Field field = getFieldByName(this.getClass(), property);

			if (field != null && JsonEntity.class.isAssignableFrom(field.getType())) {
				try {
					field.setAccessible(true);
					JsonEntity entity = (JsonEntity) field.get(this);
					entity.fromJson(json.get(property));
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	@Override
	protected Object buildJson() {
		LinkedList<String> propsOrder = new LinkedList<String>(sortOrder);

		// First: create an index to search for field names and add them to the
		// props order
		HashMap<String, Field> namedFields = new HashMap<String, Field>();
		for (Field field : getFields(this.getClass())) {
			field.setAccessible(true);
			String fieldName = getFieldName(field);
			namedFields.put(fieldName, field);
			propsOrder.add(fieldName);
		}

		// add properties that aren't in the hashmap yet
		for (Entry<String, V> entry : properties.entrySet()) {
			propsOrder.add(entry.getKey());
		}

		// Second: find property contents (either field or property key)
		LinkedHashMap<String, Object> out = new LinkedHashMap<String, Object>();
		for (String entry : propsOrder) {
			if (out.containsKey(entry)) {
				continue;
			}
			Object value = null;

			// search class fields first
			if (namedFields.containsKey(entry)) {
				try {
					value = namedFields.get(entry).get(this);
				} catch (Exception e) {
					log.error(e);
				}
			}

			// check properties
			else if (properties.containsKey(entry)) {
				value = properties.get(entry);
			}

			value = getJsonValue(value);

			if (value == null || value.equals("")) { //$NON-NLS-1$
				continue;
			}

			// add to output
			out.put(entry, value);
		}

		return out;
	}

	protected List<String> getOwnProperties() {
		return new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.composer.api.entities.JsonCollection#size()
	 */
	public int size() {
		return properties.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.composer.api.entities.JsonCollection#clear()
	 */
	public void clear() {
		// clear properties
		List<String> ownProps = getOwnProperties();

		for (String key : properties.keySet().toArray(new String[] {})) {
			if (ownProps.contains(key)) {
				Object value = properties.get(key);
				if (value instanceof JsonCollection) {
					JsonCollection entity = (JsonCollection) value;
					entity.clear();
				} else {
					remove(key);
				}
			} else {
				remove(key);
			}
		}

		// clear fields
		for (Field field : getFields(this.getClass())) {
			if (field != null && JsonCollection.class.isAssignableFrom(field.getType())) {
				try {
					field.setAccessible(true);
					JsonCollection entity = (JsonCollection) field.get(this);
					entity.clear();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}

		// clear sort order
		sortOrder.clear();
	}

	/**
	 * Returns whether the given property is present.
	 * 
	 * @param property
	 *            the property to look for
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property present</li>
	 *         <li><code>false</code> property not present</li>
	 *         </ul>
	 */
	public boolean has(String property) {
		return properties.containsKey(property);
	}

	/**
	 * Returns the property value.
	 * 
	 * @param property
	 *            the property
	 * @return the value
	 */
	public V get(String property) {
		return properties.get(property);
	}

	/**
	 * Sets a new value for the given property.
	 * 
	 * @param property
	 *            the property
	 * @param value
	 *            the new value
	 */
	public void set(String property, Object value) {
		set(property, value, true);
	}

	/**
	 * Sets a new value for the given property.
	 * 
	 * @param property
	 *            the property
	 * @param value
	 *            the new value
	 * @param notify
	 *            whether listeners should be notified about the change
	 */
	@SuppressWarnings("unchecked")
	protected void set(String property, Object value, boolean notify) {

		// remove listener on the current value if there is one yet
		uninstallListener(property);

		JsonEntity entity = null;
		// install listener to be aware of changes
		if (value instanceof JsonValue) {
			entity = getEntity((JsonValue) value);
		} else if (value instanceof JsonEntity) {
			entity = (JsonEntity) value;
		}

		if (entity != null && !listeners.containsKey(property)) {
			installListener(property, entity);
		}

		V oldValue = properties.get(property);
		properties.put(property, (V) value);
		appendSortOrder(property);

		if (notify) {
			firePropertyChange(property, oldValue, (V) value);
		}
	}

	/**
	 * Removes the given property.
	 * 
	 * @param property
	 *            the property
	 */
	public void remove(String property) {
		uninstallListener(property);
		Object oldValue = get(property);
		properties.remove(property);
		firePropertyChange(property, oldValue, null);
	}

	private void installListener(final String property, JsonEntity entity) {
		if (entity != null) {
			listeners.put(property, new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					firePropertyChange(property + "." + evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()); //$NON-NLS-1$
				}
			});

			entity.addPropertyChangeListener(listeners.get(property));
		}
	}

	private void uninstallListener(String property) {
		if (listeners.containsKey(property)) {
			if (has(property)) {
				JsonEntity entity = getEntity(get(property));
				if (entity != null) {
					entity.removePropertyChangeListener(listeners.get(property));
				}
			}
			listeners.remove(property);
		}
	}

	private JsonEntity getEntity(Object value) {
		JsonEntity entity = null;

		if (value instanceof JsonValue) {
			JsonValue val = (JsonValue) value;

			if (val.isArray()) {
				entity = val.getAsArray();
			}

			if (val.isObject()) {
				entity = val.getAsObject();
			}
		}

		return entity;
	}
}
