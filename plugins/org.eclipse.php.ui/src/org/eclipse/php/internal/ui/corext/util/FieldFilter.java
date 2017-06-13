/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.corext.util;

import java.util.StringTokenizer;

import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.internal.ui.util.StringMatcher;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.internal.core.search.FieldNameMatch;

/**
 *
 */
public class FieldFilter {
	private IDLTKUILanguageToolkit fToolkit;

	public FieldFilter(IDLTKUILanguageToolkit toolkit) {
		this.fToolkit = toolkit;
	}

	public boolean isFiltered(String fullMethodName) {
		return filter(fullMethodName);
	}

	public boolean isFiltered(char[] fullMethodName) {
		return filter(new String(fullMethodName));
	}

	protected String concatenate(char[] packageName, char[] MethodName) {
		return new String(packageName) + " " + new String(MethodName); //$NON-NLS-1$
	}

	public boolean isFiltered(char[] packageName, char[] MethodName) {
		return filter(concatenate(packageName, MethodName));
	}

	public boolean isFiltered(IField field) {
		if (hasFilters()) {
			return filter(field.getFullyQualifiedName());
		}
		return false;
	}

	public boolean isFiltered(FieldNameMatch match) {
		return filter(match.getFullyQualifiedName());
	}

	private StringMatcher[] fStringMatchers;

	protected IPreferenceStore getPreferenceStore() {
		return this.fToolkit.getPreferenceStore();
	}

	public FieldFilter() {
		fStringMatchers = null;
	}

	private synchronized StringMatcher[] getStringMatchers() {
		if (fStringMatchers == null) {
			// TODO String str =
			// this.getPreferenceStore().getString(PreferenceConstants.METHODFILTER_ENABLED);
			String str = ""; //$NON-NLS-1$
			StringTokenizer tok = new StringTokenizer(str, ";"); //$NON-NLS-1$
			int nTokens = tok.countTokens();

			fStringMatchers = new StringMatcher[nTokens];
			for (int i = 0; i < nTokens; i++) {
				String curr = tok.nextToken();
				if (curr.length() > 0) {
					fStringMatchers[i] = new StringMatcher(curr, false, false);
				}
			}
		}
		return fStringMatchers;
	}

	public void dispose() {
		fStringMatchers = null;
	}

	public boolean hasFilters() {
		return getStringMatchers().length > 0;
	}

	public boolean filter(String fullMethodName) {
		StringMatcher[] matchers = getStringMatchers();
		for (int i = 0; i < matchers.length; i++) {
			StringMatcher curr = matchers[i];
			if (curr.match(fullMethodName)) {
				return true;
			}
		}
		return false;
	}

}
