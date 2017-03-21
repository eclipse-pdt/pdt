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
package org.eclipse.php.ui.text.correction;

import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.ISourceModule;

/**
 * This extension add access to {@link IProblemIdentifier} instead of int in
 * hasCorrections()
 * 
 * @since 3.2.1
 */
public interface IQuickFixProcessorExtension {
	public boolean hasCorrections(ISourceModule unit, IProblemIdentifier identifier);
}
