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
package org.eclipse.php.internal.debug.ui.watch;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.*;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpEvalVariable;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackVariable;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpVariable.Kind;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.w3c.dom.Node;

/**
 * XDebug watch expression delegate.
 */
public class XDebugWatchExpressionDelegate implements IWatchExpressionDelegate {

	private String expressionText;
	private IWatchExpressionListener watchListener;
	private DBGpTarget debugTarget;
	private DBGpStackFrame stackFrame;
	private Job evalJob;

	/**
	 * @see org.eclipse.debug.core.model.IWatchExpressionDelegate#getValue(java.lang.String,
	 *      org.eclipse.debug.core.model.IDebugElement)
	 */
	@Override
	public void evaluateExpression(String expression, IDebugElement context, IWatchExpressionListener listener) {
		expressionText = expression;
		watchListener = listener;
		IDebugTarget target = context.getDebugTarget();
		if (target instanceof DBGpTarget && context instanceof DBGpStackFrame) {
			debugTarget = (DBGpTarget) target;
			stackFrame = (DBGpStackFrame) context;
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

		@Override
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

	private class XDebugWatchExpressionResult implements IWatchExpressionResult, IWatchExpressionResultExtension {
		private boolean hasErrors = false;
		private IValue evalResult;

		void evaluate() {
			String watchExpression = expressionText.trim();
			Node result = null;
			String stackLevel = stackFrame.getStackLevel();
			Kind exprKind = Kind.EVAL;
			if (watchExpression.startsWith("$")) {//$NON-NLS-1$
				ISourceRange enclosingIdentifier = PHPTextSequenceUtilities.getEnclosingIdentifier(watchExpression, 0);
				if (enclosingIdentifier != null && enclosingIdentifier.getLength() == watchExpression.length() + 1) {
					exprKind = Kind.STACK;
				}
			}
			switch (exprKind) {
			case STACK: {
				result = debugTarget.getProperty(watchExpression, stackLevel, 0);
				if (result == null || DBGpResponse.REASON_ERROR.equals(result.getNodeName())) {
					// Check if it is not super global property
					stackLevel = "-1"; //$NON-NLS-1$
					result = debugTarget.getProperty(watchExpression, stackLevel, 0);
				}
				break;
			}
			default:
				result = debugTarget.eval(watchExpression);
				break;
			}
			if (result != null) {
				IVariable tempVar;
				switch (exprKind) {
				case STACK: {
					tempVar = new DBGpStackVariable(debugTarget, result, Integer.valueOf(stackLevel));
					break;
				}
				default:
					tempVar = new DBGpEvalVariable(debugTarget, watchExpression, result);
					break;
				}
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

		@Override
		public IValue getValue() {
			return evalResult;
		}

		@Override
		public boolean hasErrors() {
			return hasErrors;
		}

		@Override
		public String[] getErrorMessages() {
			if (hasErrors) {
				// failed to evaluate expression.
				return new String[] { PHPDebugUIMessages.XDebugWatch_failed };
			}
			return null;
		}

		@Override
		public String getExpressionText() {
			return expressionText;
		}

		@Override
		public DebugException getException() {
			return null;
		}

		@Override
		public IDebugTarget getDebugTarget() {
			return debugTarget;
		}

	}

}
