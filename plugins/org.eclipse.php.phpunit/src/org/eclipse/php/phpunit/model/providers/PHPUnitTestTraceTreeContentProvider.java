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

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.phpunit.model.elements.*;

public class PHPUnitTestTraceTreeContentProvider extends ArrayContentProvider implements ITreeContentProvider {

	private boolean filter;

	public PHPUnitTestTraceTreeContentProvider(final boolean filter) {
		this.filter = filter;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		final List<PHPUnitElement> results = new ArrayList<>();
		if (parentElement instanceof PHPUnitTest) {
			getCaseChildren((PHPUnitTest) parentElement, results);
		}
		if (parentElement instanceof PHPUnitTestEvent) {
			getEventChildren((PHPUnitTestEvent) parentElement, results);
		}
		return results.toArray();
	}

	private void getCaseChildren(final PHPUnitTest testCase, final List<PHPUnitElement> results) {
		final PHPUnitTestException exception = testCase.getException();
		if (exception != null && (!filter || !exception.isFiltered())) {
			results.add(testCase.getException());
		}
		final List<PHPUnitElement> warnings = testCase.getWarnings();
		if (warnings == null || warnings.isEmpty()) {
			return;
		}

		if (!filter) {
			results.addAll(warnings);
		} else {
			for (PHPUnitElement warning : warnings) {
				if (!warning.isFiltered()) {
					results.add(warning);
				}
			}
		}
	}

	private void getEventChildren(final PHPUnitTestEvent testEvent, final List<PHPUnitElement> results) {
		List<? extends PHPUnitElement> trace = testEvent.getTrace();
		if (trace == null || trace.isEmpty()) {
			return;
		}
		if (!filter) {
			results.addAll(trace);
		} else {
			for (PHPUnitElement frame : trace) {
				if (!frame.isFiltered()) {
					results.add(frame);
				}
			}
		}
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof PHPUnitTraceFrame) {
			return ((PHPUnitTraceFrame) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof PHPUnitTest) {
			return hasChildrenCase((PHPUnitTest) element);
		}
		if (element instanceof PHPUnitTestEvent) {
			return hasChildrenEvent((PHPUnitTestEvent) element);
		}
		return false;
	}

	private boolean hasChildrenCase(final PHPUnitTest testCase) {
		if (testCase.getException() != null) {
			return true;
		}
		final List<PHPUnitElement> warnings = testCase.getWarnings();
		if (warnings != null && !warnings.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean hasChildrenEvent(final PHPUnitTestEvent testEvent) {
		List<?> trace = testEvent.getTrace();
		if (trace != null && !trace.isEmpty()) {
			return true;
		}
		return false;
	}

}