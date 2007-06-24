/**********************************************************************
 * Copyright (c) 2006,2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: IAnalysisExporter.java,v 1.1 2007/06/24 11:40:39 rganor Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.php.analysis;

import java.io.PrintStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.tptp.platform.analysis.core.history.AnalysisHistory;
import org.eclipse.tptp.platform.analysis.core.provider.AbstractAnalysisProvider;

/**
 * This interface is used to define new data exporters for the static analysis 
 * framework.  The main entry point in the export() method however before this 
 * occurs the path and file for the export will be set.  
 * 
 * @author sgutz
 * @version 1.0
 *
 */
public interface IAnalysisExporter {
	/**
	 * Generate an export document based on the data contained in a given history.  This document can
	 * take almost any format, for example XML or CSV.  
	 * 
	 * @param monitor	A progress monitor to report status
	 * @param history The history to reference for XML data generation
	 * @param provider The analysis provider whose data is being exported
	 * 
	 * @return A complete document containing exported data
	 */
	public void export(PrintStream output, IProgressMonitor monitor, AnalysisHistory history, AbstractAnalysisProvider provider );
	
	/**
	 * Set the label for this data exporter.  This can be called only once - subsequent calls will be ignored
	 *  
	 * @param label The new label for this exporter
	 */
	public void setLabel( String label );
	
	/**
	 * @return The label for this data exporter
	 */
	public String getLabel();

	
	/**
	 * Set the description for this data exporter.  This can be called only once - subsequent calls will be ignored
	 *  
	 * @param label The new label for this exporter
	 */
	public void setDescription( String label );
	
	/**
	 * @return The description for this data exporter
	 */
	public String getDescription();
	
	/**
	 * @return The file type for this data exporter
	 */
	public String getFileType();
	
	/**
	 * Set the id for this data exporter.  This can be called only once - subsequent calls will be ignored
	 *  
	 * @param id The unique identifier for this exporter
	 */
	public void setId( String id );

	/**
	 * @return The unique identifier for this data exporter
	 */
	public String getId();

	/**
	 * Set the base folder where the exported data will be written.  Note however that not 
     * all data exporters will need or desire folder information so it is possible the 
     * implementation of this method will be empty.
	 * 
	 * @param folder  The base export folder
	 */
	public void setExportFolder( String folder );

	/**
	 * Set the name of the file where the exported data will be written. Note however that not 
     * all data exporters will need or desire file information so it is possible the 
     * implementation of this method will be empty.  
	 * 
	 * @param file  The name of the export file
	 */
	public void setExportFile( String file );
}
