package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;

public class PHPDocBlock extends Comment {

	private String shortDescription;
	private PHPDocTag[] tags;

	public PHPDocBlock(int start, int end, String shortDescription, PHPDocTag[] tags) {
		super(start, end, Comment.TYPE_PHPDOC);
		this.shortDescription = shortDescription;
		this.tags = tags;
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

	public PHPDocTag[] getTags() {
		return tags;
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

	public void adjustStart(int start){
		setStart(sourceStart() + start);
		setEnd(sourceEnd() + start);
		
		for (PHPDocTag tag : tags) {
			tag.adjustStart(start);
		}
	}
		
}
