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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;

/**
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class DocumentUtils {
	public static List<UseStatement> flatten(List<UseStatement> statements) {
		Vector<UseStatement> total = new Vector<UseStatement>();

		for (UseStatement statement : statements) {
			for (UsePart part : statement.getParts()) {
				Vector<UsePart> parts = new Vector<UsePart>();
				parts.add(part);

				total.add(new UseStatement(statement.start(), statement.end(),
						parts));
			}
		}

		return total;
	}

	/**
	 * Check if a classname is used in a string
	 */
	public static boolean containsUseStatement(UsePart part, String contents) {
		String className = null != part.getAlias() ? part.getAlias().toString()
				: (part.getNamespace() != null ? part.getNamespace().toString()
						: "");

		return contents
				.matches("(?s).*\\b" + Pattern.quote(className) + "\\b.*");
	}

	/**
	 * Remove all the given use statements from a document
	 */
	public static String stripUseStatements(UseStatement[] statements,
			IDocument old_doc) {
		int offset = 0;
		IDocument doc = new Document(old_doc.get());

		for (UseStatement statement : statements) {
			int length = statement.sourceEnd() - statement.sourceStart();

			try {
				doc.replace(statement.sourceStart() - offset, length, "");
			} catch (BadLocationException e) {
			}

			offset += length;
		}

		return doc.get();
	}

	/**
	 * Create a string from the given UseStatements
	 */
	public static String createStringFromUseStatement(
			List<UseStatement> statements) {
		StringBuilder total = new StringBuilder();

		for (UseStatement statement : statements) {
			total.append(createStringFromUseStatement(statement));
		}

		return total.toString().trim();
	}

	/**
	 * Create a string from the given UseStatement
	 */
	public static String createStringFromUseStatement(UseStatement statement) {
		String use = "use ";
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

	public static List<UseStatement> filterAndSort(
			List<UseStatement> statements, String contents) {
		Vector<UseStatement> total = new Vector<UseStatement>();

		for (UseStatement statement : statements) {
			Vector<UsePart> parts = new Vector<UsePart>();
			for (UsePart part : statement.getParts()) {
				if (containsUseStatement(part, contents)) {
					parts.add(part);
				}
			}

			if (parts.size() > 0) {
				total.add(new UseStatement(statement.start(), statement.end(),
						parts));
			}
		}

		Collections.sort(total, new Comparator<UseStatement>() {
			@Override
			public int compare(UseStatement a, UseStatement b) {
				String partA = DocumentUtils.createStringFromUseStatement(a)
						.trim().toLowerCase();
				String partB = DocumentUtils.createStringFromUseStatement(b)
						.trim().toLowerCase();
				String[] partsA = partA.substring(4, partA.length() - 1)
						.split("\\\\");
				String[] partsB = partB.substring(4, partB.length() - 1)
						.split("\\\\");

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
}
