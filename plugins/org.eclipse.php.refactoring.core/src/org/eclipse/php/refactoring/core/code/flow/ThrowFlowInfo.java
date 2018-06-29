/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.code.flow;

import org.eclipse.php.core.ast.nodes.ITypeBinding;

class ThrowFlowInfo extends FlowInfo {

	public ThrowFlowInfo() {
		super(THROW);
	}

	public void merge(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}

		assignAccessMode(info);
	}

	public void mergeException(ITypeBinding exception, FlowContext context) {
		if (exception != null && context.isExceptionCaught(exception)) {
			addException(exception);
		}
	}
}
