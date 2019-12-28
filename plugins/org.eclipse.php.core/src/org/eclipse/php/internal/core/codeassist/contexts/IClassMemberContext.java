/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.IType;
import org.eclipse.php.core.codeassist.ICompletionContext;

public interface IClassMemberContext extends ICompletionContext {
	/**
	 * Trigger type of the member invocation
	 */
	public enum Trigger {
		/** Class trigger type: '::' */
		CLASS("::"), //$NON-NLS-1$
		/** Object trigger type: '->' */
		OBJECT("->"),; //$NON-NLS-1$

		String name;

		Trigger(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public Trigger getTriggerType();

	public IType[] getLhsTypes();
}
