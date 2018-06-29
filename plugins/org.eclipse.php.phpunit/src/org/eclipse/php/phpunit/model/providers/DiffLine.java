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
package org.eclipse.php.phpunit.model.providers;

import java.util.ArrayList;
import java.util.List;

public class DiffLine {
	private DiffLine parentDiff;
	private String text;
	private final List<DiffLine> children;

	public DiffLine(String text, DiffLine parentDiff) {
		this.text = text;
		children = new ArrayList<>();
		this.parentDiff = parentDiff;
		if (parentDiff != null) {
			parentDiff.addChild(this);
		}
	}

	private void addChild(DiffLine diffLine) {
		children.add(diffLine);
	}

	public Object getParent() {
		return parentDiff;
	}

	public void setParent(DiffLine parentDiff) {
		this.parentDiff = parentDiff;

	}

	public List<DiffLine> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return text;
	}

}
