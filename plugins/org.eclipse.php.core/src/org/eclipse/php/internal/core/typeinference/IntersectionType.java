/*******************************************************************************
 * Copyright (c) 2023 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.ti.types.CombinedType;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class IntersectionType extends CombinedType {
	public IntersectionType() {
	}

	public IntersectionType(IEvaluatedType[] list) {
		super();
		for (IEvaluatedType ev : list) {
			appendType(ev);
		}
	}

	@Override
	public String getTypeName() {
		StringBuilder sb = new StringBuilder("Combined <"); //$NON-NLS-1$
		for (IEvaluatedType ev : getTypes()) {
			if (sb.length() != 10) {
				sb.append(", ");//$NON-NLS-1$
			}
			sb.append(ev.getTypeName());
		}
		sb.append('>');

		return sb.toString();

	}
}
