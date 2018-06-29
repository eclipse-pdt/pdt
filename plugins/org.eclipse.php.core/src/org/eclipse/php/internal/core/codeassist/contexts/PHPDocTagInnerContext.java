/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.ArrayList;
import java.util.List;

/**
 * This context represents the state when staying inside of a PHPDoc tag. <br/>
 * Examples:
 * 
 * <pre>
 *   1. /**
 *       * @see My_|
 *       
 *   2. /**
 *       * @see |
 * </pre>
 * 
 * @author zhao
 */
public class PHPDocTagInnerContext extends AbstractPHPDocTagStartContext {

	public static List<String> TAGS = new ArrayList<>();

	static {
		TAGS.add("see"); //$NON-NLS-1$
		TAGS.add("link"); //$NON-NLS-1$
	}

	@Override
	protected List<String> getTags() {
		return TAGS;
	}

}