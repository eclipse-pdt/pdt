/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	public boolean start(StartResponseHandler responseHandler);

	/**
	 * Synchronic start Returns true if successed start.
	 */
	public boolean start();

	/**
	 * Sets the protocol to be used.
	 * 
	 * @param protocolID
	 *            The protocol identification number.
	 * @return True, if the set protocol command was successful; False,
	 *         otherwise.
	 */
	public boolean setProtocol(int protocolID);

	/**
	 * Asynchronic addBreakpoint Returns true if successed sending the request,
	 * false otherwise.
	 */
	public boolean addBreakpoint(Breakpoint bp,
			BreakpointAddedResponseHandler responseHandler);

	/**
	 * Synchronic addBreakpoint Returns true if successed adding the Breakpoint.
	 */
	public void addBreakpoint(Breakpoint breakpoint);

	/**
	 * Asynchronic removeBreakpoint Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean removeBreakpoint(int id,
			BreakpointRemovedResponseHandler responseHandler);

	/**
	 * Synchronic removeBreakpoint Returns true if successed removing the
	 * Breakpoint.
	 */
	public boolean removeBreakpoint(int id);

	/**
	 * Asynchronic removeBreakpoint Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean removeBreakpoint(Breakpoint breakpoint,
			BreakpointRemovedResponseHandler responseHandler);

	/**
	 * Synchronic removeBreakpoint Returns true if successed removing the
	 * Breakpoint.
	 */
	public boolean removeBreakpoint(Breakpoint breakpoint);

	/**
	 * Asynchronic removeAllBreakpoints Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean removeAllBreakpoints(
			AllBreakpointRemovedResponseHandler responseHandler);

	/**
	 * Synchronic removeAllBreakpoints Returns true if successed removing all
	 * the Breakpoint.
	 */
	public boolean removeAllBreakpoints();

	/**
	 * Asynchronic stepInto Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean stepInto(StepIntoResponseHandler responseHandler);

	/**
	 * Synchronic stepInto Returns true if successed stepInto.
	 */
	public boolean stepInto();

	/**
	 * Asynchronic stepOver Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean stepOver(StepOverResponseHandler responseHandler);

	/**
	 * Synchronic stepOver Returns true if successed stepOver.
	 */
	public boolean stepOver();

	/**
	 * Asynchronic stepOut Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean stepOut(StepOutResponseHandler responseHandler);

	/**
	 * Synchronic stepOut Returns true if successed stepOut.
	 */
	public boolean stepOut();

	/**
	 * Asynchronic go Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean go(GoResponseHandler responseHandler);

	/**
	 * Synchronic go Returns true if successed go.
	 */
	public boolean go();

	/** start methods are in the interface parent * */

	/**
	 * Asynchronic pause Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean pause(PauseResponseHandler responseHandler);

	/**
	 * Synchronic pause Returns true if successed pause.
	 */
	public boolean pause();

	/**
	 * Asynchronic eval Returns true if successed sending the request, false
	 * otherwise.
	 */
	public boolean eval(String commandString,
			EvalResponseHandler responseHandler);

	/**
	 * Synchronic eval Returns the evaled commandString.
	 */
	public String eval(String commandString);

	/**
	 * Asynchronic assign value Returns true if successed sending the request,
	 * false otherwise.
	 */
	public boolean assignValue(String var, String value, int depth,
			String[] path, AssignValueResponseHandler responseHandler);

	/**
	 * Synchronic assign value Returns true if successed assigning the value.
	 */
	public boolean assignValue(String var, String value, int depth,
			String[] path);

	/**
	 * Asynchronic getVariableValue Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean getVariableValue(String value, int depth, String[] path,
			VariableValueResponseHandler responseHandler);

	/**
	 * Synchronic getVariableValue Returns the variable value.
	 */
	public byte[] getVariableValue(String value, int depth, String[] path);

	/**
	 * Asynchronic getCallStack Returns true if successed sending the request,
	 * false otherwise.
	 */
	public boolean getCallStack(GetCallStackResponseHandler responseHandler);

	/**
	 * Synchronic getCallStack Returns the PHPstack;
	 */
	public PHPstack getCallStack();

	/**
	 * Asynchronic getStackVariableValue Returns true if successed sending the
	 * request, false otherwise.
	 */
	public boolean getStackVariableValue(int stackDepth, String value,
			int depth, String[] path,
			GetStackVariableValueResponseHandler responseHandler);

	/**
	 * Synchronic getStackVariableValue Returns the variable value.
	 */
	public byte[] getStackVariableValue(int stackDepth, String value,
			int depth, String[] path);

	/**
	 * Asynchronous addFiles Returns true if succeeded sending the request,
	 * false otherwise.
	 */
	public boolean addFiles(String[] paths,
			AddFilesResponseHandler responseHandler);

	/**
	 * Synchronous addFiles Returns true if succeeded adding the Breakpoint.
	 */
	public boolean addFiles(String[] paths);

	/**
	 * Finish the debugger running.
	 */
	public void finish();

	/**
	 * Checks if the debugger is active.
	 */
	public boolean isActive();

	// ---------------------------------------------------------------------------

	// Interface for started response handler.
	public static interface StartResponseHandler {

		public void started(boolean success);

	}

	// Interface for an breakpoint added response handler.
	public static interface BreakpointAddedResponseHandler {

		public void breakpointAdded(String fileName, int lineNumber, int id,
				boolean success);

	}

	// Interface for a breakpoint removed response handler.
	public static interface BreakpointRemovedResponseHandler {

		public void breakpointRemoved(int id, boolean success);

	}

	// Interface for removed all breakpoints response handler.
	public static interface AllBreakpointRemovedResponseHandler {

		public void allBreakpointRemoved(boolean success);

	}

	// Interface for evaled response handler.
	public static interface EvalResponseHandler {

		public void evaled(String expression, String result, boolean success);

	}

	// Interface for stepInto response handler.
	public static interface StepIntoResponseHandler {

		public void stepInto(boolean success);

	}

	// Interface for stepOver response handler.
	public static interface StepOverResponseHandler {

		public void stepOver(boolean success);

	}

	// Interface for stepOut response handler.
	public static interface StepOutResponseHandler {

		public void stepOut(boolean success);

	}

	// Interface for go response handler.
	public static interface GoResponseHandler {

		public void go(boolean success);

	}

	// Interface for pause response handler.
	public static interface PauseResponseHandler {

		public void pause(boolean success);

	}

	// Interface for assignValue response handler.
	public static interface AssignValueResponseHandler {

		public void valueAssigned(String var, String value, int depth,
				String[] path, boolean success);

	}

	// Interface for getVariableValue response handler.
	public static interface VariableValueResponseHandler {

		public void variableValue(String value, int depth, String[] path,
				String reslut, boolean success);

	}

	// Interface for getCallStack response handler.
	public static interface GetCallStackResponseHandler {

		public void callStack(PHPstack pstack, boolean success);

	}

	// Interface for getStackVariableValue response handler.
	public static interface GetStackVariableValueResponseHandler {

		public void stackVariableValue(int stackDepth, String value, int depth,
				String[] path, String reslut, boolean success);

	}

	// Interface for add files response handler.
	public static interface AddFilesResponseHandler {

		public void addFiles(boolean success);

	}

	// ---------------------------------------------------------------------------

	// An interface for all the respones handler.
	public interface DebugResponseHandler extends
			BreakpointAddedResponseHandler, BreakpointRemovedResponseHandler,
			AllBreakpointRemovedResponseHandler, StartResponseHandler,
			EvalResponseHandler, StepIntoResponseHandler,
			StepOverResponseHandler, StepOutResponseHandler, GoResponseHandler,
			PauseResponseHandler, AssignValueResponseHandler,
			VariableValueResponseHandler, GetCallStackResponseHandler,
			GetStackVariableValueResponseHandler, AddFilesResponseHandler {

	}

	// ---------------------------------------------------------------------------

	// Adapter for DebugResponseHandler.
	static public class DefaultDebugResponseHandler implements
			DebugResponseHandler {

		public void breakpointAdded(String fileName, int lineNumber, int id,
				boolean success) {
			// System.out.println("breakpointAdded: " + success + " " + fileName
			// + " " + lineNumber);
		}

		public void breakpointRemoved(int id, boolean success) {
			// System.out.println("breakpointRemoved: " + success + " " + id);
		}

		public void allBreakpointRemoved(boolean success) {
			// System.out.println("allBreakpointRemoved: " + success);
		}

		public void started(boolean success) {
			// System.out.println("started: " + success);
		}

		public void evaled(String expression, String result, boolean success) {
			// System.out.println("evaled: " + expression + " " + result + " " +
			// success);
		}

		public void stepInto(boolean success) {
			// System.out.println("stepInto: " + success);
		}

		public void stepOver(boolean success) {
			// System.out.println("stepOver: " + success);
		}

		public void stepOut(boolean success) {
			// System.out.println("stepOut: " + success);
		}

		public void go(boolean success) {
			// System.out.println("go: " + success);
		}

		public void pause(boolean success) {
			// System.out.println("pause: " + success);
		}

		public void valueAssigned(String var, String value, int depth,
				String[] path, boolean success) {
			// System.out.println("valueAssigned: " + value + " " + depth + " "
			// + success);
		}

		public void variableValue(String value, int depth, String[] path,
				String reslut, boolean success) {
			// System.out.println("variableValue: " + value + " " + reslut + " "
			// + success);
		}

		public void callStack(PHPstack pstack, boolean success) {
			// System.out.println("callStack: " + pstack + " " + success);
		}

		public void stackVariableValue(int stackDepth, String value, int depth,
				String[] path, String reslut, boolean success) {
			// System.out.println("stackVariableValue: " + value + " " + reslut
			// + " " + success);
		}

		public void addFiles(boolean success) {
			// System.out.println("addFiles: " + success);
		}

	}

}