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

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

public class PHPSearchQuery implements ISearchQuery {

	private ISearchResult fResult;
	private QuerySpecification fPatternData;

	public PHPSearchQuery(QuerySpecification data) {
		fPatternData = data;
	}

	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		final PHPSearchResult textResult = (PHPSearchResult) getSearchResult();
		textResult.removeAll();
		PHPSearchEngine engine = new PHPSearchEngine();
		int totalTicks = IProgressMonitor.UNKNOWN;
		monitor.beginTask(PHPUIMessages.PHPSearchQuery_task_label, totalTicks);
		IProgressMonitor mainSearchPM = new SubProgressMonitor(monitor, totalTicks);
		String stringPattern = null;
		PatternQuerySpecification patternSpec = (PatternQuerySpecification) fPatternData;
		stringPattern = patternSpec.getQuery();
		engine.search(stringPattern, fPatternData.getScope(), textResult, patternSpec.isCaseSensitive(), mainSearchPM);
		String message = Messages.format(PHPUIMessages.PHPSearchQuery_status_ok_message, String.valueOf(textResult.getMatchCount()));
		return new Status(IStatus.OK, PHPUiPlugin.getPluginId(), 0, message, null);
	}

	public String getLabel() {
		return PHPUIMessages.PHPSearchQuery_label;
	}

	public boolean canRerun() {
		return true;
	}

	public boolean canRunInBackground() {
		return true;
	}

	public ISearchResult getSearchResult() {
		if (fResult == null) {
			fResult = new PHPSearchResult(this);
			//			new SearchResultUpdater((PHPSearchResult) fResult); // TODO - Create an updater
		}
		return fResult;
	}

	QuerySpecification getSpecification() {
		return fPatternData;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getResultLabel(int matchCount) {
		if (matchCount == 1) {
			String[] args = { fPatternData.getQuery(), fPatternData.getScopeDescription() };
			return Messages.format(PHPUIMessages.PHPSearchOperation_singularOccurrencesPostfix, args);
		} else {
			Object[] args = { fPatternData.getQuery(), new Integer(matchCount), fPatternData.getScopeDescription() };
			return Messages.format(PHPUIMessages.PHPSearchOperation_pluralOccurrencesPostfix, args);
		}
	}

}
