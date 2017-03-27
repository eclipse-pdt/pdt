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
package org.eclipse.php.internal.core.util;

import org.eclipse.core.runtime.jobs.Job;

/**
 * Abstract deferred job. It is very useful in case when we already scheduled a
 * job (with some delay) but we would like to defer its execution in the
 * meantime & if delay time has not passed. Generally speaking it is like
 * "rescheduling" the same job with the same delay.
 * 
 * @author Bartlomiej Laczkowski
 */
public abstract class AbstractDeferredJob extends Job {

	private final int delay;

	/**
	 * @param name
	 * @param delay
	 */
	public AbstractDeferredJob(String name, int delay) {
		super(name);
		setSystem(true);
		setUser(false);
		this.delay = delay;
	}

	/**
	 * Defer execution of this job.
	 */
	public synchronized void defer() {
		if (getState() == NONE)
			schedule(delay);
		else if (getState() == SLEEPING)
			wakeUp(delay);
		else if (getState() == WAITING) {
			sleep();
			wakeUp(delay);
		} else {
			cancel();
			schedule(delay);
		}
	}

}
