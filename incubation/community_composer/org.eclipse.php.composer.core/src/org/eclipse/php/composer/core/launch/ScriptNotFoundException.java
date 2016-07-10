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
package org.eclipse.php.composer.core.launch;

/**
 * Thrown if a PHP script to be executed could not be found. Use this to trigger
 * automatic download of non-existing scripts/phars.
 *
 */
public class ScriptNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ScriptNotFoundException(String message) {
		super(message);
	}
}
