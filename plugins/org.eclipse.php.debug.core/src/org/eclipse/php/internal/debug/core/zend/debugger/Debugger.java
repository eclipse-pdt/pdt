/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/*
 * Debugger.java
 *
 */

package org.eclipse.php.internal.debug.core.zend.debugger;

/**
 * @author eran
 */
public interface Debugger {

	/**
	 * Asynchronic start Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean start(StartResponseHandler responseHandler);

	/**
	 * Synchronic start Returns true if successed start.
	 */
	boolean start();

	/**
	 * Sets the protocol to be used.
	 * 
	 * @param protocolID
	 *            The protocol identification number.
	 * @return True, if the set protocol command was successful; False, otherwise.
	 */
	boolean setProtocol(int protocolID);

	/**
	 * Asynchronic addBreakpoint Returns true if successed sending the request,
	 * false otherwise.
	 */
	boolean addBreakpoint(Breakpoint bp, BreakpointAddedResponseHandler responseHandler);

	/**
	 * Synchronic addBreakpoint Returns true if successed adding the Breakpoint.
	 */
	public void addBreakpoint(Breakpoint breakpoint);

	/**
	 * Asynchronic removeBreakpoint Returns true if successed sending the request,
	 * false otherwise.
	 */
	boolean removeBreakpoint(int id, BreakpointRemovedResponseHandler responseHandler);

	/**
	 * Synchronic removeBreakpoint Returns true if successed removing the
	 * Breakpoint.
	 */
	boolean removeBreakpoint(int id);

	/**
	 * Asynchronic removeBreakpoint Returns true if successed sending the request,
	 * false otherwise.
	 */
	boolean removeBreakpoint(Breakpoint breakpoint, BreakpointRemovedResponseHandler responseHandler);

	/**
	 * Synchronic removeBreakpoint Returns true if successed removing the
	 * Breakpoint.
	 */
	boolean removeBreakpoint(Breakpoint breakpoint);

	/**
	 * Asynchronic removeAllBreakpoints Returns true if successed sending the
	 * request, false otherwise.
	 */
	boolean removeAllBreakpoints(AllBreakpointRemovedResponseHandler responseHandler);

	/**
	 * Synchronic removeAllBreakpoints Returns true if successed removing all the
	 * Breakpoint.
	 */
	boolean removeAllBreakpoints();

	/**
	 * Asynchronic stepInto Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean stepInto(StepIntoResponseHandler responseHandler);

	/**
	 * Synchronic stepInto Returns true if successed stepInto.
	 */
	boolean stepInto();

	/**
	 * Asynchronic stepOver Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean stepOver(StepOverResponseHandler responseHandler);

	/**
	 * Synchronic stepOver Returns true if successed stepOver.
	 */
	boolean stepOver();

	/**
	 * Asynchronic stepOut Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean stepOut(StepOutResponseHandler responseHandler);

	/**
	 * Synchronic stepOut Returns true if successed stepOut.
	 */
	boolean stepOut();

	/**
	 * Asynchronic go Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean go(GoResponseHandler responseHandler);

	/**
	 * Synchronic go Returns true if successed go.
	 */
	boolean go();

	/** start methods are in the interface parent * */

	/**
	 * Asynchronic pause Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean pause(PauseResponseHandler responseHandler);

	/**
	 * Synchronic pause Returns true if successed pause.
	 */
	boolean pause();

	/**
	 * Asynchronic eval Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean eval(String commandString, EvalResponseHandler responseHandler);

	/**
	 * Synchronic eval Returns the evaled commandString.
	 */
	public String eval(String commandString);

	/**
	 * Asynchronic assign value Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean assignValue(String var, String value, int depth, String[] path, AssignValueResponseHandler responseHandler);

	/**
	 * Synchronic assign value Returns true if successed assigning the value.
	 */
	boolean assignValue(String var, String value, int depth, String[] path);

	/**
	 * Asynchronic getVariableValue Returns true if successed sending the request,
	 * false otherwise.
	 */
	boolean getVariableValue(String value, int depth, String[] path, VariableValueResponseHandler responseHandler);

	/**
	 * Synchronic getVariableValue Returns the variable value.
	 */
	public byte[] getVariableValue(String value, int depth, String[] path);

	/**
	 * Asynchronic getCallStack Returns true if successed sending the request, false
	 * otherwise.
	 */
	boolean getCallStack(GetCallStackResponseHandler responseHandler);

	/**
	 * Synchronic getCallStack Returns the PHPstack;
	 */
	public PHPstack getCallStack();

	/**
	 * Asynchronic getStackVariableValue Returns true if successed sending the
	 * request, false otherwise.
	 */
	boolean getStackVariableValue(int stackDepth, String value, int depth, String[] path,
			GetStackVariableValueResponseHandler responseHandler);

	/**
	 * Synchronic getStackVariableValue Returns the variable value.
	 */
	public byte[] getStackVariableValue(int stackDepth, String value, int depth, String[] path);

	/**
	 * Asynchronous addFiles Returns true if succeeded sending the request, false
	 * otherwise.
	 */
	boolean addFiles(String[] paths, AddFilesResponseHandler responseHandler);

	/**
	 * Synchronous addFiles Returns true if succeeded adding the Breakpoint.
	 */
	boolean addFiles(String[] paths);

	/**
	 * Finish the debugger running.
	 */
	public void finish();

	/**
	 * Checks if the debugger is active.
	 */
	boolean isActive();

	/**
	 * Returns PHP version that debug session is running on.
	 */
	public String getPHPVersion();

	// ---------------------------------------------------------------------------

	// Interface for started response handler.
	public static interface StartResponseHandler {

		void started(boolean success);

	}

	// Interface for an breakpoint added response handler.
	public static interface BreakpointAddedResponseHandler {

		void breakpointAdded(String fileName, int lineNumber, int id, boolean success);

	}

	// Interface for a breakpoint removed response handler.
	public static interface BreakpointRemovedResponseHandler {

		void breakpointRemoved(int id, boolean success);

	}

	// Interface for removed all breakpoints response handler.
	public static interface AllBreakpointRemovedResponseHandler {

		void allBreakpointRemoved(boolean success);

	}

	// Interface for evaled response handler.
	public static interface EvalResponseHandler {

		void evaled(String expression, String result, boolean success);

	}

	// Interface for stepInto response handler.
	public static interface StepIntoResponseHandler {

		void stepInto(boolean success);

	}

	// Interface for stepOver response handler.
	public static interface StepOverResponseHandler {

		void stepOver(boolean success);

	}

	// Interface for stepOut response handler.
	public static interface StepOutResponseHandler {

		void stepOut(boolean success);

	}

	// Interface for go response handler.
	public static interface GoResponseHandler {

		void go(boolean success);

	}

	// Interface for pause response handler.
	public static interface PauseResponseHandler {

		void pause(boolean success);

	}

	// Interface for assignValue response handler.
	public static interface AssignValueResponseHandler {

		void valueAssigned(String var, String value, int depth, String[] path, boolean success);

	}

	// Interface for getVariableValue response handler.
	public static interface VariableValueResponseHandler {

		void variableValue(String value, int depth, String[] path, String reslut, boolean success);

	}

	// Interface for getCallStack response handler.
	public static interface GetCallStackResponseHandler {

		void callStack(PHPstack pstack, boolean success);

	}

	// Interface for getStackVariableValue response handler.
	public static interface GetStackVariableValueResponseHandler {

		void stackVariableValue(int stackDepth, String value, int depth, String[] path, String reslut, boolean success);

	}

	// Interface for add files response handler.
	public static interface AddFilesResponseHandler {

		void addFiles(boolean success);

	}

	// ---------------------------------------------------------------------------

	// An interface for all the respones handler.
	public interface DebugResponseHandler extends BreakpointAddedResponseHandler, BreakpointRemovedResponseHandler,
			AllBreakpointRemovedResponseHandler, StartResponseHandler, EvalResponseHandler, StepIntoResponseHandler,
			StepOverResponseHandler, StepOutResponseHandler, GoResponseHandler, PauseResponseHandler,
			AssignValueResponseHandler, VariableValueResponseHandler, GetCallStackResponseHandler,
			GetStackVariableValueResponseHandler, AddFilesResponseHandler {

	}

	// ---------------------------------------------------------------------------

	// Adapter for DebugResponseHandler.
	public static class DefaultDebugResponseHandler implements DebugResponseHandler {

		@Override
		public void breakpointAdded(String fileName, int lineNumber, int id, boolean success) {
			// adapter
		}

		@Override
		public void breakpointRemoved(int id, boolean success) {
			// adapter
		}

		@Override
		public void allBreakpointRemoved(boolean success) {
			// adapter
		}

		@Override
		public void started(boolean success) {
			// adapter
		}

		@Override
		public void evaled(String expression, String result, boolean success) {
			// adapter
		}

		@Override
		public void stepInto(boolean success) {
			// adapter
		}

		@Override
		public void stepOver(boolean success) {
			// adapter
		}

		@Override
		public void stepOut(boolean success) {
			// adapter
		}

		@Override
		public void go(boolean success) {
			// adapter
		}

		@Override
		public void pause(boolean success) {
			// adapter
		}

		@Override
		public void valueAssigned(String var, String value, int depth, String[] path, boolean success) {
			// adapter
		}

		@Override
		public void variableValue(String value, int depth, String[] path, String reslut, boolean success) {
			// adapter
		}

		@Override
		public void callStack(PHPstack pstack, boolean success) {
			// adapter
		}

		@Override
		public void stackVariableValue(int stackDepth, String value, int depth, String[] path, String reslut,
				boolean success) {
			// adapter
		}

		@Override
		public void addFiles(boolean success) {
			// adapter
		}

	}

}