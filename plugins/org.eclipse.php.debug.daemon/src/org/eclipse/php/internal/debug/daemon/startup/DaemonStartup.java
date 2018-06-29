/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.daemon.startup;

import org.eclipse.ui.IStartup;

/**
 * This class is loaded when the UI is loading and starts the daemon that is
 * defined as 'best match'.
 */
public class DaemonStartup implements IStartup {

	@Override
	public void earlyStartup() {
		// Do nothing. Let the plugin startup do the daemon loading.
	}
}
