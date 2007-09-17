/**
 *
 */
package org.eclipse.php.internal.core.phpModel.parser;

/**
 * Listens to IncludePathModelManager for addition and removal of models
 * @author seva, 2007
 */
public interface IncludePathModelListener {

	/**
	 * Model addition event
	 */
	public void includeModelAdded(IPhpModel model);

	/**
	 * Model removal event
	 */
	public void includeModelRemoved(IPhpModel model);
}
