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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PHPDocBlockImp implements PHPDocBlock {

	private static final String DEFAULT_DESCRIPTION_TEXT = "Enter description here..."; //$NON-NLS-1$

	private String shortDescription;
	private String longDescription;
	private PHPDocTag[] tags;
	private int type;
	private int startPosition;
	private int endPosition;

	public PHPDocBlockImp(String shortDescription, String longDescription, PHPDocTag[] tags, int type) {
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.tags = tags;
		this.type = type;
	}

	public String getShortDescription() {
		if (shortDescription == null) {
			return DEFAULT_DESCRIPTION_TEXT;
		}
		return shortDescription;
	}

	public String getLongDescription() {
		if (longDescription == null) {
			return ""; //$NON-NLS-1$
		}
		return longDescription;
	}

	public PHPDocTag[] getTagsAsArray() {
		return tags;
	}

	public Iterator getTags() {
		return tags != null ? Arrays.asList(tags).iterator() : null;
	}

	public Iterator getTags(int id) {
		if (tags == null) {
			return null;
		}
		ArrayList rv = new ArrayList(tags.length);
		for (int i = 0; i < tags.length; i++) {
			PHPDocTag tag = tags[i];
			if (tag.getID() == id) {
				rv.add(tag);
			}
		}
		return rv.iterator();
	}

	public int getType() {
		return type;
	}

	public void setStartPosition(int value) {
		startPosition = value;
	}

	public void setEndPosition(int value) {
		endPosition = value;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public boolean containsPosition(int position) {
		return position > getStartPosition() && position <= getEndPosition();
	}

	public boolean hasTagOf(int id) {
		final Iterator tagsOf = getTags(id);
		return tagsOf != null && tagsOf.hasNext();
	}
	
	
}
