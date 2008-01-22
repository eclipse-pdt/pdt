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

import java.io.Serializable;
import java.util.Iterator;

public interface PHPDocBlock extends Serializable {

	String getShortDescription();

	String getLongDescription();

	Iterator<PHPDocTag> getTags();

	PHPDocTag[] getTagsAsArray();

	Iterator<PHPDocTag> getTags(int id);

	public boolean hasTagOf(int id);

	int getStartPosition();

	int getEndPosition();

	void setStartPosition(int value);

	void setEndPosition(int value);

	void setShortDescription(String shortDescription);

	boolean containsPosition(int position);
}
