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
package org.eclipse.php.internal.ui.wizards.fields;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author seva Dialog field describing a separator.
 */
public class Separator extends DialogField {

	protected static GridData gridDataForSeperator(final int span,
			final int height) {
		final GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.heightHint = height;
		gd.horizontalSpan = span;
		return gd;
	}

	private Label fSeparator;

	private int fStyle;

	public Separator() {
		this(SWT.NONE);
	}

	// ------- layout helpers

	/**
	 * @param style
	 *            of the separator. See <code>Label</code> for possible styles.
	 */
	public Separator(final int style) {
		super();
		fStyle = style;
	}

	/*
	 * @see DialogField#doFillIntoGrid
	 */
	public Control[] doFillIntoGrid(final Composite parent, final int nColumns) {
		return doFillIntoGrid(parent, nColumns, 4);
	}

	/**
	 * Creates the separator and fills it in a MGridLayout.
	 * 
	 * @param height
	 *            The height of the separator
	 */
	public Control[] doFillIntoGrid(final Composite parent, final int nColumns,
			final int height) {
		assertEnoughColumns(nColumns);

		final Control separator = getSeparator(parent);
		separator.setLayoutData(gridDataForSeperator(nColumns, height));

		return new Control[] { separator };
	}

	/*
	 * @see DialogField#getNumberOfControls
	 */
	public int getNumberOfControls() {
		return 1;
	}

	// ------- ui creation

	/**
	 * Creates or returns the created separator.
	 * 
	 * @param parent
	 *            The parent composite or <code>null</code> if the widget has
	 *            already been created.
	 */
	public Control getSeparator(final Composite parent) {
		if (fSeparator == null) {
			assertCompositeNotNull(parent);
			fSeparator = new Label(parent, fStyle);
		}
		return fSeparator;
	}

}
