/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.rewrite;

import org.eclipse.jface.text.IRegion;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.TextEditGroup;

public class TrackedNodePosition implements ITrackedNodePosition {

	private final TextEditGroup group;
	private final ASTNode node;

	public TrackedNodePosition(TextEditGroup group, ASTNode node) {
		this.group = group;
		this.node = node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.corext.dom.ITrackedNodePosition#getStartPosition
	 * ()
	 */
	public int getStartPosition() {
		if (this.group.isEmpty()) {
			return this.node.getStart();
		}
		IRegion coverage = TextEdit.getCoverage(this.group.getTextEdits());
		if (coverage == null) {
			return this.node.getStart();
		}
		return coverage.getOffset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.internal.corext.dom.ITrackedNodePosition#getLength()
	 */
	public int getLength() {
		if (this.group.isEmpty()) {
			return this.node.getLength();
		}
		IRegion coverage = TextEdit.getCoverage(this.group.getTextEdits());
		if (coverage == null) {
			return this.node.getLength();
		}
		return coverage.getLength();
	}

}
