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

import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;

public interface IOccurrencesFinder {

	public static final int K_OCCURRENCE = 5;

	public static final int K_EXCEPTION_OCCURRENCE = 6;
	public static final int K_EXIT_POINT_OCCURRENCE = 7;
	public static final int K_IMPLEMENTS_OCCURRENCE = 8;
	public static final int K_BREAK_TARGET_OCCURRENCE = 9;

	public static final int F_WRITE_OCCURRENCE = 1;
	public static final int F_READ_OCCURRENCE = 2;
	public static final int F_EXCEPTION_DECLARATION = 8;

	/**
	 * Element representing a occurrence
	 */
	public static class OccurrenceLocation {
		private final int fOffset;
		private final int fLength;
		private final int fFlags;
		private final String fDescription;

		public OccurrenceLocation(int offset, int length, int flags,
				String description) {
			fOffset = offset;
			fLength = length;
			fFlags = flags;
			fDescription = description;
		}

		public int getOffset() {
			return fOffset;
		}

		public int getLength() {
			return fLength;
		}

		public int getFlags() {
			return fFlags;
		}

		public String getDescription() {
			return fDescription;
		}

		public String toString() {
			return "[" + fOffset + " / " + fLength + "] " + fDescription; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		}

	}

	public String initialize(Program root, int offset, int length);

	public String initialize(Program root, ASTNode node);

	public String getJobLabel();

	/**
	 * Returns the plural label for this finder with 3 placeholders:
	 * <ul>
	 * <li>{0} for the {@link #getElementName() element name}</li>
	 * <li>{1} for the number of results found</li>
	 * <li>{2} for the scope (name of the compilation unit)</li>
	 * </ul>
	 * 
	 * @return the unformatted label
	 */
	public String getUnformattedPluralLabel();

	/**
	 * Returns the singular label for this finder with 2 placeholders:
	 * <ul>
	 * <li>{0} for the {@link #getElementName() element name}</li>
	 * <li>{1} for the scope (name of the compilation unit)</li>
	 * </ul>
	 * 
	 * @return the unformatted label
	 */
	public String getUnformattedSingularLabel();

	/**
	 * Returns the name of the element to look for or <code>null</code> if the
	 * finder hasn't been initialized yet.
	 * 
	 * @return the name of the element
	 */
	public String getElementName();

	/**
	 * Returns the AST root.
	 * 
	 * @return the AST root
	 */
	public Program getASTRoot();

	/**
	 * Returns the occurrences
	 * 
	 * @return the occurrences
	 */
	public OccurrenceLocation[] getOccurrences();

	public int getSearchKind();

	/**
	 * Returns the id of this finder.
	 * 
	 * @return returns the id of this finder.
	 */
	public String getID();

}
