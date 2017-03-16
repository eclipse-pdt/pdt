/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ltk.core.refactoring.TextEditBasedChange;
import org.eclipse.ltk.core.refactoring.TextEditBasedChangeGroup;
import org.eclipse.ltk.ui.refactoring.LanguageElementNode;
import org.eclipse.ltk.ui.refactoring.TextEditChangeNode;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.php.refactoring.ui.rename.ASTNodeImageProvider;
import org.eclipse.php.refactoring.ui.rename.ASTNodeLabels;
import org.eclipse.text.edits.TextEdit;

/**
 * Description: Represents the tree node on the rename view
 * 
 * @author Roy, 2007
 * @inspiredby JDT
 */
public class PhpRefactoringChangeNode extends TextEditChangeNode {

	static final ChildNode[] EMPTY_CHILDREN = new ChildNode[0];

	private static class PhpLanguageNode extends LanguageElementNode {

		private ASTNode fphpElement;
		private static final ASTNodeImageProvider fgImageProvider = new ASTNodeImageProvider();

		public PhpLanguageNode(TextEditChangeNode parent, ASTNode element) {
			super(parent);
			fphpElement = element;
			Assert.isNotNull(fphpElement);
		}

		public PhpLanguageNode(ChildNode parent, ASTNode element) {
			super(parent);
			assert element != null;

			fphpElement = element;
		}

		public String getText() {
			return ASTNodeLabels.getElementLabel(fphpElement,
					ASTNodeLabels.M_PARAMETER_TYPES | ASTNodeLabels.M_PARAMETER_NAMES);
		}

		public ImageDescriptor getImageDescriptor() {
			return fgImageProvider.getPHPImageDescriptor(fphpElement,
					ASTNodeImageProvider.OVERLAY_ICONS | ASTNodeImageProvider.SMALL_ICONS);
		}

		public IRegion getTextRange() throws CoreException {
			return new Region(fphpElement.getStart(), fphpElement.getLength());
		}
	}

	public PhpRefactoringChangeNode(TextEditBasedChange change) {
		super(change);
	}

	private static class OffsetComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			TextEditBasedChangeGroup c1 = (TextEditBasedChangeGroup) o1;
			TextEditBasedChangeGroup c2 = (TextEditBasedChangeGroup) o2;
			int p1 = getOffset(c1);
			int p2 = getOffset(c2);
			if (p1 < p2)
				return -1;
			if (p1 > p2)
				return 1;
			// same offset
			return 0;
		}

		private int getOffset(TextEditBasedChangeGroup edit) {
			return edit.getRegion().getOffset();
		}
	}

	protected ChildNode[] createChildNodes() {
		final TextEditBasedChange change = getTextEditBasedChange();
		Program program = (Program) change.getAdapter(Program.class);
		if (program != null) {
			List children = new ArrayList(5);
			Map map = new HashMap(20);
			TextEditBasedChangeGroup[] changes = getSortedChangeGroups(change);
			for (int i = 0; i < changes.length; i++) {
				final TextEditBasedChangeGroup tec = changes[i];
				try {
					final ASTNode element = getModifiedPhpElement(tec, program);
					addNode(program, children, map, tec, element);
				} catch (Exception e) {
					RefactoringUIPlugin.log(e);
				}
			}
			return (ChildNode[]) children.toArray(new ChildNode[children.size()]);
		} else {
			return EMPTY_CHILDREN;
		}
	}

	/**
	 * @param program
	 * @param children
	 * @param map
	 * @param tec
	 * @param element
	 */
	private void addNode(Program program, List children, Map map, final TextEditBasedChangeGroup tec,
			final ASTNode element) {
		if (element.getType() == ASTNode.PROGRAM) {
			children.add(createTextEditGroupNode(this, tec));
		} else {
			PhpLanguageNode pjce = getChangeElement(map, element, children, this);
			pjce.addChild(createTextEditGroupNode(pjce, tec));
		}
	}

	private TextEditBasedChangeGroup[] getSortedChangeGroups(TextEditBasedChange change) {
		TextEditBasedChangeGroup[] edits = change.getChangeGroups();
		List result = new ArrayList(edits.length);
		for (int i = 0; i < edits.length; i++) {
			if (!edits[i].getTextEditGroup().isEmpty())
				result.add(edits[i]);
		}
		Comparator comparator = new OffsetComparator();
		Collections.sort(result, comparator);
		return (TextEditBasedChangeGroup[]) result.toArray(new TextEditBasedChangeGroup[result.size()]);
	}

	private ASTNode getModifiedPhpElement(TextEditBasedChangeGroup edit, Program program) throws Exception {
		IRegion range = edit.getRegion();
		if (range.getOffset() == 0 && range.getLength() == 0)
			return program;
		ASTNode result = program.getElementAt(range.getOffset());

		if (result == null)
			return program;

		return getParentContext(result);
	}

	private PhpLanguageNode getChangeElement(Map map, ASTNode element, List children, TextEditChangeNode cunitChange) {
		PhpLanguageNode result = (PhpLanguageNode) map.get(element);
		if (result != null)
			return result;

		final int type = element.getType();
		if (type == ASTNode.CLASS_DECLARATION || type == ASTNode.INTERFACE_DECLARATION
				|| type == ASTNode.FUNCTION_DECLARATION) {
			result = new PhpLanguageNode(cunitChange, element);
			children.add(result);
			map.put(element, result);
		} else {
			assert element.getType() == ASTNode.METHOD_DECLARATION;

			ASTNode parentNode = getParentContext(element);
			final PhpLanguageNode parentChange = getChangeElement(map, parentNode, children, cunitChange);
			result = new PhpLanguageNode(parentChange, element);
			parentChange.addChild(result);
			map.put(element, result);
		}

		return result;
	}

	private ASTNode getParentContext(ASTNode element) {
		element = element.getParent();
		switch (element.getType()) {
		case ASTNode.PROGRAM:
		case ASTNode.CLASS_DECLARATION:
		case ASTNode.INTERFACE_DECLARATION:
		case ASTNode.METHOD_DECLARATION:
			return element;
		case ASTNode.FUNCTION_DECLARATION:
			if (element.getParent().getType() == ASTNode.METHOD_DECLARATION) {
				return element.getParent();
			} else {
				return element;
			}
		default:
			return getParentContext(element);
		}
	}

	private boolean coveredBy(TextEditBasedChangeGroup group, IRegion sourceRegion) {
		int sLength = sourceRegion.getLength();
		if (sLength == 0)
			return false;
		int sOffset = sourceRegion.getOffset();
		int sEnd = sOffset + sLength - 1;
		TextEdit[] edits = group.getTextEdits();
		for (int i = 0; i < edits.length; i++) {
			TextEdit edit = edits[i];
			if (edit.isDeleted())
				return false;
			int rOffset = edit.getOffset();
			int rLength = edit.getLength();
			int rEnd = rOffset + rLength - 1;
			if (rLength == 0) {
				if (!(sOffset < rOffset && rOffset <= sEnd))
					return false;
			} else {
				if (!(sOffset <= rOffset && rEnd <= sEnd))
					return false;
			}
		}
		return true;
	}
}
