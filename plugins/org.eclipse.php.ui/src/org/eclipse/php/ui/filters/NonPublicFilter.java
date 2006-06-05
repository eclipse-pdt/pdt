/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.filters;

/**
 * Non-public member filter.
 * 
 * @since 3.0
 */
public class NonPublicFilter extends MemberFilter {
	public NonPublicFilter() {
		addFilter(MemberFilter.FILTER_NONPUBLIC);
	}
}
