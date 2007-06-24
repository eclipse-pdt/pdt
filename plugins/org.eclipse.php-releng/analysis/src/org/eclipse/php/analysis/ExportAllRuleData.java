/**********************************************************************
 * Copyright (c) 2006,2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ExportAllRuleData.java,v 1.1 2007/06/24 11:40:39 rganor Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.php.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.runtime.Messages;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.tptp.platform.analysis.codereview.java.CodeReviewResult;
import org.eclipse.tptp.platform.analysis.core.AnalysisConstants;
import org.eclipse.tptp.platform.analysis.core.AnalysisCorePlugin;
import org.eclipse.tptp.platform.analysis.core.element.AnalysisParameter;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistory;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistoryElement;
import org.eclipse.tptp.platform.analysis.core.provider.AbstractAnalysisProvider;
import org.eclipse.tptp.platform.analysis.core.result.AbstractAnalysisResult;
import org.eclipse.tptp.platform.analysis.core.rule.AbstractAnalysisRule;

public class ExportAllRuleData extends AbstractAnalysisXMLExporter {
	private static final String TAG_LINE_END = "</line>"; //$NON-NLS-1$
	private static final String TAG_LINE = "<line>"; //$NON-NLS-1$
	private static final String TAG_FILE_END = "</file>"; //$NON-NLS-1$
	private static final String TAG_FILE = "<file>"; //$NON-NLS-1$
	private static final String TAG1 = "\t\t<{0} name=\"{1}\" severity=\"{2}\" severityId=\"{3}\" severityImage=\"{4}\">"; //$NON-NLS-1$

	private static final String IMAGE_FOLDER =  File.separator + "icons"; //$NON-NLS-1$
	
	public static final Map<String, List<CodeReviewResult>> files = new HashMap<String, List<CodeReviewResult>>();	

	
	/**
	 * Generate a report for this element
	 * 
	 * @param monitor	A progress monitor to report status
	 * @param history	The analysis history for which the report is being generated 
	 * @param provider The analysis provider whose data is being exported
	 * 
	 * @return	A String representation of the generated XML report or null if the report type is not available
	 */
	public void export( PrintStream output, IProgressMonitor monitor, AnalysisHistory history, AbstractAnalysisProvider provider ) {
		exportProvider( output, monitor, history, provider );
	}
	
	/**
	 * Generate a report for this element
	 * 
	 * @param history		The analysis history for which the report is being generated 
	 * @param element	 The analysis provider whose data is being exported
	 * 
	 * @return	A String representation of the generated XML report or null if the report type is not available
	 */
	public String exportResult( AnalysisHistory history, AbstractAnalysisResult element ) {
		StringBuffer result = new StringBuffer();

		CodeReviewResult resultElement = (CodeReviewResult)element;
		if( resultElement.isVisible() ) {
			AnalysisHistoryElement he = history.getHistoryElement( resultElement.getOwner() );
			AbstractAnalysisRule rule = (AbstractAnalysisRule)he.getMappedAnalysisElement();
			
			String severity = AnalysisParameter.SEVERITY_LOW;
			int index = 0;
			AnalysisParameter param = rule.getParameter( AnalysisParameter.SEVERITY );
			if( param != null ) {
				String value = rule.getParameter( AnalysisParameter.SEVERITY ).getValue();
				if (value == null) {
					value = "0";
				}
				index = Integer.parseInt( value );
				severity = (String)rule.getParameter( AnalysisParameter.SEVERITY ).getComboValues().get( index );			
			}
		
			// Generate prefix data 
			result.append( Messages.bind( TAG1, new Object[]{ 
					AnalysisConstants.XML_ELEMENT_TAGS[ resultElement.getElementType()],
					AnalysisCorePlugin.encodeForXML( resultElement.getLabelWithVariables() ),
					severity,
					Integer.toString( index ),
					writeImage( rule )
				} ) ).append( AnalysisConstants.LINE_SEPARATOR );
			
			// Write the data
			result.append( AnalysisConstants.TAB  )
				.append( AnalysisConstants.TAB )
				.append( AnalysisConstants.TAB )
				.append( TAG_FILE ) 
				.append( resultElement.getResourceName() )
				.append( TAG_FILE_END ) 
				.append( AnalysisConstants.LINE_SEPARATOR )
				.append( AnalysisConstants.TAB  )
				.append( AnalysisConstants.TAB )
				.append( AnalysisConstants.TAB )
				.append( TAG_LINE ) 
				.append( resultElement.getLineNumber() )
				.append( TAG_LINE_END ) 
				.append( AnalysisConstants.LINE_SEPARATOR );
			
			
			// Generate postfix data 
			result.append( getFooter(resultElement) );

			// add the package data
			final String filename = resultElement.getResourceName().substring(1);
			
			// add the file result
			List<CodeReviewResult> listOfResults = files.get(filename);
			if (listOfResults == null) {
				listOfResults = new LinkedList<CodeReviewResult>();
				files.put(filename, listOfResults);
			}
			listOfResults.add(resultElement);
		}
		return result.toString();
	}

	public String writeImage( AbstractAnalysisRule rule ) {
		StringBuffer path = new StringBuffer( AnalysisCorePlugin.getDefault().getStateLocation().toOSString() );
		path.append( IMAGE_FOLDER ); 
		
		// Make sure the folder exists
		File f = new File( path.toString() );
		f.mkdir();
		
		StringBuffer path2 = new StringBuffer( AnalysisCorePlugin.getDefault().getStateLocation().toOSString() );
		path2.append( File.separator ).append( rule.getIconName() );
		
		f = new File( path2.toString() );
		if( !f.exists() ) {
			try {
				URL url = FileLocator.resolve( AnalysisCorePlugin.getImageUrl( AnalysisCorePlugin.getPluginId(), rule.getIconName() ) );
				InputStream is = url.openStream();
				FileOutputStream os = new FileOutputStream( path2.toString() );
				byte[] b = new byte[32768];
				int count = is.read(b);
				while( count >= 0 ) {
					os.write( b, 0, count );
					count = is.read(b);
				}
				is.close();
				os.close();
			} catch( IOException e ) {
				return AnalysisConstants.BLANK;
			}
		}
		
		return path2.toString();		
	}
}
