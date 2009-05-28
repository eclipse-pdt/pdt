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
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;

/**
 * This strategy completes global classes 
 * @author michael
 */
public class GlobalInterfacesStrategy extends GlobalTypesStrategy {

	public GlobalInterfacesStrategy(ICompletionContext context) {
		super(context, new IElementFilter() {
			public boolean filter(IModelElement element) {
				try {
					return !PHPFlags.isInterface(((IType)element).getFlags());
				} catch (ModelException e) {
					if (DLTKCore.DEBUG_COMPLETION) {
						e.printStackTrace();
					}
				}
				return false;
			}
		});
	}
	
	public String getSuffix(AbstractCompletionContext abstractContext) {
		return ""; //$NON-NLS-1$
	}
}
