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
package org.eclipse.php.internal.ui.search;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.search.DLTKElementLine;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.search.IOccurrencesFinder;
import org.eclipse.php.internal.core.search.Messages;
import org.eclipse.php.internal.core.search.IOccurrencesFinder.OccurrenceLocation;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.text.Match;
import org.eclipse.wst.sse.ui.internal.search.OccurrencesSearchResult;

public class OccurrencesSearchQuery implements ISearchQuery {

	private final OccurrencesSearchResult fResult;
	private IOccurrencesFinder fFinder;
	private final ISourceModule fElement;
	private final String fJobLabel;
	private final String fSingularLabel;
	private final String fPluralLabel;
	private final String fName;
	private final String fFinderId;

	public OccurrencesSearchQuery(IOccurrencesFinder finder,
			ISourceModule element) {
		fFinder = finder;
		fElement = element;
		fJobLabel = fFinder.getJobLabel();
		fResult = new OccurrencesSearchResult(this);
		fSingularLabel = fFinder.getUnformattedSingularLabel();
		fPluralLabel = fFinder.getUnformattedPluralLabel();
		fName = fFinder.getElementName();
		fFinderId = fFinder.getID();
	}

	/*
	 * @seeorg.eclipse.search.ui.ISearchQuery#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	public IStatus run(IProgressMonitor monitor) {
		if (fFinder == null) {
			return new StatusInfo(IStatus.ERROR,
					org.eclipse.php.internal.ui.search.Messages.OccurrencesSearchQuery_0); 
		}
		if (monitor == null)
			monitor = new NullProgressMonitor();

		try {
			OccurrenceLocation[] occurrences = fFinder.getOccurrences();
			if (occurrences != null) {
				HashMap lineMap = new HashMap();
				Program astRoot = fFinder.getASTRoot();
				ArrayList resultingMatches = new ArrayList();

				for (int i = 0; i < occurrences.length; i++) {
					OccurrenceLocation loc = occurrences[i];

					DLTKElementLine lineKey = getLineElement(astRoot, loc,
							lineMap);
					if (lineKey != null) {
						OccurrenceMatch match = new OccurrenceMatch(lineKey,
								loc.getOffset(), loc.getLength(), loc
										.getFlags());
						resultingMatches.add(match);

						// TODO see location flags for more information
						// lineKey.setFlags(lineKey.getFlags() |
						// loc.getFlags());
					}
				}

				if (!resultingMatches.isEmpty()) {
					fResult.addMatches((Match[]) resultingMatches
							.toArray(new Match[resultingMatches.size()]));
				}
			}

		} finally {
			// Don't leak AST:
			fFinder = null;
			monitor.done();
		}
		return Status.OK_STATUS;
	}

	private DLTKElementLine getLineElement(Program astRoot,
			OccurrenceLocation location, HashMap lineToGroup) {
		int lineNumber = astRoot.getLineNumber(location.getOffset());
		if (lineNumber <= 0) {
			return null;
		}
		DLTKElementLine lineElement = null;
		Integer key = new Integer(lineNumber);
		lineElement = (DLTKElementLine) lineToGroup.get(key);
		if (lineElement == null) {
			int lineStartOffset = astRoot.getPosition(lineNumber, 0);
			if (lineStartOffset >= 0) {
				// lineNumber - 1, FIXME - set the correct line content
				lineElement = new DLTKElementLine(astRoot.getSourceModule(),
						lineStartOffset, ""); //$NON-NLS-1$
				lineToGroup.put(key, lineElement);
			}
		}
		return lineElement;
	}

	/*
	 * @see org.eclipse.search.ui.ISearchQuery#getLabel()
	 */
	public String getLabel() {
		return fJobLabel;
	}

	public String getResultLabel(int nMatches) {
		if (nMatches == 1) {
			return Messages.format(fSingularLabel, new Object[] { fName,
					fElement.getElementName() });
		} else {
			return Messages.format(fPluralLabel, new Object[] { fName,
					new Integer(nMatches), fElement.getElementName() });
		}
	}

	/*
	 * @see org.eclipse.search.ui.ISearchQuery#canRerun()
	 */
	public boolean canRerun() {
		return false; // must release finder to not keep AST reference
	}

	/*
	 * @see org.eclipse.search.ui.ISearchQuery#canRunInBackground()
	 */
	public boolean canRunInBackground() {
		return true;
	}

	/*
	 * @see org.eclipse.search.ui.ISearchQuery#getSearchResult()
	 */
	public ISearchResult getSearchResult() {
		return fResult;
	}

	/**
	 * Returns the finder ID.
	 * 
	 * @return the finder ID
	 */
	public String getFinderId() {
		return fFinderId;
	}
}
