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

import org.eclipse.php.composer.core.internal.model.Package;
import org.eclipse.php.composer.core.internal.model.Packages;
import org.eclipse.php.composer.core.model.IPackage;
import org.eclipse.php.composer.core.model.IPackages;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class PackagesDeserializer extends JsonDeserializer<IPackages> {

	@Override
	public IPackages deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		IPackages packages = new Packages();
		ObjectCodec oc = jsonParser.getCodec();
		TreeNode node = oc.readTree(jsonParser);
		Iterator<String> fields = node.fieldNames();
		while (fields.hasNext()) {
			String name = fields.next();
			TreeNode version = node.get(name);
			IPackage p = new Package(name);
			String value = version.toString();
			p.setVersionConstraint(value.substring(1, value.length() - 1));
			packages.addPackage(p, false);
		}
		return packages;
	}

}