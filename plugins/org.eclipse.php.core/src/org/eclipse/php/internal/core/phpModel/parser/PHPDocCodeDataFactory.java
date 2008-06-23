/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser;

import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocTagData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPProjectModelVisitor;
import org.eclipse.php.internal.core.util.Visitor;


public class PHPDocCodeDataFactory {

	public static final CodeData[] EMPTY_CODE_DATA_ARRAY = new CodeData[0];
	public static final PHPDocTagData[] EMPTY_TAG_DATA_ARRAY = new PHPDocTagData[0];

	/**
	 * Returns new PHPDocTagData.
	 */
	public static PHPDocTagData createPHPDocTagData(String name, String description) {
		return new PHPDocTagDataImp(name, description);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	static class PHPDocTagDataImp extends PHPDocTagData {

		private PHPDocTagDataImp(String name, String description) {
			super(name, description);
		}

		public void accept(Visitor v) {
			((PHPProjectModelVisitor) v).visit(this);
		}

	}
}
