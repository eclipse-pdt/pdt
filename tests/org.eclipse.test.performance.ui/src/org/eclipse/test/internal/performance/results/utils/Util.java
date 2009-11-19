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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.eclipse.test.internal.performance.results.db.BuildResults;
import org.eclipse.test.internal.performance.results.db.DB_Results;

/**
 * Utility methods for statistics. Got from org.eclipse.test.performance
 * framework
 */
public final class Util implements IPerformancesConstants {
private static String[] MILESTONES;
public static final BuildDateComparator BUILD_DATE_COMPARATOR = new BuildDateComparator();

static class BuildDateComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		String s1 = (String) o1;
		String s2 = (String) o2;
		return getBuildDate(s1).compareTo(getBuildDate(s2));
	}
}

private static void initMilestones() {
	String version = DB_Results.getDbVersion();

	// Initialize reference version and database directory
	char mainVersion = version.charAt(1);
	char minorVersion = version.charAt(2);

	// Initialize milestones
	if (mainVersion == '3') {
		switch (minorVersion) {
			case '3':
			case '4':
				throw new RuntimeException("Version "+mainVersion+'.'+minorVersion+" is no longer supported!");
			case '5':
				MILESTONES = V35_MILESTONES;
				break;
			case '6':
				MILESTONES = V36_MILESTONES;
				break;
			default:
				throw new RuntimeException("Version "+mainVersion+'.'+minorVersion+" is not supported yet!");
		}
	} else {
		throw new RuntimeException("Version "+mainVersion+'.'+minorVersion+" is not supported yet!");
	}
}

// Static information for time and date
public static final int ONE_MINUTE = 60000;
public static final long ONE_HOUR = 3600000L;
public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm"); //$NON-NLS-1$

/**
 * Compute the student t-test values.
 *
 * @see "http://en.wikipedia.org/wiki/Student's_t-test"
 *
 * @param baselineResults The baseline build
 * @param buildResults The current build
 * @return The student t-test value as a double.
 */
public static double computeTTest(BuildResults baselineResults, BuildResults buildResults) {

	double ref = baselineResults.getValue();
	double val = buildResults.getValue();

	double delta = ref - val;
	long dfRef = baselineResults.getCount() - 1;
	double sdRef = baselineResults.getDeviation();
	long dfVal = buildResults.getCount() - 1;
	double sdVal = buildResults.getDeviation();
	// TODO if the stdev's are not sufficiently similar, we have to take a
	// different approach

	if (!Double.isNaN(sdRef) && !Double.isNaN(sdVal) && dfRef > 0 && dfVal > 0) {
		long df = dfRef + dfVal;
		double sp_square = (dfRef * sdRef * sdRef + dfVal * sdVal * sdVal) / df;

		double se_diff = Math.sqrt(sp_square * (1.0 / (dfRef + 1) + 1.0 / (dfVal + 1)));
		double t = Math.abs(delta / se_diff);
		return t;
	}

	return -1;
}

/**
 * Copy a file to another location.
 *
 * @param src the source file.
 * @param dest the destination.
 * @return <code>true</code> if the file was successfully copied,
 * 	<code>false</code> otherwise.
 */
public static boolean copyFile(File src, File dest) {

	try {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		return false;
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
	return true;
}

/**
 * Copy a file content to another location.
 *
 * @param in the input stream.
 * @param dest the destination.
 * @return <code>true</code> if the file was successfully copied,
 * 	<code>false</code> otherwise.
 */
public static boolean copyStream(InputStream in, File dest) {

	try {
		OutputStream out = new FileOutputStream(dest);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		return false;
	} catch (IOException e) {
		e.printStackTrace();
		return false;
	}
	return true;
}

/**
 * Return the build date as yyyyMMddHHmm.
 *
 * @param buildName The build name (e.g. I20090806-0100)
 * @return The date as a string.
 */
public static String getBuildDate(String buildName) {
	return getBuildDate(buildName, DB_Results.getDbBaselinePrefix());
}

/**
 * Return the build date as yyyyMMddHHmm.
 *
 * @param buildName The build name (e.g. I20090806-0100)
 * @param baselinePrefix The baseline prefix (e.g. {@link DB_Results#getDbBaselinePrefix()})
 * @return The date as a string.
 */
public static String getBuildDate(String buildName, String baselinePrefix) {

	// Baseline name
	if (baselinePrefix != null && buildName.startsWith(baselinePrefix)) {
		int length = buildName.length();
		return buildName.substring(length-12, length);
	}

	// Build name
	char first = buildName.charAt(0);
	if (first == 'N' || first == 'I' || first == 'M') { // TODO (frederic) should be buildIdPrefixes...
		return buildName.substring(1, 9)+buildName.substring(10, 14);
	}

	// Try with date format
	int length = buildName.length() - 12 /* length of date */;
	for (int i=0; i<=length; i++) {
		try {
			String substring = i == 0 ? buildName : buildName.substring(i);
			Util.DATE_FORMAT.parse(substring);
			return substring; // if no exception is raised then the substring has a correct date format => return it
		} catch(ParseException ex) {
			// skip
		}
	}
	return null;
}

/**
 * Returns the date of the milestone corresponding at the given index.
 *
 * @param index The index of the milestone
 * @return The date as a YYYYMMDD-hhmm string.
 */
public static String getMilestoneDate(int index) {
	int length = getMilestonesLength();
	if (index >= length) return null;
	int dash = MILESTONES[index].indexOf('-');
	return MILESTONES[index].substring(dash+1);
}

/**
 * Returns the name the milestone matching the given date.
 *
 * @param buildName The name of the build
 * @return The milestone name as a string (e.g. M1)
 */
public static String getMilestoneName(String buildName) {
	int length = getMilestonesLength();
	String buildDate = getBuildDate(buildName, DB_Results.getDbBaselinePrefix());
	for (int i=0; i<length; i++) {
		int start = MILESTONES[i].indexOf(buildDate);
		if (start > 0) {
			return MILESTONES[i].substring(0, start - 1);
		}
	}
	return null;
}

/**
 * Returns whether the given build name is a milestone or not.
 *
 * @param buildName The build name
 * @return <code>true</code> if the build name matches a milestone one,
 * 	<code>false</code> otherwise.
 */
public static boolean isMilestone(String buildName) {
	return getMilestoneName(buildName) != null;
}

/**
 * Returns the name of the milestone which run after the given build name
 * or <code>null</code> if there's no milestone since the build has run.
 *
 * @param buildName The build name
 * @return <code>true</code> if the build name matches a milestone one,
 * 	<code>false</code> otherwise.
 */
public static String getNextMilestone(String buildName) {
	int length = getMilestonesLength();
	String buildDate = getBuildDate(buildName);
	for (int i=0; i<length; i++) {
		String milestoneDate = MILESTONES[i].substring(MILESTONES[i].indexOf('-')+1);
		if (milestoneDate.compareTo(buildDate) > 0) {
			return milestoneDate;
		}
	}
	return null;
}

/**
 * Return the number of milestones.
 *
 * @return The number as an int
 */
public static int getMilestonesLength() {
	if (MILESTONES == null) initMilestones();
	int length = MILESTONES.length;
	return length;
}

/**
 * @deprecated
 */
public static boolean matchPattern(String name, String pattern) {
	if (pattern.equals("*")) return true; //$NON-NLS-1$
	if (pattern.indexOf('*') < 0 && pattern.indexOf('?') < 0) {
		pattern += "*"; //$NON-NLS-1$
	}
	StringTokenizer tokenizer = new StringTokenizer(pattern, "*?", true); //$NON-NLS-1$
	int start = 0;
	String previous = ""; //$NON-NLS-1$
	while (tokenizer.hasMoreTokens()) {
		String token = tokenizer.nextToken();
		if (!token.equals("*") && !token.equals("?")) { //$NON-NLS-1$ //$NON-NLS-2$
			if (previous.equals("*")) { //$NON-NLS-1$
				int idx = name.substring(start).indexOf(token);
				if (idx < 0) return false;
				start += idx;
			} else {
				if (previous.equals("?")) start++; //$NON-NLS-1$
				if (!name.substring(start).startsWith(token)) return false;
			}
			start += token.length();
		}
		previous = token;
	}
	if (previous.equals("*")) { //$NON-NLS-1$
		return true;
	} else if (previous.equals("?")) { //$NON-NLS-1$
		return name.length() == start;
	}
	return name.endsWith(previous);
}

/**
 * @deprecated
 */
public static double round(double value) {
	return Math.round(value * 10000) / 10000.0;
}

/**
 * @deprecated
 */
public static double round(double value, int precision) {
	if (precision < 0) {
		throw new IllegalArgumentException("Should have a precision at least greater than 0!");
	}
	if (precision == 0) return (long) Math.floor(value);
	double factor = 10;
	int n = 1;
	while (n++ < precision)
		factor *= 10;
	return Math.round(value * factor) / factor;
}

/**
 * Returns a string to display the given time as a duration
 * formatted as "hh:mm:ss".
 *
 * @param time The time to format as a long.
 * @return The formatted string.
 */
public static String timeChrono(long time) {
	if (time < 1000) { // less than 1s
		return "00:00:00"; //$NON-NLS-1$
	}
	StringBuffer buffer = new StringBuffer();
	int seconds = (int) (time / 1000);
	if (seconds < 60) {
		buffer.append("00:00:"); //$NON-NLS-1$
		if (seconds < 10) buffer.append('0');
		buffer.append(seconds);
	} else {
		int minutes = seconds / 60;
		if (minutes < 60) {
			buffer.append("00:"); //$NON-NLS-1$
			if (minutes < 10) buffer.append('0');
			buffer.append(minutes);
			buffer.append(':');
			seconds = seconds % 60;
			if (seconds < 10) buffer.append('0');
			buffer.append(seconds);
		} else {
			int hours = minutes / 60;
			if (hours < 10) buffer.append('0');
			buffer.append(hours);
			buffer.append(':');
			minutes = minutes % 60;
			if (minutes < 10) buffer.append('0');
			buffer.append(minutes);
			buffer.append(':');
			seconds = seconds % 60;
			if (seconds < 10) buffer.append('0');
			buffer.append(seconds);
		}
	}
	return buffer.toString();
}

/**
 * Returns a string to display the given time as the hour of the day
 * formatted as "hh:mm:ss".
 *
 * @param time The time to format as a long.
 * @return The formatted string.
 */
public static String timeEnd(long time) {
	GregorianCalendar calendar = new GregorianCalendar();
	calendar.add(Calendar.SECOND, (int)(time/1000));
	Date date = calendar.getTime();
	SimpleDateFormat dateFormat = new SimpleDateFormat("KK:mm:ss"); //$NON-NLS-1$
	return dateFormat.format(date);
}

/**
 * Returns a string to display the given time as a duration
 * formatted as:
 *	<ul>
 *	<li>"XXXms" if the duration is less than 0.1s (e.g. "543ms")</li>
 *	<li>"X.YYs" if the duration is less than 1s (e.g. "5.43s")</li>
 *	<li>"XX.Ys" if the duration is less than 1mn (e.g. "54.3s")</li>
 *	<li>"XXmn XXs" if the duration is less than 1h (e.g. "54mn 3s")</li>
 *	<li>"XXh XXmn XXs" if the duration is over than 1h (e.g. "5h 4mn 3s")</li>
 *	</ul>
 *
 * @param time The time to format as a long.
 * @return The formatted string.
 */
public static String timeString(long time) {
	NumberFormat format = NumberFormat.getInstance();
	format.setMaximumFractionDigits(1);
	StringBuffer buffer = new StringBuffer();
	if (time == 0) {
		// print nothing
	} if (time < 100) { // less than 0.1s
		buffer.append(time);
		buffer.append("ms"); //$NON-NLS-1$
	} else if (time < 1000) { // less than 1s
		if ((time%100) != 0) {
			format.setMaximumFractionDigits(2);
		}
		buffer.append(format.format(time/1000.0));
		buffer.append("s"); //$NON-NLS-1$
	} else if (time < Util.ONE_MINUTE) {  // less than 1mn
		if ((time%1000) == 0) {
			buffer.append(time/1000);
		} else {
			buffer.append(format.format(time/1000.0));
		}
		buffer.append("s"); //$NON-NLS-1$
	} else if (time < Util.ONE_HOUR) {  // less than 1h
		buffer.append(time/Util.ONE_MINUTE).append("mn "); //$NON-NLS-1$
		long seconds = time%Util.ONE_MINUTE;
		buffer.append(seconds/1000);
		buffer.append("s"); //$NON-NLS-1$
	} else {  // more than 1h
		long h = time / Util.ONE_HOUR;
		buffer.append(h).append("h "); //$NON-NLS-1$
		long m = (time % Util.ONE_HOUR) / Util.ONE_MINUTE;
		buffer.append(m).append("mn "); //$NON-NLS-1$
		long seconds = m%Util.ONE_MINUTE;
		buffer.append(seconds/1000);
		buffer.append("s"); //$NON-NLS-1$
	}
	return buffer.toString();
}

private Util() {
	// don't instantiate
}

/**
 * Set the milestones.
 *
 * @param items The milestones list (e.g. {@link IPerformancesConstants#V35_MILESTONES}).
 */
public static void setMilestones(String[] items) {
	MILESTONES = items;
}
}
