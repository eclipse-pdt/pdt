/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.converter;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.databinding.conversion.Converter;

import org.eclipse.php.composer.api.collection.JsonArray;

public class Keywords2StringConverter extends Converter {

	public Keywords2StringConverter() {
		super(JsonArray.class, String.class);
	}

	@Override
	public String convert(Object fromObject) {
		if (fromObject == null) {
			return "";
		}
		JsonArray keywords = (JsonArray)fromObject;
		return StringUtils.join((String[])keywords.toArray(new String[]{}), ", ");
	}

}
