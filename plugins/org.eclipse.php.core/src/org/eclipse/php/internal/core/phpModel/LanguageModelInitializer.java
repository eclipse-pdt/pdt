package org.eclipse.php.internal.core.phpModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.parser.PHPVersion;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;

public class LanguageModelInitializer extends BuildpathContainerInitializer {

	public static final String CONTAINER_PATH = "org.eclipse.php.core.LANGUAGE";
	private static final String LANGUAGE_LIBRARY_PATH = "Resources/language/php%d"; //$NON-NLS-1$

	public LanguageModelInitializer() {
	}

	public void initialize(IPath containerPath, IScriptProject project) throws CoreException {
		if (containerPath.segmentCount() > 0 && containerPath.segment(0).equals(CONTAINER_PATH)) {
			try {
				if (isPHPProject(project)) {
					DLTKCore.setBuildpathContainer(containerPath,
						new IScriptProject[] { project },
						new IBuildpathContainer[] { getBuildpathContainer(project, containerPath) }, null);
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
	}

	private IBuildpathContainer getBuildpathContainer(IScriptProject project, IPath containerPath) throws IOException {
		String libraryPath = getLanguageLibraryPath(project);

		URL url = FileLocator.find(PHPCorePlugin.getDefault().getBundle(), new Path(libraryPath), null);
		URL resolved = FileLocator.resolve(url);
		IPath path = Path.fromOSString(resolved.getFile());

		return new LanguageModelContainer(path, containerPath);
	}

	private static int getPHPVersion(IScriptProject project) {
		return PHPVersion.PHP4.equals(PhpVersionProjectPropertyHandler.getVersion(project.getProject())) ? 4 : 5;
	}

	private static String getLanguageLibraryPath(IScriptProject project) {
		return String.format(LANGUAGE_LIBRARY_PATH, getPHPVersion(project));
	}

	private static boolean isPHPProject(IScriptProject project) {
		String nature = getNatureFromProject(project);
		return PHPNature.ID.equals(nature);
	}

	private static String getNatureFromProject(IScriptProject project) {
		try {
			IDLTKLanguageToolkit languageToolkit = DLTKLanguageManager.getLanguageToolkit(project);
			if (languageToolkit != null) {
				return languageToolkit.getNatureId();
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return null;
	}

	/**
	 * Modifies PHP project buildpath so it will contain path to the language model library
	 * @param project Project handle
	 * @throws ModelException
	 */
	public static void enableLanguageModelFor(IScriptProject project) throws ModelException {
		if (!isPHPProject(project)) {
			return;
		}

		IPath containerPath = new Path(LanguageModelInitializer.CONTAINER_PATH);

		boolean found = false;

		IBuildpathEntry[] rawBuildpath = project.getRawBuildpath();
		for (IBuildpathEntry entry : rawBuildpath) {
			if (entry.isContainerEntry() && entry.getPath().equals(containerPath)) {
				found = true;
				break;
			}
		}

		if (!found) {
			IBuildpathEntry containerEntry = DLTKCore.newContainerEntry(containerPath);
			int newSize = rawBuildpath.length + 1;
			List<IBuildpathEntry> newRawBuildpath = new ArrayList<IBuildpathEntry>(newSize);
			newRawBuildpath.addAll(Arrays.asList(rawBuildpath));
			newRawBuildpath.add(containerEntry);
			project.setRawBuildpath(newRawBuildpath.toArray(new IBuildpathEntry[newSize]), null);
		}
	}

	class LanguageModelContainer implements IBuildpathContainer {

		private IPath libraryPath;
		private IPath containerPath;

		public LanguageModelContainer(IPath libraryPath, IPath containerPath) {
			this.libraryPath = libraryPath;
			this.containerPath = containerPath;
		}

		public IBuildpathEntry[] getBuildpathEntries() {
			return new IBuildpathEntry[] {
				DLTKCore.newLibraryEntry(libraryPath, BuildpathEntry.NO_ACCESS_RULES, BuildpathEntry.NO_EXTRA_ATTRIBUTES,
					BuildpathEntry.INCLUDE_ALL, BuildpathEntry.EXCLUDE_NONE, false, true)
			};
		}

		public IBuiltinModuleProvider getBuiltinProvider() {
			return null;
		}

		public String getDescription() {
			return "PHP Language Library";
		}

		public int getKind() {
			return K_SYSTEM;
		}

		public IPath getPath() {
			return containerPath;
		}
	}
}
