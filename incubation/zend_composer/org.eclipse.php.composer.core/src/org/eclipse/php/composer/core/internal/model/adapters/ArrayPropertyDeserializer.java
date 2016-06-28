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
package org.eclipse.php.composer.core.internal.model.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.composer.core.internal.model.ArrayProperty;
import org.eclipse.php.composer.core.model.IArrayProperty;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ArrayPropertyDeserializer extends JsonDeserializer<IArrayProperty> {

	@Override
	public IArrayProperty deserialize(JsonParser jsonParser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		IArrayProperty p = new ArrayProperty();
		List<String> values = new ArrayList<String>();
		if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
			while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
				values.add(jsonParser.getText());
			}
		}
		p.setValue(values.toArray(new String[values.size()]), false);
		return p;
	}

}