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
package org.eclipse.php.internal.debug.core.zend.debugger;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

/**
 * Code coverage data descriptor.
 */
public class CodeCoverageData implements IAdaptable {

	private String fURL;
	private String fFileName;
	private String fLocalFileName;
	private int fLinesNum;
	private int fPhpLinesNum;
	private byte[] fCoverageBitmask;
	private byte[] fSignificanceBitmask;

	public CodeCoverageData(String fileName, int linesNum, byte[] coverageBitmask) {
		fFileName = fileName;
		fLinesNum = linesNum;
		fCoverageBitmask = coverageBitmask;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public String getURL() {
		return fURL;
	}

	public String getFileName() {
		return fFileName;
	}

	public String getLocalFileName() {
		return fLocalFileName;
	}

	public int getLinesNum() {
		return fLinesNum;
	}

	public int getPhpLinesNum() {
		return fPhpLinesNum;
	}

	public byte[] getCoverageBitmask() {
		return fCoverageBitmask;
	}

	public byte[] getSignificanceBitmask() {
		return fSignificanceBitmask;
	}

	public void setURL(String url) {
		fURL = url;
	}

	public void setFileName(String fileName) {
		fFileName = fileName;
	}

	public void setLocalFileName(String localFileName) {
		fLocalFileName = localFileName;
	}

	public void setLinesNum(int linesNum) {
		fLinesNum = linesNum;
	}

	public void setCoverageBitmask(byte[] coverageBitmask) {
		fCoverageBitmask = coverageBitmask;
	}

	public void setPhpLinesNum(int phpLinesNum) {
		fPhpLinesNum = phpLinesNum;
	}

	public void setSignificanceBitmask(byte[] significanceBitmask) {
		fSignificanceBitmask = significanceBitmask;
	}
}
