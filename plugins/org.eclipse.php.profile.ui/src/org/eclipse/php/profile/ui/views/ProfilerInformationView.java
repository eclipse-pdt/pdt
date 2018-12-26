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
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;
import org.eclipse.php.profile.core.engine.IProfileSessionListener;
import org.eclipse.php.profile.core.engine.ProfileSessionsManager;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.utils.ChartUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Profiler information view.
 */
public class ProfilerInformationView extends AbstractProfilerView implements IProfileSessionListener {

	private DecimalFormat fDecimalFormat = new DecimalFormat("#0.0##", //$NON-NLS-1$
			new DecimalFormatSymbols(new Locale("en"))); //$NON-NLS-1$

	private ScrolledForm fForm;
	private Label fUriLabel;
	private Label fQueryLabel;
	private Label fPathLabel;
	private Label fTotalTimeLabel;
	private Label fFileCountLabel;
	private Label fDateLabel;
	private ProfilerDB fProfilerDB;

	private ChartViewer fChartViewer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		createForm(parent);
		ProfileSessionsManager.addProfileSessionListener(this);
		setInput(ProfileSessionsManager.getCurrent());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IPHPHelpContextIds.PROFILER_INFORMATION_VIEW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		ProfileSessionsManager.removeProfileSessionListener(this);
		super.dispose();
	}

	private void createForm(Composite parent) {

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		fForm = toolkit.createScrolledForm(parent);
		FillLayout layout = new FillLayout();
		layout.type = SWT.HORIZONTAL;
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.spacing = 10;
		fForm.getBody().setLayout(layout);

		createGeneralSection(toolkit);
		createChartSection(toolkit);
	}

	private void createGeneralSection(FormToolkit toolkit) {
		Section section = toolkit.createSection(fForm.getBody(), SWT.DEFAULT);
		section.setText(PHPProfileUIMessages.getString("ProfilerInformationView_0")); //$NON-NLS-1$

		Composite sectionClient = new Composite(section, SWT.NONE);
		toolkit.paintBordersFor(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 10;
		sectionClient.setLayout(layout);

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;

		fUriLabel = toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
		fUriLabel.setLayoutData(data);

		fQueryLabel = toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
		fQueryLabel.setLayoutData(data);

		fPathLabel = toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
		fPathLabel.setLayoutData(data);

		fTotalTimeLabel = toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
		fTotalTimeLabel.setLayoutData(data);

		fFileCountLabel = toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
		fFileCountLabel.setLayoutData(data);

		fDateLabel = toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
		fDateLabel.setLayoutData(data);

		section.setClient(sectionClient);
		toolkit.adapt(sectionClient);
	}

	private void createChartSection(FormToolkit toolkit) {
		Section section = toolkit.createSection(fForm.getBody(), SWT.DEFAULT);
		section.setText(PHPProfileUIMessages.getString("ProfilerInformationView_1")); //$NON-NLS-1$

		Composite sectionClient = new Composite(section, SWT.NONE);
		toolkit.paintBordersFor(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		sectionClient.setLayout(layout);

		fChartViewer = new ChartViewer(sectionClient, SWT.NONE);
		toolkit.adapt(fChartViewer, false, false);
		GridData data = new GridData();
		data.widthHint = 300;
		data.heightHint = 400;
		data.grabExcessHorizontalSpace = true;
		fChartViewer.setLayoutData(data);

		section.setClient(sectionClient);
		toolkit.adapt(sectionClient);
	}

	private void redrawPieChart(ProfilerDB db) {
		if (db != null) {
			ProfilerGlobalData globalData = db.getGlobalData();

			String[] fileNames = globalData.getFileNames();
			int fileCount = fileNames.length;
			ArrayList<FileTime> fileTimeValues = new ArrayList<>();
			// create FileTime values
			for (int i = 0; i < fileCount; i++) {
				ProfilerFileData fileData = db.getFileData(fileNames[i]);
				if (fileData != null) {
					fileTimeValues.add(new FileTime(fileData.getTotalOwnTimeInMilli(), fileData.getName()));
				}
			}
			// recalculate the number of FileTime values
			fileCount = fileTimeValues.size();
			FileTime[] allValues = fileTimeValues.toArray(new FileTime[fileCount]);
			// sort them
			Arrays.sort(allValues);

			int slicesLimit = 5;

			int actualNumber = Math.min(slicesLimit, fileCount);
			FileTime[] selectedFiles = new FileTime[actualNumber];
			// creating the new array of values
			int index = 0;
			for (int i = fileCount - 1; i >= 0; i--) {
				if (index >= slicesLimit - 1 && fileCount != slicesLimit) { // sum
																			// the
																			// other
																			// values
																			// all
																			// together
																			// -
																			// the
																			// "Other"
					if (selectedFiles[slicesLimit - 1] == null) {
						selectedFiles[slicesLimit - 1] = new FileTime(allValues[i].getTime(),
								NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_8"), //$NON-NLS-1$
										Integer.toString(fileCount - slicesLimit + 1)));
					} else {
						selectedFiles[slicesLimit - 1]
								.setTime(selectedFiles[slicesLimit - 1].getTime() + allValues[i].getTime());
					}
				} else {
					selectedFiles[index] = new FileTime(allValues[i].getTime(), allValues[i].getName());
					index++;
				}
			}

			double globalTime = 0;
			final double values[] = new double[selectedFiles.length];
			for (int i = 0; i < selectedFiles.length; ++i) {
				values[i] = selectedFiles[i].getTime() * 1000;
				globalTime += selectedFiles[i].getTime();
			}

			final String labels[] = new String[selectedFiles.length];
			String tooltips[] = new String[selectedFiles.length];
			DecimalFormat percentFormat = new DecimalFormat("#0.#", //$NON-NLS-1$
					new DecimalFormatSymbols(new Locale("en"))); //$NON-NLS-1$
			double percentageSum = 0;
			for (int i = 0; i < selectedFiles.length; i++) {
				FileTime file = selectedFiles[i];
				if (file != null) {
					File currentFile = new File(file.getName());
					String percentageString = ""; //$NON-NLS-1$
					if (i == selectedFiles.length - 1) {
						percentageString = NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_11"), //$NON-NLS-1$
								percentFormat.format(100 - percentageSum));
					} else {
						double currentPercent = (file.getTime() / globalTime) * 100;
						percentageSum += currentPercent;
						percentageString = NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_11"), //$NON-NLS-1$
								percentFormat.format(currentPercent));
					}
					labels[i] = NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_12"), //$NON-NLS-1$
							new String[] { currentFile.getName(), file.isMicroSecOrLess() ? ("<= " + fDecimalFormat //$NON-NLS-1$
									.format(file.getTime())) : fDecimalFormat.format(file.getTime()),
									percentageString });
					String fullPath = currentFile.getAbsolutePath();
					// to check whether it's the last file with the Other
					if (currentFile.getName()
							.startsWith(PHPProfileUIMessages.getString("ProfilerInformationView_8").substring(0, //$NON-NLS-1$
									PHPProfileUIMessages.getString("ProfilerInformationView_8") //$NON-NLS-1$
											.indexOf("("))) //$NON-NLS-1$
							&& i == selectedFiles.length - 1) {
						fullPath = currentFile.getName();
					}
					tooltips[i] = NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_12"), //$NON-NLS-1$
							new String[] { fullPath, fDecimalFormat.format(file.getTime()), percentageString });
				}
			}

			if (labels.length > 0) {
				final Display display = getSite().getShell().getDisplay();
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						BusyIndicator.showWhile(getSite().getShell().getDisplay(), new Runnable() {
							@Override
							public void run() {
								Chart chart = ChartUtil.createPieChart(labels, values);
								chart.getBlock()
										.setBackground(ColorDefinitionImpl.create(fForm.getBackground().getRed(),
												fForm.getBackground().getGreen(), fForm.getBackground().getBlue()));
								fChartViewer.updateChartModel(chart);
							}
						});
					}
				});
			}
		} else {
			fChartViewer.updateChartModel(null);
		}
	}

	private void setGeneralInfoLabelText(ProfilerDB db) {
		if (db != null) {
			ProfilerGlobalData globalData = db.getGlobalData();

			fUriLabel.setText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_2"), //$NON-NLS-1$
					globalData.getOriginalURL().replaceAll("&", "&&"))); //$NON-NLS-1$ //$NON-NLS-2$
			fUriLabel.setToolTipText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_2"), //$NON-NLS-1$
					globalData.getOriginalURL()));

			fQueryLabel.setText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_3"), //$NON-NLS-1$
					calculateQueryMessage(globalData.getQuery().replaceAll("&", "&&")))); //$NON-NLS-1$ //$NON-NLS-2$
			fQueryLabel.setToolTipText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_3"), //$NON-NLS-1$
					calculateQueryMessage(globalData.getQuery())));

			fPathLabel.setText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_4"), //$NON-NLS-1$
					globalData.getPath()));
			fPathLabel.setToolTipText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_4"), //$NON-NLS-1$
					globalData.getPath()));

			String totalTime = fDecimalFormat.format(globalData.getGlobalTimeInMilli());
			fTotalTimeLabel.setText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_5"), //$NON-NLS-1$
					totalTime));
			fTotalTimeLabel.setToolTipText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_5"), //$NON-NLS-1$
					totalTime));

			fFileCountLabel.setText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_6"), //$NON-NLS-1$
					Integer.toString(globalData.getFileCount())));
			fFileCountLabel.setToolTipText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_6"), //$NON-NLS-1$
					Integer.toString(globalData.getFileCount())));

			fDateLabel.setText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_7"), //$NON-NLS-1$
					db.getProfileDate().toString()));
			fDateLabel.setToolTipText(NLS.bind(PHPProfileUIMessages.getString("ProfilerInformationView_7"), //$NON-NLS-1$
					db.getProfileDate().toString()));

		} else {
			fUriLabel.setText(""); //$NON-NLS-1$
			fUriLabel.setToolTipText(""); //$NON-NLS-1$
			fQueryLabel.setText(""); //$NON-NLS-1$
			fQueryLabel.setToolTipText(""); //$NON-NLS-1$
			fPathLabel.setText(""); //$NON-NLS-1$
			fPathLabel.setToolTipText(""); //$NON-NLS-1$
			fTotalTimeLabel.setText(""); //$NON-NLS-1$
			fTotalTimeLabel.setToolTipText(""); //$NON-NLS-1$
			fFileCountLabel.setText(""); //$NON-NLS-1$
			fFileCountLabel.setToolTipText(""); //$NON-NLS-1$
			fDateLabel.setText(""); //$NON-NLS-1$
			fDateLabel.setToolTipText(""); //$NON-NLS-1$
		}
		fForm.redraw();
	}

	public static String calculateQueryMessage(String query) {
		String newString = query;
		if (query.indexOf("start_debug") != -1) { //$NON-NLS-1$
			newString = query.substring(0, query.indexOf("start_debug")); //$NON-NLS-1$
		}
		if (newString.equals("")) { //$NON-NLS-1$
			return PHPProfileUIMessages.getString("ProfilerInformationView_10"); //$NON-NLS-1$
		}
		if (newString.endsWith("&")) { //$NON-NLS-1$
			newString = newString.substring(0, newString.length() - 1);
		}
		return newString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.views.AbstractProfilerView#getInput()
	 */
	@Override
	public ProfilerDB getInput() {
		return fProfilerDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.views.AbstractProfilerView#setInput(org.
	 * eclipse. php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void setInput(ProfilerDB profilerDB) {
		if (fProfilerDB != profilerDB) {
			setGeneralInfoLabelText(profilerDB);
			redrawPieChart(profilerDB);

			fProfilerDB = profilerDB;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	private static class FileTime implements Comparable<Object> {

		private static final double microSecOrLess = 0.001;

		private double time;
		private String name;

		public FileTime(double time, String name) {
			this.time = time > 0 ? time : microSecOrLess;
			this.name = name;
		}

		public double getTime() {
			return time;
		}

		public String getName() {
			return name;
		}

		public boolean isMicroSecOrLess() {
			return time == microSecOrLess;
		}

		public void setTime(double time) {
			this.time = time;
		}

		@Override
		public int compareTo(Object o) {
			double newTime = ((FileTime) o).getTime();
			if (time - newTime <= 0) {
				if (time - newTime == 0) {
					return 0;
				}
				return -1;
			}
			return 1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * currentSessionChanged(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void currentSessionChanged(final ProfilerDB current) {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				setInput(current);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * profileSessionAdded(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void profileSessionAdded(ProfilerDB db) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.profile.core.profiler.IProfileSessionListener#
	 * profileSessionRemoved(org.eclipse.php.profile.core.profiler.ProfilerDB)
	 */
	@Override
	public void profileSessionRemoved(ProfilerDB db) {
	}
}
