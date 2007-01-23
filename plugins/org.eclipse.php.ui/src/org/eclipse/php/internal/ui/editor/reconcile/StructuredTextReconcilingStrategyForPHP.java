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
package org.eclipse.php.internal.ui.editor.reconcile;

import org.eclipse.jface.text.reconciler.IReconcileStep;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.wst.sse.ui.internal.reconcile.AbstractStructuredTextReconcilingStrategy;

/**
 * 
 * @author pavery
 */
public class StructuredTextReconcilingStrategyForPHP extends AbstractStructuredTextReconcilingStrategy {

	public StructuredTextReconcilingStrategyForPHP(ISourceViewer sourceViewer) {
		super(sourceViewer);
	}

	public void createReconcileSteps() {

		// the order is:
		// 1. translation step
		// 2. java step
//		if (getFile() != null) {
//			super.setffFirstStep = new ReconcileStepForPHP();
//		}
	}

	/**
	 * @return <code>true</code> if the entire document is validated
	 * for each edit (this strategy can't handle partial document validation).
	 * This will greatly help performance.
	 */
	public boolean isTotalScope() {
		return true;
	}

	protected boolean containsStep(IReconcileStep step) {
		// TODO - Migration to Eclipse 3.2
		return false;
	}
	
	
}