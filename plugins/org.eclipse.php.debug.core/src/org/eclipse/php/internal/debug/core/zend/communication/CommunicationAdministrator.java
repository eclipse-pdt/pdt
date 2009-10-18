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
/*
 * CommunicationAdministrator.java
 *
 * Created on July 26, 2000, 12:11 PM
 */

package org.eclipse.php.internal.debug.core.zend.communication;

/**
 * 
 * @author erez
 * @version
 */
public interface CommunicationAdministrator {

	public void connectionEstablished();

	public void connectionClosed();
}
