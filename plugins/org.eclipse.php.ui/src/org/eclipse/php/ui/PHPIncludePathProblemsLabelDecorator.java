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
package org.eclipse.php.ui;

import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.ui.ProblemsLabelDecorator;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.ui.phar.wizard.PharUIUtil;

public class PHPIncludePathProblemsLabelDecorator extends ProblemsLabelDecorator {
	@Override
	protected int computeAdornmentFlags(Object obj) {
		if (obj instanceof IncludePath) {
			final Object entry = ((IncludePath) obj).getEntry();
			if (entry instanceof IBuildpathEntry) {
				if (PharUIUtil.isInvalidPharBuildEntry((IBuildpathEntry) entry)) {
					return ScriptElementImageDescriptor.ERROR;
				}
			}
		}
		return 0;
	}
}