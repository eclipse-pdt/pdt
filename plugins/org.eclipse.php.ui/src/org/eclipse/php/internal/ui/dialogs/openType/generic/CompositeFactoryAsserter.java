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
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import org.eclipse.swt.widgets.Composite;

public class CompositeFactoryAsserter implements CompositeFactory {

	private CompositeFactory innerCompositeFactory;

	public CompositeFactoryAsserter(CompositeFactory compositeFactory) {
		innerCompositeFactory = compositeFactory;
	}

	public Composite createComposite(Composite parent) {
		Composite composite = innerCompositeFactory.createComposite(parent);
		// assert composite != null;

		return composite;
	}
}
