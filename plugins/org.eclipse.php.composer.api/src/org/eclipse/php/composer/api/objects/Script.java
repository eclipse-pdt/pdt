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
package org.eclipse.php.composer.api.objects;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.entities.JsonEntity;
import org.eclipse.php.composer.api.entities.JsonValue;

/**
 * Represents a script entry in the scripts entity of a composer package.
 */
public class Script extends JsonObject {

	/**
	 * Used as a wrapper for string values, since Script objects (i.e. events)
	 * can hold duplicate string values (i.e. handler values). The HandlerValue
	 * wrapper class holds a handler value but also its index (i.e. position)
	 * when stored in the "handlers" array of a Script object. The index
	 * information could be seen as redundant, but it's used (and necessary) for
	 * the TreeViewer in the ScriptsSection class to compare handler values
	 * retrieved by ScriptsController instances because TreeViewers don't
	 * support duplicate (string) values. To make it simple, the HandlerValue
	 * class is used to handle only unique values (per Script object) in
	 * TreeViewers.
	 * 
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=510095
	 */
	public static class HandlerValue extends JsonValue {
		private transient int index = 0;

		public HandlerValue(String value) {
			super(value);
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof HandlerValue)) {
				return false;
			}
			HandlerValue other = (HandlerValue) obj;
			if (index != other.index) {
				return false;
			}
			String s1 = getAsString();
			String s2 = other.getAsString();
			if (s1 != null) {
				return s1.equals(s2);
			}
			return s1 == s2;
		}

		public HandlerValue clone() {
			HandlerValue clone = new HandlerValue(getAsString());
			clone.setIndex(index);
			return clone;
		}
	}

	private transient JsonArray handlers = new JsonArray();

	public Script() {
		super();
		listen();
		handlers.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange(getScript() + "." + evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()); //$NON-NLS-1$
			}
		});
	}

	public Script(String scriptname, HandlerValue handler) {
		this();
		setScript(scriptname);
		add(handler);
	}

	@Override
	protected Object buildJson() {
		try {
			Method mtd = JsonEntity.class.getDeclaredMethod("buildJson"); //$NON-NLS-1$
			return mtd.invoke(handlers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected List<String> getOwnProperties() {
		String[] props = new String[] { "handlers" }; //$NON-NLS-1$
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
		return getAsString("script"); //$NON-NLS-1$
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setScript(String script) {
		set("script", script); //$NON-NLS-1$
	}

	public HandlerValue get(int index) throws IndexOutOfBoundsException {
		return (HandlerValue) handlers.get(index);
	}

	public void add(HandlerValue handler) {
		handlers.add(handler);
		handler.setIndex(handlers.size() - 1);
	}

	public void add(int index, HandlerValue handler) throws IndexOutOfBoundsException {
		handlers.add(index, handler);
		handler.setIndex(index);
	}

	public void remove(int index) throws IndexOutOfBoundsException {
		handlers.remove(index);
		updateIndexes();
	}

	@Override
	public boolean has(String handler) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Clears the handlers from this script
	 */
	public void clear() {
		handlers.clear();
	}

	public void addHandlers(JsonArray handlers) {
		if (handlers == this.handlers) {
			return;
		}
		for (Object handler : handlers) {
			add((HandlerValue) handler);
		}
	}

	public void addHandlers(Script script) {
		if (script == this) {
			return;
		}
		addHandlers(script.getHandlers());
	}

	private void updateIndexes() {
		int index = 0;
		for (Object handler : handlers) {
			((HandlerValue) handler).setIndex(index++);
		}
	}

	public Object getHandlersJsonValue() {
		return getJsonValue(handlers);
	}

	/**
	 * Returns the handler and if there are more than one, returns the first
	 * one.
	 * 
	 * @return the version
	 */
	public HandlerValue getFirst() {
		return (HandlerValue) handlers.get(0);
	}

	private JsonArray getHandlers() {
		return handlers;
	}

	/**
	 * Removes a handler from the script
	 */
	public void remove(HandlerValue handler) {
		handlers.remove(handler);
		updateIndexes();
	}

	public void removeAll() {
		handlers.clear();
	}

	public HandlerValue[] toArray() {
		return handlers.toArray(new HandlerValue[0]);
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
		clone.clear();
		for (Object value : handlers) {
			// Handlers must also be cloned to not be shared among Script
			// objects. See ScriptsSection#handleEdit() and condition
			// "if (cpscript.equals(dialog.getScript())) {...}".
			clone.add(((HandlerValue) value).clone());
		}
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
