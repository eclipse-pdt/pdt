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
package org.eclipse.php.internal.core.documentModel.parser.regions;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This is a base class for optional PHP script regions
 * The default extension is {@link PhpScriptRegion}
 * @author Roy, 2007
 */
public interface IPhpScriptRegion extends ITextRegion {

	public abstract ITextRegion[] getPhpTokens(int from, int min) throws BadLocationException;

	public abstract ITextRegion getPhpToken(int offset) throws BadLocationException;

}
