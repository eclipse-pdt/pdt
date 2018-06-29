/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.profile.core.data.ProfilerClassData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;
import org.eclipse.swt.graphics.Image;

/**
 * Execution statistics label provider.
 */
public class ExecutionStatisticsLabelProvider implements ITableLabelProvider {

	private Image fFileImage;
	private Image fClassImage;
	private Image fFunctionImage;
	private IPreferenceStore fStore;

	private NumberFormat fNumberFormatter = new DecimalFormat("0.000000"); //$NON-NLS-1$

	public ExecutionStatisticsLabelProvider() {
		fFileImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_OBJS_CUNIT);
		fClassImage = DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
		fFunctionImage = PHPUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_MISC_PUBLIC);
		fStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		TreeElement item = (TreeElement) element;
		if (columnIndex == 0) {
			Object data = item.getData();
			if (data instanceof ProfilerFileData) {
				return fFileImage;
			} else if (data instanceof ProfilerFunctionData) {
				return fFunctionImage;
			} else if (data instanceof ProfilerClassData) {
				return fClassImage;
			}
		}
		return null;
	}

	private String getPercents(double sum, double val) {
		val = val / sum * 100.0;
		val = (Math.round(val * 100)) / 100.0;
		return NLS.bind(PHPProfileUIMessages.getString("ExecutionStatisticsLabelProvider_0"), //$NON-NLS-1$
				Double.toString(val));
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		TreeElement item = (TreeElement) element;
		Object data = item.getData();

		boolean showAsPercentage = fStore.getBoolean(PreferenceKeys.EXECUTION_VIEW_SHOW_AS_PERCENTAGE);

		if (data instanceof ProfilerFileData) {
			ProfilerFileData fileData = (ProfilerFileData) data;
			switch (columnIndex) {
			case 0:
				return new File(fileData.getName()).getName();
			case 5:
				return fNumberFormatter.format(fileData.getTotalOwnTime());
			}
		} else if (data instanceof ProfilerFunctionData) {
			ProfilerFunctionData funcData = (ProfilerFunctionData) data;
			switch (columnIndex) {
			case 0:
				return funcData.getFunctionName();
			case 1:
				return Integer.toString(funcData.getCallsCount());
			case 2:
				if (funcData.getCallsCount() > 0) {
					double averageOwnTime = funcData.getOwnTime() / funcData.getCallsCount();
					if (showAsPercentage) {
						return getPercents(funcData.getTotalTime(), averageOwnTime);
					}
					return fNumberFormatter.format(averageOwnTime);
				}
			case 3:
				double ownTime = funcData.getOwnTime();
				if (showAsPercentage) {
					return getPercents(funcData.getTotalTime(), ownTime);
				}
				return fNumberFormatter.format(ownTime);
			case 4:
				double othersTime = funcData.getTotalTime() - funcData.getOwnTime();
				if (showAsPercentage) {
					return getPercents(funcData.getTotalTime(), othersTime);
				}
				return fNumberFormatter.format(othersTime);
			case 5:
				return fNumberFormatter.format(funcData.getTotalTime());
			}
		} else if (data instanceof ProfilerClassData) {
			ProfilerClassData classData = (ProfilerClassData) data;
			switch (columnIndex) {
			case 0:
				return classData.getName();
			case 5:
				return fNumberFormatter.format(classData.getTotalOwnTime());
			}
		}
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}
}
