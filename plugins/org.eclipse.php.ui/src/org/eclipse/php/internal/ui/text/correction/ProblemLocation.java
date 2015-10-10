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

import java.util.Scanner;

import org.eclipse.dltk.compiler.problem.CategorizedProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.compiler.problem.IProblemIdentifierExtension;
import org.eclipse.dltk.core.IModelMarker;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.editor.ScriptMarkerAnnotation;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;

/**
 *
 */
public class ProblemLocation implements IProblemLocation {

	private final int fId;
	private final String[] fArguments;
	private final int fOffset;
	private final int fLength;
	private final boolean fIsError;
	private final String fMarkerType;
	private final IProblemIdentifier fIdentifier;

	public ProblemLocation(int offset, int length, IScriptAnnotation annotation) {
		if (annotation.getId() != null) {
			Scanner scan = new Scanner(annotation.getId().name());
			if (scan.hasNextInt()) {
				fId = scan.nextInt();
			} else {
				fId = -1;
			}
			fIdentifier = annotation.getId();
			scan.close();
		} else {
			fId = -1;
			fIdentifier = null;
		}
		fArguments = annotation.getArguments();
		fOffset = offset;
		fLength = length;
		fIsError = ScriptMarkerAnnotation.ERROR_ANNOTATION_TYPE.equals(annotation.getType());

		String markerType = annotation.getMarkerType();
		fMarkerType = markerType != null ? markerType : IModelMarker.SCRIPT_MODEL_PROBLEM_MARKER;
	}

	public ProblemLocation(int offset, int length, int id, String[] arguments, boolean isError, String markerType) {
		fId = id;
		fArguments = arguments;
		fOffset = offset;
		fLength = length;
		fIsError = isError;
		fMarkerType = markerType;
		fIdentifier = null;
	}

	@SuppressWarnings("deprecation")
	public ProblemLocation(IProblem problem) {
		if (problem.getID() != null) {
			Scanner scan = new Scanner(problem.getID().name());
			if (scan.hasNextInt()) {
				fId = scan.nextInt();
			} else {
				fId = -1;
			}
			fIdentifier = problem.getID();
			scan.close();
		} else {
			fId = -1;
			fIdentifier = null;
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#getProblemId
	 * ()
	 */
	public int getProblemId() {
		return fId;
	}

	public IProblemIdentifier getProblemIdentifier() {
		return fIdentifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.text.correction.IProblemLocation#
	 * getProblemArguments()
	 */
	public String[] getProblemArguments() {
		return fArguments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#getLength()
	 */
	public int getLength() {
		return fLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#getOffset()
	 */
	public int getOffset() {
		return fOffset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.text.java.IProblemLocation#isError()
	 */
	public boolean isError() {
		return fIsError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.text.java.IProblemLocation#getMarkerType()
	 */
	public String getMarkerType() {
		return fMarkerType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.internal.ui.text.correction.IProblemLocation#
	 * getCoveringNode (org.eclipse.jdt.core.dom.CompilationUnit)
	 */
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
	public ASTNode getCoveredNode(Program astRoot) {
		NodeFinder finder = new NodeFinder(fOffset, fLength);
		astRoot.accept(finder);
		return finder.getCoveredNode();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(Messages.ProblemLocation_0).append(getErrorCode(fId)).append('\n');
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

	private String getErrorCode(int code) {
		StringBuffer buf = new StringBuffer();

		if ((code & IProblem.TypeRelated) != 0) {
			buf.append(Messages.ProblemLocation_2);
		}
		if ((code & IProblem.FieldRelated) != 0) {
			buf.append(Messages.ProblemLocation_3);
		}
		if ((code & IProblem.ConstructorRelated) != 0) {
			buf.append(Messages.ProblemLocation_4);
		}
		if ((code & IProblem.MethodRelated) != 0) {
			buf.append(Messages.ProblemLocation_5);
		}
		if ((code & IProblem.ImportRelated) != 0) {
			buf.append(Messages.ProblemLocation_6);
		}
		if ((code & IProblem.Internal) != 0) {
			buf.append(Messages.ProblemLocation_7);
		}
		if ((code & IProblem.Syntax) != 0) {
			buf.append(Messages.ProblemLocation_8);
		}
		if ((code & IProblem.Documentation) != 0) {
			buf.append(Messages.ProblemLocation_9);
		}
		buf.append(code & IProblem.IgnoreCategoriesMask);

		return buf.toString();
	}

}
