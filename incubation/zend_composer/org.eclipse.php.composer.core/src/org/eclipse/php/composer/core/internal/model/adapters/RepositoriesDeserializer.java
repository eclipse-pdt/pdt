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
import java.util.Iterator;
import java.util.Map;

import org.eclipse.php.composer.core.internal.model.Repositories;
import org.eclipse.php.composer.core.internal.model.Repository;
import org.eclipse.php.composer.core.model.IRepositories;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RepositoriesDeserializer extends JsonDeserializer<IRepositories> {

	@Override
	public IRepositories deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		Repositories repositories = new Repositories();
		ObjectCodec oc = jsonParser.getCodec();
		TreeNode node = oc.readTree(jsonParser);
		if (node instanceof ArrayNode) {
			ArrayNode array = (ArrayNode) node;

			int size = array.size();
			for (int i = 0; i < size; i++) {
				JsonNode val = array.get(i);
				Repository r = ComposerJacksonMapper.getMapper().readValue(val.traverse(), Repository.class);
				repositories.addRepository(r, false);
			}
			repositories.setArray(true);
		} else if (node instanceof ObjectNode) {
			ObjectNode objectNode = (ObjectNode) node;

			Iterator<Map.Entry<String, JsonNode>> iterator = objectNode.fields();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonNode> val = iterator.next();

				Repository r = ComposerJacksonMapper.getMapper().readValue(val.getValue().traverse(), Repository.class);
				r.setKey(val.getKey());
				repositories.addRepository(r, false);
			}
			repositories.setArray(false);
		}
		return repositories;
	}

}