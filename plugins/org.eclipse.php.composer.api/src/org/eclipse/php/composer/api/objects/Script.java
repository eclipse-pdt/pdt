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
package org.eclipse.php.composer.api.objects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.entities.JsonEntity;

/**
 * Represents a script entry in the scripts entity of a composer package.
 */
public class Script extends JsonObject {

	private transient JsonArray handlers = new JsonArray();

	public Script() {
		super();
		listen();
		handlers.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange(getScript() + "." + evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}
		});
	}

	public Script(String scriptname, String handler) {
		this();
		setScript(scriptname);
		add(handler);
	}

	@Override
	protected Object buildJson() {
		try {
			Method mtd = JsonEntity.class.getDeclaredMethod("buildJson");
			return mtd.invoke(handlers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected List<String> getOwnProperties() {
		String[] props = new String[] { "handlers" };
		List<String> list = new ArrayList<String>(Arrays.asList(props));
		list.addAll(super.getOwnProperties());
		return list;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getScript() {
		return getAsString("script");
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setScript(String script) {
		set("script", script);
	}

	public void add(String handler) {
		handlers.add(handler);
	}

	@Override
	public boolean has(String handler) {
		return handlers.has(handler);
	}

	/**
	 * Clears the handlers from this script
	 */
	public void clear() {
		handlers.clear();
	}

	public void addHandlers(JsonArray handlers) {
		for (Object handler : handlers) {
			add((String) handler);
		}
	}

	/**
	 * Returns the handler and if there are more than one, returns the first
	 * one.
	 * 
	 * @return the version
	 */
	public String getFirst() {
		return (String) handlers.get(0);
	}

	public JsonArray getHandlers() {
		return handlers;
	}

	/**
	 * Removes a handler from the script
	 */
	public void remove(String handler) {
		handlers.remove(handler);
	}

	public void removeAll() {
		handlers.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.composer.api.entities.AbstractJsonObject#size()
	 */
	public int size() {
		return handlers.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Script clone() {
		Script clone = new Script();
		cloneProperties(clone);
		clone.addHandlers(handlers);
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Script) {
			Script script = (Script) obj;
			return script == this
					|| (getScript() == null ? script.getScript() == null : getScript().equals(script.getScript()))
							&& getHandlers().equals(script.getHandlers());
		}
		return false;
	}
}
