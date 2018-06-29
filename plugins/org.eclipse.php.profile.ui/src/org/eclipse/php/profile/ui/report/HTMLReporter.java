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
package org.eclipse.php.profile.ui.report;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.profile.core.data.ProfilerGlobalData;
import org.eclipse.php.profile.core.engine.ProfilerDB;
import org.eclipse.php.profile.ui.PHPProfileUIMessages;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.utils.ProfileUITools;
import org.eclipse.php.profile.ui.views.AbstractProfilerFunctionsView;
import org.eclipse.php.profile.ui.views.ProfilerInformationView;
import org.eclipse.ui.IViewPart;

/**
 * Profiler HTML reporter.
 */
public class HTMLReporter {

	private static final DecimalFormat fDecimalFormat = new DecimalFormat("#0.0#", //$NON-NLS-1$
			new DecimalFormatSymbols(new Locale("en"))); //$NON-NLS-1$
	private static final String CSS_PATH = "resources/html_report.css"; //$NON-NLS-1$
	private static final String JAVASCRIPT_PATH = "resources/html_report.js"; //$NON-NLS-1$
	public static final String IMAGES_DIR = "report_images"; //$NON-NLS-1$

	private ProfilerDB fProfilerDB;
	private String[] fViews;
	private PrintWriter fWriter;

	public HTMLReporter(ProfilerDB profilerDB, String[] views, PrintWriter writer) {
		fProfilerDB = profilerDB;
		fViews = views;
		fWriter = writer;
	}

	/**
	 * Read file into string buffer
	 * 
	 * @param String
	 *            file path
	 */
	private void readFile(String file) {
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					FileLocator.openStream(ProfilerUiPlugin.getDefault().getBundle(), new Path(file), false)));
			String line = null;
			while ((line = is.readLine()) != null) {
				fWriter.println(line);
			}
			is.close();
		} catch (Exception e) {
			ProfilerUiPlugin.log(e);
		}
	}

	/**
	 * Add CSS section
	 */
	private void addCSS() {
		fWriter.println("<style type=\"text/css\">"); //$NON-NLS-1$
		readFile(CSS_PATH);
		fWriter.println("</style>"); //$NON-NLS-1$
	}

	/**
	 * Add JavaScript section
	 */
	private void addJavaScript() {
		fWriter.println("<script type=\"text/javascript\">"); //$NON-NLS-1$
		readFile(JAVASCRIPT_PATH);
		fWriter.println("</script>"); //$NON-NLS-1$
	}

	/**
	 * Add head section to the report
	 */
	private void addHeader() {
		fWriter.println("<head>"); //$NON-NLS-1$
		fWriter.print("<title>"); //$NON-NLS-1$
		fWriter.print(NLS.bind(PHPProfileUIMessages.getString("HTMLReporter.8"), fProfilerDB.getGlobalData().getURI(), //$NON-NLS-1$
				fProfilerDB.getProfileDate()));
		fWriter.print("</title>"); //$NON-NLS-1$
		addCSS();
		addJavaScript();
		fWriter.println("</head>"); //$NON-NLS-1$
	}

	/**
	 * Generates report for specific view
	 * 
	 * @param String
	 *            AbstractProfilerFunctionsView view id
	 */
	private void generateViewReport(String viewId) {
		IViewPart view = ProfileUITools.findExistingView(viewId);
		if (view instanceof AbstractProfilerFunctionsView) {
			fWriter.print("<h2>"); //$NON-NLS-1$
			fWriter.print(view.getTitle());
			fWriter.println("</h2>"); //$NON-NLS-1$
			fWriter.println("<br>"); //$NON-NLS-1$
			TreeViewer2HTML.process(((AbstractProfilerFunctionsView) view).getViewer(), fWriter);
		}
	}

	private void printGlobalDataRow(String key, String value) {
		fWriter.print("<tr><td>"); //$NON-NLS-1$
		fWriter.print(key);
		fWriter.print("</td><td>"); //$NON-NLS-1$
		fWriter.print(value);
		fWriter.println("</td><tr>"); //$NON-NLS-1$
	}

	/**
	 * Generates report of the global information
	 */
	private void generateGlobalInfoReport() {
		fWriter.println("<table cellpadding=4 cellspacing=0>"); //$NON-NLS-1$
		fWriter.println("<colgroup><col id=\"col1\"/><col width=\"0*\" id=\"col2\"/></colgroup>"); //$NON-NLS-1$

		ProfilerGlobalData globalData = fProfilerDB.getGlobalData();
		printGlobalDataRow(PHPProfileUIMessages.getString("HTMLReporter.0"), globalData.getOriginalURL()); //$NON-NLS-1$
		printGlobalDataRow(PHPProfileUIMessages.getString("HTMLReporter.1"), //$NON-NLS-1$
				ProfilerInformationView.calculateQueryMessage(globalData.getQuery()));
		printGlobalDataRow(PHPProfileUIMessages.getString("HTMLReporter.2"), globalData.getPath()); //$NON-NLS-1$
		printGlobalDataRow(PHPProfileUIMessages.getString("HTMLReporter.3"), //$NON-NLS-1$
				fDecimalFormat.format(globalData.getGlobalTimeInMilli()));
		printGlobalDataRow(PHPProfileUIMessages.getString("HTMLReporter.4"), //$NON-NLS-1$
				Integer.toString(globalData.getFileCount()));
		printGlobalDataRow(PHPProfileUIMessages.getString("HTMLReporter.5"), fProfilerDB.getProfileDate().toString()); //$NON-NLS-1$

		fWriter.print("</table><br>\n"); //$NON-NLS-1$
	}

	private void generateReport() {
		fWriter.println("<html>"); //$NON-NLS-1$
		addHeader();

		fWriter.println("<body>"); //$NON-NLS-1$
		fWriter.println("<center>"); //$NON-NLS-1$

		fWriter.print("<h1>"); //$NON-NLS-1$
		fWriter.print(PHPProfileUIMessages.getString("HTMLReporter.6")); //$NON-NLS-1$
		fWriter.println("</h1><br>"); //$NON-NLS-1$

		generateGlobalInfoReport();

		for (int i = 0; i < fViews.length; ++i) {
			generateViewReport(fViews[i]);
			fWriter.println("<br>"); //$NON-NLS-1$
		}
		fWriter.println("</center>\n</body>\n</html>"); //$NON-NLS-1$
	}

	/**
	 * Generates report for specified views
	 * 
	 * @param ProfilerDB
	 *            profiler database, used for the common information.
	 * @param String
	 *            [] AbstractProfilerFunctionsView[] views ids. These views must
	 *            belong to one profile session.
	 * @param PrintWriter
	 *            stream to print the report to
	 */
	public static void generateReport(ProfilerDB profilerDB, String[] views, PrintWriter writer) {
		HTMLReporter reporter = new HTMLReporter(profilerDB, views, writer);
		reporter.generateReport();
	}

	/**
	 * Save images needed for report
	 * 
	 * @param String
	 *            target directory
	 */
	public static void saveImages(String targetDirectory) throws IOException {
		targetDirectory = targetDirectory + File.separatorChar + IMAGES_DIR;
		File target = new File(targetDirectory);
		if (!target.exists()) {
			URL imagesURL = FileLocator.find(ProfilerUiPlugin.getDefault().getBundle(),
					new Path("resources/" + IMAGES_DIR), new HashMap<String, String>()); //$NON-NLS-1$
			File imagesDir = new File(FileLocator.toFileURL(imagesURL).getPath());
			try {
				new LocalFile(imagesDir).copy(new LocalFile(new File(targetDirectory)), EFS.NONE,
						new NullProgressMonitor());
			} catch (CoreException e) {
				ProfilerUiPlugin.log(e);
			}
		}
	}
}
