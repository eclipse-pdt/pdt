/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
import org.eclipse.dltk.internal.ui.navigator.TreeHierarchyLayoutProblemsDecorator;
import org.eclipse.dltk.ui.ScriptElementImageDescriptor;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.ui.phar.wizard.PharUIUtil;

/**
 * @since 4.1
 */
public class PHPElementProblemsLabelDecorator extends TreeHierarchyLayoutProblemsDecorator {
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
		return super.computeAdornmentFlags(obj);
	}
}
