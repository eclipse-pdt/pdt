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
package org.eclipse.php.internal.ui.editor.validation;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.wst.sse.ui.internal.reconcile.validator.ISourceValidator;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

public class PHPValidator implements IValidator, ISourceValidator {

	public void connect(IDocument document) {

	}

	public void disconnect(IDocument document) {

	}

	public void validate(IRegion dirtyRegion, IValidationContext helper, IReporter reporter) {
		// TODO Auto-generated method stub
		return;
		

	}

	public void cleanup(IReporter reporter) {
		// TODO Auto-generated method stub
		
	}

	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

}
