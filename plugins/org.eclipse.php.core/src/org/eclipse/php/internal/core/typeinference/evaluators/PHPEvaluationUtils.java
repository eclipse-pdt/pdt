/*******************************************************************************
 * Copyright (c) 2014, 2015, 2016 IBM Corporation and others.
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

	public static final Pattern ARRAY_TYPE_PATTERN = Pattern.compile("array\\[.*\\]"); //$NON-NLS-1$

	private static final String SELF_RETURN_TYPE = "self"; //$NON-NLS-1$

	private static final String STATIC_RETURN_TYPE = "static"; //$NON-NLS-1$

	private static final String THIS_RETURN_TYPE = "$this"; //$NON-NLS-1$

	public static final String BRACKETS_PATTERN = "\\[.*\\]"; //$NON-NLS-1$

	private static final IEvaluatedType[] EMPTY_LIST = new IEvaluatedType[0];

	// XXX: handle nested array[] types?
	public static String extractArrayType(String typeName) {
		Matcher m = PHPEvaluationUtils.ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find()) {
			int beginIndex = typeName.indexOf('[') + 1;
			int endIndex = typeName.lastIndexOf(']');
			if (endIndex != -1) {
				return typeName.substring(beginIndex, endIndex);
			}
		}
		return removeArrayBrackets(typeName);
	}

	public static boolean isArrayType(String typeName) {
		if (typeName == null || typeName.isEmpty()) {
			return false;
		}
		Matcher m = PHPEvaluationUtils.ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find() || (typeName.endsWith(PHPEvaluationUtils.BRACKETS) && typeName.length() > 2)) {
			return true;
		}
		return false;
	}

	public static IEvaluatedType extractArrayType(String typeName, IType currentNamespace, int offset) {
		if (typeName == null || typeName.isEmpty()) {
			return null;
		}
		Matcher m = PHPEvaluationUtils.ARRAY_TYPE_PATTERN.matcher(typeName);
		if (m.find()) {
			return PHPEvaluationUtils.getArrayType(m.group(), currentNamespace, offset);
		} else if (typeName.endsWith(PHPEvaluationUtils.BRACKETS) && typeName.length() > 2) {
			return PHPEvaluationUtils.getArrayType(typeName.substring(0, typeName.length() - 2), currentNamespace,
					offset);
		}
		return null;
	}

	public static MultiTypeType getArrayType(String type, IType currentNamespace, int offset) {
		int beginIndex = type.indexOf('[') + 1;
		int endIndex = type.lastIndexOf(']');
		if (endIndex != -1) {
			type = type.substring(beginIndex, endIndex);
		}
		MultiTypeType arrayType = new MultiTypeType();
		Matcher m = ARRAY_TYPE_PATTERN.matcher(type);
		if (m.find()) {
			arrayType.addType(getArrayType(m.group(), currentNamespace, offset));
			type = m.replaceAll(""); //$NON-NLS-1$
		} else if (type.endsWith(BRACKETS) && type.length() > 2) {
			arrayType.addType(getArrayType(type.substring(0, type.length() - 2), currentNamespace, offset));
			type = type.replaceAll(Pattern.quote(BRACKETS), ""); //$NON-NLS-1$
		}
		String[] typeNames = type.split(","); //$NON-NLS-1$
		for (String name : typeNames) {
			if (!"".equals(name)) { //$NON-NLS-1$
				int nsSeparatorIndex = name.indexOf(NamespaceReference.NAMESPACE_SEPARATOR);
				if (currentNamespace != null && (nsSeparatorIndex < 0 || nsSeparatorIndex > 0)) {
					// check if the first part is an alias, then get the full
					// name
					// NB: do as in method
					// PDTModelUtils#collectParameterTypes(IMethod method)
					ModuleDeclaration moduleDeclaration = SourceParserUtil
							.getModuleDeclaration(currentNamespace.getSourceModule());
					String prefix = name;
					if (nsSeparatorIndex > 0) {
						prefix = name.substring(0, nsSeparatorIndex);
					}
					final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration, offset,
							currentNamespace, true);
					if (result.containsKey(prefix)) {
						String fullName = result.get(prefix).getNamespace().getFullyQualifiedName();
						name = name.replace(prefix, fullName);
						if (name.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
							name = NamespaceReference.NAMESPACE_SEPARATOR + name;
						}
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
	public static Collection<String> getTypeBinding(String name, PHPDocTag docTag) {
		String[] split = MagicMemberUtil.WHITESPACE_SEPERATOR.split(docTag.getValue().trim());
		if (split.length < 2) {
			return Collections.emptyList();
		}
		if (split[1].equals(name)) {
			if (Constants.STATIC.equals(split[0])) {
				return Collections.emptyList();
			}
			return Arrays.asList(split[0].split("\\" //$NON-NLS-1$
					+ Constants.TYPE_SEPERATOR_CHAR));
		}
		if (Constants.STATIC.equals(split[0])) {
			split = Arrays.copyOfRange(split, 1, split.length);
			if (split.length < 2) {
				return Collections.emptyList();
			}
		}

		String substring = split[1];
		int parenIndex = split[1].indexOf('('); // $NON-NLS-1$
		if (parenIndex != -1) {
			substring = substring.substring(0, parenIndex);
		}
		if (substring.equals(name)) {
			return Arrays.asList(split[0].split("\\" //$NON-NLS-1$
					+ Constants.TYPE_SEPERATOR_CHAR));
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
	public static IEvaluatedType getEvaluatedType(String typeName, IType currentNamespace) {
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

	public static String removeArrayBrackets(String variableName) {
		return variableName.replaceAll(BRACKETS_PATTERN, "");
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
	 * @param types
	 * @return
	 */
	public static IEvaluatedType[] evaluatePHPDocType(String[] typeNames, IModelElement space, int offset,
			IType[] types) {
		ISourceModule sourceModule = space.getAncestor(ISourceModule.class);
		IType currentNamespace = space instanceof IType ? (IType) space : null;
		List<IEvaluatedType> res = new LinkedList<>();
		for (String typeName : typeNames) {
			List<IEvaluatedType> evaluated = new LinkedList<>();
			if (StringUtils.isBlank(typeName)) {
				continue;
			}
			IEvaluatedType evaluatedType = PHPEvaluationUtils.extractArrayType(typeName, currentNamespace, offset);
			if (evaluatedType != null) {
				evaluated.add(evaluatedType);
			} else {
				if (PHPSimpleTypes.isSimpleTypeCS(typeName)) {
					ClassFinder classFinder = new ClassFinder(typeName);
					try {
						space.accept(classFinder);
					} catch (ModelException e) {
						Logger.logException(e);
					}
					if (classFinder.found) {
						evaluated.add(PHPEvaluationUtils.getEvaluatedType(typeName, currentNamespace));
					} else {
						evaluated.add(PHPSimpleTypes.fromStringCS(typeName));
					}
				} else if ((typeName.equals(SELF_RETURN_TYPE) || typeName.equals(THIS_RETURN_TYPE)
						|| typeName.equals(STATIC_RETURN_TYPE)) && types != null) {
					for (IType t : types) {
						IEvaluatedType type = PHPEvaluationUtils.getEvaluatedType(PHPModelUtils.getFullName(t), null);
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
							String prefix = typeName.substring(0,
									typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR));
							final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration,
									offset, currentNamespace, true);
							if (result.containsKey(prefix)) {
								String fullName = result.get(prefix).getNamespace().getFullyQualifiedName();
								typeName = typeName.replace(prefix, fullName);
								if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
									typeName = NamespaceReference.NAMESPACE_SEPARATOR + typeName;
								}
							}
						} else if (typeName.indexOf(NamespaceReference.NAMESPACE_SEPARATOR) < 0) {

							String prefix = typeName;
							final Map<String, UsePart> result = PHPModelUtils.getAliasToNSMap(prefix, moduleDeclaration,
									offset, currentNamespace, true);
							if (result.containsKey(prefix)) {
								String fullName = result.get(prefix).getNamespace().getFullyQualifiedName();
								typeName = fullName;
								if (typeName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
									typeName = NamespaceReference.NAMESPACE_SEPARATOR + typeName;
								}
							}
						}
					}
					IEvaluatedType type = PHPEvaluationUtils.getEvaluatedType(typeName, currentNamespace);
					if (type != null) {
						evaluated.add(type);
					}
				}
			}
			res.addAll(evaluated);
		}
		if (res.isEmpty()) {
			return EMPTY_LIST;
		}

		return res.toArray(new IEvaluatedType[res.size()]);
	}

	public static IEvaluatedType[] evaluatePHPDocType(List<TypeReference> typeNames, IModelElement space, int offset,
			IType[] types) {
		if (typeNames == null || typeNames.isEmpty()) {
			return EMPTY_LIST;
		}
		String[] tmp = new String[typeNames.size()];
		for (int i = 0; i < typeNames.size(); i++) {
			tmp[i] = typeNames.get(i).getName();
		}
		return evaluatePHPDocType(tmp, space, offset, types);
	}

}