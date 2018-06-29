/**********************************************import org.eclipse.osgi.util.NLS;
 * Copyright (c) 2012 IBM Corporation and others.
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
 *     Martin Eisengardt <martin.eisengardt@fiducia.de>
 *******************************************************************************/
package org.eclipse.php.internal.core.facet;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.php.internal.core.facet.messages"; //$NON-NLS-1$
	public static String PHPFacets_SettingVersionFailed;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
