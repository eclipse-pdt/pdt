/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.outline;

import java.util.Vector;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class PHPOutlineContentProvider implements ITreeContentProvider {

	// outline tree viewer
	private TreeViewer fOutlineViewer;

	public PHPOutlineContentProvider(TreeViewer viewer) {
		super();
		fOutlineViewer = viewer;
		inputChanged(fOutlineViewer, null, null);
	}

	// private Object[] NO_CLASS = new Object[] { new NoClassElement() };
	private ElementChangedListener fListener;

	public void dispose() {
		if (fListener != null) {
			DLTKCore.removeElementChangedListener(fListener);
			fListener = null;
		}
	}

	protected IModelElement[] filter(IModelElement[] children) {
		boolean initializers = false;
		for (int i = 0; i < children.length; i++) {
			if (matches(children[i])) {
				initializers = true;
				break;
			}
		}

		if (!initializers) {
			return children;
		}

		Vector<IModelElement> v = new Vector<IModelElement>();
		for (int i = 0; i < children.length; i++) {
			if (matches(children[i])) {
				continue;
			}
			v.addElement(children[i]);
		}

		IModelElement[] result = new IModelElement[v.size()];
		v.copyInto(result);
		return result;
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof IParent) {
			IParent c = (IParent) parent;
			try {
				return filter(c.getChildren());
			} catch (ModelException x) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=38341
				// don't log NotExist exceptions as this is a valid case
				// since we might have been posted and the element
				// removed in the meantime.
				if (DLTKCore.DEBUG || !x.isDoesNotExist()) {
					DLTKUIPlugin.log(x);
				}
			}
		}
		return PHPContentOutlineConfiguration.NO_CHILDREN;
	}

	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	public Object getParent(Object child) {
		if (child instanceof IModelElement) {
			IModelElement e = (IModelElement) child;
			return e.getParent();
		}
		return null;
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof IParent) {
			IParent c = (IParent) parent;
			try {
				IModelElement[] children = filter(c.getChildren());
				return (children != null && children.length > 0);
			} catch (ModelException x) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=38341
				// don't log NotExist exceptions as this is a valid case
				// since we might have been posted and the element
				// removed in the meantime.
				if (DLTKUIPlugin.isDebug() || !x.isDoesNotExist()) {
					DLTKUIPlugin.log(x);
				}
			}
		}
		return false;
	}

	/*
	 * @see IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

//		boolean isCU = (newInput instanceof ISourceModule);

		if (/*isCU &&*/fListener == null) {
			fListener = new ElementChangedListener();
			DLTKCore.addElementChangedListener(fListener);
		} else if (/*!isCU &&*/fListener != null) {
			DLTKCore.removeElementChangedListener(fListener);
			fListener = null;
		}
	}

	public boolean isDeleted(Object o) {
		return false;
	}

	protected boolean matches(IModelElement element) {
		if (element.getElementType() == IModelElement.METHOD) {
			String name = element.getElementName();
			return (name != null && name.indexOf('<') >= 0);
		}
		// Filter out non-class variables:
		if (element.getElementType() == IModelElement.FIELD) {
			IField field = (IField) element;
			try {
				if ((field.getFlags() & Modifiers.AccConstant) != 0) {
					return false;
				}
			} catch (ModelException e) {
			}
			return (element.getParent().getElementType() != IModelElement.TYPE); 
		}
		return false;
	}

	/**
	 * The element change listener of the java outline viewer.
	 * 
	 * @see IElementChangedListener
	 */
	protected class ElementChangedListener implements IElementChangedListener {

		public void elementChanged(final ElementChangedEvent e) {

			Control control = fOutlineViewer.getControl();
			if (control == null || control.isDisposed()) {
				return;
			}

			Display d = control.getDisplay();
			if (d != null) {
				d.asyncExec(new Runnable() {
					public void run() {
						IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
						if (activeEditor instanceof PHPStructuredEditor) {
							IModelElement base = ((PHPStructuredEditor) activeEditor).getModelElement();

							IModelElementDelta delta = findElement(base, e.getDelta());
							if (delta != null && fOutlineViewer != null) {
								fOutlineViewer.refresh();
								//								fOutlineViewer.reconcile(delta);
							}
						}
					}
				});
			}
		}

		protected IModelElementDelta findElement(IModelElement unit, IModelElementDelta delta) {

			if (delta == null || unit == null) {
				return null;
			}

			IModelElement element = delta.getElement();

			if (unit.equals(element)) {
				if (isPossibleStructuralChange(delta)) {
					return delta;
				}
				return null;
			}

			if (element.getElementType() > IModelElement.SOURCE_MODULE) {
				return null;
			}

			IModelElementDelta[] children = delta.getAffectedChildren();
			if (children == null || children.length == 0) {
				return null;
			}

			for (int i = 0; i < children.length; i++) {
				IModelElementDelta d = findElement(unit, children[i]);
				if (d != null) {
					return d;
				}
			}

			return null;
		}

		private boolean isPossibleStructuralChange(IModelElementDelta cuDelta) {
			if (cuDelta.getKind() != IModelElementDelta.CHANGED) {
				return true; // add or remove
			}
			int flags = cuDelta.getFlags();
			if ((flags & IModelElementDelta.F_CHILDREN) != 0) {
				return true;
			}
			return (flags & (IModelElementDelta.F_CONTENT | IModelElementDelta.F_FINE_GRAINED)) == IModelElementDelta.F_CONTENT;
		}
	}

}
