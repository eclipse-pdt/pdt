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
package org.eclipse.php.internal.core.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;

/**
 * A base class for all the occurrence finders.
 * 
 * @author shalom
 */
public abstract class AbstractOccurrencesFinder extends AbstractVisitor
		implements IOccurrencesFinder {

	protected static final String BASE_DESCRIPTION = CoreMessages
			.getString("AbstractOccurrencesFinder.0"); //$NON-NLS-1$
	protected static final String BASE_WRITE_DESCRIPTION = CoreMessages
			.getString("AbstractOccurrencesFinder.1"); //$NON-NLS-1$
	protected static final String BRACKETS = "()"; //$NON-NLS-1$

	protected List<OccurrenceLocation> fResult;
	protected String fDescription;
	protected Program fASTRoot;
	protected ProblemDesc[] fProblems;

	protected NamespaceDeclaration fCurrentNamespace;
	protected Map<String, UseStatementPart> fLastUseParts = new HashMap<String, UseStatementPart>();

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
			return (this.severity & IMarker.SEVERITY_ERROR) != 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#initialize(org.
	 * eclipse.php.internal.core.ast.nodes.Program, int, int)
	 */
	public String initialize(Program root, int offset, int length) {
		return initialize(root, NodeFinder.perform(root, offset, length));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#getOccurrences()
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
	 * 
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
				IMarker[] markers = resource.findMarkers(
						DefaultProblem.MARKER_TYPE_PROBLEM, true,
						IResource.DEPTH_ONE);
				ProblemDesc[] problems = new ProblemDesc[markers.length];
				for (int i = 0; i < markers.length; ++i) {
					problems[i] = new ProblemDesc(markers[i].getAttribute("id",//$NON-NLS-1$
							0), markers[i].getAttribute(IMarker.CHAR_START, 0),
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
	 * 
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
	 * 
	 * @param location
	 *            Occurrence location
	 */
	protected void addOccurrence(OccurrenceLocation location) {
		if (!hasProblems(location.getOffset(),
				location.getOffset() + location.getLength())) {
			fResult.add(location);
		}
	}

	/**
	 * Find and add all the occurrences. Extending finders must implement this
	 * method to fill the results list. Note that this method should not be
	 * called directly. It is being called by the {@link #getOccurrences()}.
	 * 
	 * @see #getOccurrences()
	 */
	protected abstract void findOccurrences();

	/**
	 * Returns the type of this occurrence.
	 * 
	 * @param node
	 *            The {@link ASTNode} to check.
	 * @return The occurrence type (one of {@link IOccurrencesFinder} type
	 *         constants)
	 * @see IOccurrencesFinder
	 */
	protected abstract int getOccurrenceType(ASTNode node);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getASTRoot()
	 */
	public Program getASTRoot() {
		return fASTRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getJobLabel()
	 */
	public String getJobLabel() {
		return "OccurrencesFinder_job_label"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.search.IOccurrencesFinder#getSearchKind()
	 */
	public int getSearchKind() {
		return IOccurrencesFinder.K_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.IOccurrencesFinder#
	 * getUnformattedPluralLabel()
	 */
	public String getUnformattedPluralLabel() {
		return "OccurrencesFinder_label_plural"; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.search.IOccurrencesFinder#
	 * getUnformattedSingularLabel()
	 */
	public String getUnformattedSingularLabel() {
		return "OccurrencesFinder_label_singular"; //$NON-NLS-1$
	}

	public boolean visit(NamespaceDeclaration namespaceDeclaration) {
		fCurrentNamespace = namespaceDeclaration;
		fLastUseParts.clear();
		return true;
	}

	public void endVisit(NamespaceDeclaration namespaceDeclaration) {
		fCurrentNamespace = null;
		fLastUseParts.clear();
	}

	public boolean visit(UseStatement useStatement) {
		List<UseStatementPart> useParts = useStatement.parts();
		for (UseStatementPart part : useParts) {
			String name = null;
			if (part.getAlias() != null) {
				name = part.getAlias().getName();
			} else {
				name = part.getName().getName();
				int index = name
						.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
				if (index >= 0) {
					name = name.substring(index + 1);
				}
			}
			fLastUseParts.put(name, part);
		}
		return true;
	}

	public static String getFullName(Identifier identifier,
			Map<String, UseStatementPart> lastUseParts,
			NamespaceDeclaration currentNamespace) {
		return getFullName(identifier.getName(), lastUseParts, currentNamespace);
	}

	public static String getFullName(String fullName,
			Map<String, UseStatementPart> lastUseParts,
			NamespaceDeclaration currentNamespace) {
		if (fullName.charAt(0) != NamespaceReference.NAMESPACE_SEPARATOR) {
			int index = fullName
					.lastIndexOf(NamespaceReference.NAMESPACE_SEPARATOR);
			if (index >= 0) {
				String namespace = fullName.substring(0, index);
				if (lastUseParts.containsKey(namespace)) {
					fullName = new StringBuilder(lastUseParts.get(namespace)
							.getName().getName())
							.append(NamespaceReference.NAMESPACE_SEPARATOR)
							.append(fullName.substring(index + 1)).toString();
				}
			} else if (lastUseParts.containsKey(fullName)) {
				fullName = new StringBuilder(lastUseParts.get(fullName)
						.getName().getName()).toString();
			} else {
				if (currentNamespace != null
						&& currentNamespace.getName() != null) {
					fullName = new StringBuilder(currentNamespace.getName()
							.getName())
							.append(NamespaceReference.NAMESPACE_SEPARATOR)
							.append(fullName).toString();
				}
			}
		}
		if (fullName.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			fullName = fullName.substring(1);
		}
		return fullName;
	}
}
