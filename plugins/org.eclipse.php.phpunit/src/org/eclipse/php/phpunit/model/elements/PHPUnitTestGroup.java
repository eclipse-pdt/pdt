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
package org.eclipse.php.phpunit.model.elements;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.phpunit.model.connection.PHPUnitMessageParser;

public class PHPUnitTestGroup extends PHPUnitTest {

	private class StatusCount {
		public int[] counts = { 0, 0, 0, 0, 0, 0 }; // STATUS_STARTED,
													// STATUS_PASS, STATUS_SKIP,
													// STATUS_INCOMPLETE,
													// STATUS_FAIL, STATUS_ERROR
	}

	private Set<PHPUnitTest> children = null;

	private int runCount = 0;

	private final StatusCount statusCount = new StatusCount();

	private int totalCount;

	private boolean method;

	public PHPUnitTestGroup(final Map<?, ?> test, final PHPUnitTestGroup parent, RemoteDebugger remoteDebugger) {
		super(test, parent, remoteDebugger);
		if (test == null)
			totalCount = 0;
		else
			totalCount = Integer.parseInt((String) test.get(PHPUnitMessageParser.PROPERTY_COUNT));
	}

	@Override
	protected void processName(String name) {
		int cutFrom = name.indexOf(METHOD_SEPARATOR);
		if (cutFrom > 0) {
			this.name = name.substring(cutFrom + 2);
			method = true;
		} else {
			this.name = name;
		}
	}

	public void addChild(final PHPUnitTest test, boolean finished) {
		if (children == null) {
			children = new LinkedHashSet<>();
		}
		children.add(test);
		if (test instanceof PHPUnitTestCase && finished) {
			addRunCount(1);
		}
		setStatus(test.getStatus());
	}

	public void addRunCount(final int count) {
		runCount += count;
		if (parent != null)
			((PHPUnitTestGroup) parent).addRunCount(count);
	}

	public Set<PHPUnitTest> getChildren() {
		return children;
	}

	@Override
	public int getRunCount() {
		return runCount;
	}

	@Override
	public int getStatus() {
		return status;
	}

	public int getStatusCount(final int status) {
		return statusCount.counts[status];
	}

	public int getFailedCount() {
		return getStatusCount(STATUS_FAIL) + getStatusCount(STATUS_ERROR);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setParent(final PHPUnitTestGroup group) {
		parent = group;
	}

	@Override
	public String getLocalFile() {
		if (isMethod() && parent != null) {
			return parent.getLocalFile();
		}

		return super.getLocalFile();
	}

	@Override
	public void setStatus(final int status) {
		statusCount.counts[status]++;
		this.status = Math.max(this.status, status);
		if (parent != null)
			((PHPUnitTestGroup) parent).setStatus(status);
	}

	public boolean isMethod() {
		return method;
	}

	public String getSuiteName() {
		if (getLocalFile() == null) {
			return getName();
		}

		return null;
	}

	public String getFilterName() {
		if (getLocalFile() == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(getName());
		if (method) {
			sb.insert(0, METHOD_SEPARATOR).insert(0, ((PHPUnitTestGroup) parent).getName());
		} else {
			sb.append(METHOD_SEPARATOR);
		}

		return sb.append(".*").toString(); //$NON-NLS-1$
	}
}