/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.coverage;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Code coverage label provider.
 */
public class CodeCoverageLabelProvider extends AppearanceAwareLabelProvider
		implements ITableLabelProvider, ITableColorProvider, ITableFontProvider {
	private CodeCoverageContentProvider cProvider;

	public CodeCoverageLabelProvider(CodeCoverageContentProvider cProvider) {
		super(PHPUiPlugin.getDefault().getPreferenceStore());
		this.cProvider = cProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	public Image getColumnImage(Object element, final int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (element instanceof String) {
				return PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_CUNIT);
			}
			return super.getImage(element);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang
	 * .Object, int)
	 */
	public String getColumnText(Object element, final int columnIndex) {
		CodeCoverageResult coverageResult = cProvider.getCodeCoverageResult(element);
		switch (columnIndex) {
		case 0:
			if (element instanceof String) {
				return (String) element;
			}
			StringBuffer text = new StringBuffer(super.getText(element));
			if (coverageResult.getFiles() > 1 || cProvider.hasChildren(element)) {
				text.append(MessageFormat.format(" ({0})", new Object[] { String.valueOf(coverageResult.getFiles()) })); //$NON-NLS-1$
			}
			return text.toString();
		case 1:
			float percentage = coverageResult.getPercentage();
			if (Float.isNaN(percentage)) {
				return null;
			}
			return MessageFormat.format("{0,number,percent} ({1}/{2}/{3})", new Object[] { //$NON-NLS-1$
					new Float(coverageResult.getPercentage()), String.valueOf(coverageResult.getCovered()),
					String.valueOf(coverageResult.getSignificant()), String.valueOf(coverageResult.getLines()) });
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableColorProvider#getBackground(java.lang
	 * .Object, int)
	 */
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableColorProvider#getForeground(java.lang
	 * .Object, int)
	 */
	public Color getForeground(Object element, int columnIndex) {
		if (columnIndex == 1) {
			if (element instanceof ISourceModule || element instanceof IFile) {
				return JFaceColors.getHyperlinkText(Display.getCurrent());
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableFontProvider#getFont(java.lang.Object,
	 * int)
	 */
	public Font getFont(Object element, int columnIndex) {
		if (columnIndex == 1) {
			if (element instanceof ISourceModule || element instanceof IFile) {
				return JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
			}
		}
		return JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT);
	}
}