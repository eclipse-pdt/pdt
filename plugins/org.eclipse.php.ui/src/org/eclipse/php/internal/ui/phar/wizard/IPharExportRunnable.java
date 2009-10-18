package org.eclipse.php.internal.ui.phar.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.operation.IRunnableWithProgress;

public interface IPharExportRunnable extends IRunnableWithProgress {

	/**
	 * Returns the current status of this operation. The result is a status
	 * object which may contain individual nested status objects.
	 * <p>
	 * Clients may call this method during the operation and add additional
	 * status information.
	 * </p>
	 * 
	 * @return the status of this operation
	 */
	public IStatus getStatus();
}
