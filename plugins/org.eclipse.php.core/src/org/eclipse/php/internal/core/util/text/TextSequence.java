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
package org.eclipse.php.internal.core.util.text;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

public interface TextSequence extends CharSequence {

	public @NonNull IStructuredDocumentRegion getSource();

	public int getOriginalOffset(int index);

	public @NonNull TextSequence subTextSequence(int start, int end);

	public @NonNull TextSequence cutTextSequence(int start, int end);

}
