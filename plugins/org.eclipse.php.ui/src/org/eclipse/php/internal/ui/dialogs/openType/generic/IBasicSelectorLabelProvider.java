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
package org.eclipse.php.internal.ui.dialogs.openType.generic;

import org.eclipse.swt.graphics.Image;

public interface IBasicSelectorLabelProvider {
    public Image getElementImage(Object element);
    
    /**
     * The description is for visual purpuses, such as ginig the location of the element as well as the name
     * @param element
     * @return
     */
    public String getElementDescription(Object element);
    
    /**
     * The name is for comparison reasons - is the key that identifies the element.
     * 
     * @param element
     * @return
     */
    public String getElementName(Object element);
}
