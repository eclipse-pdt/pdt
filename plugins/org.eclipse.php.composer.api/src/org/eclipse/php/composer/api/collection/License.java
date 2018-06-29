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

	@Override
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
			return get(0);
		}

		return super.buildJson();
	}

	/**
	 * Adds a license.
	 * 
	 * @param license
	 * @return this
	 */
	@Override
	public void add(String license) {
		if (!has(license)) {
			super.add(license);
		}
	}
}
