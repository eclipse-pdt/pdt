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
package org.eclipse.php.internal.ui.util;

import org.eclipse.php.internal.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.util.IPHPOutlineElementComparer;

public class PHPElementComparer implements IPHPOutlineElementComparer {

	public boolean equals(Object a, Object b) {
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		if (a.getClass() != b.getClass())
			return false;
		if (a.equals(b))
			return true;
		if (a instanceof PHPCodeData) {
			PHPCodeData data1 = (PHPCodeData) a;
			PHPCodeData data2 = (PHPCodeData) b;
			if (data1.getUserData() != null || data2.getUserData() != null) {
				if (data1.getUserData() == null || data2.getUserData() == null)
					return false;
				if (!data1.getUserData().getFileName().equals(data2.getUserData().getFileName()))
					return false;
			}
			if (!data1.getName().equals(data2.getName()))
				return false;
			if (data1.getContainer() != null) {
				if (data2.getContainer() != null) {
					if (!data1.getContainer().getName().equals(data2.getContainer().getName())) {
						return false;
					}
				} else {
					return false;
				}

			}

			return true;
		} else if (a instanceof PHPElementImpl) {
			//			PHPElementImpl phpElement = (PHPElementImpl) a;
			//			PHPElementImpl phpElement2= (PHPElementImpl)b;
			//			phpElement.getNodeValue().equals(phpElement2.getNodeValue());

		}
		return false;
	}

	public int hashCode(Object element) {
		if (element instanceof PHPCodeData) {
			PHPCodeData codeData = (PHPCodeData) element;
			String s;
			String containerName = codeData.getContainer() != null ? codeData.getContainer().getName() : "";
			if (codeData.isUserCode()) {
				s = codeData.getUserData().getFileName() + ":::" + codeData.getName() + ":::" + containerName;
			} else {
				s = codeData.getName();
			}
			return s.hashCode();
		}
		return element.hashCode();
	}

	public boolean supports(Object o) {
		return (o instanceof PHPCodeData) || (o instanceof PHPElementImpl);
	}

}
