/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.ltk.internal.ui.refactoring.FileStatusContextViewer;
import org.eclipse.swt.widgets.Composite;

public class PHPFileStatusContextViewer extends FileStatusContextViewer {

	public void createControl(Composite parent) {
		super.createControl(parent);
		getSourceViewer().getControl().setFont(
				JFaceResources.getFont("org.eclipse.wst.sse.ui.textfont")); //$NON-NLS-1$
	}
}
