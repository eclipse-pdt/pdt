/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.documentModel.validate;

import org.eclipse.wst.sse.core.internal.validate.ValidationAdapter;
import org.eclipse.wst.sse.core.internal.validate.ValidationReporter;
import org.eclipse.wst.xml.core.internal.validate.AbstractPropagatingValidator;
import org.eclipse.wst.xml.core.internal.validate.ValidationComponent;

public class HTMLElementPropagatingValidator extends AbstractPropagatingValidator {

	private ValidationComponent validator = new HTMLEmptyValidator();

	public HTMLElementPropagatingValidator() {
		super();
	}

	public boolean isAdapterForType(Object type) {
		return (type == HTMLElementPropagatingValidator.class || super.isAdapterForType(type));
	}

	public void setReporter(ValidationReporter reporter) {
		super.setReporter(reporter);
		validator.setReporter(reporter);
	}

	protected ValidationComponent getPropagatee() {
		return this;
	}

	protected ValidationAdapter getValidator() {
		return validator;
	}
}