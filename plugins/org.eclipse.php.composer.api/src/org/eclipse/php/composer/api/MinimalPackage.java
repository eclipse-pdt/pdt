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
package org.eclipse.php.composer.api;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.objects.JsonObject;

/**
 * A package that can be read from a file or string and also dump to the latter
 * one.
 * 
 * @author Thomas Gossmann <gos.si>
 * 
 */
public class MinimalPackage extends JsonObject {

	public MinimalPackage() {
		super();
		listen();
	}

	public MinimalPackage(Object json) {
		this();
		fromJson(json);
	}

	public MinimalPackage(String json) throws ParseException {
		this();
		fromJson(json);
	}

	public MinimalPackage(File file) throws IOException, ParseException {
		this();
		fromJson(file);
	}

	public MinimalPackage(Reader reader) throws IOException, ParseException {
		this();
		fromJson(reader);
	}

	/**
	 * Returns the <code>name</code> property.
	 * 
	 * @return the <code>name</code> value
	 */
	public String getName() {
		return getAsString("name"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>name</code> property.
	 * 
	 * @param name
	 *            the new <code>name</code> value
	 */
	public void setName(String name) {
		set("name", name); //$NON-NLS-1$
	}

	/**
	 * Returns the <code>description</code> property.
	 * 
	 * @return
	 */
	public String getDescription() {
		return getAsString("description"); //$NON-NLS-1$
	}

	/**
	 * Sets the <code>description</code> property.
	 * 
	 * @param description
	 *            the new <code>description</code> value
	 */
	public void setDescription(String description) {
		set("description", description); //$NON-NLS-1$
	}

}
