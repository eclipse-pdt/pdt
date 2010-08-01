package org.eclipse.php.internal.core.index;

/**
 * This is a kind of element that can hold some PHPDoc information
 * 
 * @author Michael
 * 
 */
public interface IPHPDocAwareElement {

	/**
	 * Returns whether the element is marked as deprecated
	 * 
	 * @return
	 */
	boolean isDeprecated();

	/**
	 * Returns array of method return types
	 * 
	 * @return return types array or <code>null</code> if there were no return
	 *         types declared
	 */
	String[] getReturnTypes();
}
