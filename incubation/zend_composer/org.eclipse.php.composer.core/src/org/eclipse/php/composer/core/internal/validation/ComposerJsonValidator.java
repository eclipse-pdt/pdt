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
package org.eclipse.php.composer.core.internal.validation;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.composer.core.ComposerCorePlugin;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;
import org.eclipse.php.composer.core.model.ComposerRoot;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerJsonValidator {

	private IFile composerJson;
	private ComposerRoot root;
	private JsonParseException exception;

	public ComposerJsonValidator(IFile composerJson) {
		super();
		this.composerJson = composerJson;
	}

	public void validate() {
		try {
			this.root = ComposerJacksonMapper.getMapper().readValue(
					composerJson.getContents(), ComposerRoot.class);
		} catch (JsonParseException e) {
			exception = e;
		} catch (IOException e) {
			if (e.getCause() instanceof JsonParseException) {
				exception = (JsonParseException) e.getCause();
			}
		} catch (CoreException e) {
			ComposerCorePlugin.logError(e);
		}
	}

	public boolean isValid() {
		return exception == null;
	}

	public ComposerRoot getObject() {
		return root;
	}

	public int getLine() {
		JsonLocation l = exception.getLocation();
		return l.getLineNr();
	}

	public int getColumn() {
		JsonLocation l = exception.getLocation();
		return l.getColumnNr();
	}

	public long getByteOffset() {
		JsonLocation l = exception.getLocation();
		return l.getByteOffset();
	}

	public long getCharOffset() {
		JsonLocation l = exception.getLocation();
		return l.getCharOffset();
	}

	public String getMessage() {
		return exception.getOriginalMessage();
	}

}
