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
package org.eclipse.php.internal.debug.core.zend.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.php.internal.debug.core.model.PHPDebugElement;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.debugger.ExpressionValue;

/**
 * PHP stack frame.
 */
public class PHPStackFrame extends PHPDebugElement implements IStackFrame {

	private final class VariablesContainer {

		private Map<String, IVariable> fAllCurrentVariables = null;
		private Map<String, IVariable> fAllPreviousVariables = null;
		private IVariable[] fVariables = null;
		private boolean fIsOutdated = true;

		IVariable[] getVariables() {
			if (fIsOutdated) {
				updateVariables();
				fVariables = fAllCurrentVariables.values().toArray(new IVariable[fAllCurrentVariables.size()]);
				fIsOutdated = false;
			}
			return fVariables;
		}

		void markOutdated() {
			fIsOutdated = true;
		}

		/**
		 * Merges incoming variable. Merge is done by means of checking if related child
		 * variable existed in "one step back" state of a frame. If related variable
		 * existed, it is updated with the use of the most recent descriptor and
		 * returned instead of the incoming one.
		 * 
		 * @param variable
		 * @param descriptor
		 * @return merged variable
		 */
		private IVariable merge(IVariable variable) {
			if (fAllPreviousVariables == null) {
				return variable;
			}
			if (!(variable instanceof PHPVariable)) {
				return variable;
			}
			PHPVariable incoming = (PHPVariable) variable;
			if (incoming.getFullName().isEmpty()) {
				return incoming;
			}
			IVariable stored = fAllPreviousVariables.get(incoming.getFullName());
			if (stored != null) {
				((PHPVariable) stored).update(incoming.getExpression());
				return stored;
			}
			return variable;
		}

		private void updateVariables() {
			fAllPreviousVariables = fAllCurrentVariables;
			fAllCurrentVariables = new LinkedHashMap<>();
			Expression[] localVariables = ExpressionValue.sort(fExpressions);
			fAllCurrentVariables = new LinkedHashMap<>();
			for (int i = 0; i < localVariables.length; i++) {
				PHPVariable incoming = new PHPVariable((PHPDebugTarget) fThread.getDebugTarget(), localVariables[i]);
				fAllCurrentVariables.put(incoming.getFullName(), merge(incoming));
			}
		}
	}

	private static final Pattern LAMBDA_FUNC_PATTERN = Pattern.compile("(.*)\\((\\d+)\\) : runtime-created function"); //$NON-NLS-1$

	private PHPThread fThread;
	private String fFunctionName;
	private String fFileName;
	private String fResolvedFileName;
	private int fLineNumber;
	private int fDepth;
	private Expression[] fExpressions;
	private VariablesContainer fVariablesContainer;

	/**
	 * Create new PHP stack frame
	 * 
	 * @param thread
	 *            Debug thread
	 * @param fileName
	 *            Current file name
	 * @param funcName
	 *            Current function name
	 * @param lineNumber
	 *            Current line number
	 * @param depth
	 *            Stack layer depth
	 * @param resolvedFileName
	 *            Resolved file name
	 * @param localVariables
	 *            All local function variables
	 */
	public PHPStackFrame(IThread thread, String fileName, String resolvedFileName, String funcName, int lineNumber,
			int depth, Expression[] localVariables) {
		super((PHPDebugTarget) thread.getDebugTarget());
		fVariablesContainer = new VariablesContainer();
		baseInit(thread, fileName, resolvedFileName, funcName, lineNumber, depth, localVariables);
	}

	private void baseInit(IThread thread, String fileName, String resolvedFileName, String funcName, int lineNumber,
			int depth, Expression[] localVariables) {
		Matcher matcher = LAMBDA_FUNC_PATTERN.matcher(fileName);
		if (matcher.matches()) {
			fileName = matcher.group(1);
			lineNumber = Integer.parseInt(matcher.group(2));
		}
		fFunctionName = funcName;
		fFileName = fileName;
		fResolvedFileName = resolvedFileName;
		fLineNumber = lineNumber;
		fDepth = depth;
		fThread = (PHPThread) thread;
		fExpressions = localVariables;
	}

	protected synchronized void update(int lineNumber, Expression[] localVariables) throws DebugException {
		this.fLineNumber = lineNumber;
		this.fExpressions = localVariables;
		this.fVariablesContainer.markOutdated();
	}

	/**
	 * Returns this stack frame's unique identifier within its thread
	 * 
	 * @return this stack frame's unique identifier within its thread
	 */
	protected int getDepth() {
		return fDepth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getThread()
	 */
	@Override
	public IThread getThread() {
		return fThread;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getVariables()
	 */
	@Override
	public synchronized IVariable[] getVariables() throws DebugException {
		return fVariablesContainer.getVariables();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
	 */
	@Override
	public boolean hasVariables() throws DebugException {
		return ((PHPDebugTarget) getDebugTarget()).getVariables(this).length > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getLineNumber()
	 */
	@Override
	public synchronized int getLineNumber() throws DebugException {
		return fLineNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharStart()
	 */
	@Override
	public int getCharStart() throws DebugException {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharEnd()
	 */
	@Override
	public int getCharEnd() throws DebugException {
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getName()
	 */
	@Override
	public String getName() throws DebugException {
		return fFunctionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getRegisterGroups()
	 */
	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return new IRegisterGroup[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasRegisterGroups()
	 */
	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	@Override
	public boolean canStepInto() {
		return getThread().canStepInto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	@Override
	public boolean canStepOver() {
		return getThread().canStepOver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	@Override
	public boolean canStepReturn() {
		return getThread().canStepReturn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	@Override
	public boolean isStepping() {
		return getThread().isStepping();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	@Override
	public void stepInto() throws DebugException {
		getThread().stepInto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	@Override
	public void stepOver() throws DebugException {
		getThread().stepOver();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	@Override
	public void stepReturn() throws DebugException {
		getThread().stepReturn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	@Override
	public boolean canResume() {
		return getThread().canResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	@Override
	public boolean canSuspend() {
		return getThread().canSuspend();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	@Override
	public boolean isSuspended() {
		return getThread().isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	@Override
	public void resume() throws DebugException {
		getThread().resume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	@Override
	public void suspend() throws DebugException {
		getThread().suspend();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	@Override
	public boolean canTerminate() {
		return getThread().canTerminate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	@Override
	public boolean isTerminated() {
		return getThread().isTerminated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	@Override
	public void terminate() throws DebugException {
		getThread().terminate();
	}

	/**
	 * Returns the name of the source file this stack frame is associated with.
	 * 
	 * @return the name of the source file this stack frame is associated with
	 */
	public String getSourceName() {
		return fResolvedFileName;
	}

	/**
	 * Returns the file name with full path.
	 * 
	 * @return the file name with full path
	 */
	public String getAbsoluteFileName() {
		return fFileName;
	}

	public synchronized Expression[] getStackVariables() {
		return fExpressions;
	}

}
