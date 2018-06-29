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
package org.eclipse.php.composer.api.json;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

public class JsonParser {

	public JsonParser() {

	}

	public Object parse(String json) throws ParseException {
		com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
		try {
			return buildTree(jsonParser.parse(json));
		} catch (JsonSyntaxException e) {
			throw buildException(e);
		}
	}

	private Object buildTree(JsonElement entity) {

		if (entity.isJsonPrimitive()) {
			JsonPrimitive p = entity.getAsJsonPrimitive();
			if (p.isBoolean()) {
				return p.getAsBoolean();
			}
			if (p.isNumber()) {
				return p.getAsLong();
			}
			return p.getAsString();
		} else if (entity.isJsonNull()) {
			return null;
		} else if (entity.isJsonArray()) {
			LinkedList<Object> arr = new LinkedList<>();
			for (JsonElement el : entity.getAsJsonArray()) {
				arr.add(buildTree(el));
			}
			return arr;
		} else if (entity.isJsonObject()) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			for (Entry<String, JsonElement> el : entity.getAsJsonObject().entrySet()) {
				map.put(el.getKey(), buildTree(el.getValue()));
			}
			return map;
		}

		return null;
	}

	public Object parse(Reader reader) throws ParseException, IOException {
		com.google.gson.JsonParser jsonParser = new com.google.gson.JsonParser();
		try {
			return buildTree(jsonParser.parse(reader));
		} catch (JsonSyntaxException e) {
			throw buildException(e);
		}
	}

	private ParseException buildException(JsonSyntaxException e) {
		ParseException pe = new ParseException(
				e.getCause() instanceof MalformedJsonException ? e.getCause().getMessage() : e.getMessage());
		if (e.getCause() instanceof MalformedJsonException) {
			pe.setErrorType(ParseException.ERROR_UNEXPECTED_MALFORMED);
		} else if (e.getCause() instanceof IOException) {
			pe.setErrorType(ParseException.ERROR_UNEXPECTED_IO);
		} else if (e.getCause() != null) {
			pe.setErrorType(ParseException.ERROR_UNEXPECTED_EXCEPTION);
		}

		return pe;
	}

}
