/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.phpunit.model.elements.PHPUnitElement;
import org.eclipse.php.phpunit.model.elements.PHPUnitTest;
import org.eclipse.php.phpunit.model.elements.PHPUnitTestGroup;

public class PHPUnitElementTreeContentProvider extends ArrayContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(final Object parentElement) {
		return get(parentElement);
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return get(inputElement);
	}

	private synchronized Object[] get(Object inputElement) {
		if (inputElement instanceof PHPUnitTestGroup) {
			final List<PHPUnitTest> objects = new ArrayList<>();
			final PHPUnitTestGroup testGroup = (PHPUnitTestGroup) inputElement;
			final Set<PHPUnitTest> children = testGroup.getChildren();
			if (children != null) {
				objects.addAll(children);
			}
			return objects.toArray();
		}
		return Collections.emptyList().toArray();
	}

	@Override
	public Object getParent(final Object element) {
		return ((PHPUnitElement) element).getParent();
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof PHPUnitTestGroup) {
			final PHPUnitTestGroup testGroup = (PHPUnitTestGroup) element;
			final Set<PHPUnitTest> children = testGroup.getChildren();
			return children != null && !children.isEmpty();
		}
		return false;
	}

}