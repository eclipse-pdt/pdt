/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import org.eclipse.dltk.internal.ui.text.CompositeReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.php.internal.ui.editor.validation.PHPReconcilingStrategy;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.internal.reconcile.StructuredRegionProcessor;

public class PHPStructuredRegionProcessor extends StructuredRegionProcessor {

	private PHPStructuredEditor fEditor;
	private PHPReconcilingStrategy fPHPStrategy;

	public PHPStructuredRegionProcessor(ITextEditor editor) {
		super();
		fPHPStrategy = new PHPReconcilingStrategy(editor);
		if (editor instanceof PHPStructuredEditor) {
			fEditor = (PHPStructuredEditor) editor;
		}

	}

	@Override
	public IReconcilingStrategy getReconcilingStrategy(String partitionType) {
		IReconcilingStrategy strategy = super.getReconcilingStrategy(partitionType);
		if (strategy != null) {
			// support SSE extension point
			CompositeReconcilingStrategy compositeReconcilingStrategy = new CompositeReconcilingStrategy();
			compositeReconcilingStrategy
					.setReconcilingStrategies(new IReconcilingStrategy[] { fPHPStrategy, strategy });
			return compositeReconcilingStrategy;
		}

		return fPHPStrategy;
	}

	@Override
	protected void beginProcessing() {
		if (fEditor != null) {
			fEditor.aboutToBeReconciled();
		}
		super.beginProcessing();
	}

	@Override
	protected void initialReconcile() {
		fPHPStrategy.initialReconcile();
		super.initialReconcile();
	}

}
