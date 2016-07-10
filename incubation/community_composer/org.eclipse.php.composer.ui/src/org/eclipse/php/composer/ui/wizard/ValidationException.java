/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.wizard;

/**
 * Throw this exception with the corresponding {@link Severity} into your
 * {@link AbstractValidator} implementation
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * 
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	private Severity severity;

	public enum Severity {
		WARNING, ERROR
	}

	public ValidationException(String message, Severity severity) {
		super(message);
		this.severity = severity;
	}

	public Severity getSeverity() {
		return severity;
	}
}
