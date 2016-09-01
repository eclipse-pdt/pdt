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

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.php.composer.api.entities.AbstractJsonArray;
import org.eclipse.php.composer.api.objects.Person;


/**
 * Represents a person collection for authors and maintainers
 * 
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Persons extends AbstractJsonArray<Person> implements Iterable<Person> {

	public Persons() {
	}
	
	@SuppressWarnings("rawtypes")
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedList) {
			for (Object pObj : (LinkedList)obj) {
				if (pObj instanceof LinkedHashMap) {
					LinkedHashMap p = (LinkedHashMap)pObj;
					Person person = new Person(p);
					add(person);
				}
			}
		}
	}
	
	@Override
	public boolean has(Person value) {
		if (super.has(value)) {
			return true;
		}
		
		for (Person p : this) {
			if (p.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
}
