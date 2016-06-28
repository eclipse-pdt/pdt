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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.model.IPackages;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PackagesSerializer extends JsonSerializer<IPackages> {

	@Override
	public void serialize(IPackages value, JsonGenerator jsonGen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		List<IPackage> packages = value.getPackages();
		Map<String , String> values = new LinkedHashMap<String, String>();
		for (IPackage p : packages) {
			values.put(p.getName(), p.getVersionConstraint());
		}
		jsonGen.writeObject(values); 
	}
}