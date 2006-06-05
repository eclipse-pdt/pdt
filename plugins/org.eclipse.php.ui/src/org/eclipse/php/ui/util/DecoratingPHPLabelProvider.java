/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

public class DecoratingPHPLabelProvider extends DecoratingLabelProvider implements IColorProvider {

	/**
	 * Decorating label provider for PHP. Combines a PHPUILabelProvider
	 * with problem and override indicuator with the workbench decorator (label
	 * decorator extension point).
	 */
	public DecoratingPHPLabelProvider(PHPUILabelProvider labelProvider) {
		this(labelProvider, true);
	}

	/**
	 * Decorating label provider for PHP. Combines a PHPUILabelProvider
	 * (if enabled with problem indicator) with the workbench
	 * decorator (label decorator extension point).
	 */
	public DecoratingPHPLabelProvider(PHPUILabelProvider labelProvider, boolean errorTick) {
		super(labelProvider, PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator());
		if (errorTick) {
			labelProvider.addLabelDecorator(new ProblemsLabelDecorator(null));
		}
	}

	public Color getForeground(Object element) {
		// label provider is a PHPUILabelProvider
		return ((IColorProvider) getLabelProvider()).getForeground(element);
	}

	public Color getBackground(Object element) {
		// label provider is a PHPUILabelProvider
		return ((IColorProvider) getLabelProvider()).getBackground(element);
	}

}
