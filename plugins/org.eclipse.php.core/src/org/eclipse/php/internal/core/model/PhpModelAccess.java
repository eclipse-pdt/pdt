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
package org.eclipse.php.internal.core.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.php.core.compiler.IPHPModifiers;

public class PhpModelAccess extends ModelAccess {
	public static final IType[] NULL_TYPES = new IType[0];
	public static final IMethod[] NULL_METHODS = new IMethod[0];
	public static final IField[] NULL_FIELDS = new IField[0];
	private static final PhpModelAccess instance = new PhpModelAccess();

	public static PhpModelAccess getDefault() {
		return instance;
	}

	@Override
	public IField[] findFields(String name, MatchRule matchRule, int trueFlags,
			int falseFlags, IDLTKSearchScope scope, IProgressMonitor monitor) {
		IField[] result = super.findFields(name, matchRule, trueFlags,
				falseFlags, scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_FIELDS;
		}
		return result;
	}

	@Override
	public IField[] findFields(String qualifier, String name,
			MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IField[] result = super.findFields(qualifier, name, matchRule,
				trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_FIELDS;
		}
		return result;
	}

	@Override
	public IMethod[] findMethods(String name, MatchRule matchRule,
			int trueFlags, int falseFlags, IDLTKSearchScope scope,
			IProgressMonitor monitor) {
		IMethod[] result = super.findMethods(name, matchRule, trueFlags,
				falseFlags, scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_METHODS;
		}
		return result;
	}

	@Override
	public IMethod[] findMethods(String qualifier, String name,
			MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IMethod[] result = super.findMethods(qualifier, name, matchRule,
				trueFlags, falseFlags, scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_METHODS;
		}
		return result;
	}

	@Override
	public IType[] findTypes(String name, MatchRule matchRule, int trueFlags,
			int falseFlags, IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(name, matchRule, trueFlags, falseFlags
				| IPHPModifiers.AccTrait, scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_TYPES;
		}
		return result;
	}

	@Override
	public IType[] findTypes(String qualifier, String name,
			MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(qualifier, name, matchRule, trueFlags,
				falseFlags | IPHPModifiers.AccTrait, scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_TYPES;
		}
		return result;
	}

	public IType[] findTraits(String name, MatchRule matchRule, int trueFlags,
			int falseFlags, IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(name, matchRule, trueFlags
				| IPHPModifiers.AccTrait, falseFlags
				| IPHPModifiers.AccInterface | IPHPModifiers.AccNameSpace,
				scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_TYPES;
		}
		return result;
	}

	public IType[] findTraits(String qualifier, String name,
			MatchRule matchRule, int trueFlags, int falseFlags,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		IType[] result = super.findTypes(qualifier, name, matchRule, trueFlags
				| IPHPModifiers.AccTrait, falseFlags
				| IPHPModifiers.AccInterface | IPHPModifiers.AccNameSpace,
				scope, monitor);
		if (result == null) {
			result = PhpModelAccess.NULL_TYPES;
		}
		return result;
	}

	public IField[] findIncludes(String name, MatchRule matchRule,
			IDLTKSearchScope scope, IProgressMonitor monitor) {
		List<IField> result = new LinkedList<IField>();
		if (!findElements(IModelElement.IMPORT_DECLARATION, name, matchRule, 0,
				0, scope, result, monitor)) {
			return PhpModelAccess.NULL_FIELDS;
		}
		return (IField[]) result.toArray(new IField[result.size()]);
	}
}
