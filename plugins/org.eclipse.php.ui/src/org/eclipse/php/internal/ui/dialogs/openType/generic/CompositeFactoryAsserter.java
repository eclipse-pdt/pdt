/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import org.eclipse.swt.widgets.Composite;

public class CompositeFactoryAsserter implements CompositeFactory {

	private CompositeFactory innerCompositeFactory;

	public CompositeFactoryAsserter(CompositeFactory compositeFactory) {
		innerCompositeFactory = compositeFactory;
	}

	@Override
	public Composite createComposite(Composite parent) {
		Composite composite = innerCompositeFactory.createComposite(parent);
		// assert composite != null;

		return composite;
	}
}
