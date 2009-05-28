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

package org.eclipse.php.astview.views;


import org.eclipse.php.internal.core.ast.nodes.IBinding;
import org.eclipse.swt.graphics.Image;


public abstract class DynamicBindingProperty extends ExceptionAttribute {

	protected static final String N_A= "N/A"; //$NON-NLS-1$
	private final Binding fParent;
	
	private Binding fViewerElement;
	private String fLabel= "<unknown>";
	
	public DynamicBindingProperty(Binding parent) {
		fParent= parent;
	}

	public Object getParent() {
		return fParent;
	}

	public Object[] getChildren() {
		return EMPTY;
	}
	
	public void setViewerElement(Binding viewerElement) {
		if (fViewerElement == viewerElement)
			return;
		
		fViewerElement= viewerElement;
		fException= null;
		IBinding trayBinding= fParent.getBinding();
		StringBuffer buf= new StringBuffer(getName());
		if (viewerElement != null) {
			IBinding viewerBinding= viewerElement.getBinding();
			try {
				String queryResult= executeQuery(viewerBinding, trayBinding);
				buf.append(queryResult);
			} catch (RuntimeException e) {
				fException= e;
				buf.append(e.getClass().getName());
				buf.append(" for \""); //$NON-NLS-1$
				if (viewerBinding == null)
					buf.append("null"); //$NON-NLS-1$
				else
					buf.append('"').append(viewerBinding.getKey());
				buf.append("\" and "); //$NON-NLS-1$
				buf.append(trayBinding.getKey()).append('"');
			}
		} else {
			buf.append(N_A);
		}
		fLabel= buf.toString();
	}
	
	/**
	 * Executes this dynamic binding property's query in a protected environment.
	 * A {@link RuntimeException} thrown by this method is made available via
	 * {@link #getException()}. 
	 * 
	 * @param viewerBinding the binding of the element selected in the AST viewer, or <code>null</code> iff none
	 * @param trayBinding the binding of the element selected in the comparison tray, or <code>null</code> iff none
	 * @return this property's result
	 */
	protected abstract String executeQuery(IBinding viewerBinding, IBinding trayBinding);

	/**
	 * @return a description of the dynamic property
	 */
	protected abstract String getName();

	public String getLabel() {
		return fLabel;
	}

	public Image getImage() {
		return null;
	}
}
