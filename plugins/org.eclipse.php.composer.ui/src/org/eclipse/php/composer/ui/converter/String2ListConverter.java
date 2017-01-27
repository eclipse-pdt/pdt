/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class String2ListConverter extends ComposerConverter {

	public String2ListConverter(Object fromType, Object toType) {
		super(fromType, toType);
	}

	@Override
	public Object convert(Object fromObject) {
		List<String> oldValues = Arrays.asList(start());
		List<String> newValues = new ArrayList<String>();

		// add chunks
		String[] chunks = ((String) fromObject).split(","); //$NON-NLS-1$
		for (String chunk : chunks) {
			chunk = chunk.trim();

			if (!chunk.isEmpty()) {
				newValues.add(chunk);

				if (!has(chunk)) {
					add(chunk);
				}
			}
		}

		// remove deleted entries
		for (String item : oldValues) {
			if (!newValues.contains(item)) {
				remove(item);
			}
		}

		return finish();
	}

	protected abstract String[] start();

	protected abstract Object finish();

	protected abstract boolean has(String value);

	protected abstract void add(String value);

	protected abstract void remove(String value);

}
