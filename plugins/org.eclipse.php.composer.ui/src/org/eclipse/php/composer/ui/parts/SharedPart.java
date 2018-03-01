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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class SharedPart {
	private boolean enabled = true;

	public void setEnabled(boolean enabled) {
		if (enabled != this.enabled) {
			this.enabled = enabled;
			updateEnabledState();
		}
	}

	public abstract void createControl(Composite parent, int style, int span, FormToolkit toolkit);

	public boolean isEnabled() {
		return enabled;
	}

	protected void updateEnabledState() {
	}

	protected Composite createComposite(Composite parent, FormToolkit toolkit) {
		if (toolkit == null) {
			return new Composite(parent, SWT.NULL);
		}
		return toolkit.createComposite(parent);
	}

	protected Label createEmptySpace(Composite parent, int span, FormToolkit toolkit) {
		Label label;
		if (toolkit != null) {
			label = toolkit.createLabel(parent, null);
		} else {
			label = new Label(parent, SWT.NULL);
		}
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		gd.horizontalSpan = span;
		gd.widthHint = 0;
		gd.heightHint = 0;
		label.setLayoutData(gd);
		return label;
	}
}
