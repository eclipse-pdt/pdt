/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.core.engine;

/**
 * Interface for profile session listeners.
 */
public interface IProfileSessionListener {

	public void profileSessionAdded(ProfilerDB db);

	public void profileSessionRemoved(ProfilerDB db);

	public void currentSessionChanged(ProfilerDB current);
}
