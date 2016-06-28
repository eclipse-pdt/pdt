/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public enum ComposerCLIParams {

	PREFER_SOURCE("--prefer-source"), //$NON-NLS-1$
	PREFER_DIST("--prefer-dist"), //$NON-NLS-1$
	NO_INTERACTION("--no-interaction"), //$NON-NLS-1$
	NO_ANSI("--no-ansi"), //$NON-NLS-1$
	ANSI("--ansi"), //$NON-NLS-1$
	NO_PROGRESS("--no-progress"); //$NON-NLS-1$

	private String parameter;

	private ComposerCLIParams(String parameter) {
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		return getParameter();
	}

	public static String[] toStringArray(ComposerCLIParams[] options) {
		List<String> params = new ArrayList<String>();
		for (ComposerCLIParams option : options) {
			params.add(option.toString());
		}
		return params.toArray(new String[0]);
	}

}
