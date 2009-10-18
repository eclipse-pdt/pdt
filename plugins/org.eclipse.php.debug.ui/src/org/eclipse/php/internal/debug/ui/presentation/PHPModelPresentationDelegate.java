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
package org.eclipse.php.internal.debug.ui.presentation;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;

/**
 * A delegate class for all IDebugModelPresentation that are registereed to the
 * phpDebugModelPresentation extention point.
 * 
 * @author shalom
 */
public class PHPModelPresentationDelegate implements IDebugModelPresentation {

	private IDebugModelPresentation bestMatchPresentation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse
	 * .debug.core.model.IValue, org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener) {
		getPresentation().computeDetail(value, listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		return getPresentation().getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		return getPresentation().getText(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.String
	 * , java.lang.Object)
	 */
	public void setAttribute(String attribute, Object value) {
		getPresentation().setAttribute(attribute, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		getPresentation().addListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		getPresentation().dispose();
		bestMatchPresentation = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		return getPresentation().isLabelProperty(element, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		getPresentation().removeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.
	 * IEditorInput, java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element) {
		return getPresentation().getEditorId(input, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element) {
		return getPresentation().getEditorInput(element);
	}

	// Returns the best match presentation.
	private IDebugModelPresentation getPresentation() {
		if (bestMatchPresentation == null) {
			bestMatchPresentation = PHPModelPresentationRegistry
					.getBestMatchPresentation();
		}
		return bestMatchPresentation;
	}
}
