/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.model.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.phpunit.model.elements.*;

public class PHPUnitTestTraceTreeContentProvider implements ITreeContentProvider {

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
		if (parentElement instanceof PHPUnitTestCase) {
			getCaseChildren((PHPUnitTestCase) parentElement, results);
		}
		if (parentElement instanceof PHPUnitTestEvent) {
			getEventChildren((PHPUnitTestEvent) parentElement, results);
		}
		return results.toArray();
	}

	private void getCaseChildren(final PHPUnitTestCase testCase, final List<PHPUnitElement> results) {
		final PHPUnitTestException exception = testCase.getException();
		if (exception != null && exception.getTrace() != null && (!filter || !exception.isFiltered())) {
			results.add(testCase.getException());
		}
		final List<PHPUnitElement> warnings = testCase.getWarnings();
		if (warnings != null && !warnings.isEmpty()) {
			if (!filter) {
				results.addAll(warnings);
			} else {
				for (final Iterator<PHPUnitElement> i = warnings.iterator(); i.hasNext();) {
					final PHPUnitTestWarning warning = (PHPUnitTestWarning) i.next();
					if (!warning.isFiltered()) {
						results.add(warning);
					}
				}
			}
		}
	}

	private void getEventChildren(final PHPUnitTestEvent testEvent, final List<PHPUnitElement> results) {
		List<? extends PHPUnitElement> trace = testEvent.getTrace();
		if (trace != null && !trace.isEmpty())
			if (!filter) {
				results.addAll(trace);
			} else {
				PHPUnitTraceFrame frame;
				for (final Iterator<? extends PHPUnitElement> i = trace.iterator(); i.hasNext();) {
					frame = (PHPUnitTraceFrame) i.next();
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
		if (element instanceof PHPUnitTestCase) {
			return hasChildrenCase((PHPUnitTestCase) element);
		}
		if (element instanceof PHPUnitTestEvent) {
			return hasChildrenEvent((PHPUnitTestEvent) element);
		}
		return false;
	}

	private boolean hasChildrenCase(final PHPUnitTestCase testCase) {
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

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

	@Override
	public void dispose() {
	}

}