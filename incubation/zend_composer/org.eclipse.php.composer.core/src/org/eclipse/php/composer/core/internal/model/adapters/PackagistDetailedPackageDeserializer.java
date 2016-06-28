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

import org.eclipse.php.composer.core.internal.model.PackagistDetailedPackage;
import org.eclipse.php.composer.core.model.IPackage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
public class PackagistDetailedPackageDeserializer extends
		JsonDeserializer<IPackage> {

	@Override
	public IPackage deserialize(JsonParser jsonParser,
			DeserializationContext arg1) throws IOException,
			JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		TreeNode node = oc.readTree(jsonParser);

		IPackage p = new PackagistDetailedPackage();
		p.setVersions(new ArrayList<String>());
		node = node.get("package"); //$NON-NLS-1$
		if (node == null) {
			return null;
		}

		String name = ((TextNode) node.get("name")).textValue(); //$NON-NLS-1$
		p.setName(name);

		Iterator<String> fieldNames = node.get("versions").fieldNames(); //$NON-NLS-1$
		while (fieldNames.hasNext()) {
			p.getVersions().add(fieldNames.next());
		}
		return p;
	}

}
