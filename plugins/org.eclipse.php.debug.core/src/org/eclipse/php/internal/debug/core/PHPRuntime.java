package org.eclipse.php.internal.debug.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;

public class PHPRuntime {
	public static final String PHP_CONTAINER = PHPDebugPlugin.ID
			+ ".PHP_CONTAINER"; //$NON-NLS-1$

	public static final String ID_STANDARD_VM_TYPE = "org.eclipse.php.internal.debug.ui.launcher.StandardPHPType"; //$NON-NLS-1$

	/**
	 * Returns a path for the PHP classpath container identifying the default VM
	 * install.
	 * 
	 * @return classpath container path
	 * @since 3.2
	 */
	public static IPath newDefaultPHPContainerPath() {
		return new Path(PHP_CONTAINER);
	}

	/**
	 * Returns a path for the PHP classpath container identifying the specified
	 * VM install by type and name.
	 * 
	 * @param vm
	 *            vm install
	 * @return classpath container path
	 * @since 3.2
	 */
	public static IPath newPHPContainerPath(PHPexeItem item) {
		return newPHPContainerPath(item.getDebuggerID(), item.getName());
	}

	/**
	 * Returns a path for the PHP classpath container identifying the specified
	 * VM install by type and name.
	 * 
	 * @param typeId
	 *            vm install type identifier
	 * @param name
	 *            vm install name
	 * @return classpath container path
	 * @since 3.2
	 */
	public static IPath newPHPContainerPath(String typeId, String name) {
		IPath path = newDefaultPHPContainerPath();
		path = path.append(typeId);
		path = path.append(name);
		return path;
	}

	/**
	 * Returns a path for the PHP classpath container identifying the specified
	 * execution environment.
	 * 
	 * @param environment
	 *            execution environment
	 * @return classpath container path
	 * @since 3.2
	 */
	public static IPath newPHPContainerPath(PHPVersion version) {
		IPath path = newDefaultPHPContainerPath();
		path = path.append(ID_STANDARD_VM_TYPE);
		path = path.append(version.getAlias());
		return path;
	}

	public static PHPVersion getPHPVersion(IPath containerPath) {
		if (containerPath.segmentCount() == 3
				&& containerPath.segment(1).equals(ID_STANDARD_VM_TYPE)) {
			return PHPVersion.byAlias(containerPath.segment(2));
		}
		return null;
	}

	public static PHPexeItem getPHPexeItem(IPath containerPath) {
		if (containerPath.segmentCount() == 3) {
			return PHPexes.getInstance().getItem(containerPath.segment(1),
					containerPath.segment(2));
		}
		return null;
	}

}
