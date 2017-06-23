/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
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

		reset();
	}

	public void reconciled(final Program ast, final ISourceModule input, final IProgressMonitor progressMonitor) {
		ISourceModule refInput = fInput.get();

		if (refInput == null || input != refInput) {
			return;
		}

		synchronized (this) {
			fAST = ast;
			fState = STATES.DONE;
		}
	}

	public void reset() {
		// do not reset when an AST is under construction
		if (fWaitLock.tryLock()) {
			synchronized (this) {
				fAST = null;
				fState = STATES.NONE;
			}
			fWaitLock.unlock();
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
				synchronized (this) {
					if (fState == STATES.DONE) {
						return fAST;
					}
					fState = STATES.STARTED;
				}

				Program currentAST = ASTUtils.createAST(input, progressMonitor);

				synchronized (this) {
					if (currentAST == null) {
						fAST = null;
						fState = STATES.CANCELED;
					} else {
						fAST = currentAST;
						fState = STATES.DONE;
					}
					return fAST;
				}
			}
		} catch (Throwable e) {
			synchronized (this) {
				fAST = null;
				fState = STATES.CANCELED;
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
