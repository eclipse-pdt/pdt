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
package org.eclipse.php.internal.ui.text.correction;

import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.compiler.problem.*;
import org.eclipse.dltk.core.IModelMarker;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.editor.ScriptMarkerAnnotation;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.ui.text.correction.IProblemLocation;

/**
 *
 */
public class ProblemLocation implements IProblemLocation {

	@Nullable
	private final IProblemIdentifier fIdentifier;
	private final String[] fArguments;
	private final int fOffset;
	private final int fLength;
	private final boolean fIsError;
	private final String fMarkerType;

	public ProblemLocation(int offset, int length, IScriptAnnotation annotation) {
		fIdentifier = annotation.getId();
		fArguments = annotation.getArguments();
		fOffset = offset;
		fLength = length;
		fIsError = ScriptMarkerAnnotation.ERROR_ANNOTATION_TYPE.equals(annotation.getType());

		String markerType = annotation.getMarkerType();
		fMarkerType = markerType != null ? markerType : IModelMarker.SCRIPT_MODEL_PROBLEM_MARKER;
	}

	public ProblemLocation(int offset, int length, IProblemIdentifier id, String[] arguments, boolean isError,
			String markerType) {
		fIdentifier = id;
		fArguments = arguments;
		fOffset = offset;
		fLength = length;
		fIsError = isError;
		fMarkerType = markerType;
	}

	@SuppressWarnings("deprecation")
	public ProblemLocation(IProblem problem) {
		fIdentifier = problem.getID();
		fArguments = problem.getArguments();
		fOffset = problem.getSourceStart();
		fLength = problem.getSourceEnd() - fOffset + 1;
		fIsError = problem.isError();
		if (problem.getID() instanceof IProblemIdentifierExtension) {
			fMarkerType = ((IProblemIdentifierExtension) problem.getID()).getMarkerType();
		} else if (problem instanceof CategorizedProblem) {
			fMarkerType = ((CategorizedProblem) problem).getMarkerType();
		} else {
			fMarkerType = IModelMarker.SCRIPT_MODEL_PROBLEM_MARKER;
		}
	}

	@Override
	public IProblemIdentifier getProblemIdentifier() {
		return fIdentifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.text.correction.IProblemLocation#
	 * getProblemArguments()
	 */
	@Override
	public String[] getProblemArguments() {
		return fArguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#getLength()
	 */
	@Override
	public int getLength() {
		return fLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#getOffset()
	 */
	@Override
	public int getOffset() {
		return fOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.text.java.IProblemLocation#isError()
	 */
	@Override
	public boolean isError() {
		return fIsError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.text.java.IProblemLocation#getMarkerType()
	 */
	@Override
	public String getMarkerType() {
		return fMarkerType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#
	 * getCoveringNode (org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	@Override
	public ASTNode getCoveringNode(Program astRoot) {
		NodeFinder finder = new NodeFinder(fOffset, fLength);
		astRoot.accept(finder);
		return finder.getCoveringNode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#
	 * getCoveredNode (org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	@Override
	public ASTNode getCoveredNode(Program astRoot) {
		NodeFinder finder = new NodeFinder(fOffset, fLength);
		astRoot.accept(finder);
		return finder.getCoveredNode();
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(Messages.ProblemLocation_0).append(DefaultProblemIdentifier.encode(fIdentifier)).append('\n');
		buf.append('[').append(fOffset).append(", ").append(fLength).append(']') //$NON-NLS-1$
				.append('\n');
		String[] arg = fArguments;
		if (arg != null) {
			for (int i = 0; i < arg.length; i++) {
				buf.append(arg[i]);
				buf.append('\n');
			}
		}
		return buf.toString();
	}

}
