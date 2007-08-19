/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.model;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;

public class PHPResponseHandler {

	PHPDebugTarget fDebugTarget;

	public class StartResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.StartResponseHandler {

		public void started(boolean success) {
			if (!success)
				Logger.log(Logger.ERROR, "PHPResponseHandler: StartResponseHandler failed");

		}
	}

	public class BreakpointAddedResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.BreakpointAddedResponseHandler {

		public void breakpointAdded(String fileName, int lineNumber, int id, boolean success) {
			fileName = RemoteDebugger.convertToSystemIndependentFileName(fileName, false);
			String info = "Filename: " + fileName + " lineNumber " + lineNumber + " id: " + id;
			if (success) {
				IBreakpoint breakpoint = fDebugTarget.findBreakpoint(fileName, lineNumber);
				if (breakpoint != null) {
					PHPLineBreakpoint lineBreakpoint = (PHPLineBreakpoint) breakpoint;
					org.eclipse.php.internal.debug.core.zend.debugger.Breakpoint rbp = lineBreakpoint.getRuntimeBreakpoint();
					rbp.setID(id);
				} else {
					// it is still possible that the breakpoint we are dealing with was not registered to the 
					// BreakpointManager because it's a Run-To-Line breakpoint.
					Logger.trace("debug", "PHPResponseHandler:: BreakpointAddedResponseHandler unable to find breakpoint " + info);
				}
			} else {
				Logger.log(Logger.ERROR, "PHPResponseHandler: BreakpointAddedResponseHandler failed " + info);
			}
		}
	}

	public class BreakpointRemovedResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.BreakpointRemovedResponseHandler {

		public void breakpointRemoved(int id, boolean success) {
			/**
			 * Commented, because breakpoint may be removed twice: once it's disabled, and another time - when it's actually
			 * removed (see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=147870)
			 */
//            if (!success)
//                Logger.log(Logger.ERROR, "PHPResponseHandler: BreakpointRemovedResponseHandler failed " + id);
		}

	}

	public class StepIntoResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.StepIntoResponseHandler {

		public void stepInto(boolean success) {
			if (!success)
				Logger.log(Logger.ERROR, "PHPResponseHandler: StepIntoResponseHandler failed");

		}

	}

	public class StepOverResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.StepOverResponseHandler {

		public void stepOver(boolean success) {
			if (!success)
				Logger.log(Logger.ERROR, "PHPResponseHandler: StepOverResponseHandler failed");

		}

	}

	public class StepOutResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.StepOutResponseHandler {

		public void stepOut(boolean success) {
			if (!success)
				Logger.log(Logger.ERROR, "PHPResponseHandler: StepOutResponseHandler failed");

		}

	}

	public class GoResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.GoResponseHandler {

		public void go(boolean success) {
			if (!success)
				Logger.log(Logger.ERROR, "PHPResponseHandler: GoResponseHandler failed");

		}

	}

	public class PauseResponseHandler implements org.eclipse.php.internal.debug.core.zend.debugger.Debugger.PauseResponseHandler {

		public void pause(boolean success) {
			if (!success)
				Logger.log(Logger.ERROR, "PHPResponseHandler: PauseResponseHandler failed");

		}

	}

	public PHPResponseHandler(PHPDebugTarget debugTarget) {
		fDebugTarget = debugTarget;
	}

}
