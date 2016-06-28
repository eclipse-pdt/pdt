/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Helper method for managing extra attributes of Composer repository.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ExtraAttributes {

	private static final String NEW_LINE = "\n"; //$NON-NLS-1$
	private static final String BRACKET_RIGHT = "}"; //$NON-NLS-1$
	private static final String BRACKET_LEFT = "{"; //$NON-NLS-1$

	protected Map<String, Object> additionalAttributes = new HashMap<String, Object>();

	@JsonAnyGetter
	public Map<String, Object> any() {
		return additionalAttributes;
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		additionalAttributes.put(name, value);
	}

	public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}

	public static ExtraAttributes toModel(String extra) throws IOException {
		return deserialize(BRACKET_LEFT + extra + BRACKET_RIGHT);
	}

	public String toList() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String json = mapper.writeValueAsString(this);
		if (json != null) {
			StringBuilder result = new StringBuilder();
			String[] lines = json.split(NEW_LINE);
			for (String line : lines) {
				line = line.trim();
				if (BRACKET_LEFT.equals(line) || BRACKET_RIGHT.equals(line)
						|| (BRACKET_LEFT + ' ' + BRACKET_RIGHT).equals(line)) {
					continue;
				}
				if (result.length() != 0) {
					result.append(NEW_LINE);
				}
				result.append(line);
			}
			return result.toString();
		}
		return ""; //$NON-NLS-1$
	}

	private static ExtraAttributes deserialize(String input) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(input, ExtraAttributes.class);
	}

}