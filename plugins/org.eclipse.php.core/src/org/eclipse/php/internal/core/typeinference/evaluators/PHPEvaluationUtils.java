/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public class PHPEvaluationUtils {

	public static final String BRACKETS = "[]"; //$NON-NLS-1$

	public final static Pattern ARRAY_TYPE_PATTERN = Pattern
			.compile("array\\[.*\\]"); //$NON-NLS-1$

	public static final String BRACKETS_PATTERN = "\\[.*\\]";

	public static String extractArrayType(String typeName) {
		Matcher m = PHPEvaluationUtils.ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find()) {
			int beginIndex = typeName.indexOf("[") + 1; //$NON-NLS-1$
			int endIndex = typeName.lastIndexOf("]"); //$NON-NLS-1$
			if (endIndex != -1) {
				return typeName.substring(beginIndex, endIndex);
			}
		}
		return removeArrayBrackets(typeName);
	}

	public static IEvaluatedType extractArrayType(String typeName,
			IType currentNamespace, int offset) {
		if (typeName == null || typeName.isEmpty()) {
			return null;
		}
		Matcher m = PHPEvaluationUtils.ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find()) {
			return PHPEvaluationUtils.getArrayType(m.group(), currentNamespace,
					offset);
		} else if (typeName.endsWith(PHPEvaluationUtils.BRACKETS)
				&& typeName.length() > 2) {
			return PHPEvaluationUtils.getArrayType(
					typeName.substring(0, typeName.length() - 2),
					currentNamespace, offset);
		}
		return null;
	}

	public static MultiTypeType getArrayType(String type,
			IType currentNamespace, int offset) {
		int beginIndex = type.indexOf("[") + 1; //$NON-NLS-1$
		int endIndex = type.lastIndexOf("]"); //$NON-NLS-1$
		if (endIndex != -1) {
			type = type.substring(beginIndex, endIndex);
		}
		MultiTypeType arrayType = new MultiTypeType();
		Matcher m = ARRAY_TYPE_PATTERN.matcher(type);
		if (m.find()) {
			arrayType
					.addType(getArrayType(m.group(), currentNamespace, offset));
			type = m.replaceAll(""); //$NON-NLS-1$
		} else if (type.endsWith(BRACKETS) && type.length() > 2) {
			arrayType.addType(getArrayType(
					type.substring(0, type.length() - 2), currentNamespace,
					offset));
			type = type.replaceAll(BRACKETS, ""); //$NON-NLS-1$
		}
		String[] typeNames = type.split(","); //$NON-NLS-1$
		for (String name : typeNames) {
			if (!"".equals(name)) { //$NON-NLS-1$
				int nsSeparatorIndex = name
						.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
				if (currentNamespace != null) {
					// check if the first part is an
					// alias,then get the full name
					ModuleDeclaration moduleDeclaration = SourceParserUtil
							.getModuleDeclaration(currentNamespace
									.getSourceModule());
					String prefix = name;
					if (nsSeparatorIndex != -1) {
						name.substring(
								0,
								name.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
					}
					final Map<String, UsePart> result = PHPModelUtils
							.getAliasToNSMap(prefix, moduleDeclaration, offset,
									currentNamespace, true);
					if (result.containsKey(prefix)) {
						String fullName = result.get(prefix).getNamespace()
								.getFullyQualifiedName();
						name = name.replace(prefix, fullName);
						if (name.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
							name = NamespaceReference.NAMESPACE_SEPARATOR
									+ name;
						}
					} else {

					}
				}
				arrayType.addType(getEvaluatedType(name, currentNamespace));
			}
		}
		return arrayType;
	}

	/**
	 * Resolves the type strings e.g from the @property and @method tag.
	 * 
	 * e.g DateTime|DateTimeZone
	 * 
	 * @param variableName
	 * @param docTag
	 * @return the types of the given variable
	 */
	public static Collection<String> getTypeBinding(String name,
			PHPDocTag docTag) {
		String[] split = docTag.getValue().trim().split("\\s+"); //$NON-NLS-1$
		if (split.length < 2) {
			return null;
		}
		if (split[1].equals(name)) {
			if (Constants.STATIC.equals(split[0])) {
				return Collections.emptyList();
			}
			return Arrays.asList(split[0].split("\\|")); //$NON-NLS-1$
		}
		if (Constants.STATIC.equals(split[0])) {
			split = Arrays.copyOfRange(split, 1, split.length);
			if (split.length < 2) {
				return Collections.emptyList();
			}
		}

		String substring = split[1];
		int parenIndex = split[1].indexOf('('); //$NON-NLS-1$
		if (parenIndex != -1) {
			substring = substring.substring(0, parenIndex);
		}
		if (substring.equals(name)) {
			return Arrays.asList(split[0].split("\\|")); //$NON-NLS-1$
		}
		return Collections.emptyList();
	}

	/**
	 * Creates evaluated type according name and namespace.
	 * 
	 * @param typeName
	 * @param currentNamespace
	 * @return evaluated type
	 */
	public static IEvaluatedType getEvaluatedType(String typeName,
			IType currentNamespace) {
		if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0
				&& currentNamespace != null) {
			typeName = NamespaceReference.NAMESPACE_SEPARATOR
					+ currentNamespace.getElementName()
					+ NamespaceReference.NAMESPACE_SEPARATOR + typeName;
		}
		if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1
				|| currentNamespace == null) {
			return new PHPClassType(typeName);
		} else {
			return new PHPClassType(currentNamespace.getElementName(), typeName);
		}
	}

	public static String removeArrayBrackets(String variableName) {
		return variableName.replaceAll(BRACKETS_PATTERN, "");
	}

}
