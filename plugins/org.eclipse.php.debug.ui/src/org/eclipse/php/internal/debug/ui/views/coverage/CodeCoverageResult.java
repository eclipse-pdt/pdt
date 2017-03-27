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
package org.eclipse.php.internal.debug.ui.views.coverage;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;

/**
 * Code coverage result.
 */
public class CodeCoverageResult {
	private IModelElement modelElement;
	private CodeCoverageData coverageData;
	private int lines;
	private int covered;
	private int significant;
	private int files;

	public CodeCoverageResult(final int lines, final int covered, final int significant, final int files) {
		this.lines = lines;
		this.covered = covered;
		if (significant == -1)
			this.significant = lines;
		else
			this.significant = significant;
		this.files = files;
	}

	public void addCoverageResult(final CodeCoverageResult data) {
		lines += data.getLines();
		covered += data.getCovered();
		significant += data.getSignificant();
		files += data.getFiles();
	}

	public IModelElement getModelElement() {
		return modelElement;
	}

	public CodeCoverageData getCoverageData() {
		return coverageData;
	}

	public int getCovered() {
		return covered;
	}

	public int getSignificant() {
		return significant;
	}

	public int getFiles() {
		return files;
	}

	public int getLines() {
		return lines;
	}

	public float getPercentage() {
		return (float) covered / significant;
	}

	public void setModelElement(final IModelElement modelElement) {
		this.modelElement = modelElement;
	}

	public void setCoverageData(final CodeCoverageData coverageData) {
		this.coverageData = coverageData;
	}

}