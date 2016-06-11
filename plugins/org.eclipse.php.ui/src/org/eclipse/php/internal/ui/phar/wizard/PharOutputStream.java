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
package org.eclipse.php.internal.ui.phar.wizard;

import java.io.FilterOutputStream;
import java.io.OutputStream;

import org.eclipse.php.internal.core.phar.IOutputArchiveEntry;
import org.eclipse.php.internal.core.phar.IStub;

@Deprecated
public class PharOutputStream extends FilterOutputStream {

	public PharOutputStream(OutputStream out, IStub manifest) {
		super(out);
		// TODO Auto-generated constructor stub
	}

	public PharOutputStream(OutputStream out) {
		super(out);
	}

	public void putNextEntry(IOutputArchiveEntry newEntry) {
		// TODO Auto-generated method stub

	}

}
