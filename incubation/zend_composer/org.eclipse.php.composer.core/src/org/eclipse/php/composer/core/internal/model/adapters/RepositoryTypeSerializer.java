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

import org.eclipse.php.composer.core.model.RepositoryType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class RepositoryTypeSerializer extends JsonSerializer<RepositoryType> {

	@Override
	public void serialize(RepositoryType value, JsonGenerator jsonGen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jsonGen.writeString(value.getName());
	}
}