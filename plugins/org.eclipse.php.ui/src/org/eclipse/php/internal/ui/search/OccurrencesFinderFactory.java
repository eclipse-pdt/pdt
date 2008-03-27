/**
 * 
 */
package org.eclipse.php.internal.ui.search;

import org.eclipse.jface.text.IDocument;
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
	 * @param type One of the {@link PhpElementConciliator} constant types.
	 * @return An {@link IOccurrencesFinder}; Null, if the given type does not have an occurrences finder.
	 */
	public static IOccurrencesFinder getOccurrencesFinder(int type) {
		switch (type) {
			case PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
				return new LocalVariableOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
				return new GlobalVariableOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_FUNCTION:
				return new FunctionOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_CONSTANT:
				return new ConstantsOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_CLASSNAME:
				return new ClassNameOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_CLASS_MEMBER:
				return new ClassMembersOccurrencesFinder();
			case PhpElementConciliator.CONCILIATOR_UNKNOWN:
			case PhpElementConciliator.CONCILIATOR_PROGRAM:
			default:
				return null;
		}
	}

	/**
	 * Creates and returns an {@link HTMLOccurrencesFinder}.
	 * @param selectionOffset 
	 * @param document An instance of IStructuredDocument.
	 * 
	 * @return A new {@link HTMLOccurrencesFinder}
	 */
	public static IOccurrencesFinder createHTMLOccurrencesFinder(IDocument document, int selectionOffset) {
		return new HTMLOccurrencesFinder(document, selectionOffset);
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
}
