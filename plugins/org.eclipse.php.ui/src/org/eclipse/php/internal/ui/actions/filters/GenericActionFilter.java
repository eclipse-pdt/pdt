/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions.filters;

import org.eclipse.php.ui.actions.filters.IActionFilterContributor;
import org.eclipse.ui.IActionFilter;

public class GenericActionFilter implements IActionFilter {

	/** Parameter name that should be specified in Action visibility/enablement */
	public static final String PN_CONTRIBUTOR_ID = "actionFilterContributorId"; //$NON-NLS-1$

	public boolean testAttribute(Object target, String name, String value) {
		if (PN_CONTRIBUTOR_ID.equals(name)) {
			// find relevant action filter contributor:
			IActionFilterContributor actionFilterContributor = ActionFilterContributorsRegistry
					.getInstance().getContributor(value);
			if (actionFilterContributor != null) {
				return actionFilterContributor.testAttribute(target, name,
						value);
			}
		}
		return false;
	}
}
