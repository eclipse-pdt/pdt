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
package org.eclipse.php.internal.ui.compare;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Description: Creates the voewer for PHP
 * 
 * @author Roy, 2007
 */
public class PhpMergeViewerCreator implements IViewerCreator {

	/*
	 * @see
	 * org.eclipse.compare.IViewerCreator#createViewer(org.eclipse.swt.widgets
	 * .Composite, org.eclipse.compare.CompareConfiguration)
	 */

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		// return new TextMergeViewer(parent, config);
		return new PhpMergeViewer(parent, config);
	}

}
