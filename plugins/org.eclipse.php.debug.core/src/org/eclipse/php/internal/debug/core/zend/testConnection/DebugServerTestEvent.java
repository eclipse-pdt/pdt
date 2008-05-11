/*******************************************************************************
 * Copyright (c) 2008 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.testConnection;

import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.debug.core.PHPDebugCoreMessages;

/**
 * This represents an event which is created when a Debug Server Test completes.
 * Note : The result of event can be Success,Timeout OR Failure.
 * @author yaronm
 */
public class DebugServerTestEvent {
	/**
	 * A Success type event
	 */
	public final static int TEST_SUCCEEDED = 0;

	/**
	 * A Failure Test event.
	 * NOTE: You should add a failure message to the constructor when using "Failure" type of event 
	 */
	public final static int TEST_FAILED = 1;

	/**
	 * A Timeout type event which simply caused by a time out
	 */
	public final static int TEST_TIMEOUT = 2;

	private int fEventType;
	private String fURL;
	private String fFailureMessage = ""; //$NON-NLS-1$

	public DebugServerTestEvent(String url, int eventType) {
		this(url, eventType, ""); //$NON-NLS-1$
	}

	/**
	 * Constructs an event with a custom failure message
	 * @param url
	 * @param eventType
	 * @param failureMessage
	 */
	public DebugServerTestEvent(String url, int eventType, String failureMessage) {
		fEventType = eventType;
		fURL = url;
		fFailureMessage = failureMessage;
	}

	/**
	 * Returns the message that comes with this test event.
	 * @return
	 */
	public String getEventMessage() {
		switch (fEventType) {
			case TEST_SUCCEEDED:
				return PHPDebugCoreMessages.DebugServerTestEvent_success;
			case TEST_FAILED:
				return fFailureMessage;
			case TEST_TIMEOUT:
				return NLS.bind(PHPDebugCoreMessages.DebugServerTestEvent_timeOutMessage,fURL);
		}
		return "";//$NON-NLS-0$
	}

	/**
	 * Returns the event type.
	 * See DebugServerTestEvent constants types
	 * @return
	 */
	public int getEventType() {
		return fEventType;
	}

	/**
	 * The URL string representation of the source of event
	 * @return
	 */
	public String getSourceURL() {
		return fURL;
	}
}
