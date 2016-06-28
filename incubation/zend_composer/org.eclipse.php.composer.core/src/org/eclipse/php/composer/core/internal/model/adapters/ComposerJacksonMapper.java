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

import org.eclipse.php.composer.core.model.*;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerJacksonMapper {

	private static final ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(IProperty.class, new PropertySerializer());
		module.addDeserializer(IProperty.class, new PropertyDeserializer());
		module.addDeserializer(IArrayProperty.class,
				new ArrayPropertyDeserializer());
		module.addDeserializer(IPackages.class, new PackagesDeserializer());
		module.addSerializer(IPackages.class, new PackagesSerializer());
		module.addDeserializer(IRepositories.class, new RepositoriesDeserializer());
		module.addSerializer(IRepositories.class, new RepositoriesSerializer());
		module.addSerializer(RepositoryType.class, new RepositoryTypeSerializer());
		mapper.registerModule(module);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	public static synchronized ObjectMapper getMapper() {
		return mapper;
	}

}
