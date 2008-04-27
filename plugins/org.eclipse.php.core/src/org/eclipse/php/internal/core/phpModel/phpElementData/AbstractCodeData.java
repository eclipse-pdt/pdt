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
package org.eclipse.php.internal.core.phpModel.phpElementData;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

public abstract class AbstractCodeData implements CodeData {

	protected String name;
	private boolean isUserCode;
	protected UserData userData;

	/**
	 * Construct a new AbstractCodeData that is not user data.
	 *
	 * @param name        The name of the Code Data.
	 * @param description The description of the Code Data.
	 */
	public AbstractCodeData(String name) {
		this(name, null);
	}

	/**
	 * Construct a new AbstractCodeData.
	 *
	 * @param name        The name of the Code Data.
	 * @param description The description of the Code Data.
	 * @param userData
	 */
	public AbstractCodeData(String name, UserData userData) {
		this.name = name;
		this.userData = userData;
		this.isUserCode = userData != null;
	}

	/**
	 * Returns the name of the CodeData.
	 *
	 * @return The name of the CodeData.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * return true if this CodeData is user code
	 */
	public final boolean isUserCode() {
		return isUserCode;
	}

	/**
	 * Returns the user data
	 *
	 * @return the user data
	 */
	public final UserData getUserData() {
		return userData;
	}

	/**
	 * Compare this object to anther object.
	 * If the other object is not instanceof CodeData return -1;
	 * Compares the name of this CodeData to the name of the other Code data, if the result is not 0 return the result.
	 *
	 * @param o the object that we compare to.
	 * @return
	 */
	public int compareTo(CodeData other) {
		int compared = name.compareToIgnoreCase(other.getName());
		if (compared != 0) {
			return compared;
		}
		boolean otherIsUserCode = other.isUserCode();
		if (!isUserCode) {
			if (!otherIsUserCode) {
				return 0;
			}
			return -1;
		}
		if (!otherIsUserCode) {
			return 1;
		}
		return userData.getFileName().compareTo(other.getUserData().getFileName());
	}

	public String toString() {
		if (getUserData() == null) {
			return name;
		}
		return name + " in " + getUserData().getFileName(); //$NON-NLS-1$
	}

	/**
	 * Returns an object which is an instance of the given class
	 * associated with this object. Returns <code>null</code> if
	 * no such object can be found.
	 * <p>
	 * This implementation of the method declared by <code>IAdaptable</code>
	 * passes the request along to the platform's adapter manager; roughly
	 * <code>Platform.getAdapterManager().getAdapter(this, adapter)</code>.
	 * Subclasses may override this method (however, if they do so, they
	 * should invoke the method on their superclass to ensure that the
	 * Platform's adapter manager is consulted).
	 * </p>
	 *
	 * @param adapter the class to adapt to
	 * @return the adapted object or <code>null</code>
	 * @see IAdaptable#getAdapter(Class)
	 * @see Platform#getAdapterManager()
	 */
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}