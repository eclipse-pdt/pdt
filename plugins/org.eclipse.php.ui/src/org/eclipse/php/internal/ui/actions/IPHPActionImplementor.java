package org.eclipse.php.internal.ui.actions;

/**
 * Actions that want to enable the mechanism of getting their implementation 
 * through the phpActionImplementor extention point, need to implement this interface
 * @author Eden K., 2007
 *
 */
public interface IPHPActionImplementor {
	
	void instantiateActionFromExtentionPoint();
}
