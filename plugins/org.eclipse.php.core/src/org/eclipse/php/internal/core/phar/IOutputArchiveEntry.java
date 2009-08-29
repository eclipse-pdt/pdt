/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import org.eclipse.dltk.core.IArchiveEntry;

public interface IOutputArchiveEntry extends IArchiveEntry {

	void setMethod(int deflated);

	void setTime(long lastModified);

	void setSize(int size);

	void setCrc(long value);

}
