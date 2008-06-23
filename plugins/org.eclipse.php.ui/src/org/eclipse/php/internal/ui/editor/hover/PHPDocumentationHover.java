/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.dltk.internal.ui.text.hover.DocumentationHover;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;

public class PHPDocumentationHover extends DocumentationHover implements IPHPTextHover {

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}
