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

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.objects.JsonObject;

public class JsonValue {

	protected Object value;

	public JsonValue(Object value) {
		this.value = value;
	}

	public Object toJsonValue() {
		if (isArray()) {
			if (getAsArray().size() == 0) {
				return null;
			}
			return buildJson(getAsArray());
		} else if (isObject()) {
			if (getAsObject().size() == 0) {
				return null;
			}
			return buildJson(getAsObject());
		} else if (isNumber()) {
			return getAsNumber();
		} else if (isBoolean()) {
			return getAsBoolean();
		} else {
			return getAsString();
		}
	}

	private Object buildJson(JsonCollection jsonObject) {
		try {
			Method mtd = JsonEntity.class.getDeclaredMethod("buildJson"); //$NON-NLS-1$
			return mtd.invoke(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns whether the value is instance of the given type.
	 * 
	 * @param type
	 *            the type
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is instance of type</li>
	 *         <li><code>false</code> property is not an instance of type</li>
	 *         </ul>
	 */
	public boolean is(Type type) {
		return value.getClass().isAssignableFrom((Class<?>) type);
	}

	/**
	 * Returns whether the value is instance of an array.
	 * 
	 * @see #getAsArray
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is an array</li>
	 *         <li><code>false</code> property is not an array</li>
	 *         </ul>
	 */
	public boolean isArray() {
		return value instanceof JsonArray;
	}

	/**
	 * Returns whether the property is instance of an entity.
	 * 
	 * @see #getAsEntity
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is an entity</li>
	 *         <li><code>false</code> property is not an entity</li>
	 *         </ul>
	 */
	public boolean isObject() {
		return value instanceof JsonObject;
	}

	/**
	 * Returns whether the property is a boolean.
	 * 
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is a boolean</li>
	 *         <li><code>false</code> property is not a boolean</li>
	 *         </ul>
	 */
	public boolean isBoolean() {
		return value instanceof Boolean;
	}

	/**
	 * Returns whether the property is a number.
	 * 
	 * @return
	 *         <ul>
	 *         <li><code>true</code> property is a number</li>
	 *         <li><code>false</code> property is not a number</li>
	 *         </ul>
	 */
	public boolean isNumber() {
		return value instanceof Number;
	}

	/**
	 * Returns the value.
	 * 
	 * @return the value
	 */
	public Object getAsRaw() {
		return value;
	}

	/**
	 * Returns the value as array.
	 * 
	 * @return the value
	 */
	public JsonArray getAsArray() {
		if (!(value instanceof JsonArray)) {
			JsonArray val = new JsonArray();
			val.add(value);
			value = val;
		}
		return (JsonArray) value;
	}

	/**
	 * Returns the value as string.
	 * 
	 * @return the value as string
	 */
	public String getAsString() {
		if (value instanceof String) {
			return (String) value;
		}
		return value != null ? value.toString() : null;
	}

	/**
	 * Returns the value as boolean.
	 * 
	 * @return the value as boolean
	 */
	public Boolean getAsBoolean() {
		if (value instanceof String) {
			return Boolean.parseBoolean((String) value);
		}
		return (Boolean) value;
	}

	/**
	 * Returns the value as integer.
	 * 
	 * @return the value as integer
	 */
	public Integer getAsInteger() {
		if (value instanceof String) {
			return Integer.valueOf((String) value);
		} else if (value instanceof Long) {
			return ((Long) value).intValue();
		}
		return (Integer) value;
	}

	/**
	 * Returns the value as float.
	 * 
	 * @return the value as float
	 */
	public Float getAsFloat() {
		return Float.valueOf((String) value);
	}

	/**
	 * Returns the value as number.
	 * 
	 * @return the value as number
	 */
	public Number getAsNumber() {
		return (Number) value;
	}

	/**
	 * Returns the value as entity.
	 * 
	 * @return the value as entity
	 */
	public JsonObject getAsObject() {
		return (JsonObject) value;
	}
}
