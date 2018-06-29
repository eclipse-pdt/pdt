/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Thierry Blind - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.ast;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.php.ui.editor.SharedASTProvider.WAIT_FLAG;

public class ASTCache {
	private final ReentrantLock fWaitLock = new ReentrantLock(true);
	private final WeakReference<ISourceModule> fInput;
	private STATES fState = STATES.NONE;
	private Program fAST = null;

	enum STATES {
		NONE, STARTED, DONE, CANCELED
	}

	public ASTCache(@NonNull final ISourceModule input) {
		fInput = new WeakReference<>(input);
	}

	public void aboutToBeReconciled(final ISourceModule input) {
		ISourceModule refInput = fInput.get();

		if (refInput == null || input != refInput) {
			return;
		}

		synchronized (this) {
			fState = STATES.STARTED;
			// it seems that we should not provide non-null ASTs when
			// calling getAST() with a WAIT_NO flag after a reconcile
			// process has been started:
			fAST = null;
		}
	}

	public void reconciled(final Program ast, final ISourceModule input, final IProgressMonitor progressMonitor) {
		ISourceModule refInput = fInput.get();

		if (refInput == null || input != refInput) {
			return;
		}

		synchronized (this) {
			if (ast == null) {
				fState = STATES.CANCELED;
				fAST = null;
			} else {
				fState = STATES.DONE;
				fAST = ast;
			}
		}
	}

	public Program getAST(final ISourceModule input, final WAIT_FLAG waitFlag, final IProgressMonitor progressMonitor) {
		if (waitFlag == SharedASTProvider.WAIT_ACTIVE_ONLY) {
			throw new IllegalArgumentException("Flag WAIT_ACTIVE_ONLY is unsupported"); //$NON-NLS-1$
		}

		ISourceModule refInput = fInput.get();

		if (refInput == null || input != refInput) {
			return null;
		}

		synchronized (this) {
			if (waitFlag == SharedASTProvider.WAIT_NO || fState == STATES.DONE) {
				return fAST;
			}
		}

		try {
			if (fWaitLock.tryLock(30, TimeUnit.SECONDS)) {
				boolean isReconciling = false;
				int nbIterations = 300;

				do {
					synchronized (this) {
						if (fState == STATES.DONE) {
							return fAST;
						} else if (fState == STATES.STARTED) {
							isReconciling = true;
						} else {
							nbIterations = 0;
							// same as aboutToBeReconciled(input)
							// but we keep current fAST value
							fState = STATES.STARTED;
						}
					}

					if (isReconciling && nbIterations > 0) {
						Thread.sleep(100);
						nbIterations--;
						if (nbIterations == 0 || fInput.get() == null) {
							// stop the loop
							synchronized (this) {
								fState = STATES.CANCELED;
								fAST = null;
								return fAST;
							}
						}
					}
				} while (isReconciling && nbIterations > 0);

				Program currentAST = ASTUtils.createAST(input, progressMonitor);

				// same as reconciled(currentAST, input, progressMonitor)
				synchronized (this) {
					if (currentAST == null) {
						fState = STATES.CANCELED;
						fAST = null;
					} else {
						fState = STATES.DONE;
						fAST = currentAST;
					}
					return fAST;
				}
			}
		} catch (Throwable e) {
			synchronized (this) {
				fState = STATES.CANCELED;
				fAST = null;
			}
		} finally {
			if (fWaitLock.isHeldByCurrentThread()) {
				fWaitLock.unlock();
			}
		}

		return null;
	}

	public ISourceModule getInput() {
		return fInput.get();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTCache)) {
			return false;
		}
		ISourceModule thisInput = fInput.get();
		ISourceModule otherInput = ((ASTCache) obj).fInput.get();

		if (thisInput == null || otherInput == null || thisInput != otherInput) {
			return false;
		}

		return true;
	}
}
