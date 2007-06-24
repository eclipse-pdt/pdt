/**********************************************************************
 * Copyright (c) 2006,2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: AbstractAnalysisXMLExporter.java,v 1.1 2007/06/24 11:40:39 rganor Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.php.analysis;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.tptp.platform.analysis.core.AnalysisConstants;
import org.eclipse.tptp.platform.analysis.core.AnalysisCorePlugin;
import org.eclipse.tptp.platform.analysis.core.CoreMessages;
import org.eclipse.tptp.platform.analysis.core.category.DefaultAnalysisCategory;
import org.eclipse.tptp.platform.analysis.core.element.AbstractAnalysisElement;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistory;
import org.eclipse.tptp.platform.analysis.core.provider.AbstractAnalysisProvider;
import org.eclipse.tptp.platform.analysis.core.result.AbstractAnalysisResult;
import org.eclipse.tptp.platform.analysis.core.rule.AbstractAnalysisRule;

/**
 * This class provides functionality that is common for all XML data exporters.
 * All XML exporter classes should extend this abstract class and override any
 * methods required to support custom exporting.
 * 
 * @author sgutz
 * @version 1.0
 * 
 */
public abstract class AbstractAnalysisXMLExporter implements IAnalysisExporter {

	private String id;
	private String label;
	private String description;

	protected static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //$NON-NLS-1$

	protected static final String TAG_TERMINATE_OPEN = "</"; //$NON-NLS-1$
	protected static final String ID_ATTRIB = " id=\""; //$NON-NLS-1$
	protected static final String NAME_ATTRIB = " name=\""; //$NON-NLS-1$
	protected static final String QUOTE = "\""; //$NON-NLS-1$
	protected static final String TAG_OPEN = "<"; //$NON-NLS-1$
	protected static final String TAG_CLOSE = ">"; //$NON-NLS-1$

	private static final String REPORT_FILE_TYPE = "xml"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#export(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.tptp.platform.analysis.core.history.AnalysisHistory, org.eclipse.tptp.platform.analysis.core.provider.AbstractAnalysisProvider)
	 */
	public abstract void export(PrintStream output, IProgressMonitor monitor, AnalysisHistory history, AbstractAnalysisProvider provider);

	/**
	 * Export the specified provider and its child elements to XML.  This method can be over ridden
	 * to provide alternate data for the provider
	 * 
	 * @param monitor	A progress monitor to report status
	 * @param history	The history instance whose data will be exported
	 * @param provider	The provider to export
	 * 
	 * @return A XML string containing the export data
	 */
	protected void exportProvider(PrintStream output, IProgressMonitor monitor, AnalysisHistory history, AbstractAnalysisProvider provider) {
		output.print(XML_HEADER);
		output.print(AnalysisConstants.LINE_SEPARATOR);
		output.print(getHeader(provider));

		if (provider.getOwnedElements() != null) {
			for (Iterator it = provider.getOwnedElements().iterator(); it.hasNext();) {
				DefaultAnalysisCategory child = (DefaultAnalysisCategory) it.next();
				if (history.containsAnalysisElement(child)) {
					exportTopLevelCategory(output, monitor, history, child);
				}
			}
		}

		output.print(getFooter(provider));
	}

	/**
	 * Export the specified top-level category and its child elements to XML.  This method can be over ridden
	 * to provide alternate data for the category
	 * @param output 
	 * 
	 * @param monitor	A progress monitor to report status
	 * @param history	The history instance whose data will be exported
	 * @param category	The top level category to export
	 * 
	 * @return A XML string containing the export data
	 */
	protected void exportTopLevelCategory(PrintStream output, IProgressMonitor monitor, AnalysisHistory history, DefaultAnalysisCategory category) {
		output.print(getHeader(category));

		if (category.getOwnedElements() != null) {
			monitor.beginTask(CoreMessages.export_job, category.getOwnedElements().size());

			for (Iterator it = category.getOwnedElements().iterator(); it.hasNext();) {
				AbstractAnalysisElement child = (AbstractAnalysisElement) it.next();
				if (history.containsAnalysisElement(child)) {
					if (child.getElementType() == AbstractAnalysisElement.CATEGORY_ELEMENT_TYPE) {
						exportSubCategory(output, history, (DefaultAnalysisCategory) child);
					} else {
						exportRule(output, history, (AbstractAnalysisRule) child);
					}
				}
				monitor.worked(1);
			}
		}

		output.print( getFooter(category));
	}

	private void exportSubCategory(PrintStream output, AnalysisHistory history, DefaultAnalysisCategory category) {

		if (category.getOwnedElements() != null) {
			for (Iterator it = category.getOwnedElements().iterator(); it.hasNext();) {
				AbstractAnalysisElement child = (AbstractAnalysisElement) it.next();
				if (history.containsAnalysisElement(child)) {
					if (child.getElementType() == AbstractAnalysisElement.CATEGORY_ELEMENT_TYPE) {
						exportSubCategory(output, history, (DefaultAnalysisCategory) child);
					} else {
						exportRule(output, history, (AbstractAnalysisRule) child);
					}
				}
			}
		}
	}

	/**
	 * Export the specified rule and its child elements to XML.  This method can be over ridden
	 * to provide alternate data for the rule
	 * @param output 
	 * 
	 * @param history	The history instance whose data will be exported
	 * @param rule	The rule to export
	 * 
	 * @return A XML string containing the export data
	 */
	protected void exportRule(PrintStream output, AnalysisHistory history, AbstractAnalysisRule rule) {
		List results = rule.getHistoryResults(history.getHistoryId());
		if (results != null) {
			output.print( getRuleHeader(rule));

			for (Iterator it = results.iterator(); it.hasNext();) {
				AbstractAnalysisResult child = (AbstractAnalysisResult) it.next();
				String report = exportResult(history, child);
				if (report != null) {
					output.print( report);
				}
			}
		}

		output.print( getFooter(rule));
	}

	/**
	 * Generate a report for this element
	 * 
	 * @param history		The analysis history for which the report is being generated 
	 * @param element	 The analysis provider whose data is being exported
	 * 
	 * @return	A String representation of the generated XML report or null if the report type is not available
	 */
	public String exportResult(AnalysisHistory history, AbstractAnalysisResult element) {
		return AnalysisConstants.BLANK;
	}

	/**
	 * Generate the appropriate XML header tag for the specified element
	 * 
	 * @param element	The element whose tag will be generated
	 * 
	 * @return A XML string containing the element's XML tag
	 */
	protected final String getHeader(AbstractAnalysisElement element) {
		// Generate prefix data 
		StringBuffer result = new StringBuffer();
		result.append(getIndent(element)).append(TAG_OPEN).append(AnalysisConstants.XML_ELEMENT_TAGS[element.getElementType()]).append(NAME_ATTRIB).append(AnalysisCorePlugin.encodeForXML(element.getLabel())).append(QUOTE).append(ID_ATTRIB).append(element.getId()).append(QUOTE).append(TAG_CLOSE)
			.append(AnalysisConstants.LINE_SEPARATOR);

		return result.toString();
	}

	/**
	 * Generate the appropriate XML header tag for the specified element
	 * 
	 * @param element	The element whose tag will be generated
	 * 
	 * @return A XML string containing the element's XML tag
	 */
	protected final String getRuleHeader(AbstractAnalysisRule element) {
		// Generate prefix data 
		StringBuffer result = new StringBuffer();
		result.append(getIndent(element)).append(TAG_OPEN).append(AnalysisConstants.XML_ELEMENT_TAGS[element.getElementType()]).append(NAME_ATTRIB).append(AnalysisCorePlugin.encodeForXML(element.getLabelWithParameters())).append(QUOTE).append(ID_ATTRIB).append(element.getId()).append(QUOTE).append(
			TAG_CLOSE).append(AnalysisConstants.LINE_SEPARATOR);

		return result.toString();
	}

	/**
	 * Generate the appropriate XML footer tag for the specified element
	 * 
	 * @param element	The element whose tag will be generated
	 * 
	 * @return A XML string containing the element's XML tag
	 */
	protected final String getFooter(AbstractAnalysisElement element) {
		// Generate prefix data 
		StringBuffer result = new StringBuffer();

		result.append(getIndent(element)).append(TAG_TERMINATE_OPEN).append(AnalysisConstants.XML_ELEMENT_TAGS[element.getElementType()]).append(TAG_CLOSE).append(AnalysisConstants.LINE_SEPARATOR);

		return result.toString();
	}

	/**
	 * Generate the correct indentation for the specified element.  Indentation serves only
	 * to make the generate XML file more readable.
	 * 
	 * @param element	The element whose indentation will be generated
	 * 
	 * @return A XML string containing the element's XML tag
	 */
	protected final String getIndent(AbstractAnalysisElement element) {
		StringBuffer indent = new StringBuffer();
		switch (element.getElementType()) {
			case AbstractAnalysisElement.CATEGORY_ELEMENT_TYPE:
				indent.append(AnalysisConstants.TAB);
				break;
			case AbstractAnalysisElement.RULE_ELEMENT_TYPE:
				indent.append(AnalysisConstants.TAB);
				indent.append(AnalysisConstants.TAB);
				break;
			case AbstractAnalysisElement.RESULT_ELEMENT_TYPE:
				indent.append(AnalysisConstants.TAB);
				indent.append(AnalysisConstants.TAB);
				indent.append(AnalysisConstants.TAB);
				break;
			default:
				break;
		}
		return indent.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#setLabel(java.lang.String)
	 */
	public final void setLabel(String newLabel) {
		if (this.label == null) {
			this.label = newLabel;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#getLabel()
	 */
	public final String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#setId(java.lang.String)
	 */
	public final void setId(String newId) {
		if (this.id == null) {
			this.id = newId;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#getId()
	 */
	public final String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#setDescription(java.lang.String)
	 */
	public final void setDescription(String newDescription) {
		if (this.description == null) {
			this.description = newDescription;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#getDescription()
	 */
	public final String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#setExportFolder(java.lang.String)
	 */
	public void setExportFolder(String folder) {
		// Nothing to do because we don't care about output paths
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#setExportFile(java.lang.String)
	 */
	public void setExportFile(String file) {
		// Nothing to do because we don't care about output paths
	}

	/* (non-Javadoc)
	 * @see org.eclipse.tptp.platform.analysis.core.reporting.IAnalysisExporter#getFileType()
	 */
	public final String getFileType() {
		return REPORT_FILE_TYPE;
	}
}
