/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.php.internal.ui.PHPElementLabelProvider;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public abstract class SearchLabelProvider extends PHPElementLabelProvider {

	public static final String PROPERTY_MATCH_COUNT = "org.eclipse.php.search.matchCount"; //$NON-NLS-1$

	// copied from SearchPreferencePage
	private static final String EMPHASIZE_POTENTIAL_MATCHES = "org.eclipse.search.potentialMatch.emphasize"; //$NON-NLS-1$
	private static final String POTENTIAL_MATCH_FG_COLOR = "org.eclipse.search.potentialMatch.fgColor"; //$NON-NLS-1$

	private Color fPotentialMatchFgColor;
	private Map fLabelProviderMap;

	protected PHPSearchResultPage fPage;

	private ScopedPreferenceStore fSearchPreferences;
	private IPropertyChangeListener fSearchPropertyListener;

	public SearchLabelProvider(PHPSearchResultPage page, int flags) {
		super(flags);
		//		addLabelDecorator(new ProblemsLabelDecorator());

		fPage = page;
		fLabelProviderMap = new HashMap(5);

		fSearchPreferences = new ScopedPreferenceStore(new InstanceScope(), NewSearchUI.PLUGIN_ID);
		fSearchPropertyListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				doSearchPropertyChange(event);
			}
		};
		fSearchPreferences.addPropertyChangeListener(fSearchPropertyListener);
	}

	final void doSearchPropertyChange(PropertyChangeEvent event) {
		if (fPotentialMatchFgColor == null)
			return;
		if (POTENTIAL_MATCH_FG_COLOR.equals(event.getProperty()) || EMPHASIZE_POTENTIAL_MATCHES.equals(event.getProperty())) {
			fPotentialMatchFgColor.dispose();
			fPotentialMatchFgColor = null;
			LabelProviderChangedEvent lpEvent = new LabelProviderChangedEvent(SearchLabelProvider.this, null); // refresh all
			fireLabelProviderChanged(lpEvent);
		}
	}

	protected final int getNumberOfPotentialMatches(Object element) {
		int res = 0;
		// TODO - Add potential matches
		//		AbstractTextSearchResult result= fPage.getInput();
		//		if (result != null) {
		//			Match[] matches= result.getMatches(element);
		//			for (int i = 0; i < matches.length; i++) {
		//				if ((matches[i]) instanceof PHPElementMatch) {
		//					if (((JavaElementMatch)matches[i]).getAccuracy() == SearchMatch.A_INACCURATE)
		//						res++;
		//				}
		//			}
		//		}
		return res;
	}

	protected final String getLabelWithCounts(Object element, String elementName) {
		int matchCount = fPage.getDisplayedMatchCount(element);
		int potentialCount = getNumberOfPotentialMatches(element);

		if (matchCount < 2) {
			if (matchCount == 1 && hasChildren(element)) {
				if (potentialCount > 0)
					return Messages.format(PHPUIMessages.SearchLabelProvider_potential_singular, elementName);
				return Messages.format(PHPUIMessages.SearchLabelProvider_exact_singular, elementName);
			}
			if (potentialCount > 0)
				return Messages.format(PHPUIMessages.SearchLabelProvider_potential_noCount, elementName);
			return Messages.format(PHPUIMessages.SearchLabelProvider_exact_noCount, elementName);
		} else {
			int exactCount = matchCount - potentialCount;

			if (potentialCount > 0 && exactCount > 0) {
				String[] args = new String[] { elementName, String.valueOf(matchCount), String.valueOf(exactCount), String.valueOf(potentialCount) };
				return Messages.format(PHPUIMessages.SearchLabelProvider_exact_and_potential_plural, args);
			} else if (exactCount == 0) {
				String[] args = new String[] { elementName, String.valueOf(matchCount) };
				return Messages.format(PHPUIMessages.SearchLabelProvider_potential_plural, args);
			}
			String[] args = new String[] { elementName, String.valueOf(matchCount) };
			return Messages.format(PHPUIMessages.SearchLabelProvider_exact_plural, args);
		}
	}

	protected boolean hasChildren(Object elem) {
		return false;
	}

	public void dispose() {
		if (fPotentialMatchFgColor != null) {
			fPotentialMatchFgColor.dispose();
			fPotentialMatchFgColor = null;
		}
		fSearchPreferences.removePropertyChangeListener(fSearchPropertyListener);
		for (Iterator labelProviders = fLabelProviderMap.values().iterator(); labelProviders.hasNext();) {
			ILabelProvider labelProvider = (ILabelProvider) labelProviders.next();
			labelProvider.dispose();
		}
		fSearchPreferences = null;
		fSearchPropertyListener = null;
		fLabelProviderMap.clear();

		super.dispose();
	}

	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
		for (Iterator labelProviders = fLabelProviderMap.values().iterator(); labelProviders.hasNext();) {
			ILabelProvider labelProvider = (ILabelProvider) labelProviders.next();
			labelProvider.addListener(listener);
		}
	}

	public boolean isLabelProperty(Object element, String property) {
		if (PROPERTY_MATCH_COUNT.equals(property)) {
			return true;
		}
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		super.removeListener(listener);
		for (Iterator labelProviders = fLabelProviderMap.values().iterator(); labelProviders.hasNext();) {
			ILabelProvider labelProvider = (ILabelProvider) labelProviders.next();
			labelProvider.removeListener(listener);
		}
	}

	private boolean arePotentialMatchesEmphasized() {
		return fSearchPreferences.getBoolean(EMPHASIZE_POTENTIAL_MATCHES);
	}

	private RGB getPotentialMatchForegroundColor() {
		return PreferenceConverter.getColor(fSearchPreferences, POTENTIAL_MATCH_FG_COLOR);
	}
}
