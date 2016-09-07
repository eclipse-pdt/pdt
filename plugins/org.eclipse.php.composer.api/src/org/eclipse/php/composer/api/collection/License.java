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
package org.eclipse.php.composer.api.collection;

import java.util.LinkedList;

import org.eclipse.php.composer.api.entities.AbstractJsonArray;

/**
 * Represents the license property of a composer package
 * 
 * @see http://getcomposer.org/doc/04-schema.md#license
 * @author Thomas Gossmann <gos.si>
 */
public class License extends AbstractJsonArray<String> {

	public License() {
	}

	@SuppressWarnings("rawtypes")
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedList) {
			for (Object license : (LinkedList) obj) {
				add((String) license);
			}
		} else {
			add((String) obj);
		}

	}

	@Override
	protected Object buildJson() {
		if (size() == 1) {
			return (String) get(0);
		}

		return super.buildJson();
	}

	/**
	 * Adds a license.
	 * 
	 * @param license
	 * @return this
	 */
	public void add(String license) {
		if (!has(license)) {
			super.add(license);
		}
	}
}
