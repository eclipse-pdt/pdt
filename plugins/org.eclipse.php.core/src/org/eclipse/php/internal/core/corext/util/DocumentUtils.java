/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceDeclaration;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

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

				total.add(new UseStatement(statement.start(), statement.end(), parts));
			}
		}

		return total;
	}

	/**
	 * Check if a classname is used in a string
	 */
	public static boolean containsUseStatement(UsePart part, String contents) {
		String className = null != part.getAlias() ? part.getAlias().toString()
				: (part.getNamespace() != null ? part.getNamespace().toString() : "");

		return contents.matches("(?i:(?s).*\\b" + Pattern.quote(className) + "\\b.*)");
	}

	public static String stripUseStatements(UseStatement[] statements, IDocument old_doc) {
		return stripUseStatements(statements, old_doc, 0, old_doc.getLength());
	}

	/**
	 * Remove all the given use statements from a document
	 */
	public static String stripUseStatements(UseStatement[] statements, IDocument old_doc, int start, int end) {
		int offset = 0;
		IDocument doc = new Document(old_doc.get());

		for (UseStatement statement : statements) {
			if (statement.sourceStart() < start || statement.sourceEnd() > end) {
				continue;
			}
			int length = statement.sourceEnd() - statement.sourceStart();

			try {
				doc.replace(statement.sourceStart() - offset, length, "");
			} catch (BadLocationException e) {
			}

			offset += length;
		}

		try {
			return doc.get(start, end - offset - start);
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

	/**
	 * Create a string from the given UseStatement
	 */
	public static String createStringFromUseStatement(UseStatement statement, String indent) {
		String use = indent + "use ";
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

		return use + ";\n";
	}

	/**
	 * Filter and sort a list of Use statements from a document
	 */
	public static List<UseStatement> filterAndSort(UseStatement[] statements, IDocument doc,
			ModuleDeclaration moduleDeclaration) {
		Vector<UseStatement> total = new Vector<UseStatement>();

		NamespaceFinder visitor = new DocumentUtils.NamespaceFinder();
		try {
			moduleDeclaration.traverse(visitor);
		} catch (Exception e1) {
		}

		for (UseStatement statement : statements) {
			String contents;
			NamespaceDeclaration currentNamespace = visitor.getNamespaceDeclarationFor(statement);
			if (currentNamespace != null && currentNamespace.isBracketed()) {
				contents = DocumentUtils.stripUseStatements(statements, doc, currentNamespace.sourceStart(),
						currentNamespace.sourceEnd());
			} else {
				contents = stripUseStatements(statements, doc);
			}

			Vector<UsePart> parts = new Vector<UsePart>();
			for (UsePart part : statement.getParts()) {
				if (containsUseStatement(part, contents)) {
					parts.add(part);
				}
			}

			if (parts.size() > 0) {
				total.add(new UseStatement(statement.start(), statement.end(), parts));
			}
		}

		Collections.sort(total, new Comparator<UseStatement>() {
			@Override
			public int compare(UseStatement a, UseStatement b) {
				String partA = DocumentUtils.createStringFromUseStatement(a).trim().toLowerCase();
				String partB = DocumentUtils.createStringFromUseStatement(b).trim().toLowerCase();
				String[] partsA = partA.substring(4, partA.length() - 1).split("\\\\");
				String[] partsB = partB.substring(4, partB.length() - 1).split("\\\\");

				int checkLength = Math.min(partsA.length, partsB.length);
				for (int i = 0; i < checkLength; i++) {
					int comp = partsA[i].compareTo(partsB[i]);

					if (comp != 0) {
						return comp;
					}
				}

				return partsA.length == partsB.length ? 1 : 0;
			}
		});

		return total;
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
					DocumentUtils.filterAndSort(Arrays.copyOfRange(statements, start, last_item + 1), doc,
							moduleDeclaration),
					statements[start].sourceStart(), statements[last_item].sourceEnd(), indent));

			start = last_item + 1; // start at the next one
		}

		int offset = 0;
		for (ReplaceAction item : queue) {
			List<UseStatement> sorted = item.statements;
			int length = item.end - item.start;

			String newNamespaces = DocumentUtils.createStringFromUseStatement(sorted, item.indent);
			try {
				doc.replace(item.start - offset, length, newNamespaces);
			} catch (BadLocationException e) {
			}

			offset += length - newNamespaces.length();
		}
	}
}
