/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
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
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.composer.core.model.IDist;
import org.eclipse.php.composer.core.model.IRepositoryPackage;
import org.eclipse.php.composer.core.model.ModelFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public class ComposerRepositoryPackageDeserializer extends
		JsonDeserializer<List<IRepositoryPackage>> {

	@Override
	public List<IRepositoryPackage> deserialize(JsonParser jsonParser,
			DeserializationContext context) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		TreeNode node = oc.readTree(jsonParser);

		List<IRepositoryPackage> packages = new ArrayList<IRepositoryPackage>();

		Iterator<String> fieldIterator = node.fieldNames();
		while (fieldIterator.hasNext()) {
			String name = fieldIterator.next();
			TreeNode packageNode = node.get(name);
			Iterator<String> versionIterator = packageNode.fieldNames();
			while (versionIterator.hasNext()) {
				String version = versionIterator.next();
				IRepositoryPackage repositoryPackage = ModelFactory
						.createRepositoryPackage();
				repositoryPackage.setName(name);
				repositoryPackage.setVersion(version);

				TreeNode distNode = packageNode.get(version).get("dist"); //$NON-NLS-1$ 
				if (distNode != null) {
					IDist dist = ComposerJacksonMapper.getMapper().readValue(
							distNode.traverse(), IDist.class);
					repositoryPackage.setDist(dist);
				}
				packages.add(repositoryPackage);
			}
		}
		return packages;
	}

}
