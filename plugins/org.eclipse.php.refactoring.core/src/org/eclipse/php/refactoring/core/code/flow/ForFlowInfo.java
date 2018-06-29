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

class ForFlowInfo extends FlowInfo {

	public void mergeInitializer(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}
		mergeAccessModeSequential(info, context);
	}

	public void mergeCondition(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}
		mergeAccessModeSequential(info, context);
	}

	public void mergeIncrement(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}

		info.mergeEmptyCondition(context);
		mergeAccessModeSequential(info, context);
	}

	public void mergeAction(FlowInfo info, FlowContext context) {
		if (info == null) {
			return;
		}

		info.mergeEmptyCondition(context);

		mergeSequential(info, context);
	}
}
