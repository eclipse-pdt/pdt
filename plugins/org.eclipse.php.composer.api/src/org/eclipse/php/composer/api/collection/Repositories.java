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
package org.eclipse.php.composer.api.collection;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.php.composer.api.entities.AbstractJsonArray;
import org.eclipse.php.composer.api.repositories.Repository;
import org.eclipse.php.composer.api.repositories.RepositoryFactory;

/**
 * Represents a repositories collection of a composer package
 * 
 * @see http://getcomposer.org/doc/04-schema.md#repositories
 * @author Thomas Gossmann <gos.si>
 */
public class Repositories extends AbstractJsonArray<Repository> {

	public Repositories() {
	}

	@SuppressWarnings("rawtypes")
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedList) {
			for (Object repo : (LinkedList) obj) {
				if (repo instanceof LinkedHashMap && ((LinkedHashMap) repo).containsKey("type")) { //$NON-NLS-1$
					String type = (String) ((LinkedHashMap) repo).get("type"); //$NON-NLS-1$
					Repository r = RepositoryFactory.create(type);
					r.fromJson(repo);
					add(r);
				}
			}
		}
	}
}
