/*******************************************************************************
 * Copyright (c) 2014, 2015, 2016, 2017 IBM Corporation and others.
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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.evaluation.types.MultiTypeType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.core.compiler.ast.nodes.UsePart;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.util.MagicMemberUtil;

public class PHPEvaluationUtils {

	public static final String BRACKETS = "[]"; //$NON-NLS-1$

	public static final Pattern ARRAY_TYPE_PATTERN = Pattern.compile("array\\[.*\\]", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	private static final String SELF_RETURN_TYPE = "self"; //$NON-NLS-1$

	private static final String STATIC_RETURN_TYPE = "static"; //$NON-NLS-1$

	private static final String THIS_RETURN_TYPE = "$this"; //$NON-NLS-1$

	public static final String BRACKETS_REGEX = "\\[.*\\]"; //$NON-NLS-1$

	// Matches all type separators used by method getArrayType()
	public static final Pattern TYPE_DELIMS_PATTERN = Pattern.compile("([,\\[\\]]+)"); //$NON-NLS-1$

	private static final IEvaluatedType[] EMPTY_LIST = new IEvaluatedType[0];

	// XXX: handle nested array[] types?
	public static String extractArrayType(@NonNull String typeName) {
		Matcher m = ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find()) {
			int beginIndex = typeName.indexOf('[') + 1;
			int endIndex = typeName.lastIndexOf(']');
			if (endIndex != -1) {
				return typeName.substring(beginIndex, endIndex);
			}
		}
		return removeArrayBrackets(typeName);
	}

	public static boolean isArrayType(@Nullable String typeName) {
		if (typeName == null || typeName.isEmpty()) {
			return false;
		}
		Matcher m = ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find() || (typeName.endsWith(BRACKETS) && typeName.length() > 2)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("null")
	public static IEvaluatedType extractArrayType(@Nullable String typeName, @NonNull IModelElement space, int offset,
			@Nullable IType[] selfTypes) {
		if (typeName == null || typeName.isEmpty()) {
			return null;
		}
		Matcher m = ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find()) {
			return getArrayType(m.group(), space, offset, selfTypes);
		} else if (typeName.endsWith(BRACKETS) && typeName.length() > 2) {
			return getArrayType(typeName.substring(0, typeName.length() - 2), space, offset, selfTypes);
		}
		return null;
	}

	@SuppressWarnings("null")
	public static MultiTypeType getArrayType(@NonNull String type, @NonNull IModelElement space, int offset,
			@Nullable IType[] selfTypes) {
		int beginIndex = type.indexOf('[') + 1;
		int endIndex = type.lastIndexOf(']');
		if (endIndex != -1) {
			type = type.substring(beginIndex, endIndex);
		}
		MultiTypeType arrayType = new MultiTypeType();
		Matcher m = ARRAY_TYPE_PATTERN.matcher(type);
		if (m.find()) {
			arrayType.addType(getArrayType(m.group(), space, offset, selfTypes));
			type = m.replaceAll(""); //$NON-NLS-1$
		} else if (type.endsWith(BRACKETS) && type.length() > 2) {
			arrayType.addType(getArrayType(type.substring(0, type.length() - 2), space, offset, selfTypes));
			type = type.replaceAll(Pattern.quote(BRACKETS), ""); //$NON-NLS-1$
		}
		if (!type.isEmpty()) {
			for (IEvaluatedType e : evaluateSinglePHPDocType(type, space, offset, selfTypes)) {
				arrayType.addType(e);
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
	public static Collection<String> getTypeBinding(@NonNull String name, @NonNull PHPDocTag docTag) {
		String[] split = MagicMemberUtil.WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
		if (split.length < 2) {
			return Collections.emptyList();
		}
		if (split[1].equals(name)) {
			if (Constants.STATIC.equals(split[0])) {
				return Collections.emptyList();
			}
			return Arrays.asList(split[0].split("\\" //$NON-NLS-1$
					+ Constants.TYPE_SEPARATOR_CHAR));
		}
		if (Constants.STATIC.equals(split[0])) {
			split = Arrays.copyOfRange(split, 1, split.length);
			if (split.length < 2) {
				return Collections.emptyList();
			}
		}

		String substring = split[1];
		int parenIndex = split[1].indexOf('(');
		if (parenIndex != -1) {
			substring = substring.substring(0, parenIndex);
		}
		if (substring.equals(name)) {
			return Arrays.asList(split[0].split("\\" //$NON-NLS-1$
					+ Constants.TYPE_SEPARATOR_CHAR));
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
	@NonNull
	public static IEvaluatedType getEvaluatedType(@NonNull String typeName, @Nullable IType currentNamespace) {
		if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0 && currentNamespace != null) {
			typeName = NamespaceReference.NAMESPACE_SEPARATOR + currentNamespace.getElementName()
					+ NamespaceReference.NAMESPACE_SEPARATOR + typeName;
		}
		if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) != -1 || currentNamespace == null) {
			return new PHPClassType(typeName);
		} else {
			return new PHPClassType(currentNamespace.getElementName(), typeName);
		}
	}

	public static String removeArrayBrackets(@NonNull String variableName) {
		return variableName.replaceAll(BRACKETS_REGEX, ""); //$NON-NLS-1$
	}

	private static class ClassFinder implements IModelElementVisitor {
		private final String search;
		public boolean found = false;

		public ClassFinder(String name) {
			search = name;
		}

		@Override
		public boolean visit(IModelElement element) {
			if (element.getElementType() == IModelElement.TYPE && search.equals(element.getElementName())) {
				found = true;
			}

			return !found;
		}
	}

	/**
	 * @param typeName
	 * @param space
	 *            namespace (IType) or file (ISourceModule)
	 * @param offset
	 * @param selfTypes
	 * @return
	 */
	@SuppressWarnings("null")
	public static IEvaluatedType[] evaluatePHPDocType(@NonNull String[] typeNames, @NonNull IModelElement space,
			int offset, @Nullable IType[] selfTypes) {
		List<IEvaluatedType> res = new LinkedList<>();
		for (String typeName : typeNames) {
			if (StringUtils.isBlank(typeName)) {
				continue;
			}
			IEvaluatedType evaluatedType = extractArrayType(typeName, space, offset, selfTypes);
			if (evaluatedType != null) {
				res.add(evaluatedType);
			} else {
				res.addAll(evaluateSinglePHPDocType(typeName, space, offset, selfTypes));
			}
		}
		if (res.isEmpty()) {
			return EMPTY_LIST;
		}

		return res.toArray(new IEvaluatedType[res.size()]);
	}

	@SuppressWarnings("null")
	private static List<IEvaluatedType> evaluateSinglePHPDocType(@NonNull String typeName, @NonNull IModelElement space,
			int offset, @Nullable IType[] selfTypes) {
		IType currentNamespace = space instanceof IType ? (IType) space : null;
		ISourceModule sourceModule = space.getAncestor(ISourceModule.class);

		List<IEvaluatedType> evaluated = new LinkedList<>();
		if (PHPSimpleTypes.isSimpleType(typeName)) {
			ClassFinder classFinder = new ClassFinder(typeName);
			try {
				space.accept(classFinder);
			} catch (ModelException e) {
				Logger.logException(e);
			}
			if (classFinder.found) {
				evaluated.add(getEvaluatedType(typeName, currentNamespace));
			} else {
				evaluated.add(PHPSimpleTypes.fromString(typeName));
			}
		} else if ((typeName.equals(SELF_RETURN_TYPE) || typeName.equals(THIS_RETURN_TYPE)
				|| typeName.equals(STATIC_RETURN_TYPE)) && selfTypes != null) {
			for (IType t : selfTypes) {
				IEvaluatedType type = getEvaluatedType(PHPModelUtils.getFullName(t), null);
				if (type != null) {
					evaluated.add(type);
				}
			}
		} else if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) == 0) {
			evaluated.add(new PHPClassType(typeName));
		} else {
			if (currentNamespace != null) {
				ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule);
				if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) > 0) {
					String prefix = typeName.substring(0, typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
					final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration, offset,
							currentNamespace, true);
					if (result.containsKey(prefix)) {
						String fullName = result.get(prefix).getNamespace().getFullyQualifiedName();
						typeName = typeName.replace(prefix, fullName);
						if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
							typeName = NamespaceReference.NAMESPACE_SEPARATOR + typeName;
						}
					}
				} else if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {

					String prefix = typeName;
					final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration, offset,
							currentNamespace, true);
					if (result.containsKey(prefix)) {
						String fullName = result.get(prefix).getNamespace().getFullyQualifiedName();
						typeName = fullName;
						if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
							typeName = NamespaceReference.NAMESPACE_SEPARATOR + typeName;
						}
					}
				}
			}
			evaluated.add(getEvaluatedType(typeName, currentNamespace));
		}

		return evaluated;
	}

	public static IEvaluatedType[] evaluatePHPDocType(@Nullable List<TypeReference> typeNames,
			@NonNull IModelElement space, int offset, @Nullable IType[] selfTypes) {
		if (typeNames == null || typeNames.isEmpty()) {
			return EMPTY_LIST;
		}
		String[] tmp = new String[typeNames.size()];
		for (int i = 0; i < typeNames.size(); i++) {
			tmp[i] = typeNames.get(i).getName();
		}
		return evaluatePHPDocType(tmp, space, offset, selfTypes);
	}

}