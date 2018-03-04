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
/**
 * 
 */
package org.eclipse.php.internal.core.search;

import org.eclipse.php.internal.core.ast.locator.PHPElementConciliator;

/**
 * A factory class for the creation of {@link IOccurrencesFinder}s. The factory
 * receives {@link PHPElementConciliator} type identifiers and returns an
 * appropriate {@link IOccurrencesFinder}.
 * 
 * @author shalom
 */
public class OccurrencesFinderFactory {

	/**
	 * Creates and returns an {@link IOccurrencesFinder} for the given
	 * {@link PHPElementConciliator} type.
	 * 
	 * @param type
	 *            One of the {@link PHPElementConciliator} constant types.
	 * @return An {@link IOccurrencesFinder}; Null, if the given type does not have
	 *         an occurrences finder.
	 */
	public static IOccurrencesFinder getOccurrencesFinder(int type) {
		switch (type) {
		case PHPElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
			return new LocalVariableOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
			return new GlobalVariableOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_FUNCTION:
			return new FunctionOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_CONSTANT:
			return new ConstantsOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_CLASSNAME:
			return new ClassNameOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_TRAITNAME:
			return new TraitNameOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_CLASS_MEMBER:
			return new ClassMembersOccurrencesFinder();
		case PHPElementConciliator.CONCILIATOR_UNKNOWN:
		case PHPElementConciliator.CONCILIATOR_PROGRAM:
		default:
			return null;
		}
	}

	/**
	 * Creates and returns an {@link MethodExitsFinder}.
	 * 
	 * @return A new {@link MethodExitsFinder}
	 */
	public static IOccurrencesFinder createMethodExitsFinder() {
		return new MethodExitsFinder();
	}

	/**
	 * Creates and returns an {@link BreakContinueTargetFinder}.
	 * 
	 * @return A new {@link BreakContinueTargetFinder}
	 */
	public static IOccurrencesFinder createBreakContinueTargetFinder() {
		return new BreakContinueTargetFinder();
	}

	/**
	 * Creates and returns an {@link ImplementOccurrencesFinder}.
	 * 
	 * @return A new {@link ImplementOccurrencesFinder}
	 */
	public static IOccurrencesFinder createImplementorsOccurrencesFinder() {
		return new ImplementOccurrencesFinder();
	}

	public static IOccurrencesFinder createIncludeFinder() {
		return new IncludeOccurrencesFinder();
	}
}
