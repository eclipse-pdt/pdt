/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
