/**
 *
 */
package org.eclipse.php.internal.core.phpModel.parser;

import java.util.List;

/**
 * @author seva, 2007
 *
 */
public interface IPHPUserModel extends IPhpModel {

	public abstract void addModelListener(ModelListener l);

	public abstract List<ModelListener> getModelListenerList();

	public abstract void removeModelListener(ModelListener l);

}