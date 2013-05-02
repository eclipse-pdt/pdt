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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.format.DefaultCodeFormattingProcessor;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.text.edits.*;

/**
 * AST rewrite formatter
 * 
 * @author shalom (based on JDT code)
 */
/* package */final class ASTRewriteFormatter {

	// TODO - Need a code cleanup
	private static IFormatterProcessorFactory contentFormatter;

	public static class NodeMarker extends Position {
		public Object data;
	}

	private class ExtendedFlattener extends ASTRewriteFlattener {

		private ArrayList<NodeMarker> positions;

		public ExtendedFlattener(RewriteEventStore store) {
			super(store);
			this.positions = new ArrayList<NodeMarker>();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#preVisit(ASTNode)
		 */
		public void preVisit(ASTNode node) {
			Object trackData = getEventStore().getTrackedNodeData(node);
			if (trackData != null) {
				addMarker(trackData, this.result.length(), 0);
			}
			Object placeholderData = getPlaceholders().getPlaceholderData(node);
			if (placeholderData != null) {
				addMarker(placeholderData, this.result.length(), 0);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jdt.core.dom.ASTVisitor#postVisit(ASTNode)
		 */
		public void postVisit(ASTNode node) {
			Object placeholderData = getPlaceholders().getPlaceholderData(node);
			if (placeholderData != null) {
				fixupLength(placeholderData, this.result.length());
			}
			Object trackData = getEventStore().getTrackedNodeData(node);
			if (trackData != null) {
				fixupLength(trackData, this.result.length());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jdt.internal.corext.dom.ASTRewriteFlattener#visit(org
		 * .eclipse.jdt.core.dom.Block)
		 */
		public boolean visit(Block node) {
			if (getPlaceholders().isCollapsed(node)) {
				visitList(node, Block.STATEMENTS_PROPERTY, null);
				return false;
			}
			return super.visit(node);
		}

		private NodeMarker addMarker(Object annotation, int startOffset,
				int length) {
			NodeMarker marker = new NodeMarker();
			marker.offset = startOffset;
			marker.length = length;
			marker.data = annotation;
			this.positions.add(marker);
			return marker;
		}

		private void fixupLength(Object data, int endOffset) {
			for (int i = this.positions.size() - 1; i >= 0; i--) {
				NodeMarker marker = this.positions.get(i);
				if (marker.data == data) {
					marker.length = endOffset - marker.offset;
					return;
				}
			}
		}

		public NodeMarker[] getMarkers() {
			return this.positions
					.toArray(new NodeMarker[this.positions.size()]);
		}
	}

	private final String lineDelimiter;
	private final int tabWidth;
	private final int indentWidth;

	private final NodeInfoStore placeholders;
	private final RewriteEventStore eventStore;

	private final Map options;
	private IDocument document;
	private PHPVersion phpVersion;
	private boolean useShortTags;

	public ASTRewriteFormatter(IDocument document, NodeInfoStore placeholders,
			RewriteEventStore eventStore, Map options, String lineDelimiter,
			PHPVersion version, boolean useShortTags) {
		this.document = document;
		this.placeholders = placeholders;
		this.eventStore = eventStore;

		if (options == null) {
			options = PHPCorePlugin.getOptions();
		}
		// options.put(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT,
		// String.valueOf(9999));

		this.options = options;
		this.lineDelimiter = lineDelimiter;

		this.tabWidth = IndentManipulation.getTabWidth(options);
		this.indentWidth = IndentManipulation.getIndentWidth(options);
		this.phpVersion = version;
	}

	public NodeInfoStore getPlaceholders() {
		return this.placeholders;
	}

	public RewriteEventStore getEventStore() {
		return this.eventStore;
	}

	public int getTabWidth() {
		return this.tabWidth;
	}

	public int getIndentWidth() {
		return this.indentWidth;
	}

	public String getLineDelimiter() {
		return this.lineDelimiter;
	}

	/**
	 * Returns the string accumulated in the visit formatted using the default
	 * formatter. Updates the existing node's positions.
	 * 
	 * @param node
	 *            The node to flatten.
	 * @param initialIndentationLevel
	 *            The initial indentation level.
	 * @param resultingMarkers
	 *            Resulting the updated NodeMarkers.
	 * @return Returns the serialized and formatted code.
	 */
	public String getFormattedResult(ASTNode node, int initialIndentationLevel,
			Collection<NodeMarker> resultingMarkers) {

		ExtendedFlattener flattener = new ExtendedFlattener(this.eventStore);
		node.accept(flattener);

		NodeMarker[] markers = flattener.getMarkers();
		for (int i = 0; i < markers.length; i++) {
			resultingMarkers.add(markers[i]); // add to result
		}

		String unformatted = flattener.getResult();
		TextEdit edit = formatNode(node, unformatted, initialIndentationLevel);
		if (edit == null) {
			if (initialIndentationLevel > 0) {
				// at least correct the indent
				String indentString = createIndentString(initialIndentationLevel);
				ReplaceEdit[] edits = IndentManipulation.getChangeIndentEdits(
						unformatted, 0, this.tabWidth, this.indentWidth,
						indentString);
				edit = new MultiTextEdit();
				edit.addChild(new InsertEdit(0, indentString));
				edit.addChildren(edits);
			} else {
				return unformatted;
			}
		}
		return evaluateFormatterEdit(unformatted, edit, markers);
	}

	public String createIndentString(int indentationUnits) {
		try {
			return createCodeFormatter(this.options, new Region(0, 0),
					createDocument("", null)).createIndentationString( //$NON-NLS-1$
					indentationUnits);
		} catch (Exception e) {
			Logger.logException(e);
		}
		return ""; //$NON-NLS-1$
	}

	public String getIndentString(String currentLine) {
		return IndentManipulation.extractIndentString(currentLine,
				this.tabWidth, this.indentWidth);
	}

	public String changeIndent(String code, int codeIndentLevel,
			String newIndent) {
		return IndentManipulation.changeIndent(code, codeIndentLevel,
				this.tabWidth, this.indentWidth, newIndent, this.lineDelimiter);
	}

	public int computeIndentUnits(String line) {
		return IndentManipulation.measureIndentUnits(line, this.tabWidth,
				this.indentWidth);
	}

	/**
	 * Evaluates the edit on the given string.
	 * 
	 * @param string
	 *            The string to format
	 * @param edit
	 *            The edit resulted from the code formatter
	 * @param positions
	 *            Positions to update or <code>null</code>.
	 * @return The formatted string
	 * @throws IllegalArgumentException
	 *             If the positions are not inside the string, a
	 *             IllegalArgumentException is thrown.
	 */
	public static String evaluateFormatterEdit(String string, TextEdit edit,
			Position[] positions) {
		try {
			Document doc = createDocument(string, positions);
			edit.apply(doc, 0);
			if (positions != null) {
				for (int i = 0; i < positions.length; i++) {
					Assert.isTrue(!positions[i].isDeleted,
							"Position got deleted"); //$NON-NLS-1$
				}
			}
			return doc.get();
		} catch (BadLocationException e) {
			// JavaPlugin.log(e); // bug in the formatter
			Assert.isTrue(
					false,
					"Fromatter created edits with wrong positions: " + e.getMessage()); //$NON-NLS-1$
		}
		return null;
	}

	public TextEdit formatString(int kind, String string, int offset,
			int length, int indentationLevel) {
		try {
			ICodeFormattingProcessor codeFormatter = createCodeFormatter(
					this.options, new Region(offset, length),
					createDocument(string, null));
			return codeFormatter.getTextEdits();
		} catch (Exception e) {
			Logger.logException(e);
		}
		return new MultiTextEdit();
	}

	private ICodeFormattingProcessor createCodeFormatter(Map options,
			IRegion region, IDocument document) throws Exception {
		if (getContentFomatter() != null) {
			return contentFormatter.getCodeFormattingProcessor(document,
					phpVersion, useShortTags, region);
		}
		return new DefaultCodeFormattingProcessor(options);
	}

	/*
	 * Returns an instance of IFormatterProcessorFactory extracted from the
	 * extension point of the 'phpFormatterProcessor'
	 */
	private static IFormatterProcessorFactory getContentFomatter() {
		if (contentFormatter != null) {
			return contentFormatter;
		}

		String formatterExtensionName = "org.eclipse.php.ui.phpFormatterProcessor"; //$NON-NLS-1$
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(formatterExtensionName);
		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (element.getName().equals("processor")) { //$NON-NLS-1$
				final Object elementObject[] = new Object[1];
				SafeRunner.run(new ISafeRunnable() {
					public void run() throws Exception {
						elementObject[0] = element
								.createExecutableExtension("class"); //$NON-NLS-1$
					}

					public void handleException(Throwable exception) {
						Logger.logException(exception);
					}
				});
				if (elementObject[0] instanceof IFormatterProcessorFactory) {
					contentFormatter = (IFormatterProcessorFactory) elementObject[0];
				}
			}
		}
		return contentFormatter;
	}

	/**
	 * Creates edits that describe how to format the given string. Returns
	 * <code>null</code> if the code could not be formatted for the given kind.
	 * 
	 * @param node
	 *            Node describing the type of the string
	 * @param str
	 *            The unformatted string
	 * @param indentationLevel
	 * @return Returns the edit representing the result of the formatter
	 * @throws IllegalArgumentException
	 *             If the offset and length are not inside the string, a
	 *             IllegalArgumentException is thrown.
	 */
	private TextEdit formatNode(ASTNode node, String str, int indentationLevel) {
		// (shalom) We create chunk of php codes that we inject to the
		// formatter.
		// Every node is created with a <?php prefix and, optionally, some more
		// prefix string that is needed for the formatting.
		int code;
		String prefix = "<?php "; //$NON-NLS-1$
		String suffix = ""; //$NON-NLS-1$
		if (node instanceof Statement) {
			if (node.getType() == ASTNode.SWITCH_CASE) {
				prefix += "switch(1) {"; //$NON-NLS-1$
				suffix = "}"; //$NON-NLS-1$
			}
			if (node instanceof MethodDeclaration) {
				prefix += "class x{"; //$NON-NLS-1$
				suffix = "}"; //$NON-NLS-1$
			}

		} else if (node instanceof Expression
				&& node.getType() != ASTNode.SINGLE_FIELD_DECLARATION) {
		} else if (node instanceof BodyDeclaration) {
		} else if (node instanceof Comment) {
			prefix += "class x{"; //$NON-NLS-1$
			suffix = "}"; //$NON-NLS-1$
		} else {
			if (node.getType() == ASTNode.CATCH_CLAUSE) {
				prefix += "try {}"; //$NON-NLS-1$
			}
		}

		String concatStr = prefix + str + suffix;
		TextEdit edit = formatString(0, concatStr, prefix.length(),
				str.length(), indentationLevel);

		if (prefix.length() > 0) {
			edit = shifEdit(edit, prefix.length(), prefix);
		}
		return edit;
	}

	private static TextEdit shifEdit(TextEdit oldEdit, int diff, String prefix) {
		TextEdit newEdit;
		if (oldEdit instanceof ReplaceEdit) {
			ReplaceEdit edit = (ReplaceEdit) oldEdit;
			int editOffset = edit.getOffset();
			if (editOffset >= diff) {
				newEdit = new ReplaceEdit(editOffset - diff, edit.getLength(),
						edit.getText());
			} else {
				// The new edit is actually an insertion of whitespace and new
				// lines characters.
				newEdit = getEndPrefixInsertion(edit.getText());
			}
		} else if (oldEdit instanceof InsertEdit) {
			InsertEdit edit = (InsertEdit) oldEdit;
			int editOffset = edit.getOffset();
			if (editOffset >= diff) {
				newEdit = new InsertEdit(editOffset - diff, edit.getText());
			} else {
				newEdit = new InsertEdit(0, edit.getText());
			}
		} else if (oldEdit instanceof DeleteEdit) {
			DeleteEdit edit = (DeleteEdit) oldEdit;
			int editOffset = edit.getOffset();
			if (editOffset >= diff) {
				newEdit = new DeleteEdit(editOffset - diff, edit.getLength());
			} else {
				newEdit = new DeleteEdit(0, edit.getLength()
						- (diff - editOffset));
			}
		} else if (oldEdit instanceof MultiTextEdit) {
			newEdit = new MultiTextEdit();
		} else {
			return null; // not supported
		}
		TextEdit[] children = oldEdit.getChildren();
		for (int i = 0; i < children.length; i++) {
			TextEdit shifted = shifEdit(children[i], diff, prefix);
			if (shifted != null) {
				newEdit.addChild(shifted);
			}
		}
		return newEdit;
	}

	/*
	 * Returns an InsertEdit edit for a situation where the edit was done also
	 * on the prefix string. In this case, the edit always start from offset 0
	 * and the added string contains the new line and the whitespace characters.
	 */
	private static InsertEdit getEndPrefixInsertion(String newPrefix) {
		// collect the whitespace characters from the end of the new prefix
		StringBuilder newPrefixEnding = new StringBuilder();
		char[] newPrefixArr = newPrefix.toCharArray();
		for (int i = newPrefixArr.length - 1; i >= 0; i--) {
			char c = newPrefixArr[i];
			if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
				newPrefixEnding.append(c);
			} else {
				break;
			}
		}
		return new InsertEdit(0, newPrefixEnding.reverse().toString());
	}

	private static Document createDocument(String string, Position[] positions)
			throws IllegalArgumentException {
		Document doc = new Document(string);
		try {
			if (positions != null) {
				final String POS_CATEGORY = "myCategory"; //$NON-NLS-1$

				doc.addPositionCategory(POS_CATEGORY);
				doc.addPositionUpdater(new DefaultPositionUpdater(POS_CATEGORY) {
					protected boolean notDeleted() {
						int start = this.fOffset;
						int end = start + this.fLength;
						if (start < this.fPosition.offset
								&& (this.fPosition.offset
										+ this.fPosition.length < end)) {
							this.fPosition.offset = end; // deleted
															// positions:
															// set to
															// end of
															// remove
							return false;
						}
						return true;
					}
				});
				for (int i = 0; i < positions.length; i++) {
					try {
						doc.addPosition(POS_CATEGORY, positions[i]);
					} catch (BadLocationException e) {
						throw new IllegalArgumentException(
								"Position outside of string. offset: " + positions[i].offset + ", length: " + positions[i].length + ", string size: " + string.length()); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
					}
				}
			}
		} catch (BadPositionCategoryException cannotHappen) {
			// can not happen: category is correctly set up
		}
		return doc;
	}

	public static interface Prefix {
		String getPrefix(int indent);
	}

	public static interface BlockContext {
		String[] getPrefixAndSuffix(int indent, ASTNode node,
				RewriteEventStore events);
	}

	public static class ConstPrefix implements Prefix {
		private String prefix;

		public ConstPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix(int indent) {
			return this.prefix;
		}
	}

	private class FormattingPrefix implements Prefix {
		private int kind;
		private String string;
		private int start;
		private int length;

		public FormattingPrefix(String string, String sub, int kind) {
			this.start = string.indexOf(sub);
			this.length = sub.length();
			this.string = string;
			this.kind = kind;
		}

		public String getPrefix(int indent) {
			Position pos = new Position(this.start, this.length);
			String str = this.string;
			TextEdit res = formatString(this.kind, str, 0, str.length(), indent);
			if (res != null) {
				str = evaluateFormatterEdit(str, res, new Position[] { pos });
			}
			return str.substring(pos.offset + 1, pos.offset + pos.length - 1);
		}
	}

	private class BlockFormattingPrefix implements BlockContext {
		private String prefix;
		private int start;

		public BlockFormattingPrefix(String prefix, int start) {
			this.start = start;
			this.prefix = prefix;
		}

		public String[] getPrefixAndSuffix(int indent, ASTNode node,
				RewriteEventStore events) {
			String nodeString = ASTRewriteFlattener.asString(node, events);
			String str = this.prefix + nodeString;
			Position pos = new Position(this.start, this.prefix.length() + 1
					- this.start);

			TextEdit res = null; // formatString(CodeFormatter.K_STATEMENTS,
									// str, 0, str.length(), indent);
			if (res != null) {
				str = evaluateFormatterEdit(str, res, new Position[] { pos });
			}
			return new String[] {
					str.substring(pos.offset + 1, pos.offset + pos.length - 1),
					"" }; //$NON-NLS-1$
		}
	}

	private class BlockFormattingPrefixSuffix implements BlockContext {
		private String prefix;
		private String suffix;
		private int start;

		public BlockFormattingPrefixSuffix(String prefix, String suffix,
				int start) {
			this.start = start;
			this.suffix = suffix;
			this.prefix = prefix;
		}

		public String[] getPrefixAndSuffix(int indent, ASTNode node,
				RewriteEventStore events) {
			String nodeString = ASTRewriteFlattener.asString(node, events);
			int nodeStart = this.prefix.length();
			int nodeEnd = nodeStart + nodeString.length() - 1;

			String str = this.prefix + nodeString + this.suffix;

			Position pos1 = new Position(this.start, nodeStart + 1 - this.start);
			Position pos2 = new Position(nodeEnd, 2);

			TextEdit res = null; // formatString(CodeFormatter.K_STATEMENTS,
									// str, 0, str.length(), indent);
			if (res != null) {
				str = evaluateFormatterEdit(str, res, new Position[] { pos1,
						pos2 });
			}
			return new String[] {
					str.substring(pos1.offset + 1, pos1.offset + pos1.length
							- 1),
					str.substring(pos2.offset + 1, pos2.offset + pos2.length
							- 1) };
		}
	}

	public final static Prefix NONE = new ConstPrefix(""); //$NON-NLS-1$
	public final static Prefix SPACE = new ConstPrefix(" "); //$NON-NLS-1$
	public final static Prefix ASSERT_COMMENT = new ConstPrefix(" : "); //$NON-NLS-1$

	//	public final Prefix VAR_INITIALIZER= new FormattingPrefix("A a={};", "a={" , CodeFormatter.K_STATEMENTS); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix METHOD_BODY= new FormattingPrefix("void a() {}", ") {" , CodeFormatter.K_CLASS_BODY_DECLARATIONS); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix FINALLY_BLOCK= new FormattingPrefix("try {} finally {}", "} finally {", CodeFormatter.K_STATEMENTS); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix CATCH_BLOCK= new FormattingPrefix("try {} catch(Exception e) {}", "} c" , CodeFormatter.K_STATEMENTS); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix ANNOT_MEMBER_DEFAULT= new FormattingPrefix("String value() default 1;", ") default 1" , CodeFormatter.K_CLASS_BODY_DECLARATIONS); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix ENUM_BODY_START= new FormattingPrefix("enum E { A(){void foo(){}} }", "){v" , CodeFormatter.K_COMPILATION_UNIT); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix ENUM_BODY_END= new FormattingPrefix("enum E { A(){void foo(){ }}, B}", "}}," , CodeFormatter.K_COMPILATION_UNIT); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix WILDCARD_EXTENDS= new FormattingPrefix("A<? extends B> a;", "? extends B" , CodeFormatter.K_CLASS_BODY_DECLARATIONS); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix WILDCARD_SUPER= new FormattingPrefix("A<? super B> a;", "? super B" , CodeFormatter.K_CLASS_BODY_DECLARATIONS); //$NON-NLS-1$ //$NON-NLS-2$
	//
	//	public final Prefix FIRST_ENUM_CONST= new FormattingPrefix("enum E { X;}", "{ X" , CodeFormatter.K_COMPILATION_UNIT); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix ANNOTATION_SEPARATION= new FormattingPrefix("@A @B class C {}", "A @" , CodeFormatter.K_COMPILATION_UNIT); //$NON-NLS-1$ //$NON-NLS-2$
	//	public final Prefix PARAM_ANNOTATION_SEPARATION= new FormattingPrefix("void foo(@A @B p) { }", "A @" , CodeFormatter.K_CLASS_BODY_DECLARATIONS); //$NON-NLS-1$ //$NON-NLS-2$

	public final BlockContext IF_BLOCK_WITH_ELSE = new BlockFormattingPrefixSuffix(
			"if (true)", "else{}", 8); //$NON-NLS-1$ //$NON-NLS-2$
	public final BlockContext IF_BLOCK_NO_ELSE = new BlockFormattingPrefix(
			"if (true)", 8); //$NON-NLS-1$
	public final BlockContext ELSE_AFTER_STATEMENT = new BlockFormattingPrefix(
			"if (true) foo(); else ", 15); //$NON-NLS-1$
	public final BlockContext ELSE_AFTER_BLOCK = new BlockFormattingPrefix(
			"if (true) {} else ", 11); //$NON-NLS-1$

	public final BlockContext FOR_BLOCK = new BlockFormattingPrefix(
			"for (;;) ", 7); //$NON-NLS-1$
	public final BlockContext WHILE_BLOCK = new BlockFormattingPrefix(
			"while (true)", 11); //$NON-NLS-1$
	public final BlockContext DO_BLOCK = new BlockFormattingPrefixSuffix(
			"do ", "while (true);", 1); //$NON-NLS-1$ //$NON-NLS-2$

}
