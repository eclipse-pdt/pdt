/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.server.core.types;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.php.internal.server.core.Activator;
import org.eclipse.php.server.core.types.IServerType;

/**
 * Common implementation for server type.
 */
public class ServerType implements IServerType {

	/**
	 * Default generic PHP server type id.
	 */
	public static final String GENERIC_PHP_SERVER_ID = Activator.PLUGIN_ID + ".genericServerType"; //$NON-NLS-1$

	private IConfigurationElement element;

	private String name;
	private String description;
	private String id;

	/**
	 * Creates and returns server type instance created with the use of provided
	 * configuration element.
	 * 
	 * @param element
	 * @return server type
	 */
	public static IServerType create(IConfigurationElement element) {
		ServerType type = new ServerType(element);
		return type.construct();
	}

	private IServerType construct() {
		this.id = element.getAttribute("id"); //$NON-NLS-1$
		this.name = element.getAttribute("name"); //$NON-NLS-1$
		this.description = element.getAttribute("description"); //$NON-NLS-1$
		return this;
	}

	private ServerType(IConfigurationElement element) {
		this.element = element;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.core.types.IServerType#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.server.core.types.IServerType#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.server.core.types.IServerType#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

}
