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

import java.io.IOException;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.search.IOccurrencesFinder;
import org.eclipse.php.ui.editor.SharedASTProvider;
import org.eclipse.search.ui.NewSearchUI;

public final class FindOccurrencesEngine {

	public static FindOccurrencesEngine create(IOccurrencesFinder finder) {
		return new FindOccurrencesEngine(finder);
	}

	private IOccurrencesFinder fFinder;

	private FindOccurrencesEngine(IOccurrencesFinder finder) {
		if (finder == null)
			throw new IllegalArgumentException();
		fFinder = finder;
	}

	private String run(Program astRoot, int offset, int length) {
		String message = fFinder.initialize(astRoot, offset, length);
		if (message != null)
			return message;

		performNewSearch(fFinder, astRoot.getSourceModule());
		return null;
	}

	public String run(ISourceModule input, int offset, int length)
			throws ModelException, IOException {
		if (input.getSourceRange() == null) {
			return "SearchMessages.FindOccurrencesEngine_noSource_text"; //$NON-NLS-1$
		}

		final Program root = SharedASTProvider.getAST(input,
				SharedASTProvider.WAIT_YES, null);
		if (root == null) {
			return "SearchMessages.FindOccurrencesEngine_cannotParse_text"; //$NON-NLS-1$
		}
		return run(root, offset, length);
	}

	private void performNewSearch(IOccurrencesFinder finder,
			ISourceModule element) {
		NewSearchUI.runQueryInBackground(new OccurrencesSearchQuery(finder,
				element));
	}
}
