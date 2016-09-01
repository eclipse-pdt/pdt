/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.parts;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class StructuredViewerPart extends SharedPartWithButtons {

	private StructuredViewer fViewer;

	private Point fMinSize;

	public StructuredViewerPart(String[] buttonLabels) {
		super(buttonLabels);
	}

	public StructuredViewer getViewer() {
		return fViewer;
	}

	public Control getControl() {
		return fViewer.getControl();
	}

	protected void createMainControl(Composite parent, int style, int span, FormToolkit toolkit) {
		fViewer = createStructuredViewer(parent, style, toolkit);
		Control control = fViewer.getControl();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = span;
		control.setLayoutData(gd);
		applyMinimumSize();
	}

	public void setMinimumSize(int width, int height) {
		fMinSize = new Point(width, height);
		if (fViewer != null)
			applyMinimumSize();
	}

	private void applyMinimumSize() {
		if (fMinSize != null) {
			GridData gd = (GridData) fViewer.getControl().getLayoutData();
			gd.widthHint = fMinSize.x;
			gd.heightHint = fMinSize.y;
		}
	}

	protected void updateEnabledState() {
		getControl().setEnabled(isEnabled());
		super.updateEnabledState();
	}

	protected abstract StructuredViewer createStructuredViewer(Composite parent, int style, FormToolkit toolkit);
}
