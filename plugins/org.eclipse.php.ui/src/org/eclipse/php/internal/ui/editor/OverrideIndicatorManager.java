package org.eclipse.php.internal.ui.editor;

/*******************************************************************************
 * Copyright (c) 2000, 2008 Zend, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend, IBM Corporation - initial API and implementation
 *******************************************************************************/

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.text.IScriptReconcilingListener;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.ui.editor.SharedASTProvider;

/**
 * Manages the override and overwrite indicators for
 * the given PHP element and annotation model.
 *
 * @since 3.0
 */
class OverrideIndicatorManager implements IScriptReconcilingListener, IPhpScriptReconcilingListener {

	/**
	 * Overwrite and override indicator annotation.
	 *
	 * @since 3.0
	 */
	class OverrideIndicator extends Annotation {

		private boolean fIsOverwriteIndicator;
		private String fAstNodeKey;

		/**
		 * Creates a new override annotation.
		 *
		 * @param isOverwriteIndicator <code>true</code> if this annotation is
		 *            an overwrite indicator, <code>false</code> otherwise
		 * @param text the text associated with this annotation
		 * @param key the method binding key
		 * @since 3.0
		 */
		OverrideIndicator(boolean isOverwriteIndicator, String text, String key) {
			super(ANNOTATION_TYPE, false, text);
			fIsOverwriteIndicator = isOverwriteIndicator;
			fAstNodeKey = key;
		}

		/**
		 * Tells whether this is an overwrite or an override indicator.
		 *
		 * @return <code>true</code> if this is an overwrite indicator
		 */
		public boolean isOverwriteIndicator() {
			return fIsOverwriteIndicator;
		}

		/**
		 * Opens and reveals the defining method.
		 */
		public void open() {
			//			MessageDialog.openInformation(null, "Message", "TBD");
			// TODO - Open the defining method
			//			Program ast = SharedASTProvider.getAST((ISourceModule) fModelElement, SharedASTProvider.WAIT_ACTIVE_ONLY, null);
			//			if (ast != null) {
			//				ASTNode node = ast.findDeclaringNode(fAstNodeKey);
			//				if (node instanceof MethodDeclaration) {
			//					try {
			//						IMethodBinding methodBinding = ((MethodDeclaration) node).resolveBinding();
			//						IMethodBinding definingMethodBinding = Bindings.findOverriddenMethod(methodBinding, true);
			//						if (definingMethodBinding != null) {
			//							IJavaElement definingMethod = definingMethodBinding.getJavaElement();
			//							if (definingMethod != null) {
			//								JavaUI.openInEditor(definingMethod, true, true);
			//								return;
			//							}
			//						}
			//					} catch (CoreException e) {
			//						ExceptionHandler.handle(e, JavaEditorMessages.OverrideIndicatorManager_open_error_title, JavaEditorMessages.OverrideIndicatorManager_open_error_messageHasLogEntry);
			//						return;
			//					}
			//				}
			//			}
			//			String title = JavaEditorMessages.OverrideIndicatorManager_open_error_title;
			//			String message = JavaEditorMessages.OverrideIndicatorManager_open_error_message;
			//			MessageDialog.openError(JavaPlugin.getActiveWorkbenchShell(), title, message);
		}
	}

	static final String ANNOTATION_TYPE = "org.eclipse.php.ui.overrideIndicator"; //$NON-NLS-1$

	private IAnnotationModel fAnnotationModel;
	private Object fAnnotationModelLockObject;
	private Annotation[] fOverrideAnnotations;
	private IModelElement fModelElement;

	public OverrideIndicatorManager(IAnnotationModel annotationModel, IModelElement modelElement, Program ast) {
		Assert.isNotNull(annotationModel);
		Assert.isNotNull(modelElement);

		fModelElement = modelElement;
		fAnnotationModel = annotationModel;
		fAnnotationModelLockObject = getLockObject(fAnnotationModel);

		updateAnnotations(ast, new NullProgressMonitor());
	}

	/**
	 * Returns the lock object for the given annotation model.
	 *
	 * @param annotationModel the annotation model
	 * @return the annotation model's lock object
	 * @since 3.0
	 */
	private Object getLockObject(IAnnotationModel annotationModel) {
		if (annotationModel instanceof ISynchronizable) {
			Object lock = ((ISynchronizable) annotationModel).getLockObject();
			if (lock != null)
				return lock;
		}
		return annotationModel;
	}

	/**
	 * Updates the override and implements annotations based
	 * on the given AST.
	 *
	 * @param ast the compilation unit AST
	 * @param progressMonitor the progress monitor
	 * @since 3.0
	 */
	protected void updateAnnotations(Program ast, IProgressMonitor progressMonitor) {

		if (ast == null || progressMonitor.isCanceled())
			return;

		final Map<OverrideIndicator, Position> annotationMap = new HashMap<OverrideIndicator, Position>(50);

		ast.accept(new AbstractVisitor() {
			/*
			 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
			 */
			public boolean visit(MethodDeclaration node) {
				IMethodBinding binding = node.resolveMethodBinding();
				if (binding != null) {
					IMethodBinding definingMethod = Bindings.findOverriddenMethod(binding, true);
					if (definingMethod != null) {

						ITypeBinding definingType = definingMethod.getDeclaringClass();
						String qualifiedMethodName = definingType.getName() + "." + binding.getName(); //$NON-NLS-1$ [TODO - Use definingType.getQualifiedName()]

						boolean isImplements = (Modifiers.AccAbstract & definingMethod.getModifiers()) != 0;
						String text;
						if (isImplements)
							text = Messages.format(PHPUIMessages.getString("OverrideIndicatorManager_implements"), qualifiedMethodName);
						else
							text = Messages.format(PHPUIMessages.getString("OverrideIndicatorManager_overrides"), qualifiedMethodName);

						Identifier name = node.getFunction().getFunctionName();
						Position position = new Position(name.getStart(), name.getLength());
						annotationMap.put(new OverrideIndicator(isImplements, text, binding.getKey()), position);
					}
				}
				return true;
			}
		});

		if (progressMonitor.isCanceled())
			return;

		synchronized (fAnnotationModelLockObject) {
			if (fAnnotationModel instanceof IAnnotationModelExtension) {
				((IAnnotationModelExtension) fAnnotationModel).replaceAnnotations(fOverrideAnnotations, annotationMap);
			} else {
				removeAnnotations();
				Iterator<Map.Entry<OverrideIndicator, Position>> iter = annotationMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<OverrideIndicator, Position> mapEntry = iter.next();
					fAnnotationModel.addAnnotation(mapEntry.getKey(), mapEntry.getValue());
				}
			}
			fOverrideAnnotations = (Annotation[]) annotationMap.keySet().toArray(new Annotation[annotationMap.keySet().size()]);
		}
	}

	/**
	 * Removes all override indicators from this manager's annotation model.
	 */
	void removeAnnotations() {
		if (fOverrideAnnotations == null)
			return;

		synchronized (fAnnotationModelLockObject) {
			if (fAnnotationModel instanceof IAnnotationModelExtension) {
				((IAnnotationModelExtension) fAnnotationModel).replaceAnnotations(fOverrideAnnotations, null);
			} else {
				for (int i = 0, length = fOverrideAnnotations.length; i < length; i++)
					fAnnotationModel.removeAnnotation(fOverrideAnnotations[i]);
			}
			fOverrideAnnotations = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.text.IScriptReconcilingListener#aboutToBeReconciled()
	 */
	public void aboutToBeReconciled() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.text.IScriptReconcilingListener#reconciled(org.eclipse.dltk.core.ISourceModule, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void reconciled(ISourceModule sourceModule, boolean forced, IProgressMonitor progressMonitor) {
		Program ast;
		try {
			ast = SharedASTProvider.getAST(sourceModule, SharedASTProvider.WAIT_ACTIVE_ONLY, null);
			if (ast != null) {
				updateAnnotations(ast, progressMonitor);
			}
		} catch (ModelException e) {
			Logger.logException(e);
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.editor.IPhpScriptReconcilingListener#reconciled(org.eclipse.php.internal.core.ast.nodes.Program, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void reconciled(Program ast, boolean forced, IProgressMonitor progressMonitor) {
		if (ast != null) {
			updateAnnotations(ast, progressMonitor);
		}
	}
}
