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
package org.eclipse.php.core;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.mixin.IMixinRequestor;

public abstract class PHPMixinBuildVisitorExtension extends ASTVisitor {

	private ISourceModule sourceModule;
	private boolean moduleAvailable;
	private IMixinRequestor requestor;

	public ISourceModule getSourceModule() {
		return sourceModule;
	}

	public void setSourceModule(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
	}

	public boolean isModuleAvailable() {
		return moduleAvailable;
	}

	public void setModuleAvailable(boolean moduleAvailable) {
		this.moduleAvailable = moduleAvailable;
	}

	public IMixinRequestor getRequestor() {
		return requestor;
	}

	public void setRequestor(IMixinRequestor requestor) {
		this.requestor = requestor;
	}

}
