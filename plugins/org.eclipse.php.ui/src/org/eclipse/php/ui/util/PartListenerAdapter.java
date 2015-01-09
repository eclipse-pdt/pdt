/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * This adapter class provides default implementations for the methods described
 * by the <code>IPartListener2</code> interface.
 * <p>
 * Classes that wish to listen to the part changes can extend this class and
 * override only the methods which they are interested in.
 * </p>
 * 
 * @author Bartlomiej Laczkowski
 */
public class PartListenerAdapter implements IPartListener2 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IPartListener2#partInputChanged(org.eclipse.ui.
	 * IWorkbenchPartReference)
	 */
	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

}
