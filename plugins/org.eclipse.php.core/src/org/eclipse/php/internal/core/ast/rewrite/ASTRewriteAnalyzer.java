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
package org.eclipse.php.internal.core.ast.rewrite;

import java.io.IOException;
import java.util.*;

import java_cup.runtime.Symbol;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.compiler.util.ScannerHelper;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewriteFormatter.BlockContext;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewriteFormatter.NodeMarker;
import org.eclipse.php.internal.core.ast.rewrite.ASTRewriteFormatter.Prefix;
import org.eclipse.php.internal.core.ast.rewrite.NodeInfoStore.CopyPlaceholderData;
import org.eclipse.php.internal.core.ast.rewrite.NodeInfoStore.StringPlaceholderData;
import org.eclipse.php.internal.core.ast.rewrite.RewriteEventStore.CopySourceInfo;
import org.eclipse.php.internal.core.ast.rewrite.TargetSourceRangeComputer.SourceRange;
import org.eclipse.php.internal.core.ast.scanner.AstLexer;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.text.edits.*;

/**
 * Infrastructure to support code modifications. Existing code must stay untouched, new code
 * added with correct formatting, moved code left with the user's formatting / comments.
 * Idea:
 * - Get the AST for existing code 
 * - Describe changes
 * - This visitor analyzes the changes or annotations and generates text edits
 * (text manipulation API) that describe the required code changes. 
 */
public final class ASTRewriteAnalyzer extends AbstractVisitor {

	TextEdit currentEdit;
	final RewriteEventStore eventStore; // used from inner classes

	private TokenScanner tokenScanner; // shared scanner

	private final Map sourceCopyInfoToEdit;
	private final Stack sourceCopyEndNodes;

	private final char[] content;
	private final IDocument document;
	private final LineInformation lineInfo;
	private final ASTRewriteFormatter formatter;
	private final NodeInfoStore nodeInfos;
	private final TargetSourceRangeComputer extendedSourceRangeComputer;
	private final LineCommentEndOffsets lineCommentEndOffsets;

	private final AstLexer scanner;

	/**
	 * Constructor for ASTRewriteAnalyzer.
	 * @param scanner An {@link AstLexer} scanner.
	 * @param document The IDocument that contains the content of the compilation unit to rewrite.
	 * @param lineInfo line information for the content of the compilation unit to rewrite.
	 * @param rootEdit the edit to add all generated edits to
	 * @param eventStore the event store containing the description of changes
	 * @param nodeInfos annotations to nodes, such as if a node is a string placeholder or a copy target
	 * @param comments list of comments of the compilation unit to rewrite (elements of type <code>Comment</code>) or <code>null</code>.
	 * @param options the current options (formatting/compliance) or <code>null</code>.
	 * @param extendedSourceRangeComputer the source range computer to use
	 */
	public ASTRewriteAnalyzer(AstLexer scanner, IDocument document, LineInformation lineInfo, String lineDelim, TextEdit rootEdit, RewriteEventStore eventStore, NodeInfoStore nodeInfos, List comments, Map options, TargetSourceRangeComputer extendedSourceRangeComputer) {
		this.scanner = scanner;
		this.eventStore = eventStore;
		this.document = document;
		this.content = document.get().toCharArray();
		this.lineInfo = lineInfo;
		this.nodeInfos = nodeInfos;
		this.tokenScanner = null;
		this.currentEdit = rootEdit;
		this.sourceCopyInfoToEdit = new IdentityHashMap();
		this.sourceCopyEndNodes = new Stack();

		this.formatter = new ASTRewriteFormatter(document, nodeInfos, eventStore, options, lineDelim, scanner.getPHPVersion());

		this.extendedSourceRangeComputer = extendedSourceRangeComputer;
		this.lineCommentEndOffsets = new LineCommentEndOffsets(comments);
	}

	final TokenScanner getScanner() {
		if (this.tokenScanner == null) {
			try {
				this.tokenScanner = new TokenScanner(scanner, content);
			} catch (IOException e) {
				Logger.logException(e);
			}
		}
		return this.tokenScanner;
	}

	final char[] getContent() {
		return this.content;
	}

	final LineInformation getLineInformation() {
		return this.lineInfo;
	}

	/**
	 * Returns the extended source range for a node.
	 * 
	 * @return an extended source range (never null)
	 * @since 3.1
	 */
	final SourceRange getExtendedRange(ASTNode node) {
		if (this.eventStore.isRangeCopyPlaceholder(node)) {
			return new SourceRange(node.getStart(), node.getLength());
		}
		return this.extendedSourceRangeComputer.computeSourceRange(node);
	}

	final int getExtendedOffset(ASTNode node) {
		return getExtendedRange(node).getStartPosition();
	}

	final int getExtendedEnd(ASTNode node) {
		TargetSourceRangeComputer.SourceRange range = getExtendedRange(node);
		return range.getStartPosition() + range.getLength();
	}

	final TextEdit getCopySourceEdit(CopySourceInfo info) {
		TextEdit edit = (TextEdit) this.sourceCopyInfoToEdit.get(info);
		if (edit == null) {
			SourceRange range = getExtendedRange(info.getNode());
			int start = range.getStartPosition();
			int end = start + range.getLength();
			if (info.isMove) {
				MoveSourceEdit moveSourceEdit = new MoveSourceEdit(start, end - start);
				moveSourceEdit.setTargetEdit(new MoveTargetEdit(0));
				edit = moveSourceEdit;
			} else {
				CopySourceEdit copySourceEdit = new CopySourceEdit(start, end - start);
				copySourceEdit.setTargetEdit(new CopyTargetEdit(0));
				edit = copySourceEdit;
			}
			this.sourceCopyInfoToEdit.put(info, edit);
		}
		return edit;
	}

	private final int getChangeKind(ASTNode node, StructuralPropertyDescriptor property) {
		RewriteEvent event = getEvent(node, property);
		if (event != null) {
			return event.getChangeKind();
		}
		return RewriteEvent.UNCHANGED;
	}

	private final boolean hasChildrenChanges(ASTNode node) {
		return this.eventStore.hasChangedProperties(node);
	}

	private final boolean isChanged(ASTNode node, StructuralPropertyDescriptor property) {
		RewriteEvent event = getEvent(node, property);
		if (event != null) {
			return event.getChangeKind() != RewriteEvent.UNCHANGED;
		}
		return false;
	}

	private final boolean isCollapsed(ASTNode node) {
		return this.nodeInfos.isCollapsed(node);
	}

	final boolean isInsertBoundToPrevious(ASTNode node) {
		return this.eventStore.isInsertBoundToPrevious(node);
	}

	private final TextEditGroup getEditGroup(ASTNode parent, StructuralPropertyDescriptor property) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null) {
			return getEditGroup(event);
		}
		return null;
	}

	final RewriteEvent getEvent(ASTNode parent, StructuralPropertyDescriptor property) {
		return this.eventStore.getEvent(parent, property);
	}

	final TextEditGroup getEditGroup(RewriteEvent change) {
		return this.eventStore.getEventEditGroup(change);
	}

	private final Object getOriginalValue(ASTNode parent, StructuralPropertyDescriptor property) {
		return this.eventStore.getOriginalValue(parent, property);
	}

	private final Object getNewValue(ASTNode parent, StructuralPropertyDescriptor property) {
		return this.eventStore.getNewValue(parent, property);
	}

	final void addEdit(TextEdit edit) {
		this.currentEdit.addChild(edit);
	}

	final String getLineDelimiter() {
		return this.formatter.getLineDelimiter();
	}

	final String createIndentString(int indent) {
		return this.formatter.createIndentString(indent);
	}

	final private String getIndentOfLine(int pos) {
		int line = getLineInformation().getLineOfOffset(pos);
		if (line >= 0) {
			char[] cont = getContent();
			int lineStart = getLineInformation().getLineOffset(line);
			int i = lineStart;
			while (i < cont.length && IndentManipulation.isIndentChar(content[i])) {
				i++;
			}
			return new String(cont, lineStart, i - lineStart);
		}
		return new String();
	}

	final String getIndentAtOffset(int pos) {
		return this.formatter.getIndentString(getIndentOfLine(pos));
	}

	final void doTextInsert(int offset, String insertString, TextEditGroup editGroup) {
		if (insertString.length() > 0) {
			// bug fix for 95839: problem with inserting at the end of a line comment
			if (this.lineCommentEndOffsets.isEndOfLineComment(offset, this.content)) {
				if (!insertString.startsWith(getLineDelimiter())) {
					TextEdit edit = new InsertEdit(offset, getLineDelimiter()); // add a line delimiter
					addEdit(edit);
					if (editGroup != null) {
						addEditGroup(editGroup, edit);
					}
				}
				this.lineCommentEndOffsets.remove(offset); // only one line delimiter per line comment required
			}
			TextEdit edit = new InsertEdit(offset, insertString);
			addEdit(edit);
			if (editGroup != null) {
				addEditGroup(editGroup, edit);
			}
		}
	}

	final void addEditGroup(TextEditGroup editGroup, TextEdit edit) {
		editGroup.addTextEdit(edit);
	}

	final TextEdit doTextRemove(int offset, int len, TextEditGroup editGroup) {
		if (len == 0) {
			return null;
		}
		TextEdit edit = new DeleteEdit(offset, len);
		addEdit(edit);
		if (editGroup != null) {
			addEditGroup(editGroup, edit);
		}
		return edit;
	}

	final void doTextRemoveAndVisit(int offset, int len, ASTNode node, TextEditGroup editGroup) {
		TextEdit edit = doTextRemove(offset, len, editGroup);
		if (edit != null) {
			this.currentEdit = edit;
			voidVisit(node);
			this.currentEdit = edit.getParent();
		} else {
			voidVisit(node);
		}
	}

	final int doVisit(ASTNode node) {
		node.accept(this);
		return getExtendedEnd(node);
	}

	private final int doVisit(ASTNode parent, StructuralPropertyDescriptor property, int offset) {
		Object node = getOriginalValue(parent, property);
		if (property.isChildProperty() && node != null) {
			return doVisit((ASTNode) node);
		} else if (property.isChildListProperty()) {
			return doVisitList((List) node, offset);
		}
		return offset;
	}

	private int doVisitList(List list, int offset) {
		int endPos = offset;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ASTNode curr = ((ASTNode) iter.next());
			endPos = doVisit(curr);
		}
		return endPos;
	}

	final void voidVisit(ASTNode node) {
		node.accept(this);
	}

	private final void voidVisit(ASTNode parent, StructuralPropertyDescriptor property) {
		Object node = getOriginalValue(parent, property);
		if (property.isChildProperty() && node != null) {
			voidVisit((ASTNode) node);
		} else if (property.isChildListProperty()) {
			voidVisitList((List) node);
		}
	}

	private void voidVisitList(List list) {
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			doVisit(((ASTNode) iter.next()));
		}
	}

	private final boolean doVisitUnchangedChildren(ASTNode parent) {
		List properties = parent.structuralPropertiesForType();
		for (int i = 0; i < properties.size(); i++) {
			voidVisit(parent, (StructuralPropertyDescriptor) properties.get(i));
		}
		return false;
	}

	private final void doTextReplace(int offset, int len, String insertString, TextEditGroup editGroup) {
		if (len > 0 || insertString.length() > 0) {
			TextEdit edit = new ReplaceEdit(offset, len, insertString);
			addEdit(edit);
			if (editGroup != null) {
				addEditGroup(editGroup, edit);
			}
		}
	}

	private final TextEdit doTextCopy(TextEdit sourceEdit, int destOffset, int sourceIndentLevel, String destIndentString, TextEditGroup editGroup) {
		TextEdit targetEdit;
		SourceModifier modifier = new SourceModifier(sourceIndentLevel, destIndentString, this.formatter.getTabWidth(), this.formatter.getIndentWidth());

		if (sourceEdit instanceof MoveSourceEdit) {
			MoveSourceEdit moveEdit = (MoveSourceEdit) sourceEdit;
			moveEdit.setSourceModifier(modifier);

			targetEdit = new MoveTargetEdit(destOffset, moveEdit);
			addEdit(targetEdit);
		} else {
			CopySourceEdit copyEdit = (CopySourceEdit) sourceEdit;
			copyEdit.setSourceModifier(modifier);

			targetEdit = new CopyTargetEdit(destOffset, copyEdit);
			addEdit(targetEdit);
		}

		if (editGroup != null) {
			addEditGroup(editGroup, sourceEdit);
			addEditGroup(editGroup, targetEdit);
		}
		return targetEdit;

	}

	private void changeNotSupported(ASTNode node) {
		Assert.isTrue(false, "Change not supported in " + node.getClass().getName()); //$NON-NLS-1$
	}

	class ListRewriter {
		protected String contantSeparator;
		protected int startPos;

		protected RewriteEvent[] list;

		protected final ASTNode getOriginalNode(int index) {
			return (ASTNode) this.list[index].getOriginalValue();
		}

		protected final ASTNode getNewNode(int index) {
			return (ASTNode) this.list[index].getNewValue();
		}

		protected String getSeparatorString(int nodeIndex) {
			return this.contantSeparator;
		}

		protected int getInitialIndent() {
			return getIndent(this.startPos);
		}

		protected int getNodeIndent(int nodeIndex) {
			ASTNode node = getOriginalNode(nodeIndex);
			if (node == null) {
				for (int i = nodeIndex - 1; i >= 0; i--) {
					ASTNode curr = getOriginalNode(i);
					if (curr != null) {
						return getIndent(curr.getStart());
					}
				}
				return getInitialIndent();
			}
			return getIndent(node.getStart());
		}

		protected int getStartOfNextNode(int nextIndex, int defaultPos) {
			for (int i = nextIndex; i < this.list.length; i++) {
				RewriteEvent elem = this.list[i];
				if (elem.getChangeKind() != RewriteEvent.INSERTED) {
					ASTNode node = (ASTNode) elem.getOriginalValue();
					return getExtendedOffset(node);
				}
			}
			return defaultPos;
		}

		protected int getEndOfNode(ASTNode node) {
			return getExtendedEnd(node);
		}

		public final int rewriteList(ASTNode parent, StructuralPropertyDescriptor property, int offset, String keyword, String separator) {
			this.contantSeparator = separator;
			return rewriteList(parent, property, offset, keyword);
		}

		private boolean insertAfterSeparator(ASTNode node) {
			return !isInsertBoundToPrevious(node);
		}

		public final int rewriteList(ASTNode parent, StructuralPropertyDescriptor property, int offset, String keyword) {
			this.startPos = offset;
			this.list = getEvent(parent, property).getChildren();

			int total = this.list.length;
			if (total == 0) {
				return this.startPos;
			}

			int currPos = -1;

			int lastNonInsert = -1;
			int lastNonDelete = -1;

			for (int i = 0; i < total; i++) {
				int currMark = this.list[i].getChangeKind();

				if (currMark != RewriteEvent.INSERTED) {
					lastNonInsert = i;
					if (currPos == -1) {
						ASTNode elem = (ASTNode) this.list[i].getOriginalValue();
						currPos = getExtendedOffset(elem);
					}
				}
				if (currMark != RewriteEvent.REMOVED) {
					lastNonDelete = i;
				}
			}

			if (currPos == -1) { // only inserts
				if (keyword.length() > 0) { // creating a new list -> insert keyword first (e.g. " throws ")
					TextEditGroup editGroup = getEditGroup(this.list[0]); // first node is insert
					doTextInsert(offset, keyword, editGroup);
				}
				currPos = offset;
			}
			if (lastNonDelete == -1) { // all removed, set back to start so the keyword is removed as well
				currPos = offset;
			}

			int prevEnd = currPos;

			final int NONE = 0, NEW = 1, EXISTING = 2;
			int separatorState = NEW;

			for (int i = 0; i < total; i++) {
				RewriteEvent currEvent = this.list[i];
				int currMark = currEvent.getChangeKind();
				int nextIndex = i + 1;

				if (currMark == RewriteEvent.INSERTED) {
					TextEditGroup editGroup = getEditGroup(currEvent);
					ASTNode node = (ASTNode) currEvent.getNewValue();

					if (separatorState == NONE) { // element after last existing element (but not first)
						doTextInsert(currPos, getSeparatorString(i - 1), editGroup); // insert separator
						separatorState = NEW;
					}
					if (separatorState == NEW || insertAfterSeparator(node)) {
						doTextInsert(currPos, node, getNodeIndent(i), true, editGroup); // insert node

						separatorState = NEW;
						if (i != lastNonDelete) {
							if (this.list[nextIndex].getChangeKind() != RewriteEvent.INSERTED) {
								doTextInsert(currPos, getSeparatorString(i), editGroup); // insert separator
							} else {
								separatorState = NONE;
							}
						}
					} else { // EXISTING && insert before separator
						doTextInsert(prevEnd, getSeparatorString(i - 1), editGroup);
						doTextInsert(prevEnd, node, getNodeIndent(i), true, editGroup);
					}
				} else if (currMark == RewriteEvent.REMOVED) {
					ASTNode node = (ASTNode) currEvent.getOriginalValue();
					TextEditGroup editGroup = getEditGroup(currEvent);
					int currEnd = getEndOfNode(node);
					if (i > lastNonDelete && separatorState == EXISTING) {
						// is last, remove previous separator: split delete to allow range copies
						doTextRemove(prevEnd, currPos - prevEnd, editGroup); // remove separator
						doTextRemoveAndVisit(currPos, currEnd - currPos, node, editGroup); // remove node
						currPos = currEnd;
						prevEnd = currEnd;
					} else {
						// remove element and next separator
						int end = getStartOfNextNode(nextIndex, currEnd); // start of next
						doTextRemoveAndVisit(currPos, currEnd - currPos, node, getEditGroup(currEvent)); // remove node
						doTextRemove(currEnd, end - currEnd, editGroup); // remove separator
						currPos = end;
						prevEnd = currEnd;
						separatorState = NEW;
					}
				} else { // replaced or unchanged
					if (currMark == RewriteEvent.REPLACED) {
						ASTNode node = (ASTNode) currEvent.getOriginalValue();
						int currEnd = getEndOfNode(node);

						TextEditGroup editGroup = getEditGroup(currEvent);
						ASTNode changed = (ASTNode) currEvent.getNewValue();
						doTextRemoveAndVisit(currPos, currEnd - currPos, node, editGroup);
						doTextInsert(currPos, changed, getNodeIndent(i), true, editGroup);

						prevEnd = currEnd;
					} else { // is unchanged
						ASTNode node = (ASTNode) currEvent.getOriginalValue();
						voidVisit(node);
					}
					if (i == lastNonInsert) { // last node or next nodes are all inserts
						separatorState = NONE;
						if (currMark == RewriteEvent.UNCHANGED) {
							ASTNode node = (ASTNode) currEvent.getOriginalValue();
							prevEnd = getEndOfNode(node);
						}
						currPos = prevEnd;
					} else if (this.list[nextIndex].getChangeKind() != RewriteEvent.UNCHANGED) {
						// no updates needed while nodes are unchanged
						if (currMark == RewriteEvent.UNCHANGED) {
							ASTNode node = (ASTNode) currEvent.getOriginalValue();
							prevEnd = getEndOfNode(node);
						}
						currPos = getStartOfNextNode(nextIndex, prevEnd); // start of next
						separatorState = EXISTING;
					}
				}

			}
			return currPos;
		}

	}

	private int rewriteRequiredNode(ASTNode parent, StructuralPropertyDescriptor property) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			ASTNode node = (ASTNode) event.getOriginalValue();
			TextEditGroup editGroup = getEditGroup(event);
			SourceRange range = getExtendedRange(node);
			int offset = range.getStartPosition();
			int length = range.getLength();
			doTextRemoveAndVisit(offset, length, node, editGroup);
			doTextInsert(offset, (ASTNode) event.getNewValue(), getIndent(offset), true, editGroup);
			return offset + length;
		}
		return doVisit(parent, property, 0);
	}

	private int rewriteNode(ASTNode parent, StructuralPropertyDescriptor property, int offset, Prefix prefix) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null) {
			switch (event.getChangeKind()) {
				case RewriteEvent.INSERTED: {
					ASTNode node = (ASTNode) event.getNewValue();
					TextEditGroup editGroup = getEditGroup(event);
					int indent = getIndent(offset);
					doTextInsert(offset, prefix.getPrefix(indent), editGroup);
					doTextInsert(offset, node, indent, true, editGroup);
					return offset;
				}
				case RewriteEvent.REMOVED: {
					ASTNode node = (ASTNode) event.getOriginalValue();
					TextEditGroup editGroup = getEditGroup(event);

					int nodeEnd = getExtendedEnd(node);
					// if there is a prefix, remove the prefix as well
					int len = nodeEnd - offset;
					doTextRemoveAndVisit(offset, len, node, editGroup);
					return nodeEnd;
				}
				case RewriteEvent.REPLACED: {
					ASTNode node = (ASTNode) event.getOriginalValue();
					TextEditGroup editGroup = getEditGroup(event);
					SourceRange range = getExtendedRange(node);
					int nodeOffset = range.getStartPosition();
					int nodeLen = range.getLength();
					doTextRemoveAndVisit(nodeOffset, nodeLen, node, editGroup);
					doTextInsert(nodeOffset, (ASTNode) event.getNewValue(), getIndent(offset), true, editGroup);
					return nodeOffset + nodeLen;
				}
			}
		}
		return doVisit(parent, property, offset);
	}

	private int rewriteDocumentation(ASTNode node, StructuralPropertyDescriptor property) {
		int pos = rewriteNode(node, property, node.getStart(), ASTRewriteFormatter.NONE);
		int changeKind = getChangeKind(node, property);
		if (changeKind == RewriteEvent.INSERTED) {
			String indent = getLineDelimiter() + getIndentAtOffset(pos);
			doTextInsert(pos, indent, getEditGroup(node, property));
		} else if (changeKind == RewriteEvent.REMOVED) {
			try {
				getScanner().readNext(pos/*, false*/);
				doTextRemove(pos, getScanner().getCurrentStartOffset() - pos, getEditGroup(node, property));
				pos = getScanner().getCurrentStartOffset();
			} catch (CoreException e) {
				handleException(e);
			}
		}
		return pos;
	}

	/*
	 * endpos can be -1 -> use the end pos of the body
	 */
	private int rewriteBodyNode(ASTNode parent, StructuralPropertyDescriptor property, int offset, int endPos, int indent, BlockContext context) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null) {
			switch (event.getChangeKind()) {
				case RewriteEvent.INSERTED: {
					ASTNode node = (ASTNode) event.getNewValue();
					TextEditGroup editGroup = getEditGroup(event);

					String[] strings = context.getPrefixAndSuffix(indent, node, this.eventStore);

					doTextInsert(offset, strings[0], editGroup);
					doTextInsert(offset, node, indent, true, editGroup);
					doTextInsert(offset, strings[1], editGroup);
					return offset;
				}
				case RewriteEvent.REMOVED: {
					ASTNode node = (ASTNode) event.getOriginalValue();
					if (endPos == -1) {
						endPos = getExtendedEnd(node);
					}

					TextEditGroup editGroup = getEditGroup(event);
					// if there is a prefix, remove the prefix as well
					int len = endPos - offset;
					doTextRemoveAndVisit(offset, len, node, editGroup);
					return endPos;
				}
				case RewriteEvent.REPLACED: {
					ASTNode node = (ASTNode) event.getOriginalValue();
					if (endPos == -1) {
						endPos = getExtendedEnd(node);
					}
					TextEditGroup editGroup = getEditGroup(event);
					int nodeLen = endPos - offset;

					ASTNode replacingNode = (ASTNode) event.getNewValue();
					String[] strings = context.getPrefixAndSuffix(indent, replacingNode, this.eventStore);
					doTextRemoveAndVisit(offset, nodeLen, node, editGroup);

					String prefix = strings[0];
					doTextInsert(offset, prefix, editGroup);
					String lineInPrefix = getCurrentLine(prefix, prefix.length());
					if (prefix.length() != lineInPrefix.length()) {
						// prefix contains a new line: update the indent to the one used in the prefix
						indent = this.formatter.computeIndentUnits(lineInPrefix);
					}
					doTextInsert(offset, replacingNode, indent, true, editGroup);
					doTextInsert(offset, strings[1], editGroup);
					return endPos;
				}
			}
		}
		int pos = doVisit(parent, property, offset);
		if (endPos != -1) {
			return endPos;
		}
		return pos;
	}

	private int rewriteOptionalQualifier(ASTNode parent, StructuralPropertyDescriptor property, int startPos) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null) {
			switch (event.getChangeKind()) {
				case RewriteEvent.INSERTED: {
					ASTNode node = (ASTNode) event.getNewValue();
					TextEditGroup editGroup = getEditGroup(event);
					doTextInsert(startPos, node, getIndent(startPos), true, editGroup);
					doTextInsert(startPos, ".", editGroup); //$NON-NLS-1$
					return startPos;
				}
				case RewriteEvent.REMOVED: {
					try {
						ASTNode node = (ASTNode) event.getOriginalValue();
						TextEditGroup editGroup = getEditGroup(event);
						int dotEnd = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.DOT_SYMBOL_ID, scanner.getPHPVersion()), node.getStart() + node.getLength());
						doTextRemoveAndVisit(startPos, dotEnd - startPos, node, editGroup);
						return dotEnd;
					} catch (CoreException e) {
						handleException(e);
					}
					break;
				}
				case RewriteEvent.REPLACED: {
					ASTNode node = (ASTNode) event.getOriginalValue();
					TextEditGroup editGroup = getEditGroup(event);
					SourceRange range = getExtendedRange(node);
					int offset = range.getStartPosition();
					int length = range.getLength();

					doTextRemoveAndVisit(offset, length, node, editGroup);
					doTextInsert(offset, (ASTNode) event.getNewValue(), getIndent(startPos), true, editGroup);
					try {
						return getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.DOT_SYMBOL_ID, scanner.getPHPVersion()), offset + length);
					} catch (CoreException e) {
						handleException(e);
					}
					break;
				}
			}
		}
		Object node = getOriginalValue(parent, property);
		if (node == null) {
			return startPos;
		}
		ASTNode astNode = (ASTNode) node;
		int pos = doVisit(astNode);
		try {
			return getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.DOT_SYMBOL_ID, scanner.getPHPVersion()), pos);
		} catch (CoreException e) {
			handleException(e);
		}
		return pos;
	}

	class ParagraphListRewriter extends ListRewriter {

		public final static int DEFAULT_SPACING = 1;

		private int initialIndent;
		private int separatorLines;

		public ParagraphListRewriter(int initialIndent, int separator) {
			this.initialIndent = initialIndent;
			this.separatorLines = separator;
		}

		protected int getInitialIndent() {
			return this.initialIndent;
		}

		protected String getSeparatorString(int nodeIndex) {
			int newLines = this.separatorLines == -1 ? getNewLines(nodeIndex) : this.separatorLines;

			String lineDelim = getLineDelimiter();
			StringBuffer buf = new StringBuffer(lineDelim);
			for (int i = 0; i < newLines; i++) {
				buf.append(lineDelim);
			}
			buf.append(createIndentString(getNodeIndent(nodeIndex + 1)));
			return buf.toString();
		}

		private ASTNode getNode(int nodeIndex) {
			ASTNode elem = (ASTNode) this.list[nodeIndex].getOriginalValue();
			if (elem == null) {
				elem = (ASTNode) this.list[nodeIndex].getNewValue();
			}
			return elem;
		}

		private int getNewLines(int nodeIndex) {
			ASTNode curr = getNode(nodeIndex);
			ASTNode next = getNode(nodeIndex + 1);

			int currKind = curr.getType();
			int nextKind = next.getType();

			ASTNode last = null;
			ASTNode secondLast = null;
			for (int i = 0; i < this.list.length; i++) {
				ASTNode elem = (ASTNode) this.list[i].getOriginalValue();
				if (elem != null) {
					if (last != null) {
						if (elem.getType() == nextKind && last.getType() == currKind) {
							return countEmptyLines(last);
						}
						secondLast = last;
					}
					last = elem;
				}
			}
			if (currKind == ASTNode.FIELD_DECLARATION && nextKind == ASTNode.FIELD_DECLARATION) {
				return 0;
			}
			if (secondLast != null) {
				return countEmptyLines(secondLast);
			}
			return DEFAULT_SPACING;
		}

		private int countEmptyLines(ASTNode last) {
			LineInformation lineInformation = getLineInformation();
			int lastLine = lineInformation.getLineOfOffset(getExtendedEnd(last));
			if (lastLine >= 0) {
				int startLine = lastLine + 1;
				int start = lineInformation.getLineOffset(startLine);
				if (start < 0) {
					return 0;
				}
				char[] cont = getContent();
				int i = start;
				while (i < cont.length && ScannerHelper.isWhitespace(cont[i])) {
					i++;
				}
				if (i > start) {
					lastLine = lineInformation.getLineOfOffset(i);
					if (lastLine > startLine) {
						return lastLine - startLine;
					}
				}
			}
			return 0;
		}
	}

	private int rewriteParagraphList(ASTNode parent, StructuralPropertyDescriptor property, int insertPos, int insertIndent, int separator, int lead) {
		RewriteEvent event = getEvent(parent, property);
		if (event == null || event.getChangeKind() == RewriteEvent.UNCHANGED) {
			return doVisit(parent, property, insertPos);
		}

		RewriteEvent[] events = event.getChildren();
		ParagraphListRewriter listRewriter = new ParagraphListRewriter(insertIndent, separator);
		StringBuffer leadString = new StringBuffer();
		if (isAllOfKind(events, RewriteEvent.INSERTED)) {
			for (int i = 0; i < lead; i++) {
				leadString.append(getLineDelimiter());
			}
			leadString.append(createIndentString(insertIndent));
		}
		return listRewriter.rewriteList(parent, property, insertPos, leadString.toString());
	}

	private int rewriteOptionalTypeParameters(ASTNode parent, StructuralPropertyDescriptor property, int offset, String keyword, boolean adjustOnNext, boolean needsSpaceOnRemoveAll) {
		int pos = offset;
		RewriteEvent event = getEvent(parent, property);
		if (event != null && event.getChangeKind() != RewriteEvent.UNCHANGED) {
			RewriteEvent[] children = event.getChildren();
			try {
				boolean isAllInserted = isAllOfKind(children, RewriteEvent.INSERTED);
				if (isAllInserted && adjustOnNext) {
					pos = getScanner().getNextStartOffset(pos/*, false*/); // adjust on next element
				}
				boolean isAllRemoved = !isAllInserted && isAllOfKind(children, RewriteEvent.REMOVED);
				if (isAllRemoved) { // all removed: set start to left bracket
					int posBeforeOpenBracket = getScanner().getTokenStartOffset(SymbolsProvider.getSymbol(SymbolsProvider.LESS_ID, scanner.getPHPVersion()), pos);
					if (posBeforeOpenBracket != pos) {
						needsSpaceOnRemoveAll = false;
					}
					pos = posBeforeOpenBracket;
				}
				pos = new ListRewriter().rewriteList(parent, property, pos, String.valueOf('<'), ", "); //$NON-NLS-1$
				if (isAllRemoved) { // all removed: remove right and space up to next element
					int endPos = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.GREATER_ID, scanner.getPHPVersion()), pos); // set pos to '>'
					endPos = getScanner().getNextStartOffset(endPos/*, false*/);
					String replacement = needsSpaceOnRemoveAll ? String.valueOf(' ') : new String();
					doTextReplace(pos, endPos - pos, replacement, getEditGroup(children[children.length - 1]));
					return endPos;
				} else if (isAllInserted) {
					doTextInsert(pos, String.valueOf('>' + keyword), getEditGroup(children[children.length - 1]));
					return pos;
				}
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			pos = doVisit(parent, property, pos);
		}
		if (pos != offset) { // list contained some type -> parse after closing bracket
			try {
				return getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.GREATER_ID, scanner.getPHPVersion()), pos);
			} catch (CoreException e) {
				handleException(e);
			}
		}
		return pos;
	}

	private boolean isAllOfKind(RewriteEvent[] children, int kind) {
		for (int i = 0; i < children.length; i++) {
			if (children[i].getChangeKind() != kind) {
				return false;
			}
		}
		return true;
	}

	private int rewriteNodeList(ASTNode parent, StructuralPropertyDescriptor property, int pos, String keyword, String separator) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null && event.getChangeKind() != RewriteEvent.UNCHANGED) {
			return new ListRewriter().rewriteList(parent, property, pos, keyword, separator);
		}
		return doVisit(parent, property, pos);
	}

	/*
	 * Rewrite the dollar property when dealing with a variable.
	 */
	private void rewriteVariableDollar(Variable variable) {
		// Make the necessary changes to add or remove the $ sign.
		RewriteEvent event = getEvent(variable, variable.getDollaredProperty());
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			TextEditGroup editGroup = getEditGroup(event);
			if ((Boolean) event.getNewValue()) {
				// Add a dollar sign to the variable
				this.doTextInsert(variable.getStart(), "$", editGroup);
			} else {
				// Remove the dollar sign from the variable
				this.doTextRemove(variable.getStart(), 1, editGroup);
			}
		}
	}

	/*
	 * Next token is a left brace. Returns the offset of the open brace.
	 * @throws CoreException  
	 */
	private int getLeftBraceStartPosition(int pos) throws CoreException {
		return getSymbolStartPosition(pos, SymbolsProvider.getSymbol(SymbolsProvider.LBRACE_ID, scanner.getPHPVersion()));
	}

	/*
	 * Next token is a right brace. Returns the offset of the closing brace.
	 * @throws CoreException  
	 */
	private int getRightBraceStartPosition(int pos) throws CoreException {
		return getSymbolStartPosition(pos, SymbolsProvider.getSymbol(SymbolsProvider.RBRACE_ID, scanner.getPHPVersion()));
	}

	/*
	 * Next token is a left parentheses. Returns the offset of the open parentheses.
	 * @throws CoreException  
	 */
	private int getLeftParenthesesStartPosition(int pos) throws CoreException {
		return getSymbolStartPosition(pos, SymbolsProvider.getSymbol(SymbolsProvider.LPAREN_ID, scanner.getPHPVersion()));
	}

	/*
	 * Next token is a right parentheses. Returns the offset of the closing parentheses.
	 * @throws CoreException  
	 */
	private int getRightParenthesesStartPosition(int pos) throws CoreException {
		return getSymbolStartPosition(pos, SymbolsProvider.getSymbol(SymbolsProvider.RPAREN_ID, scanner.getPHPVersion()));
	}

	/*
	 * Next token is a left bracket. Returns the offset of the open bracket.
	 * @throws CoreException  
	 */
	private int getLeftBracketStartPosition(int pos) throws CoreException {
		return getSymbolStartPosition(pos, SymbolsProvider.getSymbol(SymbolsProvider.LBRACKET_ID, scanner.getPHPVersion()));
	}

	/*
	 * Next token is a right bracket. Returns the offset of the closing bracket.
	 * @throws CoreException  
	 */
	private int getRightBracketStartPosition(int pos) throws CoreException {
		return getSymbolStartPosition(pos, SymbolsProvider.getSymbol(SymbolsProvider.RBRACKET_ID, scanner.getPHPVersion()));
	}

	/*
	 * A general call to get the scanner's start offset of the Symbol token.
	 */
	private int getSymbolStartPosition(int pos, Symbol sym) throws CoreException {
		return getScanner().getTokenStartOffset(sym, pos);
	}

	final int getIndent(int offset) {
		return this.formatter.computeIndentUnits(getIndentOfLine(offset));
	}

	final void doTextInsert(int insertOffset, ASTNode node, int initialIndentLevel, boolean removeLeadingIndent, TextEditGroup editGroup) {
		ArrayList markers = new ArrayList();
		String formatted = this.formatter.getFormattedResult(node, initialIndentLevel, markers);

		int currPos = 0;
		if (removeLeadingIndent) {
			while (currPos < formatted.length() && ScannerHelper.isWhitespace(formatted.charAt(currPos))) {
				currPos++;
			}
		}
		for (int i = 0; i < markers.size(); i++) { // markers.size can change!
			NodeMarker curr = (NodeMarker) markers.get(i);

			int offset = curr.offset;
			if (offset != currPos) {
				String insertStr = formatted.substring(currPos, offset);
				doTextInsert(insertOffset, insertStr, editGroup); // insert until the marker's begin
			}

			Object data = curr.data;
			if (data instanceof TextEditGroup) { // tracking a node
				// need to split and create 2 edits as tracking node can surround replaced node.
				TextEdit edit = new RangeMarker(insertOffset, 0);
				addEditGroup((TextEditGroup) data, edit);
				addEdit(edit);
				if (curr.length != 0) {
					int end = offset + curr.length;
					int k = i + 1;
					while (k < markers.size() && ((NodeMarker) markers.get(k)).offset < end) {
						k++;
					}
					curr.offset = end;
					curr.length = 0;
					markers.add(k, curr); // add again for end position
				}
				currPos = offset;
			} else {
				String destIndentString = this.formatter.getIndentString(getCurrentLine(formatted, offset));
				if (data instanceof CopyPlaceholderData) { // replace with a copy/move target
					CopySourceInfo copySource = ((CopyPlaceholderData) data).copySource;
					int srcIndentLevel = getIndent(copySource.getNode().getStart());
					TextEdit sourceEdit = getCopySourceEdit(copySource);
					doTextCopy(sourceEdit, insertOffset, srcIndentLevel, destIndentString, editGroup);
					currPos = offset + curr.length; // continue to insert after the replaced string
					if (needsNewLineForLineComment(copySource.getNode(), formatted, currPos)) {
						doTextInsert(insertOffset, getLineDelimiter(), editGroup);
					}
				} else if (data instanceof StringPlaceholderData) { // replace with a placeholder
					String code = ((StringPlaceholderData) data).code;
					String str = this.formatter.changeIndent(code, 0, destIndentString);
					doTextInsert(insertOffset, str, editGroup);
					currPos = offset + curr.length; // continue to insert after the replaced string
				}
			}

		}
		if (currPos < formatted.length()) {
			String insertStr = formatted.substring(currPos);
			doTextInsert(insertOffset, insertStr, editGroup);
		}
	}

	private boolean needsNewLineForLineComment(ASTNode node, String formatted, int offset) {
		if (!this.lineCommentEndOffsets.isEndOfLineComment(getExtendedEnd(node), this.content)) {
			return false;
		}
		// copied code ends with a line comment, but doesn't contain the new line
		return offset < formatted.length() && !IndentManipulation.isLineDelimiterChar(formatted.charAt(offset));
	}

	private String getCurrentLine(String str, int pos) {
		for (int i = pos - 1; i >= 0; i--) {
			char ch = str.charAt(i);
			if (IndentManipulation.isLineDelimiterChar(ch)) {
				return str.substring(i + 1, pos);
			}
		}
		return str.substring(0, pos);
	}

	private void rewriteModifiers(ASTNode parent, StructuralPropertyDescriptor property, int offset) {
		RewriteEvent event = getEvent(parent, property);
		if (event == null || event.getChangeKind() != RewriteEvent.REPLACED) {
			return;
		}
		try {
			int oldModifiers = (Integer) event.getOriginalValue();
			int newModifiers = (Integer) event.getNewValue();
			TextEditGroup editGroup = getEditGroup(event);

			TokenScanner scanner = getScanner();

			Symbol tok = scanner.readNext(offset/*, false*/);
			int startPos = scanner.getCurrentStartOffset();
			int nextStart = startPos;
			// prepare the modifiers 'syms'
			PHPVersion phpVersion = this.scanner.getPHPVersion();
			int modifiers[] = new int[] { SymbolsProvider.getModifierSym("public", phpVersion), SymbolsProvider.getModifierSym("private", phpVersion), SymbolsProvider.getModifierSym("protected", phpVersion), SymbolsProvider.getModifierSym("static", phpVersion),
				SymbolsProvider.getModifierSym("abstract", phpVersion), SymbolsProvider.getModifierSym("final", phpVersion) };
			loop: while (true) {
				if (TokenScanner.isComment(tok)) {
					tok = scanner.readNext(/*true*/); // next non-comment token
				}
				boolean keep = true;
				if (tok == null) {
					break loop;
				}
				if (tok.sym == modifiers[0]) {
					keep = PHPFlags.isPublic(newModifiers);
				} else if (tok.sym == modifiers[1]) {
					keep = PHPFlags.isPrivate(newModifiers);
				} else if (tok.sym == modifiers[2]) {
					keep = PHPFlags.isProtected(newModifiers);
				} else if (tok.sym == modifiers[3]) {
					keep = PHPFlags.isStatic(newModifiers);
				} else if (tok.sym == modifiers[4]) {
					keep = PHPFlags.isAbstract(newModifiers);
				} else if (tok.sym == modifiers[5]) {
					keep = PHPFlags.isFinal(newModifiers);
				} else {
					break loop;
				}
				tok = getScanner().readNext(/*false*/); // include comments
				int currPos = nextStart;
				nextStart = getScanner().getCurrentStartOffset();
				if (!keep) {
					doTextRemove(currPos, nextStart - currPos, editGroup);
				}
			}
			int addedModifiers = newModifiers & ~oldModifiers;
			if (addedModifiers != 0) {
				if (startPos != nextStart) {
					int visibilityModifiers = addedModifiers & (Modifiers.AccPublic | Modifiers.AccPrivate | Modifiers.AccProtected);
					if (visibilityModifiers != 0) {
						StringBuffer buf = new StringBuffer();
						ASTRewriteFlattener.printModifiers(visibilityModifiers, buf);
						doTextInsert(startPos, buf.toString(), editGroup);
						addedModifiers &= ~visibilityModifiers;
					}
				}
				StringBuffer buf = new StringBuffer();
				ASTRewriteFlattener.printModifiers(addedModifiers, buf);
				doTextInsert(nextStart, buf.toString(), editGroup);
			}
		} catch (CoreException e) {
			handleException(e);
		}
	}

	class ModifierRewriter extends ListRewriter {

		private final Prefix annotationSeparation;

		public ModifierRewriter(Prefix annotationSeparation) {
			this.annotationSeparation = annotationSeparation;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jdt.internal.core.dom.rewrite.ASTRewriteAnalyzer.ListRewriter#getSeparatorString(int)
		 */
		protected String getSeparatorString(int nodeIndex) {
			//			ASTNode curr = getNewNode(nodeIndex);
			//			if (curr instanceof Annotation) {
			//				return this.annotationSeparation.getPrefix(getNodeIndent(nodeIndex + 1));
			//			}
			// TODO
			return super.getSeparatorString(nodeIndex);
		}
	}

	private int rewriteModifiers2(ASTNode node, ChildListPropertyDescriptor property, int pos) {
		//		RewriteEvent event = getEvent(node, property);
		//		if (event == null || event.getChangeKind() == RewriteEvent.UNCHANGED) {
		//			return doVisit(node, property, pos);
		//		}
		//		RewriteEvent[] children = event.getChildren();
		//		boolean isAllInsert = isAllOfKind(children, RewriteEvent.INSERTED);
		//		boolean isAllRemove = isAllOfKind(children, RewriteEvent.REMOVED);
		//		if (isAllInsert || isAllRemove) {
		//			// update pos
		//			try {
		//				pos = getScanner().getNextStartOffset(pos/*, false*/);
		//			} catch (CoreException e) {
		//				handleException(e);
		//			}
		//		}
		//
		//		Prefix formatterPrefix;
		//		if (property == SingleVariableDeclaration.MODIFIERS2_PROPERTY)
		//			formatterPrefix = this.formatter.PARAM_ANNOTATION_SEPARATION;
		//		else
		//			formatterPrefix = this.formatter.ANNOTATION_SEPARATION;
		//
		//		int endPos = new ModifierRewriter(formatterPrefix).rewriteList(node, property, pos, "", " "); //$NON-NLS-1$ //$NON-NLS-2$
		//
		//		try {
		//			int nextPos = getScanner().getNextStartOffset(endPos/*, false*/);
		//
		//			boolean lastUnchanged = children[children.length - 1].getChangeKind() != RewriteEvent.UNCHANGED;
		//
		//			if (isAllRemove) {
		//				doTextRemove(endPos, nextPos - endPos, getEditGroup(children[children.length - 1]));
		//				return nextPos;
		//			} else if (isAllInsert || (nextPos == endPos && lastUnchanged)) { // see bug 165654
		//				RewriteEvent lastChild = children[children.length - 1];
		//				String separator;
		//				if (lastChild.getNewValue() instanceof Annotation) {
		//					separator = formatterPrefix.getPrefix(getIndent(pos));
		//				} else {
		//					separator = String.valueOf(' ');
		//				}
		//				doTextInsert(endPos, separator, getEditGroup(lastChild));
		//			}
		//		} catch (CoreException e) {
		//			handleException(e);
		//		}
		//		return endPos;
		// TODO
		return 0;
	}

	private void replaceOperation(int posBeforeOperation, String newOperation, TextEditGroup editGroup) {
		try {
			getScanner().readNext(posBeforeOperation/*, true*/);
			doTextReplace(getScanner().getCurrentStartOffset(), getScanner().getCurrentLength(), newOperation, editGroup);
		} catch (CoreException e) {
			handleException(e);
		}
	}

	private void rewriteOperation(ASTNode parent, StructuralPropertyDescriptor property, int posBeforeOperation) {
		RewriteEvent event = getEvent(parent, property);
		if (event != null && event.getChangeKind() != RewriteEvent.UNCHANGED) {
			try {
				String newOperation = event.getNewValue().toString();
				if (parent instanceof IOperationNode) {
					newOperation = ((IOperationNode) parent).getOperationString((Integer) event.getNewValue());
				} else {
					throw new CoreException(new Status(IStatus.ERROR, PHPCorePlugin.ID, "The node must be an IOperationNode")); //$NON-NLS-1$
				}
				TextEditGroup editGroup = getEditGroup(event);
				getScanner().readNext(posBeforeOperation/*, true*/);
				doTextReplace(getScanner().getCurrentStartOffset(), getScanner().getCurrentLength(), newOperation, editGroup);
			} catch (CoreException e) {
				handleException(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#postVisit(ASTNode)
	 */
	public void postVisit(ASTNode node) {
		TextEditGroup editGroup = this.eventStore.getTrackedNodeData(node);
		if (editGroup != null) {
			this.currentEdit = this.currentEdit.getParent();
		}
		// remove copy source edits
		doCopySourcePostVisit(node, this.sourceCopyEndNodes);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#preVisit(ASTNode)
	 */
	public void preVisit(ASTNode node) {
		// copies, then range marker

		CopySourceInfo[] infos = this.eventStore.getNodeCopySources(node);
		doCopySourcePreVisit(infos, this.sourceCopyEndNodes);

		TextEditGroup editGroup = this.eventStore.getTrackedNodeData(node);
		if (editGroup != null) {
			SourceRange range = getExtendedRange(node);
			int offset = range.getStartPosition();
			int length = range.getLength();
			TextEdit edit = new RangeMarker(offset, length);
			addEditGroup(editGroup, edit);
			addEdit(edit);
			this.currentEdit = edit;
		}
	}

	final void doCopySourcePreVisit(CopySourceInfo[] infos, Stack nodeEndStack) {
		if (infos != null) {
			for (int i = 0; i < infos.length; i++) {
				CopySourceInfo curr = infos[i];
				TextEdit edit = getCopySourceEdit(curr);
				addEdit(edit);
				this.currentEdit = edit;
				nodeEndStack.push(curr.getNode());
			}
		}
	}

	final void doCopySourcePostVisit(ASTNode node, Stack nodeEndStack) {
		while (!nodeEndStack.isEmpty() && nodeEndStack.peek() == node) {
			nodeEndStack.pop();
			this.currentEdit = this.currentEdit.getParent();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(Program)
	 */
	public boolean visit(Program node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		int pos = content.length; // TODO - Check if this is right (using 0 cause for the code to be inserted before the <?php section).
		rewriteNodeList(node, Program.STATEMENTS_PROPERTY, pos, "", "");
		//		int startPos = rewriteNode(node, Program.PACKAGE_PROPERTY, 0, ASTRewriteFormatter.NONE);
		//
		//		if (getChangeKind(node, Program.PACKAGE_PROPERTY) == RewriteEvent.INSERTED) {
		//			doTextInsert(0, getLineDelimiter(), getEditGroup(node, Program.PACKAGE_PROPERTY));
		//		}
		//
		//		startPos = rewriteParagraphList(node, Program.IMPORTS_PROPERTY, startPos, 0, 0, 2);
		//		rewriteParagraphList(node, Program.TYPES_PROPERTY, startPos, 0, -1, 2);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(TypeDeclaration)
	 */
	public boolean visit(TypeDeclaration node) {
		// Return false.
		// There is no need to visit it, all the implementation is done in the extending ClassDeclaration and InterfaceDeclaration.
		return false;
	}

	private void rewriteReturnType(MethodDeclaration node, boolean isConstructor, boolean isConstructorChange) {
		// TODO
		//		ChildPropertyDescriptor property = (node.getAST().apiLevel() == PHP4_INTERNAL) ? MethodDeclaration.RETURN_TYPE_PROPERTY : MethodDeclaration.RETURN_TYPE2_PROPERTY;
		//
		//		// weakness in the AST: return type can exist, even if missing in source
		//		ASTNode originalReturnType = (ASTNode) getOriginalValue(node, property);
		//		boolean returnTypeExists = originalReturnType != null && originalReturnType.getStart() != -1;
		//		if (!isConstructorChange && returnTypeExists) {
		//			rewriteRequiredNode(node, property);
		//			return;
		//		}
		//		// difficult cases: return type insert or remove
		//		ASTNode newReturnType = (ASTNode) getNewValue(node, property);
		//		if (isConstructorChange || !returnTypeExists && newReturnType != originalReturnType) {
		//			// use the start offset of the method name to insert
		//			ASTNode originalMethodName = (ASTNode) getOriginalValue(node, MethodDeclaration.NAME_PROPERTY);
		//			int nextStart = originalMethodName.getStart(); // see bug 84049: can't use extended offset
		//			TextEditGroup editGroup = getEditGroup(node, property);
		//			if (isConstructor || !returnTypeExists) { // insert
		//				doTextInsert(nextStart, newReturnType, getIndent(nextStart), true, editGroup);
		//				doTextInsert(nextStart, " ", editGroup); //$NON-NLS-1$
		//			} else { // remove up to the method name
		//				int offset = getExtendedOffset(originalReturnType);
		//				doTextRemoveAndVisit(offset, nextStart - offset, originalReturnType, editGroup);
		//			}
		//		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(MethodDeclaration)
	 */
	public boolean visit(MethodDeclaration node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		rewriteModifiers(node, MethodDeclaration.MODIFIER_PROPERTY, node.getStart());
		rewriteRequiredNode(node, MethodDeclaration.FUNCTION_PROPERTY);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(Block)
	 */
	public boolean visit(Block node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		int blockStart = node.getStart();
		int startIndent = getIndent(node.getStart()) + 1;
		rewriteParagraphList(node, Block.STATEMENTS_PROPERTY, blockStart + 1, startIndent, 0, 1);

		// Check for While, For, If, ForEach, Switch
		// In each case, the basic form of the alternate syntax is to change the opening brace to a colon (:) 
		// and the closing brace to endif;, endwhile;, endfor;, endforeach;, or endswitch;, respectively.
		RewriteEvent event = getEvent(node, Block.IS_CURLY_PROPERTY);
		if (event != null) {
			TextEditGroup editGroup = getEditGroup(event);
			boolean shouldBeCurly = (Boolean) event.getNewValue();
			StructuralPropertyDescriptor propertyDescriptor = node.getLocationInParent();
			if (propertyDescriptor == IfStatement.TRUE_STATEMENT_PROPERTY || propertyDescriptor == IfStatement.FALSE_STATEMENT_PROPERTY) {
				rewriteIfBlocks(node, editGroup, shouldBeCurly);
			} else if (propertyDescriptor == WhileStatement.BODY_PROPERTY) {
				Symbol symbol = SymbolsProvider.getSymbol(SymbolsProvider.END_WHILE_ID, scanner.getPHPVersion());
				rewriteBlock(node, editGroup, shouldBeCurly, "endwhile", symbol);
			} else if (propertyDescriptor == ForStatement.BODY_PROPERTY) {
				Symbol symbol = SymbolsProvider.getSymbol(SymbolsProvider.END_FOR_ID, scanner.getPHPVersion());
				rewriteBlock(node, editGroup, shouldBeCurly, "endfor", symbol);
			} else if (propertyDescriptor == ForEachStatement.STATEMENT_PROPERTY) {
				Symbol symbol = SymbolsProvider.getSymbol(SymbolsProvider.END_FOREACH_ID, scanner.getPHPVersion());
				rewriteBlock(node, editGroup, shouldBeCurly, "endforeach", symbol);
			} else if (propertyDescriptor == SwitchStatement.BODY_PROPERTY) {
				Symbol symbol = SymbolsProvider.getSymbol(SymbolsProvider.END_SWITCH_ID, scanner.getPHPVersion());
				rewriteBlock(node, editGroup, shouldBeCurly, "endswitch", symbol);
			}
		}

		return false;
	}

	/*
	 * Rewrite the If statement blocks from curly to 'Alternative syntax' blocks.
	 * 
	 * @param node
	 * @param editGroup
	 * @param shouldBeCurly
	 */
	private void rewriteIfBlocks(Block node, TextEditGroup editGroup, boolean shouldBeCurly) {
		int blockStart = node.getStart();
		int blockEnd = node.getEnd() - 1;
		StructuralPropertyDescriptor propertyDescriptor = node.getLocationInParent();
		IfStatement ifStatement = (IfStatement) node.getParent();
		if (propertyDescriptor == IfStatement.TRUE_STATEMENT_PROPERTY) {
			if (shouldBeCurly) {
				// Change the if's open block char to the opening brace char
				doTextReplace(blockStart, 1, "{", editGroup);
				// Change the closing mark to be a closing brace

				doTextInsert(blockEnd + 1, "\n}", editGroup);
				// remove the endif token at this stage
				Symbol endIfSymbol = SymbolsProvider.getSymbol(SymbolsProvider.END_IF_ID, scanner.getPHPVersion());
				try {
					int endifPos = getScanner().getTokenStartOffset(endIfSymbol, blockEnd);
					// search for the semicolon that might appear after that token
					int semicolonPos = scanToSemicolon(endifPos + 5);
					doTextRemove(endifPos, semicolonPos - endifPos + 1, editGroup);
				} catch (Exception e) {
					// Should not happen, since the if should have an endif
					handleException(e);
				}
			} else {
				doTextReplace(blockStart, 1, ":", editGroup);
				// In case that we don't have a false statement, add the endif word
				if (ifStatement.getFalseStatement() == null) {
					doTextReplace(blockEnd, 1, "endif;", editGroup);
				} else {
					doTextRemove(blockEnd, 1, editGroup);
				}
			}
		} else if (propertyDescriptor == IfStatement.FALSE_STATEMENT_PROPERTY) {
			// Change the if's closing block char to an endif; or to a closing brace
			if (shouldBeCurly) {
				// replace the opening colon char to a brace char
				doTextReplace(blockStart, 1, "{", editGroup);
				// close the block with a brace
				doTextInsert(blockEnd + 1, "\n}", editGroup);
			} else {
				doTextReplace(blockStart, 1, ":", editGroup);
				// End the if statement
				doTextReplace(blockEnd, 1, "endif;", editGroup);
			}
		}
	}

	/*
	 * Rewrite a statement blocks from curly to 'Alternative syntax' blocks.
	 * 
	 * @param node
	 * @param editGroup
	 * @param shouldBeCurly
	 * @param keyword The block closing keyword (e.g. endif, endwhile etc.)
	 * @param keywordSymbol The keyword Symbol
	 */
	private void rewriteBlock(Block node, TextEditGroup editGroup, boolean shouldBeCurly, String keyword, Symbol keywordSymbol) {
		int blockStart = node.getStart();
		int blockEnd = node.getEnd() - 1;
		if (shouldBeCurly) {
			// Change the if's open block char to the opening brace char
			doTextReplace(blockStart, 1, "{", editGroup);
			// Change the closing mark to be a closing brace
			doTextInsert(blockEnd + 1, "\n}", editGroup);
			try {
				// We scan for the Block end position by looking for the symbol's start offset.
				// The search is done a bit before the block's end in order to cover the 'SwitchStatement' that is 
				// marking the end of the Block after the 'endswitch' keyword (unlike all the other types of blocks).
				int endBlockPos = getScanner().getTokenStartOffset(keywordSymbol, blockEnd - keyword.length());
				// search for the semicolon that might appear after that token
				int semicolonPos = scanToSemicolon(endBlockPos + keyword.length());
				doTextRemove(endBlockPos, semicolonPos - endBlockPos + 1, editGroup);
			} catch (Exception e) {
				// Should not happen, since the if should have the keyword
				handleException(e);
			}
		} else {
			doTextReplace(blockStart, 1, ":", editGroup);
			// In case that we don't have a false statement, add the keyword
			doTextReplace(blockEnd, 1, keyword + ';', editGroup);
		}
	}

	/*
	 * Scan to the first semicolon that appears in the content after the given position.
	 * The scan skip all the whitespace characters and tries to locate the first non-whitespace that is a semicolon (;).
	 * The return value is the semicolon index.
	 * The given index is returned when no semicolon was found right after the whitespaces. 
	 */
	private int scanToSemicolon(int startIndex) {
		for (int i = startIndex; i < content.length; i++) {
			if (content[i] == ';') {
				return i;
			}
			if (content[i] != ' ' && content[i] != '\t' && content[i] != '\n' && content[i] != '\r') {
				return startIndex;
			}
		}
		return startIndex;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ReturnStatement)
	 */
	public boolean visit(ReturnStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		try {
			int offset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.RETURN_ID, scanner.getPHPVersion()), node.getStart());
			ensureSpaceBeforeReplace(node, ReturnStatement.EXPRESSION_PROPERTY, offset, 0);

			rewriteNode(node, ReturnStatement.EXPRESSION_PROPERTY, offset, ASTRewriteFormatter.SPACE);
		} catch (CoreException e) {
			handleException(e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ArrayAccess)
	 */
	public boolean visit(ArrayAccess node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		if (isChanged(node, ArrayAccess.DOLLARED_PROPERTY)) {
			rewriteVariableDollar(node);
		}
		if (isChanged(node, ArrayAccess.ARRAY_TYPE_PROPERTY)) {
			rewriteArrayAccessType(node);
		}

		return rewriteRequiredNodeVisit(node, ArrayAccess.NAME_PROPERTY, ArrayAccess.INDEX_PROPERTY);
	}

	private void rewriteArrayAccessType(ArrayAccess arrayAccess) {
		RewriteEvent event = getEvent(arrayAccess, ArrayAccess.ARRAY_TYPE_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			Integer original = (Integer) event.getOriginalValue();
			try {
				if (original.intValue() == ArrayAccess.VARIABLE_ARRAY) {
					// the modification was from a variable array to a variable hashtable.
					int openPos = getLeftBracketStartPosition(arrayAccess.getStart());
					int closePos = arrayAccess.getEnd() - 1;
					TextEditGroup editGroup = getEditGroup(event);
					doTextReplace(openPos, 1, "{", editGroup);
					doTextReplace(closePos, 1, "}", editGroup);
				} else {
					// the modification was from a variable hashtable to a variable array.
					int openPos = getLeftBraceStartPosition(arrayAccess.getStart());
					int closePos = arrayAccess.getEnd() - 1;
					TextEditGroup editGroup = getEditGroup(event);
					doTextReplace(openPos, 1, "[", editGroup);
					doTextReplace(closePos, 1, "]", editGroup);
				}
			} catch (CoreException ce) {
				handleException(ce);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ArrayCreation)
	 */
	public boolean visit(ArrayCreation node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		rewriteNodeList(node, ArrayCreation.ELEMENTS_PROPERTY, node.getStart(), "", ", ");
		return false;
	}

	//
	//	private Type getElementType(ArrayType parent) {
	//		Type t = (Type) getOriginalValue(parent, ArrayType.COMPONENT_TYPE_PROPERTY);
	//		while (t.isArrayType()) {
	//			t = (Type) getOriginalValue(t, ArrayType.COMPONENT_TYPE_PROPERTY);
	//		}
	//		return t;
	//	}

	//	private int getDimensions(ArrayType parent) {
	//		Type t = (Type) getOriginalValue(parent, ArrayType.COMPONENT_TYPE_PROPERTY);
	//		int dimensions = 1; // always include this array type
	//		while (t.isArrayType()) {
	//			dimensions++;
	//			t = (Type) getOriginalValue(t, ArrayType.COMPONENT_TYPE_PROPERTY);
	//		}
	//		return dimensions;
	//	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(Assignment)
	 */
	public boolean visit(Assignment node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, Assignment.LEFT_HAND_SIDE_PROPERTY);
		rewriteOperation(node, Assignment.OPERATOR_PROPERTY, pos);
		rewriteRequiredNode(node, Assignment.RIGHT_HAND_SIDE_PROPERTY);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(BreakStatement)
	 */
	public boolean visit(BreakStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		try {
			int offset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.BREAK_ID, scanner.getPHPVersion()), node.getStart());
			rewriteNode(node, BreakStatement.EXPRESSION_PROPERTY, offset, ASTRewriteFormatter.SPACE); // space between break and label
		} catch (CoreException e) {
			handleException(e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(CastExpression)
	 */
	public boolean visit(CastExpression cast) {
		if (isChanged(cast, CastExpression.CASTING_TYPE_PROPERTY)) {
			try {
				rewriteCastType(cast);
			} catch (Exception e) {
				handleException(e);
			}
		}
		return rewriteRequiredNodeVisit(cast, CastExpression.EXPRESSION_PROPERTY);
	}

	private void rewriteCastType(CastExpression cast) throws CoreException {
		RewriteEvent event = getEvent(cast, CastExpression.CASTING_TYPE_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			TextEditGroup editGroup = getEditGroup(event);
			String castType = CastExpression.getCastType(cast.getCastingType());
			int offset = cast.getStart() + 1;
			int closingParenOffset = getScanner().getTokenStartOffset((SymbolsProvider.getSymbol(SymbolsProvider.RPAREN_ID, scanner.getPHPVersion())), offset);

			doTextReplace(offset, closingParenOffset - offset, castType, editGroup);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(CatchClause)
	 */
	public boolean visit(CatchClause node) { // catch (Exception) Block
		return rewriteRequiredNodeVisit(node, CatchClause.CLASS_NAME_PROPERTY, CatchClause.BODY_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ClassInstanceCreation)
	 */
	public boolean visit(ClassInstanceCreation node) {
		rewriteRequiredNodeVisit(node, ClassInstanceCreation.CLASSNAME_PROPERTY);
		if (isChanged(node, ClassInstanceCreation.CTOR_PARAMS_PROPERTY)) {
			try {
				int pos = getLeftParenthesesStartPosition(node.getStart()) + 1;
				rewriteNodeList(node, ClassInstanceCreation.CTOR_PARAMS_PROPERTY, pos, "", ", ");
			} catch (Exception e) {
				handleException(e);
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ConditionalExpression)
	 */
	public boolean visit(ConditionalExpression node) { // expression ? thenExpression : elseExpression
		return rewriteRequiredNodeVisit(node, ConditionalExpression.CONDITION_PROPERTY, ConditionalExpression.IF_TRUE_PROPERTY, ConditionalExpression.IF_FALSE_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ContinueStatement)
	 */
	public boolean visit(ContinueStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		try {
			int offset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.CONTINUE_ID, scanner.getPHPVersion()), node.getStart());
			rewriteNode(node, ContinueStatement.EXPRESSION_PROPERTY, offset, ASTRewriteFormatter.SPACE); // space between continue and label
		} catch (CoreException e) {
			handleException(e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(DoStatement)
	 */
	public boolean visit(DoStatement node) { // do statement while expression
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = node.getStart();
		try {
			RewriteEvent event = getEvent(node, DoStatement.BODY_PROPERTY);
			if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
				int startOffset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.DO_ID, scanner.getPHPVersion()), pos);
				ASTNode body = (ASTNode) event.getOriginalValue();
				int bodyEnd = body.getStart() + body.getLength();
				int endPos = getScanner().getTokenStartOffset(SymbolsProvider.getSymbol(SymbolsProvider.WHILE_ID, scanner.getPHPVersion()), bodyEnd);
				rewriteBodyNode(node, DoStatement.BODY_PROPERTY, startOffset, endPos, getIndent(node.getStart()), this.formatter.DO_BLOCK); // body
			} else {
				voidVisit(node, DoStatement.BODY_PROPERTY);
			}
		} catch (CoreException e) {
			handleException(e);
		}

		rewriteRequiredNode(node, DoStatement.CONDITION_PROPERTY);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(EmptyStatement)
	 */
	public boolean visit(EmptyStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		changeNotSupported(node); // no modification possible
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ExpressionStatement)
	 */
	public boolean visit(ExpressionStatement node) { // expression
		return rewriteRequiredNodeVisit(node, ExpressionStatement.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(FieldAccess)
	 */
	public boolean visit(FieldAccess node) { // expression.name
		return rewriteRequiredNodeVisit(node, FieldAccess.DISPATCHER_PROPERTY, FieldAccess.FIELD_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(FieldDeclaration)
	 */
	public boolean visit(FieldsDeclaration node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		rewriteModifiers(node, FieldsDeclaration.MODIFIER_PROPERTY, node.getStart());
		rewriteNodeList(node, FieldsDeclaration.FIELDS_PROPERTY, node.getStart() + node.getModifierString().length(), "", ", ");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.SingleFieldDeclaration)
	 */
	public boolean visit(SingleFieldDeclaration singleFieldDeclaration) {
		if (!hasChildrenChanges(singleFieldDeclaration)) {
			return doVisitUnchangedChildren(singleFieldDeclaration);
		}
		RewriteEvent event = getEvent(singleFieldDeclaration, SingleFieldDeclaration.VALUE_PROPERTY);
		if (event != null) {
			rewriteOptionalValueProperty(singleFieldDeclaration, singleFieldDeclaration.getName().getEnd(), SingleFieldDeclaration.VALUE_PROPERTY, event);
		}
		return rewriteRequiredNodeVisit(singleFieldDeclaration, SingleFieldDeclaration.NAME_PROPERTY);
	}

	/*
	 * Rewrite an optional value property.
	 * This should handle declarations like $a = 3 etc. and add, remove or modify the assigned value.
	 * Note that the new value that will be used must be an ASTNode, so in any other case where a value property does not
	 * hold an ASTNode this call will fail.
	 * 
	 * @param node The node that we rewrite
	 * @param pos The position that the edit should start from
	 * @param valueProperty ChildPropertyDescriptor
	 * @param event Non-null RewriteEvent
	 */
	private void rewriteOptionalValueProperty(ASTNode node, int pos, ChildPropertyDescriptor valueProperty, RewriteEvent event) {
		TextEditGroup editGroup = getEditGroup(event);
		int kind = event.getChangeKind();
		switch (kind) {
			case RewriteEvent.REPLACED:
				rewriteRequiredNode(node, valueProperty);
				break;
			case RewriteEvent.INSERTED:
				ASTNode newNode = (ASTNode) event.getNewValue();
				doTextInsert(pos, " = ", editGroup);
				doTextInsert(pos, newNode, 0, false, editGroup);
				break;
			case RewriteEvent.REMOVED:
				ASTNode originalNode = (ASTNode) event.getOriginalValue();
				int endPos = originalNode.getEnd();
				doTextRemove(pos, endPos - pos, editGroup);
				break;
			default:
				// do nothing
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ForStatement)
	 */
	public boolean visit(ForStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		try {
			int pos = node.getStart();

			if (isChanged(node, ForStatement.INITIALIZERS_PROPERTY)) {
				// position after opening parent
				int startOffset = getLeftParenthesesStartPosition(pos) + 1;
				pos = rewriteNodeList(node, ForStatement.INITIALIZERS_PROPERTY, startOffset, "", ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				pos = doVisit(node, ForStatement.INITIALIZERS_PROPERTY, pos);
			}

			// position after first semicolon
			Symbol semicolonSym = SymbolsProvider.getSymbol(SymbolsProvider.SEMICOLON_ID, scanner.getPHPVersion());
			pos = getScanner().getTokenEndOffset(semicolonSym, pos);

			pos = rewriteNodeList(node, ForStatement.EXPRESSION_PROPERTY, pos, "", ", ");

			if (isChanged(node, ForStatement.UPDATERS_PROPERTY)) {
				int startOffset = getScanner().getTokenEndOffset(semicolonSym, pos);
				pos = rewriteNodeList(node, ForStatement.UPDATERS_PROPERTY, startOffset, "", ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				pos = doVisit(node, ForStatement.UPDATERS_PROPERTY, pos);
			}

			RewriteEvent bodyEvent = getEvent(node, ForStatement.BODY_PROPERTY);
			if (bodyEvent != null && bodyEvent.getChangeKind() == RewriteEvent.REPLACED) {
				int startOffset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.RPAREN_ID, scanner.getPHPVersion()), pos);
				rewriteBodyNode(node, ForStatement.BODY_PROPERTY, startOffset, -1, getIndent(node.getStart()), this.formatter.FOR_BLOCK); // body
			} else {
				voidVisit(node, ForStatement.BODY_PROPERTY);
			}

		} catch (CoreException e) {
			handleException(e);
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(IfStatement)
	 */
	public boolean visit(IfStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, IfStatement.CONDITION_PROPERTY); // statement

		RewriteEvent thenEvent = getEvent(node, IfStatement.TRUE_STATEMENT_PROPERTY);
		int elseChange = getChangeKind(node, IfStatement.FALSE_STATEMENT_PROPERTY);

		if (thenEvent != null && thenEvent.getChangeKind() != RewriteEvent.UNCHANGED) {
			try {
				pos = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.RPAREN_ID, scanner.getPHPVersion()), pos); // after the closing parent
				int indent = getIndent(node.getStart());

				int endPos = -1;
				Object elseStatement = getOriginalValue(node, IfStatement.FALSE_STATEMENT_PROPERTY);
				if (elseStatement != null) {
					ASTNode thenStatement = (ASTNode) thenEvent.getOriginalValue();
					endPos = getScanner().getTokenStartOffset(SymbolsProvider.getSymbol(SymbolsProvider.ELSE_ID, scanner.getPHPVersion()), thenStatement.getStart() + thenStatement.getLength()); // else keyword
				}
				if (elseStatement == null || elseChange != RewriteEvent.UNCHANGED) {
					pos = rewriteBodyNode(node, IfStatement.TRUE_STATEMENT_PROPERTY, pos, endPos, indent, this.formatter.IF_BLOCK_NO_ELSE);
				} else {
					pos = rewriteBodyNode(node, IfStatement.TRUE_STATEMENT_PROPERTY, pos, endPos, indent, this.formatter.IF_BLOCK_WITH_ELSE);
				}
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			pos = doVisit(node, IfStatement.TRUE_STATEMENT_PROPERTY, pos);
		}

		if (elseChange != RewriteEvent.UNCHANGED) {
			int indent = getIndent(node.getStart());
			Object newThen = getNewValue(node, IfStatement.TRUE_STATEMENT_PROPERTY);
			if (newThen instanceof Block) {
				rewriteBodyNode(node, IfStatement.FALSE_STATEMENT_PROPERTY, pos, -1, indent, this.formatter.ELSE_AFTER_BLOCK);
			} else {
				rewriteBodyNode(node, IfStatement.FALSE_STATEMENT_PROPERTY, pos, -1, indent, this.formatter.ELSE_AFTER_STATEMENT);
			}
		} else {
			pos = doVisit(node, IfStatement.FALSE_STATEMENT_PROPERTY, pos);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(InfixExpression)
	 */
	public boolean visit(InfixExpression node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, InfixExpression.LEFT_OPERAND_PROPERTY);
		boolean needsNewOperation = isChanged(node, InfixExpression.OPERATOR_PROPERTY);
		if (needsNewOperation) {
			String operation = InfixExpression.getOperator((Integer) getNewValue(node, InfixExpression.OPERATOR_PROPERTY));
			replaceOperation(pos, operation, getEditGroup(node, InfixExpression.OPERATOR_PROPERTY));
		}
		pos = rewriteRequiredNode(node, InfixExpression.RIGHT_OPERAND_PROPERTY);
		return false;
	}

	public void ensureSpaceAfterReplace(ASTNode node, ChildPropertyDescriptor desc) {
		if (getChangeKind(node, desc) == RewriteEvent.REPLACED) {
			int leftOperandEnd = getExtendedEnd((ASTNode) getOriginalValue(node, desc));
			try {
				int offset = getScanner().getNextStartOffset(leftOperandEnd/*, true*/); // instanceof

				if (offset == leftOperandEnd) {
					doTextInsert(offset, String.valueOf(' '), getEditGroup(node, desc));
				}
			} catch (CoreException e) {
				handleException(e);
			}
		}
	}

	public void ensureSpaceBeforeReplace(ASTNode node, ChildPropertyDescriptor desc, int offset, int numTokenBefore) {
		// bug 103970
		if (getChangeKind(node, desc) == RewriteEvent.REPLACED) {
			try {
				while (numTokenBefore > 0) {
					offset = getScanner().getNextEndOffset(offset/*, true*/);
					numTokenBefore--;
				}
				if (offset == getExtendedOffset((ASTNode) getOriginalValue(node, desc))) {
					doTextInsert(offset, String.valueOf(' '), getEditGroup(node, desc));
				}
			} catch (CoreException e) {
				handleException(e);
			}
		}
	}

	//	/* (non-Javadoc)
	//	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(Javadoc)
	//	 */
	//	public boolean visit(Javadoc node) {
	//		if (!hasChildrenChanges(node)) {
	//			return doVisitUnchangedChildren(node);
	//		}
	//		int startPos= node.getStart() + 3;
	//		String separator= getLineDelimiter() + getIndentAtOffset(node.getStart())  + " * "; //$NON-NLS-1$
	//		
	//		rewriteNodeList(node, Javadoc.TAGS_PROPERTY, startPos, separator, separator);
	//		return false;
	//	}

	//	/* (non-Javadoc)
	//	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(LabeledStatement)
	//	 */
	//	public boolean visit(LabeledStatement node) {
	//		if (!hasChildrenChanges(node)) {
	//			return doVisitUnchangedChildren(node);
	//		}
	//		
	//		rewriteRequiredNode(node, LabeledStatement.LABEL_PROPERTY);
	//		rewriteRequiredNode(node, LabeledStatement.BODY_PROPERTY);		
	//		return false;
	//	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(MethodInvocation)
	 */
	public boolean visit(MethodInvocation node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteOptionalQualifier(node, MethodInvocation.DISPATCHER_PROPERTY, node.getStart());
		//		pos = rewriteRequiredNode(node, MethodInvocation.NAME_PROPERTY);
		pos = rewriteRequiredNode(node, MethodInvocation.METHOD_PROPERTY);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(PostfixExpression)
	 */
	public boolean visit(PostfixExpression node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, PostfixExpression.VARIABLE_PROPERTY);
		rewriteOperation(node, PostfixExpression.OPERATOR_PROPERTY, pos);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(PrefixExpression)
	 */
	public boolean visit(PrefixExpression node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		rewriteOperation(node, PrefixExpression.OPERATOR_PROPERTY, node.getStart());
		rewriteRequiredNode(node, PrefixExpression.VARIABLE_PROPERTY);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(SwitchCase)
	 */
	public boolean visit(SwitchCase node) {
		// dont allow switching from case to default or back. New statements should be created.
		if (isChanged(node, SwitchCase.ACTIONS_PROPERTY)) {
			int pos = node.getStart();
			ASTNode value = node.getValue();
			if (value != null) {
				int valueEnd = value.getEnd();
				if (valueEnd > -1) {
					pos = valueEnd;
				}
			}
			rewriteNodeList(node, SwitchCase.ACTIONS_PROPERTY, pos, "", "");
		}
		return rewriteRequiredNodeVisit(node, SwitchCase.VALUE_PROPERTY);
	}

	class SwitchListRewriter extends ParagraphListRewriter {

		public SwitchListRewriter(int initialIndent) {
			super(initialIndent, 0);
		}

		protected int getNodeIndent(int nodeIndex) {
			int indent = getInitialIndent();
			ASTNode node = (ASTNode) this.list[nodeIndex].getOriginalValue();
			if (node == null) {
				node = (ASTNode) this.list[nodeIndex].getNewValue();
			}
			if (node.getType() != ASTNode.SWITCH_CASE) {
				indent++;
			}
			return indent;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(SwitchStatement)
	 */
	public boolean visit(SwitchStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, SwitchStatement.EXPRESSION_PROPERTY);
		Block body = node.getBody();
		ChildListPropertyDescriptor property = Block.STATEMENTS_PROPERTY;
		if (getChangeKind(body, property) != RewriteEvent.UNCHANGED) {
			try {
				pos = getLeftBraceStartPosition(pos) + 1;
				int insertIndent = getIndent(body.getStart()) + 1;
				ParagraphListRewriter listRewriter = new SwitchListRewriter(insertIndent);
				StringBuffer leadString = new StringBuffer();
				leadString.append(getLineDelimiter());
				leadString.append(createIndentString(insertIndent));
				listRewriter.rewriteList(body, property, pos, leadString.toString());
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			voidVisit(body, Block.STATEMENTS_PROPERTY);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(ThrowStatement)
	 */
	public boolean visit(ThrowStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		try {
			int offset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.THROW_ID, scanner.getPHPVersion()), node.getStart());
			ensureSpaceBeforeReplace(node, ThrowStatement.EXPRESSION_PROPERTY, offset, 0);

			rewriteRequiredNode(node, ThrowStatement.EXPRESSION_PROPERTY);
		} catch (CoreException e) {
			handleException(e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(TryStatement)
	 */
	public boolean visit(TryStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, TryStatement.BODY_PROPERTY);

		if (isChanged(node, TryStatement.CATCH_CLAUSES_PROPERTY)) {
			int indent = getIndent(node.getStart());
			//			String prefix = this.formatter.CATCH_BLOCK.getPrefix(indent);
			// TODO - Get the formatter prefix for the catch clause indentation
			String prefix = "";
			pos = rewriteNodeList(node, TryStatement.CATCH_CLAUSES_PROPERTY, pos, prefix, prefix);
		} else {
			pos = doVisit(node, TryStatement.CATCH_CLAUSES_PROPERTY, pos);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(WhileStatement)
	 */
	public boolean visit(WhileStatement node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}

		int pos = rewriteRequiredNode(node, WhileStatement.CONDITION_PROPERTY);

		try {
			if (isChanged(node, WhileStatement.BODY_PROPERTY)) {
				int startOffset = getScanner().getTokenEndOffset(SymbolsProvider.getSymbol(SymbolsProvider.RPAREN_ID, scanner.getPHPVersion()), pos);
				rewriteBodyNode(node, WhileStatement.BODY_PROPERTY, startOffset, -1, getIndent(node.getStart()), this.formatter.WHILE_BLOCK); // body
			} else {
				voidVisit(node, WhileStatement.BODY_PROPERTY);
			}
		} catch (CoreException e) {
			handleException(e);
		}
		return false;
	}

	final void handleException(Throwable e) {
		IllegalArgumentException runtimeException = new IllegalArgumentException("Document does not match the AST"); //$NON-NLS-1$
		runtimeException.initCause(e);
		throw runtimeException;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ArrayElement)
	 */
	public boolean visit(ArrayElement arrayElement) {
		// Since the key property is optional, we need to treat it separately.
		rewriteArrayElementKey(arrayElement);
		return rewriteRequiredNodeVisit(arrayElement, ArrayElement.VALUE_PROPERTY);
	}

	/*
	 * Rewrite the optional key part of the array element
	 * 
	 * @param arrayElement
	 */
	private void rewriteArrayElementKey(ArrayElement arrayElement) {
		RewriteEvent event = getEvent(arrayElement, ArrayElement.KEY_PROPERTY);
		if (event != null) {
			rewriteKeyValue(arrayElement, event);
		}

	}

	/*
	 * Rewrite a key=>value pair
	 * @param arrayElement
	 * @param event
	 */
	private void rewriteKeyValue(ASTNode node, RewriteEvent event) {
		int kind = event.getChangeKind();
		TextEditGroup editGroup = getEditGroup(event);
		switch (kind) {
			case RewriteEvent.INSERTED:
				// We should insert the key and the => string
				Expression newValue = (Expression) event.getNewValue();
				int start = node.getStart();
				if (node instanceof ForEachStatement) {
					start = ((ForEachStatement) node).getValue().getStart();
				}
				doTextInsert(start, newValue, 0, false, editGroup);
				doTextInsert(start, "=>", editGroup);
				break;
			case RewriteEvent.REMOVED:
				Expression removedExpression = (Expression) event.getOriginalValue();
				int deleteEndPos = -1;
				if (node instanceof ArrayElement) {
					deleteEndPos = ((ArrayElement) node).getValue().getStart();
				} else if (node instanceof ForEachStatement) {
					deleteEndPos = ((ForEachStatement) node).getValue().getStart();
				}
				int deleteStartPos = removedExpression.getStart();
				doTextRemove(deleteStartPos, deleteEndPos - deleteStartPos, editGroup);
				break;
			case RewriteEvent.REPLACED:
				if (node instanceof ArrayElement) {
					rewriteRequiredNode(node, ArrayElement.KEY_PROPERTY);
				} else if (node instanceof ForEachStatement) {
					rewriteRequiredNode(node, ForEachStatement.KEY_PROPERTY);
				}
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ASTError)
	 */
	@Override
	public boolean visit(ASTError astError) {
		if (!hasChildrenChanges(astError)) {
			return doVisitUnchangedChildren(astError);
		}
		changeNotSupported(astError); // no modification possible
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.BackTickExpression)
	 */
	public boolean visit(BackTickExpression backTickExpression) {
		if (!hasChildrenChanges(backTickExpression)) {
			return doVisitUnchangedChildren(backTickExpression);
		}
		rewriteNodeList(backTickExpression, BackTickExpression.EXPRESSIONS_PROPERTY, backTickExpression.getStart(), "", "");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ClassConstantDeclaration)
	 */
	@Override
	public boolean visit(ConstantDeclaration classConstantDeclaration) {
		// TODO - Same as with the DeclareStatement, this require a different rewriting for now.
		if (!hasChildrenChanges(classConstantDeclaration)) {
			return doVisitUnchangedChildren(classConstantDeclaration);
		}
		changeNotSupported(classConstantDeclaration);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ClassDeclaration)
	 */
	@Override
	public boolean visit(ClassDeclaration classDeclaration) {
		if (!hasChildrenChanges(classDeclaration)) {
			return doVisitUnchangedChildren(classDeclaration);
		}
		try {
			// Rewrite the modifier property
			rewriteClassDeclarationModifier(classDeclaration);
			// Rewrite the super-class property
			rewriteClassDeclarationSuperClass(classDeclaration);
			// Rewrite the interfaces
			int pos;
			if (classDeclaration.getSuperClass() == null) {
				pos = classDeclaration.getName().getEnd();
			} else {
				pos = classDeclaration.getSuperClass().getEnd();
			}
			rewriteNodeList(classDeclaration, ClassDeclaration.INTERFACES_PROPERTY, pos, " implements ", ", ");
			// Rewrite the name and the body
			return rewriteRequiredNodeVisit(classDeclaration, ClassDeclaration.NAME_PROPERTY, ClassDeclaration.BODY_PROPERTY);
		} catch (Exception e) {
			handleException(e);
		}
		return false;
	}

	/* 
	 * Rewrite the modifier part of the class declaration 
	 * @param classDeclaration
	 */
	private void rewriteClassDeclarationModifier(ClassDeclaration classDeclaration) throws CoreException {
		RewriteEvent event = getEvent(classDeclaration, ClassDeclaration.MODIFIER_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			TextEditGroup editGroup = getEditGroup(event);
			int start = classDeclaration.getStart();
			int classKeywordStart = getScanner().getTokenStartOffset(SymbolsProvider.getSymbol(SymbolsProvider.CLASS_ID, scanner.getPHPVersion()), start);
			int modifier = (Integer) event.getNewValue();
			switch (modifier) {
				case ClassDeclaration.MODIFIER_NONE:
					// The modifier was removed
					doTextRemove(start, classKeywordStart - start, editGroup);
					break;
				case ClassDeclaration.MODIFIER_ABSTRACT:
				case ClassDeclaration.MODIFIER_FINAL:
					// Replace what we have (if we have it) with the 'abstract' or the 'final' keyword
					doTextReplace(start, classKeywordStart - start, ClassDeclaration.getModifier(modifier) + ' ', editGroup);
					break;
			}
		}
	}

	/* 
	 * Rewrite the super-class part of the class declaration 
	 * @param classDeclaration
	 */
	private void rewriteClassDeclarationSuperClass(ClassDeclaration classDeclaration) throws CoreException {
		RewriteEvent event = getEvent(classDeclaration, ClassDeclaration.SUPER_CLASS_PROPERTY);
		if (event != null) {
			int changeKind = event.getChangeKind();
			TextEditGroup editGroup = getEditGroup(event);
			switch (changeKind) {
				case RewriteEvent.INSERTED:
					Identifier superClass = (Identifier) event.getNewValue();
					int insertionPos = classDeclaration.getName().getEnd();
					String extendsKeyword = " extends ";
					doTextInsert(insertionPos, extendsKeyword, editGroup);
					doTextInsert(insertionPos, superClass, 0, false, editGroup);
					break;
				case RewriteEvent.REMOVED:
					superClass = (Identifier) event.getOriginalValue();
					// locate the end offset of the deletion
					int deletionEnd;
					if (classDeclaration.interfaces().size() > 0) {
						deletionEnd = getScanner().getTokenStartOffset(SymbolsProvider.getSymbol(SymbolsProvider.IMPLEMENTS_ID, scanner.getPHPVersion()), classDeclaration.getStart());
					} else {
						deletionEnd = classDeclaration.getBody().getStart();
					}
					int deletionStart = classDeclaration.getName().getEnd();
					doTextRemove(deletionStart, deletionEnd - deletionStart, editGroup);
					doTextInsert(deletionStart, " ", editGroup);
					break;
				case RewriteEvent.REPLACED:
					rewriteRequiredNode(classDeclaration, ClassDeclaration.SUPER_CLASS_PROPERTY);
					break;
			}
		}
	}

	/**
	 * Rewrite the interfaces in the class declaration
	 * @param classDeclaration
	 */
	private void rewriteInterfaces(ClassDeclaration classDeclaration) {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ClassName)
	 */
	public boolean visit(ClassName className) {
		return rewriteRequiredNodeVisit(className, ClassName.NAME_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.CloneExpression)
	 */
	public boolean visit(CloneExpression cloneExpression) {
		return rewriteRequiredNodeVisit(cloneExpression, CloneExpression.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Comment)
	 */
	@Override
	public boolean visit(Comment comment) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.DeclareStatement)
	 */
	@Override
	public boolean visit(DeclareStatement declareStatement) {
		//	TODO - This require a different rewrite approach since a regular list rewrite will not work here when adding and removing items
		if (!hasChildrenChanges(declareStatement)) {
			return doVisitUnchangedChildren(declareStatement);
		}
		changeNotSupported(declareStatement);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.EchoStatement)
	 */
	public boolean visit(EchoStatement echoStatement) {
		if (!hasChildrenChanges(echoStatement)) {
			return doVisitUnchangedChildren(echoStatement);
		}
		rewriteNodeList(echoStatement, EchoStatement.EXPRESSIONS_PROPERTY, echoStatement.getStart(), "", ", ");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ForEachStatement)
	 */
	@Override
	public boolean visit(ForEachStatement forEachStatement) {
		if (!hasChildrenChanges(forEachStatement)) {
			return doVisitUnchangedChildren(forEachStatement);
		}
		RewriteEvent event = getEvent(forEachStatement, ForEachStatement.KEY_PROPERTY);
		if (event != null) {
			rewriteKeyValue(forEachStatement, event);
		}
		rewriteRequiredNodeVisit(forEachStatement, ForEachStatement.EXPRESSION_PROPERTY, ForEachStatement.VALUE_PROPERTY, ForEachStatement.STATEMENT_PROPERTY);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.FormalParameter)
	 */
	public boolean visit(FormalParameter formalParameter) {
		try {
			if (formalParameter.getAST().apiLevel() == PHPVersion.PHP4 && isChanged(formalParameter, FormalParameter.IS_MANDATORY_PROPERTY)) {
				if (formalParameter.getAST().apiLevel() == PHPVersion.PHP5) {
					throw new CoreException(new Status(IStatus.ERROR, PHPCorePlugin.ID, "Could not set a FormalParameter 'isMandatory' property for PHP5 AST"));
				}
				// Rewrite the isMandatory field
				RewriteEvent event = getEvent(formalParameter, FormalParameter.IS_MANDATORY_PROPERTY);
				if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
					TextEditGroup editGroup = getEditGroup(event);
					boolean isMandatory = (Boolean) event.getNewValue();
					if (isMandatory) {
						// remove the const from the start of the parameter (6 characters including the space)
						doTextRemove(formalParameter.getStart(), 6, editGroup);
					} else {
						doTextInsert(formalParameter.getStart(), "const ", editGroup);
					}
				}
			}
		} catch (Exception e) {
			handleException(e);
		}

		// Rewrite the parameter type
		rewriteFormalParameterType(formalParameter);
		// Rewrite the default parameters
		rewriteFomalParameterDefault(formalParameter);
		return rewriteRequiredNodeVisit(formalParameter, FormalParameter.PARAMETER_NAME_PROPERTY);
	}

	/*
	 * Rewrite the parameter type of a {@link FormalParameter}
	 * @param formalParameter
	 */
	private void rewriteFormalParameterType(FormalParameter formalParameter) {
		// Rewrite the parameter type
		RewriteEvent event = getEvent(formalParameter, FormalParameter.PARAMETER_TYPE_PROPERTY);
		if (event != null) {
			int kind = event.getChangeKind();
			switch (kind) {
				case RewriteEvent.REPLACED:
					int pos = rewriteRequiredNode(formalParameter, FormalParameter.PARAMETER_TYPE_PROPERTY);
					ASTNode originalValue = (ASTNode) event.getOriginalValue();
					if (originalValue == null || originalValue.getLength() == 0) {
						// Add another space to split the type from the name
						doTextInsert(pos, " ", getEditGroup(event));
					}
					break;
				case RewriteEvent.INSERTED:
					Identifier identifier = (Identifier) event.getNewValue();
					String name = identifier.getName();
					if (name != null) {
						if (!name.endsWith(" ") && !name.endsWith("\t")) {
							name += ' ';
						}
						doTextInsert(formalParameter.getStart(), name, getEditGroup(event));
					}
					break;
				case RewriteEvent.REMOVED:
					originalValue = (ASTNode) event.getOriginalValue();
					doTextRemove(originalValue.getStart(), originalValue.getLength(), getEditGroup(event));
					break;
			}
		}
	}

	/*
	 * Rewrite the parameter's default value of a {@link FormalParameter}
	 * @param formalParameter
	 */
	private void rewriteFomalParameterDefault(FormalParameter formalParameter) {
		RewriteEvent event = getEvent(formalParameter, FormalParameter.DEFAULT_VALUE_PROPERTY);
		if (event != null) {
			int kind = event.getChangeKind();
			switch (kind) {
				case RewriteEvent.REPLACED:
					rewriteRequiredNode(formalParameter, FormalParameter.DEFAULT_VALUE_PROPERTY);
					break;
				case RewriteEvent.INSERTED:
					Scalar scalar = (Scalar) event.getNewValue();
					String scalarValue = scalar.getStringValue();
					if (scalar != null) {
						doTextInsert(formalParameter.getStart(), " = " + scalarValue, getEditGroup(event));
					}
					break;
				case RewriteEvent.REMOVED:
					ASTNode originalValue = (ASTNode) event.getOriginalValue();
					int nameEnd = formalParameter.getParameterName().getEnd();
					doTextRemove(nameEnd, originalValue.getEnd() - nameEnd, getEditGroup(event));
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration)
	 */
	public boolean visit(FunctionDeclaration functionDeclaration) {
		if (!hasChildrenChanges(functionDeclaration)) {
			return doVisitUnchangedChildren(functionDeclaration);
		}
		// Reference
		rewriteFunctionReference(functionDeclaration);
		// Name
		int pos = rewriteRequiredNode(functionDeclaration, FunctionDeclaration.NAME_PROPERTY);
		// Parameters
		if (isChanged(functionDeclaration, FunctionDeclaration.FORMAL_PARAMETERS_PROPERTY)) {
			try {
				int startOffset = getLeftParenthesesStartPosition(pos) + 1;
				rewriteNodeList(functionDeclaration, FunctionDeclaration.FORMAL_PARAMETERS_PROPERTY, startOffset, "", ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			voidVisit(functionDeclaration, FunctionDeclaration.FORMAL_PARAMETERS_PROPERTY);
		}
		// Body
		rewriteRequiredNode(functionDeclaration, FunctionDeclaration.BODY_PROPERTY);
		return false;
	}

	private void rewriteFunctionReference(FunctionDeclaration functionDeclaration) {
		RewriteEvent event = getEvent(functionDeclaration, FunctionDeclaration.IS_REFERENCE_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			boolean isReference = (Boolean) event.getNewValue();

			TextEditGroup editGroup = getEditGroup(event);
			// we need to remove everything between the word 'function' to the start of the function's
			// name and then place a blank or an &.
			int nameStart = functionDeclaration.getFunctionName().getStart();
			int startDeletionFrom = functionDeclaration.getStart() + 8; // 8 is the 'function' keyword length
			doTextRemove(startDeletionFrom, nameStart - startDeletionFrom, editGroup);
			if (isReference) {
				// we need to insert the &
				doTextInsert(startDeletionFrom, " &", editGroup);
			} else {
				doTextInsert(startDeletionFrom, " ", editGroup);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.FunctionInvocation)
	 */
	public boolean visit(FunctionInvocation functionInvocation) {
		if (!hasChildrenChanges(functionInvocation)) {
			return doVisitUnchangedChildren(functionInvocation);
		}
		int pos = rewriteRequiredNode(functionInvocation, FunctionInvocation.FUNCTION_PROPERTY);
		if (isChanged(functionInvocation, FunctionInvocation.PARAMETERS_PROPERTY)) {
			// eval position after opening parent
			try {
				int startOffset = getLeftParenthesesStartPosition(pos) + 1;
				rewriteNodeList(functionInvocation, FunctionInvocation.PARAMETERS_PROPERTY, startOffset, "", ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			voidVisit(functionInvocation, FunctionInvocation.PARAMETERS_PROPERTY);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.FunctionName)
	 */
	public boolean visit(FunctionName functionName) {
		return rewriteRequiredNodeVisit(functionName, FunctionName.NAME_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.GlobalStatement)
	 */
	public boolean visit(GlobalStatement globalStatement) {
		if (!hasChildrenChanges(globalStatement)) {
			return doVisitUnchangedChildren(globalStatement);
		}
		rewriteNodeList(globalStatement, GlobalStatement.VARIABLES_PROPERTY, globalStatement.getStart(), "", ", ");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Identifier)
	 */
	public boolean visit(Identifier node) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		String newString = (String) getNewValue(node, Identifier.NAME_PROPERTY);
		TextEditGroup group = getEditGroup(node, Identifier.NAME_PROPERTY);
		doTextReplace(node.getStart(), node.getLength(), newString, group);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.IgnoreError)
	 */
	public boolean visit(IgnoreError ignoreError) {
		return rewriteRequiredNodeVisit(ignoreError, IgnoreError.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Include)
	 */
	public boolean visit(Include include) {
		if (!hasChildrenChanges(include)) {
			return doVisitUnchangedChildren(include);
		}
		int offsetGap = 0;
		if (isChanged(include, Include.INCLUDE_TYPE_PROPERTY)) {
			RewriteEvent event = getEvent(include, Include.INCLUDE_TYPE_PROPERTY);
			if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
				TextEditGroup editGroup = getEditGroup(event);
				int newValue = (Integer) event.getNewValue();
				int originalTypeLength = Include.getType((Integer) event.getOriginalValue()).length();
				String newIncludeType = Include.getType(newValue);
				doTextReplace(include.getStart(), originalTypeLength, newIncludeType, editGroup);
				offsetGap = originalTypeLength - newIncludeType.length();
			}
		}
		if (isChanged(include, Include.EXPRESSION_PROPERTY)) {
			// This should be treated specially in case that the new expression removes the parentheses of the 
			// include. 
			// In this situation, we might get a syntax error, so we have to deal with it here.
			RewriteEvent event = getEvent(include, Include.EXPRESSION_PROPERTY);
			if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
				TextEditGroup editGroup = getEditGroup(event);
				int typeEndOffset = Include.getType(include.getIncludeType()).length() + include.getStart();
				ASTNode newNode = (ASTNode) event.getNewValue();
				ASTNode originalNode = (ASTNode) event.getOriginalValue();
				// In case that the offset of the original node started right at the end of the include string, check that
				// the new node is not a parenthesis, and if not - add a blank
				if (typeEndOffset + offsetGap == originalNode.getStart() && newNode.getType() != ASTNode.PARENTHESIS_EXPRESSION) {
					doTextInsert(offsetGap + typeEndOffset, " ", editGroup); //$NON-NLS-1$
					rewriteRequiredNode(include, Include.EXPRESSION_PROPERTY);
				} else {
					rewriteRequiredNode(include, Include.EXPRESSION_PROPERTY);
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.InLineHtml)
	 */
	@Override
	public boolean visit(InLineHtml inLineHtml) {
		if (!hasChildrenChanges(inLineHtml)) {
			return doVisitUnchangedChildren(inLineHtml);
		}
		changeNotSupported(inLineHtml); // no modification possible
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.InstanceOfExpression)
	 */
	public boolean visit(InstanceOfExpression instanceOfExpression) {
		return rewriteRequiredNodeVisit(instanceOfExpression, InstanceOfExpression.CLASSNAME_PROPERTY, InstanceOfExpression.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.InterfaceDeclaration)
	 */
	@Override
	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		if (!hasChildrenChanges(interfaceDeclaration)) {
			return doVisitUnchangedChildren(interfaceDeclaration);
		}
		try {
			// Rewrite the extended interfaces
			rewriteNodeList(interfaceDeclaration, InterfaceDeclaration.INTERFACES_PROPERTY, interfaceDeclaration.getName().getEnd(), " extends ", ", ");
			// Rewrite the name and the body
			return rewriteRequiredNodeVisit(interfaceDeclaration, InterfaceDeclaration.NAME_PROPERTY, InterfaceDeclaration.BODY_PROPERTY);
		} catch (Exception e) {
			handleException(e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ListVariable)
	 */
	public boolean visit(ListVariable listVariable) {
		if (!hasChildrenChanges(listVariable)) {
			return doVisitUnchangedChildren(listVariable);
		}
		rewriteNodeList(listVariable, ListVariable.VARIABLES_PROPERTY, listVariable.getStart(), "", ",");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ParenthesisExpression)
	 */
	public boolean visit(ParenthesisExpression parenthesisExpression) {
		return rewriteRequiredNodeVisit(parenthesisExpression, ParenthesisExpression.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Quote)
	 */
	public boolean visit(Quote quote) {
		// Rewrite the quoate's type
		rewriteQuoteType(quote);
		// Rewrite the quoate's expressions list
		rewriteQuoteExpression(quote);
		return false;
	}

	/**
	 * @param quote
	 */
	private void rewriteQuoteExpression(Quote quote) {
		int expressionStart = quote.getStart();
		switch (quote.getQuoteType()) {
			case Quote.QT_QUOTE:
			case Quote.QT_SINGLE:
				expressionStart++;
				break;
			case Quote.QT_HEREDOC:
				// search for the first new line 
				int quoteEnd = quote.getEnd();
				for (; expressionStart < quoteEnd; expressionStart++) {
					if (content[expressionStart] == '\n' || content[expressionStart] == '\r') {
						expressionStart++;
						if (content[expressionStart] == '\n' || content[expressionStart] == '\r') {
							expressionStart++;
						}
						break;
					}
				}
				break;

		}
		rewriteNodeList(quote, Quote.EXPRESSIONS_PROPERTY, expressionStart, "", "");
		// In case that the original expressions list was empty, we should add a new line
		List originalValue = (List) getOriginalValue(quote, Quote.EXPRESSIONS_PROPERTY);
		List newValue = (List) getNewValue(quote, Quote.EXPRESSIONS_PROPERTY);
		if ((originalValue == null || originalValue.size() == 0) && newValue != null && newValue.size() > 0) {
			doTextInsert(expressionStart, "\n", getEditGroup(quote, Quote.EXPRESSIONS_PROPERTY));
		}
	}

	/**
	 * @param quote
	 */
	private void rewriteQuoteType(Quote quote) {
		if (isChanged(quote, Quote.QUOTE_TYPE_PROPERTY)) {
			List<Expression> expressions = quote.expressions();
			if (expressions.size() > 0) {
				RewriteEvent event = getEvent(quote, Quote.QUOTE_TYPE_PROPERTY);
				if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
					TextEditGroup editGroup = getEditGroup(event);
					int expressionsStart = expressions.get(0).getStart();
					int expressionsEnd = expressions.get(expressions.size() - 1).getEnd();
					int quoteStart = quote.getStart();
					int quoteEnd = quote.getEnd();
					// In case this is a Heredoc, we need to fix the expression end position to exclude the heredoc marker.
					int originalType = (Integer) event.getOriginalValue();
					if (originalType == Quote.QT_HEREDOC) {
						for (; expressionsEnd > expressionsStart; expressionsEnd--) {
							if (content[expressionsEnd] == '\n' || content[expressionsEnd] == '\r') {
								// Check that we don't have a pair of \n\r before we break the loop
								if (content[expressionsEnd - 1] == '\n' || content[expressionsEnd - 1] == '\r') {
									expressionsEnd--;
								}
								break;
							}
						}
					}

					int newType = (Integer) event.getNewValue();
					String newStart = "";
					String newEnd = "";
					switch (newType) {
						case Quote.QT_SINGLE:
							newStart = "'";
							newEnd = "'";
							break;
						case Quote.QT_QUOTE:
							newStart = "\"";
							newEnd = "\"";
							break;
						case Quote.QT_NOWDOC:
							newStart = "<<<'Heredoc'\n";
							newEnd = "\nHeredoc;\n";
							break;
						case Quote.QT_HEREDOC:
							newStart = "<<<Heredoc\n";
							newEnd = "\nHeredoc;\n";
							break;
					}
					doTextReplace(quoteStart, expressionsStart - quoteStart, newStart, editGroup);
					doTextReplace(expressionsEnd, quoteEnd - expressionsEnd, newEnd, editGroup);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Reference)
	 */
	public boolean visit(Reference reference) {
		return rewriteRequiredNodeVisit(reference, Reference.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.ReflectionVariable)
	 */
	public boolean visit(ReflectionVariable reflectionVariable) {
		if (isChanged(reflectionVariable, ReflectionVariable.DOLLARED_PROPERTY)) {
			rewriteVariableDollar(reflectionVariable);
		}
		return rewriteRequiredNodeVisit(reflectionVariable, ReflectionVariable.NAME_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Scalar)
	 */
	public boolean visit(Scalar scalar) {
		// For now, we ignore the Scalar.TYPE_PROPERTY changes and we only deal with the value property of the scalar.
		RewriteEvent event = getEvent(scalar, Scalar.VALUE_PROPERTY);
		if (event != null) {
			String newValue = (String) event.getNewValue();
			if (newValue == null) {
				newValue = "";
			}
			if (event != null) {
				int kind = event.getChangeKind();
				switch (kind) {
					case RewriteEvent.REPLACED:
						doTextReplace(scalar.getStart(), scalar.getLength(), newValue, getEditGroup(event));
						break;
					case RewriteEvent.INSERTED:
						doTextInsert(scalar.getStart(), newValue, getEditGroup(event));
						break;
					case RewriteEvent.REMOVED:
						doTextRemove(scalar.getStart(), scalar.getLength(), getEditGroup(event));
						break;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess)
	 */
	public boolean visit(StaticConstantAccess classConstantAccess) {
		return rewriteRequiredNodeVisit(classConstantAccess, StaticConstantAccess.CLASS_NAME_PROPERTY, StaticConstantAccess.CONSTANT_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.StaticFieldAccess)
	 */
	public boolean visit(StaticFieldAccess staticFieldAccess) {
		return rewriteRequiredNodeVisit(staticFieldAccess, StaticFieldAccess.CLASS_NAME_PROPERTY, StaticFieldAccess.FIELD_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.StaticMethodInvocation)
	 */
	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		return rewriteRequiredNodeVisit(staticMethodInvocation, StaticMethodInvocation.CLASS_NAME_PROPERTY, StaticMethodInvocation.METHOD_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.StaticStatement)
	 */
	public boolean visit(StaticStatement staticStatement) {
		if (!hasChildrenChanges(staticStatement)) {
			return doVisitUnchangedChildren(staticStatement);
		}
		rewriteNodeList(staticStatement, StaticStatement.EXPRESSIONS_PROPERTY, staticStatement.getStart(), "", ", ");
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.UnaryOperation)
	 */
	public boolean visit(UnaryOperation unaryOperation) {
		rewriteOperation(unaryOperation, UnaryOperation.OPERATOR_PROPERTY, unaryOperation.getStart());
		return rewriteRequiredNodeVisit(unaryOperation, UnaryOperation.EXPRESSION_PROPERTY);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.visitor.AbstractVisitor#visit(org.eclipse.php.internal.core.ast.nodes.Variable)
	 */
	public boolean visit(Variable variable) {
		if (isChanged(variable, Variable.DOLLARED_PROPERTY)) {
			rewriteVariableDollar(variable);
		}
		return rewriteRequiredNodeVisit(variable, Variable.NAME_PROPERTY);
	}

	public boolean visit(GotoLabel gotoLabel) {
		return rewriteRequiredNodeVisit(gotoLabel, GotoLabel.NAME_PROPERTY);
	}

	public boolean visit(GotoStatement gotoStatement) {
		return rewriteRequiredNodeVisit(gotoStatement, GotoStatement.LABEL_PROPERTY);
	}

	public boolean visit(LambdaFunctionDeclaration lambdaFunctionDeclaration) {
		if (!hasChildrenChanges(lambdaFunctionDeclaration)) {
			return doVisitUnchangedChildren(lambdaFunctionDeclaration);
		}

		// Reference
		RewriteEvent event = getEvent(lambdaFunctionDeclaration, LambdaFunctionDeclaration.IS_REFERENCE_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			boolean isReference = (Boolean) event.getNewValue();

			try {
				TextEditGroup editGroup = getEditGroup(event);
				// we need to remove everything between the word 'function' to the start of the function's
				// name and then place a blank or an &.
				int startDeletionFrom = lambdaFunctionDeclaration.getStart() + 8; // 8 is the 'function' keyword length
				int startOffset = getLeftParenthesesStartPosition(startDeletionFrom);
				doTextRemove(startDeletionFrom, startOffset - startDeletionFrom, editGroup);
				if (isReference) {
					// we need to insert the &
					doTextInsert(startDeletionFrom, " & ", editGroup);
				} else {
					doTextInsert(startDeletionFrom, " ", editGroup);
				}
			} catch (CoreException e) {
				handleException(e);
			}
		}

		// Parameters
		if (isChanged(lambdaFunctionDeclaration, LambdaFunctionDeclaration.FORMAL_PARAMETERS_PROPERTY)) {
			try {
				int startDeletionFrom = lambdaFunctionDeclaration.getStart() + 8; // 8 is the 'function' keyword length
				int startOffset = getLeftParenthesesStartPosition(startDeletionFrom);
				rewriteNodeList(lambdaFunctionDeclaration, LambdaFunctionDeclaration.FORMAL_PARAMETERS_PROPERTY, startOffset, "", ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			voidVisit(lambdaFunctionDeclaration, LambdaFunctionDeclaration.FORMAL_PARAMETERS_PROPERTY);
		}
		
		// Lexical vars
		if (isChanged(lambdaFunctionDeclaration, LambdaFunctionDeclaration.LEXICAL_VARIABLES_PROPERTY)) {
			try {
				int startDeletionFrom = lambdaFunctionDeclaration.getStart() + 8; // 8 is the 'function' keyword length
				int startOffset = getRightBraceStartPosition(startDeletionFrom) + 1;
				rewriteNodeList(lambdaFunctionDeclaration, LambdaFunctionDeclaration.LEXICAL_VARIABLES_PROPERTY, startOffset, " as ", ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (CoreException e) {
				handleException(e);
			}
		} else {
			voidVisit(lambdaFunctionDeclaration, LambdaFunctionDeclaration.LEXICAL_VARIABLES_PROPERTY);
		}
		
		// Body
		rewriteRequiredNode(lambdaFunctionDeclaration, LambdaFunctionDeclaration.BODY_PROPERTY);
		
		return false;
	}

	public boolean visit(NamespaceDeclaration namespaceDeclaration) {
		return rewriteRequiredNodeVisit(namespaceDeclaration, NamespaceDeclaration.NAME_PROPERTY, NamespaceDeclaration.BODY_PROPERTY);
	}

	public boolean visit(NamespaceName namespaceName) {

		// Make the necessary changes to add or remove the '\' and 'namespace' prefixes
		RewriteEvent event = getEvent(namespaceName, NamespaceName.GLOBAL_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			TextEditGroup editGroup = getEditGroup(event);
			if ((Boolean) event.getNewValue()) {
				// Add the '\' to the namespace name
				this.doTextInsert(namespaceName.getStart(), "\\", editGroup);
			} else {
				// Remove the '\' from the namespace name
				this.doTextRemove(namespaceName.getStart(), 1, editGroup);
			}
		}
		event = getEvent(namespaceName, NamespaceName.CURRENT_PROPERTY);
		if (event != null && event.getChangeKind() == RewriteEvent.REPLACED) {
			TextEditGroup editGroup = getEditGroup(event);
			if ((Boolean) event.getNewValue()) {
				// Add the 'namespace' to the namespace name
				this.doTextInsert(namespaceName.getStart(), "namespace\\", editGroup);
			} else {
				// Remove the 'namespace' from the namespace name
				this.doTextRemove(namespaceName.getStart(), 10, editGroup);
			}
		}
		
		int pos = namespaceName.getStart();
		if (namespaceName.isGlobal()) {
			pos += 1;
		}
		if (namespaceName.isCurrent()) {
			pos += 10;
		}
		
		if (isChanged(namespaceName, NamespaceName.ELEMENTS_PROPERTY)) {
			rewriteNodeList(namespaceName, NamespaceName.ELEMENTS_PROPERTY, pos, "", "\\");
		} else {
			voidVisit(namespaceName, NamespaceName.ELEMENTS_PROPERTY);
		}
		
		return false;
	}

	public boolean visit(UseStatement useStatement) {
		rewriteNodeList(useStatement, UseStatement.PARTS_PROPERTY, useStatement.getStart(), "", ", "); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}

	public boolean visit(UseStatementPart useStatementPart) {
		return rewriteRequiredNodeVisit(useStatementPart, UseStatementPart.NAME_PROPERTY, UseStatementPart.ALIAS_PROPERTY);  
	}

	/**
	 * A general visit implementations that calls {@link #doVisitUnchangedChildren(ASTNode)} in case that the node 
	 * has no changes in its children, and calls {@link #rewriteRequiredNode(ASTNode, StructuralPropertyDescriptor)}
	 * on the given {@link StructuralPropertyDescriptor} properties. 
	 * The given property descriptors should be only {@link ChildPropertyDescriptor} and {@link SimplePropertyDescriptor}.
	 * In any other case, {@link #rewriteNodeList(ASTNode, StructuralPropertyDescriptor, int, String, String)} might be needed.
	 * 
	 * @param node An {@link ASTNode}.
	 * @param properties StructuralPropertyDescriptors of the types {@link ChildPropertyDescriptor} and {@link SimplePropertyDescriptor}.
	 * @return false by default
	 */
	protected boolean rewriteRequiredNodeVisit(ASTNode node, StructuralPropertyDescriptor... properties) {
		if (!hasChildrenChanges(node)) {
			return doVisitUnchangedChildren(node);
		}
		for (StructuralPropertyDescriptor property : properties) {
			rewriteRequiredNode(node, property);
		}
		return false;
	}
}
