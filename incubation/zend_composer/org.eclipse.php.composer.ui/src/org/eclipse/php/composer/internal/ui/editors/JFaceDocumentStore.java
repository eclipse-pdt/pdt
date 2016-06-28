/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.internal.ui.editors;

import java.io.CharArrayWriter;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.composer.core.utils.DocumentStore;

/**
 * Reading and writing Json document from/to {@link IDocument}.
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class JFaceDocumentStore implements DocumentStore {

	private IDocument document;
	private CharArrayWriter caw;

	public JFaceDocumentStore(IDocument document) {
		this.document = document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.ccm.internal.core.config.DocumentStore#write()
	 */
	public void write() throws CoreException {
		document.set(caw.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zend.php.ccm.internal.core.config.DocumentStore#getOutput()
	 */
	public CharArrayWriter getOutput() throws IOException {
		caw = new CharArrayWriter();
		return caw;
	}

}