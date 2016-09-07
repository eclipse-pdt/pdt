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

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.JsonArray;

public class String2KeywordsConverter extends String2ListConverter {

	private JsonArray keywords;

	public String2KeywordsConverter() {
		super(String.class, JsonArray.class);
	}

	public String2KeywordsConverter(ComposerPackage composerPackage) {
		this();
		setComposerPackage(composerPackage);
	}

	@Override
	protected void composerPackageUpdated() {
		keywords = composerPackage.getKeywords();
	}

	@Override
	protected String[] start() {
		if (keywords == null) {
			return new String[] {};
		}

		return keywords.toArray(new String[] {});
	}

	@Override
	protected Object finish() {
		return keywords;
	}

	@Override
	protected boolean has(String value) {
		if (keywords == null) {
			return false;
		}

		return keywords.has(value);
	}

	@Override
	protected void add(String value) {
		if (keywords != null && !keywords.has(value)) {
			keywords.add(value);
		}
	}

	@Override
	protected void remove(String value) {
		if (keywords != null && keywords.has(value)) {
			keywords.remove(value);
		}
	}
}
