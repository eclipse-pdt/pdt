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
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
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
	protected ProblemDesc[] fProblems;

	static class ProblemDesc {
		int kind;
		int offset;
		int end;
		int severity;

		public ProblemDesc(int kind, int offset, int end, int severity) {
			this.kind = kind;
			this.offset = offset;
			this.end = end;
			this.severity = severity;
		}

		public boolean isError() {
			return (this.severity & ProblemSeverities.Error) != 0;
		}
	}

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
	 * Returns all problems in this program 
	 * @param node
	 * @return
	 */
	public static ProblemDesc[] getProblems(Program node) {
		try {
			if (node.getSourceModule() == null) {
				return null;
			}
			
			IResource resource = node.getSourceModule().getUnderlyingResource();
			if (resource != null) {
				IMarker[] markers = resource.findMarkers(DefaultProblem.MARKER_TYPE_PROBLEM, true, IResource.DEPTH_ONE);
				ProblemDesc[] problems = new ProblemDesc[markers.length];
				for (int i = 0; i < markers.length; ++i) {
					problems[i] = new ProblemDesc(
						markers[i].getAttribute("id", 0), 
						markers[i].getAttribute(IMarker.CHAR_START, 0), 
						markers[i].getAttribute(IMarker.CHAR_END, 0), 
						markers[i].getAttribute(IMarker.SEVERITY, 0));
				}
				return problems;
			}
		} catch (CoreException e) {
		}
		return null;
	}

	/**
	 * Whether the specified source range contains error
	 * @param offset
	 * @param end
	 * @return
	 */
	protected boolean hasProblems(int offset, int end) {
		if (fProblems != null) {
			// Check that current location doesn't contain errors
			for (ProblemDesc problemDesc : fProblems) {
				if (problemDesc.offset <= offset && problemDesc.end >= end) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds occurrence to results list in case there's no error in this position
	 * @param location Occurrence location
	 */
	protected void addOccurrence(OccurrenceLocation location) {
		if (!hasProblems(location.getOffset(), location.getOffset() + location.getLength())) {
			fResult.add(location);
		}
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
