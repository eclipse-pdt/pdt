/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.php.composer.api.VersionedPackage;
import org.eclipse.php.composer.api.entities.AbstractJsonObject;

/**
 * Represents a dependencies collection of a composer package, either require or
 * require-dev
 * 
 * @see http://getcomposer.org/doc/04-schema.md#require
 * @see http://getcomposer.org/doc/04-schema.md#require-dev
 * @author Thomas Gossmann <gos.si>
 */
public class Dependencies extends AbstractJsonObject<VersionedPackage> implements Iterable<VersionedPackage> {

	public Dependencies() {
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedHashMap) {
			for (Entry<String, Object> entry : ((Map<String, Object>) ((LinkedHashMap) obj)).entrySet()) {
				VersionedPackage dep = new VersionedPackage();
				dep.setName(entry.getKey());
				dep.setVersion((String) entry.getValue());
				add(dep);
			}
		}
	}

	@Override
	protected Object buildJson() {
		LinkedHashMap<String, Object> out = new LinkedHashMap<>();
		for (VersionedPackage dep : this) {
			out.put(dep.getName(), dep.getVersion());
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
	public void add(VersionedPackage dependency) {
		if (!has(dependency)) {
			set(dependency.getName(), dependency);
		}
	}

	public void addAll(Dependencies dependencies) {
		for (VersionedPackage pkg : dependencies) {
			add(pkg);
		}
	}

	public boolean has(VersionedPackage dependency) {
		return super.has(dependency.getName());
		// TODO: matches version, e.g. ~1 matches 1.0.1 but not 2.* and >1
		// matches 1.0.1 and 2.*
		// if (super.has(dependency.getName())) {
		// return
		// get(dependency.getName()).getVersion().equals(dependency.getVersion());
		// }
		//
		// return false;
	}

	/**
	 * Removes a dependency.
	 * 
	 * @param dependency
	 *            the dependency to remove
	 */
	public void remove(VersionedPackage dependency) {
		super.remove(dependency.getName());
	}

	public VersionedPackage[] toArray() {
		return properties.values().toArray(new VersionedPackage[] {});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<VersionedPackage> iterator() {
		return properties.values().iterator();
	}
}
