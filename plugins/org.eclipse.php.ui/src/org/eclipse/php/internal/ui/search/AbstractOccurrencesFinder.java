/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.corext.dom.NodeFinder;

/**
 * A base class for all the occurrence finders.
 * 
 * @author shalom
 */
public abstract class AbstractOccurrencesFinder extends AbstractVisitor implements IOccurrencesFinder {

	protected static final String BASE_DESCRIPTION = PHPUIMessages.getString("AbstractOccurrencesFinder.0"); //$NON-NLS-1$
	protected static final String BASE_WRITE_DESCRIPTION = PHPUIMessages.getString("AbstractOccurrencesFinder.1"); //$NON-NLS-1$
	protected static final String BRACKETS = "()";

	protected List<OccurrenceLocation> fResult;
	protected String fDescription;
	protected Program fASTRoot;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#initialize(org.eclipse.php.internal.core.ast.nodes.Program, int, int)
	 */
	public String initialize(Program root, int offset, int length) {
		return initialize(root, NodeFinder.perform(root, offset, length));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getOccurrences()
	 */
	public OccurrenceLocation[] getOccurrences() {
		fResult = new ArrayList<OccurrenceLocation>();
		findOccurrences();
		if (fResult.isEmpty())
			return null;

		return fResult.toArray(new OccurrenceLocation[fResult.size()]);
	}

	/**
	 * Find and add all the occurrences.
	 * Extending finders must implement this method to fill the results list.
	 * Note that this method should not be called directly. It is being called by the {@link #getOccurrences()}.
	 * 
	 * @see #getOccurrences()
	 */
	protected abstract void findOccurrences();

	/**
	 * Returns the type of this occurrence.
	 * 
	 * @param node The {@link ASTNode} to check.
	 * @return The occurrence type (one of {@link IOccurrencesFinder} type constants)
	 * @see IOccurrencesFinder
	 */
	protected abstract int getOccurrenceType(ASTNode node);

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getASTRoot()
	 */
	public Program getASTRoot() {
		return fASTRoot;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getJobLabel()
	 */
	public String getJobLabel() {
		return "OccurrencesFinder_job_label"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getSearchKind()
	 */
	public int getSearchKind() {
		return IOccurrencesFinder.K_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getUnformattedPluralLabel()
	 */
	public String getUnformattedPluralLabel() {
		return "OccurrencesFinder_label_plural"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getUnformattedSingularLabel()
	 */
	public String getUnformattedSingularLabel() {
		return "OccurrencesFinder_label_singular"; //$NON-NLS-1$
	}
}
