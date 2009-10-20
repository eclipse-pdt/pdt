package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IContainer;

public abstract class AbstractMoveDelegator implements IPHPActionDelegator {

	public abstract void setTarget(IContainer target);
}
