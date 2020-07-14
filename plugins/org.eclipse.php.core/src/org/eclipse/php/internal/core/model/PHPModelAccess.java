/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.php.core.compiler.IPHPModifiers;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCoreConstants;

public class PHPModelAccess extends ModelAccess {

	@NonNull
	public static final IType[] NULL_TYPES = new IType[0];
	@NonNull
	public static final IMethod[] NULL_METHODS = new IMethod[0];
	@NonNull
	public static final IField[] NULL_FIELDS = new IField[0];

	@NonNull
	private static final PHPModelAccess instance = new PHPModelAccess();

	@NonNull
	public static PHPModelAccess getDefault() {
		return instance;
	}

	@Override
	@NonNull
	public IField[] findFields(String name, MatchRule matchRule, int trueFlags, int falseFlags, IDLTKSearchScope scope,
			IProgressMonitor monitor) {
		IField[] result = super.findFields(name, matchRule, trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_FIELDS;
		}
		return result;
	}

	@Override
	@NonNull
	public IField[] findFields(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IField[] result = super.findFields(qualifier, name, matchRule, trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_FIELDS;
		}
		return result;
	}

	@Override
	@NonNull
	public IMethod[] findMethods(String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IMethod[] result = super.findMethods(name, matchRule, trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_METHODS;
		}
		return result;
	}

	@Override
	@NonNull
	public IMethod[] findMethods(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {

		IMethod[] result = super.findMethods(qualifier, name, matchRule, trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_METHODS;
		}
		return result;
	}

	protected <T extends IModelElement> boolean findFileElements(int elementType, String name, MatchRule matchRule,
			int trueFlags, int falseFlags, IDLTKSearchScope scope, final Collection<T> result,
			IProgressMonitor monitor) {
		String qualifier = null;
		if (name != null) {
			ISearchPatternProcessor processor = DLTKLanguageManager
					.getSearchPatternProcessor(scope.getLanguageToolkit());
			if (processor != null) {
				String delim = processor.getDelimiterReplacementString();
				int i = name.lastIndexOf(delim);
				if (i != -1) {
					qualifier = name.substring(0, i);
					name = name.substring(i + 1);
				}
			}
		}
		return findFileElements(elementType, qualifier, name, matchRule, trueFlags, falseFlags, scope, result, monitor);
	}

	protected <T extends IModelElement> boolean findFileElements(int elementType, String qualifier, String name,
			MatchRule matchRule, int trueFlags, int falseFlags, IDLTKSearchScope scope, final Collection<T> result,
			IProgressMonitor monitor) {
		return findElements(elementType, qualifier, name, PHPCoreConstants.FILE_PARENT, matchRule, trueFlags,
				falseFlags, scope, result, monitor);
	}

	@SuppressWarnings("null")
	@NonNull
	public IMethod[] findFunctions(String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IMethod> result = new LinkedList<>();
		if (!findFileElements(IModelElement.METHOD, name, matchRule, trueFlags, falseFlags, scope, result, monitor)) {
			return PHPModelAccess.NULL_METHODS;
		}

		return result.toArray(new IMethod[result.size()]);
	}

	@SuppressWarnings("null")
	@NonNull
	public IMethod[] findFunctions(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IMethod> result = new LinkedList<>();
		if (!findFileElements(IModelElement.METHOD, qualifier, name, matchRule, trueFlags, falseFlags, scope, result,
				monitor)) {
			return PHPModelAccess.NULL_METHODS;
		}

		return result.toArray(new IMethod[result.size()]);
	}

	@SuppressWarnings("null")
	@NonNull
	public IField[] findFileFields(String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IField> result = new LinkedList<>();
		if (!findFileElements(IModelElement.FIELD, name, matchRule, trueFlags, falseFlags, scope, result, monitor)) {
			return PHPModelAccess.NULL_FIELDS;
		}

		return result.toArray(new IField[result.size()]);
	}

	@SuppressWarnings("null")
	@NonNull
	public IField[] findFileFields(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IField> result = new LinkedList<>();
		if (!findFileElements(IModelElement.FIELD, qualifier, name, matchRule, trueFlags, falseFlags, scope, result,
				monitor)) {
			return PHPModelAccess.NULL_FIELDS;
		}

		return result.toArray(new IField[result.size()]);
	}

	@Override
	@NonNull
	public IType[] findTypes(String name, MatchRule matchRule, int trueFlags, int falseFlags, IDLTKSearchScope scope,
			IProgressMonitor monitor) {
		IType[] result = super.findTypes(name, matchRule, trueFlags, falseFlags | IPHPModifiers.AccTrait, scope,
				monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_TYPES;
		}
		return result;
	}

	@Override
	@NonNull
	public IType[] findTypes(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(qualifier, name, matchRule, trueFlags, falseFlags | IPHPModifiers.AccTrait,
				scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_TYPES;
		}
		return result;
	}

	@SuppressWarnings("null")
	@NonNull
	public IType[] findNamespaces(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IType> result = new LinkedList<>();
		if (!findElements(IModelElement.PACKAGE_DECLARATION, qualifier, name, matchRule, trueFlags, falseFlags, scope,
				result, monitor)) {
			return NULL_TYPES;
		}
		return result.toArray(new IType[result.size()]);
	}

	@NonNull
	public IType[] findTraits(String name, MatchRule matchRule, int trueFlags, int falseFlags, IDLTKSearchScope scope,
			IProgressMonitor monitor) {
		IType[] result = super.findTypes(name, matchRule, trueFlags | IPHPModifiers.AccTrait,
				falseFlags | IPHPModifiers.AccInterface | IPHPModifiers.AccNameSpace, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_TYPES;
		}
		return result;
	}

	@NonNull
	public IType[] findTraits(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(qualifier, name, matchRule, trueFlags | IPHPModifiers.AccTrait,
				falseFlags | IPHPModifiers.AccInterface | IPHPModifiers.AccNameSpace, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_TYPES;
		}
		return result;
	}

	@SuppressWarnings("null")
	@NonNull
	public IField[] findIncludes(String name, MatchRule matchRule, IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IField> result = new LinkedList<>();
		if (!findElements(IModelElement.IMPORT_DECLARATION, name, matchRule, 0, 0, scope, result, monitor)) {
			return PHPModelAccess.NULL_FIELDS;
		}
		return result.toArray(new IField[result.size()]);
	}

	/**
	 * @since 5.1
	 */
	@NonNull
	public IType[] findTraitOrTypes(String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(name, matchRule, trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_TYPES;
		}
		return result;
	}

	/**
	 * @since 5.1
	 */
	@NonNull
	public IType[] findTraitOrTypes(String qualifier, String name, MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(qualifier, name, matchRule, trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PHPModelAccess.NULL_TYPES;
		}
		return result;
	}

	public IField[] findConstants(String qualifier, String type, String name, MatchRule matchRule, int trueFlags,
			int falseFlags, IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IField> result = new LinkedList<>();
		if (type == null || type.length() == 0) {
			type = PHPCoreConstants.FILE_PARENT;
		}
		if (qualifier == null || qualifier.length() == 0) {
			qualifier = PHPCoreConstants.GLOBAL_NAMESPACE;
		}
		if (!findElements(IModelElement.FIELD, qualifier, name, type, matchRule, trueFlags | PHPFlags.AccConstant,
				falseFlags, scope, result, monitor)) {
			return PHPModelAccess.NULL_FIELDS;
		}

		return result.toArray(new IField[result.size()]);
	}

}
