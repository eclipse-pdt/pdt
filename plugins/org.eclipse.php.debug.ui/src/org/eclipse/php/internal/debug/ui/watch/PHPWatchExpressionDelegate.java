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
package org.eclipse.php.internal.debug.ui.watch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpressionsManager;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPValue;
import org.eclipse.php.internal.debug.ui.Logger;

/**
 * 
 */
public class PHPWatchExpressionDelegate implements IWatchExpressionDelegate {

	private String fExpressionText;
	private IWatchExpressionListener fListener;
	private PHPDebugTarget debugTarget;
	private Job fRunDispatch;

	/**
	 * @see org.eclipse.debug.core.model.IWatchExpressionDelegate#getValue(java.lang.String,
	 *      org.eclipse.debug.core.model.IDebugElement)
	 */
	public void evaluateExpression(String expression, IDebugElement context,
			IWatchExpressionListener listener) {
		fExpressionText = expression;
		fListener = listener;
		// find a stack frame context if possible.
		IStackFrame frame = null;
		if (context instanceof IStackFrame) {
			frame = (IStackFrame) context;
		} else if (context instanceof IThread) {
			try {
				frame = ((IThread) context).getTopStackFrame();
			} catch (DebugException e) {
				Logger.logException(e);
			}
		}
		if (frame == null) {
			fListener.watchEvaluationFinished(null);
		} else {
			IDebugTarget target = frame.getDebugTarget();
			if (target instanceof PHPDebugTarget) {
				debugTarget = (PHPDebugTarget) target;
				fRunDispatch = new EvaluationRunnable();
				fRunDispatch.schedule();
			} else {
				fListener.watchEvaluationFinished(null);
			}
		}
	}

	/**
	 * Runnable used to evaluate the expression.
	 */
	private final class EvaluationRunnable extends Job {

		public EvaluationRunnable() {
			super("EvaluationRunnable"); //$NON-NLS-1$
			setSystem(true);
		}

		public IStatus run(IProgressMonitor monitor) {

			try {
				IWatchExpressionResult watchResult = new PHPWatchExpressionResult();
				fListener.watchEvaluationFinished(watchResult);
			} catch (Exception e) {
				Logger.logException(e);
				fListener.watchEvaluationFinished(null);
				// TODo fix
			}
			DebugPlugin.getDefault()
					.fireDebugEventSet(
							new DebugEvent[] { new DebugEvent(
									PHPWatchExpressionDelegate.this,
									DebugEvent.SUSPEND,
									DebugEvent.EVALUATION_IMPLICIT) });
			return Status.OK_STATUS;
		}
	}

	/**
	 * Returns the variable value.
	 * 
	 * @param variable
	 *            The variable name
	 * @return
	 */
	protected Expression getExpression(PHPDebugTarget debugTarget,
			String variable) {
		DefaultExpressionsManager expressionManager = debugTarget
				.getExpressionManager();
		Expression expression = expressionManager.buildExpression(variable);

		// Get the value from the debugger
		debugTarget.getExpressionManager().getExpressionValue(expression, 1);
		expressionManager.update(expression, 1);
		return expression;
	}

	private class PHPWatchExpressionResult implements IWatchExpressionResult,
			IWatchExpressionResultExtension {
		public IValue getValue() {
			Expression value = getExpression(debugTarget, fExpressionText);
			IValue iValue = new PHPValue(debugTarget, value);
			return iValue;
		}

		public boolean hasErrors() {
			return false;
		}

		public String[] getErrorMessages() {
			return null;
		}

		public String getExpressionText() {
			return fExpressionText;
		}

		public DebugException getException() {
			return null;
		}

		public IDebugTarget getDebugTarget() {
			return debugTarget;
		}
	}
}
