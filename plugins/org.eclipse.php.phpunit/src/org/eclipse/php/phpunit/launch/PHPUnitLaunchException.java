/*******************************************************************************
 * Copyright (c) 2018 Michał Niewrzał and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Michał Niewrzał - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.launch;

public class PHPUnitLaunchException extends Exception {

	private static final long serialVersionUID = 324325429298713366L;

	public PHPUnitLaunchException(String message) {
		super(message);
	}

}
