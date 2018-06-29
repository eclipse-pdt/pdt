/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.utils;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public class WidgetHelper {

	/**
	 * Trims a composite and its vertical and horizontal spacing
	 * 
	 * @param composite
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 * @param vertical
	 * @param horizontal
	 */
	public static void trimComposite(Composite composite, int top, int right, int bottom, int left, int vertical,
			int horizontal) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.marginTop = top;
			layout.marginRight = right;
			layout.marginBottom = bottom;
			layout.marginLeft = left;
			layout.verticalSpacing = vertical;
			layout.horizontalSpacing = horizontal;
		}
	}

	/**
	 * Trims a composite
	 * 
	 * @param composite
	 * @param top
	 * @param right
	 * @param bottom
	 * @param left
	 */
	public static void trimComposite(Composite composite, int top, int right, int bottom, int left) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.marginTop = top;
			layout.marginRight = right;
			layout.marginBottom = bottom;
			layout.marginLeft = left;
		}
	}

	/**
	 * Trims a composite
	 * 
	 * @param composite
	 * @param top
	 * @param rightleft
	 * @param bottom
	 */
	public static void trimComposite(Composite composite, int top, int rightleft, int bottom) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.marginTop = top;
			layout.marginRight = rightleft;
			layout.marginBottom = bottom;
			layout.marginLeft = rightleft;
		}
	}

	/**
	 * Trims a composite
	 * 
	 * @param composite
	 * @param topbottom
	 * @param rightleft
	 */
	public static void trimComposite(Composite composite, int topbottom, int rightleft) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.marginTop = topbottom;
			layout.marginRight = rightleft;
			layout.marginBottom = topbottom;
			layout.marginLeft = rightleft;
		}
	}

	/**
	 * Trims a composite
	 * 
	 * @param composite
	 * @param topbottom
	 * @param rightleft
	 */
	public static void trimComposite(Composite composite, int all) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.marginTop = all;
			layout.marginRight = all;
			layout.marginBottom = all;
			layout.marginLeft = all;
		}
	}

	/**
	 * Sets spacing for a composite
	 * 
	 * @param composite
	 * @param vertical
	 * @param horizontal
	 */
	public static void setSpacing(Composite composite, int vertical, int horizontal) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.verticalSpacing = vertical;
			layout.horizontalSpacing = horizontal;
		}
	}

	/**
	 * Sets padding for a composite
	 * 
	 * @param composite
	 * @param vertical
	 * @param horizontal
	 */
	public static void setMargin(Composite composite, int vertical, int horizontal) {
		Layout raw = composite.getLayout();
		if (raw instanceof GridLayout) {
			GridLayout layout = (GridLayout) raw;
			layout.marginHeight = vertical;
			layout.marginWidth = horizontal;
		}
	}
}
