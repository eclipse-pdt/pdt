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
package org.eclipse.php.composer.api.objects;

import org.eclipse.php.composer.api.annotation.Name;
import org.eclipse.php.composer.api.collection.JsonArray;
import org.eclipse.php.composer.api.collection.Psr;

/**
 * Represents the autoload entity of a composer package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#autoload
 * @see http://getcomposer.org/doc/04-schema.md#autoload-dev
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Thomas Gossmann <gos.si>
 */
public class Autoload extends JsonObject {

	private JsonArray classmap = new JsonArray();
	private JsonArray files = new JsonArray();

	@Name("psr-0")
	private Psr psr0 = new Psr();

	@Name("psr-4")
	private Psr psr4 = new Psr();

	public Autoload() {
		super();
		listen();
	}

	public boolean hasPsr0() {
		return psr0 != null && psr0.size() > 0;
	}

	public Psr getPsr0() {
		return psr0;
	}

	public boolean hasPsr4() {
		return psr4 != null && psr4.size() > 0;
	}

	public Psr getPsr4() {
		return psr4;
	}

	public boolean hasFiles() {
		return files != null && files.size() > 0;
	}

	public JsonArray getFiles() {
		return files;
	}

	public boolean hasClassMap() {
		return classmap != null && classmap.size() > 0;
	}

	public JsonArray getClassMap() {
		return classmap;
	}

	@Override
	public int size() {
		return super.size() + classmap.size() + files.size() + psr0.size() + psr4.size();
	}
}
