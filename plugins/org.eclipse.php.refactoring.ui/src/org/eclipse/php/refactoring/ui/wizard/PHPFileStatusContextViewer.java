/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.ltk.internal.ui.refactoring.FileStatusContextViewer;
import org.eclipse.swt.widgets.Composite;

public class PHPFileStatusContextViewer extends FileStatusContextViewer {

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		getSourceViewer().getControl().setFont(JFaceResources.getFont("org.eclipse.wst.sse.ui.textfont")); //$NON-NLS-1$
	}
}
