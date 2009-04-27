package org.eclipse.php.internal.ui.cleanup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.internal.corext.refactoring.changes.SourceModuleChange;


/**
 * A clean up fix calculates a {@link CompilationUnitChange} which can be applied on a document to
 * fix one or more problems in a compilation unit.
 * 
 * @since 3.5
 */
public interface ICleanUpFix {

	/**
	 * Calculates and returns a {@link CompilationUnitChange} which can be applied on a document to
	 * fix one or more problems in a compilation unit.
	 * 
	 * @param progressMonitor the progress monitor or <code>null</code> if none
	 * @return a compilation unit change change which should not be empty
	 * @throws CoreException if something went wrong while calculating the change
	 */
	public SourceModuleChange createChange(IProgressMonitor progressMonitor) throws CoreException;

}
