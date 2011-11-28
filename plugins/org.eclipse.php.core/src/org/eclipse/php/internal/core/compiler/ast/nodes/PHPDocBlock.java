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
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;

public class PHPDocBlock extends Comment {

	private String shortDescription;
	private String longDescription;
	private PHPDocTag[] tags;
	private List<Scalar> texts;

	public PHPDocBlock(int start, int end, String shortDescription,
			String longDescription, PHPDocTag[] tags) {
		this(start, end, shortDescription, longDescription, tags, null);
	}

	public PHPDocBlock(int start, int end, String shortDescription,
			String longDescription, PHPDocTag[] tags, List<Scalar> texts) {
		super(start, end, Comment.TYPE_PHPDOC);
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.tags = tags;
		this.texts = texts;
	}

	public void traverse(ASTVisitor visitor) throws Exception {
		boolean visit = visitor.visit(this);
		if (visit) {
			for (PHPDocTag tag : tags) {
				tag.traverse(visitor);
			}
		}
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.PHP_DOC_BLOCK;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public PHPDocTag[] getTags() {
		return tags;
	}

	public List<Scalar> getTexts() {
		return texts;
	}

	public PHPDocTag[] getTags(int kind) {
		List<PHPDocTag> res = new LinkedList<PHPDocTag>();
		if (tags != null) {
			for (PHPDocTag tag : tags) {
				if (tag.getTagKind() == kind) {
					res.add(tag);
				}
			}
		}
		return res.toArray(new PHPDocTag[res.size()]);
	}

	public void adjustStart(int start) {
		setStart(sourceStart() + start);
		setEnd(sourceEnd() + start);

		for (PHPDocTag tag : tags) {
			tag.adjustStart(start);
		}
	}

}
