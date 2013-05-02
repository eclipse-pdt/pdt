/*******************************************************************************
 *  Copyright (c) 2005, 2009 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.php.internal.debug.core.model.PHPLineBreakpoint;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;

/**
 * An abstract change for breakpoint type refactoring changes
 * 
 * @since 3.2
 * 
 */
public abstract class BreakpointChange extends Change {

	/**
	 * constant for no line number
	 * 
	 * @since 3.5
	 */
	protected static final int NO_LINE_NUMBER = -1;

	private PHPLineBreakpoint fBreakpoint;
	// private String fTypeName;
	// private int fHitCount;
	// private IJavaObject[] fInstanceFilters;
	// private int fSuspendPolicy;
	// private IJavaThread[] fThreadFilters;
	private boolean fEnabled;

	/**
	 * Constructor
	 * 
	 * @param breakpoint
	 * @throws CoreException
	 */
	public BreakpointChange(PHPLineBreakpoint breakpoint) throws CoreException {
		fBreakpoint = breakpoint;
		// fTypeName = breakpoint.getTypeName();
		// fHitCount = breakpoint.getHitCount();
		// fInstanceFilters = breakpoint.getInstanceFilters();
		// fSuspendPolicy = breakpoint.getSuspendPolicy();
		// fThreadFilters = breakpoint.getThreadFilters();
		fEnabled = breakpoint.isEnabled();
	}

	/**
	 * Applies the original attributes to the new breakpoint
	 * 
	 * @param breakpoint
	 *            the new breakpoint
	 * @throws CoreException
	 */
	protected void apply(PHPLineBreakpoint breakpoint) throws CoreException {
		// breakpoint.setHitCount(fHitCount);
		// for (int i = 0; i < fInstanceFilters.length; i++) {
		// breakpoint.addInstanceFilter(fInstanceFilters[i]);
		// }
		// breakpoint.setSuspendPolicy(fSuspendPolicy);
		// for (int i = 0; i < fThreadFilters.length; i++) {
		// breakpoint.setThreadFilter(fThreadFilters[i]);
		// }
		breakpoint.setEnabled(fEnabled);
	}

	/**
	 * Returns the original breakpoints prior to the change
	 * 
	 * @return the original breakpoint prior to the change
	 */
	protected PHPLineBreakpoint getOriginalBreakpoint() {
		return fBreakpoint;
	}

	/**
	 * Returns the original name of the type the associated breakpoint was set
	 * on. This can be different than the type being changed.
	 * 
	 * @return
	 */
	protected String getOriginalBreakpointTypeName() {
		return ""; //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.
	 * eclipse.core.runtime.IProgressMonitor)
	 */
	public void initializeValidationData(IProgressMonitor pm) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return new RefactoringStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
	 */
	public Object getModifiedElement() {
		return fBreakpoint;
	}

	/**
	 * Returns an array of ints representing the new line number, char start and
	 * char end of the member.
	 * 
	 * @param member
	 * @return array of 3 ints or <code>null</code>
	 */
	protected int[] getNewLineNumberAndRange(IMember member)
			throws CoreException {
		ISourceRange nameRange = member.getNameRange();
		int offset = nameRange.getOffset();
		int lineNumber = getNewLineNumber(member, offset);
		return new int[] { lineNumber, offset, offset + nameRange.getLength() };
	}

	/**
	 * Returns the new line number of the from the source of the specified
	 * member's compilation unit
	 * 
	 * @param member
	 *            the member to query
	 * @param offset
	 *            the offset
	 * @return the new line number
	 * @throws ModelException
	 */
	private int getNewLineNumber(IMember member, int offset)
			throws ModelException {
		int lineNumber = getLineNumber();
		Document document = new Document(member.getSource());
		try {
			lineNumber = document.getLineOfOffset(offset);
		} catch (BadLocationException e) {
		}
		return lineNumber;
	}

	/**
	 * Return the line number for the breakpoint
	 * 
	 * @return the line number for the breakpoint
	 */
	protected int getLineNumber() {
		return -1;
	}

	/**
	 * Returns the hit count for the breakpoint
	 * 
	 * @return the hit count for the breakpoint
	 */
	// protected int getHitCount() {
	// return fHitCount;
	// }

	/**
	 * Returns the <code>IType</code> within the specified parent type given by
	 * simpleName
	 * 
	 * @param parent
	 * @param simpleName
	 * @return the <code>IType</code> within the specified parent type given by
	 *         simpleName
	 */
	// public static IType getType(IJavaElement parent, String simpleName) {
	// switch (parent.getElementType()) {
	// case IJavaElement.COMPILATION_UNIT:
	// return ((ICompilationUnit)parent).getType(simpleName);
	// case IJavaElement.TYPE:
	// return ((IType)parent).getType(simpleName);
	// case IJavaElement.FIELD:
	// case IJavaElement.INITIALIZER:
	// case IJavaElement.METHOD:
	// return ((IMember)parent).getType(simpleName, -1);
	// }
	// return null;
	// }

	/**
	 * Returns the <code>IJavaElement</code> contained within the specified
	 * parent one, or the parent one by default
	 * 
	 * @param parent
	 * @param element
	 * @return the <code>IJavaElement</code> contained within the specified
	 *         parent one, or the parent one by default
	 */
	// public static IJavaElement findElement(IJavaElement parent, IJavaElement
	// element) {
	// List children = getPath(element);
	// List path = getPath(parent);
	// IJavaElement currentElement = parent;
	// for (int i = children.size() - path.size() - 1; i >= 0; i--) {
	// IJavaElement child = (IJavaElement)children.get(i);
	// switch (child.getElementType()) {
	// case IJavaElement.PACKAGE_DECLARATION:
	// currentElement =
	// ((ICompilationUnit)currentElement).getPackageDeclaration(child.getElementName());
	// break;
	// case IJavaElement.IMPORT_CONTAINER:
	// currentElement = ((ICompilationUnit)currentElement).getImportContainer();
	// break;
	// case IJavaElement.IMPORT_DECLARATION:
	// currentElement =
	// ((IImportContainer)currentElement).getImport(child.getElementName());
	// break;
	// case IJavaElement.TYPE:
	// switch (currentElement.getElementType()) {
	// case IJavaElement.COMPILATION_UNIT:
	// currentElement =
	// ((ICompilationUnit)currentElement).getType(child.getElementName());
	// break;
	// case IJavaElement.CLASS_FILE:
	// currentElement = ((IClassFile)currentElement).getType();
	// break;
	// case IJavaElement.TYPE:
	// currentElement = ((IType)currentElement).getType(child.getElementName());
	// break;
	// case IJavaElement.FIELD:
	// case IJavaElement.INITIALIZER:
	// case IJavaElement.METHOD:
	// currentElement =
	// ((IMember)currentElement).getType(child.getElementName(),
	// ((IMember)child).getOccurrenceCount());
	// break;
	// }
	// break;
	// case IJavaElement.INITIALIZER:
	// currentElement =
	// ((IType)currentElement).getInitializer(((IMember)child).getOccurrenceCount());
	// break;
	// case IJavaElement.FIELD:
	// currentElement =
	// ((IType)currentElement).getField(child.getElementName());
	// break;
	// case IJavaElement.METHOD:
	// currentElement =
	// ((IType)currentElement).getMethod(child.getElementName(),
	// ((IMethod)child).getParameterTypes());
	// break;
	// }
	//
	// }
	// return currentElement;
	// }

	/**
	 * Returns the path of the given element up to but not including its
	 * compilation unit, in bottom up order.
	 * 
	 * @param element
	 * @return element's path
	 */
	// private static List getPath(IJavaElement element) {
	// ArrayList children = new ArrayList();
	// while (element != null && element.getElementType() !=
	// IJavaElement.COMPILATION_UNIT) {
	// children.add(element);
	// element = element.getParent();
	// }
	// return children;
	// }

	/**
	 * Returns a label for the given breakpoint generated from the JDI model
	 * presentation.
	 * 
	 * @param breakpoint
	 *            a breakpoint
	 * @return standard label for the breakpoint
	 */
	protected String getBreakpointLabel(IBreakpoint breakpoint) {
		return PHPDebugUIPlugin.getDefault().getModelPresentation().getText(
				breakpoint);
	}
}
