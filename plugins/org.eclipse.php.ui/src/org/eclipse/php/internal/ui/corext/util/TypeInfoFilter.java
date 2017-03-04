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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.ISearchPatternProcessor;
import org.eclipse.dltk.core.ISearchPatternProcessor.ITypePattern;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.core.search.indexing.IIndexConstants;
import org.eclipse.dltk.internal.corext.util.TypeInfoRequestorAdapter;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.ui.dialogs.ITypeInfoFilterExtension;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.ui.util.PatternMatcher;

public class TypeInfoFilter {

	private final String fText;
	private final IDLTKSearchScope fSearchScope;
	private final boolean fIsWorkspaceScope;
	private final int fElementKind;
	private final ITypeInfoFilterExtension fFilterExtension;
	private final TypeInfoRequestorAdapter fAdapter = new TypeInfoRequestorAdapter();

	private PatternMatcher fPackageMatcher;
	private final PatternMatcher fNameMatcher;

	private static final int TYPE_MODIFIERS = Flags.AccAnnotation | Flags.AccInterface;

	public TypeInfoFilter(IDLTKUILanguageToolkit uiToolkit, String text, IDLTKSearchScope scope, int elementKind,
			ITypeInfoFilterExtension extension, ISearchPatternProcessor processor) {
		fText = text;
		fSearchScope = scope;
		fIsWorkspaceScope = fSearchScope.equals(SearchEngine.createWorkspaceScope(uiToolkit.getCoreToolkit()));
		fElementKind = elementKind;
		fFilterExtension = extension;

		ITypePattern pattern = processor.parseType(text);
		String simpleName = pattern.getSimpleName();
		if (simpleName.length() == 0) {
			simpleName = "*"; //$NON-NLS-1$
		}
		fNameMatcher = new PatternMatcher(simpleName);
		if (pattern.getQualification() != null) {
			fPackageMatcher = new PatternMatcher(evaluatePackagePattern(pattern.getQualification()));
		}
	}

	/*
	 * Transforms o.e.j to o*.e*.j*
	 */
	private String evaluatePackagePattern(String s) {
		StringBuilder buf = new StringBuilder();
		boolean hasWildCard = false;
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == ISearchPatternProcessor.TYPE_SEPARATOR) {
				if (!hasWildCard) {
					buf.append('*');
				}
				hasWildCard = false;
			} else if (ch == '*' || ch == '?') {
				hasWildCard = true;
			}
			buf.append(ch);
		}
		if (!hasWildCard) {
			if (len == 0) {
				buf.append('?');
			}
			buf.append('*');
		}
		return buf.toString();
	}

	public String getText() {
		return fText;
	}

	/**
	 * Checks whether <code>this</code> filter is a subFilter of the given
	 * <code>text</code>.
	 * <p>
	 * <i>WARNING: This is the <b>reverse</b> interpretation compared to
	 * {@link org.eclipse.ui.dialogs.SearchPattern#isSubPattern(org.eclipse.ui.dialogs.SearchPattern)}
	 * and
	 * {@link org.eclipse.ui.dialogs.FilteredItemsSelectionDialog.ItemsFilter#isSubFilter}.
	 * </i>
	 * </p>
	 *
	 * @param text
	 *            another filter text
	 * @return <code>true</code> if <code>this</code> filter is a subFilter of
	 *         <code>text</code> e.g. "List" is a subFilter of "L". In this case,
	 *         the filters matches a proper subset of the items matched by
	 *         <code>text</code>.
	 */
	public boolean isSubFilter(String text) {
		if (!fText.startsWith(text))
			return false;

		return fText.indexOf(NamespaceReference.NAMESPACE_DELIMITER, text.length()) == -1;
	}

	public boolean isCamelCasePattern() {
		int ccMask = SearchPattern.R_CAMELCASE_MATCH | SearchPattern.R_CAMELCASE_SAME_PART_COUNT_MATCH;
		return (fNameMatcher.getMatchKind() & ccMask) != 0;
	}

	public String getPackagePattern() {
		if (fPackageMatcher == null)
			return null;
		return fPackageMatcher.getPattern();
	}

	public String getNamePattern() {
		return fNameMatcher.getPattern();
	}

	public int getSearchFlags() {
		return fNameMatcher.getMatchKind();
	}

	public int getElementKind() {
		return fElementKind;
	}

	public IDLTKSearchScope getSearchScope() {
		return fSearchScope;
	}

	public int getPackageFlags() {
		if (fPackageMatcher == null)
			return SearchPattern.R_EXACT_MATCH;

		return fPackageMatcher.getMatchKind();
	}

	public boolean matchesRawNamePattern(TypeNameMatch type) {
		return StringUtils.startsWithIgnoreCase(type.getSimpleTypeName(), fNameMatcher.getPattern());
	}

	public boolean matchesCachedResult(TypeNameMatch type) {
		if (!(matchesPackage(type) && matchesFilterExtension(type)))
			return false;
		return matchesName(type);
	}

	public boolean matchesHistoryElement(TypeNameMatch type) {
		if (!(matchesPackage(type) && matchesModifiers(type) && matchesScope(type) && matchesFilterExtension(type)))
			return false;
		return matchesName(type);
	}

	public boolean matchesFilterExtension(TypeNameMatch type) {
		if (fFilterExtension == null)
			return true;
		fAdapter.setMatch(type);
		return fFilterExtension.select(fAdapter);
	}

	private boolean matchesName(TypeNameMatch type) {
		if (fText.length() == 0) {
			return true; // empty pattern matches all names
		}
		return fNameMatcher.matches(type.getSimpleTypeName());
	}

	private boolean matchesPackage(TypeNameMatch type) {
		if (fPackageMatcher == null)
			return true;
		return fPackageMatcher.matches(type.getTypeContainerName().replace(NamespaceReference.NAMESPACE_SEPARATOR,
				IIndexConstants.TYPE_SEPARATOR));
	}

	private boolean matchesScope(TypeNameMatch type) {
		if (fIsWorkspaceScope)
			return true;
		return fSearchScope.encloses(type.getType());
	}

	private boolean matchesModifiers(TypeNameMatch type) {
		if (fElementKind == IDLTKSearchConstants.TYPE)
			return true;
		int modifiers = type.getModifiers() & TYPE_MODIFIERS;
		switch (fElementKind) {
		case IDLTKSearchConstants.TYPE:
			return modifiers == 0;
		}
		return false;
	}

}
