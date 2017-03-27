/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.server.core.builtin.debugger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPMultiDebugTarget;

@SuppressWarnings("restriction")
public class PHPServerDebugTarget extends PHPMultiDebugTarget {

	public PHPServerDebugTarget(ILaunch launch, IProcess process) throws CoreException {
		super(launch, process);
	}

	@Override
	public String getName() throws DebugException {
		return "sssss";
	}

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			Object source = event.getSource();
			if (event.getKind() == DebugEvent.TERMINATE) {
				if (source instanceof PHPDebugTarget) {
					PHPDebugTarget target = (PHPDebugTarget) source;
					if (fDebugTargets.contains(target)) {
						try {
							if (!target.isTerminated()) {
								target.terminate();
							}
						} catch (DebugException e) {
						}
					}
				}
			}
		}
	}

}
