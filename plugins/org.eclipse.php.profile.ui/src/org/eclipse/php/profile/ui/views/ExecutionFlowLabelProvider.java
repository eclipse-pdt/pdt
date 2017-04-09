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
package org.eclipse.php.profile.ui.views;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.swt.graphics.Image;

/**
 * Execution flow label provider.
 */
public class ExecutionFlowLabelProvider implements ITableLabelProvider {

	DecimalFormat fDecimalFormat = new DecimalFormat(
			"#0.0#", new DecimalFormatSymbols(new Locale("en"))); //$NON-NLS-1$ //$NON-NLS-2$

	private Image fFunctionImage;
	private Image fFileImage;

	public ExecutionFlowLabelProvider() {
		fFunctionImage = PHPUiPlugin.getImageDescriptorRegistry().get(
				PHPPluginImages.DESC_MISC_PUBLIC);
		fFileImage = PHPUiPlugin.getImageDescriptorRegistry().get(
				PHPPluginImages.DESC_OBJS_CUNIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang
	 * .Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return fFunctionImage;
		case 1:
			return fFileImage;
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
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof ExecutionFlowTreeElement) {
			ExecutionFlowTreeElement executionFlowElement = (ExecutionFlowTreeElement) element;
			ProfilerFunctionData functionData = (ProfilerFunctionData) executionFlowElement
					.getData();
			if (functionData != null) {
				switch (columnIndex) {
				case 0:
					return functionData.toString();
				case 1:
					return functionData.getFileName();
				case 2:
					return NLS
							.bind(
									PHPProfileUIMessages
											.getString("ExecutionFlowLabelProvider_0"), Double.toString(executionFlowElement.getTimePercentage())); //$NON-NLS-1$
				case 3:
					return fDecimalFormat.format(executionFlowElement
							.getDuration());
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.
	 * jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang
	 * .Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse
	 * .jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
}
