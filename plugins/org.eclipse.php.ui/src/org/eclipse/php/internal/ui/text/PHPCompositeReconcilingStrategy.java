/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.internal.ui.text.CompositeReconcilingStrategy;
import org.eclipse.dltk.internal.ui.text.IProblemRequestorExtension;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Reconciling strategy for PHP code. This is a composite strategy containing
 * the regular php model reconciler and the comment spelling strategy.
 *
 */
public class PHPCompositeReconcilingStrategy extends CompositeReconcilingStrategy {

	private ITextEditor fEditor;
	private PHPReconcilingStrategy fPHPStrategy;

	/**
	 * Creates a new PHP reconciling strategy.
	 *
	 * @param editor
	 *            the editor of the strategy's reconciler
	 * @param documentPartitioning
	 *            the document partitioning this strategy uses for configuration
	 */
	public PHPCompositeReconcilingStrategy(ITextEditor editor, String documentPartitioning) {
		fEditor = editor;
		fPHPStrategy = new PHPReconcilingStrategy(editor);
		setReconcilingStrategies(new IReconcilingStrategy[] { fPHPStrategy });
	}

	/**
	 * Returns the problem requestor for the editor's input element.
	 *
	 * @return the problem requestor for the editor's input element
	 */
	private IProblemRequestorExtension getProblemRequestorExtension() {
		if (fEditor == null) {
			return null;
		}

		IDocumentProvider p = fEditor.getDocumentProvider();
		if (p == null) {
			p = DLTKUIPlugin.getDefault().getSourceModuleDocumentProvider();
		}
		IAnnotationModel m = p.getAnnotationModel(fEditor.getEditorInput());
		if (m instanceof IProblemRequestorExtension)
			return (IProblemRequestorExtension) m;
		return null;
	}

	@Override
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		try {
			IProblemRequestorExtension e = getProblemRequestorExtension();
			if (e != null) {
				try {
					e.beginReportingSequence();
					super.reconcile(dirtyRegion, subRegion);
				} finally {
					e.endReportingSequence();
				}
			} else {
				super.reconcile(dirtyRegion, subRegion);
			}
		} finally {
			reconciled();
		}
	}

	/*
	 * @see
	 * org.eclipse.jface.text.reconciler.CompositeReconcilingStrategy#reconcile(
	 * org.eclipse.jface.text.IRegion)
	 */
	@Override
	public void reconcile(IRegion partition) {
		try {
			IProblemRequestorExtension e = getProblemRequestorExtension();
			if (e != null) {
				try {
					e.beginReportingSequence();
					super.reconcile(partition);
				} finally {
					e.endReportingSequence();
				}
			} else {
				super.reconcile(partition);
			}
		} finally {
			reconciled();
		}
	}

	/**
	 * Tells this strategy whether to inform its listeners.
	 *
	 * @param notify
	 *            <code>true</code> if listeners should be notified
	 */
	public void notifyListeners(boolean notify) {
		fPHPStrategy.notifyListeners(notify);
	}

	@Override
	public void initialReconcile() {
		try {
			IProblemRequestorExtension e = getProblemRequestorExtension();
			if (e != null) {
				try {
					e.beginReportingSequence();
					super.initialReconcile();
				} finally {
					e.endReportingSequence();
				}
			} else {
				super.initialReconcile();
			}
		} finally {
			reconciled();
		}
	}

	/**
	 * Called before reconciling is started.
	 *
	 */
	public void aboutToBeReconciled() {
		fPHPStrategy.aboutToBeReconciled();

	}

	/**
	 * Called when reconcile has finished.
	 *
	 */
	private void reconciled() {
		fPHPStrategy.reconciled();
	}
}
