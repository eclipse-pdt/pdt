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
package org.eclipse.php.composer.api.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.php.composer.api.collection.Versions;
import org.eclipse.php.composer.api.objects.JsonObject;

/**
 * Represents a composer repository
 * 
 * @author Thomas Gossmann <gos.si>
 *
 */
public class ComposerRepository extends Repository implements Cloneable {

	private Map<String, Versions> packages;
	private JsonObject options = new JsonObject();
	
	public ComposerRepository() {
		super("composer");
		listen();
	}
	
	@Override
	protected List<String> getOwnProperties() {
		String[] props = new String[]{"options"};
		List<String> list = new ArrayList<String>(Arrays.asList(props));
		list.addAll(super.getOwnProperties());
		return list;
	}

	public Versions getVersions(String packageName) {
		if (packages.containsKey(packageName)) {
			return packages.get(packageName);
		}
		return null;
	}
	
	/**
	 * Returns the options entity
	 * 
	 * @return the options
	 */
	public JsonObject getOptions() {
		return options;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ComposerRepository clone() {
		ComposerRepository clone = new ComposerRepository();
		cloneProperties(clone);
		return clone;
	}
}
