/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

/**
 * 
 * @author Michal Niewrzal, 2016
 * 
 */
public enum ComposerCLICommands {

	INSTALL("install"), //$NON-NLS-1$
	UPDATE("update"), //$NON-NLS-1$
	DUMP_AUTOLOAD("dump-autoload"), //$NON-NLS-1$
	SEARCH("search"), //$NON-NLS-1$
	SHOW("show"); //$NON-NLS-1$

	private String parameter;

	private ComposerCLICommands(String parameter) {
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		return getParameter();
	}

}
