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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.php.composer.core.model.IRepositories;
import org.eclipse.php.composer.core.model.IRepository;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class RepositoriesSerializer extends JsonSerializer<IRepositories> {

	@Override
	public void serialize(IRepositories value, JsonGenerator jsonGen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		List<IRepository> repos = value.getRepositories();
		if (value.isArray()) {
			jsonGen.writeObject(repos.toArray(new IRepository[repos.size()]));
		} else {
			Map<String, IRepository> map = new HashMap<String, IRepository>();

			for (int i = 0; i < repos.size(); i++) {
				IRepository repository = repos.get(i);
				if (repository.getKey() != null) {
					map.put(repository.getKey(), repository);
				} else {
					map.put(String.valueOf(i), repository);
				}
			}
			jsonGen.writeObject(map);
		}
	}
}