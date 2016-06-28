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
package org.eclipse.php.composer.core.model;

import java.util.List;

/**
 * @author Wojciech Galanciak, 2013
 *
 */
public interface IRepositories extends IModelElement {

	List<IRepository> getRepositories();

	void removeRepository(IRepository repository, boolean update);

	void addRepository(IRepository repository, boolean update);
	
	void setRepositories(List<IRepository> repositories);

	String serialize();

	IRepositories deserialize(String repositories);

	boolean isDirty();
	
	boolean isArray();

}