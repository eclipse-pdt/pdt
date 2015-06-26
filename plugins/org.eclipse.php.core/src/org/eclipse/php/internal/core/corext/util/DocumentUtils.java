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

import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.compiler.ast.nodes.FullyQualifiedReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.compiler.ast.nodes.UseStatement;

/**
 * @author Yannick de Lange <yannickl88@gmail.com>
 */
public class DocumentUtils {
	public static UseStatementBlock findUseStatmentsBlock(IDocument doc)
			throws BadLocationException {
		if (null == doc) {
			return null;
		}

		int lines = doc.getNumberOfLines(0, doc.getLength());
		int start = -1, end = -1, offset = -1, length = -1;
		Vector<UseStatement> statements = new Vector<UseStatement>();
		boolean seenEnd = true;
		String buffer = "";

		for (int i = 0; i < lines; i++) {
			offset = doc.getLineOffset(i);
			length = doc.getLineLength(i);
			String line = doc.get(offset, length).trim();

			if (line.length() == 0 || line.startsWith("<?php")
					|| line.startsWith("// ") || line.startsWith("/* ")
					|| line.startsWith("*")) {
				seenEnd = true;
				continue;
			}

			if (line.startsWith("namespace ") && line.contains(";")) {
				// is there a use-statement behind it?
				int endOfLine = line.indexOf(';') + 1;
				offset += endOfLine;
				length -= endOfLine;

				line = line.substring(endOfLine);

				if (line.trim().length() == 0) {
					seenEnd = true;
					continue;
				}
			}

			if (line.startsWith("use ")) {
				seenEnd = line.endsWith(";");

				if (seenEnd) {
					statements.add(
							getUseStatement(offset, offset + length, line));

				} else {
					buffer = line;
				}
				if (start == -1) {
					start = offset;
					end = offset + length;
				} else {
					end = offset + length;
				}
			} else if (!seenEnd) {
				buffer += line;
				seenEnd = line.endsWith(";");
				end = offset + length;

				if (seenEnd) {
					break;
				}
			} else {
				break;
			}
		}
		if (buffer.trim().length() > 0) {
			statements.add(
					getUseStatement(start, offset + length, buffer.trim()));
		}

		return new UseStatementBlock(start, end, statements);
	}

	private static UseStatement getUseStatement(int start, int end,
			String line) {
		String[] useLines = line.split(";");
		Vector<UsePart> useParts = new Vector<UsePart>();

		for (String useLine : useLines) {
			if (useLine.trim().length() == 0) {
				continue;
			}

			String[] parts = useLine.substring(4).split(",");

			for (String part : parts) {
				String[] subParts = part.split("\\s+as\\s+");
				ArrayList<String> namespace = new ArrayList<String>(
						Arrays.asList(subParts[0].trim().split("\\\\")));
				String className = namespace.remove(namespace.size() - 1);
				NamespaceReference namespaceRef = null;

				if (namespace.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (String n : namespace) {
						if (sb.length() > 0) {
							sb.append("\\");
						}
						sb.append(n);
					}
					namespaceRef = new NamespaceReference(0, 0,
							sb.toString().trim());
				}

				if (subParts.length == 2) {
					useParts.add(new UsePart(
							new FullyQualifiedReference(0, 0, className.trim(),
									namespaceRef),
							new SimpleReference(0, 0, subParts[1].trim())));
				} else {
					useParts.add(
							new UsePart(
									new FullyQualifiedReference(0, 0,
											className.trim(), namespaceRef),
									null));
				}
			}
		}

		return new UseStatement(start, end, useParts);
	}

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

	public static String createStringFromUseStatement(
			List<UseStatement> statements) {
		StringBuilder total = new StringBuilder();

		for (UseStatement statement : statements) {
			total.append(createStringFromUseStatement(statement));
		}

		return total.toString();
	}

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
