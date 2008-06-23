/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 *
 */
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.List;

/**
 * @author seva, 2007
 *
 */
public interface IPHPUserModel extends IPhpModel {

	public abstract void addModelListener(ModelListener l);

	public abstract List<ModelListener> getModelListenerList();

	public abstract void removeModelListener(ModelListener l);

}