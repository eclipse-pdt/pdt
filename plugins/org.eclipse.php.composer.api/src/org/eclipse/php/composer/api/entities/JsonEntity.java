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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.php.composer.api.annotation.Name;
import org.eclipse.php.composer.api.json.JsonFormatter;
import org.eclipse.php.composer.api.json.JsonParser;
import org.eclipse.php.composer.api.json.ParseException;

public abstract class JsonEntity extends Entity {

	private transient Set<String> listening = new HashSet<String>();
	@SuppressWarnings("rawtypes")
	private transient Map<Class, Map<String, Field>> fieldNameCache = new HashMap<Class, Map<String, Field>>();

	private transient Log log = LogFactory.getLog(JsonEntity.class);

	protected transient LinkedList<String> sortOrder = new LinkedList<String>();

	public JsonEntity() {
		listen();
		initialize();
	}

	// can be filled by subclasses
	protected void initialize() {
	}

	protected void listen() {
		try {
			for (Field field : getFields(this.getClass())) {
				if (JsonCollection.class.isAssignableFrom(field.getType())) {
					final String prop = getFieldName(field);
					final JsonEntity sender = this;

					if (listening.contains(prop)) {
						continue;
					}

					field.setAccessible(true);
					JsonEntity obj = (JsonEntity) field.get(this);

					if (obj != null) {
						obj.addPropertyChangeListener(new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent e) {
								firePropertyChange(prop + "." + e.getPropertyName(), e.getOldValue(), e.getNewValue()); //$NON-NLS-1$

								// append to sort order - use reflection
								if (sender instanceof AbstractJsonObject) {
									try {
										Method mtd = JsonEntity.class.getDeclaredMethod("appendSortOrder", //$NON-NLS-1$
												String.class);
										mtd.setAccessible(true);
										mtd.invoke(sender, prop);
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							}
						});

						listening.add(prop);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@SuppressWarnings("rawtypes")
	protected ArrayList<Field> getFields(Class entity) {
		ArrayList<Field> fields = new ArrayList<Field>();
		Class superClass = entity;

		while (superClass != null) {
			for (Field field : superClass.getDeclaredFields()) {
				if (!((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)) {
					fields.add(field);
				}
			}
			superClass = superClass.getSuperclass();
		}

		return fields;
	}

	protected String getFieldName(Field field) {
		String name = field.getName();
		for (Annotation anno : field.getAnnotations()) {
			if (anno.annotationType() == Name.class) {
				name = ((Name) anno).value();
			}
		}
		return name;
	}

	@SuppressWarnings("rawtypes")
	protected List<String> getFieldNames(Class entity) {
		ArrayList<String> names = new ArrayList<String>();

		for (Field field : getFields(entity)) {
			names.add(getFieldName(field));
		}

		return names;
	}

	@SuppressWarnings("rawtypes")
	protected Field getFieldByName(Class entity, String fieldName) {

		// create cache
		if (!fieldNameCache.containsKey(entity)) {
			Map<String, Field> mapping = new HashMap<String, Field>();

			for (Field field : getFields(entity)) {
				mapping.put(getFieldName(field), field);
			}
			fieldNameCache.put(entity, mapping);
		}

		Map<String, Field> mapping = fieldNameCache.get(entity);

		if (mapping.containsKey(fieldName)) {
			return mapping.get(fieldName);
		}

		return null;
	}

	protected void appendSortOrder(String name) {
		if (!sortOrder.contains(name)) {
			sortOrder.add(name);
		}
	}

	protected Object getJsonValue(Object value) {
		if (value instanceof JsonValue) {
			return ((JsonValue) value).toJsonValue();
		} else if (value instanceof JsonCollection) {
			JsonCollection coll = (JsonCollection) value;
			if (coll.size() > 0) {
				// call buildJson - use reflection
				try {
					Method mtd = JsonEntity.class.getDeclaredMethod("buildJson"); //$NON-NLS-1$
					return mtd.invoke(coll);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			return value;
		}

		return null;
	}

	protected abstract void doParse(Object obj);

	private void parse(String json) throws ParseException {
		JsonParser parser = new JsonParser();
		doParse(parser.parse(json));
	}

	private void parse(Reader reader) throws IOException, ParseException {
		JsonParser parser = new JsonParser();
		doParse(parser.parse(reader));
	}

	public void fromJson(Object json) {
		doParse(json);
	}

	public void fromJson(String json) throws ParseException {
		parse(json);
	}

	public void fromJson(File file) throws IOException, ParseException {
		if (file.length() > 0) {
			fromJson(new FileReader(file));
		}
	}

	public void fromJson(Reader reader) throws IOException, ParseException {
		parse(reader);
	}

	protected abstract Object buildJson();

	public String toJson() {
		return JsonFormatter.format(buildJson());
	}
}
