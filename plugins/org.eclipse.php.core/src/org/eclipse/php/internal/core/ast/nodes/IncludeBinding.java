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
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;

/**
 * @see IIncludeBinding
 * @author Roy 2008
 */
public class IncludeBinding implements IIncludeBinding {

	private final ISourceModule model;
	private final String name;

	public IncludeBinding(ISourceModule model, Include includeDeclaration) {
		super();
		this.model = model;		
		final Expression expression = includeDeclaration.getExpression();
		String scalars = ASTNodes.getScalars(expression);
		this.name = scalars.replace("\'", "").replace("\"", "");
	}	
	
	public String getName() {
		return name;
	}

	public String[] getNameComponents() {
		return null;
	}

	public String getKey() {
		return model.getHandleIdentifier() + "#" + name;
	}

	public int getKind() {
		return IBinding.INCLUDE;
	}

	public int getModifiers() {
		return 0;
	}

	public IModelElement getPHPElement() {
		return FileNetworkUtility.findSourceModule(this.model, this.name);
	}

	public boolean isDeprecated() {
		return false;
	}

	public boolean isSynthetic() {
		return false;
	}
}
