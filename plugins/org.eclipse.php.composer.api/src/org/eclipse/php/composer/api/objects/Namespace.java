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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.composer.api.collection.UniqueJsonArray;
import org.eclipse.php.composer.api.entities.JsonEntity;

/**
 * Represents a namespace entry in the psr0 entity of a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#psr-0
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Namespace extends JsonObject {

	private transient UniqueJsonArray paths = new UniqueJsonArray();
	
	public Namespace() {
		super();
		listen();
		paths.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChange(getNamespace() +"."+ evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
			}
		});
	}
	
	public Namespace(String namespacename, String path) {
		this();
		setNamespace(namespacename);
		add(path);
	}
	
	@Override
	protected Object buildJson() {
		try {
			Method mtd = JsonEntity.class.getDeclaredMethod("buildJson");
			return mtd.invoke(paths);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected List<String> getOwnProperties() {
		String[] props = new String[]{"paths"};
		List<String> list = new ArrayList<String>(Arrays.asList(props));
		list.addAll(super.getOwnProperties());
		return list;
	}
	
	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getNamespace() {
		return getAsString("namespace");
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name the name to set
	 */
	public void setNamespace(String namespace) {
		set("namespace", namespace);
	}
	
	public void add(String path) {
		paths.add(path);
	}
	
	@Override
	public boolean has(String path) {
		return paths.has(path);
	}
	
	/**
	 * Clears the paths from this namespace
	 */
	public void clear() {
		paths.clear();
	}
	
	public void addPaths(UniqueJsonArray paths) {
		for (Object path : paths) {
			if (!has((String)path)) {
				add((String)path);
			}
		}
	}
	
	/**
	 * Returns the path and if there are more than one, returns the first one.
	 * 
	 * @return the version
	 */
	public String getFirst() {
		return (String) paths.get(0);
	}

	public UniqueJsonArray getPaths() {
		return paths;
	}
	
	/**
	 * Removes a path from the namespace
	 */
	public void remove(String path) {
		paths.remove(path);
	}
	
	public void removeAll() {
		paths.clear();
	}
	

	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.composer.api.entities.AbstractJsonObject#size()
	 */
	public int size() {
		return paths.size();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Namespace clone() {
		Namespace clone = new Namespace();
		cloneProperties(clone);
		clone.addPaths(paths);
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Namespace) {
			Namespace namespace = (Namespace) obj;
			return namespace == this 
					|| (getNamespace() == null 
							? namespace.getNamespace() == null
							: getNamespace().equals(namespace.getNamespace()))
						&& getPaths().equals(namespace.getPaths());
		}
		return false;
	}
}
