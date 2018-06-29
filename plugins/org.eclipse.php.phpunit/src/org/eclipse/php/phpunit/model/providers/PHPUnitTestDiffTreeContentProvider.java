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
import java.util.List;
import java.util.Stack;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.phpunit.model.elements.PHPUnitTest;
import org.eclipse.php.phpunit.model.elements.PHPUnitTestException;

public class PHPUnitTestDiffTreeContentProvider extends ArrayContentProvider implements ITreeContentProvider {

	private String diff;

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof DiffLine) {
			return ((DiffLine) parentElement).getChildren().toArray();
		} else {
			final List<DiffLine> results = new ArrayList<>();
			if (parentElement instanceof PHPUnitTest) {
				getCaseChildren((PHPUnitTest) parentElement, results);
			}
			return results.toArray();
		}
	}

	private void getCaseChildren(final PHPUnitTest testCase, final List<DiffLine> results) {
		final PHPUnitTestException exception = testCase.getException();
		if (hasChildren(testCase)) {
			final DiffLine testDiffLine = new DiffLine(exception.getMessage(), null);
			results.add(testDiffLine);
			String[] children = exception.getDiff().split("\n"); //$NON-NLS-1$
			Stack<DiffLine> parentStack = new Stack<>();
			parentStack.push(testDiffLine);
			DiffLine diffLine;
			for (String child : children) {
				diffLine = new DiffLine(child, parentStack.peek());
				if (child.endsWith(" Object (") || child.endsWith(" Array (")) { //$NON-NLS-1$ //$NON-NLS-2$
					parentStack.push(diffLine);
				} else if (")".equals(child.trim()) && parentStack.size() > 1) { //$NON-NLS-1$
					parentStack.pop();
				}
			}
		}
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof DiffLine) {
			return ((DiffLine) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof PHPUnitTest) {
			final PHPUnitTestException exception = ((PHPUnitTest) element).getException();
			if (exception == null) {
				return false;
			}
			diff = exception.getDiff();
			return diff != null && !diff.isEmpty();
		} else if (element instanceof DiffLine) {
			return !((DiffLine) element).getChildren().isEmpty();

		}
		return false;
	}

	public String getDiff() {
		return diff;
	}

}