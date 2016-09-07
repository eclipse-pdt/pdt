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
package org.eclipse.php.composer.api.collection;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.entities.AbstractJsonArray;
import org.eclipse.php.composer.api.json.ParseException;

/**
 * A collection to manage multiple composer packages.
 * 
 * Can be parsed from e.g. composer/installed.json or
 * composer/installed_dev.json file
 * 
 * @author Thomas Gossmann <gos.si>
 */
public class ComposerPackages extends AbstractJsonArray<ComposerPackage> {

	public ComposerPackages() {
	}

	public ComposerPackages(Object json) {
		fromJson(json);
	}

	public ComposerPackages(String json) throws ParseException {
		fromJson(json);
	}

	public ComposerPackages(File file) throws IOException, ParseException {
		fromJson(file);
	}

	public ComposerPackages(Reader reader) throws IOException, ParseException {
		fromJson(reader);
	}

	@SuppressWarnings("rawtypes")
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedHashMap) {
			add(new ComposerPackage(obj));
		} else if (obj instanceof LinkedList) {
			LinkedList array = (LinkedList) obj;
			for (Object entry : array) {
				if (entry instanceof LinkedHashMap) {
					add(new ComposerPackage(entry));
				}
			}
		}
	}

	public void addAll(ComposerPackages packages) {
		for (ComposerPackage pkg : packages) {
			add(pkg);
		}
	}

	public void removeAll(ComposerPackages packages) {
		for (ComposerPackage pkg : packages) {
			remove(pkg);
		}
	}

	public boolean has(String name) {
		for (ComposerPackage pkg : values) {
			if (pkg.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
