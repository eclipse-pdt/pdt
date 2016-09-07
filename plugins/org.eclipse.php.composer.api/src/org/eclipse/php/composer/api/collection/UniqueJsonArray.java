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

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.eclipse.php.composer.api.entities.AbstractUniqueJsonArray;
import org.eclipse.php.composer.api.json.ParseException;

public class UniqueJsonArray extends AbstractUniqueJsonArray<Object> {

	public UniqueJsonArray() {
	}

	public UniqueJsonArray(Object json) {
		fromJson(json);
	}

	public UniqueJsonArray(String json) throws ParseException {
		fromJson(json);
	}

	public UniqueJsonArray(File file) throws IOException, ParseException {
		fromJson(file);
	}

	public UniqueJsonArray(Reader reader) throws IOException, ParseException {
		fromJson(reader);
	}
}
