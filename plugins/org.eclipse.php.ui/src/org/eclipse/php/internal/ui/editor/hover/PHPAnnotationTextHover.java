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
package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.dltk.internal.ui.text.hover.AbstractAnnotationHover;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;

public class PHPAnnotationTextHover extends AbstractAnnotationHover implements IPHPTextHover {

	public PHPAnnotationTextHover() {
		super(true);
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}
