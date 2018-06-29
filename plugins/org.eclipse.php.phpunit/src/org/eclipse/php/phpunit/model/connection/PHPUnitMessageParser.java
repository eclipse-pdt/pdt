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
package org.eclipse.php.phpunit.model.connection;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.phpunit.model.elements.*;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;
import org.eclipse.php.phpunit.ui.view.TestViewer;

public class PHPUnitMessageParser {

	public static final String CALL_DYNAMIC = "->"; //$NON-NLS-1$

	public static final String CALL_STATIC = "::"; //$NON-NLS-1$

	private static final String ELEMENT_TARGET_TESTSUITE = "testsuite"; //$NON-NLS-1$

	private static final String ELEMENT_TARGET_TESTCASE = "testcase"; //$NON-NLS-1$

	private static PHPUnitMessageParser instance;

	/**
	 * @return Singleton
	 */
	public static PHPUnitMessageParser getInstance() {
		if (instance == null) {
			instance = new PHPUnitMessageParser();
		}
		return instance;
	}

	private RemoteDebugger remoteDebugger;
	private PHPUnitTestGroup currentGroup;
	private PHPUnitTestCase currentTestCase;
	private boolean inProgress = false;

	public PHPUnitMessageParser() {
	}

	public void parseMessage(Message message, final TestViewer viewer) {
		if (!isInProgress()) {
			setInProgress(true);
		}
		if (message == null) {
			return;
		}

		final String target = message.getTarget();
		final MessageEventType event = message.getEvent();
		if (target.equals(ELEMENT_TARGET_TESTSUITE)) {
			if (event == MessageEventType.start) {
				parseGroupStart(viewer, message);
			} else if (event == MessageEventType.end) {
				parseGroupEnd(viewer, message);
			} else {
				currentGroup.setStatus(event);
				currentGroup.addRunCount(1);
			}
		} else if (target.equals(ELEMENT_TARGET_TESTCASE)) {
			if (event == MessageEventType.start) {
				parseTestStart(viewer, message);
			} else {
				parseTestEnd(viewer, message);
			}
		}

		PHPUnitView.getDefault().refresh(PHPUnitElementManager.getInstance().getRoot());
	}

	private void parseGroupStart(final TestViewer viewer, Message message) {
		if (message.getEvent() != MessageEventType.start) {
			return;
		}
		final PHPUnitTestGroup group = new PHPUnitTestGroup(message.getTest(), currentGroup, remoteDebugger);
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

	private void parseTestStart(final TestViewer viewer, Message message) {
		final PHPUnitTestCase testCase = new PHPUnitTestCase(message.getTest(), currentGroup, message.getEvent(),
				remoteDebugger);
		currentTestCase = testCase;
		mapTest(testCase);
		currentGroup.addChild(testCase, false);
		viewer.registerTestAdded();
	}

	private void parseTestEnd(final TestViewer viewer, Message message) {
		final PHPUnitTestCase testCase = currentTestCase;
		testCase.setStatus(message.getEvent());
		if (message.getTime() != null) {
			testCase.setTime(message.getTime());
		}
		parseProblems(testCase, message);
		currentGroup.addChild(testCase, true);
		viewer.registerTestAdded();
		if (testCase.getStatus() > PHPUnitTest.STATUS_PASS) {
			viewer.registerAutoScrollTarget(testCase);
			viewer.registerFailedForAutoScroll(testCase);
		}
	}

	private void parseProblems(PHPUnitTest model, Message message) {
		mapException(model, message.getException());
		mapWarnings(model, message.getWarnings());
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
	public void mapException(final PHPUnitTest testCase, MessageException exception) {
		if (exception == null) {
			return;
		}
		testCase.setException(new PHPUnitTestException(exception, testCase, remoteDebugger));
		mapTest(testCase.getException());
	}

	private void mapTest(final PHPUnitElement test) {
		PHPUnitElementManager.getInstance().add(test.getTestId(), test);
	}

	private void mapWarnings(final PHPUnitTest testCase, Map<Integer, MessageException> warnings) {
		if (warnings == null) {
			return;
		}

		if (testCase.getWarnings() == null) {
			testCase.setWarnings(new ArrayList<>(warnings.size()));
		}
		for (Integer key : warnings.keySet()) {
			final PHPUnitTestWarning warning = new PHPUnitTestWarning(warnings.get(key), testCase, remoteDebugger);
			mapTest(warning);
			testCase.getWarnings().add(key, warning);
		}
	}

	private void parseGroupEnd(final TestViewer viewer, Message message) {
		if (currentGroup.getChildren() == null) {
			parseProblems(currentGroup, message);
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