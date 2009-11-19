/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.test.internal.performance.results.utils;

/**
 * Interface to define all constants used for performances.
 */
public interface IPerformancesConstants {
    public static final String PLUGIN_ID = "org.eclipse.test.performance.ui"; //$NON-NLS-1$

	public static final String PREFIX = PLUGIN_ID + "."; //$NON-NLS-1$

	// State constants
    public static final String PRE_FILTER_BASELINE_BUILDS = PREFIX + "filter.baseline.builds"; //$NON-NLS-1$
    public static final String PRE_FULL_LINE_SELECTION  = PREFIX + "full.line.selection"; //$NON-NLS-1$

	    // Preference constants
    public static final String PRE_ECLIPSE_VERSION = PREFIX + "eclipse.version"; //$NON-NLS-1$
    public static final String PRE_DATABASE_CONNECTION = PREFIX + "database.connection"; //$NON-NLS-1$
    public static final String PRE_DATABASE_LOCAL = PREFIX + "local"; //$NON-NLS-1$
    public static final String PRE_DATABASE_LOCATION = PREFIX + "database.location"; //$NON-NLS-1$
    public static final String PRE_LOCAL_DATA_DIR = PREFIX + "local.data.dir"; //$NON-NLS-1$
    public static final String PRE_RESULTS_GENERATION_DIR = PREFIX + "results.generation.dir"; //$NON-NLS-1$
    public static final String PRE_CONFIG_DESCRIPTOR_NAME = PREFIX + "config.descriptor.name"; //$NON-NLS-1$
    public static final String PRE_CONFIG_DESCRIPTOR_DESCRIPTION = PREFIX + "config.descriptor.description"; //$NON-NLS-1$
    public static final String PRE_DEFAULT_DIMENSION = PREFIX + "default.dimension"; //$NON-NLS-1$
    public static final String PRE_RESULTS_DIMENSION = PREFIX + "results.dimension"; //$NON-NLS-1$
    public static final String PRE_MILESTONE_BUILDS = PREFIX + "milestone.builds"; //$NON-NLS-1$
    public static final String PRE_STATUS_COMMENT_PREFIX = PREFIX + "status.comment"; //$NON-NLS-1$
    public static final String PRE_FILTER_ADVANCED_SCENARIOS = PREFIX + "filter.non.fingerprints.scenarios"; //$NON-NLS-1$
    public static final String PRE_FILTER_OLD_BUILDS = PREFIX + "filter.non.milestones.builds"; //$NON-NLS-1$
    public static final String PRE_FILTER_NIGHTLY_BUILDS = PREFIX + "filter.nightly.builds"; //$NON-NLS-1$

	// Other constants
	public static final int ECLIPSE_MAINTENANCE_VERSION = 35;
	public static final int ECLIPSE_DEVELOPMENT_VERSION = 36;

	// Default values
	public static final String DATABASE_NAME_PREFIX = "perfDb";
	public static final String NETWORK_DATABASE_LOCATION = "net://trelenggtk.ottawa.ibm.com:1528";
	public static final int DEFAULT_ECLIPSE_VERSION = ECLIPSE_DEVELOPMENT_VERSION;
	public static final boolean DEFAULT_FILTER_ADVANCED_SCENARIOS = true;
	public static final boolean DEFAULT_FILTER_OLD_BUILDS = false;
	public static final boolean DEFAULT_FILTER_NIGHTLY_BUILDS = false;
	public static final boolean DEFAULT_DATABASE_CONNECTION = false;
	public static final boolean DEFAULT_DATABASE_LOCAL = false;

	// Default milestones nowadays
	public static final String[] V36_MILESTONES = new String[] {
		"M1-200908060100",
		"M2-200909170100",
	};
	public static final String[] V35_MILESTONES = new String[] {
		        "M1-200808071402",
		        "M2-200809180100",
		        "M3-200810301917",
		        "M4-200812111908",
		        "M5-200902021535",
		        "M6-200903130100",
		        "M7-200904302300",
		        "RC1-200905151143",
		        "RC2-200905221710",
		        "RC3-200905282000",
		        "RC4-200906051444",
		        "R3_5-200906111540"
	};
	/** @deprecated */
	public static final String[] V34_MILESTONES = new String[] {
		        "M1-200708091105",
		        "M2-200709210919",
		        "M3-200711012000",
		        "M4-200712131700",
		        "M5-200802071530",
		        "M6a-200804091425",
		        "M7-200805020100",
		        "RC1-200805161333",
		        "RC2-200805230100",
		        "RC3-200805301730",
		        "RC4-200806091311",
		        "R3_4-200806172000"
	};
	/** @deprecated */
	public static final String[] V33_MILESTONES = new String[] {
		        "M1-200608101230",
		        "M2-200609220010",
		        "M3-200611021715",
		        "M4-200612141445",
		        "M5-200702091006",
		        "M5eh-200702220951",
		        "M6-200703231616",
		        "M7-200705031400",
		        "RC1-200705171700",
		        "RC2-200705251350",
		        "RC3-200706011539",
		        "RC4-200706081718",
		        "R3_3-200706251500",
		        "R3_3_1-200709211145",
		        "R3_3_2-200802211800"
	};

}
