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

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.eclipse.php.composer.api.entities.AbstractJsonArray;
import org.eclipse.php.composer.api.json.ParseException;

public class JsonArray extends AbstractJsonArray<Object> {

	public JsonArray() {
	}

	public JsonArray(Object json) {
		fromJson(json);
	}

	public JsonArray(String json) throws ParseException {
		fromJson(json);
	}

	public JsonArray(File file) throws IOException, ParseException {
		fromJson(file);
	}

	public JsonArray(Reader reader) throws IOException, ParseException {
		fromJson(reader);
	}
}
