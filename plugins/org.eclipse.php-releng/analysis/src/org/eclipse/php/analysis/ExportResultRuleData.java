/**********************************************************************
 * Copyright (c) 2006,2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ExportResultRuleData.java,v 1.1 2007/06/24 11:40:39 rganor Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.php.analysis;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.tptp.platform.analysis.codereview.java.CodeReviewResult;
import org.eclipse.tptp.platform.analysis.codereview.java.quickfix.JavaCodeReviewQuickFix;
import org.eclipse.tptp.platform.analysis.core.AnalysisConstants;
import org.eclipse.tptp.platform.analysis.core.AnalysisCorePlugin;
import org.eclipse.tptp.platform.analysis.core.element.AbstractAnalysisElement;
import org.eclipse.tptp.platform.analysis.core.element.AnalysisParameter;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistory;
import org.eclipse.tptp.platform.analysis.core.provider.AbstractAnalysisProvider;
import org.eclipse.tptp.platform.analysis.core.quickfix.IAnalysisQuickFix;
import org.eclipse.tptp.platform.analysis.core.result.AbstractAnalysisResult;
import org.eclipse.tptp.platform.analysis.core.rule.AbstractAnalysisRule;

public class ExportResultRuleData extends ExportAllRuleData {

	public void export(PrintStream stream, IProgressMonitor monitor, AnalysisHistory history, AbstractAnalysisProvider provider) {
		exportProvider(stream, monitor, history, provider);
	}

	protected String exportRule(AnalysisHistory history, AbstractAnalysisRule rule) {
		StringBuffer resultString = new StringBuffer();
		List results = rule.getHistoryResults(history.getHistoryId());
		if (results != null && results.size() > 0) {
			//Generate results into a new stringbuffer
			StringBuffer sb = new StringBuffer();
			for (Iterator it = results.iterator(); it.hasNext();) {
				AbstractAnalysisResult child = (AbstractAnalysisResult) it.next();
				if (child.isVisible()) {
					String report = exportResult(history, child);
					if (report != null) {
						sb.append(report);
					}
				}
			}

			// If there were any results, then write the rule/result block
			if (sb.length() > 0) {
				resultString.append(getRuleHeader(rule));
				resultString.append(sb);
				resultString.append(getFooter(rule));
			}
		}

		return resultString.toString();
	}

	public void exportSuites(PrintStream output) {
		assert files != null && output != null;

		output.print(XML_HEADER);
		output.println(AnalysisConstants.LINE_SEPARATOR);
		
		output.println("<analysissuites>");
		
		
		// <analysissuite results="2" severes="1" warnings="1" recommendations="0" name="org.eclipse.php.ui.pack1.Hello1" package="org.eclipse.php.ui.pack1" >
		for (Entry<String, List<CodeReviewResult>> element : files.entrySet()) {
			final String fileName = element.getKey();
			final List<CodeReviewResult> results = element.getValue();

			// write suite header
			output.print("<analysissuite name=\"");
			replaceSlash(output, fileName);
			output.print("\" ");
			
			output.print("package=\"");
			replaceSlash(output, fileName.substring(0, fileName.lastIndexOf("/")));
			output.print("\" ");

			output.print("results=\"");
			output.print(results.size());			
			output.print("\" ");
			
			int severes = 0;
			int warnings = 0;
			int recommendations = 0;
			
			// write case
			for (CodeReviewResult codeReviewResult : results) {
				final AnalysisParameter parameter = codeReviewResult.getOwner().getParameter(AnalysisParameter.SEVERITY);
				final String value = parameter.getValue() == null ? "0" : parameter.getValue();
				
				if (value.equals(AnalysisParameter.SEVERITY_LOW)) {
					recommendations++;
				} else if (value.equals(AnalysisParameter.SEVERITY_MEDIUM)) {
					warnings++;
				} else {
					severes++; 
				}
			}

			output.print("severes=\"");
			output.print(severes);			
			output.print("\" ");

			output.print("warnings=\"");
			output.print(warnings);			
			output.print("\" ");

			output.print("recommendations=\"");
			output.print(recommendations);			
			output.println("\">");
			
			// write analysis cases
			for (CodeReviewResult codeReviewResult : results) {
				// <analysiscase classname="org.eclipse.php.ui.pack1.Hello1" line="12">
				output.print("<analysiscase classname=\"");
				replaceSlash(output, fileName);
				output.print("\" ");

				output.print("line=\"");
				output.print(codeReviewResult.getLineNumber());			
				output.println("\">");
				
				final AnalysisParameter parameter = codeReviewResult.getOwner().getParameter(AnalysisParameter.SEVERITY);
				String value = parameter.getValue() == null ? "0" : parameter.getValue() ;
				final String severity = value.equals(AnalysisParameter.SEVERITY_LOW) ? "recommendation" : value.equals(AnalysisParameter.SEVERITY_MEDIUM) ? "warning" : "severe"; 
				
	        	// <severe message="should do this not that"> content goes here </severe>
				output.print("<");
				output.print(severity);
				output.print(" type=\"");
				final String name = codeReviewResult.getOwner().getClass().getName();
				printClassName(output, name, name.length() - 1);
				output.print("\" message=\"");
				output.print(AnalysisCorePlugin.encodeForXML(codeReviewResult.getLabel())); 
				output.print("\">");
				
				final AbstractAnalysisRule rule = (AbstractAnalysisRule) codeReviewResult.getOwner();
				if (rule.hasQuickFixes()) {
					final String id = (String) rule.getQuickFixIterator().next();
					final JavaCodeReviewQuickFix quickFix = (JavaCodeReviewQuickFix) rule.getQuickFix(id);
				}
				output.print("</");
				output.print(severity);
				output.print(">");
				
				// write suite footer
				output.println("</analysiscase>");
			}
			output.println("</analysissuite>");
		}

		output.println("</analysissuites>");
	}

	private void printClassName(PrintStream output, String name, int index) {
		if (index > 0 && name.charAt(index - 1) != '.') {
			printClassName(output, name, index - 1);
		}
		output.print(name.charAt(index));
	}

	private void replaceSlash(PrintStream output, String fileName) {
		final int length = fileName.length();
		for (int i = 0; i < length; i++) {
			final char charAt = fileName.charAt(i);
			output.print(charAt == '/' ? "." : charAt);
		}
	}
}
