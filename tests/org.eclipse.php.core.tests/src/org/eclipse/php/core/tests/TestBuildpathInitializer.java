package org.eclipse.php.core.tests;

import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.BuildpathContainerInitializer;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathContainer;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IBuiltinModuleProvider;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.php.internal.core.Logger;

public class TestBuildpathInitializer extends BuildpathContainerInitializer {
	
	public void initialize(IPath containerPath, IScriptProject project) throws CoreException {
		
		if (containerPath.segmentCount() > 0) {
			String segment = containerPath.segment(0);
			
			IBuildpathContainer container = null;
			if (segment.endsWith(".PERSON")) {
				container = new BuildpathContainer("Person Library", containerPath, "workspace/php_libraries/person");
			} 
			// Add another libraries here:
			
			if (container != null) {
				DLTKCore.setBuildpathContainer(
					containerPath, 
					new IScriptProject[] { project },
					new IBuildpathContainer[] {
						container
					},
					null
				);
			}
		}
	}
	
	class BuildpathContainer implements IBuildpathContainer {

		private String description;
		private IPath containerPath;
		private String libraryPath;
		private IBuildpathEntry[] buildPathEntries;

		public BuildpathContainer(String description, IPath containerPath, String libraryPath) {
			this.description = description;
			this.containerPath = containerPath;
			this.libraryPath = libraryPath;
		}

		public IBuildpathEntry[] getBuildpathEntries(IScriptProject project) {
			if (buildPathEntries == null) {
				IEnvironment environment = EnvironmentManager.getEnvironment(project);
				try {
					URL url = FileLocator.find(PHPCoreTests.getDefault().getBundle(), new Path(libraryPath), null);
					URL resolved = FileLocator.resolve(url);
					IPath path = Path.fromOSString(resolved.getFile());
					if (environment != null) {
						path = EnvironmentPathUtils.getFullPath(environment, path);
					}
					buildPathEntries = new IBuildpathEntry[] {
						DLTKCore.newLibraryEntry(
							path, 
							BuildpathEntry.NO_ACCESS_RULES,
							BuildpathEntry.NO_EXTRA_ATTRIBUTES,
							BuildpathEntry.INCLUDE_ALL,
							BuildpathEntry.EXCLUDE_NONE,
							false,
							true
						)
					};
				} catch (Exception e) {
					Logger.logException(e);
				}
			}
			return buildPathEntries;
		}

		public IBuiltinModuleProvider getBuiltinProvider(IScriptProject project) {
			return null;
		}

		public String getDescription(IScriptProject project) {
			return description;
		}

		public int getKind() {
			return K_SYSTEM;
		}

		public IPath getPath() {
			return containerPath;
		}
	}
}
