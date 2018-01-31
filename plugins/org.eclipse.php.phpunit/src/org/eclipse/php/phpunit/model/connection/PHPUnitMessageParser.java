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
package org.eclipse.php.phpunit.model.connection;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.phpunit.model.elements.*;
import org.eclipse.php.phpunit.ui.view.TestViewer;

public class PHPUnitMessageParser {

	public static final String CALL_DYNAMIC = "->"; //$NON-NLS-1$

	public static final String CALL_STATIC = "::"; //$NON-NLS-1$

	private static final String ELEMENT_EVENT = "event"; //$NON-NLS-1$

	private static final String ELEMENT_EXCEPTION = "exception"; //$NON-NLS-1$

	private static final String ELEMENT_TARGET_TESTSUITE = "testsuite"; //$NON-NLS-1$

	private static final String ELEMENT_TARGET_TESTCASE = "testcase"; //$NON-NLS-1$

	private static final String ELEMENT_TEST = "test"; //$NON-NLS-1$

	private static final String ELEMENT_WARNINGS = "warnings"; //$NON-NLS-1$

	public static final String PROPERTY_CLASS = "class"; //$NON-NLS-1$

	public static final String PROPERTY_CODE = "code"; //$NON-NLS-1$

	public static final String PROPERTY_COUNT = "tests"; //$NON-NLS-1$

	public static final String PROPERTY_FILE = "file"; //$NON-NLS-1$

	public static final String PROPERTY_FILTERED = "filtered"; //$NON-NLS-1$

	public static final String PROPERTY_LINE = "line"; //$NON-NLS-1$

	public static final String PROPERTY_MESSAGE = "message"; //$NON-NLS-1$

	public static final String PROPERTY_DIFF = "diff"; //$NON-NLS-1$

	public static final String PROPERTY_NAME = "name"; //$NON-NLS-1$

	private static final String PROPERTY_TARGET = "target"; //$NON-NLS-1$

	public static final String PROPERTY_TRACE = "trace"; //$NON-NLS-1$

	public static final String STATUS_ERROR = "error"; //$NON-NLS-1$

	public static final String STATUS_FAIL = "fail"; //$NON-NLS-1$

	public static final String STATUS_INCOMPLETE = "incomplete"; //$NON-NLS-1$

	public static final String STATUS_PASS = "pass"; //$NON-NLS-1$

	public static final String STATUS_SKIP = "skip"; //$NON-NLS-1$

	public static final String TAG_END = "end"; //$NON-NLS-1$

	public static final String TAG_START = "start"; //$NON-NLS-1$

	private static PHPUnitMessageParser instance;

	/**
	 * @return Singleton
	 */
	public static PHPUnitMessageParser getInstance() {
		if (instance == null)
			instance = new PHPUnitMessageParser();
		return instance;
	}

	private RemoteDebugger remoteDebugger;
	private PHPUnitTestGroup currentGroup;
	private PHPUnitTestCase currentTestCase;
	private boolean inProgress = false;

	private PHPUnitMessageParser() {
	}

	public void parseMessage(final Map<?, ?> message, final TestViewer viewer) {
		if (!isInProgress()) {
			setInProgress(true);
		}
		if (message == null) {
			return;
		}
		final String target = (String) message.get(PROPERTY_TARGET);
		final String event = (String) message.get(ELEMENT_EVENT);
		final Map<?, ?> mTest = (Map<?, ?>) message.get(ELEMENT_TEST);
		if (target.equals(ELEMENT_TARGET_TESTSUITE)) {
			if (event.equals(TAG_START)) {
				parseGroupStart(viewer, event, mTest);
			} else if (event.equals(TAG_END)) {
				parseGroupEnd(viewer, message);
			} else {
				currentGroup.setStatus(event);
				currentGroup.addRunCount(1);
			}
		} else if (target.equals(ELEMENT_TARGET_TESTCASE)) {
			if (event.equals(TAG_START)) {
				parseTestStart(viewer, event, mTest);
			} else {
				parseTestEnd(message, viewer, event, mTest);
			}
		}
	}

	private void parseGroupStart(final TestViewer viewer, final String event, final Map<?, ?> mTest) {
		if (!event.equals(TAG_START)) {
			return;
		}
		final PHPUnitTestGroup group = new PHPUnitTestGroup(mTest, currentGroup, remoteDebugger);
		mapTest(group);
		if (currentGroup.getTotalCount() > 0) {
			currentGroup.addChild(group, false);
			viewer.registerViewerUpdate(currentGroup);
			viewer.registerTestAdded();
			group.setParent(currentGroup);
		}
		currentGroup = group;
		currentTestCase = null;
		if (currentGroup.getTotalCount() > 0 && PHPUnitElementManager.getInstance().getRoot().getTotalCount() == 0) {
			PHPUnitElementManager.getInstance().setRoot(currentGroup);
			viewer.registerTestAdded();
		}
	}

	private void parseTestStart(final TestViewer viewer, final String event, final Map<?, ?> mTest) {
		final PHPUnitTestCase testCase = new PHPUnitTestCase(mTest, currentGroup, event, remoteDebugger);
		currentTestCase = testCase;
		mapTest(testCase);
		currentGroup.addChild(testCase, false);
		viewer.registerTestAdded();
	}

	private void parseTestEnd(final Map<?, ?> message, final TestViewer viewer, final String event,
			final Map<?, ?> mTest) {
		final PHPUnitTestCase testCase = currentTestCase;
		testCase.setStatus(event);
		parseProblems(testCase, message);
		currentGroup.addChild(testCase, true);
		viewer.registerTestAdded();
		if (testCase.getStatus() > PHPUnitTest.STATUS_PASS) {
			viewer.registerAutoScrollTarget(testCase);
			viewer.registerFailedForAutoScroll(testCase);
		}
	}

	private void parseProblems(PHPUnitTest model, Map<?, ?> message) {
		final Map<?, ?> exception = (Map<?, ?>) message.get(ELEMENT_EXCEPTION);
		if (exception != null) {
			mapException(model, exception);
		}
		final Map<?, ?> warnings = (Map<?, ?>) message.get(ELEMENT_WARNINGS);
		if (warnings != null) {
			mapWarnings(model, warnings);
		}
	}

	public void clean() {
		final PHPUnitElementManager manager = PHPUnitElementManager.getInstance();
		manager.initialize();
		currentGroup = manager.getRoot();
		currentTestCase = null;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	/**
	 * @param testCase
	 * @param exception
	 */
	public void mapException(final PHPUnitTest testCase, final Map<?, ?> exception) {
		testCase.setException(new PHPUnitTestException(exception, testCase, remoteDebugger));
		mapTest(testCase.getException());
	}

	private void mapTest(final PHPUnitElement test) {
		PHPUnitElementManager.getInstance().add(test.getTestId(), test);
	}

	private void mapWarnings(final PHPUnitTest testCase, final Map<?, ?> warnings) {
		Map<?, ?> mWarning;
		// keep initial order
		for (int i = 0; (mWarning = (Map<?, ?>) warnings.get(String.valueOf(i))) != null; ++i) {
			if (testCase.getWarnings() == null)
				testCase.setWarnings(new ArrayList<PHPUnitElement>(warnings.size()));
			final PHPUnitTestWarning warning = new PHPUnitTestWarning(mWarning, testCase, remoteDebugger);
			mapTest(warning);
			testCase.getWarnings().add(i, warning);
		}
	}

	private void parseGroupEnd(final TestViewer viewer, Map<?, ?> mTest) {

		if (currentGroup.getChildren() == null) {
			parseProblems(currentGroup, mTest);
		}
		currentGroup = (PHPUnitTestGroup) currentGroup.getParent();
		currentTestCase = null;
		viewer.registerViewerUpdate(currentGroup);
	}

	public void setInProgress(final boolean inProgress) {
		this.inProgress = inProgress;
	}

	public void setDebugTarget(IDebugTarget debugTarget) {
		if (debugTarget instanceof PHPDebugTarget) {
			PHPDebugTarget phpDebugTarget = (PHPDebugTarget) debugTarget;
			this.remoteDebugger = (RemoteDebugger) phpDebugTarget.getRemoteDebugger();
		} else {
			this.remoteDebugger = null;
		}
	}

	public PHPUnitTestCase getCurrentTestCase() {
		return currentTestCase;
	}
}