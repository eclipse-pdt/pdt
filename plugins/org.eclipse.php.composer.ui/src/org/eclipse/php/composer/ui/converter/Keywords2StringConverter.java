/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.converter;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.php.composer.api.collection.JsonArray;

public class Keywords2StringConverter extends Converter {

	public Keywords2StringConverter() {
		super(JsonArray.class, String.class);
	}

	@Override
	public String convert(Object fromObject) {
		if (fromObject == null) {
			return ""; //$NON-NLS-1$
		}
		JsonArray keywords = (JsonArray) fromObject;
		return StringUtils.join(keywords.toArray(new String[] {}), ", "); //$NON-NLS-1$
	}

}
