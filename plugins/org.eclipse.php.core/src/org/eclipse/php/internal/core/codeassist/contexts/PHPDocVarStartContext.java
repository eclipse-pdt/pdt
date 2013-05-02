/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 *       * @var My_|
 *       
 *   2. /**
 *       * @var |
 * </pre>
 * 
 * @author zhao
 */
public class PHPDocVarStartContext extends AbstractPHPDocTagStartContext {

	public static List<String> TAGS = new ArrayList<String>();
	static {
		TAGS.add("return"); //$NON-NLS-1$
		TAGS.add("see"); //$NON-NLS-1$
		TAGS.add("var"); //$NON-NLS-1$
		TAGS.add("link"); //$NON-NLS-1$
	}

	@Override
	protected List<String> getTags() {
		return TAGS;
	}

}