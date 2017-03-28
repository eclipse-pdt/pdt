/*******************************************************************************
 * Copyright (c) 2009, 2016, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Yannick de Lange <yannickl88@gmail.com>
 *******************************************************************************/
package org.eclipse.php.internal.core.corext.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.php.core.compiler.ast.nodes.*;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;

/**
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class DocumentUtils {
	private static class NamespaceFinder extends PHPASTVisitor {
		Vector<NamespaceDeclaration> declarations = new Vector<NamespaceDeclaration>();

		public boolean visit(NamespaceDeclaration n) throws Exception {
			declarations.add(n);

			return super.visit(n);
		}

		public NamespaceDeclaration getNamespaceDeclarationFor(UseStatement statement) {
			for (NamespaceDeclaration n : declarations) {
				if (n.sourceStart() <= statement.sourceStart() && n.sourceEnd() >= statement.sourceEnd()) {
					return n;
				}
			}
			return null;
		}
	}

	private static class ReplaceAction {
		public List<UseStatement> statements;
		public int start, end;
		public String indent;

		public ReplaceAction(List<UseStatement> statements, int start, int end, String indent) {
			this.statements = statements;
			this.start = start;
			this.end = end;
			this.indent = indent;
		}
	}

	public static List<UseStatement> flatten(List<UseStatement> statements) {
		Vector<UseStatement> total = new Vector<UseStatement>();

		for (UseStatement statement : statements) {
			for (UsePart part : statement.getParts()) {
				Vector<UsePart> parts = new Vector<UsePart>();
				parts.add(part);

				total.add(new UseStatement(statement.start(), statement.end(), parts, statement.getStatementType()));
			}
		}

		return total;
	}

	/**
	 * Check if a classname is used in a string. The "excludePositions" list
	 * contains positions where we should NOT look if the classname is used
	 * (typically in comment sections). It is important that the
	 * "excludePositions" list is sorted by increasing position, or this method
	 * won't work correctly.
	 */
	public static boolean containsUseStatement(UsePart part, String contents, List<Position> excludePositions) {
		String className = null != part.getAlias() ? part.getAlias().toString()
				: (part.getNamespace() != null ? part.getNamespace().toString() : "");

		Pattern p = Pattern.compile("(?i)\\b(" + Pattern.quote(className) + ")\\b");
		Matcher m = p.matcher(contents);
		int restartPos = 0;
		while (m.find()) {
			// No exclusion list, so we're good
			if (excludePositions.isEmpty()) {
				return true;
			}
			for (int i = restartPos; i < excludePositions.size(); i++) {
				Position position = excludePositions.get(i);
				// If current exclusion is before current match,
				// we have to continue our checks
				if (position.getOffset() + position.getLength() <= m.start()) {
					// No further exclusions, we are good, current match is
					// pertinent
					if (i == excludePositions.size() - 1) {
						return true;
					}
					restartPos++;
					continue;
				}
				// Current exclusion is after current match, so we're also good
				if (position.getOffset() >= m.end()) {
					return true;
				}
				// Here we know that current exclusion overlaps current match,
				// so current match is non-pertinent, we have to continue with
				// next match.
				// Assert that current exclusion FULLY overlaps current match.
				assert position.getOffset() <= m.start() && m.end() <= position.getOffset() + position.getLength();
				break;
			}
		}
		// No pertinent match was found
		return false;
	}

	/**
	 * Returns the positions of a list of ASTNodes. It is important that the
	 * returned list is sorted by increasing position, or the other
	 * DocumentUtils methods (using this list) won't work correctly.
	 * 
	 * @param nodes
	 *            Node positions to exclude
	 * @return sorted positions to exclude
	 */
	public static List<Position> getExcludeSortedAndFilteredPositions(ASTNode[] nodes) {
		List<Position> excludePositions = new ArrayList<Position>();
		for (ASTNode n : nodes) {
			if (n instanceof PHPDocBlock) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=490434
				// Do not handle PHPDoc comments for now
				continue;
			}
			excludePositions.add(new Position(n.sourceStart(), n.sourceEnd() - n.sourceStart()));
		}
		return excludePositions;
	}

	/**
	 * Remove all the given use statements from a document. Be warned that the
	 * "excludePositions" list will be updated to match the content of the
	 * returned string. It is important that this list is sorted by increasing
	 * position and that the statement positions don't intersect with any of the
	 * exclude positions, or this method won't work correctly.
	 */
	public static String stripUseStatements(UseStatement[] statements, IDocument old_doc,
			List<Position> excludePositions) {
		return stripUseStatements(statements, old_doc, 0, old_doc.getLength(), excludePositions);
	}

	/**
	 * Remove all the given use statements from a document. Be warned that the
	 * "excludePositions" list will be updated to match the content of the
	 * returned string. It is important that this list is sorted by increasing
	 * position and that the statement positions don't intersect with any of the
	 * exclude positions, or this method won't work correctly.
	 */
	public static String stripUseStatements(UseStatement[] statements, IDocument old_doc, int start, int end,
			List<Position> excludePositions) {
		int removedLength = 0;
		IDocument doc = new Document(old_doc.get());

		for (UseStatement statement : statements) {
			if (statement.sourceStart() < start || statement.sourceEnd() > end) {
				continue;
			}
			int length = statement.sourceEnd() - statement.sourceStart();

			try {
				doc.replace(statement.sourceStart() - removedLength, length, "");
			} catch (BadLocationException e) {
			}

			for (int i = excludePositions.size() - 1; i >= 0; i--) {
				Position position = excludePositions.get(i);
				int offset = position.getOffset() + removedLength;
				if (offset >= statement.sourceEnd()) {
					position.setOffset(position.getOffset() - length);
				} else if (offset + position.getLength() > statement.sourceStart()) {
					// statement positions should never intersect with exclude
					// positions, so (for safety) remove them from the exclusion
					// list
					excludePositions.remove(i);
				} else {
					break;
				}
			}

			removedLength += length;
		}

		try {
			return doc.get(start, end - start - removedLength);
		} catch (BadLocationException e) {
			return doc.get();
		}
	}

	/**
	 * Create a string from the given UseStatements
	 */
	public static String createStringFromUseStatement(List<UseStatement> statements, String indent) {
		StringBuilder total = new StringBuilder();

		for (UseStatement statement : statements) {
			total.append(createStringFromUseStatement(statement, indent));
		}

		return total.toString().trim();
	}

	public static String createStringFromUseStatement(UseStatement statement) {
		return createStringFromUseStatement(statement, "");
	}

	public static String createStringFromUseStatement(UseStatement statement, boolean addIndentedUsePrefixAndNewline) {
		return createStringFromUseStatement(statement, "", addIndentedUsePrefixAndNewline);
	}

	public static String createStringFromUseStatement(UseStatement statement, String indent) {
		return createStringFromUseStatement(statement, indent, true);
	}

	/**
	 * Create a string from the given UseStatement
	 */
	public static String createStringFromUseStatement(UseStatement statement, String indent,
			boolean addIndentedUsePrefixAndNewline) {
		String use = "";
		if (addIndentedUsePrefixAndNewline) {
			use = indent + "use ";

			switch (statement.getStatementType()) {
			case UseStatement.T_NONE:
				break;
			case UseStatement.T_FUNCTION:
				use += "function ";
				break;
			case UseStatement.T_CONST:
				use += "const ";
				break;
			}
		}

		boolean first = true;
		for (UsePart part : statement.getParts()) {
			if (!first) {
				use += ", ";
			}
			use += part.getNamespace().getFullyQualifiedName();
			if (part.getAlias() != null) {
				use += " as " + part.getAlias().getName();
			}
			first = false;
		}

		if (addIndentedUsePrefixAndNewline) {
			return use + ";\n";
		}
		return use;
	}

	/**
	 * Filter and sort a list of Use statements from a document
	 */
	public static List<UseStatement> filterAndSort(UseStatement[] statements, IDocument doc,
			ModuleDeclaration moduleDeclaration) {
		Vector<UseStatement> total = new Vector<UseStatement>();

		NamespaceFinder visitor = new NamespaceFinder();
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e1) {
		}

		for (UseStatement statement : statements) {
			List<Position> excludePositions;
			if (moduleDeclaration instanceof PHPModuleDeclaration) {
				excludePositions = getExcludeSortedAndFilteredPositions(
						((PHPModuleDeclaration) moduleDeclaration).getCommentList().toArray(new ASTNode[0]));
			} else {
				excludePositions = new ArrayList<Position>();
			}
			String contents;
			NamespaceDeclaration currentNamespace = visitor.getNamespaceDeclarationFor(statement);
			if (currentNamespace != null && currentNamespace.isBracketed()) {
				contents = stripUseStatements(statements, doc, currentNamespace.sourceStart(),
						currentNamespace.sourceEnd(), excludePositions);
			} else {
				contents = stripUseStatements(statements, doc, excludePositions);
			}

			Vector<UsePart> parts = new Vector<UsePart>();
			for (UsePart part : statement.getParts()) {
				if (containsUseStatement(part, contents, excludePositions)) {
					parts.add(part);
				}
			}

			if (parts.size() > 0) {
				total.add(new UseStatement(statement.start(), statement.end(), parts, statement.getStatementType()));
			}
		}

		// sort and remove duplicate UseStatements
		Set<UseStatement> set = new TreeSet<>(new Comparator<UseStatement>() {
			@Override
			public int compare(UseStatement a, UseStatement b) {
				if (a == b) {
					return 0;
				}
				if (a.getStatementType() != b.getStatementType()) {
					return a.getStatementType() - b.getStatementType();
				}

				String partA = createStringFromUseStatement(a, false).toLowerCase();
				String partB = createStringFromUseStatement(b, false).toLowerCase();
				String[] partsA = partA.split("\\\\");
				String[] partsB = partB.split("\\\\");

				int checkLength = Math.min(partsA.length, partsB.length);
				for (int i = 0; i < checkLength; i++) {
					int comp = partsA[i].compareTo(partsB[i]);

					if (comp != 0) {
						return comp;
					}
				}

				return partsA.length - partsB.length;
			}
		});
		set.addAll(total);

		return new ArrayList<UseStatement>(set);
	}

	/**
	 * Sort the blocks of use statements from a document
	 */
	public static void sortUseStatements(ModuleDeclaration moduleDeclaration, IDocument doc) {
		UseStatement[] statements = ASTUtils.getUseStatements(moduleDeclaration, doc.getLength());

		int start = 0;
		Vector<ReplaceAction> queue = new Vector<ReplaceAction>();

		while (start < statements.length) {
			int last_item = statements.length - 1;

			for (int i = start; i < statements.length - 1; i++) {
				try {
					if (doc.getLineOfOffset(
							statements[i + 1].sourceStart()) > doc.getLineOfOffset(statements[i].sourceStart()) + 1) {
						last_item = i;
						break;
					}
				} catch (BadLocationException e) {
					last_item = i;
					break;
				}
			}

			String indent = "";
			try {
				int lineOffset = doc.getLineOffset(doc.getLineOfOffset(statements[start].sourceStart()));

				indent = doc.get(lineOffset, statements[start].sourceStart() - lineOffset);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

			queue.add(new ReplaceAction(
					filterAndSort(Arrays.copyOfRange(statements, start, last_item + 1), doc, moduleDeclaration),
					statements[start].sourceStart(), statements[last_item].sourceEnd(), indent));

			start = last_item + 1; // start at the next one
		}

		int offset = 0;
		for (ReplaceAction item : queue) {
			List<UseStatement> sorted = item.statements;
			int length = item.end - item.start;

			String newNamespaces = createStringFromUseStatement(sorted, item.indent);
			try {
				doc.replace(item.start - offset, length, newNamespaces);
			} catch (BadLocationException e) {
			}

			offset += length - newNamespaces.length();
		}
	}
}
