/**
 * 
 */
package org.eclipse.php.internal.ui.search;

import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;

/**
 * A factory class for the creation of {@link IOccurrencesFinder}s.
 * The factory receives {@link PhpElementConciliator} type identifiers and returns
 * an appropriate {@link IOccurrencesFinder}.
 * 
 * @author shalom
 */
public class OccurrencesFinderFactory {

	/**
	 * Creates and returns an {@link IOccurrencesFinder} for the given {@link PhpElementConciliator} type.
	 * 
	 * @param type
	 * @return
	 */
	public static IOccurrencesFinder getOccurrencesFinder(int type) {
		switch (type) {
			case PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
				return new LocalVariableOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
				return new GlobalVariableOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_FUNCTION:
			case PhpElementConciliator.CONCILIATOR_CLASSNAME:
			case PhpElementConciliator.CONCILIATOR_CONSTANT:
			case PhpElementConciliator.CONCILIATOR_CLASS_MEMBER:
			case PhpElementConciliator.CONCILIATOR_UNKNOWN:
			case PhpElementConciliator.CONCILIATOR_PROGRAM:
			default:
				return null;
		}
	}
}
