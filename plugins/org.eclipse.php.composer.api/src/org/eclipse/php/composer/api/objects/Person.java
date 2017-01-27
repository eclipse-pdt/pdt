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
package org.eclipse.php.composer.api.objects;

import org.eclipse.php.composer.api.json.ParseException;

/**
 * Represents a person entry in a composer package that is used in authors and
 * maintainers
 * 
 * @see http://getcomposer.org/doc/04-schema.md#authors
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Person extends JsonObject implements Cloneable {

	/**
	 * Creates an empty person
	 */
	public Person() {
		super();
		listen();
	}

	public Person(Object json) {
		this();
		fromJson(json);
	}

	public Person(String json) throws ParseException {
		this();
		fromJson(json);
	}

	/**
	 * Returns a string that is passed to composer's init command
	 * 
	 * @return
	 */
	public String getInitString() {
		return String.format("%s <%s>", get("name"), get("email")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * Returns the person's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return getAsString("name"); //$NON-NLS-1$
	}

	/**
	 * Sets the person's name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		set("name", name); //$NON-NLS-1$
	}

	/**
	 * Returns the perons's email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return getAsString("email"); //$NON-NLS-1$
	}

	/**
	 * Sets the person's email
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		set("email", email); //$NON-NLS-1$
	}

	/**
	 * Returns the person's homepage
	 * 
	 * @return the homepage
	 */
	public String getHomepage() {
		return getAsString("homepage"); //$NON-NLS-1$
	}

	/**
	 * Sets the person's homepage
	 * 
	 * @param homepage
	 *            the homepage to set
	 */
	public void setHomepage(String homepage) {
		set("homepage", homepage); //$NON-NLS-1$
	}

	/**
	 * Returns the person's role
	 * 
	 * @return the role
	 */
	public String getRole() {
		return getAsString("role"); //$NON-NLS-1$
	}

	/**
	 * Sets the person's role
	 * 
	 * @param role
	 *            the role to set
	 * @return this
	 */
	public void setRole(String role) {
		set("role", role); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Person clone() {
		Person clone = new Person();
		cloneProperties(clone);
		return clone;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Person) {
			Person person = (Person) obj;

			if (person == this) {
				return true;
			}

			String p1, p2;
			String props[] = new String[] { "name", "email", "homepage", "role" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			boolean equal = true;

			for (String prop : props) {
				p1 = getAsString(prop);
				p2 = person.getAsString(prop);
				if (p1 != null && p2 != null) {
					equal = equal && p1.equals(p2);
				} else {
					equal = equal && (p1 == null && p2 == null);
				}
			}

			return equal;
		}

		return false;
	}

}
