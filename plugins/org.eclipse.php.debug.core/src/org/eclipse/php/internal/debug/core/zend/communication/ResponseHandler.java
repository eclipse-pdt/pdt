/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/*
 * ResponseHandler.java
 *
 * Created on January 4, 2001, 11:28 AM
 */

package org.eclipse.php.internal.debug.core.zend.communication;

/**
 * 
 * @author eran
 * @version
 */
public interface ResponseHandler {

	public void handleResponse(Object request, Object response);

}
