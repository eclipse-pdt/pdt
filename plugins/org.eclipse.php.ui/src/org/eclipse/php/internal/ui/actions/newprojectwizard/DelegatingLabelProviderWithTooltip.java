/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions.newprojectwizard;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.ViewerColumn;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

/**
 * @since 3.5
 *
 */
public abstract class DelegatingLabelProviderWithTooltip extends ColumnLabelProvider {
	/**
	 * 
	 */
	private final ILabelDecorator decorator;
	ILabelProvider wrappedLabelProvider;

	/**
	 * @param decorator
	 */
	DelegatingLabelProviderWithTooltip(ILabelProvider wrappedLabelProvider,
			ILabelDecorator decorator) {
		this.wrappedLabelProvider = wrappedLabelProvider;
		this.decorator = decorator;
		wrappedLabelProvider.addListener(new ILabelProviderListener() {
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				fireLabelProviderChanged(event);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.CellLabelProvider#initialize(org.eclipse.jface
	 * .viewers.ColumnViewer, org.eclipse.jface.viewers.ViewerColumn)
	 */
	protected void initialize(ColumnViewer viewer, ViewerColumn column) {
		super.initialize(viewer, column);
		if (decorator != null) {
			ColumnViewerToolTipSupport.enableFor(viewer);
		}
	}

	protected void fireLabelProviderChanged(LabelProviderChangedEvent event) {
		super.fireLabelProviderChanged(event);
	}

	public String getText(Object element) {
		return wrappedLabelProvider.getText(element);
	}

	public Image getImage(Object element) {
		return wrappedLabelProvider.getImage(element);
	}

	public Font getFont(Object element) {
		if (wrappedLabelProvider instanceof IFontProvider) {
			return ((IFontProvider) wrappedLabelProvider).getFont(element);
		}
		return null;
	}

	public Color getForeground(Object element) {
		if (wrappedLabelProvider instanceof IColorProvider) {
			return ((IColorProvider) wrappedLabelProvider).getForeground(element);
		}
		return null;
	}

	public Color getBackground(Object element) {
		if (wrappedLabelProvider instanceof IColorProvider) {
			return ((IColorProvider) wrappedLabelProvider).getBackground(element);
		}
		return null;
	}

	public String getToolTipText(Object element) {
		if (decorator == null) {
			return null;
		}
		String text = getText(element);
		element = unwrapElement(element);
		return decorator.decorateText(text, element);
	}

	/**
	 * Returns the element that will be used to determine the bundle id. In most
	 * cases, this method can just return the provided element. Sometimes, it
	 * might be necessary to return a nested object, or an
	 * IConfigurationElement.
	 * 
	 * @param element
	 * @return the element, or a client object wrapped by element, or an
	 *         IConfigurationElement
	 */
	protected abstract Object unwrapElement(Object element);

	public void dispose() {
		wrappedLabelProvider.dispose();
		super.dispose();
	}
}