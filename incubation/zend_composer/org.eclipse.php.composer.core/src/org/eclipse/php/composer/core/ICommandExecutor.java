/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ICommandExecutor {

	void setCommand(List<String> command);

	void setCommand(String... command);

	int run(IProgressMonitor monitor) throws IOException;

	void setWorkingDirectory(String workkingDirectory);

	String getCommandOutput();

	void setEnvironmentVar(String key, String value);

	String getCommandError();

}
