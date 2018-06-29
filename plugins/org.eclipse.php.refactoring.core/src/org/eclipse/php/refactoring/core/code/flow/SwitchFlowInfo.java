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

class SwitchFlowInfo extends FlowInfo {
	private GenericConditionalFlowInfo fCases;
	private boolean fHasNullCaseInfo;

	public SwitchFlowInfo() {
		fCases = new GenericConditionalFlowInfo();
	}

	public void mergeTest(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}
		mergeSequential(info, context);
	}

	public void mergeCase(FlowInfo info, FlowContext context) {
		if (info == null) {
			fHasNullCaseInfo = true;
			return;
		}
		fCases.mergeConditional(info, context);
	}

	public void mergeDefault(boolean defaultCaseExists, FlowContext context) {
		if (!defaultCaseExists || fHasNullCaseInfo) {
			fCases.mergeEmptyCondition(context);
		}
		mergeSequential(fCases, context);
	}
}
