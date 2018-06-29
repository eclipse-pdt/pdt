/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.launch.execution;

public interface ExecutionResponseListener {

	// when finished
	public void executionFinished(String response, int exitValue);

	public void executionFailed(String response, Exception exception);

	// instant notification
	public void executionError(String message);

	public void executionMessage(String message);

	public void executionAboutToStart();

	public void executionStarted();
}
