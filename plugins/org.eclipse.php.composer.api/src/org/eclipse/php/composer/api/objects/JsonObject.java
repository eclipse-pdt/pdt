/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.objects;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map.Entry;

import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.entities.AbstractJsonObject;
import org.eclipse.php.composer.api.entities.JsonValue;
import org.eclipse.php.composer.api.json.ParseException;

public class JsonObject extends AbstractJsonObject<JsonValue> {

	public JsonObject() {
		super();
		listen();
	}

	public JsonObject(Object json) {
		this();
		fromJson(json);
	}

	public JsonObject(String json) throws ParseException {
		this();
		fromJson(json);
	}

	public JsonObject(File file) throws IOException, ParseException {
		this();
		fromJson(file);
	}

	public JsonObject(Reader reader) throws IOException, ParseException {
		this();
		fromJson(reader);
	}

	@Override
	protected void initialize() {
		super.initialize();
	}

	/**
	 * Returns whether the property is instance of the given type.
	 * 
	 * @param property
	 *            the property
	 * @param type
	 *            the type
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is instance of type</li>
	 *         <li><code>false</code> property is not an instance of type or it
	 *         doesn't exist.</li>
	 *         </ul>
	 */
	public boolean is(String property, Type type) {
		if (super.has(property)) {
			return get(property).is(type);
		}
		return false;
	}

	/**
	 * Returns whether the property is instance of an array.
	 * 
	 * @see #getAsArray
	 * @param property
	 *            the property
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is an array</li>
	 *         <li><code>false</code> property is not an array or it doesn't
	 *         exist.</li>
	 *         </ul>
	 */
	public boolean isArray(String property) {
		if (super.has(property)) {
			return get(property).isArray();
		}
		return false;
	}

	/**
	 * Returns whether the property is instance of an entity.
	 * 
	 * @see #getAsObject
	 * @param property
	 *            the property
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is an entity</li>
	 *         <li><code>false</code> property is not an entity or it doesn't
	 *         exist.</li>
	 *         </ul>
	 */
	public boolean isObject(String property) {
		if (super.has(property)) {
			return get(property).isObject();
		}
		return false;
	}

	/**
	 * Returns the property raw value or <code>null</code> if it has not been set.
	 * 
	 * @param property
	 *            the property
	 * @return the value
	 */
	public Object getAsRaw(String property) {
		if (super.has(property)) {
			return get(property).getAsRaw();
		}
		return null;
	}

	/**
	 * Returns the property value as array.
	 * 
	 * @param property
	 *            the property
	 * @return the value
	 */
	public JsonArray getAsArray(String property) {
		if (!super.has(property)) {
			super.set(property, new JsonValue(new JsonArray()), false);
		}
		return get(property).getAsArray();
	}

	/**
	 * Returns the property value as string or <code>null</code> if it has not been
	 * set.
	 * 
	 * @param property
	 *            the property
	 * @return the value as string
	 */
	public String getAsString(String property) {
		if (super.has(property)) {
			return get(property).getAsString();
		}
		return null;
	}

	/**
	 * Returns the property value as boolean or <code>null</code> if it has not been
	 * set.
	 * 
	 * @param property
	 *            the property
	 * @return the value as boolean
	 */
	public Boolean getAsBoolean(String property) {
		if (super.has(property)) {
			return get(property).getAsBoolean();
		}
		return null;
	}

	/**
	 * Returns the property value as integer or <code>null</code> if it has not been
	 * set.
	 * 
	 * @param property
	 *            the property
	 * @return the value as integer
	 */
	public Integer getAsInteger(String property) {
		if (super.has(property)) {
			return get(property).getAsInteger();
		}
		return null;
	}

	/**
	 * Returns the property value as double or <code>null</code> if it has not been
	 * set.
	 * 
	 * @param property
	 *            the property
	 * @return the value as double
	 */
	public Float getAsFloat(String property) {
		if (super.has(property)) {
			return get(property).getAsFloat();
		}
		return null;
	}

	/**
	 * Returns the property value as object.
	 * 
	 * @param property
	 *            the property
	 * @return the value as entity
	 */
	public JsonObject getAsObject(String property) {
		if (!super.has(property)) {
			super.set(property, new JsonValue(new JsonObject()), false);
		}
		return get(property).getAsObject();
	}

	/**
	 * Sets a new value for the given property.
	 * 
	 * @param property
	 *            the property
	 * @param value
	 *            the new value
	 */
	@Override
	public void set(String property, Object value) {
		super.set(property, new JsonValue(value));
	}

	// /**
	// * Sets a new value for the given property.
	// *
	// * @param property
	// * the property
	// * @param value
	// * the new value
	// */
	// public void set(String property, JsonValue value) {
	// if (silentProps.contains(property)) {
	// properties.put(property, value);
	// } else {
	// super.set(property, value);
	// }
	// }

	protected void cloneProperties(JsonObject clone) {
		for (Entry<String, JsonValue> entry : properties.entrySet()) {
			clone.set(entry.getKey(), entry.getValue().getAsRaw());
		}
	}
}
