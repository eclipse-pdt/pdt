/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.utils;

import java.io.CharArrayWriter;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public interface DocumentStore {

	void write() throws CoreException;

	CharArrayWriter getOutput() throws IOException;
	
}