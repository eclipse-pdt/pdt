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
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.*;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpVariable;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.w3c.dom.Node;

/**
 * 
 */
public class XDebugWatchExpressionDelegate implements IWatchExpressionDelegate {

	private String expressionText;
	private IWatchExpressionListener watchListener;
	private DBGpTarget debugTarget;
	private Job evalJob;

	/**
	 * @see org.eclipse.debug.core.model.IWatchExpressionDelegate#getValue(java.lang.String,
	 *      org.eclipse.debug.core.model.IDebugElement)
	 */
	public void evaluateExpression(String expression, IDebugElement context,
			IWatchExpressionListener listener) {
		expressionText = expression;
		watchListener = listener;
		IDebugTarget target = context.getDebugTarget();
		if (target instanceof DBGpTarget) {
			debugTarget = (DBGpTarget) target;
			if (!debugTarget.isSuspended()) {
				// can't evaluate unless suspended
				watchListener.watchEvaluationFinished(null);
			} else {
				evalJob = new EvaluationRunnable();
				evalJob.schedule();
			}
		} else {
			watchListener.watchEvaluationFinished(null);
		}
	}

	/**
	 * Runnable used to evaluate the expression.
	 */
	private final class EvaluationRunnable extends Job {

		public EvaluationRunnable() {
			super("XDEbugEvaluationRunnable"); //$NON-NLS-1$
			setSystem(true);
		}

		public IStatus run(IProgressMonitor monitor) {

			try {
				XDebugWatchExpressionResult watchResult = new XDebugWatchExpressionResult();
				watchResult.evaluate();
				watchListener.watchEvaluationFinished(watchResult);
			} catch (Exception e) {
				Logger.logException(e);
				watchListener.watchEvaluationFinished(null);
			}
			return Status.OK_STATUS;
		}
	}

	private class XDebugWatchExpressionResult implements
			IWatchExpressionResult, IWatchExpressionResultExtension {
		private boolean hasErrors = false;
		private IValue evalResult;

		public IValue getValue() {
			return evalResult;
		}

		public void evaluate() {
			// Logger.debug("getValue() for: " + expressionText);
			String stackLevel = "0"; //$NON-NLS-1$
			String testExp = expressionText.trim();
			Node result = null;

			// disable this performance enhancement as it requires
			// better determination of whether we have a variable
			// or an expression.
			/*
			 * if (testExp.startsWith("$") && testExp.substring(1).indexOf(" ")
			 * == -1) { result = debugTarget.getProperty(testExp, stackLevel,
			 * 0); } else { result = debugTarget.eval(testExp); }
			 */
			result = debugTarget.eval(testExp);
			if (result != null) {
				IVariable tempVar = new DBGpVariable(debugTarget, result,
						stackLevel);
				evalResult = null;
				try {
					evalResult = tempVar.getValue();
					if (evalResult == null) {
						hasErrors = true;
					}
				} catch (Exception e) {
					hasErrors = true;
				}
			} else {
				hasErrors = true;
			}
		}

		public boolean hasErrors() {
			return hasErrors;
		}

		public String[] getErrorMessages() {
			if (hasErrors) {
				// failed to evaluate expression.
				return new String[] { PHPDebugUIMessages.XDebugWatch_failed };
			}
			return null;
		}

		public String getExpressionText() {
			return expressionText;
		}

		public DebugException getException() {
			return null;
		}

		public IDebugTarget getDebugTarget() {
			return debugTarget;
		}

	}

}
