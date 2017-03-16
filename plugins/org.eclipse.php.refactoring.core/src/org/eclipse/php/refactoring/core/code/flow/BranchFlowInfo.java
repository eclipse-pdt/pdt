/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.code.flow;

import java.util.HashSet;

import org.eclipse.php.core.ast.nodes.Identifier;

class BranchFlowInfo extends FlowInfo {

	public BranchFlowInfo(Identifier label, FlowContext context) {
		super(NO_RETURN);
		fBranches = new HashSet(2);
		fBranches.add(makeString(label));
	}
}
