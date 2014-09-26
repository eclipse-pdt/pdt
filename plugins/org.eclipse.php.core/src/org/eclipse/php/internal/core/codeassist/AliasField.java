/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceField;

public class AliasField extends SourceField {

	private String alias;
	IField field;

	public AliasField(ModelElement sourceModule, String name, String alias) {
		super((ModelElement) sourceModule.getParent(), name);
		this.alias = alias;
		this.field = (IField) sourceModule;
	}

	@Override
	public ISourceRange getSourceRange() throws ModelException {
		return ((IField) field).getSourceRange();
	}

	@Override
	public int getFlags() throws ModelException {
		return ((IField) field).getFlags();
	}

	public String getAlias() {
		return alias;
	}

	public IModelElement getField() {
		return field;
	}

}
