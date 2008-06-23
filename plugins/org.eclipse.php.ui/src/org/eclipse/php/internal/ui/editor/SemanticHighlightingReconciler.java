/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Scalar;
import org.eclipse.php.internal.core.ast.visitor.ApplyAll;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.SemanticHighlightingManager.HighlightedPosition;
import org.eclipse.php.internal.ui.editor.SemanticHighlightingManager.Highlighting;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * Semantic highlighting reconciler - Background thread implementation.
 *
 * @since 3.0
 */
public class SemanticHighlightingReconciler implements IPhpScriptReconcilingListener {

	/**
	 * Collects positions from the AST.
	 */
	private class PositionCollector extends ApplyAll {

		/** The semantic token */
		private SemanticToken fToken= new SemanticToken();

		/*
		 * @see org.eclipse.jdt.internal.corext.dom.GenericVisitor#visitNode(org.eclipse.jdt.core.dom.ASTNode)
		 */
		protected boolean apply(ASTNode node) {
			if ((node.getFlags() & ASTNode.MALFORMED) == ASTNode.MALFORMED) {
				retainPositions(node.getStart(), node.getLength());
				return false;
			}
			return true;
		}
		
		
		public boolean visit(Scalar node) {
			fToken.update(node);
			for (int i= 0, n= fJobSemanticHighlightings.length; i < n; i++) {
				SemanticHighlighting semanticHighlighting= fJobSemanticHighlightings[i];
				if (fJobHighlightings[i].isEnabled() && semanticHighlighting.consumesLiteral(fToken)) {
					int offset= node.getStart();
					int length= node.getLength();
					if (offset > -1 && length > 0)
						addPosition(offset, length, fJobHighlightings[i]);
					break;
				}
			}
			fToken.clear();
			return false;
		}

		/*
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SimpleName)
		 */
		public boolean visit(Identifier node) {
			fToken.update(node);
			for (int i= 0, n= fJobSemanticHighlightings.length; i < n; i++) {
				SemanticHighlighting semanticHighlighting= fJobSemanticHighlightings[i];
				if (fJobHighlightings[i].isEnabled() && semanticHighlighting.consumes(fToken)) {
					int offset= node.getStart();
					int length= node.getLength();
					if (offset > -1 && length > 0)
						addPosition(offset, length, fJobHighlightings[i]);
					break;
				}
			}
			fToken.clear();
			return false;
		}

		/**
		 * Add a position with the given range and highlighting iff it does not exist already.
		 * @param offset The range offset
		 * @param length The range length
		 * @param highlighting The highlighting
		 */
		private void addPosition(int offset, int length, Highlighting highlighting) {
			boolean isExisting= false;
			// TODO: use binary search
			for (int i= 0, n= fRemovedPositions.size(); i < n; i++) {
				HighlightedPosition position= (HighlightedPosition) fRemovedPositions.get(i);
				if (position == null)
					continue;
				if (position.isEqual(offset, length, highlighting)) {
					isExisting= true;
					fRemovedPositions.set(i, null);
					fNOfRemovedPositions--;
					break;
				}
			}

			if (!isExisting) {
				Position position= fJobPresenter.createHighlightedPosition(offset, length, highlighting);
				fAddedPositions.add(position);
			}
		}

		/**
		 * Retain the positions completely contained in the given range.
		 * @param offset The range offset
		 * @param length The range length
		 */
		private void retainPositions(int offset, int length) {
			// TODO: use binary search
			for (int i= 0, n= fRemovedPositions.size(); i < n; i++) {
				HighlightedPosition position= (HighlightedPosition) fRemovedPositions.get(i);
				if (position != null && position.isContained(offset, length)) {
					fRemovedPositions.set(i, null);
					fNOfRemovedPositions--;
				}
			}
		}
	}

	/** Position collector */
	private PositionCollector fCollector= new PositionCollector();

	/** The Java editor this semantic highlighting reconciler is installed on */
	private PHPStructuredEditor fEditor;
	/** The source viewer this semantic highlighting reconciler is installed on */
	private ISourceViewer fSourceViewer;
	/** The semantic highlighting presenter */
	private SemanticHighlightingPresenter fPresenter;
	/** Semantic highlightings */
	private SemanticHighlighting[] fSemanticHighlightings;
	/** Highlightings */
	private Highlighting[] fHighlightings;

	/** Background job's added highlighted positions */
	private List fAddedPositions= new ArrayList();
	/** Background job's removed highlighted positions */
	private List fRemovedPositions= new ArrayList();
	/** Number of removed positions */
	private int fNOfRemovedPositions;

	/** Background job */
	private Job fJob;
	/** Background job lock */
	private final Object fJobLock= new Object();
	/**
	 * Reconcile operation lock.
	 * @since 3.2
	 */
	private final Object fReconcileLock= new Object();
	/**
	 * <code>true</code> if any thread is executing
	 * <code>reconcile</code>, <code>false</code> otherwise.
	 * @since 3.2
	 */
	private boolean fIsReconciling= false;

	/** The semantic highlighting presenter - cache for background thread, only valid during {@link #reconciled(CompilationUnit, boolean, IProgressMonitor)} */
	private SemanticHighlightingPresenter fJobPresenter;
	/** Semantic highlightings - cache for background thread, only valid during {@link #reconciled(CompilationUnit, boolean, IProgressMonitor)} */
	private SemanticHighlighting[] fJobSemanticHighlightings;
	/** Highlightings - cache for background thread, only valid during {@link #reconciled(CompilationUnit, boolean, IProgressMonitor)} */
	private Highlighting[] fJobHighlightings;

	/*
	 * @see org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#aboutToBeReconciled()
	 */
	public void aboutToBeReconciled() {
		// Do nothing
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#reconciled(CompilationUnit, boolean, IProgressMonitor)
	 */
	public void reconciled(Program ast, boolean forced, IProgressMonitor progressMonitor) {
		// ensure at most one thread can be reconciling at any time
		synchronized (fReconcileLock) {
			if (fIsReconciling)
				return;
			else
				fIsReconciling= true;
		}
		fJobPresenter= fPresenter;
		fJobSemanticHighlightings= fSemanticHighlightings;
		fJobHighlightings= fHighlightings;
		
		try {
			if (fJobPresenter == null || fJobSemanticHighlightings == null || fJobHighlightings == null)
				return;
			
			fJobPresenter.setCanceled(progressMonitor.isCanceled());
			
			if (ast == null || fJobPresenter.isCanceled())
				return;
			
			ASTNode[] subtrees= getAffectedSubtrees(ast);
			if (subtrees.length == 0)
				return;
			
			startReconcilingPositions();
			
			if (!fJobPresenter.isCanceled())
				reconcilePositions(subtrees);
			
			TextPresentation textPresentation= null;
			if (!fJobPresenter.isCanceled())
				// textPresentation= fJobPresenter.createPresentation(fAddedPositions, fRemovedPositions);
			
			if (!fJobPresenter.isCanceled())
				updatePresentation(textPresentation, fAddedPositions, fRemovedPositions);
			
			stopReconcilingPositions();
		} finally {
			fJobPresenter= null;
			fJobSemanticHighlightings= null;
			fJobHighlightings= null;
			synchronized (fReconcileLock) {
				fIsReconciling= false;
			}
		}
	}

	/**
	 * @param node Root node
	 * @return Array of subtrees that may be affected by past document changes
	 */
	private ASTNode[] getAffectedSubtrees(ASTNode node) {
		// TODO: only return nodes which are affected by document changes - would require an 'anchor' concept for taking distant effects into account
		return new ASTNode[] { node };
	}

	/**
	 * Start reconciling positions.
	 */
	private void startReconcilingPositions() {
		fJobPresenter.addAllPositions(fRemovedPositions);
		fNOfRemovedPositions= fRemovedPositions.size();
	}

	/**
	 * Reconcile positions based on the AST subtrees
	 *
	 * @param subtrees the AST subtrees
	 */
	private void reconcilePositions(ASTNode[] subtrees) {
		// FIXME: remove positions not covered by subtrees
		for (int i= 0, n= subtrees.length; i < n; i++)
			subtrees[i].accept(fCollector);
		List oldPositions= fRemovedPositions;
		List newPositions= new ArrayList(fNOfRemovedPositions);
		for (int i= 0, n= oldPositions.size(); i < n; i ++) {
			Object current= oldPositions.get(i);
			if (current != null)
				newPositions.add(current);
		}
		fRemovedPositions= newPositions;
	}

	/**
	 * Update the presentation.
	 *
	 * @param textPresentation the text presentation
	 * @param addedPositions the added positions
	 * @param removedPositions the removed positions
	 */
	private void updatePresentation(TextPresentation textPresentation, List addedPositions, List removedPositions) {
		Runnable runnable= fJobPresenter.createUpdateRunnable(textPresentation, addedPositions, removedPositions);
		if (runnable == null)
			return;

		PHPStructuredEditor editor= fEditor;
		if (editor == null)
			return;

		IWorkbenchPartSite site= editor.getSite();
		if (site == null)
			return;

		Shell shell= site.getShell();
		if (shell == null || shell.isDisposed())
			return;

		Display display= shell.getDisplay();
		if (display == null || display.isDisposed())
			return;

		display.asyncExec(runnable);
	}

	/**
	 * Stop reconciling positions.
	 */
	private void stopReconcilingPositions() {
		fRemovedPositions.clear();
		fNOfRemovedPositions= 0;
		fAddedPositions.clear();
	}

	/**
	 * Install this reconciler on the given editor, presenter and highlightings.
	 * @param editor the editor
	 * @param sourceViewer the source viewer
	 * @param presenter the semantic highlighting presenter
	 * @param semanticHighlightings the semantic highlightings
	 * @param highlightings the highlightings
	 */
	public void install(PHPStructuredEditor editor, ISourceViewer sourceViewer, SemanticHighlightingPresenter presenter, SemanticHighlighting[] semanticHighlightings, Highlighting[] highlightings) {
		fPresenter= presenter;
		fSemanticHighlightings= semanticHighlightings;
		fHighlightings= highlightings;

		fEditor= editor;
		fSourceViewer= sourceViewer;

		fEditor.addReconcileListener(this);
		// TODO check this code 
//		if (fEditor instanceof CompilationUnitEditor) {
//			((CompilationUnitEditor)fEditor).addReconcileListener(this);
//		} else if (fEditor == null) {
//			fSourceViewer.addTextInputListener(this);
//			scheduleJob();
//		}
	}

	/**
	 * Uninstall this reconciler from the editor
	 */
	public void uninstall() {
		if (fPresenter != null)
			fPresenter.setCanceled(true);

		fEditor.removeReconcileListener(this);
		// TODO check this code		
//		if (fEditor != null) {
//			if (fEditor instanceof CompilationUnitEditor)
//				((CompilationUnitEditor)fEditor).removeReconcileListener(this);
//			else
//				fSourceViewer.removeTextInputListener(this);
//			fEditor= null;
//		}

		fSourceViewer= null;
		fSemanticHighlightings= null;
		fHighlightings= null;
		fPresenter= null;
	}
	
	/**
	 * Schedule a background job for retrieving the AST and reconciling the Semantic Highlighting model.
	 */
	private void scheduleJob() {
		final ISourceModule element= (ISourceModule) fEditor.getModelElement();

		synchronized (fJobLock) {
			final Job oldJob= fJob;
			if (fJob != null) {
				fJob.cancel();
				fJob= null;
			}
			
			if (element != null) {
				fJob= new Job(PHPUIMessages.getString("SemanticHighlighting_job")) {
					protected IStatus run(IProgressMonitor monitor) {
						if (oldJob != null) {
							try {
								oldJob.join();
							} catch (InterruptedException e) {
								PHPCorePlugin.log(e);
								return Status.CANCEL_STATUS;
							}
						}
						if (monitor.isCanceled())
							return Status.CANCEL_STATUS;
						Program ast;
						try {
							ast = SharedASTProvider.getAST(element, SharedASTProvider.WAIT_YES, monitor);
						} catch (ModelException e) {
							PHPCorePlugin.log(e);
							return Status.CANCEL_STATUS;
						} catch (IOException e) {
							PHPCorePlugin.log(e);
							return Status.CANCEL_STATUS;
						}
						reconciled(ast, false, monitor);
						synchronized (fJobLock) {
							// allow the job to be gc'ed
							if (fJob == this)
								fJob= null;
						}
						return Status.OK_STATUS;
					}
				};
				fJob.setSystem(true);
				fJob.setPriority(Job.DECORATE);
				fJob.schedule();
			}
		}
	}

	/*
	 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
	 */
	public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
		synchronized (fJobLock) {
			if (fJob != null) {
				fJob.cancel();
				fJob= null;
			}
		}
	}	
	
	/*
	 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
	 */
	public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
		if (newInput != null)
			scheduleJob();
	}	
	
	/**
	 * Refreshes the highlighting.
	 * 
	 * @since 3.2
	 */
	public void refresh() {
		scheduleJob();
	}
	
}
