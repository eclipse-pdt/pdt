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
package org.eclipse.php.internal.core.documentModel.dom;

import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.validate.ValidationReporter;
import org.eclipse.wst.xml.core.internal.validate.ValidationComponent;

/**
 * NullValidator class is intended to be a replacement of null for
 * ValidationComponent type.
 */
public class NullValidator extends ValidationComponent {

	public NullValidator() {
		super();
	}

	@Override
	public void validate(IndexedRegion node) {
		return;
	}

	@Override
	public void setReporter(ValidationReporter reporter) {
		return;
	}

}
